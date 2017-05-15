package com.infoeai.eai.action.bsuv.lms; 

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.action.bsuv.common.LonPropertiesLoad;
import com.infoeai.eai.action.bsuv.dms.DCSTODMS002Service;
import com.infoeai.eai.action.bsuv.dms.DCSTODMS003Service;
import com.infoeai.eai.dao.bsuv.lms.TiEcPotentialCustomerDao;
import com.infoeai.eai.po.TiEcPotentialCustomerPO;
import com.infoeai.eai.po.TiEcRevokeOrderPO;
import com.infoeai.eai.wsClient.bsuv.lms.GetOrdersRequest;
import com.infoeai.eai.wsClient.bsuv.lms.Order;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortService;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortServiceLocator;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortSoap11Stub;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author wjs
 * @date 2016年4月29日 上午10:50:24
 * @description 功能描述   DCS官网潜客接收-修改接口
 */
@SuppressWarnings("rawtypes")
@Service
public class LMSTODCS002Impl extends BaseService implements LMSTODCS002{

	private static final Logger logger = LoggerFactory.getLogger(LMSTODCS002Impl.class);
//	private POFactory factory = POFactoryBuilder.getInstance();
	@Autowired
	private TiEcPotentialCustomerDao dao;
	@Autowired
	private DeCommonDao deCommonDao;
	
	@Autowired
	DCSTODMS002Service service2;
	
	@Autowired
	DCSTODMS003Service service3;
	
