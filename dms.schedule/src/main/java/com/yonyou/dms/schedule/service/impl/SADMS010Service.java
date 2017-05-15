/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.schedule.domains.DTO.SADMS010Dto;

/**
 * 每周上报展厅报表（展厅周报）
 * @author Administrator
 * @date 2017年2月28日
 */

public interface SADMS010Service {
	
    public LinkedList<SADMS010Dto> getSADMS010();
}
