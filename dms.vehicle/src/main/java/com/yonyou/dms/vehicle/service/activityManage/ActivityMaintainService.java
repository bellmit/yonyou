package com.yonyou.dms.vehicle.service.activityManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpWrActivityVehicleDcsImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtWrActivityDTO;

/**
* @author liujiming
* @date 2017年3月23日
*/
@SuppressWarnings("all")

public interface ActivityMaintainService {

	//服务活动建立 查询
	public PageInfoDto getActivityInitQuery(Map<String,String> queryParam) throws ServiceBizException;
	
	//服务活动建立 主页面新增
	public Map activityAddSave(TtWrActivityDTO twaDto) throws ServiceBizException;
	//明细
	public Map activityDetailQuery(Long activityId) throws ServiceBizException;
	//明细
	public PageInfoDto activityLabourDetailQuery(Long activityId) throws ServiceBizException;
	//明细
	public PageInfoDto activityPartDetailQuery(Long activityId) throws ServiceBizException;
	//明细
	public PageInfoDto activityOtherDetailQuery(Long activityId) throws ServiceBizException;
	//明细
	public PageInfoDto activityVehicleDetailQuery(Long activityId) throws ServiceBizException;	
	//明细
	public PageInfoDto activityAgeDetailQuery(Long activityId) throws ServiceBizException;
	//删除
	public void deleteByActivityId(Long activityId) throws ServiceBizException;
	
	//服务活动建立 主页面修改
	public void activityModifySave(TtWrActivityDTO twaDto) throws ServiceBizException;
	
	//服务活动建立 主页面车型
	public PageInfoDto activityModifyVehicle(Map<String, String> queryParam) throws ServiceBizException;
	
	//服务活动建立 主页面车型新增查询
	public PageInfoDto getActivityCarModelQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//服务活动建立 主页面车型新增 确定
	public void activityAddModelSave(ActivityManageDTO amDto) throws ServiceBizException;
	
	//删除 主页面车型
	public void deleteCarModelById(Long id) throws ServiceBizException;
	
	// 车龄  新增
	public void saveActivityCarAge(ActivityManageDTO amDto) throws ServiceBizException;
	//车龄  查询
	public PageInfoDto getActivityCarAgeQuery(Map<String, String> queryParam) throws ServiceBizException;
	//车龄  删除 
	public void deleteCarAgeById(Long id) throws ServiceBizException;
	//车系代码查询
	public List<Map> getGroupCodeListQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	// 工时维护 查询
	public PageInfoDto getRangeLabourQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//工时维护 确定
	public void activityAddLabourSave(ActivityManageDTO amDto) throws ServiceBizException;
	
	// 工时维护 查询
	public PageInfoDto getRangeLabourList(Map<String, String> queryParam) throws ServiceBizException;
		
	//工时  删除 
	public void deleteLabelDeleteByDetailId(Long detailId) throws ServiceBizException;
	
	// 配件维护 查询
	public PageInfoDto getRangePartQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//配件维护 确定
	public void activityAddPartSave(ActivityManageDTO amDto) throws ServiceBizException;
	
	// 配件维护 查询
	public PageInfoDto getRangePartList(Map<String, String> queryParam) throws ServiceBizException;
		
	//配件  删除 
	public void deletePartByDetailId(Long detailId) throws ServiceBizException;
	
	
	// 其他项目维护 查询
	public PageInfoDto getRangeOtherQuery(Map<String, String> queryParam) throws ServiceBizException;
	
	//其他项目维护 确定
	public void activityAddOtherSave(ActivityManageDTO amDto) throws ServiceBizException;
	
	// 其他项目维护 查询
	public PageInfoDto getRangeOtherList(Map<String, String> queryParam) throws ServiceBizException;
		
	//其他项目  删除 
	public void deleteOtherByDetailId(Long detailId) throws ServiceBizException;
	
	//导入清空临时表数据
	public void deleteTmpWrActivityVehicleDcs() throws ServiceBizException;
	//导入临时表
	public void saveTmpWrActivityVehicleDcs(TmpWrActivityVehicleDcsImportDTO rowDto) throws ServiceBizException;
	//校验临时表数据
	public List<TmpWrActivityVehicleDcsImportDTO> checkData(String activityId) throws ServiceBizException;
	//查询临时表数据
	public List<Map> queryTmpWrActivityVehicleDcsList(Map<String, String> queryParam) throws ServiceBizException;
	//导入业务表，删除临时表
	public void  saveAndDeleteData(Map<String, String> queryParam)throws ServiceBizException;
}



















