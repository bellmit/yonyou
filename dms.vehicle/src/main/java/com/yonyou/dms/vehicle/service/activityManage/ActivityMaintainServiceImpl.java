package com.yonyou.dms.vehicle.service.activityManage;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivityMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivityManageDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpWrActivityVehicleDcsImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtWrActivityDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TmpWrActivityVehicleDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityAgeDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityLabourDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityModelDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityOtherDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityPartDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtWrActivityVehicleDcsPO;

/**
* @author liujiming
* @date 2017年3月23日
*/
@SuppressWarnings("all")

@Service
public class ActivityMaintainServiceImpl implements ActivityMaintainService{
	
	@Autowired
	private ActivityMaintainDao actMatDao;
	
	private static String ACTIVITY_ID = "";
	
	/**
	 * 服务活动建立 查询
	 */
	@Override
	public PageInfoDto getActivityInitQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityInitQuery(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 服务活动建立  主页面新增
	 */
	@Override
	public Map activityAddSave(TtWrActivityDTO twaDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		//校验ACTIVITY_CODE是否已经存在		
		boolean flagCode = actMatDao.checkActivityCode(twaDto.getActivityCode());
		if(flagCode){
			throw new ServiceBizException("活动编号已存在，请重新输入！");
		}

				
			TtWrActivityPO savePo = new TtWrActivityPO();
			savePo.set("ACTIVITY_CODE", twaDto.getActivityCode());
			savePo.set("ACTIVITY_NAME", twaDto.getActivityName());
			savePo.set("GLOBAL_ACTIVITY_CODE", twaDto.getGlobalActivityCode());
			savePo.set("ACTIVITY_TITLE", twaDto.getActivityTitle());			
			savePo.set("ACTIVITY_TYPE", twaDto.getActivityType());			
			savePo.set("CLAIM_TYPE", twaDto.getClaimType());
			savePo.setTimestamp("START_DATE", twaDto.getStartDate());
			savePo.setTimestamp("END_DATE", twaDto.getEndDate());
			savePo.setTimestamp("SUMM_CLOSEDATE", twaDto.getSummClosedate());			
			savePo.set("IS_FEE", twaDto.getIsFee());
			savePo.set("IS_MULTI", twaDto.getIsMulti());
			//固定费用=是 时 获取页面费用value
			if(twaDto.getIsFee() == OemDictCodeConstants.IF_TYPE_YES){
				savePo.set("LABOUR_FEE", twaDto.getLabourFee());
				savePo.set("PART_FEE", twaDto.getPartFee());
				savePo.set("OTHER_FEE", twaDto.getOtherFee());
			}else{
				savePo.set("LABOUR_FEE", 0);
				savePo.set("PART_FEE", 0);
				savePo.set("OTHER_FEE", 0);
			}			
			savePo.set("CHOOSE_WAY", twaDto.getChooseWay());
			savePo.set("EXPLANS", twaDto.getExplans());
			savePo.set("GUIDE", twaDto.getGuide());
			savePo.set("IS_DEL", OemDictCodeConstants.IS_DEL_00); //删除状态
			savePo.set("STATUS",OemDictCodeConstants.ACTIVITY_STATUS_01);// 活动状态未发布
			savePo.set("OEM_COMPANY_ID", loginInfo.getCompanyId()); //公司ID
			//savePo.set("OEM_COMPANY_ID", "2010010100070674");
			savePo.saveIt();
		
		TtWrActivityDTO dto = new TtWrActivityDTO();
		TtWrActivityPO findPo = new TtWrActivityPO();
		findPo  = 	TtWrActivityPO.findFirst(" ACTIVITY_CODE = ?  ", twaDto.getActivityCode());		
		Map	map = actMatDao.getActivityDetailQuery(findPo.getLong("ACTIVITY_ID"));
		
		return map;
		
	}
	
	
	
	//标签
	@Override
	public Map activityDetailQuery(Long activityId) throws ServiceBizException {
		Map resultMap = actMatDao.getActivityDetailQuery(activityId);
		
		return resultMap;
	}
	//工时
	@Override
	public PageInfoDto activityLabourDetailQuery(Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityLabourQuery(activityId);
		return pageInfoDto;
	}
	//配件
	@Override
	public PageInfoDto activityPartDetailQuery(Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityPartQuery(activityId);
		return pageInfoDto;
	}
	//其他项目
	@Override
	public PageInfoDto activityOtherDetailQuery(Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityOtherQuery(activityId);
		return pageInfoDto;
	}
	//车型
	@Override
	public PageInfoDto activityVehicleDetailQuery(Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityVehicleQuery(activityId);
		return pageInfoDto;
	}
	//车龄
	@Override
	public PageInfoDto activityAgeDetailQuery(Long activityId) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityAgeQuery(activityId);
		return pageInfoDto;
	}
	/**
	 * 删除 By activityId
	 */
	@Override
	public void deleteByActivityId(Long activityId) throws ServiceBizException {
		TtWrActivityPO deletePo = new TtWrActivityPO();
		deletePo = TtWrActivityPO.findById(activityId);
		deletePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_01);
		deletePo.saveIt();
		
	}
	/**
	 * 主页面修改方法
	 */
	@Override
	public void activityModifySave(TtWrActivityDTO twaDto) throws ServiceBizException {

			TtWrActivityPO savePo = TtWrActivityPO.findFirst(" ACTIVITY_CODE = ?  ", twaDto.getActivityCode().toString());
			savePo.set("ACTIVITY_CODE", twaDto.getActivityCode());
			savePo.set("ACTIVITY_NAME", twaDto.getActivityName());
			savePo.set("GLOBAL_ACTIVITY_CODE", twaDto.getGlobalActivityCode());
			savePo.set("ACTIVITY_TITLE", twaDto.getActivityTitle());			
			savePo.set("ACTIVITY_TYPE", twaDto.getActivityType());			
			savePo.set("CLAIM_TYPE", twaDto.getClaimType());
			savePo.setTimestamp("START_DATE", twaDto.getStartDate());
			savePo.setTimestamp("END_DATE", twaDto.getEndDate());
			savePo.setTimestamp("SUMM_CLOSEDATE", twaDto.getSummClosedate());			
			savePo.set("IS_FEE", twaDto.getIsFee());
			savePo.set("IS_MULTI", twaDto.getIsMulti());
			//固定费用=是时 获取页面费用value
			if(twaDto.getIsFee() == OemDictCodeConstants.IF_TYPE_YES){
				savePo.set("LABOUR_FEE", twaDto.getLabourFee());
				savePo.set("PART_FEE", twaDto.getPartFee());
				savePo.set("OTHER_FEE", twaDto.getOtherFee());
			}else{
				savePo.set("LABOUR_FEE", 0);
				savePo.set("PART_FEE", 0);
				savePo.set("OTHER_FEE", 0);
			}			
			savePo.set("CHOOSE_WAY", twaDto.getChooseWay());
			savePo.set("EXPLANS", twaDto.getExplans());
			savePo.set("GUIDE", twaDto.getGuide());
			savePo.saveIt();
		
	}
	/**
	 * 主页面车型维护
	 */
	@Override
	public PageInfoDto activityModifyVehicle(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getModifyVehicleQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 主页面车型新增车型查询
	 */	
	@Override
	public PageInfoDto getActivityCarModelQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getActivityCarModelQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 主页面车型新增车型 确定
	 */
	@Override
	public void activityAddModelSave(ActivityManageDTO amDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		Long activityId = Long.parseLong(amDto.getActivityId2().toString());
		String[] groupIds = amDto.getGroupIds().split(",");
		for(int i=0;i<groupIds.length;i++){
		    	Long groupId = Long.parseLong(groupIds[i].toString());
		    	TtWrActivityModelDcsPO  twamdPo = new TtWrActivityModelDcsPO();
		    	twamdPo.set("ACTIVITY_ID", activityId);
		    	twamdPo.set("GROUP_ID", groupId);
		    	twamdPo.set("CREATE_BY", loginInfo.getUserId());
		    	twamdPo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
		    	twamdPo.saveIt();
		}
	}
	/**
	 * 主页面车型  删除
	 */
	@Override
	public void deleteCarModelById(Long id) throws ServiceBizException {
		TtWrActivityModelDcsPO  deletePo = new TtWrActivityModelDcsPO();
		deletePo.delete(" ID = ?  ", id);
		
	}
	/**
	 * 车龄 新增
	 */
	@Override
	public void saveActivityCarAge(ActivityManageDTO amDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				
		Long activityId = Long.parseLong(amDto.getActivityId2().toString());
		TtWrActivityAgeDcsPO teaadPo = new TtWrActivityAgeDcsPO();
		teaadPo.set("ACTIVITY_ID", activityId);
		teaadPo.set("AGE_TYPE", amDto.getAgeType());
		teaadPo.set("START_DATE", amDto.getStartDate());
		teaadPo.set("END_DATE", amDto.getEndDate());
		teaadPo.set("CREATE_BY", loginInfo.getUserId());
		teaadPo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
		teaadPo.insert();
		
	}
	/**
	 * 车龄 查询
	 */
	@Override
	public PageInfoDto getActivityCarAgeQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getCarAgeQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 车龄 删除
	 */
	@Override
	public void deleteCarAgeById(Long id) throws ServiceBizException {
		TtWrActivityAgeDcsPO deletePo = new TtWrActivityAgeDcsPO();
		deletePo.delete(" ID = ?  ", id);
	}
	/**
	 * 车系代码下拉选查询
	 */
	@Override
	public List<Map> getGroupCodeListQuery(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = actMatDao.getGroupCodeListQuery(queryParam);
		return list;
	}
	/**
	 * 工时维护 查询
	 */
	@Override
	public PageInfoDto getRangeLabourQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangeLabourQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 工时维护确定
	 */
	@Override
	public void activityAddLabourSave(ActivityManageDTO amDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);				
		Long activityId = Long.parseLong(amDto.getActivityId2().toString());
		
		String[] strArray = amDto.getGroupIds().split(",");
		for(int i=0;i<strArray.length;i++){
			String[] labourArray =  strArray[i].split("#");
			TtWrActivityLabourDcsPO savePo = new TtWrActivityLabourDcsPO();
			savePo.set("ACTIVITY_ID", activityId);
			savePo.set("LABOUR_CODE", labourArray[1]);
			savePo.set("LABOUR_NAME", labourArray[2]);
			savePo.set("LABOUR_NUM", labourArray[3]);
			savePo.set("DISCOUNT", 1.00);
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
			savePo.insert();
		}
	}
	/**
	 * 活动项目 工时查询
	 */
	@Override
	public PageInfoDto getRangeLabourList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangeLabourList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 活动项目 工时删除
	 */
	@Override
	public void deleteLabelDeleteByDetailId(Long detailId) throws ServiceBizException {
		TtWrActivityLabourDcsPO deletePo = new TtWrActivityLabourDcsPO();
		deletePo.delete(" DETAIL_ID = ?  ", detailId);
	}
	/**
	 * 活动项目  配件维护查询
	 */
	@Override
	public PageInfoDto getRangePartQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangePartQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 活动项目  配件维护 确定
	 */
	@Override
	public void activityAddPartSave(ActivityManageDTO amDto) throws ServiceBizException {
				//获取当前用户
				LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);				
				Long activityId = Long.parseLong(amDto.getActivityId2().toString());
				
				String[] strArray = amDto.getGroupIds().split(",");
				for(int i=0;i<strArray.length;i++){
					String[] partArray =  strArray[i].split("#");
					TtWrActivityPartDcsPO savePo = new TtWrActivityPartDcsPO();
					savePo.set("ACTIVITY_ID", activityId);
					savePo.set("PART_CODE", partArray[1]);
					savePo.set("PART_NAME", partArray[2]);
					savePo.set("DNP", partArray[3]);
					savePo.set("MSRP", partArray[4]); 
					savePo.set("PART_AMOUNT", partArray[5]);
					savePo.set("DISCOUNT", 1.00);
					savePo.set("CREATE_BY", loginInfo.getUserId());
					savePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
					savePo.insert();
				}
		
	}
	/**
	 * 活动项目  配件 查询
	 */
	@Override
	public PageInfoDto getRangePartList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangePartList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 活动项目  配件删除
	 */
	@Override
	public void deletePartByDetailId(Long detailId) throws ServiceBizException {
		TtWrActivityPartDcsPO deletePo = new TtWrActivityPartDcsPO();
		deletePo.delete(" DETAIL_ID = ?  ", detailId);
		
	}
	/**
	 * 其他项目 维护查询
	 */
	@Override
	public PageInfoDto getRangeOtherQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangeOtherQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 其他项目 维护确定
	 */
	@Override
	public void activityAddOtherSave(ActivityManageDTO amDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);				
		Long activityId = Long.parseLong(amDto.getActivityId2().toString());
		
		String[] strArray = amDto.getGroupIds().split(",");
		for(int i=0;i<strArray.length;i++){
			String[] otherArray =  strArray[i].split("#");
			TtWrActivityOtherDcsPO savePo = new TtWrActivityOtherDcsPO();
			savePo.set("ACTIVITY_ID", activityId);
			savePo.set("OTHER_FEE_CODE", otherArray[1]);
			savePo.set("OTHER_FEE_NAME", otherArray[2]);
			savePo.set("AMOUNT", otherArray[3]);
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
			savePo.insert();
		}
		
	}
	/**
	 * 其他项目 查询
	 */
	@Override
	public PageInfoDto getRangeOtherList(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = actMatDao.getRangeOtherList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 其他项目 删除
	 */
	@Override
	public void deleteOtherByDetailId(Long detailId) throws ServiceBizException {
		TtWrActivityOtherDcsPO deletePo = new TtWrActivityOtherDcsPO();
		deletePo.delete(" OTHER_REL_ID = ?  ", detailId);
		
	}
	/**
	 * 清空临时表数据
	 */
	@Override
	public void deleteTmpWrActivityVehicleDcs() throws ServiceBizException {
		TmpWrActivityVehicleDcsPO.deleteAll();
		
	}
	/**
	 * 导入临时表
	 */
	@Override
	public void saveTmpWrActivityVehicleDcs(TmpWrActivityVehicleDcsImportDTO rowDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = "";
		String dealerName = "";
		if(rowDto.getDealerCode() != null || ! "".equals(rowDto.getDealerCode())){
			dealerCode = rowDto.getDealerCode();		
		}else{
			//dealerCode为空时，根据vin查找dealerCode
			dealerCode = actMatDao.findDealerCodeByVIN(rowDto.getVin());
		}
		 dealerName = actMatDao.findDealerNameByCode(dealerCode);
		TmpWrActivityVehicleDcsPO twavdPo = new TmpWrActivityVehicleDcsPO();		
		twavdPo.set("ID", rowDto.getRowNO());
		twavdPo.set("VIN", rowDto.getVin());
		twavdPo.set("DEALER_CODE", dealerCode);
		twavdPo.set("DEALER_NAME", dealerName);
		twavdPo.set("LINK_MAN", rowDto.getLinkMan());
		twavdPo.set("LINK_PHONE", rowDto.getLinkPhone());
		twavdPo.set("CREATE_BY", loginInfo.getUserId());
		twavdPo.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
		twavdPo.insert();
	}
	
	
	/**
	 * 校验临时表数据
	 */
	@Override
	public List<TmpWrActivityVehicleDcsImportDTO> checkData(String activityId) throws ServiceBizException {
		
		ACTIVITY_ID = activityId;
		
		ArrayList<TmpWrActivityVehicleDcsImportDTO> tmpDTOList = new ArrayList<TmpWrActivityVehicleDcsImportDTO>();
		ImportResultDto<TmpWrActivityVehicleDcsImportDTO> importResult = new ImportResultDto<TmpWrActivityVehicleDcsImportDTO>();
		//查询临时表数据
		List<Map>  tmpList = actMatDao.findTmpWrActivityVehicleDcsList();
		if (null == tmpList || tmpList.size() <= 0) {
			tmpList = new ArrayList();
		}
		//校验
		for(Map map : tmpList){			
			//校验联系人电话
			String linkPhone = map.get("LINK_PHONE").toString();
			if(linkPhone!=null && !"".equals(linkPhone)){
				boolean isNum = linkPhone.matches("[0-9]+"); 
				if(!isNum){
					TmpWrActivityVehicleDcsImportDTO rowDto = new TmpWrActivityVehicleDcsImportDTO();
					rowDto.setRowNO(Integer.parseInt(map.get("ID").toString()));
					rowDto.setErrorMsg("电话为"+linkPhone+"的，不能导入,必须是数字");
					tmpDTOList.add(rowDto);					
				}
			}
			
			
		}
		//检查vin是否存在
		List<Map> vinList = actMatDao.checkDataVIN(activityId);		
		if(vinList.size()>0){
			for(Map<String, Object> p:vinList){
				TmpWrActivityVehicleDcsImportDTO rowDto = new TmpWrActivityVehicleDcsImportDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ID").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 tmpDTOList.add(rowDto);				 
			}
			
		}
		//校验数据是否重复
		//检查vin是否存在
		List<Map> dumpList = actMatDao.checkDataTmpTableDump();		
		if(dumpList.size()>0){
			for(Map<String, Object> p:dumpList){
				TmpWrActivityVehicleDcsImportDTO rowDto = new TmpWrActivityVehicleDcsImportDTO();
				rowDto.setRowNO(Integer.valueOf(p.get("ID").toString()));
				rowDto.setErrorMsg(p.get("ERROR").toString());
				tmpDTOList.add(rowDto);				 
			}
					
		}
		
		//校验VIN是否在车辆表中存在
		List<Map> vehicleList = actMatDao.tableCheckNotVinDump();		
		if(vehicleList.size()>0){
			for(Map<String, Object> p:vehicleList){
				TmpWrActivityVehicleDcsImportDTO rowDto = new TmpWrActivityVehicleDcsImportDTO();
				rowDto.setRowNO(Integer.valueOf(p.get("ID").toString()));
				rowDto.setErrorMsg(p.get("ERROR").toString());
				tmpDTOList.add(rowDto);				 
			}
					
		}
				
		//校验经销商是否存在
		List<Map> dealerList = actMatDao.tableCheckNotDealerDump();		
		if(dealerList.size()>0){
			for(Map<String, Object> p:dealerList){
				TmpWrActivityVehicleDcsImportDTO rowDto = new TmpWrActivityVehicleDcsImportDTO();
				rowDto.setRowNO(Integer.valueOf(p.get("ID").toString()));
				rowDto.setErrorMsg(p.get("ERROR").toString());
				tmpDTOList.add(rowDto);				 
			}
					
		}
				
		return tmpDTOList;
	}
	/**
	 * 查询临时表数据
	 */
	@Override
	public List<Map> queryTmpWrActivityVehicleDcsList(Map<String, String> queryParam) throws ServiceBizException {
		List<Map>  tmpList = actMatDao.findTmpWrActivityVehicleDcsList();
		return tmpList;
	}
	/**
	 * 插入业务表，删除临时表
	 */
	@Override
	public void saveAndDeleteData(Map<String, String> queryParam) throws ServiceBizException {
		//删除业务表数据
		List<Map>  tmpList = actMatDao.findTmpWrActivityVehicleDcsList();
		for(Map map : tmpList){
			TtWrActivityVehicleDcsPO savePo = new TtWrActivityVehicleDcsPO();
			savePo.set("ACTIVITY_ID", ACTIVITY_ID); 
			savePo.set("VIN", map.get("VIN"));
			savePo.set("DEALER_CODE", map.get("DEALER_CODE"));
			savePo.set("DEALER_NAME", map.get("DEALER_NAME"));
			savePo.set("LINK_MAN", map.get("LINK_MAN"));
			savePo.set("LINK_PHONE", map.get("LINK_PHONE"));
			savePo.set("VEHICLE_STATUS", map.get("VEHICLE_STATUS"));
			savePo.set("CREATE_BY", map.get("CREATE_BY"));
			savePo.set("CREATE_DATE", map.get("CREATE_DATE"));			
			savePo.saveIt();
		}
		//删除临时表数据
		TmpWrActivityVehicleDcsPO.deleteAll();
		
	}


	
	
	
}













