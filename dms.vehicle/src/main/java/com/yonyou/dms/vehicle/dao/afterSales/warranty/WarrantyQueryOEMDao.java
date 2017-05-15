package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.math.BigDecimal;
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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author zhanghongyi
 */
@Repository
public class WarrantyQueryOEMDao extends OemBaseDAO {

	/**
	 * 保修单查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getWarrantyQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 保修单明细
	 * @param deliverId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWarrantyDetail(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TWWC.ID,TWWC.WC_CODE,TWWC.REP_CODE,TWWC.CACU_CODE,TWWC.VIN,tvmg2.GROUP_NAME,\n");
		sql.append("tvmg3.GROUP_CODE,tvmg3.GROUP_NAME,tvm.COLOR_NAME,tvm.TRIM_NAME,TWWC.RANGET,\n");
		sql.append("DATE_FORMAT(TWWC.REP_DATE,'%Y-%m-%d') REP_DATE,DATE_FORMAT(TWWC.FINISH_DATE,'%Y-%m-%d') FINISH_DATE,\n");
		sql.append("DATE_FORMAT(TWWC.WR_BEGIN_DATE,'%Y-%m-%d') WR_BEGIN_DATE,TWWC.IS_PRESALE,\n");
		sql.append("DATE_FORMAT(TWWC.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TWWC.SUBMIT_DATE,'%Y-%m-%d') SUBMIT_DATE,\n");
		sql.append("TWWC.MVS,TWWC.REMARK FROM TT_WR_WARRANTY_CARD_DCS TWWC\n");
		sql.append("LEFT JOIN tm_vehicle_dec tv on TWWC.VIN=tv.VIN\n");
		sql.append("left join tm_vhcl_material tvm on tvm.MATERIAL_ID=tv.MATERIAL_ID\n");
		sql.append("left join tm_vhcl_material_group_r tvmgr on tvm.MATERIAL_ID=tvmgr.MATERIAL_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg on tvmgr.GROUP_ID = tvmg.GROUP_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg3 on tvmg3.GROUP_ID = tvmg.PARENT_GROUP_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg2 on tvmg2.GROUP_ID = tvmg3.PARENT_GROUP_ID\n");
		sql.append("WHERE 1 = 1 \n");
		sql.append("AND TWWC.ID = " + id + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * 保修单故障明细
	 * @param deliverId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getWarrantyFault(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t.ID,t.WT_CODE,t.BUG_CODE,t.BUG_NAME,t.LOC_CODE1,t.LOC_CODE2,t.LOC_CODE3,t.LOC_CODE4,t.LOC_CODE5, \n");
		sql.append("t.SECRET_CODE,t.PREPOWER_CODE,t.TAG,t.LAST_PT_CODE,t.LAST_PT_NAME,t.LAST_RANGE, \n");
		sql.append("DATE_FORMAT(t.LAST_INSTALL_DATE,'%Y-%m-%d') LAST_INSTALL_DATE,t.ACT_CODE,t.ACT_NAME,t.ORDER_CODE \n");
		sql.append("from tt_wr_wc_detail_dcs t \n");
		sql.append("WHERE 1 = 1 \n");
		sql.append("AND t.ID = " + id + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * SQL组装保修单
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  \n");
		sql.append("TWWC.ID, \n");
		sql.append("TD.DEALER_CODE, #经销商代码 \n");
		sql.append("TD.DEALER_SHORTNAME, #经销商名称 \n");
		sql.append("TWWC.WC_CODE, #保修单号 \n");
		sql.append("TWWC.WC_CODE_ESIGI, #ESIGI保修单号 \n");
		sql.append("TWWC.IS_NO_REP, #商务保修 \n");
		sql.append("TWWC.CACU_CODE, #结算单号 \n");
		sql.append("TWWC.REP_CODE, #工单号 \n");
		sql.append("TWWC.VIN, \n");
		sql.append("TWWC.IS_PRESALE, #售前保修 \n");
		sql.append("TWWC.STATUS, #保修单状态 \n");
		sql.append("TWWC.STATUS_ESIGI, #ESIGI审核状态 \n");
		sql.append("TWWC.SEND_STATUS, #上报esg的结果  \n");
		sql.append("DATE_FORMAT(TWWC.CREATE_DATE,'%Y-%m-%d') CREATE_DATE #申请日期 \n");
		sql.append("FROM TT_WR_WARRANTY_CARD_DCS TWWC\n");
		sql.append("LEFT JOIN TM_DEALER TD ON TWWC.DEALER_ID = TD.DEALER_ID \n");
		sql.append("LEFT JOIN tm_vehicle_dec tv on TWWC.VIN=tv.VIN\n");
		sql.append("left join tm_vhcl_material tvm on tvm.MATERIAL_ID=tv.MATERIAL_ID\n");
		sql.append("left join tm_vhcl_material_group_r tvmgr on tvm.MATERIAL_ID=tvmgr.MATERIAL_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg on tvmgr.GROUP_ID = tvmg.GROUP_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg3 on tvmg3.GROUP_ID = tvmg.PARENT_GROUP_ID\n");
		sql.append("left join tm_vhcl_material_group tvmg2 on tvmg2.GROUP_ID = tvmg3.PARENT_GROUP_ID\n");
		sql.append("WHERE 1 = 1 \n");
		sql.append("AND TWWC.STATUS IN ("+OemDictCodeConstants.WARRANTY_REPAIR_STATUS_02+","+OemDictCodeConstants.WARRANTY_REPAIR_STATUS_04+","+OemDictCodeConstants.WARRANTY_REPAIR_STATUS_05+") \n");
        //经销商编号
        if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){ 
        	sql.append(" 	AND TD.DEALER_CODE = '" + queryParam.get("dealerCode") + "' \n");
        }
        //经销商名称
        if(!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))){ 
        	sql.append(" 	AND TD.DEALER_SHORTNAME like '%" + queryParam.get("dealerName") + "%' \n");
        }
        //VIN
        if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){ 
        	sql.append(" 	AND TWWC.VIN like '%" + queryParam.get("vin") + "%' \n");
        }
        //车系
        if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){ 
        	sql.append(" 	AND tvmg2.GROUP_ID = " + queryParam.get("seriesId") + " \n");
        }
        //车型
        if(!StringUtils.isNullOrEmpty(queryParam.get("modelId"))){ 
        	sql.append(" 	AND tvmg3.GROUP_ID = " + queryParam.get("modelId") + " \n");
        }
        //车型名称
        if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){ 
        	sql.append(" 	AND tvmg3.GROUP_NAME like '%" + queryParam.get("groupName") + "%' \n");
        }
        //保修单号
        if(!StringUtils.isNullOrEmpty(queryParam.get("wcCode"))){ 
        	sql.append(" 	AND TWWC.WC_CODE like '%" + queryParam.get("wcCode") + "%' \n");
        }
        //ESIGI保修单号
        if(!StringUtils.isNullOrEmpty(queryParam.get("wcCodeESIGI"))){ 
        	sql.append(" 	AND TWWC.WC_CODE_ESIGI like '%" + queryParam.get("wcCodeESIGI") + "%' \n");
        }
        //工单号
        if(!StringUtils.isNullOrEmpty(queryParam.get("repCode"))){ 
        	sql.append(" 	AND TWWC.REP_CODE like '%" + queryParam.get("repCode") + "%' \n");
        }
        //保修单状态
        if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){ 
        	sql.append(" 	AND TWWC.STATUS = " + queryParam.get("status") + " \n");
        }
        //ESIGI审核状态
        if(!StringUtils.isNullOrEmpty(queryParam.get("statusESIGI"))){ 
        	sql.append(" 	AND TWWC.STATUS_ESIGI = " + queryParam.get("statusESIGI") + " \n");
        }
        //商务保修
        if(!StringUtils.isNullOrEmpty(queryParam.get("isNoRep"))){ 
        	sql.append(" 	AND TWWC.IS_NO_REP = " + queryParam.get("isNoRep") + " \n");
        }
        //售前保修
        if(!StringUtils.isNullOrEmpty(queryParam.get("isPresale"))){ 
        	sql.append(" 	AND TWWC.IS_PRESALE = " + queryParam.get("isPresale") + " \n");
        }
		//开始日期不为空
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){ 
			sql.append(" AND  DATE_FORMAT(TWWC.CREATE_DATE,'%Y-%m-%d') >='" + queryParam.get("startDate") +"' \n");
	    }
		//结束日期不为空
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){ 
			sql.append(" AND  DATE_FORMAT(TWWC.CREATE_DATE,'%Y-%m-%d') <='" + queryParam.get("endDate") +"' \n");
	    }
		System.out.println(sql.toString());
		return sql.toString();
	}

}
