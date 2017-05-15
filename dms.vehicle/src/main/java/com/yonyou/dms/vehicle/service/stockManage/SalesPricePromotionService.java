package com.yonyou.dms.vehicle.service.stockManage;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 促销价格维护接口
 * @author Zhl
 * @date 2017年3月06日
 */


public interface SalesPricePromotionService {
	//车辆日志信息列表分页查询
	public  PageInfoDto QuerySalesPrice(Map<String, String> queryParam);
	
    /**
     * 批量修改促销价格
    * @author zhl
    * @date 2017年1月12日
    * @param vins
    * @param location
     */
	 void editsalesPrice(Map<String, String> map) throws ServiceBizException;
	
}
