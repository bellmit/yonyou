package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtPartBaseDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TmpPtPartWbpDcsPO;

/**
* 配件信息管理service
* @author ZhaoZ
* @date 2017年3月22日
*/
public interface PartInfoManageAaService {

	//配件信息维护查询
	public PageInfoDto findPartList(Map<String, String> queryParams) throws  ServiceBizException;
	//查询替代配件
	public List<Map> selectPart(String oldPartCode)throws  ServiceBizException;
	//车型信息
	public List<Map> vhclMaterial(String code)throws  ServiceBizException;
	//查询替代配件
	public PageInfoDto selectPart(Map<String, String> queryParams)throws  ServiceBizException;
	//车型信息
	public PageInfoDto addTmVhclMaterialGroupQuery(Map<String, String> queryParams)throws  ServiceBizException;
	//更新配件基础信息(保存)
	public void saveDeliveryOrder(TtPtPartBaseDcsDTO dto, BigDecimal id,LoginInfoDto loginUser)throws  ServiceBizException;

	//配件主数据监控查询异常
	public PageInfoDto exceFindList(Map<String, String> queryParams)throws  ServiceBizException;
	//查询状态
	public List<Map> getStatus()throws  ServiceBizException;
	//查询数据标记
	public List<Map> getDataFlag()throws  ServiceBizException;
	//配件查询
	public PageInfoDto partBaseDlr(Map<String, String> queryParams)throws  ServiceBizException;
	//数据校验
	public List<TtPtPartBaseDcsDTO>  checkData(LazyList<TmpPtPartWbpDcsPO> poList)throws  ServiceBizException;
	
	public void insertTmpPtPartWbpDcs(TtPtPartBaseDcsDTO rowDto)throws  ServiceBizException;
	//查询导入数据
	public List<Map> findTmpPtPartWbpDcsList()throws  ServiceBizException;

}
