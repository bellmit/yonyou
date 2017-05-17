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
import com.yonyou.dms.retail.service.basedata.TmRetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 经销商提报查询
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/sales")
public class TmRetailController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	TmRetailService trservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = trservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("经销商提报信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BANK", "银行名称"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME2", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE1", "零售上报经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME1", "零售上报经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE2", "申请贴息经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME2", "申请贴息经销商名称"));
		exportColumnList.add(new ExcelExportColumn("IS_SAME", "是否相同"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("NVDR_DATE", "零售上报时间"));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "销售类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RETAIL_PRICE", "MSRP"));
		exportColumnList.add(new ExcelExportColumn("NET_PRICE", "净车价","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请时间"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "提报时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_DATE", "银行放款时间"));
		exportColumnList.add(new ExcelExportColumn("DEAL_AMOUNT", "贷款金额","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("FIRST_PERMENT_RATIO", "首付比例"));
		exportColumnList.add(new ExcelExportColumn("INSTALL_MENT_NUM", "分期期数", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES", "商户手续费","###0.00##"));
		exportColumnList.add(new ExcelExportColumn("MERCHANT_FEES_RATE", "商户手续费率"));
		exportColumnList.add(new ExcelExportColumn("TRDO", "是否上传", ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "经销商提报信息管理.xls", request, response);

	}
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("查询经销商提报信息");
        PageInfoDto pageInfoDto=trservice.getTmRetaillist(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据ID进行查询
     * @param id
     * @return
     */

    @RequestMapping(value="/sun/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable(value = "id") Long id) throws Exception{
    	Map<String,Object> map = trservice.findById(id);
        return map;
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

}
