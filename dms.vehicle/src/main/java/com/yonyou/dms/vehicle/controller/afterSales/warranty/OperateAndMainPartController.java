package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
import java.text.ParseException;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOptOldptDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.OperateAndMainPartService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 操作代码与主因件维护 
 * @author xuqinqin
 */
@Controller
@TxnConn
@RequestMapping("/operateAndMainPart")
public class OperateAndMainPartController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	OperateAndMainPartService  optAndMpartService;
	
	/**
	 * 操作代码与主因件维护 查询
	 */
	@RequestMapping(value = "/optAndMpartSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto operateAndMainPartSearch(@RequestParam Map<String, String> queryParam) {
		logger.info("==============操作代码与主因件维护 查询=============");
		PageInfoDto pageInfoDto = optAndMpartService.optAndMpartQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 操作代码与主因件维护 新增
	 */
	@RequestMapping(value = "/addOptAndMpart", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrOptOldptDTO> addWorkHour(@RequestBody TtWrOptOldptDTO ptdto, UriComponentsBuilder uriCB) {
		logger.info("=====新增操作代码与主因件维护 =====");
		Long id = optAndMpartService.addOptAndMpart(ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/operateAndMainPartAdd/addOptAndMpart").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}
	@RequestMapping(value="/{ID}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> editBulletinInit(@PathVariable Long ID) throws ParseException{
		logger.info("==================操作代码与主因件维护 修改回显================");
		Map<String, Object> map = optAndMpartService.editOptAndMpart(ID);
		return map;
	}
	/**
	 * 更新操作代码与主因件维护 (保存)
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateOptAndMpart/{ID}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrOptOldptDTO> saveDeliveryOrder(@RequestBody TtWrOptOldptDTO dto,UriComponentsBuilder uriCB,
			@PathVariable(value = "ID") BigDecimal id){
		logger.info("==================操作代码与主因件维护 修改================");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		optAndMpartService.saveOptAndMpart(dto,id,loginUser);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/updateOptAndMpart").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	/**
	 * 操作代码与主因件维护 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/downloadOptAndMpart", method = RequestMethod.GET)
	public void WorkHourDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============操作代码与主因件维护   主页面下载  ===============");
    	optAndMpartService.optAndMpartDownload(queryParam, request, response);
	}
}
