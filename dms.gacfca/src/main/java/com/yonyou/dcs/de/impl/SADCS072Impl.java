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

import com.infoservice.dms.cgcsl.vo.VehicleCustomerVO;
import com.yonyou.dcs.dao.SADCS072Dao;
import com.yonyou.dcs.de.SADCS072;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADCS072Impl  extends BaseImpl  implements  SADCS072 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS072Impl.class);
	
	@Autowired
	SADCS072Dao dao;
	
	@Override
	public String sendData(String vin,String dealerCode){
		try {
			List<String> dmsCodes = new ArrayList<String>();
			//下发的经销商
			List<Map> dealerList= dao.getSendDealer(vin,dealerCode);
			//下发数据
			LinkedList<VehicleCustomerDTO> dataList=dao.getDataList(vin);
			for (int i=0;i<dealerList.size();i++) {
				// 可下发的经销商列表
				dmsCodes.add(dealerList.get(i).get("DMS_CODE").toString());
			}
			send(dataList,dmsCodes);
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
	private String send(LinkedList<VehicleCustomerDTO> dataList, List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<VehicleCustomerVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(dmsCodes)){
					sendMsg("SEDMS072", dmsCodes, body);
					logger.info("SADCS072  车主资料发送成功======size："+dataList.size());
				}else{
					logger.info("SADCS072  车主资料下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SADCS072  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<VehicleCustomerVO> vos, LinkedList<VehicleCustomerDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			VehicleCustomerDTO dto = dataList.get(i);
			VehicleCustomerVO vo = new VehicleCustomerVO();
			vo.setOwnerProperty(dto.getOwnerProperty());
			vo.setOwnerName(dto.getOwnerName());
			vo.setContactorMobile(dto.getContactorMobile());
			vo.setContactorPhone(dto.getContactorPhone());
			vo.setCtCode(dto.getCtCode());
			vo.setCertificateNo(dto.getCertificateNo());
			vo.setGender(dto.getGender());
			vo.setFamilyIncome(dto.getFamilyIncome());
			vo.setOwnerMarriage(dto.getOwnerMarriage());
			vo.setProvince(dto.getProvince());
			vo.setCity(dto.getCity());
			vo.setDistrict(dto.getDistrict());
			vo.setAddress(dto.getAddress());
			vo.setIndustryFirst(dto.getIndustryFirst());
			vo.setIndustrySecond(dto.getIndustrySecond());
			vo.setZipCode(dto.getZipCode());
			vo.setEmail(dto.getEmail());
			vo.setVin(dto.getVin());
			vo.setUpDate(dto.getUpDate());
			vos.add(vo);
		}
	}
}
