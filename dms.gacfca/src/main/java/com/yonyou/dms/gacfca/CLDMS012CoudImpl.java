package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;
import com.yonyou.dms.common.domains.DTO.basedata.OutBoundReturnDTO;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 接收 400 外呼核实结果实现类
 * @author wangliang
 * @date 2017年4月18日
 */
@Service
public class CLDMS012CoudImpl implements CLDMS012Coud {
	private String owNo="%888888888888%";
	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getCLDMS012(LinkedList<OutBoundReturnDTO> voList, String dealerCode) throws ServiceBizException {
		try {
			System.err.println(voList);
			System.err.println(dealerCode);
			if (voList != null && voList.size() > 0) {
				for (int i = 0; i < voList.size(); i++) {
					OutBoundReturnDTO dto = new OutBoundReturnDTO();
					dto = voList.get(i);
					if ("2".equals(dto.getObFlag())) {
						if (Utility.testString(dto.getDmsOwnerId())) {
							String str = dto.getDmsOwnerId().substring(0, 2);
							if ("PU".equalsIgnoreCase(str)) {
								System.err.println(dealerCode);
								System.err.println(dto.getDmsOwnerId());
								
								StringBuffer sb = new StringBuffer();
								sb.append("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = '"+dealerCode+"' AND CUSTOMER_NO = '"+dto.getDmsOwnerId()+"'");
								List<Map> tpo3 = Base.findAll(sb.toString());
								System.err.println(tpo3);
								
								String IS_BINDING = "";
								if (!StringUtils.isNullOrEmpty(tpo3)) {
									if ("1".equals(dto.getIsBinding())) {
										IS_BINDING = DictCodeConstants.DICT_IS_YES;
									} else if ("0".equals(dto.getIsBinding())) {
										IS_BINDING = DictCodeConstants.DICT_IS_NO;
									}
									
									PotentialCusPO.update("IS_BINDING=? , BINDING_DATE=?", 
											"DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?", IS_BINDING,new Date(),dealerCode,dto.getDmsOwnerId(),0);
									System.err.println("=============================更新成功================================");
								}
								List<Map> pList = Base.findAll("SELECT * FROM tt_po_cus_relation WHERE DEALER_CODE = '" + dealerCode+ "' AND PO_CUSTOMER_NO = '" + dto.getDmsOwnerId() + "' ");
                                System.err.println(pList);
								if (pList != null && pList.size() > 0) {
									for (int y = 0; y < pList.size(); y++) {
										Map pc2 = pList.get(i);
										String IsBinding = "";
										if (Utility.testString(pc2.get("VIN").toString())) {
											if ("1".equals(dto.getIsBinding())) {
												IsBinding = DictCodeConstants.DICT_IS_YES;
											} else if ("0".equals(dto.getIsBinding())) {
												IsBinding = DictCodeConstants.DICT_IS_NO;
											}
											VehiclePO.update("IS_BINDING=? , BINDING_DATE=?", "DEALER_CODE=? AND VIN=?",
													IsBinding,new Date(),dealerCode,pc2.get("VIN").toString());
											System.err.println("+++++++++++++++更新成功+++++++++++++++++++");
										}
									}
								}
							}

						}
					} else {

						if (Utility.testString(dto.getDmsOwnerId())) {
							
						    System.err.println(dto.getDmsOwnerId());
							List<Map> list1 = Base.findAll("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = '" + dealerCode+ "' AND CUSTOMER_NO = '" + dto.getDmsOwnerId() + "'");
							
							if (!StringUtils.isNullOrEmpty(dto.getOutboundTime())) {
								PotentialCusPO.update("Outbound_Return_Time=?", "DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?",
										dto.getOutboundTime(),dealerCode,dto.getDmsOwnerId(),0);
							}
							if (!StringUtils.isNullOrEmpty(list1)) {// 潜客编号没有查到数据
								String CUSTOMER_TYPE = null;
								int GENDER = 0;
								if ("1".equals(dto.getIsUpdate())) {
									if ("1".equals(dto.getClientType())) {
										CUSTOMER_TYPE=DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL;// 个人
									} else {
										CUSTOMER_TYPE=DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY;// 公司
									}
									if ("1".equals(dto.getGender())) {
										GENDER=DictCodeConstants.DICT_GENDER_MAN;
									} else {
										GENDER=DictCodeConstants.DICT_GENDER_WOMAN;
									}
									
									// 看是否有保有客户 有则更新信息
									String customerNo = this.checkCustomerInfo(dto.getVin(), dealerCode);
									if (Utility.testString(customerNo)) {
										int customerType = 0;
										int gender = 0;
										if ("1".equals(dto.getClientType())) {
											customerType = Utility.getInt(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL);// 个人
										} else {
											customerType = Utility.getInt(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY);// 公司
										}
										if ("1".equals(dto.getGender())) {
											gender = DictCodeConstants.DICT_GENDER_MAN;
										} else {
											gender = DictCodeConstants.DICT_GENDER_WOMAN;
										}

										CustomerPO.update("CUSTOMER_NAME=? , CUSTOMER_TYPE=? , GENDER=? , ADDRESS=? , PROVINCE=?"
												+ " , CITY=? , DISTRICT=? , ZIP_CODE=? , E_MAIL=? , CERTIFICATE_NO=?",
												"DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?",dto.getName(),customerType,gender,dto.getAddress(),
												dto.getProvinceId(),Utility.getInt(dto.getCityId()),Utility.getInt(dto.getDistrict()),
												dto.getPostCode(),dto.getEmail(),dto.getIdOrCompCode(),dealerCode,customerNo,0);
										System.err.println("====================tm_customer更新成功========================");
									}
									// 是否存在车主信息
									String ownerNo = this.queryOwner(dto.getVin(), dealerCode);
									if (StringUtils.isNullOrEmpty(ownerNo)) {
										int customerType = 0;
										int gender = 0;
										if ("1".equals(dto.getClientType())) {
											customerType = Utility.getInt(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL);// 个人
										} else {
											customerType = Utility.getInt(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY);// 公司
										}
										if ("1".equals(dto.getGender())) {
											gender = DictCodeConstants.DICT_GENDER_MAN;
										} else {
											gender = DictCodeConstants.DICT_GENDER_WOMAN;
										}
										
										CarownerPO.update("OWNER_NAME=? , CUSTOMER_TYPE=? , GENDER=? , ADDRESS=? , PROVINCE=?"
												+ " , CITY=? , DISTRICT=? , ZIP_CODE=? , E_MAIL=? , CERTIFICATE_NO=?",
												"DEALER_CODE=? AND OWNER_NO=?",
												dto.getName(),customerType,gender,dto.getAddress(),dto.getProvinceId(),Utility.getInt(dto.getCityId()),
												Utility.getInt(dto.getDistrict()),dto.getPostCode(),dto.getEmail(),dto.getIdOrCompCode(),dealerCode,ownerNo);
									}
								}

								String IS_VERIFY_ADDRESS = null;
								String IS_OUTBOUND = null;
								String OB_IS_SUCCESS = null;
								String REASONS = null;
								String IS_UPLOAD = null;
								String IS_OWNER = null;
								if ("1".equals(dto.getIsVerifyAddress())) {
									IS_VERIFY_ADDRESS = DictCodeConstants.DICT_IS_YES;// 是否核实地址(1、是 0、否)
								} else if ("0".equals(dto.getIsVerifyAddress())) {
									IS_VERIFY_ADDRESS = DictCodeConstants.DICT_IS_NO;
								}
								if ("2".equals(dto.getIsOutbound())) {
									IS_OUTBOUND = DictCodeConstants.DICT_IS_YES;// 数据是否外呼
								} else if ("1".equals(dto.getIsOutbound())) {
									IS_OUTBOUND = DictCodeConstants.DICT_IS_NO;
								}
								if ("1".equals(dto.getObIsSuccess())) {
									OB_IS_SUCCESS = DictCodeConstants.DICT_IS_YES;// 数据是否外呼
								} else if ("0".equals(dto.getObIsSuccess())) {
									OB_IS_SUCCESS = DictCodeConstants.DICT_IS_NO;
								}
								// 失败原因 (obIsSuccess=0) 1、非机主 2、非车主 3、空号/错号
								// 4、占线/无人接听/停机
								// 外呼成功原因(obIsSuccess=1) 5、需要联系 6、成功核实 7、信息未核实
								switch (Utility.getInt(dto.getReasons())) {
								case 1:
									REASONS = DictCodeConstants.DICT_OUTBOUND_FAILED_REASONS_NOT_PHONEOWNER;
									break;
								case 2:
									REASONS = DictCodeConstants.DICT_OUTBOUND_FAILED_REASONS_NOT_CAROWNER;
									break;
								case 3:
									REASONS = DictCodeConstants.DICT_OUTBOUND_FAILED_REASONS_NULL_NUMBER;
									break;
								case 4:
									REASONS = DictCodeConstants.DICT_OUTBOUND_FAILED_REASONS_BUSY_LINE;
									break;
								case 5:
									REASONS = DictCodeConstants.DICT_OUTBOUND_SUCCESS_REASONS_NEED_CONTACT;
									break;
								case 6:
									REASONS = DictCodeConstants.DICT_OUTBOUND_SUCCESS_REASONS_SUCCESS_CHECK;
									break;
								case 7:
									REASONS = DictCodeConstants.DICT_OUTBOUND_SUCCESS_REASONS_INFO_UNCHECK;
									break;
								}
								if ("1".equals(dto.getIsUpdate())) {
									IS_UPLOAD = DictCodeConstants.DICT_IS_YES;// 客户信息是否更新
								} else if ("0".equals(dto.getIsUpdate())) {
									IS_UPLOAD = DictCodeConstants.DICT_IS_NO;
								}
								//tpo2.setString("OUTBOUND_REMARK", dto.getOutboundRemark());// 外呼备注
								if ("1".equals(dto.getIsOwner())) {
									IS_OWNER = DictCodeConstants.DICT_IS_YES;
								} else if ("2".equals(dto.getIsOwner())) {
									IS_OWNER = DictCodeConstants.DICT_IS_NO;
								}
								//tpo2.setDate("OUTBOUND_TIME", dto.getOutboundTime()); // 外呼时间
								
								System.err.println(dto.getDmsOwnerId());
								PotentialCusPO.update("CUSTOMER_NAME=? , CUSTOMER_TYPE=? , GENDER=? , ADDRESS=? , "
										+ "PROVINCE=? , CITY=? , DISTRICT=? , ZIP_CODE=? , E_MAIL=? , CERTIFICATE_NO=? , "
										+ "IS_VERIFY_ADDRESS=? , IS_OUTBOUND=? , OB_IS_SUCCESS=? , REASONS=? , "
										+ "IS_UPLOAD=? , OUTBOUND_REMARK=? , IS_OWNER=? , OUTBOUND_TIME=?",
										"DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?", dto.getName(),CUSTOMER_TYPE,GENDER,dto.getAddress(),
										Utility.getInt(dto.getProvinceId()),Utility.getInt(dto.getCityId()),Utility.getInt(dto.getDistrict()),
										dto.getPostCode(),dto.getEmail(),dto.getIdOrCompCode(),IS_VERIFY_ADDRESS,IS_OUTBOUND,OB_IS_SUCCESS,
										REASONS,IS_UPLOAD,dto.getOutboundRemark(),IS_OWNER,dto.getOutboundTime(),dealerCode,dto.getDmsOwnerId(),0);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}
	
	
	@SuppressWarnings("rawtypes")
	public String checkCustomerInfo(String vin,String dealerCode) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from tm_vehicle where DEALER_CODE = '"+dealerCode+"' and vin='"+vin+"' ");
		List<Map> list = Base.findAll(sb.toString());
		Map map = list.get(0);
		return map.get("CUSTOMER_NO").toString();
	}
	
	/**
	 * 是否存在车主信息
	 */
	@SuppressWarnings("rawtypes")
	public String queryOwner(String vin,String dealerCode) {
		StringBuffer sb = new StringBuffer();
		sb.append(" select * from tm_vehicle where DEALER_CODE ='"+dealerCode+"' and vin='"+vin+"' ");
		sb.append(" and OWNER_NO not like '"+owNo+"' ");
		List<Map> list = Base.findAll(sb.toString());
		Map map = list.get(0);
		return map.get("OWNER_NO").toString();
	}
	
}
