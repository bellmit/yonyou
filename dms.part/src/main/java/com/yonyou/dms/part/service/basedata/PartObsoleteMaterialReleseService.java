package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialApplyDcsDTO;

/**
* 呆滞品发布信息查询service
* @author ZhaoZ
* @date 2017年4月11日
*/
public interface PartObsoleteMaterialReleseService {

	//呆滞品发布信息查询
	public PageInfoDto queryPartObsoleteMaterialList(Map<String, String> queryParams)throws  ServiceBizException;

	//呆滞品发布信息下载查询
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	//查询指定编号的呆滞品
	public Map<String, Object> queryReleaseById(Long id)throws  ServiceBizException;
	//呆滞品申请
	public void insertApplyRelease(TtObsoleteMaterialApplyDcsDTO dto)throws  ServiceBizException;

}
