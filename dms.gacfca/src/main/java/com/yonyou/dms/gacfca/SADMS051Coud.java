package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SADMS051DTO;

/**
 * @description  经营月报表上报
 * @author Administrator
 *
 */
public interface SADMS051Coud {
	LinkedList<SADMS051DTO> getSADMS051(String dealerCode,String year,String month);
}
