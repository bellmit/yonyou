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
public interface RepairGroupService {

	/**
	 * 查询维修组合主表
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findGroupItem(Map<String, String> queryParam);

	/**
	 * 查询维修项目
	 * @param queryParam
	 * @return
	 */
	List<Map> findRepairProject(Map<String, String> queryParam);

	/**
	 * 查询维修配件
	 * @param queryParam
	 * @return
	 */
	List<Map> findRepairPart(Map<String, String> queryParam);

}
