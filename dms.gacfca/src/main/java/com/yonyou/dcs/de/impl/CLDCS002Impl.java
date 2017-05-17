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
import com.yonyou.dcs.de.CLDCS002;
import com.yonyou.dcs.gacfca.CLDCS002Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class CLDCS002Impl extends BaseImpl implements CLDCS002 {
	
	
	private static final Logger logger = LoggerFactory.getLogger(CLDCS002Impl.class);
	
	@Autowired
	CLDCS002Cloud cloud;
	
	
	public String sendData(List<String> dealerList, String[] groupId) throws Exception {
		logger.info("================车型组主数据下发执行开始（CLDCS002）====================");
		
		try {
			if(null==dealerList || dealerList.size()==0){
				dealerList = OemBaseDAO.getAllDcsCode(1);
			}
			for (int i = 0; i < dealerList.size(); i++) {
				String dealerCode = dealerList.get(i);
				send(cloud.getDataList(groupId,dealerCode),dealerCode);
			}
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
	private void send(LinkedList<CLDMS002Dto> dataList, String dealerCode) throws Exception {
		try {

			if(null!=dataList && dataList.size()>0){
				List<ProductGroupVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(OemBaseDAO.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("CLDMS002", dmsCode, body);
					logger.info("CLDMS002发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("CLDMS002发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("CLDMS002发送数据为空=====");
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
	private void setVos(List<ProductGroupVO> vos, LinkedList<CLDMS002Dto> dataList) throws IllegalAccessException, InvocationTargetException {
		for (int i = 0; i < dataList.size(); i++) {
			CLDMS002Dto dto = dataList.get(i);
			ProductGroupVO vo = new ProductGroupVO();
			vo.setEntityCode(dto.getDealerCode());
			BeanUtils.copyProperties(dto, vo);
			vos.add(vo);
		}
	}
	
}
