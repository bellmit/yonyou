package com.yonyou.dms.retail.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountBankImportDTO;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountBankImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportPO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportTempPO;
import com.yonyou.dms.retail.service.basedata.RetailBankService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 银行提报查询
 * 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/reality")
@SuppressWarnings("rawtypes")
public class RetailBankController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	RetailBankService rbservice;

	@Autowired
	ExcelGenerator excelService;

	@Autowired
	SystemParamService paramService;

	@Autowired
	private ExcelRead<TmRetailDiscountBankImportTempDTO> excelReadService;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
		logger.info("查询银行提报信息");
		PageInfoDto pageInfoDto = rbservice.findRetailDiscountBank(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = rbservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("银行提报信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BANK_NAME", "金融机构"));
		exportColumnList.add(new ExcelExportColumn("ORG_D", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_X", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码1"));
		exportColumnList.add(new ExcelExportColumn("DMS_CODE", "经销商代码2"));
		exportColumnList.add(new ExcelExportColumn("S_DEALER_SHORTNAME", "零售经销商名称"));
		exportColumnList.add(new ExcelExportColumn("S_DEALER_CODE", "零售经销商代码"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "车架号"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("APP_STATE", "审批状态"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_DATE", "银行放款时间"));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "含税MSRP"));
		exportColumnList.add(new ExcelExportColumn("NET_PRICE", "成交价","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("FINANCING_PNAME", "零售融资产品名称"));
		exportColumnList.add(new ExcelExportColumn("FIRST_PERMENT", "首付","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("DEAL_AMOUNT", "贷款金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("FIRST_PERMENT_RATIO", "首付比例"));
		exportColumnList.add(new ExcelExportColumn("END_PERMENT_RATIO", "尾款比例"));
		exportColumnList.add(new ExcelExportColumn("INSTALL_MENT_NUM", "分期期数", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("TOTAL_INTEREST", "总利息（手续费）","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("INTEREST_RATE", "原利率"));
		exportColumnList.add(new ExcelExportColumn("POLICY_RATE", "政策费率"));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES_RATE", "商户手续费率(客户利率%)"));
		exportColumnList.add(new ExcelExportColumn("ALLOWANCED_SUM_TAX", "贴息金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "销售类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "零售上报时间"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "发票扫描时间"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传时间"));
		exportColumnList.add(new ExcelExportColumn("ACNT", "上传账号"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		excelService.generateExcel(excelData, exportColumnList, "银行提报信息管理.xls", request, response);

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
	 * 根据ID进行查询
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") Long id) {
		TmRetailDiscountBankImportPO model = rbservice.findById(id);
		return model.toMap();
	}

	/**
	 * 导入银行提报
	 * 
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<TmRetailDiscountBankImportTempDTO> importforecastAudit(@RequestParam(value = "file") MultipartFile importFile,
			UriComponentsBuilder uriCB) throws Exception {
		logger.info("============银行提报信息导入===============");
		TmRetailDiscountBankImportTempPO.deleteAll();
		try {
			/**登陆信息**/
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			// 解析Excel 表格(如果需要进行回调)
			ImportResultDto<TmRetailDiscountBankImportTempDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TmRetailDiscountBankImportTempDTO>(TmRetailDiscountBankImportTempDTO.class));
			if(!importResult.isSucess()){
				throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
			}else{
				ArrayList<TmRetailDiscountBankImportTempDTO> dataList = importResult.getDataList();
				ImportResultDto<TmRetailDiscountBankImportTempDTO> list = new ImportResultDto<TmRetailDiscountBankImportTempDTO>();
				rbservice.insertTmRetailDiscountBankImportTemp(dataList,loginInfo);//插入临时表
				// 只有全部是成功的情况下，才执行数据库保存
				list = rbservice.checkData();
				if(list.getErrorList() != null && list.getErrorList().size() >0){
					throw new ServiceBizException("导入出错,请见错误列表", list.getErrorList());
				}
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
		logger.info("===========零售销售上传（查询待插入数据） ===============");
		// 确认后查询待插入的数据
		List<Map> list = rbservice.oemSelectTmpYearPlan(queryParam);
		return list;
	}

	/**
	 * 银行零售贴息导入
	 * 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/checkImportData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TmRetailDiscountBankImportDTO> importExcel(UriComponentsBuilder uriCB,
			@Valid TmRetailDiscountBankImportDTO ypDTO)
			throws ParseException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> tmpList = rbservice.selectTmRetailDiscountBankImportTempList(loginInfo);
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		for (int i = 0; i < tmpList.size(); i++) {
			Map map = tmpList.get(i);
			TmRetailDiscountBankImportPO po = TmRetailDiscountBankImportPO.findFirst("VIN = ? AND DEALER_CODE = ? AND CUSTOMER = ? AND BANK = ? AND APPLY_DATE = ?",
					map.get("VIN").toString(),map.get("DEALER_CODE").toString(),map.get("CUSTOMER").toString(),
					map.get("BANK").toString(),map.get("APPLY_DATE").toString());
			if(po == null){
				po = new TmRetailDiscountBankImportPO();
				getPO(po,map);
				po.setTimestamp("CREATE_DATE", new Date());//创建日期
				po.setLong("CREATE_BY", loginUser.getUserId());
				po.insert();
			}else{
				getPO(po,map);
				po.setTimestamp("UPDATE_DATE", new Date());//修改日期
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.saveIt();
			}
			
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	public TmRetailDiscountBankImportPO getPO(TmRetailDiscountBankImportPO po,Map map){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			po.setLong("BANK", new Long(map.get("BANK").toString()));
			po.setString("DEALER_SHORTNAME", map.get("DEALER_SHORTNAME").toString());// 经销商名称
			po.setString("FINANCING_PNAME", map.get("FINANCING_PNAME").toString());// 产品
			po.setString("DEALER_CODE", map.get("DEALER_CODE").toString());// 经销商代码
			po.setString("CUSTOMER", map.get("CUSTOMER").toString());// 客户姓名
			po.setString("VIN", map.get("VIN").toString());// 车架号
			po.setString("APP_STATE", map.get("APP_STATE").toString());// 审批状态
			// 申请时间
			po.setTimestamp("APPLY_DATE", sdf.parse(map.get("APPLY_DATE").toString()));
			po.setTimestamp("DEAL_DATE", sdf.parse(map.get("DEAL_DATE").toString()));// 银行放款时间
			if (map.get("NET_PRICE").toString() != null && !"".equals(map.get("NET_PRICE").toString())) {
				po.setDouble("NET_PRICE", new Double(map.get("NET_PRICE").toString()));// 成交价
			}
			po.setString("FINANCING_PNAME", map.get("FINANCING_PNAME").toString());// 零售融资产品名称
			if (map.get("FIRST_PERMENT").toString() != null && !"".equals(map.get("FIRST_PERMENT").toString())) {
				po.setString("FIRST_PERMENT", new Double(map.get("FIRST_PERMENT").toString()));// 首付
			}
			if (map.get("DEAL_AMOUNT").toString() != null && !"".equals(map.get("DEAL_AMOUNT").toString())) {
				po.setDouble("DEAL_AMOUNT", new Double(map.get("DEAL_AMOUNT").toString()));// 贷款金额
			}
			if (map.get("END_PERMENT").toString() != null && !"".equals(map.get("END_PERMENT").toString())) {
				po.setString("END_PERMENT", new Double(map.get("END_PERMENT").toString()));// 尾款
			}
			if (map.get("FIRST_PERMENT_RATIO").toString() != null
					&& !"".equals(map.get("FIRST_PERMENT_RATIO").toString())) {
				Double firstPermentRatio = new Double(map.get("FIRST_PERMENT_RATIO").toString());
				po.setDouble("FIRST_PERMENT_RATIO", firstPermentRatio / 100);// 首付比例
			}
	
			if (map.get("END_PERMENT_RATIO").toString() != null
					&& !"".equals(map.get("END_PERMENT_RATIO").toString())) {
				po.setString("END_PERMENT_RATIO", new Double(map.get("END_PERMENT_RATIO").toString()));// 尾款比例
			}
			// 分期期数
			if ("12".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_01);
			} else if ("18".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_02);
			} else if ("24".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_03);
			} else if ("36".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_04);
			} else if ("48".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_05);
			} else if ("60".equals(map.get("INSTALL_MENT_NUM").toString().trim())) {
				po.setInteger("INSTALL_MENT_NUM", OemDictCodeConstants.INSTALLMENT_NUMBER_TYPE_06);
			}
			if (map.get("TOTAL_INTEREST").toString() != null && !"".equals(map.get("TOTAL_INTEREST").toString())) {
				po.setDouble("TOTAL_INTEREST", new Double(map.get("TOTAL_INTEREST").toString()));// 总利息
			}
			if (map.get("INTEREST_RATE").toString() != null && !"".equals(map.get("INTEREST_RATE").toString())) {
				po.setDouble("INTEREST_RATE", new Double(map.get("INTEREST_RATE").toString()));// 原利息
			}
			if (map.get("POLICY_RATE").toString() != null && !"".equals(map.get("POLICY_RATE").toString())) {
				po.setDouble("POLICY_RATE", new Double(map.get("POLICY_RATE").toString()));// 政策利率
			}
			if (map.get("MERCHANT_FEES_RATE").toString() != null
					&& !"".equals(map.get("MERCHANT_FEES_RATE").toString())) {
				po.setDouble("MERCHANT_FEES_RATE", new Double(map.get("MERCHANT_FEES_RATE").toString()));// 商户利率
			}
			if (map.get("ALLOWANCED_SUM_TAX").toString() != null
					&& !"".equals(map.get("ALLOWANCED_SUM_TAX").toString())) {
				po.setDouble("ALLOWANCED_SUM_TAX", new Double(map.get("ALLOWANCED_SUM_TAX").toString()));// 贴息金额
			}
			// po.setMerchantFees(new
			// Double(map.get("MERCHANT_FEES").toString()));
			po.setTimestamp("CREATE_DATE", new Date());//创建日期
			po.setString("REMARK", map.get("REMARK").toString().trim());
			return po;
		} catch (ParseException e) {
			throw new ServiceBizException("导入保存数据出错!请连续管理员！");
		}
	}

}
