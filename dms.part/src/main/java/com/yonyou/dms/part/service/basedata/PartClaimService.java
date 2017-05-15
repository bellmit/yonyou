package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtptClaimDcsDTO;

/**
* 配件索赔审核service
* @author ZhaoZ
* @date 2017年3月28日
*/
public interface PartClaimService {
	
	//到货索赔审核查询
	public PageInfoDto checkClaim(Map<String, String> queryParams)throws  ServiceBizException;
	//索赔基本信息
	public Map<String, Object> checkDetail(BigDecimal id)throws  ServiceBizException;
	//索赔订单详细信息
	public Map<String, Object> findClaimDetailInfoByClaimId(BigDecimal claimId)throws  ServiceBizException;
	//审核历史
	public PageInfoDto findCheckInfoByClaimId(BigDecimal claimId)throws  ServiceBizException;
	//补发交货单录入
	public void saveReissueTransNo(TtptClaimDcsDTO dto, BigDecimal id)throws  ServiceBizException;
	// 同意   拒绝  驳回
	public void checkAgree(TtptClaimDcsDTO dto, BigDecimal id, String type)throws  ServiceBizException;
	//到货索赔查询
	public PageInfoDto queryClaim(Map<String, String> queryParams)throws  ServiceBizException;
	//导出
	public List<Map> queryDownLoad(Map<String, String> queryParams)throws  ServiceBizException;
	
	public Map<String, Object> findClaimInfoByClaimId(BigDecimal id)throws  ServiceBizException;

}
