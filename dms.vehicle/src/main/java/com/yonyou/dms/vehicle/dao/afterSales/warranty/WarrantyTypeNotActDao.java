package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author zhanghongyi
 */
@Repository
public class WarrantyTypeNotActDao extends OemBaseDAO {

	/**
	 * 保修单类型查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getWarrantyTypeList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getWarrantyTypeQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	/**
	 * SQL组装保修类型
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getWarrantyTypeQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT \n");
		sql.append("TWWT.ID, \n");
		sql.append("TWWT.WT_CODE, -- 类型代码 \n");
		sql.append("TWWT.WT_NAME, -- 类型名称 \n");
		sql.append("TWWT.IS_PRESALE, -- 是否售前保修 \n");
		sql.append("TWWT.IS_ACT, -- 是否活动相关 \n");
		sql.append("TWWT.WT_TYPE, -- 保修类型 \n");
		sql.append("TWWT.STATUS, -- 状态 \n");
		sql.append("TWWT.ACT_TYPE, -- 活动类型 \n");
		sql.append("TC.NAME UPDATE_BY, -- 更新人 \n");
		sql.append("DATE_FORMAT(TWWT.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE -- 更新时间 \n");
		sql.append("FROM TT_WR_WARRANTY_TYPE_DCS TWWT \n");
		sql.append("LEFT JOIN TC_USER TC ON TC.USER_ID = TWWT.UPDATE_BY \n");
		sql.append("WHERE 1 = 1 and TWWT.IS_ACT=10041002 \n");
        //类型代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("wtCode"))){ 
        	sql.append(" 	AND TWWT.WT_CODE = '" + queryParam.get("wtCode") + "' \n");
        }
        //类型名称
        if(!StringUtils.isNullOrEmpty(queryParam.get("wtName"))){ 
        	sql.append(" 	AND TWWT.WT_NAME like '%" + queryParam.get("wtName") + "%' \n");
        }
        //是否售前保修
        if(!StringUtils.isNullOrEmpty(queryParam.get("isPresale"))){ 
        	sql.append(" 	AND TWWT.IS_PRESALE = " + queryParam.get("isPresale") + " \n");
        }
        //状态
        if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){ 
        	sql.append(" 	AND TWWT.STATUS = " + queryParam.get("status") + " \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 查询MVS
	 */
	public List<Map> getMvs(Map<String, String> queryParams,String mvsType) throws ServiceBizException {
		if(mvsType.equals(OemDictCodeConstants.WARRANTY_MVS_RANGE_01.toString())){
			StringBuffer sql  = new StringBuffer("\n");
			sql.append("select '*' MVS from dual \n");
			return OemDAOUtil.findAll(sql.toString(), null);
		}else if(mvsType.equals(OemDictCodeConstants.WARRANTY_MVS_RANGE_02.toString())){
			StringBuffer sql  = new StringBuffer("\n");
			sql.append("select substring(MVS,1,3) MVS from tm_vhcl_material_group \n");
			sql.append("where MVS is not null and MVS<>'' group by substring(MVS,1,3) \n");
			return OemDAOUtil.findAll(sql.toString(), null);
		}else if(mvsType.equals(OemDictCodeConstants.WARRANTY_MVS_RANGE_03.toString())){
			StringBuffer sql  = new StringBuffer("\n");
			sql.append("select MVS from tm_vhcl_material_group \n");
			sql.append("where MVS is not null and MVS<>'' group by MVS \n");
			return OemDAOUtil.findAll(sql.toString(), null);
		}
		else{return null;}
	}
}
