package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dms.DTO.gacfca.RepairOrderReStatusDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class HMCISE18CloudImpl extends BaseCloudImpl implements HMCISE18Cloud {
	private static final Logger logger = LoggerFactory.getLogger(HMCISE18CloudImpl.class);
	
	@Autowired
	RepairOrderResultStatusDao dao ;

	@Override
	public List<RepairOrderReStatusDTO> receiveData(List<RepairOrderReStatusDTO> vos) throws Exception {
		List<RepairOrderReStatusDTO> retdtos=new ArrayList<RepairOrderReStatusDTO>();
		try {
			logger.info("*************************** 同步查询工单取消结算状态上报信息开始******************************");
			for (RepairOrderReStatusDTO dto : vos) {
				// 获得取消结算返回给下端的VO列表
				List<RepairOrderReStatusDTO> dtos = dao.queryRepairDTO(dto);// 在该方法内部有检查索赔单是否存在的功能
				logger.info("====同步查询工单取消结算状态上报信息结束====");
				retdtos.addAll(dtos);
			}
		} catch (Exception e) {
			logger.error("同步查询工单取消结算状态失败", e);
			throw new ServiceBizException(e);
		} 
		logger.info("*************************** 同步查询工单取消结算状态上报成功******************************");
		return retdtos;
	}

}
