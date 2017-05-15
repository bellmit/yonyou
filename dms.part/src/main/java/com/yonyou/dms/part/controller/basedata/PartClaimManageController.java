package com.yonyou.dms.part.controller.basedata;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.pdf.CreatePdf;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtptClaimDcsDTO;
import com.yonyou.dms.part.service.basedata.PartClaimService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件索赔审核
 * @author ZhaoZ
 * @date 2017年3月28日 
 */
@Controller
@TxnConn
@RequestMapping("/partClaimManage")
public class PartClaimManageController {
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
   
    @Autowired
	private PartClaimService partClaimService;
    @Autowired
	private ExcelGenerator excelService;
    
    /**
	 * 到货索赔审核查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/checkClaim",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto checkClaim(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====到货索赔审核查询=====");
		
		 return partClaimService.checkClaim(queryParams);
		
	}
    
	/**
	  * 到货索赔审核明细、补发交货单录入初始化
	  * @param  id
	  * @throws Exception
	  */
	@RequestMapping(value="/checkDetailInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkDetailInit(@PathVariable(value = "id") BigDecimal claimId) {
		logger.info("=====到货索赔审核明细、补发交货单录入初始化=====");
		//索赔基本信息
		Map<String, Object> mapA = partClaimService.checkDetail(claimId);
		// 索赔订单详细信息
		Map<String, Object> mapB = partClaimService.findClaimDetailInfoByClaimId(claimId);
		if(mapA!=null){
			mapA.putAll(mapB);
			return mapA;
		}
		return mapB;
	}
	
	/**
	 * 审核历史
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/findCheckInfoByClaimId/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findCheckInfoByClaimId(@PathVariable(value = "id") BigDecimal claimId) {
		 logger.info("=====审核历史=====");
		
		 return partClaimService.findCheckInfoByClaimId(claimId);
		
	}
	
	/**
	 * 查询附件
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/findPartFuJian/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findPartFuJian(@PathVariable(value = "id") BigDecimal claimId) {
		 logger.info("=====查询附件=====");
		 String sql = "SELECT * FROM tt_pt_claim_att_dcs WHERE CLAIM_ID = "+claimId+"";
		 return OemDAOUtil.pageQuery(sql, null);
	}
	
	
	/**
	 * 补发交货单录入
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/saveReissueTransNo/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtptClaimDcsDTO> saveReissueTransNo(@RequestBody TtptClaimDcsDTO dto,UriComponentsBuilder uriCB,@PathVariable(value = "id") BigDecimal id){
		logger.info("==================补发交货单录入================");
		partClaimService.saveReissueTransNo(dto,id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveReissueTransNo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	/**
	 * 同意   拒绝  驳回
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/checkAgree/{id}/{type}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtptClaimDcsDTO> checkAgree(@RequestBody TtptClaimDcsDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "id") BigDecimal id,@PathVariable(value = "type") String type){
		logger.info("==================同意   拒绝  驳回================");
		partClaimService.checkAgree(dto,id,type);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/checkAgree").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	 /**
	  * 到货索赔查询
	  * @param queryParams
	  * @throws Exception
	  */
	 @RequestMapping(value="/queryClaim",method = RequestMethod.GET)
	 @ResponseBody
	 public PageInfoDto queryClaim(@RequestParam Map<String, String> queryParams) {
		  logger.info("=====到货索赔查询=====");
		
		  return partClaimService.queryClaim(queryParams);
		
	 }
	 
	   /**
	    * 信息下载
	    * @param request
	    * @param response
	    * @param queryParams
	    */
		@RequestMapping(value="/download",method = RequestMethod.GET)
		@ResponseBody
		public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
			logger.info("============信息下载===============");
			List<Map> dealerList = partClaimService.queryDownLoad(queryParams);
			List<ExcelExportColumn> exportColumnList = new ArrayList<>();
			
