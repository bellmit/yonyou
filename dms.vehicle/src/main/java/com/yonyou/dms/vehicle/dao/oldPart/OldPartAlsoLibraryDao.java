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
public class OldPartAlsoLibraryDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	public PageInfoDto findGcs(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select distinct tos.STORE_ID	\n");
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
		sql.append("	  ,DATE_FORMAT(tos.CREATE_DATE,'%Y-%m-%d') OPERATION_DATE	#入库日期\n");
		sql.append("	  ,tos.STORE_STATUS#库存状态	\n");
		sql.append("	  ,tmos.STOR_NAME	\n");
		sql.append("	  ,vm.BRAND_NAME #品牌	\n");
		sql.append("	  ,vm.SERIES_NAME #车系	\n");
		sql.append("	  ,vm.GROUP_NAME #车型	\n");
		sql.append("	  ,too.OLDPART_TYPE#旧件类型	\n");
		sql.append("	  ,too.RETURN_BILL_TYPE#回运类型	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_APPLY_DATE,'%Y-%m-%d') CLAIM_APPLY_DATE#索赔申请日期	\n");
		sql.append("	  ,DATE_FORMAT(too.CLAIM_AUDIT_DATE,'%Y-%m-%d') CLAIM_AUDIT_DATE#索赔审核通过日期	\n");
		sql.append("	  ,too.MSV  #MSV码	\n");
		sql.append("	  ,DATE_FORMAT(tv.PURCHASE_DATE,'%Y-%m-%d') PURCHASE_DATE#购车日期	\n");
		sql.append("	  ,DATE_FORMAT(too.REPAIR_DATE,'%Y-%m-%d') REPAIR_DATE#维修日期	\n");
		sql.append("	  ,too.REPAIR_TYPE#保修类型	\n");
		sql.append("	  ,too.OUT_MILEAGE#行驶里程	\n");
		sql.append("	  ,too.REPAIR_REMARK#维修备注	\n");
		sql.append("	from TT_OP_STORE_DCS tos	#旧件库存表	\n");
		sql.append("	   LEFT JOIN TT_OP_OLDPART_DCS too on (tos.OLDPART_NO=too.OLDPART_NO and tos.OLDPART_ORDER=too.OLDPART_ORDER and tos.DEALER_CODE=too.DEALER_CODE and tos.REPAIR_NO=too.REPAIR_NO)#旧件清单表	\n");
		sql.append("	   LEFT JOIN TM_VEHICLE_DEC TV ON TOO.VIN = TV.VIN	\n");
		sql.append("	   LEFT JOIN ("+getVwMaterialSql()+") VM ON VM.MATERIAL_ID = TV.MATERIAL_ID	\n");
		sql.append("	   LEFT JOIN TM_OLDPART_STOR_DCS tmos on tmos.STOR_ID = tos.STOR_ADDRESS_ID#旧件仓库维护	\n");
		sql.append("where 1=1 \n");
		sql.append("and tos.OEM_COMPANY_ID = '"+loginInfo.getCompanyId()+"'\n");
		//默认查询出库状态数据
		if (!StringUtils.isNullOrEmpty(queryParam.get("storeStatus"))) {
			sql.append("   AND tos.STORE_STATUS =?");
			params.add(queryParam.get("storeStatus"));
		}
		//出库时间
		if (!StringUtils.isNullOrEmpty(queryParam.get("startDate"))) {
			sql.append("   AND DATE(tos.UPDATE_DATE) >=? \n");
			params.add(queryParam.get("startDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append("   AND DATE(tos.UPDATE_DATE) <=? \n");
			params.add(queryParam.get("endDate"));
		}
		//旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpartNo"))) {
			sql.append("   AND tos.OLDPART_NO like(?)");
			params.add("%"+queryParam.get("oldpartNo")+"%");
		}
		//工单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
			sql.append("   AND tos.REPAIR_NO like(?)");
			params.add("%"+queryParam.get("repairNo")+"%");
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append("   AND tos.VIN =?");
			params.add(queryParam.get("vin"));
			}
		//经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append("   AND tos.DEALER_CODE =?");
			params.add(queryParam.get("dealerCode"));
		}
        return sql.toString(); 
	}
	/**
	 * 待入库的旧件查询
	 * @param storeId
	 * @param companyId
	 * @param pageSize
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto outSotrageApproval(Long id) throws Exception {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("select distinct tos.STORE_ID	\n");
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
		sql.append("	  ,DATE_FORMAT(tos.CREATE_DATE,'%Y-%m-%d') OPERATION_DATE	#入库日期\n");
		sql.append("	  ,tos.STORE_STATUS#库存状态	\n");
		sql.append("	  ,tmos.STOR_NAME	\n");
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
		sql.append("	  	and tos.STORE_STATUS="+OemDictCodeConstants.OP_STORE_STATUS_OUT+"	\n");//出库
		if (!StringUtils.isNullOrEmpty(id)) {
			sql.append("   AND tos.STORE_ID =?");
			params.add(id);
		}
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
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
		tosd.set("Store_Type",OemDictCodeConstants.OP_STORE_STATUS_IN);
		tosd.set("Stor_Address_Id",opStoreList.get(0).get("Stor_Address_Id"));
		tosd.set("Operation_Date",new Date());
		tosd.set("Create_By",loginInfo.getUserId());
		tosd.set("Create_Date",new Date());
		tosd.saveIt();
		
	}
}
