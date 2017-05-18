package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMCS026DTO;

/**
 * @description  计划任务统计配件入库来源监控报表并上报总部
 * @author Administrator
 *
 */
public interface SADMS026Coud {
	public List<SADMCS026DTO> getSADMS026(String entityCode);
}
