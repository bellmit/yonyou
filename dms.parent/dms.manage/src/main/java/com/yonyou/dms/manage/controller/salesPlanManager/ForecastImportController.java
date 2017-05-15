package com.yonyou.dms.manage.controller.salesPlanManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.service.salesPlanManager.ForecastImportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: ForecastImportController 
* @Description: 生产订单管理控制层 
* @author zhengzengliang
* @date 2017年2月14日 下午4:30:08 
*
 */
@Controller
@TxnConn
@RequestMapping("/forecastImport")
@SuppressWarnings({"rawtypes", "unchecked"})
public class ForecastImportController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private ForecastImportService forecastImportService;
	
	/**
	 * 
	* @Title: getYearList 
	* @Description: 生产订单任务下发（获取年）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearList() {
		logger.info("============ 生产订单任务下发（获取年）===============");
		List<Map> yearList = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);	//获取当前年份
		Map<String,Object> yearMap = new HashMap<String,Object>();
		yearMap.put("FORECAST_YEAR", year);
		yearList.add(yearMap);
		Map<String,Object> yearMap2 = new HashMap<String,Object>();
		yearMap2.put("FORECAST_YEAR", year+1);
		yearList.add(yearMap2);
		return yearList;
	}
	
	/**
	 * 
	* @Title: queryByMaterialid1 
	* @Description: 生产订单任务下发（查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryByMaterialid1",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryByMaterialid1(@RequestParam Map<String, String> queryParam) {
		logger.info("============ 生产订单任务下发（查询）===============");
		PageInfoDto pageInfoDto = forecastImportService.queryByMaterialid1(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: addUser 
	* @Description: 生产订单任务下发（新增） 
	* @param @param userDto
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<DemoUserDto>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/addMaterialIds",method = RequestMethod.POST)
    public ResponseEntity<TmpVsMonthlyForecastDTO> addMaterialIds(
    		@RequestBody @Valid TmpVsMonthlyForecastDTO tvmfDto, 
    		UriComponentsBuilder uriCB) {
		logger.info("============生产订单任务下发（新增）===============");
        forecastImportService.addMaterialIds(tvmfDto);
        return new ResponseEntity<>( HttpStatus.CREATED);

    }
	
	/**
	 * 
	* @Title: retailforecastIssued 
	* @Description: 生产订单任务下发（任务下发） 
	* @param @param tvmfDto
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<TmpVsMonthlyForecastDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/retailforecastIssued",method = RequestMethod.POST)
    public ResponseEntity<TmpVsMonthlyForecastDTO> retailforecastIssued(
    		@RequestBody @Valid TmpVsMonthlyForecastDTO tvmfDto, 
    		UriComponentsBuilder uriCB) {
		logger.info("============生产订单任务下发===============");
        Long taskId = forecastImportService.retailforecastIssuedAdd(tvmfDto);
        List<Map> materialList = forecastImportService.getMaterialIdList(tvmfDto);
        Map materialMap = new HashMap();
        for (int i = 0; i < materialList.size(); i++) {
        	materialMap = materialList.get(i);
        	 forecastImportService.addMaterialId(materialMap,taskId);
		}
        //订单任务下发后清空临时表数据
        forecastImportService.deleteTmpVsMonthlyForecast(tvmfDto);
        
        return new ResponseEntity<>( HttpStatus.CREATED);
    }
	
	/**
	 * 
	* @Title: forecastImportOTDInitQuery 
	* @Description: 生产订单任务录入 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastImportOTDInitQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastImportOTDInitQuery(@RequestParam Map<String, 
			String> queryParam) {
		logger.info("============ 生产订单任务录入（查询）===============");
		PageInfoDto pageInfoDto = forecastImportService.getRetailforecasList3(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: forecastImportOTDQuery 
	* @Description: 生产订单任务录入（查询品牌，车系）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastImportOTDQuery/{taskId}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> forecastImportOTDQuery(@PathVariable("taskId") String taskId,
			@RequestParam Map<String, String> queryParam) {
		logger.info("============ 生产订单任务录入（查询品牌，车系）===============");
		queryParam.put("taskId", taskId);
		List<Map> forecastImportOTDList = forecastImportService.forecastImportOTDQuery(queryParam);
		if(forecastImportOTDList.size() >0){
			for(int i=0; i<forecastImportOTDList.size(); i++){
				forecastImportOTDList.get(i).put("TASK_ID", taskId);
			}
		}
		return forecastImportOTDList;
	}
	
	/**
	 * 
	* @Title: forecastOTDImportList 
	* @Description: 生产订单任务录入   N+3预测录入界面(车系,车型,车款)
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastOTDImportList/{taskId}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> forecastOTDImportList(@RequestParam Map<String, String> queryParam,
			@PathVariable("taskId") String taskId ) {
		logger.info("============ 生产订单任务录入(N+3预测录入界面(车系,车型,车款))===============");
		queryParam.put("taskId", taskId);
		List<Map> forecastImportOTDList = forecastImportService.forecastImportOTDQuery(queryParam);
		String groupCode = forecastImportOTDList.get(0).get("SERIES_CODE").toString();
		TmVhclMaterialGroupPO tvmgPo = new TmVhclMaterialGroupPO();
		tvmgPo.setString("GROUP_CODE", groupCode);
		List<Map> tvmgPoList = forecastImportService.selectTmVhclMaterialGroupUnique(tvmgPo);
		
		/*TtVsRetailTaskPO trtPO = new TtVsRetailTaskPO();
		trtPO.set("TASK_ID", Long.valueOf(taskId));
		List<Map> trtList = forecastImportService.selectTtVsRetailTaskUnique(trtPO);*/
		
		//获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		// List<Map> tvmgList = forecastImportService.getForecastPackageOTDFilterList(groupCode,loginInfo,taskId);
		List<Map> totalList =  forecastImportService.getForecastCarOTDListTotal(groupCode,loginInfo,taskId);
		Map map = new HashMap<String,Object>();
		map = totalList.get(0);
		
		/*act.setOutData("S_TOTAL",map.get("UP_LIMIT")+"-"+map.get("LOWER_LIMIT"));
		act.setOutData("LOWER_LIMIT",map.get("LOWER_LIMIT"));
		act.setOutData("UP_LIMIT",map.get("UP_LIMIT"));
		act.setOutData("FORECAST_TOTAL",FORECAST_TOTAL);*/
		
		/*TtVsMonthlyForecastPercentPO tvmfp = new TtVsMonthlyForecastPercentPO();
		List tvmfpList = dao.select(tvmfp);*/
		
		/*act.setOutData("isSetColor", "2");//录入页面初始化
		act.setOutData("notSumColor", "2");//录入页面初始化
		act.setOutData("taskId", taskId);
		act.setOutData("tvmgPo", tvmgPo);
		act.setOutData("groupId", groupCode);
		act.setOutData("tvmgList", tvmgList);*/
		
		if(tvmgPoList.size() >0){
			for(int i=0; i<tvmgPoList.size(); i++){
				tvmgPoList.get(i).put("S_TOTAL",map.get("UP_LIMIT")+"-"+map.get("LOWER_LIMIT"));
				tvmgPoList.get(i).put("LOWER_LIMIT",map.get("LOWER_LIMIT"));
				tvmgPoList.get(i).put("UP_LIMIT",map.get("UP_LIMIT"));
				tvmgPoList.get(i).put("FORECAST_TOTAL", map.get("FORECAST_TOTAL"));
			}
		}

		return tvmgPoList;
	}
	
	/**
	 * 
	* @Title: forecastOTDImportList 
	* @Description: 生产订单任务录入   N+3预测录入界面(车系,车型,车款)2
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastOTDImportList2/{taskId}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> forecastOTDImportList2(@RequestParam Map<String, String> queryParam,
			@PathVariable("taskId") String taskId) {
		logger.info("============ 生产订单任务录入2(N+3预测录入界面(车系,车型,车款)2)===============");
		queryParam.put("taskId", taskId);
		List<Map> forecastImportOTDList = forecastImportService.forecastImportOTDQuery(queryParam);
		String groupCode = forecastImportOTDList.get(0).get("SERIES_CODE").toString();
		
		/*TmVhclMaterialGroupPO tvmgPo = new TmVhclMaterialGroupPO();
		tvmgPo.setString("GROUP_CODE", groupCode);
		List<Map> tvmgPoList = forecastImportService.selectTmVhclMaterialGroupUnique(tvmgPo);*/
		
		/*TtVsRetailTaskPO trtPO = new TtVsRetailTaskPO();
		trtPO.set("TASK_ID", Long.valueOf(taskId));
		List<Map> trtList = forecastImportService.selectTtVsRetailTaskUnique(trtPO);*/
		
		//获取当前用户
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> tvmgList = forecastImportService.getForecastPackageOTDFilterList(groupCode,loginInfo,taskId);
		List<Map> totalList =  forecastImportService.getForecastCarOTDListTotal(groupCode,loginInfo,taskId);
		
		Map map = new HashMap<String,Object>();
		map = totalList.get(0);

		/*act.setOutData("S_TOTAL",map.get("UP_LIMIT")+"-"+map.get("LOWER_LIMIT"));
		act.setOutData("LOWER_LIMIT",map.get("LOWER_LIMIT"));
		act.setOutData("UP_LIMIT",map.get("UP_LIMIT"));
		act.setOutData("FORECAST_TOTAL",FORECAST_TOTAL);*/
		
		/*TtVsMonthlyForecastPercentPO tvmfp = new TtVsMonthlyForecastPercentPO();
		List tvmfpList = dao.select(tvmfp);*/
		
		/*act.setOutData("isSetColor", "2");//录入页面初始化
		act.setOutData("notSumColor", "2");//录入页面初始化
		act.setOutData("taskId", taskId);
		act.setOutData("tvmgPo", tvmgPo);
		act.setOutData("groupId", groupCode);
		act.setOutData("tvmgList", tvmgList);*/
		
		if(tvmgList.size() >0){
			for(int i=0; i<tvmgList.size(); i++){
				tvmgList.get(i).put("S_TOTAL",map.get("UP_LIMIT")+"-"+map.get("LOWER_LIMIT"));
				tvmgList.get(i).put("LOWER_LIMIT",map.get("LOWER_LIMIT"));
				tvmgList.get(i).put("UP_LIMIT",map.get("UP_LIMIT"));
				tvmgList.get(i).put("FORECAST_TOTAL", map.get("FORECAST_TOTAL"));
			}
		}

		return tvmgList;
	}
	
	/**
	 * 
	* @Title: ModifyforecastOTDSubmit 
	* @Description: 生产订单任务录入（提交） 
	* @param @param taskId
	* @param @param userDto
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<DemoUserDto>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/modifyforecastOTDSubmit",
			method = RequestMethod.PUT)
    public ResponseEntity<TtVsMonthlyForecastDTO> modifyforecastOTDSubmit(
    		@PathVariable("taskId") Long taskId, 
    		@RequestBody @Valid TtVsMonthlyForecastDTO tvmfDTO,
            UriComponentsBuilder uriCB) {
		logger.info("============ 生产订单任务录入（提交）===============");
		forecastImportService.modifyforecastOTDSubmit(taskId, tvmfDTO);

        return new ResponseEntity<>( HttpStatus.CREATED);
    }
	
	
}
