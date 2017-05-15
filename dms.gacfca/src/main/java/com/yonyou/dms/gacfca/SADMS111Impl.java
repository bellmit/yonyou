package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.LimitPriceSeriesDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.LimitSeriesDatainfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWxBookingMaintainPackagePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 限价车系及维修类型数据下发接口
 * @author Administrator
 *
 */
@Service
public class SADMS111Impl implements SADMS111 {
	
	final Logger logger = Logger.getLogger(SADMS111Impl.class);

	@Override
	public int getSADMS111(String dealerCode, List<LimitPriceSeriesDTO> voList) {
		logger.info("==========SADMS111Impl执行===========");
		try{
			//判断是否为空,循环操作，根据业务相应的修改数据
			if (voList != null && voList.size() > 0){
				for (LimitPriceSeriesDTO limitPriceSeriesDTO : voList){
					if(dealerCode==null){
						logger.debug("dealerCode 为空，方法中断");
						return 0;
					}
					String [] repairTypeList = null;
					if (Utility.testString(limitPriceSeriesDTO.getRepairTypeCode())) {
						repairTypeList = limitPriceSeriesDTO.getRepairTypeCode().split(",");
					}
					if (repairTypeList != null && repairTypeList.length > 0) {
						for(String dmsRepairTypeCode : repairTypeList){
							dmsRepairTypeCode = getDmsRepairTypeCode(dmsRepairTypeCode);
							if(Utility.testString(limitPriceSeriesDTO.getBrandCode()) && Utility.testString(limitPriceSeriesDTO.getSeriesCode()) && Utility.testString(dmsRepairTypeCode)){
							    logger.debug("from LimitSeriesDatainfoPO DEALER_CODE = "+dealerCode+" and BRAND_CODE = "+limitPriceSeriesDTO.getBrandCode()+" and SERIES_CODE = "+limitPriceSeriesDTO.getSeriesCode()+" and REPAIR_TYPE_CODE = "+dmsRepairTypeCode);
								List<LimitSeriesDatainfoPO> list = LimitSeriesDatainfoPO.findBySQL("DEALER_CODE = ? and BRAND_CODE = ? and SERIES_CODE = ? and REPAIR_TYPE_CODE = ?", 
							    		dealerCode,limitPriceSeriesDTO.getBrandCode(),limitPriceSeriesDTO.getSeriesCode(),dmsRepairTypeCode);
								
							    LimitSeriesDatainfoPO fpo = new LimitSeriesDatainfoPO();
							    fpo.setString("DEALER_CODE",dealerCode);
							    fpo.setString("BRAND_CODE",limitPriceSeriesDTO.getBrandCode());
							    fpo.setString("SERIES_CODE",limitPriceSeriesDTO.getSeriesCode());
							    fpo.setString("REPAIR_TYPE_CODE",dmsRepairTypeCode);
							    if(Utility.testString(limitPriceSeriesDTO.getLimitPriceRate()))
							    	fpo.setFloat("LIMIT_PRICE_RATE",Utility.getFloat(limitPriceSeriesDTO.getLimitPriceRate().toString()));
							    if(Utility.testString(limitPriceSeriesDTO.getIsValid()))
							    	fpo.setInteger("IS_VALID",limitPriceSeriesDTO.getIsValid());
							    if(Utility.testString(limitPriceSeriesDTO.getDownTimestamp()))
							    	fpo.setDate("DOWN_TIMESTAMP",limitPriceSeriesDTO.getDownTimestamp());
								
								if(list.size()>0){
									Date downDate = ((LimitSeriesDatainfoPO)list.get(0)).getDate("DOWN_TIMESTAMP");
									if(downDate!=null && downDate.getTime() >= limitPriceSeriesDTO.getDownTimestamp().getTime()){
										logger.debug("============>>DE下发时序不对，更新失败！");
										continue;//跳出本次循环
									}
									else{
										fpo.setString("UPDATE_BY",CommonConstants.DE_CREATE_UPDATE_BY);
										fpo.setDate("UPDATE_AT",Utility.getCurrentDateTime());
										StringBuffer sb = new StringBuffer(fpo.toUpdate());
										sb.append(" and BRAND_CODE = " + limitPriceSeriesDTO.getBrandCode());
										sb.append(" and SERIES_CODE = " + limitPriceSeriesDTO.getSeriesCode());
										sb.append(" and REPAIR_TYPE_CODE" + dmsRepairTypeCode);
										sb.append(" and D_KEY = " + CommonConstants.D_KEY);
										DAOUtil.findAll(sb.toString(), null);
									}
								}else{
									
									fpo.setInteger("OEM_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));
									fpo.setString("CREATE_BY",CommonConstants.DE_CREATE_UPDATE_BY);
									fpo.setDate("CREATE_AT",Utility.getCurrentDateTime());
									fpo.saveIt();
								}
							}
						}
					}
				}
			}
			return 1;
		}catch (Exception e){
			logger.debug(e);
			e.printStackTrace();
			return 0;
		}finally{
			logger.info("==========SADMS111Impl结束===========");
		}
	}
	
	/**
	 * @description 根据维修类型代码上下端对应转化关系函数
	 * @param dcsRepairTypeCode
	 */
	private String getDmsRepairTypeCode(String dcsRepairTypeCode) {
		String returnValue = null;
		if (dcsRepairTypeCode != null && !"".equals(dcsRepairTypeCode.trim())) {
			if ("41001007".equals(dcsRepairTypeCode)) {
				returnValue = "BJPQ";//钣金喷漆
			} else if ("41001012".equals(dcsRepairTypeCode)) {
				returnValue = "BXIU";//保修
			} else if ("41001001".equals(dcsRepairTypeCode)) {
				returnValue = "CGBY";//常规保养
			} else if ("41001016".equals(dcsRepairTypeCode)) {
				returnValue = "FUWU";//服务活动
			} else if ("41001003".equals(dcsRepairTypeCode)) {
				returnValue = "NBXL";//内部修理
			} else if ("41001004".equals(dcsRepairTypeCode)) {
				returnValue = "NFWX";//内部返工维修
			} else if ("41001014".equals(dcsRepairTypeCode)) {
				returnValue = "SQWX";//PDI检查
			} else if ("41001002".equals(dcsRepairTypeCode)) {
				returnValue = "PTWX";//普通维修
			} else if ("41001010".equals(dcsRepairTypeCode)) {
				returnValue = "QITA";//其他
			} else if ("41001017".equals(dcsRepairTypeCode)) {
				returnValue = "SBWS";//三包维修
			} else if ("41001011".equals(dcsRepairTypeCode)) {
				returnValue = "SCBY";//首次保养
			} else if ("41001013".equals(dcsRepairTypeCode)) {
				returnValue = "SHYX";//善意维修
			} else if ("41001005".equals(dcsRepairTypeCode)) {
				returnValue = "WFWX";//外部返工维修
			} else if ("41001008".equals(dcsRepairTypeCode)) {
				returnValue = "XCZH";//精品装潢
			} else if ("41001018".equals(dcsRepairTypeCode)) {
				returnValue = "YXYX";//营销活动
			} else if ("41001009".equals(dcsRepairTypeCode)) {
				returnValue = "ZDHD";//自店活动
			} else {
				returnValue = "";
			}
		}
		return returnValue;
	}
}
