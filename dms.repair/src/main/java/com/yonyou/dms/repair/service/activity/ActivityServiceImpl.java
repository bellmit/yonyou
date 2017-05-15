package com.yonyou.dms.repair.service.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.common.GfkDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityAddDTO;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityDTO;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityLabourDTO;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityModelDTO;
import com.yonyou.dms.repair.domains.DTO.activity.ActivityPartDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityAddPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPartPO;

@Service
public class ActivityServiceImpl implements ActivityService {
	
	/**
	 * 查询
	 * 
	 * @author wantao
	 * @date 2017年4月24日
	 * @param param
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.repair.service.activity.ActivityService#search(java.util.Map)
	 */
	@Override
	public PageInfoDto search(Map<String, String> param) throws ServiceBizException {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT t.DEALER_CODE,t.ACTIVITY_CODE,t.ACTIVITY_NAME,t.GLOBAL_ACTIVITY_CODE,t.ACTIVITY_TITLE,");
		sb.append("t.ACTIVITY_TYPE,t.ACTIVITY_KIND,t.ACTIVITY_PROPERTY,t.BEGIN_DATE,t.END_DATE,t.RELEASE_DATE,t.RELEASE_TAG,");
		sb.append("t.LABOUR_AMOUNT,t.REPAIR_PART_AMOUNT,t.ACTIVITY_AMOUNT,");
		sb.append("t.FROM_ENTITY,t.IS_REPEAT_ATTEND   ");
		sb.append("from tt_activity t where 1=1 ");
		if (!StringUtils.isNullOrEmpty(param.get("activityCode"))) {
			sb.append("and t.ACTIVITY_CODE like ?  ");
			queryParam.add("%" + param.get("activityCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(param.get("releaseTag"))) {
			sb.append("and t.RELEASE_TAG=?  ");
			queryParam.add(param.get("releaseTag"));
		}
		if (!StringUtils.isNullOrEmpty(param.get("beginDateFrom"))) {
			sb.append("and t.BEGIN_DATE >= ?  ");
			queryParam.add(param.get("beginDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(param.get("beginDateTo"))) {
			sb.append("and t.BEGIN_DATE < ?  ");
			queryParam.add(DateUtil.addOneDay(param.get("beginDateTo")));
		}
		if (!StringUtils.isNullOrEmpty(param.get("activityName"))) {
			sb.append("and t.ACTIVITY_NAME like ?  ");
			queryParam.add("%" + param.get("activityName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(param.get("endDateFrom"))) {
			sb.append("and t.END_DATE >= ?  ");
			queryParam.add(param.get("endDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(param.get("endDateTo"))) {
			sb.append("and t.END_DATE < ?  ");
			queryParam.add(DateUtil.addOneDay(param.get("endDateTo")));
		}
		return DAOUtil.pageQuery(sb.toString(), queryParam);
	}
	
	/**
	 * 新增服务活动
	 * @author wantao
	 * @date 2017年4月24日
	 * @param activityDto
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.repair.service.activity.ActivityService#addActivity(com.yonyou.dms.repair.domains.DTO.activity.ActivityDTO)
	 */
	@Override
	public String[] addActivity(ActivityDTO activityDto) throws ServiceBizException {
		if (findByActivityCode(activityDto.getActivityCode(), null)) {
			throw new ServiceBizException("该活动编号已经存在");
		}
		int compareDate = activityDto.getBeginDate().compareTo(activityDto.getEndDate());
		if (compareDate == 1) {
			throw new ServiceBizException("结束时间不能小于开始时间");
		}
		int compareDate2 = activityDto.getEndDate().compareTo(DateUtil.truncDate(new Date()));
		if (compareDate2 == -1) {
			throw new ServiceBizException("结束时间必须大于或等于当前时间");
		}
		TtActivityPO activityPo = new TtActivityPO();
		setActivityPO(activityPo, activityDto);
		activityPo.saveIt();
		// 新增维修项目、配件、附加项目、车型
		addActivityItem(activityPo, activityDto);
		return activityPo.getCompositeKeys();
		//return activityPo.getString("ACTIVITY_CODE");
	}
	
	/**
	 * 查询是否存在活动编号
	 * 
	 * @author wantao
	 * @date 2017年4月24日
	 * @return
	 */
	private boolean findByActivityCode(String activityCode, Long id) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sb = new StringBuilder(
				"SELECT t.ACTIVITY_CODE,t.DEALER_CODE from tt_activity t where t.ACTIVITY_CODE=? and t.DEALER_CODE=? ");
		param.add(activityCode);
		param.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> map = DAOUtil.findAll(sb.toString(), param);
		if (map.size() == 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 注入ActivityPO属性
	 * 
	 * @author jcsi
	 * @date 2016年12月22日
	 * @param activityPo
	 * @param ActivityDto
	 */
	private void setActivityPO(TtActivityPO activityPo,ActivityDTO ActivityDto) {
		activityPo.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
		activityPo.setString("ACTIVITY_CODE", ActivityDto.getActivityCode()); // 活动编号
		activityPo.setString("ACTIVITY_NAME", ActivityDto.getActivityName()); // 活动名称
		activityPo.setLong("ACTIVITY_TYPE", GfkDictCodeConstants.ACTIVITY_TYPE); // 活动类型
		activityPo.setLong("ACTIVITY_KIND", ActivityDto.getActivityKind()); // 活动类别
		activityPo.setDate("BEGIN_DATE", ActivityDto.getBeginDate()); // 开始日期
		activityPo.setDate("END_DATE", ActivityDto.getEndDate()); // 结束日期
		activityPo.setDate("RELEASE_DATE", ActivityDto.getReleaseDate()); // 发布时间
		activityPo.setLong("RELEASE_TAG", GfkDictCodeConstants.RELEASE_TAG_NOT); // 发布状态
		Map<String, Object> amountMap = getActivityAmount(ActivityDto);
		activityPo.setDouble("LABOUR_AMOUNT", amountMap.get("labourAmount")); // 工时费
		activityPo.setDouble("ADD_ITEM_AMOUNT", amountMap.get("addAmount")); // 附加项目费
		activityPo.setDouble("REPAIR_PART_AMOUNT", amountMap.get("partAmount")); // 维修材料费
		activityPo.setDouble("ACTIVITY_AMOUNT", amountMap.get("activityAmount")); // 活动总金额
		activityPo.setString("FROM_ENTITY", GfkDictCodeConstants.ACTIVITY_FROM_THIS); // 数据来源
		activityPo.setLong("IS_VALID", DictCodeConstants.STATUS_IS_YES); // 是否有效
		activityPo.setString("LAUNCH_ENTITY", ActivityDto.getLaunchEntity()); // 发起机构
		activityPo.setString("LAUNCH_ENTITY_NAME", ActivityDto.getLaunchEntityName()); // 发起机构名称
		activityPo.setDate("SALES_DATE_BEGIN", ActivityDto.getSalesDateBegin()); // 销售开始日期
		activityPo.setDate("SALES_DATE_END", ActivityDto.getSalesDateEnd()); // 销售结束日期
		activityPo.setDouble("MILEAGE_BEGIN", ActivityDto.getMileageBegin()); // 行程开始里程
		activityPo.setDouble("MILEAGE_END", ActivityDto.getMileageEnd()); // 行程结束里程
		activityPo.setLong("IS_REPEAT_ATTEND", ActivityDto.getIsRepeatAttend()); // 本活动可以多次参加
		activityPo.setLong("VEHICLE_PURPOSE", ActivityDto.getVehiclePurpose()); // 车辆用途
		activityPo.setInteger("ACTIVITY_SUM_COUNT", ActivityDto.getActivitySumCount()); // 活动总台次
		activityPo.setInteger("ACTIVITY_COUNT", new Integer(1)); // 单台能参加活动总次数
		activityPo.setString("SHARE_ENTITY_CODE", ActivityDto.getShareEntityCode()); // 共享ENTITY_CODE
		activityPo.setString("SHARE_ACTIVITY_CODE", ActivityDto.getShareActivityCode()); // 共享活动代码
		activityPo.setString("REMARK", ActivityDto.getRemark());
		activityPo.setLong("ACTIVITY_PROPERTY", ActivityDto.getActivityProperty()); // 活动性质;
		activityPo.setString("GLOBAL_ACTIVITY_CODE", ActivityDto.getGlobalActivityCode()); // 全球活动编号
		activityPo.setString("ACTIVITY_TITLE", ActivityDto.getActivityTitle()); // 全球活动主题
	}
	
	/**
	 * 计算活动总金额
	 * 
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityDto
	 * @return
	 */
	public Map<String, Object> getActivityAmount(ActivityDTO activityDto) {
		Map<String, Object> amountMap = new HashMap<String, Object>();
		Double activityAmount = new Double("0");
		Double activityLabourAmount = new Double("0"); // 维修项目费
		Double activityPartAmount = new Double("0"); // 维修材料费
		Double activityAddAmount = new Double("0"); // 附加项目费
		if (!CommonUtils.isNullOrEmpty(activityDto.getLabourList())) {
			for (ActivityLabourDTO activityLabourDto : activityDto.getLabourList()) {
				Double labourAmount = NumberUtil.mul2Double(activityLabourDto.getStdLabourHour(),activityLabourDto.getLabourPrice());
				Double disLabourAmount = NumberUtil.mul2Double(labourAmount, activityLabourDto.getDiscount());
				activityAmount = NumberUtil.add2Double(disLabourAmount, activityAmount);
				activityLabourAmount = NumberUtil.add2Double(activityLabourAmount, disLabourAmount);
			}
		}
		amountMap.put("labourAmount", activityLabourAmount);
		if (!CommonUtils.isNullOrEmpty(activityDto.getPartList())) {
			// 新增维修配件
			for (ActivityPartDTO activityPartDto : activityDto.getPartList()) {
				Double partSalesPrice = NumberUtil.mul2Double(activityPartDto.getPartSalesPrice(),activityPartDto.getPartQuantity());
				Double disPartSalesPrice = NumberUtil.mul2Double(partSalesPrice, activityPartDto.getDiscount());
				activityAmount = NumberUtil.add2Double(disPartSalesPrice, activityAmount);
				activityPartAmount = NumberUtil.add2Double(activityPartAmount, disPartSalesPrice);
			}
		}
		amountMap.put("partAmount", activityPartAmount);
		if (!CommonUtils.isNullOrEmpty(activityDto.getSubjoinList())) {
			// 新增附加项目
			for (ActivityAddDTO activityAddDto : activityDto.getSubjoinList()) {
				Double disAddItemAmount = NumberUtil.mul2Double(activityAddDto.getAddItemAmount(),
						activityAddDto.getDiscount());
				activityAmount = NumberUtil.add2Double(disAddItemAmount, activityAmount);
				activityAddAmount = NumberUtil.add2Double(activityAddAmount, disAddItemAmount);
			}
		}
		amountMap.put("addAmount", activityAddAmount);
		amountMap.put("activityAmount", activityAmount);
		return amountMap;
	}
	
	/**
	 * 
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityDto
	 */
	private void addActivityItem(TtActivityPO activityPo, ActivityDTO activityDto) throws ServiceBizException {
		// 新增维修项目
		if (!CommonUtils.isNullOrEmpty(activityDto.getLabourList())) {
			for (ActivityLabourDTO activityLabourDto : activityDto.getLabourList()) {
				TtActivityLabourPO activityLabourPo = new TtActivityLabourPO();
				setActivityLabourPO(activityLabourPo, activityLabourDto);
				activityLabourPo.setString("ACTIVITY_CODE", activityDto.getActivityCode()); // 活动编号
				activityLabourPo.setString("DEALER_CODE",activityPo.getString("DEALER_CODE"));
				activityLabourPo.setString("ACTIVITY_CODE",activityPo.getString("ACTIVITY_CODE"));
				activityLabourPo.saveIt();
			}
		}
		// 新增维修配件
		if (!CommonUtils.isNullOrEmpty(activityDto.getPartList())) {
			for (ActivityPartDTO activityPartDto : activityDto.getPartList()) {
				TtActivityPartPO activityPartPo = new TtActivityPartPO();
				setActivityPartPO(activityPartPo, activityPartDto);
				activityPartPo.setString("ACTIVITY_CODE", activityDto.getActivityCode()); // 活动编号
				activityPartPo.setString("DEALER_CODE",activityPo.getString("DEALER_CODE"));
				activityPartPo.setString("ACTIVITY_CODE",activityPo.getString("ACTIVITY_CODE"));
				activityPartPo.saveIt();
			}
		}
		// 新增附加项目
		if (!CommonUtils.isNullOrEmpty(activityDto.getSubjoinList())) {
			for (ActivityAddDTO activityAddDto : activityDto.getSubjoinList()) {
				TtActivityAddPO activityAddPo = new TtActivityAddPO();
				setActivityAddPO(activityAddPo,activityAddDto);
				activityAddPo.setString("ACTIVITY_CODE", activityDto.getActivityCode()); // 活动编号
				activityAddPo.setString("DEALER_CODE",activityPo.getString("DEALER_CODE"));
				activityAddPo.setString("ACTIVITY_CODE",activityPo.getString("ACTIVITY_CODE"));
				activityAddPo.saveIt();
			}
		}
		// 新增活动车型
		if (!CommonUtils.isNullOrEmpty(activityDto.getModelList())) {
			for (ActivityModelDTO activityModelDto : activityDto.getModelList()) {
				TtActivityModelPO activityModelPo = new TtActivityModelPO();
				setActivityModelPO(activityModelPo, activityModelDto);
				activityModelPo.setString("ACTIVITY_CODE", activityDto.getActivityCode()); // 活动编号
				activityModelPo.setString("DEALER_CODE",activityPo.getString("DEALER_CODE"));
				activityModelPo.setString("ACTIVITY_CODE",activityPo.getString("ACTIVITY_CODE"));
				activityModelPo.saveIt();
			}
		}
	}
	
	/**
	 * 注入ActivityLabourPO属性
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityLabourPo
	 * @param activityLabourDto
	 */
	public void setActivityLabourPO(TtActivityLabourPO activityLabourPo, ActivityLabourDTO activityLabourDto) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t.LABOUR_CODE,t.LABOUR_NAME,MODEL_LABOUR_CODE,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR,REPAIR_TYPE_CODE,t.DEALER_CODE ");
		sb.append("from tm_labour t where t.LABOUR_ID=? ");
		List<Object> param = new ArrayList<Object>();
		param.add(activityLabourDto.getLabourId());
		Map LabourMap = DAOUtil.findFirst(sb.toString(),param);

		activityLabourPo.setString("ACTIVITY_CODE",activityLabourDto.getActivityCode()); // 活动编号
		activityLabourPo.setString("LABOUR_CODE", LabourMap.get("LABOUR_CODE")); // 维修项目代码
		activityLabourPo.setString("LABOUR_NAME", LabourMap.get("LABOUR_NAME")); // 维修项目名称
		activityLabourPo.setString("MODEL_LABOUR_CODE", LabourMap.get("MODEL_LABOUR_CODE")); // 维修车型分组代码
		activityLabourPo.setDouble("STD_LABOUR_HOUR", LabourMap.get("STD_LABOUR_HOUR")); // 标准工时
		activityLabourPo.setDouble("ASSIGN_LABOUR_HOUR", LabourMap.get("ASSIGN_LABOUR_HOUR")); // 派工工时
		setUpdateActivityLabourPO(activityLabourPo, activityLabourDto);
	}
	
	/**
	 * 用DTO中的值给PO属性赋值
	 * @author wantao
	 * @date 2016年4月25日
	 * @param activityLabourPo
	 * @param activityLabourDto
	 */
	public void setUpdateActivityLabourPO(TtActivityLabourPO activityLabourPo, ActivityLabourDTO activityLabourDto) {

		activityLabourPo.setLong("REPAIR_TYPE_CODE", activityLabourDto.getRepairTypeCode()); // 维修类型代码
		activityLabourPo.setString("CHARGE_PARTITION_CODE", activityLabourDto.getChargePartitionCode()); // 收费区分
		activityLabourPo.setDouble("LABOUR_PRICE", activityLabourDto.getLabourPrice()); // 工时单价
		activityLabourPo.setDouble("LABOUR_AMOUNT", NumberUtil.mul2Double(activityLabourPo.getDouble("STD_LABOUR_HOUR"),activityLabourPo.getDouble("LABOUR_PRICE"))); // = 标准工时*工时单价
		activityLabourPo.setDouble("DISCOUNT", activityLabourDto.getDiscount()); // 折扣率
	}
	
	/**
	 * 注入ActivityPartPO属性
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityPartPo
	 * @param activityPartDto
	 */
	public void setActivityPartPO(TtActivityPartPO activityPartPo, ActivityPartDTO activityPartDto) {
		StringBuilder sb = new StringBuilder();
		sb.append("select  t.PART_CODE,t.PART_NAME,t.COST_PRICE,t.COST_AMOUNT,t.UNIT,t.DEALER_CODE ");
		sb.append("from tt_part_stock t  where t.PART_STOCK_ID=? ");
		List<Object> param = new ArrayList<Object>();
		param.add(activityPartDto.getPartId());
		Map partMap = DAOUtil.findFirst(sb.toString(), param);

		setUpdateActivityPartPO(activityPartPo, activityPartDto);
		activityPartPo.setString("PART_NO", partMap.get("PART_CODE")); // 配件代码
		activityPartPo.setString("PART_NAME", partMap.get("PART_NAME")); // 配件名称
		activityPartPo.setDouble("PART_COST_PRICE", partMap.get("COST_PRICE"));
		activityPartPo.setDouble("PART_COST_AMOUNT", partMap.get("COST_AMOUNT"));
		activityPartPo.setString("UNIT_NAME", partMap.get("UNIT")); // 计量单位
	}
	
	/**
	 * 用ActivityPartDTO给PO属性赋值
	 * 
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityPartPo
	 * @param activityPartDto
	 */
	private void setUpdateActivityPartPO(TtActivityPartPO activityPartPo, ActivityPartDTO activityPartDto) {
		activityPartPo.setString("STORAGE_CODE", activityPartDto.getStorageCode()); // 仓库代码
		activityPartPo.setString("CHARGE_PARTITION_CODE", activityPartDto.getChargePartitionCode()); // 收费区分
		activityPartPo.setLong("IS_MAIN_PART", activityPartDto.getIsMainPart()); // 主要配件
		activityPartPo.setDouble("PART_QUANTITY", activityPartDto.getPartQuantity()); // 配件数量
		activityPartPo.setDouble("PART_SALES_PRICE", activityPartDto.getPartSalesPrice());
		activityPartPo.setDouble("PART_SALES_AMOUNT",NumberUtil.mul2Double(activityPartDto.getPartSalesPrice(),activityPartDto.getPartQuantity()));
		activityPartPo.setDouble("DISCOUNT", activityPartDto.getDiscount()); // 折扣率
	}
	
	/**
	 * 注入ActivityAddPO属性
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityAddPo
	 * @param activityAddDto
	 */
	public void setActivityAddPO(TtActivityAddPO activityAddPo, ActivityAddDTO activityAddDto) {
		activityAddPo.setDouble("DISCOUNT", activityAddDto.getDiscount());
		activityAddPo.setString("CHARGE_PARTITION_CODE", activityAddDto.getChargePartitionCode()); // 收费区分代码
		activityAddPo.setString("ADD_ITEM_CODE", activityAddDto.getAddItemCode()); // 附加项目代码
		activityAddPo.setString("ADD_ITEM_NAME", activityAddDto.getAddItemName()); // 附加项目名称
		activityAddPo.setDouble("ADD_ITEM_AMOUNT", activityAddDto.getAddItemAmount()); // 附加项目费
		activityAddPo.setString("MANAGE_SORT_CODE", activityAddDto.getManageSortCode()); // 收费类别代码
	}
	
	/**
	 * 注入tt_activity_model属性
	 * @author wantao
	 * @date 2017年4月25日
	 * @param activityAddPo
	 * @param activityAddDto
	 */
	public void setActivityModelPO(TtActivityModelPO activityModelPO, ActivityModelDTO activityModelDTO) {
		StringBuffer sb = new StringBuffer();
		sb.append("select m.MODEL_CODE,m.MODEL_NAME,s.SERIES_CODE,t.CONFIG_CODE,t.DEALER_CODE ");
		sb.append("from tm_package t ");
		sb.append("LEFT JOIN tm_model m ON t.MODEL_ID = m.MODEL_ID ");
		sb.append("LEFT JOIN tm_series s ON m.SERIES_ID = s.SERIES_ID ");
		sb.append("where t.PACKAGE_ID=? ");
		List<Object> param = new ArrayList<Object>();
		param.add(activityModelDTO.getPackageId());
		Map modelMap = DAOUtil.findFirst(sb.toString(), param);

		activityModelPO.setString("MODEL_CODE", modelMap.get("MODEL_CODE"));
		activityModelPO.setString("MODEL_NAME", modelMap.get("MODEL_NAME"));
		activityModelPO.setString("SERIES_CODE", modelMap.get("SERIES_CODE"));
		activityModelPO.setString("CONFIG_CODE", modelMap.get("CONFIG_CODE"));

	}

	/**
	 * 查询活动车辆信息
	* @author wantao
	* @date 2017年04月25日
	* @param queryParam
	* @return
	 */
	@Override
	public PageInfoDto queryActivityVehicle(Map<String,String> param){
		List<Object> queryParam=new ArrayList<Object>();
		StringBuffer sb=new StringBuffer();
		sb.append("select ACTIVITY_CODE,DEALER_CODE,VEHICLE_SUM,BALANCED_SUM,BALANCED_SUM/VEHICLE_SUM COMPLETED_RATE,ACTIVITY_NAME,ACTIVITY_TYPE,ACTIVITY_KIND,BEGIN_DATE,END_DATE,LABOUR_AMOUNT,REPAIR_PART_AMOUNT,ACTIVITY_AMOUNT ");
		sb.append("from (select ACTIVITY_CODE,DEALER_CODE,VEHICLE_SUM,case when BALANCED_SUM is null then 0 else BALANCED_SUM end BALANCED_SUM,ACTIVITY_NAME,ACTIVITY_TYPE,ACTIVITY_KIND,BEGIN_DATE,END_DATE,LABOUR_AMOUNT,REPAIR_PART_AMOUNT,ACTIVITY_AMOUNT ");
		sb.append("from (select tb.ACTIVITY_CODE,tb.DEALER_CODE,count(tb.ACTIVITY_VEHICLE_ID) VEHICLE_SUM,dd.BALANCED_SUM BALANCED_SUM,ta.ACTIVITY_NAME,ta.ACTIVITY_TYPE,ta.ACTIVITY_KIND,ta.BEGIN_DATE,ta.END_DATE,ta.LABOUR_AMOUNT,ta.REPAIR_PART_AMOUNT,ta.ACTIVITY_AMOUNT ");
		sb.append("from tt_activity_vehicle tb ");
		sb.append("left join tt_activity ta on tb.ACTIVITY_CODE=ta.ACTIVITY_CODE ");
		sb.append("left join (select ACTIVITY_CODE, COUNT(VIN) BALANCED_SUM ");
		sb.append("from (select distinct a.VIN,a.ACTIVITY_CODE from tt_activity_vehicle a,tt_repair_order e,tt_balance_accounts f where a.BALANCE_NO=f.BALANCE_NO and e.RO_NO=a.RO_NO and f.RO_NO=e.RO_NO and a.BALANCE_NO is not null) aa ");
		sb.append("group by ACTIVITY_CODE) dd on dd.ACTIVITY_CODE=tb.ACTIVITY_CODE ");
		sb.append("group by tb.ACTIVITY_CODE,tb.DEALER_CODE,ta.ACTIVITY_NAME,ta.ACTIVITY_TYPE,ta.ACTIVITY_KIND,ta.BEGIN_DATE,ta.END_DATE,ta.LABOUR_AMOUNT,ta.REPAIR_PART_AMOUNT,ta.ACTIVITY_AMOUNT,dd.balanced_sum) tmp_a) tmp_b where 1=1 ");
		if(!StringUtils.isNullOrEmpty(param.get("activityCode"))){
			sb.append(" and tmp_b.ACTIVITY_CODE like ? ");
			queryParam.add("%"+param.get("activityCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(param.get("activityName"))){
			sb.append(" and tmp_b.ACTIVITY_NAME like ? ");
			queryParam.add("%"+param.get("activityName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(param.get("endDateFrom"))) {
			sb.append(" and tmp_b.END_DATE >= ?  ");
			queryParam.add(param.get("endDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(param.get("endDateTo"))) {
			sb.append(" and tmp_b.END_DATE < ?  ");
			queryParam.add(DateUtil.addOneDay(param.get("endDateTo")));
		}
		return DAOUtil.pageQuery(sb.toString(), queryParam);
	}

	/**
	 * 查询活动车辆明细
	* @author wantao
	* @date 2017年04月25日
	* @param queryParam
	* @return
	 */
	@Override
	public PageInfoDto queryVehicleDetail(String id) {
		StringBuffer sb=new StringBuffer();
		sb.append("select ACTIVITY_CODE,VIN,DEALER_CODE from tt_activity_vehicle where 1=1 and ACTIVITY_CODE=? ");
		List<Object> queryParam=new ArrayList<Object>();
		queryParam.add(id);
		List<Map> vinList=DAOUtil.findAll(sb.toString(), queryParam);
		
		StringBuffer detailSql=new StringBuffer();
		detailSql.append("select distinct tmp_a.OWNER_NAME,tmp_a.DEALER_CODE,tmp_a.RO_CREATE_DATE,tmp_a.DELIVERER,tmp_a.DELIVERER_PHONE,tmp_a.DELIVERER_MOBILE,tmp_a.LICENSE,tmp_a.VIN,tmp_a.SERIES_NAME,tmp_a.MODEL_NAME,tmp_a.ENGINE_NO,tmp_a.CONSULTANT,tmp_a.SALES_DATE,tmp_a.EMPLOYEE_NAME ");
		detailSql.append("from (SELECT distinct r.OWNER_NAME,t.DEALER_CODE,t.SERVICE_ADVISOR_ASS,t.RO_CREATE_DATE,t.DELIVERER,t.DELIVERER_PHONE,t.DELIVERER_MOBILE,v.LICENSE,v.VIN,ts.SERIES_NAME,tm.MODEL_NAME,v.ENGINE_NO,v.CONSULTANT,v.SALES_DATE,te.EMPLOYEE_NAME ");
		detailSql.append("from tt_repair_order t left join tm_vehicle v on t.VIN=v.VIN and t.DEALER_CODE=v.DEALER_CODE left join TM_OWNER r on t.OWNER_NO=r.OWNER_NO left join tm_series ts on v.SERIES=ts.SERIES_CODE left join tm_model tm on v.MODEL=tm.MODEL_CODE left join tm_employee te on t.SERVICE_ADVISOR_ASS=te.EMPLOYEE_NO");
		detailSql.append(") tmp_a ");
		detailSql.append("where 1=1 and tmp_a.VIN in ");
		List<Object> param=new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(vinList)){
			detailSql.append("(");
			for(int i=0;i<vinList.size();i++){
				if(i==0){
					detailSql.append("? ");
				}else{
					detailSql.append(",? ");
				}
				param.add(vinList.get(i).get("VIN"));
			}
			detailSql.append(") ");
		}
		detailSql.append("order by tmp_a.RO_CREATE_DATE desc");
		return DAOUtil.pageQuery(detailSql.toString(), param);
	}
}
