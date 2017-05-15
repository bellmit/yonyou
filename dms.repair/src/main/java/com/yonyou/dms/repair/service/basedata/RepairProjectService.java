/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface RepairProjectService {

	/**
	 * 查询项目车型组下拉框
	 * @return
	 */
	List<Map> findProjectModelList();

	/**
	 * 查询车型组与车型匹配集合
	 * @return
	 */
	Map<String, String> findModelForInput();

	/**
	 * 查询维修项目树状图
	 * @return
	 */
	List<Map> findRepairProjectTree();

	/**
	 * 查询维修项目列表
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findRepairProjectList(Map<String, String> queryParam);

	/**
	 * 查询维修项目子表
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findRepairProjectItem(Map<String, String> queryParam);
	
}
