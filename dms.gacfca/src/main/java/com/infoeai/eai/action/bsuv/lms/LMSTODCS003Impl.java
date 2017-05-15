package com.infoeai.eai.action.bsuv.lms; 

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.action.bsuv.common.LonPropertiesLoad;
import com.infoeai.eai.action.bsuv.dms.DCSTODMS003Service;
import com.infoeai.eai.po.TiEcRevokeOrderPO;
import com.infoeai.eai.wsClient.bsuv.lms.Customer;
import com.infoeai.eai.wsClient.bsuv.lms.GetTypeBTimeOutRequest;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortService;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortServiceLocator;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortSoap11Stub;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author wjs
 * @date 2016年4月29日 上午10:48:18
 * @description 功能描述  DCS官网订单逾期接收接口
 */
@Service
public class LMSTODCS003Impl extends BaseService implements LMSTODCS003{
	
	private static final Logger logger = LoggerFactory.getLogger(LMSTODCS003Impl.class);
	@Autowired
	private DeCommonDao deCommonDao;
	
	@Autowired
	DCSTODMS003Service service3;

	private LonPropertiesLoad lonLeader=LonPropertiesLoad.getInstance();
	/**
	 * @description DCS官网订单(Order Cancal)接收
	 * @param dealerCode 经销商代码
	 * @param ec_order_no 官网订单号
	 * @return  000   失败 001  成功  003   经销商验证失败
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public String receiveBSUVOrderCansel(String from,String to) throws Exception {
		Date startTime=new Date();
		int size=0;
		String excString="";
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		//
		//加载Web Service URL 地址
		//
		String urlString=lonLeader.getValue("BSUV_LMS_DCS_003");
		try {
			logger.info("================LMSTODCS003 DCS官网订单逾期接收接口========开始====url====="+urlString);
			//事务开启
			beginDbService();
			//调用彪扬WS接口
			OrderServicePortSoap11Stub stub=new OrderServicePortSoap11Stub();
			OrderServicePortService orderServicePortService=new OrderServicePortServiceLocator();
			stub=(OrderServicePortSoap11Stub) orderServicePortService.getOrderServicePortSoap11(new URL(urlString));
			GetTypeBTimeOutRequest getTypeBTimeOutRequest=new GetTypeBTimeOutRequest(from,to);
			Customer[] customers=stub.getTypeBTimeOut(getTypeBTimeOutRequest);
			
			//判断数据是不是为空，不为空赋值size，为空默认0
			if (customers!=null && customers.length!=0) {
				size=customers.length;
				for (Customer customer:customers) {
					//主键ID
//					Long id=SequenceManager.getSequence();
					TiEcRevokeOrderPO po=new TiEcRevokeOrderPO();
//					po.setId(id);
					if (!CommonUtils.isNullString(customer.getDEALER_CODE())) {
						List<Map> dmsDealer = deCommonDao.getDmsDealerCodes(customer.getDEALER_CODE());
						if (null!=dmsDealer && dmsDealer.size()>0) {
							po.setString("ENTITY_CODE",dmsDealer.get(0));
						}else {
							po.setString("ENTITY_CODE","");
						}
						po.setString("DEALER_CODE",customer.getDEALER_CODE());
					}
					po.setString("EC_ORDER_NO",customer.getEC_ORDER_NO());
					po.setInteger("VERIFICATION_TIMEOUT",1);//是否逾期[0：未逾期][1：已逾期]
					po.setInteger("REVOKE_TYPE",2);
					po.setInteger("SEND_DMS",0);
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
			dbService.endTxn(true);
			//DCS将撤单信息下发到DMS
			logger.info("================LMSTODCS003 DCS官网订单逾期接收接口========成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			logger.info("================LMSTODCS003 DCS官网订单逾期接收接口========异常=========" + e);
		} finally {
			logger.info("================LMSTODCS003 DCS官网订单逾期接收接口========结束=========");
			Base.detach();
			dbService.clean();
			
			service3.handleExecute();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "DCS官网订单逾期接收接口：LMS->DCS", startTime, size, ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================LMSTODCS003 DCS官网订单逾期接收接口日志插入失败=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			
		}
		return "";

	}
	/* (non-Javadoc)
	 * @see com.infoservice.schedule.Task#execute()
	 */
	public String execute() throws Exception {
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date toDate=calendar.getTime();
		String to=formater.format(toDate);
		calendar.add(Calendar.MINUTE, -35);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		Date fromDate=calendar.getTime();
		String from=formater.format(calendar.getTime());
		logger.info("=============================from Date:=+"+from+"+========to Date :="+to+"==");
		receiveBSUVOrderCansel(from,to);
		return "DCS官网订单逾期接收接口";
	}
}
 