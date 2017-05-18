
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : BrandCarsModels.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月12日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月12日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.controller.basedata;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TmBrandPo;
import com.yonyou.dms.common.domains.PO.basedata.TmSeriesPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.BrandDto;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.ModelDto;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.PackageDto;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.SeriesDto;
import com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService;
import com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService;
import com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService;
import com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 品牌车系车型配置的控制类（四个写在一起）
 * @author yll
 * @date 2016年7月12日
 */
@Controller
@TxnConn
@RequestMapping("/basedata")
@SuppressWarnings("rawtypes")
public class BrandCarsModelsController extends BaseController{
	@Autowired
	private BrandService brandService;

	@Autowired
	private ExcelGenerator excelService;

	/*******************************************************品牌*******************************************************/
	
	/**
	 * 根据查询条件返回对应的品牌数据
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brands" , method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryBrands(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = brandService.queryBrand(queryParam);
		return pageInfoDto;
	}
	   /**
     * 二手车品牌
     * @author yll
     * @date 2016年7月6日
     * @param queryParam
     * @return
     */
	@RequestMapping(value="/brandsSr" , method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryBrandsSr(@RequestParam Map<String, String> queryParam) {
        List<Map> map = brandService.queryBrandSr(queryParam);
        return map;
    }
	   /**
     * 
     * 二手车车系关联
     * @author yll
     * @date 2016年9月13日
     * @param brandsid
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/brandsdictS/{brandscode}/seriessdictS",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySeriessS(@PathVariable String brandscode,@RequestParam Map<String,String> queryParam) {

        queryParam.put("code", brandscode);
        List<Map> serieslist = seriesService.querySeriesS(queryParam);
        return serieslist;
    }
    /**
     * 二手车车型关联
     * LGQ
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/brandsdictS/{brandscode}/seriessdictS/{seriesscode}/modelsdictS",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelectS(@PathVariable("seriesscode") String seriesscode,@RequestParam Map<String,String> queryParam) {

        queryParam.put("code", seriesscode);
        List<Map> modellist = modelService.queryModelS(queryParam);

        return modellist;
    }
    
	/**
	 * 
	 * 提供给下拉框的方法
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdict",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> brandsSelect(@RequestParam Map<String,String> queryParam) {
		List<Map> brandlist = brandService.queryBrand2(queryParam);
		return brandlist;
	}
	
	/**
     * 
     * 提供给下拉框的方法(过滤valid)
     * @author yll
     * @date 2016年7月8日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/brandsdict2",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> brandsSelect2(@RequestParam Map<String,String> queryParam) {
        List<Map> brandlist = brandService.queryBrand3(queryParam);
        return brandlist;
    }
	
	/**
	 * 
	 * 提供给下拉框的方法(过滤oem)
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictOEM",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> brandsSelectOEM(@RequestParam Map<String,String> queryParam) {
		List<Map> brandlist = brandService.queryBrandOEM(queryParam);
		return brandlist;
	}
	
	
	/**
	 *restful,GET提交方式 获取单个品牌信息
	 * 通过@PathVariable获取 、
	 * 根据id获取品牌信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param id 品牌id
	 * @return
	 */
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  getBrandById(@PathVariable(value = "id") String id) {
		TmBrandPo Brand= brandService.getBrandById(id);
		return Brand.toMap();
	}


	/**
	 * 
	 *新增一个品牌数据
	 * @author yll
	 * @date 2016年7月8日
	 * @param brandDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/brands" ,method = RequestMethod.POST)
	public ResponseEntity<BrandDto> addBrand(@RequestBody BrandDto brandDto,UriComponentsBuilder uriCB) {
		String id = brandService.addBrand(brandDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/brands/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<BrandDto>(brandDto,headers, HttpStatus.CREATED);  

	}
	/**
	 * 
	 * 修改品牌信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param brandDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.PUT)
	public ResponseEntity<BrandDto> ModifyBrand(@PathVariable("id") String id,@RequestBody BrandDto brandDto,UriComponentsBuilder uriCB) {

		brandService.modifyBrand(id, brandDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/brands/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<BrandDto>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 删除品牌信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/brands/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBrand(@PathVariable("id") String id) {

		brandService.deleteBrandById(id);
	}
	/**
	 * 
	 * 品牌导出
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/brands/export/excel", method = RequestMethod.GET)
	public void exportBrands(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList =brandService.queryBrandsForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("品牌信息", resultList);
		//String[] keys = {"BRAND_CODE","BRAND_NAME","OEM_TAG","IS_VALID" };
		//String[] columnNames = { "品牌代码", "品牌名称","是否OEM","是否有效" };
		//生成excel 文件
		//excelService.generateExcel(excelData, keys, columnNames, "品牌信息.xls", response);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE","品牌代码"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌名称"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_VALID","是否有效",ExcelDataType.Dict));
		excelService.generateExcel(excelData, exportColumnList, "品牌信息.xls", request, response);
	}
	
	/*******************************************************车系*******************************************************/

	@Autowired
	private SeriesService seriesService;




	/**
	 *根据查询条件返回对应的车系数据
	 * restful,GET提交方式 获取全部车系信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/seriess",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querySeriess(@RequestParam Map<String, String> queryParam) {

		PageInfoDto pageInfoDto = seriesService.querySeries(queryParam);

		return pageInfoDto;
	}
	/**
	 * 
	 * 提供给下拉框的方法 id
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdict/{brandsid}/seriessdict",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySeriess2(@PathVariable String brandsid,@RequestParam Map<String,String> queryParam) {
		System.err.println("********"+brandsid);
		queryParam.put("code", brandsid);
		List<Map> serieslist = seriesService.querySeries2(queryParam);

		return serieslist;
	}
	
	/**
	 * 
	 * 提供给下拉框的方法 id
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictOEM/{brandsid}/seriessdictOEM",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySeriessOEM(@PathVariable String brandsid,@RequestParam Map<String,String> queryParam) {

		queryParam.put("code", brandsid);
		List<Map> serieslist = seriesService.querySeriesOEM(queryParam);

		return serieslist;
	}
	/**
	 * 
	 * 提供给下拉框的方法 code
	 * @author yll
	 * @date 2016年9月13日
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictC/{brandscode}/seriessdictC",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> querySeriessC(@PathVariable String brandscode,@RequestParam Map<String,String> queryParam) {

		queryParam.put("code", brandscode);
		List<Map> serieslist = seriesService.querySeriesC(queryParam);
		return serieslist;
	}
	
	
	@RequestMapping(value="/brandsdictCSr/{brandscode}/seriessdictCSr",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySeriessCSr(@PathVariable String brandscode,@RequestParam Map<String,String> queryParam) {

        queryParam.put("code", brandscode);
        List<Map> serieslist = seriesService.querySeriesCSr(queryParam);
        return serieslist;
    }
	
	   /**
     * 
     * 提供给下拉框的方法 code
     * @author yll
     * @date 2016年9月13日
     * @param brandsid
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/brandsdictC/seriessdict",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySeriess(@PathVariable String brandscode,@RequestParam Map<String,String> queryParam) {

        queryParam.put("code", brandscode);
        List<Map> serieslist = seriesService.querySeriesC(queryParam);
        return serieslist;
    }

	/**
	 * restful,GET提交方式 获取单个车系信息
	 * 通过@PathVariable获取 
	 * 根据id查找车系
	 * @author yll
	 * @date 2016年7月6日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/seriess/{scode}/{bcode}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getSeriesById(@PathVariable(value = "scode") String scode,@PathVariable(value = "bcode") String bcode) {
		TmSeriesPo series= seriesService.getSeriesById(scode, bcode);
		return series.toMap();
	}


	/**
	 *通过传入信息对车系信息进行新增操作
	 * restful,POST提交方式 添加新 
	 * @author yll
	 * @date 2016年7月6日
	 * @param seriesDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/seriess",method = RequestMethod.POST)
	public ResponseEntity<SeriesDto> addSeries(@RequestBody SeriesDto seriesDto,UriComponentsBuilder uriCB) {
		String id = seriesService.addSeries(seriesDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/seriess/{scode}/{bcode}").buildAndExpand(id,seriesDto.getBrandId()).toUriString());  
		return new ResponseEntity<SeriesDto>(seriesDto,headers, HttpStatus.CREATED);  

	}
	/**
	 * 
	 * 修改车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param seriesDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/seriess/{scode}/{bcode}", method = RequestMethod.PUT)
	public ResponseEntity<SeriesDto> ModifySeries(@PathVariable("scode") String scode,@PathVariable("bcode") String bcode,@RequestBody SeriesDto seriesDto,UriComponentsBuilder uriCB) {

		seriesService.modifySeries(scode, bcode, seriesDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/seriess/{scode}/{bcode}").buildAndExpand(scode,bcode).toUriString());  
		return new ResponseEntity<SeriesDto>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 删除车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/seriess/{scode}/{bcode}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteSeries(@PathVariable("scode") String scode,@PathVariable("bcode") String bcode) {
		seriesService.deleteSeriesById(scode, bcode);
	}
	/**
	 * 
	 * 车系导出
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/seriess/export/excel", method = RequestMethod.GET)
	public void exportSeriess(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList =seriesService.querySeriessForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车系信息", resultList);
		//String[] keys = {"BRAND_NAME","SERIES_CODE","SERIES_NAME","OEM_TAG","IS_VALID"};
		//String[] columnNames = { "品牌名称", "车系代码","车系名称","是否OEM","是否有效" };
		//生成excel 文件
		//excelService.generateExcel(excelData, keys, columnNames, "车系信息.xls", response);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌名称"));
		exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_VALID","是否有效",ExcelDataType.Dict));
		excelService.generateExcel(excelData, exportColumnList, "车系信息.xls", request, response);

	}
	
	/*******************************************************车型*******************************************************/

	@Autowired
	private ModelService modelService;




	/**
	 *根据查询条件返回对应的车型数据
	 * restful,GET提交方式 获取全部车型信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/models",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryModels(@RequestParam Map<String, String> queryParam) {

		PageInfoDto pageInfoDto = modelService.queryModel(queryParam);

		return pageInfoDto;
	}

	/**
	 * 
	 * 提供给下拉框的方法 Id
	 * @author yll
	 * @date 2016年9月13日
	 * @param seriessid
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdict/{brandsid}/seriessdict/{seriessid}/modelsdict",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> modelSelectI(@PathVariable("seriessid") String seriessid,@RequestParam Map<String,String> queryParam) {
		queryParam.put("seriescode", seriessid);
		List<Map> modellist = modelService.queryModel2(queryParam);

		return modellist;
	}
	
	/**
	 * 
	 * 提供给下拉框的方法 Id
	 * @author yll
	 * @date 2016年9月13日
	 * @param seriessid
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictOEM/{brandsid}/seriessdictOEM/{seriessid}/modelsdictOEM",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> modelSelectOEM(@PathVariable("seriessid") String seriessid,@RequestParam Map<String,String> queryParam) {
		queryParam.put("code", seriessid);
		List<Map> modellist = modelService.queryModelOEM(queryParam);

		return modellist;
	}
	/**
	 * 
	 * 提供给下拉框的方法 code
	 * @author yll
	 * @date 2016年9月13日
	 * @param seriessid
	 * @param brandsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictC/{brandscode}/seriessdictC/{seriesscode}/modelsdictC",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> modelSelectC(@PathVariable("seriesscode") String seriesscode,@RequestParam Map<String,String> queryParam) {

		queryParam.put("code", seriesscode);
		List<Map> modellist = modelService.queryModelC(queryParam);

		return modellist;
	}
	
	//model
	@RequestMapping(value="/brandsdictCSr/{brandscode}/seriessdictCSr/{seriesscode}/modelsdictCSr",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelectCSr(@PathVariable("seriesscode") String seriesscode,@RequestParam Map<String,String> queryParam) {

        queryParam.put("code", seriesscode);
        List<Map> modellist = modelService.queryModelCSr(queryParam);

        return modellist;
    }


	/**
	 * restful,GET提交方式 获取单个车型信息
	 * 通过@PathVariable获取 
	 * 根据id查找车系
	 * @author yll
	 * @date 2016年7月6日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/models/{mcode}/{scode}/{bcode}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getModelById(@PathVariable(value = "mcode") String mcode,@PathVariable(value = "scode") String scode,@PathVariable(value = "bcode") String bcode) {
		Map<String,Object> map=modelService.getModelById(mcode, scode, bcode);
		return map;
	}


	/**
	 *通过传入信息对车型信息进行新增操作
	 * restful,POST提交方式 添加新 
	 * @author yll
	 * @date 2016年7月6日
	 * @param modelDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/models",method = RequestMethod.POST)
	public ResponseEntity<ModelDto> addModel(@RequestBody ModelDto modelDto,UriComponentsBuilder uriCB) {
		modelService.addModel(modelDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/models/{mcode}/{scode}/{bcode}").buildAndExpand(modelDto.getModelCode(),modelDto.getSeriesId(),modelDto.getBrandId()).toUriString());  
		return new ResponseEntity<ModelDto>(modelDto,headers, HttpStatus.CREATED);  

	}
	/**
	 * 
	 * 修改车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param modelDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/models/{mcode}/{scode}/{bcode}", method = RequestMethod.PUT)
	public ResponseEntity<ModelDto> ModifyModel(@PathVariable("mcode") String mcode,@PathVariable("scode") String scode,@PathVariable("bcode") String bcode,@RequestBody ModelDto modelDto,UriComponentsBuilder uriCB) {
		modelService.modifyModel(mcode,scode,bcode, modelDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/models/{mcode}/{scode}/{bcode}").buildAndExpand(modelDto.getModelCode(),modelDto.getSeriesId(),modelDto.getBrandId()).toUriString());  
		return new ResponseEntity<ModelDto>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 删除车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/models/{mcode}/{scode}/{bcode}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteModel(@PathVariable("mcode") String mcode,@PathVariable("scode") String scode,@PathVariable("bcode") String bcode,@RequestBody ModelDto modelDto) {

		modelService.deleteModelById(mcode,scode,bcode);
	}
	/**
	 * 
	 * 车型导出
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/models/export/excel", method = RequestMethod.GET)
	public void exportModels(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Map> resultList =modelService.queryModelsForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车型信息", resultList);
		//String[] keys = {"BRAND_NAME","SERIES_NAME","MODEL_CODE","MODEL_NAME","MODEL_GROUP_NAME","OEM_TAG","IS_VALID","MODEL_LABOUR_CODE"};
		//String[] columnNames = { "品牌名称", "车系名称","车型代码","车型名称","车型维修项目分组","是否OEM","是否有效","索赔车型组名称"};
		//生成excel 文件
		//excelService.generateExcel(excelData, keys, columnNames, "车型信息.xls", response);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌名称"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_LABOUR_NAME","车型维修项目分组"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_VALID","是否有效",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("MODEL_LABOUR_NAME","索赔车型组名称"));
		excelService.generateExcel(excelData, exportColumnList, "车型信息.xls", request, response);
	}
	
    /**
     * 提供下拉框查询维修车型信息 
    * TODO description
    * @author yangjie
    * @date 2016年12月22日
    * @param queryParam
    * @return
     */
	@RequestMapping(value="/laboursdict",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelectLabour(@RequestParam Map<String,String> queryParam) {
        List<Map> modellist = modelService.queryLabour(queryParam);
        return modellist;
    }
	
	/**
	 * 提供下拉框查询工时单价
	* TODO description
	* @author yangjie
	* @date 2016年12月22日
	* @param queryParam
	* @return
	 */
	@RequestMapping(value="/pricedict",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelectPrice(@RequestParam Map<String,String> queryParam) {
        List<Map> modellist = modelService.queryPrice(queryParam);
        return modellist;
    }

	/*******************************************************配置*******************************************************/


    /////////////配置
    @Autowired
    private PackageService packageService;
	
	/**
	 *根据查询条件返回对应的配置数据
	 * restful,GET提交方式 获取全部用户信息
	 * @author yll
	 * @date 2016年7月7日
	 * @param queryParam 查询条件
	 * @return
	 */
	@RequestMapping(value="/packages",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryConfigurations(@RequestParam Map<String, String> queryParam) {

		PageInfoDto pageInfoDto = packageService.queryConfiguration(queryParam);

		return pageInfoDto;
	}
	/**
	 * 
	 * 提供给下拉框的方法id
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdict/{brandsid}/seriessdict/{seriessid}/modelsdict/{modelsid}/packagesdict",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> packageSelectI(@PathVariable("modelsid") String modelsid,@RequestParam Map<String,String> queryParam) {
		queryParam.put("code", modelsid);
		
		List<Map> configurationlist = packageService.queryConfiguration2(queryParam);

		return configurationlist;
	}
	
	/**
	 * 
	 * 提供给下拉框的方法id
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictOEM/{brandid}/seriessdictOEM/{seriesid}/modelsdictOEM/{modelsid}/packagesdictOEM",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> packageSelectOEM(@PathVariable("modelsid") String modelsid,@RequestParam Map<String,String> queryParam) {
		queryParam.put("code", modelsid);
		List<Map> configurationlist = packageService.queryConfigurationOEM(queryParam);

		return configurationlist;
	}
	/**
	 * 
	 * 提供给下拉框的方法code
	 * @author yll
	 * @date 2016年9月13日
	 * @param seriessid
	 * @param brandsid
	 * @param modelsid
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/brandsdictC/{brandcode}/seriessdictC/{seriescode}/modelsdictC/{modelscode}/packagesdictC",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> packageSelectC(@PathVariable("modelscode") String modelscode,@RequestParam Map<String,String> queryParam) {
		queryParam.put("code", modelscode);
		List<Map> configurationlist = packageService.queryPackageC(queryParam);
		return configurationlist;
	}
	
	/**
	 * 
	 * 提供给下拉框的方法code
	 * @author  
	 * @date 2016年12月26日
	 * @param modelsid
	 * @param queryParam
	 * @return
	 */		
	@RequestMapping(value="/intentionModel",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> packageSelectModel(@RequestParam Map<String,String> queryParam) {	
		List<Map> configurationlist = packageService.queryPackageSelectModel(queryParam);
		return configurationlist;
	}



	/**
	 * restful,GET提交方式 获取单个信息
	 * 通过@PathVariable获取 
	 * 根据id查找车系
	 * @author yll
	 * @date 2016年7月7日
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/packages/{id}", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object> getConfigurationById(@PathVariable(value = "id") String id) {
		String str = id.replace("@@@", "/");
		String str2 = str.replace("**", ".");
		return  packageService.getConfigurationById(str2);
	}


	/**
	 *通过传入信息对战败车型信息进行新增操作
	 * restful,POST提交方式 添加新 
	 * @author yll
	 * @date 2016年7月6日
	 * @param PackageDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/packages",method = RequestMethod.POST)
	public ResponseEntity<PackageDto> addConfiguration(@RequestBody PackageDto configurationDto,UriComponentsBuilder uriCB) {
		packageService.addConfiguration(configurationDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/packages/{id}").buildAndExpand(configurationDto.getConfigCode()).toUriString());  
		return new ResponseEntity<PackageDto>(configurationDto,headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 修改配置信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param configurationDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/packages/{id}", method = RequestMethod.PUT)
	public ResponseEntity<PackageDto> ModifyConfiguration(@PathVariable("id") String id,@RequestBody PackageDto configurationDto,UriComponentsBuilder uriCB) {
		id = id.replace("@@@", "/");//转义
		id = id.replace("**", ".");//转义
		packageService.modifyConfiguration(id, configurationDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/packages/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<PackageDto>(headers, HttpStatus.CREATED);  
	}

	/**
	 * 
	 * 根据id删除配置信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param uriCB
	 */
	@RequestMapping(value = "/packages/{bcode}/{scode}/{mcode}/{ccode}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteConfiguration(@PathVariable("bcode") String bcode,@PathVariable("scode") String scode,@PathVariable("mcode") String mcode,@PathVariable("ccode") String ccode) {
		packageService.deleteConfigurationById(bcode, scode, mcode, ccode);

	}
	/**
	 * 
	 * 配置导出
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/packages/export/excel", method = RequestMethod.GET)
	public void exportConfis(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList =packageService.queryConfisForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("配置信息", resultList);
		//String[] keys = {"BRAND_NAME","SERIES_NAME","MODEL_CODE","MODEL_NAME","CONFIG_CODE","CONFIG_NAME","OEM_TAG","IS_VALID"};
		//String[] columnNames = { "品牌名称", "车系名称","车型代码","车型名称","配置代码","配置名称","是否OEM" ,"是否有效"};
		//生成excel 文件
		//excelService.generateExcel(excelData, keys, columnNames, "配置信息.xls", response);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌名称"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
		exportColumnList.add(new ExcelExportColumn("CONFIG_CODE","配置代码"));
		exportColumnList.add(new ExcelExportColumn("CONFIG_NAME","配置名称"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_VALID","是否有效",ExcelDataType.Dict));
		excelService.generateExcel(excelData, exportColumnList, "配置信息.xls", request, response);
	}
	
	/**
     * 
     * 下拉框直接获取车系信息
     * @author Benzc
     * @date 2016年12月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/selectSeries",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> seriesSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> serieslist = brandService.querySeries(queryParam);
        return serieslist;
    }
    
    
    @RequestMapping(value="/selectSeriesSr",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> seriesSelectSr(@RequestParam Map<String,String> queryParam) {
        List<Map> serieslist = brandService.querySeriesSr(queryParam);
        return serieslist;
    }
    

    
	/**
     * 
     * 下拉框直接获取车型信息
     * @author Benzc
     * @date 2016年12月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/selectModel",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> modellist = modelService.selectModel(queryParam);
        System.out.println("----"+modellist.get(0));
        return modellist;
    }
    
    
    @RequestMapping(value="/selectModelSr",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> modelSelectSr(@RequestParam Map<String,String> queryParam) {
        List<Map> modellist = modelService.selectModelSr(queryParam);
        System.out.println("----"+modellist.get(0));
        return modellist;
    }
    
	/**
     * 
     * 下拉框直接获取配置信息
     * @author Benzc
     * @date 2016年12月28日
     * @param queryParam
     * @return
     */
    @RequestMapping(value="/selectConfig",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> configSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> configlist = packageService.queryConfiguration2(queryParam);
        return configlist;
    }

}
