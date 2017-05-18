package com.yonyou.dms.gacfca;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS052DTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtAbsorbRatePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 吸收率报表上报
 * @author Administrator
 *
 */
@Service
public class SADMS052CoudImpl implements SADMS052Coud {
	final Logger logger = Logger.getLogger(SADMS052CoudImpl.class);

	@Override
	public LinkedList<SADMS052DTO> getSADMS052(String dealerCode, String year, String month) {
		logger.info("==========SADMS052Impl执行===========");
		try{ 
			if(!Utility.testString(year) || !Utility.testString(month)){
			    Calendar calm = Calendar.getInstance();		    
			    if (calm.get(Calendar.DATE) != 5){
			    	logger.debug("当前日期，不允许上报");
			    	return null;
			    }	
//			    //获取上月上报
			    calm.add(Calendar.MONTH,-1);
			    
	            year = String.valueOf(calm.get(Calendar.YEAR));
	            month = String.valueOf(calm.get(Calendar.MONTH) + 1);
	            if (month.length() == 1){
	            	month = "0"+month;	
	            }
			}
           //定义VO
			LinkedList<SADMS052DTO> resultList = new LinkedList<SADMS052DTO>();
			//查询当月或则手工填写后的月份数据 （仅限本月月尾 或则补录上月数据）g
			logger.debug("from TtAbsorbRatePO DEALER_CODE = "+dealerCode+" and ABSORB_YEAR = "+year+" and ABSORB_MONTH = "+month+" and D_key = "+CommonConstants.D_KEY);
			List<TtAbsorbRatePO> listAbs = TtAbsorbRatePO.findBySQL("DEALER_CODE = ? and ABSORB_YEAR = ? and ABSORB_MONTH = ? and D_key = ?", 
					dealerCode,year,month,CommonConstants.D_KEY);
			if (listAbs !=null && !listAbs.isEmpty()) {
				TtAbsorbRatePO ttAbsorbRatePO = listAbs.get(0);
				  SADMS052DTO SADMS052Dto = new SADMS052DTO();
				  SADMS052Dto.setEntityCode(dealerCode);
				  SADMS052Dto.setEntityName(ttAbsorbRatePO.getString("ENTITY_NAME"));
				  SADMS052Dto.setAbsorbYear(year);
				  SADMS052Dto.setAbsorbMonth(month);
				  SADMS052Dto.setServiceBusinessAmount(ttAbsorbRatePO.getDouble("SERVICE_BUSINESS_AMOUNT"));
				  SADMS052Dto.setServiceGrossProfit(ttAbsorbRatePO.getDouble("SERVICE_GROSS_PROFIT"));
				  SADMS052Dto.setServiceGrossProfitRate(ttAbsorbRatePO.getDouble("SERVICE_GROSS_PROFIT_RATE"));
				  SADMS052Dto.setEntityAmount(ttAbsorbRatePO.getDouble("ENTITY_AMOUNT"));
				  SADMS052Dto.setAbsorbRate(ttAbsorbRatePO.getDouble("ABSORB_RATE"));
				  SADMS052Dto.setCustomerDepotRate(ttAbsorbRatePO.getDouble("CUSTOMER_DEPOT_RATE"));
				  SADMS052Dto.setBpValueMonth(ttAbsorbRatePO.getDouble("BP_VALUE_MONTH"));
				  SADMS052Dto.setJpValueMonth(ttAbsorbRatePO.getDouble("JP_VALUE_MONTH"));
				  SADMS052Dto.setDegreeBpValueMonth(ttAbsorbRatePO.getDouble("DEGREE_BP_VALUE_MONTH"));
				  SADMS052Dto.setDegreeJpValueMonth(ttAbsorbRatePO.getDouble("DEGREE_JP_VALUE_MONTH"));
				  SADMS052Dto.setCreateBy(ttAbsorbRatePO.getLong("CREATE_BY"));
				  SADMS052Dto.setCreateDate(ttAbsorbRatePO.getDate("CREATE_AT"));
				  SADMS052Dto.setUpdateBy(ttAbsorbRatePO.getLong("UPDATE_BY"));
				  SADMS052Dto.setUpdateDate(ttAbsorbRatePO.getDate("UPDATE_AT"));
				  resultList.add(SADMS052Dto);
			}			
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SADMS051Impl结束===========");
		}
	}

}
