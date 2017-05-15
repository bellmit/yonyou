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

import com.infoservice.dms.cgcsl.vo.SalesorderShoppingVO;
import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dcs.de.SA02;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SA02Impl  extends BaseImpl  implements  SA02 {
	private static final Logger logger = LoggerFactory.getLogger(SA02Impl.class);
	
	@Autowired
	RepairOrderResultStatusDao dao ;
	
	@Override
	public String sendData(List<String> dealerCodes, SalesorderShoppingDTO vo){
		try {
			LinkedList<SalesorderShoppingDTO> dataListlist=new LinkedList<SalesorderShoppingDTO>();
			List<String> dmsCodes = new ArrayList<String>();
			List<String> errCodes = new ArrayList<String>();
			dataListlist.add(vo);
			for (String dealerCode : dealerCodes) {
				try {
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCode);
					// 可下发的经销商列表
					dmsCodes.add(dmsDealer.get("DMS_CODE").toString());
				} catch (Exception e) {
					logger.error("Cann't send to " + dealerCode, e);
					errCodes.add(dealerCode);
				}
			}
			send(dataListlist,dmsCodes);
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
	private String send(LinkedList<SalesorderShoppingDTO> dataList, List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<SalesorderShoppingVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(dmsCodes)){
					sendMsg("DSA02", dmsCodes, body);
					logger.info("SA02调拨审批结果发送成功======size："+dataList.size());
				}else{
					logger.info("SA02调拨审批结果下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SA02发送数据为空=====");
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
	private void setVos(List<SalesorderShoppingVO> vos, LinkedList<SalesorderShoppingDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			SalesorderShoppingDTO dto = dataList.get(i);
			SalesorderShoppingVO vo = new SalesorderShoppingVO();
			vo.setVin(dto.getVin());
			vo.setRemark(dto.getRemark());
			vo.setDownTimestamp(dto.getDownTimestamp());
			vo.setEntityCode(dto.getEntityCode());
			vo.setConsigneeCode(dto.getConsigneeCode());
			vo.setProductCode(dto.getProductCode());
			vo.setDealStatus(dto.getDealStatus());
			vos.add(vo);
		}
	}
}
