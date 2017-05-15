package com.yonyou.dms.part.controller.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialReleaseDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtObsoleteMaterialApplyDcsDTO;
import com.yonyou.dms.part.service.basedata.PartObsoleteMaterialReleseService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 呆滞品发布信息查询
 * @author ZhaoZ
 *@date 2017年4月12日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partObsoleteMaterialRelese")
public class PartObsoleteMaterialReleseController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartObsoleteMaterialReleseService service;
    @Autowired
   	private ExcelGenerator excelService;
    
    /**
  	 * 呆滞品发布信息查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/queryPartObsoleteMaterialRelese",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto queryPartObsoleteMaterialRelese(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====呆滞品发布信息查询=====");
  		
  		 return service.queryPartObsoleteMaterialList(queryParams);
  		
  	}
  	
  	/**
	 * 呆滞品发布信息下载
	 */
	@RequestMapping(value="/download",method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============呆滞品发布信息下载===============");
		List<Map> dealerList = service.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("呆滞品发布信息下载",dealerList);
		exportColumnList.add(new ExcelExportColumn("ORGMAX", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORGMIN", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_TEL", "联系电话"));
		exportColumnList.add(new ExcelExportColumn("WAREHOUSE", "仓库"));
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("RELEASE_NUMBER", "发布数量"));
		exportColumnList.add(new ExcelExportColumn("APPLY_NUMBER", "可申请数量"));
		exportColumnList.add(new ExcelExportColumn("SALES_PRICE", "单价"));
		exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布日期"));
		exportColumnList.add(new ExcelExportColumn("END_DATE", "结束日期"));
		excelService.generateExcel(excelData, exportColumnList, "呆滞品发布信息.xls", request, response);
	}
  	
	/**
	  * 进入呆滞品申请页面
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/applyPartObsoleteMaterialRelese/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> applyPartObsoleteMaterialRelese(@PathVariable(value = "id") Long id) {
		logger.info("=====进入呆滞品申请页面=====");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Map<String, Object> mapA = service.queryReleaseById(id);
		TmDealerPO dealerPO = TmDealerPO.findFirst("DEALER_CODE = ?", loginInfo.getDealerCode());
		if(dealerPO!=null){
			Map<String, Object> mapB = dealerPO.toMap();
			mapA.putAll(mapB);
		}
		return mapA;
	}
	
	/**
	 * 呆滞品申请
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/applyRelease", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtObsoleteMaterialApplyDcsDTO> applyRelease(@RequestBody TtObsoleteMaterialApplyDcsDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================呆滞品申请================");
		service.insertApplyRelease(dto);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/applyRelease").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 取消发布
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/cancelApply/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> cancelApply(@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB){
		logger.info("==================取消发布================");
		TtObsoleteMaterialReleaseDcsPO updatePo = TtObsoleteMaterialReleaseDcsPO.findById(id);
		updatePo.setInteger("STATUS",OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_02);//修改成下架状态
		updatePo.setDate("END_DATE",new Date());
		updatePo.setInteger("CANCEL_TYPE",1);//下架类型改为取消发布下架
		boolean flag = false;
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("取消发布失败！");
		}
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/cancelApply").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
}
