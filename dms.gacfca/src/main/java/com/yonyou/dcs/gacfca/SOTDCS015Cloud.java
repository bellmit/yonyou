package com.yonyou.dcs.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;

public interface SOTDCS015Cloud extends BaseCloud{

	String handleExecutor(LinkedList<TiDmsUCustomerStatusDto> dtoList) throws Exception;

}
