package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.MaintainActivityDTO;
import com.yonyou.dms.DTO.gacfca.MaintainActivityLabourDTO;
import com.yonyou.dms.DTO.gacfca.MaintainActivityPartDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPartPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description OEM下发优惠模式      (克莱斯勒对这个的称呼：保养套餐)
 * @author Administrator
 */
@Service
public class CLDMS008Impl implements CLDMS008 {
	
	final Logger logger = Logger.getLogger(CLDMS008Impl.class);

	/**
	 * @description OEM下发优惠模式     (克莱斯勒对这个的称呼：保养套餐)
	 * @author Administrator
	 */
	@Override
	public int getCLDMS008(LinkedList<MaintainActivityDTO> voList,String dealerCode){
		logger.info("==========getCLDMS008Impl执行===========");
		try{
			if (dealerCode == null || dealerCode.isEmpty()) {
				logger.debug("dealerCode 为空");
				return 0;
			}
			if (voList != null && !voList.isEmpty()) {
				for(MaintainActivityDTO maintainActivityPartDTO : voList){
					String activityCode = maintainActivityPartDTO.getPackageCode();
					if (activityCode == null || activityCode.isEmpty()) {
						logger.debug("套餐代码为空");
						throw new Exception("套餐代码为空");
					}
					//如果activityCode的长度大于14，就截取14位之后的数据，如果长度没有大于14，就直接复制
					activityCode = activityCode.length() > 14 ? activityCode.substring(activityCode.length() - 14):activityCode;
					
					logger.debug("from TtActivityPO DEALER_CODE="+dealerCode+" AND ACTIVITY_CODE="+activityCode+" AND DOWN_TAG="+CommonConstants.DICT_IS_YES+" AND D_KEY="+CommonConstants.D_KEY);
					List<TtActivityPO> ttActivityPOs = TtActivityPO.findBySQL("DEALER_CODE = ? AND ACTIVITY_CODE = ? AND DOWN_TAG = ? AND D_KEY = ?",
							dealerCode,activityCode,Integer.parseInt(CommonConstants.DICT_IS_YES),CommonConstants.D_KEY);
					TtActivityPO activityPO = setActivityPO(dealerCode, activityCode, maintainActivityPartDTO);
					if (ttActivityPOs != null && !ttActivityPOs.isEmpty()) {
						activityPO.setDate("UPDATE_AT", new Date());
						activityPO.setString("UPDATE_BY", "1");
						activityPO.saveIt();
						// 删除 活动维修项目
						
						logger.debug("delete TtActivityLabourPO DEALER_CODE="+dealerCode+" and ACTIVITY_CODE="+activityCode+" and D_KEY="+CommonConstants.D_KEY);
						TtActivityLabourPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?",
								dealerCode,activityCode,CommonConstants.D_KEY);
						//删除参加活动的配件
						logger.debug("delete TtActivityPartPO DEALER_CODE="+dealerCode+" and ACTIVITY_CODE="+activityCode+" and D_KEY="+CommonConstants.D_KEY);
						TtActivityPartPO.delete("DEALER_CODE = ? and ACTIVITY_CODE = ? and D_KEY = ?", 
								dealerCode,activityCode,CommonConstants.D_KEY);
					} else {
						activityPO.setString("CREATE_BY", "1");
						activityPO.setDate("CREATE_AT",new Date());
						activityPO.saveIt();
					}
					if(maintainActivityPartDTO.getLabourVoList() != null && !maintainActivityPartDTO.getLabourVoList().isEmpty()){
						for (MaintainActivityLabourDTO maintainActivityLabourDTO : maintainActivityPartDTO.getLabourVoList()) {
							saveLabourPO(dealerCode, activityCode, maintainActivityLabourDTO);
						}
					}
					if(maintainActivityPartDTO.getPartVoList() != null && !maintainActivityPartDTO.getPartVoList().isEmpty()){
						for (MaintainActivityPartDTO maintainActivityPartDTO1 : maintainActivityPartDTO.getPartVoList()) {
							savePartPO(dealerCode, activityCode, maintainActivityPartDTO1);
						}
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========getCLDMS008Impl结束===========");
		}
	
	}


	/**
	 * @description 把 MaintainActivityLabourDTO 类型包装成 TtActivityLabourPO 类型
	 * 				然后保存进数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param maintainActivityLabourDTO
	 * @return
	 */
	private void savePartPO(String dealerCode, String activityCode, MaintainActivityPartDTO maintainActivityPartDTO) {
		TtActivityPartPO ttActivityPartPO = new TtActivityPartPO();
		ttActivityPartPO.setString("DEALER_CODE", dealerCode);
		ttActivityPartPO.setString("ACTIVITY_CODE", activityCode);
		ttActivityPartPO.setInteger("IS_MAIN_PART", revertDictIsType(maintainActivityPartDTO.getIsMain()));
		ttActivityPartPO.setString("PART_NO", maintainActivityPartDTO.getPartCode());
		ttActivityPartPO.setString("PART_NAME", maintainActivityPartDTO.getPartName());
		ttActivityPartPO.setFloat("PART_QUANTITY", maintainActivityPartDTO.getAmount().floatValue());
		ttActivityPartPO.setDouble("PART_SALES_PRICE", maintainActivityPartDTO.getPrice());
		ttActivityPartPO.setDouble("PART_SALES_AMOUNT", maintainActivityPartDTO.getFee());
		ttActivityPartPO.setFloat("DISCOUNT", new Float(1.0000));
		ttActivityPartPO.setString("LABOUR_CODE", maintainActivityPartDTO.getLabourCode());
		ttActivityPartPO.setInteger("D_KEY", CommonConstants.D_KEY);
		ttActivityPartPO.setString("CHARGE_PARTITION_CODE", CommonConstants.MANAGE_SORT_CLAIM_TAG);
		ttActivityPartPO.setString("STORAGE_CODE", "CKA1");
		ttActivityPartPO.setString("CREATE_BY", "1");
		ttActivityPartPO.setDate("CREATE_AT", new Date());
		ttActivityPartPO.saveIt();
	}

	/**
	 * @description 把 MaintainActivityLabourDTO 类型包装成 TtActivityLabourPO 类型
	 * 				然后更新进数据库
	 * @param dealerCode
	 * @param activityCode
	 * @param maintainActivityLabourDTO
	 * @return
	 */
	private void saveLabourPO(String dealerCode, String activityCode, MaintainActivityLabourDTO maintainActivityLabourDTO) {
		TtActivityLabourPO labourPO = new TtActivityLabourPO();
		labourPO.setString("DEALER_CODE", dealerCode);
		labourPO.setString("ACTIVITY_CODE", activityCode);
		labourPO.setString("LABOUR_CODE",maintainActivityLabourDTO.getLabourCode());
		labourPO.setString("LABOUR_NAME", maintainActivityLabourDTO.getLabourName());
		labourPO.setDouble("STD_LABOUR_HOUR", maintainActivityLabourDTO.getFrt());
		labourPO.setFloat("LABOUR_PRICE", maintainActivityLabourDTO.getPrice().floatValue());
		labourPO.setDouble("LABOUR_AMOUNT", maintainActivityLabourDTO.getFee());
		labourPO.setString("CHARGE_PARTITION_CODE", CommonConstants.MANAGE_SORT_CLAIM_TAG);
		labourPO.setFloat("DISCOUNT", new Float(1.0000));
		labourPO.setInteger("D_KEY",CommonConstants.D_KEY);
		labourPO.setString("CREATE_BY", "1");
		labourPO.setDate("CREATE_AT", new Date());
		labourPO.setString("REPAIR_TYPE_CODE", "FWHD");
		labourPO.saveIt();
	}

	/**
	 * @description  把  MaintainActivityDTO 类型包装成 TtActivityPO 类型
	 * @param dealerCode
	 * @param activityCode
	 * @param maintainActivityDTO
	 * @return
	 */
	private TtActivityPO setActivityPO(String dealerCode, String activityCode, MaintainActivityDTO maintainActivityDTO) {
		TtActivityPO activityPO = new TtActivityPO();
		activityPO.setString("DEALER_CODE", dealerCode);
		activityPO.setString("ACTIVITY_CODE", activityCode);
		activityPO.setString("ACTIVITY_NAME", maintainActivityDTO.getPackageName());
		activityPO.setDouble("MAINTAIN_BEGIN_DAY", maintainActivityDTO.getMaintainStartday());
		activityPO.setDouble("Maintain_End_Day", maintainActivityDTO.getMaintainEndday());
		activityPO.setDouble("MILEAGE_BEGIN", maintainActivityDTO.getMaintainStartmileage());
		activityPO.setDouble("MILEAGE_END", maintainActivityDTO.getMaintainEndmileage());
		if (Utility.testString(maintainActivityDTO.getModelCode()))	{
			LazyList<TmModelPO> tmModelPOs = TmModelPO.findBySQL("Entity_Code = ? and MODEL_CODE = ?", dealerCode,maintainActivityDTO.getModelCode());
			if (tmModelPOs != null && !tmModelPOs.isEmpty()){
				TmModelPO tmModelPO = tmModelPOs.get(0);
				activityPO.setString("BRAND_CODE", tmModelPO.getString("BRAND_CODE"));
				activityPO.setString("SERIES_CODE", tmModelPO.getString("SERIES_CODE"));
				activityPO.setString("MODEL_CODE", tmModelPO.getString("MODEL_CODE"));
				activityPO.setString("CONFIG_CODE", maintainActivityDTO.getGroupCode());
			}
		}
		activityPO.setInteger("DOWN_TAG", Integer.parseInt(CommonConstants.DICT_IS_YES));
		activityPO.setInteger("ACTIVITY_TYPE", Integer.parseInt(CommonConstants.DICT_ACTIVITY_TYPE_MAINTAIN));
		activityPO.setDate("BEGIN_DATE", maintainActivityDTO.getBeginDate());
		activityPO.setDate("END_DATE", maintainActivityDTO.getEndDate());
		activityPO.setInteger("RELEASE_TAG", Integer.parseInt(CommonConstants.DICT_ACTIVITY_RELEASE_TAG_RELEASED));
		activityPO.setDate("Release_Date", new Date());
		activityPO.setString("MODEL_YEAR", maintainActivityDTO.getModelYear());
		activityPO.setInteger("IS_REPEAT_ATTEND", Integer.parseInt(CommonConstants.DICT_IS_NO));
		activityPO.setDate("DOWN_TIMESTAMP", new Date());
		activityPO.setInteger("D_KEY", CommonConstants.D_KEY);
		return activityPO;
	}

	/**
	 * @description 状态数值改变
	 * @param num
	 * @return
	 */
	private Integer revertDictIsType(Integer num){
		switch(num){
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