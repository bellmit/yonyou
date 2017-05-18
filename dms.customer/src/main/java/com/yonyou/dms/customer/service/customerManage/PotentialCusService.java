
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : PotentialCusService.java
*
* @Author : Administrator
*
* @Date : 2016年9月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月1日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusListDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCustomerImportDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 潜在客户
 * 
 * @author zhanshiwei
 * @date 2016年9月1日
 */

public interface PotentialCusService {

    public String addPotentialCusInfo(PotentialCusDTO potentialCusDto, String customerNo) throws ServiceBizException;

    public PageInfoDto queryPotentialCusInfo(Map<String, String> queryParam) throws ServiceBizException;

    public PageInfoDto queryPotentialCusForSel(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryCampaignName(Map<String, String> queryParam) throws ServiceBizException;
    
    public PageInfoDto queryOwnerVehicles(Map<String, String> queryParam) throws ServiceBizException;

    PageInfoDto qryPotentialCus(Map<String, String> queryParam) throws ServiceBizException;

    public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException;
    
    public List<Map> queryxiumian(String id) throws ServiceBizException;
    
    public PageInfoDto queryMainCusInfoByid(String id) throws ServiceBizException;

    public List<Map> queryCustomerIntentoByCustomerNo(String id) throws ServiceBizException;
    
    public List<Map> queryKeepCartoByCustomerNo(String id) throws ServiceBizException;
    public List<Map> queryFollowtoByCustomerNo(String id) throws ServiceBizException;
    public List<Map> queryLinkMantoByCustomerNo(String id) throws ServiceBizException;
    public PageInfoDto queryCompanyName(Map<String, String> queryParam) throws ServiceBizException;

    public void modifyPotentialCusInfo(String id, PotentialCusDTO potentialCusDto) throws ServiceBizException;

    public List<Map> queryPotentialCusforExport(Map<String, String> queryParam) throws ServiceBizException;

    public void modifyPotentialCusForRedis(PotentialCusDTO potentialCusDto) throws ServiceBizException;

    public void modifyFailConsultant(PotentialCusListDTO potentialCusDto) throws ServiceBizException;

    public void ChangePotentialCus(Long id, PotentialCusDTO potentialCusDto) throws ServiceBizException;

    public Long modifyPotentialCusforDefeat(PotentialCusDTO potentialCusDto) throws ServiceBizException;

    public PageInfoDto queryPotentialCusForSalesPromotion(Map<String, String> queryParam) throws ServiceBizException;
    
    public void modifySoldBy(PotentialCusDTO potentialCusDto) throws ServiceBizException;
    
    public void dormancyApply(PotentialCusDTO potentialCusDto) throws ServiceBizException;
    
    public void activeCustomer(PotentialCusDTO potentialCusDto) throws ServiceBizException;
    
    public void mainCustomerUnite(PotentialCusDTO potentialCusDto) throws ServiceBizException;
    
    public List<Map> checkMainCustomer(String id) throws ServiceBizException;
    
    public String searchSeriesName(String id) throws ServiceBizException;
    
    public String searchModelName(String s,String m) throws ServiceBizException;
    
    public String searchConfigName(String m,String c) throws ServiceBizException;
    
    public String CheckIsCustomer(String ID) throws ServiceBizException;
    
    public String CheckCustomerContactorMobile(String id,String customerNo) throws ServiceBizException;

    public void addInfo(PotentialCustomerImportDTO dto) throws ServiceBizException;
    
    public List<Map> searchSoldBy(String no) throws ServiceBizException;
    
    public String CheckCustomerContactorMobile(String id) throws ServiceBizException;
    
    public String PhoneOrMobile(String id) throws ServiceBizException;
    
    public String CheckCusSourse(String id,String customerNo) throws ServiceBizException;
    
    public List<Map> querySleepSeries(Map<String, String> queryParam) throws ServiceBizException;
    

}
