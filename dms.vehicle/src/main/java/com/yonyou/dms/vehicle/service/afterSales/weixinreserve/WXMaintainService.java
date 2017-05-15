package com.yonyou.dms.vehicle.service.afterSales.weixinreserve;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve.TmWxMaintainPackageDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPartPO;


/**
 * 微信保养套餐维护
 * @author Administrator
 *
 */
public interface WXMaintainService {
	// 微信保养套餐维护查询
	public PageInfoDto WXMaintainQuery(Map<String, String> queryParam);
	   //年款查询
		public List<Map> getModelYearList();
		//车系查询
		public List<Map> getSeriesList(String modelYear);
		//通过车系和年款查询排量信息
		public List<Map> getEngineList(String modelYear,String seriesCode) ;
		//删除
		public void delete(Long id);
		
		//查询所有工时信息
		public List<Map> getGongshi(Map<String, String> queryParam) throws ServiceBizException;
		//获得保养套餐零部件信息列表
		public List<Map> getLingJian(Map<String, String> queryParam) throws ServiceBizException ;
		
		//新增微信保养套餐维护
		public void add(TmWxMaintainPackageDcsDTO ptdto);
		
		//保养套餐信息回显
		public Map getBaoYang(Long id);
		
		//获得项目信息列表
		public PageInfoDto getXiangMu(Map<String, String> queryParam, Long id);
		
		//获得零件信息列表
		public PageInfoDto getLingJian(Map<String, String> queryParam, Long id);
		
		//项目信息回显
		public TmWxMaintainLabourPO getXiangMuById(Long id);
		
		//零件信息回显
		public TmWxMaintainPartPO getLingJianById(Long id);
		
		//修改微信保养套餐维护信息
		public void edit(Long id, TmWxMaintainPackageDcsDTO dto);
		
		//复制微信保养套餐信息
		public void add2(TmWxMaintainPackageDcsDTO ptdto, Long id);

}
