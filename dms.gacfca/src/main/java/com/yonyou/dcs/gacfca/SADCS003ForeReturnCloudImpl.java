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
import com.yonyou.dms.common.domains.PO.basedata.TiSalesLeadsFeedbackCreateDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS003ForeReturnCloudImpl 
* @Description: DCC建档客户信息反馈
* @author zhengzengliang 
* @date 2017年4月13日 上午9:46:05 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS003ForeReturnCloudImpl  extends BaseCloudImpl implements SADCS003ForeReturnCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS003ForeReturnCloudImpl.class);

	@Autowired
	DeCommonDao  deCommonDao;
	
	@Autowired
	SalesLeadsFeedbackCreateDao salesLeadsFeedbackCreateDao;
	
	@Override
	public String receiveDate(List<SADMS003ForeReturnDTO> dtoList) throws Exception {
		logger.info("====DCC建档客户信息反馈接收开始====");
		for (SADMS003ForeReturnDTO entry :dtoList) {
			try {
				insertSalesLeadsFeedbackCreate(entry);
			} catch (Exception e) {
				logger.error("DCC建档客户信息反馈接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====DCC建档客户信息反馈接收结束====");
		return null;
	}
	
	/**
	 * 
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertSalesLeadsFeedbackCreate(SADMS003ForeReturnDTO vo)
			throws Exception {
		TiSalesLeadsFeedbackCreateDcsPO salesLeadsFeedbackCreatePO = new TiSalesLeadsFeedbackCreateDcsPO();
		salesLeadsFeedbackCreatePO.setLong("NID", vo.getNid());
		// 根据entitycode 查 dealerCode
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		salesLeadsFeedbackCreatePO.setString("DEALER_CODE", dealerCode);
		salesLeadsFeedbackCreatePO.setInteger("CONFLICTED_TYPE", vo.getConflictedType());
		salesLeadsFeedbackCreatePO.setString("DMS_CUSTOMER_NO", vo.getDmsCustomerNo());
		//convert 1310 to H A B C N
		if(vo!=null && vo.getOpportunityLevelID()!=null){
			String opportunityLevelID = salesLeadsFeedbackCreateDao.getRelationIdInfo(vo.getOpportunityLevelID());
			salesLeadsFeedbackCreatePO.setString("OPPORTUNITY_LEVEL_ID", opportunityLevelID);
		}
		salesLeadsFeedbackCreatePO.setString("SALES_CONSULTANT", vo.getSalesConsultant());
		salesLeadsFeedbackCreatePO.setString("IS_SCAN", "0");
		salesLeadsFeedbackCreatePO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		salesLeadsFeedbackCreatePO.setTimestamp("CREATE_DATE", new Date());
		
		salesLeadsFeedbackCreatePO.saveIt();
	}

	

}
