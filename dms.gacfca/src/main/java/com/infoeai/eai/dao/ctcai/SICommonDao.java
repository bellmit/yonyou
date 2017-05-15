/**
 * @Title: DealerRebateImportDao.java
 * @Description:
 * @Copyright: Copyright (c) 2013
 * @Company: http://autosoft.ufida.com
 * @Date: 2013-5-17
 * @author niehao
 * @version 1.0
 * @remark
 */
package com.infoeai.eai.dao.ctcai;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.vo.TmVhclMaterialGroupVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
@SuppressWarnings("rawtypes")
public class SICommonDao extends OemBaseDAO{
	public static Logger logger = LoggerFactory.getLogger(SICommonDao.class);
	
	/**
	 * 功能说明:根据给定的字段查找物料id
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-23
	 * @return
	 */
	
	public Long getMaterialId(Map<String, String> passValue) {
		//定义返回的物料id
		Long materialId = null;
		//接收传递的参数
		String model = passValue.get("model");  // 暂定
		String modelYear = passValue.get("modelYear");
		String colour = passValue.get("colour");
		String trim = passValue.get("trim");
		String factoryStandardOptions = passValue.get("factoryStandardOptions");  //标准配置(不能为空)
		String factoryOptions = passValue.get("factoryOptions");    //其他配置(可以为空)
//		String localOptions = passValue.get("localOptions");        //本地配置(可以为空)
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT MATERIAL_ID \n");
		sql.append("    FROM ("+getVwMaterialSql()+") VM \n");
		sql.append("   WHERE 1=1  \n"); 
		sql.append("     AND MODEL_YEAR = '"+modelYear+"' \n");
		sql.append("     AND VM.COLOR_CODE = '"+colour+"' \n");
		sql.append("     AND VM.TRIM_CODE = '"+trim+"' \n");
		//sql.append("     and VM.MODEL_CODE = '"+model+"' \n");
		sql.append("     and VM.MODEL_CODE = '"+model.replace("-8BL", "")+"' \n");
		
//		sql.append("     AND ordertext(STANDARD_OPTION) = ordertext('"+factoryStandardOptions+"') \n");
		sql.append("     "+ordertext("STANDARD_OPTION",factoryStandardOptions)+" \n");
		if(null!=factoryOptions){
//			sql.append("     and ordertext(FACTORY_OPTIONS) = ordertext('"+factoryOptions+"') \n");
			sql.append("     "+ordertext("FACTORY_OPTIONS",factoryOptions)+" \n");
		}else{
			sql.append("     and (FACTORY_OPTIONS is null or FACTORY_OPTIONS = '') \n");
		}
//		if(null!=localOptions){
//			sql.append("     and ordertext(TVMG.LOCAL_OPTION) = ordertext('"+localOptions+"') \n");
//		}else{
//			sql.append("     and (TVMG.LOCAL_OPTION is null or TVMG.LOCAL_OPTION = '') \n");
//		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		
	   if(list.size()==0){
		   logger.info("=============没有找到对应条件的物料id=================");
		   materialId = new Long(11111);
	   }else if(list.size()>1){
		   logger.info("=============找出对应条件的物料id有多条返回记录=================");
		   materialId = Long.parseLong(CommonUtils.checkNull(list.get(0).get("MATERIAL_ID"))); //对于找出多条记录的情况，任取一个物料id
	   }else{
		   materialId = Long.parseLong(CommonUtils.checkNull(list.get(0).get("MATERIAL_ID")));
	   }
	   return materialId;
	}
	
	public String ordertext(String code,String str){
		StringBuffer sql = new StringBuffer();
		if(!"".equals(CommonUtils.checkNull(str))){
			String[] strs = str.split(",");
			sql.append(" and (");
			sql.append("length("+code+")="+str.length()+" and ");
			String sh = "";
			for (String s : strs) {
				if("".equals(sh)){
					sh = "REPLACE("+code+",'"+s+"','')";
				}else{
					sh = "REPLACE("+sh+",'"+s+"','')";
				}
			}
			if(strs.length>1){
				sh = "REPLACE("+sh+",',','')";
			}
			sql.append(sh+" = '')");
		}else{
			sql.append(" and "+code+" = '' ");
		}
		return sql.toString();
	}
	
