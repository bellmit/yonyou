package com.yonyou.dms.manage.controller.salesPlanManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.TmWorkWeekImportDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.ForecastImportDto;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyPlanPO;
import com.yonyou.dms.manage.service.salesPlanManager.MonthPlanImportMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: MonthPlanImportMaintainController 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月14日 上午10:05:20 
*
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Controller
@TxnConn
@RequestMapping("/monthPlanImportMaintain")
public class MonthPlanImportMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
    private SystemParamService paramService;
	
	@Autowired
	private MonthPlanImportMaintainService monthPlanImportMaintainService ;
	
	@Autowired
    private ExcelRead<TmpVsMonthlyPlanDTO>  excelReadService;
	
	/**
	 * 
	* @Title: downloadTemple 
	* @Description: 月度任务上传（模版下载）
	* @param @param type
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/downloadTemple/{type}",method = RequestMethod.GET)
    public void downloadTemple(@PathVariable(value = "type") String type,
    		HttpServletRequest request,HttpServletResponse response){
		logger.info("============ 月度任务上传（模版下载））===============");
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
	 * 
	* @Title: getYearList 
	* @Description: 月度任务上传（获取年列表 ）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearList() {
		logger.info("============ 月度任务上传（获取年列表 ）===============");
		List<Map> yearList = new ArrayList<Map>();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);	// 当前年
		for (int i = year - 1; i < year + 3; i++) {
			Map m = new HashMap();
			m.put("YEAR", i);
			yearList.add(m);
		}
		return yearList;
	}
	
	/**
	 * 
	* @Title: getYearList 
	* @Description: 获取月份 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getMonthList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getMonthList() {
		List<Map> monthList= new ArrayList<Map>();
		for (int i = 1; i < 13; i++) { // 初始化当前年包含当前月以及以后月信息
			Map m = new HashMap();
			m.put("MONTH", i);
			monthList.add(m);
		}
		return monthList;
	}

	/**
	 * 
	* @Title: monthPlanInfoImport 
	* @Description: 月度目标导入
	* @param @param queryParam
	* @param @param importFile
	* @param @param forecastImportDto
	* @param @param uriCB
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<TmpVsYearlyPlanDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/monthPlanInfoImport", method = RequestMethod.POST)
    @ResponseBody
    public List<TmpVsMonthlyPlanDTO> monthPlanInfoImport(
    		@RequestParam final Map<String,String> queryParam,
    		@RequestParam(value = "file") MultipartFile importFile,
    		ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) 
    				throws Exception {
		logger.info("============月度目标导入===============");
        // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<TmpVsMonthlyPlanDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TmpVsMonthlyPlanDTO>(TmpVsMonthlyPlanDTO.class));
        if(!importResult.isSucess()){
			throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
		}
        ArrayList<TmpVsMonthlyPlanDTO> list = monthPlanImportMaintainService.monthPlanInfoImport(importResult,queryParam);
		return list;
    }
	
	
	
	
}
