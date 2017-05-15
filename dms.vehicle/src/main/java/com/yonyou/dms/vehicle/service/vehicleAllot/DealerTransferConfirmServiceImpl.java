package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS007Cloud;
import com.yonyou.dms.common.domains.PO.basedata.TiWxVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.dao.vehicleAllotDao.DealerTransferConfirmDao;

@Service
public class DealerTransferConfirmServiceImpl implements DealerTransferConfirmService {
	
	@Autowired
	private DealerTransferConfirmDao transDao;
	@Autowired
	SADCS007Cloud sadcs007cloud;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = transDao.search(param);
		return dto;
	}

	@Override
	public Map<String, String> vehicleConfirm(String transferIds) throws Exception {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long userId = loginUser.getUserId();
		Map<String,String> map = new HashMap<String,String>();
		Date currentTime = new Date();
		boolean flag = false;
		// 获得调入经销商ID
		List<Map> list = transDao.getTransferInDealerIds(transferIds);
		if(list != null && !list.isEmpty()){
			for(int i = 0;i < list.size(); i++){
				Map tmap = list.get(i);
				Long transferId = Long.parseLong(String.valueOf(tmap.get("TRANSFER_ID")));  // 调拨信息ID
				Long vehicleId = Long.parseLong(String.valueOf(tmap.get("VEHICLE_ID")));  // 车辆ID
				Long outDealerId = Long.parseLong(String.valueOf(tmap.get("OUT_DEALER_ID")));  // 调出经销商ID
				Long inDealerId = Long.parseLong(String.valueOf(tmap.get("IN_DEALER_ID")));  // 调入经销商ID
				
				TmVehiclePO vehiclePo = TmVehiclePO.findById(vehicleId);
				Integer lifeCycle = vehiclePo.getInteger("LIFE_CYCLE");
				// 校验该车辆的生命周期是否属于在途或者在库
				if(OemDictCodeConstants.LIF_CYCLE_03.equals(lifeCycle) 
				   || OemDictCodeConstants.LIF_CYCLE_04.equals(lifeCycle)){
					// 将被调拨的车辆状态置为已调拨
					TtVsVehicleTransferPO.update("CHECK_STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
							"TRANSFER_ID = ? AND CHECK_STATUS = ?", 
							OemDictCodeConstants.TRANSFER_CHECK_STATUS_04,loginUser.getUserId(),currentTime,
							transferId,OemDictCodeConstants.TRANSFER_CHECK_STATUS_02);
					
					// 更新车辆主数据表被调拨车辆的经销商ID
					TmVehiclePO.update("DEALER_ID = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
							"VEHICLE_ID = ? AND DEALER_ID = ?", 
							inDealerId,loginUser.getUserId(),currentTime,
							vehicleId,outDealerId);
					
					// 详细车籍
					TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
					vehicleChangPO.setLong("VEHICLE_ID", vehicleId);
					vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_14);
					vehicleChangPO.setString("CHANGE_DESC", "调拨入库");
					vehicleChangPO.setTimestamp("CREATE_DATE", currentTime);
					vehicleChangPO.setLong("CREATE_BY", userId);
					vehicleChangPO.setInteger("RESOURCE_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
					vehicleChangPO.setString("RESOURCE_ID", outDealerId);
					flag = vehicleChangPO.saveIt();
					
					/*
					 * 调用车辆调拨接口 Begin
					 */
					List<Long> dealerIdList = new LinkedList<Long>();
					dealerIdList.add(inDealerId);
					dealerIdList.add(outDealerId);
					
					// 测试时关闭，提交时要放开
					sadcs007cloud.execute(dealerIdList, vehicleId, inDealerId, outDealerId);
					
					TtVsVehicleTransferPO.update("IS_SEND = ?", "TRANSFER_ID = ?", 1,transferId);
					
					/*
					 * 调用车辆调拨接口 End..
					 */
					
					TmDealerPO tdPO = TmDealerPO.findById(inDealerId);
					
					// 车辆验收表中有isUpdate写1，否则0
					TiWxVehiclePO tPO = new TiWxVehiclePO();
					tPO.setLong("INSPECTION_ID", transferId);
					tPO.setString("VIN", vehiclePo.getString("VIN"));
					tPO.setString("DEALER_CODE", tdPO.getString("DEALER_CODE"));
					tPO.setString("IS_SCAN", "0");
					tPO.setString("IS_UPDATE", "1");
					
					Map materialMap = transDao.getMaterial(vehiclePo.getString("MATERIAL_ID"));
					if(map != null && !map.isEmpty()){
						tPO.setString("BRAND_ID", materialMap.get("BRAND_CODE"));
						tPO.setString("SERIES_ID", materialMap.get("SERIES_CODE"));
						tPO.setString("MODEL_ID", materialMap.get("MODEL_CODE"));
						tPO.setString("COLOR_ID", materialMap.get("COLOR_CODE"));
					}
					tPO.setLong("CREATE_BY", userId);
					tPO.setTimestamp("CREATE_DATE", currentTime);
					flag = tPO.saveIt();
					
				}
			}
		}else{
			map.put("code", "false");
			map.put("msg", "入库出错！");
			return map;
		}
		map.put("code", "true");
		map.put("msg", "入库成功！");
		return map;
	}

}
