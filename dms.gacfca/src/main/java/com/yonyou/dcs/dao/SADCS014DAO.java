package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class SADCS014DAO extends OemBaseDAO {
	/**
	 * 根据实销信息
	 * 
	 * @param contractNo
	 *            销售订单号
	 * @return
	 */
	public List<Map> getSalesVhclInfo() {
		StringBuffer pasql = new StringBuffer(" SELECT TVSR.CONTRACT_NO,TVSR.INVOICE_DATE,TVSR.SALES_DATE "
				+ " ,TVSR.CONTRACT_NO,TV.VIN " + " FROM  TT_VS_SALES_REPORT TVSR ,TM_VEHICLE_DEC TV "
				+ " WHERE TVSR.VEHICLE_ID = TV.VEHICLE_ID "
				+ " AND (case when TVSR.CONTRACT_NO=1 then TVSR.CONTRACT_NO else '' end) <> '1' ");
		return OemDAOUtil.findAll(pasql.toString(), null);
	}

}
