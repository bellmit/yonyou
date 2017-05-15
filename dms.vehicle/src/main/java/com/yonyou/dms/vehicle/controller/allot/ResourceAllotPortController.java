package com.yonyou.dms.vehicle.controller.allot;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.vehicle.domains.DTO.allot.ResourceAllotPortDto;
import com.yonyou.dms.vehicle.service.allot.ResourceAllotPortService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/resourceallotport")
public class ResourceAllotPortController {
	@Autowired
	ResourceAllotPortService resourceallotportService;
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotPortController.class);

	/**
	 * 资源分配
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/resourceallotportInt", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> resourceallotportInt() {
		logger.info("==============资源分配港口维护查询=============");
		List<Map> list = resourceallotportService.resourceallotportInt();
		return list;
	}

	/**
	 * 资源分配港口维护修改查询
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") Long id) {
		logger.info("=============资源分配港口维护修改查询==============");
		Map map = resourceallotportService.findById(id);
		return map;

	}

	//
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResourceAllotPortDto> editPayRemind(@RequestBody @Valid ResourceAllotPortDto Dto) throws Exception {
		logger.info("============资源分配港口维护修改===============");
		resourceallotportService.editAllotPort(Dto);
		return new ResponseEntity<>(Dto, HttpStatus.CREATED);

	}

	/**
	 * 资源分配港口维护新增
	 * @throws Exception 
	 * 
	 */

	@RequestMapping(value = "/addResourceallotport", method = RequestMethod.POST)
	public ResponseEntity<ResourceAllotPortDto> addResourceallotport(@RequestBody @Valid ResourceAllotPortDto pyDto,
			UriComponentsBuilder uriCB) throws Exception {
		logger.info("============资源分配港口维护新增===============");

		resourceallotportService.addResourceallotport(pyDto);

		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	//
	@RequestMapping(value = "/delResourceallotport/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<ResourceAllotPortDto> delOrderRepeal(@PathVariable(value = "id") Long id) throws Exception {
		logger.info("=============资源分配港口维护删除==============");
		resourceallotportService.delOrderRepeal(id);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
