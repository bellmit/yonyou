
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : SalessynthesisController.java
*
* @Author : zhongsw
*
* @Date : 2016年10月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月8日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.controller.ordermanage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.ordermanage.SalesOrderDTO;
import com.yonyou.dms.retail.service.ordermanage.SalessynthesisService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 销售综合查询
* @author zhongsw 
* @date 2016年10月8日
*/

@Controller
@TxnConn
@RequestMapping("/ordermanage/salesSynthesis")
public class SalessynthesisController extends BaseController{
    
    @Autowired
    private SalessynthesisService salesSynthesisService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**查询
     */
         
     @RequestMapping(method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto searchsalesSynthesis(@RequestParam Map<String, String> queryParam) {
         PageInfoDto pageInfoDto = salesSynthesisService.searchsalesSynthesis(queryParam);
         return pageInfoDto;
     }
     
     /**
      * 根据id查询销售单
      * @author xukl
      * @date 2016年9月18日
      * @param id
      * @return
      */

     @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
     @ResponseBody
     public Map getSalesOrderById(@PathVariable(value = "id") String id) {
         Map map = salesSynthesisService.getSalesOrderById(id);
         return map;
     }
     
     
    /**
    * 查询客户信息(基础信息)
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    */
    	
     @RequestMapping(value = "/{id}/customerInfo", method = RequestMethod.GET)
     @ResponseBody
     public Map<String, Object> querycustomerInfoById(@PathVariable("id") Long id) {
         Map<String, Object> potentialCusPo = salesSynthesisService.queryCustomerInfoById(id);
         return potentialCusPo;
     }
    
    
    
    /**
    * 查询客户信息(意向信息)
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    */
    	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{id}/customerItem", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryInfoByParendId(@PathVariable("id") Long id) {
        List<Map> result = salesSynthesisService.queryCustomerInfoByParendId(id);
        return result;
    }
    
    
    /**
    * 订单详细
    * @author zhongsw
    * @date 2016年10月10日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}/orderInfo", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryorderInfoById(@PathVariable("id") Long id) {
        Map<String, Object> potentialCusPo = salesSynthesisService.queryorderInfoById(id);
        return potentialCusPo;
    }
     
    /**
    * 审核记录查询
    * @author zhongsw
    * @date 2016年10月9日
    * @param id
    * @return
    */
    	
