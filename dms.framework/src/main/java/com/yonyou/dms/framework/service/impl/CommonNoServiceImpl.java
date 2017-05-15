
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : CommonNoServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.service.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.PO.TmBillTypePO;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.domains.PO.TtBillNoPo;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author jcsi
 * @date 2016年7月19日
 */
@Service
public class CommonNoServiceImpl implements CommonNoService {

    // 比较二个时间的时间差S返回秒，M返回分钟，H返回小时，D发回天
    @Override
    public long getTimeDiff(String type, Date t1, Date t2) throws ServiceBizException {
        long time1 = t1.getTime();
        long time2 = t2.getTime();
        long diff;
        long time = 0;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        if ("S".equals(type)) {
            time = diff / 1000;
        }
        if ("M".equals(type)) {
            time = diff / (1000 * 60);
        }
        if ("H".equals(type)) {
            time = diff / (1000 * 60 * 60);
        }
        if ("D".equals(type)) {
            time = diff / (1000 * 60 * 60 * 24);
        }
        return time;
    }

    // 批售单编号生成规则
    @Override
    public String getWSOrderNo(String orderPrefix, String entityCode) throws ServiceBizException {
        try {
            String systemOrderNo = StringUtils.EMPTY_STRING;
            String yyMM = StringUtils.EMPTY_STRING;
            String yyyy = StringUtils.EMPTY_STRING;
            String MM = StringUtils.EMPTY_STRING;
            String dd = StringUtils.EMPTY_STRING;
            String str = entityCode.substring(1, 5);
            // 获取年月
            Date data = new Date();
            SimpleDateFormat yyyyMM = new SimpleDateFormat("yyMM");
            SimpleDateFormat yearF = new SimpleDateFormat("yy");
            SimpleDateFormat monthF = new SimpleDateFormat("MM");
            SimpleDateFormat dayF = new SimpleDateFormat("dd");
            yyMM = yyyyMM.format(data);
            yyyy = yearF.format(data);
            MM = monthF.format(data);
            dd = dayF.format(data);
            System.out.println(str);
            System.out.println(yyMM);
            System.out.println(yyyy);
            System.out.println(MM);
            System.out.println(dd);
            // 查询数据
            List<Map> listPo = lock(orderPrefix, yyyy, MM, dd);
            if (!CommonUtils.isNullOrEmpty(listPo)) {
                // 如果存在数据 取出 序号 然后+1 并修改记录
                Map map = listPo.get(0);
                // 订单号前缀
                systemOrderNo += map.get("BILL_NO_TYPE");
                // entityCode后六位
                systemOrderNo += str;
                // 年
                systemOrderNo += map.get("B_YEAR");
                // 月
                systemOrderNo += map.get("B_MONTH");
                // 日
                systemOrderNo += map.get("B_DAY");
                // 序列号
                Integer sequence = Integer.parseInt(map.get("SEQ").toString());
                systemOrderNo += CommonUtils.getFourOrderNo(sequence);
                // 更新数据库序列号
                TtBillNoPo son = TtBillNoPo.findById(map.get("BILL_NO_ID"));
                son.set("SEQ", sequence + 1);

                son.saveIt();

                return systemOrderNo;
            } else {
                // 如果记录不存在 新增
                TtBillNoPo son = new TtBillNoPo();
                if (!StringUtils.isNullOrEmpty(orderPrefix)) {
                    son.set("BILL_NO_TYPE", orderPrefix);
                }

                if (!StringUtils.isNullOrEmpty(yyyy)) {
                    son.set("B_YEAR", yyyy);
                }
                if (!StringUtils.isNullOrEmpty(MM)) {
                    son.set("B_MONTH", MM);
                }
                if (!StringUtils.isNullOrEmpty(dd)) {
                    son.set("B_DAY", dd);
                }
                son.set("SEQ", CommonConstants.INIT_ORDER_NO + 1);

                son.saveIt();

                return orderPrefix + str + yyyy + MM + dd + CommonUtils.getFourOrderNo(CommonConstants.INIT_ORDER_NO);
            }
        } catch (Exception e) {
            throw new ServiceBizException("erro", e);
        }
    }

