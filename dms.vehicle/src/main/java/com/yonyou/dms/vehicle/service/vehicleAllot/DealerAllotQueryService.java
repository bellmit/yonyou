package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface DealerAllotQueryService {

	PageInfoDto search(Map<String, String> param);

}
