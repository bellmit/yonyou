package com.yonyou.dms.gacfca;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmWxMaintainLabourDTO;
import com.yonyou.dms.DTO.gacfca.TmWxMaintainPackageDTO;
import com.yonyou.dms.DTO.gacfca.TmWxMaintainPartDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtWxBookingMaintainPackagePO;
import com.yonyou.dms.common.domains.PO.basedata.TtWxMaintainPackageLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWxMaintainPackagePartPO;

/**
 * @description 接收保养套餐主数据
 * @author Administrator
 *
 */
@Service
public class CLDMS018Impl implements CLDMS018 {

	final Logger logger = Logger.getLogger(CLDMS018Impl.class);

	@Override
	public int getCLDMS018(String dealerCode, List<TmWxMaintainPackageDTO> voList) {
		logger.info("==========CLDMS018Impl执行===========");
		try{
			if (dealerCode == null || "".equals(dealerCode.trim())) {
				logger.debug("dealerCode为空，方法中断");
				return 0;
			}
			if(voList!=null && voList.size()>0){
				for(int i=0;i<voList.size();i++){
					TmWxMaintainPackageDTO tmWxMaintainPackageDTO = (TmWxMaintainPackageDTO)voList.get(i);
					if(Utility.testString(tmWxMaintainPackageDTO.getPackageCode())){
						//新增保养套餐
						TtWxBookingMaintainPackagePO packagePO = new TtWxBookingMaintainPackagePO();
						packagePO.setString("DEALER_CODE",dealerCode);
						packagePO.setInteger("OEM_TAG",12781001);
						packagePO.setString("PACKAGE_CODE",tmWxMaintainPackageDTO.getPackageCode());
						packagePO.setString("PACKAGE_NAME",tmWxMaintainPackageDTO.getPackageName());
						packagePO.setDouble("PACKAGE_PRICE",tmWxMaintainPackageDTO.getTotalAmount());
						packagePO.setString("MODEL_YEAR",tmWxMaintainPackageDTO.getModelYear());
						if(Utility.testString(tmWxMaintainPackageDTO.getSeriesCode())){
							packagePO.setString("SERIES_CODE",tmWxMaintainPackageDTO.getSeriesCode());							
						}
						packagePO.setString("EXHAUST_QUANTITY",tmWxMaintainPackageDTO.getEngineDesc());
						if(Utility.testString(tmWxMaintainPackageDTO.getOileType())){
							packagePO.setString("OIL_TYPE",tmWxMaintainPackageDTO.getOileType().toString());							
						}
						packagePO.setLong("OEM_COMPANY_ID",tmWxMaintainPackageDTO.getOemCompanyId());
						packagePO.setDate("MAINTAIN_STARTDAY",tmWxMaintainPackageDTO.getMaintainStartdate());
						packagePO.setDate("MAINTAIN_ENDDAY",tmWxMaintainPackageDTO.getMaintainEnddate());
						packagePO.setDouble("MAINTAIN_STARTMILEAGE",tmWxMaintainPackageDTO.getMaintainStartmileage());
						packagePO.setDouble("MAINTAIN_ENDMILEAGE",tmWxMaintainPackageDTO.getMaintainEndmileage());
						packagePO.setInteger("OEM_TYPE",tmWxMaintainPackageDTO.getOemType());
						packagePO.setInteger("PACKAGE_TYPE",tmWxMaintainPackageDTO.getPackageType());
						packagePO.setString("CREATE_BY","1");
						packagePO.setDate("CREATE_AT",tmWxMaintainPackageDTO.getCreateDate());
						packagePO.saveIt();

						//新增保养套餐包含的维修项目
						if(tmWxMaintainPackageDTO.getTmWxMaintainLabourDTOs()!=null && !tmWxMaintainPackageDTO.getTmWxMaintainLabourDTOs().isEmpty()){
							for(TmWxMaintainLabourDTO labourVo : tmWxMaintainPackageDTO.getTmWxMaintainLabourDTOs()){
								TtWxMaintainPackageLabourPO labourPO = new TtWxMaintainPackageLabourPO();
								labourPO.setString("DEALER_CODE",dealerCode);
								labourPO.setDouble("DISCOUNT",1.0);
								labourPO.setString("LABOUR_CODE",labourVo.getLabourCode());
								labourPO.setString("LABOUR_NAME",labourVo.getLabourName());
								labourPO.setString("PACKAGE_CODE",tmWxMaintainPackageDTO.getPackageCode());
								if(Utility.testString(labourVo.getLabourPrice())){
									labourPO.setFloat("LABOUR_PRICE",labourVo.getLabourPrice().floatValue());									
								}
								labourPO.setDouble("LABOUR_AMOUNT",labourVo.getLabourFee());
								labourPO.setFloat("STD_LABOUR_HOUR",labourVo.getFrt());
								labourPO.setString("CREATE_BY","1");
								labourPO.setDate("CREATE_AT",labourVo.getLabourCreateDate());
								labourPO.setInteger("LABOUR_DEAL_TYPE",labourVo.getLabourDealType());
								labourPO.saveIt();
							}
						}
						//新增保养套餐包含的配件
						if(tmWxMaintainPackageDTO.getTmWxMaintainPartDTOs()!= null && tmWxMaintainPackageDTO.getTmWxMaintainPartDTOs().size()>0){
							for(TmWxMaintainPartDTO partVo : tmWxMaintainPackageDTO.getTmWxMaintainPartDTOs()){
								TtWxMaintainPackagePartPO partPO = new TtWxMaintainPackagePartPO();
								partPO.setString("DEALER_CODE",dealerCode);
								partPO.setString("PACKAGE_CODE",tmWxMaintainPackageDTO.getPackageCode());
								partPO.setString("LABOUR_CODE",partVo.getLabourCode());
								partPO.setString("PART_NO",partVo.getPartCode());
								partPO.setString("PART_NAME",partVo.getPartName());
								if(Utility.testString(partVo.getAmount())){
									partPO.setFloat("PART_QUANTITY",partVo.getAmount().floatValue());									
								}
								partPO.setDouble("PART_SALES_PRICE",partVo.getPartPrice());
								partPO.setDouble("PART_SALES_AMOUNT",partVo.getPartFee());
								partPO.setInteger("IS_MAIN",10041001==partVo.getIsMain()?12781001:12781002);	
								partPO.setString("CREATE_BY","1");
								partPO.setDate("CREATE_AT",partVo.getPartCreateDate());
								partPO.setInteger("PART_DEAL_TYPE",partVo.getPartDealType());
								partPO.saveIt();
							}
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS018Impl结束===========");
		}
	}
}
