/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.PreparedStatement;
import com.yonyou.dcs.gacfca.SADCS004Cloud;
import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.PO.TtCustomerIntentDetailPO;

/**
 * @author Administrator
 *
 */
@Service
public class SubmitCustomerToDccServiceImpl implements SubmitCustomerToDccService {


	private static final Logger logger = LoggerFactory.getLogger(SubmitCustomerToDccServiceImpl.class);
	@Autowired SADCS004Cloud SADCS004Cloud;
	
	@SuppressWarnings({"rawtypes", "unused" })
	@Override
	public List queryNeedToDccCustomer() throws ServiceBizException {
		StringBuffer sql=new StringBuffer();
		  	PreparedStatement ps = null;
		    ResultSet rs = null;
		    Date date = new Date();
		    Calendar calm = Calendar.getInstance();
		    calm.setTime(date);
		    calm.add(2, -6);
		    String dateAA = "'" + new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + "'";
		    sql.append( "SELECT DISTINCT CUSTOMER_NO FROM tm_potential_customer  where " ); 
		    sql.append("  intent_level in (13101001,13101002,13101003,13101004, 13101005) " ); 
		    //sql.append( " and DATEDIFF(NOW(), VALIDITY_BEGIN_DATE) >=0 " );
            sql.append( " and DATEDIFF( "+dateAA+ ", VALIDITY_BEGIN_DATE) >=0 " );
            
            System.err.println(sql.toString());
		    
		    List<Map> rsList = Base.findAll(sql.toString());
		
            return rsList;
	}

	@SuppressWarnings({ })
	@Override
	public void deleteFcustomerAction(String customerNo) throws ServiceBizException {
		 String sql = " delete from TT_SALES_PROMOTION_PLAN where (PROM_RESULT IS NULL OR PROM_RESULT=0) and customer_no='"+customerNo+"' ";
		 /*List list=new ArrayList();
		 list.add(customerNo); */
		// org.javalite.activejdbc.Base.find(sql.toString(), list);
		 Base.exec(sql.toString());
		 
	}
	
