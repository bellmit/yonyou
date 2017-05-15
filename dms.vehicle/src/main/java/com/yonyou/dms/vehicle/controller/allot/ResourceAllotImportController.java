package com.yonyou.dms.vehicle.controller.allot;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmAllotStatusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmpUploadResourcePO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImport2Dto;
import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotImportDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.importDto;
import com.yonyou.dms.vehicle.service.allot.ResourceAllotPortService;
import com.yonyou.f4.mvc.annotation.TxnConn;
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@TxnConn
@RequestMapping("/resourceallotimport")
public class ResourceAllotImportController {
	@Autowired
	ResourceAllotPortService resourceallotportService;
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotImportController.class);

	/**
	 * 资源分配上传模版下载
	 * 
	 * @author 廉兴鲁
	 * @date
	 * 
	 */
	@Autowired
	private SystemParamService paramService;
	 
	@Autowired
	private ExcelGenerator      excelService;
	
	@Autowired
	private ExcelRead<ResourceAllotImportDto> excelReadService;
	
	@Autowired
	private ExcelRead<ResourceAllotImport2Dto> excelReadService2;

	@RequestMapping(value = "/import/{type}", method = RequestMethod.GET)
	@ResponseBody
	public void resourceImportM(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============ 资源分配上传模版下载===============");
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
	 * 资源分配上传
	 * @author 夏威
	 * @date 2017-02-10 ResourceOrderUploadDTO
	 * flag 1 错误提示 2、成功- 本周  3、成功-月初结转 4、错误- 本周  5、错误-月初结转
	 */

	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resourceorder(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, importDto importDto, UriComponentsBuilder uriCB)
			throws Exception {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Integer type = 1;
		logger.info("============资源分配上传===============");
		Map<String, Object> resultMap = new HashMap<>();
		try {
			resultMap.put("flag", 0);
			List<Map> tlist = resourceallotportService.findTime();		
			if(tlist.size()>0){
				Map<String,Object> map = tlist.get(0);
				//如果超过15分钟未完成资源上传并且分配则认为是系统死锁
				if(new Integer(map.get("DIF_TIME").toString())>15){
					resourceallotportService.update(CommonUtils.checkNull(map.get("TM_ALLOT_STATUS_ID")));
				}
			}
			List<TmAllotStatusPO> taList = TmAllotStatusPO.find(" ATYPE = ? AND STATUS = ? ", 3,0);
			if(taList.size()>0){
				resultMap.put("flag", 1);
				resultMap.put("message", "资源分配上传后台正在执行中,请耐心等待...");
			}else{
				DateFormat dfmt = new SimpleDateFormat("yyyyMMdd");
				TmAllotStatusPO po = new TmAllotStatusPO();
				po.setLong("STATUS", 0L);
				po.setInteger("ATYPE", 3);
				po.setString("REMARK", "OTD资源分配上传");
				po.setString("ALLOT_DATE", dfmt.format(new Date()));
				po.setLong("CREATE_BY", loginInfo.getUserId());
				po.setTimestamp("CREATE_DATE", new Date());
				po.save();
				
				if(type==1){
					TmpUploadResourcePO.deleteAll();
				}		
			}
			ArrayList<ResourceAllotImportDto> dataList = null;
			ArrayList<ResourceAllotImport2Dto> dataList2 = null;
			if(type==1){
				ImportResultDto<ResourceAllotImportDto> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
						new AbstractExcelReadCallBack<ResourceAllotImportDto>(ResourceAllotImportDto.class));
				dataList = importResult.getDataList();
				for (ResourceAllotImportDto rowDto : dataList) {
					// 只有全部是成功的情况下，才执行数据库保存
					resourceallotportService.insertTmp(rowDto);
				}
			}else{
				ImportResultDto<ResourceAllotImport2Dto> importResult = excelReadService2.analyzeExcelFirstSheet(importFile,
						new AbstractExcelReadCallBack<ResourceAllotImport2Dto>(ResourceAllotImport2Dto.class));
				dataList2 = importResult.getDataList();
				for (ResourceAllotImport2Dto rowDto : dataList2) {
					resourceallotportService.insertTmp2(rowDto);
				}
			}
			if(type==1){
				List<Map> errList = resourceallotportService.checkData();
				if(errList.size()>0){
					resourceallotportService.updateStatus();
					resultMap.put("flag", 4);
					resultMap.put("errList", errList);
					resultMap.put("message", "导入数据校验失败，请见错误列表");
				}else{
					List<Map> tList = resourceallotportService.findOrderAllot();
					String allotDate = "";
					if(tList.size()>0){
						allotDate = tList.get(0).get("CREATE_DATE").toString();
					}else{
						DateFormat df = new SimpleDateFormat("yyyyMMdd");
						allotDate = df.format(new Date());
					}
					long startTime=System.currentTimeMillis();
					resourceallotportService.updateDeal();
					
					List<Object> inParameter = new ArrayList<Object>();// 输入参数
					inParameter.add(loginInfo.getUserId());
					inParameter.add(allotDate);
					
					//月初结转存储过程
//				dao.callProcedure("MONLY_LATELY_ORDER",inParameter,null);
					resourceallotportService.callProcedureMonth(inParameter);
					
					//资源分配大区存储过程
//	        	dao.callProcedure("AREA_TOTAL_GAP_INFO",inParameter,null);
					resourceallotportService.callProcedureInfo(inParameter);
					
					//修改导入状态
					resourceallotportService.updateStatus();		
					
					resultMap.put("flag", 2);
					resultMap.put("succesList", getList(resourceallotportService.getExcelData(queryParam)));
					logger.info("------OTD资源分配 调用存储过程(AREA_TOTAL_GAP_INFO) 结束 用时："+String.format("%.2f",(System.currentTimeMillis()-startTime)*0.001)+"秒------");			
				}
			}else{
				List<Map> errList = resourceallotportService.checkData2();
				if(errList.size()>0){		
					resourceallotportService.updateStatus();
					resultMap.put("flag", 5);
					resultMap.put("errList", errList);
					resultMap.put("message", "导入数据校验失败，请见错误列表");
				}else{			
					DateFormat df = new SimpleDateFormat("yyyy年MM月");
					resourceallotportService.insertTempToTm();
					resultMap.put("flag", 3);
					resultMap.put("message", df.format(new Date())+"月初结转上传完成");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("资源分配上传失败！");
		}
		return resultMap;
	}
	
	/**
	 * 封装汇总的数据
	 * @param excelData
	 * @return
	 */
	private List<Map> getList(Map<String, List<Map>> excelData) {
		List<Map> resultMap = new ArrayList<Map>();
		List<Map> sList = excelData.get("Slist");
		if(null!= sList && sList.size()>0){
			for (Map map : sList) {
				String seriesName = CommonUtils.checkNull(map.get("SERIES_NAME"));
				resultMap.addAll(excelData.get(seriesName));
			}
		}
		return resultMap;
	}

	/**
	 * 
	 * 资源上传下载
	 * @author 夏威
	 * @date 2017年1月16日
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============资源分配信息下载===============");
		Map<String, List<Map>> excelData = resourceallotportService.getExcelData(queryParam);
		excelData.remove("Slist");
	    List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	    exportColumnList.add(new ExcelExportColumn("ORG_NAME","区域"));
	    exportColumnList.add(new ExcelExportColumn("SALE_AMOUNT","批售目标"));
	    exportColumnList.add(new ExcelExportColumn("NUM1","月初结转"));
	    exportColumnList.add(new ExcelExportColumn("NUM2","全国池已定"));
	    exportColumnList.add(new ExcelExportColumn("NUM22","全国池未定"));
	    exportColumnList.add(new ExcelExportColumn("NUM3","区域池已定"));
	    exportColumnList.add(new ExcelExportColumn("NUM33","区域池未定"));
	    exportColumnList.add(new ExcelExportColumn("NUM4","指派资源"));
	    exportColumnList.add(new ExcelExportColumn("NUM5","期货订单(一次开票)"));
	    exportColumnList.add(new ExcelExportColumn("GAP","当前Gap"));
	    exportColumnList.add(new ExcelExportColumn("ALLOT_NUM","本次分配"));
	    exportColumnList.add(new ExcelExportColumn("ALLOT_MONTH_NUM","本月分配"));
	    exportColumnList.add(new ExcelExportColumn("RATE","资源满足率(分配资源/批售目标)"));
	    excelService.generateExcel(excelData, exportColumnList, "资源分配信息.xls", request,response);
	}
    /**
     * 
     * 资源分配初始化
     * @author 夏威
     * @date 2017年1月16日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> init(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("============资源分配初始化===============");
    	Map<String, Object> result = new HashMap<>();
    	List<Map> list = getList(resourceallotportService.getExcelData(queryParam));
    	if(list.size()>0){
    		result.put("status", 1);
    		result.put("succesList",list);
    	}else{
    		result.put("status", 0);
    	}
    	return result;
    }
    
    /**
     * 
     * 资源分配
     * @author 夏威
     * @date 2017年1月16日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/allot", method = RequestMethod.PUT)
    public ResponseEntity<LoginInfoDto> allot(@RequestParam Map<String, String> queryParam, UriComponentsBuilder uriCB) throws Exception {
    	logger.info("============资源分配操作===============");
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	resourceallotportService.allot(queryParam);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/init").buildAndExpand(loginInfo.getUserId()).toUriString());
        return new ResponseEntity<>(loginInfo,headers,HttpStatus.CREATED);
    }

}
