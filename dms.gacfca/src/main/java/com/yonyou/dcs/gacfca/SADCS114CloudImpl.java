package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADMS114Dao;
import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS114CloudImpl extends BaseCloudImpl implements SADCS114Cloud {
	
	@Autowired 
	SADMS114Dao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS054CloudImpl.class);
	
	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<PayingBankDTO> getDataList(Long bankId) throws ServiceBizException {
		/**
		 *  获取数据逻辑：
		 * 	判断经销商是否为空
		 * 	  不为空、根据经销商查询业务范围重新给groupId赋值
		 * 	 为空      、判断groupId是否为空：不为空根据GroupID取物料信息，为空取所有
		 * 	 发送逻辑：每个经销商一次查一次
		 *  原有逻辑：
		 *  判断选中的业务范围都存在选中或所有经销商的业务范围
		 *  否则另外存储为Map
		 *  发送逻辑：分为两种情况发送
		 */
		LinkedList<PayingBankDTO> vos = null;
		vos = dao.queryPolicyApplyDateInfo(bankId);
		Integer size = vos==null?0:vos.size();
		logger.info("================下发数据大小："+ size +"====================");
		return vos;
	}
	

	/**
	 * 执行下发动作
	 */
	@Override
	public String execute(Long bankId) throws ServiceBizException {
		logger.info("====克莱斯勒明检和神秘客下发开始====");
		//第二步：循环获取经销商 所有拥有的业务范围数据并下发
		sendData(getDataList(bankId));
		return null;
	}


	private void sendData(LinkedList<PayingBankDTO> list) {
		try {
			if(null!=list && list.size()>0){
//				int flag = cldms.getCLDMS002(list,CommonUtils.checkNull(dao.getDmsDealerCode(dealerCode).get("DMS_CODE")));
//				if(flag==1){
//					logger.info("================车型组主数据下发成功（CLDCS002）====================");
//				}else{
//					logger.info("================车型组主数据下发失败（CLDCS002）====================");
//				}
			}else{
				//经销商无业务范围
				logger.info("================车型组主数据下发经销商无业务范围（CLDCS002）====================");
			}
		} catch (Exception e) {
			logger.info("================车型组主数据下发异常（CLDCS002）====================");
		}
	}

}
