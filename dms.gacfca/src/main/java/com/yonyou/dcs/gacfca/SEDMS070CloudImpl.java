package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDMS070Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 接口名称：入库信息上报接口
 * 接口方式：DE
 * 接口频次：120min/次
 * 传输方向：DMS->DCS
 * @author luoyang
 *
 */
@Service
public class SEDMS070CloudImpl extends BaseCloudImpl implements SEDMS070Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDMS070CloudImpl.class);

	@Override
	public String handleExecutor(List<SEDMS070Dto> list) throws Exception {
		String msg = "1";
		logger.info("====入库信息上报接口(SEDMS070)接收开始===="); 
		int flag = 0;
		for (SEDMS070Dto dto : list) {
			try {
				insertData(dto);
			} catch (Exception e) {
				logger.error("入库信息上报接口(SEDMS070)接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====入库信息上报接口(SEDMS070)接收成功====");
		logger.info("====入库信息上报接口(SEDMS070)接收结束===="); 
		return msg;
	}

	private void insertData(SEDMS070Dto dto) {
		logger.info("****************入库单号***"+dto.getAllocateInNo()+"********************");
		TtObsoleteMaterialApplyDcsPO po = new TtObsoleteMaterialApplyDcsPO();
		Date currentTime = new Date();
		po.setTimestamp("PUT_WAREHOUS_DATE", dto.getStockInDate());
		po.setString("PUT_WAREHOUS_BY", dto.getHandler());
		po.setInteger("STATUS", OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_04);
		po.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);
		po.setTimestamp("UPDATE_DATE", currentTime);
		po.insert();
		
	}

}
