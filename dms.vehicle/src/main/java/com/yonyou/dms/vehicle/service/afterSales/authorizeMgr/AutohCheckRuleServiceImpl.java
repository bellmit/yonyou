package com.yonyou.dms.vehicle.service.afterSales.authorizeMgr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.dao.afterSales.authorizeMgr.AutohCheckRuleDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrAutoRuleDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAuthlevelPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrAutoRulePO;

/**
 * 索赔自动审核规则管理
 * @author Administrator
 *
 */
@Service
public class AutohCheckRuleServiceImpl implements AutohCheckRuleService{
	@Autowired
	AutohCheckRuleDao  autohCheckRuleDao;

	/**
	 * 索赔自动审核规则管理查询
	 */
	@Override
	public PageInfoDto AutohCheckRuleQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return autohCheckRuleDao.AuthLevelQuery(queryParam);
	}

	/**
	 * 启动
	 */
	@Override
	public void qidong(Long id) {
		TtWrAutoRulePO ptPo = TtWrAutoRulePO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
			   ptPo.saveIt();
		   	}
		
	}

	/**
	 * 停止
	 */
	@Override
	public void tingzhi(Long id) {
		TtWrAutoRulePO ptPo = TtWrAutoRulePO.findById(id);
		   	if(ptPo!=null){
			   ptPo.setInteger("STATUS",OemDictCodeConstants.STATUS_DISABLE);
			   ptPo.saveIt();
		   	}
		
	}
	/**
	 * 查询所有授权顺序
	 * @param queryParam
	 * @return
	 */
	public List<Map> getAllLevel(Map<String, String> queryParam,Long id) {
		// TODO Auto-generated method stub
		return autohCheckRuleDao.getAllLevel(queryParam,id);
	}
	
	/**
	 * 通过id进行信息回显
	 * 
	 */
	public TtWrAutoRulePO getLevelById(Long id) {
		// TODO Auto-generated method stub
		return TtWrAutoRulePO.findById(id);
	}

	/**
	 * 修改
	 */
	@Override
	public void edit(Long id, TtWrAutoRuleDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtWrAutoRulePO ptPo = TtWrAutoRulePO.findById(id);
		   ptPo.setString("LEVEL_CODE",dto.getLevelCode());
		   
		   TtWrAuthlevelPO  po = new TtWrAuthlevelPO();
		   po = TtWrAuthlevelPO.findFirst("LEVEL_CODE=? ", dto.getLevelCode());
		   String levelDesc=po.get("LEVEL_NAME").toString();
		   
		   ptPo.setString("LEVEL_DESC", levelDesc);
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		   ptPo.saveIt();
	}

}
