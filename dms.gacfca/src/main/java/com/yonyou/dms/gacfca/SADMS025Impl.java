package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADCS025Dto;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * PAD建档率报表上报
 * @author Benzc
 * @date 2017年1月13日
 *
 */
@Service
public class SADMS025Impl implements SADMS025{

	@Override
	public LinkedList<SADCS025Dto> getSADMS025() throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calm = Calendar.getInstance();
		calm.setTime(new Date());//计划任务周五的凌晨执行 >=beigin  <end 
		Calendar calmLast = Calendar.getInstance();
	    calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMaximum(Calendar.DAY_OF_MONTH));
	    int strIsEndMonth = DictCodeConstants.STATUS_IS_NOT;
	    //PAD建档率接口上报定义
	    LinkedList<SADCS025Dto> resList = new LinkedList<SADCS025Dto>();
	    if (!format.format(calmLast.getTime()).equals(format.format(calm.getTime()))) {//判断当前日期是否是本月最后一天
	    	if (calm.get(Calendar.DAY_OF_WEEK)!=5){//5是周四
		    	return resList;
		    }
	    }else{
	    	strIsEndMonth = DictCodeConstants.STATUS_IS_YES;
	    }
	    String endDate=format.format(calm.getTime()).toString() + " 23:59:59";
	    calm.add(Calendar.MONTH,0);
	    calm.set(Calendar.DAY_OF_MONTH, 1);
	    String beginDate=format.format(calm.getTime()).toString()+ " 00:00:00";
	    //PAD建档量
	    int padArchivedCustomers = 0;
	    //PAD建档客户HSL
	    int padHslArchivedCustomers = 0;
	    //建档客户HSL
	    int hslArchivedCustomers = 0;
	    //PAD建档率
	    double padArchivedRatio = 0.00;
	  	
	    List list = new ArrayList();	    
	    list.add(dealerCode);
	    list.add(beginDate);
	    list.add(endDate);
	    StringBuffer sb = new StringBuffer();
	    sb.append(" select sum(pad_archived) pad_archived,sum(pad_hsl_archived) pad_hsl_archived,sum(hsl_archived) hsl_archived,\n");
	    sb.append(" DECIMAL(ROUND(COALESCE(CAST(SUM(pad_hsl_archived) AS DOUBLE), 0)/(CASE WHEN SUM(hsl_archived)=0 THEN 1 ELSE SUM(hsl_archived) END),4),20,4) AS pad_archived_ratio\n");
	    sb.append(" from (\n");
	    sb.append(" select count(1) as pad_archived,0 as pad_hsl_archived,0 as hsl_archived\n");
	    sb.append(" from  tm_potential_customer P\n");
	    sb.append(" where p.dealer_code ='"+dealerCode+"'\n");
	    sb.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"' and p.is_pad_create=12781001\n");
	    sb.append(" union all\n");
	    sb.append(" select 0 as pad_archived,count(1) as pad_hsl_archived,0 as hsl_archived\n");
	    sb.append(" from  tm_potential_customer P\n");
	    sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id\n");
	    sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
	    sb.append(" where p.dealer_code ='"+dealerCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003\n");
	    sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009)\n");
	    sb.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"' and p.is_pad_create=12781001\n");
	    sb.append(" union all\n");
	    sb.append(" select 0 as pad_archived,0 as pad_hsl_archived,count(1) as hsl_archived\n");
	    sb.append(" from  tm_potential_customer P\n");
	    sb.append(" inner join  TT_CUSTOMER_INTENT i  on  i.dealer_code=p.dealer_code and p.customer_no=i.customer_no and i.intent_id=p.intent_id\n");
	    sb.append(" inner join  TT_CUSTOMER_INTENT_DETAIL d on d.dealer_code=p.dealer_code and d.INTENT_ID=i.INTENT_ID and d.IS_MAIN_MODEL=12781001\n");
	    sb.append(" where p.dealer_code ='"+dealerCode+"'  and (p.intent_level=13101001 or p.intent_level=13101002 or p.intent_level=13101003\n");
	    sb.append(" or p.intent_level=13101004 or p.intent_level=13101008 or p.intent_level=13101009)\n");
	    sb.append(" and p.FOUND_DATE>'"+beginDate+"' and p.FOUND_DATE<='"+endDate+"'\n");
	    sb.append(" )\n");
	    List<Map> padArchivedList = DAOUtil.findAll(sb.toString(), list);
	    
	    if(padArchivedList != null && padArchivedList.size() > 0){
	    	for(int i = 0; i < padArchivedList.size(); i++) {
	    		Map map = padArchivedList.get(i);
	    		padArchivedCustomers = (int)map.get("PAD_ARCHIVED");
	    		padHslArchivedCustomers = (int) map.get("PAD_HSL_ARCHIVED");
	    		hslArchivedCustomers = (int) map.get("HSL_ARCHIVED");
	    		padArchivedRatio = (double) map.get("PAD_ARCHIVED_RATIO");
	    		
	    		SADCS025Dto dto = new SADCS025Dto();
	    		dto.setDealerCode(dealerCode);
	    		dto.setIsValid(10011001);
	    		dto.setDownTimestamp(new Date());
	    		dto.setPadArchivedCustomers(padArchivedCustomers);
	    		dto.setPadHslArchivedCustomers(padHslArchivedCustomers);
	    		dto.setHslArchivedCustomers(hslArchivedCustomers);
	    		dto.setPadArchivedRatio(padArchivedRatio);
	    		dto.setUploadTimestamp(new Date(System.currentTimeMillis()));
	    		dto.setIsEndOfMonth(strIsEndMonth);
	    		resList.add(dto);
	    	}
	    }else{
	    	SADCS025Dto dto = new SADCS025Dto();
    		dto.setDealerCode(dealerCode);
    		dto.setIsValid(10011001);
    		dto.setDownTimestamp(new Date());
    		dto.setPadArchivedCustomers(padArchivedCustomers);
    		dto.setPadHslArchivedCustomers(padHslArchivedCustomers);
    		dto.setHslArchivedCustomers(hslArchivedCustomers);
    		dto.setPadArchivedRatio(padArchivedRatio);
    		dto.setUploadTimestamp(new Date(System.currentTimeMillis()));
    		dto.setIsEndOfMonth(strIsEndMonth);
    		resList.add(dto);
	    }
	    
		return resList;
	}

}
