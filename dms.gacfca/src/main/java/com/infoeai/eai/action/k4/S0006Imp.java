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
import com.infoeai.eai.po.TiK4VsOrderTransPO;
import com.infoeai.eai.vo.S0006VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dcs.gacfca.SADCS001Cloud;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderTransPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0006Imp extends BaseService implements S0006 {

	private static final Logger logger = LoggerFactory.getLogger(S0006Imp.class);
	@Autowired
	K4SICommonDao dao;
	@Autowired
	SADCS001Cloud osc;

	/**
	 * 执行入口
	 * 
	 * @param voList
	 * @return
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0006VO> voList) throws Throwable {

		logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], start..");
		logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 开启事务!");
		dbService.beginTxn();

		List<returnVO> retVoList = new ArrayList<returnVO>();
		String[] returnVo = null;

		try {

			for (int i = 0; i < voList.size(); i++) {

				S0006VO s0006 = new S0006VO();
				s0006 = voList.get(i);

				// 校验S0006数据逻辑
				returnVo = s0006Check(s0006);

				if (returnVo == null) {

					// 插入接口表
					s0006.setIsResult(OemDictCodeConstants.IF_TYPE_YES.toString());
					this.saveTiTable(s0006);

					// 一下是业务处理方法方法参数是循环出voList的vo
					k4BusinessProcess(s0006);

				} else {

					// 插入接口表
					s0006.setIsResult(OemDictCodeConstants.IF_TYPE_NO.toString());
					s0006.setIsMessage(returnVo[1]);
					this.saveTiTable(s0006);

					// 返回错误信息
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);

					logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 不合格数据RowId:" + returnVo[0]);
					logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 不合格数据Message:" + returnVo[1]);

				}
			}

			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], success!");
			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 提交事务!");
			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], exception!");
			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 回滚事务!");
			dbService.endTxn(false);
		} finally {
			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], finish.");
			logger.info("logger.info ->>> 国产车车辆发运信息接收[SAP->EAI->DCS], 清除事务!");
			dbService.clean();
		}

		return retVoList;
	}

	/*
	 * S0006数据校验逻辑
	 */
	private String[] s0006Check(S0006VO vo) {

		// 将错误的数据rowID放入returnVo[0],message放入returnVo[1]中
		String[] returnVo = new String[2];

		if (null == vo.getSoNo() || "".equals(vo.getSoNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "销售订单号为空值";
			return returnVo;
		} else {
			List<TtVsOrderPO> orderList = TtVsOrderPO.find("So_No=?", vo.getSoNo());
			if (null == orderList || orderList.size() <= 0) {

				returnVo[0] = vo.getRowId();
				returnVo[1] = "销售订单号在订单表中不存在";
				return returnVo;

			} else {

				// 订单状态
				Integer orderStatus = (Integer) orderList.get(0).get("Order_Status");

				// 订单状态判断（已到店或已收车的状态下只更新发运信息和历史信息，不更改订单状态和车辆节点状态）
				if (orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_09.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_11.intValue()
						&& orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_12.intValue()) {

					/*
					 * public final static Integer SALE_ORDER_TYPE_09 =
					 * 70031009; // 已开票 public final static Integer
					 * SALE_ORDER_TYPE_11 = 70031011; // 已到店 public final static
					 * Integer SALE_ORDER_TYPE_12 = 70031012; // 已收车 订单状态非以上三种,
					 * 则返回错误信息
					 */

					returnVo[0] = vo.getRowId();
					returnVo[1] = "不符合当前订单状态(" + orderStatus + ")";
					return returnVo;
				}
			}
		}

		if (null == vo.getRowId() || "".equals(vo.getRowId())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "ROWID为空值";
			return returnVo;
		}

		if (null == vo.getSoNo() || "".equals(vo.getSoNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "销售订单号为空值";
			return returnVo;
		}

		if (null == vo.getDnNo() || "".equals(vo.getDnNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单号为空值";
			return returnVo;
		}

		if (null == vo.getVin() || "".equals(vo.getVin())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "vin码为空值";
			return returnVo;
		} else if (vo.getVin().length() != 17) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = vo.getVin() + "的vin码长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getTransNo() || "".equals(vo.getTransNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运单号为空值";
			return returnVo;
		}

		if (null == vo.getTransCreateDate() || "".equals(vo.getTransCreateDate())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运单创建日期为空值";
			return returnVo;
		} else if (vo.getTransCreateDate().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运单创建日期长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getTransCreateTime() || "".equals(vo.getTransCreateTime())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运单创建时间为空值";
			return returnVo;
		} else if (vo.getTransCreateTime().length() != 6) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运单创建时间长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDepartureDate() || "".equals(vo.getDepartureDate())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发车日期为空值";
			return returnVo;
		} else if (vo.getDepartureDate().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发车日期长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDepartureTime() || "".equals(vo.getDepartureTime())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发车时间为空值";
			return returnVo;
		} else if (vo.getDepartureTime().length() != 6) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发车时间长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getExpectedDate() || "".equals(vo.getExpectedDate())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "预计到店日期为空值";
			return returnVo;
		}

		if (null == vo.getTransAgentCode() || "".equals(vo.getTransAgentCode())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运输商编号为空值";
			return returnVo;
		}

		if (null == vo.getTransAgentName() || "".equals(vo.getTransAgentName())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运输商名称为空值";
			return returnVo;
		}

		if (null == vo.getTransType() || "".equals(vo.getTransType())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "运输类型为空值";
			return returnVo;
		}

		if (null == vo.getReceivesDealerCode() || "".equals(vo.getReceivesDealerCode())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "送达方为空值";
			return returnVo;

		} else {
			Map<String, Object> map = dao.getDealerByFCACode(vo.getReceivesDealerCode());
			if (null == map || map.size() <= 0) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "送达方在系统中不存在";
				return returnVo;
			}
		}

		if (null == vo.getSapTransNo() || "".equals(vo.getSapTransNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "销售运单号为空值";
			return returnVo;
		}

		return null;
	}

	/*
	 * S0006数据业务处理逻辑
	 */
	public void k4BusinessProcess(S0006VO s0006Vo) throws Throwable {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("==========S0006 业务处理逻辑开始==========");

		Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(s0006Vo.getVin()); // 车辆信息
		String vehicleIdStr = "";
		Long vehicleId = 0l;
		if (null != vehicleInfo) {
			vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();
			vehicleId = Long.parseLong(vehicleIdStr); // 车辆ID
		}

		// 运单创建日期
		String transCreateDate = s0006Vo.getTransCreateDate() + s0006Vo.getTransCreateTime();
		Date nodeDate = DateUtil.yyyyMMddHHmmss2Date(transCreateDate);
		Timestamp changeDate = new Timestamp(nodeDate.getTime());

		// 运单发运日期
		String departureDateStr = s0006Vo.getDepartureDate() + s0006Vo.getDepartureTime();
		Date departureDate = DateUtil.yyyyMMddHHmmss2Date(departureDateStr);

		Long dealerId = Long
				.valueOf(dao.getDealerByFCACode(s0006Vo.getReceivesDealerCode()).get("DEALER_ID").toString());
		String dealerCode = dao.getDealerByFCACode(s0006Vo.getReceivesDealerCode()).get("DEALER_CODE").toString();

		// 新增发运信息
		TtVsOrderTransPO orderTrans = new TtVsOrderTransPO();
		// orderTrans.setTransId(new Long(SequenceManager.getSequence("")));
		orderTrans.setString("Trans_Agent_Code", s0006Vo.getTransAgentCode());
		orderTrans.setString("Trans_Agent_Name", s0006Vo.getTransAgentName());
		orderTrans.setTimestamp("Trans_Date", nodeDate);
		orderTrans.setString("Trans_No", s0006Vo.getTransNo());
		orderTrans.setInteger("Trans_Type", new Integer(this.getK4TransType(s0006Vo.getTransType())));
		orderTrans.setString("Trans_Vehicle_No", s0006Vo.getTransVehicleNo());
		orderTrans.setString("Departure_Date", departureDate);
		orderTrans.setString("Driver_Name", s0006Vo.getDriverName());
		orderTrans.setString("Driver_Tel", s0006Vo.getDriverTel());
		orderTrans.setTimestamp("Expected_Date", s0006Vo.getExpectedDate());
		orderTrans.setString("Vin", s0006Vo.getVin());
		orderTrans.setLong("Receives_Dealer_Id", dealerId);
		orderTrans.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
		orderTrans.setInteger("Create_By", OemDictCodeConstants.K4_S0006);
		orderTrans.setTimestamp("Create_Date", format);
		orderTrans.insert();

		// 发运单取消重新创建情况下
		String orderTransNo = null;
		// TtVsOrderPO vsOrderTrans = new TtVsOrderPO();
		List<TtVsOrderPO> votList = TtVsOrderPO.find("vin=? and So_No=?", s0006Vo.getVin(), s0006Vo.getSoNo());

		if (null != votList && votList.size() > 0) {

			TtVsOrderPO vsOrderTrans = votList.get(0);
			orderTransNo = vsOrderTrans.get("Trans_No").toString();

			CommonDAO.insertHistory((Long) votList.get(0).get("Order_Id"), OemDictCodeConstants.SALE_ORDER_TYPE_10,
					"已发运", "SAP", OemDictCodeConstants.K4_S0006, "");

		}

		if (null != orderTransNo && orderTransNo.length() > 0) {

			// 更新订单表条件

			TtVsOrderPO setOrder = TtVsOrderPO.findFirst("vin=? and so_no=?", s0006Vo.getVin(), s0006Vo.getSoNo());
			// 更新订单表内容
			setOrder.setTimestamp("Deliver_Order_Date", nodeDate); // 发运指令创建时间
			setOrder.setString("Trans_No", s0006Vo.getTransNo()); // 运单号
			setOrder.setTimestamp("Eta_Date", Utility.parseString2DateTime(s0006Vo.getExpectedDate(), "yyyyMMdd")); // 预计到达日期
			setOrder.setTimestamp("Stockout_Dealer_Date", departureDate); // 车辆发运日期
			setOrder.setInteger("Update_By", OemDictCodeConstants.K4_S0006); // 更新人ID
			setOrder.setTimestamp("Update_Date", format); // 更新日期
			setOrder.saveIt();

		} else {

			// 订单状态
			Integer orderStatus = (Integer) votList.get(0).get("Order_Status");

			// 订单状态判断（已到店或已收车的状态下只更新发运信息和历史信息，不更改订单状态和车辆节点状态）
			if (orderStatus == OemDictCodeConstants.SALE_ORDER_TYPE_09.intValue()) { // 已开票

				// 更新订单表条件
				TtVsOrderPO setOrder = TtVsOrderPO.findFirst("vin=? and So_No", s0006Vo.getVin(), s0006Vo.getSoNo());
				// 更新订单表内容
				setOrder.setTimestamp("Deliver_Order_Date", nodeDate); // 发运指令创建时间
				setOrder.setString("Trans_No", s0006Vo.getTransNo()); // 运单号
				setOrder.setTimestamp("Eta_Date", Utility.parseString2DateTime(s0006Vo.getExpectedDate(), "yyyyMMdd")); // 预计到达日期
				setOrder.setTimestamp("Stockout_Dealer_Date", departureDate); // 车辆发运日期
				setOrder.setInteger("Order_Status", OemDictCodeConstants.SALE_ORDER_TYPE_10);
				setOrder.setInteger("Update_By", OemDictCodeConstants.K4_S0006); // 更新人ID
				setOrder.setTimestamp("Update_Date", format); // 更新日期
				setOrder.saveIt();

				// 更新车辆表条件
				TmVehiclePO tpo = TmVehiclePO.findFirst("vin=?", s0006Vo.getVin());
				// 更新车辆表内容
				tpo.setInteger("NodeStatus", OemDictCodeConstants.K4_VEHICLE_NODE_17); // 车辆节点状态
				tpo.setInteger("LifeCycle", OemDictCodeConstants.LIF_CYCLE_03); // 车辆生命周期
				tpo.setTimestamp("ZbilDate", departureDate); // 一次开票日期 = 发车日期
				tpo.setTimestamp("StockoutDealerDate", departureDate); // 发运日期 =
																		// 发车日期
				tpo.setTimestamp("NodeDate", nodeDate);
				tpo.saveIt();

			} else if (orderStatus == OemDictCodeConstants.SALE_ORDER_TYPE_11.intValue() || // 已到店
					orderStatus == OemDictCodeConstants.SALE_ORDER_TYPE_12.intValue()) { // 已收车

				// 更新订单表条件

				TtVsOrderPO setOrder = TtVsOrderPO.findFirst("SoNo=? and vin=?", s0006Vo.getSoNo(), s0006Vo.getVin());
				// 更新订单表内容
				setOrder.setTimestamp("Deliver_Order_Date", nodeDate); // 发运指令创建时间
				setOrder.setString("Trans_No", s0006Vo.getTransNo()); // 运单号
				setOrder.setTimestamp("Eta_Date", Utility.parseString2DateTime(s0006Vo.getExpectedDate(), "yyyyMMdd")); // 预计到达日期
				setOrder.setTimestamp("Stockout_Dealer_Date", departureDate); // 车辆发运日期
				setOrder.setInteger("Update_By", OemDictCodeConstants.K4_S0006); // 更新人ID
				setOrder.setTimestamp("Update_Date", format); // 更新日期
				setOrder.saveIt();

				// 更新车辆表条件
				TmVehiclePO tpo = TmVehiclePO.findFirst("vin=?", s0006Vo.getVin());
				// 更新车辆表内容
				tpo.setTimestamp("Zbil_Date", departureDate); // 一次开票日期 = 发车日期
				tpo.setTimestamp("Stockout_Dealer_Date", departureDate); // 发运日期
																			// =
																			// 发车日期
				tpo.saveIt();

			} else {

			}

			// 更新车辆节点日期记录表条件
			TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("Vehicle_Id", vehicleId);
			// 更新车辆节点日期记录表内容
			setVehicleNodeHistoryPo.setTimestamp("Zvhc_Date", changeDate); // 已发运日期
			setVehicleNodeHistoryPo.setInteger("Is_Del", 0); // 逻辑删除[0:不删除,1:删除]
			setVehicleNodeHistoryPo.setInteger("Update_By", OemDictCodeConstants.K4_S0006); // 更新人ID
			setVehicleNodeHistoryPo.setTimestamp("Update_Date", format); // 更新日期
			setVehicleNodeHistoryPo.saveIt();

			// 新增车辆节点变更记录表数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vin", s0006Vo.getVin());
			params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_17);
			params.put("changeName", "已发运");
			params.put("changeDate", changeDate);
			params.put("changeDesc", "物流已发运");
			params.put("createBy", OemDictCodeConstants.K4_S0006);
			dao.insertVehicleChange(params);

		}

		/*
		 * 下发所有车辆（不再限制菲亚特车辆） update maxiang 20160706 begin..
		 */

		// 非菲亚特车辆查询
		// Map<String, Object> vehiclePoMap =
		// dao.getNotFiatVehicle(s0006Vo.getVin());

		// 下发DMS
		// if (null != vehiclePoMap) {
		// SADCS001 osc = new SADCS001();
		// osc.sendData(dealerCode, dealerId.toString(),
		// vehiclePoMap.get("VEHICLE_ID").toString());
		// }
		List<TmVehiclePO> vehiclePoList = TmVehiclePO.find("vin=?", s0006Vo.getVin());
		// 下发DMS
		if (null != vehiclePoList && vehiclePoList.size() > 0) {
			osc.sendData(dealerCode, dealerId.toString(), vehiclePoList.get(0).get("Vehicle_Id").toString());
		}

		/*
		 * 下发所有车辆（不再限制菲亚特车辆） update maxiang 20160706 end..
		 */

		logger.info("==========S0006 业务处理逻辑结束==========");

	}

	public List<S0006VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0006VO> voList = new ArrayList<S0006VO>();

		try {

			logger.info("==========S0006 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0006VO outVo = new S0006VO();
						outVo.setTransNo(map.get("TRANS_NO")); // 运单号
						outVo.setTransCreateDate(map.get("TRANS_CREATE_DATE")); // 运单创建日期
						outVo.setTransCreateTime(map.get("TRANS_CREATE_TIME")); // 运单创建时间
						outVo.setDepartureDate(map.get("DEPARTURE_DATE")); // 发车日期
						outVo.setDepartureTime(map.get("DEPARTURE_TIME")); // 发车时间
						outVo.setExpectedDate(map.get("EXPECTED_DATE")); // 预计到店日期
						outVo.setTransAgentCode(map.get("TRANS_AGENT_CODE")); // 运输商编号
						outVo.setTransAgentName(map.get("TRANS_AGENT_NAME")); // 运输商名称
						outVo.setTransType(map.get("TRANS_TYPE")); // 运输类型
						outVo.setTransVehicleNo(map.get("TRANS_VEHICLE_NO")); // 挂车牌号
						outVo.setDriverName(map.get("DRIVER_NAME")); // 司机姓名
						outVo.setDriverTel(map.get("DRIVER_TEL")); // 司机电话
						outVo.setReceivesDealerCode(map.get("RECEIVES_DEALER_CODE")); // 送达方
						outVo.setSoNo(map.get("SO_NO")); // 销售订单号
						outVo.setDnNo(map.get("DN_NO")); // 销售发货单号
						outVo.setSapTransNo(map.get("SAP_TRANS_NO")); // 销售运单号
						outVo.setVin(map.get("VIN")); // 车架号
						outVo.setRowId(map.get("ROW_ID")); // ROWID
						voList.add(outVo);

						logger.info("==========outVo:==========" + outVo);

					}
				}
			}

			logger.info("==========S0006 getXMLToVO() is END==========");

		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0006 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0006 getXMLToVO() is finish==========");
		}
		return voList;
	}

	/**
	 * 写入订单发运信息接口表
	 * 
	 * @param vo
	 */
	public void saveTiTable(S0006VO vo) {

		TiK4VsOrderTransPO orderTrans = new TiK4VsOrderTransPO();

		// orderTrans.setIfId(new Long(SequenceManager.getSequence("")));
		orderTrans.setString("Trans_No", vo.getTransNo()); // 运单号
		orderTrans.setTimestamp("Trans_Create_Date", vo.getTransCreateDate()); // 运单创建日期
		orderTrans.setTimestamp("Trans_Create_Time", vo.getTransCreateTime()); // 运单创建时间
		orderTrans.setTimestamp("Departure_Date", vo.getDepartureDate()); // 发车日期
		orderTrans.setTimestamp("Departure_Time", vo.getDepartureTime()); // 发车时间
		orderTrans.setTimestamp("Expected_Date", vo.getExpectedDate()); // 预计到店日期
		orderTrans.setString("Trans_Agent_Code", vo.getTransAgentCode()); // 运输商编号
		orderTrans.setString("Trans_Agent_Name", vo.getTransAgentName()); // 运输商名称
		orderTrans.setString("Trans_Type", vo.getTransType()); // 运输类型
		orderTrans.setString("Trans_Vehicle_No", vo.getTransVehicleNo()); // 挂车牌号
		orderTrans.setString("Driver_Name", vo.getDriverName()); // 司机姓名
		orderTrans.setString("Driver_Tel", vo.getDriverTel()); // 司机电话
		orderTrans.setString("Receives_Dealer_Code", vo.getReceivesDealerCode()); // 送达方
		orderTrans.setString("So_No", vo.getSoNo()); // 销售订单号
		orderTrans.setString("Dn_No", vo.getDnNo()); // 销售发货单号
		orderTrans.setString("Sap_Trans_No", vo.getSapTransNo()); // 销售运单号
		orderTrans.setString("Vin", vo.getVin()); // 车架号
		orderTrans.setInteger("Row_Id", vo.getRowId()); // ROWID
		orderTrans.setInteger("Create_By", OemDictCodeConstants.K4_S0006); // 创建人ID
		orderTrans.setTimestamp("Create_Date", new Date()); // 创建日期
		orderTrans.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
		orderTrans.setInteger("Is_Result", vo.getIsResult()); // 是否成功
		orderTrans.setString("Is_Message", vo.getIsMessage()); // 处理结果信息
		orderTrans.insert();
	}

	/**
	 * 获得运输类型
	 * 
	 * @param code
	 * @return
	 */
	private int getK4TransType(String code) throws Exception {
		int transType = -1;
		if (code != null && code.length() > 0) {
			if ("01".equals(code)) {
				transType = OemDictCodeConstants.K4_TRAFFIC_TYPE_01; // 公路
				return transType;
			} else if ("02".equals(code)) {
				transType = OemDictCodeConstants.K4_TRAFFIC_TYPE_02; // 邮件
				return transType;
			} else if ("03".equals(code)) {
				transType = OemDictCodeConstants.K4_TRAFFIC_TYPE_03; // 铁路
				return transType;
			} else if ("04".equals(code)) {
				transType = OemDictCodeConstants.K4_TRAFFIC_TYPE_04; // 海运
				return transType;
			} else if ("05".equals(code)) {
				transType = OemDictCodeConstants.K4_TRAFFIC_TYPE_05; // 空运
				return transType;
			}
		}
		return transType;
	}

}
