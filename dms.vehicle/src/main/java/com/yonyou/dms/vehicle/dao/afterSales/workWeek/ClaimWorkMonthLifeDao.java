package com.yonyou.dms.vehicle.dao.afterSales.workWeek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

/**
 * 索赔工作越维护
 * @author Administrator
 *
 */
@Repository
public class ClaimWorkMonthLifeDao extends OemBaseDAO{

	//查询临时表的所有数据
	public List<Map> findclaimWorkMonthLife() {
		//获取当前用户
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				List<Object> params = new ArrayList<Object>();
				StringBuffer sql = new StringBuffer("\n");
				sql.append(" SELECT * FROM  TMP_WR_CLAIMMONTH_dcs   \n");
				sql.append(" WHERE  CREATE_BY =  "+loginInfo.getUserId()+"  \n");
				sql.append(" order by ID  \n");
				List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
				return resultList;
	}


}
