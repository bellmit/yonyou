
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAccountsServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAccountsPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutPo;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartSalesPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAccountsDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAddItemDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAllocatePartDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceLabourDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalancePayobjDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceRepairPartDTO;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceSalesPartDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceSalesPartPO;
import com.yonyou.dms.repair.service.order.RepairOrderService;

/**
* 结算单实现类
* @author ZhengHe
* @date 2016年9月27日
*/
@Service
public class BalanceAccountsServiceImpl implements BalanceAccountsService{

    @Autowired
    private BalanceLabourService bLService;
    
    @Autowired
    private BalanceRepairPartService bRPService;
    
    @Autowired
    private balanceAddItemService bAIService;
    
    @Autowired
    private balancePayobjService bPOService;
    
    @Autowired
    private RepairOrderService roService;
    
    @Autowired
    private OperateLogService operateLogService;
    
    @Autowired
    private BalanceSalesPartService bSPService;
    
    /**
    * 生成新的结算单
    * @author ZhengHe
    * @date 2016年9月27日
    * @param baDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#addBalanceAccounts(com.yonyou.dms.repair.domains.DTO.balance.BalanceAccountsDTO)
    */
        
    @Override
    public Long addBalanceAccounts(BalanceAccountsDTO baDto) throws ServiceBizException {
        if(baDto.getRoType()==DictCodeConstants.RO_TYPE_REPAIR){
            setBADTO(baDto,baDto.getRoId());
            countAmount(baDto);
            if(getChargePartitionCode(baDto,baDto.getRoId())){
                //含有索赔金额，费用拆分
                countPayObjAmount(baDto,"true");
                //含有索赔金额
                return addAllBalanceTable(baDto);
            }else{
                //不含索赔金额费用拆分
                countPayObjAmount(baDto);
                return addAllBalanceTable(baDto);
            }

        //销售结算    
        }else if(baDto.getRoType()==DictCodeConstants.RO_TYPE_SALES){
            return saveSalesPartBalance(baDto); 
        }else if(baDto.getRoType()==DictCodeConstants.RO_TYPE_TRANSFERS){
            return saveAllocateBalance(baDto);
        }
        return null;
    }
    /**
    * 保存调拨出库结算单
    * @author jcsi
    * @date 2016年11月9日
    * @param baDto
    * @return
     */
    public Long saveAllocateBalance(BalanceAccountsDTO baDto){
    	TtBalanceAccountsPO baPo=new TtBalanceAccountsPO();
        calAllocateAmount(baDto);
        setBAPO(baPo,baDto);
        //保存调拨出库结算单
        baPo.saveIt();
        Long allocateOutId=null;
        for(int i=0;i<baDto.getbAPDtoList().size();i++){
        	TtBalanceSalesPartPO bapPO=new TtBalanceSalesPartPO();
            setBalanceAllocatePart(bapPO,baDto.getbAPDtoList().get(i));
            //保存调拨出库单明细
            baPo.add(bapPO);
            allocateOutId=bapPO.getLong("ALLOCATE_OUT_ID");
        }
        saveBalancePayobj(baPo,baDto);
        //更新结算单状态
        if(!StringUtils.isNullOrEmpty(allocateOutId)){
            PartAllocateOutPo allOutPo=PartAllocateOutPo.findById(allocateOutId);
            allOutPo.setInteger("IS_BALANCE", DictCodeConstants.STATUS_IS_YES);
            allOutPo.saveIt();
        }
        return baPo.getLongId();
    }
    /**
    * 保存收费结算单对象
    * @author jcsi
    * @date 2016年11月9日
    * @param baPo
    * @param baDto
    * @throws ServiceBizException
     */
    public void saveBalancePayobj(TtBalanceAccountsPO baPo,BalanceAccountsDTO baDto)throws ServiceBizException{
        //保存结算单收费对象
        for(int i=0;i<baDto.getbPODtoList().size();i++){
            BalancePayobjDTO balancePayobjDto=baDto.getbPODtoList().get(i);
            if(baDto.getCustomerType().equals(DictCodeConstants.CUSTOMER_OWNER+"")){
                StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,OWNER_ID,OWNER_NO,OWNER_PROPERTY,OWNER_NAME FROM tm_owner WHERE 1=1 ");
                sqlsb.append(" and OWNER_NO=?");
                List<Object> queryParam=new ArrayList<Object>();
                queryParam.add(balancePayobjDto.getPaymentObjectCode());
                Map bPOMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
                balancePayobjDto.setPaymentObjectType(DictCodeConstants.VEHICLE_TYPE);
                balancePayobjDto.setPaymentObjectName(bPOMap.get("OWNER_NAME").toString());
            }else if(baDto.getCustomerType().equals(DictCodeConstants.CUSTOMER_CONTACT+"")){
                StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,CUSTOMER_NAME FROM tm_part_customer WHERE 1=1 ");
                sqlsb.append(" and CUSTOMER_CODE=?");
                List<Object> queryParam=new ArrayList<Object>();
                queryParam.add(balancePayobjDto.getPaymentObjectCode());
                Map bPOMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
                balancePayobjDto.setPaymentObjectType(DictCodeConstants.CUSTOMER_TYPE);
                balancePayobjDto.setPaymentObjectName(bPOMap.get("CUSTOMER_NAME").toString());
            }
        
           
            balancePayobjDto.setBalanceId(baPo.getLongId());
            Long id=bPOService.addBalancePayobj(balancePayobjDto);
            baDto.getbPODtoList().get(i).setBalaPayobjId(id);
        }
    }
    /**
    * 保存销售结算单
    * @author jcsi
    * @date 2016年11月7日
     */
    public Long saveSalesPartBalance(BalanceAccountsDTO baDto)throws ServiceBizException{
        TtBalanceAccountsPO baPo=new TtBalanceAccountsPO();
        calAmount(baDto);
        setBAPO(baPo,baDto);
        //保存销售结算单
        baPo.saveIt();
        Long salesId=null;
        for(int i=0;i<baDto.getbSPDtoList().size();i++){
        	TtBalanceSalesPartPO BalanceSalesPartPo=new TtBalanceSalesPartPO();
            setBalanceSalesPart(BalanceSalesPartPo,baDto.getbSPDtoList().get(i));
            //保存销售单结算明细
            salesId=BalanceSalesPartPo.getLong("SALES_PART_ID");
            baPo.add(BalanceSalesPartPo);;
        }
        //保存收费对象
        saveBalancePayobj(baPo,baDto);
       
        //更新结算状态
        if(!StringUtils.isNullOrEmpty(salesId)){
            PartSalesPo partSalesPo=PartSalesPo.findById(salesId);
            partSalesPo.setInteger("IS_BALANCE",DictCodeConstants.STATUS_IS_YES);
            partSalesPo.saveIt();
        }
        
        return baPo.getLongId();
    }
   
    /**
    * 计算结算单金额(调拨)
    * @author jcsi
    * @date 2016年11月9日
     */
    public void calAllocateAmount(BalanceAccountsDTO baDto){
        Double partSalesAmount=0.0;  //销售材料费
        Double amount=0.0;   //总金额
        for(int i=0;i<baDto.getbAPDtoList().size();i++){
            BalanceAllocatePartDTO bapDto= baDto.getbAPDtoList().get(i);
            partSalesAmount=NumberUtil.add2Double(partSalesAmount, bapDto.getOutAmount());
            amount=partSalesAmount;
        }
        Double discount=new Double(1);
        if(!StringUtils.isNullOrEmpty(baDto.getDiscountModeCode())){
            //根据优惠模式查找折扣率
            StringBuilder sb=new StringBuilder();
            sb.append("select DEALER_CODE,SALES_PART_DISCOUNT from tm_discount_mode t where t.DISCOUNT_MODE_CODE=? and t.DEALER_CODE=?");
            List<Object> param=new ArrayList<Object>();
            param.add(baDto.getDiscountModeCode());
            param.add(FrameworkUtil.getLoginInfo().getDealerCode());
            Map result=DAOUtil.findFirst(sb.toString(),param);
            discount=(Double)result.get("SALES_PART_DISCOUNT");
        }
       
        
        baDto.setSalesPartAmount(partSalesAmount);
        baDto.setAmount(amount);
        //折扣后销售材料费
        baDto.setDisSalesPartAmount(NumberUtil.mul2Double(partSalesAmount,discount));
        //折扣后总金额
        baDto.setDisAmount(NumberUtil.mul2Double(partSalesAmount,discount));
        //结算金额
        baDto.setBalanceAmount(NumberUtil.sub2Double(baDto.getDisSalesPartAmount(),baDto.getSubObbAmount()));
    }
    
    /**
    * 计算结算单金额（销售）
    * @author jcsi
    * @date 2016年11月7日
    * @param baDto
     */
    public void calAmount(BalanceAccountsDTO baDto){
        Double partSalesAmount=0.0;  //销售材料费
        Double amount=0.0;   //总金额
        Double disSalesPartAmount=0.0;  //折扣后销售材料费
        Double disAmount=0.0;
        Double balanceAmount=0.0;  //结算金额
        for(int i=0;i<baDto.getbSPDtoList().size();i++){
            BalanceSalesPartDTO balanceSalesPartDto= baDto.getbSPDtoList().get(i);
            partSalesAmount=NumberUtil.add2Double(partSalesAmount,balanceSalesPartDto.getPartSalesAmount());
            amount=partSalesAmount;
            disSalesPartAmount=NumberUtil.add2Double(disSalesPartAmount, NumberUtil.mul2Double(balanceSalesPartDto.getPartSalesAmount(), balanceSalesPartDto.getDiscount()));
            disAmount=disSalesPartAmount;
            balanceAmount=NumberUtil.add2Double(balanceAmount, balanceSalesPartDto.getSalesAmount());
        }
        baDto.setSalesPartAmount(partSalesAmount);
        baDto.setAmount(amount);
        baDto.setDisSalesPartAmount(disSalesPartAmount);
        baDto.setDisAmount(disAmount);
        baDto.setBalanceAmount(NumberUtil.sub2Double(balanceAmount, baDto.getSubObbAmount()));
    }
    /**
    * 结算单销售出库明细
    * @author jcsi
    * @date 2016年11月9日
    * @param balanceAllocatePartPO
    * @param balanceAllocatePartDto
    * @throws ServiceBizException
     */
    public void setBalanceAllocatePart(TtBalanceSalesPartPO balanceAllocatePartPO,BalanceAllocatePartDTO balanceAllocatePartDto)throws ServiceBizException{
        StringBuilder sb=new StringBuilder();
        sb.append("select ITEM_ID,DEALER_CODE,ALLOCATE_OUT_ID,STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO, ");
        sb.append("PART_NAME,UNIT,OUT_QUANTITY,OUT_PRICE,OUT_AMOUNT,COST_PRICE,COST_AMOUNT   ");
        sb.append("from tt_part_allocate_out_item where ITEM_ID=?");
        List<Object> param=new ArrayList<Object>();
        param.add(balanceAllocatePartDto.getItemId());
        Map result=DAOUtil.findFirst(sb.toString(), param);
        balanceAllocatePartPO.setLong("ITEM_ID",result.get("ITEM_ID"));
        balanceAllocatePartPO.setLong("ALLOCATE_OUT_ID",result.get("ALLOCATE_OUT_ID"));
        balanceAllocatePartPO.setString("STORAGE_CODE",result.get("STORAGE_CODE"));
        balanceAllocatePartPO.setString("STORAGE_POSITION_CODE",result.get("STORAGE_POSITION_CODE"));
        balanceAllocatePartPO.setString("PART_NO",result.get("PART_NO"));
        balanceAllocatePartPO.setString("PART_NAME",result.get("PART_NAME"));
        balanceAllocatePartPO.setString("UNIT",result.get("UNIT"));
        balanceAllocatePartPO.setDouble("OUT_QUANTITY",result.get("OUT_QUANTITY"));
        balanceAllocatePartPO.setDouble("OUT_PRICE",result.get("OUT_PRICE"));
        balanceAllocatePartPO.setDouble("OUT_AMOUNT",result.get("OUT_AMOUNT"));
        balanceAllocatePartPO.setDouble("COST_PRICE",result.get("COST_PRICE"));
        balanceAllocatePartPO.setDouble("COST_AMOUNT",result.get("COST_AMOUNT"));
    
    }
    
