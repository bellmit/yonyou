package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialDefineDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtObsoleteMaterialDefineDcsPO;
import com.yonyou.dms.part.service.basedata.PartObsoleteMaterialDefineService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 呆滞品定义
 * @author ZhaoZ
 *@date 2017年4月11日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partObsoleteMaterialDefine")
public class PartObsoleteMaterialDefineController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartObsoleteMaterialDefineService service;
    @Autowired
   	private ExcelGenerator excelService;
    
    /**
  	 * 查询呆滞品定义List
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/queryPartObsoleteMaterialDefine",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto queryPartObsoleteMaterialDefine(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====查询呆滞品定义List=====");
  		
  		 return service.queryPartObsoleteMaterialList(queryParams);
  		
  	}
  	/**
	 * 下发
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/isSend/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> isSend(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB){
		logger.info("==================下发================");
		service.isSendStatus(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/isSend").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
  	
	/**
     * 查询月份
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/queryMonth",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>> queryMonth(@RequestParam Map<String, String> queryParams){
    	logger.info("=====查询月份=====");
    	List<Map<String,Object>> list = new ArrayList<>();
   	 	
    	for(int i=1 ; i<=24;i++){
 		     if(i==1||i%3==0){
 		    	Map<String,Object> map = new HashMap<>();
     		      String stockMonthName = i+"个月";
     		      int stockMonth = i;
     		      map.put("stockMonthName", stockMonthName);
     		      map.put("stockMonth", stockMonth);
     		      list.add(map);
     		     }
		    }
        return list;
    }
	
    /**
	  * 修改呆滞品页面跳转
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/modifyPartObsoleteMaterialDefine/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> modifyPartObsoleteMaterialDefine(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====修改呆滞品页面跳转=====");
		TtObsoleteMaterialDefineDcsPO mdPO = TtObsoleteMaterialDefineDcsPO.findById(id);
		if(mdPO!=null){
			return mdPO.toMap();
		}
		return null;
	}
	
	/**
	 * 呆滞品定义修改
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updatePartObsoleteMaterialDefine/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtObsoleteMaterialDefineDcsDTO> updatePartObsoleteMaterialDefine(@RequestBody TtObsoleteMaterialDefineDcsDTO dto,@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB){
		logger.info("==================呆滞品定义修改================");
		service.update(id,dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/updatePartObsoleteMaterialDefine").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
  	 * 呆滞品查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/findALL",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto findALL(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====呆滞品查询=====");
  		
  		 return service.findALLList(queryParams);
  		
  	}
  	
  	/**
	 * 下载
	 */
  	@RequestMapping(value="/downloadAll",method = RequestMethod.GET)
  	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============下载===============");
		List<Map> dealerList = service.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("呆滞品查询下载",dealerList);
		exportColumnList.add(new ExcelExportColumn("ORG_NAME2", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_CODE", "仓库代码"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_NAME", "仓库名称"));
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("OH_COUNT", "库存数量"));
		exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "含税MSRP"));
		exportColumnList.add(new ExcelExportColumn("TOTALS", "金额"));
		exportColumnList.add(new ExcelExportColumn("LAST_DATE", "最近出库日期"));
		exportColumnList.add(new ExcelExportColumn("INTERVALDATE", "积压期（天）"));
		excelService.generateExcel(excelData, exportColumnList, "呆滞品查询.xls", request, response);
	}
}
