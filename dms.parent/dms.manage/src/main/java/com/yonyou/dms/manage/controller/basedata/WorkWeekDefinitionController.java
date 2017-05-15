package com.yonyou.dms.manage.controller.basedata;

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
import javax.xml.transform.ErrorListener;

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
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.TmWorkWeekImportDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.WorkWeekDTO;
import com.yonyou.dms.manage.service.basedata.workWeek.WorkWeekDefinitionService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 工作周定义
 * @author luoyang
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/workWeek")
public class WorkWeekDefinitionController extends BaseController {
	
	@Autowired
	private WorkWeekDefinitionService  service;
	@Autowired
	ExcelGenerator excelService;
    @Autowired
    SystemParamService paramService;
	@Autowired
    private ExcelRead<TmWorkWeekImportDTO>  excelReadService;
	
	private Logger logger = LoggerFactory.getLogger(WorkWeekDefinitionController.class);
	
	/**
	 * 获取工作年
	 * @return
	 */
	@RequestMapping(value = "/getYear",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYear(){
		logger.info("===================获取工作年=================");
		return service.getYear();
		
	}
	
	/**
	 * 获取新增和修改工作年
	 * @return
	 */
	@RequestMapping(value = "/getYearForAdd",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearForAdd(){
		logger.info("===================获取新增和修改工作年=================");
		List<Map> list = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        String formatDate = sdf.format(date);
        Integer nowYear = Integer.parseInt(formatDate);
        for(int i = 0;i <= 3; i++){
        	Map<String,Integer> map = new HashMap<String,Integer>();
        	map.put("work_year", nowYear+i);
        	list.add(map);
        }
        return list;
	}
	
	/**
	 * 获取工作月
	 * @return
	 */
	@RequestMapping(value = "/getMonth",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getMonth(){
		logger.info("===================获取工作月=================");
		return service.getMonth();
		
	}
	
	/**
	 * 获取工作周
	 * @param workYear
	 * @param workMonth
	 * @return
	 */
	@RequestMapping(value = "/getWeek/{workYear}/{workMonth}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getWeek(@PathVariable String workYear,@PathVariable String workMonth){
		logger.info("===================获取工作周=================");
		return service.getWeek(workYear,workMonth);		
	}
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/search",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String,String> param){
		logger.info("===================工作周定义首页查询=================");
		PageInfoDto dto = service.search(param);
		return dto;
	}
	
	/**
	 * 工作周定义新增
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<WorkWeekDTO> add(@RequestBody WorkWeekDTO dto,UriComponentsBuilder uriCB){
		logger.info("===================工作周新增=================");
		service.addOrUpdate(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/workWeek/add").buildAndExpand().toUriString());
        return new ResponseEntity<WorkWeekDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 修改数据回显
	 * @param weekId
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/{weekId}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getWeekById(@PathVariable Long weekId) throws ParseException{
		logger.info("===================工作周修改数据回显=================");
		return service.findById(weekId);
	}
	
	/**
	 * 修改
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/edit",method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<WorkWeekDTO> edit(@RequestBody WorkWeekDTO dto,UriComponentsBuilder uriCB){
		logger.info("===================工作周修改=================");
		service.addOrUpdate(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/workWeek/add").buildAndExpand().toUriString());
        return new ResponseEntity<WorkWeekDTO>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 导出
	 * @param queryParam
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	public void exportQueryInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response){
		logger.info("==================工作周导出================");
		service.exportExcel(queryParam,request,response);
	}
	
	/**
	 * 导入模板下载
	 * @param type
	 * @param request
	 * @param response
	 */
    @RequestMapping(value = "/export/{type}",method = RequestMethod.GET)
    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response){
		logger.info("==================导入模板下载================");
    	BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //查询对应的参数
            BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
            Resource resource = new ClassPathResource(paramDto.getParamValue()); 
            //获取文件名称
            FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
            
            response.addHeader("Content-Length", "" + resource.getFile().length());
            
            bis = new BufferedInputStream(resource.getInputStream());
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载模板失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }
    
    @RequestMapping(value = "/importExcel",method = RequestMethod.POST)
	@ResponseBody
    public ArrayList<TmWorkWeekImportDTO> importExcel(@RequestParam(value = "file") MultipartFile importFile, UriComponentsBuilder uriCB) throws Exception{
    	 // 解析Excel 表格(如果需要进行回调)
		ImportResultDto<TmWorkWeekImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TmWorkWeekImportDTO>(TmWorkWeekImportDTO.class));
		if(!importResult.isSucess()){
			throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
		}
		ArrayList<TmWorkWeekImportDTO> dataList = importResult.getDataList();		
		ArrayList<TmWorkWeekImportDTO> list = new ArrayList<>();
		for(TmWorkWeekImportDTO rowDto : dataList){			
			// 只有全部是成功的情况下，才执行数据库保存
			logger.info("TmWorkWeekImportDTO:"+rowDto.toString());
			TmWorkWeekImportDTO dto = service.validatorWorkData(rowDto);
			if(dto != null){
				list.add(dto);
			}
		}
		if(list != null && !list.isEmpty()){
			throw new ServiceBizException("导入出错,请见错误列表", list);
		}
		return null;
    }

}
