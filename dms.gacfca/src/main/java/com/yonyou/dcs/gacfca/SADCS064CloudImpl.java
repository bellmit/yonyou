package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiPdicheckReportPO;
import com.yonyou.dcs.dao.ActivityResultDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDTO;
import com.yonyou.dms.DTO.gacfca.TtVehiclePdiResultDetailDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SADCS064CloudImpl extends BaseCloudImpl implements SADCS064Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS064CloudImpl.class);
	@Autowired
	ActivityResultDao dao ;
	
	@Override
	public String receiveDate(List<TtVehiclePdiResultDTO> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("*************** SADCS064Cloud PDI检查上报接收开始 *******************");
			for (TtVehiclePdiResultDTO dto : dtos) {
				insertData(dto);
			}
			logger.info("*************** SADCS064Cloud PDI检查上报完成 ********************");
			
		} catch (Exception e) {
			logger.error("*************** SADCS064Cloud PDI检查上报异常 *****************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		return msg;
	}
	/**
	 * 接收上报上来的PDI检查信息
	 * @param dto
	 * @throws Exception
	 */
	private void insertData(TtVehiclePdiResultDTO dto) {
		try {
			TiPdicheckReportPO pdi = new TiPdicheckReportPO();
			if (Utility.testIsNotNull(dto.getDealerCode())) {
				Map<String, Object> dcsInfoMap = dao.getSaDcsDealerCode(dto.getDealerCode());
				String dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE"));
				pdi.setString("DEALER_CODE",dealerCode);
			}
			pdi.setString("VIN", dto.getVin());//vin号
			pdi.setString("PDI_RESULT",dto.getPdiResult());//PDI检查结果
			pdi.setDate("PDI_CHECK_DATE",dto.getPdiCheckDate());//PDI检查时间
			pdi.setDate("PDI_SUBMIT_DATE",dto.getPdiSubmitDate());//PDI检查提交时间
			pdi.setString("TH_REPORT_NO",dto.getThReportNo());//技术报告号
			pdi.setString("PDI_URL",dto.getPdiUrl());//附件URL
			pdi.setDouble("MILEAGE",dto.getMileage());//公里数
			pdi.setLong("CREATE_BY", DEConstant.DE_UPDATE_BY);
			pdi.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
			LinkedList<TtVehiclePdiResultDetailDTO> vprdList = dto.getVprdList();
			if (vprdList != null && vprdList.size() > 0) {
				for (int i = 0; i < vprdList.size(); i++) {
					TtVehiclePdiResultDetailDTO pdiDetaildto= (TtVehiclePdiResultDetailDTO) vprdList.get(i);
					pdi.setString("PDI_REMARK",pdiDetaildto.getPdiRemark());//故障描述
					pdi.insert();
				}
			}else{
				pdi.insert();
			}
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}
		
	}
	
}
