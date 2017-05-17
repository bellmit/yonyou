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

import com.infoservice.dms.cgcsl.vo.SA007VO;
import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dcs.de.SADCS007_BAK;
import com.yonyou.dcs.gacfca.SADCS007_BAKCloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SADCS007_BAKImpl extends BaseImpl implements SADCS007_BAK {
	
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS007_BAKImpl.class);
	
	@Autowired
	SADCS007_BAKCloud cloud;
	
	@Autowired
	RepairOrderResultStatusDao dao ;
	

	public String sendData() throws Exception {
		logger.info("================经销商之间车辆调拨下发开始（SADCS007_BAK）,无数据====================");
		
		try {
			//获取下发数据
			List<Map> list=cloud.getSendDate();
			if (null == list || list.size() == 0) {
				logger.info("================经销商之间车辆调拨下发（SADCS007_BAK）,无数据====================");
				return null;
			}
			List<String> dmsCodes = new ArrayList<String>();
			//下发经销商
			List<Long> dealerIds = new ArrayList<Long>();
		    //组装数据
			LinkedList<SA007Dto> dtoList=cloud.setDto(list);
			
			for(int i=0;i<list.size();i++){
				Long inDealerId = Long.parseLong(CommonUtils.checkNull(list.get(i).get("IN_DEALER_ID"), "0L"));
				Long outDealerId = Long.parseLong(CommonUtils.checkNull(list.get(i).get("OUT_DEALER_ID"), "0L"));
				dealerIds.add(inDealerId);
				dealerIds.add(outDealerId);
			}
			
			for (Long dealerId : dealerIds) {
				try {
					System.out.println("经销商ID：" + dealerId);
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerId);
					System.out.println("dmsDealer.get(DMS_CODE).toString()::" + dmsDealer.get("DMS_CODE").toString());
					if(null!=dmsDealer&&dmsDealer.size()>0){
						
					}else{
						logger.info("经销商:"+dealerId+"没有维护对应的下端的entityCode");
					}
					// 可下发的经销商列表
					dmsCodes.add(dmsDealer.get("DMS_CODE").toString());
					
				} catch (Exception e) {
					logger.error("Cann't send to " + dealerId, e);
				}
			}
			
			send(dtoList,dmsCodes);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================车型组主数据下发执行结束（CLDCS002）====================");
		return null;
	}

	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(LinkedList<SA007Dto> dataList, List<String> dmsCodes) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				List<SA007VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(dmsCodes)){
					sendMsg("SADMS007", dmsCodes, body);
					logger.info("SADCS007_BAK发送成功=====entityCode"+dmsCodes+"===size："+dataList.size());
				}else{
					logger.info("SADCS007_BAK发送失败=====entityCode"+dmsCodes+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS007_BAK发送数据为空=====");
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
	private void setVos(List<SA007VO> vos, LinkedList<SA007Dto> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			SA007Dto dto = dataList.get(i);
			SA007VO vo = new SA007VO();
			vo.setInEntityCode(dto.getInEntityCode());
			vo.setOutEntityCode(dto.getOutEntityCode());
			vo.setProductCode(dto.getProductCode());
			vo.setEngineNo(dto.getEngineNo());
			vo.setVin(dto.getVin());
			vo.setManufactureDate(dto.getManufactureDate());
			vo.setHasCertificate(dto.getHasCertificate());
			vo.setCertificateNumber(dto.getCertificateNumber());
			vo.setFactoryDate(dto.getFactoryDate());
			vo.setVehiclePrice(dto.getVehiclePrice());
			vos.add(vo);
		}
	}
}
