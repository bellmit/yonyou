package com.infoeai.eai.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsFactoryOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * @author yuyong
 * 
 */
public class FuturesResourceSettingDao extends OemBaseDAO {

	public static Logger logger = Logger.getLogger(FuturesResourceSettingDao.class);
	private static final FuturesResourceSettingDao dao = new FuturesResourceSettingDao();

	public static final FuturesResourceSettingDao getInstance() {
		return dao;
	}

	public List<Map> getFuturesResourceList(Map<String,String> map, Integer pageSize,Integer curPage) {
		StringBuffer conditionWhere = new StringBuffer();
		String brand = map.get("brand").toString();
		String series = map.get("series").toString();
		String groupName = map.get("groupName").toString();
		String modelYear = map.get("modelYear").toString();
		String colorName = map.get("colorName").toString();
		String trimName = map.get("trimName").toString();
		String nodeState = map.get("nodeState").toString();
		String status = map.get("status").toString();
		
		if(!brand.equals("")&&brand.indexOf(",")<0){
			conditionWhere.append("  and BRAND_CODE like '%"+brand+"%'");
		}
		if(!series.equals("")&&series.indexOf(",")<0){
			conditionWhere.append("  and SERIES_CODE like '%"+series+"%'");
		}
		if(!groupName.equals("")&&groupName.indexOf(",")<0){
			conditionWhere.append("  and GROUP_CODE like '%"+groupName+"%'");
		}
		if(!modelYear.equals("")&&modelYear.indexOf(",")<0){
			conditionWhere.append("  and MODEL_YEAR like '%"+modelYear+"%'");
		}
		if(!colorName.equals("")&&colorName.indexOf(",")<0){
			conditionWhere.append("  and COLOR_CODE like '%"+colorName+"%'");
		}
		if(!trimName.equals("")&&trimName.indexOf(",")<0){
			conditionWhere.append("  and TRIM_CODE like '%"+trimName+"%'");
		}
		if(!nodeState.equals("")&&nodeState.indexOf(",")<0){
			conditionWhere.append("     and tc1.NUM>=(select NUM from TC_CODE where type = 1151 and CODE_ID="+nodeState+")\n");
		}
		if(!status.equals("")&&status.indexOf(",")<0){
			conditionWhere.append("  and tf.STATUS = "+status+"");
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select RESOURCE_SETTING_ID,BRAND_CODE,SERIES_CODE,SERIES_NAME,GROUP_CODE,GROUP_NAME,MODEL_YEAR,COLOR_CODE,COLOR_NAME,TRIM_CODE,TRIM_NAME,FACTORY_OPTIONS,\n");
		sql.append("	   nvl(NODE_STATUS_START,0) NODE_STATUS_START,nvl(NODE_STATUS_END,0) NODE_STATUS_END,to_char(NODE_DATE_START,'yyyy-MM-dd') NODE_DATE_START,to_char(NODE_DATE_END,'yyyy-MM-dd') NODE_DATE_END,tf.STATUS,\n");
		sql.append("      (case RESOURCE_CODE when "+OemDictCodeConstants.OEM_ACTIVITIES+" then '全国' else (select ORG_DESC from TM_ORG where ORG_ID=RESOURCE_CODE) end) RESOURCE\n");
		sql.append("    from TT_FUTURES_RESOURCE_SETTING tf\n");	
		sql.append("   left join TC_CODE  tc1 on tc1.CODE_ID=NODE_STATUS_START\n");
		sql.append("   where 1=1\n");		
		if(!conditionWhere.toString().equals("")){
			sql.append("  "+conditionWhere.toString()+"\n");
		}
		
		List<Map> ps=OemDAOUtil.findAll(sql.toString(),null);
		
		return ps;
	}
	public List<Map<String, Object>> findBrandList(Integer pageSize,Integer curPage) {
		List<Map<String, Object>> ps = pageQuery("select distinct BRAND_CODE,BRAND_NAME from VW_MATERIAL where GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT, null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findSeriesList(String type,String brandCode,Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct SERIES_CODE,SERIES_NAME from VW_MATERIAL VM where 1=1\n");
		//sql.append("     and VM.SERIES_CODE<>SERIES_NAME and VM.SERIES_NAME<>'TBD'\n");
		sql.append("     and VM.SERIES_NAME<>'TBD'\n");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		sql.append("    and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findGroupList(String type,String brandCode,String seriesName, Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct GROUP_NAME from VW_MATERIAL VM where 1=1\n");
		sql.append("     and VM.GROUP_CODE<>GROUP_NAME and VM.GROUP_NAME<>'TBD'\n");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		if(!seriesName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.SERIES_NAME in("+seriesName+")\n");
		}
		sql.append("    and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findYearList(String type,String brandCode,String seriesName,String groupName, Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct MODEL_YEAR from VW_MATERIAL VM where 1=1\n");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		if(!seriesName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.SERIES_NAME in("+seriesName+")\n");
		}	
		if(!groupName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.GROUP_NAME in("+groupName+")\n");
		}
		sql.append("    and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findColorList(String type,String brandCode,String seriesName,String groupName,String modelYear, Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct COLOR_NAME from VW_MATERIAL VM where 1=1\n");
		sql.append("     and VM.COLOR_CODE<>COLOR_NAME and VM.COLOR_NAME<>'TBD' and VM.COLOR_NAME<>''\n");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		if(!seriesName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.SERIES_NAME in("+seriesName+")\n");
		}	
		if(!groupName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.GROUP_NAME in("+groupName+")\n");
		}
		if(!modelYear.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.MODEL_YEAR in("+modelYear+")\n");
		}		
		sql.append("    and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findTrimList(String type,String brandCode,String seriesName,String groupName,String modelYear,String colorName, Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct TRIM_NAME from VW_MATERIAL VM where 1=1\n");
		sql.append("     and VM.TRIM_CODE<>TRIM_NAME and VM.TRIM_NAME<>'TBD'\n");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		if(!seriesName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.SERIES_NAME in("+seriesName+")\n");
		}	
		if(!groupName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.GROUP_NAME in("+groupName+")\n");
		}
		if(!modelYear.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.MODEL_YEAR in("+modelYear+")\n");
		}	
		if(!colorName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.COLOR_NAME in("+colorName+")\n");
		}	
		sql.append("    and vm.GROUP_TYPE="+OemDictCodeConstants.GROUP_TYPE_IMPORT);
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findOptionList(String type,String brandCode,String seriesName,String groupName,String modelYear,String colorName,String trimName,Integer pageSize,Integer curPage) {
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct FACTORY_OPTIONS from VW_MATERIAL VM where 1=1\n");
		sql.append("	 and FACTORY_OPTIONS<>''");
		sql.append("	 and exists (select 1 from TM_VHCL_MATERIAL_GROUP p where p.GROUP_ID = VM.GROUP_ID and p.STATUS=10011001)\n");
		if(!brandCode.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.BRAND_CODE in("+brandCode+")\n");
		}
		if(!seriesName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.SERIES_NAME in("+seriesName+")\n");
		}	
		if(!groupName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.GROUP_NAME in("+groupName+")\n");
		}
		if(!modelYear.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.MODEL_YEAR in("+modelYear+")\n");
		}	
		if(!colorName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.COLOR_NAME in("+colorName+")\n");
		}	
		if(!trimName.equals("")&&!type.equals("1")){
			sql.append(" 	and VM.TRIM_NAME in("+trimName+")\n");
		}	
		List<Map<String, Object>> ps = pageQuery(sql.toString(), null, getFunName());	
		return ps;
	}
	public List<Map<String, Object>> findNodeStatusList(String nodeStatus) {	
		String sql = "select CODE_ID,CODE_DESC from TC_CODE where type="+OemDictCodeConstants.VEHICLE_NODE+" and num>=(select num from TC_CODE where CODE_ID="+nodeStatus+") and num<=7 order by num";
		List<Map<String, Object>> list = pageQuery(sql, null, getFunName());
		return list;
	}	
	public List<Map> findFutureCommonResourseList(String frsId,Integer pageSize,Integer curPage) {	
		StringBuffer sql = new StringBuffer();
		sql.append("select  TVCRD.TYPE,\n");//公共资源类型
		sql.append("		TV.VIN,\n");//VIN
		sql.append(" 		VM.BRAND_CODE,\n");//品牌
		sql.append(" 		VM.SERIES_NAME,\n");//车系
		sql.append("  		VM.GROUP_NAME,\n");//车款
		sql.append("  		VM.MODEL_YEAR,\n");//年款
		sql.append("  		VM.COLOR_NAME,\n");//颜色
		sql.append("  		VM.TRIM_NAME,\n");//内饰
		sql.append(" 		TV.NODE_STATUS,\n");//车辆节点
		sql.append(" 		to_char(TV.EXPECT_PORT_DATE,'yyyy-MM-dd') EXPECT_PORT_DATE,\n");//预计到港日期
		sql.append(" 		(case when TVCR.RESOURCE_SCOPE="+OemDictCodeConstants.OEM_ACTIVITIES+" then '全国' else (select ORG_NAME from TM_ORG where ORG_ID=TVCR.RESOURCE_SCOPE) end) ORG_NAME,\n");//公共资源范围
		sql.append(" 		to_char(TVCR.ISSUED_DATE,'yyyy-MM-dd') ISSUED_DATE\n");//下发日期
		sql.append("   from  TM_VEHICLE                        TV,\n");
		sql.append("         VW_MATERIAL                       VM,\n");
		sql.append("         TT_VS_COMMON_RESOURCE             TVCR,\n");
		sql.append("		 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("	where  TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("	  and  TV.VEHICLE_ID = TVCR.VEHICLE_ID\n");
		sql.append("	  and  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");
		sql.append("	  and  TVCRD.STATUS = "+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append("	  and  TVCR.FUTURES_RESORCE_SETTING_ID="+frsId+"\n");	
		
		List<Map> ps=OemDAOUtil.findAll(sql.toString(), null);	
		return ps;
	}

	public List<Map> selectFuturesResourceSetting(String tfrId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select RESOURCE_SETTING_ID,BRAND_CODE,SERIES_CODE,SERIES_NAME,GROUP_CODE,GROUP_NAME,MODEL_YEAR,COLOR_CODE,COLOR_NAME,TRIM_CODE,TRIM_NAME,FACTORY_OPTIONS,STATUS,RESOURCE_CODE,\n");
		sql.append("	   NODE_STATUS_START,NODE_STATUS_END,to_char(NODE_DATE_START,'yyyy-MM-dd') NODE_DATE_START,to_char(NODE_DATE_END,'yyyy-MM-dd') NODE_DATE_END\n");
		sql.append("   from TT_FUTURES_RESOURCE_SETTING where RESOURCE_SETTING_ID="+tfrId+"\n");
		
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		

		return list;
	}

	/**
	 * 期货资源设定
	 * @param vin
	 * @param loginUser
	 * @return
	 * @throws SQLException 
	 */
	public int checkFuturesResourceSetting(String vin) throws SQLException{
		int num = 0;
		Date now = new Date();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Map> list = dao.getFuturesResourceSettings();
		for(int i=0;i<list.size();i++){
			Map m = list.get(i);
			StringBuffer sql = new StringBuffer();
			sql.append("select t.VEHICLE_ID from TM_VEHICLE_dec t,("+getVwMaterialSql()+") v \n");
			sql.append("   where t.MATERIAL_ID=v.MATERIAL_ID\n");
			sql.append("     and t.vin='"+vin+"'\n");
			sql.append("	 and v.MATERIAL_ID in(select MATERIAL_ID from ("+getVwMaterialSql()+") vm where 1=1 \n");
			sql.append(changeStr("vm.BRAND_CODE",m.get("BRAND_CODE").toString(),","));
			sql.append(changeStr("vm.SERIES_NAME",m.get("SERIES_NAME").toString(),","));
			sql.append(changeStr("vm.GROUP_NAME",m.get("GROUP_NAME").toString(),","));
			sql.append(changeStr("vm.MODEL_YEAR",m.get("MODEL_YEAR").toString(),","));
			sql.append(changeStr("vm.COLOR_NAME",m.get("COLOR_NAME").toString(),","));
			sql.append(changeStr("vm.TRIM_NAME",m.get("TRIM_NAME").toString(),","));	
			sql.append(changeStr("vm.FACTORY_OPTIONS",m.get("FACTORY_OPTIONS").toString(),"_"));	
			
			sql.append("	     )\n");
			if(!m.get("NODE_STATUS_START").toString().equals("0")&&!m.get("NODE_STATUS_END").toString().equals("0")){
				sql.append("	 and t.NODE_STATUS in (select CODE_ID from TC_CODE where type="+OemDictCodeConstants.VEHICLE_NODE+" and  num>=(select num from TC_CODE where CODE_ID="+m.get("NODE_STATUS_START")+") and num<=(select num from TC_CODE where CODE_ID="+m.get("NODE_STATUS_END")+"))\n");
			}else if(!m.get("NODE_STATUS_START").toString().equals("0")&&m.get("NODE_STATUS_END").toString().equals("0")){
				sql.append("	 and t.NODE_STATUS="+m.get("NODE_STATUS_START")+"\n");
			}
			if(m.get("NODE_DATE_START")!=null&&m.get("NODE_DATE_END")!=null){
				sql.append("	 and t.NODE_DATE>='"+m.get("NODE_DATE_START")+"' and t.NODE_DATE <='"+m.get("NODE_DATE_END")+" 23:59:59.999'\n");
			}else if(m.get("NODE_DATE_START")!=null&&m.get("NODE_DATE_END")==null){
				sql.append("	 and t.NODE_DATE>='"+m.get("NODE_DATE_START")+"'\n");
			}else if(m.get("NODE_DATE_START")==null&&m.get("NODE_DATE_END")!=null){
				sql.append("	 and t.NODE_DATE<='"+m.get("NODE_DATE_END")+"'\n");
			}
			sql.append("	and not exists(select 1 from TT_VS_ORDER where VIN=t.VIN and ORDER_STATUS<20071008)\n");
			
			List<Map<String,Object>> l = dao.pageQuery(sql.toString(), null, getFunName());
			if(l.size()>0){
				Map<String,Object> mp = l.get(0);
				num++;
				//插入公共资源记录
				TtVsCommonResourcePO tvcr = new TtVsCommonResourcePO();
				tvcr.setLong("COMMON_ID", null);
				tvcr.setLong("Futures_Resorce_Setting_Id", new Long(m.get("RESOURCE_SETTING_ID").toString()));
				tvcr.setLong("VEHICLE_ID", new Long(mp.get("VEHICLE_ID").toString()));				
				tvcr.setLong("CREATE_BY", new Long(88888881));
				tvcr.setDate("CREATE_DATE", now);				
				tvcr.setLong("RESOURCE_SCOPE", new Long(m.get("RESOURCE_CODE").toString()));				
				tvcr.setInteger("STATUS", OemDictCodeConstants.COMMON_RESOURCE_STATUS_02);//资源状态（未下发1，已下发2，已取消3）
				tvcr.setInteger("TYPE", OemDictCodeConstants.COMMON_RESOURCE_TYPE_01);
				tvcr.setDate("ISSUED_DATE", now);
				tvcr.saveIt();
				
				TtVsFactoryOrderPO tfPO = new TtVsFactoryOrderPO();
				List<TtVsFactoryOrderPO> fl = TtVsFactoryOrderPO.findBySQL("SELECT * FROM TT_VS_FACTORY_ORDER WHERE VIN=?", vin);
				if(fl.size()>0){
					tfPO = fl.get(0);
				}
				//插入公共资源明细记录
				TtVsCommonResourceDetailPO tvcrd = new TtVsCommonResourceDetailPO();
				//tvcrd.setCommonDetailId(new Long(SequenceManager.getSequence(null)));
				tvcrd.setLong("COMMON_ID", tvcr.get("COMMON_ID"));
				tvcrd.setLong("CREATE_BY", new Long(88888881));
				tvcrd.setDate("CREATE_DATE", now);
				tvcrd.setLong("FACTORY_ORDER_ID", tfPO.get("COMMON_DETAIL_ID"));//工厂id
				tvcrd.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);//资源有效状态
				tvcrd.setInteger("TYPE", OemDictCodeConstants.COMMON_RESOURCE_TYPE_01);
				tvcrd.setLong("VEHICLE_ID", new Long(mp.get("VEHICLE_ID").toString()));//车辆ID
				tvcrd.saveIt();
					
				//车辆表车辆节点设为"资源已分配" 期货不更新车辆节点
				List<TmVehiclePO> tPO = TmVehiclePO.findBySQL("SELECT * FROM TM_VEHICLE_DEC WHERE VIN=?", vin);
				TmVehiclePO tvPO = tPO.get(0);
				tvPO.setDate("NODE_DATE", now);
				tvPO.saveIt();
				
				//记录详细车籍
				TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
				vhclChng.setLong("VHCL_CHANGE_ID", null);
				vhclChng.setLong("VEHICLE_ID", new Long(mp.get("VEHICLE_ID").toString()));
				vhclChng.setInteger("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_24);
				vhclChng.setString("CHANGE_DESC", "下发公共资源");
				vhclChng.setDate("CHANGE_DATE", now);
				vhclChng.setLong("CREATE_BY", new Long(88888881));
				vhclChng.setDate("CREATE_DATE", now);
				vhclChng.saveIt();
				
				//写入接口表，将期货车辆（VIN+车型）传给中进CATC系统
				//不写了2014/02/11
			}
		}
		return num;
	}
	@SuppressWarnings("rawtypes")
	private List<Map> getFuturesResourceSettings() {
		StringBuffer sql = new StringBuffer();
		sql.append("select RESOURCE_SETTING_ID,BRAND_CODE,SERIES_NAME,GROUP_NAME,MODEL_YEAR,COLOR_NAME,TRIM_NAME,FACTORY_OPTIONS,STATUS,RESOURCE_CODE,\n");
		sql.append("	   IFNULL(NODE_STATUS_START,0) NODE_STATUS_START,IFNULL(NODE_STATUS_END,0) NODE_STATUS_END,DATE_FORMAT(NODE_DATE_START,'%Y-%m-%d') NODE_DATE_START,DATE_FORMAT(NODE_DATE_END,'%Y-%m-%d') NODE_DATE_END\n");
		sql.append("   from TT_FUTURES_RESOURCE_SETTING_DCS where STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		 
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null );
		return list;
	}

	private String changeStr(String tabName,String str,String sp) throws SQLException{
		if(str==null){
			return "";
		}
		String result = "";
		String[] s = str.split(sp);
		for(int i=0;i<s.length;i++){
			if(!s[i].equals("")){
				result+="'"+s[i]+"',";				
			}		
		}
		if(result.indexOf(",")>0){
			result = result.substring(0, result.length()-1);
		}
		if(!result.equals("")){
			result="	 	 and "+tabName+" in("+result+")\n";
		}
		return result;
	}


}