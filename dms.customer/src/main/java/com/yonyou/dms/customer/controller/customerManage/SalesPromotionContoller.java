
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesPromotionContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月12日    zhanshiwei    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPromotionPlanDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.customer.service.customerManage.SalesPromotionService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 潜客跟进
 * 
 * @author zhanshiwei
 * @date 2016年9月12日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/salesPromotion")
@SuppressWarnings("rawtypes")
public class SalesPromotionContoller extends BaseController {

    @Autowired
    private SalesPromotionService salespromotionservice;
    
    @Autowired
    private ExcelGenerator      excelService;

    
    /**
    * 潜客跟进信息查询
    * @author LGQ
    * @date 2017年1月12日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/follow/tab1",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesPromotionInfo(@RequestParam Map<String, String> queryParam) {
        System.out.println("flag"+12781001);
        int flag=12781001;
        PageInfoDto pageInfoDto = salespromotionservice.querySalesPromotionInfo(queryParam,flag);
        return pageInfoDto;
    }
    
    /**
     * 潜客跟进信息查询
     * @author LGQ
     * @date 2017年1月12日
     * @param queryParam
     * @return
     */
         
     @RequestMapping(value = "/follow/tab2",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto querySalesPromotionInfoAudit(@RequestParam Map<String, String> queryParam) {
         System.out.println("flag"+12781002);
         int flag=12781002;
         PageInfoDto pageInfoDto = salespromotionservice.querySalesPromotionInfo(queryParam,flag);
         return pageInfoDto;
     }
    /**
     * 再分配
     * @author LGQ
     * @date 2017年1月12日
     * @param salesProDto
     */
    @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody TtSalesPromotionPlanDTO salesProDto,
                                           UriComponentsBuilder uriCB) {
        salespromotionservice.modifySoldBy(salesProDto);
    }
    /**
     * 审核
     * @author LGQ
     * @date 2017年1月12日
     * @param salesProDto
     */
    @RequestMapping(value = "/audit/auditList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void auditSalesPromotionPlan(@RequestBody TtSalesPromotionPlanDTO salesProDto,
                                           UriComponentsBuilder uriCB) {
        salespromotionservice.auditSalesPromotionPlan(salesProDto);
    }
    
    /**
     * 潜客跟进信息查询
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param queryParam
     * @return
     */
         
