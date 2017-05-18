/**
 * 
 */
package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SADCS032VO;
import com.yonyou.dcs.de.SADCS032;
import com.yonyou.dcs.gacfca.SADCS032Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerApplyDataPO;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS032Impl extends BaseImpl implements SADCS032 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS032Impl.class);
	
	@Autowired
	SADCS032Cloud cloud;
	
	@Override
	public String sendData() throws Exception {
		logger.info("================大客户政策车型数据下发开始（SADCS032）====================");
		try {
			send(cloud.getDataList());
			logger.info("================大客户政策车型数据下发成功（SADCS032）====================");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================大客户政策车型数据下发结束（SADCS032）====================");
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(List<SADCS032Dto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				List<SADCS032VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SADMS032", body);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String format = df.format(new Date());
				// 状态修改,修改成已下发
				for (int i = 0; i < vos.size(); i++) {
					Long bigCustomerApplyId = vos.get(i).getBigCustomerApplyId();

					TtBigCustomerApplyDataPO updatePo = TtBigCustomerApplyDataPO
							.findFirst("BIG_CUSTOMER_APPLY_ID=?", bigCustomerApplyId);

					updatePo.setInteger("Is_Scan", 1);
					updatePo.setInteger("Update_By", 11111111L);
					updatePo.setTimestamp("Update_Date", format);
					updatePo.saveIt();
				}
			}else{
				logger.info("SADCS032发送数据为空=====");
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
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setVos(List<SADCS032VO> vos, List<SADCS032Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADCS032Dto dto = dataList.get(i);
			SADCS032VO vo = new SADCS032VO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}

}
