
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : RetainCusTrackContoller.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月26日    zhanshiwei    1.0
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

import com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TreatDTO;
import com.yonyou.dms.common.domains.DTO.customer.CustomerTrackingDTO;
import com.yonyou.dms.customer.service.customerManage.RetainCusTrackService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 保有客户跟进
 * 
 * @author zhanshiwei
 * @date 2016年8月26日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/retainCusTracking")
public class RetainCusTrackContoller extends BaseController {

    @Autowired
    private RetainCusTrackService retaincustrackservice;
    @Autowired
    private ExcelGenerator excelService;

    /**
     * 客户跟进查询
     * 
     * @author zhanshiwei
     * @date 2016年8月22日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRetainCusTrackInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = retaincustrackservice.queryRetainCusTrackInfo(queryParam);
        return pageInfoDto;
    }
    
    
    
    @RequestMapping(value = "/vin", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRetainCusTrackInfoByVin(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = retaincustrackservice.queryRetainCusTrackVin(queryParam);
        return pageInfoDto;
    }
    
    @RequestMapping(value = "{CUSTOMER_NO}/link", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryRetainCusTrackInfoByLink(@PathVariable(value = "CUSTOMER_NO") String customerNo) {
        List<Map> linkList = retaincustrackservice.queryRetainCusTrackLink(customerNo);
        return linkList;
    }
    
    @RequestMapping(value = "{ITEM_ID}/cus", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryRetainCusTrackInfoVin(@PathVariable(value = "ITEM_ID") String itemId) {
        Map<String, Object> linkList = retaincustrackservice.queryRetainCusTrackInfoVin(itemId);
        return linkList;
    }
    
    @RequestMapping(value = "{VIN}/treat/{CUSTOMER_NO}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryRetainCusTrackInfoVinCus(@PathVariable(value = "VIN") String vin,@PathVariable(value = "CUSTOMER_NO") String customerNo) {
        String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        List<Map> linkList = retaincustrackservice.queryOwnerCusByTreat(customerNo,vin,dealerCode);
        return linkList;
    }
   //市场活动 
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "campaign", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRetainCusTrackInfoCampaign() {
        String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        PageInfoDto pageInfoDto = retaincustrackservice.queryRetainCusTrackInfoCampaign(dealerCode);
        return pageInfoDto;
    }

    /**
     * 保有客户跟进根据ID查询
     * 
     * @author zhanshiwei
     * @date 2016年8月23日
     * @param tracking_id
     * @return
     */

    @RequestMapping(value = "/{tracking_id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryRetainCusTrackInfoByid(@PathVariable(value = "tracking_id") Long tracking_id) {
        Map<String, Object> cusTraMap = retaincustrackservice.queryRetainCusTrackInfoByid(tracking_id);
        return cusTraMap;
    }
  
    /**
     * 保有客户跟进新增
     * 
     * @author zhanshiwei
     * @date 2016年8月23日
     * @param traDto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addRetainCusTrackInfo(@RequestBody TreatDTO traDto,
                                                                     UriComponentsBuilder uriCB) {

        Long tracking_id = retaincustrackservice.addRetainCusTrackInfo(traDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",
                    uriCB.path("/customerManage/retainCusTracking/").buildAndExpand(tracking_id).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }

    /**
     * 保有客户跟进修改
     * 
     * @author zhanshiwei
     * @date 2016年8月23日
     * @param tracking_id
     * @param traDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{ITEM_ID}/id", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CustomerTrackingDTO> modifyCustomerTrackingInfo(@PathVariable("ITEM_ID") Long itemId,
                                                                          @RequestBody TreatDTO traDto,
                                                                          UriComponentsBuilder uriCB) {

        retaincustrackservice.modifyRetainCusTrackInfo(itemId, traDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",
                    uriCB.path("/customerManage/retainCusTracking/{ITEM_ID}/id").buildAndExpand(itemId).toUriString());
        return new ResponseEntity<CustomerTrackingDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 根据ID删除保有客户跟进
     * 
     * @author zhanshiwei
     * @date 2016年8月23日
     * @param tracking_id
     * @param uriCB
     */

    @RequestMapping(value = "/{ITEM_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRetainCusTrackInfo(@PathVariable("ITEM_ID") Long itemId, UriComponentsBuilder uriCB) {
        retaincustrackservice.deleteRetainCusTrackInfo(itemId);
    }
    
    /**
    * 保有客户跟进重新分配
    * @author zhanshiweis
    * @date 2016年8月23日
    * @param traDto
    * @param uriCB
    */
        
    @RequestMapping(value = "/redistribution", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody CustomerTrackingDTO traDto,
                                           UriComponentsBuilder uriCB) {
        retaincustrackservice.modifyReRetainCusTrack(traDto);
    }
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = retaincustrackservice.queryRetainCustrackforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("关怀信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CR_LINKER", "联系人"));   
        exportColumnList.add(new ExcelExportColumn("LINK_PHONE", "联系电话"));

        exportColumnList.add(new ExcelExportColumn("LINK_MOBILE", "联系手机"));
        exportColumnList.add(new ExcelExportColumn("SCHEDULE_DATE", "计划日期"));
        exportColumnList.add(new ExcelExportColumn("ACTION_DATE", "执行日期"));       
        exportColumnList.add(new ExcelExportColumn("CR_NAME", "关怀名称"));
        exportColumnList.add(new ExcelExportColumn("CR_TYPE", "关怀方式", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CR_RESULT", "关怀结果", ExcelDataType.Dict));
      
        exportColumnList.add(new ExcelExportColumn("CR_SCENE", "访问经过"));        
        exportColumnList.add(new ExcelExportColumn("SOLD_BY", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("CR_CONTEXT", "关怀内容"));
    
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "保有客户关怀.xls", request, response);

    }
    
    /**
     * 延迟再分配
     * 
     * @author LGQ
     * @date 2017年03月20日
     * @param OwnerCusDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack1(@RequestBody CustomerTrackingDTO DTO,
                                           UriComponentsBuilder uriCB) {
        retaincustrackservice.modifySoldBy(DTO);
    }
    
}
