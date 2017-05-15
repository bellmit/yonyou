package com.yonyou.dms.vehicle.service.claimMgr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 旧件查询
 * @author zhiahongmiao 
 *
 */
public interface OldPartQueryDlrService {
//查询
	public PageInfoDto MVSFamilyMaintainQuery(Map<String,String> queryParam) throws ServiceBizException;
//查询f相关菲亚特车系
	public List<Map> ClaimType(Map<String, String> queryParams) throws ServiceBizException;
//下载
	public void OldPartQueryDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;

}
