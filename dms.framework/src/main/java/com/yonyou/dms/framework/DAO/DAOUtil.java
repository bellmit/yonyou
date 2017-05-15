
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : DAOUtil.java
*
* @Author : zhangxc
*
* @Date : 2016年7月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月6日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.DAO;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Paginator;
import org.javalite.activejdbc.RowProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.domain.BaseModel;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.RequestPageInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.DALException;
import com.yonyou.dms.function.service.common.RegexReplaceCallBack;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.ModelDelegate;
import org.javalite.activejdbc.Paginator;
import org.javalite.activejdbc.RowProcessor;

/**
 * 定义数据库层的util 方法
 * 
 * @author zhangxc
 * @date 2016年7月6日
 */

public class DAOUtil {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

    /**
     * 屏蔽私有构造方法
     * 
     * @author zhangxc
     * @date 2017年1月15日
     */
    private DAOUtil(){
    }

    /**
     * 根据sql 语句进行查询
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static Map findFirst(String sql, List queryParam) {
        return commonFindFirst(sql, queryParam, null);
    }

    /**
     * 根据自定义接口查询对应的数据
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql
     * @param queryParam
     * @param defindProcessor
     * @return
     */
    public static Map findFirst(String sql, List queryParam, DefinedRowProcessor defindProcessor) {
        return commonFindFirst(sql, queryParam, defindProcessor);
    }

