package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.ActivityDTO;
import com.yonyou.dms.DTO.gacfca.ActivityLabourDTO;
import com.yonyou.dms.DTO.gacfca.ActivityModelDTO;
import com.yonyou.dms.DTO.gacfca.ActivityOtherFeeDTO;
import com.yonyou.dms.DTO.gacfca.ActivityPartDTO;
import com.yonyou.dms.DTO.gacfca.ActivityVehicleDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmLabourPricePO;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityAddPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityVehiclePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 服务活动下发
 * @author Administrator
 *
 */
@Service
public class SEDMS005CoudImpl implements SEDMS005Coud{

	final Logger logger = Logger.getLogger(SEDMS005CoudImpl.class);


	/**
	 * @description 服务活动下发
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public int getSEDMS005(List<ActivityDTO> list, String dealerCode) {
		logger.info("==========SEDMS005Impl执行===========");
		try{
			if(dealerCode == null || dealerCode.isEmpty()){
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if(list != null && !list.isEmpty()){
				for (ActivityDTO activityDTO : list) {
					String activityCode = activityDTO.getActivityCode();
					if (activityCode == null || "".equals(activityCode.trim())) {
						logger.debug("服务活动编号为空，方法中断");
						throw new Exception("服务活动编号为空");
					}
					activityCode = activityCode.length()>14 ? activityCode.substring(activityCode.length()-14) : activityCode;
				
					logger.debug("from TtActivityPO DEALER_CODE="+dealerCode+" and ACTIVITY_CODE="+activityCode+" and DOWN_TAG="+Integer.parseInt(CommonConstants.DICT_IS_YES)+" and D_KEY="+CommonConstants.D_KEY);
					LazyList<TtActivityPO> ttActivityPOs = TtActivityPO.findBySQL("DEALER_CODE = ? and ACTIVITY_CODE = ? and DOWN_TAG = ? and D_KEY = ?", 
							dealerCode,activityCode,Integer.parseInt(CommonConstants.DICT_IS_YES),CommonConstants.D_KEY);
					TtActivityPO activityPO = setActivityPO(dealerCode, activityCode, activityDTO);
					if(ttActivityPOs != null && !ttActivityPOs.isEmpty()){
						//删除参加活动的配件
						logger.debug("delete TtActivityPartPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
						TtActivityPartPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", 
								dealerCode,activityCode,CommonConstants.D_KEY);
						//删除参加活动的附加项目
						logger.debug("delete TtActivityAddPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
						TtActivityAddPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", 
								dealerCode,activityCode,CommonConstants.D_KEY);
						//删除参加活动的维修项目
						logger.debug("delete TtActivityLabourPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
						TtActivityLabourPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", 
								dealerCode,activityCode,CommonConstants.D_KEY);
						
						//判断清单是否有数据且 下发状态 是取消的
						logger.debug("from TtActivityVehiclePO ACTIVITY_CODE = "+activityCode+" and DEALER_CODE = "+dealerCode+" and D_KEY = "+CommonConstants.D_KEY);
						LazyList<TtActivityVehiclePO> ttActivityVehiclePOs = TtActivityVehiclePO.findBySQL("ACTIVITY_CODE = ? and DEALER_CODE = ? and D_KEY = ?", 
								activityCode,dealerCode,CommonConstants.D_KEY);
						if(ttActivityVehiclePOs != null && !ttActivityVehiclePOs.isEmpty()){
							//如果此活动是未发布，那么删除当前活动。
							if(CommonConstants.DICT_ACTIVITY_RELEASE_TAG_NOT_RELEASE.equals(
									String.valueOf(DEpofactoryTools(activityDTO.getReleaseTag())))){
								//删除  参加活动的车型
								logger.debug("delete TtActivityVehiclePO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
								TtActivityVehiclePO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?",
										dealerCode,activityCode,CommonConstants.D_KEY);
								//删除 服务活动主信息
								logger.debug("delete TtActivityPO DEALER_CODE = "+dealerCode+" and ACTIVITY_CODE = "+activityCode+" and D_KEY = "+CommonConstants.D_KEY);
								TtActivityPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?",
										dealerCode,activityCode,CommonConstants.D_KEY);
							}
						}else{
							activityPO.setString("UPDATE_BY","1");
							activityPO.setDate("UPDATE_AT",new Date());
							activityPO.saveIt();
						}
					}else{
						activityPO.setString("CREATE_BY", "1");
						activityPO.setDate("CREATE_AT", new Date());
						activityPO.saveIt();
					}
					//添加活动维修项目
					if(!CommonConstants.DICT_ACTIVITY_RELEASE_TAG_NOT_RELEASE.equals(
							String.valueOf(DEpofactoryTools(activityDTO.getReleaseTag())))){				
						if (activityDTO.getLabourVoList() != null && !activityDTO.getLabourVoList().isEmpty()) {
							for (ActivityLabourDTO activityLabourDTO : activityDTO.getLabourVoList()) {

								TtActivityLabourPO activityLabourPO = setActivityLabourPO(dealerCode, activityCode, activityLabourDTO, activityDTO);
								//下端在接收的时候自动将工时单价代码为8888的工时单价默认到该服务活动中
								if (!Utility.testString(activityLabourPO.getFloat("LABOUR_PRICE"))) {
									TmLabourPricePO tmLabourPricePO = TmLabourPricePO.findByCompositeKeys(dealerCode,"8888");
									if (tmLabourPricePO != null && Utility.testString(tmLabourPricePO.getFloat("LABOUR_PRICE"))) {
										activityLabourPO.setFloat("LABOUR_PRICE",tmLabourPricePO.getFloat("LABOUR_PRICE"));
									}
								}
								activityLabourPO.saveIt();
							}
						}
						//添加活动配件
						if (activityDTO.getPartVoList() != null && !activityDTO.getPartVoList().isEmpty()) {
							for (ActivityPartDTO activityPartDTO : activityDTO.getPartVoList()) {
								setActivityPartPO(dealerCode, activityCode, activityPartDTO, activityDTO);
							}
						}
						//添加活动车型明细
						if (activityDTO.getModelVoList() != null && !activityDTO.getModelVoList().isEmpty()) {
							for (ActivityModelDTO activityModelDTO : activityDTO.getModelVoList()) {
								setActivityModelPO(dealerCode, activityCode, activityModelDTO);
							}
						}
						//添加活动车辆
						if (activityDTO.getVehicleVoList() != null && !activityDTO.getVehicleVoList().isEmpty()) {
							for (ActivityVehicleDTO activityVehicleDTO : activityDTO.getVehicleVoList()) {
								setActivityVehiclePO(dealerCode, activityCode, activityVehicleDTO);
							}
						}
						//添加活动附加项目
						if (activityDTO.getOtherFeeVoList() != null && !activityDTO.getOtherFeeVoList().isEmpty()) {
							for (ActivityOtherFeeDTO activityOtherFeeDTO : activityDTO.getOtherFeeVoList()) {
								setActivityOtherFeePO(dealerCode, activityCode, activityOtherFeeDTO,activityDTO);
							}
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========SEDMS005Impl结束===========");
		}
	}

	/**
	 * @deacription 将ActivityModelDTO类型转换成TtActivityModelPO类型
	 * 				并保存到数据库中
	 * @param dealerCode
	 * @param activityCode
	 * @param activityModelDTO
	 * @return
	 */
	private void setActivityModelPO(String dealerCode, String activityCode, ActivityModelDTO activityModelDTO) {
		TtActivityModelPO activityModelPO = new TtActivityModelPO();
		TmModelPO po = new TmModelPO();
		LazyList<TmModelPO> tmModelPOs = TmModelPO.findBySQL("MODEL_CODE = ? and MODEL_NAME = ? and DEALER_CODE = ?",
				activityModelDTO.getModelCode(),activityModelDTO.getModelName(),dealerCode);
		if(tmModelPOs != null && !tmModelPOs.isEmpty()){
			po = tmModelPOs.get(0);
			activityModelPO.setString("SERIES_CODE",po.getString("SERIES_CODE"));
		}
		activityModelPO.setString("DEALER_CODE", dealerCode);
		activityModelPO.setString("ACTIVITY_CODE", activityCode);
		activityModelPO.setString("MODEL_CODE", activityModelDTO.getModelCode());
		activityModelPO.setString("MODEL_NAME", activityModelDTO.getModelName());
		activityModelPO.setInteger("D_KEY", CommonConstants.D_KEY);
		activityModelPO.setString("CREATE_BY", "1");
		activityModelPO.setDate("CREATE_AT", new Date());
		activityModelPO.saveIt();
	}

