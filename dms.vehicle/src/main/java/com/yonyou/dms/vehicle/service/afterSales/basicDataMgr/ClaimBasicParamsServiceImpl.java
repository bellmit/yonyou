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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.ClaimBasicParamsDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrBasicParaDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrBasicParaPO;

/**
 * 索赔基本参数设定
 * @author Administrator
 *
 */
@Service
public class ClaimBasicParamsServiceImpl implements ClaimBasicParamsService{
	@Autowired
	ClaimBasicParamsDao   claimBasicParamsDao;

	/**
	 * 索赔基本参数设定查询
	 */
	@Override
	public PageInfoDto BasicParamsQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return claimBasicParamsDao.BasicParamsQuery(queryParam);
	}
	/**
	 * 索赔基本参数设定新增
	 */
	  public Long addBasicParams(TtWrBasicParaDTO ptdto) {
		  TtWrBasicParaPO ptPo=new TtWrBasicParaPO();
			    setApplyPo(ptPo,ptdto);
			    return ptPo.getLongId();
	}
		private void setApplyPo(TtWrBasicParaPO ptPo, TtWrBasicParaDTO ptdto) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			   if(!CommonUtils.isNullOrEmpty( getCheBy(ptdto))){
		              throw new ServiceBizException("该经销商不能重复新增！");
		          }else{
			   if(ptdto.getDealerId()!=null&&ptdto.getLabourPrice()!=null&&ptdto.getTaxRate()!=null&&ptdto.getPartMangefee()!=null){
				   ptPo.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
				   ptPo.setLong("DEALER_ID", ptdto.getDealerId());
				   ptPo.setDouble("LABOUR_PRICE",ptdto.getLabourPrice());
				   ptPo.setDouble("TAX_RATE",ptdto.getTaxRate());
				   ptPo.setDouble("PART_MANGEFEE", ptdto.getPartMangefee());
				   ptPo.setInteger("VALID_DAYS", ptdto.getValidDays());
				   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
				   ptPo.setDate("UPDATE_DATE", new Date());  
				   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
				   ptPo.setDate("CREATE_DATE", new Date());  
				   ptPo.setInteger("VER",0);
				   ptPo.setInteger("IS_DEL",0);
				   ptPo.setInteger("IS_DOWN",0);
				   ptPo.saveIt();
			   }else{
				   throw new ServiceBizException("未填写完整信息，请输入！"); 
			   }
		          }
		}
		/**
		 * 查询，不能重复新增
		 */
	    public List<Map> getCheBy(TtWrBasicParaDTO ptdto) throws ServiceBizException {
	        StringBuilder sqlSb = new StringBuilder(" 	SELECT dealer_id FROM TT_WR_BASIC_PARA_DCS WHERE 1=1");
	        List<Object> params = new ArrayList<>();
	        sqlSb.append("  and dealer_id= ? and IS_DEL=0 ");
	        params.add(ptdto.getDealerId());
	        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	        return applyList;
	    }
	/*	@Override
		public TtWrBasicParaPO getBasicParamsById(Long id) {
			// TODO Auto-generated method stub
			return TtWrBasicParaPO.findById(id);
		} */
	    
	    public Map  getShuju(Long id){
	    	 Map<String, Object> m=new HashMap<String, Object>();
	    	 List<Map> list=claimBasicParamsDao.getShuju(id);
	    	 m= list.get(0);
	    	 /*	 for (Map map : list) {	
	   	m.put("DEALER_CODE", map.get("DEALER_CODE"));
	    		m.put("DEALER_SHORTNAME", map.get("DEALER_SHORTNAME"));
	    		m.put("LABOUR_PRICE", map.get("LABOUR_PRICE"));
	    		m.put("TAX_RATE", map.get("TAX_RATE"));
	    		m.put("PART_MANGEFEE", map.get("PART_MANGEFEE"));
	    		m.put("VALID_DAYS", map.get("VALID_DAYS"));
	    	 }*/
	    	   return m;
	    }
	    
	    
		/**
		 * 修改
		 * @param id
		 * @param ptdto
		 */
		@Override
		public void edit(Long id,TtWrBasicParaDTO ptdto) {
		 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		 TtWrBasicParaPO ptPo = TtWrBasicParaPO.findById(id);
	       ptPo.setDouble("LABOUR_PRICE",ptdto.getLabourPrice());
		   ptPo.setDouble("TAX_RATE",ptdto.getTaxRate());
		   ptPo.setDouble("PART_MANGEFEE", ptdto.getPartMangefee());
		   ptPo.setInteger("VALID_DAYS", ptdto.getValidDays());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());  
		   ptPo.setInteger("VER",0);
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.setInteger("IS_DOWN",0);
		   ptPo.saveIt();
		}
		/**
		 * 删除
		 */
		public void delete(Long id) {
			TtWrBasicParaPO ptPo = TtWrBasicParaPO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("IS_DEL",1);
			   ptPo.saveIt();
		   	}
		}
		/**
		 * 下发(接口未写)
		 */
		@Override
		public void getAll() {
			
	/*		String retrunMsg = null;
			// 调用下发接口 Start
			CLDCS010 osc = new CLDCS010();
			retrunMsg = osc.sendAllData(null);
			// 调用下发接口 End
			if (retrunMsg == null) {*/
		List<Map>	 list=claimBasicParamsDao.getAll();
		 for (Map mapList : list) {
			 TtWrBasicParaPO ptPo =new TtWrBasicParaPO();
			 ptPo.setLong("PARA_ID",mapList.get("PARA_ID"));
			  ptPo.setInteger("IS_DOWN",OemDictCodeConstants.IS_DOWN_01);
			 ptPo.saveIt();
		 }
	//	}
		}

}
