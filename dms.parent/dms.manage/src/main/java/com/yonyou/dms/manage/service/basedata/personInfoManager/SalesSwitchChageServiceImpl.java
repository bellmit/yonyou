package com.yonyou.dms.manage.service.basedata.personInfoManager;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtVsBasicParaPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.dao.personInfoManager.SalesSwitchChageDao;


/**
 * 经销商零售开关设定
 * @author Administrator
 *
 */
@Service
public class SalesSwitchChageServiceImpl implements SalesSwitchChageService{
	@Autowired
	SalesSwitchChageDao  salesSwitchChageDao;
	
	@Override
	public PageInfoDto salesSwitchChangeQuery(Map<String, String> queryParam) {
		 
			return salesSwitchChageDao.salesSwitchChangeQuery(queryParam);
	}
	/**
	 * 获取大区信息
	 */
	@Override
	public List<Map> getBigOrg(Map<String, String> queryParams) throws ServiceBizException {
	        return  salesSwitchChageDao.getBigOrg(queryParams);
	}
	
	/**
	 * 获取小区信息
	 */
	@Override
	public List<Map> getSmallOrg(String bigorgid,Map<String, String> queryParams) throws ServiceBizException {
	          return  salesSwitchChageDao.getSmallOrg(bigorgid, queryParams);
	     }
	
	/**
	 * 获取开关开启经销商
	 */
	@Override
	public List<Map> getOrgLeft() {
		return salesSwitchChageDao.getOrgLeft(null,null,null,null);
	}
	
	/**
	 * 获取开关关闭经销商
	 */
	@Override
	public List<Map> getOrgRight() {
		return salesSwitchChageDao.getOrgRight(null,null,null,null);
	}
	
	@Override
	public String searchOrg(Map<String, String> param) {
		String dealerCode = param.get("dealerCode");
		String dealerName = param.get("dealerName");
		String orgId1 = param.get("bigOrgName");
		String orgId2 = param.get("smallOrgName");
		String org = "";
		if(StringUtils.isNullOrEmpty(dealerCode) && StringUtils.isNullOrEmpty(dealerName) && 
				StringUtils.isNullOrEmpty(orgId1) && StringUtils.isNullOrEmpty(orgId2)){
				//四个查询条件均为空  返回 ‘’	
		}else{			
			List<Map> list1 = salesSwitchChageDao.getOrgLeft(dealerCode, dealerName, orgId1, orgId2);
			List<Map> list2 = salesSwitchChageDao.getOrgRight(dealerCode, dealerName, orgId1, orgId2);
			if(list1 != null && !list1.isEmpty()){			
				for(Map map : list1){
					org += map.get("code") + ",";
				}
			}
			if(list2 != null && !list2.isEmpty()){
				for(Map map : list2){
					org += map.get("code") + ",";
				}
			}
			if(!StringUtils.isNullOrEmpty(org)){
				org = org.substring(0, org.length() -1);
			}
		}
		return org;
	}
	
	@Override
	public void saveChange(String ids) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		boolean flag = false;
		try {			
			TtVsBasicParaPO.updateAll("FAST_RETAIL_STATUS = ? ", OemDictCodeConstants.FAST_RETAIL_STATUS_02);
			if(!"-1".equals(ids)){
				String[] dealerIds = ids.split(",");
				for(String dealerId : dealerIds){
					List<TtVsBasicParaPO> list = TtVsBasicParaPO.find("DEALER_ID = ? ", dealerId);
					if(list != null && !list.isEmpty()){
						for(int i = 0; i < list.size(); i++){
							TtVsBasicParaPO po = list.get(i);
							po.setInteger("FAST_RETAIL_STATUS", OemDictCodeConstants.FAST_RETAIL_STATUS_01);
							po.setLong("UPDATE_BY", loginInfo.getUserId());
							po.setTimestamp("UPDATE_DATE", currentTime);
							flag = po.saveIt();
						}
					}else{
						TtVsBasicParaPO po = new TtVsBasicParaPO();
						po.setInteger("FAST_RETAIL_STATUS", OemDictCodeConstants.FAST_RETAIL_STATUS_01);
						po.setLong("DEALER_ID", Long.parseLong(dealerId));
						po.setLong("CREATE_BY", loginInfo.getUserId());
						po.setTimestamp("CREATE_DATE", currentTime);
						flag = po.saveIt();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("保存失败！");
		}
		
	}

}