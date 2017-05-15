/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.SADCS033DTO;

/**
 * @author Administrator
 *
 */
public interface SADMS033Service {
	LinkedList<SADCS033DTO> getSADCS033() throws ServiceBizException;
	@SuppressWarnings("rawtypes")
	public List<Map> QueryEntityReport() throws SQLException;//查询经销商维度汇总的报表数据
	public Integer QueryFailReasonsNum(int reasons) throws SQLException;//查询各个失败原因数据数，通过reasons来传入失败原因
}
