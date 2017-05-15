
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairTypeController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月27日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月27日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

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

import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairTypeDTO;
import com.yonyou.dms.repair.service.basedata.RepairTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 维修类型
 * @author DuPengXin
 * @date 2016年7月27日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/repairtypes")
public class RepairTypeController extends BaseController {

    @Autowired
    private RepairTypeService   repairtypeService;


    /**
     *获取维修项目分类的下拉框 
     * @author rongzoujie
     * @date 2016年8月15日
     * @return
     */
    
    @RequestMapping(value = "/getRepairTypes/item" ,method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getRepairTypesList(){
        return repairtypeService.queryRepairType();
    }
    
    /**
     *获取工时单价的下拉框 
     * @author rongzoujie
     * @date 2016年8月15日
     * @return
     */
    
    @RequestMapping(value = "/labourprice/item" ,method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryLabourPrice(){
        return repairtypeService.queryLabourPrice();
    }

    /**
     * 查询
     * @author DuPengXin
     * @date 2016年7月27日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairType(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = repairtypeService.QueryRepairType(queryParam);
        return pageInfoDto;
    }


    /**
     * 修改
     * @author DuPengXin
     * @date 2016年7月27日
     * @param id
     * @param repairtypedto
     * @param uriCB
     * @return
     */

   @RequestMapping(value = "/savelabourprice", method = RequestMethod.PUT)
   public ResponseEntity<Map<String, String>> updateRepairType(@RequestBody Map<String, String> map,UriComponentsBuilder uriCB) {
        repairtypeService.updateRepairType(map);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/repairtypes").buildAndExpand().toUriString());  
    	return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);  
    }


    /**
     * 根据ID查询
     * @author zhl
     * @date 2017年3月24日
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id){
        TmRepairTypePO repairtype= repairtypeService.findByCode(id);
        return repairtype.toMap();
    }
}