	/**
 @description  将 ActivityPartDTO 的数据包装成TtActivityPartPO 类型,
	 * 					并保存到数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param activityVehicleDTO
	 */
	private void setActivityVehiclePO(String dealerCode, String activityCode, ActivityVehicleDTO activityVehicleDTO) {
		TtActivityVehiclePO activityVehiclePO = new TtActivityVehiclePO();
		activityVehiclePO.setString("DEALER_CODE", dealerCode);
		activityVehiclePO.setString("DUTY_ENTITY", activityVehicleDTO.getDutyEntityCode());
		activityVehiclePO.setString("ACTIVITY_CODE", activityCode);
		activityVehiclePO.setString("CUSTOMER_NAME", activityVehicleDTO.getCustomerName());
		activityVehiclePO.setString("VIN", activityVehicleDTO.getVin());
		activityVehiclePO.setString("LICENSE", activityVehicleDTO.getLicense());
		activityVehiclePO.setString("CONTACTOR_NAME", activityVehicleDTO.getContactorName());
		activityVehiclePO.setString("CONTACTOR_PHONE", activityVehicleDTO.getContactorPhone());
		activityVehiclePO.setString("CONTACTOR_MOBILE", activityVehicleDTO.getContactorMobile());
		activityVehiclePO.setInteger("PROVINCE", activityVehicleDTO.getProvince());
		activityVehiclePO.setInteger("CITY", activityVehicleDTO.getCity());
		activityVehiclePO.setInteger("DISTRICT", activityVehicleDTO.getDistrict());
		activityVehiclePO.setString("ZIP_CODE", activityVehicleDTO.getZipCode());
		activityVehiclePO.setString("ADDRESS", activityVehicleDTO.getAddress());
		activityVehiclePO.setInteger("IS_ACHIEVE", Integer.parseInt(CommonConstants.DICT_IS_YES));
		activityVehiclePO.setInteger("D_KEY", CommonConstants.D_KEY);
		activityVehiclePO.setString("CREATE_BY", "1");
		activityVehiclePO.setDate("CREATE_AT", new Date());
		activityVehiclePO.saveIt();
	}

