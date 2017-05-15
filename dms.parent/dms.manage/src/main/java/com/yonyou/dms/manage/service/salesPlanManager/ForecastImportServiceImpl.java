package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.salesPlanManager.ForecastImportDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyForecastPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtForecastMaterialPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsRetailTaskPO;

/**
 * 
* @ClassName: OemForecastServiceImpl 
* @Description: 生产订单任务下发service实现层 
* @author zhengzengliang
* @date 2017年2月10日 下午4:10:54 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class ForecastImportServiceImpl implements ForecastImportService{

	@Autowired
	private ForecastImportDao forecastImportDao;

	@Override
	public PageInfoDto queryByMaterialid1(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = forecastImportDao.queryByMaterialid1(queryParam);
		return pgInfo;
	}

	@Override
	public Long addMaterialIds(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException {
		TmpVsMonthlyForecastPO tvmfPO = new TmpVsMonthlyForecastPO();
		//设置对象属性
		forecastImportDao.setMaterialIdsPO(tvmfPO,tvmfDto);
		tvmfPO.insert();
		Long billId = tvmfPO.getLongId();
		
		return billId;
	}

	@Override
	public Long retailforecastIssuedAdd(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException {
		TtVsRetailTaskPO tvrtPO = new TtVsRetailTaskPO();
		//设置对象属性
		forecastImportDao.setretailforecastIssuedPO(tvrtPO,tvmfDto);
		tvrtPO.insert();
		Long taskId = tvrtPO.getLongId();
		
		return taskId;
	}

	@Override
	public List<Map> getMaterialIdList(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException {
		List<Map> materialIdList = forecastImportDao.getMaterialIdList(tvmfDto);
		return materialIdList;
	}

	@Override
	public Long addMaterialId(Map materialMap, Long taskId) throws ServiceBizException {
		TtForecastMaterialPO tfmPO = new TtForecastMaterialPO();
		//插入
		forecastImportDao.setMaterialIdPO(tfmPO,materialMap,taskId);
		tfmPO.insert();
		Long  forecastMaterialId = tfmPO.getLongId();
		
		return forecastMaterialId;
	}

	@Override
	public void deleteTmpVsMonthlyForecast(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException {
		forecastImportDao.deleteTmpVsMonthlyForecast(tvmfDto);
	}

	@Override
	public PageInfoDto getRetailforecasList3(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = forecastImportDao.getRetailforecasList3(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> forecastImportOTDQuery(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> forecastImportOTDList = forecastImportDao.forecastImportOTDQuery(queryParam);
		return forecastImportOTDList;
	}

	@Override
	public void modifyforecastOTDSubmit(Long taskId, TtVsMonthlyForecastDTO userDto) throws ServiceBizException {
		forecastImportDao.modifyforecastOTDSubmit(taskId, userDto);
	}

	@Override
	public List<Map> selectTmVhclMaterialGroupUnique(TmVhclMaterialGroupPO tvmgPo) throws ServiceBizException {
		List<Map> list = forecastImportDao.selectTmVhclMaterialGroupUnique(tvmgPo);
		return list;
	}

	@Override
	public List<Map> selectTtVsRetailTaskUnique(TtVsRetailTaskPO trtPO) throws ServiceBizException {
		List<Map> list = forecastImportDao.selectTtVsRetailTaskUnique(trtPO);
		return list;
	}

	@Override
	public List<Map> getForecastPackageOTDFilterList(String groupCode, LoginInfoDto loginInfo,
			String taskId)throws ServiceBizException {
		List<Map> list = 
				forecastImportDao.selectTtVsRetailTaskUnique(groupCode, loginInfo, taskId);
		return list;
	}

	@Override
	public List<Map> getForecastCarOTDListTotal(String groupCode, LoginInfoDto loginInfo,
			String taskId)throws ServiceBizException {
		List<Map> list = 
				forecastImportDao.getForecastCarOTDListTotal(groupCode, loginInfo, taskId);
		return list;
	}
	

}