    // ID生成
    @Override
    public long getId(String orderPrefix) throws ServiceBizException {
        try {
            String systemOrderNo = StringUtils.EMPTY_STRING;
            String yyMM = StringUtils.EMPTY_STRING;
            String yyyy = StringUtils.EMPTY_STRING;
            String MM = StringUtils.EMPTY_STRING;
            String dd = StringUtils.EMPTY_STRING;
            String str = "5000";
            // 获取年月
            Date data = new Date();
            SimpleDateFormat yyyyMM = new SimpleDateFormat("yyMM");
            SimpleDateFormat yearF = new SimpleDateFormat("yy");
            SimpleDateFormat monthF = new SimpleDateFormat("MM");
            SimpleDateFormat dayF = new SimpleDateFormat("dd");
            yyMM = yyyyMM.format(data);
            yyyy = yearF.format(data);
            MM = monthF.format(data);
            dd = dayF.format(data);

            System.out.println(yyMM);
            System.out.println(yyyy);
            System.out.println(MM);
            System.out.println(dd);
            // 查询数据
            List<Map> listPo = lock(orderPrefix, yyyy, MM, dd);
            if (!CommonUtils.isNullOrEmpty(listPo)) {
                // 如果存在数据 取出 序号 然后+1 并修改记录
                Map map = listPo.get(0);
                // 订单号前缀
                systemOrderNo += str;
                // 年
                systemOrderNo += map.get("B_YEAR");
                // 月
                systemOrderNo += map.get("B_MONTH");
                // 日
                systemOrderNo += map.get("B_DAY");
                // 序列号
                Integer sequence = Integer.parseInt(map.get("SEQ").toString());
                systemOrderNo += CommonUtils.getFourOrderNo(sequence);
                // 更新数据库序列号
                TtBillNoPo son = TtBillNoPo.findById(map.get("BILL_NO_ID"));
                son.set("SEQ", sequence + 1);

                son.saveIt();

                return Long.parseLong(systemOrderNo);
            } else {
                // 如果记录不存在 新增
                TtBillNoPo son = new TtBillNoPo();
                if (!StringUtils.isNullOrEmpty(orderPrefix)) {
                    son.set("BILL_NO_TYPE", orderPrefix);
                }

                if (!StringUtils.isNullOrEmpty(yyyy)) {
                    son.set("B_YEAR", yyyy);
                }
                if (!StringUtils.isNullOrEmpty(MM)) {
                    son.set("B_MONTH", MM);
                }
                if (!StringUtils.isNullOrEmpty(dd)) {
                    son.set("B_DAY", dd);
                }
                son.set("SEQ", CommonConstants.INIT_ORDER_NO + 1);

                son.saveIt();

                return Long.parseLong((str + yyyy + MM + dd
                                       + CommonUtils.getFourOrderNo(CommonConstants.INIT_ORDER_NO)));
            }
        } catch (Exception e) {
            throw new ServiceBizException("erro", e);
        }

    }

    /**
     * @author jcsi
     * @date 2016年7月19日
     * @param orderPrefix
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.framework.service.CommonNoService#getSystemOrderNo(java.lang.String)
     */

