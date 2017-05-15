/**
 * 
 */
package com.yonyou.dms.schedule.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 每天将400逾期未进行二次补录的数据标为逾期
 * @author xhy
 * @date 2017年2月23号
 */
@Service
public class CheckUploadWetherTimeoutServiceImpl implements CheckUploadWetherTimeoutService {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int performExecute() throws ServiceBizException {
		try{
			//查出逾期的的数据
			System.out.println("******hello********");
			List<Map> list=this.QueryOverTime();
			System.out.println("hello********"+list.size());
			if(list!=null && list.size()>0){
				int i;
				VehiclePO po = null;
				VehiclePO tv2 = null;
				//将满足条件的数据置为逾期
				for(i=0;i<list.size();i++){
				    po = (VehiclePO)list.get(i);
				    tv2 = new VehiclePO();
				    tv2=VehiclePO.findFirst("select *  from  tm_vehicle where vin=?", new Object[]{po.getString("VIN")});
				    tv2.setDate("IS_OVER_TIME",12781001);
				    tv2.saveIt();
				}
				
			}
			return 1;
		}catch(Exception e){
			return 0;
		}
		
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

	@SuppressWarnings("rawtypes")
	@Override
	public List QueryOverTime() throws ServiceBizException, ParseException {
		System.out.println("111112222222");
		StringBuffer sql =new StringBuffer();
		sql.append(" select tv.vin from tm_potential_customer tm inner join tt_sales_order tt on tm.dealer_code=tt.dealer_code and  ");
		sql.append(" tm.customer_no = tt.customer_no inner join tm_vehicle tv on tt.dealer_code= tv.dealer_code and tt.vin=tv.vin ");
		sql.append(" where  tm.ob_is_success=70031002 and tm.IS_SECOND_TIME=12781002 and tt.confirmed_date is not null and  ");
		sql.append(" DATEDIFF("+getCurrentDateTime()+",tt.confirmed_date) >10 and (tt.so_status=13011030 OR tt.so_status=13011035 OR tt.so_status=13011075) ");
		sql.append(" and tv.IS_OVERTIME=12781002 ");
		List<Map> rList = Base.findAll(sql.toString());
		System.out.println("sql:"+rList.size());
		
        return rList;
		
	
	}

}
