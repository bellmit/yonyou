package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP03DTO;

/**
 * @Description:配件订货上报接口
 * @author xuqinqin 
 */
public interface SEDCSP03Cloud  extends BaseCloud{
	
	public String handleExecutor(List<SEDCSP03DTO> dtos) throws Exception;
	
}
