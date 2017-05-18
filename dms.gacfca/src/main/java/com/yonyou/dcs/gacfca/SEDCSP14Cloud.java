package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP14DTO;

/**
 * @ClassName: SEDCSP14Cloud 
 * @Description:运单收货管理回执(DMS->DCS->SAP)
 * @author xuqinqin 
 */
public interface SEDCSP14Cloud extends BaseCloud{
	
	public String handleExecutor(List<SEDCSP14DTO> dtos) throws Exception;
	
}
