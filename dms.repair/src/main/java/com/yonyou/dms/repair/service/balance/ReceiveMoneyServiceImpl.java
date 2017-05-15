
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : ReceiveMoneyServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.balance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.ChargeDeratePO;
import com.yonyou.dms.common.domains.PO.basedata.ReceiveMoneyPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAccountsPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.SystemParamConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.balance.ChargeDerateDTO;
import com.yonyou.dms.repair.domains.DTO.balance.ReceiveMoneyDTO;
import com.yonyou.dms.repair.domains.PO.balance.BalancePayobjPO;

/**
*
* @author jcsi
* @date 2016年9月28日
*/
@Service
public class ReceiveMoneyServiceImpl implements ReceiveMoneyService{

    @Autowired
    private SystemParamService systemParamService;
    
    @Autowired
    private CommonNoService commonNoService;
    
    @Autowired
    private OperateLogService operateLogService;
    
    /**
     * 保存收款单
    * @author jcsi
    * @date 2016年9月28日
    * @param receiveMoneyDto
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.ReceiveMoneyService#saveReceiveMoney(com.yonyou.dms.repair.domains.DTO.balance.ReceiveMoneyDTO)
     */
    @Override
    public Long saveReceiveMoney(ReceiveMoneyDTO receiveMoneyDto)throws ServiceBizException {
        //1.保存收账单
        ReceiveMoneyPO receiveMoneyPo=new ReceiveMoneyPO();
        setAttribute(receiveMoneyDto,receiveMoneyPo);
        receiveMoneyPo.saveIt();
        //2.更新结算金额
        updateAmount(receiveMoneyDto);
        return receiveMoneyPo.getLongId();
        
    }
    
