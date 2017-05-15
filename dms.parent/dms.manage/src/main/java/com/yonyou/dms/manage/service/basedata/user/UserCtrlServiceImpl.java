
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : UserCtrlServiceImpl.java
*
* @Author : yll
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeDto;
import com.yonyou.dms.manage.domains.DTO.basedata.CommonTreeStateDto;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserCtrlPO;

/**
 * 用户受控权限实现类
 * 
 * @author yll
 * @date 2016年8月19日
 */
@Service
public class UserCtrlServiceImpl implements UserCtrlService {

    /**
     * 新增用户受控权限
     * 
     * @author yll
     * @date 2016年8月31日
     * @param UserID
     * @param ctrlCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserCtrlService#addUserCtrl(java.lang.String, java.lang.String)
     */
    @Override
    public Long addUserCtrl(String UserID, String ctrlCode) throws ServiceBizException {
        if (StringUtils.isNullOrEmpty(UserID)) {
            throw new ServiceBizException("用户id不能为空");
        }
        if (StringUtils.isNullOrEmpty(ctrlCode)) {
            // throw new ServiceBizException("控制代码不能为空");
            return null;
        }
        String[] sourceStrArray = ctrlCode.split(",");
        for (String ctrlCodeTree : sourceStrArray) {

            UserCtrlPO userCtrlPO = new UserCtrlPO();
            userCtrlPO.setLong("USER_ID", Long.parseLong(UserID));
            userCtrlPO.setString("CTRL_CODE", ctrlCodeTree);
            userCtrlPO.setString("TYPE", ctrlCodeTree.substring(0, 1));
            userCtrlPO.saveIt();
        }

        return null;
    }

    /**
     * 根据用户id删除菜单信息
     * 
     * @author yll
     * @date 2016年8月31日
     * @param UserID
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserCtrlService#deleteMenuByUserId(java.lang.String)
     */
    @Override
    public void deleteMenuByUserId(String UserID) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select USER_CTRL_ID,DEALER_CODE  from tm_user_CTRL where USER_ID='");
        sqlSb.append(Long.parseLong(UserID));
        sqlSb.append("'");

