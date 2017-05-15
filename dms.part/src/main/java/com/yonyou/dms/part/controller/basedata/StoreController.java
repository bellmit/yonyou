
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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;
import com.yonyou.dms.part.service.basedata.StoreService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 仓库信息
 * @author zhengcong
 * @date 2017年3月21日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/store")
public class StoreController extends BaseController{

    @Autowired
    private StoreService storeService;

    /**
     * 根据查询条件返回对应数据
     * @author zhengcong
     * @date 2017年3月21日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto storeSql(@RequestParam Map<String, String> storeSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = storeService.searchStore(storeSQL);
        return pageInfoDto;
    }  

    /**
     * 根据code查询
    * TODO 根据vin查询
     * @author zhengcong
     * @date 2017年3月21日
    * @param vin
    * @return
     */
    @RequestMapping(value = "/findByStorageCode/{STORAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByStorageCode(@PathVariable(value = "STORAGE_CODE") String STORAGE_CODE){
        return storeService.findByStorageCode(STORAGE_CODE);
    }

    /**
     * 
     * 根据code 修改
     * @author zhengcong
     * @date 2017年3月21日
     */
    	
    @RequestMapping(value = "/modifyBycode/{STORAGE_CODE}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<StoreDTO> updateStore(@PathVariable("STORAGE_CODE") String STORAGE_CODE,@RequestBody StoreDTO storeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        storeService.updateStore(STORAGE_CODE,storeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/Store/{STORAGE_CODE}").buildAndExpand(STORAGE_CODE).toUriString());  
        return new ResponseEntity<StoreDTO>(headers, HttpStatus.CREATED);  
    }



    
    /**
     * 
     * 根据仓库查询下拉列框
     * @author zhengcong
     * @date 2017年4月10日
     */

    @RequestMapping(value="/storelist",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> refBrand() throws ServiceBizException{
        List<Map> storelist = storeService.queryList();
        return storelist;  

    }
    

     
    

	
}
