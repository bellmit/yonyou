package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackWarnParaDTO;

public interface TtThreepackWarnParaService {
	//分页查询
		public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
		//修改
		public void modifyMinclass(Long id, TtThreepackWarnParaDTO tcdto) throws ServiceBizException ;
		//新增
		public long addMinclass(TtThreepackWarnParaDTO tcdto) throws ServiceBizException;
	
		public List<Map> findById(Long id);
		
		public void deleteById(Long id);
}
