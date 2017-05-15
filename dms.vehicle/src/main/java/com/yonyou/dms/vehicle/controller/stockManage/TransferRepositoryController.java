
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : TransferRepositoryController.java
*
* @Author : yangjie
*
* @Date : 2017年1月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月8日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.controller.stockManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.service.basedata.StockInService;
import com.yonyou.dms.vehicle.service.stockManage.TransferService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月9日
 */

@Controller
@TxnConn
@RequestMapping("/transferRepository")
@SuppressWarnings({"rawtypes","unchecked"})
public class TransferRepositoryController extends BaseController {

    @Autowired
    private TransferService transferService;
    
    @Autowired
    private StockInService stockInService;

    @Autowired
    private CommonNoService commonNoService;
    
    private static String sNo;
    
    private static String[] vins;

    /**
     * 查询所有移库单 TODO description
     * 
     * @author yangjie
     * @date 2017年1月10日
     * @param map
     * @return
     */
    @RequestMapping(value = "/findAllRepositoryList", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllRepositoryList(@RequestParam Map<String, String> map) {
        return transferService.findAllRepository(map);
    }

    /**
     * 查询所有移库单明细 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param query
     * @return
     */
    @RequestMapping(value = "/findAllListDetails/{stNo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllListDetails(@PathVariable(value = "stNo") String stNo) {
        String no = stNo.substring(5, stNo.length());
        sNo = no;
        if (StringUtils.isNotBlank(no)) {
            return transferService.findAllRepositoryDetails(no);
        } else {
            return null;
        }
    }

    /**
     * 删除订单明细中的项 TODO description
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param stNo
     * @param vin
     * @param uriCB
     */
    @RequestMapping(value = "/{stNo}/{vin}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDetails(@PathVariable(value = "stNo") String stNo, @PathVariable(value = "vin") String vin,
                              UriComponentsBuilder uriCB) {
        String[] items = vin.split(",");
        for (int i = 0; i < items.length; i++) {
            transferService.deleteItem(stNo, items[i]);
        }
    }

    /**
     * 查询所有车辆信息 TODO description--移库用
     * 
     * @author yangjie
     * @date 2017年1月9日
     * @param map
     * @return
     */
    @RequestMapping(value = "/findAllVehicleInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findALlVehicleInfo(@RequestParam Map<String, String> map) {
        return transferService.findAllVehicleInfo(map);
    }
    
    /**
     * 查询车辆信息	--移位用
     * @param map
     * @return
     */
    @RequestMapping(value = "/findAllVehicleInfoForLocal", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllVehicleInfoForLocal(@RequestParam Map<String, String> map) {
        return transferService.findAllVehicleInfoForLocal(map);
    }

    /**
     * 查询所有经办人 TODO description
     * 
     * @author yangjie
     * @date 2017年1月10日
     * @return
     */
    @RequestMapping(value = "/findAllEmp", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllEmp() {
        return transferService.findAllEmp();
    }

    /**
     * 新增明细 TODO description
     * 
     * @author yangjie
     * @date 2017年1月10日
     * @param map
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/addDetails", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> addDetails(@RequestBody Map map,
                                                        UriComponentsBuilder uriCB) {
        Map<String, Object> dt = new HashMap<String,Object>();
        String no = commonNoService.getSystemOrderNo(CommonConstants.SAL_CLYKDH);
        dt.put("stNo", no);//移库单号
        dt.put("sheetCreatedBy", FrameworkUtil.getLoginInfo().getUserId());//经办人
        //dt.put("sheetCreateDate", map.get("sheetCreateDate"));//开单日期
        dt.put("remark", map.get("remark"));//备注
        transferService.addInfoToList(dt);//添加到移库单

        String[] vin = map.get("vins").toString().split(";");
        List<Map> allVIN = transferService.findAllVIN(vin);
        allVIN.size();
        List<Map> code = stockInService.findStorageCode();
        for (Map vm : allVIN) {
        	for (Map map2 : code) {//将仓库名称改成仓库代码
				if(map.get("inStorage")!=null){
					if(map.get("inStorage").toString().trim().equals(map2.get("STORAGE_NAME").toString().trim())){
						map.put("inStorage", map2.get("STORAGE_CODE").toString());
					}
				}
			}
            dt = new HashMap<String,Object>();
            dt.put("stNo", no);//移库单号
            dt.put("vin", vm.get("VIN"));
            dt.put("remark", map.get("remark"));//备注
            dt.put("takeCarMan", map.get("takeCarMan"));//提车人
            dt.put("productCode", vm.get("PRODUCT_CODE"));
            dt.put("purchasePrice", vm.get("PURCHASE_PRICE"));
            dt.put("marStatus", vm.get("MAR_STATUS"));
            dt.put("inStorage", map.get("inStorage"));
            dt.put("inPosition", map.get("inPosition"));
            dt.put("transactor", map.get("transactor"));
            dt.put("additionalCost", vm.get("ADDITIONAL_COST"));
            dt.put("directivePrice", vm.get("DIRECTIVE_PRICE"));
            dt.put("outPosition", vm.get("OUT_POSITION"));
            dt.put("outStorage", vm.get("OUT_STORAGE"));
            transferService.addInfoToItem(dt);
        }
        
        Map<String, String> para = new HashMap<String,String>();
        para.put("NO", no);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllListDetails/{stNo}").buildAndExpand("stNo:"+sNo).toUriString());
        return new ResponseEntity<Map<String, String>>(para,headers, HttpStatus.CREATED);
    }
    
    /**
     * 修改明细
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param map
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/editDetails", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> editDetails(@RequestBody Map map,
                                                        UriComponentsBuilder uriCB) {
        List<Map> list = transferService.findAllItem(map.get("stNo").toString());
        String[] strings = map.get("vins").toString().split(";");
        List<String> tt = new ArrayList<String>();//用来储存原有的VIN
        
        for (int i = 0; i < strings.length; i++) {
            Map ts = new HashMap();
            ts.put("ST_NO", map.get("stNo").toString());
            ts.put("VIN", strings[i]);
            ts.put("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
            if (list.contains(ts)) {
            	System.err.println(1);
                Map<String, Object> param = new HashMap<String,Object>();
                if(map.get("inPosition")!=null){
                    param.put("IN_POSITION", map.get("inPosition").toString());
                }
                if(map.get("inStorage")!=null){
                    param.put("IN_STORAGE", map.get("inStorage").toString());
                }
                param.put("ST_NO", map.get("stNo").toString());
                param.put("VIN", strings[i]);
                transferService.editItemByIn(param);
                tt.add(ts.get("VIN").toString());
            }else{
            	System.err.println(2);
                Map<String, Object> dt = new HashMap<String,Object>();
                Map vm = transferService.findAllVIN2(strings[i]);
                dt.put("stNo", map.get("stNo").toString());//移库单号
                dt.put("vin", vm.get("VIN"));
                dt.put("remark", map.get("REMARK"));//备注
                dt.put("productCode", vm.get("PRODUCT_CODE"));
                dt.put("purchasePrice", vm.get("PURCHASE_PRICE"));
                dt.put("marStatus", vm.get("MAR_STATUS"));
                dt.put("inStorage", map.get("inStorage"));
                dt.put("inPosition", map.get("inPosition"));
                dt.put("transactor", map.get("transactor"));
                dt.put("takeCarMan", vm.get("TAKE_CAR_MAN"));
                dt.put("additionalCost", vm.get("ADDITIONAL_COST"));
                dt.put("directivePrice", vm.get("DIRECTIVE_PRICE"));
                dt.put("outPosition", vm.get("OUT_POSITION"));
                dt.put("outStorage", vm.get("OUT_STORAGE"));
                transferService.addInfoToItem(dt);
                
                //新增后修改主表是否入账状态
                transferService.editList(map.get("stNo").toString());
            }
        }

        for (Map mp : list) {
            if (!tt.contains(mp.get("VIN").toString())) {//删除多于的列
                transferService.deleteItem(map.get("stNo").toString(), mp.get("VIN").toString());
            }
        }
        
        Map<String, String> para = new HashMap<String,String>();
        para.put("NO", map.get("stNo").toString());
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllListDetails/{stNo}").buildAndExpand("stNo:"+sNo).toUriString());
        return new ResponseEntity<Map<String, String>>(para,headers, HttpStatus.CREATED);
    }
    
    /**
     * 作废
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param map
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/deleteDetails", method = RequestMethod.DELETE)
    public ResponseEntity<Map<String, String>> cancleItem(UriComponentsBuilder uriCB) {
        
        transferService.deleteTransfer(sNo);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllListDetails/{stNo}").buildAndExpand("stNo:"+sNo).toUriString());
        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 移库
    * TODO description
    * @author yangjie
    * @date 2017年1月11日
    * @param map
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/moveTransfer/{ids}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> btnTransfer(@PathVariable(value = "ids") String ids,
                                                           UriComponentsBuilder uriCB) throws ServiceBizException {
        Map map = new HashMap();
        
        String[] str = ids.split(",");
        
        for (int i = 0; i < str.length; i++) {
            map.put(str[i].substring(0, str[i].indexOf("=")), str[i].substring(str[i].indexOf("=")+1,str[i].length()));
        }
        
        String[] split = map.get("vins").toString().split("!");
        for (int i = 0; i < split.length; i++) {
            Map param = new HashMap();
            List<Map> findStorageCode = stockInService.findStorageCode();
            for (Map map3 : findStorageCode) {
				if(map3.get("STORAGE_NAME")!=null){
					if(map3.get("STORAGE_NAME").equals(map.get("inStorage").toString())){
						param.put("storageCode", map3.get("STORAGE_CODE").toString());
					}
				}
			}
            param.put("storagePositionCode", map.get("inPosition").toString());
            param.put("vin", split[i]);
            param.put("stNo", map.get("stNo").toString());
            Map map2 = transferService.findAllVIN2(split[i]);
            if(map2!=null){
            	if (map2.get("PRODUCT_CODE") != null && map2.get("PRODUCT_CODE") != "") {
            		param.put("productCode", map2.get("PRODUCT_CODE").toString());
            	}
            	if (map2.get("PURCHASE_PRICE") != null && map2.get("PURCHASE_PRICE") != "") {
            		param.put("purchasePrice", map2.get("PURCHASE_PRICE").toString());
            	}
            	if (map2.get("MAR_STATUS") != null && map2.get("MAR_STATUS") != "") {
            		param.put("marStatus", map2.get("MAR_STATUS").toString());
            	}
            }
            transferService.btnTransfer(param);
        }
        
        transferService.refreshMainInfo(map.get("stNo").toString());//修改主表是否入账为是
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAllListDetails/{stNo}").buildAndExpand("stNo:"+sNo).toUriString());
        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    }
    
    @SuppressWarnings("static-access")
	@RequestMapping(value = "/moveLocation", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> editLocation(@RequestBody Map map,
                                                           UriComponentsBuilder uriCB) {
        String location = map.get("inPosition").toString();
        String[] vins = map.get("vins").toString().split(";");
        this.vins = vins;
        transferService.editLocation(vins, location);
        
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/transferRepository/findAllVehicleInfo").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 移库打印
     * @return
     */
    @RequestMapping(value = "/findAllPrintAbout", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> printInfo(){
    	if (StringUtils.isNotBlank(sNo)) {
            return transferService.findAllRepositoryDetails(sNo);
        } else {
            return null;
        }
    }
    
    @RequestMapping(value = "/printFirstShow",method = RequestMethod.GET)
    @ResponseBody
    public Map printFirstShow() {
        return transferService.printFirstShow(sNo);
    }
    
    /**
     * 移位打印
     * @return
     */
    @RequestMapping(value = "/findAllPrintLocation", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> printInfo2Location(){
        return transferService.findAllPrintLocation(vins);
    }
}
