
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : InvoiceTypeController.java
*
* @Author : yangjie
*
* @Date : 2016年12月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月19日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.InvoiceYTypeDTO;
import com.yonyou.dms.manage.domains.PO.basedata.InvoiceTypePO;
import com.yonyou.dms.manage.service.basedata.invoiceType.InvoiceTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 发票类型
 * @author yangjie
 * @date 2016年12月19日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/invoiceType")
public class InvoiceTypeController {

    @Autowired
    private InvoiceTypeService invoiceTypeService;

    /**
     * 遍历所有类型 TODO description
     * 
     * @author yangjie
     * @date 2016年12月19日
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAll(@RequestParam Map<String, String> param) throws ServiceBizException {
        return invoiceTypeService.findAll(param);
    }

    /**
     * 通过id查询类型信息 TODO description
     * 
     * @author yangjie
     * @date 2016年12月19日
     * @param id
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@PathVariable String id) throws ServiceBizException {
        InvoiceTypePO po = invoiceTypeService.findById(id);
        return po.toMap();
    }

    /**
     * 新增方法 TODO description
     * 
     * @author yangjie
     * @date 2016年12月19日
     * @param dto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<InvoiceYTypeDTO> addType(@RequestBody InvoiceYTypeDTO dto, UriComponentsBuilder uriCB) {
        invoiceTypeService.addType(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/invoiceType/{id}").buildAndExpand(dto.getCode()).toUriString());
        return new ResponseEntity<InvoiceYTypeDTO>(dto, headers, HttpStatus.CREATED);
    }

    /**
     * 修改方法 TODO description
     * 
     * @author yangjie
     * @date 2016年12月19日
     * @param id
     * @param dto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<InvoiceYTypeDTO> editType(@PathVariable("id") String id,
                                                    @RequestBody @Valid InvoiceYTypeDTO dto,
                                                    UriComponentsBuilder uriCB) {
        invoiceTypeService.editType(id, dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/invoiceType/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<InvoiceYTypeDTO>(dto, headers, HttpStatus.CREATED);
    }

    /**
     * 删除方法 TODO description
     * 
     * @author yangjie
     * @date 2016年12月19日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delType(@PathVariable("id") String id, UriComponentsBuilder uriCB) {
        invoiceTypeService.delType(id);
    }
}
