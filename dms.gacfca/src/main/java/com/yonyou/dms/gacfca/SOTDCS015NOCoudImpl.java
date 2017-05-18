package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS015Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新 接口实现类
 * 订单 NO
 * @author wangliang
 * @date 2017年2月24日
 */
@Service
public class SOTDCS015NOCoudImpl implements SOTDCS015NOCoud {
    private static final Logger logger = LoggerFactory.getLogger(SOTDCS015NOCoudImpl.class);
    @Autowired
    SOTDCS015Cloud SOTDCS015Cloud;

	@SuppressWarnings("unused")
	@Override
	public int getSOTDCS015NO(String customerNo,String sheetCreateDate) throws ServiceBizException{
	    String msg="1";
		try {
		    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  
		    boolean isupl=false;
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Long userId = FrameworkUtil.getLoginInfo().getUserId();
		if(customerNo != null && !"".equals(customerNo)) {
		  
			PotentialCusPO po =PotentialCusPO.findByCompositeKeys(dealerCode,customerNo);
			if(po != null) {
			   
				if(po.getInteger("IS_UPLOAD") != null && po.getInteger("IS_UPLOAD") == 12781001 ) {
				
				    isupl=true;
				}
				  if(!isupl){
				    
				        LinkedList<TiDmsUCustomerStatusDto> resultList = new LinkedList<TiDmsUCustomerStatusDto>();
		                TiDmsUCustomerStatusDto dto = new TiDmsUCustomerStatusDto();
		                dto.setDealerCode(dealerCode);
		                dto.setUniquenessID(customerNo);
		                if(!StringUtils.isNullOrEmpty(po.getLong("SPAD_CUSTOMER_ID"))) {
		                    dto.setFCAID(po.getLong("SPAD_CUSTOMER_ID"));
		                }
		                if(!StringUtils.isNullOrEmpty(po.getString("INTENT_LEVEL"))){
		                    dto.setOppLevelID(po.getString("INTENT_LEVEL"));
		                }
		                if(!StringUtils.isNullOrEmpty(sheetCreateDate)) {
		                    dto.setOrderDate(format1.parse(sheetCreateDate));
		                }
		                if(!StringUtils.isNullOrEmpty(po.getString("SOLD_BY"))) {
		                    dto.setDealerUserID(po.getString("SOLY_BY"));
		                }
		                dto.setDealerCode(dealerCode);
		                resultList.add(dto);
		                logger.info("==========================SOTDCS015Cloud上报开始");
		                msg =SOTDCS015Cloud.handleExecutor(resultList);
		                logger.info("==========================SOTDCS015Cloud上报结束");
		                System.out.println(customerNo);
		                PotentialCusPO po1 =PotentialCusPO.findByCompositeKeys(dealerCode,customerNo);
		                System.out.println(customerNo);
		                po1.setInteger("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_YES));
		              
		                System.out.println(customerNo);
		                po1.saveIt();
		                
		                
		            } 
	                }
		
		}
		
		} catch (Exception e) {
			return 0;
		}
		 logger.info("==========================SOTDCS015Cloudzhou====================");
		return Integer.parseInt(msg);
	}

}
