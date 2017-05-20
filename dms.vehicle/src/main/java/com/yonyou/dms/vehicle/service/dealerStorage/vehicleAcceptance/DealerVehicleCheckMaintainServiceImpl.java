package com.yonyou.dms.vehicle.service.dealerStorage.vehicleAcceptance;

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

import com.yonyou.dms.common.domains.PO.basedata.TiVehicleInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.dealerStorage.vehicleAcceptance.DealerVehicleCheckMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsInspectionDTO;

@Service
@SuppressWarnings("all")
public class DealerVehicleCheckMaintainServiceImpl implements DealerVehicleCheckMaintainService {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DealerVehicleCheckMaintainDao dao;
	
	/**
	 * 待验收车辆查询
	 */
	@Override
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	@Override
	public Map<String, Object> queryDetail(Long id, LoginInfoDto loginInfo) throws ServiceBizException {
		Map<String, Object> map = dao.queryDetail(id,loginInfo);
		return map;
	}

	@SuppressWarnings({ "static-access" })
	@Override
	public void dealerVehicleDtialCheck(TtVsInspectionDTO tviDTO, LoginInfoDto loginInfo) throws ServiceBizException {
		try {
			// 增加经销商验收信息
			TtVsInspectionPO inspectionPO = new TtVsInspectionPO();
			SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String arrivDateString = sDate.format(tviDTO.getArriveDate());
			Date arrivDate = sDate.parse(arrivDateString);
			String nowDateString = sDate.format(new Date());
			Date nowDate = sDate.parse(nowDateString);
			inspectionPO.setTimestamp("ARRIVE_DATE", arrivDate);
			inspectionPO.setLong("CREATE_BY", loginInfo.getUserId());
			inspectionPO.setString("ARRIVE_TIME", tviDTO.getArriveTime());
			inspectionPO.setLong("VEHICLE_ID", tviDTO.getVehicleId());
			inspectionPO.setString("INSPECTION_PERSON", tviDTO.getInspectionPerson());
			inspectionPO.setString("REMARK", tviDTO.getRemark());
			if (tviDTO.getDamageFlag() == 0) {
				inspectionPO.setInteger("DAMAGE_FLAG", 0);
			} else {
				inspectionPO.setInteger("DAMAGE_FLAG", 1);
			}
			inspectionPO.insert();
			
			/*
			 * 查询该车辆是否是做过车辆零售补传
			 */
			TtVsNvdrPO nvdr = new TtVsNvdrPO();
//			nvdr.setVin(vin);
//			nvdr.setReportType(OemDictCodeConstants.RETAIL_REPORT_TYPE_03);
//			List<TtVsNvdrPO> nvdrList = factory.select(nvdr);
			List<TtVsNvdrPO> nvdrList = nvdr.find("  VIN = '"+tviDTO.getVin()+"' AND REPORT_TYPE = ? ", OemDictCodeConstants.RETAIL_REPORT_TYPE_03);
			if (nvdrList.size() == 0) {
				// 获得车辆业务类别
				String businessType = OemBaseDAO.getVehicleBusinessType(tviDTO.getVehicleId(), tviDTO.getVin());
				
				if (businessType.equals(OemDictCodeConstants.GROUP_TYPE_DOMESTIC)) {	// 国产车业务处理
					// 修改车辆信息表生命周期状态
					TmVehiclePO valueVehiclePO = TmVehiclePO.findById(tviDTO.getVehicleId());
					valueVehiclePO.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_04);
					valueVehiclePO.setDate("DEALER_STORAGE_DATE", nowDate);
					valueVehiclePO.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_19);
					valueVehiclePO.setDate("NODE_DATE", nowDate);
					valueVehiclePO.setLong("UPDATE_BY", loginInfo.getUserId());
					valueVehiclePO.setDate("UPDATE_DATE", nowDate);
					valueVehiclePO.saveIt();
					// 增加车籍信息
					TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
					vehicleChangPO.setLong("VEHICLE_ID", tviDTO.getVehicleId());
					vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.K4_VEHICLE_NODE_19);
					vehicleChangPO.setString("CHANGE_DESC", "经销商车辆验收");
					vehicleChangPO.setDate("CHANGE_DATE", nowDate);
					vehicleChangPO.setLong("CREATE_BY", loginInfo.getUserId());
					vehicleChangPO.setDate("CREATE_DATE", nowDate);
					vehicleChangPO.setInteger("RESOURCE_TYPE", 10191002);
					vehicleChangPO.setLong("RESOURCE_ID", loginInfo.getDealerId());
					vehicleChangPO.insert();
					
					// 车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistory = TtVehicleNodeHistoryPO.findFirst(" VEHICLE_ID = ?", tviDTO.getVehicleId());
					if(setVehicleNodeHistory == null){
						throw new ServiceBizException("车辆节点日期记录不存在，请联系管理员！"); 
					}
					setVehicleNodeHistory.setDate("ZPOD_DATE", nowDate); // 执行扣款日期
					setVehicleNodeHistory.saveIt();
					
					// 更新订单状态
					TtVsOrderPO setOrderPo = TtVsOrderPO.findFirst(" VIN = ? ", tviDTO.getVin());
					setOrderPo.setInteger("ORDER_STATUS", OemDictCodeConstants.SALE_ORDER_TYPE_12);
					setOrderPo.saveIt();
					
					String arriveDate = convertDate2Str(tviDTO.getArriveDate());
					
					// 插入国产车验收接口表
					dao.insertTiK4VsNvdr(arriveDate, loginInfo.getDealerCode(), loginInfo.getUserId(), tviDTO.getVin());
					
				} else if (businessType.equals(OemDictCodeConstants.GROUP_TYPE_IMPORT)) { // 进口车业务处理
					
					// 修改车辆信息表生命周期状态
					TmVehiclePO valueVehiclePO = TmVehiclePO.findById(tviDTO.getVehicleId());
					valueVehiclePO.setInteger("LIFE_CYCLE",OemDictCodeConstants.LIF_CYCLE_04);
					valueVehiclePO.setDate("DEALER_STORAGE_DATE", nowDate);
					valueVehiclePO.setInteger("NODE_STATUS",OemDictCodeConstants.VEHICLE_NODE_15);
					valueVehiclePO.setDate("NODE_DATE", nowDate);
					valueVehiclePO.setLong("UPDATE_BY", loginInfo.getUserId());
					valueVehiclePO.setDate("UPDATE_DATE", nowDate);
					valueVehiclePO.saveIt();
					
					// 增加车籍信息
					TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
					vehicleChangPO.setLong("VEHICLE_ID", tviDTO.getVehicleId());
					vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_09);
					vehicleChangPO.setString("CHANGE_DESC", "经销商车辆验收");
					vehicleChangPO.setDate("CHANGE_DATE", nowDate);
					vehicleChangPO.setLong("CREATE_BY", loginInfo.getUserId());
					vehicleChangPO.setDate("CREATE_DATE", nowDate);
					vehicleChangPO.setInteger("RESOURCE_TYPE",10191002);
					vehicleChangPO.setLong("RESOURCE_ID", loginInfo.getDealerId());
					vehicleChangPO.insert();
					
