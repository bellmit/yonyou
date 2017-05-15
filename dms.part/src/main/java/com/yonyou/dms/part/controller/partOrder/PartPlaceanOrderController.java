
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderController.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月14日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.partOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderItemDTO;
import com.yonyou.dms.part.domains.PO.partOrder.TtPtDmsOrderPO;
import com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件订货
 * 
 * @author zhanshiwei
 * @date 2017年4月14日
 */
@Controller
@TxnConn
@RequestMapping("/partOrder/placeAnorder")
public class PartPlaceanOrderController extends BaseController {

    private static final int List = 0;
    @Autowired
    private PartPlaceanOrderService partorderservice;
    @Autowired
    private ExcelGenerator          excelService;

    /**
     * 查询配件订单信息
     * 
     * @author zhanshiwei
     * @date 2017年4月14日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * @throws Exception
     */

    @RequestMapping(value = "/selectPartOrder", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsTtPtOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException,
                                                                                       Exception {
        PageInfoDto id = partorderservice.queryAllocateInOrders(queryParam);
        return id;
    }

    /**
     * 业务描述：配件仓库三包缺料查询
     * 
     * @author zhanshiwei
     * @date 2017年4月17日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * @throws Exception
     */

    @RequestMapping(value = "/dmsSanBaoOrderPartInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsSanBaoOrderPartInfo(@RequestParam Map<String, String> queryParam) throws ServiceBizException,
                                                                                                 Exception {
        PageInfoDto id = partorderservice.QueryDmsSanBaoOrderPartInfo(queryParam);
        return id;
    }

    /**
     * 创建配件订单
     * 
     * @author zhanshiwei
     * @date 2017年4月18日
     * @param ttPtOrderIntenDto
     * @return
     * @throws ServiceBizException
     * @throws Exception
     */

    @RequestMapping(value = "/{orderNo}/createNewDMSOrder", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<TtPtDmsOrderPO>> CreateNewDMSOrder(@RequestBody TtPtDmsOrderItemDTO ttPtOrderIntenDto,
                                                                  @RequestParam("orderNo") String orderNo,
                                                                  UriComponentsBuilder uriCB) throws ServiceBizException {
        List<TtPtDmsOrderPO> ordetItemPo = partorderservice.CreateNewDMSOrder(ttPtOrderIntenDto, orderNo);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/createNewDMSOrder").buildAndExpand().toUriString());
        return new ResponseEntity<List<TtPtDmsOrderPO>>(ordetItemPo, headers, HttpStatus.CREATED);
    }

    /**
     * 业务描述:查询配件订单明细
     * 
     * @author zhanshiwei
     * @date 2017年4月9日
     * @param queryParam
     * @param orderNo
     * @return
     * @throws ServiceBizException
     * @throws Exception
     */

    @RequestMapping(value = "/{orderNo}/queryDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDetail(@RequestParam Map<String, String> queryParam,
                                 @PathVariable("orderNo") String orderNo) throws ServiceBizException {
        List<Map> id = partorderservice.queryDetail(queryParam, orderNo);
        return id;
    }

    /**
     * 业务描述:配件出入小计
     * 
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param queryParam
     * @param partNo
     * @param storageCode
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDetail/{partNo}/{storageCode}/queryDmsPartInOutSub", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QueryDmsPartInOutSub(@RequestParam Map<String, String> queryParam,
                                            @PathVariable("partNo") String partNo,
                                            @PathVariable("storageCode") String storageCode) throws ServiceBizException {
        PageInfoDto id = partorderservice.queryDmsPartInOutSub(queryParam, partNo, storageCode);
        return id;
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param queryParam
     * @param partNo
     * @param storageCode
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/{partNo}/queryDmsPartOption", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDmsPartOption(@RequestParam Map<String, String> queryParam,
                                        @PathVariable("partNo") String partNo) throws ServiceBizException {
        List<Map> id = partorderservice.queryDmsPartOption(partNo);
        return id;
    }

    /**
     * 业务描述:配件订单以及明细信息维护
     * 
     * @author zhanshiwei
     * @date 2017年4月22日
     * @param ttptdmsorderdto
     * @param uriCB
     * @throws Exception
     */

    @RequestMapping(value = "/maintainDmsPtOrder", method = RequestMethod.POST)
    public  ResponseEntity<Map>  maintainDmsPtOrder(@RequestBody @Valid TtPtDmsOrderDTO ttptdmsorderdto,
                                   UriComponentsBuilder uriCB) throws Exception {
       Map map= partorderservice.maintainDmsPtOrder(ttptdmsorderdto);
       MultiValueMap<String, String> headers = new HttpHeaders();
       return new ResponseEntity<Map>(map, headers, HttpStatus.CREATED);
    }
    
    /**
    * 业务描述：配件计划作废功能
    * @author zhanshiwei
    * @date 2017年5月1日
    * @param ttptdmsorderdto
    * @param uriCB
    * @throws Exception
    */
    	
    @RequestMapping(value = "/deleteDmsPtOrderPlan", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void deleteDmsPtOrderPlan(@RequestBody @Valid TtPtDmsOrderDTO ttptdmsorderdto,
                                   UriComponentsBuilder uriCB) throws Exception {
        partorderservice.deleteDmsPtOrderPlan(ttptdmsorderdto);
    }

    /**
     * 查询配件订货公式
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDmsPartOrderFormulan", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDmsPartOrderFormula(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        List<Map> id = partorderservice.queryDmsPartOrderFormula(queryParam);
        return id;
    }

    /**
     * 查询建议采购
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDmsSuggestOrder", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryDmsSuggestOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        List<Map> id = partorderservice.queryDmsSuggestOrder(queryParam);
        return id;
    }

    /**
     * 库存配件缺料查询 TODO description
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDmsPartInfoAboutOrder", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsPartInfoAboutOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        PageInfoDto id = partorderservice.queryDmsPartInfoAboutOrder(queryParam);
        return id;
    }

    @RequestMapping(value = "/export/excel", method = RequestMethod.POST)
    public void exportUsers(@RequestParam Map<Object, Object> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        System.err.println("-------------------------------------------------");
        List<Map> resultList =JSONUtil.jsonToList(queryParam.get("scene").toString(), Map.class);
        Map<String, List<Map>> excelData = new HashMap<>();
        excelData.put("配件订货计划", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<>();
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE", "仓库"));
        exportColumnList.add(new ExcelExportColumn("COUNT", "订货数量"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY", "可用库存"));
        exportColumnList.add(new ExcelExportColumn("Is_Stock", "库存数量"));
        exportColumnList.add(new ExcelExportColumn("NETWR", "单价"));
        exportColumnList.add(new ExcelExportColumn("TOTAL_PRICES", "总价"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型代码"));
        exportColumnList.add(new ExcelExportColumn("MIN_PACKAGE", "最小包装数"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "单位"));
        exportColumnList.add(new ExcelExportColumn("QUOTA_COUNT", "配货量"));
        exportColumnList.add(new ExcelExportColumn("LATEST_PRICE", "最新进货价"));
        exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY", "借进数量"));
        exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY", "借出数量"));
        exportColumnList.add(new ExcelExportColumn("LOCKED_QUANTITY", "锁定数量"));
        exportColumnList.add(new ExcelExportColumn("MAX_STOCK", "最大库存"));
        exportColumnList.add(new ExcelExportColumn("MIN_STOCK", "最小库存"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE", "成本单价"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT", "成本金额"));
        exportColumnList.add(new ExcelExportColumn("LAST_STOCK_IN", "最新入库日期"));
        exportColumnList.add(new ExcelExportColumn("LAST_STOCK_OUT", "最新出库日期"));
        exportColumnList.add(new ExcelExportColumn("IS_LACK_GOODS", "是否欠货", ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("DETAIL_REMARK", "备注"));
       

        excelService.generateExcel(excelData, exportColumnList, "配件订货计划.xls", request, response);

    }

    /**
     * 业务描述: 查询符合条件的库存配件
     * 
     * @author zhanshiwei
     * @date 2017年4月27日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDmsPartForOrder", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDmsPartForOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        PageInfoDto id = partorderservice.queryDmsPartForOrder(queryParam);
        return id;
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月27日
     * @param queryParam
     * @param partNo
     * @return
     * @throws ServiceBizException
     */

    @RequestMapping(value = "/queryDmsPartForOrder/{partNo}/{storageCode}/queryPartSalesHistory", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartSalesHistory(@RequestParam Map<String, String> queryParam,
                                             @PathVariable("partNo") String partNo,@PathVariable("storageCode") String storageCode) throws ServiceBizException {
        PageInfoDto id = partorderservice.queryPartSalesHistory(queryParam, partNo,storageCode);
        return id;
    }
    
    
    /**
    * 业务描述：配件订货计划界面根据配件代码查找替代件
    * @author zhanshiwei
    * @date 2017年4月27日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    */
    	
    @RequestMapping(value = "/queryDmsDeliverPartsReplace", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> queryDmsDeliverPartsReplace(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        List<Object> id = partorderservice.queryDmsDeliverPartsReplace(queryParam);
        return id;
    }
}
