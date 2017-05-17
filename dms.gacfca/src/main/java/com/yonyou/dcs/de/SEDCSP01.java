package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SEDCSP01VO;
import com.yonyou.dcs.dao.SEDCSP01Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * @Description:同步查询获取配件清单信息(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCSP01  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP01.class);
	
	@Autowired
	SEDCSP01Dao dao;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 同步查询获取配件清单信息开始******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				SEDCSP01VO dto = new SEDCSP01VO();
				dto = (SEDCSP01VO) entry.getValue();
				List<SEDCSP01VO> list = dao.querySedcsP01VO(dto);
				if (list == null || list.size() <= 0) {
					return wrapperMsg(list);
				}
				logger.info("====同步查询获取配件清单信息结束====");
				DEMessage rmsg = wrapperMsg(list);
				return rmsg;
			}	
			
		}  catch(Throwable t) {
			logger.info("*************************** 同步查询获取配件清单信息出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 同步查询获取配件清单信息成功******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<SEDCSP01VO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			HashMap<String, Serializable> body = DEUtil.assembleBody(dtos);
			if (body != null && body.size() > 0) {
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMSP01");
					rmsg=assembleDEMessage("SEDMSP01", body);
					logger.info("结束下发SEDMSP01");
				} catch (Exception e) {
					logger.info("配件清单信息同步失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
}