     @RequestMapping(value = "/history/{id}",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryBigCustomerHistoryIntent(@PathVariable(value = "id") String id) {
         PageInfoDto pageInfoDto = salespromotionservice.queryBigCustomerHistoryIntent(id);
         return pageInfoDto;
     }

    
    /**
    * 潜客跟进信息新增本
    * @author zhanshiwei
    * @date 2016年9月12日
    * @param salesProDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addSalesPromotionInfo(@RequestBody  TtSalesPromotionPlanDTO salesProDto,
                                                                     UriComponentsBuilder uriCB) {
        Long id = salespromotionservice.addSalesPromotionInfo(salesProDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/salesPromotion/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 接待(展厅接待)
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param salesProDto
     * @param uriCB
     * @return
     */
     	
     @RequestMapping(value="/{itemId}/promotion",method = RequestMethod.POST)
     @ResponseBody
     public ResponseEntity<Map<String, Object>> addSalesPromotion(@PathVariable("itemId") String itemId,@RequestBody  TtSalesPromotionPlanDTO salesProDto,
                                                                      UriComponentsBuilder uriCB) {
         Long id = salespromotionservice.addSalesPromotion(itemId,salesProDto);
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/customerManage/salesPromotion/{id}").buildAndExpand(id).toUriString());
         return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
     }

    
    /**
    * 潜客跟进信息修改
    * @author LGQ
    * @date 2016年9月12日
    * @param id
    * @param salesProDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtSalesPromotionPlanDTO> modifySalesPromotionInfo(@PathVariable("id") Long id,
                                                                      @RequestBody @Valid TtSalesPromotionPlanDTO salesProDto,
                                                                      UriComponentsBuilder uriCB) {

        salespromotionservice.updateSalesPromotionInfo(id, salesProDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TtSalesPromotionPlanDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
    * 跟进编辑查询
    * @author LGQ
    * @date 2016年9月23日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> querySalesPromotionInfoByid(@PathVariable(value = "id") long id) {
        Map<String,Object> sesult= salespromotionservice.querySalesPromotionInfo(id);
        return sesult;
    }
    /**
     * 根据父表CUSTOMER_NO明细
     * 
     * @author LGQ
     * @date 2016年12月28日
     * @param id
     * @return
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/followHistoryList", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryVisitIntentInfoByParendId(@PathVariable("id") String id) {
        List<Map> result = salespromotionservice.queryFollowHistoryList(id);
        return result;
    }
    
    /**
    * 客户跟进信息删除
    * @author zhanshiwei
    * @date 2016年9月13日
    * @param id
    * @param uriCB
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSalesPromotionInfoByid(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
        salespromotionservice.deleteSalesPromotionInfoByid(id);
    }
    
    /**
    * 跟进记录查询
    * @author zhanshiwei
    * @date 2016年9月19日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "records/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySalesPromotionAllByid(@PathVariable(value = "id") long id) {
        List<Map> sesult= salespromotionservice.querySalesPromotionAllByid(id);
        return sesult;
    }
    
    /**
    * 重新分配
    * @author zhanshiwei
    * @date 2016年9月13日
    * @param salesProDto
    * @param uriCB
    */
    	
    @RequestMapping(value = "/promoRedistribution", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED) 
    public void modifySalesPromotionForConsultant(@RequestBody SalesPromotionDTO salesProDto,
                                           UriComponentsBuilder uriCB) {
        salespromotionservice.modifySalesPromotionForConsultant(salesProDto);
    }
    
    /**
    *战败原因下拉
    * @author zhanshiwei
    * @date 2016年9月18日
    * @param customeId
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value="/defeatModel/{customeId}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDefeatModel(@PathVariable("customeId") Long customeId, UriComponentsBuilder uriCB) {
        List<Map> result = salespromotionservice.queryDefeatModel(customeId);
        return result;
    }
    /**
     * 
    * 待跟进查询，右侧快捷
    * @author yll
    * @date 2016年10月13日
    * @param queryBuilder
    * @param range
    * @param state
    * @return
     */
    @RequestMapping(value="/homePage/quSalesPromotionInfo",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> queryInWorkOrder(){
        List<Map> pageInfoDto=salespromotionservice.quickQuery();
        return pageInfoDto;
    }
    
    
    /**
     * 跟进活动信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportAxcel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        int flag =12781001;
        List<Map> resultList = salespromotionservice.queryPotentialfollowforExport(queryParam,flag);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("跟进活动", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("CREATE_TYPE", "活动类型", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("BOOKING_CUSTOMER_TYPE", "是否邀约", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BOOKING_DATE", "邀约日期"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
        exportColumnList.add(new ExcelExportColumn("MOBILE", "手机"));
        exportColumnList.add(new ExcelExportColumn("PRIOR_GRADE", "促进前客户级别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("NEXT_GRADE", "促进后客户级别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SCHEDULE_DATE", "计划日期"));
        exportColumnList.add(new ExcelExportColumn("ACTION_DATE", "执行日期"));
        exportColumnList.add(new ExcelExportColumn("PROM_WAY", "跟进方式",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PROM_CONTENT", "跟进内容"));      
        exportColumnList.add(new ExcelExportColumn("PROM_RESULT", "跟进结果",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SCENE", "经过情形"));
        exportColumnList.add(new ExcelExportColumn("AUDITING_REMARK", "审核意见"));
        exportColumnList.add(new ExcelExportColumn("NEXT_PROM_DATE", "下次预定时间"));       
        exportColumnList.add(new ExcelExportColumn("NEXT_BOOKING_DATE", "下次邀约日期"));
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "意向品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));       
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "意向配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "意向颜色"));
        exportColumnList.add(new ExcelExportColumn("LAST_SCENE", "上次跟进的经过情形"));
        exportColumnList.add(new ExcelExportColumn("LMS_REMARK", "LMS校验反馈"));
        exportColumnList.add(new ExcelExportColumn("IS_BIG_CUSTOMER", "是否大客户",ExcelDataType.Dict));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "跟进活动.xls", request, response);

    }
    /**
     * 跟进活动信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "export/auditingexcel", method = RequestMethod.GET)
    public void exportAuditingexcel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        int flag =12781002;
        List<Map> resultList = salespromotionservice.queryPotentialfollowforExport(queryParam,flag);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("跟进活动", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("BOOKING_CUSTOMER_TYPE", "是否邀约", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BOOKING_DATE", "邀约日期"));    
        exportColumnList.add(new ExcelExportColumn("PRIOR_GRADE", "促进前客户级别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("NEXT_GRADE", "促进后客户级别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SCHEDULE_DATE", "计划日期"));
        exportColumnList.add(new ExcelExportColumn("ACTION_DATE", "执行日期"));
        exportColumnList.add(new ExcelExportColumn("PROM_WAY", "跟进方式",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PROM_CONTENT", "跟进内容"));   
        exportColumnList.add(new ExcelExportColumn("NEXT_PROM_DATE", "下次预定时间"));   
        exportColumnList.add(new ExcelExportColumn("SCENE", "经过情形"));
        exportColumnList.add(new ExcelExportColumn("AUDITING_REMARK", "审核意见"));
        exportColumnList.add(new ExcelExportColumn("IS_AUDITING", "是否审核", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "意向品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));       
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "意向配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "意向颜色"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("LAST_SCENE", "上次跟进的经过情形"));
        exportColumnList.add(new ExcelExportColumn("IS_BIG_CUSTOMER", "是否大客户",ExcelDataType.Dict));
        // 生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, "跟进活动.xls", request, response);

    }
    
}
