package com.yonyou.dms.manage.service.basedata.questionnaire;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 问卷名称查询service
 * @author Administrator
 *
 */
public interface QuestionnaireNameService {
	
	 public List<Map> queryQuestionnaireName(Map<String, String> queryParams)throws ServiceBizException;
}
