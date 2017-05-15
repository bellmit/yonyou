package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS095Dto;

/**
 * 
 * Title:SADCS095Cloud
 * Description: 经销商零售信息变更
 * @author DC
 * @date 2017年4月10日 下午5:16:55
 * result msg 1：成功 0：失败
 */
public interface SADCS095Cloud {
	public String handleExecutor(List<SADMS095Dto> dtoList) throws Exception;

}
