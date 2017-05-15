
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS061Imp.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADCS060Dto;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * 交车确认上报专属客户经理信息
* TODO description
* @author LiGaoqi
* @date 2017年4月18日
*/
@Service
public class SADMS061Imp implements SADMS061 {
    private static final Logger logger = LoggerFactory.getLogger(SADMS061Imp.class);

    /**
    * @author LiGaoqi
    * @date 2017年4月18日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.SADMS061#getSADMS061()
    */

    @Override
    public LinkedList<SADCS060Dto> getSADMS061(String soNo,String vin,String isConfirmed) throws ServiceBizException {
        LinkedList<SADCS060Dto> resultList = new LinkedList<SADCS060Dto>();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
       /* Long userId = FrameworkUtil.getLoginInfo().getUserId();*/
        SalesOrderPO orderPO = SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
        if(!StringUtils.isNullOrEmpty(orderPO)&&orderPO.getString("D_KEY").equals("0")&&orderPO.getString("VIN").equals(vin)){
            String customerNo = orderPO.getString("CUSTOMER_NO");
            PotentialCusPO poCustomer = PotentialCusPO.findByCompositeKeys(dealerCode,customerNo);
            VehiclePO tpo = VehiclePO.findByCompositeKeys(vin,dealerCode);
            EmployeePo emp =new EmployeePo();
            if(!StringUtils.isNullOrEmpty(tpo)){
             emp = EmployeePo.findByCompositeKeys(dealerCode,tpo.getString("SERVICE_ADVISOR"));
            }
            if(!StringUtils.isNullOrEmpty(poCustomer)){
                SADCS060Dto vo = new SADCS060Dto();
                vo.setDealerCode(dealerCode);
                vo.setDmsOwnerId(customerNo);
                vo.setName(poCustomer.getString("CUSTOMER_NAME"));//车主名
                if(poCustomer.getInteger("CUSTOMER_TYPE")==10181001){
                    vo.setClientType("1");//客户类型 客户
                }else{
                    vo.setClientType("2");//公司
                }
                
                vo.setIdOrCompCode(poCustomer.getString("CERTIFICATE_NO"));//身份证号
                vo.setAddress(poCustomer.getString("ADDRESS"));//地址
                if(poCustomer.getInteger("GENDER")==10061001){
                    vo.setGender("1");//性别 男
                }else if(poCustomer.getInteger("GENDER")==10061002){
                    vo.setGender("2");//女
                }else{
                    vo.setGender("");
                }
                
                if(poCustomer.get("PROVINCE")!=null){
                    vo.setProvinceId(poCustomer.get("PROVINCE").toString());//省份                  
                }
                if(poCustomer.get("CITY")!=null){
                    vo.setCityId(poCustomer.get("CITY").toString());//城市                  
                }
                if(poCustomer.get("DISTRICT")!=null){
                    vo.setDistrict(poCustomer.get("DISTRICT").toString());//区县                    
                }
                vo.setPostCode(poCustomer.getString("ZIP_CODE"));//邮编
                vo.setCellphone(poCustomer.getString("CONTACTOR_MOBILE"));//联系手机
                vo.setEmail(poCustomer.getString("EMAIL"));//邮箱
                SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
                String buyTime = dateformat1.format(orderPO.getDate("CONFIRMED_DATE"));
                vo.setBuyTime(buyTime);//交车确认日期
                vo.setVin(vin);
                vo.setEntityCode(dealerCode);
                if(!StringUtils.isNullOrEmpty(emp)){                    
                    vo.setServiceAdviser(emp.getString("EMPLOYEE_NO"));
                    vo.setEmployeeName(emp.getString("EMPLOYEE_NAME"));        
                    if(!StringUtils.isNullOrEmpty(emp.getString("MOBILE"))){
                        vo.setMobile(emp.getString("MOBILE"));                            
                    }else if(!StringUtils.isNullOrEmpty(emp.getString("PHONE"))){
                        vo.setMobile(emp.getString("PHONE"));
                    }
                    vo.setWxBindTime(dateformat1.format(new Date()));
                }
                resultList.add(vo);
            }
        }
       
        // TODO Auto-generated method stub
        logger.info("================经销商:"+dealerCode+"====================");
        return resultList;
    }

}
