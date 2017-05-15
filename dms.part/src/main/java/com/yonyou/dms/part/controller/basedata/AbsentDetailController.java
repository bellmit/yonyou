
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AbsentDetailController.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月26日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.controller.basedata;

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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.PartPriceAdjustDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtShortPartDTO;
import com.yonyou.dms.part.service.basedata.AbsentDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月26日
*/
@Controller
@TxnConn
@RequestMapping("/basedata/AbsentDetail")
public class AbsentDetailController {
    
    @Autowired
    private AbsentDetailService absentDetailService;
    
    @Autowired
    private ExcelGenerator excelService;
    
    /**
     * 根据查询条件返回对应的用户数据
     * @author zhongshiwei
     * @date 2016年7月18日
     * @param partPriceAdjustSql 
     * @return 
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryShortPart(@RequestParam Map<String, String> map) throws ServiceBizException{
        PageInfoDto pageInfoDto = absentDetailService.queryShortPart(map);
        return pageInfoDto;
    }
    
    /**
     * 保存
     * @param userSelectDto
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/save",method = RequestMethod.PUT)
    public ResponseEntity<TtShortPartDTO> changePrice(@RequestBody TtShortPartDTO dto,UriComponentsBuilder uriCB) {
        absentDetailService.save(dto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/AbsentDetail/save").toUriString());
        return new ResponseEntity<TtShortPartDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 导出
     * @author dingchaoyu
     * @date 2016年7月22日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
         
     @RequestMapping(value = "/export/import", method = RequestMethod.GET)
     public void exportBookPartRelease(@RequestParam Map<String, String> map, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
         List<Map> resultList = absentDetailService.queryShortPartImport(map);
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
         excelData.put("配件缺料明细表", resultList);
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
         exportColumnList.add(new ExcelExportColumn("IS_URGENT","是否急件"));
         exportColumnList.add(new ExcelExportColumn("CLOSE_STATUS","是否已结案"));
         exportColumnList.add(new ExcelExportColumn("SHORT_TYPE","缺料类型"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_CODE","仓库"));
         exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
         exportColumnList.add(new ExcelExportColumn("IN_OUT_TYPE","出入库类型"));
         exportColumnList.add(new ExcelExportColumn("SHEET_NO","单据号码"));
         exportColumnList.add(new ExcelExportColumn("LICENSE","车牌号"));
         exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
         exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","当前库存量"));
         exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
         exportColumnList.add(new ExcelExportColumn("SHORT_QUANTITY","缺件数量"));
         exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME","客户名称"));
         exportColumnList.add(new ExcelExportColumn("PHONE","电话"));
         exportColumnList.add(new ExcelExportColumn("SEND_TIME","发料时间"));
         exportColumnList.add(new ExcelExportColumn("HANDLER","经手人"));
         //生成excel 文件
         excelService.generateExcel(excelData, exportColumnList,FrameworkUtil.getLoginInfo().getDealerName()+"_配件缺料明细表.xls", request, response);

     }
}
