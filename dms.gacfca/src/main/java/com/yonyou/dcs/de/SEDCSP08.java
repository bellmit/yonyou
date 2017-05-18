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

import com.infoservice.dms.cgcsl.vo.SEDCSP08VO;
import com.yonyou.dcs.dao.SEDCSP08Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:配件订单开票接货清单(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCSP08  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP08.class);
	
	@Autowired
	SEDCSP08Dao dao;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 获取配件订单开票接货清单开始******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				SEDCSP08VO vo= new SEDCSP08VO();
				vo = (SEDCSP08VO) entry.getValue();
				List<SEDCSP08VO> list = dao.querySEDCSP08VO(vo);
				if (list == null || list.size() <= 0) {
					return wrapperMsg(list);
				}
				logger.info("====获取配件订单开票接货清单结束====");
				DEMessage rmsg = wrapperMsg(list);
				return rmsg;
			}	
			
		}  catch(Throwable t) {
			logger.info("*************************** 获取配件订单开票接货清单出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 获取配件订单开票接货清单成功******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<SEDCSP08VO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			HashMap<String, Serializable> body = DEUtil.assembleBody(dtos);
			if (body != null && body.size() > 0) {
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMSP08");
					rmsg=assembleDEMessage("SEDMSP08", body);
					logger.info("结束下发SEDMSP08");
				} catch (Exception e) {
					logger.info("获取配件订单开票接货清单失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
}
