/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS030Cloud;
import com.yonyou.dms.DTO.gacfca.TmWxReserverInfoReportDTO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @author Administrator 每天上报产品信息
 */
@Service
public class SADMS088CoudImpl implements SADMS088Coud {

	@Autowired
	SADCS030Cloud sadcsCloud;

	int clock = 100;// 100条一发;
	List<TmWxReserverInfoReportDTO> resultList = new LinkedList<TmWxReserverInfoReportDTO>();

	@Override
	public int getSADMS088() throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try {
			//String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Date date = new Date();
			Calendar ca = Calendar.getInstance();
			ca.setTime(date);
			String endDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()).toString() + " 01:29:59";
			ca.add(Calendar.DATE, -1);
			String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime()).toString() + " 01:29:59";
			List<Map> orderList = Base.findAll(" select * from  TM_MICROMSG_BOOKING_ORDER  where  ((CREATED_AT>='" + beginDate + "' and CREATED_AT<='" + endDate
					+ "') OR(UPDATED_AT>='" + beginDate + "' and UPDATED_AT<='" + endDate + "'))");
			if (orderList != null && orderList.size() > 0) {
				for (int i = 0; i < orderList.size(); i++) {
					TmWxReserverInfoReportDTO vo = new TmWxReserverInfoReportDTO();
					Map po = orderList.get(i);
					vo.setEntityCode(po.get("DEALER_CODE")+"");
					vo.setDealerCode(po.get("ENTITY_CODE") + "");
					vo.setLinkmanName(po.get("Linkman_Name") + "");
					vo.setVin(po.get("Vin") + "");
					vo.setRemark(po.get("Remark") + "");
					if (Utility.testString(po.get("Mileage") + "")) {
						vo.setMiles(po.get("Mileage") + "");
					}
					vo.setCreateDate((Date) po.get("Created_at"));
					vo.setUpdateDate((Date) po.get("Updated_at"));
					vo.setTelephone(po.get("Linkman_Mobile") + "");
					vo.setReserveTimeId(po.get("Reservation_Period") + "");
					vo.setLicenceNum(po.get("License") + "");
					vo.setReserveDate((Date) po.get("Reservation_Date"));
					vo.setReserveId(po.get("Reservation_Id") + "");
					vo.setStatus((Integer) po.get("Reservation_Status"));
					vo.setReserverConfirmTime((Date) po.get("Reserv_Confirm_Date"));
					vo.setReserveCancelDate((Date) po.get("Reserv_Cancel_Date"));
					vo.setReserveExpireDate((Date) po.get("Reserv_Expire_Date"));
					vo.setServiceFinishDate((Date) po.get("Reserv_Finish_Date"));
					resultList.add(vo);
					if (canSubmit(i + 1, clock, orderList.size())) {
						// 发送消息
						sadcsCloud.handleExecutor(resultList);
						resultList = new LinkedList<TmWxReserverInfoReportDTO>();
					}
				}
			}

		} catch (Exception e) {
			return 0;
		}

		return 1;
	}

	private boolean canSubmit(int i, int clock, int size) {
		if (size < clock) {
			if (i == size) {
				return true;
			} else {
				return false;
			}
		} else {
			if (((i % clock) == 0) || (i == size)) {
				return true;
			} else {
				return false;
			}
		}
	}

}