	/**
	 * @description  将 ActivityPartDTO 的数据包装成TtActivityPartPO 类型,
	 * 					并保存到数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param activityPartVO
	 * @param activityVO
	 */
	private void setActivityPartPO(String dealerCode, String activityCode, ActivityPartDTO activityPartVO,ActivityDTO activityVO) {
		TtActivityPartPO activityPartPO = new TtActivityPartPO();
		activityPartPO.setString("DEALER_CODE", dealerCode);
		activityPartPO.setString("ACTIVITY_CODE", activityCode);
		activityPartPO.setString("PART_NO", activityPartVO.getPartNo());
		activityPartPO.setString("PART_NAME", activityPartVO.getPartNo());
		activityPartPO.setFloat("PART_QUANTITY", activityPartVO.getPartQuantity());
		activityPartPO.setDouble("PART_SALES_PRICE", activityPartVO.getPartSalesPrice());
		activityPartPO.setDouble("PART_SALES_AMOUNT", activityPartVO.getPartSalesAmount());
		//2）	总部添加服务活动工时时，对于非索赔类的活动，允许设定工时的折扣，工时折扣默认为1，精确到小数位2位，对于索赔类的活动，折扣默认为1，不能修改
		if(activityVO.getActivityType()!=null && activityVO.getActivityType()==40151008 ){
			activityPartPO.setFloat("DISCOUNT",Float.parseFloat(activityPartVO.getDiscount().toString()));
		}else{
			activityPartPO.setFloat("DISCOUNT",new Float(1.0000));
			activityPartPO.setString("CHARGE_PARTITION_CODE",CommonConstants.MANAGE_SORT_CLAIM_TAG);
		}
		activityPartPO.setString("STORAGE_CODE", "CKA1");
		activityPartPO.setString("LABOUR_CODE", activityPartVO.getLabourCode());
		activityPartPO.setInteger("D_KEY", CommonConstants.D_KEY);
		activityPartPO.setString("CREATE_BY", "1");
		activityPartPO.setDate("CREATE_AT",new Date());
		activityPartPO.setInteger("IS_MAIN_PART", DEpofactoryTools(activityPartVO.getIsMain()));
		activityPartPO.saveIt();
	}


