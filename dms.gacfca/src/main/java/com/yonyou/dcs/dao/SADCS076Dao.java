package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADCS076DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS076Dao extends OemBaseDAO {
	
	/*
	 * 查询下发参与召回车辆的DMS经销商代码
	 */
	public List<String> queryIDealerCode(String recallNo){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT TR.DMS_CODE\n");
		sql.append("FROM TT_RECALL_DEALER TRD,\n");
		sql.append("     TT_RECALL_SERVICE TRS,\n");
		sql.append("     TM_DEALER TD,\n");
		sql.append("     TI_DEALER_RELATION TR\n");
		sql.append("     WHERE  TRD.RECALL_ID = TRS.RECALL_ID\n");
		sql.append("     AND TRD.DEALER_CODE = TD.DEALER_ID\n");
		sql.append("     AND case when right(TD.DEALER_CODE,LENGTH(TD.DEALER_CODE)-(LENGTH(TD.DEALER_CODE)-1))='A' \n");
		sql.append("      then replace(TD.DEALER_CODE,'A','') else TD.DEALER_CODE end = TR.DCS_CODE  \n");
												/*参数召回编号*/
		sql.append("     AND TRS.RECALL_NO = '"+recallNo+"'\n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> dealist=null;
		if(null!=listmap&&listmap.size()>0){
			dealist=new ArrayList<>();
			for(int i=0;i<dealist.size();i++){
				dealist.add(CommonUtils.checkNull(listmap.get(i).get("DMS_CODE")));
			}
		}
	    return dealist;
	}
	
	/**
	 * 推送字段 VIN 责任经销商简称 召回编号 召回名称
	 * @Title: sendData  
	 * @param @param recallNo
	 * @param @param vin
	 * @param @return      
	 * @return List<SADCS076DTO>     
	 * @throws
	 */
	public List<SADCS076DTO> sendData(String recallNo,String vin){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TRV.VIN,TRS.RECALL_NO,TRS.RECALL_NAME,TD.DEALER_SHORTNAME \n");
		sql.append("FROM TT_RECALL_SERVICE TRS,\n");
		sql.append("     TT_RECALL_VEHICLE TRV,\n");
		sql.append("     TM_DEALER TD\n");
		sql.append("     WHERE TRS.RECALL_ID = TRV.RECALL_ID\n");
		sql.append("     AND TRV.DUTY_DEALER = TD.DEALER_ID\n");
										/*参数：召回编号，车架号*/
		sql.append("     AND TRS.RECALL_NO = '"+recallNo+"'\n");
		sql.append("     AND TRV.VIN = '"+vin+"'\n");
		
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<SADCS076DTO> dtolist=null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new ArrayList<>();
			for(int i=0;i<listmap.size();i++){
				SADCS076DTO dto = new SADCS076DTO();
				dto.setRecallNo(CommonUtils.checkNull(listmap.get(i).get("RECALL_NO")));//召回编号
				dto.setRecallName(CommonUtils.checkNull(listmap.get(i).get("RECALL_NAME")));//召回名称
				dto.setVin(CommonUtils.checkNull(listmap.get(i).get("VIN")));//车架号
				dto.setDealerName(CommonUtils.checkNull(listmap.get(i).get("DEALER_SHORTNAME")));//责任经销商
				dtolist.add(dto);
			}
		}
		return dtolist;
		
	}

	/*
	 * 查询召回数据
	 */
	public List<Map> queryService(){
		StringBuffer sql = new StringBuffer();
		sql.append("select s.RECALL_ID,s.RECALL_NO,s.RECALL_NAME,count(*) count \n");
		sql.append("from TT_RECALL_SERVICE s inner join TT_RECALL_VEHICLE v on s.RECALL_ID = v.RECALL_ID \n");
		sql.append("and v.RECALL_STATUS = '"+OemDictCodeConstants.ACTIVITY_VEHICLE_TYPE_02+"' \n");
		sql.append("and v.GCS_SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_NO+"' \n");
		sql.append("where  s.RECALL_STATUS ='"+OemDictCodeConstants.RECALL_STATUS_02+"' \n");
		sql.append("group by s.RECALL_ID, s.RECALL_NO, s.RECALL_NAME \n");
		sql.append("order by count desc \n");
		sql.append(" FETCH FIRST 1 ROW ONLY \n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
	    return listmap;
	}
	
	/**
	 * 查询召回经销商
	 */
	public List<String> queryDealer(String Service){
		StringBuffer sql = new StringBuffer();
		sql.append("select DISTINCT TR.DMS_CODE   \n");
		sql.append("from TT_RECALL_VEHICLE V, TT_RECALL_SERVICE S, TM_DEALER D,TI_DEALER_RELATION TR \n");
		sql.append("     where v.RECALL_ID = S.RECALL_ID and v.DUTY_DEALER = D.DEALER_ID   \n");
		sql.append("     and case when right(D.DEALER_CODE,LENGTH(D.DEALER_CODE)-(LENGTH(D.DEALER_CODE)-1))='A'  \n");
		sql.append("    then replace(D.DEALER_CODE,'A','') else D.DEALER_CODE end = TR.DCS_CODE  \n");
		sql.append("     and s.RECALL_NO = '"+Service+"'  \n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<String> dealist =new ArrayList<>();
		if(null!=listmap&&listmap.size()>0){
			for(int i=0;i<listmap.size();i++){
				dealist.add(CommonUtils.checkNull(listmap.get(i).get("DMS_CODE")));
			}
		}
	    return dealist;
	}
	
	/**
	 * 查询召回车辆
	 */
	public LinkedList<SADCS076DTO> queryVehicle(String Service){
		StringBuffer sql = new StringBuffer();
		sql.append(" select s.RECALL_NO,s.RECALL_NAME,v.VIN,D.DEALER_SHORTNAME    \n");
		sql.append(" from TT_RECALL_VEHICLE v, TT_RECALL_SERVICE s, TM_DEALER D \n");
		sql.append(" where s.RECALL_ID = v.RECALL_ID \n");
		sql.append("  and D.DEALER_ID = v.DUTY_DEALER  \n");
		sql.append("  and v.RECALL_STATUS = '"+OemDictCodeConstants.ACTIVITY_VEHICLE_TYPE_02+"'  \n");
		sql.append("  and v.GCS_SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_NO+"'  \n");
		sql.append("  and s.RECALL_NO = '"+Service+"' \n");
		sql.append(" FETCH FIRST 1000 ROW ONLY \n");
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<SADCS076DTO> dtolist=null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new LinkedList<>();
			for(int i=0;i<listmap.size();i++){
				SADCS076DTO dto = new SADCS076DTO();
				dto.setRecallNo(CommonUtils.checkNull(listmap.get(i).get("RECALL_NO")));//召回编号
				dto.setRecallName(CommonUtils.checkNull(listmap.get(i).get("RECALL_NAME")));//召回名称
				dto.setVin(CommonUtils.checkNull(listmap.get(i).get("VIN")));//车架号
				dto.setDealerName(CommonUtils.checkNull(listmap.get(i).get("DEALER_SHORTNAME")));//责任经销商
				dtolist.add(dto);
			}
		}
		return dtolist;
	}
}
