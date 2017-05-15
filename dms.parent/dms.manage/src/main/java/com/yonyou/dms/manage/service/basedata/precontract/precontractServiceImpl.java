package com.yonyou.dms.manage.service.basedata.precontract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.BookingTypeCodeDTO;
import com.yonyou.dms.common.domains.PO.basedata.BookingTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.PO.basedata.PayTypePO;
@Service
public class precontractServiceImpl implements precontractService {
	// 遍历数量方法
	@Override
	public PageInfoDto findAllAmount(Map<String, String> queryParams) {
		StringBuilder sb = new StringBuilder(
				"SELECT DEALER_CODE,BOOKING_TYPE_CODE,BOOKING_TYPE_NAME FROM TM_BOOKING_TYPE");
		PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), null);
		System.err.println(dto);
		return dto;
	}

	/**
	 * 新增
	 */
	@Override
	public void insertPayTypePo(BookingTypeCodeDTO bookingTypeCodeDTO) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"SELECT DEALER_CODE,BOOKING_TYPE_CODE,BOOKING_TYPE_NAME FROM TM_BOOKING_TYPE where 1=1 and BOOKING_TYPE_CODE=? ");
		List<Object> list = new ArrayList<Object>();
		list.add(bookingTypeCodeDTO.getBookingTypeCode());
		List<Map> map = DAOUtil.findAll(sb.toString(), list);
		StringBuffer sb2 = new StringBuffer(
				"SELECT DEALER_CODE,BOOKING_TYPE_CODE,BOOKING_TYPE_NAME FROM TM_BOOKING_TYPE where 1=1 and BOOKING_TYPE_NAME=? ");
		List<Object> list2 = new ArrayList<Object>();
		list2.add(bookingTypeCodeDTO.getBookingTypeName());
		List<Map> map2 = DAOUtil.findAll(sb2.toString(), list2);
		if (map.size() > 0 || map2.size() > 0) {
			throw new ServiceBizException("预约类别代码或名称不能重复！");
		} else {
			BookingTypePO lap = new BookingTypePO();
			lap.setString("BOOKING_TYPE_CODE", bookingTypeCodeDTO.getBookingTypeCode());
			lap.setString("BOOKING_TYPE_NAME", bookingTypeCodeDTO.getBookingTypeName());
			lap.saveIt();

		}
	}

	/**
	 * 修改
	 */
	@Override
	public void updatePayType(String id, BookingTypeCodeDTO bookingTypeCodeDTO) throws ServiceBizException {
		BookingTypePO bookingTypePO = BookingTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), id);
		setPayType(bookingTypePO, bookingTypeCodeDTO);
		bookingTypePO.saveIt();
	}

	/**
	 * 设置对象属性
	 * 
	 * @param bookingTypePO
	 * @param bookingTypeCodeDTO
	 */
	private void setPayType(BookingTypePO bookingTypePO, BookingTypeCodeDTO bookingTypeCodeDTO) {
		bookingTypePO.setString("BOOKING_TYPE_CODE", bookingTypeCodeDTO.getBookingTypeCode());
		bookingTypePO.setString("BOOKING_TYPE_NAME", bookingTypeCodeDTO.getBookingTypeName());
	}

	/**
	 * 根据id查询
	 */
	@Override
	public BookingTypePO findById(String id) throws ServiceBizException {
		return BookingTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), id);
	}

}
