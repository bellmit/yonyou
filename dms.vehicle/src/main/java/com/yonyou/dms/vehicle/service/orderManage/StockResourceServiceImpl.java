package com.yonyou.dms.vehicle.service.orderManage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesorderImportPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.StockResourceDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.StockResourceDTO;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtOrderSaleGroupPO;

@Service
public class StockResourceServiceImpl implements StockResourceService {

	@Autowired
	private StockResourceDao dao;

	@Override
	public PageInfoDto stockResourceQuery(Map<String, String> queryParam) {

		PageInfoDto pageInfoDto = dao.stockResourceQuery(queryParam);

		return pageInfoDto;
	}

	@Override
	public Map<String, Object> findStockResource() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long dealerId = loginInfo.getDealerId();
		String sql = "SELECT t1.DEALER_ID, t1.ADDRESS,t1.DEALER_CODE,v.PAYMENT_TYPE FROM  TM_DEALER t1 ,tt_vs_order v	WHERE v.DEALER_ID=t1.DEALER_ID AND t1.DEALER_ID="
				+ dealerId;
		List<Map> list = OemDAOUtil.findAll(sql, null);
		Map<String, Object> map = new HashMap<>();
		for (Map m : list) {
			map.put("PAYMENT_TYPE", m.get("PAYMENT_TYPE"));
			map.put("ADDRESS", m.get("ADDRESS"));
			map.put("DEALER_CODE", m.get("DEALER_CODE"));
			map.put("DETAIL_ID", m.get("DETAIL_ID"));
		}

