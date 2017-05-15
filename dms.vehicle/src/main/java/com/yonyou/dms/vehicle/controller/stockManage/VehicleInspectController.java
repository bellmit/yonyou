
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : VehicleInspectController.java
*
* @Author : zhanshiwei
*
* @Date : 2016年12月6日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月6日    zhanshiwei    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.VehicleInspectDTO;
import com.yonyou.dms.vehicle.service.stockManage.VehicleInspectService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆验收
 * 
 * @author zhanshiwei
 * @date 2016年12月6日
 */
@Controller
@TxnConn
@RequestMapping("/stockManage/vehicleInspect")
public class VehicleInspectController extends BaseController {

    @Autowired
    VehicleInspectService  vehicleinspectservice;
    @Autowired
    private ExcelGenerator excelService;

    /**
     * 车辆验收查询
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleInspect(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = vehicleinspectservice.queryVehicleInspect(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据ID查询车辆验收信息
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryVehicleInspectById(@PathVariable(value = "id") Long id) {
        Map<String, Object> result = vehicleinspectservice.queryVehicleInspectById(id);
        return result;
    }

    /**
     * 验收数据查询
     * 
     * @author zhanshiwei
     * @date 2016年12月6日
     * @param id
     * @return
     */

    @RequestMapping(value = "/InspctionMar/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryVinspectionMar(@PathVariable("id") Long id) {
        List<Map> map = vehicleinspectservice.queryVinspectionMar(id);
        return map;
    }

    /**
     * pdi列表查询
     * 
     * @author yll
     * @date 2016年9月22日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/pdiInspction/{bussinessNo}", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings("rawtypes")
    public List<Map> queryPdiInspection(@PathVariable("bussinessNo") String bussinessNo) {
        List<Map> map = vehicleinspectservice.queryPdiInspection(14121008, bussinessNo);
        return map;
    }

    /**
     * 车辆验收更新保存
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param id
     * @param veInspDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<VehicleInspectDTO> modifyVehicleInspect(@PathVariable("id") Long id,
                                                                  @RequestBody VehicleInspectDTO veInspDto,
                                                                  UriComponentsBuilder uriCB) {
        vehicleinspectservice.modifyVehicleInspect(id, veInspDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockManage/vehicleInspect/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<VehicleInspectDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 车辆验收完成
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param id
     * @param veInspDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/inspectFinish/{id}", method = RequestMethod.PUT)
    public ResponseEntity<VehicleInspectDTO> modifyVehicleInspectFinish(@PathVariable("id") Long id,
                                                                        @RequestBody VehicleInspectDTO veInspDto,
                                                                        UriComponentsBuilder uriCB) {
        veInspDto.setInspectStatus(DictCodeConstants.INSPECTION_RESULT_SUCCESS);
        vehicleinspectservice.modifyVehicleInspect(id, veInspDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/stockManage/vehicleInspect/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<VehicleInspectDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 批量验收
     * 
     * @author zhanshiwei
     * @date 2016年12月8日
     * @param veInspDto
     * @param uriCB
     */

    @RequestMapping(value = "/selInspctionS", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyVehicleInspectFinishSel(@RequestBody VehicleInspectDTO veInspDto, UriComponentsBuilder uriCB) {
        vehicleinspectservice.modifyVehicleInspectFinishSel(veInspDto);
    }

    
    /**
    * 导出
    * @author zhanshiwei
    * @date 2016年12月8日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportVehicleInspectInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        List<Map> resultList = vehicleinspectservice.exportVehicleInspectInfo(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆验收", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("VB_TYPE", "业务类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BUSSINESS_NO", "业务单号"));
        exportColumnList.add(new ExcelExportColumn("BUSSINESS_DATE", "单据日期"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("INSPECT_STATUS", "验收状态",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "验收人员"));
        exportColumnList.add(new ExcelExportColumn("MAR_STATUS", "质损状态",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_IN_OUT_STOCK", "是否入库",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_NAME", "产品名称"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
        excelService.generateExcel(excelData, exportColumnList, "车辆验收.xls", request, response);

    }
}
