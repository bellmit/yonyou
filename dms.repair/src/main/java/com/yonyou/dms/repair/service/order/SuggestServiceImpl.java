
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : SuggestServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年9月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月9日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.order.SuggestDTO;
import com.yonyou.dms.repair.domains.DTO.order.SuggestLabourDTO;
import com.yonyou.dms.repair.domains.DTO.order.SuggestPartDTO;
import com.yonyou.dms.repair.domains.PO.order.SuggestLabourPO;
import com.yonyou.dms.repair.domains.PO.order.SuggestPartPO;


/**
* 维修建议  
* @author jcsi
* @date 2016年9月9日
*/
@Service
public class SuggestServiceImpl implements SuggestService {

    /**
    * @author jcsi
    * @date 2016年9月9日
    * @param labourDto 维修项目
    * @param partDto   维修配件
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.SuggestService#saveSuggest(com.yonyou.dms.repair.domains.DTO.order.SuggestLabourDTO, com.yonyou.dms.repair.domains.DTO.order.SuggestPartDTO)
    */

    @Override
    public Long saveSuggest(SuggestDTO suggest)throws ServiceBizException {
        SuggestLabourPO labourPO=null;
        //维修项目
        for(SuggestLabourDTO dto:suggest.getSuggestLabours()){
             labourPO=new SuggestLabourPO();
             setLabourPO(labourPO,dto,suggest.getVin());
             labourPO.saveIt();
        }
        //维修配件
        for(SuggestPartDTO dto:suggest.getSuggestParts()){
            SuggestPartPO partPO=new SuggestPartPO();
            setPartPO(partPO,dto,suggest.getVin());
            partPO.saveIt();
            
        }
        return labourPO.getLongId();
    }
    
    /**
     * 注入维修项目属性值
     * @author jcsi
     * @date 2016年9月9日
      */
    private void setLabourPO(SuggestLabourPO labour,SuggestLabourDTO labourDto,String vin)throws ServiceBizException{
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.ASSIGN_LABOUR_HOUR,t.DEALER_CODE,t.LABOUR_CODE,t.LOCAL_LABOUR_CODE,t.LOCAL_LABOUR_NAME from tm_labour t where t.LABOUR_CODE=? and t.MODEL_LABOUR_CODE=?");
        List<Object> param=new ArrayList<Object>();
        param.add(labourDto.getLabourCode());
        param.add(labourDto.getModeGroup());
        Map result=DAOUtil.findFirst(sb.toString(), param);
        
        labour.setString("VIN", vin);
        labour.setString("LABOUR_CODE", labourDto.getLabourCode()); //维修项目代码
        labour.setString("LABOUR_NAME", labourDto.getLabourName()); //维修项目
        labour.setString("LOCAL_LABOUR_CODE", result.get("LOCAL_LABOUR_CODE")); //行管项目代码
        labour.setString("LOCAL_LABOUR_NAME", result.get("LOCAL_LABOUR_NAME")); //行管项目名称
        labour.setDouble("STD_LABOUR_HOUR", labourDto.getStdLabourHour());  //标准工时
        labour.setDouble("ASSIGN_LABOUR_HOUR", result.get("ASSIGN_LABOUR_HOUR")); //派工工时
        labour.setDouble("LABOUR_PRICE", labourDto.getLabourPrice());  //工时单价
        if(StringUtils.isNullOrEmpty(labourDto.getStdLabourHour())){
            labourDto.setStdLabourHour(new Double("0"));
        }
        if(StringUtils.isNullOrEmpty(labourDto.getLabourPrice())){
            labourDto.setLabourPrice(new Double("0"));
        }
        labour.setDouble("LABOUR_AMOUNT", labourDto.getStdLabourHour()*labourDto.getLabourPrice()); //工时费
        labour.setLong("REASON", labourDto.getReason()); //原因
        labour.setDate("SUGGEST_DATE", labourDto.getSuggestDate());
        labour.setString("REMARK", labourDto.getRemark());
        labour.setString("MODEL_LABOUR_CODE", labourDto.getModeGroup());
    }
    
    /**
    * 注入维修配件属性值
    * @author jcsi
    * @date 2016年9月9日
     */
    private void setPartPO(SuggestPartPO part,SuggestPartDTO partDto,String vin)throws ServiceBizException{
        part.setString("VIN", vin);
        part.setString("PART_NO", partDto.getPartNo()); //配件代码
        part.setString("PART_NAME", partDto.getPartName()); //配件名称
        part.setDate("SUGGEST_DATE", partDto.getSuggestDate()); //建议日期
        part.setDouble("SALES_PRICE", partDto.getSalesPrice());  //销售价
        part.setDouble("QUANTITY", partDto.getQuantity()); //数量
        if(!StringUtils.isNullOrEmpty(partDto.getQuantity())&&!StringUtils.isNullOrEmpty(partDto.getSalesPrice())){
            part.setDouble("AMOUNT", partDto.getQuantity()*partDto.getSalesPrice());  //金额
        }else{
            part.setDouble("AMOUNT", new Double(0));  //金额
        }
       
        part.setString("REMARK", partDto.getRemark()); //备注
        part.setLong("REASON", partDto.getReason()); //原因
    }

