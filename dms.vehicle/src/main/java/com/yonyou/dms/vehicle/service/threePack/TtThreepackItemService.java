package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemDTO;

public interface TtThreepackItemService {
		//分页查询
		public PageInfoDto findItem(Map<String, String> queryParam) throws Exception ;
		//修改
		public void modifyItem(Long id, TtThreepackItemDTO tcdto) throws ServiceBizException ;

}
