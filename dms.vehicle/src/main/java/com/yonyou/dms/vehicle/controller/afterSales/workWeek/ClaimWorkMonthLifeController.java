package com.yonyou.dms.vehicle.controller.afterSales.workWeek;

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
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallActivityImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.workWeek.TmpWrClaimmonthDTO;
import com.yonyou.dms.vehicle.service.afterSales.workWeek.ClaimWorkMonthLifeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import org.springframework.http.HttpStatus;
/**
 * 索赔工作越维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/claimWorkMonthLife")
public class ClaimWorkMonthLifeController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ClaimWorkMonthLifeService  claimWorkMonthLifeService;
	@Autowired
	private SystemParamService paramService;
	@Autowired
	private ExcelRead<TmpWrClaimmonthDTO> excelReadService;
	/**
	 * 下载导入模板
	 */
	@RequestMapping(value = "/downloadTemple/{type}", method = RequestMethod.GET)
	public void levelSetDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 索赔工作越维护（下载导入模版）===============");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 查询对应的参数
			BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
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
     * 导入临时表
     * @param queryParam
     * @param importFile
     * @param raiDto
     * @param uriCB
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excelOperate", method = RequestMethod.POST)
 	@ResponseBody
 	public List<TmpWrClaimmonthDTO>  recallActivityExcelOperate(@RequestParam final Map<String, String> queryParam,
 			@RequestParam(value = "file") MultipartFile importFile, RecallActivityImportDTO raiDto,
 			UriComponentsBuilder uriCB) throws Exception {
 		logger.info("============配件分组级别设定(导入临时表)===============");
 		// 解析Excel 表格(如果需要进行回调)
 		ImportResultDto<TmpWrClaimmonthDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
 				new AbstractExcelReadCallBack<TmpWrClaimmonthDTO>(TmpWrClaimmonthDTO.class));
 		ArrayList<TmpWrClaimmonthDTO> dataList = importResult.getDataList();
 		
 		ArrayList<TmpWrClaimmonthDTO> list = new ArrayList<>();
 		//清空临时表中的数据
 		claimWorkMonthLifeService.deleteTmpRecallVehicleDcs();
 		
 		for (TmpWrClaimmonthDTO rowDto : dataList) {
 			// 只有全部是成功的情况下，才执行数据库保存
 			claimWorkMonthLifeService.saveTmpRecallVehicleDcs(rowDto);
 		}
 		List<TmpWrClaimmonthDTO>  dto =claimWorkMonthLifeService.checkData();
 		if (dto != null) {
 			list.addAll(dto);
 		}
 		if (list != null && !list.isEmpty()) {
 			throw new ServiceBizException("导入出错,请见错误列表", list);
 		}
 		
 		return null;
 	}
	
	
	
	
	
    
    
/**
	 * 查询临时表数据
	 * @param queryParam
	 * @return
	 */
	  @RequestMapping(value = "/selectData", method = RequestMethod.GET)
		@ResponseBody
		public List<Map> selectTmpRecallVehicleDcs(
				@RequestParam Map<String, String> queryParam) {
			logger.info("============配件分组级别设定（查询待插入数据） ===============");
			// 确认后查询待插入的数据
			
			List<Map> list = claimWorkMonthLifeService.findTmpList();
			return list;
		}
	
	
	/**
	 * 导入业务表
	 * @param uriCB
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/levelSetVecile/importExcel", method = RequestMethod.POST)
	public ResponseEntity<TmpWrClaimmonthDTO> importExcel(UriComponentsBuilder uriCB,
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============配件分组级别设定（导入业务表）==============");
		//插入业务表 删除临时表
		claimWorkMonthLifeService.saveAndDeleteData(queryParam);
				
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
