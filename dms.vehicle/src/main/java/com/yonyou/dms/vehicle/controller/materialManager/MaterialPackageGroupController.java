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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialPackageGroupDTO;
import com.yonyou.dms.vehicle.domains.PO.materialManager.MaterialPackageGroupPO;
import com.yonyou.dms.vehicle.service.materialManager.MaterialGroupService;
import com.yonyou.dms.vehicle.service.materialManager.MaterialPackageGroupService;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
 * 车款组维护Controller
 * @author 夏威
 */
@Controller
@TxnConn
@RequestMapping("/materialPackageGroupMaintain")
public class MaterialPackageGroupController {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialPackageGroupController.class);
	
	@Autowired
	MaterialGroupService mgService;
	
	@Autowired
	MaterialPackageGroupService mpgService;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 
	 * 车款组维护查询
	 * @author 夏威
	 * @date 2017年1月20日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============车款组维护查询===============");
		PageInfoDto pageInfoDto = mpgService.queryList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 
	 * 车款组维护查询
	 * @author 夏威
	 * @date 2017年1月20日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/select/packageGroup", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto selectList(@RequestParam Map<String, String> queryParam) {
		logger.info("============车款查询===============");
		PageInfoDto pageInfoDto = mgService.selectMaterialGroupWin(queryParam, 2);
		return pageInfoDto;
	}
	
	/**
	 * 
	 * 车款组下载
	 * @author 夏威
	 * @date 2017年1月20日
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============车款组下载===============");
		List<Map> resultList = mpgService.queryListForExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<>();
	    excelData.put("车款组信息", resultList);	
	        
	    List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("PACKAGE_GROUP_CODE","物料组代码"));
	    exportColumnList.add(new ExcelExportColumn("PACKAGE_GROUP_NAME","物料组名称"));
	    exportColumnList.add(new ExcelExportColumn("STATUS","状态",ExcelDataType.Oem_Dict));
	    excelService.generateExcel(excelData, exportColumnList, "车款组信息.xls", request,response);
	}
    
    
    /**
     * 根据ID 获取车款组的信息
     * @author xiawei
     * @date 2017年1月20日
     * @param id 用户ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@PathVariable(value = "id") Long id) {
    	logger.info("============车款组修改查询===============");
        MaterialPackageGroupPO po = mpgService.getById(id);
        Map<String, Object> map = po.toMap();
        return map;
    }

    /**
     * 新增车款组信息
     * @author xiawei
     * @date 2017年1月20日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MaterialPackageGroupDTO> addMaterialGroup(@RequestBody @Valid MaterialPackageGroupDTO mpgDto, UriComponentsBuilder uriCB) {
    	logger.info("============车款组新增===============");
        Long id = mpgService.addMaterialPackageGroup(mpgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialPackageGroupMaintain").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(mpgDto, headers, HttpStatus.CREATED);

    }

    /**
     * 根据ID 修改车款组信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<MaterialPackageGroupDTO> ModifyMaterialGroup(@PathVariable("id") Long id, @RequestBody @Valid MaterialPackageGroupDTO mpgDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============车款组修改===============");
    	mpgService.ModifyMaterialPackageGroup(id, mpgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialPackageGroupMaintain").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
	
	

}
