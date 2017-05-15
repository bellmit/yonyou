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

import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.controller.basedata.PartInfoController;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtPartBaseDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;
import com.yonyou.dms.part.service.basedata.PartInfoManageAaService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件管理
 * @author ZhaoZ
 *@date 2017年3月22日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/partBaseInfoManage")
public class PartBaseInfoAaController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartBaseInfoAaController.class);

	@Autowired
	private PartInfoManageAaService partBaseInfoAaService;
	@Autowired
	private ExcelGenerator excelService;
	@Autowired
    private ExcelRead<TtPtPartBaseDcsDTO>  excelReadService;
	
    /**
	 * 配件信息维护查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPartBase",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartBase(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件信息维护查询=====");
		
		 return partBaseInfoAaService.findPartList(queryParams);
		
	}
	 
	 /**
	  * 配件信息维护更新
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/updateQueryInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateQueryInit(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====配件信息维护更新=====");
		TtPtPartBaseDcsPO tqs = TtPtPartBaseDcsPO.findById(id);
		return tqs.toMap();	
	}
	
	
	  /**
		 * 替换件信息查询
		 * @param queryParams
		 * @throws Exception
		 */
		@RequestMapping(value="/addPartSerch/{id}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> addPartSerch(@PathVariable(value = "id") BigDecimal id,@RequestParam Map<String, String> queryParams) {
			 logger.info("=====替换件信息查询=====");
			 TtPtPartBaseDcsPO tqs = TtPtPartBaseDcsPO.findById(id);
			 String oldPartCode="";
			 String orderPartCode = tqs.getString("OLD_PART_CODE");
			 if(!StringUtils.isNullOrEmpty(orderPartCode)){ 
				 String[] arrs = orderPartCode.split(",");
				 for(int a=0;a<arrs.length;a++){
					oldPartCode+="'"+arrs[a]+"',";
				 }		
					oldPartCode=oldPartCode.substring(0, oldPartCode.length()-1);			
					/*
					 * 查询替代配件
					 */
					 return partBaseInfoAaService.selectPart(oldPartCode);
		       }
			return  new ArrayList<>();
		}
		
		  /**
			* 车型信息查询
			* @param queryParams
			* @throws Exception
			 */
			@RequestMapping(value="/selectVhclMaterial/{id}",method = RequestMethod.GET)
			@ResponseBody
			public List<Map> selectVhclMaterial(@PathVariable(value = "id") BigDecimal id,@RequestParam Map<String, String> queryParams) {
				logger.info("=====车型信息=====");
				TtPtPartBaseDcsPO tqs = TtPtPartBaseDcsPO.findById(id);
				String code="";
				String modelCode = tqs.getString("MODEL_CODES");
				if(!StringUtils.isNullOrEmpty(modelCode)){ 
					String[] arrs = modelCode.split(",");
					for(int a=0;a<arrs.length;a++){
						code+="'"+arrs[a]+"',";
					}		
						code=code.substring(0, code.length()-1);			
						
						return partBaseInfoAaService.vhclMaterial(code);
				 }
					return  new ArrayList<>();
				}
			
			 /**
			 * 替换件信息查询
			 * @param queryParams
			 * @throws Exception
			 */
			@RequestMapping(value="/addPartSerchinfo",method = RequestMethod.GET)
			@ResponseBody
			public PageInfoDto addPartSerchinfo(@RequestParam Map<String, String> queryParams) {
				 logger.info("=====替换件信息查询=====");
				
				 return partBaseInfoAaService.selectPart(queryParams);
			
				
			}
			
			 /**
			 * 车型信息查询
			 * @param queryParams
			 * @throws Exception
			 */
			@RequestMapping(value="/addtmVhclMaterialGroupSearch",method = RequestMethod.GET)
			@ResponseBody
			public PageInfoDto addtmVhclMaterialGroupSearch(@RequestParam Map<String, String> queryParams) {
				 logger.info("=====车型信息查询=====");
				
				 return partBaseInfoAaService.addTmVhclMaterialGroupQuery(queryParams);
			 
			}
			
			/**
			 * 更新配件基础信息(保存)
			 * @param dto
			 * @param uriCB
			 * @return
			 */
			@RequestMapping(value = "/saveDeliveryOrder/{id}", method = RequestMethod.PUT)
			@ResponseBody
			public ResponseEntity<TtPtPartBaseDcsDTO> saveDeliveryOrder(@RequestBody TtPtPartBaseDcsDTO dto,UriComponentsBuilder uriCB,
					@PathVariable(value = "id") BigDecimal id){
				logger.info("==================经销商维护修改================");
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				partBaseInfoAaService.saveDeliveryOrder(dto,id,loginUser);
		        MultiValueMap<String,String> headers = new HttpHeaders();  
		        headers.set("Location", uriCB.path("/saveDeliveryOrder").buildAndExpand().toUriString());
		        return new ResponseEntity<>(headers, HttpStatus.CREATED);
			
			}
			
			/**
			 * BWP价格导入模板下载
			 */
			@RequestMapping(value="/downloadTemple",method = RequestMethod.GET)
			@ResponseBody
			public void downloadTemple(HttpServletRequest request,HttpServletResponse response) {
				logger.info("============BWP价格导入模板下载===============");
				Map<String, List<Map>> excelData = new HashMap<>();
				
				List<ExcelExportColumn> exportColumnList = new ArrayList<>();
				excelData.put("WBP价格导入模板",null);
				exportColumnList.add(new ExcelExportColumn("", "配件编号"));
				exportColumnList.add(new ExcelExportColumn("", "配件名称"));
				exportColumnList.add(new ExcelExportColumn("", "WBP价格"));
				excelService.generateExcel(excelData, exportColumnList, "WBP价格导入模板.xls", request, response);
			}
			
			/**
			 * WBP价格导入（临时表,导入校验）
			 * @param importFile
			 * @param forecastImportDto
			 * @param uriCB
			 * @return
			 * @throws Exception
			 */
			@RequestMapping(value = "/wbpImport", method = RequestMethod.POST)
			@ResponseBody
			public ArrayList<TtPtPartBaseDcsDTO> wbpImport(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,
					TtPtPartBaseDcsDTO forecastImportDto) throws Exception {
				 logger.info("============WBP价格导入（临时表,导入校验）===============");
				// 解析Excel 表格(如果需要进行回调)
					ImportResultDto<TtPtPartBaseDcsDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
							new AbstractExcelReadCallBack<TtPtPartBaseDcsDTO>(TtPtPartBaseDcsDTO.class));
					ArrayList<TtPtPartBaseDcsDTO> dataList = importResult.getDataList();
					if (dataList.size()>10000) {
						throw new ServiceBizException("导入出错,单次上传文件至多10000行");
					}
					ArrayList<TtPtPartBaseDcsDTO> list = new ArrayList<>();
					//根据用户ID清空临时表中的数据
					//获取当前用户
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TmpPtPartWbpDcsPO.delete(" CREATE_BY = ? ", loginInfo.getUserId());
					for (TtPtPartBaseDcsDTO rowDto : dataList) {			
						// 只有全部是成功的情况下，才执行数据库保存
						partBaseInfoAaService.insertTmpPtPartWbpDcs(rowDto);
					}
					LazyList<TmpPtPartWbpDcsPO> poList = TmpPtPartWbpDcsPO.find("CREATE_BY = ?", loginInfo.getUserId());
					List<TtPtPartBaseDcsDTO>  dto = partBaseInfoAaService.checkData(poList);
				
					if (dto != null) {
						list.addAll(dto);
					}
					if (list != null && !list.isEmpty()) {
						throw new ServiceBizException("导入出错,请见错误列表", list);
					}
					
					return null;
				
		}
			
			/**
			 * 配件主数据监控查询异常
			 * @param queryParams
			 * @throws Exception
			 */
			@RequestMapping(value="/queryPartExce",method = RequestMethod.GET)
			@ResponseBody
			public PageInfoDto queryPartExce(@RequestParam Map<String, String> queryParams) {
				 logger.info("=====配件主数据监控查询异常=====");
				
				 return partBaseInfoAaService.exceFindList(queryParams);
				
			}
			
			/**
		     * 查询状态
		     * @author ZhaoZ
		     * @date 2017年2月20日
		     * @return
		     */
		    @RequestMapping(value="/queryStatus",method = RequestMethod.GET)
		    @ResponseBody
		    public List<Map> queryStatus(){
		    	logger.info("=====查询状态=====");
		    	List<Map> status = partBaseInfoAaService.getStatus();
		        return status;
		    }
		    
		    /**
		     * 查询数据标记
		     * @author ZhaoZ
		     * @date 2017年2月20日
		     * @return
		     */
		    @RequestMapping(value="/queryDataFlag",method = RequestMethod.GET)
		    @ResponseBody
		    public List<Map> queryDataFlag(){
		    	logger.info("=====查询数据标记=====");
		    	List<Map> flag = partBaseInfoAaService.getDataFlag();
		        return flag;
		    }
		    
		    /**
			 * SAP配件主数据接收
			 * @param dto
			 * @param uriCB
			 * @return
			 */
			@RequestMapping(value = "/doSEDCSP12", method = RequestMethod.GET)
			@ResponseBody
			public ResponseEntity<TtPtPartBaseDcsDTO> doSEDCSP(){
				logger.info("==================SAP配件主数据接收================");
				return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
			
			}
			/**
			 * 配件主数据导入业务表
			 * @param dto
			 * @param uriCB
			 * @return
			 */
			@RequestMapping(value = "/doP12", method = RequestMethod.GET)
			@ResponseBody
			public ResponseEntity<TtPtPartBaseDcsDTO> doP(){
				logger.info("==================配件主数据导入业务表================");
				
				return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
			
			}
			
			/**
			 * 配件信息查询
			 * @param queryParams
			 * @throws Exception
			 */
			@RequestMapping(value="/queryPartBaseDlr",method = RequestMethod.GET)
			@ResponseBody
			public PageInfoDto queryPartBaseDlr(@RequestParam Map<String, String> queryParams) {
				 logger.info("=====配件信息查询=====");
				
				 return partBaseInfoAaService.partBaseDlr(queryParams);
				
			}
			
			/**
			 * 确认后查询待插入的数据
			 * @return
			 */
		  @RequestMapping(value = "/selectData", method = RequestMethod.GET)
		  @ResponseBody
		  public List<Map> selectData() {
				logger.info("============车辆召回管理（查询待插入数据） ===============");
				List<Map> list = partBaseInfoAaService.findTmpPtPartWbpDcsList();
				return list;
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
