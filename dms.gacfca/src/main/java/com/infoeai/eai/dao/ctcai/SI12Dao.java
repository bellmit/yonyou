package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.infoeai.eai.DTO.TcCodeDTO;
import com.infoeai.eai.DTO.TmDealerDTO;
import com.infoeai.eai.DTO.TmRegionDTO;
import com.infoeai.eai.DTO.TmVhclMaterialGroupDTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

public class SI12Dao {
	public static Logger logger = Logger.getLogger(SI12Dao.class);
	
	/**
	 * 功能说明:将对方的ID转换成DMS的ID
	 * 创建人: Benzc 
	 * 创建日期: 2017-04-24
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getRelationIdInfo(String id,String type) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tcr.CODE_ID \n");
		sql.append("    FROM TC_RELATION tcr, tc_code tcc \n");
		sql.append("   WHERE tcr.CODE_ID = tcc.CODE_ID AND tcc.TYPE = "+type+"\n");
		sql.append("     and tcr.RELATION_CODE = "+id+"\n");
		sql.append(" with ur ");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<TcCodeDTO> list = new ArrayList<>();
		for(Map map : mapList){
			TcCodeDTO dto = new TcCodeDTO();
			String codeId = CommonUtils.checkNull(map.get("CODE_ID"));
			dto.setCodeId(codeId);
			list.add(dto);
		}
		if(mapList.size()>0){
			return mapList.get(0).get("CODE_ID").toString();
		}else{
			return null;
		}
	}
	
	/**
	 * 功能说明:根据给定treeCode查GroupId
	 * 创建人: Benzc 
	 * 创建日期: 2017-04-24
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long getGroupId(String treeCode) {
		Long groupId = null;
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT GROUP_ID \n");
		sql.append("    FROM TM_VHCL_MATERIAL_GROUP \n");
		sql.append("   WHERE tree_code = '"+treeCode+"' \n"); 
		sql.append(" with ur ");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<TmVhclMaterialGroupDTO> list = new ArrayList<>();
		for(Map map : mapList){
			TmVhclMaterialGroupDTO dto = new TmVhclMaterialGroupDTO();
			Long groupId1 = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID")));
			dto.setGroupId(groupId1);
			list.add(dto);
		}
		if(mapList.size()>0){
			groupId = (Long) mapList.get(0).get("GROUP_ID");
		}
		return groupId;
	}
	
	/**
	 * 功能说明:将LMS的地址转换成本地地址
	 * 创建人: Benzc
	 * 创建日期: 2017-04-24
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Long getLocalId(String lmsId) {
		String regionCode = null;
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  select region_code from TM_REGION where LMS_ID = "+lmsId+" \n");
		sql.append(" with ur ");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<TmRegionDTO> list = new ArrayList<>();
		for(Map map : mapList){
			TmRegionDTO dto = new TmRegionDTO();
			String regionCode1 = CommonUtils.checkNull(map.get("REGION_CODE"));
			dto.setRegionCode(regionCode1);
			list.add(dto);
		}
		if(mapList.size()>0){
			regionCode = (String) mapList.get(0).get("REGION_CODE");
		}
		return new Long(regionCode);
	}
	
	/**
	 * 功能说明:将LMS的经销商代码转换成我们的经销商代码
	 * 创建人: Benzc
	 * 创建日期: 2017-04-24
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String getLocalDealerCode(String lmsDealerCode) {
		String dealerCode = null;
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tmd.DEALER_CODE \n");
		sql.append("    FROM TM_COMPANY TMC,TM_DEALER TMD \n");
		sql.append("   WHERE TMC.COMPANY_ID = TMD.COMPANY_ID \n");
		sql.append("     AND TMD.DEALER_TYPE = 10771001 \n");
		sql.append("     AND TMC.LMS_CODE ='"+lmsDealerCode+"' \n");
		sql.append(" with ur ");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<TmDealerDTO> list = new ArrayList<>();
		for(Map map : mapList){
			TmDealerDTO dto = new TmDealerDTO();
			String dealerCode1 = CommonUtils.checkNull(map.get("DEALER_CODE"));
			dto.setDealerCode(dealerCode1);
			list.add(dto);
		}
		if(mapList.size()>0){
			dealerCode = (String) mapList.get(0).get("DEALER_CODE");
		}
		return dealerCode;
		
	}

}
