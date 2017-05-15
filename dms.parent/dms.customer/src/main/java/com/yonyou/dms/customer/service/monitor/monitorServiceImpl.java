package com.yonyou.dms.customer.service.monitor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.DTO.customer.MontiorVehicleDTO;
import com.yonyou.dms.common.domains.DTO.customer.SuggestComplaintDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendItemPO;
import com.yonyou.dms.common.domains.PO.customer.MontiorVehiclePO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class monitorServiceImpl implements monitorService {

	/**
	 * 分页查询监控车主车辆信息
	 */
	@Override
	public PageInfoDto queryMonitorInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				" SELECT DEALER_CODE,VIN_BEGIN_RANGE,VIN_END_RANGE,OWNER_CONSUME_AMOUNT_MIN,OWNER_CONSUME_AMOUNT_MAX,SALES_DATE_BEGIN, SALES_DATE_END, MONITOR_ID, VIN, LICENSE, ENGINE_NO, OWNER_NO, OWNER_NAME, MESSAGE, "
						+ " REPAIR_ORDER_TAG, BALANCE_TAG,BOOKING_TAG, OPERATOR,BEGIN_DATE,END_DATE, CREATED_AT,VER,FOUND_DATE,IS_VALID FROM TT_MONITOR_VEHICLE A"
						+ " INNER JOIN TM_ENTITY_RELATIONSHIP B  ON A.DEALER_CODE = B.PARENT_ENTITY  AND B.BIZ_CODE = 'TT_MONITOR_VEHICLE'  "
						+ " WHERE D_KEY = " + CommonConstants.D_KEY + " AND A.DEALER_CODE ='" + dealerCode
						+ "'  GROUP BY A.VIN ");
		this.setWhere(sql, queryParam, queryList);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 设置属性
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {

		if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate"))) {
			sql.append(" AND A.FOUND_DATE like ? ");
			queryList.add("%" + queryParam.get("foundDate") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sql.append(" AND A.LICENSE like ? ");
			queryList.add("%" + queryParam.get("license") + "%");
		}

	}

	// 新增车主车辆监控
	@Override
	public void addComplaint(MontiorVehicleDTO montiorVehicleDTO) throws ServiceBizException {

		MontiorVehiclePO montiorVehiclePO = new MontiorVehiclePO();// 新增
		setvehiclePo(montiorVehicleDTO, montiorVehiclePO);

	}

	private void setvehiclePo(MontiorVehicleDTO montiorVehicleDTO, MontiorVehiclePO montiorVehiclePO) {

		montiorVehiclePO.setString("LICENSE", montiorVehicleDTO.getLicense());// 车牌
		montiorVehiclePO.setString("OWNER_NO", montiorVehicleDTO.getOwnerNo());// 车主编号
		montiorVehiclePO.setString("ENGINE_NO", montiorVehicleDTO.getEngineNo());// 发动机号
		montiorVehiclePO.setString("OWNER_NAME", montiorVehicleDTO.getOwnerName());// 车主
		montiorVehiclePO.setString("VIN", montiorVehicleDTO.getVin());// VIN
		montiorVehiclePO.setDate("SALES_DATE_BEGIN", montiorVehicleDTO.getSalesDateBegin());// 销售开始日期
		montiorVehiclePO.setDate("SALES_DATE_END", montiorVehicleDTO.getSaledDateEnd());// 销售结束日期
		montiorVehiclePO.setString("VIN_BEGIN_RANGE", montiorVehicleDTO.getVinBeginRange());// 监控VIN开始区间
		montiorVehiclePO.setString("VIN_END_RANGE", montiorVehicleDTO.getVinEndRange());// 监控VIN结束区间
		montiorVehiclePO.setDate("FOUND_DATE", montiorVehicleDTO.getFoundDate());// 建档日期
		montiorVehiclePO.setDouble("OWNER_CONSUME_AMOUNT_MIN", montiorVehicleDTO.getOwnerConsumeAmountMin());// 最低消费额
		montiorVehiclePO.setDouble("OWNER_CONSUME_AMOUNT_MAX", montiorVehicleDTO.getOwnerConsumeAmountMax()); // 最贵消费额
		montiorVehiclePO.setString("MESSAGE", montiorVehicleDTO.getMessage());// 提示信息
		montiorVehiclePO.setInteger("BOOKING_TAG", montiorVehicleDTO.getBookingTag());// 预约提醒
		montiorVehiclePO.setInteger("REPAIR_ORDER_TAG", montiorVehicleDTO.getRepairOrderTag());// 接待提醒
		montiorVehiclePO.setInteger("BALANCE_TAG", montiorVehicleDTO.getBalanceTag());// 结算提醒
		montiorVehiclePO.setDate("BEGIN_DATE", montiorVehicleDTO.getBeginDate());//有效期
		montiorVehiclePO.setDate("END_DATE", montiorVehicleDTO.getBeginDate());//至有效期
		montiorVehiclePO.saveIt();

	}

	/**
	 * 修改车主车辆监控
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void updateComplaint(String monitorId, MontiorVehicleDTO montiorVehicleDTO) {
			List list = MontiorVehiclePO.findBySQL(" SELECT * FROM TT_MONITOR_VEHICLE WHERE MONITOR_ID = ? ",monitorId);
			MontiorVehiclePO montiorVehiclePO = (MontiorVehiclePO) list.get(0);
			setvehiclePo(montiorVehicleDTO, montiorVehiclePO);
	}

	/**
	 * 分页查询监控车主车辆历史信息
	 */
	@Override
	public PageInfoDto queryMonitorHistory(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"SELECT VIN_BEGIN_RANGE,VIN_END_RANGE,OWNER_CONSUME_AMOUNT_MIN,OWNER_CONSUME_AMOUNT_MAX,MONITOR_ID, VIN, LICENSE, ENGINE_NO, OWNER_NO, OWNER_NAME, MESSAGE, "
						+ "REPAIR_ORDER_TAG, BALANCE_TAG,BOOKING_TAG,OPERATOR,BEGIN_DATE,END_DATE, CREATE_DATE,VER,FOUND_DATE FROM "+CommonConstants.VT_MONITOR_VEHICLE
						+ " WHERE D_KEY = " + CommonConstants.D_KEY + " AND DEALER_CODE =" + dealerCode);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 删除车主车辆监控
	 */
	@Override
	public void deletePlanById(String monitorId) throws ServiceBizException {
		MontiorVehiclePO montiorVehiclePO = MontiorVehiclePO.findById(monitorId);
		montiorVehiclePO.delete();
	}

}
