package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.MaintainActivityDTO;

/**
 * @description OEM下发优惠模式
 * @author Administrator
 */
public interface CLDMS008Coud {
	public int getCLDMS008(LinkedList<MaintainActivityDTO> voList,String dealerCode);
}
