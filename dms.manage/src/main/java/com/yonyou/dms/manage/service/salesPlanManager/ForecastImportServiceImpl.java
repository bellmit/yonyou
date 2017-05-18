package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.salesPlanManager.ForecastImportDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyForecastPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtForecastMaterialPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyForecastPO;
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
		boolean flag = tfmPO.insert();
		Long  forecastMaterialId = tfmPO.getLongId();
		
		return forecastMaterialId;
	}

	@Override
	public void deleteTmpVsMonthlyForecast(TmpVsMonthlyForecastDTO tvmfDto) throws ServiceBizException {
		String monthName = CommonUtils.checkNull(tvmfDto.getMonthName());
		Integer month = Integer.parseInt(monthName.substring(monthName.length() -2));
		Integer year = tvmfDto.getYearName();
//		forecastImportDao.deleteTmpVsMonthlyForecast(tvmfDto);
		TmpVsMonthlyForecastPO.delete("FORECAST_YEAR = ? AND FORECAST_MONTH = ?",year,month);
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

	@Override
	public List<Map> findModelYearList() {
		List<Map> list = forecastImportDao.findModelYearList();
		return list;
	}

	@Override
	public List<Map> findGroupByModelYear(String modelYear) {
		List<Map> list = forecastImportDao.findGroupByModelYear(modelYear);
		return list;
	}

	@Override
	public List<Map> findColorAndTrim(Map<String, String> params) {
		List<Map> dto = forecastImportDao.findColorAndTrim(params);
		return dto;
	}

	@Override
	public boolean saveColorAndTrim(Map<String, String> params) {
		boolean flag = true;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String material = params.get("material");
		String year = params.get("year");
		String month = params.get("month");
		month = month.substring(month.length() -2);
		if(!StringUtils.isNullOrEmpty(params)){
			String[] materialIds = material.split(",");
			for(String str: materialIds){
				List<TmpVsMonthlyForecastPO> list = TmpVsMonthlyForecastPO.find("MATERIAL_ID = ?", str);
				if(list != null && list.size() > 0){
					TmpVsMonthlyForecastPO.update("FORECAST_YEAR = ?,FORECAST_MONTH = ?,STATUS = ?,UPDATE_DATE = ?,UPDATE_BY = ?", 
							"MATERIAL_ID = ?", 
							Integer.parseInt(year),Integer.parseInt(month),OemDictCodeConstants.STATUS_ENABLE,new Date(),loginUser.getUserId(),
							str);
				}else{				
					TmpVsMonthlyForecastPO po = new TmpVsMonthlyForecastPO();
					po.setLong("MATERIAL_ID", Long.parseLong(str));
					po.setInteger("FORECAST_YEAR", Integer.parseInt(year));
					po.setInteger("FORECAST_MONTH", Integer.parseInt(month));
					po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
					po.setTimestamp("CREATE_DATE", new Date());
					po.setLong("CREATE_BY", loginUser.getUserId());
					flag = po.saveIt();
				}
			}
		}
		return flag;
	}

	@Override
	public boolean delColorAndTrim(Long id) {
		TmpVsMonthlyForecastPO.update("STATUS = ?", "MATERIAL_ID = ?", OemDictCodeConstants.STATUS_DISABLE,id);
		return true;
	}
	

}
