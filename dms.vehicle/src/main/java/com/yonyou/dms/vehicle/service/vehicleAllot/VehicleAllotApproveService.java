package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface VehicleAllotApproveService {

	PageInfoDto searchVehicleAllotApprove(Map<String, String> param);

	List<Map> selectSeriesName(Map<String, String> params);

	List<Map> selectBrandName(Map<String, String> params);

	List<Map> selectGroupName(Map<String, String> params);

	List<Map> selectModelYear(Map<String, String> params);

	List<Map> selectColorName(Map<String, String> params);

	List<Map> selectTrimName(Map<String, String> params);

	Map<String, String> checkStatus(String[] ids, String opinion, String result)throws Exception;

}
