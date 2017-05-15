package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;

@SuppressWarnings("rawtypes")
public interface BankingService {

	public PageInfoDto findBanking(LoginInfoDto loginInfo, Map<String, String> queryParam) throws ServiceBizException;/// 查询

	List<Map> queryEmpInfoforExport(LoginInfoDto loginInfo, Map<String, String> queryParam) throws Exception;

	public void modifyBanking(TmRetailDiscountOfferDTO tcDto, LoginInfoDto loginInfo) throws Exception;

	public Map<String, Object> findById(Long reportId, String vin, LoginInfoDto loginInfo) throws Exception;

	public List<Map> selectBankName();

}
