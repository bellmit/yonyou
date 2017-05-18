package com.yonyou.dms.manage.controller.salesPlanManager;



import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.ForecastImportDto;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsYearlyPlanDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.YearPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsYearlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsYearlyPlanPO;
import com.yonyou.dms.manage.service.salesPlanManager.YearPlanImportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: DlrForecastQueryController 
* @Description:  生产订单管理
* @author zhengzengliang
* @date 2017年2月17日 下午5:32:11 
*
 */
@SuppressWarnings({"rawtypes"})
@Controller
@TxnConn
@RequestMapping("/yearPlanImport")
public class YearPlanImportController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
    private SystemParamService paramService;
	
	@Autowired
    private ExcelRead<TmpVsYearlyPlanDTO>  excelReadService;
	
	@Autowired
	private YearPlanImportService yearPlanImportService;
	
	/**
	 * 
	* @Title: getYearList 
	* @Description: 年度目标上传 （获取年）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearList() {
		logger.info("============ 年度目标上传 （获取年）===============");
		List<Map> yearList = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);	//获取当前年份
		for(int i =year; i<year+3;i++){
			Map<String,Object> yearMap = new HashMap<String,Object>();
			yearMap.put("YEAR", i);
			yearList.add(yearMap);
		}
		return yearList;
	}
	
	/**
	 * 
	* @Title: downloadTemple 
	* @Description:年度目标上传（下载导入模版） 
	* @param @param type
	* @param @param request
	* @param @param response    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/yearPlanDownloadTemple/{type}",method = RequestMethod.GET)
    public void yearPlanDownloadTemple(@PathVariable(value = "type") String type,
    		HttpServletRequest request,HttpServletResponse response){
		logger.info("============ 年度目标上传 （下载导入模版）===============");
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
	* @Title: yearPlanExcelOperate 
	* @Description:  年度目标上传(导入临时表)
	* @param @param importFile
	* @param @param forecastImportDto
	* @param @param uriCB
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<TmpVsYearlyPlanDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/yearPlanExcelOperate", method = RequestMethod.POST)
    @ResponseBody
    public List<TmpVsYearlyPlanDTO> yearPlanExcelOperate(
    		@RequestParam final Map<String,String> queryParam,
    		@RequestParam(value = "file") MultipartFile importFile,
    		TmpVsYearlyPlanDTO tmpVsYearlyPlanDTO, UriComponentsBuilder uriCB) 
    				throws Exception {
		/*logger.info("============年度目标上传(导入临时表)===============");
		// 清空临时表中目标年度的数据
		//TmpVsYearlyPlanPO.deleteAll();
	//	yearPlanImportService.deleteTmpVsYearlyPlan();
        // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<TmpVsYearlyPlanDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TmpVsYearlyPlanDTO>(TmpVsYearlyPlanDTO.class,new ExcelReadCallBack<TmpVsYearlyPlanDTO>() {
            @Override
            public void readRowCallBack(TmpVsYearlyPlanDTO rowDto, boolean isValidateSucess) {
                try{
                    logger.debug("dealerCode:"+rowDto.getDealerCode());
                    // 只有全部是成功的情况下，才执行数据库保存
                    if(isValidateSucess){
                    	//向临时表插入数据
                    	rowDto.setYear(queryParam.get("year"));
                    	rowDto.setPlanType(queryParam.get("planType"));
                    	yearPlanImportService.insertTmpVsYearlyPlan(rowDto);
                    	// 校验临时表数据
                    	ImportResultDto<TmpVsYearlyPlanDTO> importResultList =
                    			yearPlanImportService.checkData(rowDto);
                    	if(importResultList != null){
                    		throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList()) ;
                    	}
                    }
                }catch(Exception e){
                    throw e; 
                }
            }
        }));
        logger.debug("param:" + forecastImportDto.getFileParam());
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }*/
		
		// 解析Excel 表格(如果需要进行回调)
				ImportResultDto<TmpVsYearlyPlanDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
						new AbstractExcelReadCallBack<TmpVsYearlyPlanDTO>(TmpVsYearlyPlanDTO.class));
				ArrayList<TmpVsYearlyPlanDTO> dataList = importResult.getDataList();
				
				ImportResultDto<TmpVsYearlyPlanDTO> importResultList = new ImportResultDto<>();
			    TmpVsYearlyPlanPO.deleteAll();
				//获取年份和目标类型
				String year = tmpVsYearlyPlanDTO.getYear();
				String planType = tmpVsYearlyPlanDTO.getPlanType();
				for (TmpVsYearlyPlanDTO rowDto : dataList) {
					// 只有全部是成功的情况下，才执行数据库保存
					//往rowDto中添加年份和目标类型
					rowDto.setYear(year);
					rowDto.setPlanType(planType);
					yearPlanImportService.insertTmpVsYearlyPlan(rowDto);
					ImportResultDto<TmpVsYearlyPlanDTO> list = yearPlanImportService.checkData(rowDto);
					if (!StringUtils.isNullOrEmpty(list)) {
						throw new ServiceBizException("导入出错,请见错误列表", list.getErrorList());
					}
				}
	
				return null;
				
    }
	
	/**
	 * 
	* @Title: oemSelectTmpYearPlan 
	* @Description: 年度目标上传（查询待插入数据） 
	* @param @param year
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/oemSelectTmpYearPlan/{year}/{planType}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> oemSelectTmpYearPlan(@PathVariable("year") String year,
			@PathVariable("planType") String planType,
			@RequestParam Map<String, String> queryParam) {
		logger.info("============年度目标上传（查询待插入数据） ===============");
		//确认后查询待插入的数据
		queryParam.put("year", year);
		List<Map> list = yearPlanImportService.oemSelectTmpYearPlan(queryParam);
		for(int i=0;i<list.size();i++){
			Map map = list.get(i);
			map.put("yearId", year);
			map.put("planTypeId", planType);
		}
		return list;
	}
	
	/**
	 * 
	* @Title: checkImportData 
	* @Description:  校验待导入的数据版本是否一致
	* @param @param yearId
	* @param @param monthId
	* @param @param queryParam
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkImportData/{year}",
			method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkImportData(@PathVariable("year") String year,
			@RequestParam Map<String,String> queryParam) {
		logger.info("=============年度目标上传（校验待导入的数据版本是否一致）==============");
		queryParam.put("year", year);
		int total = yearPlanImportService.getTmpTtVsYearlyPlanTotal(queryParam);
		int flag = 0;
		List<Map> list = yearPlanImportService.checkImportData("");
		if(list.size() == total){
			flag = 1;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FLAG", flag);
		return map;
	}
	
	/**
	 * 
	* @Title: addUser 
	* @Description:  年度目标上传 导入业务表
	* @param @param userDto
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<DemoUserDto>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value="/yearPlan/importExcel/{year}/{planType}",method = RequestMethod.POST)
    public ResponseEntity<TmpVsYearlyPlanDTO> importExcel(UriComponentsBuilder uriCB,
    		@RequestBody @Valid YearPlanDTO ypDTO,
			@RequestParam Map<String,String> queryParam) {
		logger.info("=============年度目标上传（导入业务表）==============");
		String year = ypDTO.getYear();
		String planType = ypDTO.getPlanType();
		if (null == year || "".equals(year)) {
			try {
				throw new Exception("导入失败!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		CommonUtils.checkNull(planType);
		// 清空业务表中，该用户已导入未确认的年度目标
		// 查询要删除的结果集
		List<TtVsYearlyPlanPO> clrList = yearPlanImportService.selectTtVsYearlyPlan(year);
		if (null != clrList && clrList.size() > 0) {
			TtVsYearlyPlanDetailPO detailPo = null;
			TtVsYearlyPlanPO actPo = null;
			for (int i = 0; i < clrList.size(); i++) {
				actPo = clrList.get(i);
				detailPo = new TtVsYearlyPlanDetailPO();
				// 删除明细表
				yearPlanImportService.deleteTtVsYearlyPlanDetail(detailPo);
				// 删除业务表
				yearPlanImportService.clearUserYearlyPlan(actPo);
			}
		}
		// 查询要插入到业务表的临时表数据
		List<TmpVsYearlyPlanPO> tmpList = yearPlanImportService.selectTmpVsYearlyPlan(year);
		if (null == tmpList) {
			tmpList = new ArrayList<TmpVsYearlyPlanPO>();
		}
		int plan = 0;
		List<Map> list = yearPlanImportService.findMaxPlanVer(year,planType);
		if(list != null && list.size()>0){
			Map map= list.get(0);
			plan = map.get("PLAN")!=null?Integer.parseInt(map.get("PLAN").toString()):1;
		}
		int planVer = 0;
		int planBak = planVer; //备份，传到页面
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		for (int i = 0; i < tmpList.size(); i++) {
			TmpVsYearlyPlanPO po = new TmpVsYearlyPlanPO();
			po = tmpList.get(i);
			TtVsYearlyPlanPO newPo = new TtVsYearlyPlanPO();
			newPo = yearPlanImportService.getYearlyPlanPo(plan,planType,po, year);	
			planVer = newPo.getInteger("PLAN_VER");
			planBak = planVer >= planBak ? planVer : planBak;
			TtVsYearlyPlanDetailPO detailPo = null;
			for (int j = 1; j < 13; j++) {
				if(po == null || newPo == null) continue;  //跳过本循环
				switch (j) {
				case 1:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(1), new Integer(Integer.valueOf(po.getString("JAN_AMT"))), loginInfo.getUserId());
					break;
				case 2:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(2), new Integer(Integer.valueOf(po.getString("FEB_AMT"))), loginInfo.getUserId());
					break;
				case 3:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(3), new Integer(Integer.valueOf(po.getString("MAR_AMT"))), loginInfo.getUserId());
					break;
				case 4:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(4), new Integer(Integer.valueOf(po.getString("APR_AMT"))), loginInfo.getUserId());
					break;
				case 5:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(5), new Integer(Integer.valueOf(po.getString("MAY_AMOUNT"))), loginInfo.getUserId());
					break;
				case 6:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(6), new Integer(Integer.valueOf(po.getString("JUN_AMT"))), loginInfo.getUserId());
					break;
				case 7:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(7), new Integer(Integer.valueOf(po.getString("JUL_AMT"))), loginInfo.getUserId());
					break;
				case 8:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(8), new Integer(Integer.valueOf(po.getString("AUG_AMT"))), loginInfo.getUserId());
					break;
				case 9:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(9), new Integer(Integer.valueOf(po.getString("SEP_AMT"))), loginInfo.getUserId());
					break;
				case 10:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(10), new Integer(Integer.valueOf(po.getString("OCT_AMT"))), loginInfo.getUserId());
					break;
				case 11:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(11), new Integer(Integer.valueOf(po.getString("NOV_AMT"))), loginInfo.getUserId());
					break;
				default:
					yearPlanImportService.insertDetailPo(po.getString("GROUP_CODE"), newPo.getLong("PLAN_ID"), new Integer(12), new Integer(Integer.valueOf(po.getString("DEC_AMT"))), loginInfo.getUserId());
					break;
				}
			}
		}
		TmpVsYearlyPlanDTO dto = new TmpVsYearlyPlanDTO();
		dto.setAprAmt(Long.valueOf(String.valueOf(planBak)));
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
	
	
	
	
}
