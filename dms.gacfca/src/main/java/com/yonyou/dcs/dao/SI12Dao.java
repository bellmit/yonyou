package com.yonyou.dcs.dao;



import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;


@Repository
public class SI12Dao extends OemBaseDAO {
	private static Logger logger = LoggerFactory.getLogger(SI12Dao.class);

	/**
	 * 功能说明:将对方的ID转换成DMS的ID
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-01
	 * @return
	 */
	public String getRelationIdInfo(String id,String type) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tcr.CODE_ID \n");
		sql.append("    FROM TC_RELATION tcr, tc_code_dcs tcc \n");
		sql.append("   WHERE tcr.CODE_ID = tcc.CODE_ID AND tcc.TYPE = "+type+"\n");
		sql.append("     and tcr.RELATION_CODE = "+id+"\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),null);
		if(list.size()>0) {
			return CommonUtils.checkNull(list.get(0).get("CODE_ID"));
		}else{
			return null;
		}
	}
	
	/**
	 * 功能说明:根据给定treeCode查GroupId
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-19
	 * @return
	 */
	public Long getGroupId(String treeCode) {
		Long groupId = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT GROUP_ID \n");
		sql.append("    FROM TM_VHCL_MATERIAL_GROUP \n");
		sql.append("   WHERE tree_code = '"+treeCode+"' \n"); 
		List<Map> list = OemDAOUtil.findAll(sql.toString(),null);
		if(list.size()>0) {
			return (Long) list.get(0).get("GROUP_ID");
		}else{
			return null;
		}
		
	}
	
	/**
	 * 功能说明:将LMS的地址转换成本地地址
	 * 创建人: zhangRM
	 * 创建日期: 2013-07-19
	 * @return
	 */
	public Long getLocalId(String lmsId) {
		String regionCode = null;
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  select region_code from TM_REGION_dcs where LMS_ID = "+lmsId+" \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(),null);
		if(list.size()>0) {
			return (Long) list.get(0).get("region_code");
		}else{
			return null;
		}
		
	}
	
	/**
	 * 功能说明:将LMS的经销商代码转换成我们的经销商代码
	 * 创建人: zhangRM
	 * 创建日期: 2013-07-19
	 * @return
	 */
	public String getLocalDealerCode(String lmsDealerCode) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tmd.DEALER_CODE \n");
		sql.append("    FROM TM_COMPANY TMC,TM_DEALER TMD \n");
		sql.append("   WHERE TMC.COMPANY_ID = TMD.COMPANY_ID \n");
		sql.append("     AND TMD.DEALER_TYPE = 10771001 \n");
		sql.append("     AND TMC.LMS_CODE ='"+lmsDealerCode+"' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0) {
			return (String) list.get(0).get("DEALER_CODE");
		}else{
			return null;
		}
		
	}
}
