package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle.CustcomVehicleZiLiaoQueryDao;

/**
 * 客户车辆资料查询
 * @author Administrator
 *
 */
@Service
public class CustcomVehicleQueryServiceImpl implements CustcomVehicleQueryService{
    @Autowired
    CustcomVehicleZiLiaoQueryDao  custcomVehicleQueryDao;

    /**
     * 客户车辆资料查询
     */
	@Override
	public PageInfoDto VehicleZiLiaoQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return custcomVehicleQueryDao.VehicleZiLiaoQuery(queryParam);
	}
	//通过id进行明细查询车辆信息
	@Override
	public Map getVehicle(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=custcomVehicleQueryDao.getVehicle(id);
    	 m= list.get(0);
    	   return m;
	}
	//通过id进行明细查询客户信息
	@Override
	public Map getCustomer(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=custcomVehicleQueryDao.getCustomer(id);
    	 m= list.get(0);
    	   return m;
	}
	//通过id进行明细查询联系人信息
	@Override
	public Map getPeople(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=custcomVehicleQueryDao.getPeople(id);
		 if(list!=null&&list.size()>0){
			 m= list.get(0);
			 return m; 
		 }else{
			 return null;
		 }
    	 
	}
	// 通过id进行查询二手车信息
	@Override
	public Map getCheXinXi(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=custcomVehicleQueryDao.getCheXinXi(id);
    	 if(list!=null&&list.size()>0){
    		 m= list.get(0);
      	   return m;
    	 }else{
    		 return null;
    	 }
    	
	}
			
			
			
			
}
