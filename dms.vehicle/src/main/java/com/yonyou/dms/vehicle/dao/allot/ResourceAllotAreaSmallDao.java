package com.yonyou.dms.vehicle.dao.allot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmAllotResourcePO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
@SuppressWarnings("rawtypes")
@Repository
public class ResourceAllotAreaSmallDao extends OemBaseDAO{
	public static Logger logger = Logger.getLogger(ResourceAllotAreaSmallDao.class);

	public List<Map<String, Object>> findVinExists() {
		return null;
	}

	public List<Map<String, Object>> getResourceAllotList(String conditionWhere, Integer pageSize200, int i) {
		
		return null;
	}


	/*public List<Map<String, Object>> getSeries(LoginInfoDto loginUser) {
		String grand36="0";
		TmAllotGrandPO taPO = new TmAllotGrandPO();
		taPO.setGrandType(36);
		List<TmAllotGrandPO> taList = dao.select(taPO); 
		if(taList.size()>0){
			taPO = taList.get(0);
			grand36 = taPO.getGrandId().toString();
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE           tar,\n");
		sql.append("        VW_MATERIAL                 vm,\n");
		sql.append("		TM_DEALER                   td,\n");
		sql.append("		TM_ORG                      tor,\n");
		sql.append("        TM_DEALER_ORG_RELATION      tdor,\n");	
		sql.append("		TM_VHCL_MATERIAL            tvm,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg4,\n");
		sql.append(" 	    TM_VHCL_MATERIAL_GROUP_R    tvmgr,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg2\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append(" 	 and vm.MATERIAL_ID=tvm.MATERIAL_ID\n");
		sql.append("	 and tvmg4.GROUP_ID=tvmgr.GROUP_ID\n");
		sql.append("	 and tvm.MATERIAL_ID=tvmgr.MATERIAL_ID\n");
		sql.append("	 and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tvmg2.GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");		
		
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select distinct tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME\n");
		sql2.append("   from TM_ALLOT_RESOURCE           tar,\n");
		sql2.append("        VW_MATERIAL                 vm,\n");
		sql2.append("		TM_DEALER                   td,\n");
		sql2.append("		TM_ORG                      tor,\n");
		sql2.append("        TM_DEALER_ORG_RELATION      tdor,\n");	
		sql2.append("		TM_VHCL_MATERIAL            tvm,\n");
		sql2.append("		TM_VHCL_MATERIAL_GROUP      tvmg4,\n");
		sql2.append(" 	    TM_VHCL_MATERIAL_GROUP_R    tvmgr,\n");
		sql2.append("		TM_VHCL_MATERIAL_GROUP      tvmg3,\n");
		sql2.append("		TM_VHCL_MATERIAL_GROUP      tvmg2\n");
		sql2.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql2.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql2.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql2.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql2.append(" 	 and vm.MATERIAL_ID=tvm.MATERIAL_ID\n");
		sql2.append("	 and tvmg4.GROUP_ID=tvmgr.GROUP_ID\n");
		sql2.append("	 and tvm.MATERIAL_ID=tvmgr.MATERIAL_ID\n");
		sql2.append("	 and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql2.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql2.append("    and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql2.append("    and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql2.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");		
	
		List list = pageQuery(sql.toString(), null, getFunName());
		int grand36Flag = 0;
		for(int i=0;i<list.size();i++){
			Map<String,Object> map = (Map<String, Object>) list.get(i);
			if(map.get("SERIES_ID").toString().equals(grand36)){
				grand36Flag = 1;
			}
		}
		if(grand36Flag>0){
			return list;
		}else{
			list = pageQuery(sql2.toString(), null, getFunName());
		}
		return list;
	}
*/  
	public List<Map> getSeries(LoginInfoDto loginUser){
		StringBuffer sql=new StringBuffer();
        sql.append("SELECT group_id SERIES_ID, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group \n");
		sql.append("  WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("  and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	public int findVinsTotalNum(LoginInfoDto loginUser, TmAllotResourcePO PO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select nvl(sum(ALLOT_NUM),0) NUM\n");
		sql.append("    from TM_ALLOT_RESOURCE        tar,\n");
		sql.append("		 TM_DEALER                td,\n");
		sql.append("		 TM_ORG                   tor,\n");
		sql.append("         TM_DEALER_ORG_RELATION   tdor\n");	
		sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and GROUP_ID="+PO.getLong("GROUP_ID")+"\n");
		sql.append("	 and COLOR_NAME='"+PO.getString("COLOR_NAME")+"'\n");
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return new Integer(list.get(0).get("NUM").toString());
		}else{
			return 0;
		}		
	}

	public int findExistsNum(LoginInfoDto loginUser, TmAllotResourcePO PO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select nvl(sum(ADJUST_NUM),0) NUM\n");
		sql.append("    from TM_ALLOT_RESOURCE  	  tar,\n");
		sql.append("		 TM_DEALER                td,\n");
		sql.append("		 TM_ORG                   tor,\n");
		sql.append("         TM_DEALER_ORG_RELATION   tdor\n");	
		sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and GROUP_ID="+PO.getLong("GROUP_ID")+"\n");
		sql.append("	 and COLOR_NAME='"+PO.getString("COLOR_NAME")+"'\n");
		sql.append("     and TM_ALLOT_ID!="+PO.getLong("TM_ALLOT_ID")+"\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return Integer.parseInt(CommonUtils.checkNull(list.get(0).get("NUM")));
	}

	public void updateAllot(String allotDate,String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("update TM_ALLOT_RESOURCE set ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+",AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+",AUDIT_STATUS="+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+",AUTHOR_TYPE=10431003 where TM_ALLOT_ID in(\n");
		sql.append("  select tar.TM_ALLOT_ID\n");
		sql.append("     from TM_ALLOT_RESOURCE        tar,\n");
		sql.append(" 		  TM_ORG                   tor,\n");
		sql.append("		  TM_DEALER                td,\n");
		sql.append(" 		  TM_DEALER_ORG_RELATION   tdor\n");
		sql.append("	 where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append(" 	   and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	   and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("       and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	   and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("       and tor.ORG_ID="+orgId+")\n");
		
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		
	}

	/**
	 * @author huyu
	 * 查询小区下面经销商的邮箱
	 */
	public List<Map> findAllotAreaSmallByEmail(Long org,String allotDate){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct td.DEALER_NAME,td.EMAIL,tdor.ORG_ID,tar.ALLOT_DATE from  TM_ALLOT_RESOURCE tar,TM_DEALER td,TM_DEALER_ORG_RELATION tdor \n") ;
		sql.append("    where tar.ALLOT_TARGET_ID = td.DEALER_ID \n") ;
		sql.append("	and td.DEALER_ID = tdor.DEALER_ID\n") ; 
		sql.append("	and tar.ALLOT_DATE="+ allotDate +" and tdor.ORG_ID="+ org +" \n") ;
		sql.append("	and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_DEALER +" \n") ;
		sql.append("	and td.email <> '' and td.EMAIL <> 'chrysler-dms@yonyou.com';\n") ; 
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list ; 
	}
	
	public int resourceAllotCheck(String allotDate,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();		
		sql.append("select sum(ALLOT_NUM)-sum(ADJUST_NUM) NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE  		tar,\n");
		sql.append("       TM_ORG             		tor,\n");
		sql.append("       TM_DEALER                td,\n");
		sql.append("       TM_DEALER_ORG_RELATION   tdor\n");
		sql.append("   where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_DATE='"+allotDate+"'\n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return new Integer(list.get(0).get("NUM").toString());
		}else{
			return 0;
		}		
	}

	public List<Map> getAllotDate(LoginInfoDto loginUser,String allotStatus) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct tar.ALLOT_DATE\n");
		sql.append("   from TM_ALLOT_RESOURCE        tar,\n");
		sql.append("        VW_MATERIAL              vm,\n");
		sql.append("		TM_DEALER                td,\n");
		sql.append("		TM_ORG                   tor,\n");
		sql.append("        TM_DEALER_ORG_RELATION   tdor\n");	
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 	 and tar.ALLOT_STATUS="+allotStatus+"\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/*public List<Map<String, Object>> findTotalGapListBySeries(String groupId, String dealerCode, String allotDate,String allotType,LoginInfoDto loginUser) {
		String dcode = "";
		dcode=dealerCode.replace(",", "','");
		dcode="'"+dcode+"'";		
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append("  from (select t1.*,t2.ALLOT_NUM\n");
		sql.append("    from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("         	   TM_DEALER                td,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("            where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("			 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("			and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID=t2.DEALER_ID\n");
		sql.append("union all\n");
		sql.append("select t1.GROUP_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(t1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,sum(NUM22) NUM22,sum(NUM3) NUM3,sum(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(t2.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("         	   TM_DEALER                td,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("            where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("			 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				and ttg.ALLOT_DATE='"+allotDate+"'");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  inner join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID=t2.ALLOT_TARGET_ID\n");
		sql.append("  group by t1.GROUP_ID\n");
		sql.append(") t\n");
		sql.append(" order by t.DEALER_NAME");
		List<Map<String, Object>> list=factory.select(sql.toString(),null,new DAOCallback<Map<String, Object>>(){
			public Map<String, Object> wrapper(ResultSet rs, int arg1) {
				try {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("SERIES_ID", rs.getString("GROUP_ID"));
					map.put("SERIES_NAME", rs.getString("SERIES_NAME"));
					map.put("ORG_ID", rs.getLong("ORG_ID"));
					map.put("ORG_NAME", rs.getString("ORG_NAME"));
					map.put("DEALER_NAME", rs.getString("DEALER_NAME"));
					map.put("SALE_AMOUNT", rs.getString("SALE_AMOUNT"));
					map.put("NUM1", rs.getInt("NUM1"));
					map.put("NUM2", rs.getInt("NUM2"));
					map.put("NUM22", rs.getInt("NUM22"));
					map.put("NUM3", rs.getInt("NUM3"));
					map.put("NUM33", rs.getInt("NUM33"));
					map.put("NUM4", rs.getInt("NUM4"));
					map.put("NUM5", rs.getInt("NUM5"));
					map.put("GAP", rs.getInt("GAP"));
					map.put("ALLOT_NUM", rs.getInt("ALLOT_NUM"));
					map.put("RATE", rs.getFloat("RATE"));
					map.put("DRATE", rs.getFloat("DRATE"));
					return map;
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}});
		return list;
	}*/
	public List<Map> findTotalGapListBySeries(String groupId, String dealerCode, String allotDate,String allotMonthDate,String allotType,LoginInfoDto loginUser) {
		String dcode = "";
		dcode=dealerCode.replace(",", "','");
		dcode="'"+dcode+"'";		
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append(" from (  select tt1.*,tt2.ALLOT_MONTH_NUM");
		sql.append("  from (select t1.*,t2.ALLOT_NUM\n");
		sql.append("    from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,nvl(ttg.GAP,0) gap\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("         	   TM_DEALER                td,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("            where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("			 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("			and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("		  		and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("		  		  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID=t2.DEALER_ID\n");
		sql.append(" )tt1");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("				  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
		sql.append("		  		  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,td.DEALER_ID,td.DEALER_SHORTNAME) tt2\n");
		sql.append("  on tt1.GROUP_ID=tt2.GROUP_ID and tt1.DEALER_ID=tt2.DEALER_ID\n");
		sql.append("union all\n");
		sql.append(" select tt1.GROUP_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,sum(nvl(tT1.SALE_AMOUNT,0)) SALE_AMOUNT,sum(nvl(NUM1,0)) NUM1,sum(nvl(NUM2,0)) NUM2,avg(nvl(NUM22,0)) NUM22,sum(nvl(NUM3,0)) NUM3,avg(nvl(NUM33,0)) NUM33,sum(nvl(NUM4,0)) NUM4,sum(nvl(NUM5,0)) NUM5,sum(nvl(GAP,0)) GAP,sum(nvl(tT1.ALLOT_NUM,0)) ALLOT_NUM\n");
		sql.append(",sum(tt2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM  from\n");
		sql.append("(select t1.DEALER_ID,t1.GROUP_ID,'TOTAL' as SERIES_NAME,t1.SALE_AMOUNT,NUM1,NUM2,NUM22,NUM3,NUM33,NUM4,NUM5,GAP,t2.ALLOT_NUM FROM\n");
		sql.append("  (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("         	   TM_DEALER                td,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("            where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("			 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("			 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'");
		sql.append("		  	 and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("		 		  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID=t2.ALLOT_TARGET_ID\n");
		//sql.append("  group by t1.GROUP_ID\n");
		sql.append("  ) tt1 left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("         	   		 TM_DEALER                		td,\n");
		sql.append("               		 TM_ORG         				tor,\n");
		sql.append("			   		 TM_DEALER_ORG_RELATION   		tdor\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			 	  and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("			 	  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("             	  and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("				and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(!dealerCode.equals("")){
			sql.append("				and td.DEALER_CODE in ("+dcode+")\n");
		}
		sql.append("				  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("		  		  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) tt2\n");
		sql.append("  on tt1.GROUP_ID=tt2.GROUP_ID and tt1.DEALER_ID=tt2.ALLOT_TARGET_ID\n");
		sql.append("  group by tt1.GROUP_ID\n");
		sql.append(") t\n");
		sql.append(" order by t.DEALER_NAME");
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/*public List<Map<String, Object>> findResourceAllotList(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as (select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME,td.DEALER_SHORTNAME,tar.COLOR_NAME),");
		sql.append("tab2 as (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME)\n");
		sql.append(" select tab1.* from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("    and exists(select tvmg2.GROUP_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) \n");
		sql.append("				  from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("					   TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("					   TM_ORG                	   tor,\n");
		sql.append("					   TM_DEALER                  td,\n");
		sql.append("					   TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    			  where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     				and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 				and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 				and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 				and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 				"+conditionWhere+"\n");
		}
		sql.append("     				and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     				and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("					and tab1.SERIES_ID=tvmg2.GROUP_ID\n");
		sql.append("					and tvmg2.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("     			group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME having sum(tar.ADJUST_NUM)>0)\n");
		sql.append("   left join TM_DEALER_WHS tdw on tdw.DEALER_ID=tab1.DEALER_ID and tdw.TYPE=1 order by tdw.ORDER_NUM\n");
		
		List list = dao.pageQuery(sql.toString(), null, getFunName());	
		
		sql.delete(0, sql.toString().length());
		sql.append("with tab1 as (select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME,td.DEALER_SHORTNAME,tar.COLOR_NAME),");
		sql.append("tab2 as (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME)\n");
		sql.append(" select tab1.* from tab1,tab2\n");
		sql.append("  where tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("    and exists(select tvmg2.GROUP_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) \n");
		sql.append("				  from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("					   TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("					   TM_ORG                	   tor,\n");
		sql.append("					   TM_DEALER                  td,\n");
		sql.append("					   TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    			  where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     				and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 				and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 				and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 				and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 				"+conditionWhere+"\n");
		}
		sql.append("     				and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     				and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("					and tab1.SERIES_ID=tvmg2.GROUP_ID\n");
		sql.append("					and tvmg2.GROUP_ID in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("     			  group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME)\n");
		
		List list2 = dao.pageQuery(sql.toString(), null, getFunName());	
		for(int i=0;i<list2.size();i++){
			list.add(list2.get(i));
		}
		return list;
	}*/
	public List<Map> findResourceAllotList(String conditionWhere,LoginInfoDto loginUser,String allotDate,String groupId,String dealerCode) {
		if(dealerCode!=""){
			dealerCode=dealerCode.replaceAll(",", "','");
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as ( \n");   
		sql.append("SELECT '' STANDARD_OPTION,'' FACTORY_OPTIONS,'' local_option,'' MODEL_YEAR,NVL(TT3.TM_ALLOT_ID,0)TM_ALLOT_ID,TT1.ORG_ID ALLOT_TARGET_ID,TT2.SERIES_ID,TT2.SERIES_NAME,NVL(TT3.GROUP_ID,0) GROUP_ID,nvl(TT3.GROUP_NAME,'0') group_name,TT1.ORG_NAME,TT1.DEALER_ID,TT1.DEALER_SHORTNAME DEALER_NAME,NVL(TT3.COLOR_NAME,'0') COLOR_NAME,NVL(TT3.NUM,0) NUM,TT1.STATUS,0 as IS_FROZEN \n");
		sql.append(" 	  FROM  \n");
		sql.append(" 			(SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TD.DEALER_SHORTNAME,TD.DEALER_ID,TOR.ORG_NAME,TOR.ORG_ID FROM \n");
		sql.append(" 					TM_TOTAL_GAP TTG,TM_ORG                		   TOR,TM_DEALER                      TD,\n");
		sql.append(" 					TM_DEALER_ORG_RELATION         TDOR,TM_VHCL_MATERIAL_GROUP TVMG WHERE TD.DEALER_ID=TTG.TARGET_ID AND TTG.GROUP_ID=TVMG.GROUP_ID AND TTG.ALLOT_DATE='"+allotDate+"' AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 					 AND TTG.STATUS=0 	AND TD.DEALER_ID=TTG.TARGET_ID  AND TD.DEALER_ID=TDOR.DEALER_ID\n");
		sql.append("  and tor.org_id="+loginUser.getOrgId()+"\n");
		sql.append(" 				AND TOR.ORG_ID=TDOR.ORG_ID and ttg.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		if(groupId!=""){
			sql.append("                and ttg.group_id in ("+groupId+")\n");
		}
		if(dealerCode!=""){		
			sql.append(                 "and TD.DEALER_CODE in ('"+dealerCode+"')  \n");
		}
		sql.append(" 			)TT1 LEFT JOIN\n");
		sql.append(" 			(SELECT GROUP_ID SERIES_ID,GROUP_CODE,GROUP_NAME SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001\n");
		sql.append(" 				AND GROUP_LEVEL=2 and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT+")\n");
		sql.append(" 			TT2 ON TT1.SERIES_ID=TT2.SERIES_ID LEFT JOIN\n");
		sql.append(" 			(SELECT TAR.TM_ALLOT_ID,TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID SERIES_ID,(CASE WHEN TVMG2.GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TVMG2.GROUP_NAME END) SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TOR.ORG_NAME,TD.DEALER_ID,TD.DEALER_SHORTNAME DEALER_NAME,TAR.COLOR_NAME,TAR.ADJUST_NUM NUM\n");
		sql.append(" 					FROM TM_ALLOT_RESOURCE     		   TAR,TM_VHCL_MATERIAL_GROUP         TVMG4,\n");
		sql.append(" 							TM_VHCL_MATERIAL_GROUP         TVMG3,TM_VHCL_MATERIAL_GROUP         TVMG2,\n");
		sql.append(" 							TM_ORG                		   TOR,TM_DEALER                      TD,TM_DEALER_ORG_RELATION         TDOR\n");
		sql.append(" 					WHERE TAR.GROUP_ID = TVMG4.GROUP_ID AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID\n");
		sql.append(" 							AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID AND TAR.ALLOT_TARGET_ID = TD.DEALER_ID\n");
		sql.append(" 							AND TD.DEALER_ID = TDOR.DEALER_ID  AND TDOR.ORG_ID = TOR.ORG_ID\n");
		sql.append(" 							  AND TAR.ALLOT_STATUS='0'   \n");
		 if(!conditionWhere.equals("")){
				sql.append("	 "+conditionWhere+"\n");
			}
		sql.append(" 							 AND TOR.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append(" 							AND TAR.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("		  					and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 				ORDER BY TVMG2.GROUP_NAME,TVMG4.GROUP_NAME,TD.DEALER_SHORTNAME,TAR.COLOR_NAME) TT3 ON TT2.SERIES_ID=TT3.SERIES_ID\n");
		sql.append(" union all\n");
		sql.append("select distinct nvl(tt2.STANDARD_OPTION,'') STANDARD_OPTION,nvl(tt2.FACTORY_OPTIONS,'') FACTORY_OPTIONS,nvl(tt2.local_option,'') local_option,nvl(tt2.MODEL_YEAR,'') MODEL_YEAR,t.*\n");
		sql.append("from (select tt.* from (\n");
		sql.append("select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM,1 as status,tar.IS_FROZEN\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tvmg2.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("  order by tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tar.COLOR_NAME) tt\n");
		//
	/*	sql.append("   where exists(select 1 \n");
		sql.append("				  from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("					   TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("					   TM_ORG                	   tor,\n");
		sql.append("					   TM_DEALER                  td,\n");
		sql.append("					   TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    			  where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     				and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 				and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 				and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("					and tar.COLOR_NAME=tt.COLOR_NAME\n");
		sql.append("	 				and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("				 	and tar.GROUP_ID=tt.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 				"+conditionWhere+"\n");
		}
		sql.append("     				and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     				and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     			group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tar.COLOR_NAME\n");
		sql.append("				having sum(tar.ADJUST_NUM)>0)\n");*/
		//
		sql.append(") t");
		sql.append(" LEFT JOIN \n");
		sql.append(" 	(SELECT VW.GROUP_ID,VW.STANDARD_OPTION,VW.FACTORY_OPTIONS,VW.LOCAL_OPTION,VW.MODEL_YEAR FROM VW_MATERIAL VW where VW.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+") TT2\n");
		sql.append("    ON T.GROUP_ID=TT2.GROUP_ID\n");

		sql.append(" ),\n");
		
		sql.append("tab2 as (\n");
		sql.append("select tt1.series_id,tt1.series_name,nvl(tt2.group_id,0) group_id,nvl(tt2.group_name,'0') group_name,nvl(tt2.color_name,'0') color_name,nvl(tt2.color_num,0) color_num \n");
		sql.append(" 	 from\n");
		sql.append(" 		(SELECT group_id SERIES_ID, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group\n");
		sql.append(" 		 WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND) and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT+" \n");
		if(groupId!=""){
			sql.append("         	and group_id in ("+groupId+")\n");
		}
		sql.append("         )\n");
		sql.append(" 		tt1  left join (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME)\n");
		sql.append("   TT2 ON TT1.SERIES_ID=TT2.SERIES_ID \n");	
		sql.append(" ) \n");
		sql.append(" select tab1.* from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");		
		sql.append("   left join TM_DEALER_WHS tdw on tdw.DEALER_ID=tab1.DEALER_ID and tdw.TYPE=1\n");
		sql.append("  order by tdw.ORDER_NUM,tab1.SERIES_ID,tab1.GROUP_ID,tab1.COLOR_NAME with ur\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
		sql.delete(0, sql.toString().length());
		/*sql.append("with tab1 as (select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME,td.DEALER_SHORTNAME,tar.COLOR_NAME),");
		*/
		sql.append("with tab1 as ( \n");   
		sql.append("SELECT '' STANDARD_OPTION,'' FACTORY_OPTIONS,'' local_option,'' MODEL_YEAR,NVL(TT3.TM_ALLOT_ID,0)TM_ALLOT_ID,TT1.ORG_ID ALLOT_TARGET_ID,TT2.SERIES_ID,TT2.SERIES_NAME,NVL(TT3.GROUP_ID,0) GROUP_ID,nvl(TT3.GROUP_NAME,'0') group_name,TT1.ORG_NAME,TT1.DEALER_ID,TT1.DEALER_SHORTNAME DEALER_NAME,NVL(TT3.COLOR_NAME,'0') COLOR_NAME,NVL(TT3.NUM,0) NUM,TT1.STATUS,0 as IS_FROZEN \n");
		sql.append(" 	  FROM  \n");
		sql.append(" 			(SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TD.DEALER_SHORTNAME,TD.DEALER_ID,TOR.ORG_NAME,TOR.ORG_ID FROM \n");
		sql.append(" 					TM_TOTAL_GAP TTG,TM_ORG                		   TOR,TM_DEALER                      TD,\n");
		sql.append(" 					TM_DEALER_ORG_RELATION         TDOR,TM_VHCL_MATERIAL_GROUP TVMG WHERE TD.DEALER_ID=TTG.TARGET_ID AND TVMG.GROUP_ID=TTG.GROUP_ID AND TTG.ALLOT_DATE='"+allotDate+"' AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" AND TVMG.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 					 AND TTG.STATUS=0 	AND TD.DEALER_ID=TTG.TARGET_ID  AND TD.DEALER_ID=TDOR.DEALER_ID\n");
		sql.append(" 				AND TOR.ORG_ID=TDOR.ORG_ID and ttg.GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("                and tor.org_id="+loginUser.getOrgId()+"\n");
		if(groupId!=""){
			sql.append("                and ttg.group_id in ("+groupId+")\n");
		}
		if(dealerCode!=""){
			sql.append(                 "and TD.DEALER_CODE in ('"+dealerCode+"')  \n");
		}
		sql.append(" 			)TT1 LEFT JOIN\n");
		sql.append(" 			(SELECT GROUP_ID SERIES_ID,GROUP_CODE, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001\n");
		sql.append(" 				AND GROUP_LEVEL=2 AND GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+")\n");
		sql.append(" 			TT2 ON TT1.SERIES_ID=TT2.SERIES_ID LEFT JOIN\n");
		sql.append(" 			(SELECT TAR.TM_ALLOT_ID,TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID SERIES_ID,(CASE WHEN TVMG2.GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TVMG2.GROUP_NAME END) SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TOR.ORG_NAME,TD.DEALER_ID,TD.DEALER_SHORTNAME DEALER_NAME,TAR.COLOR_NAME,TAR.ADJUST_NUM NUM\n");
		sql.append(" 					FROM TM_ALLOT_RESOURCE     		   TAR,TM_VHCL_MATERIAL_GROUP         TVMG4,\n");
		sql.append(" 							TM_VHCL_MATERIAL_GROUP         TVMG3,TM_VHCL_MATERIAL_GROUP         TVMG2,\n");
		sql.append(" 							TM_ORG                		   TOR,TM_DEALER                      TD,TM_DEALER_ORG_RELATION         TDOR\n");
		sql.append(" 					WHERE TAR.GROUP_ID = TVMG4.GROUP_ID AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID\n");
		sql.append(" 							AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID AND TAR.ALLOT_TARGET_ID = TD.DEALER_ID\n");
		sql.append(" 							AND TD.DEALER_ID = TDOR.DEALER_ID  AND TDOR.ORG_ID = TOR.ORG_ID\n");
		sql.append(" 							  AND TAR.ALLOT_STATUS='0'   \n");
		 if(!conditionWhere.equals("")){
				sql.append("	 "+conditionWhere+"\n");
			}
		sql.append(" 							 AND TOR.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append(" 							AND TAR.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 						and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 				ORDER BY TVMG2.GROUP_NAME,TVMG4.GROUP_NAME,TD.DEALER_SHORTNAME,TAR.COLOR_NAME) TT3 ON TT2.SERIES_ID=TT3.SERIES_ID\n");
		sql.append(" union all\n");
		sql.append("select distinct nvl(vw.STANDARD_OPTION,'') STANDARD_OPTION,nvl(vw.FACTORY_OPTIONS,'') FACTORY_OPTIONS,nvl(vw.local_option,'') local_option,nvl(vw.MODEL_YEAR,'') MODEL_YEAR, tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM,1 as status,tar.IS_FROZEN\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		vw_material       vw\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID and vw.group_Id=tvmg4.group_Id\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tvmg2.GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND)\n");
	//	sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME,td.DEALER_SHORTNAME,tar.COLOR_NAME");

		sql.append(" ),\n");
		sql.append("tab2 as (\n");
		sql.append("select tt1.series_id,tt1.series_name,nvl(tt2.group_id,0) group_id,nvl(tt2.group_name,'0') group_name,nvl(tt2.color_name,'0') color_name,nvl(tt2.color_num,0) color_num \n");
		sql.append(" 	 from\n");
		sql.append(" 		(SELECT group_id SERIES_ID, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group\n");
		sql.append(" 		 WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND) and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT+")\n");
		sql.append(" 		tt1  left join (\n");
		
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		//sql.append("	having sum(tar.ADJUST_NUM)>0\n");
		sql.append(") TT2 ON TT1.SERIES_ID=TT2.SERIES_ID )\n");
		sql.append(" select tab1.* from tab1,tab2\n");
		sql.append("  where tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME with ur\n");
		/*sql.append("    and exists(select 1 \n");
		sql.append("				  from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        			   TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("					   TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("					   TM_ORG                	   tor,\n");
		sql.append("					   TM_DEALER                  td,\n");
		sql.append("					   TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    			  where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     				and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 				and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 				and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 				and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("				 	and tar.GROUP_ID=tab1.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 				"+conditionWhere+"\n");
		}
		sql.append("     				and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     				and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");	
		sql.append("					and tvmg2.GROUP_ID in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("     			  group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME having sum(tar.ADJUST_NUM)>0)\n");*/
		List<Map> list2 = OemDAOUtil.findAll(sql.toString(), null);	
		for(int i=0;i<list2.size();i++){
			list.add(list2.get(i));
		}
		return list;
	}
	public List<Map> findResourceIsNotAllotList(String conditionWhere,LoginInfoDto loginUser,String allotDate,String groupId,String dealerCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as ( \n");   
		sql.append("select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM,1 as status\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tvmg2.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");

		sql.append(" ),\n");
		
		sql.append("tab2 as (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		sql.append(" ) \n");
		sql.append(" select tab1.series_name,tab1.series_id,sum(tab1.num) num from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");		
		sql.append("  group by tab1.series_name,tab1.series_id having sum(num)=0 with ur\n");
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);	
		
		sql.delete(0, sql.toString().length());
	
		sql.append("with tab1 as ( \n");   
		sql.append("select distinct nvl(vw.STANDARD_OPTION,'') STANDARD_OPTION,nvl(vw.FACTORY_OPTIONS,'') FACTORY_OPTIONS,nvl(vw.local_option,'') local_option,nvl(vw.MODEL_YEAR,'') MODEL_YEAR, tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM,1 as status\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");	
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		vw_material       vw\n");
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID and vw.group_Id=tvmg4.group_Id\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tvmg2.GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append(" ),\n");
		sql.append("tab2 as (\n");

		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		//sql.append("	having sum(tar.ADJUST_NUM)>0\n");
		sql.append(" ) \n");
		sql.append(" select tab1.series_name,tab1.series_id,sum(tab1.num) num from tab1,tab2\n");
		sql.append("  where tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("       group by tab1.series_name,tab1.series_id having sum(num)=0\n");
		List<Map> list2=OemDAOUtil.findAll(sql.toString(), null);	
		if(list2.size()>0){
			for(int i=0;i<list2.size();i++){
				list.add(list2.get(i));
			}
		}	
		return list;
	}
	public List<Map> findResourceAllotListBySeries(String conditionWhere, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) SER_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);	
		return list;
	}
	public List<Map> findResourceAllotListByColor(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);	
		return list;
	}

	public List checkColorNum(String allotDate,String seriesId, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID,sum(tar.ALLOT_NUM) ALLOT_NUM,sum(tar.ADJUST_NUM) ADJUST_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");		
		sql.append("		TM_DEALER                      td,\n");
		sql.append("		TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_ORG                	       tor,\n");
		sql.append("		TM_ORG                		   tor2,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2\n");	
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tdor.ORG_ID = tor.ORG_ID\n");
		sql.append("	 and tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     and tvmg2.GROUP_ID="+seriesId+"\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("   group by tvmg2.GROUP_ID\n");
		sql.append("   having sum(tar.ALLOT_NUM)<>sum(tar.ADJUST_NUM)\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);	
		return list;
	}

	/*public List<Map<String, Object>> findHasNumListBySeries(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   TM_ORG                         tor,\n");
		sql.append("	   TM_DEALER                      td,\n");
		sql.append("	   TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	and tvmg2.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME  having sum(ADJUST_NUM)>0\n");
		sql.append("union\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   TM_ORG                         tor,\n");
		sql.append("	   TM_DEALER                      td,\n");
		sql.append("	   TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	and tvmg2.GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");

		List<Map<String, Object>> list=factory.select(sql.toString(),null,new DAOCallback<Map<String, Object>>(){
			public Map<String, Object> wrapper(ResultSet rs, int arg1) {
				try {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("SERIES_ID", rs.getString("SERIES_ID"));	
					map.put("SERIES_CODE", rs.getString("SERIES_CODE"));	
					map.put("SERIES_NAME", rs.getString("SERIES_NAME"));
					map.put("ALLOT_NUM", rs.getInt("ALLOT_NUM"));
					return map;
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}});
		return list;
	}*/
	public List<Map> findHasNumListBySeries(String conditionWhere,LoginInfoDto loginUser,String allotDate,String groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tt1.*,nvl(tt2.ALLOT_NUM,0) ALLOT_NUM,nvl(tt3.status,0) status from  \n");
		sql.append("	(SELECT group_id series_id,group_code series_code, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group \n");
		sql.append(" 		WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND) and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(groupId!=""){
			sql.append("  and group_id in ("+groupId+")\n");
		}
		sql.append("	)tt1 left join \n");
		sql.append("( \n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   TM_ORG                         tor,\n");
		sql.append("	   TM_DEALER                      td,\n");
		sql.append("	   TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	and tvmg2.GROUP_ID not in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("	and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME  having sum(ADJUST_NUM)>0\n");
		sql.append(" ) tt2  on tt1.series_id=tt2.series_id \n");
		sql.append("left join    \n");
		sql.append("   (SELECT distinct TTG.STATUS,TTG.GROUP_ID SERIES_ID FROM\n");
		sql.append("   		TM_TOTAL_GAP TTG where \n");
		sql.append("   			TTG.ALLOT_DATE='"+allotDate+"' AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("   ) tt3 on tt1.series_id=tt3.series_id\n");		
		sql.append("union\n");
		sql.append("select tt1.*,nvl(tt2.status,0) status from  (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   TM_ORG                         tor,\n");
		sql.append("	   TM_DEALER                      td,\n");
		sql.append("	   TM_DEALER_ORG_RELATION         tdor\n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	and tdor.ORG_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	and tvmg2.GROUP_ID  in (select GRAND_ID from TM_ALLOT_GRAND)\n");
		sql.append("	and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");
		sql.append(")\n");
		sql.append("tt1   left join \n");
	  	sql.append("		(SELECT distinct TTG.STATUS,TTG.GROUP_ID SERIES_ID FROM \n");
	  	sql.append(" 			TM_TOTAL_GAP TTG where \n");
	  	sql.append("   			  TTG.ALLOT_DATE='"+allotDate+"' AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
	  	sql.append("		)	  tt2 on tt1.series_id=tt2.series_id \n");

		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);	
		return list;
	}
}
