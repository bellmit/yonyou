package com.infoeai.eai.action.k4;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4VaArrivedPO;
import com.infoeai.eai.vo.S0015VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0015Impl extends BaseService implements S0015 {
	private static final Logger logger = LoggerFactory.getLogger(S0015Impl.class);
	@Autowired
	K4SICommonDao dao;

	@Override
	public List<S0015VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0015VO> volist = new ArrayList<S0015VO>();

		try {

			logger.info("==========S00015 getXMLToVO() is START==========");
			logger.info("==========XML赋值到VO==========");
			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0015VO vo = new S0015VO();
						vo.setINSPECTIONDATE(map.get("INSPECTION_DATE")); // 经销商签收日期
																			// 格式：YYYYMMDD
						vo.setINSPECTIONBY(map.get("INSPECTION_BY")); // 经销商签收人
						vo.setVIN(map.get("VIN")); // 车架号
						vo.setDEALERCODE(map.get("DEALER_CODE")); // 经销商代码
						vo.setTRANSNO(map.get("TRANS_NO")); // 运单号
						vo.setSONO(map.get("SO_NO")); // 销售订单号
						vo.setROWID(map.get("ROW_ID")); // ROW_ID
						volist.add(vo);

						logger.info("==========outVo:==========" + vo);
					}
				}
			}

			logger.info("==========S00015 getXMLToVO() is END==========");

		} catch (Exception e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0015 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S00015 getXMLToVO() is finish==========");
		}
		return volist;

	}

	@Override
	public List<returnVO> execute(List<S0015VO> volist) throws Throwable {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("S0015 is begin");

		List<returnVO> revolist = new ArrayList<returnVO>();

		String[] returnvo = null;

		beginDbService();

		try {

			for (int i = 0; i < volist.size(); i++) {

				returnvo = S0015Chechk(volist.get(i));

				if (null == returnvo) {

					K4CheckThroughData(volist.get(i)); // 写入接口表数据

					inserttable(volist.get(i)); // 写入业务表数据

				} else {

					logger.info("==========S0015未通过校验数据处理开始==========");

					TiK4VaArrivedPO po = new TiK4VaArrivedPO();
					po.setString("Inspection_Date", volist.get(i).getINSPECTIONDATE()); // 经销商签收日期
					// 格式：YYYYMMDD
					po.setString("Inspection_By", volist.get(i).getINSPECTIONBY()); // 经销商签收人
					po.setString("Vin", volist.get(i).getVIN()); // 车架号
					po.setString("Dealer_Code", volist.get(i).getDEALERCODE()); // 经销商代码
					po.setString("Trans_No", volist.get(i).getTRANSNO()); // 运单号
					po.setString("So_No", volist.get(i).getSONO()); // 销售订单号
					po.setString("Row_Id", volist.get(i).getROWID()); // ROW_ID
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0015); // 创建人
					po.setTimestamp("Create_Date2", format); // 创建日期
					po.setInteger("Is_Del", 0);
					po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_NO);
					po.setString("Is_Message", returnvo[1]);
					// po.setIfId("",Long.valueOf(SequenceManager.getSequence("")));
					po.saveIt();

					logger.info("==========S0015未通过校验数据处理开始==========");

					// 返回错误信息
					returnVO revo = new returnVO();
					revo.setOutput(returnvo[0]);
					revo.setMessage(returnvo[1]);
					revolist.add(revo);

					logger.info("==========S00015返回不合格数据==========ROWId为==========" + returnvo[0]);

					logger.info("==========S00015返回不合格数据==========Message为==========" + returnvo[1]);

				}

			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0015业务处理异常！" + e);
		}
		logger.info("==========S00015 is finish==========");
		dbService.clean();
		return revolist;
	}

	private void inserttable(S0015VO vo) {

		try {

			logger.info("==========S0015业务数据处理开始==========");

			Date inspectionDate = DateUtil.yyyyMMdd2Date(vo.getINSPECTIONDATE());

			Timestamp changeDate = new Timestamp(inspectionDate.getTime());

			Long vehicleId = 0l;

			// 更新车辆节点状态

			LazyList<TmVehiclePO> vehiclePoList = TmVehiclePO.find("vin=?", vo.getVIN());

			if (null != vehiclePoList && vehiclePoList.size() > 0) {

				vehicleId = (Long) vehiclePoList.get(0).get("Vehicle_Id"); // 车辆ID

				// 车辆节点状态为已发运的场合才更新车辆节点状态
				if (OemDictCodeConstants.K4_VEHICLE_NODE_17.equals(vehiclePoList.get(0).get("Node_Status"))) {

					for (TmVehiclePO setVehiclePo : vehiclePoList) {
						setVehiclePo.setString("VIN", vo.getVIN());
						setVehiclePo.setInteger("Node_Status", OemDictCodeConstants.K4_VEHICLE_NODE_18);
						setVehiclePo.setTimestamp("Node_Date", inspectionDate);
						setVehiclePo.saveIt();
					}

				}
			}

			LazyList<TtVsOrderPO> list = TtVsOrderPO.find("vin=?", vo.getVIN());

			if (list != null && list.size() > 0) {

				TtVsOrderPO setOrderPo = TtVsOrderPO.findFirst("vin=?", vo.getVIN());

				// 订单状态为已发运的场合才更新订单状态
				if (OemDictCodeConstants.SALE_ORDER_TYPE_10.equals(list.get(0).get("Order_Status"))) {
					setOrderPo.setInteger("Order_Status", OemDictCodeConstants.SALE_ORDER_TYPE_11);
				}

				setOrderPo.setTimestamp("Inspection_Date", inspectionDate);
				setOrderPo.setString("Inspection_By", vo.getINSPECTIONBY());
				setOrderPo.saveIt();
				CommonDAO.insertHistory((Long) list.get(0).get("Order_Id"), OemDictCodeConstants.SALE_ORDER_TYPE_11,
						"已到店", "SAP", OemDictCodeConstants.K4_S0015, "");
			}
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());
			// 更新车辆节点日期记录表（存储日期）
//			TtVehicleNodeHistoryPO conVehicleNodeHistoryPo = new TtVehicleNodeHistoryPO();
//			conVehicleNodeHistoryPo.setLong("Vehicle_Id", vehicleId); // 车辆ID
			TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("Vehicle_Id=?", vehicleId);
//			TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = new TtVehicleNodeHistoryPO();
			setVehicleNodeHistoryPo.setTimestamp("Zpd2_Date", changeDate); // 物流到店日期
			setVehicleNodeHistoryPo.setInteger("Is_Del", 0);
			setVehicleNodeHistoryPo.setInteger("Update_By", OemDictCodeConstants.K4_S0015);
			setVehicleNodeHistoryPo.setTimestamp("Update_Date", format);
			setVehicleNodeHistoryPo.saveIt();

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("vin", vo.getVIN());
			params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_18);
			params.put("changeName", "物流到店");
			params.put("changeDate", changeDate);
			params.put("changeDesc", "物流承运商到店反馈");
			params.put("createBy", OemDictCodeConstants.K4_S0015);
			dao.insertVehicleChange(params);

			logger.info("==========S0015业务数据处理结束==========");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void K4CheckThroughData(S0015VO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try {

			logger.info("==========S0015通过校验数据处理开始==========");

			TiK4VaArrivedPO po = new TiK4VaArrivedPO();
			po.setString("INSPECTION_DATE", vo.getINSPECTIONDATE()); // 经销商签收日期
			// 格式：YYYYMMDD
			po.setString("INSPECTION_BY", vo.getINSPECTIONBY()); // 经销商签收人
			po.setString("VIN", vo.getVIN()); // 车架号
			po.setString("DEALER_CODE", vo.getDEALERCODE()); // 经销商代码
			po.setString("TRANS_NO", vo.getTRANSNO()); // 运单号
			po.setString("SO_NO", vo.getSONO()); // 销售订单号
			po.setString("ROW_ID", vo.getROWID()); // ROWID
			po.setInteger("CREATE_BY", OemDictCodeConstants.K4_S0015); // 创建人
			po.setTimestamp("CREATE_DATE2", format); // 创建日期
			po.setInteger("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES);
			po.setInteger("IS_DEL", 0);
			// po.setIfId(Long.valueOf(SequenceManager.getSequence("")));
			po.saveIt();

			logger.info("==========S0015通过校验数据处理开始==========");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String[] S0015Chechk(S0015VO vo) throws Exception {

		String[] returnvo = new String[2];

		// 经销商签收日期
		if (CheckUtil.checkNull(vo.getINSPECTIONDATE())) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "经销商签收日期为空";
			return returnvo;
		}

		if (vo.getINSPECTIONDATE().length() != 8) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "经销商签收日期长度于接口定义不一致" + vo.getINSPECTIONDATE();
			return returnvo;
		}

		// VIN号非空校验
		if (CheckUtil.checkNull(vo.getVIN())) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "VIN号为空";
			return returnvo;
		}

		// 校验VIN号长度
		if (vo.getVIN().length() != 17) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "VIN号长度与接口定义不一致：" + vo.getVIN();
			return returnvo;
		}

		// 校验VIN号是否已存在
		if (doVinCheck(vo.getVIN()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "VIN号不存在：" + vo.getVIN();
			return returnvo;
		}

		if (CheckUtil.checkNull(vo.getDEALERCODE())) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "经销商代码为空";
			return returnvo;

		}

		// 经销商代码
		if (dodealerCheck(vo.getDEALERCODE()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "经销商代码不存在";
			return returnvo;
		}

		// 运单号
		if (CheckUtil.checkNull(vo.getTRANSNO())) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "运单号为空";
			return returnvo;
		}

		// 销售订单号
		if (doSONOCheck(vo.getSONO()) == OemDictCodeConstants.IF_TYPE_NO) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "销售订单号不存在";
			return returnvo;
		}

		// ROW_ID
		if (CheckUtil.checkNull(vo.getROWID())) {
			returnvo[0] = vo.getROWID();
			returnvo[1] = "ROWID不得为空";
			return returnvo;
		}
		return null;
	}

	private int dodealerCheck(String dealer) throws Exception {

		Map<String, Object> map = dao.getDealerByFCACode(dealer);

		/*
		 * 校验经销商是否已存在
		 */
		if (map != null && map.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}

	}

	private int doSONOCheck(String SONO) throws Exception {

		LazyList<TtVsOrderPO> spolist = TtVsOrderPO.find("SO_NO=?", SONO);

		/*
		 * SAP订单号是否已存在
		 */
		if (spolist != null && spolist.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}

	}

	/**
	 * 车架号校验
	 * 
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	private Integer doVinCheck(String vin) {

		LazyList<TmVehiclePO> list = TmVehiclePO.find("vin=?", vin);

		/*
		 * 校验车架号是否已存在
		 */
		if (list.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}

	}

}
