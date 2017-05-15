package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.UcReplaceRebateApplyDto;
import com.yonyou.dms.DTO.gacfca.UcReplaceRebateOwnerDto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtUcCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcRebateApprovalHisPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcRebateApprovalPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcReplaceInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcReplaceVhclFilePO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcVhclInfoPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class SADCS016CloudImpl implements SADCS016Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SADCS055CloudImpl.class);
	@Autowired
	DeCommonDao decommondao;

	@Override
	public String handleExecutor(List<UcReplaceRebateApplyDto> dto) {
		logger.info("*************************** 开始获取二手车置换客户上传数据 ******************************");
		String msg = "1";
		try {

			dealUpBigCustomerData(dto);

		} catch (Exception e) {
			logger.error("二手车置换客户上传数据失败", e);
			msg = "0";
			// throw new Exception(e);
		}
		logger.info("*************************** 成功获取二手车置换客户上传数据 ******************************");
		return msg;
	}

	// 处理上报数据
	private void dealUpBigCustomerData(List<UcReplaceRebateApplyDto> dto) throws Exception {
		logger.info("##################### 开始处理二手车置换客户上传数据 ############################");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		for (UcReplaceRebateApplyDto v : dto) {
			// UcReplaceRebateApplyDto applyVo = (UcReplaceRebateApplyVO)
			// v.getValue();
			Map<String, Object> map = decommondao.getSaDcsDealerCode(v.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			List<UcReplaceRebateOwnerDto> ownerList = v.getOwnerList(); // 获取车主
			Long customerId = null; // 客户ID
			Long newCustomerId = null; // 新客户ID
			Long oldCustomerId = null; // 旧客户ID
			// Long oldVhclId = factory.getLongPK(new TtUcVhclInfoPO()); //
			// 旧车车辆ID
			TtUcVhclInfoPO vhclInfoPo = new TtUcVhclInfoPO();
			// vhclInfoPo.setVhclId(oldVhclId); // 主键
			vhclInfoPo.setInteger("Customer_Id", oldCustomerId); // 旧车客户ID
			vhclInfoPo.setString("Dealer_Code", dealerCode); // 经销商代码
			vhclInfoPo.setString("Vhcl_Brand", v.getOldVhclBrand()); // 旧车品牌
			vhclInfoPo.setString("Vhcl_Series", v.getOldVhclSeries()); // 旧车车系
			vhclInfoPo.setString("Vhcl_Model", v.getOldVhclModel()); // 旧车车型
			vhclInfoPo.setString("Vhcl_Vin", v.getOldVin()); // 旧车车架号
			vhclInfoPo.setInteger("Replace_Intent_Count", v.getReplaceIntentCount()); // 旧车置换意向数
			vhclInfoPo.setString("Vhcl_Price", v.getOldVhclTransactionPrice()); // 旧车价格
			vhclInfoPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE); // 是否启用
																					// -
																					// 是
			vhclInfoPo.setLong("Create_By", DEConstant.DE_CREATE_BY); // 创建人
			vhclInfoPo.setTimestamp("Create_Date", format); // 创建时间
			vhclInfoPo.insert();
			Long oldVhclId = (Long) vhclInfoPo.getId();
			Map<String, Long> customerMap = new HashMap<String, Long>();
			if (null == ownerList || ownerList.size() == 0) {
				throw new Exception("车主信息为空,停止解析数据!");
			}
			if (Utility.testIsNull(v.getReplaceApplyNo())) {
				throw new Exception("销售单号为空!");
			}
			List<Map> vhclInfoList = getReplaceRebateDetailInfo(v.getReplaceApplyNo()); // 获取新车信息
			if (null != vhclInfoList && vhclInfoList.size() > 0) {
			} else {
				throw new Exception("根据销售订单号" + v.getReplaceApplyNo() + "没有获取到车辆信息!");
			}

			// 判断销售订单号是否存在
			TtUcReplaceInfoPO keyReplaceInfo = new TtUcReplaceInfoPO();
			// keyReplaceInfo.setReplaceApplyNo(v.getReplaceApplyNo()); //
			// 置换申请单号(销售单号)
			// keyReplaceInfo.setEnable(Constant.STATUS_ENABLE);
			List<TtUcReplaceInfoPO> replaceInfoList = TtUcReplaceInfoPO.find("Replace_Apply_No=? and Enable=?",
					v.getReplaceApplyNo(), OemDictCodeConstants.STATUS_ENABLE);
			if (null != replaceInfoList && replaceInfoList.size() > 0) { // 存在
				/****************************** 删除上次上报相关信息 ************************************/
				// 删除旧车客户信息
				// TtUcCustomerPO keyOldCustomerPo = new TtUcCustomerPO();
				// keyOldCustomerPo.setLong("Customer_Id",replaceInfoList.get(0).get("Old_Vhcl_Customer_Id"));
				// keyOldCustomerPo.setInteger
				// ("Enable",OemDictCodeConstants.STATUS_ENABLE);
				int delete = TtUcCustomerPO.delete("Customer_Id=? and Enable=?",
						replaceInfoList.get(0).get("Old_Vhcl_Customer_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除旧车客户信息,受影响行数为:" + delete + " ******************************");
				// 删除旧车联系人信息

				int delOldLinkManCount = TtUcLinkmanPO.delete("Customer_Id=? and Enable=?",
						replaceInfoList.get(0).get("Old_Vhcl_Customer_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除旧车车客户联系人信息,受影响行数为:" + delOldLinkManCount + " ******************************");

				// 删除新车客户信息
				int delNewCustomerCount = TtUcCustomerPO.delete("Customer_Id=? and Enable=?",
						replaceInfoList.get(0).get("New_Vhcl_Customer_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除新车客户信息,受影响行数为:" + delNewCustomerCount + " ******************************");
				// 删除新车联系人信息

				int delNewLinkManCount = TtUcLinkmanPO.delete("Customer_Id=? and Enable=?",
						replaceInfoList.get(0).get("New_Vhcl_Customer_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除新车车客户联系人信息,受影响行数为:" + delNewLinkManCount + " ******************************");

				// 删除置换信息
				int delReplaceInfoCount = TtUcReplaceInfoPO.delete("Replace_Apply_No=? and Enable=?",
						v.getReplaceApplyNo(), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除置换信息,受影响行数为:" + delReplaceInfoCount + " ******************************");

				// 车辆信息
				int delVhclCount = TtUcReplaceInfoPO.delete("Old_Vhcl_Id=? and Enable=?",
						replaceInfoList.get(0).get("Old_Vhcl_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除旧车车辆信息,受影响行数为:" + delVhclCount + " ******************************");

				// 返利材料信息

				int delFileCount = TtUcReplaceVhclFilePO.delete("VhclId=? and Enable=?",
						replaceInfoList.get(0).get("Old_Vhcl_Id"), OemDictCodeConstants.STATUS_ENABLE);
				logger.info("******* 成功删除旧车返利材料信息,受影响行数为:" + delFileCount + " ******************************");

				// 返利审批信息

				TtUcRebateApprovalPO approvalPo = TtUcRebateApprovalPO.findFirst("REPLACE_APPLY_NO=? and Enable=?",
						v.getReplaceApplyNo(), OemDictCodeConstants.STATUS_ENABLE);
				// approvalPo.setLong("Vhcl_Id", oldVhclId); // 由于车辆会先删除,所以应取主键
				approvalPo.setInteger("Rebate_Approval_Status",
						OemDictCodeConstants.UC_REBATE_APPROVAL_TYPE_UNAPPROVED); // 未审批
				approvalPo.setLong("Update_By", DEConstant.DE_CREATE_BY);
				approvalPo.setTimestamp("Update_Date", format);
				approvalPo.saveIt();

				// logger.info("******* 成功更新返利审批信息,受影响行数为:" + rebateCount + "
				// ******************************");

				// 更新返利审批历史信息

				TtUcRebateApprovalHisPO approvalHisPo = TtUcRebateApprovalHisPO.findFirst(
						"Replace_Apply_No=? and Enable=?", v.getReplaceApplyNo(), OemDictCodeConstants.STATUS_ENABLE);
				approvalHisPo.setLong("Vhcl_Id", oldVhclId); //
				// 由于车辆会先删除,所以应取主键
				approvalHisPo.setLong("Update_By", DEConstant.DE_CREATE_BY);
				approvalHisPo.setTimestamp("Update_Date", format);
				approvalHisPo.saveIt();
				// logger.info("******* 成功更新返利审批信息,受影响行数为:" + rebateHisCount + "
				// ******************************");
			} else {
				// 审批信息
				TtUcRebateApprovalPO approvalPo = new TtUcRebateApprovalPO();
				// approvalPo.setRebateApprovalId(factory.getLongPK(approvalPo));
				approvalPo.setString("Dealer_Code", dealerCode);
				approvalPo.setString("Replace_Apply_No", v.getReplaceApplyNo());
				approvalPo.setInteger("Vhcl_Id", oldVhclId); // 车辆ID
				approvalPo.setString("Vhcl_Series", vhclInfoList.get(0).get("SERIES_CODE").toString()); // 新车车系
				approvalPo.setInteger("Rebate_Approval_Status",
						OemDictCodeConstants.UC_REBATE_APPROVAL_TYPE_UNAPPROVED); // 未审批
				approvalPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE);
				approvalPo.setLong("Create_By", DEConstant.DE_CREATE_BY);
				approvalPo.setTimestamp("Create_Date", format);
				approvalPo.insert();
				logger.info("******* 成功写入审批信息******************************");
			}

			for (UcReplaceRebateOwnerDto ownerVo : ownerList) { // 客户信息
				/**************************** 客户信息 **********************************/
				TtUcCustomerPO ucCustomerPo = new TtUcCustomerPO();
				TtUcLinkmanPO ucLinkManPo = new TtUcLinkmanPO();
				// ucCustomerPo = new TtUcCustomerPO();
				// customerId = factory.getLongPK(ucCustomerPo);
				if (ownerVo.getIsNewOwner() == 1) { // 是新车车主 1:新 2 :旧
					logger.info("新车车主" + ownerVo.getOwnerName() + "客户ID：" + customerId);
					ucCustomerPo.setInteger("Customer_Type", parseCustomerType(ownerVo.getCustomerType())); // 客户类型
				}
				// ucCustomerPo.setCustomerId(customerId); // 主键
				ucCustomerPo.setString("Dealer_Code", dealerCode);
				ucCustomerPo.setInteger("Customer_Business_Type", OemDictCodeConstants.IF_TYPE_NO); // 非大客户
				ucCustomerPo.setString("Customer_Name", ownerVo.getOwnerName()); // 客户名称
				ucCustomerPo.setString("Customer_Phone", ownerVo.getCellphone()); // 客户电话
				ucCustomerPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE); // 是否启用
																						// -
																						// 是
				ucCustomerPo.setLong("Create_By", DEConstant.DE_CREATE_BY);
				ucCustomerPo.setTimestamp("Create_Date", format);
				ucCustomerPo.insert();
				// customerId = (Long) ucCustomerPo.getId();

				// customerId = ucCustomerPo.getLongId();
				customerId = (Long) ucCustomerPo.getId();
				if (ownerVo.getIsNewOwner() == 1) { // 是新车车主 1:新 2 :旧
					// logger.info("新车车主"+ownerVo.getOwnerName()+"客户ID："+customerId);
					customerMap.put("new_customer", customerId);
				} else {
					// logger.info("旧车车主"+ownerVo.getOwnerName()+"客户ID："+customerId);
					customerMap.put("old_customer", customerId);
				}
				// 客户信息
				// factory.insert(customerList);
				logger.info("*************************** 成功写入客户信息表(TT_UC_CUSTOMER) ******************************");

				/**************************** 联系人信息 **********************************/
				ucLinkManPo = new TtUcLinkmanPO();
				// ucLinkManPo.setLmId(factory.getLongPK(ucLinkManPo)); // 联系人主键
				ucLinkManPo.setInteger("Customer_Id", customerId); // 客户ID
				ucLinkManPo.setString("Dealer_Code", dealerCode); // 经销商代码
				ucLinkManPo.setInteger("Lm_Type", OemDictCodeConstants.IF_TYPE_YES); // 是否车主
				ucLinkManPo.setString("Lm_Name", ownerVo.getOwnerName()); // 联系人名称
				ucLinkManPo.setString("Lm_Card_Type", ownerVo.getCardType()); // 证件类型
				ucLinkManPo.setString("Lm_Card_No", ownerVo.getCardNo()); // 证件号码
				ucLinkManPo.setTimestamp("Lm_Birth_Day", ownerVo.getBirthDay()); // 生日
				ucLinkManPo.setString("Lm_Sex", ownerVo.getSex()); // 性别
				ucLinkManPo.setString("Lm_Cellphone", ownerVo.getCellphone()); // 手机
				ucLinkManPo.setString("Lm_Telephone", ownerVo.getTelephone()); // 电话
				ucLinkManPo.setString("Lm_Email", ownerVo.getEmail()); // 邮箱
				ucLinkManPo.setString("Lm_Fax", ownerVo.getFax()); // 传真
				ucLinkManPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE); // 是否启用-是
				ucLinkManPo.setLong("Create_By", DEConstant.DE_CREATE_BY); // 创建人
				ucLinkManPo.setTimestamp("Create_Date", format); // 创建时间
				ucLinkManPo.insert();
				// 联系人信息
				logger.info("*************************** 成功写入联系人信息表(TT_UC_LINKMAN) ******************************");
			}

			// 获取旧车客户ID与新车客户ID
			if (customerMap.size() == 1) {
				newCustomerId = customerMap.get("new_customer");
				oldCustomerId = customerMap.get("new_customer");
			} else {
				oldCustomerId = customerMap.get("old_customer");
				newCustomerId = customerMap.get("new_customer");
			}

			// 置换信息
			TtUcReplaceInfoPO replaceInfo = new TtUcReplaceInfoPO();
			// replaceInfo.setReplaceInfoId(factory.getLongPK(replaceInfo));
			replaceInfo.setString("Dealer_Code", dealerCode); // 经销商代码
			replaceInfo.setInteger("Replace_Way", v.getReplaceWay()); // 置换方式
			replaceInfo.setTimestamp("Replace_Submit_Apply_Date", v.getReplaceSubmitApplyDate()); // 提交申请日期
			replaceInfo.setTimestamp("Replace_Date", v.getReplaceDate()); // 置换日期
			replaceInfo.setString("Replace_Apply_No", v.getReplaceApplyNo()); // 置换申请单号(销售单号)
			replaceInfo.setInteger("Old_Vhcl_Customer_Id", oldCustomerId); // 旧车客户
			replaceInfo.setInteger("New_Vhcl_Customer_Id", newCustomerId); // 新车客户
			replaceInfo.setInteger("Old_Vhcl_Id", oldVhclId); // 旧车车辆ID
			replaceInfo.setString("Old_Vhcl_Vin", v.getOldVin()); // 旧车VIN
			replaceInfo.setLong("New_Vhcl_Id", Long.parseLong(vhclInfoList.get(0).get("VEHICLE_ID").toString()));
			replaceInfo.setString("New_Vhcl_Vin", vhclInfoList.get(0).get("VIN").toString());
			replaceInfo.setTimestamp("Old_Vhcl_Transaction_Date", v.getOldVhclTransactionDate()); // 旧车成交日期
			replaceInfo.setString("Old_Vhcl_Transaction_Price", v.getOldVhclTransactionPrice()); // 旧车成交价格
			replaceInfo.setInteger("Old_Vhcl_Transaction_Way", v.getOldVhclTransactionWay()); // 旧车成交方式
			replaceInfo.setString("Subsidy_Money", v.getSubsidyMoney()); // 补贴金额
			replaceInfo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE); // 是否启用
			replaceInfo.setLong("Create_By", DEConstant.DE_CREATE_BY);
			replaceInfo.setTimestamp("Create_Date", format);
			replaceInfo.insert();
			logger.info("*************************** 成功写入置换信息表(TT_UC_REPLACE_INFO) ******************************");

			// 车辆信息
			// vhclInfoPo.setVhclId(oldVhclId); // 主键
			TtUcVhclInfoPO.update("Customer_Id", "VHCL_ID=?", oldCustomerId, oldVhclId);

			logger.info("*************************** 成功写入旧车车辆信息表(TT_UC_VHCL_INFO) ******************************");

			// 销售订单信息(暂时不存,取实销上报中的信息)

			// 返利申请材料信息
			TtUcReplaceVhclFilePO filePo = new TtUcReplaceVhclFilePO();
			// filePo.setVhclFileId(factory.getLongPK(filePo));
			filePo.setString("Vhcl_Sale_Order_No", v.getReplaceApplyNo()); // 销售订单号
			filePo.set("Vhcl_Id", oldVhclId); // 旧车车辆ID
			filePo.setString("New_Vhcl_Invoice_Pic", v.getNewVhclInvoicePic()); // 新车发票图片
			filePo.setString("Old_Vhcl_Travel_Card_Pic", v.getOldVhclTravelCardPic()); // 旧车行驶证图片
			filePo.setString("Old_Vhcl_Transfer_Membership_Pic", v.getOldVhclTransferMembershipPic()); // 旧车转籍单图片
			filePo.setString("Card_Pic", v.getCardPic()); // 身份证明
			filePo.setString("Old_Vhcl_Certificates_Of_Title_Pic", v.getOldVhclCertificatesOfTitlePic()); // 旧车产权证图片
			filePo.setString("Marriage_Certificates_Pic", v.getMarriageCertificatesPic()); // 婚姻状况图片
			filePo.setString("Fina_Pic", v.getFinaPic()); // 财务证明图片
			filePo.setString("Other_Pic", v.getOtherPic()); // 其它证明图片
			filePo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE); // 是否启用-是
			filePo.setLong("Create_By", DEConstant.DE_CREATE_BY);
			filePo.setTimestamp("Create_Date", format);
			filePo.insert();
			logger.info(
					"*************************** 成功写入申请材料信息表(TT_UC_REPLACE_VHCL_FILE) ******************************");
		}

	}

	private Integer parseCustomerType(Integer customerType) {
		Integer nctype = null;
		if (customerType.equals(10181001)) {
			nctype = OemDictCodeConstants.CTM_TYPE_01; // 个人客户
		} else if (customerType.equals(10181002)) {
			nctype = OemDictCodeConstants.CTM_TYPE_02; // 公司客户
		}
		return nctype;
	}

	/**
	 * 根据销售订单号获取VIN,VHCLID
	 * 
	 * @param contractNo
	 *            销售订单号
	 * @return
	 */
	private List<Map> getReplaceRebateDetailInfo(String contractNo) {
		StringBuffer pasql = new StringBuffer(
				" SELECT TV.VEHICLE_ID,TV.VIN,VW.SERIES_CODE " + " FROM TT_VS_SALES_REPORT TSR ,TM_VEHICLE_DEC TV , ("
						+ OemBaseDAO.getVwMaterialSql() + ") VW " + " WHERE TSR.VEHICLE_ID = TV.VEHICLE_ID AND "
						+ " TV.MATERIAL_ID = VW.MATERIAL_ID " + " AND TSR.CONTRACT_NO = '" + contractNo + "'");
		// params.add(contractNo);
		List<Map> ps = OemDAOUtil.findAll(pasql.toString(), null);

		return ps;
	}

}
