package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.gacfca.SADCS003ReturnCloud;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;
import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SystemStatusPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;

/**
 * 业务描述：DCC潜在客户信息下发
 * 
 * @author Benzc
 * @date 2017年2月8日
 */
@Service
public class SADMS003CoudImpl implements SADMS003Coud {

	private static final Logger logger = LoggerFactory.getLogger(SADMS003CoudImpl.class);

	@Autowired
	private CommonNoService commonNoService;
	
	@Autowired SADCS003ReturnCloud SADCS003Return;

	@SuppressWarnings({ "static-access", "rawtypes", "null" })
	@Override
	public int getSADMS003(dccDto dtodDto) throws ServiceBizException {

		try {
			LinkedList<dccDto> dtoList = new LinkedList<>();
			dtoList.add(dtodDto);
			// LinkedList dtoList = new LinkedList();
			String dealerCode = dtodDto.getEntityCode();
			if (dtoList != null && dtoList.size() > 0) {
				LinkedList<SADMS003ForeReturnDTO> resultList = new LinkedList<SADMS003ForeReturnDTO>();
				for (int i = 0; i < dtoList.size(); i++) {
					dccDto dto = new dccDto();
					dto = dtoList.get(0);
					if (!StringUtils.isEmpty(dto.getDmsCustomerId())) {// 存在原来就有这个客户
						List<Map> oldmap = Base.findAll(
								"SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = ? AND CUSTOMER_NO = ? AND D_KYE = ? ", dealerCode,
								dto.getDmsCustomerId(), 0);
						Map oldpo = oldmap.get(i);
						if (oldpo != null) {
							List<Map> updatemap = Base.findAll(
									"SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE OEM_CUSTOMER_NO = ? AND VALIDITY_BEGIN_DATE = ? AND CUS_SOURCE = ? AND CUSTOMER_TYPE = ?",
									dto.getNid().toString(), new Date(), 13111016, 10181001);
							Map updatepo = updatemap.get(i);
							// dealerUserName;//电话营销员 salesConsultant;//负责销售顾问
							String remark = "";
							if (!StringUtils.isEmpty(dto.getDealerRemark())) {
								remark = remark + dto.getDealerRemark();
							}
							if (!StringUtils.isEmpty(dto.getSalesConsultant())
									&& !StringUtils.isEmpty(dto.getDealerUserName())) {
								remark = remark + "(负责销售顾问：" + dto.getSalesConsultant() + ",电话营销员:"
										+ dto.getDealerUserName() + ")";
							} else if (!StringUtils.isEmpty(dto.getSalesConsultant())) {
								remark = remark + "(负责销售顾问:" + dto.getSalesConsultant() + ")";
							} else if (!StringUtils.isEmpty(dto.getDealerUserName())) {
								remark = remark + "(电话营销员:" + dto.getDealerUserName() + ")";
							}
							String REMARK = null;
							if (!StringUtils.isEmpty(dto.getDealerUserName())) {
								REMARK = remark;
							}
							// 拼备注 end
							String BUY_REASON = null;
							if (dto.getConsiderationId() != null && dto.getConsiderationId() != 0) {
								String desc = getStatusDescByCode(dto.getConsiderationId());
								if (!StringUtils.isEmpty(desc)) {// 数据库存的样子
																	// ;8:品牌声誉
									BUY_REASON = ";" + String.valueOf((desc.length() * 2)) + desc;
								}
							}
							int INIT_LEVEL = 0;
							int INTENT_LEVEL = 0;
							Date DDCN_UPDATE_DATE = null;
							if (dto.getOpportunityLevelId() != null && dto.getOpportunityLevelId() != 0) {
								INIT_LEVEL = dto.getOpportunityLevelId();
								INTENT_LEVEL = dto.getOpportunityLevelId();
								if (oldpo.get("INTENT_LEVEL") == null
										|| oldpo.get("INTENT_LEVEL") != dto.getOpportunityLevelId()) {
									DDCN_UPDATE_DATE = new Date();
								}
							} else {
								INIT_LEVEL = 13101005;
								INTENT_LEVEL = 13101005;
								if (oldpo.get("INTENT_LEVEL") == null
										|| (Integer)oldpo.get("INTENT_LEVEL") != 13101005) {
									DDCN_UPDATE_DATE = new Date();
								}
							}
							// ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
							String CONTACTOR_PHONE = null;
							String CONTACTOR_MOBILE = null;
							String contactorPhone = null;
							String contactorMobile = null;
							if (!StringUtils.isEmpty(dto.getTelephone())) {
								if (dto.getTelephone().indexOf("-") != -1)
									CONTACTOR_PHONE = dto.getTelephone();
								else
									CONTACTOR_MOBILE = dto.getTelephone();
							}
							if (!StringUtils.isEmpty(dto.getPhone())) {
								if (dto.getPhone().indexOf("-") != -1
										&& "".equals(updatepo.get("CONTACTOR_PHONE")))
									contactorPhone = dto.getPhone();
								else
									contactorMobile = dto.getPhone();
							}
							String GENDER = null;
							if (dto.getGender() != null && dto.getGender().equals("先生")) {
								GENDER = "10061001";
							} else if (dto.getGender() != null && dto.getGender().equals("女士")) {
								GENDER = "10061002";
							}
							String INTENT_ID=null;
							if (!StringUtils.isEmpty(dto.getBrandCode()) || !StringUtils.isEmpty(dto.getSeriesCode())
									|| !StringUtils.isEmpty(dto.getModelCode())) {
								if (oldpo.get("INTENT_ID") != null && oldpo.get("INTENT_ID") != "0") {// 有意向ID
									List<Map> listdetail = Base.findAll(
											"SELECT * FROM tt_customer_intent_detail WHERE DEALER_CODE=? and D_KEY=? and INTENT_ID=? and IS_MAIN_MODEL=?", dealerCode,
											0, oldpo.get("INTENT_ID"), 12781001);
									if (listdetail != null && listdetail.size() > 0) {// 能找到这个意向明细
										TtCustomerIntentDetailPO.update("INTENT_BRAND=? , INTENT_SERIES=? , INTENT_MODEL=? , INTENT_CONFIG=? , INTENT_COLOR=? , UPDATED_BY=? , UPDATED_AT=?", 
												"DEALER_CODE=? and D_KEY=? and INTENT_ID=? and IS_MAIN_MODEL=?", dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode(),
												dto.getConfigCode(),dto.getColorCode(),1L,new Date(),dealerCode, 0, oldpo.get("INTENT_ID"), 12781001);
									} else {// 找不到明细
										TtCusIntentPO intentNewpo = new TtCusIntentPO();
										List<Map> intentNepo = Base.findAll(
												"SELECT * FROM tt_customer_intent WHERE INTENT_ID = ? AND DEALER_CODE = ? AND D_KEY = ? ",
												oldpo.get("INTENT_ID"), dealerCode, 0);
										if (intentNepo != null) {
											TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
											updatedetail.setString("DEALER_CODE", dealerCode);
											updatedetail.setString("ITEM_ID", oldpo.get("ITEM_ID"));
											updatedetail.setString("INTENT_ID", oldpo.get("INTENT_ID"));
											updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
											updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
											updatedetail.setString("INTENT_MODEL", dto.getModelCode());
											updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
											updatedetail.setString("INTENT_COLOR", dto.getColorCode());
											updatedetail.setLong("CREATED_BY", 1L);
											updatedetail.setInteger("IS_MAIN_MODEL", 12781001);
											updatedetail.setInteger("D_KEY", 0);
											updatedetail.insert();

										} else {// 没有意向主表的话 先插主表一记录 再插字表明细
											intentNewpo.setString("DEALER_CODE", dealerCode);
											intentNewpo.setString("INTENT_ID",
													TtCusIntentPO.findByCompositeKeys(oldpo.get("INTENT_ID")));
											intentNewpo.setInteger("D_KEY", 0);
											intentNewpo.setString("CUSTOMER_NO", oldpo.get("CUSTOMER_NO"));
											intentNewpo.setLong("CREATED_BY", 1L);
											intentNewpo.insert();

											TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
											updatedetail.setString("DEALER_CODE", dealerCode);
											updatedetail.setString("INTENT_ID", intentNepo.get(i).get("INTENT_ID"));
											updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
											updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
											updatedetail.setString("INTENT_MODEL", dto.getModelCode());
											updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
											updatedetail.setString("INTENT_COLOR", dto.getColorCode());
											updatedetail.setLong("CREATED_BY", 1L);
											updatedetail.setString("IS_MAIN_MODEL", 12781001);
											updatedetail.setInteger("D_KEY", 0);
											updatedetail.insert();
										}
									}
								} else {// 没有意向 则插入意向 再插入意向明细 再更新客户表的intentId
									TtCusIntentPO intentNewpo = new TtCusIntentPO();
									intentNewpo.setString("DEALER_CODE", dealerCode);
									intentNewpo.setInteger("D_KEY", 0);
									intentNewpo.setString("CUSTOMER_NO", oldpo.get("CUSTOMER_NO"));
									intentNewpo.setLong("CREATED_BY", 1L);
									intentNewpo.insert();

									TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
									updatedetail.setString("DEALER_CODE", dealerCode);
									updatedetail.setString("INTENT_ID", oldpo.get("INTENT_ID"));
									updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
									updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
									updatedetail.setString("INTENT_MODEL", dto.getModelCode());
									updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
									updatedetail.setString("INTENT_COLOR", dto.getColorCode());
									updatedetail.setLong("CREATED_BY", 1L);
									updatedetail.setString("IS_MAIN_MODEL", 12781001);
									updatedetail.setInteger("D_KEY", 0);
									updatedetail.insert();
									INTENT_ID = intentNewpo.getLong("INTENT_ID").toString();
								}
							}
							
							PotentialCusPO.update("REMARK=? , ADDRESS=? , BIRTHDAY=? , CITY=? , BUY_REASON=? , E_MAIL=? , MEDIA_DETAIL=? , MEDIA_TYPE=? , "
									+ "CUSTMER_NAME=? , INIT_LEVEL=? , INTENT_LEVEL=? , DDCN_UPDATE_DATE=? ,CONTACTOR_PHONE=? , CONTACTOR_MOBILE=? , "
									+ "CONTACTOR_PHONE=? , CONTACTOR_MOBILE=? , ZIP_CODE=? , PROVINCE=? , IM=? , GENDER=? , INTENT_ID=? , UPDATED_BY=? , UPDATED_AT=?", 
									"OEM_CUSTOMER_NO = ? AND VALIDITY_BEGIN_DATE = ? AND CUS_SOURCE = ? AND CUSTOMER_TYPE = ?", 
									REMARK,dto.getAddress(),dto.getBirthday(),dto.getCityId(),BUY_REASON,dto.getEmail(),dto.getMediaNameId(),dto.getMediaTypeId(),
									dto.getName(),INIT_LEVEL,INTENT_LEVEL,DDCN_UPDATE_DATE,CONTACTOR_PHONE,CONTACTOR_MOBILE,contactorPhone,contactorMobile,
									dto.getPostCode(),dto.getProvinceId(),dto.getSocialityAccount(),GENDER,INTENT_ID,1L,new Date(),
									dto.getNid().toString(), new Date(), 13111016, 10181001);
							
							// 经确认 只要下发来的客户 不管更新插入 都要建一条跟进记录 begin
							if (updatepo.get("INTENT_LEVEL") != null
									&& updatepo.get("INTENT_LEVEL") != "0") {
								List<TrackingTaskPO> list22 = new ArrayList<>();
								list22 = TrackingTaskPO.find("DEALER_CODE = ? AND INTENT_LEVEL = ? AND IS_VALID = ? ",
										dealerCode, updatepo.get("INTENT_LEVEL"), 12781001);
								TrackingTaskPO tt2 = new TrackingTaskPO();
								if (list22 != null && list22.size() > 0) {
									Calendar c = Calendar.getInstance();
									for (int jj = 0; jj < list22.size(); jj++) {
										tt2 = (TrackingTaskPO) list22.get(jj);
										String dates = new String();
										if (tt2.getInteger("INTERVAL_DAYS") != null
												&& !"".equals(tt2.getInteger("INTERVAL_DAYS"))
												&& !tt2.getInteger("INTERVAL_DAYS").equals("0")) {
											c.setTime(new Date());
											c.add(c.DAY_OF_WEEK, tt2.getInteger("INTERVAL_DAYS"));
											dates = DateUtil.parseDefaultDate(c.getTime().toString()).toString();
										}
										TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
										spro.setString("DEALER_CODE", dealerCode);
										if (oldpo.get("INTENT_ID") != null && oldpo.get("INTENT_ID") != "0")
											spro.setLong("INTENT_ID", oldpo.get("INTENT_ID"));
										else
											spro.setLong("INTENT_ID", updatepo.get("INTENT_ID"));
										spro.setString("CUSTOMER_NO", oldpo.get("CUSTOMER_NO"));
										spro.setString("CUSTOMER_NAME", updatepo.get("CUSTOMER_NAME"));
										// 促进前级别
										spro.setLong("TASK_ID", tt2.getLong("TASK_ID"));
										spro.setInteger("PRIOR_GRADE", tt2.getInteger("INTENT_LEVEL"));

										if (dates != null && !"".equals(dates)) {
											spro.setDate("SCHEDULE_DATE", new Date());
										}
										// 跟进任务中的任务内容添加到跟进活动内容
										if (tt2.getString("TASK_CONTENT") != null
												&& !"".equals(tt2.getString("TASK_CONTENT"))) {
											spro.setString("PROM_CONTENT", tt2.getString("TASK_CONTENT"));
										}
										// 跟进任务中的执行方式添加到跟进活动跟进方式
										if (tt2.getInteger("EXECUTE_TYPE") != null
												&& !"".equals(tt2.getInteger("EXECUTE_TYPE"))
												&& !tt2.getInteger("EXECUTE_TYPE").equals("0")) {
											spro.setInteger("PROM_WAY", tt2.getInteger("EXECUTE_TYPE"));
										}
										// 创建方式为系统创建
										spro.setInteger("CREATE_TYPE", 13291001);
										// 联系人
										spro.setString("CONTACTOR_NAME", updatepo.get("CUSTOMER_NAME"));
										// ADD BY LNY
										// 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
										if (!StringUtils.isEmpty(updatepo.get("CONTACTOR_PHONE").toString())) {
											if (updatepo.get("CONTACTOR_PHONE").toString().indexOf("-") != -1)
												spro.setString("PHONE", updatepo.get("CONTACTOR_PHONE"));
											else
												spro.setString("MOBILE", updatepo.get("CONTACTOR_PHONE"));
										}
										if (!StringUtils.isEmpty(updatepo.get("CONTACTOR_MOBILE").toString())) {
											if (updatepo.get("CONTACTOR_MOBILE").toString().indexOf("-") != -1
													&& "".equals(spro.getString("PHONE")))
												spro.setString("PHONE", updatepo.get("CONTACTOR_MOBILE"));
											else
												spro.setString("MOBILE", updatepo.get("CONTACTOR_MOBILE"));
										}
										spro.setInteger("IS_AUDITING", 12781002);
										spro.setLong("CREATED_BY", 1L);
										spro.insert();// 新增销售活动
									}
								}
							}
						}
					} else {
						if (!StringUtils.isEmpty(dto.getPhone()) || !StringUtils.isEmpty(dto.getTelephone())) {// 没有联系方式的无效，有联系方式的用联系方式判断是否撞单
							if (!StringUtils.isEmpty(dto.getPhone())) {
								List<Map> listcheck = Base.findAll("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE (Contactor_Mobile= ? or Contactor_Phone= ?) and DEALER_CODE= ? AND D_KEY= ?"
										,dto.getPhone(), dto.getPhone(), dealerCode, 0);
								if (listcheck != null && listcheck.size() > 0) {
									Map customerpo = listcheck.get(0);
									if (this.updateNF(dealerCode, customerpo, dto)) {// N或者F(或者FO客户的休眠前级别是N也算N)覆盖客户信息，客户来源标志DCC转入跳出本次循环
										break;
									}
									SADMS003ForeReturnDTO retrundto = new SADMS003ForeReturnDTO();
									retrundto.setNid(dto.getNid());
									retrundto.setDealerCode(dealerCode);
									retrundto.setConflictedType("1");
									retrundto.setDmsCustomerNo(customerpo.get("CUSTOMER_NO").toString());
									retrundto.setOpportunityLevelID((Integer)customerpo.get("INTENT_LEVEL"));
									if (customerpo.get("SOLD_BY") != null && customerpo.get("SOLD_BY") != "0") {
										UserPO userpo = UserPO.findFirst("DEALER_CODE = ? AND USER_ID = ?", dealerCode,
												customerpo.get("SOLD_BY"));
										if (userpo != null) {
											retrundto.setSalesConsultant(userpo.getString("USER_NAME"));
										}
									}
									resultList.add(retrundto);// 每一个都要给反馈
									updateCustomerToSame(dealerCode, dto.getPhone(), dto.getNid().toString());
									break;
								}
							}
							if (!StringUtils.isEmpty(dto.getTelephone())) {
								List<Map> listcheck = Base.findAll(
										"SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE ( Contactor_Mobile=? or Contactor_Phone=?) AND DEALER_CODE = ? AND D_KEY = ?",
										dto.getTelephone(), dto.getTelephone(), dealerCode, 0);
								if (listcheck != null && listcheck.size() > 0) {
									Map customerpo = listcheck.get(0);
									if (this.updateNF(dealerCode, customerpo, dto)) {// N或者F(或者FO客户的休眠前级别是N也算N)
																						// 覆盖客户信息，客户来源标志DCC转入
																						// 跳出本次循环
										break;
									}
									SADMS003ForeReturnDTO retrundto = new SADMS003ForeReturnDTO();
									retrundto.setNid(dto.getNid());
									retrundto.setDealerCode(dealerCode);
									retrundto.setConflictedType("1");
									retrundto.setDmsCustomerNo(customerpo.get("CUSTOMER_NO").toString());
									retrundto.setOpportunityLevelID((Integer)customerpo.get("INTENT_LEVEL"));
									if (customerpo.get("SOLD_BY") != null && customerpo.get("SOLD_BY") != "0") {
										List<Map> userpo = Base.findAll("SELECT * FROM tm_user WHERE DEALER_CODE = ? AND USER_ID = ?", dealerCode,
												customerpo.get("SOLD_BY"));
										if (userpo != null) {
											retrundto.setSalesConsultant(userpo.get(0).get("USER_NAME").toString());
										}
									}
									resultList.add(retrundto);// 每一个都要给反馈
									updateCustomerToSame(dealerCode, dto.getPhone(), dto.getNid().toString());
									;
								}
							}
							// 系统中没有这个客户 先插入客户 再插入意向 再更新客户中intentid
							PotentialCusPO updatepo = new PotentialCusPO();
							updatepo.setTimestamp("VALIDITY_BEGIN_DATE", new Date());
							updatepo.setDate("FOUND_DATE", new Date());
							updatepo.setString("DEALER_CODE", dealerCode);
							String customerNo = getCustomerNo();
							updatepo.setString("CUSTOMER_NO", customerNo);
							updatepo.setInteger("D_KEY", 0);
							String remark = "";
							// dealerUserName;//电话营销员 salesConsultant;//负责销售顾问
							if (!StringUtils.isEmpty(dto.getDealerRemark())) {
								remark = remark + dto.getDealerRemark();
							}
							if (!StringUtils.isEmpty(dto.getSalesConsultant())
									&& !StringUtils.isEmpty(dto.getDealerUserName())) {
								remark = remark + "(负责销售顾问:" + dto.getSalesConsultant() + ",电话营销员:"
										+ dto.getDealerUserName() + ")";
							} else if (!StringUtils.isEmpty(dto.getSalesConsultant())) {
								remark = remark + "(负责销售顾问:" + dto.getSalesConsultant() + ")";
							} else if (!StringUtils.isEmpty(dto.getDealerUserName())) {
								remark = remark + "(电话营销员:" + dto.getDealerUserName() + ")";
							}
							if (!StringUtils.isEmpty(remark)) {
								updatepo.setString("REMARK", remark);
							}
							// 拼备注
							updatepo.setString("ADDRESS", dto.getAddress());
							updatepo.setDate("BIRTHDAY", dto.getBirthday());
							updatepo.setInteger("CITY", dto.getCityId());
							if (dto.getConsiderationId() != null && dto.getConsiderationId() != 0) {
								String desc = getStatusDescByCode(dto.getConsiderationId());
								if (!StringUtils.isEmpty(desc)) {// 数据库存的样子
																	// ;8:品牌声誉
									updatepo.setString("BUY_REASON", ";" + String.valueOf((desc.length() * 2)) + desc);
								}
							}
							updatepo.setString("E_MAIL", dto.getEmail());
							Integer mediaNameId = dto.getMediaNameId();
							if(mediaNameId!=null && !mediaNameId.equals("")){
								updatepo.setString("MEDIA_DETAIL", mediaNameId.toString());
							}else{
								updatepo.setString("MEDIA_DETAIL", null);
							}
							updatepo.setInteger("MEDIA_TYPE", dto.getMediaTypeId());
							updatepo.setString("CUSTOMER_NAME", dto.getName());
							if (dto.getOpportunityLevelId() != null && dto.getOpportunityLevelId() != 0) {
								updatepo.setInteger("INIT_LEVEL", dto.getOpportunityLevelId());
								updatepo.setInteger("INTENT_LEVEL", dto.getOpportunityLevelId());
							} else {
								updatepo.setInteger("INIT_LEVEL", 13101005);
								updatepo.setInteger("INTENT_LEVEL", 13101005);
							}
							// ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
							if (!StringUtils.isEmpty(dto.getTelephone())) {
								if (dto.getTelephone().indexOf("-") != -1) {
									updatepo.setString("CONTACTOR_PHONE", dto.getTelephone());
								} else {
									updatepo.setString("CONTACTOR_MOBILE", dto.getTelephone());
								}
							}
							if (!StringUtils.isEmpty(dto.getPhone())) {
								if (dto.getPhone().indexOf("-") != -1
										&& "".equals(updatepo.getString("CONTACTOR_PHONE"))) {
									updatepo.setString("CONTACTOR_PHONE", dto.getPhone());
								} else {
									updatepo.setString("CONTACTOR_MOBILE", dto.getPhone());
								}
							}
							updatepo.setString("ZIP_CODE", dto.getPostCode());
							updatepo.setInteger("PROVINCE", dto.getProvinceId());
							updatepo.setString("IM", dto.getSocialityAccount());
							// 额外要加DMS自己的字段 \
							updatepo.setString("OEM_CUSTOMER_NO", dto.getNid().toString());
							updatepo.setInteger("CUS_SOURCE", 13111016);
							updatepo.setInteger("CUSTOMER_STATUS", 13211001);
							updatepo.setInteger("CUSTOMER_TYPE", 10181001);
							if (dto.getGender() != null && dto.getGender().equals("先生")) {
								updatepo.setInteger("GENDER", 10061001);
							} else if (dto.getGender() != null && dto.getGender().equals("女士")) {
								updatepo.setInteger("GENDER", 10061002);
							}
							updatepo.setLong("CREATED_BY", 1L);
							updatepo.insert();
							String[] keys = updatepo.getCompositeKeys();
							String updateDealerCode = updatepo.getString(keys[0]);
							String updatecustomerNo = updatepo.getString(keys[1]);
							if (!StringUtils.isEmpty(dto.getBrandCode()) || !StringUtils.isEmpty(dto.getSeriesCode())
									|| !StringUtils.isEmpty(dto.getModelCode())) {
								// 没有意向 则插入意向 再插入意向明细 再更新客户表的intentId
								TtCusIntentPO intentNewpo = new TtCusIntentPO();
								intentNewpo.setString("DEALER_CODE", dealerCode);
								Long itemId = commonNoService.getId("ID");
								intentNewpo.setLong("INTENT_ID", itemId);
								intentNewpo.setInteger("D_KEY", 0);
								intentNewpo.setString("CUSTOMER_NO", updatepo.getString("CUSTOMER_NO"));
								intentNewpo.setLong("CREATED_BY", 1L);
								intentNewpo.saveIt();

								TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
								updatedetail.setString("DEALER_CODE", dealerCode);
								updatedetail.setLong("INTENT_ID", itemId);
								updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
								updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
								updatedetail.setString("INTENT_MODEL", dto.getModelCode());
								updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
								updatedetail.setString("INTENT_COLOR", dto.getColorCode());
								updatedetail.setLong("CREATED_BY", 1L);
								updatedetail.setInteger("IS_MAIN_MODEL", 12781001);
								updatedetail.setInteger("D_KEY", 0);
								updatedetail.saveIt();
								// 再更新客户表的intentId
								PotentialCusPO.update("INTENT_ID=?", "DEALER_CODE = ? AND CUSTOMER_NO = ?", itemId,updateDealerCode,updatecustomerNo);				
								
							}
							// 经确认 只要下发来的客户 不管更新插入 都要建一条跟进记录 begin
							if (updatepo.getInteger("INTENT_LEVEL") != null
									&& updatepo.getInteger("INTENT_LEVEL") != 0) {
								List<Map> list22 = Base.findAll(
										"SELECT * FROM tm_tracking_task WHERE DEALER_CODE=? and INTENT_LEVEL=? and IS_VALID", dealerCode,
										updatepo.getInteger("INTENT_LEVEL"), 12781001);
								if (list22 != null && list22.size() > 0) {
									Calendar c = Calendar.getInstance();
									SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
									for (int jj = 0; jj < list22.size(); jj++) {
										Map tt2 = list22.get(jj);
										String dates = new String();
										if (tt2.get("INTERVAL_DAYS") != null
												&& !"".equals(tt2.get("INTERVAL_DAYS"))
												&& !tt2.get("INTERVAL_DAYS").equals("0")) {
											c.setTime(new Date());
											c.add(c.DAY_OF_WEEK, (Integer)tt2.get("INTERVAL_DAY"));
											dates = format.format(c.getTime()).toString();
										}
										TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
										spro.setString("DEALER", dealerCode);
										Long itemId = commonNoService.getId("ID");
										spro.setLong("INTENT_ID", itemId);
										spro.setString("CUSTOMER_NO", updatepo.getString("CUSTOMER_NO"));
										spro.setString("CUSTOMER_NAME", updatepo.getString("CUSTOMER_NAME"));
										// 促进前级别
										spro.setLong("TASK_ID", tt2.get("TASK_ID"));
										spro.setInteger("PRIOR_GRADE", tt2.get("INTENT_LEVEL"));

										if (dates != null && !"".equals(dates)) {
											spro.setDate("SCHEDULE_DATE", new Date());
										}
										// 跟进任务中的任务内容添加到跟进活动内容
										if (tt2.get("TASK_CONTENT") != null
												&& !"".equals(tt2.get("TASK_CONTENT"))) {
											spro.setString("PROM_CONTENT", tt2.get("TASK_CONTENT"));
										}
										// 跟进任务中的执行方式添加到跟进活动跟进方式
										if (tt2.get("EXECUTE_TYPE") != null
												&& !"".equals(tt2.get("EXECUTE_TYPE"))
												&& !tt2.get("EXECUTE_TYPE").equals("0")) {
											spro.setInteger("PROM_WAY", tt2.get("EXECUTE_TYPE"));
										}
										// 创建方式为系统创建
										spro.setInteger("CREATE_TYPE", 13291001);
										// 联系人
										spro.setString("CONTACTOR_NAME", updatepo.getString("CUSTOMER_NAME"));
										// ADD BY LNY
										// 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
										if (!StringUtils.isEmpty(updatepo.getString("CONTACTOR_PHONE"))) {
											if (updatepo.getString("CONTACTOR_PHONE").indexOf("-") != -1) {
												spro.setString("PHONE", updatepo.getString("CONTACTOR_PHONE"));
											} else {
												spro.setString("MOBILE", updatepo.getString("CONTACTOR_PHONE"));
											}
										}
										if (!StringUtils.isEmpty(updatepo.getString("CONTACTOR_MOBILE"))) {
											if (updatepo.getString("CONTACTOR_MOBILE").indexOf("-") != -1
													&& "".equals(spro.getString("PHONE"))) {
												spro.setString("PHONE", updatepo.getString("CONTACTOR_MOBILE"));
											} else {
												spro.setString("MOBILE", updatepo.getString("CONTACTOR_MOBILE"));
											}
										}
										spro.setInteger("IS_AUDITING", 12781002);
										spro.setLong("CREATED_BY", 1L);
										spro.insert();
									}
								}
							}
						}
					}
				}
				if (resultList != null && resultList.size() > 0) {
					//LMS邀约到店撞单接口的反馈上报接收
					SADCS003Return.receiveDate(resultList);
				} else {
					logger.debug("没有发生撞单的记录，不进行反馈SADCS003Return");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	private boolean updateNF(String dealerCode, Map customerpo, dccDto dto) {
		try {
			// 如果更新则反馈true
			if (customerpo != null && customerpo.get("INTENT_LEVEL") != null
					&& (customerpo.get("INTENT_LEVEL").equals(13101005)
							|| customerpo.get("INTENT_LEVEL").equals(13101007)
							|| (customerpo.get("INTENT_LEVEL").equals(13101006)
									&& customerpo.get("FAIL_INTENT_LEVEL") != null
									&& customerpo.get("FAIL_INTENT_LEVEL").equals(13101005)))) {// 更新本地客户
				List<Map> updatemap = Base.findAll("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = ? AND CUSTOMER_NO = ? AND D_KEY = ? ",
						dealerCode, customerpo.get("CUSTOMER_NO"), 0);
				Map updatepo = updatemap.get(0);				
				
				// dealerUserName;//电话营销员 salesConsultant;//负责销售顾问
				String remark = "";
				if (!StringUtils.isEmpty(dto.getDealerRemark())) {
					remark = remark + dto.getDealerRemark();
				}
				if (!StringUtils.isEmpty(dto.getSalesConsultant()) && !StringUtils.isEmpty(dto.getDealerUserName())) {
					remark = remark + "(负责销售顾问:" + dto.getSalesConsultant() + ",电话营销员:" + dto.getDealerUserName() + ")";
				} else if (!StringUtils.isEmpty(dto.getSalesConsultant())) {
					remark = remark + "(负责销售顾问:" + dto.getSalesConsultant() + ")";
				} else if (!StringUtils.isEmpty(dto.getDealerUserName())) {
					remark = remark + "(电话营销员:" + dto.getDealerUserName() + ")";
				}
				String REMARK = null;
				if (!StringUtils.isEmpty(remark)) {
					REMARK = remark;
				}
				// 拼备注 end
				String BUY_REASON = null;
				if (dto.getConsiderationId() != null && dto.getConsiderationId() != 0) {
					String desc = getStatusDescByCode(dto.getConsiderationId());
					if (!StringUtils.isEmpty(desc)) {// 数据库存的样子;8:品牌声誉
						BUY_REASON = ";" + String.valueOf((desc.length() * 2)) + desc;
					}
				}
				int INIT_LEVEL = 0;
				int INTENT_LEVEL = 0;
				Date DDCN_UPDATE_DATE = null;
				if (dto.getOpportunityLevelId() != null && dto.getOpportunityLevelId() != 0) {
					INIT_LEVEL = dto.getOpportunityLevelId();
					INTENT_LEVEL = dto.getOpportunityLevelId();
					if (customerpo.get("INTENT_LEVEL") == null
							|| customerpo.get("INTENT_LEVEL") != dto.getOpportunityLevelId()) {
						DDCN_UPDATE_DATE = new Date();
					}
				} else {
					INIT_LEVEL = 13101005;
					INTENT_LEVEL = 13101005;
					if (customerpo.get("INTENT_LEVEL") == null
							|| !customerpo.get("INTENT_LEVEL").equals(13101005)) {
						DDCN_UPDATE_DATE = new Date();
					}
				}
				// ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
				String CONTACTOR_PHONE = null;
				String CONTACTOR_MOBILE = null;
				if (!StringUtils.isEmpty(dto.getTelephone())) {
					if (dto.getTelephone().indexOf("-") != -1)
						CONTACTOR_PHONE = dto.getTelephone();
					else
						CONTACTOR_MOBILE = dto.getTelephone();
				}
				if (!StringUtils.isEmpty(dto.getPhone())) {
					if (dto.getPhone().indexOf("-") != -1 && "".equals(updatepo.get("CONTACTOR_PHONE")))
						CONTACTOR_PHONE = dto.getPhone();
					else
						CONTACTOR_MOBILE = dto.getPhone();
				}
				// updatepo.setContactorPhone(vo.getTelephone());
				
				// updatepo.setContactorMobile(vo.getPhone());
				String GENDER = null;
				if (dto.getGender() != null && dto.getGender().equals("先生")) {
					GENDER = "10061001";
				} else if (dto.getGender() != null && dto.getGender().equals("女士")) {
					GENDER = "10061002";
				}
				String INTENT_ID = null;
				if (!StringUtils.isEmpty(dto.getBrandCode()) || !StringUtils.isEmpty(dto.getSeriesCode())
						|| !StringUtils.isEmpty(dto.getModelCode())) {
					if (customerpo.get("INTENT_ID") != null && customerpo.get("INTENT_ID") != "0") {// 有意向ID
						List<Map> listdetail = Base.findAll(
								"SELECT * FROM tt_customer_intent_detail WHERE DEALER_CODE=? and D_KEY=? and INTENT_ID=? and IS_MAIN_MODEL=?", dealerCode, 0,
								customerpo.get("INTENT_ID"), 12781001);
						if (listdetail != null && listdetail.size() > 0) {// 能找到这个意向明细
							TtCustomerIntentDetailPO.update("INTENT_BRAND=? , INTENT_SERIES=? , INTENT_MODEL=? , INTENT_CONFIG=? , INTENT_COLOR=? , UPDATED_BY=? , UPDATED_AT=?", 
									"DEALER_CODE=? and D_KEY=? and INTENT_ID=? and IS_MAIN_MODEL=?", dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode(),
									dto.getConfigCode(),dto.getColorCode(),1L,new Date(),dealerCode, 0, customerpo.get("INTENT_ID"), 12781001);
						} else {// 找不到明细
							TtCusIntentPO intentNewpo = new TtCusIntentPO();
							List<Map> intentNepo = Base.findAll(
									"SELECT * FROM tt_customer_intent_detail WHERE INTENT_ID=? and DEALER_CODE=? and D_KEY=?", customerpo.get("INTENT_ID"),
									dealerCode, 0);
							if (intentNepo != null) {
								TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
								updatedetail.setString("DEALER_CODE", dealerCode);
								updatedetail.setInteger("INTENT_ID", customerpo.get("INTENT_ID"));
								updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
								updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
								updatedetail.setString("INTENT_MODEL", dto.getModelCode());
								updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
								updatedetail.setString("INTENT_COLOR", dto.getColorCode());
								updatedetail.setLong("CREATED_BY", 1L);
								updatedetail.setInteger("IS_MAIN_MODEL", 12781001);
								updatedetail.setInteger("D_KEY", 0);
								updatedetail.insert();
							} else {// 没有意向主表的话 先插主表一记录 再插字表明细
								intentNewpo.setString("DEALER_CODE", dealerCode);
								Long itemId = commonNoService.getId("ID");
								intentNewpo.setInteger("INTENT_ID", itemId);
								intentNewpo.setInteger("D_KEY", 0);
								intentNewpo.setString("CUSTOMER_NO", customerpo.get("CUSTOMER_NO"));
								intentNewpo.setLong("CREATED_BY", 1L);
								intentNewpo.insert();

								TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
								updatedetail.setString("DEALER_CODE", dealerCode);
								updatedetail.setInteger("INTENT_ID", itemId);
								updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
								updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
								updatedetail.setString("INTENT_MODEL", dto.getModelCode());
								updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
								updatedetail.setString("INTENT_COLOR", dto.getColorCode());
								updatedetail.setLong("CREATED_BY", 1L);
								updatedetail.setInteger("IS_MAIN_MODEL", 12781001);
								updatedetail.setInteger("D_KEY", 0);
								updatedetail.insert();
							}
						}
					} else {// 没有意向 则插入意向 再插入意向明细 再更新客户表的intentId
						TtCusIntentPO intentNewpo = new TtCusIntentPO();
						intentNewpo.setString("DEALER_CODE", dealerCode);
						Long itemId = commonNoService.getId("ID");
						intentNewpo.setInteger("INTENT_ID", itemId);
						intentNewpo.setInteger("D_KEY", 0);
						intentNewpo.setString("CUSTOMER_NO", customerpo.get("CUSTOMER_NO"));
						intentNewpo.setLong("CREATED_BY", 1L);
						intentNewpo.insert();

						TtCustomerIntentDetailPO updatedetail = new TtCustomerIntentDetailPO();
						updatedetail.setString("DEALER_CODE", dealerCode);
						updatedetail.setInteger("INTENT_ID", itemId);
						updatedetail.setString("INTENT_BRAND", dto.getBrandCode());
						updatedetail.setString("INTENT_SERIES", dto.getSeriesCode());
						updatedetail.setString("INTENT_MODEL", dto.getModelCode());
						updatedetail.setString("INTENT_CONFIG", dto.getConfigCode());
						updatedetail.setString("INTENT_COLOR", dto.getColorCode());
						updatedetail.setLong("CREATED_BY", 1L);
						updatedetail.setInteger("IS_MAIN_MODEL", 12781001);
						updatedetail.setInteger("D_KEY", 0);
						updatedetail.insert();
						INTENT_ID = intentNewpo.getInteger("INTENT_ID").toString();
					}
				}
				
				PotentialCusPO.update("OEM_CUSTOMER_NO=? , VALIDITY_BEGIN_DATE=? , CUS_SOURCE=? , CUSTOMER_TYPE=? , REMARK=? , ADDRESS=? , BIRTHDAY=? ,"
						+ "CITY=? , BUY_REASON=? , E_MAIL=? , MEDIA_DETAIL=? , MEDIA_TYPE=? , CUSTOMER_NAME=? , INIT_LEVEL=? , INTENT_LEVEL=? , DDCN_UPDATE_DATE=? ,"
						+ "CONTACTOR_PHONE=? , CONTACTOR_MOBILE=? ,  ZIP_CODE=? , PROVINCE=? , IM=? , GENDER=? , INTENT_ID=? , UPDATED_BY=? ", 
						"DEALER_CODE = ? AND CUSTOMER_NO = ? AND D_KEY=?", dto.getNid().toString(),new Date(),13111016,10181001,REMARK,
						dto.getAddress(),dto.getBirthday(),dto.getCityId(),BUY_REASON,dto.getEmail(),dto.getMediaNameId(),dto.getMediaTypeId(),
						dto.getName(),INIT_LEVEL,INTENT_LEVEL,DDCN_UPDATE_DATE,CONTACTOR_PHONE,CONTACTOR_MOBILE,
						dto.getPostCode(),dto.getProvinceId(),dto.getSocialityAccount(),GENDER,INTENT_ID,1L,dealerCode, customerpo.get("CUSTOMER_NO"),0);
				
				
				// 经确认 只要下发来的客户 不管更新插入 都要建一条跟进记录 begin
				if (updatepo.get("INTENT_LEVEL") != null && updatepo.get("INTENT_LEVEL") != "0") {
					List<Map> list22 = Base.findAll("SELECT * FROM tm_tracking_task WHERE DEALER_CODE=? and INTENT_LEVEL=? and IS_VALID=?",
							dealerCode, updatepo.get("INTENT_LEVEL"), 12781001);
					if (list22 != null && list22.size() > 0) {
						Calendar c = Calendar.getInstance();
						SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						for (int jj = 0; jj < list22.size(); jj++) {
							Map tt2 = list22.get(jj);
							String dates = new String();
							if (tt2.get("INTERVAL_DAYS") != null && !"".equals(tt2.get("INTERVAL_DAYS"))
									&& !tt2.get("INTERVAL_DAYS").equals("0")) {
								c.setTime(new Date());
								c.add(c.DAY_OF_WEEK, (Integer)tt2.get("INTERVAL_DAYS"));
								dates = format.format(c.getTime()).toString();
							}
							TtSalesPromotionPlanPO spro = new TtSalesPromotionPlanPO();
							spro.setString("DEALER_CODE", dealerCode);
							if (customerpo.get("INTENT_ID") != null && customerpo.get("INTENT_ID") != "0") {
								spro.setInteger("INTENT_ID", customerpo.get("INTENT_ID"));
							} else {
								spro.setInteger("INTENT_ID", updatepo.get("INTENT_ID"));
							}
							spro.setString("CUSTOMER_NO", customerpo.get("CUSTOMER_NO"));
							spro.setString("CUSTOMER_NAME", updatepo.get("CUSTOMER_NAME"));
							// 促进前级别
							spro.setInteger("TASK_ID", tt2.get("TASK_ID"));
							spro.setInteger("PRIOR_GRADE", tt2.get("INTENT_LEVEL"));

							if (dates != null && !"".equals(dates)) {
								spro.setInteger("SCHEDULE_DATE", new Date());
							}
							// 跟进任务中的任务内容添加到跟进活动内容
							if (tt2.get("TASK_CONTENT") != null && !"".equals(tt2.get("TASK_CONTENT"))) {
								spro.setString("PROM_CONTENT", tt2.get("TASK_CONTENT"));
							}
							// 跟进任务中的执行方式添加到跟进活动跟进方式
							if (tt2.get("EXECUTE_TYPE") != null && !"".equals(tt2.get("EXECUTE_TYPE"))
									&& !tt2.get("EXECUTE_TYPE").equals("0")) {
								spro.setInteger("PROM_WAY", tt2.get("PROM_WAY"));
							}
							// 创建方式为系统创建
							spro.setInteger("CREATED_TYPE", 13291001);
							// 联系人
							spro.setString("CONTACTOR_NAME", updatepo.get("CUSTOMER_NAME"));
							// ADD BY LNY 判断电话或手机中带“-”的，则存入电话字段，否则存入手机字段
							if (!StringUtils.isEmpty(updatepo.get("CONTACTOR_PHONE").toString())) {
								if (updatepo.get("CONTACTOR_PHONE").toString().indexOf("-") != -1) {
									spro.setString("PHONE", updatepo.get("CONTACTOR_PHONE"));
								} else {
									spro.setString("MOBILE", updatepo.get("CONTACTOR_PHONE"));
								}
							}
							if (!StringUtils.isEmpty(updatepo.get("CONTACTOR_MOBILE").toString())) {
								if (updatepo.get("CONTACTOR_MOBILE").toString().indexOf("-") != -1
										&& "".equals(spro.getString("PHONE"))) {
									spro.setString("PHONE", updatepo.get("CONTACTOR_MOBILE"));
								} else {
									spro.setString("MOBILE", updatepo.get("CONTACTOR_MOBILE"));
								}
							}
							// spro.setPhone(updatepo.getContactorPhone());
							// spro.setMobile(updatepo.getContactorMobile());
							spro.setInteger("IS_AUDITING", 12781002);
							spro.setLong("CREATED_BY", 1L);
							spro.insert();
						}
					}
				}
				// end
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static String getStatusDescByCode(Integer code) {
		String Desc = null;
		if (code != null && code != 0) {
			List<SystemStatusPO> list = SystemStatusPO.find("STATUS_CODE = ?", new Object[] { code });
			if (list != null && list.size() > 0) {
				SystemStatusPO po1 = new SystemStatusPO();
				po1 = (SystemStatusPO) list.get(0);
				Desc = po1.getString("STATUS_DESC");
			}
		}
		return Desc;
	}

	// 撞单了的客户 记录在表中 计划任务的时候好上报
	public void updateCustomerToSame(String dealerCode, String number, String nid) throws ServiceBizException {
		List<Object> params = new ArrayList<>();
		String sql = "update Tm_Potential_Customer set IS_SAME_DCC=12781001,IS_UPLOAD=12781002,OEM_CUSTOMER_NO='" + nid
				+ "'" + " where ( Contactor_Mobile= ? or Contactor_Phone= ?)" + " and DEALER_CODE= ? AND D_KEY= ?  ";
		params.add(number);
		params.add(number);
		params.add(dealerCode);
		params.add(CommonConstants.D_KEY);
		try {
			logger.debug(sql);
			DAOUtil.execBatchPreparement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public String getCustomerNo(){
		CommonNoServiceImpl commonNoService = new CommonNoServiceImpl();
		String customerNo = commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX);
		return customerNo;

	}

}
