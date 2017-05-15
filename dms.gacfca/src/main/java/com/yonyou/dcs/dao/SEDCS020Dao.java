package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.RecallServiceClearDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtRecallServiceDcsPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS020Dao extends OemBaseDAO {
	
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
	 * 需要下发的召回活动数据
	 * @return
	 */
	public List<RecallServiceClearDTO> queryAllInfo(String recallId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("   	TRS.RECALL_NO,  --召回活动编号  \n");
		sql.append("    TRS.RECALL_NAME,  --召回名称 \n");
		sql.append("    TRS.RECALL_STATUS  --召回状态 \n");
		sql.append("FROM TT_RECALL_SERVICE_DCS TRS \n");
		sql.append("  where TRS.RECALL_ID = '"+recallId+"'  \n");
		List<Object> parame=new ArrayList<Object>();
		parame.add(recallId);
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), parame);
		List<RecallServiceClearDTO> dtolist = null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new ArrayList<RecallServiceClearDTO>();
			for(Map map:listmap){
				RecallServiceClearDTO dto=wrapperDTO(map);
				dtolist.add(dto);
			}
		}
		return dtolist;
	}
	
	protected RecallServiceClearDTO wrapperDTO(Map map) {
		RecallServiceClearDTO vo = new RecallServiceClearDTO();
		try {
			vo.setRecallNo(CommonUtils.checkNull(map.get("RECALL_NO")));//召回编号
			vo.setRecallName(CommonUtils.checkNull(map.get("RECALL_NAME")));//召回名称
			vo.setRecallStatus(OemDictCodeConstants.RECALL_STATUS_03.toString());//召回状态
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}

	
	/**
	 * 下发成功后，更新下发状态，下发时间
	 * @param currDate
	 */
	public void updateSend(String recallId){
//		String updateSql =   "update TT_RECALL_SERVICE_DCS set RECALL_STATUS='"+OemDictCodeConstants.RECALL_STATUS_03+"', RELEASE_DATE= sysdate()" +
//							 " where RECALL_ID = '"+recallId+"' ";
//		OemDAOUtil.execBatchPreparement(updateSql, null);
		TtRecallServiceDcsPO.update("RECALL_STATUS = ? AND RELEASE_DATE = ?", "RECALL_ID = ?", OemDictCodeConstants.RECALL_STATUS_03,new Date(System.currentTimeMillis()),recallId);
		
	}
}
