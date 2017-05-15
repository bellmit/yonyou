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
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallActivityImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtRecallServiceDcsDTO;
import com.yonyou.dms.vehicle.service.activityRecallManage.RecallActivityBuildService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年4月10日
*/
@SuppressWarnings({ "rawtypes" })

@Controller
@TxnConn
@RequestMapping("/recallActivityBuild")
public class RecallActivityBuildController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(RecallActivityBuildController.class);
	
	@Autowired
	private SystemParamService paramService;
	
	@Autowired
	private ExcelRead<RecallActivityImportDTO> excelReadService;
	
	@Autowired
	private RecallActivityBuildService rabService;
	
	
	/**
	 * 召回活动建立 主页面查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/recallQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto recallActivityBuildInitQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回活动建立  主页面查询01==============");
    	PageInfoDto pageInfoDto = rabService.getRecallInitQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
	
	/**
	 * 召回活动建立 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/recallQueryDownload", method = RequestMethod.GET)
	public void recallActivityBuildInitDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回活动建立  主页面下载  01===============");
    	rabService.getRecallInitQueryDownload(queryParam, request, response);
	}
	
	
    /**
     * 召回活动建立 主页面新增    
     * @return
     */
    @RequestMapping(value="/recallAddSave",method = RequestMethod.POST)
   
    public ResponseEntity<Map> recallActivityAddSave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid TtRecallServiceDcsDTO trsdDto,
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回活动建立 主页面新增01===============");
    	 Map map =	 rabService.recallActivityAddSave(trsdDto);
    	 MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/recallActivityBuild/recallAddSave").buildAndExpand().toUriString());
         return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);    
    }
    
    /**
     * 召回活动建立 删除
     * @param recallId
     * @param uriCB
     */
    @RequestMapping(value = "/deleteRecall/{RECALL_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecallService(@PathVariable("RECALL_ID") Long recallId, UriComponentsBuilder uriCB) {
    	logger.info("============召回活动建立 删除01==============");
    	rabService.deleteRecallService(recallId);
    }
    /**
     * 召回活动建立 发布 
     * @param recallId
     * @param uriCB
     */
    @RequestMapping(value = "/sendRecall/{RECALL_ID}/{RECALL_STATUS}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendRecallService(@PathVariable("RECALL_ID") Long recallId, 
    		@PathVariable("RECALL_STATUS") Integer recallStatus, 
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回活动建立 发布01==============");
    	rabService.sendRecallService(recallId, recallStatus);
    	
    }
    /**
     * 召回活动建立 取消发布
     * @param recallId
     * @param uriCB
     */
    @RequestMapping(value = "/sendRecallCancle/{RECALL_ID}/{RECALL_STATUS}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendRecallServiceCancle(@PathVariable("RECALL_ID") Long recallId,
    		@PathVariable("RECALL_STATUS") Integer recallStatus,
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回活动建立 取消发布01==============");
    	rabService.sendRecallService(recallId, recallStatus);
    }
    
    /**
     * 召回活动建立 修改查询
     * @param recallId
     * @return
     */
    @RequestMapping(value = "/recallEdit/{RECALL_ID}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> activityDetailQuery(@PathVariable(value = "RECALL_ID") Long recallId) {
    	logger.info("============召回活动建立修改查询 01===============");
    	Map map = rabService.queryEditRecallServiceMap(recallId);
        return map;
    }
    
    @RequestMapping(value="/recallEditSave",method = RequestMethod.POST)
    
    public ResponseEntity<TtRecallServiceDcsDTO> recallActivityEditSave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid TtRecallServiceDcsDTO trsdDto,
    		UriComponentsBuilder uriCB) {
    	logger.info("============召回活动建立 修改保存01===============");
    	 rabService.editRecallServiceSave(trsdDto);
    	 MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/recallActivityBuild/recallEditSave").buildAndExpand().toUriString());
         return new ResponseEntity<TtRecallServiceDcsDTO>(headers, HttpStatus.CREATED);    
    }
    
    /**
	 * 召回活动建立 经销商查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/recallDealer/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto dealerRecallQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回活动建立  经销商查询01==============");
    	PageInfoDto pageInfoDto = rabService.dealerRecallQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
	/**
	 * 召回活动建立 经销商新增查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/recallDealer/addQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto dealerRecallAddQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============召回活动建立  经销商 新增查询01==============");
    	PageInfoDto pageInfoDto = rabService.dealerRecallAddQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
	
	/**
     * 召回活动建立  经销商新增确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/recallDealer/addSave",method = RequestMethod.PUT)
    @ResponseBody
    public void dealerRecallAddSave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid RecallServiceActDTO rsDto) {
    	logger.info("============  经销商新增确定 01==============");
    	rabService.dealerRecallAddSave(rsDto);
    } 
	
    /**
     * 召回活动建立  经销商删除
     * @param activityId
     * @return
     */
    @RequestMapping(value="/recallDealer/delete",method = RequestMethod.PUT)
    @ResponseBody
    public void dealerRecallDelete(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid RecallServiceActDTO rsDto) {
    	logger.info("============  经销商删除 01==============");
    	rabService.dealerRecallDelete(rsDto);
    } 
    
    /**
	 * 召回活动建立 VIN下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/vinDownload/{RECALL_ID}", method = RequestMethod.GET)
	public void recallActivityVinDownLoad(@RequestParam Map<String, String> queryParam, 
			@PathVariable(value = "RECALL_ID") Long recallId,
			HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回活动建立  VIN下载  01===============");
    	rabService.recallActivityVinDownLoad(recallId, request, response);
	}
    /**
	 * 召回活动建立 活动项目下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/projectDownload/{RECALL_ID}", method = RequestMethod.GET)
	public void recallActivityProjectDownLoad(@RequestParam Map<String, String> queryParam,
			@PathVariable(value = "RECALL_ID") Long recallId,
			HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回活动建立  活动项目下载  01===============");
    	rabService.recallActivityProjectDownLoad(recallId, request, response);
	}
    /**
	 * 召回活动建立 经销商下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/dealerDownLoad/{RECALL_ID}", method = RequestMethod.GET)
	public void recallActivityDealerDownLoad(@RequestParam Map<String, String> queryParam, 
			@PathVariable(value = "RECALL_ID") Long recallId,
			HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============召回活动建立  经销商下载  01===============");
    	rabService.recallActivityDealerDownLoad(recallId, request, response);
	}
    
    
    
    /**
     * 导入模板下载
     * @param type
     * @param request
     * @param response
     */
    @RequestMapping(value = "/downloadTemple/{type}", method = RequestMethod.GET)
	public void recallActivityDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 召回活动建立（下载导入模版）===============");
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
 	public List<RecallActivityImportDTO>  recallActivityExcelOperate(@RequestParam final Map<String, String> queryParam,
 			@RequestParam(value = "file") MultipartFile importFile, RecallActivityImportDTO raiDto,
 			UriComponentsBuilder uriCB) throws Exception {
 		logger.info("============召回活动建立(导入临时表)===============");
 		// 解析Excel 表格(如果需要进行回调)
 		ImportResultDto<RecallActivityImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
 				new AbstractExcelReadCallBack<RecallActivityImportDTO>(RecallActivityImportDTO.class));
 		ArrayList<RecallActivityImportDTO> dataList = importResult.getDataList();
 		
 		ArrayList<RecallActivityImportDTO> list = new ArrayList<>();
 		//清空临时表中的数据
 		rabService.deleteTmpRecallVehicleDcs();
 		
 		for (RecallActivityImportDTO rowDto : dataList) {
 			rowDto.setRecallNo(queryParam.get("recallNo").toString());
 			rowDto.setInportType(Integer.parseInt(queryParam.get("inportType").toString()));
 			// 只有全部是成功的情况下，才执行数据库保存
 			rabService.saveTmpRecallVehicleDcs(rowDto);
 		}
 		List<RecallActivityImportDTO>  dto = rabService.checkData(Integer.parseInt(queryParam.get("inportType").toString()));
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
		logger.info("============召回活动建立（查询待插入数据） ===============");
		// 回显确认查询待插入的数据		
		List<Map> list = rabService.queryTmpRecallVehicleDcsList(queryParam);
		return list;
	}
    /**
     * 导入业务表
     * @param uriCB
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
  	public ResponseEntity<RecallActivityImportDTO> importExcel(UriComponentsBuilder uriCB,
  			@RequestParam Map<String, String> queryParam) {
  		logger.info("=============召回活动建立（导入业务表）==============");
  		//插入业务表 删除临时表
  		rabService.saveAndDeleteData(queryParam);
  				
  		return new ResponseEntity<>(HttpStatus.CREATED);
  	}
    
}












