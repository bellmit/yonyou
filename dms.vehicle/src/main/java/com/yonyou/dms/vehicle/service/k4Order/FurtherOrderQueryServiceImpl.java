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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TiFutureorderCancelImportDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmorderCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.FurtherOrderQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年3月7日
 */
@Service
public class FurtherOrderQueryServiceImpl implements FurtherOrderQueryService {

	@Autowired
	private FurtherOrderQueryDao furtherQuDao;

	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 期货订单撤单修改
	 */
	@Override
	public void modifyFurtherOrderCancle(K4OrderDTO k4OrderDTO) throws ServiceBizException {
		String[] vins = k4OrderDTO.getVins().split(",");
		for (int i = 0; i < vins.length; i++) {
			String orderId = "";
			String vin1 = vins[i].toString();
			List<Map> orderList = furtherQuDao.getFurtherOrderByVIN(vins[i]);
			for (Map map : orderList) {
				// 如果订单ID大于0,为期货订单撤单，否则为原期货资源释放
				orderId = map.get("ORDER_ID").toString();
				String vin = map.get("VIN").toString();
				// 将订单检查删除
				TmorderCheckPO.delete("VIN = ? ", vin);
				if (new Long(orderId) > 0) {
					TtVsOrderPO tvoPo = new TtVsOrderPO();
					List<TtVsOrderPO> po = tvoPo.findBySQL("select * from TT_VS_ORDER where ORDER_ID = ? ",
							new Long(orderId));
					String sql = "update TT_VS_ORDER set ORDER_STATUS=" + OemDictCodeConstants.ORDER_STATUS_09
							+ " where ORDER_ID=" + orderId;
					OemDAOUtil.execBatchPreparement(sql, new ArrayList<Object>());
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String format = df.format(new Date());
					for (TtVsOrderPO tPO : po) {
						TmDealerPO tdPO = TmDealerPO.findFirst("DEALER_ID=?", tPO.get("DEALER_ID").toString());
						TmVehiclePO tvPO = TmVehiclePO.findFirst("vin=?", tPO.get("VIN").toString());
						// 更新车辆表状态
						// TmVehiclePO tmvPO = new TmVehiclePO();
						// tmvPO.setString("VIN", tPO.get("VIN"));
						// tmvPO.setInteger("ORG_TYPE",
						// OemDictCodeConstants.ORG_TYPE_OEM);
						// tmvPO.setLong("DEALER_ID", new Long(0));
						// tmvPO.setLong("ORG_ID", new Long(0));
						// //
						// tmvPO.setNodeStatus(Constant.VEHICLE_NODE_18);期货订单取消不改车辆节点
						// tmvPO.setTimestamp("NODE_DATE", format);
						// tmvPO.setTimestamp("UPDATE_DATE", format);
						// tmvPO.setInteger("UPDATE_DATE",
						// loginInfo.getUserId());
						// tmvPO.saveIt();
						TmVehiclePO.update("ORG_TYPE=?,DEALER_ID=?,ORG_ID=?,NODE_DATE=?,UPDATE_DATE=?,UPDATE_BY=?",
								"vin=?", OemDictCodeConstants.ORG_TYPE_OEM, new Long(0), new Long(0), format, format,
								loginInfo.getUserId(), tPO.get("VIN").toString());
						// 如果该车辆节点已超过ZVP8-入VPC仓库，则取消期货公共资源
						String vin2 = tPO.get("VIN").toString();
						List<Map> list = furtherQuDao.findCommonResource(vin2);
						if (list.size() > 0) {
							for (Map map1 : list) {
								TtVsCommonResourcePO tvcPO = new TtVsCommonResourcePO();
								tvcPO.setInteger("COMMON_ID", new Long(map1.get("COMMON_ID").toString()));
								tvcPO.setInteger("VEHICLE_ID", new Long(map1.get("VEHICLE_ID").toString()));
								tvcPO.setInteger("STATUS", OemDictCodeConstants.COMMON_RESOURCE_STATUS_03);
								tvcPO.setTimestamp("UPDATE_DATE", format);
								tvcPO.saveIt();

								TtVsCommonResourceDetailPO vtPO = new TtVsCommonResourceDetailPO();
								vtPO.setInteger("COMMON_ID", tvcPO.get("COMMON_ID"));
								vtPO.setInteger("VEHICLE_ID", new Long(map1.get("VEHICLE_ID").toString()));
								vtPO.setInteger("STATUS", OemDictCodeConstants.STATUS_DISABLE);
								vtPO.saveIt();

								TmVehiclePO tmpPO = new TmVehiclePO();
								tmpPO.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);
								tmpPO.setInteger("DEALER_ID", new Long(0));
								tmpPO.setInteger("ORG_ID", new Long(0));
								// tmpPO.setNodeStatus(Constant.VEHICLE_NODE_08);期货订单取消不改车辆节点
								tmpPO.setTimestamp("NODE_DATE", format);
								tmpPO.setLong("UPDATE_BY", loginInfo.getUserId());
								tmpPO.setTimestamp("UPDATE_DATE", format);
								tmpPO.saveIt();

							}

							// 记录日志
							com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO(); // 匹配更改日志表
							mcPo.setInteger("OLD_VEHICLE_ID", tvPO.get("VEHICLE_ID"));// 原车辆ID
							mcPo.setInteger("CHG_VEHICLE_ID", tvPO.get("VEHICLE_ID"));// 改变车辆ID
							mcPo.setLong("UPDATE_BY", loginInfo.getUserId());
							// 操作人
							mcPo.setTimestamp("UPDATE_DATE", format);// 操作时间
							mcPo.setInteger("CANCEL_TYPE", 1002);// 1001订单取消
																	// 1002 订单撤单
							mcPo.setString("CANCEL_REASON", "未支付全款撤单");
							mcPo.saveIt();

							// 将撤单信息写入接口表
							TiFutureorderCancelImportDcsPO tfcPO = new TiFutureorderCancelImportDcsPO();
							tfcPO.setString("VIN", tPO.get("VIN"));
							tfcPO.setString("DEALER_CODE", tdPO.get("DEALER_CODE"));
							tfcPO.setInteger("IS_SCAN", "0");
							tfcPO.setLong("CREATE_BY", loginInfo.getUserId());
							tfcPO.setTimestamp("CREATE_DATE", format);
							tfcPO.saveIt();

						} else {
							StringBuffer sql2 = new StringBuffer();
							sql2.append("UPDATE TT_VS_COMMON_RESOURCE t \n");
							sql2.append(" SET  t.STATUS = " + OemDictCodeConstants.COMMON_RESOURCE_STATUS_03
									+ " ,t.RESOURCE_SCOPE=null,t.ISSUED_DATE=null,t.UPDATE_BY=" + loginInfo.getUserId()
									+ ",t.UPDATE_DATE=current_date \n");
							sql2.append(" WHERE t.VEHICLE_ID in(select VEHICLE_ID from TM_VEHICLE_DEC where vin='" + vin
									+ "')");
							List params = new ArrayList();
							OemDAOUtil.execBatchPreparement(sql2.toString(), params);

							StringBuffer sql1 = new StringBuffer();
							sql1.append("UPDATE TT_VS_COMMON_RESOURCE_DETAIL t \n");
							sql1.append(" SET  t.STATUS = " + OemDictCodeConstants.STATUS_DISABLE + ",t.UPDATE_BY="
									+ loginInfo.getUserId() + ",t.UPDATE_DATE=current_date \n");
							sql1.append(" WHERE t.VEHICLE_ID in(select VEHICLE_ID from TM_VEHICLE_DEC where vin='" + vin
									+ "')");
							OemDAOUtil.execBatchPreparement(sql1.toString(), params);

							// TmVehiclePO tvPO = new TmVehiclePO();
							// tvPO.setVin(vin);
							// tvPO = (TmVehiclePO) dao.selectUnique(tvPO);
							TmVehiclePO tvP = TmVehiclePO.findFirst("vin=?", vin);
							// TmVehiclePO tmVPO = new TmVehiclePO();
							// tmVPO.setString("VIN", tvP.get("VIN"));
							//
							// tmVPO.setInteger("ORG_TYPE",
							// OemDictCodeConstants.ORG_TYPE_OEM);
							// tmVPO.setLong("DEALER_ID", new Long(0));
							// tmVPO.set("ORG_ID", new Long(0));
							// //
							// tmpPO.setNodeStatus(Constant.VEHICLE_NODE_08);期货订单取消不改车辆节点
							// tmVPO.setTimestamp("NODE_DATE", format);
							// tmVPO.setInteger("UPDATE_BY",
							// loginInfo.getUserId());
							// tmVPO.setTimestamp("UPDATE_DATE", format);
							// tmVPO.saveIt();

							TmVehiclePO.update("ORG_TYPE=?,DEALER_ID=?,ORG_ID=?,NODE_DATE=?,UPDATE_DATE=?,UPDATE_BY=?",
									"vin=?", OemDictCodeConstants.ORG_TYPE_OEM, new Long(0), new Long(0), format,
									format, loginInfo.getUserId(), tPO.get("VIN").toString());

							TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO();

							mcPo.setInteger("OLD_VEHICLE_ID", tvP.get("VEHICLE_ID"));// 原车辆ID
							mcPo.setInteger("CHG_VEHICLE_ID", tvP.get("VEHICLE_ID"));
							mcPo.setLong("UPDATE_BY", loginInfo.getUserId());// 操作人
							mcPo.setInteger("CANCEL_TYPE", 1001);// 订单取消
							mcPo.setTimestamp("UPDATE_DATE", format);// 操作时间
							mcPo.setString("CANCEL_REASON", "期货资源撤单");
							mcPo.saveIt();
						}
					}
				}
			}
		}

	}

	/**
	 * 期货订单撤单下载
	 */
	@Override
	public void furtherOrderDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {

		List<Map> orderList = furtherQuDao.getFurtherOrderDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("期货订单撤单下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RESOURCE", "资源权属"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("VPC_PORT", "VPC港口", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEAL_BOOK_DATE", "订单日期"));
		exportColumnList.add(new ExcelExportColumn("NODE_STATUS", "车辆节点", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_CODE", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("DAY", "未付全款天数"));
		excelService.generateExcel(excelData, exportColumnList, "期货订单撤单下载.xls", request, response);
	}

}