    /**
    * 更新结算收费对象列表单、结算单中金额
    * @author jcsi
    * @date 2016年9月28日
     */
    public void updateAmount(ReceiveMoneyDTO receiveMoneyDto)throws ServiceBizException{
        BalancePayobjPO balancePayobjPo=BalancePayobjPO.findById(receiveMoneyDto.getBalaPayobjId());
        BasicParametersDTO bpDto=systemParamService.queryBasicParameterByTypeandCode(SystemParamConstants.PARAM_TYPE_AMOUNTRECEIVABLE, SystemParamConstants.REPAIR_AMOUNTRECEIVABLE);
        if(balancePayobjPo!=null){
            //已收账款
            String receivedAmountString=balancePayobjPo.getString("RECEIVED_AMOUNT");
            Double receivedAmount=new Double("0");
            if(!StringUtils.isNullOrEmpty(receivedAmountString)){
                receivedAmount=Double.parseDouble(receivedAmountString);
                balancePayobjPo.setDouble("RECEIVED_AMOUNT",receivedAmount+receiveMoneyDto.getReceiveAmount());
            }else{
                balancePayobjPo.setDouble("RECEIVED_AMOUNT",receiveMoneyDto.getReceiveAmount());
            }
            
            if(receiveMoneyDto.getReceiveAmount()>balancePayobjPo.getDouble("NOT_RECEIVED_AMOUNT")){
                throw new ServiceBizException("收款金额不能大于 未收款金额");
            }
            if(receiveMoneyDto.getReceiveAmount()<0){
                Double absReceiveAmount=Math.abs(receiveMoneyDto.getReceiveAmount());
                if(absReceiveAmount>receivedAmount){
                    throw new ServiceBizException("负数金额的绝对值不能大于 已收账款金额");
                }
            }
            //未收帐款
            balancePayobjPo.setDouble("NOT_RECEIVED_AMOUNT",balancePayobjPo.getDouble("RECEIVABLE_AMOUNT")-balancePayobjPo.getDouble("RECEIVED_AMOUNT"));
           /* if(balancePayobjPo.getDouble("NOT_RECEIVED_AMOUNT")==0){
                balancePayobjPo.setLong("PAY_OFF", DictCodeConstants.REPAIR_IS_SETTLE);
            }else{
                balancePayobjPo.setLong("PAY_OFF", DictCodeConstants.REPAIR_NO_SETTLE);
            }*/
            //更新结算收费对象
            balancePayobjPo.saveIt();
            //更新结算单
            TtBalanceAccountsPO balanceAccountsPO= TtBalanceAccountsPO.findById(balancePayobjPo.getLong("BALANCE_ID"));  
            //收款金额
            if(!StringUtils.isNullOrEmpty(balanceAccountsPO.getDouble("RECEIVE_AMOUNT"))){
                balanceAccountsPO.setDouble("RECEIVE_AMOUNT", balanceAccountsPO.getDouble("RECEIVE_AMOUNT")+receiveMoneyDto.getReceiveAmount());
            }else{
                balanceAccountsPO.setDouble("RECEIVE_AMOUNT", receiveMoneyDto.getReceiveAmount());
            }
           
            //是否自动结清
            if(bpDto.getParamValue().equals(DictCodeConstants.AUTOMATICALLY_LIQUIDATE+"")){
                int result=NumberUtil.compareTo(balanceAccountsPO.getDouble("BALANCE_AMOUNT"),balanceAccountsPO.getDouble("RECEIVE_AMOUNT"));
                if(result==0){
                    balanceAccountsPO.setLong("PAY_OFF", DictCodeConstants.STATUS_IS_YES);
                    balanceAccountsPO.setTimestamp("SQUARE_DATE", new Date()); 
                }
                //if(balanceAccountsPO.getDouble("BALANCE_AMOUNT")==balanceAccountsPO.getDouble("RECEIVE_AMOUNT")){
                   
                    
                //}
            }
            balanceAccountsPO.saveIt();
           
        }
    
    
    }
    /**
    * 属性赋值
    * @author jcsi
    * @date 2016年9月28日
    * @param receiveMoneyDto
    * @param receiveMoneyPo
     */
    public void setAttribute(ReceiveMoneyDTO receiveMoneyDto,ReceiveMoneyPO receiveMoneyPo)throws ServiceBizException{
        receiveMoneyPo.setLong("BALA_PAYOBJ_ID", receiveMoneyDto.getBalaPayobjId());
        receiveMoneyPo.setLong("PAY_TYPE_CODE", receiveMoneyDto.getPayTypeCode());
        receiveMoneyPo.setTimestamp("RECEIVE_DATE", receiveMoneyDto.getReceiveDate());
        receiveMoneyPo.setString("HANDLER", receiveMoneyDto.getHandler());
        receiveMoneyPo.setLong("INVOICE_TYPE_CODE", receiveMoneyDto.getInvoiceTypeCode());
        receiveMoneyPo.setString("BILL_NO", receiveMoneyDto.getBillNo());
        receiveMoneyPo.setString("CHECK_NO", receiveMoneyDto.getCheckNo());
        receiveMoneyPo.setDouble("RECEIVE_AMOUNT", receiveMoneyDto.getReceiveAmount());
        receiveMoneyPo.setLong("INVOICE_TAG", receiveMoneyDto.getInvoiceTag());
        receiveMoneyPo.setLong("WRITEOFF_TAG", receiveMoneyDto.getWriteoffTag());
        receiveMoneyPo.setTimestamp("WRITEOFF_DATE", receiveMoneyDto.getWriteoffDate());
        receiveMoneyPo.setString("REMARK", receiveMoneyDto.getRemark());
    }

