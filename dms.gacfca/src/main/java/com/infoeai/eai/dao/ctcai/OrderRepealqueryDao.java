package com.infoeai.eai.dao.ctcai;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmCactAllotPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrderPayChangePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmorderCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TmpCancelOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsAppointOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class OrderRepealqueryDao extends OemBaseDAO {

	/**
	 * 供接口调用
	 * 预撤单表中如果有经销商信息交订单直接分配给经销商
	 * 休眠15秒，防止撤单还未结束而撤单结果已返回情况
	 * @param list
	 */
	public void undoOrder(List<Map<String, String>> list) {
		try {
			Thread.sleep(15*1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		for(int i=0;i<list.size();i++){
			Map<String,String> map = list.get(i);
			String vin = map.get("Vin");
			TmCactAllotPO tcPO = new TmCactAllotPO();
			List<TmCactAllotPO> tcList = TmCactAllotPO.find("VIN = ? AND STATUS = ? ", vin ,OemDictCodeConstants.STATUS_ENABLE);
			if(tcList.size()>0){
				tcPO = tcList.get(0);
			}
			TmVehiclePO tPO = TmVehiclePO.findFirst("VIN = ? ", vin);
			
			String code = map.get("StateCode").toString();
			if("1".equals(code)){
				List<Map> tmpList = selecedZdrrOrder(vin);
				Map tmp = new HashMap<String,Object>();
				if(tmpList.size()>0){
					tmp = tmpList.get(0);
				}
				
				TmVehiclePO tmvPO = new TmVehiclePO();
				
				Long dealerId = 0L;
				TmDealerPO tdPO = new TmDealerPO();
				if(!StringUtils.isNullOrEmpty(tcPO.getString("DEALER_CODE"))){
					tdPO = TmDealerPO.findFirst("DEALER_CODE = ? ", tcPO.getString("DEALER_CODE"));
					dealerId = tdPO.getLong("DEALER_ID");
				}else{
					dealerId = new Long(tmp.get("DEALER_ID").toString());
				}
				
				//将订单检查删除
				TmorderCheckPO.delete("VIN = ? ", vin);
				
				//将锁定数据设为无效,isSuc=1为成功信息
				TmCactAllotPO.update("STATUS = ?,IS_SUC = ?,ERROR_INFO = ?,UPDATE_BY = ?,UPDATE_DATE = ?",
						"VIN = ? AND STATUS = ?",
						OemDictCodeConstants.STATUS_DISABLE,1,"撤单成功",tcPO.getLong("CREATE_BY"),cal.getTime(),vin,OemDictCodeConstants.STATUS_ENABLE);
				
				//start 撤单  modify by luoyg
				TmOrderPayChangePO.update("AUDIT_TYPE = ?,AUDIT_STATUS = ?,AUDIT_DATE = ?,IS_SUC = ?,ERROR_INFO = ?",
						"VIN = ? AND IS_SUC = ?",
						OemDictCodeConstants.DUTY_TYPE_DEPT,OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04,
						cal.getTime(),1,"撤单成功",vin,3);
				//end
				
				//匹配更改日志表
				TtVsMatchCheckPO.update("UPDATE_BY = ?,UPDATE_DATE = ?,CANCEL_TYPE = ?,CANCEL_REASON = ?",
						"CHG_VEHICLE_ID = ? AND CANCEL_TYPE = ?",
						tcPO.getLong("CREATE_BY"),cal.getTime(),1002,"撤单",tPO.getLong("VEHICLE_ID"),1003);
				
				//记录详细车籍
				TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
				vhclChng.setLong("VEHICLE_ID", tPO.getLong("VEHICLE_ID"));
				vhclChng.setInteger("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_24);
				vhclChng.setTimestamp("CHANGE_DATE", cal.getTime());
				vhclChng.setLong("CREATE_BY", tcPO.getLong("UPDATE_BY"));
				vhclChng.setTimestamp("CREATE_DATE", cal.getTime());
				vhclChng.setString("CHANGE_DESC", "订单撤单");
				vhclChng.setInteger("RESOURCE_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
				vhclChng.setLong("RESOURCE_ID", dealerId);
				vhclChng.insert();
				
				TmVehiclePO.update("NODE_STATUS = ?,NODE_DATE = ?", "VIN = ?", OemDictCodeConstants.VEHICLE_NODE_18,cal.getTime(),vin);
				
				if(tmp.get("ORDER_TYPE").toString().equals(OemDictCodeConstants.ORDER_TYPE_03.toString())){
					//如果是指派订单,则返回已分配状态
					TtVsOrderPO.update("ORDER_STATUS = ?,PAYMENT_TYPE = ?,REMARK = ?,UPDATE_BY = ?,UPDATE_DATE = ?",
							"ORDER_ID = ?",
							OemDictCodeConstants.ORDER_STATUS_09,0,"撤单",new Long(44444441),cal.getTime(),
							new Long(tmp.get("ORDER_ID").toString()));
					
					TtVsAppointOrderDcsPO.update("STATUS = ? ", "ORDER_ID = ?", OemDictCodeConstants.STATUS_DISABLE,new Long(tmp.get("ORDER_ID").toString()));
					
					TtVsOrderPO tvOrderPO = new TtVsOrderPO();
					tvOrderPO.setString("VIN", map.get("Vin").toString());
					tvOrderPO.setLong("MATERAIL_ID = ?", tPO.getLong("MATERAIL_ID"));
					tvOrderPO.setLong("DEALER_ID", dealerId);//经销商ID
					tvOrderPO.setString("ORDER_NO", getOrderNO(OemDictCodeConstants.SALES_ORDER_APPOINT_NO));//订单号
					tvOrderPO.setTimestamp("ORDER_DATE", cal.getTime());
					tvOrderPO.setInteger("ORDER_TYPE", OemDictCodeConstants.ORDER_TYPE_03);//订单类型
					tvOrderPO.setInteger("ORDER_STATUS", OemDictCodeConstants.ORDER_STATUS_07);//订单状态
					tvOrderPO.setLong("CREATE_BY", new Long(44444441));//创建人
					tvOrderPO.setTimestamp("CREATE_DATE", cal.getTime());//创建时间
					tvOrderPO.insert();
					
					//订单指派表
					TtVsAppointOrderDcsPO ttPO = new TtVsAppointOrderDcsPO();
					ttPO.setLong("ORDER_ID", tvOrderPO.getLong("ORDER_ID"));
					ttPO.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
					ttPO.setLong("DEALER_ID", dealerId);
					ttPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);//指派状态
					ttPO.setTimestamp("APPOINT_DATE", cal.getTime());
					ttPO.setLong("APPOINT_BY", new Long(44444441));
					ttPO.setTimestamp("UPDATE_DATE", cal.getTime());
					ttPO.setLong("UPDATE_BY", new Long(44444441));
					ttPO.insert();	
				}else{
					//如果公共资源预分配有经销商，则分配给经销商：ZRL1;否则公共资源：ZRL1
					if(!StringUtils.isNullOrEmpty(tcPO.getString("DEALER_CODE"))){
						TtVsOrderPO.update("ORDER_STATUS = ?,PAYMENT_TYPE = ?,REMARK = ?,UPDATE_BY = ?,UPDATE_DATE = ?",
								"ORDER_ID = ?",
								OemDictCodeConstants.ORDER_STATUS_09,0,"撤单",new Long(44444441),cal.getTime(),
								new Long(tmp.get("ORDER_ID").toString()));
						
						TtVsAppointOrderDcsPO.update("STATUS = ? ", "ORDER_ID = ?", OemDictCodeConstants.STATUS_DISABLE,new Long(tmp.get("ORDER_ID").toString()));
						
						TtVsOrderPO tvOrderPO = new TtVsOrderPO();
						tvOrderPO.setString("VIN", map.get("Vin").toString());
						tvOrderPO.setLong("MATERAIL_ID = ?", tPO.getLong("MATERAIL_ID"));
						tvOrderPO.setLong("DEALER_ID", dealerId);//经销商ID
						tvOrderPO.setString("ORDER_NO", getOrderNO(OemDictCodeConstants.SALES_ORDER_APPOINT_NO));//订单号
						tvOrderPO.setTimestamp("ORDER_DATE", cal.getTime());
						tvOrderPO.setInteger("ORDER_TYPE", OemDictCodeConstants.ORDER_TYPE_03);//订单类型
						tvOrderPO.setInteger("ORDER_STATUS", OemDictCodeConstants.ORDER_STATUS_07);//订单状态
						tvOrderPO.setLong("CREATE_BY", new Long(44444441));//创建人
						tvOrderPO.setTimestamp("CREATE_DATE", cal.getTime());//创建时间
						tvOrderPO.insert();
						
						//订单指派表
						TtVsAppointOrderDcsPO ttPO = new TtVsAppointOrderDcsPO();
						ttPO.setLong("ORDER_ID", tvOrderPO.getLong("ORDER_ID"));
						ttPO.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
						ttPO.setLong("DEALER_ID", dealerId);
						ttPO.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);//指派状态
						ttPO.setTimestamp("APPOINT_DATE", cal.getTime());
						ttPO.setLong("APPOINT_BY", new Long(44444441));
						ttPO.setTimestamp("UPDATE_DATE", cal.getTime());
						ttPO.setLong("UPDATE_BY", new Long(44444441));
						ttPO.insert();	
					}else{
						List<Map> ttList = findCommonResource(map.get("Vin").toString());
						Map<String,Object> tmap = new HashMap<String,Object>();
						String commonId = "0";
						if(ttList.size()>0){
							tmap = ttList.get(0);	
							commonId = tmap.get("COMMON_ID").toString();
						}
						
						TtVsCommonResourcePO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?","COMMON_ID = ?", 
								OemDictCodeConstants.COMMON_RESOURCE_STATUS_03,new Long(44444441),cal.getTime(),new Long(commonId));
									
						TtVsCommonResourceDetailPO.update("STATUS = ?", "COMMON_ID = ?", OemDictCodeConstants.STATUS_DISABLE,new Long(commonId));
						
						//车辆表的经销商ID设为空
						TmVehiclePO.update("DEALER_ID = ?,ORG_ID = ?,NODE_STATUS = ?,NODE_DATE = ?", "VIN = ?",new Long(0),new Long(0), OemDictCodeConstants.VEHICLE_NODE_18,cal.getTime(),vin);
						
						//插入公共资源记录
						TtVsCommonResourcePO tvcr = new TtVsCommonResourcePO();
						tvcr.setLong("VEHICLE_ID", tPO.getLong("VEHICLE_ID"));			
						tvcr.setLong("CREATE_BY", new Long(44444441));
						tvcr.setTimestamp("CREATE_DATE", cal.getTime());				
						tvcr.setLong("RESOURCE_SCOPE",new Long(tmap.get("RESOURCE_SCOPE").toString()));				
						tvcr.setInteger("STATUS",OemDictCodeConstants.COMMON_RESOURCE_STATUS_02);//资源状态（未下发1，已下发2，已取消3）
						tvcr.setInteger("TYPE",new Integer(tmap.get("TYPE").toString()));
						tvcr.setTimestamp("ISSUED_DATE",cal.getTime());
						tvcr.insert();
						
						//插入公共资源明细记录
						List<Map> factorys = getFactoryId(map.get("Vin").toString());
						long factoryid =0;
						if(factorys.size()>0){
							factorys.get(0);
							String ts =  ((Map) factorys.get(0)).get("COMMON_DETAIL_ID").toString();
							factoryid = Long.valueOf(ts);
						}				
						TtVsCommonResourceDetailPO tvcrd = new TtVsCommonResourceDetailPO();
						tvcrd.setLong("COMMON_ID", tvcr.getLong("COMMON_ID"));
						tvcrd.setLong("CREATE_BY",new Long(44444441));
						tvcrd.setTimestamp("CREATE_DATE",cal.getTime());
						tvcrd.setLong("FACTORY_ORDER_ID",factoryid);//工厂id
						tvcrd.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);//资源有效状态
						tvcrd.setInteger("TYPE",tvcr.getInteger("TYPE"));
						tvcrd.setLong("VEHICLE_ID",tPO.getLong("VEHICLE_ID"));//车辆ID
						tvcrd.insert();
						
						//设置订单表的公共资源ID为空,订单状态为已撤单
						TtVsOrderPO.update("COMMONALITY_ID = ?,ORDER_STATUS = ?", "ORDER_ID = ?", new Long(0),OemDictCodeConstants.ORDER_STATUS_09,new Long(tmp.get("ORDER_ID").toString()));
						
					}
				}
				
			}else if("0".equals(code)){
				//将锁定数据设为无效,isSuc=0为失败信息
				TmCactAllotPO.update("STATUS = ?,IS_SUC = ?,ERROR_INFO = ?,UPDATE_BY = ?,UPDATE_DATE = ?",
						"VIN = ? AND STATUS = ?",
						OemDictCodeConstants.STATUS_DISABLE,0,map.get("ErrorInfo").toString(),tcPO.getLong("CREATE_BY"),cal.getTime(),vin,OemDictCodeConstants.STATUS_ENABLE);
				
				//start 撤单  modify by luoyg
				String errorInfo = "";
				if(map.get("ErrorInfo").toString().contains("没有下订单")){
					errorInfo = map.get("ErrorInfo").toString()+"请联系中进确认接收订单";
				}else{
					errorInfo = map.get("ErrorInfo").toString()+"请联系中进";
				}
				TmOrderPayChangePO.update("AUDIT_TYPE = ?,AUDIT_STATUS = ?,AUDIT_DATE = ?,IS_SUC = ?,ERROR_INFO = ?",
						"VIN = ? AND IS_SUC = ?",
						OemDictCodeConstants.DUTY_TYPE_DEPT,OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04,
						cal.getTime(),2,errorInfo,vin,3);
				//end
				
				//匹配更改日志表
				TtVsMatchCheckPO.update("UPDATE_BY = ?,UPDATE_DATE = ?,CANCEL_TYPE = ?,CANCEL_REASON = ?",
						"CHG_VEHICLE_ID = ? AND CANCEL_TYPE = ?",
						tcPO.getLong("CREATE_BY"),cal.getTime(),1002,"撤单",tPO.getLong("VEHICLE_ID"),1003);
				
			}
			
			//解锁车辆
			TtResourceRemarkPO.update("IS_LOCK = ?", "VIN = ?", 0,vin);
			
			//start
			//对临时表TMP_CANCEL_ORDER进行修改
			String mapvin = map.get("Vin").toString();
			TmpCancelOrderDcsPO.update("STATUS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "VIN = ? AND STATUS = ?", 
					1,88888888,new Date(),mapvin,0);
			//end
			
		}
		
		
		
	}

	private List<Map> getFactoryId(String vin) {
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT COMMON_DETAIL_ID\n");
		sql.append("  FROM TT_VS_FACTORY_ORDER\n");
		sql.append(" WHERE 1 = 1\n");
		sql.append(" and VIN = '");
		sql.append(vin);
		sql.append("'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findCommonResource(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select TVCR.COMMON_ID,TVCR.TYPE,TVCR.RESOURCE_SCOPE\n");
		sql.append("   from  TM_VEHICLE                 	   TV,\n");
		sql.append("  		 VW_MATERIAL                	   VM,\n");
		sql.append("  		 TT_VS_COMMON_RESOURCE      	   TVCR,\n");
		sql.append("   		 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("    where  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("   	  and  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("  	  and  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");		
		sql.append("  	  and  TVCR.STATUS="+OemDictCodeConstants.COMMON_RESOURCE_STATUS_02+"\n");
		sql.append("      and  TVCRD.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append("	  and  TV.VIN='"+vin+"'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> selecedZdrrOrder(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvo.ORDER_ID,tvo.ORDER_TYPE,tvo.VIN,tvo.DEALER_ID\n");
		sql.append("   from TT_VS_ORDER tvo,TM_VEHICLE tv, TM_DEALER td, VW_MATERIAL vm\n");
		sql.append("   where tvo.VIN=tv.VIN\n");
		sql.append("     and tvo.DEALER_ID = td.DEALER_ID\n");
		sql.append("     and tvo.MATERAIL_ID = vm.MATERIAL_ID\n");
		sql.append("     and tv.LIFE_CYCLE in("+OemDictCodeConstants.LIF_CYCLE_01+","+OemDictCodeConstants.LIF_CYCLE_02+")\n");
		sql.append("	 and tv.NODE_STATUS in("+OemDictCodeConstants.VEHICLE_NODE_19+")\n");
		sql.append("     and tvo.ORDER_STATUS<"+OemDictCodeConstants.ORDER_STATUS_08+"\n");
		sql.append("     and tvo.vin='"+vin+"'\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 生成订单号 订单号规则：经销商代码+年+月+日+序列号(从001开始递增)
	 */
	public static String getOrderNO(String dealerCode) {

		StringBuffer orderNO = new StringBuffer();

		Calendar calendar = Calendar.getInstance();
		int year_ = calendar.get(calendar.YEAR);
		String year = year_ + "";
		int month_ = calendar.get(calendar.MONTH) + 1;
		String month = month_ + "";
		if (month.length() < 2) {
			month = month.format("0" + month, month);
		}
		int day_ = calendar.get(calendar.DAY_OF_MONTH);
		String day = day_ + "";
		if (day.length() < 2) {
			day = day.format("0" + day, day);
		}
		/*
		 * 查询数据库中是否已有相关最大订单号，如果有，新订单号=原订单号+1 否则新生成订单号
		 */
		Map<String, String> oldOrderNOMap = getMaxOrderSeq(dealerCode, year, month, day);
		String oldOrderNO = oldOrderNOMap.get("ORDER_NO");
		if (null != oldOrderNO && !"".equals(oldOrderNO)) {
			String commonNO = oldOrderNO.substring(0, oldOrderNO.length() - 4); // 1得到订单序列号前的号码
			String indexNO = oldOrderNO.substring(oldOrderNO.length() - 4, oldOrderNO.length()); // 2.得到订单序列号
			int index_res = Integer.parseInt(indexNO) + 1; // 3.将订单序列号+1
			String index_res_ = index_res + ""; // 4.如果订单序列号不足三位，用0进行填补
			StringBuffer noBuffer = new StringBuffer();
			int index_res_length = 4 - index_res_.length();
			for (int i = 0; i < index_res_length; i++) {
				noBuffer.append("0");
			}
			noBuffer.append(index_res_);
			orderNO.append(commonNO).append(noBuffer); // 5.将从数据中查询到的订单号+1后，赋给orderNO
			return orderNO.toString();
		} else {
			orderNO.append(dealerCode);
			return orderNO.append(year).append(month).append(day).append("0001").toString();
		}
	}
	
	/**
	 * 查询最大订单号
	 */
	public static Map getMaxOrderSeq(String dealerCode, String year, String month, String day) {

		StringBuffer orderNOBuffer = new StringBuffer();
		orderNOBuffer.append(dealerCode).append(year).append(month).append(day);

		StringBuffer sql = new StringBuffer();
		sql.append("SELECT MAX(TTO.ORDER_NO) ORDER_NO FROM TT_VS_ORDER TTO WHERE TTO.ORDER_NO LIKE '" + orderNOBuffer + "%'\n");
		return OemDAOUtil.findAll(sql.toString(), null).get(0);
	}

}
