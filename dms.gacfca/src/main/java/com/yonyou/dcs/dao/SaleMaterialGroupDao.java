/**
 * @Title: SaleMaterialGroupDao.java 
 * @Description:车型组信息下发数据操作类
 * @Date: 2012-6-26
 * @author yuyang
 * @version 1.0
 * @remark
 */

package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SaleMaterialGroupDao extends OemBaseDAO {

	/**
	 * 
	 * @Title: queryMaterialGroupInfo
	 * @Description: (查询车型组列表)
	 * @return List<CompeteModeVO> 返回类型
	 * @throws
	 */
	public LinkedList<CLDMS002Dto> queryMaterialGroupInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.GROUP_CODE as CONFIGCODE,t1.GROUP_NAME as CONFIGNAME,t1.STATUS, \n");
		sql.append(" t2.GROUP_CODE as MODELCODE,t2.GROUP_NAME as MODELNAME, t2.STATUS as MODELSTATUS, \n");
		sql.append(" t3.GROUP_CODE as SERIESCODE,t3.GROUP_NAME as SERIESNAME, t3.STATUS as SERIESSTATUS,  \n");
		sql.append(" t4.GROUP_CODE as BRANDCODE,t4.GROUP_NAME as BRANDNAME ,t4.STATUS as BRANDSTATUS \n");
		//wjs  2016-01-05 添加燃油类型，发动机排量
		sql.append(", t2.OILE_TYPE,t2.WX_ENGINE  ");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP t1,TM_VHCL_MATERIAL_GROUP t2,TM_VHCL_MATERIAL_GROUP t3,TM_VHCL_MATERIAL_GROUP t4 \n");
		sql.append(" WHERE t1.PARENT_GROUP_ID=t2.GROUP_ID \n");
		sql.append(" AND   t2.PARENT_GROUP_ID=t3.GROUP_ID \n");
		sql.append(" AND   t3.PARENT_GROUP_ID=t4.GROUP_ID \n");
		sql.append(" AND   t1.GROUP_LEVEL='4' \n");
		LinkedList<CLDMS002Dto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(), null));
		return list;
	}
	
	/**
	 * 
	 * @Title: queryMaterialGroupInfo
	 * @Description: (查询车型组列表)
	 * @return List<CompeteModeVO> 返回类型
	 * @throws
	 */
	public LinkedList<CLDMS002Dto> selectMaterialGroupInfo(String[] groupIds) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.GROUP_CODE as CONFIGCODE,t1.GROUP_NAME as CONFIGNAME,t1.STATUS, \n");
		sql.append(" t2.GROUP_CODE as MODELCODE,t2.GROUP_NAME as MODELNAME, t2.STATUS as MODELSTATUS, \n");
		sql.append(" t3.GROUP_CODE as SERIESCODE,t3.GROUP_NAME as SERIESNAME, t3.STATUS as SERIESSTATUS,  \n");
		sql.append(" t4.GROUP_CODE as BRANDCODE,t4.GROUP_NAME as BRANDNAME ,t4.STATUS as BRANDSTATUS \n");
		//wjs  2016-01-05 添加燃油类型，发动机排量
		sql.append(",   t2.OILE_TYPE,t2.WX_ENGINE  ");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP t1,TM_VHCL_MATERIAL_GROUP t2,TM_VHCL_MATERIAL_GROUP t3,TM_VHCL_MATERIAL_GROUP t4 \n");
		sql.append(" WHERE t1.PARENT_GROUP_ID=t2.GROUP_ID \n");
		sql.append(" AND   t2.PARENT_GROUP_ID=t3.GROUP_ID \n");
		sql.append(" AND   t3.PARENT_GROUP_ID=t4.GROUP_ID \n");
		sql.append(" AND   t1.GROUP_LEVEL='4' \n");
		if(null!=groupIds && groupIds.length>0){
			sql.append(" AND  t3.GROUP_ID in (");
			for(int i=0;i< groupIds.length ; i++){
				if( (i+1) == groupIds.length){
					sql.append(" '"+groupIds[i]+"' ");
				}else{
					sql.append(" '"+groupIds[i]+"', ");
				}
			}
			sql.append(" ) ");
		}
		LinkedList<CLDMS002Dto> list = wrapperVO(OemDAOUtil.findAll(sql.toString(),null));
		return list;
	}

	protected LinkedList<CLDMS002Dto> wrapperVO(List<Map> rs) {
		LinkedList<CLDMS002Dto> resultList = new LinkedList<CLDMS002Dto>();
		try {
			if(null!=rs && rs.size()>0){
				for (int i = 0; i < rs.size(); i++) {
					CLDMS002Dto dto = new CLDMS002Dto();
					dto.setBrandCode(CommonUtils.checkNull(rs.get(i).get("BRANDCODE")));
					dto.setBrandName(CommonUtils.checkNull(rs.get(i).get("BRANDNAME")));
					dto.setBrandStatus(CommonUtils.checkNullInt(rs.get(i).get("BRANDSTATUS"),0));
					dto.setSeriesCode(CommonUtils.checkNull(rs.get(i).get("SERIESCODE")));
					dto.setSeriesName(CommonUtils.checkNull(rs.get(i).get("SERIESNAME")));
					dto.setSeriesStatus(CommonUtils.checkNullInt(rs.get(i).get("SERIESSTATUS"),0));
					dto.setModelCode(CommonUtils.checkNull(rs.get(i).get("MODELCODE")));
					dto.setModelName(CommonUtils.checkNull(rs.get(i).get("MODELNAME")));
					dto.setModelStatus(CommonUtils.checkNullInt(rs.get(i).get("MODELSTATUS"),0));
					dto.setConfigCode(CommonUtils.checkNull(rs.get(i).get("CONFIGCODE")));
					dto.setConfigName(CommonUtils.checkNull(rs.get(i).get("CONFIGNAME")));
					dto.setIsValid(CommonUtils.checkNullInt(rs.get(i).get("STATUS"),0));
					dto.setDownTimestamp(new Date());
					dto.setOilType(CommonUtils.checkNullInt(rs.get(i).get("OILE_TYPE"),0));
					dto.setEngineDesc(CommonUtils.checkNull(rs.get(i).get("WX_ENGINE")));
					resultList.add(dto);
				}
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		return resultList;
	}

	/**
	 * 
	 * @Title: queryGroupIdByGroupCode
	 * @Description: (根据groupCode查询GroupId)
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryGroupIdByGroupCode(String groupCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.GROUP_ID  \n");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP t1 \n");
		sql.append(" WHERE t1.GROUP_CODE = ? \n");

		@SuppressWarnings("rawtypes")
		List params = new ArrayList();
		params.add(groupCode);
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), params);
		if (map != null && !map.isEmpty()) {
			return map.get("GROUP_ID").toString();
		}
		return null;
	}
	
	
	/**
	 * 
	 * @Title: queryGroupIdByTreeCode
	 * @Description: (根据groupCode查询TreeCode)
	 * @return String 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public String queryTreeCodeByGroupCode(String groupCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT t1.TREE_CODE  \n");
		sql.append(" FROM TM_VHCL_MATERIAL_GROUP t1 \n");
		sql.append(" WHERE t1.GROUP_CODE = ? \n");

		@SuppressWarnings("rawtypes")
		List params = new ArrayList();
		params.add(groupCode);
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), params);
		if (map != null && !map.isEmpty()) {
			return map.get("TREE_CODE").toString();
		}
		return null;
	}
	
	/**
	 * 获取经销商业务范围
	 * @param dealerCode
	 * @return
	 */
	public String[] getDealerByGroupId(String[] groupID,String dealerCode) {
		String[] groupIds = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT T1.GROUP_ID FROM TM_DEALER_BUSS t1 , TM_DEALER t2 WHERE  t1.DEALER_ID = t2.DEALER_ID AND T2.DEALER_CODE = '" + dealerCode + "' ");
		if(null!= groupID && groupID.length>0){
			sql.append(" AND T1.GROUP_ID IN( ");
			for (int i = 0; i < groupID.length; i++) {
				if (i == groupID.length - 1) {
					sql.append(" '" + groupID[i] + "'");
				} else {
					sql.append(" '" + groupID[i] + "',");
				}
			}
			sql.append(" ) ");
		}
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			groupIds = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				groupIds[i] = CommonUtils.checkNull(list.get(i).get("GROUP_ID"));
			}
		}
		return groupIds;
	}

}
