package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.CLDMS005Dto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 配件基本信息下发
 * @author Administrator
 */
@Service
public class CLDMS005Impl implements CLDMS005 {

	final Logger logger = Logger.getLogger(CLDMS005Impl.class);

	/**
	 * @description 更新配件基本信息，更新配件批次信息
	 * @param voList
	 * @param dealerCode
	 */
	@Override
	public int getCLDMS005(LinkedList<CLDMS005Dto> voList, String dealerCode) {
		logger.info("==========CLDMS005Impl执行===========");
		try{
			if(dealerCode == null || dealerCode.isEmpty()){
				logger.debug("dealerCode为空,方法中断");
				return 0;		//执行失败
			}
			if(voList != null && !voList.isEmpty()){
				for (CLDMS005Dto cldms005Dto : voList) {
					TmPartInfoPO tmPartinsertBean = new TmPartInfoPO();
					tmPartinsertBean.setString("DEALER_CODE", dealerCode);
					tmPartinsertBean.setString("PART_NO", cldms005Dto.getPartCode());
					tmPartinsertBean.setInteger("D_KEY", CommonConstants.D_KEY);
					TmPartInfoPO insertBean = new TmPartInfoPO();
					insertBean.setString("DEALER_CODE",dealerCode);
					insertBean.setString("PART_NO", cldms005Dto.getPartCode());
					insertBean.setString("PART_NAME", cldms005Dto.getPartName());
					insertBean.setString("PART_NAME_EN", cldms005Dto.getPartEnglishName());
					insertBean.setString("OPTION_NO", cldms005Dto.getOldPartCode());
					insertBean.setDouble("MIN_PACKAGE", cldms005Dto.getPackageNum().doubleValue());
					insertBean.setString("UNIT_CODE", cldms005Dto.getPartNuit());
					insertBean.setInteger("PART_TYPE", cldms005Dto.getPartType());
					if(Utility.testString(cldms005Dto.getIsSJV())&&Utility.testString(cldms005Dto.getIsMOP())){
						if(cldms005Dto.getIsSJV() == 10041001 && cldms005Dto.getIsMOP() == 10041001){
							//菲亚特与进口车共用的配件
							insertBean.setInteger("IS_SJV", Integer.parseInt(CommonConstants.DICT_IS_YES));
							insertBean.setInteger("IS_MOP", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}else if(cldms005Dto.getIsSJV() == 10041002 && cldms005Dto.getIsMOP() == 10041001){
							//进口车配件
							insertBean.setInteger("IS_SJV", Integer.parseInt(CommonConstants.DICT_IS_NO));
							insertBean.setInteger("IS_MOP", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}else if(cldms005Dto.getIsSJV() == 10041001 && cldms005Dto.getIsMOP() == 10041002){
							//菲亚特配件
							insertBean.setInteger("IS_SJV", Integer.parseInt(CommonConstants.DICT_IS_YES));
							insertBean.setInteger("IS_MOP", Integer.parseInt(CommonConstants.DICT_IS_NO));
						}
					}
					if(cldms005Dto.getIsSales()!=null){
						if(cldms005Dto.getIsSales()==10041002){
							insertBean.setInteger("IS_C_SALE", Integer.parseInt(CommonConstants.DICT_IS_NO));
						}else{
							insertBean.setInteger("IS_C_SALE", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}
					}
					if(cldms005Dto.getIsPurchase()!=null){
						if(cldms005Dto.getIsPurchase()==10041002){
							insertBean.setInteger("IS_C_PURCHASE", Integer.parseInt(CommonConstants.DICT_IS_NO));
						}else{
							insertBean.setInteger("IS_C_PURCHASE", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}
					}

					if(cldms005Dto.getIsNormal()!=null){
						if(cldms005Dto.getIsNormal()==10041001){
							insertBean.setInteger("IS_COMMON_IDENTITY", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}else if(cldms005Dto.getIsNormal()==10041002){
							insertBean.setInteger("IS_COMMON_IDENTITY", Integer.parseInt(CommonConstants.DICT_IS_NO));
						}else{
							insertBean.setInteger("IS_COMMON_IDENTITY", Integer.parseInt(CommonConstants.DICT_IS_YES));
						}
					}
					insertBean.setInteger("PART_PROPERTY", cldms005Dto.getPartProperty());
					insertBean.setString("REMARK",cldms005Dto.getRemark());
					if(cldms005Dto.getReportType()!=null){
						insertBean.setInteger("REPORT_WAY", cldms005Dto.getReportType());
					}else{
						insertBean.setInteger("REPORT_WAY",Integer.parseInt(CommonConstants.DICT_FIAT_PART_REPORT_WAY_NORMAL));
					}
					insertBean.setString("PART_VEHICLE_MODEL",cldms005Dto.getModelCodes());
					if(cldms005Dto.getPartModel()!=null){
						switch(cldms005Dto.getPartModel()){
							case 80011001:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_CONVENTIONAL_PART));
								break;
							case 80011002:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_THE_THIRD));
								break;
							case 80011003:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_MAINTAIN_PRODUCT));
								break;
							case 80011004:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_BATTERY));
							case 80011005:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_SUPPLIES));
								break;
							case 80011006:
								insertBean.setInteger("PART_GROUP_CODE", Integer.parseInt(CommonConstants.DICT_PART_CLASS_CHASSIS_NUMBER));
							default:
								insertBean.setInteger("PART_GROUP_CODE", cldms005Dto.getPartModel());
								break;
						}
					}
					insertBean.setInteger("PART_GROUP_CODE", cldms005Dto.getPartModel());
					insertBean.setInteger("PART_STATUS", cldms005Dto.getPartStatus());
					insertBean.setInteger("DOWN_TAG", Integer.parseInt(CommonConstants.DICT_IS_YES));
					insertBean.setInteger("OEM_TAG", Integer.parseInt(CommonConstants.DICT_IS_YES));
					insertBean.setString("FROM_ENTITY", CommonConstants.DICT_DEFAULT_TOTAL_FACTORY);
					insertBean.setString("PROVIDER_CODE", "000000000000");
					insertBean.setDouble("OEM_LIMIT_PRICE", cldms005Dto.getPartPrice());
					insertBean.setDouble("INSTRUCT_PRICE", cldms005Dto.getPartPrice());
					insertBean.setDouble("LIMIT_PRICE", cldms005Dto.getPartPrice());
					insertBean.setDouble("CLAIM_PRICE", cldms005Dto.getDnpPrice());
					insertBean.setDouble("REGULAR_PRICE", (cldms005Dto.getDnpPrice()/1.08));

					logger.debug("from tmPartinsertBean DEALER_CODE="+dealerCode+" and PART_NO = "+cldms005Dto.getPartCode()+" and D_KEY = "+CommonConstants.D_KEY);

					LazyList<TmPartInfoPO> tmPartinsertBeans = tmPartinsertBean.findBySQL("DEALER_CODE=? and PART_NO = ? and D_KEY = ? ", new Object[]{dealerCode,cldms005Dto.getPartCode(), CommonConstants.D_KEY});
					if (tmPartinsertBeans != null && !tmPartinsertBeans.isEmpty()) {
						insertBean.setString("UPDATE_BY", "1");
						insertBean.setDate("UPDATED_AT", new Date());
						insertBean.saveIt();
						//更新配件库存 配件批次 中的价格 add lim 2013-11-21
						if (Utility.testString(cldms005Dto.getPartCode())&& cldms005Dto.getDnpPrice()!=null ) {
							TmPartStockPO.update("CLAIM_PRICE = ?", "PART_NO = ? AND DEALER_CODE = ?", cldms005Dto.getPartCode(), cldms005Dto.getPartCode().trim(),dealerCode);
							TmPartStockItemPO.update("CLAIM_PRICE = ?", "PART_NO = ? and DEALER_CODE = ?", cldms005Dto.getPartCode(),cldms005Dto.getPartCode().trim(),dealerCode);
						}
					} else {
						insertBean.setString("CREATE_BY", "1");
						insertBean.setDate("CREATE_AT", new Date());
						insertBean.setInteger("D_KEY", CommonConstants.D_KEY);
						insertBean.saveIt();
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS005Impl结束===========");
		}
	}
}