   /**
   * 结算单销售配件明细
   * @author jcsi
   * @date 2016年11月7日
   * @param BalanceSalesPartPo
   * @param BalanceSalesPartDto
   * @throws ServiceBizException
    */
   public void setBalanceSalesPart(TtBalanceSalesPartPO BalanceSalesPartPo,BalanceSalesPartDTO BalanceSalesPartDto)throws ServiceBizException{
       StringBuilder sql=new StringBuilder();
       sql.append("select ITEM_ID,DEALER_CODE,SALES_PART_ID,STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO,PART_NAME,PART_QUANTITY,");
       sql.append("UNIT,PRICE_TYPE,PRICE_RATE,COST_PRICE,PART_SALES_PRICE,PART_COST_AMOUNT,PART_SALES_AMOUNT,SALES_AMOUNT   ");
       sql.append(" from tt_part_sales_item t  ");
       sql.append(" where t.ITEM_ID=?");
       List<Object> param=new ArrayList<Object>();
       param.add(BalanceSalesPartDto.getItemId());
       Map result=DAOUtil.findFirst(sql.toString(), param);

       BalanceSalesPartPo.setInteger("ITEM_ID", result.get("ITEM_ID"));  //配件销售明细ID
       BalanceSalesPartPo.setInteger("SALES_PART_ID", result.get("SALES_PART_ID"));  //配件销售单ID
       BalanceSalesPartPo.setString("STORAGE_CODE", result.get("STORAGE_CODE")); //仓库代码
       BalanceSalesPartPo.setString("STORAGE_POSITION_CODE", result.get("STORAGE_POSITION_CODE"));  //库位代码
       BalanceSalesPartPo.setString("PART_NO", result.get("PART_NO")); //配件代码
       BalanceSalesPartPo.setString("PART_NAME", result.get("PART_NAME")); //配件名称
       BalanceSalesPartPo.setDouble("PART_QUANTITY", result.get("PART_QUANTITY")); //配件数量
       BalanceSalesPartPo.setString("UNIT", result.get("UNIT")); //计量单位
       BalanceSalesPartPo.setString("DISCOUNT", BalanceSalesPartDto.getDiscount()); //折扣率
       BalanceSalesPartPo.setInteger("PRICE_TYPE", result.get("PRICE_TYPE")); //价格类型
       BalanceSalesPartPo.setDouble("PRICE_RATE", result.get("PRICE_RATE")); //价格系数
       BalanceSalesPartPo.setDouble("COST_PRICE", result.get("COST_PRICE")); //最新不含税单价
       BalanceSalesPartPo.setDouble("PART_SALES_PRICE", result.get("PART_SALES_PRICE")); //配件销售单价
       BalanceSalesPartPo.setDouble("PART_COST_AMOUNT", result.get("PART_COST_AMOUNT")); //配件成本金额
       BalanceSalesPartPo.setDouble("PART_SALES_AMOUNT", result.get("PART_SALES_AMOUNT")); //配件销售金额
       BalanceSalesPartPo.setDouble("SALES_AMOUNT",(Double)result.get("PART_SALES_AMOUNT")*BalanceSalesPartDto.getDiscount()); //折后销售金额
   }
    
    /**
    * 生成结算单及各表
    * @author ZhengHe
    * @date 2016年10月11日
    * @param baDto
    * @return
    */
    	
    public Long addAllBalanceTable(BalanceAccountsDTO baDto){
        TtBalanceAccountsPO baPo=new TtBalanceAccountsPO();
        setBAPO(baPo,baDto);
        baPo.saveIt();
        Long balanceId=baPo.getLongId();
        List<BalanceLabourDTO> bLDtoList=baDto.getbLDtoList();
        //新建tt_balance_labour
        for(BalanceLabourDTO bLDto:bLDtoList){
            bLDto.setBalanceId(balanceId);
            Long balanceLabourId=bLService.addBalanceLabour(bLDto);
            bLDto.setBalanceLabourId(balanceLabourId);
        }
        List<BalanceRepairPartDTO> bRPDtoList=baDto.getbRPDtoList();
      //新建tt_balance_repair_part
        for(BalanceRepairPartDTO bRPDto:bRPDtoList){
            bRPDto.setBalanceId(balanceId);
            for(BalanceLabourDTO bLDto:bLDtoList){
                Long roLabourId=bLDto.getRoLabourId();
                Long balanceLabourId=bLDto.getBalanceLabourId();
                if(StringUtils.isEquals(roLabourId,bRPDto.getLabourId().toString())){
                    bRPDto.setBalanceLabourId(balanceLabourId);
                    break;
                }
            }
            Long balanceRepairPartId=bRPService.addBalanceRepairPart(bRPDto);
            bRPDto.setBalanceRepairPartId(balanceRepairPartId);
        }
        
        List<BalanceAddItemDTO> bAIDtoList = baDto.getbAIDtoList();
        //新建tt_balance_add_item
        for(BalanceAddItemDTO bAIDto:bAIDtoList ){
            bAIDto.setBalanceId(balanceId);
            Long balanceAddItemId=bAIService.addBalanceAddItem(bAIDto);
            bAIDto.setBalanceAddItemId(balanceAddItemId);
        }
        
        List<BalancePayobjDTO> bPODtoList=baDto.getbPODtoList();
        //新建tt_balance_payobj
        for(BalancePayobjDTO bPODto:bPODtoList){
            bPODto.setBalanceId(balanceId);
            Long balaPayobjId=bPOService.addBalancePayobj(bPODto);
            bPODto.setBalaPayobjId(balaPayobjId);
        }
        List<BalanceSalesPartDTO> bSPDtoList=baDto.getbSPDtoList();
        //新建tt_balance_sales_part
        for(BalanceSalesPartDTO bSPDto:bSPDtoList){
            bSPDto.setBalanceId(balanceId);
            Long balanceSalesPartId=bSPService.addBalanceSalesPart(bSPDto);
            bSPDto.setBalanceSalesPartId(balanceSalesPartId);
        }
        //交车确认为否
        RepairOrderPO repair=RepairOrderPO.findById(baDto.getRoId());
        repair.setInteger("DELIVERY_TAG",DictCodeConstants.STATUS_IS_NOT);
        repair.saveIt();
        //重新计算金额放入结算单
        baPo=TtBalanceAccountsPO.findById(balanceId);
        setBAPO(baPo,baDto);
        baPo.saveIt();
//        roService.modifyRoStatus(baDto.getRoId(), "set");
        return baPo.getLongId();
    }
    
    /**
    * 设置baDto
    * @author ZhengHe
    * @date 2016年9月27日
    * @param baDto
    * @param roId
    */
    	
    public void setBADTO(BalanceAccountsDTO baDto,Long roId){
        StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,RO_ID,LABOUR_AMOUNT,REPAIR_PART_AMOUNT,SALES_PART_AMOUNT,");
        sqlsb.append("ADD_ITEM_AMOUNT,OVER_ITEM_AMOUNT,AMOUNT,DIS_LABOUR_AMOUNT,DIS_REPAIR_PART_AMOUNT,");
        sqlsb.append("DIS_SALES_PART_AMOUNT,DIS_ADD_ITEM_AMOUNT,DIS_OVER_ITEM_AMOUNT,DIS_REPAIR_AMOUNT,DIS_AMOUNT,");
        sqlsb.append("BALANCE_AMOUNT,RECEIVE_AMOUNT,SUB_OBB_AMOUNT,DERATE_AMOUNT ");
        sqlsb.append(" FROM tt_repair_order ");
        sqlsb.append(" WHERE 1=1 ");
        sqlsb.append(" AND RO_ID=?");
        List<Long> queryParam=new ArrayList<Long>();
        queryParam.add(roId);
        Map roMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
        baDto.setRoId(Long.parseLong(roMap.get("RO_ID").toString()));
        baDto.setLabourAmount(Double.valueOf(roMap.get("LABOUR_AMOUNT").toString()));
        baDto.setRepairPartAmount(Double.valueOf(roMap.get("REPAIR_PART_AMOUNT")+""));
        baDto.setSalesPartAmount(Double.valueOf(roMap.get("SALES_PART_AMOUNT").toString()));
        baDto.setAddItemAmount(Double.valueOf(roMap.get("ADD_ITEM_AMOUNT").toString()));
 //       baDto.setOverItemAmount(Double.valueOf(roMap.get("OVER_ITEM_AMOUNT").toString())); //辅料管理费目前不需要
        baDto.setRepairAmount(Double.valueOf(roMap.get("AMOUNT").toString()));
        baDto.setAmount(Double.valueOf(roMap.get("AMOUNT").toString()));
        baDto.setDisLabourAmount(Double.valueOf(roMap.get("DIS_LABOUR_AMOUNT").toString()));
        baDto.setDisRepairPartAmount(Double.valueOf(roMap.get("DIS_REPAIR_PART_AMOUNT").toString()));
        baDto.setDisSalesPartAmount(Double.valueOf(roMap.get("DIS_SALES_PART_AMOUNT").toString()));
        baDto.setDisAddItemAmount(Double.valueOf(roMap.get("DIS_ADD_ITEM_AMOUNT").toString()));
//        baDto.setDisOverItemAmount(Double.valueOf(roMap.get("DIS_OVER_ITEM_AMOUNT").toString()));//折扣后辅料管理费暂不需要
//        baDto.setDisRepairAmount(Double.valueOf(roMap.get("DIS_REPAIR_AMOUNT").toString()));
//        baDto.setDisAmount(Double.valueOf(roMap.get("DIS_AMOUNT").toString()));
    }
    
    
    /**
    * 获取收费区分及各个子表数据
    * @author ZhengHe
    * @date 2016年10月9日
    * @param baDto
    * @param roId
    */
    	
