
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : DealerBasicinfoServiceImpl1.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月13日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月13日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerBasicinfoDTO;
import com.yonyou.dms.manage.domains.PO.basedata.DealerBasicinfoPO;

/**
 * 
 * 经销商信息Service实现
 * @author ZhengHe
 * @date 2016年7月6日
 */
@Service
public class DealerBasicinfoServiceImpl implements DealerBasicinfoService{

    /**
     * 获取当前用户的经销商信息
     * @author ZhengHe
     * @date 2016年7月7日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.dealer.DealerBasicinfoService#getDealerBasicinfoById(java.lang.Long)
     */
    @Override
    public Map<String,Object> getDealerBasicinfo() throws ServiceBizException {
        List<String> params=new ArrayList<>();
        Map<String,Object> result=new HashMap<>();
        result=DAOUtil.findFirst("SELECT DEALER_CODE,DEALER_ID,GRADE,DEALER_SHORTNAME,DEALER_NAME,PROVINCE,CITY,COUNTY,PROPERTY,E_MAIL,ZIP_CODE,FAX,OPEN_DATE,HOT_LINE,SALES_LINE,SERVICE_LINE,ADDRESS,BUSINESS_HOURS,CREATED_DATE,DEALER_STATUS,COMPANY_HOMEPAGE,REMARK,RECORD_VERSION,CREATED_AT,UPDATED_AT,BANK,BOOKING_PHONE,BANK_ACCOUNT,BELONG_DATE,TAX_NO,FIXED_ASSETS,CIRCULATING_FUND,ENGINEER_NUM,SERVICEWORKER_NUM,EMPLOYEE_NUM,ASC_AREA,SERVICE_BAY_NUMBER FROM tm_dealer_basicinfo where 1=1 ", params);
        return result;
    }
    /**
     * 获取站点人员信息
     * @author GAOMING
     * @date 2016年7月7日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.dealer.DealerBasicinfoService#getDealerBasicinfoById(java.lang.Long)
     */   
    @Override
    public PageInfoDto getDealerBasicinfoMain(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,POSITION_NAME,EMPLOYEE_NAME,PHONE,MOBILE,E_MAIL FROM TM_ASC_MAIN_PERSON where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }
    /**
     * 修改经销商信息
     * @author ZhengHe
     * @date 2016年7月7日
     * @param id
     * @param dbDto
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.dealer.DealerBasicinfoService#modifyBasicinfo(java.lang.Long, com.yonyou.dms.manage.domains.DTO.basedata.DealerBasicinfoDTO)
     */
    @Override
    public void modifyBasicinfo(DealerBasicinfoDTO dbDto) throws ServiceBizException {
        DealerBasicinfoPO dbPo=DealerBasicinfoPO.findById(dbDto.getDealerId());
        setdbPo(dbPo, dbDto);
        dbPo.saveIt();
    }

    /**
     * 设置 DealerBasicinfoPO
     * @author ZhengHe
     * @date 2016年7月8日
     * @param dbPo
     * @param dbDto
     * @return
     */
    public void setdbPo(DealerBasicinfoPO dbPo,DealerBasicinfoDTO dbDto){
//        dbPo.setString("DEALER_SHORTNAME",dbDto.getDealerShortname());
//        dbPo.setString("DEALER_NAME",dbDto.getDealerName());
        dbPo.setString("PROVINCE",dbDto.getProvince());
        dbPo.setString("CITY",dbDto.getCity());
        dbPo.setString("COUNTY",dbDto.getCounty());
        dbPo.setString("PROPERTY",dbDto.getProperty());
        dbPo.setString("BANK",dbDto.getBank());
        dbPo.setString("BANK_ACCOUNT",dbDto.getBankAccount());
        dbPo.setDate("BELONG_DATE",dbDto.getBelongDate());
        dbPo.setString("TAX_NO",dbDto.getTaxNo());
        dbPo.setDouble("FIXED_ASSETS",dbDto.getFixedAssets());
        dbPo.setDouble("CIRCULATING_FUND",dbDto.getCirculatingFund());
        dbPo.setInteger("ENGINEER_NUM",dbDto.getEngineerNum());
        dbPo.setInteger("SERVICEWORKER_NUM",dbDto.getServiceworkerNum());
        dbPo.setInteger("GRADE",dbDto.getGrade());
        dbPo.setInteger("EMPLOYEE_NUM",dbDto.getEmployeeNum());
        dbPo.setString("ASC_AREA",dbDto.getAscArea());
        dbPo.setDouble("SERVICE_BAY_NUMBER",dbDto.getServiceBayNumber());
        dbPo.setString("BOOKING_PHONE",dbDto.getBookingPhone());
        dbPo.setString("E_MAIL",dbDto.geteMail());
        dbPo.setString("ZIP_CODE",dbDto.getZipCode());
        dbPo.setString("FAX",dbDto.getFax());
//        dbPo.setDate("OPEN_DATE",dbDto.getOpenDate());
        dbPo.setString("HOT_LINE",dbDto.getHotLine());
        dbPo.setString("SALES_LINE",dbDto.getSalesLine());
        dbPo.setString("SERVICE_LINE",dbDto.getServiceLine());
        dbPo.setString("ADDRESS",dbDto.getAddress());
        dbPo.setString("BUSINESS_HOURS",dbDto.getBusinessHours());
//        dbPo.setDate("CREATED_DATE",dbDto.getCreatedDate());
//        dbPo.setInteger("DEALER_STATUS",dbDto.getDealerStatus());
        dbPo.setString("COMPANY_HOMEPAGE",dbDto.getCompanyHomepage());
        dbPo.setString("REMARK",dbDto.getRemark());
    }


}
