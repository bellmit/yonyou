package com.yonyou.dms.manage.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerOrgRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.domains.PO.file.FileUploadInfoPO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerDetailedinfoDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerMaintainImportDTO;
import com.yonyou.dms.manage.domains.DTO.basedata.FsFileuploadDto;
import com.yonyou.dms.manage.domains.PO.basedata.TmDealerGroupPO;
import com.yonyou.dms.manage.domains.PO.basedata.TmpDealerBussPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmDealerBussPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmDealerEdPo;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmVsDealerShowroomPO;
import com.yonyou.dms.manage.domains.PO.dealerManager.TtCompanyDetailPO;
import com.yonyou.dms.manage.service.dealersManager.DealerInfoManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 经销商管理
 * @author ZhaoZ
 *@date 2017年2月24日
 */
@Controller
@TxnConn
@RequestMapping("/queryDealers")
public class DealerInfoManageController {
	
	private static final Logger logger = LoggerFactory.getLogger(DealerInfoManageController.class);
	
	@Autowired
	private DealerInfoManageService dealerService;
	@Autowired
	private ExcelGenerator excelService;
    @Autowired
    SystemParamService paramService;   
	@Autowired
    private ExcelRead<DealerMaintainImportDTO>  excelReadService;
	
	/**
	 * 经销商信息查询(dlr)
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/dlrQueryDealerInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto dlrQueryDealerInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====查询经销商信息=====");
		
		 return dealerService.getDealers(queryParams);
		
	}
	
	/**
	 * 经销商维护  新增时弹出公司信息
	 * @param params
	 * @return
	 */
	@RequestMapping(value="/queryCom",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryCom(@RequestParam Map<String, String> params){
		logger.info("=====新增时经销商信息弹出页面=====");
		return dealerService.queryCom(params);	
	}
	
	@RequestMapping(value="/queryComAll",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryComAll(@RequestParam Map<String, String> params){
		logger.info("=====新增时经销商信息弹出页面=====");
		return dealerService.queryComAll(params);	
	}
	
	/**
	 * 经销商基本信息查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryDealerInfos",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDealerInfos(@RequestParam Map<String, String> queryParams) {
		logger.info("============查询经销商基本信息===============");
		 PageInfoDto dealerrInfos = dealerService.getDealerInfos(queryParams);
		
		return dealerrInfos;
		
	}
	
	@RequestMapping(value="/download/series",method = RequestMethod.GET)
	@ResponseBody
	public void downloadSeries ( HttpServletRequest request,HttpServletResponse response ){
		logger.info("============车系信息下载===============");
		List<Map> dealerList = dealerService.findSeries();
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		String name = "车系信息下载.xls";
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("车系信息",dealerList);
		exportColumnList.add(new ExcelExportColumn("GROUP_CODE", "车系代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车系名称"));
		excelService.generateExcel(excelData, exportColumnList, name, request, response);
	}
	
	/**
	 * 经销商维护下载 / 经销商基本信息查询下载
	 * @param queryParams
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/download/maintainExcel/{table}",method = RequestMethod.GET)
	@ResponseBody
	public void dealerInfoDownload (@RequestParam Map<String, String> queryParams,@PathVariable String table, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============经销商信息下载===============");
		queryParams.put("table", table);
		List<Map> dealerList = dealerService.findDealerInfousDownload(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date currentTime = new Date();
		String name = "经销商信息下载"+sdf.format(currentTime)+".xls";
		
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商信息",dealerList);
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("PROVINCE", "省"));
		exportColumnList.add(new ExcelExportColumn("CITY", "市"));
		exportColumnList.add(new ExcelExportColumn("", "区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商简称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商注册名称"));
		exportColumnList.add(new ExcelExportColumn("MARKETING", "经销商英文名称"));
		exportColumnList.add(new ExcelExportColumn("NETSTATE", "入网状态"));
		exportColumnList.add(new ExcelExportColumn("RANGES", "经销商规模"));
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "销售区域(大区)"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售区域(小区)"));
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME_A", "售后区域(大区)"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_A", "售后区域(小区)"));
		exportColumnList.add(new ExcelExportColumn("ADDRESS", "详细地址"));
		exportColumnList.add(new ExcelExportColumn("ZIP_CODE", "邮政编码"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "销售热线"));
		exportColumnList.add(new ExcelExportColumn("LINK_MAN_MOBILE", "服务电话"));
		exportColumnList.add(new ExcelExportColumn("FAX_NO", "传真"));
		exportColumnList.add(new ExcelExportColumn("EMAIL", "电子邮件"));
		exportColumnList.add(new ExcelExportColumn("LINK_MAN", "业务负责人姓名"));
		exportColumnList.add(new ExcelExportColumn("PHONE", "业务负责人电话"));
		exportColumnList.add(new ExcelExportColumn("LINK_MAN_MOBILE", "业务负责人手机"));
		//展厅  4个
		exportColumnList.add(new ExcelExportColumn("ROOM_ADDRESS1", "经销商展厅地址1"));
		exportColumnList.add(new ExcelExportColumn("ROOM_ADDRESS2", "经销商展厅地址2"));
		exportColumnList.add(new ExcelExportColumn("ROOM_ADDRESS3", "经销商展厅地址3"));
		exportColumnList.add(new ExcelExportColumn("ROOM_ADDRESS4", "经销商展厅地址4"));
		
		exportColumnList.add(new ExcelExportColumn("COMPANY_LEVEL", "经销店等级"));
		exportColumnList.add(new ExcelExportColumn("LEGAL_REPRESENTATIVE", "法定代表人"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NO", "组织机构代码"));
		exportColumnList.add(new ExcelExportColumn("LICENSED_BRANDS", "授权品牌"));
		exportColumnList.add(new ExcelExportColumn("BUILD_STATUS", "店面建设状态"));
		exportColumnList.add(new ExcelExportColumn("COMPLETION_DATE", "拟完工日期"));
		exportColumnList.add(new ExcelExportColumn("START_DATE", "开业时间"));
		exportColumnList.add(new ExcelExportColumn("SHOWRROM_AREA", "展厅面积"));
		exportColumnList.add(new ExcelExportColumn("SHOWROOM_WIDTH", "展厅面宽"));
		exportColumnList.add(new ExcelExportColumn("SHOW_CARS", "展车数量"));
		exportColumnList.add(new ExcelExportColumn("SERVICE_NUM", "维修工位"));
		exportColumnList.add(new ExcelExportColumn("MACHINE_REPAIR", "机修工位"));
		exportColumnList.add(new ExcelExportColumn("SHEET_SPRAY", "钣喷工位"));
		exportColumnList.add(new ExcelExportColumn("PREFLIGHT_NUM", "预检工位"));
		exportColumnList.add(new ExcelExportColumn("RESERVED_NUM", "预留工位"));
		exportColumnList.add(new ExcelExportColumn("STOP_CAR", "停车位数量"));
		exportColumnList.add(new ExcelExportColumn("LAND", "土地租赁/购买"));
		exportColumnList.add(new ExcelExportColumn("LAND_NATURE", "土地性质 "));
		exportColumnList.add(new ExcelExportColumn("PARTS_AREA", "售后配件仓库面积"));
		exportColumnList.add(new ExcelExportColumn("DANGER_AREA", "危险库面积"));
		exportColumnList.add(new ExcelExportColumn("END_DATE", "终止授权时间 "));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "经销商所属集团"));
		exportColumnList.add(new ExcelExportColumn("RATE", "经销商的股权比例 "));
		exportColumnList.add(new ExcelExportColumn("REGISTERED_CAPITAL", "注册资金(万元)"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_INVESTORS", "投资人"));
		exportColumnList.add(new ExcelExportColumn("INVESTORS_TEL", "投资人电话 "));
		exportColumnList.add(new ExcelExportColumn("INVESTORS_EMIAL", "投资人邮箱"));
		exportColumnList.add(new ExcelExportColumn("BRANDS", "涉及品牌"));
		exportColumnList.add(new ExcelExportColumn("MAINTAINABILITY", "月平均维修能力(台次)"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "创建时间"));
		exportColumnList.add(new ExcelExportColumn("DEALER_AUDIT_STATUS", "审核状态"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_AUDIT_DATE", "最近审核时间 "));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "最近申请时间"));
		exportColumnList.add(new ExcelExportColumn("", "规模"));
		exportColumnList.add(new ExcelExportColumn("IS_EC", "是否官网"));
		exportColumnList.add(new ExcelExportColumn("CC_CODE", "CG Code/ CJ Code"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_PHONE", "办公电话"));
		exportColumnList.add(new ExcelExportColumn("OPEN_TIME", "营业时间"));
		exportColumnList.add(new ExcelExportColumn("OPEN_RANGE", "营业范围"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE1", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME1", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER1", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE1", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE1", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL1", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE1", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT1", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS1", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE2", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME2", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER2", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE2", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE2", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL2", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE2", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT2", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS2", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE3", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME3", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER3", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE3", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE3", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL3", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE3", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT3", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS3", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE4", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME4", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER4", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE4", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE4", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL4", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE4", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT4", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS4", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE5", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME5", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER5", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE5", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE5", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL5", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE5", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT5", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS5", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE6", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME6", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER6", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE6", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE6", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL6", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE6", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT6", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS6", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE7", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME7", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER7", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE7", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE7", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL7", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE7", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT7", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS7", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE8", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME8", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER8", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE8", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE8", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL8", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE8", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT8", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS8", "开户行地址"));
		
		exportColumnList.add(new ExcelExportColumn("POST_TYPE9", "职务"));
		exportColumnList.add(new ExcelExportColumn("NAME9", "姓名 "));
		exportColumnList.add(new ExcelExportColumn("GENDER9", "性别"));
		exportColumnList.add(new ExcelExportColumn("MOBILE9", "手机"));
		exportColumnList.add(new ExcelExportColumn("TELEPHONE9", "座机"));
		exportColumnList.add(new ExcelExportColumn("EMAIL9", "邮箱"));
		exportColumnList.add(new ExcelExportColumn("APPROVE9", "认证"));
		exportColumnList.add(new ExcelExportColumn("BANK_ACCOUNT9", "银行账号"));
		exportColumnList.add(new ExcelExportColumn("BANK_ADDRESS9", "开户行地址"));
		excelService.generateExcel(excelData, exportColumnList, name, request, response);
		
	}
	
	
	/**
	 * 详细查询
	 * @param transferId
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findDetailById(@PathVariable(value = "id") Long dealerId){
		TmDealerEdPo po = dealerService.getDealerDetailedInfo(dealerId);// 经销商PO
		
		return po.toMap();   	
    }
	
	/**
	 * 经销商信息查询(dlr)
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/dlrDealerInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto dlrDealerInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====查询经销商基本信息=====");
		 LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		 PageInfoDto dealers =dealerService.getDlrDealers(queryParams,loginUser);
		
		return dealers;
		
	}
	
	/**
	 * 模板下载
	 * @param type
	 * @param request
	 * @param response
	 */
    @RequestMapping(value = "/export/{type}",method = RequestMethod.GET)
    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //查询对应的参数
            BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
            Resource resource = new ClassPathResource(paramDto.getParamValue()); 
            //获取文件名称
            FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
            
            response.addHeader("Content-Length", "" + resource.getFile().length());
            
            bis = new BufferedInputStream(resource.getInputStream());
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载模板失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }

	
	/**
	 * 查询业务范围到车系
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value="/findDealerBuss",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findDealerBuss(){
	   logger.info("=====查询业务范围到车系=====");
	   List<Map> dealerBussDto = dealerService.getDealerBuss();
	     return dealerBussDto;
	 }
	
	/**
	 * 经销商维护修改 默认显示信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/queryDealerInfoDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDealerInfoDetail(@PathVariable(value = "id") Long dealerId){
	    
		TmDealerPO dealerPO = dealerService.getTmDealerPO(dealerId);
		Map<String, Object> mapA = dealerPO.toMap();
		Long comPanyId = dealerPO.getLong("COMPANY_ID");
		TmDealerPO dealerPO_ = TmDealerPO.findFirst("COMPANY_ID = ?", comPanyId);
		Map<String, Object> mapB = dealerPO_.toMap();
		mapB.remove("dealer_code");
		mapB.remove("address");
		if(dealerPO.getLong("DEALER_GROUP_ID") != null){			
			TmDealerGroupPO tdgPO = TmDealerGroupPO.findById(dealerPO.getLong("DEALER_GROUP_ID"));
			if(tdgPO!=null){
				Map<String, Object> mapC = tdgPO.toMap();
				mapA.putAll(mapC);
//			mapA.putAll(mapB);
			}else{
//			mapA.putAll(mapB);
			}
		}
		
		TmCompanyPO companyPO = TmCompanyPO.findFirst("COMPANY_ID = ?",comPanyId);
		if(companyPO!=null){
			Map<String, Object> mapD = companyPO.toMap();
			mapD.remove("address");//移除地址
//			mapA.putAll(mapD);
			mapA.put("company_id", mapD.get("company_id"));
			mapA.put("company_shortname", mapD.get("company_shortname"));
		}
		
	    TtCompanyDetailPO comPanyDetailPO  = TtCompanyDetailPO.findFirst("COMPANY_ID = ?", comPanyId);
	    if(comPanyDetailPO!=null){
			Map<String, Object> mapE = comPanyDetailPO.toMap();
//			Integer comPanyLevel = (Integer) mapE.get("COMPANY_LEVEL");
//			mapE.remove("COMPANY_LEVEL");
//			mapE.put("COMPANY_LEVEL", comPanyLevel);
			//mapA.putAll(mapE);
			mapA.put("licensed_brands", mapE.get("licensed_brands"));
			mapA.put("company_level", mapE.get("company_level"));
			mapA.put("end_date", mapE.get("end_date"));
			//文件列表
			mapA.put("company_photo", mapE.get("company_photo"));
			mapA.put("business_license", mapE.get("business_license"));
			mapA.put("organization_chart", mapE.get("organization_chart"));
			mapA.put("copies_of_manda", mapE.get("copies_of_manda"));
			mapA.put("tax_certificate", mapE.get("tax_certificate"));
			mapA.put("financial_statement", mapE.get("financial_statement"));
			mapA.put("contract_no", mapE.get("contract_no"));
		}
	    Map<String, Object> mapF=dealerService.getDealerOrgCodeAndId(dealerId);
	    if(mapF!=null){
	    	mapA.putAll(mapF);
	    }
	    //ed表信息  是否回显红色字体
	    TmDealerEdPo edPO = TmDealerEdPo.findById(dealerId);
	    if(edPO != null){	    	
	    	Map<String, Object> mapG = edPO.toMap();
	    	Set<String> set = mapG.keySet();
	    	for(String key : set){
	    		if(key.endsWith("_F") || key.endsWith("_f")){	    			
	    			mapA.put(key, mapG.get(key));
	    		}
	    	}
	    	mapA.put("open_range", mapG.get("open_range"));
	    	mapA.put("company_phone", mapG.get("company_phone"));
	    	mapA.put("open_time", mapG.get("open_time"));
	    }
	    
	    //buss表 业务范围
	    List<TmDealerBussPO> bussList = TmDealerBussPO.find("DEALER_ID = ? ", dealerId);
	    String groupIds = "";
	    if(bussList != null && !bussList.isEmpty()){
	    	for(TmDealerBussPO po : bussList){
	    		groupIds += po.getBigDecimal("GROUP_ID") + ",";
	    	}
	    	groupIds = groupIds.substring(0, groupIds.length()-1);
	    }
	    mapA.put("groupIds", groupIds);
	    
	    //获取orgName
	    TmDealerOrgRelationPO tdorp = TmDealerOrgRelationPO.findFirst("DEALER_ID = ? ", dealerId);
	    if(tdorp != null){
	    	TmOrgPO org = TmOrgPO.findById(tdorp.getLong("ORG_ID"));    	
	    	mapA.put("DEALER_ORG_ID", tdorp.getLong("ORG_ID"));
	    	mapA.put("ORG_NAME", org.getString("ORG_NAME"));
	    }
	    //获取省市
	    mapA.put("province_id", dealerPO_.getInteger("PROVINCE_ID"));
	    mapA.put("city_id", dealerPO_.getInteger("CITY_ID"));
	    //获取状态
	    mapA.put("status", dealerPO_.getInteger("STATUS"));
	    
	    //日期处理
	    Set<String> set = mapA.keySet();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for(String key : set){
    		if(key.toLowerCase().endsWith("_date")){
    			if(!"".equals(CommonUtils.checkNull(mapA.get(key)))){
    				try {
						mapA.put(key, sdf.parse(CommonUtils.checkNull(mapA.get(key))));
					} catch (ParseException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}
		return mapA;
	}
	/**
	 * 经销商入网信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/queryDealerInfoDetailTT/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDealerInfoDetailTT(@PathVariable(value = "id") Long dealerId){
		TmDealerPO dealerPO = dealerService.getTmDealerPO(dealerId);
		Map<String, Object> mapA = dealerPO.toMap();
		
		TmCompanyPO companyPO = TmCompanyPO.findById(dealerPO.getLong("COMPANY_ID"));
		Map<String, Object> mapB =companyPO.toMap();
		mapB.remove("phone");
		TtCompanyDetailPO companyDetailPO = TtCompanyDetailPO.findFirst("COMPANY_ID = ?", dealerPO.getLong("COMPANY_ID"));
	
		if(companyDetailPO!=null){
			Map<String, Object> mapC = companyDetailPO.toMap();
			mapB.putAll(mapC);
			mapA.putAll(mapB);
			return mapA;
		}
		mapA.putAll(mapB);
		return mapA;
		
	}
	
	/**
	 * 经销商基本信息审核
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value="/queryDealerAuditInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDealerAuditInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====经销商基本信息审核=====");
		
		 PageInfoDto dealers =dealerService.queryDealerAuditInfo(queryParams);
		
		return dealers;
		
	}
	
	/**
	 * 经销商维护修改
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editModifyDealerInfo/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> editModifyDealerInfo(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "id") Long dealerId){
		logger.info("==================经销商维护修改================");
		dealerService.editModifyDealer(dto,dealerId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/editModifyDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
		
	/**
	 * 经销商维护增加
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addModifyDealerInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> addModifyDealerInfo(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商维护新增================");
		dealerService.addModifyDealer(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addModifyDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);  
		
	}
	
	/**
	 * 经销商维护查询销售和售后区域
	 * @param bussType
	 * @return
	 */
	@RequestMapping(value = "/getAllOrg/{bussType}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllOrg(@PathVariable String bussType){
		logger.info("==================经销商维护查询销售和售后区域================");
		List<Map> list = dealerService.getAllOrg(bussType);
		return list;
	}
	
    /**
     * 弹出小区选择
     * @date 2017年2月20日
     * @return
     *  type  0 全部 1销售  2售后
     */
    @RequestMapping(value="/smallOrg/{type}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getSmallOrg1(@RequestParam Map<String, String> queryParams,@PathVariable String type){
    	logger.info("=====销售售后小区查询=====");
    	queryParams.put("type", type);
    	return  dealerService.getSmallOrg1(queryParams);
    }
	 /**
     * 查询经销商集团
     * @date 2017年2月20日
     * @return
     */
    @RequestMapping(value="/getDealerGroup",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getDealerGroup(@RequestParam Map<String, String> queryParams){
    	logger.info("=====销售小区查询=====");
    	
    	return  dealerService.getDealerGroupInfos(queryParams);
    }
    
    /**
	 * 经销商基本信息审核
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value="/queryDealerKeyPersonOTD/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDealerKeyPersonOTD(@PathVariable(value = "id") Long dealerId) {
		 logger.info("=====关键岗位人员信息查询=====");
		 return  dealerService.getDealerKeyPersonOTD(dealerId);
	}
		
	 /**
     * 经销商基本信息审核通过
     * @date 2017年2月20日
     * @return
     */
    @RequestMapping(value="/queryPass",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DealerDetailedinfoDTO> queryPass(@RequestParam Map<String, String> queryParams){
    	logger.info("=====经销商基本信息审核通过=====");
    	
    	dealerService.queryDealerPass(queryParams);
    	return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }
		 
    /**
     * 经销商基本信息审核驳回
     * @return
     */
    @RequestMapping(value="/queryReject",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DealerDetailedinfoDTO> queryReject(@RequestParam Map<String, String> queryParams){
    	logger.info("=====经销商基本信息审核驳回=====");
    	
    	dealerService.queryDealerReject(queryParams);
    	return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }
    
    /**
     * 经销商基本信息审核通过
     * @date 2017年2月20日
     * @return
     */
    @RequestMapping(value="/queryPass/{id}",method = RequestMethod.GET)
    @ResponseBody
    public void editPassid(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") Long dealerId){
    	logger.info("=====经销商基本信息审核通过=====");
    	
//    	dealerService.queryDealerPass(dealerId);
    	queryParams.put("dealerId", String.valueOf(dealerId));
    	dealerService.queryDealerPass(queryParams);
    }
		 
    /**
     * 经销商基本信息审核驳回
     * @return
     */
    @RequestMapping(value="/queryReject/{id}",method = RequestMethod.GET)
    @ResponseBody
    public void editRejectid(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") Long dealerId){
    	logger.info("=====经销商基本信息审核驳回=====");
    	
//    	dealerService.queryDealerReject(dealerId);
    	queryParams.put("dealerId", String.valueOf(dealerId));
    	dealerService.queryDealerReject(queryParams);
    }
    
    /**
	 * 经销商明细(dlr) 修改默认显示信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/dlrQueryDealerInfoDetail1/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> dlrQueryDealerInfoDetail1(@PathVariable(value = "id") Long dealerId){
		logger.info("=====经销商维护查询页面=====");
		Map<String, Object> mapA = queryDealerInfoDetailTT(dealerId);
		Map<String, Object> mapB = dealerService.queryDealerInfoDetail1(dealerId);
		mapA.putAll(mapB);
		
	    //buss表 业务范围
	    List<TmDealerBussPO> bussList = TmDealerBussPO.find("DEALER_ID = ? ", dealerId);
	    String groupIds = "";
	    if(bussList != null && !bussList.isEmpty()){
	    	for(TmDealerBussPO po : bussList){
	    		groupIds += po.getBigDecimal("GROUP_ID") + ",";
	    	}
	    	groupIds = groupIds.substring(0, groupIds.length()-1);
	    }
	    mapA.put("groupIds", groupIds);
	    
	    //获取group
	    TmDealerGroupPO dgp = TmDealerGroupPO.findById(mapA.get("dealer_group_id"));
	    if(dgp != null){	    	
	    	mapA.put("GROUP_NAME", dgp.getString("GROUP_NAME"));
	    }
	    
	    //日期处理
	    Set<String> set = mapA.keySet();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	for(String key : set){
    		if(key.toLowerCase().endsWith("_date")){
    			if(!"".equals(CommonUtils.checkNull(mapA.get(key)))){
    				try {
						mapA.put(key, sdf.parse(CommonUtils.checkNull(mapA.get(key))));
					} catch (ParseException e) {
						e.printStackTrace();
					}
    			}
    		}
    	}
		return mapA;
		
	}
	
	/**
	 * 经销商明细修改
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editdlrModifyDealerInfo/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> editdlrModifyDealerInfo(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "id") Long dealerId){
		logger.info("==================经销商明细修改================");
		System.out.println(dto);
		dealerService.editdlrModifyDealerInfos(dto,dealerId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/editdlrModifyDealerInfo").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
		
	}
	
	
	/**
	 * 经销商信息上报
	 * @throws Exception
	 */
	@RequestMapping(value = "/dealerInfoSubmit/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> dealerInfoSubmit(@RequestBody DealerDetailedinfoDTO dto,@PathVariable(value = "id") Long dealerId,UriComponentsBuilder uriCB){
		logger.info("==================经销商信息上报================");
		//上报之前先执行保存操作
		dealerService.editdlrModifyDealerInfos(dto,dealerId);
		dealerService.dealerInfoSubmitUpdate(dealerId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/dealerInfoSubmit").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
		
	}
	
	  
    /**
	 * 查询展厅信息
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value = "/doSearchDealerShowroom/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto doSearchDealerShowroom(@PathVariable(value = "id") Long dealerId){
		logger.info("=====查询展厅信息=====");
		
		PageInfoDto dto = dealerService.findDealerShowroom(dealerId);
		
		return dto;
		
	}
	 /**
	  * 查询经销商公司店面照片
	  * @param dealerId
	  * @return
	  */
	@RequestMapping(value = "/doSearchCompanyPhoto/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto doSearchCompanyPhoto7(@RequestParam Map<String,String> param,@PathVariable String id){
		logger.info("=====查询经销商公司店面照片=====");
		param.put("id", id);
		PageInfoDto dto = dealerService.doSearchCompanyPhoto7(param);
		return dto;
		
	}
	
	/**
	 * 经销商展厅明细
	 * @param Id
	 * @return
	 */
	@RequestMapping(value = "/dealerShowroomDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> dealerShowroomDetail(@PathVariable(value = "id") BigDecimal id){
		TmVsDealerShowroomPO tdsPo = TmVsDealerShowroomPO.findById(id);
		return tdsPo.toMap();
		
	}
	
	/**
	 * 新增经销商展厅
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addDealerShowRoom", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> addDealerShowRoom(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================新增经销商展厅================");		
		dealerService.addDealerShowRoom(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addDealerShowRoom").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 修改经销商展厅
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editDealerShowRoom", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> editDealerShowRoom(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================修改经销商展厅================");		
		dealerService.editDealerShowRoom(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/editDealerShowRoom").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除经销商展厅
	 * @param id
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/delDealerShowRoom/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<DealerDetailedinfoDTO> delDealerShowRoom(@PathVariable Long id,UriComponentsBuilder uriCB){
		logger.info("==================删除经销商展厅================");		
		dealerService.delDealerShowRoom(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/delDealerShowRoom").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	
	
	 /**
	  * 删除经销商公司附件(经销商公司照片，审核材料，授权合同)
	  * @param dealerId
	  * @return
	  */
	@RequestMapping(value = "/deleteCompanyDetailFuJian/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<FsFileuploadDto> deleteCompanyDetailFuJian(@PathVariable(value = "id") Integer id,UriComponentsBuilder uriCB){
		logger.info("===== 删除经销商公司附件(经销商公司照片，审核材料，授权合同)=====");
		FileUploadInfoPO.delete("FILE_UPLOAD_INFO_ID = ?", id);
//		FsFileuploadPO fPO = FsFileuploadPO.findById(id);
//		FsFileuploadDto dto = new FsFileuploadDto();
//		
//		try {
//			dto.setId(id);
//			fPO.deleteCascadeShallow();
//		} catch (Exception e) {
//			throw new ServiceBizException("删除失败...");
//
//		}
//		
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/deleteCompanyDetailFuJian").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
		
	}
	
 	@RequestMapping(value = "/import", method = RequestMethod.POST)
 	@ResponseBody
	public ArrayList<DealerMaintainImportDTO> importExcel(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile, UriComponentsBuilder uriCB) throws Exception{
 		logger.info("===== 经销商维护   业务范围导入临时表=====");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
 		//清空临时表
 		int delAll = TmpDealerBussPO.deleteAll();
 		 // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<DealerMaintainImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<DealerMaintainImportDTO>(DealerMaintainImportDTO.class ));
        if(importResult.isSucess()){       	
        	ArrayList<DealerMaintainImportDTO> dataList = importResult.getDataList();
        	for(DealerMaintainImportDTO dto : dataList){
        		TmpDealerBussPO po = new TmpDealerBussPO();
        		po.setString("ROW_NO", dto.getRowNO());
        		po.setString("DEALER_CODE", dto.getDealerCode());
        		po.setString("BRAND_CODE", dto.getBrandCode());
        		po.setString("GROUP_CODE", dto.getGroupCode());
        		po.setLong("CREATE_BY",loginUser.getUserId());
        		po.setTimestamp("CREATE_DATE", currentTime);
        		po.saveIt();
        	}
        	ArrayList<DealerMaintainImportDTO> errorList = dealerService.checkData();
        	importResult.setErrorList(errorList);
        }
        if(importResult.getErrorList() != null && !importResult.getErrorList().isEmpty()){
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList());
        }else{       	
        	return importResult.getDataList();
        }
	}
 	
 	/**
 	 * 经销商维护   业务范围导入正式表
 	 * @param uriCB
 	 * @return
 	 */
 	@RequestMapping(value = "/importSave", method = RequestMethod.POST)
 	@ResponseBody
 	public ResponseEntity<DealerMaintainImportDTO> importSave(UriComponentsBuilder uriCB){
 		logger.info("===== 经销商维护   业务范围导入正式表=====");
 		dealerService.importSave();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/queryDealers/importSave").buildAndExpand().toUriString());
		return new ResponseEntity<DealerMaintainImportDTO>( HttpStatus.CREATED);
 		
 	}
	
 	/**
 	 * 经销商端  基本信息查询  附件上传
 	 * @param dto
 	 * @param uriCB
 	 * @return
 	 */
 	@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
 	@ResponseBody
 	public ResponseEntity<DealerDetailedinfoDTO> uploadFiles(@RequestBody DealerDetailedinfoDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================经销商端  基本信息查询  附件上传================");
		dealerService.uploadFiles(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("").buildAndExpand().toUriString());
        return new ResponseEntity<DealerDetailedinfoDTO>(headers, HttpStatus.CREATED);
 	}
	
}
