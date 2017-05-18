package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.CLDMS005Dto;
import com.yonyou.dms.DTO.gacfca.RoItemListDTO;

/**
 * @description 工时数据下发
 * @author Administrator
 */
public interface CLDMS006Coud {
	public int getCLDMS006(LinkedList<RoItemListDTO> voList,String dealerCode);
}
