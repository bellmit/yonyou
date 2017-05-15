/**
 * 
 */
package com.yonyou.dms.vehicle.service.insuranceBusiness;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.InsProposalDTO;
import com.yonyou.dms.common.domains.DTO.customer.ProposalTrackDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author sqh
 *
 */
public interface InsProposalService {

	public PageInfoDto queryInsProposal(Map<String,String> queryParam) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryInsCompany(Map<String, String> queryParam) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> queryInsBroker(Map<String, String> queryParam) throws ServiceBizException;
	
	@SuppressWarnings("rawtypes")
	public List<Map> exportInsProposal(Map<String,String> queryParam) throws ServiceBizException;
	
	public PageInfoDto queryTrackHistory(Map<String,String> queryParam) throws ServiceBizException;
	
	/**
	 * 新增跟踪记录
	 * 
	 * @param id
	 * @param ttcampaignPlanDTO
	 */
	public void addProposalTrack(ProposalTrackDTO proposalTrackDTO) throws ServiceBizException;
	
	public void update(InsProposalDTO insProposalDTO) throws ServiceBizException;
	
	public void updateStatus(InsProposalDTO insProposalDTO) throws ServiceBizException;
	
	public PageInfoDto queryOwner(Map<String,String> queryParam) throws ServiceBizException;
	
//	public List<Map> queryByProposalCode(@PathVariable(value = "proposalCode") String proposalCode) throws ServiceBizException;
}
