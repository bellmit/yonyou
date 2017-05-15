package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDCSP01DTO;

/**
 * @ClassName: SEDCSP01Cloud 
 * @Description:同步查询获取配件清单信息(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCSP01Cloud {
	
	public List<SEDCSP01DTO> receiveData(List<SEDCSP01DTO> dtos) throws Exception;
	
}
