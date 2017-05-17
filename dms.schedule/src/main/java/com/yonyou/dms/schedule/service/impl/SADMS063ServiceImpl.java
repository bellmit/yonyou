package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS063Cloud;
import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;


@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SADMS063ServiceImpl implements SADMS063Service{
    @Autowired
    SADCS063Cloud SADCS063Cloud;

    @Override
    public List<Map> querySubmitReportRemain(String beginDate, String endDate) {
        // TODO Auto-generated method stub                    
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT series_code, BRAND_CODE, SO_STATUS,dealer_code, SUM(SALES_LCREPLACE) SALES_LCREPLACE FROM ");
        sql.append(" (SELECT p.series_code series_code, p.BRAND_CODE BRAND_CODE,S.SO_STATUS SO_STATUS,p.dealer_code dealer_code, ");
        sql.append(" COUNT(1) AS SALES_LCREPLACE  FROM tt_sales_order s  INNER JOIN tm_vs_product p ON p.dealer_code = s.dealer_code ");
        sql.append(" AND s.product_code = p.product_code WHERE s.dealer_code = p.dealer_code AND p.oem_tag = 12781001 AND p.is_valid = 12781001 AND ( ");
        sql.append(" s.so_status = 13011010 OR s.SO_STATUS = 13011015  OR s.SO_STATUS = 13011020 OR s.SO_STATUS = 13011025) GROUP BY p.series_code, ");
        sql.append(" p.BRAND_CODE,S.SO_STATUS) SE WHERE series_code IN  (SELECT series_code FROM TM_SERIES WHERE dealer_code = SE.dealer_code  AND oem_tag = 12781001  ");
        sql.append(" AND is_valid = 12781001) AND BRAND_CODE IN (SELECT BRAND_CODE  FROM TM_BRAND WHERE dealer_code = SE.dealer_code  AND oem_tag = 12781001  AND is_valid = 12781001)  ");
        sql.append(" AND so_status IN (SELECT  so_status FROM  tt_sales_order  WHERE SO_STATUS = 13011010  OR SO_STATUS = 13011015   OR SO_STATUS = 13011020  OR SO_STATUS = 13011025)  ");
        sql.append(" GROUP BY series_code, BRAND_CODE,  SO_STATUS,dealer_code  ");
        List<Object> queryList = new  ArrayList<Object>();
        List<Map> result = Base.findAll(sql.toString());
        return result;
    }
    //获取原接口SADMS063所上报的数据
    @Override
    public LinkedList<SADMS063Dto> getSADMS063() {
        try {

            Date date = new Date();
            Calendar calm = Calendar.getInstance();
            calm.setTime(date);
            String endDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + " 00:00:00";
            calm.add(Calendar.DATE,-60);
            String beginDate=new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+ " 00:00:00";
            String dealerCode=null;
            String seriesCode=null;// 车系代码
            String brandCode=null;
            int salesLcreplace=0;
            int soStatus=0;
            LinkedList<SADMS063Dto> resultList = new LinkedList<SADMS063Dto>();
            List<Map> listSeries = new ArrayList<Map>();
            listSeries =this.querySubmitReportRemain(beginDate, endDate);
            if(listSeries!=null && listSeries.size()>0){
                for(int i = 0; i < listSeries.size(); i++){
                    Map  bean =  listSeries.get(i);
                    dealerCode = (String)bean.get("dealer_code");
                    seriesCode = (String)bean.get("SERIES_CODE");
                    brandCode =(String)bean.get("BRAND_CODE");  
                    salesLcreplace =Integer.parseInt(bean.get("SALES_LCREPLACE").toString());
                    soStatus =Integer.parseInt(bean.get("SO_STATUS").toString());
                    SADMS063Dto dto = new SADMS063Dto();
                    dto.setDealerCode(dealerCode);
                    dto.setSeriesCode(seriesCode);
                    dto.setBrandCode(brandCode);   
                    dto.setSoStatus(soStatus);
                    dto.setSubmitTime(new Date());
                    dto.setSalesLcreplace(salesLcreplace);          
                    resultList.add(dto);
                }   
            }
            SADCS063Cloud.handleExecutor(resultList);
            return resultList;
        
        } catch (Exception e) {
            return null;
        }
    }

}
