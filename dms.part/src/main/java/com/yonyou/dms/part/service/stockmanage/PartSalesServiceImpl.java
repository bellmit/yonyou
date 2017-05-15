
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : SalesPartServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年8月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月4日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.stockmanage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartSalesItemPo;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartSalesPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.SystemParamConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesItemDto;

/**
* 销售出库  实现类
* @author jcsi
* @date 2016年8月4日
*/
@Service
@SuppressWarnings({"unchecked", "rawtypes" })
public class PartSalesServiceImpl implements PartSalesService {

    @Autowired
    private SystemParamService systemParamService;
    
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 查询
    * @author jcsi
    * @date 2016年8月4日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#search(java.util.Map)
     */
    @Override
    public PageInfoDto search(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT t.CUSTOMER_NAME,t.SALES_PART_ID,t.SALES_PART_NO,t.CUSTOMER_CODE,t.DEALER_CODE,t.ORDER_STATUS,t.ORDER_DATE,t.OUT_AMOUNT from tt_part_sales t where 1=1 ");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("salesPartNo"))){
            sb.append(" and SALES_PART_NO like ? ");
            queryParam.add("%"+param.get("salesPartNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customerCode"))){
            sb.append(" and CUSTOMER_CODE like ? ");
            queryParam.add("%"+param.get("customerCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderStatus"))){
            sb.append(" and ORDER_STATUS = ? ");
            queryParam.add(Integer.parseInt(param.get("orderStatus")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and ORDER_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }
        PageInfoDto dto=DAOUtil.pageQuery(sb.toString(), queryParam);
        return dto;
    }

    /**
     * 根据id删除
    * @author jcsi
    * @date 2016年8月5日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#deleteById(java.lang.Long)
     */
    @Override
    public void deleteById(Long id) throws ServiceBizException {
        PartSalesPo salesPartPo=PartSalesPo.findById(id);
        if(salesPartPo.getString("ORDER_STATUS").equals(DictCodeConstants.OUT_ORDER_ISSTATUS+"")){
            throw new ServiceBizException("该配件信息已出库,不能执行删除");      
        }
        //记录操作日志
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件销售单删除：销售单号【"+salesPartPo.getString("SALES_PART_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        
        salesPartPo.deleteCascadeShallow();
        
       
    }

    /**
     * 新增
    * @author jcsi
    * @date 2016年8月8日
    * @param salesPartNo
    * @param spDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#addSalesAndItem(java.lang.String, com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto)
     */
    @Override
    public PartSalesPo addSalesAndItem(String salesPartNo, PartSalesDto spDto) throws ServiceBizException {
        //根据仓库代码、配件代码和经销商代码查询配件成本价格
        calCostAmount(spDto.getSalesPartItemDtos());
        
        //给主表属性赋值
        PartSalesPo spPo=new PartSalesPo();
        Map<String, Object> resultAmount=calAmount(spDto.getSalesPartItemDtos());
        setSalesPart(spPo,spDto,resultAmount);
        spPo.setString("SALES_PART_NO", salesPartNo); //调拨出库单号 
        spPo.saveIt();
        for(PartSalesItemDto dto:spDto.getSalesPartItemDtos()){
            PartSalesItemPo spiPo=new PartSalesItemPo();
            setSalesPartItem(spiPo,dto);           
            spPo.add(spiPo);
        }
        //更新工单中的销售id
        if(!StringUtils.isNullOrEmpty(spDto.getRoId())){
        	RepairOrderPO repairOrderPo= RepairOrderPO.findById(spDto.getRoId());
            repairOrderPo.setLong("SALES_PART_ID", spPo.getLongId());
            repairOrderPo.saveIt();
            //更新工单中的金额
            updateOrderAmount(spPo.getLongId(),resultAmount);
        }
        
        return spPo;
    }
    
    /**
     * 更新工单金额
    * @author jcsi
    * @date 2016年8月22日
    * @param salesId   销售主单id、
    * @param resultAmount  金额计算
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#updateOrderAmount()
     */
    @Override
    public void updateOrderAmount(Long salesId,Map<String, Object> resultAmount) {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.RO_ID,t.SALES_PART_ID,t.DEALER_CODE,t.LABOUR_AMOUNT,t.REPAIR_PART_AMOUNT,t.SALES_PART_AMOUNT,t.ADD_ITEM_AMOUNT,t.AMOUNT,");
        sb.append("t.DIS_LABOUR_AMOUNT,t.DIS_REPAIR_PART_AMOUNT,t.DIS_SALES_PART_AMOUNT,t.DIS_ADD_ITEM_AMOUNT,t.DIS_AMOUNT  ");
        sb.append("from tt_repair_order t where t.SALES_PART_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(salesId);
        //Map map=DAOUtil.findFirst(sb.toString(), queryParam);
        List<Map> result=DAOUtil.findAll(sb.toString(), queryParam);
        if(result!=null){
            if(result.size()>0){
                for(int i=0;i<result.size();i++){
                   Map map=result.get(i);
                   //如果关联工单   则更新  否则不更新
                   setIsNUll(map);
                   //计算工单总金额、折后总金额
                   Double amount=Double.parseDouble(resultAmount.get("outAmount").toString())+Double.parseDouble(map.get("repair_part_amount").toString())+Double.parseDouble(map.get("labour_amount").toString())+Double.parseDouble(map.get("add_item_amount").toString());
                   Double disAmount=Double.parseDouble(resultAmount.get("salesAmount").toString())+Double.parseDouble(map.get("dis_labour_amount").toString())+Double.parseDouble(map.get("dis_repair_part_amount").toString())+Double.parseDouble(map.get("dis_add_item_amount").toString());
                   //更新工单  销售金额 折后金额。。。     
                   RepairOrderPO.update("SALES_PART_AMOUNT=?,AMOUNT=?,DIS_SALES_PART_AMOUNT=?,DIS_AMOUNT=?", "RO_ID=?",resultAmount.get("outAmount"),amount,resultAmount.get("salesAmount"),disAmount,map.get("ro_id"));
                }
            }
        }
        
       
    
    }
    /**
     * 为null的赋值为 0
     * @author jcsi
     * @date 2016年8月21日
     * @param repairOrderPO
      */
     private void setIsNUll(Map map){
         if(map.get("repair_part_amount")==null){
             map.put("repair_part_amount", 0);
         }
         if(map.get("labour_amount")==null){
             map.put("labour_amount", 0);
         }
         if(map.get("add_item_amount")==null){
             map.put("add_item_amount", 0);
         }        
         if(map.get("dis_labour_amount")==null){
             map.put("dis_labour_amount", 0);
         }
         if(map.get("dis_repair_part_amount")==null){
             map.put("dis_repair_part_amount", 0);
         }
         if(map.get("dis_add_item_amount")==null){
             map.put("dis_add_item_amount", 0);
         }
         
    }

    /**
     * 修改
    * @author jcsi
    * @date 2016年8月8日
    * @param salesId
    * @param spDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#editSalesAndItem(java.lang.Long, com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto)
     */
    @Override
    public void editSalesAndItem(Long salesId, PartSalesDto spDto) throws ServiceBizException {
       //根据仓库代码、配件代码和经销商代码查询配件成本价格
        calCostAmount(spDto.getSalesPartItemDtos());
        
        //给主表属性赋值
        PartSalesPo spPo=PartSalesPo.findById(salesId);
        Map<String, Object> resultAmount=calAmount(spDto.getSalesPartItemDtos());
        setSalesPart(spPo,spDto,resultAmount);
        spPo.saveIt();
        
        PartSalesItemPo.delete("SALES_PART_ID=?", salesId);
        for(PartSalesItemDto dto:spDto.getSalesPartItemDtos()){
            PartSalesItemPo spiPo=new PartSalesItemPo();
            setSalesPartItem(spiPo,dto);
            spPo.add(spiPo);
        }
        //更新工单中的销售id
        if(!StringUtils.isNullOrEmpty(spDto.getRoId())){
        	 RepairOrderPO repairOrderPo= RepairOrderPO.findById(spDto.getRoId());
             repairOrderPo.setLong("SALES_PART_ID", spPo.getLongId());
             repairOrderPo.saveIt();
             //更新工单中的金额
             updateOrderAmount(spPo.getLongId(),resultAmount);
             
        }
       
    }
    
    /**
    * 主表SalesPartPo属性赋值
    * @author jcsi
    * @date 2016年8月8日
    * @param spPo
    * @param spDto
     */
    public void setSalesPart(PartSalesPo spPo,PartSalesDto spDto,Map<String, Object> resultAmount){
        
        spPo.setString("SALES_PART_NO", spDto.getSalesPartNo());   //销售单号
        spPo.setString("RO_NO", spDto.getRoNo());   //工单号
        spPo.setString("CUSTOMER_CODE", spDto.getCustomerCode());   //客户代码 
        spPo.setString("CUSTOMER_TYPE",spDto.getCustomerType() );   //客户类型
        spPo.setString("CUSTOMER_NAME",spDto.getCustomerName());   //客户名称
        spPo.setDouble("OUT_AMOUNT", spDto.getOutAmount());   //出库金额
        spPo.setDouble("COST_AMOUNT", spDto.getCostAmount());   //成本金额
        spPo.setDate("ORDER_DATE", spDto.getOrderDate());   //开单日期
        spPo.setLong("ORDER_STATUS", spDto.getOrderStatus());   //单据状态
        spPo.setString("REMARK", spDto.getRemark());   //备注
        spPo.setLong("ORDER_STATUS",DictCodeConstants.OUT_ORDER_NOSTATUS );  //单据状态   （第一次保存，默认为 “未出库”）
        spPo.setDouble("COST_AMOUNT", resultAmount.get("costAmount"));  //成本金额
        spPo.setDouble("OUT_AMOUNT", resultAmount.get("outAmount"));    //出库金额
        spPo.setInteger("IS_BALANCE", DictCodeConstants.STATUS_IS_NOT);
        
    }
    
    /**
    * 子表属性赋值
    * @author jcsi
    * @date 2016年8月8日
    * @param spiPo
    * @param spiDto
     */
    public void setSalesPartItem(PartSalesItemPo spiPo,PartSalesItemDto spiDto){
        spiPo.setString("STORAGE_CODE",spiDto.getStorageCode());  //仓库代码
        spiPo.setString("STORAGE_POSITION_CODE",spiDto.getStoragePositionCode());  //库位
        spiPo.setString("PART_NO",spiDto.getPartNo());  //配件代码
        spiPo.setString("PART_NAME",spiDto.getPartName());  //配件名称
        spiPo.setDouble("PART_QUANTITY",spiDto.getPartQuantity());  //配件数量
        spiPo.setString("UNIT",spiDto.getUnit());  //计量单位
        spiPo.setDouble("DISCOUNT",spiDto.getDiscount());  //折扣率
        spiPo.setLong("PRICE_TYPE",spiDto.getPriceType());  //价格类型
        spiPo.setDouble("PRICE_RATE",spiDto.getPriceRate());  //价格系数
        spiPo.setDouble("PART_SALES_PRICE",spiDto.getPartSalesPrice());  //配件销售单价
        spiPo.setDouble("PART_COST_AMOUNT",spiDto.getPartCostAmount());  //配件成本金额
        spiPo.setDouble("PART_SALES_AMOUNT",spiDto.getPartSalesAmount());  //配件销售金额
        spiPo.setDouble("SALES_AMOUNT",spiDto.getPartSalesAmount()*spiDto.getDiscount());  //折后销售金额
        spiPo.setString("FINISHED_DATE",spiDto.getFinishedDate());  //入账日期
        spiPo.setDouble("COST_PRICE",spiDto.getPartCostPrice());  //成本单价
        spiPo.setString("IS_FINISHED",DictCodeConstants.STATUS_IS_NOT);  //入库状态
        spiPo.setDouble("PART_COST_AMOUNT", spiDto.getPartCostPrice()*spiDto.getPartQuantity());
    }
    
    /**
     * 根据仓库代码和配件代码查询成本单价
     * @author jcsi
     * @date 2016年8月5日
     * @param list
     * @throws ServiceBizException
      */
     public void calCostAmount(List<PartSalesItemDto> list)throws ServiceBizException{
         if(!CommonUtils.isNullOrEmpty(list)){
             for(PartSalesItemDto dto:list){                
                 String storageCode= dto.getStorageCode();  //仓库代码
                 String partNo=dto.getPartNo(); //配件代码
                 String strSql="SELECT t.COST_PRICE,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=? and DEALER_CODE= ? ";
                 List<Object> queryParam=new ArrayList<Object>();
                 queryParam.add(storageCode);
                 queryParam.add(partNo);
                 queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
                 Map stockMap=DAOUtil.findFirst(strSql, queryParam);
                 dto.setPartCostPrice(Double.valueOf(stockMap.get("cost_price").toString()));
             }
             
         }   
         
     }
     
     /**
     * 计算成本金额和出库金额
     * @author jcsi
     * @date 2016年8月3日
     * @param list
     * @return
      */
     public Map<String,Object> calAmount(List<PartSalesItemDto> list) throws ServiceBizException {
         Double outAmount=0.00;  //出库金额
         Double costAmount=0.00;  //成本金额
         Double salesAmount=0.00;  //折后金额
         Double costAmountItem=0.00;  //成本金额（明细）
         //累加计算成本金额和出库金额
         if(!CommonUtils.isNullOrEmpty(list)){
             for(PartSalesItemDto oiDto:list){
                 costAmountItem=oiDto.getPartCostPrice()*oiDto.getPartQuantity(); 
                 outAmount+=oiDto.getPartSalesAmount()*oiDto.getDiscount();
                 costAmount+=costAmountItem;
                 salesAmount=salesAmount+oiDto.getPartSalesAmount()*oiDto.getDiscount();
             }
         }
         Map<String,Object> result=new HashMap<String,Object>();
         result.put("outAmount", outAmount);
         result.put("costAmount", costAmount);
         result.put("salesAmount", salesAmount);
         return result;
     }

    /**
     *  根据id查询
    * @author jcsi
    * @date 2016年8月9日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#findSalesById(java.lang.Long)
     */
    @Override
    public Map findSalesById(Long id) throws ServiceBizException {
        return PartSalesPo.findById(id).toMap();
    }

    
    /**
     * 根据主表id查询子表信息
    * @author jcsi
    * @date 2016年8月9日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#findSalesItemByPartId(java.lang.Long)
     */
    @Override
    public List<Map> findSalesItemByPartId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.ITEM_ID,t.DEALER_CODE,t.IS_FINISHED isFinishedShow,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.STORAGE_CODE storageCodeShow,t.STORAGE_POSITION_CODE storagePositionCodeShow,t.UNIT unitShow,t.PART_QUANTITY canNumShow,t.PART_SALES_PRICE salesPriceShow,t.PART_SALES_AMOUNT salesAmountShow,t.DISCOUNT disCountShow,t.SALES_AMOUNT disAmountShow,t.PART_SALES_AMOUNT*(1-t.DISCOUNT) as disCountAmount from tt_part_sales_item t where t.SALES_PART_ID=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(id);
        return DAOUtil.findAll(sb.toString(), param);
    }

    /**
     * 入账
    * @author jcsi
    * @date 2016年8月9日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#updateStatusById(java.lang.Long)
     */
    @Override
    public void updateStatusById(Long id) throws ServiceBizException {
        PartSalesPo paoPo=PartSalesPo.findById(id);
        //更新配件库存数据和明细数据
        updateStockAndItem(id,paoPo.getString("sales_part_no"),paoPo.getLong("customer_type"),paoPo.getString("customer_code"));
        //更新所有明细为“入账”状态
        //PartSalesItemPo.update("IS_FINISHED=?", "SALES_PART_ID=?",DictCodeConstants.STATUS_IS_YES,id);
        //更新主单状态为“已出库”
        paoPo.setString("ORDER_STATUS", DictCodeConstants.OUT_ORDER_ISSTATUS);  //已出库
        paoPo.saveIt();
    }
    
    private void updateStockAndItem(Long id,String salesPartNo,Long cusType,String cusCode)throws ServiceBizException{
      //根据配件出库主表id查询明细信息
        List<Object> param=new ArrayList<Object>();
        String str="select t.PART_NAME,t.PART_SALES_AMOUNT,t.PART_SALES_PRICE,t.PART_QUANTITY,t.DEALER_CODE,t.STORAGE_CODE,t.PART_NO from tt_part_sales_item t  where t.SALES_PART_ID=?";
        param.add(id);
        List<Map> result=DAOUtil.findAll(str, param);
        for(int i=0;i<result.size();i++){
            Map map=result.get(i);
            String storageCode=(String) map.get("storage_code");  //仓库代码
            String partNo=(String)map.get("part_no");  //配件代码
            Double outQuantity= Double.valueOf(map.get("part_quantity").toString());   //出库数量
            Double partSalesPrice= Double.valueOf(map.get("part_sales_price").toString()); //销售单价
            //根据仓库代码+配件代码   查询配件库存信息（账面库存数量，配件成本单价...）
            Map stockMap=searchPartStore(storageCode,partNo);
            
            Double stockQuantity=Double.valueOf(stockMap.get("stock_quantity").toString());  //账面库存数量
            Double costPrice=Double.valueOf(stockMap.get("cost_price").toString()) ;  //成本单价
            Double limitPrice=Double.valueOf(stockMap.get("limit_price").toString()) ;  //销售限价
            
            //出库数量>库存数量   判断出库仓库是否允许负库存
            if(outQuantity>stockQuantity){
                String storeStr="SELECT DEALER_CODE,IS_NEGATIVE from TM_STORE  where DEALER_CODE=? and STORAGE_CODE=?";
                List<Object> queryParamStore=new ArrayList<Object>();
                queryParamStore.add(FrameworkUtil.getLoginInfo().getDealerCode());
                queryParamStore.add(storageCode);
                Map mapStore=DAOUtil.findFirst(storeStr, queryParamStore);
                String Storeresult=mapStore.get("is_negative")+"";
                if(Integer.toString(DictCodeConstants.STATUS_IS_NOT).equals(Storeresult)){
                    throw new ServiceBizException(partNo+"配件出库数量大于库存数量，保存失败");
                }
            }
           // 当销售单价高于销售限价时 抛出异常  
            if(partSalesPrice>limitPrice){
                throw new ServiceBizException(partNo+"配件销售单价高于库存销售限价，保存失败");
            }
            
            //重新计算库存数量  和 成本金额
            Double canStockQuantity=stockQuantity-outQuantity; //库存数量
            Double canCostAmount=canStockQuantity*costPrice;  //成本金额
            //更新库存账面数量，配件成本金额
            TmPartStockPO.update("LAST_STOCK_OUT=?,COST_AMOUNT=?,STOCK_QUANTITY=?","STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=? ",new Date(),canCostAmount,canStockQuantity,storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
            //更新明细表中成本单价和成本金额 
            PartSalesItemPo.update("COST_PRICE=?,PART_COST_AMOUNT=?,IS_FINISHED=?,FINISHED_DATE=?", "STORAGE_CODE=? and PART_NO=?  and DEALER_CODE=? ", costPrice,costPrice*outQuantity,DictCodeConstants.STATUS_IS_YES,new Date(),storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
            //插入配件流水
            map.put("stock_quantity", canStockQuantity);  //账面库存
            map.put("cost_price",costPrice);
            map.put("cost_Amount",costPrice*canStockQuantity);
            map.put("sales_part_no",salesPartNo);
            map.put("customer_type", cusType);
            map.put("customer_code", cusCode);
            PartFlowPO pf=setPartFlowPO(map);
            pf.saveIt();
        }
    }
    
    
    /**
    * 注入配件流水账属性
    * @author jcsi
    * @date 2016年8月10日
     */
    public PartFlowPO setPartFlowPO(Map<String,Object> map){
        PartFlowPO pf=new PartFlowPO();
        pf.setInteger("IN_OUT_TYPE", DictCodeConstants.SALES_OUT_TYPE);  //出入库类型
        pf.setTimestamp("OPERATE_DATE", new Date());   //发生时间
        pf.setDouble("STOCK_OUT_QUANTITY", Double.parseDouble(map.get("part_quantity")+""));  //出数量
        pf.setDouble("IN_OUT_TAXED_PRICE",Double.parseDouble(map.get("part_sales_price")+""));  //含税单价
        pf.setDouble("IN_OUT_TAXED_AMOUNT",Double.parseDouble(map.get("part_sales_amount")+""));  //含税金额
        
        Double salesPrice=Double.parseDouble(map.get("part_sales_price")+"");  
        Double salesAmount=Double.parseDouble(map.get("part_sales_amount")+"");
        BasicParametersDTO bpDto=systemParamService.queryBasicParameterByTypeandCode(SystemParamConstants.PARAM_TYPE_BASICOM, SystemParamConstants.BASICOM_CODE);
        pf.setDouble("IN_OUT_NET_PRICE",salesPrice/(1+new Double(bpDto.getParamValue())));  //不含税单价（含税单价/(1+缺省税率)）
        pf.setDouble("IN_OUT_NET_AMOUNT",salesAmount/(1+new Double(bpDto.getParamValue())));  //不含税金额
        
        pf.setDouble("STOCK_QUANTITY",Double.parseDouble(map.get("stock_quantity")+""));  //账面库存
        pf.setDouble("COST_PRICE",Double.parseDouble(map.get("cost_price")+""));  //成本单价
        pf.setDouble("COST_AMOUNT",Double.parseDouble(map.get("cost_Amount")+""));  //成本金额
        pf.setString("SHEET_NO",map.get("sales_part_no")); //单据号码
        pf.setString("STORAGE_CODE",map.get("storage_code")+"" ); //仓库代码
        pf.setString("PART_NO",map.get("part_no")+"" ); //配件代码
        pf.setString("PART_NAME",map.get("part_name")+"" ); //配件名称
        pf.setString("OPERATOR",FrameworkUtil.getLoginInfo().getEmployeeNo()); //操作员
        pf.setLong("CUSTOMER_TYPE", map.get("customer_type"));
        pf.setString("CUSTOMER_CODE", map.get("customer_code"));
        return pf;
    }
    
   

    /**
     * 退货明细查询
    * @author jcsi
    * @date 2016年8月12日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#searchSalesReturn(java.lang.Long)
     */
    @Override
    public PageInfoDto searchSalesReturn(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.COST_PRICE,t.ITEM_ID,t.SALES_PART_ID,t.DEALER_CODE,t.PART_NO,t.PART_NAME,t.STORAGE_CODE,t.STORAGE_POSITION_CODE,t.PART_SALES_PRICE,");
        sb.append("sum(t.PART_SALES_AMOUNT) as PART_SALES_AMOUNT,");
        sb.append("t.DISCOUNT,sum(t.SALES_AMOUNT)as SALES_AMOUNT,");
        sb.append("(SELECT sum(PART_QUANTITY) FROM tt_part_sales_item ti WHERE ti.PART_QUANTITY > 0 AND ti.ITEM_ID=t.ITEM_ID and ti.PART_NO=t.PART_NO and ti.STORAGE_CODE=ti.STORAGE_CODE and ti.PART_SALES_PRICE=t.PART_SALES_PRICE ");
        sb.append(" GROUP BY ti.PART_NO,ti.STORAGE_CODE,ti.PART_SALES_PRICE)as OUT_QUANTITY,sum(PART_QUANTITY) MAY_QUANTITY ");
        sb.append(" FROM tt_part_sales_item t where t.SALES_PART_ID=? GROUP BY t.PART_NO,t.STORAGE_CODE,t.PART_SALES_PRICE");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    /**
     * 退货
    * @author jcsi
    * @date 2016年8月12日
    * @param dto
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#salesReturnSub(com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto)
     */
    @Override
    public void salesReturnSub(Long id,PartSalesDto spDto) {
        //主单属性
        Double outAmount=0.00;  //出库金额
        Double costPrice=0.00;  //成本金额
        Double salesAmount=0.00;  //折后金额 
        //更新主单销售金额、成本金额
        PartSalesPo psPo=PartSalesPo.findById(id);
        for(PartSalesItemDto dto:spDto.getSalesPartItemDtos()){
            if(!StringUtils.isNullOrEmpty(dto.getPartQuantity())){
            	int i=NumberUtil.compareTo(dto.getPartQuantity(),dto.getReturnQuantity());
            	if(i==1){
            		 throw new ServiceBizException("本次退货数量不能大于可退货数量");
            	}
                PartSalesItemPo spiPo=new PartSalesItemPo();
                Double partQuantity=Conversion(dto.getPartQuantity()+"");  //转换成负数
                dto.setPartQuantity(partQuantity);            
                setSalesPartItem(spiPo,dto);
                spiPo.setDouble("PART_COST_AMOUNT", dto.getPartCostPrice()*dto.getPartQuantity());  //成本金额
                spiPo.setDouble("PART_SALES_AMOUNT", dto.getPartSalesPrice()*dto.getPartQuantity());  //销售金额
                spiPo.setDouble("SALES_AMOUNT", (dto.getPartSalesPrice()*dto.getPartQuantity())*dto.getDiscount()); //折后金额
                spiPo.setLong("SALES_PART_ID", id);
                //保存明细数据
                spiPo.saveIt();
                //更新账面库存 +成本金额
                Map stockMap=searchPartStore(dto.getStorageCode(),dto.getPartNo());
                Double quantity=Double.valueOf(stockMap.get("stock_quantity").toString());  //账面库存数量
                Double price=Double.valueOf(stockMap.get("cost_price").toString());  //成本单价
                //更新库存账面数量，配件成本金额(新账面库存=账面库存+退货数量)
                TmPartStockPO.update("COST_AMOUNT=?,STOCK_QUANTITY=?","STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=? ",price*(quantity-partQuantity),quantity-partQuantity,dto.getStorageCode(),dto.getPartNo(),FrameworkUtil.getLoginInfo().getDealerCode());
                //保存流水账
                PartFlowPO po=setPartFlowPOByDto(dto,quantity-partQuantity,psPo.getString("sales_part_no"));
                po.saveIt();
                outAmount+=dto.getPartSalesPrice()*dto.getPartQuantity();
                costPrice+=dto.getPartCostPrice()*dto.getPartQuantity();
                salesAmount+=(dto.getPartSalesPrice()*dto.getPartQuantity())*dto.getDiscount();
            }
            
        }
        Double getOutAmount=psPo.getDouble("out_amount");
        psPo.setDouble("OUT_AMOUNT",getOutAmount+outAmount);
        psPo.setDouble("COST_AMOUNT", costPrice);
        psPo.saveIt();
        //更新工单表中金额
        Map<String,Object> calAmount=new HashMap<String,Object>(); 
        calAmount.put("outAmount", outAmount);
        calAmount.put("salesAmount", salesAmount);
        updateOrderAmount(psPo.getLongId(),calAmount);
    }
    /**
    * 转换负数
    * @author jcsi
    * @date 2016年8月12日
    * @param param
    * @return
     */
    public Double Conversion(String param){
       param="-"+param;
       Double num=Double.parseDouble(param);
       return num;
    }
    /**
    * 属性赋值
    * @author jcsi
    * @date 2016年8月12日
    * @param dto
    * @param stockQuantity
    * @param salesPartNo
    * @return
     */
    public PartFlowPO setPartFlowPOByDto(PartSalesItemDto dto,Double stockQuantity,String salesPartNo){
        PartFlowPO pf=new PartFlowPO();
        pf.setString("IN_OUT_TYPE", DictCodeConstants.SALES_OUT_TYPE);  //出入库类型
        pf.setTimestamp("OPERATE_DATE", new Date());   //发生时间
        pf.setDouble("STOCK_OUT_QUANTITY", dto.getPartQuantity());  //出数量
        pf.setDouble("IN_OUT_TAXED_PRICE",dto.getPartSalesPrice());  //含税单价
        pf.setDouble("IN_OUT_TAXED_AMOUNT",dto.getPartSalesPrice()* dto.getPartQuantity());  //含税金额
        
        Double salesPrice=dto.getPartSalesPrice();  
        Double salesAmount=dto.getPartSalesAmount();
        pf.setDouble("IN_OUT_NET_PRICE",salesPrice/(1+0.17));  //不含税单价（含税单价/(1+缺省税率)）
        pf.setDouble("IN_OUT_NET_AMOUNT",salesAmount/(1+0.17));  //不含税金额
        
        pf.setDouble("STOCK_QUANTITY",stockQuantity);  //账面库存
        pf.setDouble("COST_PRICE",dto.getPartCostPrice());  //成本单价
        pf.setDouble("COST_AMOUNT",dto.getPartCostPrice()*dto.getPartQuantity());  //成本金额
        pf.setString("SHEET_NO",salesPartNo); //单据号码
        pf.setString("STORAGE_CODE",dto.getStorageCode()); //仓库代码
        pf.setString("PART_NO",dto.getPartNo()); //配件代码
        pf.setString("PART_NAME",dto.getPartName()); //配件名称
        pf.setString("OPERATOR",""); //操作员
        return pf;
    }
    /**
    * 查询配件库存信息
    * @author jcsi
    * @date 2016年8月12日
    * @param storageCode
    * @param partNo
    * @return
     */
    public Map searchPartStore(String storageCode,String partNo){
        String strSql="SELECT t.LIMIT_PRICE,t.STOCK_QUANTITY,t.COST_PRICE,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=?";
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(storageCode);
        queryParam.add(partNo);
        queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
        Map stockMap=DAOUtil.findFirst(strSql, queryParam);
        return stockMap;
    }

    /**
     * 根据工单号查询销售明细
    * @author jcsi
    * @date 2016年8月24日
    * @param roNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#searchItemByRoNo(java.lang.Long)
     */
    @Override
    public PageInfoDto searchItemByRoNo(Long  roId) throws ServiceBizException {
        String sb=uniteSql();
        List<Object> param=new ArrayList<Object>();
        param.add(roId);
        return DAOUtil.pageQuery(sb.toString(), param);
    }
    
    public String uniteSql(){
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT item.ITEM_ID,item.SALES_PART_ID,item.DEALER_CODE,item.STORAGE_CODE,");
        sb.append("item.STORAGE_POSITION_CODE,item.PART_NO,item.PART_NAME,item.PART_QUANTITY,");
        sb.append("item.UNIT,item.DISCOUNT,item.PART_SALES_PRICE,item.PART_SALES_AMOUNT,item.SALES_AMOUNT,");
        sb.append("item.IS_FINISHED,(item.PART_SALES_AMOUNT - item.SALES_AMOUNT) AS PREFERENTIAL  ");
        sb.append("FROM TT_PART_SALES_ITEM item    ");
        sb.append("INNER JOIN TT_PART_SALES t ON item.SALES_PART_ID = t.SALES_PART_ID ");
        sb.append("inner join TT_REPAIR_ORDER r on r.RO_NO=t.RO_NO and r.DEALER_CODE=t.DEALER_CODE ");
        sb.append("  where r.RO_ID=?");
        return sb.toString();
    }

    /**
     * 配件销售单查询(费用结算)
    * @author jcsi
    * @date 2016年10月13日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#findPartSales()
     */
    @Override
    public PageInfoDto findPartSales(Map<String, String> param) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();
        return DAOUtil.pageQuery(getSql(param,queryParam), queryParam);
    }

    
    /**
    * 打印查询
    * @author ZhengHe
    * @date 2016年11月3日
    * @param roId
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#searchSalesItemByRoId(java.lang.Long)
    */
    	
    @Override
    public List<Map> searchSalesItemByRoId(Long roId) throws ServiceBizException {
        String sb=uniteSql();
        List<Object> param=new ArrayList<Object>();
        param.add(roId);
        return DAOUtil.findAll(sb, param);
    }

    
    /**
     * 打印销售出库单打印
    * @author DuPengXin
    * @date 2016年12月8日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#getPartSalesPrint(java.lang.Long)
    */
    	
    @Override
	public List<Map> getPartSalesPrint(Long id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder("select tpsi.ITEM_ID,tpsi.DEALER_CODE,tps.SALES_PART_ID,tps.SALES_PART_NO,tps.RO_NO,tps.CUSTOMER_NAME,tps.ORDER_DATE,ts.STORAGE_CODE,ts.STORAGE_NAME,tpsi.PART_NO,tpsi.PART_NAME,tpsi.UNIT,tpsi.PART_SALES_AMOUNT,tpsi.DISCOUNT,tpsi.PART_QUANTITY,tpsi.PART_SALES_PRICE,(tpsi.PART_SALES_AMOUNT - tpsi.SALES_AMOUNT) AS PREFERENTIAL,tpsi.SALES_AMOUNT from tt_part_sales_item tpsi LEFT JOIN tt_part_sales tps ON tpsi.SALES_PART_ID=tps.SALES_PART_ID and tpsi.DEALER_CODE=tps.DEALER_CODE LEFT JOIN tm_store ts ON tpsi.STORAGE_CODE=ts.STORAGE_CODE and ts.DEALER_CODE=tpsi.DEALER_CODE where tps.SALES_PART_ID=?");
        List<Object> param = new ArrayList<Object>();
		param.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(), param);
		return list;
	}
    /**
     * 根据销售单号查询
    * @author jcsi
    * @date 2016年11月16日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartSalesService#findBySalesNo(java.util.Map)
     */
    @Override
    public List<Map> findBySalesNo(Map<String, String> param) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();
        return DAOUtil.findAll(getSql(param,queryParam), queryParam);
    }
    
    /**
    * 结算查询sql
    * @author jcsi
    * @date 2016年11月16日
    * @param param
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    private String getSql(Map<String, String> param,List<Object> queryParam)throws ServiceBizException{
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT  s.SALES_PART_NO,s.SALES_PART_ID,s.DEALER_CODE,s.CUSTOMER_CODE,s.CUSTOMER_NAME,");
        sb.append("s.OUT_AMOUNT,s.REMARK,v.VIN,s.ORDER_DATE,s.CUSTOMER_TYPE  ");
        sb.append("from TT_PART_SALES s  ");
        sb.append("left join TT_REPAIR_ORDER r on s.RO_NO=r.RO_NO  and s.DEALER_CODE=r.DEALER_CODE  ");
        sb.append("left join tm_vehicle v on v.VEHICLE_ID=r.VEHICLE_ID  ");
        sb.append("where s.ORDER_STATUS=?  and s.IS_BALANCE= ? and s.RO_NO is null ");
        queryParam.add(DictCodeConstants.OUT_ORDER_ISSTATUS);
        queryParam.add(DictCodeConstants.STATUS_IS_NOT);
        if(!StringUtils.isNullOrEmpty(param.get("salesPartNo"))){
            sb.append(" and SALES_PART_NO like ? ");
            queryParam.add("%"+param.get("salesPartNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customerName"))){
            sb.append(" and CUSTOMER_NAME like ? ");
            queryParam.add("%"+param.get("customerName")+"%");
        }
        
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and ORDER_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }
        return sb.toString();
    }
}
