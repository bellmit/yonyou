package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackWarnNotSettledQueryDao;

@Service
public class ThreePackWarnNotSettledQueryServiceImpl implements ThreePackWarnNotSettledQueryService{

	@Autowired
	ThreePackWarnNotSettledQueryDao dao;
	@Override
	public Map<String, Object> queryList(String vin) {
	
		return dao.threePackWarnNotSettledInfo(vin);
	}

	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) {
		
		return dao.findItem(queryParam);
	}

	@Override
	public PageInfoDto findHis(Map<String, String> queryParam) {
		
		return dao.findHis(queryParam);
	}

	@Override
	public PageInfoDto queryLabourList(String vin, String itemNo) throws Exception{
		return dao.mapcount(itemNo,vin);
	}

	@Override
	public Map<String, Object> queryHisList(String vin, Long id) {
		
		return dao.threePackWarnInfo(vin, id);
	}

	@Override
	public List<Map> threePackWarnNotSettleDownloadDownload() {
		// TODO Auto-generated method stub
		return dao.threePackWarnNotSettleDownloadDownload();
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public List<Map> queryHis(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageInfoDto findTtWrRepairNotSettleReasonByReasonId(Long id) {
		// TODO Auto-generated method stub
		return dao.findTtWrRepairNotSettleReasonByReasonId(id);
	}

	@Override
	public PageInfoDto findTtWrRepairNotSettleReasonByRepairId(Long id) {
		// TODO Auto-generated method stub
		return dao.findTtWrRepairNotSettleReasonByRepairId(id);
	}



}
