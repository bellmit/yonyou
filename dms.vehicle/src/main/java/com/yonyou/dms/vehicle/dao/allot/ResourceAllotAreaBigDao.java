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
public class ResourceAllotAreaBigDao extends OemBaseDAO{
	public static Logger logger = Logger.getLogger(ResourceAllotAreaBigDao.class);


	
	public List<Map> getResourceAllotList(String conditionWhere, Integer pageSize200, int i,LoginInfoDto loginUser) {
		
		return null;
	}

	public List<Map> getSeries(String seriesCode,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct vm.SERIES_CODE,vm.SERIES_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     tar,\n");
		sql.append("        VW_MATERIAL           vm,\n");
		sql.append("        TM_ORG                tor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		if(!seriesCode.equals("")){
			sql.append("     and vm.SERIES_CODE='"+seriesCode+"'\n");
		}		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> getGroupsBySeries(String seriesCode,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct vm.SERIES_CODE,vm.SERIES_NAME,VM.GROUP_ID,VM.GROUP_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     tar,\n");
		sql.append("        VW_MATERIAL           vm,\n");
		sql.append("        TM_ORG                tor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		if(!seriesCode.equals("")){
			sql.append("     and vm.SERIES_CODE='"+seriesCode+"'\n");
		}
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List findColorList(String seriesCode, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct vm.SERIES_NAME,VM.GROUP_ID,VM.GROUP_NAME,vm.COLOR_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     tar,\n");
		sql.append("        VW_MATERIAL           vm,\n");
		sql.append("        TM_ORG                tor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("     and tar.COLOR_NAME = vm.COLOR_NAME\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		if(!seriesCode.equals("")){
			sql.append("     and vm.SERIES_CODE='"+seriesCode+"'\n");
		}
		sql.append("   order by vm.SERIES_NAME,VM.GROUP_NAME,vm.COLOR_NAME\n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List isOrNotAllot(LoginInfoDto loginUser,String allotDate) {
		StringBuffer sql = new StringBuffer();  
		sql.append("select * from tm_total_gap where allot_date='"+allotDate+"' and create_By="+loginUser.getOrgId());
		List list = OemDAOUtil.findAll(sql.toString(), null);;	
	return list;
	}
	
    public List<Map> checkSmallAreaSeriesNum(String allotDate,LoginInfoDto loginUser){
    	StringBuffer sql = new StringBuffer();
    	sql.append("SELECT TT.ORG_ID,SUM(TT.NUM) NUM FROM ( \n");
    	sql.append(" 	SELECT TOR.ORG_NAME,TOR.ORG_ID, TAR.TM_ALLOT_ID,TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID SERIES_ID,\n");
    	sql.append(" 		(CASE WHEN TVMG2.GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TVMG2.GROUP_NAME END) SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,NVL(TAR.COLOR_NAME,'0') COLOR_NAME,TAR.ADJUST_NUM NUM,1 AS STATUS\n");
    	sql.append(" 		FROM TM_ALLOT_RESOURCE_DCS     		   TAR,\n");
    	sql.append(" 			 TM_ORG                		   TOR,\n");
    	sql.append(" 			 TM_VHCL_MATERIAL_GROUP         TVMG4,\n");
    	sql.append(" 			 TM_VHCL_MATERIAL_GROUP         TVMG3,\n");
    	sql.append("			 TM_VHCL_MATERIAL_GROUP         TVMG2");
    	sql.append("               where tar.GROUP_ID = tvmg4.GROUP_ID \n");
    	sql.append("          AND TAR.ALLOT_TARGET_ID = TOR.ORG_ID   \n");
    	sql.append("          AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID   \n");
    	sql.append("          AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID  \n");
    	sql.append("            AND TAR.ALLOT_DATE='"+allotDate+"' \n");
    	sql.append("             AND TAR.ALLOT_STATUS='1'  \n");
    	sql.append("             AND TOR.PARENT_ORG_ID ="+loginUser.getOrgId()+" \n");
    	sql.append("             AND TAR.ALLOT_TYPE= 10431004\n");
    	sql.append("             AND TAR.AUTHOR_TYPE=10431004\n");
    	sql.append("              ORDER BY TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID,TAR.COLOR_NAME\n");
    	sql.append("           )  TT GROUP BY TT.ORG_ID HAVING SUM(TT.NUM)=0    \n");
    	List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
    }

	public List<Map> getSeries(LoginInfoDto loginUser){
		StringBuffer sql=new StringBuffer();
        sql.append("SELECT group_id SERIES_ID, (case when GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group \n");
		sql.append("  WHERE 1=1  AND Status=10011001  AND Group_Level=2 and GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("  and group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	public List<Map> getOrgName(LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  tor.org_id,tor.org_name\n");
		sql.append("	from	TM_ORG               tor\n");
		sql.append("    left join TM_DEALER_WHS tdw on tor.ORG_ID=tdw.DEALER_ID and tdw.TYPE=3\n");
		sql.append("     where tor.parent_org_id= "+loginUser.getOrgId()+" order by tdw.ORDER_NUM\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	public List<Map> getOrgName2(int allotFlag,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  tor.org_id,tor.org_name\n");
		sql.append("	from	TM_ORG               tor\n");
		sql.append("    left join TM_DEALER_WHS tdw on tor.ORG_ID=tdw.DEALER_ID and tdw.TYPE=3\n");
		sql.append("     where tor.parent_org_id= "+loginUser.getOrgId()+" order by tdw.ORDER_NUM\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	public List<Map> findResourceByIds(LoginInfoDto loginUser, String orgId, String groupId,Integer pageSize, int curPage) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct  tar.COLOR_NAME,tar.ADJUST_NUM NUM,tor.ORG_NAME,vm.SERIES_NAME,vm.GROUP_NAME,tar.TM_ALLOT_ID\n");
		sql.append(" from TM_ALLOT_RESOURCE_DCS tar\n");
		sql.append("  left join TM_ORG tor on tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("  left join  VW_MATERIAL vm on vm.GROUP_ID=tar.GROUP_ID\n");
		sql.append("  where tar.ALLOT_TARGET_ID="+orgId+"\n");
		sql.append("    and tar.GROUP_ID="+groupId+" \n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		
		List<Map> ps = OemDAOUtil.findAll(sql.toString(), null);
		return ps;
	}

	public int findVinsTotalNum(LoginInfoDto loginUser,TmAllotResourcePO PO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select nvl(sum(ALLOT_NUM),0) NUM\n");
		sql.append("    from TM_ALLOT_RESOURCE_DCS  tar,\n");
		sql.append("         TM_ORG             tor\n");
		sql.append("   where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and GROUP_ID="+PO.getLong("GROUP_ID")+"\n");
		sql.append("	 and COLOR_NAME='"+PO.getString("COLOR_NAME")+"'\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return new Integer(list.get(0).get("NUM").toString());
		}else{
			return 0;
		}		
	}

	public int findExistsNum(LoginInfoDto loginUser,TmAllotResourcePO PO) {
		StringBuffer sql = new StringBuffer();
		sql.append("select nvl(sum(ADJUST_NUM),0) NUM\n");
		sql.append("    from TM_ALLOT_RESOURCE_DCS  tar,");
		sql.append("         TM_ORG             tor");
		sql.append("   where tar.ALLOT_TARGET_ID=tor.ORG_ID");
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and GROUP_ID="+PO.getLong("GROUP_ID")+"\n");
		sql.append("	 and COLOR_NAME='"+PO.getString("COLOR_NAME")+"'\n");
		sql.append("     and TM_ALLOT_ID!="+PO.getLong("TM_ALLOT_ID")+"\n");
		
		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		return Integer.parseInt(CommonUtils.checkNull(list.get(0).get("NUM"),"0"));
	}

	public List<Map> findResourceAllotList2(String seriesCode,String orgId,String allotDate,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as (select distinct tar.ALLOT_TARGET_ID,vm.SERIES_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,vm.GROUP_ID,vm.GROUP_NAME,vm.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
		sql.append("        VW_MATERIAL              vm,\n");
		sql.append("		TM_ORG                   tor,\n");
		sql.append("        TM_DEALER                td,\n");
		sql.append("        TM_DEALER_ORG_RELATION   tdor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tar.COLOR_NAME = vm.COLOR_NAME\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("     and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
		if(!seriesCode.equals("")){
			sql.append("    and tar.SERIES_CODE='"+seriesCode+"'\n");
		}
		if(!orgId.equals("")){
			sql.append("    and tor.ORG_ID='"+orgId+"'\n");
		}
		if(!allotDate.equals("")){
			sql.append("    and tar.ALLOT_DATE between '"+allotDate+" 00:00:00' and '"+allotDate+" 23:59:59.999'\n");			
		}	
		sql.append("   order by tor.ORG_ID,tor.ORG_NAME),\n");
		sql.append("  tab2 as (select distinct tar.ALLOT_TARGET_ID,vm.SERIES_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,vm.GROUP_ID,vm.GROUP_NAME,sum(tar.ADJUST_NUM) NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
		sql.append("        VW_MATERIAL              vm,\n");
		sql.append("		TM_ORG                   tor,\n");
		sql.append("        TM_DEALER                td,\n");
		sql.append("        TM_DEALER_ORG_RELATION   tdor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("     and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append("     and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("     and tar.COLOR_NAME = vm.COLOR_NAME\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("     and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
		sql.append("     group by tar.ALLOT_TARGET_ID,vm.SERIES_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME,vm.GROUP_ID,vm.GROUP_NAME\n");
		sql.append("     having sum(tar.ADJUST_NUM)>0\n");
		sql.append("     order by tor.ORG_ID,tor.ORG_NAME)\n");
		sql.append("select tab1.* from tab1,tab2\n");
		sql.append(" where tab1.ALLOT_TARGET_ID=tab2.ALLOT_TARGET_ID\n");
		sql.append("   and tab1.ORG_ID = tab2.ORG_ID\n");
		sql.append("   and tab1.DEALER_ID=tab2.DEALER_ID\n");
		sql.append("   and tab1.GROUP_ID=tab2.GROUP_ID\n");
		sql.append("   and tab1.GROUP_NAME=tab2.GROUP_NAME\n");		
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> getSeries2(int allotFlag,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS           tar,\n");
		sql.append("        VW_MATERIAL                 vm,\n");
		sql.append("        TM_ORG                      tor,\n");
		sql.append("		TM_DEALER                	td,\n");
		sql.append(" 		TM_DEALER_ORG_RELATION   	tdor,\n");
		sql.append("		TM_VHCL_MATERIAL            tvm,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg4,\n");
		sql.append(" 	    TM_VHCL_MATERIAL_GROUP_R    tvmgr,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP      tvmg2\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append(" 	 and td.DEALER_ID = tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID = tdor.ORG_ID\n");
		sql.append(" 	 and vm.MATERIAL_ID=tvm.MATERIAL_ID\n");
		sql.append("	 and tvmg4.GROUP_ID=tvmgr.GROUP_ID\n");
		sql.append("	 and tvm.MATERIAL_ID=tvmgr.MATERIAL_ID\n");
		sql.append("	 and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("	 and tvmg2.GROUP_ID not in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30)\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public void resourceAuditRefuse(String allotDate,String orgId,LoginInfoDto loginUser) {
		StringBuffer querySql = new StringBuffer();
		querySql.append(" select tar.TM_ALLOT_ID \n");
		querySql.append("     from TM_ALLOT_RESOURCE_DCS        tar,\n");
		querySql.append(" 		  TM_ORG                   tor,\n");
		querySql.append("		  TM_DEALER                td,\n");
		querySql.append(" 		  TM_DEALER_ORG_RELATION   tdor\n");
		querySql.append("	 where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		querySql.append(" 	   and td.DEALER_ID = tdor.DEALER_ID\n");
		querySql.append("	   and tor.ORG_ID = tdor.ORG_ID\n");
		querySql.append("	   and tar.ALLOT_DATE='"+allotDate+"'\n");
		querySql.append("       and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		querySql.append("       and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
		if(orgId!=""){
			querySql.append(" and tor.ORG_ID="+orgId+"\n");
		}
		querySql.append("       and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"");
		
		List<Map> list = OemDAOUtil.findAll(querySql.toString(), null);
		if(null!=list && list.size()>0){
			String tmAllotId = "";
			for (Map map : list) {
				tmAllotId = tmAllotId+","+map.get("TM_ALLOT_ID").toString();
			}
			StringBuffer sql = new StringBuffer();
			sql.append("update TM_ALLOT_RESOURCE_DCS set ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+",AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_03+",AUDIT_STATUS="+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+" \n");
			sql.append("  where TM_ALLOT_ID in("+tmAllotId.substring(1)+")\n");
			OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		}
	}

	public void resourceAuditAgree(String allotDate,String orgId) {
		StringBuffer sql = new StringBuffer();
		//通过到大区总监
		//sql.append("update TM_ALLOT_RESOURCE set ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+",AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+",AUDIT_STATUS="+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+",AUTHOR_TYPE=10431002\n");
		//通过到OTD
		
		StringBuffer querySql = new StringBuffer();
		querySql.append(" SELECT tar.TM_ALLOT_ID \n");
		querySql.append("     from TM_ALLOT_RESOURCE_DCS        tar,\n");
		querySql.append(" 		  TM_ORG                   tor,\n");
		querySql.append("		  TM_DEALER                td,\n");
		querySql.append(" 		  TM_DEALER_ORG_RELATION   tdor\n");
		querySql.append("	 where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		querySql.append(" 	   and td.DEALER_ID = tdor.DEALER_ID\n");
		querySql.append("	   and tor.ORG_ID = tdor.ORG_ID\n");
		querySql.append("	   and tar.ALLOT_DATE='"+allotDate+"'\n");
		querySql.append("       and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		querySql.append("       and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
		querySql.append("       and tor.PARENT_ORG_ID="+orgId+"");
		
		List<Map> list = OemDAOUtil.findAll(querySql.toString(), null);
		if(null!=list && list.size()>0){
			String tmAllotId = "";
			for (Map map : list) {
				tmAllotId = tmAllotId+","+map.get("TM_ALLOT_ID").toString();
			}
			sql.append("update TM_ALLOT_RESOURCE_DCS set AUDIT_STATUS=2,AUDIT_TYPE=12301,AUTHOR_TYPE=10431003\n");	
			sql.append("  where TM_ALLOT_ID in("+tmAllotId.substring(1)+")\n");
			OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		}
		
	}

	public List<Map> findResourceAdjustList(String seriesCode,String orgId, String allotDate, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as(select distinct vm.SERIES_NAME,vm.GROUP_ID,vm.GROUP_NAME,vm.COLOR_NAME\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     tar,\n");
		sql.append("        VW_MATERIAL           vm,\n");
		sql.append("        TM_ORG                tor\n");
		sql.append("   where tar.GROUP_ID = vm.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("     and tar.COLOR_NAME = vm.COLOR_NAME\n");
		sql.append("     and tor.PARENT_ORG_ID= "+loginUser.getOrgId()+"\n");
		sql.append("     and ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		sql.append("   order by vm.SERIES_NAME,vm.GROUP_NAME,vm.COLOR_NAME),\n");
		sql.append("tab2 as (select t1.*,t2.SERIES_NAME,t1.ALLOT_NUM-t1.AD_NUM ADJUST_NUM\n");
		sql.append(" 		from (select GROUP_ID,COLOR_NAME, sum(ALLOT_NUM) ALLOT_NUM,sum(ADJUST_NUM) AD_NUM\n");
		sql.append(" 				from TM_ALLOT_RESOURCE_DCS   where ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+" group by GROUP_ID,COLOR_NAME\n");
		sql.append("  				having sum(ALLOT_NUM)>sum(ADJUST_NUM)) t1\n");
		sql.append("				left join (select distinct vm.SERIES_NAME,vm.GROUP_ID from VW_MATERIAL vm) t2\n");
		sql.append(" on t1.GROUP_ID=t2.GROUP_ID)\n");
		sql.append("  select tab1.*,nvl(tab2.ADJUST_NUM,0) ADJUST_NUM  from tab1 left join tab2\n");
		sql.append("     on tab1.SERIES_NAME=tab2.SERIES_NAME\n");	
		sql.append("     and tab1.GROUP_ID=tab2.GROUP_ID\n");
		sql.append("     and tab1.COLOR_NAME=tab2.COLOR_NAME\n");

		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public int resourceAllotCheck(String allotDate,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();		
		sql.append("select sum(ALLOT_NUM)-sum(ADJUST_NUM) NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE_DCS  tar,\n");
		sql.append("       TM_ORG             tor\n");
		sql.append("   where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_03+"\n");
		sql.append("	 and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");

		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return new Integer(list.get(0).get("NUM").toString());
		}else{
			return 0;
		}		
	}

	public String findAllotTitle(String seriesCode, String orgId,String allotDate, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();		
		sql.append("select sum(ALLOT_NUM) ALLOT_NUM,sum(ADJUST_NUM) ADJUST_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE_DCS  tar,\n");
		sql.append("       TM_ORG             tor\n");
		sql.append("   where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("     and  ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		Map map = list.get(0);
		String til = "";
		if(Integer.valueOf(map.get("ALLOT_NUM").toString())>Integer.valueOf(map.get("ADJUST_NUM").toString())){
			til = "<font color=red>"+map.get("ADJUST_NUM")+"</font>";
		}else{
			til = map.get("ADJUST_NUM").toString();
		}
		return "<h3>本区共有资源:"+map.get("ALLOT_NUM")+"  已分配:"+til+"<h3>";
	}


	public List<Map> findTotalGapListBySeries(String groupId,String orgId, String allotDate,String allotMonthDate,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append("  from  ( select  tt1.GROUP_ID SERIES_ID,tt1.SERIES_NAME,tt1.ORG_ID,tt1.ORG_NAME,tt1.SALE_AMOUNT,tt1.NUM1,tt1.NUM2,tt1.NUM22,tt1.NUM3,tt1.NUM33,tt1.NUM4,tt1.NUM5,tt1.GAP,tt1.ALLOT_NUM,tt2.ALLOT_MONTH_NUM\n");
		sql.append("  from (select t1.*,t2.ALLOT_NUM,t1.org_id ALLOT_TARGET_ID\n");
		sql.append("   from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR2.ORG_ID,TOR2.ORG_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,nvl(ttg.GAP,0) GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_ORG                   tor2,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=tor2.ORG_ID\n");
		sql.append("			 and tor2.PARENT_ORG_ID=tor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("	 		 and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(orgId!=""){
			sql.append(" and tor2.org_id='"+orgId+"'"+"\n");//
		}
		sql.append("             and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 		 and ttg.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 			  and tar.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("	 			  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.ORG_ID = t2.ALLOT_TARGET_ID \n");
		sql.append(" ) tt1     left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_MONTH_NUM \n");
   		sql.append("							from TM_ALLOT_RESOURCE_DCS              tar,\n");
   		sql.append("								 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
  		sql.append("								 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
  		sql.append("								 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
   		sql.append("							where tar.GROUP_ID=tvmg4.GROUP_ID \n");
  		sql.append("							  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
  		sql.append("							  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("							  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
	 	sql.append("							  and tar.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
  		sql.append("							  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
  		sql.append("	 						  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
 		sql.append("							group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) tt2 on  tt1.group_id=tt2.group_id and tt1.allot_target_id=tt2.allot_target_id\n");
		sql.append("union all\n");
		//sql.append(" select tt1.*,tt2.allot_month_num from (select t1.GROUP_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,sum(t1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(t2.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("select  tt1.GROUP_ID SERIES_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,sum(tt1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(tt1.ALLOT_NUM) ALLOT_NUM,sum(tt2.allot_month_num)\n");
		sql.append("	from (select  t1.org_id2,t1.GROUP_ID,t1.SALE_AMOUNT,NUM1,NUM2,NUM22,NUM3,NUM33,NUM4,NUM5,GAP,t2.ALLOT_NUM\n");
		sql.append("  		from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,tor2.ORG_ID ORG_ID2,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,nvl(ttg.GAP,0) GAP\n");
		sql.append("      	 	 from TM_TOTAL_GAP   			ttg,\n");
		sql.append("              	 TM_ORG         			tor,\n");
		sql.append("     		  	 TM_ORG                   tor2,\n");
		sql.append("  			  	 TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("          	 where ttg.TARGET_ID=tor2.ORG_ID\n");
		sql.append("				 and tor2.PARENT_ORG_ID=tor.ORG_ID\n");
		sql.append("    			 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     	 and tvmg.GROUP_LEVEL=2\n");
		sql.append("            	 and tor.ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
		sql.append("			 	 and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(orgId!=""){
			sql.append(" 			 and tor2.ORG_ID='"+orgId+"'\n");//
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 	     and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("	 		 and ttg.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("				  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 			  and tar.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("	 			  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.ORG_ID2 = t2.ALLOT_TARGET_ID \n");
		//sql.append("  group by t1.GROUP_ID\n");
		sql.append("	) tt1 left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(nvl(ADJUST_NUM,0)) ALLOT_MONTH_NUM\n");
   		sql.append("								from TM_ALLOT_RESOURCE_DCS              tar,\n");
   		sql.append("									 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
  		sql.append("									 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
  		sql.append("									 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
   		sql.append("								where tar.GROUP_ID=tvmg4.GROUP_ID\n");
  		sql.append("								  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
  		sql.append("								  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("								  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
	 	sql.append("								  and tar.AUTHOR_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
  		sql.append("								  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
  		sql.append("	 							  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
 		sql.append("								group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) tt2  on tt1.group_id=tt2.group_id and tt1.org_id2=tt2.allot_target_id group by tt1.group_id");
		sql.append(") t\n");
		sql.append(" order by t.ORG_NAME");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findResourceAllotListBySeries(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,sum(tar.ADJUST_NUM) SER_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("	 and tar.AUTHOR_TYPE=10431004\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");		
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findResourceAllotListByColor(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("	 and tar.AUTHOR_TYPE=10431004\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List checkColorNum(String allotDate,String seriesId, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID,sum(tar.ALLOT_NUM) ALLOT_NUM,sum(tar.ADJUST_NUM) ADJUST_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar,\n");
		sql.append("		TM_ORG                		   tor,\n");
		sql.append("		TM_ORG                		   tor2,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2\n");	
		sql.append("   where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tar.ALLOT_TARGET_ID = tor2.ORG_ID\n");
		sql.append("	 and tor2.PARENT_ORG_ID=tor.ORG_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     and tvmg2.GROUP_ID="+seriesId+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("     and tor.ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("   group by tvmg2.GROUP_ID\n");
		sql.append("   having sum(tar.ALLOT_NUM)<>sum(tar.ADJUST_NUM)\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public String findAllotDate(LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct to_char(tar.CREATE_DATE,'yyyy-MM-dd')  CREATE_DATE\n");
		sql.append(" from TM_ALLOT_RESOURCE_DCS  tar,TM_ORG tor\n");
		sql.append(" where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("   and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("   and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		sql.append("   and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return list.get(0).get("CREATE_DATE").toString();
		}
		return "";
	}

	/*public List<Map> findHasNumListBySeries(String conditionWhere,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ALLOT_NUM) ALLOT_NUM\n");
		sql.append("  from TM_ALLOT_RESOURCE             tar,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   TM_ORG                         tor\n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("    and tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");

		List<Map> list=factory.select(sql.toString(),null,new DAOCallback<Map>(){
			public Map wrapper(ResultSet rs, int arg1) {
				try {
					Map map = new HashMap();
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
	public List<Map> findHasNumListBySeries(String conditionWhere,LoginInfoDto loginUser,String groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select tt1.*,nvl(tt2.ALLOT_NUM,0) ALLOT_NUM,nvl(tt2.status,0) STATUS from  \n");
		sql.append("   (SELECT group_id SERIES_ID,group_code SERIES_CODE,(case when group_id in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else GROUP_NAME end) SERIES_NAME FROM Tm_Vhcl_Material_Group\n");
		sql.append(" 		WHERE 1=1  AND Status=10011001  AND Group_Level=2 \n");
		if(groupId!=""){
			sql.append("               and group_id in("+groupId+")\n");
		}
		sql.append("   )tt1  \n");
		sql.append("    left join  \n");
		sql.append("	(select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ALLOT_NUM) ALLOT_NUM,ttg.STATUS\n");
		sql.append("  		from TM_ALLOT_RESOURCE_DCS             tar,\n");
		sql.append("   	   		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("   	   		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("   	   		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("   	   		TM_ORG                         tor,\n");
		sql.append("			TM_TOTAL_GAP                   ttg \n");
		sql.append("  where tar.GROUP_ID=tvmg4.GROUP_ID and ttg.group_id=tvmg2.group_id and tor.org_id=ttg.target_id and ttg.status=1\n");
		sql.append("    and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("    and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("    and tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("    and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("    and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append("   group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME,ttg.STATUS\n");
		sql.append(" ) tt2 \n");
		sql.append("on tt1.SERIES_CODE =tt2.SERIES_CODE \n");
		sql.append(" order by ALLOT_NUM desc\n");              
                  

		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	public List<Map> findResourceAllotList2(String conditionWhere,String auditType,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as (");
		sql.append("select distinct vw.STANDARD_OPTION,vw.FACTORY_OPTIONS,vw.local_option,vw.MODEL_YEAR,tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD , \n");
		sql.append("        TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("        VW_MATERIAL                    VW");
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID and vw.group_id=tvmg4.group_Id\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}			
	//	sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME\n");
		sql.append(" ),\n");
		sql.append("tab2 as (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD ,\n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		//sql.append("    having sum(tar.ADJUST_NUM)>0\n");
		sql.append(" )\n");
		sql.append(" select tab1.* from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("	and exists(select 1\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("	 and tar.GROUP_ID=tab1.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("	 and tar.AUTHOR_TYPE=10431004\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");
		sql.append("	 having sum(tar.ADJUST_NUM)>0\n");
		sql.append(" )");
		sql.append("   left join TM_DEALER_WHS tdw on tdw.DEALER_ID=tab1.ORG_ID and tdw.TYPE=3\n");
		sql.append("   left join TM_DEALER_WHS tdw2 on tdw2.DEALER_ID=tab1.DEALER_ID and tdw2.TYPE=1\n");
		sql.append("   order by tdw.ORDER_NUM,tdw2.ORDER_NUM ASC\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);;	
		return list;
	}
	/*public List<Map> findResourceAllotList2(String conditionWhere,String auditType,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as (");
		sql.append("select distinct vw.STANDARD_OPTION,vw.FACTORY_OPTIONS,vw.local_option,vw.MODEL_YEAR,tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD , \n");
		sql.append("        TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2,\n");
		sql.append("        VW_MATERIAL                    VW");
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID and vw.group_id=tvmg4.group_Id\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}			
	//	sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME\n");
		sql.append(" ),\n");
		sql.append("tab2 as (\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD ,\n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		//sql.append("    having sum(tar.ADJUST_NUM)>0\n");
		sql.append(" )\n");
		sql.append(" select tab1.* from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("	and exists(select 1\n");
		sql.append("   from TM_ALLOT_RESOURCE     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("	 and tar.GROUP_ID=tab1.GROUP_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("	 and tar.AUTHOR_TYPE=10431004\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME\n");
		sql.append("	 having sum(tar.ADJUST_NUM)>0)\n");
		sql.append("   left join TM_DEALER_WHS tdw on tdw.DEALER_ID=tab1.ORG_ID and tdw.TYPE=3\n");
		sql.append("   left join TM_DEALER_WHS tdw2 on tdw2.DEALER_ID=tab1.DEALER_ID and tdw2.TYPE=1\n");
		sql.append("   order by tdw.ORDER_NUM,tdw2.ORDER_NUM ASC\n");
		
		List list = dao.OemDAOUtil.findAll(sql.toString(), null);;	
		return list;
	}*/
	/*public List<Map> findResourceAllotList2(String conditionWhere,String auditType,LoginInfoDto loginUser,String allotDate,String groupId,String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("with tab1 as (");
		sql.append("SELECT DISTINCT nvl(tt3.TM_ALLOT_ID,0) TM_ALLOT_ID, TT1.STATUS,TT1.DEALER_ID ALLOT_TARGET_ID,TT2.SERIES_ID,(CASE WHEN TT2.SERIES_ID IN (SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TT2.SERIES_NAME END) SERIES_NAME,NVL(TT3.GROUP_ID,0) GROUP_ID,NVL(TT3.GROUP_NAME,'0') GROUP_NAME,TT1.ORG_ID,TT1.ORG_NAME,TT1.DEALER_ID,TT1.DEALER_NAME,NVL(TT3.COLOR_NAME,'0') COLOR_NAME,NVL(TT3.NUM,0) NUM \n");
		sql.append(" 	FROM (SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TD.DEALER_SHORTNAME DEALER_NAME,TD.DEALER_ID,TOR.ORG_NAME,TOR.ORG_ID FROM\n");
		sql.append(" 			TM_TOTAL_GAP TTG,TM_ORG  TOR,TM_DEALER TD,TM_ALLOT_RESOURCE tar,\n");
		sql.append(" 			TM_DEALER_ORG_RELATION         TDOR WHERE TD.DEALER_ID=TTG.TARGET_ID AND TTG.ALLOT_DATE='"+allotDate+"'  and ttg.status=0\n");
		sql.append(" 			AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 			AND TD.DEALER_ID=TTG.TARGET_ID  AND TD.DEALER_ID=TDOR.DEALER_ID\n");
		sql.append(" 			AND TOR.ORG_ID=TDOR.ORG_ID  and  td.dealer_id=tar.allot_target_id\n");
		if(groupId!=""){
			sql.append("                and ttg.group_id in ("+groupId+")\n");
		}
		if(orgId!=""){
			sql.append("                and tor.org_id="+orgId+"\n");
		}
		sql.append(" and tor.parent_org_id="+loginUser.getOrgId()+"\n");
		
		
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("  and tar.AUDIT_STATUS in ("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")");	
		//sql.append(" and tar.AUDIT_STATUS="+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+"\n");
		sql.append("and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"  and tar.audit_type='"+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"'  \n");
		//sql.append("  and tar.AUTHOR_TYPE=10431003  \n");
		sql.append(" 		) TT1 LEFT JOIN \n");
		sql.append(" 		(SELECT GROUP_ID SERIES_ID,GROUP_CODE,GROUP_NAME SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001 AND GROUP_LEVEL=2 \n");
		sql.append(" 		) TT2 ON TT2.SERIES_ID=TT1.SERIES_ID\n");
		sql.append(" 		LEFT JOIN\n");
		sql.append("(select tar.TM_ALLOT_ID,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD , \n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}			
		sql.append("    order by tvmg2.GROUP_NAME,tvmg4.GROUP_NAME\n");
		sql.append("  ) TT3 ON TT2.SERIES_ID=TT3.SERIES_ID\n");
		sql.append("union all \n");
		
		sql.append("select tar.TM_ALLOT_ID,0 as status,tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_ID,tor.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,tar.COLOR_NAME,tar.ADJUST_NUM NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD , \n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}	
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}			
		sql.append(" \n");
		
		sql.append(" ),\n");
		sql.append("tab2 as (\n");
		sql.append("SELECT DISTINCT TT1.STATUS,TT2.SERIES_ID,(CASE WHEN TT2.SERIES_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TT2.SERIES_NAME END) SERIES_NAME,NVL(TT3.GROUP_ID,0) GROUP_ID,NVL(TT3.GROUP_NAME,'0') GROUP_NAME,NVL(TT3.COLOR_NAME,'0') COLOR_NAME,NVL(TT3.COLOR_NUM,0) COLOR_NUM \n");
		sql.append(" 	FROM (SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TD.DEALER_SHORTNAME DEALER_NAME,TD.DEALER_ID,TOR.ORG_NAME,TOR.ORG_ID FROM\n");
		sql.append(" 			TM_TOTAL_GAP TTG,TM_ORG  TOR,TM_DEALER TD,\n");
		sql.append(" 			TM_DEALER_ORG_RELATION         TDOR WHERE TD.DEALER_ID=TTG.TARGET_ID AND TTG.ALLOT_DATE='"+allotDate+"' \n");
		sql.append(" 			AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 			AND TD.DEALER_ID=TTG.TARGET_ID  AND TD.DEALER_ID=TDOR.DEALER_ID\n");
		sql.append(" 			AND TOR.ORG_ID=TDOR.ORG_ID  \n");
		if(groupId!=""){
			sql.append("                and ttg.group_id in ("+groupId+")\n");
		}
		sql.append(" 		 ) TT1 LEFT JOIN\n");
		sql.append(" 		(SELECT GROUP_ID SERIES_ID,GROUP_CODE,GROUP_NAME SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001 AND GROUP_LEVEL=2\n");
		sql.append(" 		) TT2 ON TT2.SERIES_ID=TT1.SERIES_ID\n");
		sql.append(" 			left join\n");
		sql.append("(select tvmg2.GROUP_ID SERIES_ID,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar\n");
		sql.append("  ,(SELECT * FROM TM_ORG ORDER BY CREATE_DATE ) TOR  \n");
		sql.append(" ,(select td.CREATE_DATE,TD.DEALER_SHORTNAME , TD.DEALER_ID,TDW.ORDER_NUM from TM_DEALER TD " + 
					 " LEFT JOIN TM_DEALER_WHS TDW ON ( TD.DEALER_ID = TDW.DEALER_ID   AND TDW.TYPE = '1' " + 
					 " AND TD.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.WHS_DISPLAY_STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " " +
					 " AND TD.DEALER_TYPE = " + OemDictCodeConstants.DEALER_TYPE_DVS + " ) " + 
					 " ORDER BY TDW.ORDER_NUM ASC,TD.CREATE_DATE ASC) TD ,\n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		sql.append("    having sum(tar.ADJUST_NUM)>0)\n");
		sql.append(" TT3 ON TT3.SERIES_ID=TT2.SERIES_ID");
		sql.append(" )\n");
		sql.append(" select tab1.* from tab1 inner join tab2\n");
		sql.append("     on tab1.SERIES_ID=tab2.SERIES_ID and tab1.GROUP_ID=tab2.GROUP_ID and tab1.COLOR_NAME=tab2.COLOR_NAME\n");
		sql.append("   left join TM_DEALER_WHS tdw on tdw.DEALER_ID=tab1.ORG_ID and tdw.TYPE=3\n");
		sql.append("   left join TM_DEALER_WHS tdw2 on tdw2.DEALER_ID=tab1.DEALER_ID and tdw2.TYPE=1\n");
		sql.append("   order by tdw.ORDER_NUM,tdw2.ORDER_NUM ASC\n");
		
		List list = dao.OemDAOUtil.findAll(sql.toString(), null);;	
		return list;
	}*/
	/*public List<Map> findHasNumListBySeries2(String conditionWhere,String auditType,LoginInfoDto loginUser,String allotDate,String groupId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT NVL(TT3.STATUS,0) STATUS,TT1.SERIES_ID,TT1.SERIES_CODE,TT1.SERIES_NAME,NVL(TT2.ALLOT_NUM,0) ALLOT_NUM \n");
		sql.append(" 	FROM\n");
		sql.append(" (SELECT GROUP_ID SERIES_ID,GROUP_CODE SERIES_CODE, (CASE WHEN GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE GROUP_NAME END) SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP\n");
		sql.append(" 	WHERE 1=1  AND STATUS=10011001  AND GROUP_LEVEL=2\n");
		if(groupId!=""){
			sql.append("      and group_id='"+groupId+"' \n");
		}
		sql.append(" ) TT1 LEFT JOIN\n");
		sql.append("(select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE     		   tar,\n");
		sql.append("		TM_ORG                		   tor,\n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");
		sql.append("    having sum(ADJUST_NUM)>0) tt2\n");		
		sql.append(" ON TT1.SERIES_ID=TT2.SERIES_ID\n");
		sql.append("LEFT JOIN  (SELECT DISTINCT TTG.STATUS,TTG.GROUP_ID SERIES_ID FROM \n");
		sql.append(" 			TM_TOTAL_GAP TTG WHERE\n");
		sql.append(" 			TTG.ALLOT_DATE='"+allotDate+"' AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			) TT3 ON TT3.SERIES_ID=TT2.SERIES_ID \n");
		List<Map> list=factory.select(sql.toString(),null,new DAOCallback<Map>(){
			public Map wrapper(ResultSet rs, int arg1) {
				try {
					Map map = new HashMap();
					map.put("SERIES_ID", rs.getString("SERIES_ID"));	
					map.put("SERIES_CODE", rs.getString("SERIES_CODE"));	
					map.put("SERIES_NAME", rs.getString("SERIES_NAME"));
					map.put("ALLOT_NUM", rs.getInt("ALLOT_NUM"));
					map.put("STATUS", rs.getInt("STATUS"));
					return map;
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}});
		return list;
	}*/
	public List<Map> findHasNumListBySeries2(String conditionWhere,String auditType,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_CODE SERIES_CODE,(case when tvmg2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tvmg2.GROUP_NAME end) SERIES_NAME,sum(ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar,\n");
		sql.append("		TM_ORG                		   tor,\n");
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
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_CODE,tvmg2.GROUP_NAME\n");
		sql.append("    having sum(ADJUST_NUM)>0\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
/*	public List<Map> findTotalGapListBySeries2(String groupId,String orgId, String allotDate,String auditType, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append("  from (select t1.GROUP_ID,t1.SERIES_NAME,t1.ORG_ID,t1.ORG_NAME,t1.DEALER_ID,t1.DEALER_NAME,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,t1.SALE_AMOUNT,nvl(t1.NUM1,0) NUM1,nvl(t1.NUM2,0) NUM2,nvl(t1.NUM22,0) NUM22,nvl(t1.NUM3,0) NUM3,nvl(t1.NUM33,0) NUM33,nvl(t1.NUM4,0) NUM4,nvl(t1.NUM5,0) NUM5,t1.GAP,t2.ALLOT_NUM\n");
		sql.append("   from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append("union all\n");
		sql.append("select t1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,t1.ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,sum(t1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(t2.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS,sum(tar.ADJUST_NUM) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append("  group by t1.GROUP_ID,t1.ORG_ID,t2.ALLOT_STATUS\n");
		sql.append("union all\n");
		sql.append("select tt.GROUP_ID,tt.SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,1 as ALLOT_STATUS,sum(tt.SALE_AMOUNT) SALE_AMOUNT,sum(tt.NUM1) NUM1,sum(tt.NUM2) NUM2,sum(tt.NUM22) NUM22,sum(tt.NUM3) NUM3,sum(tt.NUM33) NUM33,sum(tt.NUM4) NUM4,sum(tt.NUM5) NUM5,sum(tt.GAP) GAP,sum(tt.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("    from (select t1.GROUP_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,(case when t2.ALLOT_STATUS>0 then sum(t1.SALE_AMOUNT) else 0 end) SALE_AMOUNT,(case when t2.ALLOT_STATUS>0 then sum(t1.NUM1) else 0 end) NUM1,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then sum(t1.NUM2) else 0 end) NUM2,(case when t2.ALLOT_STATUS>0 then avg(t1.NUM22) else 0 end) NUM22,(case when t2.ALLOT_STATUS>0 then sum(t1.NUM3) else 0 end) NUM3,(case when t2.ALLOT_STATUS>0 then avg(t1.NUM33) else 0 end) NUM33,(case when t2.ALLOT_STATUS>0 then sum(t1.NUM4) else 0 end) NUM4,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then sum(t1.NUM5) else 0 end) NUM5,(case when t2.ALLOT_STATUS>0 then sum(t1.GAP) else 0 end) GAP,(case when t2.ALLOT_STATUS>0 then sum(t2.ALLOT_NUM) else 0 end) ALLOT_NUM\n");
		sql.append("  			from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  			from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               			 TM_ORG         			tor,\n");
		sql.append("     		   			 TM_DEALER                td,\n");
		sql.append("			   			 TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   			 TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           			where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     		  and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 		  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 		  and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		    		  and tvmg.GROUP_LEVEL=2\n");
		sql.append("             		  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 		  and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("					  and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 		  and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             		  and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  		    inner join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS,sum(tar.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("   						 from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 		  TM_DEALER                      td,\n");
		sql.append("					 		  TM_ORG                		 tor,\n");
		sql.append("			   		 		  TM_DEALER_ORG_RELATION   		 tdor,\n");
		sql.append("   					 		  TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  							  TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 		  TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   						 where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      		   and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  		   and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  		   and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  		   and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  		   and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("             	  		   and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  		   and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  		   and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("						and tor.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 		    and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(auditType.equals("0")){
			sql.append("	 					and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 					and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+")\n");
		}		
		sql.append(" 					     group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  			on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append("  			group by t1.GROUP_ID,t2.ALLOT_STATUS) tt\n");
		sql.append("	group by tt.GROUP_ID,tt.SERIES_NAME) t\n");
		sql.append(" order by t.ORG_NAME");	
		
		List<Map> list=factory.select(sql.toString(),null,new DAOCallback<Map>(){
			public Map wrapper(ResultSet rs, int arg1) {
				try {
					Map map = new HashMap();
					map.put("SERIES_ID", rs.getString("GROUP_ID"));
					map.put("SERIES_NAME", rs.getString("SERIES_NAME"));
					map.put("ORG_ID", rs.getLong("ORG_ID"));
					map.put("ORG_NAME", rs.getString("ORG_NAME"));
					map.put("DEALER_ID", rs.getString("DEALER_ID"));
					map.put("DEALER_NAME", rs.getString("DEALER_NAME"));
					map.put("ALLOT_STATUS", rs.getString("ALLOT_STATUS"));
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
	public List<Map> findTotalGapListBySeries2(String groupId,String orgId, String allotDate,String allotMonthDate,String auditType, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append(" from (select tt1.GROUP_ID SERIES_ID,tt1.SERIES_NAME,tt1.ORG_ID,tt1.ORG_NAME,tt1.DEALER_ID,tt1.DEALER_NAME,tt1.ALLOT_STATUS,tt1.SALE_AMOUNT,tt1.NUM1,tt1.NUM2,tt1.NUM22,tt1.NUM3,tt1.NUM33,tt1.NUM4,tt1.NUM5,tt1.GAP,tt1.ALLOT_NUM,TT2.ALLOT_MONTH_NUM \n");
		sql.append("  from (select t1.GROUP_ID,(case when T1.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else T1.SERIES_NAME end) SERIES_NAME,t1.ORG_ID,t1.ORG_NAME,t1.DEALER_ID,t1.DEALER_NAME,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,t1.SALE_AMOUNT,nvl(t1.NUM1,0) NUM1,nvl(t1.NUM2,0) NUM2,nvl(t1.NUM22,0) NUM22,nvl(t1.NUM3,0) NUM3,nvl(t1.NUM33,0) NUM33,nvl(t1.NUM4,0) NUM4,nvl(t1.NUM5,0) NUM5,t1.GAP,t2.ALLOT_NUM\n");
		sql.append("   from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,nvl(ttg.GAP,0) GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" AND TTG.STATUS=1 ) t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append(" )tt1\n");
		sql.append(" left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("				from TM_ALLOT_RESOURCE_DCS tar,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4,\n");
		sql.append("			 			TM_VHCL_MATERIAL_GROUP tvmg3,TM_VHCL_MATERIAL_GROUP tvmg2	where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("	     		 and td.DEALER_ID=tdor.DEALER_ID  and tor.ORG_ID=tdor.ORG_ID  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("		  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("	  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}
		sql.append("group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS\n");
		sql.append("	) tt2 on TT1.GROUP_ID=TT2.GROUP_ID AND TT1.DEALER_ID=TT2.ALLOT_TARGET_ID\n");
		sql.append("union all\n");
		sql.append("select tt1.GROUP_ID SERIES_ID,'ORG_TOTAL' as SERIES_NAME,tt1.ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,NVL(tt2.ALLOT_STATUS,0) ALLOT_STATUS,sum(tt1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(tt1.ALLOT_NUM) ALLOT_NUM\n");
		sql.append(",sum(tt2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM from ( \n");
		sql.append("select t1.dealer_id,t1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,t1.ORG_ID,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,nvl(t1.SALE_AMOUNT,0) SALE_AMOUNT,nvl(NUM1,0) NUM1,nvl(NUM2,0) NUM2,nvl(NUM22,0) NUM22,nvl(NUM3,0) NUM3,nvl(NUM33,0) NUM33,nvl(NUM4,0) NUM4,nvl(NUM5,0) NUM5,nvl(GAP,0) GAP,nvl(t2.ALLOT_NUM,0) ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" AND TTG.STATUS=1) t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  group by t1.GROUP_ID,t1.ORG_ID,t2.ALLOT_STATUS\n");
		sql.append(") TT1   LEFT JOIN (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
   		sql.append("		from TM_ALLOT_RESOURCE_DCS tar,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4, TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
   		sql.append("			 TM_VHCL_MATERIAL_GROUP tvmg2	where tar.ALLOT_TARGET_ID=td.DEALER_ID  and td.DEALER_ID=tdor.DEALER_ID  and tor.ORG_ID=tdor.ORG_ID\n");
   		sql.append("		  and tar.GROUP_ID=tvmg4.GROUP_ID  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
   		sql.append("   	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"  and tar.ALLOT_DATE like '%"+allotMonthDate+"%' \n");
   		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("		group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS\n");
		sql.append(") TT2 ON TT1.GROUP_ID=TT2.GROUP_ID AND TT1.DEALER_ID=TT2.ALLOT_TARGET_ID group by tt1.GROUP_ID,tt1.ORG_ID,tt2.ALLOT_STATUS\n");
		sql.append("union all\n");
		sql.append("select tt.GROUP_ID,tt.SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,1 as ALLOT_STATUS,sum(tt.SALE_AMOUNT) SALE_AMOUNT,sum(tt.NUM1) NUM1,sum(tt.NUM2) NUM2,AVG(tt.NUM22) NUM22,sum(tt.NUM3) NUM3,sum(tt.NUM33) NUM33,sum(tt.NUM4) NUM4,sum(tt.NUM5) NUM5,sum(tt.GAP) GAP,sum(tt.ALLOT_NUM) ALLOT_NUM,SUM(TT2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM\n");
		sql.append("    from (select  t1.DEALER_ID,t1.GROUP_ID,'TOTAL' as SERIES_NAME,(case when t2.ALLOT_STATUS>0 then t1.SALE_AMOUNT else 0 end) SALE_AMOUNT,(case when t2.ALLOT_STATUS>0 then t1.NUM1 else 0 end) NUM1,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then t1.NUM2 else 0 end) NUM2,(case when t2.ALLOT_STATUS>0 then t1.NUM22 else 0 end) NUM22,(case when t2.ALLOT_STATUS>0 then t1.NUM3 else 0 end) NUM3,(case when t2.ALLOT_STATUS>0 then t1.NUM33 else 0 end) NUM33,(case when t2.ALLOT_STATUS>0 then t1.NUM4 else 0 end) NUM4,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then t1.NUM5 else 0 end) NUM5,(case when t2.ALLOT_STATUS>0 then t1.GAP else 0 end) GAP,(case when t2.ALLOT_STATUS>0 then t2.ALLOT_NUM else 0 end) ALLOT_NUM\n");
		sql.append("  			from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  			from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               			 TM_ORG         			tor,\n");
		sql.append("     		   			 TM_DEALER                td,\n");
		sql.append("			   			 TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   			 TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           			where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     		  and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 		  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 		  and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		    		  and tvmg.GROUP_LEVEL=2\n");
		sql.append("             		  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 		  and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("					  and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 		  and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             		  and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" AND TTG.STATUS=1) t1\n");
		sql.append("  		    inner join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(tar.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("   						 from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("					 		  TM_DEALER                      td,\n");
		sql.append("					 		  TM_ORG                		 tor,\n");
		sql.append("			   		 		  TM_DEALER_ORG_RELATION   		 tdor,\n");
		sql.append("   					 		  TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  							  TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 		  TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   						 where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      		   and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  		   and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  		   and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  		   and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  		   and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("             	  		   and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  		   and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  		   and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("						and tor.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 		    and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(auditType.equals("0")){
			sql.append("	 					and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 					and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 					     group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  			on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  			group by t1.GROUP_ID,t2.ALLOT_STATUS\n");
		sql.append(") tt\n");
		sql.append("    left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(nvl(tar.ALLOT_NUM,0)) ALLOT_MONTH_NUM\n");
   		sql.append("				 from TM_ALLOT_RESOURCE_DCS  tar, TM_DEALER   td, TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4,\n");
   		sql.append("				  TM_VHCL_MATERIAL_GROUP tvmg3,TM_VHCL_MATERIAL_GROUP tvmg2 where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
   		sql.append("      		   and td.DEALER_ID=tdor.DEALER_ID and tor.ORG_ID=tdor.ORG_ID and tar.GROUP_ID=tvmg4.GROUP_ID and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
   		sql.append("	  		   and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID and tor.PARENT_ORG_ID="+loginUser.getOrgId()+" and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tar.ALLOT_DATE LIKE '%"+allotMonthDate+"%'\n");
   		if(!orgId.equals("")){
			sql.append("						and tor.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 		    and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(auditType.equals("0")){
			sql.append("	 					and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 					and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		 
	 	sql.append("				        group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS\n");
        sql.append("          ) tt2 on tt.group_id=tt2.group_id and tt.DEALER_ID = tt2.ALLOT_TARGET_ID \n");

		sql.append("	group by tt.GROUP_ID,tt.SERIES_NAME) t\n");
		sql.append(" order by t.ORG_NAME");	
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	/*public List<Map> findTotalGapListBySeries2(String groupId,String orgId, String allotDate,String allotMonthDate,String auditType, LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE,(case when t.SALE_AMOUNT>0 then round((t.SALE_AMOUNT-t.GAP)*100/t.SALE_AMOUNT,2) else 0 end) DRATE\n");
		sql.append(" from (select tt1.*,TT2.ALLOT_MONTH_NUM \n");
		sql.append("  from (select t1.GROUP_ID,t1.SERIES_NAME,t1.ORG_ID,t1.ORG_NAME,t1.DEALER_ID,t1.DEALER_NAME,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,t1.SALE_AMOUNT,nvl(t1.NUM1,0) NUM1,nvl(t1.NUM2,0) NUM2,nvl(t1.NUM22,0) NUM22,nvl(t1.NUM3,0) NUM3,nvl(t1.NUM33,0) NUM33,nvl(t1.NUM4,0) NUM4,nvl(t1.NUM5,0) NUM5,t1.GAP,t2.ALLOT_NUM\n");
		sql.append("   from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,nvl(ttg.GAP,0) GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		sql.append(" )tt1\n");
		sql.append(" left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
		sql.append("				from TM_ALLOT_RESOURCE tar,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4,\n");
		sql.append("			 			TM_VHCL_MATERIAL_GROUP tvmg3,TM_VHCL_MATERIAL_GROUP tvmg2	where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("	     		 and td.DEALER_ID=tdor.DEALER_ID  and tor.ORG_ID=tdor.ORG_ID  and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("		  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("	  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"  and tar.ALLOT_DATE like '%"+allotMonthDate+"%'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}
		sql.append("group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS\n");
		sql.append("	) tt2 on TT1.GROUP_ID=TT2.GROUP_ID AND TT1.DEALER_ID=TT2.ALLOT_TARGET_ID\n");
		sql.append("union all\n");
		sql.append("select tt1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,tt1.ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,NVL(tt1.ALLOT_STATUS,0) ALLOT_STATUS,sum(tt1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,avg(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(tt1.ALLOT_NUM) ALLOT_NUM\n");
		sql.append(",sum(tt2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM from ( \n");
		sql.append("select t1.dealer_id,t1.GROUP_ID,'ORG_TOTAL' as SERIES_NAME,t1.ORG_ID,nvl(t2.ALLOT_STATUS,0) ALLOT_STATUS,nvl(t1.SALE_AMOUNT,0) SALE_AMOUNT,nvl(NUM1,0) NUM1,nvl(NUM2,0) NUM2,nvl(NUM22,0) NUM22,nvl(NUM3,0) NUM3,nvl(NUM33,0) NUM33,nvl(NUM4,0) NUM4,nvl(NUM5,0) NUM5,nvl(GAP,0) GAP,nvl(t2.ALLOT_NUM,0) ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,nvl(ttg.SALE_AMOUNT,0) SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("     		   TM_DEALER                td,\n");
		sql.append("			   TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("             and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("			and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS,sum(nvl(tar.ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 TM_DEALER                      td,\n");
		sql.append("					 TM_ORG                		    tor,\n");
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
		sql.append("             	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 			group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  group by t1.GROUP_ID,t1.ORG_ID,t2.ALLOT_STATUS\n");
		sql.append(") TT1   LEFT JOIN (select tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID,sum(tar.ADJUST_NUM) ALLOT_MONTH_NUM\n");
   		sql.append("		from TM_ALLOT_RESOURCE tar,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4, TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
   		sql.append("			 TM_VHCL_MATERIAL_GROUP tvmg2	where tar.ALLOT_TARGET_ID=td.DEALER_ID  and td.DEALER_ID=tdor.DEALER_ID  and tor.ORG_ID=tdor.ORG_ID\n");
   		sql.append("		  and tar.GROUP_ID=tvmg4.GROUP_ID  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
   		sql.append("   	  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"  and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"  and tar.ALLOT_DATE like '%"+allotMonthDate+"%' \n");
   		if(!orgId.equals("")){
			sql.append("				and tor.ORG_ID="+orgId+"\n");
		}
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("		group by tvmg2.GROUP_ID,tor.ORG_ID,tar.ALLOT_TARGET_ID\n");
		sql.append(") TT2 ON TT1.GROUP_ID=TT2.GROUP_ID AND TT1.DEALER_ID=TT2.ALLOT_TARGET_ID group by tt1.GROUP_ID,tt1.ORG_ID,tt1.ALLOT_STATUS\n");
		sql.append("union all\n");
		sql.append("select tt.GROUP_ID,tt.SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,0 as DEALER_ID,'' as DEALER_NAME,1 as ALLOT_STATUS,sum(tt.SALE_AMOUNT) SALE_AMOUNT,sum(tt.NUM1) NUM1,sum(tt.NUM2) NUM2,sum(tt.NUM22) NUM22,sum(tt.NUM3) NUM3,sum(tt.NUM33) NUM33,sum(tt.NUM4) NUM4,sum(tt.NUM5) NUM5,sum(tt.GAP) GAP,sum(tt.ALLOT_NUM) ALLOT_NUM,SUM(TT2.ALLOT_MONTH_NUM) ALLOT_MONTH_NUM\n");
		sql.append("    from \n");
		sql.append("   (select  t1.DEALER_ID,t1.GROUP_ID,'TOTAL' as SERIES_NAME,(case when t2.ALLOT_STATUS>0 then t1.SALE_AMOUNT else 0 end) SALE_AMOUNT,(case when t2.ALLOT_STATUS>0 then t1.NUM1 else 0 end) NUM1,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then t1.NUM2 else 0 end) NUM2,(case when t2.ALLOT_STATUS>0 then t1.NUM22 else 0 end) NUM22,(case when t2.ALLOT_STATUS>0 then t1.NUM3 else 0 end) NUM3,(case when t2.ALLOT_STATUS>0 then t1.NUM33 else 0 end) NUM33,(case when t2.ALLOT_STATUS>0 then t1.NUM4 else 0 end) NUM4,\n");
		sql.append("         		(case when t2.ALLOT_STATUS>0 then t1.NUM5 else 0 end) NUM5,(case when t2.ALLOT_STATUS>0 then t1.GAP else 0 end) GAP,(case when t2.ALLOT_STATUS>0 then t2.ALLOT_NUM else 0 end) ALLOT_NUM\n");
		sql.append("   (select  t1.DEALER_ID,t1.GROUP_ID,'TOTAL' as SERIES_NAME,t1.SALE_AMOUNT,t1.NUM1,\n");
		sql.append("         		t1.NUM2,t1.NUM22,t1.NUM3,t1.NUM33,t1.NUM4,\n");
		sql.append("         		t1.NUM5,t1.GAP,t2.ALLOT_NUM\n");		
		sql.append("  			from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,td.DEALER_ID,td.DEALER_SHORTNAME DEALER_NAME,ttg.SALE_AMOUNT,nvl(ttg.NUM1,0) NUM1,nvl(ttg.NUM2,0) NUM2,nvl(ttg.NUM22,0) NUM22,nvl(ttg.NUM3,0) NUM3,nvl(ttg.NUM33,0) NUM33,nvl(ttg.NUM4,0) NUM4,nvl(ttg.NUM5,0) NUM5,ttg.GAP\n");
		sql.append("      	  			from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               			 TM_ORG         			tor,\n");
		sql.append("     		   			 TM_DEALER                td,\n");
		sql.append("			   			 TM_DEALER_ORG_RELATION   tdor,\n");
		sql.append("  			   			 TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           			where ttg.TARGET_ID=td.DEALER_ID\n");
		sql.append("		     		  and td.DEALER_ID= tdor.DEALER_ID\n");
		sql.append("			 		  and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    		 		  and ttg.GROUP_ID=tvmg.GROUP_ID\n");		
		sql.append("   		    		  and tvmg.GROUP_LEVEL=2\n");
		sql.append("             		  and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		if(!groupId.equals("")){
			sql.append("			 		  and ttg.GROUP_ID in("+groupId+")\n");
		}
		if(!orgId.equals("")){
			sql.append("					  and tor.ORG_ID="+orgId+"\n");
		}
		sql.append("			 		  and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("             		  and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+") t1\n");
		//sql.append(" inner join");
		sql.append(" left  join");
		sql.append("  		    (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(tar.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("   						 from TM_ALLOT_RESOURCE              tar,\n");
		sql.append("					 		  TM_DEALER                      td,\n");
		sql.append("					 		  TM_ORG                		 tor,\n");
		sql.append("			   		 		  TM_DEALER_ORG_RELATION   		 tdor,\n");
		sql.append("   					 		  TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  							  TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 		  TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   						 where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
		sql.append("			      		   and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("				  		   and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("				  		   and tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  		   and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  		   and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("             	  		   and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("  				  		   and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("			 	  		   and tar.ALLOT_DATE='"+allotDate+"'\n");
		if(!orgId.equals("")){
			sql.append("						and tor.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 		    and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(auditType.equals("0")){
			sql.append("	 					and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 					and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append(" 					     group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS) t2\n");
		sql.append("  			on t1.GROUP_ID=t2.GROUP_ID and t1.DEALER_ID = t2.ALLOT_TARGET_ID \n");
		//sql.append("  			group by t1.GROUP_ID,t2.ALLOT_STATUS\n");
		sql.append(") tt\n");
		sql.append("    left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,nvl(tar.ALLOT_STATUS,0) ALLOT_STATUS,sum(nvl(tar.ALLOT_NUM,0)) ALLOT_MONTH_NUM\n");
   		sql.append("				 from TM_ALLOT_RESOURCE  tar, TM_DEALER   td, TM_ORG tor,TM_DEALER_ORG_RELATION tdor,TM_VHCL_MATERIAL_GROUP tvmg4,\n");
   		sql.append("				  TM_VHCL_MATERIAL_GROUP tvmg3,TM_VHCL_MATERIAL_GROUP tvmg2 where tar.ALLOT_TARGET_ID=td.DEALER_ID\n");
   		sql.append("      		   and td.DEALER_ID=tdor.DEALER_ID and tor.ORG_ID=tdor.ORG_ID and tar.GROUP_ID=tvmg4.GROUP_ID and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
   		sql.append("	  		   and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID and tor.PARENT_ORG_ID="+loginUser.getOrgId()+" and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+" and tar.ALLOT_DATE LIKE '%"+allotMonthDate+"%'\n");
   		if(!orgId.equals("")){
			sql.append("						and tor.ORG_ID="+orgId+"\n");
		}
		if(!groupId.equals("")){
			sql.append("			 		    and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		if(auditType.equals("0")){
			sql.append("	 					and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 					and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 					and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		 
	 	sql.append("				        group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,tar.ALLOT_STATUS\n");
        sql.append("          ) tt2 on tt.group_id=tt2.group_id and tt.DEALER_ID = tt2.ALLOT_TARGET_ID \n");

		sql.append("	group by tt.GROUP_ID,tt.SERIES_NAME) t\n");
		sql.append(" order by t.ORG_NAME");	
		
		List<Map> list=factory.select(sql.toString(),null,new DAOCallback<Map>(){
			public Map wrapper(ResultSet rs, int arg1) {
				try {
					Map map = new HashMap();
					map.put("SERIES_ID", rs.getString("GROUP_ID"));
					map.put("SERIES_NAME", rs.getString("SERIES_NAME"));
					map.put("ORG_ID", rs.getLong("ORG_ID"));
					map.put("ORG_NAME", rs.getString("ORG_NAME"));
					map.put("DEALER_ID", rs.getString("DEALER_ID"));
					map.put("DEALER_NAME", rs.getString("DEALER_NAME"));
					map.put("ALLOT_STATUS", rs.getString("ALLOT_STATUS"));
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
					map.put("ALLOT_MONTH_NUM", rs.getInt("ALLOT_MONTH_NUM"));
					map.put("RATE", rs.getFloat("RATE"));
					map.put("DRATE", rs.getFloat("DRATE"));
					return map;
				} catch (SQLException e) {
					throw new DAOException(e);
				}
			}});
		return list;
	}*/
	public List<Map> findResourceAllotListBySeries2(String conditionWhere,String auditType,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,tvmg2.GROUP_NAME SERIES_NAME,sum(tar.ADJUST_NUM) SER_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");		
		sql.append("    having sum(tar.ADJUST_NUM)>0\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	public List<Map> findResourceAllotListByColor2(String conditionWhere,String auditType,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID SERIES_ID,'ORG_TOTAL' SERIES_NAME,tvmg4.GROUP_ID,tor.ORG_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");;
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}		
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tor.ORG_ID,tar.COLOR_NAME\n");
		sql.append("union all\n");
		sql.append("select tvmg2.GROUP_ID SERIES_ID,'TOTAL' SERIES_NAME,tvmg4.GROUP_ID, 0 as ORG_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME,sum(tar.ADJUST_NUM) COLOR_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_DEALER                  td,\n");
		sql.append("		TM_ORG                	   tor,\n");
		sql.append("	    TM_DEALER_ORG_RELATION     tdor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");;
		if(!conditionWhere.equals("")){
			sql.append("	 "+conditionWhere+"\n");
		}
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_02+"\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> getAllotDate(LoginInfoDto loginUser,String allotStatus) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct ALLOT_DATE\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
		sql.append("        TM_ORG                   tor\n");
		sql.append("   where tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_SMALLREGION+"\n");
		sql.append(" 	 and tar.ALLOT_STATUS="+allotStatus);
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	public List<Map> resourceAllotAuditCheck(String allotDate,LoginInfoDto loginUser) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct tor.ORG_ID,tor.ORG_NAME,ALLOT_STATUS,tar.AUDIT_TYPE\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     		   tar,\n");
		sql.append("        TM_ORG                		   tor,\n");
		sql.append("   		TM_DEALER                      td,\n");
		sql.append("	 	TM_DEALER_ORG_RELATION         tdor,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("	 and tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("	 and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("	 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tor.PARENT_ORG_ID="+loginUser.getOrgId()+"\n");
		sql.append("	 and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append("	 and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_03+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> getAuditAllotDate(LoginInfoDto loginUser,String auditType) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct ALLOT_DATE\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS        tar,\n");
		sql.append("        TM_ORG                	 tor,\n");
		sql.append("   		TM_DEALER                td,\n");
		sql.append("	 	TM_DEALER_ORG_RELATION   tdor\n");
		sql.append("   where tar.ALLOT_TARGET_ID = td.DEALER_ID\n");
		sql.append("	 and tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("	 and td.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("     and tor.PARENT_ORG_ID = "+loginUser.getOrgId()+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_DEALER+"\n");
		sql.append(" 	 and tar.ALLOT_STATUS=1\n");
		if(auditType.equals("0")){
			sql.append("	 and tar.AUDIT_TYPE="+OemDictCodeConstants.ORDER_ADJUST_TYPE_02+"\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+")\n");
		}else{
			sql.append("	 and tar.AUDIT_TYPE in("+OemDictCodeConstants.ORDER_ADJUST_TYPE_01+","+OemDictCodeConstants.ORDER_ADJUST_TYPE_04+")\n");
			sql.append("	 and tar.AUDIT_STATUS in("+OemDictCodeConstants.ORDER_ADJUST_STATIS_01+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_02+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_03+","+OemDictCodeConstants.ORDER_ADJUST_STATIS_04+")\n");
		}	
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> findTime() {
		String sql = "select TM_ALLOT_STATUS_ID,timestampdiff(4,char(sysdate-CREATE_DATE)) DIF_TIME from TM_ALLOT_STATUS where ATYPE=1 and STATUS=0";
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/**
	 * @author huyu
	 * 查询大区下面小区的邮箱
	 */
	public List<Map> findAreaBigAndAreaSmallByEmail(Long org,String allotDate){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct user.email,user.name,TOR3.ORG_NAME smallOrg,tar.ALLOT_TYPE,tar.ALLOT_STATUS,pose.ORG_ID from TC_POSE pose  \n") ;
		sql.append("    inner join TR_USER_POSE tup on pose.POSE_ID = tup.POSE_ID\n") ;
		sql.append("	inner join TC_USER user on tup.USER_ID = user.USER_ID\n") ; 
		sql.append("	inner join TM_ORG TOR2 on (tor2.org_id = "+org+" AND TOR2.ORG_LEVEL = 2 ) \n") ; 
		sql.append("	inner join TM_ORG TOR3 on (tor3.org_id = pose.ORG_ID and TOR3.PARENT_ORG_ID = TOR2.ORG_ID and TOR3.ORG_LEVEL = 3 ) \n") ; 
		sql.append("	inner join TM_ALLOT_RESOURCE_DCS tar on tar.ALLOT_TARGET_ID = TOR3.ORG_ID \n") ; 
		sql.append("	where tar.ALLOT_DATE="+ allotDate +" \n") ;
		sql.append("	and tar.ADJUST_NUM>0\n") ;
		sql.append("	and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_SMALLREGION +"\n") ;
		sql.append("	and user.email <> '' and user.EMAIL <> 'chrysler-dms@yonyou.com' \n") ;
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list ; 
	}
	
	/**
	 * 查询所分配的车系
	 * @param allotDate
	 * @author huyu
	 * @return
	 */
	public List<Map> findSeriesName(String allotDate,String orgId){
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct(case when tt2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tt2.GROUP_NAME end) SERIES_NAME\n");
		sql.append("	from TM_ALLOT_RESOURCE_DCS tar,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt ,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt1,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt2\n");
		sql.append("	where tt.PARENT_GROUP_ID = tt1.GROUP_ID \n");
		sql.append("		and tt1.PARENT_GROUP_ID = tt2.GROUP_ID \n");
		sql.append("		and tar.GROUP_ID = tt.GROUP_ID \n");
		sql.append("		and tt2.STATUS=10011001  AND tt2.GROUP_LEVEL=2 \n");
		sql.append("		and tar.ALLOT_TARGET_ID = "+ orgId +"\n") ;
		sql.append("		and tar.ALLOT_DATE="+ allotDate +" \n") ;
		sql.append("		and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_SMALLREGION +"\n") ;
		sql.append("		and tar.ADJUST_NUM>0\n") ;
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);;
		return list;
	}
}
