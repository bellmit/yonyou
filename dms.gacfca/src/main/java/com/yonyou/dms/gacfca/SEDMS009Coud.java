package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ActivityDTO;

/**
 * @description 服务活动完成情况下发
 * @author Administrator
 *
 */
public interface SEDMS009Coud {
	public int getSEDMS009(String dealerCode,List<ActivityDTO> activityResultList);
}
