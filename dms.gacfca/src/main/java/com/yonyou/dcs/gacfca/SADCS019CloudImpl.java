package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.ProperServManInfoDTO;
import com.yonyou.dms.common.domains.PO.customer.TiWxCustomerManagerBindingPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SADCS019CloudImpl extends BaseCloudImpl implements SADCS019Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS019CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<ProperServManInfoDTO> dto) throws Exception {
		String msg = "1";
		logger.info("====默认客户经理接口/离职客户经理修改接口接受开始====");
		for (ProperServManInfoDTO vo : dto) {
			try {
				insertData(vo);
			} catch (Exception e) {
				logger.error("默认客户经理接口/离职客户经理修改接口接受失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====默认客户经理接口/离职客户经理修改接口接受结束====");
		return msg;
	}

	private void insertData(ProperServManInfoDTO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("####################### 开始处理数据 ################################ ");
		try {
			if (CommonUtils.checkIsNull(vo.getDealerCode())) {
				throw new Exception("经销商代码为空！");
			}
			if (CommonUtils.checkIsNull(vo.getServiceAdviser())) {
				throw new Exception("客户经理为空！");
			}
			Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			TiWxCustomerManagerBindingPO bindPO = new TiWxCustomerManagerBindingPO();
			bindPO.setString("DEALER_CODE", dealerCode);
			bindPO.setString("SERVICE_ADVISOR", vo.getServiceAdviser());
			bindPO.setString("EMPLOYEE_NAME", vo.getEmployeeName());
			bindPO.setString("MOBILE", vo.getMobile());
			bindPO.setString("IS_VALID", vo.getIsValid());
			bindPO.setString("IS_SERVICE_ADVISOR", vo.getIsServiceAdvisor());

			// new add by wangjian 2015-5-27
			bindPO.setString("IS_DEFAULT_WX_SA", vo.getIsDefaultWxSa());// 是否微信默认客户经理

			logger.info("### 存在客户经理 ： " + vo.getServiceAdviser() + ",客户经理:" + vo.getServiceAdviser() + ",是否默认:"
					+ vo.getIsDefaultWxSa());

			// 判断客户经理是否存在
			if (null != findById(vo, dealerCode)) {
				logger.info("####################### 存在客户经理 ################################ ");
				Long ct = findById(vo, dealerCode);
				TiWxCustomerManagerBindingPO bindPO1 = TiWxCustomerManagerBindingPO.findFirst("MANAGER_ID=?", ct);
				bindPO1.setInteger("IS_SCAN", "0");
				bindPO1.setString("RESULT_VALUE", "update");
				bindPO1.setLong("Update_By", 999999999L);
				bindPO1.setTimestamp("Update_Date", format);
				bindPO.setString("DEALER_CODE", dealerCode);
				bindPO1.setString("SERVICE_ADVISOR", vo.getServiceAdviser());
				bindPO1.setString("EMPLOYEE_NAME", vo.getEmployeeName());
				bindPO1.setString("MOBILE", vo.getMobile());
				bindPO1.setString("IS_VALID", vo.getIsValid());
				bindPO1.setString("IS_SERVICE_ADVISOR", vo.getIsServiceAdvisor());

				// new add by wangjian 2015-5-27
				bindPO1.setString("IS_DEFAULT_WX_SA", vo.getIsDefaultWxSa());// 是否微信默认客户经理

				if (vo.getEmployeeStatus().equals("A")) {
					bindPO1.setString("Is_Update", "0");
				} else {
					bindPO1.setString("Is_Update", "1");
				}
				bindPO1.saveIt();
			} else {
				logger.info("####################### 不存在客户经理 ################################ ");
				bindPO.setString("IS_SCAN", "0");
				bindPO.setLong("CREATE_BY", 999999999L);
				bindPO.setTimestamp("CREATE_DATE", new Date());
				bindPO.setInteger("IS_ARC", 0);
				bindPO.setInteger("IS_DEL", 0);
				bindPO.setInteger("VER", 0);
				bindPO.setString("IS_UPDATE", "0");
				bindPO.insert();// 插入经销商专属客户经理数据
				bindPO.setString("IS_DEFAULT_WX_SA", vo.getIsDefaultWxSa());// 是否微信默认客户经理

				if (vo.getEmployeeStatus().equals("A")) {
					bindPO.setString("Is_Update", "0");
				} else {
					bindPO.setString("Is_Update", "1");
				}
				bindPO.saveIt();
				logger.info("====默认客户经理接口/离职客户经理修改接口接受成功====");
			}
			if (null != vo.getIsDefaultWxSa()) {
				if (vo.getIsDefaultWxSa().equals(12781001)) { // 判断当前数据客户经理是否默认经理
					String sql = " update ti_wx_customer_manager_binding_dcs set is_default_wx_sa = 12781002 where dealer_code = '"
							+ dealerCode + "'"
							+ "  and is_valid = 12781001 and is_default_wx_sa = 12781001 and  Service_Advisor <> '"
							+ vo.getServiceAdviser() + "'";
					OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
					// LOG.info("############ 受影响的行数:" + cc);
				}
			}
		} catch (Exception e) {
			logger.error("默认客户经理接口/离职客户经理修改接口接受失败", e);
			throw new ServiceBizException(e);
		}

	}

	private Long findById(ProperServManInfoDTO vo, String dealerCode) {

		Long documentId = 0L;
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT MANAGER_ID  \n");
			sql.append(" From ti_wx_customer_manager_binding_dcs   ");
			sql.append(" WHERE 1=1 ");
			sql.append(" AND DEALER_CODE ='").append(dealerCode).append("' \n");
			sql.append(" AND SERVICE_ADVISOR ='" + vo.getServiceAdviser() + "' \n");
			sql.append(" ORDER BY MANAGER_ID DESC");
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);

			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("MANAGER_ID");
					return documentId;
				}

			}
		} catch (Exception e) {
			logger.error("默认客户经理接口/离职客户经理修改校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
