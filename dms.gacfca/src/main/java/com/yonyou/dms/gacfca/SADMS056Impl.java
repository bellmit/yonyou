package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS056Cloud;
import com.yonyou.dms.DTO.gacfca.SADMS056Dto;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;



/**
 * 试乘试驾分析数据上报
 * 
 * @author wangliang
 * @date 2017年2月14日
 */
@Service
public class SADMS056Impl implements SADMS056 {
	
    @Autowired SADCS056Cloud SADCS056Cloud;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public  String getSADMS056()throws ServiceBizException {
		try {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String beginDate = "";
			String endDate = "";
			Calendar calm = Calendar.getInstance();
			Date date = new Date();
			Calendar calmLast = Calendar.getInstance();
			// 本月最后一天
			calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMaximum(Calendar.DAY_OF_MONTH));
			List<Map> rslist = new ArrayList();
			long sumDboo = 0l;
			// 本月的最后一天进行上报，试驾分析报表上报
			LinkedList<SADMS056Dto> resultList = new LinkedList<SADMS056Dto>();
			if (format.format(calmLast.getTime()).equals(format.format(calm.getTime()))) {
				endDate = format.format(calmLast.getTime()).toString() + "23:59:59";
				calm.add(Calendar.MONTH, 0);
				calm.add(Calendar.DAY_OF_MONTH, 1);
				beginDate = format.format(calm.getTime()).toString() + "00:00:00";
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT DISTINCT q.DEALER_CODE,q.QUESTIONNAIRE_CODE,q.QUESTION_CODe,w.ANSWER_no,q.series_code,w.Sum_Db000  ");
			sb.append(" FROM ");
			sb.append(" (SELECT a.DEALER_CODE,a.QUESTIONNAIRE_CODE,c.QUESTIONNAIRE_NAME,a.QUESTION_CODE,d.QUESTION_NAME,c.series_code FROM TT_TESTDRIVE_FEEDBACK a  ");
			sb.append(" INNER JOIN TT_TEST_DRIVE b ON a.DEALER_CODE = b.DEALER_CODE AND a.ITEM_ID = b.ITEM_ID  ");
			sb.append(" INNER JOIN ( ");
			sb.append( CommonConstants.VT_TRACE_QUESTIONNAIRE + " ) c ");
			sb.append(" ON a.DEALER_CODE = c.DEALER_CODE  AND a.QUESTIONNAIRE_CODE = c.QUESTIONNAIRE_CODE AND c.QUESTIONNAIRE_TYPE = 11311003 AND c.QUESTIONNAIRE_CODE IN ( 'QR0001','QR0002','QR0003','QR0004','QR0005','QR0006','QR0007') ");
			sb.append(" INNER JOIN ( ");
			sb.append( CommonConstants.VT_TRACE_QUESTION + "  ) d ");
			sb.append(" ON a.DEALER_CODE = d.DEALER_CODE AND a.QUESTION_CODE = d.QUESTION_CODE  WHERE a.QUESTION_TYPE = 11321002) q ");
			sb.append(" INNER JOIN  ");
			sb.append(" (SELECT m.DEALER_CODE,m.QUESTIONNAIRE_CODE,m.QUESTION_CODE,tfd.ANSWER_no,COUNT(*) Sum_Db000 FROM ");
			sb.append(" TT_TESTDRIVE_FEEDBACK m INNER JOIN Tt_Testdrive_Feedback_Detail tfd ON tfd.DEALER_CODE = m.DEALER_CODE AND tfd.TEST_DRIVE_FB_ID = m.TEST_DRIVE_FB_ID  ");
			sb.append(" INNER JOIN Tt_Test_Drive ttd ON m.DEALER_CODE = ttd.DEALER_CODE AND m.item_id = ttd.item_id  ");
			sb.append(" WHERE ttd.CREATED_AT > '"+beginDate+"' AND ttd.CREATED_AT < '"+endDate+"' AND m.DEALER_CODE = '"+dealerCode+"' ");
			sb.append(" GROUP BY m.DEALER_CODE,m.QUESTIONNAIRE_CODE,m.QUESTION_CODE,tfd.ANSWER_no) w ");
			sb.append(" ON q.DEALER_CODE = w.DEALER_CODE  AND q.QUESTIONNAIRE_CODE = w.QUESTIONNAIRE_CODE AND q.QUESTION_CODE = w.QUESTION_CODE  ");
			
			System.out.println("***************************************");
			System.out.println(sb.toString());
			System.out.println("***************************************");
			/*List list = new ArrayList();
			list.add(dealerCode);
			list.add(beginDate);
			list.add(endDate);*/
			
			rslist = DAOUtil.findAll(sb.toString(), null);
			if (rslist != null && rslist.size() > 0) {
				for (int i = 0; i < rslist.size(); i++) {
					Map map = rslist.get(i);
					SADMS056Dto dto = new SADMS056Dto();
					dto.setDealerCode(dealerCode);
					dto.setSubmitDate(date);
					dto.setQuestionnaireCode((String) map.get("QUESTIONNAIRE_CODE"));
					dto.setQuestionCode((String) map.get("QUESTION_CODE"));
					dto.setSeriesCode((String) map.get("SERIES_CODE"));
					sumDboo = (long) map.get("SUM_DB000");
					dto.setSumDb000(sumDboo);
					dto.setAnswerNo((String)map.get("ANSWER_NO"));
					resultList.add(dto);
				}
			}
			// 调用厂端方法，上报试乘试驾分析数据
			return SADCS056Cloud.handleExecutor(resultList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT q.DEALER_CODE,q.QUESTIONNAIRE_CODE,q.QUESTION_CODe,w.ANSWER_no,q.series_code,w.Sum_Db000  ");
		sb.append(" FROM ");
		sb.append(" (SELECT a.DEALER_CODE,a.QUESTIONNAIRE_CODE,c.QUESTIONNAIRE_NAME,a.QUESTION_CODE,d.QUESTION_NAME,c.series_code FROM TT_TESTDRIVE_FEEDBACK a  ");
		sb.append(" INNER JOIN TT_TEST_DRIVE b ON a.DEALER_CODE = b.DEALER_CODE AND a.ITEM_ID = b.ITEM_ID  ");
		sb.append(" INNER JOIN ( ");
		sb.append( CommonConstants.VT_TRACE_QUESTIONNAIRE + " ) c ");
		sb.append(" ON a.DEALER_CODE = c.DEALER_CODE  AND a.QUESTIONNAIRE_CODE = c.QUESTIONNAIRE_CODE AND c.QUESTIONNAIRE_TYPE = 11311003 AND c.QUESTIONNAIRE_CODE IN ( 'QR0001','QR0002','QR0003','QR0004','QR0005','QR0006','QR0007') ");
		sb.append(" INNER JOIN ( ");
		sb.append( CommonConstants.VT_TRACE_QUESTION + "  ) d ");
		sb.append(" ON a.DEALER_CODE = d.DEALER_CODE AND a.QUESTION_CODE = d.QUESTION_CODE  WHERE a.QUESTION_TYPE = 11321002) q ");
		sb.append(" INNER JOIN  ");
		sb.append(" (SELECT m.DEALER_CODE,m.QUESTIONNAIRE_CODE,m.QUESTION_CODE,tfd.ANSWER_no,COUNT(*) Sum_Db000 FROM ");
		sb.append(" TT_TESTDRIVE_FEEDBACK m INNER JOIN Tt_Testdrive_Feedback_Detail tfd ON tfd.DEALER_CODE = m.DEALER_CODE AND tfd.TEST_DRIVE_FB_ID = m.TEST_DRIVE_FB_ID  ");
		sb.append(" INNER JOIN Tt_Test_Drive ttd ON m.DEALER_CODE = ttd.DEALER_CODE AND m.item_id = ttd.item_id  ");
		sb.append(" WHERE ttd.CREATED_AT > '' AND ttd.CREATED_AT < '' AND m.DEALER_CODE = '' ");
		sb.append(" GROUP BY m.DEALER_CODE,m.QUESTIONNAIRE_CODE,m.QUESTION_CODE,tfd.ANSWER_no) w ");
		sb.append(" ON q.DEALER_CODE = w.DEALER_CODE  AND q.QUESTIONNAIRE_CODE = w.QUESTIONNAIRE_CODE AND q.QUESTION_CODE = w.QUESTION_CODE  ");
		
		System.out.println("***************************************");
		System.out.println(sb.toString());
		System.out.println("***************************************");
	}
}
