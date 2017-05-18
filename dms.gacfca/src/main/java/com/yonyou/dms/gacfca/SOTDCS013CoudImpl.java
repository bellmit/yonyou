package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS013Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUSalesQuotasDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class SOTDCS013CoudImpl implements SOTDCS013Coud {
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS013CoudImpl.class);
    @Autowired
    SOTDCS013Cloud SOTDCS013Cloud;
    
	@Override
	public int getSOTDCS013(String[] cusno) throws ServiceBizException {
	    try {

	        System.out.println("----------------------------------");
	        Boolean istrue = true;
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
//	      Long userId = FrameworkUtil.getLoginInfo().getUserId();
	        // 判断ENTITY_CODE是否为空
	        if (dealerCode != null && !"".equals(dealerCode)) {

	        }
	        // 根据开关来判断是否执行程序5434
	        String defautParam = Utility.getDefaultValue("5434");
	        if (defautParam != null && defautParam.equals(DictCodeConstants.DICT_IS_NO)) {
	            System.out.println("---------------------------------1-");
	            istrue=false;
	        }
	        List<TiDmsUSalesQuotasDto> voList = new LinkedList();
	        if(istrue){
	            System.out.println("---------------------------------2-");
	              for (int i = 0; i < cusno.length; i++) {
	                  System.out.println("33333333");
	                    TiDmsUSalesQuotasDto dto = new TiDmsUSalesQuotasDto();
	                    System.out.println(dealerCode);
	                    System.out.println(cusno[i]);
	                    PotentialCusPO cuspo1 = PotentialCusPO.findByCompositeKeys(dealerCode,cusno[i]);
	                    
	                    if (!StringUtils.isNullOrEmpty(cuspo1)) {
	                        System.out.println(cusno[i]);
	                        if (cuspo1.getString("CUSTOMER_NO") != null) {
	                            dto.setUniquenessID(cuspo1.getString("CUSTOMER_NO"));
	                        }
	                        if (cuspo1.getLong("SAPD_CUSTOMER_ID") != null) {
	                            dto.setFCAID(Integer.valueOf(cuspo1.getLong("SAPD_CUSTOMER_Id") + ""));
	                        }
	                        if (cuspo1.getLong("LAST_SOLD_BY") != null) {
	                            dto.setOldDealerUserID(cuspo1.getLong("LAST_SOLD_BY") + "");
	                        }
	                        dto.setDealerCode(dealerCode);
	                        if (cuspo1.getLong("SOLD_BY") != null) {
	                            dto.setDealerUserID(cuspo1.getLong("SOLD_BY") + "");
	                        }
	                        dto.setUpdateDate(cuspo1.getDate("CONSULTANT_TIME"));
	                        dto.setDealerCode(dealerCode);
	                        voList.add(dto);
	                    }
	                }
	              
	        }
	        if(voList!=null&&voList.size()>0){
	            logger.info("====================SOTDCS013Impl");
	            SOTDCS013Cloud.handleExecutor(voList);
	        }
	        
	        return 1;
	    
        } catch (Exception e) {
            return 0;
        }
	}

}
