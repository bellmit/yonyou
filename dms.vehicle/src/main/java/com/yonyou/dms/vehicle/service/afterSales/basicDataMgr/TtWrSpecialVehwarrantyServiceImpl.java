package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.TtWrSpecialVehwarrantyDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrSpecialVehwarrantyPO;

/**
 * 特殊车辆质保期维护
 * @author Administrator
 *
 */
@Service
public class TtWrSpecialVehwarrantyServiceImpl implements TtWrSpecialVehwarrantyService{

	@Autowired
	TtWrSpecialVehwarrantyDao  ttWrSpecialVehwarrantyDao;

	/**
	 * 特殊车辆质保期维护查询
	 */
	@Override
	public PageInfoDto SpecialVehwarrantyQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrSpecialVehwarrantyDao.SpecialVehwarrantyQuery(queryParam);
	}

	/**
	 * 特殊车辆质保期维护新增
	 */
	public Long add(TtWrSpecialVehwarrantyDTO ptdto) {
		TtWrSpecialVehwarrantyPO ptPo=new TtWrSpecialVehwarrantyPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TtWrSpecialVehwarrantyPO ptPo, TtWrSpecialVehwarrantyDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
		            throw new ServiceBizException("已存在此特殊车辆质保期维护数据！新增失败！");
		        } 
		        else{
		   if(ptdto.getVin()!=null&&ptdto.getQualityTime()!=null&&ptdto.getQualityMileage()!=null){
			   ptPo.setString("VIN", ptdto.getVin());
			   ptPo.setInteger("QUALITY_TIME", ptdto.getQualityTime());
			   ptPo.setDouble("QUALITY_MILEAGE",ptdto.getQualityMileage());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();	
		  
		   }else{
			   throw new ServiceBizException("未填写完整重要信息，请输入！"); 
		   }
		        }
		      
	}
	 public List<Map> getApplyDataBy(TtWrSpecialVehwarrantyDTO ptdto) throws ServiceBizException {
	        StringBuilder sqlSb = new StringBuilder("  SELECT VIN from TT_WR_SPECIAL_VEHWARRANTY_dcs where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append(" and  VIN=?");
	        params.add(ptdto.getVin());
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return applyList;
	    }

	 /**
	  * 信息回显
	  */
	@Override
	public TtWrSpecialVehwarrantyPO getSpecialVehwarrantyById(Long id) {
		// TODO Auto-generated method stub
		return TtWrSpecialVehwarrantyPO.findById(id);
	}

	/**
	 * 修改
	 */
	@Override
	public void edit(Long id, TtWrSpecialVehwarrantyDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrSpecialVehwarrantyPO ptPo = TtWrSpecialVehwarrantyPO.findById(id);
		   ptPo.setInteger("QUALITY_TIME", ptdto.getQualityTime());
		   ptPo.setDouble("QUALITY_MILEAGE",ptdto.getQualityMileage());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();	
		
	}
/**
 * 删除
 */
	@Override
	public void delete(Long id) {
		TtWrSpecialVehwarrantyPO ptPo = TtWrSpecialVehwarrantyPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}
	
	
}
