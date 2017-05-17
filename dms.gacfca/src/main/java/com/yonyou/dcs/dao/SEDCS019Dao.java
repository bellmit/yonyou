package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.RecallServiceDTO;
import com.yonyou.dms.DTO.gacfca.RecallServiceLabourDTO;
import com.yonyou.dms.DTO.gacfca.RecallServicePartDTO;
import com.yonyou.dms.DTO.gacfca.RecallServiceVehicleDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtRecallServiceDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS019Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS019Dao.class);
	/**
	 * 参与经销商的范围
	 * @param recallId
	 * @return
	 */
	public List<Map<String, Object>> queryDealer(String recallId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT TMD.DEALER_CODE \n" );
		sql.append(" from TT_RECALL_DEALER_DCS TD ,TM_DEALER TMD\n" );
		sql.append(" WHERE  TD.DEALER_CODE = TMD.DEALER_ID  \n");
		sql.append("        AND TD.IS_DEL = 0  \n");
		sql.append("  		AND TD.RECALL_ID = '"+recallId+"'  \n");
        List<Map<String, Object>> map = pageQuery(sql.toString(), null, getFunName());
        return map;
	}
	
	/**
	 * 需要下发的召回活动数据DTO
	 * @return
	 */
	public LinkedList<RecallServiceDTO> queryAllInfo(String recallId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  RECALL_ID, \n");
		sql.append("   	RECALL_NO,  \n");//召回活动编号
		sql.append("    RECALL_NAME,   \n");//召回名称
		sql.append("    RECALL_THEME,   \n");//召回类别
		sql.append("    RECALL_TYPE,   \n");//召回方式
		sql.append("    DATE_FORMAT(RECALL_START_DATE,'%Y-%m-%d') RECALL_START_DATE,   \n");//召回开始时间
		sql.append("    DATE_FORMAT(RECALL_END_DATE,'%Y-%m-%d') RECALL_END_DATE,   \n");//召回结束时间
		sql.append("    IS_FIXED_COST,   \n");//是否固定费用
		sql.append("    MAN_HOUR_COST,  \n");//工时费用 
		sql.append("    PART_COST,   \n");//配件费用
		sql.append("    OTHER_COST,   \n");//其他费用
		sql.append("    RECALL_EXPLAIN, \n"); //召回活动说明
		sql.append("    CLAIM_APPLY_GUIDANCE,  \n");//索赔申请指导
		sql.append("    RECALL_STATUS   \n");//召回状态
		sql.append("FROM TT_RECALL_SERVICE_DCS \n");
		sql.append(" where RECALL_ID = ?  \n");
		List<Object> parame=new ArrayList<Object>();
		parame.add(recallId);
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), parame);
		LinkedList<RecallServiceDTO> dtolist=null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new LinkedList<RecallServiceDTO>();
			for(Map map:listmap){
				RecallServiceDTO dto=wrapperDTO(map);
				dtolist.add(dto);
			}
		}
		return dtolist;
	}
	
	/**
	 * 车辆信息
	 * @param recallId
	 * @return
	 */
	public List<Map<String, Object>> queryModel(String recallId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT MG1.GROUP_CODE AS BRAND_CODE,MG2.GROUP_CODE AS SERIES_CODE,MG3.GROUP_CODE AS MODEL_CODE, \n" );
		sql.append(" TRM.RECALL_ID,TRM.GROUP_NO \n" );
		sql.append(" FROM TT_RECALL_MATERIAL_DCS TRM,TM_VHCL_MATERIAL M, TM_VHCL_MATERIAL_GROUP_R MGR, \n" );
		sql.append("  TM_VHCL_MATERIAL_GROUP MG4,TM_VHCL_MATERIAL_GROUP MG3,TM_VHCL_MATERIAL_GROUP MG2,TM_VHCL_MATERIAL_GROUP MG1 \n");
		sql.append(" WHERE  TRM.MATERIAL_ID =  M.MATERIAL_ID \n");
		sql.append(" AND M.MATERIAL_ID = MGR.MATERIAL_ID AND MGR.GROUP_ID = MG4.GROUP_ID AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID     \n");
		sql.append(" AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID  AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID  AND M.COMPANY_ID = 2010010100070674\n");
		sql.append(" AND TRM.RECALL_ID = '"+recallId+"'  \n");
        List<Map<String, Object>> map = pageQuery(sql.toString(), null, getFunName());
        return map;
	}

	/**
	 * 工时信息
	 * @param recallId
	 * @return
	 */
	public List<Map<String, Object>> queryLabour(String recallId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT TMMH.MANHOUR_CODE,TRM.REBATE,TRM.GROUP_NO, \n" );
		sql.append(" TMMH.MANHOUR_NAME,TMMH.MANHOUR_NUM \n" );
		sql.append(" from TT_RECALL_MANHOUR_DCS TRM ,TT_MMH_MAN_HOUR_DCS TMMH \n" );
		sql.append(" WHERE  TRM.MANHOUR_ID = TMMH.MMH_ID  \n");
		sql.append("  		AND TRM.RECALL_ID = '"+recallId+"'  \n");
        List<Map<String, Object>> map = pageQuery(sql.toString(), null, getFunName());
        return map;
	}
	
	/**
	 * 配件信息
	 * @param recallId
	 * @return
	 */
	public List<Map<String, Object>> queryPart(String recallId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT DISTINCT TRP.PART_CODE,TRP.PART_NUM,TRP.CHECK_STATUS,TRP.CHANGE_RATIO,TRP.GROUP_NO, \n" );
		sql.append(" TPPB.PART_NAME,TPPB.PART_PRICE \n" );
		sql.append(" from TT_RECALL_PART_DCS TRP ,TT_PT_PART_BASE_DCS TPPB \n" );
		sql.append(" WHERE  TRP.PART_CODE = TPPB.PART_CODE  \n");
		sql.append("  		AND TRP.RECALL_ID = '"+recallId+"'  \n");
        List<Map<String, Object>> map = pageQuery(sql.toString(), null, getFunName());
        return map;
	}
	
	
	protected RecallServiceDTO wrapperDTO(Map map) {
		RecallServiceDTO dto = new RecallServiceDTO();
		LinkedList<RecallServiceVehicleDTO> vehicleListVO = new LinkedList<RecallServiceVehicleDTO>();//车辆信息
		LinkedList<RecallServiceLabourDTO> labourListVO = new LinkedList<RecallServiceLabourDTO>();//工时信息
		LinkedList<RecallServicePartDTO> partListVO = new LinkedList<RecallServicePartDTO>();//配件信息
		try {
			logger.info("====填充LinkedList车辆信息开始====");
			List<Map<String, Object>> vehicleList = this.queryModel(CommonUtils.checkNull(map.get("RECALL_ID"))); 
			for(int i=0;i<vehicleList.size();i++){
				RecallServiceVehicleDTO vehicleVo = new RecallServiceVehicleDTO();
				vehicleVo.setBrandCode(CommonUtils.checkNull(vehicleList.get(i).get("BRAND_CODE")));
				vehicleVo.setSeriesCode(CommonUtils.checkNull(vehicleList.get(i).get("SERIES_CODE")));
				vehicleVo.setModelCode(CommonUtils.checkNull(vehicleList.get(i).get("MODEL_CODE")));
				vehicleVo.setGoupNo(vehicleList.get(i).get("GROUP_NO")!=null?Integer.parseInt(vehicleList.get(i).get("GROUP_NO").toString()):0);
				vehicleVo.setRecallNo(CommonUtils.checkNull(vehicleList.get(i).get("RECALL_ID")));
				vehicleListVO.add(vehicleVo);
			}
			logger.info("====填充LinkedList车辆信息结束====");
			
			logger.info("====填充LinkedList工时信息开始====");
			List<Map<String, Object>> labourList = this.queryLabour(CommonUtils.checkNull(map.get("RECALL_ID")));
			if(labourList.size()>0){
				for(int i=0;i<labourList.size();i++){
					RecallServiceLabourDTO labourVo = new RecallServiceLabourDTO();
					labourVo.setLabourCode(CommonUtils.checkNull(labourList.get(i).get("MANHOUR_CODE")));//工时代码
					labourVo.setLabourName(labourList.get(i).get("MANHOUR_NAME")!=null?labourList.get(i).get("MANHOUR_NAME").toString():"-");//工时名称
					labourVo.setGoupNo(labourList.get(i).get("GROUP_NO")!=null?Integer.parseInt(labourList.get(i).get("GROUP_NO").toString()):0);//组合编号
					labourVo.setStdLabourHour(labourList.get(i).get("MANHOUR_NUM")!=null?Double.parseDouble(labourList.get(i).get("MANHOUR_NUM").toString()):0.00);//工时数
					labourVo.setDiscount(labourList.get(i).get("REBATE")!=null?Double.parseDouble(labourList.get(i).get("REBATE").toString()):0.00);//折扣
					labourListVO.add(labourVo);
				}
			}
			logger.info("====填充LinkedList工时信息结束====");
			
			logger.info("====填充LinkedList配件信息开始====");
			//填充LinkedList配件信息
			List<Map<String, Object>> partList = this.queryPart(CommonUtils.checkNull(map.get("RECALL_ID")));
			if(partList.size()>0){
				for(int i=0;i<partList.size();i++){
					RecallServicePartDTO partVo = new RecallServicePartDTO();
					partVo.setPartCode(CommonUtils.checkNull(partList.get(i).get("PART_CODE")));//配件代码
					partVo.setPartName(partList.get(i).get("PART_NAME")!=null?partList.get(i).get("PART_NAME").toString():"-");//配件名称
					partVo.setPartNum(partList.get(i).get("PART_NUM")!=null?Long.parseLong(partList.get(i).get("PART_NUM").toString()):0L);//配件数量
					partVo.setPartSalesPrice(partList.get(i).get("PART_PRICE")!=null?Double.parseDouble(partList.get(i).get("PART_PRICE").toString()):0.00);//销售单价
					Double partPrice = partList.get(i).get("PART_PRICE")!=null?Double.parseDouble(partList.get(i).get("PART_PRICE").toString()):0.00;
					Long partNum = partList.get(i).get("PART_NUM")!=null?Long.parseLong(partList.get(i).get("PART_NUM").toString()):0L;
					Double salseAmount = partPrice*partNum;
					partVo.setPartSalesAmount(salseAmount);//销售金额
					partVo.setGoupNo(partList.get(i).get("GROUP_NO")!=null?Integer.parseInt(partList.get(i).get("GROUP_NO").toString()):0);//组合编号
					partVo.setChangeRatio(partList.get(i).get("CHANGE_RATIO")!=null?Double.parseDouble(partList.get(i).get("CHANGE_RATIO").toString()):0.00);//更换比例
					partVo.setCheckStatus(partList.get(i).get("CHECK_STATUS")!=null?Integer.parseInt(partList.get(i).get("CHECK_STATUS").toString()):0);//类型
					partListVO.add(partVo);
				}
			}
			logger.info("====填充LinkedList配件信息结束====");
			dto.setRecallNo(CommonUtils.checkNull(map.get("RECALL_NO")));
			dto.setRecallName(CommonUtils.checkNull(map.get("RECALL_NAME")));
			dto.setRecallTheme(Integer.parseInt(CommonUtils.checkNull(map.get("RECALL_THEME"))));//召回类别 70411001 RT快速反应、70411002 Recall召回
			dto.setRecallType(Integer.parseInt(CommonUtils.checkNull(map.get("RECALL_TYPE"))));//召回方式 70421001 必选、70421002 非必选
			dto.setRecallStartDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("RECALL_START_DATE"))));
			dto.setRecallEndDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("RECALL_END_DATE"))));
			dto.setIsFixedCost(Integer.parseInt(CommonUtils.checkNull(map.get("IS_FIXED_COST"))));//是否固定费用10041001是 1001002否
			dto.setManHourCost(Double.parseDouble(CommonUtils.checkNull(map.get("MAN_HOUR_COST"))));
			dto.setPartCost(Double.parseDouble(CommonUtils.checkNull(map.get("PART_COST"))));
			dto.setOtherCost(Double.parseDouble(CommonUtils.checkNull(map.get("OTHER_COST"))));
			dto.setClaimApplyGuidance(CommonUtils.checkNull(map.get("CLAIM_APPLY_GUIDANCE")));
			dto.setReleaseTag(OemDictCodeConstants.RECALL_STATUS_02);
			dto.setReleaseDate(new Date());
			dto.setLabourListDto(labourListVO);
			dto.setPartListDto(partListVO);
			dto.setVehicleListDto(vehicleListVO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	
	/**
	 * 下发成功后，更新下发状态，下发时间
	 * @param currDate
	 */
	public void updateSend(String recallId){
//		String updateSql =   "update TT_RECALL_SERVICE_DCS set RECALL_STATUS='"+OemDictCodeConstants.RECALL_STATUS_02+"', RELEASE_DATE= sysdate()" +
//							 " where RECALL_ID = '"+recallId+"' ";
//		OemDAOUtil.execBatchPreparement(updateSql, null);
		TtRecallServiceDcsPO.update("RECALL_STATUS = ? AND RELEASE_DATE = ?", "RECALL_ID = ?", OemDictCodeConstants.RECALL_STATUS_02,new Date(System.currentTimeMillis()),recallId);
		
	}
}
