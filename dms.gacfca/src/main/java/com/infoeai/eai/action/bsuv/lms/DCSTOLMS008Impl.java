package com.infoeai.eai.action.bsuv.lms; 

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.lms.TiEcFollowUpFeedbackDao;
import com.infoeai.eai.wsServer.bsuv.lms.TrailCustVO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author wjs
 * @date 2016年4月29日 上午10:52:12
 * @description 功能描述    官网客户跟进信息推送接口（服务端）
 */
@Service
public class DCSTOLMS008Impl extends BaseService implements DCSTOLMS008{
	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS008Impl.class);
	
	@Autowired
	private TiEcFollowUpFeedbackDao dao;

	// 官网客户跟进(Follow Up)信息推送
	@SuppressWarnings("rawtypes")
	public TrailCustVO[] sendBSUVCustFollowUpInfo(String from, String to) throws Exception {
		//事务开启
		beginDbService();
		
		TrailCustVO[] arrayVos=null;
		Date startTime=new Date();
		String excString="";
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		int size=0;
		try {
			logger.info("================DCSTOLMS008推送官网客户跟进信息推送========开始=========");	
			//存入历史记录表
			List<Map> poList=dao.getEcFollowUpFeedbackInfo(from, to);
			SimpleDateFormat sdf = new SimpleDateFormat("");
			size=poList.size();
			if(null!=poList && poList.size()>0){
				arrayVos=new TrailCustVO[poList.size()];
				for (int i = 0; i < poList.size(); i++) {
					Map po=poList.get(i);
					TrailCustVO vo=new TrailCustVO();
					vo.setDEALER_CODE(CommonUtils.checkNull(po.get("DEALER_CODE")));
					vo.setEC_ORDER_NO(CommonUtils.checkNull(po.get("EC_ORDER_NO")));
					vo.setTEL(CommonUtils.checkNull(po.get("TEL")));
					vo.setTRAIL_DATE(CommonUtils.checkNull(po.get("TRAIL_DATE")).equals("")?null:sdf.parse(CommonUtils.checkNull(po.get("TRAIL_DATE"))));
					arrayVos[i]=vo;
				}
				
			}
			dbService.endTxn(true);
			logger.info("================DCSTOLMS008推送官网客户跟进信息推送========成功==="+poList.size()+"=条=====");
		} catch (Exception e) {
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			dbService.endTxn(false);
			logger.info("================DCSTOLMS008推送官网客户跟进信息推送========异常=========" + e);
		} finally {
			Base.detach();
			dbService.clean();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "推送官网客户跟进信息：DCS->LMS", startTime, size, ifType, excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================推送官网客户跟进信息：DCS->LMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			logger.info("================DCSTOLMS008推送官网客户跟进信息推送========结束=========");
		}
		return arrayVos;

	}
}
 