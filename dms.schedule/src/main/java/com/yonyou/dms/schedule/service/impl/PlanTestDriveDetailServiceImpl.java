package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.schedule.domains.DTO.PlanTestDriveDTO;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PlanTestDriveDetailServiceImpl implements PlanTestDriveDetailService {

	public List<Map> queryPlanTestDrive() {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT H.ASC_SHORTNAME,I.CODE_CN_DESC AS PROVINCE_NAME,J.CODE_CN_DESC AS CITY_NAME,A.DEALER_CODE,A.CUSTOMER_NO,A.CUSTOMER_NAME,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.TEST_DRIVE_REGISTER,A.TEST_DRIVE_TYPE,A.PLACE,A.SCORE,A.SERIES_CODE,");
		sb.append(
				" A.TEST_DRIVE_FEEDBACK,B.SOLD_BY,B.GENDER,B.ARRIVE_TIME,C.SO_NO,C.VIN,C.STOCK_OUT_DATE,D.SERIES,E.SERIES_NAME AS SERIES_NAME_1,F.SERIES_NAME AS SERIES_NAME_2,G.USER_NAME,");
		sb.append(
				" CASE WHEN A.TEST_DRIVE_REGISTER IS NULL THEN '12781002' WHEN A.TEST_DRIVE_REGISTER IS NOT NULL THEN '12781001' ELSE '' END AS IS_REGISTER,");
		sb.append(
				" CASE WHEN A.TEST_DRIVE_FEEDBACK IS NULL THEN '12781002' WHEN A.TEST_DRIVE_FEEDBACK IS NOT NULL THEN '12781001' ELSE '' END AS IS_FEEDBACK,");
		sb.append(
				" CASE WHEN C.SO_NO IS NOT NULL THEN '12781001' WHEN C.SO_NO IS NULL THEN '12781002' ELSE '' END AS IS_STOCK_OUT ");
		sb.append(
				" FROM TT_TEST_DRIVE A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.CUSTOMER_NO = B.CUSTOMER_NO LEFT JOIN TT_SALES_ORDER C ON A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(
				" AND C.SO_STATUS = 13011035 LEFT JOIN TM_VEHICLE D ON C.VIN = D.VIN   LEFT JOIN TM_SERIES E ON A.SERIES_CODE = E.SERIES_CODE LEFT JOIN TM_SERIES F ON D.SERIES = F.SERIES_CODE");
		sb.append(
				" LEFT JOIN TM_USER G ON B.SOLD_BY = G.USER_ID  LEFT JOIN TM_ASC_BASICINFO H ON A.DEALER_CODE = H.DEALER_CODE  LEFT JOIN TC_CODE I  ON H.PROVINCE = I.CODE_ID  LEFT JOIN TC_CODE J  ON H.CITY = J.CODE_ID ");

		sb.append(" WHERE TO_DAYS(A.CREATED_AT) = TO_DAYS(NOW()) ");
		System.err.println(sb.toString());
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	@Override
	public LinkedList<PlanTestDriveDTO> getTestDriveDetail() {
		LinkedList<PlanTestDriveDTO> resultList = new LinkedList<PlanTestDriveDTO>();
		String provinceName = null;// 省
		String cityName = null;// 市
		String ascShortname = null;// 经销商名称
		String soldBy = null;// 销售顾问
		String customerName = null;// 客户名称
		String gender = null;// 性别
		String contactorPhone = null;// 联系手机
		String contactorMobile = null;// 联系电话
		String arriveTime = null;// 到店日期
		String seriesName1 = null;// 试驾车系
		String isRegister = null;// 是否登记
		String testDriveRegister = null;// 登记日期
		String testDriveRegisterTime = null;// 登记时间
		String testDriveType = null;// 体验内容
		String place = null;// 试乘试驾地点
		String isFeedback = null;// 是否填写反馈表
		String score = null;// 客户评分
		String testDriveFeedback = null;// 反馈时间
		String isStockOut = null;// 是否成交
		String seriesName2 = null;// 成交车系
		String stockOutDate = null;// 成交日期
		String remark = null;// 备注
		List<Map> listDrive = this.queryPlanTestDrive();
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
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("SOLD_BY"))) {
					soldBy = listDrive.get(i).get("SOLD_BY").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("CUSTOMER_NAME"))) {
					customerName = listDrive.get(i).get("CUSTOMER_NAME").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("GENDER"))) {
					gender = listDrive.get(i).get("GENDER").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("CONTACTOR_PHONE"))) {
					contactorPhone = listDrive.get(i).get("CONTACTOR_PHONE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("CONTACTOR_MOBILE"))) {
					contactorMobile = listDrive.get(i).get("CONTACTOR_MOBILE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("ARRIVE_TIME"))) {
					arriveTime = listDrive.get(i).get("ARRIVE_TIME").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("SERIES_NAME_1"))) {
					seriesName1 = listDrive.get(i).get("SERIES_NAME_1").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("IS_REGISTER"))) {
					isRegister = listDrive.get(i).get("IS_REGISTER").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("TEST_DRIVE_REGISTER"))) {
					testDriveRegister = listDrive.get(i).get("TEST_DRIVE_REGISTER").toString().substring(10);
					testDriveRegisterTime = listDrive.get(i).get("TEST_DRIVE_REGISTER").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("TEST_DRIVE_TYPE"))) {
					testDriveType = listDrive.get(i).get("TEST_DRIVE_TYPE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("PLACE"))) {
					place = listDrive.get(i).get("PLACE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("IS_FEEDBACK"))) {
					isFeedback = listDrive.get(i).get("IS_FEEDBACK").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("SCORE"))) {
					score = listDrive.get(i).get("SCORE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("TEST_DRIVE_FEEDBACK"))) {
					testDriveFeedback = listDrive.get(i).get("TEST_DRIVE_FEEDBACK").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("IS_STOCK_OUT"))) {
					isStockOut = listDrive.get(i).get("IS_STOCK_OUT").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("SERIES_NAME_2"))) {
					seriesName2 = listDrive.get(i).get("SERIES_NAME_2").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("STOCK_OUT_DATE"))) {
					stockOutDate = listDrive.get(i).get("STOCK_OUT_DATE").toString();
				}
				if (!StringUtils.isNullOrEmpty(listDrive.get(i).get("DEALER_CODE"))) {
					remark = listDrive.get(i).get("DEALER_CODE").toString();
				}
				PlanTestDriveDTO dto = new PlanTestDriveDTO();
				dto.setAscShortname(ascShortname);
				dto.setProvinceName(provinceName);
				dto.setCityName(cityName);
				dto.setSoldBy(soldBy);
				dto.setCustomerName(customerName);
				dto.setGender(gender);
				dto.setContactorPhone(contactorPhone);
				dto.setContactorMobile(contactorMobile);
				dto.setArriveTime(arriveTime);
				dto.setSeriesName1(seriesName1);
				dto.setIsRegister(isRegister);
				dto.setTestDriveRegister(testDriveRegister);
				dto.setTestDriveRegisterTime(testDriveRegisterTime);
				dto.setTestDriveType(testDriveType);
				dto.setPlace(place);
				dto.setIsFeedback(isFeedback);
				dto.setScore(score);
				dto.setTestDriveFeedback(testDriveFeedback);
				dto.setIsStockOut(isStockOut);
				dto.setSeriesName2(seriesName2);
				dto.setStockOutDate(stockOutDate);
				dto.setRemark(remark);
				resultList.add(dto);

			}
		}
		return resultList;
	}
}
