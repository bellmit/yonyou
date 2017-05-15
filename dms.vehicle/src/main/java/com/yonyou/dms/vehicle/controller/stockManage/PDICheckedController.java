package com.yonyou.dms.vehicle.controller.stockManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.PDICheckedDTO;
import com.yonyou.dms.vehicle.service.stockManage.PDICheckedService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* PDI检查控制类
* @author wangliang
* @date 2017年01月11日
*/
@Controller
@TxnConn
@RequestMapping("/stockManage/PDIChecked")
public class PDICheckedController extends BaseController {
    
    @Autowired
    PDICheckedService pdicheckedService;
    
    @Autowired
    private ExcelGenerator      excelService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPDIChecked(@RequestParam Map<String,String> queryParam) {
        PageInfoDto pageInfoDto = pdicheckedService.queryPDIChecked(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 修改
     * @param id
     * @param serviceitemdto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{vin}", method = RequestMethod.PUT)
    public ResponseEntity<PDICheckedDTO> updateserviceitem(@PathVariable("vin") String vin,@RequestBody PDICheckedDTO pdiCheckeddto,UriComponentsBuilder uriCB) {
    	pdicheckedService.update(vin, pdiCheckeddto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/stockManage/PDICheckedm/{vin}").buildAndExpand(vin).toUriString());  
        return new ResponseEntity<PDICheckedDTO>(headers, HttpStatus.CREATED);  
    }
    
    /**
     * 
     * @date 2017年01月12日
     * @param vin
     * @return
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/{vin}/edit",method= RequestMethod.GET)
    @ResponseBody
    public Map editSearch(@PathVariable("vin") String vin ) {
    	Map map = pdicheckedService.editSearch(vin);
    	return map;
    }
    
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response ) {
    	List<Map> resultList = pdicheckedService.queryPDICheckedExport(queryParam);
    	Map<String,List<Map>> excelData = new HashMap<String, List<Map>>();
    	excelData.put("PDI检查", resultList);
    	List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
    	exportColmnList.add(new ExcelExportColumn("VIN","VIN"));
    	exportColmnList.add(new ExcelExportColumn("TH_REPORT_NO","技术报告号"));
    	exportColmnList.add(new ExcelExportColumn("PDI_RESULT","PDI检查结果"));
    	exportColmnList.add(new ExcelExportColumn("PDI_CHECK_DATE","PDI检查时间"));
    	exportColmnList.add(new ExcelExportColumn("PDI_SUBMIT_DATE","PDI检查提交日期"));
    	exportColmnList.add(new ExcelExportColumn("MILEAGE","公里数"));
    	exportColmnList.add(new ExcelExportColumn("PDI_URL","PDI检查表jpg附件"));
    	exportColmnList.add(new ExcelExportColumn("BRAND_CODE","品牌"));
    	exportColmnList.add(new ExcelExportColumn("SERIES_CODE","车系"));
    	exportColmnList.add(new ExcelExportColumn("MODEL_CODE","车型"));
    	exportColmnList.add(new ExcelExportColumn("CONFIG_CODE","配置"));
    	exportColmnList.add(new ExcelExportColumn("PDI_REMARK","故障描述"));
    	//生成excel文件
    	excelService.generateExcel(excelData, exportColmnList, "PDI检查.xls", request, response);
    }
}
