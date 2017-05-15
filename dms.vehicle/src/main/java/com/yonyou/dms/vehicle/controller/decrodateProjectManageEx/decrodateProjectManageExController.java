package com.yonyou.dms.vehicle.controller.decrodateProjectManageEx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectManageDTO;
import com.yonyou.dms.vehicle.service.decrodateProjectService.DecrodateProjectService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/decrodateProject")
@SuppressWarnings("rawtypes")
public class decrodateProjectManageExController {

	@Autowired
	private DecrodateProjectService decrodateProjectService;

	@Autowired
	private ExcelGenerator excelService;

	@Autowired
	private ExcelRead<DecrodateImportDTO> excelReadService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDecrodateProject(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = decrodateProjectService.queryDecrodateProject(queryParam);
		return pageInfoDto;
	}

	/**
	 * 多选下拉框 查询项目车型组
	 * 
	 * @return
	 */
	@RequestMapping(value = "/duty/dicts", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllPosition() {
		List<Map> map = decrodateProjectService.findAllPosition();
		return map;
	}

	@RequestMapping(value = "/duty/main", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findMain() {
		List<Map> map = decrodateProjectService.findMain();
		return map;
	}

	@RequestMapping(value = "/{id}/sub", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findsub(@PathVariable String id, @RequestParam Map<String, String> queryParam) {
		queryParam.put("mainGroupCode", id);
		List<Map> serieslist = decrodateProjectService.findsub(queryParam);
		return serieslist;
	}

	@RequestMapping(value = "/duty/sub2", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findSub2() {
		List<Map> map = decrodateProjectService.findSub2();
		return map;
	}

	@RequestMapping(value = "/duty/work", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findWork() {
		List<Map> map = decrodateProjectService.findWork();
		return map;
	}

	@RequestMapping(value = "/findAllTree", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllTree(@RequestParam Map<String, String> queryParam) {
		return decrodateProjectService.findAllTree(queryParam);
	}

	@RequestMapping(value = "/duty/repair", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findRepair() {
		List<Map> map = decrodateProjectService.findRepair();
		return map;
	}

	@RequestMapping(value = "/addDecrodate", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void addDecrodate(@RequestBody DecrodateProjectManageDTO decrodateProjectManageDTO,
			UriComponentsBuilder uriCB) {
		decrodateProjectService.addDecrodate(decrodateProjectManageDTO);
	}

	@RequestMapping(value = "/editDecrodate", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void editDecrodate(@RequestBody DecrodateProjectDTO decrodateProjectDTO, UriComponentsBuilder uriCB) {
		decrodateProjectService.editDecrodate(decrodateProjectDTO);
	}

	@RequestMapping(value = "/findById/{id}/{id1}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByVin(@PathVariable(value = "id") String id,
			@PathVariable(value = "id1") String id1) {
		return decrodateProjectService.findById(id, id1);
	}

	@RequestMapping(value = "/copyAll", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void copyAll(@RequestBody DecrodateProjectDTO decrodateProjectDTO, UriComponentsBuilder uriCB) {
		decrodateProjectService.copyAll(decrodateProjectDTO);
	}

	@RequestMapping(value = "/copyOne", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void copyOne(@RequestBody DecrodateProjectManageDTO decrodateProjectManageDTO, UriComponentsBuilder uriCB) {
		decrodateProjectService.copyOne(decrodateProjectManageDTO);
	}

	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportDecrodate(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList = decrodateProjectService.queryDecrodate(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("维修项目", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("MODEL_LABOUR_CODE", "装潢车型分组代码"));
		exportColumnList.add(new ExcelExportColumn("LABOUR_CODE", "装潢项目代码"));
		exportColumnList.add(new ExcelExportColumn("LABOUR_NAME", "装潢项目名称"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_TYPE_CODE", "装潢类型"));
		exportColumnList.add(new ExcelExportColumn("LOCAL_LABOUR_CODE", "行管项目代码"));
		exportColumnList.add(new ExcelExportColumn("LOCAL_LABOUR_NAME", "行管项目名称"));
		exportColumnList.add(new ExcelExportColumn("STD_LABOUR_HOUR", "标准工时"));
		exportColumnList.add(new ExcelExportColumn("ASSIGN_LABOUR_HOUR", "派工工时"));
		exportColumnList.add(new ExcelExportColumn("WORKER_TYPE_NAME", "工种"));
		exportColumnList.add(new ExcelExportColumn("OPERATION_CODE", "索赔项目代码"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_LABOUR", "索赔工时"));
		exportColumnList.add(new ExcelExportColumn("SPELL_CODE", "拼音代码"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_GROUP_CODE", "装潢项目分组代码"));
		exportColumnList.add(new ExcelExportColumn("OEM_LABOUR_HOUR", "OEM标准工时"));
		exportColumnList.add(new ExcelExportColumn("DOWN_TAG", "是否下发", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CREATED_AT", "创建日期", CommonConstants.FULL_DATE_TIME_FORMAT));
		exportColumnList.add(new ExcelExportColumn("UPDATED_AT", "更新日期", CommonConstants.FULL_DATE_TIME_FORMAT));
		exportColumnList.add(new ExcelExportColumn("IS_MEMBER", "是否会员专用", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("REPLACE_STATUS", "更新状态"));

		// 生成excel 文件
		excelService.generateExcelForDms(excelData, exportColumnList,
				FrameworkUtil.getLoginInfo().getDealerShortName() + "_维修项目.xls", request, response);

	}

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public List<DecrodateImportDTO> importList(@RequestParam(value = "file") MultipartFile importFile,
			UriComponentsBuilder uriCB) throws Exception {
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<DecrodateImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<DecrodateImportDTO>(DecrodateImportDTO.class,
						new ExcelReadCallBack<DecrodateImportDTO>() {
							@Override
							public void readRowCallBack(DecrodateImportDTO rowDto, boolean isValidateSucess) {
								try {
									if (isValidateSucess) {
										decrodateProjectService.addInfo(rowDto);
									}
								} catch (Exception e) {
									throw e;
								}
							}
						}));

		if (importResult.isSucess()) {
			return importResult.getDataList();
		} else {
			throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
		}
	}

}
