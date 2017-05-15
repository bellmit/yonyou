package com.yonyou.dms.vehicle.service.activityRecallManage;

import java.util.ArrayList;
import java.util.Date;
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
import com.yonyou.dms.vehicle.dao.activityRecallManage.RecallActivityBuildDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallActivityImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.RecallServiceActDTO;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.TtRecallServiceDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TmpRecallVehicleDcsPO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtRecallDealerDcsPO;


/**
* @author liujiming
* @date 2017年4月10日
*/
@SuppressWarnings("all")

@Service
public class RecallActivityBuildServiceImpl implements RecallActivityBuildService{
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private RecallActivityBuildDao rabDao;
	
	
	
	
	/**
	 * 召回活动建立 主页面查询
	 */
	@Override
	public PageInfoDto getRecallInitQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rabDao.getRecallInitQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 召回活动建立 主页面下载
	 */
	@Override
	public void getRecallInitQueryDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = rabDao.getRecallInitQueryDownload(queryParam);
    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回活动建立下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("RECALL_THEME","召回类别", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RECALL_STATUS","召回状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("RECALL_START_DATE","召回开始时间"));
		exportColumnList.add(new ExcelExportColumn("RECALL_END_DATE","召回结束时间"));
		excelService.generateExcel(excelData, exportColumnList, "召回活动建立下载.xls", request, response);
    	
		
	}
	/**
	 * 召回活动建立  新增 保存
	 */
	@Override
	public Map recallActivityAddSave(TtRecallServiceDcsDTO trsdDto) throws ServiceBizException {
		TtRecallServiceDcsPO savePo = new TtRecallServiceDcsPO();
		//判断召回编号是否存在
		Boolean flag = rabDao.checkRecallCode(trsdDto);
		if(!flag){
			throw new ServiceBizException("召回编号已存在，请重新输入！");
		}
		//判断服务活动编号是否存在
		Boolean flagActivity = rabDao.checkActivityRecallNo(trsdDto);
		if( !flagActivity){
			throw new ServiceBizException("召回编号在服务活动中已存在，请重新输入！");
		}
		setRecallPO(savePo,trsdDto,1);
		savePo.saveIt();
		
		Map map =rabDao.findRecallServiceByCode(trsdDto.getRecallNo().toString());
		return map;
	}
	/**
	 * 召回活动  数据映射
	 * @param trsdPo
	 * @param trsdDto
	 * @param type
	 */
	private void setRecallPO(TtRecallServiceDcsPO trsdPo, TtRecallServiceDcsDTO trsdDto,int type) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		trsdPo.set("RECALL_NO", trsdDto.getRecallNo());
		trsdPo.set("RECALL_NAME", trsdDto.getRecallName());
		trsdPo.set("RECALL_THEME", trsdDto.getRecallTheme());
		trsdPo.set("RECALL_TYPE", trsdDto.getRecallType());
		trsdPo.set("RECALL_START_DATE", trsdDto.getRecallStartDate());
		trsdPo.set("RECALL_END_DATE", trsdDto.getRecallEndDate());
		trsdPo.set("IS_FIXED_COST", trsdDto.getIsFixedCost());
		//是否固定费用
		if(OemDictCodeConstants .IF_TYPE_YES.toString().equals(trsdDto.getIsFixedCost())){
			trsdPo.set("MAN_HOUR_COST", trsdDto.getManHourCost());
			trsdPo.set("PART_COST", trsdDto.getPartCost());
			trsdPo.set("OTHER_COST", trsdDto.getOtherCost());
		}
		trsdPo.set("RECALL_EXPLAIN", trsdDto.getRecallExplain());
		trsdPo.set("CLAIM_APPLY_GUIDANCE", trsdDto.getClaimApplyGuidance());
		trsdPo.set("RECALL_STATUS", OemDictCodeConstants.RECALL_STATUS_01);
		//创建或修改
		if(type==1){
			trsdPo.setInteger("CREATE_BY", loginInfo.getUserId());
			trsdPo.setTimestamp("CREATE_DATE", new Date());
		}else{
			trsdPo.setInteger("UPDATE_BY", loginInfo.getUserId());
			trsdPo.setTimestamp("UPDATE_DATE", new Date());
		}
	}
	/**
	 * 修改页面 查询
	 */
	@Override
	public Map queryEditRecallServiceMap(Long recallId) throws ServiceBizException {
		Map map = rabDao.getEditRecallServiceQuery(recallId);
		return map;
	}
	/**
	 * 修改页面 保存
	 */
	@Override
	public void editRecallServiceSave(TtRecallServiceDcsDTO trsdDto) throws ServiceBizException {
		TtRecallServiceDcsPO savePo = TtRecallServiceDcsPO.findById(trsdDto.getRecallId());
		//判断修改的召回编号是否存在
		Boolean flag = rabDao.checkEditRecallNo(trsdDto);
		if(!flag ){
			throw new ServiceBizException("召回编号已存在，请重新输入！");
		}
		//判断服务活动编号是否存在
		Boolean flagActivity = rabDao.checkActivityRecallNo(trsdDto);
		if( !flagActivity){
			throw new ServiceBizException("召回编号在服务活动中已存在，请重新输入！");
		}
		setRecallPO(savePo,trsdDto,2);
		savePo.saveIt();
		
	}
	/**
	 * 主页面 删除
	 */
	@Override
	public void deleteRecallService(Long recallId) throws ServiceBizException {
		//清除此召回活动信息
		TtRecallServiceDcsPO deletePo = new TtRecallServiceDcsPO();
		deletePo.delete(" RECALL_ID =? ", recallId);
		//清除此召回活动的导入VIN信息
		TtRecallVehicleDcsPO tvPo = new TtRecallVehicleDcsPO();
		tvPo.delete(" RECALL_ID =? ", recallId);
		//清除此召回活动的组合信息
//		TtRecallMaterialDcsPO tmPo = new TtRecallMaterialDcsPO();
//		tmPo.delete(" RECALL_ID =? ", recallId);
//		TtRecallManhourDcsPO thPo = new TtRecallManhourDcsPO();
//		thPo.delete(" RECALL_ID =? ", recallId);
//		TtRecallPartDcsPO partPo = new TtRecallPartDcsPO();
//		partPo.delete(" RECALL_ID =? ", recallId);
		//清除此召回活动参与的经销商信息
		TtRecallDealerDcsPO tdPo = new TtRecallDealerDcsPO();
		tdPo.delete(" RECALL_ID =? ", recallId);
	}
	/**
	 * 发布、取消发布
	 */
	@Override
	public void sendRecallService(Long recallId, Integer recallStatus) throws ServiceBizException {
		//发布、取消发布前判断是否有参与经销商
		Boolean flagDealer = rabDao.checkRecallDealer(recallId);
		if(flagDealer){
			throw new ServiceBizException("不存在参与经销商！");
		}
		Boolean flagModel = rabDao.checkRecallModel(recallId);
		if(!flagModel){
			throw new ServiceBizException("存在未分配的车型！");
		}
		
		
		if(recallStatus == OemDictCodeConstants.RECALL_STATUS_02){
			//取消发布
			
		}else{
			//发布、再次发布
			
		}
		
		
		
//		try {
//			String recallId = request.getParamValue("recallId");//召回活动ID
//			String recallStatus = request.getParamValue("recallStatus");//召回活动状态
//			String returnValue = "";
//			if(recallStatus.equals(Constant.RECALL_STATUS_02.toString())){
//				SEDCS020 sed020 = new SEDCS020();
//				returnValue = sed020.doSend(recallId);
//			}else{
//				SEDCS019 sed019 = new SEDCS019();
//				returnValue = sed019.doSend(recallId);
//			}
//			
//			act.setOutData("returnValue", returnValue);
//		} catch (Exception e) {
//			logger.error(logonUser, e);
//			BizException e1 = new BizException(act, e, ErrorCodeConstant.UPDATE_FAILURE_CODE, "召回活动修改失败");
//			act.setException(e1);
//		}
		
		
	}
	/**
	 * 经销商 查询
	 */
	@Override
	public PageInfoDto dealerRecallQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rabDao.getDealerRecallQueryList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 经销商 新增查询
	 */
	@Override
	public PageInfoDto dealerRecallAddQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = rabDao.getDealerRecallAddQueryList(queryParam);
		return pageInfoDto;
	}
	/**
	 * 经销商 批量确定
	 */
	@Override
	public void dealerRecallAddSave(RecallServiceActDTO rsDto) throws ServiceBizException {
		if(rsDto.getRecallId2()==null || "".equals(rsDto.getRecallId2()) ){
			throw new ServiceBizException("无法获得召回活动ID，请联系管理员！");
		}
		if(rsDto.getIds()==null || "".equals(rsDto.getIds()) ){
			throw new ServiceBizException("无法获得要新增的经销商信息，请联系管理员！");
		}
		
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		String[] dealerCodes = rsDto.getIds().split(",");
		for(int i=0; i<dealerCodes.length; i++){	
			TtRecallDealerDcsPO savePo = new TtRecallDealerDcsPO();
			savePo.set("RECALL_ID", rsDto.getRecallId2());
			savePo.set("DEALER_CODE", dealerCodes[i].toString());
			savePo.set("CREATE_BY", loginInfo.getUserId());
			savePo.setTimestamp("CREATE_DATE", new Date());
			savePo.saveIt();
		}
		
		
		
		
		
	}
	/**
	 * 经销商 批量删除
	 */
	@Override
	public void dealerRecallDelete(RecallServiceActDTO rsDto) throws ServiceBizException {
		if(rsDto.getRecallId1()==null || "".equals(rsDto.getRecallId1()) ){
			throw new ServiceBizException("无法获得召回活动ID，请联系管理员！");
		}
		if(rsDto.getIds()==null || "".equals(rsDto.getIds()) ){
			throw new ServiceBizException("无法获得要新增的经销商信息，请联系管理员！");
		}		
		String[] ids = rsDto.getIds().split(",");
		for(int i=0; i<ids.length; i++){	
			TtRecallDealerDcsPO deletePo = new TtRecallDealerDcsPO();
			deletePo.delete(" ID = ? ",  ids[i] );
	
		}
		
		
		
	}
	/**
	 * VIN下载
	 * @param recallId
	 * @param request
	 * @param response
	 * @throws ServiceBizException
	 */
	@Override
	public void recallActivityVinDownLoad(Long recallId, HttpServletRequest request, HttpServletResponse response)
			throws ServiceBizException {
		List<Map> orderList = rabDao.getrecallActivityVinDownLoadList(recallId);    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回VIN下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("VIN", "召回VIN"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "责任经销商"));
		excelService.generateExcel(excelData, exportColumnList, "召回VIN下载.xls", request, response);
    	
		
	}
	/**
	 *  召回项目下载
	 * @param recallId
	 * @param request
	 * @param response
	 * @throws ServiceBizException
	 */
	@Override
	public void recallActivityProjectDownLoad(Long recallId, HttpServletRequest request, HttpServletResponse response)
			throws ServiceBizException {
		List<Map> orderList = rabDao.getrecallActivityProjectDownLoadList(recallId);    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("召回项目下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NO", "组合编号"));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰"));
		exportColumnList.add(new ExcelExportColumn("MANHOUR_CODE", "工时代码"));
		exportColumnList.add(new ExcelExportColumn("MANHOUR_NAME", "工时名称"));
		exportColumnList.add(new ExcelExportColumn("MANHOUR_NUM", "工时数量"));
		exportColumnList.add(new ExcelExportColumn("REBATE", "工时折扣"));
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("PART_NUM", "配件数量"));
		exportColumnList.add(new ExcelExportColumn("CODE_DESC", "配件类型"));
		exportColumnList.add(new ExcelExportColumn("CHANGE_RATIO", "更换比例"));
		exportColumnList.add(new ExcelExportColumn("PART_PRICE", "配件价格"));		
		excelService.generateExcel(excelData, exportColumnList, "召回项目下载.xls", request, response);
    	
		
	}
	/**
	 * 经销商下载
	 * @param recallId
	 * @param request
	 * @param response
	 * @throws ServiceBizException
	 */
	@Override
	public void recallActivityDealerDownLoad(Long recallId, HttpServletRequest request, HttpServletResponse response)
			throws ServiceBizException {
		List<Map> orderList = rabDao.getrecallActivityDealerDownLoadList(recallId);    	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("参与经销商下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("RECALL_NO", "召回编号"));
		exportColumnList.add(new ExcelExportColumn("RECALL_NAME", "召回名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "参与经销商"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商简称"));
		excelService.generateExcel(excelData, exportColumnList, "参与经销商下载.xls", request, response);
    	
		
	}
	/**
	 * 清空临时表数据
	 */
	@Override
	public void deleteTmpRecallVehicleDcs() throws ServiceBizException {
		TmpRecallVehicleDcsPO trvdPo = new TmpRecallVehicleDcsPO();
		trvdPo.deleteAll();
	}
	/**
	 * 保存数据到临时表
	 */
	@Override
	public void saveTmpRecallVehicleDcs(RecallActivityImportDTO rowDto) throws ServiceBizException {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//经销商最后一位不为"A" 则补"A"
		String dutyDealer =rowDto.getDutyDealer();
		if(!dutyDealer.substring(dutyDealer.length()-1, dutyDealer.length()).equals("A")){//最后一位不为"A" 则补"A"
			dutyDealer=dutyDealer+"A";
		}
		//保存
		TmpRecallVehicleDcsPO trvdPo = new TmpRecallVehicleDcsPO();
		trvdPo.set("ROW_NO", rowDto.getRowNO());
		trvdPo.set("VIN", rowDto.getVin());
		trvdPo.set("DUTY_DEALER", dutyDealer);
		trvdPo.set("RECALL_NO", rowDto.getRecallNo());
		trvdPo.set("INPORT_TYPE", rowDto.getInportType());
		trvdPo.set("INPORT_CLASS", OemDictCodeConstants.INPORT_CLASS_01);
		trvdPo.set("CREATE_BY", loginInfo.getUserId());
		trvdPo.set("CREATE_DATE", new Date(System.currentTimeMillis()));
		trvdPo.saveIt();
	}
	/**
	 * 校验临时表数据
	 */
	@Override
	public List<RecallActivityImportDTO> checkData(Integer inportType) throws ServiceBizException {
		ArrayList<RecallActivityImportDTO> resultDTOList = new ArrayList<RecallActivityImportDTO>();
		ImportResultDto<RecallActivityImportDTO> importResult = new ImportResultDto<RecallActivityImportDTO>();
		//VIN是否重复
		List<Map> vinList = rabDao.checkDataVIN();		
		if(vinList.size()>0){
			for(Map<String, Object> p:vinList){
				RecallActivityImportDTO rowDto = new RecallActivityImportDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ROW_NO").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 resultDTOList.add(rowDto);				 
			}
			
		}
		//VIN、经销商代码是否存在
		List<Map> dealerList = rabDao.checkDataTmpList( inportType);		
		if(dealerList.size()>0){
			for(Map<String, Object> p:dealerList){
				RecallActivityImportDTO rowDto = new RecallActivityImportDTO();
				 rowDto.setRowNO(Integer.valueOf(p.get("ROW_NO").toString()));
				 rowDto.setErrorMsg(p.get("ERROR").toString());
				 resultDTOList.add(rowDto);				 
			}
			
		}
		
		
		return resultDTOList;
	}
	/**
	 * 查询临时表数据
	 */
	@Override
	public List<Map> queryTmpRecallVehicleDcsList(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = rabDao.findTmpRecallVehicleList();
		return list;
	}
	/**
	 * 导入业务表
	 */
	@Override
	public void saveAndDeleteData(Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = rabDao.findTmpRecallVehicleList();
		//将数据插入业务表
		String  recallNo = list.get(0).get("RECALL_NO").toString();
		Integer inportType = Integer.parseInt(list.get(0).get("INPORT_TYPE").toString());
		TtRecallServiceDcsPO  trsdPo = TtRecallServiceDcsPO.findFirst(" RECALL_NO = ? ", recallNo);
		Long recallId = Long.parseLong(trsdPo.get("RECALL_ID").toString());
		if(inportType == OemDictCodeConstants.INPORT_TYPE_01){

			TtRecallVehicleDcsPO deletePo = new TtRecallVehicleDcsPO();
			deletePo.delete(" RECALL_ID = ? ", recallId);
		}
		
		for(Map map : list){
			TtRecallVehicleDcsPO savePo = new TtRecallVehicleDcsPO();
			//根据经销商CODE查询ID
			Long dealerId = rabDao.findIdByDealerCode(map.get("DUTY_DEALER").toString());			
			savePo.set("RECALL_ID", recallId);
			savePo.setInteger("RECALL_STATUS", 40331001);
			savePo.setInteger("DMS_RECALL_STATUS", 40331001);
			savePo.setInteger("GCS_RECALL_STATUS", 40331001);
			savePo.setInteger("GCS_SEND_STATUS", 10041002);
			savePo.set("DUTY_DEALER", dealerId);
			savePo.set("VIN", map.get("VIN"));
			savePo.set("EXPECT_RECALL_DATE", null);
			savePo.setInteger("UPDATE_COUNT", 0);
			savePo.setInteger("UPLOAD_STATUS", 10041002);
			savePo.set("CREATE_BY", map.get("CREATE_BY"));
			savePo.set("CREATE_DATE", map.get("CREATE_DATE"));
			savePo.saveIt();
		}
		//删除临时表数据
		TmpRecallVehicleDcsPO deleteTmpPo = new TmpRecallVehicleDcsPO();
		deleteTmpPo.deleteAll();
	}
	
	
	
	
	
}




