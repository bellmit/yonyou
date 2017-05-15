package com.yonyou.dms.vehicle.controller.k4OrderExecuteManage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.common.domains.PO.basedata.TiK4VsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsOrderDTO;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderExecuteConfirmService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderFreezeService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderTopMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
 * @ClassName: OrderFreezeController
 * @Description: JV订单执行管理
 * @author zhengzengliang
 * @date 2017年3月13日 下午3:39:00
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/orderFreeze")
public class OrderFreezeController {

	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

	@Autowired
	private OrderFreezeService orderFreezeService;

	@Autowired
	private OrderTopMaintainService orderTopMaintainService;

	@Autowired
	private OrderExecuteConfirmService orderExecuteConfirmservice;

	/**
	 * 
	 * @Title: findFreezeReason @Description: 获取冻结原因 @param @param
	 *         queryParam @param @return 设定文件 @return List<Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/findFreezeReason", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findFreezeReason() {
		logger.info("============可冻结订单查询  ===============");
		List<Map> freezeReason = orderFreezeService.findFreezeReason();
		return freezeReason;
	}

	/**
	 * 
	 * @Title: orderFreezeQuery @Description: 可冻结订单查询 @param @param
	 *         queryParam @param @return 设定文件 @return PageInfoDto 返回类型 @throws
	 */
	@RequestMapping(value = "/orderFreezeQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderFreezeQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============可冻结订单查询  ===============");
		PageInfoDto pageInfoDto = orderFreezeService.orderFreezeQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 
	 * @Title: doOrderFreeze @Description: 冻结/解冻订单 @param @param
	 *         type @param @param ttVsOrderDTO @param @return 设定文件 @return List
	 *         <Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/doOrderFreeze/{type}", method = RequestMethod.PUT)
	@ResponseBody
	public List<Map> doOrderFreeze(@PathVariable(value = "type") String type,
			@RequestBody @Valid TtVsOrderDTO ttVsOrderDTO) {
		logger.info("=============冻结/解冻订单 ==============");
		List<Map> msgList = new ArrayList<Map>();
		String[] orderNos = ttVsOrderDTO.getOrderNos().split(",");
		String freezeReason = ttVsOrderDTO.getFreezeReasonName();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		// 冻结
		if ("1".equals(type)) {// 冻结操作
			for (int i = 0; i < orderNos.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderNos[i]);
				String msg = "";
				List<Map> tvoList = orderTopMaintainService.selectTtVsOrderAll(orderNos[i]);
				if (OemDictCodeConstants.IF_TYPE_YES.toString().equals(tvoList.get(0).get("IS_SEND"))) {
					msg = "该订单已发送至SAP。";
				} else {
					// 订单冻结修改订单表
					// 获取当前用户
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TtVsOrderPO.update("IS_FREEZE = ? , FREEZE_REASON = ? , FREEZE_DATE =? ", "ORDER_NO = ?", 10041001,
							freezeReason, format, orderNos[i]);

					if (tvoList.get(0).get("ORDER_STATUS").equals(OemDictCodeConstants.SALE_ORDER_TYPE_06)) {// 已确认订单冻结
						// 订单冻结修改接口表
						TiK4VsOrderPO.update("IS_FREEZE = ? ", "ORDER_NO = ?", 10041001, orderNos[i]);
					}
					List<Map> freezeReasonList = orderFreezeService.getFreezeReason(freezeReason);
					orderExecuteConfirmservice.insertHistory(Long.valueOf(tvoList.get(0).get("ORDER_ID").toString()),
							Integer.valueOf(tvoList.get(0).get("ORDER_STATUS").toString()), "订单截停",
							CommonUtils.checkNull(freezeReasonList.get(0).get("FREEZE_REASON")), loginInfo.getUserId(),
							loginInfo.getUserName());
					msg = "订单截停成功！";
				}
				map.put("msg", msg);
				msgList.add(map);
			}
		} else {// 解冻
			for (int i = 0; i < orderNos.length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderNos[i]);
				String msg = "";
				List<Map> tvoList = orderTopMaintainService.selectTtVsOrderAll(orderNos[i]);
				if ((OemDictCodeConstants.IF_TYPE_NO.toString()).equals(tvoList.get(0).get("IS_FREEZE"))) {
					msg = "该订单已解除截停。";
				} else {
					// 订单解冻修改订单表
					// 获取当前用户
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TtVsOrderPO.update("IS_FREEZE = ? , UNFREEZE_DATE = ? ", "ORDER_NO = ?", 10041002, format,
							orderNos[i]);

					if ((OemDictCodeConstants.SALE_ORDER_TYPE_06).equals(tvoList.get(0).get("ORDER_STATUS"))) {// 已确认订单冻结
						// 订单解冻修改接口表
						TiK4VsOrderPO.update("IS_FREEZE = ? ", "ORDER_NO = ?", 10041002, orderNos[i]);
					}
					orderExecuteConfirmservice.insertHistory(Long.valueOf(tvoList.get(0).get("ORDER_ID").toString()),
							Integer.valueOf(tvoList.get(0).get("ORDER_STATUS").toString()), "订单解除截停", "",
							loginInfo.getUserId(), loginInfo.getUserName());
					msg = "订单解除截停成功！";
				}
				map.put("msg", msg);
				msgList.add(map);
			}
		}

		return msgList;
	}

}