    /**
     * 根据结算单收费对象ID查询收款单
    * @author jcsi
    * @date 2016年9月28日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.ReceiveMoneyService#findReceiveMoneyByPayobjId(java.lang.Long)
     */
    @Override
    public List<Map> findReceiveMoneyByPayobjId(Long id)throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.RECEIVE_MONEY_ID,t.BALA_PAYOBJ_ID,t.DEALER_CODE,t.RECEIVE_DATE,t.RECEIVE_AMOUNT,t.PAY_TYPE_CODE,");
        sb.append("t.WRITEOFF_TAG,t.HANDLER,t.INVOICE_TAG,t.INVOICE_TYPE_CODE,t.BILL_NO,t.REMARK,e.EMPLOYEE_NAME   ");
        sb.append("from TT_RECEIVE_MONEY t inner join TM_EMPLOYEE e on t.HANDLER=e.EMPLOYEE_NO where t.BALA_PAYOBJ_ID=? ");     
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 收款减免单保存
    * @author jcsi
    * @date 2016年9月29日
    * @param chargeDerateDTO
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.ReceiveMoneyService#saveChargeDerate(com.yonyou.dms.repair.domains.DTO.balance.ChargeDerateDTO)
     */
    @Override
    public Long saveChargeDerate(ChargeDerateDTO chargeDerateDTO)throws ServiceBizException {
        if(chargeDerateDTO.getDerateAmount()>chargeDerateDTO.getNotReceivedAmount()){
            throw new ServiceBizException("减免金额不能大于未收款金额");
        }
        updateAmount(chargeDerateDTO);
        ChargeDeratePO chargeDeratePo=new ChargeDeratePO();
        
        setChargeDerate(chargeDeratePo,chargeDerateDTO);
        chargeDeratePo.saveIt();
        
         //记录日志
         OperateLogDto operateLogDto=new OperateLogDto();
         BalancePayobjPO balancePayobjPO= BalancePayobjPO.findById(chargeDeratePo.getLong("BALA_PAYOBJ_ID"));
         TtBalanceAccountsPO balanceAccountsPO= TtBalanceAccountsPO.findById(balancePayobjPO.getLong("BALANCE_ID"));
         StringBuilder sb=new StringBuilder();
         sb.append("select EMPLOYEE_NAME,DEALER_CODE from tm_employee t where t.EMPLOYEE_NO=? ");
         List<Object> param=new ArrayList<Object>();
         param.add(chargeDeratePo.getString("DERATE_RATIFIER"));
         Map result= DAOUtil.findFirst(sb.toString(), param);
         operateLogDto.setOperateContent("结算单【"+balanceAccountsPO.getString("BALANCE_NO")+"】减免金额："+chargeDeratePo.getDouble("DERATE_AMOUNT")+"元，减免审核人："+result.get("EMPLOYEE_NAME"));
         operateLogDto.setOperateType(DictCodeConstants.LOG_REPAIR_MANAGEMENT);
         operateLogService.writeOperateLog(operateLogDto);
        return chargeDeratePo.getLongId();
    }
    
