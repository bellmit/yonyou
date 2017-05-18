package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SADMS052DTO;

/**
 * @description 吸收率报表上报
 * @author Administrator
 *
 */
public interface SADMS052Coud {
	public LinkedList<SADMS052DTO> getSADMS052(String dealerCode,String year,String month);
}
