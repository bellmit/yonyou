
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BalanceAccountsController.java
*
* @Author : ZhengHe
*
* @Date : 2016年9月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月26日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.balance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.repair.domains.DTO.balance.BalanceAccountsDTO;
import com.yonyou.dms.repair.service.balance.BalanceAccountsService;
import com.yonyou.dms.repair.service.order.RepairOrderService;
import com.yonyou.dms.repair.service.order.RoAddItemService;
import com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 结算单
* @author ZhengHe
* @date 2016年9月26日
*/
@Controller
@TxnConn
@RequestMapping("/balance/balanceAccounts")
public class BalanceAccountsController extends BaseController{

    @Autowired
    private RoAddItemService raiService;
    
    @Autowired
    private OrderLabourDetailService orderLabourDetailService;
    
    @Autowired
    private RepairOrderService roService;
    
    @Autowired
    private BalanceAccountsService balanceAccountsService;
    
    @Autowired
    private CommonNoService commonNoService;
    
    @Autowired
    private ExcelGenerator  excelService;
    
    /**
    * 根据Id获取附加项目信息
    * @author ZhengHe
    * @date 2016年9月26日
    * @param roId
    * @return
    */
    	
    @RequestMapping(value="/queryAddRoItem/{roId}",method=RequestMethod.GET)	
    @ResponseBody
    public List<Map> queryAddRoItem(@PathVariable Long roId){
        return raiService.queryAddRoItem(roId);
    }
    
    
    
    /**
    * 根据id获取维修项目信息
    * @author ZhengHe
    * @date 2016年9月26日
    * @param roId
    * @return
    */
    	
    @RequestMapping(value="/queryRoLabour/{roId}",method=RequestMethod.GET)   
    @ResponseBody
    public List<Map> queryRoLabour(@PathVariable Long roId){
        return orderLabourDetailService.queryReapirPro(roId);
    }
    
    
    /**
    * 根据id获取维修材料信息
    * @author ZhengHe
    * @date 2016年9月26日
    * @param roId
    * @return
    */
    	
//    @RequestMapping(value="/queryRoPart/{roId}",method=RequestMethod.GET)   
//    @ResponseBody
//    public List<Map> queryRoPart(@PathVariable Long roId){
//        return roService.queryRoPart(roId);
//    }
    
    
    
    /**
    * 根据id获取销售材料信息
    * @author ZhengHe
    * @date 2016年11月10日
    * @param roId
    * @return
    */
    	
//    @RequestMapping(value="/queryRoSalesPart/{roId}",method=RequestMethod.GET)   
//    @ResponseBody
//    public List<Map> queryRoSalesPart(@PathVariable Long roId){
//        return roService.queryRoSalesPart(roId);
//    }
    
    /**
    * 结算单查询
    * @author ZhengHe
    * @date 2016年10月18日
    * @param param
    * @return
    */
    	
