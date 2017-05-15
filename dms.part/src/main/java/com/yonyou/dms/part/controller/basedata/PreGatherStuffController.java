
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PreGatherStuffController.java
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
	
package com.yonyou.dms.part.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.service.basedata.PreGatherStuffService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/preGatherStuff")
public class PreGatherStuffController {
    @Autowired
    private ExcelGenerator excelService;
    
    @Autowired
    private PreGatherStuffService gatherStuffService;
    
    /**
     * 
     * @author dingchaoyu
     * @date 2016年7月6日
     * @param queryParam
     * @return 查询结果
     */
     @RequestMapping(value = "/select",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryPick(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = gatherStuffService.queryPick(queryParam);
         return pageInfoDto;
     }
    
     /**
      * 导出
      * @author dingchaoyu
      * @date 2016年7月22日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */
          
      @RequestMapping(value = "/export/import", method = RequestMethod.GET)
      public void exportPreGatherStuffRelease(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
          List<Map> resultList = gatherStuffService.queryPreGatherStuffExport(queryParam);
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("预先拣料查询", resultList);
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("RO_NO","工单号"));
          exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR","客户经理"));
          exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
          exportColumnList.add(new ExcelExportColumn("OWNER_NO","车主编号"));
          exportColumnList.add(new ExcelExportColumn("OWNER_NAME","车主名称"));
          exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
          exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
          exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
          exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
          exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
          exportColumnList.add(new ExcelExportColumn("PRE_CHECK","是否预拣",ExcelDataType.Dict));
          exportColumnList.add(new ExcelExportColumn("PART_QUANTITY","配件数量"));
          exportColumnList.add(new ExcelExportColumn("PART_SALES_PRICE","配件销售单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("PART_SALES_AMOUNT","预约数量"));
          exportColumnList.add(new ExcelExportColumn("OBLIGATED_MAN","配件销售金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("RECEIVER","领料人"));
          exportColumnList.add(new ExcelExportColumn("VEHICLE_TOP_DESC","车顶号"));
          exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
          exportColumnList.add(new ExcelExportColumn("PRINT_SEND_TIME","发料首次打印时间"));
          exportColumnList.add(new ExcelExportColumn("PRINT_RP_TIME","预拣打印日期"));
        //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_预先拣料查询.xls", request, response);

      }
      
      /**
       * 明细导出
       * @author dingchaoyu
       * @date 2016年7月22日
       * @param queryParam
       * @param request
       * @param response
       * @throws Exception
       */
           
       @RequestMapping(value = "/export/imports", method = RequestMethod.GET)
       public void exportPreGatherStuffDetile(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
           List<Map> resultList = gatherStuffService.queryPreGatherStuffDetailExport(queryParam);
           Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
           excelData.put("预先拣料明细", resultList);
           List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
           exportColumnList.add(new ExcelExportColumn("BATCH_NO","流水号"));
           exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
           exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
           exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
           exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
           exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
           exportColumnList.add(new ExcelExportColumn("PART_QUANTITY","配件数量"));
           exportColumnList.add(new ExcelExportColumn("PART_SALES_PRICE","配件销售单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
           exportColumnList.add(new ExcelExportColumn("PART_SALES_AMOUNT","配件销售金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
           exportColumnList.add(new ExcelExportColumn("PRE_CHECK","是否预拣",ExcelDataType.Dict));
           exportColumnList.add(new ExcelExportColumn("RECEIVER","领料人"));
         //生成excel 文件
           excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_预先拣料明细.xls", request, response);

       }
       
       /**
        * 明细
        * @author xukl
        * @date 2016年7月13日
        * @param id
        * @return
        */
            
        @RequestMapping(value = "/{RO_NO}", method = RequestMethod.GET)
        @ResponseBody
        public PageInfoDto QueryPartPickDetail(@PathVariable(value = "RO_NO") String id) {
            return gatherStuffService.QueryPartPickDetail(id);
        }
}
