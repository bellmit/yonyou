package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SA007VO;
import com.yonyou.dcs.de.SADCS007;
import com.yonyou.dcs.gacfca.SADCS007Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
/**
 * 经销商之间车辆调拨下发
 * @author Benzc
 * @date 2017年5月15日
 */
@Service
public class SADCS007Impl extends BaseImpl implements SADCS007{
    
	private static final Logger logger = LoggerFactory.getLogger(SADCS007Impl.class);
	
	@Autowired SADCS007Cloud Cloud;
	
	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public String sendData(List<Long> dealerIds,Long vehicleId,Long inDealerId,Long outDealerId) throws Exception {
		logger.info("========经销商之间车辆调拨下发执行开始(SADCS007Impl)========");
		List<String> dmsCodes = new ArrayList<String>();
		try {
			//下发的数据
			if(null!=dealerIds || dealerIds.size()>0){
				for (Long dealerId : dealerIds) {
					try {
						Map<String, Object> dmsDealer = OemBaseDAO.getDmsDealerCode(dealerId);
						System.out.println("dmsDealer.get(DMS_CODE).toString()::"+dmsDealer.get("DMS_CODE").toString());
						//可下发的经销商列表
						dmsCodes.add(dmsDealer.get("DMS_CODE").toString());
					} catch (Exception e) {
						logger.error("Cann't send to " + dealerId, e);
						//errCodes.add(dealerId);
					}
				}
			}
			
				
				send(Cloud.getDataList(vehicleId, inDealerId, outDealerId),dmsCodes);
				
			
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		logger.info("========经销商之间车辆调拨下发执行结束(SADCS007Impl)========");
		return null;
	}
    
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(List<SA007Dto> dataList,List<String> dmsCodes) throws Exception {
		try {
			if(dataList != null && dataList.size() > 0){
				List<SA007VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendMsg("SADMS007",dmsCodes, body);
				logger.info("SADCS007 经销商之间车辆调拨下发发送成功======size："+dataList.size());
			}else{
				logger.info("SADCS007 经销商之间车辆调拨信息为空========");
			}
		} catch (Throwable t) {
			logger.info("SADCS007 经销商之间车辆调拨下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		}
		return null;
	}
	
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setVos(List<SA007VO> vos, List<SA007Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SA007Dto dto = dataList.get(i);
			SA007VO vo = new SA007VO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
}
