package com.yonyou.dms.retail.service.rebate;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
/**
 * 
* @ClassName: RebateDetailService 
* @Description: 经销商返利核算汇总查询（OEM)
* @author zhengzengliang 
* @date 2017年4月26日 下午8:05:36 
*
 */
@SuppressWarnings("rawtypes")
public interface RebateDetailService {
	PageInfoDto getRebateDetail( String logId,String dealerCode,Map<String, String> queryParam);

	List<Map> queryEmpInfoforExport(Map<String, String> queryParam)throws Exception;

	/**
	 * 返利核算明细查询
	 * @param logId
	 * @param dealerCode
	 * @param queryParam
	 * @return
	 */
	PageInfoDto getRebateDetail1(Map<String, String> queryParam);
	
	/**
	 * 返利核算明细导出
	 * @param queryParam
	 * @return
	 * @throws Exception
	 */
	List<Map> queryEmpInfoforExport1(Map<String, String> queryParam)throws Exception;
	
	/**
	 * 返利核算汇总查询(DLR)明细
	 * @param logId
	 * @param dealerCode
	 * @param queryParam
	 * @return
	 */
	PageInfoDto getRebateDetail2(String logId,String dealerCode,Map<String, String> queryParam);
	
}
