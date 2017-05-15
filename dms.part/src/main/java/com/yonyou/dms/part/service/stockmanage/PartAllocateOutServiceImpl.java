
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateOutServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    Administrator    1.0
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
import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutItemPo;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutPo;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateOutDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateOutItemDto;

/**
* 调拨出库 接口 实现类
* @author jcsi
* @date 2016年7月26日
*/
@Service
@SuppressWarnings({"unchecked", "rawtypes" })
public class PartAllocateOutServiceImpl implements PartAllocateOutService {

    @Autowired
    private OperateLogService operateLogService;
    /**
     * 根据条件查询
    * @author jcsi
    * @date 2016年7月31日
    * @param param 查询条件
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#search(java.util.Map)
     */
    @Override
    public PageInfoDto search(Map<String, String> param) throws ServiceBizException{
        StringBuilder sb=new StringBuilder("select t.ALLOCATE_OUT_ID,t.ALLOCATE_OUT_NO,");
        sb.append("t.ORDER_STATUS,t.ORDER_DATE,t.CUSTOMER_CODE,"); 
        sb.append("t.OUT_AMOUNT,t.DEALER_CODE,c.CUSTOMER_NAME  ");       
        sb.append(" from TT_PART_ALLOCATE_OUT t left JOIN TM_PART_CUSTOMER c on t.CUSTOMER_CODE=c.CUSTOMER_CODE ");
        sb.append(" where 1=1 " );
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("allocateOutNo"))){
            sb.append(" and t.ALLOCATE_OUT_NO like ? ");
            queryParam.add("%"+param.get("allocateOutNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customerCode"))){
            sb.append(" and t.CUSTOMER_CODE = ? ");
            queryParam.add(param.get("customerCode"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderStatus"))){
            sb.append(" and t.ORDER_STATUS = ? ");
            queryParam.add(Integer.parseInt(param.get("orderStatus")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and t.ORDER_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and t.ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }

        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }
    
    /**
     * 查询结算调拨出库单
    * @author jcsi
    * @date 2016年11月9日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateOutService#searchBalance(java.util.Map)
     */
    @Override
    public PageInfoDto searchBalance(Map<String, String> param) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();

        return DAOUtil.pageQuery(getSql(param,queryParam), queryParam);
    }

    /**
     * 根据出库单号查询
    * @author jcsi
    * @date 2016年11月16日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateOutService#searchBalanceByNo(java.util.Map)
     */
    @Override
    public List<Map> searchBalanceByNo(Map<String, String> param) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();
        return DAOUtil.findAll(getSql(param,queryParam), queryParam);
    }
    
    /**
    * 查询sql
    * @author jcsi
    * @date 2016年11月16日
    * @param param
    * @param queryParam
    * @return
     */
    public String getSql(Map<String, String> param,List<Object> queryParam){
        StringBuilder sb=new StringBuilder("select t.ALLOCATE_OUT_ID,t.ALLOCATE_OUT_NO,");
        sb.append("t.ORDER_STATUS,t.ORDER_DATE,t.CUSTOMER_CODE,"); 
        sb.append("t.OUT_AMOUNT,t.DEALER_CODE,c.CUSTOMER_NAME,t.REMARK   ");       
        sb.append(" from TT_PART_ALLOCATE_OUT t left JOIN TM_PART_CUSTOMER c on t.CUSTOMER_CODE=c.CUSTOMER_CODE ");
        sb.append(" where t.ORDER_STATUS=?  and t.IS_BALANCE = ? " );//
        queryParam.add(DictCodeConstants.OUT_ORDER_ISSTATUS);
        queryParam.add(DictCodeConstants.STATUS_IS_NOT);
        if(!StringUtils.isNullOrEmpty(param.get("allocateOutNo"))){
            sb.append(" and t.ALLOCATE_OUT_NO like ? ");
            queryParam.add("%"+param.get("allocateOutNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("customerName"))){
            sb.append(" and c.CUSTOMER_NAME like  ? ");
            queryParam.add("%"+param.get("customerName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateFrom"))){
            sb.append(" and t.ORDER_DATE >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("orderDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("orderDateTo"))){
            sb.append(" and t.ORDER_DATE < ? ");
            queryParam.add(DateUtil.addOneDay(param.get("orderDateTo")));
        }
        return sb.toString();
    }
    /**
     * 根据id级联删除调拨出库主表及子表信息
    * @author jcsi 
    * @date 2016年7月31日
    * @param id 主表id
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#delete(java.lang.Long)
     */
    @Override
    public void delete(Long id) throws ServiceBizException{
        PartAllocateOutPo paop=PartAllocateOutPo.findById(id);
        if(paop.getString("ORDER_STATUS").equals(DictCodeConstants.OUT_ORDER_ISSTATUS+"")){
            throw new ServiceBizException("该配件信息已出库,不能执行删除");      
        }
        //记录操作日志
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件调拨出库单删除：调拨出库单号【"+paop.getString("ALLOCATE_OUT_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        
        paop.deleteCascadeShallow();
        
        
    }

    /**
     * 添加调拨出库及明细信息
    * @author jcsi
    * @date 2016年7月31日
    * @param partAllocateOutDto
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#addPartOutAndOutItem(com.yonyou.dms.part.domains.DTO.basedata.PartAllocateOutDto)
     */
    @Override
    public PartAllocateOutPo addPartOutAndOutItem(String allocateOutNo,PartAllocateOutDto partAllocateOutDto) throws ServiceBizException{
        //查询成本单价重新赋值
        calCostAmount(partAllocateOutDto.getPartAllocateOutItemList());        
        PartAllocateOutPo paoPo=new PartAllocateOutPo();
        setPartAllocateOutPo(paoPo,partAllocateOutDto);        
        paoPo.setString("ALLOCATE_OUT_NO", allocateOutNo); //调拨出库单号 
        paoPo.saveIt();
        for(PartAllocateOutItemDto paoiDto:partAllocateOutDto.getPartAllocateOutItemList()){
            PartAllocateOutItemPo pao=setOutItem(paoiDto);                      
            paoPo.add(pao);
        }
        return paoPo;
    }
    
    /**
    * 根据仓库代码和配件代码查询成本单价
    * @author jcsi
    * @date 2016年8月5日
    * @param list
    * @throws ServiceBizException
     */
    public void calCostAmount(List<PartAllocateOutItemDto> list)throws ServiceBizException{
        if(!CommonUtils.isNullOrEmpty(list)){
            for(PartAllocateOutItemDto dto:list){                
                String storageCode= dto.getStorageCode();  //仓库代码
                String partNo=dto.getPartNo(); //配件代码
                String strSql="SELECT t.COST_PRICE,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=? and DEALER_CODE= ?";
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
    public Map<String,Object> calAmount(List<PartAllocateOutItemDto> list) throws ServiceBizException {
        Double outAmount=0.00;  //出库金额
        Double costAmount=0.00;  //成本金额
        Double costAmountItem=0.00;  //成本金额（明细）
        //累加计算成本金额和出库金额
        if(!CommonUtils.isNullOrEmpty(list)){
            for(PartAllocateOutItemDto oiDto:list){
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
    * 设置明细属性
    * @author jcsi
    * @date 2016年7月31日
     */
    public PartAllocateOutItemPo setOutItem(PartAllocateOutItemDto OiDto)throws ServiceBizException{
        PartAllocateOutItemPo pao=new PartAllocateOutItemPo();
        pao.setString("STORAGE_CODE",OiDto.getStorageCode());
        pao.setString("STORAGE_POSITION_CODE",OiDto.getStoragePositionCode());
        pao.setString("PART_NO", OiDto.getPartNo());
        pao.setString("PART_NAME", OiDto.getPartName());
        pao.setString("UNIT", OiDto.getUnit());
        pao.setDouble("OUT_QUANTITY", OiDto.getOutQuantity());
        pao.setDouble("OUT_PRICE", OiDto.getOutPrice());
        pao.setDouble("OUT_AMOUNT", OiDto.getOutAmount());
        pao.setDouble("COST_PRICE", OiDto.getCostPrice());
        pao.setDate("FINISHED_DATE", OiDto.getFinishedDate());
        pao.setDouble("COST_AMOUNT", OiDto.getCostPrice()*OiDto.getOutQuantity());
        pao.setLong("IS_FINISHED", DictCodeConstants.STATUS_IS_NOT);   //是否入账 （第一次保存，默认为“未入账”）
        return pao;
    }
    
    /**
    * 设置调拨出库主单属性
    * @author jcsi
    * @date 2016年7月31日
    * @param paoPo
    * @param partAllocateOutDto
     */
    public void setPartAllocateOutPo(PartAllocateOutPo paoPo,PartAllocateOutDto partAllocateOutDto)throws ServiceBizException{
        Map<String, Object> resultAmount=calAmount(partAllocateOutDto.getPartAllocateOutItemList());
        paoPo.setString("CUSTOMER_CODE", partAllocateOutDto.getCustomerCode());
        paoPo.setDouble("OUT_AMOUNT", resultAmount.get("outAmount"));
        paoPo.setDouble("COST_AMOUNT", resultAmount.get("costAmount"));
        paoPo.setDate("ORDER_DATE", partAllocateOutDto.getOrderDate());
        paoPo.setString("REMARK", partAllocateOutDto.getRemark());
        paoPo.setLong("ORDER_STATUS",DictCodeConstants.OUT_ORDER_NOSTATUS );  //单据状态   （第一次保存，默认为 “未出库”）
        paoPo.setInteger("IS_BALANCE",DictCodeConstants.STATUS_IS_NOT);
       
    }

    /**
     * 根据id查找
    * @author jcsi
    * @date 2016年7月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#findById(java.lang.Long)
     */
    @Override
    public Map<String, Object> findById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT t.ALLOCATE_OUT_ID,t.REMARK,t.ALLOCATE_OUT_NO,t.DEALER_CODE,t.CUSTOMER_CODE,t.ORDER_DATE,c.CUSTOMER_NAME from tt_part_allocate_out t left JOIN TM_PART_CUSTOMER c on t.CUSTOMER_CODE=c.CUSTOMER_CODE  AND t.DEALER_CODE=c.DEALER_CODE where t.ALLOCATE_OUT_ID=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(id);
        return DAOUtil.findFirst(sb.toString(), param);
        //return PartAllocateOutPo.findById(id).toMap();
    }

    /**
     * 更新
    * @author jcsi
    * @date 2016年7月31日
    * @param id
    * @param partAllocateOutDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#editPartAllocateOutDto(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartAllocateOutDto)
     */
    @Override
    public void editPartAllocateOutDto(Long id,PartAllocateOutDto partAllocateOutDto) throws ServiceBizException {
        //查询成本单价重新赋值
        calCostAmount(partAllocateOutDto.getPartAllocateOutItemList());
        
        PartAllocateOutPo paoPo=PartAllocateOutPo.findById(id);
        setPartAllocateOutPo(paoPo,partAllocateOutDto);       
        //保存主单信息
        paoPo.saveIt();
        //根据主表id删除之前保存的明细信息（全部）
        PartAllocateOutItemPo.delete("ALLOCATE_OUT_ID=?", id);
        for(PartAllocateOutItemDto dto:partAllocateOutDto.getPartAllocateOutItemList()){
            PartAllocateOutItemPo pao=setOutItem(dto);
            paoPo.add(pao);
        }
        
    }

    /**
     * 根据主表id查询详细信息
    * @author jcsi
    * @date 2016年8月1日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#searchOutItemByOutId(java.lang.Long)
     */
    @Override
    public List<Map> searchOutItemByOutId(Long id) throws ServiceBizException {//where ALLOCATE_OUT_ID=?
        StringBuilder sb=new StringBuilder("SELECT t.COST_PRICE costPriceShow,t.DEALER_CODE,t.ITEM_ID,t.ALLOCATE_OUT_ID,t.IS_FINISHED isFinishedShow,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.STORAGE_CODE storageCodeShow,t.STORAGE_POSITION_CODE storagePositionCodeShow,t.UNIT unitShow,t.OUT_QUANTITY canNumShow,t.OUT_PRICE outPriceShow,t.OUT_AMOUNT outAmountShow from tt_part_allocate_out_item t where ALLOCATE_OUT_ID=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(id);
        return DAOUtil.findAll(sb.toString(), param);
    }

    /**
     * 入账
    * @author jcsi
    * @date 2016年8月2日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartAllocateOutService#updateOrderStatusById(java.lang.Long)
     */
    @Override
    public void updateOrderStatusById(Long id) throws ServiceBizException {
        PartAllocateOutPo paoPo=PartAllocateOutPo.findById(id);
        //根据配件出库主表id查询明细信息
        List<Object> param=new ArrayList<Object>();
        String str="select t.OUT_QUANTITY,t.DEALER_CODE,t.STORAGE_CODE,t.PART_NO,t.PART_NAME,t.OUT_PRICE,t.OUT_AMOUNT from tt_part_allocate_out_item t  where t.ALLOCATE_OUT_ID=?";
        param.add(id);
        List<Map> result=DAOUtil.findAll(str, param);
        for(int i=0;i<result.size();i++){
            Map map=result.get(i);
            String storageCode=(String) map.get("storage_code");  //仓库代码
            String partNo=(String)map.get("part_no");  //配件代码
            Double outQuantity= Double.valueOf(map.get("out_quantity").toString());   //借出数量
            //根据仓库代码+配件代码   查询配件库存信息（账面库存数量，配件成本单价）
            String strSql="SELECT t.STOCK_QUANTITY,t.COST_PRICE,t.DEALER_CODE from tt_part_stock t where STORAGE_CODE=? and PART_CODE=?  and DEALER_CODE=?";
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(storageCode);
            queryParam.add(partNo);
            queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
            Map stockMap=DAOUtil.findFirst(strSql, queryParam);
            
            Double stockQuantity=Double.valueOf(stockMap.get("stock_quantity").toString());  //账面库存数量
            Double costPrice=Double.valueOf(stockMap.get("cost_price").toString()) ;  //成本单价
            
            //重新计算库存数量  和 成本金额
            Double canStockQuantity=stockQuantity-outQuantity; //库存数量
            Double canCostAmount=canStockQuantity*costPrice;  //成本金额
            //更新库存账面数量，配件成本金额
            TmPartStockPO.update("LAST_STOCK_OUT=?,COST_AMOUNT=?,STOCK_QUANTITY=?","STORAGE_CODE=? and PART_CODE=?   and DEALER_CODE=?",new Date(),canCostAmount,canStockQuantity,storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
            //更新明细表中成本单价和成本金额 
            PartAllocateOutItemPo.update("COST_PRICE=?,COST_AMOUNT=?,FINISHED_DATE=?,IS_FINISHED=?", "STORAGE_CODE=? and PART_NO=?   and DEALER_CODE=?", costPrice,costPrice*outQuantity,new Date(),DictCodeConstants.STATUS_IS_YES,storageCode,partNo,FrameworkUtil.getLoginInfo().getDealerCode());
            //插入配件流水
            map.put("stock_quantity", canStockQuantity);  //账面库存
            map.put("cost_price",costPrice);
            map.put("cost_amount",costPrice*canStockQuantity);
            map.put("out_no",paoPo.getString("allocate_out_no"));
            map.put("customer_code",paoPo.getString("customer_code"));
            PartFlowPO pf=setPartFlowPO(map);
            pf.saveIt();
        }
        //更新主单状态为“已出库”
        paoPo.setString("ORDER_STATUS", DictCodeConstants.OUT_ORDER_ISSTATUS);  //已出库
        paoPo.saveIt();
    }
    
    
    /**
     * 注入配件流水账属性
     * @author jcsi
     * @date 2016年8月14日
      */
     public PartFlowPO setPartFlowPO(Map<String,Object> map){
         PartFlowPO pf=new PartFlowPO();
         pf.setString("IN_OUT_TYPE", DictCodeConstants.PART_ALLOCATE_OUT);  //出入库类型
         pf.setTimestamp("OPERATE_DATE", new Date());   //发生时间
         pf.setDouble("STOCK_OUT_QUANTITY", map.get("out_quantity"));  //出数量
         pf.setDouble("IN_OUT_TAXED_PRICE",Double.parseDouble(map.get("out_price")+""));  //含税单价
         pf.setDouble("IN_OUT_TAXED_AMOUNT",Double.parseDouble(map.get("out_amount")+""));  //含税金额
         
         Double salesPrice=Double.parseDouble(map.get("out_price")+"");  
         Double salesAmount=Double.parseDouble(map.get("out_amount")+"");
         pf.setDouble("IN_OUT_NET_PRICE",salesPrice/(1+0.17));  //不含税单价（含税单价/(1+缺省税率)）
         pf.setDouble("IN_OUT_NET_AMOUNT",salesAmount/(1+0.17));  //不含税金额
         
         pf.setDouble("STOCK_QUANTITY",Double.parseDouble(map.get("stock_quantity")+""));  //账面库存
         pf.set("COST_PRICE",Double.parseDouble(map.get("cost_price")+""));  //成本单价
         pf.set("COST_AMOUNT",Double.parseDouble(map.get("cost_amount")+""));  //成本金额
         pf.setString("SHEET_NO",map.get("out_no")); //单据号码
         pf.setString("STORAGE_CODE",map.get("storage_code")+"" ); //仓库代码
         pf.setString("PART_NO",map.get("part_no")+"" ); //配件代码
         pf.setString("PART_NAME",map.get("part_name")+"" ); //配件名称
         pf.setString("OPERATOR",FrameworkUtil.getLoginInfo().getEmployeeNo()); //操作员
         pf.setInteger("CUSTOMER_TYPE", DictCodeConstants.CUSTOMER_CONTACT);
         pf.setString("CUSTOMER_CODE",map.get("customer_code"));
         return pf;
    }

   

   
}
