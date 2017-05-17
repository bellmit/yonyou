package com.yonyou.dms.part.controller.basedata;

import java.text.ParseException;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialReleaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.service.basedata.PartObsoleteMaterialDealService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 呆滞品交易确认
 * @author ZhaoZ
 *@date 2017年4月12日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partObsoleteMaterialDeal")
public class PartObsoleteMaterialDealController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartObsoleteMaterialDealService service;
    
    @Autowired
	private ExcelGenerator excelService;
    /**
  	 * 呆滞品交易查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/queryPartObsoleteMaterialDeal",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto queryPartObsoleteMaterialDeal(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====呆滞品交易查询=====");
  		
  		 return service.queryPartObsoleteMaterial(queryParams);
  		
  	}
  	
  	/**
	 * 交易确认
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/affirm/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> affirm(@PathVariable(value = "id") String applyId,UriComponentsBuilder uriCB){
		logger.info("==================交易确认================");
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		Date currentTime_1 = new java.util.Date();
		if(applyId!=null){
			TtObsoleteMaterialApplyDcsPO maPO = TtObsoleteMaterialApplyDcsPO.findById(Long.parseLong(applyId));
			String OUT_WAREHOUS_NO="O"+formatter.format(currentTime_1)+applyId.substring(applyId.length()-5,applyId.length());//生成出库单号
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			maPO.setInteger("STATUS",OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_02);
			long time= System.currentTimeMillis();
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				maPO.setTimestamp("AFFIRM_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			maPO.setString("OUT_WAREHOUS_NO",OUT_WAREHOUS_NO);
			//调用配件调拨出库单下发接口
			//SEDMS066 se=new SEDMS066();
			//se.sendData(OUT_WAREHOUS_NO);
			boolean flag = false;
			flag = maPO.saveIt();
			if(flag){			
			}else{
				throw new ServiceBizException("确认失败");
			}
		}
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/affirm").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
  
	
	/**
	 * 交易驳回
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/reject/{applyId}/{canApplyNum}/{applyNum}/{releaseId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> reject(@PathVariable(value = "applyId") String applyId,@PathVariable(value = "canApplyNum") Integer canApplyNum,
			@PathVariable(value = "applyNum") Integer applyNum,@PathVariable(value = "releaseId") Long releaseId,UriComponentsBuilder uriCB){
		logger.info("==================交易驳回================");
		boolean flag = false;
		if(applyId!=null && applyId!=""){
			TtObsoleteMaterialApplyDcsPO maPO = TtObsoleteMaterialApplyDcsPO.findById(Long.parseLong(applyId));
			maPO.setInteger("STATUS",OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_05);
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long time= System.currentTimeMillis();
			try {
				Date date = sdf.parse(sdf.format(new Date(time)));
				java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
				maPO.setTimestamp("REJECT_DATE",st);
			} catch (ParseException e) {
				e.printStackTrace();
			} 
			flag = maPO.saveIt();
			if(flag){			
			}else{
				throw new ServiceBizException("驳回失败");
			}
			TtObsoleteMaterialReleaseDcsPO tomrPo = TtObsoleteMaterialReleaseDcsPO.findById(releaseId);
			tomrPo.setInteger("APPLY_NUMBER",canApplyNum+applyNum);
			flag = tomrPo.saveIt();
			if(flag){			
			}else{
				throw new ServiceBizException("驳回失败");
			}
		}
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/reject").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	
  	/**
	 * 下载
	 */
  	@RequestMapping(value="/downloadInfo",method = RequestMethod.GET)
  	@ResponseBody
	public void downloadInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============下载===============");
		List<Map> dealerList = service.downloadInfo(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("呆滞品确认查询下载",dealerList);
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商简称"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_TEL", "联系电话"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "详细地址"));
		exportColumnList.add(new ExcelExportColumn("POST_CODE", "邮编"));
		exportColumnList.add(new ExcelExportColumn("WAREHOUSE", "经销商代码"));
		
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("RELEASE_NUMBER", "发布数量"));
		exportColumnList.add(new ExcelExportColumn("APPLY_NUMBER", "申请调拨数量"));
		exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "单价"));
		exportColumnList.add(new ExcelExportColumn("AMOUNT", "金额"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("AFFIRM_DATE", "确认日期"));
		excelService.generateExcel(excelData, exportColumnList, "呆滞品确认查询导出.xls", request, response);
	}
	
}
