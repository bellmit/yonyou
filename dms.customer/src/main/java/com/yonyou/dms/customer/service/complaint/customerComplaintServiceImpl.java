package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.SuggestComplaintDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class customerComplaintServiceImpl implements customerComplaintService {

	@Autowired
	private CommonNoService commonNoService;

	// 新增客户投诉
	@Override
	public void addComplaint(SuggestComplaintDTO suggestComplaintDTO) throws ServiceBizException {

		CustomerComplaintPO ttcustomerComplaintPO = new CustomerComplaintPO();// 新增
		setvehiclePo(suggestComplaintDTO, ttcustomerComplaintPO);

	}

	private void setvehiclePo(SuggestComplaintDTO suggestComplaintDTO, CustomerComplaintPO ttcustomerComplaintPO) {

		ttcustomerComplaintPO.setString("COMPLAINT_NO",
				commonNoService.getSystemOrderNo(CommonConstants.COMPLAINTNO_PREFIX));
		ttcustomerComplaintPO.setString("COMPLAINT_NAME", suggestComplaintDTO.getComplaintName());// 投诉人姓名
		ttcustomerComplaintPO.setString("COMPLAINT_PHONE", suggestComplaintDTO.getComplaintPhone());// 投诉人电话
		ttcustomerComplaintPO.setString("COMPLAINT_MOBILE", suggestComplaintDTO.getComplaintMobile());// 投诉人手机
		ttcustomerComplaintPO.setInteger("COMPLAINT_MAIN_TYPE", suggestComplaintDTO.getComplaintMainType());// 投诉大类
		ttcustomerComplaintPO.setInteger("COMPLAINT_GENDER", suggestComplaintDTO.getComplaintGender());// 投诉人性别
		ttcustomerComplaintPO.setString("COMPLAINT_CORP", suggestComplaintDTO.getComplaintCorp());// 投诉人单位
		ttcustomerComplaintPO.setString("RECEPTIONIST", suggestComplaintDTO.getReceptionst());// 接待员
		ttcustomerComplaintPO.setDate("COMPLAINT_DATE", suggestComplaintDTO.getComplaintDate());// 投诉日期
		ttcustomerComplaintPO.setInteger("DEAL_STATUS", suggestComplaintDTO.getDealStatus());// 处理状态
		ttcustomerComplaintPO.setString("COMPLAINT_TYPE", suggestComplaintDTO.getComplaintType());// 投诉类型

		ttcustomerComplaintPO.setString("RO_NO", suggestComplaintDTO.getRoNo()); // 工单号
		ttcustomerComplaintPO.setDate("RO_CREATE_DATE", suggestComplaintDTO.getRoCreateDate());// 开单日期
		ttcustomerComplaintPO.setDouble("RO_TYPE", suggestComplaintDTO.getRoType());// 工单类型
		ttcustomerComplaintPO.setString("SERVICE_ADVISOR", suggestComplaintDTO.getServiceAdvisor());// 客户经理
		ttcustomerComplaintPO.setString("DELIVERER", suggestComplaintDTO.getDeliverer());// 送修人
		ttcustomerComplaintPO.setString("DELIVERER_PHONE", suggestComplaintDTO.getDelivererPhone());// 送修人电话
		ttcustomerComplaintPO.setString("DELIVERER_MOBILE", suggestComplaintDTO.getDelivererMobile());// 送修人手机
		ttcustomerComplaintPO.setString("TECHNICIAN", suggestComplaintDTO.getTechician());// 技师

		ttcustomerComplaintPO.setString("LICENSE", suggestComplaintDTO.getLicense());// 车牌号
		ttcustomerComplaintPO.setString("VIN", suggestComplaintDTO.getVin());// vin
		ttcustomerComplaintPO.setString("ENGINE_NO", suggestComplaintDTO.getEngineNo());// 发动机号
		ttcustomerComplaintPO.setDouble("IN_MILEAGE", suggestComplaintDTO.getInMileage()); // 行驶里程
		ttcustomerComplaintPO.setString("OWNER_NAME", suggestComplaintDTO.getOwnerName());// 车主
		ttcustomerComplaintPO.setDouble("OWNER_PROPERTY", suggestComplaintDTO.getOwnerProperty());// 车主性质
		ttcustomerComplaintPO.setString("ENGINE_NO", suggestComplaintDTO.getEngineNo());// 发动机号
		ttcustomerComplaintPO.setString("LINK_ADDRESS", suggestComplaintDTO.getLinkAddress()); // 车主地址
		ttcustomerComplaintPO.setDate("BUY_CAR_DATE", suggestComplaintDTO.getButCarDate()); // 购车日期期

		ttcustomerComplaintPO.setString("COMPLAINT_SUMMARY", suggestComplaintDTO.getComplaintSummary());// 投诉摘要
		ttcustomerComplaintPO.setString("COMPLAINT_REASON", suggestComplaintDTO.getComplaintReason());// 投诉原因
		ttcustomerComplaintPO.setString("RESOLVENT", suggestComplaintDTO.getResolvent());// 解决方案
		ttcustomerComplaintPO.setDouble("SURVEY_CONTENT", suggestComplaintDTO.getSurveyContent()); // 事实调查

		ttcustomerComplaintPO.setString("PRINCIPAL", suggestComplaintDTO.getPrincipal());// 处理人
		ttcustomerComplaintPO.setDouble("COMPLAINT_SERIOUS", suggestComplaintDTO.getComplaintSerious());// 严重性
		ttcustomerComplaintPO.setString("DEPARTMENT", suggestComplaintDTO.getDepartment());// 被投诉部门
		ttcustomerComplaintPO.setString("BE_COMPLAINT_EMP", suggestComplaintDTO.getBeComplaintEmp()); // 被投诉员工
		ttcustomerComplaintPO.setDouble("PRIORITY", suggestComplaintDTO.getPriority());// 优先级
		ttcustomerComplaintPO.setDouble("COMPLAINT_ORIGIN", suggestComplaintDTO.getComplaintOrigin());// 投诉来源

		ttcustomerComplaintPO.saveIt();

	}

	/**
	 * 查询部门
	 */
	@Override
	public PageInfoDto queryOrganization(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT DEALER_CODE,ORG_CODE,ORG_NAME FROM TM_ORGANIZATION");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 查询工单
	 */
	@Override
	public PageInfoDto searchRepairOrder(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"select '12781001' AS COLOR_FLAG, A.VIN,B.CONSULTANT,A.DEALER_CODE, A.RO_NO, A.SALES_PART_NO, A.BOOKING_ORDER_NO, A.ESTIMATE_NO,"
						+ "   A.RO_TYPE, A.REPAIR_TYPE_CODE, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC, "
						+ "    A.SEQUENCE_NO, A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE,"
						+ "    A.IS_CUSTOMER_IN_ASC, A.IS_SEASON_CHECK, A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE,"
						+ "   A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST, A.RECOMMEND_EMP_NAME,"
						+ "     A.RECOMMEND_CUSTOMER_NAME, A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS,te.EMPLOYEE_NAME,A.RO_STATUS,"
						+ "    A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, A.OWNER_NAME,"
						+ "    A.OWNER_PROPERTY, A.LICENSE,  A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.IN_MILEAGE,"
						+ "    A.OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE, A.TOTAL_CHANGE_MILEAGE,"
						+ "    A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, A.DELIVERER_PHONE,"
						+ "    A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG, A.WAIT_INFO_TAG, A.WAIT_PART_TAG,"
						+ "    A.COMPLETE_TIME, A.FOR_BALANCE_TIME, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LABOUR_PRICE"
						+ "    , A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT, A.ADD_ITEM_AMOUNT,"
						+ "    A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, A.BALANCE_AMOUNT,"
						+ "    A.RECEIVE_AMOUNT, A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT, A.TRACE_TAG, A.REMARK,"
						+ "    A.TEST_DRIVER, A.PRINT_RO_TIME, A.RO_CHARGE_TYPE, A.PRINT_RP_TIME, A.IS_ACTIVITY,"
						+ "    A.CUSTOMER_DESC, A.LOCK_USER, A.IS_CLOSE_RO, A.RO_SPLIT_STATUS,A.SO_NO,A.VER,A.IS_MAINTAIN,"
						+ "    A.IS_LARGESS_MAINTAIN,C.SALES_DATE,A.IS_QS, A.SCHEME_STATUS ,US.USER_NAME, "
						+ "    A.REMARK1, A.RO_TROUBLE_DESC,me.USER_NAME AS ct,US.USER_NAME AS LU,mee.user_name as sa,OT.ADDRESS"
						+ "  FROM TT_REPAIR_ORDER A "
						+ " LEFT JOIN TT_SALES_PART B ON A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO "
						+ " LEFT JOIN tm_employee te ON te.DEALER_CODE = A.DEALER_CODE AND A.SERVICE_ADVISOR_ASS = te.EMPLOYEE_NO "
						+ " LEFT JOIN TM_VEHICLE C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN "
						+ " LEFT JOIN TM_USER US ON A.LOCK_USER = US.USER_ID AND A.DEALER_CODE = US.DEALER_CODE "
						+ " LEFT JOIN TM_USER me ON me.DEALER_CODE = A.DEALER_CODE  AND me.USER_ID = A.CHIEF_TECHNICIAN "
						+ " LEFT JOIN TM_USER mee ON mee.DEALER_CODE = C.SERVICE_ADVISOR  AND mee.USER_ID = A.SERVICE_ADVISOR "
						+ " LEFT JOIN TM_OWNER OT ON OT.DEALER_CODE = A.DEALER_CODE AND OT.OWNER_NO = A.OWNER_NO "
						+ " WHERE A.DEALER_CODE ='" + dealerCode + "' " + " AND A.D_KEY=" + CommonConstants.D_KEY
						+ " ");

		sql.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("A", "RO_NO", queryParam.get("roNo"), "AND"));
		
		sql.append(Utility.getLikeCond("US", "USER_NAME", queryParam.get("applicant"), "AND"));
		sql.append(Utility.getLikeCond("A", "OWNER_NAME", queryParam.get("ownerName"), "AND"));
		sql.append(Utility.getLikeCond("A", "RO_STATUS", queryParam.get("roStatus"), "AND"));
		
		List<Object> params = new ArrayList<Object>();
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

}
