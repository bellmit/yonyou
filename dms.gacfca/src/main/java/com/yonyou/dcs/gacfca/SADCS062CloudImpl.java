package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaleVehicleSaleDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.RenewalFailedDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtInsureRenewalPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS062CloudImpl
 * Description: 续保战败接口   接收
 * @author DC
 * @date 2017年4月10日 下午4:42:07
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS062CloudImpl extends BaseCloudImpl implements SADCS062Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS062CloudImpl.class);
	
	@Autowired
	SaleVehicleSaleDao saleDao;

	@Override
	public String handleExecutor(List<RenewalFailedDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====续保战败接口接收开始====");
		for (RenewalFailedDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("续保战败接口接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
			logger.info("====续保战败接口接收结束====");
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	private void insertData(RenewalFailedDTO vo) throws Exception {
		try {
			Map<String, Object> map = saleDao.getSaDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			
			TtInsureRenewalPO tirPO = new TtInsureRenewalPO();
			tirPO.setString("DEALER_CODE", dealerCode);//经销商代码
			tirPO.setLong("REMIND_ID", vo.getRemindId());//
			tirPO.setString("OWNER_NO", vo.getOwnerNo());//车主编号
			tirPO.setString("VIN", vo.getVin());//vin号
			tirPO.setInteger("RENEWAL_FAILED_REASON", vo.getRenewalfailedReason());
			tirPO.setString("RENEWAL_REMARK", vo.getRenewalRemark());//备注
			tirPO.setString("SERIES", vo.getSeriesCode());
			tirPO.setLong("CREATE_BY", DEConstant.DE_UPDATE_BY);
			tirPO.setDate("CREATE_DATE", new Date());
			tirPO.setDate("REMIND_DATE", vo.getRemindDate());
			
			tirPO.insert();
			logger.info("====续保战败接口接收成功====");
		} catch (Exception e) {
			logger.error("续保战败接口接收失败", e);
			throw new Exception(e);
		}
		
	}

}
