
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairProManager.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月11日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPartsDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairProManagerDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SingleCopyDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TotalCopyDTO;
import com.yonyou.dms.repair.domains.PO.basedata.RepairProManagerPO;
import com.yonyou.dms.repair.service.basedata.DiscountModeService;
import com.yonyou.dms.repair.service.basedata.RepairProManagerService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* TODO description
* @author rongzoujie
* @date 2016年8月11日
*/

@Controller
@TxnConn
@RequestMapping("/basedata/repairProManager")
public class RepairProManagerController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(RepairProManagerController.class);
    @Autowired
    private RepairProManagerService repairProManagerService;
    @Autowired
    private ExcelGenerator excelService;
    @Autowired
    private ExcelRead<RepairProManagerDTO> excelReadService;
    @Autowired
    private ExcelRead<RepairPartsDTO> excelReadServiceForPart;
    @Autowired
    private DiscountModeService discountModeService;
    
    
    

    /**
     * 
    * 维修车型组多选,车型代码输入框显示
    * @author rongzoujie
    * @date 2016年8月12日
    * @param modeLabourCode
    * @return
     */
    @RequestMapping(value="/{modeLabourCode}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,List<Map>> getCarType(@PathVariable("modeLabourCode") String modeLabourCode){
        String[] modeLabourCodes = modeLabourCode.split("-");
        Map<String,List<Map>> modeCodeMap = new HashMap<String,List<Map>>();
        for(int i=0;i<modeLabourCodes.length;i++){
            modeCodeMap.put(modeLabourCodes[i], repairProManagerService.getModelCode(modeLabourCodes[i]));
        }
        
        return modeCodeMap;
    }
    
    /**
     * 
    * 页面查询
    * @author rongzoujie
    * @date 2016年8月15日
    * @param queryParam
    * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairPros(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = repairProManagerService.queryRepairPros(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 添加维修项目
    * TODO description
    * @author wantao
    * @date 2017年4月24日
    * @param repairProManagerDTO
    * @param uriCB
    * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    public void addRepairPro(@RequestBody RepairProManagerDTO repairProManagerDTO,UriComponentsBuilder uriCB){
        repairProManagerService.addRepairPro(repairProManagerDTO);
    }
    
    /**
    * 更具id查找维修项目 
    * @author rongzoujie
    * @date 2016年8月16日
    * @param id
    * @return
     */
    @RequestMapping(value = "/modify/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getRepairProMngById(@PathVariable(value = "id") Long id){
        RepairProManagerPO repairProManagerPO = repairProManagerService.getRepairProMngById(id);
        return repairProManagerPO.toMap();
    }
    
    /**
     *修改维修项目 
    * @author rongzoujie
    * @date 2016年8月16日
    * @param id
    * @param repairProManagerDTO
    * @param uriCB
    * @return
     */
    @RequestMapping(value="/modify/{id}",method=RequestMethod.PUT)
    public ResponseEntity<RepairProManagerDTO> ModifyRepairProMng(@PathVariable("id") Long id, @RequestBody @Valid RepairProManagerDTO repairProManagerDTO
                                                                  ,UriComponentsBuilder uriCB){
        repairProManagerService.modifyRepairProMng(id, repairProManagerDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/repairProManager/modify/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<RepairProManagerDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
    * 更具id删除维修项目
    * @author rongzoujie
    * @date 2016年8月16日
    * @param id
    * @param uriCB
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepairProMng(@PathVariable("id") Long id,UriComponentsBuilder uriCB) {
        repairProManagerService.deleteRepairMng(id);
    }
    
    /**
    * 更具维修项目code生成维修项目单项复制 
    * @author rongzoujie
    * @date 2016年8月22日
    * @param repairCode
    * @return
     */
    @RequestMapping(value = "/singleCopy/{repairCode}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryModeGroupByRepairProMngCode(@PathVariable("repairCode") String repairCode){
        return repairProManagerService.queryModeGroupByRepairProMngCode(repairCode);
    }
    
    /**
    * 单项复制 
    * @author rongzoujie
    * @date 2016年8月22日
    * @param singleCopyDTO
     */
    @RequestMapping(value = "/selectSingleCopy",method = RequestMethod.PUT)
    @ResponseBody
    public void singleCopy(@RequestBody @Valid SingleCopyDTO singleCopyDTO){
        repairProManagerService.singleCopy(singleCopyDTO);
    }
    
    /**
    * 全部复制
    * @author rongzoujie
    * @date 2016年8月23日
    * @param totalCopyDTO
     */
    @RequestMapping(value = "/totalCopy",method = RequestMethod.PUT)
    @ResponseBody
    public void totalCopy(@RequestBody @Valid TotalCopyDTO totalCopyDTO){
        repairProManagerService.totalCopy(totalCopyDTO.getSrcModeLabourCode(), totalCopyDTO.getDestModelLabourCode());
    }
    
    /**
    * 分类树双击查询
    * @author rongzoujie
    * @date 2016年8月24日
    * @param groupCode
    * @param modeLabourCodes
    * @return
     */
    @RequestMapping(value = "/{groupCode}/queryRepairProsByTree/{modeLabourCodes}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairProsByTree(@PathVariable("groupCode") String groupCode,@PathVariable("modeLabourCodes") String modeLabourCodes){
        String modeLabourCodesFormat = modeLabourCodes.replaceAll("-", ",");
        return repairProManagerService.queryRepairProsByTree(groupCode, modeLabourCodesFormat);
    }
    
    /**
    * 导出维修项目
    * @author rongzoujie
    * @date 2016年8月24日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
     */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportRepairPro(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                HttpServletResponse response) throws Exception{
        List<Map> resultList = repairProManagerService.queryRepairProForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("维修项目信息", resultList);
        
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("MODEL_LABOUR_CODE","项目车型组代码"));
        exportColumnList.add(new ExcelExportColumn("LABOUR_CODE","维修项目代码"));
        exportColumnList.add(new ExcelExportColumn("LABOUR_NAME","维修项目名称"));
        exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE_NAME","维修分类"));
        exportColumnList.add(new ExcelExportColumn("STD_LABOUR_HOUR","标准工时"));
        exportColumnList.add(new ExcelExportColumn("ASSIGN_LABOUR_HOUR","派工工时"));
        exportColumnList.add(new ExcelExportColumn("CLAIM_LABOUR","索赔工时"));
        exportColumnList.add(new ExcelExportColumn("MAIN_GROUP_CODE","主分类代码"));
        exportColumnList.add(new ExcelExportColumn("SUB_GROUP_CODE","二级分类代码"));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("WORKER_TYPE_NAME","工种"));
        exportColumnList.add(new ExcelExportColumn("LOCAL_LABOUR_CODE","行管项目代码"));
        exportColumnList.add(new ExcelExportColumn("LOCAL_LABOUR_NAME","行管项目名称"));
        excelService.generateExcel(excelData, exportColumnList, "维修项目信息.xls", request, response);
        
    }
    
    /**
     *导入维修项目 
    * @author rongzoujie
    * @date 2016年8月30日
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public List<RepairProManagerDTO> importRepairPro(@RequestParam(value = "file") MultipartFile importFile,UriComponentsBuilder uriCB)throws Exception{
     // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<RepairProManagerDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<RepairProManagerDTO>(RepairProManagerDTO.class,new ExcelReadCallBack<RepairProManagerDTO>() {
            @Override
            public void readRowCallBack(RepairProManagerDTO rowDto, boolean isValidateSucess) {
                try{
                    // 保存用户,只有全部是成功的情况下，才执行数据库保存
                    if(isValidateSucess){
//                        repairProManagerService.addRepairPro(rowDto);
                        repairProManagerService.importRepairPro(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));
        
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            System.out.println(importResult.getErrorList());
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
    
    /**
    * 选择维修项目配件
    * @author rongzoujie
    * @date 2016年9月1日
    * @param repairPartsDTO
    * @param uriCB
     */
    @RequestMapping(value = "/setRepairParts", method = RequestMethod.POST)
    @ResponseBody
    public void setRepairParts(@RequestBody RepairPartsDTO repairPartsDTO,UriComponentsBuilder uriCB){
        repairProManagerService.setRepairParts(repairPartsDTO);
    }
    
    /**
    * 根据维修项目查询配件
    * @author rongzoujie
    * @date 2016年9月1日
    * @param labourId
    * @return
     */
    @RequestMapping(value = "/{labourId}/parts", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartsByLabour(@PathVariable("labourId") Long labourId){
        
        return repairProManagerService.queryPartsByLabour(labourId);
    }
    
    /**
     * bug
    * 删除维修项目配件
    * TODO description
    * @author rongzoujie
    * @date 2016年9月1日
    * @param id
     */
    @RequestMapping(value = "/{partId}/parts", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepairPart(@PathVariable("partId") Long id) {
        repairProManagerService.deleteRepairPart(id);
    }
    
    /**
    * 导出维修项目配件
    * @author rongzoujie
    * @date 2016年9月1日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
     */
    @RequestMapping(value = "/parts/export", method = RequestMethod.GET)
    public void exportRepairParts(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                HttpServletResponse response) throws Exception{
    	if(!StringUtils.isNullOrEmpty(queryParam.get("labourId").toString())){
    		Long labourId = Long.parseLong(queryParam.get("labourId").toString());
    		List<Map> resultList = repairProManagerService.exportRepairParts(labourId);
            Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
            excelData.put("维修项目配件信息", resultList);
//            String[] keys = { "PART_NO", "PART_NAME", "PART_QUANTITY", "STOCK_QUANTITY","PART_SALES_PRICE","LABOUR_CODE","MODEL_LABOUR_CODE"};
//            String[] columnNames = { "配件代码 ", "配件名称 ", "数量", "库存数量","销售价", "维修项目代码", "维修车型组代码"};
//            
//            excelService.generateExcel(excelData, keys, columnNames, "维修项目.xls", response);
            
            List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
            exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
            exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
            exportColumnList.add(new ExcelExportColumn("PART_QUAUTITY_NUM","数量"));
            exportColumnList.add(new ExcelExportColumn("AVAILABLE","可用库存"));
            exportColumnList.add(new ExcelExportColumn("PART_SALES_PRICE","销售价"));
            exportColumnList.add(new ExcelExportColumn("LABOUR_CODE","维修项目代码"));
            exportColumnList.add(new ExcelExportColumn("MODEL_LABOUR_CODE","维修车型组代码"));
            excelService.generateExcel(excelData, exportColumnList, "维修项目配件信息.xls", request,response);
    	}
    	
    }
    
    
    /**
     *导入维修配件
    * @author rongzoujie
    * @date 2016年8月30日
     */
    
    @RequestMapping(value = "/parts/import", method = RequestMethod.POST)
    @ResponseBody
    public List<RepairPartsDTO> importRepairPart(@RequestParam(value = "file") MultipartFile importFile,UriComponentsBuilder uriCB)throws Exception{
     // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<RepairPartsDTO> importResult = excelReadServiceForPart.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<RepairPartsDTO>(RepairPartsDTO.class,new ExcelReadCallBack<RepairPartsDTO>() {
            @Override
            public void readRowCallBack(RepairPartsDTO rowDto, boolean isValidateSucess) {
                try{
                    // 保存用户,只有全部是成功的情况下，才执行数据库保存
                    if(isValidateSucess){
                        repairProManagerService.importAddRepairPart(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));
        
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            System.out.println(importResult.getErrorList());
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
    
    /**
    * 根据工单id查找维修项目
    * @author jcsi
    * @date 2016年10月11日
    * @param id
    * @return
     */
    @RequestMapping(value="/{id}/roLabours",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> findByRoLabourByRoId(@PathVariable Long id){
        return repairProManagerService.findByRoLabourByRoId(id);
    }
        
}
