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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.ClaimPartDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrPartwarrantyDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrPartwarrantyPO;

/**
 * 索赔配件质保期维护
 * @author Administrator
 *
 */
@Service
public class ClaimPartServiceImpl implements ClaimPartService{
	@Autowired
	ClaimPartDao  claimPartDao;
	/**
	 * 索赔配件质保期维护查询
	 */
	@Override
	public PageInfoDto ClaimQualityQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimPartDao.ClaimQualityQuery(queryParam);
	}
	/**
	 * 修改信息回显
	 */
	public TtWrPartwarrantyPO getClaimPartById(Long id) {
		// TODO Auto-generated method stub
		return TtWrPartwarrantyPO.findById(id);
	}
	/**
	 * 查询所有配件代码
	 */
	@Override
	public List<Map> getAll(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimPartDao.getAll(queryParam);
	}
	/**
	 * 修改配件信息
	 */
	public void edit(Long id, TtWrPartwarrantyDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
	            throw new ServiceBizException("已存在此配件质保期数据！修改失败！");
	        } else{
		TtWrPartwarrantyPO ptPo = TtWrPartwarrantyPO.findById(id);
		   ptPo.setString("PART_CODE", ptdto.getPartCode());
		   ptPo.setString("PART_NAME",ptdto.getPartName());
		   ptPo.setInteger("QUALITY_TIME", ptdto.getQualityTime());
		   ptPo.setDouble("QUALITY_MILEAGE",ptdto.getQualityMileage());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();	
	        }
	}
	/**
	 * 删除
	 */
	@Override
	public void delete(Long id) {
		TtWrPartwarrantyPO ptPo = TtWrPartwarrantyPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
		
	}
	
	}
	
	/**
	 * 新增
	 */
	public Long add(TtWrPartwarrantyDTO ptdto) {
		TtWrPartwarrantyPO ptPo=new TtWrPartwarrantyPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TtWrPartwarrantyPO ptPo, TtWrPartwarrantyDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
		            throw new ServiceBizException("已存在此配件质保期数据！新增失败！");
		        } 
		        else{
		   if(ptdto.getQualityTime()!=null&&ptdto.getQualityMileage()!=null){
			   ptPo.setString("PART_CODE", ptdto.getPartCode());
			   ptPo.setString("PART_NAME",ptdto.getPartName());
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
	  public List<Map> getApplyDataBy(TtWrPartwarrantyDTO ptdto) throws ServiceBizException {
	        StringBuilder sqlSb = new StringBuilder("  SELECT part_code from TT_WR_PARTWARRANTY_dcs where 1=1");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append(" and  part_code=?  and is_del=0 ");
	        params.add(ptdto.getPartCode());
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return applyList;
	    }   


	
	
	
	

}
