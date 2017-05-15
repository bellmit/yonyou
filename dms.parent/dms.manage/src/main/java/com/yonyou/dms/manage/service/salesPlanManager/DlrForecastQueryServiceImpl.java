package com.yonyou.dms.manage.service.salesPlanManager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.salesPlanManager.DlrForecastQueryDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDetailColorDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.ProOrderSerialPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyForecastDetailColorPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyForecastPO;

/**
 * 
* @ClassName: OemForecastServiceImpl 
* @Description: 生产订单任务下发service实现层 
* @author zhengzengliang
* @date 2017年2月10日 下午4:10:54 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class DlrForecastQueryServiceImpl implements DlrForecastQueryService{

	@Autowired
	private DlrForecastQueryDao dlrForecastQueryDao;
	
	@Override
	public List<Map> getDealerMonthPlanYearList(Map<String, String> queryParam) 
			throws ServiceBizException {
		List<Map> taskNoList =  dlrForecastQueryDao.getDealerMonthPlanYearList(queryParam);
		return taskNoList;
	}

	@Override
	public PageInfoDto getDlrForecastQueryList2(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto =  dlrForecastQueryDao.getDlrForecastQueryList2(queryParam);
		return pageInfoDto;
	}

	/**
	 * 生产订单确认上报（确认订单）
	 */
	@Override
	public void modifyVsMonthlyForecastDetailColor(
			TtVsMonthlyForecastDetailColorDTO ttVsMonthlyForecastDetailColorDTO) {
		String[] detailColorIds = ttVsMonthlyForecastDetailColorDTO.getDetailColorIds().split(",");
		String[] serialNumbers = ttVsMonthlyForecastDetailColorDTO.getSerialNumbers().split(",");
		String  posSerialNumber="";
		int count = 0;
		for(int i=0;i<detailColorIds.length;i++){
	    	//1.查询
	        Long detailColorId = Long.parseLong(detailColorIds[i]);
	        TtVsMonthlyForecastDetailColorPO tvmfdcPO = TtVsMonthlyForecastDetailColorPO.findById(detailColorId);
	        //2.查询
	        StringBuilder sqlSb = new StringBuilder();
	        sqlSb.append("SELECT * FROM tt_vs_monthly_forecast_detail tvmfd WHERE 1=1 AND  tvmfd.DETAIL_ID=" + tvmfdcPO.get("DETAIL_ID"));
			Map result = OemDAOUtil.findFirst(sqlSb.toString(), null);
			//3.修改
			BigDecimal forecastId = (BigDecimal) result.get("FORECAST_ID");
			TtVsMonthlyForecastPO tvmfPo = TtVsMonthlyForecastPO.findById(forecastId);
			tvmfPo.setLong("STATUS", OemDictCodeConstants.TT_VS_MONTHLY_FORECAST_REPORT);
			//获取当前用户
	        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tvmfPo.setLong("UPDATE_BY",new Long(loginInfo.getUserId()));
			tvmfPo.setDate("UPDATE_DATE", new Date());
			tvmfPo.modified();
			//4.查询
			StringBuilder sqlSb2 = new StringBuilder();
			List<Object> params2 = new ArrayList<Object>();
			sqlSb2.append("SELECT pos.ORDER_SERIAL_NUMBER_ID, pos.`STATUS`, pos.UPDATE_BY, pos.UPDATE_DATE, pos.SERIAL_NUMBER, pos.DETAIL_COLOR_ID FROM pro_order_serial pos WHERE 1=1 \n");
			sqlSb2.append("AND  pos.STATUS=  " + OemDictCodeConstants.PRO_ORDER_SERIAL_REPORT_OTD +"\n");
			if(!StringUtils.isNullOrEmpty(detailColorId)){
				sqlSb2.append("AND  pos.DETAIL_COLOR_ID= ? \n");
				params2.add(detailColorId);
			}
			List<Map> posList = OemDAOUtil.findAll(sqlSb2.toString(), params2);
			//5.修改
			if(posList.size() > 0){
				ProOrderSerialPO posPO=new ProOrderSerialPO();
				StringBuilder sqlSb3 = new StringBuilder();
				List<Object> params3 = new ArrayList<Object>();
				sqlSb3.append("SELECT pos.ORDER_SERIAL_NUMBER_ID, pos.`STATUS`, pos.UPDATE_BY, pos.UPDATE_DATE, pos.SERIAL_NUMBER, pos.DETAIL_COLOR_ID FROM pro_order_serial pos WHERE 1=1");
				if(!StringUtils.isNullOrEmpty(posList.get(0).get("ORDER_SERIAL_NUMBER_ID"))){
					sqlSb3.append("AND  pos.ORDER_SERIAL_NUMBER_ID= ? \n");
					params3.add(posList.get(0).get("ORDER_SERIAL_NUMBER_ID"));
				}
				posPO.setLong("STATUS", OemDictCodeConstants.PRO_ORDER_SERIAL_REPORT_UN);
				posPO.setLong("UPDATE_BY",new Long(loginInfo.getUserId()));
				posPO.setDate("UPDATE_DATE", new Date());
				posPO.modified();
				//6.修改
				for(int k=0; k< posList.size(); k++){
					posSerialNumber = (String) posList.get(k).get("SERIAL_NUMBER");
					for(int j=0;j<serialNumbers.length;j++){
					    if(posSerialNumber.equals(serialNumbers[j])){
					    	ProOrderSerialPO pos2=new ProOrderSerialPO();
					    	StringBuilder sqlSb4 = new StringBuilder();
							List<Object> params4 = new ArrayList<Object>();
							sqlSb4.append("SELECT pos.ORDER_SERIAL_NUMBER_ID, pos.`STATUS`, pos.UPDATE_BY, pos.UPDATE_DATE, pos.SERIAL_NUMBER, pos.DETAIL_COLOR_ID FROM pro_order_serial pos WHERE 1=1");
							if(!StringUtils.isNullOrEmpty(serialNumbers[j])){
								sqlSb4.append("AND  pos.SERIAL_NUMBER= ? \n");
								params4.add(serialNumbers[j]);
							}
							pos2.setLong("STATUS", OemDictCodeConstants.PRO_ORDER_SERIAL_REPORT);
							pos2.setLong("UPDATE_BY",new Long(loginInfo.getUserId()));
							pos2.setDate("UPDATE_DATE", new Date());
							pos2.modified();
					    	count++;
					    }
					}
				}
				
				//7.修改
				if(count!=0){
					TtVsMonthlyForecastDetailColorPO tvmfdcPO2 = 
							TtVsMonthlyForecastDetailColorPO.findById(posList.get(0).get("DETAIL_COLOR_ID"));
					tvmfdcPO2.setBigDecimal("REPORT_STATUS", OemDictCodeConstants.PRO_ORDER_SERIAL_REPORT);
					tvmfdcPO2.setLong("UPDATE_BY",new Long(loginInfo.getUserId()));
					tvmfdcPO2.setDate("UPDATE_DATE", new Date());
					tvmfdcPO2.setString("REPORT_NUM", String.valueOf(count));
					tvmfdcPO2.modified();
				}
				count=0;
				
			}
	    }
		
	}

	@Override
	public PageInfoDto DlrfindBySerialNumber(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pgInfo = dlrForecastQueryDao.DlrfindBySerialNumber(queryParam);
		return pgInfo;
	}

	

}
