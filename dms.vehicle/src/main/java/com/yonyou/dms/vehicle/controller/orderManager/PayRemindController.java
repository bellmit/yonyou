package com.yonyou.dms.vehicle.controller.orderManager;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.PayRemindDto;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtPayRemindPO;
import com.yonyou.dms.vehicle.service.orderManage.PayRemindService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
 * 
 * @author 廉兴鲁
 * @date 2016年2月13日
 */
@Controller
@TxnConn
@RequestMapping("/payRemind")

public class PayRemindController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(PayRemindController.class);
	@Autowired
	private PayRemindService payRemindService;

	@RequestMapping(value = "/payRemind", method = RequestMethod.GET)
	// 查询付款通知维护
	@ResponseBody
	public PageInfoDto orderQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============付款通知维护查询===============");
		PageInfoDto pageInfoDto = payRemindService.payRemind(queryParam);
		return pageInfoDto;
	}

	/**
	 * 新增付款提醒
	 * 
	 */

	@RequestMapping(value = "/addPayRemind", method = RequestMethod.POST)
	public ResponseEntity<PayRemindDto> addPayRemind(@RequestBody @Valid PayRemindDto pyDto,
			UriComponentsBuilder uriCB) {
		logger.info("============付款通知新增===============");

		PayRemindDto payDto = payRemindService.addPayaremind(pyDto);

		return new ResponseEntity<>(payDto, HttpStatus.CREATED);

	}

	/**
	 * 查詢付款提醒
	 * 
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable(value = "id") Long id) {
		logger.info("============付款通知修改信息回显===============");
		TtPayRemindPO po = TtPayRemindPO.findById(id);
		return po.toMap();

	}

	/**
	 * 修改付款提醒
	 * 
	 */

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<PayRemindDto> editPayRemind(@PathVariable(value = "id") Long id,
			@RequestBody @Valid PayRemindDto payDto) {
		logger.info("============付款通知修改===============");
		payRemindService.editPayaremind(id, payDto);
		return new ResponseEntity<>(payDto, HttpStatus.CREATED);

	}

	// 删除
	@RequestMapping(value = "/delpayRemind/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<PayRemindDto> delPayRemind(@PathVariable(value = "id") Long id,
			@RequestBody @Valid PayRemindDto payDto) {
		logger.info("============付款通知删除===============");

		payRemindService.delPayaremind(payDto, id);
		return new ResponseEntity<>(payDto, HttpStatus.CREATED);

	}

}
