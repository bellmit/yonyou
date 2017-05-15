package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS020Dto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmWxOwnerChangePO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 微信车主信息下发
 * @author Administrator
 *
 */
@Service
public class SADMS020Impl implements SADMS020 {

	final Logger logger = Logger.getLogger(SADMS020Impl.class);

	/**
	 * @description 微信车主信息下发
	 * @param dealerCode
	 * @param SADMS020Dtos
	 */
	public int getSADMS020(String dealerCode, List<SADMS020Dto> SADMS020Dtos) {
		logger.info("==========SADMS020Impl执行===========");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		try {
			if (dealerCode == null || dealerCode.isEmpty()) {
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if (SADMS020Dtos != null && !SADMS020Dtos.isEmpty()) {
				for (SADMS020Dto sadms020Dto : SADMS020Dtos) {
					if (Utility.testString(sadms020Dto.getVin()) && Utility.testString(sadms020Dto.getDealerCode())) {
						String sql = "SELECT * from Tm_Wx_Owner_Change where vin='" + sadms020Dto.getVin()
								+ "' and Dealer_Code='" + sadms020Dto.getDealerCode() + "' ";
						List<Map> list = OemDAOUtil.findAll(sql, null);
						// TmWxOwnerChangePO tmWxOwnerChangePO =
						// TmWxOwnerChangePO
						// .findByCompositeKeys(sadms020Dto.getVin(),
						// sadms020Dto.getDealerCode());
						if (list != null) {
							TmWxOwnerChangePO.update(
									"ADDRESS=?,OWNER_NAME=?,MOBILE=?,GENDER=?,E_MAIL=?,ZIP_CODE=?,UPDATE_BY=?,UPDATE_DATE=?",
									"vin=? and Dealer_Code=? ", sadms020Dto.getAddress(), sadms020Dto.getOwnerName(),
									sadms020Dto.getMobile(), sadms020Dto.getGender(), sadms020Dto.geteMail(),
									sadms020Dto.getZipCode(), CommonConstants.DE_CREATE_UPDATE_BY, format,
									sadms020Dto.getVin(), sadms020Dto.getDealerCode());

							// tmWxOwnerChangePO.setString("ADDRESS",
							// sadms020Dto.getAddress());// 地址
							// tmWxOwnerChangePO.setString("OWNER_NAME",
							// sadms020Dto.getOwnerName());// 客户姓名
							// tmWxOwnerChangePO.setString("MOBILE",
							// sadms020Dto.getMobile());
							// tmWxOwnerChangePO.setInteger("GENDER",
							// sadms020Dto.getGender());
							// tmWxOwnerChangePO.setString("E_MAIL",
							// sadms020Dto.geteMail());
							// tmWxOwnerChangePO.setString("ZIP_CODE",
							// sadms020Dto.getZipCode());
							// tmWxOwnerChangePO.setLong("UPDATE_BY",
							// CommonConstants.DE_CREATE_UPDATE_BY);
							// tmWxOwnerChangePO.setTimestamp("UPDATE_DATE",
							// format);
						} else {
							TmWxOwnerChangePO tmWxOwnerChangePO = new TmWxOwnerChangePO();
							tmWxOwnerChangePO.setString("OWNER_NO", sadms020Dto.getCustomerNo());// 客户ID
							tmWxOwnerChangePO.setString("DEALER_CODE", sadms020Dto.getDealerCode());
							tmWxOwnerChangePO.setString("VIN", sadms020Dto.getVin());// vin号
							tmWxOwnerChangePO.setString("ADDRESS", sadms020Dto.getAddress());// 地址
							tmWxOwnerChangePO.setString("OWNER_NAME", sadms020Dto.getOwnerName());// 客户姓名
							tmWxOwnerChangePO.setString("MOBILE", sadms020Dto.getMobile());// 客户手机
							tmWxOwnerChangePO.setInteger("GENDER", sadms020Dto.getGender());// 性别
							tmWxOwnerChangePO.setString("E_MAIL", sadms020Dto.geteMail());// email
							tmWxOwnerChangePO.setString("ZIP_CODE", sadms020Dto.getZipCode());// 邮编
							tmWxOwnerChangePO.setLong("CREATE_BY", CommonConstants.DE_CREATE_UPDATE_BY);
							tmWxOwnerChangePO.setTimestamp("CREATE_DATE", format);
							tmWxOwnerChangePO.insert();
						}
						// 校验tm_vehicle 如果DMS存在该车对应的客户，则直接以微信车主信息覆盖掉DMS的客户信息
						logger.debug("from TmVehiclePO dealer_code = " + sadms020Dto.getDealerCode() + " and vin = "
								+ sadms020Dto.getVin());
						LazyList<VehiclePO> tmVehiclePOs = VehiclePO.find("DEALER_CODE = ? and VIN = ? ",
								sadms020Dto.getDealerCode(), sadms020Dto.getVin());
						if (tmVehiclePOs != null && !tmVehiclePOs.isEmpty()) {
							VehiclePO poSource = tmVehiclePOs.get(0);
							if ((!"888888888888".equals(poSource.getString("OWNER_NO")))
									&& (!"999999999999".equals(poSource.getString("OWNER_NO")))) {

								logger.debug("update CarownerPO set Address = " + sadms020Dto.getAddress()
										+ " and OwnerName = " + sadms020Dto.getOwnerName() + ", mobile = "
										+ sadms020Dto.getMobile() + ", Gender = " + sadms020Dto.getGender()
										+ " , e_mail = " + sadms020Dto.geteMail() + " , zip_Code = "
										+ sadms020Dto.getZipCode() + " where dealer_code = "
										+ sadms020Dto.getDealerCode() + " and OwnerNo = "
										+ poSource.getString("Owner_No"));
								CarownerPO.update(
										"ADDRESS = ? , OWNER_NAME = ?, MOBILE = ?, GENDER = ? , E_MAIL = ? , ZIP_CODE = ?",
										"DEALER_CODE = ? and OWNER_NO = ?", sadms020Dto.getAddress(),
										sadms020Dto.getOwnerName(), sadms020Dto.getMobile(), sadms020Dto.getGender(),
										sadms020Dto.geteMail(), sadms020Dto.getZipCode(), sadms020Dto.getDealerCode(),
										poSource.getString("OWNER_NO"));
							}
						}
					} else {
						logger.debug("集合中 dealerCode or vin 为空，方法中断");
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
			logger.info("==========SADMS020Impl执行===========");
		}
	}
}
