
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ChargeServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    Administrator    1.0
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.ChargeDTO;
import com.yonyou.dms.retail.domains.PO.basedata.ChargePo;

/**
* 收费区分实现类
* @author zhongshiwei
* @date 2016年7月10日
*/
@Service
@SuppressWarnings("rawtypes")
public class ChargeServiceImpl implements ChargeService{
    
    /**
     * 查询方法
    * @author zhongsw
    * @date 2016年8月29日
    * @param queryParam
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#ChargeSQL(java.util.Map)
    */
    @Override
    public PageInfoDto ChargeSQL(Map<String, String> queryParam) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,OEM_TAG,PRICE_TYPE,IS_PRINT,IS_FREE from tm_charge_partition where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("charge_partition_code"))){
            sqlSb.append(" and CHARGE_PARTITION_CODE = ?");
            params.add(queryParam.get("charge_partition_code"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("price_type"))){
            sqlSb.append(" and PRICE_TYPE = ?");
            params.add(Integer.parseInt(queryParam.get("price_type")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("charge_partition_name"))){
            sqlSb.append(" and CHARGE_PARTITION_NAME like ?");
            params.add("%"+queryParam.get("charge_partition_name")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("oem_tag"))){
            sqlSb.append(" and OEM_TAG = ?");
            params.add(Integer.parseInt(queryParam.get("oem_tag")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }

    /**
     * 新增
     * @author zhongshiwei
     * @date 2016年6月30日
     * @param Chargedto
     * @return 
     * @throws ServiceBizException
     */
    @Override
    public String insertCharge(ChargeDTO chargedto) throws ServiceBizException{
        StringBuffer sb =new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE from tm_charge_partition where 1=1 and CHARGE_PARTITION_CODE=?");
        List<Object> list=new ArrayList<Object>();
        list.add(chargedto.getCharge_partition_code());
		List<Map> map=DAOUtil.findAll(sb.toString(), list);
        StringBuffer sb2 =new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE from tm_charge_partition where 1=1 and CHARGE_PARTITION_NAME=?");
        List<Object> list2=new ArrayList<Object>();
        list2.add(chargedto.getCharge_partition_name());
        List<Map> map2=DAOUtil.findAll(sb2.toString(), list2);
        if(map.size()>0 || map2.size()>0){
            throw new ServiceBizException("收费区分代码或名称不能重复！");
        }else{
        ChargePo lap = new ChargePo();
        lap.setString("CHARGE_PARTITION_CODE", chargedto.getCharge_partition_code());
        lap.setString("CHARGE_PARTITION_NAME", chargedto.getCharge_partition_name());
        lap.setInteger("OEM_TAG", chargedto.getOem_tag());
        lap.setInteger("PRICE_TYPE", chargedto.getPrice_type());
        lap.setInteger("IS_PRINT", chargedto.getIs_print());
        lap.saveIt();
        return lap.getString("CHARGE_PARTITION_CODE");
        }
    }

    /**
     * 修改
    * @author zhongsw
    * @date 2016年8月5日
    * @param id
    * @param chargeDTO
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#updateCharge(java.lang.Long, com.yonyou.dms.retail.domains.DTO.basedata.ChargeDTO)
    */
    @Override
    public void updateCharge(String id, ChargeDTO chargedto) throws ServiceBizException{
        ChargePo lap=ChargePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        StringBuffer sb=new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_NAME,OEM_TAG,IS_PRINT from tm_charge_partition where CHARGE_PARTITION_NAME=?");
        List<Object> list =new ArrayList<Object>();
        list.add(chargedto.getCharge_partition_name());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        String s=(String) lap.get("CHARGE_PARTITION_NAME");
        if(s.equals(chargedto.getCharge_partition_name().toString()) || map.size()==0){
            lap.setString("CHARGE_PARTITION_CODE", chargedto.getCharge_partition_code());
            lap.setString("CHARGE_PARTITION_NAME", chargedto.getCharge_partition_name());
            lap.setInteger("OEM_TAG", chargedto.getOem_tag());
            lap.setInteger("PRICE_TYPE", chargedto.getPrice_type());
            lap.setInteger("IS_PRINT", chargedto.getIs_print());
            lap.saveIt();
        }else if(map.size()>0){
            throw new ServiceBizException("收费区分名称不能重复！");
        }
    }
    
    /**
     * 根据ID查询
    * @author zhongsw
    * @date 2016年8月5日
    * @param id
    * @return 查询结果
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#findById(java.lang.Long)
    */
    @Override
    public ChargePo findById(String id) throws ServiceBizException{
        ChargePo wtpo= ChargePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        return wtpo;
    }
    
    /**
     * 根据ID删除
    * @author zhongsw
    * @date 2016年8月5日
    * @param id
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#deleteChargeById(java.lang.Long)
    */
    @Override
    public void deleteChargeById(String id) throws ServiceBizException{
        ChargePo wtp=ChargePo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        wtp.delete();
    }
    
    /**
    * 收费区分下拉框
    * @author ZhengHe
    * @date 2016年8月22日
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#selectCharge()
    */
    @Override
    public List<Map> selectCharge() throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,OEM_TAG,PRICE_TYPE,IS_PRINT,IS_FREE from tm_charge_partition where 1=1 and IS_PRINT="+DictCodeConstants.STATUS_IS_YES);
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlsb.toString(), params);
        return result;
    }
    /**
     * 收费区分下拉框2
    * @author wantao
    * @date 2017年4月20日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#selectCharge()
     */
    public List<Map> selectCharge2() throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,OEM_TAG,PRICE_TYPE,IS_PRINT,IS_FREE from tm_charge_partition where 1=1 and IS_PRINT = 12781001");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlsb.toString(), params);
        return result;
    }

    /**
    * 根据Code获取信息
    * @author ZhengHe
    * @date 2016年8月26日
    * @param code
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#queryChargeByCode(java.lang.String)
    */
    	
    @Override
    public Map queryChargeByCode(String code) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,OEM_TAG,PRICE_TYPE,IS_PRINT,IS_FREE from tm_charge_partition where 1=1 and IS_PRINT="+DictCodeConstants.STATUS_IS_YES);
        List<Object> params = new ArrayList<Object>();
        sqlsb.append(" and CHARGE_PARTITION_CODE=?");
        params.add(code);
        Map chargeMap=DAOUtil.findFirst(sqlsb.toString(), params);
        return chargeMap;
    }

    
    /**
    * 根据Name获取信息
    * @author ZhengHe
    * @date 2016年10月20日
    * @param name
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.basedata.ChargeService#queryChargeByName(java.lang.String)
    */
    	
    @Override
    public Map queryChargeByName(String name) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,CHARGE_PARTITION_CODE,CHARGE_PARTITION_NAME,OEM_TAG,PRICE_TYPE,IS_PRINT,IS_FREE from tm_charge_partition where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlsb.append(" and CHARGE_PARTITION_NAME=?");
        params.add(name);
        Map chargeMap=DAOUtil.findFirst(sqlsb.toString(), params);
        return chargeMap;
    }
}
