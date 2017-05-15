package com.yonyou.dms.retail.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.retail.service.basedata.TmRetailSumService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 销售折扣查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/salessum")
public class TmRetailSumController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TmRetailSumService trservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	 @Autowired
	    SystemParamService paramService;
		
		 /**
	     * 导入模板下载
	     */
	    @RequestMapping(value = "/{type}",method = RequestMethod.GET)
	    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response){
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
	
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = trservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("销售折扣信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BANK", "银行名称"));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码1"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE2", "经销商代码2"));
		exportColumnList.add(new ExcelExportColumn("DEALERSHORTNAME", "零售经销商简称"));
		exportColumnList.add(new ExcelExportColumn("DEALERCODE", "零售经销商代码"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "车架号"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("SALES_TYPE", "销售类型"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_DATE", "银行放款时间"));
		exportColumnList.add(new ExcelExportColumn("MSRP", "MSRP","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("NET_PRICE", "成交价","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("FINANCING_PNAME", "零售融资产品名称"));
		exportColumnList.add(new ExcelExportColumn("FIRST_PERMENT", "首付","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("DEAL_AMOUNT", "贷款金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("FIRST_PERMENT_RATIO", "首付比例"));
		exportColumnList.add(new ExcelExportColumn("INSTALL_MENT_NUM", "分期期数"));
		exportColumnList.add(new ExcelExportColumn("TOTAL_INTEREST", "总利息（手续费）","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("INTEREST_RATE", "原利率"));
		exportColumnList.add(new ExcelExportColumn("POLICY_RATE", "政策费率"));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES_RATE", "商户手续费率"));
		exportColumnList.add(new ExcelExportColumn("ALLOWANCED_SUM_TAX", "贴息金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("ALLOWANCE_BANK_SUM_TAX", "已补贴金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("NVDR_DATE", "上报时间"));
		exportColumnList.add(new ExcelExportColumn("SCANNING_DATE", "发票扫描日期"));
		exportColumnList.add(new ExcelExportColumn("R_MONTH", "贴息月份"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传时间"));
		exportColumnList.add(new ExcelExportColumn("CREATE_BY", "上传账号"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		excelService.generateExcel(excelData, exportColumnList, "销售折扣信息管理.xls", request, response);

	}
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("查询销售折扣信息");
        PageInfoDto pageInfoDto=trservice.getTmRetaillist(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 获取补贴年
     * @author DC
     * @date 2017年3月12日
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value="/subsidyYear",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSubsidyYearList(){
    	logger.info("=====补贴年加载=====");
    	List<Map> monthList = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		int nowYear = cal.get(Calendar.YEAR);
		for(int i=nowYear-3;i<=nowYear+4;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("SUBSUDY_YEAR", i);
			monthList.add(map);
		}
		return monthList;
    }
    
}
