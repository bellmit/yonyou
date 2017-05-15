/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartBuyController.java
*
* @Author : zhengcong
*
* @Date : 2017年4月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月7日   zhengcong    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.controller.stockmanage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartBuyItemDTO;
import com.yonyou.dms.part.service.stockmanage.PartBuyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 采购入库Controller
 * 
 * @author zhengcong
 * @date 2017年4月7日
 */
@Controller
@TxnConn
@RequestMapping("/stockmanage/partbuy")
@SuppressWarnings({"rawtypes","unchecked"})
public class PartBuyController extends BaseController {

    @Autowired
    private PartBuyService partBuyService;
    @Autowired
    private CommonNoService  commonNoService;
    @Autowired
    private ExcelRead<PartBuyItemDTO>  excelReadService;
    
    
    /**
     * 根据No查询出采购入库单
     * @author zhengcong
     * @date 2017年4月5日
     */      
    @RequestMapping(value="/stockIn/{stockInNo}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partBuyFindByNo(@PathVariable(value = "stockInNo") String stockInNo) {
    	stockInNo=stockInNo.substring(2);
        PageInfoDto pageInfoDto  = partBuyService.partBuyFindByNo(stockInNo);
        return pageInfoDto;
    }     
    
    /**
     * 根据查询出的入库单，选择后将信息带到主界面后，根据NO查询出详细信息
     * @author zhengcong
     * @date 2017年4月10日
     */      
    @RequestMapping(value="/stockInItem/{stockInNo}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partBuyItemFindByNo(@PathVariable(value = "stockInNo") String stockInNo) {
    	stockInNo=stockInNo.substring(2);
        PageInfoDto pageInfoDto  = partBuyService.partBuyItemFindByNo(stockInNo);
        return pageInfoDto;
    }   
    
    /**
     * 点修改，查询出非退货单配件信息
     * @author zhengcong
     * @date 2017年4月10日
     */      
    @RequestMapping(value="/stockInItemDetail/{STORAGE_CODE}/{PART_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findNotBackItem(@PathVariable(value = "STORAGE_CODE") String STORAGE_CODE,
    		@PathVariable(value = "PART_NO") String PART_NO) {
    
        PageInfoDto pageInfoDto  = partBuyService.findNotBackItem(STORAGE_CODE,PART_NO);
        return pageInfoDto;
    }
    
