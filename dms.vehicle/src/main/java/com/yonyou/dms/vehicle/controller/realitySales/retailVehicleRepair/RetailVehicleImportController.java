package com.yonyou.dms.vehicle.controller.realitySales.retailVehicleRepair;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
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
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.importDto;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TmpVsNvdrDTO;
import com.yonyou.dms.vehicle.service.realitySales.retailVehicleRepair.RetailVehicleImportService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆补传
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/retailVehicleImport")
@SuppressWarnings("all")
public class RetailVehicleImportController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
    private SystemParamService paramService;
	
	@Autowired
    private ExcelRead<TmpVsNvdrDTO>  excelReadService;
	
	@Autowired
	private RetailVehicleImportService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 
	* @Title: downloadTemple 
	* @Description:车辆补传传（下载导入模版） 
	* @param @param type
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/retailVehicleDownloadTemple/{type}",method = RequestMethod.GET)
    public void yearPlanDownloadTemple(@PathVariable(value = "type") String type,
    		HttpServletRequest request,HttpServletResponse response){
		logger.info("============ 车辆补传 （下载导入模版）===============");
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
	
	/**
	 * 上传文件 RemarkImpDto importRemark
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/retailVehicleExcelOperate", method = RequestMethod.POST)
	@ResponseBody
	public List<Map> resourceorder(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, importDto importDto, UriComponentsBuilder uriCB)
			throws Exception {
		logger.info("============车辆补传导入===============");
		//清空临时表中数据
		service.delete();
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<TmpVsNvdrDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<TmpVsNvdrDTO>(TmpVsNvdrDTO.class));
		ArrayList<TmpVsNvdrDTO> dataList = importResult.getDataList();
		
		service.insertRemark(dataList); //导入数据记录（记录导入的数据并将验证功过的数据直接 INSERT 零售表）
		List<Map> allMessage = service.allMessageQuery();	// 查询此次导入数据验证后的结果
		return allMessage;
	}
	
	/**
	 * 查询上传数据数量和错误数据数量
	 */
	@RequestMapping(value = "/getDataNum", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getDataNum(){
		logger.info("============查询上传数据数量和错误数据数量===============");
		/**  当前登录信息 **/
		Map<String, String> countDataNum = service.countDataNum();	// 计算上传数据数量（和错误数据数量）
		return countDataNum;
	}
	
	/**
	 * OTD零售车辆上传(错误数据)下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "retailVehicleDownloadTemple/errDate", method = RequestMethod.GET)
	@ResponseBody
	public void inventoryDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============OTD零售车辆上传(错误数据)下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.findReeDate(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("扫描发票汇总查询信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN","VIN号"));
	    exportColumnList.add(new ExcelExportColumn("UPLOAD_DATE","零售上报日期"));
	    exportColumnList.add(new ExcelExportColumn("LINE_NUMBER","出错行号"));
	    exportColumnList.add(new ExcelExportColumn("CHECK_RESULT","错误原因"));

		excelService.generateExcel(excelData, exportColumnList, "OTD零售车辆上传(错误数据).xls", request,response);
	}
	
	/**
	 * 根据ID查询对应数据
	 */
	@RequestMapping(value = "/getData/{ID}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getData(@PathVariable(value = "ID") Integer id){
		logger.info("============根据ID查询对应数据===============");
		/**  当前登录信息 **/
		List<Map> allMessage = service.allMessageQuery(id);	// 查询此次导入数据验证后的结果
		return allMessage;
	}

}
