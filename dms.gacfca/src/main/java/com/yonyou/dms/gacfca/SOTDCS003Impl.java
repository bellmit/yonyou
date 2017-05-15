package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS003Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class SOTDCS003Impl implements SOTDCS003 {
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS003Impl.class);
    @Autowired
    private SOTDCS003Cloud SOTDCS003Cloud;
	@Override
	public int getSOTDCS003(String status,String conphone,String conmobil) throws ServiceBizException {
	    String msg="1";
	    try {

	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        //如果开关是12781001 则开启售中工具
	        LinkedList<TiDmsNCustomerInfoDto> resultList=new LinkedList<TiDmsNCustomerInfoDto>();
	        logger.info("====SOTDCS003====1"); 
	        if (DictCodeConstants.DICT_IS_YES.equals(Utility.getDefaultValue("5434"))) { 
	            logger.info("====SOTDCS003====2");
	            //获取触发点的触发状态  是新增还是修改 该接口只在新增的时候触发上报
	            List<PotentialCusPO> list=null;
	            PotentialCusPO cus = null;
	            TiDmsNCustomerInfoDto dto=new TiDmsNCustomerInfoDto();
	            if ("A".equals(status)) {
	                logger.info("====SOTDCS003====3");
	                if (Utility.testString(conmobil)) {
	                    list=PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE =? AND D_KEY=? AND CONTACTOR_MOBILE=?", new Object[]{dealerCode,CommonConstants.D_KEY,conmobil});                
	                }else if (Utility.testString(conphone)) {
	                    list=PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE =? AND D_KEY=? AND  CONTACTOR_PHONE=?", new Object[]{dealerCode,CommonConstants.D_KEY,conphone});
	                }
	                if (list!=null&&list.size()>0) {
	                    logger.info("====SOTDCS003====4");
	                    cus= (PotentialCusPO) list.get(0);
	                    //查找客户的意向信息
//	                  TiDmsNCustomerInfoDto inter=new TiDmsNCustomerInfoDto();

	                    List<TtCusIntentPO> intList=TtCusIntentPO.findBySQL("SELECT * FROM tt_customer_intent WHERE DEALER_CODE=? AND D_KEY=? AND INTENT_ID=? ", new Object[]{dealerCode,CommonConstants.D_KEY,cus.getLong("INTENT_ID").toString()});
	                    logger.info("====SOTDCS003====5");
	                    TtCustomerIntentDetailPO intDetail=new TtCustomerIntentDetailPO();
	                    List<TtCustomerIntentDetailPO> deList=TtCustomerIntentDetailPO.findBySQL("SELECT * FROM tt_customer_intent_detail WHERE DEALER_CODE=? AND INTENT_ID=? AND IS_MAIN_MODEl=? AND D_KEY=?",  new Object[]{dealerCode,cus.getLong("INTENT_ID").toString(),12781001,CommonConstants.D_KEY});
	                    
	                    dto.setUniquenessId(cus.getString("CUSTOMER_NO"));
	                    dto.setDealerCode(dealerCode);
	                    dto.setBirthday(cus.getDate("BIRTHDAY"));
	                    logger.info("====SOTDCS003====6");
	                    if (Utility.testString(cus.getString("CUSTMOER_TYPE"))) {
	                        dto.setClientType(cus.getInteger("CUSTMOER_TYPE").toString());//客户类型
	                    }
	                    logger.info("====SOTDCS003====7");
	                    if (Utility.testString(cus.getString("CAMPAIGN_CODE"))) {
	                        dto.setSecondSourceType(cus.getString("CAMPAIGN_CODE").toString());//二级客户类型
	                    }
	                    if (Utility.testString(cus.getString("SOLD_BY"))) {
	                        dto.setDealerUserId(cus.getLong("SOLD_BY").toString());//销售顾问
	                    }
	                    logger.info("====SOTDCS003====8");
	                    dto.setName(cus.getString("CUSTOMER_NAME"));
	                    dto.setPhone(cus.getString("CONTACTOR_PHONE"));//手机号
	                    dto.setTelephone(cus.getString("CONTACTOR_MOBILE"));//固话
	                    logger.info("====SOTDCS003====9");
	                    if (deList!=null&&deList.size()>0) {//意向说明
	                        intDetail=(TtCustomerIntentDetailPO) deList.get(0);
	                        dto.setBrandId(intDetail.getString("INTENT_BRAND"));//车型
	                        dto.setCarStyleId(intDetail.getString("INTENT_CONFIG"));//配置
	                        dto.setIntentCarColor(intDetail.getString("INTENT_COLOR"));//颜色
	                        dto.setModelId(intDetail.getString("INTENT_SERIES"));//车系
	                    }
	                    logger.info("====SOTDCS003====10");
	                    if (intList!=null&&intList.size()>0) {
	                        TtCusIntentPO cusint=(TtCusIntentPO) intList.get(0);
	                        if (Utility.testString(cusint.getString("BUDGET_AMOUNT"))) {
	                            dto.setBuyCarBugget(cusint.getString("BUDGET_AMOUNT"));
	                        }
	                    }
	                    logger.info("====SOTDCS003===11");
	                    dto.setBuyCarcondition(cus.getInteger("IS_FIRST_BUY"));//是否首次购车
	                    dto.setCityId(cus.getInteger("CITY"));
	                    dto.setCreateDate(cus.getDate("FOUND_DATE"));
	                    
	                    if (Utility.testString(cus.getString("GENDER"))) {
	                        dto.setGender(cus.getString("GENDER"));
	                    }
	                    if (Utility.testString(cus.getString("INTENT_LEVEL"))) {
	                        dto.setOppLevelId(cus.getString("INTENT_LEVEL"));
	                    }
	                    dto.setProvinceId(cus.getInteger("PROVINCE"));
	                    if (Utility.testString(cus.getString("CUS_SOURCE"))) {
	                        dto.setSourceType(cus.getString("CUS_SOURCE"));
	                    }
	                    //新增是否到店，到店日期
	                    dto.setIsToShop(cus.getInteger("IS_TO_SHOP"));
	                    if (!StringUtils.isNullOrEmpty(cus.getDate("TIME_TO_SHOP"))) {
	                        dto.setTimeToShop(cus.getDate("TIME_TO_SHOP"));
	                    }
	                   
	                }
	            }
	            resultList.add(dto);
	        }
	        logger.info("====SOTDCS003===="); 
	        msg=SOTDCS003Cloud.handleExecutor(resultList);
	        return Integer.parseInt(msg);
	    
        } catch (Exception e) {
            logger.info("====SOTDCS003====失败"); 
           return 0;
        }
	}

}
