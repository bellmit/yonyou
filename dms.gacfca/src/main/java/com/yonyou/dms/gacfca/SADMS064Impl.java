package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS064DTO;
import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDetailDTO;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SADMS064Impl implements SADMS064{
	
//	@Autowired SADMS114 SADMS114;
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS064Impl.class);
	@Override
	public int getSADMS064(String vin) throws ServiceBizException{
		String msg ="1";
		LinkedList<SADMS064DTO> resList = new LinkedList<SADMS064DTO>();	
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VehiclePdiResultPO tecpo = VehiclePdiResultPO.findByCompositeKeys(dealerCode,vin);
		if(!StringUtils.isNullOrEmpty(tecpo)){
			List tecpodetails = VehiclePdiResultDetailPO.find("VIN = ? ", vin);
			SADMS064DTO dto = new SADMS064DTO();
			dto.setDealerCode(dealerCode);
			try {
				dto.setPdiCheckDate(Utility.parseString2Date(tecpo.getString("PDI_CHECK_DATE"), "yyyy-MM-dd HH:mm:ss"));
				dto.setPdiResult(Utility.getInt(tecpo.getString("PDI_RESULT")));
				dto.setPdiSubmitDate(Utility.parseString2Date(tecpo.getString("PDI_SUBMIT_DATE"), "yyyy-MM-dd HH:mm:ss"));
				dto.setPdiResult(Utility.getInt(tecpo.getString("PDI_RESULT")));
				dto.setPdiSubmitDate(Utility.parseString2Date(tecpo.getString("PDI_SUBMIT_DATE"), "yyyy-MM-dd HH:mm:ss"));
				dto.setPdiUrl(tecpo.getString("PDI_URL"));
				dto.setThReportNo(tecpo.getString("TH_REPORT_NO"));
				dto.setMileage(Utility.getDouble(tecpo.getString("MILEAGE")));
				dto.setVin(vin);
				if(tecpodetails!=null&&tecpodetails.size()>0){
					LinkedList<TtVehiclePdiResultDetailDTO> resultListdatail = new LinkedList<TtVehiclePdiResultDetailDTO>();
					for(int i = 0;i<tecpodetails.size();i++){
						TtVehiclePdiResultDetailDTO  detailVo = new TtVehiclePdiResultDetailDTO();
						VehiclePdiResultDetailPO ttpd = (VehiclePdiResultDetailPO) tecpodetails.get(i);
						detailVo.setDealerCode(dealerCode);
						detailVo.setPdiRemark(ttpd.getString("PDI_REMARK"));
						resultListdatail.add(detailVo);
					}
					dto.setVprdList(resultListdatail);
				}
				resList.add(dto);
				
			} catch (Exception e) {
				e.printStackTrace();
				msg= "0";
			}
		}
		return Integer.parseInt(msg);
	}

}
