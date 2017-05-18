package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.RoNotSettleAccountsRepairDelDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description  经销商上报未结算的索赔工单的变更删除业务
 * @author Administrator
 *
 */
@Service
public class SEDMS013CoudImpl implements SEDMS013Coud {
	
	final Logger logger = Logger.getLogger(SEDMS013CoudImpl.class);

	
	/**
	 * @description 针对工单的操作都在这个方法里面，
	 * 					如果要 修改工单时已上报未结算索赔工单的变更 ，就填写dealerCode,roNoInUpdate这两个参数，
	 * 					如果要 作废工单时已上报未结算索赔工单的变更，就填写dealerCode,roNoInDelete这两个参数,
	 * 					如果要 结算工单时已上报未结算索赔工单的变更，就填写dealerCode,roNoInBalance这两个参数,
	 * @param dealerCode
	 * @param roNoInUpdate
	 * @param roNoInDelete
	 * @param roNoInBalance
	 * @return
	 */
	@Override
	public List<RoNotSettleAccountsRepairDelDTO> getSEDMS013(String dealerCode, String roNoInUpdate,
			String roNoInDelete, String roNoInBalance) {
		logger.info("==========SEDMS013Impl执行===========");
		try {
			List<RoNotSettleAccountsRepairDelDTO> resultList = new ArrayList<RoNotSettleAccountsRepairDelDTO>();
			List<RepairOrderPO> repairOrderList = getSubmitedTimeoutRepairOrder(dealerCode, roNoInUpdate);
			if (repairOrderList != null && !repairOrderList.isEmpty()) {
				RepairOrderPO repairOrderPO = repairOrderList.get(0);
				RoNotSettleAccountsRepairDelDTO roNotSettleAccountsRepairDelDTO = new RoNotSettleAccountsRepairDelDTO();
				//修改工单时已上报未结算索赔工单的变更
				if (Utility.testString(roNoInUpdate) && (Utility.testString(repairOrderPO.getInteger("RO_TYPE")) && !CommonConstants.DICT_RPT_CLAIM.equals(repairOrderPO.getInteger("RO_TYPE").toString()))) {
					roNotSettleAccountsRepairDelDTO.setRoNo(roNoInUpdate);
				//结算工单时已上报未结算索赔工单的变更
				}else if(Utility.testString(roNoInBalance) && Utility.testString(repairOrderPO.getInteger("RO_STATUS")) && CommonConstants.DICT_RO_STATUS_TYPE_BALANCED.equals(repairOrderPO.getInteger("RO_STATUS").toString())){
					roNotSettleAccountsRepairDelDTO.setRoNo(roNoInBalance);
				//作废工单时已上报未结算索赔工单的变更
				}else if(Utility.testString(roNoInDelete)){
					roNotSettleAccountsRepairDelDTO.setRoNo(roNoInDelete);
				}else{
					logger.debug("工单号为空，方法中断");
					return null;
				}
				roNotSettleAccountsRepairDelDTO.setEntityCode(dealerCode);
				roNotSettleAccountsRepairDelDTO.setDownTimestamp(new Date());
				roNotSettleAccountsRepairDelDTO.setIsValid(Integer.parseInt(CommonConstants.DICT_VALIDS_VALID));
				resultList.add(roNotSettleAccountsRepairDelDTO);
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS013Impl结束===========");
		}
	}

	/**
	 * @description 查询工单
	 * @param dealerCode
	 * @param roNo
	 * @return
	 */
	private List<RepairOrderPO> getSubmitedTimeoutRepairOrder(String dealerCode, String roNo) {
		logger.debug("from RepairOrderPO DEALER_CODE = "+dealerCode+" and RO_NO = "+roNo+" and IS_TIMEOUT_SUBMIT = "+CommonConstants.DICT_IS_YES+" and D_KEY = "+CommonConstants.D_KEY);
		return RepairOrderPO.findBySQL("DEALER_CODE = ? and RO_NO = ? and IS_TIMEOUT_SUBMIT = ? and D_KEY = ?", 
				dealerCode,roNo,Integer.parseInt(CommonConstants.DICT_IS_YES),CommonConstants.D_KEY);
	}
}
