package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* 配件调拨单查询service
* @author ZhaoZ
* @date 2017年3月30日
*/
public interface PartAllotAaService {

	//配件调拨单查询
	public PageInfoDto queryAllot(Map<String, String> queryParams)throws  ServiceBizException;
	//配件调拨单查询
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	//配件调拨单查询明细
	public PageInfoDto queryAllotOutDeInfo(BigDecimal id)throws  ServiceBizException;
	//配件调拨单明细查询
	public PageInfoDto partAllotDetailInfo(Map<String, String> queryParams)throws  ServiceBizException;
	//配件调拨单明细下载查询
	public List<Map> querydownAllotDetailInfo(Map<String, String> queryParams)throws  ServiceBizException;
	
}
