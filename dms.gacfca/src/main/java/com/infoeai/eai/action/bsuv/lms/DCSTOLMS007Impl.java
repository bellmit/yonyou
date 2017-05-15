package com.infoeai.eai.action.bsuv.lms; 

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.axis.encoding.Base64;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.dao.bsuv.lms.TiEcHitSingleDao;
import com.infoeai.eai.wsServer.bsuv.lms.EcCheckFeedBackVO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * @author wjs
 * @date 2016年4月29日 上午10:46:30
 * @description 功能描述  DCS线索校验结果反馈接口
 */ 
@SuppressWarnings("rawtypes")
@Service
public class DCSTOLMS007Impl extends BaseService implements DCSTOLMS007{
	private static final Logger logger = LoggerFactory.getLogger(DCSTOLMS007Impl.class);
	@Autowired
	private TiEcHitSingleDao dao;
	
	// DCS线索校验(Cue Check)接收===========结果反馈
	public EcCheckFeedBackVO[] sendBSUVCueCheckResult(String from,String to) throws Exception {
		//事务开启
		beginDbService();
		
		EcCheckFeedBackVO[] arrayVos=null;
		String excString="";
		Integer ifType=OemDictCodeConstants.IF_TYPE_YES;
		Date startTime=new Date();
		int size=0;
		try {
			logger.info("============推送到LMS====DCSTOLMS007  DCS线索校验结果反馈========开始=========");
			//存入历史记录表
			List<Map> poList=dao.getEcHitSingleFeedbackInfo(from, to);
			//List<EcCheckFeedBackVO> listVos=DataConvertUtil.copyListPoToListVo(poList, EcCheckFeedBackVO.class);
			//如果list>0遍历数据组成装成数组
			size=poList.size();
			if(null!=poList && poList.size()>0){
				arrayVos=new EcCheckFeedBackVO[poList.size()];
				for (int i = 0; i < poList.size(); i++) {
					Map poV=poList.get(i);
					EcCheckFeedBackVO vo=new EcCheckFeedBackVO();
					vo.setDEALER_CODE(CommonUtils.checkNull(poV.get("DEALER_CODE")));
					vo.setDEALER_NAME(Base64.encode(CommonUtils.checkNull(poV.get("DEALER_NAME")).getBytes("UTF-8")));
					vo.setIS_HIT_SINGLE(Integer.parseInt(CommonUtils.checkNull(poV.get("IS_HIT_SINGLE"),"0")));
					vo.setSOLD_BY(Base64.encode(CommonUtils.checkNull(poV.get("SOLD_BY")).getBytes("UTF-8")));//Base64加密
					vo.setSOLD_MOBILE(CommonUtils.checkNull(poV.get("SOLD_MOBILE")));
					vo.setTEL(CommonUtils.checkNull(poV.get("TEL")));
					arrayVos[i]=vo;
				}
			}
			dbService.endTxn(true);
			logger.info("============推送到LMS====DCSTOLMS007  DCS线索校验结果反馈========成功======"+poList.size()+"=条==");
		} catch (Exception e) {
			e.printStackTrace();
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=OemDictCodeConstants.IF_TYPE_NO;
			dbService.endTxn(false);
			logger.info("============推送到LMS====DCSTOLMS007  DCS线索校验结果反馈========异常=========" + e);
		} finally {
			Base.detach();
			dbService.clean();
			
			beginDbService();
			try {
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "线索校验结果反馈：DCS->LMS", startTime, size, ifType,excString, "", "", new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================线索校验结果反馈：DCS->LMS=========");
			}finally {
				Base.detach();
				dbService.clean();
			}
			logger.info("============推送到LMS====DCSTOLMS007  DCS线索校验结果反馈========结束=========");
		}
		return arrayVos;

	}
}
 