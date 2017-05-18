package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.salesPlanManager.OemForecastDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.OemForecastAuditDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsProImpInfoAuditPO;

/**
 * 
* @ClassName: OemForecastServiceImpl 
* @Description: 生产订单审核service实现层 
* @author zhengzengliang
* @date 2017年2月10日 下午4:10:54 
*
 */
@Service
@SuppressWarnings("rawtypes")
public class OemForecastServiceImpl implements OemForecastService{

	@Autowired
	OemForecastDao oemForecastDao;
	
	@Autowired
    FileStoreService fileStoreService;
	
	Calendar calendar = Calendar.getInstance();
	int year = calendar.get(Calendar.YEAR);		
	int month = calendar.get(Calendar.MONTH)+1;
	
	
	@Override
	public List<Map> getYearList() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct FORECAST_YEAR from TT_VS_MONTHLY_FORECAST where 1=1 and  FORECAST_YEAR is not null");
		sqlSb.append(" order by FORECAST_YEAR desc \n");
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	
	@Override
	public List<Map> getTaskCode(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct tvmf.TASK_ID from TT_VS_MONTHLY_FORECAST tvmf,Tt_Forecast_Material tfm  where  1=1 and tfm.GROUP_ID is null and tvmf.TASK_ID = tfm.TASK_ID and tvmf.status="+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT);
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("yearcode"))){
			sqlSb.append(" and FORECAST_YEAR = ?");
			params.add(queryParam.get("yearcode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("monthcode"))){
			sqlSb.append(" and FORECAST_MONTH = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("monthcode"));
		}
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	
	@Override
	public PageInfoDto getOTDForecastQueryList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.getOTDForecastQueryList(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> queryforecastAuditDataExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.queryforecastAuditDataExport(queryParam);
		return result;
	}

	@Override
	public void insertTmpVsProImpAudit(OemForecastAuditDTO forecastDTO) throws ServiceBizException {
		TmpVsProImpInfoAuditPO oemForecastAuditPO = new TmpVsProImpInfoAuditPO();
		//设置对象属性
		oemForecastDao.setForecastPO(oemForecastAuditPO,forecastDTO);
		oemForecastAuditPO.insert();
	}

	/**
	 * 生产订单查询获取年
	 */
	@Override
	public List<Map> forecastQueryInit() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct YEAR from Tt_Vs_Retail_Task  where 1=1 and IS_DEL="+DictCodeConstants.IS_DEL_00 +" AND ((YEAR = "+year+" AND MONTH >= "+month+") OR (YEAR > "+year+" ))");
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		Map<String,Integer> yearMap = new HashMap<String,Integer>();
		if(result.size() == 0){
			yearMap.put("YEAR", Integer.valueOf(year));
			result.add(yearMap);
			return result;
		}else{
			return result;
		}
	}

	@Override
	public List<Map> getMonthPlanTaskNoList2() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct tfm.TASK_ID from Tt_Vs_Retail_Task tvrt,TT_FORECAST_MATERIAL tfm where tvrt.TASK_ID = tfm.TASK_ID AND tfm.MATERIAL_ID is not null AND YEAR="+year+" and MONTH="+month);
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	@Override
	public PageInfoDto forecastQueryTotal(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.forecastQueryTotal(queryParam);
		return pgInfo;
	}

	@Override
	public PageInfoDto getOemForecastQueryTotalList2(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.getOemForecastQueryTotalList2(queryParam);
		return pgInfo;
	}

	@Override
	public PageInfoDto getOemForecastQueryDetailList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.getOemForecastQueryDetailList(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> forecastTotalDownload(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.forecastTotalDownload(queryParam);
		return result;
	}

	@Override
	public List<Map> forecastTotalDownload2(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.forecastTotalDownload2(queryParam);
		return result;
	}

	@Override
	public List<Map> forecastDetailDownload(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.forecastDetailDownload(queryParam);
		return result;
	}

	@Override
	public PageInfoDto findNoHandInDelears2(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.findNoHandInDelears2(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> noHandInDealerDownload(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.noHandInDealerDownload(queryParam);
		return result;
	}

	/**
	 * 生产订单号序列号跟踪(OTD)(获取年)
	 */
	@Override
	public List<Map> getMonthForecastYearList() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("select distinct FORECAST_YEAR from TT_VS_MONTHLY_FORECAST  where FORECAST_YEAR is not null and STATUS="+DictCodeConstants.TT_VS_MONTHLY_FORECAST_SUBMIT + "\n");
		sqlSb.append(" order by FORECAST_YEAR desc ");
		List<Object> params = new ArrayList<Object>();
		//执行查询操作
		List<Map> result = OemDAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	@Override
	public List<Map> getReportTaskNoList(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.getReportTaskNoList(queryParam);
		return result;
	}

	@Override
	public PageInfoDto OTDfindBySerialNumber(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = oemForecastDao.OTDfindBySerialNumber(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> getOemForecastQueryQueryListOtd(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> result = oemForecastDao.getOemForecastQueryQueryListOtd(queryParam);
		return result;
	}

	@Override
	public ImportResultDto<OemForecastAuditDTO> checkData(String taskId) 
			throws ServiceBizException {
		//验证临时表任务编号是否和导入是勾选的一致
		List<Map> tcList = oemForecastDao.checkTaskId(taskId);
		OemForecastAuditDTO ofaDTO = new OemForecastAuditDTO();
		ArrayList<OemForecastAuditDTO> tvypDTOList = new ArrayList<OemForecastAuditDTO>();
		ImportResultDto<OemForecastAuditDTO> importResult = new ImportResultDto<OemForecastAuditDTO>();
		if (null != tcList && tcList.size() > 0) {
			for (int i = 0; i < tcList.size(); i++) {
				Map map = tcList.get(i);
				String taskId2 = CommonUtils.checkNull(map.get("TASK_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("任务编号"+taskId2+"与勾选的任务编号"+taskId+"不一致");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		//验证临时表任务编号是否存在
		List<Map> tList = oemForecastDao.checkTaskId();
		if (null != tList && tList.size() > 0) {
			for (int i = 0; i < tList.size(); i++) {
				Map map = tList.get(i);
				String taskId3 = CommonUtils.checkNull(map.get("TASK_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("任务编号"+taskId3+"不存在");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		
		//验证临时表物料id是否存在
		List<Map> mList = oemForecastDao.checkMaterialId();
		if (null != mList && mList.size() > 0) {
			for (int i = 0; i < mList.size(); i++) {
				Map map = mList.get(i);
				String mId = CommonUtils.checkNull(map.get("MATERIAL_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("materialId(物料d):"+mId+"不存在");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		//验证临时表FORECASTID是否存在
		List<Map> fList = oemForecastDao.checkForecastId();
		if (null != fList && fList.size() > 0) {
			for (int i = 0; i < fList.size(); i++) {
				Map map = fList.get(i);
				String forecastId = CommonUtils.checkNull(map.get("FORECAST_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("FORECASTID"+forecastId+"不存在");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		//验证临时表FORECASTID状态
		List<Map> fsList = oemForecastDao.checkForecastIdStatus();
		if (null != fsList && fsList.size() > 0) {
			for (int i = 0; i < fsList.size(); i++) {
				Map map = fsList.get(i);
				String forecastId = CommonUtils.checkNull(map.get("FORECAST_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("FORECASTiD"+forecastId+"为经销商未上报或OTD已审核过");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		//验证临时表detail_id是否存在
		List<Map> dList = oemForecastDao.checkDetailId();
		if (null != dList && dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				Map map = dList.get(i);
				String detailId = CommonUtils.checkNull(map.get("DETAIL_ID"));
				
				ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
				ofaDTO.setErrorMsg("detailId(明细id):"+detailId+"不存在");
				tvypDTOList.add(ofaDTO);
				importResult.setErrorList(tvypDTOList);
			}		
			return importResult;
		}
		//验证临时表OTD确认数量是否是整数
		List<Map> cList = oemForecastDao.checkConfirmNum();
		if (null != cList && cList.size() > 0) {
			for (int i = 0; i < cList.size(); i++) {
				Map map = cList.get(i);
				String cNum = checkConfirmNum(CommonUtils.checkNull(map.get("CONFIRM_NUM")));
				if(cNum.length()!=0){//表示.xls的数量填写的为非整数
					ofaDTO.setRowNO(Integer.parseInt(map.get("ROW_NUMBER").toString()));
					ofaDTO.setErrorMsg(cNum);
					tvypDTOList.add(ofaDTO);
					importResult.setErrorList(tvypDTOList);
				}
			}		
			return importResult;
		}
		if(!StringUtils.isNullOrEmpty(importResult)){			
			return importResult;
		}else{
			return null;
		}
	}

	/**
	 * 校验合计
	 * @param amtCount 数量字符串
	 */
	private String checkConfirmNum(String amtCount){
	    Integer sumTmp=new Integer(0);
	    StringBuffer errors=new StringBuffer("");
	    if("".equals(amtCount)){
	    	return errors.toString();
	    }
	    try {
			Integer julAmt=new Integer(amtCount);
			sumTmp+=julAmt;
		} catch (Exception e) {
			errors.append("OTD确认数量必须是整数");
		}
	  
		return errors.toString();
	}

	@Override
	public List<Map> getTmVsProImpAudit() throws ServiceBizException {
		List<Map> list = oemForecastDao.getTmVsProImpAudit();
		return list;
	}
	
	
}
