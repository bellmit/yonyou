package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PutStorageDetailDTO;

/**
 * 接口名称：配件入库明细上报 接口方向：DMS -> DCS
 * 
 *
 */
public interface SADCS027Cloud {
	public String handleExecutor(List<PutStorageDetailDTO> dto) throws Exception;
}
