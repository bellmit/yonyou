package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNCustomerInfoPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS003CloudImpl
 * Description: 客户接待信息/需求分析(DMS新增)上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午4:29:23
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS003CloudImpl extends BaseCloudImpl implements SOTDCS003Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);


	@Override
	public String handleExecutor(List<TiDmsNCustomerInfoDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====客户接待信息/需求分析(DMS新增)接收开始===="); 
		for (TiDmsNCustomerInfoDto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("客户接待信息/需求分析(DMS新增)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====客户接待信息/需求分析(DMS新增)数据接收结束===="); 
		return msg;
	}


	// 插入新增的客户信息
		public void insertData(TiDmsNCustomerInfoDto vo) throws Exception {
			TiDmsNCustomerInfoPO insertNCustomerInfoPO = new TiDmsNCustomerInfoPO();
			insertNCustomerInfoPO.setString("UNIQUENESS_ID", vo.getUniquenessId());// DMS客户唯一ID
			insertNCustomerInfoPO.setString("CLIENT_TYPE", vo.getClientType());// 客户类型
			insertNCustomerInfoPO.setString("NAME", vo.getName());// 客户的姓名
			insertNCustomerInfoPO.setString("GENDER", vo.getGender());// 性别
			insertNCustomerInfoPO.setString("PHOME", vo.getPhone());// 手机号
			insertNCustomerInfoPO.setString("TELEPHONE", vo.getTelephone());// 固定电话
			insertNCustomerInfoPO.setInteger("PROVINCE_ID", vo.getProvinceId());// 省份ID
			insertNCustomerInfoPO.setInteger("CITY_ID", vo.getCityId());// 城市ID
			insertNCustomerInfoPO.setDate("BIRTHDAY", vo.getBirthday());// 生日
			insertNCustomerInfoPO.setString("OPP_LEVEL_ID", vo.getOppLevelId());// 客户级别ID
			insertNCustomerInfoPO.setString("SOURCE_TYPE", vo.getSourceType());// 客户来源
			insertNCustomerInfoPO.setString("DEALER_CODE", vo.getEntityCode());// 经销商代码
			insertNCustomerInfoPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员的ID
			insertNCustomerInfoPO.setString("BUY_CAR_BUGGET", vo.getBuyCarBugget());// 购车预算
			insertNCustomerInfoPO.setString("BRAND_ID", vo.getBrandId());// 品牌ID
			insertNCustomerInfoPO.setString("MODEL_ID", vo.getModelId());// 车型ID
			insertNCustomerInfoPO.setString("CAR_STYLE_ID", vo.getCarStyleId());// 车款ID
			insertNCustomerInfoPO.setString("INTENT_CAR_COLOR", vo.getIntentCarColor());// 车辆颜色ID
			insertNCustomerInfoPO.setInteger("BUY_CARCONDITION", vo.getBuyCarcondition());// 购车类型
			insertNCustomerInfoPO.setTimestamp("CREATE_DATE", vo.getCreateDate());// 创建日期
			insertNCustomerInfoPO.setString("IS_SEND", "0");// 同步标志
			insertNCustomerInfoPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
			
			// add maxiang 2016.06.20 begin..
			insertNCustomerInfoPO.setInteger("IS_TO_SHOP", vo.getIsToShop());
			insertNCustomerInfoPO.setTimestamp("TIME_TO_SHOP", vo.getTimeToShop());
			// add maxiang 2016.06.20 end..
			
			// 插入数据
			insertNCustomerInfoPO.insert();
		}

}
