package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

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
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.TtWrForepartRuleDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForepartRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForepartRulePO;

/**
 * 预授权配件规则维护
 * @author Administrator
 *
 */
@Service
public class TtWrForepartRuleServiceImpl implements TtWrForepartRuleService{
	@Autowired
	TtWrForepartRuleDao  ttWrForepartRuleDao;

	//预授权配件规则维护查询
	@Override
	public PageInfoDto TtWrForepartRuleQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrForepartRuleDao.TtWrForepartRuleQuery(queryParam);
	}
   //删除
	@Override
	public void delete(Long id) {
		TtWrForepartRulePO ptPo = TtWrForepartRulePO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}
	
	//新增
	@Override
	public Long addForepartRuleRule(TtWrForepartRuleDTO ptdto) throws ServiceBizException {
		 if(!CommonUtils.isNullOrEmpty( getForepartRuleBy(ptdto))){
		        throw new ServiceBizException(" 已存在该预授权配件规则维护数据！");
		    }
		  else{
				Long id = 0l;
				TtWrForepartRulePO.delete(" RULE_ID = ? ", ptdto.getRuleId());
				
				//以逗号链接的字符串
				  String result3=null;
			      String seriseRepairTypeStr ="" ;
			  	List<String> list = ptdto.getAuthLevel();
		    	if(null!=list && list.size()>0){
		    		for(int i =0;i<list.size();i++){
		    			TtWrForepartRulePO ptPo=new TtWrForepartRulePO(); 
		    			 result3=(String) list.get(i);
		    				if(i ==list.size()-1){
		    					seriseRepairTypeStr += result3;
		    				}else{
		    					seriseRepairTypeStr += result3+",";
		    				}
		    			ptPo.setString("AUTH_LEVEL", seriseRepairTypeStr);
		    			setDealerPaymentPo(ptPo,ptdto);
		    			boolean flag = ptPo.saveIt();
		    			id = ptPo.getLongId();
		    		}
		    	}
		      return id;
		      }
	}
	private void setDealerPaymentPo(TtWrForepartRulePO ptPo, TtWrForepartRuleDTO ptdto) {
  		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		ptPo.setString("PART_CODE",ptdto.getPartCode());
  		ptPo.setString("OEM_COMPANY_ID",loginInfo.getCompanyId());
          ptPo.setTimestamp("CREATE_DATE", new Date());
          ptPo.setTimestamp("UPDATE_DATE", new Date());
          ptPo.setLong("CREATE_BY", loginInfo.getUserId());
          ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
          ptPo.setInteger("VER", 0);
          ptPo.setInteger("IS_DEL",0);
  	}
	
	// 判断是否存在
	private List<Map> getForepartRuleBy(TtWrForepartRuleDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT  part_code  FROM  TT_WR_FOREPART_RULE_dcs  WHERE 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and part_code= ? ");
        params.add(ptdto.getPartCode());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    } 
	
	//修改
	@Override
	public void modifyTtWrForepartRule(Long id, TtWrForepartRuleDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrForepartRulePO   po=TtWrForepartRulePO.findById(id);
		  String result=null;
	      String ruleStr ="" ;
		List<String> list = ptdto.getAuthLevel();
		if(null!=list && list.size()>0){
    		for(int i =0;i<list.size();i++){
    			 result=(String) list.get(i);
    				if(i ==list.size()-1){
    					ruleStr += result;
    				}else{
    					ruleStr += result+",";
    				}
		 po.setString("AUTH_LEVEL", ruleStr);
	     po.setTimestamp("UPDATE_DATE", new Date());
	     po.setLong("UPDATE_BY", loginInfo.getUserId());
		 po.saveIt();
    		}
		}
		
	}
	
	//信息回显
	@Override
	public Map getForepartRuleById(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=ttWrForepartRuleDao.getJiBenXinXi(id);
		   m= list.get(0);
		   return m;
	}
	@Override
	public List<Map> getLevel() {
		// TODO Auto-generated method stub
		return ttWrForepartRuleDao.getLevel();
	}

}
