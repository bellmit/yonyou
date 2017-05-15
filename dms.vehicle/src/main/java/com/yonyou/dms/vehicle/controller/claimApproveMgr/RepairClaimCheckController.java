package com.yonyou.dms.vehicle.controller.claimApproveMgr;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
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

import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrClaimPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.service.claimApproveMgr.RepairClaimCheckService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author ZhaoZ
 * @date 2017年4月25日
 */
@Controller
@TxnConn
@RequestMapping("/repairClaimCheck")
public class RepairClaimCheckController {

	private static final Logger logger = LoggerFactory.getLogger(RepairClaimCheckController.class);
	
	@Autowired
	private  RepairClaimCheckService claimService;
	@Autowired
	private ExcelGenerator excelService;
	
	private static Long claim;
	
	/**
	 * 保修索赔申请审核查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/repairClaimQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto repairClaimQuery(@RequestParam Map<String, String> queryParams){
		logger.info("=====保修索赔申请审核查询=====");
		return claimService.repairClaimQueryList(queryParams);
		
	}
	
	/**
     * 索赔类型
     * @author ZhaoZ
     * @date 2017年4月25日
     * @return
     */
    @RequestMapping(value="/getClaimType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getClaimType(@RequestParam Map<String, String> queryParams){
    	logger.info("=====索赔类型=====");
    	String sql = "SELECT CLAIM_TYPE_CODE,CLAIM_TYPE FROM tt_wr_claimtype_dcs WHERE CLAIM_CATEGORY = 40281001 ";
        return OemDAOUtil.findAll(sql, null);
    }
	
    /**
     * 索赔类型全部
     * @author ZhaoZ
     * @date 2017年4月25日
     * @return
     */
    @RequestMapping(value="/getClaimAllType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getClaimAllType(@RequestParam Map<String, String> queryParams){
    	logger.info("=====索赔类型=====");
    	String sql = "SELECT CLAIM_TYPE_CODE,CLAIM_TYPE FROM tt_wr_claimtype_dcs WHERE CLAIM_CATEGORY = 40281001 OR CLAIM_CATEGORY = 40281002 ";
        return OemDAOUtil.findAll(sql, null);
    }
    
   
  
