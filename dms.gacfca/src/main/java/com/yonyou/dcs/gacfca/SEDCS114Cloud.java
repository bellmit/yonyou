package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.OwnerEntityDTO;

/**
 * @ClassName: SEDCS114Cloud 
 * @Description:车辆实销上报私自调拨验收(DMS->DCS->DMS)
 * @author xuqinqin 
 */
public interface SEDCS114Cloud {
	
	public List<OwnerEntityDTO> receiveData(List<OwnerEntityDTO> dtos) throws Exception;
	
}
