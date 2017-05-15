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
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsOrderDTO;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.ConfirmOrderModifyService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderExecuteConfirmService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: ConfirmOrderModifyController 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月8日 下午5:14:54 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/confirmOrderModify")
public class ConfirmOrderModifyController {

	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	@Autowired
	private ConfirmOrderModifyService confirmOrderModifyService;
	
	@Autowired
	private OrderExecuteConfirmService orderExecuteConfirmservice ;
	
	/**
	 * 
	* @Title: queryWeek 
	* @Description:  确认订单修改(获取周列表)
	* @param @param orderYearId
	* @param @param orderMonthId
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryWeek/{orderYearId}/{orderMonthId}",
			method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryWeek(@PathVariable("orderYearId") String orderYearId,
			@PathVariable("orderMonthId") String orderMonthId,
			@RequestParam Map<String,String> queryParam) {
		logger.info("============确认订单修改(获取周列表)===============");
		queryParam.put("orderYearId", orderYearId);
		queryParam.put("orderMonthId", orderMonthId);
		List<Map> taskCodeList = confirmOrderModifyService.queryWeek(queryParam);
		return taskCodeList;
	}
	
	
	/**
	 * 
	* @Title: confirmOrderInfo 
	* @Description: 确认订单修改（查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/confirmOrderInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto confirmOrderInfo(@RequestParam Map<String, String> queryParam) {
		logger.info("============确认订单修改（查询）===============");
		PageInfoDto pageInfoDto = confirmOrderModifyService.getConfirmOrderInfoQueryList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: confirmOrderModify 
	* @Description:  确认订单修改(修改)
	* @param @param ttVsOrderDTO
	* @param @return    设定文件 
	* @return ResponseEntity<TtVsOrderDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/confirmOrderModifyMthod", method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> confirmOrderModifyMthod(
			@RequestBody @Valid TtVsOrderDTO ttVsOrderDTO ) {
		logger.info("=============确认订单修改(修改) ==============");
		//订单号
		String[] arrOrderNos = ttVsOrderDTO.getOrderNos().split(",");
		//支付方式
		String paymentType = ttVsOrderDTO.getPaymentTypeName();
		//是否返利
		Integer isRebate = ttVsOrderDTO.getIsRebate();
		Long orderId = 0l;
		
		String orders="";
		//循环订单号
		for(int i=0; i < arrOrderNos.length; i++){
			List<Map> tvoList = confirmOrderModifyService.selectOrderId(arrOrderNos[i]);
			
			if(tvoList.size()!=0){
				List<Map> list = confirmOrderModifyService.selectTiK4VsOrder(arrOrderNos[i]);
				if(list.size() > 0){
					//更新订单表付款方式、是否返利字段
					TiK4VsOrderPO.update("PAYMENT_TYPE=? , IS_REBATE =? ", " ORDER_NO=? ", Integer.parseInt(CommonUtils.checkNull(paymentType)),Integer.parseInt(CommonUtils.checkNull(isRebate)), arrOrderNos[i]);
				}
				orderId = Long.valueOf(tvoList.get(0).get("ORDER_ID").toString());
				//更新接口表付款方式、是否返利字段
				//获取当前用户
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TtVsOrderPO.update("PAYMENT_TYPE=? , IS_REBATE =? , UPDATE_BY = ? , UPDATE_DATE = ? ", " ORDER_NO=? AND IS_SEND=? ", Integer.parseInt(CommonUtils.checkNull(paymentType)), Integer.parseInt(CommonUtils.checkNull(isRebate)), loginInfo.getUserId(), new Date(), arrOrderNos[i], OemDictCodeConstants.IF_TYPE_NO );
				orderExecuteConfirmservice.insertHistory(orderId,  OemDictCodeConstants.SALE_ORDER_TYPE_06, "确认订单修改", "", loginInfo.getUserId(),loginInfo.getUserName());
			}else{
				String orderNo=arrOrderNos[i];
				orders=orders+","+orderNo;
			}
		}
		String returnValue="";
		if(orders!=""){
			orders=orders.substring(1, orders.length());
			returnValue="订单"+orders+"已配车，请等待配车结果";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FLAG", returnValue);
		
		return map;
	}
	
	
	
}
