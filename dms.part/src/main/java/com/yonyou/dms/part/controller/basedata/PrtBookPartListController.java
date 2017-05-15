
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PrtBookPartListController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月26日    dingchaoyu    1.0
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.part.service.basedata.PreBookPartListService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月26日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/prtBookPartList")
public class PrtBookPartListController {
    @Autowired
    private PreBookPartListService preBookPartListService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 
     * @author dingchaoyu
     * @date 2016年7月6日
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value = "/select",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryPartBookingSec(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = preBookPartListService.queryPartBookingSec(queryParam);
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
          List<Map> resultList = preBookPartListService.queryPreBookPartListExport(queryParam);
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("预约预留情况", resultList);
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
          exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
          exportColumnList.add(new ExcelExportColumn("BOOKING_ORDER_NO","预约单号"));
          exportColumnList.add(new ExcelExportColumn("BOOKING_QUANTITY","预约数量"));
          exportColumnList.add(new ExcelExportColumn("OBLIGATED_MAN","预留人"));
          exportColumnList.add(new ExcelExportColumn("OBLIGATED_DATE","预留日期"));
          exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
          exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
          exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
          exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
          exportColumnList.add(new ExcelExportColumn("USEABLE_STOCK","可用库存"));
        //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_预约预留情况.xls", request, response);

      }
}
