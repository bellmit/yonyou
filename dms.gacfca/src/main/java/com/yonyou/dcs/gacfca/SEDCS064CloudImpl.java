package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS063Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDCS064Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialReleaseDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞件发布上报接口
 * @author luoyang
 * result msg 1：成功 0：失败
 *
 */
@Service
public class SEDCS064CloudImpl extends BaseCloudImpl implements SEDCS064Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS064CloudImpl.class);
	
	@Autowired
	SEDCS063Dao dao;

	@Override
	public String handleExecutor(List<SEDCS064Dto> list) throws Exception {
		String msg = "1";
		logger.info("====试呆滞件发布上报接口(SEDCS064)接收开始===="); 
		for (SEDCS064Dto dto : list) {
			try {
				insertData(dto);
			} catch (Exception e) {
				logger.error("呆滞件发布上报接口(SEDCS064)接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====呆滞件发布上报接口(SEDCS064)接收成功====");
		logger.info("====呆滞件发布上报接口(SEDCS064)接收结束===="); 
		return msg;
	}

	@SuppressWarnings({ "unchecked" })
	private void insertData(SEDCS064Dto dto) {
		Map<String, Object> map = dao.getSaDcsDealerCode(dto.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));
		TtObsoleteMaterialReleaseDcsPO po = new TtObsoleteMaterialReleaseDcsPO();
		Date currentTime = new Date();
		po.setBigDecimal("ITEM_ID", dto.getItemId());
		po.setString("DEALER_CODE", dealerCode);
		po.setString("LINKMAN_NAME", dto.getLinkmanName());
		po.setString("LINKMAN_TEL", dto.getLinkmanTel());
		po.setString("LINKMAN_ADDRESS", dto.getLinkmanAddress());
		po.setString("WAREHOUSE", dto.getWarehouse());
		po.setString("STORAGE_CODE", dto.getStorageCode());
		po.setString("PART_CODE", dto.getPartCode());
		po.setString("PART_NAME", dto.getPartName());
		po.setInteger("RELEASE_NUMBER", dto.getReleaseNumber());
		po.setInteger("APPLY_NUMBER", dto.getReleaseNumber());
		po.setBigDecimal("COST_PRICE", dto.getCostPrice());
		po.setBigDecimal("SALES_PRICE", dto.getSalesPrice());
		po.setString("MEASURE_UNITS", dto.getMeasureUnits());
		po.setTimestamp("RELEASE_DATE", dto.getReleaseDate());
		po.setTimestamp("END_DATE", dto.getEndDate());
		po.setInteger("STATUS", OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01);
		po.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		po.setTimestamp("CREATE_DATE", currentTime);
		boolean flag = po.insert();
	}

}
