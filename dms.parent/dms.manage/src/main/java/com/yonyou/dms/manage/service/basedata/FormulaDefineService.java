/**
 * 
 */
package com.yonyou.dms.manage.service.basedata;

import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.FormulaDefineDTO;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface FormulaDefineService {

	/**
	 * 查询所有公式方法
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAll(Map<String, String> queryParam);

	/**
	 * 新增方法
	 * @param dto
	 * @return
	 */
	void addFormulaDefine(FormulaDefineDTO dto) throws ServiceBizException;

	/**
	 * 根据item_id查询信息
	 * @param id
	 * @return
	 */
	Map findById(String id);

	/**
	 * 修改操作
	 * @param dto
	 */
	void editFormulaDefine(String id, FormulaDefineDTO dto) throws ServiceBizException;

	/**
	 * 删除操作
	 * @param id
	 */
	void deleteFormulaById(String id) throws ServiceBizException;
}
