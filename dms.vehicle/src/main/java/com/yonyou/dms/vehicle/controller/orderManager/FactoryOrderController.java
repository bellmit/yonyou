package com.yonyou.dms.vehicle.controller.orderManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.ResourceOrderUploadDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.importDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtVsUploadCommonResourcePO;
import com.yonyou.dms.vehicle.service.orderManage.FactoryOrderService;
import com.yonyou.dms.vehicle.service.orderManage.ResourceImportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 工厂订单查询下载
 * 
 * @author 廉兴鲁
 * @date 2016年1月19日
 */
@Controller
@TxnConn
@RequestMapping("/orderManager")
@ResponseBody
@SuppressWarnings("all")
public class FactoryOrderController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FactoryOrderController.class);

	@Autowired
	private FactoryOrderService orderService;
	@Autowired
	private ExcelGenerator excelService;
	@Autowired
	private ExcelRead<ResourceOrderUploadDTO> excelReadService;
	@Autowired
	private ResourceImportService resourceImportService;

	@RequestMapping(value = "/orderQuery", method = RequestMethod.GET)
	// 查询工厂订单信息

	public PageInfoDto orderQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============工厂订查询===============");
		PageInfoDto pageInfoDto = orderService.orderQuery(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/download/excel", method = RequestMethod.GET)
	public void findFactroyOrderDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============工厂订单下载===============");

		List<Map> orderList = orderService.findFactroyOrderDownload(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("工厂订单信息", orderList);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE", "车型"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型描述"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_CODE", "颜色"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色描述"));
		exportColumnList.add(new ExcelExportColumn("TRIM_CODE", "内饰"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰描述"));
		exportColumnList.add(new ExcelExportColumn("STANDARD_OPTION", "标准配置"));
		exportColumnList.add(new ExcelExportColumn("FACTORY_OPTIONS", "工厂配置"));
		exportColumnList.add(new ExcelExportColumn("LOCAL_OPTION", "本地配置"));
		exportColumnList.add(new ExcelExportColumn("NODE", "车辆节点"));
		exportColumnList.add(new ExcelExportColumn("CODE_CN_DESC", "节点描述"));
		exportColumnList.add(new ExcelExportColumn("PRIMARY_STATUS", "订单主状态"));
		exportColumnList.add(new ExcelExportColumn("SECONDARY_STATUS", "订单第二状态"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_NUMBER", "发票号"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_DATE", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("EXPECT_PORT_DATE", "预计到港日期"));
		exportColumnList.add(new ExcelExportColumn("SOLD_TO", "售达方"));
		exportColumnList.add(new ExcelExportColumn("FLAG", "是否下发", ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "工厂订单信息.xls", request, response);

	}

	/**
	 * 资源上传
	 * 
	 * @author 廉兴鲁
	 * @date 2017-02-10 ResourceOrderUploadDTO
	 */

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resourceorder(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, importDto importDto, UriComponentsBuilder uriCB)
			throws Exception {
		logger.info("============资源上传===============");
		String orderType = queryParam.get("orderType").toString();
		Map<String, Object> resultMap = new HashMap<>();
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<ResourceOrderUploadDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<ResourceOrderUploadDTO>(ResourceOrderUploadDTO.class));
		ArrayList<ResourceOrderUploadDTO> dataList = importResult.getDataList();
		ArrayList<ResourceOrderUploadDTO> list = new ArrayList<>();
		ImportResultDto<ResourceOrderUploadDTO> importResultList = new ImportResultDto<>();
		TtVsUploadCommonResourcePO.deleteAll();
		for (ResourceOrderUploadDTO rowDto : dataList) {
			// 只有全部是成功的情况下，才执行数据库保存
			String errorMsg = rowDto.getErrorMsg();
			if (errorMsg == null) {
				resourceImportService.insertTmp(rowDto, orderType);
			} else {
				throw new ServiceBizException(errorMsg);
			}
		}
		// List<Map> TmSuPO222 = TtVsUploadCommonResourcePO.findAll();
		List<Map<String, Object>> ll = new ArrayList<>();

		List<Map> list1 = resourceImportService.importShow(orderType);
		List<Map<String, Object>> dto = resourceImportService.checkData(orderType);

		if (dto != null) {
			resultMap.put("ERROR", dto);

		} else {
			resultMap.put("map", list1);
		}
		return resultMap;
	}

	/**
	 * 资源上传模版下载
	 * 
	 * @author 廉兴鲁
	 * @date
	 * 
	 */
	@Autowired
	private SystemParamService paramService;

	@RequestMapping(value = "/import/{type}", method = RequestMethod.GET)
	@ResponseBody
	public void resourceImportM(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 资源上传模版下载===============");
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
	 * 
	 * @Description: 临时表数据回显 queryParam @param
	 */
	@RequestMapping(value = "/importShow/{orderType}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> importShow(@PathVariable("orderType") String orderType) {
		logger.info("===========资源上传（查询待插入数据） ===============");
		// 确认后查询待插入的数据
		List<Map> list = resourceImportService.importShow(orderType);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
		}
		return list;

	}

	/**
	 * 导入
	 */
	@RequestMapping(value = "/importTabled", method = RequestMethod.GET)
	public void ImportTableAppProce() {
		logger.info("===========资源上传（插入数据） ===============");
		resourceImportService.importTableAppProce();
	}

}
