package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppUTestDriveDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 更新客户信息（试乘试驾）(APP更新)
 * @author luoyang
 *
 */
@Repository
public class TiAppUTestDriveDao extends OemBaseDAO {
	
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

	public LinkedList<TiAppUTestDriveDto> queryBodyInfo(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUTestDriveDto> list = this.setTiAppUTestDriveDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppUTestDriveDto> queryBodyInfo() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUTestDriveDto> list = this.setTiAppUTestDriveDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppUTestDriveDto> setTiAppUTestDriveDtoList(List<Map> mapList) throws ParseException {
		LinkedList<TiAppUTestDriveDto> resultList = new LinkedList<TiAppUTestDriveDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiAppUTestDriveDto dto = new TiAppUTestDriveDto();
				Long testDriveId = CommonUtils.checkNull(map.get("TEST_DRIVE_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("TEST_DRIVE_ID")));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : CommonUtils.checkNull(map.get("UNIQUENESS_ID"));
				Integer FCAID = CommonUtils.checkNull(map.get("FCA_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("FCA_ID")));
				Date testDriveTime = CommonUtils.checkNull(map.get("TEST_DRIVE_TIME")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("TEST_DRIVE_TIME")));
				String testBrandId = map.get("TEST_BRAND_ID") == null ? null : CommonUtils.checkNull(map.get("TEST_BRAND_ID"));
				String testModelID = map.get("TEST_MODEL_ID") == null ? null : CommonUtils.checkNull(map.get("TEST_MODEL_ID"));
				String testCarStyleID = map.get("TEST_CAR_STYLE_ID") == null ? null : CommonUtils.checkNull(map.get("TEST_CAR_STYLE_ID"));
				String driveRoadDicID = map.get("DRIVE_ROAD_DIC_ID") == null ? null : CommonUtils.checkNull(map.get("DRIVE_ROAD_DIC_ID"));
				String identificationNO = map.get("IDENTIFICATION_NO") == null ? null : CommonUtils.checkNull(map.get("IDENTIFICATION_NO"));
				String dealerCode = map.get("DEALER_CODE") == null ? null : CommonUtils.checkNull(map.get("DEALER_CODE"));
				Date updateDate = CommonUtils.checkNull(map.get("UPDATE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("UPDATE_DATE")));
				
				dto.setTestDriveId(testDriveId);
				dto.setDealerCode(dealerCode);
				dto.setDriveRoadDicID(driveRoadDicID);
				dto.setFCAID(FCAID);
				dto.setIdentificationNO(identificationNO);
				dto.setTestBrandId(testBrandId);
				dto.setTestCarStyleID(testCarStyleID);
				dto.setTestDriveTime(testDriveTime);
				dto.setTestModelID(testModelID);
				dto.setUniquenessID(uniquenessID);
				dto.setUpdateDate(updateDate);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANTD.TEST_DRIVE_ID, \n" );
		sql.append("       ANTD.FCA_ID, \n" );
		sql.append("       ANTD.UNIQUENESS_ID, \n" );
		sql.append("       ANTD.TEST_DRIVE_TIME, \n" );
		sql.append("       VMG_BRAND.GROUP_CODE AS TEST_BRAND_ID, \n" );
		sql.append("       VMG_MODEL.GROUP_CODE AS TEST_MODEL_ID, \n" );
		sql.append("       VMG_CARSTYLE.GROUP_CODE AS TEST_CAR_STYLE_ID, \n" );
		sql.append("       ANTD.DRIVE_ROAD_DIC_ID, \n" );
		sql.append("       ANTD.IDENTIFICATION_NO, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       IFNULL(DATE_FORMAT(ANTD.UPDATE_DATE,'%Y-%m-%d %H:%i:%S'),'') as UPDATE_DATE  \n" );
		sql.append("   FROM TI_APP_U_TEST_DRIVE ANTD \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANTD.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_BRAND ON ANTD.TEST_BRAND_ID = VMG_BRAND.TREE_CODE AND VMG_BRAND.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_BRAND.GROUP_LEVEL = 1 AND VMG_BRAND.IS_DEL = 0 \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_MODEL ON ANTD.TEST_MODEL_ID = VMG_MODEL.TREE_CODE AND VMG_MODEL.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_MODEL.GROUP_LEVEL = 2 AND VMG_MODEL.IS_DEL = 0 \n" );
		sql.append("       LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_CARSTYLE ON ANTD.TEST_CAR_STYLE_ID = VMG_CARSTYLE.TREE_CODE AND VMG_CARSTYLE.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " AND VMG_CARSTYLE.GROUP_LEVEL = 4 AND VMG_CARSTYLE.IS_DEL = 0 \n" );
		sql.append("   WHERE ANTD.IS_SEND = '0' OR ANTD.IS_SEND IS NULL OR ANTD.IS_SEND='9' \n" );
		return sql;
	}

}
