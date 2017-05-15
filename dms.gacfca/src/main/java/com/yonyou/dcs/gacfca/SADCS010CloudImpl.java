package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.Sa010Dao;
import com.yonyou.dms.DTO.gacfca.SA010DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtShowroomForecastDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: SADCS010CloudImpl 
* @Description: 展厅预测报告
* @author zhengzengliang 
* @date 2017年4月13日 下午2:45:31 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS010CloudImpl extends BaseCloudImpl implements SADCS010Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS010CloudImpl.class);
	
	@Autowired
	DeCommonDao  deCommonDao;
	
	@Autowired
	Sa010Dao stockDao;

	@Override
	public String receiveDate(List<SA010DTO> dtoList) throws Exception {
		logger.info("====展厅预测报告数据上报接收开始====");
		for (SA010DTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("展厅预测报告数据上报接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====展厅预测报告数据上报接收结束====");
		return null;
	}
	
	public synchronized void insertData(SA010DTO vo) throws Exception {
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		String dealerName = String.valueOf(map.get("DEALER_NAME"));//上报经销商信息
        Map weekMap = stockDao.getCurrentWeek(vo.getCurrentDate());
        int week = 0;
		if(weekMap!=null)
		{
			String weekCode =weekMap.get("WEEK_CODE").toString();
			if (weekCode == null || "".equals(weekCode)) {
				weekCode = "0";
			}
			week = Integer.parseInt(weekCode);
		}
		TtShowroomForecastDcsPO showPO = new TtShowroomForecastDcsPO();
		showPO.setString("CALLIN_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("WALKIN_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("HOT_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("SALES_SERIES_CODE", vo.getSeriesCode());
		showPO.setLong("CREATE_BY", 999999999L);
		showPO.setTimestamp("CREATE_DATE", new Date());
		showPO.setBigDecimal("CALLIN_SERIES_NUM", new Long(vo.getCallIn()));
		showPO.setBigDecimal("HOT_SERIES_NUM", new Long(vo.getHotSalesLeads()));
		showPO.setBigDecimal("SALES_SERIES_NUM", new Long(vo.getSalesOrders()));
		showPO.setBigDecimal("WALKIN_SERIES_NUM", new Long(vo.getWalkIn()));
		showPO.setString("DEALER_CODE", dealerCode);
		showPO.setString("DEALER_NAME", dealerName);
		showPO.setBigDecimal("TEST_DRIVE", new Long(vo.getTestDrive()));
		showPO.setBigDecimal("NO_OF_SC", new Long(vo.getNoOfSc()));
		showPO.setBigDecimal("CON_RATE", vo.getConversionRatio());
		//2014-07-14 insert hslReplace salesReplace
		showPO.setInteger("HSL_REPLACE", vo.getHslReplace());
		showPO.setInteger("SALES_REPLACE", vo.getSalesReplace());
		showPO.setTimestamp("CURRENT_DATE", vo.getCurrentDate());
		showPO.setBigDecimal("CURRENT_WEEK", week);
		showPO.setInteger("IS_ARC", 0);
		showPO.setInteger("IS_DEL", 0);
		showPO.setInteger("VER", 0);
		showPO.setBigDecimal("WALK_FOUND", vo.getWalkFound());
		showPO.setString("WALK_FOUND_SERIES_CODE", vo.getSeriesCode());
		//2015-1-15wjs
        showPO.setLong("DCC_OF_HOT", vo.getDccOfHot());
        //wjb
        showPO.setInteger("HSL_SUMREPLACE", vo.getHslSumreplace());//置换意向客户数
        showPO.setInteger("SALES_SUMREPLACE", vo.getSalesSumreplace());//【置换成交数】
		showPO.saveIt();//插入展厅预测报告数据
	}
	

}
