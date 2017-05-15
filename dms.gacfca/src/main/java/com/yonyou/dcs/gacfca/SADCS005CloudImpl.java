package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SaleMaterialGroupDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.common.domains.PO.basedata.TiCustomerLoseDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS005CloudImpl 
* @Description: 战败DCC潜在客户信息上报接收
* @author zhengzengliang 
* @date 2017年4月13日 上午11:43:25 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS005CloudImpl extends BaseCloudImpl implements SADCS005Cloud{
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS005CloudImpl.class);
	
	@Autowired
	SaleMaterialGroupDao saleMaterialGroupDao;
	
	@Autowired
	DeCommonDao  deCommonDao;

	@Override
	public String receiveDate(List<dccDto> dtoList) throws Exception {
		logger.info("====战败DCC潜在客户信息上报接收开始====");
		for (dccDto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("战败DCC潜在客户信息上报接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====战败DCC潜在客户信息上报接收结束====");
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertData(dccDto vo) throws Exception {
		TiCustomerLoseDcsPO inspectionPO = new TiCustomerLoseDcsPO();
		// 根据brandCode 查 brandId
		String brandId = saleMaterialGroupDao.queryGroupIdByGroupCode(vo.getBrandCode());
		if (brandId != null && brandId.length() > 0) {
			inspectionPO.setLong("BRAND_ID", Long.parseLong(brandId));
		}
		// 根据carStyleCode 查 carStyleId
		String carStyleId = saleMaterialGroupDao.queryGroupIdByGroupCode(vo.getConfigCode());
		if (carStyleId != null && carStyleId.length() > 0) {
			inspectionPO.setLong("CAR_STYLE_ID", Long.parseLong(carStyleId));
		}
		// 根据ModelCode 查  ModelId
		String modelId = saleMaterialGroupDao.queryGroupIdByGroupCode(vo.getSeriesCode());
		if (modelId != null && modelId.length() > 0) {
			inspectionPO.setLong("MODEL_ID", Long.parseLong(modelId));
		}

		// 根据entitycode 查 dealerCode
		Map map = deCommonDao.getSaDcsDealerCode(vo.getEntityCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		inspectionPO.setString("DEALER_CODE", dealerCode);
		inspectionPO.setString("INTENT_CAR_COLOR", vo.getColorCode()); //意向车型颜色
		inspectionPO.setString("ADDRESS", vo.getAddress());
		inspectionPO.setTimestamp("BIRTHDAY", vo.getBirthday());
		inspectionPO.setInteger("CITY_ID", vo.getCityId());
		inspectionPO.setInteger("CONSIDERATION_ID", vo.getConsiderationId());
		inspectionPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		inspectionPO.setTimestamp("CREATE_DATE", new Date());
		inspectionPO.setString("DEALER_REMARK", vo.getDealerRemark());
		inspectionPO.setString("DEALER_USER_NAME", vo.getDealerUserName());
		inspectionPO.setString("DMS_CUSTOMER_ID", vo.getDmsCustomerId());
		inspectionPO.setString("EMAIL", vo.getEmail());
		inspectionPO.setString("GENDER", vo.getGender());
		inspectionPO.setLong("ID", vo.getId());
		inspectionPO.setInteger("IS_SCAN", 0);
		inspectionPO.setInteger("MEDIA_NAME_ID", vo.getMediaNameId());
		inspectionPO.setInteger("MEDIA_TYPE_ID", vo.getMediaTypeId());
		inspectionPO.setString("NAME", vo.getName());
		inspectionPO.setLong("OPPORTUNITY_LEVEL_ID", new Long(vo.getOpportunityLevelId()+""));
		inspectionPO.setString("PHONE", vo.getPhone());
		inspectionPO.setString("POST_CODE", vo.getPostCode());
		inspectionPO.setInteger("PROVINCE_ID", vo.getProvinceId());
		inspectionPO.setString("SALES_CONSULTANT", vo.getSalesConsultant());
		inspectionPO.setString("SOCIALITY_ACCOUNT", vo.getSocialityAccount());
		inspectionPO.setString("TELEPHONE", vo.getTelephone());
		
		inspectionPO.saveIt();
	}
	

}