		return map;
	}

	// 回显预定车辆页面品牌信息
	@Override
	public Map<String, Object> findStockResourceById(Long id) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String, Object> map = dao.findStockResourceById(id);

		if (map.size() > 0) {
			Long GROUP_ID = (Long) map.get("GROUP_ID");
			List<Map> m1 = dao.findMonthPlan(GROUP_ID, loginInfo);
			Map<String, Object> m = new HashMap<String, Object>();
			if (m1.size() > 0) {
				m = m1.get(0);
			}
			Long PARENT_GROUP_ID = (Long) m.get("PARENT_GROUP_ID");
			Map<String, Object> m2 = dao.findDealerOrders(PARENT_GROUP_ID, loginInfo);
			Object DETAIL_ID = m.get("DETAIL_ID");
			Object SALE_AMOUNT = m.get("SALE_AMOUNT");
			Object TOTAL = m2.get("TOTAL");
			map.put("DETAIL_ID", DETAIL_ID);
			map.put("TOTAL", SALE_AMOUNT);
			map.put("HASNUM", TOTAL);
		}

		// TmDealerPO tdPO = new TmDealerPO();
		// 需要更改201308217685114用户ID+ loginInfo.getDealerId();
		Long dealerId = loginInfo.getDealerId();
		String sql = "SELECT t1.DEALER_ID, t1.ADDRESS,t1.DEALER_CODE,v.PAYMENT_TYPE FROM  TM_DEALER t1 ,tt_vs_order v	WHERE v.DEALER_ID=t1.DEALER_ID AND t1.DEALER_ID="
				+ dealerId;
		List<Map> list = OemDAOUtil.findAll(sql, null);

		for (Map m : list) {
			map.put("PAYMENT_TYPE", m.get("PAYMENT_TYPE"));
			map.put("ADDRESS", m.get("ADDRESS"));
			map.put("DEALER_CODE", m.get("DEALER_CODE"));
			// map.put("TOTAL", m.get("SALE_AMOUNT"));
			// map.put("HASNUM", m.get("TOTAL"));
			// map.put("DETAIL_ID", m.get("DETAIL_ID"));
		}

		return map;
	}

	@Override
	public void doSubmit(StockResourceDTO stockDTO) {
		String vin = CommonUtils.checkNull(stockDTO.getVin());
		String commonId = CommonUtils.checkNull(stockDTO.getCommon_id());
		// String detailId = CommonUtils.checkNull(stockDTO.get);
		String address = CommonUtils.checkNull(stockDTO.getAddress());
		String paymentType = CommonUtils.checkNull(stockDTO.getOrderTypePay());
		String remark = CommonUtils.checkNull(stockDTO.getRemark());
		String isTicket = CommonUtils.checkNull(stockDTO.getIsTicket());
		String ticketNo = CommonUtils.checkNull(stockDTO.getTicketNo());
		String saleGroupId = CommonUtils.checkNull(stockDTO.getGroup_id());
		if (vin != "") {

			List<Map> vinExists = dao.findIsSubmited(vin);
			List<Map> tvcrList = dao.findIsCancelOrder(vin);

			if (vinExists.size() > 0) {
				// 判断车辆是否已被预定
				throw new ServiceBizException("此车辆已被预定");
			} else if (tvcrList.size() > 0) {
				// 判断车辆是否正在撤单
				throw new ServiceBizException("此车辆已经撤单");
			} else {
				this.doFuture(vin, commonId, address, paymentType, isTicket, ticketNo, remark, saleGroupId);

			}

		}

	}

	private void doFuture(String vin, String commonId, String address, String paymentType, String isTicket,
			String ticketNo, String remark, String saleGroupId) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		Map map = dao.findOrderTepy(commonId);
		// String orderType = map.get("ORDER_TYPE").toString();
		// PayCommandConfirmDao payCommandConfirmDao= new
		// PayCommandConfirmDao();
		TmVehiclePO tmVehicle = TmVehiclePO.findFirst("vin=?", vin);
		TtVsOrderPO po = new TtVsOrderPO();
		// po.setOrderId(new Long(SequenceManager.getSequence("")));
		po.setLong("COMMONALITY_ID", commonId);
		po.setString("vin", vin);
		String object = tmVehicle.get("MATERIAL_ID").toString();
		Long h = Long.parseLong(object);
		po.setLong("MATERAIL_ID", h);
		// po.setLong("MATERIAL_ID", tmVehicle.get("MATERIAL_ID").toString());
		// if (orderType != null) {
		// po.setInteger("ORDER_TYPE", Integer.valueOf(orderType));
		//
		// }
		po.setLong("DEALER_ID", loginInfo.getDealerId());
		po.setString("ORDER_NO", OemBaseDAO.getOrderNO(OemDictCodeConstants.SALES_ORDER_NOW_NO));
		po.setTimestamp("ORDER_DATE", format);
		po.setTimestamp("DEAL_ORDER_AFFIRM_DATE", format);
		po.set("ADDRESS", address);
		po.setInteger("PAYMENT_TYPE", Integer.valueOf(paymentType));
		po.setInteger("IS_TICKET", Integer.valueOf(isTicket));
		po.setString("TICKET_NO", ticketNo);
		po.setString("REMARK", remark);
		/*
		 * if(!vehicleUsageType.equals("")){ po.setVehicleUse(new
		 * Integer(vehicleUsageType)); }
		 */
		po.setLong("CREATE_BY", loginInfo.getUserId());
		po.setTimestamp("CREATE_DATE", format);
		po.setInteger("VER", new Integer(0));
		po.setInteger("Is_Del", new Integer(0));
		po.setInteger("Is_Arc", new Integer(0));
		po.setInteger("ORDER_STATUS", OemDictCodeConstants.ORDER_STATUS_06);// 现货是已完成
		try {
			po.saveIt();
		} catch (Exception e) {
			throw new ServiceBizException("此车辆已被预定");

		}

		/* add by ZRM 修改车辆表信息，提报订单时将车辆表的org_id置空，dealer_id置为当前经销商 */

		TmVehiclePO tm = TmVehiclePO.findFirst("vin=?", vin);
		tm.setLong("DEALER_ID", new Long(loginInfo.getDealerId()));
		tm.setLong("ORG_ID", new Long(0));
		tm.setLong("UPDATE_BY", loginInfo.getUserId());
		tm.setTimestamp("UPDATE_DATE", format);
		tm.setInteger("NODE_STATUS", OemDictCodeConstants.VEHICLE_NODE_19);
		tm.setTimestamp("NODE_DATE", format);
		tm.saveIt();

		// 插入详细车籍
		TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
		// Long vhclId = Long.parseLong(SequenceManager.getSequence(null));
		// vhclChng.setVhclChangeId(vhclId);
		vhclChng.setLong("VEHICLE_ID", tmVehicle.getLong("VEHICLE_ID"));
		vhclChng.setInteger("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_25);
		vhclChng.setString("CHANGE_DESC", "经销商订单提报");
		vhclChng.setTimestamp("CHANGE_DATE", format);
		vhclChng.setLong("CREATE_BY", loginInfo.getUserId());
		vhclChng.setTimestamp("CREATE_DATE", format);
		vhclChng.setInteger("RESOURCE_TYPE", 10191002);
		vhclChng.setLong("RESOURCE_ID", new Long(loginInfo.getDealerId()));
		vhclChng.saveIt();

		// 现货类型写入接口表
		TiSalesorderImportPO salesImport = new TiSalesorderImportPO();
		TmDealerPO tmDealer = TmDealerPO.findById(loginInfo.getDealerId());
		if (tmDealer != null) {
			salesImport.setString("VEHICLE_SEARCH_AREA", tmDealer.get("DEALER_CODE"));
			salesImport.setString("Receiving_Dealer", tmDealer.get("DEALER_CODE"));
			salesImport.setString("Releasing_Dealer", tmDealer.get("DEALER_CODE"));
		}
		// salesImport.setString("PAYMENT_TYPE",paymentType);
		salesImport.setString("PAYMENT_TYPE", OemBaseDAO.selectFy(paymentType).get("RELATION_EN") + "");// 付款方式
		salesImport.setString("Red_Invoice_Number", ticketNo);
		// salesImport.setSequenceId(new Long(SequenceManager.getSequence("")));
		salesImport.setString("VIN", vin);
		salesImport.setInteger("IS_SCAN", "0");
		salesImport.setInteger("WHOLESALE_PRICE", tmVehicle.get("WHOLESALE_PRICE"));// 将车辆表的价格传给中进
		salesImport.setLong("CREATE_BY", loginInfo.getUserId());
		tm.setTimestamp("CREATE_DATE", format);

		if (tmVehicle.get("VEHICLE_USAGE") == null || "".equals(tmVehicle.get("VEHICLE_USAGE"))) {
			salesImport.setInteger("VEHICLE_USAGE_TYPE", "68");// 取车辆表的车辆用途
		} else {
			TcRelationPO trpPO = null;
			LazyList<TcRelationPO> list2 = TcRelationPO.findBySQL("select * from tc_relation CODE_ID=?",
					tmVehicle.getInteger("VEHICLE_USAGE"));
			if (list2.size() > 0) {
				trpPO = list2.get(0);
			}

			if (null != trpPO) {
				salesImport.setInteger("VEHICL_EUSAGE_TYPE", trpPO.get("RELATION_CODE") + "");// 取车辆表的车辆用途
			} else {
				salesImport.setInteger("VEHICLE_USAGE_TYPE", "68");// 取车辆表的车辆用途
			}
		}
		salesImport.saveIt();
		// 调用接口
		// TODO 此处接口未实现
		// SI22 action = new SI22();
		// action.execute();

		// 车辆预定组合已订数量+1
		Map<String, Object> map1 = dao.checkSaleGroupSetting(vin, loginInfo);
		String sales_group_id = map1.get("SALES_GROUP_ID").toString();
		// List<TtOrderSaleGroupPO> list = new ArrayList<TtOrderSaleGroupPO>();
		LazyList<TtOrderSaleGroupPO> list = TtOrderSaleGroupPO
				.findBySQL("select * from TT_ORDER_SALE_GROUP where SALES_GROUP_ID=?", sales_group_id);
		/*
		 * if ("".equals(CommonUtils.checkNull(saleGroupId))) { list =
		 * TtOrderSaleGroupPO.findBySQL(
		 * "select * from TT_ORDER_SALE_GROUP where gtype = ? and status = " +
		 * OemDictCodeConstants.STATUS_ENABLE, 0); }
		 */

		if (list.size() > 0) {
			for (TtOrderSaleGroupPO tgPO : list) {

				// Map<String, Object> map1 = dao.checkSaleGroupSetting(vin,
				// loginInfo);

				// TtOrderSaleGroupPO tpPO = new TtOrderSaleGroupPO();
				// tpPO.setLong("SALES_GROUP_ID",
				// tgPO.get("SALES_GROUP_ID").toString());
				// tpPO.saveIt();
				String hasnum = CommonUtils.checkNull(String.valueOf(tgPO.getInteger("HASNUM")));
				if (hasnum.equals("")) {
					hasnum = "0";
				}
				String sales_groupid = tgPO.get("SALES_GROUP_ID").toString();
				// if()
				TtOrderSaleGroupPO poo = TtOrderSaleGroupPO.findFirst("SALES_GROUP_ID=?", sales_groupid);
				poo.setInteger("HASNUM", Integer.parseInt(hasnum) + 1);
				poo.saveIt();
			}
		}
		// 如果经销商控制为没有批售目标则不能订车则用以下逻辑
		// 批售数量+1
		/*
		 * TtVsMonthlyPlanDetailPO tmPO = new TtVsMonthlyPlanDetailPO();
		 * tmPO.setDetailId(new Long(!detailId.equals("")?detailId:"0"));
		 * List<TtVsMonthlyPlanDetailPO> mList = dao.select(tmPO);
		 * if(mList.size()>0){ TtVsMonthlyPlanDetailPO tmdPO = mList.get(0);
		 * TtVsMonthlyPlanDetailPO tmpPO = new TtVsMonthlyPlanDetailPO();
		 * tmpPO.setDetailId(tmdPO.getDetailId()); TtVsMonthlyPlanDetailPO
		 * valuePO = new TtVsMonthlyPlanDetailPO();
		 * valuePO.setHasAmount(tmdPO.getHasAmount()+1); dao.update(tmpPO,
		 * valuePO); }
		 */
		// 解决资源被多个经销商同时点订单的问题

		String sql = "insert into TM_ORDER_CHECK(VIN) values('" + vin + "')";
		OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
		// TmorderCheckPO p = new TmorderCheckPO();
		// p.setString("VIN", vin);
		// p.saveIt();

	}

	/**
	 * 检查是否已被其他经销商预定
	 */
	@Override
	public void checkstockResourceSubmited(Map<String, String> param) {
		String vin = CommonUtils.checkNull(param.get("vin"));
		if (vin != "") {
			List<Map> list = dao.findAlreadSubmited(vin);
			if (list.size() > 0) {

				throw new ServiceBizException("此车辆已被预定");
			} else {
				this.checkstockResourceSubmited(vin);

			}
		}
	}

	/**
	 * 销售组合(现货)
	 * 
	 * @param vin
	 * @return
	 */
	public Map checkstockResourceSubmited(String vin) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date date = new Date();
		Map<String, Object> map = new HashMap<String, Object>();
		LazyList<TtOrderSaleGroupPO> list = TtOrderSaleGroupPO.findBySQL(
				"select * from TT_ORDER_SALE_GROUP where GTYPE=0 and STATUS=?", OemDictCodeConstants.STATUS_ENABLE);
		a: for (int i = 0; i < list.size(); i++) {
			TtOrderSaleGroupPO tPO = list.get(i);
			StringBuffer sql = new StringBuffer();
			sql.append("select v.* from TM_VEHICLE_DEC t,(" + OemBaseDAO.getVwMaterialSql() + ")  v \n");
			sql.append("   where t.MATERIAL_ID=v.MATERIAL_ID\n");
			sql.append("     and t.vin='" + vin + "'\n");
			sql.append("	 and v.MATERIAL_ID in(select MATERIAL_ID from (" + OemBaseDAO.getVwMaterialSql()
					+ ")  where 1=1 ");

			sql.append(tPO.get("BRAND_CODE") != null ? " and BRAND_CODE='" + tPO.get("Brand_Code") + "'" : "");
			sql.append(tPO.get("SERIES_NAME") != null ? " and SERIES_NAME='" + tPO.get("SERIES_NAME") + "'" : "");
			sql.append(tPO.get("GROUP_NAME") != null ? " and GROUP_NAME='" + tPO.get("GROUP_NAME") + "'" : "");
			sql.append(tPO.get("MODEL_YEAR") != null ? " and MODEL_YEAR='" + tPO.get("MODEL_YEAR") + "'" : "");
			sql.append(tPO.get("COLOR_NAME") != null ? " and COLOR_NAME='" + tPO.get("COLOR_NAME") + "'" : "");
			sql.append(tPO.get("TRIM_NAME") != null ? " and TRIM_NAME='" + tPO.get("TRIM_NAME") + "'" : "");
			sql.append(")");
			List<Map> l = OemDAOUtil.findAll(sql.toString(), null);
			// 如果车辆在组合车辆中
			if (l.size() > 0) {
				// TtOrderSaleGroupPO togPO = new TtOrderSaleGroupPO();
				// tgPO.setInteger("GTYPE", new Integer(1));
				// tgPO.setInteger("STATUS",
				// OemDictCodeConstants.STATUS_ENABLE);
				// togPO.setLong("PARENT_GROUP_ID", tPO.get("SALES_GROUP_ID"));
				LazyList<TtOrderSaleGroupPO> ll = TtOrderSaleGroupPO.findBySQL(
						"select * from TT_ORDER_SALE_GROUP where GTYPE=1 and STATUS=? and PARENT_GROUP_ID=?",
						OemDictCodeConstants.STATUS_ENABLE, tPO.get("SALES_GROUP_ID"));
				// List<TtOrderSaleGroupPO> ll = togPO.findAll();
				if (ll.size() > 0) {
					for (int j = 0; j < ll.size(); j++) {
						TtOrderSaleGroupPO ttPO = ll.get(j);
						StringBuffer sql2 = new StringBuffer();
						sql2.append("select count(*) total from TT_VS_ORDER where  1=1 AND IS_DEL <> '1' \n");
						sql2.append("	    and DEALER_ID=" + loginInfo.getDealerId() + "\n");
						sql2.append("		and ORDER_TYPE=" + OemDictCodeConstants.ORDER_TYPE_01 + "\n");
						sql2.append("       and DEAL_ORDER_AFFIRM_DATE between '" + DateUtil.getFirstDayOfMonth(date)
								+ "' and '" + DateUtil.getLastDayOfMonth(date) + " 23:59:59.999' \n");
						sql2.append("		and ORDER_STATUS <" + OemDictCodeConstants.ORDER_STATUS_08 + "\n");
						sql2.append("	    and MATERAIL_ID  in(select MATERIAL_ID from ("
								+ OemBaseDAO.getVwMaterialSql() + ") where 1=1 ");
						sql2.append(ttPO.get("BRAND_CODE") != null ? " and BRAND_CODE='" + ttPO.get("BRAND_CODE") + "'"
								: "");
						sql2.append(ttPO.get("SERIES_NAME") != null
								? " and SERIES_NAME='" + ttPO.get("SERIES_NAME") + "'" : "");
						sql2.append(ttPO.get("GROUP_NAME") != null ? " and GROUP_NAME='" + ttPO.get("GROUP_NAME") + "'"
								: "");
						sql2.append(ttPO.get("MODEL_YEAR") != null ? " and MODEL_YEAR='" + ttPO.get("MODEL_YEAR") + "'"
								: "");
						sql2.append(ttPO.get("COLOR_NAME") != null ? " and COLOR_NAME='" + ttPO.get("COLOR_NAME") + "'"
								: "");
						sql2.append(
								ttPO.get("TRIM_NAME") != null ? " and TRIM_NAME='" + ttPO.get("TRIM_NAME") + "'" : "");
						sql2.append(")\n");

						List<Map> li = OemDAOUtil.findAll(sql2.toString(), null);
						for (Map map2 : li) {

							int num = ttPO.getInteger("NUM") - (new Integer(map2.get("TOTAL").toString())
									- ttPO.getInteger("NUM") * tPO.getInteger("Hasnum"));
							// 如果当月修改了绑定车型的数量,需要当月立即生效的加上下一句,如果绑定车型数量不允许修改则不需要
							if (num > ttPO.getInteger("NUM")) {
								num = num % ttPO.getInteger("NUM");
							}
							if (num > 0) {
								map.put(ttPO.get("SALES_GROUP_ID").toString(), num);
							} else {
								map.put("SALES_GROUP_ID".toString(), tPO.get("SALES_GROUP_ID").toString());
							}
						}
					}
				}
				break a;
			}
		}
		return map;

	}

	@Override
	public void doStockSubmit(StockResourceDTO stockDTO) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String vins = CommonUtils.checkNull(stockDTO.getVins());
		String commonId = CommonUtils.checkNull(stockDTO.getCommon_id());
		// String detailId = CommonUtils.checkNull(stockDTO.get);
		String address = CommonUtils.checkNull(stockDTO.getAddress());
		String paymentType = CommonUtils.checkNull(stockDTO.getOrderTypePay());
		String remark = CommonUtils.checkNull(stockDTO.getRemark());
		String isTicket = CommonUtils.checkNull(stockDTO.getIsTicket());
		String ticketNo = CommonUtils.checkNull(stockDTO.getTicketNo());
		String saleGroupId = CommonUtils.checkNull(stockDTO.getGroup_id());
		String[] vinList = vins.split(",");
		if (vins.indexOf(",") > 0) {
			for (String vin : vinList) {
				List<Map> list = dao.findAlreadSubmited(vin);
				if (list.size() == 0) {
					StringBuffer result = new StringBuffer();
					result.append("vin号为" + vin + "的车辆为组合销售车辆,需要先订以下车辆才能订此车:\n");
					Map<String, Object> map = dao.checkSaleGroupSetting(vin, loginInfo);
					String num = CommonUtils.checkNull(map.get("NUM"));
					String id = map.get("SALES_GROUP_ID").toString();
					List<Map> tl = dao.findBandResource(id, num);
					if (tl.size() > 0) {
						Map<String, Object> tMap = tl.get(0);
						String brandCode = tMap.get("BRAND_CODE") != null ? tMap.get("BRAND_CODE").toString() : "";
						String seriesName = tMap.get("SERIES_NAME") != null ? tMap.get("SERIES_NAME").toString() : "";
						String groupName = tMap.get("GROUP_NAME") != null ? tMap.get("GROUP_NAME").toString() : "";
						String modelYear = tMap.get("MODEL_YEAR") != null ? tMap.get("MODEL_YEAR").toString() : "";
						String colorName = tMap.get("COLOR_NAME") != null ? tMap.get("COLOR_NAME").toString() : "";
						String trimName = tMap.get("TRIM_NAME") != null ? tMap.get("TRIM_NAME").toString() : "";
						if (!brandCode.equals("")) {
							result.append(" 品牌:" + brandCode);
						}
						if (!seriesName.equals("")) {
							result.append(" 车系:" + seriesName);
						}
						if (!groupName.equals("")) {
							result.append(" 车款:" + groupName);
						}
						if (!modelYear.equals("")) {
							result.append(" 年款:" + modelYear);
						}
						if (!colorName.equals("")) {
							result.append(" 颜色:" + colorName);
						}
						if (!trimName.equals("")) {
							result.append(" 内饰:" + trimName);
						}
						result.append(" 数量:" + map.get("NUM"));
						result.append("  \n");
					}

				}
				if (vin != "") {

					List<Map> vinExists = dao.findIsSubmited(vin);
					List<Map> tvcrList = dao.findIsCancelOrder(vin);

					if (vinExists.size() > 0) {
						// 判断车辆是否已被预定
						throw new ServiceBizException("此车辆已被预定");
					} else if (tvcrList.size() > 0) {
						// 判断车辆是否正在撤单
						throw new ServiceBizException("此车辆已经撤单");
					} else {
						this.doFuture(vin, commonId, address, paymentType, isTicket, ticketNo, remark, saleGroupId);

					}

				}
			}
		} else {
			for (String vin : vinList) {
				this.checkstockResourceSubmited(vin);
			}
		}
	}

}
