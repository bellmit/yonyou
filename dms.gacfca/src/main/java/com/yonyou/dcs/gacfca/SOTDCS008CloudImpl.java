package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUCustomerInfoPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS008CloudImpl
 * Description: 更新客户信息（客户接待信息/需求分析）(DMS更新)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午6:22:10
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS008CloudImpl extends BaseCloudImpl implements SOTDCS008Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsUCustomerInfoDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息（客户接待信息/需求分析）(DMS更新)接收开始===="); 
		for (TiDmsUCustomerInfoDto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("更新客户信息（客户接待信息/需求分析）(DMS更新)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====更新客户信息（客户接待信息/需求分析）(DMS更新)接收结束===="); 
		return msg;
	}
	
	public void insertData(TiDmsUCustomerInfoDto vo) throws Exception {
		/*
		 * Map<String, Object> map =
		 * deCommonDao.getSaDcsDealerCode(vo.getEntityCode()); String dealerCode
		 * = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		 */TiDmsUCustomerInfoPO insertUCustomerInfoPO = new TiDmsUCustomerInfoPO();
		if (null != vo.getFCAID()) {
			insertUCustomerInfoPO.setLong("FCA_ID", vo.getFCAID().longValue());// DMS客户唯一ID
		}
		insertUCustomerInfoPO.setString("UNIQUENESS_ID", vo.getUniquenessID());// DMS客户唯一ID
		insertUCustomerInfoPO.setString("CLIENT_TYPE", vo.getClientType());// 客户类型
		insertUCustomerInfoPO.setString("NAME", vo.getName());// 客户的姓名
		insertUCustomerInfoPO.setString("GENDER", vo.getGender());// 性别
		insertUCustomerInfoPO.setString("PHOME", vo.getPhone());// 手机号
		insertUCustomerInfoPO.setString("TELEPHONE", vo.getTelephone());// 固定电话
		insertUCustomerInfoPO.setInteger("PROVINCE_ID", vo.getProvinceID());// 省份ID
		insertUCustomerInfoPO.setInteger("CITY_ID", vo.getCityID());// 城市ID
		insertUCustomerInfoPO.setDate("BIRTHDAY", vo.getBirthday());// 生日
		insertUCustomerInfoPO.setString("OPP_LEVEL_ID", vo.getOppLevelID());// 客户级别ID
		insertUCustomerInfoPO.setString("SOURCE_TYPE", vo.getSourceType());// 客户来源
		insertUCustomerInfoPO.setString("DEALER_CODE", vo.getEntityCode());// 经销商代码
		insertUCustomerInfoPO.setString("DEALER_USER_ID", vo.getDealerUserID());// 销售人员的ID
		insertUCustomerInfoPO.setString("BUY_CAR_BUGGET", vo.getBuyCarBudget());// 购车预算
		insertUCustomerInfoPO.setString("BRAND_ID", vo.getBrandID());// 品牌ID
		insertUCustomerInfoPO.setString("MODEL_ID", vo.getModelID());// 车型ID
		insertUCustomerInfoPO.setString("CAR_STYLE_ID", vo.getCarStyleID());// 车款ID
		insertUCustomerInfoPO.setString("INTENT_CAR_COLOR", vo.getIntentCarColor());// 车辆颜色ID
		insertUCustomerInfoPO.setInteger("BUY_CARCONDITION", vo.getBuyCarcondition());// 购车类型
		insertUCustomerInfoPO.setInteger("SECOND_SOURCE_TYPE", vo.getSecondSourceType());
		insertUCustomerInfoPO.setTimestamp("UPDATE_DATE", vo.getUpdateDate());// 创建日期
		insertUCustomerInfoPO.setString("IS_SEND", "0");// 同步标志
		insertUCustomerInfoPO.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);// 创建者
		
		// add maxiang 2016.06.20 begin..
		insertUCustomerInfoPO.setInteger("IS_TO_SHOP", vo.getIsToShop());
		insertUCustomerInfoPO.setTimestamp("TIME_TO_SHOP", vo.getTimeToShop());
		// add maxiang 2016.06.20 end..
		
		// 插入数据
		insertUCustomerInfoPO.insert();

	}

}
