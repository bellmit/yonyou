package com.yonyou.dms.vehicle.service.threePack;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackPtitemRelationDTO;

public interface TtThreepackPtitemRelationService {
	//分页查询
	public PageInfoDto findthreePack(Map<String, String> queryParam) throws Exception ;
	//修改
	public void modifyMinclass(Long id, TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException ;
	//新增
	public long addMinclass(TtThreepackPtitemRelationDTO tcdto) throws ServiceBizException;
	//删除
	public void deleteChargeById(Long id) throws ServiceBizException;
	//项目编号下拉框
	public List<Map> selectItem(Map<String, String> params);
	//小类代码下拉框
	public List<Map> selectItemMin(String itemNo);
	//导入
	public void insert(TtThreepackPtitemRelationDTO rowDto);
	//导入校验
	public ImportResultDto<TtThreepackPtitemRelationDTO> checkData(TtThreepackPtitemRelationDTO rowDto)throws Exception;
	
	public List<Map> findById(Long id);
	
	public List<Map> findAllRelation(Map<String, String> queryParam);
	//查询小类名称
	public List<Map> selectmMinName(String minclassNo);
	//查询项目名称
	public List<Map> selectitemName(String itemNo);
	//配件名称
	public List<Map> selectpartName(String partCode);
}
