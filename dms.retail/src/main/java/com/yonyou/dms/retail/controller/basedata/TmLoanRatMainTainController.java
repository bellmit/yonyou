package com.yonyou.dms.retail.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.rpc.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

import com.yonyou.dcs.gacfca.SADCS022Cluod;
import com.yonyou.dms.DTO.gacfca.CLDMS009Dto;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmLoanRatMaintainDTO;
import com.yonyou.dms.retail.service.basedata.TmLoanRatMainTainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 贴息利率维护
 * 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/tateMain")
public class TmLoanRatMainTainController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TmLoanRatMainTainService tlservice;

	@Autowired
	ExcelGenerator excelService;

	@Autowired
	SystemParamService paramService;

	@Autowired
	SADCS022Cluod sadcs022;

	@Autowired
	private ExcelRead<TmLoanRatMaintainDTO> excelReadService;

	/**
	 * 下载
	 */
	@RequestMapping(value = "/export/{type}", method = RequestMethod.GET)
	public void donwloadTemplte(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("贴息利率信息导入模板下载");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 查询对应的参数
			BasicParametersDTO paramDto = paramService
					.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
			Resource resource = new ClassPathResource(paramDto.getParamValue());
			// 获取文件名称
			FrameHttpUtil.setExportFileName(request, response, resource.getFilename());

			response.addHeader("Content-Length", "" + resource.getFile().length());

			bis = new BufferedInputStream(resource.getInputStream());
			byte[] bytes = new byte[1024];
			bos = new BufferedOutputStream(response.getOutputStream());
			while ((bis.read(bytes)) != -1) {
				bos.write(bytes);
			}
			bos.flush();
		} catch (Exception e) {
			throw new ServiceBizException("下载模板失败,请与管理员联系", e);
		} finally {
			IOUtils.closeStream(bis);
			IOUtils.closeStream(bos);
		}
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("贴息利率信息下载");
		List<Map> resultList = tlservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("贴息利率信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_GROUP_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_GROUP_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("STYLE_GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("BANK_NAME", "银行"));
		exportColumnList.add(new ExcelExportColumn("INSTALLMENT_NUMBER", "分期期数", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("DPM_S", "首付比例区间"));
		exportColumnList.add(new ExcelExportColumn("RATE", "贴息利率"));
		exportColumnList.add(new ExcelExportColumn("EFFECTIVE_DATE_S", "有效开始时间"));
		exportColumnList.add(new ExcelExportColumn("EFFECTIVE_DATE_E", "有效结束时间"));
		exportColumnList.add(new ExcelExportColumn("IS_SCAN", "下发状态"));
		exportColumnList.add(new ExcelExportColumn("SEND_DATE", "下发时间"));
		exportColumnList.add(new ExcelExportColumn("NAME", "操作人"));
		excelService.generateExcel(excelData, exportColumnList, "贴息利率信息管理.xls", request, response);

	}

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
		logger.info("查询贴息利率信息");
		PageInfoDto pageInfoDto = tlservice.getTmLoanlist(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TmLoanRatMaintainDTO> addMaterialGroup(@RequestBody TmLoanRatMaintainDTO tldto,UriComponentsBuilder uriCB) {
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		logger.info("新增合作银行信息");
		tlservice.addTmLoan(tldto, loginInfo);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/add").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);  
	}

	/**
	 * 根据ID 删除用户信息
	 * 
	 * @author zhangxc
	 * @date 2016年6月30日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		tlservice.deleteChargeById(id);
	}

	/**
	 * 批量删除
	 * 
	 * @param userSelectDto
	 */
	@RequestMapping(value = "/batch/delete", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void batchDelete(@RequestBody TmLoanRatMaintainDTO tldto) {
		logger.debug("ids:" + tldto.getIds());
		tlservice.delAll(tldto);
	}

	/**
	 * 贴息利率导入
	 * 
	 * @param importFile
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<TmLoanRatMaintainDTO> importExcel(@RequestParam(value = "file") MultipartFile importFile,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		logger.info("贴息利率信息导入");
		// 解析Excel 表格(如果需要进行回调)
		try {
			ImportResultDto<TmLoanRatMaintainDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TmLoanRatMaintainDTO>(TmLoanRatMaintainDTO.class));
			ArrayList<TmLoanRatMaintainDTO> dataList = importResult.getDataList();
			boolean result = tlservice.checkData(dataList, loginInfo);
			if(result){
				return null;
			}else{
				throw new ServiceBizException("数据导入失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
		
	}

	/**
	 * 贴息利率下发
	 */
	@RequestMapping(value = "/sent", method = RequestMethod.GET)
	public ResponseEntity<CLDMS009Dto> sentModCompeteInfo(@RequestParam Map<String, String> queryParam,UriComponentsBuilder uriCB){
		logger.info("贴息利率信息下发");
		/** 当前登录信息 **/
		String id = queryParam.get("ID");
		sadcs022.sendDataEach(id);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	/**
	 * 贴息利率批量下发
	 */
	@RequestMapping(value = "/batch/sent", method = RequestMethod.PUT)
	public ResponseEntity<TmLoanRatMaintainDTO> sentModCompeteInfo(@RequestBody @Valid TmLoanRatMaintainDTO tldto,
			UriComponentsBuilder uriCB) throws ServiceBizException {
		logger.info("贴息利率信息下发");
		/** 当前登录信息 **/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		try {
			tlservice.sentDiscountRateInfo(tldto);
			Long ids = loginInfo.getDealerId();
			MultiValueMap<String, String> headers = new HttpHeaders();
			headers.set("Location", uriCB.path("/users/{ids}").buildAndExpand(ids).toUriString());
			return new ResponseEntity<>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}