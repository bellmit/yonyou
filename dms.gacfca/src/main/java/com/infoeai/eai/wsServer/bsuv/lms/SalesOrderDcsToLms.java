package com.infoeai.eai.wsServer.bsuv.lms;




import org.springframework.stereotype.Controller;

import com.infoeai.eai.action.bsuv.lms.DCSTOLMS009;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS010;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS011;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS012;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS013;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS014;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

/**
 * (DCS-LMS)零售/官网批售订单信息推送接口7
 * @author wangJian
 * date 2016-04-29
 *
 */
@Controller
public class SalesOrderDcsToLms {
	ResaleOrderDcsToLmsVO[] resaleOrderVo = null;//零售订单VO
	CloseOutOrderDcsToLmsVO[] closeOutVo = null;//官网批售订单VO
	/**
	 * 零售订单提交推送
	 * @return resaleOrder
	 * @throws Exception 
	 */
	public ResaleOrderDcsToLmsVO[] reSaleOrderInfo(String FROM,String TO) throws Exception{
		DCSTOLMS009 dcs009 = (DCSTOLMS009)ApplicationContextHelper.getBeanByType(DCSTOLMS009.class);
		resaleOrderVo = dcs009.reSaleOrderInfo(FROM,TO);
		return resaleOrderVo;
	}
	
	/**
	 * 官网批售订单配车成功状态推送接口
	 * @return closeOutVo
	 * @throws Exception 
	 */
	public CloseOutOrderDcsToLmsVO[] orderPeiCheSuccessState(String FROM,String TO) throws Exception{
		DCSTOLMS010 dcs010 = (DCSTOLMS010)ApplicationContextHelper.getBeanByType(DCSTOLMS010.class);
		closeOutVo = dcs010.orderPeiCheSuccessState(FROM,TO);
		return  closeOutVo;
	}
	
	/**
	 * 官网批售订单发车出库状态推送接口
	 * @return closeOutVo
	 * @throws Exception 
	 */
	public CloseOutOrderDcsToLmsVO[] orderDepartChuKuState(String FROM,String TO) throws Exception{
		DCSTOLMS011 dcs011 = (DCSTOLMS011)ApplicationContextHelper.getBeanByType(DCSTOLMS011.class);
		closeOutVo = dcs011.orderDepartChuKuState(FROM,TO);
		return  closeOutVo;
	}
	
	/**
	 * 官网批售订单验收入库状态推送接口
	 * @return closeOutVo
	 * @throws Exception 
	 */
	public CloseOutOrderDcsToLmsVO[] orderCheckRuKuState(String FROM,String TO) throws Exception{
		DCSTOLMS012 dcs012 = (DCSTOLMS012)ApplicationContextHelper.getBeanByType(DCSTOLMS012.class);
		closeOutVo = dcs012.orderCheckRuKuState(FROM,TO);
		return  closeOutVo;
	}
	
	/**
	 * 官网订单实销信息推送接口
	 * @return resaleOrderVo
	 * @throws Exception 
	 */
	public ResaleOrderDcsToLmsVO[] orderSalesInfo(String FROM,String TO) throws Exception{
		DCSTOLMS013 dcs013 = (DCSTOLMS013)ApplicationContextHelper.getBeanByType(DCSTOLMS013.class);
		resaleOrderVo = dcs013.orderSalesInfo(FROM,TO);
		return  resaleOrderVo;
	}
	
	/**
	 * 官网批售订单经销商确认订单推送接口
	 * @param FROM
	 * @param TO
	 * @return
	 * @throws Exception 
	 */
	public CloseOutOrderDcsToLmsVO[] dealerSure(String FROM,String TO) throws Exception{
		DCSTOLMS014 dcs014 = (DCSTOLMS014)ApplicationContextHelper.getBeanByType(DCSTOLMS014.class);
		closeOutVo = dcs014.dealerSure(FROM, TO);
		return  closeOutVo;
	}
}
