package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
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
import com.yonyou.dms.manage.domains.DTO.basedata.UserMappingDto;
import com.yonyou.dms.manage.service.basedata.UserMappingService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/userMapping")
public class UserMappingController extends BaseController{

    @Autowired
    private UserMappingService userMappingService;
    
    
    /**
    * TODO description
    * @author ceg
    * @date 2017年5月11日
    * @param queryParam
    * @return 用户映射查询
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUserMapping(@RequestParam Map<String, String> queryParam){
        PageInfoDto pageInfoDto = userMappingService.queryUserMapping(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * TODO description
    * @author ceg
    * @date 2017年5月11日
    * @param id
    * @return 修改时获取用户信息
    */
    	
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getUserMappingById(@PathVariable(value="id") String id){
       Map userMapping = userMappingService.getUserMappingById(id);                                         
        return userMapping;
    }
    
    
    
    /**
    * TODO description
    * @author ceg
    * @date 2017年5月11日
    * @return 获取经销商DCS系统账号
    */
    	
    @RequestMapping(value="/getSysAcc", method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getSys(){
        List<Map> list = userMappingService.getSystem();
        return list;
        
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.PUT)
    public ResponseEntity<UserMappingDto> modifyUserMapping(@PathVariable("id") String userCode,@RequestBody @Valid UserMappingDto userMappingDto,UriComponentsBuilder uriCB){
        userMappingService.modifyUserMapping(userCode, userMappingDto);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/userMapping/{id}").buildAndExpand(userCode).toUriString());  
        return new ResponseEntity<UserMappingDto>(headers, HttpStatus.CREATED);  
        
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<UserMappingDto> addUserMapping(@RequestBody @Valid UserMappingDto userMappingDto,UriComponentsBuilder uriCB){
        String userCode = userMappingService.addUserMapping(userMappingDto);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/userMapping/{id}").buildAndExpand(userCode).toUriString());  
        return new ResponseEntity<UserMappingDto>(headers, HttpStatus.CREATED);  
        
    }
    
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserMapping(@PathVariable("id") String userCode ){
        userMappingService.deleteUserMapping(userCode);
    }
    
    @RequestMapping(value="/users",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getUsers(@RequestParam Map<String,String> queryParam){
        PageInfoDto  pageInfoDto = userMappingService.queryUsers(queryParam);
        return pageInfoDto;
        
    }
}
