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
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.TtWrLabourDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrLabourDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrLabourPO;

/**
 * 索赔工时维护
 * @author Administrator
 *
 */
@Service
public class TtWrLabourServiceImpl  implements TtWrLabourService{
	@Autowired
	TtWrLabourDao  ttWrLabourDao;
	
	/**
	 * 查询所有车系集合
	 */
	
	@Override
	public List<Map> getlabour(Map<String, String> queryParams) {
		// TODO Auto-generated method stub
		return ttWrLabourDao.getLabour(queryParams);
	}
   /**
    * 索赔工时维护查询
    */
	@Override
	public PageInfoDto LabourQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrLabourDao.LabourQuery(queryParam);
	}
	
	/**
	 * 删除索赔工时维护
	 */
		@Override
		public void delete(Long id) {
			TtWrLabourPO ptPo = TtWrLabourPO.findById(id);
			   	if(ptPo!=null){
				   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
				   ptPo.saveIt();	
	}
		}
	/**
	 * 通过id修改索赔工时维护时的回显信息
	 */

	@Override
	public TtWrLabourPO getLabourById(Long id) {
		// TODO Auto-generated method stub
		return TtWrLabourPO.findById(id);
	}
	/**
	 * 通过id进行修改索赔工时维护
	 */
	@Override
	public void edit(Long id, TtWrLabourDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrLabourPO   po=TtWrLabourPO.findById(id);
		 po.setString("SKILL_CATEGORY", dto.getSkillCategory());
		 po.setDouble("RAW_SUBSIDY", dto.getRawSubsidy());
		 po.setString("LABOUR_TYPE", dto.getLabourType());
		 po.setDouble("LABOUR_NUM", dto.getLabourNum());
		 //得到groupCode
		 po.setString("GROUP_CODE", dto.getGroupCode1());
		 po.setLong("UPDATE_BY", loginInfo.getUserId());
		 po.setDate("UPDATE_DATE", new Date());
		 po.setInteger("IS_DOWN",0);
		 po.setInteger("VER",0);
		 po.setInteger("IS_DEL",0);		 
		 List<Map> list2=getGroupId(dto.getGroupCode1());
		  Map  m= list2.get(0);
		  Long group_id = (Long) m.get("group_id");
		  po.setLong("GROUP_ID", group_id);	
		  po.saveIt();

	}
	@Override
	public List<Map> getGroupId(String group_code) throws ServiceBizException {
		// TODO Auto-generated method stub
		return ttWrLabourDao.getGroupId(group_code);
	}

	
	
	  public void addLabour(TtWrLabourDTO ptdto) {
		   if(ptdto.getLabourCode()!=null&&ptdto.getLabourName()!=null&&ptdto.getLabourNum()!=null){
			   //判断工时代码和车系代码是否存在
		   if(!CommonUtils.isNullOrEmpty(getBy(ptdto))){
			   
		              throw new ServiceBizException("工时代码:"+ptdto.getLabourCode()+"和车系代码:"+ptdto.getGroupCode()+"已经存在！");
		          }else{
				Long id = 0l;
				TtWrLabourPO.delete(" ID = ? ", ptdto.getId());
	        	List<String> list = ptdto.getGroupCode();
	        	if(null!=list && list.size()>0){
	        		for(int i =0;i<list.size();i++){	
		        TtWrLabourPO ptPo=new TtWrLabourPO();
		        ptPo.setString("GROUP_CODE", list.get(i));
			    setApplyPo(ptPo,ptdto);
	        		}
	        	}
	        	}
		   }
	}
		private void setApplyPo(TtWrLabourPO ptPo, TtWrLabourDTO ptdto) {
			   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				   ptPo.setString("LABOUR_CODE", ptdto.getLabourCode());
				   ptPo.setString("LABOUR_NAME", ptdto.getLabourName()); 
				   ptPo.setString("SKILL_CATEGORY", ptdto.getSkillCategory());
				   ptPo.setDouble("RAW_SUBSIDY", ptdto.getRawSubsidy());
				   ptPo.setString("LABOUR_TYPE", ptdto.getLabourType());
				   ptPo.setDouble("LABOUR_NUM", ptdto.getLabourNum());
				   List<Map> list2=getGroupId(ptdto.getGroupCode().get(0));
				   Map m= list2.get(0);
				   ptPo.setLong("GROUP_ID", m.get("GROUP_ID"));
				   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
				   ptPo.setDate("UPDATE_DATE", new Date());
				   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
				   ptPo.setDate("CREATE_DATE", new Date());  
				   ptPo.setInteger("VER",0);
				   ptPo.setInteger("IS_DEL",0);
				   ptPo.setInteger("IS_DOWN",0);
				   ptPo.saveIt(); 
		}
		
	    public List<Map> getBy(TtWrLabourDTO ptdto) throws ServiceBizException {
	    	List<Map> applyList=null;
	        List<String> list = ptdto.getGroupCode();
        	if(null!=list && list.size()>0){
        		for(int i =0;i<list.size();i++){	
        	    StringBuilder sqlSb = new StringBuilder(" 	SELECT labour_code,group_code FROM TT_WR_LABOUR_DCS WHERE 1=1 ");
                List<Object> params = new ArrayList<>();
	           sqlSb.append("  AND group_code= ? ");
	           params.add(list.get(i));
	            sqlSb.append("  and labour_code= ? ");
		        params.add(ptdto.getLabourCode());
	            applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
	            System.out.println(sqlSb);
	            if(applyList != null && applyList.size() > 0){
	            	return applyList;
	            }
	            }
        	    	}
	        return null;
	    }
}
