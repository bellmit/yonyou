
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInfoController.java
*
* @Author : xukl
*
* @Date : 2016年7月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月5日    xukl    1.0
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

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
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
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoImportDto;
import com.yonyou.dms.part.service.basedata.PartInfoService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 简要描述:配件基础信息控制类
* @author xukl
* @date 2016年7月5日
*/
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/basedata/partInfos")
public class PartInfoController extends BaseController {
 // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
    private PartInfoService partInfoService;
    
    @Autowired
    private ExcelGenerator excelService;
    @Autowired
    private ExcelRead<PartInfoDTO>  excelReadService;
    /**
    * 简要描述:根据条件查询配件基础信息
    * @author xukl
    * @date 2016年7月6日
    * @param queryParam
    * @return 查询结果
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfos(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = partInfoService.queryPartInfos(queryParam);
        return pageInfoDto;
    }
    
    
    /**
    * 新增配件基本信息
    * @author xukl
    * @date 2016年7月6日
    * @param partInfoDTO
    * @param uriCB
    * @return 新增配件信息
    */
    	
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<PartInfoDTO> addPartInfo(@RequestBody @Valid PartInfoDTO partInfoDTO,UriComponentsBuilder uriCB) {
    	String id = partInfoService.addPartInfo(partInfoDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partInfos/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<PartInfoDTO>(partInfoDTO,headers, HttpStatus.CREATED);  

    }
    
    /**
    * 修改
    * @author xukl
    * @date 2016年7月12日
    * @param id
    * @param partInfoDTO
    * @param uriCB
    * @return
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<PartInfoDTO> ModifyPartInfo(@PathVariable("id") String id,@RequestBody @Valid PartInfoDTO partInfoDTO,
                                                      UriComponentsBuilder uriCB) {
        partInfoService.modifyPartInfo(id, partInfoDTO);;
        
        MultiValueMap<String, String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/basedata/partInfos/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<PartInfoDTO>(headers, HttpStatus.CREATED);  
    }
    
    /**
    * 删除
    * @author xukl
    * @date 2016年7月13日
    * @param id
    * @param uriCB
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") String id,UriComponentsBuilder uriCB) {
        partInfoService.deletePartInfoById(id);
    }
    
    /**
    * 通过配件id查询配件信息
    * @author xukl
    * @date 2016年7月13日
    * @param id
    * @return
    */
    	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getPartInfoById(@PathVariable(value = "id") String id) {
        TmPartInfoPO partInfo = partInfoService.getPartInfoById(id);
        return partInfo.toMap();
    }
    
    /**
    * 导出
    * @author xukl
    * @date 2016年7月22日
    * @param queryParam
    * @param request
    * @param response
    * @throws Exception
    */
    	
    @RequestMapping(value = "/export/items", method = RequestMethod.GET)
    public void exportPartInfos(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                            HttpServletResponse response) throws Exception {
        List<Map> resultList = partInfoService.queryPartInfoForExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("配件基本信息", resultList);
        
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BRAND","品牌"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("PART_TYPE","配件类型"));
        exportColumnList.add(new ExcelExportColumn("REPORT_WAY","提报方式"));
        exportColumnList.add(new ExcelExportColumn("PART_INFIX","中缀代码"));
        exportColumnList.add(new ExcelExportColumn("PART_INFIX_NAME","中缀名称"));
        exportColumnList.add(new ExcelExportColumn("OPTION_NO","替代件"));
        exportColumnList.add(new ExcelExportColumn("OPTION_RELATION","替代关系"));
        exportColumnList.add(new ExcelExportColumn("SPELL_CODE","拼音代码"));
        exportColumnList.add(new ExcelExportColumn("GOODS_BRANDS","用品品牌"));
        exportColumnList.add(new ExcelExportColumn("OEM_NO","原厂编号"));
        exportColumnList.add(new ExcelExportColumn("BIG_CATEGORY_CODE","用品大分类"));
        exportColumnList.add(new ExcelExportColumn("SUB_CATEGORY_CODE","用品中分类"));
        exportColumnList.add(new ExcelExportColumn("THD_CATEGORY_CODE","用品小分类"));
        exportColumnList.add(new ExcelExportColumn("MIN_PACKAGE","最小包装数"));
        exportColumnList.add(new ExcelExportColumn("ACC_MODE","型号"));
        exportColumnList.add(new ExcelExportColumn("LIMIT_NO","限制编号"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
        exportColumnList.add(new ExcelExportColumn("PART_GROUP_CODE","配件类别",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PART_MODEL_GROUP_CODE_SET","配件车型组"));
        exportColumnList.add(new ExcelExportColumn("QUANTITY_PER_CAR","单车用量"));
        exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("LIMIT_PRICE","销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("INSTRUCT_PRICE","建议销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("CLAIM_PRICE","索赔价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("REGULAR_PRICE","常规订货价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
        exportColumnList.add(new ExcelExportColumn("URGENT_PRICE","急件价"));
        exportColumnList.add(new ExcelExportColumn("INSURANCE_PRICE","保险价"));
        exportColumnList.add(new ExcelExportColumn("ORI_PRO_CODE","原产地代码"));
        exportColumnList.add(new ExcelExportColumn("MAIN_ORDER_TYPE","订单分类"));
        exportColumnList.add(new ExcelExportColumn("MAX_STOCK","最大库存"));
        exportColumnList.add(new ExcelExportColumn("SAFE_STOCK","安全库存"));
        exportColumnList.add(new ExcelExportColumn("MIN_STOCK","最小库存"));
        exportColumnList.add(new ExcelExportColumn("LEAD_TIME","订货周期"));
        exportColumnList.add(new ExcelExportColumn("PART_VEHICLE_MODEL","所属车型"));
        exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA","产地"));
        exportColumnList.add(new ExcelExportColumn("QUANTITY_PER_CAR","单车用量"));
        exportColumnList.add(new ExcelExportColumn("CREATED_AT","创建日期"));
        exportColumnList.add(new ExcelExportColumn("UPDATED_AT","更新日期"));
        exportColumnList.add(new ExcelExportColumn("UNIT_NAME","计量单位名称"));
        exportColumnList.add(new ExcelExportColumn("BO_FLAG","是否欠货",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_STORAGE_SALE","是否寄售",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_ACC","是否用品",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PART_STATUS","是否停用",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_UNSAFE","是否危险品",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_MOP","是否M",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("IS_SJV","是否S",ExcelDataType.Dict));
        exportColumnList.add(new ExcelExportColumn("PROVIDER_CODE","供应商代码"));
        exportColumnList.add(new ExcelExportColumn("PROVIDER_NAME","供应商名称"));
        exportColumnList.add(new ExcelExportColumn("IS_UNSAFE","备注"));
        //生成excel 文件
        excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_配件基本信息.xls", request, response);

    }
    
    /**
     * 模板下载
     * @author xukl
     * @date 2016年7月22日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
         
     @RequestMapping(value = "/export/itemss", method = RequestMethod.GET)
     public void exportPartInfoss(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("配件基本信息", null);
         
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
         exportColumnList.add(new ExcelExportColumn("BRAND","品牌"));
         exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
         exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
         exportColumnList.add(new ExcelExportColumn("PART_TYPE","配件类型"));
         exportColumnList.add(new ExcelExportColumn("REPORT_WAY","提报方式"));
         exportColumnList.add(new ExcelExportColumn("PART_INFIX","中缀代码"));
         exportColumnList.add(new ExcelExportColumn("PART_INFIX_NAME","中缀名称"));
         exportColumnList.add(new ExcelExportColumn("OPTION_NO","替代件"));
         exportColumnList.add(new ExcelExportColumn("OPTION_RELATION","替代关系"));
         exportColumnList.add(new ExcelExportColumn("SPELL_CODE","拼音代码"));
         exportColumnList.add(new ExcelExportColumn("GOODS_BRANDS","用品品牌"));
         exportColumnList.add(new ExcelExportColumn("OEM_NO","原厂编号"));
         exportColumnList.add(new ExcelExportColumn("BIG_CATEGORY_CODE","用品大分类"));
         exportColumnList.add(new ExcelExportColumn("SUB_CATEGORY_CODE","用品中分类"));
         exportColumnList.add(new ExcelExportColumn("THD_CATEGORY_CODE","用品小分类"));
         exportColumnList.add(new ExcelExportColumn("MIN_PACKAGE","最小包装数"));
         exportColumnList.add(new ExcelExportColumn("ACC_MODE","型号"));
         exportColumnList.add(new ExcelExportColumn("LIMIT_NO","限制编号"));
         exportColumnList.add(new ExcelExportColumn("UNIT_CODE","单位"));
         exportColumnList.add(new ExcelExportColumn("PART_GROUP_CODE","配件类别",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("PART_MAIN_TYPE","九大类",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("PART_MODEL_GROUP_CODE_SET","配件车型组"));
         exportColumnList.add(new ExcelExportColumn("QUANTITY_PER_CAR","单车用量"));
         exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("LIMIT_PRICE","销售限价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("INSTRUCT_PRICE","建议销售价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("CLAIM_PRICE","索赔价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("REGULAR_PRICE","常规订货价",CommonConstants.Excel_NUMBER_FORMAT_SAMPLE));
         exportColumnList.add(new ExcelExportColumn("URGENT_PRICE","急件价"));
         exportColumnList.add(new ExcelExportColumn("INSURANCE_PRICE","保险价"));
         exportColumnList.add(new ExcelExportColumn("ORI_PRO_CODE","原产地代码"));
         exportColumnList.add(new ExcelExportColumn("MAIN_ORDER_TYPE","订单分类"));
         exportColumnList.add(new ExcelExportColumn("MAX_STOCK","最大库存"));
         exportColumnList.add(new ExcelExportColumn("SAFE_STOCK","安全库存"));
         exportColumnList.add(new ExcelExportColumn("MIN_STOCK","最小库存"));
         exportColumnList.add(new ExcelExportColumn("LEAD_TIME","订货周期"));
         exportColumnList.add(new ExcelExportColumn("PART_VEHICLE_MODEL","所属车型"));
         exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA","产地"));
         exportColumnList.add(new ExcelExportColumn("QUANTITY_PER_CAR","单车用量"));
         exportColumnList.add(new ExcelExportColumn("CREATED_AT","创建日期"));
         exportColumnList.add(new ExcelExportColumn("UPDATED_AT","更新日期"));
         exportColumnList.add(new ExcelExportColumn("UNIT_NAME","计量单位名称"));
         exportColumnList.add(new ExcelExportColumn("BO_FLAG","是否欠货",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_STORAGE_SALE","是否寄售",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_ACC","是否用品",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("PART_STATUS","是否停用",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_UNSAFE","是否危险品",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_MOP","是否M",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("IS_SJV","是否S",ExcelDataType.Dict));
         exportColumnList.add(new ExcelExportColumn("PROVIDER_CODE","供应商代码"));
         exportColumnList.add(new ExcelExportColumn("PROVIDER_NAME","供应商名称"));
         exportColumnList.add(new ExcelExportColumn("IS_UNSAFE","备注"));
         //生成excel 文件
         excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_配件基本信息.xls", request, response);

     }
    
    /**
    * 配件基础信息和库存信息联查
    * @author xukl
    * @date 2016年8月3日
    * @param queryParam
    * @return pageInfoDto
    * @throws Exception
    */
    	
    @RequestMapping(value = "/part/inWarehouseQry", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto qrPartInfos( @RequestParam Map<String, String> queryParam) throws Exception {
        PageInfoDto pageInfoDto = partInfoService.qryPartInfos(queryParam);
        return pageInfoDto;
      }
    
    /**
     * 下拉框
     * @author dingchaoyu
     * @date 2016年8月25日
     * @param importFile
     * @param partInfoImportDto
     * @param uriCB
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value="/partGroupCode" , method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryUnitCode(@RequestParam Map<String, String> queryParam) {
    	List<Map> brandlist = partInfoService.queryUnitCode(queryParam);
		return brandlist;
	}
    
    @RequestMapping(value="/querypartGroupCodes" , method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryPartGroupCodes(@RequestParam Map<String, String> queryParam) {
    	List<Map> brandlist = partInfoService.queryPartGroupCodes(queryParam);
		return brandlist;
	}
    
    /**
     * 配件车型组
     * @author dingchaoyu
     * @date 2016年8月25日
     * @param importFile
     * @param partInfoImportDto
     * @param uriCB
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value="/querypartGroupCode" , method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartGroupCode(@RequestParam Map<String, String> queryParam) {
    	PageInfoDto brandlist = partInfoService.queryPartGroupCode(queryParam);
		return brandlist;
	}
    
    /**
     * 配件车型车系
     * @author dingchaoyu
     * @date 2016年8月25日
     * @param importFile
     * @param partInfoImportDto
     * @param uriCB
     * @return
     * @throws Exception
     */
    
    @RequestMapping(value="/partModel" , method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartModel(@RequestParam Map<String, String> queryParam) {
    	PageInfoDto brandlist = partInfoService.queryModel(queryParam);
		return brandlist;
	}
    
    /**
    * 导入
    * @author xukl
    * @date 2016年8月25日
    * @param importFile
    * @param partInfoImportDto
    * @param uriCB
    * @return
    * @throws Exception
    */
    	
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public List<PartInfoDTO> importPartInfos(@RequestParam(value = "file") MultipartFile importFile, PartInfoImportDto partInfoImportDto, UriComponentsBuilder uriCB) throws Exception {
        ImportResultDto<PartInfoDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<PartInfoDTO>(PartInfoDTO.class,new ExcelReadCallBack<PartInfoDTO>() {
            @Override
            public void readRowCallBack(PartInfoDTO rowDto, boolean isValidateSucess) {
                try{
                    if(isValidateSucess){
                        partInfoService.addPartInfo(rowDto);
                    }
                }catch(Exception e){
                    throw e;
                }
            }
        }));
        logger.debug("param:" + partInfoImportDto.getFileParam());
        
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错",importResult.getErrorList()) ;
        }
    }
    
    /**
     * 导入
     * @author jcsi
     * @date 2016年8月25日
     * @param partCode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/by/{partNO}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> searchByPartCode(@PathVariable String partNo) {
    	return partInfoService.searchByPartCode(partNo);
    }
    
}
