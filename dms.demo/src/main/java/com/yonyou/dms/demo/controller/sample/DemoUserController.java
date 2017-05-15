/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : TestController.java
*
* @Author : zhangxc
*
* @Date : 2016年6月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年6月29日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.demo.controller.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.demo.domains.DTO.sample.DemoUserDto;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserImportDto;
import com.yonyou.dms.demo.domains.DTO.sample.DemoUserUpdateDto;
import com.yonyou.dms.demo.domains.PO.sample.DemoUserPO;
import com.yonyou.dms.demo.service.sample.DemoUserService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 简要描述：模板类. 通过此功能向开发人员介绍相关的框架使用相关模板
 * 
 * @author zhangxc
 * @date 2016年6月29日
 */
@Controller
@TxnConn
@RequestMapping("/demoUsers")
public class DemoUserController extends BaseController {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(DemoUserController.class);

    @Autowired
    private DemoUserService         userservice;

    @Autowired
    private ExcelGenerator      excelService;
    
    @Autowired
    private ExcelRead<DemoUserDto>      excelReadService;
    
   
    
    /**
     * 根据查询条件返回对应的用户数据
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUsers(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = userservice.queryUsers(queryParam);

        return pageInfoDto;
        
        
    }

    /**
     * 
    * 查询URL 重复
    * @author zhangxc
    * @date 2016年11月11日
     */
    private void checkUrlDulicate(){
        List<Map> urlList = DAOUtil.findAll("select DISTINCT t.ACTION_CODE from tc_menu_action t where t.ACTION_METHOD = 'GET' ", null, false);
        
        for(Map url:urlList){
            int countNumber = 0;
            String actionCode = (String)url.get("ACTION_CODE");
            //如果存在替换服
            if(StringUtils.isMatcherPatten("\\{[^/]+\\}", actionCode)){
                String actionCodeNew = actionCode.replaceAll("\\{[^/]+\\}", "[^/]+");
                
                for(Map url2:urlList){
                    String actionCode2 = (String)url2.get("ACTION_CODE");
                    String actionCodeNew2 = actionCode2.replaceAll("\\{[^/]+\\}", "[^/]+");
                    if(!actionCode.equals(actionCode2)&&!actionCodeNew.equals(actionCodeNew2)){
                        if(StringUtils.isMatcherPatten("^"+actionCodeNew+"$", actionCode2)){
                            countNumber++;
                            System.out.println(actionCode+"-->"+actionCode2);
                        }
                    }
                }
                
                
            }
            
        }
    }
    /**
     * 根据查询条件返回对应的用户数据
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param queryParam 查询条件
     * @return 查询结果
     * @throws Exception
     */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportUsers(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = userservice.queryUsersForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("用户信息", resultList);	
//        String[] keys = { "USER_NAME", "NAME", "BIRTHDAY", "SALARY","AGE","ENTRY_TIME","SEX","DEALER_CODE" };
//        String[] columnNames = { "用户名", "姓名", "生日", "薪水","年龄","工作开始时间","性别","经销商代码" };
        // 生成excel 文件
//        excelService.generateExcel(excelData, keys, columnNames, "用户信息.xls", response);
        
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("USER_NAME","用户名"));
        exportColumnList.add(new ExcelExportColumn("NAME","姓名"));
        exportColumnList.add(new ExcelExportColumn("BIRTHDAY","生日"));
        exportColumnList.add(new ExcelExportColumn("SALARY","薪水"));
        exportColumnList.add(new ExcelExportColumn("SALARY","薪水2",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("AGE","年龄"));
        exportColumnList.add(new ExcelExportColumn("AGE_PERCENT","年龄/薪水",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE_PERCENT));
        exportColumnList.add(new ExcelExportColumn("ENTRY_TIME","工作开始时间"));
        //其它类型：Region_Provice,Region_City,Region_Country
        exportColumnList.add(new ExcelExportColumn("SEX","性别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("CREATED_AT","创建时间",CommonConstants.SIMPLE_DATE_TIME_FORMAT));
        excelService.generateExcel(excelData, exportColumnList, "用户信息.xls", request,response);

    }

    /**
     * 根据ID 获取用户的信息
     * 
     * @author zhangxc
     * @date 2016年6月29日
     * @param id 用户ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getUserById(@PathVariable(value = "id") Long id) {
        DemoUserPO user = userservice.getUserById(id);
        return user.toMap();
    }

    /**
     * 新增用户信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<DemoUserDto> addUser(@RequestBody @Valid DemoUserDto userDto, UriComponentsBuilder uriCB) {

        Long id = userservice.addUser(userDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(userDto, headers, HttpStatus.CREATED);

    }

    /**
     * 根据ID 修改用户信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<DemoUserDto> ModifyUser(@PathVariable("id") Long id, @RequestBody @Valid DemoUserDto userDto,
                                              UriComponentsBuilder uriCB) {
        userservice.modifyUser(id, userDto);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * 根据ID 删除用户信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
        userservice.deleteUserById(id);
    }

    /**
     * 根据ID 删除用户信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/selectUpdate", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void batchUpdate(@RequestBody @Valid DemoUserUpdateDto userSelectDto) {
        logger.debug("userIds:" + userSelectDto.getUserIds());
        userservice.modifyUser(userSelectDto);
        // userservice.deleteUserById(id);
    }
    
    
    /**
     * 批量删除
     * @param userSelectDto
     */
    @RequestMapping(value = "/demoUsers/batch/delete", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void batchDelete(@RequestBody DemoUserUpdateDto userSelectDto) {
        logger.debug("userIds:" + userSelectDto.getUserIds());
        userservice.modifyUser(userSelectDto);
    }

    
    /**
     * 根据ID 删除用户信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}/address", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryUserAddress(@PathVariable("id") Long id) {
        List<Map> addressList = userservice.queryUserAddress(id);
        return addressList;
    }

    /**
     * 根据ID 删除用户地址分页信息
     * 
     * @author zhangxc
     * @date 2016年6月30日
     * @param id
     * @param uriCB
     */
    @RequestMapping(value = "/{id}/addressPage", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUserAddressPage(@PathVariable("id") Long id) {
        PageInfoDto addressList = userservice.queryUserAddressPage(id);
        return addressList;
    }

    /**
     * 上传文件
     * @throws Exception 
     **/
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public List<DemoUserDto> importUsers(@RequestParam(value = "file") MultipartFile importFile,DemoUserImportDto userImportDto, UriComponentsBuilder uriCB) throws Exception {
        
        // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<DemoUserDto> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<DemoUserDto>(DemoUserDto.class,new ExcelReadCallBack<DemoUserDto>() {
            @Override
            public void readRowCallBack(DemoUserDto rowDto, boolean isValidateSucess) {
                try{
                    logger.debug("userName:"+rowDto.getName());
                    // 保存用户,只有全部是成功的情况下，才执行数据库保存
                    if(isValidateSucess){
                        userservice.addUser(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));
        
        logger.debug("param:" + userImportDto.getFileParam());
        
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
    
    
    /**
     * 上传文件
     * @throws Exception 
     **/
    @RequestMapping(value = "/importFiles", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DemoUserDto> importUsersOfFiles(DemoUserImportDto userImportDto, UriComponentsBuilder uriCB) throws Exception {
        //返回值
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand("111").toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
}
