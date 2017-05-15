package com.yonyou.dms.vehicle.controller.complaint;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtCrComplaintsDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.TtVisitJdpowerDTO;
import com.yonyou.dms.vehicle.domains.PO.basedata.TmpVisitJdpowerDcsPO;
import com.yonyou.dms.vehicle.domains.PO.claimApproveMgr.TtVisitJdpowerDcsPO;
import com.yonyou.dms.vehicle.service.complaint.ComplaintDisposalNoService;
//import com.yonyou.dms.vehicle.domains.PO.bigCustomer.TtBigCustomerReportApprovalPO;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *  客户投诉处理(总部)
 * @author ZhaoZ
 * @date 2017年5月9日
 */
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/complaintDisposalNo")
public class ComplaintDisposalNoController {

	private static final Logger logger = LoggerFactory.getLogger(ComplaintDisposalNoController.class);
	
	@Autowired
    private SystemParamService paramService;
	@Autowired
	private  ComplaintDisposalNoService comService;
	
	@Autowired
	private ExcelRead<TtVisitJdpowerDTO>  excelReadService;
	
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 客户投诉处理(总部)查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/queryComplaintDisposalNo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryComplaintDisposalNo(@RequestParam Map<String, String> queryParams){
		logger.info("=====客户投诉处理(总部)查询=====");
		return comService.complaintDisposalNoList(queryParams);
		
	}
	
