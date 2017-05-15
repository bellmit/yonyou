
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : NewStockInController.java
*
* @Author : yll
*
* @Date : 2016年12月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月8日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.vehicle.controller.stockManage;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.vehicle.domains.DTO.basedata.StockInDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInOutDto;
import com.yonyou.dms.vehicle.service.stockManage.NewStockInService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 整车入库控制类
* @author yll
* @date 2016年12月8日
*/

@Controller
@TxnConn
@RequestMapping("/stockManage/StockIn")
public class NewStockInController extends BaseController{
    
    @Autowired
    NewStockInService newStockInService;

    @Autowired
    private ExcelGenerator excelService;

    
    /**
     * 车辆入库查询
     * 
     * @author yll
     * @date 2016年12月6日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleInspect(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = newStockInService.queryStockIn(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据ID查询车辆入库信息
     * 
     * @author yll
     * @date 2016年12月6日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryVehicleInspectById(@PathVariable(value = "id") Long id) {
        Map<String, Object> result = newStockInService.queryStockInById(id);
        return result;
    }

    /**
     * 根据ID查询车辆入库信息(多个)
     * 
     * @author yll
     * @date 2016年12月6日
     * @param id
     * @return
     */

    @RequestMapping(value = "/many/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryVehicleInspectByIds(@PathVariable(value = "id") String id) {
        List<Map> result = newStockInService.queryStockInByIds(id);
        return result;
    }

    /**
     * 
     * 修改,单个入库
     * @author yll
     * @date 2016年12月08日
     * @param id
     * @param masterdatadto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<StockInDTO> ModifyStockIn(@PathVariable("id") Long id,@RequestBody StockInOutDto stockInOutdto,UriComponentsBuilder uriCB) {
        newStockInService.modifyStockIn(id, stockInOutdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/stockManage/StockIn/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<StockInDTO>(headers, HttpStatus.CREATED);  
    }
    /**
     * 
    * 批量入库
    * @author yll
    * @date 2016年12月9日
    * @param stockInListDTO
     */
    @RequestMapping(value="/inbounds",method=RequestMethod.PUT)
    @ResponseBody
    public void updateStatusById(@RequestBody StockInOutDto stockInOutDto){
        newStockInService.inbounds(stockInOutDto);
    }
    /**
     * 
    * 导出
    * @author yll
    * @date 2016年12月11日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportVehicleInspectInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        List<Map> resultList = newStockInService.exportStockInInfo(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆入库", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("IN_DELIVERY_TYPE", "业务类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("IS_IN_OUT_STOCK", "是否入库",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME", "仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "车位"));
        exportColumnList.add(new ExcelExportColumn("IN_OUT_STOCK_NO", "入库单号"));
        exportColumnList.add(new ExcelExportColumn("BUSSINESS_NO", "业务单号"));
        exportColumnList.add(new ExcelExportColumn("BUSSINESS_DATE","单据日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_NAME", "产品名称"));
        excelService.generateExcel(excelData, exportColumnList, "车辆入库.xls", request, response);

    }

}
