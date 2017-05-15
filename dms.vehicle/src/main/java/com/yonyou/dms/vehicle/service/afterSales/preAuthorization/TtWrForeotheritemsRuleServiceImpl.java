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
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.TtWrForeotheritemsRuleDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeotheritemsRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeotheritemsRulePO;

/**
 * 预授权其他费用规则维护
 * @author Administrator
 *
 */
@Service
public class TtWrForeotheritemsRuleServiceImpl implements TtWrForeotheritemsRuleService{
	@Autowired
	TtWrForeotheritemsRuleDao  ttWrForeotheritemsRuleDao;

	// 预授权其他费用规则维护查询
	@Override
	public PageInfoDto TtWrForeotheritemsRuleQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrForeotheritemsRuleDao.TtWrForeotheritemsRuleQuery(queryParam);
	}

	//删除
	@Override
	public void delete(Long id) {
		TtWrForeotheritemsRulePO ptPo = TtWrForeotheritemsRulePO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}

	//修改
	@Override
	public void modifyTtWrForeotheritemsRule(Long id, TtWrForeotheritemsRuleDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrForeotheritemsRulePO   po=TtWrForeotheritemsRulePO.findById(id);
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
//新增
	@Override
	public Long addTtWrForeotheritemsRule(TtWrForeotheritemsRuleDTO ptdto) throws ServiceBizException {
		 if(!CommonUtils.isNullOrEmpty( getForepartRuleBy(ptdto))){
		        throw new ServiceBizException(" 已存在该预授权其他费用规则维护数据！");
		    }
		  else{
				Long id = 0l;
				TtWrForeotheritemsRulePO.delete(" RULE_ID = ? ", ptdto.getRuleId());
				TtWrForeotheritemsRulePO ptPo=new TtWrForeotheritemsRulePO(); 
				//以逗号链接的字符串
				  String result3=null;
			      String seriseRepairTypeStr ="" ;
			  	List<String> list = ptdto.getAuthLevel();
		    	if(null!=list && list.size()>0){
		    		for(int i =0;i<list.size();i++){
		    		
		    			 result3=(String) list.get(i);
		    				if(i ==list.size()-1){
		    					seriseRepairTypeStr += result3;
		    				}else{
		    					seriseRepairTypeStr += result3+",";
		    				}
		    		}
		    		ptPo.setString("AUTH_LEVEL", seriseRepairTypeStr);
	    			setDealerPaymentPo(ptPo,ptdto);
	    			boolean flag = ptPo.saveIt();
	    			id = ptPo.getLongId();
		    	}
		      return id;
		      }
	}
	private void setDealerPaymentPo(TtWrForeotheritemsRulePO ptPo, TtWrForeotheritemsRuleDTO ptdto) {
  		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		ptPo.setString("OTHERITEM_CODE",ptdto.getOtheritemCode());
  		ptPo.setString("OEM_COMPANY_ID",loginInfo.getCompanyId());
          ptPo.setTimestamp("CREATE_DATE", new Date());
          ptPo.setTimestamp("UPDATE_DATE", new Date());
          ptPo.setLong("CREATE_BY", loginInfo.getUserId());
          ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
          ptPo.setInteger("VER", 0);
          ptPo.setInteger("IS_DEL",0);
  	}
	
	// 判断是否存在
	private List<Map> getForepartRuleBy(TtWrForeotheritemsRuleDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT  OTHERITEM_CODE  FROM  TT_WR_FOREOTHERITEMS_RULE_dcs  WHERE 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and OTHERITEM_CODE= ? ");
        params.add(ptdto.getOtheritemCode());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    } 
//通过id查询信息回显
	@Override
	public Map getTtWrForeotheritemsRuleById(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=ttWrForeotheritemsRuleDao.getJiBenXinXi(id);
		   m= list.get(0);
		   return m;
	}

	//得到所有授权级别信息
	@Override
	public List<Map> getLevel() {
		// TODO Auto-generated method stub
		return ttWrForeotheritemsRuleDao.getLevel();
	}
	//查询所有项目代码

	@Override
	public List<Map> getAll(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrForeotheritemsRuleDao.getAll(queryParam);
	}
	

}
