package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackWarnQueryDao;

@Service
public class ThreePackWarnQueryServiceImpl implements ThreePackWarnQueryService {
	@Autowired
	ThreePackWarnQueryDao dao;

	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {

		return dao.findItem(queryParam);
	}

	@Override
	public PageInfoDto findHis(Map<String, String> queryParam) throws Exception {

		return dao.findHis(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {

		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public List<Map> queryMX(Map<String, String> queryParam) throws Exception {

		return dao.threePackWarnDetailQuery(queryParam);
	}

	@Override
	public List<Map> queryHis(Map<String, String> queryParam) throws Exception {

		return dao.queryHis(queryParam);
	}

	@Override
	public Map<String, Object> queryList(String vin,Long id) {

		return dao.threePackWarnInfo(vin,id);
	}

	@Override
	public PageInfoDto queryLabourList(String vin,String itemNo ) {
		
		return dao.threePackControl(vin,itemNo);
	}

	@Override
	public List<Map> selectGroupName(Map<String, String> params) {
		
		return dao.selectGroupName(params);
	}


}
