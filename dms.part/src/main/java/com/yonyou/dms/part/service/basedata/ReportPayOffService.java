/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.ReportPayOffDTO;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface ReportPayOffService {

	/**
	 * 查询报溢单
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAllList(Map<String, String> queryParam);

	/**
	 * 查询盘点单
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAllInventroy(Map<String, String> queryParam);

	/**
	 * 根据盘点单号查询报溢信息
	 * @param id
	 * @return
	 */
	List<Map> findItemByInventroy(String id);

	/**
	 * 新增时查询配件库存信息
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAllPartInfo(Map<String, String> queryParam);

	/**
	 * 新增时查询配件库存信息2
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findAllPartInfoC(Map<String, String> queryParam);

	/**
	 * 配件车型组下拉框
	 * @return
	 */
	List<Map> findPartModelGroup();

	/**
	 * 授权仓库下拉框
	 * @return
	 */
	List<Map> findStorageCode();

	/**
	 * 保存操作
	 * @param dto
	 */
	String btnSave(ReportPayOffDTO dto);

	/**
	 * 入账操作
	 * @param dto
	 * @return
	 */
	String btnAccount(ReportPayOffDTO dto);

	/**
	 * 作废操作
	 * @param dto
	 * @return
	 */
	void btnDelete(String id);

	/**
	 * 根据id查询报溢单
	 * @param id
	 * @return
	 */
	Map findById(String id);

	/**
	 * 根据报溢单号查询集合 
	 * @param id
	 * @return
	 */
	List<Map> findItemByPartProfit(String id);
	
}
