package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtRecallServiceDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRecallVehicleDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityRecallManage.RecallVehicleManagerDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TmpRecallVehicleDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TmpRecallVehicleDcsPO;

/**
* @author liujiming
* @date 2017年4月13日
*/
@Service
@SuppressWarnings("all")
public class RecallVehicleManagerServiceImpl implements RecallVehicleManagerService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private RecallVehicleManagerDao rvmDao;
	
	
	/**
	 * 召回车辆管理 查询
	 */
	@Override
	public PageInfoDto recallVehicleManagerQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rvmDao.getRecallVehicleManagerQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 召回车辆管理 下载
	 */
	@Override
	
	public void getRecallInitQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rvmDao.getRecallVehicleDownloadList(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回车辆管理下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_THEME", "召回类别", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS", "召回状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE", "召回开始时间"));		
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE", "召回结束时间"));		
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "责任经销商简称"));
		exportColumnList.add(new ExcelExportColumn("TRV_RECALL_STATUS", "完成状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("DMS_RECALL_STATUS", "DMS状态", ExcelDataType.Oem_Dict));		
		exportColumnList.add(new ExcelExportColumn("GCS_RECALL_STATUS","GCS状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
		exportColumnList.add(new ExcelExportColumn("EXPECT_RECALL_DATE", "预计召回时间"));
		exportColumnList.add(new ExcelExportColumn("BALANCE_TIME", "结算时间"));
		excelService.generateExcel(excelData, exportColumnList, "召回车辆管理下载.xls", request, response);

	}
	/**
	 * 修改/明细 查询ByID
	 */
	@Override
	public Map recallDutyDealerInit(Long id) throws ServiceBizException {
		Map map = rvmDao.getRecallDutyDealerInitList(id);
		return map;
	}
	/**
	 * 修改责任经销商
	 */
	@Override
	public void recallDealerUpdate(RecallServiceActDTO rsDto, Long id) throws ServiceBizException {
		
		Long dealerId = rvmDao.getDealerIdByCode(rsDto.getDealerCode()) ;
		saveDealerPoById(dealerId, id);
	}
	
	private void saveDealerPoById(Long dealerId, Long id){
		TtRecallVehicleDcsPO savePo = TtRecallVehicleDcsPO.findById(id);
		savePo.set("DUTY_DEALER", dealerId);
		savePo.saveIt();
	}
	/**
	 * 批量修改经销商
	 */
	@Override
	public void recallDealersUpdate(RecallServiceActDTO rsDto) throws ServiceBizException {
		if(rsDto.getDealerCode() == null || "".equals(rsDto.getDealerCode())){
			throw new ServiceBizException("请选择责任经销商！");
		}
		if(rsDto.getIds() == null || "".equals(rsDto.getIds())){
			throw new ServiceBizException("请选择要修改记录！");
		}
		Long dealerId = rvmDao.getDealerIdByCode(rsDto.getDealerCode()) ;
		String[] ids = rsDto.getIds().toString().split(",");
		for(int i=0; i<ids.length; i++){
			saveDealerPoById(dealerId, Long.parseLong(ids[i].toString()));
		}
		
	}
	/**
	 * 检查临时表数据
	 */
	@Override
	public List<TmpRecallVehicleDcsDTO>   checkData() throws ServiceBizException {
		
		
		boolean isError = false;
		ArrayList<TmpRecallVehicleDcsDTO> trvdDTOList = new ArrayList<TmpRecallVehicleDcsDTO>();
		ImportResultDto<TmpRecallVehicleDcsDTO> importResult = new ImportResultDto<TmpRecallVehicleDcsDTO>();
		
		
		//数据正确性校验
		List<Map> trvdList = rvmDao.checkData2();		
		if(trvdList.size()>0){
			for(Map<String, Object> p:trvdList){
				TmpRecallVehicleDcsDTO rowDto = new TmpRecallVehicleDcsDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ROW_NUM").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 trvdDTOList.add(rowDto);
				 
			}
			importResult.setErrorList(trvdDTOList);
		}

		return trvdDTOList;
		
	}
	/**
	 * 向临时表中插入数据
	 */
	@Override
	public void insertTmpRecallVehicleDcs(TmpRecallVehicleDcsDTO rowDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmpRecallVehicleDcsPO savePo = new TmpRecallVehicleDcsPO();
		savePo.set("ROW_NO", rowDto.getRowNO());
		savePo.set("RECALL_NO", rowDto.getRecallNo());
		savePo.set("VIN", rowDto.getVin());
		savePo.set("CREATE_BY", loginInfo.getUserId());
		savePo.set("CREATE_DATE", new Date(System.currentTimeMillis()));
		savePo.saveIt();
		
	}
	/**
	 * 查询临时表数据
	 */
	@Override
	public List<Map> findTmpRecallVehicleDcsList(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = rvmDao.findTmpRecallVehicleDcsList();
		return list;
	}
	/**
	 * 导入业务表，删除临时表数据
	 */
	@Override
	public void importSaveAndDelete() throws ServiceBizException {
		List<Map> list = rvmDao.findTmpRecallVehicleDcsList();
		for(Map map : list){
			//根据召回编号获取ID
			TtRecallServiceDcsPO trsdPo = TtRecallServiceDcsPO.findFirst(" RECALL_NO = ? ", map.get("RECALL_NO"));
			
			//更新数据
			TtRecallVehicleDcsPO trvdPo = new TtRecallVehicleDcsPO();
			trvdPo = TtRecallVehicleDcsPO.findFirst(" VIN = ? AND RECALL_ID = ? ", map.get("VIN"),trsdPo.get("RECALL_ID"));
			trvdPo.set("RECALL_STATUS", OemDictCodeConstants.ACTIVITY_VEHICLE_TYPE_02);
			trvdPo.set("GCS_RECALL_STATUS", OemDictCodeConstants.ACTIVITY_VEHICLE_TYPE_02);
			trvdPo.saveIt();
		}
		TmpRecallVehicleDcsPO deletePo = new TmpRecallVehicleDcsPO();
		deletePo.deleteAll();
		
	}
	
	
	
	
	
}






