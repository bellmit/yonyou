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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.OtherFeeDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrOtherfeeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrOtherfeePO;

/**
 * 索赔其他费用设定
 * @author Administrator
 *
 */
@Service
public class OtherFeeServiceImpl implements OtherFeeService{
	@Autowired
	OtherFeeDao  otherFeeDao;

	/**
	 * 索赔其他费用设定查询
	 */
	@Override
	public PageInfoDto ClaimTypeQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return otherFeeDao.ClaimTypeQuery(queryParam);
	}
/**
 * 修改时的信息回显
 */
	@Override
	public TtWrOtherfeePO getOtherFeeById(Long id) {
		// TODO Auto-generated method stub
		return TtWrOtherfeePO.findById(id);
	}
	
	/**
	 * 更新
	 * @param id
	 * @param ptdto
	 */
	public void edit(Long id, TtWrOtherfeeDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrOtherfeePO ptPo = TtWrOtherfeePO.findById(id);
		   ptPo.setString("OTHER_FEE_CODE", ptdto.getOtherFeeCode());
		   ptPo.setString("OTHER_FEE_NAME",ptdto.getOtherFeeName());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.setInteger("IS_DOWN",0);
		   ptPo.saveIt();	
	   
	}
	
	/**
	 * 新增
	 */
	public Long add(TtWrOtherfeeDTO ptdto) {
		TtWrOtherfeePO ptPo=new TtWrOtherfeePO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TtWrOtherfeePO ptPo, TtWrOtherfeeDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
		            throw new ServiceBizException("已存在此索赔费用数据！新增失败！");
		        } 
		        else{
		   if(ptdto.getOtherFeeCode()!=null&&ptdto.getOtherFeeName()!=null){
			   ptPo.setLong("OEM_COMPANY_ID", loginInfo.getCompanyId());
			   ptPo.setString("OTHER_FEE_CODE", ptdto.getOtherFeeCode());
			   ptPo.setString("OTHER_FEE_NAME",ptdto.getOtherFeeName());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_DOWN",0);
			   ptPo.saveIt();	
		  
		   }else{
			   throw new ServiceBizException("未填写完整重要信息，请输入！"); 
		   }
		        }
		      
	}
	  public List<Map> getApplyDataBy(TtWrOtherfeeDTO ptdto) throws ServiceBizException {
	        StringBuilder sqlSb = new StringBuilder("  SELECT OTHER_FEE_CODE from TT_WR_OTHERFEE_dcs where 1=1  and  IS_DEL="+OemDictCodeConstants.IS_DEL_00+" \n");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append(" and  OTHER_FEE_CODE=?");
	       
	        params.add(ptdto.getOtherFeeCode());
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return applyList;
	    }
	@Override
	public void delete(Long id) {
		TtWrOtherfeePO ptPo = TtWrOtherfeePO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
			   ptPo.saveIt();
		   	}
		
	}   

}
