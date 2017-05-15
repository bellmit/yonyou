package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.personInfoManager.TcUserDTO;

/**
 * 授权人员管理
 * @author Administrator
 *
 */
public interface AuthUserService {
    //授权人员管理查询
	public PageInfoDto  AuthUserQuery(Map<String, String> queryParam);
	//查询所有授权顺序
	public List<Map> getAuthLevel(Map<String, String> queryParams) throws ServiceBizException ;
	//修改信息回显
	public TcUserPO getAuthLevelById(Long id); 
	//修改
	public void edit(Long id,TcUserDTO dto);

}
