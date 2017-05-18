package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.ConfigurationPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * 创建客户信息（客户接待信息/需求分析）APP新增
 * 
 * @author wangliang
 * @date 2017年2月16日
 */
@Service
public class SOTDMS003CoudImpl implements SOTDMS003Coud {

	@Override
	public int SOTDMS003(List infoList) throws Exception {
		try {
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			/*
			 * ?if(!DictDataConstant.DICT_IS_YES.equals(BusinessUtility.
			 * getDefalutPara(con, entityCode, 5434))){ return 1 ; }
			 * 
			 * if (entityCode == null || "".equals(entityCode)) {
			 * atx.setErrorContext(CommonErrorConstant.MSG_ERROR_LOST_KEY,
			 * MessageService.getInstance().getMessage(CommonErrorConstant.
			 * MSG_ERROR_LOST_KEY), null); return 0; }
			 */
			//List infoList = new ArrayList();
			if (infoList != null && infoList.size() > 0) {
				for (int i = 0; i < infoList.size(); i++) {
					TiAppNCustomerInfoDto dto = (TiAppNCustomerInfoDto) infoList.get(i);
					if (dto.getPhone() == null || "".equals(dto.getPhone())) {
						return 0;
					}
					PotentialCusPO selectpo = new PotentialCusPO();
					selectpo.setString("DEALER_CODE", dealerCode);
					selectpo.setString("CONTACTOR_MOBILE", dto.getPhone());
					selectpo.setString("D_KEY", CommonConstants.D_KEY);
					// ? List selectList=POFactory.selectWithUR(connRead,
					// selectpo);
					List selectList = new ArrayList();

					// 声明 po ,ci , cid
					PotentialCusPO po = new PotentialCusPO();
					PotentialCusPO po1 = new PotentialCusPO();
					TtCusIntentPO ci = new TtCusIntentPO();
					// 客户意向报价
					TtCustomerIntentDetailPO cid = new TtCustomerIntentDetailPO();
					TtCustomerIntentDetailPO cid1 = new TtCustomerIntentDetailPO();
					String cusno = null;

					// 初始化为true 表示插入，false表示数据库已经有数据，需要更新
					boolean flag = true;
					if (selectList != null && selectList.size() > 0) {
						// 如果 有数据
						po = (PotentialCusPO) selectList.get(0);
						flag = false;
						cusno = po.getString("CUSTOMER_NO");
						// 判断时间戳，如果比传输的数据还要晚，则直接舍弃
						if (po.getDate("FOUND_DATE").getTime() > dto.getCreateDate().getTime()) {
							continue;
						}
						// 如果DMS端的是否到店为是，不能做修改
						if (po.getInteger("IS_TO_SHOP") == Utility.getInt(CommonConstants.DICT_IS_NO)) {
							// 1.如果DMS端的是否到店为否，更新数据
							if (dto.getIsToShop() == Utility.getInt(CommonConstants.DICT_IS_YES)) {
								po1.setInteger("IS_TO_SHOP", dto.getIsToShop());
								po1.setDate("TIME_TO_SHOP", dto.getTimeToShop());
							} else {
								po1.setInteger("IS_TO_SHOP", dto.getIsToShop());
							}
						}
					} else {
						// 如果DMS端没有数据，那么插入传过来的数据
						if (dto.getIsToShop() == Utility.getInt(CommonConstants.DICT_IS_YES)) {
							po1.setInteger("IS_TO_SHOP", dto.getIsToShop());
							po1.setDate("TIME_TO_SHOP", dto.getTimeToShop());
						} else {
							po1.setInteger("IS_TO_SHOP", dto.getIsToShop());
						}
					}
					TtCusIntentPO ci1 = new TtCusIntentPO();
					TtCusIntentPO selectci = new TtCusIntentPO();
					TtCustomerIntentDetailPO selectCid = new TtCustomerIntentDetailPO();
					if (!flag) {
						selectci.setString("DEALER_CODE", dealerCode);
						selectci.setString("CUSTOMER_NO", cusno);
						selectci.setString("D_KEY", CommonConstants.D_KEY);
						// ? List selectCiList= POFactory.selectWithUR(connRead,
						// selectci);
						List selectCiList = new ArrayList();
						if (selectCiList != null && selectCiList.size() > 0) {
							ci1 = (TtCusIntentPO) selectCiList.get(0);
						}
						selectCid.setString("DEALER_CODE", dealerCode);
						selectCid.setLong("INTENT_ID", ci1.getLong("INTENT_ID"));
						selectCid.setInteger("IS_MAIN_MODEL", 12781001);
						selectCid.setInteger("D_KEY", CommonConstants.D_KEY);
						// ?List selectCidList= POFactory.selectWithUR(connRead,
						// seletcCid);
						List selectCidList = new ArrayList();
						if (selectCidList != null && selectCidList.size() > 0) {
							cid1 = (TtCustomerIntentDetailPO) selectCidList.get(0);
						}
					}
					if (flag) {
						po1.setString("DEALER_CODE", dealerCode);
						/*
						 * ?po1.setCustomerNo( Utility.GetBillNo(entityCode,
						 * BillType.SAL_QZKHBH, con));//DMS客户编号
						 */
						// DMS客户编号
						po1.setString("CUSTOMER_NO", 0);
						po1.setString("D_KEY", CommonConstants.D_KEY);
						cusno = po1.getString("CUSTOMER_NO");
						po1.setString("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_NO)); // 待上报
					}
					po1.setString("SPAD_CUSTOMER_ID", dto.getFcaId());
					if (Utility.testString(dto.getClientType())) {
						po1.setInteger("CUSTOMER_TYPE", Utility.getInt(dto.getClientType()));
					}
					po1.setString("CUSTOMER_NAME", dto.getName());
					if (Utility.testString(dto.getGender())) {
						po1.setInteger("GENDER", Integer.valueOf(dto.getGender()));
					}
					po1.setString("CONTACTOR_MOBILE", dto.getPhone());
					po1.setString("CONTACTOR_PHONE", dto.getTelephone());
					po1.setInteger("PROVINCE", dto.getProvinceId());
					po1.setInteger("CITY", dto.getCityId());
					po1.setDate("BIRTHDAY", dto.getBirthday());
					if (Utility.testString(dto.getOppLevelId())) {
						po1.setInteger("INIT_LEVEL", Integer.valueOf(Utility.getInt(dto.getOppLevelId())));
						po1.setInteger("INTENT_LEVEL", Integer.valueOf(Utility.getInt(dto.getOppLevelId())));
					}

					if (Utility.testString(dto.getSourceType())) {
						po1.setInteger("CUS_SOURCE", Integer.valueOf(Utility.getInt(dto.getSourceType())));
					}

					if (Utility.testString(dto.getDealerUserId())) {
						po1.setLong("SOLD_BY", Utility.getLong(dto.getDealerUserId()));
						po1.setLong("OWNED_BY", Utility.getLong(dto.getDealerUserId()));
					}
					po1.setDate("VALIDITY_BEGIN_DATE", dto.getCreateDate());
					po1.setDate("FOUND_DATE", dto.getCreateDate());
					if (flag) {
						po1.setDate("CREATED_AT", Utility.getCurrentDateTime());
					} else {
						po1.setDate("UPDATED_AT", Utility.getCurrentDateTime());
					}
					if (Utility.testString(dto.getDealerUserId())) {
						po1.setLong("CREATED_BY", Utility.getLong(dto.getDealerUserId()));
						po1.setLong("UPDATED_BY", Utility.getLong(dto.getDealerUserId()));
					}
					// 休眠类型
					if (Utility.testString(dto.getGiveUpCause())) {
						po1.setInteger("SLEEP_TYPE", Utility.getInt(dto.getGiveUpType()));
					}
					// 休眠原因
					po1.setString("KEEP_APPLY_REASION", dto.getGiveUpCause());
					// 创建中间表信息 (客户意向)
					if (flag) {
						ci.setString("DEALER_CODE", dealerCode);
						// ci.setIntentId(POFactory.getLongPriKey(con, ci));
						ci.setLong("INTENT_ID", 0);
						ci.setInteger("IS_TEST_DRIVE", Utility.getInt(CommonConstants.DICT_IS_NO));
						ci.setInteger("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_NO));
						ci.setString("CUSTOMER_NO", cusno);
						ci.setInteger("D_KEY", CommonConstants.D_KEY);
						if (Utility.testString(dto.getDealerUserId())) {
							ci.setLong("OWNED_BY", Utility.getLong(dto.getDealerUserId()));
							ci.setLong("CREATED_BY", Utility.getLong(dto.getDealerUserId()));
						}
						ci.setDate("CREATED_AT", dto.getCreateDate());
					}

					// 购车预算
					if (Utility.testString(dto.getBuyCarBugget())) {
						ci.setDouble("BUDGET_AMOUNT", Utility.getDouble(Utility.StringFilter(dto.getBuyCarBugget())));
					}

					// 非首次购车客户类型判断 购车类型
					if (Utility.testString(dto.getBuyCarcondition())) {
						if ("10541003".equals(dto.getBuyCarBugget())) {
							// 首次购车
							po1.setInteger("IS_FIRST_BUY", 12781001);
							// 非首次购车客户类型
							po1.setInteger("REBUY_CUSTOMER_TYPE", null);
						}
						if ("10541001".equals(dto.getBuyCarBugget())) {
							po1.setInteger("IS_FIRST_BUY", 12781001);
							// 增购客户
							po1.setInteger("REBUY_CUSTOMER_TYPE", null);
						}
						if ("10541002".equals(dto.getBuyCarBugget())) {
							po1.setInteger("IS_FIRST_BUY", 12781001);
							// 置换意向潜客
							po1.setInteger("REBUY_CUSTOMER_TYPE", null);
						}
					} else {
						po1.setInteger("IS_FIRST_BUY", 12781001);
					}

					// 创建中间表信息(客户意向)
					if (Utility.testString(dto.getDealerUserId())) {
						ci.setLong("UPDATED_BY", Utility.getLong(dto.getDealerUserId()));
					}
					ci.setDate("UPDATED_AT", dto.getCreateDate());

					if (flag) {
						// 插入时候找增加PK
						cid.setString("DEALER_CODE", dealerCode);
						// ? cid.setItemId(POFactory.getLongPriKey(con, cid));
						cid.setInteger("ITEM_ID", 0);
						cid.setInteger("IS_MAIN_MODEL", 12781001);
						po1.setLong("INTENT_ID", ci.getLong("INTENT_ID"));
						if (Utility.testString(dto.getDealerUserId())) {
							cid.setLong("CREATED_BY", Utility.getLong(dto.getDealerUserId()));
						}
						cid.setDate("CREATED_AT", dto.getCreateDate());
						cid.setInteger("D_KEY", CommonConstants.D_KEY);
						cid.setLong("INTENT_ID", ci.getLong("INTENT_ID"));
					}
					cid.setString("INTENT_BRAND", dto.getBrandId());
					// 售中品牌、车型、车款对应DMS的品牌、车系、配置
					cid.setString("MODEL_ID", dto.getModelId()); // 车型ID
					// 如果存在配置信息，则需要反向获取在DMS的车型信息的数据，否则DMS前台不会显示
					if (Utility.testString(dto.getCarStyleId())) {
						ConfigurationPO configuartionPO = new ConfigurationPO();
						configuartionPO.setString("DEALER_CODE", dealerCode);
						configuartionPO.setString("CONFIG_CODE", dto.getCarStyleId());
						// ?configurationPO=POFactory.getByPriKey(con,
						// configurationPO);
						configuartionPO = configuartionPO.findFirst("CONFIG_CODE = ? and dealer_code = ?",
								new Object[] { dto.getCarStyleId(), dealerCode });
						if (configuartionPO != null) {
							cid.setString("INTENT_MODEL", configuartionPO.getString("MODEL_CODE"));
						}
					}
					// 车款ID
					cid.setString("INTENT_CONFIG", dto.getCarStyleId());
					// 车辆颜色ID
					cid.setString("INTENT_COLOR", dto.getIntentCarColor());
					if (Utility.testString(dto.getDealerUserId())) {
						cid.setLong("UPDATED_BY", Utility.getLong(dto.getDealerUserId()));
					}
					cid.setDate("UPDATED_AT", dto.getCreateDate());
					// 竞争车型
					cid.setInteger("COMPETITOR_SERIES", dto.getContendCar());
					// 休眠时间
					po1.setDate("AUDIT_DATE", dto.getGiveUpDate());
					po1.setInteger("IS_SPAD_CREATE", Utility.getInt(CommonConstants.DICT_IS_YES));
					po1.setInteger("SPAD_UPDATE_DATE", dto.getCreateDate());
					if (flag) {
						po1.saveIt();
						ci.saveIt();
						cid.saveIt();
					} else {
						selectpo.saveIt();
						selectci.saveIt();
						selectCid.saveIt();
						po1.saveIt();
						ci.saveIt();
						cid.saveIt();
					}
					// 如果是新增
					if (flag) {
						// 新增联系人
						TtPoCusLinkmanPO customerlinkmaninfo = new TtPoCusLinkmanPO();
						// ? Long itemid = POFactory.getLongPriKey(con,
						// customerlinkmaninfo);
						Long itemid = null;
						customerlinkmaninfo.setLong("ITEM_ID", itemid);
						customerlinkmaninfo.setString("DEALER_CODE", dealerCode);
						customerlinkmaninfo.setString("CUSTOMER_NO", cusno);
						customerlinkmaninfo.setString("CONTACTOR_NAME", dto.getName());
						customerlinkmaninfo.setInteger("D_KEY", CommonConstants.D_KEY);
						if (Utility.testString(dto.getGender())) {
							customerlinkmaninfo.setInteger("GENDER", Utility.getInt(dto.getGender()));
						}
						customerlinkmaninfo.setString("PHONE", dto.getTelephone());
						customerlinkmaninfo.setString("MOBILE", dto.getTelephone());
						// 默认联系人 默认为是
						customerlinkmaninfo.setInteger("IS_DEFAULT_CONTACTOR",
								Utility.getInt(CommonConstants.DICT_IS_YES));
						// 联系人
						customerlinkmaninfo.setString("CONTACTOR_TYPE", 13301003);
						if (Utility.testString(dto.getDealerUserId())) {
							customerlinkmaninfo.setLong("OWNED_BY", Utility.getLong(dto.getDealerUserId()));
							customerlinkmaninfo.setLong("CREATED_BY", Utility.getLong(dto.getDealerUserId()));
						}
						customerlinkmaninfo.setDate("CREATED_AT", dto.getCreateDate());
						customerlinkmaninfo.saveIt();
					} else {
						TtPoCusLinkmanPO customerlinkmaninfo = new TtPoCusLinkmanPO();
						customerlinkmaninfo.setString("DEALER_CODE", dealerCode);
						customerlinkmaninfo.setString("CUSTOMER_NO", cusno);
						customerlinkmaninfo.setInteger("D_KEY", CommonConstants.D_KEY);
						TtPoCusLinkmanPO ttPoCusLinkmanPO = customerlinkmaninfo
								.findFirst("CUSTOMER_NO = ? and dealer_code = ?", new Object[] { cusno, dealerCode });
						TtPoCusLinkmanPO ttLinkMan = new TtPoCusLinkmanPO();
						if (ttPoCusLinkmanPO != null) {
							if (Utility.testString(dto.getGender())) {
								ttLinkMan.setInteger("GENDER", Utility.getInt(dto.getGender()));
							}
							ttLinkMan.setString("CONTACTOR_NAME", dto.getName());
							ttLinkMan.setString("PHONE", dto.getTelephone());
							ttLinkMan.setString("MOBILE", dto.getPhone());
							if (Utility.testString(dto.getDealerUserId())) {
								ttLinkMan.setLong("UPDATED_BY", Utility.getLong(dto.getDealerUserId()));
							}
							ttLinkMan.setDate("UPDATED_AT", dto.getCreateDate());
							// ?
							customerlinkmaninfo.saveIt();
							ttLinkMan.saveIt();
						}
					}
				}
			}
			return 1;

		} catch (Exception e) {
			return 0;
		}
	}
}
