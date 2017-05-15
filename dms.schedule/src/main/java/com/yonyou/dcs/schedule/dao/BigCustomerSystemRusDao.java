package com.yonyou.dcs.schedule.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmHolidayPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class BigCustomerSystemRusDao extends OemBaseDAO {
	/**
	 * 查询状态驳回的数据
	 * 
	 * @return
	 */
	public List<Map> systemAutoRus() {
		// List<Object> params = new ArrayList<Object>();
		StringBuffer pasql = new StringBuffer(" SELECT ");
		pasql.append("  a.BIG_CUSTOMER_CODE,DATE_FORMAT(a.REBATE_APPROVAL_DATE,'%Y-m%-d%') approval_date ");
		pasql.append(" , DATE_FORMAT(now(),'%Y-m%-d%') system_date,dealer_code,ws_no ");
		pasql.append(" ,a.REBATE_APPROVAL_USER_ID  ");
		pasql.append(" from Tt_Big_Customer_Rebate_Approval a where  a.REBATE_APPROVAL_STATUS = "
				+ OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER + " ");
		pasql.append(" AND DATE_FORMAT(a.REBATE_APPROVAL_DATE,'%Y-m%-d%') = DATE_FORMAT(a.UPDATE_DATE,'%Y-m%-d%') ");
		// params.add(OemDictCodeConstants.BIG_CUSTOMER_REBATE_APPROVAL_TYPE_OVER);
		// //系统驳回

		// List<Map<String,Object>> ps = factory.select(pasql.toString(),
		// params,
		// new DAOCallback<Map<String, Object>>() {
		// public Map<String, Object> wrapper(ResultSet rs, int idx) {
		// Map<String, Object> map = new HashMap<String, Object>();
		// try {
		// ResultSetMetaData md = rs.getMetaData();
		// for (int i = 1; i <= md.getColumnCount(); i++) {
		// map.put(md.getColumnName(i).toUpperCase(),
		// rs.getObject(md.getColumnName(i)));
		// }
		// } catch (Throwable t) {
		// throw new DAOException("DAOException:", t);
		// }
		// return map;
		// }
		// });
		List<Map> list = OemDAOUtil.findAll(pasql.toString(), null);
		return list;
	}

	public int isWorkDay(LazyList<TmHolidayPO> list, String dateStr) {
		Calendar c = Calendar.getInstance();
		Date dealBookDate = null;
		Date now = c.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int n = 0;
		try {
			if (dateStr == null || dateStr.equals("")) {
				dateStr = df2.format(c.getTime());
			}
			c.setTime(df.parse(dateStr));
			dealBookDate = df.parse(dateStr);
			// 1.除去周六日
			Long nowDay = (df.parse(df.format(now)).getTime() - dealBookDate.getTime()) / (1000 * 60 * 60 * 24);
			// logger.info("开始日期：" + dateStr + ",结束日期为："+ df.format(now)
			// +"日期相隔天数:"+nowDay);
			if (nowDay < 0) {
				return 0;
			}
			Date[] days = new Date[nowDay.intValue()];
			List<Date> hList = new ArrayList<Date>();
			if (c.getTime().before(df.parse(df.format(new Date())))) {
				while (true) {
					if (c.get(c.DAY_OF_WEEK) - 1 > 0 && c.get(c.DAY_OF_WEEK) - 1 < 6) {
						days[n++] = c.getTime();
					} else {
						hList.add(c.getTime());
					}
					c.add(Calendar.DATE, 1);
					if (c.getTime().compareTo(df.parse(df.format(now))) == 0)
						break;
				}
			}
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			List<Date> aList = new ArrayList<Date>();
			if (list.size() > 0) {
				for (int m = 0; m < list.size(); m++) {
					TmHolidayPO tPO = list.get(m);
					if (tPO.get("Adjust_Date") != null) {
						aList.add(df1.parse(tPO.get("Adjust_Date").toString()));
					}
					if (tPO.get("Adjust_Date2") != null) {
						aList.add(df1.parse(tPO.get("Adjust_Date2").toString()));
					}
				}
			}
			// 2.除去节假日
			if (list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					TmHolidayPO th = list.get(j);
					for (int k = 0; k < days.length; k++) {
						if (days[k] == null)
							continue;
						if (days[k].compareTo(df1.parse(th.get("Start_Date").toString())) >= 0
								&& days[k].compareTo(df1.parse(th.get("End_Date").toString())) <= 0) {
							n--;
						}
					}
				}
			}
			// 3.加上调班日
			for (int j = 0; j < hList.size(); j++) {
				for (int k = 0; k < aList.size(); k++) {
					if (aList.get(k).compareTo(hList.get(j)) == 0) {
						n++;
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return n;
	}

}
