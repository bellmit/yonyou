package com.yonyou.dms.vehicle.service.afterSales.partMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtPartCeilingPriceDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtPartCeilingPricePO;

/**
 * 配件限价
 * @author Administrator
 *
 */
public interface PartCeilingPriceService {
	
	//配件限价查询
	public PageInfoDto partCeilingPriceQuery(Map<String, String> queryParam);
	
	//配件限价明细查询
	public TtPartCeilingPricePO getTmLimiteById(Long id);
	//查询明细~配件限价列表
	public PageInfoDto CeilingPriceQuery(Map<String, String> queryParam,String priceCode,String priceScope);
	
	//删除配件限价信息
	public void delete(Long id);
	
	//新增
	public void add(TtPartCeilingPriceDTO ptdto) ;
	
	//修改
	public void update(Long id,TtPartCeilingPriceDTO  ptdto) ;
	
	//下发
	public void xiafa(Long id);
	
	
	
	

}
