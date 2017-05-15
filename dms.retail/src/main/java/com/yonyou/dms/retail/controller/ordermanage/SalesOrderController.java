
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : DecroDateController.java
 *
 * @Author : xukl
 *
 * @Date : 2016年9月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月5日    xukl    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.controller.ordermanage;

import java.util.HashMap;
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

import com.yonyou.dms.common.domains.DTO.basedata.SalesReturnDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;
import com.yonyou.dms.retail.service.ordermanage.SalesOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售订单Controller
 * @author xukl
 * @date 2016年9月5日
 */
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/ordermanage/salesOrders")
public class SalesOrderController extends BaseController{
    @Autowired
    private SalesOrderService salesOrderService;
    @Autowired
    private CommonNoService  commonNoService;
    @Autowired
    private SystemParamService  systemparamservice;
    /**
     * 销售订单查询
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrders(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySalesOrders(queryParam);
        return pageInfoDto;
    }
    /**
     * 查询审核通过批售单及配置
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/cusWholesaleInfo" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPoCusWholesaleInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.queryPoCusWholesaleInfo(queryParam);
        return pageInfoDto;
    }
    /**
     * 销售意向单的查询
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/getIntentSalesOrder" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getIntentSalesOrder(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.getIntentSalesOrder(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 销售意向单的查询
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/matchVehicle" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMatchVehicleByCodeDetail(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.queryMatchVehicleByCodeDetail(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 经理审核查询
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/{oldSoNo}/sales",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryOldSalesOrders(@RequestParam Map<String, String> queryParam,@PathVariable("oldSoNo") String oldSoNo) {
        List<Map> pageInfoDto = salesOrderService.qrySalesOrdersDetial(queryParam,oldSoNo);
        return pageInfoDto;
    }
//  审核人
    
    @RequestMapping(value = "/function" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrdersFunction(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForFunction(queryParam);
        return pageInfoDto;
    }
    
/*    *//**
     * 提交审核
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     *//*
    @RequestMapping(value = "/commitAudit/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> commidAudit(@PathVariable(value = "id") String id) {
        System.out.println("111111111111111111111");
        System.out.println(id);
        List<Map> result  = salesOrderService.commidAudit(id); 
        return result;
    }*/
    /**
     * 根据订单查询
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param vehicle_id
     * @return
     */
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/{SO_NO}/saleRe", method = RequestMethod.GET)
    @ResponseBody
    public Map querysoNo(@PathVariable(value = "SO_NO") String soNo,@RequestParam Map<String, String> queryParam) {
        Map pp=new HashMap();
        queryParam.put("businessType", "13001005");
        //String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        List<Map> addressList = salesOrderService.querysoNo(soNo,queryParam);
        if (addressList.size()>0){
        	
            pp.putAll(addressList.get(0));  
        }
        return pp;
    } 
    @RequestMapping(value = "/submit" ,method = RequestMethod.POST)
    public ResponseEntity<Map> addSalesOrderSumbit(@RequestBody @Valid SalesReturnDTO salesReturnDTO) {
        Map map = salesOrderService.addSalesOrdersSubmit(salesReturnDTO);
        System.out.println(map);
        MultiValueMap<String,String> headers = new HttpHeaders();  
   //     headers.set("Location", uriCB.path("/{fIsChanged}/sales").buildAndExpand(map.get("SO_NO")).toUriString());  
        return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);  

    }
    @RequestMapping(value = "/manageVerify/query" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrdersForMngVrfy(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForMangAudit(queryParam);
        return pageInfoDto;
    }
    /**
     * 财务经理审核查询
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/FinanceVerify/query" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrdersForFinVrfy(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForFincAudit(queryParam);
        return pageInfoDto;
    }
    /**
     * 销售退回查询销售订单
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/sellBack/qurySaleBackOrder" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySRSForSellBack(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForSellBack(queryParam);
        return pageInfoDto;
    }
    /**
     * 销售退回查询选择销售订单
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/saleBack/saleBackOrderSel" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySRSForSBKSlt(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForSBKSlt(queryParam);
        return pageInfoDto;
    }
    /**
     * 新增
     * @author xukl
     * @date 2016年9月18日
     * @param salesOrderDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{fIsChanged}/sales" ,method = RequestMethod.POST)
    public ResponseEntity<Map> addSalesOrder(@RequestBody @Valid SalesReturnDTO salesReturnDTO,UriComponentsBuilder uriCB,@PathVariable("fIsChanged") String fIsChanged) {
        Map map = salesOrderService.addSalesOrders(salesReturnDTO,salesReturnDTO.getOldSoNo(),fIsChanged);
        System.out.println(map);
        MultiValueMap<String,String> headers = new HttpHeaders();  
   //     headers.set("Location", uriCB.path("/{fIsChanged}/sales").buildAndExpand(map.get("SO_NO")).toUriString());  
        return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);  

    }

    /**
     * 修改
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @param salesOrderDTO
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{fIsChanged}/sales", method = RequestMethod.PUT)
    public ResponseEntity<SalesOrderDTO> modifySalesOrder(@PathVariable("fIsChanged") String fIsChanged,@RequestBody @Valid SalesReturnDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        salesOrderService.updateSalesOrder(fIsChanged, salesOrderDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();  
   //     headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<SalesOrderDTO>(headers, HttpStatus.CREATED);  
    }

    @RequestMapping(value = "/{SO_NO}/soAudit" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySubmitSalesHis(@RequestParam Map<String, String> queryParam,@PathVariable("SO_NO") String soNo) {
        PageInfoDto pageInfoDto = salesOrderService.querySubmitSalesHis(queryParam,soNo);
        return pageInfoDto;
    }

    
    /**
     * 根据id查询销售单
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map getSalesOrderById(@PathVariable(value = "id") String id) {
        Map map = salesOrderService.getSalesOrderById(id);
        return map;
    }
    
    /**
    * 销售订单打印
    * @author zhanshiwei
    * @date 2016年11月29日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}/printInfo",method = RequestMethod.GET)
    @ResponseBody
    public Map getPritSalesOrderById(@PathVariable(value = "id") Long id) {
        Map map = salesOrderService.getPritSalesOrderById(id);
        return map;
    }
    /**
     * 根据id装潢项目明细
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}/decorationproject",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryDecorationProject(@PathVariable(value = "id") Long id) {
        List<Map> list = salesOrderService.qryDecorationProject(id);
        return list;
    }
    /**
     * 根据id装潢配件明细
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     */


    @RequestMapping(value = "/{id}/deracotionmaterial",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qryDeracotionMaterial(@PathVariable(value = "id") Long id) {
        List<Map> list = salesOrderService.qryDeracotionMaterial(id);
        return list;
    }
    /**
     * 根据id查询服务项目明细
     * @author xukl
     * @date 2016年9月18日
     * @param id
     * @return
     */

    @RequestMapping(value = "/{id}/serviceproject",method = RequestMethod.GET)
    @ResponseBody
    public List<Map>  qryServiceProject(@PathVariable(value = "id") Long id) {
        List<Map> list = salesOrderService.qryServiceProject(id);
        return list;
    }

    /**
     * 提交
     * @author xukl
     * @date 2016年9月22日
     * @param id
     * @param salesOrderDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}/submit", method = RequestMethod.PUT)
    public ResponseEntity<SalesOrderDTO> submitSalesOrder(@PathVariable("id") Long id,@RequestBody SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        List<BasicParametersDTO> basiDtolist = systemparamservice.queryBasicParameterByType(Long.valueOf(DictCodeConstants.VEHICLE_BASIC_CODE));
        salesOrderService.submitSalesOrder(id, salesOrderDTO,basiDtolist);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<SalesOrderDTO>(headers, HttpStatus.CREATED); 
    }
    
    /**
    * 作废
    * @author xukl
    * @date 2016年9月26日
    * @param id
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{SO_NO}/cancel", method = RequestMethod.DELETE)
    public ResponseEntity<SalesOrderDTO> cancelSalesOrder(@PathVariable("SO_NO") String soNo,UriComponentsBuilder uriCB) {
        salesOrderService.cancelSalesOrder(soNo);
        MultiValueMap<String, String> headers = new HttpHeaders();  
   //     headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<SalesOrderDTO>(headers, HttpStatus.CREATED); 
    }
    
    /**
     * 查询作废权限
     * 
     * @author xhy
     * @date 2017年3月22日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryMenuAction()
     */

    @SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/menuAction", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryMenuAction() {
        List<Map> result = salesOrderService.queryMenuAction();
        System.out.println(result.size());
        System.out.println("1111111111111");
        System.err.println(result.size());
        
        return result;
    }
    /**
     * 客户投诉选择销售订单
     * @author zhanshiwei
     * @date 2016年9月23日
     * @param queryParam
     * @return
     */

    @RequestMapping(value="/cusComplaint/SalesOrderSel",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qryComplaintSalesOrders(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qryComplaintSalesOrders(queryParam);
        return pageInfoDto;
    }
    
    /**
    * 开票登记选销售订单
    * @author DuPengXin
    * @date 2016年10月9日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/registers/salesOrderSel", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrySalesOrderRegister(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.qrySRSForInvoice(queryParam);
        return pageInfoDto;
    }
    
    

    
    
    /**
    * 新增销售退回单
    * @author xukl
    * @date 2016年10月9日
    * @param salesOrderDTO
    * @param uriCB
    * @return
    */
    @RequestMapping(value="/sellback",method = RequestMethod.POST)
    public ResponseEntity<Map> addSellBack(@RequestBody @Valid SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        Map map = salesOrderService.addSellBack(salesOrderDTO,commonNoService.getSystemOrderNo(CommonConstants.SO_NO_PREFIX));
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(map.get("SO_NO_ID")).toUriString());  
        return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);  

    }
    
    /**
    * 销售退回编辑
    * @author xukl
    * @date 2016年10月11日
    * @param id
    * @param salesOrderDTO
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{id}/sellback", method = RequestMethod.PUT)
    public ResponseEntity<SalesOrderDTO> modifySellBack(@PathVariable("id") Long id,@RequestBody @Valid SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        salesOrderService.updateSellBack(id, salesOrderDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<SalesOrderDTO>(headers, HttpStatus.CREATED);  
    }
    /**
     * 销售退回提交
     * @author xukl
     * @date 2016年9月22日
     * @param id
     * @param salesOrderDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}/sellbacksubmit", method = RequestMethod.PUT)
    public ResponseEntity<SalesOrderDTO> submitSellBack(@PathVariable("id") Long id,@RequestBody SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        List<BasicParametersDTO> basiDtolist = systemparamservice.queryBasicParameterByType(Long.valueOf(DictCodeConstants.VEHICLE_BASIC_CODE));
        salesOrderService.submitSellBack(id, salesOrderDTO,basiDtolist);
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<SalesOrderDTO>(headers, HttpStatus.CREATED); 
    }
    
    /**
    * 结算收款查询销售订单
    * @author xukl
    * @date 2016年10月13日
    * @param queryParam
    * @return
    */
    @RequestMapping(value="/balance/slctSalesOrders",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qryOrdersForStle(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.slctSalesOrders(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询客户信息,和意向ID
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/customerAndIntent" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerAndIntent(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.queryCustomerAndIntent(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询客户信息,和意向ID
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/customerAndIntent1" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerAndIntent1(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = salesOrderService.queryCustomerAndIntent1(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询客户信息,和意向ID
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/oldCustomerVin/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryoldCustomerVin(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = salesOrderService.queryoldCustomerVin(id);
        return pageInfoDto;
    }
    

    /**
     * 查询贷款利率
     * @author LGQ
     * @date 2017年2月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/query/allLoanRate/{id}" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryallLoanRate(@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = salesOrderService.queryallLoanRate(id);
        return pageInfoDto;
    }
    
    /**
     * 新增
     * @author LGQ
     * @date 2017年2月17日
     * @param salesOrderDTO
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Map> addSalesOrderL(@RequestBody  SalesOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
        System.out.println("执行保存开始");
        String soNo = "";
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())){
            System.out.println("11111111");
            soNo=salesOrderDTO.getSoNo();
            System.out.println(soNo);
        }else{
            System.out.println("2222222");
            soNo =commonNoService.getSystemOrderNo(CommonConstants.SAL_ZZXSDH);
            System.out.println(soNo);
        }
        Map map = salesOrderService.addSalesOrder(salesOrderDTO,soNo);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders/{id}").buildAndExpand(map.get("SO_NO")).toUriString());  
        return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);  

    }
    @RequestMapping(value = "/submit/checkVerified", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void submitAndCheckVerified(@RequestBody SalesOrderDTO salesOrderDTO,
                                           UriComponentsBuilder uriCB) {
        salesOrderService.submitAndCheckVerified(salesOrderDTO);
    }

    /**
     * 提交审核
     */
    @RequestMapping(value = "/commit/commitAudit", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map> saveCommitAudit(@RequestBody SalesOrderDTO salesOrderDTO,
                                           UriComponentsBuilder uriCB) {
        Map map = salesOrderService.saveCommitAudit(salesOrderDTO);  
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOrders").buildAndExpand().toUriString());  
        return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);  
    }
    
    
    /**
     * 审批记录
     * 
     * @author LGQ
     * @date 2016年1月1日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "commitAudit/Audit", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> commidAudit(@RequestParam Map<String, String> queryParam) {
        System.out.println("111111111111111111111");
        List<Map> result  = salesOrderService.commidAudit(queryParam); 
        return result;
    }
    
    
    /**
     * 检查潜客的的信息
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/check/tmPoTentialCustomer/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> checktmPoTentialCustomer(@PathVariable(value = "id") String id) {
        List<Map> pageInfoDto = salesOrderService.checktmPoTentialCustomer(id); 
        return pageInfoDto;
    }
    
    /**
     * 检查潜客的的信息
     * 
     * @author LGQ
     * @date 2016年2月20日
     * @param queryParam
     * @return
     */

    @RequestMapping(value = "/check/saveEcOrder/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String checksaveEcOrder(@PathVariable(value = "id") String id) {
        String pageInfoDto = salesOrderService.checksaveEcOrder(id); 
        return pageInfoDto;
    }
    
    /**
     * 销售退回选销售订单
     * @author DuPengXin
     * @date 2016年10月9日
     * @param queryParam
     * @return
     */
         
     @RequestMapping(value = "/registers/salesOrderSel/set", method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto qrySalesOrderRegisterSet(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = salesOrderService.qrySRSForInvoiceSet(queryParam);
         return pageInfoDto;
     }
    
}
