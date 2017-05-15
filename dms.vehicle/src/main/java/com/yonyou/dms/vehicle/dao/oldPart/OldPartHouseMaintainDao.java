package com.yonyou.dms.vehicle.dao.oldPart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartStorDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartStorPO;
@Repository
public class OldPartHouseMaintainDao extends OemBaseDAO{

	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findOldPartHouse(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		pasql.append(" SELECT  TC.NAME, \n");
		pasql.append(" T.STOR_ID, T.STOR_CODE, T.STOR_NAME, T.STATUS,T.STOR_ADDRESS, \n");
		pasql.append(" T.REMARK,T.CREATE_BY, DATE_FORMAT(T.CREATE_DATE,'%Y-%m-%d') CREATE_DATE \n");
		pasql.append(" FROM TM_OLDPART_STOR_DCS T, TC_USER TC \n");
		pasql.append(" WHERE 1=1 AND TC.USER_ID = T.CREATE_BY \n");
		pasql.append(" AND T.OEM_COMPANY_ID="+loginInfo.getCompanyId()+" \n");

		if (!StringUtils.isNullOrEmpty(queryParam.get("storCode"))) {
			pasql.append("   AND T.STOR_CODE  like (?) ");
			params.add("%"+queryParam.get("storCode")+"%");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("storName"))) {
			pasql.append("   AND T.STOR_NAME  like(?) ");
			params.add("%"+queryParam.get("storName")+"%");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			pasql.append("   AND T.STATUS =? ");
			params.add(queryParam.get("status"));
		}
		System.out.println(pasql.toString());
		return pasql.toString();
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyoldParth(Long id, TmOldpartStorDTO tcdto) throws ServiceBizException {
		TmOldpartStorPO tc = TmOldpartStorPO.findById(id);
		setTmOldpartStor(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTmOldpartStor(TmOldpartStorPO tc, TmOldpartStorDTO tcdto) {
		StringBuffer sb = new StringBuffer(
				"select stor_code from TM_OLDPART_STOR_DCS where stor_code=? ");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getStorCode());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 1) {
			throw new ServiceBizException("仓库号已存在，请重新输入！");
		} 
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("stor_code", tcdto.getStorCode());
		tc.setString("stor_name", tcdto.getStorName());
		tc.setString("stor_address", tcdto.getStorAddress());
		tc.setInteger("status", tcdto.getStatus());
		tc.setString("remark", tcdto.getRemark());
		tc.setDate("update_date", new Date());
		tc.setLong("update_by", loginInfo.getUserId());
		
	}
	/**
	 * 旧件仓库新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addnoldPart(TmOldpartStorDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select stor_code from TM_OLDPART_STOR_DCS where stor_code=? or stor_name=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getStorCode());
		list.add(tcdto.getStorName());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("仓库号或仓库名已存在，请重新输入！");
		} else {
			TmOldpartStorPO tc = new TmOldpartStorPO();
			tc.setString("stor_code", tcdto.getStorCode());
			tc.setString("stor_name", tcdto.getStorName());
			tc.setString("stor_address", tcdto.getStorAddress());
			tc.setInteger("status", tcdto.getStatus());
			tc.setString("remark", tcdto.getRemark());
			tc.setDate("create_date", new Date());
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tc.setLong("create_by", loginInfo.getUserId());
			tc.setInteger("ver", 0);
			tc.setInteger("is_del", 0);
			tc.setInteger("is_arc", 0);
			tc.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
			tc.saveIt();
			return (Long) tc.getLongId();
		}
	}
}
