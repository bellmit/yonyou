package com.yonyou.dms.vehicle.service.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.ctcai.SI25;
import com.yonyou.dms.common.domains.PO.basedata.TmCactAllotPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrderPayChangePO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.k4Order.OrderRepealQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TmpCactAllotPO;

/**
 * @author liujiming
 * @date 2017年2月28日
 */
@Service
public class OrderRepealQueryServiceImpl implements OrderRepealQueryService {

	@Autowired
	private ExcelGenerator excelService;

	@Autowired
	private OrderRepealQueryDao orderReQuDao;
	@Autowired
	SI25 si25;

	/**
	 * 整车撤单撤单下载
	 */
	@Override
	public void findOrderRepealQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = orderReQuDao.findOrderRepealQueryDownload(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("整车订单撤单", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售区域"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "整车订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_DATE", "订单提报日期"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "订单状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型描述"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE",
				"付款方式"/* , ExcelDataType.Oem_Dict */));
		exportColumnList.add(new ExcelExportColumn("IS_SUC", "撤单状态"));
		exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		excelService.generateExcel(excelData, exportColumnList, "整车订单撤单.xls", request, response);

	}

	/**
	 * 整车撤单明细查询
	 */
	@Override
	public Map<String, Object> findOrderRepealById(long id) throws ServiceBizException {
		List<Map> orderList = orderReQuDao.findOrderRepealById(id);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (Map map : orderList) {
			resultMap.put("DEALER_CODE", map.get("DEALER_CODE"));
			resultMap.put("DEALER_NAME", map.get("DEALER_NAME"));
			String orderYyp = map.get("ORDER_TYPE").toString();
			if (orderYyp != null) {
				int orderYype = Integer.parseInt(map.get("ORDER_TYPE").toString());
				if (OemDictCodeConstants.ORDER_TYPE_01.equals(orderYype)) {
					orderYyp = "现货";
				}
				if (OemDictCodeConstants.ORDER_TYPE_02.equals(orderYype)) {
					orderYyp = "期货";
				}
				if (OemDictCodeConstants.ORDER_TYPE_03.equals(orderYype)) {
					orderYyp = "指派";
				}
				if (OemDictCodeConstants.ORDER_TYPE_04.equals(orderYype)) {
					orderYyp = "天津爆炸港资源池";
				}
			}
			resultMap.put("ORDER_TYPE", orderYyp);
			String orderStatus = map.get("ORDER_STATUS").toString();
			int parseInt = Integer.parseInt(orderStatus);
			if (OemDictCodeConstants.ORDER_STATUS_01.equals(parseInt)) {
				orderStatus = "未提报(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_02.equals(parseInt)) {
				orderStatus = "待审核(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_03.equals(parseInt)) {
				orderStatus = "审批中(期货)";
			}
			if (OemDictCodeConstants.ORDER_STATUS_04.equals(parseInt)) {
				orderStatus = "定金未确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_05.equals(parseInt)) {
				orderStatus = "定金已确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_06.equals(parseInt)) {
				orderStatus = "订单已确认";
			}
			if (OemDictCodeConstants.ORDER_STATUS_07.equals(parseInt)) {
				orderStatus = "资源已分配";
			}
			if (OemDictCodeConstants.ORDER_STATUS_08.equals(parseInt)) {
				orderStatus = "已取消";
			}
			resultMap.put("ORDER_STATUS", orderStatus);
			resultMap.put("ORDER_NO", map.get("ORDER_NO"));
			resultMap.put("ORDER_DATE", map.get("ORDER_DATE"));
			resultMap.put("DEAL_ORDER_AFFIRM_DATE", map.get("DEAL_ORDER_AFFIRM_DATE"));
			resultMap.put("EXPECT_PORT_DATE", map.get("EXPECT_PORT_DATE"));
			resultMap.put("VIN", map.get("VIN"));
			resultMap.put("SERIES_CODE", map.get("SERIES_CODE"));
			resultMap.put("MODEL_CODE", map.get("MODEL_CODE"));
			resultMap.put("MODEL_NAME", map.get("MODEL_NAME"));
			resultMap.put("COLOR_CODE", map.get("COLOR_CODE"));
			resultMap.put("COLOR_NAME", map.get("COLOR_NAME"));
			resultMap.put("TRIM_CODE", map.get("TRIM_CODE"));
			resultMap.put("TRIM_NAME", map.get("TRIM_NAME"));
			resultMap.put("MODEL_YEAR", map.get("MODEL_YEAR"));
			String nodeStatus = map.get("NODE_STATUS").toString();
			int nodeStatu = Integer.parseInt(nodeStatus);
			if (OemDictCodeConstants.VEHICLE_NODE_01.equals(nodeStatu)) {
				nodeStatus = "ZFSC-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_02.equals(nodeStatu)) {
				nodeStatus = "ZVDU-工厂订单车辆数据更新";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_03.equals(nodeStatu)) {
				nodeStatus = "ZSHP-海运在途";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_04.equals(nodeStatu)) {
				nodeStatus = "ZGOR-车辆到港";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_05.equals(nodeStatu)) {
				nodeStatus = "ZCBC-车辆清关";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_06.equals(nodeStatu)) {
				nodeStatus = "ZVP8-入VPC仓库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_07.equals(nodeStatu)) {
				nodeStatus = "ZPDP-PDI检查通过";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_08.equals(nodeStatu)) {
				nodeStatus = "ZBIL-一次开票";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_09.equals(nodeStatu)) {
				nodeStatus = "ZRL2-订单SAP审核通过";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_10.equals(nodeStatu)) {
				nodeStatus = "ZDRL-中进车款确认";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_11.equals(nodeStatu)) {
				nodeStatus = "ZDRQ-换车入库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_12.equals(nodeStatu)) {
				nodeStatus = "ZPDU-发车出库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_13.equals(nodeStatu)) {
				nodeStatus = "ZPDF-PDI检查失败";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_14.equals(nodeStatu)) {
				nodeStatus = "ZTPR-退车入库";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_15.equals(nodeStatu)) {
				nodeStatus = "ZDLD-经销商验收";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_16.equals(nodeStatu)) {
				nodeStatus = "已实销";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_17.equals(nodeStatu)) {
				nodeStatus = "ZFSN-工厂订单冻结";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_18.equals(nodeStatu)) {
				nodeStatus = "ZRL1-资源已分配";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_19.equals(nodeStatu)) {
				nodeStatus = "ZDRR-经销商订单确认";
			}
			if (OemDictCodeConstants.VEHICLE_NODE_20.equals(nodeStatu)) {
				nodeStatus = "ZDRV-中进车款取消";
			}

			resultMap.put("NODE_STATUS", nodeStatus);
			resultMap.put("INVOICE_NO", map.get("INVOICE_NO"));
			resultMap.put("SOLD_TO", map.get("SOLD_TO"));

		}

		return resultMap;
	}

	@Override
	public void queryPass(K4OrderDTO k4OrderDTO) {

		String dealerCode = CommonUtils.checkNull(k4OrderDTO.getDealerCode());
		String isAllot = CommonUtils.checkNull(k4OrderDTO.getIsEc());
		String vins = CommonUtils.checkNull(k4OrderDTO.getVins());
		String zdrrVins = CommonUtils.checkNull(k4OrderDTO.getZdrrVins());
		String arrOrderIds = "";

		if (!vins.equals("")) {
			vins = "'" + vins.replace(",", "','") + "'";
		}
		if (!zdrrVins.equals("")) {
			zdrrVins = "'" + zdrrVins.replace(",", "','") + "'";
		}
		String lokVins = "";
		if (!vins.equals("") && zdrrVins.equals("")) {
			lokVins = vins;
		} else if (vins.equals("") && !zdrrVins.equals("")) {
			lokVins = zdrrVins;
		} else {
			lokVins = vins + "," + zdrrVins;
		}
		List<Map> reList = new ArrayList<Map>();
		List<Map> lokList = orderReQuDao.findLockVins(lokVins);
		if (lokList.size() > 0) {
			for (int i = 0; i < lokList.size(); i++) {
				Map<String, String> map = lokList.get(i);
				String vin1 = map.get("VIN").toString();
				// map.put("StateCode", "0");
				// map.put("ErrorInfo", "资源已锁定");

				throw new ServiceBizException(vin1 + "资源已锁定");
			}
		}
		String result = "";
		if (zdrrVins.length() > 0) {
			// 调用WSDL文件,只传ZDRR的订单

			try {
				result = si25.doCtcaiMethod(zdrrVins);
			} catch (Exception e) {
			}
		}
		List<Map> tmList = new ArrayList<Map>();
		List<Map> tpList = new ArrayList<Map>();
		List<Map> tcList = new ArrayList<Map>();
		if (!zdrrVins.equals("")) {
			tmList = orderReQuDao.selectZDRR(zdrrVins);
		}
		if (!vins.equals("")) {
			tpList = orderReQuDao.selectZRL1(vins);
			tcList = orderReQuDao.selectForZbil(vins);
		}
		Map<String, String> reMap = new HashMap<String, String>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		// ZDRR撤单,中进返回接收结果后预撤单
		if (result.equals("1")) {
			// 确认中进收到以后再修改撤单审核状态
			if (!arrOrderIds.equals("")) {
				OemDAOUtil.execBatchPreparement("update TM_ORDER_PAY_CHANGE set AUDIT_STATUS="
						+ OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04 + ",IS_SUC=3 where ID in(" + arrOrderIds
						+ ")", null);
			}

			reMap.put("ErrorInfo", "1");

			for (int i = 0; i < tmList.size(); i++) {
				Map<String, Object> map = tmList.get(i);

				TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO();
				mcPo.setInteger("ORDER_ID", new Long(map.get("ORDER_ID").toString()));
				mcPo.setInteger("OLD_VEHICLE_ID", new Long(map.get("VEHICLE_ID").toString()));// 原车辆ID
				mcPo.setInteger("CHG_VEHICLE_ID", new Long(map.get("VEHICLE_ID").toString()));
				mcPo.setInteger("UPDATE_BY", loginInfo.getUserId());// 操作人
				mcPo.setTimestamp("UPDATE_DATE", format);// 操作时间
				mcPo.setInteger("CANCEL_TYPE", 1003);// 1001订单取消 1002 订单撤单//
														// 1003撤单中
				mcPo.setString("CANCEL_REASON", "撤单中");
				mcPo.setInteger("OTYPE", 1);// 撤单类型，1.OTD撤单，2.经销商撤单
				mcPo.saveIt();

				TmCactAllotPO tcPO = new TmCactAllotPO();
				tcPO.setString("VIN", map.get("VIN").toString());
				tcPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
				LazyList<TmCactAllotPO> tcaList = TmCactAllotPO.findBySQL(
						"select * from Tm_Cact_Allot where vin=? and STATUS=?", map.get("VIN").toString(),
						OemDictCodeConstants.STATUS_ENABLE);
				if (tcaList.size() == 0) {
					if (isAllot.equals(OemDictCodeConstants.IF_TYPE_YES)) {
						if (zdrrVins.indexOf(",") < 0) {
							tcPO.setString("DEALER_CODE", dealerCode);
						} else {
							TmpCactAllotPO tmpPO = new TmpCactAllotPO();
							LazyList<TmpCactAllotPO> tmcList = TmpCactAllotPO
									.findBySQL("select * from Tm_Cact_Allot where vin=? ", map.get("VIN").toString());
							if (tmcList.size() > 0) {
								tmpPO = tmcList.get(0);
							}
							tcPO.set("DEALER_CODE", tmpPO.get("DEALER_CODE") != null ? tmpPO.get("DEALER_CODE") : "");
						}
					}
					tcPO.setInteger("MATCH_CHECK_ID", mcPo.getId());
					tcPO.setInteger("CREATE_BY", new Long(loginInfo.getUserId()));
					tcPO.setTimestamp("CREATE_DATE", format);
					tcPO.saveIt();

					// 删除已处理的记录
					TmpCactAllotPO tmpPO = new TmpCactAllotPO();
					TmpCactAllotPO.delete("vin=?", tcPO.get("VIN").toString());
				}
				LazyList<TtResourceRemarkPO> list = TtResourceRemarkPO
						.findBySQL("select * from Tt_Resource_Remark where vin=?", tcPO.get("VIN").toString());
				if (list.size() > 0) {
					for (TtResourceRemarkPO valuePO : list) {
						// valuePO.setRemark(new Integer(0));
						valuePO.setInteger("IS_LOCK", new Integer(1));
						valuePO.setInteger("UPDATE_BY", loginInfo.getUserId());
						valuePO.setTimestamp("UPDATE_DATE", format);
						valuePO.saveIt();
					}
				} else {
					TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
					// trrPO.setRemark(new Integer(0));
					trrPO.setString("VIN", tcPO.get("VIN"));
					trrPO.setInteger("IS_LOCK", new Integer(1));
					trrPO.setInteger("UPDATE_BY", loginInfo.getUserId());
					trrPO.setInteger("CREATE_BY", loginInfo.getUserId());
					trrPO.setTimestamp("CREATE_DATE", format);
					trrPO.setTimestamp("UPDATE_DATE", format);
					trrPO.saveIt();
				}
				// 如果在撤单申请中存在撤单审核中的数据，则修改为撤单中状态
				TmOrderPayChangePO topc2 = TmOrderPayChangePO.findFirst(
						"select * from tm_order_pay_change where vin=? and AUDIT_STATUS=?", tcPO.get("VIN").toString(),
						OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_01);
				topc2.setInteger("AUDIT_STATUS", OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04);
				topc2.setInteger("IS_SUC", 3);
				topc2.saveIt();
			}
		} else {
			reMap.put("ErrorInfo", "0");
		}
		reList.add(reMap);

		// ZRL1撤单 撤回ZBIL---指派订单
		if (tpList.size() > 0) {
			for (int i = 0; i < tpList.size(); i++) {
				String orderId = tpList.get(i).get("ORDER_ID").toString();
				String vin = tpList.get(i).get("VIN").toString();
				String dealerId = tpList.get(i).get("DEALER_ID").toString();
				// 更改订单表为已撤单
				OemDAOUtil
						.execBatchPreparement(
								"update TT_VS_ORDER set COMMONALITY_ID=0,ORDER_STATUS="
										+ OemDictCodeConstants.ORDER_STATUS_09 + " where ORDER_ID=" + orderId + "",
								new ArrayList<Object>());

				// 更新车辆表状态为ZBIL
				OemDAOUtil.execBatchPreparement("update TM_VEHICLE_DEC set NODE_STATUS="
						+ OemDictCodeConstants.VEHICLE_NODE_08 + ",DEALER_ID=0 where VIN='" + vin + "'",
						new ArrayList<Object>());
				// 记录日志
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_MATCH_CHECK(ORDER_ID,CHG_VEHICLE_ID,UPDATE_BY,UPDATE_DATE,CANCEL_TYPE,CANCEL_REASON)\n"
								+ "select \n" + orderId + ",\n" + "tv.VEHICLE_ID,\n" + loginInfo.getUserId() + ",'"
								+ format + "'," + "1001," + "'订单取消'" + " from TM_VEHICLE_DEC tv " + " where tv.vin='"
								+ vin + "'",
						new ArrayList<Object>());

				// 记录详细车籍
				OemDAOUtil
						.execBatchPreparement(
								"insert into TT_VS_VHCL_CHNG(VEHICLE_ID,CHANGE_CODE,CHANGE_DATE,CHANGE_DESC,CREATE_BY,CREATE_DATE,RESOURCE_TYPE,RESOURCE_ID)\n"
										+ "select \n" + "tv.VEHICLE_ID,\n" + "20211019,'" + format + "'," + "'订单取消',"
										+ loginInfo.getUserId() + ",'" + format + "'," + "10191002," + dealerId
										+ " from TM_VEHICLE_DEC tv " + " where tv.vin='" + vin + "'",
								new ArrayList<Object>());
			}
		}
		// ZRL1撤单 撤回ZBIL---期货、现货的订单
		if (tcList.size() > 0) {
			for (int i = 0; i < tcList.size(); i++) {
				Map<String, Object> map = tcList.get(i);
				String vin = map.get("VIN").toString();
				String commonId = map.get("COMMON_ID").toString();
				// 设置公共已取消
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE set UPDATE_BY=" + loginInfo.getUserId()
						+ ",UPDATE_DATE='" + format + "',STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_03
						+ " where COMMON_ID=" + commonId, new ArrayList<Object>());

				// 公共资源明细设为无效
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE_DETAIL set STATUS="
						+ OemDictCodeConstants.STATUS_DISABLE + " where COMMON_ID=" + commonId,
						new ArrayList<Object>());

				// 更新车辆表状态为ZBIL
				OemDAOUtil.execBatchPreparement("update TM_VEHICLE_DEC set NODE_STATUS="
						+ OemDictCodeConstants.VEHICLE_NODE_08 + ",DEALER_ID=0 where  VIN='" + vin + "'",
						new ArrayList<Object>());

				// 记录日志
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_MATCH_CHECK(CHG_VEHICLE_ID,UPDATE_BY,UPDATE_DATE,CANCEL_TYPE,CANCEL_REASON)\n"
								+ "select \n" + "tv.VEHICLE_ID,\n" + loginInfo.getUserId() + ",'" + format + "',"
								+ "1001," + "'订单取消'" + " from TM_VEHICLE_DEC tv " + " where tv.vin='" + vin + "'",
						new ArrayList<Object>());

				// 记录详细车籍
				OemDAOUtil.execBatchPreparement(
						"insert into TT_VS_VHCL_CHNG(VEHICLE_ID,CHANGE_CODE,CHANGE_DATE,CHANGE_DESC,CREATE_BY,CREATE_DATE)\n"
								+ "select \n" + "tv.VEHICLE_ID,\n" + "20211019,'" + format + "'," + "'订单取消',"
								+ loginInfo.getUserId() + ",'" + format + "' from TM_VEHICLE_DEC tv "
								+ " where tv.vin='" + vin + "'",
						new ArrayList<Object>());
			}
		}

	}

}
