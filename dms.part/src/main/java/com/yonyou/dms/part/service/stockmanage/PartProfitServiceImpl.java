
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartProfitServiceImpl.java
*
* @Author : xukl
*
* @Date : 2016年8月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月12日    xukl    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartProfitPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitItemDto;

/**
* PartProfitServiceImpl
* @author xukl
* @date 2016年8月12日
*/
@SuppressWarnings("rawtypes")
@Service
public class PartProfitServiceImpl implements PartProfitService {
    @Autowired
    private SystemParamService  systemparamservice;
    
    @Autowired
    private OperateLogService operateLogService;
    
    /**
     * 查询
    *  @author xukl
    * @date 2016年10月27日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#qryPartProfit(java.util.Map)
    */
    @Override
    public PageInfoDto qryPartProfit(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("SELECT\n")
        .append("  tpp.DEALER_CODE,\n")
        .append("  tpp.PROFIT_NO,\n")
        .append("  tpp.INVENTORY_NO,\n")
        .append("  tpp.`HANDLER`,\n")
        .append("  tpp.TOTAL_AMOUNT,\n")
        .append("  tpp.ORDER_DATE,\n")
        .append("  tpp.ORDER_STATUS,\n")
        .append("  tpp.PART_PROFIT_ID,te.EMPLOYEE_NAME\n")
        .append("FROM\n")
        .append("  tt_part_profit tpp LEFT JOIN tm_employee te on te.EMPLOYEE_NO = tpp.`HANDLER` and te.DEALER_CODE = tpp.DEALER_CODE\n")
        .append("where 1=1\n");

        if (!StringUtils.isNullOrEmpty(queryParam.get("profitNo"))) {
            sqlSb.append(" and tpp.PROFIT_NO like ?");
            params.add("%" + queryParam.get("profitNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateFrom"))) {
            sqlSb.append(" and tpp.ORDER_DATE>=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("orderDateFrom")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("orderDateTo"))) {
            sqlSb.append(" and tpp.ORDER_DATE<? ");
            params.add(DateUtil.addOneDay(queryParam.get("orderDateTo")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), params);
        return pageInfoDto;
    }
    
    /**
     * 根据id查询配件报溢信息
    *  @author xukl
    * @date 2016年8月15日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#getPartProfitById(java.lang.Long)
    */
    	
    @Override
    public TtPartProfitPO getPartProfitById(Long id) throws ServiceBizException {
        return TtPartProfitPO.findById(id);
    }
   
    
    /**
     * 根据主表id查询配件报溢明细
    *  @author xukl
    * @date 2016年8月15日
    * @param masterid
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#getPartProfitItemsById(java.lang.Long)
    */
    	
    @Override
    public List<Map> getPartProfitItemsById(Long masterid) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("tppi.ITEM_ID,\n")
                .append("tppi.PART_PROFIT_ID,\n")
                .append("tppi.DEALER_CODE,\n")
                .append("tppi.STORAGE_CODE as storageCodeShow,\n")
                .append("tppi.STORAGE_POSITION_CODE as storagePositionCodeShow,\n")
                .append("tppi.PART_NO as partCodeShow,\n")
                .append("tppi.PART_NAME as partNameShow,\n")
                .append("tppi.UNIT as unitShow,\n")
                .append("tppi.PROFIT_QUANTITY as profitQuantity,\n")
                .append("tppi.COST_PRICE as costPriceShow,\n")
                .append("tppi.COST_AMOUNT as costAmountShow,\n")
                .append("tppi.PROFIT_PRICE as profitPrice,\n")
                .append("tppi.PROFIT_AMOUNT as lossAmountShow,\n")
                .append("tppi.IS_FINISHED,\n")
                .append("tppi.FINISHED_DATE\n")
                .append("FROM\n")
                .append("  tt_part_profit_item tppi\n")
                .append("where tppi.PART_PROFIT_ID=?\n");
                List<Object> param = new ArrayList<Object>();
                param.add(masterid);
                List<Map> list = DAOUtil.findAll(sb.toString(), param);
                return list;
    }
    
    /**
     * 新增
    *  @author xukl
    * @date 2016年8月15日
    * @param profitNo
    * @param partProfitDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#addPartprofit(java.lang.String, com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitDto)
    */
    @Override
    public TtPartProfitPO addPartProfit(String profitNo,PartProfitDto partProfitDto) throws ServiceBizException {
        TtPartProfitPO partProfitPO = new TtPartProfitPO();
        // 设置对象属性
        setPartProfitPO(partProfitPO, partProfitDto);
        partProfitPO.setString("PROFIT_NO", profitNo);
        partProfitPO.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_NOSTATUS);
        partProfitPO.saveIt();
        Double amount=0.00;
        if (partProfitDto.getPartProfitItemList() != null) {
            for (PartProfitItemDto partProfitItemDto : partProfitDto.getPartProfitItemList()) {
            	TtPartProfitItemPO partProfitItemPO = setPartProfitItemPO(partProfitItemDto);
                amount=NumberUtil.add2Double(amount,partProfitItemPO.getDouble("PROFIT_AMOUNT")); 
                partProfitPO.add(partProfitItemPO);
            }
        }
        partProfitPO=TtPartProfitPO.findById(partProfitPO.getLongId());
        partProfitPO.setDouble("TOTAL_AMOUNT", amount);
        partProfitPO.saveIt();
        return partProfitPO;
    }
    
