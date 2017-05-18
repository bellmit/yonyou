package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SADMS021Dto;

/**
 * @description 一对一客户经理绑定修改下发
 * @author Administrator
 *
 */
public interface SADMS021Coud {
	int getSADMS021(String dealerCode,LinkedList<SADMS021Dto> SADMS020Dtos);
}
