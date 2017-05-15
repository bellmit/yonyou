package com.yonyou.dcs.dao;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Repository
public class MySteriousCustomerReportDao extends OemBaseDAO {

	public LinkedList<SEDMS054DTO> querySADMS054DTO() {
		StringBuffer sql = new StringBuffer();
		sql.append("select TMDD.DEALER_CODE,   -- 经销code \n");
		sql.append("       TMDD.DEALER_NAME,   -- 经销简称 \n");
		sql.append("       TMDD.EXEC_AUTHOR,   -- 执行人员 \n");
		sql.append("       TMDD.PHONE          -- 留店电话 \n");
		sql.append("FROM  \n");
		sql.append("  TT_MYSTERIOUS_DATE TMD \n");
		sql.append(" INNER JOIN \n");
		sql.append("  TT_MYSTERIOUS_DATE_DOWN TMDD \n");
		sql.append(" ON \n");
		sql.append(" TMD.MYSTERIOUS_ID = TMDD.MYSTERIOUS_ID \n");
		sql.append("WHERE \n");
		sql.append(" TMD.IS_DOWN = 0 OR TMD.IS_DOWN IS NULL \n");
		LinkedList<SEDMS054DTO> list = wrapperVO(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}

	@SuppressWarnings("rawtypes")
	protected LinkedList<SEDMS054DTO> wrapperVO(List<Map> rs) {
		LinkedList<SEDMS054DTO> resultList = new LinkedList<SEDMS054DTO>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					SEDMS054DTO dto = new SEDMS054DTO();
					dto.setEntityCode(rs.get(i).get("DEALER_CODE").toString());
					dto.setDealerName(rs.get(i).get("DEALER_NAME").toString());
					dto.setExecAuthor(rs.get(i).get("EXEC_AUTHOR").toString());
					dto.setPhone(rs.get(i).get("PHONE").toString());
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}

}
