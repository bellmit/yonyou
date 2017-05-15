
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : DefeatReasonServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月8日    DuPengXin    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.DefeatReasonDTO;
import com.yonyou.dms.retail.domains.PO.basedata.DefeatReasonPO;

/*
 * @author DuPengXin
 * @date 2016年7月1日
 */
@Service
public class DefeatReasonServiceImpl implements DefeatReasonService {

    /*
     * 根据查询条件查询数据
     * @author DuPengXin
     * @date 2016年7月7日
     */

    @Override
    public PageInfoDto QueryDefeatReason(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEFEAT_REASON_ID,DR_CODE,DR_DESC,IS_VALID,DEALER_CODE from  tm_defeat_reason where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if(!StringUtils.isNullOrEmpty(queryParam.get("drDesc"))){
            sqlSb.append(" and DR_DESC like ?");
            params.add("%"+queryParam.get("drDesc")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isValid"))) {
            sqlSb.append(" and IS_VALID = ?");
            params.add(Integer.parseInt(queryParam.get("isValid")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), params);
        return pageInfoDto;
    }

    /**
     * 新增
     * 
     * @author DuPengXin
     * @date 2016年7月11日
     * @param drdto
     * @return (non-Javadoc)
     * @see com.yonyou.dms.retail.service.basedata.DefeatReasonService#addDefeatReason(com.yonyou.dms.retail.domains.DTO.basedata.DefeatReasonDTO)
     */
    @Override
    public Long addDefeatReason(DefeatReasonDTO drdto) throws ServiceBizException {
        DefeatReasonPO dr = new DefeatReasonPO();
        if(SearchRepairType(drdto.getDrCode())){
        setDefeatReason(dr, drdto);
        dr.saveIt();
        return dr.getLongId();
        }else{
            throw new ServiceBizException("该战败原因代码已经存在");
        }
    }

    
    /**
    * 查询数据库战败原因代码是否重复
    * @author DuPengXin
    * @date 2016年10月17日
    * @param drCode
    * @return
    */
    	
    private boolean SearchRepairType(String drCode) {
        StringBuilder sb=new StringBuilder("select tdr.DEFEAT_REASON_ID,tdr.DEALER_CODE,tdr.DR_CODE from tm_defeat_reason tdr where tdr.DR_CODE=?");
        List<Object> param=new ArrayList<Object>();
        param.add(drCode);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 修改信息
     * 
     * @author DuPengXin
     * @date 2016年7月7日
     * @param id
     * @param drdto (non-Javadoc)
     * @see com.yonyou.dms.retail.service.basedata.DefeatReasonService#modifyReason(java.lang.Long,
     * com.yonyou.dms.retail.domains.DTO.basedata.DefeatReasonDTO)
     */
    public void modifyReason(Long id, DefeatReasonDTO drdto) throws ServiceBizException {
        DefeatReasonPO dr = DefeatReasonPO.findById(id);

        // 设置对象属性
        setDefeatReason(dr, drdto);
        dr.saveIt();
    }

    /**
     * 获取信息
     * 
     * @author DuPengXin
     * @date 2016年7月11日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.retail.service.basedata.DefeatReasonService#findById(java.lang.Long)
     */

    public DefeatReasonPO findById(Long id) throws ServiceBizException {
        DefeatReasonPO dr = DefeatReasonPO.findById(id);
        return dr;
    }

    /**
     * 设置对象属性
     * 
     * @author DuPengXin
     * @date 2016年7月6日
     * @param dr
     * @param drdto
     */

    public void setDefeatReason(DefeatReasonPO dr, DefeatReasonDTO drdto) throws ServiceBizException {
        dr.setString("DR_CODE", drdto.getDrCode());
        dr.setString("DR_DESC", drdto.getDrDesc());
        dr.setInteger("IS_VALID", drdto.getIsValid());
    }

    
    /**
     * 战败原因
    * @author zhanshiwei
    * @date 2016年10月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.DefeatReasonService#queryDefeatReasonSel(java.util.Map)
    */
    	
    @Override
    public List<Map> queryDefeatReasonSel(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEFEAT_REASON_ID,DR_CODE,DR_DESC,IS_VALID,DEALER_CODE from  tm_defeat_reason where 1=1 and IS_VALID= ").append(DictCodeConstants.STATUS_IS_YES);
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sqlSb.toString(), params);
    }
}
