/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *
 * @Author : zhengcong
 *
 * @Date : 2017年5月3日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月3日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.advice;

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
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdviceLabourDTO;
import com.yonyou.dms.repair.domains.DTO.advice.RepairAdvicePartDTO;
import com.yonyou.dms.repair.service.advice.RepairAdviceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 工单维修建议controller
 * 
 * @author zhengcong	
 * @date 2017年5月3日
 */

@Controller
@TxnConn
@RequestMapping("/repair/advice")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RepairAdviceController {
	
	 @Autowired
	    private RepairAdviceService  adviceService ; 
	 
		/**
		 * 工单-建议配件查询
		 * 
		 * @author zhengcong
		 * @date 2017年5月3日
		 */
		@RequestMapping(value = "/queryPart/{vin}", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryPart(@PathVariable(value = "vin") String vin) {
			PageInfoDto pData = adviceService.queryPart(vin);
			return pData;

		}	 
	 	
		
		/**
		 * 工单-建议维修项目查询
		 * 
		 * @author zhengcong
		 * @date 2017年5月3日
		 */
		@RequestMapping(value = "/queryLabour/{vin}", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryLabour(@PathVariable(value = "vin") String vin) {
			PageInfoDto pData = adviceService.queryLabour(vin);
			return pData;

		}
		
		
		/**
		 * 工单-建议配件导入查询
		 * 
		 * @author zhengcong
		 * @date 2017年5月6日
		 */
		@RequestMapping(value = "/queryPartImport/{vin}", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryPartImport(@PathVariable(value = "vin") String vin) {
			PageInfoDto pData = adviceService.queryPartImport(vin);
			return pData;

		}	 
		
		/**
		 * 工单-建议项目导入查询
		 * 
		 * @author zhengcong
		 * @date 2017年5月6日
		 */
		@RequestMapping(value = "/queryLabourImport/{vin}/{code}", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryLabourImport(@PathVariable(value = "vin") String vin,
				@PathVariable(value = "code") String code) {
			PageInfoDto pData = adviceService.queryLabourImport(vin,code);
			return pData;

		}	
	 
	 
	 
	
	/**
	 * 新增、修改、删除配件-保存
	 * 
	 * @author zhengcong
	 * @date 2017年5月3日
	 */

    @RequestMapping(value = "/addSavePart",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RepairAdvicePartDTO> pSaveData(@RequestBody RepairAdvicePartDTO dataDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	adviceService.pSaveData(dataDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/repair/advice/addSavePart").buildAndExpand().toUriString());  
        return new ResponseEntity<RepairAdvicePartDTO>(dataDTO,headers, HttpStatus.CREATED);  
    }
	
	/**
	 * 新增、修改、删除项目-保存
	 * 
	 * @author zhengcong
	 * @date 2017年5月5日
	 */

    @RequestMapping(value = "/addSaveLabour",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<RepairAdviceLabourDTO> lSaveData(@RequestBody RepairAdviceLabourDTO dataDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
    	adviceService.lSaveData(dataDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/repair/advice/addSaveLabour").buildAndExpand().toUriString());  
        return new ResponseEntity<RepairAdviceLabourDTO>(dataDTO,headers, HttpStatus.CREATED);  
    }
	
	
	
	
	

}
