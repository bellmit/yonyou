package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS114Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.OwnerEntityDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:车辆实销上报私自调拨验收同步(DMS->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCS114  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS114.class);
	
	@Autowired
	SEDCS114Dao dao ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取车辆实销上报私自调拨验收信息******************************");
		try {
			List<OwnerEntityDTO> list=new ArrayList<OwnerEntityDTO>();
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				OwnerEntityDTO dto = new OwnerEntityDTO();
				dto = (OwnerEntityDTO) entry.getValue();
				list.add(dto);
			}	
			DEMessage rmsg = wrapperMsg(list);
			return rmsg;
		}  catch(Throwable t) {
			logger.info("*************************** 车辆实销上报私自调拨验收同步出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 车辆实销上报私自调拨验收同步成功******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<OwnerEntityDTO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			HashMap<String, Serializable> body = DEUtil.assembleBody(dtos);
			if (body != null && body.size() > 0) {
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMS018");
					rmsg=assembleDEMessage("SEDMS018", body);
					logger.info("结束下发SEDMS018");
				} catch (Exception e) {
					logger.info("车辆实销上报私自调拨验收同步失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
}
