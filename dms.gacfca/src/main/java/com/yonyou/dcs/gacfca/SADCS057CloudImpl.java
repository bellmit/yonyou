package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs057Dao;
import com.yonyou.dms.DTO.gacfca.SA057DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtTestDriverStatPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS057CloudImpl
 * Description:  试乘试驾统计报表上报
 * @author DC
 * @date 2017年4月7日 上午11:27:05
 * 
 */
@Service
public class SADCS057CloudImpl extends BaseCloudImpl implements SADCS057Cloud {
	
	@Autowired
	SaDcs057Dao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS057CloudImpl.class);

	@Override
	public String handleExecutor(List<SA057DTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====试乘试驾分析数据上报接收开始===="); 
		for (SA057DTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("试乘试驾分析数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====试乘试驾分析数据上报接收结束===="); 
		return msg;
	}

	@SuppressWarnings({ "unchecked", "static-access" })
	private void insertData(SA057DTO vo) throws Exception {
		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
        Map<String, Object> weekMap = dao.getCurrentWeek(vo.getCurrentDate());
        int week = 0;
		if(weekMap!=null)
		{
			String weekCode =weekMap.get("WEEK_CODE").toString();
			if (weekCode == null || "".equals(weekCode)) {
				weekCode = "0";
			}
			week = Integer.parseInt(weekCode);
		}
		TtTestDriverStatPO statPO = new TtTestDriverStatPO();
		statPO.setTimestamp("CURRENT_DATET", vo.getCurrentDate());
		statPO.setInteger("CURRENT_WEEK", week);
		statPO.setString("DEALER_CODE", dealerCode);
		statPO.setLong("PO_CUSTOMER", vo.getPoCustomer());
		statPO.setLong("TEST_DRIVER", vo.getTestDriver());
		statPO.setLong("TEST_DRIVER_FEEDBACK", vo.getTestDriverFeedback());
		statPO.setLong("TEST_DRIVER_ORDER", vo.getTestDriverOrder());
		statPO.setString("SERIES_CODE", vo.getSeriesCode());
		statPO.setTimestamp("CREATE_DATE", new Date());
		statPO.insert();//插入试乘试驾统计表
		
	}

}
