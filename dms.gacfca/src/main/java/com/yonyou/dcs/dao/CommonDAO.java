package com.yonyou.dcs.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.infoeai.eai.action.k4.S0007Impl;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TtVsOrderHistoryPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: CommonDAO 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月12日 上午10:38:08 
*
 */
@Repository
public class CommonDAO extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(CommonDAO.class);
	
	/**
	 * 新增订单历史记录
	 * @param order_id
	 * @param status
	 * @param change_remark
	 * @param remark
	 * @param loginUser
	 */
	public static void insertHistory(Long order_id,Integer status,String change_remark,
			String remark,Long userId,String userName){
		/* 新增 */
		TtVsOrderHistoryPO historyPo = new TtVsOrderHistoryPO();
		historyPo.setString("ORDER_ID", order_id.toString());
		historyPo.setString("CHANGE_STATUS", status.toString());
		historyPo.setString("CHANGE_REMARK", change_remark);
		historyPo.setString("REMARK", remark);
		historyPo.set("CREATE_BY", userId);
		historyPo.setString("CREATE_NAME", userName);
		historyPo.setTimestamp("CREATE_DATE", new Date());
		
		historyPo.saveIt();
	}
	
	/**
	 * 获得当前日期的工作周和工作年
	 * @return
	 * @throws Exception 
	 */
	public static Integer[] getNowWrokWeekAndYear() throws Exception {
		Integer[] wrokWY = new Integer[3];
		try {
			String sql = new String(" SELECT WORK_YEAR,WORK_WEEK,WORK_MONTH FROM TM_WORK_WEEK  WHERE START_DATE <= SYSDATE AND END_DATE >= SYSDATE AND STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" ");
			List<Map> list = OemDAOUtil.findAll(sql, null);
			if(null!=list && list.size()>0){
				Map map = list.get(0);
				wrokWY[0] = (Integer) map.get("WORK_WEEK");
				wrokWY[1] = (Integer) map.get("WORK_YEAR");
				wrokWY[2] = (Integer) map.get("WORK_MONTH");
				return wrokWY;
			}
		} catch (Exception e) {
			logger.error("获得当前日期的工作周和工作年", e);
			throw new Exception(e);
		}
		return null;
	}

	/**
	* 功能说明：根据订单中的EndItem取得基础价格
	* @param materialId
	* @return basePrice
	*/
	public static Object getBasePrice(long materialId) {
		Double basePrice = 0.0;
		StringBuffer sql = new StringBuffer(" select tmp.BASE_PRICE from TM_MATERIAL_PRICE tmp where tmp.ENABLE_DATE <= NOW() and tmp.DISABLE_DATE >= NOW() ");
		sql.append(" and material_id = '"+materialId+"'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list != null && !list.isEmpty()){
			basePrice = CommonUtils.checkNull(list.get(0).get("BASE_PRICE")) == "" ? 0.0 : Double.parseDouble(CommonUtils.checkNull(list.get(0).get("BASE_PRICE")));
		}
		return basePrice;
	}

	/**
	* 功能说明：根据订单中的EndItem取得MSRP价格
	* @param materialId
	* @return basePrice
	*/
	public static Object getMsrpPrice(long materialId) {
		Double basePrice = 0.0;
		StringBuffer sql = new StringBuffer(" select tmp.MSRP from TM_MATERIAL_PRICE tmp where tmp.ENABLE_DATE <= NOW() and tmp.DISABLE_DATE >= NOW() ");
		sql.append(" and material_id = '"+materialId+"'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(list != null && !list.isEmpty()){
			basePrice = CommonUtils.checkNull(list.get(0).get("BASE_PRICE")) == "" ? 0.0 : Double.parseDouble(CommonUtils.checkNull(list.get(0).get("BASE_PRICE")));
		}
		return basePrice;
	}

}
