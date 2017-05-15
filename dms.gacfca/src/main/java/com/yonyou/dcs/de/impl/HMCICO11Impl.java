package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.UrlFunctionVO;
import com.yonyou.dcs.dao.UrlFunctionDao;
import com.yonyou.dcs.de.HMCICO11;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.UrlFunctionDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @ClassName: HMCICO11Cloud 
 * @Description: TODO(URL功能列表下发) 
 * @author xuqinqin
 * @date 2017-05-04 
 */
@Service
public class HMCICO11Impl extends BaseImpl implements HMCICO11 {
	
	private static final Logger logger = LoggerFactory.getLogger(HMCICO11Impl.class);
	
	@Autowired 
	UrlFunctionDao dao;
	
	@Override
	public List<String> sendData(List<String> dealerCodes){
		logger.info("====URL功能列表下发开始==== " + dealerCodes.size());
		List<String> errCodes = null;
		try {
			LinkedList<UrlFunctionDTO> list = dao.queryUrlFunction();
			if (null == list || list.size() == 0) {
				return null;
			}
			for (String dealerCode : dealerCodes) {
				Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCode);
				errCodes= send(list,dmsDealer.get("DMS_CODE")==null?"":dmsDealer.get("DMS_CODE").toString());
			}
		}catch (Exception e) {
			logger.info("================URL功能列表下发异常（HMCICO11）====================");
			logger.error(e.getMessage(), e);
			
		}
		return errCodes;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private List<String> send(LinkedList<UrlFunctionDTO> dataList, String dealerCode) throws Exception {
		List<String> errCodes = new ArrayList<>();
		try {

			if(null!=dataList && dataList.size()>0){
				List<UrlFunctionVO> vos=new ArrayList<UrlFunctionVO>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				String dmsCode = CommonUtils.checkNull(dao.getDmsDealerCode(dealerCode).get("DMS_CODE"));
				if(!"".equals(dmsCode)){
					sendAMsg("HMCICO11", dmsCode, body);
					logger.info("HMCICO11发送成功=====entityCode"+dmsCode+"===size："+dataList.size());
				}else{
					logger.info("HMCICO11发送失败=====entityCode"+dmsCode+"===size："+dataList.size());
					errCodes.add(dealerCode);
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("HMCICO11发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
		return errCodes;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<UrlFunctionVO> vos, LinkedList<UrlFunctionDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			UrlFunctionDTO dto = dataList.get(i);
			UrlFunctionVO vo = new UrlFunctionVO();
			vo.setDcsFunctionId(dto.getDcsFunctionId());
			vo.setFunctionName(dto.getFunctionName());
			vo.setUrl(dto.getUrl());
			vo.setSort(dto.getSort());
			vo.setParentFunctionCode(dto.getParentFunctionCode());
			vo.setDownTimestamp(new Date());
			vos.add(vo);
		}
	}
}
