package com.yonyou.dms.vehicle.service.complaint;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;

/**
 *  客户投诉处理Service
 * @author ZhaoZ
 * @date 2017年4月17日
 */
public interface ComplaintDisposalDealerService {
	
	//客户投诉处理(总部)查询
	public PageInfoDto complaintDisposalOem(Map<String, String> queryParams)throws ServiceBizException;
	//取得客户投诉表的基本信息
	public Map<String, Object> getComplaintById(Long compId)throws ServiceBizException;
	//根据投诉id查询车辆表信息
	public PageInfoDto getVicheilbyCompId(Long compId)throws ServiceBizException;
	//保存
	public void saveDeliveryOrder(TtCrComplaintsDcsDTO dto, Long id)throws ServiceBizException;
	//取消发布
	public void updateComplaint(TtCrComplaintsDcsDTO dto, Long id)throws ServiceBizException;
	//客户投诉处理(总部) 不需回访查询
	public PageInfoDto complaintDisposal(Map<String, String> queryParams)throws ServiceBizException;
	//客户投诉处理(总部)不需回访处理 页面初始化
	public Map<String, Object> disposalDisposal(Long compId)throws ServiceBizException;
	//客户投诉处理(总部)不需回访处理 页面初始化
	public PageInfoDto disposalDisposal1(Long compId)throws ServiceBizException;
	//客户投诉处理(总部) 不需回访查询DLR
	public PageInfoDto complaintDisposalList(Map<String, String> queryParams)throws ServiceBizException;
	//客户投诉查询下载
	public List<Map> excelExportList(Map<String, String> queryParams)throws ServiceBizException;

}
