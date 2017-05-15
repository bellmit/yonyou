/**
 * 
 */
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

import com.infoservice.dms.cgcsl.vo.SADMS020VO;
import com.yonyou.dcs.de.SADCS020;
import com.yonyou.dcs.gacfca.SADCS020Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SADMS020Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */

@Service
public class SADCS020Impl extends BaseImpl implements SADCS020 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS020Impl.class);
	
	@Autowired
	SADCS020Cloud Cloud ;
	
	@Override
	public String sendData(String param) throws Exception {
		logger.info("================微信车主信息下发开始（SADCS020）====================");
		try {
				send(Cloud.getDataList(param));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================微信车主信息下发结束（SADCS020）====================");
		return null;
	}
	
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(List<SADMS020Dto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				for (SADMS020Dto vo : dataList) {
					List<SADMS020VO> vos = new ArrayList<>();
					setVos(vos,dataList);
					Map<String, Serializable> body = DEUtil.assembleBody(vos);
					String dmsCode = CommonUtils.checkNull(OemBaseDAO.getDmsDealerCode(vo.getDealerCode()).get("DMS_CODE"));
					if(!"".equals(dmsCode)){
						sendAMsg("SADMS020", dmsCode, body);
						logger.info("SADCS020发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
					}else{
						logger.info("SADCS020发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
						throw new ServiceBizException("经销商没有维护对应关系");
					}	
					
				}	
			}else{
				logger.info("====SADCS020微信车主信息下发结束====,无数据！ ");
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
	private void setVos(List<SADMS020VO> vos, List<SADMS020Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADMS020Dto dto = dataList.get(i);
			SADMS020VO vo = new SADMS020VO();
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
	

}
