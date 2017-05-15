package com.yonyou.dms.vehicle.controller.complaint;

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

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;
import com.yonyou.dms.vehicle.service.complaint.ComplaintDisposalDealerService;
//import com.yonyou.dms.vehicle.domains.PO.bigCustomer.TtBigCustomerReportApprovalPO;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *  客户投诉处理
 * @author ZhaoZ
 * @date 2017年4月17日
 */
@Controller
@TxnConn
@RequestMapping("/complaintDisposalDealer")
public class ComplaintDisposalDealerController {

	private static final Logger logger = LoggerFactory.getLogger(ComplaintDisposalDealerController.class);
	
	@Autowired
	private  ComplaintDisposalDealerService comService;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 客户投诉处理(总部)查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryComplaintDisposalDealer", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryComplaintDisposalOem(@RequestParam Map<String, String> queryParams){
		logger.info("=====客户投诉处理(总部)查询=====");
		return comService.complaintDisposalOem(queryParams);
		
	}
	
	/**
     * 投诉大类
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/compBtype",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> compBtype(@RequestParam Map<String, String> queryParams){
    	logger.info("=====投诉大类=====");
    	String sql = "SELECT CODE_ID,CODE_DESC FROM TC_CODE_DCS WHERE TYPE = 4035 ";
        return OemDAOUtil.findAll(sql, null);
    }
	
    /**
     * 投诉小类
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/{compBtype}/compStype",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> compStype(@PathVariable(value = "compBtype") String compBtype){
    	logger.info("=====投诉大类=====");
    	StringBuffer sql= new StringBuffer();
		sql.append(" SELECT CODE_ID,CODE_DESC FROM TC_CODE_DCS WHERE 1=1 ");
		if("40351001".equals(compBtype)){
			sql.append(" AND TYPE = 4036");
    	}else{
    		sql.append(" AND TYPE = 4050");
    	}
        return OemDAOUtil.findAll(sql.toString(), null);
    }
	
    /**
     * 客户投诉处理(服务站)处理初始化
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/disposalComplaintDealer/{compId}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> disposalComplaintDealer(@PathVariable(value = "compId") Long compId){
    	logger.info("=====客户投诉处理(服务站)处理初始化=====");
    	Map<String, Object> complaintMap = comService.getComplaintById(compId);
		return complaintMap;
    }
    
    /**
     * 客户车辆信息
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/getVicheil/{compId}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getVicheil(@PathVariable(value = "compId") Long compId){
    	logger.info("=====插入车辆信息=====");
    	PageInfoDto complaintVicheil = comService.getVicheilbyCompId(compId); 
		return complaintVicheil;
    }
	
    
    /**
     * 获取处理历史信息
     * @author ZhaoZ
     * @date 2017年4月11日
     * @return
     */
    @RequestMapping(value="/comPlaintsHistory/{compId}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto comPlaintsHistory(@PathVariable(value = "compId") Long compId){
    	logger.info("=====获取处理历史信息=====");
    	String sql = "SELECT * FROM tt_cr_complaints_history_dcs WHERE COMP_ID = "+compId+"";
		return OemDAOUtil.pageQuery(sql, null);
    }
    
    
    /**
	 * 保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/saveComplaint/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtCrComplaintsDcsDTO> saveDeliveryOrder(@RequestBody TtCrComplaintsDcsDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "id") Long id){
		logger.info("==================保存================");
		comService.saveDeliveryOrder(dto,id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveComplaint").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	 	/**
		 * 申请结案
		 * @param dto
		 * @param uriCB
		 * @return
		 */
		@RequestMapping(value = "/updateComplaint/{id}", method = RequestMethod.PUT)
		@ResponseBody
		public ResponseEntity<TtCrComplaintsDcsDTO> updateComplaint(@RequestBody TtCrComplaintsDcsDTO dto,UriComponentsBuilder uriCB,
				@PathVariable(value = "id") Long id){
			logger.info("==================保存================");
			comService.updateComplaint(dto,id);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/updateComplaint").buildAndExpand().toUriString());
	        return new ResponseEntity<>(headers, HttpStatus.CREATED);
		
		}
		
		/**
		 * 客户投诉处理(总部) 不需回访查询
		 * @param queryParams
		 * @return
		 */
		@RequestMapping(value = "/queryComplaintDisposal", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryComplaintDisposal(@RequestParam Map<String, String> queryParams){
			logger.info("=====客户投诉处理(总部) 不需回访查询=====");
			return comService.complaintDisposal(queryParams);
			
		}
	
		/**
	     * 客户投诉处理(总部)不需回访处理 页面初始化
	     * @author ZhaoZ
	     * @date 2017年4月11日
	     * @return
	     */
	    @RequestMapping(value="/disposalDisposal/{compId}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> disposalDisposal(@PathVariable(value = "compId") Long compId){
	    	logger.info("=====客户投诉处理(总部)不需回访处理 页面初始化=====");
	    	Map<String, Object> complaintMap = comService.disposalDisposal(compId);
			return complaintMap;
	    }
	    
	    /**
	     * 客户投诉处理(总部)不需回访处理 页面初始化
	     * @author ZhaoZ
	     * @date 2017年4月11日
	     * @return
	     */
	    @RequestMapping(value="/disposalDisposal1/{compId}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto disposalDisposal1(@PathVariable(value = "compId") Long compId){
	    	logger.info("=====客户投诉处理(总部)不需回访原因 =====");
			return comService.disposalDisposal1(compId);
	    }
	    /**
		 * 客户投诉处理(总部) 不需回访查询DLR
		 * @param queryParams
		 * @return
		 */
		@RequestMapping(value = "/queryComplaintDisposalDLR", method = RequestMethod.GET)
		@ResponseBody
		public PageInfoDto queryComplaintDisposalDLR(@RequestParam Map<String, String> queryParams){
			logger.info("=====客户投诉处理(总部) 不需回访查询=====");
			return comService.complaintDisposalList(queryParams);
			
		}
		
		/**
		 * 客户投诉查询导出
		 * @param queryParams
		 * @return
		 */
		@RequestMapping(value="/ComplaintDisposalDownLoad",method = RequestMethod.GET)
		@ResponseBody
		public void excelExport (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
				HttpServletResponse response ){
			logger.info("============客户投诉查询导出===============");
			
			List<Map> customerList = comService.excelExportList(queryParams);
			List<ExcelExportColumn> exportColumnList = new ArrayList<>();
			Map<String, List<Map>> excelData = new HashMap<>();
			excelData.put("返利使用明细查询(Dealer)下载",customerList);
			exportColumnList.add(new ExcelExportColumn("COMP_CODE", "投诉编号"));
			exportColumnList.add(new ExcelExportColumn("LINK_MAN", "客户名称"));
			exportColumnList.add(new ExcelExportColumn("TEL", "联系电话"));
			exportColumnList.add(new ExcelExportColumn("OWN_ORG_ID", "区域"));
			exportColumnList.add(new ExcelExportColumn("PROVINCE", "省份"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "投诉经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "投诉经销商"));
			exportColumnList.add(new ExcelExportColumn("COMP_LEVEL", "投诉等级"));
			exportColumnList.add(new ExcelExportColumn("COMP_NATURE", "投诉性质"));
			exportColumnList.add(new ExcelExportColumn("COMP_BTYPE", "投诉大类"));
			exportColumnList.add(new ExcelExportColumn("COMP_STYPE", "投诉小类"));
			exportColumnList.add(new ExcelExportColumn("COMP_DATE", "投诉日期"));
			exportColumnList.add(new ExcelExportColumn("STATUS", "投诉状态"));
			exportColumnList.add(new ExcelExportColumn("ALLOT_DEALER_CODE", "分配经销商代码"));
			exportColumnList.add(new ExcelExportColumn("ALLOT_DEALER", "分配经销商"));
			exportColumnList.add(new ExcelExportColumn("IS_RETURN", "不需回访"));
			exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "最后处理日期"));

			excelService.generateExcel(excelData, exportColumnList, "返利使用明细查询(Dealer)下载.xls", request, response);
		}
}
