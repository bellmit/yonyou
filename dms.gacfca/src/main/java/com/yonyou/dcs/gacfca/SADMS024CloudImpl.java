package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADMS024Dao;
import com.yonyou.dms.DTO.gacfca.LimitPriceSeriesDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.gacfca.SADMS111;
@Service
public class SADMS024CloudImpl extends BaseCloudImpl implements SADMS024Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS024CloudImpl.class);
	
	@Autowired
	SADMS024Dao dao;
	
	@Autowired
	SADMS111 sadms111;
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendData(String limitId,String  dsFlag){
		try {
			logger.info("==============SADMS024Cloud  限价车辆下发开始================");
			List<Map> listMap = dao.querySendDmsInfoById(limitId);
			List<LimitPriceSeriesDTO> dtolist = new ArrayList<LimitPriceSeriesDTO>();
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
						dtolist.add(dto);
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
						dtolist.add(dto);
						dealerList.add(dmsCodes);
					}
				}
				
			}
			
			//下发的数据
			if(null!=dtolist && dtolist.size()>0&&null!=dealerList && dealerList.size()>0){
				for(int i=0;i<dealerList.size();i++){
					//下发操作
					int flag = sadms111.getSADMS111(dealerList.get(i), dtolist);
					if(flag==1){
						logger.info("====================SADMS024Cloud  车系限价下发成功========================");
					}else{
						logger.info("================SADMS024Cloud  车系限价下发失败====================");
					}
				}

			}else{
				logger.info("====SADMS024Cloud  车系限价下发结束====,无数据！ ");
			}
			//更新数据状态
			updateDataStatus(limitId);
		} catch (Exception e) {
			logger.info("================SADMS024Cloud  车系限价下发异常====================");
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
