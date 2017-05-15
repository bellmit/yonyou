
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : DAOUtilGF.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.DAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Paginator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domain.RequestPageInfoDto;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

/**
* GFK定义数据库层的util 方法
* @author zhanshiwei
* @date 2017年1月13日
*/

public class DAOUtilGF{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
    /**
     * 根据sql 语句进行数据权限控制范围内的分页查询
     * 
     * @author zsw
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
     * 通用功能查询
     * 
     * @author zhangxc
     * @date 2016年7月7日
     * @param sql 查询的SQL 语句
     * @param queryParam 查询的参数
     * @param processor 转换器
     * @return
     */
    private static PageInfoDto commonPageQuery(String sql, List queryParam, DefinedRowProcessor processor,String menuId) {
        //创建queryParam 对象
        if (queryParam == null) {
            queryParam = new ArrayList<String>();
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
        String sqlFinal  = null;
        //进行用户权限控制
        if(menuId!=null){
            sqlFinal = getDefaultDataRange(sql, queryParam, menuId);
        }else{
            //进行经销商权限控制
            sqlFinal = getDealerAclSql(sql, queryParam);
        }
        
        //构建分页对象
       
        Paginator paginator = new Paginator(pageSize, sqlFinal, queryParam.toArray());
        if (orders != null) {
            paginator.orderBy(orders.toString());
        }
        PageInfoDto pageDto = new PageInfoDto();
        pageDto.setTotal(paginator.getCount());
        List<Map> results = null;
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
        
        //查询菜单信息
        if (!datasRange.containsKey(menuId)) {
            Map<String, Object> defalultDataRange = new HashMap<String, Object>();
            defalultDataRange.put("10371001", true);
            datasRange.put(menuId, defalultDataRange);
        }
        String sqlFinal = getUserAclSql(sql, queryParam, datasRange.get(menuId));
        
        logger.debug("SQL:"+sqlFinal+";param:"+Arrays.toString(queryParam.toArray()));
        return sqlFinal;
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
        // 拼装Dealer_code 字段
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if (!StringUtils.isNullOrEmpty(loginInfo) && !StringUtils.isNullOrEmpty(loginInfo.getDealerCode())) {
            String dealerCode = loginInfo.getDealerCode();
            StringBuilder sbFinal = new StringBuilder();
            sbFinal.append("select * from (").append(sql).append(") tt where DEALER_CODE in (?,'").append(CommonConstants.PUBLIC_DEALER_CODE).append("')");
            queryParam.add(dealerCode);
            sql = sbFinal.toString();
        }
        logger.debug("SQL:"+sql+";param:"+Arrays.toString(queryParam.toArray()));
        return sql;
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
            String userCode = loginInfo.getUserAccount();
            String orgCode=loginInfo.getOrgCode();
            Long   userId = loginInfo.getUserId();

            StringBuilder sbFinal = new StringBuilder();
            String childDeptsId = userAccessInfoDto.getChildDepts();
            int i = 0;
            // 根据数据权限范围判断对sql语句进行封装
            sbFinal.append("select * from (").append(sql).append(") tt where DEALER_CODE in (?,'").append(CommonConstants.PUBLIC_DEALER_CODE).append("')");
            queryParam.add(dealerCode);
            
            if(!dataRange.containsKey("10371004")){
                if (dataRange.containsKey("10371001")) {
                    sbFinal.append(" AND ( OWNED_BY =?");
                    queryParam.add(userId);
                }else{
                    sbFinal.append(" AND ( 1=1 ");
                }
                // 本组织及下属部门
                if (dataRange.containsKey("10371003") && !StringUtils.isNullOrEmpty(childDeptsId)) {
                    sbFinal.append(" OR OWNED_BY IN ( SELECT t_user.USER_ID FROM TM_USER t_user WHERE t_user.ORG_CODE in(?) AND USER_STATUS =").append(DictCodeConstants.STATUS_QY).append(")");
                    queryParam.add(childDeptsId);
                // 本组织
                } else if (dataRange.containsKey("10371002")) {
                    sbFinal.append(" OR OWNED_BY IN ( SELECT t_user.USER_ID FROM TM_USER t_user WHERE t_user.ORG_CODE=? AND USER_STATUS =").append(DictCodeConstants.STATUS_QY).append(")");
                    queryParam.add(orgCode);
                }
                //拼结束符
                sbFinal.append(")");
            }
            
            sql = sbFinal.toString();
        }
        return sql;
    }
    
    
    /**
     * 根据用户ID,ORG_CODE,menuId返回 OWEND_BY数据权限条件,并不是所有的功能页 面都需要设定数据控制范围的
     * 需要根据实际业务来设定
     * 
     * @param tabName
     *            表别名
     * @param userId
     * @param orgCode
     * @Param functionCode
     * @return
     */
    public static String getOwnedByStr(String tabName, Long userId, String orgCode, String menuId, String entityCode)
    {
        String str = "";
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        Map<String, Map<String, Object>> datasRange = userAccessInfoDto.getDataRange();
      
        if (tabName == null || tabName.trim().equals(""))
        {
            str = " AND OWNED_BY ";
        } else
        {
            str = " AND " + tabName + ".OWNED_BY ";
        }
        if (datasRange!=null&&!datasRange.isEmpty())
        {
            Map<String, Object> dataRangeMe =datasRange.get(menuId);
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371001"))
            {
                // 查询个人的数据
                str = str + " = " + userId;
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371002"))
            {
                // 查询本组织的数据
                str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE = '" + orgCode + "' AND USER_STATUS = "
                            + DictCodeConstants.STATUS_QY + ")";
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371003"))
            {
                // 查询本组织及组织下的数据
                str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE LIKE '" + orgCode + "%' AND USER_STATUS = "
                            + DictCodeConstants.STATUS_QY + ")";
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371004"))
            {
                // 查询所有的数据
                str = "";
            }
            if(dataRangeMe == null){
            	str = str + " = " + userId;
            }
        } else
        {
            str = str + " = " + userId;
        }
        return str;
    }
    
    //add by Bill Tang 2013-01-28 start
    /**
     * 根据用户ID,ORG_CODE,FUNCTION_CODE返回 数据权限条件,并不是所有的功能页 面都需要设定数据控制范围的
     * 需要根据实际业务来设定
     * 
     * @param tabName
     *            表别名
     * @param userId
     * @param orgCode
     * @Param functionCode
     * @return
     */
    public static String getFunRangeByStr( String tabName, String colName, Long userId, String orgCode, String menuId, String entityCode)
    {
        String str = "";
        UserAccessInfoDto userAccessInfoDto = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        Map<String, Map<String, Object>> datasRange = userAccessInfoDto.getDataRange();
        if (tabName == null || tabName.trim().equals(""))
        {
            str = " AND " + colName + " ";
        } else
        {
            str = " AND " + tabName + "." + colName + " ";
        }
        if (datasRange!=null&&!datasRange.isEmpty())
        {
            Map<String, Object> dataRangeMe =datasRange.get(menuId);
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371001"))
            {
                // 查询个人的数据
                str = str + " = " + userId;
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371002"))
            {
                // 查询本组织的数据
                str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE = '" + orgCode + "' AND USER_STATUS = "
                            + DictCodeConstants.STATUS_IS_YES + ")";
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371003"))
            {
                // 查询本组织及组织下的数据
                str = str + " IN " + "(SELECT USER_ID FROM TM_USER WHERE ORG_CODE LIKE '" + orgCode + "%' AND USER_STATUS = "
                            + DictCodeConstants.DICT_IN_USE_START + ")";
            }
            if (dataRangeMe!=null&&!dataRangeMe.isEmpty()&&dataRangeMe.containsKey("10371004"))
            {
                // 查询所有的数据
                str = "";
            }
            if(dataRangeMe == null){
            	str = str + " = " + userId;
            }
        } else
        {
            str = str + " = " + userId;
        }
        return str;
    }
}
