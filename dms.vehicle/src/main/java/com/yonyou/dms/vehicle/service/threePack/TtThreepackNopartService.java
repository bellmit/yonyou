package com.yonyou.dms.vehicle.service.threePack;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNopartDTO;

public interface TtThreepackNopartService {
		//分页查询
		public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
		//修改
		public void modifyNopart(Long id, TtThreepackNopartDTO tcdto) throws ServiceBizException ;
		//新增
		public long addNopart(TtThreepackNopartDTO tcdto) throws ServiceBizException;
		//删除
		public void deleteById(Long id) throws ServiceBizException;
		//导入
		public void insert(TtThreepackNopartDTO rowDto);
		//导入校验
		public ImportResultDto<TtThreepackNopartDTO> checkData(TtThreepackNopartDTO rowDto)throws Exception;
		
}
