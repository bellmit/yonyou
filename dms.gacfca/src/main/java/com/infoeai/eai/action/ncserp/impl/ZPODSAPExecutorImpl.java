package com.infoeai.eai.action.ncserp.impl;
/**
 * 
 * @author wujinbiao
 * @date: 2014-10-23
 * ZPOD
 * 验收信息上报
 */

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
import com.yonyou.dms.common.domains.PO.basedata.TiVehicleInspectionPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;


@Service
public class ZPODSAPExecutorImpl extends BaseService implements ZPODSAPExecutor {
	
	private static Logger logger = LoggerFactory.getLogger(ZPODSAPExecutorImpl.class);
	
	/**
	 * 返回sap的信息
	 */
	public List<SAPInboundVO> getInfo() throws Exception{
		logger.info("============ZPOD验收信息上报处理开始==================");
		List<SAPInboundVO> list_zpod = null;
		//List<SAPInboundVO> sapInboundList = null;
		
		try{
			beginDbService();
			list_zpod = getSI24Info();
			
			dbService.endTxn(true);
			
			if(list_zpod == null || list_zpod.size() == 0){
				return null;
			}
			List<SAPInboundVO> dataList = new ArrayList<SAPInboundVO>();
			for (int i = 0; i < list_zpod.size(); i++) {
				SAPInboundVO tempPO = new SAPInboundVO();
				tempPO = list_zpod.get(i);
				if (null == tempPO.getVin() || "".equals(tempPO.getVin())) {
					logger.info("=============" + tempPO.getSequenceId()
							+ "的VIN码为空！=================");
					continue;
				} else if (tempPO.getVin().length() > 17) {
					logger.info("=============" + tempPO.getSequenceId()
							+ "的VIN码长度与接口定义不一致！=================");
					continue;
				}
				dataList.add(tempPO);
			}
			logger.info("============ZPOD验收信息上报数据查询方法结束==================");
			return dataList;
		}catch (Throwable e) {
			dbService.endTxn(false);
			throw new Exception("============ZPOD查询处理异常=================="+e.getMessage());
		} finally {
			Base.detach();
			dbService.clean();
		}
/*		if (null != list_zpod && list_zpod.size() > 0) {
			try {
				logger.info("list_zpod size == " + list_zpod.size());
				POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
				sapInboundList = updateVoMethod(list_zpod);
				POContext.endTxn(true);
				logger.info("============ZPOD验收信息上报处理结束==================");
			} catch (Throwable e) {
				POContext.endTxn(false);
				throw new Exception("============ZPOD修改状态处理异常=================="+e.getMessage());
			} finally {
				POContext.cleanTxn();
			}
		}else{
			logger.info("list_zpod is null or size == 0：ZPOD查询结果为0");
		}
		return sapInboundList;*/
	}
	
	/**
	 * 发送成功修改状态
	 * @param list_zpod
	 */
	public List<SAPInboundVO> updateVoMethod(List<SAPInboundVO> list_zpod) throws Exception{
		try {
			//List<SAPInboundVO> dataList = new ArrayList<SAPInboundVO>();
			//****************************** 开启事物 *********************//*
		 beginDbService();
		 if (null != list_zpod && list_zpod.size() > 0) {
//			dbService();
			for (int i = 0; i < list_zpod.size(); i++) {
				SAPInboundVO tempPO = new SAPInboundVO();
				tempPO = list_zpod.get(i);
/*				if (null == tempPO.getVin() || "".equals(tempPO.getVin())) {
					logger.info("=============" + tempPO.getSequenceId()
							+ "的VIN码为空！=================");
					continue;
				} else if (tempPO.getVin().length() > 17) {
					logger.info("=============" + tempPO.getSequenceId()
							+ "的VIN码长度与接口定义不一致！=================");
					continue;
				}*/
				// 逐个消息检查通过修改扫描状态，同时重新封装list
				TiVehicleInspectionPO PO = TiVehicleInspectionPO.findById(tempPO.getSequenceId());
				PO.setString("IS_SCAN","1");
				PO.setLong("UPDATE_BY",new Long(80000002));
				PO.setTimestamp("UPDATE_DATE",Calendar.getInstance().getTime());
				PO.saveIt();
				//dataList.add(tempPO);
			}
			dbService.endTxn(true);
		 }
			logger.info("=============ZP0D发送成功修改状态方法结束============");
			return null;
		} catch (Throwable e) {
			dbService.endTxn(false);
			logger.info("=============ZP0D发送成功修改状态更新异常异常============");
			throw new Exception(e);
		}finally {
			Base.detach();
			dbService.clean();
		}
	}


	/**
	 * 获取ZPOD信息
	 * @return
	 */
	private List<SAPInboundVO> getSI24Info() {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT tvi.SEQUENCE_ID,tvi.VIN, \n");
		sql.append("        'ZPOD' ACTION_CODE, \n");
		sql.append("         COALESCE(DATE_FORMAT(tvi.ACTION_DATE, '%Y-%m-%d'),'0000-00-00') ACTION_DATE, \n");
		sql.append("         '00:00:00' ACTION_TIME , \n");//修改ZPOD上传action_time默认为"0000-00-00"  
		sql.append("         COALESCE(DATE_FORMAT(tvi.CREATE_DATE, '%Y-%m-%d'),'0000-00-00') POD_DATE , \n");
		sql.append("         '0000-00-00' BIRTHDAY, \n");
		sql.append("         '0000-00-00' REGISTRATION_DATE, \n");
		sql.append("         '0000-00-00' RETAIL_DATE, \n");
		sql.append("        (SELECT SWT_CODE FROM TM_COMPANY WHERE company_code = tvi.DEALER_CODE) RECEIVING_DEALER \n");
		sql.append("   FROM TI_VEHICLE_INSPECTION tvi  \n");
		sql.append("  WHERE (tvi.IS_SCAN = '0' OR tvi.IS_SCAN IS NULL)  AND tvi.VIN is not null limit 0,100 \n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null); 
		List<SAPInboundVO> list_zpod = new ArrayList<SAPInboundVO>();
		SAPInboundVO vo = null;
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Map map = list.get(i);
				vo = new SAPInboundVO();
				vo.setSequenceId((long) map.get("SEQUENCE_ID"));
				vo.setVin(map.get("VIN").toString());
				vo.setActionCode(map.get("ACTION_CODE").toString());
				vo.setActionDate(map.get("ACTION_DATE").toString());
				vo.setDealerCode(map.get("RECEIVING_DEALER").toString());
				vo.setActionTime(map.get("ACTION_TIME").toString());
				vo.setPodDate(map.get("POD_DATE").toString());
				vo.setBirthday(map.get("BIRTHDAY").toString());
				vo.setRegistDate(map.get("REGISTRATION_DATE").toString());
				vo.setRetailDate(map.get("RETAIL_DATE").toString());
				
				list_zpod.add(vo);
			}
		}
		return list_zpod;
	}
	
	// 功能说明:手动发送消息包
//	public static void main(String[] args) throws Throwable {
//		ContextUtil.loadConf();
//		ZPODSAPExecutor zdld = new ZPODSAPExecutor();
//		int size = zdld.getInfo().size();
//		System.out.println(size);
//	}
	


}
