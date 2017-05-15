package com.yonyou.dms.manage.service.basedata.workWeek;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.dao.workWeek.WorkWeekDefinitionDao;
import com.yonyou.dms.manage.domains.DTO.basedata.TmWorkWeekImportDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.WorkWeekDTO;
import com.yonyou.dms.manage.domains.PO.basedata.TmWorkWeekPO;

@Service
public class WorkWeekDefinitionServiceImpl implements WorkWeekDefinitionService {
	
	@Autowired
	private WorkWeekDefinitionDao dao;
	
    @Autowired
    private ExcelGenerator excelService;

	@Override
	public List<Map> getYear() {
		List<Map> list = dao.getYear();
		return list;
	}

	@Override
	public List<Map> getMonth() {
		List<Map> list = new ArrayList<>();
		for (int i = 1; i < 13; i++) { // 初始化当前年包含当前月以及以后月信息
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("work_month",i);
			list.add(map);
		}
		return list; 
	}

	@Override
	public List<Map> getWeek( String workYear, String workMonth) {
		List<Map> list = dao.getWeek(workYear,workMonth);
		return list;
	}

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = dao.search(param);
		return dto;
	}

	@Override
	public void addOrUpdate(WorkWeekDTO dto) {
		Long weekId = dto.getWeekId();
		Integer workYear = dto.getWorkYear();
		Integer workMonth = dto.getWorkMonth();
		Integer workWeek = dto.getWorkWeek();
		Date startDate = dto.getStartDate();
		Date endDate = dto.getEndDate();
		Integer status = dto.getStatus();
		Date currentTime = new Date();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		if(!dao.checkDate(startDate,endDate,weekId)){
			throw new ServiceBizException("时间重合,请重新选择！");
		}
		
		if(!dao.checkDate2(workYear, workMonth, workWeek, weekId)){
			throw new ServiceBizException("该工作周已定义,请重新选择！");
		}
		
		boolean newOne = weekId == null;
		TmWorkWeekPO po = null;
		
		if(newOne){
			po = new TmWorkWeekPO();
		}else{
			po  = TmWorkWeekPO.findById(weekId);
		}
		
		if(workYear != null){
			po.setInteger("WORK_YEAR", workYear);
		}
		if(workMonth != null){
			po.setInteger("WORK_MONTH", workMonth);
		}
		if(workWeek != null){
			String week = null;
			if(newOne){				
				String year = String.valueOf(workYear); 
				String month = String.valueOf(workMonth);
				week = String.valueOf(workWeek);
				year = year.substring(2,4);
				if(month.length()<2){
					month = "0"+month; 
				}
				if(week.length()<2){
					week = "0"+week; 
				}
				week = year+month+week;
			}else{
				week = String.valueOf(workWeek);
			}
			po.setInteger("WORK_WEEK", Integer.parseInt(week));
		}
		po.setDate("START_DATE", startDate);
		po.setDate("END_DATE", endDate);
		po.setInteger("STATUS", status);
		
		if(newOne){
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", currentTime);
		}else{
			po.setLong("UPDATE_BY", loginInfo.getUserId());
			po.setDate("UPDATE_DATE", currentTime);
		}
		
		boolean flag = po.saveIt();
	}

	@Override
	public Map<String, Object> findById(Long weekId) throws ParseException {
		TmWorkWeekPO po = TmWorkWeekPO.findById(weekId);
		Map<String,Object> map = po.toMap();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = sdf.parse(String.valueOf(map.get("start_date")));
		Date endDate = sdf.parse(String.valueOf(map.get("end_date")));
		map.put("start_date", startDate);
		map.put("end_date", endDate);
		return map;
	}

	@Override
	public void exportExcel(Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response) {
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		List<Map> resultList = dao.queryExport(queryParam);
//		List<Map> resultList = new ArrayList<>();
		String excelName = "工作周下载";
		excelData.put(excelName, resultList);
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("WORK_YEAR", "工作年"));
		exportColumnList.add(new ExcelExportColumn("WORK_MONTH", "工作月"));
		exportColumnList.add(new ExcelExportColumn("WORK_WEEK", "工作周"));
		exportColumnList.add(new ExcelExportColumn("START_DATE", "开始时间"));
		exportColumnList.add(new ExcelExportColumn("END_DATE", "结束时间"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态",ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, excelName + ".xls", request, response);
		
	}

	/**
	 * excel导入处理
	 */
	@Override
	public TmWorkWeekImportDTO validatorWorkData(TmWorkWeekImportDTO dto) {
		Date currentTime = new Date();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String,Object> map = new HashMap<>();
		Integer workYear = dto.getWorkYear();
		Integer workMonth = dto.getWorkMonth();
		Integer workWeek = dto.getWorkWeek();
		Integer rowNo = dto.getRowNO();
		Date startDate  = dto.getStartDate();
		Date endDate = dto.getEndDate();
		if(!dao.checkDate(startDate, endDate,null )){
			dto.setErrorMsg("第"+rowNo+"行时间重合,请重新输入");
			return dto;
		}
		if(workYear != null){
			String year = String.valueOf(workYear).substring(2,4);
			String month = String.valueOf(workMonth);
			String week = String.valueOf(workWeek);
			if(week.length() != 6){
				dto.setErrorMsg("第"+rowNo+"行工作周应该为六位数(例如:150901),请重新输入!");
				return dto;
			}
		    if(month.length()<2){
		    	month = "0"+month; 
		    }
		    week = week.substring(0, 4);
		    if(!week.equals(year+month)){
				dto.setErrorMsg("第"+rowNo+"行工作周与工作年月不符合,请重新输入!");
				return dto;
		    }
		}
		TmWorkWeekPO.delete("WORK_YEAR = ? AND WORK_MONTH = ? AND WORK_WEEK = ? ", workYear,workMonth,workWeek);
		TmWorkWeekPO po = new TmWorkWeekPO();
		po.setInteger("WORK_YEAR", workYear);
		po.setInteger("WORK_MONTH", workMonth);
		po.setInteger("WORK_WEEK", workWeek);
		po.setDate("START_DATE", startDate);
		po.setDate("END_DATE", endDate);
		po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
		po.setLong("UPDATE_BY", loginInfo.getUserId());
		po.setDate("UPDATE_DATE", currentTime);
		boolean flag = po.saveIt();
		return null;
	}
	

}
