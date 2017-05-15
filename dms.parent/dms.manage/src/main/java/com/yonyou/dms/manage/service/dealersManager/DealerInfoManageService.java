package com.yonyou.dms.manage.service.dealersManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerDetailedinfoDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerMaintainImportDTO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmDealerEdPo;

/**
 * 经销商管理
 * @author ZhaoZ
 *@date 2017年2月24日
 */
public interface DealerInfoManageService {

	
	//查询经销商信息
	public PageInfoDto getDealers(Map<String, String> queryParams)  throws  ServiceBizException;
	//查询经销商基本信息
	public PageInfoDto getDealerInfos(Map<String, String> queryParams) throws  ServiceBizException;
	//经销商基本信息下载
	public List<Map> findDealerInfousDownload(Map<String, String> queryParams) throws  ServiceBizException;
	//经销商详细信息
	public TmDealerEdPo getDealerDetailedInfo(Long dealerId) throws  ServiceBizException;
	//经销商维护信息下载
	public List<Map> dealerMaintainInfosDownload(Map<String, String> queryParams) throws  ServiceBizException;
	//车系下载
	public List<Map> findGroupDownload() throws  ServiceBizException;
	//查询经销商基本信息
	public PageInfoDto getDlrDealers(Map<String, String> queryParams, LoginInfoDto loginUser) throws  ServiceBizException;
	// 查询业务范围到车系
	public List<Map> getDealerBuss()throws  ServiceBizException;
	//得到TmDealerPO
	public TmDealerPO getTmDealerPO(Long dealerId)throws  ServiceBizException;
	
	public Map<String, Object> getDealerOrgCodeAndId(Long dealerId)throws  ServiceBizException;
	//经销商基本信息审核
	public PageInfoDto queryDealerAuditInfo(Map<String, String> queryParams)throws ServiceBizException;
	//经销商维护信息修改
	public void editModifyDealer(DealerDetailedinfoDTO dto,Long dealerd) throws ServiceBizException;
	//新增經銷商
	public void addModifyDealer(DealerDetailedinfoDTO dto)throws ServiceBizException;
	//查询小区
	public List<Map> getSmallOrg(Map<String, String> queryParams)throws ServiceBizException;
	//查询经销商集团
	public PageInfoDto getDealerGroupInfos(Map<String, String> queryParams)throws ServiceBizException;
	//关键岗位人员信息查询
	public PageInfoDto getDealerKeyPersonOTD( Long dealerId)throws ServiceBizException;
	//经销商审核通过
	public void queryDealerPass(Map<String, String> queryParams)throws ServiceBizException;
	//经销商审核驳回
	public void queryDealerReject(Map<String, String> queryParams)throws ServiceBizException;
	//经销商审核通过byID
	public void queryDealerPass(Long dealerId)throws ServiceBizException;
	//经销商审核驳回byID
	public void queryDealerReject(Long dealerId)throws ServiceBizException;
	//经销商维护查询页面
	public Map<String, Object> queryDealerInfoDetail1(Long dealerId)throws ServiceBizException;
	//经销商明细修改
	public void editdlrModifyDealerInfos(DealerDetailedinfoDTO dto, Long dealerId)throws ServiceBizException;
	//经销商信息上报
	public void dealerInfoSubmitUpdate(Long dealerId)throws ServiceBizException;
	//查询展厅信息
	public PageInfoDto findDealerShowroom(Long dealerId)throws ServiceBizException;
	//查询经销商公司店面照片
	public PageInfoDto findCompanyPhoto(String ywzj)throws ServiceBizException;
	//查询小区
	public PageInfoDto getSmallOrg1(Map<String, String> queryParams)throws ServiceBizException;
	//经销商导入临时表  校验
	public ArrayList<DealerMaintainImportDTO> checkData();
	public void importSave();
	public PageInfoDto queryCom(Map<String, String> params);
	public List<Map> getAllOrg(String bussType);
	//查询文件表格
	public PageInfoDto doSearchCompanyPhoto7(Map<String, String> queryParam);
	public void uploadFiles(DealerDetailedinfoDTO dto);
	public void addDealerShowRoom(DealerDetailedinfoDTO dto);
	public void editDealerShowRoom(DealerDetailedinfoDTO dto);
	public void delDealerShowRoom(Long id);
	public List<Map> queryComAll(Map<String, String> params);
	public List<Map> findSeries();

	

}
