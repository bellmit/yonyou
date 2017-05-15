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
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartReturnaddrDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartReturnaddrPO;
@Repository
public class OldPartReturnAddrDao extends OemBaseDAO{

	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findOldPartHouse(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		    sql.append("select *  from (SELECT 	 \n");
	        sql.append("  TOR.RETURNADDR_ID, 	 \n");
	        sql.append("  TOR.RETURNADDR_CODE, 	 \n");
	        sql.append("  TOR.RETURNADDR_NAME, 	 \n");
	        sql.append("  (SELECT REGION_NAME FROM TM_REGION WHERE REGION_CODE = TOR.PROVINCE_ID limit 0,1) PROVINCE_NAME, 	 \n");
	        sql.append("  (SELECT REGION_NAME FROM TM_REGION WHERE REGION_CODE = TOR.CITY_ID limit 0,1) CITY_NAME, 	 \n");
	        sql.append("  (SELECT REGION_NAME FROM TM_REGION WHERE REGION_CODE = TOR.COUNTY_ID limit 0,1) COUNTY_NAME, 	 \n");
	        sql.append("  TOR.LINK_MAN,  \n");
	        sql.append("  TOR.LINK_TEL, 	 \n");
	        sql.append("  TOR.POST_CODE, \n");
	        sql.append("  TOR.ADDRESS,  	 \n");
	        sql.append("  TOR.STATUS,TOR.CREATE_DATE 	 \n");
	        sql.append("FROM 	 \n");
	        sql.append("   TM_OLDPART_RETURNADDR_DCS TOR	 \n");
	        sql.append("WHERE 1=1 \n");
	        sql.append(" AND TOR.OEM_COMPANY_ID ="+loginInfo.getCompanyId());
			if (!StringUtils.isNullOrEmpty(queryParam.get("returnaddrCode"))) {
				sql.append("   AND TOR.RETURNADDR_CODE  like '%"+queryParam.get("returnaddrCode")+"%' ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("returnaddrName"))) {
				sql.append("   AND TOR.RETURNADDR_NAME  like '%"+queryParam.get("returnaddrName")+"%' ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
				sql.append("   AND TOR.STATUS =? ");
				params.add(queryParam.get("status"));
			}
			sql.append(" ) aa ");
		return sql.toString();
	}
	
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyoldParth(Long id, TmOldpartReturnaddrDTO tcdto) throws ServiceBizException {
		TmOldpartReturnaddrPO tc = TmOldpartReturnaddrPO.findById(id);
		setTmOldpartReturnaddr(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTmOldpartReturnaddr(TmOldpartReturnaddrPO tc, TmOldpartReturnaddrDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("returnaddr_code", tcdto.getReturnaddrCode());
		tc.setString("returnaddr_name", tcdto.getReturnaddrName());
		tc.setString("PROVINCE_ID", tcdto.getProvinceId());
		tc.setString("CITY_ID", tcdto.getCityId());
		tc.setString("COUNTY_ID", tcdto.getCountyId());
		tc.setString("LINK_MAN", tcdto.getLinkMan());
		tc.setString("LINK_TEL", tcdto.getLinkTel());
		tc.setString("POST_CODE", tcdto.getPostCode());
		tc.setString("ADDRESS", tcdto.getAddress());
		tc.setInteger("status", tcdto.getStatus());
		tc.setString("remark", tcdto.getRemark());
		tc.setDate("update_date", new Date());
		tc.setLong("update_by", loginInfo.getUserId());
	}
	/**
	 * 旧件返还地新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addnoldPart(TmOldpartReturnaddrDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select returnaddr_code from TM_OLDPART_RETURNADDR_DCS where returnaddr_code=? and returnaddr_name=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getReturnaddrCode());
		list.add(tcdto.getReturnaddrName());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("返还地号或返还地名已存在，请重新输入！");
		} else {
			TmOldpartReturnaddrPO tc = new TmOldpartReturnaddrPO();
			tc.setString("returnaddr_code", tcdto.getReturnaddrCode());
			tc.setString("returnaddr_name", tcdto.getReturnaddrName());
			tc.setString("PROVINCE_ID", tcdto.getProvinceId());
			tc.setString("CITY_ID", tcdto.getCityId());
			tc.setString("COUNTY_ID", tcdto.getCountyId());
			tc.setString("LINK_MAN", tcdto.getLinkMan());
			tc.setString("LINK_TEL", tcdto.getLinkTel());
			tc.setString("POST_CODE", tcdto.getPostCode());
			tc.setString("ADDRESS", tcdto.getAddress());
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
