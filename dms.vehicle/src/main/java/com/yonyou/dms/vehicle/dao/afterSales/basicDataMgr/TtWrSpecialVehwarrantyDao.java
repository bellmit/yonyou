package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 特殊车辆质保期维护
 * @author Administrator
 *
 */
@Repository
public class TtWrSpecialVehwarrantyDao extends OemBaseDAO{
	/**
	 * 特殊车辆质保期维护查询
	 */
	public PageInfoDto  SpecialVehwarrantyQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("	 select twcs.ID,twcs.VIN,twcs.QUALITY_TIME,twcs.QUALITY_MILEAGE,twcs.CREATE_BY  \n");
		sql.append("     ,twcs.CREATE_DATE,twcs.UPDATE_BY,twcs.UPDATE_DATE,twcs.VER,twcs.IS_DEL\n");
		sql.append("     from TT_WR_SPECIAL_VEHWARRANTY_dcs twcs \n");
		sql.append("     where  twcs.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("AND  twcs.VIN like '%"+queryParam.get("vin")+"%'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