    public boolean getChargePartitionCode(BalanceAccountsDTO baDto,Long roId){
        List<Object> queryParam=new ArrayList<Object>();
        StringBuffer sqlsb=new StringBuffer("SELECT CHARGE_PARTITION_CODE,RO_ID,DEALER_CODE FROM tt_ro_add_item WHERE RO_ID=? ");
        queryParam.add(roId);
        sqlsb.append("UNION ALL SELECT CHARGE_PARTITION_CODE,RO_ID,DEALER_CODE FROM tt_ro_labour WHERE RO_ID=? ");
        queryParam.add(roId);
        sqlsb.append("UNION ALL SELECT CHARGE_PARTITION_CODE,RO_ID,DEALER_CODE FROM tt_ro_part WHERE RO_ID=? ");
        queryParam.add(roId);
        List<Map> chargePartitionCodeList=DAOUtil.findAll(sqlsb.toString(), queryParam);
        for(Map cPCMap:chargePartitionCodeList){
            if(StringUtils.isEquals(cPCMap.get("CHARGE_PARTITION_CODE"),"R")){
                return true;
            }
        }
        return false;
    }
    
    /**
    * 金额计算
    * @author ZhengHe
    * @date 2016年10月9日
    * @param baDto
    */
    	
    public void countAmount(BalanceAccountsDTO baDto){
        //优惠率不统一时
        if(baDto.getDiscountModeCode()==null){
            countLabourAmount(baDto);
            countPartAmount(baDto);
            countAddItemAmount(baDto);
            countSalesPart(baDto);
        }else{
            Double labourAmountDiscount=baDto.getLabourAmountDiscount();//优惠率
            countLabourAmount(baDto,labourAmountDiscount.toString());
            countPartAmount(baDto,labourAmountDiscount.toString());
            countAddItemAmount(baDto,labourAmountDiscount.toString());
            countSalesPart(baDto,labourAmountDiscount.toString());
        }
    }
    
    
    /**
    * 设置tt_balance_labour信息
    * @author ZhengHe
    * @date 2016年10月10日
    * @param baDto
    * @param labourAmountDiscount
    */
    	
