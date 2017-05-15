package com.yonyou.dms.vehicle.controller.k4OrderExecuteManage;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.yonyou.dms.common.domains.PO.basedata.TiK4VsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.dao.k4OrderExecuteManage.OrderExecuteConfirmDao;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsOrderDTO;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderExecuteConfirmService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
 * @ClassName: OrderExecuteConfirmController
 * @Description: JV订单执行管理
 * @author zhengzengliang
 * @date 2017年3月3日 下午5:18:07
 *
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/orderExecuteConfirm")
public class OrderExecuteConfirmController {
	@Autowired
	private OrderExecuteConfirmDao orderExecuteConfirmDao;
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);

	@Autowired
	private OrderExecuteConfirmService orderExecuteConfirmservice;

	/**
	 * 
	 * @Title: getYearList @Description: 订单执行确认 （年列表） @param @return
	 *         设定文件 @return List<Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/getYearList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearList() {
		logger.info("============ 订单执行确认  （年列表）===============");
		List<Map> yearList = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR); // 获取当前年份
		Map<String, Object> yearMap = new HashMap<String, Object>();
		yearMap.put("ORDER_YEAR", year - 1);
		yearList.add(yearMap);
		Map<String, Object> yearMap2 = new HashMap<String, Object>();
		yearMap2.put("ORDER_YEAR", year);
		yearList.add(yearMap2);
		Map<String, Object> yearMap3 = new HashMap<String, Object>();
		yearMap3.put("ORDER_YEAR", year + 1);
		yearList.add(yearMap3);
		return yearList;
	}

	/**
	 * 
	 * @Title: queryWeek @Description: 订单执行确认（周列表） @param @return 设定文件 @return
	 *         List<Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/queryWeek", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryWeek() {
		logger.info("============订单执行确认（周列表）===============");
		List<Map> weekList = orderExecuteConfirmservice.queryWeek();
		return weekList;
	}

	/**
	 * 
	 * @Title: orderExecuteConfirmInfo @Description: 订单执行确认（查询） @param @param
	 *         queryParam @param @return 设定文件 @return PageInfoDto 返回类型 @throws
	 */
	@RequestMapping(value = "/orderExecuteConfirmInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderExecuteConfirmInfo(@RequestParam Map<String, String> queryParam) {
		logger.info("============订单执行确认（查询）===============");
		PageInfoDto pageInfoDto = orderExecuteConfirmservice.getOrderExecuteConfirmInfoQueryList(queryParam);
		return pageInfoDto;
	}

	/**
	 * 
	 * @Title: getAddressAndDealerCode @Description:
	 *         订单执行确认（查询运达方代码和收货地址） @param @return 设定文件 @return List
	 *         <Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/getAddressAndDealerCode", method = RequestMethod.GET)
	@ResponseBody
	public Map getAddressAndDealerCode() {
		logger.info("============订单执行确认（查询运达方代码和收货地址） ===============");
		List<Map> list = orderExecuteConfirmservice.getAddressAndDealerCode();
		Map map = new HashMap();
		if (list.size() > 0) {
			map = list.get(0);
		}
		return map;
	}

	/**
	 * 
	 * @Title: getPaymentList @Description: 订单执行确认（订单付款方式 ） @param @return
	 *         设定文件 @return List<Map> 返回类型 @throws
	 */
	@RequestMapping(value = "/getPaymentList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getPaymentList() {
		logger.info("============订单执行确认（订单付款方式） ===============");
		List<Map> List = orderExecuteConfirmservice.getPaymentList();
		return List;
	}

	/**
	 * 
	 * @Title: orderExecuteConfirmValidate @Description:
	 *         订单执行确认验证（车辆用途不是普通不能使用返利） @param @param year @param @param
	 *         queryParam @param @return 设定文件 @return Map
	 *         <String,Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/orderExecuteConfirmValidate/{orderNos}/{isRebate}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orderExecuteConfirmValidate(@PathVariable("orderNos") String orderNos,
			@PathVariable("isRebate") String isRebate, @RequestParam Map<String, String> queryParam) {
		logger.info("=============订单执行确认验证（车辆用途不是普通不能使用返利） ==============");
		queryParam.put("orderNos", orderNos);
		queryParam.put("isRebate", isRebate);
		Map<String, Object> map = orderExecuteConfirmservice.orderExecuteConfirmValidate(queryParam);

		return map;
	}

	/**
	 * 
	 * @Title: checkOrderNoStatus @Description:校验当前订单是否为可确认状态 @param @param
	 *         ttVsOrderDTO @param @param queryParam @param @return 设定文件 @return
	 *         Map<String,Object> 返回类型 @throws
	 */
	@RequestMapping(value = "/checkOrderNoStatus/{orderNos}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkOrderNoStatus(@PathVariable("orderNos") String orderNos,
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============订单执行确认验证（校验当前订单是否为可确认状态） ==============");
		queryParam.put("orderNos", orderNos);
		Map<String, Object> map = orderExecuteConfirmservice.checkOrderNoStatus(queryParam);

		return map;
	}

	/**
	 * 
	 * @Title: orderExecuteConfirmCommit
	 * @Description: 订单执行确认
	 * @param @param
	 *            uriCB
	 * @param @param
	 *            ttVsOrderDTO
	 * @param @param
	 *            queryParam
	 * @param @return
	 *            设定文件
	 * @return ResponseEntity<TtVsOrderDTO> 返回类型
	 * @throws /orderExecuteConfirm/orderExecuteConfirmCommit/{orderNos}/{
	 *             dealerCodeName}/{paymentTypeName}/{isRebate}
	 */
	@RequestMapping(value = "/orderExecuteConfirmCommit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtVsOrderDTO> orderExecuteConfirmCommit(@RequestBody @Valid TtVsOrderDTO ttVsOrderDTO) {
		logger.info("=============订单执行确认  ==============");
		String[] arrOrderNos = ttVsOrderDTO.getOrderNos().split(",");
		String dealerCode = ttVsOrderDTO.getDealerCodeName();
		String paymentType = ttVsOrderDTO.getPaymentTypeName();
		Integer isRebate = ttVsOrderDTO.getIsRebate();
		// 日期格式化
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HHmmss");
		TtVsOrderPO tvoPo1 = new TtVsOrderPO();
		Long orderId = 0L;
		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		for (int i = 0; i < arrOrderNos.length; i++) {
			String orderNo = arrOrderNos[i];
			List<Map> tvoist = orderExecuteConfirmDao.selectVehicleUse(orderNo);
			if (tvoist.size() > 0) {
				for (Map po : tvoist) {
					if (!OemDictCodeConstants.VEHICLE_USAGE_TYPE_68.equals(po.get("VEHICLE_USE"))
							&& OemDictCodeConstants.IF_TYPE_YES.equals(isRebate)) {
						throw new ServiceBizException("特殊车辆用途的订单不能使用返利");
					}
				}
			}
			List<Map> tvoList = orderExecuteConfirmservice.selectTtVsOrder(arrOrderNos[i]);
			if (tvoList.size() != 0) {
				TiK4VsOrderPO tkvoPo = new TiK4VsOrderPO();
				tkvoPo.setString("ORDER_NO", arrOrderNos[i]);
				List<TmDealerPO> tdList = orderExecuteConfirmservice.selectTmDealerByDealerCode(dealerCode);
				/* 从订单表取售方经销商信息 start */
				List<TmDealerPO> tdList1 = orderExecuteConfirmservice
						.selectTmDealerByDealerId((BigDecimal) (tvoList.get(0).get("DEALER_ID")));
				if (tdList1.size() != 0) {
					tkvoPo.setString("DEALER_CODE", tdList1.get(0).getString("DEALER_CODE"));
				}
				/* 从订单表取售方经销商信息 end */
				if (tdList.size() != 0) {
					// 更新运达方
					// tvoPo1.setBigDecimal("RECEIVES_DEALER_ID",
					// tdList.get(0).getBigDecimal("DEALER_ID"));
					// // 运达方地址
					// tvoPo1.setString("ADDRESS",
					// tdList.get(0).getString("ADDRESS"));
					TtVsOrderPO.update("RECEIVES_DEALER_ID=?,ADDRESS=?", "ORDER_NO=?",
							tdList.get(0).getBigDecimal("DEALER_ID"), tdList.get(0).getString("ADDRESS"), orderNo);
				}
				// tvoPo1.setBigDecimal("IS_SEND",
				// OemDictCodeConstants.IF_TYPE_NO);
				tkvoPo.setString("CREATE_DATE", sdf.format(tvoList.get(0).get("CREATE_DATE")));
				tkvoPo.setString("CREATE_TIME", sdf1.format(tvoList.get(0).get("CREATE_DATE")));
				tkvoPo.setString("RECEIVES_DEALER_CODE", dealerCode);
				tkvoPo.setString("SAP_ORDER_TYPE", CommonUtils.checkNull(tvoList.get(0).get("ORDER_TYPE")));
				tkvoPo.setString("PAYMENT_TYPE", paymentType);
				tkvoPo.setString("VIN", tvoList.get(0).get("VIN"));
				tkvoPo.setString("ORDER_NUM", 1);
				// MATERAIL_ID=2015122250596506
				List<Map> vmList = orderExecuteConfirmservice
						.selectVwMaterial(Long.valueOf(tvoList.get(0).get("MATERAIL_ID").toString()));
				if (vmList.size() != 0) {
					tkvoPo.setString("MODEL_CODE", vmList.get(0).get("MODEL_CODE"));
					tkvoPo.setString("MODEL_YEAR", vmList.get(0).get("MODEL_YEAR"));
					tkvoPo.setString("COLOR_CODE", vmList.get(0).get("COLOR_CODE"));
					tkvoPo.setString("TRIM_CODE", vmList.get(0).get("TRIM_CODE"));
					tkvoPo.setString("FACTORY_OPTIONS", vmList.get(0).get("FACTORY_OPTIONS"));
					tkvoPo.setString("STANDARD_OPTION", vmList.get(0).get("STANDARD_OPTION"));
					tkvoPo.setString("LOCAL_OPTION", vmList.get(0).get("LOCAL_OPTION"));
					tkvoPo.setString("SPECIAL_SERIES", vmList.get(0).get("SPECIAL_SERIE_CODE"));
				}
				if (tvoList.get(0).get("ORDER_YEAR") != null && tvoList.get(0).get("ORDER_WEEK") != null) {

					String request_date = orderExecuteConfirmservice.getEndDate(
							Integer.valueOf(tvoList.get(0).get("ORDER_YEAR").toString()),
							Integer.valueOf(tvoList.get(0).get("ORDER_WEEK").toString()));
					tkvoPo.setString("REQUEST_DATE", request_date);
				}
				tkvoPo.setString("VEHICLE_USE", CommonUtils.checkNull(tvoList.get(0).get("VEHICLE_USE")));
				tkvoPo.setString("IS_REBATE", isRebate);
				tkvoPo.setString("SO_NO", tvoList.get(0).get("SO_NO"));
				tkvoPo.setString("IS_RESULT", OemDictCodeConstants.IF_TYPE_NO.toString());
				tkvoPo.setTimestamp("CREATE_DATE2", format);
				tkvoPo.setLong("CREATE_BY", loginInfo.getUserId());
				tkvoPo.setDate("DEAL_ORDER_AFFIRM_DATE", tvoList.get(0).get("DEAL_ORDER_AFFIRM_DATE"));
				tkvoPo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);
				tkvoPo.setInteger("IS_TOP", tvoList.get(0).get("IS_TOP"));
				tkvoPo.setDate("TOP_DATE", tvoList.get(0).get("TOP_DATE"));
				tkvoPo.setInteger("ORDER_WEEK", tvoList.get(0).get("ORDER_WEEK"));
				tkvoPo.setInteger("ORDER_YEAR", tvoList.get(0).get("ORDER_YEAR"));
				tkvoPo.setInteger("IS_FREEZE", tvoList.get(0).get("IS_FREEZE"));
				tkvoPo.insert();
				orderId = Long.valueOf(tvoList.get(0).get("ORDER_ID").toString());
			}
			// 根据订单号 更新付款方式 是否返利 经销商订单确认时间 已确认
			TtVsOrderPO.update("Payment_Type=?,Is_Rebate=?,Deal_Order_Affirm_Date=?,Order_Status=?,IS_SEND=?",
					"order_No=?", paymentType, isRebate, format, OemDictCodeConstants.SALE_ORDER_TYPE_06,
					OemDictCodeConstants.IF_TYPE_NO, orderNo);
			// orderExecuteConfirmservice.updateTtVsOrder(paymentType, isRebate,
			// orderNo);
			// 新增订单历史记录
			orderExecuteConfirmservice.insertHistory(orderId, OemDictCodeConstants.SALE_ORDER_TYPE_06, "经销商订单确认", "",
					loginInfo.getUserId(), loginInfo.getUserName());
		}

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
