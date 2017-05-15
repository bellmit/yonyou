package com.yonyou.dms.vehicle.controller.k4OrderExecuteManage;

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
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.domains.DTO.checkManagement.TtVsOrderDTO;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderExecuteConfirmService;
import com.yonyou.dms.vehicle.service.k4OrderExecuteManage.OrderTopMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: OrderTopMaintainController 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月9日 下午2:46:52 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/orderTopMaintain")
public class OrderTopMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	
	@Autowired
	private OrderTopMaintainService orderTopMaintainService ;
	
	@Autowired
	private OrderExecuteConfirmService orderExecuteConfirmservice ;
	
	/**
	 * 
	* @Title: yearPlanQueryInit 
	* @Description: 设置置顶维护（获取工作年） 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/queryYear",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryYear() {
		logger.info("============设置置顶维护（获取工作年）===============");
		List<Map> yearList = orderTopMaintainService.queryYear();
		return yearList;
	}
	
	/**
	 * 
	* @Title: orderTopQueryDetialInfoListQuery 
	* @Description: 订单置顶/解除置顶维护查询(oem)
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/orderTopQueryDetialInfoListQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderTopQueryDetialInfoListQuery(
			@RequestParam Map<String, String> queryParam) {
		logger.info("=============订单置顶/解除置顶维护查询(oem)==============");
		PageInfoDto pageInfoDto = orderTopMaintainService.findAll(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: orderIsTopList 
	* @Description: 订单置顶/解除置顶维护批量置顶 
	* @param @param ttVsOrderDTO
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/orderIsTopList/{type}", method = RequestMethod.PUT)
	@ResponseBody
	public List<Map> orderIsTopList(@PathVariable(value = "type") String type,
			@RequestBody @Valid TtVsOrderDTO ttVsOrderDTO ) {
		logger.info("=============订单置顶/解除置顶维护批量置顶 ==============");
		List<Map> msgList = new ArrayList<Map>();
		String[] orderNos = ttVsOrderDTO.getOrderNos().split(",") ; 
		Date date = new Date();
		if("1".equals(type)){//置顶
			for(int i=0;i<orderNos.length;i++) {//批处理数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderNos[i]);
				String msg = "";
				List<Map> tvoList = orderTopMaintainService.selectTtVsOrderAll(orderNos[i]);
				String log = null;
				if(!StringUtils.isNullOrEmpty(tvoList.get(0).get("IS_SEND"))){
					 log = tvoList.get(0).get("IS_SEND").toString();
				}else {
					 log = "";
				}
				if(log.equals(OemDictCodeConstants.IF_TYPE_YES.toString())){
					msg = "该订单已发送至SAP。";
				}else{
					String orderNo = orderNos[i];
					//修改订单表的置顶字段
					//获取当前用户
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TtVsOrderPO.update("IS_TOP = ? , UPDATE_BY = ? , UPDATE_DATE = ? , TOP_DATE = ? , TOP_BY = ? ", "ORDER_NO = ?", OemDictCodeConstants.IF_TYPE_YES, loginInfo.getUserId(), date, date, loginInfo.getUserId(), orderNo) ;
					
					//修改接口订单表的置顶字段
					TiK4VsOrderPO.update("IS_TOP = ? , UPDATE_BY = ? , UPDATE_DATE = ? , TOP_DATE = ? ", "ORDER_NO = ?", OemDictCodeConstants.IF_TYPE_YES, loginInfo.getUserId(), date, date,  orderNo) ;
					
					orderExecuteConfirmservice.insertHistory(Long.valueOf(tvoList.get(0).get("ORDER_ID").toString()),  Integer.valueOf(tvoList.get(0).get("ORDER_STATUS").toString()), "订单置顶", "", loginInfo.getUserId(), loginInfo.getUserName());
					msg = "订单置顶成功！";
				}
				map.put("msg", msg);
				map.put("freezeOrTop", 1);
				msgList.add(map);
			}
		}else{//解除置顶
			for(int i = 0 ; i<orderNos.length; i ++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orderNo", orderNos[i]);
				String msg = "";
				List<Map> tvoList = orderTopMaintainService.selectTtVsOrderAll(orderNos[i]);
				if((OemDictCodeConstants.IF_TYPE_NO.toString()).equals(tvoList.get(0).get("IS_TOP"))){
					msg = "该订单已解除置顶。";
				}else{
					String orderNo = orderNos[i];
					//修改订单表的置顶字段
					//获取当前用户
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TtVsOrderPO.update("IS_TOP = ? , UPDATE_BY = ? , UPDATE_DATE = ? , TOP_CANCEL_DATE = ? , TOP_BY = ? ", "ORDER_NO = ?", OemDictCodeConstants.IF_TYPE_NO, loginInfo.getUserId(), date, date, loginInfo.getUserId(), orderNo) ;
					
					//修改接口订单表的置顶字段
					//修改接口订单表的置顶字段
					TiK4VsOrderPO.update("IS_TOP = ? , UPDATE_BY = ? , UPDATE_DATE = ? ", "ORDER_NO = ?", OemDictCodeConstants.IF_TYPE_NO, loginInfo.getUserId(), date,  orderNo) ;
					
					orderExecuteConfirmservice.insertHistory(Long.valueOf(tvoList.get(0).get("ORDER_ID").toString()),  Integer.valueOf(tvoList.get(0).get("ORDER_STATUS").toString()), "订单解除置顶", "", loginInfo.getUserId(), loginInfo.getUserName());
					
					msg = "订单解除置顶成功！";
				}
				map.put("msg", msg);
				map.put("freezeOrTop", 1);
				msgList.add(map);
			}
		}
		
		return msgList;
	}
	
	
	
	
	
	
	
	
	
	

}
