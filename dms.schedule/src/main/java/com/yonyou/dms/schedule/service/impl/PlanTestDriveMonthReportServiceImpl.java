package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.schedule.domains.DTO.PlanTestDriveMonthDTO;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PlanTestDriveMonthReportServiceImpl implements PlanTestDriveMonthReportService {

	public List<Map> queryPlanTestDriveMonth() {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT     TT.*,    (      CASE        WHEN TT.RETAINED_CUSTOMERS_NUMBER = 0         THEN 0         ELSE "
						+ "TT.MONTHLY_TEST_DRIVE_NUMBER / TT.RETAINED_CUSTOMERS_NUMBER       END    ) AS TEST_DRIVE_RATE,   "
						+ " (      CASE        WHEN TT.FIRST_CUSTOMER_NUMBER = 0         THEN 0         ELSE TT.FIRST_TEST_DRIVE_NUMBER / "
						+ "TT.FIRST_CUSTOMER_NUMBER       END    ) AS FIRST_TEST_DRIVE_RATE,    (      CASE        WHEN TT.MONTHLY_TEST_DRIVE_NUMBER "
						+ "= 0         THEN 0         ELSE TT.TOTAL_TURNOVER / TT.MONTHLY_TEST_DRIVE_NUMBER       END    ) AS TEST_DRIVE_DEAL_RATE,    "
						+ "(      CASE        WHEN TT.MONTHLY_TEST_DRIVE_NUMBER = 0         THEN 0         ELSE TT.CUMULATIVE_TURNOVER / "
						+ "TT.MONTHLY_TEST_DRIVE_NUMBER       END    ) AS TEST_DRIVE_CONVERSION_RATE,    (      CASE        WHEN TT.TOTAL_TURNOVER = "
						+ "0         THEN 0         ELSE TT.TEST_DRIVE_TOTAL / TT.TOTAL_TURNOVER       END    ) AS TEST_DRIVE_CONTRIBUTION,    (      CASE       "
						+ " WHEN (          TT.ENTRIES_NUMBER + TT.ABDUCTION_NUMBER        ) = 0         THEN 0         ELSE TT.ABDUCTION_NUMBER / (          "
						+ "TT.ENTRIES_NUMBER + TT.ABDUCTION_NUMBER        )       END    ) AS ABDUCTION_TEST_DRIVE_RATIO,    (      CASE        WHEN (          "
						+ "TT.ENTRIES_NUMBER + TT.ABDUCTION_NUMBER        ) = 0         THEN 0         ELSE (          TT.HALL_FEEDBACK + TT.ABDUCTION_FEEDBACK"
						+ "        ) / (          TT.ENTRIES_NUMBER + TT.ABDUCTION_NUMBER        )       END    ) REGISTRATION_FEEDBACK_RATE,    (      CASE        WHEN"
						+ " (          TT.HALL_FEEDBACK + TT.ABDUCTION_FEEDBACK        ) = 0         THEN 0         ELSE TT.SCORE_ALL / (          TT.HALL_FEEDBACK + "
						+ "TT.ABDUCTION_FEEDBACK        )       END    ) AS TEST_DRIVE_SATISFACTION   FROM    (SELECT       B.ASC_SHORTNAME,     "
						+ " C.CODE_CN_DESC AS PROVINCE_NAME,      D.CODE_CN_DESC AS CITY_NAME,      A.SERIES_CODE,      (COUNT(*)) AS "
						+ "MONTHLY_TEST_DRIVE_NUMBER,      (SELECT         COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER       WHERE TO_DAYS(CREATED_AT)"
						+ " = TO_DAYS(NOW())) AS RETAINED_CUSTOMERS_NUMBER,      (SELECT         COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER AA        "
						+ " INNER JOIN TT_TEST_DRIVE BB           ON AA.`CUSTOMER_NO` = BB.`CUSTOMER_NO`           AND MONTH(AA.TIME_TO_SHOP) = "
						+ "MONTH(BB.TEST_DRIVE_REGISTER)       WHERE BB.SERIES_CODE = A.`SERIES_CODE`         AND MONTH(BB.CREATED_AT) = MONTH(NOW()) "
						+ " AND YEAR(BB.CREATED_AT) = YEAR(NOW()) ) "
						+ "AS FIRST_TEST_DRIVE_NUMBER,      (SELECT         COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER AA       WHERE MONTH(AA.CREATED_AT) = "
						+ "MONTH(NOW())   AND YEAR(AA.CREATED_AT) = YEAR(NOW())     AND AA.IS_TO_SHOP = 12781001) AS FIRST_CUSTOMER_NUMBER,     "
						+ " (SELECT         COUNT(*)       FROM        "
						+ "TT_SALES_ORDER E         JOIN TM_VEHICLE F           ON E.VIN = F.VIN       WHERE E.SO_STATUS = 13011035         AND F.SERIES ="
						+ " A.SERIES_CODE         AND MONTH(E.CREATED_AT) = MONTH(NOW())  AND YEAR(E.CREATED_AT) = YEAR(NOW()) )"
						+ " AS TOTAL_TURNOVER,      (SELECT         COUNT(*)       FROM       "
						+ " TT_TEST_DRIVE AA         LEFT JOIN TT_SALES_ORDER B           ON AA.CUSTOMER_NO = B.CUSTOMER_NO           AND B.SO_STATUS = "
						+ "13011035       WHERE MONTH(AA.CREATED_AT) = MONTH(NOW())   AND YEAR(AA.CREATED_AT) = YEAR(NOW()) "
						+ "      AND AA.SERIES_CODE = A.`SERIES_CODE`) AS CUMULATIVE_TURNOVER,"
						+ "      (SELECT         COUNT(*)       FROM        TT_SALES_ORDER BB         INNER JOIN TT_TEST_DRIVE CC           ON BB.CUSTOMER_NO = "
						+ "CC.CUSTOMER_NO       WHERE MONTH(BB.CREATED_AT) = MONTH(NOW())   AND  YEAR(BB.CREATED_AT) = YEAR(NOW())   "
						+ "  AND CC.SERIES_CODE = A.`SERIES_CODE`         AND "
						+ "BB.SO_STATUS = 13011035) AS TEST_DRIVE_TOTAL,      (SELECT         COUNT(*)       FROM        TT_TEST_DRIVE AA         LEFT JOIN"
						+ " TM_POTENTIAL_CUSTOMER BB           ON AA.CUSTOMER_NO = BB.CUSTOMER_NO       WHERE MONTH(BB.CREATED_AT) = MONTH(NOW()) "
						+ " AND YEAR(BB.CREATED_AT) = YEAR(NOW()) "
						+ "       AND AA.SERIES_CODE = A.`SERIES_CODE`         AND AA.PLACE = 91001001         AND AA.TEST_DRIVE_REGISTER IS NOT NULL) AS"
						+ " ENTRIES_NUMBER,      (SELECT         COUNT(*)       FROM        TT_TEST_DRIVE AA         LEFT JOIN TM_POTENTIAL_CUSTOMER BB           "
						+ "ON AA.CUSTOMER_NO = BB.CUSTOMER_NO       WHERE MONTH(BB.CREATED_AT) = MONTH(NOW())    "
						+ " AND YEAR(BB.CREATED_AT) = YEAR(NOW())      AND AA.SERIES_CODE ="
						+ " A.`SERIES_CODE`         AND AA.PLACE = 91001001         AND AA.TEST_DRIVE_REGISTER IS NOT NULL         AND AA.TEST_DRIVE_FEEDBACK"
						+ " IS NOT NULL) AS HALL_FEEDBACK,      (SELECT         COUNT(*)       FROM        TT_TEST_DRIVE AA         LEFT JOIN TM_POTENTIAL_CUSTOMER BB  "
						+ "         ON AA.CUSTOMER_NO = BB.CUSTOMER_NO       WHERE MONTH(BB.CREATED_AT) = MONTH(NOW())    "
						+ " AND YEAR(BB.CREATED_AT) = YEAR(NOW())    AND AA.SERIES_CODE = "
						+ "A.`SERIES_CODE`         AND AA.PLACE = 91001002         AND AA.TEST_DRIVE_REGISTER IS NOT NULL) AS ABDUCTION_NUMBER,      "
						+ "(SELECT         COUNT(*)       FROM        TT_TEST_DRIVE AA         LEFT JOIN TM_POTENTIAL_CUSTOMER BB           ON AA.CUSTOMER_NO = "
						+ "BB.CUSTOMER_NO       WHERE MONTH(BB.CREATED_AT) = MONTH(NOW())      "
						+ " AND YEAR(BB.CREATED_AT) = YEAR(NOW())     AND AA.SERIES_CODE = A.`SERIES_CODE`         AND "
						+ "AA.PLACE = 91001002         AND AA.TEST_DRIVE_REGISTER IS NOT NULL         AND AA.TEST_DRIVE_FEEDBACK IS NOT NULL) AS "
						+ "ABDUCTION_FEEDBACK,      (SELECT         COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER AA         LEFT JOIN TT_TEST_DRIVE BB           "
						+ "ON AA.CUSTOMER_NO = BB.CUSTOMER_NO       WHERE MONTH(AA.CREATED_AT) = MONTH(NOW())     "
						+ " AND YEAR(AA.CREATED_AT) = YEAR(NOW())      AND AA.IS_TO_SHOP = 12781001  "
						+ "       AND BB.TEST_DRIVE_REGISTER IS NOT NULL         AND BB.SERIES_CODE = A.`SERIES_CODE`) AS STORE_NUMBER,      (SELECT        "
						+ " COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER AA         LEFT JOIN TT_TEST_DRIVE BB           ON AA.CUSTOMER_NO = BB.CUSTOMER_NO"
						+ "       WHERE MONTH(AA.CREATED_AT) = MONTH(NOW())    "
						+ " AND YEAR(AA.CREATED_AT) = YEAR(NOW())      AND AA.IS_TO_SHOP = 12781001         AND BB.TEST_DRIVE_REGISTER IS NOT NULL"
						+ "         AND BB.TEST_DRIVE_FEEDBACK IS NOT NULL         AND BB.SERIES_CODE = A.`SERIES_CODE`) AS STORE_FEEDBACK,      (SELECT        "
						+ " COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER AA         LEFT JOIN TT_TEST_DRIVE BB           ON AA.CUSTOMER_NO = BB.CUSTOMER_NO "
						+ "      WHERE MONTH(AA.CREATED_AT) = MONTH(NOW())  AND YEAR(AA.CREATED_AT) = YEAR(NOW())    "
						+ "    AND AA.IS_TO_SHOP = 12781002         AND BB.TEST_DRIVE_REGISTER IS NOT NULL"
						+ "         AND BB.SERIES_CODE = A.`SERIES_CODE`) AS NOT_STORE_NUMBER,      (SELECT         COUNT(*)       FROM        TM_POTENTIAL_CUSTOMER "
						+ "AA         LEFT JOIN TT_TEST_DRIVE BB           ON AA.CUSTOMER_NO = BB.CUSTOMER_NO       WHERE MONTH(AA.CREATED_AT) = MONTH(NOW()) "
						+ " AND YEAR(AA.CREATED_AT) = YEAR(NOW()) "
						+ "        AND AA.IS_TO_SHOP = 12781002         AND BB.TEST_DRIVE_REGISTER IS NOT NULL         AND BB.TEST_DRIVE_FEEDBACK IS NOT NULL        "
						+ " AND BB.SERIES_CODE = A.`SERIES_CODE`) AS NOT_STORE_FEEDBACK,      (SELECT         SUM(SCORE)       FROM        TT_TEST_DRIVE AA     "
						+ "  WHERE MONTH(AA.CREATED_AT) = MONTH(NOW())  AND YEAR(AA.CREATED_AT) = YEAR(NOW())    "
						+ "   AND AA.SERIES_CODE = A.`SERIES_CODE`) AS SCORE_ALL     FROM     "
						+ " TT_TEST_DRIVE A       LEFT JOIN TM_ASC_BASICINFO B         ON A.`DEALER_CODE` = B.`DEALER_CODE`       LEFT JOIN TC_CODE C       "
						+ "  ON B.PROVINCE = C.CODE_ID       LEFT JOIN TC_CODE D         ON B.CITY = D.CODE_ID       LEFT JOIN TT_SALES_ORDER E        "
						+ " ON A.CUSTOMER_NO = E.CUSTOMER_NO         AND E.SO_STATUS = 13011035     WHERE MONTH(A.CREATED_AT) = MONTH(NOW())    "
						+ " AND YEAR(A.CREATED_AT) = YEAR(NOW()) GROUP BY A.SERIES_CODE) TT ");
		System.err.println(sb.toString());
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	@Override
	public LinkedList<PlanTestDriveMonthDTO> getTestDriveMonthReport() {
		LinkedList<PlanTestDriveMonthDTO> resultList = new LinkedList<PlanTestDriveMonthDTO>();
		String provinceName = null;
		String cityName = null;
		String ascShortname = null;
		String series = null;
		String monthlyTestDriveNumber = null;
		String retainedCustomersNumber = null;
		String testDriveRate = null;
		String firstTestDriveNumber = null;
		String firstCustomerNumber = null;
		String firstTestDriveRate = null;
		String testDriveDealRate = null;
		String cumulativeTurnover = null;
		String testDriveConversionRate = null;
		String testDriveTotal = null;
		String TotalTurnover = null;
		String testDriveContribution = null;
		String entriesNumber = null;
		String hallFeedback = null;
		String abductionNumber = null;
		String abductionFeedback = null;
		String storeNumber = null;
		String storeFeedback = null;
		String notStoreNumber = null;
		String notStoreFeedback = null;
		String abductionTestDriveRatio = null;
		String registrationFeedbackRate = null;
		String testDriveSatisfaction = null;
		List<Map> listDrive = this.queryPlanTestDriveMonth();
		if (listDrive != null && listDrive.size() > 0) {
			for (int i = 0; i < listDrive.size(); i++) {
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("PROVINCE_NAME"))) {
					provinceName = listDrive.get(i).get("PROVINCE_NAME").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("CITY_NAME"))) {
					cityName = listDrive.get(i).get("CITY_NAME").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("ASC_SHORTNAME"))) {
					ascShortname = listDrive.get(i).get("ASC_SHORTNAME").toString();
				}
				if(!StringUtils.isNullOrEmpty(listDrive.get(i).get("SERIES_CODE"))){
					series = listDrive.get(i).get("SERIES_CODE").toString();
				}
				  monthlyTestDriveNumber = listDrive.get(i).get("MONTHLY_TEST_DRIVE_NUMBER").toString(); 					
				  retainedCustomersNumber = listDrive.get(i).get("RETAINED_CUSTOMERS_NUMBER").toString(); 
				  testDriveRate  = listDrive.get(i).get("TEST_DRIVE_RATE").toString();
				  firstTestDriveNumber = listDrive.get(i).get("FIRST_TEST_DRIVE_NUMBER").toString(); 
				  firstCustomerNumber  = listDrive.get(i).get("FIRST_CUSTOMER_NUMBER").toString();
				  firstTestDriveRate  = listDrive.get(i).get("FIRST_TEST_DRIVE_RATE").toString();
				  testDriveDealRate  = listDrive.get(i).get("TEST_DRIVE_DEAL_RATE").toString();
				  cumulativeTurnover  = listDrive.get(i).get("CUMULATIVE_TURNOVER").toString();
				  testDriveConversionRate  = listDrive.get(i).get("TEST_DRIVE_CONVERSION_RATE").toString();
				  testDriveTotal  = listDrive.get(i).get("TEST_DRIVE_TOTAL").toString();
				  TotalTurnover  = listDrive.get(i).get("TOTAL_TURNOVER").toString();
				  testDriveContribution  = listDrive.get(i).get("TEST_DRIVE_CONTRIBUTION").toString();
				  entriesNumber  = listDrive.get(i).get("ENTRIES_NUMBER").toString();
				  hallFeedback  = listDrive.get(i).get("HALL_FEEDBACK").toString();
				  abductionNumber  = listDrive.get(i).get("ABDUCTION_NUMBER").toString();
				  abductionFeedback  = listDrive.get(i).get("ABDUCTION_FEEDBACK").toString();
				  storeNumber  = listDrive.get(i).get("STORE_NUMBER").toString();
				  storeFeedback  = listDrive.get(i).get("STORE_FEEDBACK").toString();
				  notStoreNumber  = listDrive.get(i).get("NOT_STORE_NUMBER").toString();
				  notStoreFeedback  = listDrive.get(i).get("NOT_STORE_FEEDBACK").toString();
				  abductionTestDriveRatio  = listDrive.get(i).get("ABDUCTION_TEST_DRIVE_RATIO").toString();
				  registrationFeedbackRate  = listDrive.get(i).get("REGISTRATION_FEEDBACK_RATE").toString();
				  testDriveSatisfaction = listDrive.get(i).get("TEST_DRIVE_SATISFACTION").toString();
				  
				  PlanTestDriveMonthDTO dto = new PlanTestDriveMonthDTO();
				  dto.setProvinceName(provinceName);
				  dto.setCityName(cityName);
				  dto.setAscShortname(ascShortname);
				  dto.setSeries(series);	
				  dto.setMonthlyTestDriveNumber(monthlyTestDriveNumber); 	
				  dto.setRetainedCustomersNumber(retainedCustomersNumber); 
				  dto.setTestDriveRate(testDriveRate); 
				  dto.setFirstTestDriveNumber(firstTestDriveNumber); 
				  dto.setFirstCustomerNumber(firstCustomerNumber); 
				  dto.setFirstTestDriveRate(firstTestDriveRate); 
				  dto.setTestDriveDealRate(testDriveDealRate); 
				  dto.setCumulativeTurnover(cumulativeTurnover); 
				  dto.setTestDriveConversionRate(testDriveConversionRate); 
				  dto.setTestDriveTotal(testDriveTotal); 
				  dto.setTotalTurnover(TotalTurnover); 
				  dto.setTestDriveContribution(testDriveContribution); 
				  dto.setEntriesNumber(entriesNumber); 
				  dto.setHallFeedback(hallFeedback); 	
				  dto.setAbductionNumber(abductionNumber); 	
				  dto.setAbductionFeedback(abductionFeedback); 
				  dto.setStoreNumber(storeNumber); 
				  dto.setStoreFeedback(storeFeedback); 
				  dto.setNotStoreNumber(notStoreNumber); 
				  dto.setNotStoreFeedback(notStoreFeedback); 
				  dto.setAbductionTestDriveRatio(abductionTestDriveRatio); 
				  dto.setRegistrationFeedbackRate(registrationFeedbackRate); 
				  dto.setTestDriveSatisfaction(testDriveSatisfaction); 
				  resultList.add(dto);
			}
		}
		return resultList;
	}
}