    @RequestMapping(value="/querys/balanceAccounts",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBalanceAccounts(@RequestParam Map<String,String> param){
        return balanceAccountsService.queryBalanceAccounts(param,"true");
    }
    
    /**
    * 维修收款查询
    * @author jcsi
    * @date 2016年9月28日
    * @param param
    * @return
     */
    @RequestMapping(value="/query/repairAccounts",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchRepairAccounts(@RequestParam Map<String,String> param){
        return balanceAccountsService.search(param);
    }
    
    /**
    * 根据id查询 结算单收费对象列表
    * @author jcsi
    * @date 2016年9月28日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/balancePayobj")
    @ResponseBody
    public List<Map> findBalancePayobjById(@PathVariable Long id){
        return balanceAccountsService.findBalancePayobjById(id);
    }
    
   
    
    
    /**
    * 查询客户信息
    * @author ZhengHe
    * @date 2016年9月28日
    * @param param
    * @return
    */
    	
    @RequestMapping(value="/querys/CustomerOrVehicle",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerOrVehicle(@RequestParam Map<String,String> param){
        return balanceAccountsService.queryCustomerOrVehicle(param);
    }
    
    
    /**
    * 查询车主车辆信息
    * @author ZhengHe
    * @date 2016年9月28日
    * @param queryParam
    * @return
    */
    	
//    @RequestMapping(value="/querys/repairOrder",method=RequestMethod.GET)
//    @ResponseBody
//    public PageInfoDto queryRepairOrder(@RequestParam Map<String, String> queryParam){
//        PageInfoDto pageInfoDto=roService.queryRepairOrder(queryParam);
//        return pageInfoDto;
//    }
    
    
    /**
    * 根据RO_NO查询车主车辆信息
    * @author ZhengHe
    * @date 2016年10月26日
    * @param roNo
    * @return
    */
    	
//    @RequestMapping(value="/queryRepairOrder/{roNo}",method=RequestMethod.GET)
//    @ResponseBody
//    public List<Map> queryRepairOrder(@PathVariable String roNo,@RequestParam Map<String, String> queryParam){
//        List<Map> roList=roService.queryRepairOrderByRoNo(queryParam,"roNo",roNo);
//        return roList;
//    }
    
    /**
    * 新建结算单
    * @author ZhengHe
    * @date 2016年10月9日
    * @param baDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BalanceAccountsDTO> saveReceiveMoney(@RequestBody BalanceAccountsDTO baDto, UriComponentsBuilder uriCB){
        String balanceNo=commonNoService.getSystemOrderNo("BO");
        baDto.setBalanceNo(balanceNo);
        baDto.setPayOffMain(DictCodeConstants.STATUS_IS_NOT);
        Long id=balanceAccountsService.addBalanceAccounts(baDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/balance/balanceAccounts/{id}").buildAndExpand(id).toUriString());
        baDto.setBalanceId(id);
        return new ResponseEntity<BalanceAccountsDTO>(baDto, headers, HttpStatus.CREATED);
    }
    
    /**
     * 新建结算单(销售)
     * @author jcsi
     * @date 2016年10月9日
     * @param 
     * @param uriCB
     * @return
     */
    @RequestMapping(value="/balanceSales",method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<BalanceAccountsDTO> saveSalesReceiveMoney(@RequestBody BalanceAccountsDTO baDto, UriComponentsBuilder uriCB){
        String balanceNo=commonNoService.getSystemOrderNo("BO");
        baDto.setBalanceNo(balanceNo);
        baDto.setPayOffMain(DictCodeConstants.STATUS_IS_NOT);
        Long id=balanceAccountsService.addBalanceAccounts(baDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/balance/balanceAccounts/{id}").buildAndExpand(id).toUriString());
        baDto.setBalanceId(id);
        return new ResponseEntity<BalanceAccountsDTO>(baDto, headers, HttpStatus.CREATED);
    }
    
    /**
    * 导出excel（维修收款）
    * @author jcsi
    * @date 2016年10月9日
    * @param param
    * @param response
     */
    @RequestMapping(value = "/export/receiveMoneys", method = RequestMethod.GET)
    public void excelReceiveMoney(@RequestParam Map<String,String> param,HttpServletRequest request,HttpServletResponse response){
        List<Map> resultList = balanceAccountsService.excelReceiveMoney(param);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>(); 
        excelData.put("维修收款", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BALANCE_NO","结算单号"));
        exportColumnList.add(new ExcelExportColumn("PAYMENT_OBJECT_CODE","收费对象代码"));
        exportColumnList.add(new ExcelExportColumn("PAYMENT_OBJECT_NAME","收费对象名称"));
        exportColumnList.add(new ExcelExportColumn("RECEIVABLE_AMOUNT","应收账款"));
        exportColumnList.add(new ExcelExportColumn("RECEIVED_AMOUNT","已收账款"));
        exportColumnList.add(new ExcelExportColumn("DERATE_AMOUNT","已免账款"));
        exportColumnList.add(new ExcelExportColumn("NOT_RECEIVED_AMOUNT","未收账款"));
        exportColumnList.add(new ExcelExportColumn("BALANCE_TIME","结算时间",CommonConstants.SIMPLE_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("PAY_OFF","是否结清",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SQUARE_DATE","结清日期",CommonConstants.SIMPLE_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("BUSINESS_TYPE","业务类型",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("BUSINESS_NO","业务单号"));
        exportColumnList.add(new ExcelExportColumn("OWNER_NAME","车主"));
        exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
        excelService.generateExcel(excelData, exportColumnList, "维修收款.xls", request, response);
    }
    
    
    /**
    * 取消结算
    * @author ZhengHe
    * @date 2016年10月18日
    * @param baDto
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<BalanceAccountsDTO> calcleBalanceAmounts(@PathVariable Long id,@RequestBody BalanceAccountsDTO baDto, UriComponentsBuilder uriCB){
        baDto.setBalanceId(id);
        balanceAccountsService.cancelBalanceAccounts(baDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/balance/balanceAccounts/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<BalanceAccountsDTO>(baDto, headers, HttpStatus.CREATED);
    }
    
    
    /**
    * 获取结算单收费对象信息
    * @author ZhengHe
    * @date 2016年10月28日
    * @param id
    * @param paymentObjectCode
    * @return
    */
    	
    @RequestMapping(value="/queryBalancePayobj/{id}/{paymentObjectCode}",method=RequestMethod.GET)
    @ResponseBody
    public Map queryRepairOrder(@PathVariable Long id,@PathVariable String paymentObjectCode){
        Map bpMap=balanceAccountsService.selectBalancePayobj(id, paymentObjectCode);
        return bpMap;
    }
    
    
    /**
    * 根据id返回结算单信息
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map getBalanceAmountsById(@PathVariable Long id){
        return balanceAccountsService.findBalanceAmountsById(id);
    }
    
    /**
     * 根据id返回结算单信息(打印)
     * @author ZhengHe
     * @date 2016年12月08日
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/print",method=RequestMethod.GET)
    @ResponseBody
    public Map getBalanceAmountsPrintById(@PathVariable Long id){
        return balanceAccountsService.findBalanceAmountsById(id);
    }
    
    /**
    * 查询结算调拨出库单明细
    * @author jcsi
    * @date 2016年11月11日
    * @param id
    * @return
     */
    @RequestMapping(value="/balanceAllocate/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalanceAllocatePartById(@PathVariable Long id){
        return balanceAccountsService.queryBalanceAllocatePartById(id);
    }
    
    /**
    * 根据id查询结算单维修项目
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/balanceLabour/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalanceLaourById(@PathVariable Long id){
        return balanceAccountsService.queryBalanceLabour(id);
    }
    
    
    /**
    * 根据id查询结算单维修材料
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/balanceRepairPart/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalanceRepairPartById(@PathVariable Long id){
        return balanceAccountsService.queryBalanceRepairPart(id);
    }
    
    
    /**
    *  根据id查询结算附加项目
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/balanceAddItem/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalanceAddItemById(@PathVariable Long id){
        return balanceAccountsService.queryBalanceAddItem(id);
    }
    
    
    /**
    * 根据id查询结算单销售材料
    * @author ZhengHe
    * @date 2016年10月31日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/balanceSalesPart/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalanceSalesPartById(@PathVariable Long id){
        return balanceAccountsService.queryBalanceSalesPart(id);
    }
    
  
    
    /**
    * 根据id查询结算单结算对象
    * @author ZhengHe
    * @date 2016年11月11日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/balancePayobj/{id}",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getBalancePayobjById(@PathVariable Long id){
        return balanceAccountsService.queryBalancePayobj(id);
    }
    
    /**
     * 查询销售结算单
     * @author jcsi
     * @date 2016年11月11日
     * @param id
     * @return
      */
     @RequestMapping(value="/sales/{id}",method=RequestMethod.GET)
     @ResponseBody
    public Map queryBalanceSalesById(@PathVariable Long id){
         return balanceAccountsService.queryBalanceSalesById(id);
    }
     /**
      * 查询调拨结算单
      * @author jcsi
      * @date 2016年11月11日
      * @param id
      * @return
       */
      @RequestMapping(value="/allocate/{id}",method=RequestMethod.GET)
      @ResponseBody
     public Map queryBalanceAllocateById(@PathVariable Long id){
          return balanceAccountsService.queryBalanceAllocateById(id);
     }
}
