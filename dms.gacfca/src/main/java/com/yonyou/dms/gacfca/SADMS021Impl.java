package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS021Dto;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmWxServiceAdvisorChangePO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 一对一客户经理绑定修改下发
 * @author Administrator
 *
 */
@Service
public class SADMS021Impl implements SADMS021 {

	final Logger logger = Logger.getLogger(SADMS021Impl.class);

	/**
	 * @description 一对一客户经理绑定修改下发
	 * @param dealerCode
	 * @param SADMS020Dtos
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int getSADMS021(String dealerCode, LinkedList<SADMS021Dto> SADMS020Dtos) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("==========SADMS021Impl执行===========");
		try {
			if (dealerCode == null) {
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			// 判断是否为空,循环操作，根据业务相应的修改数据
			if (SADMS020Dtos != null && !SADMS020Dtos.isEmpty()) {
				for (SADMS021Dto SADMS021Dto : SADMS020Dtos) {
					if (Utility.testString(SADMS021Dto.getVin()) && Utility.testString(SADMS021Dto.getEntityCode())) {
						logger.debug("from TmWxServiceAdvisorChangePO DEALER_CODE = " + SADMS021Dto.getEntityCode()
								+ " and VIN = " + SADMS021Dto.getVin());
						List<Map> tmWxServiceAdvisorChangePOs = Base.findAll(
								"select  *  from TM_WX_SERVICE_ADVISOR_CHANGE where VIN=? AND DEALER_CODE=?",
								new Object[] { SADMS021Dto.getVin(), dealerCode });
						if (tmWxServiceAdvisorChangePOs != null && tmWxServiceAdvisorChangePOs.size() > 0) {
							// 绑定类型：0为DMS;1为微信
							Integer boundType = null;
							if (SADMS021Dto.getBoundType().equals(1)) {
								boundType = 15991001;
							} else if (SADMS021Dto.getBoundType().equals(0)) {
								boundType = 15991002;
							}
							TmWxServiceAdvisorChangePO.update(
									"SERVICE_ADVISOR = ?,MOBILE = ?,EMPLOYEE_NAME = ?, BOUND_TYPE = ?, UPDATED_BY = ?,UPDATED_AT = ?",
									"DEALER_CODE = ? and VIN = ?", SADMS021Dto.getServiceAdvisor(),
									SADMS021Dto.getMobile(), SADMS021Dto.getEmployeeName(), boundType,
									CommonConstants.DE_CREATE_UPDATE_BY, format, dealerCode, SADMS021Dto.getVin());
						} else {
							TmWxServiceAdvisorChangePO tmWxServiceAdvisorChangePO = new TmWxServiceAdvisorChangePO();
							tmWxServiceAdvisorChangePO.setString("DEALER_CODE", dealerCode);
							tmWxServiceAdvisorChangePO.setString("SERVICE_ADVISOR", SADMS021Dto.getServiceAdvisor());// 客户经理ID
							tmWxServiceAdvisorChangePO.setString("VIN", SADMS021Dto.getVin());// vin号
							tmWxServiceAdvisorChangePO.setString("MOBILE", SADMS021Dto.getMobile());// 联系电话
							tmWxServiceAdvisorChangePO.setString("EMPLOYEE_NAME", SADMS021Dto.getEmployeeName());// 客户经理姓名
							if (SADMS021Dto.getBoundType().equals(1)) {
								tmWxServiceAdvisorChangePO.setInteger("BOUND_TYPE", 15991001);// 绑定类型：0为DMS;1为微信
							} else if (SADMS021Dto.getBoundType().equals(0)) {
								tmWxServiceAdvisorChangePO.setInteger("BOUND_TYPE", 15991002);// 绑定类型：0为DMS;1为微信
							}
							tmWxServiceAdvisorChangePO.setLong("CREATED_BY", 1L);

							// tmWxServiceAdvisorChangePO.setDate("CREATED_AT",new
							// Date());
							tmWxServiceAdvisorChangePO.insert();
						}
						// 校验tm_vehicle 有车修改，无车则不做任何操作
						List<Map> vList = Base.findAll(
								"select  *  from TM_VEHICLE where DEALER_CODE = ? and VIN = ? ", dealerCode,
								SADMS021Dto.getVin());
						if (vList != null && vList.size() > 0) {
							// 校验客户经理ID是否存在于TM_EMPLOYEE
							List<Map> elist = Base.findAll(
									"select  *  from TM_EMPLOYEE where DEALER_CODE = ? and EMPLOYEE_NO = ?", dealerCode,
									SADMS021Dto.getServiceAdvisor());
							if (elist != null && elist.size() > 0) {
								VehiclePO.update("SERVICE_ADVISOR = ?,UPDATED_BY = ?,UPDATED_AT = ?",
										"DEALER_CODE = ? and VIN = ?", SADMS021Dto.getServiceAdvisor(),
										CommonConstants.DE_CREATE_UPDATE_BY, format, dealerCode,
										SADMS021Dto.getVin());
							}
						}
					} else {
						logger.debug("vin号和dealerCode为空，");
						return 0;
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		} finally {
			logger.info("==========SADMS021Impl结束===========");
		}

	}

}