	/**
	 * 功能说明:根据给定的字段查找车款
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-31
	 * @return
	 */
	public TmVhclMaterialGroupVO getGroup4Id(Map<String, String> passValue) {
		//定义返回的对象
		TmVhclMaterialGroupVO returnPO = new TmVhclMaterialGroupVO();
		//接收传递的参数
		String model = passValue.get("model");  
		String modelYear = passValue.get("modelYear");
		String factoryStandardOptions = passValue.get("factoryStandardOptions");  //标准配置(不能为空)
		String factoryOptions = passValue.get("factoryOptions");    //其他配置(可以为空)
		String localOptions = passValue.get("localOptions");        //本地配置(可以为空)
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT GROUP_ID,GROUP_CODE,GROUP_NAME \n");
		sql.append("    FROM TM_VHCL_MATERIAL_GROUP tmg \n");
		sql.append("   WHERE 1=1\n");
		sql.append("         and tmg.STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");//有效
		sql.append("         and tmg.MODEL_YEAR = '"+modelYear+"' \n");
//		sql.append("         AND ordertext(tmg.STANDARD_OPTION) = ordertext('"+factoryStandardOptions+"') \n");
		sql.append("     "+ordertext("tmg.STANDARD_OPTION",factoryStandardOptions)+" \n");
		if(null!=factoryOptions){
//			sql.append("     and ordertext(tmg.FACTORY_OPTIONS) = ordertext('"+factoryOptions+"') \n");
			sql.append("     "+ordertext("tmg.FACTORY_OPTIONS",factoryOptions)+" \n");
		}else{
			sql.append("     and (tmg.FACTORY_OPTIONS is null or tmg.FACTORY_OPTIONS = '') \n");
		}
		
		if(null!=localOptions && !"".equals(localOptions)){
			sql.append("     "+ordertext("tmg.LOCAL_OPTION",localOptions)+" \n");
//			sql.append("     and ordertext(tmg.LOCAL_OPTION) = ordertext('"+localOptions+"') \n");
		}else{
			sql.append("     and (tmg.LOCAL_OPTION is null or tmg.LOCAL_OPTION = '') \n");
		}
		
		sql.append("         AND tmg.PARENT_GROUP_ID = \n");
		sql.append("                (SELECT group_id \n");
		sql.append("                   FROM TM_VHCL_MATERIAL_GROUP \n");
		sql.append("                  WHERE GROUP_LEVEL = 3 AND group_code = '"+model.replace("-8BL", "")+"') \n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
				
				
				
		
	   if(list.size()==0){
		   logger.info("=============没有找到对应条件的物料组信息=================");
		   returnPO = null;
	   }else if(list.size()>1){
		   logger.info("=============找出对应条件的物料组信息有多条返回记录,取第一条数据=================");
		   returnPO.setGroupId(Long.parseLong(CommonUtils.checkNull(list.get(0).get("GROUP_ID"),"0")));
		   returnPO.setGroupCode(CommonUtils.checkNull(list.get(0).get("GROUP_CODE")));
		   returnPO.setGroupName(CommonUtils.checkNull(list.get(0).get("GROUP_NAME")));
	   }else{
		   returnPO.setGroupId(Long.parseLong(CommonUtils.checkNull(list.get(0).get("GROUP_ID"),"0")));
		   returnPO.setGroupCode(CommonUtils.checkNull(list.get(0).get("GROUP_CODE")));
		   returnPO.setGroupName(CommonUtils.checkNull(list.get(0).get("GROUP_NAME")));
	   }
	   return returnPO;
	}
	
	/**
	 * 功能说明:根据给定的节点代码查找codeId
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-23
	 * @return
	 */
	public String getCodeId(String codeDesc,String typeName) {
		String codeId = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT CODE_ID \n");
		sql.append("    FROM TC_CODE_DCS TC \n");
		sql.append("   WHERE locate('"+codeDesc+"',TC.CODE_DESC) !=0\n"); 
		if(null!=typeName){
			sql.append("   AND TC.TYPE_NAME = '"+typeName+"'\n"); 
		}

	   List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	   codeId = CommonUtils.checkNull(list.get(0).get("CODE_ID"));
	   return codeId;
	}
	
	/**
	 * 功能说明:对传进来的日期和时间字符串，进行组合成时间格式
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-23
	 * @return
	 */
	public Date dataTime(String actionDate,String actionTime) {
		Date dateTime = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			dateTime =  sdf.parse(actionDate+actionTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateTime;
	}
	
	/**
	 * 功能说明:获取新的id，取值规则是当前表中最大的id+1
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-23
	 * @return 对应code的id
	 */
	public Long getNewMaterialId() {
     /*多线程执行的时候出现重复数据
      * 	
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  select max(MATERIAL_ID)+1 as material_id from TM_VHCL_MATERIAL \n");

		List<TcCodePO> list = factory.select(sql.toString(), null,
				new DAOCallback<TcCodePO>() {

					public TcCodePO wrapper(ResultSet rs, int idx) {
						TcCodePO bean = new TcCodePO();
						try {
							bean.setCodeId(rs.getString("MATERIAL_ID"));
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return bean;
					}
				});
	   codeId = new Long(list.get(0).getCodeId());*/
	   Long codeId = null;
//	   codeId = new Long();
	   return codeId;
	}
	
	/**
	 * 功能说明:获取新的id，取值规则是当前表中最大的id+1
	 * 创建人: zhangRM 
	 * 创建日期: 2013-05-23
	 * @return 对应code的id
	 */
	public Long getNewId() {
		Long codeId = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  select max(ID)+1 as material_id from TM_VHCL_MATERIAL_GROUP_R \n");

	   List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	   codeId = new Long(CommonUtils.checkNull(list.get(0).get("MATERIAL_ID"),"0"));
	   return codeId;
	}
	
	/**
	 * 功能说明:根据给定dealerId查找唯一的company_code
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-19
	 * @return
	 */
	public String getCompanyCodeById(Long dealerId) {
		String companyCode = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT COMPANY_CODE \n");
		sql.append("    FROM TM_DEALER tmd, TM_COMPANY tmc \n");
		sql.append("   WHERE tmd.COMPANY_ID = tmc.COMPANY_ID \n"); 
		sql.append("     and tmd.dealer_id ="+dealerId+" \n"); 

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			companyCode = CommonUtils.checkNull(list.get(0).get("COMPANY_CODE"));
		}else{
			companyCode = null;
		}
		
	    return companyCode;
	}
	
	/**
	 * 功能说明:根据给定物料组code及物料组级别查treeCode
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-19
	 * @return
	 */
	public String getTreeCode(String groupCode,String groupLevel) {
		String treeCode = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT TREE_CODE \n");
		sql.append("    FROM TM_VHCL_MATERIAL_GROUP \n");
		sql.append("   WHERE group_code = '"+groupCode+"' AND group_level = '"+groupLevel+"' \n"); 

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			treeCode = CommonUtils.checkNull(list.get(0).get("TREE_CODE"));
		}else{
			treeCode = null;
		}
		
	    return treeCode;
	}
	
