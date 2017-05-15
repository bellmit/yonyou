
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceProjectController.java
*
* @Author : zhongsw
*
* @Date : 2016年9月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月11日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;

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
import com.yonyou.dms.retail.domains.DTO.ordermanage.ServiceProjectDTO;
import com.yonyou.dms.retail.domains.PO.ordermanage.ServiceProjectPO;
import com.yonyou.dms.retail.service.ordermanage.ServiceProjectService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**服务项目定义控制类
* 
* @author zhongsw
* @date 2016年9月11日
*/
@Controller
@TxnConn
@RequestMapping("/service/project")
public class ServiceProjectController extends BaseController{
    
    @Autowired
    private ServiceProjectService serviceProjectService;
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年6月29日
     * @param insuranceSQL 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto search(@RequestParam Map<String, String> search) throws ServiceBizException{
        PageInfoDto pageInfoDto = serviceProjectService.searchServiceProject(search);
        return pageInfoDto;
    }        
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年6月29日
     * @param insuranceSQL 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/salesorder",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qryServiceForSales(@RequestParam Map<String, String> search) throws ServiceBizException{
        PageInfoDto pageInfoDto = serviceProjectService.searchServiceProject(search);
        return pageInfoDto;
    }
    

    /**
    * 新增用户信息
    * @author zhongsw
    * @date 2016年9月11日
    * @param serviceprojectdto
    * @param uriCB
    * @return
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value = "/addService", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ServiceProjectDTO> add(@RequestBody ServiceProjectDTO serviceprojectdto,ServiceProjectPO lap,UriComponentsBuilder uriCB) throws ServiceBizException{
        Long id = serviceProjectService.add(serviceprojectdto, lap);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/service/project/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<ServiceProjectDTO>(serviceprojectdto,headers, HttpStatus.CREATED);  
    }

    /**根据ID 修改用户信息
    * 
    * @author zhongsw
    * @date 2016年9月11日
    * @param id
    * @param serviceProjectdto
    * @param uriCB
    * @return
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ServiceProjectDTO> update(@PathVariable("id") Long id,@RequestBody ServiceProjectDTO serviceProjectdto,UriComponentsBuilder uriCB) throws ServiceBizException{
        serviceProjectService.update(id,serviceProjectdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/service/project/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<ServiceProjectDTO>(headers, HttpStatus.CREATED);  
    }
    
    
    /**
    * 显示编辑明细数据
    * @author zhongsw
    * @date 2016年9月14日
    * @param id
    * @param serviceProjectdto
    * @return
    */
    	
    @RequestMapping(value = "/{id}/edit",method= RequestMethod.GET)
    @ResponseBody
    public Map editSearch(@PathVariable("id") Long id){
        Map map=serviceProjectService.editSearch(id);
        return map;
    }
    
    /**根据ID 删除用户信息
    * TODO description
    * @author zhongsw
    * @date 2016年9月11日
    * @param id
    * @param uriCB
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
        serviceProjectService.deleteById(id);
    }

}
