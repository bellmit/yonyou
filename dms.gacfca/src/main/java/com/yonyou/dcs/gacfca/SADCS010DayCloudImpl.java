package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dms.DTO.gacfca.SA010DayDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtShowroomDayForecastDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS010DayCloudImpl 
* @Description: 展厅预测报告(每天)
* @author zhengzengliang 
* @date 2017年4月13日 下午3:21:11 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS010DayCloudImpl  extends BaseCloudImpl implements SADCS010DayCloud{
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS010DayCloudImpl.class);
	
	@Autowired
	DeCommonDao  deCommonDao;

	@Override
	public String receiveDate(List<SA010DayDTO> dtoList) throws Exception {
		logger.info("====展厅日预测报告数据上报接收开始====");
		for (SA010DayDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("展厅日预测报告数据上报接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====展厅日预测报告数据上报接收结束====");
		return null;
	}
	
	public synchronized void insertData(SA010DayDTO vo) throws Exception {
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		String dealerName = String.valueOf(map.get("DEALER_NAME"));// 上报经销商信息
		TtShowroomDayForecastDcsPO showPO = new TtShowroomDayForecastDcsPO();
		showPO.setString("CALLIN_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("WALKIN_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("HOT_SERIES_CODE", vo.getSeriesCode());
		showPO.setString("SALES_SERIES_CODE", vo.getSeriesCode());
		showPO.setLong("CREATE_BY", 999999999L);
		showPO.setTimestamp("CREATE_DATE", new Date());
		if (vo.getCallIn() != null) {
			showPO.set("COMEIN_SERIES_NUM", new Long(vo.getCallIn()));
		}
		if (vo.getHotSalesLeads() != null) {
			showPO.set("HOT_SERIES_NUM", new Long(vo.getHotSalesLeads()));
		}
		if (vo.getSalesOrders() != null) {
			showPO.set("SALES_SERIES_NUM", new Long(vo.getSalesOrders()));
		}
		if (vo.getComeIn() != null) {
			showPO.set("COMEIN_SERIES_NUM", new Long(vo.getComeIn()));
		}
		if(vo.getWalkIn()!=null)
		{
			showPO.set("WALKIN_SERIES_NUM", new Long(vo.getWalkIn()));
		}
		showPO.setString("DEALER_CODE", dealerCode);
		showPO.setString("DEALER_NAME", dealerName);
		if (vo.getTestDrive() != null) {
			showPO.set("TEST_DRIVE", new Long(vo.getTestDrive()));
		}
		if (vo.getNoOfSc() != null) {
			showPO.set("NO_OF_SC", new Long(vo.getNoOfSc()));
		}
		// showPO.setConRate(vo.getConversionRatio());
		showPO.setTimestamp("CURRENT_DATE", vo.getCurrentDate());
		showPO.setInteger("IS_ARC", 0);
		showPO.setInteger("IS_DEL", 0);
		showPO.setInteger("VER", 0);
		
		showPO.saveIt(); // 插入展厅预测报告数据
	}
	
	
	
	
	

}
