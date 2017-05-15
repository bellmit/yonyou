package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SalesLeadsCustomerDao;
import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SADMS003;

/**
 * 
* @ClassName: SADCS003CloudImpl 
* @Description: DCC潜在客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午3:33:48 
*
 */
@Service
public class SADCS003CloudImpl extends BaseCloudImpl implements SADCS003Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS003CloudImpl.class);
	
	@Autowired
	SalesLeadsCustomerDao dao;
	
	@Autowired
	SADMS003 sadms003;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================DCC潜在客户信息下发SADCS003开始====================");
		List<dccDto> dccVos = getDataList();
		for (int i = 0; i < dccVos.size(); i++) {
			String dealerCode = dccVos.get(i).getEntityCode();
			if(!"".equals(CommonUtils.checkNull(dealerCode))){
				
				sendData(dccVos.get(i));
				//根据nid将 TI_SALES_LEADS_CUSTOMER中 IS_SCAN=0的字段更新为1
				dao.finishSalesLeadsCustomerSCANStatus(dccVos.get(i).getNid());
				logger.info("====DCC潜在客户信息下发SADCS003结果====,第(" + (i+1) + ")下发了条数据");
			}
			
		}
		logger.info("====DCC潜在客户信息下发SADCS003结束====,下发了(" + dccVos.size() + ")条数据");
		return null;
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public List<dccDto> getDataList() throws ServiceBizException {
		List<dccDto> dccVos = dao.querySalesLeadsCustomerPOInfo();
		if (null == dccVos || dccVos.size() == 0) {
			logger.info("====DCC潜在客户信息下发SADCS003结束====,无数据");
			return null;
		}
		return dccVos;
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public void sendData(dccDto list ){
		try {
			if(null!=list){
				int flag = sadms003.getSADMS003(list);
				if(flag==1){
					logger.info("================车型组主数据下发成功（CLDCS002）====================");
				}else{
					logger.info("================车型组主数据下发失败（CLDCS002）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================车型组主数据下发经销商无业务范围（CLDCS002）====================");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("================车型组主数据下发异常（CLDCS002）====================");
		}
	}

}
