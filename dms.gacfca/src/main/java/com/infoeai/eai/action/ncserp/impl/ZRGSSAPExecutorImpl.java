package com.infoeai.eai.action.ncserp.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.vo.SAPInboundVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiVehicleRetailPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;



/**
 * @author wujinbiao
 * @date: 2014-10-23
 * ZECU
 * 零售信息上报
 */
@SuppressWarnings("rawtypes")
@Service
public class ZRGSSAPExecutorImpl extends BaseService implements ZRGSSAPExecutor{
	
	private static Logger logger = LoggerFactory.getLogger(ZRGSSAPExecutorImpl.class);
	
	
	Calendar sysDate = Calendar.getInstance();
	
	/**
	 * 返回sap的信息
	 */
	public List<SAPInboundVO> getInfo() throws Exception {
		
		logger.info("============ZRGS零售信息上报处理开始==================");
		
		//List<SAPInboundVO> sapInboundList = null;
		List<SAPInboundVO> list_zrgs = null;
		
		try {
			
//			POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
			beginDbService();
			list_zrgs = getZRGSSAPVOInfo(); // ZRGS
			//sapInboundList = new ArrayList<SAPInboundVO>();
			dbService.endTxn(true);
			logger.info("============ZRGS零售信息上报数据查询方法结束==================");
			if(list_zrgs == null || list_zrgs.size() == 0){
				return null;
			}
			List<SAPInboundVO> dataList = new ArrayList<SAPInboundVO>();
           for (int i = 0; i < list_zrgs.size(); i++) {
				SAPInboundVO tempPO = new SAPInboundVO();
				tempPO = list_zrgs.get(i);
				if (CheckUtil.checkNull(tempPO.getVin())) {
					logger.info("=============" + tempPO.getSequenceId() + "的VIN码为空！=================");
					continue;
				} else if (tempPO.getVin().length() > 17) {
					logger.info("=============" + tempPO.getSequenceId() + "的VIN码长度与接口定义不一致！=================");
					continue;
				}
				dataList.add(tempPO);
           }	
			return dataList;
		} catch (Throwable e) {
			dbService.endTxn(false);
			throw new Exception("============ZRGS查询数据异常==================" + e.getMessage(), e.getCause());
		} finally {
			Base.detach();
			dbService.clean();
		}

		// 发送成功修改状态
/*		if (null != list_zrgs && list_zrgs.size() > 0) {
			
			try {
				*//****************************** 开启事物 *********************//*
				POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
				sapInboundList = updateVoMethod(list_zrgs);
				POContext.endTxn(true);
				logger.info("============ZRGS零售信息上报处理结束==================");
				return sapInboundList;
			} catch (Exception e) {
				POContext.endTxn(false);
				throw new Exception("============ZRGS修改状态处理异常==================" + e.getMessage());
			} finally {
				POContext.cleanTxn();
			}
		}
		return null;*/
	}
	
	/**
	 * 发送成功修改状态
	 * @param list_zpod
	 */
	public List<SAPInboundVO> updateVoMethod(List<SAPInboundVO> list_zrgs) throws Exception {
		
		try {
			
			//List<SAPInboundVO> dataList = new ArrayList<SAPInboundVO>();
			//****************************** 开启事物 *********************//*
		  beginDbService();
		  if (null != list_zrgs && list_zrgs.size() > 0) {
//			POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
			
			for (int i = 0; i < list_zrgs.size(); i++) {
				
				SAPInboundVO tempPO = new SAPInboundVO();
				tempPO = list_zrgs.get(i);
				/*if (CheckUtil.checkNull(tempPO.getVin())) {
					logger.info("=============" + tempPO.getSequenceId() + "的VIN码为空！=================");
					continue;
				} else if (tempPO.getVin().length() > 17) {
					logger.info("=============" + tempPO.getSequenceId() + "的VIN码长度与接口定义不一致！=================");
					continue;
				}*/
				
				// 逐个消息检查通过修改扫描状态，同时重新封装list
				TiVehicleRetailPO PO = TiVehicleRetailPO.findById(tempPO.getSequenceId());
				PO.setString("IS_SCAN","1");
				PO.setLong("UPDATE_BY",(new Long(80000002)));
				PO.setTimestamp("UPDATE_DATE",sysDate.getTime());
//				factory.update(PO1, PO2);
				//dataList.add(tempPO);
				PO.saveIt();
			}
			dbService.endTxn(true);
		 }
			logger.info("============ZRGS零售信息上报状态更新方法结束==================");
			return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("============ZRGS修改状态处理异常==================" + e.getMessage());
		} finally {
			Base.detach();
			dbService.clean();
		}
	}

