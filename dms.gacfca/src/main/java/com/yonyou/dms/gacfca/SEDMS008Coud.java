package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.PartStockDTO;

/**
 * @description 特约店配件存库库存信息上报（计划任务)
 * @author Administrator
 *
 */
public interface SEDMS008Coud {
	public LinkedList<PartStockDTO> getSEDMS008(String dealerCode);
}
