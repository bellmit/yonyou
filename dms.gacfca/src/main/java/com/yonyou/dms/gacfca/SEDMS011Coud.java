package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RepairOrderSchemeDTO;

/**
 * @description 三包状态下发
 * @author Administrator
 *
 */
public interface SEDMS011Coud {
	public int getSEDMS011(String dealerCode,List<RepairOrderSchemeDTO> contentList);
}
