package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SEDMS065Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * 呆滞件下架or取消下发接口
 * @author luoyang
 *
 */
@Repository
public class SEDCS065Dao extends OemBaseDAO {

	public LinkedList<SEDMS065Dto> queryObsoleteMaterialRelease(String currTime) throws Exception {
		StringBuffer sql = getSql(currTime);
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SEDMS065Dto> list = setSEDMS065DtoList(mapList);
		return list;
	}
	
	private LinkedList<SEDMS065Dto> setSEDMS065DtoList(List<Map> mapList) throws Exception {
		LinkedList<SEDMS065Dto> resultList = new LinkedList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				SEDMS065Dto dto = new SEDMS065Dto();
				Map<String, Object> dmsDealer = getDmsDealerCode(String.valueOf(map.get("DEALER_CODE")));
				if (dmsDealer == null || dmsDealer.size() == 0) {
					throw new Exception("DCS端不存在编码为" + dmsDealer.get("DMS_CODE") + "的经销商记录");
				} else {
					dto.setDealerCode(String.valueOf(dmsDealer.get("DMS_CODE")));//经销商
				}
				
				dto.setStatus(map.get("CANCEL_TYPE") == null ? null : Integer.parseInt((String.valueOf(map.get("CANCEL_TYPE")))) );
				if(new Integer(1).equals(dto.getStatus())){
					dto.setCancelDate(map.get("END_DATE") == null ? null : sdf.parse(String.valueOf(map.get("END_DATE"))) );//1:取消下架 
				}else if(new Integer(2).equals(dto.getStatus())){
					dto.setShelfDate(map.get("END_DATE") == null ? null : sdf.parse(String.valueOf(map.get("END_DATE"))));//2：超期下架
				}
				dto.setItemId(map.get("ITEM_ID") == null ? null : Long.parseLong(String.valueOf(map.get("ITEM_ID"))));
				dto.setPartNo(map.get("PART_CODE") == null ? null : String.valueOf(map.get("PART_CODE")));
				dto.setStorageCode(map.get("WAREHOUSE") == null ? null : String.valueOf(map.get("WAREHOUSE")));
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(String currTime){
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    TOMR.ITEM_ID,  -- 下端发布流水号  \n");
		sql.append("    TOMR.DEALER_CODE,    -- 经销商 \n");
		sql.append("    TOMR.WAREHOUSE,  -- 仓库  \n");
		sql.append("    TOMR.PART_CODE,   -- 配件代码 \n");
		sql.append("    TOMR.CANCEL_TYPE,   -- 1:取消下架 2：超期下架 \n");
		sql.append("    TOMR.END_DATE   -- 下架时间 \n");
		sql.append("FROM TT_OBSOLETE_MATERIAL_RELEASE_DCS TOMR    \n");
		sql.append("WHERE TOMR.STATUS = '"+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_02+"'  \n");
		sql.append("  and TOMR.CANCEL_TYPE in(1,2) and TOMR.IS_SEND = 0 \n");
		sql.append("  and DATE_FORMAT(TOMR.END_DATE,'%Y-%m-%d') <= '"+currTime+"' \n");
		return sql;
	}

}
