package com.yonyou.dms.manage.service.basedata.dealer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 经销商弹出框
 * 
 * @author 夏威
 *
 */
@Service
public class SearchDealersServiceImpl implements SearchDealersService {

	/**
	 * 经销商查询方法
	 */
	@Override
	public List<Map> getDealerList(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT COMPANY_ID,DEALER_ID,DEALER_CODE,DEALER_SHORTNAME,MARKETING,DEALER_NAME,STATUS,DEALER_LEVEL,ADDRESS,DEALER_TYPE   ");
		sql.append(" FROM tm_dealer WHERE STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " ");
		if (loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)) {
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DWR);
		} else {
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DVS);
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
			sql.append(" and DEALER_CODE like ?");
			params.add("%" + queryParams.get("dealerCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))) {
			sql.append(" and DEALER_SHORTNAME like ? ");
			params.add("%" + queryParams.get("dealerShortname") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
			sql.append(" and DEALER_NAME like ? ");
			params.add("%" + queryParams.get("dealerName") + "%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.findAll(sql.toString(), params);
	}

	@Override
	public List<Map> searchCheckDealer(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(
				" SELECT COMPANY_ID,DEALER_ID,DEALER_CODE CHECK_CODE,DEALER_SHORTNAME,MARKETING,DEALER_NAME,STATUS,DEALER_LEVEL,ADDRESS,DEALER_TYPE   ");
		sql.append(" FROM tm_dealer WHERE STATUS = " + OemDictCodeConstants.STATUS_ENABLE + " ");
		if (loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)) {
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DWR);
		} else {
			sql.append(" AND DEALER_TYPE  =  ? ");
			params.add(OemDictCodeConstants.DEALER_TYPE_DVS);
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
			sql.append(" and DEALER_CODE like ?");
			params.add("%" + queryParams.get("dealerCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))) {
			sql.append(" and DEALER_SHORTNAME like ? ");
			params.add("%" + queryParams.get("dealerShortname") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))) {
			sql.append(" and DEALER_NAME like ? ");
			params.add("%" + queryParams.get("dealerName") + "%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.findAll(sql.toString(), params);
	}

	/**
	 * 售后经销商
	 */
	@Override
	public PageInfoDto getDealerShouHouList(Map<String, String> queryParams) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COMPANY_ID,DEALER_ID,DEALER_CODE,DEALER_SHORTNAME,DEALER_NAME,STATUS,DEALER_LEVEL,ADDRESS,DEALER_TYPE   ");
		sql.append(" FROM tm_dealer WHERE STATUS = "+ OemDictCodeConstants.STATUS_ENABLE + " ");
		sql.append(" AND DEALER_TYPE  =  '10771002' ");
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sql.append(" and DEALER_CODE like ?");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerShortname"))){
			sql.append(" and DEALER_SHORTNAME like ? ");
			params.add("%"+queryParams.get("dealerShortname")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))){
			sql.append(" and DEALER_NAME like ? ");
			params.add("%"+queryParams.get("dealerName")+"%");
		}
		System.out.println(sql.toString() + params.toString());
		return OemDAOUtil.pageQuery(sql.toString(),params);
	}

}
