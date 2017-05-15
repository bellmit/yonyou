package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dcs.dao.SADCS033DTO;

/**
 * 车主信息核实固化月报表
 * 
 * @author lianxinglu
 *
 */
public interface SADCS033Cloud {
	public String handleExecutor(List<SADCS033DTO> dto) throws Exception;
}
