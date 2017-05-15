package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNovehicleDTO;

public interface ThreePackNoVehicleService {
	//分页查询
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
	//修改
	public void modifynoVehicle(Long id, TtThreepackNovehicleDTO tcdto) throws ServiceBizException ;
	//新增
	public long addnoVehicle(TtThreepackNovehicleDTO tcdto) throws ServiceBizException;
	//删除
	public void deleteChargeById(Long id) throws ServiceBizException;
	//导入
	public void insert(TtThreepackNovehicleDTO rowDto);
	//导入校验
	public ImportResultDto<TtThreepackNovehicleDTO> checkData(TtThreepackNovehicleDTO rowDto)throws Exception;
	
	public List<Map> findById(Long id);
	
	public  List<Map> findByVin(String vin);
	
	public PageInfoDto  findAllVIN(Map<String, String> queryParam) throws Exception;
}
