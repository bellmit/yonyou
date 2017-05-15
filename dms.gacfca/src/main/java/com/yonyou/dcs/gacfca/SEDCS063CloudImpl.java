package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS063Dao;
import com.yonyou.dms.DTO.gacfca.SEDMS063Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞品定义下发接口
 * @author luoyang
 *
 */
@Service
public class SEDCS063CloudImpl extends BaseCloudImpl implements SEDCS063Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS063CloudImpl.class);
	
	@Autowired
	SEDCS063Dao dao;

	@Override
	public String execute(String id) throws ServiceBizException {
		logger.info("================呆滞品定义下发接口下发执行开始（SEDCS063）====================");
		LinkedList<SEDMS063Dto> vos = getDataList(id);
		if(vos != null && !vos.isEmpty()){
			sendData(vos);
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================呆滞品定义下发接口下发执行结束（SEDCS063）,下发了(" + size + ")条数据====================");
		return null;
	}

	/**
	 * send all
	 * @param dataList
	 */
	private void sendData(LinkedList<SEDMS063Dto> dataList) {
		try {
			if(null!=dataList && dataList.size()>0){
				int flag = 0;
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					logger.info("================呆滞品定义下发接口下发成功（SEDCS063）====================");
				}else{
					logger.info("================呆滞品定义下发接口下发失败（SEDCS063）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================呆滞品定义下发接口下发经销商无业务范围（SEDCS063）====================");
			}
		} catch (Exception e) {
			logger.info("================呆滞品定义下发接口下发异常（SEDCS063）====================");
		}
		
	}

	/**
	 * send by dealerCode
	 * @param dataList
	 * @param dealerCode
	 */
	private void sendData(SEDMS063Dto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<SEDMS063Dto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					logger.info("================呆滞品定义下发接口下发成功（SEDCS063）====================");
				}else{
					logger.info("================呆滞品定义下发接口下发失败（SEDCS063）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================呆滞品定义下发接口下发经销商无业务范围（SEDCS063）====================");
			}
		} catch (Exception e) {
			logger.info("================呆滞品定义下发接口下发异常（SEDCS063）====================");
		}
		
	}

	/**
	 * get by dealerCode
	 */
	@Override
	public LinkedList<SEDMS063Dto> getDataList(String id)
			throws ServiceBizException {
		LinkedList<SEDMS063Dto> vos = null;
		try {
			vos = dao.queryObsoleteMaterialDefine(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
