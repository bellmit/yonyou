
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : LabourPriceService1.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月30日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月30日    zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.basedata;

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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartOrderSignDTO;
import com.yonyou.dms.part.service.basedata.PartOrderSignService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;



/**
 * 
 *签收货运单controller
 * @author zhengcong
 * @date 2017年3月30日
 */
@Controller
@TxnConn
@RequestMapping("/partOrder/deliverOrders")
public class PartOrderSignController extends BaseController {

    @Autowired
    private PartOrderSignService signService;

    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 根据条件查询
 * @author zhengcong
 * @date 2017年3月30日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartOrder(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto  = signService.queryPartOrder(queryParam);
        return pageInfoDto;
    }
 
    /**
     * 签收根据code查询出核销明细信息
     * @author zhengcong
     * @date 2017年4月5日
     */      
    @RequestMapping(value="/verification/{ORDER_REGEDIT_NO}/{DELIVERY_ORDER_NO}/item",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVerification(@PathVariable(value = "ORDER_REGEDIT_NO") String ORDER_REGEDIT_NO,
    		@PathVariable(value = "DELIVERY_ORDER_NO") String DELIVERY_ORDER_NO) {
        PageInfoDto pageInfoDto  = signService.queryVerification(ORDER_REGEDIT_NO,DELIVERY_ORDER_NO);
        return pageInfoDto;
    }  
    
    /**
     * 签收根据code查询货运单信息
     * @author zhengcong
     * @date 2017年4月5日
     */
    @RequestMapping(value = "/sign/{ORDER_REGEDIT_NO}/{DELIVERY_TIME}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> findByCode(@PathVariable(value = "ORDER_REGEDIT_NO") String ORDER_REGEDIT_NO,
    		@PathVariable(value = "DELIVERY_TIME") String DELIVERY_TIME){
        return signService.findByCode(ORDER_REGEDIT_NO,DELIVERY_TIME);
    }
    
    
    /**
     * 
     * 根据code修改签收信息
     * @author zhengcong
     * @date 2017年4月5日
     */

    @RequestMapping(value = "/sign/{ORDER_REGEDIT_NO}/{DELIVERY_TIME}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PartOrderSignDTO> signByCode(@PathVariable("ORDER_REGEDIT_NO") String ORDER_REGEDIT_NO,
    		@PathVariable("DELIVERY_TIME") String DELIVERY_TIME,
    		@RequestBody PartOrderSignDTO ctdto,
    		UriComponentsBuilder uriCB) throws ServiceBizException{
    	signService.signByCode(ORDER_REGEDIT_NO,DELIVERY_TIME, ctdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/partOrder/deliverOrders/sign/{[ORDER_REGEDIT_NO]}/{[DELIVERY_TIME]}").buildAndExpand(ORDER_REGEDIT_NO,DELIVERY_TIME).toUriString());  
        return new ResponseEntity<PartOrderSignDTO>(headers, HttpStatus.CREATED);  
    }
       
  /**
  * 查询发运单明细信息
 * @author zhengcong
 * @date 2017年3月30日
  */
 @RequestMapping(value="/{ORDER_REGEDIT_NO}/{DELIVERY_TIME}",method = RequestMethod.GET)
 @ResponseBody
 public List<Map> queryPartOrderDetail(@PathVariable("ORDER_REGEDIT_NO") String ORDER_REGEDIT_NO,
		 @PathVariable("DELIVERY_TIME") String DELIVERY_TIME ) throws ServiceBizException{
     List<Map> pageInfoDto = signService.queryPartOrderDetail(ORDER_REGEDIT_NO,DELIVERY_TIME);
     return pageInfoDto;
 }     
    
 /**
  * 明细信息导出excel
  * @author zhengcong
  * @date 2017年4月5日
  */
 @RequestMapping(value="/export",method = RequestMethod.GET)
 public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                   HttpServletResponse response){
     List<Map> resultList =signService.queryToExport(queryParam);
     Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
     excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_货运单明细", resultList);
     List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
     exportColumnList.add(new ExcelExportColumn("OEM_ORDER_NO","OEM订单编号"));
     exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO","SAP订单编号"));
     exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
     exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
     exportColumnList.add(new ExcelExportColumn("COUNT","订货数量"));
     exportColumnList.add(new ExcelExportColumn("SUPPLY_QTY","供应数量"));
     exportColumnList.add(new ExcelExportColumn("NEED_QUANTITY","累计入库数量"));
     exportColumnList.add(new ExcelExportColumn("CASE_NO","包装箱号"));
     exportColumnList.add(new ExcelExportColumn("PLAN_PRICE","采购价"));
     exportColumnList.add(new ExcelExportColumn("AMOUNT","金额"));
     exportColumnList.add(new ExcelExportColumn("INSTRUCT_PRICE","建议销售价"));
     exportColumnList.add(new ExcelExportColumn("SORT","货运单序号"));
     exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
     excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_货运单明细.xls", request, response);
 }    
    

 /**
  * 
  * 根据code修改信息
  * @author zhengcong
  * @date 2017年4月5日
  */

 @RequestMapping(value = "/cancel/{ORDER_REGEDIT_NO}/{DELIVERY_TIME}", method = RequestMethod.PUT)
 @ResponseBody
 public ResponseEntity<PartOrderSignDTO> modifyByCode(@PathVariable("ORDER_REGEDIT_NO") String ORDER_REGEDIT_NO,
 		@PathVariable("DELIVERY_TIME") String DELIVERY_TIME,
 		@RequestBody PartOrderSignDTO ctdto,
 		UriComponentsBuilder uriCB) throws ServiceBizException{
 	signService.cancelByCode(ORDER_REGEDIT_NO,DELIVERY_TIME, ctdto);
     MultiValueMap<String,String> headers = new HttpHeaders();  
     headers.set("Location", uriCB.path("/partOrder/deliverOrders/cancel/{[ORDER_REGEDIT_NO]}/{[DELIVERY_TIME]}").buildAndExpand(ORDER_REGEDIT_NO,DELIVERY_TIME).toUriString());  
     return new ResponseEntity<PartOrderSignDTO>(headers, HttpStatus.CREATED);  
 } 
 
 
 
//    /**
//     * 根据查询条件返回对应的用户数据
//     * @author zhongshiwei
//     * @date 2016年7月18日
//     * @param partOrderSignSQL 
//     * @return 
//     * @throws ServiceBizException
//     */
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public PageInfoDto searchPartOrderSign(@RequestParam Map<String, String> partOrderSignSQL) throws ServiceBizException{
//        PageInfoDto pageInfoDto = partOrderSignService.partOrderSignSQL(partOrderSignSQL);
//        return pageInfoDto;
//    }    
//
//    /**
//     * 查询货运单签收界面信息
//     * @author zhongsw
//     * @date 2016年8月2日
//     * @param id
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value="/{id}/item",method = RequestMethod.GET)
//    @ResponseBody
//    public PageInfoDto partOrderSignItem(@PathVariable Long id) throws ServiceBizException{
//        return partOrderSignService.partOrderSignItem(id);
//    }  
//

//
//    /**
//     * 采购入库查询货运单
//     * @author xukl
//     * @date 2016年8月8日
//     * @param queryParam
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value="/partBuy/qryDeliver",method = RequestMethod.GET)
//    @ResponseBody
//    public  PageInfoDto qryPartOrderSign(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
//        PageInfoDto pageInfoDto = partOrderSignService.qryPartOrderSign(queryParam);
//        return pageInfoDto;
//    }
//    /**
//     * 采购入库取货运单明细查询
//     * @author xukl
//     * @date 2016年8月3日
//     * @param id
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value="/{id}/itemBuy",method = RequestMethod.GET)
//    @ResponseBody
//    public  List<Map> partOrderSignItemBuy(@PathVariable("id") Long id)throws ServiceBizException {
//        List<Map> list = partOrderSignService.qryOrderSignItemBuy(id);
//        return list;
//    }
//    /**
//     * 根据ID 修改用户信息
//     * @author zhongsw
//     * @date 2016年7月20日
//     * @param id
//     * @param partOrderSignDTO
//     * @param uriCB
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    @ResponseBody
//    public ResponseEntity<PartOrderSignDTO> updatePartPriceAdjust(@PathVariable("id") Long id, @RequestBody PartOrderSignDTO partOrderSignDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
//        partOrderSignService.updatePartOrderSign(id, partOrderSignDTO);
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.set("Location", uriCB.path("/partOrder/deliverOrders/{id}").buildAndExpand(id).toUriString());
//        return new ResponseEntity<PartOrderSignDTO>(headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * 根据ID 修改发运单状态
//     * @author zhongsw
//     * @date 2016年7月20日
//     * @param id
//     * @param partOrderSignDTO
//     * @param uriCB
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value = "/{id}/state", method = RequestMethod.PUT)
//    public ResponseEntity<PartOrderSignDTO> updatePartPriceAdjustState(@PathVariable("id") Long id, @RequestBody PartOrderSignDTO partOrderSignDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
//        partOrderSignService.updateOrderStatus(id, partOrderSignDTO);
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.set("Location", uriCB.path("/partOrder/deliverOrders/{id}/state").buildAndExpand(id).toUriString());
//        return new ResponseEntity<PartOrderSignDTO>(headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * 根据ID 修改核销状态
//     * @author zhongsw
//     * @date 2016年7月20日
//     * @param id
//     * @param partOrderSignDTO
//     * @param uriCB
//     * @return
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value = "/{id}/HXstate", method = RequestMethod.PUT)
//    public ResponseEntity<PartOrderSignDTO> updateHeXiaoState(@PathVariable("id") Long id, @RequestBody PartOrderSignDTO partOrderSignDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
//        partOrderSignService.updateHXStatu(id, partOrderSignDTO);
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.set("Location", uriCB.path("/partOrder/deliverOrders/{id}/HXstate").buildAndExpand(id).toUriString());
//        return new ResponseEntity<PartOrderSignDTO>(headers, HttpStatus.CREATED);
//    }
//
//    /**
//     * 根据id查找
//     * @author zhongshiwei
//     * @date 2016年7月18日
//     * @param id
//     * @throws ServiceBizException
//     */
//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String,Object> findById(@PathVariable Long id) throws ServiceBizException{
//        Map map= partOrderSignService.findById(id);
//        return map;
//    }
//
//    /**
//     * 根据查询条件返回对应的用户数据
//     * @author zhongsw
//     * @date 2016年7月21日
//     * @param queryParam 查询条件
//     * @return 查询结果
//     * @throws Exception
//     */
//    @RequestMapping(value = "/export/{deliverId}", method = RequestMethod.GET)
//    public void exportUsers(@PathVariable Long deliverId,@RequestParam Map<String, String> queryParam, HttpServletRequest request,
//                            HttpServletResponse response) throws Exception {
//    	List<Map> resultList = partOrderSignService.queryUsersForExport(deliverId,queryParam);
//        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
//        excelData.put("用户信息", resultList);
//        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
//        exportColumnList.add(new ExcelExportColumn("OEM_ORDER_NO","OEM订单号"));
//        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
//        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
//        exportColumnList.add(new ExcelExportColumn("COUNT","订货数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("SUPPLY_QTY","供应数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("THIS_TIME_SIGN_COUNT","累计签收数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("IN_QUANTITY_HAVE","已入库数量",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("AMOUNT","采购价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("TAXED_AMOUNT","采购金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
//        exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
////        String[] keys = { "item_id","oem_order_no", "part_no","part_name","count","supply_qty","this_time_sign_count","in_quantity_have","amount","taxed_amount","remark" };
////        String[] columnNames = { "序号","OEM订单号","配件代码","配件名称","订货数量","供应数量","累计签收数量","已入库数量","采购价","采购金额","备注"};
//        excelService.generateExcel(excelData, exportColumnList, "发运单明细信息.xls", request, response);
//
//    }

}
