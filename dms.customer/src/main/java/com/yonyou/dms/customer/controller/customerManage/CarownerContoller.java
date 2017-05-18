
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CarownerContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月8日    zhanshiwei    1.0
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

import com.yonyou.dms.common.domains.DTO.stockmanage.CarownerDTO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.customer.service.customerManage.CarownerService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车主信息维护
 * 
 * @author zhanshiwei
 * @date 2016年8月8日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/carowner")
public class CarownerContoller extends BaseController {

    @Autowired
    private CarownerService carownerService;
    @Autowired
    private ExcelGenerator  excelService;
    @Autowired
    private CommonNoService  commonNoService;

    /**
     * 根据查询条件查询车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCarownerInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = carownerService.queryCarownerInfo(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 车辆车主选择查询
    * @author zhanshiwei
    * @date 2016年8月18日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value="/ownerInfo/ownerSelect", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCarownerSelectInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = carownerService.queryCarownerSelectInfo(queryParam);
        return pageInfoDto;
    }
    /**
    * 查询车主信息
    * @author jcsi
    * @date 2016年8月11日
    * @param queryParam
    * @return
     */
    @RequestMapping(value="/ownerselect/sales",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto selectCarownerInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = carownerService.queryCarowner(queryParam);
        return pageInfoDto;
    }
    /**  
    * 查询家庭成员信息
    * @author jcsi
    * @date 2016年8月11日
    * @param queryParam
    * @return
     */
    @RequestMapping(value="/ownerInfo/family",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto selectCarownerInfoFamily(@RequestParam Map<String, String> queryParam) {
    	String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        PageInfoDto pageInfoDto = carownerService.queryCarownerFamily(queryParam,dealerCode);
        return pageInfoDto;
    }

    /**
     * 新增车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param ownDto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<CarownerDTO> addcarownerInfo(@RequestBody CarownerDTO ownDto, UriComponentsBuilder uriCB) {
        Long id = carownerService.addcarownerInfo(ownDto,commonNoService.getSystemOrderNo(CommonConstants.OWNER_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/carowner/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<CarownerDTO>(ownDto, headers, HttpStatus.CREATED);
    }

    /**
     * 根据ID查询车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getcarownerInfobyid(@PathVariable(value = "id") Long id) {
        CarownerPO ownpto = carownerService.customerResoInfoByid(id);
        return ownpto.toMap();
    }

    /**
     * 修改车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param id
     * @param ownDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CarownerDTO> modifycarownerInfo(@PathVariable("id") Long id, @RequestBody CarownerDTO ownDto,
                                                          UriComponentsBuilder uriCB) {
        carownerService.modifycarownerInfo(id, ownDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/carowner/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<CarownerDTO>(headers, HttpStatus.CREATED);
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

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = carownerService.queryCarownerInfoforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车主信息", resultList);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("OWNER_NO","车主编号"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NAME","车主名称"));
        exportColumnList.add(new ExcelExportColumn("PHONE","电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE","手机"));
        exportColumnList.add(new ExcelExportColumn("OWNER_PROPERTY","车主性质",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("GENDER","车主性别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CT_CODE","证件类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO","证件号码"));
        exportColumnList.add(new ExcelExportColumn("PROVINCE","省份",ExcelDataType.Region_Provice));
        exportColumnList.add(new ExcelExportColumn("CITY","城市",ExcelDataType.Region_City));
        exportColumnList.add(new ExcelExportColumn("DISTRICT","区县",ExcelDataType.Region_Country));
        exportColumnList.add(new ExcelExportColumn("ZIP_CODE","邮编"));
        exportColumnList.add(new ExcelExportColumn("ADDRESS","地址"));
        exportColumnList.add(new ExcelExportColumn("E_MAIL","邮箱"));
        exportColumnList.add(new ExcelExportColumn("BIRTHDAY","生日"));
        exportColumnList.add(new ExcelExportColumn("FAMILY_INCOME","月收入",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("OWNER_MARRIAGE","婚姻状况",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("EDU_LEVEL","学历",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PRE_PAY","预收款"));
        exportColumnList.add(new ExcelExportColumn("ARREARAGE_AMOUNT","欠款金额"));
        exportColumnList.add(new ExcelExportColumn("HOBBY","爱好"));
        exportColumnList.add(new ExcelExportColumn("TAX_NO","税号"));
        exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
        excelService.generateExcel(excelData, exportColumnList, "车主信息.xls", request, response);

    }
}
