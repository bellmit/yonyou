package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.DiscountCouponDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SEDCS071Dao extends OemBaseDAO {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS071Dao.class);
	/**
	 * 查询卡券使用状态为使用中跟未使用的个数
	 */
	public List<Map<String, Object>> countVehicle(String vin){
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT * \n");
		sql.append("FROM TT_WECHAT_CARD_INFO where 1=1 \n");
		sql.append("	AND IS_DEL = 0  \n");
		sql.append("	AND IS_VALID = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		sql.append("	AND USE_STATUS IN ("+OemDictCodeConstants.CARD_VOUCHER_STATUS_01+","+OemDictCodeConstants.CARD_VOUCHER_STATUS_02+")  \n");
		if (Utility.testIsNotNull(vin)) {
			sql.append("  AND VIN ='"+vin+"' \n");
		}
		List<Map<String, Object>> listJJ = pageQuery(sql.toString(), null,getFunName());
		return listJJ;
	}
	
	public LinkedList<DiscountCouponDTO> queryVehicleDto(String vin,int countNum) throws Exception {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT \n");
		sql.append("  CARD_NO,CARD_NAME,CARD_TYPE,SUBTYPE,CARD_VALUE,BUY_AMOUNT, \n");
		sql.append("  ISSUE_DATE,to_char(START_DATE,'yyyy-MM-dd') START_DATE,to_char(END_DATE,'yyyy-MM-dd') END_DATE, \n");
		sql.append("  USE_DEALER,USE_PROJECT,USE_STATUS,SAME_OVERLAP_COUNT,IS_DIFFERENT_OVERLAP, \n");
		sql.append("  IS_VALID,IS_CARD_US_WECHAT \n");
		sql.append("FROM TT_WECHAT_CARD_INFO where 1=1 \n");
		sql.append("	AND IS_DEL = 0  \n");
		sql.append("	AND IS_VALID = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		logger.info("SEDCS071下发总数据数据：" + countNum);
		if(countNum == 1 || countNum == 0){
			sql.append("	AND USE_STATUS IN ("+OemDictCodeConstants.CARD_VOUCHER_STATUS_01+","+OemDictCodeConstants.CARD_VOUCHER_STATUS_02+")  \n");
		}else{
			sql.append("	AND USE_STATUS IN ("+OemDictCodeConstants.CARD_VOUCHER_STATUS_02+")  \n");
		}
		if (Utility.testIsNotNull(vin)) {
			sql.append("  AND VIN ='"+vin+"' \n");
		} 
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<DiscountCouponDTO> dtolist=null;
		if(null!=listmap&&listmap.size()>0){
			dtolist=new LinkedList<DiscountCouponDTO>();
			for(Map map:listmap){
				DiscountCouponDTO dto = new DiscountCouponDTO();
				dto.setCardNo(CommonUtils.checkNull(map.get("CARD_NO")));
				dto.setCardName(CommonUtils.checkNull(map.get("CARD_NAME")));
				dto.setCardType(Utility.getInt(CommonUtils.checkNull(map.get("CARD_TYPE"))));
				dto.setSubType(CommonUtils.checkNull(map.get("SUBTYPE")));
				dto.setCardValue(Utility.getDouble(CommonUtils.checkNull(map.get("CARD_VALUE"))));
				dto.setBuyAmount(Utility.getDouble(CommonUtils.checkNull(map.get("BUY_AMOUNT"))));
				dto.setIssueDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("ISSUE_DATE"))));
				dto.setStartDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("START_DATE"))));
				dto.setEndDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("END_DATE"))));
				dto.setUseDealer(CommonUtils.checkNull(map.get("USE_DEALER")));
				dto.setUseProject(CommonUtils.checkNull(map.get("USE_PROJECT")));
				dto.setUseStatus(Utility.getInt(CommonUtils.checkNull(map.get("USE_STATUS"))));
				dto.setSameOverlapCount(Utility.getInt(CommonUtils.checkNull(map.get("SAME_OVERLAP_COUNT"))));
				dto.setIsDifferentOverlap(Utility.getInt(CommonUtils.checkNull(map.get("IS_DIFFERENT_OVERLAP"))));
			}
		}
		if (dtolist == null || dtolist.size() <= 0) {
			logger.info("SEDCS071同步接口没有找到VIN：" + vin + "的车辆信息！");
		}
		return dtolist;
	}
	
}
