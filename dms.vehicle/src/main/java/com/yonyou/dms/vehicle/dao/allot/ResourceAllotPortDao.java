package com.yonyou.dms.vehicle.dao.allot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
@SuppressWarnings("rawtypes")
public class ResourceAllotPortDao extends OemBaseDAO {

	public List<Map> resourceallotportInt() {
		String sql = "select tc.CODE_DESC,tap.PORT_LEVEL,tap.TM_ALLOT_PORT_ID,tap.VPC_PORT from TM_ALLOT_PORT tap left join TC_CODE_DCS tc on tc.CODE_ID = tap.VPC_PORT and tc.type='1392'";
		return OemDAOUtil.findAll(sql, null);
	}

	public Map findById(Long id) {
		String sql = "select tc.CODE_DESC,tap.PORT_LEVEL,tap.TM_ALLOT_PORT_ID,tap.VPC_PORT from TM_ALLOT_PORT tap left join TC_CODE_DCS tc on tc.CODE_ID = tap.VPC_PORT and tc.type='1392' where TM_ALLOT_PORT_ID="
				+ id;
		return OemDAOUtil.findFirst(sql, null);
	}
	
	public List<Map> findTime() {
		String sql = "SELECT TM_ALLOT_STATUS_ID,TIMESTAMPDIFF(MINUTE,NOW(),CREATE_DATE) DIF_TIME FROM TM_ALLOT_STATUS WHERE ATYPE=3 AND STATUS=0";
		return OemDAOUtil.findAll(sql, null);
	}

