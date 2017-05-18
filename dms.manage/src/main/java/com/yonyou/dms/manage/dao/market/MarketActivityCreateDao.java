package com.yonyou.dms.manage.dao.market;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.market.TmMarketActivityDTO;
import com.yonyou.dms.manage.domains.PO.market.TmMarketActivityPO;

@Repository
public class MarketActivityCreateDao extends OemBaseDAO{
	/**
	 * 市场活动查询
	 * @param conditionWhere
	 * @param pageSize
	 * @param curPage
	 * @return
	 */
	public PageInfoDto marketActivityQueryList(Map<String, String> queryParam) {
		System.out.println("进入市场活动查询方法");
		List params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	public String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ID,MARKET_NO,MARKET_NAME,DATE_FORMAT(START_DATE,'%Y-%m-%d ') START_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d ') END_DATE from TM_MARKET_ACTIVITY where 1=1\n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("marketName"))) {
			sql.append(" and MARKET_NAME like '%"+queryParam.get("marketName")+"%' ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("marketNo"))) {
			sql.append("   AND MARKET_NO like '%"+queryParam.get("marketNo")+"%' \n");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("retailDateStart"))) {
			sql.append("   AND (DATE(START_DATE) >= ? \n");
			params.add(queryParam.get("retailDateStart"));
			sql.append("   AND DATE(START_DATE) <= ? \n");
			params.add(queryParam.get("retailDateEnd"));
			sql.append("      or END_DATE>= ? ");
			params.add(queryParam.get("retailDateStart"));
			sql.append("   and END_DATE<= ?) \n");
			params.add(queryParam.get("retailDateEnd"));
		}
		System.out.println(sql.toString());
		return sql.toString();
	}
	/**
	 * 新增信息
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addMarket(TmMarketActivityDTO tcbdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer("select MARKET_NO,MARKET_NAME  from TM_MARKET_ACTIVITY where 1=1 and MARKET_NO = ?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcbdto.getMarketNo());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("活动编号不能重复！");
		} else {
			TmMarketActivityPO lap = new TmMarketActivityPO();
			lap.setString("MARKET_NAME", tcbdto.getMarketName());
			lap.setString("MARKET_NO", tcbdto.getMarketNo());
			lap.setDate("START_DATE", tcbdto.getStartDate());
			lap.setDate("END_DATE", tcbdto.getEndDate());
			lap.saveIt();
			return (Long) lap.getLongId();
		}
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyMarket(Long id, TmMarketActivityDTO tcdto) throws ServiceBizException {
		TmMarketActivityPO tc = TmMarketActivityPO.findById(id);
		setTmMarketActivityPO(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * 
	 * @param tc
	 * @param user
	 */
	private void setTmMarketActivityPO(TmMarketActivityPO lap, TmMarketActivityDTO tcbdto) {
		lap.setString("MARKET_NAME", tcbdto.getMarketName());
		lap.setString("MARKET_NO", tcbdto.getMarketNo());
		lap.setDate("START_DATE", tcbdto.getStartDate());
		lap.setDate("END_DATE", tcbdto.getEndDate());
	}
}
