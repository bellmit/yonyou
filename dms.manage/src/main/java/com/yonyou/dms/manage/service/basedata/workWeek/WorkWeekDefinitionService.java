package com.yonyou.dms.manage.service.basedata.workWeek;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.TmWorkWeekImportDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.WorkWeekDTO;

public interface WorkWeekDefinitionService {

	List<Map> getYear();

	List<Map> getMonth();

	List<Map> getWeek(String workYear, String workMonth);

	PageInfoDto search(Map<String, String> param);

	void addOrUpdate(WorkWeekDTO dto);

	Map<String, Object> findById(Long weekId) throws ParseException;

	void exportExcel(Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response);

	TmWorkWeekImportDTO validatorWorkData(TmWorkWeekImportDTO rowDto);

}
