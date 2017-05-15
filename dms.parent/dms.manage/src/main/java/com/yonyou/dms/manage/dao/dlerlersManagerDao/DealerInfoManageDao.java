package com.yonyou.dms.manage.dao.dlerlersManagerDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.PO.dealerManager.TmVsDealerShowroomPO;
/**
 * 经销商查询
 * @author ZhaoZ
 * @date 2017年2月24日
 */
@Repository
public class DealerInfoManageDao extends OemBaseDAO{


	
	/**
	 * 查询经销商信息
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getQueryDealers(Map<String, String> queryParams) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryDealersSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 查询经销商SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */

	private String getQueryDealersSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer(); 
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT TDS.*, TG.BIG_ORG_NAME,TG.BIG_ORG_ID, TG.ORG_NAME, TG.ORG_ID from ( SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_TYPE,TC.COMPANY_SHORTNAME,TD.STATUS,TD.IS_EC \n");
		sql.append(" FROM TM_DEALER TD,TM_COMPANY TC \n");
		sql.append(" WHERE TD.COMPANY_ID=TC.COMPANY_ID \n");
		System.out.println(queryParams.get("provinceId"));
		if(!StringUtils.isNullOrEmpty(queryParams.get("provinceId"))){
			sql.append(" AND TD.PROVINCE_ID = ?");
			params.add(queryParams.get("provinceId"));
		}
		//增加车厂公司过滤
		sql.append("     AND TC.OEM_COMPANY_ID="+loginUser.getCompanyId()+"\n");
		
	
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sql.append(" AND TD.DEALER_CODE LIKE ?");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
			sql.append(" AND TD.DEALER_SHORTNAME LIKE ?");
			params.add("%"+queryParams.get("dealerShortname")+"%");
		}
		
	
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TD.STATUS = ?");
			params.add(queryParams.get("status"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("type"))){
			sql.append(" AND TD.DEALER_TYPE = ?");
			params.add(queryParams.get("type"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("companyId"))){
			sql.append(" AND TC.COMPANY_ID = ?");
			params.add(queryParams.get("companyId"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("isEc"))){
			sql.append(" AND TD.IS_EC = ?");
			params.add(queryParams.get("isEc"));
		}
		
		
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId")))
		{
			sql.append(" )tds ,( \n");
			sql.append("SELECT TDOR.DEALER_ID,TOR2.ORG_DESC BIG_ORG_NAME,TOR2.ORG_ID BIG_ORG_ID,TOR3.ORG_DESC ORG_NAME,TOR3.ORG_ID  \n");
			sql.append(" FROM  TM_DEALER_ORG_RELATION TDOR,TM_ORG  TOR3, TM_ORG TOR2 \n");
			sql.append(" WHERE TOR3.ORG_ID = TDOR.ORG_ID \n");
			sql.append(" AND TOR3.ORG_LEVEL = 3 \n");
			sql.append(" AND TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
			sql.append(" AND TOR2.ORG_LEVEL = 2 \n");
			sql.append(" AND TOR3.ORG_ID=? \n");
			sql.append(") TG WHERE TDS.DEALER_ID=TG.DEALER_ID \n");		
			params.add(queryParams.get("orgId"));
		}else
		{
			sql.append(" )TDS     LEFT JOIN ( \n");
			sql.append("SELECT TDOR.DEALER_ID,TOR2.ORG_DESC BIG_ORG_NAME,TOR2.ORG_ID BIG_ORG_ID,TOR3.ORG_DESC ORG_NAME,TOR3.ORG_ID  \n");
			sql.append(" FROM  TM_DEALER_ORG_RELATION TDOR,TM_ORG  TOR3, TM_ORG TOR2 \n");
			sql.append(" WHERE TOR3.ORG_ID = TDOR.ORG_ID \n");
			sql.append(" AND TOR3.ORG_LEVEL = 3 \n");
			sql.append(" AND TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
			sql.append(" AND TOR2.ORG_LEVEL = 2 \n");
			sql.append(") TG ON TDS.DEALER_ID=TG.DEALER_ID \n");	
		}
	
		
		return sql.toString();
		
	
	}
	
	
	/**
	 * 查询经销商基本信息
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getQueryDealerInfos(Map<String, String> queryParams) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryDealerInfosSQL(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	
	
/**
 * 查询经销商基本信息SQL
 * @param queryParams
 * @param params
 * @return
 */
	public String getQueryDealerInfosSQL(Map<String, String> queryParams, List<Object> params) {

		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT TDS.*, TG.BIG_ORG_NAME,TG.BIG_ORG_ID, TG.ORG_NAME, TG.ORG_ID from ( SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_TYPE,TC.COMPANY_SHORTNAME,TD.RANGES,TD.CC_CODE,TD.COMPANY_PHONE,TD.OPEN_TIME,TD.OPEN_RANGE,TD.STATUS,TD.DEALER_AUDIT_STATUS,TD.IS_EC,CASE TD.DEALER_TYPE WHEN '"+OemDictCodeConstants.DEALER_TYPE_DVS+"' THEN TCD.COMPANY_LEVEL ELSE '' END AS COMPANY_LEVEL \n");
		sql.append(" FROM TM_DEALER_ED TD,TM_COMPANY TC LEFT JOIN TT_COMPANY_DETAIL TCD ON TC.COMPANY_ID = TCD.COMPANY_ID \n");
		sql.append(" WHERE TD.COMPANY_ID=TC.COMPANY_ID \n");
		
		//增加车厂公司过滤
		sql.append("     AND TC.OEM_COMPANY_ID="+loginUser.getCompanyId()+"\n");
		
		//省份
		if(!StringUtils.isNullOrEmpty(queryParams.get("provinceId"))){
			sql.append(" AND TD.PROVINCE_ID in (?)");
			params.add(queryParams.get("provinceId"));
		}
		//经销商代码
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sql.append(" AND TD.DEALER_CODE LIKE ?");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		//简称
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
			sql.append(" AND TD.DEALER_SHORTNAME LIKE ?");
			params.add("%"+queryParams.get("dealerShortname")+"%");
		}
		
		//状态
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
			sql.append(" AND TD.STATUS = ?");
			params.add(queryParams.get("status"));
		}
		//类型
		if(!StringUtils.isNullOrEmpty(queryParams.get("type"))){
			sql.append(" AND TD.DEALER_TYPE = ?");
			params.add(queryParams.get("type"));
		}
		//公司id
		if(!StringUtils.isNullOrEmpty(queryParams.get("companyId"))){
			sql.append(" AND TC.COMPANY_ID = ?");
			params.add(queryParams.get("companyId"));
		}
		//是否官网
		if(!StringUtils.isNullOrEmpty(queryParams.get("sex"))){
			sql.append(" AND TD.IS_EC = ?");
			params.add(queryParams.get("sex"));
		}
		//经销商审核状态
		if(!StringUtils.isNullOrEmpty(queryParams.get("auditStatus"))){
			sql.append(" AND TD.DEALER_AUDIT_STATUS = ?");
			params.add(queryParams.get("auditStatus"));
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId")))
		{
			sql.append(" )TDS ,( \n");
			sql.append("SELECT TDOR.DEALER_ID,TOR2.ORG_DESC BIG_ORG_NAME,TOR2.ORG_ID BIG_ORG_ID,TOR3.ORG_DESC ORG_NAME,TOR3.ORG_ID  \n");
			sql.append(" FROM  TM_DEALER_ORG_RELATION TDOR,TM_ORG  TOR3, TM_ORG TOR2 \n");
			sql.append(" WHERE TOR3.ORG_ID = TDOR.ORG_ID \n");
			sql.append(" AND TOR3.ORG_LEVEL = 3 \n");
			sql.append(" AND TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
			sql.append(" AND TOR2.ORG_LEVEL = 2 \n");
			sql.append(" AND TOR3.ORG_ID=? \n");
			sql.append(") TG WHERE TDS.DEALER_ID=TG.DEALER_ID \n");		
			params.add(queryParams.get("orgId"));
		}else
		{
			sql.append(" )TDS     LEFT JOIN ( \n");
			sql.append("SELECT TDOR.DEALER_ID,TOR2.ORG_DESC BIG_ORG_NAME,TOR2.ORG_ID BIG_ORG_ID,TOR3.ORG_DESC ORG_NAME,TOR3.ORG_ID  \n");
			sql.append(" FROM  TM_DEALER_ORG_RELATION TDOR,TM_ORG  TOR3, TM_ORG TOR2 \n");
			sql.append(" WHERE TOR3.ORG_ID = TDOR.ORG_ID \n");
			sql.append(" AND TOR3.ORG_LEVEL = 3 \n");
			sql.append(" AND TOR3.PARENT_ORG_ID = TOR2.ORG_ID \n");
			sql.append(" AND TOR2.ORG_LEVEL = 2 \n");
			sql.append(") TG ON TDS.DEALER_ID=TG.DEALER_ID \n");	
		}	
		
		return sql.toString();
	}

	/**
	 * 经销商基本信息下载
	 * @param queryParams
	 * @return
	 */
	public List<Map> getDealerInfoList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParams, params);
		List<Map> dealerList = OemDAOUtil.findAll(sql, params);
		//展厅
		if(dealerList != null && !dealerList.isEmpty()){			
			for(int i = 0; i < dealerList.size(); i++){
				List<TmVsDealerShowroomPO> rmlist = TmVsDealerShowroomPO.find("DEALER_ID = ?", dealerList.get(i).get("DEALER_ID"));
				if(rmlist != null && !rmlist.isEmpty()){
					for(int j = 0; j < rmlist.size(); j++){
						Map map = dealerList.get(i);
						map.put("ROOM_ADDRESS"+(j+1), rmlist.get(j).getString("ADDRESS"));
					}
				}
			}
		}
		return dealerList;
	}

	/**
	 * 查询下载基本信息SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	
	private String getQuerySql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("  select  TD.DEALER_ID,TD.DEALER_CODE,\n");
		sql.append("tr_pro.REGION_NAME  PROVINCE,\n");
		sql.append("  tr_city.REGION_NAME  CITY,\n");
		sql.append("TD.DEALER_SHORTNAME,\n");
		sql.append("TD.DEALER_NAME,\n");
		sql.append("TD.MARKETING,\n");
		sql.append("CASE IFNULL(tcd.COMPANY_ID,0) WHEN '0' THEN '未入网' ELSE '已入网' END AS NETSTATE,\n");
//		sql.append("tor.ORG_NAME  ORG_NAME,\n");
//		sql.append("tor2.ORG_NAME   BIG_ORG_NAME,\n");
		sql.append("CASE TD.DEALER_TYPE WHEN "+OemDictCodeConstants.DEALER_TYPE_DVS+" THEN tor.ORG_NAME ELSE '' END AS ORG_NAME, "); //销售小区
		sql.append("CASE TD.DEALER_TYPE WHEN "+OemDictCodeConstants.DEALER_TYPE_DVS+" THEN tor2.ORG_NAME ELSE '' END AS BIG_ORG_NAME, "); //销售大区
		sql.append("CASE TD.DEALER_TYPE WHEN "+OemDictCodeConstants.DEALER_TYPE_DWR+" THEN tor.ORG_NAME ELSE '' END AS ORG_NAME_A, ");//售后小区
		sql.append("CASE TD.DEALER_TYPE WHEN "+OemDictCodeConstants.DEALER_TYPE_DWR+" THEN tor2.ORG_NAME ELSE '' END AS BIG_ORG_NAME_A, ");//售后大区
		sql.append("TD.ADDRESS,\n");
		sql.append("TD.ZIP_CODE,\n");
		sql.append("TD.FAX_NO,\n");
		sql.append("TD.EMAIL,\n");
		sql.append("	TD.LINK_MAN,\n");
		sql.append("TD.PHONE,\n");
		sql.append("	TD.LINK_MAN_MOBILE,\n");
		sql.append("tcd_range.CODE_DESC AS RANGES ,\n");
		sql.append("tc_clevel.CODE_DESC AS COMPANY_LEVEL, \n");
		sql.append("tcd.LEGAL_REPRESENTATIVE,\n");
		sql.append("tcd.COMPANY_NO,\n");
		sql.append("tcd.LICENSED_BRANDS,\n");
		sql.append("tcd.BUILD_STATUS,\n");
		sql.append("DATE_FORMAT(tcd.COMPLETION_DATE,'%Y-%m-%d') COMPLETION_DATE,\n");
		sql.append("DATE_FORMAT(tcd.START_DATE,'%Y-%m-%d') START_DATE,\n");
		sql.append("tcd.SHOWRROM_AREA,\n");
		sql.append("tcd.SHOWROOM_WIDTH,\n");
		sql.append("	tcd.SHOW_CARS,\n");
		sql.append("tcd.SERVICE_NUM,\n");
		sql.append("tcd.MACHINE_REPAIR,\n");
		sql.append("tcd.SHEET_SPRAY,\n");
		sql.append("tcd.PREFLIGHT_NUM,\n");
		sql.append("tcd.RESERVED_NUM,\n");
		sql.append("tcd.STOP_CAR,\n");
		sql.append("tcd.LAND,\n");
		sql.append("tcd.LAND_NATURE,\n");
		sql.append("tcd.PARTS_AREA,\n");
		sql.append("tcd.DANGER_AREA ,\n");
		sql.append("tcd.END_DATE,\n");
		sql.append("tdg.GROUP_NAME,\n");
		sql.append("tcd.RATE,\n");
		sql.append("tcd.REGISTERED_CAPITAL,\n");
		sql.append("tcd.COMPANY_INVESTORS,\n");
		sql.append("tcd.INVESTORS_TEL,\n");
		sql.append("tcd.INVESTORS_EMIAL,\n");
		sql.append("tcd.BRANDS,\n");
		sql.append("tcd.MAINTAINABILITY,\n");
		sql.append("DATE_FORMAT(TD.CREATE_DATE,'%Y-%m-%d %H:%i:%S') CREATE_DATE,\n");
		sql.append(" CASE td.DEALER_AUDIT_STATUS WHEN '40561001' THEN '未上报' WHEN '40561002' THEN '审核中' WHEN '40561003' THEN '审核通过' WHEN '40561004' THEN '审核驳回' ELSE '' END AS DEALER_AUDIT_STATUS ,\n");
		sql.append("DATE_FORMAT(TD.DEALER_AUDIT_DATE,'%Y-%m-%d %H:%i:%S') DEALER_AUDIT_DATE,\n");
		sql.append("DATE_FORMAT(TD.UPDATE_DATE,'%Y-%m-%d %H:%i:%S') UPDATE_DATE,\n");
    //总经理
		sql.append("IFNULL(tc1_post_type.CODE_DESC,'') POST_TYPE1,\n");
		sql.append("IFNULL(tvdp1.NAME,'') NAME1,\n");
		sql.append("IFNULL(tc1_gender.CODE_DESC,'') GENDER1,\n");
		sql.append("tvdp1.MOBILE  MOBILE1,\n");
		sql.append("tvdp1.TELEPHONE TELEPHONE1,\n");
		sql.append("IFNULL(tvdp1.EMAIL,'') EMAIL1,\n");
		sql.append("IFNULL(tc1_approve.CODE_DESC,'') APPROVE1,\n");
		sql.append("IFNULL(tvdp1.BANK_ACCOUNT,'') BANK_ACCOUNT1,\n");
		sql.append("IFNULL(tvdp1.BANK_ADDRESS,'') BANK_ADDRESS1,\n");
    //售后经理
		sql.append("	IFNULL(tc3_post_type.CODE_DESC,'') POST_TYPE3,\n");
		sql.append("	IFNULL(tvdp3.NAME,'') NAME3,\n");
		sql.append("	IFNULL(tc3_gender.CODE_DESC,'') GENDER3,\n");
		sql.append("	tvdp3.MOBILE  MOBILE3,\n");
		sql.append("	tvdp3.TELEPHONE TELEPHONE3,\n");
		sql.append("	IFNULL(tvdp3.EMAIL,'') EMAIL3,\n");
		sql.append("	IFNULL(tc3_approve.CODE_DESC,'') APPROVE3,\n");
		sql.append("	IFNULL(tvdp3.BANK_ACCOUNT,'') BANK_ACCOUNT3,\n");
		sql.append("	IFNULL(tvdp3.BANK_ADDRESS,'') BANK_ADDRESS3,\n");
    //销售经理
		sql.append(" 	IFNULL(tc2_post_type.CODE_DESC,'') POST_TYPE2,\n");
		sql.append("	IFNULL(tvdp2.NAME,'') NAME2,\n");
		sql.append("	IFNULL(tc2_gender.CODE_DESC,'') GENDER2,\n");
		sql.append("	tvdp2.MOBILE  MOBILE2,\n");
		sql.append("	tvdp2.TELEPHONE TELEPHONE2,\n");
		sql.append("	IFNULL(tvdp2.EMAIL,'') EMAIL2,\n");
		sql.append("	IFNULL(tc2_approve.CODE_DESC,'') APPROVE2,\n");
		sql.append("IFNULL(tvdp2.BANK_ACCOUNT,'') BANK_ACCOUNT2,\n");
		sql.append("IFNULL(tvdp2.BANK_ADDRESS,'') BANK_ADDRESS2,\n");
    //市场经理
		sql.append("  IFNULL(tc4_post_type.CODE_DESC,'') POST_TYPE4,\n");
		sql.append("	IFNULL(tvdp4.NAME,'') NAME4,\n");
		sql.append("	IFNULL(tc4_gender.CODE_DESC,'') GENDER4,\n");
		sql.append("	tvdp4.MOBILE  MOBILE4,\n");
		sql.append("	tvdp4.TELEPHONE TELEPHONE4,\n");
		sql.append("	IFNULL(tvdp4.EMAIL,'') EMAIL4,\n");
		sql.append("	IFNULL(tc4_approve.CODE_DESC,'') APPROVE4,\n");
		sql.append("  IFNULL(tvdp4.BANK_ACCOUNT,'') BANK_ACCOUNT4,\n");
		sql.append("  IFNULL(tvdp4.BANK_ADDRESS,'') BANK_ADDRESS4,\n");
    //DCC经理
		sql.append("   IFNULL(tc5_post_type.CODE_DESC,'') POST_TYPE5,\n");
		sql.append("	IFNULL(tvdp5.NAME,'') NAME5,\n");
		sql.append("	IFNULL(tc5_gender.CODE_DESC,'') GENDER5,\n");
		sql.append("		tvdp5.MOBILE  MOBILE5,\n");
		sql.append("		tvdp5.TELEPHONE TELEPHONE5,\n");
		sql.append("	IFNULL(tvdp5.EMAIL,'') EMAIL5,\n");
		sql.append("		IFNULL(tc5_approve.CODE_DESC,'') APPROVE5,\n");
		sql.append("	  IFNULL(tvdp5.BANK_ACCOUNT,'') BANK_ACCOUNT5,\n");
		sql.append("	  IFNULL(tvdp5.BANK_ADDRESS,'') BANK_ADDRESS5,\n");
    //技术主管
		sql.append("    IFNULL(tc7_post_type.CODE_DESC,'') POST_TYPE7,\n");
		sql.append("		IFNULL(tvdp7.NAME,'') NAME7,\n");
		sql.append("		IFNULL(tc7_gender.CODE_DESC,'') GENDER7,\n");
		sql.append("	tvdp7.MOBILE  MOBILE7,\n");
		sql.append("	tvdp7.TELEPHONE TELEPHONE7,\n");
		sql.append("	IFNULL(tvdp7.EMAIL,'') EMAIL7,\n");
		sql.append("	IFNULL(tc7_approve.CODE_DESC,'') APPROVE7,\n");
		sql.append("  IFNULL(tvdp7.BANK_ACCOUNT,'') BANK_ACCOUNT7,\n");
		sql.append("  IFNULL(tvdp7.BANK_ADDRESS,'') BANK_ADDRESS7,\n");
    //客户经理
		sql.append("   IFNULL(tc8_post_type.CODE_DESC,'') POST_TYPE8,\n");
		sql.append("		IFNULL(tvdp8.NAME,'') NAME8,\n");
		sql.append("	IFNULL(tc8_gender.CODE_DESC,'') GENDER8,\n");
		sql.append("		tvdp8.MOBILE  MOBILE8,\n");
		sql.append("	tvdp8.TELEPHONE TELEPHONE8,\n");
		sql.append("	IFNULL(tvdp8.EMAIL,'') EMAIL8,\n");
		sql.append("	IFNULL(tc8_approve.CODE_DESC,'') APPROVE8,\n");
		sql.append("  IFNULL(tvdp8.BANK_ACCOUNT,'') BANK_ACCOUNT8,\n");
		sql.append("	  IFNULL(tvdp8.BANK_ADDRESS,'') BANK_ADDRESS8,\n");
    //客户关系经理
		sql.append("   IFNULL(tc6_post_type.CODE_DESC,'') POST_TYPE9,\n");
		sql.append("		IFNULL(tvdp6.NAME,'') NAME9,\n");
		sql.append("		IFNULL(tc6_gender.CODE_DESC,'') GENDER9,\n");
		sql.append("		tvdp6.MOBILE  MOBILE9,\n");
		sql.append("	tvdp6.TELEPHONE TELEPHONE9,\n");
		sql.append("	IFNULL(tvdp6.EMAIL,'') EMAIL9,\n");
		sql.append("	IFNULL(tc6_approve.CODE_DESC,'') APPROVE9,\n");
		sql.append("	  IFNULL(tvdp6.BANK_ACCOUNT,'') BANK_ACCOUNT9,\n");
		sql.append("	  IFNULL(tvdp6.BANK_ADDRESS,'') BANK_ADDRESS9\n");
		sql.append("	  ,CASE IFNULL(TD.IS_EC,0) WHEN '10041001' THEN '是' WHEN '10041002' THEN '否' ELSE '' END AS IS_EC,	\n");
		sql.append("	TD.CC_CODE AS CC_CODE,");
		sql.append("	TD.COMPANY_PHONE AS COMPANY_PHONE,");
		sql.append("	TD.OPEN_TIME AS OPEN_TIME,");
		sql.append("	TD.OPEN_RANGE AS OPEN_RANGE");
		if("dealer".equals(CommonUtils.checkNull(queryParams.get("table")))){			
			sql.append("	from  TM_DEALER     TD\n");
		}else{
			sql.append("	from  TM_DEALER_ED     TD\n");
		}
		sql.append(" 		left join TM_COMPANY   TC on TD.COMPANY_ID = TC.COMPANY_ID\n");
		sql.append("    left join TM_DEALER_ORG_RELATION tdor on TD.DEALER_ID=tdor.DEALER_ID\n");
		sql.append("   left join TM_ORG  tor on tor.ORG_ID=tdor.ORG_ID\n");
		sql.append("    left join TM_ORG  tor2 on tor.PARENT_ORG_ID=tor2.ORG_ID\n");
		sql.append("    left join TM_REGION_DCS tr_pro on tr_pro.REGION_CODE = TC.PROVINCE_ID\n");
		sql.append("   left join TM_REGION_DCS tr_city on tr_city.REGION_CODE = TC.CITY_ID\n");
    sql.append("    left join TT_COMPANY_DETAIL tcd on TC.COMPANY_ID=tcd.COMPANY_ID\n");
    sql.append("   left join TC_CODE_DCS tcd_range on tcd_range.CODE_ID=TD.RANGES\n");
    sql.append("   left join TC_CODE_DCS tc_clevel on tc_clevel.CODE_ID=tcd.COMPANY_LEVEL\n");
    sql.append("    left join TC_CODE_DCS tc_audit_status on tc_audit_status.CODE_ID=td.DEALER_AUDIT_STATUS\n");
    sql.append("    left join TM_VS_DEALER_PERSONNEL tvdp1 on tvdp1.DEALER_ID=td.DEALER_ID and tvdp1.POST_TYPE=21011001\n");
    sql.append("    left join TC_CODE_DCS tc1_post_type on tc1_post_type.CODE_ID=tvdp1.POST_TYPE\n");
    sql.append("    left join TC_CODE_DCS tc1_gender on tc1_post_type.CODE_ID=tvdp1.GENDER\n");
    sql.append("    left join TC_CODE_DCS tc1_approve on tc1_approve.CODE_ID=tvdp1.APPROVE\n");
    sql.append("    left join TM_VS_DEALER_PERSONNEL tvdp3 on tvdp3.DEALER_ID=td.DEALER_ID and tvdp3.POST_TYPE=21011003\n");
    sql.append("    left join TC_CODE_DCS tc3_post_type on tc3_post_type.CODE_ID=tvdp3.POST_TYPE\n");
    sql.append("   left join TC_CODE_DCS tc3_gender on tc3_post_type.CODE_ID=tvdp3.GENDER\n");
    sql.append("   left join TC_CODE_DCS tc3_approve on tc3_approve.CODE_ID=tvdp3.APPROVE\n");
    sql.append("    left join TM_VS_DEALER_PERSONNEL tvdp2 on tvdp2.DEALER_ID=td.DEALER_ID and tvdp2.POST_TYPE=21011002\n");
    sql.append("   left join TC_CODE_DCS tc2_post_type on tc2_post_type.CODE_ID=tvdp2.POST_TYPE\n");
    sql.append("   left join TC_CODE_DCS tc2_gender on tc2_post_type.CODE_ID=tvdp2.GENDER\n");
    sql.append("   left join TC_CODE_DCS tc2_approve on tc2_approve.CODE_ID=tvdp2.APPROVE\n");
    sql.append("   left join TM_VS_DEALER_PERSONNEL tvdp4 on tvdp4.DEALER_ID=td.DEALER_ID and tvdp4.POST_TYPE=21011004\n");
    sql.append("   left join TC_CODE_DCS tc4_post_type on tc4_post_type.CODE_ID=tvdp4.POST_TYPE\n");
    sql.append("   left join TC_CODE_DCS tc4_gender on tc4_post_type.CODE_ID=tvdp4.GENDER\n");
    sql.append("    left join TC_CODE_DCS tc4_approve on tc4_approve.CODE_ID=tvdp4.APPROVE\n");
    sql.append("    left join TM_VS_DEALER_PERSONNEL tvdp5 on tvdp5.DEALER_ID=td.DEALER_ID and tvdp5.POST_TYPE=21011005\n");
    sql.append("    left join TC_CODE_DCS tc5_post_type on tc5_post_type.CODE_ID=tvdp5.POST_TYPE\n");
    sql.append("    left join TC_CODE_DCS tc5_gender on tc5_post_type.CODE_ID=tvdp5.GENDER\n");
    sql.append("    left join TC_CODE_DCS tc5_approve on tc5_approve.CODE_ID=tvdp5.APPROVE\n");
    sql.append("    left join TM_VS_DEALER_PERSONNEL tvdp7 on tvdp7.DEALER_ID=td.DEALER_ID and tvdp7.POST_TYPE=21011007\n");
    sql.append("   left join TC_CODE_DCS tc7_post_type on tc7_post_type.CODE_ID=tvdp7.POST_TYPE\n");
    sql.append("   left join TC_CODE_DCS tc7_gender on tc7_post_type.CODE_ID=tvdp7.GENDER\n");
    sql.append("    left join TC_CODE_DCS tc7_approve on tc7_approve.CODE_ID=tvdp7.APPROVE\n");
    sql.append("   left join TM_VS_DEALER_PERSONNEL tvdp8 on tvdp8.DEALER_ID=td.DEALER_ID and tvdp8.POST_TYPE=21011008 \n");
    sql.append("   left join TC_CODE_DCS tc8_post_type on tc8_post_type.CODE_ID=tvdp8.POST_TYPE \n");
    sql.append("   left join TC_CODE_DCS tc8_gender on tc8_post_type.CODE_ID=tvdp8.GENDER\n");
    sql.append("   left join TC_CODE_DCS tc8_approve on tc8_approve.CODE_ID=tvdp8.APPROVE\n");
    sql.append("   left join TM_VS_DEALER_PERSONNEL tvdp6 on tvdp6.DEALER_ID=td.DEALER_ID and tvdp6.POST_TYPE=21011006\n");
    sql.append("   left join TC_CODE_DCS tc6_post_type on tc6_post_type.CODE_ID=tvdp6.POST_TYPE\n");
    sql.append("   left join TC_CODE_DCS tc6_gender on tc6_post_type.CODE_ID=tvdp6.GENDER\n");
    sql.append("   left join TC_CODE_DCS tc6_approve on tc6_approve.CODE_ID=tvdp6.APPROVE\n");
    sql.append("   left join TM_DEALER_GROUP tdg on TD.DEALER_GROUP_ID = tdg.GROUP_ID");
    sql.append("	where   1=1");
	
    if(!StringUtils.isNullOrEmpty(queryParams.get("provinceId"))){
		sql.append(" AND TD.PROVINCE_ID = ?");
		params.add(queryParams.get("provinceId"));
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
		sql.append(" AND TD.DEALER_CODE LIKE ?");
		params.add("%"+queryParams.get("dealerCode")+"%");
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
		sql.append(" AND TD.DEALER_SHORTNAME LIKE ?");
		params.add("%"+queryParams.get("dealerShortname")+"%");
	}
	

	if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){
		sql.append(" AND TD.STATUS = ?");
		params.add(queryParams.get("status"));
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("type"))){
		sql.append(" AND TD.DEALER_TYPE = ?");
		params.add(queryParams.get("type"));
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))){
		sql.append(" AND TC.COMPANY_NAME LIKE ?");
		params.add("%"+queryParams.get("dealerName")+"%");
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("sex"))){
		sql.append(" AND TD.IS_EC = ?");
		params.add(queryParams.get("sex"));
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("auditStatus"))){
		sql.append(" AND TD.DEALER_AUDIT_STATUS = ?");
		params.add(queryParams.get("auditStatus"));
	}
	if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
		sql.append("      AND TOR.ORG_ID= ?");
		params.add(queryParams.get("orgId"));
	}
	sql.append("      ORDER BY TD.DEALER_CODE ");
	
		return sql.toString();
	}

	/**
	 * 查询车系信息
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> getGroupList() {
		String sql = "select distinct GROUP_CODE, GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_LEVEL = 2";
		List<Map> groupList = OemDAOUtil.findAll(sql, null);
		return groupList;
	}

	/**
	 * 查询经销商基本信息
	 * @return
	 */
	public PageInfoDto getDlrDealerInfo(Map<String, String> queryParams,LoginInfoDto loginUser) {
		
		String sql = getDlrDealerInfosSQL(queryParams,loginUser);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, null);
		return pageInfoDto;
	}

	/**
	 * 查询经销商基本信息SQL
	 * @return
	 */
	private String  getDlrDealerInfosSQL(Map<String, String> queryParams,LoginInfoDto loginUser) {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TDS.*, TG.ORG_NAME, TG.ORG_ID\n");
		sql.append("   from ( SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_TYPE,TC.COMPANY_SHORTNAME,TD.STATUS,TD.MARKETING,TD.DEALER_STATUS,TD.DEALER_AUDIT_STATUS,TD.IS_EC\n");
		sql.append("             FROM TM_DEALER_ED TD,TM_COMPANY TC\n");
		sql.append("   			WHERE TD.COMPANY_ID=TC.COMPANY_ID\n");

//		if(!StringUtils.isNullOrEmpty(loginUser.getDealerId())){
			sql.append("    	and TD.DEALER_ID= "+loginUser.getDealerId()+" \n");
//		}
			System.out.println(loginUser.getDealerId());
		sql.append("    		   )  tds \n");
		sql.append("   LEFT JOIN (SELECT tdor.DEALER_ID,t.ORG_NAME,t.ORG_ID\n");
		sql.append("  			    FROM  TM_DEALER_ORG_RELATION TDOR, TM_ORG T\n");
		sql.append("   			   WHERE tdor.ORG_ID=t.ORG_ID  )   tg\n");
		sql.append("   ON Tds.dealer_id=tg.DEALER_ID\n");
		
		return sql.toString();
		
	}


	/**
	 * 查询业务范围到车系
	 * @param dealerId
	 * @return
	 */
	public List<Map> getDealerBussInfo() {
		
		String sql = getDealerBussInfoSQL();
		
		List<Map> pageInfoDto = OemDAOUtil.findAll(sql, null);
		return pageInfoDto;
	}
	
	/**
	 * 查询业务范围到车系SQL
	 * @return
	 */
	private String getDealerBussInfoSQL() {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select tt.*,case \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" AND BCODE = 'JEEP' then '1' \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" AND BCODE = 'FIAT' then '2' \n");
		sql.append("                 when tt.GTYPE = "+OemDictCodeConstants.GROUP_TYPE_IMPORT+" then '3' \n");
		sql.append("                 else '4' \n");
		sql.append("             end as GSTYPE \n");
		sql.append(" from (select t1.GROUP_ID as  GROUPID ,t1.GROUP_TYPE as GTYPE, t1.GROUP_CODE as SCODE,t1.GROUP_NAME as SNAME ,t2.GROUP_NAME as BNAME,t2.GROUP_CODE as BCODE , \n");
		sql.append(" 	(select code_desc  from tc_code_dcs where code_id like t1.GROUP_TYPE) as GROUP_TYPE  \n");
		sql.append(" 	from TM_VHCL_MATERIAL_GROUP  t1 left join TM_VHCL_MATERIAL_GROUP t2 on t1.PARENT_GROUP_ID = t2.GROUP_ID \n");
		sql.append(" 	where t1.GROUP_LEVEL = 2 and t1.status = "+OemDictCodeConstants.STATUS_ENABLE+" and t2.STATUS =  "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append(" ) tt \n");
		sql.append(" order by tt.GTYPE,tt.BNAME,tt.SNAME \n");
		return sql.toString();
		
	}

	public Map<String, Object> getOrgCodesInfo(Long dealerId) {
		
		String sql = "SELECT TDOR.ORG_ID,TMO.ORG_CODE,TMO.ORG_NAME FROM TM_DEALER_ORG_RELATION TDOR,TM_ORG TMO WHERE TMO.ORG_ID=TDOR.ORG_ID \n"
				+ " AND TDOR.DEALER_ID='"+dealerId+"' \n";
		List<Map> List = OemDAOUtil.findAll(sql, null);
		if(List!=null && List.size()!=0){
			return List.get(0);
			
		}
		return null;
	}
	
	/**
	 * 经销商基本信息审核
	 * @param dealerId
	 * @return
	 */
	public PageInfoDto getDealerAuditInfo(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerAuditInfoSQL(queryParams,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}


	private String getDealerAuditInfoSQL(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TDS.*, TG.ORG_NAME, TG.ORG_ID \n");
		sql.append(" from (SELECT TD.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME,TD.DEALER_TYPE,TC.COMPANY_SHORTNAME,TD.RANGES,TD.CC_CODE,TD.COMPANY_PHONE,TD.OPEN_TIME,TD.OPEN_RANGE,TD.STATUS,TD.DEALER_STATUS,TD.DEALER_AUDIT_STATUS,IS_EC");
		sql.append(" FROM TM_DEALER_ED TD,TM_COMPANY TC\n");
		sql.append(" WHERE TD.COMPANY_ID=TC.COMPANY_ID \n");
		
		//增加车厂公司过滤
		sql.append("     AND TC.OEM_COMPANY_ID="+loginUser.getCompanyId()+"\n");

		if(!StringUtils.isNullOrEmpty(queryParams.get("provinceId"))){
			sql.append(" AND TD.PROVINCE_ID IN (?)");
			params.add(queryParams.get("provinceId"));
}
		if(!StringUtils.isNullOrEmpty(queryParams.get("companyId"))){
			sql.append(" AND TC.COMPANY_ID = ?");
			params.add(queryParams.get("companyId"));
		}

		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
					sql.append(" AND TD.DEALER_CODE LIKE ?");
					params.add("%"+queryParams.get("dealerCode")+"%");
		}

		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
					sql.append(" AND TD.DEALER_SHORTNAME LIKE ?");
					params.add("%"+queryParams.get("dealerShortname")+"%");
		}
	
		if(!StringUtils.isNullOrEmpty(queryParams.get("type"))){
					sql.append(" AND TD.DEALER_TYPE = ?");
					params.add(queryParams.get("type"));
		}
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("isEc"))){
					sql.append(" AND TD.IS_EC = ?");
					params.add(queryParams.get("isEc"));
		}
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId")))
		{
			sql.append(" )tds ,( \n");
			sql.append("SELECT tdor.DEALER_ID,T.ORG_NAME,T.ORG_ID FROM  TM_DEALER_ORG_RELATION TDOR, TM_ORG T WHERE tdor.ORG_ID=T.ORG_ID ");
			sql.append("and T.ORG_ID= ? \n") ;
			params.add(queryParams.get("orgId"));
			sql.append(") tg WHERE Tds.dealer_id=tg.DEALER_ID \n");	
			sql.append(" and tds.DEALER_AUDIT_STATUS="+OemDictCodeConstants.DEALER_AUDIT_STATE_02+"\n");
		}else
		{
			sql.append(" )tds     LEFT JOIN ( \n");
			sql.append("SELECT tdor.DEALER_ID,t.ORG_NAME,t.ORG_ID FROM  TM_DEALER_ORG_RELATION TDOR, TM_ORG T WHERE tdor.ORG_ID=t.ORG_ID \n");
			sql.append(") tg ON Tds.dealer_id=tg.DEALER_ID \n");	
			sql.append(" where tds.DEALER_AUDIT_STATUS="+OemDictCodeConstants.DEALER_AUDIT_STATE_02+"\n");
		}		
	return sql.toString();
	}

	/**
	 * 查询小区
	 * @param queryParams
	 * @return
	 */
	public List<Map> getSmallOrgInfos(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ORG2.ORG_ID SMALL_ORG,ORG2.ORG_NAME SMALL_ORG_NAME,ORG2.ORG_CODE SMALL_ORG_CODE FROM TM_ORG ORG1 ,TM_ORG ORG2  ");
		sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
		sql.append(" AND ORG2.DUTY_TYPE = 10431004 AND ORG2.ORG_LEVEL = 3 ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND ORG2.BUSS_TYPE = '12351002'  ");
		}else{
			sql.append(" AND ORG2.BUSS_TYPE = '12351001'  ");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgName"))){
			sql.append(" AND ORG2.ORG_NAME = ?  ");
			params.add(queryParams.get("orgName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("orgId"))){
			sql.append(" AND ORG2.orgId = ?  ");
			params.add(queryParams.get("orgId"));
		}
		return  OemDAOUtil.findAll(sql.toString(), params);
	}

	/**
	 * 查询经销商集团信息
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto DealerGroupInfos(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  GROUP_ID,GROUP_CODE,GROUP_SHOTNAME FROM TM_DEALER_GROUP");
		sql.append(" WHERE  1=1  ");
	
		if(!StringUtils.isNullOrEmpty(queryParams.get("groupCode"))){
			sql.append(" AND GROUP_CODE = ?  ");
			params.add(queryParams.get("groupCode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("groupShotname"))){
			sql.append(" AND GROUP_SHOTNAME LIKE ?  ");
			params.add("%"+queryParams.get("groupShotname")+"%");
		}
		return  OemDAOUtil.pageQuery(sql.toString(), params);
	}


	/**
	 * 关键岗位人员信息查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto dealerKeyPersonOTDs(Long dealerId) {
		String sql = getdealerKeyPersonOTDsSql(dealerId);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, null);
		return pageInfoDto;
	}


	private String getdealerKeyPersonOTDsSql(Long dealerId) {
		LoginInfoDto loginUser = new LoginInfoDto();
		StringBuffer sql =new StringBuffer();
		sql.append("  SELECT TS.NAME AS STNAME, \n ");
		sql.append("         TSIC.NAME, \n ");
		sql.append("         TSIC.LINK_TEL, \n ");
		sql.append("         TSIC.EMAIL, \n ");
		sql.append("         TSIC.ID_NO,\n ");
		sql.append("         TC.CODE_DESC GENDER \n ");
		sql.append("FROM TM_STATION_INFOS_CHANGE TSIC, \n ");
		sql.append("     TM_STATION              TS, \n ");
		sql.append("     TC_CODE_DCS             TC, \n ");
		sql.append("     TM_DEALER               TD \n ");
		sql.append(" WHERE TSIC.STATION_ID = TS.STATION_ID \n ");
		sql.append("     AND TSIC.GENDER = TC.CODE_ID \n ");
		sql.append("     AND TSIC.DEALER_ID = TD.DEALER_ID \n ");
		sql.append("     AND TS.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " \n ");
		sql.append("     AND TSIC.IS_CHANGE = " +new OemDictCodeConstants().KEY_CHANGE_TYPE_CURRENT + " \n ");
		sql.append("     AND TSIC.INFOS_STATUS IN (" + OemDictCodeConstants.STATUS_IN_THE_POST + ", " + OemDictCodeConstants.STATUS_BEFOREIN_THE_POST + ") \n ");
		sql.append("     AND TSIC.AUDIT_STATUS IN (" + OemDictCodeConstants.KEY_STATUS_APPROVAL_03 + ") \n ");
		if(!StringUtils.isNullOrEmpty(loginUser.getCompanyId())){
			sql.append("     AND TD.OEM_COMPANY_ID='"+loginUser.getCompanyId() +"'\n ");
		}
	
		if(!StringUtils.isNullOrEmpty(dealerId)){
		   sql.append("AND TD.DEALER_ID like'%"+dealerId+"%' \n ");
		}
		return sql.toString();
	}


	/**
	 * 查询展厅信息
	 */
	public PageInfoDto dealerShowroom(Long dealerId) {
		
		String sql = getdealerShowroomSql(dealerId);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, null);
		return pageInfoDto;
	}

	/**
	 * 查询展厅信息SQL
	 */
	private String getdealerShowroomSql(Long dealerId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvds.ID, \n");
		sql.append(" tvds.DEALER_ID, \n");
		sql.append(" (case when tvds.ADDRESS_F=1 THEN  ('<font color=red >'|| tvds.ADDRESS ||'</font>') \n");
		sql.append("				   ELSE tvds.ADDRESS	\n");
		sql.append("				END) ADDRESS ,\n");
		sql.append("    tvds.ZIP_CODE,      \n");
		sql.append("    tvds.OPEN_TIME,     \n");
		sql.append("    tvds.RX_PHONE,      \n");
		sql.append("   tvds.OFFICE_PHONE,   \n");
		sql.append("   tvds.FAX,            \n");
		sql.append("   tvds.LINK_MAN,       \n");
		sql.append("   tvds.LINK_PHONE,     \n");
		sql.append("   tvds.BRAND,          \n");
		sql.append("   tvds.BUNINESS_TIME,  \n");
		sql.append("   tvds.SHOWROOM_AREA,  \n");
		sql.append("   tvds.SHOWROOM_WIDTH,  \n");
		sql.append("   tvds.STOPPING_NUM,  \n");
		sql.append("   tvds.SHOWCAR_NUM,  \n");
		sql.append("   tvds.STATUS,\n");
		sql.append("   tvds.LAND_BUY_OF_RENT,\n");
		sql.append("  tvds.LAND_PRO,        \n");
		sql.append("   tvds.WEIXIU_NUM,     \n");
		sql.append("   tvds.JIXIU_NUM,      \n");
		sql.append("   tvds.BANPEN_NUM,     \n");
		sql.append("   tvds.YUJIAN_NUM,     \n");
		sql.append("   tvds.YULIU_NUM,      \n");
		sql.append("   tvds.AFTERSALE_AREA, \n");
		sql.append("   tvds.DANGER_AREA     \n");
		sql.append("   from TM_VS_DEALER_SHOWROOM tvds where tvds.DEALER_ID="+dealerId);
		sql.append("   and tvds.STATUS="+OemDictCodeConstants.STATUS_ENABLE+"");
		return sql.toString();
	}

	/**
	 * 查询经销商公司店面照片
	 * @param ywzj
	 * @return
	 */
	public PageInfoDto companyPhoto(String ywzj) {
		
		String sql = "select  FF.ID,FF.FJID,FF.FILENAME,FF.YWZJ,FF.FILEURL  from   FS_FILEUPLOAD   FF where FF.YWZJ="+ywzj+" ";
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, null);
		return pageInfoDto;
	}
	/**
	 * 查询小区
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getSmallOrgInfo(Map<String, String> queryParams) {
		
		
			List<Object> params = new ArrayList<Object>();
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT  ORG2.ORG_ID SMALL_ORG,ORG2.ORG_NAME SMALL_ORG_NAME,ORG2.ORG_CODE SMALL_ORG_CODE FROM TM_ORG ORG1 ,TM_ORG ORG2  ");
			sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
			sql.append(" AND ORG2.DUTY_TYPE = 10431004 AND ORG2.ORG_LEVEL = 3 ");
			if("1".equals(queryParams.get("type"))){
				sql.append(" AND ORG2.BUSS_TYPE = '12351001'  ");
			}else if("2".equals(queryParams.get("type"))){
				sql.append(" AND ORG2.BUSS_TYPE = '12351002'  ");
			}else{
				
			}
			if(!StringUtils.isNullOrEmpty(queryParams.get("orgName"))){
				sql.append(" AND ORG2.ORG_NAME like ?  ");
				params.add("%"+queryParams.get("orgName")+"%");
			}
			if(!StringUtils.isNullOrEmpty(queryParams.get("orgCode"))){
				sql.append(" AND ORG2.ORG_CODE like ?  ");
				params.add("%"+queryParams.get("orgCode")+"%");
			}
			return  OemDAOUtil.pageQuery(sql.toString(), params);
	}

	/**
	 * 导入临时表校验
	 * @return
	 */
	public List<Map> checkDealerOrGroupIsNull() {
		List<Object> queryParam = new ArrayList<>();
		String sql = "SELECT ROW_NO FROM TMP_DEALER_BUSS WHERE DEALER_CODE IS NULL OR DEALER_CODE = '' OR GROUP_CODE IS NULL OR GROUP_CODE = '' ORDER BY ROW_NO";
		return OemDAOUtil.findAll(sql, queryParam);
	}

	/**
	 * 导入临时表校验
	 * @return
	 */
	public List<Map> checkDealer() {
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TMP_DEALER_BUSS T WHERE NOT EXISTS (\n");
		sql.append("    SELECT 1 FROM TM_DEALER TT WHERE TT.DEALER_CODE = T.DEALER_CODE AND TT.DEALER_TYPE = '"+OemDictCodeConstants.DEALER_TYPE_DVS+"' \n");
		sql.append(" ) \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}
	
	public List<Map> checkGroup(){
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM TMP_DEALER_BUSS T WHERE NOT EXISTS (\n");
		sql.append("    SELECT 1 FROM (SELECT DISTINCT GROUP_CODE, GROUP_NAME FROM TM_VHCL_MATERIAL_GROUP WHERE GROUP_LEVEL = 2) TT WHERE TT.GROUP_CODE = T.GROUP_CODE \n");
		sql.append(" ) \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public void importSave() {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into TM_DEALER_BUSS ( DEALER_ID,GROUP_ID,CREATE_DATE,CREATE_BY)\n");
		sql.append("select td.DEALER_ID,tvmg.GROUP_ID,NOW(),"+loginUser.getUserId()+" from  \n");
		sql.append("TMP_DEALER_BUSS tmp , TM_DEALER td , TM_VHCL_MATERIAL_GROUP tvmg \n");
		sql.append("where tmp.DEALER_CODE = td.DEALER_CODE and tmp.GROUP_CODE = tvmg.GROUP_CODE and tvmg.GROUP_LEVEL=2 \n");
		sql.append("and not exists (select 1 from TM_DEALER_BUSS tdb where td.DEALER_ID = tdb.DEALER_ID and tvmg.GROUP_ID = tdb.GROUP_ID )\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}

	public PageInfoDto queryCom(Map<String, String> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> queryParam = new ArrayList<>();
		String companyCode = params.get("companyCode");
		String companyName = params.get("companyName");
		String companyShortName = params.get("companyShortName");
		StringBuilder sql = new StringBuilder();
		sql.append("select t.COMPANY_ID, t.COMPANY_CODE, t.COMPANY_NAME,t.STATUS, t.COMPANY_SHORTNAME ,t.COMPANY_EN, t.PROVINCE_ID,t.CITY_ID,t.PHONE,t.FAX,t.ADDRESS,t.ZIP_CODE,t.CTCAI_CODE ,t.SWT_CODE ,t.ELINK_CODE,t.DC_CODE,t.LMS_CODE,t.JEC_CODE from TM_COMPANY t ");
		sql.append(" where  t.COMPANY_TYPE <> ");
		sql.append(OemDictCodeConstants.COMPANY_TYPE_OEM);
		sql.append(" and t.STATUS = ");
		sql.append(OemDictCodeConstants.STATUS_ENABLE);
		//开发时关闭
		sql.append(" and t.OEM_COMPANY_ID = "+loginUser.getCompanyId());
		sql.append(companyCode != null ? ("		and t.COMPANY_CODE like '%"
						+ companyCode + "%'") : "");
		sql.append(companyName != null ? ("		and t.COMPANY_NAME like '%"
						+ companyName + "%'") : "");
		sql.append(companyShortName != null ? ("	and t.COMPANY_SHORTNAME like '%"
				+ companyShortName + "%'") : "");
		System.out.println("SQL ================\n"+sql+"\n====================");
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> getAllOrg(String bussType) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> queryParam = new ArrayList<>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT TMO.ORG_ID,\n");
		sql.append("       TMO.ORG_CODE,\n");  
		sql.append("       TMO.ORG_NAME,\n");  
		sql.append("       TMO.ORG_DESC,\n");  
		sql.append("       TMO.TREE_CODE,\n");  
		sql.append("       TMO.ORG_LEVEL\n");  
		sql.append("  FROM TM_ORG TMO\n");  
		sql.append(" WHERE TMO.STATUS = "+OemDictCodeConstants.STATUS_ENABLE+"\n");
		sql.append(" AND TMO.ORG_TYPE = " + OemDictCodeConstants.ORG_TYPE_OEM);
		//sql.append(" AND TOBA.ORG_ID=TMO.ORG_ID");
		sql.append(" AND tmo.org_level=3");
		sql.append(" AND tmo.duty_type=10431004");
		sql.append(" AND TMO.COMPANY_ID = " + loginUser.getCompanyId());
		if(bussType!=null){
			sql.append("   AND TMO.BUSS_TYPE="+bussType+"\n");
		}
		sql.append(" ORDER  BY  TMO.ORG_ID \n");
		System.out.println("SQL ================\n"+sql+"\n====================");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public PageInfoDto doSearchCompanyPhoto7(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<>();
		String billId = param.get("id");
		if(StringUtils.isNullOrEmpty(billId)){
			billId = "-99999";		
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select  TF.FILE_UPLOAD_INFO_ID AS FILE_ID,TF.FILE_NAME AS FILENAME  FROM  TC_FILE_UPLOAD_INFO  TF where TF.BILL_ID='"+billId+"' ");
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> queryComAll(Map<String, String> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> queryParam = new ArrayList<>();
		String companyCode = params.get("companyCode");
		String companyName = params.get("companyName");
		String companyShortName = params.get("companyShortName");
		StringBuilder sql = new StringBuilder();
		sql.append("select t.COMPANY_ID, t.COMPANY_CODE, t.COMPANY_NAME,t.STATUS, t.COMPANY_SHORTNAME ,t.COMPANY_EN, t.PROVINCE_ID,t.CITY_ID,t.PHONE,t.FAX,t.ADDRESS,t.ZIP_CODE,t.CTCAI_CODE ,t.SWT_CODE ,t.ELINK_CODE,t.DC_CODE,t.LMS_CODE,t.JEC_CODE from TM_COMPANY t ");
		sql.append(" where  t.COMPANY_TYPE <> ");
		sql.append(OemDictCodeConstants.COMPANY_TYPE_OEM);
		sql.append(" and t.STATUS = ");
		sql.append(OemDictCodeConstants.STATUS_ENABLE);
		//开发时关闭
		sql.append(" and t.OEM_COMPANY_ID = "+loginUser.getCompanyId());
		sql.append(companyCode != null ? ("		and t.COMPANY_CODE like '%"
						+ companyCode + "%'") : "");
		sql.append(companyName != null ? ("		and t.COMPANY_NAME like '%"
						+ companyName + "%'") : "");
		sql.append(companyShortName != null ? ("	and t.COMPANY_SHORTNAME like '%"
				+ companyShortName + "%'") : "");
		System.out.println("SQL ================\n"+sql+"\n====================");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public void updateDealerEd(String dealerIds) {
		StringBuffer sql =new StringBuffer();
		sql.append("update TM_DEALER_ED set DEALER_AUDIT_STATUS="+OemDictCodeConstants.DEALER_AUDIT_STATE_03+",DEALER_AUDIT_DATE= NOW() ");
		sql.append(",DEALER_SHOORTNAME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",PROVINCE_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",CITY_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",ZIP_CODE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",PHONE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",LINK_MAN_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",LINK_MAN_MOBILE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",FAX_NO_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",EMAIL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",TAXES_NO_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",ERP_CODE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",BEGIN_BANK_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",BANK_CODE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",ADDRESS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",REMARK_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",TREE_CODE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",PRICE_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",OFFICE_PHONE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",FOUND_DATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",ACURA_CHAS_TYPE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",SWT_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",CLAIM_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );	
		sql.append(",PART_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",LMS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",MARKETING_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",DEALER_ORDER_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",DISCOUNT_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",RANGE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",CC_CODE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",OPEN_TIME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",OPEN_RANGE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",COMPANY_PHONE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",COMPANY_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",DEALER_GROUP_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",DEALER_NAME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",DEALER_SHORTNAME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS+"\n" );
		sql.append("where DEALER_ID in('"+dealerIds+"')");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
		
	}

	public void updateCompanyDetail(String dealerIds) {
		StringBuffer sql =new StringBuffer();
		sql.append("update TT_COMPANY_DETAIL  set LEGAL_REPRESENTATIVE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS);
		sql.append(",COMPANY_NO_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );		
		sql.append(",COMPANY_PHOTO_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",AUDIT_MATERIALS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",LICENSED_BRANS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",BUILD_STATUS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",COMPLETION_DATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",CONTRACT_NO_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",START_DATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",SHOWRROW_AREA_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",SHOWROOM_WIDTH_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",SHOW_CARS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",SERVICE_NUM_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",MACHINE_REPAIR_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",SHEET_SPRAY_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",PREFLIGHT_NUM_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",RESERVED_NUM_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",STOP_CAR_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",LAND_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",LAND_NATURE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",PARTS_ARES_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",DANGER_AREA_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",END_DATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",GROUP_NAME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",RATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",REGISTERED_CAPITAL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",COMPANY_INVERSTORS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",INVESTOPS_TEL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",INVESTORS_EMAIL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",BRANDS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",MAINTAINABILITY_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",BUSINESS_LICENSE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",ORGANIZATION_CHART_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",COPIES_OF_MANDA_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",TAX_CERTIFICATE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",FINANCIAL_STARTMENT_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",COMPANY_LEVEL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(" where COMPANY_ID in  (select COMPANY_ID from TM_DEALER_ED where  DEALER_ID in('"+dealerIds+"'))\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());		
	}

	public void updateDealerPersonal(String dealerIds) {
		StringBuffer sql =new StringBuffer();
		sql.append("update TM_VS_DEALER_PERSONNEL  set NAME_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS);
		sql.append(",GENDER_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );		
		sql.append(",PROVINCE_ID_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",MOBILE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",TELEPHONE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",EMAIL_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",BANK_ACCOUNT_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  );
		sql.append(",BANK_ADDRESS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS );
		sql.append(",POST_TYPE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(",APPROVE_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS  +"\n ");
		sql.append(" where DEALER_ID in('"+dealerIds+"') \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());	
		
	}

	public void updateDealerShowroom(String dealerIds) {
		StringBuffer sql =new StringBuffer();
		sql.append("update TM_VS_DEALER_SHOWROOM   set ADDRESS_F="+OemDictCodeConstants.NO_CHANGE_VERSIONS +"\n");
		sql.append(" where DEALER_ID in('"+dealerIds+"') \n");
		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());	
	}

	public List<Map> queryGroupInfo() {
		String sql = "select distinct GROUP_CODE, GROUP_NAME from TM_VHCL_MATERIAL_GROUP where GROUP_LEVEL = 2";
		return OemDAOUtil.downloadPageQuery(sql, null);
	}
}
