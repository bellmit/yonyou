
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AccountPayableManageController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.service.basedata.AccountPayableManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
* TODO description
* @author dingchaoyu
* @date 2017年5月3日
*/
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/basedata/accountPayableManage")
public class AccountPayableManageController {
    
    @Autowired
    private AccountPayableManageService AccountPayableManageService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 
     * @author dingchaoyu
     * @date 2016年7月6日    
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value = "/query",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto retrieveByReceive(@RequestParam Map map) {
         PageInfoDto pageInfoDto = AccountPayableManageService.retrieveByReceive(map);
         return pageInfoDto;
     }
     
     /**
      * 客户
      * @author dingchaoyu
      * @date 2016年7月6日    
      * @param queryParam
      * @return 查询结果
      */
      @RequestMapping(value = "/queryCustomer",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto queryPartCustomer(@RequestParam Map map) {
          PageInfoDto pageInfoDto = AccountPayableManageService.queryPartCustomer(map);
          return pageInfoDto;
      }
     
     /**
      * 明细
      * @author dingchaoyu
      * @date 2017年5月04日
      * @param id
      * @return
      */
          
      @RequestMapping(value = "/detail/{STOCK_IN_NO}/{tag}", method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto getPartInfoById(@PathVariable(value = "STOCK_IN_NO") String id,@PathVariable(value = "tag") String ids) {
        PageInfoDto buyInfo = null;
        if (ids.equals("15880001")) {
            buyInfo = AccountPayableManageService.queryPartsBuyInfo(id);
        }else if (ids.equals("15880012")) {
            buyInfo = AccountPayableManageService.queryPartAllocateInItem(id);
        }else if (ids.equals("15880017")) {
            buyInfo = AccountPayableManageService.queryPartAllocateOutItem(id);
        }
          return buyInfo;
      }
      
      /**
       * 客户
       * @author dingchaoyu
       * @date 2017年5月04日
       * @param id
       * @return
       */
           
       @RequestMapping(value = "/customer", method = RequestMethod.GET)
       @ResponseBody
       public List<Map> queryPartCustomers() {
           return AccountPayableManageService.queryPartCustomers();
       }
       
       /**
        * 保存
        * @author dingchaoyu
        * @date 2016年7月12日
        * @param id
        * @return
        */
        @ResponseStatus(HttpStatus.CREATED)
        @RequestMapping(value = "/update", method = RequestMethod.PUT)
        public void maintainPartPayUpdate(@RequestBody Map map,UriComponentsBuilder uriCB) {
            AccountPayableManageService.maintainPartPayUpdate(map);
        }
       
       /**
        * 明细导出
        * @author xukl
        * @date 2016年7月22日
        * @param queryParam
        * @param request
        * @param response
        * @throws Exception
        */
            
        @RequestMapping(value = "/coustomerExport/{STOCK_IN_NO}/{tag}", method = RequestMethod.GET)
        public void exportPartInfos(@PathVariable(value = "STOCK_IN_NO") String id,@PathVariable(value = "tag") String ids, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
            List<Map> resultList = new ArrayList<>();
            if (ids.equals("15880001")) {
                resultList = AccountPayableManageService.queryPartsBuyInfoExport(id);
            }else if (ids.equals("15880012")) {
                resultList = AccountPayableManageService.queryPartAllocateInItemExport(id);
            }else if (ids.equals("15880017")) {
                resultList = AccountPayableManageService.queryPartAllocateOutItemExport(id);
            }
            Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
            excelData.put("配件明细", resultList);
            
            List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
            exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
            exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
            exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
            exportColumnList.add(new ExcelExportColumn("UNIT_CODE","计量单位"));
            exportColumnList.add(new ExcelExportColumn("IN_QUANTITY","入库数量"));
            exportColumnList.add(new ExcelExportColumn("IN_PRICE","不含税单价"));
            exportColumnList.add(new ExcelExportColumn("IN_AMOUNT","不含税金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
            exportColumnList.add(new ExcelExportColumn("IN_PRICE_TAXED","含税单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
            exportColumnList.add(new ExcelExportColumn("IN_AMOUNT_TAXED","含税金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
            //生成excel 文件
            excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_配件明细.xls", request, response);

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
         public void retrieveByReceiveExport(@RequestParam Map map, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
             List<Map> resultList = AccountPayableManageService.retrieveByReceiveExport(map);
             Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
             excelData.put("应付账款管理", resultList);
             
             List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
             exportColumnList.add(new ExcelExportColumn("STOCK_IN_NO","入库单号"));
             exportColumnList.add(new ExcelExportColumn("FINISHED_DATE","完成/入帐日期"));
             exportColumnList.add(new ExcelExportColumn("CUSTOMER_CODE","客户代码"));
             exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
             exportColumnList.add(new ExcelExportColumn("ITEM_COUNT","项目数"));
             exportColumnList.add(new ExcelExportColumn("TOTAL_AMOUNT","总金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
             exportColumnList.add(new ExcelExportColumn("IS_PAYOFF","是否付讫",ExcelDataType.Dict));
             exportColumnList.add(new ExcelExportColumn("CREDENCE","付款凭证"));
             exportColumnList.add(new ExcelExportColumn("ORDER_REGEDIT_NO","注册号码"));
             exportColumnList.add(new ExcelExportColumn("ARRIVE_DATE","帐款到期日期"));
             exportColumnList.add(new ExcelExportColumn("PAY_OFF_DATE","付讫日期"));
             exportColumnList.add(new ExcelExportColumn("PAY_OFF_TAX","付讫税率"));
             exportColumnList.add(new ExcelExportColumn("PAY_OFF_MAN","付讫对象"));
             exportColumnList.add(new ExcelExportColumn("RECEIVE_REMARK","付款备注"));
             //生成excel 文件
             excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_应付账款管理.xls", request, response);

         }
}
