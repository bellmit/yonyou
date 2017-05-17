package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.VehicleCustomerVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SADCS073Cloud;
import com.yonyou.dms.DTO.gacfca.VehicleCustomerDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description: 车主资料接收接口
 * 接收经销商修改后的车主资料
 * @author xuqinqin 
 */
@Service
public class SADCS073  extends BaseImpl  implements DEAction{
	private static final Logger logger = LoggerFactory.getLogger(SADCS073.class);
	
	@Autowired
	SADCS073Cloud cloud ;
	
	@Autowired
	SADCS072 sadcs072 ;
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** SADCS073  开始获取车主资料******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<VehicleCustomerDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
			logger.info("*************************** SADCS073  成功获取车主资料******************************");
			//SADCS072 车主资料下发 
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				VehicleCustomerVO vo = new VehicleCustomerVO();
				vo = (VehicleCustomerVO) entry.getValue();
				sadcs072.sendData(vo.getVin(), vo.getEntityCode());
			}
		}  catch(Throwable t) {
			logger.info("*************************** SADCS073  车主资料上传出错******************************");
			t.printStackTrace();
			try {
			} catch(Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		return null;
	}
	private void setDTO(LinkedList<VehicleCustomerDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			VehicleCustomerVO vo = new VehicleCustomerVO();
			VehicleCustomerDTO dto = new VehicleCustomerDTO();
			vo = (VehicleCustomerVO)entry.getValue();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
