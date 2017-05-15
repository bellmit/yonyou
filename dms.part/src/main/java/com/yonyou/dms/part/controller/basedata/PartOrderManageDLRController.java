package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.OrderRegisterDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtPartBaseDcsDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtOrderDetailDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtOrderDetailDcsPO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtPtOrderDetailMopDcsPO;
import com.yonyou.dms.part.service.basedata.PartOrderDLRService;
import com.yonyou.dms.part.service.basedata.PartOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件订货计划
 * @author ZhaoZ
 * @date 2017年3月24日 
 */
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/partOrderManageDLR")
public class PartOrderManageDLRController {
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
   
    private static String partCodes = "";
    private static Integer orderTypeA = null;
    @Autowired
	private PartOrderDLRService partDLRService;
    @Autowired
	private ExcelGenerator excelService;
    
    @Autowired
    private ExcelRead<TtPtOrderDetailDcsDTO>  excelReadService;
   
    /**
	 * 配件订货计划查询信息
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryOrderPlan",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderPlan(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订货计划查询信息=====");
		
		 return partDLRService.queryOrderPlanInfo(queryParams);
		
	}
	
	/**
	 * 删除
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/deleteOrderPlan/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> deleteOrderPlan(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==================重置================");
		partDLRService.delete(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/deleteOrderPlan").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 新增/修改 信息回显
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/getOrderPlan/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderPlan(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id){
		logger.info("==================新增/修改 信息回显================");
		
		TtPtOrderDcsPO ttPtOrderPO = TtPtOrderDcsPO.findById(id);
		if(ttPtOrderPO!=null){
			orderTypeA = ttPtOrderPO.getInteger("ORDER_TYPE");
			return ttPtOrderPO.toMap();
		}
		return null;
	}
	
	 /**
	  * 配件订货计划查询信息
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/getFuJian/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getFuJian(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====配件订货计划查询信息=====");
		
		 String sql = "SELECT * FROM TT_PT_ORDER_ATT_DCS WHERE ORDER_ID = "+id+"";
		 return OemDAOUtil.pageQuery(sql, null);
	}
	
	/**
	  * 订货配件清单信息
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/getOrderPartInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto getOrderPartInfo(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====订货配件清单信息=====");
		 LazyList<TtPtOrderDetailDcsPO> list  = TtPtOrderDetailDcsPO.find("ORDER_ID = ?", id);
		 partCodes = "";
		 for(TtPtOrderDetailDcsPO po:list){
			 partCodes+=po.getString("PART_CODE")+",";
		 }
		 String sql = "SELECT * FROM TT_PT_ORDER_DETAIL_DCS WHERE ORDER_ID = "+id+"";
		 return OemDAOUtil.pageQuery(sql, null);
	}
	
	/**
	 * 查询配件清单
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryOrderPartList",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderPartList(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====查询配件清单=====");
		
		 return partDLRService.queryOrderList(queryParams,partCodes);
		
	}
	
	/**
	 * 配件订单补登记查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryInvoice",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInvoice(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单补登记查询=====");
		
		 return partDLRService.queryInvoiceList(queryParams);
		
	}
	
	
	/**
	 * 接口
	 * 配件订单补登记明细查询
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/invoiceDetailQuery/{opInvvbeIn}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getOrderPlan(@RequestParam Map<String, String> queryParams,@PathVariable(value = "opInvvbeIn") String opInvvbeIn){
		logger.info("==================配件订单补登记明细查询================");
		
	
		return null;
	}
	
	/**
	 * 配件订单补登记明细查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/invoiceQuery/{opInvvbeIn}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto invoiceQuery(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单补登记明细查询=====");
		
		 return new PageInfoDto();
		
	}
	
	/**
	 * 配件订单补登记 
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/orderRegister", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<OrderRegisterDTO> orderRegister(@RequestBody OrderRegisterDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================配件订单补登记 ================");
		partDLRService.register(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/orderRegister").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 登记状态
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> status(){
		logger.info("==================登记状态================");
		 StringBuffer pasql = new StringBuffer();
		 pasql.append("SELECT tc.code_id codeId, CASE WHEN tc.code_id LIKE 10041001 THEN '已登记' WHEN tc.code_id LIKE 10041002  THEN '未登记'  END typeName  \n  ");
		 pasql.append("FROM tc_code_dcs tc WHERE TYPE=1004 \n ");
		 List<Map> list = OemDAOUtil.findAll(pasql.toString(), null);
		 return list;
		
	}
	
	/**
	 * 操作代码模板下载
	 */
	@RequestMapping(value="/downloadTemple",method = RequestMethod.GET)
	@ResponseBody
	public void downloadTemple(HttpServletRequest request,HttpServletResponse response) {
		logger.info("============订货配件清单导入===============");
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("订货配件清单导入",null);
		exportColumnList.add(new ExcelExportColumn("", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("", "配件名称(非必填)"));
		exportColumnList.add(new ExcelExportColumn("", "订货数量"));
		
		excelService.generateExcel(excelData, exportColumnList, "订货配件清单导入.xls", request, response);
	}
	
	/**
	 * 清单查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryExportList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryExportList() {
		 logger.info("=====清单查询=====");
		 String sql = " SELECT * FROM TMP_PT_ORDER_DETAIL_dcs TMP";
		 return  OemDAOUtil.findAll(sql, null);
	}
	
	/**
	 * 导入临时表信息
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/exporOrderPartData", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<TtPtOrderDetailDcsDTO> exporOrderPartData(@RequestParam(value = "file") MultipartFile importFile,
			TtPtOrderDetailDcsDTO dto) throws Exception {
		 	logger.info("============导入临时表信息===============");
		 	String partModel = "";
			if (OemDictCodeConstants.PART_ORDER_TYPE_02.toString().equals(dto.getOrderType2()) ||
					OemDictCodeConstants.PART_ORDER_TYPE_03.toString().equals(dto.getOrderType2()) ||
					OemDictCodeConstants.PART_ORDER_TYPE_04.toString().equals(dto.getOrderType2())) { // s 否   m 是
//				1、	选择EO\SO\VOR 订单类型，系统将只能够订购选择添加配件类别为“常规零部件”的配件至配件清单和 非SJV来源的配件清单
				partModel = OemDictCodeConstants.PART_TYPE_01.toString();
			} else if (OemDictCodeConstants.PART_ORDER_TYPE_05.toString().equals(dto.getOrderType2())) {
//				2、	选择TO 订单类型，系统将只能够订购选择添加配件类别为“第三方”的配件至配件清单；
				partModel = OemDictCodeConstants.PART_TYPE_02.toString();
			} else if (OemDictCodeConstants.PART_ORDER_TYPE_01.toString().equals(dto.getOrderType2())||
					OemDictCodeConstants.PART_ORDER_TYPE_08.toString().equals(dto.getOrderType2())) {
//				3、	选择E-CODE 2\ S-CODE 2 订单类型，系统将只能够订购选择添加配件类别为“底盘号相关”的配件至配件清单；
				partModel = OemDictCodeConstants.PART_TYPE_06.toString();
			} else if (OemDictCodeConstants.PART_ORDER_TYPE_06.toString().equals(dto.getOrderType2())) {
//				4、	选择WO 订单类型，系统将能够订购选择添加所有配件类别的配件清单；
				partModel = OemDictCodeConstants.PART_TYPE_01.toString()+","+OemDictCodeConstants.PART_TYPE_02.toString()+","+OemDictCodeConstants.PART_TYPE_06.toString();
			}
		 	
		 	// 解析Excel 表格(如果需要进行回调)
			ImportResultDto<TtPtOrderDetailDcsDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TtPtOrderDetailDcsDTO>(TtPtOrderDetailDcsDTO.class));
			ArrayList<TtPtOrderDetailDcsDTO> dataList = importResult.getDataList();
			if (dataList.size()>10000) {
				throw new ServiceBizException("导入出错,单次上传文件至多10000行");
			}
			ArrayList<TtPtOrderDetailDcsDTO>  list = new ArrayList<>();
			//根据用户ID清空临时表中的数据
			//获取当前用户
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TmpPtOrderDetailDcsPO.delete(" CREATE_BY = ? ", loginInfo.getUserId());
			for (TtPtOrderDetailDcsDTO rowDto : dataList) {			
				// 只有全部是成功的情况下，才执行数据库保存
				partDLRService.insertTmpPtOrderDetailDcs(rowDto);
			}
			LazyList<TmpPtOrderDetailDcsPO> poList = TmpPtOrderDetailDcsPO.find("CREATE_BY = ?", loginInfo.getUserId());
			List<TtPtOrderDetailDcsDTO>  tpoDdto = partDLRService.checkData(poList,partModel,dto.getPartCodes());
		
			if (tpoDdto != null) {
				list.addAll(tpoDdto);
			}
			if (list != null && !list.isEmpty()) {
				throw new ServiceBizException("导入出错,请见错误列表", list);
			}
			
			return null;
		
		}
	
	 /**
	 * WBP价格导入
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtPtPartBaseDcsDTO> importWbp(UriComponentsBuilder uriCB){
		logger.info("==================WBP价格导入================");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		LazyList<TmpPtPartWbpDcsPO> poList = TmpPtPartWbpDcsPO.find("CREATE_BY = ?", loginInfo.getUserId());
		for(TmpPtPartWbpDcsPO po:poList){
			TtPtPartBaseDcsPO tqs =TtPtPartBaseDcsPO.findFirst("PART_CODE = ?", po.getString("PART_CODE"));
        	tqs.setDouble("WBP",new Double(po.getString("WBP")));
        	tqs.setLong("UPDATE_BY",loginInfo.getUserId());
        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	long time= System.currentTimeMillis();
        	try {
        		Date date = sdf.parse(sdf.format(new Date(time)));
        		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
        		tqs.setTimestamp("UPDATE_DATE",st);
        	} catch (ParseException e) {
        		e.printStackTrace();
        	} 	
        	tqs.saveIt();
		}
		MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/import").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	
}
