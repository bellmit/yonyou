package com.yonyou.dms.retail.service.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.dao.basedata.BankingDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;

@Service
@SuppressWarnings("rawtypes")
public class BankingServiceImpl implements BankingService{

	@Autowired
	BankingDao dao;

	@Override
	public PageInfoDto findBanking(LoginInfoDto loginInfo,Map<String, String> queryParam) throws ServiceBizException {
		
		return dao.findBanking(loginInfo,queryParam);
	}
	@Override
	public List<Map> queryEmpInfoforExport(LoginInfoDto loginInfo, Map<String, String> queryParam) throws Exception {
	
		return dao.queryEmpInfoforExport(loginInfo, queryParam);
	}
	@Override
	public void modifyBanking(TmRetailDiscountOfferDTO tcDto,LoginInfoDto loginInfo) throws Exception {
		dao.modifyBanking(tcDto,loginInfo);
		
	}
	
	@Override
	public Map<String, Object> findById(Long reportId, String vin, LoginInfoDto loginInfo) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map = dao.findById(vin,loginInfo);
		map.put("REPORT_ID", reportId);
		map.put("ORG_ID", loginInfo.getOrgId());
		return map;
	}
	@Override
	public List<Map> selectBankName() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ID,BANK_NAME FROM TC_BANK WHERE STATUS = 10011001");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		return resultList;
	}
}
