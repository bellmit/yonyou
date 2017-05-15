
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : StoreController.java
 *
 * @Author : zhongshiwei
 *
 * @Date : 2016年7月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月10日    zhongshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.controller.basedata;

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

import com.yonyou.dms.common.domains.PO.basedata.PartModelGroupPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartModelGroupDTO;
import com.yonyou.dms.part.service.basedata.PartModelGroupService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件车型组信息
 * @author chenwei
 * @date 2017年3月22日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/partModelGroup")
public class PartModelGroupController extends BaseController{

    @Autowired
    private PartModelGroupService partModelGroupService;
    
    /**
     * 根据PartModelGroupCode查询
    * TODO 根据vin查询
     * @author zhengcong
     * @date 2017年3月21日
    * @param vin
    * @return
     */
    @RequestMapping(value = "/{partModelGroupCode}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByPartModelGroupCode(@PathVariable(value = "partModelGroupCode") String partModelGroupCode){
    	PartModelGroupPo partModelGroupPO = partModelGroupService.findByPrimaryKey(partModelGroupCode);
    	return partModelGroupPO.toMap();
    }
    
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年6月29日
     * @param searchStore 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partModelGroupSql(@RequestParam Map<String, String> storeSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = partModelGroupService.searchPartModelGroup(storeSQL);
        return pageInfoDto;
    }
    
    /**
     * 新增配件车型组信息
     * @author zhongshiwei
     * @date 2016年6月30日
     * @param storeDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PartModelGroupDTO> addStore(@RequestBody PartModelGroupDTO partModelGroupDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        String pageModelGroupCode = partModelGroupService.insertPartModelGroupPo(partModelGroupDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partModelGroup/{pageModelGroupCode}").buildAndExpand(pageModelGroupCode).toUriString());  
        return new ResponseEntity<PartModelGroupDTO>(partModelGroupDTO,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * 根据code 修改配件车型组信息
     * @author zhongshiwei
     * @date 2016年6月30日
     * @param id
     * @param storeDTO
     * @param uriCB
     * @return 
     * @throws ServiceBizException
     */
    	
    @RequestMapping(value = "/{partModelGroupCode}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PartModelGroupDTO> updateStore(@PathVariable("partModelGroupCode") String partModelGroupCode,@RequestBody PartModelGroupDTO partModelGroupDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	partModelGroupService.updatePartModelGroup(partModelGroupCode,partModelGroupDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partModelGroup/{pageModelGroupCode}").buildAndExpand(partModelGroupCode).toUriString());  
        return new ResponseEntity<PartModelGroupDTO>(headers, HttpStatus.CREATED);  
    }
}
