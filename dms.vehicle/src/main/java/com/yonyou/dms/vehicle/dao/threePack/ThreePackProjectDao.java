package com.yonyou.dms.vehicle.dao.threePack;

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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemDTO;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackItemPO;
/**
 * 三包项目设定dao
 * @author zhoushijie
 *
 */
@Repository
public class ThreePackProjectDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findItem(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ID, \n");
		sql.append("       ITEM_NO,  \n");
		sql.append("       ITEM_NAME,  \n");
		sql.append("       ITEM_REMARK,  \n");
		sql.append("       CREATE_BY,  \n");
		sql.append("       CREATE_DATE, \n");
		sql.append("       UPDATE_BY, \n");
		sql.append("       UPDATE_DATE,  \n");
		sql.append("       VER, \n");
		sql.append("       IS_DEL, \n");
		sql.append("       IS_ARC \n");
		sql.append("  FROM TT_THREEPACK_ITEM_DCS \n");
		sql.append(" WHERE IS_DEL = " + OemDictCodeConstants.IS_DEL_00 + " \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemNo"))) {
			sql.append("   AND ITEM_NO = ? ");
			params.add(queryParam.get("itemNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("itemName"))) {
			sql.append(" and ITEM_NAME = ? ");
			params.add(queryParam.get("itemName"));
		}
		return sql.toString();
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMinclass(Long id, TtThreepackItemDTO tcdto) throws ServiceBizException {
		TtThreepackItemPO tc = TtThreepackItemPO.findById(id);
		setTtThreepackItem(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTtThreepackItem(TtThreepackItemPO tc, TtThreepackItemDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("item_remark", tcdto.getItemRemark());
		tc.setDate("update_date", new Date());
		tc.setString("update_by", loginInfo.getUserId());

	}
}