    public void countLabourAmount(BalanceAccountsDTO baDto, String... labourAmountDiscount){
        StringBuffer sqlsb=new StringBuffer("SELECT RO_LABOUR_ID,DEALER_CODE,RO_ID,RO_NO,REPAIR_TYPE_CODE,CHARGE_PARTITION_CODE,LABOUR_CODE,LABOUR_NAME,");
        sqlsb.append("LOCAL_LABOUR_CODE,LOCAL_LABOUR_NAME,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,LABOUR_PRICE,LABOUR_AMOUNT,DISCOUNT,AFTER_DISCOUNT_AMOUNT,");
        sqlsb.append("TROUBLE_DESC,TECHNICIAN,WORKER_TYPE_CODE,REMARK,ASSIGN_TAG,ACTIVITY_CODE,PACKAGE_CODE,MODEL_LABOUR_CODE ");
        sqlsb.append(" from tt_ro_labour where 1=1");
        sqlsb.append(" AND RO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(baDto.getRoId());
        List<Map>labourList=DAOUtil.findAll(sqlsb.toString(), queryParam);
        List<BalanceLabourDTO> bLDtoList=baDto.getbLDtoList();
        int bLDtoListSize=1;
        if(bLDtoList==null){
            bLDtoList=new ArrayList<BalanceLabourDTO>();
            bLDtoListSize=0;
        }
        int i=0;
        Double disLabourAmount=new Double("0");
        for(Map labourMap:labourList){
            BalanceLabourDTO bLDto=new BalanceLabourDTO();
            if(bLDtoList.size()>0&&bLDtoListSize>0){
                bLDto=bLDtoList.get(i);
            }
            bLDto.setRoLabourId(Long.valueOf(labourMap.get("RO_LABOUR_ID").toString()));
            bLDto.setRepairTypeCode(Integer.parseInt(labourMap.get("REPAIR_TYPE_CODE").toString()));
            bLDto.setChargePartitionCode(labourMap.get("CHARGE_PARTITION_CODE")==null?"":labourMap.get("CHARGE_PARTITION_CODE").toString());
            bLDto.setLabourCode(labourMap.get("LABOUR_CODE").toString());
            bLDto.setLabourName(labourMap.get("LABOUR_NAME").toString());
            bLDto.setLocalLabourCode(ObjectToObject(labourMap.get("LOCAL_LABOUR_CODE")));
            bLDto.setLocalLabourName(ObjectToObject(labourMap.get("LOCAL_LABOUR_NAME")));
            bLDto.setStdLabourHour(ObjectToDouble(labourMap.get("STD_LABOUR_HOUR").toString()));
            bLDto.setAssignLabourHour(Double.parseDouble(labourMap.get("ASSIGN_LABOUR_HOUR").toString()));
            bLDto.setLabourPrice(Double.parseDouble(labourMap.get("LABOUR_PRICE").toString()));
            Double labourAmount=Double.parseDouble(labourMap.get("LABOUR_AMOUNT").toString());
            bLDto.setLabourAmount(labourAmount);
            bLDto.setTroubleDesc(labourMap.get("TROUBLE_DESC")+"");
            bLDto.setTechnician(labourMap.get("TECHNICIAN")+"");
            bLDto.setWorkerTypeCode(labourMap.get("WORKER_TYPE_CODE")+"");
            bLDto.setRemark(labourMap.get("REMARK")+"");
            bLDto.setAssignTag(Integer.parseInt(labourMap.get("ASSIGN_TAG")==null?"0":labourMap.get("ASSIGN_TAG")+"")==0?null:Integer.parseInt(labourMap.get("ASSIGN_TAG")==null?"0":labourMap.get("ASSIGN_TAG")+""));
            bLDto.setActivityCode(labourMap.get("ACTIVITY_CODE")+"");
            bLDto.setPackageCode(labourMap.get("PACKAGE_CODE")+"");
            bLDto.setModelLabourCode(labourMap.get("MODEL_LABOUR_CODE")+"");
            if(labourAmountDiscount.length>0){
                if(labourAmountDiscount[0]!=""||labourAmountDiscount[0]!=null){
                    Double disCount=Double.parseDouble(labourAmountDiscount[0]);
                    bLDto.setDiscount(disCount);
                    Double afterDiscountAmount =Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(labourAmount),BigDecimal.valueOf(disCount)).toString());
                    disLabourAmount=Double.valueOf(NumberUtil.add(BigDecimal.valueOf(disLabourAmount),BigDecimal.valueOf(afterDiscountAmount)).toString());
                    bLDto.setAfterDiscountAmount(afterDiscountAmount);
                }  
            }else{
       
                Double disCount=bLDto.getDiscount();
                bLDto.setDiscount(disCount);
                Double afterDiscountAmount =Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(labourAmount),BigDecimal.valueOf(disCount)).toString());
                disLabourAmount=Double.valueOf(NumberUtil.add(BigDecimal.valueOf(disLabourAmount),BigDecimal.valueOf(afterDiscountAmount)).toString());
                bLDto.setAfterDiscountAmount(afterDiscountAmount);
            } 
            if(bLDtoList.size()==0){
                bLDtoList.add(bLDto);
            }
            i++;
        }
        baDto.setbLDtoList(bLDtoList);
        //计算折扣后工时费
        baDto.setDisLabourAmount(disLabourAmount);
    }
    
    
    /**
    * 设置tt_balance_repair_part信息
    * @author ZhengHe
    * @date 2016年10月10日
    * @param baDto
    * @param labourAmountDiscount
    */
    	
    public void countPartAmount(BalanceAccountsDTO baDto, String... labourAmountDiscount){
        StringBuffer sqlsb=new StringBuffer("SELECT RO_PART_ID,RO_ID,RO_LABOUR_ID,DEALER_CODE,OUT_STOCK_NO,STORAGE_CODE,");
        sqlsb.append("STORAGE_POSITION_CODE,PART_NO,PART_NAME,CHARGE_PARTITION_CODE,UNIT,IS_MAIN_PART,PART_QUANTITY, ");
        sqlsb.append("PRICE_RATE,PART_COST_PRICE,PART_COST_AMOUNT,PART_SALES_PRICE,PART_SALES_AMOUNT,DISCOUNT,AFTER_DISCOUNT_AMOUNT,SENDER,");
        sqlsb.append("RECEIVER,BATCH_NO,IS_FINISHED,SEND_TIME,ACTIVITY_CODE ");
        sqlsb.append(" from tt_ro_part where 1=1 ");
        sqlsb.append(" AND RO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(baDto.getRoId());
        List<Map>partList=DAOUtil.findAll(sqlsb.toString(), queryParam);
        List<BalanceRepairPartDTO> bRPDtoList=baDto.getbRPDtoList();
        int bRPDtoListSize=1;
        if(bRPDtoList==null){
            bRPDtoList=new ArrayList<BalanceRepairPartDTO>();
            bRPDtoListSize=0;
        }
        int i=0;
        Double disRepairPartAmount=new Double("0");
        for(Map partMap: partList){
            BalanceRepairPartDTO bRPDto=new BalanceRepairPartDTO();
            if(bRPDtoList.size()>0&&bRPDtoListSize>0){
               bRPDto= bRPDtoList.get(i);
            }
            bRPDto.setLabourId(Long.parseLong(partMap.get("RO_LABOUR_ID").toString()));
            bRPDto.setStorageCode(partMap.get("STORAGE_CODE")+"");
            bRPDto.setStoragePositionCode(partMap.get("STORAGE_POSITION_CODE")+"");
            bRPDto.setPartNo(partMap.get("PART_NO")+"");
            bRPDto.setPartName(partMap.get("PART_NAME").toString());
            bRPDto.setChargePartitionCode(partMap.get("CHARGE_PARTITION_CODE")+"");
            bRPDto.setUnit(partMap.get("UNIT")+"");
            bRPDto.setIsMainPart(Integer.parseInt(partMap.get("IS_MAIN_PART")==null?"0":partMap.get("IS_MAIN_PART")+"")==0?null:Integer.parseInt(partMap.get("IS_MAIN_PART")==null?"0":partMap.get("IS_MAIN_PART")+""));
            bRPDto.setPartQuantity(ObjectToDouble(partMap.get("PART_QUANTITY")));
            bRPDto.setPriceRate(ObjectToDouble(partMap.get("PRICE_RATE")));
            bRPDto.setPartCostPrice(ObjectToDouble(partMap.get("PART_COST_PRICE")));
            Double partCostAmount= ObjectToDouble(partMap.get("PART_COST_AMOUNT"));
            bRPDto.setPartCostAmount(partCostAmount);
            bRPDto.setPartSalesPrice(ObjectToDouble(partMap.get("PART_SALES_PRICE")));
            Double partSalesAmount=ObjectToDouble(partMap.get("PART_SALES_AMOUNT"));
            bRPDto.setPartSalesAmount(partSalesAmount);
            if(labourAmountDiscount.length>0){
                if(labourAmountDiscount[0]!=""||labourAmountDiscount[0]!=null){
                    Double disCount=Double.parseDouble(labourAmountDiscount[0]);
                    bRPDto.setDiscount(disCount);
                    Double afterDiscountAmount =Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(partSalesAmount==null?0:partSalesAmount),BigDecimal.valueOf(disCount)).toString());
                    disRepairPartAmount=Double.valueOf(NumberUtil.add(BigDecimal.valueOf(disRepairPartAmount),BigDecimal.valueOf(afterDiscountAmount)).toString());
                    bRPDto.setAfterDiscountAmount(afterDiscountAmount);
                }  
            }else{
                Double disCount=bRPDto.getDiscount();
                bRPDto.setDiscount(disCount);
                Double afterDiscountAmount =new Double("0");
                if(disCount==null){
                    afterDiscountAmount=partSalesAmount;
                }else if(partSalesAmount==null){
                    afterDiscountAmount=partSalesAmount;
                }else{
                    afterDiscountAmount= Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(partSalesAmount),BigDecimal.valueOf(disCount)).toString());
                }
                if(afterDiscountAmount==null){
                }else{
                    disRepairPartAmount=Double.valueOf(NumberUtil.add(BigDecimal.valueOf(disRepairPartAmount),BigDecimal.valueOf(afterDiscountAmount)).toString());
                }
                bRPDto.setAfterDiscountAmount(ObjectToDouble(afterDiscountAmount));
            } 
            if(bRPDtoList.size()==0){
                bRPDtoList.add(bRPDto);
            }
            i++;
        }
        baDto.setbRPDtoList(bRPDtoList);
        //计算折扣后维修材料费
        baDto.setDisRepairPartAmount(disRepairPartAmount);
    }
    
    
    /**
    * 设置tt_balance_add_item信息
    * @author ZhengHe
    * @date 2016年10月10日
    * @param baDto
    * @param labourAmountDiscount
    */
    	
    public void countAddItemAmount(BalanceAccountsDTO baDto, String... labourAmountDiscount){
        StringBuffer sqlsb=new StringBuffer("SELECT RO_ADD_ITEM_ID,RO_ID,DEALER_CODE,CHARGE_PARTITION_CODE,ADD_ITEM_CODE,");
        sqlsb.append("ADD_ITEM_NAME,ADD_ITEM_AMOUNT,RECEIVABLE_AMOUNT,REMARK ");
        sqlsb.append(" from tt_ro_add_item where 1=1");
        sqlsb.append(" AND RO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(baDto.getRoId());
        List<Map>addItemList=DAOUtil.findAll(sqlsb.toString(), queryParam);
        List<BalanceAddItemDTO> bAIDtoList=baDto.getbAIDtoList();
        int bAIDtoListSize=1;
        if(bAIDtoList==null){
            bAIDtoList=new ArrayList<BalanceAddItemDTO>();
            bAIDtoListSize=0;
        }
        int i=0;
        Double disAddItemAmount=new Double("0");
        for(Map addItemMap:addItemList){
           BalanceAddItemDTO bAIDto=new BalanceAddItemDTO();
           if(bAIDtoList.size()>0&&bAIDtoListSize>0){
               bAIDto=bAIDtoList.get(i);
           }
           bAIDto.setRoAddItemId(Long.parseLong(addItemMap.get("RO_ADD_ITEM_ID").toString()));
           bAIDto.setChargePartitionCode(ObjectToObject(addItemMap.get("CHARGE_PARTITION_CODE")));
           bAIDto.setAddItemCode(addItemMap.get("ADD_ITEM_CODE").toString());
           bAIDto.setAddItemName(addItemMap.get("ADD_ITEM_NAME").toString());
           bAIDto.setAddItemAmount(Double.parseDouble(addItemMap.get("ADD_ITEM_AMOUNT").toString()));
           Double receivableAmount=Double.parseDouble(addItemMap.get("RECEIVABLE_AMOUNT").toString());
           bAIDto.setReceivableAmount(receivableAmount);
           if(!StringUtils.isNullOrEmpty(addItemMap.get("REMARK"))){
        	   bAIDto.setRemark(addItemMap.get("REMARK").toString());
           }
         
           disAddItemAmount=Double.valueOf(NumberUtil.add(BigDecimal.valueOf(disAddItemAmount),BigDecimal.valueOf(receivableAmount)).toString());
           if(bAIDtoList.size()==0){
               bAIDtoList.add(bAIDto);
           }
           i++;
       }
        baDto.setbAIDtoList(bAIDtoList);
        //计算折扣后附加项目费
        baDto.setDisAddItemAmount(disAddItemAmount);
    }
    
    public void countSalesPart(BalanceAccountsDTO baDto, String... labourAmountDiscount){
        StringBuffer sqlsb=new StringBuffer("SELECT   tpsi.ITEM_ID, tpsi.SALES_PART_ID,tpsi.DEALER_CODE,tpsi.STORAGE_CODE, tpsi.STORAGE_POSITION_CODE,")
                .append(" tpsi.PART_NO,tpsi.PART_NAME, tpsi.PART_QUANTITY, tpsi.UNIT, tpsi.DISCOUNT, tpsi.PRICE_TYPE,  tpsi.PRICE_RATE,  tpsi.COST_PRICE,")
                .append(" tpsi.PART_SALES_PRICE,  tpsi.PART_COST_AMOUNT,tpsi.PART_SALES_AMOUNT,tpsi.SALES_AMOUNT,tps.RO_NO, tro.RO_ID, ")
                .append(" ts.STORAGE_NAME")
                .append(" FROM tt_part_sales_item tpsi ")
                .append(" LEFT JOIN tt_part_sales tps  ON tps.DEALER_CODE = tpsi.DEALER_CODE  AND tps.SALES_PART_ID = tpsi.SALES_PART_ID  ")
                .append("  LEFT JOIN tt_repair_order tro   ON tro.DEALER_CODE = tps.DEALER_CODE AND tro.RO_NO = tps.RO_NO  ")
                .append(" LEFT JOIN tm_store ts ON ts.DEALER_CODE=tpsi.DEALER_CODE AND ts.STORAGE_CODE=tpsi.STORAGE_CODE ")
                .append(" WHERE 1 = 1 AND tro.RO_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(baDto.getRoId());
        List<Map>salesPartList=DAOUtil.findAll(sqlsb.toString(), queryParam);
        List<BalanceSalesPartDTO> bSPDtoList=baDto.getbSPDtoList();
        int salesPartListSize=1;
        if(bSPDtoList==null){
            bSPDtoList=new ArrayList<BalanceSalesPartDTO>();
            salesPartListSize=0;
        }
        int i=0;
        Double disSalesAmount=new Double("0");
        for(Map salesPartMap:salesPartList){
            BalanceSalesPartDTO bSPDto=new BalanceSalesPartDTO();
            if(bSPDtoList.size()>0&&salesPartListSize>0){
                bSPDto= bSPDtoList.get(i);
             }
            bSPDto.setItemId(Long.parseLong(salesPartMap.get("ITEM_ID").toString()));
            bSPDto.setSalesPartId(Long.parseLong(salesPartMap.get("SALES_PART_ID").toString()));
            bSPDto.setStorageCode(salesPartMap.get("STORAGE_CODE")+"");
            bSPDto.setStoragePositionCode(salesPartMap.get("STORAGE_POSITION_CODE")+"");
            bSPDto.setPartNo(salesPartMap.get("PART_NO")+"");
            bSPDto.setPartName(salesPartMap.get("PART_NAME")+"");
            bSPDto.setPartQuantity(ObjectToDouble(salesPartMap.get("PART_QUANTITY")));
            bSPDto.setUnit(salesPartMap.get("UNIT")+"");
            bSPDto.setDiscount(ObjectToDouble(salesPartMap.get("DISCOUNT")));
            bSPDto.setPriceType(Integer.parseInt(salesPartMap.get("PRICE_TYPE")+""));
            bSPDto.setPriceRate(ObjectToDouble(salesPartMap.get("PRICE_RATE")));
            bSPDto.setCostPrice(ObjectToDouble(salesPartMap.get("COST_PRICE")));
            bSPDto.setPartSalesPrice(ObjectToDouble(salesPartMap.get("PART_SALES_PRICE")));
            bSPDto.setPartCostAmount(ObjectToDouble(salesPartMap.get("PART_COST_AMOUNT")));
            Double partSalesAmount=ObjectToDouble(salesPartMap.get("PART_SALES_AMOUNT"));
            if(labourAmountDiscount.length>0){
                if(labourAmountDiscount[0]!=""||labourAmountDiscount[0]!=null){
                    Double disCount=Double.parseDouble(labourAmountDiscount[0]);
                    bSPDto.setDiscount(disCount);
                    Double afterDiscountAmount =NumberUtil.mul2Double(partSalesAmount, disCount);
                    disSalesAmount=NumberUtil.add2Double(disSalesAmount,afterDiscountAmount);
                    bSPDto.setSalesAmount(afterDiscountAmount);
                }
            }else{
                Double disCount=bSPDto.getDiscount();
                bSPDto.setDiscount(disCount);
                Double afterDiscountAmount =new Double("0");
                if(disCount==null){
                    afterDiscountAmount=partSalesAmount;
                }else if(partSalesAmount==null){
                    afterDiscountAmount=partSalesAmount;
                }else{
                    afterDiscountAmount= Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(partSalesAmount),BigDecimal.valueOf(disCount)).toString());
                }
                if(afterDiscountAmount==null){
                }else{
                    disSalesAmount=NumberUtil.add2Double(disSalesAmount,afterDiscountAmount);
                }
                bSPDto.setSalesAmount(afterDiscountAmount);
            } 
            if(bSPDtoList.size()==0){
                bSPDtoList.add(bSPDto);
            }
            i++;
        }
        baDto.setbSPDtoList(bSPDtoList);
        //计算折扣后维修材料费
        baDto.setDisSalesPartAmount(disSalesAmount);
    }
    
    /**
    * 设置tt_balance_payobj信息
    * @author ZhengHe
    * @date 2016年10月11日
    * @param baDto
    */
    	
    public void countPayObjAmount(BalanceAccountsDTO baDto,String...isOem){

        List<BalancePayobjDTO> bPODtoList=baDto.getbPODtoList();
        int i=0;
        Double subObbAmountAll=new Double("0");
        Double balanceAmount=new Double("0");
        for(BalancePayobjDTO bPODto:bPODtoList){
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(bPODto.getPaymentObjectCode());
            if(i==0){
                StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,OWNER_ID,OWNER_NO,OWNER_PROPERTY,OWNER_NAME FROM tm_owner WHERE 1=1 ");
                sqlsb.append(" and OWNER_NO=?");
                Map bPOMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
                bPODto.setPaymentObjectType(DictCodeConstants.VEHICLE_TYPE);
                bPODto.setPaymentObjectName(bPOMap.get("OWNER_NAME").toString());
                bPODto.setPaymentObjectCode(bPOMap.get("OWNER_NO").toString());
                Double disAmount=bPODto.getDisAmount();
                bPODto.setDisAmount(disAmount);
                Double subObbAmount=bPODto.getSubObbAmount();
                if(subObbAmount==null){
                    subObbAmount=new Double(0);
                }
                subObbAmountAll=subObbAmountAll+subObbAmount;
                bPODto.setSubObbAmount(subObbAmount);
                Double receivableAmount=Double.valueOf(NumberUtil.sub(BigDecimal.valueOf(disAmount), BigDecimal.valueOf(subObbAmount)).toString());
                bPODto.setReceivableAmount(receivableAmount);
                balanceAmount=balanceAmount+receivableAmount;
            }else{
                StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,PART_CUSTOMER_ID,CUSTOMER_CODE,CUSTOMER_NAME,CUSTOMER_SHORT_NAME from tm_part_customer where 1=1 ");
                sqlsb.append(" and CUSTOMER_CODE=?");
                Map bPOMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
                bPODto.setPaymentObjectType(DictCodeConstants.CUSTOMER_TYPE);
                bPODto.setPaymentObjectName(bPOMap.get("CUSTOMER_NAME").toString());
                bPODto.setPaymentObjectCode(bPOMap.get("CUSTOMER_CODE").toString());
                Double disAmount=bPODto.getDisAmount();
                bPODto.setDisAmount(disAmount);
                Double subObbAmount=bPODto.getSubObbAmount();
                subObbAmountAll=subObbAmountAll+subObbAmount;
                bPODto.setSubObbAmount(subObbAmount);
                Double receivableAmount=Double.valueOf(NumberUtil.sub(BigDecimal.valueOf(disAmount), BigDecimal.valueOf(subObbAmount)).toString());
                bPODto.setReceivableAmount(receivableAmount);
                balanceAmount=balanceAmount+receivableAmount;
            }
            i++;
        }
        //含有索赔的情况下
        if(isOem.length>0){
            if(isOem[0]!=""||isOem[0]!=null){
                BalancePayobjDTO bPODto=new BalancePayobjDTO();
                List<Object> queryParam=new ArrayList<Object>();
                StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,PART_CUSTOMER_ID,CUSTOMER_CODE,CUSTOMER_NAME,CUSTOMER_SHORT_NAME from tm_part_customer where 1=1 ");
                sqlsb.append(" and OEM_TAG=?");
                queryParam.add(DictCodeConstants.STATUS_IS_YES);
                sqlsb.append(" and IS_VALID=?");
                queryParam.add(DictCodeConstants.STATUS_IS_YES);
                Map bPOMap=DAOUtil.findFirst(sqlsb.toString(), queryParam);
                bPODto.setPaymentObjectType(DictCodeConstants.CUSTOMER_TYPE);
                bPODto.setPaymentObjectName(bPOMap.get("CUSTOMER_NAME").toString());
                bPODto.setPaymentObjectCode(bPOMap.get("CUSTOMER_CODE").toString());
                //计算索赔金额
                Double disAmount=new Double("0");
                for(BalanceLabourDTO bLDto:baDto.getbLDtoList()){
                    if(StringUtils.isEquals(bLDto.getChargePartitionCode(),"R")){
                        disAmount=disAmount+bLDto.getAfterDiscountAmount();
                    }
                }
                
                for(BalanceRepairPartDTO bRPDto:baDto.getbRPDtoList()){
                    if(StringUtils.isEquals(bRPDto.getChargePartitionCode(),"R")){
                        disAmount=disAmount+bRPDto.getAfterDiscountAmount();
                    }
                }
                
                for(BalanceAddItemDTO bAIDto:baDto.getbAIDtoList()){
                    if(StringUtils.isEquals(bAIDto.getChargePartitionCode(),"R")){
                        disAmount=disAmount+bAIDto.getAddItemAmount();
                    }
                }
                bPODto.setDisAmount(disAmount);
                bPODto.setReceivableAmount(disAmount);
                bPODtoList.add(bPODto);
            }
            baDto.setIsHasClaim(DictCodeConstants.STATUS_IS_YES);
         }else{
             baDto.setIsHasClaim(DictCodeConstants.STATUS_IS_NOT);
         }
        
        //计算去零金额
        baDto.setSubObbAmount(subObbAmountAll);
      //计算结算金额
        baDto.setBalanceAmount(balanceAmount);
    }
   
    
    /**
    * 设置BAPO
    * @author ZhengHe
    * @date 2016年9月27日
    * @param baPo
    */
    	
    public void setBAPO(TtBalanceAccountsPO baPo,BalanceAccountsDTO baDto){
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        baPo.setString("BALANCE_NO", baDto.getBalanceNo());
        baPo.setLong("RO_ID", baDto.getRoId());
        baPo.setLong("SALES_PART_ID", baDto.getSalesPartId());
        baPo.setLong("ALLOCATE_OUT_ID", baDto.getAllocateOutId());
        baPo.setString("DISCOUNT_MODE_CODE", baDto.getDiscountModeCode());
        baPo.setString("REMARK", baDto.getRemark());
        baPo.setDouble("LABOUR_AMOUNT", baDto.getLabourAmount());
        baPo.setDouble("REPAIR_PART_AMOUNT", baDto.getRepairPartAmount());
        baPo.setDouble("SALES_PART_AMOUNT",baDto.getSalesPartAmount() );
        baPo.setDouble("ADD_ITEM_AMOUNT",baDto.getAddItemAmount() );
        baPo.setDouble("OVER_ITEM_AMOUNT",baDto.getOverItemAmount() );
        baPo.setDouble("REPAIR_AMOUNT",baDto.getRepairAmount() );
        baPo.setDouble("AMOUNT",baDto.getAmount() );
        baPo.setDouble("DIS_LABOUR_AMOUNT",baDto.getDisLabourAmount() );
        baPo.setDouble("DIS_REPAIR_PART_AMOUNT",baDto.getDisRepairPartAmount() );
        baPo.setDouble("DIS_SALES_PART_AMOUNT",baDto.getDisSalesPartAmount() );
        baPo.setDouble("DIS_ADD_ITEM_AMOUNT",baDto.getDisAddItemAmount() );
        baPo.setDouble("DIS_OVER_ITEM_AMOUNT",baDto.getDisOverItemAmount() );
        baPo.setDouble("DIS_REPAIR_AMOUNT",baDto.getDisRepairAmount() );
        baPo.setDouble("DIS_AMOUNT",baDto.getDisAmount() );
        baPo.setDouble("BALANCE_AMOUNT",baDto.getBalanceAmount() );
        baPo.setDouble("RECEIVE_AMOUNT",baDto.getReceiveAmount() );
        baPo.setDouble("SUB_OBB_AMOUNT",baDto.getSubObbAmount() );
        baPo.setDouble("DERATE_AMOUNT",baDto.getDerateAmount() );
        baPo.setDouble("TAX",baDto.getTax() );
        baPo.setDouble("TAX_AMOUNT",baDto.getTaxAmount() );
        baPo.setDouble("NET_AMOUNT",baDto.getNetAmount() );
        baPo.setInteger("PAY_OFF", baDto.getPayOffMain());
        baPo.setString("BALANCE_HANDLER",loginInfo.getEmployeeNo());
        baPo.setInteger("IS_RED", DictCodeConstants.STATUS_IS_NOT);
        baPo.setTimestamp("BALANCE_TIME", new Date());
        //baPo.setTimestamp("SQUARE_DATE",new Date()); //结清时间
        baPo.setInteger("ARR_BALANCE", baDto.getArrBalance());
        baPo.setTimestamp("PRINT_BALANCE_TIME", baDto.getPrintBalanceTime());
        baPo.setInteger("IS_HAS_CLAIM", baDto.getIsHasClaim());
    }

    
    /**
     * 维修收款  查询
    * @author jcsi
    * @date 2016年9月27日
    * @param param
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#search(java.util.Map)
     */
    @Override
    public PageInfoDto search(Map<String, String> param) {      
        List<Object> queryParam=new ArrayList<Object>();      
        return DAOUtil.pageQuery(getQuerySql(param,queryParam), queryParam);
    }

    /**
     * 拼接sql
     * @author jcsi
     * @date 2016年9月27日
     * @param param
     * @param queryParam
     * @return
      */
     public String getQuerySql(Map<String, String> param,List<Object> queryParam){
         StringBuilder  sb=new StringBuilder();
       //业务类型
         int businessType=DictCodeConstants.RO_TYPE_REPAIR;  //维修
         if(param.get("businessType").equals(DictCodeConstants.RO_TYPE_SALES+"")){
             businessType=DictCodeConstants.RO_TYPE_SALES;//销售
         }else if(param.get("businessType").equals(DictCodeConstants.RO_TYPE_TRANSFERS+"")){
             businessType=DictCodeConstants.RO_TYPE_TRANSFERS;//调拨
         }
         sb.append("select * from (");
         sb.append("SELECT  tba.IS_RED,tba.BALANCE_ID,tbp.BALA_PAYOBJ_ID,tba.DEALER_CODE,tba.BALANCE_NO,tbp.PAYMENT_OBJECT_CODE,tbp.PAYMENT_OBJECT_NAME,");
         sb.append("tbp.RECEIVABLE_AMOUNT,tbp.RECEIVED_AMOUNT,tbp.NOT_RECEIVED_AMOUNT,tba.BALANCE_TIME,tba.PAY_OFF,");
         sb.append("tba.SQUARE_DATE,tbp.DERATE_AMOUNT, ");
         sb.append(businessType+" as BUSINESS_TYPE, ");
         if(param.get("businessType").equals(DictCodeConstants.RO_TYPE_REPAIR+"")){
             sb.append("  tro.RO_NO ");
         }else if(param.get("businessType").equals(DictCodeConstants.RO_TYPE_SALES+"")){
             sb.append("  tps.SALES_PART_NO  ");
         }else if(param.get("businessType").equals(DictCodeConstants.RO_TYPE_TRANSFERS+"")){
             sb.append("  tpo.ALLOCATE_OUT_NO  ");
         }
         sb.append("as BUSINESS_NO  ");
         sb.append("from TT_BALANCE_ACCOUNTS tba  ");
         sb.append("left join TT_BALANCE_PAYOBJ tbp on tba.BALANCE_ID=tbp.BALANCE_ID  ");
         if(businessType==DictCodeConstants.RO_TYPE_REPAIR){
             sb.append("inner join TT_REPAIR_ORDER  tro on  tba.RO_ID=tro.RO_ID  ");    
         }else if(businessType==DictCodeConstants.RO_TYPE_SALES){
             sb.append("inner join TT_PART_SALES tps on tba.SALES_PART_ID=tps.SALES_PART_ID ");
         }else if(businessType==DictCodeConstants.RO_TYPE_TRANSFERS){
             sb.append("inner join TT_PART_ALLOCATE_OUT tpo on tba.ALLOCATE_OUT_ID=tpo.ALLOCATE_OUT_ID ");
         }
         
         //sb.append("left join TM_VEHICLE tv on  tv.VEHICLE_ID=tro.VEHICLE_ID ");
         
         
         //sb.append(" left join TM_OWNER  tmo on  tro.OWNER_ID=tmo.OWNER_ID  ");
        // sb.append(" left join TT_CHARGE_DERATE tcd on tcd.BALA_PAYOBJ_ID=tbp.BALA_PAYOBJ_ID ");
         sb.append(" ) t where   IS_RED <> ?");
         //queryParam.add(DictCodeConstants.ORDER_SETTLED);
         queryParam.add(DictCodeConstants.STATUS_IS_YES);
         if(!StringUtils.isNullOrEmpty(param.get("businessNo"))){
             sb.append(" and BUSINESS_NO=? ");
             queryParam.add(param.get("businessNo"));
         }
         if(!StringUtils.isNullOrEmpty(param.get("balanceFrom"))){
             sb.append(" and BALANCE_TIME >= ?");
             queryParam.add(DateUtil.parseDefaultDate(param.get("balanceFrom")));
         }
         if(!StringUtils.isNullOrEmpty(param.get("balanceTo"))){
             sb.append(" and BALANCE_TIME < ?");
             queryParam.add(DateUtil.addOneDay(param.get("balanceTo")));
         }
         if(!StringUtils.isNullOrEmpty(param.get("balanceNo"))){
             sb.append(" and BALANCE_NO  like ? ");
             queryParam.add("%"+param.get("balanceNo")+"%");
         }
         if(!StringUtils.isNullOrEmpty(param.get("paymentObjectName"))){
             sb.append(" and PAYMENT_OBJECT_NAME like ? ");
             queryParam.add("%"+param.get("paymentObjectName")+"%");
         }
         if(!StringUtils.isNullOrEmpty(param.get("squareFrom"))){
             sb.append(" and SQUARE_DATE >= ?");
             queryParam.add(DateUtil.parseDefaultDate(param.get("squareFrom")));
         }
         if(!StringUtils.isNullOrEmpty(param.get("squareTo"))){
             sb.append(" and SQUARE_DATE < ?");
             queryParam.add(DateUtil.addOneDay(param.get("squareTo")));
         }
         if(!StringUtils.isNullOrEmpty(param.get("ownerName"))){
             sb.append(" and OWNER_NAME like ? ");
             queryParam.add("%"+param.get("ownerName")+"%");
         }
         if(!StringUtils.isNullOrEmpty(param.get("license"))){
             sb.append(" and LICENSE like ? ");
             queryParam.add("%"+param.get("license")+"%");
         }
         if(!StringUtils.isNullOrEmpty(param.get("payOff"))){
             sb.append(" and PAY_OFF=? ");
             queryParam.add(param.get("payOff"));
         }
         if(!StringUtils.isNullOrEmpty(param.get("notReceivedAmount"))){
             if(param.get("notReceivedAmount").equals(DictCodeConstants.GREATER_ZERO+"")){
                 sb.append(" and NOT_RECEIVED_AMOUNT > 0");
                 
             }else if(param.get("notReceivedAmount").equals(DictCodeConstants.EQUAL_ZERO+"")){
                 sb.append(" and NOT_RECEIVED_AMOUNT = 0");
             }
         }    
         return sb.toString();
     }
     
     public void excelReceiveMoney(){
         
     }
    /**
     * 根据id查询结算单收费对象列表
    * @author jcsi
    * @date 2016年9月28日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#findBalancePayobjById(java.lang.Long)
     */
    @Override
    public List<Map> findBalancePayobjById(Long id) {
        //return BalancePayobjPO.findById(id).toMap();
        StringBuilder sb=new StringBuilder("SELECT t.DERATE_AMOUNT,t.BALA_PAYOBJ_ID,t.DEALER_CODE,b.BALANCE_NO,t.RECEIVABLE_AMOUNT,t.RECEIVED_AMOUNT,t.NOT_RECEIVED_AMOUNT,t.INVOICE_TAG,v.LICENSE,b.PAY_OFF   ");
        sb.append("from TT_BALANCE_PAYOBJ t left join TT_BALANCE_ACCOUNTS b on t.BALANCE_ID=b.BALANCE_ID  ");
        sb.append("left join TT_REPAIR_ORDER r on r.RO_ID=b.RO_ID  ");
        sb.append("left join  TM_VEHICLE v on r.VEHICLE_ID=v.VEHICLE_ID  ");
        sb.append("where t.BALA_PAYOBJ_ID=?");
        List<Object> param=new ArrayList<Object>();
        param.add(id);
       // Map<String,Object> result= DAOUtil.findFirst(sb.toString(), param);
        return DAOUtil.findAll(sb.toString(), param);
    }
    
    
    /**
    * 查询客户信息
    * @author ZhengHe
    * @date 2016年9月28日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryCustomerOrVehicle(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryCustomerOrVehicle(Map<String, String> param) throws ServiceBizException {
        String cOv=param.get("customerType");
        PageInfoDto pageInfoDto=null;
        if(StringUtils.isEquals(DictCodeConstants.CUSTOMER_TYPE,cOv)){
            StringBuffer sqlsb=new StringBuffer("select DEALER_CODE,PART_CUSTOMER_ID,CUSTOMER_CODE as OWNER_NO,CUSTOMER_NAME,CUSTOMER_SHORT_NAME,CUSTOMER_SPELL,CUSTOMER_TYPE_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,OEM_TAG,IS_VALID,");
            sqlsb.append(cOv+" as CUSTOMER_TYPE ");
            sqlsb.append(" from  tm_part_customer where 1=1 ");
            sqlsb.append(" and OEM_TAG=? ");
            List<Object> queryList = new ArrayList<Object>();
            queryList.add(DictCodeConstants.STATUS_IS_NOT);
            if (!StringUtils.isNullOrEmpty(param.get("customerName"))) {
                sqlsb.append(" and CUSTOMER_NAME like ?");
                queryList.add("%" + param.get("customerName") + "%");
            }
            pageInfoDto=DAOUtil.pageQuery(sqlsb.toString(), queryList);
        }
        if(StringUtils.isEquals(DictCodeConstants.VEHICLE_TYPE,cOv)){
            StringBuffer sqlsb=new StringBuffer("select tv.VEHICLE_ID,tv.VIN,tv.DEALER_CODE,tv.OWNER_ID,tv.LICENSE,tv.CONTACTOR_NAME,tv.CONTACTOR_MOBILE,");
            sqlsb.append("tow.OWNER_NO AS OWNER_NO,");
            sqlsb.append(cOv+" as CUSTOMER_TYPE ");
            sqlsb.append(" from tm_vehicle tv ");
            sqlsb.append(" left join tm_owner tow on tow.DEALER_CODE=tv.DEALER_CODE and tow.OWNER_ID=tv.OWNER_ID");
            sqlsb.append(" where 1=1 ");
            List<Object> queryList = new ArrayList<Object>();
            if (!StringUtils.isNullOrEmpty(param.get("license"))) {
                sqlsb.append(" and LICENSE like ?");
                queryList.add("%" + param.get("license") + "%");
            }
            pageInfoDto=DAOUtil.pageQuery(sqlsb.toString(), queryList);
        }
        return pageInfoDto;
    }

    /**
     * 导出
    * @author jcsi
    * @date 2016年10月9日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#excelReceiveMoney(java.util.Map)
     */
    @Override
    public List<Map> excelReceiveMoney(Map<String, String> param) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();
        String sql=getQuerySql(param,queryParam);
        return DAOUtil.findAll(sql, queryParam);
    }

    
    
    
    /**
    * Object转Double
    * @author ZhengHe
    * @date 2016年10月11日
    * @param param
    * @return
    */
    	
    public Double ObjectToDouble(Object param){
        return Double.parseDouble(param==null?"0":param.toString())==0?null:Double.parseDouble(param==null?"0":param.toString());
    }
    
    
    /**
    * Object转String
    * @author ZhengHe
    * @date 2016年10月11日
    * @param param
    * @return
    */
    	
    public String ObjectToObject(Object param){
        return param==null?null:param.toString();
    }


    
    /**
    * 查询结算单信息
    * @author ZhengHe
    * @date 2016年10月18日
    * @param param
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceAccounts(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryBalanceAccounts(Map<String, String> queryParam,String ...params) throws ServiceBizException {
        String businessType=queryParam.get("BUSINESS_TYPE");
        StringBuffer sqlsb=new StringBuffer("SELECT"); 
        sqlsb.append(" tba.BALANCE_ID,");
        sqlsb.append("tbp.BALA_PAYOBJ_ID,");                  
        sqlsb.append("tba.DEALER_CODE,"); 
        sqlsb.append(" tba.BALANCE_NO,"); 
        sqlsb.append(" tba.BALANCE_HANDLER,"); 
        sqlsb.append(" tbp.PAYMENT_OBJECT_CODE,"); 
        sqlsb.append(" tbp.PAYMENT_OBJECT_NAME,"); 
        sqlsb.append("tbp.RECEIVABLE_AMOUNT,"); 
        sqlsb.append("tbp.RECEIVED_AMOUNT,");
        sqlsb.append("tbp.NOT_RECEIVED_AMOUNT,");
        sqlsb.append(" tbp.INVOICE_TAG,");
        sqlsb.append("tba.BALANCE_TIME,");
        sqlsb.append("tba.PAY_OFF,");
        sqlsb.append(" tba.SQUARE_DATE,");
        sqlsb.append(" tba.DERATE_AMOUNT,");
        sqlsb.append(" tba.IS_RED, ");
        sqlsb.append(" tba.RECEIVE_AMOUNT,"); 
        sqlsb.append(businessType+" AS BUSINESS_TYPE,");
        //维修
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_REPAIR, businessType)){
            sqlsb.append(" tmo.OWNER_NAME,");
            sqlsb.append(" tv.LICENSE,");
            sqlsb.append("tro.RO_NO AS BUSINESS_NO,");
            sqlsb.append(" tro.RO_STATUS ");
        }
        //销售
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_SALES, businessType)){
            sqlsb.append("tps.SALES_PART_NO  AS BUSINESS_NO ");
        }
        //调拨
        if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_TRANSFERS, businessType)){
            sqlsb.append(" tpo.ALLOCATE_OUT_NO  AS BUSINESS_NO ");
        }
        sqlsb.append("FROM");
        sqlsb.append("    TT_BALANCE_ACCOUNTS tba ");
        sqlsb.append("    LEFT JOIN TT_BALANCE_PAYOBJ tbp ");
        sqlsb.append("ON tba.DEALER_CODE=tbp.DEALER_CODE and tba.BALANCE_ID = tbp.BALANCE_ID "); 
        
        sqlsb.append("LEFT JOIN TT_REPAIR_ORDER tro ");
        sqlsb.append("ON tba.DEALER_CODE=tro.DEALER_CODE and tba.RO_ID = tro.RO_ID  ");
        sqlsb.append("LEFT JOIN TM_VEHICLE tv ");
        sqlsb.append("ON tba.DEALER_CODE=tv.DEALER_CODE and tv.VEHICLE_ID = tro.VEHICLE_ID ");
        sqlsb.append("LEFT JOIN TM_OWNER tmo ");
        sqlsb.append("ON tba.DEALER_CODE=tmo.DEALER_CODE and tro.OWNER_ID = tmo.OWNER_ID ");

        sqlsb.append("LEFT JOIN TT_PART_SALES tps ");
        sqlsb.append("ON tba.DEALER_CODE=tps.DEALER_CODE and tba.SALES_PART_ID = tps.SALES_PART_ID  ");

        sqlsb.append("LEFT JOIN TT_PART_ALLOCATE_OUT tpo ");
        sqlsb.append(" ON tba.DEALER_CODE=tpo.DEALER_CODE and tba.ALLOCATE_OUT_ID = tpo.ALLOCATE_OUT_ID  ");
        sqlsb.append(" where 1=1 ");

        List<Object> queryList=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("BUSINESS_NO"))){
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_REPAIR, businessType)){
                sqlsb.append(" and tbp.PAYMENT_OBJECT_TYPE="+DictCodeConstants.CUSTOMER_OWNER);
                sqlsb.append(" and tro.RO_STATUS="+DictCodeConstants.SET_CREATE_STATUS);
                sqlsb.append(" and tro.RO_NO like ?");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
            //销售
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_SALES, businessType)){
                sqlsb.append(" and tps.SALES_PART_NO  like ? ");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
            //调拨
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_TRANSFERS, businessType)){
            	sqlsb.append(" and tbp.PAYMENT_OBJECT_TYPE="+DictCodeConstants.CUSTOMER_CONTACT);
                sqlsb.append(" and tpo.ALLOCATE_OUT_NO like ? ");
                queryList.add("%"+queryParam.get("BUSINESS_NO")+"%");
            }
        }else{
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_REPAIR, businessType)){
                sqlsb.append(" and tbp.PAYMENT_OBJECT_TYPE="+DictCodeConstants.CUSTOMER_OWNER);
                sqlsb.append(" and tba.RO_ID IS NOT NULL ");
            }
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_SALES, businessType)){
                sqlsb.append(" and tba.SALES_PART_ID  IS NOT NULL ");
            }
            if(StringUtils.isEquals(DictCodeConstants.RO_TYPE_TRANSFERS, businessType)){
            	sqlsb.append(" and tbp.PAYMENT_OBJECT_TYPE="+DictCodeConstants.CUSTOMER_CONTACT);
                sqlsb.append(" and tba.ALLOCATE_OUT_ID IS NOT NULL ");
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("LICENSE"))){
            sqlsb.append(" and tv.LICENSE like ?");
            queryList.add("%"+queryParam.get("LICENSE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_NO"))){
            sqlsb.append(" and tba.BALANCE_NO like ?");
            queryList.add("%"+queryParam.get("BALANCE_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_TIME_FROM"))){
            sqlsb.append(" and tba.BALANCE_TIME >=?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("BALANCE_TIME_FROM")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BALANCE_TIME_TO"))){
            sqlsb.append(" and tba.BALANCE_TIME <?");
            queryList.add(DateUtil.addOneDay(queryParam.get("BALANCE_TIME_TO")));
        }
        if(params.length>0){
            sqlsb.append(" and tba.IS_RED ="+DictCodeConstants.STATUS_IS_NOT);
        }
        PageInfoDto  pageInfoDto=DAOUtil.pageQuery(sqlsb.toString(), queryList);
        return pageInfoDto;
    }


    
    /**
    * 取消结算
    * @author ZhengHe
    * @date 2016年10月18日
    * @param baDto
    * @throws ServiceBizException
    */
    	
    @Override
    public Long cancelBalanceAccounts(BalanceAccountsDTO baDto) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        TtBalanceAccountsPO baPO=TtBalanceAccountsPO.findById(baDto.getBalanceId());
        if(baPO.getDouble("RECEIVE_AMOUNT")>0){
        	 throw new ServiceBizException("该结算单已收款，不允许取消结算!");
        }
        baPO.setInteger("IS_RED",DictCodeConstants.STATUS_IS_YES);
        baPO.setDate("RETURNED_BALANCE_TIME", new Date());
        baPO.setString("BALANCE_HANDLER",loginInfo.getEmployeeNo());
        baPO.saveIt();
        operateLogService.recordOperateLog("取消结算单：【"+baPO.get("BALANCE_NO")+"】",DictCodeConstants.LOG_REPAIR_MANAGEMENT);
        Long roId=baPO.getLong("RO_ID");
        Long salesPartId=baPO.getLong("SALES_PART_ID");
        Long allocateOutId=baPO.getLong("ALLOCATE_OUT_ID");
        if(!StringUtils.isNullOrEmpty(roId)){
//            roService.modifyRoStatus(roId, "sub");
        }
        if(!StringUtils.isNullOrEmpty(salesPartId)){
            PartSalesPo partSalesPo=PartSalesPo.findById(salesPartId);
            partSalesPo.setInteger("IS_BALANCE",DictCodeConstants.STATUS_IS_NOT);
            partSalesPo.saveIt();
        }
        if(!StringUtils.isNullOrEmpty(allocateOutId)){
            PartAllocateOutPo allOutPo=PartAllocateOutPo.findById(allocateOutId);
            allOutPo.setInteger("IS_BALANCE", DictCodeConstants.STATUS_IS_NOT);
            allOutPo.saveIt();
        }
        return baPO.getLongId();
    }


    
    /**
    * @author ZhengHe
    * @date 2016年10月28日
    * @param id
    * @param paymentObjectCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#selectBalancePayobj(java.lang.Long, java.lang.String)
    */
    	
    @Override
    public Map selectBalancePayobj(Long id, String paymentObjectCode) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer(" select BALA_PAYOBJ_ID,BALANCE_ID,DEALER_CODE,PAYMENT_OBJECT_TYPE,")
                .append("PAYMENT_OBJECT_CODE,PAYMENT_OBJECT_NAME,DIS_AMOUNT,SUB_OBB_AMOUNT,RECEIVABLE_AMOUNT,")
                .append("RECEIVED_AMOUNT,NOT_RECEIVED_AMOUNT,DERATE_AMOUNT,TAX,TAX_AMOUNT,PAY_OFF,INVOICE_TAG,IS_RED ")
                .append(" from tt_balance_payobj where 1=1")
                .append(" and BALANCE_ID=? and PAYMENT_OBJECT_CODE=?");
        List<Object> params=new ArrayList<Object>();
        params.add(id);
        params.add(paymentObjectCode);
        return DAOUtil.findFirst(sqlsb.toString(), params);
    }


    
    /**
    * 根据id查询结算单
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#findBalanceAmountsById(java.lang.Long)
    */
    	
    @Override
    public Map findBalanceAmountsById(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select tba.BALANCE_ID,tba.DEALER_CODE,tba.BALANCE_NO,tba.RO_ID,")
                .append("tba.SALES_PART_ID,tba.ALLOCATE_OUT_ID,tba.DISCOUNT_MODE_CODE,tba.REMARK AS REMARK,tba.LABOUR_AMOUNT,")
                .append("tba.REPAIR_PART_AMOUNT,tba.SALES_PART_AMOUNT,tba.ADD_ITEM_AMOUNT,tba.AMOUNT,tba.DIS_LABOUR_AMOUNT,")
                .append("tba.DIS_REPAIR_PART_AMOUNT,tba.DIS_SALES_PART_AMOUNT,tba.DIS_ADD_ITEM_AMOUNT,tba.DIS_OVER_ITEM_AMOUNT,")
                .append("tba.DIS_REPAIR_AMOUNT,tba.DIS_AMOUNT,tba.BALANCE_AMOUNT,tba.RECEIVE_AMOUNT,tba.SUB_OBB_AMOUNT,tba.DERATE_AMOUNT, ")
                .append("tba.TAX,tba.TAX_AMOUNT,tba.NET_AMOUNT,PAY_OFF,tba.BALANCE_HANDLER,tba.BALANCE_TIME,tba.SQUARE_DATE,")
                .append("tba.ARR_BALANCE,tba.PRINT_BALANCE_TIME,tba.IS_HAS_CLAIM,tba.IS_RED,tba.RETURNED_BALANCE_BY,tba.RETURNED_BALANCE_TIME,")
                .append("tro.RO_NO,tro.REPAIR_TYPE_CODE,tro.RO_CREATE_DATE,tro.DELIVERER,tro.SERVICE_ADVISOR_ASS,tro.OUT_MILEAGE,tro.REMARK as RO_REMARK, ")
                .append("tv.LICENSE,tv.VIN,tm.MODEL_NAME as MODEL_NAME,(tba.AMOUNT-tba.DIS_AMOUNT) AS DIS_COUNT_AMOUNT, ")
                .append("tro.DELIVERER_PHONE,tw.OWNER_NAME,tw.PHONE ")
                .append(" from tt_balance_accounts tba ")
                .append("left join tt_repair_order tro on tro.DEALER_CODE=tba.DEALER_CODE and tro.RO_ID=tba.RO_ID ")
                .append("LEFT JOIN  tm_vehicle tv ON tba.DEALER_CODE=tv.DEALER_CODE AND tv.VEHICLE_ID=tro.VEHICLE_ID ")
                .append("LEFT JOIN  tm_model tm ON tba.DEALER_CODE=tm.DEALER_CODE AND tm.MODEL_ID=tv.MODEL_CODE ")
               .append(" LEFT JOIN tm_owner tw ON tw.DEALER_CODE=tba.DEALER_CODE AND tw.OWNER_ID=tv.OWNER_ID  ")
                .append(" where 1=1 and tba.BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);         
        return DAOUtil.findFirst(sqlsb.toString(), params);
    }

    

    
    /**
    * 根据id查询结算单维修项目
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceLabour(java.lang.Long)
    */
    	
    @Override
    public List<Map> queryBalanceLabour(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select BALANCE_LABOUR_ID,BALANCE_ID,RO_LABOUR_ID,DEALER_CODE,")
                .append("REPAIR_TYPE_CODE,CHARGE_PARTITION_CODE,LABOUR_CODE,LABOUR_NAME,LOCAL_LABOUR_CODE,")
                .append("LOCAL_LABOUR_NAME,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,LABOUR_PRICE,LABOUR_AMOUNT,")
                .append("DISCOUNT,AFTER_DISCOUNT_AMOUNT,TROUBLE_DESC,TECHNICIAN,WORKER_TYPE_CODE,REMARK,")
                .append("ASSIGN_TAG,ACTIVITY_CODE,PACKAGE_CODE,MODEL_LABOUR_CODE,(LABOUR_AMOUNT*(1-DISCOUNT)) AS DIS_COUNT_AMOUNT ")
                .append(" from tt_balance_labour where 1=1")
                .append(" and BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sqlsb.toString(), params);
    }


    
    /**
    * 根据id查询结算单维修材料
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceRepairPart(java.lang.Long)
    */
    	
    @Override
    public List<Map> queryBalanceRepairPart(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select BALANCE_REPAIR_PART_ID,BALANCE_ID,BALANCE_LABOUR_ID,DEALER_CODE,STORAGE_CODE,")
                .append(" STORAGE_POSITION_CODE,PART_NO,PART_NAME,CHARGE_PARTITION_CODE,UNIT,IS_MAIN_PART,PART_QUANTITY,PRICE_RATE,")
                .append("PART_COST_PRICE,PART_COST_AMOUNT,PART_SALES_PRICE,PART_SALES_AMOUNT,DISCOUNT,AFTER_DISCOUNT_AMOUNT,(PART_SALES_AMOUNT*(1-DISCOUNT)) AS DIS_COUNT_AMOUNT ")
                .append(" from tt_balance_repair_part where 1=1 ")
                .append(" and BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sqlsb.toString(), params);
    }


    
    /**
    * 根据id查询结算单附加项目
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceAddItem(java.lang.Long)
    */
    	
    @Override
    public List<Map> queryBalanceAddItem(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select BALANCE_ADD_ITEM_ID,BALANCE_ID,RO_ADD_ITEM_ID,DEALER_CODE,CHARGE_PARTITION_CODE,ADD_ITEM_CODE,")
                .append("ADD_ITEM_NAME,ADD_ITEM_AMOUNT,RECEIVABLE_AMOUNT,REMARK,(ADD_ITEM_AMOUNT- RECEIVABLE_AMOUNT) AS DIS_COUNT_AMOUNT ")
                .append(" from tt_balance_add_item where 1=1 ")
                .append(" and BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sqlsb.toString(), params);
    }


    
    /**
    * 根据id查询结算单销售材料
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceSalesPart(java.lang.Long)
    */
    	
    @Override
    public List<Map> queryBalanceSalesPart(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("select BALANCE_SALES_PART_ID,BALANCE_ID,ITEM_ID,SALES_PART_ID,DEALER_CODE,")
                .append("STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO,PART_NAME,PART_QUANTITY,UNIT,DISCOUNT,PRICE_TYPE,")
                .append("PRICE_RATE,COST_PRICE,PART_SALES_PRICE,PART_COST_AMOUNT,PART_SALES_AMOUNT,SALES_AMOUNT ")
                .append(" from tt_balance_sales_part where 1=1 ")
                .append(" and BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sqlsb.toString(), params);
    }


    
    /**
    * 根据id查询结算单结算对象
    * @author ZhengHe
    * @date 2016年11月1日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalancePayobj(java.lang.Long)
    */
    	
    @Override
    public List<Map> queryBalancePayobj(Long id) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer(" select BALA_PAYOBJ_ID,BALANCE_ID,DEALER_CODE,PAYMENT_OBJECT_TYPE,PAYMENT_OBJECT_CODE,PAYMENT_OBJECT_NAME,")
                .append("DIS_AMOUNT,SUB_OBB_AMOUNT,RECEIVABLE_AMOUNT,RECEIVED_AMOUNT,NOT_RECEIVED_AMOUNT,DERATE_AMOUNT,")
                .append("TAX,TAX_AMOUNT,PAY_OFF,INVOICE_TAG,IS_RED ")
                .append(" from tt_balance_payobj where 1=1 ")
                .append(" and BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sqlsb.toString(), params);
    }
    /**
     * 查询结算调拨出库单明细
    * @author jcsi
    * @date 2016年11月11日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceAllocatePartById(java.lang.Long)
     */
    @Override
    public List<Map> queryBalanceAllocatePartById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("select BALANCE_ALLOCATE_PART_ID,ITEM_ID,ALLOCATE_OUT_ID,DEALER_CODE,STORAGE_CODE,STORAGE_POSITION_CODE,");
        sb.append("PART_NO,PART_NAME,UNIT,OUT_QUANTITY,OUT_PRICE,OUT_AMOUNT,COST_PRICE,COST_AMOUNT   ");
        sb.append("from tt_balance_allocate_part where BALANCE_ID=? ");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);  
        return DAOUtil.findAll(sb.toString(), params);
    }
    /**
     * 查询结算单（销售）
    * @author jcsi
    * @date 2016年11月11日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceSalesById(java.lang.Long)
     */
    @Override
    public Map queryBalanceSalesById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("select tba.BALANCE_ID,tba.DEALER_CODE,tba.BALANCE_NO,tba.SALES_PART_ID,");
        sb.append("tba.ALLOCATE_OUT_ID,tba.DISCOUNT_MODE_CODE,tba.REMARK AS REMARK,tba.LABOUR_AMOUNT,");
        sb.append("tba.REPAIR_PART_AMOUNT,tba.SALES_PART_AMOUNT,tba.ADD_ITEM_AMOUNT,tba.AMOUNT,tba.DIS_LABOUR_AMOUNT,");
        sb.append("tba.DIS_REPAIR_PART_AMOUNT,tba.DIS_SALES_PART_AMOUNT,tba.DIS_ADD_ITEM_AMOUNT,tba.DIS_OVER_ITEM_AMOUNT,");
        sb.append("tba.DIS_REPAIR_AMOUNT,tba.DIS_AMOUNT,tba.BALANCE_AMOUNT,tba.RECEIVE_AMOUNT,tba.SUB_OBB_AMOUNT,tba.DERATE_AMOUNT, ");
        sb.append("tba.TAX,tba.TAX_AMOUNT,tba.NET_AMOUNT,tp.PAY_OFF,tba.BALANCE_HANDLER,tba.BALANCE_TIME,tba.SQUARE_DATE,");
        sb.append("tba.ARR_BALANCE,tba.PRINT_BALANCE_TIME,tba.IS_HAS_CLAIM,tba.IS_RED,tba.RETURNED_BALANCE_BY,tba.RETURNED_BALANCE_TIME,");
        sb.append("tro.SALES_PART_NO,tro.CUSTOMER_TYPE,tro.CUSTOMER_CODE,tro.CUSTOMER_NAME, ");
        sb.append("tv.LICENSE,tv.VIN,tm.MODEL_NAME as MODEL_NAME,(tba.AMOUNT-tba.DIS_AMOUNT) AS DIS_COUNT_AMOUNT,tp.PAYMENT_OBJECT_NAME  ");
        sb.append("from tt_balance_accounts tba  ");
        sb.append("left join tt_part_sales tro on tro.DEALER_CODE=tba.DEALER_CODE and tro.SALES_PART_ID=tba.SALES_PART_ID  ");
        sb.append("left join tm_owner m on tro.CUSTOMER_CODE=m.OWNER_NO and tro.DEALER_CODE=m.DEALER_CODE  ");
        sb.append("left join tm_vehicle tv on tv.OWNER_ID=m.OWNER_ID and tv.DEALER_CODE=m.DEALER_CODE  ");
        sb.append("LEFT JOIN  tm_model tm ON tba.DEALER_CODE=tm.DEALER_CODE AND tm.MODEL_ID=tv.MODEL_CODE ");
        sb.append("left join tt_balance_payobj tp on tba.BALANCE_ID=tp.BALANCE_ID and tp.DEALER_CODE=tp.DEALER_CODE ");
        sb.append(" where 1=1 and tba.BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);         
        return DAOUtil.findFirst(sb.toString(), params);
    }
    /**
     * 查询结算单（调拨）
    * @author jcsi
    * @date 2016年11月11日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.BalanceAccountsService#queryBalanceAllocateById(java.lang.Long)
     */
    @Override
    public Map queryBalanceAllocateById(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("select tba.BALANCE_ID,tba.DEALER_CODE,tba.BALANCE_NO,tba.SALES_PART_ID,");
        sb.append("tba.ALLOCATE_OUT_ID,tba.DISCOUNT_MODE_CODE,tba.REMARK AS REMARK,tba.LABOUR_AMOUNT,");
        sb.append("tba.REPAIR_PART_AMOUNT,tba.SALES_PART_AMOUNT,tba.ADD_ITEM_AMOUNT,tba.AMOUNT,tba.DIS_LABOUR_AMOUNT,");
        sb.append("tba.DIS_REPAIR_PART_AMOUNT,tba.DIS_SALES_PART_AMOUNT,tba.DIS_ADD_ITEM_AMOUNT,tba.DIS_OVER_ITEM_AMOUNT,");
        sb.append("tba.DIS_REPAIR_AMOUNT,tba.DIS_AMOUNT,tba.BALANCE_AMOUNT,tba.RECEIVE_AMOUNT,tba.SUB_OBB_AMOUNT,tba.DERATE_AMOUNT, ");
        sb.append("tba.TAX,tba.TAX_AMOUNT,tba.NET_AMOUNT,tp.PAY_OFF,tba.BALANCE_HANDLER,tba.BALANCE_TIME,tba.SQUARE_DATE,");
        sb.append("tba.ARR_BALANCE,tba.PRINT_BALANCE_TIME,tba.IS_HAS_CLAIM,tba.IS_RED,tba.RETURNED_BALANCE_BY,tba.RETURNED_BALANCE_TIME,");
        sb.append("tro.ALLOCATE_OUT_NO,tro.CUSTOMER_CODE,tc.CUSTOMER_NAME, ");
        sb.append("(tba.AMOUNT-tba.DIS_AMOUNT) AS DIS_COUNT_AMOUNT,tp.PAYMENT_OBJECT_NAME  ");
        sb.append("from tt_balance_accounts tba  ");
        sb.append("left join tt_part_allocate_out tro on tro.DEALER_CODE=tba.DEALER_CODE and tro.ALLOCATE_OUT_ID=tba.ALLOCATE_OUT_ID  ");
        sb.append("left join tt_balance_payobj tp on tba.BALANCE_ID=tp.BALANCE_ID and tp.DEALER_CODE=tp.DEALER_CODE ");
        sb.append("left join tm_part_customer tc on tc.CUSTOMER_CODE=tro.CUSTOMER_CODE and tc.DEALER_CODE=tro.DEALER_CODE  ");
        sb.append(" where 1=1 and tba.BALANCE_ID=?");
        List<Object> params=new ArrayList<Object>();    
        params.add(id);         
        return DAOUtil.findFirst(sb.toString(), params);
    }
}
