package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP08DTO;

/**
 * @ClassName: SEDCSP01Cloud 
 * @Description:获取配件订单开票接货清单(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCSP08Cloud  extends BaseCloud{
	
	public List<SEDCSP08DTO> handleExecutor(List<SEDCSP08DTO> dtos) throws Exception;
	
}