	/**
	 * 功能说明:根据给定VIN码查找车型
	 * 创建人: zhangRM 
	 * 创建日期: 2013-07-19
	 * @return
	 */
	@SuppressWarnings("null")
	public TmVhclMaterialGroupVO getGroupInfo(String vin) {
		TmVhclMaterialGroupVO treeCode = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT vm.SERIES_CODE, vm.GROUP_CODE  \n");
		sql.append("        FROM TM_VEHICLE_DEC tmv, VW_MATERIAL vm \n");
		sql.append("      WHERE tmv.MATERIAL_ID = vm.MATERIAL_ID \n");
		sql.append("        and tmv.vin = '"+vin+"' \n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			treeCode.setGroupCode(CommonUtils.checkNull(list.get(0).get("SERIES_CODE")));
			treeCode.setGroupName(CommonUtils.checkNull(list.get(0).get("GROUP_CODE")));
		}
	    return treeCode;
	}
	
	/**
	 * 功能说明:ZPDF时清空当前车辆的OrgStorageDate时间
	 * 创建人: zhangRM 
	 * 创建日期: 2013-08-03
	 * @return
	 */
	public void cealerOrgStorDate(String vin) {
		String  sql = "  update TM_VEHICLE_DEC set org_storage_date = null where  vin = '"+vin+"'" ;
	    OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());
	}
	
	/**
	 * 功能说明:ZDRQ时需要更新订单表状态为已撤单；
	 * 创建人: zhangRM 
	 * 创建日期: 2013-08-06
	 * @return
	 */
	public void changeStatusByVin(String vin) {
		String  sql = "  UPDATE TT_VS_ORDER SET order_status = 20071009,update_date=sysdate,update_by=80000002 WHERE vin = '"+vin+"' AND order_status NOT IN (20071008, 20071009) and IS_DEL=0 " ;
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());
	}
	
	/**
	 * 功能说明:根据给定系统的dealerCode查找我们系统对应的dealerCode
	 * 创建人: zhangRM 
	 * 创建日期: 2013-08-13
	 * @return
	 */
	public String getDealerCodeByInfo(String dealerCode,String flagCode) {
		String returnCode = null;
		
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tmd.DEALER_CODE \n");
		sql.append("    FROM tm_company tmc, tm_dealer tmd \n");
		sql.append("   WHERE tmd.company_id = tmc.company_id  \n"); 
		if(flagCode.equals("eLink")){
			sql.append("     AND tmc.elink_code = '"+dealerCode+"' \n"); 
		}else if(flagCode.equals("ctcai")){
			sql.append("     AND tmc.ctcai_code = '"+dealerCode+"' \n"); 
		}
		if(flagCode.equals("eLink")){
			sql.append("     and tmd.DEALER_TYPE = 10771002 \n"); 
		}else if(flagCode.equals("ctcai")){
			sql.append("     and tmd.DEALER_TYPE = 10771001 \n"); 
		}
		

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			returnCode = CommonUtils.checkNull(list.get(0).get("DEALER_CODE"));
		}else{
			//returnCode = null;
			// E-LINK传过来的经销商系统查不到,将返回E-LINK传过来的原始经销商代码
			returnCode = dealerCode;
		}
		
	    return returnCode;
	}
	
	/**
	 * 功能说明:根据给定物料code查name
	 * 参数说明:codeFlag 表示trimCode or colourCode
	 * 创建人: zhangRM 
	 * 创建日期: 2013-09-12
	 * @return
	 */
	public String getNameByCode(String code,String codeFlag) {
		String name = null;
		
		StringBuffer sql = new StringBuffer("");
		
		if(codeFlag.equals("colourCode")){
			sql.append("  SELECT DISTINCT COLOR_CODE CODE,COLOR_NAME NAME FROM TM_VHCL_MATERIAL \n");
			sql.append("    WHERE COLOR_CODE = '"+code+"' \n");
			sql.append("     AND COLOR_NAME IS NOT NULL \n"); 
		}else{
			sql.append("  SELECT DISTINCT TRIM_CODE CODE,TRIM_NAME NAME FROM TM_VHCL_MATERIAL \n");
			sql.append("    WHERE TRIM_CODE = '"+code+"' \n");
			sql.append("     AND TRIM_NAME IS NOT NULL \n"); 
		}

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			name = CommonUtils.checkNull(list.get(0).get("NAME"));
		}else{
			name = "TBD";
		}
		
	    return name;
	}
	
	/**
	 * 功能说明:根据给定车辆ID查找对应的VIN码和对应的中进dealerCode
	 * 创建人: zhangRM 
	 * 创建日期: 2013-10-18
	 * @return
	 */
	public List<Map> getVinAndDealerCode(String vin) {
//		vin = vin.replace("，", ",");
//		vin = vin.replace(",", "','");
		StringBuffer sql = new StringBuffer("");
		sql.append("  SELECT tor.VIN,tmc.CTCAI_CODE  \n");
		sql.append("    FROM TT_VS_ORDER tor, TM_DEALER tmd, TM_COMPANY tmc , TM_VEHICLE_DEC TV, ("+getVwMaterialSql()+") VM  \n"); //edit 只查询进口车  by weixia 2015-08-05 
		sql.append("   WHERE 1=1 \n");
		sql.append("     AND tor.VIN in ("+vin+")  \n");
		sql.append("     AND tmd.DEALER_ID = tor.DEALER_ID   \n");
		sql.append("     AND tmc.COMPANY_ID = tmd.COMPANY_ID  \n");		
		/** edit 只查询进口车  by weixia 2015-08-05  start **/
		sql.append("     AND tor.VIN = TV.VIN AND TV.MATERIAL_ID = VM.MATERIAL_ID  \n");
		sql.append("     AND VM.GROUP_TYPE = '"+OemDictCodeConstants.GROUP_TYPE_IMPORT+"' \n");
		/** edit 只查询进口车  by weixia 2015-08-05  end **/		
		sql.append("	 AND tor.ORDER_STATUS<"+OemDictCodeConstants.ORDER_STATUS_08+"\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		  /*SELECT tor.VIN,tmc.CTCAI_CODE  
		    FROM TT_VS_ORDER tor, TM_DEALER tmd, TM_COMPANY tmc , TM_VEHICLE_DEC TV, 
			(		
						 SELECT MG1.GROUP_ID AS BRAND_ID,         MG1.GROUP_CODE AS BRAND_CODE,         MG1.GROUP_NAME AS BRAND_NAME,               MG1.STATUS AS BRAND_STATUS,         MG2.GROUP_ID AS SERIES_ID,         MG2.GROUP_CODE AS SERIES_CODE,               MG2.GROUP_NAME AS SERIES_NAME,         MG2.STATUS AS SERIES_STATUS,         MG2.GROUP_TYPE AS GROUP_TYPE,               MG3.GROUP_ID AS MODEL_ID,         MG3.GROUP_CODE AS MODEL_CODE,         MG3.GROUP_NAME AS MODEL_NAME,               MG3.STATUS AS MODEL_STATUS,         MG3.WX_ENGINE AS WX_ENGINE,           MG3.OILE_TYPE AS OILE_TYPE,                 MG4.GROUP_ID AS GROUP_ID,         MG4.GROUP_CODE AS GROUP_CODE,         MG4.GROUP_NAME AS GROUP_NAME,               MG4.STATUS AS GROUP_STATUS,         MG4.MODEL_YEAR AS MODEL_YEAR,         MG4.FACTORY_OPTIONS AS FACTORY_OPTIONS,               MG4.STANDARD_OPTION AS STANDARD_OPTION,         MG4.LOCAL_OPTION AS LOCAL_OPTION,               MG4.SPECIAL_SERIE_CODE AS SPECIAL_SERIE_CODE,         M.TRIM_CODE,         M.TRIM_NAME,         M.COLOR_CODE,               M.COLOR_NAME,         M.MATERIAL_ID,         M.MATERIAL_CODE,         M.MATERIAL_NAME,         M.IS_SALES,           MG3.MVS AS MVS      
				
						 FROM TM_VHCL_MATERIAL M,         TM_VHCL_MATERIAL_GROUP_R MGR,         TM_VHCL_MATERIAL_GROUP MG4,         TM_VHCL_MATERIAL_GROUP MG3,         TM_VHCL_MATERIAL_GROUP MG2,         TM_VHCL_MATERIAL_GROUP MG1             
				
						 WHERE M.MATERIAL_ID = MGR.MATERIAL_ID     AND MGR.GROUP_ID = MG4.GROUP_ID    AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID     AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID             AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID     AND M.COMPANY_ID = 2010010100070674 ) VM  
		   WHERE 1=1  
		     AND tmd.DEALER_ID = tor.DEALER_ID   
		     AND tmc.COMPANY_ID = tmd.COMPANY_ID  		
		     AND tor.VIN = TV.VIN AND TV.MATERIAL_ID = VM.MATERIAL_ID  
		     AND VM.GROUP_TYPE = '90081002' 	
			 AND tor.ORDER_STATUS< '20071008'*/
	    return list;
	}
	
	
	/**
	 * 功能说明:根据给定车辆vin查找对应的订单号
	 * 创建人: baojie 
	 * 创建日期: 2014-01-14
	 * @return
	 */
	public List<Map> getOrderByVin(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvo.ORDER_ID,tvo.ORDER_TYPE\n");
		sql.append("   from TT_VS_ORDER tvo,TM_VEHICLE_DEC tv\n");
		sql.append("   where tvo.VIN=tv.VIN\n");
		sql.append("     and tvo.ORDER_STATUS NOT IN (20071008, 20071009) \n");
		sql.append("     and tvo.vin='"+vin+"'\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	    return list;
	}

	/**
	 * 功能说明:根据给定车辆vin查找对应的公共资源信息
	 * 创建人: baojie 
	 * 创建日期: 2014-01-14
	 * @return
	 */
	public List<Map> selectForZDRQ(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select TV.VIN,TV.NODE_STATUS,TV.VEHICLE_ID,TVCR.COMMON_ID\n");
		sql.append("   from  TM_VEHICLE_DEC                 	   TV,\n");
		sql.append("  		 TT_VS_COMMON_RESOURCE      	   TVCR,\n");
		sql.append("   		 TT_VS_COMMON_RESOURCE_DETAIL      TVCRD\n");
		sql.append("    where  TVCR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("  	  and  TVCR.COMMON_ID = TVCRD.COMMON_ID\n");
		sql.append("      and  TVCRD.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append("  	  and  TVCR.STATUS="+OemDictCodeConstants.COMMON_RESOURCE_STATUS_02+"\n");
		sql.append("	  and  TV.vin ='"+vin+"'\n");
		
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	public String getRelationIdInfo(String id,String type) {
		StringBuffer sql = new StringBuffer("");
		
		sql.append("  SELECT tcr.CODE_ID \n");
		sql.append("    FROM TC_RELATION tcr, tc_code tcc \n");
		sql.append("   WHERE tcr.CODE_ID = tcc.CODE_ID AND tcc.TYPE = "+type+"\n");
		sql.append("     and tcr.RELATION_CODE = "+id+"\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list.size()>0){
			return CommonUtils.checkNull(list.get(0).get("CODE_ID"));
		}else{
			return null;
		}
	}
	
	
	/**
	 * 功能说明:根据车辆vin获取当前有效订单；
	 * 创建人: zhangRM 
	 * 创建日期: 2013-08-06
	 * @return
	 */
	public List<Map> getValidOrderByOrderNo(String orderNo) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvo.ORDER_ID,tvo.DEALER_ID \n");
		sql.append("   from TT_VS_ORDER tvo \n");
		sql.append("   where 1=1");
		sql.append("     and tvo.ORDER_STATUS = 20071006 \n");
		sql.append("     and tvo.ORDER_NO='"+orderNo+"'\n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	    return list;
	}
	
	
	/**
	 * 功能说明:根据车辆清空订单表中的swt_affirm_date；
	 * 创建人: baojie 
	 * 创建日期: 2014-04-25
	 * @return
	 */
	public int cleanOrderSwtAffirmDateByVin(String vin) {
		String  sql = " UPDATE TT_VS_ORDER SET SWT_AFFIRM_DATE = null,update_date=sysdate,update_by=80000002 WHERE vin = '"+vin+"' AND order_status = 20071006 and IS_DEL=0 " ;
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<Object>());
//		String sql2 = " SELECT 1"
//		List<Map> list 
		return 1;
	}

	public List<Map> getValidOrderByVin(String vin) {
		String sql = "select IN_DEALER_ID from TT_VS_VEHICLE_TRANSFER   tvvt where tvvt.VEHICLE_ID=(select VEHICLE_ID from TM_VEHICLE_DEC where vin ='"+vin+"') and tvvt.CHECK_STATUS=20201002";
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	    return list;
	}
	
	/**
	 * 根据中进code查找DCS系统对应的dealerId
	 * @param factory
	 * @param dealerCode
	 * @return
	 */
	public List<Map> getDealeIdByInfo(String dealerCode) {
		StringBuffer sql = new StringBuffer("");
		sql.append("SELECT tmd.DEALER_ID \n");
		sql.append("	FROM TM_COMPANY tmc, TM_DEALER tmd \n");
		sql.append("	WHERE tmd.COMPANY_ID = tmc.COMPANY_ID  \n"); 
		sql.append("     AND tmc.CTCAI_CODE = '"+dealerCode+"' \n"); 
		sql.append("     and tmd.DEALER_TYPE = 10771001 \n"); 

		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
	    return list;
	}
}