			Map<String, List<Map>> excelData = new HashMap<>();
			excelData.put("到货索赔查询导出",dealerList);
			exportColumnList.add(new ExcelExportColumn("FCA_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("CLAIM_NO", "索赔单号"));
			exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件编号"));
			exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
			exportColumnList.add(new ExcelExportColumn("CLAIM_NUM", "索赔数量"));
			exportColumnList.add(new ExcelExportColumn("CLAIM_REQUIRE", "索赔要求",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("CLAIM_PROPERTY", "索赔性质",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("DELIVER_NO", "交货单号"));
			exportColumnList.add(new ExcelExportColumn("PART_MDOEL", "配件分类",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("REPORT_DATE", "提交时间"));
			exportColumnList.add(new ExcelExportColumn("CLAIM_STATUS", "状态",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("DUTY_BY", "责任供应商",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("REISSUE_TRANS_NO", "补发运单号"));
			exportColumnList.add(new ExcelExportColumn("CHECK_DATE", "索赔审核日期"));
			exportColumnList.add(new ExcelExportColumn("TRANS_COMPANY", "运输公司"));
			exportColumnList.add(new ExcelExportColumn("TRANS_STOCK", "库存发货地点"));
			exportColumnList.add(new ExcelExportColumn("AMOUNT", "索赔价格"));
			
			
			excelService.generateExcel(excelData, exportColumnList, "到货索赔.xls", request, response);
		}
		
		 /**
		    * 到货索赔查询明细导出PDF下载
		    * @param request
		    * @param response
		    * @param queryParams
		    * @throws IOException 
		    */
			@RequestMapping(value="/downloadPDF/{id}",method = RequestMethod.GET)
			@ResponseBody
			public void downloadPDF(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "id") BigDecimal id) throws IOException {
				logger.info("============到货索赔查询明细导出PDF下载===============");
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date currentTime_1 = new java.util.Date();
				// 索赔基本信息
				Map<String, Object> claimInfoMap = partClaimService.findClaimInfoByClaimId(id);
				// 索赔订单详细信息
				Map<String, Object> claimDetailInfoMap = partClaimService.findClaimDetailInfoByClaimId(id);
				String contextPath=request.getContextPath();
				String tmpFileName = "配件到货查询明细"+formatter.format(currentTime_1)+".pdf";
				File file = new File(tmpFileName);
				try {
					new CreatePdf(file).generatePDF(claimInfoMap,claimDetailInfoMap,contextPath);
				} catch (Exception e) {
					e.printStackTrace();
				}
               
				String tmpFileUrl="."+File.separator+tmpFileName;
			    file.createNewFile(); 
			    exportFile(tmpFileUrl,tmpFileName,response);
			}

		@SuppressWarnings({ "unused"})
		private void exportFile(String fileUrl, String fileName,HttpServletResponse response) {
			HashMap<String,String> mime = new HashMap<String,String>();		
			mime.put("doc","application/msword");
			mime.put("pdf","application/pdf");
			mime.put("zip","application/zip");
			mime.put("rar","application/x-rar");
			mime.put("xml","application/xml");
			mime.put("gif","image/gif");
			mime.put("jpg","image/jpeg");
			mime.put("xls","application/vnd.ms-excel");
			mime.put("ppt","application/vnd.ms-powerpoint");
			mime.put("txt","text/plain");
			mime.put("csv","text/csv");
			mime.put("","application/octet-stream");		
		
		try{			
			OutputStream output = response.getOutputStream();
			FileInputStream fis = new FileInputStream(fileUrl);
			if ( fis!=null){				
				//写输出流
				String fileType = fileName.substring(fileName.lastIndexOf('.')+1);
				String tmpmime = (String)mime.get(fileType.toLowerCase());
				response.setContentType(tmpmime);			
				response.addHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(fileName, "utf-8"));
		
				byte[] b = new byte[1024];
				int i = 0;
				while((i = fis.read(b)) > 0){
					output.write(b, 0, i);
				}				
				//rpw.flushBuffer();
			}else{
				logger.debug("下载文件不存在！fileName="+fileName+"");
				//rpw.sendError(404);
			}	
			fis.close();
			output.close();
		}catch(Exception e){	
			throw new ServiceBizException("下载PDF失败！");
		}
	}
}
