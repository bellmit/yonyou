package com.yonyou.dms.vehicle.controller.activityManage;

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
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpWrActivityVehicleDcsImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtWrActivityDTO;
import com.yonyou.dms.vehicle.service.activityManage.ActivityMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月23日
*/
@SuppressWarnings({ "rawtypes" })

@Controller
@TxnConn
@RequestMapping("/activityMaintain")
public class ActivityMaintainController {
	
	

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(ActivityMaintainController.class);
	
	@Autowired
	private SystemParamService paramService;
	
	@Autowired
	private ExcelRead<TmpWrActivityVehicleDcsImportDTO> excelReadService;
	
	@Autowired
	private ActivityMaintainService  actMatService;
	
	
	/**
     *服务活动建立 查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/activityQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityInitQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动建立 01==============");
    	PageInfoDto pageInfoDto = actMatService.getActivityInitQuery(queryParam);   	
        return pageInfoDto;
        
        
    }
    
    /**
     * 服务活动建立 主页面新增    
     * @return
     */
    @RequestMapping(value="/activityAddSave",method = RequestMethod.POST)
   
    public ResponseEntity<Map> activityAddSave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid TtWrActivityDTO twaDto,
    		UriComponentsBuilder uriCB) {
    	logger.info("============服务活动建立 主页面新增01===============");

    	
    	 
    	 Map map = 	actMatService.activityAddSave(twaDto);
    	 MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/activityMaintain/activityAddSave").buildAndExpand().toUriString());
         return new ResponseEntity<Map>(map,headers, HttpStatus.CREATED);    
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/{ACTIVITY_ID}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> activityDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	logger.info("============服务活动建立 明细（文本框）01===============");
    	Map map = actMatService.activityDetailQuery(activityId);
        return map;
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryLabour/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityLabourDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	//System.out.println("queryInitController");
    	logger.info("============服务活动建立 明细（工时）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityLabourDetailQuery(activityId);   	
        return pageInfoDto;             
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryPart/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityPartDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	//System.out.println("queryInitController");
    	logger.info("============服务活动建立 明细（配件）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityPartDetailQuery(activityId);   	
        return pageInfoDto;             
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryOther/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityOtherDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	//System.out.println("queryInitController");
    	logger.info("============服务活动建立 明细（其他项目）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityOtherDetailQuery(activityId);   	
        return pageInfoDto;   
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryVehicle/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityVehicleDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	//System.out.println("queryInitController");
    	logger.info("============服务活动建立 明细（车型）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityVehicleDetailQuery(activityId);   	
        return pageInfoDto;    
    }
    
    
    /**
     * 服务活动建立 明细
     * @param activityId
     * @return
     */
    @RequestMapping(value="/activityQueryAge/{ACTIVITY_ID}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityAgeDetailQuery(@PathVariable(value = "ACTIVITY_ID") Long activityId) {
    	//System.out.println("queryInitController");
    	logger.info("============服务活动建立 明细（车龄）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityAgeDetailQuery(activityId);   	
        return pageInfoDto;    
    }
    
    /**
     * 服务活动建立 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/activityDelete/{ACTIVITY_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityDelete(@PathVariable("ACTIVITY_ID") Long activityId, UriComponentsBuilder uriCB) {
    	logger.info("============服务活动建立 删除01==============");
    	actMatService.deleteByActivityId(activityId);
    }
    
    /**
     * 服务活动建立 主页面修改   
     * @return
     */
    @RequestMapping(value="/activityModifySave",method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrActivityDTO> activityModifySave(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid TtWrActivityDTO twaDto,
    		UriComponentsBuilder uriCB) {
    	logger.info("============服务活动建立 主页面 修改01===============");
    	
    	 actMatService.activityModifySave(twaDto);
    	 MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/activityMaintain/activityEdit").buildAndExpand().toUriString());
         return new ResponseEntity<TtWrActivityDTO>(headers, HttpStatus.CREATED);    
    }
    
    
    /**
     * 服务活动建立 修改（车型）查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editVehicle",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityEditVehicle(@RequestParam Map<String, String> queryParam ) {
    	logger.info("============服务活动建立 修改（车型）01==============");
    	PageInfoDto pageInfoDto = actMatService.activityModifyVehicle(queryParam);   	
        return pageInfoDto;    
    } 
    
    /**
     * 服务活动建立 修改（车型）新增查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editVehicle/modelQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityCarModelQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动建立 修改（车型 新增页面）01==============");
    	PageInfoDto pageInfoDto = actMatService.getActivityCarModelQuery(queryParam);   	
        return pageInfoDto;    
    } 
    
    /**
     * 服务活动建立 修改（车型）新增 确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editVehicle/modelSave",method = RequestMethod.PUT)
    @ResponseBody
    public void saveActivityCarModel(@RequestParam Map<String, String> queryParam,@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============服务活动建立 修改（车型 新增页面 确定）01==============");
    	actMatService.activityAddModelSave( amDto); 
    } 
    /**
     * 服务活动建立 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/activityModelDelete/{ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityCarModelDelete(@PathVariable("ID") Long id, UriComponentsBuilder uriCB) {
    	logger.info("============服务活动建立  车型删除01==============");
    	actMatService.deleteCarModelById(id);
    }
    
    
    /**
     * 服务活动建立 修改（车龄）查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editAge",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityCarAgeQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============服务活动建立  查询（车龄）01==============");
    	PageInfoDto pageInfoDto = actMatService.getActivityCarAgeQuery(queryParam);   	
        return pageInfoDto;    
    } 
    
    /**
     * 服务活动建立 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/activityAgeDelete/{ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityCarAgeDelete(@PathVariable("ID") Long id, UriComponentsBuilder uriCB) {
    	logger.info("============服务活动建立  车龄删除01==============");
    	actMatService.deleteCarAgeById(id);
    }
    
    @RequestMapping(value="/carAgeAdd",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ActivityManageDTO> saveActivityCarAge(@RequestParam Map<String, String> queryParam,
    		@RequestBody @Valid ActivityManageDTO amDto,
    		UriComponentsBuilder uriCB ) {
    	logger.info("============车龄 新增保存 01===============");
    		actMatService.saveActivityCarAge(amDto);	
         MultiValueMap<String, String> headers = new HttpHeaders();
         headers.set("Location", uriCB.path("/activityMaintain/carAgeAdd").buildAndExpand().toUriString());
         return new ResponseEntity<ActivityManageDTO>(headers, HttpStatus.CREATED);


    }
    
    /**
     *车系代码下拉选 下拉选 查询	
     * @param queryParam 查询条件
     * @return tenantMapping 查询结果
     */
    @RequestMapping(value="/groupCode/selectList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> groupCodeList(@RequestParam Map<String, String> queryParam){
    	logger.info("=====车系代码加载=====");
    	List<Map> tenantMapping =  actMatService.getGroupCodeListQuery(queryParam);
    	return tenantMapping;
    }
 
    /**
     *   工时维护  查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/labourQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangeLabourQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动页面   工时  查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangeLabourQuery(queryParam);   	
        return pageInfoDto;    
    } 
    /**
     * 工时维护 确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/labourSave",method = RequestMethod.PUT)
    @ResponseBody
    public void activityAddLabourSave(@RequestParam Map<String, String> queryParam,@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============  工时维护 确定01==============");
    	actMatService.activityAddLabourSave(amDto);
    } 
    
    /**
     *  活动项目 工时查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/labourList",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangeLabour(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动项目   工时  查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangeLabourList(queryParam);   	
        return pageInfoDto;    
    } 
    
    /**
     * 服务活动建立 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/labourDelete/{DETAIL_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityLabelDelete(@PathVariable("DETAIL_ID") Long detailId, UriComponentsBuilder uriCB) {
    	logger.info("============ 项目活动  工时  删除01==============");
    	actMatService.deleteLabelDeleteByDetailId(detailId);
    }
    
    
    /**
     *  活动项目 配件查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/partList",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangePartList(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动项目   配件  查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangePartList(queryParam);   	
        return pageInfoDto;    
    } 
    /**
     *   配件维护  查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/partQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangePartQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动页面   配件维护  查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangePartQuery(queryParam);   	
        return pageInfoDto;    
    } 
 
    /**
     * 服务活动建立 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/partDelete/{DETAIL_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityPartDelete(@PathVariable("DETAIL_ID") Long detailId, UriComponentsBuilder uriCB) {
    	logger.info("============ 项目活动  配件  删除01==============");
    	actMatService.deletePartByDetailId(detailId);
    }
    
    
    /**
     * 配件维护 确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/partSave",method = RequestMethod.PUT)
    @ResponseBody
    public void activityAddPartSave(@RequestParam Map<String, String> queryParam,@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============  配件维护 确定01==============");
    	actMatService.activityAddPartSave(amDto);
    } 
    
    
    /**
     *  活动项目 其他项目 查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/otherList",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangeOtherList(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动项目   其他项目   查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangeOtherList(queryParam);   	
        return pageInfoDto;    
    } 
    /**
     *  其他项目维护  查询
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/otherQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto activityRangeOtherQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============活动页面   其他项目 维护  查询01==============");
    	PageInfoDto pageInfoDto = actMatService.getRangeOtherQuery(queryParam);   	
        return pageInfoDto;    
    } 
 
    /**
     * 其他项目 删除
     * @param activityId
     * @return
     */    
    @RequestMapping(value = "/otherDelete/{OTHER_REL_ID}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activityOtherDelete(@PathVariable("OTHER_REL_ID") Long otherRelId, UriComponentsBuilder uriCB) {
    	logger.info("============ 项目活动  其他项目  删除01==============");
    	actMatService.deleteOtherByDetailId(otherRelId);
    }
    
    
    /**
     * 其他项目维护 确定
     * @param activityId
     * @return
     */
    @RequestMapping(value="/editRange/otherSave",method = RequestMethod.PUT)
    @ResponseBody
    public void activityAddOtherSave(@RequestParam Map<String, String> queryParam,@RequestBody @Valid ActivityManageDTO amDto) {
    	logger.info("============  其他项目维护 确定01==============");
    	actMatService.activityAddOtherSave(amDto);
    } 
    
    @RequestMapping(value = "/downloadTemple/{type}", method = RequestMethod.GET)
	public void recallVehicleDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 服务活动管理管理（下载导入模版）===============");
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
    
    
    @RequestMapping(value = "/excelOperate", method = RequestMethod.POST)
	@ResponseBody
	public List<TmpWrActivityVehicleDcsImportDTO>  recallVehicleExcelOperate(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, TmpWrActivityVehicleDcsImportDTO twavdDto,
			UriComponentsBuilder uriCB) throws Exception {
		logger.info("============服务活动管理(导入临时表)===============");
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<TmpWrActivityVehicleDcsImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TmpWrActivityVehicleDcsImportDTO>(TmpWrActivityVehicleDcsImportDTO.class));
		ArrayList<TmpWrActivityVehicleDcsImportDTO> dataList = importResult.getDataList();
		
		ArrayList<TmpWrActivityVehicleDcsImportDTO> list = new ArrayList<>();
				
		//清空临时表中的数据
		actMatService.deleteTmpWrActivityVehicleDcs();
		
		
		for (TmpWrActivityVehicleDcsImportDTO rowDto : dataList) {			
			actMatService.saveTmpWrActivityVehicleDcs(rowDto);
			
		}
		List<TmpWrActivityVehicleDcsImportDTO>  dto = actMatService.checkData(queryParam.get("activityId3").toString());
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
		logger.info("============服务活动管理（查询待插入数据） ===============");
		// 确认后查询待插入的数据		
		List<Map> map = actMatService.queryTmpWrActivityVehicleDcsList(queryParam);
		return map;
	}
    /**
     * 导入业务表
     * @param uriCB
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public ResponseEntity<TmpWrActivityVehicleDcsImportDTO> importExcel(UriComponentsBuilder uriCB,
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============服务活动管理（导入业务表）==============");
		//插入业务表 删除临时表
		actMatService.saveAndDeleteData(queryParam);			
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
