package com.yonyou.dms.manage.service.basedata.precontract;

import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.BookingTypeCodeDTO;
import com.yonyou.dms.common.domains.PO.basedata.BookingTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface precontractService {
	PageInfoDto findAllAmount(Map<String, String> queryParams);// 遍历数量方法

	void insertPayTypePo(BookingTypeCodeDTO bookingTypeCodeDTO) throws ServiceBizException;/// 新增

	public void updatePayType(String id, BookingTypeCodeDTO bookingTypeCodeDTO) throws ServiceBizException;// 修改

	BookingTypePO findById(String id) throws ServiceBizException;
}
