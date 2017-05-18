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

import com.infoservice.dms.cgcsl.vo.SADCS031VO;
import com.yonyou.dcs.de.SADCS030;
import com.yonyou.dcs.de.SADCS031;
import com.yonyou.dcs.gacfca.SADCS031Cluod;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerPolicySeriesPO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS031Impl extends BaseImpl implements SADCS031 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS031Impl.class);
	
	@Autowired
	SADCS031Cluod cloud;
	@Override
	public String sendData() throws DEException {
		logger.info("================大客户政策车系下发开始（SADCS031）====================");
		try {
			send(cloud.getDataList());
			logger.info("================大客户政策车系下发成功（SADCS031）====================");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================大客户政策车系下发结束（SADCS031）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(List<SADCS031Dto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				List<SADCS031VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendAllMsg("SADMS031", body);
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String strDate = df.format(new Date());
				// 状态修改,修改成已下发
				for (int i = 0; i < vos.size(); i++) {
					Long bigCustomerPolicyId = vos.get(i).getBigCustomerPolicyId();

					TtBigCustomerPolicySeriesPO updatePo = TtBigCustomerPolicySeriesPO
							.findFirst("BIG_CUSTOMER_POLICY_ID=?", bigCustomerPolicyId);
					updatePo.setInteger("Is_Scan", 1);
					updatePo.setInteger("Update_By", 11111111L);
					updatePo.setTimestamp("Update_Date", strDate);
					updatePo.saveIt();
				}
			}else{
				logger.info("SADCS031发送数据为空=====");
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
	private void setVos(List<SADCS031VO> vos, List<SADCS031Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADCS031Dto dto = dataList.get(i);
			SADCS031VO vo = new SADCS031VO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}


}
