package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TestDrivePO;
import com.yonyou.dms.common.domains.PO.basedata.TmEntityPrivateFieldPO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TestDriverDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class TestDriverServiceImpl implements TestDriverService {

	@Override
	public PageInfoDto queryTestDriver(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT AAA.*,BBB.SERIES_NAME,CCC.USER_NAME FROM (");
		sb.append(
				"SELECT  a.dealer_code,a.CUSTOMER_NO,a.`CUSTOMER_NAME`,a.`CUSTOMER_TYPE`,a.`CUS_SOURCE`,c.`GENDER`,C.CONTACTOR_PHONE, ");
		sb.append(" a.TEST_DRIVE_REGISTER,a.TEST_DRIVE_FEEDBACK,a.SCORE,a.ITEM_ID, ");
		sb.append(
				"c.`CONTACTOR_MOBILE`,c.`TIME_TO_SHOP`,  a.`SERIES_CODE`,a.`TEST_DRIVE_TYPE`,a.`PLACE`,c.`SOLD_BY`,a.`TEST_DRIVE_STATUS`  FROM ");
		sb.append(
				"TT_TEST_DRIVE A  LEFT JOIN tm_potential_customer c    ON a.dealer_code = c.dealer_code    AND a.customer_no = c.customer_no  ");
		sb.append(" where 1=1 ");

		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
			sb.append(" and a.CUSTOMER_NAME like '%" + queryParam.get("customerName") + "%'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
			sb.append(" and a.CONTACTOR_PHONE like '%" + queryParam.get("contactorPhone") + "%'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
			sb.append(" and a.CUSTOMER_TYPE = '" + queryParam.get("customerType") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("cusSource"))) {
			sb.append(" and a.CUS_SOURCE = '" + queryParam.get("cusSource") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
			sb.append(" and C.SOLD_BY = '" + queryParam.get("soldBy") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("intentSeries"))) {
			sb.append(" and a.SERIES_CODE = '" + queryParam.get("intentSeries") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("testDriveType"))) {
			sb.append(" and a.TEST_DRIVE_TYPE like '%" + queryParam.get("testDriveType") + "%'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("place"))) {
			sb.append(" and a.PLACE = '" + queryParam.get("place") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("testDriveStatus"))) {
			sb.append(" and a.TEST_DRIVE_STATUS = '" + queryParam.get("testDriveStatus") + "'");
		}
		sb.append(DAOUtilGF.getOwnedByStr("C", loginInfo.getUserId(), loginInfo.getOrgCode(),  "201011", loginInfo.getDealerCode()));
		
		sb.append(" )AAA LEFT JOIN TM_SERIES BBB ON AAA.SERIES_CODE = BBB.SERIES_CODE AND AAA.DEALER_CODE=BBB.DEALER_CODE");
		sb.append(" LEFT JOIN TM_USER CCC ON AAA.SOLD_BY = CCC.USER_ID AND AAA.DEALER_CODE=CCC.DEALER_CODE");

		System.err.println(sb.toString());
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public Map<String, Object> queryTestDriverByid(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  c.dealer_code,c.CUSTOMER_NO,c.`CUSTOMER_NAME`,c.`CUSTOMER_TYPE`,c.`CUS_SOURCE`,c.`GENDER`,C.CONTACTOR_PHONE, ");
		sb.append(
				"c.`CONTACTOR_MOBILE`,c.`ARRIVE_TIME`, A.BRAND_CODE,A.MODEL_CODE, a.`SERIES_CODE`,a.`TEST_DRIVE_TYPE`,a.`PLACE`,c.`SOLD_BY`,");
		sb.append("a.`TEST_DRIVE_STATUS`,c.MEDIA_TYPE ,c.MEDIA_DETAIL,A.DRIVER_LICENSE,A.BUY_INTENT,A.ITEM_ID,A.URL_JZ,A.URL_ID,A.URL_XY  FROM ");
		sb.append(
				"TT_TEST_DRIVE A  JOIN tm_potential_customer c    ON a.dealer_code = c.dealer_code    AND a.customer_no = c.customer_no  ");
		sb.append(" where 1=1 and a.ITEM_ID = ?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		Map<String, Object> map = DAOUtil.findFirst(sb.toString(), queryList);
		return map;
	}

	@Override
	public Map<String, Object> queryTestDriverDetailByid(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT  c.dealer_code,c.CUSTOMER_NO,c.`CUSTOMER_NAME`,c.`CUSTOMER_TYPE`,c.`CUS_SOURCE`,c.`GENDER`,C.CONTACTOR_PHONE, ");
		sb.append(
				"c.`CONTACTOR_MOBILE`,c.`ARRIVE_TIME`, A.BRAND_CODE,A.MODEL_CODE, a.`SERIES_CODE`,a.`TEST_DRIVE_TYPE`,a.`PLACE`,c.`SOLD_BY`,");
		sb.append("a.`TEST_DRIVE_STATUS`,c.MEDIA_TYPE ,c.MEDIA_DETAIL,A.DRIVER_LICENSE,A.BUY_INTENT,a.score,A.URL_JZ,A.URL_ID,A.URL_XY,A.URL_FK FROM ");
		sb.append(
				"TT_TEST_DRIVE A  JOIN tm_potential_customer c    ON a.dealer_code = c.dealer_code    AND a.customer_no = c.customer_no  ");
		sb.append(" where 1=1 and a.ITEM_ID = ?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		Map<String, Object> map = DAOUtil.findFirst(sb.toString(), queryList);
		return map;
	}

	@Override
	public Long addTestDriver(TestDriverDTO testDriverDTO) throws ServiceBizException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String nowDate = df.format(new Date());// new Date()为获取当前系统时间
		TestDrivePO testDrivePO = new TestDrivePO();
		System.err.println(testDriverDTO.getCustomerNo());
		String cusNo = testDriverDTO.getCustomerNo();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		testDrivePO.setString("CUSTOMER_NO", testDriverDTO.getCustomerNo());
		testDrivePO.setString("CUSTOMER_NAME", testDriverDTO.getCustomerName());
		testDrivePO.setString("CUSTOMER_TYPE", testDriverDTO.getCustomerType());
		testDrivePO.setString("CUS_SOURCE", testDriverDTO.getCusSource());
		testDrivePO.setString("MEDIA_TYPE", testDriverDTO.getMediaType());
		testDrivePO.setString("MEDIA_DETAIL", testDriverDTO.getMediaDetail());
		testDrivePO.setString("BRAND_CODE", testDriverDTO.getIntentBrand());
		testDrivePO.setString("SERIES_CODE", testDriverDTO.getSeriesCode());
		testDrivePO.setString("MODEL_CODE", testDriverDTO.getIntentModel());
		testDrivePO.setString("DRIVER_LICENSE", testDriverDTO.getDriverLicense());
		testDrivePO.setString("BUY_INTENT", testDriverDTO.getBuyIntent());
		testDrivePO.setString("CONTACTOR_MOBILE", testDriverDTO.getContactorMobile());
		testDrivePO.setString("CONTACTOR_PHONE", testDriverDTO.getContactorPhone());
		testDrivePO.setString("TEST_DRIVE_TYPE", testDriverDTO.getTestDriveType());
		testDrivePO.setString("PLACE", testDriverDTO.getPlace());
		testDrivePO.setString("TEST_DRIVE_REGISTER", nowDate);
		testDrivePO.setString("TEST_DRIVE_STATUS", "93001001");
		testDrivePO.setString("IS_TEST_DRIVE", "12781001");
		testDrivePO.setString("URL_JZ", testDriverDTO.getJzPhoto());
		testDrivePO.setString("URL_ID", testDriverDTO.getIdPhoto());
		testDrivePO.setString("URL_XY", testDriverDTO.getXyPhoto());
		testDrivePO.saveIt();
		//回写潜客表的是否试乘试驾
		PotentialCusPO cusPo = PotentialCusPO.findByCompositeKeys(dealerCode, cusNo);
		cusPo.setString("IS_TEST_DRIVE", 12781001);
		cusPo.saveIt();
		//回写展厅接待表的是否试乘试驾
        List<Object> relatList = new ArrayList<Object>();
        relatList.add( testDriverDTO.getCustomerNo());       		
        List<VisitingRecordPO > vrList = VisitingRecordPO.findBySQL("SELECT * FROM TT_VISITING_RECORD WHERE CUSTOMER_NO = ?",relatList.toArray());
        if(vrList != null && vrList.size()>0){
        	for(int i = 0;i<vrList.size();i++){
        		VisitingRecordPO vrPo = vrList.get(i);
        		vrPo.setString("IS_TEST_DRIVE", 12781001);
        		vrPo.saveIt();
        	}
        }
        
		return testDrivePO.getLongId();
	}

	@Override
	public void updateTestDriver(String id, TestDriverDTO testDriverDTO) throws ServiceBizException {
		TestDrivePO testDrivePO = TestDrivePO.findById(id);
		String smdate = testDrivePO.getString("TEST_DRIVE_REGISTER");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String bdate = df.format(new Date());// new Date()为获取当前系统时间
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		String newDate = df2.format(new Date());// new Date()为获取当前系统时间
		int i = this.daysBetween(smdate, bdate);
		System.err.println("相差天数" + i);
		if (i != 0) {
			throw new ServiceBizException("当天参与试乘试驾的登记信息，反馈时间仅限于当天!");
		}

		System.err.println(testDriverDTO.getScore());
		testDrivePO.setString("SCORE", testDriverDTO.getScore());
		testDrivePO.setString("URL_FK", testDriverDTO.getFkPhoto());
		testDrivePO.setString("TEST_DRIVE_STATUS", "93001002");
		testDrivePO.setString("TEST_DRIVE_FEEDBACK", newDate);
		testDrivePO.saveIt();
	}
	
	@Override
    public PageInfoDto queryCustomerAndIntent(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sql = new StringBuffer("");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> paramsList = new ArrayList<Object>();
        String number = "";
        String cuName = "";
        String cuType = "";
        String cuStatus = "";
        String cuMobile ="";
        String mainModel ="";
        String isAf = "";
        String cuCustomerStatus = "";
        String cuSoldBy2 = "";
        String strBF = "";
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))){
            number =  " and A.CUSTOMER_NO LIKE '%"+queryParam.get("customerNo")+"%' ";
        }else{
            number = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            cuName =  " and A.CUSTOMER_NAME LIKE '%"+queryParam.get("customerName")+"%' ";
        }else{
            cuName = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerType"))){
            cuType =  " and A.CUSTOMER_TYPE  =  " + queryParam.get("customerType") + "  ";;
        }else{
            cuType = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))){
            cuStatus =  " and A.INTENT_LEVEL  =  " + queryParam.get("intentLevel") + "  ";;
        }else{
            cuStatus = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))){
            cuMobile =  " and A.CONTACTOR_MOBILE  LIKE  '%" + queryParam.get("contactorMobile") + "%'  ";;
        }else{
            cuMobile = " and 1 = 1";
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))){
            mainModel =  " and C.IS_MAIN_MODEL  =  " + queryParam.get("isMainModel") + "  ";;
        }else{
            mainModel = " and 1 = 1";
        }
        isAf = " and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_F+" and A.INTENT_LEVEL <> "+DictCodeConstants.DICT_INTENT_LEVEL_FO+" ";
        StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE=80900000 ");
        List<Object> params = new ArrayList<Object>();
        params.add(loginInfo.getUserId());
        params.add(loginInfo.getDealerCode());
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        if(list!=null&&list.size()>0){
            strBF = " and 1 = 1";
        }else{
            strBF = " and A.IS_BIG_CUSTOMER = "+DictCodeConstants.DICT_IS_NO+" ";
        }
        cuCustomerStatus = " and 1 = 1";
        DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode());
      //替换权限
        sql.append(" SELECT DISTINCT A.CONTACTOR_MOBILE AS MOBILE, A.*, em.USER_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME, (CASE WHEN B.INTENT_ID IS NULL THEN 0 ELSE B.INTENT_ID END) AS BINTENT_ID,tl.CONTACTOR_NAME ");
                //000
        sql.append("  ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR, c.IS_MAIN_MODEL,c.RETAIL_FINANCE,c.DEPOSIT_AMOUNT   ");
                //111
        sql.append(" FROM TM_POTENTIAL_CUSTOMER A INNER JOIN TT_CUSTOMER_INTENT B ON B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND B.CUSTOMER_NO = A.CUSTOMER_NO AND B.INTENT_ID=A.INTENT_ID ");
                //000
        sql.append(" left join  TT_CUSTOMER_INTENT_DETAIL C on c.DEALER_CODE = A.DEALER_CODE and c.INTENT_ID=A.INTENT_ID");
        sql.append(" left join TT_PO_CUS_LINKMAN tl on A.CUSTOMER_NO=tl.CUSTOMER_NO and A.DEALER_CODE=tl.DEALER_CODE");
        sql.append(" left  join   tm_brand   br   on   c.INTENT_BRAND = br.BRAND_CODE and b.DEALER_CODE=br.DEALER_CODE\n");
        sql.append(" left  join   TM_SERIES  se   on   c.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and b.DEALER_CODE=se.DEALER_CODE\n");
        sql.append(" left  join   TM_MODEL   mo   on   c.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and b.DEALER_CODE=mo.DEALER_CODE\n");
        sql.append(" left  join   tm_configuration pa   on   c.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and b.DEALER_CODE=pa.DEALER_CODE\n");
        sql.append(" left  join   tm_color   co   on   C.INTENT_COLOR = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sql.append(" left  join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");  
        sql.append(" WHERE A.DEALER_CODE = '"+ loginInfo.getDealerCode()+ "' "+ " AND A.D_KEY =  "+ DictCodeConstants.D_KEY+ cuName+ number+ cuMobile+ cuType+ mainModel+ cuStatus+isAf+strBF+ cuCustomerStatus+cuSoldBy2);
        sql.append(DAOUtilGF.getOwnedByStr("A", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode()));//替换权限201003
        
        return DAOUtil.pageQuery(sql.toString(), paramsList);
    
    }

	public static int daysBetween(String smdate, String bdate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		long time1 = 0;
		long time2 = 0;

		try {
			cal.setTime(sdf.parse(smdate));
			time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			time2 = cal.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
}