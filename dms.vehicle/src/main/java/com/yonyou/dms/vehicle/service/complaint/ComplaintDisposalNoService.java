package com.yonyou.dms.vehicle.service.complaint;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtVisitJdpowerDTO;

/**
 *  客户投诉处理Service
 * @author ZhaoZ
 * @date 2017年4月17日
 */
public interface ComplaintDisposalNoService {

	//客户投诉处理(总部) 不需回访查询
	public PageInfoDto complaintDisposalNoList(Map<String, String> queryParams)throws ServiceBizException;
	//审核通过
	public void dealAgree(TtCrComplaintsDcsDTO dto, Long compId)throws ServiceBizException;
	//审核驳回
	public void dealNotAgree(TtCrComplaintsDcsDTO dto, Long compId)throws ServiceBizException;
	//查询临时表
	public void insertTmpVisitJdpowerDcs(TtVisitJdpowerDTO rowDto)throws ServiceBizException;
	//数据校验
	public List<TtVisitJdpowerDTO> checkData()throws ServiceBizException;
	//回访结果查询
	public PageInfoDto returnVisitResultList(Map<String, String> queryParams)throws ServiceBizException;
	//经销商端回访任务处理/回访结果下载
	public List<Map> excelExportList(Map<String, String> queryParams,String dealerCode)throws ServiceBizException;
	//经销商端回访任务处理查询
	public PageInfoDto returnVisitDealerList(Map<String, String> queryParams)throws ServiceBizException;
	//处理结果保存
	public void save(TtVisitJdpowerDTO dto, String type, Long visitId)throws ServiceBizException;

}
