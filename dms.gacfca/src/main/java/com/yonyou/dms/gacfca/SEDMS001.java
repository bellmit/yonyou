package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.TtOwnerChangeDTO;

/**
 * @description 计划任务
 * 				上传车主关键信息变更历史
 * @author Administrator
 *
 */
public interface SEDMS001 {

	LinkedList<TtOwnerChangeDTO> getSEDMS001(String dealerCode);
}
