package com.yonyou.dms.vehicle.service.oldPart;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface OldPartReturnManageService {

	PageInfoDto findReturnManage(Map<String, String> queryParam);

	Map<String,Object> findReturn(String returnBillNo);

	PageInfoDto findOldPartById(String returnBillNo);

	void deleteById(String returnBillNo);

	void queryDealerPass(Map<String, String> queryParams);

}
