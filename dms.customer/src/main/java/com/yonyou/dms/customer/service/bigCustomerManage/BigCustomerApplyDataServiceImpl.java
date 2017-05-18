package com.yonyou.dms.customer.service.bigCustomerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerApplyDataPO;
import com.yonyou.dms.customer.dao.bigCustomerManage.BigCustomerApplyDataDao;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerApplyDataDTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 政策申请数据定义
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("rawtypes")
@Service
public class BigCustomerApplyDataServiceImpl implements BigCustomerApplyDataService {

	@Autowired
	BigCustomerApplyDataDao applyDataDao;

	/**
	 * 申请数据定义查询
	 */
	@Override
	public PageInfoDto applyDateQuery(Map<String, String> queryParam) {

		return applyDataDao.applyDateQuery(queryParam);
	}

	/**
	 * 通过政策类别查询客户类型
	 */
	@Override
	public List<Map> getEmpType(String type, Map<String, String> queryParams) throws ServiceBizException {
		return applyDataDao.getEmpType(type, queryParams);

	}

	/**
	 * 通过id删除申请数据
	 * 
	 * @param id
	 */
	public void deleteApplyDataById(Long id, TtBigCustomerApplyDataDTO ptdto) {
		TtBigCustomerApplyDataPO ptPo = TtBigCustomerApplyDataPO.findById(id);
		if (ptPo != null) {
			setApplyDaPo(ptPo, ptdto);
			// ptPo.deleteCascadeShallow();
			ptPo.saveIt();
		}

	}

	private void setApplyDaPo(TtBigCustomerApplyDataPO ptPo, TtBigCustomerApplyDataDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		ptPo.setInteger("IS_SCAN", 0);// 未下发
		ptPo.setInteger("IS_DELETE", 1);// 删除
	}

	/**
	 * 通过id查询回显申请数据
	 */
	@Override
	public TtBigCustomerApplyDataPO getApplyDataById(Long id) throws ServiceBizException {
		return TtBigCustomerApplyDataPO.findById(id);
	}

	/**
	 * 通过id修改申请数据
	 */
	public void modifyApplyData(Long id, TtBigCustomerApplyDataDTO ptdto) throws ServiceBizException {
		TtBigCustomerApplyDataPO ptPo = TtBigCustomerApplyDataPO.findById(id);
		setApplyDataPo(ptPo, ptdto);
		ptPo.saveIt();
	}

	private void setApplyDataPo(TtBigCustomerApplyDataPO ptPo, TtBigCustomerApplyDataDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setInteger("NUMBER", ptdto.getNumber());
		ptPo.setInteger("STATUS", ptdto.getStatus());
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		ptPo.setInteger("IS_SCAN", 0);// 未下发
	}

	/**
	 * 新增申请数据
	 *
	 */
	public Long addApplyData(TtBigCustomerApplyDataDTO ptdto) throws ServiceBizException {
		if (!CommonUtils.isNullOrEmpty(getApplyDataBy(ptdto))) {
			throw new ServiceBizException("已存在此申请数据！");
		} else {
			TtBigCustomerApplyDataPO ptPo = new TtBigCustomerApplyDataPO();
			setApplyPo(ptPo, ptdto);
			ptPo.saveIt();
			return ptPo.getLongId();
		}
	}

	private void setApplyPo(TtBigCustomerApplyDataPO ptPo, TtBigCustomerApplyDataDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		ptPo.setInteger("PS_TYPE", ptdto.getPsType());
		ptPo.setInteger("EMPLOYEE_TYPE", ptdto.getEmployeeType());
		ptPo.setInteger("NUMBER", ptdto.getNumber());
		ptPo.setInteger("STATUS", ptdto.getStatus());
		ptPo.setInteger("IS_DELETE", 0);// 未删除
		ptPo.setInteger("IS_SCAN", 0);// 未下发
		ptPo.setDate("CREATE_DATE", new Date());
		ptPo.setDate("UPDATE_DATE", new Date());
		ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
	}

	/**
	 * 通过政策类别和客户类型进行查询是否已经存在该申请数据
	 * 
	 */

	public List<Map> getApplyDataBy(TtBigCustomerApplyDataDTO ptdto) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder(
				"  SELECT ps_type,employee_type FROM  TT_BIG_CUSTOMER_APPLY_DATA  WHERE 1=1");
		List<Object> params = new ArrayList<>();
		sqlSb.append(" and ps_type=?");
		params.add(ptdto.getPsType());
		sqlSb.append(" and employee_type=?");
		params.add(ptdto.getEmployeeType());
		List<Map> applyList = OemDAOUtil.findAll(sqlSb.toString(), params);
		return applyList;
	}

}