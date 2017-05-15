package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface VehicleAllotQueryService {

	PageInfoDto searchVehicleAllotQuery(Map<String, String> param);

	Map<String, Object> findDetailById(String transferId);

	void exportQueryInfo(Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response);

}
