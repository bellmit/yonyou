package com.yonyou.dms.vehicle.service.afterSales.priceLimit;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yonyou.dms.common.domains.PO.basedata.TmLimiteCposPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TmLimiteCposDTO;

/**
 *  车系限价管理 
 * @author Administrator
 *
 */
public interface PriceLimiteManageService {
	//车系限价管理 查询
	public PageInfoDto PriceLimiteManageQuery(Map<String, String> queryParam) ;
	//查询所有品牌代码
	  public List<Map> getBrandCode();
	  //通过品牌代码查询所有车系代码
	   public List<Map> getSeriesCode(String brandCode);
/*	   public List<Map> getSeriesCode2(String brandCode);*/
	   //下载
		public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request);
		//新增
		public Long add(TmLimiteCposDTO ptdto);
		
		//修改
		public void edit(Long id, TmLimiteCposDTO ptdto);
		
		//修改时的信息回显
		public TmLimiteCposPO getTmLimiteById(Long id);
		/*   public Map getTmLimiteById(Long id);*/
		
		//删除
		public void delete(Long id);
		
		//下发
		public void xiafa(Long id);

}
