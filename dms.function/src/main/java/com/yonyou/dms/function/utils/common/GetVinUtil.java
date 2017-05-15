package com.yonyou.dms.function.utils.common;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GetVinUtil {

	/**
	 * 入参
	 * @param voucherId 要查询的卡券ID
	 * @param alias 别名
	 * @return
	 */
	public static String getVoucherIds(String voucherId,String alias){
		String returnSql = "";
		if (null != voucherId && !"".equals(voucherId)) {
			voucherId = voucherId.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对卡券ID进行拆分
			String sP = "\\r\\n";
			if(voucherId.indexOf("\r\n")==-1 && voucherId.indexOf("\n")>0)
			{
				inbuffer.append(" AND "+alias).append(".VOUCHER_ID");
				likeBuffer.append(" AND (");
				sP = "\n";
			}else 
			{
				inbuffer.append("   AND ").append(alias).append(".VOUCHER_ID");
				//LIKE查询
				
				likeBuffer.append("   AND (");
			}
			String[] voucherIds = voucherId.split(sP);
			//得到卡券ID长度：strLength  非20位   ：LIKE查询；20位：IN查询
			int strLength = voucherIds[0].trim().length();
			//对卡券ID进行重复数据过滤
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < voucherIds.length; j++) {
				if(!voucherIds[j].equals("")){
					set.add(voucherIds[j].trim());
				}
			}
			//对卡券ID进行组合
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				if (strLength == 20) {//如果是IN查询
					buffer.append("'").append(s).append("'").append(",");
				}else {//如果是LIKE查询
					buffer.append(alias).append(".VOUCHER_ID LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");
				}
			}
			//将卡券ID封装成SQL
			//IN查询
			if (strLength == 20) {
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql = inbuffer.append(" IN (").append(buffer).append(")\n").toString();
			}else{
				buffer=buffer.delete(buffer.length()-3, buffer.length());
				likeBuffer.append(buffer).append(")\n");
				returnSql = likeBuffer.toString();
			}
		}
		return returnSql;
	}
	/**
	 * 入参
	 * @param insureOrderCode 要查询的投保单号
	 * @param alias 别名
	 * @return
	 */
	public static String getInsureOrderCodes(String insureOrderCode,String alias){
		String returnSql = "";
		if (null != insureOrderCode && !"".equals(insureOrderCode)) {
			insureOrderCode = insureOrderCode.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 投保单号进行拆分
			String sP = "\\r\\n";
			if(insureOrderCode.indexOf("\r\n")==-1 && insureOrderCode.indexOf("\n")>0)
			{
				inbuffer.append(" AND "+alias).append(".INSURE_ORDER_CODE");
				likeBuffer.append(" AND (");
				sP = "\n";
			}else 
			{
				inbuffer.append("   AND ").append(alias).append(".INSURE_ORDER_CODE");
				//LIKE查询
				
				likeBuffer.append("   AND (");
			}	
			String[] insureOrderCodes = insureOrderCode.split(sP);
			//得到投保单号长度：strLength  非12位   ：LIKE查询；12位：IN查询
			int strLength = insureOrderCodes[0].trim().length();
			//对投保单号进行重复数据过滤
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < insureOrderCodes.length; j++) {
				if(!insureOrderCodes[j].equals("")){
					set.add(insureOrderCodes[j].trim());
				}
			}
			//对投保单号进行组合
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				if (strLength == 12) {//如果是IN查询
					buffer.append("'").append(s).append("'").append(",");
				}else {//如果是LIKE查询
					buffer.append(alias).append(".INSURE_ORDER_CODE LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");
				}
			}
			//将投保单号封装成SQL
			//IN查询
			if (strLength == 12) {
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql = inbuffer.append(" IN (").append(buffer).append(")\n").toString();
			}else{
				buffer=buffer.delete(buffer.length()-3, buffer.length());
				likeBuffer.append(buffer).append(")\n");
				returnSql = likeBuffer.toString();
			}
		}
		return returnSql;
	}
	
	/* 入参：
	 * 1.vin:要查询的vin
	 * 2.alias：TM_VEHICLE表的别名
	 * */
	public static String getVins(String vin,String alias){
		String returnSql = "";
		if (null != vin && !"".equals(vin)) {
			vin = vin.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 VIN进行拆分
			String sP = "\\r\\n";
			if(vin.indexOf("\r\n")==-1 && vin.indexOf("\n")>0)
			{
				inbuffer.append(" AND "+alias).append(".VIN");
				likeBuffer.append(" AND (");
				sP = "\n";
			}else 
			{
				inbuffer.append("   AND ").append(alias).append(".VIN");
				//LIKE查询
				
				likeBuffer.append("   AND (");
			}	
			String[] vins = vin.split(sP);
			//得到VIN长度：strLength  非17位   ：LIKE查询；17位：IN查询
			int strLength = vins[0].trim().length();
			//对VIN进行重复数据过滤
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if(!vins[j].equals("")){
					set.add(vins[j].trim());
				}
			}
			//对VIN进行组合
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				if (strLength == 17) {//如果是IN查询
					buffer.append("'").append(s).append("'").append(",");
				}else {//如果是LIKE查询
					buffer.append(alias).append(".VIN LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");
				}
			}
			//将VIN封装成SQL
			//IN查询
			if (strLength == 17) {
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql = inbuffer.append(" IN (").append(buffer).append(")\n").toString();
			}else{
				buffer=buffer.delete(buffer.length()-3, buffer.length());
				likeBuffer.append(buffer).append(")\n");
				returnSql = likeBuffer.toString();
			}
		}
		return returnSql;
	}
	
	public static String getVins(String vin,String alias,String autoWhere ){
		String returnSql = "";
		if (null != vin && !"".equals(vin)) {
			vin = vin.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 VIN进行拆分
			String sP = "\\r\\n";
			if(vin.indexOf("\r\n")==-1 && vin.indexOf("\n")>0)
			{
				inbuffer.append(alias).append(".VIN");
				likeBuffer.append(" (");
				sP = "\n";
			}else 
			{
				inbuffer.append(alias).append(".VIN");
				//LIKE查询
				
				likeBuffer.append("  (");
			}	
			String[] vins = vin.split(sP);
			//得到VIN长度：strLength  非17位   ：LIKE查询；17位：IN查询
			int strLength = vins[0].trim().length();
			//对VIN进行重复数据过滤
			Set<String> set = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				set.add(vins[j].trim());
			}
			//对VIN进行组合
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next();
				if (strLength == 17) {//如果是IN查询
					buffer.append("'").append(s).append("'").append(",");
				}else {//如果是LIKE查询
					buffer.append(alias).append(".VIN LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");
				}
			}
			//将VIN封装成SQL
			//IN查询
			if (strLength == 17) {
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql = inbuffer.append(" IN (").append(buffer).append(")\n").toString();
			}else{
				buffer=buffer.delete(buffer.length()-3, buffer.length());
				likeBuffer.append(buffer).append(")\n");
				returnSql = likeBuffer.toString();
			}
		}
		return returnSql;
	}
	/* 入参：
	 * 1.vin:要查询的vin
	 * 2.alias：TM_VEHICLE表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like'
	 * update by luoyg 2013/11/19
	 * */
	public static String getVinsAuto(String vin,String alias){
		String returnSql = "";
		if (null != vin && !"".equals(vin)) {
			vin = vin.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 VIN进行拆分
			String sP = "\\r\\n";
			if(vin.indexOf("\r\n")==-1 && vin.indexOf("\n")>0){
				sP = "\n";
			}
			String[] vins = vin.split(sP);
			//得到VIN长度：strLength  非17位   ：LIKE查询；17位：IN查询
			//对VIN进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if(!vins[j].equals("")){
					if(vins[j].trim().length()==17){
						set.add(vins[j].trim());
					}else{
						//set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			//对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				buffer.append("'").append(s).append("'").append(",");				
			}
			//对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();			
				buffer2.append(alias).append(".VIN LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");			
			}
			
			if(set.size()==0&&set2.size()>0){
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql+=likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if(set.size()>0&&set2.size()==0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql=inbuffer.append(" AND "+alias).append(".VIN").append(" IN (").append(buffer).append(")\n").toString();
			}
			if(set.size()>0&&set2.size()>0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql=inbuffer.append(" AND ("+alias).append(".VIN").append(" IN (").append(buffer).append(") OR ").toString();
				returnSql+=likeBuffer.append(buffer2).append(") \n");
			}		
		}
		return returnSql;
	}
	/* 入参：
	 * 1.ORDER_NO:要查询的ORDER_NO
	 * 2.alias：TT_VS_ORDER表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like'
	 * update by luoyg 2015/07/31
	 * */
	public static String getOrderNOsAuto(String orderNo,String alias){
		String returnSql = "";
		if (null != orderNo && !"".equals(orderNo)) {
			orderNo = orderNo.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 VIN进行拆分
			String sP = "\\r\\n";
			if(orderNo.indexOf("\r\n")==-1 && orderNo.indexOf("\n")>0){
				sP = "\n";
			}
			String[] vins = orderNo.split(sP);
			//得到orderNo长度：strLength  非17位   ：LIKE查询；17位：IN查询
			//对orderNo进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if(!vins[j].equals("")){
					if(vins[j].trim().length()==19){
						set.add(vins[j].trim());
					}else{
						//set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			//对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				buffer.append("'").append(s).append("'").append(",");				
			}
			//对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();			
				buffer2.append(alias).append(".ORDER_NO LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");			
			}
			
			if(set.size()==0&&set2.size()>0){
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql+=likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if(set.size()>0&&set2.size()==0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql=inbuffer.append(" AND "+alias).append(".VIN").append(" IN (").append(buffer).append(")\n").toString();
			}
			if(set.size()>0&&set2.size()>0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql=inbuffer.append(" AND ("+alias).append(".VIN").append(" IN (").append(buffer).append(") OR ").toString();
				returnSql+=likeBuffer.append(buffer2).append(") \n");
			}		
		}
		return returnSql;
	}
	
	
	
	/* 入参：
	 * 1.SO_NO:要查询的SO_NO
	 * 2.alias：TT_VS_ORDER表的别名
	 * 如果VIN为17位则用‘=‘，如果不足17位，则用’like'
	 * update by wangxing 2015/09/07
	 * */
	public static String getSoNOsAuto(String soNo,String alias){
		String returnSql = "";
		if (null != soNo && !"".equals(soNo)) {
			soNo = soNo.trim();
			//IN查询
			StringBuffer inbuffer = new StringBuffer();
			StringBuffer likeBuffer = new StringBuffer();
			//对 VIN进行拆分
			String sP = "\\r\\n";
			if(soNo.indexOf("\r\n")==-1 && soNo.indexOf("\n")>0){
				sP = "\n";
			}
			String[] vins = soNo.split(sP);
			//得到orderNo长度：strLength  非17位   ：LIKE查询；17位：IN查询
			//对orderNo进行重复数据过滤
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			for (int j = 0; j < vins.length; j++) {
				if(!vins[j].equals("")){
					if(vins[j].trim().length()==10){
						set.add(vins[j].trim());
					}else{
						//set.add(vins[j].trim());
						set2.add(vins[j].trim());
					}
				}
			}
			//对VIN进行组合(in查询)
			StringBuffer buffer = new StringBuffer();
			Iterator<String> i = set.iterator();
			while (i.hasNext()) {
				String s = i.next().trim();			
				buffer.append("'").append(s).append("'").append(",");				
			}
			//对VIN进行组合(like查询)
			StringBuffer buffer2 = new StringBuffer();
			Iterator<String> j = set2.iterator();
			while (j.hasNext()) {
				String s = j.next().trim();			
				buffer2.append(alias).append(".SO_NO LIKE ").append("'").append("%").append(s).append("%").append("'").append(" OR ");			
			}
			
			if(set.size()==0&&set2.size()>0){
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql+=likeBuffer.append(" AND (").append(buffer2).append(")\n");
			}
			if(set.size()>0&&set2.size()==0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				returnSql=inbuffer.append(" AND "+alias).append(".SO_NO").append(" IN (").append(buffer).append(")\n").toString();
			}
			if(set.size()>0&&set2.size()>0){
				buffer=buffer.deleteCharAt(buffer.length()-1);
				buffer2=buffer2.delete(buffer2.length()-3, buffer2.length());
				returnSql=inbuffer.append(" AND ("+alias).append(".SO_NO").append(" IN (").append(buffer).append(") OR ").toString();
				returnSql+=likeBuffer.append(buffer2).append(") \n");
			}		
		}
		return returnSql;
	}
	
}
