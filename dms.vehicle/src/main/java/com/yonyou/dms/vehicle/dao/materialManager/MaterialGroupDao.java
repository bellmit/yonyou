package com.yonyou.dms.vehicle.dao.materialManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialGroupDTO;

/**
 * 物料组维护DAO
 * @author 夏威
 * 2017-1-16
 */

@Repository
public class MaterialGroupDao extends OemBaseDAO {
	
	
	/**
	 * 查询方法
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	
	/**
	 * SQL组装
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT t1.GROUP_ID,t1.GROUP_CODE,t1.GROUP_NAME,t1.GROUP_LEVEL, t1.IF_FOREC, t1.IS_EC,t1.STATUS,t1.GROUP_ID PT_GROUP_ID,  \n");
		sql.append(" t1.GROUP_CODE PT_GROUP_CODE,IFNULL(t1.WX_ENGINE,'') AS WX_ENGINE,t1.OILE_TYPE,IFNULL(t1.OTHER_OPTION,'') AS OTHER_OPTION,IFNULL(t1.STANDARD_OPTION,'') AS STANDARD_OPTION,IFNULL(t1.MODEL_YEAR,'') AS MODEL_YEAR   \n");
		sql.append(" FROM tm_vhcl_material_group t1  \n");
		sql.append(" LEFT JOIN  tm_vhcl_material_group t2  ON t1.PARENT_GROUP_ID = t2.GROUP_ID \n");
		sql.append(" WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
			sql.append(" and t1.GROUP_CODE like ?");
			params.add("%"+queryParam.get("groupCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and t1.GROUP_NAME like ?");
			params.add("%"+queryParam.get("groupName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and t1.STATUS = ?");
			params.add(queryParam.get("status"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("parentGroupCode"))){
			sql.append(" and t2.GROUP_CODE = ?");
			params.add(queryParam.get("parentGroupCode"));
		}
		return sql.toString();
	}

	/**
	 * 下载查询
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryMaterialGroupForExport(Map<String, String> queryParam) {
		 List<Object> params = new ArrayList<>();
		 String sql = getQuerySql(queryParam,params);
		 sql += " ORDER BY t1.GROUP_LEVEL, t1.GROUP_CODE \n";
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql,params);
	     return resultList;
	}
	
	
	/**
	 * 下载明细查询
	 * @param queryParam
	 * @return
	 */
	public List<Map> queryMaterialGroupDetailForExport(Map<String, String> queryParam) {
		 List<Object> params = new ArrayList<>();
		 StringBuilder sql = new StringBuilder();
	     sql.append(" SELECT t1.GROUP_CODE BRAND_CODE,t1.GROUP_NAME BRAND_NAME,t2.GROUP_CODE SERIES_CODE,t2.GROUP_NAME SERIES_NAME,t2.GROUP_TYPE, \n");
		 sql.append(" t3.GROUP_CODE MODEL_CODE,t3.GROUP_NAME MODEL_NAME,CASE t3.IF_FOREC WHEN '10041001' THEN '是' WHEN '10041002' THEN '否' ELSE '' END AS IF_FOREC,t4.GROUP_CODE,t4.GROUP_NAME,t4.MODEL_YEAR,t4.FACTORY_OPTIONS, \n");
		 sql.append(" t4.STANDARD_OPTION,t4.LOCAL_OPTION,CASE t4.IS_EC WHEN '10041001' THEN '是' WHEN '10041002' THEN '否' ELSE '' END AS IS_EC,t4.STATUS \n");
		 sql.append(" FROM tm_vhcl_material_group t1 , tm_vhcl_material_group t2 , tm_vhcl_material_group t3 , tm_vhcl_material_group t4 \n");
		 sql.append(" WHERE	t2.PARENT_GROUP_ID = t1.GROUP_ID AND t3.PARENT_GROUP_ID = t2.GROUP_ID AND t4.PARENT_GROUP_ID = t3.GROUP_ID  \n");
		 if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
			sql.append(" and t1.GROUP_CODE like ?");
			params.add("%"+queryParam.get("groupCode")+"%");
		 }
		 if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
	 		sql.append(" and t1.GROUP_NAME like ?");
			params.add("%"+queryParam.get("groupName")+"%");
		 }
		 if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and t1.STATUS = ?");
			params.add(queryParam.get("status"));
		 }
		 if(!StringUtils.isNullOrEmpty(queryParam.get("parentGroupCode"))){
			sql.append(" and t2.GROUP_CODE = ?");
			params.add(queryParam.get("parentGroupCode"));
		 }
		 sql.append(" ORDER BY t1.GROUP_CODE ");
		 System.out.println("SQL:"+sql);
	     List<Map> resultList = OemDAOUtil.findAll(sql.toString(),params);
	     return resultList;
	}
	
	/**
	 * 上级物料组弹出窗口
	 * @param queryParam
	 * @param type 
	 * @return
	 */
	public PageInfoDto selectMaterialGroupWin(Map<String, String> queryParam, int type) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  \n");
		if(queryParam.get("type").toString().equals("1")){
			sql.append(" t1.GROUP_ID,t1.GROUP_CODE,  \n");
		}else{
			sql.append(" t1.GROUP_ID PARENT_GROUP_ID,t1.GROUP_CODE PARENT_GROUP_CODE,  \n");
		}
		sql.append(" t1.GROUP_NAME,t1.GROUP_LEVEL,t1.IF_FOREC,t1.IS_EC,t1.STATUS,t1.WX_ENGINE,t1.OILE_TYPE,t1.OTHER_OPTION,t1.STANDARD_OPTION,t1.MODEL_YEAR  \n");
		sql.append(" FROM tm_vhcl_material_group t1  \n");
		sql.append(" LEFT JOIN  tm_vhcl_material_group t2  ON t1.PARENT_GROUP_ID = t2.GROUP_ID \n");
		
		if(type==1){
			sql.append(" WHERE t1.GROUP_LEVEL <> 4 \n");
		}else{
			sql.append(" WHERE t1.GROUP_LEVEL = 4 and t1.status = 10011001 \n");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
			sql.append(" and t1.GROUP_CODE like ?");
			params.add("%"+queryParam.get("groupCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and t1.GROUP_NAME like ?");
			params.add("%"+queryParam.get("groupName")+"%");
		}
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(),params);
		return pageInfoDto;
	}
	
	/**
	 * 获得物料组的树代码
	 * @param parentTreeCode
	 * @return
	 */
	public String getTreeCode(String parentTreeCode){
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT MAX(TREE_CODE) AS TREE_CODE,COUNT(*) AS COUNT FROM TM_VHCL_MATERIAL_GROUP");
		sql.append(" WHERE TREE_CODE LIKE '"+parentTreeCode+"___'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		
		String treeCode="";
		if(list !=null){
			String count=String.valueOf(list.get(0).get("COUNT"));
			String str=String.valueOf(list.get(0).get("TREE_CODE"));
			if(count==null || count=="") {
				return "";
			}
			//如果count值为0说明上级物料组没有子节点
			if("0".equals(count)){
				treeCode=parentTreeCode+="A01";
				//如果count不为0说明已经有子节点
			}else {
				if(null!=str && !"".equals(str)){
					String t1=str.substring(str.length()-2, str.length());
					String t2 = Integer.parseInt(t1)+1+"";
					String t3=str.substring(0,str.length()-2);
					if(t2.length()==1){
						treeCode=t3+"0"+t2;
					}else{
						treeCode=t3+t2;
					}
				}
			}
		}else{
			treeCode=parentTreeCode+="A01";
		}
		
		
		return treeCode;
	}
	
	/**
	 * 校验物料组代码
	 * @param mgDto
	 * @return
	 */
	public Boolean checkCode(MaterialGroupDTO mgDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(1) CNT FROM TM_VHCL_MATERIAL_GROUP WHERE GROUP_CODE = ? ");
		params.add(mgDto.getGroupCode());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
		}
		return flag;
	}

}
