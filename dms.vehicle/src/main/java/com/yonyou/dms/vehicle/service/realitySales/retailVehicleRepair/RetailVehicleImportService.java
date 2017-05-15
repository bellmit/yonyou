package com.yonyou.dms.vehicle.service.realitySales.retailVehicleRepair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TmpVsNvdrDTO;

@SuppressWarnings("rawtypes")
public interface RetailVehicleImportService {

	/**
	 * 导入临时表
	 * @param tvnDto
	 */
	public void insertRemark(ArrayList<TmpVsNvdrDTO> dataList)throws ServiceBizException;

	/**
	 * 查询此次导入数据验证后的结果
	 * @return
	 */
	public List<Map> allMessageQuery()throws ServiceBizException;

	/**
	 * 计算上传数据数量（和错误数据数量）
	 * @return
	 * @throws ServiceBizException
	 */
	public Map<String, String> countDataNum()throws ServiceBizException;

	public void delete()throws ServiceBizException;

	public List<Map> findReeDate(Map<String, String> queryParam, LoginInfoDto loginInfo)throws ServiceBizException;

	/**
	 * 根据选择类型获取相应数据
	 * @param id
	 * @return
	 */
	public List<Map> allMessageQuery(Integer id) throws ServiceBizException;


}
