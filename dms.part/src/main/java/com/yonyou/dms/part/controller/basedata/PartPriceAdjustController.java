
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartPriceAdjustController.java
 *
 * @Author : zhongshiwei
 *
 * @Date : 2016年7月17日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月17日    zhongshiwei    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustImportDTO;
import com.yonyou.dms.part.service.basedata.PartPriceAdjustService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件价格调整
 * @author zhongshiwei
 * @date 2016年7月17日
 */
@Controller
@TxnConn
@SuppressWarnings("rawtypes")
@RequestMapping("/basedata/partPrice")
public class PartPriceAdjustController extends BaseController{

    @Autowired
    private PartPriceAdjustService partPriceAdjustService;
    @Autowired
    private ExcelRead<PartPriceAdjustDTO>  excelReadService;
    @Autowired
    private ExcelGenerator excelService;
    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年7月18日
     * @param partPriceAdjustSql 
     * @return 
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partPriceAdjustSql(@RequestParam Map<String, String> partPriceAdjustSQL) throws ServiceBizException{
        PageInfoDto pageInfoDto = partPriceAdjustService.partPriceAdjustSQL(partPriceAdjustSQL);
        return pageInfoDto;
    }    

    /**
     * 根据ID 修改配件价格信息
     * @author zhongsw
     * @date 2016年7月20日
     * @param id
     * @param partPriceAdjustDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/aa/{PART_NO}/{STORAGE_CODE}/{PART_BATCH_NO}", method = RequestMethod.PUT)
    public ResponseEntity<PartPriceAdjustDTO> updatePartPriceAdjust(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids,@PathVariable("PART_BATCH_NO") String idd, @RequestBody @Valid Map<String, String> param,UriComponentsBuilder uriCB)throws ServiceBizException {
        partPriceAdjustService.updatePartPriceAdjust(id,ids,idd,param);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partPrice/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PartPriceAdjustDTO>(headers, HttpStatus.CREATED);
    }

    /**
     * 根据id查找
     * @author zhongshiwei
     * @date 2016年7月18日
     * @param id
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/bb/{PART_NO}/{STORAGE_CODE}", method = RequestMethod.GET)
    @ResponseBody
    public Map findById(@PathVariable("PART_NO") String id,@PathVariable("STORAGE_CODE") String ids) throws ServiceBizException{
        List<Map> list = partPriceAdjustService.findById(id,ids);
        if(list.size()>0){
            return partPriceAdjustService.findById(id,ids).get(0);
        }else{
            return new HashMap();
        }
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
    public List<PartPriceAdjustDTO> importPartInfos(@RequestParam(value = "file") MultipartFile importFile, PartPriceAdjustImportDTO partPriceAdjustImportDTO, UriComponentsBuilder uriCB) throws Exception {
        ImportResultDto<PartPriceAdjustDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<PartPriceAdjustDTO>(PartPriceAdjustDTO.class,new ExcelReadCallBack<PartPriceAdjustDTO>() {
            @Override
            public void readRowCallBack(PartPriceAdjustDTO rowDto, boolean isValidateSucess) {
                try{
                    if(isValidateSucess){
                        partPriceAdjustService.imports(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));
//        logger.debug("param:" + partPriceAdjustImportDTO.getFileParam());
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
    
    /**
     * 根据ID 批量修改信息
     * @author zhongsw
     * @date 2016年7月30日
     * @param userSelectDto
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/clums",method = RequestMethod.PUT)
    public ResponseEntity<PartPriceAdjustDTO> changePrice(@RequestBody  Map<String, Object> param,UriComponentsBuilder uriCB) {
        String id = param.get("PART_NO").toString();
        partPriceAdjustService.modifyPrice(param);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/partPrice/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<PartPriceAdjustDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 导出
     * @author dingchaoyu
     * @date 2016年8月2日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
         
     @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
     public void exportPartStocks(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
         List<Map> resultList = partPriceAdjustService.querySql(queryParam);
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("配件销售价格调整", resultList);
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
         exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库代码"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_CODE_NAME","库位代码"));
         exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
         exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
         exportColumnList.add(new ExcelExportColumn("SALES_PRICE","现销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("INSURANCE_PRICE","保险价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("NODE_PRICE","网点价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("LATEST_PRICE","最新进货价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("LIMIT_PRICE","销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("INSTRUCT_PRICE","建议销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("SALES_PRICE","销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("CLAIM_PRICE","车型组集",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("RATE","加价率(%)",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         //生成excel 文件
         excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_配件销售价格调整.xls",request, response);
     }
     /**
      * 模板下载
      * @author dingchaoyu
      * @date 2016年8月2日
      * @param queryParam
      * @param request
      * @param response
      * @throws Exception
      */
          
      @RequestMapping(value = "/export/excels", method = RequestMethod.GET)
      public void exportPartPrice(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
          Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
          excelData.put("配件销售价格调整", null);
          List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
          exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库代码"));
          exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
          exportColumnList.add(new ExcelExportColumn("SALES_PRICE","现销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
          //生成excel 文件
          excelService.generateExcel(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerName()+"_配件销售价格调整.xls",request, response);
      }
}
