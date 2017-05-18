package com.yonyou.dms.customer.service.bigCustomerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerPolicySeriesPO;
import com.yonyou.dms.customer.dao.bigCustomerManage.BigCustomerPolicySeriesDao;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerPolicySeriesDTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 政策车系定义
 * 
 * @author Administrator
 *
 */

@Service
public class BigCustomerPolicySeriesServiceImpl implements BigCustomerPolicySeriesService {
	@Autowired
	BigCustomerPolicySeriesDao policySeriesDao;

	/**
	 * 政策车系定义查询
	 */
	@Override
	public PageInfoDto policySeriesQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return policySeriesDao.policySeriesQuery(queryParam);
	}

	/**
	 * 查询所有品牌代码
	 */
	@Override
	public List<Map> queryBrand() {
		return policySeriesDao.queryBrand();
	}

	/**
	 * 通过品牌代码得到车系编码
	 */
	@Override
	public List<Map> queryChexi(String brandCode, Map<String, String> queryParam) {
		return policySeriesDao.queryChexi(queryParam, brandCode);
	}

	/**
	 * 通过id删除政策车系数据
	 */
	public void deleteChexiById(Long id, TtBigCustomerPolicySeriesDTO ptdto) {
		TtBigCustomerPolicySeriesPO ptPo = TtBigCustomerPolicySeriesPO.findById(id);
		if (ptPo != null) {
			setChexiPo(ptPo, ptdto);
			// ptPo.deleteCascadeShallow();
			ptPo.saveIt();
		}

	}

	private void setChexiPo(TtBigCustomerPolicySeriesPO ptPo, TtBigCustomerPolicySeriesDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		ptPo.setInteger("IS_SCAN", 0);// 未下发
		ptPo.setInteger("IS_DELETE", 1);// 删除
	}

	/**
	 * 通过id 查询回显数据
	 */
	public TtBigCustomerPolicySeriesPO getChexiById(Long id) throws ServiceBizException {
		return TtBigCustomerPolicySeriesPO.findById(id);
	}

	/**
	 * 通过id修改政策车系数据
	 */
	public void modifyChexi(Long id, TtBigCustomerPolicySeriesDTO ptdto) throws ServiceBizException {
		TtBigCustomerPolicySeriesPO ptPo = TtBigCustomerPolicySeriesPO.findById(id);
		setChexishPo(ptPo, ptdto);
		ptPo.saveIt();
	}

	private void setChexishPo(TtBigCustomerPolicySeriesPO ptPo, TtBigCustomerPolicySeriesDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setInteger("STATUS", ptdto.getStatus());
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		ptPo.setInteger("IS_SCAN", 0);// 未下发
	}

	/**
	 * 新增政策车系数据
	 */
	public Long addChexi(TtBigCustomerPolicySeriesDTO ptdto) throws ServiceBizException {
		if (!CommonUtils.isNullOrEmpty(getCheBy(ptdto))) {
			throw new ServiceBizException("已存在此车系数据！");
		} else {
			TtBigCustomerPolicySeriesPO ptPo = new TtBigCustomerPolicySeriesPO();
			setChexisPo(ptPo, ptdto);
			ptPo.saveIt();
			return ptPo.getLongId();
		}
	}

	private void setChexisPo(TtBigCustomerPolicySeriesPO ptPo, TtBigCustomerPolicySeriesDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setInteger("PS_TYPE", ptdto.getPsType());
		ptPo.setString("BRAND_CODE", ptdto.getBrandCode());
		ptPo.setString("SERIES_CODE", ptdto.getSeriesCode());
		ptPo.setInteger("STATUS", ptdto.getStatus());
		ptPo.setInteger("IS_DELETE", 0);// 未删除
		ptPo.setInteger("IS_SCAN", 0);// 未下发
		ptPo.setDate("CREATE_DATE", new Date());
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
	}

	// 通过政策类别，品牌，车系来判断是否可进行新增
	public List<Map> getCheBy(TtBigCustomerPolicySeriesDTO ptdto) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder(
				"  SELECT ps_type,BRAND_CODE,SERIES_CODE FROM  TT_BIG_CUSTOMER_POLICY_SERIES  WHERE 1=1");
		List<Object> params = new ArrayList<>();
		sqlSb.append(" and ps_type=?");
		params.add(ptdto.getPsType());
		sqlSb.append(" and BRAND_CODE=?");
		params.add(ptdto.getBrandCode());
		sqlSb.append(" and SERIES_CODE=?");
		params.add(ptdto.getSeriesCode());
		List<Map> applyList = OemDAOUtil.findAll(sqlSb.toString(), params);
		return applyList;
	}
}
