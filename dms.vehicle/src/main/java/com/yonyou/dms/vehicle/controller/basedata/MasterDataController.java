
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : MasterDataController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月8日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.controller.basedata;

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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO;
import com.yonyou.dms.vehicle.service.basedata.MasterDataService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 整车产品信息控制类
 * @author DuPengXin
 * @date 2016年9月8日
 */
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/basedata/vehicleMasterDatas")
public class MasterDataController extends BaseController{

    @Autowired
    private MasterDataService masterdataservice;

    @Autowired
    private ExcelGenerator excelService;

    /**
     * 查询
     * @author DuPengXin
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMasterData(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = masterdataservice.QueryMasterData(queryParam);
        
        return pageInfoDto;
    }

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年9月9日
     * @param masterdatadto
     * @param uriCB
     * @return
     */

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MasterDataDTO> addMasterData(@RequestBody MasterDataDTO masterdatadto,UriComponentsBuilder uriCB){
        Long id=masterdataservice.addMasterData(masterdatadto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/vehicleMasterDatas/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<MasterDataDTO>(masterdatadto,headers, HttpStatus.CREATED);  
    }

    /**
     * 修改
     * @author DuPengXin
     * @date 2016年9月9日
     * @param id
     * @param masterdatadto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/master", method = RequestMethod.PUT)
    public ResponseEntity<MasterDataDTO> ModifyMasterData(@RequestBody MasterDataDTO masterdatadto,UriComponentsBuilder uriCB) {
        String id=masterdatadto.getProductCode();
        System.out.println("--------"+masterdatadto.getBrandCode());
        masterdataservice.modifyMasterData(id,masterdatadto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/vehicleMasterDatas/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<MasterDataDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 根据ID查询数据
     * @author DuPengXin
     * @date 2016年9月9日
     * @param id
     * @return
     */

    @SuppressWarnings("unchecked")
	@RequestMapping(value="/noList/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@PathVariable String id){
        List<Map> result= masterdataservice.findById(id);
        return result.get(0);
    }

    /**
     * 批量修改
     * @author DuPengXin
     * @date 2016年9月18日
     * @param masterdatadto
     */

    @RequestMapping(value = "/changeSalesPrice",method = RequestMethod.PUT)
    @ResponseBody
    public void changePrice(@RequestBody MasterDataDTO masterdatadto) {
        masterdataservice.salesPrice(masterdatadto);
    }

    /**
     * 导出
     * @author DuPengXin
     * @date 2016年9月14日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportMasterDatas(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        List<Map> resultList = masterdataservice.queryMasterDataExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("整车产品信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE","产品代码"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_NAME","产品名称"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME","配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM"));
        //其它类型：Region_Provice,Region_City,Region_Country
        exportColumnList.add(new ExcelExportColumn("IS_VALID","是否有效"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_STATUS","产品状态"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_TYPE","产品类别"));
        exportColumnList.add(new ExcelExportColumn("OEM_DIRECTIVE_PRICE","车厂指导价"));
        exportColumnList.add(new ExcelExportColumn("VEHICLE_PRICE","销售指导价"));
        exportColumnList.add(new ExcelExportColumn("WHOLESALE_DIRECTIVE_PRICE","批售指导价"));
        exportColumnList.add(new ExcelExportColumn("ENTER_DATE","上市日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("EXEUNT_DATE","退市日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
        exportColumnList.add(new ExcelExportColumn("YEAR_MODEL","年款"));
        exportColumnList.add(new ExcelExportColumn("EXHAUST_QUANTITY","发动机排量"));
        exportColumnList.add(new ExcelExportColumn("OIL_TYPE","燃油类型"));
        exportColumnList.add(new ExcelExportColumn("IS_MUST_PDI","是否必须PDI"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_整车产品信息.xls", request, response);
    }
}
