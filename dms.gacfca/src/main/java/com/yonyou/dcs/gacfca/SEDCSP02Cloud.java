package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP02DTO;

/**
 * @ClassName: SEDCSP02Cloud 
 * @Description:同步查询获取经销商可用额度(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCSP02Cloud  extends BaseCloud{
	
	public List<SEDCSP02DTO> handleExecutor(List<SEDCSP02DTO> dtos) throws Exception;
	
}
