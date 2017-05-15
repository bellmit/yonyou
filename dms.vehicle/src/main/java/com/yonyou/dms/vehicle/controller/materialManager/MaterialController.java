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

import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.domains.DTO.materialManager.MaterialDTO;
import com.yonyou.dms.vehicle.service.materialManager.MaterialGroupService;
import com.yonyou.dms.vehicle.service.materialManager.MaterialService;
import com.yonyou.f4.mvc.annotation.TxnConn;


/**
 * 物料维护Controller
 * @author 夏威
 */
@Controller
@TxnConn
@RequestMapping("/materialMaintain")
public class MaterialController {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	@Autowired
	MaterialGroupService mgService;
	
	@Autowired
	MaterialService service;
	
	 @Autowired
	 private ExcelGenerator  excelService;
	
	/**
	 * 
	 * 物料维护查询
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============物料维护查询===============");
		PageInfoDto pageInfoDto = service.queryList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	 * 物料下载
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============物料下载===============");
		List<Map> resultList = service.queryMaterialGroupForExport(queryParam);
	    Map<String, List<Map>> excelData = new HashMap<>();
	    excelData.put("物料信息", resultList);	
	        
	    List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("GROUP_CODE","物料代码"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","物料名称"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_CODE","颜色代码"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色描述"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_CODE","内饰代码"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰描述"));
	    exportColumnList.add(new ExcelExportColumn("IS_SALES","是否销售",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("IS_EC","是否官网",ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("STATUS","状态",ExcelDataType.Oem_Dict));
	    excelService.generateExcel(excelData, exportColumnList, "物料信息.xls", request,response);
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
		PageInfoDto pageInfoDto = mgService.selectMaterialGroupWin(queryParam,2);
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
    	logger.info("============物料修改查询===============");
        MaterialPO po = service.getById(id);
        Map<String, Object> map = po.toMap();
        MaterialGroupRPO po2 = service.getGroupById(id);
        TmVhclMaterialGroupPO po3 = mgService.getById(po2.getLong("GROUP_ID"));
        map.put("GROUP_ID", po3.getString("GROUP_ID"));
        map.put("GROUP_CODE", po3.getString("GROUP_CODE"));
        map.put("GROUP_NAME", po3.getString("GROUP_NAME"));
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
    public ResponseEntity<MaterialDTO> addMaterialGroup(@RequestBody @Valid MaterialDTO mgDto, UriComponentsBuilder uriCB) {
    	logger.info("============物料新增===============");
        Long id = service.addMaterialGroup(mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialMaintain").buildAndExpand(id).toUriString());
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
    public ResponseEntity<MaterialDTO> ModifyMaterialGroup(@PathVariable("id") Long id, @RequestBody @Valid MaterialDTO mgDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============物料修改===============");
    	service.ModifyMaterialGroup(id, mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialMaintain").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
	
    /**
     * 查询选择经销商
     * @param queryParams
     * @return
     */
    @RequestMapping(value="/searchDealers",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllDealers(@RequestParam Map<String, String> queryParams){
    	logger.info("=====弹出经销商多选界面=====");
    	List<Map> tenantMapping = service.getDealerList(queryParams);
        return tenantMapping;
    }
    
	/**
	 * 查询业务范围
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value="/findDealerBuss",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findDealerBuss(){
	   logger.info("=====查询业务范围=====");
	   List<Map> dealerBussDto = service.getDealerBuss();
	     return dealerBussDto;
	}
	
	/**
	 * 物料组下发
	 * @param mgDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/sendMaterialGroup",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MaterialDTO> sendMaterialGroup(@RequestBody MaterialDTO mgDto, UriComponentsBuilder uriCB){
    	logger.info("============物料组下发===============");
        service.sendMaterialGroup(mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialMaintain/sendMaterialGroup").buildAndExpand().toUriString());
        return new ResponseEntity<>(mgDto, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 物料下发
	 * @param mgDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/sendMaterial",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MaterialDTO> sendMaterial(@RequestBody MaterialDTO mgDto, UriComponentsBuilder uriCB){
    	logger.info("============物料下发===============");
        service.sendMaterial(mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/materialMaintain/sendMaterial").buildAndExpand().toUriString());
        return new ResponseEntity<>(mgDto, headers, HttpStatus.CREATED);
	}

}
