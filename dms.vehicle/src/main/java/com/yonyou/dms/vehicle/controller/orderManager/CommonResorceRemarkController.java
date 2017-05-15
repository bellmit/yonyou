package com.yonyou.dms.vehicle.controller.orderManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.CommonResorceRemarkDTO;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.RemarkImpDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.importDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TmpCommonResourceRemarkPO;
import com.yonyou.dms.vehicle.service.orderManage.CommonResorceRemarkService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/commonResorceRemark")
@ResponseBody
public class CommonResorceRemarkController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(CommonResorceRemarkController.class);
	@Autowired
	private CommonResorceRemarkService commonResorceRemarkService;

	@RequestMapping(value = "/commonResorceRemarkInit", method = RequestMethod.GET)
	public PageInfoDto commonResorceRemarkInt(@RequestParam Map<String, String> queryParam) {
		logger.info("============车辆备注初始化===============");
		PageInfoDto pageInfoDto = commonResorceRemarkService.CommonResorceRemarkInt(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/resourceSupport", method = RequestMethod.PUT)
	public ResponseEntity<CommonResorceRemarkDTO> resourceSupport(@RequestBody @Valid CommonResorceRemarkDTO Dto) {
		logger.info("=============额外支持（设定）==============");

		commonResorceRemarkService.resourceSupport(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/resourceNOSupport", method = RequestMethod.PUT)
	public ResponseEntity<CommonResorceRemarkDTO> resourceNOSupport(@RequestBody @Valid CommonResorceRemarkDTO Dto) {
		logger.info("=============额外支持（取消）==============");

		commonResorceRemarkService.resourceNoSupport(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/resourceUnlock", method = RequestMethod.PUT)
	public ResponseEntity<CommonResorceRemarkDTO> resourceUnlock(@RequestBody @Valid CommonResorceRemarkDTO Dto) {
		logger.info("=============解锁==============");

		commonResorceRemarkService.resourceUnlock(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/resourceLock", method = RequestMethod.PUT)
	public ResponseEntity<CommonResorceRemarkDTO> resourceLock(@RequestBody @Valid CommonResorceRemarkDTO Dto) {
		logger.info("=============锁定==============");

		commonResorceRemarkService.resourceLock(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/resourceAllotRemark", method = RequestMethod.PUT)
	public ResponseEntity<CommonResorceRemarkDTO> resourceAllotRemark(@RequestBody @Valid CommonResorceRemarkDTO Dto) {
		logger.info("=============分配备注==============");

		commonResorceRemarkService.resourceAllotRemark(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============资源备注下载下载===============");
		commonResorceRemarkService.download(queryParam, response, request);

	}

	/**
	 * 上传文件 RemarkImpDto importRemark
	 * 
	 * @throws Exception
	 **/
	@Autowired
	private ExcelRead<RemarkImpDto> excelReadService;

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<RemarkImpDto> resourceorder(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, importDto importDto, UriComponentsBuilder uriCB)
			throws Exception {
		logger.info("============车辆备注设定导入===============");
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<RemarkImpDto> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<RemarkImpDto>(RemarkImpDto.class));
		ArrayList<RemarkImpDto> dataList = importResult.getDataList();
		ArrayList<RemarkImpDto> list = new ArrayList<>();
		ImportResultDto<RemarkImpDto> importResultList = new ImportResultDto<>();
		TmpCommonResourceRemarkPO.deleteAll();
		for (RemarkImpDto rowDto : dataList) {
			// 只有全部是成功的情况下，才执行数据库保存
			commonResorceRemarkService.insertTmpCommonResourceRemark(rowDto);
		}
		ArrayList<RemarkImpDto> dto = commonResorceRemarkService.checkData();
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
	 * @Description: 临时表数据回显 queryParam @param
	 */
	@RequestMapping(value = "/importShow", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> importShow(@RequestParam Map<String, String> queryParam) {
		logger.info("===========车辆备注设定---临时表数据回显 ===============");
		// 确认后查询待插入的数据
		List<Map> list = commonResorceRemarkService.importShowCon(queryParam);
		for (int i = 0; i < list.size(); i++) {
			Map map = list.get(i);
		}
		return list;
	}

	/**
	 * 导入
	 */
	@RequestMapping(value = "/importSave", method = RequestMethod.GET)
	public ResponseEntity<RemarkImpDto> ImportTableAppProce(UriComponentsBuilder uriCB) {
		logger.info("===========资源分配导入 ===============");
		commonResorceRemarkService.importSave();
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/commonResorceRemark/importSave").buildAndExpand(0).toUriString());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

	@Autowired
	private SystemParamService paramService;

	@RequestMapping(value = "/import/{type}", method = RequestMethod.GET)
	public void importdd(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 资源分配（下载导入模版）===============");
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
