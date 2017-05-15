package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppUCustomerInfoDao extends OemBaseDAO {
	
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

	public LinkedList<TiAppUCustomerInfoDto> queryBodyInfo(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUCustomerInfoDto> list = this.setTiAppUCustomerInfoDtoList(mapList);
		return list;
	}

	public LinkedList<TiAppUCustomerInfoDto> queryBodyInfo() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppUCustomerInfoDto> list = this.setTiAppUCustomerInfoDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppUCustomerInfoDto> setTiAppUCustomerInfoDtoList(List<Map> mapList) throws ParseException {
		LinkedList<TiAppUCustomerInfoDto> resultList = new LinkedList<TiAppUCustomerInfoDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiAppUCustomerInfoDto dto = new TiAppUCustomerInfoDto();
				Long customerId = CommonUtils.checkNull(map.get("CUSTOMER_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("CUSTOMER_ID")));
				String dealerCode = map.get("DEALER_CODE") == null ? null : CommonUtils.checkNull(map.get("DEALER_CODE"));
			    String entityCode = map.get("DEALER_CODE") == null ? null : CommonUtils.checkNull(map.get("DEALER_CODE"));
			    String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : CommonUtils.checkNull(map.get("UNIQUENESS_ID"));//DMS客户唯一ID
			    Integer FCAID = CommonUtils.checkNull(map.get("FCA_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("FCA_ID")));//售中APP的客户ID
			    String clientType = map.get("CLIENT_TYPE") == null ? null : CommonUtils.checkNull(map.get("CLIENT_TYPE"));//客户类型
			    String name = map.get("NAME") == null ? null : CommonUtils.checkNull(map.get("NAME"));//客户的姓名
			    String gender = map.get("GENDER") == null ? null : CommonUtils.checkNull(map.get("GENDER"));//性别
			    String phone = map.get("PHOME") == null ? null : CommonUtils.checkNull(map.get("PHOME"));//手机号
			    String telephone = map.get("TELEPHONE") == null ? null : CommonUtils.checkNull(map.get("TELEPHONE"));//手机号//固定电话
			    Integer provinceID = CommonUtils.checkNull(map.get("PROVINCE_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("PROVINCE_ID")));//省份ID
			    Integer cityID = CommonUtils.checkNull(map.get("CITY_ID")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("CITY_ID")));//城市ID
			    Date birthday = CommonUtils.checkNull(map.get("BIRTHDAY")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("BIRTHDAY")));//生日
			    String oppLevelID = map.get("OPP_LEVEL_ID") == null ? null : CommonUtils.checkNull(map.get("OPP_LEVEL_ID"));//客户级别ID
			    String sourceType = map.get("SOURCE_TYPE") == null ? null : CommonUtils.checkNull(map.get("SOURCE_TYPE"));//客户来源
			    String secondSourceType = map.get("SECOND_SOURCE_TYPE") == null ? null : CommonUtils.checkNull(map.get("SECOND_SOURCE_TYPE"));//客户来源
			    String dealerUserID = map.get("DEALER_USER_ID") == null ? null : CommonUtils.checkNull(map.get("DEALER_USER_ID"));//销售人员的ID
			    String buyCarBudget = map.get("BUY_CAR_BUGGET") == null ? null : CommonUtils.checkNull(map.get("BUY_CAR_BUGGET"));//购车预算
			    String brandID = map.get("BRAND_ID") == null ? null : CommonUtils.checkNull(map.get("BRAND_ID"));//品牌ID
			    String modelID = map.get("MODEL_ID") == null ? null : CommonUtils.checkNull(map.get("MODEL_ID"));//车型ID
			    String carStyleID = map.get("CAR_STYLE_ID") == null ? null : CommonUtils.checkNull(map.get("CAR_STYLE_ID"));//车款ID
			    String intentCarColor = map.get("INTENT_CAR_COLOR") == null ? null : CommonUtils.checkNull(map.get("INTENT_CAR_COLOR"));//车辆颜色ID
			    Integer buyCarcondition = CommonUtils.checkNull(map.get("BUY_CARCONDITION")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("BUY_CARCONDITION")));//购车类型
			    Date updateDate =CommonUtils.checkNull( map.get("UPDATE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("UPDATE_DATE")));//更新时间
			    String giveUpType = map.get("GIVE_UP_TYPE") == null ? null : CommonUtils.checkNull(map.get("GIVE_UP_TYPE"));//休眠类型
			    String giveUpCause = map.get("GIVE_UP_REASON") == null ? null : CommonUtils.checkNull(map.get("GIVE_UP_REASON"));//休眠原因
			    String contendCar = map.get("CONTEND_CAR") == null ? null : CommonUtils.checkNull(map.get("CONTEND_CAR"));//竞品车型
			    Date giveUpDate = CommonUtils.checkNull(map.get("GIVE_UP_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("GIVE_UP_DATE")));//休眠时间
			    Integer isToShop = CommonUtils.checkNull(map.get("IS_TO_SHOP")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("IS_TO_SHOP")));//是否到店  2016-6-17 潜客改造
			    Date timeToShop = CommonUtils.checkNull(map.get("TIME_TO_SHOP")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("TIME_TO_SHOP")));//到店时间
			    
			    dto.setCustomerId(customerId);
				dto.setBirthday(birthday);
				dto.setBrandID(brandID);
				dto.setBuyCarBudget(buyCarBudget);
				dto.setBuyCarcondition(buyCarcondition);
				dto.setCarStyleID(carStyleID);
				dto.setCityID(cityID);
				dto.setClientType(clientType);
				dto.setContendCar(contendCar);
				dto.setDealerUserID(dealerUserID);
				dto.setEntityCode(entityCode);
				dto.setFCAID(FCAID);
				dto.setGender(gender);
				dto.setGiveUpCause(giveUpCause);
				dto.setGiveUpDate(giveUpDate);
				dto.setGiveUpType(giveUpType);
				dto.setIntentCarColor(intentCarColor);
				dto.setIsToShop(isToShop);
				dto.setModelID(modelID);
				dto.setName(name);
				dto.setOppLevelID(oppLevelID);
				dto.setPhone(phone);
				dto.setProvinceID(provinceID);
				dto.setSecondSourceType(secondSourceType);
				dto.setSourceType(sourceType);
				dto.setTelephone(telephone);
				dto.setTimeToShop(timeToShop);
				dto.setUniquenessID(uniquenessID);
				dto.setUpdateDate(updateDate);
				dto.setDealerCode(dealerCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	private StringBuffer getSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT ANCI.CUSTOMER_ID, \n");
		sql.append("       ANCI.FCA_ID, \n");
		sql.append("       ANCI.UNIQUENESS_ID, \n");
		sql.append("       ANCI.CLIENT_TYPE, \n");
		sql.append("       ANCI.NAME, \n");
		sql.append("       ANCI.GENDER, \n");
		sql.append("       ANCI.PHOME, \n");
		sql.append("       ANCI.TELEPHONE, \n");
		sql.append("       (SELECT R.REGION_CODE FROM TM_REGION R WHERE R.REGION_ID = ANCI.PROVINCE_ID LIMIT 1 ) AS PROVINCE_ID, \n");
		sql.append("       (SELECT TR.REGION_CODE FROM TM_REGION TR WHERE TR.REGION_ID = ANCI.CITY_ID LIMIT 1 ) AS CITY_ID, \n");
		sql.append("       IFNULL(DATE_FORMAT(ANCI.BIRTHDAY, '%Y-%m-%d %H:%i:%S'), '') AS BIRTHDAY, \n");
		sql.append("       ANCI.OPP_LEVEL_ID, \n");
		sql.append("       ANCI.SOURCE_TYPE, \n");
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n");
		sql.append("       ANCI.DEALER_USER_ID, \n");
		sql.append("       ANCI.BUY_CAR_BUGGET, \n");
		sql.append("       VMG_BRAND.GROUP_CODE AS BRAND_ID, \n");
		sql.append("       VMG_MODEL.GROUP_CODE AS MODEL_ID, \n");
		sql.append("       VMG_CARSTYLE.GROUP_CODE AS CAR_STYLE_ID, \n");
		sql.append("       ANCI.INTENT_CAR_COLOR, \n");
		sql.append("       ANCI.BUY_CARCONDITION, \n");
		sql.append("       ANCI.GIVE_UP_TYPE, \n");
		sql.append("       ANCI.GIVE_UP_REASON, \n");
		sql.append("       ANCI.CONTEND_CAR, \n");
		sql.append("       ANCI.SECOND_SOURCE_TYPE, \n");
		sql.append("       IFNULL(DATE_FORMAT(ANCI.GIVE_UP_DATE, '%Y-%m-%d %H:%i:%S'), '') AS GIVE_UP_DATE, \n");
		sql.append("       IFNULL(DATE_FORMAT(ANCI.UPDATE_DATE, '%Y-%m-%d %H:%i:%S'), '') AS UPDATE_DATE, \n");
		sql.append("       ANCI.IS_TO_SHOP, \n");
		sql.append("       IFNULL(DATE_FORMAT(ANCI.TIME_TO_SHOP, '%Y-%m-%d %H:%i:%S'), '') AS TIME_TO_SHOP \n");
		sql.append("  FROM TI_APP_U_CUSTOMER_INFO ANCI \n");
		sql.append("  LEFT JOIN TI_DEALER_RELATION DR ON ANCI.DEALER_CODE = DR.DCS_CODE \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_BRAND ON ANCI.BRAND_ID = VMG_BRAND.TREE_CODE \n");
		sql.append("   AND VMG_BRAND.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_BRAND.GROUP_LEVEL = 1 \n");
		sql.append("   AND VMG_BRAND.IS_DEL = 0 \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_MODEL ON ANCI.MODEL_ID = VMG_MODEL.TREE_CODE \n");
		sql.append("   AND VMG_MODEL.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_MODEL.GROUP_LEVEL = 2 \n");
		sql.append("   AND VMG_MODEL.IS_DEL = 0 \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP VMG_CARSTYLE ON ANCI.CAR_STYLE_ID = VMG_CARSTYLE.TREE_CODE \n");
		sql.append("   AND VMG_CARSTYLE.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND VMG_CARSTYLE.GROUP_LEVEL = 4 \n");
		sql.append("   AND VMG_CARSTYLE.IS_DEL = 0 \n");
		sql.append(" WHERE ANCI.IS_SEND = '0' OR ANCI.IS_SEND IS NULL OR ANCI.IS_SEND = '9' \n");
		sql.append("   AND DR.DMS_CODE IS NOT NULL \n");
		return sql;
	}

}
