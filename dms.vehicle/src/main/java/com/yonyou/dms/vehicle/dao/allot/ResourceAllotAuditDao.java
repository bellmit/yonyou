package com.yonyou.dms.vehicle.dao.allot;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
@SuppressWarnings("rawtypes")
@Repository
public class ResourceAllotAuditDao extends OemBaseDAO {
	
	/**
	 * 获取审核日期
	 * @param auditType
	 * @return
	 */
	public List<Map> getAllotDate2(String auditType) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		if(auditType.equals("0")){
			sql.append("select distinct tar.ALLOT_DATE,CONCAT(SUBSTRING(ALLOT_DATE,1,4),'-',SUBSTRING(ALLOT_DATE,5,2),'-',SUBSTRING(ALLOT_DATE,7,2)) ALLOTDATE\n");
			sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
			sql.append("		TM_DEALER                td,\n");
			sql.append("		TM_ORG                   tor,\n");
			sql.append("		TM_ORG                   tor2,\n");
			sql.append("        TM_DEALER_ORG_RELATION   tdor\n");	
			sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
			sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
			sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
			sql.append("     and tor.PARENT_ORG_ID = tor2.ORG_ID\n");
			sql.append("	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
			sql.append("	 and tar.AUTHOR_TYPE=10431002\n");
			sql.append("	 and tar.AUDIT_TYPE=12304\n");
			sql.append("	 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		}else{
			sql.append("select distinct tar.ALLOT_DATE,CONCAT(SUBSTRING(ALLOT_DATE,1,4),'-',SUBSTRING(ALLOT_DATE,5,2),'-',SUBSTRING(ALLOT_DATE,7,2)) ALLOTDATE\n");
			sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
			sql.append("		TM_DEALER                td,\n");
			sql.append("		TM_ORG                   tor,\n");
			sql.append("		TM_ORG                   tor2,\n");
			sql.append("        TM_DEALER_ORG_RELATION   tdor\n");	
			sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
			sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
			sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
			sql.append("     and tor.PARENT_ORG_ID = tor2.ORG_ID\n");
			sql.append("	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
			sql.append("	 and tar.AUTHOR_TYPE=10431003\n");
			sql.append("	 and tar.AUDIT_TYPE=12301\n");
			sql.append("	 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");			
		}
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;	
	}
	
	/**
	 * 初始化大区
	 * @param loginInfo
	 * @return
	 */
	public List<Map> getArea2() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct tor2.ORG_ID,tor2.ORG_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
		sql.append("		TM_DEALER                td,\n");
		sql.append("		TM_ORG                   tor,\n");
		sql.append("		TM_ORG                   tor2,\n");
		sql.append("        TM_DEALER_ORG_RELATION   tdor\n");	
		sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = tor2.ORG_ID\n");
		sql.append("	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取车系
	 * @param loginInfo
	 * @return
	 */
	public List<Map> getSeries(){
		StringBuffer sql=new StringBuffer();
        sql.append("SELECT group_id SERIES_ID, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group \n");
		sql.append("  WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("  and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取资源分配数据
	 * @param conditionWhere
	 * @param auditType
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findResourceAllotList(String conditionWhere, String auditType, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TT1.*, IFNULL(TT2.STANDARD_OPTION,'') STANDARD_OPTION,IFNULL(TT2.FACTORY_OPTIONS,'') FACTORY_OPTIONS,IFNULL(TT2.LOCAL_OPTION,'') LOCAL_OPTION,IFNULL(TT2.MODEL_YEAR,'') MODEL_YEAR \n");
		sql.append(" FROM (\n");
		sql.append("select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor2.ORG_ID ORG_ID2,tor2.ORG_NAME ORG_NAME2,tor.ORG_ID,tor.ORG_NAME,\n");
		sql.append("	   td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar,\n");
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_ORG                		   tor2,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("        TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2\n");	
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("	 and tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("		 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME\n");
		sql.append(") \n");   
		sql.append("tt1 left join  \n");
		sql.append("  ( select distinct vw.group_id, IFNULL(vw.STANDARD_OPTION,'') STANDARD_OPTION,IFNULL(vw.FACTORY_OPTIONS,'') FACTORY_OPTIONS,IFNULL(vw.LOCAL_OPTION,'') LOCAL_OPTION,IFNULL(vw.MODEL_YEAR,'') MODEL_YEAR\n");
		sql.append("   from ("+getVwMaterialSql()+") vw\n");
		sql.append("   )\n");
		sql.append(" tt2 on tt2.GROUP_ID=tt1.GROUP_ID \n");
		
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findHasNumListBySeries(String groupId, String allotDate, String parOrgId, String orgId,
			String auditType, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();	
		sql.append("select t.SERIES_ID,t.SERIES_CODE,t.SERIES_NAME,sum(ALLOT_NUM) ALLOT_NUM\n");
		sql.append(" from (select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar,\n");
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("	    TM_ORG                	   	   tor2,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("        TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2\n");	
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append(" 			and tvmg2.GROUP_ID in("+groupId+")");
		}
		if(!allotDate.equals("")){
			sql.append(" and tar.ALLOT_DATE='"+allotDate+"'");
		}
		//sql.append("	 and tvmg2.GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("	 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");
		sql.append(") t group by t.SERIES_ID,t.SERIES_CODE,t.SERIES_NAME\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findTotalGapListBySeries(String groupId, String parOrgId, String orgId, String allotDate,
			String allotMonthDate, String auditType, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.GROUP_ID SERIES_ID,t.SERIES_NAME,t.ORG_ID2,t.ORG_ID,t.ORG_NAME,t.DEALER_ID,t.DEALER_NAME,t.SALE_AMOUNT,t.NUM1,t.NUM2,t.NUM22,t.NUM3,t.NUM33,t.NUM4,t.NUM5,t.GAP,t.ALLOT_NUM,t.ALLOT_MONTH_NUM,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append("  from \n");
		sql.append("(select tt1.GROUP_ID,tt1.SERIES_NAME,TT1.ORG_ID2,tt1.ORG_ID,tt1.ORG_NAME,tt1.DEALER_ID,tt1.DEALER_NAME, tt1.SALE_AMOUNT,NUM1,IFNULL(tt1.NUM2,0) NUM2,IFNULL(tt1.NUM22,0) NUM22,IFNULL(tt1.NUM3,0) NUM3,IFNULL(tt1.NUM33,0) NUM33,IFNULL(tt1.NUM4,0) NUM4,IFNULL(tt1.NUM5,0) NUM5,tt1.GAP,IFNULL(tt1.ALLOT_NUM,0) ALLOT_NUM,tt2.ALLOT_MONTH_NUM\n");
		sql.append(" from (select t1.GROUP_ID,t1.SERIES_NAME,T1.ORG_ID2,t1.ORG_ID,t1.ORG_NAME,t1.DEALER_ID,t1.DEALER_NAME, t1.SALE_AMOUNT,IFNULL(t1.NUM1,0) NUM1,IFNULL(t1.NUM2,0) NUM2,IFNULL(t1.NUM22,0) NUM22,IFNULL(t1.NUM3,0) NUM3,IFNULL(t1.NUM33,0) NUM33,IFNULL(t1.NUM4,0) NUM4,IFNULL(t1.NUM5,0) NUM5,t1.GAP,IFNULL(t2.ALLOT_NUM,0) ALLOT_NUM \n");
		sql.append("   from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR2.ORG_ID ORG_ID2,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("	    	   TM_ORG                	tor2,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("	 		 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		sql.append(" 	  	     and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM)  ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	    		     TM_ORG                	   	    tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 			  and tor.PARENT_ORG_ID=tor2.ORG_ID");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append(" 	  		and tvmg2.GROUP_ID in("+groupId+") \n");
		}
		sql.append(" 	  	     and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append(" ) tt1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM)  ALLOT_MONTH_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	    		     TM_ORG                	   	    tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 			  and tor.PARENT_ORG_ID=tor2.ORG_ID");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append(" 	  		and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		sql.append(" 	  	     and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n ");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) tt2");
		sql.append(" 				 on tt1.GROUP_ID=tt2.GROUP_ID and tt1.DEALER_ID = tt2.ALLOT_TARGET_ID \n");
		sql.append("   union all\n");
		sql.append("select tt.GROUP_ID,tt.SERIES_NAME,tt.ORG_ID2,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(tt.SALE_AMOUNT) SALE_AMOUNT,sum(tt.NUM1) NUM1,\n");
		sql.append("	sum(tt.NUM2) NUM2,avg(tt.NUM22) NUM22,sum(tt.NUM3) NUM3,avg(tt.NUM33) NUM33,sum(tt.NUM4) NUM4,sum(tt.NUM5) NUM5,sum(tt.GAP) GAP,sum(tt.ALLOT_NUM) ALLOT_NUM,\n");
		sql.append("	IFNULL(sum(tt.ALLOT_MONTH_NUM),0) ALLOT_MONTH_NUM from \n");
		sql.append("	(select tt1.GROUP_ID,'BIG_ORG_TOTAL' as SERIES_NAME,tt1.ORG_ID2,tt1.ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(tt1.SALE_AMOUNT) SALE_AMOUNT,\n");
		sql.append("	sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(tt1.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("	,sum(tt2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM from \n");
		sql.append("	(select t1.dealer_id,t1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,t1.ORG_ID2,t1.ORG_ID,IFNULL(t1.SALE_AMOUNT,0) SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2,IFNULL(NUM22,0) NUM22,IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,IFNULL(NUM4,0) NUM4,IFNULL(NUM5,0) NUM5,IFNULL(GAP,0) GAP,IFNULL(t2.ALLOT_NUM,0) ALLOT_NUM\n");
		sql.append("		from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR2.ORG_ID ORG_ID2,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("			 from 	TM_TOTAL_GAP   			ttg,\n");
		sql.append("		 			TM_ORG         			tor,\n");
		sql.append("					TM_ORG                	tor2,\n");
		sql.append("					TM_DEALER                td,\n");
		sql.append("					TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("					TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("			where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("				and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("				and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("				and tvmg.GROUP_LEVEL=2\n");
		sql.append("				and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		sql.append("				 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 	  	     and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("			left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
	    sql.append("				from TM_ALLOT_RESOURCE_DCS              tar,\n");
	    sql.append("					 TM_DEALER                      td,\n");
	    sql.append("					 TM_ORG                		    tor,\n");
	    sql.append(" 						TM_ORG                	   		tor2,\n");
	    sql.append("					 TM_DEALER_ORG_RELATION   		tdor,\n");
	    sql.append(" 					TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
	    sql.append("					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
	    sql.append(" 					TM_VHCL_MATERIAL_GROUP         tvmg2\n");
	    sql.append("				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
	   sql.append("	      				and td.DEALER_ID=tdor.DEALER_ID\n");
	   sql.append("		 				 and tor.ORG_ID=tdor.ORG_ID\n");
	   sql.append("		  				and tar.GROUP_ID=tvmg4.GROUP_ID\n");
	   sql.append("		  				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
	   sql.append("		 				 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
	   sql.append("	 					  and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
	   sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("				and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		sql.append(" 	  	     and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
	   sql.append("					group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID) t2\n");
	   sql.append("				on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
	   sql.append("			)tt1 left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
	   sql.append("					from TM_ALLOT_RESOURCE_DCS              tar,\n");
	   sql.append(" 					TM_DEALER                      td,\n");
	   sql.append(" 					TM_ORG                		    tor,\n");
	   sql.append(" 					TM_ORG                	   		tor2,\n");
	   sql.append(" 					TM_DEALER_ORG_RELATION   		tdor,\n");
	   sql.append(" 					TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
	   sql.append(" 					TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("					TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("			where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("    			and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	  			and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("	  			and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	  			and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append(" 	  			and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("				and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}
		sql.append("    			and tar.ALLOT_DATE LIKE '%"+allotMonthDate+"%' 			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID=2014101414145567)\n");
		sql.append("			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID) tt2\n");
		sql.append("	on tt1.GROUP_ID=tt2.GROUP_ID and tt1.DEALER_ID = tt2.ALLOT_TARGET_ID \n");
		sql.append("		group by tt1.GROUP_ID,tt1.ORG_ID2,tt1.ORG_ID) tt\n");
	    sql.append("	group by tt.GROUP_ID,tt.SERIES_NAME,tt.ORG_ID2\n");
		sql.append("union all\n");
		sql.append("		SELECT tt1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,tt1.ORG_ID2,tt1.ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(tt1.SALE_AMOUNT) SALE_AMOUNT,sum(tt1.NUM1) NUM1,sum(tt1.NUM2) NUM2,avg(tt1.NUM22) NUM22,sum(tt1.NUM3) NUM3,avg(tt1.NUM33) NUM33,sum(tt1.NUM4) NUM4,sum(tt1.NUM5) NUM5,sum(tt1.GAP) GAP,\n");
		sql.append("			sum(tt1.ALLOT_NUM) ALLOT_NUM,sum(IFNULL(tt2.ALLOT_MONTH_NUM,0)) ALLOT_MONTH_NUM\n");
		sql.append("				FROM (select T1.DEALER_ID,t1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,T1.ORG_ID2,t1.ORG_ID,t1.SALE_AMOUNT,t1.NUM1,t1.NUM2,t1.NUM22,t1.NUM3,t1.NUM33,t1.NUM4,t1.NUM5,t1.GAP,IFNULL(t2.ALLOT_NUM,0) ALLOT_NUM \n");
		sql.append("  			from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR2.ORG_ID ORG_ID2,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	 			 from TM_TOTAL_GAP   			ttg,\n");
		sql.append("              			 TM_ORG         			tor,\n");
		sql.append("	          			 TM_ORG                	tor2,\n");
		sql.append("     		  			 TM_DEALER                td,\n");
		sql.append("			   			TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   			TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           			where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     			and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 			and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 			and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		    			 and tvmg.GROUP_LEVEL=2\n");
		sql.append("	 		 			and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		sql.append("				 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 	  	     and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	   				 TM_ORG                	   		tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 		 	  and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("				and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		sql.append(" 	  	     and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  group by t1.GROUP_ID,t1.ORG_ID\n");
		sql.append(" )tt1 \n");
		sql.append("  left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	   				 TM_ORG                	   		tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 		 	  and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("				and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		sql.append(" 	  	     and tar.ALLOT_DATE LIKE '%"+allotMonthDate+"%' ");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID) tt2\n");
		sql.append("  on tt1.GROUP_ID=tt2.GROUP_ID and tt1.DEALER_ID = tt2.ALLOT_TARGET_ID  \n");
		sql.append("  group by tt1.GROUP_ID,tT1.ORG_ID,tt1.ORG_ID2\n");
		sql.append("union all\n");
		sql.append("select tt.GROUP_ID,tt.SERIES_NAME,0 as ORG_ID2,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(tt.SALE_AMOUNT) SALE_AMOUNT,sum(tt.NUM1) NUM1,sum(tt.NUM2) NUM2,avg(tt.NUM22) NUM22,sum(tt.NUM3) NUM3,avg(tt.NUM33) NUM33,sum(tt.NUM4) NUM4,sum(tt.NUM5) NUM5,sum(tt.GAP) GAP,sum(tt.ALLOT_NUM) ALLOT_NUM,sum(tt1.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM\n");
		sql.append("	from (select t1.dealer_id,t1.GROUP_ID,'TOTAL' as SERIES_NAME,t1.SALE_AMOUNT,t1.NUM1,t1.NUM2,t1.NUM22,t1.NUM3,t1.NUM33,t1.NUM4,t1.NUM5,t1.GAP,t2.ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("	    	   TM_ORG                	tor2,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("	 		 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
	//	sql.append("             and tor.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		sql.append(" 	  	     and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  inner join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	    			 TM_ORG                	   	    tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 		 	  and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append(" 	  	     	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!parOrgId.equals("")){
			sql.append("			and tor2.org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		sql.append("				and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  group by t1.GROUP_ID,t1.ORG_ID\n");
		sql.append(") tt\n");
		sql.append("  left  join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
		sql.append("	    			 TM_ORG                	   	    tor2,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 		 	  and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("             	  and tor2.PARENT_ORG_ID="+loginInfo.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("				  and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 			and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 			and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 			and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append(" 	  	     	  and tar.ALLOT_DATE LIKE '%"+allotMonthDate+"%' ");
		if(!parOrgId.equals("")){
			sql.append("			and tor.parent_org_id="+parOrgId+"\n");
		}
		if(!orgId.equals("")){
			sql.append("			and TOR.ORG_ID="+orgId+"\n");
		}
		sql.append("				and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) tt1\n");
		sql.append(" ON tt.GROUP_ID=tt1.GROUP_ID and tt.DEALER_ID = tt1.ALLOT_TARGET_ID group by tt.GROUP_ID,tt.SERIES_NAME) t\n");
		sql.append(" order by t.ORG_NAME");
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findResourceAllotListBySeries(String conditionWhere, String auditType, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) SER_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("	    TM_ORG                	   tor2,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append( conditionWhere+"\n");
		}
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("	 and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("  	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");		
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findResourceAllotListByColor(String conditionWhere, String auditType, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,'BIG_ORG_TOTAL' SERIES_NAME,tvmg4.GROUP_ID,tor2.ORG_ID ORG_ID2,0 as ORG_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("		from 	TM_ALLOT_RESOURCE_DCS     	   tar,\n");
        sql.append("				TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
        sql.append("				TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("				TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("				TM_DEALER                  td,\n");
		sql.append("				TM_ORG                	   tor,\n");
		sql.append("				TM_ORG                	   tor2,\n");
		sql.append("				TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("	where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("	and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	"+conditionWhere+"\n");
	    }
		sql.append("	and tor2.PARENT_ORG_ID = "+loginInfo.getOrgId()+"\n");
		sql.append("	and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("	and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 	and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+"\n");
		sql.append("	and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		sql.append("	 group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor2.ORG_ID,tar.COLOR_NAME\n");
		sql.append("union all\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,'ORG_TOTAL' SERIES_NAME,tvmg4.GROUP_ID,tor2.ORG_ID ORG_ID2,tor.ORG_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("	    TM_ORG                	   tor2,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	"+conditionWhere+"\n");
	    }
		sql.append("  	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");		
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("		 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor2.ORG_ID,tor.ORG_ID,tar.COLOR_NAME\n");
		sql.append("union all\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,'TOTAL' SERIES_NAME,tvmg4.GROUP_ID, 0 as ORG_ID2, 0 as ORG_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_ORG                	   tor2,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");;
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("  	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");		
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("		 and tor2.ORG_ID in(select ORG_ID from TC_ORG_BIG where USER_ID="+loginInfo.getUserId()+")\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	

}
