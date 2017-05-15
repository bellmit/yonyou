package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS014DAO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.PoCusWholeRepayClryslerDto;
import com.yonyou.dms.DTO.gacfca.WsConfigInfoRepayClryslerDto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerFilingBaseInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerSaleVhclInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtUcCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.common.domains.PO.customer.TtBigCustomerRebateApprovalPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SADCS014CloudImpl implements SADCS014Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS014CloudImpl.class);
	@Autowired
	SADCS014DAO dao;
	private static List<Map> reportSaleVhclList = new ArrayList<>();

	@Override
	public String handleExecutor(List<PoCusWholeRepayClryslerDto> dto) throws Exception {
		logger.info("*************************** 开始获取大客户返利审批上传数据 ******************************");
		String msg = "1";
		for (PoCusWholeRepayClryslerDto tBInfoDTo : dto) {
			try {
				dealUpBigCustomerApprovalData(tBInfoDTo);
			} catch (Exception e) {
				msg = "0";
				throw new ServiceBizException(e);
			}

		}
		logger.info("*************************** 成功获取大客户返利审批上传数据 ******************************");
		return msg;
	}

	@SuppressWarnings("unchecked")
	private void dealUpBigCustomerApprovalData(PoCusWholeRepayClryslerDto tBInfoDTo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("##################### 开始处理大客户报备审批上传数据 ############################");
		 reportSaleVhclList = dao.getSalesVhclInfo();
		for (Map map : reportSaleVhclList) {
			TtVsSalesReportPO reportPo = new TtVsSalesReportPO();
			reportPo.setTimestamp("SALES_DATE", map.get("SALES_DATE"));
			reportPo.setTimestamp("INVOICE_DATE", map.get("INVOICE_DATE"));
			reportPo.saveIt();
			map.put(map.get("CONTRACT_NO") + "@" + map.get("VIN"), reportPo);
		}
		Map<String, Object> map = dao.getSaDcsDealerCode(tBInfoDTo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

		// 验证是否存在报备单(用于判断是否首次报备)
		LazyList<TtBigCustomerFilingBaseInfoPO> baseInfoList = TtBigCustomerFilingBaseInfoPO.find(
				" WS_NO=? and DEALER_CODE=? and ENABLE=?",
				tBInfoDTo.getWsNo(), dealerCode, OemDictCodeConstants.STATUS_ENABLE);
		if (null == baseInfoList || baseInfoList.size() == 0) {
			throw new ServiceBizException("报备单号" + tBInfoDTo.getWsNo() + "不存在!");
		}

		// 更新大客户信息（公司名称）

//		TtUcCustomerPO ttUcPO = TtUcCustomerPO.findFirst("Customer_Id=? and Enable=? and dealer_Code=?",
//				baseInfoList.get(0).get("Customer_Id"), OemDictCodeConstants.STATUS_ENABLE, dealerCode);
//
//		ttUcPO.setString("DEALER_CODE", dealerCode);
//		ttUcPO.setString("Company_Name", tBInfoDTo.getCompanyName());// 公司的名字
//		ttUcPO.setLong("Update_By", DEConstant.DE_CREATE_BY);
//		ttUcPO.setTimestamp("Update_Date", format);
//		ttUcPO.saveIt();
		TtUcCustomerPO.update("DEALER_CODE=?,Company_Name=?,Update_By=?,Update_Date=?", "Customer_Id=? and Enable=? and dealer_Code=?",
				dealerCode,tBInfoDTo.getCompanyName(), DEConstant.DE_CREATE_BY,format,baseInfoList.get(0).get("Customer_Id"), OemDictCodeConstants.STATUS_ENABLE, dealerCode);
		// 更新批售类型
		TtBigCustomerFilingBaseInfoPO filingBasePO = new TtBigCustomerFilingBaseInfoPO();
		filingBasePO.setInteger("Ps_Type", tBInfoDTo.getWsAppType()); // 批售类型
		if (Integer.parseInt(tBInfoDTo.getWsAppType().toString())==OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP_BUY) {// 团购:15971003
			if (null != tBInfoDTo.getWsEmployeeType()) {
				switch (tBInfoDTo.getWsEmployeeType()) {
				case 61201002:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_02);// 知名企业
					break;
				case 61201005:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_01);// 一般大客户
					break;
				case 61201008:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_05);// 知名院校、医院
					break;
				case 61201009:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_06);// 合作银行
					break;
				case 61201010:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_03);// 平安集团
					break;
				case 61201011:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE_04);// 公务员
					break;
				}
			}
		} else if (Integer.parseInt(
				tBInfoDTo.getWsAppType().toString()) == OemDictCodeConstants.BIG_CUSTOMER_BATCH_SALE_TYPE_GROUP) {// 集团销售
			if (null != tBInfoDTo.getWsEmployeeType()) {
				switch (tBInfoDTo.getWsEmployeeType()) {
				case 61201002:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE1_01);// 知名企业
					break;
				case 61201003:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE1_02);// 酒店/航空
					break;
				case 61201004:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE1_03);// 租赁
					break;
				case 61201012:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE1_04);// 一般企业
					break;
				case 61201013:
					filingBasePO.setInteger("Employee_Type", OemDictCodeConstants.EMPLOYEE_TYPE1_05);// 政府
					break;
				}
			}
		}

		// 客户细分类别
		if (null != tBInfoDTo.getWsthreeType()) {
			switch (tBInfoDTo.getWsthreeType()) {
			case 61301001:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_01);// 腾讯集团
				break;
			case 61301002:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_02);// 中石油中石化
				break;
			case 61301003:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_03);// 平安集团
				break;
			case 61301004:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_04);// 其他
				break;
			case 61301005:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_05);// 建设银行
				break;
			case 61301006:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_06);// 中国银行
				break;
			// case 61301007:
			// filingBasePO.setInteger("Customer_Sub_Type",Constant.EMPLOYEE_TYPE2_07);//工商银行
			// break;
			case 61301008:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_08);// 中信银行
				break;
			case 61301009:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_09);// 世界五百强
				break;
			case 61301010:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_10);// 中国五百强
				break;
			case 61301011:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_11);// 万科
				break;
			case 61301012:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_12);// 华为
				break;
			case 61301013:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_13);// 海尔
				break;
			case 61301014:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_14);// 蓝色光标
				break;
			case 61301015:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_15);// 中欧商学院
				break;
			case 61301016:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_16);// 长江商学院
				break;
			case 61301017:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_17);// 211院校
				break;
			case 61301018:
				filingBasePO.setInteger("Customer_Sub_Type", OemDictCodeConstants.EMPLOYEE_TYPE2_18);// 三甲医院
				break;
			}
		}

		filingBasePO.setLong("Update_By", DEConstant.DE_CREATE_BY); // 更新人
		filingBasePO.setTimestamp("Update_Date", format); // 更新时间
		filingBasePO.setString("Rebate_Remark", tBInfoDTo.getRemark()); // 申请备注
		filingBasePO.saveIt();

		// logger.info("***************************成功更新报备基础信息表,受影响行数为"+upCount+"******************");

		// 判断实销车辆信息(报备返利实销表)

		LazyList<TtBigCustomerSaleVhclInfoPO> saleInfoList = TtBigCustomerSaleVhclInfoPO.find(
				" Ws_No=? and Dealer_Code=? and Enable=?", tBInfoDTo.getWsNo(), dealerCode,
				OemDictCodeConstants.STATUS_ENABLE);
		// 实销信息
		LinkedList<WsConfigInfoRepayClryslerDto> saleVhclList = tBInfoDTo.getConfigList();
		// 实销车辆集合
		List<TtBigCustomerSaleVhclInfoPO> bigSaleVhclList = new ArrayList<TtBigCustomerSaleVhclInfoPO>();
		if ((null == saleInfoList || saleInfoList.size() == 0) && (null == saleVhclList || saleVhclList.size() == 0)) {
			throw new ServiceBizException("上传报备单号" + tBInfoDTo.getWsNo() + "实销车辆为空");
		}
		// 上报实销车辆不为空
		if (null != saleVhclList && saleVhclList.size() > 0) {
			// 删除实销车辆信息
			int delSaleCount = TtBigCustomerSaleVhclInfoPO.delete(" Ws_No=? and Dealer_Code=? and Enable=?",
					tBInfoDTo.getWsNo(), dealerCode, OemDictCodeConstants.STATUS_ENABLE);
			logger.info("************* 成功删除报备单号(" + tBInfoDTo.getWsNo() + ")的实销车辆信息,影响行数 " + delSaleCount
					+ "*******************");

			TtBigCustomerSaleVhclInfoPO saleVhclInfo = null;
			String soNo = "";
			String vin = "";
			// 写入实销车辆信息
			for (WsConfigInfoRepayClryslerDto repayClryslerVO : saleVhclList) {
				soNo = repayClryslerVO.getSoNo();
				vin = repayClryslerVO.getVin();
				if (CommonUtils.checkIsNull(soNo)) {
					throw new ServiceBizException("实销车辆SONO为空!");
				}
				if (CommonUtils.checkIsNull(vin)) {
					throw new ServiceBizException("实销VIN为空!");
				}
				TtVsSalesReportPO reportPo = getSaleVhclInfo(soNo.trim() + "@" + vin.trim());
				if (reportPo == null) {
					throw new ServiceBizException("根据销售单号:" + soNo + ",VIN:" + vin + "没找到对应的实销信息!");
				}
				saleVhclInfo = new TtBigCustomerSaleVhclInfoPO();
				saleVhclInfo.setString("DEALER_CODE", dealerCode);
				saleVhclInfo.setString("CUSTOMER_COMPANY_CODE", baseInfoList.get(0).get("Customer_Company_Code")); // 大客户代码
				saleVhclInfo.setString("Ws_No", tBInfoDTo.getWsNo());
				saleVhclInfo.setString("VIN", vin); // 车辆车辆号
				saleVhclInfo.setString("BRANDCODE", repayClryslerVO.getBrandCode()); // 品牌
				saleVhclInfo.setString("SERIESCODE", repayClryslerVO.getSeriesCode()); // 车系
				saleVhclInfo.setString("MODELCODE", repayClryslerVO.getModelCode()); // 车型
				saleVhclInfo.setString("CONFIGCODE", repayClryslerVO.getConfigCode()); // 配置
				saleVhclInfo.setString("VEHICLEPRICE", String.valueOf(repayClryslerVO.getVehiclePrice())); // 车辆价格
				saleVhclInfo.setString("SONO", soNo); // 销售单号

				String fileInvoiceUrl = repayClryslerVO.getFileInvoiceUrl();
				String fileInvoiceUrl1 = "";
				if (!CommonUtils.checkIsNull(fileInvoiceUrl)) {
					saleVhclInfo.setString("SALE_INVOICE1", fileInvoiceUrl); // 销售发票
					fileInvoiceUrl1 = fileInvoiceUrl.substring(fileInvoiceUrl.lastIndexOf("/") + 1,
							fileInvoiceUrl.length());
					// fileInvoiceUrl1
					// =ImageResizer.resizeImage(fileInvoiceUrl1, 800, 800);//
					// 弹出图片
					saleVhclInfo.setString("Sale_Invoice1", fileInvoiceUrl1); //
					// 销售发票压缩后图片
				}
					String fileCardUrl = repayClryslerVO.getFileCardUrl();
					String fileCardUrl1 = "";
					if (!CommonUtils.checkIsNull(fileCardUrl)) {
						saleVhclInfo.setString("GWY_WORK_CARD_PIC", fileCardUrl); // 行驶证
						fileCardUrl1 = fileCardUrl.substring(fileCardUrl.lastIndexOf("/") + 1, fileCardUrl.length());
						// TODO
						// fileCardUrl1 = ImageResizer.resizeImage(fileCardUrl1,
						// 800, 800);// 弹出图片
						saleVhclInfo.setString("VHCL_TRAVEL_CARD_PIC1", fileCardUrl1); //
						// 行驶证压缩后图片
				}
				

				String fileCustomerCardUrl = repayClryslerVO.getFileCustomerCardUrl();
				String fileCustomerCardUrl1 = "";
				if (!CommonUtils.checkIsNull(fileCustomerCardUrl)) {
					saleVhclInfo.setString("CARD_PIC1", fileCustomerCardUrl); // 客户身份证明
					fileCustomerCardUrl1 = fileCustomerCardUrl.substring(fileCustomerCardUrl.lastIndexOf("/") + 1,
							fileCustomerCardUrl.length());
					// TODO
					// fileCustomerCardUrl1
					// =ImageResizer.resizeImage(fileCustomerCardUrl1,
					// 800,800);// 弹出图片
					saleVhclInfo.setString("Card_Pic1", fileCustomerCardUrl1); //
					// 客户身份证明压缩后图片
				}

				String fileSaleContractUrl = repayClryslerVO.getFileSaleContractUrl();
				String fileSaleContractUrl1 = "";
				if (!CommonUtils.checkIsNull(fileSaleContractUrl)) {
					saleVhclInfo.setString("SALE_CONTRACT", fileSaleContractUrl); // 社保缴纳凭证1
					fileSaleContractUrl1 = fileSaleContractUrl.substring(fileSaleContractUrl.lastIndexOf("/") + 1,
							fileSaleContractUrl.length());
					// fileSaleContractUrl1 =
					// ImageResizer.resizeImage(fileSaleContractUrl1, 800,
					// 800);// 弹出图片
					saleVhclInfo.setString("SALE_CONTRACT1", fileSaleContractUrl1); // 社保缴纳凭证1压缩后图片

				}

				/** add weixia by 2015-4-1 start **/
				String fileSaleContractUrl11 = repayClryslerVO.getFileSaleContractUrl1();
				String fileSaleContractUrl12 = "";
				if (!CommonUtils.checkIsNull(fileSaleContractUrl11)) {
					saleVhclInfo.setString("FILE_SALE_CONTRACT_URL1", fileSaleContractUrl11); // 社保缴纳凭证2
					fileSaleContractUrl12 = fileSaleContractUrl11.substring(fileSaleContractUrl11.lastIndexOf("/") + 1,
							fileSaleContractUrl11.length());
					// fileSaleContractUrl12 =
					// ImageResizer.resizeImage(fileSaleContractUrl12, 800,
					// 800);// 弹出图片
					saleVhclInfo.setString("FILE_SALE_CONTRACT_URL11", fileSaleContractUrl12); // 社保缴纳凭证2压缩后图片
				}

				String fileUrlEmpH21 = repayClryslerVO.getFileUrlEmpH2();
				String fileUrlEmpH22 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpH21)) {
					saleVhclInfo.setString("file_Url_Emp_H21", fileUrlEmpH21); // 公务员专用销售合同原件扫描件URL2
					fileUrlEmpH22 = fileUrlEmpH21.substring(fileUrlEmpH21.lastIndexOf("/") + 1, fileUrlEmpH21.length());
					// fileUrlEmpH22 = ImageResizer.resizeImage(fileUrlEmpH22,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("file_Url_Emp_H21", fileUrlEmpH22); //
					// 公务员专用销售合同原件扫描件URL2压缩后图片
				}

				String fileUrlEmpH31 = repayClryslerVO.getFileUrlEmpH3();
				String fileUrlEmpH32 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpH31)) {
					saleVhclInfo.setString("file_Url_Emp_H31", fileUrlEmpH31); // 公务员专用销售合同原件扫描件URL3
					fileUrlEmpH32 = fileUrlEmpH31.substring(fileUrlEmpH31.lastIndexOf("/") + 1, fileUrlEmpH31.length());
					// fileUrlEmpH32 = ImageResizer.resizeImage(fileUrlEmpH32,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("file_Url_Emp_H31", fileUrlEmpH32); //
					// 公务员专用销售合同原件扫描件URL3压缩后图片
				}

				String fileUrlEmpH41 = repayClryslerVO.getFileUrlEmpH4();
				String fileUrlEmpH42 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpH41)) {
					saleVhclInfo.setString("file_Url_Emp_H4", fileUrlEmpH41); // 公务员专用销售合同原件扫描件URL4
					fileUrlEmpH42 = fileUrlEmpH41.substring(fileUrlEmpH41.lastIndexOf("/") + 1, fileUrlEmpH41.length());
					// fileUrlEmpH42 = ImageResizer.resizeImage(fileUrlEmpH42,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("file_Url_Emp_H41", fileUrlEmpH42); //
					// 公务员专用销售合同原件扫描件URL4压缩后图片
				}
				String fileUrlEmpG = repayClryslerVO.getFileUrlEmpG();
				String fileUrlEmpG1 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpG)) {
					saleVhclInfo.setString("GWY_SALE_CONTRACT_PIC", fileUrlEmpG);// 员工在职证明URL
					fileUrlEmpG1 = fileUrlEmpG.substring(fileUrlEmpG.lastIndexOf("/") + 1, fileUrlEmpG.length());
					// fileUrlEmpG1 = ImageResizer.resizeImage(fileUrlEmpG1,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("EMP_WORK_PROOF_PIC1", fileUrlEmpG1);//
					// 员工在职证明URL压缩后图片
				}

				String fileUrlEmpH = repayClryslerVO.getFileUrlEmpH();
				String fileUrlEmpH1 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpH)) {
					saleVhclInfo.setString("GWY_SALE_CONTRACT_PIC", fileUrlEmpH);// 公务员专用销售合同原件扫描件url1
					fileUrlEmpH1 = fileUrlEmpH.substring(fileUrlEmpH.lastIndexOf("/") + 1, fileUrlEmpH.length());
					// fileUrlEmpH1 = ImageResizer.resizeImage(fileUrlEmpH1,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("GWY_SALE_CONTRACT_PIC1", fileUrlEmpH1);//
					// 公务员专用销售合同原件扫描件url1压缩后图片
				}

				String fileUrlEmpI = repayClryslerVO.getFileUrlEmpI();
				String fileUrlEmpI1 = "";
				if (!CommonUtils.checkIsNull(fileUrlEmpI)) {
					saleVhclInfo.setString("GWY_WORK_CARD_PIC", fileUrlEmpI); // 公务员工作证原件扫描件url
					fileUrlEmpI1 = fileUrlEmpI.substring(fileUrlEmpI.lastIndexOf("/") + 1, fileUrlEmpI.length());
					// fileUrlEmpI1 = ImageResizer.resizeImage(fileUrlEmpI1,
					// 800, 800);// 弹出图片
					saleVhclInfo.setString("GWY_WORK_CARD_PIC1", fileUrlEmpI1); //
					// 公务员工作证原件扫描件url压缩后图片
				}

				saleVhclInfo.setString("VHCL_DELIVERY_DATE", reportPo.get("Sales_Date")); // 交车日期
				saleVhclInfo.setString("Invoice_Date", reportPo.get("Invoice_Date")); // 开票日期
				saleVhclInfo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE);
				saleVhclInfo.setLong("Update_By", DEConstant.DE_CREATE_BY); // 更新人
				saleVhclInfo.setTimestamp("Update_Date", format); // 更新时间
				saleVhclInfo.saveIt();
				// bigSaleVhclList.add(saleVhclInfo);
			}
			// factory.insert(bigSaleVhclList);
			logger.info("************* 成功写入实销车辆信息 *******************");
		}
		// 审批信息
		LazyList<TtBigCustomerRebateApprovalPO> rebateApprovalList = TtBigCustomerRebateApprovalPO.findBySQL(
				"Select * from tt_big_customer_rebate_approval where Ws_No=? and Dealer_Code=? and Enable=?",
				tBInfoDTo.getWsNo().trim(), dealerCode, OemDictCodeConstants.STATUS_ENABLE);

		if (null != rebateApprovalList && rebateApprovalList.size() > 0) {
			TtBigCustomerRebateApprovalPO rebateApprovalPo = new TtBigCustomerRebateApprovalPO();
			rebateApprovalPo.setInteger("Rebate_Approval_Status",
					OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED); // 未审批
			rebateApprovalPo.setInteger("Amount", tBInfoDTo.getAmount());// 申请数量
			rebateApprovalPo.setLong("Update_By", DEConstant.DE_CREATE_BY);
			rebateApprovalPo.setTimestamp("Update_Date", format);
			rebateApprovalPo.saveIt();
			// int upApprovalCount = factory.update(keyRebateApprovalPo,
			// rebateApprovalPo);
			// logger.info("*************
			// 成功更新报备单号("+repayVo.getWsNo()+")的审批信息,影响行数
			// "+upApprovalCount+"*******************");
		} else {
			TtBigCustomerRebateApprovalPO rebateApprovalPo = new TtBigCustomerRebateApprovalPO();
			// rebateApprovalPo.setApprovalId(factory.getLongPK(rebateApprovalPo));
			// // 主键
			rebateApprovalPo.setString("dealer_Code", dealerCode);
			rebateApprovalPo.setString("Rebate_Approval_Status",
					OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_UNAPPROVED); // 未审批
			rebateApprovalPo.setString("Big_Customer_Code", baseInfoList.get(0).get("Customer_Company_Code"));
			rebateApprovalPo.setString("Ws_No", tBInfoDTo.getWsNo());
			rebateApprovalPo.setInteger("Amount", tBInfoDTo.getAmount());// 申请数量
			rebateApprovalPo.setTimestamp("Report_Date", tBInfoDTo.getSubmitTime());
			rebateApprovalPo.setInteger("Enable", OemDictCodeConstants.STATUS_ENABLE);
			rebateApprovalPo.setLong("Create_By", DEConstant.DE_CREATE_BY);
			rebateApprovalPo.setTimestamp("Create_Date", format);
			rebateApprovalPo.saveIt();
			logger.info("*************************** END * 成功写入大客户报备返利审批表 ******************************");
		}

	}

	private TtVsSalesReportPO getSaleVhclInfo(String sono) {
		TtVsSalesReportPO saleVhclInfo = null;
		for (Map<String, TtVsSalesReportPO> map : reportSaleVhclList) {
			saleVhclInfo = map.get(sono);
			if (null != saleVhclInfo) {
				break;
			}
		}
		return saleVhclInfo;
	}

}
