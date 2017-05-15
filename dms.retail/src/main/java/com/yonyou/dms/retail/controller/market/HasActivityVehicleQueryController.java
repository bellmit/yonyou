
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : HasActivityVehicleQueryController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.market;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.service.market.HasActivityVehicleQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月15日
*/
@Controller
@TxnConn
@RequestMapping("/market/hasActivityVehicleQuery")
@SuppressWarnings("rawtypes")
public class HasActivityVehicleQueryController {
    
    @Autowired
    private HasActivityVehicleQueryService activityVehicleQueryService;
        
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 查询
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map map) {
        return activityVehicleQueryService.query(map);
    }
    
    /**
     * 查询明细
     * @author dingchaoyu
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/queryDetail",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDetail(@RequestParam Map map) {
        return activityVehicleQueryService.queryDetail(map);
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
     public void queryExport(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
         List<Map> resultList = activityVehicleQueryService.queryExport(queryParam);
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("参与活动车辆", resultList);
         
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
         exportColumnList.add(new ExcelExportColumn("ACTIVITY_CODE","活动编号"));
         exportColumnList.add(new ExcelExportColumn("ACTIVITY_NAME","活动名称"));
         exportColumnList.add(new ExcelExportColumn("BEGIN_DATE","开始日期"));
         exportColumnList.add(new ExcelExportColumn("END_DATE","结束日期"));
         exportColumnList.add(new ExcelExportColumn("IS_ACTIVITY_STATUS","活动状态"));
         exportColumnList.add(new ExcelExportColumn("RELEASE_DATE","发布日期"));
         exportColumnList.add(new ExcelExportColumn("LABOUR_AMOUNT","工时费",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("REPAIR_PART_AMOUNT","维修材料费",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("ACTIVITY_AMOUNT","活动总金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("IS_PART_ACTIVITY","是否配件活动",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("DOWN_TAG","是否下发",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("DUTY_CAR_SUM","责任车辆数"));
         exportColumnList.add(new ExcelExportColumn("ACHIEVE_CAR_SUM","完成车辆数"));
         exportColumnList.add(new ExcelExportColumn("DUTY_RATE","责任完成率"));
         exportColumnList.add(new ExcelExportColumn("ALL_CAR_SUM","总活动车辆数"));
         exportColumnList.add(new ExcelExportColumn("ACHIEVE_RATE","总完成率"));
         //生成excel 文件
         excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_参与活动车辆.xls", request, response);

     }
     
     /**
      * 
      * @author
      * @param id
      * @throws ServiceBizException
      */
     @RequestMapping(value = "/{VIN}", method = RequestMethod.GET)
     @ResponseBody
     public Map<String, Object> queryOwnerById(@PathVariable(value = "VIN") String id) throws ServiceBizException {
         List<Map> result = activityVehicleQueryService.queryOwnerNoByid(id);
         return result.get(0);
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
          
      @RequestMapping(value = "/queryDetailexport", method = RequestMethod.GET)
      public void exportPartInfos(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
          List<Map> resultList = activityVehicleQueryService.queryDetailExport(queryParam);
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("车辆", resultList);
          
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("OWNER_NO","车主编号"));
          exportColumnList.add(new ExcelExportColumn("LICENSE","车主编号"));
          exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
          exportColumnList.add(new ExcelExportColumn("ENGINE_NO","发动机"));
          exportColumnList.add(new ExcelExportColumn("BRAND","品牌"));
          exportColumnList.add(new ExcelExportColumn("SERIES","车系"));
          exportColumnList.add(new ExcelExportColumn("MODEL","车型"));
          exportColumnList.add(new ExcelExportColumn("KEY_NUMBER","钥匙号"));
          exportColumnList.add(new ExcelExportColumn("DELIVERER","送修人"));
          exportColumnList.add(new ExcelExportColumn("DELIVERER_MOBILE","送修人手机"));
          exportColumnList.add(new ExcelExportColumn("DELIVERER_PHONE","送修人电话"));
          exportColumnList.add(new ExcelExportColumn("CONSULTANT","销售顾问"));
          exportColumnList.add(new ExcelExportColumn("CREATED_BY","创建人"));
          exportColumnList.add(new ExcelExportColumn("FOUND_DATE","建档日期"));
          exportColumnList.add(new ExcelExportColumn("RO_CREATE_DATE","参加活动日期"));
          //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_车辆.xls", request, response);

      }
}
