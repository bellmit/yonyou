package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SalesOrderDao;
import com.yonyou.dms.DTO.gacfca.TtSalesOrderDTO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: DCSBI001CloudImpl 
* @Description: 销售订单上报数据
* @author zhengzengliang 
* @date 2017年4月6日 下午3:45:03 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class DCSBI001CloudImpl extends BaseCloudImpl implements DCSBI001Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(DCSBI001CloudImpl.class);

	@Autowired
	SalesOrderDao salesOrderDao;
	
	@Override
	public String receiveDate(List<TtSalesOrderDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====经销商车辆实销数据上报接收开始===="); 
		for (TtSalesOrderDTO entry : dtoList) {
			try {
				List<Map> list = salesOrderDao.selectTtSalesOrder(entry);
				
				if(list.size() > 0){
					entry.setEntityCode(null);
					entry.setSoNo(null);
					SalesOrderPO soPO = new SalesOrderPO();
					BeanUtils.copyProperties(entry, soPO);
					soPO.saveIt();
				}else{
					SalesOrderPO soPO = new SalesOrderPO();
					BeanUtils.copyProperties(entry, soPO);
					soPO.saveIt();
				}
			} catch (Exception e) {
				logger.error("经销商车辆实销数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====经销商车辆实销数据上报接收结束===="); 
		return msg;
	}

}
