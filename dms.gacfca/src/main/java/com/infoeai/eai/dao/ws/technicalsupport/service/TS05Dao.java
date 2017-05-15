package com.infoeai.eai.dao.ws.technicalsupport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.wsServer.material.TsMaterialVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TS05Dao extends OemBaseDAO {

	public List<TsMaterialVO> getTS05VO(String updateDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select TVMG.GROUP_ID MATERIAL_ID,TVMG.GROUP_CODE MATERIAL_CODE,TVMG.GROUP_NAME MATERIAL_NAME \n");
		sql.append("	   ,TVMG.MODEL_YEAR,TVMG.GROUP_LEVEL,TVMG.PARENT_GROUP_ID,TVMG.REMARK,TVMG.STATUS,TVMG2.GROUP_CODE PARENT_GROUP_CODE \n");
		sql.append(" FROM  TM_VHCL_MATERIAL_GROUP TVMG,\n");
		sql.append("       TM_VHCL_MATERIAL_GROUP TVMG2 \n");
		sql.append(" WHERE TVMG.PARENT_GROUP_ID=TVMG2.GROUP_ID\n");
		if (updateDate!=null&&!"".equals(updateDate)){
			sql.append("		AND (TVMG.CREATE_AT BETWEEN '"+updateDate+"' AND NOW() AND TVMG.UPDATE_AT is null\n");
			sql.append("		OR TVMG.UPDATE_AT BETWEEN '"+updateDate+"' AND NOW() ) \n");
		}
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setTsMaterialVOList(mapList);
	}

	private List<TsMaterialVO> setTsMaterialVOList(List<Map> mapList) {
		List<TsMaterialVO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TsMaterialVO vo = new TsMaterialVO();
			    Long materialId = CommonUtils.checkNull(map.get("MATERIAL_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("MATERIAL_ID")));
			    String materialCode = CommonUtils.checkNull(map.get("MATERIAL_CODE"));
			    String materialName = CommonUtils.checkNull(map.get("MATERIAL_NAME"));
			    Integer materialLevel = CommonUtils.checkNull(map.get("GROUP_LEVEL")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("GROUP_LEVEL")));
			    String modelYear = CommonUtils.checkNull(map.get("MODEL_YEAR"));
			    String parentMaterialCode = CommonUtils.checkNull(map.get("PARENT_GROUP_CODE"));
			    String remark = CommonUtils.checkNull(map.get("REMARK"));
			    Integer status = CommonUtils.checkNull(map.get("STATUS")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("STATUS")));
				    
				vo.setMaterialCode(materialCode);
				vo.setMaterialId(materialId);
				vo.setMaterialLevel(materialLevel);
				vo.setMaterialName(materialName);
				vo.setModelYear(modelYear);
				vo.setParentMaterialCode(parentMaterialCode);
				vo.setRemark(remark);
				vo.setStatus(status);
				resultList.add(vo);
			}
		}
		return resultList;
	}

}
