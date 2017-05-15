package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dms.DTO.gacfca.InsProposalCardListDTO;
import com.yonyou.dms.DTO.gacfca.InsProposalDTO;
import com.yonyou.dms.DTO.gacfca.InsProposalListDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtInsureOrderDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtInsureOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWechatCardInfoPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS058CloudImpl
 * Description: 投保单上端接口
 * @author DC
 * @date 2017年4月7日 下午3:01:23
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS058CloudImpl extends BaseCloudImpl implements SADCS058Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS058CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<InsProposalDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====经销商车辆实销数据上报接收开始===="); 
		for (InsProposalDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("投保单上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("====投保单上报接收结束====");
		}
		return msg;
	}
	
	@SuppressWarnings("unchecked")
	public void insertData(InsProposalDTO vo) throws Exception {
		logger.info("====投保单写入数据库开始====");
		if(vo.getDealerCode() == null ){
			throw new ServiceBizException("经销商代码" + vo.getDealerCode() + "为空!");
		}
		Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		String formStatus = "" ; 
		if(null == vo.getFormStatus() ){
			throw new ServiceBizException("保单状态：" + vo.getFormStatus() + "为空!");
		}
		formStatus = vo.getFormStatus().toString();//保单状态
		if(formStatus.equals("12291005")){//退保
			logger.info("==========更新投保单(退保)开始=============");
			TtInsureOrderPO ioptuibao = TtInsureOrderPO.findFirst(" DEALER_CODE = "+dealerCode+" AND INSURE_ORDER_CODE = ? ", vo.getProposalCode());
			ioptuibao.setDate("UPDATE_DATE", vo.getUpdateDate());
			ioptuibao.setLong("UPDATE_BY", vo.getUpdateBy());
			ioptuibao.setInteger("FORM_STATUS", 12291005);
			ioptuibao.saveIt();//更新主表中的时间
			logger.info("==================更新主表结束=========================");
			List<InsProposalCardListDTO> cardList = vo.getInsProposalCardList();
			if (cardList!=null && cardList.size() > 0) {
				logger.info("===================更新卡券表开始==共"+cardList.size()+"条====================");
				for (int i = 0; i < cardList.size(); i++) {
					InsProposalCardListDTO cardVo = cardList.get(i);
					logger.info("=================更新卡券表=====卡券ID："+cardVo.getCardID()+"================");
					String voucherId = cardVo.getCardID();//卡券ID
					String insureOrderCode = vo.getProposalCode();//保单号
					TtWechatCardInfoPO po = TtWechatCardInfoPO.findFirst(" VOUCHER_ID = "+voucherId+" AND INSURE_ORDER_CODE = ? ", insureOrderCode);
					po.setInteger("USE_STATUS", cardVo.getUseStatus());//状态
					po.setInteger("UPDATE_BY", 22222222L);
					po.setDate("UPDATE_DATE", new Date());
					po.saveIt();
					logger.info("=================更新卡券表=====卡券ID："+cardVo.getCardID()+"结束======");
				}
			}
			logger.info("==============更新投保单(退保)结束===================");
		}else if (formStatus.equals("12291004")){//新增保险
			logger.info("=====================投保单写入数据库开始=====================");
			
			TtInsureOrderPO  insOrder = new TtInsureOrderPO();//投保单
			
			insOrder.setString("ADDRESS", vo.getAddress());
			insOrder.setFloat("APPROVED_CUSTOMER_COUNT", vo.getApprovedCustomerCount());
			insOrder.setFloat("APPROVED_CUSTOMER_QUALITY", vo.getApprovedCustomerQuality());
			insOrder.setString("BUSI_INS_CODE", vo.getBusiInsCode());
			insOrder.setFloat("BUSI_INSURANCE_REBATE_AMT", vo.getComInsuranceRebateAmt());
			insOrder.setFloat("BUSI_INSURANCE_RE_PAY_AMT", vo.getBusiInsuranceRePayAmt());
			insOrder.setString("CERTIFICATE_NO", vo.getCertificateNo());
			insOrder.setFloat("COM_INSURANCE_REBATE_AMT", vo.getComInsuranceRebateAmt());
			insOrder.setFloat("COM_INSURANCE_RE_PAY_AMT", vo.getComInsuranceRePayAmt());
			insOrder.setDate("COMPLETED_DATE", vo.getCompletedDate());
			insOrder.setString("COM_INS_CODE", vo.getComInsCode());
			
			//insOrder.setDdcnUpdateDate(vo.getd)
			insOrder.setInteger("CT_CODE", vo.getCtCode());
			
			insOrder.setString("FILE_A_INS_ID", vo.getFileAInsId());
			insOrder.setString("FILE_A_INS_URL", vo.getFileAInsUrl());
			insOrder.setString("FILE_B_INS_ID", vo.getFileBInsId());
			insOrder.setString("FILE_B_INS_URL", vo.getFileBInsUrl());
			insOrder.setDate("FIRST_REGISTER_DATE", vo.getFirstRegisterDate());
			insOrder.setInteger("FORM_STATUS", vo.getFormStatus());//重点值
			insOrder.setDouble("GATHERED_SUM", vo.getGatheredSum());
			insOrder.setString("INS_BROKER", vo.getInsBroker());
			insOrder.setInteger("INS_CHANNELS", vo.getInsChannels());
			insOrder.setDate("INS_SALES_DATE", vo.getInsSalesDate());
			insOrder.setString("INSURATION_CODE", vo.getInsurationCode());
			insOrder.setString("INSURED_NAME", vo.getInsuredName());
			
			insOrder.setString("INSURE_ORDER_NAME", vo.getProposalName());
			insOrder.setInteger("INSURE_ORDER_TYPE", vo.getProposalType());
			insOrder.setInteger("IS_ADD_PROPOSAL", vo.getIsAddProposal());
			insOrder.setInteger("IS_COLLECTION", vo.getIsCollection());
			insOrder.setInteger("IS_INS_CREDIT", vo.getIsInsCredit());
			insOrder.setInteger("IS_INS_LOCAL", vo.getIsInsLocal());
			insOrder.setInteger("IS_PAYOFF_REBATE", vo.getIsPayoffRebate());
			insOrder.setInteger("IS_SELF_COMPANY_INSURANCE", vo.getIsSelfCompanyInsurance());
			insOrder.setInteger("IS_TRANSFER", vo.getIsTransfer());
			insOrder.setInteger("LOSSES_PAYOFF", vo.getLossesPayOff());
			insOrder.setString("MOBILE", vo.getMobile());
			insOrder.setString("ORG_CUS_NO", vo.getOrgCusNo());
			insOrder.setFloat("OTHER_AMOUNT", (double)vo.getOtherAmount());
			insOrder.setInteger("PAYOFF", vo.getPayOff());
			insOrder.setDate("PAYOFF_DATE", vo.getPayOffDate());
			insOrder.setString("PHONE", vo.getPhone());
			insOrder.setFloat("RECEIVABLE_BUSI_AMOUNT", vo.getReceivableBusiAmount());
			insOrder.setFloat("RECEIVABLE_COM_AMOUNT", vo.getReceivableComAmount());
			insOrder.setString("REMARK", vo.getRemark());
			insOrder.setInteger("SALES_MODEL", vo.getSalesModel());
			insOrder.setString("SO_NO", vo.getSoNo());
			insOrder.setDouble("TOTAL_AOMUNT", vo.getTotalAmount());
			insOrder.setDouble("TOTAL_BUSINESS_AMOUNT", vo.getTotalBusinessAmount());
			insOrder.setDouble("TOTAL_DISCOUNT_AMOUNT", vo.getTotalDiscountAmount());
			insOrder.setDouble("TOTAL_TRAFFIC_AMOUNT", vo.getTotalTrafficAmount());
			insOrder.setString("TRANSFER_OWNER_NO", vo.getTransferOwnerNo());
			insOrder.setFloat("TRAVEL_TAX_AMOUNT", vo.getTravelTaxAmount());
			
			insOrder.setFloat("USED_YEAR", vo.getUsedYear());
			insOrder.setInteger("VEHICLE_RELATION", vo.getVehicleRelation());
			insOrder.setInteger("VEHICLE_TYPE", vo.getVehicleType());
			insOrder.setInteger("VEHICLE_USE_NATURE", vo.getVehicleUseNature());
			insOrder.setInteger("VER", vo.getVer());
			insOrder.setString("VIN", vo.getVin());
			insOrder.setString("ZIP_CODE", vo.getZipCode());
			//add by lsy 2015-6-23
			insOrder.setDouble("TOTAL_PREFERENTIAL", vo.getTotalPreferential());
			insOrder.setString("LICENSE", vo.getLicense());
			insOrder.setString("ENGINE_NO", vo.getEngineNo());
			insOrder.setInteger("OWNER_PROPERTY", vo.getOwnerProperty());
			insOrder.setInteger("DCS_SEND_BOLD_DTATUS", 0);
			insOrder.setString("SEND_NUM", "0");
			//end
			insOrder.setString("BRAND_NAME", vo.getBrand());
			insOrder.setString("SERIES_NAME", vo.getSeries());
			insOrder.setString("MODEL_NAME", vo.getModel());
			insOrder.setString("GROUP_NAME", vo.getConfigCode());//车款
			insOrder.setString("MODEL_YEAR", vo.getModelYear());
			
			String  insureOrderCode = vo.getProposalCode(); // 保单编号
			List<TtInsureOrderPO> list = TtInsureOrderPO.find(" INSURE_ORDER_CODE = "+insureOrderCode+"  AND DEALER_CODE = ? ", dealerCode);
			if (list != null && list.size() > 0) {//存在则修改
				logger.info("======================投保单存在，修改==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"========================");
				insOrder.setLong("UPDATE_BY", vo.getUpdateBy());//更新时间
				insOrder.setDate("UPDATE_DATE", vo.getUpdateDate());
				
				insOrder.saveIt();
			}else {//不存在则新增
				logger.info("======================投保单不存在，新增==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"========================");
				insOrder.setString("INSURE_ORDER_CODE", vo.getProposalCode());
				insOrder.setString("DEALER_CODE", dealerCode);
				insOrder.setInteger("CREATE_BY", vo.getCreateBy());
				insOrder.setDate("CREATE_DATE", vo.getCreateDate());
				
				insOrder.insert();//插入投保单主表表
			}
			
			logger.info("======================新增主表结束==========================");
			List<InsProposalListDTO> insList = vo.getInsProposalList();
			if(insList != null && insList.size()> 0 ){
				logger.info("===================新增明细表开始==共新增/修改"+insList.size()+"条====================");
				for(int j = 0;j < insList.size(); j++){
					InsProposalListDTO proVo=insList.get(j);
					
					TtInsureOrderDetailPO insOrderDet = new TtInsureOrderDetailPO();//投保单详情
					
					insOrderDet.setDate("BEGIN_DATE", proVo.getBeginDate());
					
					insOrderDet.setDouble("DISCOUNT_AMOUNT", proVo.getDiscountAmount());
					insOrderDet.setDate("END_DATE", proVo.getEndDate());
					
					insOrderDet.setInteger("INSURANCE_SORT_LEVEL", proVo.getInsuranceTypeLevel());
					insOrderDet.setDouble("INSURE_ORDER_AMOUNT", proVo.getProposalAmount());
					insOrderDet.setInteger("IS_PRESENTED", proVo.getIsPresented());
					insOrderDet.setInteger("ITEM_ID", proVo.getItemId());
					insOrderDet.setDouble("REAL_RECEIVE_AMOUNT", proVo.getRealReceiveAmount());
					insOrderDet.setDouble("RECEIVABLE_AMOUNT", proVo.getReceivableAmount());
					insOrderDet.setString("REMARK", proVo.getRemark());
					
					insOrderDet.setInteger("VER", proVo.getVer());
					insOrderDet.setString("INSURANCE_SORT_NAME", proVo.getInsuranceTypeName());
					
					List<TtInsureOrderDetailPO> detaillist = TtInsureOrderDetailPO.find(" INSURE_ORDER_CODE = "+proVo.getProposalCode()+ //保单Code 
							" AND DEALER_CODE = "+dealerCode+" AND INSURANCE_SORT_CODE = ? ", proVo.getInsuranceTypeCode()); //险种
					if (detaillist != null && detaillist.size() > 0) {//存在则修改
						logger.info("==========投保单详情存在，修改==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"，险种："+proVo.getInsuranceTypeCode()+"===========");
						insOrderDet.setLong("UPDATE_BY", proVo.getUpdateBy());
						insOrderDet.setDate("UPDATE_DATE", proVo.getUpdateDate());
						
						insOrderDet.saveIt();
					}else {//不存在则新增
						logger.info("=========投保单详情不存在，新增==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"，险种："+proVo.getInsuranceTypeCode()+"===========");
						insOrderDet.setString("INSURE_ORDER_CODE", proVo.getProposalCode());
						insOrderDet.setString("DEALER_CODE", dealerCode);
						insOrderDet.setString("INSURANCE_SORT_CODE", proVo.getInsuranceTypeCode());
						insOrderDet.setLong("CREATR_BY", proVo.getCreateBy());
						insOrderDet.setDate("CREATE_DATE", proVo.getCreateDate());
						
						insOrderDet.insert();
					}
				}
				logger.info("===================新增明细表结束======================");
			}
			List<InsProposalCardListDTO> cardListVO = vo.getInsProposalCardList();
			if(cardListVO != null && cardListVO.size()> 0 ){
				logger.info("===================新增/修改卡券表开始==共"+cardListVO.size()+"条====================");
				for (int j = 0 ; j < cardListVO.size() ; j++) {
					InsProposalCardListDTO cardvo = cardListVO.get(j);
					logger.info("=================新增卡券表=====卡券ID："+cardvo.getCardID()+"================");
					TtWechatCardInfoPO cardInfoPO = new TtWechatCardInfoPO();
					cardInfoPO.setInteger("USE_STATUS", cardvo.getUseStatus());//状态
					cardInfoPO.setInteger("ISSUE_NUMBER", vo.getCardNumber());//发放数量
					cardInfoPO.setDate("ISSUE_DATE", cardvo.getIssueDate());//卡券发放日期
					cardInfoPO.setString("ACTIVITY_CODE", cardvo.getActivityCode());//营销活动ID
//					cardInfoPO.setRepairType(getRepairType(CommonUtils.checkNull(cardvo.getRepairType())));//维修类型代码
//					cardInfoPO.setVoucherStandard(getVoucherStandard(cardvo.getVoucherStandard()==null?0:cardvo.getVoucherStandard()));//发券标准
					List<TtWechatCardInfoPO> cardList = TtWechatCardInfoPO.find("  INSURE_ORDER_CODE = "+vo.getProposalCode()+//保单Code
							" AND DEALER_CODE = "+ dealerCode  +//经销商
							" AND CARD_NO = " + cardvo.getCardType() +//卡券类型ID
							" AND VOUCHER_ID = ? ", cardvo.getCardID());//卡券ID
					if (cardList != null && cardList.size() > 0) {//存在则修改
						logger.info("===========卡券表存在，修改==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"，卡券类型ID："+cardvo.getCardType()+"============");
						cardInfoPO.setLong("UPDATE_BY", 2222222222L);
						cardInfoPO.setDate("UPDATE_DATE", new Date());
						
						cardInfoPO.saveIt();
					}else {//不存在则新增
						logger.info("===========卡券表不存在，新增==保单代码："+vo.getProposalCode()+"，经销商："+dealerCode+"，卡券类型ID："+cardvo.getCardType()+"============");
						cardInfoPO.setString("INSURE_ORDER_CODE", vo.getProposalCode());//保单Code
						cardInfoPO.setString("DEALER_CODE", dealerCode);//经销商
						cardInfoPO.setString("CARD_NO", cardvo.getCardType());//卡券类型ID
						cardInfoPO.setString("VOUCHER_ID", cardvo.getCardID());//卡券ID
						cardInfoPO.setLong("CREATE_BY", 2222222222L);
						cardInfoPO.setDate("CREATE_DATE", new Date());
						cardInfoPO.insert();
					}
				}
				logger.info("===================新增卡券表结束======================");
			}
			logger.info("====投保单详情插入或修改数据库====");
		}
	}

}
