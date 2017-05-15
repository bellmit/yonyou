package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */
@Repository
public class MvsFamilyMaintainDao extends OemBaseDAO{

	/**
	 * 查询菲亚特车系
	 */
	public List<Map> GetMVCCheXi(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql  = new StringBuffer("\n");
		sql.append("select TVMG.GROUP_ID as seriesId ,TVMG.GROUP_NAME as seriesName ,TVMG.GROUP_CODE as seriesCode \n");
		sql.append("from TM_VHCL_MATERIAL_GROUP TVMG where TVMG.PARENT_GROUP_ID in ( \n");
		sql.append("select TVMG.GROUP_ID from TM_VHCL_MATERIAL_GROUP TVMG \n");
		sql.append("where TVMG.GROUP_LEVEL=1 and TVMG.GROUP_CODE = \n");
		sql.append("(select code_desc from tc_code_dcs where code_id ='"+OemDictCodeConstants.FIAT_GROUP_CODE+"')\n");
		sql.append(") and TVMG.STATUS='"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("order by TVMG.GROUP_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 关联车款
	 */
	@SuppressWarnings("unchecked")
	public List<Map> getGroupCodes(Long seriesId) {
		StringBuffer  sql = new StringBuffer("\n");
		sql.append("  SELECT DISTINCT \n");
		sql.append("	 C.GROUP_ID AS MODEL_ID, -- 车型ID \n");
		sql.append("	 C.GROUP_CODE AS MODEL_CODE, -- 车型代码 \n");
		sql.append("	 C.GROUP_NAME AS MODEL_NAME, -- 车型名称 \n");
		sql.append("	 C.PARENT_GROUP_ID -- 父级ID \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP C \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append("     WHERE C.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL+"' \n");
		sql.append("	 AND S.GROUP_LEVEL = '"+OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR+"' \n");
		sql.append("	 AND C.STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' \n");
		sql.append("     AND C.PARENT_GROUP_ID = '"+seriesId+"'  \n");
		System.out.println("----sql-----------------"+sql);
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 *查询
	 */
	public PageInfoDto MVSFamilyMaintainQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> MVSFamilyMaintainDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("    SELECT  \n");
		sql.append("		TVMG.MVS, \n");
		sql.append("		TVMG.GROUP_CODE, \n");
		sql.append("		TVMG.GROUP_NAME, \n");
		sql.append("		TVMG.GROUP_ID, \n");
		sql.append("		TVMG.STATUS, \n");
		sql.append("		TU2.NAME UPDATE_BY, \n");
		sql.append("		left(TVMG.UPDATE_AT,10) UPDATE_AT \n");
		sql.append("	FROM  TM_VHCL_MATERIAL_GROUP TVMG  \n");
		sql.append("		LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TVMG.CREATE_BY	 \n");
		sql.append("		LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TVMG.UPDATE_BY  \n");
		sql.append("	WHERE 1=1	 \n");
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sql.append("       AND TVMG.PARENT_GROUP_ID in \n");
			sql.append("		( \n");
			sql.append("			select GROUP_ID from TM_VHCL_MATERIAL_GROUP where  GROUP_ID='"+queryParam.get("seriesId")+"' \n");
			sql.append("		) \n");
		}
		//车型代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelId"))){
			sql.append("	 AND TVMG.GROUP_ID = '"+queryParam.get("modelId")+"' \n");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId")) && !StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sql.append("       AND TVMG.PARENT_GROUP_ID in \n");
			sql.append("		( \n");
			sql.append("			select GROUP_ID from TM_VHCL_MATERIAL_GROUP   \n");
			sql.append("				where PARENT_GROUP_ID= \n");
			sql.append("					( \n");
			sql.append("						select GROUP_ID from TM_VHCL_MATERIAL_GROUP where GROUP_CODE= \n");
			sql.append("							( \n");
			sql.append("								select code_desc from tc_code_dcs where code_id ='"+OemDictCodeConstants.FIAT_GROUP_CODE+"' \n");
			sql.append("							) \n");
			sql.append("					) \n");
			sql.append("		) \n");
		 }
		//MVS
		if(!StringUtils.isNullOrEmpty(queryParam.get("MVS"))){
			sql.append("       AND TVMG.MVS LIKE '%"+queryParam.get("MVS")+"%' \n");
		}
		 	sql.append("       AND TVMG.MVS is not NULL AND TVMG.MVS !=''   \n");
		 	System.out.println("-----查询sql------"+sql.toString());
		return sql.toString();
	}
	/**
	 * 新增（查询出车型对应数据进行修改）
	 */
	public Long MVSAdd(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select GROUP_CODE,GROUP_ID,MVS from TM_VHCL_MATERIAL_GROUP where 1=1 and GROUP_ID = ?");
		List<Object> list = new ArrayList<Object>();
		list.add(tvmgDto.getGroupId());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		Long gropuId = null;
		if (map!=null&&map.size() > 0) {
			gropuId =  (Long) map.get(0).get("GROUP_ID");
			if(map.get(0).get("MVS")!=null&&!"".equals(map.get(0).get("MVS"))){
				throw new ServiceBizException("该车型代码已维护MVS,请从新输入！");
			}else{
				if(tvmgDto.getMvs()!=null&&!"".equals(tvmgDto.getMvs())){
					TmVhclMaterialGroupPO tc = TmVhclMaterialGroupPO.findById(gropuId);
					setTt(tc, tvmgDto);
					tc.saveIt();
				}
			}
		} 
		return gropuId;
	}
	private void setTt(TmVhclMaterialGroupPO tc, TmVhclMaterialGroupDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setString("mvs", tcdto.getMvs());
		tc.setDate("update_at", new Date());
		tc.setString("update_by", loginInfo.getUserId());
		tc.setString("status", OemDictCodeConstants.STATUS_ENABLE);
	}
	/*
	 * 回显
	 */
	public TmVhclMaterialGroupPO findMVS(Long id) {
		StringBuffer sql = new StringBuffer(" \n");
		sql.append("      SELECT  \n");
		sql.append("		TVMG.MVS, \n");
		sql.append("		PTVMG.GROUP_ID PGROUP_ID,\n");
		sql.append("		PTVMG.GROUP_CODE PGROUP_CODE, \n");
		sql.append("		PTVMG.GROUP_NAME PGROUP_NAME, \n");
		sql.append("		TVMG.GROUP_CODE, \n");
		sql.append("		TVMG.GROUP_NAME, \n");
		sql.append("		TVMG.GROUP_ID \n");
		sql.append("	 FROM  TM_VHCL_MATERIAL_GROUP TVMG  \n");
		sql.append("		LEFT JOIN TM_VHCL_MATERIAL_GROUP PTVMG ON PTVMG.GROUP_ID = TVMG.PARENT_GROUP_ID	 \n");
		sql.append("		LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TVMG.CREATE_BY	 \n");
		sql.append("		LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TVMG.UPDATE_BY  \n");
		sql.append("	 WHERE  1=1 \n");
	 	sql.append("  		AND TVMG.MVS is not NULL AND TVMG.MVS !=''   \n");
	 	sql.append("  		AND TVMG.GROUP_ID = '"+id+"'  \n");
	 	System.out.println("-----sql------"+sql);
		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		TmVhclMaterialGroupPO PO = new TmVhclMaterialGroupPO();
		PO.setString("GROUP_CODE", list.get(0).get("PGROUP_NAME"));//车型名称
		PO.setString("GROUP_NAME", list.get(0).get("GROUP_CODE"));//车系code存放于字段GROUP_NAME
		PO.setString("MVS", list.get(0).get("MVS"));//MVS
		return PO;
	}
	/**
	 * 修改
	 */
	public Long MVSUpdate(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select GROUP_CODE,GROUP_ID,MVS from TM_VHCL_MATERIAL_GROUP where 1=1 and GROUP_CODE = ?");
		List<Object> list = new ArrayList<Object>();
		list.add(tvmgDto.getGroupCode());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		Long gropuId = null;
		if (map!=null&&map.size() > 0) {
			gropuId =  (Long) map.get(0).get("GROUP_ID");
			if(tvmgDto.getMvs()!=null&&!"".equals(tvmgDto.getMvs())){
				TmVhclMaterialGroupPO tc = TmVhclMaterialGroupPO.findById(gropuId);
				setTt(tc, tvmgDto);
				tc.saveIt();
			}else{
					TmVhclMaterialGroupPO tc = TmVhclMaterialGroupPO.findById(gropuId);
					setTt(tc, tvmgDto);
					tc.saveIt();
			}
		} 
		return gropuId;
	}
}
