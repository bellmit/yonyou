package com.yonyou.dms.customer.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtMysteriousDateDownPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMysteriousDatePO;
import com.yonyou.dms.customer.domains.DTO.basedata.TtMysteriousTempExceDTO;
import com.yonyou.dms.customer.domains.PO.basedata.TtMysteriousTempExcePO;
import com.yonyou.dms.customer.service.basedata.CustomerService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售来店客户信息追溯
 * 
 * @author Administrator
 * 
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/customer")
public class CustomerSteriousController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	CustomerService cservice;
	@Autowired
	ExcelGenerator excelService;

	@Autowired
	SystemParamService paramService;

	@Autowired
	private ExcelRead<TtMysteriousTempExceDTO> excelReadService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
		logger.info("查询销售来店客户信息");
		PageInfoDto pageInfoDto = cservice.findCustomer(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = cservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("销售来店客户信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商简称"));
		exportColumnList.add(new ExcelExportColumn("EXEC_AUTHOR", "执行人员姓名"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "留店电话"));
		exportColumnList.add(new ExcelExportColumn("IS_INPUT_DMS", "是否录入DMS"));
		exportColumnList.add(new ExcelExportColumn("INPUT_NAME", "录入姓名"));
		exportColumnList.add(new ExcelExportColumn("INPUT_PHONE", "录入电话"));
		exportColumnList.add(new ExcelExportColumn("INPUT_DATE", "录入日期"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "反馈日期"));
		excelService.generateExcel(excelData, exportColumnList, "销售来店客户信息管理.xls", request, response);

	}

	/**
	 * 下载
	 */
	@RequestMapping(value = "/export/{type}", method = RequestMethod.GET)
	public void donwloadTemplte(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
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

	/**
	 * 品牌下拉框
	 * 
	 * @return
	 */
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> selecttype(Map<String, String> params) {
		List<Map> chargelist = cservice.selecttype(params);
		return chargelist;
	}

	/**
	 * 零售车辆贴息金额导入
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public List<TtMysteriousTempExceDTO> yearPlanExcelOperate(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, TtMysteriousTempExceDTO forecastImportDto,
			UriComponentsBuilder uriCB) throws Exception {
		CommonsMultipartFile cf= (CommonsMultipartFile)importFile;
		DiskFileItem fi = (DiskFileItem)cf.getFileItem();
		File f = fi.getStoreLocation();
		f.getName();
		TtMysteriousTempExcePO.deleteAll();
		ImportResultDto<TtMysteriousTempExceDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TtMysteriousTempExceDTO>(TtMysteriousTempExceDTO.class,
						new ExcelReadCallBack<TtMysteriousTempExceDTO>() {
							@Override
							public void readRowCallBack(TtMysteriousTempExceDTO rowDto, boolean isValidateSucess) {
								try {
									logger.debug("dealerCode:" + rowDto.getDealerCode());
									// 只有全部是成功的情况下，才执行数据库保存
									if (isValidateSucess) {
										// 校验临时表数据
										cservice.checkData(rowDto);
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
	
	/**
	 * 
	* @Title: getQueryValidatorDataFail 
	* @Description: 导入临时表错误信息
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/validatorDataFailmethod", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> GetQueryValidatorDataFail(@RequestParam Map<String, String> queryParam) {
		logger.info("=========导入临时表错误信息================================================");
		//查询错误信息表
		List<Map> list = cservice.getQueryValidatorDataFail();
		return list;
	}

	/**
	 * 
	 * @Title: oemSelectTmpYearPlan @Description: 临时表数据回显 @param @param
	 * year @param @param queryParam @param @return 设定文件 @return PageInfoDto
	 * 返回类型 @throws
	 */
	@RequestMapping(value = "/oemSelectTmpYearPlan", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> oemSelectTmpYearPlan(@RequestParam Map<String, String> queryParam) {
		logger.info("===========销售来店客户信息追溯（查询待插入数据） ===============");
		// 确认后查询待插入的数据
		List<Map> list = cservice.oemSelectTmpYearPlan(queryParam);
		return list;
	}

	/**
	 * 确认导入数据
	 */
	@RequestMapping(value = "/checkImportData", method = RequestMethod.POST)
	@ResponseBody
	public void confirmImportData(UriComponentsBuilder uriCB,
   		 @Valid TtMysteriousTempExceDTO ypDTO,
			@RequestParam Map<String,String> queryParam) throws ParseException {
		List<TtMysteriousTempExcePO> listTtMysteriousTempExce = TtMysteriousTempExcePO.findAll();
		if (null != listTtMysteriousTempExce && listTtMysteriousTempExce.size() > 0) {// 不为空并且有值
			for (int i = 0; i < listTtMysteriousTempExce.size(); i++) {
				TtMysteriousTempExcePO po = listTtMysteriousTempExce.get(i);
				// 在TT_MYSTERIOUS_DATE表中，经销商code和留店电话是唯一的
				String dealerCode = po.getString("DEALER_CODE");// 经销商code
				String dealerName = po.getString("DEALER_NAME");// 经销商简称
				String execAuthor = po.getString("EXEC_AUTHOR");// 执行人员
				String phone = po.getString("PHONE");// 留店电话
				List<TtMysteriousDatePO> listTMDPO = TtMysteriousDatePO.find("dealer_code = ? AND PHONE = ? ", dealerCode,phone);
				if (listTMDPO != null && listTMDPO.size() > 0) {// 修改
					TtMysteriousDatePO tpo =listTMDPO.get(0);
//					udto.setDealerCode(dealerCode);// 经销code
					tpo.setString("Dealer_code", dealerCode);
//					udto.setDealerName(dealerName);// 经销商简称
					tpo.setString("Dealer_name", dealerName);
//					udto.setExecAuthor(execAuthor);// 执行人员
					tpo.setString("EXEC_AUTHOR", execAuthor);
//					udto.setPhone(phone);// 留店电话
					tpo.setString("PHONE", phone);
					tpo.setInteger("IS_DOWN", 0);
					tpo.setLong("UPDATE_BY", 1111111L);
					tpo.setDate("UPDATE_DATE", new Date());
					tpo.saveIt();
					StringBuffer sql = new StringBuffer();
					// 修改TT_MYSTERIOUS_DATE_DOWN
					sql.append("UPDATE TT_MYSTERIOUS_DATE_DOWN \n");
					sql.append("SET \n");
					sql.append(" IS_INPUT_DMS = " + OemDictCodeConstants.IS_INPUT_DMS_03 + ", \n");// 是否录入DMS
					sql.append(" INPUT_PHONE=null, \n");// 录入电话
					sql.append(" INPUT_NAME=null, \n");// 录入姓名
					sql.append(" INPUT_DATE=null, \n");// 录入时间
					sql.append(" CREATE_BY=11111111, \n");// 创建人
					sql.append(" CREATE_DATE = CURRENT_TIMESTAMP() \n");// 创建时间
					sql.append("WHERE DEALER_CODE = ? \n");
					sql.append("  AND DEALER_NAME = ? \n");
					List<Object> params = new ArrayList<Object>();	
					params.add(dealerCode);
					params.add(dealerName);
					OemDAOUtil.execBatchPreparement(sql.toString(), params);
				} else {// 新增
					TtMysteriousDatePO tpo =new TtMysteriousDatePO();
					tpo.setString("Dealer_code", dealerCode);
					tpo.setString("Dealer_name", dealerName);
					tpo.setString("EXEC_AUTHOR", execAuthor);
					tpo.setString("PHONE", phone);
					tpo.setInteger("IS_DOWN", 0);
					tpo.setLong("UPDATE_BY", 1111111L);
					tpo.setDate("CREATE_DATE", new Date());
					tpo.saveIt();
					// TT_MYSTERIOUS_DATE_DOWN
					TtMysteriousDateDownPO tpp = new TtMysteriousDateDownPO();
					tpp.setString("Dealer_code", dealerCode);
					tpp.setString("Dealer_name", dealerName);
					tpp.setString("EXEC_AUTHOR", execAuthor);
					tpp.setString("PHONE", phone);
					tpp.setInteger("IS_INPUT_DMS", OemDictCodeConstants.IS_INPUT_DMS_03);
					tpp.setLong("CREATE_BY", 1111111L);
					tpp.setDate("CREATE_DATE", new Date());
					tpp.saveIt();
				}
			}
		}

	}
}
