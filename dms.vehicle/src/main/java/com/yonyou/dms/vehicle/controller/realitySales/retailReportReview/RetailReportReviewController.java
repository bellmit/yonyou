package com.yonyou.dms.vehicle.controller.realitySales.retailReportReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.RetailReportReviewPassDTO;
import com.yonyou.dms.vehicle.service.realitySales.retailReportReview.RetailReportReviewService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 零售上报审核Controller
 * @author DC
 *
 */
@Controller
@TxnConn
@RequestMapping("/retailReportReview")
public class RetailReportReviewController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private RetailReportReviewService service;
	
	@Autowired
	private ExcelGenerator  excelService;
	
	/**
	 * 零售上报审核查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto retailReportReviewQuery(@RequestParam Map<String, String> queryParam){
		logger.info("============零售上报审核查询===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfoDto = service.retailReportReviewQuery(queryParam,loginInfo);
		return pageInfoDto;
	}
	
	/**
     * 根据ID 上报审核信息
     * @param id NVDR_ID
     * @return
     */

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findRetailReportQueryDetail(@PathVariable(value = "id") Long id) {
    	logger.info("============根据ID 零售上报信息===============");
    	Map<String, Object> map = service.queryDetail(id);
        return map;
    }
	
	
	/**
	 * 零售上报审核信息下载
	 * @author DC
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	@ResponseBody
	public void retailReportReviewDownLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============零售上报审核信息下载===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = service.retailReportReviewQuerySuccList(queryParam,loginInfo);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("零售上报审核信息", resultList);	
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_DESC2","大区"));
	    exportColumnList.add(new ExcelExportColumn("ORG_DESC3","小区"));
	    exportColumnList.add(new ExcelExportColumn("SWI_CODE","SAP代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
	    exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
	    exportColumnList.add(new ExcelExportColumn("CTM_TYPE","客户类型", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("BRAND_CODE","品牌"));
	    exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
	    exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款"));
	    exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","年款"));
	    exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
	    exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
	    exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
	    exportColumnList.add(new ExcelExportColumn("REMARK","车辆分配备注", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE","车辆用途", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("STATUS","开票状态", ExcelDataType.Oem_Dict));
	    exportColumnList.add(new ExcelExportColumn("INVOICE_DATE","开票日期"));
	    exportColumnList.add(new ExcelExportColumn("REPORT_TYPE","类型"));
	    exportColumnList.add(new ExcelExportColumn("CREATE_DATE","零售上报日期"));
	    excelService.generateExcel(excelData, exportColumnList, "零售上报审核信息.xls", request,response);
	}
	
	/**
	 * 批量零售上报审核通过
	 * @param flagnvdrId
	 */
	@RequestMapping(value = "/approved", method = RequestMethod.PUT)
	@ResponseBody
	public void retailReportReviewApproved(@RequestBody RetailReportReviewPassDTO rrrpDTO) {//@RequestBody DemoUserUpdateDto userSelectDto
		logger.info("============零售上报审核通过===============");
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String flag = rrrpDTO.getFlag();//1为批量  0为详细审批
		String nvdrIds = rrrpDTO.getNvdrIds();
		String nvdrId = rrrpDTO.getNvdrId();
		if("1".equals(flag)){ 
			nvdrId = nvdrIds.substring(0,nvdrIds.length()-1);
		}
		service.batchPass(nvdrId,loginInfo);
	}

}
