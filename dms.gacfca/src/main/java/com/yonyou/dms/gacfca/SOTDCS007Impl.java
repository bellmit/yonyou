package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS007Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNCultivateDto;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPromotionPlanDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class SOTDCS007Impl implements SOTDCS007 {
    
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS007Impl.class);
    @Autowired
    SOTDCS007Cloud SOTDCS007Cloud;

	@Override
	public int getSOTDCS007(String status, TtSalesPromotionPlanDTO salesProDto) throws Exception {
	    try {
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        LinkedList<TiDmsNCultivateDto> resultList = new LinkedList<TiDmsNCultivateDto>();
	        if (DictCodeConstants.DICT_IS_YES.equals(Utility.getDefaultValue("5434"))) {
	                        // 直接获取前台传过来的值上报
	                        TiDmsNCultivateDto dto = new TiDmsNCultivateDto();
	                        System.out.println(dealerCode);
	                        dto.setEntityCode(dealerCode);
	                        dto.setDealerCode(dealerCode);
	                        System.out.println(salesProDto.getPromContent());
	                        dto.setCommContent(salesProDto.getPromContent());
	                        System.out.println(salesProDto.getActionDate());
	                        dto.setCommDate(salesProDto.getActionDate());
	                        if(!StringUtils.isNullOrEmpty(salesProDto.getPromWay())){
	                            dto.setCommType(salesProDto.getPromWay().toString());
	                        }else{
	                            dto.setCommType(null);
	                        }
	                        dto.setCreateDate(new Date());
	                        dto.setDealerUserId(salesProDto.getSoldBy());
	                        if(!StringUtils.isNullOrEmpty(salesProDto.getNextGrade())){
	                            dto.setFollowOppLevelId(salesProDto.getNextGrade().toString());// 跟进后级别
                            }else{
                                dto.setFollowOppLevelId(null);
                            }
	                       
	                        dto.setNextCommContent(salesProDto.getNextPromContent());//
	                        dto.setNextCommDate(salesProDto.getNextPromDate());
	                        dto.setUniquenessId(salesProDto.getCustomerNo());
	                        // 根据customerNo查找到FCAID
	                        System.out.println(salesProDto.getCustomerNo());
	                        PotentialCusPO cus2 = PotentialCusPO.findByCompositeKeys(dealerCode,salesProDto.getCustomerNo());
	                        if (Utility.testString(cus2.getLong("SPAD_CUSTOMER_ID"))) {
	                            dto.setFCAID(cus2.getLong("SPAD_CUSTOMER_ID"));
	                        }
	                        resultList.add(dto);
	        }
	        if(resultList!=null&&resultList.size()>0){
	            logger.info("========================SOTDCS007开始");
	            SOTDCS007Cloud.handleExecutor(resultList);
	            logger.info("========================SOTDCS007结束");
	        }

	        return 1;
            
        } catch (Exception e) {
            return 0;
        }

	}

}
