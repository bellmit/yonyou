package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.WXBindingRsgDTO;

/**
 * @description 接收微信绑定信息
 * @author Administrator
 *
 */
public interface CLDMS013Coud {
	public int getCLDMS013(String dealerCode,Long userId,List<WXBindingRsgDTO> voList);
}
