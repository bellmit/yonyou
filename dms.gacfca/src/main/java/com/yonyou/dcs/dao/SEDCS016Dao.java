package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDCS016DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS016Dao extends OemBaseDAO {
	
	/**
	 * 需要下发的行管经销商数据
	 * @return
	 */
	public List<SEDCS016DTO> queryAllInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    C.DMS_CODE,   \n");//经销商 
		sql.append("    D.DEALER_SHORTNAME,   \n");//经销商简称
		sql.append("    D.IS_RESTRICT,  \n");//是否行管经销商  
		sql.append("    D.IS_OTHER \n");//他牌车维修控制 
		sql.append("FROM TM_DEALER D,TI_DEALER_RELATION C    \n");
		sql.append("WHERE (MMH_SEND_STATUS IS NULL OR MMH_SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_NO+"')  \n");//下发状态，未下发
		sql.append("  	  AND STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' // 有效经销商 \n");
		sql.append("	  AND DEALER_TYPE = "+OemDictCodeConstants.DEALER_TYPE_DWR+"\n" );
		sql.append("	  AND C.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("  	  AND D.DEALER_CODE=concat(C.DCS_CODE,'A' )\n");
		List<Map> list=OemDAOUtil.findAll(sql.toString(), null);
		List<SEDCS016DTO> dtolist = new ArrayList<>();
		if(null!=list&&list.size()>0){
			for(Map map:list){
				SEDCS016DTO dto=new SEDCS016DTO();
				dto.setEntityCode(CommonUtils.checkNull(map.get("DMS_CODE")));//经销商代码
				dto.setDealerShortname(CommonUtils.checkNull(map.get("DEALER_SHORTNAME")));//经销商简称
				dto.setIsRestrict(CommonUtils.checkNull(map.get("IS_RESTRICT")));//是否行管经销商
				dto.setIsOther(CommonUtils.checkNull(map.get("IS_OTHER")));//它牌车维修控制
			}
		}
		return dtolist;
	}
	
	/**
	 * 下发成功后，更新下发状态，下发时间
	 * @param currDate
	 */
	public void updateSend(){
		String updateSql =   "update TM_DEALER set MMH_SEND_STATUS='"+OemDictCodeConstants.IF_TYPE_YES+"', MMH_SEND_BY = "+DEConstant.DE_CREATE_BY+",MMH_SEND_DATE=CURRENT_TIMESTAMP" +
							 " where (MMH_SEND_STATUS IS NULL OR MMH_SEND_STATUS = '"+OemDictCodeConstants.IF_TYPE_NO+"') " +
							 " AND STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"'  AND DEALER_TYPE = '"+OemDictCodeConstants.DEALER_TYPE_DWR+"' ";
		OemDAOUtil.execBatchPreparement(updateSql, null);
//		List<Object> params = new ArrayList<Object>();
//		params.add(OemDictCodeConstants.IF_TYPE_YES);
//		params.add(DEConstant.DE_CREATE_BY);
//		params.add(new Date());
//		params.add(OemDictCodeConstants.IF_TYPE_NO);
//		params.add(OemDictCodeConstants.STATUS_ENABLE);
//		params.add(OemDictCodeConstants.DEALER_TYPE_DWR);
//		TmDealerPO.update("MMH_SEND_STATUS = ? AND MMH_SEND_BY = ? AND MMH_SEND_DATE = ?", "MMH_SEND_STATUS = ? AND STATUS = ? AND DEALER_TYPE = ?", params.toArray());
	}

}
