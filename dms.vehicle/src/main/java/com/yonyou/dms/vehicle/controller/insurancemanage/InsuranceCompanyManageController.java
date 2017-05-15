package com.yonyou.dms.vehicle.controller.insurancemanage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.threePack.ForecastImportDto;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyExcelTempDcsPO;
import com.yonyou.dms.vehicle.service.insurancemanage.InsuranceCompanyManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn
@RequestMapping("/InsuranceCompanyManange")
public class InsuranceCompanyManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	InsuranceCompanyManageService  insuranceCompanyManageService;
	@Autowired
    private ExcelRead<TtInsuranceCompanyExcelTempDcsDTO>  excelReadService;
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto InsuranceCompanyManageQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("-----------------------保险公司维护查询-----------------------");
    	PageInfoDto pageInfoDto = insuranceCompanyManageService.InsuranceCompanyManageQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void InsuranceCompanyManageDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("-----------------------保险公司维护下载-----------------------");
    	insuranceCompanyManageService.InsuranceCompanyManageDownload(queryParam, request, response);
	}
	/**
	 * 导入模板下载
	 */
	@RequestMapping(value="/downloadTemple",method = RequestMethod.GET)
	@ResponseBody
	public void downloadTemple(HttpServletRequest request,HttpServletResponse response) {
		logger.info("============保险公司维护导入模板下载===============");
		insuranceCompanyManageService.downloadTemple(request, response);
	}
	
	/**
	 * 导入临时表
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
		 	@RequestMapping(value = "/Import", method = RequestMethod.POST)
		 	@ResponseBody
		    public ArrayList<TtInsuranceCompanyExcelTempDcsDTO> importforecastAudit(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) throws Exception {
		 		logger.info("============保险公司维护导入===============");
		 		TtInsuranceCompanyExcelTempDcsPO.deleteAll();
		         // 解析Excel 表格(如果需要进行回调)
		         ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TtInsuranceCompanyExcelTempDcsDTO>(TtInsuranceCompanyExcelTempDcsDTO.class,new ExcelReadCallBack<TtInsuranceCompanyExcelTempDcsDTO>() {
		             @Override
		             public void readRowCallBack(TtInsuranceCompanyExcelTempDcsDTO rowDto, boolean isValidateSucess) {
		                 try{
		                     // 只有全部是成功的情况下，才执行数据库保存
		                     if(isValidateSucess){
		                    	 insuranceCompanyManageService.TtInsuranceCompanyExcelTempImp(rowDto);
		                     	ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> importResultList =insuranceCompanyManageService.checkData(rowDto);
		                     	if(importResultList.getErrorList() != null){
		                    		throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList()) ;
		                    	}
		                     }
		                 }catch(Exception e){
		                		throw new ServiceBizException(e) ;
		                 }
		             }
		         }));
		         if(importResult.isSucess()){
		             return importResult.getDataList();
		         }else{
		             throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
		         }
		     }
		 	/**
			 * 
			* @Title: oemSelectTmpYearPlan 
			* @Description: 临时表数据回显 
			* @param @param year
			* @param @param queryParam
			* @param @return    设定文件 
			* @return PageInfoDto    返回类型 
			* @throws
			 */
			@RequestMapping(value="/selectData",method = RequestMethod.GET)
			@ResponseBody
			public List<Map> oemSelectTmpYearPlan(@RequestParam Map<String, String> queryParam) {
				logger.info("===========保险公司维护（查询待插入数据） ===============");
				//确认后查询待插入的数据
				List<Map> list = insuranceCompanyManageService.oemSelectTmpYearPlan(queryParam);
				for(int i=0;i<list.size();i++){
					Map map = list.get(i);
				}
				return list;
			}
			/**
			 * 导入
		 	 * @throws ParseException 
			 */
			@RequestMapping(value="/importInsuranceCompanyManage",method = RequestMethod.POST)
			@ResponseBody
		    public ResponseEntity<TtInsuranceCompanyMainDcsDTO> importExcel(UriComponentsBuilder uriCB,
		    		 @Valid TtInsuranceCompanyMainDcsDTO ypDTO,
					@RequestParam Map<String,String> queryParam) throws ParseException {
				try{
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					List<Map> tmpList = insuranceCompanyManageService.oemSelectTmpYearPlan(queryParam);	
					for(int i=0;i<tmpList.size();i++){				
						Map<String,Object> map = tmpList.get(i);	
						TtInsuranceCompanyMainDcsDTO po = new TtInsuranceCompanyMainDcsDTO();
						po.setInsuranceCompanyCode(map.get("INSURANCE_COMPANY_CODE").toString());//保险公司代码
						po.setInsuranceCompanyName(map.get("INSURANCE_COMPANY_NAME").toString());//保险公司名称
						po.setInsCompanyShortName(map.get("INS_COMPANY_SHORT_NAME").toString());//保险公司简称
						if("是".equals(map.get("IS_CO_INSURANCE_COMPANY").toString())){
							po.setIsCoInsuranceCompany(1);//合作保险公司
						}else if("否".equals(map.get("IS_CO_INSURANCE_COMPANY").toString())){
							po.setIsCoInsuranceCompany(0);//合作保险公司
						}
						po.setCreateBy(loginInfo.getUserId());
						po.setCreateDate(new Date());
						insuranceCompanyManageService.setImpPO(po);	
					}
				}catch (Exception e) {
					throw e;
					}
					return null;
					
			}
}
