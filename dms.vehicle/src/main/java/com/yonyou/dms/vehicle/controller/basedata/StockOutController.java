
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockOutController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月21日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月21日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.controller.basedata;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.StockOutListDTO;
import com.yonyou.dms.vehicle.service.basedata.StockOutService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 整车出库控制类
 * @author DuPengXin
 * @date 2016年9月21日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/stockOut")
@SuppressWarnings({"rawtypes","unchecked"})
public class StockOutController extends BaseController{

    @Autowired
    private StockOutService stockoutservice;

    /**
     * 查询出库主表
    * TODO description
    * @author yangjie
    * @date 2017年2月7日
    * @param queryParam
    * @return
     */
    @RequestMapping(value = "/findAllItems", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllOutItems(@RequestParam Map<String, String> queryParam) {
        return stockoutservice.findAllOutItems(queryParam);
    }

    /**
     * 查询车辆信息
     */
    @RequestMapping(value = "/findAllVehicle", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllVehicle(@RequestParam Map<String, String> queryParam) {
        return stockoutservice.findAllVehicle(queryParam);
    }
    
    /**
     * 查询出库子表信息
    * TODO description
    * @author yangjie
    * @date 2017年2月15日
    * @param sdNo
    * @return
     */
    @RequestMapping(value = "/findAllDetails/{sdNo}/{vin}/{productCode}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllDetails(@PathVariable String sdNo,@PathVariable String vin,@PathVariable String productCode){
        if (!StringUtils.isNullOrEmpty(sdNo)) {
            return stockoutservice.findAllOutDetails(sdNo, vin, productCode);
        }else{
            return null;
        }
    }

    /**
     * 下拉框查询行业大类
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @return
     */
    @RequestMapping(value = "/findIndustryFirst",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findIndustryFirst(){
        return stockoutservice.findIndustryFirst();
    }
    
    /**
     * 下拉框查询行业小类
    * TODO description
    * @author yangjie
    * @date 2017年2月14日
    * @param first
    * @return
     */
    @RequestMapping(value = "/findIndustrySecond/{IndustryFirst}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findIndustrySecond(@PathVariable(value = "IndustryFirst") String first){
        return stockoutservice.findIndustrySecond(first);
    }
    
    /**
     * 查询保有客户信息
    * TODO description
    * @author yangjie
    * @date 2017年2月15日
    * @param vin
    * @param soNo
    * @return
     */
    @RequestMapping(value = "/findCusInfo/{vin}/{soNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map findCustomerInfo(@PathVariable String vin,@PathVariable String soNo){
        return stockoutservice.findCustomerInfo(vin, soNo);
    }
    
    /**
     * 保存出库单
    * TODO description
    * @author yangjie
    * @date 2017年2月16日
    * @param stockOutDto
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/saveStockOut", method = RequestMethod.POST)
    public ResponseEntity<String> btnSave(@RequestBody StockOutListDTO stockOutDto, UriComponentsBuilder uriCB){
        String sdNo = "";
        sdNo = stockoutservice.btnSave(stockOutDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllDetails/{sdNo}/{vin}/{productCode}").buildAndExpand(sdNo,null,null).toUriString());
        return new ResponseEntity<String>(sdNo, headers, HttpStatus.CREATED);
    }
    
    /**
     * 车辆出库
     * @param param
     * @param sdNo
     * @param outType
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/btnStockOut/{sdNo}/{outType}", method = RequestMethod.POST)
    public ResponseEntity<String> btnStockOut(@PathVariable String sdNo,@PathVariable(value="outType") String vin, UriComponentsBuilder uriCB){
    	String[] split = vin.split(",");
    	String vins = "";//用来储存出库时选择的VIN号
    	List<Map> findAllOutDetails = new ArrayList<Map>();
    	if (split.length>1) {//表示批量出库
    		for (int i = 0; i < split.length; i++) {
				if(i==split.length-1){
					vins += "'"+split[i]+"'";
				}else{
					vins += "'"+split[i]+"',";
				}
			}
    		findAllOutDetails = stockoutservice.findAllOutDetailsForBatch(sdNo, vins, null);
		}else{
			if(split.length!=0){
				findAllOutDetails = stockoutservice.findAllOutDetails(sdNo, split[0], null);
			}
		}
        stockoutservice.btnStockOut(findAllOutDetails, sdNo, null);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllDetails/{sdNo}/{vin}/{productCode}").buildAndExpand(sdNo,sdNo,null).toUriString());
        return new ResponseEntity<String>(null, headers, HttpStatus.CREATED);
    }
    
    /**
	 * 删除操作 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param id
	 */
	@RequestMapping(value = "/{vin}/{sdNo}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteItem(@PathVariable String vin,@PathVariable String sdNo) {
		Map map = new HashMap();
		map.put("VIN", vin);
		map.put("SD_NO", sdNo);
		stockoutservice.deleteOutItems(map);
	}
	
	/**
	 * 打印
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/loadPrintInfo/{vins}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findPrintInfo(@PathVariable String vins) {
		String[] vin = vins.split(",");
		String item = "";
		for (int i = 0; i < vin.length; i++) {
			if (i==vin.length-1) {//表示最后一条
				item += "'"+vin[i]+"'";
			}else{
				item += "'"+vin[i]+"',";
			}
		}
		return stockoutservice.findPrintAbout(item);
	}
}
