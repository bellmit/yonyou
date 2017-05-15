
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : VisitingRecordContoller.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月31日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月31日    zhanshiwei    1.0
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

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitRecordDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.customer.service.customerManage.VisitingRecordService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 展厅接待
 * 
 * @author zhanshiwei
 * @date 2016年8月31日
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/visitingRecord")
public class VisitingRecordContoller extends BaseController {

    @Autowired
    private VisitingRecordService visitingrecordservice;

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
        PageInfoDto pageInfoDto = visitingrecordservice.queryVisitingRecordInfo(queryParam);
        return pageInfoDto;
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
        visitingrecordservice.addVisitingRecordInfo(visitDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord").buildAndExpand().toUriString());
        return new ResponseEntity<VisitingRecordDTO>(visitDto, headers, HttpStatus.CREATED);
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
    public Map<String,Object> queryVisitingRecordInfoByid(@PathVariable(value = "id") long id) {
    	Map<String,Object> visitPo = visitingrecordservice.queryVisitingRecordInfoByid(id);
        return visitPo;
    }
    
    /**
     * 展厅意向修改信息查询
     * 
     * @author Benzc
     * @date 2017年1月3日
     * @param queryParam
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}/intent", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryIntent(@PathVariable("id") String id) {
        List<Map> intent = visitingrecordservice.queryIntent(id);
        return intent;
    }
    
    /**
     * 展厅意向新增信息查询
     * @author Benzc
     * @date 2017年1月3日
     * @param queryParam
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/add/intent",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryAddIntent(@RequestParam Map<String, String> queryParam) {
    	List<Map> addIntent = visitingrecordservice.queryAddIntent(queryParam);
        return addIntent;
    }

    /**
     * 根据父表主键查询(展厅客户意向报价)明细
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/{id}/intents", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryVisitIntentInfoByParendId(@PathVariable("id") Long id) {
        List<Map> addressList = visitingrecordservice.queryVisitIntentInfoByParendId(id);
        return addressList;
    }

    /**
     * 客户手机验证
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param mobile
     * @return
     */

    @RequestMapping(value = "checkoutMobile/{mobile}/{cusno}", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkoutMobile(@PathVariable("mobile") String mobile, String cusno) {
        boolean flag = visitingrecordservice.checkoutMobile(mobile, cusno);
        return flag;
    }

    /**
     * 厅接待修改
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
        visitingrecordservice.modifyVisitingRecordInfo(id, visitDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<VisitingRecordDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 检查客户手机是否在展厅记录中存在
     * 
     * @author zhanshiwei
     * @date 2016年9月8日
     * @param mobile
     * @param id
     * @return
     */

    @RequestMapping(value = "visits/{mobile}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkoutMobileforVisits(@PathVariable("mobile") String mobile, @PathVariable("id") String id) {
        boolean flag = visitingrecordservice.checkoutMobileforVisits(mobile, id);
        return flag;
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
        visitingrecordservice.deleteVisitIntentInfo(id);
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
        List<Map> resultList = visitingrecordservice.queryVisitingRecordforExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("展厅接待", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();

        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称 "));
        exportColumnList.add(new ExcelExportColumn("IS_STEP_FORWARD_GREETING", "是否已接待"));
        exportColumnList.add(new ExcelExportColumn("IS_FIRST_VISIT", "是否首次客流"));
        exportColumnList.add(new ExcelExportColumn("IS_VALID", "是否有效客流"));
        exportColumnList.add(new ExcelExportColumn("IS_TEST_DRIVE", "是否试乘试驾"));       
        exportColumnList.add(new ExcelExportColumn("CUS_SOURCE", "客户来源"));      
        exportColumnList.add(new ExcelExportColumn("CON", "联系人"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_PHONE", "联系人电话"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_MOBILE", "联系人手机"));
        exportColumnList.add(new ExcelExportColumn("COMPLAINT_RESULT", "来访结果"));
        exportColumnList.add(new ExcelExportColumn("INTENT_LEVEL", "意向级别"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "意向车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "意向车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "意向配置"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
        exportColumnList.add(new ExcelExportColumn("MEDIA_TYPE", "信息渠道"));
        exportColumnList.add(new ExcelExportColumn("VISITOR", "来访人数"));
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("NEXT_CONSULTANT", "再分配"));
        exportColumnList.add(new ExcelExportColumn("VISIT_TYPE", "来访方式 "));
        exportColumnList.add(new ExcelExportColumn("QUOTED_AMOUNT", "初次报价"));
        exportColumnList.add(new ExcelExportColumn("VISIT_TIME", "来访时间 "));
        exportColumnList.add(new ExcelExportColumn("LEAVE_TIME", "离店时间"));
        exportColumnList.add(new ExcelExportColumn("SCENE", "经过情形"));
        exportColumnList.add(new ExcelExportColumn("CAMPAIGN_CODE", "市场活动"));
        exportColumnList.add(new ExcelExportColumn("CONTACT_TIME", "接触时长（分钟）"));
        exportColumnList.add(new ExcelExportColumn("MEDIA_DETAIL", "渠道细分"));
        exportColumnList.add(new ExcelExportColumn("GENDER", "客户性别"));
        excelService.generateExcelForDms(excelData, exportColumnList, "展厅接待.xls", request, response);

    }
    
    /**
     * 展厅接待查询联系人
     * 
     * @author Benzc
     * @date 2016年12月29日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/contactor",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> contactorSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> contactor = visitingrecordservice.queryContactor(queryParam);
        return contactor;
    }  
    
    /**
	 * 
	 * 渠道细分下拉框
	 * @author Benzc
	 * @date 2017年1月5日
	 * @param queryParam
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/{id}/mediaDetail",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryMediaDetail(@PathVariable String id,@RequestParam Map<String,String> queryParam) {

		queryParam.put("code", id);
		List<Map> list = visitingrecordservice.queryMediaDetail(queryParam);

		return list;
	}
	
	/**
	 * 获取联系人电话
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/{mobile}/mobile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> queryMobileInfoByid(@PathVariable(value = "mobile") String mobile, UriComponentsBuilder uriCB) {
		System.err.println(mobile);
    	Map<String,Object> visitPo = visitingrecordservice.queryMobileInfoByid(mobile);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(mobile).toUriString());
        return new ResponseEntity<Map<String,Object>>(visitPo,headers, HttpStatus.CREATED);
    }
	
	/**
     * 展厅接待再分配
     * @author Benzc
     * @date 2017年3月13日
     */
    @RequestMapping(value = "/soldBy/noList", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody VisitRecordDTO visitDto,
                                           UriComponentsBuilder uriCB) {
    	visitingrecordservice.modifySoldBy(visitDto);
    }
    
    /**
     * 新增时可修改来访时间（受控权限）
     * @author Benzc
     * @date 2017年3月15日
     */
    /*@RequestMapping(value = "/visitTime",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> visitSelect(@RequestParam Map<String,String> queryParam) {
    	Map<String, Object> visitTime = visitingrecordservice.queryVisitTime(queryParam);
        return visitTime;
    }*/  
    
    /**
     * 再分配查询
     * 
     * @author Benzcc
     * @date 2017年4月12日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/redistribution",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVisitingInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = visitingrecordservice.queryRedistributionInfo(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 潜在客户查询 
     * @author Benzc
     * @date 2017年4月13日
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{id}/customerInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPotentialCusInfoByid(@PathVariable(value = "id") String id) {
        List<Map> result = visitingrecordservice.queryPotentialCusInfoByid(id);
        return result.get(0);
    }
    
    /**
     * 潜在客户意向查询 
     * @author Benzc
     * @date 2017年4月17日
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}/cusIntent", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryCusIntent(@PathVariable(value = "id") String id) {
    	List<Map> result = visitingrecordservice.queryCusIntent(id);
        return result;
    }
    
    /**
     * 潜在客户新增(建档)
     * @author Benzc
     * @date 2017年4月21日
     */

    @RequestMapping(value="/{id}/potentialcus",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addPotentialCusInfo(@PathVariable(value = "id") String id,@RequestBody  PotentialCusDTO potentialCusDto,
                                                                   UriComponentsBuilder uriCB) {
    	
    	CommonNoServiceImpl commonNoService = new CommonNoServiceImpl();
        String customerNo = visitingrecordservice.addPotentialCusInfo(id,potentialCusDto,
                                                          commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/potentialcus/{customerNo}").buildAndExpand(customerNo).toUriString());
        return new ResponseEntity<Map<String, Object>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 新增时校验已建档手机号 
     * @author Benzc
     * @date 2017年4月21日
     */
    @RequestMapping(value = "/{id}/CheckCustomerContactorMobile", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Map<String,Object>> queryContactorMobile(@PathVariable(value = "id") String id, UriComponentsBuilder uriCB) {
		System.err.println(id);
    	Map<String,Object> visitPo = visitingrecordservice.queryContactorMobile(id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<Map<String,Object>>(visitPo,headers, HttpStatus.CREATED);
    }
    
    /**
     * 客户带出展厅意向
     * @author Benzc
     * @date 2017年5月2日
     * @param queryParam
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}/cusintent", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryCusIntent1(@PathVariable("id") String id) {
        List<Map> intent = visitingrecordservice.queryCusIntent1(id);
        return intent;
    }
    
    
}
