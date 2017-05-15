package com.infoeai.eai.action.k4;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
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
import com.infoeai.eai.po.TiK4VsOrderUnlockPO;
import com.infoeai.eai.po.TtVsOrderBackPO;
import com.infoeai.eai.vo.S0007VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiK4VsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class S0007Impl extends BaseService implements S0007 {
	private static final Logger logger = LoggerFactory.getLogger(S0007Impl.class);
	@Autowired
	K4SICommonDao dao;

	@Override
	public List<returnVO> execute(List<S0007VO> voList) throws Throwable {
		logger.info("==========S0007 is begin==========");
		List<returnVO> retVoList = new ArrayList<returnVO>();
		String[] returnVo = null;
		/********** 开启事物 **********/
		beginDbService();
		try {

			for (int i = 0; i < voList.size(); i++) {

				S0007VO s0007 = new S0007VO();
				s0007 = voList.get(i);

				// 校验S0007数据逻辑
				returnVo = s0007Check(s0007);

				if (returnVo == null) {

					// 插入接口表
					s0007.setIsResult(OemDictCodeConstants.IF_TYPE_YES.toString());
					this.saveTiTable(s0007);

					// 一下是业务处理方法方法参数是循环出voList的vo
					k4BusinessProcess(s0007);

				} else {

					// 插入接口表
					s0007.setIsResult(OemDictCodeConstants.IF_TYPE_NO.toString());
					s0007.setIsMessage(returnVo[1]);
					this.saveTiTable(s0007);

					// 返回错误信息
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);

					logger.info("==========S0007 返回不合格数据==========RowId==========" + returnVo[0]);
					logger.info("==========S0007 返回不合格数据==========Message==========" + returnVo[1]);

				}
			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new ServiceBizException("S0007业务处理异常！" + e);
		} finally {
			logger.info("==========S0007 is finish==========");
			dbService.clean();
		}

		return retVoList;

		/********** 结束事物 **********/
	}

	/*
	 * S0007数据业务处理逻辑
	 */
	private void k4BusinessProcess(S0007VO s0007Vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("==========S0007 业务处理逻辑开始==========");

		String isCancel = s0007Vo.getIsCancel(); // 是否取消[0:否][1:是]

		String unlockDate = s0007Vo.getUnlockDate() + " " + s0007Vo.getUnlockTime(); // 车辆解绑日期

		Date nodeDate = parseString2DateTime(unlockDate, "yyyyMMdd HHmmss"); // 节点日期

		// 根据销售订单号查询订单ID

		TtVsOrderPO orderPo = TtVsOrderPO.findFirst(" So_No = ? ", s0007Vo.getSoNo());
		if ("0".equals(isCancel)) {

			// 根据SO号变更订单状态为“已确认”，并将其设为“置顶”
			this.doChangeOrder(s0007Vo.getSoNo());

			// 更新订单接口表

			TiK4VsOrderPO setTiOrder = TiK4VsOrderPO.findFirst("VIN=?", s0007Vo.getVin());
			setTiOrder.setString("So_No", s0007Vo.getSoNo());
			setTiOrder.setString("Vin", ""); // 清空车架号
			setTiOrder.setInteger("Is_Top", OemDictCodeConstants.IF_TYPE_YES); // 将其置顶
			setTiOrder.setTimestamp("Top_Date", format); // 置顶时间
			setTiOrder.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_NO); // 设置发送状态为[10041002:待发送]
			setTiOrder.setLong("Update_By", OemDictCodeConstants.K4_S0007);
			setTiOrder.setTimestamp("Update_Date", format);
			setTiOrder.saveIt();

			// 写入订单历史信息
			CommonDAO.insertHistory(Long.parseLong(orderPo.get("Order_Id").toString()),
					OemDictCodeConstants.SALE_ORDER_TYPE_06, "已确认", "", OemDictCodeConstants.K4_S0007,
					s0007Vo.getOperaterName());

		} else {

			// 付款方式
			String paymentType = CommonUtils.checkNull(orderPo.get("Payment_Type"));
			// 这个字段有问题
			String financingStatus = CommonUtils.checkNull(orderPo.get("Financing_Status"));// 财务状态
			String soNo = CommonUtils.checkNull(s0007Vo.getSoNo());
			// 取消订单
			doCancelOrder(soNo, paymentType, nodeDate, financingStatus);

			// 写入订单历史信息
			CommonDAO.insertHistory(Long.parseLong(CommonUtils.checkNull(orderPo.get("Order_Id"))),
					OemDictCodeConstants.SALE_ORDER_TYPE_13, "已取消", "", OemDictCodeConstants.K4_S0007,
					s0007Vo.getOperaterName());

		}

		/*
		 * 车架号不为空，且合法有效： 1、更新车辆节点状态，更新车辆生命周期，车辆用途制空 2、记录车辆节点变更时间 3、记录车辆节点变更历史
		 */
		if (!CheckUtil.checkNull(s0007Vo.getVin())) {

			// 根据车架号查询车辆ID
			List<TmVehiclePO> vehiclePoList = TmVehiclePO.find("VIN=?", s0007Vo.getVin());

			if (null != vehiclePoList && vehiclePoList.size() > 0) {

				// 退车或换车情况下更新车辆信息表中的车辆用途为null

				TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN=?", s0007Vo.getVin());
				setVehiclePo.setString("Vehicle_Usage", "");
				setVehiclePo.setInteger("Node_Status", OemDictCodeConstants.K4_VEHICLE_NODE_12);
				setVehiclePo.setTimestamp("Node_Date", nodeDate);
				setVehiclePo.setInteger("Life_Cycle", OemDictCodeConstants.LIF_CYCLE_02);
				setVehiclePo.setLong("Dealer_Id", new Long(0));
				setVehiclePo.saveIt();
				// 更新车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("Vehicle_Id=?",
						vehiclePoList.get(0).get("Vehicle_Id"));
				setVehicleNodeHistoryPo.setTimestamp("YYRK_DATE", nodeDate); // 退换车入库日期
				setVehicleNodeHistoryPo.setInteger("Is_Del", 0);
				setVehicleNodeHistoryPo.setInteger("Update_By", OemDictCodeConstants.K4_S0007);
				setVehicleNodeHistoryPo.setTimestamp("Update_Date", format);
				setVehicleNodeHistoryPo.saveIt();

				// 车辆节点状态变更表数据插入
				Map<String, Object> params = new HashMap<String, Object>();
				Date unlockD = parseString2DateTime(unlockDate, "yyyyMMdd HHmmss");
				Timestamp changeDate = new Timestamp(unlockD.getTime());
				params.put("vin", s0007Vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_12);
				params.put("changeName", "退换车入库");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "退换车入库");
				params.put("createBy", OemDictCodeConstants.K4_S0007);
				try {
					dao.insertVehicleChange(params);
				} catch (Exception e) {
					logger.info("==========S0007 车辆节点状态变更表数据插入失败==========");
					e.printStackTrace();
				}
			}

		}

		// 记录退车业务表 TT_VS_ORDER_BACK
		TtVsOrderBackPO orderBack = new TtVsOrderBackPO();
		orderBack.setInteger("Is_Cancel", Integer.parseInt(isCancel));
		orderBack.setString("Operater_Code", s0007Vo.getOperaterCode());
		orderBack.setString("Operater_Name", s0007Vo.getOperaterName());
		orderBack.setInteger("So_No", s0007Vo.getSoNo());
		orderBack.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);
		orderBack.setTimestamp("Unlock_Date", parseString2DateTime(unlockDate, "yyyyMMdd HHmmss"));
		orderBack.setString("Vin", s0007Vo.getVin());
		orderBack.setInteger("Create_By", OemDictCodeConstants.K4_S0007);
		orderBack.setTimestamp("Create_Date", new Date());
		orderBack.saveIt();

		logger.info("==========S0007 业务处理逻辑结束==========");

	}

	/**
	 * 根据SO号取消订单
	 * 
	 * @param soNo
	 * @param paymentType
	 * @param financingStatus
	 *            财务状态
	 */
	private void doCancelOrder(String soNo, String paymentType, Date nodeDate, String financingStatus) {

		String orderCancelDate = handleFormatDate(nodeDate);
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("UPDATE TT_VS_ORDER \n");
		sql.append("   SET VIN = '', \n");
		sql.append("       ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_13 + "', \n"); // 已取消
		sql.append("       ALLOT_VEHICLE_DATE = NULL, \n");
		sql.append("       CANCEL_RESON = 'QX003', \n");// 订单取消原因:【SAP取消订单】
		if (!CommonUtils.checkIsNull(orderCancelDate)) {
			sql.append("       ORDER_CANCEL_DATE = '" + orderCancelDate + "', \n");// 订单取消时间
		}
		sql.append("       UPDATE_BY = '" + OemDictCodeConstants.K4_S0007 + "', \n");
		sql.append("       UPDATE_DATE = now() \n");

		if (paymentType.equals(OemDictCodeConstants.K4_PAYMENT_02.toString()) || // 广汽汇理汽车金融有限公司
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_03.toString()) || // 菲亚特汽车金融有限责任公司
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_04.toString()) || // 兴业银行
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_05.toString()) || // 交通银行
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_07.toString())) { // 建行融资
			if (financingStatus.equals(OemDictCodeConstants.FINANCING_STATUS_01.toString())
					|| financingStatus.equals(OemDictCodeConstants.FINANCING_STATUS_05.toString())) {
				sql.append(",      FINANCING_STATUS = '" + OemDictCodeConstants.FINANCING_STATUS_03 + "' \n"); // 融资失败
			} else if (financingStatus.equals(OemDictCodeConstants.FINANCING_STATUS_02.toString())
					|| financingStatus.equals(OemDictCodeConstants.FINANCING_STATUS_03.toString())) {

			} else {
				sql.append(",      FINANCING_STATUS = '" + OemDictCodeConstants.FINANCING_STATUS_04 + "' \n"); // 财务审批中
			}
		} else if (paymentType.equals(OemDictCodeConstants.K4_PAYMENT_01.toString())) {// 现金
			sql.append(",      FINANCING_STATUS = '" + OemDictCodeConstants.FINANCING_STATUS_04 + "' \n"); // 财务审批中
		}

		sql.append(" WHERE SO_NO = '" + soNo + "' \n");

		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());

	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private String handleFormatDate(Date date) {
		if (date == null) {
			return "";
		} else {
			return sdf.format(date);
		}
	}

	/**
	 * 根据SO号变更订单状态为“已确认”，并将其设为“置顶”
	 * 
	 * @param soNo
	 */
	private void doChangeOrder(String soNo) {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("UPDATE TT_VS_ORDER S \n");
		sql.append("   SET S.VIN = '', \n");
		sql.append("       S.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_06 + "', \n"); // 已确认
		sql.append("       S.IS_TOP = '" + OemDictCodeConstants.IF_TYPE_YES + "', \n");
		sql.append("       S.TOP_DATE = now(), \n");
		sql.append("       S.ALLOT_VEHICLE_DATE = NULL, \n");
		sql.append("       S.UPDATE_BY = '" + OemDictCodeConstants.K4_S0007 + "', \n");
		sql.append("       S.UPDATE_DATE = now() \n");
		sql.append(" WHERE S.SO_NO = '" + soNo + "' \n");

		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());

	}

	private Date parseString2DateTime(String dateStr, String dateFormat){

//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//		Date parse = null;
//		try {
//			parse = format.parse(dateStr);
//		} catch (ParseException e) {
//
//			e.printStackTrace();
//		}
//
//		return parse;
		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = "yyyy-MM-dd HH:mm:ss";

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = null;
		try {
			date = formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 数据写入接口表
	 * 
	 * @param vo
	 */
	private void saveTiTable(S0007VO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		TiK4VsOrderUnlockPO orderUnlock = new TiK4VsOrderUnlockPO();

		orderUnlock.setString("VIN", vo.getVin());
		orderUnlock.setString("IS_CANCEL", vo.getIsCancel());
		orderUnlock.setString("SO_NO", vo.getSoNo());
		orderUnlock.setString("UNLOCK_DATE", vo.getUnlockDate());
		orderUnlock.setString("UNLOCK_TIME", vo.getUnlockTime());
		orderUnlock.setString("OPERATER_NAME", vo.getOperaterCode());
		orderUnlock.setString("OPERATER_NAME", vo.getOperaterName());

		orderUnlock.setLong("CREATE_BY", OemDictCodeConstants.K4_S0007);
		orderUnlock.setTimestamp("CREATE_DATE2", format);
		orderUnlock.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);
		orderUnlock.setString("IS_RESULT", vo.getIsResult());
		orderUnlock.setString("IS_MESSAGE", vo.getIsMessage());
		orderUnlock.setString("ROW_ID", vo.getRowId());

		orderUnlock.saveIt();

	}

	/*
	 * S0007数据校验逻辑
	 */
	private String[] s0007Check(S0007VO vo) {

		logger.info("==========S0007 校验逻辑开始==========");

		// 将错误的数据rowID放入returnVo[0],message放入returnVo[1]中
		String[] returnVo = new String[2];
		String remark = null;

		if (null == vo.getRowId() || "".equals(vo.getRowId())) {
			remark = "ROWID为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		if (null == vo.getSoNo() || "".equals(vo.getSoNo())) {
			remark = "销售订单号为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		} else {
			remark = "销售订单号在订单表中不存在";
			List<TtVsOrderPO> orderList = TtVsOrderPO.find("So_No=?", vo.getSoNo());
			if (null == orderList || orderList.size() <= 0) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = remark;
				return returnVo;
			}
		}

		if (null == vo.getUnlockDate() || "".equals(vo.getUnlockDate())) {
			remark = "车辆解绑日期为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		} else if (vo.getUnlockDate().length() != 8) {
			remark = "车辆解绑日期长度与接口定义不一致";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		if (null == vo.getUnlockTime() || "".equals(vo.getUnlockTime())) {
			remark = "车辆解绑时间为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		} else if (vo.getUnlockTime().length() != 6) {
			remark = "车辆解绑时间长度与接口定义不一致";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		if (null == vo.getIsCancel() || "".equals(vo.getIsCancel())) {
			remark = "是否取消销售订单不能为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		} else if (vo.getIsCancel().length() != 1) {
			remark = "是否取消销售订单长度与接口定义不一致";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		// if (null == vo.getVin() || "".equals(vo.getVin())) {
		// remark = "SO创建成功vin码不能为空值";
		// returnVo[0] = vo.getRowId();
		// returnVo[1] = remark;
		// return returnVo;
		// } else if (vo.getVin().length() != 17) {
		// remark = "SO创建成功" + vo.getVin() + "的vin码长度与接口定义不一致";
		// returnVo[0] = vo.getRowId();
		// returnVo[1] = remark;
		// return returnVo;
		// }

		if (null == vo.getOperaterCode() || "".equals(vo.getOperaterCode())) {
			remark = "操作员为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		if (null == vo.getOperaterName() || "".equals(vo.getOperaterName())) {
			remark = "操作账号为空值";
			returnVo[0] = vo.getRowId();
			returnVo[1] = remark;
			return returnVo;
		}

		logger.info("==========S0007 校验逻辑结束==========");

		return null;

	}

	@Override
	public List<S0007VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0007VO> voList = new ArrayList<S0007VO>();

		try {

			logger.info("==========S0007 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0007VO outVo = new S0007VO();
						outVo.setSoNo(map.get("SO_NO")); // 销售订单号
						outVo.setUnlockDate(map.get("UNLOCK_DATE")); // 车辆解绑日期
						outVo.setUnlockTime(map.get("UNLOCK_TIME")); // 车辆解绑时间
						outVo.setIsCancel(map.get("IS_CANCEL")); // 是否取消销售订单
						outVo.setVin(map.get("VIN")); // 车架号
						outVo.setOperaterName(map.get("OPERATER_NAME")); // 操作员
						outVo.setOperaterCode(map.get("OPERATER_CODE")); // 操作账号
						outVo.setRowId(map.get("ROW_ID")); // ROW_ID
						voList.add(outVo);

						logger.info("==========outVo:==========" + outVo);

					}
				}
			}

			logger.info("==========S0007 getXMLToVO() is END==========");

		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0007 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0007 getXMLToVO() is finish==========");
		}
		return voList;

	}

}
