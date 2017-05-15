package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */
public interface MvsFamilyMaintainService {
//查询f相关菲亚特车系
	public List<Map> GetMVCCheXi(Map<String, String> queryParams) throws ServiceBizException;
//关联车款
	public List<Map> getGroupCodes(Long seriesId) throws ServiceBizException;
//查询
	public PageInfoDto MVSFamilyMaintainQuery(Map<String,String> queryParam) throws ServiceBizException;
//下载
	public void MVSFamilyMaintainDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException;
//新增
	public Long MVSAdd(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException;
//回显
	public TmVhclMaterialGroupPO findMVS(Long id) throws ServiceBizException;
//删除
	public Long MVSUpdate(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException;
}
