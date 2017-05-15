package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS008Cloud;
import com.yonyou.dcs.gacfca.SOTDCS008CloudImpl;
import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SOTDCS008Impl implements SOTDCS008 {
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS008Impl.class);
    @Autowired
    private SOTDCS008Cloud SOTDCS008Cloud;
    
    @Override
	public int getSOTDCS008(String status, String cusNo) throws ServiceBizException {
        String msg="1";
        try {
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            List<TiDmsUCustomerInfoDto> resultList = new LinkedList<TiDmsUCustomerInfoDto>();
            // 如果开关是12781001 则开启售中工具
            if (DictCodeConstants.DICT_IS_YES.equals(dealerCode)) {
                TiDmsUCustomerInfoDto dto = new TiDmsUCustomerInfoDto();
                // 获取触发点的触发状态 是新增还是修改 该接口只在更新的时候触发上报
                if ("U".equals(status)) {
                    // 因为是更新所以这个时候是能够获取到潜客编号的
                    if (cusNo != null) {
                        PotentialCusPO cus = null;
                        List cusList = PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER where DEALER_CODE=? AND CUSTOMER_NO =? AND D_KEY=? ",
                                new Object[] { dealerCode, cusNo, CommonConstants.D_KEY });
                       
                        if (cusList != null && cusList.size() > 0) {
                            cus = (PotentialCusPO) cusList.get(0);
                            // 查找客户的意向信息
                            TtCusIntentPO intent = new TtCusIntentPO();
                            List intList = TtCusIntentPO.findBySQL("SELECT * FROM tt_customer_intent where DEALER_CODE=? AND CUSTOMER_NO =? AND D_KEY=? ",
                                    new Object[] { dealerCode, cusNo, CommonConstants.D_KEY });
                            TtCustomerIntentDetailPO intDetail = new TtCustomerIntentDetailPO();
                            List deList = TtCustomerIntentDetailPO.findBySQL("SELECT * FROM tt_customer_intent_detail where DEALER_CODE=? AND INTENT_ID =? AND D_KEY=? ",
                                    new Object[] { dealerCode, cus.getLong("INTENT_ID").toString(),
                                            CommonConstants.D_KEY });
                            dto.setBirthday(cus.getDate("BIRTHDAY"));
                            dto.setEntityCode(dealerCode);
                            if (intList != null && intList.size() > 0) {
                                intent = (TtCusIntentPO) intList.get(0);
                                if (Utility.testString(intent.getDouble("BUDGET_AMOUNT"))) {
                                    dto.setBuyCarBudget(intent.getDouble("BUDGET_AMOUNT").toString());// 购车预算
                                }
                                dto.setGiveUpCause(intent.getString("DR_CODE"));// 休眠原因
                                dto.setGiveUpDate(intent.getDate("FAIL_CONFIRMED_DATE"));// 休眠日期
                            }
                            if (deList != null && deList.size() > 0) {
                                intDetail = (TtCustomerIntentDetailPO) deList.get(0);
                                dto.setBrandID(intDetail.getString("INTENT_BRAND"));
                                dto.setCarStyleID(intDetail.getString("INTENT_CONFIG"));
                                dto.setIntentCarColor(intDetail.getString("INTENT_COLOR"));
                                dto.setModelID(intDetail.getInteger("INTENT_SERIES").toString());
                                dto.setContendCar(intDetail.getString("COMPETITOR_SERIES"));// 竞品车型
                            }
                            dto.setBuyCarcondition(cus.getInteger("IS_FIRST_BUY"));// 购车类型
                            dto.setCityID(cus.getInteger("CITY"));
                            if (Utility.testString(cus.getInteger("CUSTOMER_TYPE"))) {
                                dto.setDealerUserID(cus.getLong("CUSTOMER_TYPE").toString());
                            }
                            if (Utility.testString(cus.getLong("SOLD_BY"))) {
                                dto.setDealerUserID(cus.getLong("SOLD_BY").toString());
                            }
                            if (Utility.testString(cus.getLong("SPAD_CUSTOMER_ID"))) {
                                dto.setFCAID(cus.getLong("SPAD_CUSTOMER_ID").intValue());
                            }
                            if (Utility.testString(cus.getString("CAMPAIGN_CODE"))) {
                                dto.setSecondSourceType(cus.getString("CAMPAIGN_CODE"));
                            }
                            if (Utility.testString(cus.getInteger("GENDER"))) {
                                dto.setGender(cus.getInteger("GENDER").toString());
                            }
                            if (Utility.testString(cus.getInteger("SLEEP_TYPE"))) {
                                dto.setGiveUpType(cus.getInteger("SLEEP_TYPE").toString());
                            }
                            dto.setName(cus.getString("CUSTOMER_NAME"));
                            if (Utility.testString(cus.getInteger("INTENT_LEVEL"))) {
                                dto.setOppLevelID(cus.getInteger("INTENT_LEVEL").toString());
                            }
                            dto.setPhone(cus.getString("CONTACTOR_MOBILE"));
                            dto.setProvinceID(cus.getInteger("PROVINCE"));
                            if (Utility.testString(cus.getInteger("CUS_SOURCE"))) {
                                dto.setSourceType(cus.getInteger("CUS_SOURCE").toString());
                            }
                            dto.setTelephone(cus.getString("CONTACTOR_PHONE"));
                            dto.setUniquenessID(cus.getString("CUSTOMER_NO"));
                            dto.setUpdateDate(cus.getDate("UPDATED_AT"));
                            // 新增是否到店，到店日期
                            dto.setIsToShop(cus.getInteger("IS_TO_SHOP"));
                            if (Utility.testString(cus.getDate("TIME_TO_SHOP"))) {
                                dto.setTimeToShop(cus.getDate("TIME_TO_SHOP"));
                            }
                        }
                        
                    }
                }
                
                resultList.add(dto);
            }
            System.out.println("SOTDCS008Impl");
            msg=SOTDCS008Cloud.handleExecutor(resultList);
            return Integer.parseInt(msg);
            
       
    
    
        } catch (Exception e) {
            return 0;
        }
    }

}
