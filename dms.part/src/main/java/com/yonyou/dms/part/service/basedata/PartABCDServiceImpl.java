
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartABCDServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月17日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO 配件ABCD分析
* @author dingchaoyu
* @date 2017年4月17日
*/
@Service
public class PartABCDServiceImpl implements PartABCDService{
    
    /**
     * 查询
    * @author dingchaoyu
    * @date 2017年4月18日
    * @param map
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartABCDService#findAll(java.util.Map)
     */
    @SuppressWarnings("static-access")
    @Override
    public PageInfoDto findAll(Map map) throws ServiceBizException {
        
        float sumPartCount = 0F;
        float sumpartAmount = 0F;
        String partARate = null;
        String partBRate = null;
        String partCRate = null;
        String partDRate = null;
        String beginMonth = "201003";
        String s = map.get("LAST_MONTH").toString();
        if (map.get("LAST_MONTH").toString().equals("90091001")) {
            s="1";
        }else if (map.get("LAST_MONTH").toString().equals("90091002")) {
            s="2";
        }else if (map.get("LAST_MONTH").toString().equals("90091003")) {
            s="3";
        }else if (map.get("LAST_MONTH").toString().equals("90091004")) {
            s="4";
        }else if (map.get("LAST_MONTH").toString().equals("90091005")) {
            s="5";
        }else if (map.get("LAST_MONTH").toString().equals("90091006")) {
            s="6";
        }else if (map.get("LAST_MONTH").toString().equals("90091007")) {
            s="7";
        }else if (map.get("LAST_MONTH").toString().equals("90091008")) {
            s="8";
        }else if (map.get("LAST_MONTH").toString().equals("90091009")) {
            s="9";
        }else if (map.get("LAST_MONTH").toString().equals("900910010")) {
            s="10";
        }else if (map.get("LAST_MONTH").toString().equals("900910011")) {
            s="11";
        }else if (map.get("LAST_MONTH").toString().equals("900910012")) {
            s="12";
        }
        int minusMonth = 3;
        minusMonth = Integer.parseInt(s);
        minusMonth = -minusMonth + 1;
        java.util.Calendar df = java.util.Calendar.getInstance();
        df.setTime(new Date());
        
        createTempTable(map.get("LAST_MONTH").toString(), String.valueOf(df.get(java.util.Calendar.YEAR)));// 创建临时表
        
        df.add(df.MONTH, minusMonth);
        if (df.get(java.util.Calendar.MONTH) < 10)
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + "0" + String.valueOf(df.get(java.util.Calendar.MONTH));
        } else
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + String.valueOf(df.get(java.util.Calendar.MONTH));
        }
        
        if (StringUtils.isNullOrEmpty(map.get("PROPERTY_A_RATE"))) {
            partARate ="60";
            partBRate ="20";
            partCRate ="15";
            partDRate ="5";
        }else{
            partARate = map.get("PROPERTY_A_RATE").toString();
            partARate = partARate.substring(0,partARate.length()-1);
            partBRate = map.get("PROPERTY_B_RATE").toString();
            partBRate = partBRate.substring(0,partBRate.length()-1);
            partCRate = map.get("PROPERTY_C_RATE").toString();
            partCRate = partCRate.substring(0,partCRate.length()-1);
            partDRate = map.get("PROPERTY_D_RATE").toString();
            partDRate = partDRate.substring(0,partDRate.length()-1);
        }
        df.setTime(new Date());
        String abcYear = String.valueOf(df.get(java.util.Calendar.YEAR));
        String abcMonth = String.valueOf(df.get(java.util.Calendar.MONTH) + 1);
        if (abcMonth.length() < 2)
        {
            abcMonth = "0" + abcMonth;
        }
        
        List list = getSumPartAmountAndCount(beginMonth);
        
        if (list != null && list.size() > 0) {
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT")).equals("")) {
                sumPartCount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))); // 获取总数量
            }
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT")).equals("")) {
                sumpartAmount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))); // 获取总金额  
            }
        }
        partBRate = String.valueOf((Double.valueOf(partARate) + Double.valueOf(partBRate)));
        partCRate = String.valueOf((Double.valueOf(partBRate) + Double.valueOf(partCRate)));

        partARate = String.valueOf(Double.valueOf(partARate) / 100);
        partBRate = String.valueOf(Double.valueOf(partBRate) / 100);
        partCRate = String.valueOf(Double.valueOf(partCRate) / 100);
        
        inster(abcYear,abcMonth,beginMonth);
        List<Object> a = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT 12781002 AS IS_SELECTED,AAA.DEALER_CODE,AAA.ROW_NO,PART_NO,PART_NAME,PART_MODEL_GROUP_CODE_SET");
        sb.append(" ,ABC_TYPE,SET_ABC_TYPE,STOCK_QUANTITY,UNIT_CODE,SALES_PRICE,CLAIM_PRICE,COST_PRICE,MAX_STOCK,MIN_STOCK,");
        sb.append(" PART_OUT_COUNT,PART_OUT_AMOUNT,ADD_PART_AMOUNT_VIEW AS ADD_PART_AMOUNT,AAA.PART_AMOUNT_RATE AS ADD_PART_AMOUNT_RATE,");
        sb.append(" CASE when AAA.PART_AMOUNT_RATE < "+partARate+" then 13911005 when ((AAA.PART_AMOUNT_RATE >= "+partARate+") AND ");
        sb.append(" (AAA.PART_AMOUNT_RATE < "+partBRate+")) then 13911010 when ((AAA.PART_AMOUNT_RATE >="+partBRate+") AND ");
        sb.append(" (AAA.PART_AMOUNT_RATE < "+partCRate+")) then 13911015 ELSE 13911020 END AS CALC_ABC_TYPE FROM");
        sb.append(" ( select AA.*, CASE WHEN "+sumpartAmount+"= 0.0 THEN 0.0 ELSE (AA.ADD_PART_AMOUNT_VIEW / "+sumpartAmount+")");
        sb.append(" END AS PART_AMOUNT_RATE from ( select A.*,(select SUM(PART_OUT_AMOUNT) FROM TT_PART_ABCD_TEMP B WHERE B.ROW_NO <= A.ROW_NO)");
        sb.append(" AS ADD_PART_AMOUNT_VIEW from TT_PART_ABCD_TEMP A ) AA ) AAA where 1=1 ");
        if(!StringUtils.isNullOrEmpty(map.get("PART_NO"))){
            sb.append(" and AAA.PART_NO = ?");
            a.add(Integer.parseInt(map.get("PART_NO").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("PART_NAME"))){
            sb.append(" and AAA.PART_NAME like ?");
            a.add(Integer.parseInt("%"+map.get("PART_NAME").toString())+"%");
        }
        if(!StringUtils.isNullOrEmpty(map.get("PART_OUT_AMOUNT"))){
            sb.append(" and AAA.PART_OUT_AMOUNT = ?");
            a.add(Integer.parseInt(map.get("PART_OUT_AMOUNT").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("PART_NO"))){
            sb.append(" and AAA.PART_NO = ?");
            a.add(Integer.parseInt(map.get("PART_NO").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("SALE_BEGIN"))&&!StringUtils.isNullOrEmpty(map.get("SALE_END"))){
            sb.append(" and AAA.PART_OUT_AMOUNT >=  ? and AAA.PART_OUT_AMOUNT <= ");
            a.add(Integer.parseInt(map.get("SALE_BEGIN").toString()));
            a.add(Integer.parseInt(map.get("SALE_END").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("PRICE_BEGIN"))&&!StringUtils.isNullOrEmpty(map.get("PRICE_END"))){
            sb.append(" and AAA.SALES_PRICE >= ？ and AAA.SALES_PRICE <= ？");
            a.add(Integer.parseInt(map.get("PRICE_BEGIN").toString()));
            a.add(Integer.parseInt(map.get("PRICE_END").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("AMOUNT_BEGIN"))&&!StringUtils.isNullOrEmpty(map.get("AMOUNT_END"))){
            sb.append(" and AAA.PART_OUT_COUNT >= ? and AAA.PART_OUT_COUNT <= ?");
            a.add(Integer.parseInt(map.get("AMOUNT_BEGIN").toString()));
            a.add(Integer.parseInt(map.get("AMOUNT_END").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("ABC_TYPE"))){
            sb.append(" and AAA.ABC_TYPE = ?");
            a.add(Integer.parseInt(map.get("ABC_TYPE").toString()));
        }
        if(!StringUtils.isNullOrEmpty(map.get("partModelGroupCodeSet"))){
            sb.append(" and AAA.PART_MODEL_GROUP_CODE_SET = ?");
            a.add(Integer.parseInt(map.get("partModelGroupCodeSet").toString()));
        }
        
        return DAOUtil.pageQuery(sb.toString(), a);
    }
    
    /**
     * 单项修改
    * @author dingchaoyu
    * @date 2017年4月18日
    * @param id
    * @param map
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartABCDService#findByid(java.lang.String, java.util.Map)
     */
    @Override
    public void findByid(String id) throws ServiceBizException {
        
        String updatesql = "select DEALERT_CODE,ROW_NO,PART_NO,SET_ABC_TYPE from TT_PART_ABCD_TEMP order by row_NO ASC ";
        List<Map> lists = DAOUtil.findAll(updatesql, null);
        List<Object> update = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++)
        {
            int tPartProperty = Integer.parseInt(lists.get(i).get("SET_ABC_TYPE").toString());
            update.add(tPartProperty);
            update.add(id);
            update.add(CommonConstants.D_KEY);
            String s = "UPDATE tm_part_info SET ABC_TYPE = ? WHERE part_no=? and D_KEY=?";
            DAOUtil.findAll(s, update);
        }
        
        
    }
    
    /**
     * 
    * TODO 批量更新
    * @author dingchaoyu
    * @date 2017年4月18日
     */
    @Override
    public void updateAll(Map map)  throws ServiceBizException {
        String tPartNo;
        float sumPartCount = 0F;
        float sumpartAmount = 0F;
        String partARate = null;
        String partBRate = null;
        String partCRate = null;
        String partDRate = null;
        int tPartProperty;
        
        String beginMonth = "201003";
        String s = map.get("LAST_MONTH").toString();
        if (map.get("LAST_MONTH").toString().equals("90091001")) {
            s="1";
        }else if (map.get("LAST_MONTH").toString().equals("90091002")) {
            s="2";
        }else if (map.get("LAST_MONTH").toString().equals("90091003")) {
            s="3";
        }else if (map.get("LAST_MONTH").toString().equals("90091004")) {
            s="4";
        }else if (map.get("LAST_MONTH").toString().equals("90091005")) {
            s="5";
        }else if (map.get("LAST_MONTH").toString().equals("90091006")) {
            s="6";
        }else if (map.get("LAST_MONTH").toString().equals("90091007")) {
            s="7";
        }else if (map.get("LAST_MONTH").toString().equals("90091008")) {
            s="8";
        }else if (map.get("LAST_MONTH").toString().equals("90091009")) {
            s="9";
        }else if (map.get("LAST_MONTH").toString().equals("900910010")) {
            s="10";
        }else if (map.get("LAST_MONTH").toString().equals("900910011")) {
            s="11";
        }else if (map.get("LAST_MONTH").toString().equals("900910012")) {
            s="12";
        }
        int minusMonth = 3;
        minusMonth = Integer.parseInt(s);
        minusMonth = -minusMonth + 1;
        java.util.Calendar df = java.util.Calendar.getInstance();
        df.setTime(new Date());
        
        createTempTable(map.get("LAST_MONTH").toString(), String.valueOf(df.get(java.util.Calendar.YEAR)));// 创建临时表
        
        df.add(df.MONTH, minusMonth);
        if (df.get(java.util.Calendar.MONTH) < 10)
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + "0" + String.valueOf(df.get(java.util.Calendar.MONTH));
        } else
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + String.valueOf(df.get(java.util.Calendar.MONTH));
        }
        if (StringUtils.isNullOrEmpty(map.get("PROPERTY_A_RATE"))) {
            partARate ="60";
            partBRate ="20";
            partCRate ="15";
            partDRate ="5";
        }else{
            partARate = map.get("PROPERTY_A_RATE").toString();
            partBRate = map.get("PROPERTY_B_RATE").toString();
            partCRate = map.get("PROPERTY_C_RATE").toString();
            partDRate = map.get("PROPERTY_D_RATE").toString();
        }
        partBRate = String.valueOf((Double.valueOf(partARate) + Double.valueOf(partBRate)));
        partCRate = String.valueOf((Double.valueOf(partBRate) + Double.valueOf(partCRate)));

        partARate = String.valueOf(Double.valueOf(partARate) / 100);
        partBRate = String.valueOf(Double.valueOf(partBRate) / 100);
        partCRate = String.valueOf(Double.valueOf(partCRate) / 100);
        List list = getSumPartAmountAndCount(beginMonth);
        
        if (list != null && list.size() > 0) {
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT")).equals("")) {
                sumPartCount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))); // 获取总数量
            }
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT")).equals("")) {
                sumpartAmount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))); // 获取总金额  
            }
        }
        String updatesql = " update TT_PART_ABCD_TEMP A set ADD_PART_AMOUNT = (select SUM(PART_OUT_AMOUNT) FROM TT_PART_ABCD_TEMP B WHERE B.ROW_NO <= A.ROW_NO)";
        DAOUtil.findAll(updatesql, null);
        
        updatesql = " update TT_PART_ABCD_TEMP A set ADD_PART_AMOUNT_RATE = ADD_PART_AMOUNT / " + sumpartAmount;
        DAOUtil.findAll(updatesql, null);
        
        updatesql = "update TT_PART_ABCD_TEMP AA set AA.SET_ABC_TYPE = " + "CASE when AA.ADD_PART_AMOUNT_RATE  <" + partARate
                + " then 13911005 " + "when ((AA.ADD_PART_AMOUNT_RATE >= " + partARate + ") AND (AA.ADD_PART_AMOUNT_RATE < " + partBRate
                + ")) then 13911010 " + "when ((AA.ADD_PART_AMOUNT_RATE >= " + partBRate + ") AND (AA.ADD_PART_AMOUNT_RATE < "
                + partCRate + ")) then 13911015 " + " ELSE 13911020 end where AA.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'";
        DAOUtil.findAll(updatesql, null);
        
        updatesql = "select DEALERT_CODE,ROW_NO,PART_NO,SET_ABC_TYPE from TT_PART_ABCD_TEMP order by row_NO ASC ";
        List<Map> lists = DAOUtil.findAll(updatesql, null);
        List<Object> update = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++)
        {
            tPartNo = lists.get(i).get("PART_NO").toString();
            tPartProperty = Integer.parseInt(lists.get(i).get("SET_ABC_TYPE").toString());
            update.add(tPartProperty);
            update.add(tPartNo);
            if (!StringUtils.isNullOrEmpty(tPartNo)){
                TmPartInfoPO.update("ABC_TYPE","part_no", update);
            }
        }
    }
    
    private void createTempTable(String ABCMonth, String ABCYear){
        String s = "SELECT * FROM INFORMATION_SCHEMA.TABLES where table_name = 'TT_PART_ABCD_TEMP'";
        List<Map> findAll = Base.findAll(s);
        if (findAll != null && findAll.size() > 0)
        {
            s = "truncate table TT_PART_ABCD_TEMP";
            Base.exec(s);
        }
    }
    
    private List getSumPartAmountAndCount(String beginMonth){
        
        List<Object> list = new ArrayList<>();
        list.add(beginMonth);
        StringBuffer sbsql = new StringBuffer();
        sbsql.append("select A.DEALER_CODE,SUM(Coalesce(A.REPAIR_OUT_COUNT,0)+ Coalesce(A.SALE_OUT_COUNT,0)) AS SumOutCount,");
        sbsql.append(" SUM(Coalesce(A.REPAIR_OUT_SALE_AMOUNT,0)+Coalesce(A.SALE_OUT_SALE_AMOUNT,0)) AS SumOutAmount");
        sbsql.append(" from TT_PART_PERIOD_REPORT A where ");
        sbsql.append(" floor(A.REPORT_YEAR || A.REPORT_MONTH)>= ?");
        List<Map> findAll = DAOUtil.findAll(sbsql.toString(), list);
        return findAll;
    }
    
    private void inster(String abcYear,String abcMonth,String beginMonth){
        StringBuffer sql = new StringBuffer();
        // 插入数据
        sql.append("INSERT INTO TT_PART_ABCD_TEMP(ROW_NO,DEALER_CODE,ABCD_YEAR,ABCD_MONTH,PART_NO,PART_NAME,PART_MODEL_GROUP_CODE_SET,")
           .append("ABC_TYPE,STOCK_QUANTITY,UNIT_CODE,SALES_PRICE,CLAIM_PRICE,COST_PRICE,MAX_STOCK,MIN_STOCK,PART_OUT_COUNT,PART_OUT_AMOUNT)")
           .append("select  @rownum:=@rownum+1 as ROW_NO,'" + FrameworkUtil.getLoginInfo().getDealerCode() + "','" + abcYear + "','" + abcMonth)
           .append("', b.* from ( SELECT C.PART_NO,C.PART_NAME,C.PART_MODEL_GROUP_CODE_SET,C.ABC_TYPE,")
           .append(" B.STOCK_QUANTITY,C.UNIT_CODE,AVG(B.SALES_PRICE) AS SALES_PRICE, ")
           .append(" AVG(B.CLAIM_PRICE) AS CLAIM_PRICE,AVG(COST_PRICE) AS COST_PRICE,SUM(B.MAX_STOCK) AS MAX_STOCK,SUM(B.MIN_STOCK) As MIN_STOCK,")
           .append(" SUM(Coalesce(A.REPAIR_OUT_COUNT,0) + Coalesce(A.SALE_OUT_COUNT,0)) AS PART_OUT_COUNT,")
           .append(" SUM(Coalesce(A.REPAIR_OUT_SALE_AMOUNT,0)+Coalesce(A.SALE_OUT_SALE_AMOUNT,0)) AS PART_OUT_AMOUNT ")
           .append(" from TT_PART_PERIOD_REPORT A LEFT JOIN TM_PART_STOCK B on (A.DEALER_CODE = B.DEALER_CODE AND ")
           .append( " A.PART_NO = B.PART_NO and A.STORAGE_CODE = B.STORAGE_CODE ) ")
           .append(" left join ("+CommonConstants.VM_PART_INFO+") C on (C.DEALER_CODE = B.DEALER_CODE AND C.PART_NO = B.PART_NO ) WHERE 1=1 AND A.DEALER_CODE = '")
           .append( FrameworkUtil.getLoginInfo().getDealerCode() + "'")
           .append(" AND floor(concat(A.REPORT_YEAR,A.REPORT_MONTH)) >= " + beginMonth)
           .append(" group by C.PART_NO,C.PART_NAME,C.UNIT_CODE,C.PART_MODEL_GROUP_CODE_SET,C.ABC_TYPE,B.STOCK_QUANTITY order by PART_OUT_AMOUNT  desc ) b,(select @rownum :=0 , @pdept := null ,@rank:=0) a ");
        Base.exec(sql.toString());
    }

    @Override
    public List<Map> queryPartInfoForExport(Map map) throws ServiceBizException {
        float sumPartCount = 0F;
        float sumpartAmount = 0F;
        String partARate = null;
        String partBRate = null;
        String partCRate = null;
        String partDRate = null;
        String beginMonth = "201003";
        int minusMonth = 3;
        minusMonth = Integer.parseInt(map.get("LAST_MONTH").toString());
        minusMonth = -minusMonth + 1;
        java.util.Calendar df = java.util.Calendar.getInstance();
        df.setTime(new Date());
        
        createTempTable(map.get("LAST_MONTH").toString(), String.valueOf(df.get(java.util.Calendar.YEAR)));// 创建临时表
        
        df.add(df.MONTH, minusMonth);
        if (df.get(java.util.Calendar.MONTH) < 10)
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + "0" + String.valueOf(df.get(java.util.Calendar.MONTH));
        } else
        {
            beginMonth = String.valueOf(df.get(java.util.Calendar.YEAR)) + String.valueOf(df.get(java.util.Calendar.MONTH));
        }
        
        if (StringUtils.isNullOrEmpty(map.get("PROPERTY_A_RATE"))) {
            partARate ="60";
            partBRate ="20";
            partCRate ="15";
            partDRate ="5";
        }else{
            partARate = map.get("PROPERTY_A_RATE").toString();
            partARate = partARate.substring(0,partARate.length()-1);
            partBRate = map.get("PROPERTY_B_RATE").toString();
            partBRate = partBRate.substring(0,partBRate.length()-1);
            partCRate = map.get("PROPERTY_C_RATE").toString();
            partCRate = partCRate.substring(0,partCRate.length()-1);
            partDRate = map.get("PROPERTY_D_RATE").toString();
            partDRate = partDRate.substring(0,partDRate.length()-1);
        }
        df.setTime(new Date());
        String abcYear = String.valueOf(df.get(java.util.Calendar.YEAR));
        String abcMonth = String.valueOf(df.get(java.util.Calendar.MONTH) + 1);
        if (abcMonth.length() < 2)
        {
            abcMonth = "0" + abcMonth;
        }
        
        List list = getSumPartAmountAndCount(beginMonth);
        
        if (list != null && list.size() > 0) {
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT")).equals("")) {
                sumPartCount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTCOUNT"))); // 获取总数量
            }
            if (String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))!=null && !String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT")).equals("")) {
                sumpartAmount = Float.parseFloat(String.valueOf(((Utility) list.get(0)).getDouble("SUMOUTAMOUNT"))); // 获取总金额  
            }
        }
        partBRate = String.valueOf((Double.valueOf(partARate) + Double.valueOf(partBRate)));
        partCRate = String.valueOf((Double.valueOf(partBRate) + Double.valueOf(partCRate)));

        partARate = String.valueOf(Double.valueOf(partARate) / 100);
        partBRate = String.valueOf(Double.valueOf(partBRate) / 100);
        partCRate = String.valueOf(Double.valueOf(partCRate) / 100);
        
        inster(abcYear,abcMonth,beginMonth);
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT 12781002 AS IS_SELECTED,AAA.DEALER_CODE,AAA.ROW_NO,PART_NO,PART_NAME,PART_MODEL_GROUP_CODE_SET");
        sb.append(" ,ABC_TYPE,SET_ABC_TYPE,STOCK_QUANTITY,UNIT_CODE,SALES_PRICE,CLAIM_PRICE,COST_PRICE,MAX_STOCK,MIN_STOCK,");
        sb.append(" PART_OUT_COUNT,PART_OUT_AMOUNT,ADD_PART_AMOUNT_VIEW AS ADD_PART_AMOUNT,AAA.PART_AMOUNT_RATE AS ADD_PART_AMOUNT_RATE,");
        sb.append(" CASE when AAA.PART_AMOUNT_RATE < "+partARate+" then 13911005 when ((AAA.PART_AMOUNT_RATE >= "+partARate+") AND ");
        sb.append(" (AAA.PART_AMOUNT_RATE < "+partBRate+")) then 13911010 when ((AAA.PART_AMOUNT_RATE >="+partBRate+") AND ");
        sb.append(" (AAA.PART_AMOUNT_RATE < "+partCRate+")) then 13911015 ELSE 13911020 END AS CALC_ABC_TYPE FROM");
        sb.append(" ( select AA.*, CASE WHEN "+sumpartAmount+"= 0.0 THEN 0.0 ELSE (AA.ADD_PART_AMOUNT_VIEW / "+sumpartAmount+")");
        sb.append(" END AS PART_AMOUNT_RATE from ( select A.*,(select SUM(PART_OUT_AMOUNT) FROM TT_PART_ABCD_TEMP B WHERE B.ROW_NO <= A.ROW_NO)");
        sb.append(" AS ADD_PART_AMOUNT_VIEW from TT_PART_ABCD_TEMP A ) AA ) AAA where 1=1 ");
        return DAOUtil.findAll(sb.toString(), null);
    }
    
}
