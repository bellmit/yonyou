package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@Repository
public class PartOrderManageDao extends OemBaseDAO {
	
	/**
	 * 根据SAP订单号查找DMS订单号
	 */
	public String getOrderBySAPOrderNO(String SAPOrderNO) {
		
		String sql = "SELECT ORDER_NO FROM TT_PT_ORDER_DCS WHERE SAP_ORDER_NO = '"+SAPOrderNO+"' ";
		List<Map> list = OemDAOUtil.findAll(sql, null);
		//如果没有查询到数据  就返回空
		if (null != list && list.size() > 0) {
			return String.valueOf(list.get(0).get("ORDER_NO"));
		} else {
			return "";
		}
	}

}
