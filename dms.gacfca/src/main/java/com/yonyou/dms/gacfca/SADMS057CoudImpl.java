package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS057Cloud;
import com.yonyou.dms.DTO.gacfca.SA057DTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 上报试乘试驾统计报表（周报）
 * @author Benzc
 * @date 2017年5月4日
 * */
@Service
public class SADMS057CoudImpl implements SADMS057Coud{
	
	@Autowired SADCS057Cloud SADCS057Cloud;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public int getSADMS057() throws ServiceBizException {
		try {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Date date = new Date();  
		    Calendar calm = Calendar.getInstance();
		    calm.setTime(date);//计划任务周五的凌晨执行 >=beigin  <end 
		    if (calm.get(Calendar.DAY_OF_WEEK)!=1){//1是周日
		    	return 1;
		    }
		    String endDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + " 23:59:59";
			calm.add(Calendar.DATE,-7);
			String beginDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+ " 23:59:59";
//			int poCustomer=0; //潜客人数
//			Long testDriverFeedback=0l;//试驾反馈人数
//			Long testDriverOrder=0l;//试驾成交数  
//			Long testDriver=0l; //试驾人数
			List<SA057DTO> resultList = new ArrayList<SA057DTO>();
			List<Map> listNoseries = new ArrayList();
			
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT series_code,  SUM(po_Customer) po_Customer, SUM(test_drive) test_drive,  SUM(test_Driver_Feedback) test_Driver_Feedback, SUM(test_Driver_Order) test_Driver_Order ");
			sb.append("  FROM (  SELECT  d.intent_series series_code, COUNT(DISTINCT p.customer_no) po_Customer , 0 AS test_drive,  0 AS test_Driver_Feedback,  0 AS test_Driver_Order FROM ");
			sb.append(" tm_potential_customer P INNER JOIN TT_CUSTOMER_INTENT i   ON i.dealer_code=p.dealer_code AND p.customer_no=i.customer_no AND i.intent_id=p.intent_id  INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ");
			sb.append("  ON   d.dealer_code=p.dealer_code   AND d.INTENT_ID=i.INTENT_ID  AND d.IS_MAIN_MODEL=12781001  WHERE  p.dealer_code ='"+dealerCode+"' ");
			sb.append(" AND p.FOUND_DATE>'"+beginDate+"' AND p.FOUND_DATE<='"+endDate+"'  GROUP By d.intent_series");
			sb.append(" UNION ALL ");
			sb.append("  SELECT  v.series_code series_code,  0                             AS po_Customer ,  0                             AS test_drive,  0                             AS test_Driver_Feedback, COUNT(DISTINCT p.customer_no) AS test_Driver_Order ");
			sb.append(" FROM TT_SALES_PROMOTION_PLAN p INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code=p.dealer_code AND p.customer_no=i.customer_no  AND i.intent_id=p.intent_id  INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ");
			sb.append("  ON d.dealer_code=p.dealer_code AND d.INTENT_ID=i.INTENT_ID  AND d.IS_MAIN_MODEL=12781001  LEFT JOIN Tt_SALES_ORDER E ON  D.dealer_code=E.dealer_code  AND E.CUSTOMER_NO=P.CUSTOMER_NO LEFT JOIN TM_VS_STOCK t ON d.dealer_code=t.dealer_code AND t.vin=e.vin LEFT JOIN VM_VS_PRODUCT v ON  e.dealer_code=v.dealer_code AND e.D_KEY=v.D_KEY  AND e.PRODUCT_CODE=v.PRODUCT_CODE ");
			sb.append("   WHERE   p.ACTION_DATE>'"+beginDate+"' AND p.ACTION_DATE<='"+endDate+"'  AND E.CREATE_DATE>'"+beginDate+"' AND E.CREATE_DATE<='"+endDate+"' ");
			sb.append("  AND E.BUSINESS_TYPE=13001001  AND p.REAL_VISIT_ACTION LIKE '%试乘试驾%' AND p.dealer_code='"+dealerCode+"' AND (  E.SO_STATUS=13011010 OR E.SO_STATUS=13011015  OR E.SO_STATUS=13011020  OR E.SO_STATUS=13011025  OR E.SO_STATUS=13011030  OR E.SO_STATUS=13011035  OR E.SO_STATUS=13011075 ) ");
			sb.append(" AND NOT EXISTS (  SELECT  1 FROM TT_SALES_PROMOTION_PLAN pt WHERE  pt.customer_no=p.customer_no AND pt.dealer_code=p.dealer_code AND pt.REAL_VISIT_ACTION LIKE '%试乘试驾%'  AND pt.ACTION_DATE<'"+beginDate+"'  ) GROUP BY  v.series_code ");
			sb.append("  UNION ALL ");
			sb.append("  SELECT  h.SERIES_CODE series_code,  0                             AS po_Customer ,  0                             AS test_drive,  COUNT(DISTINCT p.customer_no) AS test_Driver_Feedback,  0                             AS test_Driver_Order ");
			sb.append("  FROM TT_SALES_PROMOTION_PLAN p INNER JOIN TT_CUSTOMER_INTENT i ON  i.dealer_code=p.dealer_code AND p.customer_no=i.customer_no AND i.intent_id=p.intent_id  INNER JOIN TT_CUSTOMER_INTENT_DETAIL d ON d.dealer_code=p.dealer_code  AND d.INTENT_ID=i.INTENT_ID AND d.IS_MAIN_MODEL=12781001 INNER JOIN Tt_Drive_Plan F  ON P.dealer_code=F.dealer_code AND P.CUSTOMER_NO=F.CUSTOMER_CODE  AND F.PLAN_STATUS=33311003  inner join tm_model h  on  f.dealer_code=h.dealer_code  and f.model_code=h.model_code ");
			sb.append(" INNER JOIN Tt_Test_Drive G ON  P.dealer_code=G.dealer_code AND P.CUSTOMER_NO=G.CUSTOMER_NO WHERE ");
			sb.append("  p.ACTION_DATE>'"+beginDate+"'  AND p.ACTION_DATE<= '"+endDate+"' AND p.REAL_VISIT_ACTION LIKE '%试乘试驾%' AND p.dealer_code='"+dealerCode+"' AND G.CREATE_DATE>'"+beginDate+"' AND G.CREATE_DATE<= '"+endDate+"' ");
			sb.append("         AND NOT EXISTS (  SELECT   1   FROM  TT_SALES_PROMOTION_PLAN pt  WHERE  pt.customer_no=p.customer_no AND pt.dealer_code=p.dealer_code  AND pt.REAL_VISIT_ACTION LIKE '%试乘试驾%'  AND pt.ACTION_DATE<'"+beginDate+"'   )   GROUP BY h.SERIES_CODE ");
			sb.append("       UNION ALL ");
			sb.append(" SELECT  d.intent_series series_code,  0                             AS po_Customer ,  COUNT(DISTINCT p.customer_no) AS test_drive,  0                             AS test_Driver_Feedback,  0                             AS test_Driver_Order ");
			sb.append("  FROM TT_SALES_PROMOTION_PLAN p  INNER JOIN TT_CUSTOMER_INTENT i ON i.dealer_code=p.dealer_code AND p.customer_no=i.customer_no AND i.intent_id=p.intent_id INNER JOIN TT_CUSTOMER_INTENT_DETAIL d  ON d.dealer_code=p.dealer_code AND d.INTENT_ID=i.INTENT_ID AND d.IS_MAIN_MODEL=12781001 ");
			sb.append(" WHERE  p.ACTION_DATE>'"+beginDate+"' AND p.ACTION_DATE<= '"+endDate+"'  AND p.REAL_VISIT_ACTION LIKE '%试乘试驾%' AND p.dealer_code='"+dealerCode+"' ");
			sb.append("    AND NOT EXISTS  (  SELECT   1   FROM  TT_SALES_PROMOTION_PLAN pt  WHERE  pt.customer_no=p.customer_no AND pt.dealer_code=p.dealer_code   AND pt.REAL_VISIT_ACTION LIKE '%试乘试驾%'  AND pt.ACTION_DATE<'"+beginDate+"'  )  GROUP BY   d.intent_series  )" );
			sb.append(" WHERE series_code IN ( SELECT series_code  FROM tm_series WHERE oem_tag=12781001  AND is_valid=12781001  AND dealer_code='"+dealerCode+"' ) GROUP BY series_code ");
			System.err.println(sb.toString());
			listNoseries = DAOUtil.findAll(sb.toString(), null);
			
			if (listNoseries!=null && listNoseries.size()>0){
				for(int i=0;i<listNoseries.size();i++){
					  SA057DTO vo=new SA057DTO();
					  Map map = listNoseries.get(i);
					  vo.setDealerCode(dealerCode);
					  vo.setCurrentDate(date);
					  vo.setPoCustomer((Long)map.get("PO_CUSTOMER")+0l);
					  vo.setTestDriver((Long)map.get("TEST_DRIVE")+0l);
					  vo.setTestDriverFeedback((Long)map.get("TEST_DRIVER_FEEDBACK")+0l);
					  vo.setTestDriverOrder((Long)map.get("TEST_DRIVER_ORDER")+0l);
					  vo.setSeriesCode(map.get("SERIES_CODE").toString());
					  resultList.add(vo);
				}
			}
			//调用厂端方法，上报试乘试驾统计报表（周报）
			SADCS057Cloud.handleExecutor(resultList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
