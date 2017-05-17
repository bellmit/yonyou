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

import com.infoservice.dms.cgcsl.vo.SEDCSP02VO;
import com.yonyou.dcs.dao.SEDCSP02Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:同步查询获取经销商可用额度(DMS->DCS->SAP->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCSP02  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP02.class);
	
	@Autowired
	SEDCSP02Dao dao;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 同步查询获取经销商可用额度开始******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				SEDCSP02VO vo= new SEDCSP02VO();
				vo = (SEDCSP02VO) entry.getValue();
				List<SEDCSP02VO> list = dao.querySedcsP02VO(vo);
				if (list == null || list.size() <= 0) {
					return wrapperMsg(list);
				}
				logger.info("====同步查询获取经销商可用额度结束====");
				DEMessage rmsg = wrapperMsg(list);
				return rmsg;
			}	
			
		}  catch(Throwable t) {
			logger.info("*************************** 同步查询获取经销商可用额度出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 同步查询获取经销商可用额度成功******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<SEDCSP02VO> dtos) {
		if (dtos != null && dtos.size() > 0) {
			HashMap<String, Serializable> body = DEUtil.assembleBody(dtos);
			if (body != null && body.size() > 0) {
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMSP02");
					rmsg=assembleDEMessage("SEDMSP02", body);
					logger.info("结束下发SEDMSP02");
				} catch (Exception e) {
					logger.info("经销商可用额度同步失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
}
