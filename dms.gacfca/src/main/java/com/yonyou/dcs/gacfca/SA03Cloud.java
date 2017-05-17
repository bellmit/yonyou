package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;

/**
 * @Description: TODO(调拨入库数据上传) 
 * @author xuqinqin
 */
public interface SA03Cloud extends BaseCloud{
	public String handleExecutor(List<VsStockEntryItemDto> vos) throws Exception;
}