    @Override
    public String getSystemOrderNo(String orderPrefix, String... titles) throws ServiceBizException {
        try {
            String systemOrderNo = StringUtils.EMPTY_STRING;
            String yyMM = StringUtils.EMPTY_STRING;
            String yyyy = StringUtils.EMPTY_STRING;
            String MM = StringUtils.EMPTY_STRING;
            String dd = StringUtils.EMPTY_STRING;
            // 获取年月
            Date data = new Date();
            SimpleDateFormat yyyyMM = new SimpleDateFormat("yyMM");
            SimpleDateFormat yearF = new SimpleDateFormat("yy");
            SimpleDateFormat monthF = new SimpleDateFormat("MM");
            SimpleDateFormat dayF = new SimpleDateFormat("dd");
            yyMM = yyyyMM.format(data);
            yyyy = yearF.format(data);
            MM = monthF.format(data);
            dd = dayF.format(data);
            System.out.println(yyMM);
            System.out.println(yyyy);
            System.out.println(MM);
            System.out.println(dd);
            // 查询数据
            List<Map> listPo = lock(orderPrefix, yyyy, MM, dd);
            if (!CommonUtils.isNullOrEmpty(listPo)) {
                // 如果存在数据 取出 序号 然后+1 并修改记录
                Map map = listPo.get(0);
                // 订单号前缀
                systemOrderNo += map.get("BILL_NO_TYPE");
                // 年
                systemOrderNo += yyMM;
                // 月
                
                //systemOrderNo += map.get("B_MONTH");
                // 日
                systemOrderNo += dd;
                // 序列号
                Integer sequence = Integer.parseInt(map.get("SEQ").toString());
                systemOrderNo += CommonUtils.getFourOrderNo(sequence);
                // 更新数据库序列号
                TtBillNoPo son = TtBillNoPo.findById(map.get("BILL_NO_ID"));
                son.set("SEQ", sequence + 1);

                son.saveIt();
                System.out.println(systemOrderNo);
                return systemOrderNo;
            } else {
                // 如果记录不存在 新增
                TtBillNoPo son = new TtBillNoPo();
                if (!StringUtils.isNullOrEmpty(orderPrefix)) {
                    son.setString("BILL_NO_TYPE", orderPrefix);
                }

                if (!StringUtils.isNullOrEmpty(yyyy)) {
                    son.setString("B_YEAR", yyyy);
                }
                if (!StringUtils.isNullOrEmpty(MM)) {
                    son.setString("B_MONTH", MM);
                }
                if (!StringUtils.isNullOrEmpty(dd)) {
                    son.setString("B_DAY", dd);
                }
                son.setString("SEQ", CommonConstants.INIT_ORDER_NO + 1);

                son.saveIt();
                System.out.println(orderPrefix);
                return orderPrefix + yyyy + MM + dd + CommonUtils.getFourOrderNo(CommonConstants.INIT_ORDER_NO);
               
            }
        } catch (Exception e) {
            throw new ServiceBizException("erro", e);
        }
    }

    private static int sumUp(int... values) {// 表示传入sumUp的整数个数不确定，values是一个长度不确定的int数组，根据传入的参数确定长度
        int sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum;
    }

    /**
     * @author jcsi
     * @date 2016年7月19日
     * @param orderPrefix
     * @param yyyyMM
     * @return
     */
    public List<Map> lock(String orderPrefix, String yyyy, String MM, String dd) {
        StringBuilder sb = new StringBuilder("select BILL_NO_ID,DEALER_CODE,BILL_NO_TYPE,B_YEAR,B_MONTH,B_DAY,SEQ,CREATED_BY,UPDATED_BY,CREATED_AT,UPDATED_AT from TT_BILL_NO where 1=1 ");
        List<Object> queryParam = new ArrayList<>();
        if (!StringUtils.isNullOrEmpty(orderPrefix)) {
            sb.append(" and BILL_NO_TYPE = ? ");
            queryParam.add(orderPrefix);
        }
        if (!StringUtils.isNullOrEmpty(yyyy)) {
            sb.append(" and B_YEAR = ? ");
            queryParam.add(yyyy);
        }
        if (!StringUtils.isNullOrEmpty(MM)) {
            sb.append(" and B_MONTH = ? ");
            queryParam.add(MM);
        }
        if (!StringUtils.isNullOrEmpty(dd)) {
            sb.append(" and B_DAY = ? ");
            queryParam.add(dd);
        }
        return DAOUtil.findAll(sb.toString(), queryParam);
        /*
         * select SYSTEM_ORDER_NO_ID,DEALER_CODE,ORDER_PREFIX,YYYY_MM,ORDER_SEQUENCE,CREATE_BY,UPDATE_BY,CREATE_DATE,
         * UPDATE_DATE from TC_SYSTEM_ORDER_NO where ORDER_PREFIX=#{orderPrefix,jdbcType=VARCHAR} and
         * YYYY_MM=#{yyyyMM,jdbcType=VARCHAR} and DEALER_CODE=#{dealerCode,jdbcType=VARCHAR} for update
         */
    }

