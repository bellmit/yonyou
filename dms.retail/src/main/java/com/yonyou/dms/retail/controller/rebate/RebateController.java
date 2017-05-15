package com.yonyou.dms.retail.controller.rebate;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.ForecastImportDto;
import com.yonyou.dms.retail.domains.DTO.rebate.TtRebateCalculateTmpDTO;
import com.yonyou.dms.retail.domains.DTO.rebate.TtRebateCalculateTmpDTO2;
import com.yonyou.dms.retail.domains.PO.rebate.RebateManagePO;
import com.yonyou.dms.retail.service.rebate.RebateManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 经销商返利核算管理
 * @author zhengzengliang
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Controller
@TxnConn
@RequestMapping("/rebate")
public class RebateController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	RebateManageService rservice;
	
	@Autowired
	ExcelGenerator excelService;
	
	@Autowired
    private SystemParamService paramService;
	
	@Autowired
    private ExcelRead<TtRebateCalculateTmpDTO>  excelReadService;
	
	/**
	 * 
	* @Title: exportCarownerInfo 
	* @Description: 返利核算下载 
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
		@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
		public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		 logger.info("返利核算信息下载");
			List<Map> resultList = rservice.queryEmpInfoforExport(queryParam);
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("返利核算信息下载", resultList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_TYPE", "商务政策类型"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "最后上传时间"));
			exportColumnList.add(new ExcelExportColumn("USER_NAME", "操作人"));
			excelService.generateExcel(excelData, exportColumnList, "返利核算信息下载.xls", request, response);
		}
	 
	 	/**
	 	 * 
	 	* @Title: queryDefeatReason 
	 	* @Description: 返利核算管理（查询） 
	 	* @param @param queryParam
	 	* @param @return
	 	* @param @throws Exception    设定文件 
	 	* @return PageInfoDto    返回类型 
	 	* @throws
	 	 */
	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("返利核算管理（查询）");
	        PageInfoDto pageInfoDto=rservice.getRebateManage(queryParam);
	        return pageInfoDto;
	    }
	    
	    /**
	     * 
	    * @Title: downloadTemple 
	    * @Description: 返利核算管理导入模版下载
	    * @param @param type
	    * @param @param request
	    * @param @param response    设定文件 
	    * @return void    返回类型 
	    * @throws
	     */
	    @RequestMapping(value = "/downloadTemple/{type}",method = RequestMethod.GET)
	    public void downloadTemple(@PathVariable(value = "type") String type,
	    		HttpServletRequest request,HttpServletResponse response){
			logger.info("============ 返利核算管理导入模版下载===============");
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
		 * 
		* @Title: uploadFileInitYearList 
		* @Description: 返利核算管理导入初始化年列表
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/uploadFileInitYearList",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> uploadFileInitYearList() {
			logger.info("============ 返利核算管理导入初始化年列表===============");
			List<Map> yearList = new ArrayList<Map>();
			Calendar cal = Calendar.getInstance();
			int nowYear = cal.get(Calendar.YEAR);
			for(int j=-3;j<5;j++){
				Map m = new HashMap();
				m.put("YEAR", (nowYear+j));
				yearList.add(m);
			}
			return yearList;
		}
		
		/**
		 * 
		* @Title: uploadFileInitMonthList 
		* @Description: 返利核算管理导入初始化月列表 
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/uploadFileInitMonthList",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> uploadFileInitMonthList() {
			logger.info("============ 返利核算管理导入初始化年列表===============");
			List<Map> mList = new ArrayList<Map>();
			for(int i=1;i<=12;i++){
				if(i<10){
					Map m = new HashMap();
					m.put("MONTH", "0"+i+"");
					mList.add(m);
				}else{
					Map m = new HashMap();
					m.put("MONTH", i+"");
					mList.add(m);
				}
			}
			return mList;
		}
		
		/**
		 * 
		* @Title: yearPlanExcelOperate 
		* @Description: 返利核算管理导入临时表与校验
		* @param @param queryParam
		* @param @param importFile
		* @param @param forecastImportDto
		* @param @param uriCB
		* @param @return
		* @param @throws Exception    设定文件 
		* @return List<TmpVsYearlyPlanDTO>    返回类型 
		* @throws
		 */
		@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	    @ResponseBody
	    public List<TtRebateCalculateTmpDTO> uploadFile(
	    		@RequestParam Map<String,String> queryParam,
	    		@RequestParam(value = "file") MultipartFile importFile,
	    		ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) 
	    				throws Exception {
			logger.info("============返利核算管理导入临时表与校验===============");
			// 清空临时表中的数据
			rservice.deleteTtRebateCalculateTmp();
	        // 解析Excel 表格(如果需要进行回调)
	        ImportResultDto<TtRebateCalculateTmpDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TtRebateCalculateTmpDTO>(TtRebateCalculateTmpDTO.class,new ExcelReadCallBack<TtRebateCalculateTmpDTO>() {
	            @Override
	            public void readRowCallBack(TtRebateCalculateTmpDTO rowDto, boolean isValidateSucess) {
	                try{
	                    // 只有全部是成功的情况下，才执行数据库保存
	                    if(isValidateSucess){
	                    	//向临时表插入数据
	                    	rservice.insertTmprebateCalculate(rowDto);
	                    }
	                }catch(Exception e){
	                    throw e;
	                }
	            }
	        }));
	        logger.debug("param:" + forecastImportDto.getFileParam());
	        // 校验临时表数据
        	//获取当前用户
    		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	         importResult = rservice.checkData(loginInfo);
        	if(importResult.isSucess()){
	            return importResult.getDataList();
	        }else{
	            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
	        }
	    }
		
		/**
		 * 
		* @Title: findTmpList 
		* @Description:插入临时表成功后查询待插入的数据
		* @param @param queryParam
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/findTmpList",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> findTmpList(
				@RequestParam Map<String, String> queryParam) {
			logger.info("============年度目标上传（查询待插入数据） ===============");
			//确认后查询待插入的数据
			//获取当前用户
    		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			List<Map> list = rservice.findTmpList(loginInfo);
			return list;
		}
	    
		/**
		 * 
		* @Title: rebateCalManageImportExcel 
		* @Description: 导入返利核算主表 
		* @param @param uriCB
		* @param @param trctDTO
		* @param @param queryParam
		* @param @return    设定文件 
		* @return ResponseEntity<TtRebateCalculateTmpDTO>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/rebateCalManage/importExcel",method = RequestMethod.POST)
		@ResponseBody
	    public ResponseEntity<TtRebateCalculateTmpDTO> rebateCalManageImportExcel(
	    		UriComponentsBuilder uriCB,
	    		@RequestBody @Valid TtRebateCalculateTmpDTO2 trctDTO2,
				@RequestParam Map<String,String> queryParam) {
			logger.info("============导入返利核算主表 ===============");
			String businessPolicyName = CommonUtils.checkNull(trctDTO2.getBusinessPolicyName());//商务政策名称
			String rYear = CommonUtils.checkNull(trctDTO2.getrYear());//起始年
			String rMonth = CommonUtils.checkNull(trctDTO2.getrMonth());//起始月
			String eYear = CommonUtils.checkNull(trctDTO2.geteYear());//结束年年
			String eMonth = CommonUtils.checkNull(trctDTO2.geteMonth());//结束月
			String uploadWay = CommonUtils.checkNull(trctDTO2.getUploadWayName());//上传方式
			String businessPolicyType = CommonUtils.checkNull(trctDTO2.getBusinessPolicyTypeName());//商务政策类型
			
			String startMonth=rYear+"-"+rMonth;//拼接起始月
			String endMonth=eYear+"-"+eMonth;//结束月
			if (null == businessPolicyName || "".equals(businessPolicyName)) {
				throw new ServiceBizException("导入失败!");
			}
			//覆盖添加
			if(uploadWay.equals("91191006")){
				//删除动态表数据
				rservice.deleteDyn(businessPolicyName,startMonth,endMonth);
				rservice.deleteSta(businessPolicyName,startMonth,endMonth);
			}
			//商务政策主表操作
			RebateManagePO trmPo=new RebateManagePO();
			//商务政策数据
			List<RebateManagePO> tcmList = rservice.selectRebateManage(businessPolicyName,startMonth,endMonth);
			Long logId=0L;
			if(tcmList.size()>0){
				//重新上传时候根据LogId更新数据
				 trmPo=tcmList.get(0);
				 logId=trmPo.getLong("LOG_ID");
				//获取当前用户
	    		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				//插入固定列数据表
				rservice.insertRebateStat(logId,loginInfo);
				
				List<Object> param = new ArrayList<Object>();
				param.add(loginInfo.getUserId());
				//存储过程跑动态列
				rservice.P_REBATE_CAL_DYN(loginInfo);
				//商务政策主表
				if(tcmList.size()>0){
					RebateManagePO tmPo=tcmList.get(0);
					RebateManagePO.update(" UPLOAD_WAY =? AND BUSINESS_POLICY_TYPE=? AND UPDATE_DATE=? AND UPDATE_BY=? ", " LOG_ID =? ", Integer.parseInt(uploadWay),Integer.parseInt(businessPolicyType),new Date(),loginInfo.getUserId(),tmPo.getLong("LOG_ID"));
				}
			}else{
				//首次导入做新增操作
				//获取当前用户
	    		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				
				List<Object> param = new ArrayList<Object>();
				param.add(loginInfo.getUserId());
				//存储过程跑动态列
				rservice.P_REBATE_CAL_DYN(loginInfo);
				//商务政策主表
				//首次导入无数据
				rservice.insertRebateManage(businessPolicyName,businessPolicyType,loginInfo,uploadWay,logId,startMonth,endMonth);
			
			}
			
			return new ResponseEntity<>( HttpStatus.CREATED);
		}
		
		/**
		 * 
		* @Title: reUploadFile 
		* @Description: 返利核算管理重新上传起始年
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFile(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理重新上传起始年 ===============");
			//查询数据
			List<Map> trmList = rservice.selectTtRebateCalculateManage(logId);
			return trmList;
		}
		
		/**
		 * 
		* @Title: reUploadFileStartMonth 
		* @Description: 返利核算管理获取开始月 
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/startMonthList/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFileStartMonth(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理重新上传起始月  ===============");
			//查询数据
			List<Map> trmList = rservice.selectReUploadStartMonth(logId);
			return trmList;
		}
		
		/**
		 * 
		* @Title: reUploadFileendYearList 
		* @Description: 返利核算管理获取结束年
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/endYearList/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFileEndYearList(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理获取结束年 ===============");
			//查询数据
			List<Map> trmList = rservice.selectReUploadEndYearList(logId);
			return trmList;
		}
		
		/**
		 * 
		* @Title: reUploadFileEndMonthList 
		* @Description: 返利核算管理获取结束月
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/endMonthList/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFileEndMonthList(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理获取结束月  ===============");
			//查询数据
			List<Map> trmList = rservice.selectReUploadEndMonthList(logId);
			return trmList;
		}
		
		/**
		 * 
		* @Title: reUploadFilegetBusinessPolicyName 
		* @Description: 返利核算管理重新上传获取商务政策名称 
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/getBusinessPolicyName/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFilegetBusinessPolicyName(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理重新上传获取商务政策名称  ===============");
			//查询数据
			List<Map> trmList = rservice.selectReUploadgetBusinessPolicyName(logId);
			return trmList;
		}
		
		/**
		 * 
		* @Title: reUploadFilegetgetBusinessPolicyType 
		* @Description: 返利核算管理获取商务政策类型 
		* @param @param logId
		* @param @return    设定文件 
		* @return List<Map>    返回类型 
		* @throws
		 */
		@RequestMapping(value="/reUploadFile/getBusinessPolicyType/{logId}",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> reUploadFilegetgetBusinessPolicyType(@PathVariable("logId") String logId) {
			logger.info("============ 返利核算管理获取商务政策类型  ===============");
			//查询数据
			List<Map> trmList = rservice.selectReUploadgetgetBusinessPolicyType(logId);
			return trmList;
		}
		
		
		

}