					// 插入进口车验收接口表
					TiVehicleInspectionPO vehicleInspection = new TiVehicleInspectionPO();
					vehicleInspection.setString("ACTION_CODE", "ZDLD");
					String actionDate = convertDate2Str(tviDTO.getArriveDate());
					vehicleInspection.setString("ACTION_DATE", actionDate);
					vehicleInspection.setString("VIN", tviDTO.getVin());
					vehicleInspection.setString("DEALER_CODE", loginInfo.getDealerCode());
					vehicleInspection.setLong("CREATE_BY", loginInfo.getUserId());
					vehicleInspection.setDate("CREATE_DATE", nowDate);
					vehicleInspection.setString("IS_SCAN", "0");
					vehicleInspection.setString("IS_CTCAI_SCAN", "0");
					vehicleInspection.insert();
				}
				
			}
			
			if ("1".equals(String.valueOf(tviDTO.getDamageFlag()))) {
				
				String[] detialArr =tviDTO.getDamageDatas().split(",");
				
				for (int i = 0; i < detialArr.length; i++) {
					// 新增验收明细
					String damageInfo = detialArr[i];
					String[] tempDetialArr = damageInfo.split("/");
					String descInfo = tempDetialArr[0];// 损坏描述
					String partInfo = tempDetialArr[1];// 损坏描述
					TtVsInspectionDetailPO inspectionDetailPO = new TtVsInspectionDetailPO();
					inspectionDetailPO.setLong("INSPECTION_ID", inspectionPO.getLongId());
					inspectionDetailPO.setString("DAMAGE_PART",descInfo);
					inspectionDetailPO.setString("DAMAGE_DESC",partInfo);
					inspectionDetailPO.setLong("create_by", loginInfo.getUserId());
					inspectionDetailPO.setDate("CREATE_DATE", nowDate);
					inspectionDetailPO.insert();

				}
				
			}
			
		} catch (Exception e) {
			logger.debug(loginInfo.toString(), e);
			throw new ServiceBizException("验收失败！请联系管理员！"); 
		}
	}

	/**
	 * 生成实际倒车时间
	 */
	@Override
	public List<Map> getHourList() throws ServiceBizException {
		List<Map> hourList = new ArrayList<>();
		for (int i = 1; i < 25; i++) { 
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("HOUR_LIST", i);
			hourList.add(map);
		} 
		return hourList;
	}
	
	private String convertDate2Str(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.format(date);
		}
		return null;
	}


}
