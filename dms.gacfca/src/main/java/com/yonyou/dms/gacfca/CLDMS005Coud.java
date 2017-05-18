package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.CLDMS005Dto;

/**
 * @description 配件基本信息下发
 * @author Administrator
 */
public interface CLDMS005Coud {
	public int getCLDMS005(LinkedList<CLDMS005Dto> voList,String dealerCode);
}