	private LonPropertiesLoad lonLeader=LonPropertiesLoad.getInstance();
	private SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * @description DCS官网潜客 (Potential Cust)接收
	 * @param ec_order_no官网订单号
	 * @param dealerCode经销商代码
	 * @param retailFinance 零售金融
	 * @param depositAmount 定金金额
	 * @param brandCode品牌代码
	 * @param seriesCode车系代码
	 * @param modelCode车型代码
	 * @param groupCode车款代码
	 * @param trimCode内饰代码
	 * @param colorCode颜色代码
	 * @param customerName客户名称
	 * @param customerTel客户手机
	 * @param determinedTime下定时间
	 * @return  000  失败  001 成功   002 物料信息验证失败  003  经销商验证失败
	 * @throws Exception 
	 */
	public void receiveBSUVPotentialCust(String from,String to) throws Exception {
		Date startTime=new Date();
		int size=0;
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		String excString="";
		boolean flag = false;//撤单接口标记
		//
		//加载Web Service URL 地址
		//
		String urlString=lonLeader.getValue("BSUV_LMS_DCS_002");
		try {
			logger.info("================LMSTODCS002 DCS官网潜客接收-修改========开始========url====="+urlString);
			//事务开启
			beginDbService();
			/**
			 * 调用彪扬WS接口，获取潜客信息
			 */
			OrderServicePortSoap11Stub stub=new OrderServicePortSoap11Stub();
			OrderServicePortService orderServicePortService=new OrderServicePortServiceLocator();
			stub=(OrderServicePortSoap11Stub) orderServicePortService.getOrderServicePortSoap11(new URL(urlString));
			GetOrdersRequest orderRequest=new GetOrdersRequest(from,to);
			Order[] order=stub.getOrders(orderRequest);
			//判断数据是不是为空，不为空赋值size，为空默认0
			if (order!=null && order.length!=0) {
				size=order.length;
				// 主键ID
				for (Order ecVO:order) {
					//如果是撤单，插入撤单表
					int ecOrderType=ecVO.getEC_ORDER_TYPE();
					if(ecOrderType==OemDictCodeConstants.BSUV_EC_OEDER_STATUS_04){//
						insertCancelEcOrder(ecVO);
						flag = true;
					}else {//如果不是撤单，插入潜客表
						TiEcPotentialCustomerPO po = new TiEcPotentialCustomerPO();
//						Long id = SequenceManager.getSequence();
						po.setInteger("EC_ORDER_TYPE",ecVO.getEC_ORDER_TYPE());//逾期，有效，期货，撤单
						if (!CommonUtils.isNullString(ecVO.getDEALER_CODE())) {
							List<Map> dmsDealer = deCommonDao.getDmsDealerCodes(ecVO.getDEALER_CODE());
							if (null !=dmsDealer && dmsDealer.size()>0) {
								po.setString("ENTITY_CODE",dmsDealer.get(0));
							}else{
								po.setString("ENTITY_CODE","");
							}
							po.setString("DEALER_CODE",ecVO.getDEALER_CODE());
						}
//						po.setId(id);
						// 校验物料是否是在DCS端存在
						String checkResult = doCheckData(ecVO);
						if ("".equals(checkResult)) {
							po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);
						} else {
							po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_NO);
							po.setString("MESSAGE",checkResult);
						}
						po.setString("BRAND_CODE",ecVO.getBRAND_CODE());// 品牌
						po.setString("SERIES_CODE",ecVO.getSERIES_CODE());// 车系
						po.setString("MODEL_CODE",ecVO.getGROUP_CODE());// 车型
						po.setString("GROUP_CODE",ecVO.getMODEL_CODE());// 车款
						po.setString("MODEL_YEAR",ecVO.getMODEL_YEAR());// 年款
						po.setString("COLOR_CODE",ecVO.getCOLOR_CODE());// 颜色
						po.setString("TRIM_CODE",ecVO.getTRIM_CODE());// 内饰
						String custName=ecVO.getCUSTOMER_NAME();
						logger.info("=================="+custName+"======"+CommonUtils.checkNull(custName)+"=============");
						if (!"".equals(CommonUtils.checkNull(custName))) {
							po.setString("CUSTOMER_NAME",new String(Base64.decode(CommonUtils.checkNull(custName))));
						}else {
							po.setString("CUSTOMER_NAME","");
						}
						po.setString("DEALER_CODE",ecVO.getDEALER_CODE());
						// po.setDealerName(ecVO.get);
						if (!"".equals(CommonUtils.checkNull(ecVO.getDEPOSIT_AMOUNT()))) {
							po.setFloat("DEPOSIT_AMOUNT",Float.parseFloat(ecVO.getDEPOSIT_AMOUNT().toString()));
						}
						if (Utility.testIsNotNull(ecVO.getDETERMINED_TIME())) {// 下定时间
							Date determinedTime =formater.parse(ecVO.getDETERMINED_TIME());
							po.setTimestamp("DEPOSIT_DATE",determinedTime);
						}
						po.setString("EC_ORDER_NO",ecVO.getEC_ORDER_NO());
						// po.setEntityCode(ecVO);
						po.setString("ID_CRAD",ecVO.getID_CRAD());// 身份证号
						po.setString("TEL",ecVO.getCUSTOMER_TEL());
						po.setInteger("OPERATION_FLAG",ecVO.getOPERATION_FLAG());// 0新增， 1修改
						if (!CommonUtils.isNullString(ecVO.getRETAIL_FINANCE())) {// 零售金融
							po.setString("RETAIL_FINANCIAL",ecVO.getRETAIL_FINANCE());
						}
						po.setInteger("SEND_DMS",0);// 未发送
						po.setLong("CREATE_BY",222222L);
						po.setTimestamp("CREATE_DATE",new Date());
						try {
							po.saveIt();
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				}
			}
				dbService.endTxn(true);
				//DCS-DMS 将潜客信息（有新增的和修改的信息）
//				DCSTODMS002 dcsToDms002=new DCSTODMS002();
//				dcsToDms002.execute();
			logger.info("================LMSTODCS002 DCS官网潜客接收-修改========成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			logger.info("================LMSTODCS002 DCS官网潜客接收-修改========异常=========" + e);
		} finally {
			logger.info("================LMSTODCS002 DCS官网潜客接收-修改========结束=========");
			Base.detach();
			dbService.clean();
			//DCS-DMS 将潜客信息（有新增的和修改的信息）
			logger.info("================LMSTODCS002 官网订单潜客信息（推送DMS接口）========开始=========");
			service2.handleExecute();
			logger.info("================LMSTODCS002 官网订单潜客信息（推送DMS接口）========结束=========");
			logger.info("================LMSTODCS002 撤单标记：==="+flag+"==============");
			if(flag){
				//DCS将撤单信息下发到DMS
				logger.info("================LMSTODCS002 DCS将撤单信息下发到DMS========开始=========");
				service3.handleExecute();
				logger.info("================LMSTODCS002 DCS将撤单信息下发到DMS========结束=========");
			}
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "DCS官网潜客接收-修改：LMS->DCS", startTime, size, ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================LMSTODCS002 DCS官网潜客接收-修改日志插入失败=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
		}

	}
	
	public String doCheckData(Order vo) {
		String result=dao.checkMertires(vo.getBRAND_CODE(),vo.getSERIES_CODE(),vo.getGROUP_CODE(),vo.getMODEL_CODE(),vo.getCOLOR_CODE(),vo.getTRIM_CODE(),vo.getMODEL_YEAR());
		return result;
	}

	/*
	 * 插入撤单信息
	 */
	private void insertCancelEcOrder(Order vo) throws Exception{
		logger.info("================LMSTODCS002 插入撤单信息========开始=========");
		//开启事务
//		POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
//		String exString="";
//		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
//		Date startTime=new Date();
//		try {
			TiEcRevokeOrderPO poOrder=new TiEcRevokeOrderPO();
//			long id=SequenceManager.getSequence();
//			poOrder.setId(id);
			if (!CommonUtils.isNullString(vo.getDEALER_CODE())) {
				Map dmsDealer = deCommonDao.getDmsDealerCode(vo.getDEALER_CODE());
				poOrder.setString("DEALER_CODE",vo.getDEALER_CODE());
				poOrder.setString("ENTITY_CODE",dmsDealer.get("DMS_CODE").toString());
			}
			poOrder.setString("EC_ORDER_NO",vo.getEC_ORDER_NO());
			poOrder.setInteger("VERIFICATION_TIMEOUT",1);//是否逾期[0：未逾期][1：已逾期]
			if (Utility.testIsNotNull(vo.getORDER_CANCEL_TIME())) {
				Date cancelTime=formater.parse(vo.getORDER_CANCEL_TIME());
				poOrder.setTimestamp("REVOKE_DATE",cancelTime);//撤单日期
			}
			poOrder.setInteger("SEND_DMS",0);
			poOrder.setInteger("REVOKE_TYPE",1);//1,撤单,2逾期
			poOrder.setLong("CREATE_BY",222222L);
			poOrder.setTimestamp("CREATE_DATE",new Date());
			poOrder.saveIt();
//			factory.insert(poOrder);
//			POContext.endTxn(true);
			//DCS将撤单信息下发到DMS
//			DCSTODMS003 dcsToDms003=new DCSTODMS003();
//			dcsToDms003.execute();
//		} catch (Exception e) {
//			POContext.endTxn(false);
//			ifType=OemDictCodeConstants.IF_TYPE_NO;
//			exString=CommonBSUV.getErrorInfoFromException(e);
//		} finally {
//			POContext.cleanTxn();
//			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "DCS官网潜客接收-修改：LMS->DCS", startTime, 1,ifType, exString, "", "", new Date());
//		}
		logger.info("================LMSTODCS002 插入撤单信息========结束=========");
	}
	/* (non-Javadoc)
	 * @see com.infoservice.schedule.Task#execute()
	 */
	public String execute() throws Exception {
		Calendar calendar=Calendar.getInstance();
		Date toDate=calendar.getTime();
		String to=formater.format(toDate);
		calendar.add(Calendar.MINUTE, -35);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		Date fromDate=calendar.getTime();
		String from=formater.format(calendar.getTime());
		logger.info("=============================from Date:=+"+from+"+========to Date :="+to+"==");
		receiveBSUVPotentialCust(from, to);
		return "DCS官网潜客接收-修改";
	}

}
 