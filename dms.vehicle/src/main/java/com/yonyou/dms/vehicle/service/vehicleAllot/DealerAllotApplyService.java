package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface DealerAllotApplyService {

	PageInfoDto search(Map<String, String> param);

	Map<String, String> vehicleTransfersApply(String inDealerCode, String reason, String vehicleIds);

	Map<String,Object> getDealerResult(String dealerCode);

	Map<String, String> checkDealer(String dealerCode);

}
