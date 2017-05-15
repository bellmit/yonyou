package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface ThreePackWarnNotSettledQueryService {

	Map<String, Object> queryList(String vin);

	PageInfoDto findthreePack(Map<String, String> queryParam);

	PageInfoDto findHis(Map<String, String> queryParam);

	PageInfoDto queryLabourList(String vin, String itemNo)throws Exception;

	Map<String, Object> queryHisList(String vin, Long id);
	//未结算明细下载
	List<Map> threePackWarnNotSettleDownloadDownload();
	//超48小时未结算下载
	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;
	//历史下载
	List<Map> queryHis(Map<String, String> queryParam)throws Exception;

	PageInfoDto findTtWrRepairNotSettleReasonByReasonId(Long id);

	PageInfoDto findTtWrRepairNotSettleReasonByRepairId(Long id);
	
}
