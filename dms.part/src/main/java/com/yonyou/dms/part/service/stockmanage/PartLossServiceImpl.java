
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartLossServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年8月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月13日    jcsi    1.0
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
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartLossDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartLossItemDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartLossItemPo;
import com.yonyou.dms.part.domains.PO.stockmanage.PartLossPo;

/**
* 配件报损 （实现类）
* @author jcsi
* @date 2016年8月13日
*/

@Service
@SuppressWarnings({"unchecked", "rawtypes" })
public class PartLossServiceImpl implements PartLossService {

    @Autowired
    private OperateLogService operateLogService;
    /**
     * 根据条件查询配件报损信息
    * @author jcsi
    * @date 2016年8月13日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#searchPartLoss(java.util.Map)
     */
    @Override
    public PageInfoDto searchPartLoss(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.PART_LOSS_ID,t.DEALER_CODE,t.LOSS_NO,t.ORDER_DATE,t.ORDER_STATUS,t.OUT_AMOUNT,tm.EMPLOYEE_NAME from tt_part_loss t left join tm_employee tm on t.HANDLER=tm.EMPLOYEE_NO and t.DEALER_CODE=tm.DEALER_CODE where 1=1");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("lossNo"))){
            sb.append(" and t.LOSS_NO like ? ");
            queryParam.add("%"+param.get("lossNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and ORDER_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    
    }

    /**
     * 根据id删除配件报损及明细信息
    * @author jcsi
    * @date 2016年8月13日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#deleteById(java.lang.Long)
     */
    @Override
    public void deleteById(Long id) throws ServiceBizException {
        PartLossPo plPo=PartLossPo.findById(id); 
        if(Integer.toString(DictCodeConstants.OUT_ORDER_ISSTATUS).equals(plPo.get("order_status").toString())){
            throw new ServiceBizException("该条信息已出库,不能执行删除");
        }
        //记录操作日志
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件报损单删除：报损单号【"+plPo.getString("LOSS_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        
        plPo.deleteCascadeShallow();
        
       
    }

    /**
     * 保存
    * @author jcsi
    * @date 2016年8月13日
    * @param partLossDto
    * @param LossNo
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#addPartLoss(com.yonyou.dms.part.domains.DTO.stockmanage.PartLossDto, java.lang.String)
     */
    @Override
    public PartLossPo addPartLoss(PartLossDto partLossDto, String LossNo) throws ServiceBizException {
        //根据配件代码、仓库代码+declCode查询配件成本价
        searchCostPrice(partLossDto.getPartLossItemList());
        //给主表属性赋值
        PartLossPo lossPo=new PartLossPo();
        setPartLossPo(lossPo,partLossDto);
        lossPo.setString("LOSS_NO", LossNo);
        lossPo.saveIt();
        for(PartLossItemDto itemDot:partLossDto.getPartLossItemList()){
            PartLossItemPo itemPo=setPartLossItem(itemDot);
            lossPo.add(itemPo);
        }
        return lossPo;
        
    }

    /**
    * PartLossPo属性赋值
    * @author jcsi
    * @date 2016年8月13日
    * @param partLossDto
    * @return
     */
    public PartLossPo setPartLossPo(PartLossPo plPo,PartLossDto partLossDto){
        Map<String, Object> resultAmount=calAmount(partLossDto.getPartLossItemList());
        plPo.setString("INVENTORY_NO",partLossDto.getInventoryNo());  //盘点单号
        plPo.setDouble("COST_AMOUNT",resultAmount.get("costAmount"));  //成本金额 
        plPo.setDouble("OUT_AMOUNT",resultAmount.get("outAmount"));   //出库金额
        plPo.setString("HANDLER",partLossDto.getHandler() );  //经手人
        plPo.setDate("ORDER_DATE",partLossDto.getOrderDate() ); //开单日期
        plPo.setString("ORDER_STATUS",DictCodeConstants.OUT_ORDER_NOSTATUS); //单据状态(保存默认为‘未出库’)
        return plPo;        
    }
    
    /**
    * PartLossItemPo属性赋值
    * @author jcsi
    * @date 2016年8月13日
    * @param itemDto
    * @return
    * @throws ServiceBizException
     */
    public PartLossItemPo setPartLossItem(PartLossItemDto itemDto)throws ServiceBizException{
        PartLossItemPo itemPo=new PartLossItemPo();
        itemPo.setString("STORAGE_CODE", itemDto.getStorageCode());  //仓库代码
        itemPo.setString("STORAGE_POSITION_CODE", itemDto.getStoragePositionCode());  //库位
        itemPo.setString("PART_NO", itemDto.getPartNo());  //配件代码
        itemPo.setString("PART_NAME", itemDto.getPartName());  //配件名称
        itemPo.setString("UNIT", itemDto.getUnit());  //计量单位
        itemPo.setDouble("LOSS_QUANTITY", itemDto.getLossQuantity()); //报损数量
        itemPo.setDouble("COST_PRICE", itemDto.getCostPrice());  //成本单价
        itemPo.setDouble("COST_AMOUNT", itemDto.getCostPrice()*itemDto.getLossQuantity()); //成本金额
        itemPo.setDouble("LOSS_PRICE", itemDto.getLossPrice());  //报损单价
        itemPo.setDouble("LOSS_AMOUNT", itemDto.getLossAmount());//盘亏金额
        itemPo.setLong("IS_FINISHED", DictCodeConstants.STATUS_IS_NOT);  //是否入账 (默认为否)
        return itemPo;
    }
    /**
     * 根据仓库代码和配件代码查询成本单价
     * @author jcsi
     * @date 2016年8月13日
     * @param list
     * @throws ServiceBizException
      */
     public void searchCostPrice(List<PartLossItemDto> list)throws ServiceBizException{
         if(!CommonUtils.isNullOrEmpty(list)){
             for(PartLossItemDto dto:list){                
                 String storageCode= dto.getStorageCode();  //仓库代码
                 String partNo=dto.getPartNo(); //配件代码
                 String strSql="SELECT t.COST_PRICE,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=? and DEALER_CODE= ? ";
                 List<Object> queryParam=new ArrayList<Object>();
                 queryParam.add(storageCode);
                 queryParam.add(partNo);
                 queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
                 Map stockMap=DAOUtil.findFirst(strSql, queryParam);
                 dto.setCostPrice(Double.valueOf(stockMap.get("cost_price").toString()));
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
      public Map<String,Object> calAmount(List<PartLossItemDto> list) throws ServiceBizException {
          Double outAmount=0.00;  //出库金额
          Double costAmount=0.00;  //成本金额
          Double costAmountItem=0.00;  //成本金额（明细）
          //累加计算成本金额和出库金额
          if(!CommonUtils.isNullOrEmpty(list)){
              for(PartLossItemDto oiDto:list){
                  costAmountItem=oiDto.getCostPrice()*oiDto.getLossQuantity(); 
                  outAmount+=oiDto.getLossAmount();
                  costAmount+=costAmountItem;
              }
          }
          Map<String,Object> result=new HashMap<String,Object>();
          result.put("outAmount", outAmount);
          result.put("costAmount", costAmount);
          return result;
      }

    /**
    * 修改  
    * @author jcsi
    * @date 2016年8月13日
    * @param id
    * @param partLossDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#editPartLoss(java.lang.Long, com.yonyou.dms.part.domains.DTO.stockmanage.PartLossDto)
     */
    @Override
    public void editPartLoss(Long id, PartLossDto partLossDto) throws ServiceBizException {
        //根据配件代码、仓库代码+declCode查询配件成本价
        searchCostPrice(partLossDto.getPartLossItemList());
        //给主表属性赋值
        PartLossPo lossPo=PartLossPo.findById(id);
        setPartLossPo(lossPo,partLossDto);
        lossPo.saveIt();
        
        PartLossItemPo.delete("PART_LOSS_ID=?", id);
        for(PartLossItemDto itemDot:partLossDto.getPartLossItemList()){
            PartLossItemPo itemPo=setPartLossItem(itemDot);
            lossPo.add(itemPo);
        }
        
    }

    /**
     * 根据id查询主表信息
    * @author jcsi
    * @date 2016年8月13日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#findLossById(java.lang.Long)
     */
    @Override
    public Map findLossById(Long id) throws ServiceBizException {
        return PartLossPo.findById(id).toMap();
    }

    /**
     * 根据主表id查询字表信息
    * @author jcsi
    * @date 2016年8月13日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#findLossItemById()
     */
    @Override
    public PageInfoDto findLossItemById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT t.ITEM_ID,t.PART_LOSS_ID,t.DEALER_CODE,t.IS_FINISHED isFinishedShow,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.STORAGE_CODE storageCodeShow,t.STORAGE_POSITION_CODE storagePositionCodeShow,t.UNIT unitShow,t.LOSS_QUANTITY lossQuantityShow,t.COST_PRICE costPriceShow,t.COST_AMOUNT costAmountShow,t.LOSS_PRICE lossPriceShow,t.LOSS_AMOUNT lossAmountShow from tt_part_loss_item t where PART_LOSS_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);        
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    /**
     * 入账
    * @author Administrator
    * @date 2016年8月14日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartLossService#updateOrderStatus(java.lang.Long)
     */
    @Override
    public void updateOrderStatus(Long id) throws ServiceBizException {
        PartLossPo lossPo=PartLossPo.findById(id);
        //是否关联了盘点数据
        if(!StringUtils.isNullOrEmpty(lossPo.getString("INVENTORY_NO"))){
            if(lossPo.getString("INVENTORY_NO").equals(DictCodeConstants.STATUS_IS_YES)){
                throw  new ServiceBizException(lossPo.getString("INVENTORY_NO")+"盘点单号不能重复报损");
            }else{
            	TtPartInventoryPO.update("LOSS_TAG=?","INVENTORY_NO=? and DEALER_CODE=?",DictCodeConstants.STATUS_IS_YES,lossPo.getString("INVENTORY_NO"),FrameworkUtil.getLoginInfo().getDealerCode());
            }
            
        }
        
        //更新子表、库存  信息    插入配件流水账
        updateStockAndItem(id,lossPo.getString("loss_no"));
        //更新主单信息
        lossPo.set("ORDER_STATUS",DictCodeConstants.OUT_ORDER_ISSTATUS);
        
        lossPo.saveIt();
    }
    
    private void updateStockAndItem(Long id,String lossNo)throws ServiceBizException{
        //根据配件出库主表id查询明细信息
          List<Object> param=new ArrayList<Object>();
          String str="SELECT t.DEALER_CODE,t.STORAGE_CODE,t.PART_NO,t.LOSS_QUANTITY,t.LOSS_PRICE,t.LOSS_AMOUNT,t.PART_NAME from TT_PART_LOSS_ITEM t  where t.PART_LOSS_ID=?";
          param.add(id);
          List<Map> result=DAOUtil.findAll(str, param);
          for(int i=0;i<result.size();i++){
              Map map=result.get(i);
              String storageCode=(String) map.get("storage_code");  //仓库代码
              String partNo=(String)map.get("part_no");  //配件代码
              Double outQuantity= Double.valueOf(map.get("loss_quantity").toString());   //出库数量
              //根据仓库代码+配件代码   查询配件库存信息（账面库存数量，配件成本单价...）
              Map stockMap=searchPartStore(storageCode,partNo);
              
              Double stockQuantity=Double.valueOf(stockMap.get("stock_quantity").toString());  //账面库存数量
              Double costPrice=Double.valueOf(stockMap.get("cost_price").toString()) ;  //成本单价
              
              //出库数量>库存数量   判断出库仓库是否允许负库存
              if(outQuantity>stockQuantity){
                  String storeStr="SELECT DEALER_CODE,IS_NEGATIVE from TM_STORE  where DEALER_CODE=? and STORAGE_CODE=?";
                  List<Object> queryParamStore=new ArrayList<Object>();
                  queryParamStore.add(FrameworkUtil.getLoginInfo().getDealerCode());
                  queryParamStore.add(storageCode);
                  Map mapStore=DAOUtil.findFirst(storeStr, queryParamStore);
                  String Storeresult=mapStore.get("is_negative")+"";
                  if(Integer.toString(DictCodeConstants.STATUS_IS_NOT).equals(Storeresult)){
                      throw new ServiceBizException(partNo+"配件报损数量不能大于配件库存数量 ");
                  }
              }
              
              //重新计算库存数量  和 成本金额
              Double canStockQuantity=stockQuantity-outQuantity; //新库存数量
              Double canCostAmount=canStockQuantity*costPrice;  //新成本金额
              //更新库存账面数量，配件成本金额
              TmPartStockPO.update("LAST_STOCK_OUT=?,COST_AMOUNT=?,STOCK_QUANTITY=?","STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=? ",new Date(),canCostAmount,canStockQuantity,storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
              //更新明细表中成本单价和成本金额 、入账状态
              PartLossItemPo.update("COST_PRICE=?,COST_AMOUNT=?,IS_FINISHED=?,FINISHED_DATE=? ", "STORAGE_CODE=? and PART_NO=?  and DEALER_CODE=? ", costPrice,costPrice*outQuantity,DictCodeConstants.STATUS_IS_YES,new Date(),storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
              //插入配件流水
              map.put("stock_quantity", canStockQuantity);  //账面库存
              map.put("cost_price",costPrice);
              map.put("cost_Amount",costPrice*canStockQuantity);
              map.put("lossNo",lossNo);
              PartFlowPO pf=setPartFlowPO(map);
              pf.saveIt();
          }
      }
    /**
     * 注入配件流水账属性
     * @author jcsi
     * @date 2016年8月14日
      */
     public PartFlowPO setPartFlowPO(Map<String,Object> map){
         PartFlowPO pf=new PartFlowPO();
         pf.setString("IN_OUT_TYPE", DictCodeConstants.PART_LOSS_TYPE);  //出入库类型
         pf.setTimestamp("OPERATE_DATE", new Date());   //发生时间
         pf.setDouble("STOCK_OUT_QUANTITY", Double.parseDouble(map.get("loss_quantity")+""));  //出数量
         pf.setDouble("IN_OUT_TAXED_PRICE",Double.parseDouble(map.get("loss_price")+""));  //含税单价
         pf.setDouble("IN_OUT_TAXED_AMOUNT",Double.parseDouble(map.get("loss_amount")+""));  //含税金额
         
         Double salesPrice=Double.parseDouble(map.get("loss_price")+"");  
         Double salesAmount=Double.parseDouble(map.get("loss_amount")+"");
         pf.setDouble("IN_OUT_NET_PRICE",salesPrice/(1+0.17));  //不含税单价（含税单价/(1+缺省税率)）
         pf.setDouble("IN_OUT_NET_AMOUNT",salesAmount/(1+0.17));  //不含税金额
         
         pf.setDouble("STOCK_QUANTITY",Double.parseDouble(map.get("stock_quantity")+""));  //账面库存
         pf.set("COST_PRICE",Double.parseDouble(map.get("cost_price")+""));  //成本单价
         pf.set("COST_AMOUNT",Double.parseDouble(map.get("cost_Amount")+""));  //成本金额
         pf.setString("SHEET_NO",map.get("lossNo")); //单据号码
         pf.setString("STORAGE_CODE",map.get("storage_code")+"" ); //仓库代码
         pf.setString("PART_NO",map.get("part_no")+"" ); //配件代码
         pf.setString("PART_NAME",map.get("part_name")+"" ); //配件名称
         pf.setString("OPERATOR",FrameworkUtil.getLoginInfo().getEmployeeNo()); //操作员
         return pf;
     }
    
    /**
     * 查询配件库存信息
     * @author jcsi
     * @date 2016年8月14日
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

}