	/**
	 * 获取ZRGS信息
	 */
	public List<SAPInboundVO> getZRGSSAPVOInfo() {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TVR.SEQUENCE_ID, \n");
		sql.append("       'ZRGS'AS ACTION_CODE, \n");
		sql.append("       TVR.VIN, \n");
		sql.append("       TMC.SWT_CODE DEALER_CODE, \n");
		sql.append("       TVR.VEHICLE_USAGE, \n");
		sql.append("       COALESCE(DATE_FORMAT(TVR.RETAIL_DATE, '%Y-%m-%d'), '0000-00-00') AS RETAIL_DATE, \n");
		sql.append("       TVR.REGISTRATION_NUMBER, \n");
		sql.append("       COALESCE(DATE_FORMAT(TVR.REGISTRATION_DATE, '%Y-%m-%d'), '0000-00-00') AS REGISTRATION_DATE, \n");
		sql.append("       'Organisation' AS PERO, \n");
		sql.append("       '0003' AS TITILE, \n");
		sql.append("       'Chrysler Group (China) Sales Limited' AS COMPANY_NAME, \n");
		sql.append("       '' AS SURNAME, \n");
		sql.append("       '' AS FIRST_NAME, \n");
		sql.append("       '' AS TEL_NUMBER1, \n");
		sql.append("       '' AS TEL_NUMBER2, \n");
		sql.append("       '' AS MOBILE_NO, \n");
		sql.append("       '' AS FAX_NUMBER, \n");
		sql.append("       '' AS EMAIL_ADDRESS, \n");
		sql.append("       'Dawning Center 500 Hongbaoshi Road, Shanghai' AS ADDRESS, \n");
		sql.append("       'CN' AS COUNTRY, \n");
		sql.append("       'SH' AS REGION, \n");
		sql.append("       'Shanghai' AS CITY, \n");
		sql.append("       '201103' AS POST_CODE, \n");
		sql.append("       COALESCE(DATE_FORMAT(TVR.CREATE_DATE, '%Y-%m-%d'), '0000-00-00') ACTION_DATE, \n");
		sql.append("       COALESCE(DATE_FORMAT(TVR.CREATE_DATE, '%Y-%m-%d'), '00:00:00') ACTION_TIME, \n");
		sql.append("       '0000-00-00' AS BIRTHDAY, \n");
		sql.append("       '0000-00-00' AS POD_DATE \n");
		sql.append("  FROM TI_VEHICLE_RETAIL TVR, \n");
		sql.append("       TM_DEALER TMD, \n");
		sql.append("       TM_COMPANY TMC \n");
		sql.append(" WHERE (TVR.IS_SCAN = '0' OR TVR.IS_SCAN IS NULL) \n");
		sql.append("   AND TVR.DEALER_CODE = TMD.DEALER_CODE \n");
		sql.append("   AND TMD.COMPANY_ID = TMC.COMPANY_ID \n");
		sql.append("   AND TVR.VIN IS NOT NULL \n");
		sql.append(" LIMIT 0,100 \n");
		List<SAPInboundVO> beanList = new ArrayList<>();
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		for (Map map : list) {
			SAPInboundVO bean = new SAPInboundVO();
			bean.setSequenceId(Long.parseLong(CommonUtils.checkNull(map.get("SEQUENCE_ID"),"0")));
			bean.setActionCode(CommonUtils.checkNull(map.get("ACTION_CODE")));
			bean.setVin(CommonUtils.checkNull(map.get("VIN")));
			bean.setDealerCode(CommonUtils.checkNull(map.get("DEALER_CODE")));
			bean.setVhusg(CommonUtils.checkNull(map.get("VEHICLE_USAGE")));
			bean.setRetailDate(CommonUtils.checkNull(map.get("RETAIL_DATE")));
			bean.setRegistNumb(CommonUtils.checkNull(map.get("REGISTRATION_NUMBER")));
			bean.setRegistDate(CommonUtils.checkNull(map.get("REGISTRATION_DATE")));
			bean.setPersonOrg(CommonUtils.checkNull(map.get("PERO")));
			bean.setTitle(CommonUtils.checkNull(map.get("TITILE")));
			bean.setCompany(CommonUtils.checkNull(map.get("COMPANY_NAME")));
			bean.setSurname(CommonUtils.checkNull(map.get("SURNAME")));
			bean.setName1(CommonUtils.checkNull(map.get("FIRST_NAME")));
			bean.setTel1(CommonUtils.checkNull(map.get("TEL_NUMBER1")));
			bean.setTel2(CommonUtils.checkNull(map.get("TEL_NUMBER2")));
			bean.setMobile1(CommonUtils.checkNull(map.get("MOBILE_NO")));
			bean.setFax(CommonUtils.checkNull(map.get("FAX_NUMBER")));
			bean.setEmail(CommonUtils.checkNull(map.get("EMAIL_ADDRESS")));
			bean.setAddress(CommonUtils.checkNull(map.get("ADDRESS")));
			bean.setCountry(CommonUtils.checkNull(map.get("COUNTRY")));
			bean.setRegion(CommonUtils.checkNull(map.get("REGION")));
			bean.setCity(CommonUtils.checkNull(map.get("CITY")));
			bean.setPostcode(CommonUtils.checkNull(map.get("POST_CODE")));
			bean.setBirthday(CommonUtils.checkNull(map.get("BIRTHDAY")));
			bean.setActionDate(CommonUtils.checkNull(map.get("ACTION_DATE")));
			bean.setActionTime(CommonUtils.checkNull(map.get("ACTION_TIME")));
			bean.setPodDate(CommonUtils.checkNull(map.get("POD_DATE")));
			beanList.add(bean);
		}
		return beanList;
	}
	

/*	public static void main(String[] args) throws Throwable {
		ContextUtil.loadConf();
		ZRGSSAPExecutor zdld = new ZRGSSAPExecutor();
		int size = zdld.getInfo().size();
		System.out.println(size);
	}*/
//	public static void main(String[] args) throws Throwable {
//		ContextUtil.loadConf();
//		POFactory factory = POFactoryBuilder.getInstance();
//		ContextUtil.loadConf();
//		POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
//	    for (int i=0;i < 150; i++){
//			TiVehicleRetailPO PO2 = new TiVehicleRetailPO();
//			Long nodeId = Long.parseLong(SequenceManager.getSequence(null));
//			PO2.setSequenceId(nodeId);
//			PO2.setVin("2C3CCAPG3DH598323");
//			PO2.setDealerCode("33102");
//			PO2.setVehicleUsage("68");
//			PO2.setUpdateBy(new Long(80000002));
//            PO2.setRetailDate(new Date());
//            PO2.setIsScan("0");
//            PO2.setCreateDate(new Date());
//			factory.insert(PO2);
//			System.out.println(i);
//	    }
//	    POContext.endTxn(true);
//	    POContext.cleanTxn();
//	}
}
