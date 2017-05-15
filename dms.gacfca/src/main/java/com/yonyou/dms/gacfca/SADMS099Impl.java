package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmWxMaintainLabourDTO;
import com.yonyou.dms.DTO.gacfca.TmWxMaintainPackageRcvDTO;
import com.yonyou.dms.DTO.gacfca.TmWxMaintainPartDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtWxBookingMaintainPackagePO;
import com.yonyou.dms.common.domains.PO.basedata.TtWxMaintainPackageLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWxMaintainPackagePartPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 保养套餐修改上报接口
 * @author Administrator
 *
 */
@Service
public class SADMS099Impl implements SADMS099 {
	
	final Logger logger = Logger.getLogger(SADMS099Impl.class);

	@Override
	public List<TmWxMaintainPackageRcvDTO> getSADMS099(String dealerCode, Long userId, String packageCode,
			String packagePrice) {
		logger.info("==========SADMS099Impl执行===========");
		try{
			LinkedList<TmWxMaintainPackageRcvDTO> resultList = new LinkedList<TmWxMaintainPackageRcvDTO>();
			//获取被修改的保养套餐code
			if(Utility.testString(packageCode)){	
				TmWxMaintainPackageRcvDTO vo = new TmWxMaintainPackageRcvDTO();
				//根据code找到修改后的保养套餐
				logger.debug("update TtWxBookingMaintainPackagePO set PACKAGE_PRICE = "+packagePrice+",UPDATE_BY = "+userId+",UPDATE_AT = "+Utility.getCurrentDateTime()+" where DEALER_CODE = "+dealerCode+" and D_KEY = "+CommonConstants.D_KEY+" and PACKAGE_COOD = "+packageCode);
				TtWxBookingMaintainPackagePO.update("PACKAGE_PRICE = ?,UPDATE_BY = ?,UPDATE_AT = ?",
						"DEALER_CODE = ? and D_KEY = ? and PACKAGE_COOD = ?", 
						Double.valueOf(packagePrice),userId,Utility.getCurrentDateTime(),dealerCode,CommonConstants.D_KEY,packageCode);
				
				logger.debug("DEALER_CODE = "+dealerCode+" and PACKAGE_CODE ="+packageCode+" and D_KEY = "+CommonConstants.D_KEY);
				LazyList<TtWxBookingMaintainPackagePO> list = TtWxBookingMaintainPackagePO.findBySQL("DEALER_CODE = ? and PACKAGE_CODE =? and D_KEY = ?", 
						dealerCode,packageCode,CommonConstants.D_KEY);
				if(list != null){
					TtWxBookingMaintainPackagePO temp = list.get(0);
					vo.setDealerCode(dealerCode);
					vo.setPackageCode(packageCode);
					vo.setTotalAmount(temp.getDouble("PACKAGE_PRICE"));
					vo.setCreateDate(temp.getDate("CREATE_AT"));
					vo.setEngineDesc(temp.getString("EXHAUST_QUANTITY"));
					vo.setMaintainEnddate(temp.getDate("MAINTAIN_ENDDAY"));
					vo.setMaintainStartdate(temp.getDate("MAINTAIN_STARTDAY"));
					vo.setMaintainStartmileage(temp.getDouble("MAINTAIN_STARTMILEAGE"));
					vo.setMaintainEndmileage(temp.getDouble("MAINTAIN_ENDMILEAGE"));
					vo.setModelYear(temp.getString("MODEL_YEAR"));
					vo.setOemCompanyId(temp.getLong("OEM_COMPANY_ID"));
					vo.setOemType(temp.getInteger("OEM_TYPE"));
					if(Utility.testString(temp.getString("OIL_TYPE"))){
						vo.setOileType(Integer.parseInt(temp.getString("OIL_TYPE")));						
					}
					vo.setPackageName(temp.getString("PACKAGE_NAME"));
					vo.setPackageType(temp.getInteger("PACKAGE_TYPE"));
					vo.setSeriesCode(temp.getString("SERIES_CODE"));
					
					LinkedList<TmWxMaintainLabourDTO> labourVoList =new LinkedList<TmWxMaintainLabourDTO>();
					TmWxMaintainLabourDTO labourVo = null;
					//根据套餐代码找到对应的维修项目明细
					logger.debug("DEALER_CODE = "+dealerCode+" and PACKAGE_CODE = "+packageCode+" and D_KEY = "+CommonConstants.D_KEY);
					List<TtWxMaintainPackageLabourPO> labourList = TtWxMaintainPackageLabourPO.findBySQL("DEALER_CODE = ? and PACKAGE_CODE = ? and D_KEY = ?", 
							dealerCode,packageCode,CommonConstants.D_KEY);
					if(labourList!= null && labourList.size()>0){
						for(TtWxMaintainPackageLabourPO labourPo : labourList){
							labourVo = new TmWxMaintainLabourDTO();
							labourVo.setLabourCode(labourPo.getString("LABOUR_CODE"));
							labourVo.setLabourName(labourPo.getString("LABOUR_NAME"));
							if(Utility.testString(labourPo.getFloat("STDLABOUR_HOUR"))){
								labourVo.setFrt(labourPo.getFloat("STDLABOUR_HOUR"));								
							}else{
								labourVo.setFrt(0f);
							}
							if(Utility.testString(labourPo.getFloat("LABOUR_PRICE"))){
								labourVo.setLabourPrice(Double.valueOf(labourPo.getFloat("LABOUR_PRICE")));								
							}
							labourVo.setLabourFee(labourPo.getDouble("LABOUR_AMOUNT"));
							labourVo.setLabourCreateDate(labourPo.getDate("CREATE_AT"));
							labourVo.setLabourDealType(labourPo.getInteger("LABOUR_DEAL_TYPE"));
							labourVoList.add(labourVo);
						}
					}
					vo.setTmWxMaintainLabourDTOs(labourVoList);
					//根据套餐代码找到对应的配件
					LinkedList<TmWxMaintainPartDTO> partVoList = new LinkedList<TmWxMaintainPartDTO>();
					TmWxMaintainPartDTO partVo = null;
					logger.debug("DEALER_CODE = "+dealerCode+" and PACKAGE_CODE = "+packageCode+" and D_KEY = "+CommonConstants.D_KEY);
					LazyList<TtWxMaintainPackagePartPO> partList = TtWxMaintainPackagePartPO.findBySQL("DEALER_CODE = ? and PACKAGE_CODE = ? and D_KEY = ?", 
							dealerCode,packageCode,CommonConstants.D_KEY);
					if(partList!=null && partList.size()>0){
						for(TtWxMaintainPackagePartPO partPo : partList){
							partVo = new TmWxMaintainPartDTO();
							partVo.setLabourCode(partPo.getString("LABOUR_CODE"));
							partVo.setPartCode(partPo.getString("PART_NO"));
							partVo.setPartName(partPo.getString("PARTNAME"));
							partVo.setPartPrice(partPo.getDouble("PART_SALES_PRICE"));
							partVo.setPartFee(partPo.getDouble("PART_SALES_AMOUNT"));
							partVo.setAmount(Double.valueOf(partPo.getFloat("PART_QUANTITY")));
							partVo.setPartCreateDate(partPo.getDate("CREATE_AT"));
							partVo.setPartDealType(partPo.getInteger("PART_DEAL_TYPE"));
							if(Utility.testString(partPo.getInteger("IS_MAIN"))&& partPo.getInteger("IS_MAIN")==12781001){
								partVo.setIsMain(10041001);								
							}else{
								partVo.setIsMain(10041002);
							}
							partVoList.add(partVo);
						}
					}
					vo.setTmWxMaintainPartDTOs(partVoList);
				}
				resultList.add(vo);
			}
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return null;	
		}finally{
			logger.info("==========SADMS099Impl结束===========");
		}
	}
}