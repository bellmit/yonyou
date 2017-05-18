package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.TmDiscounDTO;

/**
 * @description OEM下发优惠模式
 * @author Administrator
 */
public interface CLDMS007Coud {
	public int getCLDMS007(LinkedList<TmDiscounDTO> voList,String dealerCode);
}
