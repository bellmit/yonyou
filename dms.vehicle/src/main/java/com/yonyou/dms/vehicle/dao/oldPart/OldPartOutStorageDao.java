package com.yonyou.dms.vehicle.dao.oldPart;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpStoreDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpStoreDetailPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpStorePO;

@Repository
public class OldPartOutStorageDao extends OemBaseDAO{

	
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	public PageInfoDto findGcs(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select * from (select tos.STORE_ID	\n");
		sql.append("	  ,tos.DEALER_CODE,tos.DEALER_NAME	\n");
		sql.append("	  ,tos.STOR_ADDRESS_ID#旧件仓库	\n");
		sql.append("	  ,tos.RETURN_BILL_NO#回运单号	\n");
		sql.append("	  ,tos.BALANCE_NO#结算单号	\n");
		sql.append("	  ,tos.REPAIR_NO#工单号	\n");
		sql.append("	  ,tos.CLAIM_NUMBER#索赔单号	\n");
		sql.append("	  ,tos.VIN	\n");
		sql.append("	  ,tos.OLDPART_NO#旧件代码	\n");
		sql.append("	  ,tos.OLDPART_NAME#旧件名称	\n");
		sql.append("	  ,tos.OLDPART_ORDER#旧件序号	\n");
//		sql.append("	  ,DATE_FORMAT(tos.CREATE_DATE,'%Y-%m-%d') RECEIVE_DATE	#入库日期\n");
		sql.append("	  ,(select DATE_FORMAT(tosd.OPERATION_DATE,'%Y-%m-%d')as RECEIVE_DATE from TT_OP_STORE_DETAIL_DCS tosd where tos.STORE_ID = tosd.STORE_ID order by tosd.OPERATION_DATE desc LIMIT 0,1)AS OPERATION_DATE	#入库日期\n");
		sql.append("	  ,tos.STORE_STATUS#库存状态	\n");
		sql.append("	  ,tmos.STOR_NAME	\n");
		sql.append("	  ,too.PART_FEE#含税索赔价	\n");
		sql.append("	  ,vm.BRAND_NAME #品牌	\n");
		sql.append("	  ,vm.SERIES_NAME #车系	\n");
		sql.append("	  ,vm.GROUP_NAME #车型	\n");
		sql.append("	  ,too.OLDPART_TYPE#旧件类型	\n");
		sql.append("	  ,too.RETURN_BILL_TYPE#回运类型	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_APPLY_DATE,'%Y-%m-%d')as CLAIM_APPLY_DATE#索赔申请日期	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_AUDIT_DATE,'%Y-%m-%d')as CLAIM_AUDIT_DATE#索赔审核通过日期	\n");
		sql.append("	  ,too.MSV#MSV码	\n");
		sql.append("	  ,DATE_FORMAT(tv.PURCHASE_DATE,'%Y-%m-%d')as PURCHASE_DATE#购车日期	\n");
		sql.append("	  ,DATE_FORMAT(too.REPAIR_DATE,'%Y-%m-%d')as REPAIR_DATE#维修日期	\n");
		sql.append("	  ,too.REPAIR_TYPE#保修类型	\n");
		sql.append("	  ,too.OUT_MILEAGE#行驶里程	\n");
		sql.append("	  ,too.REPAIR_REMARK#维修备注	\n");
		sql.append("	from TT_OP_STORE_DCS tos	#旧件库存表	\n");
		sql.append("	   LEFT JOIN TT_OP_OLDPART_DCS too on (tos.OLDPART_NO=too.OLDPART_NO and tos.OLDPART_ORDER=too.OLDPART_ORDER and tos.DEALER_CODE=too.DEALER_CODE and tos.REPAIR_NO=too.REPAIR_NO)#旧件清单表	\n");
		sql.append("	   LEFT JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN	\n");
		sql.append("	   LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV.MATERIAL_ID	\n");
		sql.append("	   LEFT JOIN TM_OLDPART_STOR_DCS tmos on tmos.STOR_ID = tos.STOR_ADDRESS_ID#旧件仓库维护	\n");
		sql.append("	  where tos.OEM_COMPANY_ID="+loginInfo.getCompanyId()+"	\n");
		//经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append("   AND tos.DEALER_CODE =?");
			params.add(queryParam.get("dealerCode"));
		}
		//旧件库
		if (!StringUtils.isNullOrEmpty(queryParam.get("tmStoreId"))) {
			sql.append("   AND tos.STOR_ADDRESS_ID =?");
			params.add(queryParam.get("tmStoreId"));
		}
		//旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartNo"))) {
			sql.append("   AND tos.OLDPART_NO like(?)");
			params.add("%"+queryParam.get("oldpartNo")+"%");
		}
		//旧件名称
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartName"))) {
			sql.append("   AND tos.OLDPART_NAME like(?)");
			params.add("%"+queryParam.get("oldpartName")+"%");
		}
		//库存状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("storeStatus"))) {
			sql.append("   AND tos.STORE_STATUS =?");
			params.add(queryParam.get("storeStatus"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
			sql.append("   AND DATE(tos.CREATE_DATE) >=? \n");
			params.add(queryParam.get("startdate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
			sql.append("   AND DATE(tos.CREATE_DATE) <=? \n");
			params.add(queryParam.get("enddate"));
		}
		sql.append("   ) ttzz \n");
		return sql.toString();
	}
	
	/**
	 * 待出库的旧件查询
	 * @param storeId
	 * @param companyId
	 * @param pageSize
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto outSotrageApproval(Long id) {
		List<Object> params = new ArrayList<Object>();
		String sql = getSql(id, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String getSql(Long id, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select * from (select tos.STORE_ID	\n");
		sql.append("	  ,tos.DEALER_CODE,tos.DEALER_NAME	\n");
		sql.append("	  ,tos.STOR_ADDRESS_ID#旧件仓库	\n");
		sql.append("	  ,tos.RETURN_BILL_NO#回运单号	\n");
		sql.append("	  ,tos.BALANCE_NO#结算单号	\n");
		sql.append("	  ,tos.REPAIR_NO#工单号	\n");
		sql.append("	  ,tos.CLAIM_NUMBER#索赔单号	\n");
		sql.append("	  ,tos.VIN	\n");
		sql.append("	  ,tos.OLDPART_NO#旧件代码	\n");
		sql.append("	  ,tos.OLDPART_NAME#旧件名称	\n");
		sql.append("	  ,tos.OLDPART_ORDER#旧件序号	\n");
//		sql.append("	  ,DATE_FORMAT(tosd.OPERATION_DATE,'%Y-%m-%d') RECEIVE_DATE	#入库日期\n");
		sql.append("	  ,(select DATE_FORMAT(tosd.OPERATION_DATE,'%Y-%m-%d') RECEIVE_DATE from TT_OP_STORE_DETAIL_dcs tosd where tos.STORE_ID = tosd.STORE_ID order by tosd.OPERATION_DATE desc LIMIT 0,1)	#入库日期\n");
		sql.append("	  ,tos.STORE_STATUS#库存状态	\n");
		sql.append("	  ,tmos.STOR_NAME	\n");
		sql.append("	  ,too.PART_FEE#含税索赔价	\n");
		sql.append("	  ,vm.BRAND_NAME #品牌	\n");
		sql.append("	  ,vm.SERIES_NAME #车系	\n");
		sql.append("	  ,vm.GROUP_NAME #车型	\n");
		sql.append("	  ,too.OLDPART_TYPE#旧件类型	\n");
		sql.append("	  ,too.RETURN_BILL_TYPE#回运类型	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_APPLY_DATE,'%Y-%m-%d') CLAIM_APPLY_DATE#索赔申请日期	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_AUDIT_DATE,'%Y-%m-%d') CLAIM_AUDIT_DATE#索赔审核通过日期	\n");
		sql.append("	  ,too.MSV#MSV码	\n");
		sql.append("	  ,DATE_FORMAT(tv.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE#购车日期	\n");
		sql.append("	  ,DATE_FORMAT(too.REPAIR_DATE,'%Y-%m-%d') REPAIR_DATE#维修日期	\n");
		sql.append("	  ,too.REPAIR_TYPE#保修类型	\n");
		sql.append("	  ,too.OUT_MILEAGE#行驶里程	\n");
		sql.append("	  ,too.REPAIR_REMARK#维修备注	\n");
		sql.append("	from TT_OP_STORE_DCS tos	#旧件库存表	\n");
		sql.append("	   LEFT JOIN TT_OP_STORE_DETAIL_DCS tosd on tos.STORE_ID = tosd.STORE_ID#旧件库存明细表	\n");
		sql.append("	   LEFT JOIN TT_OP_OLDPART_DCS too on (tos.OLDPART_NO=too.OLDPART_NO and tos.OLDPART_ORDER=too.OLDPART_ORDER and tos.DEALER_CODE=too.DEALER_CODE and tos.REPAIR_NO=too.REPAIR_NO)#旧件清单表	\n");
		sql.append("	   LEFT JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN	\n");
		sql.append("	   LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV.MATERIAL_ID	\n");
		sql.append("	   LEFT JOIN TM_OLDPART_STOR_DCS tmos on tmos.STOR_ID = tos.STOR_ADDRESS_ID#旧件仓库维护	\n");
		sql.append("	  where tos.OEM_COMPANY_ID="+loginInfo.getCompanyId()+"	\n");
		sql.append("	  	and tos.STORE_STATUS="+OemDictCodeConstants.OP_STORE_STATUS_IN+"	\n");//在库
		sql.append("	  	and tosd.STORE_TYPE="+OemDictCodeConstants.OP_STORE_STATUS_IN+"	\n");//在库
		if (!StringUtils.isNullOrEmpty(id)) {
			sql.append("   AND tos.STORE_ID =?");
			params.add(id);
		}
		sql.append("	limit 0,1	\n");
		sql.append("	) zzt	\n");
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyoldParth(Long id, TtOpStoreDTO tcdto) throws ServiceBizException {
		TtOpStorePO tc = TtOpStorePO.findById(id);
		setTmOldpartStor(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTmOldpartStor(TtOpStorePO tc, TtOpStoreDTO tcdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setInteger("STORE_STATUS", tcdto.getStoreStatus());
		tc.setString("REMARK", tcdto.getRemark());
		tc.setLong("UPDATE_BY", loginInfo.getUserId());
		tc.setDate("UPDATE_DATE",new Date());
		
		TtOpStoreDetailPO tosd = new TtOpStoreDetailPO();
		List<TtOpStorePO> opStoreList = TtOpStorePO.findAll();
		tosd.set("Store_Id",(Long)opStoreList.get(0).get("Store_Id"));
		tosd.set("Store_Type",OemDictCodeConstants.OP_STORE_STATUS_OUT);
		tosd.set("Stor_Address_Id",opStoreList.get(0).get("Stor_Address_Id"));
		tosd.set("Operation_Date",new Date());
		tosd.set("Create_By",loginInfo.getUserId());
		tosd.set("Create_Date",new Date());
		tosd.saveIt();
		
	}
}