package com.yonyou.dms.vehicle.controller.oldPart;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartRuleDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpOldpartImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpUrgencyVinImpDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartRulePO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpOldpartImpPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpUrgencyVinImpPO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartRuleMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/oldPartRule")
public class OldPartRuleMaintainController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	OldPartRuleMaintainService oservice;
	
	@Autowired
	private ExcelRead<TmpOldpartImpDTO> excelReadService;
	
	
	@Autowired
	private ExcelRead<TmpUrgencyVinImpDTO> excelRead;
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
	    	logger.info("查询标记规则信息");
	        PageInfoDto pageInfoDto=oservice.getQuerySql(queryParam);
	        return pageInfoDto;
	    }
	    
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TmOldpartRuleDTO> addMaterialGroup(@RequestBody @Valid TmOldpartRuleDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增标记规则信息");
	        Long id = oservice.addTmOldpartRule(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/oldPartRule/add/{PART_IMP}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TmOldpartRuleDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	    
	    @RequestMapping(value = "/{PART_IMP}", method = RequestMethod.PUT)
		public ResponseEntity<TmOldpartRuleDTO> ModifyTmOldpartStor(@PathVariable("PART_IMP") Long id,@RequestBody TmOldpartRuleDTO tcDto,UriComponentsBuilder uriCB) throws Exception {
			logger.info("修改标记规则信息");
	    	oservice.modifyTmOldpartRule(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/oldPartRule/{PART_IMP}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TmOldpartRuleDTO>(headers, HttpStatus.CREATED);
		}

	    
	    /**
	     * 根据ID进行查询
	     * @param id
	     * @return
	     */

	    @RequestMapping(value="/{PART_IMP}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> findById(@PathVariable(value = "PART_IMP") Long id){
	    	TmOldpartRulePO map=TmOldpartRulePO.findById(id);
	        return map.toMap();
	    }
	    
	    /**
		 * 旧件导入
		 */
		@RequestMapping(value = "/import", method = RequestMethod.POST)
		@ResponseBody
		public List<TmpOldpartImpDTO> yearPlanExcelOperate(@RequestParam final Map<String, String> queryParam,
				@RequestParam(value = "file") MultipartFile importFile, TmpOldpartImpDTO forecastImportDto,
				UriComponentsBuilder uriCB) throws Exception {

			TmpOldpartImpPO.deleteAll();
			ImportResultDto<TmpOldpartImpDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TmpOldpartImpDTO>(TmpOldpartImpDTO.class,
							new ExcelReadCallBack<TmpOldpartImpDTO>() {
								@Override
								public void readRowCallBack(TmpOldpartImpDTO rowDto, boolean isValidateSucess) {
									try {
										// 只有全部是成功的情况下，才执行数据库保存
										if (isValidateSucess) {
											oservice.insertTmpVsYearlyPlan(rowDto);
											// 校验临时表数据
											ImportResultDto<TmpOldpartImpDTO> importResultList = oservice
													.checkData(rowDto);
											if (importResultList != null) {
												throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList());
											}
										}
									} catch (Exception e) {
										throw new ServiceBizException(e);
									}
								}
							}));
			if (importResult.isSucess()) {
				return importResult.getDataList();
				
			} else {
				throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
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
	@RequestMapping(value="/oemSelectTmpYearPlan",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> oemSelectTmpYearPlan(@RequestParam Map<String, String> queryParam) {
		logger.info("===========零售销售上传（查询待插入数据） ===============");
		//确认后查询待插入的数据
		List<Map> list = oservice.oemSelectTmpYearPlan(queryParam);
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
		}
		return list;
	}
	/**
	 * 导入正式表
	 * @throws ParseException 
	 */
	@RequestMapping(value="/checkImportData",method = RequestMethod.POST)
	@ResponseBody
   public void importExcel(UriComponentsBuilder uriCB,
			@RequestParam Map<String,String> queryParam) throws ParseException {
		oservice.save(queryParam);
	}
	/**
	 * 删除vin
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */
    @RequestMapping(value = "/deletevin/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVin(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
    	oservice.deleteVinById(id);
    }
	/**
	 * 删除旧件
	 * @param id
	 * @param uriCB
	 * @throws ServiceBizException
	 */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharge(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
    	oservice.deleteChargeById(id);
    }
		   		/**
				 * VIN导入
				 */
				@RequestMapping(value = "/importvin", method = RequestMethod.POST)
				@ResponseBody
				public List<TmpUrgencyVinImpDTO> importVin(@RequestParam final Map<String, String> queryParam,
						@RequestParam(value = "file") MultipartFile importFile, TmpUrgencyVinImpDTO forecastImportDto,
						UriComponentsBuilder uriCB) throws Exception {

					TmpUrgencyVinImpPO.deleteAll();
					ImportResultDto<TmpUrgencyVinImpDTO> importResult = excelRead.analyzeExcelFirstSheet(importFile,
							new AbstractExcelReadCallBack<TmpUrgencyVinImpDTO>(TmpUrgencyVinImpDTO.class,
									new ExcelReadCallBack<TmpUrgencyVinImpDTO>() {
										@Override
										public void readRowCallBack(TmpUrgencyVinImpDTO rowDto, boolean isValidateSucess) {
											try {
												// 只有全部是成功的情况下，才执行数据库保存
												if (isValidateSucess) {
													oservice.insertvin(rowDto);
													// 校验临时表数据
													ImportResultDto<TmpUrgencyVinImpDTO> importResultList = oservice
															.checkDatavin(rowDto);
													if (importResultList != null) {
														throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList());
													}
												}
											} catch (Exception e) {
												throw new ServiceBizException("导入出错");
											}
										}
									}));
					if (importResult.isSucess()) {
						return importResult.getDataList();
						
					} else {
						throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
					}
				}
				
				/**
				 * 
				* @Title: oemSelect
				* @Description: 临时表数据回显 
				* @param @param year
				* @param @param queryParam
				* @param @return    设定文件 
				* @return PageInfoDto    返回类型 
				* @throws
				 */
				@RequestMapping(value="/oemSelectvin",method = RequestMethod.GET)
				@ResponseBody
				public List<Map> oemSelect(@RequestParam Map<String, String> queryParam) {
					logger.info("===========零售销售上传（查询待插入数据） ===============");
					//确认后查询待插入的数据
					List<Map> list = oservice.oemSelect(queryParam);
					for(int i=0;i<list.size();i++){
						Map map = list.get(i);
					}
					return list;
				}
				/**
				 * 导入正式表
				 * @throws ParseException 
				 */
				@RequestMapping(value="/checkvin",method = RequestMethod.POST)
				@ResponseBody
			   public void importVin(UriComponentsBuilder uriCB,
						@RequestParam Map<String,String> queryParam) throws ParseException {
					oservice.savevin(queryParam);
				}
}
