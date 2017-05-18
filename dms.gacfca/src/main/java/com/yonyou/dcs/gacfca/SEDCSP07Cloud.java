package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP07DTO;

/**
 * @ClassName: SEDCSP01Cloud 
 * @Description:同步查询获取配件开票接货状态(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCSP07Cloud extends BaseCloud{
	
	public List<SEDCSP07DTO> handleExecutor(List<SEDCSP07DTO> dtos) throws Exception;
	
}
