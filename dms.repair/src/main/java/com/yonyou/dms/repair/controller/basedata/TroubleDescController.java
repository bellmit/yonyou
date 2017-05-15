
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

package com.yonyou.dms.repair.controller.basedata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.yonyou.dms.common.domains.PO.basedata.TroubleDescPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.ChineseCharToEnUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TroubleDescDTO;
import com.yonyou.dms.repair.service.basedata.TroubleDescService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 故障描述信息
 * @author chenwei
 * @date 2017年3月24日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/troubleDesc")
public class TroubleDescController extends BaseController{

    @Autowired
    private TroubleDescService troubleDescService;
    
    /**
     * 根据troubleCode查询
    * TODO 
     * @author chenwei
     * @date 2017年3月24日
    * @param labourPositionCode
    * @return
     */
    @RequestMapping(value = "/{troubleCode}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByTroubleCode(@PathVariable(value = "troubleCode") String troubleCode){
    	TroubleDescPO troubleDescPO = troubleDescService.findByPrimaryKey(troubleCode);
    	return troubleDescPO.toMap();
    }
    
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author chenwei
     * @date 2017年3月24日
     * @param troubleDescSql 查询条件
     * @return PageInfoDto
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto troubleDescSql(@RequestParam Map<String, String> troubleDescSql) throws ServiceBizException{
        PageInfoDto pageInfoDto = troubleDescService.searchTroubleDesc(troubleDescSql);
        return pageInfoDto;
    }
    
    /**
     * 新增故障描述信息
     * @author 陈伟
     * @date 2017年3月24日
     * @param troubleDescDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TroubleDescDTO> addTroubleDesc(@RequestBody TroubleDescDTO troubleDescDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        String troubleCode = troubleDescService.insertTroubleDescPO(troubleDescDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/troubleDesc/{troubleCode}").buildAndExpand(troubleCode).toUriString());  
        return new ResponseEntity<TroubleDescDTO>(troubleDescDTO,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * 根据code 修改故障描述信息
     * @author chenwei
     * @date 2017年3月24日
     * @param labourPositionCode
     * @param maintainWorkTypeDTO
     * @param uriCB
     * @return 
     * @throws ServiceBizException
     */
    	
    @RequestMapping(value = "/{troubleCode}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TroubleDescDTO> updateTroubleDesc(@PathVariable("troubleCode") String troubleCode,@RequestBody TroubleDescDTO troubleDescDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	troubleDescService.updateTroubleDesc(troubleCode, troubleDescDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/troubleDesc/{troubleCode}").buildAndExpand(troubleCode).toUriString());  
        return new ResponseEntity<TroubleDescDTO>(headers, HttpStatus.CREATED);  
    }
    
    @RequestMapping(value="/getChToEn/{params}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getChToEn(@PathVariable("params") String params) throws IOException{  
        List<Map> listMap = new ArrayList<Map>();
        Map map = new HashMap();
        if(!StringUtils.isNullOrEmpty(params)){
        	String englishStr = ChineseCharToEnUtil.getFirstSpell(params);
        	map.put("englishStr", englishStr);
        	listMap.add(map);
        	return listMap;
        }else{
        	map.put("englishStr", "");
        	listMap.add(map);
        	return listMap;
        }
    }   
}
