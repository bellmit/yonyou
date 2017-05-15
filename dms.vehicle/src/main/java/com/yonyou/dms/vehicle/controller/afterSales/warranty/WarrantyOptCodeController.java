package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmpWrOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;
import com.yonyou.dms.vehicle.domains.DTO.threePack.ForecastImportDto;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyOptCodeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 操作代码维护
 * @author xuqinqin
 */
@Controller
@TxnConn
@RequestMapping("/warrantyOptCode")
public class WarrantyOptCodeController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
    private ExcelRead<TmpWrOperateDTO>  excelReadService;
	
	@Autowired
	WarrantyOptCodeService  warrantyOptCodeService;
	
	/**
	 * 操作代码维护查询
	 */
	@RequestMapping(value = "/warrantyOptCodeSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto warrantyOptCodeSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==============操作代码维护查询=============");
		PageInfoDto pageInfoDto = warrantyOptCodeService.optCodeQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 操作代码新增
	 */
	@RequestMapping(value = "/addOptCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrOperateDTO> addOptCode(@RequestBody TtWrOperateDTO ptdto, UriComponentsBuilder uriCB) {
		logger.info("=====新增操作代码维护=====");
		Long id = warrantyOptCodeService.addOptCode(ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/warrantyOptCodeAdd/addOptCode").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{ID}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> editBulletinInit(@PathVariable Long ID) throws ParseException{
		logger.info("==================操作代码维护修改回显================");
		Map<String, Object> map = warrantyOptCodeService.editOptCode(ID);
		return map;
	}
	/**
	 * 更新操作代码(保存)
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateOptCode/{ID}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrOperateDTO> saveDeliveryOrder(@RequestBody TtWrOperateDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "ID") BigDecimal id){
		logger.info("==================操作代码维护修改================");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		warrantyOptCodeService.saveOptCode(dto,id,loginUser);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/updateOptCode").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	/**
	 * 操作代码下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/downloadOptCode", method = RequestMethod.GET)
	public void optCodeDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============操作代码维护  主页面下载  ===============");
    	warrantyOptCodeService.optCodeDownload(queryParam, request, response);
	}
    /**
	 * 操作代码维护导入模板下载
	 */
	@RequestMapping(value="/downloadTemple",method = RequestMethod.GET)
	@ResponseBody
	public void downloadTemple(HttpServletRequest request,HttpServletResponse response) {
		logger.info("============操作代码维护导入模板下载===============");
		Map<String, List<Map>> excelData = new HashMap<>();
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		excelData.put("操作代码维护导入模板",null);
		exportColumnList.add(new ExcelExportColumn("", "操作代码"));
		exportColumnList.add(new ExcelExportColumn("", "操作描述（中文）"));
		excelService.generateExcel(excelData, exportColumnList, "操作代码维护导入模板.xls", request, response);
	}
	
	/**
	 * 操作代码维护导入（临时表,导入校验）
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
	
	
	@RequestMapping(value = "/importOptCode", method = RequestMethod.POST)
	@ResponseBody
	public List<TmpWrOperateDTO> wbpImport(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,
			ForecastImportDto forecastImportDto) throws Exception {
		logger.info("============操作代码维护导入（临时表,导入校验）===============");
		ImportResultDto<TmpWrOperateDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TmpWrOperateDTO>(TmpWrOperateDTO.class,
						new ExcelReadCallBack<TmpWrOperateDTO>() {
							@Override
							public void readRowCallBack(TmpWrOperateDTO rowDto, boolean isValidateSucess) {
								try {
									// 只有全部是成功的情况下，才执行数据库保存
									if (isValidateSucess) {
										// 向临时表插入数据
										warrantyOptCodeService.insertTmpOptCode(rowDto);
										// 校验临时表数据
										ImportResultDto<TmpWrOperateDTO> importResultList = warrantyOptCodeService.checkData(rowDto);
										if (importResultList != null) {
											throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList());
										}
									}
								} catch (Exception e) {
									throw e;
								}
							}
						}));
		logger.debug("param:" + forecastImportDto.getFileParam());
		if (importResult.isSucess()) {
			return importResult.getDataList();
		} else {
			throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
		}

	}
}
