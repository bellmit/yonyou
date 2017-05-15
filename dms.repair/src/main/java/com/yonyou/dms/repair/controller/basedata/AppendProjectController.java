
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : AppendProjectController.java
 *
 * @Author : zhongsw
 *
 * @Date : 2016年8月19日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月19日    zhongsw    1.0
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.AppendProjectDTO;
import com.yonyou.dms.repair.domains.PO.basedata.AppendProjectPO;
import com.yonyou.dms.repair.service.basedata.AppendProjectService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;




/**
 * 附加项目定义类
* TODO description
* @author zhongsw
* @date 2016年8月29日
*/
	
@Controller
@TxnConn
@RequestMapping("/basedata/appendProject")
public class AppendProjectController extends BaseController {

    @Autowired
    private AppendProjectService appendProjectService;

    /**
     * 查询
     * @author zhongsw
     * @date 2016年8月19日
     * @param queryParam
     * @return
     * @throws ServiceBizException 
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryModel(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = appendProjectService.searchAppendProject(queryParam);
        return pageInfoDto;
    }

    /**
     * 根据id查找
     * @author zhongshiwei
     * @date 2016年7月1日
     * @param id
     * @throws ServiceBizException 
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id) throws ServiceBizException{
        AppendProjectPO wtpo= appendProjectService.findById(id);
        return wtpo.toMap();
    }

    /**
     * 新增
     * @author zhongsw
     * @date 2016年8月19日
     * @param modeldto
     * @param uriCB
     * @return
     * @throws ServiceBizException 
     */

    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AppendProjectDTO> addModel(@RequestBody AppendProjectDTO modeldto,UriComponentsBuilder uriCB) throws ServiceBizException{
        String id=appendProjectService.addModel(modeldto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/appendProject/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<AppendProjectDTO>(modeldto,headers, HttpStatus.CREATED);  
    }

    /**
     * 修改
     * @author zhongsw
     * @date 2016年8月19日
     * @param id
     * @param modeldto
     * @param uriCB
     * @return
     * @throws ServiceBizException 
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AppendProjectDTO> updateModel(@PathVariable("id") String id,@RequestBody AppendProjectDTO modeldto,UriComponentsBuilder uriCB) throws ServiceBizException{
        appendProjectService.updateModel(id,modeldto);

        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/appendProject/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<AppendProjectDTO>(headers, HttpStatus.CREATED);  
    }

    /**
     * 附加项目下拉框加载
     * @author ZhengHe
     * @date 2016年8月19日
     * @return
     * @throws ServiceBizException 
     */
    @RequestMapping(value="/appendProject/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectappendProject() throws ServiceBizException{
        List<Map> appendProjectlist = appendProjectService.selectAppendProject();
        return appendProjectlist;
    }

    /**
     * 附加项目下拉框数据
     * @author ZhengHe
     * @date 2016年8月19日
     * @return
     * @throws ServiceBizException 
     */
    @RequestMapping(value="/{addItemCodes}/addItem",method = RequestMethod.GET)
    @ResponseBody
    public Map selectappendProjectByCode(@PathVariable("addItemCodes") String code) throws ServiceBizException{
        Map appendProjectMap = appendProjectService.selectAppendProjectByCode(code);
        return appendProjectMap;
    }
}
