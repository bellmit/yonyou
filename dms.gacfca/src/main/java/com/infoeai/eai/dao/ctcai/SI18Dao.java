package com.infoeai.eai.dao.ctcai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.DTO.Dcs2Jec01DTO;
import com.infoeai.eai.DTO.Dcs2Jec02DTO;
import com.infoeai.eai.DTO.Dcs2Jec03DTO;
import com.infoeai.eai.DTO.JECAddNVhclMaintainDTO;
import com.infoeai.eai.DTO.JECCarDTO;
import com.infoeai.eai.DTO.JECMaintainDTO;
import com.infoeai.eai.DTO.JECRecommendDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 新车信息导入
 * @author luoyang
 *
 */
@Repository
public class SI18Dao extends OemBaseDAO {

	/**
	 * 功能说明:新车信息导入
	 * @return
	 */
	public List<Dcs2Jec01DTO> getSI18Info() {
		StringBuffer sql = new StringBuffer("");		
		sql.append("  SELECT tmv.SEQUENCE_ID,tmv.TRUNKCODE,tmv.MODEL,tmv.STYLE, tmv.BUYDEALER\n");
		sql.append("    FROM TI_VEHICLES_JEC_DCS tmv \n");
		sql.append("   WHERE 1=1 \n");
		sql.append("     AND tmv.IS_SCAN = '0' \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setDcs2Jec01DTOList(mapList);
	}

	private List<Dcs2Jec01DTO> setDcs2Jec01DTOList(List<Map> mapList) {
		List<Dcs2Jec01DTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				Dcs2Jec01DTO dto = new Dcs2Jec01DTO();
				String sequenceId = CommonUtils.checkNull(map.get("SEQUENCE_ID")); // 序列号
				String trunkCode = CommonUtils.checkNull(map.get("TRUNKCODE")); // VIN码
				String model = CommonUtils.checkNull(map.get("MODEL")); // 车型
				String style = CommonUtils.checkNull(map.get("STYLE")); // 车款
				String buyDealer = CommonUtils.checkNull(map.get("BUYDEALER")); // 车系
				
				dto.setBuyDealer(buyDealer);
				dto.setModel(model);
				dto.setSequenceId(sequenceId);
				dto.setStyle(style);
				dto.setTrunkCode(trunkCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	public List<Dcs2Jec02DTO> getSI19Info() {
		StringBuffer sql = new StringBuffer("");	
		sql.append("  SELECT cust.SEQUENCE_ID, \n");
		sql.append("         cust.CODE, \n");
		sql.append("         cust.NAME, \n");
		sql.append("         cust.ID_OR_COMPCODE, \n");
		sql.append("         cust.EMAIL, \n");
		sql.append("         case cust.GENDER when '10061001' then '1' when '10061002' then '2' else '1' end as GENDER, \n");
		sql.append("         (select region_id from TM_REGION_DCS where region_code = cust.PROVINCE ) PROVINCE, \n");
		sql.append("         (select region_id from TM_REGION_DCS where region_code = cust.CITY ) CITY, \n");
		sql.append("         cust.ADDRESS, \n");
		sql.append("         cust.POST_CODE, \n");
		sql.append("         cust.CELL_PHONE, \n");
		sql.append("         cust.DIRVE_AGE, \n");
		sql.append("         cust.HOBBY, \n");
		sql.append("         '1' ACCEPT_POST \n");
		sql.append("    FROM TI_SALES_JEC_CUSTOMER cust \n");
		sql.append("   WHERE 1 = 1 AND cust.IS_SCAN = '0' \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setDcs2Jec02DTOList(mapList);
	}

	private List<Dcs2Jec02DTO> setDcs2Jec02DTOList(List<Map> mapList) {
		List<Dcs2Jec02DTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				Dcs2Jec02DTO dto = new Dcs2Jec02DTO();
				Long sequenceId = Long.parseLong(CommonUtils.checkNull(map.get("SEQUENCE_ID"))); // 客户唯一ID
				String code = CommonUtils.checkNull(map.get("CODE")); // 客户唯一ID
				String name = CommonUtils.checkNull(map.get("NAME")); // 车主姓名/企业名称
				String idOrCompCode = CommonUtils.checkNull(map.get("ID_OR_COMPCODE")); // 身份证号码/企业代码
				String email = CommonUtils.checkNull(map.get("EMAIL")); // 邮箱地址
				String gender = CommonUtils.checkNull(map.get("GENDER")); // 性别
				String province = CommonUtils.checkNull(map.get("PROVINCE")); // 省/直辖市
				String city = CommonUtils.checkNull(map.get("CITY")); // 市/区
				String address = CommonUtils.checkNull(map.get("ADDRESS")); // 通讯地址
				String postCode = CommonUtils.checkNull(map.get("POST_CODE")); // 邮政编码
				String cellPhone = CommonUtils.checkNull(map.get("CELL_PHONE")); // 手机
				String driveAge = CommonUtils.checkNull(map.get("DIRVE_AGE")); // 驾龄
				String hobby = CommonUtils.checkNull(map.get("HOBBY")); // 兴趣爱好
				String acceptPost = CommonUtils.checkNull(map.get("ACCEPT_POST")); // 请勿打扰
				
				dto.setAcceptPost(acceptPost);
				dto.setAddress(address);
				dto.setCellPhone(cellPhone);
				dto.setCity(city);
				dto.setCode(code);
				dto.setDriveAge(driveAge);
				dto.setEmail(email);
				dto.setGender(gender);
				dto.setHobby(hobby);
				dto.setIdOrCompCode(idOrCompCode);
				dto.setName(name);
				dto.setPostCode(postCode);
				dto.setProvince(province);
				dto.setSequenceId(sequenceId);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	public List<JECCarDTO> getVehiclesByCustInfo(Long jecCustId) {
		StringBuffer sql = new StringBuffer("");		
		sql.append("  SELECT vehl.BUY_TIME, \n");
		sql.append("         (select region_id from TM_REGION_DCS where region_code = vehl.BUY_PROVINCE ) BUY_PROVINCE, \n");
		sql.append("         (select region_id from TM_REGION_DCS where region_code = vehl.BUY_CITY ) BUY_CITY, \n");
		sql.append("         vehl.TRUNK_CODE, \n");
		sql.append("         TMC.JEC_CODE BUY_DEALER, \n");
		sql.append("         vehl.MODEL, \n");
		sql.append("         vehl.STYLE, \n");
		sql.append("         vehl.COLOR, \n");
		sql.append("         vehl.MOTOR_CODE, \n");
		sql.append("         case vehl.CAR_CODE when '无牌照' then '' else vehl.CAR_CODE end as CAR_CODE, \n");
		sql.append("         vehl.IS_NEW_CAR, \n");
		sql.append("         FLOOR(vehl.DELIVER_MILEAGE) DELIVER_MILEAGE, \n");
		sql.append("         vehl.BUY_STATUS \n");
		sql.append("    FROM TI_SALES_JEC_VEHICLE vehl,TM_DEALER tmd,TM_COMPANY tmc \n");
		sql.append("   WHERE 1 = 1 \n");
		sql.append("     AND TMD.DEALER_ID = VEHL.BUY_DEALER \n");
		sql.append("     AND TMC.COMPANY_ID = TMD.COMPANY_ID \n");
		sql.append("     AND vehl.JEC_CUSTOMER_ID = "+jecCustId+" \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setJECCarDTOList(mapList);
	}

	private List<JECCarDTO> setJECCarDTOList(List<Map> mapList) {
		List<JECCarDTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				JECCarDTO dto = new JECCarDTO();
				String buyTime = CommonUtils.checkNull(map.get("BUY_TIME")); // 购车时间
				String buyProvince = CommonUtils.checkNull(map.get("BUY_PROVINCE")); // 购车省/直辖市
				String buyCity = CommonUtils.checkNull(map.get("BUY_CITY")); // 购车市/区
				String trunkCode = CommonUtils.checkNull(map.get("TRUNK_CODE")); // 车架号
				String buyDealer = CommonUtils.checkNull(map.get("BUY_DEALER")); // 购车经销商
				String model = CommonUtils.checkNull(map.get("MODEL")); // 购买车型
				String style = CommonUtils.checkNull(map.get("STYLE")); // 车款
				String color = CommonUtils.checkNull(map.get("COLOR")); // 颜色
				String motorCode = CommonUtils.checkNull(map.get("MOTOR_CODE")); // 发动机编码
				String carCode = CommonUtils.checkNull(map.get("CAR_CODE")); // 牌照号
				String isNewCar = CommonUtils.checkNull(map.get("IS_NEW_CAR")); // 新车/二手车
				String deliverMileage = CommonUtils.checkNull(map.get("DELIVER_MILEAGE")); // 交付里程表数
				String buyStatus = CommonUtils.checkNull(map.get("BUY_STATUS")); // 购车状态
				
				dto.setBuyCity(buyCity);
				dto.setBuyDealer(buyDealer);
				dto.setBuyProvince(buyProvince);
				dto.setBuyStatus(buyStatus);
				dto.setBuyTime(buyTime);
				dto.setCarCode(carCode);
				dto.setColor(color);
				dto.setDeliverMileage(deliverMileage);
				dto.setIsNewCar(isNewCar);
				dto.setModel(model);
				dto.setMotorCode(motorCode);
				dto.setStyle(style);
				dto.setTrunkCode(trunkCode);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	public List<JECAddNVhclMaintainDTO> getMaintainsByCustInfo(Long jecCustId) {
		StringBuffer sql = new StringBuffer("");		
		sql.append("  SELECT main.DMS_MAPPING_ID,main.TRUNK_CODE,main.DDC_CORPORATION,\n");
		sql.append("         main.USED_CAR_REG_DATE,main.MODEL_YEAR,main.CARE_INTERVAL,main.CARE_INTERVAL_IN_MONTH,\n");
		sql.append("         main.LAST_MILEAGE,main.WARRANTY_END_DATE,main.WARRANTY_STATE,main.EXTEND_PROTEC_START_DATE,\n");
		sql.append("         main.EXTEND_PROTEC_IN_MONTH,main.EXTEND_PROTECT_END_DATE,main.LAST_CARE_DATE,main.NEXT_PLAN_MAINTAI,\n");
		sql.append("         main.LAST_MAINTAIN_DATE,main.LAST_YEARLY_CHECK_DATE,main.YEARLY_CHECK_EXP_DATE \n");
		sql.append("    FROM TI_SALES_JEC_VEHICLE vehl,TI_SALES_JEC_VEHICLE_MAINTAIN_DCS main \n");
		sql.append("   WHERE main.TRUNK_CODE = vehl.TRUNK_CODE \n");
		sql.append("     AND vehl.JEC_CUSTOMER_ID = "+jecCustId+" \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setJECAddNVhclMaintainDTOList(mapList);
	}

	private List<JECAddNVhclMaintainDTO> setJECAddNVhclMaintainDTOList(List<Map> mapList) {
		List<JECAddNVhclMaintainDTO> resultList = new ArrayList<>();
		for(Map map : mapList){
			JECAddNVhclMaintainDTO dto = new JECAddNVhclMaintainDTO();
			String dmsMappingId = CommonUtils.checkNull(map.get("DMS_MAPPING_ID")); // 客户唯一ID
			String trunkCode = CommonUtils.checkNull(map.get("TRUNK_CODE"));//车架号
			String ddCorporation = CommonUtils.checkNull(map.get("DDC_CORPORATION")); // DD公司
			String usedCarRegDate = CommonUtils.checkNull(map.get("USED_CAR_REG_DATE")); // 二手车登记日期
			String modelYear = CommonUtils.checkNull(map.get("MODEL_YEAR")); // 车型年款
			String carelnterval = CommonUtils.checkNull(map.get("CARE_INTERVAL")); // 保养间隔
			String carelntervallnMonth = CommonUtils.checkNull(map.get("CARE_INTERVAL_IN_MONTH")); // 保养间隔（月数）
			String lastMileage = CommonUtils.checkNull(map.get("LAST_MILEAGE")); // 上次里程数
			String warrantyEndDate = CommonUtils.checkNull(map.get("WARRANTY_END_DATE")); // 制造商质保结束日期
			String warrantyState = CommonUtils.checkNull(map.get("WARRANTY_STATE")); // 制造商质保状态
			String extendProtectInMonth = CommonUtils.checkNull(map.get("EXTEND_PROTEC_IN_MONTH")); // 延保开始时期（月）
			String extendProtectEndDate = CommonUtils.checkNull(map.get("EXTEND_PROTECT_END_DATE")); // 延保结束日期
			String lastCareDate = CommonUtils.checkNull(map.get("LAST_CARE_DATE")); // 车辆历史记录上次保养日期
			String nextPlanMaintai = CommonUtils.checkNull(map.get("NEXT_PLAN_MAINTAI")); // 车辆历史记录预计下次维修
			String lastMaintainDate = CommonUtils.checkNull(map.get("LAST_MAINTAIN_DATE")); // 上一个维修日期
			String lastYearlyCheckDate = CommonUtils.checkNull(map.get("LAST_YEARLY_CHECK_DATE")); // 车辆历史记录上一次年检日期
			String yearlyCheckExpDate = CommonUtils.checkNull(map.get("YEARLY_CHECK_EXP_DATE")); // 车辆历史记录年检过期
			
			dto.setCarelnterval(carelnterval);
			dto.setCarelntervallnMonth(carelntervallnMonth);
			dto.setDdCorporation(ddCorporation);
			dto.setDmsMappingId(dmsMappingId);
			dto.setExtendProtectEndDate(extendProtectEndDate);
			dto.setExtendProtectInMonth(extendProtectInMonth);
			dto.setLastCareDate(lastCareDate);
			dto.setLastMaintainDate(lastMaintainDate);
			dto.setLastMileage(lastMileage);
			dto.setLastYearlyCheckDate(lastYearlyCheckDate);
			dto.setModelYear(modelYear);
			dto.setNextPlanMaintai(nextPlanMaintai);
			dto.setTrunkCode(trunkCode);
			dto.setUsedCarRegDate(usedCarRegDate);
			dto.setWarrantyEndDate(warrantyEndDate);
			dto.setWarrantyState(warrantyState);
			dto.setYearlyCheckExpDate(yearlyCheckExpDate);
			resultList.add(dto);
		}
		return resultList;
	}
	/**
	 * 功能说明:车辆维修信息查询_查询符合条件客户信息
	 * @return
	 * @throws Exception 
	 */
	public List<Dcs2Jec03DTO> getSI20Info() throws Exception {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tmv.SEQUENCE_ID,tmv.CODE \n");
		sql.append("    FROM TI_SERVICE_JEC_CUSTOMER_DCS tmv \n");
		sql.append("   WHERE 1=1 \n");
		sql.append("     AND tmv.IS_SCAN = '0' \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<Dcs2Jec03DTO> list = null;
		if(null!=mapList&&mapList.size()>0){
			list = new ArrayList<>();
			for(Map map : mapList){
				Dcs2Jec03DTO dto=new Dcs2Jec03DTO();
				dto.setSequenceId(Utility.getLong(CommonUtils.checkNull(map.get("SEQUENCE_ID"))));
				dto.setCode(CommonUtils.checkNull(map.get("CODE")));
				list.add(dto);
			}
		}
		return list;
	}
	/**
	 * 功能说明:车辆维修信息查询_根据客户信息查找所有推荐客户信息
	 * 创建人: zhangRM 
	 * 创建日期: 2013-06-17
	 * @return
	 */
	public List<JECRecommendDTO> getIntroduceInfo(Long codeId) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tvm.DMS_MAPPING_ID,tvm.RECOMMEND_DATE,tvm.RECOMMENDEE_NAME,tvm.RECOMMEND_MODEL \n");
		sql.append("    FROM TI_JEC_INTRODUCE_CUSTOMER_DCS tvm \n");
		sql.append("   WHERE 1=1 \n");
		sql.append("     AND tvm.CODE_ID = "+codeId+" \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<JECRecommendDTO> list = null;
		if(null!=mapList&&mapList.size()>0){
			list = new ArrayList<>();
			for(Map map : mapList){
				JECRecommendDTO dto=new JECRecommendDTO();
				dto.setDmsMappingId(CommonUtils.checkNull(map.get("DMS_MAPPING_ID")));
				dto.setRecommendDate(CommonUtils.checkNull(map.get("RECOMMEND_DATE")));
				dto.setRecommendeeName(CommonUtils.checkNull(map.get("RECOMMENDEE_NAME")));
				dto.setRecommendModel(CommonUtils.checkNull(map.get("RECOMMEND_MODEL")));
				list.add(dto);
			}
		}
		return list;
	}
	/**
	 * 功能说明:车辆维修信息查询_根据客户信息查找所有维修信息
	 * 创建人: zhangRM 
	 * 创建日期: 2013-06-17
	 * @return
	 */
	public List<JECMaintainDTO> getMaintainsInfo(Long codeId) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tvm.TRUNK_CODE,tvm.USED_CAR_REG_DATE,tvm.CARE_INTERVAL,tvm.CARE_INTERVAL_IN_MONTH, \n");
		sql.append("         FLOOR(tvm.LAST_MILEAGE) LAST_MILEAGE,tvm.WARRANTY_END_DATE,tvm.WARRANTY_STATE,tvm.EXTEND_PROTEC_START_DATE, \n");
		sql.append("         tvm.EXTEND_PROTECT_IN_MONTH,tvm.EXTEND_PROTECT_END_DATE,tvm.LAST_CARE_DATE,tvm.NEXT_PLAN_MAINTAI, \n");
		sql.append("         tvm.LAST_YEARLY_CHECK_DATE,tvm.YEARLY_CHECK_EXP_DATE,'' BUY_STATUS \n");
		sql.append("    FROM TI_SERVICE_JEC_MAINTAIN_DCS tvm \n");
		sql.append("   WHERE 1=1 \n");
		sql.append("     AND tvm.CODE_ID = "+codeId+" \n");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		List<JECMaintainDTO> list = null;
		if(null!=mapList&&mapList.size()>0){
			list = new ArrayList<>();
			for(Map map : mapList){
				JECMaintainDTO dto=new JECMaintainDTO();
				dto.setTrunkCode(CommonUtils.checkNull(map.get("TRUNK_CODE")));
				dto.setUsedCarRegDate(CommonUtils.checkNull(map.get("USED_CAR_REG_DATE")));
				dto.setCareInterval(CommonUtils.checkNull(map.get("CARE_INTERVAL")));
				dto.setCareIntervalInMonth(CommonUtils.checkNull(map.get("CARE_INTERVAL_IN_MONTH")));
				dto.setLastMileage(CommonUtils.checkNull(map.get("LAST_MILEAGE")));
				dto.setWarrantyEndDate(CommonUtils.checkNull(map.get("WARRANTY_END_DATE")));
				dto.setWarrantyState(CommonUtils.checkNull(map.get("WARRANTY_STATE")));
				dto.setExtendProtecStartDate(CommonUtils.checkNull(map.get("EXTEND_PROTEC_START_DATE")));
				dto.setExtendProtectInMonth(CommonUtils.checkNull(map.get("EXTEND_PROTECT_IN_MONTH")));
				dto.setExtendProtectEndDate(CommonUtils.checkNull(map.get("EXTEND_PROTECT_END_DATE")));
				dto.setLastCareDate(CommonUtils.checkNull(map.get("LAST_CARE_DATE")));
				dto.setNextPlanMaintai(CommonUtils.checkNull(map.get("NEXT_PLAN_MAINTAI")));
				dto.setLastYearlyCheckDate(CommonUtils.checkNull(map.get("LAST_YEARLY_CHECK_DATE")));
				dto.setYearlyCheckExpDate(CommonUtils.checkNull(map.get("YEARLY_CHECK_EXP_DATE")));
				dto.setBuystatus(CommonUtils.checkNull(map.get("BUY_STATUS")));
				
				list.add(dto);
			}
		}
		return list;
	}
}
