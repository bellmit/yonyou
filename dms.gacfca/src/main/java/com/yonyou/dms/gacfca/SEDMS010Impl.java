package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.RepairOrderSchemeDTO;
import com.yonyou.dms.DTO.gacfca.RoLabourSchemeDTO;
import com.yonyou.dms.DTO.gacfca.RoRepairPartSchemeDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.EmployeePo;
import com.yonyou.dms.common.domains.PO.basedata.TmAscBasicinfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 上报三包维修方案
 * @author Administrator
 *
 */
@Service
public class SEDMS010Impl implements SEDMS010 {
	
	final Logger logger = Logger.getLogger(SEDMS010Impl.class);

	@Override
	public List<RepairOrderSchemeDTO> getSEDMS010(String dealerCode, Long userId, String roNo, Integer sSchemeStatus,Double inMileage,String serviceAdvisorAss,String vin,String ownerName,String model,String roCreateDate,String roTroubleDesc,String reMark1, String[] labourCode,
			String[] labourName,String[] stdLabourHour,String[] lbRemark,String[] partCode,String[] ptMum,String[] warmTimes,
			String[] warmItemName,String[] partNo,String[] partName,String[] partQuantity) {
		logger.info("==========SEDMS010Impl执行===========");
		List<RepairOrderSchemeDTO> roVoList = new ArrayList<RepairOrderSchemeDTO>();
		try {
			//三包方案状态为“待审核”或“方案调整”，才需要上报
			if (Utility.testString(roNo) && Utility.testString(String.valueOf(sSchemeStatus))
					&& (CommonConstants.DICT_SCHEME_STATUS_ADJUST.equals(String.valueOf(sSchemeStatus))|| CommonConstants.DICT_SCHEME_STATUS_ADUITING.equals(String.valueOf(sSchemeStatus)))){

				String entityName = TmAscBasicinfoPO.findByCompositeKeys(dealerCode).getString("ASC_NAME");
				RepairOrderSchemeDTO repairOrderSchemeDTO = new RepairOrderSchemeDTO();
				repairOrderSchemeDTO.setDealerCode(dealerCode);
				repairOrderSchemeDTO.setEntityName(entityName);
				repairOrderSchemeDTO.setRoNo(roNo);
				repairOrderSchemeDTO.setInMileage(inMileage);

				if (Utility.testString(serviceAdvisorAss) ){
					EmployeePo getEmp = EmployeePo.findByCompositeKeys(dealerCode,serviceAdvisorAss);				
					repairOrderSchemeDTO.setServiceAdvisorTel(getEmp.getString("MOBILE")); //开单人电话
					repairOrderSchemeDTO.setServiceAdvisor(getEmp.getString("EMPLOYEE_NAME"));//开单人
				}
				logger.debug("from TmVehiclePO DEALER_CODE = "+dealerCode+" and VIN = "+vin);
				TmVehiclePO tmVehiclePO = (TmVehiclePO) TmVehiclePO.findBySQL("DEALER_CODE = ? and VIN = ?", dealerCode,vin).get(0);
				repairOrderSchemeDTO.setSalesDate(tmVehiclePO.getDate("SALES_DATE"));
				repairOrderSchemeDTO.setLicense(tmVehiclePO.getString("LICENSE"));
				repairOrderSchemeDTO.setVin(vin);
				repairOrderSchemeDTO.setOwnerName(ownerName);
				repairOrderSchemeDTO.setModel(model);
				repairOrderSchemeDTO.setRoCreateDate(Utility.getTimeStamp(roCreateDate));
				repairOrderSchemeDTO.setRoTroubleDesc(roTroubleDesc);
				repairOrderSchemeDTO.setDealerOpinoin(reMark1); //经销商处理意见

				if(labourCode != null && labourCode.length > 0){
					LinkedList<RoLabourSchemeDTO> lcList = new LinkedList<RoLabourSchemeDTO>();
					for (int li = 0; li < labourCode.length; li++){
						RoLabourSchemeDTO labourVo = new RoLabourSchemeDTO();
						labourVo.setLabourCode(labourCode[li]);
						labourVo.setLabourName(labourName[li]);
						labourVo.setRemark(lbRemark[li]);
						if (Utility.testString(stdLabourHour[li]))
							labourVo.setStdLabourHour(Utility.getDouble(stdLabourHour[li]));
						else
							labourVo.setStdLabourHour(0D);

						lcList.add(labourVo);					
					}
					repairOrderSchemeDTO.setLabourVoList(lcList);
				}
				//到达预警的配件信息，返回DCS
				if (partNo != null && partNo.length > 0){
					LinkedList<RoRepairPartSchemeDTO> partList = new LinkedList<RoRepairPartSchemeDTO>();
					for (int pi = 0; pi < partNo.length; pi++){
						int getIndex = getIndexOfPartList(partNo[pi], partCode);
						if ( getIndex > -1){
							RoRepairPartSchemeDTO partVo = new RoRepairPartSchemeDTO();
							partVo.setPartNo(partNo[pi]);
							partVo.setPartName(partName[pi]);
							partVo.setPartQuantity(Utility.getFloat(partQuantity[pi]));
							Integer ptMum1,warmTimes1;
							ptMum1 = (ptMum != null) && Utility.testString(ptMum[getIndex]) ? Utility.getInt(ptMum[getIndex]) : 0;
							partVo.setPtMum(ptMum1);
							warmTimes1 = (warmTimes != null) && Utility.testString(warmTimes[getIndex]) ? Utility.getInt(warmTimes[getIndex]) : 0;
							partVo.setWarmTimes(warmTimes1);
							if ((warmItemName != null) && Utility.testString(warmItemName[getIndex]))
								partVo.setWarnItemName(warmItemName[getIndex]);

							partList.add(partVo);
						}
					}
					repairOrderSchemeDTO.setRepairPartVoList(partList);
				}
				roVoList.add(repairOrderSchemeDTO);
			}
			return roVoList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS010Impl结束===========");
		}
	}
	
	/**
	 * @description 在partNoList中查找partNo,如果存在返回数组下标，如果不存在，返回-1;数据异常 返回-1
	 * @param partNo
	 * @param partNoList
	 * @return
	 */
	private int getIndexOfPartList(String partNo, String[] partNoList){
		if (partNoList == null) return -1;
		for (int i = 0; i < partNoList.length; i++){
			if (partNoList[i].equals(partNo)) return i;
		}
		return -1;
	}

}
