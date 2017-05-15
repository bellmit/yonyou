package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppNTestDriveDao extends OemBaseDAO {
	
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

	public LinkedList<TiAppNTestDriveDto> queryAppNTestDrive(String[] groupIds) throws ParseException {
		StringBuffer sql = this.getSql();
//		if(null!=groupIds && groupIds.length>0){
//			sql.append(" AND  VMG_CARSTYLE.GROUP_ID in (");
//			for(int i=0;i< groupIds.length ; i++){
//				if( (i+1) == groupIds.length){
//					sql.append(" '"+groupIds[i]+"' ");
//				}else{
//					sql.append(" '"+groupIds[i]+"', ");
//				}
//			}
//			sql.append(" ) ");
//		}
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNTestDriveDto> list = setTiAppNTestDriveDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppNTestDriveDto> queryAppNTestDrive() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNTestDriveDto> list = setTiAppNTestDriveDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppNTestDriveDto> setTiAppNTestDriveDtoList(List<Map> list) throws ParseException {
		LinkedList<TiAppNTestDriveDto> resultList = new LinkedList<TiAppNTestDriveDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && !list.isEmpty()){
			for(Map<String,Object> map : list){
				TiAppNTestDriveDto dto = new TiAppNTestDriveDto();
				Long testDriveId = map.get("TEST_DRIVE_ID") == null ? null : Long.parseLong(String.valueOf(map.get("TEST_DRIVE_ID")));
				Long fcaId = map.get("FCA_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FCA_ID")));
				String testModelId = map.get("TEST_MODEL_ID") == null ? null : String.valueOf(map.get("TEST_MODEL_ID"));
				String dealerCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));
				String dealerUserId = map.get("DEALER_USER_ID") == null ? null : String.valueOf(map.get("DEALER_USER_ID"));
				Date testDriveTime = map.get("TEST_DRIVE_TIME") == null ? null : sdf.parse(String.valueOf(map.get("TEST_DRIVE_TIME")));
				String testBrandId = map.get("TEST_BRAND_ID") == null ? null : String.valueOf(map.get("TEST_BRAND_ID"));
				String testCarStyleId = map.get("TEST_CAR_STYLE_ID") == null ? null : String.valueOf(map.get("TEST_CAR_STYLE_ID"));
				String driveRoadDicId = map.get("DRIVE_ROAD_DIC_ID") == null ? null : String.valueOf(map.get("DRIVE_ROAD_DIC_ID"));
				String identificationNo = map.get("IDENTIFICATION_NO") == null ? null : String.valueOf(map.get("IDENTIFICATION_NO"));
				Date createDate = map.get("CREATE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("CREATE_DATE")));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : String.valueOf(map.get("UNIQUENESS_ID"));
				
				dto.setTestDriveId(testDriveId);
				dto.setCreateDate(createDate);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserId(dealerUserId);
				dto.setDriveRoadDicId(driveRoadDicId);
				dto.setFcaId(fcaId);
				dto.setIdentificationNo(identificationNo);
				dto.setTestBrandId(testBrandId);
				dto.setTestCarStyleId(testCarStyleId);
				dto.setTestDriveTime(testDriveTime);
				dto.setTestModelId(testModelId);
				dto.setUniquenessID(uniquenessID);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANTD.TEST_DRIVE_ID, \n" );
		sql.append("       ANTD.FCA_ID, \n" );
		sql.append("       DATE_FORMAT(ANTD.TEST_DRIVE_TIME,'%Y-%m-%d %H:%i:%S') AS TEST_DRIVE_TIME, \n" );
		sql.append("       VMG_BRAND.GROUP_CODE AS TEST_BRAND_ID, \n" );
		sql.append("       VMG_MODEL.GROUP_CODE AS TEST_MODEL_ID, \n" );
		sql.append("       VMG_CARSTYLE.GROUP_CODE AS TEST_CAR_STYLE_ID, \n" );
		sql.append("       ANTD.DRIVE_ROAD_DIC_ID, \n" );
		sql.append("       ANTD.UNIQUENESS_ID, \n" );
		sql.append("       ANTD.IDENTIFICATION_NO, \n" );
		sql.append("       ANTD.DEALER_USER_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       DATE_FORMAT(ANTD.CREATE_DATE,'%Y-%m-%d %H:%i:%S') AS CREATE_DATE \n" );
		sql.append("   FROM TI_APP_N_TEST_DRIVE ANTD \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANTD.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_BRAND ON ANTD.TEST_BRAND_ID = VMG_BRAND.TREE_CODE AND VMG_BRAND.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_BRAND.GROUP_LEVEL = 1 AND VMG_BRAND.IS_DEL = 0 \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_MODEL ON ANTD.TEST_MODEL_ID = VMG_MODEL.TREE_CODE AND VMG_MODEL.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_MODEL.GROUP_LEVEL = 2 AND VMG_MODEL.IS_DEL = 0 \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_CARSTYLE ON ANTD.TEST_CAR_STYLE_ID = VMG_CARSTYLE.TREE_CODE AND VMG_CARSTYLE.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_CARSTYLE.GROUP_LEVEL = 3 AND VMG_CARSTYLE.IS_DEL = 0 \n" );
		sql.append("   WHERE ANTD.IS_SEND = '0' OR ANTD.IS_SEND IS NULL  OR ANTD.IS_SEND ='9' \n" );
		sql.append("  and DR.dms_code is not null");
		return sql;	
	}

}