	/**
	 * 进入保修申请审核页面初始化
	 * @param claimId
	 * @return
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "/repairClaimApproveInit/{claimId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> repairClaimApproveInit(@PathVariable(value = "claimId") Long claimId){
		logger.info("============进入保修申请审核页面初始化===============");
		claim = claimId;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(loginUser);
		Long userId = loginUser.getUserId();//登陆用户ID
		TcUserPO userpo =TcUserPO.findById(userId);
		LazyList<TcUserPO> list = userpo.findAll();
		if(list !=null && list.size()>0){
			userpo = list.get(0);
		}
		String authCode = String.valueOf(userpo.getString("APPROVAL_LEVEL_CODE"));//审核级别
		Map<String, Object> map = claimService.claimInfo(claimId,oemCompanyId);
		map.put("authCode", authCode);
		
		List<Map> listLa = claimService.queryRepairPart(claimId).getRows();
		List<Map> listLa1 = claimService.queryClainLabour(claimId).getRows();
		//工时
		Double sum = 0.00;
		for(int count=0;count<listLa.size();count++){
			sum+= Double.parseDouble(listLa.get(count).get("AMOUNT").toString());
		}
		//配件金额
		Double labourNumVal = 0.00;
		for(int count=0;count<listLa1.size();count++){
			labourNumVal+= Double.parseDouble(listLa1.get(count).get("LABOUR_NUM").toString());
		}
		//管理费
		double partPay = Double.parseDouble(map.get("PART_PAY").toString())/100*sum;
		//保留两位小数
		String result = String .format("%.2f",partPay);
		String result1 = String .format("%.2f",labourNumVal);
		map.put("labourNumVal", result1);
		map.put("partFeeVal", sum);
		map.put("partPay", result);
		return map;
	}
	
	/**
	 * 查询索赔单下的其他项目列表
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryOtherItemList/{claimId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOtherItemList(@PathVariable(value = "claimId") Long claimId){
		logger.info("=====查询索赔单下的其他项目列表=====");
		return claimService.queryOtherItem(claimId);
		
	}
	/**
	 * 获得索赔单的零部件信息列表
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryRepairPartList/{claimId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryRepairPartList(@PathVariable(value = "claimId") Long claimId){
		logger.info("=====获得索赔单的零部件信息列表=====");
		return claimService.queryRepairPart(claimId);
		
	}
	/**
	 * 查询索赔单与工时关系
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryClainLabourList/{claimId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryClainLabourList(@PathVariable(value = "claimId") Long claimId){
		logger.info("=====查询索赔单与工时关系=====");
		return claimService.queryClainLabour(claimId);
		
	}
	
	/**
	 * 查询索赔单的索赔情形
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryClainCaseList/{claimId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryClainCaseList(@PathVariable(value = "claimId") Long claimId){
		logger.info("=====查询索赔单的索赔情形=====");
		return claimService.queryClainCase(claimId);
		
	}
	
	/**
	 * 保修索赔单申请审核
	 * @param dto
	 * @param uriCB
	 * @return
	 * @throws Exception 
	 * @throws ServiceBizException 
	 */
	@RequestMapping(value = "/approveDo/{claimId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrClaimDcsDTO> approveDo(@RequestBody TtWrClaimDcsDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "claimId") Long claimId) throws ServiceBizException, Exception{
		logger.info("==================保修索赔单申请审核================");
		claimService.approve(dto,claimId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/approveDo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 查询索赔单审批历史信息
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/showApproveInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto showApproveInfo(){
		logger.info("=====查询索赔单审批历史信息=====");
		return claimService.claimHistoryQuery(claim);
		
	}
	
	
	/**
	 * 查询附件信息
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/doSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto doSearch(){
		logger.info("=====查询附件信息=====");
		return claimService.doSearchFujian(claim);
		
	}
	
	/**
	 * 索赔单状态跟踪--查询操作
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryOrderInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderInfo(@RequestParam Map<String, String> queryParams){
		logger.info("=====索赔单状态跟踪查询操作=====");
		return claimService.orderInfoList(queryParams);
		
	}
	
	
	/**
	 * 导出
	 * @param queryParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excelExport",method = RequestMethod.GET)
	@ResponseBody
	public void excelExport (@RequestParam Map<String, String> queryParams, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============导出===============");
		
		List<Map> customerList = claimService.queryClaimOrderList(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		int i = 1;
		for (Map map:customerList) {
			map.put("LINE_NO",i++);
		}
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("索赔状态跟踪查询导出",customerList);
		exportColumnList.add(new ExcelExportColumn("LINE_NO", "序号"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_NO", "索赔单号"));
		exportColumnList.add(new ExcelExportColumn("RO_NO", "维修工单号"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_TYPE", "索赔类型"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CLAIM_CATEGORY", "类别",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SUBMIT_COUNT", "提报次数"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "索赔申请日期"));
		exportColumnList.add(new ExcelExportColumn("PART_FEE", "零部件费用"));
		exportColumnList.add(new ExcelExportColumn("LABOUR_FEE", "人工费用"));
		exportColumnList.add(new ExcelExportColumn("OTHER_AMOUNT", "其他费用"));
		exportColumnList.add(new ExcelExportColumn("ALL_AMOUNT", "总费用"));

		excelService.generateExcel(excelData, exportColumnList, "召回完成统计明细下载.xls", request, response);
	}
	
	/**
	 * 进入保修申请审核页面初始化
	 * @param claimId
	 * @return
	 */
	@RequestMapping(value = "/repairClaimApprove/{dealerCode}/{claimNo}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> repairClaimApprove(@PathVariable(value = "dealerCode") String dealerCode,
			@PathVariable(value = "claimNo") String claimNo){
		logger.info("============进入保修申请审核页面初始化===============");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(loginUser);
		Map map = claimService.claimInfo(oemCompanyId, claimNo, dealerCode);
		return map;
	}
	
	/**
	 * 进入保修申请审核
	 * @param claimId
	 * @return
	 */
	@RequestMapping(value = "/repairClaimApprove1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> repairClaimApprove1(@RequestParam Map<String, String> queryParams){
		logger.info("============进入保修申请审核页面初始化===============");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(loginUser);
		Map map = claimService.claimInfos(oemCompanyId, queryParams);
		return map;
	}
	
	
	/**
	 * 进入保修申请审核页面
	 * @param claimId
	 * @return
	 */
	@RequestMapping(value = "/repairClaim", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto repairClaim(@RequestParam Map<String, String> queryParams){
		logger.info("============进入保修申请审核页面初始化===============");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Long oemCompanyId = OemDAOUtil.getOemCompanyId(loginUser);
		return claimService.repairClaimList(oemCompanyId,queryParams);
	}
	/**
	 * 保修索赔单申请审核
	 * @param dto
	 * @param uriCB
	 * @return
	 * @throws Exception 
	 * @throws ServiceBizException 
	 */
	@RequestMapping(value = "/deductDo", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrClaimDcsDTO> deductDo(@RequestBody TtWrClaimDcsDTO dto,UriComponentsBuilder uriCB) throws ServiceBizException{
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		logger.info("==================保修索赔单申请审核================");
		Long userId = loginUser.getUserId();//登陆用户ID
		String DEDUCT_FEE=CommonUtils.checkNull(dto.getDeductFee());   //
		String deductRemark=CommonUtils.checkNull(dto.getDeductRemark().trim());   //意见：
		boolean flag = false;
		if(!StringUtils.isNullOrEmpty(claim)){
			TtWrClaimPO conditionPOOld = TtWrClaimPO.findById(claim);
			conditionPOOld.setInteger("IS_DEL",0);
			if(conditionPOOld!=null){//不可以再次审核，该索赔单已经审核过
				conditionPOOld.setDouble("DEDUCT_FEE",Double.valueOf(DEDUCT_FEE));//DEDUCT_REMARK
				conditionPOOld.setString("DEDUCT_REMARK",deductRemark);
				conditionPOOld.setLong("UPDATE_BY",userId);
				SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				long time= System.currentTimeMillis();
				try {
					Date date = sdf.parse(sdf.format(new Date(time)));
					java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
					conditionPOOld.setTimestamp("UPDATE_DATE",st);
				} catch (ParseException e) {
					e.printStackTrace();
				} 
				flag = conditionPOOld.saveIt();
				if(!flag){
					throw new ServiceBizException("抵扣失败!");
				}
			}
		}
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deductDo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
}
