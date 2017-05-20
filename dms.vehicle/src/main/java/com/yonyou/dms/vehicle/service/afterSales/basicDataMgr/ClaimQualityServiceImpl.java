package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.ClaimQualityDAO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrWarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrWarrantyPO;

/**
 * 索赔质保期维护
 * @author Administrator
 *
 */
@Service
public class ClaimQualityServiceImpl  implements ClaimQualityService{
	@Autowired
	ClaimQualityDAO  claimQualityDAO;


	/**
	 * 索赔质保期维护查询
	 */
	@Override
	public PageInfoDto ClaimQualityQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimQualityDAO.ClaimQualityQuery(queryParam);
	}

		/**
		 * 修改信息回显
		 */
	@Override
	public Map getShuju(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=claimQualityDAO.getShuju(id);
    	 m= list.get(0);
		return m;
	}
	
	/**
	 * 索赔质保期维护新增
	 */
	public Long add(TtWrWarrantyDTO ptdto) {
		TtWrWarrantyPO ptPo=new TtWrWarrantyPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TtWrWarrantyPO ptPo, TtWrWarrantyDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
		            throw new ServiceBizException("已存在此索赔质保期数据！新增失败！");
		        } 
		        else{
		        	if( ptdto.getQualityTime()>=24){        		
		        if(ptdto.getQualityMileage()>=50000){
			   ptPo.setDouble("QUALITY_TIME", ptdto.getQualityTime());
			   ptPo.setInteger("QUALITY_MILEAGE",ptdto.getQualityMileage());
			   ptPo.setInteger("THREEPACK_TIME",24);
			   ptPo.setDouble("THREEPACK_MILEAGE",50000);   
			   ptPo.setLong("MODEL_ID",ptdto.getModelId());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();
		        	}else{
		        		 throw new ServiceBizException("质保里程（公里）不能小于  三包责任里程！新增失败！");
		        	 }
		        	}else{
		        		 throw new ServiceBizException("质保期限（月）不能小于  三包责任期！新增失败！");
		        	}
		        }
	
	}
    public List<Map> getApplyDataBy(TtWrWarrantyDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT MODEL_ID from tt_wr_warranty_dcs where 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and model_id=?");
        params.add(ptdto.getModelId());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }   

	/**
	 * 索赔质保期维护修改
	 */
	@Override
	public void edit(Long id, TtWrWarrantyDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	  	if( ptdto.getQualityTime()>=24){        		
	        if(ptdto.getQualityMileage()>=50000){
		   TtWrWarrantyPO ptPo = TtWrWarrantyPO.findById(id);
		   ptPo.setDouble("QUALITY_TIME", ptdto.getQualityTime());
		   ptPo.setInteger("QUALITY_MILEAGE",ptdto.getQualityMileage());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.saveIt();
	        }else{
       		 throw new ServiceBizException("质保里程（公里）不能小于  三包责任里程！新增失败！");
       	 }
	  	 }else{
      		 throw new ServiceBizException("质保期限（月）不能小于  三包责任期！新增失败！");
      	}
		
	}
	
	/**
	 * 索赔质保期维护删除
	 */
	@Override
	public void delete(Long id) {
		TtWrWarrantyPO ptPo = TtWrWarrantyPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	}
	
	}
/**
 * 查询所有车系代码
 */
	@Override
	public List<Map> getAll(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimQualityDAO.getAll(queryParam);
	}


}
