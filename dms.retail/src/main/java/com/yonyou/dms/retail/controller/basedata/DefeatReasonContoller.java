
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : DefeatResonConntoller.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月1日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月1日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.controller.basedata;

import java.util.List;
import java.util.Map;

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
import com.yonyou.dms.retail.domains.DTO.basedata.DefeatReasonDTO;
import com.yonyou.dms.retail.domains.PO.basedata.DefeatReasonPO;
import com.yonyou.dms.retail.service.basedata.DefeatReasonService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author DuPengXin
 * @date 2016年7月11日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/defeats")
public class DefeatReasonContoller extends BaseController {

    @Autowired
    private DefeatReasonService drservice;


    /**
     * 查询
     * @author DuPengXin
     * @date 2016年7月11日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = drservice.QueryDefeatReason(queryParam);
        return pageInfoDto;
    }

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年7月4日
     * @param drdto 战败原因信息
     * @param uriCB 
     * @return 新增的战败原因信息
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<DefeatReasonDTO> addDefeatReason(@RequestBody DefeatReasonDTO drdto,UriComponentsBuilder uriCB){
        Long id=drservice.addDefeatReason(drdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/defeats/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<DefeatReasonDTO>(drdto,headers, HttpStatus.CREATED);  
    }

    /**
     * 修改
     * @author DuPengXin
     * @date 2016年7月8日
     * @param id
     * @param drdto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DefeatReasonDTO> ModifyUser(@PathVariable("id") Long id,@RequestBody DefeatReasonDTO drdto,UriComponentsBuilder uriCB) {
        drservice.modifyReason(id,drdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/defeats/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<DefeatReasonDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 根据ID查找数据
     * @author DuPengXin
     * @date 2016年7月8日
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable Long id){
        DefeatReasonPO dr= drservice.findById(id);
        return dr.toMap();
    }
    
    /**
    * 战败原因
    * @author zhanshiwei
    * @date 2016年10月26日
    * @param queryParam
    * @return
    */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value="/defeatReason/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDefeatReasonSel(@RequestParam Map<String, String> queryParam) {
        List<Map> result = drservice.queryDefeatReasonSel(queryParam);
        return result;
    }
}
