
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartAllocateOutController.java
*
* @Author : jcsi
*
* @Date : 2016年7月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月26日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.stockmanage;

import java.io.IOException;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateOutDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateOutItemDto;
import com.yonyou.dms.part.service.basedata.PartStockService;
import com.yonyou.dms.part.service.stockmanage.PartAllocateOutService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 调拨出库
* @author jcsi
* @date 2016年7月26日
*/
@Controller
@TxnConn
@RequestMapping("/stockmanage/partallocateouts")
public class PartAllocateOutController extends BaseController{

    @Autowired
    private PartAllocateOutService partAllocateOutService;
    
    @Autowired
    private CommonNoService  commonNoService;
    
    @Autowired
    private PartStockService partStockService;
    
    @Autowired
    private ExcelRead<PartAllocateOutItemDto>      excelReadService;
    
    /**
    * 查询
    * @author jcsi
    * @date 2016年7月26日
    * @param param
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchPartAllocateOut(@RequestParam Map<String, String> param){
        return partAllocateOutService.search(param);
    }
    
    /**
    * 查询结算调拨出库单
    * @author jcsi
    * @date 2016年11月9日
    * @param param
    * @return
     */
    @RequestMapping(value="/allocateOutBalance/selectInfo",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchBalance(@RequestParam Map<String,String> param){
        return partAllocateOutService.searchBalance(param);
    }
    
    /**
    * 根据出库单号查询
    * @author jcsi
    * @date 2016年11月16日
    * @param param
    * @return
     */
    @RequestMapping(value="/allocateOutBalanceByNo/selectInfo",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> searchBalanceByNo(@RequestParam Map<String,String> param){
        return partAllocateOutService.searchBalanceByNo(param);
    }
    
    /**
    * 删除
    * @author jcsi
    * @date 2016年7月29日
    * @param id
     */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id, UriComponentsBuilder uriCB){
        partAllocateOutService.delete(id);
    }
    
   /**
   * 添加
   * @author jcsi
   * @date 2016年7月31日
   * @param partAllocateOutDto
   * @param uriCB
   * @return
    */
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> addPartAllocateOut(@RequestBody @Valid PartAllocateOutDto partAllocateOutDto,UriComponentsBuilder uriCB){
        PartAllocateOutPo partAllocatePo=partAllocateOutService.addPartOutAndOutItem(commonNoService.getSystemOrderNo(CommonConstants.PART_ALLOCATE_OUT_PREFIX),partAllocateOutDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partallocateouts/{id}").buildAndExpand(partAllocatePo.getLongId()).toUriString());
        return new ResponseEntity<Map<String,Object>>(partAllocatePo.toMap(), headers, HttpStatus.CREATED);
    }
    
    /**
    * 根据id修改
    * @author jcsi
    * @date 2016年8月3日
    * @param id
    * @param userDto
    * @param uriCB
    * @return
     */
    @RequestMapping(value = "/{id}/outitems", method = RequestMethod.PUT)
    public ResponseEntity<PartAllocateOutDto> ModifyUser(@PathVariable("id") Long id, @RequestBody @Valid PartAllocateOutDto partAllocateOutDto,
                                              UriComponentsBuilder uriCB) {
        partAllocateOutService.editPartAllocateOutDto(id, partAllocateOutDto);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partallocateouts/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PartAllocateOutDto>(partAllocateOutDto,headers, HttpStatus.CREATED);
    }
    /**
    * 根据配件出库主表id查询 详细信息
    * @author jcsi
    * @date 2016年8月1日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/outitems",method=RequestMethod.GET)
    @ResponseBody
    public List<Map> searchOutItemByOutId(@PathVariable Long id){
        return partAllocateOutService.searchOutItemByOutId(id);
    }
    
    
    /**
    * 入账
    * @author jcsi
    * @date 2016年8月2日
    * @param id
     */
    @RequestMapping(value="/{id}/orderstatus",method=RequestMethod.GET)
    @ResponseBody
    public void updateOrderStatusById(@PathVariable Long id){
        partAllocateOutService.updateOrderStatusById(id);
    }
    
    /**
    * 根据id查询主单信息
    * @author jcsi
    * @date 2016年8月4日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable Long id){
        Map<String,Object> map= partAllocateOutService.findById(id);
         return map;
    }
    
    /**
     * 导入
     * @author jcsi
     * @date 2016年9月1日
     * @param file
     * @return
     * @throws IOException
      */
     @RequestMapping(value = "/import", method = RequestMethod.POST)
     @ResponseBody
     public List<Map<String,Object>> ImportSalesItem(@RequestParam MultipartFile file) throws IOException{
         final List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
         // 解析Excel 表格(如果需要进行回调)
         ImportResultDto<PartAllocateOutItemDto> importResult = excelReadService.analyzeExcelFirstSheet(file,new AbstractExcelReadCallBack<PartAllocateOutItemDto>(PartAllocateOutItemDto.class,new ExcelReadCallBack<PartAllocateOutItemDto>() {
            @Override
            public void readRowCallBack(PartAllocateOutItemDto rowDto, boolean isValidateSucess) {
                try{
                    if(partStockService.partNoOrStorageExist(rowDto.getPartNo(),"partno")){
                        throw new ServiceBizException("配件代码不存在，导入失败！");
                    }
                    if(partStockService.partNoOrStorageExist(rowDto.getStorageCode(),"storagecode")){
                        throw new ServiceBizException("仓库代码不存在，导入失败！");
                    }
                    //验证成功
                    if(isValidateSucess){
                        Map<String,Object> item=new HashMap<String,Object>();
                        item.put("isFinishedShow", DictCodeConstants.STATUS_IS_NOT); //是否入账
                        item.put("partCodeShow", rowDto.getPartNo());   //配件代码
                        item.put("partNameShow", rowDto.getPartName());   //配件名称
                        item.put("storageCodeShow", rowDto.getStorageCode());  //仓库名称
                        item.put("storagePositionCodeShow", rowDto.getStoragePositionCode());  //库位
                        item.put("unitShow", rowDto.getUnit());   //计量单位
                        item.put("canNumShow", rowDto.getOutQuantity());  //出库数量
                        item.put("outPriceShow", rowDto.getOutPrice());  //出库单价
                        item.put("outAmountShow", rowDto.getOutAmount());  //出库金额
                        result.add(item);
                    }
                }catch(Exception e){
                    throw e;
                }
            }         
         }));
         
         if(importResult.isSucess()){
             return result;
         }else{
             throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
         }
     }
    
    
}
