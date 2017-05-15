
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

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainWorkTypeDTO;
import com.yonyou.dms.repair.service.basedata.MaintainWorkTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 维修工位信息
 * @author chenwei
 * @date 2017年3月23日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/maintainWorkType")
public class MaintainWorkTypeController extends BaseController{

    @Autowired
    private MaintainWorkTypeService maintainWorkTypeService;
    
    /**
     * 根据labourPositionCode查询
    * TODO 
     * @author chenwei
     * @date 2017年3月23日
    * @param labourPositionCode
    * @return
     */
    @RequestMapping(value = "/{labourPositionCode}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByLabourPositionCode(@PathVariable(value = "labourPositionCode") String labourPositionCode){
    	MaintainWorkTypePO maintainWorkTypePO = maintainWorkTypeService.findByPrimaryKey(labourPositionCode);
    	return maintainWorkTypePO.toMap();
    }
    
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author chenwei
     * @date 2017年3月23日
     * @param searchMaintainWorkType 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partModelGroupSql(@RequestParam Map<String, String> maintainWorkTypeSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = maintainWorkTypeService.searchMaintainWorkType(maintainWorkTypeSQL);
        return pageInfoDto;
    }
    
    /**
     * 新增维修工位信息
     * @author 陈伟
     * @date 2017年3月23日
     * @param maintainWorkTypeDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<MaintainWorkTypeDTO> addMaintainWorkType(@RequestBody MaintainWorkTypeDTO maintainWorkTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        String labourPositionCode = maintainWorkTypeService.insertMaintainWorkTypePo(maintainWorkTypeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/maintainWorkType/{labourPositionCode}").buildAndExpand(labourPositionCode).toUriString());  
        return new ResponseEntity<MaintainWorkTypeDTO>(maintainWorkTypeDTO,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * 根据code 修改维修工位信息
     * @author chenwei
     * @date 2017年3月23日
     * @param labourPositionCode
     * @param maintainWorkTypeDTO
     * @param uriCB
     * @return 
     * @throws ServiceBizException
     */
    	
    @RequestMapping(value = "/{labourPositionCode}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<MaintainWorkTypeDTO> updateStore(@PathVariable("labourPositionCode") String labourPositionCode,@RequestBody MaintainWorkTypeDTO maintainWorkTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	maintainWorkTypeService.updateMaintainWorkType(labourPositionCode, maintainWorkTypeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/maintainWorkType/{labourPositionCode}").buildAndExpand(labourPositionCode).toUriString());  
        return new ResponseEntity<MaintainWorkTypeDTO>(headers, HttpStatus.CREATED);  
    }
}
