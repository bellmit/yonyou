
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerManager.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月27日    dingchaoyu    1.0
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerManageDTO;
import com.yonyou.dms.customer.service.customerManage.CustomerManagerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/customerManage/customerManager")
public class CustomerManagerContoller {
    
    @Autowired
    private ExcelGenerator  excelService;
    
    @Autowired
    private CustomerManagerService customerManagerService;
    
    /**
     * 车主
     * 
     * @author dingchaoyu
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(value ="/owen",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryByFixspell(@RequestParam Map<String, String> queryParam) {
        return customerManagerService.queryByFixspell(queryParam);
    }
    
    /**
     * 车主
     * 
     * @author dingchaoyu
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(value ="/owen/{OWNER_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryByFixspells(@PathVariable(value = "OWNER_NO") String id) {
        return customerManagerService.queryByFixspells(id);
    }
    
    /**
     * 车辆
     * 
     * @author dingchaoyu
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(value ="/license",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryByNoOrSpellForPageQuery(@RequestParam Map<String, String> queryParam) {
        return customerManagerService.queryByNoOrSpellForPageQuery(queryParam);
    }
    
    /**
     * 车辆
     * 
     * @author dingchaoyu
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(value ="/license/{OWNER_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryByNoOrSpellForPageQuerys(@PathVariable(value = "OWNER_NO") String id) {
        return customerManagerService.queryByNoOrSpellForPageQuerys(id);
    }
    
    /**
     * 车主导出
     * 
     * @author dingchaoyu
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void queryByFixspellExport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = customerManagerService.queryByFixspellExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车主车辆管理", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
        exportColumnList.add(new ExcelExportColumn("MEMBER_STATUS", "是否是会员",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "手机"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "职业"));     
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "联系人"));        
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CREATED_BY", "创建人"));
        exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
        exportColumnList.add(new ExcelExportColumn("BUY_REASON", "购车因素"));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_车主管理.xls", request, response);

    }
    
    /**
     * 车主导出
     * 
     * @author dingchaoyu
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel/{OWNER_NO}", method = RequestMethod.GET)
    public void queryByFixspellExports(@PathVariable(value = "OWNER_NO") String id, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = customerManagerService.queryByFixspellExports(id);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车主车辆管理", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY", "车主性质"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NAME", "车主"));
        exportColumnList.add(new ExcelExportColumn("MEMBER_STATUS", "是否是会员",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "手机"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮编"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "地址"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "职业"));     
        exportColumnList.add(new ExcelExportColumn("POSITION_NAME", "联系人"));        
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CREATED_BY", "创建人"));
        exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
        exportColumnList.add(new ExcelExportColumn("BUY_REASON", "购车因素"));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_车主管理.xls", request, response);

    }
    
    /**
     * 车辆导出
     * 
     * @author dingchaoyu
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excels", method = RequestMethod.GET)
    public void queryByNoOrSpellForPageQueryExport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = customerManagerService.queryByNoOrSpellForPageQueryExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车主车辆管理", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机"));
        exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
        exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙号"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
        exportColumnList.add(new ExcelExportColumn("CONSULTANT", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE", "建档日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("NO_VALID_REASON", "不激活原因"));
        exportColumnList.add(new ExcelExportColumn("IS_BINDING", "是否绑定微信"));
        exportColumnList.add(new ExcelExportColumn("BINDING_DATE", "微信绑定时间",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("WX_UNBUNDLING_DATE", "微信解绑时间"));     
        exportColumnList.add(new ExcelExportColumn("IS_WX_CONNECT", "是否微信首要联系方式", ExcelDataType.Dict));   
        exportColumnList.add(new ExcelExportColumn("MOBILE_PHONE", "微信首要联系方式"));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_车辆管理.xls", request, response);

    }
    
    /**
     * 车辆导出
     * 
     * @author dingchaoyu
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excels/{OWNER_NO}", method = RequestMethod.GET)
    public void queryByNoOrSpellForPageQueryExports(@PathVariable(value = "OWNER_NO") String id, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = customerManagerService.queryByNoOrSpellForPageQueryExports(id);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车主车辆管理", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OWNER_NO", "车主编号"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机"));
        exportColumnList.add(new ExcelExportColumn("BRAND", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
        exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙号"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER", "送修人"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE", "送修人手机"));
        exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE", "送修人电话"));
        exportColumnList.add(new ExcelExportColumn("CONSULTANT", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("CREATE_BY", "创建人"));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE", "建档日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("NO_VALID_REASON", "不激活原因"));
        exportColumnList.add(new ExcelExportColumn("IS_BINDING", "是否绑定微信"));
        exportColumnList.add(new ExcelExportColumn("BINDING_DATE", "微信绑定时间",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("WX_UNBUNDLING_DATE", "微信解绑时间"));     
        exportColumnList.add(new ExcelExportColumn("IS_WX_CONNECT", "是否微信首要联系方式", ExcelDataType.Dict));   
        exportColumnList.add(new ExcelExportColumn("MOBILE_PHONE", "微信首要联系方式"));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_车辆管理.xls", request, response);

    }
    
    /**
     * 新增车辆
     * @author dingchaoyu
     * @param partInfoDTO
     * @param uriCB
     * @return 新增配件信息
     */
         
     @RequestMapping(value="/insurance/{VIN}",method = RequestMethod.POST)
     public ResponseEntity<CustomerManageDTO> addCustomerManageles(@PathVariable(value = "VIN") String id,@RequestBody CustomerManageDTO customerManageDTO,UriComponentsBuilder uriCB) {
         customerManagerService.addCustomerManageles(id,customerManageDTO);
         MultiValueMap<String,String> headers = new HttpHeaders();  
         headers.set("Location", uriCB.path("/customerManage/customerManager").toUriString());  
         return new ResponseEntity<CustomerManageDTO>(customerManageDTO,headers, HttpStatus.CREATED);  

     }
     
     /**
      * 删除车辆
      * @author dingchaoyu
      * @param partInfoDTO
      * @param uriCB
      * @return 新增配件信息
      */
          
      @RequestMapping(value="/delect/{VIN}",method = RequestMethod.DELETE)
      public ResponseEntity<CustomerManageDTO> delCustomerManageles(@PathVariable(value = "VIN") String id,UriComponentsBuilder uriCB) {
          customerManagerService.queryRepairOrderByDateStand(id);
          customerManagerService.performExecute(id);
          MultiValueMap<String,String> headers = new HttpHeaders();  
          headers.set("Location", uriCB.path("/customerManage/customerManager/delect/{VIN}").toUriString());  
          return new ResponseEntity<CustomerManageDTO>(headers, HttpStatus.CREATED);  

      }
    
}
