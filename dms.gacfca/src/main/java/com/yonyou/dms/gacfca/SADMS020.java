package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS020Dto;

/**
 * @description 微信车主信息下发
 * @author Administrator
 *
 */
public interface SADMS020 {
	int getSADMS020(String dealerCode, List<SADMS020Dto> SADMS020Dtos);
}
