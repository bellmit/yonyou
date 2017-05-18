
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月16日    zhanshiwei    1.0
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
import javax.validation.Valid;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.customer.RetainCustomersDTO;
import com.yonyou.dms.customer.service.customerManage.CustomerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 保有客户维护
 * 
 * @author zhanshiwei
 * @date 2016年8月16日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/retainCustomer")
public class CustomerContoller extends BaseController {

    @Autowired
    private CustomerService customerservice;
    
    @Autowired
    private ExcelGenerator      excelService;


    /**
     * 保有客户查询
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerInnfo(@RequestParam Map<String, String> queryParam) {
    	String DealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        PageInfoDto pageInfoDto = customerservice.queryCustomerInnfo(queryParam,DealerCode);
        return pageInfoDto;
    }

    /**
     * 根据车辆ID查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */

    @RequestMapping(value = "/{vehicle_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryCarownerInfoByid(@PathVariable(value = "vehicle_id") Long vehicle_id) {
        Map<String, Object> tepomap = customerservice.queryCustomerVehicleInfoByid(vehicle_id);
        return tepomap;
    }

    /**
     * 保有客户信息修改
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @param tetainDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{vehicle_id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> modifyRetainCusVehicleInfo(@PathVariable("vehicle_id") Long vehicle_id,
                                                                             @RequestBody @Valid RetainCustomersDTO tetainDto,
                                                                             UriComponentsBuilder uriCB) {

        Map<String, Object> tepomap = customerservice.modifyRetainCusVehicleInfo(vehicle_id, tetainDto,null);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/retainCustomer/{vehicle_id}").buildAndExpand(vehicle_id).toUriString());
        return new ResponseEntity<Map<String, Object>>(tepomap, headers, HttpStatus.CREATED);
    }
    
    /**
    * 保有客户选择查询
    * @author zhanshiwei
    * @date 2016年8月22日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value="/cusTracking/customerInfoSe",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerSelectInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = customerservice.queryCustomerSelectInfo(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 潜客信息导出
     * 
     * @author zhanshiwei
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
        List<Map> resultList = customerservice.queryOwnerCusforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("潜客信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SUBMIT_STATUS", "上报状态"));
        exportColumnList.add(new ExcelExportColumn("EXCEPTION_CAUSE", "异常原因"));
        exportColumnList.add(new ExcelExportColumn("VIS_UPLOAD", "是否上报"));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("SALES_DATE", "开票日期"));
        exportColumnList.add(new ExcelExportColumn("SUBMIT_TIME", "上报日期"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("SOLY", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型"));
        exportColumnList.add(new ExcelExportColumn("BEST_CONTACT_TYPE", "最佳联系方式"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "联系电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系手机"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("GENDER", "性别", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BIRTHDAY", "出生日期"));
        exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
        exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份", ExcelDataType.Region_Provice));
        exportColumnList.add(new ExcelExportColumn("CITY", " 城市", ExcelDataType.Region_City));
        exportColumnList.add(new ExcelExportColumn("DISTRICT", "区县", ExcelDataType.Region_Country));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("E_MAIL", "邮箱"));
        exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE", "婚姻状况", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("AGE_STAGE", "年龄段", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EDUCATION_LEVEL", "教育水平"));
        exportColumnList.add(new ExcelExportColumn("INDUSTRY_FIRST", "行业大类", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("VOCATION_TYPE", "职业", ExcelDataType.Dict));     
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "职务名称"));        
        exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME", "家庭月收入", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("HOBBY", "爱好",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_FIRST_BUY", "是否首次购车"));
        exportColumnList.add(new ExcelExportColumn("DCRC_SERVICE", "DCRC顾问"));
        exportColumnList.add(new ExcelExportColumn("BUY_PURPOSE", "购车目的"));
        exportColumnList.add(new ExcelExportColumn("BUY_REASON", "购车因素", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型"));
        exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "颜色"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_CUSTOMER", "开票客户"));
        exportColumnList.add(new ExcelExportColumn("ORDER_SUM", "订单总额"));
        exportColumnList.add(new ExcelExportColumn("IS_DIRECT", "是否直销"));
        exportColumnList.add(new ExcelExportColumn("IS_CONSIGNED", "是否委托交车"));
        exportColumnList.add(new ExcelExportColumn("SALES_AGENT_NAME", "经销商"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("LICENSE_DATE", "上牌日期"));
        exportColumnList.add(new ExcelExportColumn("SO_NO", "销售单号"));
        exportColumnList.add(new ExcelExportColumn("IS_REMOVED", "是否移交"));
        exportColumnList.add(new ExcelExportColumn("LAST_SOLY", "前销售顾问"));
     
       
             
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "保有客户.xls", request, response);

    }
}