	@SuppressWarnings({  "unused" })
	public void updateCustomerToF() throws Exception{
		StringBuffer sql=new StringBuffer();
	    PreparedStatement ps = null;
	    Date date = new Date();
	    Calendar calm = Calendar.getInstance();
	    calm.setTime(date);
	    calm.add(2, -6);
	    String dateAA = "'" + new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + "'";
	    
	    sql.append( "update tm_potential_customer set intent_level=13101007,DCC_DATE='" );
	    sql.append( new SimpleDateFormat("yyyy-MM-dd").format(getCurrentDateTime()) + "',Fail_Intent_Level=intent_level,KEEP_APPLY_REASION='逾期自动休眠',IS_UPLOAD=12781001  where " ); 
	    sql.append( "  intent_level in (13101001,13101002,13101003,13101004, 13101005)" ); 
	    sql.append( "  and DateDiff( "+dateAA+ ", VALIDITY_BEGIN_DATE )>=0 " ); 
	    sql.append( " ");
		
	   
		 Base.exec(sql.toString());
		
	}
	/**
	 * string转换成date
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Date parseString2Date(String dateStr, String dateFormat) throws ParseException {

		if (null == dateStr || dateStr.length() <= 0)
			return null;

		if (null == dateFormat || dateFormat.length() <= 0)
			dateFormat = FULL_DATE_FORMAT;

		DateFormat formatter = new SimpleDateFormat(dateFormat);
		Date date = (Date) formatter.parse(dateStr);
		return date;
	}
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static java.util.Date getCurrentDateTime() throws ParseException{
		Date now = new Date(System.currentTimeMillis());
		return parseString2Date(sdf.format(now), null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SameToDccDto> performExecute() throws ServiceBizException {
			List<SameToDccDto> resultList= new ArrayList<SameToDccDto>();
			try {
				logger.info("============================6个月未生成订单转为休眠客户,Start=========================");
				List<Map> uplist = new ArrayList<Map>();
				//String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
				uplist = this.queryNeedToDccCustomer();
				if (uplist != null && uplist.size() > 0) {

					String customerNo = null;

					for (int i = 0; i < uplist.size(); i++) {
						Map po = uplist.get(i);
						customerNo = (String) po.get("CUSTOMER_NO");
						if (customerNo != null && !"".equals(customerNo)) {
							this.deleteFcustomerAction(customerNo);//改成F级而且上报的客户 要把未跟进的跟进记录删掉才行
							PotentialCusPO potentialCusPo = PotentialCusPO.findFirst(" customer_no=? ",
									new Object[] { customerNo });
							if (potentialCusPo.getInteger("IS_UPLOAD") != null
									|| potentialCusPo.getInteger("IS_UPLOAD") == 12781001) {
								SameToDccDto vo = new SameToDccDto();
								vo.setDealerCode(potentialCusPo.getString("DEALER_CODE"));
								vo.setNid(potentialCusPo.getLong("OEM_CUSTOMER_NO"));
								vo.setCustomerNo(customerNo);
								vo.setStatus("3");
								vo.setName(potentialCusPo.getString("CUSTOMER_NAME"));
								vo.setGender(potentialCusPo.getInteger("GENDER"));
								vo.setPhone(potentialCusPo.getString("CONTACTOR_MOBILE"));
								vo.setTelephone(potentialCusPo.getString("CONTACTOR_PHONE"));
								vo.setProvinceID(potentialCusPo.getInteger("PROVINCE"));
								vo.setCityID(potentialCusPo.getInteger("CITY"));
								vo.setCreatedAt(potentialCusPo.getDate("CREATED_AT"));
								try {

									vo.setSleepTime(getCurrentDateTime());
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								vo.setSleepReason("逾期自动休眠");
								vo.setOpportunityLevelID(13101007);
								if (potentialCusPo.getString("SOLD_BY") != null
										&& potentialCusPo.getString("SOLD_BY") != "") {
									UserPO userpo = new UserPO();
									userpo = UserPO.findByCompositeKeys(potentialCusPo.getString("SOLD_BY"));
									if (userpo != null) {
										vo.setSalesConsultant(userpo.getString("USER_NAME"));
									}
								}

								if (potentialCusPo.getInteger("INTEND_ID") != null
										&& potentialCusPo.getInteger("INTEND_ID") != 0) {
									TtCustomerIntentDetailPO intent2 = new TtCustomerIntentDetailPO();
									List inList = TtCustomerIntentDetailPO.findBySQL(
											"select *  from  tt_customer_intent_detail where INTENT_ID=? AND D_KEY=? AND IS_MAIN_MODEL=?",
											new Object[] { potentialCusPo.getInteger("INTENT_ID"),
													CommonConstants.D_KEY, 12781001 });
									if (inList != null && inList.size() > 0) {
										intent2 = (TtCustomerIntentDetailPO) inList.get(0);
										vo.setSeriasCode(intent2.getString("INTENT_SERIES"));
										vo.setModelCode(intent2.getString("INTENT_MODEL"));
										vo.setConsiderationID(intent2.getString("CHOOSE_REASON"));
										vo.setBrandCode(intent2.getString("INTENT_BRAND"));
										vo.setConfigerCode(intent2.getString("INTENT_CONFIG"));

									}
								}
								try {
									this.updateCustomerToF();
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								resultList.add(vo);

							}

						}
					}

				} 
				//调用厂端代码
				logger.info("======================准备调用SADCS004Cloud==========================");
				SADCS004Cloud.receiveDate(resultList);
				logger.info("======================SubmitCustomerToDcc数据上报完成==========================");
			} catch (Exception e) {
				// TODO: handle exception
				logger.info("======================SubmitCustomerToDcc出异常啦!!!========================");
				e.printStackTrace();
			}
			
			return resultList;
	}

	

}
