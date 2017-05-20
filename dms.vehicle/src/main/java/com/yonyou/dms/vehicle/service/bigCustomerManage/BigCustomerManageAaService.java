package com.yonyou.dms.vehicle.service.bigCustomerManage;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerAuthorityApprovalDTO;
import com.yonyou.dms.vehicle.domains.DTO.bigCustomer.TtBigCustomerReportApprovalDTO;

/**
 * 大客户管理
 * @author ZhaoZ
 * @date 2017年3月10日
 */
@SuppressWarnings("rawtypes")
public interface BigCustomerManageAaService {

	//大客户根据状态查询
	public PageInfoDto QueryCustomerByStatus(Map<String, String> queryParams, int flag)throws ServiceBizException;
	//大客户报备审批信息下载
	public List<Map> dlrFilingInfoExport(Map<String, String> queryParams,int flag)throws ServiceBizException;
	//大客户报备审批明细信息下载
	public List<Map> dlrFilingInfoDetailExport(Map<String, String> queryParams, int flag)throws ServiceBizException;
	//大客户申购车型查询
	public PageInfoDto getCustomerBatchVhclInfoS(String wsno,TtBigCustomerReportApprovalPO tbcraPO)throws ServiceBizException;
	//功能描述：查询大客户返利审批信息
	public PageInfoDto QueryapplyforQuery(Map<String, String> queryParams, int type)throws ServiceBizException;
	//大客户申请审批信息下载
	public List<Map> dlrRebateApprovalInfoExport(Map<String, String> queryParams, int flag)throws ServiceBizException;
	//大客户申请明细下载
	public List<Map> reBateApprovalInfoDownLoadDetail(Map<String, String> queryParams)throws ServiceBizException;
	
	public Map<String, Object> QueryCustomerByStatus(String wsno, int flag)throws ServiceBizException;
	//查询客户相关信息
	public Map<String, Object> getCustomerInfo(String customerCode, String dealerCode, String wsno)throws ServiceBizException;
	//查询审批信息
	public PageInfoDto getApprovalHisInfos(String wsno, String customerCode)throws ServiceBizException;
	//查询审批信息
	public PageInfoDto getApprovalHisInfos1(String wsno, String customerCode)throws ServiceBizException;
	//保存大客户报备审批信息
	public void saveApprovalInfos(TtBigCustomerReportApprovalDTO dto,String wsno)throws ServiceBizException;
	//保存大客户申请审批信息(未审批)
    public void saveApprovalInfos1(TtBigCustomerReportApprovalDTO dto,String wsno)throws ServiceBizException;
    //保存大客户申请审批信息(资料完整待签字)
    public void saveApprovalInfos2(TtBigCustomerReportApprovalDTO dto,String wsno)throws ServiceBizException;
	//大客户激活
	public void activationApprovalInfos(TtBigCustomerReportApprovalDTO dto, String wsno)throws ServiceBizException;
	//查询大客户的信息
	public PageInfoDto queryBigCustomerPolicy(Map<String, String> queryParams,LoginInfoDto loginUser)throws ServiceBizException;
	//查询大客户组织架构权限审批信息
	public PageInfoDto queryBigCustomerAuthorityApproval(Map<String, String> queryParams)throws ServiceBizException;
	//大客户组织架构权限审批页面
	public Map<String, Object> approvalDetailPre(BigDecimal id)throws ServiceBizException;
	//审核历史
	public PageInfoDto approvalHis(BigDecimal id)throws ServiceBizException;
	//审批更改
	public void saveApprovalInfo(BigDecimal id,TtBigCustomerAuthorityApprovalDTO dto)throws ServiceBizException;
	
	public int downBigCustomerPolicy(BigDecimal policyFileId) throws ServiceBizException;
	
	//附件上传
	public int uploadFiles(String dmsFileIds) throws ServiceBizException;
	//大客户报备申请显示信息
	public Map<String, Object> QueryCustomerByStatus1(String wsno, int flag);
	//大客户政策删除
	public void delete(BigDecimal id) throws ServiceBizException;
	

	
}
