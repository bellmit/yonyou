
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : ColorController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月11日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月11日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

import java.util.List;
import java.util.Map;

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

import com.yonyou.dms.common.domains.PO.basedata.TmColorPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.ColorDTO;
import com.yonyou.dms.repair.service.basedata.ColorService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆颜色
 * 
 * @author Benzc
 * @date 2016年12月22日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/colors")
public class ColorController extends BaseController {

    @Autowired
    private ColorService colorservice;

    /**
     * 查询
     * 
     * @author Benzc
     * @date 2016年12月22日
     * @param queryParam
     * @return
     */

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryColor(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = colorservice.QueryColor(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 通过id查询类型信息 TODO description
     * 
     * @author Benzc
     * @date 2016年12月23日
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findById(@PathVariable String id) throws ServiceBizException {
        TmColorPo po = colorservice.findById(id);
        return po.toMap();
    }

    /**
     * 新增
     * 
     * @author Benzc
     * @date 2016年12月22日
     * @param colordto
     * @param uriCB
     * @return
     */

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ColorDTO> addDiscountMode(@RequestBody ColorDTO colordto, UriComponentsBuilder uriCB) {
        colorservice.addColor(colordto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/colors/{id}").buildAndExpand(colordto.getColorCode()).toUriString());
        return new ResponseEntity<ColorDTO>(colordto, headers, HttpStatus.CREATED);
    }

    /**
     * 修改
     * 
     * @author Benzc
     * @date 2016年12月22日
     * @param id
     * @param colordto
     * @param uriCB
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ColorDTO> updateColor(@PathVariable("id") String id, @RequestBody ColorDTO colordto,
                                                UriComponentsBuilder uriCB) {
        colorservice.updateColor(id, colordto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/basedata/colors/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<ColorDTO>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 下拉框查询颜色名称
     * @author Benzc
     * @date 2016年12月28日 
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/colorinfo/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> colorSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> colorlist = colorservice.selectColor(queryParam);
        return colorlist;
    }
}
