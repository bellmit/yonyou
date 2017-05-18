package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.WXMainDetailDTO;

/**
 * @description 修改微信首要联系方式
 * @author Administrator
 *
 */
public interface CLDMS014Coud {
	public int getCLDMS014(String dealerCode,Long userId,List<WXMainDetailDTO> voList);
}
