/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryCheckDTO;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryItemDTO;

/**
 * @author yangjie
 *
 */
@SuppressWarnings("rawtypes")
public interface InventoryCheckService {

	/**
	 * 查询盘点单信息
	 * @param map
	 * @return
	 */
	PageInfoDto findAllInventoryInfo(Map<String, String> map);

	/**
	 * 根据盘点单号查询盘点明细
	 * @param id
	 * @return
	 */
	List<Map> findAllInventoryItemInfoById(String id);

	/**
	 * 根据盘点单号查询盘点单信息
	 * @param id
	 * @return
	 */
	Map findinventoryFirst(String id);

	/**
	 * 新增时查询配件信息
	 * @param param
	 * @return
	 */
	PageInfoDto findPartInfo(Map<String, String> param);

	/**
	 * 生成空盘点单
	 * @return
	 */
	String saveBlankInventoryNo();

	/**
	 * 新增盘点单
	 * @param dto
	 * @return
	 */
	String addNewInventoryNo(InventoryCheckDTO dto);

	/**
	 * 保存按钮
	 * @param dto
	 * @return
	 */
	String saveInventoryInfo(InventoryItemDTO dto);

	/**
	 * 盘点确认按钮
	 * @param dto
	 * @return
	 */
	String btnConfirm(InventoryItemDTO dto);

	/**
	 * 作废按钮
	 * @param dto
	 */
	void btnDel(InventoryItemDTO dto);

}
