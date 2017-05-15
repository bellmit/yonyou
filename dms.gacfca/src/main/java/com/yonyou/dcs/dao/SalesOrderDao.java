package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TtSalesOrderDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
/**
 * 
* @ClassName: SalesOrderDao 
* @Description: 销售订单上报数据
* @author zhengzengliang 
* @date 2017年4月6日 下午5:19:18 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class SalesOrderDao extends OemBaseDAO{

	/**
	 * 
	* @Title: selectTtSalesOrder 
	* @Description: 查询销售订单
	* @param @param entry
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectTtSalesOrder(TtSalesOrderDTO entry) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT tso.* FROM tt_sales_order tso WHERE 1=1 AND tso.SO_NO = ? AND tso.DEALER_CODE = ?");
		params.add(entry.getSoNo());
		params.add(entry.getEntityCode());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

}
