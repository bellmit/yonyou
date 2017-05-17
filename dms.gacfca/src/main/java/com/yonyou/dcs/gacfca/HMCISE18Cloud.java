package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.RepairOrderReStatusDTO;

/**
 * @Description:二手车置换客户返利数据接收
 * @author xuqinqin
 */
public interface HMCISE18Cloud extends BaseCloud{
	public List<RepairOrderReStatusDTO>  handleExecutor(List<RepairOrderReStatusDTO> vos) throws Exception;
}
