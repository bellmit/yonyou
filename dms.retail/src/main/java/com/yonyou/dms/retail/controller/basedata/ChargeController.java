
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ChargeController.java
*
* @Author : Administrator
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.basedata;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.ChargeDTO;
import com.yonyou.dms.retail.domains.PO.basedata.ChargePo;
import com.yonyou.dms.retail.service.basedata.ChargeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 费用区分
* @author zhongshiwei
* @date 2016年7月10日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/ChargeCO")
@SuppressWarnings("rawtypes")
public class ChargeController extends BaseController{
    
    @Autowired
    private ChargeService chargeService;
    /**
    * 根据查询条件返回对应的用户数据
    * @author zhongshiwei
    * @date 2016年7月12日
    * @param chargeSQL
    * @return 
    * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto chargeSql(@RequestParam Map<String, String> chargeSQL) throws ServiceBizException{
            PageInfoDto pageInfoDto = chargeService.ChargeSQL(chargeSQL);
            return pageInfoDto;
        }        
    /**
     * 
    * 新增用户信息
    * @author zhongshiwei
    * @date 2016年7月12日
    * @param chargeDTO
    * @param uriCB
    * @return 
    * @throws ServiceBizException
     */
    @RequestMapping(value="/Charge",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ChargeDTO> addInsurance(@RequestBody ChargeDTO chargeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        String id = chargeService.insertCharge(chargeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/ChargeCO/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<ChargeDTO>(chargeDTO,headers, HttpStatus.CREATED);  
    }

    /**
     * 
    * 根据ID 修改用户信息
    * @author zhongshiwei
    * @date 2016年7月12日
    * @param id
    * @param chargeDTO
    * @param uriCB
    * @return 
    * @throws ServiceBizException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ChargeDTO> updateCharge(@PathVariable("id") String id,@RequestBody ChargeDTO chargeDTO,UriComponentsBuilder uriCB) throws ServiceBizException {
        chargeService.updateCharge(id,chargeDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/ChargeCO/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<ChargeDTO>(headers, HttpStatus.CREATED);  
    }

    /**
    * 根据ID 删除用户信息
    * @author zhongsw
    * @date 2016年7月12日
    * @param id
    * @param uriCB
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharge(@PathVariable("id") String id,UriComponentsBuilder uriCB) throws ServiceBizException{
        chargeService.deleteChargeById(id);
    }
    
    /**
    * 根据id查找
    * @author zhongsw
    * @date 2016年7月12日
    * @param id
    * @return 查询结果
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable String id) throws ServiceBizException{
        ChargePo wtpo= chargeService.findById(id);
        return wtpo.toMap();
    }
     
    /**
    * 收费区分下拉框加载
    * @author ZhengHe
    * @date 2016年8月22日
    * @return
    */
	@RequestMapping(value="/Charge/dicts",method = RequestMethod.GET)
     @ResponseBody
     public List<Map> selectCharge() {
         List<Map> chargelist = chargeService.selectCharge();
         return chargelist;
     }
    
    /**
     * 收费区分下拉框加载2
    * @author rongzoujie
    * @date 2016年11月8日
    * @return
     */
     @RequestMapping(value="/Charge/dictexs",method = RequestMethod.GET)
      @ResponseBody
      public List<Map> selectCharge2() {
          List<Map> chargelist = chargeService.selectCharge2();
          return chargelist;
      }
    
    /**
    * 根据code获取charge
    * @author ZhengHe
    * @date 2016年8月26日
    * @param code
    * @return
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value="/{code}/getCharge",method=RequestMethod.GET)
    @ResponseBody
    public Map queryChargeByCode(@PathVariable String code)throws ServiceBizException{
        Map chargeMap=chargeService.queryChargeByCode(code);
        return chargeMap;
    }

    
    /**
    * 根据name获取charge
    * @author ZhengHe
    * @date 2016年10月20日
    * @param name
    * @return
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value="/getCharge/{name}",method=RequestMethod.GET)
    @ResponseBody
    public Map queryChargeByName(@PathVariable String name)throws ServiceBizException{
        Map chargeMap=chargeService.queryChargeByName(name);
        return chargeMap;
    }

}
