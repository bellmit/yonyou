package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.DTO.gacfca.SADMS017Dto;
import com.yonyou.dms.common.domains.PO.basedata.BigCustomerDefinitionPO;
import com.yonyou.dms.common.domains.PO.basedata.ReplaceRepayPO;
import com.yonyou.dms.common.domains.PO.basedata.TmWxServiceAdvisorChangePO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 下发大客户车型系列
 * @author Benzc
 * @date 2017年1月10日
 */
@Service
public class SADMS031CoudImpl implements SADMS031Coud{
	final Logger logger = Logger.getLogger(SADMS031CoudImpl.class);
	@Override
	public int getSADMS031(String dealerCode,List<SADCS031Dto> dtList) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try {
			// 此处需要定义一个DTO列表获取上端数据
			// LinkedList dtList = new LinkedList();
			// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			if(dtList != null && dtList.size() > 0){
				for(int i=0;i<dtList.size();i++){
					SADCS031Dto dto = new SADCS031Dto();
					dto = (SADCS031Dto) dtList.get(i);
					BigCustomerDefinitionPO po2 = new BigCustomerDefinitionPO();
					List<BigCustomerDefinitionPO> list = BigCustomerDefinitionPO.find(" DEALER_CODE=?",new Object[]{dealerCode});
					if(!StringUtils.isNullOrEmpty(dto.getBigCustomerPolicyId().toString())){
						if(!StringUtils.isNullOrEmpty(list)){
							BigCustomerDefinitionPO.update(
									"BRAND_CODE=?  , UPDATED_BY=? ,SERIES_CODE=? , IS_DELETE=? , IS_VALID=? , PS_TYPE=?",
									"POLICY_ID=? and DEALER_CODE=? and D_KEY=?",
									dto.getBrandCode(),1L,dto.getSeriesCode(),dto.getIsDelete(),dto.getStatus(),dto.getPsType(),
									dto.getBigCustomerPolicyId(),dealerCode,0);
							
						}else{
							
							po2.setString("DEALER_CODE", dealerCode);
							po2.setString("BRAND_CODE", dto.getBrandCode());
							po2.setString("SERIES_CODE", dto.getSeriesCode());
							po2.setInteger("IS_DELETE", dto.getIsDelete());
							po2.setInteger("IS_VALID", dto.getStatus());
							po2.setLong("POLICY_ID", dto.getBigCustomerPolicyId());
							po2.setInteger("D_KEY", 0);
							//po2.setTimestamp("CREATED_AT", format);
							po2.setLong("CREATED_BY", 1L);
							po2.setInteger("PS_TYPE",dto.getPsType());
							po2.saveIt();
							
						}
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		} finally {
			logger.info("==========SADMS017Impl结束===========");
		}
		
	}

}
