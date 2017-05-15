
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartABCDController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月17日    dingchaoyu    1.0
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
import javax.validation.Valid;

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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.part.service.basedata.PartABCDService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO 配件ABCD分析
* @author dingchaoyu
* @date 2017年4月17日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/partABCD")
public class PartABCDController extends BaseController {
    
    @Autowired
    private PartABCDService abcdService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 
    * TODO 查询
    * @author dingchaoyu
    * @date 2017年4月18日
    * @param map
    * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfos(@RequestParam Map map) {
        PageInfoDto pageInfoDto = abcdService.findAll(map);
        return pageInfoDto;
    }
    
    /**
     * 根据id修改
     * @author dingchaoyu
     * @date 2016年7月12日
     * @param id
     * @return
     */
     @ResponseStatus(HttpStatus.CREATED)
     @RequestMapping(value = "/{PART_NO}", method = RequestMethod.PUT)
     public void ModifyPartInfo(@PathVariable("PART_NO") String id,UriComponentsBuilder uriCB) {
         abcdService.findByid(id);
     }
     
     /**
     * 批量修改
     * @author dingchaoyu
     * @date 2016年7月12日
     * @param id
     * @return
     */
     @ResponseStatus(HttpStatus.CREATED)
     @RequestMapping(value = "/update", method = RequestMethod.PUT)
     public void ModifyPartInfo(@RequestBody Map map,UriComponentsBuilder uriCB) {
         abcdService.updateAll(map);
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
      public void exportPartInfos(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
          List<Map> resultList = abcdService.queryPartInfoForExport(queryParam);
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("配件基本信息", resultList);
          
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("ABC_TYPE","现ABCD属性"));
          exportColumnList.add(new ExcelExportColumn("CALC_ABC_TYPE","ABCD属性"));
          exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
          exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
          exportColumnList.add(new ExcelExportColumn("SALES_PRICE","销售单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("PART_OUT_COUNT","销售数量"));
          exportColumnList.add(new ExcelExportColumn("PART_OUT_AMOUNT","销售金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("ADD_PART_AMOUNT","累计金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("ADD_PART_AMOUNT_RATE","累计金额比率"));
          exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          exportColumnList.add(new ExcelExportColumn("PART_MODEL_GROUP_CODE_SET","配件车型组"));
          exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
          //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_配件ABCD金额分析.xls", request, response);

      }
}