	/**
	 * 通过
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/dealAgree/{compId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtCrComplaintsDcsDTO> dealAgree(@RequestBody TtCrComplaintsDcsDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "compId") Long compId){
		logger.info("==================保存================");
		comService.dealAgree(dto,compId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/dealAgree").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 驳回
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/dealNotAgree/{compId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtCrComplaintsDcsDTO> dealNotAgree(@RequestBody TtCrComplaintsDcsDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "compId") Long compId){
		logger.info("==================保存================");
		comService.dealNotAgree(dto,compId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/dealAgree").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	
	/**
	 * 导入临时表信息
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/impOperate", method = RequestMethod.POST)
	@ResponseBody
	public ArrayList<TtVisitJdpowerDTO> exporOrderPartData(@RequestParam(value = "file") MultipartFile importFile,
			TtVisitJdpowerDTO dto) throws Exception {
		 	logger.info("============导入临时表信息===============");
		 	
		 	// 解析Excel 表格(如果需要进行回调)
			ImportResultDto<TtVisitJdpowerDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TtVisitJdpowerDTO>(TtVisitJdpowerDTO.class));
			ArrayList<TtVisitJdpowerDTO> dataList = importResult.getDataList();
			if (dataList.size()>10000) {
				throw new ServiceBizException("导入出错,单次上传文件至多10000行");
			}
			ArrayList<TtVisitJdpowerDTO>  list = new ArrayList<>();
			//清空临时表中的数据
			TmpVisitJdpowerDcsPO.deleteAll();
			for (TtVisitJdpowerDTO rowDto : dataList) {			
				// 只有全部是成功的情况下，才执行数据库保存
				comService.insertTmpVisitJdpowerDcs(rowDto);
			}
			List<TtVisitJdpowerDTO>  tpoDdto = comService.checkData();
			if (tpoDdto != null) {
				list.addAll(tpoDdto);
			}
			if (list != null && !list.isEmpty()) {
				throw new ServiceBizException("导入出错,请见错误列表", list);
			}
			
			return null;
		
		}
	
	/**
	 * 
	* @Title: downloadTemple 
	* @Description: 回访任务导入（下载导入模版） 
	* @param @param type
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/download/{type}",method = RequestMethod.GET)
    public void yearPlanDownloadTemple(@PathVariable(value = "type") String type,
    		HttpServletRequest request,HttpServletResponse response){
		logger.info("============ 回访任务导入（下载导入模版）===============");
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
	 * 清单查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/getTmpList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryExportList() {
		 logger.info("=====清单查询=====");
		 String sql = " SELECT * FROM TMP_VISIT_JDPOWER_dcs ";
		 return  OemDAOUtil.findAll(sql, null);
	}
	
	
	/**
	 * 回访任务导入
	 * @param dto
	 * @param uriCB
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtVisitJdpowerDTO> importVisit(UriComponentsBuilder uriCB) throws ParseException{
		logger.info("==================回访任务导入================");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		LazyList<TmpVisitJdpowerDcsPO> poList = TmpVisitJdpowerDcsPO.find("CREATE_BY = ?", loginInfo.getUserId());
		for(TmpVisitJdpowerDcsPO po:poList){
			String sql = "SELECT CODE_ID CODE1 FROM tc_code_dcs WHERE CODE_DESC = '"+po.getString("VISIT_TYPE")+"'";
			String sql2 = "SELECT CODE_ID CODE2 FROM tc_code_dcs WHERE CODE_DESC = '"+po.getString("VISIT_SOURCE")+"'";
			Integer code1 = Integer.parseInt(OemDAOUtil.findFirst(sql,null).get("CODE1").toString());
			Integer code2 = Integer.parseInt(OemDAOUtil.findFirst(sql2,null).get("CODE2").toString());
			TmDealerPO tmPo = TmDealerPO.findFirst("DEALER_CODE = ?", po.getString("DEALER_CODE"));
			TtVisitJdpowerDcsPO tqs = new TtVisitJdpowerDcsPO();
        	tqs.setLong("DEALER_ID", tmPo.getLong("DEALER_ID"));
        	tqs.setString("DEALER_CODE", po.getString("DEALER_CODE"));
        	tqs.setString("LINK_NAME", po.getString("LINK_NAME"));
        	tqs.setString("LINK_PHONE", po.getString("LINK_PHONE"));
        	tqs.setString("VIN", po.getString("VIN"));
        	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	if(po.getString("BACK_DATE")!=null && po.getString("BACK_DATE")!=""){
        		tqs.setDate("BACK_DATE", sdf.parse(po.getString("BACK_DATE")));
        	}
        	tqs.setString("BACK_TYPE", po.getString("BACK_TYPE"));
        	tqs.setString("BACK_CONTENT", po.getString("BACK_CONTENT"));
        	tqs.setString("VISIT_SOURCE", code2);
        	tqs.setInteger("VISIT_STATUS", 92031001);
        	tqs.setLong("CREATE_BY", loginInfo.getUserId());
        	tqs.setInteger("VISIT_TYPE",code1);
    		long time= System.currentTimeMillis();
    		try {
    			Date date = sdf.parse(sdf.format(new Date(time)));
    			java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    			tqs.setTimestamp("CREATE_DATE",st);
    		} catch (ParseException e) {
    			e.printStackTrace();
    		} 
        	tqs.insert();
		}
		MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/import").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 回访结果查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/returnVisitResultQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto returnVisitResultQuery(@RequestParam Map<String, String> queryParams){
		logger.info("=====回访结果查询=====");
		return comService.returnVisitResultList(queryParams);
		
	}
	
	/**
	 * 经销商端回访任务处理查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/returnVisitDealerQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto returnVisitDealerQuery(@RequestParam Map<String, String> queryParams){
		logger.info("=====回访结果查询=====");
		return comService.returnVisitDealerList(queryParams);
		
	}
	
	/**
	 * 经销商端回访任务处理/回访结果下载
	 * @param queryParams
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/excelExport/{flag}",method = RequestMethod.GET)
	@ResponseBody
	public void excelExport (@RequestParam Map<String, String> queryParams,@PathVariable(value = "flag") String flag, HttpServletRequest request,
			HttpServletResponse response ){
		logger.info("============回访任务处理/结果查询下载===============");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = "";
		if("2".equals(flag)){
			dealerCode = queryParams.get("dealerCode");//回访结果页面
		}else{
			dealerCode = loginUser.getDealerCode();//经销商回访任务处理页面
		}
		List<Map> customerList = comService.excelExportList(queryParams,dealerCode);
		for(Map map:customerList){
			if(map.get("VISIT_STATUS").equals(OemDictCodeConstants.TAIL_AFTER_STATUS_01.toString())){
				if("2".equals(flag)){//回访结果查询时，跟踪状态为待处理，不下载处理内容
					map.put("DISPOSE_NAME", "");
					map.put("DISPOSE_DATE", "");
					map.put("CODE_DESC_RESULT", "");
					map.put("RESULT_CONTENT", "");
				}
			}
		}
		
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("回访结果下载",customerList);
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "导入日期"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_SMALL", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商简称"));
		exportColumnList.add(new ExcelExportColumn("LINK_NAME", "联系人名称"));
		exportColumnList.add(new ExcelExportColumn("LINK_PHONE", "联系人电话"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BACK_DATE", "反馈时间"));
		exportColumnList.add(new ExcelExportColumn("BACK_TYPE", "反馈类型"));
		exportColumnList.add(new ExcelExportColumn("BACK_CONTENT", "反馈内容"));
		exportColumnList.add(new ExcelExportColumn("CODE_DESC_TYPE", "跟踪性质"));
		exportColumnList.add(new ExcelExportColumn("CODE_DESC_SOURCE", "渠道来源"));
		exportColumnList.add(new ExcelExportColumn("CODE_DESC_STATUS", "跟踪状态"));
		exportColumnList.add(new ExcelExportColumn("DISPOSE_NAME", "跟踪人"));
		exportColumnList.add(new ExcelExportColumn("DISPOSE_DATE", "处理时间"));
		exportColumnList.add(new ExcelExportColumn("CODE_DESC_RESULT", "处理结果"));
		exportColumnList.add(new ExcelExportColumn("RESULT_CONTENT", "处理结果描述"));

		excelService.generateExcel(excelData, exportColumnList, "回访结果下载.xls", request, response);
	}
	
	/**
	 * 跟踪处理跳转/回访结果明细
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/editReturnVisitUrl/{VisitId}",method = RequestMethod.GET)
	@ResponseBody
	public Map editReturnVisitUrl(@PathVariable(value = "VisitId") Long VisitId) {
		 logger.info("=====跟踪处理跳转/回访结果明细=====");
		 SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		 TtVisitJdpowerDcsPO po = TtVisitJdpowerDcsPO.findById(VisitId);
		 if(po!=null){
			Map map = po.toMap();
			String BACK_DATE = map.get("back_date").toString();
			
			map.put("BACK_DATE", BACK_DATE.substring(0,10));
			return map;
		 }
		 return  new HashMap<>();
	}
	
	/**
	 * 处理结果保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/resultSave/{type}/{VisitId}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtVisitJdpowerDTO> resultSave(@RequestBody TtVisitJdpowerDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "type") String type,@PathVariable(value = "VisitId") Long VisitId){
		logger.info("==================处理结果保存================");
		comService.save(dto,type,VisitId);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/resultSave").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
}
