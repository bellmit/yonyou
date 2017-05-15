package com.yonyou.dms.schedule.service.impl;



import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;

import org.springframework.stereotype.Service;


import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.SADMS034Dto;

@Service
@SuppressWarnings({ "rawtypes" })
public class SADMS034ServiceImpl implements SADMS034Service {	
	@Override
	public LinkedList<SADMS034Dto> getSADMS034() throws ServiceBizException{
		        
			    LinkedList<SADMS034Dto> resultList = new LinkedList<SADMS034Dto>();
				String defautParam =getDefaultValuePre("1414");
				String defautParam2 =getDefaultValuePre("5435");
				int rangeDateOrder = Integer.parseInt(defautParam);
				int rangeDateCus = Integer.parseInt(defautParam2);
				
				List<Map> list = null;
				list = queryCoOverTotalReport(rangeDateCus,rangeDateOrder);
			  			    
				if(list != null && "".equals(list)) {
					for(int i=0;i<list.size();i++) {
						SADMS034Dto dto = new SADMS034Dto();
						Map map = list.get(i);
						dto.setDealerCode(map.get("DEALER_CODE").toString());
						dto.setOverCustomer(Integer.parseInt(map.get("OVER_CUSTOMER").toString()));
						dto.setOverOrder(Integer.parseInt(map.get("OVER_ORDER").toString()));
						dto.setValidCustomerNum(Integer.parseInt(map.get("VALID_CUSTOMER_NUM").toString()));
						dto.setValidOrderNum(Integer.parseInt(map.get("VALID_ORDER_NUM").toString()));
						dto.setSeriesCode(map.get("SERIES_CODE").toString());
						resultList.add(dto);
					}
					
				}
			return resultList;
		
	}

	private List<Map> queryCoOverTotalReport(int rangeDateCus, int rangeDateOrder) {
	   
		StringBuffer sql =new  StringBuffer();
		sql.append(" select dealer_code,seriesCode AS SERIES_CODE, sum(overCustomer) as OVER_CUSTOMER, sum(overOrder) as OVER_ORDER,sum(validCustomerNum) as VALID_CUSTOMER_NUM,sum(validOrderNum) as VALID_ORDER_NUM     from ( ");
		sql.append(" select TP.dealer_code,TI.INTENT_SERIES AS seriesCode,count(1) as overCustomer, 0 as overOrder,0 as validCustomerNum,0 as validOrderNum from tm_potential_customer TP left join ( ");
		sql.append(" SELECT a.customer_no , a.dealer_code ,b.INTENT_SERIES ,A.INTENT_ID FROM TT_CUSTOMER_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B ON ");
		sql.append(" A.dealer_code = B.dealer_code AND A.INTENT_ID = B.INTENT_ID and b.IS_MAIN_MODEL = 12781001  ");
		sql.append(" ) TI ON TP.dealer_code=TI.dealer_code AND TP.CUSTOMER_NO = TI.CUSTOMER_NO  and TP.INTENT_ID=TI.INTENT_ID ");
		sql.append(" where ");
		sql.append(" (to_days(now())-1-to_days(TP.FOUND_DATE)>"+rangeDateCus+")  and TP.INTENT_LEVEL in('13101001', ");
		sql.append(" '13101002','13101003','13101004','13101005') and TP.EXPECT_TIMES_RANGE is null and TI.INTENT_SERIES is not null group by TP.dealer_code,TI.INTENT_SERIES ");
		sql.append(" union all ");
		sql.append(" select TP.dealer_code,TI.INTENT_SERIES AS seriesCode,0 as overCustomer, 0 as overOrder,count(1) as validCustomerNum,0 as validOrderNum from tm_potential_customer  TP left join ( ");
		sql.append(" SELECT a.customer_no , a.dealer_code ,b.INTENT_SERIES ,A.INTENT_ID FROM TT_CUSTOMER_INTENT A LEFT JOIN TT_CUSTOMER_INTENT_DETAIL B ON ");
	    sql.append(" A.dealer_code = B.dealer_code AND A.INTENT_ID = B.INTENT_ID and b.IS_MAIN_MODEL = 12781001  ");
		sql.append(" ) TI ON TP.dealer_code=TI.dealer_code AND TP.CUSTOMER_NO = TI.CUSTOMER_NO  and TP.INTENT_ID=TI.INTENT_ID ");
		sql.append(" where ");
		sql.append("  (to_days(now())-1-to_days(TP.FOUND_DATE)>"+rangeDateCus+")   and TP.INTENT_LEVEL in('13101001', ");
		sql.append(" '13101002','13101003','13101004','13101005') and TI.INTENT_SERIES is not null group by TP.dealer_code,TI.INTENT_SERIES  ");
		sql.append(" union all ");
		sql.append(" select ts.dealer_code,tv.SERIES AS seriesCode ,0 as overCustomer,count(1) as overOrder,0 as validCustomerNum,0 as validOrderNum from tt_sales_order ts left join tm_vehicle tv on   ");
		sql.append(" ts.dealer_code=tv.dealer_code and ts.vin=tv.vin  ");
		sql.append(" where    (to_days(now())-1-to_days(ts.CREATED_AT)>"+rangeDateOrder+") and ts.SO_STATUS in('13011010','13011015', ");
		sql.append(" '13011020','13011025','13011040','13011045','13011050')  And ((to_days(now())-1-to_days(ts.ENTERING_DATE)>30) or ts.ENTERING_DATE is null) and tv.SERIES is not null  group by ts.dealer_code,tv.SERIES  ");
		sql.append(" union all ");
		sql.append(" select ts.dealer_code,tv.SERIES AS seriesCode,0 as overCustomer,0 as overOrder,0 as validCustomerNum,count(1) as validOrderNum from tt_sales_order ts left join tm_vehicle tv on  ");
		sql.append(" ts.dealer_code=tv.dealer_code and ts.vin=tv.vin  ");
		sql.append(" where ts.SO_STATUS in('13011010','13011015', ");
		sql.append(" '13011020','13011025','13011040','13011045','13011050') and tv.SERIES is not null  group by ts.dealer_code,tv.SERIES   ");
		sql.append(" ) A group by dealer_code,seriesCode ");
		List<Map> list = Base.findAll(sql.toString());
		return list;
	}
	
	public  String getDefaultValuePre(String itemCode) {
		String str = "";
		StringBuffer sb = new StringBuffer("SELECT * FROM TM_DEFAULT_PARA WHERE ITEM_CODE = '"+itemCode+"'");
 		List<Map> list3 = Base.findAll(sb.toString());
 		str = (String) list3.get(0).get("DEFAULT_VALUE");
		return str;
	}
}
