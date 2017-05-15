package com.yonyou.dms.vehicle.controller.activityRecallManage;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpRecallVehicleDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TmpRecallVehicleDcsPO;
import com.yonyou.dms.vehicle.service.activityRecallManage.RecallVehicleManagerService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* @author liujiming
* @date 2017年4月13日
*/
@SuppressWarnings({ "rawtypes" })

@Controller
@TxnConn
@RequestMapping("/recallVehicleManage")
public class RecallVehicleManagerController {
	
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(RecallVehicleManagerController.class);
	
	@Autowired
	private SystemParamService paramService;
	@Autowired
	private ExcelRead<TmpRecallVehicleDcsDTO> excelReadService;

	@Autowired
	private RecallVehicleManagerService rvmService;
	
	
	
	/**
	 * 召回车辆管理 查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/recallVehicleQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallVehicleManagerQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆管理02==============");
    	PageInfoDto pageInfoDto = rvmService.recallVehicleManagerQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	/**
	 * 召回车辆管理 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/recallVehicleDownload", method = RequestMethod.GET)
    @ResponseBody
    public void recallVehicleManagerDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回车辆管理下载  02===============");
    	rvmService.getRecallInitQueryDownload(queryParam, request, response);
	}
    
    
    @RequestMapping(value = "/recallVehicleDetail/{ID}", method = RequestMethod.GET)
    @ResponseBody
    public Map recallDutyDealerInit(@PathVariable("ID") Long id,
			@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆管理 修改/明细回显  02===============");
    	Map map = rvmService.recallDutyDealerInit(id);
    	return map;
	}
    /**
     * 经销商 修改
     * @param id
     * @param rsDto
     * @param queryParam
     */
    @RequestMapping(value = "/recallVehicle/updateDealer/{ID}", method = RequestMethod.POST)
    @ResponseBody
    public void recallDutyDealerUpdate(@PathVariable("ID") Long id,
    		@RequestBody @Valid RecallServiceActDTO rsDto,
			@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆管理 修改保存  02===============");
    	rvmService.recallDealerUpdate(rsDto, id);
    	
	}
    /**
     * 经销商 批量修改
     * @param id
     * @param rsDto
     * @param queryParam
     */
    @RequestMapping(value = "/recallVehicle/updateDealersSave", method = RequestMethod.POST)
    @ResponseBody
    public void recallDutyDealersUpdate(@RequestBody @Valid RecallServiceActDTO rsDto,
			@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回车辆管理 批量修改保存  02===============");
    	rvmService.recallDealersUpdate(rsDto);	
	}
    /**
     * 导入模板下载
     * @param type
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadTemple/{type}", method = RequestMethod.GET)
	public void recallVehicleDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 召回车辆管理（下载导入模版）===============");
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
     * 导入临时表并校验
     * @param queryParam
     * @param importFile
     * @param tmpRecallVehicleDcsDto
     * @param uriCB
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/excelOperate", method = RequestMethod.POST)
	@ResponseBody
	public List<TmpRecallVehicleDcsDTO>  recallVehicleExcelOperate(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, TmpRecallVehicleDcsDTO tmpRecallVehicleDcsDto,
			UriComponentsBuilder uriCB) throws Exception {
		logger.info("============召回车辆管理(导入临时表)===============");
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<TmpRecallVehicleDcsDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TmpRecallVehicleDcsDTO>(TmpRecallVehicleDcsDTO.class));
		ArrayList<TmpRecallVehicleDcsDTO> dataList = importResult.getDataList();
		if (dataList.size()>10000) {
			throw new ServiceBizException("导入出错,单次上传文件至多10000行");
		}
		ArrayList<TmpRecallVehicleDcsDTO> list = new ArrayList<>();
		//根据用户ID清空临时表中的数据
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpRecallVehicleDcsPO.delete(" CREATE_BY = ? ", loginInfo.getUserId());
		for (TmpRecallVehicleDcsDTO rowDto : dataList) {			
			// 只有全部是成功的情况下，才执行数据库保存
			rvmService.insertTmpRecallVehicleDcs(rowDto);
		}
		List<TmpRecallVehicleDcsDTO>  dto = rvmService.checkData();
		if (dto != null) {
			list.addAll(dto);
		}
		if (list != null && !list.isEmpty()) {
			throw new ServiceBizException("导入出错,请见错误列表", list);
		}
		
		return null;
	}
    
    @RequestMapping(value = "/selectData", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> selectTmpRecallVehicleDcs(
			@RequestParam Map<String, String> queryParam) {
		logger.info("============车辆召回管理（查询待插入数据） ===============");
		// 确认后查询待插入的数据
		
		List<Map> list = rvmService.findTmpRecallVehicleDcsList(queryParam);
		return list;
	}

    @RequestMapping(value = "/recallVehicle/importExcel", method = RequestMethod.POST)
	public ResponseEntity<TmpRecallVehicleDcsDTO> importExcel(UriComponentsBuilder uriCB,
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============车辆召回管理（导入业务表）==============");
		//插入业务表 删除临时表
		rvmService.importSaveAndDelete();
				
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}









