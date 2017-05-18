package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;


/**
 * @description 下发配件货运单信息
 * @author Administrator
 *
 */
public interface SEDMS006Coud {
	public int getSEDMS006(String dealerCode,LinkedList<PtDlyInfoDto> ptDlyInfoList);
}