    @RequestMapping(value="/{id}/auditing",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto searchAuditing(@PathVariable String id) {
         PageInfoDto pageInfoDto = salesSynthesisService.searchAuditing(id);
         return pageInfoDto;
     }
    
    /**
     * 订单解锁
     * 
     * @author xiahaiyang
     * @date 2017年1月13日
     * @param sono
     * @param salesOrderDto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/orderUnlock", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.CREATED)
    public void modifyReRetainCusTrack(@RequestBody SalesOrderDTO salesOrderDto,
                                           UriComponentsBuilder uriCB) {
       
        salesSynthesisService.modifySalesOrderInfo(salesOrderDto);
    }
    
    /**
     * 根据id查询销售单
     * @author xhy
     * @date 2017年3月13日
     * @param id
     * @return
     */

    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/insurance/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map getInsuranceById(@PathVariable(value = "id") String soNo) {
    	System.out.println("******"+soNo);
        Map map = salesSynthesisService.getInsuranceById(soNo);
        return map;
    }
    
    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     */

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/PotentialCus/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryPotentialCusInfoByid(@PathVariable(value = "id") String id) {
        List<Map> result = salesSynthesisService.queryPotentialCusInfoByid(id);
        
        return result.get(0);
    }
    
    /**
     * 查询打印权限
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
        List<Map> result = salesSynthesisService.queryMenuAction();
        System.out.println(result.size());
        System.out.println("1111111111111");
        System.err.println(result.size());
        
        return result;
    }
    /**
     * 潜客信息导出
     * 
     * @author LGQ
     * @date 2016年9月11日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */

    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPotentialCus(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = salesSynthesisService.querySalesOrderInfoExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("销售综合查询", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BUSINESS_TYPE", "业务类型"));
        exportColumnList.add(new ExcelExportColumn("SO_NO", "销售订单编号"));
        exportColumnList.add(new ExcelExportColumn("SO_STATUS", "单据状态"));
        exportColumnList.add(new ExcelExportColumn("IS_SPEEDINESS", "是否快速订单"));
        exportColumnList.add(new ExcelExportColumn("ORDER_SUM", "订单总额"));
        exportColumnList.add(new ExcelExportColumn("SALES_DATE", "销售日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("SHEET_CREATE_DATE", "开单日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("DELIVERING_DATE", "预定交车日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("INVOICE_NO", "发票编号"));
        exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("DIRECTIVE_PRICE", "销售指导价"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME", "销售顾问"));
        exportColumnList.add(new ExcelExportColumn("AUDITED_BY", "经理审核人"));
        exportColumnList.add(new ExcelExportColumn("RE_AUDITED_BY", "财务审核人"));
        exportColumnList.add(new ExcelExportColumn("OPERATOR", "关单人"));       
        exportColumnList.add(new ExcelExportColumn("BALANCE_CLOSE_TIME", "关单日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "客户编号"));
        exportColumnList.add(new ExcelExportColumn("CUSTOMER_TYPE", "客户类型"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        
       
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
        exportColumnList.add(new ExcelExportColumn("CONFIG_NAME", "配置"));  
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
        exportColumnList.add(new ExcelExportColumn("CONTACTOR_NAME", "联系人"));
        exportColumnList.add(new ExcelExportColumn("PHONE", "电话"));
        exportColumnList.add(new ExcelExportColumn("CONTRACT_NO", "合约编号"));
        exportColumnList.add(new ExcelExportColumn("CONTRACT_DATE", "签约日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("CONTRACT_MATURITY", "合约到期日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("CT_CODE", "证件类型"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NO", "证件号码"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("WS_TYPE", "批售类型"));
        exportColumnList.add(new ExcelExportColumn("OTHER_AMOUNT", "其他金额"));
        exportColumnList.add(new ExcelExportColumn("OTHER_AMOUNT_OBJECT", "其他金额对象"));
        exportColumnList.add(new ExcelExportColumn("OTHER_PAY_OFF", "其他金额是否已付"));
        
        exportColumnList.add(new ExcelExportColumn("ADDRESS", "客户联系地址"));
        exportColumnList.add(new ExcelExportColumn("LOCK_USER", "锁定人"));
        exportColumnList.add(new ExcelExportColumn("MEDIA_DETAIL", "渠道细分"));
        exportColumnList.add(new ExcelExportColumn("IS_NOW_ORDER_CAR", "是否当时订车"));
        exportColumnList.add(new ExcelExportColumn("FIRST_COMMIT_TIME", "首次提交审核时间",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("LAST_COMMIT_TIME", "最近提交审核时间",CommonConstants.FULL_DATE_TIME_FORMAT));
        exportColumnList.add(new ExcelExportColumn("ABORTING_DATE", "取消日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("VEHICLE_PRICE", "车辆价格"));
        exportColumnList.add(new ExcelExportColumn("GARNITURE_SUM", "精品金额"));
        exportColumnList.add(new ExcelExportColumn("UPHOLSTER_SUM", "装潢金额"));
        exportColumnList.add(new ExcelExportColumn("INSURANCE_SUM", "保险金额"));

        exportColumnList.add(new ExcelExportColumn("TAX_SUM", "税费金额"));
        exportColumnList.add(new ExcelExportColumn("PLATE_SUM", "牌照金额"));
        exportColumnList.add(new ExcelExportColumn("OTHER_SERVICE_SUM", "其他服务金额"));
        exportColumnList.add(new ExcelExportColumn("LOAN_SUM", "信贷金额"));
        exportColumnList.add(new ExcelExportColumn("OFFSET_AMOUNT", "抵扣金额"));
        exportColumnList.add(new ExcelExportColumn("PRESENT_SUM", "赠送金额"));
        exportColumnList.add(new ExcelExportColumn("ORDER_RECEIVABLE_SUM", "订单应收"));
        exportColumnList.add(new ExcelExportColumn("ORDER_PAYED_AMOUNT", "订单已收"));
        exportColumnList.add(new ExcelExportColumn("ORDER_DERATED_SUM", "订单减免"));
        exportColumnList.add(new ExcelExportColumn("ORDER_ARREARAGE_AMOUNT", "订单欠款"));
        exportColumnList.add(new ExcelExportColumn("CONSIGNED_SUM", "代办预收"));
        exportColumnList.add(new ExcelExportColumn("CON_RECEIVABLE_SUM", "代办应收"));
        exportColumnList.add(new ExcelExportColumn("CON_PAYED_AMOUNT", "代办已收"));
        exportColumnList.add(new ExcelExportColumn("CON_ARREARAGE_AMOUNT", "代办欠款"));
        exportColumnList.add(new ExcelExportColumn("APPLY_NO", "调拨申请单号"));
        exportColumnList.add(new ExcelExportColumn("PERMUTED_VIN", "二手车VIN"));
        exportColumnList.add(new ExcelExportColumn("PERMUTED_DATE", "置换日期",CommonConstants.SIMPLE_DATE_FORMAT));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "二手车品牌"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "二手车车型"));
        exportColumnList.add(new ExcelExportColumn("PAY_MODE", "支付方式"));
        exportColumnList.add(new ExcelExportColumn("LOAN_ORG", "付款银行"));
        exportColumnList.add(new ExcelExportColumn("INSTALLMENT_NUMBER", "分期期数"));
        exportColumnList.add(new ExcelExportColumn("INSTALLMENT_AMOUNT", "贷款总金额"));
        exportColumnList.add(new ExcelExportColumn("IS_PAD_CONFIRM", "是否PAD交车"));
        exportColumnList.add(new ExcelExportColumn("ENTERING_DATE", "保留订单日期"));
        exportColumnList.add(new ExcelExportColumn("ABORTING_REASON", "取消订单原因"));
        exportColumnList.add(new ExcelExportColumn("DELIVERY_MODE_ELEC", "官网交车方式"));
        exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "官网订单号"));
        
        
        
       
        
        // 生成excel 文件
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_销售综合查询.xls", request, response);

    }
    
    
    

}
