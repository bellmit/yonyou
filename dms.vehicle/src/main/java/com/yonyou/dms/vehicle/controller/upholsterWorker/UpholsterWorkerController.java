package com.yonyou.dms.vehicle.controller.upholsterWorker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.UpholsterWorker.UpholsterWorkerDTO;
import com.yonyou.dms.vehicle.service.upholsterWorker.UpholsterWorkerService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
* 装潢派工控制类
* @author wangliang
* @date 2017年03月22日
*/

@Controller
@TxnConn
@RequestMapping("/stockManage/UpholsterWorker")
public class UpholsterWorkerController extends BaseController{
	@Autowired
	UpholsterWorkerService upholsterWorkerService;
	
	@Autowired
	private ExcelGenerator      excelService;
	
	/**
	 * 查询装潢派工
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUpholsterWorker(@RequestParam Map<String,String> queryParam){
		System.err.println(queryParam.get("roStatus"));
		System.err.println(queryParam.get("soNo"));
		System.err.println(queryParam.get("vin"));
		System.err.println(queryParam.get("beginDate"));
		System.err.println(queryParam.get("endDate"));
		System.err.println(queryParam.get("serviceAdvisor"));
		System.err.println(queryParam.get("customerName"));
		System.err.println(queryParam.get("completeTag"));
		System.err.println(queryParam.get("license"));
		PageInfoDto pageInfoDto = upholsterWorkerService.queryUpholsterWorker(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value="/decrodateProject",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDecrodateProject(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = upholsterWorkerService.queryDecrodateProject(queryParam);
        return pageInfoDto;
    }
	
	
	/**
	 * 项目添加回显
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryUpholerByid(@PathVariable(value = "id") String id){
		List<Map> result = upholsterWorkerService.queryUpholerByid(id);
		return result.get(0);
	}
	
	/**
	 * 打印回显
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "print/{id}/{id1}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> printrByid(@PathVariable(value = "id") String id,@PathVariable(value = "id1") String id1){
		List<Map> result = upholsterWorkerService.printrByid(id,id1);
		return result.get(0);
	}

	/**
	* 新增装潢项目
	* @author wangliang
	* @date 2017年03月27日
	*/
    @SuppressWarnings("unused")
	@RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> addVisitingRecordInfo(@RequestBody  UpholsterWorkerDTO upholsterWokerDto,@PathVariable(value = "id") String id,
                                                      UriComponentsBuilder uriCB) {
    	
        long id1= upholsterWorkerService.addUpholsterWorkerInfo(upholsterWokerDto,id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<UpholsterWorkerDTO>(upholsterWokerDto, headers, HttpStatus.CREATED);
    }
    
	/**
	* 新增分项派工
	* @author wangliang
	* @date 2017年03月27日
	*/
    @SuppressWarnings("unused")
	@RequestMapping(value = "/addProject/{id}/project/{id1}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> addVisitingRecordInfo(@RequestBody  UpholsterWorkerDTO upholsterWokerDto,@PathVariable(value = "id") String id,@PathVariable(value = "id1") String id1,
                                                      UriComponentsBuilder uriCB) {
    	System.err.println(id);
    	System.err.println(id1);
    	long id2 = upholsterWorkerService.addProject(upholsterWokerDto,id,id1);
        MultiValueMap<String, String> headers = new HttpHeaders();
       // headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<UpholsterWorkerDTO>(upholsterWokerDto, headers, HttpStatus.CREATED);
    }
    
	/**
	* 查询派工技师
	* @author wangliang
	* @date 2017年03月29日
	*/
	@RequestMapping(value = "/Technician",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryTechnician(@RequestParam Map<String,String> queryParam){
		PageInfoDto pageInfoDto = upholsterWorkerService.queryTechnician(queryParam);
		return pageInfoDto;
	}
	
	/**
	* 查询派工工位
	* @author wangliang
	* @date 2017年03月29日
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/LabourPostion",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryLabourPostion(@RequestParam Map<String,String> queryParam){
		List<Map> list= upholsterWorkerService.queryLabourPostion(queryParam);
		return list;
	}
	
	/**
	* 编辑分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> toEditProject(@RequestParam Map<String,String> queryParam,@PathVariable(value = "id") String id){
		Map map= upholsterWorkerService.toEditProject(queryParam,id);
		return map;
	}
	
	/**
	* 修改分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
    @RequestMapping(value = "toedit/{id}/{id1}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> update(@PathVariable("id") Long id,@PathVariable("id1") Long id1,@RequestBody UpholsterWorkerDTO upholesterWorkerDto,UriComponentsBuilder uriCB) throws ServiceBizException{
    	upholsterWorkerService.update(id,id1,upholesterWorkerDto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/stockManage/UpholsterWorker/{id}").buildAndExpand(id).toUriString());  
        return new ResponseEntity<UpholsterWorkerDTO>(headers, HttpStatus.CREATED);  
    }
    
	/**
	* 删除分项派工
	* @author wangliang
	* @date 2017年04月5日
	*/
    @RequestMapping(value = "deleteProject/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
    	upholsterWorkerService.deleteById(id);
    }
    
	/**
	* 查询工种名称
	* @author wangliang
	* @date 2017年04月05日
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/WorkType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryWorkType(@RequestParam Map<String,String> queryParam){
		List<Map> list= upholsterWorkerService.queryWorkType(queryParam);
		return list;
	}
	
	
	/**
	 * 查询整单派工--项目添加
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/SearchWorker/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryUpholsterWorker1(@PathVariable(value = "id") String id){
		List<Map> list= upholsterWorkerService.queryUpholsterWorker(id);
		return list;
	}
	
	
	/**
	 * 查询整单派工--装潢项目
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/SearchWorker1/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUpholsterWorker2(@PathVariable(value = "id") String id){
		PageInfoDto pageInfoDto= upholsterWorkerService.queryUpholsterWorker1(id);
		return pageInfoDto;
	}
	
	/**
	 * 查询整单派工--装潢配件
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@RequestMapping(value = "/part/{id}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySoPart(@PathVariable(value = "id") String id){
		PageInfoDto pageInfoDto = upholsterWorkerService.querySoPart(id);
		return pageInfoDto;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/part1/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySoPart1(@PathVariable(value = "id") String id){
		List<Map> list= upholsterWorkerService.querySoPart1(id);
		return list;
	}
	
	/**
	* 新增整单派工
	* @author wangliang
	* @date 2017年04月1日
	*/
    @SuppressWarnings("unused")
	@RequestMapping(value = "/addUpholsterWorker/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> addUpholesterWorker(@RequestBody  UpholsterWorkerDTO upholsterWokerDto,
    		                           @PathVariable(value = "id") String id,UriComponentsBuilder uriCB) {
        long id1 = upholsterWorkerService.addUpholsterWorker(upholsterWokerDto,id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        //headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<UpholsterWorkerDTO>(upholsterWokerDto, headers, HttpStatus.CREATED);
    }
    
	/**
	 * 查询整单派工--项目
	 * @author wangliang
	 * @date 2017年04月1日
	 */
	@RequestMapping(value = "/SearchWorker/{id}/{id1}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUpholsterWorker2(@PathVariable(value = "id") String id,@PathVariable(value = "id1") String id1){
		PageInfoDto pageInfoDto = upholsterWorkerService.queryUpholsterWorker1(id,id1);
		return pageInfoDto;
	}
	
	/**
	* 竣工
	* @author wangliang
	* @date 2017年04月6日
	*/

	@RequestMapping(value = "/finalChecker/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> finalChecked(@RequestBody  UpholsterWorkerDTO upholsterWokerDto,
    		                           @PathVariable(value = "id") String id,UriComponentsBuilder uriCB) {
        upholsterWorkerService.finalChecked(upholsterWokerDto,id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        //headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<UpholsterWorkerDTO>(upholsterWokerDto, headers, HttpStatus.CREATED);
    }
	
	/**
	* 取消竣工
	* @author wangliang
	* @date 2017年04月6日
	*/

	@RequestMapping(value = "/finalChecker1/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<UpholsterWorkerDTO> finalChecked1(@RequestBody  UpholsterWorkerDTO upholsterWokerDto,
    		                           @PathVariable(value = "id") String id,UriComponentsBuilder uriCB) {
        upholsterWorkerService.finalChecked1(upholsterWokerDto,id);
        MultiValueMap<String, String> headers = new HttpHeaders();
        //headers.set("Location", uriCB.path("/customerManage/visitingRecord/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<UpholsterWorkerDTO>(upholsterWokerDto, headers, HttpStatus.CREATED);
    }
	
	/**
	* 是否竣工提示
	* @author wangliang
	* @date 2017年04月10日
	*/
	@RequestMapping(value = "/isFinshWork/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  int  isFinshWork(@PathVariable(value = "id") String id){
		Integer data= upholsterWorkerService.isFinshWork(id);
		return data;
	}
	/**
	* 是否竣工提示1
	* @author wangliang
	* @date 2017年04月10日
	*/
	@RequestMapping(value = "/isFinshWork1/{id}",method = RequestMethod.GET)
    @ResponseBody
    public  int  toEditProject1(@PathVariable(value = "id") String id){
		Integer data= upholsterWorkerService.isFinshWork1(id);
		return data;
	}
	/**
	* 查询打印技师
	* @author wangliang
	* @date 2017年03月29日
	*/
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/SearchTechnician/{id}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryLabourPostion( @PathVariable(value = "id") String id){
		List<Map> list= upholsterWorkerService.queryPrintTechnician(id);
		return list;
	}
	
	/**
	* 导出
	* @author wangliang
	* @date 2017年04月14日
	*/
	 @SuppressWarnings("rawtypes")
	    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	    public void export(@RequestParam Map<String, String> queryParam, HttpServletRequest request, HttpServletResponse response ) {
	    	List<Map> resultList = upholsterWorkerService.queryUpholsterWorkerExport(queryParam);
	    	Map<String,List<Map>> excelData = new HashMap<String, List<Map>>();
	    	excelData.put("装潢派工", resultList);
	    	List<ExcelExportColumn> exportColmnList = new ArrayList<ExcelExportColumn>();
	    	exportColmnList.add(new ExcelExportColumn("SO_NO","服务订单号"));
	    	exportColmnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
	    	exportColmnList.add(new ExcelExportColumn("LICENSE","车牌号"));
	    	exportColmnList.add(new ExcelExportColumn("VIN","VIN"));
	    	exportColmnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
	    	exportColmnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
	    	exportColmnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
	    	exportColmnList.add(new ExcelExportColumn("SOLD_USER","销售顾问"));
	    	exportColmnList.add(new ExcelExportColumn("COMPLETE_TAG","是否竣工"));
	    	exportColmnList.add(new ExcelExportColumn("SHEET_USER","开单人"));
	    	exportColmnList.add(new ExcelExportColumn("LOCK_USER","锁定人"));
	    	exportColmnList.add(new ExcelExportColumn("SHEET_CREATE_DATE","开单日期"));
	    	exportColmnList.add(new ExcelExportColumn("ASSIGN_STATUS","派工状态"));
	    	exportColmnList.add(new ExcelExportColumn("FINISH_USER","终检人"));

	    	//生成excel文件
	    	excelService.generateExcelForDms(excelData, exportColmnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_PDI检查.xls", request, response);
	    }
	
	public static String getTest(String id) {
		  StringBuffer sql2 = new StringBuffer();
		   
			sql2.append(" (((((A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_GENERAL+") OR A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_GENERAL+") " );//普通服务订单和销售订单
			sql2.append( " AND (A.PAY_OFF="+DictCodeConstants.DICT_IS_YES+" OR LOSSES_PAY_OFF="+DictCodeConstants.DICT_IS_YES+")) " );//已结清 或者 财务审核已通过并挂账已结清
			sql2.append( " OR (A.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+" AND A.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+" "); //售前装潢服务订单
			sql2.append( " AND A.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_DELIVERY_CONFIRMING+")) " );//财务审核已通过(交车确认中)
			sql2.append(" AND (EXISTS (SELECT * FROM TT_SO_PART P WHERE A.DEALER_CODE = P.DEALER_CODE AND A.SO_NO = P.SO_NO) OR AA.COUNT1>0) AND A.COMPLETE_TAG="+DictCodeConstants.DICT_IS_NO+")"); 
		  
			return sql2.toString() ;
	}
	
	public static void main(String[] args) {
		System.err.println(getTest("2100000"));
	}
}
