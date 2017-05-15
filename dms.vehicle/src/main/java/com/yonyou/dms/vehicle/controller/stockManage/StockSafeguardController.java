
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : stockSafeguard.java
*
* @Author : yangjie
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    yangjie    1.0
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.vehicle.service.stockManage.StockSafeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * TODO description 库存维护相关交互
 * 
 * @author yangjie
 * @date 2016年12月28日
 */
@Controller
@TxnConn
@RequestMapping("/stockManage/safeguard")
@SuppressWarnings({"rawtypes","unchecked"})
@Scope("prototype")
public class StockSafeguardController extends BaseController {

    @Autowired
    private StockSafeService stockSafeService;
    
    @Autowired
    private ExcelGenerator excelService;

    /**
     * 查询所有库存信息 TODO 查询所有库存信息
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAll(@RequestParam Map<String, String> queryParam) {
        return stockSafeService.findAll(queryParam);
    }
    
    /**
     * 用于树状显示
    * TODO description
    * @author yangjie
    * @date 2016年12月31日
    * @return
     */
    @RequestMapping(value = "/findAllTree", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findAllTree(@RequestParam Map<String, String> queryParam){
        return stockSafeService.findAllTree(queryParam);
    }
    
    /**
     * 根据vin查询
    * TODO 根据vin查询
    * @author yangjie
    * @date 2016年12月31日
    * @param vin
    * @return
     */
    @RequestMapping(value = "/findByVin/{vin}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByVin(@PathVariable(value = "vin") String vin){
        return stockSafeService.findByVin(vin);
    }
    
    /**
     * 查询产品列表
    * TODO 查询产品列表
    * @author yangjie
    * @date 2017年1月3日
    * @param queryParam
    * @return
     */
    @RequestMapping(value = "/findProduct" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllProduct(@RequestParam Map<String, String> queryParam){
        return stockSafeService.findAllProduct(queryParam);
    }
    
    /**
     * 查询经销商简称下拉框 TODO 查询经销商简称下拉框
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @return
     */
    @RequestMapping(value = "/findDealerInfo", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findDealerInfo() {
        return stockSafeService.findDealerInfo();
    }

    /**
     * 查询仓库下拉框 TODO 查询仓库下拉框
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param id 经销商编号
     * @return
     */
    @RequestMapping(value = "/findStorage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findStorageInfo(@PathVariable(value = "id") String id) {
        return stockSafeService.findStorageInfo(id);
    }

    /**
     * 下拉框查询颜色 TODO 下拉框查询颜色
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @return
     */
    @RequestMapping(value = "/findColor", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findColor() {
        return stockSafeService.findColor();
    }

    /**
     * TODO 实现批量修改方法
     * 
     * @author yangjie
     * @date 2016年12月31日
     * @param ids
     */
    @RequestMapping(value = "/batchEdit/{ids}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> editByBatch(@PathVariable(value = "ids") String ids,
                                                           UriComponentsBuilder uriCB) {
    	System.err.println(ids);
        String[] items = ids.substring(5, ids.length()).split(",");
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < items.length; i++) {
            String[] child = items[i].split("=");
            if (child.length == 1) {
                map.put(child[0], null);
            } else {
                map.put(child[0], child[1]);
            }
            if ((i + 1) % 14 == 0) {
                list.add(map);
                map = new HashMap<String, String>();
            }
        }
        Integer index = stockSafeService.batchModify(list);
        Map<String, String> callback = new HashMap<String, String>();
        callback.put("back", index.toString());
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/findAll").buildAndExpand().toUriString());
        return new ResponseEntity<Map<String, String>>(callback,headers, HttpStatus.CREATED);
    }
    
    /**
     * 库存维护生成excel
    * TODO 库存维护生成excel
    * @author yangjie
    * @date 2017年1月2日
    * @param queryParam
    * @param request
    * @param response
     */
    @RequestMapping(value="/export/excel",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =stockSafeService.querySafeToExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_库存维护", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("dealer_shortname","经销商简称"));
        exportColumnList.add(new ExcelExportColumn("is_lock","是否锁定"));
        exportColumnList.add(new ExcelExportColumn("is_vip","是否VIP预留"));
        exportColumnList.add(new ExcelExportColumn("is_test_drive_car","是否试乘试驾车"));
        exportColumnList.add(new ExcelExportColumn("has_certificate","是否有合格证"));
        exportColumnList.add(new ExcelExportColumn("mar_status","质损状态"));
        exportColumnList.add(new ExcelExportColumn("is_promotion","是否促销车"));
        exportColumnList.add(new ExcelExportColumn("is_secondhand","是否二手车"));
        exportColumnList.add(new ExcelExportColumn("is_refitting_car","是否改装车"));
        exportColumnList.add(new ExcelExportColumn("config_name","配置"));
        exportColumnList.add(new ExcelExportColumn("color_name","颜色"));
        exportColumnList.add(new ExcelExportColumn("vin","VIN"));
        exportColumnList.add(new ExcelExportColumn("storage_code_name","仓库"));
        exportColumnList.add(new ExcelExportColumn("engine_no","发动机号"));
        exportColumnList.add(new ExcelExportColumn("certificate_number","合格证号"));
        exportColumnList.add(new ExcelExportColumn("key_number","钥匙编号"));
        exportColumnList.add(new ExcelExportColumn("vs_purchase_date","采购日期"));
        exportColumnList.add(new ExcelExportColumn("purchase_price","采购价格"));
        exportColumnList.add(new ExcelExportColumn("oem_directive_price","车厂指导价"));
        exportColumnList.add(new ExcelExportColumn("directive_price","销售指导价"));
        exportColumnList.add(new ExcelExportColumn("wholesale_directive_price","批售指导价"));
        exportColumnList.add(new ExcelExportColumn("oem_tag","OEM标志"));
        exportColumnList.add(new ExcelExportColumn("is_consigned","是否委托车"));
        exportColumnList.add(new ExcelExportColumn("storage_position_code","库位"));
        exportColumnList.add(new ExcelExportColumn("product_code","产品代码"));
        exportColumnList.add(new ExcelExportColumn("product_name","产品名称"));
        exportColumnList.add(new ExcelExportColumn("brand_name","品牌"));
        exportColumnList.add(new ExcelExportColumn("series_name","车系"));
        exportColumnList.add(new ExcelExportColumn("model_name","车型"));
        exportColumnList.add(new ExcelExportColumn("stock_status","库存状态"));
        exportColumnList.add(new ExcelExportColumn("dispatched_status","配车状态"));
        exportColumnList.add(new ExcelExportColumn("manufacture_date","生产日期"));
        exportColumnList.add(new ExcelExportColumn("first_stock_in_date","首次入库日期"));
        exportColumnList.add(new ExcelExportColumn("se_no","入库单号"));
        exportColumnList.add(new ExcelExportColumn("stock_in_type","入库类型"));
        exportColumnList.add(new ExcelExportColumn("sd_no","出库单号"));
        exportColumnList.add(new ExcelExportColumn("stock_out_type","出库类型"));
        exportColumnList.add(new ExcelExportColumn("factory_date","出厂日期"));
        exportColumnList.add(new ExcelExportColumn("remark","备注"));
        exportColumnList.add(new ExcelExportColumn("vsn","VSN"));
        exportColumnList.add(new ExcelExportColumn("exhaust_quantity","排气量"));
        exportColumnList.add(new ExcelExportColumn("discharge_standard","排放标准"));
        exportColumnList.add(new ExcelExportColumn("po_no","采购订单编号"));
        exportColumnList.add(new ExcelExportColumn("ec_order_no","官网订单号"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_库存维护.xls", request, response);
    }
    
    /**
     * 修改配置
    * TODO 修改配置
    * @author yangjie
    * @date 2017年1月3日
    * @param ids
    * @param map
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/editConfig/{vin}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String, String>> editBConfig(@PathVariable(value = "vin") String ids,@RequestBody Map map,
                                                           UriComponentsBuilder uriCB) {
        stockSafeService.editConfig(map);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/editConfig/{vin}").buildAndExpand(map.get("vin")).toUriString());
        return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);
    }
}
