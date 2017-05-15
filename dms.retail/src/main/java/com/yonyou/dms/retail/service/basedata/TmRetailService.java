package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;

public interface TmRetailService {
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto getTmRetaillist(Map<String, String> queryParam) throws Exception ;
	
	/**
	 * 下载
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception;

	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findById(Long id)throws Exception;

	/**
	 * 修改
	 * @param tcDto
	 * @param loginInfo
	 * @throws Exception
	 */
	public void modifyBanking(TmRetailDiscountOfferDTO tcDto, LoginInfoDto loginInfo)throws Exception;

}