    /**
     * 点修改，查询出退货单配件信息
     * @author zhengcong
     * @date 2017年4月10日
     */      
    @RequestMapping(value="/backItem/{PART_NO}/{OLD_STOCK_IN_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findBackItem(@PathVariable(value = "PART_NO") String PART_NO,
    		@PathVariable(value = "OLD_STOCK_IN_NO") String OLD_STOCK_IN_NO) {
    
        PageInfoDto pageInfoDto  = partBuyService.findBackItem(PART_NO,OLD_STOCK_IN_NO);
        return pageInfoDto;
    }
    
//    
//	/**
//	 * 根据查询条件返回对应的采购入库单数据
//	 * 
//	 * @author zhengcong
//	 * @date 2017年4月7日
//	 * @param queryParam
//	 *            查询条件
//	 * @return 查询结果
//	 */
//
//    @RequestMapping(method = RequestMethod.GET)
//    @ResponseBody
//    public PageInfoDto queryPurchaseOrders(@RequestParam Map<String, String> queryParam) {
//        PageInfoDto pageInfoDto = partBuyService.queryPurchaseOrders(queryParam);
//        return pageInfoDto;
//    }
//    /**
//    * 新增采购入库单以及明细
//    * @author xukl
//    * @date 2016年8月4日
//    * @param partBuyDto
//    * @param uriCB
//    * @return
//    */
//    	
//    @RequestMapping(method = RequestMethod.POST)
//    public ResponseEntity<Map<String,Object>> addPartBuy(@RequestBody @Valid PartBuyDTO partBuyDto, UriComponentsBuilder uriCB) {
//        
//        PartBuyPO partBuyPO = partBuyService.addPartBuy(commonNoService.getSystemOrderNo(CommonConstants.PART_BUY_IN_PREFIX),partBuyDto);
//        MultiValueMap<String, String> headers = new HttpHeaders();
//        headers.set("Location", uriCB.path("/stockmanage/purchaseOrders/{id}").buildAndExpand(partBuyPO.getId()).toUriString());
//        return new ResponseEntity<Map<String,Object>>(partBuyPO.toMap(), headers, HttpStatus.CREATED);
//
//    }
//    
//    /**
//    * 入账
//    * @author xukl
//    * @date 2016年8月5日
//    * @param id
//    */
//    	
//    @RequestMapping(value="/{id}/inWarehouse",method=RequestMethod.PUT)
//    @ResponseBody
//    public void updateOrderStatusById(@PathVariable Long id,@RequestBody @Valid PartBuyDTO partBuyDto){
//        partBuyService.updatePartBuy(id, partBuyDto);
//        partBuyService.doInWarehouse(id);
//    }
//    
//    /**
//    * 通过id获取配件入库主表信息
//    * @author xukl
//    * @date 2016年8月7日
//    * @param id
//    * @return
//    */
//    	
//    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
//    @ResponseBody
//    public Map getPartBuyById(@PathVariable(value = "id") Long id) {
//        PartBuyPO partBuyPO = partBuyService.getPartBuyById(id);
//        return partBuyPO.toMap();
//    }
//    
//    /**
//    * 通过主表ID查询采购入库明细表数据
//    * @author xukl
//    * @date 2016年8月7日
//    * @param id
//    * @return
//    */
//    	
//    @RequestMapping(value = "/{id}/Items",method = RequestMethod.GET)
//    @ResponseBody
//    public List<Map> getPartBuyItems(@PathVariable(value = "id") Long id) {
//        List<Map> list = partBuyService.getPartBuyItemsById(id);
//        return list;
//    }
//    
//    
//    /**
//     * 采购入库单查询
//    * @author DuPengXin
//    * @date 2016年12月7日
//    * @param id
//    * @return
//    */
//    	
//    @RequestMapping(value = "/{id}/print",method = RequestMethod.GET)
//    @ResponseBody
//    public Map getPrint(@PathVariable(value = "id") Long id) {
//        List<Map> list = partBuyService.getPartBuyPrintById(id);
//        Map map=list.get(0);
//        map.put("printdata", new Date());
//        return map;
//    }
//    
//    
//    /**
//     * 打印
//    * @author DuPengXin
//    * @date 2016年12月7日
//    * @param id
//    * @return
//    */
//    	
//    @RequestMapping(value = "/{id}/partbuyprint",method = RequestMethod.GET)
//    @ResponseBody
//    public List<Map> getPartBuyPrint(@PathVariable(value = "id") Long id) {
//        List<Map> list = partBuyService.getPartBuyPrintById(id);
//        return list;
//    }
//    /**
//    * 修改
//    * @author xukl
//    * @date 2016年8月7日
//    * @param id
//    * @param partBuyDto
//    * @param uriCB
//    * @return
//    */
//    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public ResponseEntity<PartBuyDTO> ModifyPartBuy(@PathVariable("id") Long id,@RequestBody @Valid PartBuyDTO partBuyDto,UriComponentsBuilder uriCB) {
//        partBuyService.updatePartBuy(id, partBuyDto);
//        MultiValueMap<String, String> headers = new HttpHeaders();  
//        headers.set("Location", uriCB.path("/stockmanage/purchaseOrders/{id}").buildAndExpand(id).toUriString());  
//        return new ResponseEntity<PartBuyDTO>(headers, HttpStatus.CREATED);  
//    }
//    
//    
//    /**
//    * 退货   明细查询
//    * @author xukl
//    * @date 2016年8月8日
//    * @param id
//    * @return
//    */
//   
//    @RequestMapping(value = "/{id}/returnItems",method = RequestMethod.GET)
//    @ResponseBody
//    public List<Map> getPartBuyReturnItems(@PathVariable(value = "id") Long id) {
//        List<Map> list = partBuyService.qryPartBuyReturnItems(id);
//        return list;
//    }
//    
//    /**
//    * 退货
//    * @author xukl
//    * @date 2016年8月8日
//    * @param partBuyDto
//    * @param uriCB
//    * @return
//    */
//    	
//    @RequestMapping(value="{id}/backGoods",method = RequestMethod.POST)
//    @ResponseBody
//    public void backGoods(@PathVariable(value = "id") Long id,@RequestBody PartBuyDTO partBuyDto) {
//      partBuyService.backGoods(partBuyDto, id);
//    }
//    
//    
//    /**
//    * 删除
//    * @author xukl
//    * @date 2016年8月9日
//    * @param id
//    * @param uriCB
//    */
//    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
//        partBuyService.deletePartBuybyId(id);;
//    }
//    
//    /**
//    * 导入
//    * @author xukl
//    * @date 2016年8月29日
//    * @param importFile
//    * @param partInfoImportDto
//    * @param uriCB
//    * @return
//    * @throws Exception
//    */
//    	
//    
//    @RequestMapping(value = "/import", method = RequestMethod.POST)
//    @ResponseBody
//    public List<Map> importPartInfos(@RequestParam(value = "file") MultipartFile importFile,@Valid PartBuyItemImportDto partBuyItemImportDto, UriComponentsBuilder uriCB) throws Exception {
//        final List<Map> listMap = new ArrayList<Map>();
//        ImportResultDto<PartBuyItemDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<PartBuyItemDTO>(PartBuyItemDTO.class,new ExcelReadCallBack<PartBuyItemDTO>() {
//            @Override
//            public void readRowCallBack(PartBuyItemDTO partBuyItemDto, boolean isValidateSucess) {
//                
//                if(verifyExist("SELECT PART_ID, DEALER_CODE from tm_part_info where PART_CODE= ?", partBuyItemDto.getPartNo())){
//                    throw new ServiceBizException("不存在此配件，不能导入！");
//                }
//                if(verifyExist("select STORAGE_ID,DEALER_CODE from tm_store where STORAGE_CODE = ?", partBuyItemDto.getStorageCode())){
//                    throw new ServiceBizException("不存在此仓库，不能导入！");
//                }
//                if(isValidateSucess){
//                    Map map = new HashMap();
//                    map.put("IS_FINISHED", DictCodeConstants.STATUS_IS_NOT);
//                    map.put("PART_CODE", partBuyItemDto.getPartNo());
//                    map.put("PART_NAME", partBuyItemDto.getPartName());
//                    map.put("STORAGE_CODE", partBuyItemDto.getStorageCode());
//                    map.put("STORAGE_POSITION_CODE", partBuyItemDto.getStoragePositionCode());
//                    map.put("UNIT", partBuyItemDto.getUnit());
//                    map.put("INQUANTITY", partBuyItemDto.getInQuantity());
//                    map.put("PRICE", partBuyItemDto.getInPrice());
//                    map.put("AMOUNT", partBuyItemDto.getInAmount());
//                    map.put("PRICETAXED", partBuyItemDto.getInPriceTaxed());
//                    map.put("TAXEDAMOUNT", partBuyItemDto.getInAmountTaxed());
//                    map.put("FROMTYPE", DictCodeConstants.SOURCE_TYPE_MANUAL);
//                    listMap.add(map);
//                }
//            }
//            private boolean verifyExist(String whereSql,String condition){
//                List<Object> queryParam=new ArrayList<Object>();
//                queryParam.add(condition);
//                List<Map> list = DAOUtil.findAll(whereSql, queryParam);
//                if(!CommonUtils.isNullOrEmpty(list)){
//                    return false;
//                }else{
//                    return true;
//                }
//            }
//        }));
//        
//        if(importResult.isSucess()){
//            return listMap;
//        }else{
//            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
//        }
//    }
}