	/**
	 * @description 将 activityLabourDTO 的数据包装成TtActivityLabourPO 类型
	 * @param dealerCode
	 * @param activityCode
	 * @param activityLabourDTO
	 * @param activityDTO
	 * @return
	 */
	private TtActivityLabourPO setActivityLabourPO( String dealerCode, String activityCode, ActivityLabourDTO activityLabourDTO,ActivityDTO activityDTO) {
		TtActivityLabourPO activityLabourPO = new TtActivityLabourPO();
		activityLabourPO.setString("DEALER_CODE", dealerCode);
		activityLabourPO.setString("ACTIVITY_CODE", activityCode);
		activityLabourPO.setString("LABOUR_CODE", activityLabourDTO.getLabourCode());
		activityLabourPO.setString("LABOUR_NAME",activityLabourDTO.getLabourName());
		activityLabourPO.setDouble("STD_LABOUR_HOUR", activityLabourDTO.getStdLabourHour());
		//2）	总部添加服务活动工时时，对于非索赔类的活动，允许设定工时的折扣，工时折扣默认为1，精确到小数位2位，对于索赔类的活动，折扣默认为1，不能修改
		if (activityDTO.getActivityType()!=null && activityDTO.getActivityType()==40151008 ){
			activityLabourPO.setFloat("DISCOUNT", Float.parseFloat(activityLabourDTO.getDiscount().toString()));
		}else{
			activityLabourPO.setFloat("DISCOUNT", new Float(1.0000));
			activityLabourPO.setString("CHARGE_PARTITION_CODE", CommonConstants.MANAGE_SORT_CLAIM_TAG);
		}
		activityLabourPO.setInteger("D_KEY", CommonConstants.D_KEY);
		activityLabourPO.setString("CRATE_BY", "1");
		activityLabourPO.setDate("CREATE_AT", new Date());
		activityLabourPO.setString("REPAIR_TYPE_CODE", "FWHD");
		return activityLabourPO;
	}

	/**
	 * @deacription 将 ActivityOtherFeeDTO 的数据包装成TtActivityAddPO 类型,
	 * 					并保存到数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param activityOtherFeeDTO
	 * @param activityDTO
	 * @return
	 */
	private void setActivityOtherFeePO(String dealerCode, String activityCode, ActivityOtherFeeDTO activityOtherFeeDTO,ActivityDTO activityDTO) {
		TtActivityAddPO activityAddPO = new TtActivityAddPO();
		activityAddPO.setString("DEALER_CODE",dealerCode);
		activityAddPO.setString("ACTIVITY_CODE",activityCode);
		activityAddPO.setString("ADD_ITEM_CODE",activityOtherFeeDTO.getOtherFeeCode());
		activityAddPO.setString("ADD_ITEM_NAME",activityOtherFeeDTO.getOtherFeeName());
		activityAddPO.setDouble("ADD_ITEM_AMOUNT",activityOtherFeeDTO.getAmount());
		if (activityDTO.getActivityType()!=null && activityDTO.getActivityType()!=40151008 ){			
			activityAddPO.setString("CHARGE_PARTITION_CODE",CommonConstants.MANAGE_SORT_CLAIM_TAG);
		}
		activityAddPO.setFloat("DISCOUNT",new Float(1.0000));
		activityAddPO.setInteger("D_KEY",CommonConstants.D_KEY);
		activityAddPO.setString("CREATE_BY","1");
		activityAddPO.setDate("CREATE_AT",new Date());
		activityAddPO.saveIt();
	}

