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
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.TtWrForelabourRuleDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForelabourRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForelabourRulePO;

/**
 * 索赔预授权工时规则维护
 * @author Administrator
 *
 */
@Service
public class TtWrForelabourRuleServiceImpl implements TtWrForelabourRuleService{
	@Autowired
	TtWrForelabourRuleDao  ttWrForelabourRuleDao;

	/**
	 * 索赔预授权工时规则维护查询
	 */
	@Override
	public PageInfoDto TtWrForelabourRuleQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return ttWrForelabourRuleDao.TtWrForelabourRuleQuery(queryParam);
	}
	
	/**
	 * 查询所有授权顺序
	 * @param queryParam
	 * @return
	 */
	public List<Map> getAllLevel(String labourCode) {
		// TODO Auto-generated method stub
		return ttWrForelabourRuleDao.getAllLevel(labourCode);
	}
	
	public List<Map> getLevel() {
		// TODO Auto-generated method stub
		return ttWrForelabourRuleDao.getLevel();
	}
	
	/**
	 * 删除
	 */
	public void delete(Long id) {
		TtWrForelabourRulePO ptPo = TtWrForelabourRulePO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
		
	}

	/**
	 * 信息回显
	 */
	public  Map getForelabourRuleById(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
		 List<Map> list=ttWrForelabourRuleDao.getJiBenXinXi(id);
		   m= list.get(0);
		   return m;
	}

	/**
	 * 预授权工时规则维护新增
	 */
	@Override
	public Long addForelabourRule(TtWrForelabourRuleDTO ptdto) throws ServiceBizException {
		 if(!CommonUtils.isNullOrEmpty( getDealerPaymentBy(ptdto))){
        throw new ServiceBizException("已存在预授权工时规则维护数据！");
    }
  else{
		Long id = 0l;
		TtWrForelabourRulePO.delete(" RULE_ID = ? ", ptdto.getRuleId());
		
		//以逗号链接的字符串
		  String result3=null;
	      String seriseRepairTypeStr ="" ;
    	List<String> list = ptdto.getAuthLevel();
    	if(null!=list && list.size()>0){
    		for(int i =0;i<list.size();i++){
    			TtWrForelabourRulePO ptPo=new TtWrForelabourRulePO(); 
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
	/**
	 * 判断是否存在
	 * @param ptdto
	 * @return
	 * @throws ServiceBizException
	 */
	private List<Map> getDealerPaymentBy(TtWrForelabourRuleDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT  labour_code  FROM  TT_WR_FORELABOUR_RULE_dcs  WHERE 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and labour_code= ? ");
        params.add(ptdto.getLabourCode());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    } 
	
	
	private void setDealerPaymentPo(TtWrForelabourRulePO ptPo, TtWrForelabourRuleDTO ptdto) {
  		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		ptPo.setString("LABOUR_CODE",ptdto.getLabourCode());
  		ptPo.setLong("MODEL_GROUP",ptdto.getModelGroup());
  		ptPo.setString("OEM_COMPANY_ID",loginInfo.getCompanyId());
          ptPo.setTimestamp("CREATE_DATE", new Date());
          ptPo.setTimestamp("UPDATE_DATE", new Date());
          ptPo.setLong("CREATE_BY", loginInfo.getUserId());
          ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
          ptPo.setInteger("VER", 0);
          ptPo.setInteger("IS_DEL",0);
  	}

	/**
	 * 预授权工时规则维护修改
	 */
	public void modifyTtWrForelabourRule(Long id, TtWrForelabourRuleDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrForelabourRulePO   po=TtWrForelabourRulePO.findById(id);
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

}
