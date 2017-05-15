package com.yonyou.dms.retail.dao.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TcBankPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TcBankDTO;
/**
 * 合作银行Dao
 * @author Administrator
 *
 */
@Repository
public class TcBankDao extends OemBaseDAO {
	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getBanklist(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT TB.ID,TB.BANK_NAME,TB.STATUS,TB.UPDATE_STATUS,CASE WHEN TB.IS_SEND=0 THEN '未下发' ELSE'已下发' END IS_SEND,DATE_FORMAT(TB.SEND_DATE,'%Y-%m-%d %H:%i:%s') SEND_DATE,TC.NAME FROM TC_BANK TB");
		sql.append("  left join TC_USER TC on TC.USER_ID = TB.SEND_BY \n");
		sql.append("  WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			sql.append(" and TB.STATUS = ?");
			params.add(queryParam.get("status"));
		}
		return sql.toString();
	}

	/**
	 * 新增合作银行信息
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addTcBank(TcBankDTO tcbdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer("select bank_name from tc_bank where 1=1 and bank_name=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcbdto.getBankName());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("银行名称不能重复！");
		} else {
			TcBankPO lap = new TcBankPO();
			lap.setString("BANK_NAME", tcbdto.getBankName());
			lap.setInteger("status", tcbdto.getStatus());
			lap.saveIt();
			return (Long) lap.getLongId();
		}
	}

	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyTcBank(Long id, TcBankDTO tcdto) throws ServiceBizException {
		TcBankPO tc = TcBankPO.findById(id);
		setTcBankPO(tc, tcdto);
		tc.saveIt();
	}

	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTcBankPO(TcBankPO tc, TcBankDTO tcdto) {
		tc.setString("bank_name", tcdto.getBankName());
		tc.setString("status", tcdto.getStatus());

	}

	/**
	 * 下发方法
	 */
	public void doSendEach(Long id, TcBankDTO tcdto) {

	}
}
