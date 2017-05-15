
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusComplaintContoller.java
*
* @Author : zhanshiwei
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.controller.customerManage;

import java.util.List;
import java.util.Map;

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

import com.yonyou.dms.customer.domains.DTO.basedata.CustomerResoDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerComplaintDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.customer.service.customerManage.CusComplaintService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 客户投诉
 * 
 * @author zhanshiwei
 * @date 2016年7月27日
 */
@Controller
@TxnConn
@RequestMapping("/customerComplaint")
public class CusComplaintContoller extends BaseController {

    @Autowired
    private CusComplaintService cusComplaintService;
    @Autowired
    private CommonNoService     commonNoService;

    /**
     * 客户投诉查询
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = cusComplaintService.queryCusComplaint(queryParam);
        return pageInfoDto;
    }

    /**
     * 新增投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param compl
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<CustomerComplaintDTO> addCustomerComplaint(@RequestBody @Valid CustomerComplaintDTO compl,
                                                                     UriComponentsBuilder uriCB) {
        Long id = cusComplaintService.addCustomerComplaint(compl,
                                                           commonNoService.getSystemOrderNo(CommonConstants.COMPLAINTNO_PREFIX));
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerComplaint/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<CustomerComplaintDTO>(compl, headers, HttpStatus.CREATED);
    }

    /**
     * 根据ID查询投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getCustomerComplaintById(@PathVariable(value = "id") Long id) {
        CustomerComplaintPO CuscomplainPo = cusComplaintService.getCustomerComplaintById(id);
        Map<String, Object> opetypeMap = cusComplaintService.getCustomerComplaintOpeType();
        Map<String, Object> orgMap=cusComplaintService.getCustomerComplaintOrgCode(id);
        Map<String, Object> complMap = CuscomplainPo.toMap();
        complMap.putAll(opetypeMap);
        complMap.putAll(orgMap);
        return complMap;
    }

    /**
     * 根据主表主键id查询明细信息
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}/complaintdetail", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryComplaintDetail(@PathVariable("id") Long id) {
        List<Map> addressList = cusComplaintService.queryComplaintDetail(id);
        return addressList;
    }

    /**
     * 修改投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param id
     * @param cusCompDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<CustomerResoDTO> modifyCustomerComplaint(@PathVariable("id") Long id,
                                                                   @RequestBody @Valid CustomerComplaintDTO cusCompDto,
                                                                   UriComponentsBuilder uriCB) {
        cusComplaintService.modifyCustomerComplaint(id, cusCompDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerComplaint/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<CustomerResoDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 结案操作
     * 
     * @author zhanshiwei
     * @date 2016年8月1日
     * @param cusCompDto
     * @param uriCB
     */

    @RequestMapping(value = "/selectSettle/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> modifySettleSate(@PathVariable("id") Long id,
                                                                @RequestBody CustomerComplaintDTO cusCompDto,
                                                                UriComponentsBuilder uriCB) {
        CustomerComplaintPO cuscompPo = cusComplaintService.modifySettleSate(id, cusCompDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerComplaint/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<Map<String, Object>>(cuscompPo.toMap(), headers, HttpStatus.CREATED);
    }

    /**
     * OEM非手动调用接口结案操作(不用了)
     * 
     * @author zhanshiwei
     * @date 2016年8月1日
     * @param cusCompDto
     * @param uriCB
     */

    @RequestMapping(value = "/complaintSettle/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, Object>> complaintSettleforOem(@PathVariable("id") Long id,
                                                                     UriComponentsBuilder uriCB) {
        CustomerComplaintPO cuscompPo = cusComplaintService.complaintSettleforOem(id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerComplaint/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<Map<String, Object>>(cuscompPo == null ? null : cuscompPo.toMap(), headers,
                                                       HttpStatus.CREATED);
    }
}
