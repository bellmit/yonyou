package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.SADMS056Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtTestDriveAnalysisPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS056CloudImpl
 * Description: 试乘试驾分析数据上报
 * @author DC
 * @date 2017年4月7日 下午8:06:58
 * result msg 1：成功 0：失败
 */
@Service
public class SADCS056CloudImpl extends BaseCloudImpl implements SADCS056Cloud {
	
	@Autowired
	SaDcs056Dao dao;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<SADMS056Dto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====试乘试驾分析数据上报接收开始===="); 
		for (SADMS056Dto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("试乘试驾分析数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====试乘试驾分析数据上报接收结束===="); 
		return msg;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized void insertData(SADMS056Dto vo) throws Exception {
		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		List<Map> seriesCodeList = dao.getSeriesCode(vo.getSeriesCode());
		for(int i=0; i<seriesCodeList.size();i++){
			Map<String,Object> seriesCode= seriesCodeList.get(i);
			String groupCode = (String) seriesCode.get("GROUP_CODE");
			TtTestDriveAnalysisPO analysisPO = new TtTestDriveAnalysisPO();
			analysisPO.setString("QUESTIONNAIRE_CODE", vo.getQuestionnaireCode());
			analysisPO.setString("SERIES_CODE", groupCode);
			analysisPO.setString("DEALER_CODE", dealerCode);
			analysisPO.setDate("FEEDBACK_DATE", vo.getSubmitDate());//反馈时间
			if(vo.getAnswerNo().equals("DB0001")){
				analysisPO.setLong("SATISFIED", vo.getSumDb000());//非常满意
			}
			if(vo.getAnswerNo().equals("DB0002")){
				analysisPO.setLong("SATISFIED", vo.getSumDb000());//满意
			}
			if(vo.getAnswerNo().equals("DB0003")){
				analysisPO.setLong("GENERALLY", vo.getSumDb000());//一般
			}
			analysisPO.setString("QUESTION_CODE", vo.getQuestionCode());
			analysisPO.setDate("CREATE_DATE", new Date());
			analysisPO.insert();//插入试驾试乘分析数据
		}
	}

}
