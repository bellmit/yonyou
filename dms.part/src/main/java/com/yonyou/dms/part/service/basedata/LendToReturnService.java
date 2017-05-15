/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.LendToReturnDTO;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface LendToReturnService {

	/**
	 * 查询所有借出单号
	 * @param query
	 * @return
	 */
	PageInfoDto findAllList(Map<String, String> query);

	/**
	 * 根据借出单号查询明细
	 * @param id
	 * @return
	 */
	PageInfoDto findAllDetails(String id,Map<String, String> queryParam);

	/**
	 * 归还入账操作
	 * @param dto
	 */
	String operate(LendToReturnDTO dto);

	/**
	 * 主页根据借出单号查询明细
	 * @param id
	 * @param queryParam
	 * @return
	 */
	List<Map> findAllDetailsForLocale(String id, Map<String, String> queryParam);

	/**
	 * 查询仓库代码
	 * @return
	 */
	List<Map> findStorageCode();

}
