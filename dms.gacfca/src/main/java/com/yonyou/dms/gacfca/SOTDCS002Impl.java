package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS002Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNSalesPersonnelDto;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS002Impl implements SOTDCS002 {
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS002Impl.class);
	
	@Autowired SOTDCS002Cloud SOTDCS002Cloud;

	@SuppressWarnings("rawtypes")
	@Override
	public int getSOTDCS002(String isChgUserStatus, String userid)
			throws ServiceBizException {
		try {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			// 根据业务参数工具开关来判断是否执行上报
			String defautParam = Utility.getDefaultValue(dealerCode);
			if (defautParam != null && defautParam.equals(DictCodeConstants.DICT_IS_NO)) {

			}
			long userId = 0;
			// 前台用户停用与启用触发点
			if (Utility.testString(isChgUserStatus) && DictCodeConstants.DICT_IS_YES.equals(isChgUserStatus)) {
				if (Utility.testString(userid)) {
					userId = Long.valueOf(FrameworkUtil.getLoginInfo().getUserId());
				}
			} else {
				userId = FrameworkUtil.getLoginInfo().getUserId();
			}
			if (userId == 0) {
				throw new ServiceBizException("上报用户ID为空返回");
			}
			LinkedList<TiDmsNSalesPersonnelDto> resultList = new LinkedList<TiDmsNSalesPersonnelDto>();
			UserPO tUserPo = new UserPO();
			List<UserPO> ulist = UserPO.find("DEALER_CODE= "+dealerCode+" AND USER_ID=?", userId);
			if (ulist != null && ulist.size() > 0) {
				tUserPo = ulist.get(0);
				TiDmsNSalesPersonnelDto dto = new TiDmsNSalesPersonnelDto();
				dto.setUserId(tUserPo.getString("USER_ID").toString());
				dto.setDealerCode(tUserPo.getString("DEALER_CODE"));
				dto.setUserCode(tUserPo.getString("USER_CODE"));
				dto.setUserName(tUserPo.getString("USER_NAME"));
				dto.setUserStatus(tUserPo.getString("USER_STATUS"));
				dto.setSalPassword(tUserPo.getString("PASSWORD"));
				EmployeePo te = new EmployeePo();
				List<EmployeePo> eList = EmployeePo.find("DEALER_CODE= "+dealerCode+" AND EMPLOYEE_NO=?", tUserPo.getString("EMPLOYEE_NO"));
				if (eList != null && eList.size() > 0) {
					te = eList.get(0);
					dto.setEmail(te.getString("E_MAIL"));
					dto.setMobile(te.getString("MOBILE"));
				}
				List<Map> vDOIList = viewDccOptionInfoList(dealerCode, tUserPo.getString("USER_ID").toString());
				if (vDOIList != null && vDOIList.size() > 0) {
					dto.setIsDccView(DictCodeConstants.DICT_IS_YES);
				} else {
					dto.setIsDccView(DictCodeConstants.DICT_IS_NO);
				}
				List<Map> uRIList = listUserRoleInfo(dealerCode, tUserPo.getString("USER_ID").toString());
				if (uRIList != null && uRIList.size() > 0) {
					Map utlist = uRIList.get(0);
					dto.setRoleId(utlist.get("ROLE_CODE").toString());
					dto.setRoleName(utlist.get("ROLE_NAME").toString());
				}
				resultList.add(dto);
			}
			logger.info("=====SOTDCS002=====");
			SOTDCS002Cloud.handleExecutor(resultList);
			
		} catch (Exception e) {
			logger.debug(e.toString());
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("rawtypes")
	private List<Map> viewDccOptionInfoList(String dealerCode, String userId) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT A.DEALER_CODE,A.USER_ID,A.USER_CODE,c.OPTION_CODE  ");
		sql.append(" FROM TM_USER A  ");
		sql.append(" INNER JOIN TT_USER_OPTION_MAPPING B ");
		sql.append(" ON A.DEALER_CODE=B.DEALER_CODE ");
		sql.append(" AND A.USER_ID=B.USER_ID ");
		sql.append(" INNER JOIN TM_AUTH_OPTION c ");
		sql.append(" ON C.OPTION_CODE = B.OPTION_CODE  ");
		sql.append(" AND C.OPTION_CODE = '80800000' ");
		sql.append(" WHERE A.DEALER_CODE= '" + dealerCode + "'");
		sql.append(" AND A.USER_ID= " + userId + " ");
		logger.info(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	@SuppressWarnings("rawtypes")
	private List<Map> listUserRoleInfo(String dealerCode, String userId) {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ");
		sql.append(" A.DEALER_CODE,A.USER_ID,B.ROLE_CODE,c.ROLE_NAME ");
		sql.append(" from TM_USER A ");
		sql.append(" INNER JOIN TT_USER_ROLE_MAPPING B ");
		sql.append(" ON A.DEALER_CODE=B.DEALER_CODE ");
		sql.append(" AND A.USER_ID=B.USER_ID");
		sql.append(" INNER JOIN TM_ROLE c");
		sql.append(" ON B.DEALER_CODE=C.DEALER_CODE");
		sql.append(" AND B.ROLE_CODE=C.ROLE_CODE ");
		sql.append(" WHERE A.DEALER_CODE = " + dealerCode + " ");
		sql.append(" AND A.USER_ID= " + userId + " ");
		logger.info(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;

	}
}
