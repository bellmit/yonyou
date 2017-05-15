
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LmsThread.java
*
* @Author : LiGaoqi
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.IntHolder;
import javax.xml.rpc.holders.StringHolder;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.infoeai.eai.wsClient.lms.singlequery.SingleQuerySoapProxy;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author LiGaoqi
* @date 2017年5月8日
*/

public class LmsThread extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(LmsThread.class);
    private String entityCode;
    private String cusNo;
    private String phone;
    private String mobile;
    
    public String getCusNo() {
        return cusNo;
    }

    public void setCusNo(String cusNo) {
        this.cusNo = cusNo;
    }

    public String getEntityCode() {
        return entityCode;
    }

    public void setEntityCode(String entityCode) {
        this.entityCode = entityCode;
    }

    public void run(){
        Boolean excuteFlag=true;
        for (int ii=0;(ii<=3 && excuteFlag);ii++){
        
        try{
            logger.debug("客户信息已经生成，下面去LMS校验线程开始3");
            BooleanHolder createSingleQueryResult = new BooleanHolder();
            IntHolder conflictedType = new IntHolder() ;
            IntHolder opportunityLevelID = new IntHolder(1);
            logger.debug("客户信息已经生成，下面去LMS校验线程开始31");
            StringHolder consultant = new StringHolder();
            logger.debug("客户信息已经生成，下面去LMS校验线程开始4");
            try {
                logger.debug("客户信息已经生成，下面去LMS校验线程开始5");
           /*     StringBuilder sqlSb = new StringBuilder("");
                sqlSb.append("select DCS_CODE from Tt_DEALER_RELATION where DMS_CODE='"+entityCode+"'");
                List<Object> params = new ArrayList<Object>();
         
                System.out.println(sqlSb);*/
                List<Map> lsit = Base.findAll("select DCS_CODE from Tt_DEALER_RELATION where DMS_CODE=? ",new Object[] { entityCode });
                /*List<Map> lsit = Base.findAll("select DCS_CODE from Tt_DEALER_RELATION where DMS_CODE='"+entityCode+"'", null);*/
                logger.debug("客户信息已经生成，下面去LMS校验线程开始6");
                String lmsCode=null;
                if(lsit!=null&&lsit.size()>0){
                    if(!StringUtils.isNullOrEmpty(lsit.get(0).get("DCS_CODE")))
                    lmsCode=lsit.get(0).get("DCS_CODE").toString();
                }
                logger.debug("线程开始经销商代码"+lmsCode+"手机"+mobile+"电话"+phone);
                if (!StringUtils.isNullOrEmpty(lmsCode)){
                    SingleQuerySoapProxy webservices= new SingleQuerySoapProxy();
                    webservices.createSingleQuery(lmsCode, phone, mobile, createSingleQueryResult, conflictedType, opportunityLevelID, consultant);
                }
            } catch (Exception e) {
                logger.debug("网络不通或者服务器连不上!webservice出错了!");
                throw e;
                // TODO: handle exception  return true---1--5--姜屹旸
            }
            boolean tOrF=   createSingleQueryResult.value;
            int intFlag=conflictedType.value;
            String consultantname=consultant.value;
            int levelId=opportunityLevelID.value;
             logger.debug("LMS反馈结果"+tOrF+"---"+intFlag+"--"+levelId+"--"+consultantname);
            if (tOrF && intFlag==1){
                String level="";
                  switch(levelId)
                  {
                  case 1:  level="意向级别为H级,";break;
                  case 2:  level="意向级别为A级,";break;
                  case 3:  level="意向级别为B级,";break;
                  case 4:  level="意向级别为C级,";break;
                  case 5:  level="意向级别为N级,";break;
                  default :  level="意向级别为N级,";
                  }
                    String date="LMS反馈日期:"+new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString(); 
                    String lmsRemark="客户已存在LMS系统中,"+level+date;
                    try
                    {
                     PotentialCusPO cuspo =PotentialCusPO.findByCompositeKeys(entityCode,cusNo); 
                     cuspo.setString("LMS_REMARK", lmsRemark);
                     cuspo.saveIt();
                 
                    }
                    catch (Exception ex)
                    {
                        logger.debug("数据库执行失败");
                        throw ex;
                    }
            }
            excuteFlag=false;   
                
        }
        catch(Exception e){
                e.printStackTrace();
            }
           try {
               if(excuteFlag){
                   logger.debug("异常了两分钟后重试"+ii);
                      long retry = 120L; 
                      sleep(retry * 1000L);
                  }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
        }
    }




 public static void main(String[] args) {
     LmsThread aa = new LmsThread();
     aa.start();
     
}

public String getMobile() {
    return mobile;
}

public void setMobile(String mobile) {
    this.mobile = mobile;
}

public String getPhone() {
    return phone;
}

public void setPhone(String phone) {
    this.phone = phone;
}



}
