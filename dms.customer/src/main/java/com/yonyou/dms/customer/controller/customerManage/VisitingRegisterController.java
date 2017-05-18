
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingRegisterController.java
*
* @Author : Administrator
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    Administrator    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.customer.service.customerManage.VisitingRegisterService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO 展厅流量登记
* @author Administrator
* @date 2016年12月28日
*/
@Controller
@TxnConn
@RequestMapping("/customerManage/visitingRegister")
public class VisitingRegisterController {
    @Autowired
    private VisitingRegisterService visitingregisterservice;

    @Autowired
    private ExcelGenerator        excelService;
    
    /**
     * 展厅接待查询
     * 
     * @author zhanshiwei
     * @date 2016年8月31日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVisitingRecordInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = visitingregisterservice.queryVisitingRecordInfo(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据ID查询展厅接待信息
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param id 展厅接待ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryVisitingRecordInfoByid(@PathVariable(value = "id") long id) {
        VisitingRecordPO visitPo = visitingregisterservice.queryVisitingRecordInfoByid(id);
        return visitPo.toMap();
    }
    /**
     * 展厅接待新增
     * 
     * @author zhnashiwei
     * @date 2016年9月1日
     * @param visitDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<VisitingRecordDTO> addVisitingRecordInfo(@RequestBody  VisitingRecordDTO visitDto,
                                                                   UriComponentsBuilder uriCB) {
        long id = visitingregisterservice.addVisitingRecordInfo(visitDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<VisitingRecordDTO>(visitDto, headers, HttpStatus.CREATED);
    }
    /**
     * 展厅接待修改
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @param visitDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<VisitingRecordDTO> modifyVisitingRecordInfo(@PathVariable("id") Long id,
                                                                      @RequestBody  VisitingRecordDTO visitDto,
                                                                      UriComponentsBuilder uriCB) {
        visitingregisterservice.modifyVisitingRecordInfo(id, visitDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<VisitingRecordDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 删除展厅
     * @author zhanshiwei
     * @date 2016年10月25日
     * @param id
     * @param uriCB
     */
         
     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
     @ResponseStatus(HttpStatus.NO_CONTENT)
     public void deleteVisitIntentInfo(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
         visitingregisterservice.deleteVisitIntentInfo(id);
     }

     /**
      * 展厅接待导出
      * 
      * @author zhanshiwei
      * @date 2016年9月11日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */

     @SuppressWarnings("rawtypes")
     @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
     public void exportVisitingRecord(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
         List<Map> resultList = visitingregisterservice.queryVisitingRecordforExport(queryParam);
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("展厅流量登记", resultList);
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();

         // 生成excel 文件
         exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
         exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称 "));
         exportColumnList.add(new ExcelExportColumn("VISIT_TYPE", "来访方式 ", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("VISIT_TIME", "来访时间 "));
         exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "销售顾问 "));
         exportColumnList.add(new ExcelExportColumn("MOBILE", "手机号"));
         exportColumnList.add(new ExcelExportColumn("INTENT_LEVEL", "意向级别 ", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("MEDIA_TYPE", "信息渠道", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_FIRST_ARRIVE", "是否首次到店", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_SECOND_ARRIVE", "是否二次到店", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("LEAVE_TIME", "离店时间"));
         exportColumnList.add(new ExcelExportColumn("IS_TEST_DRIVE", "试乘试驾", ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("SCENE", "经过情形"));
         exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
         exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
         exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
         exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));
         exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
         exportColumnList.add(new ExcelExportColumn("QUOTED_PRICE", "意向报价 "));
         exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
         excelService.generateExcel(excelData, exportColumnList, "展厅流量登记.xls", request, response);

     }
     
     
     /**
      * 展厅流量登记再分配查询客户
      * 
      * @author 
      * @date 2016年9月11日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */
     @RequestMapping(value = "/customer",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryCustomerInfo(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = visitingregisterservice.queryCustomerInfoByid(queryParam);
         return pageInfoDto;
     }
     
     /**
      * 展厅流量登记再分配
      * 
      * @author 
      * @date 2016年9月11日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */
     @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
     @ResponseStatus(HttpStatus.CREATED)
     public void modifyReRetainCusTrack(@RequestBody VisitingRecordDTO visitDto,
                                            UriComponentsBuilder uriCB) {
         visitingregisterservice.modifySoldBy(visitDto);
     }
     
     
    
}
