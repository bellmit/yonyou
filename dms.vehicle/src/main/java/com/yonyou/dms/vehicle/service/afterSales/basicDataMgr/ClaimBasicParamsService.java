package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBasicParaDTO;


/**
 * 索赔基本参数设定
 * @author Administrator
 *
 */
public interface ClaimBasicParamsService {
	//索赔基本参数设定查询
	public PageInfoDto  BasicParamsQuery(Map<String, String> queryParam);
	
	//索赔基本参数设定新增
	 public Long addBasicParams(TtWrBasicParaDTO ptdto) ;
	//通过id查询回显参数设定数据
/*	 public TtWrBasicParaPO getBasicParamsById(Long id); */
	 
	 public Map  getShuju(Long id);
	//修改索赔基本参数设定数据
	 public void edit(Long id,TtWrBasicParaDTO ptdto) ;
	//删除索赔基本参数设定数据
	 public void delete(Long id) ;
     //下发
	 public void getAll();
}
