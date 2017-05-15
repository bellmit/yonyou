package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemMinclassDTO;

public interface ThreepackItemMinclassService {
	//分页查询
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
	//修改
	public void modifyMinclass(Long id, TtThreepackItemMinclassDTO tcdto) throws ServiceBizException ;
	//新增
	public long addMinclass(TtThreepackItemMinclassDTO tcdto) throws ServiceBizException;
	//删除
	public void deleteChargeById(Long id) throws ServiceBizException;
	//项目编号下拉框
	public List<Map> selectItem(Map<String, String> params);
	//导入
	public void insert(TtThreepackItemMinclassDTO rowDto);
	//导入校验
	public ImportResultDto<TtThreepackItemMinclassDTO> checkData(TtThreepackItemMinclassDTO rowDto)throws Exception;
	
	public List<Map> findById(Long id);
	

}
