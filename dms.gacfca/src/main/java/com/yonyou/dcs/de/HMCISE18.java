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

import com.infoservice.dms.cgcsl.vo.RepairOrderReStatusVO;
import com.yonyou.dcs.dao.RepairOrderResultStatusDao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * @Description:二手车置换客户返利数据接收
 * @author xuqinqin
 */
@Service
public class HMCISE18  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(HMCISE18.class);
	@Autowired
	RepairOrderResultStatusDao dao ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的二手车置换意向明细数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				RepairOrderReStatusVO vo = new RepairOrderReStatusVO();
				vo = (RepairOrderReStatusVO) entry.getValue();
				// 获得取消结算返回给下端的DTO列表
				List<RepairOrderReStatusVO> list = dao.queryRepairVO(vo);// 在该方法内部有检查索赔单是否存在的功能
				
				DEMessage rmsg = wrapperMsg(list, null);
				logger.info("====同步查询工单取消结算状态上报信息结束====");
				return rmsg;
			}
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的二手车置换意向明细数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的二手车置换意向明细数据******************************");
		return null;
	}
	/**
	 * 处理返回的DEMessage
	 * @param vos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(List<RepairOrderReStatusVO> vos, String msg) {
		HashMap<String, Serializable> body = DEUtil.assembleBody(vos);
		BaseImpl de = new BaseImpl();
		DEMessage rmsg = null;
		try {
			rmsg = de.assembleDEMessage("HMCISE18", body);
		} catch (Exception e) {
			logger.error("同步查询工单取消结算状态上报信息失败", e);
		}
		return rmsg;
	}
}
