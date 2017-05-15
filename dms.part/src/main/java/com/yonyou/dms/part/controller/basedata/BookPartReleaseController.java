
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : BookPartReleaseController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月24日    dingchaoyu    1.0
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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.BookPartReleaseDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO;
import com.yonyou.dms.part.service.basedata.BookPartReleaseService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月24日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/BookPartRelease")
public class BookPartReleaseController extends BaseController {
    
    @Autowired
    private BookPartReleaseService bookPartReleaseService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 简要描述:根据条件查询配件基础信息
     * @author dingchaoyu
     * @date 2016年7月6日
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value = "/all",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryPartBookingSec(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = bookPartReleaseService.queryPartBookingSec(queryParam);
         return pageInfoDto;
     }
     
     /**
      * 简要描述:预约预留
      * @author dingchaoyu
      * @date 2016年7月6日
      * @param queryParam
      * @return 查询结果
      */
      @RequestMapping(value = "/booking",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto QueryPartObligatedSec(@RequestParam Map<String, String> queryParam) {
          PageInfoDto pageInfoDto = bookPartReleaseService.QueryPartObligatedSec(queryParam);
          return pageInfoDto;
      }
      
      /**
       * 简要描述:工单预留
       * @author dingchaoyu
       * @date 2016年7月6日
       * @param queryParam
       * @return 查询结果
       */
       @RequestMapping(value = "/ro",method = RequestMethod.GET)
       @ResponseBody
       public PageInfoDto QueryPartObligatedSecRo(@RequestParam Map<String, String> queryParam) {
           PageInfoDto pageInfoDto = bookPartReleaseService.QueryPartObligatedSecRo(queryParam);
           return pageInfoDto;
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
            
        @RequestMapping(value = "/export/import", method = RequestMethod.GET)
        public void exportBookPartRelease(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
            List<Map> resultList = bookPartReleaseService.queryPartBookReleaseForExportbooking(queryParam);
            Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
            excelData.put("配件解预留", resultList);
            List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
            exportColumnList.add(new ExcelExportColumn("OBLIGATED_NO","预留单号"));
            exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
            exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
            exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
            exportColumnList.add(new ExcelExportColumn("QUANTITY","预留数量"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
            exportColumnList.add(new ExcelExportColumn("APPLICANT","申请人"));
            exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_ORDER_NO","预约单号"));
            exportColumnList.add(new ExcelExportColumn("SHEET_NO","工单号"));
            exportColumnList.add(new ExcelExportColumn("SO_NO","服务单号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_DATE","预留日期"));
            
            resultList = bookPartReleaseService.queryPartBookReleaseForExportro(queryParam);
            excelData.put("配件解预留工单预留", resultList);
            exportColumnList.add(new ExcelExportColumn("OBLIGATED_NO","预留单号"));
            exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
            exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
            exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
            exportColumnList.add(new ExcelExportColumn("QUANTITY","预留数量"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
            exportColumnList.add(new ExcelExportColumn("APPLICANT","申请人"));
            exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_ORDER_NO","预约单号"));
            exportColumnList.add(new ExcelExportColumn("SHEET_NO","工单号"));
            exportColumnList.add(new ExcelExportColumn("SO_NO","服务单号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_DATE","预留日期"));
            
            resultList = bookPartReleaseService.queryPartBookReleaseForExportall(queryParam);
            excelData.put("配件解预留预约预留", resultList);
            exportColumnList.add(new ExcelExportColumn("OBLIGATED_NO","预留单号"));
            exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
            exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
            exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
            exportColumnList.add(new ExcelExportColumn("QUANTITY","预留数量"));
            exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
            exportColumnList.add(new ExcelExportColumn("APPLICANT","申请人"));
            exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_ORDER_NO","预约单号"));
            exportColumnList.add(new ExcelExportColumn("SHEET_NO","工单号"));
            exportColumnList.add(new ExcelExportColumn("SO_NO","服务单号"));
            exportColumnList.add(new ExcelExportColumn("BOOKING_DATE","预留日期"));
            //生成excel 文件
            excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_bsPartBookReleaseForm.xls", request, response);

        }
        
        @RequestMapping(value = "/release",method = RequestMethod.PUT)
        @ResponseBody
        public ResponseEntity<BookPartReleaseDTO> performExecute(@RequestBody BookPartReleaseDTO bookPartReleaseDTO,UriComponentsBuilder uriCB){
            bookPartReleaseService.performExecute(bookPartReleaseDTO);
            MultiValueMap<String, String> headers = new HttpHeaders();  
            headers.set("Location", uriCB.path("/basedata/BookPartRelease").toUriString());  
            return new ResponseEntity<BookPartReleaseDTO>(headers, HttpStatus.CREATED);
        }
}
