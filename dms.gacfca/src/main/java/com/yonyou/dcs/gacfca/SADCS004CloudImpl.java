package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SalesLeadsFeedbackCreateDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS004CloudImpl 
* @Description: 销售信息撞单上报接收（反馈状态，1表示成功，2表示失败，3表示休眠）
* @author zhengzengliang 
* @date 2017年4月13日 上午11:33:03 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS004CloudImpl   extends BaseCloudImpl implements SADCS004Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS004CloudImpl.class);
	
	@Autowired
	DeCommonDao  deCommonDao;
	
	@Autowired
	SalesLeadsFeedbackCreateDao salesLeadsFeedbackCreateDao;

	@Override
	public String receiveDate(List<SameToDccDto> dtoList) throws ServiceBizException {
		logger.info("====销售信息撞单上报接收开始====");
		String msg = "1";
		for (SameToDccDto entry : dtoList) {
			try {
				insertSalesLeadsFeedback(entry);
			} catch (Exception e) {
				logger.error("销售信息撞单上报接收失败", e);
				msg ="0";
				throw new ServiceBizException(e);
				
			}
		}
		logger.info("====销售信息撞单接收结束====");
		return msg;
	}
	
	/**
	 * 
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertSalesLeadsFeedback(SameToDccDto vo) throws Exception {
		TiSalesLeadsFeedbackDcsPO salesLeadsFeedbackPO = new TiSalesLeadsFeedbackDcsPO();
		salesLeadsFeedbackPO.setLong("NID", vo.getNid());
		salesLeadsFeedbackPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		salesLeadsFeedbackPO.setTimestamp("CREATE_DATE", new Date());
		salesLeadsFeedbackPO.setString("CUSTOMER_CODE", vo.getCustomerNo());
		// 根据entitycode 查 dealerCode
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		salesLeadsFeedbackPO.setString("DEALER_CODE", dealerCode);
		
		if(vo!=null && vo.getOpportunityLevelID()!=null){
			String opportunityLevelID = salesLeadsFeedbackCreateDao.getRelationIdInfo(vo.getOpportunityLevelID());
			salesLeadsFeedbackPO.setString("OPPORTUNITY_LEVEL_ID", opportunityLevelID);
		}
		salesLeadsFeedbackPO.setString("SALES_CONSULTANT", vo.getSalesConsultant());
		
		String status = vo.getStatus();//反馈状态，1表示成功，2表示失败，3表示休眠
		if (status!=null && "1".equals(status.trim())) {
			salesLeadsFeedbackPO.setString("STATUS", status);
			salesLeadsFeedbackPO.setString("BRAND_CODE", vo.getBrandCode());
			salesLeadsFeedbackPO.setString("SERIAS_CODE", vo.getSeriasCode());
			salesLeadsFeedbackPO.setString("MODEL_CODE", vo.getModelCode());
			salesLeadsFeedbackPO.setString("CONFIGER_CODE", vo.getConfigerCode());
			salesLeadsFeedbackPO.setTimestamp("PURCHASE_TIME", vo.getPurchaseTime());
		} else if (status!=null && "2".equals(status.trim())) {
			salesLeadsFeedbackPO.setString("STATUS", status);
		} else if (status!=null && "3".equals(status.trim())) {//休眠状态
			salesLeadsFeedbackPO.setString("STATUS", status);
			salesLeadsFeedbackPO.setString("SLEEP_REASON", vo.getSleepReason());
			salesLeadsFeedbackPO.setTimestamp("SLEEP_TIME", vo.getSleepTime());
			//2015-1-16增加上报字段值
			salesLeadsFeedbackPO.setString("BRAND_CODE", vo.getBrandCode());
			salesLeadsFeedbackPO.setString("SERIAS_CODE", vo.getSeriasCode());
			salesLeadsFeedbackPO.setString("MODEL_CODE", vo.getModelCode());
			salesLeadsFeedbackPO.setString("CONFIGER_CODE", vo.getConfigerCode());
			//2015-1-9休眠状态新增字段
			salesLeadsFeedbackPO.setString("NAME", vo.getName());//客户姓名
			salesLeadsFeedbackPO.setInteger("GENDER", vo.getGender());//性别
			salesLeadsFeedbackPO.setString("PHONE", vo.getPhone());//手机
			salesLeadsFeedbackPO.setString("TELEPHONE", vo.getTelephone());//固话
			salesLeadsFeedbackPO.set("PROVINCE_ID", vo.getProvinceID());//省份
			salesLeadsFeedbackPO.set("CITY_ID", vo.getCityID());//城市
			salesLeadsFeedbackPO.setString("CONSIDERATION_ID", vo.getConsiderationID());//适合车型原因
			salesLeadsFeedbackPO.setTimestamp("REGISTER_DATE", vo.getCreatedAt());//注册日期
		}
		
		salesLeadsFeedbackPO.saveIt();
	}
	

}
