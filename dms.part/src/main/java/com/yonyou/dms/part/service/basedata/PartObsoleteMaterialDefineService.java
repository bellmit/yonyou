package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialDefineDcsDTO;

/**
* 呆滞品定义service
* @author ZhaoZ
* @date 2017年4月11日
*/
public interface PartObsoleteMaterialDefineService {

	//查询呆滞品定义Lists
	public PageInfoDto queryPartObsoleteMaterialList(Map<String, String> queryParams)throws  ServiceBizException;
	//下发
	public void isSendStatus(Long id)throws  ServiceBizException;
	//呆滞品定义修改
	public void update(Long id,TtObsoleteMaterialDefineDcsDTO dto)throws  ServiceBizException;
	//呆滞品查询
	public PageInfoDto findALLList(Map<String, String> queryParams)throws  ServiceBizException;
	//呆滞品下载查询
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;

}
