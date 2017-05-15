package com.yonyou.dms.vehicle.controller.k4Order;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.DealerRebateImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.TmpVsRebateImpDTO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TmpVsRebateImpPO;
import com.yonyou.dms.vehicle.service.k4Order.DealerRebateImportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author liujiming
 * @date 2017年3月13日
 */
@SuppressWarnings({ "rawtypes" })
@Controller
@TxnConn
@RequestMapping("/dealerRebateImport")
public class DealerRebateImportConterller {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerRebateImportConterller.class);

	@Autowired
	private SystemParamService paramService;

	@Autowired
	private ExcelRead<TmpVsRebateImpDTO> excelReadService;

	@Autowired
	private DealerRebateImportService dlrRbtImportService;

	/**
	 * 
	 * @Description: 经销返利上传 导入业务表 @param userDto @param uriCB @return
	 *               设定文件 @return ResponseEntity<TmpVsRebateImpDTO> 返回类型 @throws
	 */
	@RequestMapping(value = "/dealerRebate/importExcel", method = RequestMethod.POST)
	public ResponseEntity<TmpVsRebateImpDTO> importExcel(UriComponentsBuilder uriCB,
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============经销商返利上传（导入业务表）==============");
		dlrRbtImportService.insertToRebate();
		dlrRbtImportService.deleteTmpVsRebateImp();
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	/**
	 * 
	 * @Title: selectTmpVsRebateType @Description: 上传（查询待插入数据） @param
	 *         reabteType @param queryParam @param 设定文件 @return PageInfoDto
	 *         返回类型 @throws
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/selectTmpVsRebateImp/{rebateType}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> selectTmpVsRebateType(@PathVariable("rebateType") String rebateType,
			@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商返利上传（查询待插入数据） ===============");
		// 确认后查询待插入的数据
		queryParam.put("rebateType", rebateType);
		List<Map> list = dlrRbtImportService.dealerRebateImportQuery();
		return list;
	}

	/**
	 * 
	 * @Title: dealerRebateExcelOperate @Description: 经销商返利上传(导入临时表) @param
	 *         importFile @param dealerRebateImportDto @param uriCB @param
	 *         Exception 设定文件 @return List<TmpVsRebateImpDTO> 返回类型 @throws
	 */
	@RequestMapping(value = "/dealerRebateExcelOperate", method = RequestMethod.POST)
	@ResponseBody
	public List<TmpVsRebateImpDTO> dealerRebateExcelOperate(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, DealerRebateImportDTO dealerRebateImportDto,
			UriComponentsBuilder uriCB) throws Exception {
		logger.info("============经销商返利上传(导入临时表)===============");
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<TmpVsRebateImpDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TmpVsRebateImpDTO>(TmpVsRebateImpDTO.class));
		ArrayList<TmpVsRebateImpDTO> dataList = importResult.getDataList();
		ArrayList<TmpVsRebateImpDTO> list = new ArrayList<>();
		TmpVsRebateImpPO.deleteAll();
		for (TmpVsRebateImpDTO rowDto : dataList) {
			rowDto.setRebateType(Long.parseLong(queryParam.get("rebateType")));
			// 只有全部是成功的情况下，才执行数据库保存
			dlrRbtImportService.insertTmpVsRebateImp(rowDto);
		}
		List<TmpVsRebateImpDTO> dto = dlrRbtImportService.checkData();
		if (dto != null) {
			list.addAll(dto);
		}
		if (list != null && !list.isEmpty()) {
			throw new ServiceBizException("导入出错,请见错误列表", list);
		}
		return null;
	}

	/**
	 * 
	 * @Title: downloadTemple @Description:经销商返利上传（下载导入模版） @param @param
	 *         type @param @param request @param @param response 设定文件 @return
	 *         void 返回类型 @throws
	 */
	@RequestMapping(value = "/rebateDownloadTemple/{type}", method = RequestMethod.GET)
	public void rebateDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 经销商返利上传 （下载导入模版）===============");
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

}
