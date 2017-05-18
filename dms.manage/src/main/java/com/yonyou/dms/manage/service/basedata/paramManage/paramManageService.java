package com.yonyou.dms.manage.service.basedata.paramManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.ParamManageDTO;

public interface paramManageService {

	PageInfoDto search(Map<String, String> param);

	Map findForecastProportion(Long seriesId);

	void editForecastProportion(ParamManageDTO dto);

	Map findConversionRate(Long seriesId);

	Map saveOrderRate(Map<String, String> param);

	ParamManageDTO getOrderRate();

	List<Map> getDealerLeft();

	List<Map> getDealerRight();

	void saveDealerAndDate(String ids, String date, Integer type);

	List<Map> getOrgLeft();

	List<Map> getOrgRight();

}
