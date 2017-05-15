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
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.action.bsuv.common.LonPropertiesLoad;
import com.infoeai.eai.action.bsuv.dms.DCSTODMS001Sercvice;
import com.infoeai.eai.po.TiEcHitSinglePO;
import com.infoeai.eai.wsClient.bsuv.lms.GetSaleLeadsRequest;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortService;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortServiceLocator;
import com.infoeai.eai.wsClient.bsuv.lms.OrderServicePortSoap11Stub;
import com.infoeai.eai.wsClient.bsuv.lms.SaleLead;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author wjs
 * @date 2016年4月29日 上午10:36:34
 * @description 功能描述  DCS线索校验接收接口
 */ 
@Service
@SuppressWarnings("rawtypes")
public class LMSTODCS001Impl extends BaseService implements LMSTODCS001{
	private static final Logger logger = LoggerFactory.getLogger(LMSTODCS001Impl.class);
	@Autowired
	private DeCommonDao deCommonDao ;
	private LonPropertiesLoad lonLeader=LonPropertiesLoad.getInstance();
	
	@Autowired
	DCSTODMS001Sercvice service;
	/**
	 * @description  DCS线索校验(Cue Check)接收操作
	 * 参数说明
	 * @param dealerCode 经销商代码
	 * @param dealerName 经销商名称
	 * @param tel  手机 、电话
	 * @return  000   失败   001  成功   003   经销商验证失败
	 * @throws Exception 
	 */
	
	public String receiveBSUVCueCheck(String from,String to) throws Exception {
		String excString="";
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		Date startTime=new Date();
		int size=0;
		//
		//加载Web Service URL 地址
		//
		String urlString=lonLeader.getValue("BSUV_LMS_DCS_001");
		try {
			logger.info("================LMSTODCS001 DCS线索校验接收========开始=========url====="+urlString);
			//事务开启
			beginDbService();
			/**
			 * 调用彪扬WS接口，获取潜客信息
			 */
			OrderServicePortSoap11Stub stub=new OrderServicePortSoap11Stub();
			OrderServicePortService orderServicePortService=new OrderServicePortServiceLocator();
			stub=(OrderServicePortSoap11Stub) orderServicePortService.getOrderServicePortSoap11(new URL(urlString));
			//传参类
			GetSaleLeadsRequest salesLeads=new GetSaleLeadsRequest(from,to);
			SaleLead[] sales=stub.getSaleLeads(salesLeads);
			//判断数据是不是为空，不为空赋值size，为空默认0
			if (sales!=null && sales.length!=0) {
				size=sales.length;
				for (SaleLead sale:sales) {
					//主键ID
//					Long id=SequenceManager.getSequence();
					//实例插入PO
					TiEcHitSinglePO po=new TiEcHitSinglePO();
//					po.setId(id);
					if (!CommonUtils.isNullString(sale.getDEALER_CODE())) {
						// 根据上端 dealerCode 查询下端 entityCode
						List<Map> dmsDealer = deCommonDao.getDmsDealerCodes(sale.getDEALER_CODE());
						if (null !=dmsDealer && dmsDealer.size()>0) {
							po.setString("ENTITY_CODE",dmsDealer.get(0));
						}else {
							po.setString("ENTITY_CODE","");
						}
						po.setString("DEALER_CODE",sale.getDEALER_CODE());
					}
					String dealerName=sale.getDEALER_NAME();
					if (!"".equals(CommonUtils.checkNull(dealerName))) {
						po.setString("DEALER_NAME",new String(Base64.decode(new String(sale.getDEALER_NAME().getBytes("UTF-8"),"UTF-8"))));
					}else {
						po.setString("DEALER_NAME","");
					}
					
					po.setString("TEL",sale.getTEL());
					po.setLong("CREATE_BY",222222L);
					po.setTimestamp("CREATE_DATE",new Date());
					po.setInteger("SEND_DMS",0);
					try {
						po.saveIt();
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					} 
				}
			}
			dbService.endTxn(true);
			logger.info("================LMSTODCS001 DCS线索校验接收========成功=========");
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			logger.info("================LMSTODCS001 DCS线索校验接收========异常=========" + e);
		} finally {
			Base.detach();
			dbService.clean();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "DCS线索校验接收：LMS->DCS", startTime, size, ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================LMSTODCS001 DCS线索校验接收日志插入失败=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			//下发dms操作
			service.handleExecute();
			logger.info("================LMSTODCS001 DCS线索校验接收========结束=========");
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
		String from=formater.format(calendar.getTime());
		logger.info("=============================from Date:=+"+from+"+========to Date :="+to+"==");
		receiveBSUVCueCheck(from,to);
		return "LMSTODCS001线索校验接收";
	}
	
	public static void main(String[] args) {
//		ContextUtil.loadConf();
//		POFactory factory=POFactoryBuilder.getInstance();
//		BSUVLMSTODCS001 bsuvlmstodcs001=new BSUVLMSTODCS001();
//		bsuvlmstodcs001.execute(factory);
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();  
        context.setValidating(false);  
        context.load("classpath*:applicationContext*.xml");  
        context.refresh();  
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat formater=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date toDate=calendar.getTime();
		String to=formater.format(toDate);
		calendar.add(Calendar.MINUTE, -60);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String from=formater.format(calendar.getTime());
		logger.info("=============================from Date:=+"+from+"+========to Date :="+to+"==");
	}
}
