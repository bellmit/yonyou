package com.yonyou.dms.manage.service.basedata.personInfoManager;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SalesSwitchChageService {
	//查询经销商信息
	public PageInfoDto salesSwitchChangeQuery(Map<String, String> queryParam) ;
	
	
    //销售大区查询
	List<Map> getBigOrg(Map<String, String> queryParams) throws ServiceBizException;
	
	//销售小区查询
	List<Map> getSmallOrg(String bigorgid, Map<String, String> queryParams) throws ServiceBizException;


	public List<Map> getOrgLeft();


	public List<Map> getOrgRight();


	public String searchOrg(Map<String, String> param);


	public void saveChange(String ids);

}
