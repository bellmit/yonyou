package com.yonyou.dms.gacfca;
//package com.yonyou.dms.gacfca;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.log4j.Logger;
//
//import com.yonyou.dms.DTO.gacfca.SADCS026DTO;
//import com.yonyou.dms.DTO.gacfca.SADMCS026DTO;
//import com.yonyou.dms.common.Util.Utility;
//import com.yonyou.dms.framework.DAO.DAOUtil;
//import com.yonyou.dms.function.common.CommonConstants;
//
///**
// * @description  计划任务统计配件入库来源监控报表并上报总部
// * @author Administrator
// *
// */
//public class SADMS026Impl implements SADMS026 {
//	final Logger logger = Logger.getLogger(SADMS026Impl.class);
//
//	@Override
//	public List<SADMCS026DTO> getSADMS026(String dealerCode) {
//		logger.info("==========SADMS026Impl执行===========");
//		List<SADCS026DTO> result = new LinkedList<SADCS026DTO>();
//		try{ 
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			Calendar calm = Calendar.getInstance();
//			calm.setTime(new Date()); 
//			Calendar calmLast = Calendar.getInstance();
//			calmLast.set(Calendar.DAY_OF_MONTH, calmLast.getActualMaximum(Calendar.DAY_OF_MONTH));
//			Boolean sign = false;
//			if (!format.format(calmLast.getTime()).equals(format.format(calm.getTime()))) {//判断当前日期是否是本月最后一天
//				if (calm.get(Calendar.DAY_OF_WEEK) != 5){//判断当前是不是星期四
//					return null;
//				}
//			} else {
//				sign = true;
//			}
//
//			String endDate=format.format(calm.getTime()).toString() + " 23:59:59";
//
//			calm.add(Calendar.MONTH,0);
//			calm.set(Calendar.DAY_OF_MONTH, 1);
//			String beginDate=format.format(calm.getTime()).toString()+ " 00:00:00";
//
//			if(sign){
//				//每月月底执行此计划任务 并上报总部
//				//货运单入库配件数量
//				double deliverPartQuantity = 0.00;
//				//货运单入库配件金额
//				double deliverPartAmount = 0.00;
//				//临时入库配件数量
//				double allPartQuantity = 0.00;
//				//临时入库配件金额
//				double allPartAmount = 0.00;
//				//临时入库已核销配件数量
//				double proPartQuantity = 0.00;
//				//临时入库已核销配件金额
//				double proPartAmount = 0.00;
//				//手工入库配件数量
//				double handlePartQuantity = 0.00;
//				//手工入库配件金额
//				double handlePartAmount = 0.00;
//				//调拨配件数量
//				double allocatePartQuantity = 0.00;
//				//调拨配件金额
//				double allocatePartAmount = 0.00;
//				//报溢配件数量
//				double profitPartQuantity = 0.00;
//				//报溢配件金额
//				double profitPartAmount = 0.00;
//				//汇总配件数量
//				double sumPartQuantity = 0.00;
//				//汇总配件金额
//				double sumPartAmount = 0.00;
//
//				//配件入库来源接口上报定义
//				List<Map> partList = querySubmitPartDeliverReport(dealerCode, beginDate, endDate);
//				if (partList != null && partList.size() > 0) {
//					for(Map<String,Object> bean : partList) {
//						deliverPartQuantity = (double)Math.round(Double.parseDouble(bean.get("PART_QUANTITY_SUM").toString())*100)/100 ;//货运单入库配件数量
//						deliverPartAmount = (double)Math.round(Double.parseDouble(bean.get("PART_AMOUNT_TEXEDSUM").toString())*100)/100 ;//货运单入库配件金额
//						allPartQuantity = (double)Math.round(Double.parseDouble(bean.get("ALL_PART_QUANTITY_SUM").toString())*100)/100 ;//临时入库配件数量
//						allPartAmount = (double)Math.round(Double.parseDouble(bean.get("ALL_PART_AMOUNT_TEXEDSUM").toString())*100)/100; //临时入库配件金额
//						proPartQuantity = (double)Math.round(Double.parseDouble(bean.get("PRO_PART_QUANTITY_SUM").toString())*100)/100;//临时入库已核销配件数量
//						proPartAmount = (double)Math.round(Double.parseDouble(bean.get("PRO_PART_AMOUNT_TEXEDSUM").toString())*100)/100;//临时入库已核销配件金额
//						handlePartQuantity = (double)Math.round(Double.parseDouble(bean.get("HAN_PART_QUANTITY_SUM").toString())*100)/100;//手工入库配件数量
//						handlePartAmount = (double)Math.round(Double.parseDouble(bean.get("HAN_PART_AMOUNT_TEXEDSUM").toString())*100)/100;//手工入库配件金额
//						allocatePartQuantity = (double)Math.round(Double.parseDouble(bean.get("LO_PART_QUANTITY_SUM").toString())*100)/100;//调拨入库配件数量
//						allocatePartAmount = (double)Math.round(Double.parseDouble(bean.get("LO_PART_AMOUNT_TEXEDSUM").toString())*100)/100;//调拨入库配件金额
//						profitPartQuantity = (double)Math.round(Double.parseDouble(bean.get("PROFIT_PART_QUANTITY_SUM").toString())*100)/100;//报溢入库配件数量
//
//						//edit by jll 2015-11-26 报溢入库配件金额之前取值错误，取了临时入库已核销配件金额，导致上端该日期之前的数据都是错误的
//						profitPartAmount = (double)Math.round(Double.parseDouble(bean.get("PROFIT_PART_AMOUNT_TEXEDSUM").toString())*100)/100;//报溢入库配件金额
//						//			    		edit by jll 2015-11-26 汇总配件数量和汇总配件金额 算法写错 导致上端这2个数值也是错误的
//						sumPartQuantity = (double)Math.round((Double.parseDouble(bean.get("PART_QUANTITY_SUM").toString())+Double.parseDouble(bean.get("ALL_PART_QUANTITY_SUM").toString())+
//								Double.parseDouble(bean.get("HAN_PART_QUANTITY_SUM").toString())+ Double.parseDouble(bean.get("LO_PART_QUANTITY_SUM").toString())+
//								Double.parseDouble(bean.get("PROFIT_PART_QUANTITY_SUM").toString()))*100)/100;//汇总配件数量
//
//						sumPartAmount = (double)Math.round((Double.parseDouble(bean.get("PART_AMOUNT_TEXEDSUM").toString())+Double.parseDouble(bean.get("ALL_PART_AMOUNT_TEXEDSUM").toString())+
//								Double.parseDouble(bean.get("HAN_PART_AMOUNT_TEXEDSUM").toString())+Double.parseDouble(bean.get("LO_PART_AMOUNT_TEXEDSUM").toString())+
//								Double.parseDouble(bean.get("PRO_PART_AMOUNT_TEXEDSUM").toString()))*100)/100;//汇总配件金额
//
//
//
//						SADCS026DTO vo = new SADCS026DTO();
//						vo.setEntityCode(dealerCode);
//						vo.setDeliverPartQuantity(deliverPartQuantity);
//						vo.setDeliverPartAmount(deliverPartAmount);
//						vo.setProvisPartQuantity(proPartQuantity);
//						vo.setProvisPartAmount(proPartAmount);
//						vo.setVerPartQuantity(allPartQuantity-proPartQuantity);
//						vo.setVerPartAmount(allPartAmount-proPartAmount);
//						vo.setHandlePartQuantity(handlePartQuantity);
//						vo.setHandlePartAmount(handlePartAmount);
//						vo.setAllocatePartQuantity(allocatePartQuantity);
//						vo.setAllocatePartAmount(allocatePartAmount);
//						vo.setProfitPartQuantity(profitPartQuantity);
//						vo.setProfitPartAmount(profitPartAmount);
//						vo.setSumPartQuantity(sumPartQuantity);
//						vo.setSumPartAmount(sumPartAmount);
//						vo.setUploadTimestamp(Utility.getCurrentDateTime());
//						result.add(vo);	
//					}
//				}
//			}
//			return result;
//		} catch(Exception e) {
//			e.printStackTrace();
//			logger.debug(e);
//			return null;
//		}finally{
//			logger.info("==========SADMS026Impl结束===========");
//		}
//	}
//
//
//	/**
//	 * 查询DMS配件入库数据上报到上端(每月月底)
//	 * @param con
//	 * @param dealerCode
//	 * @param beginDate
//	 * @param endDate
//	 * @return
//	 * @throws Exception
//	 */ 
//	public List<Map> querySubmitPartDeliverReport(String dealerCode,String beginDate,String endDate) throws Exception{
//		String sql = " select sum(PART_QUANTITY_SUM) as PART_QUANTITY_SUM, " + //货运单入库配件总数量
//				" sum(PART_AMOUNT_TEXEDSUM) as PART_AMOUNT_TEXEDSUM, " +//货运单入库配件总金额
//				" sum(ALL_PART_QUANTITY_SUM) as ALL_PART_QUANTITY_SUM, " +//临时入库配件总数量
//				" sum(ALL_PART_AMOUNT_TEXEDSUM) as ALL_PART_AMOUNT_TEXEDSUM, " +//临时入库配件总金额
//				" sum(PRO_PART_QUANTITY_SUM) as PRO_PART_QUANTITY_SUM, " +//临时入库已核销配件总数量
//				" sum(PRO_PART_AMOUNT_TEXEDSUM) as PRO_PART_AMOUNT_TEXEDSUM, " +//临时入库已核销配件总金额
//				" sum(HAN_PART_QUANTITY_SUM) as HAN_PART_QUANTITY_SUM, " +//手工入库配件总数量
//				" sum(HAN_PART_AMOUNT_TEXEDSUM) as HAN_PART_AMOUNT_TEXEDSUM, " +//手工入库配件总金额
//				" sum(LO_PART_QUANTITY_SUM) as LO_PART_QUANTITY_SUM," + //调拨入库配件总数量
//				" sum(LO_PART_AMOUNT_TEXEDSUM) as LO_PART_AMOUNT_TEXEDSUM," + // 调拨入库配件总金额
//				" sum(PROFIT_PART_QUANTITY_SUM) as PROFIT_PART_QUANTITY_SUM," + // 报溢配件总数量
//				" sum(PROFIT_PART_AMOUNT_TEXEDSUM) as PROFIT_PART_AMOUNT_TEXEDSUM " + // 报溢配件总金额  
//				" from ( " +
//				" select COALESCE(SUM(B.IN_QUANTITY),0) AS PART_QUANTITY_SUM,COALESCE(SUM(B.IN_AMOUNT_TAXED),0) AS PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS ALL_PART_QUANTITY_SUM,0 AS ALL_PART_AMOUNT_TEXEDSUM ,0 AS PRO_PART_QUANTITY_SUM, 0 AS PRO_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS HAN_PART_QUANTITY_SUM,0 AS HAN_PART_AMOUNT_TEXEDSUM," +
//				" 0 AS LO_PART_QUANTITY_SUM, 0 AS LO_PART_AMOUNT_TEXEDSUM,0 AS PROFIT_PART_QUANTITY_SUM, 0 AS PROFIT_PART_AMOUNT_TEXEDSUM " +
//				"  from TT_PART_BUY A INNER JOIN TT_PART_BUY_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE  " +
//				" AND A.STOCK_IN_NO=B.STOCK_IN_NO  WHERE A.DELVIERY_NO IS NOT NULL AND A.DELVIERY_NO!='' and A.IS_FINISHED=12781001 " +
//				" AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"'  " +  //货运单入库的配件总数量、总金额
//				" UNION ALL " +
//				" select 0 AS PART_QUANTITY_SUM, 0 AS PART_AMOUNT_TEXEDSUM,COALESCE(SUM(B.IN_QUANTITY),0) AS ALL_PART_QUANTITY_SUM," +
//				" COALESCE(SUM(B.IN_AMOUNT_TAXED),0) AS ALL_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS PRO_PART_QUANTITY_SUM, 0 AS PRO_PART_AMOUNT_TEXEDSUM,0 AS HAN_PART_QUANTITY_SUM,0 AS HAN_PART_AMOUNT_TEXEDSUM," +
//				" 0 AS LO_PART_QUANTITY_SUM, 0 AS LO_PART_AMOUNT_TEXEDSUM,0 AS PROFIT_PART_QUANTITY_SUM, 0 AS PROFIT_PART_AMOUNT_TEXEDSUM  " +
//				" from TT_PART_BUY A INNER JOIN TT_PART_BUY_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE  " +
//				" AND A.STOCK_IN_NO=B.STOCK_IN_NO  WHERE  A.STOCK_IN_TYPE = 70051002 AND A.OEM_ORDER_NO IS NOT NULL AND  " +
//				" A.OEM_ORDER_NO!='' and A.IS_FINISHED=12781001  " +
//				" AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"'  " + //统计临时入库的配件总数量X、总金额Y
//				" UNION ALL " +
//				" select 0 AS PART_QUANTITY_SUM, 0 AS PART_AMOUNT_TEXEDSUM,0 AS ALL_PART_QUANTITY_SUM,0 AS ALL_PART_AMOUNT_TEXEDSUM ," +
//				" COALESCE(SUM(D.SUPPLY_QTY),0) AS PRO_PART_QUANTITY_SUM,COALESCE(SUM(D.TAXED_AMOUNT),0) AS PRO_PART_AMOUNT_TEXEDSUM" +
//				" ,0 AS HAN_PART_QUANTITY_SUM,0 AS HAN_PART_AMOUNT_TEXEDSUM," +
//				" 0 AS LO_PART_QUANTITY_SUM, 0 AS LO_PART_AMOUNT_TEXEDSUM,0 AS PROFIT_PART_QUANTITY_SUM, 0 AS PROFIT_PART_AMOUNT_TEXEDSUM from  " +
//				" TT_PART_BUY A INNER JOIN TT_PART_BUY_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE  " +
//				" AND A.STOCK_IN_NO=B.STOCK_IN_NO  INNER JOIN TT_PT_DELIVER C ON A.ENTITY_CODE=C.ENTITY_CODE AND A.OEM_ORDER_NO = C.DELIVERY_ORDER_NO " +
//				" INNER JOIN TT_PT_DELIVER_ITEM D ON C.ENTITY_CODE=D.ENTITY_CODE AND C.ORDER_REGEDIT_NO = D.ORDER_REGEDIT_NO " +
//				"  " +
//				" WHERE  A.STOCK_IN_TYPE = 70051002 AND A.OEM_ORDER_NO IS NOT NULL AND " +
//				" A.OEM_ORDER_NO!='' and A.IS_FINISHED=12781001 AND C.IS_VERIFICATION=12781001 " + //统计已核销的临时入库的配件总数量X1、总金额Y1，X-X1=临时入库未核销的配件总数量
//				" AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"' " + //Y-Y1= 临时入库未核销的配件总金额
//				" UNION ALL " +
//				" select 0 AS PART_QUANTITY_SUM, 0 AS PART_AMOUNT_TEXEDSUM,0 AS ALL_PART_QUANTITY_SUM,0 AS ALL_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS PRO_PART_QUANTITY_SUM, 0 AS PRO_PART_AMOUNT_TEXEDSUM  " +
//				" ,COALESCE(SUM(B.IN_QUANTITY),0) AS HAN_PART_QUANTITY_SUM,COALESCE(SUM(B.IN_AMOUNT_TAXED),0) AS HAN_PART_AMOUNT_TEXEDSUM," +
//				" 0 AS LO_PART_QUANTITY_SUM, 0 AS LO_PART_AMOUNT_TEXEDSUM,0 AS PROFIT_PART_QUANTITY_SUM, 0 AS PROFIT_PART_AMOUNT_TEXEDSUM  " +
//				" from TT_PART_BUY A INNER JOIN TT_PART_BUY_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE " +
//				" AND A.STOCK_IN_NO=B.STOCK_IN_NO  WHERE  A.STOCK_IN_TYPE = 70051003 and A.IS_FINISHED=12781001 " + //手工入库的配件总数量、总金额
//				" AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"' " +
//				" UNION ALL " +
//				" select 0 AS PART_QUANTITY_SUM, 0 AS PART_AMOUNT_TEXEDSUM,0 AS ALL_PART_QUANTITY_SUM,0 AS ALL_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS PRO_PART_QUANTITY_SUM, 0 AS PRO_PART_AMOUNT_TEXEDSUM,0 AS HAN_PART_QUANTITY_SUM,0 AS HAN_PART_AMOUNT_TEXEDSUM, " +
//				" COALESCE(SUM(B.IN_QUANTITY),0) AS LO_PART_QUANTITY_SUM,COALESCE(SUM(B.IN_AMOUNT),0)*1.17 AS LO_PART_AMOUNT_TEXEDSUM," +
//				" 0 AS PROFIT_PART_QUANTITY_SUM, 0 AS PROFIT_PART_AMOUNT_TEXEDSUM  " +
//				" from TT_PART_ALLOCATE_IN A INNER JOIN TT_PART_ALLOCATE_IN_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE " +
//				" AND A.ALLOCATE_IN_NO=B.ALLOCATE_IN_NO " +
//				" WHERE A.IS_FINISHED=12781001 AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"' " + //调拨入库配件总数量、总金额
//				" UNION ALL  " +
//				" select 0 AS PART_QUANTITY_SUM, 0 AS PART_AMOUNT_TEXEDSUM,0 AS ALL_PART_QUANTITY_SUM,0 AS ALL_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS PRO_PART_QUANTITY_SUM, 0 AS PRO_PART_AMOUNT_TEXEDSUM,0 AS HAN_PART_QUANTITY_SUM,0 AS HAN_PART_AMOUNT_TEXEDSUM, " +
//				" 0 AS LO_PART_QUANTITY_SUM, 0 AS LO_PART_AMOUNT_TEXEDSUM " +
//				" ,COALESCE(SUM(B.PROFIT_QUANTITY),0) AS PROFIT_PART_QUANTITY_SUM,COALESCE(SUM(B.PROFIT_AMOUNT),0)*1.17 AS PROFIT_PART_AMOUNT_TEXEDSUM " +
//				" from TT_PART_PROFIT A INNER JOIN TT_PART_PROFIT_ITEM B ON A.ENTITY_CODE=B.ENTITY_CODE AND A.PROFIT_NO=B.PROFIT_NO  " +
//				"  WHERE A.IS_FINISHED=12781001 AND A.ENTITY_CODE='"+dealerCode+"' AND A.FINISHED_DATE >= '"+beginDate+"' AND A.FINISHED_DATE<= '"+endDate+"' " + //报溢配件总数量、总金额
//				" ) ";
//		return DAOUtil.findAll(sql, null);
//	}
//}
