package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * Title:SaDcs056Dao
 * Description: 试乘试驾分析数据上报
 * @author DC
 * @date 2017年4月7日 上午10:27:57
 */
@Repository
public class SaDcs056Dao extends OemBaseDAO{
	
	/**
     * 查询车系
     * @param seriesCode
     * @return
     */
	@SuppressWarnings("rawtypes")
    public List<Map> getSeriesCode(String seriesCode){ 
    	StringBuffer sql = new StringBuffer("");
    	sql.append(" SELECT TVMG.GROUP_CODE FROM TM_VHCL_MATERIAL_GROUP TVMG \n");
    	sql.append("		WHERE TVMG.GROUP_LEVEL = 2 AND TVMG.STATUS='10011001' \n");
    	if(!StringUtils.isNullOrEmpty(seriesCode)){
			sql.append(" 		AND TVMG.GROUP_CODE LIKE '%"+ seriesCode +"%'\n");
		}
	    return OemDAOUtil.findAll(sql.toString(), null);
    }  
}
