
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : StatisticalDataServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月23日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月23日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 首页统计
 * 
 * @author zhanshiwei
 * @date 2016年9月23日
 */
@Service
public class StatisticalDataServiceImpl implements StatisticalDataService {

    /**
     * 统计投诉未处理个数
     * 
     * @author zhanshiwei
     * @date 2016年9月21日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CusComplaintService#queryComplainCounts()
     */

    @Override
    public Map<String, Object> queryComplainCounts() throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select  COUNT(COMPLAINT_ID) as complainNum,DEALER_CODE  from TT_CUSTOMER_COMPLAINT  where DEAL_STATUS not in(?) and COMPLAINT_SERIOUS=? GROUP BY DEALER_CODE ");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.COMPLAINT_DEAL_STATUS_04);
        params.add(DictCodeConstants.COMPLAINT_SERIOUS_01);
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("complainNum", "0");
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                resultMap.put("complainNum", row.get("complainNum") == null ? 0 : row.get("complainNum"));
            }
        });
        return resultMap;
    }

    /**
     * @author zhanshiwei
     * @date 2016年10月11日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.StatisticalDataService#queryCurrentMonthSales()
     */

    @Override
    public Map<String, Object> queryCurrentMonthSales() throws ServiceBizException {

        StringBuffer sb = new StringBuffer("select COUNT(DISTINCT sie.SO_INVOICE_ID)-COUNT(DISTINCT sto.VS_STOCK_ID) as saleNum,sie.DEALER_CODE\n");
        sb.append("from TT_SALES_ORDER sale\n");
        sb.append("INNER JOIN TT_SO_INVOICE sie on sale.SO_NO_ID=sie.SO_NO_ID  and  sie.INVOICE_CHARGE_TYPE=").append(DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR).append("\n");
        sb.append("LEFT JOIN TM_VS_STOCK sto  on sie.DEALER_CODE=sto.DEALER_CODE and  sto.ENTRY_TYPE=").append(DictCodeConstants.ENTRY_TYPE_SENDBACK).append("\n");
        sb.append("and  sto.LATEST_STOCK_IN_DATE>=?\n");
        sb.append("and  sto.LATEST_STOCK_IN_DATE<?\n");
        sb.append("where\n");
        sb.append("1=1\n");
        sb.append("and  sale.SHEET_CREATE_DATE>=?\n");
        sb.append("and  sale.SHEET_CREATE_DATE<?\n");
        sb.append("GROUP BY sale.DEALER_CODE\n");

        List<Object> params = new ArrayList<Object>();
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("saleNum", "0");
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
            @Override
            protected void process(Map<String, Object> row) {
                resultMap.put("saleNum", row.get("saleNum") == null ? 0 : row.get("saleNum"));
            }
        });
        return resultMap;
    }

    
	/**3.销售订单转化率
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, Object> queryConversionRate() throws ServiceBizException {
		StringBuffer sb = new StringBuffer("select sale.DEALER_CODE\n")
		.append("       ,(count(DISTINCT sale.SO_NO_ID)/count(DISTINCT potcus.POTENTIAL_CUSTOMER_ID)) as conversionRate\n")
		.append("from TT_SALES_ORDER sale,TM_POTENTIAL_CUSTOMER potcus\n")
		.append("where sale.DEALER_CODE =potcus.DEALER_CODE\n")
		.append("and sale.SO_STATUS not in (").append(DictCodeConstants.ORDER_CANCEL).append(",").append(DictCodeConstants.ORDER_BACK).append(")\n")
		.append("and sale.SHEET_CREATE_DATE>=?\n")
		.append("and sale.SHEET_CREATE_DATE<?\n")
		.append("and potcus.FOUND_DATE>=?\n")
		.append("and potcus.FOUND_DATE<?\n")
		.append("and potcus.INTENT_LEVEL NOT in (").append(DictCodeConstants.F_LEVEL).append(")\n")
		.append("GROUP BY sale.DEALER_CODE\n");

		List<Object> params = new ArrayList<Object>();
		params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
		final Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("conversionRate", "0");
		DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
			@Override
			protected void process(Map<String, Object> row) {
					resultMap.put("conversionRate", row.get("conversionRate") == null ? 0 : new DecimalFormat("0.#").format(NumberUtil.mul((BigDecimal) row.get("conversionRate"),BigDecimal.valueOf(100))));
			}
		});
		return resultMap;
	}
    /**
     * 首页统计
     * 
     * @author zhanshiwei
     * @date 2016年9月26日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.StatisticalDataService#StatisticalData()
     */

    @Override
    public Map<String, Object> StatisticalData() throws ServiceBizException {
        Map<String, Object> complainNumMap = queryComplainCounts();
        Map<String, Object> saleNumMap = queryCurrentMonthSales();
        Map<String, Object> repairNumMap = queryCurrentMonthRepairs();
        Map<String, Object> conversionRateMap=queryConversionRate();
        complainNumMap.putAll(saleNumMap);
        complainNumMap.putAll(repairNumMap);
        complainNumMap.putAll(conversionRateMap);
        complainNumMap.put("saleStai", querySaleStatus());
        return complainNumMap;
    }

    /**
     * 进厂维修
     * 
     * @author zhanshiwei
     * @date 2016年9月26日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.StatisticalDataService#queryCurrentMonthRepairs()
     */

    @Override
    public Map<String, Object> queryCurrentMonthRepairs() throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select  t1.RO_ID,DEALER_CODE\n").append(",count(distinct t1.VEHICLE_ID,date_format(t1.RO_CREATE_DATE,'%Y-%c-%d'),t1.DEALER_CODE) as repairNum\n").append("from TT_REPAIR_ORDER t1\n").append("where t1.RO_CREATE_DATE>=?\n").append("and t1.RO_CREATE_DATE<?\n").append("and t1.REPAIR_TYPE_CODE not in ('1004') GROUP BY t1.DEALER_CODE\n");
        List<Object> params = new ArrayList<Object>();
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("repairNum", "0");
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                resultMap.put("repairNum", row.get("repairNum") == null ? 0 : row.get("repairNum"));
            }
        });
        return resultMap;
    }

    
    /**
    * 销售统计
    * @author zhanshiwei
    * @date 2016年10月11日
    * @return
    * @throws ServiceBizException
    */
    	
    public Map<String, List<Map<String, Object>>> querySaleStatus() throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("select sale.SO_NO_ID,sale.DEALER_CODE,sale.CONSULTANT,\n");
        sb.append("       br.BRAND_CODE,\n");
        sb.append("       em.EMPLOYEE_NAME,\n");
        sb.append("       sale.SHEET_CREATE_DATE,\n");
        sb.append("       br.BRAND_NAME,\n");
        sb.append("       COUNT(*) as SAL_NUM\n");
        sb.append(" from TT_SALES_ORDER sale\n");
        sb.append(" INNER JOIN  TT_SO_INVOICE sie on sale.SO_NO_ID=sie.SO_NO_ID  and  sie.INVOICE_CHARGE_TYPE=").append(DictCodeConstants.INVOICE_CHARGE_TYPE_BUYCAR).append("\n");
        sb.append(" INNER join  TM_VS_STOCK  sto  on sale.VS_STOCK_ID=sto.VS_STOCK_ID and sie.DEALER_CODE=sto.DEALER_CODE\n");
        sb.append(" left join   TM_EMPLOYEE  em   on sale.CONSULTANT=em.EMPLOYEE_NO and sale.DEALER_CODE=em.DEALER_CODE\n");
        sb.append(" left join   TM_BRAND     br   on sto.BRAND_CODE=br.BRAND_CODE and sale.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" where  sale.SHEET_CREATE_DATE>=?\n");
        sb.append(" and    sale.SHEET_CREATE_DATE<?\n");
        sb.append(" GROUP BY sale.CONSULTANT,br.BRAND_ID,sale.DEALER_CODE\n");
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        final Map<String, List<Map<String, Object>>> resultMap = new HashMap<String, List<Map<String, Object>>>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
            @Override
            protected void process(Map<String, Object> row) {
                List<Map<String, Object>> listMap = null;
                String consultant=row.get("CONSULTANT")==null?"未知":row.get("CONSULTANT").toString();
                if(resultMap.get(consultant)!=null){
                    listMap = resultMap.get(consultant);
                }else{
                    listMap = new ArrayList<Map<String, Object>>();
                    resultMap.put(consultant, listMap);
                }
                listMap.add(row);
            }
        });
        return resultMap;
    }

    
    /**
     * 售后维修统计
    * @author zhanshiwei
    * @date 2016年11月24日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.report.service.impl.StatisticalDataService#queryRepairs()
    */
    	
    @Override
    public List<List<Object>> queryRepairs() throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();

        StringBuffer sb = new StringBuffer("select  t2.DEALER_CODE,t2.RO_CREATE_DATE as _data,t2._numer from (select\n")
        .append("  t1.DEALER_CODE,date_format(t1.RO_CREATE_DATE,'%Y-%c-%d') as RO_CREATE_DATE\n")
        .append(" ,t1.VEHICLE_ID,t1.RO_NO\n")
        .append(" , COUNT(1) as _numer\n")
        .append("from TT_REPAIR_ORDER t1\n")
        .append("where\n")
        .append("      t1.RO_CREATE_DATE>=?\n")
        .append("and   t1.RO_CREATE_DATE<?\n")
        .append(" GROUP BY t1.DEALER_CODE,date_format(t1.RO_CREATE_DATE,'%Y-%c-%d'),t1.VEHICLE_ID) t2 GROUP BY t2.DEALER_CODE,date_format(t2.RO_CREATE_DATE,'%Y-%c-%d') \n");
        params.add(DateUtil.getFirstDayOfMonth(new Date()));
        params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        final List<List<Object>> resullt=new ArrayList<List<Object>>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
            @Override
            protected void process(Map<String, Object> row) {
                setDMSRepairGraph(resullt, row);
            }
        });
        setEomday(resullt);
        return resullt;
    }
    
    /**
    * 
    * @author zhanshiwei
    * @date 2016年11月25日
    * @param row
    * @return
    */
    	
    public void setDMSRepairGraph(List<List<Object>> resullt, Map<String, Object> row) {
        Date firstData=getBeginDate(resullt, row);
        Date endData=getEndDate(resullt, firstData);
        List<Object> _date= new ArrayList<Object>();
        if(endData.getTime()>DateUtil.parseDefaultDate(row.get("_data").toString()).getTime()){
            if(resullt.size()>0&&StringUtils.isEquals(resullt.get(resullt.size()-1).get(0), DateUtil.parseMonth(firstData))){
                _date=resullt.get(resullt.size());
                _date.add(Integer.parseInt(_date.get(0).toString())+Integer.parseInt(row.get("_numer").toString()));
                resullt.add(_date);
            }else{
                _date.add(DateUtil.parseMonth(firstData));
                _date.add(Integer.parseInt(row.get("_numer").toString()));  
                resullt.add(_date);
            }
        }else{
            _date.add(DateUtil.parseMonth(firstData));
            _date.add(0); 
            resullt.add(_date);
            this.setDMSRepairGraph(resullt, row);
        }
    }
    
    public Date getBeginDate(List<List<Object>> _data,Map<String, Object> row){
        Date firstData = DateUtil.getFirstDayOfMonth(new Date());
        if(_data.size()>0&&firstData.getTime()<DateUtil.parseDefaultDate(row.get("_data").toString()).getTime()){
            return firstData= DateUtil.addDay(firstData, CommonConstants.INTERVAL_DAY*_data.size());
        }else{
            return  firstData ;
        }
    }
    public Date getEndDate(List<List<Object>> _data,Date firstData){
            return DateUtil.addDay(firstData, CommonConstants.INTERVAL_DAY);   
    }
    
    public void setEomday(List<List<Object>> resullt){
        Date eomdayData = DateUtil.getLastDayOfMonth(new Date());
        List<Object> _date= new ArrayList<Object>();
        _date.add(DateUtil.parseMonth(eomdayData));
        _date.add(0);
        resullt.add(_date);
    }

    @Override
    public List<Map> queryRepairCon() throws ServiceBizException {
             List<Object> params = new ArrayList<Object>();
                StringBuffer sb = new StringBuffer("select  t1.RO_ID,DEALER_CODE\n")
                .append(",count(distinct t1.VEHICLE_ID,date_format(t1.RO_CREATE_DATE,'%Y-%c-%d'),t1.DEALER_CODE) as car_num\n")
                .append(",count(distinct t1.RO_ID) as cars_num\n")
                .append(",sum(DIS_AMOUNT)/10000 as DIS_AMOUNT\n")
                .append("from TT_REPAIR_ORDER t1\n")
                .append("where t1.RO_CREATE_DATE>=?\n")
                .append("and t1.RO_CREATE_DATE<?\n")
                .append("GROUP BY t1.DEALER_CODE\n");

            params.add(DateUtil.getFirstDayOfMonth(new Date()));
            params.add(DateUtil.getPerFirstDayOfMonth(new Date()));
        return   DAOUtil.findAll(sb.toString(), params);
    }
}