    /**
    * 减免金额  需要更新的字段
    * @author jcsi
    * @date 2016年9月29日
     */
    public void updateAmount(ChargeDerateDTO chargeDerateDTO)throws ServiceBizException{
        StringBuilder sb=new StringBuilder("select DERATE_AMOUNT,DEALER_CODE from TT_CHARGE_DERATE where BALA_PAYOBJ_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(chargeDerateDTO.getBalaPayobjId());
        List<Map> result=DAOUtil.findAll(sb.toString(), queryParam);
        //已免账款
        Double derateAmount=chargeDerateDTO.getDerateAmount();
        if(result.size()>0){
            for(Map map:result){
                derateAmount+=Double.parseDouble(map.get("DERATE_AMOUNT")+""); 
            }
        }
       
        
        BalancePayobjPO balancePayobjPO=BalancePayobjPO.findById(chargeDerateDTO.getBalaPayobjId());
        
        //2.更新（结算单）减免金额 
        TtBalanceAccountsPO balanceAccountsPO= TtBalanceAccountsPO.findById(balancePayobjPO.getLong("BALANCE_ID"));
        if(StringUtils.isNullOrEmpty(balanceAccountsPO.getDouble("DERATE_AMOUNT"))){
            balanceAccountsPO.setDouble("DERATE_AMOUNT",chargeDerateDTO.getDerateAmount());
        }else{
            balanceAccountsPO.setDouble("DERATE_AMOUNT",balanceAccountsPO.getDouble("DERATE_AMOUNT")+chargeDerateDTO.getDerateAmount() );
        }
        
        balanceAccountsPO.saveIt();
        
        
        //3.是否自动结清
        BasicParametersDTO bpDto=systemParamService.queryBasicParameterByTypeandCode(SystemParamConstants.PARAM_TYPE_AMOUNTRECEIVABLE, SystemParamConstants.REPAIR_AMOUNTRECEIVABLE);
        Double BalanceNotReceivedAmount=new Double("0");  //结算单未收款金额统计
        List<Object> param=new ArrayList<Object>();
        param.add(balancePayobjPO.getLong("BALANCE_ID"));
        List<Map> payObjMap=DAOUtil.findAll("select DEALER_CODE,NOT_RECEIVED_AMOUNT from tt_balance_payobj where BALANCE_ID = ? ",param);
        if(!CommonUtils.isNullOrEmpty(payObjMap)){
        	for(int i=0;i<payObjMap.size();i++){
        		Map payMap= payObjMap.get(i);
        		BalanceNotReceivedAmount=NumberUtil.add2Double(BalanceNotReceivedAmount,(Double)payMap.get("NOT_RECEIVED_AMOUNT"));
        	}
        }
        int i=NumberUtil.compareTo(chargeDerateDTO.getDerateAmount(),BalanceNotReceivedAmount);
        if(i==0){
            if(bpDto.getParamValue().equals(DictCodeConstants.AUTOMATICALLY_LIQUIDATE+"")){
            	balanceAccountsPO.setLong("PAY_OFF", DictCodeConstants.STATUS_IS_YES);
                balanceAccountsPO.saveIt();
            }
            
        }
        
       //1.更新（结算单收费对象列表）未收款金额
        Double notReceivedAmount=0.0;   //未收款金额（=应收账款-已收账款-已免账款）
        notReceivedAmount=balancePayobjPO.getDouble("RECEIVABLE_AMOUNT")-balancePayobjPO.getDouble("RECEIVED_AMOUNT")-derateAmount;
        balancePayobjPO.setDouble("NOT_RECEIVED_AMOUNT", notReceivedAmount);
        balancePayobjPO.setDouble("DERATE_AMOUNT", derateAmount);
        balancePayobjPO.saveIt();
        
       
        
    }
    /**
     * 设置收款减免单属性
     * @author jcsi
     * @date 2016年9月29日
     * @param chargeDeratePo
     * @param chargeDerateDTO
      */
    public void setChargeDerate(ChargeDeratePO chargeDeratePo,ChargeDerateDTO chargeDerateDTO)throws ServiceBizException{
        chargeDeratePo.setLong("BALA_PAYOBJ_ID", chargeDerateDTO.getBalaPayobjId());
        chargeDeratePo.setString("DERATE_NO", commonNoService.getSystemOrderNo(CommonConstants.DERATE_NO));
        chargeDeratePo.setDouble("DERATE_AMOUNT", chargeDerateDTO.getDerateAmount());
        chargeDeratePo.setTimestamp("DERATE_DATE", new Date());
        chargeDeratePo.setString("HANDLER", chargeDerateDTO.getHandler());
        chargeDeratePo.setString("DERATE_RATIFIER", chargeDerateDTO.getDerateRatifier());
        chargeDeratePo.setLong("REMARK", chargeDerateDTO.getRemark());
    }

    /**
     * 查询减免明细
    * @author jcsi
    * @date 2016年10月8日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.ReceiveMoneyService#findChargeDerate()
     */
    @Override
    public List<Map> findChargeDerate(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.DERATE_ID,t.DEALER_CODE,t.DERATE_NO,t.DERATE_DATE,t.DERATE_AMOUNT,t.REMARK from TT_CHARGE_DERATE t ");
        sb.append(" where t.BALA_PAYOBJ_ID=?");
        List<Object> queryPram=new ArrayList<Object>();
        queryPram.add(id);
        return DAOUtil.findAll(sb.toString(), queryPram);
    }

    /**
     * 销账
    * @author jcsi
    * @date 2016年10月9日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.balance.ReceiveMoneyService#updateWriteoffTag(java.lang.Long)
     */
    @Override
    public void updateWriteoffTag(Long id) throws ServiceBizException {
        ReceiveMoneyPO receiveMoneyPO=  ReceiveMoneyPO.findById(id);
        receiveMoneyPO.setLong("WRITEOFF_TAG", DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
        receiveMoneyPO.saveIt();
    }

   

}
