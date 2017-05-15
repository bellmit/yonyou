package com.yonyou.dms.vehicle.dao.materialManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialDTO;

/**
 * 物料组维护DAO
 * @author 夏威
 * 2017-1-16
 */

@Repository
public class MaterialDao extends OemBaseDAO {
	
	
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
		sql.append(" SELECT  tvm.MATERIAL_ID,tvmg.GROUP_CODE,tvmg.GROUP_NAME,tvm.COLOR_CODE,tvm.COLOR_NAME,tvm.TRIM_CODE,tvm.TRIM_NAME, \n");
		sql.append(" tvm.IS_SALES,tvm.IS_EC,tvm.STATUS  \n");
		sql.append(" FROM tm_vhcl_material tvm, tm_vhcl_material_group_r tvmgr,tm_vhcl_material_group tvmg \n");
		sql.append(" WHERE  tvm.MATERIAL_ID = tvmgr.MATERIAL_ID AND tvmgr.GROUP_ID = tvmg.GROUP_ID \n");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))){
			sql.append(" and tvmg.GROUP_CODE like ?");
			params.add("%"+queryParam.get("groupCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and tvmg.GROUP_NAME like ?");
			params.add("%"+queryParam.get("groupName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){
			sql.append(" and tvm.STATUS = ?");
			params.add(queryParam.get("status"));
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
	     List<Map> resultList = OemDAOUtil.downloadPageQuery(sql,params);
	     return resultList;
	}
	
	/**
	 * 校验物料组代码
	 * @param mgDto
	 * @return
	 */
	public Boolean checkCode(MaterialDTO mgDto) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT COUNT(1) CNT FROM TM_VHCL_MATERIAL tvm ,tm_vhcl_material_group_r tvmgr  WHERE tvm.MATERIAL_ID = tvmgr.MATERIAL_ID AND tvmgr.GROUP_ID = ? AND tvm.COLOR_CODE = ? AND TRIM_CODE = ? ");
		params.add(mgDto.getGroupId());
		params.add(mgDto.getColorCode());
		params.add(mgDto.getTrimCode());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		Boolean flag = true;
		if(null!=list&&list.size()>0){
			if(Integer.parseInt(list.get(0).get("CNT").toString())>0){
				flag = false;
			}
		}
		return flag;
	}

	public List<Map> getDealerList(Map<String, String> queryParams) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT D.COMPANY_ID,DEALER_ID,DEALER_CODE,DEALER_SHORTNAME,MARKETING,DEALER_NAME,D.STATUS,DEALER_LEVEL,ADDRESS,DEALER_TYPE,O.ORG_NAME");
		sql.append(" FROM TM_DEALER D JOIN TM_ORG O ON D.DEALER_ORG_ID = O.ORG_ID ");
		sql.append(" WHERE D.STATUS = "+ OemDictCodeConstants.STATUS_ENABLE + " ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DWR);
		}else{
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DVS);
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sql.append(" and DEALER_CODE like ?");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
			sql.append(" and DEALER_SHORTNAME like ? ");
			params.add("%"+queryParams.get("dealerShortname")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))){
			sql.append(" and DEALER_NAME like ? ");
			params.add("%"+queryParams.get("dealerName")+"%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.findAll(sql.toString(),params);
	}

	public List<Map> getDealerBussInfo() {
StringBuffer sql = new StringBuffer();
		
		sql.append(" select tt.*,case \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" AND BCODE = 'JEEP' then '1' \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" AND BCODE = 'FIAT' then '2' \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_IMPORT+" then '3' \n");
		sql.append("                 else '4' \n");
		sql.append("             end as GSTYPE \n");
		sql.append(" from (select t1.GROUP_ID as  GROUPID ,t1.GROUP_TYPE as GTYPE, t1.GROUP_CODE as SCODE,t1.GROUP_NAME as SNAME ,t2.GROUP_NAME as BNAME,t2.GROUP_CODE as BCODE , \n");
		sql.append(" 	(select code_desc  from tc_code_dcs where code_id like t1.GROUP_TYPE) as GROUP_TYPE  \n");
		sql.append(" 	from TM_VHCL_MATERIAL_GROUP  t1 left join TM_VHCL_MATERIAL_GROUP t2 on t1.PARENT_GROUP_ID = t2.GROUP_ID \n");
		sql.append(" 	where t1.GROUP_LEVEL = 2 and t1.status = "+OemDictCodeConstants.STATUS_ENABLE+" and t2.STATUS =  "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append(" ) tt \n");
		List<Map> pageInfoDto = OemDAOUtil.findAll(sql.toString(), null);
		return pageInfoDto;
	}

}
