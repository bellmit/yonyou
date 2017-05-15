package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.OrderCarDTO;

/**
 * 
 * Title:SADCS061Cloud
 * Description: 订车接口  接收
 * @author DC
 * @date 2017年4月10日 下午12:08:08
 * result msg 1：成功 0：失败
 */
public interface SADCS061Cloud {
	public String handleExecutor(List<OrderCarDTO> dtoList) throws Exception;
}
