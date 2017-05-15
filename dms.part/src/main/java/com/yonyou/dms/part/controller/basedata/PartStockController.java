
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartStockController.java
*
* @Author : xukl
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    xukl    1.0
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockImportDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.service.basedata.PartStockService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 配件库存查询操作类
* @author xukl
* @date 2016年7月15日
*/
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/partStocks")
public class PartStockController extends BaseController{
    
    @Autowired
    private PartStockService partStockService;
    @Autowired
    private ExcelGenerator excelService;
    @Autowired
    private ExcelRead<PartStockDTO>  excelReadService;
    /**
    *  配件库存查询
    * @author xukl
    * @date 2016年7月15日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfos(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = partStockService.queryPartStocks(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询配件信息（ 调拨出库   ）
     * @author jcsi
     * @date 2016年7月27日
     * @param param
     * @return
      */
     @RequestMapping(value="/partInfo/selectInfo",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto partInfoList(@RequestParam Map<String, String> param){
        return partStockService.pageInfoList(param);
     }
     
     /**
      * 查询配件销售记录
      * @author dingchaoyu
      * @date 2017年3月30日
      * @param param
      * @return
       */
      @RequestMapping(value="/partInfo/selectp/{PART_NO}/{STORAGE_CODE}",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto partInfoLists(@PathVariable(value = "PART_NO") String id,@PathVariable(value = "STORAGE_CODE") String ids){
         return partStockService.pageInfoLists(id, ids);
      }
     
      /**
       * 新增配件基本信息
       * @author dingchaoyu
       * @date 2016年7月6日
       * @param partInfoDTO
       * @param uriCB
       * @return 新增配件信息
       */
           
       @RequestMapping(value="/partinster",method = RequestMethod.POST)
       public ResponseEntity<Map> addPartInfo(@RequestBody @Valid Map partInfoDTO,UriComponentsBuilder uriCB) {
           String id = partStockService.addPartStock(partInfoDTO);
           MultiValueMap<String,String> headers = new HttpHeaders();  
           headers.set("Location", uriCB.path("/basedata/partInfos/{id}").buildAndExpand(id).toUriString());  
           return new ResponseEntity<Map>(partInfoDTO,headers, HttpStatus.CREATED);  

       }
      
     /**
     * 查找替代配件信息
     * @author jcsi
     * @date 2016年8月24日
     * @param optionNo
     * @return
      */
     @RequestMapping(value="/{optionNo}/list",method=RequestMethod.GET)
     @ResponseBody
     public PageInfoDto optionPartInfo(@PathVariable String optionNo){
         Map<String,String> param=new HashMap<String,String>();
         param.put("optionNo", optionNo);
         return partStockService.pageInfoList(param);
     }
    /**
    * 通过id 查询配件库存信息
    * @author xukl
    * @date 2016年7月19日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{PART_NO}/{STORAGE_CODE}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPartStockById(@PathVariable(value = "PART_NO") String id,@PathVariable(value = "STORAGE_CODE") String ids) {
        Map<String, Object> map = partStockService.getPartStockById(id,ids);
        return map;
    }
    
    /**
     * 仓库下拉框
     * @author dingchaoyu
     * @date 2016年7月19日
     * @param id
     * @return
     */
         
     @RequestMapping(value = "/StockCode", method = RequestMethod.GET)
     @ResponseBody
     public List<Map> getStockCodeById(Map<String, String> queryParam) {
         List<Map> map = partStockService.getStockCodeById(queryParam);
         return map;
     }
     
     /**
      * 经销商下拉框
      * @author dingchaoyu
      * @date 2016年7月19日
      * @param id
      * @return
      */
          
      
      @RequestMapping(value = "/DealertCode", method = RequestMethod.GET)
      @ResponseBody
      public List<Map> getDealertCode(Map<String, String> queryParam) {
          List<Map> map = partStockService.getDealertCode(queryParam);
          return map;
      }
    
    /**
    * 修改库存信息
    * @author xukl
    * @date 2016年7月19日
    * @param id
    * @param partStockDTO
    * @param uriCB
    * @return
    */
    	
    @RequestMapping(value = "/{PART_NO}/{STORAGE_CODE}", method = RequestMethod.PUT)
    public ResponseEntity<PartStockDTO> ModifyPartInfo(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids,@RequestBody Map partStockDTO,UriComponentsBuilder uriCB) {
        partStockService.modifyPartStock(id,ids, partStockDTO);
        
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/partStocks/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<PartStockDTO>(headers, HttpStatus.CREATED);  
    }
    
    /**
    * 导出
    * @author xukl
    * @date 2016年8月2日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportPartStocks(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = partStockService.queryPartStockForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("配件库存信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库代码"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME","库位代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("BIG_CATEGORY_CODE","用品大分类"));
        exportColumnList.add(new ExcelExportColumn("SUB_CATEGORY_CODE","用品中分类"));
        exportColumnList.add(new ExcelExportColumn("THD_CATEGORY_CODE","用品小分类"));
        exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","库存数量"));
        exportColumnList.add(new ExcelExportColumn("USEABLE_STOCK","可用库存"));
        exportColumnList.add(new ExcelExportColumn("IS_THINGS","是否用品",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("SALES_PRICE","销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("LATEST_PRICE","不含税进价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("PART_GROUP_CODE","配件类别"));
        exportColumnList.add(new ExcelExportColumn("SPELL_CODE","拼音代码"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
        exportColumnList.add(new ExcelExportColumn("INSURANCE_PRICE","保险价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("LIMIT_PRICE","销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY","借进数量"));
        exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY","借出数量"));
        exportColumnList.add(new ExcelExportColumn("LOCKED_QUANTITY","锁定数量"));
        exportColumnList.add(new ExcelExportColumn("MAX_STOCK","最大库存"));
        exportColumnList.add(new ExcelExportColumn("SAFE_STOCK","安全库存"));
        exportColumnList.add(new ExcelExportColumn("MIN_STOCK","最小库存"));
        exportColumnList.add(new ExcelExportColumn("MIN_PACKAGE","最小包装数"));
        exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA","产地"));
        exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
        exportColumnList.add(new ExcelExportColumn("LAST_STOCK_IN","最新入库日期"));
        exportColumnList.add(new ExcelExportColumn("OPTION_NO","替代配件"));
        exportColumnList.add(new ExcelExportColumn("BRAND","品牌"));
        exportColumnList.add(new ExcelExportColumn("PART_STATUS","是否停用",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
        exportColumnList.add(new ExcelExportColumn("PART_MODEL","配件车型组集"));
        exportColumnList.add(new ExcelExportColumn("PART_SPE_TYPE","配件特殊类型"));
        exportColumnList.add(new ExcelExportColumn("IS_SUGGEST_ORDER","建议采购"));
        exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类"));
        exportColumnList.add(new ExcelExportColumn("NET_COST_PRICE","含税成本单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("NET_COST_AMOUNT","含税成本金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("DOWN_TAG","OEM下发",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_STORAGE_SALE","是否寄售",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PROVIDER_CODE","供应商代码"));
        exportColumnList.add(new ExcelExportColumn("PROVIDER_NAME","供应商名称"));
        //生成excel 文件
        excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_配件库存.xls",request, response);
    }
    
    /**
     * 模板下载
     * @author xukl
     * @date 2016年8月2日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
         
     @RequestMapping(value = "/export/excels", method = RequestMethod.GET)
     public void exportPartStockss(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("配件库存信息", null);
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
         exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库代码"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME","库位代码"));
         exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
         exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
         exportColumnList.add(new ExcelExportColumn("BIG_CATEGORY_CODE","用品大分类"));
         exportColumnList.add(new ExcelExportColumn("SUB_CATEGORY_CODE","用品中分类"));
         exportColumnList.add(new ExcelExportColumn("THD_CATEGORY_CODE","用品小分类"));
         exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","库存数量"));
         exportColumnList.add(new ExcelExportColumn("USEABLE_STOCK","可用库存"));
         exportColumnList.add(new ExcelExportColumn("IS_THINGS","是否用品",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("SALES_PRICE","销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("LATEST_PRICE","不含税进价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("PART_GROUP_CODE","配件类别"));
         exportColumnList.add(new ExcelExportColumn("SPELL_CODE","拼音代码"));
         exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
         exportColumnList.add(new ExcelExportColumn("INSURANCE_PRICE","保险价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("LIMIT_PRICE","销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY","借进数量"));
         exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY","借出数量"));
         exportColumnList.add(new ExcelExportColumn("LOCKED_QUANTITY","锁定数量"));
         exportColumnList.add(new ExcelExportColumn("MAX_STOCK","最大库存"));
         exportColumnList.add(new ExcelExportColumn("SAFE_STOCK","安全库存"));
         exportColumnList.add(new ExcelExportColumn("MIN_STOCK","最小库存"));
         exportColumnList.add(new ExcelExportColumn("MIN_PACKAGE","最小包装数"));
         exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA","产地"));
         exportColumnList.add(new ExcelExportColumn("COST_AMOUNT","成本金额"));
         exportColumnList.add(new ExcelExportColumn("LAST_STOCK_IN","最新入库日期"));
         exportColumnList.add(new ExcelExportColumn("OPTION_NO","替代配件"));
         exportColumnList.add(new ExcelExportColumn("BRAND","品牌"));
         exportColumnList.add(new ExcelExportColumn("PART_STATUS","是否停用",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
         exportColumnList.add(new ExcelExportColumn("PART_MODEL","配件车型组集"));
         exportColumnList.add(new ExcelExportColumn("PART_SPE_TYPE","配件特殊类型"));
         exportColumnList.add(new ExcelExportColumn("IS_SUGGEST_ORDER","建议采购"));
         exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类"));
         exportColumnList.add(new ExcelExportColumn("NET_COST_PRICE","含税成本单价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("NET_COST_AMOUNT","含税成本金额",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("DOWN_TAG","OEM下发",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_STORAGE_SALE","是否寄售",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("PROVIDER_CODE","供应商代码"));
         exportColumnList.add(new ExcelExportColumn("PROVIDER_NAME","供应商名称"));
         //生成excel 文件
         excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_配件库存.xls",request, response);
     }

    /**
     * 导入
     * @author xukl
     * @date 2016年8月23日
     * @param importFile
     * @param partInfoImportDto
     * @param uriCB
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public List<PartStockDTO> importPartInfos(@RequestParam(value = "file") MultipartFile importFile, PartStockImportDTO partStockImportDTO, UriComponentsBuilder uriCB) throws Exception {
        ImportResultDto<PartStockDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<PartStockDTO>(PartStockDTO.class,new ExcelReadCallBack<PartStockDTO>() {
            @Override
            public void readRowCallBack(PartStockDTO rowDto, boolean isValidateSucess) {
                try{
                    if(isValidateSucess){
                         partStockService.importPartStock(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));

        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
    
    /**
     * 维修项目查询配件主数据 
    * @author rongzoujie
    * @date 2016年10月20日
    * @param queryParam
    * @return
     */
    @RequestMapping(value = "/partInfo/labourPart",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfoByLabour(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = partStockService.queryPartInfoByLabour(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 追溯件 
    * @author rongzoujie
    * @date 2016年10月20日
    * @param queryParam
    * @return
     */
    @RequestMapping(value = "/partInfo/{PART_NO}/{STORAGE_CODE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartSn(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids) {
        PageInfoDto pageInfoDto = partStockService.queryPartSn(id,ids);
        return pageInfoDto;
    }
    
}
