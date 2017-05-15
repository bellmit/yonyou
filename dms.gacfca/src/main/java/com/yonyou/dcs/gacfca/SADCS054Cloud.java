package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS054DTO;

/**
 * 克莱斯勒明检和神秘客 上报
 * 
 * @author Administrator
 *
 */
public interface SADCS054Cloud {
	public String handleExecutor(List<SADMS054DTO> dto) throws Exception;
}
