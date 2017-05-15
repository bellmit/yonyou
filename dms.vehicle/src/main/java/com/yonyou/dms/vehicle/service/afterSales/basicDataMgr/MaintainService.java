package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainLabourDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPackageDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPartDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPackagePO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPartPO;

/**
 * 保养套餐维护
 * @author Administrator
 *
 */
public interface MaintainService {
	//得到所有车型代码
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException;
	//查询保养套餐维护
	public PageInfoDto  MaintainQuery(Map<String, String> queryParam) ;
	//查询所有工时信息
	public List<Map> getGongshi(Map<String, String> queryParam) throws ServiceBizException;
	//获得保养套餐零部件信息列表
	public List<Map> getLingJian(Map<String, String> queryParam) throws ServiceBizException ;
	//新增保养套餐维护
	public void add(TtWrMaintainPackageDTO ptdto);
	//删除
	public void delete(Long id);
	//信息回显
	public Map getBaoYang(Long id);
	public PageInfoDto  getXiangMu(Map<String, String> queryParam,Long id) ;
	public PageInfoDto  getTaoCan(Map<String, String> queryParam,Long id) ;
     //修改
	public void edit(Long id, TtWrMaintainPackageDTO ptdto) ;
	//删除工时信息
	public void delete2(Long id);
	//删除零件信息
	public void delete3(Long id);
	//项目信息回显
	public TtWrMaintainLabourPO getXiangMu(Long id);
	//零件信息回显
	public TtWrMaintainPartPO getTaoCan(Long id) ;
	//修改项目信息
	public void editLabour(Long id, TtWrMaintainLabourDTO ptdto) ;
	//修改零件信息
	public void editPart(Long id, TtWrMaintainPartDTO ptdto) ;
	//复制
	public void add2(TtWrMaintainPackageDTO ptdto,Long id);
	//下发
	public void xiaFa(Long id);
	
	
	
	
}