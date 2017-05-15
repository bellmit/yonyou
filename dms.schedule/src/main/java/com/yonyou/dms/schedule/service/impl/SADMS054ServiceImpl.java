package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;


import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMysteriousCustomerPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.schedule.domains.DTO.SADMS054Dto;


/**
 * 克莱斯勒明检和神秘上报 接口实现类
 * 计划任务
 * @author wangliang
 * @date 2017年2月20日
 */

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SADMS054ServiceImpl implements SADMS054Service {
	/*String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();*/
	@Override
	public LinkedList<SADMS054Dto> getSADMS054() throws Exception {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		Calendar calm = Calendar.getInstance();
		calm.setTime(date);
		Calendar calmLast = Calendar.getInstance();
		calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMaximum(Calendar.DAY_OF_MONTH)); // 本月的最后一天
		
		LinkedList<SADMS054Dto> resultList = new LinkedList<SADMS054Dto>();
		TmMysteriousCustomerPO myCustomer = new TmMysteriousCustomerPO();
		
		/*myCustomer.setString("DEALER_CODE", dealerCode);
		//是否上报 默认为否
		myCustomer.setInteger("IS_UPLOAD", 12781002);
		myCustomer.setInteger("D_KEY", CommonConstants.D_KEY);*/
		List myList = TmMysteriousCustomerPO.findBySQL("SELECT * FROM  tm_mysterious_customer WHERE IS_UPLOAD = ? and D_KEY = ? ", new Object[]{12781002,CommonConstants.D_KEY});
	
		if (myList != null && myList.size() > 0) {
			for (int i = 0; i < myList.size(); i++) {
				// 遍历神秘客 看能否在潜客表中找到其匹配的潜客信息（同时匹配手机电话）
				TmMysteriousCustomerPO myCustomer1 = new TmMysteriousCustomerPO();
				SADMS054Dto dto = new SADMS054Dto();
				myCustomer1 = (TmMysteriousCustomerPO) myList.get(i);
				PotentialCusPO tp = matchMysteriousCustomer(myCustomer1.getString("PHONE"));
				if (tp != null) {
					/*TmMysteriousCustomerPO myCustomer2 = TmMysteriousCustomerPO.findByCompositeKeys(myCustomer1.getLong("MYSTERIOUS_ID"));*/
					List<TmMysteriousCustomerPO> list = TmMysteriousCustomerPO.findBySQL("select * from Tm_Mysterious_Customer where MYSTERIOUS_ID = ?" ,new Object[]{myCustomer1.getLong("MYSTERIOUS_ID")});
					TmMysteriousCustomerPO myCustomer2 = list.get(0);
					myCustomer2.setInteger("IS_INPUT_DMS", 12781001);
					myCustomer2.setString("INPUT_NAME", tp.getString("CUSTOMER_NAME"));
					if (myCustomer1.getString("PHONE").equals(tp.getString("CONTACTOR_MOBILE"))) {
						myCustomer2.setString("INPUT_PHONE", tp.getString("CONTACTOR_MOBILE"));
					}
					if (myCustomer1.getString("PHONE").equals(tp.getString("CONTACTOR_PHONE"))) {
						myCustomer2.setString("INPUT_PHONE", tp.getString("CONTACTOR_PHONE"));
					}
					myCustomer2.setDate("INPUT_DATE", tp.getDate("FOUND_DATE"));
					myCustomer2.setInteger("IS_UPLOAD", 12781001);
					myCustomer2.setDate("UPLOAD_DATE", new Date());
					myCustomer2.setDate("MATCH_DATE", new Date());
					myCustomer2.setDate("UPDATED_AT", new Date());
					//myCustomer1.saveIt();
					myCustomer2.saveIt();
					dto.setDealerCode(myCustomer1.getString("DEALER_CODE"));
					dto.setDealerName(myCustomer1.getString("DEALER_NAME"));
					dto.setExecAuthor(myCustomer1.getString("EXEC_NAME"));
					dto.setPhone(myCustomer1.getString("PHONE"));
					dto.setIsInputDms(12781001);
					dto.setInputName(tp.getString("CUSTOMER_NAME"));
					dto.setInputName(myCustomer2.getString("INPUT_PHONE"));
					dto.setInputDate(myCustomer2.getDate("INPUT_DATE"));
					resultList.add(dto);
					
				} else {
					//TmMysteriousCustomerPO myCustomer2 = TmMysteriousCustomerPO.findByCompositeKeys(myCustomer1.getLong("MYSTERIOUS_ID"));
					List<TmMysteriousCustomerPO> list = TmMysteriousCustomerPO.findBySQL("select * from Tm_Mysterious_Customer where MYSTERIOUS_ID = ?" ,new Object[]{myCustomer1.getLong("MYSTERIOUS_ID")});
					TmMysteriousCustomerPO myCustomer2 = list.get(0);
					myCustomer2.setString("IS_INPUT_DMS", 12781002);
					myCustomer2.setString("IS_UPLOAD", 12781001);
					myCustomer2.setDate("UPLOAD_DATE", new Date());
					myCustomer2.setDate("MATCH_DATE", new Date());
					myCustomer2.setDate("UPDATED_AT", new Date());
					//myCustomer1.saveIt();
					myCustomer2.saveIt();
					dto.setDealerCode(myCustomer1.getString("DEALER_CODE"));
					dto.setDealerName(myCustomer1.getString("DEALER_NAME"));
					dto.setExecAuthor(myCustomer1.getString("EXEC_NAME"));
					dto.setPhone(myCustomer1.getString("PHONE"));
					dto.setIsInputDms(12781002);
					resultList.add(dto);
					
				}
			}
		}
		return resultList;
	}
	
	
	/**
	 * 将神秘客的手机在潜客信息进行匹配
	 */
	public PotentialCusPO matchMysteriousCustomer(String phone) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM tm_potential_customer WHERE  CONTACTOR_MOBILE='"
				+ phone + "' OR CONTACTOR_PHONE='" + phone + "' ");
		
		/*List list = new ArrayList();
		list.add(phone);*/
		PotentialCusPO tp =  null;
		//base.findAll()执行出来的不会再where条件下面加上dealerCode
		List<Map> rsList = Base.findAll(sb.toString());
		if (rsList != null && rsList.size() > 0) {
			for (int i = 0; i < rsList.size(); i++) {
				Map map = rsList.get(i);
				tp = new PotentialCusPO();
				tp.setString("DEALER_CODE", map.get("DEALER_CODE"));
				tp.setString("CUSTOMER_NO", map.get("CUSTOMER_NO"));
				tp.setString("CUSTOMER_NAME", map.get("CUSTOMER_NAME"));
				tp.setString("CONTACTOR_PHONE", map.get("CONTACTOR_PHONE"));
				tp.setString("CONTACTOR_MOBILE", map.get("CONTACTOR_MOBILE"));
				tp.setDate("FOUND_DATE", (Date) map.get("FOUND_DATE"));
			}
		}
		return tp;
	}
}
