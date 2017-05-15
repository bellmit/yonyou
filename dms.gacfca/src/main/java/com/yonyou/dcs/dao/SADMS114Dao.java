package com.yonyou.dcs.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Repository
public class SADMS114Dao extends OemBaseDAO {

	public LinkedList<PayingBankDTO> queryPolicyApplyDateInfo(Long bankId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    TC.ID,    \n");
		sql.append("    TC.BANK_NAME,    \n");
		sql.append("    TC.STATUS,    \n");
		sql.append("    TC.UPDATE_STATUS,    \n");
		sql.append("    TC.BTC_CODE    \n");
		sql.append("FROM TC_BANK TC    \n");
		sql.append("WHERE TC.ID="+ bankId +"   \n");
		sql.append("and (TC.IS_SEND = 0 or TC.IS_SEND IS null)   \n");
		LinkedList<PayingBankDTO> vos = wrapperVO(OemDAOUtil.findAll(sql.toString(), null));
		return vos;
		
	}
	@SuppressWarnings("rawtypes")
	private LinkedList<PayingBankDTO> wrapperVO(List<Map> rs) {
		LinkedList<PayingBankDTO> resultList = new LinkedList<PayingBankDTO>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					PayingBankDTO dto = new PayingBankDTO();
					dto.setBankCode(Integer.valueOf(rs.get(i).get("BTC_CODE").toString()));
					dto.setUpdateStatus(Integer.valueOf(rs.get(i).get("UPDATE_STATUS").toString()));
					dto.setBankName(rs.get(i).get("BANK_NAME").toString());
					dto.setStatus(Integer.valueOf(rs.get(i).get("STATUS").toString()));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}

}