    /**
     * 修改
    *  @author xukl
    * @date 2016年8月15日
    * @param id
    * @param partProfitDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#updatePartProfit(java.lang.Long, com.yonyou.dms.part.domains.DTO.stockmanage.PartProfitDto)
    */
    	
    @Override
    public void updatePartProfit(Long id, PartProfitDto partProfitDto) throws ServiceBizException{
    	TtPartProfitPO partProfitPO = TtPartProfitPO.findById(id);
        //设置对象属性
        setPartProfitPO(partProfitPO, partProfitDto);
        partProfitPO.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_NOSTATUS);
        partProfitPO.saveIt();
          
          //删除原配件报溢明细
        TtPartProfitItemPO.delete("PART_PROFIT_ID = ?", id);
          
        if (partProfitDto.getPartProfitItemList() != null) {
            for (PartProfitItemDto partProfitItemDto : partProfitDto.getPartProfitItemList()) {
            	TtPartProfitItemPO partProfitItemPO = setPartProfitItemPO(partProfitItemDto);
                partProfitPO.add(partProfitItemPO);
            }
        }
    }
    
    /**
     * 
    *  @author xukl
    * @date 2016年8月15日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#deletePartProfitbyId(java.lang.Long)
    */
    	
    @Override
    public void deletePartProfitbyId(Long id) throws ServiceBizException{
    	TtPartProfitPO partProfitPO = TtPartProfitPO.findById(id);
        int order_status = (Integer) partProfitPO.get("ORDER_STATUS");
        if(order_status==DictCodeConstants.IN_ORDER_ISSTATUS){
            throw new ServiceBizException("此报溢单已入库不能删除!");
        }
        //记录操作日志
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件报溢单删除：报溢单号【"+partProfitPO.getString("PROFIT_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        
        partProfitPO.deleteCascadeShallow();
        
       
    }
    /**
    * 设置主表对象属性
    * @author xukl
    * @date 2016年8月15日
    * @param partProfitPO
    * @param partProfitDto
    * @throws ServiceBizException
    */
    	
    private void setPartProfitPO(TtPartProfitPO partProfitPO, PartProfitDto partProfitDto) throws ServiceBizException {
        partProfitPO.setString("PROFIT_NO", partProfitDto.getProfitNo());//报溢单号
        partProfitPO.setDate("ORDER_DATE", partProfitDto.getOrderDate());//开单日期
        partProfitPO.setString("HANDLER", partProfitDto.getHandler());//经手人
        partProfitPO.setInteger("ORDER_STATUS", partProfitDto.getOrderStatus());//单据状态
        partProfitPO.setString("INVENTORY_NO", partProfitDto.getInventoryNo());//盘点单号
    }
    
    /**
    *设置明细表对象属性
    * @author xukl
    * @date 2016年8月15日
    * @param partProfitItemDto
    * @return
    * @throws ServiceBizException
    */
    	
    private TtPartProfitItemPO setPartProfitItemPO(PartProfitItemDto partProfitItemDto) throws ServiceBizException {
    	TtPartProfitItemPO partProfitItemPO = new TtPartProfitItemPO();
        partProfitItemPO.setInteger("IS_FINISHED", partProfitItemDto.getIsFinished());
        partProfitItemPO.setString("STORAGE_CODE", partProfitItemDto.getStorageCode());
        partProfitItemPO.setString("STORAGE_POSITION_CODE", partProfitItemDto.getStoragePositionCode());
        partProfitItemPO.setString("PART_NO", partProfitItemDto.getPartNo());
        partProfitItemPO.setString("PART_NAME", partProfitItemDto.getPartName());
        partProfitItemPO.setString("UNIT", partProfitItemDto.getUnit());
        partProfitItemPO.setDouble("PROFIT_QUANTITY", partProfitItemDto.getProfitQuantity());
        partProfitItemPO.setDouble("COST_PRICE", partProfitItemDto.getCostPrice());
        partProfitItemPO.setDouble("COST_AMOUNT", partProfitItemDto.getCostAmount());
        partProfitItemPO.setDouble("PROFIT_AMOUNT", partProfitItemDto.getProfitAmount());
        partProfitItemPO.setDouble("PROFIT_PRICE", partProfitItemDto.getProfitPrice());
        return partProfitItemPO;
    }
     
    /**
    * 入账
    * @author xukl
    * @date 2016年8月15日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartProfitService#doProfitInWhouse(java.lang.Long)
    */
    	
    @Override
     public void doProfitInWhouse(Long id) throws ServiceBizException {
    	TtPartProfitPO partProfitPO = TtPartProfitPO.findById(id);
         //是否关联了盘点数据
         if(!StringUtils.isNullOrEmpty(partProfitPO.getString("INVENTORY_NO"))){
             if(partProfitPO.getString("INVENTORY_NO").equals(DictCodeConstants.STATUS_IS_YES)){
                 throw  new ServiceBizException(partProfitPO.getString("INVENTORY_NO")+"盘点单号不能重复报溢");
             }else{
            	 TtPartInventoryPO.update("LOSS_TAG=?","INVENTORY_NO=? and DEALER_CODE=?",DictCodeConstants.STATUS_IS_YES,partProfitPO.getString("INVENTORY_NO"),FrameworkUtil.getLoginInfo().getDealerCode());
             }
             
         }
         
         // 查询报溢单明细表信息
         List<Map> result = getPartProfitItemsById(id);
         if (null == result || result.size() == 0) {
             throw new ServiceBizException("未找到相关数据");
         }
         // 更新库存and记录配件流水
         partProfitPO= updateStock(result,partProfitPO);
         // 更新配件报溢明细表入库状态为已入库
         TtPartProfitItemPO.update("IS_FINISHED=?,FINISHED_DATE=?", "PART_PROFIT_ID=?", DictCodeConstants.STATUS_IS_YES,new Date(), id);
         // 更新配件报溢主表入库状态为已入库
         partProfitPO.setInteger("ORDER_STATUS", DictCodeConstants.IN_ORDER_ISSTATUS);
         partProfitPO.saveIt();
     }
     
    /**
    * 更新库存
    * @author xukl
    * @date 2016年8月15日
    * @param result
    * @param partProfitPO
    * @return
    * @throws ServiceBizException
    */
    private TtPartProfitPO updateStock(List<Map> result,TtPartProfitPO partProfitPO ) throws ServiceBizException {
        BasicParametersDTO basicParametersDTO= systemparamservice.queryBasicParameterByTypeandCode(Long.valueOf(DictCodeConstants.BASICOM_TAXRATE), "basicom_taxrate");
        String basicomTaxrate = basicParametersDTO.getParamValue();//税率
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
         Double sumInAmount=0.0;//不含税总金额
         for (int i = 0; i < result.size(); i++) {
             Map map = result.get(i);
             String storageCode = (String) map.get("storageCodeShow"); // 仓库代码
             String storagePositionCode = (String) map.get("storagePositionCodeShow");//库位代码
             String partNo = (String) map.get("partCodeShow"); // 配件代码
             String partName=(String) map.get("partNameShow");//配件名称
             Double inQuantity = Double.valueOf(map.get("profitQuantity").toString());// 入库数量
             Double profitAmount = Double.valueOf(map.get("lossAmountShow").toString());// 报溢金额
             Double profitPrice = Double.valueOf(map.get("profitPrice").toString());// 报溢单价
             Double inTaxedPrice = profitPrice*(1+Double.valueOf(basicomTaxrate));//入库含税单价
             Double inTaxedAmount = profitAmount*(1+Double.valueOf(basicomTaxrate));//入库含税金额
             sumInAmount +=profitAmount;
             // 仓库代码+配件代码 查询配件库存信息
             String strSql = "SELECT t.STOCK_QUANTITY,t.COST_AMOUNT,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=?";
             List<Object> queryParam = new ArrayList<Object>();
             queryParam.add(storageCode);
             queryParam.add(partNo);
             List<Map> stockList = DAOUtil.findAll(strSql, queryParam);
             if(null==stockList||stockList.size()==0){
            	 TmPartStockPO partStock=new TmPartStockPO();
                 partStock.setString("STORAGE_POSITION_CODE", storagePositionCode);
                 partStock.setString("storage_code",storageCode);
                 partStock.setString("part_code",partNo);
                 partStock.setString("part_name",partName);
                 partStock.setDate("last_stock_in",new Date());
                 partStock.setDouble("stock_quantity",inQuantity);
                 
                 Map mappart=qryPartByCode(partNo);
                 if(!CommonUtils.isNullOrEmpty(mappart)){
                     partStock.setInteger("part_status", mappart.get("part_status"));//是否停用
                     partStock.setDouble("limit_price", mappart.get("limit_price"));//销售限价
                     partStock.setDouble("advice_sale_price", mappart.get("advice_sale_price"));//建议销售价
                     partStock.setDouble("sales_price", mappart.get("advice_sale_price"));//销售价
                     partStock.setDouble("claim_price", mappart.get("claim_price"));//索赔价
                 }
                 partStock.setDouble("cost_price", profitPrice);
                 partStock.setDouble("cost_amount", profitAmount);
                 //新增库存
                 partStock.saveIt();
               //记录配件流水账
                 PartFlowPO pfpo= new PartFlowPO();
                 pfpo.setInteger("IN_OUT_TYPE", DictCodeConstants.PART_PROFIT);//出入库类型
                 pfpo.setTimestamp("OPERATE_DATE", new Date());//发生日期
                 pfpo.setDouble("STOCK_IN_QUANTITY", inQuantity);//入库数量
                 pfpo.setDouble("STOCK_QUANTITY", inQuantity);//库存数量
                 pfpo.setDouble("IN_OUT_NET_PRICE", profitPrice);//入库不含税单价
                 pfpo.setDouble("IN_OUT_NET_AMOUNT", profitAmount);//入库不含税金额
                 pfpo.setDouble("cost_price", profitPrice);//成本单价
                 pfpo.setDouble("cost_amount", profitAmount);//成本金额
                 pfpo.setDouble("IN_OUT_TAXED_PRICE",inTaxedPrice);//入库含税单价
                 pfpo.setDouble("IN_OUT_TAXED_AMOUNT", inTaxedAmount);//入库含税金额
                 pfpo.setString("SHEET_NO", partProfitPO.get("PROFIT_NO"));//入库单号
                 pfpo.setString("STORAGE_CODE", storageCode);//仓库代码
                 pfpo.setString("PART_NO", partNo);//配件代码
                 pfpo.setString("PART_NAME", partName);//配件名称
                 pfpo.setString("OPERATOR", loginInfo.getEmployeeNo());
                 pfpo.saveIt();
             }else{
                 Map stockMap = stockList.get(0);
                 Double stockQuantity = Double.valueOf(stockMap.get("stock_quantity").toString()); // 账面库存数量
                 Double costAmount = Double.valueOf(stockMap.get("cost_amount").toString()); // 成本金额
                 
                 // 计算最新库存数量 和 成本金额  成本单价
                 Double newStockQuantity = NumberUtil.add(new BigDecimal(inQuantity.toString()),new BigDecimal(stockQuantity.toString())).doubleValue();  // 入库后库存数量
                 Double newcostAmount =  NumberUtil.add(new BigDecimal(profitAmount.toString()),new BigDecimal(costAmount.toString())).doubleValue(); // 入库后成本金额
                 //Double newCosuPrice=newcostAmount/newStockQuantity;
                 
                 
                 // 更新库存
                 TmPartStockPO.update("last_stock_in = ? ,STORAGE_POSITION_CODE = ?,COST_AMOUNT=?,STOCK_QUANTITY=?,COST_PRICE=?",
                                    "STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=?",new Date(),storagePositionCode, newcostAmount,
                                    newStockQuantity,newcostAmount/newStockQuantity,
                                    storageCode, partNo,loginInfo.getDealerCode());
                 //记录配件流水账
                 PartFlowPO pfpo= new PartFlowPO();
                 pfpo.setInteger("IN_OUT_TYPE", DictCodeConstants.PART_PROFIT);//出入库类型
                 pfpo.setTimestamp("OPERATE_DATE", new Date());//发生日期
                 pfpo.setDouble("STOCK_IN_QUANTITY", inQuantity);//入库数量
                 pfpo.setDouble("STOCK_QUANTITY", newStockQuantity);//库存数量
                 pfpo.setDouble("IN_OUT_NET_PRICE", profitPrice);//入库不含税金额
                 pfpo.setDouble("IN_OUT_NET_AMOUNT", profitAmount);//成本金额
                 pfpo.setDouble("cost_price", newcostAmount/newStockQuantity);
                 pfpo.setDouble("cost_amount", newcostAmount);
                 pfpo.setDouble("IN_OUT_TAXED_PRICE", inTaxedPrice);
                 pfpo.setDouble("IN_OUT_TAXED_AMOUNT", inTaxedAmount);
                 pfpo.setString("SHEET_NO", partProfitPO.get("PROFIT_NO"));//入库单号
                 pfpo.setString("STORAGE_CODE", storageCode);//仓库代码
                 pfpo.setString("PART_NO", partNo);//配件代码
                 pfpo.setString("PART_NAME", partName);//配件名称
                 pfpo.setString("OPERATOR", loginInfo.getEmployeeNo());
                 pfpo.saveIt();
             }
         }
         partProfitPO.setDouble("TOTAL_AMOUNT", sumInAmount);//设置税后总金额
         return partProfitPO;
     }
    
    /**
    * 查配件基础信息
    * @author xukl
    * @date 2016年8月25日
    * @param partNo
    * @return
    */
    private Map qryPartByCode(String partNo){
        StringBuilder str=new StringBuilder("select * from tm_part_info where PART_CODE=?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(partNo);
        Map map = DAOUtil.findFirst(str.toString(), queryParam);
        return map;
    }
}
