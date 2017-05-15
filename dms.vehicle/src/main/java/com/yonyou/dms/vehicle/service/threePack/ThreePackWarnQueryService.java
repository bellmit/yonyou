package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

public interface ThreePackWarnQueryService {
		//分页查询
		public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
		//分页查询历史
		public PageInfoDto findHis(Map<String, String> queryParam) throws Exception ;
		//下载
		public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;
		//下载明细
		public List<Map> queryMX(Map<String, String> queryParam) throws Exception;
		//下载维修历史
		public List<Map> queryHis(Map<String, String> queryParam) throws Exception;
		//预警列表客户信息
		public Map<String,Object> queryList(String vin,Long id);
		//预警列表查询
		public PageInfoDto queryLabourList(String vin,String itemNo);
		//车型查询
		 public List<Map> selectGroupName(Map<String, String> params);
	
		
}
