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

import com.infoservice.dms.cgcsl.vo.SEDCSP07VO;
import com.yonyou.dcs.dao.SEDCSP07Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:获取配件开票接货状态(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCSP07  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP07.class);
	
	@Autowired
	SEDCSP07Dao dao;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 获取配件开票接货状态开始******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				SEDCSP07VO vo= new SEDCSP07VO();
				vo = (SEDCSP07VO) entry.getValue();
				List<SEDCSP07VO> list = dao.querySedcsP07VO(vo);
				if (list == null || list.size() <= 0) {
					return wrapperMsg(list);
				}
				logger.info("====获取配件开票接货状态结束====");
				DEMessage rmsg = wrapperMsg(list);
				return rmsg;
			}	
			
		}  catch(Throwable t) {
			logger.info("*************************** 获取配件开票接货状态出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 获取配件开票接货状态成功******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<SEDCSP07VO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			HashMap<String, Serializable> body = DEUtil.assembleBody(dtos);
			if (body != null && body.size() > 0) {
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMSP07");
					rmsg=assembleDEMessage("SEDMSP07", body);
					logger.info("结束下发SEDMSP07");
				} catch (Exception e) {
					logger.info("获取配件开票接货状态失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
}
