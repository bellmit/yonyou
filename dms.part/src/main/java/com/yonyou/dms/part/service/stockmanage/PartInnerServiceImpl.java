
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInnerServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年8月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月15日    jcsi    1.0
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
import com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerItemDto;
import com.yonyou.dms.part.domains.PO.stockmanage.PartInnerItemPo;
import com.yonyou.dms.part.domains.PO.stockmanage.PartInnerPo;


/**
* 内部领用 实现类
* @author jcsi
* @date 2016年8月15日
*/
@Service
@SuppressWarnings({"unchecked", "rawtypes" })
public class PartInnerServiceImpl implements PartInnerService {

    @Autowired
    private OperateLogService operateLogService;
    /**
     * 根据条件查询内部领用信息
    * @author jcsi
    * @date 2016年8月15日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#search(java.util.Map)
    */

    @Override
    public PageInfoDto search(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.PART_INNER_ID,t.DEALER_CODE,t.RECEIPT_NO,t.ORDER_STATUS,t.RECEIPTOR,td.ORG_NAME,t.OUT_AMOUNT,t.RECEIPT_DATE,e.EMPLOYEE_NAME  ");
        sb.append("FROM tt_part_inner t  ");
        sb.append("inner JOIN tm_employee e ON t.RECEIPTOR = e.EMPLOYEE_NO and t.DEALER_CODE=e.DEALER_CODE  ");
        sb.append("inner JOIN tm_organization  td on e.ORG_CODE=td.ORG_CODE and e.DEALER_CODE=td.DEALER_CODE  ");
        sb.append("where 1=1 ");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("receiptNo"))){
            sb.append(" and t.RECEIPT_NO like ?");
            queryParam.add("%"+param.get("receiptNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("receiptor"))){
            sb.append(" and t.RECEIPTOR = ? ");
            queryParam.add("%"+param.get("receiptor")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderStatus"))){
            sb.append(" and t.ORDER_STATUS= ? ");
            queryParam.add(Integer.parseInt(param.get("orderStatus")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and t.RECEIPT_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and t.RECEIPT_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    /**
     * 根据id删除内部领用及明细信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#deleteById(java.lang.Long)
    */

    @Override
    public void deleteById(Long id) throws ServiceBizException {
        PartInnerPo partInnerPo=PartInnerPo.findById(id);
        if(Integer.toString(DictCodeConstants.OUT_ORDER_ISSTATUS).equals(partInnerPo.get("order_status").toString())){
            throw new ServiceBizException("该条信息已出库,不能执行删除");
        }
        //记录操作日志
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件内部领用单删除：领用单号【"+partInnerPo.getString("RECEIPT_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        
        partInnerPo.deleteCascadeShallow();
        
      
    }

    /**
     * 新增
    * @author jcsi
    * @date 2016年8月15日
    * @param partInnerDto
    * @param receiptNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#addPartInner(com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerDto, java.lang.String)
    */

    @Override
    public PartInnerPo addPartInner(PartInnerDto partInnerDto, String receiptNo) throws ServiceBizException {
        //根据配件代码、仓库代码+declCode查询配件成本价
        searchCostPrice(partInnerDto.getPartInnerItemDtos());
        //给主表属性赋值
        PartInnerPo partInnerPo=new PartInnerPo();
        setPartInnerPo(partInnerPo,partInnerDto);
        partInnerPo.setString("RECEIPT_NO", receiptNo);
        partInnerPo.saveIt();
        for(PartInnerItemDto itemDto:partInnerDto.getPartInnerItemDtos()){
            PartInnerItemPo itemPo=new PartInnerItemPo();
            setPartInnerItemPo(itemPo,itemDto);
            partInnerPo.add(itemPo);
        }
        return partInnerPo;
    }

    /**
     * 修改
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @param partInnerDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#editPartInner(java.lang.Long, com.yonyou.dms.part.domains.DTO.stockmanage.PartInnerDto)
    */

    @Override
    public void editPartInner(Long id, PartInnerDto partInnerDto) throws ServiceBizException {
        //根据配件代码、仓库代码+declCode查询配件成本价
        searchCostPrice(partInnerDto.getPartInnerItemDtos());
        //给主表属性赋值
        PartInnerPo partInnerPo=PartInnerPo.findById(id);
        setPartInnerPo(partInnerPo,partInnerDto);
        partInnerPo.saveIt();
        
        PartInnerItemPo.delete("PART_INNER_ID=?", id);
        for(PartInnerItemDto itemDto:partInnerDto.getPartInnerItemDtos()){
            PartInnerItemPo itemPo=new PartInnerItemPo();
            setPartInnerItemPo(itemPo,itemDto);
            partInnerPo.add(itemPo);
        }
    }
    
    /**
     * 根据仓库代码和配件代码查询成本单价
     * @author jcsi
     * @date 2016年8月15日
     * @param list
     * @throws ServiceBizException
      */
    public void searchCostPrice(List<PartInnerItemDto> list)throws ServiceBizException{
         if(!CommonUtils.isNullOrEmpty(list)){
             for(PartInnerItemDto dto:list){                
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
    * PartInnerPo属性赋值
    * @author jcsi
    * @date 2016年8月15日
    * @param partInnerPo
    * @param partInnerDto
     */
    public void setPartInnerPo(PartInnerPo partInnerPo,PartInnerDto partInnerDto){
        Map<String, Object> resultAmount=calAmount(partInnerDto.getPartInnerItemDtos());
        partInnerPo.setDouble("COST_AMOUNT", resultAmount.get("costAmount"));  //成本金额
        partInnerPo.setDouble("OUT_AMOUNT", resultAmount.get("outAmount"));  //出库金额
        partInnerPo.setDate("RECEIPT_DATE", partInnerDto.getReceiptDate()); //领用日期
        partInnerPo.setString("RECEIPTOR", partInnerDto.getReceiptor()); //领用人
        partInnerPo.setLong("ORDER_STATUS",DictCodeConstants.OUT_ORDER_NOSTATUS); //单据状态(保存默认为‘未出库’)
        partInnerPo.setString("REMARK", partInnerDto.getRemark()); //备注
    }
    
    /**
    * PartInnerItemPo属性赋值
    * @author jcsi
    * @date 2016年8月15日
    * @param itemPo
    * @param ItemDto
     */
    public void setPartInnerItemPo(PartInnerItemPo itemPo,PartInnerItemDto itemDto){
        itemPo.setString("STORAGE_CODE", itemDto.getStorageCode());  //仓库代码
        itemPo.setString("STORAGE_POSITION_CODE", itemDto.getStoragePositionCode());  //库位
        itemPo.setString("PART_NO", itemDto.getPartNo());  //配件代码
        itemPo.setString("PART_NAME", itemDto.getPartName());  //配件名称
        itemPo.setString("UNIT", itemDto.getUnit());  //计量单位
        itemPo.setDouble("OUT_QUANTITY", itemDto.getOutQuantity()); //出库数量
        itemPo.setDouble("COST_PRICE", itemDto.getCostPrice());  //成本单价
        itemPo.setDouble("COST_AMOUNT", itemDto.getCostPrice()*itemDto.getOutQuantity()); //成本金额
        itemPo.setDouble("OUT_PRICE", itemDto.getOutPrice());  //报损单价
        itemPo.setDouble("OUT_AMOUNT", itemDto.getOutAmount());//盘亏金额
        itemPo.setLong("IS_FINISHED", DictCodeConstants.STATUS_IS_NOT);  //是否入账 (默认为否)
    }
    
    /**
     * 计算成本金额和出库金额
     * @author jcsi
     * @date 2016年8月3日
     * @param list
     * @return
      */
     public Map<String,Object> calAmount(List<PartInnerItemDto> list) throws ServiceBizException {
         Double outAmount=0.00;  //出库金额
         Double costAmount=0.00;  //成本金额
         Double costAmountItem=0.00;  //成本金额（明细）
         //累加计算成本金额和出库金额
         if(!CommonUtils.isNullOrEmpty(list)){
             for(PartInnerItemDto oiDto:list){
                 costAmountItem=oiDto.getCostPrice()*oiDto.getOutQuantity(); 
                 outAmount+=oiDto.getOutAmount();
                 costAmount+=costAmountItem;
             }
         }
         Map<String,Object> result=new HashMap<String,Object>();
         result.put("outAmount", outAmount);
         result.put("costAmount", costAmount);
         return result;
     }

    /**
     * 根据id查询主单信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#findById(java.lang.Long)
     */
    @Override
    public Map<String, Object> findById(Long id) throws ServiceBizException {
        return PartInnerPo.findById(id).toMap();
    }

    /**
     * 根据主表id查询子表信息
    * @author jcsi
    * @date 2016年8月15日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#findItemById(java.lang.Long)
     */
    @Override
    public PageInfoDto findItemById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT t.ITEM_ID,t.PART_INNER_ID,t.DEALER_CODE,t.IS_FINISHED isFinishedShow,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.STORAGE_CODE storageCodeShow ,t.STORAGE_POSITION_CODE storagePositionCodeShow,t.UNIT unitShow,t.OUT_QUANTITY outQuantityShow,t.OUT_PRICE outPriceShow ,t.OUT_AMOUNT outAmountShow from tt_part_inner_item t where PART_INNER_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    /**
     * 入账
    * @author jcsi 
    * @date 2016年8月16日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartInnerService#updateOrderStatus(java.lang.Long)
     */
    @Override
    public void updateOrderStatus(Long id) throws ServiceBizException {
        PartInnerPo itemPo=PartInnerPo.findById(id);
        //更新子表、库存  信息    插入配件流水账
        updateStockAndItem(id,itemPo.getString("receipt_no"));
        //更新主单信息
        itemPo.set("ORDER_STATUS",DictCodeConstants.OUT_ORDER_ISSTATUS);
        itemPo.saveIt();
    }
    
    private void updateStockAndItem(Long id,String receiptNo)throws ServiceBizException{
        //根据配件出库主表id查询明细信息
          List<Object> param=new ArrayList<Object>();
          String str="SELECT t.DEALER_CODE,t.STORAGE_CODE,t.PART_NO,t.PART_NAME,t.OUT_PRICE,t.OUT_QUANTITY,t.OUT_AMOUNT from tt_part_inner_item t where t.PART_INNER_ID=?";
          param.add(id);
          List<Map> result=DAOUtil.findAll(str, param);
          for(int i=0;i<result.size();i++){
              Map map=result.get(i);
              String storageCode=(String) map.get("storage_code");  //仓库代码
              String partNo=(String)map.get("part_no");  //配件代码
              Double outQuantity= Double.valueOf(map.get("out_quantity").toString());   //出库数量
              //根据仓库代码+配件代码   查询配件库存信息（账面库存数量，配件成本单价...）
              Map stockMap=searchPartStore(storageCode,partNo);
              
              Double stockQuantity=Double.valueOf(stockMap.get("stock_quantity").toString());  //账面库存数量
              Double costPrice=Double.valueOf(stockMap.get("cost_price").toString()) ;  //成本单价
              
              //出库数量>库存数量   判断出库仓库是否允许负库存
             /* if(outQuantity>stockQuantity){
                  String storeStr="SELECT DEALER_CODE,IS_NEGATIVE from TM_STORE  where DEALER_CODE=? and STORAGE_CODE=?";
                  List<Object> queryParamStore=new ArrayList<Object>();
                  queryParamStore.add(FrameworkUtil.getLoginInfo().getDealerCode());
                  queryParamStore.add(storageCode);
                  Map mapStore=DAOUtil.findFirst(storeStr, queryParamStore);
                  String Storeresult=mapStore.get("is_negative")+"";
                  if(Integer.toString(DictCodeConstants.STATUS_IS_NOT).equals(Storeresult)){
                      throw new ServiceBizException(partNo+"配件出库数量大于库存数量，保存失败");
                  }
              }*/
              
              //重新计算库存数量  和 成本金额
              Double canStockQuantity=stockQuantity-outQuantity; //新库存数量
              Double canCostAmount=canStockQuantity*costPrice;  //新成本金额
              //更新库存账面数量，配件成本金额
              TmPartStockPO.update("LAST_STOCK_OUT=?,COST_AMOUNT=?,STOCK_QUANTITY=?","STORAGE_CODE=? and PART_CODE=? and DEALER_CODE=? ",new Date(),canCostAmount,canStockQuantity,storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
              //更新明细表中成本单价和成本金额 、入账状态
              PartInnerItemPo.update("COST_PRICE=?,COST_AMOUNT=?,IS_FINISHED=?,FINISHED_DATE=? ", "STORAGE_CODE=? and PART_NO=?  and DEALER_CODE=? ", costPrice,costPrice*outQuantity,DictCodeConstants.STATUS_IS_YES,new Date(),storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
              //插入配件流水
              map.put("stock_quantity", canStockQuantity);  //账面库存
              map.put("cost_price",costPrice);
              map.put("cost_amount",costPrice*canStockQuantity);
              map.put("receipt_no",receiptNo);
              
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
         pf.setString("IN_OUT_TYPE", DictCodeConstants.PART_INNER_TYPE);  //出入库类型
         pf.setTimestamp("OPERATE_DATE", new Date());   //发生时间
         pf.setDouble("STOCK_OUT_QUANTITY", Double.parseDouble(map.get("out_quantity")+""));  //出数量
         pf.setDouble("IN_OUT_TAXED_PRICE",Double.parseDouble(map.get("out_price")+""));  //含税单价
         pf.setDouble("IN_OUT_TAXED_AMOUNT",Double.parseDouble(map.get("out_amount")+""));  //含税金额
         
         Double salesPrice=Double.parseDouble(map.get("out_price")+"");  
         Double salesAmount=Double.parseDouble(map.get("out_amount")+"");
         pf.setDouble("IN_OUT_NET_PRICE",salesPrice/(1+0.17));  //不含税单价（含税单价/(1+缺省税率)）
         pf.setDouble("IN_OUT_NET_AMOUNT",salesAmount/(1+0.17));  //不含税金额
         
         pf.setDouble("STOCK_QUANTITY",Double.parseDouble(map.get("stock_quantity")+""));  //账面库存
         pf.set("COST_PRICE",Double.parseDouble(map.get("cost_price")+""));  //成本单价
         pf.set("COST_AMOUNT",Double.parseDouble(map.get("cost_amount")+""));  //成本金额
         pf.setString("SHEET_NO",map.get("receipt_no")); //单据号码
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
