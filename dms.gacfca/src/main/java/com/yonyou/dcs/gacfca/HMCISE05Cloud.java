package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ActivityResultDTO;


/**
 * @Description: TODO(活动车辆完工上报) 
 * @author xuqinqin
 */
public interface HMCISE05Cloud extends BaseCloud{
	public String handleExecutor(List<ActivityResultDTO> dtos) throws Exception;
}
