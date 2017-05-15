
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VehicleContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    zhanshiwei    1.0
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
import javax.validation.Valid;

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

import com.yonyou.dms.common.domains.DTO.customer.RetainCustomersDTO;
import com.yonyou.dms.customer.service.customerManage.VehicleService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆信息
 * 
 * @author zhanshiwei
 * @date 2016年8月9日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/vehicle")
@SuppressWarnings("rawtypes")
public class VehicleContoller extends BaseController {

    @Autowired
    private VehicleService  vehicleservice;
    @Autowired
    private ExcelGenerator  excelService;
    @Autowired
    private CommonNoService commonNoService;

    /**
     * 根据查询条件查询车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCarownerInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = vehicleservice.queryVehicleInfo(queryParam);
        return pageInfoDto;
    }

    /**
     * 车主车辆信息查询(选择车主车辆信息时用)
     * 
     * @author zhanshiwei
     * @date 2016年8月16日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/ownerVehicles/selectInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleAndOwnerInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = vehicleservice.queryVehicleAndOwnerInfo(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据ID查询车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param vehicle_id
     * @return
     */

    @RequestMapping(value = "/{vehicle_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryCarownerInfoByid(@PathVariable(value = "vehicle_id") Long vehicle_id) {
        Map<String, Object> tepomap = vehicleservice.queryVehicleInfoByid(vehicle_id);
        return tepomap;
    }

    /**
     * 新增车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param velDto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addVehicleInfo(@RequestBody @Valid RetainCustomersDTO tetainDto,
                                                              UriComponentsBuilder uriCB) {

        Long vehicle_id = vehicleservice.addVehicleInfo(tetainDto,
                                                        commonNoService.getSystemOrderNo(CommonConstants.OWNER_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",
                    uriCB.path("/customerManage/vehicle/{vehicle_id}").buildAndExpand(vehicle_id).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }

    /**
     * 修改车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月11日
     * @param vehicle_id
     * @param tetainDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{vehicle_id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> modifyVehicleInfo(@PathVariable("vehicle_id") Long vehicle_id,
                                                                 @RequestBody @Valid RetainCustomersDTO tetainDto,
                                                                 UriComponentsBuilder uriCB) {
        vehicleservice.modifyVehicleInfo(vehicle_id, tetainDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",
                    uriCB.path("/customerManage/carowner/{vehicle_id}").buildAndExpand(vehicle_id).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }

    /**
     * 导出
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportVehicleInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        List<Map> resultList = vehicleservice.queryVehicleInfoforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆信息", resultList);

        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN号"));
        exportColumnList.add(new ExcelExportColumn("LICENSE", "车牌号 "));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人姓名 "));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_GENDER", "联系人性别", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PROVINCE", "联系人省份", ExcelDataType.Region_Provice));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_CITY", "联系人城市", ExcelDataType.Region_City));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_DISTRICT", "联系人区县", ExcelDataType.Region_Country));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_ADDRESS", "联系人地址 "));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_ZIP_CODE", "联系人邮编"));
        exportColumnList.add(new ExcelExportColumn("REMARK2", "备注"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_DATE", "制造日期"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR", "颜色"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
        exportColumnList.add(new ExcelExportColumn("SERVICE_ADVISOR", "指定服专"));
        exportColumnList.add(new ExcelExportColumn("DCRC_ADVISOR", "DCRC专员"));
        exportColumnList.add(new ExcelExportColumn("INSURANCE_ADVISOR", "续保专员"));
        exportColumnList.add(new ExcelExportColumn("VEHICLE_PURPOSE", "车辆用途",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("INNER_COLOR", "内饰色"));
        exportColumnList.add(new ExcelExportColumn("GEAR_NO", "变速箱号"));
        exportColumnList.add(new ExcelExportColumn("KEY_NO", "钥匙号"));
        exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA", "产地"));
        exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD", "排放标准", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CONSULTANT", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期"));
        exportColumnList.add(new ExcelExportColumn("LICENSE_DATE", "上牌日期"));
        exportColumnList.add(new ExcelExportColumn("IS_SELF_COMPANY", "是否本公司购车", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("MILEAGE", "行驶里程"));
        exportColumnList.add(new ExcelExportColumn("CHANGE_DATE", "换表日期"));
        exportColumnList.add(new ExcelExportColumn("DAILY_AVERAGE_MILEAGE", "日平均里程"));
        exportColumnList.add(new ExcelExportColumn("WRT_BEGIN_DATE", "保修起始日期"));
        exportColumnList.add(new ExcelExportColumn("WRT_END_DATE", "保修结束日期"));
        exportColumnList.add(new ExcelExportColumn("WRT_BEGIN_MILEAGE", "保修起始里程"));
        exportColumnList.add(new ExcelExportColumn("WRT_END_MILEAGE", "保修结束里程"));
        exportColumnList.add(new ExcelExportColumn("FIRST_IN_DATE", "首次进厂日期"));
        exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_DATE", "上次维修日期"));

        exportColumnList.add(new ExcelExportColumn("LAST_MAINTAIN_MILEAGE", "上次维修里程"));
        exportColumnList.add(new ExcelExportColumn("LAST_MAINTENANCE_DATE", "上次保养日期"));

        exportColumnList.add(new ExcelExportColumn("LAST_MAINTENANCE_MILEAGE", "上次保养里程"));
        exportColumnList.add(new ExcelExportColumn("NEXT_MAINTENANCE_DATE", "下次保养日期"));
        exportColumnList.add(new ExcelExportColumn("NEXT_MAINTAIN_MILEAGE", "下次保养里程"));

        excelService.generateExcel(excelData, exportColumnList, "车辆信息.xls", request, response);

    }

    /**
     * 根据车牌，姓名，手机号,vin查询车辆信息
     * 
     * @author ZhengHe
     * @date 2016年9月12日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/query/{params}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryCarownerInfoByParams(@PathVariable("params") String params) {
        List<Map> vehicleList = vehicleservice.queryVehicleInfoByParams(params);
        return vehicleList;
    }

    /**
     * 客户接待选择车辆
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/queryRepairCar/selectInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCarownerInfoBy(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = vehicleservice.queryVehicleInfoForRepair(queryParam);
        return pageInfoDto;
    }
}
