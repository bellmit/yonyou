/**
 * 
 */
package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.ProductGroupVO;
import com.infoservice.dms.cgcsl.vo.SADMS021VO;
import com.yonyou.dcs.de.SADCS021;
import com.yonyou.dcs.gacfca.SADCS021Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.DTO.gacfca.SADMS021Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SADCS021Impl extends BaseImpl implements SADCS021 {
	private static final Logger logger = LoggerFactory.getLogger(SADCS021Impl.class);
	
	@Autowired
	SADCS021Cloud Cloud ;

	@Override
	public String sendData(String param) throws Exception {
		logger.info("================一对一客户经理绑定修改下发开始（SADCS021）====================");
		try {
				send(Cloud.getDataList(param));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("================一对一客户经理绑定修改下发结束（SADCS021）====================");
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private void send(LinkedList<SADMS021Dto> dataList) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				for (SADMS021Dto vo : dataList) {
					List<SADMS021VO> vos = new ArrayList<>();
					setVos(vos,dataList);
					Map<String, Serializable> body = DEUtil.assembleBody(vos);
					String dmsCode = CommonUtils.checkNull(OemBaseDAO.getDmsDealerCode(vo.getEntityCode()).get("DMS_CODE"));
					if(!"".equals(dmsCode)){
						sendAMsg("SADMS021", dmsCode, body);
						logger.info("SADCS021发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
					}else{
						logger.info("SADCS021发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
						throw new ServiceBizException("经销商没有维护对应关系");
					}	
					
				}	
			}else{
				logger.info("====SADCS021一对一客户经理绑定修改下发结束====,无数据！ ");
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
	private void setVos(List<SADMS021VO> vos, LinkedList<SADMS021Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			SADMS021Dto dto = dataList.get(i);
			SADMS021VO vo = new SADMS021VO();
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
	
	
	

}
