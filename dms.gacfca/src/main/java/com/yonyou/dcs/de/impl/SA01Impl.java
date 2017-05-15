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
import com.yonyou.dcs.de.SA01;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.common.domains.DTO.common.SalesorderShoppingDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SA01Impl  extends BaseImpl  implements  SA01 {
	private static final Logger logger = LoggerFactory.getLogger(SA01Impl.class);
	
	@Autowired
	RepairOrderResultStatusDao dao ;
	
	@Override
	public String sendData(String dealerCode, SalesorderShoppingDTO vo){
		try {
			LinkedList<SalesorderShoppingDTO> dataListlist=new LinkedList<SalesorderShoppingDTO>();
			dataListlist.add(vo);
			
			send(dataListlist,dealerCode);
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
	private String send(LinkedList<SalesorderShoppingDTO> dataList, String dealerCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<SalesorderShoppingVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String entityCode = CommonUtils.checkNull(dao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(entityCode)){
					sendAMsg("DSA01", entityCode, body);
					logger.info("SA01车辆调拨申请发送成功=====entityCode"+entityCode+"===size："+dataList.size());
				}else{
					logger.info("SA01车辆调拨申请下发失败=====entityCode"+entityCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SA01发送数据为空=====");
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
			vos.add(vo);
		}
		
	}
}
