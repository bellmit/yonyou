package com.yonyou.dms.vehicle.controller.materialManager;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmpVhclMaterialGroupDcsPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.MaterialGroupImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialGroupDTO;
import com.yonyou.dms.vehicle.service.materialManager.MaterialGroupService;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
 * 物料组维护Controller
 * @author 夏威
 *
 */
@Controller
@TxnConn
@RequestMapping("/materialGroupMaintain")
public class MaterialGroupController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	MaterialGroupService service;
	
	
	@Autowired
	private ExcelGenerator  excelService;
	
    @Autowired
    SystemParamService paramService;   
    
	@Autowired
    private ExcelRead<MaterialGroupImportDTO>  excelReadService;
	
	/**
	 * 
	 * 物料组维护查询
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============物料组维护查询===============");
		PageInfoDto pageInfoDto = service.queryList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	 * 物料组下载
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    @ResponseBody
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============物料组下载===============");
		List<Map> resultList = service.queryMaterialGroupForExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<>();
	    excelData.put("物料组信息", resultList);	
	        
	    List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("GROUP_CODE","物料组代码"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","物料组名称"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_LEVEL","物料组级别"));
	    exportColumnList.add(new ExcelExportColumn("STANDARD_OPTION","标准配置"));
	    exportColumnList.add(new ExcelExportColumn("OTHER_OPTION","其他配置"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("WX_ENGINE","发动机排量"));
	    exportColumnList.add(new ExcelExportColumn("OILE_TYPE","燃油类型",ExcelDataType.Oem_Dict));
	    //其它类型：Region_Provice,Region_City,Region_Country
	    exportColumnList.add(new ExcelExportColumn("IF_FOREC","是否参与预测"));
	    exportColumnList.add(new ExcelExportColumn("IS_EC","是否官网"));
	    exportColumnList.add(new ExcelExportColumn("STATUS","状态",ExcelDataType.Oem_Dict));
	    excelService.generateExcel(excelData, exportColumnList, "物料组信息.xls", request,response);
	}
    
    /**
	 * 
	 * 物料组明细下载
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/export/excelDetail", method = RequestMethod.GET)
	public void downLoadDetailList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============物料组明细下载===============");
		List<Map> resultList = service.queryMaterialGroupDetailForExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<>();
	    excelData.put("物料组明细信息", resultList);	
	        
	    List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("BRAND_CODE","品牌代码"));
	    exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌名称"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
	    exportColumnList.add(new ExcelExportColumn("IF_FOREC","是否参与预测"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_CODE","车款代码"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款名称"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("STANDARD_OPTION","标准配置"));
	    exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS","其他配置"));
	    exportColumnList.add(new ExcelExportColumn("LOCAL_OPTION","本地配置"));
	    exportColumnList.add(new ExcelExportColumn("IS_EC","是否官网"));
	    exportColumnList.add(new ExcelExportColumn("STATUS","状态",ExcelDataType.Dict));
	    excelService.generateExcel(excelData, exportColumnList, "物料组明细信息.xls", request,response);
	}
    /**
     * 
     * 上级物料弹出
     * @author 夏威
     * @date 2017年1月16日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/select/materialGroupWin", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto selectMaterialGroupWin(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("============上级物料组查询===============");
		PageInfoDto pageInfoDto = service.selectMaterialGroupWin(queryParam,1);
		return pageInfoDto;
    }
	
    
    
    /**
     * 根据ID 获取物料组的信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id 用户ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@PathVariable(value = "id") Long id) {
    	TmVhclMaterialGroupPO po = service.getById(id);
        Map<String, Object> map = po.toMap();
        if(!po.getInteger("GROUP_LEVEL").toString().equals("1")){
        	TmVhclMaterialGroupPO po2 = service.getById(po.getLong("PARENT_GROUP_ID"));
	        map.put("PARENT_GROUP_CODE", po2.getString("GROUP_CODE"));
        }
        return map;
    }

    /**
     * 新增物料组信息
     * @author xiawei
     * @date 2017年1月17日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MaterialGroupDTO> addMaterialGroup(@RequestBody @Valid MaterialGroupDTO mgDto, UriComponentsBuilder uriCB) {

        Long id = service.addMaterialGroup(mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialGroupMaintain").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(mgDto, headers, HttpStatus.CREATED);

    }

    /**
     * 根据ID 修改物料组信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MaterialGroupDTO> ModifyMaterialGroup(@PathVariable("id") Long id, @RequestBody @Valid MaterialGroupDTO mgDto,
                                              UriComponentsBuilder uriCB) {
    	service.ModifyMaterialGroup(id, mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialGroupMaintain").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
	
    /**
     * 物料组表更导入临时表
     * @param queryParam
     * @param importFile
     * @param uriCB
     * @return
     * @throws Exception
     */
 	@RequestMapping(value = "/import", method = RequestMethod.POST)
 	@ResponseBody
	public ArrayList<MaterialGroupImportDTO> importExcel(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile, UriComponentsBuilder uriCB) throws Exception{
		logger.info("===== 物料组表更导入临时表=====");
		int delAll = TmpVhclMaterialGroupDcsPO.deleteAll();
		// 解析Excel 表格(如果需要进行回调)
        ImportResultDto<MaterialGroupImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<MaterialGroupImportDTO>(MaterialGroupImportDTO.class ));
        if(importResult.isSucess()){ 
        	ArrayList<MaterialGroupImportDTO> dataList = importResult.getDataList();
        	ArrayList<MaterialGroupImportDTO> errorList = service.checkData(dataList);
        	importResult.setErrorList(errorList);
        }
        
        if(importResult.getErrorList() != null && !importResult.getErrorList().isEmpty()){
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList());
        }else{       	
        	return importResult.getDataList();
        }
	}
 	
 	/**
 	 * 物料组表更导入正式表
 	 * @param uriCB
 	 * @return
 	 */
 	@RequestMapping(value = "/importSave", method = RequestMethod.POST)
 	@ResponseBody
 	public ResponseEntity<MaterialGroupImportDTO> importSave(UriComponentsBuilder uriCB){
 		logger.info("===== 物料组表更导入正式表=====");
 		service.importSave();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/queryDealers/importSave").buildAndExpand().toUriString());
		return new ResponseEntity<MaterialGroupImportDTO>( HttpStatus.CREATED);
 	}
	

}