        List<Object> params = new ArrayList<Object>();
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        for (int i = 0; i < list.size(); i++) {
            Object ctrlId = list.get(i).get("USER_CTRL_ID");
            UserCtrlPO aa = UserCtrlPO.findById(ctrlId);
            aa.delete();
        }
    }

    /**
     * 加载用户受控权限
     * 
     * @author yll
     * @date 2016年8月31日
     * @param roletId
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.user.UserCtrlService#queryMenuCtrl(java.lang.String)
     */
    @Override
    public Map<String, String> queryMenuCtrl(String userId) throws ServiceBizException {
        String maintain = null;
        String accessories = null;
        String vehicleWarehouse = null;// 整车仓库
        String accessoriesWarehouse = null;// 配件仓库
        String favorableModels = null;
        StringBuilder sqlSb = new StringBuilder("select USER_CTRL_ID,DEALER_CODE,USER_ID,CTRL_CODE,TYPE from tm_user_ctrl where 1=1");
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(userId)) {
            String[] idList = userId.split(",");
            sqlSb.append(" and USER_ID in (");
            for (int i = 0; i < idList.length - 1; i++) {
                sqlSb.append("?,");
            }
            sqlSb.append("?");
            sqlSb.append(")");
            for (int i = 0; i < idList.length; i++) {
                params.add(Long.parseLong(idList[i]));
            }
            // sqlSb.append(" and USER_ID in ("+userId+")");

        }
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        Map<String, String> basicresult = new HashMap<String, String>();
        for (int i = 0; i < list.size(); i++) {
            String ctrlCode = (String) list.get(i).get("CTRL_CODE");
            Integer type = (Integer) list.get(i).get("TYPE");
            // String tcCode=ctrlCode.substring(0, 4);
            if (type.equals(DictCodeConstants.MAINTAIN)) {
                if (maintain == null) {
                    maintain = ctrlCode;
                } else {
                    maintain += ",";
                    maintain += ctrlCode;
                }
            }
            if (type.equals(DictCodeConstants.ACCESSORIES)) {

                if (accessories == null) {
                    accessories = ctrlCode;
                } else {
                    accessories += ",";
                    accessories += ctrlCode;
                }
            }
            if (type.equals(DictCodeConstants.VEHICLE_WAREHOUSE)) {
                if (vehicleWarehouse == null) {
                    vehicleWarehouse = ctrlCode;
                } else {
                    vehicleWarehouse += ",";
                    vehicleWarehouse += ctrlCode;
                }
            }
            if (type.equals(DictCodeConstants.ACCESSORIES_WAREHOUSE)) {
                if (accessoriesWarehouse == null) {
                    accessoriesWarehouse = ctrlCode;
                } else {
                    accessoriesWarehouse += ",";
                    accessoriesWarehouse += ctrlCode;
                }
            }
            if (type.equals(DictCodeConstants.FAVORABLE_MODELS)) {
                if (favorableModels == null) {
                    favorableModels = ctrlCode;
                } else {
                    favorableModels += ",";
                    favorableModels += ctrlCode;
                }
            }

        }

        basicresult.put("maintain", maintain);
        basicresult.put("accessories", accessories);
        basicresult.put("vehicleWarehouse", vehicleWarehouse);
        basicresult.put("accessoriesWarehouse", accessoriesWarehouse);
        basicresult.put("favorableModels", favorableModels);

        return basicresult;
    }

    @Override
    public List<CommonTreeDto> UserCtrlOption(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select a.OPTION_CODE,a.DEALER_CODE,a.OPTION_NAME,a.TYPE_NAME,b.CTRL_CODE,\n");
        sb.append("CASE a.OPTION_CODE\n");
        sb.append("   when '10000000' THEN '#'\n");
        sb.append("   when '20000000' THEN '#'\n");
        sb.append("   when '30000000' THEN '#'\n");
        sb.append("   when '70000000' THEN '#'\n");
        sb.append("   when '80000000' THEN '#'\n");
        sb.append("   when '90000000' THEN '#' else a.OPTION_TYPE END  as OPTION_TYPE\n");
        sb.append("  from tm_auth_option a\n");
        sb.append(" left JOIN tm_user t on 1=1 and t.USER_ID=\n").append(id);
        sb.append(" left join tm_user_ctrl b on a.OPTION_CODE=b.CTRL_CODE and t.USER_ID=b.USER_ID where 1=1\n");
        List<String> params = new ArrayList<String>();
        final List<CommonTreeDto> authOption = new ArrayList<CommonTreeDto>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                CommonTreeDto orgTreeOrg = new CommonTreeDto();
                CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
                String parent = (String) row.get("OPTION_TYPE").toString();
                if (!StringUtils.isEquals("#", parent)) {
                    parent = row.get("OPTION_TYPE") + "0000000";
                }
                orgTreeOrg.setParent(parent);
                orgTreeOrg.setId(row.get("OPTION_CODE").toString());
                orgTreeOrg.setText(row.get("OPTION_NAME").toString());
                if (!StringUtils.isNullOrEmpty(row.get("CTRL_CODE"))) {
                    CommonTreeStateDto.setChecked(true);
                } else {
                    CommonTreeStateDto.setChecked(false);
                }
                orgTreeOrg.setState(CommonTreeStateDto);
                authOption.add(orgTreeOrg);
            }
        });
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("SELECT DISTINCT A.DEALER_CODE,A.storage_code,A.storage_name,c.CTRL_CODE FROM ("
                                            + CommonConstants.VM_STORAGE + " )A\n");
        sql.append(" LEFT JOIN TT_USER_OPTION_MAPPING B \n");
        sql.append(" ON B.dealer_CODE = A.dealer_CODE \n");
        sql.append(" AND concat('4010',A.STORAGE_CODE) = B.OPTION_CODE\n");
        sql.append(" left JOIN tm_user t on 1=1 and t.USER_ID=\n").append(id);
        sql.append(" left join tm_user_ctrl c on t.USER_ID=c.USER_ID AND concat('4010',A.STORAGE_CODE) =c.CTRL_CODE\n");
        sql.append(" WHERE A.dealer_CODE = ? and A.STORAGE_NAME is not null and A.STORAGE_NAME !=''");
        List<String> params1 = new ArrayList<String>();
        params1.add(loginInfo.getDealerCode());
        List<Map> result = DAOUtil.findAll(sql.toString(), params1);
        String parent = "40100000";
        CommonTreeDto orgTreeOrg4 = new CommonTreeDto();
        CommonTreeStateDto CommonTreeStateDto1 = new CommonTreeStateDto();
        orgTreeOrg4.setParent("#");
        orgTreeOrg4.setId("40100000");
        orgTreeOrg4.setText("仓库");
        CommonTreeStateDto1.setChecked(false);
        orgTreeOrg4.setState(CommonTreeStateDto1);
        authOption.add(orgTreeOrg4);
        for (int i = 0; i < result.size(); i++) {
            // result.get(i);
            CommonTreeDto orgTreeOrg = new CommonTreeDto();
            CommonTreeStateDto CommonTreeStateDto = new CommonTreeStateDto();
            orgTreeOrg.setParent(parent);
            orgTreeOrg.setId("4010" + result.get(i).get("storage_code"));
            orgTreeOrg.setText("" + result.get(i).get("storage_name"));
            if (!StringUtils.isNullOrEmpty(result.get(i).get("CTRL_CODE"))) {
                CommonTreeStateDto.setChecked(true);
            } else {
                CommonTreeStateDto.setChecked(false);
            }
            orgTreeOrg.setState(CommonTreeStateDto);
            authOption.add(orgTreeOrg);

        }

        return authOption;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Map GetBusinessPurview(String ctrlCode) throws ServiceBizException {
        Map powerResult = new HashMap();
        synchronized(this){
            String sql = "select * from tm_user_ctrl where CTRL_CODE=? and USER_ID=?";
            List<Object> queryParam = new ArrayList<>();
            queryParam.add(ctrlCode);
            queryParam.add(FrameworkUtil.getLoginInfo().getUserId());
            List<Map> powerList = DAOUtil.findAll(sql, queryParam);
            Iterator<Map> powerIterator = powerList.iterator();
            while (powerIterator.hasNext()) {
                powerResult.put(powerIterator.next().get("CTRL_CODE"), true);
            } 
        }
        return powerResult;
    }
}
