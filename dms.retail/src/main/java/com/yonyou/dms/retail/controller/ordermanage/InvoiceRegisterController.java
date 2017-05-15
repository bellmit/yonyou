
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : InvoiceRegisterController.java
*
* @Author : DuPengXin
*
* @Date : 2016年9月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月28日    DuPengXin   1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;

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

import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS095;
import com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO;
import com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 开票登记控制类
* @author DuPengXin
* @date 2016年9月28日
*/
@Controller
@TxnConn
@RequestMapping("/ordermanage/invoiceregisters")
public class InvoiceRegisterController extends BaseController{
    
    @Autowired
    private InvoiceRegisterService invoiceregisterservice;
    @Autowired
    private ExcelGenerator excelService; 
    @Autowired
    private SADMS095 sadms095;
    
    
    /**
    * 查询
    * @author DuPengXin
    * @date 2016年9月28日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryInvoiceRegister(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = invoiceregisterservice.queryInvoiceRegister(queryParam);
        return pageInfoDto;
    }
    /**
     * 开票登记选销售订单
     * @author DuPengXin
     * @date 2016年10月9日
     * @param queryParam
     * @return
     */
         
     @RequestMapping(value = "/registers/salesOrderSel/set", method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto qrySalesOrderRegisterSet(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = invoiceregisterservice.qrySRSForInvoiceSet(queryParam);
         return pageInfoDto;
     }
     
     /**
      * 零售变更查询订单
      * @author DuPengXin
      * @date 2016年10月9日
      * @param queryParam
      * @return
      */  
     @RequestMapping(value = "/registers/salesOrderSel/setrefund", method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto qrySalesOrderRegisterSetrefund(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = invoiceregisterservice.qrySRSForInvoiceSetrefund(queryParam);
         return pageInfoDto;
     }
    
    /**
    * 新增
    * @author DuPengXin
    * @date 2016年9月29日
    * @param irdto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<InvoiceRegisterDTO> addInvoiceRegister(@RequestBody InvoiceRegisterDTO irdto,UriComponentsBuilder uriCB){
        String soNo = invoiceregisterservice.addInvoiceRegister(irdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
   //     headers.set("Location", uriCB.path("/ordermanage/invoiceregisters/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<InvoiceRegisterDTO>(irdto,headers, HttpStatus.CREATED);  
    }
    
    
    @RequestMapping(value = "/setrefund",method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<InvoiceRefundDTO> addInvoiceRefund(@RequestBody InvoiceRefundDTO irdto,UriComponentsBuilder uriCB){
        String soNo = invoiceregisterservice.addInvoiceRefund(irdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
     //   LinkedList<SADMS095Dto> sadms095dto= sadms095.getSADMS095(irdto);
   //     headers.set("Location", uriCB.path("/ordermanage/invoiceregisters/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<InvoiceRefundDTO>(irdto,headers, HttpStatus.CREATED);  
    }
    
    
    /**
    * 修改
    * @author DuPengXin
    * @date 2016年9月29日
    * @param id
    * @param irdto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<InvoiceRegisterDTO> updateInvoiceRegister(@PathVariable("id") Long id, @RequestBody InvoiceRegisterDTO irdto,
                                                UriComponentsBuilder uriCB) {
        invoiceregisterservice.updateInvoiceRegister(id, irdto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/ordermanage/invoiceregisters/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<InvoiceRegisterDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 修改
     * @author DuPengXin
     * @date 2016年9月29日
     * @param id
     * @param irdto
     * @param uriCB
     * @return
     */
         
     @RequestMapping(value = "/invoice/{id}", method = RequestMethod.PUT)
     public ResponseEntity<InvoiceRegisterDTO> updateInvoiceRegisterInvoice(@PathVariable("id") Long id, @RequestBody InvoiceRegisterDTO irdto,
                                                 UriComponentsBuilder uriCB) {
         
         invoiceregisterservice.updateInvoiceRegisterInvoice(id, irdto);
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/ordermanage/invoiceregisters/{id}").buildAndExpand(id).toUriString());
         return new ResponseEntity<InvoiceRegisterDTO>(headers, HttpStatus.CREATED);
     }
    
    
    /**
    * 根据ID查询
    * @author DuPengXin
    * @date 2016年9月29日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map findById(@PathVariable("id") Long id) {
        Map md= invoiceregisterservice.findById(id);
        return md;
    }
    
    /**
    * 根据ID查询
    * @author DuPengXin
    * @date 2016年9月29日
    * @param id
    * @return
    */
        
    @RequestMapping(value = "/invoice/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map findByIdInvoice(@PathVariable("id") Long id) {
        Map md= invoiceregisterservice.findById(id);
        return md;
    }
    
    @RequestMapping(value = "/wx", method = RequestMethod.GET)
    @ResponseBody
    public Map qrySalesOrderRegisterWx(@RequestParam Map<String, String> queryParam) {
        Map md= invoiceregisterservice.qrySalesOrderRegisterWx(queryParam);
        System.out.println("____________________________");
        System.out.println(queryParam.get("vin").toString());
        System.out.println("____________________________2");
        System.out.println(queryParam.get("soNo").toString());
        String msg = invoiceregisterservice.searchVehicleSum(queryParam.get("vin").toString(),queryParam.get("soNo").toString());
        Map<String, String> ms =new HashMap();
        if (!StringUtils.isNullOrEmpty(md)&&md.size()>0){
            ms.put("A", "12781001") ;
            ms.put("B", msg);
        }else{
            ms.put("B", msg);
            ms.put("A", "12781002");
        }
        return ms;
    }
    
    
    /**
    * 作废
    * @author DuPengXin
    * @date 2016年10月10日
    * @param id
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{id}/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<InvoiceRegisterDTO> cancelInvoiceRegister(@PathVariable("id") Long id,UriComponentsBuilder uriCB) {
        invoiceregisterservice.cancelInvoiceRegister(id);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/invoiceregisters/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<InvoiceRegisterDTO>(headers, HttpStatus.CREATED); 
    }
    @RequestMapping(value="/export/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =invoiceregisterservice.queryRetainCustrackforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_大客户报备", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SO_NO","报备单号"));
        exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO","官网订单编号"));
        exportColumnList.add(new ExcelExportColumn("SO_STATUS","单据状态", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_CUSTOMER","开票客户"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_WRITER","开票人员"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_NO","发票编号"));
        exportColumnList.add(new ExcelExportColumn("FEE_VEHICLE_RESCAN_REMAINING","发票补扫剩余次数"));
        exportColumnList.add(new ExcelExportColumn("IDENTIFY_STATUS","扫描识别状态", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("RETIAL_CHANGE_TIMES","零售变更次数"));
        exportColumnList.add(new ExcelExportColumn("RETIAL_DATE","零售变更时间"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_TYPE_CODE","发票类型"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_CHARGE_TYPE","费用类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("INVOICE_AMOUNT","发票金额"));
        exportColumnList.add(new ExcelExportColumn("TRANSACTOR","经办人"));
        exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
        exportColumnList.add(new ExcelExportColumn("CT_CODE","证件类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO","证件号码"));
        exportColumnList.add(new ExcelExportColumn("RECORDER","登记人"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_DATE","开票日期"));
        exportColumnList.add(new ExcelExportColumn("RECORD_DATE","登记日期"));
        exportColumnList.add(new ExcelExportColumn("FILE_URL","发票URL"));
       
     
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_开票登记.xls", request, response);
    }
}
