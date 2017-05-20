package com.yonyou.dms.part.service.stockmanage;

import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;

import com.yonyou.dms.common.domains.DTO.basedata.TtcampaignPlanDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TmPartStockItemDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtPartFlowDTO;
import com.yonyou.dms.common.domains.DTO.stockmanage.TtpartLendDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface CheckOutService {
	/**
	 * 查询借出登记单
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findOrderChoose(Map<String, String> queryParam) throws SerialException;

	/**
	 * 查询借出登记单明细
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto lendDetail(String id, Map<String, String> queryParam) throws SerialException;

	/**
	 * 查询借出登记单明细
	 * 
	 * @param queryParam
	 * @return
	 */
	List<Map> lendDetail2(String id) throws SerialException;

	/**
	 * 新增查询借出登记单明细
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findLendRegisterItem(Map<String, String> queryParam) throws SerialException;

	/**
	 * 查询配件新增销售价
	 * 
	 * @param queryParam
	 * @return
	 */
	PageInfoDto findLendPrice(String partNo, String storageCode, Map<String, String> queryParam) throws SerialException;

	/**
	 * 保存操作
	 * 
	 * @param dto
	 */
	String btnSave(TtpartLendDTO ttpartLendDTO);

	/**
	 * 出库操作
	 * 
	 * @param list
	 * @param sdNo
	 * @param outType
	 * @return
	 * @throws ServiceBizException
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> btnOutter(List<Map> list, String partNo2s, String storageCodes, String outQuantitys,
			TmPartStockItemDTO tmPartStockItemDTO) throws Exception;

	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> btnOutter2(List<Map> list, String split, String split2, String split3,
			TmPartStockItemDTO tmPartStockItemDTO) throws Exception;

	/**
	 * 查询出库
	 * 
	 * @param ttpartLendDTO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String btnOut(String lendNo, List<Map> lendDetail2) throws Exception;

	/**
	 * 作废
	 * 
	 * @param customerCode
	 * @param allocateInNo
	 * @throws ServiceBizException
	 */
	public void deleteAllocateIn(String lendNo) throws ServiceBizException;

}
