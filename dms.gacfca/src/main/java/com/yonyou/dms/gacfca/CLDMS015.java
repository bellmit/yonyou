package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.WXUnbundingDTO;

/**
 * @description 微信解绑
 * @author Administrator
 *
 */
public interface CLDMS015 {
	public int getCLDMS015(String dealerCode,Long userId,List<WXUnbundingDTO> voList);
}