    // 获得开关
    @Override
    public String getDefalutPara(String para) throws ServiceBizException {
        LoginInfoDto login = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        TmDefaultParaPO paraPO = TmDefaultParaPO.findByCompositeKeys(login.getDealerCode(), para);
        if (paraPO != null) {
            return paraPO.getString("DEFAULT_VALUE");
        } else {
            return null;
        }

    }

    /**
     * 取得票据号码
     * 
     * @author guodong
     * @param pOrg_Code
     * @param type :"EO" 估价单号
     * @param conn
     * @return
     * @throws SQLException
     */
    public String GetBillNo(String type) {
        StringBuffer sb = new StringBuffer();
        lockVerify(type);
        Calendar cal = Calendar.getInstance();
        String year = DateUtil.getYearString(cal.getTime());
        String cyear = year.substring(year.length() - 3, year.length());// --服务器年份
        String cmonth = DateUtil.getMonthString(cal.getTime());// --服务器月份
        String cday = DateUtil.getDayString(cal.getTime());// --服务器日期
        List<TtBillNoPo> son = lockin(type, cyear, cmonth, cday);
        int NID=0;
        if (NID > 9999) {
            throw new ServiceBizException("ERROR-9999,没有对应的单据类型!");
        } 
        if (son != null && son.size() > 0) {
            NID=son.get(son.size()-1).getInteger("SEQ")+1;//sumFunc(son.size());
            TtBillNoPo son2 = TtBillNoPo.findById(son.get(son.size()-1).getInteger("BILL_NO_ID"));
            son2.setInteger("SEQ", NID);
            son2.saveIt();
        } else {
            insertBillNo(type, cyear, cmonth, cday);
        }
        if (StringUtils.isEquals("MC", type)) {
            sb.append(type).append(1101*10000000000L).append(cyear).append(cmonth).append(cday).append(CommonUtils.getFourOrderNo(NID));
        } else {
            sb.append(type).append(cyear.trim()).append(cmonth.trim()).append(cday.trim()).append(NID);
        }
        System.err.println(type.trim()+cyear.trim()+cmonth.trim()+cday.trim()+(CommonUtils.getFourOrderNo(NID))+"----------");
        return sb.toString();
    }

    public List<TtBillNoPo> lockin(Object... queryParam) {
/*        List<TtBillNoPo> son = TtBillNoPo.findBySQL("select * from tt_bill_no where  DEALER_CODE=?  AND BILL_NO_TYPE=?  AND B_YEAR=?  AND B_MONTH=?   AND B_DAY=?",
                                                    queryParam);*/
        List<TtBillNoPo> son= DAOUtil.findByDealer(TtBillNoPo.class, "BILL_NO_TYPE=?  AND B_YEAR=?  AND B_MONTH=?   AND B_DAY=?", queryParam);
        return son;
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月8日
     * @param type
     */

    public void lockVerify(String type) {
        TmBillTypePO tmBipo = TmBillTypePO.findById(type);
        if (tmBipo == null) {
            throw new ServiceBizException("ERROR-9999,没有对应的单据类型!");
        }
    }

    public void insertBillNo(String type,String cyear, String cmonth, String cday) {
        TtBillNoPo sno = new TtBillNoPo();
        sno.setString("BILL_NO_TYPE", type);
        sno.setString("B_YEAR", cyear);
        sno.setString("B_MONTH", cmonth);
        sno.setString("B_DAY", cday);
        sno.setInteger("SEQ", 0);
        sno.saveIt();
    }
}
