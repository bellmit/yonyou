package com.yonyou.dms.manage.service.basedata.payType;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.PayTypeDto;
import com.yonyou.dms.manage.domains.PO.basedata.PayTypePO;

/**
 * 支付方式接口
 * @author Benzc
 * @data 2016年12月21日
 */
public interface PayTypeService {
	
	PageInfoDto findAllAmount(Map<String, String> queryParams);//遍历数量方法
	
	void insertPayTypePo(PayTypeDto payDto)throws ServiceBizException;///新增
	
	public void updatePayType(String id,PayTypeDto payDto) throws ServiceBizException;//修改
	
	PayTypePO findById(String id) throws ServiceBizException;

}