    /**
     * 查询维修项目
    * @author jcsi
    * @date 2016年10月12日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.SuggestService#findSuggestLabour(java.lang.Long)
     */
    @Override
    public List<Map> findSuggestLabour(String vin)throws ServiceBizException {
        //RepairOrderPO repairOrderPO= RepairOrderPO.findById(id);
        //VehiclePO vehiclePO=VehiclePO.findById(repairOrderPO.getLong("VEHICLE_ID"));
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.SUGGEST_LABOUR_ID,t.DEALER_CODE,t.LABOUR_CODE labourCode,t.LABOUR_NAME labourName,t.MODEL_LABOUR_CODE modeGroup,");
        sb.append("t.ASSIGN_LABOUR_HOUR assignLabourHour,t.LABOUR_AMOUNT labourAmount,t.REASON ,t.SUGGEST_DATE suggestDate,");
        sb.append("t.remark,t.LABOUR_PRICE workHourSinglePrice,t.VIN vin,t.STD_LABOUR_HOUR stdLabourHour,t.LOCAL_LABOUR_CODE localLabourCode,t.LOCAL_LABOUR_NAME localLabourName ");
        sb.append("from TT_SUGGEST_LABOUR t");
        sb.append(" where t.VIN=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(vin);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 查询维修配件
    * @author jcsi
    * @date 2016年10月12日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.SuggestService#findSuggestPart(java.lang.Long)
     */
    @Override
    public List<Map> findSuggestPart(String vin)throws ServiceBizException {
        //RepairOrderPO repairOrderPO= RepairOrderPO.findById(id);
        //VehiclePO vehiclePO=VehiclePO.findById(repairOrderPO.getLong("VEHICLE_ID"));
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.SUGGEST_MAINTAIN_PART_ID,t.DEALER_CODE,t.PART_NO part_code,t.PART_NAME part_name,t.SALES_PRICE salesPrice,");
        sb.append("t.QUANTITY quantity,t.AMOUNT amount,t.REASON reason,t.SUGGEST_DATE suggestDate,t.REMARK remark,t.vin vinShow    ");
        sb.append("from TT_SUGGEST_PART t   ");
        sb.append("where t.VIN=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(vin);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 修改维修建议
    * @author jcsi
    * @date 2016年10月12日
    * @param suggest
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.SuggestService#editSuggest(com.yonyou.dms.repair.domains.DTO.order.SuggestDTO)
     */
    @Override
    public Long editSuggest(SuggestDTO suggest) throws ServiceBizException {
        //1.删除
        if(!StringUtils.isNullOrEmpty(suggest.getDelLabourId())){
            String[] delLabourIdArr=suggest.getDelLabourId().split(",");  //删除的建议维修项目id
            for(int i=0;i<delLabourIdArr.length;i++){
                SuggestLabourPO suggestLabourPO= SuggestLabourPO.findById(delLabourIdArr[i]);
                if(suggestLabourPO!=null){
                	suggestLabourPO.delete();	
                }
                
            }
        }
        if(!StringUtils.isNullOrEmpty(suggest.getDelPartId()) ){
            String[] delPartIdArr=suggest.getDelPartId().split(",");  //删除建议维修配件的id
            
            for(int i=0;i<delPartIdArr.length;i++){
                SuggestPartPO suggestPartPO= SuggestPartPO.findById(delPartIdArr[i]);
                if(suggestPartPO!=null){
                	suggestPartPO.delete();
                }
                
            }
        }
        
        
        //修改
        List<SuggestLabourDTO>  laboursList =suggest.getSuggestLabours();
        for(SuggestLabourDTO dto:laboursList){
            //如果为空  则  新增  否则修改
            SuggestLabourPO labourPO=new SuggestLabourPO();
            if(!StringUtils.isNullOrEmpty(dto.getSuggestLabourId())){
                 labourPO=SuggestLabourPO.findById(dto.getSuggestLabourId());
            }
            setLabourPO(labourPO,dto,suggest.getVin());
            labourPO.saveIt();
        }
        
        List<SuggestPartDTO>  partList =suggest.getSuggestParts();
        for(SuggestPartDTO dto:partList){
            //如果为空  则  新增  否则修改
            SuggestPartPO partPO=new SuggestPartPO();
            if(!StringUtils.isNullOrEmpty(dto.getSuggestMaintainPartId())){
                partPO=SuggestPartPO.findById(dto.getSuggestMaintainPartId());
            }
            setPartPO(partPO,dto,suggest.getVin());
            partPO.saveIt();
        }
        
        
        return null;
    }

}
