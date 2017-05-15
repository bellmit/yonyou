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
import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS003ReturnCloudImpl 
* @Description: LMS邀约到店撞单接口的反馈上报接收(反馈状态，1表示实销，2表示撞单，3表示休眠)
* @author zhengzengliang 
* @date 2017年4月13日 上午11:22:40 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS003ReturnCloudImpl   extends BaseCloudImpl implements SADCS003ReturnCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS003ReturnCloudImpl.class);

	@Autowired
	DeCommonDao  deCommonDao;
	
	@Autowired
	SalesLeadsFeedbackCreateDao salesLeadsFeedbackCreateDao;
	
	@Override
	public String receiveDate(List<SADMS003ForeReturnDTO> dtoList) throws Exception {
		logger.info("====LMS邀约到店撞单接口的反馈上报接收开始====");
		for (SADMS003ForeReturnDTO entry : dtoList) {
			try {
				insertSalesLeadsFeedback(entry);
			} catch (Exception e) {
				logger.error("LMS邀约到店撞单接口的反馈上报接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====销售信息撞单接收结束====");
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertSalesLeadsFeedback(SADMS003ForeReturnDTO vo) throws Exception {
		TiSalesLeadsFeedbackDcsPO salesLeadsFeedbackPO = new TiSalesLeadsFeedbackDcsPO();
		salesLeadsFeedbackPO.setLong("NID", vo.getNid());
		salesLeadsFeedbackPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		salesLeadsFeedbackPO.setTimestamp("CREATE_DATE", new Date());
		salesLeadsFeedbackPO.setString("CUSTOMER_CODE", vo.getDmsCustomerNo());
		// 根据entitycode 查 dealerCode
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		salesLeadsFeedbackPO.setString("DEALER_CODE", dealerCode);
		salesLeadsFeedbackPO.setString("STATUS", "2");	//反馈状态，1表示实销，2表示撞单，3表示休眠
		salesLeadsFeedbackPO.setString("SALES_CONSULTANT", vo.getSalesConsultant());
		//convert 1310 to H A B C N
		if(vo!=null && vo.getOpportunityLevelID()!=null){
			String opportunityLevelID = salesLeadsFeedbackCreateDao.getRelationIdInfo(vo.getOpportunityLevelID());
			System.out.println("opportunityLevelID:"+opportunityLevelID);
			salesLeadsFeedbackPO.setString("OPPORTUNITY_LEVEL_ID", opportunityLevelID);
		}
		
		salesLeadsFeedbackPO.saveIt();
//		salesLeadsFeedbackDao.insertSalesLeadsFeedbackData(salesLeadsFeedbackPO);
	}
	

}
