package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.ClaimRejectedDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @Title: HMCISE16Cloud
 * @Description:索赔单驳回下发
 * @author xuqinqin 
 * @date 2017年5月4日 
 */
public interface HMCISE16Cloud extends BaseCloud{

	public String sendAllData(TtWrClaimDcsDTO dto) throws ServiceBizException;
	
	public LinkedList<ClaimRejectedDTO> getDataList(TtWrClaimDcsDTO dto) throws ServiceBizException;
	
	public List<String> getEntityCodeList(TtWrClaimDcsDTO dto)throws ServiceBizException;

}
