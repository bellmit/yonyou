package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.ActivityDTO;

/**
 * @description 下发服务活动
 * @author Administrator
 *
 */
public interface SEDMS005 {
	int getSEDMS005(List<ActivityDTO> list,String dealerCode);
}
