package com.yonyou.dms.retail.controller.basedata;

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
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountOfferDTO;
import com.yonyou.dms.retail.service.basedata.BankingService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/salesb")
@SuppressWarnings("rawtypes")
public class BankingController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	BankingService trservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> resultList = trservice.queryEmpInfoforExport(loginInfo,queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("经销商零售金融贴息提报信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BANK_NAME", "银行名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "零售上报经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "零售上报经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE2", "申请贴息经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME2", "申请贴息经销商名称"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "零售上报时间"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "销售类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "MSRP","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("NET_PRICE", "净车价","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请时间"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "提报时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_DATE", "银行放款时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_AMOUNT", "贷款金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("SF_RATE", "首付比例"));
		exportColumnList.add(new ExcelExportColumn("INSTALL_MENT_NUM", "分期期数", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES", "商户手续费","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES_RATE", "商户手续费率"));
		excelService.generateExcel(excelData, exportColumnList, "经销商零售金融贴息提报信息管理.xls", request, response);

	}
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	logger.info("查询经销商零售金融贴息提报信息");
        PageInfoDto pageInfoDto=trservice.findBanking(loginInfo,queryParam);
        return pageInfoDto;
    }
    /**
     * 修改零售金融贴息
     * @param id
     * @param tcDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    @ResponseBody
  	public void ModifyBanking(@RequestBody TmRetailDiscountOfferDTO tcDto) throws Exception {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		logger.info("修改零售金融贴息提报信息");
      	trservice.modifyBanking(tcDto,loginInfo);
  	}
    /**
     * 根据ID进行查询
     * @param id
     * @return
     */

    @RequestMapping(value="/{REPORT_ID}/{VIN}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable(value = "REPORT_ID") Long reportId,
							    	   @PathVariable(value = "VIN") String vin
    		) throws Exception{
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	Map<String,Object> map = trservice.findById(reportId,vin,loginInfo);
        return map;
    }
    
    /**
     * 银行
     * @param params
     * @return
     */
	@RequestMapping(value="/Charge/bank",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectBankName() {
        List<Map> chargelist = trservice.selectBankName();
        return chargelist;
    } 
    
}
