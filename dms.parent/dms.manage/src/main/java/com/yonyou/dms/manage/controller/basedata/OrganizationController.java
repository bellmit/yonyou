/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : OrganizationController.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.controller.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationDto;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationTreeDto;
import com.yonyou.dms.manage.service.basedata.org.OrganizationService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 部门
* @author rongzoujie
* @date 2016年9月6日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/orgs")
public class OrganizationController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(PositionController.class);
    @Autowired
    private OrganizationService organizationService;
    
    /**
    *
    * 获取组织树形结构方法
    * @author rongzoujie
    * @date 2016年8月2日
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public List<OrganizationTreeDto> getOrgs(){
        List<Map> list = organizationService.getOrganization();
        List<OrganizationTreeDto> orgList = new ArrayList<>(); 
        
        for(int i=0;i<list.size();i++){
            OrganizationTreeDto orgTreeOrg = new OrganizationTreeDto();
            orgTreeOrg.setId((String)list.get(i).get("ORG_CODE").toString());
            String parent = "#";
            if(!StringUtils.isNullOrEmpty(list.get(i).get("PARENT_ORG_CODE"))){
                parent = (String)list.get(i).get("PARENT_ORG_CODE").toString();
            }
            orgTreeOrg.setParent(parent);
            orgTreeOrg.setText((String)list.get(i).get("ORG_NAME").toString());
            orgList.add(orgTreeOrg);
        }
        
        return orgList;
    }
    
    /**
    *
    * 获取全部部门
    * @author sunqinghua
    * @date 2017年3月21日
    * @return
     */
    @RequestMapping(value="/getOrgsAll", method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getOrgsAll(){
        List<Map> list = organizationService.getOrganization();
        return list;
    }
    
    /**
     * 
    * 获取有效组织的树桩图
    * @author rongzoujie
    * @date 2016年11月14日
    * @return
     */
    @RequestMapping(value="/getIsValid/Orgs", method=RequestMethod.GET)
    @ResponseBody
    public List<OrganizationTreeDto> getIsValidOrgs(){
        List<Map> list = organizationService.getIsValidOrganization();
        List<OrganizationTreeDto> orgList = new ArrayList<>(); 
        
        for(int i=0;i<list.size();i++){
            OrganizationTreeDto orgTreeOrg = new OrganizationTreeDto();
            orgTreeOrg.setId((String)list.get(i).get("ORG_CODE").toString());
            String parent = "#";
            if(!StringUtils.isNullOrEmpty(list.get(i).get("PARENT_ORG_CODE"))){
                parent = (String)list.get(i).get("PARENT_ORG_CODE").toString();
            }
            orgTreeOrg.setParent(parent);
            orgTreeOrg.setText((String)list.get(i).get("ORG_NAME").toString());
            orgList.add(orgTreeOrg);
        }
        
        return orgList;
    }
    
    
    /**
     * 
    * 更具code查询
    * @author ron
    * @date 2016年8月2日
    * @param orgCode
    * @return
     */
    @RequestMapping(value = "/{orgCode}", method = RequestMethod.GET)
    @ResponseBody
    public Map getOrgByCodeName(@PathVariable(value = "orgCode") String orgCode){
        return organizationService.getOrgByCode(orgCode);
    }
    
    /**
     *添加部门 
    * @author rongzoujie
    * @date 2016年8月3日
    * @param orgDto
    * @param uriCB
    * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OrganizationDto> addOrg(@RequestBody OrganizationDto orgDto,UriComponentsBuilder uriCB) {
//        orgDto.setIsValid(10011001);
    	
    	String oc =  orgDto.getOrgCode();
    	String opc = orgDto.getParentOrgCode();
    	if(!oc.substring(0, oc.length()-2).equals(opc)){
			throw new ServiceBizException("只能对组织代码的后两位进行更改"); 
		}
    	String orgCode = organizationService.addOrg(orgDto);
    	
    	
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/orgs/{orgCode}").buildAndExpand(orgCode).toUriString());  
        return new ResponseEntity<>(orgDto,headers, HttpStatus.CREATED);  
    }
    
    /**
     * 修改部门
    * @author rongzoujie
    * @date 2016年8月2日
    * @param id
    * @param orgDto
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<OrganizationDto> ModifyOrg(@PathVariable("id") Integer id,@RequestBody OrganizationDto orgDto,UriComponentsBuilder uriCB) {
        organizationService.modifyOrg(id, orgDto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/orgs/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
    }
    
    /**
     * 删除部门
    * @author rongzoujie
    * @date 2016年8月3日
    * @param id
    * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrg(@PathVariable("id") Long id,UriComponentsBuilder uriCB){
        organizationService.deleteOrgById(id);
    }
    
    /**
     * 获取上级组织
    * TODO description
    * @author rongzoujie
    * @date 2016年8月3日
    * @return
     */
    @RequestMapping(value = "/getParents/super", method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getParents(){
        return organizationService.getParents();
    }
    
    @RequestMapping(value="/employee/{employeeCode}")
    @ResponseBody
    public Map findByOrgCode(@PathVariable String employeeCode){
        return organizationService.findByOrgCode(employeeCode);
    }
    
    @RequestMapping(value = "/parentCode/{parentCode}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryOldSalesOrders(@RequestParam Map<String, String> queryParam,@PathVariable("parentCode") String parentCode) {
    	
        List<Map> pageInfoDto = organizationService.qrySalesOrdersDetial(queryParam,parentCode);
        return pageInfoDto;
    }
}
