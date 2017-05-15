package com.yonyou.dms.vehicle.controller.k4OrderExecuteManage;

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
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsOrderDTO;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.ConfirmOrderModifyService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderCancelService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderExecuteConfirmService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderTopMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: OrderCancelController 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月10日 下午4:02:02 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/orderCancel")
public class OrderCancelController {

	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	@Autowired
	private OrderCancelService orderCancelService;
	
	@Autowired
	private ConfirmOrderModifyService confirmOrderModifyService;
	
	@Autowired
	private OrderTopMaintainService orderTopMaintainService ;
	
	@Autowired
	private OrderExecuteConfirmService orderExecuteConfirmservice ;
	
	/**
	 * 
	* @Title: findOrderCancelRemark 
	* @Description: 获取取消备注信息 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/findOrderCancelRemark",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findOrderCancelRemark() {
		logger.info("============ 获取取消备注信息===============");
		List<Map> orderCancelRemark = orderCancelService.orderCancelService();
		return orderCancelRemark;
	}
	
	/**
	 * 
	* @Title: orderCancelQueryDetialInfoListQuery 
	* @Description: 订单取消查询(oem) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/orderCancelQueryDetialInfoListQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderCancelQueryDetialInfoListQuery(
			@RequestParam Map<String, String> queryParam) {
		logger.info("============订单取消查询(oem)===============");
		PageInfoDto pageInfoDto = orderCancelService.findAll(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: checkOrderSendlList 
	* @Description: 订单取消验证 
	* @param @param orderNos
	* @param @param queryParam
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/checkOrderSendlList/{orderNos}",
			method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkOrderSendlList(
			@PathVariable("orderNos") String orderNos,
			@RequestParam Map<String,String> queryParam) {
		logger.info("=============订单取消验证 ==============");
		boolean flag = false;
		String[] arrOrderNo = orderNos.split(",");
		for(int i=0;i<arrOrderNo.length;i++) {//批处理数据
			List<Map> tvoList = confirmOrderModifyService.selectOrderId(arrOrderNo[i]);
			if(tvoList.size()>0){
				flag = true;
				break;
			}
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(flag){
			map.put("FLAG", 1);
		}else{
			map.put("FLAG", 0);
		}
		return map;
	}
	
	/**
	 * 
	* @Title: doOrderCancelList 
	* @Description: 订单批量取消 
	* @param @param ttVsOrderDTO
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/doOrderCancelList", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> doOrderCancelList(
			@RequestBody @Valid TtVsOrderDTO ttVsOrderDTO ) {
		logger.info("=============订单批量取消 ==============");
		//订单取消原因
		String cancelReason = ttVsOrderDTO.getCancelReasonName();
		//订单号
		String[] arrOrderNos = ttVsOrderDTO.getOrderNos().split(",");
		Date date = new Date();
		for(int i=0;i<arrOrderNos.length;i++) {//批处理数据
			String orderNo = arrOrderNos[i];
			//修改订单表的订单状态字段
			//获取当前用户
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			TtVsOrderPO.update("UPDATE_BY = ? AND UPDATE_DATE = ? AND ORDER_STATUS = ?    AND ORDER_CANCEL_DATE = ? AND SO_CR_FAILURE_REASON = ? AND VIN = ? AND CANCEL_RESON = ?", "ORDER_NO = ?", loginInfo.getUserId(), date, OemDictCodeConstants.SALE_ORDER_TYPE_13, new Date(), "", "", cancelReason, orderNo) ;
			
			/* 新增接口表isdel start  */
			
			TiK4VsOrderPO.update("UPDATE_BY = ? AND UPDATE_DATE = ? AND IS_DEL = ?", "ORDER_NO = ?",  loginInfo.getUserId(), new Date(), 1, orderNo);
			/* 新增接口表isdel  end  */
			
			/* 新增订单历史记录信息  start    */
			List<Map> tvoList = orderTopMaintainService.selectTtVsOrderAll(orderNo);
			//新增订单历史记录
			List<Map> cancelReasonList = orderTopMaintainService.getFreezeReason(cancelReason);
			orderExecuteConfirmservice.insertHistory(Long.valueOf(tvoList.get(0).get("ORDER_ID").toString()),  OemDictCodeConstants.SALE_ORDER_TYPE_13, "订单取消", cancelReasonList.get(0).get("CANCEL_REASON_TEXT").toString(),loginInfo.getUserId(),loginInfo.getUserName());
			/* 新增订单历史记录信息  end */
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnValue", 1);
		
		return map;
	}
	
	
	
}
