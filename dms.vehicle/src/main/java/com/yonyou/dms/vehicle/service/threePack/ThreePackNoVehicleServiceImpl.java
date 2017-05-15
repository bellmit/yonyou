package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.threePack.ThreePackNoVehicleDao;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNovehicleDTO;

@Service
public class ThreePackNoVehicleServiceImpl implements ThreePackNoVehicleService{

	@Autowired
	ThreePackNoVehicleDao dao;
	@Override
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception {
		
		return dao.findItem(queryParam);
	}

	@Override
	public void modifynoVehicle(Long id, TtThreepackNovehicleDTO tcdto) throws ServiceBizException {
		dao.modifynoVehicle(id, tcdto);
		
	}

	@Override
	public long addnoVehicle(TtThreepackNovehicleDTO tcdto) throws ServiceBizException {
		return dao.addnoVehicle(tcdto);
	}

	@Override
	public void deleteChargeById(Long id) throws ServiceBizException {
		dao.deleteChargeById(id);
		
	}

	@Override
	public void insert(TtThreepackNovehicleDTO rowDto) {
		dao.insert(rowDto);
		
	}

	@Override
	public ImportResultDto<TtThreepackNovehicleDTO> checkData(TtThreepackNovehicleDTO rowDto)
			throws Exception {
		return dao.checkData(rowDto);
	}

	@Override
	public List<Map> findById(Long id) {
		return dao.findById(id);
	}

	@Override
	public  List<Map> findByVin(String vin) {
	
		return dao.quertVehicleDetailInfo(vin);
	}

	@Override
	public PageInfoDto  findAllVIN(Map<String, String> queryParam) throws Exception {
		// TODO Auto-generated method stub
		return dao.partQuery(queryParam);
	}

}
