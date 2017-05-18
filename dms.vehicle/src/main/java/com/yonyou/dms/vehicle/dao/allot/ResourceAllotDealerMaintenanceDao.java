package com.yonyou.dms.vehicle.dao.allot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 资源分配经销商维护
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class ResourceAllotDealerMaintenanceDao extends OemBaseDAO {

	/**
	 * 资源分配经销商维护查询
	 */
	public PageInfoDto allotDealerMaintenanceQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		System.out.println(pageInfoDto);
		return pageInfoDto;
	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("  select * from ( ");
		sql.append(
				"select tp.DEALER_ID,tp.ID ,tp.DEALER_CODE,tp.DEALER_SHORTNAME,tp.COMPANY_NAME,tp.DEALER_TYPE,tp.BIG_AREA,tp.SMALL_AREA,tp.STATUS,	\n");
		sql.append(
				"	(case  WHEN tp.TJ_PORT_LEVEL is null THEN '未设置' else tp.TJ_PORT_LEVEL END) as TJ_PORT_LEVEL,	\n");
		sql.append(
				"	(case  WHEN tp.SH_PORT_LEVEL is null THEN '未设置' else tp.SH_PORT_LEVEL END) as SH_PORT_LEVEL	\n");
		sql.append("		from(	\n");
		sql.append(
				"			select tm.DEALER_ID, tm.DEALER_CODE,tm.DEALER_SHORTNAME,tm.COMPANY_NAME,tm.DEALER_TYPE,tm.BIG_AREA,tm.SMALL_AREA,tm.STATUS,	\n");
		sql.append("				max((case  WHEN tdm.VPC_PORT = " + OemDictCodeConstants.VPC_PORT_01
				+ " THEN tdm.PORT_LEVEL END)) as TJ_PORT_LEVEL,tdm.ID,	\n");
		sql.append("				max((case  WHEN tdm.VPC_PORT = " + OemDictCodeConstants.VPC_PORT_02
				+ " THEN tdm.PORT_LEVEL END)) as SH_PORT_LEVEL	\n");
		sql.append(
				"					from (select td.DEALER_ID, td.DEALER_CODE,td.DEALER_SHORTNAME,tc.COMPANY_NAME,TD.DEALER_TYPE,TOR.ORG_DESC BIG_AREA,TOR1.ORG_DESC  SMALL_AREA,TD.STATUS	\n");
		sql.append(
				"							from TM_ORG tor, TM_ORG tor1,TM_DEALER td,TM_DEALER_ORG_RELATION tdor,TM_COMPANY tc	\n");
		sql.append("								where tor1.PARENT_ORG_ID = TOR.ORG_ID	\n");
		sql.append("									AND tor1.ORG_ID=tdor.ORG_ID	\n");
		sql.append("									AND td.DEALER_ID=tdor.DEALER_ID	\n");
		sql.append("									AND tor1.ORG_LEVEL = 3	\n");
		sql.append("									AND TOR.ORG_LEVEL =2	\n");
		sql.append("									AND TOR.ORG_ID=tor1.PARENT_ORG_ID	\n");
		// sql.append(" AND tor.BUSS_TYPE="+Constant.ORG_BUSS_TYPE_01 +"\n");
		sql.append("									AND tc.COMPANY_ID = td.COMPANY_ID	\n");
		sql.append("									AND td.OEM_COMPANY_ID=" + loginInfo.getCompanyId() + "\n");
		sql.append(
				"									AND td.DEALER_TYPE=" + OemDictCodeConstants.DEALER_TYPE_DVS + "\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append("AND TOR.ORG_ID like '%" + queryParam.get("bigOrgName") + "%'  \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append("AND TOR1.ORG_ID like '%" + queryParam.get("smallOrgName") + "%'  \n");
		}
		sql.append(
				"					)tm left join TM_DEALER_MAINTENANCE  tdm on tm.DEALER_ID = tdm.DEALER_ID	\n");
		sql.append(
				"					group by tm.DEALER_ID, tm.DEALER_CODE,tm.DEALER_SHORTNAME,tm.COMPANY_NAME,tm.DEALER_TYPE,tm.BIG_AREA,tm.SMALL_AREA,tm.STATUS	\n");
		sql.append("			)tp where 1=1	\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			sql.append("AND tp.STATUS =" + queryParam.get("status") + "  \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			String dealerCodes = queryParam.get("dealerCode");
			String s = new String();
			if (dealerCodes.indexOf(",") > 0) {
				String[] str = dealerCodes.split(",");
				for (int i = 0; i < str.length; i++) {
					s += "'" + str[i] + "'";
					if (i < str.length - 1)
						s += ",";
				}
			} else {
				s = "'" + dealerCodes + "'";
			}
			sql.append("AND tp.DEALER_CODE in(" + s + ")  \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerShortNmae"))) {
			sql.append("AND tp.DEALER_SHORTNAME like'%" + queryParam.get("dealerShortNmae") + "%'  \n");
		}

		sql.append("   ) k");
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 下载
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> download(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> download = OemDAOUtil.downloadPageQuery(sql, null);
		return download;
	}

	/**
	 * 大区总维护下拉框查询 查询大区总
	 */
	public List<Map> queryTotalOrg(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();

		sql.append(
				"select distinct tob.TOTAL_ORG_NAME from TC_ORG_BIG tob where 1=1 and tob.TOTAL_ORG_NAME is not null \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> queryOrgName(Map<String, String> queryParam) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("  select tmo.ORG_ID,tmo.ORG_CODE,tmo.ORG_NAME \n");
		sql.append("    from TM_ORG tmo\n");
		sql.append("   where tmo.DUTY_TYPE=" + OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "\n");
		sql.append("     and tmo.BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01);
		sql.append("     and tmo.STATUS=" + OemDictCodeConstants.STATUS_ENABLE);
		sql.append("     and tmo.COMPANY_ID=" + loginInfo.getCompanyId());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	public List<Map> queryTcUserName(Map<String, String> queryParam) {
		String region = CommonUtils.checkNull(queryParam.get("tcUserName"));
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select TU.USER_ID,TU.NAME from TC_USER TU, TC_POSE TP, TR_USER_POSE TUP  \n");
		sql.append(" where TU.USER_ID = TUP.USER_ID AND TP.POSE_ID = TUP.POSE_ID \n");
		sql.append(" and TP.POSE_CODE='SALES_MNG' \n");
		if (region != null && !region.equals("")) {
			sql.append(" and TU.NAME like '%" + region + "%' \n");
		}
		sql.append("     and TU.COMPANY_ID=" + loginInfo.getCompanyId());
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> queryAll(Map<String, String> queryParam) {
		String orgName = CommonUtils.checkNull(queryParam.get("orgName"));
		String totalOrgName = CommonUtils.checkNull(queryParam.get("totalOrgName"));
		StringBuffer sql = new StringBuffer();
		sql.append("select \n");
		sql.append("if(@pdept=USER_ID,@rank:=@rank+1,@rank:=1) as rank,\n");
		sql.append("@pdept:=USER_ID,@rownum:=@rownum+1,b.*  from\n");
		sql.append("(select \n");
		sql.append(
				"	   tob.ORG_BIG_ID,tcu.USER_ID,tcu.NAME,tob.TOTAL_ORG_NAME, tob.TOTAL_ORG_CODE,tmo.ORG_NAME,tmo.ORG_ID \n");
		sql.append("  from TC_ORG_BIG tob \n");
		sql.append("  inner join TC_USER tcu on tob.USER_ID=tcu.USER_ID \n");
		sql.append("  inner join TM_ORG tmo on tob.ORG_ID=tmo.ORG_ID  \n");
		sql.append(" where 1=1 \n");
		if (totalOrgName != null && !totalOrgName.equals("")) {
			sql.append(" and tob.TOTAL_ORG_NAME = '" + totalOrgName + "' \n");
		}
		if (orgName != null && !orgName.equals("")) {
			sql.append(" and tmo.ORG_ID = '" + orgName + "' \n");
		}
		sql.append("		 order by tob.USER_ID  ,tob.ORG_ID )b,\n");
		sql.append("(select @rownum :=0 , @pdept := null ,@rank:=0) a\n");

		// LoginInfoDto loginInfo =
		// ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		// sql.append(" select tmo.ORG_ID,tmo.ORG_CODE,tmo.ORG_NAME \n");
		// sql.append(" from TM_ORG tmo\n");
		// sql.append(" where tmo.DUTY_TYPE=" +
		// OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "\n");
		// sql.append(" and tmo.BUSS_TYPE=" +
		// OemDictCodeConstants.ORG_BUSS_TYPE_01);
		// sql.append(" and tmo.STATUS=" + OemDictCodeConstants.STATUS_ENABLE);
		// sql.append(" and tmo.COMPANY_ID=" + loginInfo.getCompanyId());

		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> findById(Long userId) {

		// 查大区总监名称
		String userSql = "select * from TC_USER WHERE USER_ID=" + userId;

		List<Map> map = OemDAOUtil.findAll(userSql, null);

		return map;
	}

	public List<Map> queryAllById(Long id) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select org.ORG_ID,org.ORG_NAME,case when IFNULL(tob.ORG_BIG_ID,0)=0 then 10041002 else 10041001 end IS_CHECK\n");
		sql.append("	    from TM_ORG org \n");
		sql.append("	left join TC_ORG_BIG tob on org.ORG_ID = tob.ORG_ID and tob.USER_ID = " + id + "\n");
		sql.append("  where  org.DUTY_TYPE=" + OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "\n");
		sql.append(" and org.BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01 + "\n");
		sql.append(" and org.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + " \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> findById1(Long userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select org.ORG_ID,org.ORG_NAME,case when IFNULL(tob.ORG_BIG_ID,0)=0 then 10041002 else 10041001 end IS_CHECK\n");
		sql.append("	    from TM_ORG org \n");
		sql.append("	INNER JOIN TC_ORG_BIG tob on org.ORG_ID = tob.ORG_ID and tob.USER_ID = " + userId + "\n");
		sql.append("  where  org.DUTY_TYPE=" + OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "\n");
		sql.append(" and org.BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01 + "\n");
		sql.append(" and org.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + " \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public Map finndMaxOrderNum() {
		String sql = "select ( case  when max(ORDER_NUM)=0 then  ORDER_NUM+1 ELSE ORDER_NUM end) ORDER_NUM from TM_DEALER_WHS where type =2 ";
		Map list = OemDAOUtil.findFirst(sql, null);

		return list;
	}

	public List<Map> queryAddInt() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

		StringBuffer strBuff = new StringBuffer();
		strBuff.append("  select org.ORG_ID,org.ORG_NAME \n");
		strBuff.append("    from TM_ORG org\n");
		strBuff.append("   where org.DUTY_TYPE=" + OemDictCodeConstants.DUTY_TYPE_LARGEREGION + "\n");
		strBuff.append("     and org.BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01);
		strBuff.append("     and org.STATUS=" + OemDictCodeConstants.STATUS_ENABLE);
		strBuff.append("     and org.COMPANY_ID=" + loginInfo.getCompanyId());
		return OemDAOUtil.findAll(strBuff.toString(), null);
	}

	public Map findByid(Long id) {
		StringBuffer strBuff = new StringBuffer();
		strBuff.append(
				"select 	max((case  WHEN tdm.VPC_PORT = 13921001 THEN tdm.PORT_LEVEL END)) as TJ_PORT_LEVEL,	tdm.VPC_PORT,tdm.id,\n");
		strBuff.append(
				"max((case  WHEN tdm.VPC_PORT = 13921002 THEN tdm.PORT_LEVEL END)) as SH_PORT_LEVEL,tdm.VPC_PORT, t1.DEALER_SHORTNAME,t1.DEALER_ID	\n");
		strBuff.append(" from TM_DEALER_MAINTENANCE tdm,tm_dealer t1 WHERE tdm.DEALER_ID=t1.DEALER_ID AND t1.DEALER_ID="
				+ id + "\n");
		System.out.println(strBuff.toString());
		return OemDAOUtil.findFirst(strBuff.toString(), null);
	}

}
