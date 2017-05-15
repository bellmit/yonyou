
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerResoContoller.java
*
* @Author : zhengcong
*
* @Date : 2017年3月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月22日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.basedata;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.domains.DTO.basedata.BusinessCustomerDTO;
import com.yonyou.dms.customer.service.basedata.BusinessCustomerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 业务往来客户资料
 * @author chenwei
 * @date 2017年3月28日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/businessCustomer")
public class BusinessCustomerController extends BaseController {


	@Autowired
	private BusinessCustomerService businessCustomerService;
	
	@Autowired
    private ExcelGenerator        excelService;

	/**
	 * 根据查询条件查询业务往来客户信息
	 * 
	 * @author chenwei
	 * @date 2017年3月28日
	 * @param queryParam 查询条件
	 *            
	 * @return 查询结果
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryBusinessCustomer(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = businessCustomerService.queryBusinessCustomer(queryParam);
		return pageInfoDto;
	}
	

	/**
	 * 新增业务往来客户信息
	 * 
	 * @author chenwei
	 * @date 2017年3月28日
	 */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BusinessCustomerDTO> addBusinessCustomer(@RequestBody BusinessCustomerDTO cudto,UriComponentsBuilder uriCB) throws ServiceBizException{
        businessCustomerService.addBusinessCustomer(cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/findByCustomerCode/{CUSTOMER_CODE}").buildAndExpand(cudto.getCustomerCode()).toUriString());  
        return new ResponseEntity<BusinessCustomerDTO>(cudto,headers, HttpStatus.CREATED);
    }

	
    /**
     * 根据customerCode查询业务往来客户信息
     * @author chenwei
     * @date 2017年3月22日
     */
    @RequestMapping(value = "/findByCustomerCode/{CUSTOMER_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCustomerCode(@PathVariable(value = "CUSTOMER_CODE") String customerCode){
        return businessCustomerService.findByCustomerCode(customerCode);
    }
    
    /**
     * 
     * 提供给客户类别下拉框的方法
     * @author chenwei
     * @date 2017年3月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/customersdict",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> customersSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> customerslist = businessCustomerService.queryCustomersDict(queryParam);
        return customerslist;
    } 

    
    /**
     * 
     * 根据customerCode修改信息
     * @author chenwei
     * @date 2017年3月22日
     */
    	
    @RequestMapping(value = "/modifyByCustomerCode/{customerCode}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<BusinessCustomerDTO> updateBusinessCustomer(@PathVariable String customerCode,@RequestBody BusinessCustomerDTO cudto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
        businessCustomerService.modifyByCustomerCode(customerCode, cudto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/businessCustomer/modifyByCustomerCode/{customerCode}").buildAndExpand(customerCode).toUriString());  
        return new ResponseEntity<BusinessCustomerDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 展厅接待导出
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
    public void exportCustomerRecord(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = businessCustomerService.queryCustomerRecordforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_业务往来客户", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE", "客户代码"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_SHORT_NAME", "客户简称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_SPELL", "客户拼音"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE_NAME", "客户类型名称"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("FAX", "传真"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("PRE_PAY", "预收款"));
        exportColumnList.add(new ExcelExportColumn("BAL_OBJ_CODE", "结算对象代码"));
        exportColumnList.add(new ExcelExportColumn("BAL_OBJ_NAME", "结算对象名称"));
        exportColumnList.add(new ExcelExportColumn("PRICE_ADD_RATE", "加价率"));
        exportColumnList.add(new ExcelExportColumn("AGREEMENT_BEGIN_DATE", "签约开始日期"));
        exportColumnList.add(new ExcelExportColumn("AGREEMENT_END_DATE", "签约结束日期"));
        exportColumnList.add(new ExcelExportColumn("CONTRACT_NO", "合约编号"));
        exportColumnList.add(new ExcelExportColumn("PRICE_RATE", "价格系数"));
        exportColumnList.add(new ExcelExportColumn("SALES_BASE_PRICE_TYPE", "销售基价"));
        exportColumnList.add(new ExcelExportColumn("CREDIT_AMOUNT", "信用(元)"));
        exportColumnList.add(new ExcelExportColumn("ACCOUNT_TERM", "帐期(天)"));
        exportColumnList.add(new ExcelExportColumn("TOTAL_ARREARAGE_AMOUNT", "累计欠款(元)"));
        exportColumnList.add(new ExcelExportColumn("TOTAL_ARREARAGE_TERM", "累计欠款(天)"));
        exportColumnList.add(new ExcelExportColumn("ARREARAGE_AMOUNT", "维修欠款"));
        exportColumnList.add(new ExcelExportColumn("CUS_RECEIVE_SORT", "客户收款类别"));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志"));
        exportColumnList.add(new ExcelExportColumn("ACCOUNT_AGE", "帐龄"));
        exportColumnList.add(new ExcelExportColumn("LEAD_TIME", "订货周期"));
        exportColumnList.add(new ExcelExportColumn("DEALER2_CODE", "经销商"));
        exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否有效"));
        
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_业务往来客户.xls", request, response);

    }
   
}