    /**
     * 通用查找数据库的第一条数据
     * 
     * @author zhangxc
     * @date 2016年12月17日
     * @param sql
     * @param queryParam
     * @param defindProcessor
     * @return
     */
    private static Map commonFindFirst(String sql, List queryParam, DefinedRowProcessor defindProcessor) {
        // 根据SQL 语句返回对应的数据
        List<Map> result = commonFindAll(sql, queryParam, true, defindProcessor, null);
        /**
         * 对返回的条数进行控制
         */
        if (CommonUtils.isNullOrEmpty(result)) {
            return null;
        }
        if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new DALException("返回的条数不正确");
        }
    }

    /**
     * 执行权限查询
     * 
     * @author zhangxc
     * @date 2016年8月1日
     * @param sql
     * @param queryParam
     * @return
     */
    public static List<Map> findAll(String sql, List queryParam) {
        return findAll(sql, queryParam, true);
    }

    /**
     * 根据sql 语句进行查询
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static List<Map> findAll(String sql, List queryParam, boolean isAclCheck) {
        return commonFindAll(sql, queryParam, isAclCheck, null, null);
    }

    /**
     * 根据自定义接口查询对应的数据
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql
     * @param queryParam
     * @param defindProcessor
     * @return
     */
    public static List<Map> findAll(String sql, List queryParam, DefinedRowProcessor defindProcessor) {
        return commonFindAll(sql, queryParam, true, defindProcessor, null);
    }

    /**
     * 执行数据权限控制范围内的查询
     * 
     * @author Administrator
     * @date 2016年12月5日
     * @param sql
     * @param queryParam
     * @param menuId
     * @return
     */
    public static List<Map> findAll(String sql, List queryParam, String menuId) {
        return commonFindAll(sql, queryParam, true, null, menuId);
    }

    /**
     * 根据自定义接口查询对应数据权限范围的数据
     * 
     * @author Administrator
     * @date 2016年12月6日
     * @param sql
     * @param queryParam
     * @param defindProcessor
     * @param menuId
     * @return
     */
    public static List<Map> findAll(String sql, List queryParam, DefinedRowProcessor defindProcessor, String menuId) {
        return commonFindAll(sql, queryParam, true, defindProcessor, menuId);
    }

    /**
     * 制定通用查询所有数据的方法
     * 
     * @author zhangxc
     * @date 2016年12月17日
     * @param sql
     * @param queryParam
     * @param isAclCheck
     * @param defindProcessor
     * @return
     */
    private static List<Map> commonFindAll(String sql, List queryParam, boolean isAclCheck,
                                           DefinedRowProcessor defindProcessor, String menuId) {
        List queryParamList = queryParam;
        if (queryParam == null) {
            queryParamList = new ArrayList<>();
        }
        // 获得封装了数据权限的SQL
        String sqlFinal;
        if (isAclCheck) {
            // 进行用户权限控制
            if (menuId != null) {
                sqlFinal = getDefaultDataRange(sql, queryParamList, menuId);
            } else {
                // 进行经销商权限控制
                sqlFinal = getDealerAclSql(sql, queryParamList);
            }
        } else {
            sqlFinal = sql;
        }
        if (defindProcessor != null) {
            RowProcessor processor = Base.find(sqlFinal, queryParamList.toArray());
            processor.with(defindProcessor);
            return defindProcessor.getResult();
        } else {
            return Base.findAll(sqlFinal, queryParamList.toArray());
        }
    }

    /**
     * 根据sql 语句进行分页查询
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static PageInfoDto pageQuery(String sql, List queryParam) {
        return commonPageQuery(sql, queryParam, null, null);
    }

    /**
     * 根据sql 语句进行分页查询,通过自定义实现接口
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static PageInfoDto pageQuery(String sql, List queryParam, DefinedRowProcessor processor) {
        return commonPageQuery(sql, queryParam, processor, null);
    }

    /**
     * 根据sql 语句进行数据权限控制范围内的分页查询
     * 
     * @author Administrator
     * @date 2016年12月5日
     * @param sql
     * @param queryParam
     * @param menuId
     * @return
     */
    public static PageInfoDto pageQuery(String sql, List queryParam, String menuId) {
        return commonPageQuery(sql, queryParam, null, menuId);
    }

    /**
     * 根据sql 语句进行分页查询,通过自定义实现接口
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static PageInfoDto pageQuery(String sql, List queryParam, DefinedRowProcessor processor, String menuId) {
        return commonPageQuery(sql, queryParam, processor, menuId);
    }

    /**
     * 根据sql语句进行限制最大条数下载
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static List<Map> downloadPageQuery(String sql, List queryParam) {
        return downloadCommonQuery(sql, queryParam, null, null).getRows();
    }

    /**
     * 下载最大条数限制
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql 查询的SQL 语句
     * @param queryParam 查询的参数
     * @param processor 转换器
     * @return
     */
    private static PageInfoDto downloadCommonQuery(String sql, List queryParam, DefinedRowProcessor processor,
                                                   String menuId) {
        List queryParamList = queryParam;
        // 创建queryParam 对象
        if (queryParam == null) {
            queryParamList = new ArrayList<>();
        }
        // 获取分页信息
        RequestPageInfoDto requestPageInfoDto = ApplicationContextHelper.getBeanByType(RequestPageInfoDto.class);
        Integer pageSize = OemDictCodeConstants.DOWNLOAD_PAGE_SIZE_MAX;
        String sort = requestPageInfoDto.getSort();
        String order = requestPageInfoDto.getOrder();
        int page = 1;

        // 定义排序字段
        StringBuilder orders = null;
        if (!StringUtils.isNullOrEmpty(sort)) {
            orders = new StringBuilder();
            String[] sortSplitArray = sort.split(",");
            String[] orderSplitArray = order.split(",");
            for (int i = 0; i < sortSplitArray.length; i++) {
                orders.append(sortSplitArray[i]).append(" ").append(orderSplitArray[i]);
                if (i != (sortSplitArray.length - 1)) {
                    orders.append(",");
                }
            }
        }
        // 获得封装了数据权限的SQL
        String sqlFinal;
        // 进行用户权限控制
        if (menuId != null) {
            sqlFinal = getDefaultDataRange(sql, queryParamList, menuId);
        } else {
            // 进行经销商权限控制
            // sqlFinal = getDealerAclSql(sql, queryParamList);
            sqlFinal = sql;
        }

        // 构建分页对象
        Paginator paginator = new Paginator(pageSize, sqlFinal, queryParamList.toArray());
        if (orders != null) {
            paginator.orderBy(orders.toString());
        }
        PageInfoDto pageDto = new PageInfoDto();
        pageDto.setTotal(paginator.getCount());
        List<Map> results;
        if (processor == null) {
            results = paginator.getPage(page);
        } else {
            paginator.getPage(page, processor);
            results = processor.getResult();
        }
        pageDto.setRows(results);
        return pageDto;
    }

    /**
     * 通用功能查询
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql 查询的SQL 语句
     * @param queryParam 查询的参数
     * @param processor 转换器
     * @return
     */
    private static PageInfoDto commonPageQuery(String sql, List queryParam, DefinedRowProcessor processor,
                                               String menuId) {
        List queryParamList = queryParam;
        // 创建queryParam 对象
        if (queryParam == null) {
            queryParamList = new ArrayList<>();
        }
        // 获取分页信息
        RequestPageInfoDto requestPageInfoDto = ApplicationContextHelper.getBeanByType(RequestPageInfoDto.class);
        Integer pageSize = Integer.parseInt(requestPageInfoDto.getLimit());
        String sort = requestPageInfoDto.getSort();
        String order = requestPageInfoDto.getOrder();
        Integer offset = Integer.parseInt(requestPageInfoDto.getOffset());
        int page = (offset / pageSize) + 1;

        // 定义排序字段
        StringBuilder orders = null;
        if (!StringUtils.isNullOrEmpty(sort)) {
            orders = new StringBuilder();
            String[] sortSplitArray = sort.split(",");
            String[] orderSplitArray = order.split(",");
            for (int i = 0; i < sortSplitArray.length; i++) {
                orders.append(sortSplitArray[i]).append(" ").append(orderSplitArray[i]);
                if (i != (sortSplitArray.length - 1)) {
                    orders.append(",");
                }
            }
        }
        // 获得封装了数据权限的SQL
        String sqlFinal;
        // 进行用户权限控制
        if (menuId != null) {
            sqlFinal = getDefaultDataRange(sql, queryParamList, menuId);
        } else {
            // 进行经销商权限控制
            sqlFinal = getDealerAclSql(sql, queryParamList);
        }

        // 构建分页对象
        Paginator paginator = new Paginator(pageSize, sqlFinal, queryParamList.toArray());
        if (orders != null) {
            paginator.orderBy(orders.toString());
        }
        PageInfoDto pageDto = new PageInfoDto();
        pageDto.setTotal(paginator.getCount());
        List<Map> results;
        if (processor == null) {
            results = paginator.getPage(page);
        } else {
            paginator.getPage(page, processor);
            results = processor.getResult();
        }
        pageDto.setRows(results);
        return pageDto;
    }

    /**
     * 获取拼装后Dealer_Code 的SQL 语句
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql
     * @param queryParam
     * @return
     */
    @SuppressWarnings("unchecked")
    private static String getDealerAclSql(String sql, List queryParam) {
        String executeSql = sql;
        // 拼装Dealer_code 字段
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if (!StringUtils.isNullOrEmpty(loginInfo) && !StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
            String dealerCode = loginInfo.getDealerCode();
            StringBuilder sbFinal = new StringBuilder();
            sbFinal.append("select * from (").append(sql).append(") tt where DEALER_CODE in (?,'").append(CommonConstants.PUBLIC_DEALER_CODE).append("')");
            queryParam.add(dealerCode);
            executeSql = sbFinal.toString();
        }
        logger.debug("SQL:" + executeSql + ";param:" + Arrays.toString(queryParam.toArray()));
        return executeSql;
    }

    /**
     * 获取拼装后Dealer_Code 的SQL 语句
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql
     * @param queryParam
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String getDealerAclTableSql(String tableName, List queryParam) {
        // 拼装Dealer_code 字段
        String sql = tableName;
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if (!StringUtils.isNullOrEmpty(loginInfo) && !StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
            String dealerCode = loginInfo.getDealerCode();
            StringBuilder sbFinal = new StringBuilder();
            sbFinal.append("(select * from ").append(tableName).append(" where DEALER_CODE in (?,'").append(CommonConstants.PUBLIC_DEALER_CODE).append("'))");
            queryParam.add(dealerCode);
            sql = sbFinal.toString();
        }
        return sql;
    }

    /**
     * 对权限控制的SQL语句权限设置进行判断
     * 
     * @author Administrator
     * @date 2016年12月6日
     * @param sql
     * @param queryParam
     * @param menuId
     * @return
     */
    private static String getDefaultDataRange(String sql, List queryParam, String menuId) {
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        Map<String, Map<String, Object>> datasRange = userAccessInfoDto.getDataRange();

        // 查询菜单信息
        if (!datasRange.containsKey(menuId)) {
            Map<String, Object> defalultDataRange = new HashMap<>();
            defalultDataRange.put("10371001", true);
            datasRange.put(menuId, defalultDataRange);
        }
        String sqlFinal = getUserAclSql(sql, queryParam, datasRange.get(menuId));

        logger.debug("SQL:" + sqlFinal + ";param:" + Arrays.toString(queryParam.toArray()));
        return sqlFinal;
    }

    /**
     * 获取该菜单页面下的数据权限范围控制的sql语句
     * 
     * @author Administrator
     * @date 2016年12月5日
     * @param sql
     * @param queryParam
     * @param dataRange
     * @return
     */
    private static String getUserAclSql(String sql, List queryParam, Map<String, Object> dataRange) {
        // 拼装Dealer_code 字段
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);

        if (!StringUtils.isNullOrEmpty(loginInfo) && !StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
            String dealerCode = loginInfo.getDealerCode();
            Long organizationId = loginInfo.getOrgId();
            String employeeNo = loginInfo.getEmployeeNo();
            StringBuilder sbFinal = new StringBuilder();
            String childDeptsId = userAccessInfoDto.getChildDepts();
            // 根据数据权限范围判断对sql语句进行封装
            sbFinal.append("select * from (").append(sql).append(") tt where DEALER_CODE in (?,'").append(CommonConstants.PUBLIC_DEALER_CODE).append("')");
            queryParam.add(dealerCode);

            if (!dataRange.containsKey("10371004")) {
                // 本人
                if (dataRange.containsKey("10371001")) {
                    sbFinal.append("AND ( EMPLOYEE_NO =?");
                    queryParam.add(employeeNo);
                } else {
                    sbFinal.append("AND ( 1=1 ");
                }

                // 本组织及下属部门
                if (dataRange.containsKey("10371003") && !StringUtils.isNullOrEmpty(childDeptsId)) {
                    sbFinal.append(" OR ORGANIZATION_ID IN (");
                    sbFinal.append(childDeptsId).append(")");
                    // 本组织
                } else if (dataRange.containsKey("10371002")) {
                    sbFinal.append(" OR ORGANIZATION_ID =");
                    sbFinal.append("?");
                    queryParam.add(organizationId);
                }
                // 拼结束符
                sbFinal.append(")");
            }

            return sbFinal.toString();
        }
        return sql;
    }

    /**
     * 获得字段的本地化值
     * 
     * @author zhangxc
     * @date 2016年8月29日
     * @return
     */
    public static String getLocaleFieldValue(Map rowMap, String fieldName) {
        String dbLanguage = getDBLanguage();
        if (dbLanguage == null) {
            return (String) rowMap.get(fieldName);
        } else {
            String returnValue;
            if ((returnValue = (String) rowMap.get(fieldName + "_" + dbLanguage)) != null) {
                return returnValue;
            }
            return (String) rowMap.get(fieldName + "_" + dbLanguage.toLowerCase());
        }
    }

    /**
     * 获得当前
     * 
     * @author zhangxc
     * @date 2016年8月29日
     * @return
     */
    private static String getDBLanguage() {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        Locale locale = loginInfo.getLocale();
        if (locale != null) {
            String language = locale.getLanguage();
            return language.split("_")[0].toUpperCase();
        } else {
            return null;
        }
    }

    /**
     * 根据SQL语句参数执行对应的SQL,主要用于不同数据库中不同的SQL语句的实现
     * 
     * @author zhangxc
     * @date 2016年12月26日
     * @param sql
     * @param paramterMap
     */
    public static void execSqlByParamter(String sql, final Map<String, Object> paramterMap) {
        final List<Object> paramList = new ArrayList<>();
        // 对字符串结构进行替换分解
        String sqlResult = StringUtils.getMatcherPatternStr(sql, "#\\[([\\S]+)\\]", new RegexReplaceCallBack() {

            @Override
            public String replace(String matcherGroup) {
                paramList.add(paramterMap.get(matcherGroup));
                return "?";
            }
        });
        logger.debug("Sql:" + sqlResult + ";" + Arrays.toString(paramList.toArray()));
        // 执行对应的SQL 语句
        Base.exec(sqlResult, paramList.toArray());
    }

    /**
     * 执行批处理操作，如insert .... select 语句
     * 
     * @author zhangxc
     * @date 2017年1月3日
     * @param sql
     * @param paramterMap
     */
    public static void execBatchPreparement(String sql, List<Object> params) {
        PreparedStatement ps = Base.startBatch(sql);
        logger.debug("Sql:" + sql + ";" + Arrays.toString(params.toArray()));
        Base.addBatch(ps, params.toArray());
        Base.executeBatch(ps);
    }

    /**
     * 获得经销商参数信息
     * 
     * @author zhangxc
     * @date 2017年2月15日
     * @param params
     * @return
     */
    private static Object[] getDealerParamList(Object... params) {
        String currentDealerCode = getSessionDealerCode();
        List<Object> paramList = new ArrayList<Object>(Arrays.asList(params));
        paramList.add(currentDealerCode);
        return paramList.toArray();
    }

    /**
     * 获得经销商的信息
     * 
     * @author zhangxc
     * @date 2017年2月15日
     * @param subQuery
     * @return
     */
    private static String getDealerSubString(String subQuery) {
        return subQuery + " and " + BaseModel.Dealer_Code + " in(?,'-1')";
    }

    /**
     * 获得当前用户的经销商代码
     * 
     * @author zhangxc
     * @date 2016年11月9日
     * @return
     */
    public static String getSessionDealerCode() {
        try {
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            return loginInfo.getDealerCode();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        ;
        return null;
    }

    /**
     * 获得当前用户的经销商代码
     * 
     * @author zhangxc
     * @date 2016年11月9日
     * @return
     */
    public static LoginInfoDto getSessionUserInfo() {
        try {
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            return loginInfo;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        ;
        return null;
    }

    /**
     * 根据PO 的Class 返回对应的经销商权限过滤的SQL
     * 
     * @date 2016年7月6日
     * @param sql
     * @param queryParam
     * @return
     */
    public static <T extends Model> T findFirstByDealer(Class<T> poClass, String subQuery, Object... params) {
        return ModelDelegate.findFirst(poClass, getDealerSubString(subQuery), getDealerParamList(params));
    }

    /**
     * 根据当前经销商查询对应的数据
     * 
     * @author zhangxc
     * @date 2017年2月15日
     * @param subQuery
     * @param params
     * @return
     */
    public static <T extends Model> LazyList<T> findByDealer(Class<T> objClass, String subQuery, Object... params) {
        return ModelDelegate.where(objClass, getDealerSubString(subQuery), getDealerParamList(params));
    }

    public static <T extends Model> int deleteByDealer(Class<T> objClass, String query, Object... params) {
        int n = ModelDelegate.delete(objClass, getDealerSubString(query),  getDealerParamList(params));
        return n;
    }
}
