package com.yonyou.dms.retail.service.rebate;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.rebate.TtRebateCalculateTmpDTO;
import com.yonyou.dms.retail.domains.PO.rebate.RebateManagePO;
/**
 * 
* @ClassName: RebateManageService 
* @Description: 返利核算管理
* @author zhengzengliang 
* @date 2017年3月29日 下午3:28:34 
*
 */
@SuppressWarnings("rawtypes")
public interface RebateManageService {

	PageInfoDto getRebateManage(Map<String, String> queryParam)throws ServiceBizException;

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws ServiceBizException;

	public void deleteTtRebateCalculateTmp()throws ServiceBizException;

	public void insertTmprebateCalculate(TtRebateCalculateTmpDTO rowDto) throws ServiceBizException;

	public ImportResultDto<TtRebateCalculateTmpDTO> checkData(LoginInfoDto loginInfo) throws ServiceBizException;

	public List<Map> findTmpList(LoginInfoDto loginInfo) throws ServiceBizException;

	public void deleteDyn(String businessPolicyName, String startMonth, String endMonth) throws ServiceBizException;

	public void deleteSta(String businessPolicyName, String startMonth, String endMonth) throws ServiceBizException;

	public List<RebateManagePO> selectRebateManage(String businessPolicyName, String startMonth,
			String endMonth) throws ServiceBizException;

	public void insertRebateStat(Long logId, LoginInfoDto loginInfo) throws ServiceBizException;

	public void P_REBATE_CAL_DYN(LoginInfoDto loginInfo) throws ServiceBizException;

	public void insertRebateManage(String businessPolicyName, String businessPolicyType, LoginInfoDto loginInfo,
			String uploadWay, Long logId, String startMonth, String endMonth) throws ServiceBizException;

	public List<Map> selectTtRebateCalculateManage(String logId) throws ServiceBizException;

	public List<Map> selectReUploadStartMonth(String logId) throws ServiceBizException;

	public List<Map> selectReUploadEndYearList(String logId) throws ServiceBizException;

	public List<Map> selectReUploadEndMonthList(String logId) throws ServiceBizException;

	public List<Map> selectReUploadgetBusinessPolicyName(String logId) throws ServiceBizException;

	public List<Map> selectReUploadgetgetBusinessPolicyType(String logId) throws ServiceBizException;

}
