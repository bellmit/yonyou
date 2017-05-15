package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface DealerTransferConfirmService {

	PageInfoDto search(Map<String, String> param);

	Map<String, String> vehicleConfirm(String transferId) throws Exception;

}
