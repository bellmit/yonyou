
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AdvancesReceivedManageController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月8日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.basedata;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.repair.service.basedata.AdvancesReceivedManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月8日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/advancesReceivedManage")
public class AdvancesReceivedManageController {
    
    @Autowired
    private ExcelGenerator excelService;
    
    @Autowired
    private AdvancesReceivedManageService advancesReceivedManageService;
    
    /**
     * 含预收款客户查询
     * @author dingchaoyu
     * @date 2016年7月6日
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value="/prepay",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto retrieveHasPrePay(){
         PageInfoDto pageInfoDto = advancesReceivedManageService.retrieveHasPrePay();
         return pageInfoDto;
     }
     
     /**
      * 查询预收款
      * @author dingchaoyu
      * @date 2016年7月6日
      * @param queryParam
      * @return 查询结果
      */
      @RequestMapping(value="/queryCustomer",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto retrieveByCustomer(@RequestParam Map<String, String> map){
          PageInfoDto pageInfoDto = advancesReceivedManageService.retrieveByCustomer(map);
          return pageInfoDto;
      }
      
      /**
       * 查询收款
       * @author dingchaoyu
       * @date 2016年7月6日
       * @param queryParam
       * @return 查询结果
       */
       @RequestMapping(value="/queryByCustomer",method = RequestMethod.GET)
       @ResponseBody
       public PageInfoDto retrieveInsuranceFeeByCustomer(@RequestParam Map<String, String> map){
           PageInfoDto pageInfoDto = advancesReceivedManageService.retrieveInsuranceFeeByCustomer(map);
           return pageInfoDto;
       }
       
       /**
        * 查询车主车辆信息
        * @author dingchaoyu
        * @date 2016年7月6日
        * @param queryParam
        * @return 查询结果
        */
        @RequestMapping(value="/queryByNoOrSpell",method = RequestMethod.GET)
        @ResponseBody
        public PageInfoDto queryByNoOrSpell(@RequestParam Map<String, String> map){
            PageInfoDto pageInfoDto = advancesReceivedManageService.queryByNoOrSpell(map);
            return pageInfoDto;
        }
        
        /**
         * 确定保存
         */
        @RequestMapping(value="/save",method = RequestMethod.PUT)
        public ResponseEntity<String> performExecutes(@RequestBody Map map, UriComponentsBuilder uriCB){
           String s = advancesReceivedManageService.inster(map);
           MultiValueMap<String, String> headers = new HttpHeaders();
           headers.set("Location", uriCB.path("/basedata/advancesReceivedManage/save").buildAndExpand().toUriString());
           return new ResponseEntity<String>(s, headers, HttpStatus.CREATED)  ;
        }
     
     /**
      * 导出
      * @author xukl
      * @date 2016年7月22日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */
          
      @RequestMapping(value = "/export", method = RequestMethod.GET)
      public void exportPartInfos(HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
          List<Map> resultList = advancesReceivedManageService.retrieveHasPrePayExport();
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("含预收款客户", resultList);
          
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","客户代码"));
          exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
          exportColumnList.add(new ExcelExportColumn("ADDRESS","地址"));
          exportColumnList.add(new ExcelExportColumn("ZIP_CODE","邮编"));
          exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE","联系人电话"));
          exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE","联系人手机"));
          exportColumnList.add(new ExcelExportColumn("PRE_PAY","预收款",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_含预收款客户.xls", request, response);

      }
}