	public void update(String id) {
		List<Object> params = new ArrayList<>();
		String sql = "update TM_ALLOT_STATUS set STATUS=1,UPDATE_BY=1112,UPDATE_DATE=NOW() where TM_ALLOT_STATUS_ID= ? ";
		params.add(id);
		OemDAOUtil.execBatchPreparement(sql, params);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<Map> findRepeatData() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select p1.ROW_NO,p1.VIN\n");
		sql.append("  from TMP_UPLOAD_RESOURCE p1\n");
		sql.append("    where p1.VIN<>''\n");
		sql.append("    and exists (select 1\n");
		sql.append("    			from TMP_UPLOAD_RESOURCE p2\n");
		sql.append("    			where p1.VIN = p2.VIN\n");
		sql.append("     			  and p1.ROW_NO <> p2.ROW_NO)\n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 判断是否在车辆表中存在
	 * @return
	 */
	public List<Map> findNoExistsVin() {
		return OemDAOUtil.findAll("select t.ROW_NO,t.VIN from TMP_UPLOAD_RESOURCE t where not exists(select 1 from TM_VEHICLE_DEC tv where tv.VIN=t.VIN) ", null);
	}
	
	/**
	 * 是否在车厂库存
	 * @return
	 */
	public List<Map> checkVinIsStore() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tur.ROW_NO,tur.VIN from TMP_UPLOAD_RESOURCE tur,TM_VEHICLE_DEC tv\n");
		sql.append("   where  tur.VIN=tv.VIN\n");
		sql.append("     and  tv.LIFE_CYCLE!="+OemDictCodeConstants.LIF_CYCLE_02+"\n");
		sql.append("   order by tur.ROW_NO \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 是否生成有效订单
	 * @return
	 */
	public List<Map> findIsOrder() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tur.VIN,tur.ROW_NO\n");
		sql.append("   from TMP_UPLOAD_RESOURCE    tur,\n");
		sql.append("    	TM_VEHICLE_DEC          tv,\n");
		sql.append("        TT_VS_ORDER            tvo\n");
		sql.append("   where tur.VIN=tv.VIN\n");
		sql.append("     and tv.VIN=tvo.VIN\n");
		sql.append("     and  tvo.ORDER_STATUS<"+OemDictCodeConstants.ORDER_STATUS_08+"\n");
		sql.append("   order by tur.ROW_NO \n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 是否存在公共资源池中
	 * @return
	 */
	public List<Map> findIsCommon() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tur.VIN,tur.ROW_NO\n");
		sql.append("   from TMP_UPLOAD_RESOURCE            tur,\n");
		sql.append("    	TM_VEHICLE_DEC                 tv,\n");
		sql.append("        TT_VS_COMMON_RESOURCE          tvcr,\n");
		sql.append("        TT_VS_COMMON_RESOURCE_DETAIL   tvcrd");
		sql.append("   where tur.VIN=tv.VIN\n");
		sql.append("     and tv.VEHICLE_ID = tvcr.VEHICLE_ID");
		sql.append("	 and tvcr.COMMON_ID = tvcrd.COMMON_ID");
		sql.append("     and tvcrd.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append("     and tvcr.STATUS="+OemDictCodeConstants.COMMON_RESOURCE_STATUS_02+"\n");
		sql.append("     and tv.NODE_STATUS in("+OemDictCodeConstants.VEHICLE_NODE_18+"\n)");
		sql.append("     and not exists(select 1 from TT_VS_ORDER where VIN=TV.VIN and ORDER_STATUS<"+OemDictCodeConstants.ORDER_STATUS_08+")\n");
		sql.append("   order by tur.ROW_NO \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 月度目标是否存在
	 * @return
	 */
	public List<Map> findMonthPlanNotExists() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select TVMG2.GROUP_ID,TVMG2.GROUP_NAME,TUR.ROW_NO,tur.VIN\n");
		sql.append("   from TMP_UPLOAD_RESOURCE            TUR,\n");
		sql.append("   		TM_VEHICLE_DEC                 TV,\n");
		sql.append("      	TM_VHCL_MATERIAL               TVM,\n");
		sql.append("    	TM_VHCL_MATERIAL_GROUP_R       TVMGR,\n");
		sql.append("    	TM_VHCL_MATERIAL_GROUP         TVMG4,\n");
		sql.append("     	TM_VHCL_MATERIAL_GROUP         TVMG3,\n");
		sql.append("   		TM_VHCL_MATERIAL_GROUP         TVMG2\n");
		sql.append("   where TUR.VIN = TV.VIN\n");
		sql.append("	 and TV.MATERIAL_ID = TVM.MATERIAL_ID\n");
		sql.append("	 and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID\n");
		sql.append("	 and TVMGR.GROUP_ID = TVMG4.GROUP_ID\n");
		sql.append("	 and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID\n");
		sql.append("	 and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID\n");
		sql.append(" 	 and TVMG4.GROUP_LEVEL = 4\n");
		sql.append("	 and TVMG3.GROUP_LEVEL = 3\n");
		sql.append(" 	 and TVMG2.GROUP_LEVEL = 2\n");
		sql.append(" 	 and TVMG2.GROUP_ID not in(select distinct TVMPD.MATERIAL_GROUPID\n");
		sql.append(" 			from TT_VS_MONTHLY_PLAN             TVMP,\n");
		sql.append("				 TT_VS_MONTHLY_PLAN_DETAIL      TVMPD,\n");
		sql.append("				 TM_DEALER                      TD,\n");
		sql.append(" 				 TM_ORG                         TOR2,\n");
		sql.append("  				 TM_ORG                         TOR,\n");
		sql.append("				 TM_DEALER_ORG_RELATION         TDOR\n");
		sql.append(" 		    where TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		sql.append("   			  and TVMP.DEALER_ID = TD.DEALER_ID\n");
		sql.append(" 			  and TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("			  and TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append(" 			  and TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("			  and TVMP.PLAN_TYPE = 20781001\n");
		sql.append("  			  and TVMP.PLAN_YEAR = year(now())\n");
		sql.append("			  and TVMP.PLAN_MONTH = month(now())\n");
		sql.append("			  and TVMP.TASK_ID is null\n");
		sql.append("			  and TVMP.PLAN_VER = (select max(PLAN_VER) from TT_VS_MONTHLY_PLAN where PLAN_TYPE=20781001 and PLAN_YEAR=year(now()) and PLAN_MONTH=month(now()) and TASK_ID is null)\n");
		sql.append("			group by  TVMPD.MATERIAL_GROUPID)\n");
		sql.append("   order by TUR.ROW_NO \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 月度目标是否为0
	 * @return
	 */
	public List<Map> findMonthPlanNotExists2() {
		
		StringBuffer sql = new StringBuffer("");
		sql.append("select TVMG2.GROUP_ID,TVMG2.GROUP_NAME,TUR.ROW_NO,tur.VIN\n");
		sql.append("   from TMP_UPLOAD_RESOURCE            TUR,\n");
		sql.append("   		TM_VEHICLE_DEC                 TV,\n");
		sql.append("      	TM_VHCL_MATERIAL               TVM,\n");
		sql.append("    	TM_VHCL_MATERIAL_GROUP_R       TVMGR,\n");
		sql.append("    	TM_VHCL_MATERIAL_GROUP         TVMG4,\n");
		sql.append("     	TM_VHCL_MATERIAL_GROUP         TVMG3,\n");
		sql.append("   		TM_VHCL_MATERIAL_GROUP         TVMG2\n");
		sql.append("   where TUR.VIN = TV.VIN\n");
		sql.append("	 and TV.MATERIAL_ID = TVM.MATERIAL_ID\n");
		sql.append("	 and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID\n");
		sql.append("	 and TVMGR.GROUP_ID = TVMG4.GROUP_ID\n");
		sql.append("	 and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID\n");
		sql.append("	 and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID\n");
		sql.append(" 	 and TVMG4.GROUP_LEVEL = 4\n");
		sql.append("	 and TVMG3.GROUP_LEVEL = 3\n");
		sql.append(" 	 and TVMG2.GROUP_LEVEL = 2\n");
		sql.append(" 	 and exists(select distinct TVMPD.MATERIAL_GROUPID,sum(TVMPD.SALE_AMOUNT)\n");
		sql.append(" 			from TT_VS_MONTHLY_PLAN             TVMP,\n");
		sql.append("				 TT_VS_MONTHLY_PLAN_DETAIL      TVMPD,\n");
		sql.append("				 TM_DEALER                      TD,\n");
		sql.append(" 				 TM_ORG                         TOR2,\n");
		sql.append("  				 TM_ORG                         TOR,\n");
		sql.append("				 TM_DEALER_ORG_RELATION         TDOR\n");
		sql.append(" 		    where TVMP.PLAN_ID = TVMPD.PLAN_ID\n");
		sql.append("   			  and TVMP.DEALER_ID = TD.DEALER_ID\n");
		sql.append(" 			  and TD.DEALER_ID = TDOR.DEALER_ID\n");
		sql.append("			  and TOR.ORG_ID = TDOR.ORG_ID\n");
		sql.append(" 			  and TOR.PARENT_ORG_ID = TOR2.ORG_ID\n");
		sql.append("			  and TVMP.PLAN_TYPE = 20781001\n");
		sql.append(" 			  and TVMG2.GROUP_ID=TVMPD.MATERIAL_GROUPID\n");
		sql.append("  			  and TVMP.PLAN_YEAR = year(now())\n");
		sql.append("			  and TVMP.PLAN_MONTH = month(now())\n");
		sql.append("			  and TVMP.PLAN_VER = (select max(PLAN_VER) from TT_VS_MONTHLY_PLAN where PLAN_TYPE=20781001 and PLAN_YEAR=year(now()) and PLAN_MONTH=month(now())  and TASK_ID is null)\n");
		sql.append("			group by  TVMPD.MATERIAL_GROUPID having sum(TVMPD.SALE_AMOUNT)=0)\n");
		sql.append("	 and not exists(select 1 from TM_ALLOT_GRAND where GRAND_TYPE=30 and GRAND_ID=TVMG2.GROUP_ID)\n");
		sql.append("   order by TUR.ROW_NO \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 判断是否在资源上传临时表
	 * @return
	 */
	public List<Map> findExistsCommon() {
		return OemDAOUtil.findAll("select t.ROW_NO,t.VIN from TMP_UPLOAD_RESOURCE t where exists (select 1 from TT_VS_UPLOAD_COMMON_RESOURCE tr where tr.VIN=t.VIN and tr.IS_ORDER in(0,1))", null);
	}
	
	/**
	 * 港口是否为空
	 * @return
	 */
	public List<Map> findVpcPortIsNull() {
    	StringBuffer sql = new StringBuffer("");
    	sql.append(" select * from TMP_UPLOAD_RESOURCE where vpc_port is null \n");
    	return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 港口是否维护了相应区域
	 * @return
	 */
	public List<Map> getVpcPort() {
		StringBuffer sql = new StringBuffer("");
    	sql.append(" select distinct vpc_port from TMP_UPLOAD_RESOURCE \n");
    	return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public List<Map> getVpcPortOrgId(String portId) {
		StringBuffer sql = new StringBuffer("");
    	sql.append("   select * from  TM_ALLOT_AREA where port_id='"+portId+"' \n");
    	return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public List<Map> getVpcPortDesc(String portId) {
		StringBuffer sql = new StringBuffer("");
    	sql.append("   select * from tc_code_dcs where   code_id='"+portId+"' \n");
    	return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 判断是否是进口车
	 * @return
	 */
	public List<Map> findIsNotImport() {
		String sql="select * from TMP_UPLOAD_RESOURCE tur where exists(select 1 from ("+getVwMaterialSql()+") vm,TM_VEHICLE_DEC tv where vm.MATERIAL_ID=tv.MATERIAL_ID and tv.VIN=tur.VIN and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+")";
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 批量执行修改操作
	 */
	public void updateStatus() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		OemDAOUtil.execBatchPreparement("update TM_ALLOT_STATUS set STATUS=1,UPDATE_BY="+loginInfo.getUserId()+",UPDATE_DATE=NOW() where ATYPE=3 and STATUS=0", new ArrayList<>());
	}
	
	public List<Map> findOrderAllot() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct date_format(CREATE_DATE,'yyyyMMdd') CREATE_DATE from TMP_UPLOAD_RESOURCE");
		sql.append("  where DEAL=1 and ALLOT=0 and CREATE_BY="+loginInfo.getUserId()+"\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public void updateDeal() {
		OemDAOUtil.execBatchPreparement(" update TMP_UPLOAD_RESOURCE set DEAL=1 ", new ArrayList<>());
		
	}

	public List<Map> callProcedureMonth(String month_day_start, String month_day_end, String lately_month_day_start,
			String lately_month_day_end) {
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select  TD.DEALER_ID,TV.VIN \n");
		sql.append("                 from TT_VS_ORDER                    TVO, \n");
		sql.append("                       TM_VEHICLE_DEC                 TV, \n");
		sql.append("                       TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                       TM_DEALER                      TD, \n");
		sql.append("                       TM_ORG                         TOR2, \n");
		sql.append("                       TM_ORG                         TOR, \n");
		sql.append("                       TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                       TM_ALLOT_SUPPORT               TAS \n");
		sql.append("                  where TVO.VIN = TV.VIN \n");
		sql.append("                    and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                    and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                    and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                    and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                    and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("                    and TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                    and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                    and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                    and TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                    and TV.VIN = TAS.VIN \n");
		sql.append("                    and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                    and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                    and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                    and TVO.ORDER_STATUS<20071008                  \n");
		sql.append("                    and TVO.CREATE_DATE<'"+lately_month_day_end+"' \n");
		sql.append("                    and TVO.ORDER_TYPE=20831003 \n");
		sql.append("                    and TV.NODE_STATUS in(11511018,11511019) \n");
		sql.append("                    and TAS.IS_SUPPORT=1 \n");
		sql.append("             union \n");
		sql.append("             select  TD.DEALER_ID,TV.VIN \n");
		sql.append("                 from TT_VS_ORDER                    TVO, \n");
		sql.append("                       TM_VEHICLE_DEC                 TV, \n");
		sql.append("                       TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                       TM_DEALER                      TD, \n");
		sql.append("                       TM_ORG                         TOR2, \n");
		sql.append("                       TM_ORG                         TOR, \n");
		sql.append("                       TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                       TM_ALLOT_SUPPORT               TAS \n");
		sql.append("                  where TVO.VIN = TV.VIN \n");
		sql.append("                    and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                    and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                    and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                    and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                    and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("                    and TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                    and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                    and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                    and TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                    and TV.VIN = TAS.VIN \n");
		sql.append("                    and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                    and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                    and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                    and TVO.ORDER_STATUS<20071008              \n");
		sql.append("                    and TVO.ORDER_TYPE=20831003 \n");
		sql.append("                    and TVO.CREATE_DATE<'"+lately_month_day_end+"' \n");
		sql.append("                    and TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("                    and TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("                    and TAS.IS_SUPPORT=1 \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取前置游标数据
	 * @return
	 */
	public List<Map> getMycursor() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tt1.group_id,tt1.group_code,IFNULL(tt2.num,0) num FROM \n");
		sql.append("                 (SELECT group_id,group_code FROM Tm_Vhcl_Material_Group \n");
		sql.append("                     WHERE 1=1  AND STATUS=10011001  AND Group_Level=2 \n");
		sql.append("                 )tt1 \n");
		sql.append("               LEFT JOIN \n");
		sql.append("                 (SELECT TVMG2.GROUP_ID,TVMG2.GROUP_CODE,COUNT(*) num FROM TMP_UPLOAD_RESOURCE  TUR, \n");
		sql.append("                   TM_VEHICLE_dec  TV,TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                   WHERE \n");
		sql.append("                   TUR.VIN = TV.VIN \n");
		sql.append("                     AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                     AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                     AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                     AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                     AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                     AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                     AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                     AND TVMG2.GROUP_LEVEL = 2 AND tvmg2.status=10011001 \n");
		sql.append("                       GROUP BY TVMG2.GROUP_ID,TVMG2.GROUP_CODE,TVMG2.GROUP_name \n");
		sql.append("                     ) tt2 \n");
		sql.append("                  ON tt1.group_code =tt2.GROUP_CODE \n");
		sql.append("                  ORDER BY NUM \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 获取前置游标数据2
	 * @return
	 */
	public List<Map> getMycursor1() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVMG2.GROUP_ID,TVMG2.GROUP_CODE FROM TMP_UPLOAD_RESOURCE  TUR, \n");
		sql.append("                   TM_VEHICLE_dec  TV,TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                   TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                   WHERE \n");
		sql.append("                   TUR.VIN = TV.VIN \n");
		sql.append("                     AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                     AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                     AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                     AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                     AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                     AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                     AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                     AND TVMG2.GROUP_LEVEL = 2 AND tvmg2.status=10011001 \n");
		sql.append("                       GROUP BY TVMG2.GROUP_ID,TVMG2.GROUP_CODE,TVMG2.GROUP_name \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取最大月计划任务版本
	 * @param year
	 * @param month
	 * @return
	 */
	public int getMaxPalyVer(Integer year, Integer month) {
		StringBuffer sql = new StringBuffer("\n");
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT MAX(PLAN_VER) PLAN_VER FROM TT_VS_MONTHLY_PLAN  \n");
		sql.append(" WHERE PLAN_TYPE=20781001 AND PLAN_YEAR=? AND PLAN_MONTH=? AND task_id IS NULL \n");
		params.add(year);
		params.add(month);
		Map map = OemDAOUtil.findFirst(sql.toString(), params);
		if(null!=map){
			int planVer = Integer.parseInt(CommonUtils.checkNull(map.get("PLAN_VER"), "0"));
			return planVer;
		}else{
			return 0;
		}
	}
	
	/**
	 * 1转
	 * @param month_day_start
	 * @param month_day_end
	 * @param year
	 * @param month
	 * @param s_series_id
	 * @param s_series_code
	 * @param inParameter
	 * @param playVer
	 * @return
	 */
	public void save(String month_day_start, String month_day_end, Integer year, Integer month,
			long s_series_id, List<Object> inParameter, int playVer) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" INSERT INTO TM_TOTAL_GAP(GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,NUM1,NUM2,NUM22,NUM3,NUM33,NUM4,NUM5,GAP,ALLOT_DATE,CREATE_BY,CREATE_DATE) \n");
		sql.append("SELECT 10431003,t.GROUP_ID,t.ORG_ID,t.SALE_AMOUNT,t.NUM1,t.NUM2,t.NUM22,t.NUM3,t.NUM33,t.NUM4,t.NUM5, \n");
		sql.append("(CASE WHEN t.GAP>0 THEN t.GAP ELSE 0 END) GAP,'"+inParameter.get(1)+"','"+inParameter.get(0)+"',NOW() \n");
		sql.append("FROM ( \n");
		sql.append("  SELECT tab.GROUP_ID,tab.ORG_ID,tab.SALE_AMOUNT,tab1.NUM1,tab2.NUM2,tab22.NUM22,tab3.NUM3,tab33.NUM33,tab4.NUM4,tab5.NUM5, \n");
		sql.append("                                      (tab.SALE_AMOUNT-IFNULL(tab1.NUM1,0)-IFNULL(tab2.NUM2,0)-IFNULL(tab3.NUM3,0)-IFNULL(tab33.NUM33,0)-IFNULL(tab4.NUM4,0)-IFNULL(tab5.NUM5,0)) AS GAP \n");
		sql.append("    FROM ( \n");
		sql.append("    SELECT  TVMPD.MATERIAL_GROUPID GROUP_ID,TOR2.ORG_ID,SUM(TVMPD.SALE_AMOUNT) SALE_AMOUNT \n");
		sql.append("                                   FROM TT_VS_MONTHLY_PLAN             TVMP, \n");
		sql.append("                                         TT_VS_MONTHLY_PLAN_DETAIL      TVMPD, \n");
		sql.append("                                         TM_DEALER                      TD, \n");
		sql.append("                                         TM_ORG                         TOR2, \n");
		sql.append("                                         TM_ORG                         TOR, \n");
		sql.append("                                         TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                                         TM_VHCL_MATERIAL_GROUP         TVMG \n");
		sql.append("                                   WHERE TVMP.PLAN_ID = TVMPD.PLAN_ID \n");
		sql.append("                                     AND TVMP.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                     AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                     AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                     AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                                     AND TVMPD.MATERIAL_GROUPID = TVMG.GROUP_ID \n");
		sql.append("                                     AND TVMP.PLAN_TYPE=20781001 \n");
		sql.append("                                     AND TVMP.PLAN_YEAR= '"+year+"' \n");
		sql.append("                                     AND TVMP.PLAN_MONTH = '"+month+"' \n");
		sql.append("                                     AND TVMP.PLAN_VER= '"+playVer+"' \n");
		sql.append("                                     AND tvmp.task_id IS NULL \n");
		sql.append("                                     AND TVMPD.MATERIAL_GROUPID= '"+s_series_id+"' \n");
		sql.append("                                     AND NOT EXISTS(SELECT 1 FROM TM_RESOURCE_ALLOT_FROZEN t \n");
		sql.append("     WHERE t.DEALER_ID=TD.DEALER_ID AND STATUS=19990001 \n");
		sql.append("       AND t.SERIES_NAME=TVMG.GROUP_NAME \n");
		sql.append("       AND t.GROUP_CODE='' AND t.GROUP_NAME='' AND t.MODEL_YEAR='' AND t.COLOR_CODE='' AND t.COLOR_NAME='') \n");
		sql.append("                                   GROUP BY  TVMPD.MATERIAL_GROUPID, TOR2.ORG_ID \n");
		sql.append("  ) tab LEFT JOIN ( \n");
		sql.append("        SELECT t.GROUP_ID,t.ORG_ID,SUM(t.NUM) NUM1 \n");
		sql.append("                                FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,COUNT(1) NUM \n");
		sql.append("                                        FROM TM_LATELY_MONTH                TLM, \n");
		sql.append("                                              TM_VEHICLE_DEC                     TV, \n");
		sql.append("                                              TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                                              TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                                              TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                                              TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                                              TM_VHCL_MATERIAL_GROUP         TVMG2,                     \n");
		sql.append("                                              TM_DEALER                      TD, \n");
		sql.append("                                              TM_ORG                         TOR2, \n");
		sql.append("                                              TM_ORG                         TOR, \n");
		sql.append("                                              TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                                              TM_ALLOT_SUPPORT               TAS \n");
		sql.append("                                        WHERE TLM.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                          AND TLM.VIN = TV.VIN \n");
		sql.append("                                          AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                          AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                          AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                          AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                          AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                          AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                          AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                          AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                                          AND TLM.VIN = TAS.VIN \n");
		sql.append("                                          AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                                          AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                                          AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                                          AND TLM.R_TYPE=10431005 \n");
		sql.append("                                          AND TAS.IS_SUPPORT=1 \n");
		sql.append("                                          AND TLM.ALLOT_DATE='"+inParameter.get(1)+"' \n");
		sql.append("                                          AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                       GROUP BY TVMG2.GROUP_ID,TOR2.ORG_ID \n");
		sql.append("                            UNION ALL \n");
		sql.append("                            SELECT TVMG2.GROUP_ID,TOR.ORG_ID,COUNT(1) NUM \n");
		sql.append("                                    FROM TM_LATELY_MONTH                TLM, \n");
		sql.append("                                          TM_VEHICLE_DEC                     TV, \n");
		sql.append("                                          TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                                          TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                                          TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                                          TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                                          TM_VHCL_MATERIAL_GROUP         TVMG2,                     \n");
		sql.append("                                          TM_ORG                         TOR, \n");
		sql.append("                                          TM_ALLOT_SUPPORT               TAS \n");
		sql.append("                                    WHERE TLM.DEALER_ID = TOR.ORG_ID \n");
		sql.append("                                      AND TLM.VIN = TV.VIN \n");
		sql.append("                                      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID   \n");
		sql.append("                                      AND TLM.VIN = TAS.VIN \n");
		sql.append("                                      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                                      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                                      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                                      AND TLM.R_TYPE=10431003 \n");
		sql.append("                                      AND TAS.IS_SUPPORT=1 \n");
		sql.append("                                      AND TLM.ALLOT_DATE='"+inParameter.get(1)+"' \n");
		sql.append("                                      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                    GROUP BY TVMG2.GROUP_ID,TOR.ORG_ID) t \n");
		sql.append("                                GROUP BY t.GROUP_ID,t.ORG_ID \n");
		sql.append(" \n");
		sql.append("  ) tab1 ON tab1.ORG_ID=tab.ORG_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(1) NUM2 \n");
		sql.append("                                    FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append("                                               FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("   FROM TT_VS_ORDER                    TVO, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("         TM_DEALER                      TD, \n");
		sql.append("         TM_ORG                         TOR2, \n");
		sql.append("         TM_ORG                         TOR, \n");
		sql.append("         TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD \n");
		sql.append("    WHERE TVO.VIN = TV.VIN \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("      AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("      AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("      AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("      AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("      AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("      AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVCR.STATUS = 20801002 \n");
		sql.append("      AND TVCR.TYPE = 20811002 \n");
		sql.append("      AND TVCRD.STATUS = 10011001 \n");
		sql.append("      AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("      AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("      AND TV.NODE_STATUS IN (11511019) \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL) \n");
		sql.append("										  \n");
		sql.append("						     UNION 			  \n");
		sql.append("						     \n");
		sql.append("						     SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("   FROM TT_VS_ORDER                    TVO, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("         TM_DEALER                      TD, \n");
		sql.append("         TM_ORG                         TOR2, \n");
		sql.append("         TM_ORG                         TOR, \n");
		sql.append("         TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD \n");
		sql.append("    WHERE TVO.VIN = TV.VIN \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("      AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("      AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("      AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("      AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("      AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("      AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVCR.STATUS = 20801002 \n");
		sql.append("      AND TVCR.TYPE = 20811002 \n");
		sql.append("      AND TVCRD.STATUS = 10011001 \n");
		sql.append("      AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("      AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("      AND TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("      AND TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL)) tt1 \n");
		sql.append("                                             LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                             ON tt1.VIN=tas.VIN \n");
		sql.append("                                             WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                   GROUP BY tt2.GROUP_ID,tt2.ORG_ID \n");
		sql.append("  ) tab2 ON tab2.ORG_ID=tab.ORG_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT TVMG2.GROUP_ID GROUP_ID,TVCR.RESOURCE_SCOPE ORG_ID,COUNT(1) NUM22 \n");
		sql.append("                                  FROM TT_VS_COMMON_RESOURCE           TVCR, \n");
		sql.append("                                       TT_VS_COMMON_RESOURCE_DETAIL     TVCRD, \n");
		sql.append("                                       TM_VEHICLE_DEC                       TV, \n");
		sql.append("                                       ( SELECT MG1.GROUP_ID AS BRAND_ID,         MG1.GROUP_CODE AS BRAND_CODE,         MG1.GROUP_NAME AS BRAND_NAME,               \n");
		sql.append("                                       MG1.STATUS AS BRAND_STATUS,         MG2.GROUP_ID AS SERIES_ID,         MG2.GROUP_CODE AS SERIES_CODE,               \n");
		sql.append("                                       MG2.GROUP_NAME AS SERIES_NAME,         MG2.STATUS AS SERIES_STATUS,         MG2.GROUP_TYPE AS GROUP_TYPE,               \n");
		sql.append("                                       MG3.GROUP_ID AS MODEL_ID,         MG3.GROUP_CODE AS MODEL_CODE,         MG3.GROUP_NAME AS MODEL_NAME,               \n");
		sql.append("                                       MG3.STATUS AS MODEL_STATUS,         MG3.WX_ENGINE AS WX_ENGINE,           MG3.OILE_TYPE AS OILE_TYPE,                 \n");
		sql.append("                                       MG4.GROUP_ID AS GROUP_ID,         MG4.GROUP_CODE AS GROUP_CODE,         MG4.GROUP_NAME AS GROUP_NAME,               \n");
		sql.append("                                       MG4.STATUS AS GROUP_STATUS,         MG4.MODEL_YEAR AS MODEL_YEAR,         MG4.FACTORY_OPTIONS AS FACTORY_OPTIONS,               \n");
		sql.append("                                       MG4.STANDARD_OPTION AS STANDARD_OPTION,         MG4.LOCAL_OPTION AS LOCAL_OPTION,               MG4.SPECIAL_SERIE_CODE AS SPECIAL_SERIE_CODE,         \n");
		sql.append("                                       M.TRIM_CODE,         M.TRIM_NAME,         M.COLOR_CODE,               M.COLOR_NAME,         M.MATERIAL_ID,         M.MATERIAL_CODE,         \n");
		sql.append("                                       M.MATERIAL_NAME,         M.IS_SALES,           MG3.MVS AS MVS                  \n");
		sql.append("				       FROM TM_VHCL_MATERIAL M,         TM_VHCL_MATERIAL_GROUP_R MGR,         TM_VHCL_MATERIAL_GROUP MG4,         \n");
		sql.append("				       TM_VHCL_MATERIAL_GROUP MG3,         TM_VHCL_MATERIAL_GROUP MG2,         TM_VHCL_MATERIAL_GROUP MG1                                        \n");
		sql.append("				      WHERE M.MATERIAL_ID = MGR.MATERIAL_ID     AND MGR.GROUP_ID = MG4.GROUP_ID    AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID     \n");
		sql.append("				      AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID             AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID     AND M.COMPANY_ID = 2010010100070674) VM, \n");
		sql.append("                                       TM_VHCL_MATERIAL                 TVM, \n");
		sql.append("                                       TM_VHCL_MATERIAL_GROUP_R         TVMGR, \n");
		sql.append("                                       TM_VHCL_MATERIAL_GROUP           TVMG4, \n");
		sql.append("                                       TM_VHCL_MATERIAL_GROUP           TVMG3, \n");
		sql.append("                                       TM_VHCL_MATERIAL_GROUP           TVMG2 \n");
		sql.append("                                 WHERE TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("                                   AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("                                   AND TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                   AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                   AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                   AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                   AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                   AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                   AND TVCR.STATUS = 20801002 \n");
		sql.append("                                   AND TVCR.TYPE = 20811002 \n");
		sql.append("                                   AND TVCRD.STATUS = 10011001 \n");
		sql.append("                                   AND TV.NODE_STATUS = 11511018 \n");
		sql.append("                                   AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("                                   AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                   AND NOT EXISTS (SELECT 1  FROM TT_VS_ORDER TOR WHERE TOR.ORDER_STATUS < 20071008  AND TOR.VIN=TV.VIN) \n");
		sql.append("                                 GROUP BY TVCR.RESOURCE_SCOPE,TVMG2.GROUP_ID \n");
		sql.append("  ) tab22 ON tab22.GROUP_ID=tab.GROUP_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(1) NUM3 \n");
		sql.append("           FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append("               FROM (SELECT TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("                    FROM TM_ORG                         TOR, \n");
		sql.append("                         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                         TT_VS_ORDER                    TVO, \n");
		sql.append("                         TM_DEALER                      TD, \n");
		sql.append("                         TM_VEHICLE_DEC                     TV,                                      \n");
		sql.append("                         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                         TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                        WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("                          AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("                          AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("                          AND TVO.VIN = TV.VIN \n");
		sql.append("                          AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                          AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                          AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                          AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                          AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                          AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                          AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                          AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                          AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                          AND TVCR.STATUS = 20801002 \n");
		sql.append("                          AND TVCR.TYPE = 20811002 \n");
		sql.append("                          AND TVCRD.STATUS = 10011001 \n");
		sql.append("                          AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("                          AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("                          AND TV.NODE_STATUS IN (11511019) \n");
		sql.append("                          AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                          AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                                  WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                                    AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                                    AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL) \n");
		sql.append("                           UNION \n");
		sql.append("                                    SELECT TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("                                         FROM TM_ORG                         TOR, \n");
		sql.append("                                            TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                                            TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                                            TT_VS_ORDER                    TVO, \n");
		sql.append("                                            TM_DEALER                      TD, \n");
		sql.append("                                            TM_VEHICLE_DEC                     TV,                                      \n");
		sql.append("                                            TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                                            TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                                            TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                                            TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                                            TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                                        WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("                                            AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("                                            AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("                                            AND TVO.VIN = TV.VIN \n");
		sql.append("                                            AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                            AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                            AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                            AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                            AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                            AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                            AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                                            AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                                            AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                                            AND TVCR.STATUS = 20801002 \n");
		sql.append("                                            AND TVCR.TYPE = 20811002 \n");
		sql.append("                                            AND TVCRD.STATUS = 10011001 \n");
		sql.append("                                            AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("                                            AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("                                            AND TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("                                            AND TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("                                            AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                            AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("  WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("  AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("  AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL))  tt1 \n");
		sql.append("                                            LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                              ON tt1.VIN=tas.VIN \n");
		sql.append("                                              WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                    GROUP BY tt2.GROUP_ID,tt2.ORG_ID \n");
		sql.append("  ) tab3 ON tab3.ORG_ID=tab.ORG_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(*) NUM33 \n");
		sql.append("                                    FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append("                                              FROM (SELECT TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("   FROM TM_ORG                         TOR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("    WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("      AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("      AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVCR.STATUS = 20801002 \n");
		sql.append("      AND TVCR.TYPE = 20811002 \n");
		sql.append("      AND TVCRD.STATUS = 10011001 \n");
		sql.append("      AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND NOT EXISTS (SELECT 1  FROM TT_VS_ORDER TOR WHERE TOR.ORDER_STATUS < 20071008  AND TOR.VIN=TV.VIN))  tt1 \n");
		sql.append("                                              LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                              ON tt1.VIN=tas.VIN \n");
		sql.append("                                              WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                    GROUP BY tt2.GROUP_ID,tt2.ORG_ID   \n");
		sql.append("  ) tab33 ON tab33.ORG_ID=tab.ORG_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(*) NUM4 \n");
		sql.append("                                    FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append("                                              FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("   FROM TT_VS_ORDER                    TVO, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2,        \n");
		sql.append("         TM_DEALER                      TD, \n");
		sql.append("         TM_ORG                         TOR2, \n");
		sql.append("         TM_ORG                         TOR, \n");
		sql.append("         TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("    WHERE TVO.VIN=TV.VIN \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                 \n");
		sql.append("      AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("      AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("      AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("      AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVO.ORDER_TYPE=20831003 \n");
		sql.append("      AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("      AND TVO.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append("      AND TVO.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL))  tt1 \n");
		sql.append("                                              LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                              ON tt1.VIN=tas.VIN \n");
		sql.append("                                              WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                    GROUP BY tt2.GROUP_ID,tt2.ORG_ID   \n");
		sql.append("  ) tab4 ON tab4.ORG_ID=tab.ORG_ID \n");
		sql.append("  LEFT JOIN ( \n");
		sql.append("    SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(*) NUM5 \n");
		sql.append("                                    FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append("                                              FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("   FROM TT_VS_ORDER                    TVO, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2,    \n");
		sql.append("         TM_DEALER                      TD, \n");
		sql.append("         TM_ORG                         TOR2, \n");
		sql.append("         TM_ORG                         TOR, \n");
		sql.append("         TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD \n");
		sql.append("    WHERE TVO.VIN = TV.VIN \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("      AND TVO.DEALER_ID = TD.DEALER_ID                       \n");
		sql.append("      AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("      AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("      AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("      AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("      AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVCR.STATUS = 20801002 \n");
		sql.append("      AND TVCR.TYPE = 20811001 \n");
		sql.append("      AND TVCRD.STATUS = 10011001 \n");
		sql.append("      AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("      AND TV.NODE_STATUS IN(11511008) \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL) \n");
		sql.append("						    UNION 			  \n");
		sql.append("						    SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("   FROM TT_VS_ORDER                    TVO, \n");
		sql.append("         TM_VEHICLE_DEC                     TV, \n");
		sql.append("         TM_VHCL_MATERIAL               TVM, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("         TM_VHCL_MATERIAL_GROUP         TVMG2,    \n");
		sql.append("         TM_DEALER                      TD, \n");
		sql.append("         TM_ORG                         TOR2, \n");
		sql.append("         TM_ORG                         TOR, \n");
		sql.append("         TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("         TT_VS_COMMON_RESOURCE_DETAIL   TVCRD \n");
		sql.append("    WHERE TVO.VIN = TV.VIN \n");
		sql.append("      AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("      AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("      AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("      AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("      AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("      AND TVO.DEALER_ID = TD.DEALER_ID                       \n");
		sql.append("      AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("      AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("      AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("      AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("      AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("      AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("      AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("      AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("      AND TVCR.STATUS = 20801002 \n");
		sql.append("      AND TVCR.TYPE = 20811001 \n");
		sql.append("      AND TVCRD.STATUS = 10011001 \n");
		sql.append("      AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("      AND TVO.UPDATE_DATE>='"+month_day_start+"' \n");
		sql.append("      AND TVO.UPDATE_DATE<'"+month_day_end+"' \n");
		sql.append("      AND TV.NODE_STATUS IN(11511008,11511019,11511010,11511012,11511015,11511016) \n");
		sql.append("      AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("      AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER='"+playVer+"' \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL))  tt1 \n");
		sql.append("                                              LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                              ON tt1.VIN=tas.VIN \n");
		sql.append("                                              WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                    GROUP BY tt2.GROUP_ID,tt2.ORG_ID \n");
		sql.append("  ) tab5 ON tab5.ORG_ID=tab.ORG_ID ) t \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 游标2转换
	 * @param s_series_id
	 * @param s_series_code
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatch(long s_series_id, Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set status=1 where  GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and GROUP_ID="+s_series_id);
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp1
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList1(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,GAP,CREATE_BY,ALLOT_DATE from TM_TOTAL_GAP \n");
		sql.append("where GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp1 批量执行
	 * @param sALE_AMOUNT
	 * @param tARGET_ID
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp1(Integer sALE_AMOUNT, Long tARGET_ID, Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set SALE_AMOUNT="+sALE_AMOUNT+"   \n");
		sql.append(" where  GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+tARGET_ID+" \n");
		sql.append(" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	/**
	 * temp2
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList2(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2, \n");
		sql.append("IFNULL(NUM22,0) NUM22,IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,IFNULL(NUM4,0) NUM4, \n");
		sql.append("IFNULL(NUM5,0) NUM5,CREATE_BY,ALLOT_DATE \n");
		sql.append("FROM TM_TOTAL_GAP \n");
		sql.append("WHERE GAP_TYPE=10431003 AND CREATE_BY="+userId+" AND ALLOT_DATE='"+allotDate+"' \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=30) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * temp2 批量执行
	 * @param sALE_AMOUNT
	 * @param tARGET_ID
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp2(Map map, Long userId, String allotDate) {
		Integer NUM1 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"));
		Integer NUM2 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"));
		Integer NUM3 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"));
		Integer NUM4 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"));
		Integer NUM5 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"));
		Integer NUM33 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"));
		Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set NUM33=NUM33+"+NUM33+", \n");
		sql.append(" GAP=SALE_AMOUNT-IFNULL(NUM1,0)-"+NUM1+"-IFNULL(NUM2,0)-"+NUM2+"-IFNULL(NUM3,0)-"+NUM3+"-IFNULL(NUM33,0)-IFNULL(NUM4,0)-"+NUM4+"-IFNULL(NUM5,0)-"+NUM5+" \n");
		sql.append(" where  GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		
		StringBuffer sql2 = new StringBuffer("\n");
		sql2.append("update TM_TOTAL_GAP set GAP=0 \n");
		sql2.append("where GAP<0 and GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql2.toString(), new ArrayList<>());
	}
	
	/**
	 * 是否查询得到数据
	 * @return
	 */
	public List<Map> checkList() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TVMG2.GROUP_ID,TVMG2.GROUP_CODE,count(1) NUM \n");
		sql.append("                 from TMP_UPLOAD_RESOURCE            TUR,   \n");
		sql.append("                       TM_VEHICLE_DEC                 TV, \n");
		sql.append("                       TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                   where TUR.VIN = TV.VIN \n");
		sql.append("                     and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                     and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                     and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                     and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                     and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                     and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                     and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                     and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                     and TVMG2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		sql.append("                   group by TVMG2.GROUP_ID,TVMG2.GROUP_CODE \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取FlagList
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getFlagList(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2,IFNULL(NUM22,0) NUM22, \n");
		sql.append("IFNULL(NUM3,0) NUM3,NUM33,IFNULL(NUM4,0) NUM4,IFNULL(NUM5,0) NUM5,GAP \n");
		sql.append("FROM TM_TOTAL_GAP \n");
		sql.append("WHERE GAP_TYPE=10431003 AND CREATE_BY="+userId+" AND ALLOT_DATE='"+allotDate+"' \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 批量执行3
	 * @param map
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp3(Map map, Long userId, String allotDate) {
		Integer SALE_AMOUNT = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
		Integer NUM1 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"));
		Integer NUM2 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"));
		Integer NUM22 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM22"),"0"));
		Integer NUM3 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"));
		Integer NUM33 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"));
		Integer NUM4 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"));
		Integer NUM5 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"));
		Integer GAP = Integer.parseInt(CommonUtils.checkNull(map.get("GAP"),"0"));
		Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set SALE_AMOUNT="+SALE_AMOUNT+", \n");
		sql.append("NUM1=IFNULL(NUM1,0)+"+NUM1+",NUM2=IFNULL(NUM2,0)+"+NUM2+", \n");
		sql.append("NUM22=IFNULL(NUM22,0)+"+NUM22+",NUM3=IFNULL(NUM3,0)+"+NUM3+", \n");
		sql.append("NUM33=IFNULL(NUM33,0)+"+NUM33+",NUM4=IFNULL(NUM4,0)+"+NUM4+", \n");
		sql.append("NUM5=IFNULL(NUM5,0)+"+NUM5+",GAP="+GAP+" \n");
		sql.append("where TARGET_ID="+TARGET_ID+" and  GAP_TYPE=10431003  and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp3转
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList3(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TARGET_ID,GAP from TM_TOTAL_GAP \n");
		sql.append("where GAP_TYPE=10431003 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp4 批量
	 * @param map
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp4(Map map, Long userId, String allotDate) {
		Integer GAP = Integer.parseInt(CommonUtils.checkNull(map.get("GAP"),"0"));
		Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP="+GAP+"  \n");
		sql.append("where GAP_TYPE=10431003 and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30); \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp5 批量
	 * @param allotDate
	 */
	public void exceBatchTemp5(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP=0  \n");
		sql.append("where GAP<0 and GAP_TYPE=10431003 and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp4转
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList4(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select GROUP_ID,sum(GAP) GAP from TM_TOTAL_GAP  \n");
		sql.append("where GAP_TYPE=10431003 and ALLOT_DATE='"+allotDate+"' and CREATE_BY="+userId+" \n");
		sql.append("group by GROUP_ID having sum(GAP)=0 \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp6 批量
	 * @param map
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp6(Map map, Long userId, String allotDate) {
		Long groupId = Long.parseLong(CommonUtils.checkNull(map.get("GROUP_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP2=SALE_AMOUNT \n");
		sql.append("where GROUP_ID="+groupId+" and GAP_TYPE=10431003 \n");
		sql.append("and ALLOT_DATE='"+allotDate+"' and CREATE_BY="+userId+" \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 获取VPC
	 * @return
	 */
	public List<Map> getListVpc() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TAP.VPC_PORT from TM_ALLOT_PORT TAP ORDER BY TAP.PORT_LEVEL \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp5转
	 * @param s_series_code
	 * @param vPC_PORT
	 * @return
	 */
	public List<Map> getTempList5(String s_series_code, Integer vPC_PORT) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select VM.SERIES_CODE,VM.GROUP_ID,VM.COLOR_NAME,count(*) NUM \n");
		sql.append("                                 from TMP_UPLOAD_RESOURCE     TUR,   \n");
		sql.append("                                       TM_VEHICLE_DEC         TV, \n");
		sql.append("                                       ("+getVwMaterialSql()+")             VM \n");
		sql.append("                                   where TUR.VIN = TV.VIN \n");
		sql.append("                                     and TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                     and VM.SERIES_CODE = '"+s_series_code+"' \n");
		sql.append("                                     and TUR.vpc_port = "+vPC_PORT+" \n");
		sql.append("                                   group by VM.SERIES_CODE,VM.GROUP_ID,VM.COLOR_NAME \n");
		sql.append("                                   order by VM.SERIES_CODE,VM.GROUP_ID,VM.COLOR_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * tempCount
	 * @param vPC_PORT
	 * @return
	 */
	public int getTempCount(Integer vPC_PORT) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select count(1) TEMP_COUNT from (select org_level from TM_ALLOT_AREA TAA \n");
		sql.append("                                    where  TAA.PORT_ID = "+vPC_PORT+" \n");
		sql.append("                                    group by org_level order by org_level) t \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("TEMP_COUNT"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * temp6转
	 * @param vPC_PORT
	 * @return
	 */
	public List<Map> getTempList6(Integer vPC_PORT) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ORG_LEVEL from TM_ALLOT_AREA TAA \n");
		sql.append("where  TAA.PORT_ID = "+vPC_PORT+" group by org_level order by org_level \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取count数
	 * @param vPC_PORT
	 * @param org_level 
	 * @return
	 */
	public int getOCount(Integer vPC_PORT, Integer org_level) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select count(org_id) O_COUNT from TM_ALLOT_AREA  TAA \n");
		sql.append("where TAA.PORT_ID = "+vPC_PORT+" and TAA.org_level = "+org_level+" \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("O_COUNT"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * temp7转
	 * @param vPC_PORT
	 * @param org_level
	 * @return
	 */
	public List<Map> getTempList7(Integer vPC_PORT, Integer org_level) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TAA.ORG_ID from TM_ALLOT_AREA TAA \n");
		sql.append("where TAA.PORT_ID = "+vPC_PORT+" and TAA.org_level = "+org_level+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取orgCount
	 * @param s_series_id
	 * @param userId
	 * @param allotDate
	 * @param org_id
	 * @return
	 */
	public int getOrgCount(long s_series_id, Long userId, String allotDate, Long org_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" select count(1) ORG_COUNT from TM_TOTAL_GAP \n");
		sql.append("                                where GAP_TYPE=10431003  \n");
		sql.append("                                        and GROUP_ID="+s_series_id+" \n");
		sql.append("                                        and CREATE_BY="+userId+" \n");
		sql.append("                                        and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                                        and TARGET_ID = "+org_id+" \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("ORG_COUNT"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取GAP
	 * @param org_id 
	 * @param allotDate 
	 * @param userId 
	 * @param s_series_id 
	 * @return
	 */
	public int getGAP(long s_series_id, Long userId, String allotDate, Long org_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT DISTINCT (CASE WHEN IFNULL(GAP2,0)>0 THEN GAP2 ELSE GAP END) GAP  FROM TM_TOTAL_GAP \n");
		sql.append(" WHERE GAP_TYPE=10431003  \n");
		sql.append("    and GROUP_ID="+s_series_id+" \n");
		sql.append("    and CREATE_BY="+userId+" \n");
		sql.append("    and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("    and TARGET_ID = "+org_id+" \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取target
	 * @param s_series_id
	 * @param userId
	 * @param allotDate
	 * @param org_id
	 * @return
	 */
	public int getTargetNum(long s_series_id, Long userId, String allotDate, Long org_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT IFNULL(SUM(TARGET_NUM),0) TARGET_NUM FROM TM_ALLOT_RESOURCE_PORT_DCS TARP \n");
		sql.append("  where \n");
		sql.append("    SERIES_ID="+s_series_id+" \n");
		sql.append("    and CREATE_BY="+userId+" \n");
		sql.append("    and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("    and ORG_ID = "+org_id+" \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("TARGET_NUM"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 转8
	 * @param userId
	 * @param allotDate
	 * @param s_series_id
	 * @param d
	 * @return
	 */
	public List<Map> getTempList8(Long userId, String allotDate, long s_series_id, double d) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT DISTINCT (CASE WHEN IFNULL(TTG.GAP2,0)>0 THEN TTG.GAP2 ELSE TTG.GAP END) GAP,IFNULL(T.TARGET_NUM,0) TOTAL_ALLOT_NUM \n");
		sql.append("                                    FROM TM_TOTAL_GAP  ttg \n");
		sql.append("                                    LEFT JOIN (SELECT TARP.ORG_ID,TARP.SERIES_ID,SUM(TARGET_NUM) TARGET_NUM \n");
		sql.append("						                             from TM_ALLOT_RESOURCE_PORT_DCS tarp \n");
		sql.append("                                                     where  tarp.ALLOT_DATE='"+allotDate+"' and tarp.SERIES_ID="+s_series_id+" \n");
		sql.append("                                                    group by tarp.ORG_ID,tarp.SERIES_ID) t \n");
		sql.append("                                   on ttg.TARGET_ID=t.ORG_ID and ttg.GROUP_ID=t.SERIES_ID \n");
		sql.append("                                   where ttg.GAP_TYPE=10431003 \n");
		sql.append("                                     and ttg.CREATE_BY="+userId+" \n");
		sql.append("                                     and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                                     and ttg.TARGET_ID="+d+" \n");
		sql.append("                                     and ttg.GROUP_ID="+s_series_id+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp9
	 * @param s_series_code
	 * @return
	 */
	public List<Map> getTempList9(String s_series_code) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select VM.SERIES_CODE,VM.GROUP_ID,VM.COLOR_NAME,count(*) NUM \n");
		sql.append("                                 from TMP_UPLOAD_RESOURCE     TUR,   \n");
		sql.append("                                       TM_VEHICLE_DEC         TV, \n");
		sql.append("                                       ("+getVwMaterialSql()+")             VM \n");
		sql.append("                                   where TUR.VIN = TV.VIN \n");
		sql.append("                                     and TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                     and VM.SERIES_CODE = '"+s_series_code+"' \n");
		sql.append("                                   group by VM.SERIES_CODE,VM.GROUP_ID,VM.COLOR_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp10
	 * @param vPC_PORT 
	 * @param s_series_code
	 * @return
	 */
	public List<Map> getTempList10(Integer vPC_PORT) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" select ORG_ID from TM_ALLOT_AREA TAA where  TAA.PORT_ID = "+vPC_PORT+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param orgId
	 * @param vPC_PORT
	 * @param allotDate
	 * @param userId
	 * @param nowDate
	 * @param groupId
	 * @param colorName
	 * @return
	 */
	public Long getPortId(Long orgId, Integer vPC_PORT, String allotDate, Long userId, Date nowDate, Long groupId,
			String colorName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TM_ALLOT_PORT_ID TEMP_ARR from TM_ALLOT_RESOURCE_PORT_DCS \n");
		sql.append("where ORG_ID = "+orgId+" and TM_PORT_ID = "+vPC_PORT+" and ALLOT_DATE = '"+allotDate+"' \n");
		sql.append("and CREATE_BY = "+userId+" and CREATE_DATE = '"+sdf.format(nowDate)+"' \n");
		sql.append("and GROUP_ID = "+groupId+" and COLOR_NAME = '"+colorName+"' \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Long.parseLong(CommonUtils.checkNull(map.get("TEMP_ARR"),"0"));
		}else{
			return 0L;
		}
	}
	
	/**
	 * temp11
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTmppList11(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select SERIES_ID,GROUP_ID,COLOR_NAME,vpc_port,sum(NUM) NUM \n");
		sql.append("from TM_ALLOT_GROUP_TEMP_DCS  \n");
		sql.append("where ALLOT_TYPE=10431003 and allot_date='"+allotDate+"' and CREATE_BY="+userId+" \n");
		sql.append("group by SERIES_ID,GROUP_ID,COLOR_NAME,vpc_port \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param userId
	 * @param allotDate
	 * @param groupId
	 * @param orgId
	 * @return
	 */
	public int getGAP2(Long userId, String allotDate, Long groupId, Long orgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select IFNULL(ttg.SALE_AMOUNT,0) GAP_ORG  from TM_TOTAL_GAP  ttg \n");
		sql.append("		              where ttg.GAP_TYPE=10431003 \n");
		sql.append("		                and ttg.CREATE_BY="+userId+" \n");
		sql.append("		                and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("		                and ttg.GROUP_ID="+groupId+" \n");
		sql.append("		                and ttg.TARGET_ID = "+orgId+" \n");
		sql.append("		                LIMIT 0,1 \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP_ORG"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * temp12
	 * @param userId
	 * @param allotDate
	 * @param groupId
	 * @param orgId
	 * @return
	 */
	public List<Map> getTempList12(Long userId, String allotDate, Long groupId, Long orgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ttg.SALE_AMOUNT GAP  from TM_TOTAL_GAP  ttg \n");
		sql.append("                               where ttg.GAP_TYPE=10431003 \n");
		sql.append("                                and ttg.GROUP_ID="+groupId+" \n");
		sql.append("                                and ttg.CREATE_BY="+userId+" \n");
		sql.append("                                and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("				                and ttg.TARGET_ID = "+orgId+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp13
	 * @param d
	 * @param vPC_PORT
	 * @param groupId
	 * @param colorName
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public Long getTempList13(double d, Integer vPC_PORT, Long groupId, String colorName, Long userId,
			String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select DISTINCT TM_ALLOT_PORT_ID TEMP_ARR \n");
		sql.append("from TM_ALLOT_RESOURCE_PORT_DCS \n");
		sql.append("where ORG_ID="+d+" and TM_PORT_ID = "+vPC_PORT+" \n");
		sql.append("and GROUP_ID="+groupId+" and COLOR_NAME='"+colorName+"' \n");
		sql.append("and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' \n");
		Map map =  OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Long.parseLong(CommonUtils.checkNull(map.get("TEMP_ARR"),"0"));
		}else{
			return 0L;
		}
	}
	
	/**
	 * 批量执行7
	 * @param d
	 * @param e
	 * @param vPC_PORT
	 * @param groupId
	 * @param colorName
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatchTemp7(Long d, double e, Integer vPC_PORT, Long groupId, String colorName, Long userId,
			String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_ALLOT_RESOURCE_PORT_DCS t set t.TARGET_NUM=(t.TARGET_NUM+"+d+") \n");
		sql.append("where t.ORG_ID="+e+" and t.TM_PORT_ID = "+vPC_PORT+" \n");
		sql.append("and t.GROUP_ID="+groupId+" and t.COLOR_NAME='"+colorName+"' \n");
		sql.append("and t.CREATE_BY="+userId+" and t.ALLOT_DATE='"+allotDate+"' \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}

	/**
	 * temp14转
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList14(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ORG_ID,GROUP_ID,COLOR_NAME,SUM(TARGET_NUM) TARGET_NUM \n");
		sql.append("from TM_ALLOT_RESOURCE_PORT_DCS \n");
		sql.append("where ALLOT_DATE='"+allotDate+"' \n");
		sql.append("group by org_id,group_id,color_name \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * temp15转
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList15(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT ORG_ID,TM_PORT_ID,SUM(TARGET_NUM) TARGET_NUM \n");
		sql.append(" FROM TM_ALLOT_RESOURCE_PORT_DCS \n");
		sql.append(" WHERE ALLOT_DATE='"+allotDate+"' \n");
		sql.append(" GROUP BY ORG_ID,TM_PORT_ID \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * temp16转
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList16(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT T.TM_PORT_ID,TAA.ORG_LEVEL,SUM(T.ALLOT_NUM) ALLOT_NUM \n");
		sql.append("                   FROM (SELECT TARP.ORG_ID,TARP.TM_PORT_ID,SUM(TARP.TARGET_NUM) ALLOT_NUM \n");
		sql.append("                           FROM TM_ALLOT_RESOURCE_PORT_DCS TARP \n");
		sql.append("                           WHERE TARP.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                           GROUP BY TARP.ORG_ID,TARP.TM_PORT_ID) t \n");
		sql.append("                   INNER JOIN TM_ALLOT_AREA TAA ON T.ORG_ID = TAA.ORG_ID AND T.TM_PORT_ID=TAA.PORT_ID \n");
		sql.append("                   GROUP BY T.TM_PORT_ID,TAA.ORG_LEVEL \n");
		sql.append("                   HAVING SUM(T.ALLOT_NUM)>0 \n");
		sql.append("                   ORDER BY TAA.ORG_LEVEL \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp16转
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTempList17(String allotDate, Integer vPC_PORT, Integer orgLevel) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select taa.ORG_ID,t.TM_PORT_ID,taa.ORG_LEVEL,sum(t.ALLOT_NUM) ALLOT_NUM \n");
		sql.append("                   from (select tarp.ORG_ID,tarp.TM_PORT_ID,sum(tarp.TARGET_NUM) ALLOT_NUM \n");
		sql.append("                           from TM_ALLOT_RESOURCE_PORT_DCS tarp \n");
		sql.append("                           where tarp.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                           group by tarp.ORG_ID,tarp.TM_PORT_ID) t \n");
		sql.append("                   inner join TM_ALLOT_AREA taa on t.ORG_ID = taa.ORG_ID and t.TM_PORT_ID=taa.PORT_ID \n");
		sql.append("                   where t.TM_PORT_ID="+vPC_PORT+" and taa.ORG_LEVEL="+orgLevel+" \n");
		sql.append("                   group by taa.ORG_ID,t.TM_PORT_ID,taa.ORG_LEVEL \n");
		sql.append("                   having sum(t.ALLOT_NUM)>0 \n");
		sql.append("                   order by taa.ORG_LEVEL \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 数据重复校验
	 * @return
	 */
	public List<Map> findRepeatData2() {
		StringBuffer sql = new StringBuffer("");
		sql.append("select p1.ROW_NO,p1.VIN,p1.DEALER_CODE\n");
		sql.append("  from TMP_UPLOAD_LATELY_MONTH_DCS p1\n");
		sql.append("   where exists (select 1\n");
		sql.append("    			from TMP_UPLOAD_LATELY_MONTH_DCS p2\n");
		sql.append("    			where p1.VIN = p2.VIN\n");
		sql.append("    			  and p1.DEALER_CODE = p2.DEALER_CODE\n");
		sql.append("     			  and p1.ROW_NO <> p2.ROW_NO)\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 判断是否在车辆表中存在
	 * @return
	 */
	public List<Map> findNoExistsVin2() {
		return OemDAOUtil.findAll("select ROW_NO,VIN from TMP_UPLOAD_LATELY_MONTH_DCS where VIN not in(select tv.VIN from TM_VEHICLE_DEC tv where tv.VIN=VIN)", null);
	}
	
	/**
	 * 判断资源范围是否存在
	 * @return
	 */
	public List<Map> findNoExistsResource2() {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT P.ROW_NO,P.VIN,P.DEALER_CODE FROM TMP_UPLOAD_LATELY_MONTH_DCS P WHERE P.R_TYPE=0 ");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 查询区域信息
	 */
	public List<Map> findOrg(String code) {
		return OemDAOUtil.findAll(" select ORG_ID,ORG_CODE from TM_ORG where ORG_TYPE=10191001 and DUTY_TYPE in(10431001,10431003) and BUSS_TYPE=12351001 and ORG_CODE='"+code+"'", null);
	}
	
	/**
	 * 临时表数据导入正式表
	 */
	public void insertTempToTm() {
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("insert into TM_LATELY_MONTH(DEALER_ID,VIN,R_TYPE,TRANS_DATE,CREATE_BY,CREATE_DATE) \n");
		sql.append("select td.DEALER_ID,tulm.VIN,tulm.R_TYPE,tulm.CREATE_DATE,1,NOW() \n");
		sql.append("from TM_DEALER td,TMP_UPLOAD_LATELY_MONTH_DCS  tulm \n");
		sql.append(" where td.DEALER_CODE=tulm.DEALER_CODE and tulm.R_TYPE in(10431005,10431003) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findOrderIsAllot(LoginInfoDto loginInfo) {
		
		return OemDAOUtil.findAll("select * from TMP_UPLOAD_RESOURCE where ALLOT=0 and DEAL=2", null);
		
	}
	
	/**
	 * 
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findOrderAllot(LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct DATE_FORMAT(CREATE_DATE,'%Y%m%d') CREATE_DATE from TMP_UPLOAD_RESOURCE");
		sql.append("  where DEAL=1 and ALLOT=0 and CREATE_BY="+loginInfo.getUserId()+"\n");
		
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param groupId
	 * @param allotDate
	 * @param loginInfo
	 * @return
	 */
	public java.util.List<Map> findResourceAllotList(String groupId, String allotDate, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tt.* FROM (  \n");
		sql.append("        SELECT  '' STANDARD_OPTION,'' FACTORY_OPTIONS,'' LOCAL_OPTION,'' MODEL_YEAR,TT1.ORG_NAME,IFNULL(TT3.TM_ALLOT_ID,0) TM_ALLOT_ID,TT1.ORG_ID ALLOT_TARGET_ID,IFNULL(TT2.SERIES_ID,0) SERIES_ID,(CASE WHEN tt2.series_id IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE tt2.series_name END)  SERIES_NAME,IFNULL(TT3.GROUP_ID,0) GROUP_ID,IFNULL(TT3.GROUP_NAME,'0') group_name,IFNULL(TT3.COLOR_NAME,'0') color_Name,IFNULL(TT3.NUM,0) NUM,TT1.STATUS  	 \n");
		sql.append("        		 FROM  	 \n");
		sql.append("         	(SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TM.ORG_NAME,TM.ORG_ID FROM TM_ORG TM,TM_TOTAL_GAP TTG,TM_VHCL_MATERIAL_GROUP TVMG WHERE TM.ORG_ID=TTG.TARGET_ID AND TTG.GROUP_ID=TVMG.GROUP_ID AND TTG.ALLOT_DATE='"+allotDate+"' AND TTG.STATUS=0 AND TTG.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+" AND TVMG.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+")	 \n");
		sql.append("        		TT1 LEFT JOIN 	 \n");
		sql.append("         	(SELECT GROUP_ID SERIES_ID,GROUP_CODE,GROUP_NAME SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001  AND GROUP_LEVEL=2 AND GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+") 	 \n");
		sql.append("                TT2 ON TT2.SERIES_ID=TT1.SERIES_ID   LEFT JOIN 	 \n");
		sql.append("         	(SELECT TAR.TM_ALLOT_ID,TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID SERIES_ID,TVMG2.GROUP_NAME SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TAR.COLOR_NAME,TAR.ADJUST_NUM NUM	 \n");
		sql.append("         		FROM TM_ALLOT_RESOURCE_DCS     	   TAR,	 \n");
		sql.append("         		TM_VHCL_MATERIAL_GROUP         TVMG4,	 \n");
		sql.append("         		TM_VHCL_MATERIAL_GROUP         TVMG3,	 \n");
		sql.append("         		TM_VHCL_MATERIAL_GROUP         TVMG2	 \n");
		sql.append("         		WHERE	TAR.GROUP_ID = TVMG4.GROUP_ID	 \n");
		sql.append("        			    AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID 	 \n");
		sql.append("         				AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("         				AND TAR.CREATE_BY = "+loginInfo.getUserId()+" \n");
		sql.append("         				AND TAR.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("         				AND TAR.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+" \n");
		sql.append("        				AND TAR.ALLOT_STATUS=0 \n");
		sql.append("    				 	AND tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+" \n");
		sql.append("        				ORDER BY TVMG2.GROUP_ID,TVMG4.GROUP_ID,TAR.COLOR_NAME \n");
		sql.append("         	) TT3 ON TT3.SERIES_ID=TT2.SERIES_ID \n");
		sql.append("        UNION  ALL 	 \n");
		sql.append("        SELECT t1.* FROM ( \n");
		sql.append("        SELECT DISTINCT IFNULL(vw.STANDARD_OPTION,'') STANDARD_OPTION,IFNULL(vw.FACTORY_OPTIONS,'') FACTORY_OPTIONS,IFNULL(vw.LOCAL_OPTION,'') LOCAL_OPTION,IFNULL(vw.MODEL_YEAR,'') MODEL_YEAR,t.* FROM ( \n");
		sql.append("        SELECT TOR.ORG_NAME,IFNULL(TAR.TM_ALLOT_ID,0) TM_ALLOT_ID,IFNULL(TAR.ALLOT_TARGET_ID,0) ALLOT_TARGET_ID,IFNULL(TVMG2.GROUP_ID,0) SERIES_ID,(CASE WHEN TVMG2.GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND) THEN '大切诺基' ELSE TVMG2.GROUP_NAME END) SERIES_NAME,IFNULL(TVMG4.GROUP_ID,0) GROUP_ID,TVMG4.GROUP_NAME,TAR.COLOR_NAME,IFNULL(TAR.ADJUST_NUM,0) NUM,1 AS STATUS 	 \n");
		sql.append("         		FROM	TM_ALLOT_RESOURCE_DCS     	       TAR,	 \n");
		sql.append("        				TM_VHCL_MATERIAL_GROUP         TVMG4, 	 \n");
		sql.append("         				TM_VHCL_MATERIAL_GROUP         TVMG3,	 \n");
		sql.append("         				TM_VHCL_MATERIAL_GROUP         TVMG2,	 \n");
		sql.append("         				 TM_ORG 					   TOR \n");
		sql.append("        		WHERE   TAR.GROUP_ID = TVMG4.GROUP_ID AND TOR.ORG_ID=TAR.ALLOT_TARGET_ID 	 \n");
		sql.append("         				AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID	 \n");
		sql.append("         				AND TAR.CREATE_BY = "+loginInfo.getUserId()+"	 \n");
		sql.append("         				AND TAR.ALLOT_DATE='"+allotDate+"'	 \n");
		sql.append("         				AND TAR.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"	 \n");
		sql.append("    				 	AND tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+" \n");
		if(groupId!=""){ 
		sql.append("      and TVMG2.group_id in("+groupId+") \n");
		} 
		sql.append("         				AND TAR.ALLOT_STATUS=0	 \n");
		sql.append("         ORDER BY TVMG2.GROUP_ID,TVMG4.GROUP_ID,TAR.COLOR_NAME) t \n");
		sql.append("        LEFT JOIN \n");
		sql.append("        (SELECT  group_id,IFNULL(STANDARD_OPTION,'') STANDARD_OPTION,IFNULL(FACTORY_OPTIONS,'') FACTORY_OPTIONS,IFNULL(LOCAL_OPTION,'') LOCAL_OPTION,IFNULL(MODEL_YEAR,'') MODEL_YEAR FROM ("+getVwMaterialSql()+" \n");
		
		sql.append("        ) tm WHERE group_type="+OemDictCodeConstants.GROUP_TYPE_IMPORT+") vw \n");
		sql.append("          ON vw.group_id=t.group_Id  ORDER BY t.SERIES_ID,t.group_id,t.COLOR_NAME) t1 \n");
		sql.append("         ) tt    LEFT JOIN TM_DEALER_WHS TDW ON tdw.DEALER_ID=tt.allot_target_id AND tdw.TYPE=2 \n");
		sql.append("         WHERE 1=1  ORDER BY TDW.ORDER_NUM ASC \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param groupId
	 * @param allotDate
	 * @param allotMonthDate
	 * @param loginInfo
	 * @return
	 */
	public java.util.List<Map> findTotalGapListBySeries(String groupId, String allotDate, String allotMonthDate,
			LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.*,(case when t.SALE_AMOUNT>0 then  round((t.NUM1+t.NUM2+t.NUM3+t.NUM33+t.NUM4+t.NUM5+t.ALLOT_NUM)*100/t.SALE_AMOUNT,2) else 0 end) RATE \n");
		sql.append("  from ( select  tt1.GROUP_ID SERIES_ID,tt1.SERIES_NAME,tt1.ORG_ID,tt1.ORG_NAME,tt1.SALE_AMOUNT,tt1.NUM1,tt1.NUM2,tt1.NUM22,tt1.NUM3,tt1.NUM33,tt1.NUM4,tt1.NUM5,tt1.GAP,tt1.ALLOT_NUM\n");
		sql.append(",tt2.ALLOT_MONTH_NUM  from (select t1.*,t2.ALLOT_NUM,t1.org_id ALLOT_TARGET_ID\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,IFNULL(ttg.SALE_AMOUNT,0) SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,IFNULL(ttg.GAP,0) GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=tor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("     		 and ttg.GAP_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("             and ttg.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!groupId.equals("")){ 
			sql.append("			and ttg.GROUP_ID in("+groupId+")\n");
		}
		//sql.append("			 and ttg.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(IFNULL(ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     			  and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("  				  and tar.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 	  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		//sql.append("			 	  and tvmg2.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.ORG_ID = t2.ALLOT_TARGET_ID \n");
		sql.append("	) tt1 LEFT JOIN   ( select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID, sum(IFNULL(ADJUST_NUM,0)) ALLOT_month_NUM\n");
		sql.append("				from TM_ALLOT_RESOURCE_DCS tar,\n");
		sql.append("    				 TM_VHCL_MATERIAL_GROUP tvmg4,\n");
		sql.append("				     TM_VHCL_MATERIAL_GROUP tvmg3,\n");
		sql.append("					 TM_VHCL_MATERIAL_GROUP tvmg2\n");
		sql.append("				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("    				and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
        sql.append(" 					and tar.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("	   				and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("					and tar.ALLOT_DATE like '%"+allotMonthDate+"%' \n");
		sql.append("			 	    and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("		group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID\n");
		sql.append("  ) tt2 ON tt1.group_id=tt2.group_id and tt1.allot_target_id=tt2.allot_target_id\n");
		sql.append(" union all\n");
		sql.append("select tt1.GROUP_ID SERIES_ID,tt1.SERIES_NAME,tt1.ORG_ID,tt1.ORG_NAME,tt1.SALE_AMOUNT,tt1.NUM1,tt1.NUM2,tt1.NUM22,tt1.NUM3,tt1.NUM33,tt1.NUM4,tt1.NUM5,tt1.GAP,tt1.ALLOT_NUM,tt2.allot_month_num from \n");
		sql.append("( select t1.GROUP_ID,'TOTAL' as SERIES_NAME,0 as ORG_ID,'' as ORG_NAME,sum(t1.SALE_AMOUNT) SALE_AMOUNT,sum(NUM1) NUM1,sum(NUM2) NUM2,avg(NUM22) NUM22,sum(NUM3) NUM3,sum(NUM33) NUM33,sum(NUM4) NUM4,sum(NUM5) NUM5,sum(GAP) GAP,sum(t2.ALLOT_NUM) ALLOT_NUM\n");
		sql.append("  from (select ttg.GROUP_ID,tvmg.GROUP_NAME SERIES_NAME,TOR.ORG_ID,TOR.ORG_NAME,IFNULL(ttg.SALE_AMOUNT,0) SALE_AMOUNT,IFNULL(ttg.NUM1,0) NUM1,IFNULL(ttg.NUM2,0) NUM2,IFNULL(ttg.NUM22,0) NUM22,IFNULL(ttg.NUM3,0) NUM3,IFNULL(ttg.NUM33,0) NUM33,IFNULL(ttg.NUM4,0) NUM4,IFNULL(ttg.NUM5,0) NUM5,IFNULL(ttg.GAP,0) GAP\n");
		sql.append("      	  from TM_TOTAL_GAP   			ttg,\n");
		sql.append("               TM_ORG         			tor,\n");
		sql.append("  			   TM_VHCL_MATERIAL_GROUP 	tvmg\n");
		sql.append("           where ttg.TARGET_ID=tor.ORG_ID\n");
		sql.append("    		 and ttg.GROUP_ID=tvmg.GROUP_ID\n");
		sql.append("   		     and tvmg.GROUP_LEVEL=2\n");
		sql.append("     		 and ttg.GAP_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("             and ttg.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("			 and ttg.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 and tvmg.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		if(!groupId.equals("")){
			sql.append("			 and ttg.GROUP_ID in("+groupId+")\n");
		}
		//sql.append("			 and ttg.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append("             and ttg.GAP_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+") t1\n");
		sql.append("  left join (select tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID,sum(IFNULL(ADJUST_NUM,0)) ALLOT_NUM\n");
		sql.append("   				from TM_ALLOT_RESOURCE_DCS              tar,\n");
		sql.append("   					 TM_VHCL_MATERIAL_GROUP         tvmg4,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg3,\n");
		sql.append("  					 TM_VHCL_MATERIAL_GROUP         tvmg2\n");
		sql.append("   				where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("  				  and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("  				  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("     			  and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("  				  and tar.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("			 	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("			 	  and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		//sql.append("			      and tvmg2.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append(" 			group by tvmg2.GROUP_ID,tar.ALLOT_TARGET_ID) t2");
		sql.append("  on t1.GROUP_ID=t2.GROUP_ID and t1.ORG_ID = t2.ALLOT_TARGET_ID \n");
		sql.append("  group by t1.GROUP_ID\n");
		sql.append(" ) tt1 left join (select tvmg2.GROUP_ID,sum(IFNULL(ADJUST_NUM,0)) ALLOT_MONTH_NUM \n"); 
		sql.append("						from TM_ALLOT_RESOURCE_DCS tar,\n");
		sql.append("							 TM_VHCL_MATERIAL_GROUP tvmg4,\n");
		sql.append("	     					 TM_VHCL_MATERIAL_GROUP tvmg3,\n");
		sql.append("							 TM_VHCL_MATERIAL_GROUP tvmg2\n");
		sql.append("	where tar.GROUP_ID=tvmg4.GROUP_ID\n");
		sql.append("		and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID  and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("		and tar.CREATE_BY="+loginInfo.getUserId()+"\n");
		sql.append("		and tar.ALLOT_TYPE="+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+" \n");
		sql.append("		and tar.ALLOT_DATE like '%"+allotMonthDate+"%' \n");
		sql.append("		and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("	group by tvmg2.GROUP_ID \n");
		sql.append("	) tt2 on tt1.group_id=tt2.group_id\n");
		sql.append(") t\n");
		sql.append(" order by t.ORG_NAME");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param groupId
	 * @param allotDate
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findResourceAllotListBySeries(String groupId, String allotDate, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvmg2.GROUP_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) SER_NUM\n");
		sql.append("   from TM_ALLOT_RESOURCE_DCS     	   tar,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg4,\n");
		sql.append("        TM_VHCL_MATERIAL_GROUP     tvmg3,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP     tvmg2,\n");
		sql.append("		TM_ORG                	   tor\n");
		sql.append("    where tar.GROUP_ID = tvmg4.GROUP_ID\n");
		sql.append("     and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID\n");
		sql.append("     and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID\n");
		sql.append("	 and tar.ALLOT_TARGET_ID = tor.ORG_ID\n");
		if(!groupId.equals("")){
			sql.append("	 and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		sql.append("     and tar.CREATE_BY = "+loginInfo.getUserId()+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		//sql.append("     and tvmg2.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append("    group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 
	 * @param groupId
	 * @param allotDate
	 * @param loginInfo
	 * @return
	 */
	public List<Map> findResourceAllotListByColor(String groupId, String allotDate, LoginInfoDto loginInfo) {
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
		if(!groupId.equals("")){
			sql.append("	 and tvmg2.GROUP_ID in("+groupId+")\n");
		}
		sql.append("     and tar.CREATE_BY = "+loginInfo.getUserId()+"\n");
		sql.append("	 and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 and tvmg2.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT+"\n");
		sql.append("     and tar.ALLOT_TYPE= "+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"\n");
		sql.append("     and tar.ALLOT_STATUS="+OemDictCodeConstants.ORDER_ALLOT_STATIS_01+"\n");
		//sql.append("	 and tar.GROUP_ID!="+LonPropertiesLoad.getInstance().getValue("GRAND_30")+"\n");
		sql.append("     group by tvmg2.GROUP_ID,tvmg2.GROUP_NAME,tvmg4.GROUP_ID,tvmg4.GROUP_NAME,tar.COLOR_NAME\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 如果上传分配时未分配到大区，则需要将该大区的数据置为审核通过
	 */
	public List<Map> findNoAllotNums(String allotDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tor.ORG_ID,tor.ORG_NAME\n");
		sql.append("	from TM_ALLOT_RESOURCE_DCS             tar,\n");
		sql.append("		 TM_ORG                        tor\n");
		sql.append(" 	where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("	  and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_LARGEREGION +"\n");
		sql.append("	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	 group by tor.ORG_ID,tor.ORG_NAME\n");
		sql.append("	 having sum(tar.ADJUST_NUM)=0\n");
		
		List<Map> list =OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	/**
	 * 将大区下所有的小区提交
	 * @param allotDate
	 * @param bigOrgId
	 * @return
	 */
	public List<Map> findNoAllotNumsSmallArea(String allotDate, Long bigOrgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tor.ORG_ID,tor.ORG_NAME\n");
		sql.append("	from TM_ALLOT_RESOURCE_DCS             tar,\n");
		sql.append("		 TM_ORG                        tor\n");
		sql.append(" 	where tar.ALLOT_TARGET_ID=tor.ORG_ID\n");
		sql.append("	  and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_SMALLREGION +"\n");
		sql.append("	  and tar.ALLOT_DATE='"+allotDate+"'\n");
		sql.append("	  and PARENT_ORG_ID="+bigOrgId+"\n");
		sql.append("	 group by tor.ORG_ID,tor.ORG_NAME\n");
		sql.append("	 having sum(tar.ADJUST_NUM)=0\n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	/**
	 * 查询大区的邮箱
	 * @param org
	 * @param allotDate
	 * @return
	 */
	public List<Map> findOTDAndAreaBigByEmail(Long org, String allotDate) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct user.email,user.name,TOR3.ORG_DESC bigOrg,tar.ALLOT_TYPE,tar.ALLOT_STATUS,pose.ORG_ID from TC_POSE pose  \n") ;
		sql.append("    inner join TR_USER_POSE tup on pose.POSE_ID = tup.POSE_ID \n") ;
		sql.append("	inner join TC_USER user on tup.USER_ID = user.USER_ID\n") ; 
		sql.append("	inner join TM_ORG TOR2 on (tor2.org_id = "+ org +" AND TOR2.ORG_LEVEL = 1 ) \n") ; 
		sql.append("	inner join TM_ORG TOR3 on (TOR3.org_id = pose.ORG_ID and TOR3.PARENT_ORG_ID = TOR2.ORG_ID and TOR3.ORG_LEVEL = 2 )\n") ; 
		sql.append("	inner join TM_ALLOT_RESOURCE_DCS tar on tar.ALLOT_TARGET_ID = TOR3.ORG_ID\n") ; 
		sql.append("	where tar.ALLOT_DATE='"+ allotDate +"'\n") ;
		sql.append("	and tar.ADJUST_NUM>0\n") ;
		sql.append("	and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_LARGEREGION +"\n") ;
		sql.append("	and user.email <> '' and user.EMAIL <> 'chrysler-dms@yonyou.com'\n") ; 
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list ; 
	}
	/**
	 * 查询所分配的车系
	 * @param allotDate
	 * @author huyu
	 * @return
	 */
	public List<Map> findSeriesName(String allotDate, String orgId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct(case when tt2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND) then '大切诺基' else tt2.GROUP_NAME end) SERIES_NAME\n");
		sql.append("	from TM_ALLOT_RESOURCE_DCS tar,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt ,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt1,\n");
		sql.append("		TM_VHCL_MATERIAL_GROUP tt2\n");
		sql.append("	where tt.PARENT_GROUP_ID = tt1.GROUP_ID \n");
		sql.append("		and tt1.PARENT_GROUP_ID = tt2.GROUP_ID \n");
		sql.append("		and tar.GROUP_ID = tt.GROUP_ID \n");
		sql.append("		and tar.ALLOT_TARGET_ID = "+ orgId +"\n");
		sql.append("		and tt2.STATUS=10011001  AND tt2.GROUP_LEVEL=2 \n");
		sql.append("		and tar.ALLOT_DATE="+ allotDate +" \n") ;
		sql.append("		and tar.ALLOT_TYPE="+ OemDictCodeConstants.DUTY_TYPE_LARGEREGION +"\n") ;
		sql.append("		and tar.ADJUST_NUM>0\n") ;
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	/**
	 * 资源分配游标数据查询
	 * @param inParameter
	 * @return
	 */
	public List<Map> getCursorTotal(List<Object> inParameter) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ALLOT_TARGET_ID,sum(ADJUST_NUM) ALLOT_NUM \n");
		sql.append("                                         from TM_ALLOT_RESOURCE_DCS \n");
		sql.append("                                         where ALLOT_TYPE=10431003 \n");
		sql.append("                                           and CREATE_BY=? \n");
		sql.append("                                           and ALLOT_DATE=? \n");
		sql.append("                                         group by ALLOT_TARGET_ID; \n");
		return OemDAOUtil.findAll(sql.toString(), inParameter);
	}
	
	/**
	 * 资源分配游标数据查询
	 * @param inParameter
	 * @return
	 */
	public List<Map> getCursorSeries(List<Object> inParameter) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tt1.org_Id ALLOT_TARGET_ID,IFNULL(TT2.SERIES_ID,0) GROUP_ID,IFNULL(TT3.NUM,0) NUM,TT1.STATUS  \n");
		sql.append("										 FROM  \n");
		sql.append("									(SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TM.ORG_NAME,tm.org_id FROM TM_ORG TM,TM_TOTAL_GAP TTG WHERE TM.ORG_ID=TTG.TARGET_ID AND TTG.ALLOT_DATE='"+inParameter.get(1)+"'  AND TTG.STATUS=0 AND ttg.gap_type=10431003) \n");
		sql.append("										TT1 LEFT JOIN \n");
		sql.append("									(SELECT GROUP_ID SERIES_ID,GROUP_CODE,GROUP_NAME SERIES_NAME FROM TM_VHCL_MATERIAL_GROUP  WHERE 1=1  AND STATUS=10011001  AND GROUP_LEVEL=2) \n");
		sql.append("										TT2 ON TT2.SERIES_ID=TT1.SERIES_ID   LEFT JOIN \n");
		sql.append("									(SELECT tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID series_id,tvmg2.GROUP_CODE,SUM(tar.ADJUST_NUM) NUM \n");
		sql.append("                                     FROM TM_ALLOT_RESOURCE_DCS              tar,   \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP         tvmg4, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP         tvmg3, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP         tvmg2 \n");
		sql.append("                                       WHERE tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("                                         AND tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("                                         AND tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("                                         AND tar.ALLOT_TYPE=10431003 \n");
		sql.append("                                         AND tvmg4.GROUP_LEVEL = 4 \n");
		sql.append("                                         AND tvmg3.GROUP_LEVEL = 3 \n");
		sql.append("                                         AND tvmg2.GROUP_LEVEL = 2 \n");
		sql.append("                                         AND tar.CREATE_BY='"+inParameter.get(0)+"' \n");
		sql.append("                                         AND tar.ALLOT_DATE='"+inParameter.get(1)+"' \n");
		sql.append("                                       GROUP BY tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_CODE \n");
		sql.append("                                       ORDER BY NUM) TT3 ON TT3.SERIES_ID=TT2.SERIES_ID \n");
		sql.append("								UNION  ALL \n");
		sql.append("								SELECT TAR.ALLOT_TARGET_ID,IFNULL(TVMG2.GROUP_ID,0) series_id,SUM(tar.ADJUST_NUM) NUM,1 AS STATUS \n");
		sql.append("										FROM	TM_ALLOT_RESOURCE_DCS     		   TAR, \n");
		sql.append("												TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("												TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("												TM_VHCL_MATERIAL_GROUP         TVMG2, \n");
		sql.append("												 TM_ORG 					   TOR \n");
		sql.append("										WHERE   TAR.GROUP_ID = TVMG4.GROUP_ID AND TOR.ORG_ID=TAR.ALLOT_TARGET_ID \n");
		sql.append("												AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("												AND TAR.CREATE_BY ='"+inParameter.get(0)+"' \n");
		sql.append("												AND TAR.ALLOT_DATE='"+inParameter.get(1)+"' \n");
		sql.append("												AND TAR.ALLOT_TYPE= 10431003 \n");
		sql.append("												AND TAR.ALLOT_STATUS=0                        \n");
		sql.append("                        GROUP BY tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_CODE \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp3
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp3list(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ttg.GROUP_ID,tor.PARENT_ORG_ID from TM_TOTAL_GAP ttg,TM_ORG tor \n");
		sql.append("  where ttg.TARGET_ID=tor.ORG_ID and ttg.GAP_TYPE=10431004 and ttg.ALLOT_DATE='"+allotDate+"' and  ttg.CREATE_BY="+userId+" \n");
		sql.append("  group by ttg.GROUP_ID,tor.PARENT_ORG_ID having sum(ttg.GAP)=0 \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 资源分配temp4
	 * @param allotDate
	 * @param groupId
	 * @param parentOrgId
	 * @return
	 */
	public List<Map> getTemp4List(String allotDate, Long groupId, Long parentOrgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ttg.TM_GAP_ID from TM_TOTAL_GAP ttg,TM_ORG tor  \n");
		sql.append("where ttg.TARGET_ID=tor.ORG_ID and ttg.GAP_TYPE=10431004 and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("and tor.PARENT_ORG_ID="+parentOrgId+" and ttg.GROUP_ID="+groupId+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 资源分配批处理1
	 * @param tmpGapId
	 */
	public void exceBatch1Temp(Long tmpGapId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set GAP2=SALE_AMOUNT where TM_GAP_ID="+tmpGapId+" \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 嵌套循环1
	 * @param userId
	 * @param allotDate
	 * @param s_org_id
	 * @param s_series_id
	 * @return
	 */
	public List<Map> getTemp5List(Long userId, String allotDate, Long s_org_id, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID SERIES_ID,TVMG2.GROUP_NAME SERIES_NAME,TVMG4.GROUP_ID,sum(TAR.ADJUST_NUM) NUM \n");
		sql.append("                                     from TM_ALLOT_RESOURCE_DCS          TAR,   \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG4, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG3, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG2, \n");
		sql.append("                                           TM_ORG                     TOR \n");
		sql.append("                                       where TAR.GROUP_ID=TVMG4.GROUP_ID \n");
		sql.append("                                         and TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID \n");
		sql.append("                                         and TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("                                         and TAR.ALLOT_TARGET_ID=TOR.ORG_ID \n");
		sql.append("                                         and TAR.ALLOT_TYPE=10431003 \n");
		sql.append("                                         and TAR.CREATE_BY="+userId+" \n");
		sql.append("                                         and ALLOT_TARGET_ID="+s_org_id+" \n");
		sql.append("                                         and TVMG2.GROUP_ID="+s_series_id+" \n");
		sql.append("                                         and TAR.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                                       group by TAR.ALLOT_TARGET_ID,TVMG2.GROUP_ID,TVMG2.GROUP_NAME,TVMG4.GROUP_ID \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 嵌套循环2
	 * @param userId
	 * @param allotDate
	 * @param s_org_id
	 * @param s_series_id
	 * @return
	 */
	public List<Map> getTemp6List(String groupId, Long userId, String allotDate, Long s_org_id, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TVMG2.GROUP_NAME SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TAR.COLOR_NAME,sum(TAR.ADJUST_NUM) NUM \n");
		sql.append("                                             from TM_ALLOT_RESOURCE_DCS          TAR,   \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG4, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG3, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG2                       \n");
		sql.append("                                               where TAR.GROUP_ID=TVMG4.GROUP_ID \n");
		sql.append("                                                 and TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID \n");
		sql.append("                                                 and TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("                                                 and TAR.ALLOT_TYPE=10431003 \n");
		sql.append("                                                 and TAR.CREATE_BY="+userId+" \n");
		sql.append("                                                 and TVMG2.GROUP_ID="+s_series_id+" \n");
		sql.append("                                                 and TAR.ALLOT_TARGET_ID="+s_org_id+" \n");
		sql.append("                                                 and TAR.GROUP_ID="+groupId+" \n");
		sql.append("                                                 and TAR.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                                                group by TVMG2.GROUP_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TAR.COLOR_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 嵌套循环3
	 * @param userId
	 * @param allotDate
	 * @param s_org_id
	 * @param s_series_id
	 * @return
	 */
	public List<Map> getTemp7List(String month_day_start, String month_day_end, Long userId, String allotDate,
			Long s_org_id, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select distinct ttg.TARGET_ID,(case when ifnull(ttg.GAP2,0)>0 then ttg.GAP2 else ttg.GAP end) GAP \n");
		sql.append(" from TM_TOTAL_GAP      ttg, \n");
		sql.append("       TM_ORG            tor \n");
		sql.append("                                                     where ttg.TARGET_ID = tor.ORG_ID \n");
		sql.append(" and ttg.GAP_TYPE=10431004 \n");
		sql.append(" and ttg.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append(" and ttg.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append(" and ttg.CREATE_BY="+userId+" \n");
		sql.append(" and ttg.GROUP_ID="+s_series_id+" \n");
		sql.append(" and tor.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append(" and ttg.ALLOT_DATE='"+allotDate+"' \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取Gap_total
	 * @param userId
	 * @param allotDate
	 * @param s_org_id
	 * @param s_series_id
	 * @return
	 */
	public int getGapTotal(Long userId, String allotDate, Long s_org_id, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT (CASE WHEN SUM(GAP2)>0 THEN SUM(GAP2) ELSE SUM(GAP) END)  GAP_TOTAL \n");
		sql.append("FROM TM_TOTAL_GAP  ttg, TM_ORG   tor WHERE ttg.TARGET_ID = tor.ORG_ID AND ttg.GAP_TYPE=10431004 \n");
		sql.append("AND ttg.CREATE_BY=USER_ID AND ttg.ALLOT_DATE=allotDate AND ttg.GROUP_ID=s_series_id  AND tor.PARENT_ORG_ID=s_org_id \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP_TOTAL"),"0"));
		}else{
			return 0;
		}
	}

	/**
	 * 资源分配 temp8
	 * @param group_Id
	 * @param userId
	 * @param allotDate
	 * @param s_series_id
	 * @return
	 */
	public List<Map> getTemp8List(double group_Id, Long userId, String allotDate, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" select  (case when IFNULL(ttg.GAP2,0)>0 then ttg.GAP2 else ttg.GAP end) total_gap_num,IFNULL(t.ALLOT_NUM,0) total_allot_num \n");
		sql.append("                                            from TM_TOTAL_GAP  ttg \n");
		sql.append("                                            left join (select tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,sum(tar.ADJUST_NUM) ALLOT_NUM \n");
		sql.append("       from TM_ALLOT_RESOURCE_dcs             tar, \n");
		sql.append("             TM_ORG                        tor, \n");
		sql.append("             TM_VHCL_MATERIAL_GROUP        tvmg4, \n");
		sql.append("             TM_VHCL_MATERIAL_GROUP        tvmg3, \n");
		sql.append("             TM_VHCL_MATERIAL_GROUP        tvmg2  \n");
		sql.append("       where tar.ALLOT_TARGET_ID=tor.ORG_ID \n");
		sql.append("         and tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("         and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("         and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("         and tvmg4.GROUP_LEVEL=4 \n");
		sql.append("         and tvmg3.GROUP_LEVEL=3 \n");
		sql.append("         and tvmg2.GROUP_LEVEL=2 \n");
		sql.append("         and tar.ALLOT_TYPE=10431004 \n");
		sql.append("         and tar.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("         and tar.CREATE_BY="+userId+" \n");
		sql.append("      group by tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID) t \n");
		sql.append("                                           on ttg.TARGET_ID=t.ALLOT_TARGET_ID and ttg.GROUP_ID=t.GROUP_ID \n");
		sql.append("                                           where ttg.GAP_TYPE=10431004 \n");
		sql.append("                                              and ttg.CREATE_BY="+userId+" \n");
		sql.append("                                              and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                                             and ttg.TARGET_ID="+group_Id+" \n");
		sql.append("                                             and ttg.GROUP_ID="+s_series_id+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 资源分配 temp 9 
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp9List(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tor.PARENT_ORG_ID,tor.ORG_ID,tagt.SERIES_ID,tagt.GROUP_ID,tagt.COLOR_NAME,SUM(tagt.NUM) NUM  \n");
		sql.append("                  FROM TM_ALLOT_GROUP_TEMP_dcs          tagt, \n");
		sql.append("                        TM_ORG                       tor \n");
		sql.append("                  WHERE tagt.TARGET_ID=tor.ORG_ID  \n");
		sql.append("                    AND tagt.ALLOT_TYPE=10431004 \n");
		sql.append("                    AND tagt.CREATE_BY="+userId+" \n");
		sql.append("                    AND tagt.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                GROUP BY tor.PARENT_ORG_ID,tor.ORG_ID,tagt.SERIES_ID,tagt.GROUP_ID,tagt.COLOR_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 资源分配 temp 10
	 * @param userId
	 * @param allotDate
	 * @param seriesId
	 * @param parentOrgId
	 * @return
	 */
	public List<Map> getTemp10List(Long userId, String allotDate, Long seriesId, Long parentOrgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ttg.TARGET_ID,ttg.SALE_AMOUNT GAP  \n");
		sql.append("                          FROM TM_TOTAL_GAP                 ttg, \n");
		sql.append("                                TM_ORG                       tor \n");
		sql.append("                          WHERE ttg.TARGET_ID=tor.ORG_ID  \n");
		sql.append("                            AND ttg.GAP_TYPE=10431004 \n");
		sql.append("                            AND ttg.GROUP_ID="+seriesId+" \n");
		sql.append("                            AND ttg.CREATE_BY="+userId+" \n");
		sql.append("                            AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                            AND tor.PARENT_ORG_ID="+parentOrgId+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取GAP_TOTAl
	 * @param userId
	 * @param allotDate
	 * @param seriesId
	 * @param parentOrgId
	 * @return
	 */
	public int getGapTotal2(Long userId, String allotDate, Long seriesId, Long parentOrgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT IFNULL(SUM(ttg.SALE_AMOUNT),0) GAP_TOTAL \n");
		sql.append("                FROM TM_TOTAL_GAP            ttg, \n");
		sql.append("                      TM_ORG                  tor \n");
		sql.append("                WHERE ttg.TARGET_ID=tor.ORG_ID    \n");
		sql.append("                  AND ttg.GAP_TYPE=10431004 \n");
		sql.append("                  AND tor.PARENT_ORG_ID="+parentOrgId+" \n");
		sql.append("                  AND ttg.CREATE_BY="+userId+" \n");
		sql.append("                  AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                  AND ttg.GROUP_ID="+seriesId+" \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP_TOTAL"), "0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 获取temparr
	 * @param d
	 * @param userId
	 * @param allotDate
	 * @param seriesId
	 * @param parentOrgId
	 * @param colorName 
	 * @return
	 */
	public double getTempArr(double d, Long userId, String allotDate, Long seriesId, Long parentOrgId, String colorName) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT DISTINCT TM_ALLOT_ID TEMP_ARR FROM TM_ALLOT_RESOURCE_DCS \n");
		sql.append("WHERE ALLOT_TYPE=10431004 AND ALLOT_TARGET_ID="+d+" \n");
		sql.append("AND GROUP_ID="+seriesId+" AND COLOR_NAME='"+colorName+"' \n");
		sql.append("AND CREATE_BY="+userId+" AND ALLOT_DATE='"+allotDate+"' \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Double.parseDouble(CommonUtils.checkNull(map.get("TEMP_ARR"), "0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 批量处理二 
	 * @param d
	 * @param e
	 * @param seriesId
	 * @param colorName
	 * @param userId
	 * @param allotDate
	 */
	public void excebatch2Temp(double d, double e, Long seriesId, String colorName, Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_ALLOT_RESOURCE_DCS set ALLOT_NUM=ALLOT_NUM+"+d+",ADJUST_NUM=ADJUST_NUM+"+d+" \n");
		sql.append("                        where ALLOT_TYPE=10431004 and ALLOT_TARGET_ID="+e+" and GROUP_ID="+seriesId+" and COLOR_NAME='"+colorName+"' \n");
		sql.append("                          and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"';  \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp12
	 * @return
	 */
	public List<Map> getTemp11List() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TVMG2.GROUP_ID,TVMG2.GROUP_CODE,count(*) NUM \n");
		sql.append("                 from TMP_UPLOAD_RESOURCE            TUR,   \n");
		sql.append("                       TM_VEHICLE_DEC                     TV, \n");
		sql.append("                       TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                   where TUR.VIN = TV.VIN \n");
		sql.append("                     and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                     and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                     and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                     and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                     and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                     and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                     and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                     and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                     and TVMG2.GROUP_ID in(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		sql.append("                   group by TVMG2.GROUP_ID,TVMG2.GROUP_CODE \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp12
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp12List(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2,IFNULL(NUM22,0) NUM22,IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,0,IFNULL(NUM4,0) NUM4,IFNULL(NUM5,0) NUM5,GAP \n");
		sql.append("from TM_TOTAL_GAP \n");
		sql.append("where GAP_TYPE=10431004 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 批处理3
	 * @param map
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatch3Temp(Map map, Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set SALE_AMOUNT="+Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"))+",NUM1=nvl(NUM1,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"))+",NUM2=nvl(NUM2,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"))+", \n");
		sql.append("NUM22=nvl(NUM22,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM22"),"0"))+",NUM3=nvl(NUM3,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"))+",NUM33=nvl(NUM33,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"))+",NUM4=nvl(NUM4,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"))+", \n");
		sql.append("NUM5=nvl(NUM5,0)+"+Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"))+" \n");
		sql.append("where TARGET_ID="+Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"))+" and  GAP_TYPE=10431004 and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 批处理4
	 * @param allotDate
	 */
	public void exceBatch4Temp(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP=SALE_AMOUNT-NUM1-NUM2-NUM3-NUM4-NUM5  \n");
		sql.append("where GAP_TYPE=10431004 and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	/**
	 * 批处理5
	 * @param allotDate
	 */
	public void exceBatch5Temp(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP=0  where GAP<0 and GAP_TYPE=10431004 \n");
		sql.append("and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	
	/**
	 * temp1
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp1List(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,GAP,CREATE_BY,ALLOT_DATE from TM_TOTAL_GAP \n");
		sql.append("where GAP_TYPE=10431004 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp1 批量执行
	 * @param sALE_AMOUNT
	 * @param tARGET_ID
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatch1Temp(Integer sALE_AMOUNT, Long tARGET_ID, Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set SALE_AMOUNT="+sALE_AMOUNT+"   \n");
		sql.append(" where  GAP_TYPE=10431004 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+tARGET_ID+" \n");
		sql.append(" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	/**
	 * temp2
	 * @param userId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp2List(Long userId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2, \n");
		sql.append("IFNULL(NUM22,0) NUM22,IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,IFNULL(NUM4,0) NUM4, \n");
		sql.append("IFNULL(NUM5,0) NUM5,CREATE_BY,ALLOT_DATE \n");
		sql.append("FROM TM_TOTAL_GAP \n");
		sql.append("WHERE GAP_TYPE=10431004 AND CREATE_BY="+userId+" AND ALLOT_DATE='"+allotDate+"' \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=30) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * temp2 批量执行
	 * @param sALE_AMOUNT
	 * @param tARGET_ID
	 * @param userId
	 * @param allotDate
	 */
	public void exceBatch2Temp(Map map, Long userId, String allotDate) {
		Integer NUM1 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"));
		Integer NUM2 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"));
		Integer NUM3 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"));
		Integer NUM4 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"));
		Integer NUM5 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"));
		Integer NUM33 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"));
		Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" update TM_TOTAL_GAP set NUM33=NUM33+"+NUM33+", \n");
		sql.append(" GAP=SALE_AMOUNT-IFNULL(NUM1,0)-"+NUM1+"-IFNULL(NUM2,0)-"+NUM2+"-IFNULL(NUM3,0)-"+NUM3+"-IFNULL(NUM33,0)-IFNULL(NUM4,0)-"+NUM4+"-IFNULL(NUM5,0)-"+NUM5+" \n");
		sql.append(" where  GAP_TYPE=10431004 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		
		StringBuffer sql2 = new StringBuffer("\n");
		sql2.append("update TM_TOTAL_GAP set GAP=0 \n");
		sql2.append("where GAP<0 and GAP_TYPE=10431004 and CREATE_BY="+userId+" and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql2.toString(), new ArrayList<>());
	}
	
	/**
	 * 获取游标1
	 * @param inParam
	 * @return
	 */
	public List<Map> getCursorTotal1(List<Object> inParam) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ALLOT_TARGET_ID,sum(ADJUST_NUM) ALLOT_NUM \n");
		sql.append("                                         from TM_ALLOT_RESOURCE_DCS \n");
		sql.append("                                         where ALLOT_TYPE=10431004 \n");
		sql.append("                                           and CREATE_BY=? \n");
		sql.append("                                           and ALLOT_DATE=? \n");
		sql.append("                                         group by ALLOT_TARGET_ID; \n");
		return OemDAOUtil.findAll(sql.toString(), inParam);
	}


	/**
	 * 获取游标2
	 * @param inParam
	 * @return
	 */
	public List<Map> getCursorSeries1(List<Object> inParam) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("								 SELECT tt3.org_id allot_target_id,tt1.*,IFNULL(tt2.num,0) num FROM \n");
		sql.append("										(SELECT TTG.STATUS,TTG.GROUP_ID SERIES_ID,TM.ORG_NAME,tm.org_id FROM TM_ORG TM,TM_TOTAL_GAP TTG WHERE TM.ORG_ID=TTG.TARGET_ID AND TTG.ALLOT_DATE='"+CommonUtils.checkNull(inParam.get(1))+"' AND TTG.STATUS=0 AND \n");
		sql.append("											ttg.gap_type=10431004) \n");
		sql.append("										TT3 LEFT JOIN \n");
		sql.append("										(SELECT group_id,group_name FROM Tm_Vhcl_Material_Group \n");
		sql.append("											WHERE 1=1  AND STATUS=10011001  AND Group_Level=2 \n");
		sql.append("										)tt1 ON tt1.group_id=tt3.series_id \n");
		sql.append("									   LEFT JOIN ( SELECT tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME,SUM(tar.ADJUST_NUM) NUM \n");
		sql.append("												FROM   TM_ALLOT_RESOURCE_DCS              tar,   \n");
		sql.append("													   TM_VHCL_MATERIAL_GROUP         tvmg4, \n");
		sql.append("													   TM_VHCL_MATERIAL_GROUP         tvmg3, \n");
		sql.append("													   TM_VHCL_MATERIAL_GROUP         tvmg2, \n");
		sql.append("													   TM_ORG                         tor \n");
		sql.append("												   WHERE tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("													 AND tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("													 AND tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("													 AND tar.ALLOT_TARGET_ID=tor.org_id \n");
		sql.append("													 AND tar.ALLOT_TYPE=10431004 \n");
		sql.append("													 AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("													 AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("													 AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("													 AND tor.PARENT_ORG_ID="+CommonUtils.checkNull(inParam.get(0))+" \n");
		sql.append("													 AND tar.ALLOT_DATE='"+CommonUtils.checkNull(inParam.get(1))+"' \n");
		sql.append("												   GROUP BY tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME \n");
		sql.append("												   \n");
		sql.append("												   ORDER BY NUM DESC \n");
		sql.append("												) tt2 ON tt1.group_id=tt2.group_id                  \n");
		sql.append("									UNION ALL \n");
		sql.append("									  SELECT tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME,SUM(tar.ADJUST_NUM) NUM \n");
		sql.append("															FROM   TM_ALLOT_RESOURCE_DCS              tar,   \n");
		sql.append("																   TM_VHCL_MATERIAL_GROUP         tvmg4, \n");
		sql.append("																   TM_VHCL_MATERIAL_GROUP         tvmg3, \n");
		sql.append("																   TM_VHCL_MATERIAL_GROUP         tvmg2, \n");
		sql.append("																   TM_ORG                         tor \n");
		sql.append("															   WHERE tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("																 AND tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("																 AND tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("																 AND tar.ALLOT_TARGET_ID=tor.org_id \n");
		sql.append("																 AND tar.ALLOT_TYPE=10431004 \n");
		sql.append("																 AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("																 AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("																 AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("																 AND tor.PARENT_ORG_ID="+CommonUtils.checkNull(inParam.get(0))+" \n");
		sql.append("																 AND tar.ALLOT_DATE='"+CommonUtils.checkNull(inParam.get(1))+"' \n");
		sql.append("															   GROUP BY tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME \n");
		sql.append("															   \n");
		sql.append("															   ORDER BY NUM desc \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	
	/**
	 * 资源分配批量
	 * @param s_status 
	 * @param s_org_id 
	 */
	public void save1(int s_status, String month_day_start, String month_day_end, Integer year, Integer month, Long s_series_id,
			Long s_org_id, List<Object> inParameter, int playVer) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("INSERT INTO TM_TOTAL_GAP(GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,NUM1,NUM2,NUM22,NUM3,NUM33,NUM4,NUM5,GAP,ALLOT_DATE,AUTHOR_TYPE,CREATE_BY,CREATE_DATE,STATUS)     \n");
		sql.append("                                         select  10431004,t.GROUP_ID,t.ORG_ID,t.SALE_AMOUNT,t.NUM1,t.NUM2,t.NUM22,t.NUM3,t.NUM33,t.NUM4,t.NUM5,(case when t.GAP>0 then t.GAP else 0 end) GAP,'"+inParameter.get(1)+"',10431004,"+inParameter.get(0)+",now(),"+s_status+" \n");
		sql.append("                                            from (select tab.GROUP_ID,tab.ORG_ID,tab.SALE_AMOUNT,tab1.NUM1,tab2.NUM2,tab22.NUM22,tab3.NUM3,tab33.NUM33,tab4.NUM4,tab5.NUM5, \n");
		sql.append("                                                  (tab.SALE_AMOUNT-IFNULL(tab1.NUM1,0)-IFNULL(tab2.NUM2,0)-IFNULL(tab3.NUM3,0)-IFNULL(tab4.NUM4,0)-IFNULL(tab5.NUM5,0)) as GAP \n");
		sql.append("                                           from ( \n");
		sql.append("                                            select  TVMPD.MATERIAL_GROUPID GROUP_ID,TOR.ORG_ID,sum(TVMPD.SALE_AMOUNT) SALE_AMOUNT \n");
		sql.append("                                               from TT_VS_MONTHLY_PLAN             TVMP, \n");
		sql.append("                                                     TT_VS_MONTHLY_PLAN_DETAIL      TVMPD, \n");
		sql.append("                                                     TM_DEALER                      TD, \n");
		sql.append("                                                     TM_ORG                         TOR, \n");
		sql.append("                                                     TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("                                               where TVMP.PLAN_ID = TVMPD.PLAN_ID \n");
		sql.append("                                                 and TVMP.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                                 and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                                 and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                                 and TVMP.PLAN_TYPE=20781001 \n");
		sql.append("                                                 and TVMP.PLAN_YEAR="+year+" \n");
		sql.append("                                                 and TVMP.PLAN_MONTH ="+month+" \n");
		sql.append("                                                 and TVMP.PLAN_VER="+playVer+" \n");
		sql.append("                                                 and tvmp.task_id is null \n");
		sql.append("                                                 and TVMPD.MATERIAL_GROUPID='"+s_series_id+"' \n");
		sql.append("                                                 and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("                                               group by  TVMPD.MATERIAL_GROUPID, TOR.ORG_ID ) tab \n");
		sql.append("                                           left join ( \n");
		sql.append("                                           select TVMG2.GROUP_ID,TOR.ORG_ID,count(*) NUM1 \n");
		sql.append("                                                from TM_LATELY_MONTH                TLM, \n");
		sql.append("TM_VEHICLE_DEC                     TV, \n");
		sql.append("TM_VHCL_MATERIAL               TVM, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG2,                     \n");
		sql.append("TM_DEALER                      TD, \n");
		sql.append("TM_ORG                         TOR, \n");
		sql.append("TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("                                                where TLM.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                                  and TLM.VIN = TV.VIN \n");
		sql.append("                                                  and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                                  and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                                  and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                                  and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                                  and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                                  and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                                  and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                                  and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                                                  and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                                                  and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                                                  and TLM.ALLOT_DATE='"+inParameter.get(1)+"' \n");
		sql.append("                                                  and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                                  and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("                                                group by TVMG2.GROUP_ID,TOR.ORG_ID )tab1 on tab1.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           left join (select tt2.GROUP_ID,tt2.ORG_ID,count(*) NUM2 \n");
		sql.append("                                            from (select tt1.*,tas.IS_SUPPORT \n");
		sql.append(" from (select TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("           from TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            where TVO.VIN = TV.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("              and TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811002 \n");
		sql.append("              and TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TV.NODE_STATUS IN (11511019) \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null) \n");
		sql.append("      union \n");
		sql.append("      select TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("           from TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            where TVO.VIN = TV.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("              and TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811002 \n");
		sql.append("              and TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null)) tt1 \n");
		sql.append("                                                     left join TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     on tt1.VIN=tas.VIN \n");
		sql.append("                                                     where IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           group by tt2.GROUP_ID,tt2.ORG_ID ) tab2 on tab2.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           left join ( \n");
		sql.append("                                            select TVMG2.GROUP_ID GROUP_ID,TVCR.RESOURCE_SCOPE ORG_ID,count(1) NUM22 \n");
		sql.append("                                              from TT_VS_COMMON_RESOURCE           TVCR, \n");
		sql.append("                                                   TT_VS_COMMON_RESOURCE_DETAIL     TVCRD, \n");
		sql.append("                                                   TM_VEHICLE_DEC                       TV, \n");
		sql.append("                                                   ("+getVwMaterialSql()+")                      VM, \n");
		sql.append("                                                   TM_VHCL_MATERIAL                 TVM, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP_R         TVMGR, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG4, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG3, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG2 \n");
		sql.append("                                             where TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("                                               and TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("                                               and TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                               and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                               and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                               and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                               and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                               and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                               and TVCR.STATUS = 20801002 \n");
		sql.append("                                               and TVCR.TYPE = 20811002 \n");
		sql.append("                                               and TVCRD.STATUS = 10011001 \n");
		sql.append("                                               and TV.NODE_STATUS = 11511018 \n");
		sql.append("                                               and TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("                                               and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                               and not exists (select 1  from TT_VS_ORDER TOR where TOR.ORDER_STATUS < 20071008  and TOR.VIN=TV.VIN) \n");
		sql.append("                                             group by TVCR.RESOURCE_SCOPE,TVMG2.GROUP_ID \n");
		sql.append("                                         ) tab22 on tab22.GROUP_ID=tab.GROUP_ID \n");
		sql.append("                                           left join ( \n");
		sql.append("                                        select tt2.GROUP_ID,tt2.ORG_ID,count(*) NUM3 \n");
		sql.append("                                            from (select tt1.*,tas.IS_SUPPORT \n");
		sql.append(" from (select TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("           from TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            where TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              and TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              and TOR2.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID  \n");
		sql.append("              and TD.DEALER_ID = TVO.DEALER_ID \n");
		sql.append("              and TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              and TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              and TV.VIN = TVO.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811002 \n");
		sql.append("              and TVCRD.STATUS = 10011001 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              and TV.NODE_STATUS IN (11511019) \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR2.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null) \n");
		sql.append("      union \n");
		sql.append("      select TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("           from TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            where TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              and TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              and TOR2.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID  \n");
		sql.append("              and TD.DEALER_ID = TVO.DEALER_ID \n");
		sql.append("              and TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              and TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              and TV.VIN = TVO.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811002 \n");
		sql.append("              and TVCRD.STATUS = 10011001 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR2.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null)) tt1 \n");
		sql.append("                                                     left join TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     on tt1.VIN=tas.VIN \n");
		sql.append("                                                     where IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                             group by tt2.GROUP_ID,tt2.ORG_ID                                             \n");
		sql.append("                                        )tab3 on tab3.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           left join ( \n");
		sql.append("                                        select tt2.GROUP_ID,tt2.ORG_ID,count(*) NUM33 \n");
		sql.append("                                            from (select tt1.*,tas.IS_SUPPORT \n");
		sql.append(" from (select TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("           from TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            where TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              and TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              and TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              and TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811002 \n");
		sql.append("              and TVCRD.STATUS = 10011001 \n");
		sql.append("              and TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              and TVCR.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append("              and TVCR.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR.ORG_ID="+s_org_id+" \n");
		sql.append("              and not exists (select 1  from TT_VS_ORDER TOR where TOR.ORDER_STATUS < 20071008  and TOR.VIN=TV.VIN)) tt1 \n");
		sql.append("                                                     left join TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     on tt1.VIN=tas.VIN \n");
		sql.append("                                                     where IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                             group by tt2.GROUP_ID,tt2.ORG_ID \n");
		sql.append("                                        )tab33 on tab33.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           left join ( \n");
		sql.append("                                        select tt2.GROUP_ID,tt2.ORG_ID,count(*) NUM4 \n");
		sql.append("                                            from (select tt1.*,tas.IS_SUPPORT \n");
		sql.append(" from ( select TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("           from TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,        \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("            where TVO.VIN=TV.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                 \n");
		sql.append("              and TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TVO.ORDER_TYPE=20831003 \n");
		sql.append("              and TVO.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append("              and TVO.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null)) tt1 \n");
		sql.append("                                                     left join TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     on tt1.VIN=tas.VIN \n");
		sql.append("                                                     where IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                             group by tt2.GROUP_ID,tt2.ORG_ID     \n");
		sql.append("                                        ) tab4 on tab4.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           left join ( \n");
		sql.append("                                        select tt2.GROUP_ID,tt2.ORG_ID,count(*) NUM5 \n");
		sql.append("                                            from (select tt1.*,tas.IS_SUPPORT \n");
		sql.append(" from (select TVMG2.GROUP_ID,TOR.ORG_ID,TV.VIN \n");
		sql.append("           from TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,    \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            where TVO.VIN = TV.VIN \n");
		sql.append("              and TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              and TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              and TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              and TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              and TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              and TVO.DEALER_ID = TD.DEALER_ID                       \n");
		sql.append("              and TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              and TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              and TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              and TVCR.STATUS = 20801002 \n");
		sql.append("              and TVCR.TYPE = 20811001 \n");
		sql.append("              and TVO.ORDER_STATUS<20071008 \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              and TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              and TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              and TOR.PARENT_ORG_ID="+s_org_id+" \n");
		sql.append("              and exists (select 1 from TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          where tvmp.PLAN_ID = tvmpd.PLAN_ID and tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            and tvmp.PLAN_TYPE=20781001 and tvmp.PLAN_YEAR="+year+" and tvmp.PLAN_MONTH ="+month+" and tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            and tvmpd.MATERIAL_GROUPID='"+s_series_id+"' and tvmp.task_id is null)) tt1 \n");
		sql.append("                                                     left join TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     on tt1.VIN=tas.VIN \n");
		sql.append("                                                     where IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                             group by tt2.GROUP_ID,tt2.ORG_ID  \n");
		sql.append("                                        ) tab5 on tab5.ORG_ID=tab.ORG_ID) t \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 游标3
	 * @param inParam
	 * @return
	 */
	public List<Map> getCursorSeries2(List<Object> inParam) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME,sum(tar.ADJUST_NUM) NUM \n");
		sql.append("									from   TM_ALLOT_RESOURCE_DCS              tar,   \n");
		sql.append("										   TM_VHCL_MATERIAL_GROUP         tvmg4, \n");
		sql.append("										   TM_VHCL_MATERIAL_GROUP         tvmg3, \n");
		sql.append("										   TM_VHCL_MATERIAL_GROUP         tvmg2, \n");
		sql.append("										   TM_ORG                         tor \n");
		sql.append("									   where tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("										 and tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("										 and tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("										 and tar.ALLOT_TARGET_ID=tor.ORG_ID \n");
		sql.append("										 and tar.ALLOT_TYPE=10431004 \n");
		sql.append("										 and TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("										 and TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("										 and TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("										 and tor.PARENT_ORG_ID=? \n");
		sql.append("										 and tar.ALLOT_DATE=? \n");
		sql.append("									   group by tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,tvmg2.GROUP_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), inParam);
	}
	
	/**
	 * 
	 * @param month_day_start
	 * @param month_day_end
	 * @param year
	 * @param month
	 * @param s_org_id
	 * @param s_series_id
	 * @param inParam
	 * @param playVer
	 */
	public void save2(String month_day_start, String month_day_end, Integer year, Integer month, Long s_org_id,
			Long s_series_id, List<Object> inParam, int playVer) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("                                        INSERT INTO TM_TOTAL_GAP(GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,NUM1,NUM2,NUM22,NUM3,NUM33,NUM4,NUM5,GAP,ALLOT_DATE,AUTHOR_TYPE,CREATE_BY,CREATE_DATE)   \n");
		sql.append("                                         SELECT  10431005,t.GROUP_ID,t.DEALER_ID,t.SALE_AMOUNT,t.NUM1,t.NUM2,t.NUM22,t.NUM3,t.NUM33,t.NUM4,t.NUM5,(CASE WHEN t.GAP>0 THEN t.GAP ELSE 0 END) GAP, \n");
		sql.append("                                            '"+CommonUtils.checkNull(inParam.get(1))+"',10431005,"+CommonUtils.checkNull(inParam.get(0))+",now() \n");
		sql.append("                                            FROM (SELECT tab.GROUP_ID,tab.DEALER_ID,tab.SALE_AMOUNT,tab1.NUM1,tab2.NUM2,tab22.NUM22,tab3.NUM3,tab33.NUM33,tab4.NUM4,tab5.NUM5, \n");
		sql.append("                                                  (tab.SALE_AMOUNT-IFNULL(tab1.NUM1,0)-IFNULL(tab2.NUM2,0)-IFNULL(tab3.NUM3,0)-IFNULL(tab4.NUM4,0)-IFNULL(tab5.NUM5,0)) AS GAP \n");
		sql.append("                                           FROM (SELECT  TVMPD.MATERIAL_GROUPID GROUP_ID,TOR2.PARENT_ORG_ID,TOR.ORG_ID,TD.DEALER_ID,SUM(TVMPD.SALE_AMOUNT) SALE_AMOUNT \n");
		sql.append("                                               FROM TT_VS_MONTHLY_PLAN             TVMP, \n");
		sql.append("                                                     TT_VS_MONTHLY_PLAN_DETAIL      TVMPD, \n");
		sql.append("                                                     TM_DEALER                      TD, \n");
		sql.append("                                                     TM_ORG                         TOR, \n");
		sql.append("                                                     TM_ORG                         TOR2, \n");
		sql.append("                                                     TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                                                     TM_VHCL_MATERIAL_GROUP         TVMG \n");
		sql.append("                                               WHERE TVMP.PLAN_ID = TVMPD.PLAN_ID \n");
		sql.append("                                                 AND TVMP.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                                 AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                                 AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                                 AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                                                 AND TVMPD.MATERIAL_GROUPID = TVMG.GROUP_ID \n");
		sql.append("                                                 AND TD.STATUS=10011001 \n");
		sql.append("                                                 AND TVMP.PLAN_TYPE=20781001 \n");
		sql.append("                                                 AND TVMP.PLAN_YEAR='"+year+"' \n");
		sql.append("                                                 AND TVMP.PLAN_MONTH ='"+month+"' \n");
		sql.append("                                                 AND TVMP.PLAN_VER="+playVer+" \n");
		sql.append("                                                 AND TVMPD.MATERIAL_GROUPID='"+s_series_id+"' \n");
		sql.append("                                                 AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("                                                   AND tvmp.task_id IS NULL \n");
		sql.append("                                                 AND NOT EXISTS(SELECT 1 FROM TM_RESOURCE_ALLOT_FROZEN t \n");
		sql.append("     WHERE t.DEALER_ID=TD.DEALER_ID AND STATUS=19990001 \n");
		sql.append("       AND t.SERIES_NAME=TVMG.GROUP_NAME \n");
		sql.append("       AND t.GROUP_CODE='' AND t.GROUP_NAME='' AND t.MODEL_YEAR='' AND t.COLOR_CODE='' AND t.COLOR_NAME='') \n");
		sql.append("                                               GROUP BY  TVMPD.MATERIAL_GROUPID,TOR2.PARENT_ORG_ID,TOR.ORG_ID,TD.DEALER_ID) tab \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                             SELECT TVMG2.GROUP_ID,TD.DEALER_ID,COUNT(*) NUM1 \n");
		sql.append("                                                FROM TM_LATELY_MONTH                TLM, \n");
		sql.append("TM_VEHICLE_DEC                     TV, \n");
		sql.append("TM_VHCL_MATERIAL               TVM, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("TM_VHCL_MATERIAL_GROUP         TVMG2,                     \n");
		sql.append("TM_DEALER                      TD, \n");
		sql.append("TM_ORG                         TOR, \n");
		sql.append("TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("                                                WHERE TLM.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                                                  AND TLM.VIN = TV.VIN \n");
		sql.append("                                                  AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                                  AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                                  AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                                  AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                                  AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                                  AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                                                  AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                                                  AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                                                  AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                                                  AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                                                  AND TLM.ALLOT_DATE='"+CommonUtils.checkNull(inParam.get(1))+"' \n");
		sql.append("                                                  AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("                                                  AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("                                                GROUP BY TVMG2.GROUP_ID,TD.DEALER_ID \n");
		sql.append("                                        ) tab1 ON tab1.DEALER_ID=tab.DEALER_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                         SELECT tt2.GROUP_ID,tt2.DEALER_ID,COUNT(*) NUM2 \n");
		sql.append("                                            FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append(" FROM (SELECT TVMG2.GROUP_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            WHERE TVO.VIN = TV.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("              AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811002 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("              AND TV.NODE_STATUS IN (11511019) \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL) \n");
		sql.append("     UNION                        \n");
		sql.append("     SELECT TVMG2.GROUP_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,               \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            WHERE TVO.VIN = TV.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID                           \n");
		sql.append("              AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811002 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL)) tt1 \n");
		sql.append("                                                     LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     ON tt1.VIN=tas.VIN \n");
		sql.append("                                                     WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           GROUP BY tt2.GROUP_ID,tt2.DEALER_ID \n");
		sql.append("                                        ) tab2 ON tab2.DEALER_ID=tab.DEALER_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                             SELECT TVMG2.GROUP_ID GROUP_ID,TVCR.RESOURCE_SCOPE ORG_ID,COUNT(1) NUM22 \n");
		sql.append("                                              FROM TT_VS_COMMON_RESOURCE           TVCR, \n");
		sql.append("                                                   TT_VS_COMMON_RESOURCE_DETAIL     TVCRD, \n");
		sql.append("                                                   TM_VEHICLE_DEC                       TV, \n");
		sql.append("                                                   ("+getVwMaterialSql()+")                      VM, \n");
		sql.append("                                                   TM_VHCL_MATERIAL                 TVM, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP_R         TVMGR, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG4, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG3, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP           TVMG2 \n");
		sql.append("                                             WHERE TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("                                               AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("                                               AND TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("                                               AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                                               AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                                               AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                                               AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                                               AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                                               AND TVCR.STATUS = 20801002 \n");
		sql.append("                                               AND TVCR.TYPE = 20811002 \n");
		sql.append("                                               AND TVCRD.STATUS = 10011001 \n");
		sql.append("                                               AND TV.NODE_STATUS = 11511018 \n");
		sql.append("                                               AND TVCR.RESOURCE_SCOPE=2010010100070674 \n");
		sql.append("                                               AND TVMG2.GROUP_ID='"+s_series_id+"'  \n");
		sql.append("                                               AND NOT EXISTS (SELECT 1  FROM TT_VS_ORDER TOR WHERE TOR.ORDER_STATUS < 20071008  AND TOR.VIN=TV.VIN) \n");
		sql.append("                                             GROUP BY TVCR.RESOURCE_SCOPE,TVMG2.GROUP_ID                                      \n");
		sql.append("                                        ) tab22 ON tab22.ORG_ID=tab.PARENT_ORG_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                        SELECT tt2.GROUP_ID,tt2.DEALER_ID,COUNT(*) NUM3 \n");
		sql.append("                                            FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append(" FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              AND TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              AND TOR2.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID  \n");
		sql.append("              AND TD.DEALER_ID = TVO.DEALER_ID \n");
		sql.append("              AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              AND TV.VIN = TVO.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811002 \n");
		sql.append("              AND TVCRD.STATUS = 10011001 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              AND TV.NODE_STATUS IN (11511019) \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR2.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL) \n");
		sql.append("      UNION              \n");
		sql.append("      SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              AND TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              AND TOR2.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID  \n");
		sql.append("              AND TD.DEALER_ID = TVO.DEALER_ID \n");
		sql.append("              AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              AND TV.VIN = TVO.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811002 \n");
		sql.append("              AND TVCRD.STATUS = 10011001 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR2.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL)) tt1 \n");
		sql.append("                                                     LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     ON tt1.VIN=tas.VIN \n");
		sql.append("                                                     WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           GROUP BY tt2.GROUP_ID,tt2.DEALER_ID                                     \n");
		sql.append("                                        ) tab3 ON tab3.DEALER_ID=tab.DEALER_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                        SELECT tt2.GROUP_ID,tt2.ORG_ID,COUNT(*) NUM33 \n");
		sql.append("                                            FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append(" FROM (SELECT TVMG2.GROUP_ID,TOR2.ORG_ID,TV.VIN \n");
		sql.append("           FROM TM_ORG                         TOR, \n");
		sql.append("                 TM_ORG                         TOR2, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE_DETAIL   TVCRD, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("            WHERE TOR.ORG_ID = TVCR.RESOURCE_SCOPE \n");
		sql.append("              AND TOR2.PARENT_ORG_ID = TOR.ORG_ID \n");
		sql.append("              AND TVCR.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append("              AND TVCR.COMMON_ID = TVCRD.COMMON_ID \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811002 \n");
		sql.append("              AND TVCRD.STATUS = 10011001 \n");
		sql.append("              AND TVCR.RESOURCE_SCOPE!=2010010100070674 \n");
		sql.append("              AND TVCR.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append("              AND TVCR.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR2.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND NOT EXISTS (SELECT 1  FROM TT_VS_ORDER TOR WHERE TOR.ORDER_STATUS < 20071008  AND TOR.VIN=TV.VIN)) tt1 \n");
		sql.append("                                                     LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     ON tt1.VIN=tas.VIN \n");
		sql.append("                                                     WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           GROUP BY tt2.GROUP_ID,tt2.ORG_ID                                       \n");
		sql.append("                                        ) tab33 ON tab33.ORG_ID=tab.ORG_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                        SELECT tt2.GROUP_ID,tt2.DEALER_ID,COUNT(*) NUM4 \n");
		sql.append("                                            FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append(" FROM (SELECT TVMG2.GROUP_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,        \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR \n");
		sql.append("            WHERE TVO.VIN=TV.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID             \n");
		sql.append("              AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("              AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVO.ORDER_TYPE=20831003 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVO.CREATE_DATE>='"+month_day_start+"' \n");
		sql.append("              AND TVO.CREATE_DATE<'"+month_day_end+"' \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                            AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"'   AND tvmp.task_id IS NULL)) tt1 \n");
		sql.append("                                                     LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     ON tt1.VIN=tas.VIN \n");
		sql.append("                                                     WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           GROUP BY tt2.GROUP_ID,tt2.DEALER_ID \n");
		sql.append("                                        ) tab4 ON tab4.DEALER_ID=tab.DEALER_ID \n");
		sql.append("                                           LEFT JOIN ( \n");
		sql.append("                                        SELECT tt2.GROUP_ID,tt2.DEALER_ID,COUNT(*) NUM5 \n");
		sql.append("                                            FROM (SELECT tt1.*,tas.IS_SUPPORT \n");
		sql.append(" FROM (SELECT TVMG2.GROUP_ID,TD.DEALER_ID,TV.VIN \n");
		sql.append("           FROM TT_VS_ORDER                    TVO, \n");
		sql.append("                 TM_VEHICLE_DEC                     TV, \n");
		sql.append("                 TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                 TM_VHCL_MATERIAL_GROUP         TVMG2,    \n");
		sql.append("                 TM_DEALER                      TD, \n");
		sql.append("                 TM_ORG                         TOR, \n");
		sql.append("                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                 TT_VS_COMMON_RESOURCE          TVCR \n");
		sql.append("            WHERE TVO.VIN = TV.VIN \n");
		sql.append("              AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("              AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("              AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("              AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("              AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("              AND TVO.DEALER_ID = TD.DEALER_ID                       \n");
		sql.append("              AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("              AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("              AND TVO.COMMONALITY_ID = TVCR.COMMON_ID \n");
		sql.append("              AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("              AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("              AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("              AND TVCR.STATUS = 20801002 \n");
		sql.append("              AND TVCR.TYPE = 20811001 \n");
		sql.append("              AND TVO.ORDER_STATUS<20071008 \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE>='"+month_day_start+"' \n");
		sql.append("              AND TVO.SWT_AFFIRM_DATE<'"+month_day_end+"' \n");
		sql.append("              AND TVMG2.GROUP_ID='"+s_series_id+"' \n");
		sql.append("              AND TOR.ORG_ID='"+s_org_id+"' \n");
		sql.append("              AND EXISTS (SELECT 1 FROM TT_VS_MONTHLY_PLAN tvmp,TT_VS_MONTHLY_PLAN_DETAIL tvmpd \n");
		sql.append("                          WHERE tvmp.PLAN_ID = tvmpd.PLAN_ID AND tvmp.DEALER_ID=td.DEALER_ID \n");
		sql.append("                            AND tvmp.PLAN_TYPE=20781001 AND tvmp.PLAN_YEAR='"+year+"' AND tvmp.PLAN_MONTH ='"+month+"' AND tvmp.PLAN_VER="+playVer+" \n");
		sql.append("                           AND tvmp.task_id IS NULL AND tvmpd.MATERIAL_GROUPID='"+s_series_id+"')) tt1 \n");
		sql.append("                                                     LEFT JOIN TM_ALLOT_SUPPORT tas \n");
		sql.append("                                                     ON tt1.VIN=tas.VIN \n");
		sql.append("                                                     WHERE IFNULL(tas.IS_SUPPORT,0)=1) tt2 \n");
		sql.append("                                           GROUP BY tt2.GROUP_ID,tt2.DEALER_ID                                      \n");
		sql.append("                                        ) tab5 ON tab5.DEALER_ID=tab.DEALER_ID) t \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 分配经销商temp1
	 * @param allotDate
	 * @param s_series_id
	 * @return
	 */
	public List<Map> getTemp1(String allotDate, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select * from TM_TOTAL_GAP where GAP_TYPE=10431005 and ALLOT_DATE='"+allotDate+"' and GROUP_ID="+s_series_id+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 批量1
	 * @param allotDate
	 * @param s_series_id
	 */
	public void exceBatch1(String allotDate, Long s_series_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set status=1 where  GAP_TYPE=10431005 and ALLOT_DATE='"+allotDate+"' and GROUP_ID="+s_series_id+" \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp2
	 * @param year
	 * @param month
	 * @param playVer
	 * @param t_org_id
	 * @return
	 */
	public List<Map> getTemp2(Integer year, Integer month, int playVer, double t_org_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT  TVMPD.MATERIAL_GROUPID GROUP_ID,TOR2.PARENT_ORG_ID,TOR.ORG_ID,TD.DEALER_ID,SUM(TVMPD.SALE_AMOUNT) SALE_AMOUNT \n");
		sql.append("                           FROM TT_VS_MONTHLY_PLAN             TVMP, \n");
		sql.append("                                 TT_VS_MONTHLY_PLAN_DETAIL      TVMPD, \n");
		sql.append("                                 TM_DEALER                      TD, \n");
		sql.append("                                 TM_ORG                         TOR, \n");
		sql.append("                                 TM_ORG                         TOR2, \n");
		sql.append("                                 TM_DEALER_ORG_RELATION         TDOR, \n");
		sql.append("                                 TM_VHCL_MATERIAL_GROUP         TVMG \n");
		sql.append("                           WHERE TVMP.PLAN_ID = TVMPD.PLAN_ID \n");
		sql.append("                             AND TVMP.DEALER_ID = TD.DEALER_ID \n");
		sql.append("                             AND TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append("                             AND TOR.ORG_ID = TDOR.ORG_ID \n");
		sql.append("                             AND TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append("                             AND TVMPD.MATERIAL_GROUPID = TVMG.GROUP_ID \n");
		sql.append("                             AND TVMP.PLAN_TYPE=20781001 \n");
		sql.append("                             AND TVMP.PLAN_YEAR='"+year+"' \n");
		sql.append("                             AND TVMP.PLAN_MONTH ='"+month+"' \n");
		sql.append("                             AND TVMP.PLAN_VER="+playVer+" \n");
		sql.append("                             AND tvmp.task_id IS NULL \n");
		sql.append("                             AND TVMPD.MATERIAL_GROUPID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=36) \n");
		sql.append("                             AND TOR.ORG_ID="+t_org_id+" \n");
		sql.append("                             AND NOT EXISTS(SELECT 1 FROM TM_RESOURCE_ALLOT_FROZEN t \n");
		sql.append("                                       WHERE t.DEALER_ID=TD.DEALER_ID AND STATUS=19990001 \n");
		sql.append("                                         AND t.SERIES_NAME=TVMG.GROUP_NAME \n");
		sql.append("                                         AND t.GROUP_CODE='' AND t.GROUP_NAME='' AND t.MODEL_YEAR='' AND t.COLOR_CODE='' AND t.COLOR_NAME='') \n");
		sql.append("                           GROUP BY  TVMPD.MATERIAL_GROUPID,TOR2.PARENT_ORG_ID,TOR.ORG_ID,TD.DEALER_ID \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 资源分配至经销商批量二
	 * @param dealerId
	 * @param salesAmount
	 * @param orgId
	 * @param allotDate
	 */
	public void exceBatch2(Long dealerId, Integer salesAmount, Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set SALE_AMOUNT="+salesAmount+" \n");
		sql.append("where  GAP_TYPE=10431005 and CREATE_BY="+orgId+" \n");
		sql.append("and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+dealerId+" \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	/**
	 * temp3
	 * @param orgId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp3(Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT GAP_TYPE,GROUP_ID,TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2,IFNULL(NUM22,0) NUM22, \n");
		sql.append("IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,IFNULL(NUM4,0) NUM4,IFNULL(NUM5,0) NUM5,CREATE_BY,ALLOT_DATE \n");
		sql.append("FROM TM_TOTAL_GAP WHERE GAP_TYPE=10431005 AND CREATE_BY="+orgId+" AND ALLOT_DATE='"+allotDate+"' \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=30) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 分配资源经销商批量3
	 * @param map
	 * @param orgId
	 * @param allotDate
	 */
	public void exceBatch3(Map map, Long orgId, String allotDate) {
		Integer NUM1 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"));
		Integer NUM2 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"));
		Integer NUM3 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"));
		Integer NUM4 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"));
		Integer NUM5 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"));
		Integer NUM33 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"));
		Long TARGET_ID = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("UPDATE TM_TOTAL_GAP SET NUM33=NUM33+"+NUM33+", \n");
		sql.append("GAP=SALE_AMOUNT-nvl(NUM1,0)-"+NUM1+"-nvl(NUM2,0)-"+NUM2+"-nvl(NUM3,0)-"+NUM3+"-nvl(NUM33,0)-nvl(NUM4,0)-"+NUM4+"-nvl(NUM5,0)-"+NUM5+" \n");
		sql.append("WHERE  GAP_TYPE=10431005 AND CREATE_BY="+orgId+" AND ALLOT_DATE='"+allotDate+"' AND TARGET_ID="+TARGET_ID+" \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		
		StringBuffer sql2 = new StringBuffer("\n");
		sql2.append("update TM_TOTAL_GAP set GAP=0 \n");
		sql2.append("where GAP<0 and GAP_TYPE=10431005 and CREATE_BY="+orgId+" \n");
		sql2.append("and ALLOT_DATE='"+allotDate+"' and TARGET_ID="+TARGET_ID+" \n");
		sql2.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=36) \n");
		OemDAOUtil.execBatchPreparement(sql2.toString(), new ArrayList<>());
		
	}
	/**
	 * temp4
	 * @param orgId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp4(Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ttg.GROUP_ID,tor.ORG_ID \n");
		sql.append("from TM_TOTAL_GAP ttg,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor \n");
		sql.append("where ttg.TARGET_ID=td.DEALER_ID and td.DEALER_ID=tdor.DEALER_ID and tor.ORG_ID=tdor.ORG_ID \n");
		sql.append("and ttg.GAP_TYPE=10431005 and ttg.ALLOT_DATE='"+allotDate+"' and ttg.CREATE_BY="+orgId+" \n");
		sql.append("group by ttg.GROUP_ID,tor.ORG_ID having sum(GAP)=0 \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * temp5
	 * @param org_Id
	 * @param orgId
	 * @param groupId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp5(Long org_Id, Long orgId, Long groupId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select ttg.TM_GAP_ID \n");
		sql.append("from TM_TOTAL_GAP ttg,TM_DEALER td,TM_ORG tor,TM_DEALER_ORG_RELATION tdor \n");
		sql.append("where ttg.TARGET_ID=td.DEALER_ID and td.DEALER_ID=tdor.DEALER_ID and tor.ORG_ID=tdor.ORG_ID \n");
		sql.append("and GAP_TYPE=10431005 and ALLOT_DATE='"+allotDate+"' and ttg.GROUP_ID="+groupId+" \n");
		sql.append("and tor.ORG_ID="+org_Id+" and ttg.CREATE_BY="+orgId+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	/**
	 * 批量4
	 * @param tmGapId
	 */
	public void exceBatch4(Long tmGapId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP2=SALE_AMOUNT where TM_GAP_ID="+tmGapId+" \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 获取temp6
	 * @param t_org_id
	 * @param s_series_id
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp6(double t_org_id, Long s_series_id, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVMG2.GROUP_NAME SERIES_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME,SUM(TAR.ADJUST_NUM) NUM \n");
		sql.append("                                     FROM TM_ALLOT_RESOURCE_DCS          TAR,   \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG4, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG3, \n");
		sql.append("                                           TM_VHCL_MATERIAL_GROUP     TVMG2 \n");
		sql.append("                                       WHERE TAR.GROUP_ID=TVMG4.GROUP_ID \n");
		sql.append("                                         AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID \n");
		sql.append("                                         AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("                                         AND TAR.ALLOT_TYPE=10431004 \n");
		sql.append("                                         AND TAR.ALLOT_TARGET_ID="+t_org_id+" \n");
		sql.append("                                         AND TVMG2.GROUP_ID="+s_series_id+" \n");
		sql.append("                                         AND TAR.ALLOT_DATE='"+allotDate+"'                                       \n");
		sql.append("                                       GROUP BY TVMG2.GROUP_NAME,TVMG4.GROUP_ID,TVMG4.GROUP_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取Temp7
	 * @param t_org_id
	 * @param s_series_id
	 * @param groupId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp7(double t_org_id, Long s_series_id, Long groupId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVMG4.GROUP_ID,TVMG4.GROUP_NAME,TAR.COLOR_NAME,TVMG4.MODEL_YEAR,TAR.ADJUST_NUM NUM \n");
		sql.append("                                             FROM TM_ALLOT_RESOURCE_DCS          TAR,   \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG4, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG3, \n");
		sql.append("                                                   TM_VHCL_MATERIAL_GROUP     TVMG2                       \n");
		sql.append("                                               WHERE TAR.GROUP_ID=TVMG4.GROUP_ID \n");
		sql.append("                                                 AND TVMG4.PARENT_GROUP_ID=TVMG3.GROUP_ID \n");
		sql.append("                                                 AND TVMG3.PARENT_GROUP_ID=TVMG2.GROUP_ID \n");
		sql.append("                                                 AND TAR.ALLOT_TYPE=10431004 \n");
		sql.append("                                                 AND TAR.ALLOT_TARGET_ID="+t_org_id+" \n");
		sql.append("                                                 AND TVMG2.GROUP_ID="+s_series_id+" \n");
		sql.append("                                                 AND TAR.GROUP_ID="+groupId+" \n");
		sql.append("                                                 AND TAR.ALLOT_DATE='"+allotDate+"' \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 删除临时表数据
	 */
	public void exceBatch5() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" delete from TM_ALLOT_GAP_TEMP_DCS where GAP_TYPE=10431005 \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 批量6
	 * @param map2
	 * @param '"+s_series_name+"' 
	 * @param groupId2 
	 */
	public void exceBatch6(Map map2,double s_org_id, Long orgId,Long s_series_id, String s_series_name, Long groupId,  String allotDate) {
		String groupName = CommonUtils.checkNull(map2.get("GROUP_NAME"));
		String colorName = CommonUtils.checkNull(map2.get("COLOR_NAME"));
		String modelYear = CommonUtils.checkNull(map2.get("MODEL_YEAR"));
		StringBuffer sql = new StringBuffer("\n");
		sql.append("     INSERT INTO TM_ALLOT_GAP_TEMP(GAP_TYPE,TARGET_ID,GROUP_ID,ORG_ID,GAP,ALLOT_DATE,CREATE_BY) \n");
		sql.append("     SELECT 10431005,TTG.TARGET_ID,S_SERIES_ID,S_ORG_ID,(CASE WHEN NVL(TTG.GAP2,0)>0 THEN TTG.GAP2 ELSE TTG.GAP END) GAP,ALLOTDATE,IN_ORG_ID \n");
		sql.append("       FROM TM_TOTAL_GAP             ttg, \n");
		sql.append("       TM_DEALER                td, \n");
		sql.append("       TM_ORG                   tor, \n");
		sql.append("       TM_DEALER_ORG_RELATION   tdor \n");
		sql.append("       where ttg.TARGET_ID = td.DEALER_ID \n");
		sql.append(" and td.DEALER_ID = tdor.DEALER_ID                              \n");
		sql.append(" and tor.ORG_ID = tdor.ORG_ID                                              \n");
		sql.append(" and ttg.CREATE_BY="+orgId+" \n");
		sql.append(" and ttg.GAP_TYPE=10431005 \n");
		sql.append(" and ttg.GROUP_ID="+s_series_id+" \n");
		sql.append(" and tor.ORG_ID="+s_org_id+" \n");
		sql.append(" and ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append(" and not exists(select 1 from TM_RESOURCE_ALLOT_FROZEN traf where traf.DEALER_ID=td.DEALER_ID and traf.STATUS=19990001 \n");
		sql.append("                     and ((traf. SERIES_NAME='' and locate('"+groupName+"',traf.GROUP_NAME)>0 and traf.COLOR_NAME='' and traf.MODEL_YEAR='') \n");
		sql.append("                            or (traf. SERIES_NAME='' and locate('"+groupName+"',traf.GROUP_NAME)>0 and locate('"+colorName+"',traf.COLOR_NAME)>0 and traf.MODEL_YEAR='') \n");
		sql.append("                            or (traf. SERIES_NAME='' and locate('"+groupName+"',traf.GROUP_NAME)>0 and traf.COLOR_NAME='' and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (traf. SERIES_NAME='' and locate('"+groupName+"',traf.GROUP_NAME)>0 and locate('"+colorName+"',traf.COLOR_NAME)>0 and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (traf. SERIES_NAME='' and traf.GROUP_NAME='' and locate('"+colorName+"',traf.COLOR_NAME)>0 and traf.MODEL_YEAR='') \n");
		sql.append("                            or (traf. SERIES_NAME='' and traf.GROUP_NAME='' and traf.COLOR_NAME='' and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (traf. SERIES_NAME='' and traf.GROUP_NAME='' and locate('"+colorName+"',traf.COLOR_NAME)>0 and locate('"+modelYear+"',traf.MODEL_YEAR)>0)                            \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and traf.GROUP_NAME='' and traf.COLOR_NAME='' and traf.MODEL_YEAR='') \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and traf.GROUP_NAME='' and locate('"+colorName+"',traf.COLOR_NAME)>0 and traf.MODEL_YEAR='') \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and traf.GROUP_NAME='' and traf.COLOR_NAME='' and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and traf.GROUP_NAME='' and locate('"+colorName+"',traf.COLOR_NAME)>0 and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and locate('"+groupName+"',traf.GROUP_NAME)>0 and locate('"+colorName+"',traf.COLOR_NAME)>0 and traf.MODEL_YEAR='') \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and locate('"+groupName+"',traf.GROUP_NAME)>0 and traf.COLOR_NAME='' and locate('"+modelYear+"',traf.MODEL_YEAR)>0) \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and locate('"+groupName+"',traf.GROUP_NAME)>0 and traf.COLOR_NAME='' and traf.MODEL_YEAR='')  \n");
		sql.append("                            or (locate('"+s_series_name+"',traf.SERIES_NAME)>0 and locate('"+groupName+"',traf.GROUP_NAME)>0 and locate('"+colorName+"',traf.COLOR_NAME)>0 and locate('"+modelYear+"',traf.MODEL_YEAR)>0))) \n");
		sql.append("        order by ttg.TARGET_ID \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * temp8
	 * @param s_series_id
	 * @param s_org_id
	 * @param allotDate
	 * @param orgId
	 * @return
	 */
	public List<Map> getTemp8(Long s_series_id, Long s_org_id, String allotDate, Long orgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TARGET_ID,GAP from TM_ALLOT_GAP_TEMP \n");
		sql.append("where GAP_TYPE=10431005 and GROUP_ID="+s_series_id+" and ORG_ID="+s_org_id+" and ALLOT_DATE='"+allotDate+"' and CREATE_BY="+orgId+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取gaptotal
	 * @param orgId
	 * @param targetId
	 * @param s_series_id
	 * @param s_org_id
	 * @param allotDate
	 * @return
	 */
	public int getGapTotal3(Long orgId, Long targetId, Long s_series_id, Long s_org_id, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT (CASE WHEN SUM(ttg.GAP2)>0 THEN SUM(ttg.GAP2) ELSE SUM(ttg.GAP) END) GAP_TOTAL \n");
		sql.append("    FROM TM_TOTAL_GAP             ttg, \n");
		sql.append("          TM_DEALER                td, \n");
		sql.append("          TM_ORG                   tor, \n");
		sql.append("          TM_DEALER_ORG_RELATION   tdor, \n");
		sql.append("          TM_ALLOT_GAP_TEMP_DCS        tagt \n");
		sql.append("  WHERE ttg.TARGET_ID = td.DEALER_ID \n");
		sql.append("    AND td.DEALER_ID = tdor.DEALER_ID  \n");
		sql.append("    AND tor.ORG_ID = tdor.ORG_ID    \n");
		sql.append("    AND ttg.CREATE_BY="+orgId+" \n");
		sql.append("    AND ttg.TARGET_ID="+targetId+" \n");
		sql.append("    AND ttg.GAP_TYPE=10431005 \n");
		sql.append("    AND ttg.GROUP_ID="+s_series_id+" \n");
		sql.append("    AND tor.ORG_ID="+s_org_id+" \n");
		sql.append("    AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP_TOTAL")));
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * @param long1
	 * @param s_org_id
	 * @param s_series_id
	 * @param allotDate
	 * @param orgId
	 * @return
	 */
	public List<Map> getTemp9(Long long1, Long s_org_id, Long s_series_id, String allotDate, Long orgId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT t1.GAP,t2.ALLOT_NUM \n");
		sql.append(" FROM (SELECT ttg.TARGET_ID,ttg.GROUP_ID,(CASE WHEN IFNULL(ttg.GAP2,0)>0 THEN ttg.GAP2 ELSE ttg.GAP END) GAP \n");
		sql.append("            FROM TM_TOTAL_GAP            ttg, \n");
		sql.append("                 TM_DEALER                td, \n");
		sql.append("                 TM_ORG                   tor, \n");
		sql.append("                 TM_DEALER_ORG_RELATION   tdor \n");
		sql.append("             WHERE ttg.TARGET_ID=td.DEALER_ID \n");
		sql.append("               AND td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("               AND tor.ORG_ID=tdor.ORG_ID \n");
		sql.append("               AND ttg.GAP_TYPE=10431005 \n");
		sql.append("               AND tor.ORG_ID="+s_org_id+" \n");
		sql.append("               AND ttg.GROUP_ID="+s_series_id+" \n");
		sql.append("               AND ttg.CREATE_BY="+orgId+" \n");
		sql.append("               AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("               ) t1 \n");
		sql.append(" LEFT JOIN (SELECT tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID,SUM(tar.ADJUST_NUM) ALLOT_NUM \n");
		sql.append("                        FROM TM_ALLOT_RESOURCE_DCS             tar, \n");
		sql.append("                              TM_DEALER                     td, \n");
		sql.append("                              TM_ORG                         tor, \n");
		sql.append("                              TM_DEALER_ORG_RELATION         tdor, \n");
		sql.append("                              TM_VHCL_MATERIAL_GROUP        tvmg4, \n");
		sql.append("                              TM_VHCL_MATERIAL_GROUP        tvmg3, \n");
		sql.append("                              TM_VHCL_MATERIAL_GROUP        tvmg2  \n");
		sql.append("                        WHERE tar.ALLOT_TARGET_ID=td.DEALER_ID \n");
		sql.append("                          AND td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("                          AND tdor.ORG_ID=tor.ORG_ID \n");
		sql.append("                          AND tar.GROUP_ID=tvmg4.GROUP_ID \n");
		sql.append("                          AND tvmg4.PARENT_GROUP_ID=tvmg3.GROUP_ID \n");
		sql.append("                          AND tvmg3.PARENT_GROUP_ID=tvmg2.GROUP_ID \n");
		sql.append("                          AND tvmg4.GROUP_LEVEL=4 \n");
		sql.append("                          AND tvmg3.GROUP_LEVEL=3 \n");
		sql.append("                          AND tvmg2.GROUP_LEVEL=2 \n");
		sql.append("                          AND tar.ALLOT_TYPE=10431005 \n");
		sql.append("                          AND tor.ORG_ID="+s_org_id+" \n");
		sql.append("                          AND tvmg2.GROUP_ID="+s_series_id+" \n");
		sql.append("                          AND tar.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                          AND tar.CREATE_BY="+orgId+" \n");
		sql.append("                        GROUP BY tar.ALLOT_TARGET_ID,tvmg2.GROUP_ID) t2 \n");
		sql.append("                                          ON t1.TARGET_ID=t2.ALLOT_TARGET_ID AND t1.GROUP_ID=t2.GROUP_ID \n");
		sql.append("                                          WHERE t1.TARGET_ID="+long1+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp10
	 * @param orgId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp10(Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT tor.ORG_ID,tagt.SERIES_ID,tagt.GROUP_ID,tagt.COLOR_NAME,SUM(tagt.NUM) NUM  \n");
		sql.append("                  FROM TM_ALLOT_GROUP_TEMP_DCS          tagt, \n");
		sql.append("                        TM_DEALER                    td, \n");
		sql.append("                        TM_ORG                       tor, \n");
		sql.append("                        TM_DEALER_ORG_RELATION       tdor \n");
		sql.append("                  WHERE tagt.TARGET_ID=td.DEALER_ID \n");
		sql.append("                    AND td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("                    AND tor.ORG_ID=tdor.ORG_ID     \n");
		sql.append("                    AND tagt.ALLOT_TYPE=10431005 \n");
		sql.append("                    AND tagt.CREATE_BY="+orgId+" \n");
		sql.append("                    AND tagt.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                    AND tor.PARENT_ORG_ID="+orgId+" \n");
		sql.append("                GROUP BY tor.ORG_ID,tagt.SERIES_ID,tagt.GROUP_ID,tagt.COLOR_NAME \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * temp11
	 * @param seriesId
	 * @param orgId
	 * @param allotDate
	 * @param org_id
	 * @return
	 */
	public List<Map> getTemp11(String seriesId, Long orgId, String allotDate, Long org_id) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ttg.TARGET_ID,ttg.SALE_AMOUNT GAP  \n");
		sql.append("                          FROM TM_TOTAL_GAP                 ttg, \n");
		sql.append("                                TM_DEALER                    td, \n");
		sql.append("                                TM_ORG                       tor, \n");
		sql.append("                                TM_DEALER_ORG_RELATION       tdor \n");
		sql.append("                          WHERE ttg.TARGET_ID=td.DEALER_ID \n");
		sql.append("                            AND td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("                            AND tor.ORG_ID=tdor.ORG_ID     \n");
		sql.append("                            AND ttg.GAP_TYPE=10431005 \n");
		sql.append("                            AND ttg.GROUP_ID="+seriesId+" \n");
		sql.append("                            AND ttg.CREATE_BY="+orgId+" \n");
		sql.append("                            AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                            AND tor.ORG_ID="+org_id+" \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取gap_total
	 * @param org_id
	 * @param orgId
	 * @param allotDate
	 * @param seriesId
	 * @return
	 */
	public int getGapTotal4(Long org_id, Long orgId, String allotDate, String seriesId) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT IFNULL(SUM(ttg.SALE_AMOUNT),0) GAP_TOTAL \n");
		sql.append("                FROM TM_TOTAL_GAP                ttg, \n");
		sql.append("                     TM_DEALER                    td, \n");
		sql.append("                     TM_ORG                       tor, \n");
		sql.append("                     TM_DEALER_ORG_RELATION       tdor \n");
		sql.append("                WHERE ttg.TARGET_ID=td.DEALER_ID \n");
		sql.append("                  AND td.DEALER_ID=tdor.DEALER_ID \n");
		sql.append("                  AND tor.ORG_ID=tdor.ORG_ID     \n");
		sql.append("                  AND ttg.GAP_TYPE=10431005 \n");
		sql.append("                  AND tor.ORG_ID="+org_id+" \n");
		sql.append("                  AND ttg.CREATE_BY="+orgId+" \n");
		sql.append("                  AND ttg.ALLOT_DATE='"+allotDate+"' \n");
		sql.append("                  AND ttg.GROUP_ID="+seriesId+" \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		if(null!=map){
			return Integer.parseInt(CommonUtils.checkNull(map.get("GAP_TOTAL"),"0"));
		}else{
			return 0;
		}
	}
	
	/**
	 * 
	 * @param long1
	 * @param seriesId
	 * @param colorName
	 * @param orgId
	 * @param allotDate
	 * @return
	 */
	public double getTempArr(Long long1, String seriesId, String colorName, Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT DISTINCT TM_ALLOT_ID  TEMP_ARR FROM TM_ALLOT_RESOURCE_DCS \n");
		sql.append("WHERE ALLOT_TYPE=10431005 AND ALLOT_TARGET_ID=group_id_array[temp] \n");
		sql.append("AND GROUP_ID=etb.GROUP_ID AND COLOR_NAME=etb.COLOR_NAME \n");
		sql.append("AND CREATE_BY=IN_ORG_ID AND ALLOT_DATE=allotDate \n");
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		return Double.parseDouble(CommonUtils.checkNull(map.get("TEMP_ARR"),"0"));
	}
	/**
	 * 
	 * @param i
	 * @param long1
	 * @param seriesId
	 * @param colorName
	 * @param orgId
	 * @param allotDate
	 */
	public void exceBatch7(int i, Long long1, String seriesId, String colorName, Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("UPDATE TM_ALLOT_RESOURCE_DCS SET ALLOT_NUM=ALLOT_NUM+"+i+",ADJUST_NUM=ADJUST_NUM+"+i+" \n");
		sql.append("WHERE ALLOT_TYPE=10431005 AND ALLOT_TARGET_ID="+long1+" AND GROUP_ID="+seriesId+" AND COLOR_NAME='"+colorName+"' \n");
		sql.append("AND CREATE_BY="+orgId+" AND ALLOT_DATE='"+allotDate+"' \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 批量执行 8 
	 * @param orgId
	 * @param allotDate
	 */
	public void exceBatch8(Long orgId, String allotDate) {
		StringBuffer querySql = new StringBuffer("SELECT tar.TM_ALLOT_ID FROM TM_ALLOT_RESOURCE_DCS tar,TM_ORG tor \n");
		querySql.append("                               WHERE tar.ALLOT_TARGET_ID=tor.ORG_ID AND ALLOT_TYPE=10431004 \n");
		querySql.append("                               AND tor.PARENT_ORG_ID="+orgId+" AND tar.ALLOT_DATE='"+allotDate+"'");
		List<Map> list = OemDAOUtil.findAll(querySql.toString(), null);
		
		if(null!= list && list.size()>0){
			String tmAllotId = "";
			for (Map map : list) {
				tmAllotId = tmAllotId +","+map.get("TM_ALLOT_ID").toString();
			}
			
			StringBuffer sql = new StringBuffer("\n");
			sql.append("UPDATE TM_ALLOT_RESOURCE_DCS SET ALLOT_STATUS=1 \n");
			sql.append("        WHERE TM_ALLOT_ID IN("+tmAllotId.substring(1)+") \n");
			OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		}
		
	}
	
	/**
	 * 36+1
	 * @return
	 */
	public List<Map> getTemp12() {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVMG2.GROUP_ID,TVMG2.GROUP_CODE,COUNT(*) NUM \n");
		sql.append("                 FROM TMP_UPLOAD_RESOURCE            TUR,   \n");
		sql.append("                       TM_VEHICLE_DEC                     TV, \n");
		sql.append("                       TM_VHCL_MATERIAL               TVM, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP_R       TVMGR, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG4, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG3, \n");
		sql.append("                       TM_VHCL_MATERIAL_GROUP         TVMG2 \n");
		sql.append("                   WHERE TUR.VIN = TV.VIN \n");
		sql.append("                     AND TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append("                     AND TVM.MATERIAL_ID = TVMGR.MATERIAL_ID \n");
		sql.append("                     AND TVMGR.GROUP_ID = TVMG4.GROUP_ID \n");
		sql.append("                     AND TVMG4.PARENT_GROUP_ID = TVMG3.GROUP_ID \n");
		sql.append("                     AND TVMG3.PARENT_GROUP_ID = TVMG2.GROUP_ID \n");
		sql.append("                     AND TVMG4.GROUP_LEVEL = 4 \n");
		sql.append("                     AND TVMG3.GROUP_LEVEL = 3 \n");
		sql.append("                     AND TVMG2.GROUP_LEVEL = 2 \n");
		sql.append("                     AND TVMG2.GROUP_ID IN(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=36) \n");
		sql.append("                   GROUP BY TVMG2.GROUP_ID,TVMG2.GROUP_CODE \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 获取分配数据
	 * @param orgId
	 * @param allotDate
	 * @return
	 */
	public List<Map> getTemp13(Long orgId, String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TARGET_ID,SALE_AMOUNT,IFNULL(NUM1,0) NUM1,IFNULL(NUM2,0) NUM2,IFNULL(NUM22,0) NUM22,IFNULL(NUM3,0) NUM3,IFNULL(NUM33,0) NUM33,IFNULL(NUM4,0) NUM4,IFNULL(NUM5,0) NUM5,GAP \n");
		sql.append("FROM TM_TOTAL_GAP WHERE GAP_TYPE=10431005 AND CREATE_BY="+orgId+" AND ALLOT_DATE='"+allotDate+"' \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=36) \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	/**
	 * 批处理9
	 * @param map
	 * @param allotDate
	 */
	public void exceBatch9(Map map, String allotDate) {
		Integer saleAmount = Integer.parseInt(CommonUtils.checkNull(map.get("SALE_AMOUNT"),"0"));
		Integer num1 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM1"),"0"));
		Integer num2 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM2"),"0"));
		Integer num22 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM22"),"0"));
		Integer num3 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM3"),"0"));
		Integer num33 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM33"),"0"));
		Integer num4 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM4"),"0"));
		Integer num5 = Integer.parseInt(CommonUtils.checkNull(map.get("NUM5"),"0"));
		Long targetId = Long.parseLong(CommonUtils.checkNull(map.get("TARGET_ID"),"0"));
		StringBuffer sql = new StringBuffer("\n");
		sql.append("UPDATE TM_TOTAL_GAP SET SALE_AMOUNT="+saleAmount+",NUM1=nvl(NUM1,0)+"+num1+", \n");
		sql.append("NUM2=nvl(NUM2,0)+"+num2+",NUM22=nvl(NUM22,0)+"+num22+", \n");
		sql.append("NUM3=nvl(NUM3,0)+"+num3+",NUM33=nvl(NUM33,0)+"+num33+", \n");
		sql.append("NUM4=nvl(NUM4,0)+"+num4+",NUM5=nvl(NUM5,0)+"+num5+" \n");
		sql.append("WHERE TARGET_ID="+targetId+" AND  GAP_TYPE=10431005 AND ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	/**
	 * 批处理10
	 * @param orgId
	 * @param allotDate
	 */
	public void exceBatch10(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("UPDATE TM_TOTAL_GAP SET GAP=SALE_AMOUNT-NUM1-NUM2-NUM3-NUM4-NUM5  \n");
		sql.append("WHERE GAP_TYPE=10431005 AND ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("AND GROUP_ID=(SELECT GRAND_ID FROM TM_ALLOT_GRAND WHERE GRAND_TYPE=30) \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}

	public void exceBatch11(String allotDate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("update TM_TOTAL_GAP set GAP=0  \n");
		sql.append("where GAP<0 and GAP_TYPE=10431005 and ALLOT_DATE='"+allotDate+"'  \n");
		sql.append("and GROUP_ID=(select GRAND_ID from TM_ALLOT_GRAND where GRAND_TYPE=30); \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}
	
	
	

}
