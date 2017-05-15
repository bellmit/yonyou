package com.infoeai.eai.action.k4;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4VsBillingReturnPO;
import com.infoeai.eai.po.TtDealerAccountDtlPO;
import com.infoeai.eai.po.TtDealerAccountPO;
import com.infoeai.eai.vo.S0005VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0005Impl extends BaseService implements S0005 {
	private static final Logger logger = LoggerFactory.getLogger(S0005Impl.class);
	@Autowired
	K4SICommonDao dao;

	public List<S0005VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0005VO> voList = new ArrayList<S0005VO>();

		try {

			logger.info("====S0005 getXMLToVO() is START====");
			logger.info("====XML赋值到VO======");
			logger.info("====XMLSIZE:=======" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0005VO outVo = new S0005VO();
						outVo.setSoNo(map.get("SO_NO")); // 销售订单号
						outVo.setInvoiceType(map.get("INVOICE_TYPE")); // 发票类型
						outVo.setInvoiceNo(map.get("INVOICE_NO")); // 发票号码
						outVo.setBillingBy(map.get("BILLING_BY")); // 开票方
						outVo.setBillingDate(map.get("BILLING_DATE")); // 开票日期
						outVo.setBillingTime(map.get("BILLING_TIME")); // 开票时间
						outVo.setPostingStatus(map.get("POSTING_STATUS")); // 过账状态
						outVo.setPostingDate(map.get("POSTING_DATE")); // 过账日期
						outVo.setPostingTime(map.get("POSTING_TIME")); // 过账时间
						outVo.setVin(map.get("VIN")); // VIN
						outVo.setBillingNum(map.get("BILLING_NUM")); // 开票数量
						outVo.setBillingAmount(map.get("BILLING_AMOUNT")); // 开票金额
						outVo.setTaxAmount(map.get("TAX_AMOUNT")); // 税额
						outVo.setCancelInvoiceNo(map.get("CANCEL_INVOICE_NO")); // 被取消发票号码
						outVo.setRowId(map.get("ROW_ID")); // ROW_ID

						voList.add(outVo);

						logger.info("====outVo:====" + outVo);

					}
				}
			}

			logger.info("====S0005 getXMLToVO() is END====");
		} catch (Throwable e) {
			logger.info("==============XML赋值VO失败===================");
			logger.error(e.getMessage(), e);
			throw new Exception("S0005 XML转换处理异常！" + e);
		} finally {
			logger.info("====S0005 getXMLToVO() is finish====");

		}
		return voList;
	}

	/**
	 * 执行
	 * 
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0005VO> voList) throws Throwable {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("====S0005 is begin====");

		List<returnVO> retVoList = new ArrayList<returnVO>();

		String[] returnVo = null;

		/******************** 开启事物 ********************/
		dbService.beginTxn();

		try {

			for (int i = 0; i < voList.size(); i++) {

				returnVo = S0005Check(voList.get(i)); // 校验 S0005VO 数据

				if (null == returnVo || returnVo.length <= 0) {

					k4BusinessProcess(voList.get(i)); // S0005 数据业务处理逻辑

				} else {

					/*
					 * 车辆接口表错误数据插入
					 */
					TiK4VsBillingReturnPO po = new TiK4VsBillingReturnPO();
					/// po.setIfId(new Long(SequenceManager.getSequence("")));
					/// // 接口ID
					po.setString("So_No", voList.get(i).getSoNo()); // 销售订单号
					po.setString("Invoice_Type", voList.get(i).getInvoiceType()); // 发票类型
					po.setString("Invoice_No", voList.get(i).getInvoiceNo()); // 发票号码
					po.setString("Billing_By", voList.get(i).getBillingBy()); // 开票方
					po.setTimestamp("Billing_Date", voList.get(i).getBillingDate()); // 开票日期
					po.setTimestamp("Billing_Time", voList.get(i).getBillingTime()); // 开票时间
					po.setTimestamp("Posting_Status", voList.get(i).getPostingStatus()); // 过账状态
					po.setTimestamp("Posting_Date", voList.get(i).getPostingDate()); // 过账日期
					po.setTimestamp("Posting_Time", voList.get(i).getPostingTime()); // 过账时间
					po.setString("Vin", voList.get(i).getVin()); // VIN
					po.setString("Billing_Num", voList.get(i).getBillingNum()); // 开票数量
					po.setString("Billing_Amount", voList.get(i).getBillingAmount()); // 开票金额
					po.setString("Tax_Amount", voList.get(i).getTaxAmount()); // 税额
					po.setString("Cancel_Invoice_No", voList.get(i).getCancelInvoiceNo()); // 被取消发票号码
					po.setInteger("Row_Id", voList.get(i).getRowId()); // ROW_ID
					po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_NO.toString()); // 是否成功（否）
					po.setString("Is_Message", returnVo[1]); // 错误
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0005); // 创建人ID
					po.setTimestamp("Create_Date", format); // 创建日期
					po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
					po.insert();

					/*
					 * 返回错误信息
					 */
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);
					logger.info("==============S0005 返回不合格数据============RowId====" + returnVo[0]);
					logger.info("==============S0005 返回不合格数据============Message====" + returnVo[1]);
				}
			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0005业务处理异常！" + e);
		} finally {
			logger.info("====S0005 is finish====");
			dbService.clean();
		}
		return retVoList;
		/******************** 结束事物 ********************/
	}

	/**
	 * S0005数据校验逻辑
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private String[] S0005Check(S0005VO vo) throws Exception {

		logger.info("==============S0005 校验逻辑开始================");

		String[] returnVo = new String[2];

		if (vo.getInvoiceType().equals("YF50")) { // 国产车发票

			// ROW_ID 非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}

			// 销售订单号非空校验
			if (CheckUtil.checkNull(vo.getSoNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "销售订单号为空";
				return returnVo;
			}

			// 销售订单号校验

			List<TtVsOrderPO> vsOrderList = TtVsOrderPO.find("so_no=?", vo.getSoNo());
			if (null == vsOrderList || vsOrderList.size() <= 0) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "SO号无效：" + vo.getSoNo();
				return returnVo;
			} else {

				// 订单状态
				Integer orderStatus = Integer.parseInt(vsOrderList.get(0).get("Order_Status").toString());

				if (orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_08.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_16.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_07.intValue()) {

					/*
					 * public final static Integer SALE_ORDER_TYPE_08 =
					 * 70031008; // 已生成发货单 public final static Integer
					 * SALE_ORDER_TYPE_16 = 70031016; // 发票取消 订单状态非以上两个，则返回错误信息
					 */

					returnVo[0] = vo.getRowId();
					returnVo[1] = "YF50：不符合当前订单状态(" + orderStatus + ")";
					return returnVo;
				}
			}

			// 发票号码非空校验
			if (CheckUtil.checkNull(vo.getInvoiceNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "发票号码为空";
				return returnVo;
			}

			// 开票方非空校验
			if (CheckUtil.checkNull(vo.getBillingBy())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票方为空";
				return returnVo;
			}

			// 开票数量非空校验
			if (CheckUtil.checkNull(vo.getBillingNum())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票数量为空";
				return returnVo;
			}

			// 开票金额非空校验
			if (CheckUtil.checkNull(vo.getBillingAmount())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票金额为空";
				return returnVo;
			}

			// 税额非空校验
			if (CheckUtil.checkNull(vo.getTaxAmount())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "税额为空";
				return returnVo;
			}

		} else if (vo.getInvoiceType().equals("YS10")) { // 国产车取消发票

			// ROW_ID 非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}

			// 销售订单号非空校验
			if (CheckUtil.checkNull(vo.getSoNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "销售订单号为空";
				return returnVo;
			}

			// 销售订单号校验
			List<TtVsOrderPO> vsOrderList = TtVsOrderPO.find("so_no=?", vo.getSoNo());
			;

			if (null == vsOrderList || vsOrderList.size() <= 0) {

				returnVo[0] = vo.getRowId();
				returnVo[1] = "SO号无效：" + vo.getSoNo();
				return returnVo;

			} else {

				// 订单状态
				Integer orderStatus = Integer.parseInt(vsOrderList.get(0).get("Order_Status").toString());

				if (orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_09.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_10.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_11.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_12.intValue()) {

					/*
					 * public final static Integer SALE_ORDER_TYPE_09 =
					 * 70031009; // 已开票 public final static Integer
					 * SALE_ORDER_TYPE_10 = 70031010; // 已发运 public final static
					 * Integer SALE_ORDER_TYPE_11 = 70031011; // 已到店 public
					 * final static Integer SALE_ORDER_TYPE_12 = 70031012; //
					 * 已收车 订单状态非以上四个，则返回错误信息
					 */

					returnVo[0] = vo.getRowId();
					returnVo[1] = "YS10：不符合当前订单状态(" + orderStatus + ")";
					return returnVo;
				}
			}

			// 发票号码非空校验
			if (CheckUtil.checkNull(vo.getInvoiceNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "发票号码为空";
				return returnVo;
			}

			// 开票方非空校验
			if (CheckUtil.checkNull(vo.getBillingBy())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票方为空";
				return returnVo;
			}

			// 开票数量非空校验
			if (CheckUtil.checkNull(vo.getBillingNum())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票数量为空";
				return returnVo;
			}

			// 开票金额非空校验
			if (CheckUtil.checkNull(vo.getBillingAmount())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "开票金额为空";
				return returnVo;
			}

			// 税额非空校验
			if (CheckUtil.checkNull(vo.getTaxAmount())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "税额为空";
				return returnVo;
			}

		} else if (vo.getInvoiceType().equals("YR50")) { // 国产车退货发票
			return null;
		} else if (vo.getInvoiceType().equals("S2")) { // 取消贷项凭证
			return null;
		} else if (CheckUtil.checkNull(vo.getInvoiceType())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发票类型为空";
			return returnVo;
		} else {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发票类型无效";
			return returnVo;
		}

		return null;
	}

	/*
	 * S0005 数据业务处理逻辑
	 */
	public void k4BusinessProcess(S0005VO vo) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("==============S0005 业务处理逻辑开始================");

		/*
		 * 1.根据销售订单号，找到对应的订单号。根据接口返回字段，更新相应信息。
		 * 2.通过销售订单号找到的订单号查找对应的经销商，然后到经销商账户异动明细表里，根据对应的开票信息插入一条数据。
		 * 3.根据销售订单里的付款方式，往账户异动明细里插入该经销商的一条账户异动信息。账户类型=付款方式类型。
		 */

		// 插入接口表
		TiK4VsBillingReturnPO po = new TiK4VsBillingReturnPO();
		// po.setIfId(Long.parseLong(SequenceManager.getSequence(""))); // 接口ID
		po.setString("So_No", vo.getSoNo()); // 销售订单号
		po.setString("Invoice_Type", vo.getInvoiceType()); // 发票类型
		po.setString("Invoice_No", vo.getInvoiceNo()); // 发票号码
		po.setString("Billing_By", vo.getBillingBy()); // 开票方
		po.setString("Billing_Date", vo.getBillingDate()); // 开票日期
		po.setString("Billing_Time", vo.getBillingTime()); // 开票时间
		po.setString("Posting_Status", vo.getPostingStatus()); // 过账状态
		po.setString("Posting_Date", vo.getPostingDate()); // 过账日期
		po.setString("Posting_Time", vo.getPostingTime()); // 过账时间
		po.setString("Vin", vo.getVin()); // VIN
		po.setString("Billing_Num", vo.getBillingNum()); // 开票数量
		po.setString("Billing_Amount", vo.getBillingAmount()); // 开票金额
		po.setString("Tax_Amount", vo.getTaxAmount()); // 税额
		po.setString("Cancel_Invoice_No", vo.getCancelInvoiceNo()); // 被取消发票号码
		po.setString("Row_Id", vo.getRowId()); // ROW_ID
		po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_YES.toString()); // 是否成功（是）
		// po.setIsMessage("校验通过"); // 消息
		po.setInteger("Create_By", OemDictCodeConstants.K4_S0005); // 创建人ID
		po.setTimestamp("Create_Date", new Date()); // 创建日期
		po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
		po.insert();

		String invoiceDate = (vo.getBillingDate() + vo.getBillingTime()).toString();
		Date invoiceD = DateUtil.yyyyMMddHHmmss2Date(invoiceDate);
		Timestamp changeDate = new Timestamp(invoiceD.getTime());

		// 根据车架号查询车辆ID

		List<TmVehiclePO> vehiclePoList = TmVehiclePO.find("vin=?", vo.getVin());
		Long vehicleId = 0l; // 车辆ID
		if (null != vehiclePoList && vehiclePoList.size() > 0) {
			vehicleId = (Long) vehiclePoList.get(0).get("Vehicle_Id");
		}

		if (vo.getInvoiceType().equals("YF50")) { // 国产车发票

			// 更新订单表
			TtVsOrderPO setPo = TtVsOrderPO.findFirst("So_No=?", vo.getSoNo());
			setPo.setInteger("Order_Status", OemDictCodeConstants.SALE_ORDER_TYPE_09); // 销售订单状态：已开票
			setPo.setString("Invoice_No", vo.getInvoiceNo()); // 发票号
			setPo.setTimestamp("Invoice_Date", DateUtil.yyyyMMddHHmmss2Date(invoiceDate)); // 开票日期
			setPo.setDouble("Billing_Amount", Double.parseDouble(vo.getBillingAmount())); // 开票金额
			setPo.setDouble("Tax_Amount", Double.parseDouble(vo.getTaxAmount())); // 税额
			setPo.setInteger("Update_By", OemDictCodeConstants.K4_S0005); // 更新人
			setPo.setTimestamp("Update_Date", format); // 更新时间
			setPo.saveIt();

			// 查询订单表

			List<TtVsOrderPO> vsOrderList = TtVsOrderPO.find("So_No=?", vo.getSoNo());

			// 经销商账户表
			TtDealerAccountPO dealerAccount = new TtDealerAccountPO();
			dealerAccount.setLong("Dealer_Id", vsOrderList.get(0).get("Dealer_Id"));
			dealerAccount.setString("Acc_Type", vsOrderList.get(0).get("Payment_Type"));
			List<TtDealerAccountPO> dealerAccountList = TtDealerAccountPO.find("Dealer_Id=? and Payment_Type=?",
					vsOrderList.get(0).get("Dealer_Id"), vsOrderList.get(0).get("Payment_Type"));

			Long accId = 0l;
			if (dealerAccountList != null && dealerAccountList.size() > 0) {
				accId = (Long) dealerAccountList.get(0).get("Acc_Id");
			} else {
				// accId = new Long(SequenceManager.getSequence(""));
				dealerAccount.setLong("Acc_Id", accId);
				dealerAccount.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
				dealerAccount.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
				dealerAccount.setTimestamp("Create_Date", new Date());
				dealerAccount.setInteger("Create_By", OemDictCodeConstants.K4_S0005);
				dealerAccount.insert();
			}

			// 经销商账户异动明细表
			TtDealerAccountDtlPO dealerAccountDtl = new TtDealerAccountDtlPO();
			// dealerAccountDtl.setAccDtlId(factory.getLongPK(dealerAccountDtl));
			// // 账户异动明细ID
			dealerAccountDtl.setLong("Acc_Id", accId); // accId
			dealerAccountDtl.setInteger("Opera_Type", OemDictCodeConstants.ACCOUNT_OPPER_TYPE_04); // 操作类型

			String operaDate = (vo.getBillingDate() + vo.getBillingTime()).toString();
			dealerAccountDtl.setTimestamp("Opera_Date", DateUtil.yyyyMMddHHmmss2Date(operaDate)); // 操作日期

			dealerAccountDtl.setString("Invoice_Type", getK4InvoiceType(vo.getInvoiceType())); // 发票类型
			dealerAccountDtl.setString("Invoice_No", vo.getInvoiceNo()); // 发票号码
			dealerAccountDtl.setString("Billing_By", vo.getBillingBy()); // 开票方
			dealerAccountDtl.setString("Posting_Status", Integer.parseInt(vo.getPostingStatus())); // 过账状态
			// 1：财务会计凭证生成
			// 0：未生成

			String postingDate = (vo.getPostingDate() + vo.getPostingTime()).toString();
			dealerAccountDtl.setTimestamp("Posting_Date", DateUtil.yyyyMMddHHmmss2Date(postingDate)); // 过账日期

			dealerAccountDtl.setString("Billing_Num", Integer.parseInt(vo.getBillingNum())); // 开票数量
			dealerAccountDtl.setString("Amount", Double.parseDouble(vo.getBillingAmount())); // 开票金额
			dealerAccountDtl.setString("Tax_Amount", Double.parseDouble(vo.getTaxAmount())); // 税额
			dealerAccountDtl.setString("Cancel_Invoice_No", vo.getCancelInvoiceNo()); // 被取消发票号码
			dealerAccountDtl.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE); // 状态有效
			dealerAccountDtl.setInteger("Create_By", OemDictCodeConstants.K4_S0005); // 创建人ID
			dealerAccountDtl.setTimestamp("Create_Date", format); // 创建时间
			dealerAccountDtl.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
			dealerAccountDtl.insert();

			// 更新车辆表状态

			TmVehiclePO tpo = TmVehiclePO.findFirst("vin=?", vo.getVin());
			tpo.setInteger("Node_Status", OemDictCodeConstants.K4_VEHICLE_NODE_14);
			tpo.setInteger("Life_Cycle", OemDictCodeConstants.LIF_CYCLE_03);
			tpo.setTimestamp("Node_Date", DateUtil.yyyyMMddHHmmss2Date(invoiceDate));
			tpo.saveIt();

			// 更新车辆节点日期记录表（存储日期）
			TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("vehicle_Id=?",
					vehicleId);
			setVehicleNodeHistoryPo.setTimestamp("Youi_Date", changeDate); // 已开票日期
			setVehicleNodeHistoryPo.setInteger("Is_Del", 0);
			setVehicleNodeHistoryPo.setInteger("Update_By", OemDictCodeConstants.K4_S0005);
			setVehicleNodeHistoryPo.setTimestamp("Update_Date", format);
			setVehicleNodeHistoryPo.saveIt();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vin", vo.getVin());
			params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_14);
			params.put("changeName", "已开票");
			params.put("changeDate", changeDate);
			params.put("changeDesc", "SSAP已开票给经销商");
			params.put("createBy", OemDictCodeConstants.K4_S0005);
			dao.insertVehicleChange(params);

			// update_date 20151208 by maxiang begin..
			CommonDAO.insertHistory((Long) vsOrderList.get(0).get("Order_Id"), OemDictCodeConstants.SALE_ORDER_TYPE_09,
					"已开票", "SAP", OemDictCodeConstants.K4_S0005, "");
			// update_date 20151208 by maxiang end..

		} else if (vo.getInvoiceType().equals("YS10")) { // 国产车取消发票

			StringBuffer orderSql = new StringBuffer();
			orderSql.append(" \n");
			orderSql.append("UPDATE TT_VS_ORDER O \n");
			orderSql.append("   SET O.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_16 + "', \n");
			orderSql.append("       O.INVOICE_NO = NULL, \n");
			orderSql.append("       O.INVOICE_DATE = NULL, \n");
			orderSql.append("       O.BILLING_AMOUNT = NULL, \n");
			orderSql.append("       O.TAX_AMOUNT = NULL, \n");
			orderSql.append("       O.UPDATE_BY = '" + OemDictCodeConstants.K4_S0005 + "', \n");
			orderSql.append("       O.UPDATE_DATE = CURRENT TIMESTAMP \n");
			orderSql.append(" WHERE O.SO_NO = '" + vo.getSoNo() + "' \n");
			OemDAOUtil.execBatchPreparement(orderSql.toString(), new ArrayList<>());

			// 获得经销商账户信息
			List<Map<String, String>> dealerAccountList = this.getDealerAccountInfo(vo.getSoNo());

			Long accId = 0l;
			Long orderId = 0l;
			if (dealerAccountList != null && dealerAccountList.size() > 0) {
				// 转型经销商账户ID
				String strAccId = dealerAccountList.get(0).get("ACC_ID");
				accId = Long.parseLong(strAccId);
				// 转型订单ID
				String strOrderId = dealerAccountList.get(0).get("ORDER_ID");
				orderId = Long.parseLong(strOrderId);
			} else {

				// accId = new Long(SequenceManager.getSequence(""));

				TtDealerAccountPO dealerAccount = new TtDealerAccountPO();
				// dealerAccount.setAccId(accId); // 主键ID
				dealerAccount.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除标识
				dealerAccount.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE); // 有效状态
				dealerAccount.setInteger("Create_By", OemDictCodeConstants.K4_S0005); // 创建人ID
				dealerAccount.setTimestamp("CreateDate", format); // 创建日期
				dealerAccount.insert();
			}

			// 经销商账户异动明细表
			TtDealerAccountDtlPO dealerAccountDtl = new TtDealerAccountDtlPO();
			// dealerAccountDtl.setAccDtlId(factory.getLongPK(dealerAccountDtl));
			// // 账户异动明细ID
			dealerAccountDtl.setLong("Acc_Id", accId); // accId
			dealerAccountDtl.setString("Opera_Type", OemDictCodeConstants.ACCOUNT_OPPER_TYPE_04); // 操作类型

			String operaDate = (vo.getBillingDate() + vo.getBillingTime()).toString();
			dealerAccountDtl.setTimestamp("Opera_Date", DateUtil.yyyyMMddHHmmss2Date(operaDate)); // 操作日期

			dealerAccountDtl.setInteger("Invoice_Type", getK4InvoiceType(vo.getInvoiceType())); // 发票类型
			dealerAccountDtl.setString("Invoice_No", vo.getInvoiceNo()); // 发票号码
			dealerAccountDtl.setString("Billing_By", vo.getBillingBy()); // 开票方

			dealerAccountDtl.setString("Posting_Status", Integer.parseInt(vo.getPostingStatus())); // 过账状态
			// 1：财务会计凭证生成
			// 0：未生成

			String postingDate = (vo.getPostingDate() + vo.getPostingTime()).toString();
			dealerAccountDtl.setTimestamp("Posting_Date", DateUtil.yyyyMMddHHmmss2Date(postingDate)); // 过账日期

			dealerAccountDtl.setString("Billing_Num", Integer.parseInt(vo.getBillingNum())); // 开票数量
			dealerAccountDtl.setString("Amount", Double.parseDouble(vo.getBillingAmount())); // 开票金额
			dealerAccountDtl.setString("Tax_Amount", Double.parseDouble(vo.getTaxAmount())); // 税额
			dealerAccountDtl.setString("Cancel_Invoice_No", vo.getCancelInvoiceNo()); // 被取消发票号码
			dealerAccountDtl.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE); // 状态有效
			dealerAccountDtl.setInteger("Create_By", OemDictCodeConstants.K4_S0005); // 创建人ID
			dealerAccountDtl.setTimestamp("Create_Date", format); // 创建时间
			dealerAccountDtl.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
			dealerAccountDtl.insert();

			// 更新车辆表状态

			TmVehiclePO tpo = TmVehiclePO.findFirst("vin=?", vo.getVin());
			tpo.setInteger("Node_Status", OemDictCodeConstants.K4_VEHICLE_NODE_13);
			tpo.setInteger("Life_Cycle", OemDictCodeConstants.LIF_CYCLE_02);
			tpo.setTimestamp("Node_Date", DateUtil.yyyyMMddHHmmss2Date(invoiceDate));
			tpo.saveIt();

			// 更新车辆节点日期记录表（存储日期）
			TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("Vehicle_Id=?",
					vehicleId);
			setVehicleNodeHistoryPo.setTimestamp("Yoqx_Date", changeDate); // 发票取消日期
			setVehicleNodeHistoryPo.setInteger("Is_Del", 0);
			setVehicleNodeHistoryPo.setInteger("Update_By", OemDictCodeConstants.K4_S0005);
			setVehicleNodeHistoryPo.setTimestamp("Update_Date", format);
			setVehicleNodeHistoryPo.saveIt();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vin", vo.getVin());
			params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_13);
			params.put("changeName", "发票取消");
			params.put("changeDate", changeDate);
			params.put("changeDesc", "发票冲销");
			params.put("createBy", OemDictCodeConstants.K4_S0005);
			dao.insertVehicleChange(params);

			// update_date 20151208 by maxiang begin..
			CommonDAO.insertHistory(orderId, OemDictCodeConstants.SALE_ORDER_TYPE_16, "发票取消", "SAP",
					OemDictCodeConstants.K4_S0005, "");
			// update_date 20151208 by maxiang end..

		} else if (vo.getInvoiceType().equals("YR50")) { // 国产车退货发票
			// 不做处理
		} else if (vo.getInvoiceType().equals("S2")) { // 取消贷项凭证
			// 不做处理
		} else {
			// 不做处理
		}

		logger.info("==============S0005 业务处理逻辑结束================");

	}

	/**
	 * 获得发票类型
	 * 
	 * @param code
	 * @return
	 */
	private int getK4InvoiceType(String code) throws Exception {

		if (code.equals("YF50")) {
			return OemDictCodeConstants.K4_INVOICE_TYPE_01; // 国产车发票
		} else if (code.equals("YS10")) {
			return OemDictCodeConstants.K4_INVOICE_TYPE_02; // 国产车取消发票
		} else if (code.equals("YR50")) {
			return OemDictCodeConstants.K4_INVOICE_TYPE_03; // 国产车退货发票
		} else {
			return OemDictCodeConstants.K4_INVOICE_TYPE_04; // 取消贷项凭证(S2)
		}
	}

	/**
	 * 获得经销商账户信息
	 * 
	 * @param soNo
	 * @return
	 */
	public List<Map<String, String>> getDealerAccountInfo(String soNo) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DA.ACC_ID, -- 主键ID \n");
		sql.append("       O.ORDER_ID -- 订单ID \n");
		sql.append("  FROM TT_DEALER_ACCOUNT DA \n");
		sql.append(" INNER JOIN TT_VS_ORDER O ON O.DEALER_ID = DA.DEALER_ID AND O.PAYMENT_TYPE = DA.ACC_TYPE \n");
		sql.append(" WHERE O.SO_NO = '" + soNo + "' \n");
		Map findFirst = OemDAOUtil.findFirst(sql.toString(), null);
		List<Map<String, String>> ll = new ArrayList<>();
		ll.add(findFirst);

		return ll;
	}

}
