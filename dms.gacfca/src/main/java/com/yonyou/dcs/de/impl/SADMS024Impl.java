package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADMS024Dao;
import com.yonyou.dcs.de.SADMS024;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.LimitPriceSeriesDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADMS024Impl  extends BaseImpl  implements  SADMS024 {
	private static final Logger logger = LoggerFactory.getLogger(SADMS024Impl.class);
	
	@Autowired
	SADMS024Dao dao;
	
	@Override
	public String sendData(String limitId,String  dsFlag){
		try {
			logger.info("==============SADMS024  限价车辆下发开始================");
			List<Map> listMap = dao.querySendDmsInfoById(limitId);
			List<LimitPriceSeriesDTO> dataList = new ArrayList<LimitPriceSeriesDTO>();
			//下发的经销商
			List<String> dealerList= new ArrayList<>();
			for (Map map : listMap) {
				String dmsCodes = String.valueOf(map.get("ENTITY_CODE"));
				String[] seriesCodes = String.valueOf(map.get("SERIES_CODE")).split(",");
				//页面做删除操作
				if("D".equals(dsFlag)){
					for (String seriesCode : seriesCodes) {
						LimitPriceSeriesDTO dto=new LimitPriceSeriesDTO();
						List<String> ListStrBrandCode=dao.findBrandBySeriesCode(seriesCode);
						String brandCode=ListStrBrandCode.get(0);
						dto.setSeriesCode(seriesCode);
						dto.setEntityCode(dmsCodes);
						dto.setIsValid(12781002);//下端标识无效
						dto.setRepairTypeCode(String.valueOf(map.get("REPAIR_TYPE")));
						dto.setBrandCode(brandCode);
						dto.setDownTimestamp(new Date());
						dto.setLimitPriceRate(Double.valueOf(map.get("LIMITED_RANGE").toString()));
						dataList.add(dto);
						dealerList.add(dmsCodes);
					}
				}
				//页面做下发操作
				if("S".equals(dsFlag)){
					for (String seriesCode : seriesCodes) {
						LimitPriceSeriesDTO dto=new LimitPriceSeriesDTO();
						List<String> ListStrBrandCode=dao.findBrandBySeriesCode(seriesCode);
						String brandCode=ListStrBrandCode.get(0);
						dto.setSeriesCode(seriesCode);
						dto.setEntityCode(dmsCodes);
						dto.setIsValid(12781001);//下端标识有效
						dto.setRepairTypeCode(String.valueOf(map.get("REPAIR_TYPE")));
						dto.setBrandCode(brandCode);
						dto.setDownTimestamp(new Date());
						dto.setLimitPriceRate(Double.valueOf(map.get("LIMITED_RANGE").toString()));
						dataList.add(dto);
						dealerList.add(dmsCodes);
					}
				}
			}
			send(dataList,dealerList);
			//更新数据状态
			updateDataStatus(limitId);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<LimitPriceSeriesDTO> dataList, List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				if(!"".equals(dmsCodes)){
					sendMsg("SADMS111", dmsCodes, body);
					logger.info("SADMS024  限价车辆发送成功======size："+dataList.size());
				}else{
					logger.info("SADMS024  限价车辆下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADMS024  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
	/**
	 * 更新状态
	 * @param limitId
	 */
	private void updateDataStatus(String limitId) {
		Long lmId = Long.valueOf(limitId);
		logger.info("Q##################### 下发DMS车系限价信息更新数据状态################结束#########" + limitId);
		TmLimiteCposPO.update("DESCEND_STATUS = ? AND DESCEND_DATE = ?", "LIMITED_ID = ?", OemDictCodeConstants.COMMON_RESOURCE_STATUS_02,new Date(),lmId);
		logger.info("Q##################### 下发DMS车系限价信息更新数据状态################结束#########");
		
	}
}
