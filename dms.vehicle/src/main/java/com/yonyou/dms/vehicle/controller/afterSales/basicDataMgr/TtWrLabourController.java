package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrLabourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrLabourPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.TtWrLabourService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/wrLabour")
public class TtWrLabourController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TtWrLabourService  ttWrLabourService;
	@Autowired
	private SystemParamService paramService;
	@Autowired
	private ExcelRead<TtWrLabourDTO> excelReadService;
	/**
	 * 查询所有车系集合
	 */
    @RequestMapping(value="/labourList",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBigOrg(@RequestParam Map<String, String> queryParams){
    	logger.info("=====查询所有车系集合=====");
    	List<Map> tenantMapping = ttWrLabourService.getlabour(queryParams);
        return tenantMapping;
    }
    /**
     * 索赔工时维护查询
     */
	@RequestMapping(value="/labourSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querylabour(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔工时维护查询=============");
        PageInfoDto pageInfoDto =ttWrLabourService.LabourQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 删除索赔工时维护
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 删除索赔工时维护=====");
    	ttWrLabourService.delete(id);
    }
    /**
     * 通过id修改索赔工时维护时的回显信息
     */
    @RequestMapping(value = "/getLabour/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	TtWrLabourPO ptPo=ttWrLabourService.getLabourById(id);
        return ptPo.toMap();
    }
    
    /**
     * 通过id进行修改索赔工时维护
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrLabourDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrLabourDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 通过id进行修改索赔工时维护=====");
    	ttWrLabourService.edit(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/wrLabour/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrLabourDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 新增索赔工时维护
     */
    @RequestMapping(value ="/addLabour",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrLabourDTO> addLabourt(@RequestBody TtWrLabourDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("===== 新增索赔工时维护=====");
    	ttWrLabourService.addLabour(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }
    
    
    
    
    
    
    
    /**
	 * 下载导入模板
	 */
	@RequestMapping(value = "/downloadTemple/{type}", method = RequestMethod.GET)
	public void levelSetDownloadTemple(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 索赔工时维护（下载导入模版）===============");
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
    @RequestMapping(value = "/excelOperate/{flag}", method = RequestMethod.POST)
 	@ResponseBody
 	public List<TtWrLabourDTO>  recallActivityExcelOperate(@RequestParam final Map<String, String> queryParam,
 			@RequestParam(value = "file") MultipartFile importFile, TtWrLabourDTO raiDto,@PathVariable(value = "flag") String flag,
 			UriComponentsBuilder uriCB) throws Exception {
 		logger.info("============索赔工时维护，数据校验===============");
 		// 解析Excel 表格(如果需要进行回调)
 		ImportResultDto<TtWrLabourDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
 				new AbstractExcelReadCallBack<TtWrLabourDTO>(TtWrLabourDTO.class));
 		ArrayList<TtWrLabourDTO> dataList = importResult.getDataList();
 		
 		ArrayList<TtWrLabourDTO> list = new ArrayList<>();
 		
 		for (TtWrLabourDTO rowDto : dataList) {
 			// 只有全部是成功的情况下，才执行数据库保存
 			List<TtWrLabourDTO>  dto =ttWrLabourService.checkData(rowDto,flag);
 			if (!dto.isEmpty()) {
	 			list.addAll(dto);
	 		}else{
	 			ttWrLabourService.insertTtWrLabourDcs(rowDto);//将数据导入到表中
	 		}
			if (list != null && !list.isEmpty()) {
	 			throw new ServiceBizException("导入出错,请见错误列表", list);
	 		}
 		}
 		return null;
 	}
    
    
    
    
    
    
    
    

}
