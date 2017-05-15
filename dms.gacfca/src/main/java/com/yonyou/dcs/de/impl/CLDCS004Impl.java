package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.TmMarketActivityVO;
import com.yonyou.dcs.dao.TmMarketActivityDao;
import com.yonyou.dcs.de.CLDCS004;
import com.yonyou.dcs.gacfca.CLDCS004Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
/**
 * 
* @ClassName: CLDCS004Impl 
* @Description: 市场活动（活动主单、车型清单）
* @author zhengzengliang 
* @date 2017年4月5日 下午6:53:41 
*
 */
@Service
public class CLDCS004Impl extends BaseImpl implements CLDCS004{
	
	private static final Logger logger = LoggerFactory.getLogger(CLDCS004Impl.class);
	
	@Autowired
	TmMarketActivityDao tmMarketActivityDao ;
	
	@Autowired
	CLDCS004Cloud  CLDCS004Cloud;

	@Override
	public String sendData(List<String> dealerList, String[] groupId) throws Exception {
		logger.info("================产品主数据下发开始（CLDCS004）====================");
		try {
			send(CLDCS004Cloud.getDataList());
			logger.info("================产品主数据下发成功（CLDCS004）====================");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================产品主数据下发结束（CLDCS004）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(LinkedList<TmMarketActivityDto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				List<TmMarketActivityVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("CLDMS004", body);
				//将TM_MARKET_ACTIVITY中 is_down=0或者null的字段更新为1
				tmMarketActivityDao.updateTmMarketActivityDownStatus();
			}else{
				logger.info("CLDMS004发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<TmMarketActivityVO> vos, LinkedList<TmMarketActivityDto> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			TmMarketActivityDto dto = dataList.get(i);
			TmMarketActivityVO vo = new TmMarketActivityVO();
			vo.setMarketFee(dto.getMarketFee());
			vo.setSeriesName(dto.getSeriesName());
			vo.setMarketName(dto.getMarketName());
			vo.setEndDate(dto.getEndDate());
			vo.setModelCode(dto.getModelCode());
			vo.setModelName(dto.getModelName());
			vo.setIsDown(dto.getIsDown());
			vo.setIsDel(dto.getIsDel());
			vo.setMarketNo(dto.getMarketNo());
			vo.setStartDate(dto.getStartDate());
			vo.setVer(dto.getVer());
			vo.setSeriesCode(dto.getSeriesCode());
			vo.setIsArc(dto.getIsArc());
			vos.add(vo);
		}
	}

}