	/**
	 * @description 将 ActivityDTO 类型转换成 TtActivityPO 的数据库查询类型
	 * @param dealerCode
	 * @param activityCode
	 * @param activityDTO
	 * @return
	 */
	private TtActivityPO setActivityPO(String dealerCode, String activityCode, ActivityDTO activityDTO) {
		TtActivityPO activityPO = new TtActivityPO();
		activityPO.setString("DEALER_CODE", dealerCode);
		activityPO.setString("ACTIVITY_CODE", activityCode);
		activityPO.setString("ACTIVITY_NAME", activityDTO.getActivityName());
		activityPO.setInteger("ACTIVITY_TYPE", Integer.parseInt(CommonConstants.DICT_ACTIVITY_TYPE_SERVICE));
		activityPO.setDate("BEGIN_DATE", activityDTO.getBeginDate());
		activityPO.setDate("END_DATE", activityDTO.getEndDate());
		activityPO.setDate("SALES_DATE_BEGIN", activityDTO.getSalesDateBegin());
		activityPO.setDate("SALES_DATE_END", activityDTO.getSalesDateEnd());
		activityPO.setDate("RELEASE_DATE", activityDTO.getReleaseDate());
		activityPO.setInteger("RELEASE_TAG", DEpofactoryTools(activityDTO.getReleaseTag()));
		activityPO.setDouble("LABOUR_AMOUNT", activityDTO.getLabourAmount());
		activityPO.setDouble("REPAIR_PART_AMOUNT", activityDTO.getRepairPartAmount());
		activityPO.setDouble("ADD_ITEM_AMOUNT", activityDTO.getOtherFee());
		activityPO.setDouble("ACTIVITY_AMOUNT", activityDTO.getActivityAmount());
		if(activityDTO.getActivityType()!=null && activityDTO.getActivityType()==40151008){
			activityPO.setInteger("IS_CLAIM",Integer.parseInt(CommonConstants.DICT_IS_NO));
		}else{
			activityPO.setInteger("IS_CLAIM",Integer.parseInt(CommonConstants.DICT_IS_YES));
		}
		activityPO.setDate("DOWN_TIMESTAMP", activityDTO.getDownTimestamp());
		activityPO.setInteger("IS_VALID", activityDTO.getIsValid());
		activityPO.setInteger("DOWN_TAG", Integer.parseInt(CommonConstants.DICT_IS_YES));
		activityPO.setString("GLOBAL_ACTIVITY_CODE", activityDTO.getGlobalActivityCode());
		activityPO.setString("ACTIVITY_TITLE", activityDTO.getActivityTitle());
		activityPO.setString("ATTACHMENT_URL", activityDTO.getAttachmentUrl());
		activityPO.setInteger("ACTIVITY_PROPERTY", activityDTO.getActivityProperty());
		activityPO.setInteger("D_KEY", CommonConstants.D_KEY);
		activityPO.setInteger("RELEASE_TAG", Integer.parseInt(CommonConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED));
		if (activityDTO.getIsRepeatAttend()!=null && activityDTO.getIsRepeatAttend()==10041001){
			activityPO.setInteger("IS_REPEAT_ATTEND",Integer.parseInt(CommonConstants.DICT_IS_YES));	
		} else {
			activityPO.setInteger("IS_REPEAT_ATTEND",Integer.parseInt(CommonConstants.DICT_IS_NO));
		}
		if(activityDTO.getActivityType()!=null){
			switch(activityDTO.getActivityType()){
			case 40151001:			//RRT快速反应
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_RRT);	
				break;
			case 40151002:  		//TSB技术通报
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_TSB);
				break;
			case 40151003:			//CSN满意度改进
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_CSN);
				break;
			case 40151004:			//召回   	Recall召回活动
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_RETURN);
				break;
			case 40151005:			//免费维修
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_WX);
				break;
			case 40151006:			//免费检查
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_JC);
				break;
			case 40151007:			//维修套餐
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_FSA);
				break;
			case 40151008:			//配件活动
				activityPO.setString("ACTIVITY_KIND",CommonConstants.DICT_ACTIVITY_KIND_PS);
				break;
			}
		}
		return activityPO;
	}

	/**
	 * @description 来自老系统的com.infoservice.dms.cgcsl.common.DEpofactory的一个方法，
	 * 				用于转换指定的编码
	 * @param num
	 * @return
	 */
	public Integer DEpofactoryTools(Integer num){
		switch(num){
		case 40171002:
			num = 12891002;
			break;
		case 40171003:
			num = 12891001;
			break;
		case 10041001:
			num = 12781001;
			break;
		case 10041002:
			num = 12781002;
			break;
		default:
			num = null;
			break;
		}
		return num;
	}
}
