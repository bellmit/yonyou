
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : PotentialCusContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月1日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月1日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.customerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusListDTO;
import com.yonyou.dms.common.domains.DTO.customer.CustomerTrackingDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCustomerImportDTO;
import com.yonyou.dms.customer.service.customerManage.PotentialCusService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 潜在客户
 * 
 * @author zhanshiwei
 * @date 2016年9月1日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/potentialcus")
public class PotentialCusContoller extends BaseController {

    @Autowired
    private PotentialCusService potentialcusservice;
    @Autowired
    private ExcelGenerator      excelService;
    @Autowired
    private ExcelRead<PotentialCustomerImportDTO> excelReadService;

    
    

    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPotentialCusInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryPotentialCusInfo(queryParam);
        return pageInfoDto;
    }

    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPotentialCusInfoByid(@PathVariable(value = "id") String id) {
        List<Map> result = potentialcusservice.queryPotentialCusInfoByid(id);
        
        return result.get(0);
    }
    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/xiumsq/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryxiumian(@PathVariable(value = "id") String id) {
        List<Map> result = potentialcusservice.queryxiumian(id);
        
        return result.get(0);
    }
    /**
     * 潜在客户查询
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/maincustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMainCusInfoByid(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = potentialcusservice.queryMainCusInfoByid(id); 
       
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/maincustomer/check/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> checkMainCustomer(@PathVariable(value = "id") String id) {
        System.out.println(id);
        List<Map> result = potentialcusservice.checkMainCustomer(id);
        
        return result;
    }

    /**
     * 销售订单选择潜客
     * 
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/salesOrderSel/potentialCuInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qryPotentialCusInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.qryPotentialCus(queryParam);
        return pageInfoDto;
    }

    /**
     * 潜在客户新增
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param potentialCusDto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addPotentialCusInfo(@RequestBody  PotentialCusDTO potentialCusDto,
                                                                   UriComponentsBuilder uriCB) {
    	
    	CommonNoServiceImpl commonNoService = new CommonNoServiceImpl();
        String customerNo = potentialcusservice.addPotentialCusInfo(potentialCusDto,
                                                          commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{customerNo}").buildAndExpand(customerNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    

    @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.modifySoldBy(potentialCusDto);
    }
    
    @RequestMapping(value = "/apply/applyList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void dormancyApply(@RequestBody PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.dormancyApply(potentialCusDto);
    }
    
    @RequestMapping(value = "/active/activeList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void activeCustomer(@RequestBody PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.activeCustomer(potentialCusDto);
    }
    @RequestMapping(value = "/mainCustomer/unite", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void mainCustomerUnite(@RequestBody PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.mainCustomerUnite(potentialCusDto);
    }
    /**
     * 展厅接待选择潜客查询
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月7日,2017年1月4日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/visitRecord/potentialCusSel", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPotentialCusForSel(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryPotentialCusForSel(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 选择市场活动
     * 
     * @author LGQ
     * @date 2017年1月4日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/campaign/campaignName", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCampaignName(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryCampaignName(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 选择推荐人
     * 
     * @author LGQ
     * @date 2017年1月4日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/ownerVehicles/selectInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryOwnerVehicles(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryOwnerVehicles(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据父表CUSTOMER_NO(客户意向)明细
     * 
     * @author LGQ
     * @date 2016年12月28日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/intents", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryVisitIntentInfoByParendId(@PathVariable("id") String id) {
        List<Map> result = potentialcusservice.queryCustomerIntentoByCustomerNo(id);
        return result;
    }
    /**
     * 根据父表CUSTOMER_NO(保有车辆)明细
     * 
     * @author LGQ
     * @date 2016年12月28日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/keepCar", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryKeepCarByParendId(@PathVariable("id") String id) {
        List<Map> result = potentialcusservice.queryKeepCartoByCustomerNo(id);
        return result;
    }
    /**
     * 根据父表CUSTOMER_NO(联系人)明细
     * 
     * @author LGQ
     * @date 2016年12月28日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/linkMan", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryLinkManByParendId(@PathVariable("id") String id) {
        List<Map> result = potentialcusservice.queryLinkMantoByCustomerNo(id);
        return result;
    }
    /**
     * 根据父表CUSTOMER_NO(联系人)明细
     * 
     * @author LGQ
     * @date 2016年12月28日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/follow", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryFollowParendId(@PathVariable("id") String id) {
        List<Map> result = potentialcusservice.queryFollowtoByCustomerNo(id);
        return result;
    }
    /**
     * 潜客信息修改
     * 
     * @author LGQ
     * @date 2016年12月18日
     * @param id
     * @param potentialCusDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PotentialCusDTO> modifyPotentialCusInfo(@PathVariable("id") String id,
                                                                  @RequestBody  PotentialCusDTO potentialCusDto,
                                                                  UriComponentsBuilder uriCB) {
        
    /*    //更新保有车辆
        List<Object> keepList = new ArrayList<Object>();
        keepList.add(id);
        keepList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<TtCustomerVehicleListPO> keepCarPo = TtCustomerVehicleListPO.findBySQL("select * from TT_CUSTOMER_VEHICLE_LIST where CUSTOMER_NO= ? AND DEALER_CODE= ? ", keepList.toArray());
        */
        potentialcusservice.modifyPotentialCusInfo(id, potentialCusDto);
        //sotdcs005.getSOTDCS005(id, "U",keepCarPo, potentialCusDto.getKeepCarList());
       //SOTDCS003.getSOTDCS003("U", conphone, conmobil)
       //sotdcs008.getSOTDCS008("U", id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PotentialCusDTO>(headers, HttpStatus.CREATED);
    }
    
 

    /**
     * 潜客信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = potentialcusservice.queryPotentialCusforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_潜客信息.xls", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE", "建档时间"));
        exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("INTENT_LEVEL", "意向级别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系手机"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系电话"));
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("DELAY_CONSULTANT", "延时再分配"));
        exportColumnList.add(new ExcelExportColumn("CONSULTANT_TIME", "再分配时间"));       
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人姓名"));
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "默认联系人电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "默认联系人手机"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "意向品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "意向配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "意向颜色"));
        exportColumnList.add(new ExcelExportColumn("LAST_ARRIVE_TIME", "最后一次进店时间"));       
        exportColumnList.add(new ExcelExportColumn("LAST_SOLD_BY", "前销售顾问"));
        exportColumnList.add(new ExcelExportColumn("VALIDITY_BEGIN_DATE", "客户有效开始日期"));
        exportColumnList.add(new ExcelExportColumn("UPDATED_AT", "修改日期"));
        exportColumnList.add(new ExcelExportColumn("LMS_REMARK", "LMS校验反馈"));
        exportColumnList.add(new ExcelExportColumn("DDCN_UPDATE_DATE", "意向级别变得时间"));
        exportColumnList.add(new ExcelExportColumn("REBUY_CUSTOMER_TYPE", "非首次购车客户类型"));
        exportColumnList.add(new ExcelExportColumn("IS_PAD_CREATE", "是否PAD建档",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_BIG_CUSTOMER", "是否大客户",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("REPLACE_DATE", "置换意向日期"));
        exportColumnList.add(new ExcelExportColumn("OLD_CUSTOMER_NAME", "保客推荐人姓名"));
        exportColumnList.add(new ExcelExportColumn("OLD_CUSTOMER_VIN", "保客推荐人VIN"));
        exportColumnList.add(new ExcelExportColumn("EXPECT_TIMES_RANGE", "预计成交时段",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EXPECT_DATE", "预计成交时段录入时间"));
        exportColumnList.add(new ExcelExportColumn("IS_ECO_INTENT_MODEL", "是否官网意向车型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "官网订单编号"));
        exportColumnList.add(new ExcelExportColumn("ESC_ORDER_STATUS", "电商订单状态",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("ESC_TYPE", "电商客户类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("DEPOSIT_AMOUNT", "定金金额"));
        exportColumnList.add(new ExcelExportColumn("RETAIL_FINANCE", "零售金融"));
        exportColumnList.add(new ExcelExportColumn("DETERMINED_TIME", "下定日期 "));
        exportColumnList.add(new ExcelExportColumn("IS_TO_SHOP", "是否到店",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("TIME_TO_SHOP", "到店日期"));
        // 生成excel 文件
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_潜客信息.xls", request, response);
        
        

    }

    /**
     * (在分配),
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param potentialCusDto
     * @param uriCB
     */

    @RequestMapping(value = "/redistribution", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyPotentialCusForRedis(@RequestBody  PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.modifyPotentialCusForRedis(potentialCusDto);
    }
    
    /**
    * (战败再分配)
    * @author zhanshiwei
    * @date 2016年11月10日
    * @param potentialCusDto
    * @param uriCB
    */
    	
    @RequestMapping(value = "/redistributionDefeat", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyPotentialCusForRedisDefeat(@RequestBody  PotentialCusDTO potentialCusDto,
                                           UriComponentsBuilder uriCB) {
        potentialcusservice.modifyPotentialCusForRedis(potentialCusDto);
    }

    /**
     * 战败确认
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param potentialCusDto
     * @param uriCB
     */

    @RequestMapping(value = "/failConsultant", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyFailConsultant(@RequestBody PotentialCusListDTO potentialCusDto, UriComponentsBuilder uriCB) {
        potentialcusservice.modifyFailConsultant(potentialCusDto);
    }

    /**
     * 转潜客
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param id
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/changeConsult/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerTrackingDTO> ChangePotentialCus(@PathVariable("id") Long id,@RequestBody PotentialCusDTO potentialCusDto,
                                                                  UriComponentsBuilder uriCB) {
        potentialcusservice.ChangePotentialCus(id,potentialCusDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<CustomerTrackingDTO>(headers, HttpStatus.CREATED);

    }

    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "followCus", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryFollowPotentialCusInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryPotentialCusInfo(queryParam);
        return pageInfoDto;
    }
    
    
    /**
     * 潜在客户查询
     * 
     * @author LGQ
     * @date 2016年12月29日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/customer/company", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCompanyName(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryCompanyName(queryParam);
        return pageInfoDto;
    }

    /**
     * 潜客跟进更新战败车型
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param potentialCusDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "defeat", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PotentialCusDTO> modifyPotentialCusforDefeat(@RequestBody  PotentialCusDTO potentialCusDto,
                                                                       UriComponentsBuilder uriCB) {

        Long id = potentialcusservice.modifyPotentialCusforDefeat(potentialCusDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PotentialCusDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 潜客跟进选择潜客查询
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/salespromotionSel/potentialCuInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPotentialCusForSalesPromotion(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = potentialcusservice.queryPotentialCusForSalesPromotion(queryParam);
        return pageInfoDto;
    }
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/PhoneOrMobile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String PhoneOrMobile(@PathVariable(value = "id") String id) {
        String pageInfoDto = potentialcusservice.PhoneOrMobile(id); 
        return pageInfoDto;
    }
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/CheckCustomerContactorMobile/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckCustomerContactorMobile(@PathVariable(value = "id") String id) {
        String pageInfoDto = potentialcusservice.CheckCustomerContactorMobile(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/CheckCustomerContactorMobile/{id}/{customerNo}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckCustomerContactorMobile(@PathVariable(value = "id") String id,@PathVariable(value = "customerNo") String customerNo) {
        String pageInfoDto = potentialcusservice.CheckCustomerContactorMobile(id,customerNo); 
        return pageInfoDto;
    }
    
    /**
     * 检查客户来源
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/Check/CheckCusSourse/{id}/{customerNo}", method = RequestMethod.GET)
    @ResponseBody
    public String CheckCusSourse(@PathVariable(value = "id") String id,@PathVariable(value = "customerNo") String customerNo) {
        String pageInfoDto = potentialcusservice.CheckCusSourse(id,customerNo); 
        return pageInfoDto;
    }
    
    /**
     * 上传文件
     * 
     * @throws Exception
     **/
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public List<PotentialCustomerImportDTO> importList(@RequestParam(value = "file") MultipartFile importFile,
            UriComponentsBuilder uriCB) throws Exception {
        /* final List<Map> list = potentialcusservice.searchSoldBy();*/
         // 解析Excel 表格(如果需要进行回调)
         ImportResultDto<PotentialCustomerImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
                new AbstractExcelReadCallBack<PotentialCustomerImportDTO>(PotentialCustomerImportDTO.class,
                        new ExcelReadCallBack<PotentialCustomerImportDTO>() {
                            @Override
                            public void readRowCallBack(PotentialCustomerImportDTO rowDto, boolean isValidateSucess) {
                                try {
                                    // 保存入库信息,只有全部是成功的情况下，才执行数据库保存
                                    if (isValidateSucess) {
                               /*      for (Map map : list) {
                                            if (map.get("USER_NAME")!=null) {
                                                if (map.get("USER_NAME").toString().equals(rowDto.getSoldBy())) {
                                                    rowDto.setSoldBy(map.get("USER_ID").toString());
                                                }else{
                                                    rowDto.setSoldBy("");
                                                }
                                            }
                                        }*/
                                        potentialcusservice.addInfo(rowDto);
                                    }
                                } catch (Exception e) {
                                    throw e;
                                }
                            }
                        }));

        if (importResult.isSucess()) {
            return importResult.getDataList();
        } else {
            throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
        }
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/search/{id}/SeriesName", method = RequestMethod.GET)
    @ResponseBody
    public String searchSeriesName(@PathVariable(value = "id") String id) {
        String pageInfoDto = potentialcusservice.searchSeriesName(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/search/{S}/{M}/ModelName", method = RequestMethod.GET)
    @ResponseBody
    public String searchModelName(@PathVariable(value = "S") String S,@PathVariable(value = "M") String M) {
        String pageInfoDto = potentialcusservice.searchModelName(S,M); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客电话
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/search/{M}/ConfigName/{C}", method = RequestMethod.GET)
    @ResponseBody
    public String searchConfigName(@PathVariable(value = "M") String M,@PathVariable(value = "C") String C) {
        String pageInfoDto = potentialcusservice.searchConfigName(M,C); 
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/Check/{ID}/CheckIsCustomer", method = RequestMethod.GET)
    @ResponseBody
    public String CheckIsCustomer(@PathVariable(value = "ID") String ID) {
        String pageInfoDto = potentialcusservice.CheckIsCustomer(ID); 
        return pageInfoDto;
    }
    /**
     * 
     * 提供给下拉框的方法(过滤valid)
     * @author yll
     * @date 2016年7月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/{intentSeries}/sleepSeries",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySleepSeries(@PathVariable String intentSeries,@RequestParam Map<String,String> queryParam) {
        queryParam.put("code", intentSeries);
        List<Map> brandlist = potentialcusservice.querySleepSeries(queryParam);
        
        return brandlist;
    }
    
}
