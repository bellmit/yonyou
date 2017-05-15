package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDMS063Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 呆滞品定义下发接口
 * @author luoyang
 *
 */
@Repository
public class SEDCS063Dao extends OemBaseDAO {

	public LinkedList<SEDMS063Dto> queryObsoleteMaterialDefine(String id) throws Exception {
		StringBuffer sql = getSql(id);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDMS063Dto> list = setSEDMS063DtoList(mapList);
		return list;
	}
	
	private LinkedList<SEDMS063Dto> setSEDMS063DtoList(List<Map> mapList) throws Exception {
		LinkedList<SEDMS063Dto> resultList = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SEDMS063Dto dto = new SEDMS063Dto();
				Integer backloggedDate = map.get("OVERSTOCK_MONTH") == null ? null : Integer.parseInt(String.valueOf(map.get("OVERSTOCK_MONTH")));
				Integer validityDate = map.get("RELEASE_MONTH") == null ? null : Integer.parseInt(String.valueOf(map.get("RELEASE_MONTH"))); 
				String slowMovingName = map.get("OM_NAME") == null ? null : String.valueOf(map.get("OM_NAME"));
				Date updateTime = map.get("SEND_DATE") == null ? null : sdf.parse(String.valueOf(map.get("SEND_DATE")));
				Long itemId = map.get("DEFINE_ID") == null ? null : Long.parseLong(String.valueOf(map.get("DEFINE_ID")));
				String remark = map.get("REMARK") == null ? null : String.valueOf(map.get("REMARK"));
				
				dto.setBackloggedDate(backloggedDate);
				dto.setItemId(itemId);
				dto.setRemark(remark);
				dto.setSlowMovingName(slowMovingName);
				dto.setUpdateTime(updateTime);
				dto.setValidityDate(validityDate);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(String defineId){
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    TOMD.DEFINE_ID,  -- 呆滞品ID  \n");
		sql.append("    TOMD.OM_NAME,    -- 呆滞品名称 \n");
		sql.append("    TOMD.OVERSTOCK_MONTH,  -- 积压期（月）  \n");
		sql.append("    TOMD.RELEASE_MONTH,   -- 有效期（月） \n");
		sql.append("    TOMD.REMARK,   -- 备注） \n");
		sql.append("    TOMD.SEND_DATE   -- 下发时间 \n");
		sql.append("FROM TT_OBSOLETE_MATERIAL_DEFINE_DCS TOMD    \n");
		sql.append("WHERE TOMD.DEFINE_ID="+ defineId +"   \n");
		return sql;
	}

}
