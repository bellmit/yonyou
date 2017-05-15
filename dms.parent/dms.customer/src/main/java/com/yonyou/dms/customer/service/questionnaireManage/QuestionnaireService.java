package com.yonyou.dms.customer.service.questionnaireManage;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.customer.AnswerDTO;
import com.yonyou.dms.common.domains.DTO.customer.AnswerGroupDTO;
import com.yonyou.dms.common.domains.DTO.customer.QuestionnaireDTO;
import com.yonyou.dms.common.domains.DTO.customer.TraceQuestionDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

@SuppressWarnings("rawtypes")
public interface QuestionnaireService {
	// 查询问题
	public PageInfoDto Queryquestions(Map<String, String> queryParam) throws ServiceBizException;

	// 新增问题
	void addquestionsInfo(TraceQuestionDTO queDto) throws ServiceBizException;

	// 查询问题组
	public PageInfoDto Queryanswers(Map<String, String> queryParam) throws ServiceBizException;

	// 新增问题组
	void addanswerInfo(AnswerGroupDTO queDto) throws ServiceBizException;

	// 新增问题
	void addanswerAInfo(AnswerDTO queDto) throws ServiceBizException;

	// 根据问题组编号查询问题
	public PageInfoDto searchAnswerByGroupNo(String id) throws ServiceBizException;

	// 修改问题
	void editQues(TraceQuestionDTO dto) throws ServiceBizException;

	public void editAnswers(Map<String, String> map) throws ServiceBizException;

	Map<String, Object> findByCode(String id) throws ServiceBizException;

	public List<Map> queryAnswersItem() throws ServiceBizException;

	Map<String, Object> findByNo(String id) throws ServiceBizException;

	void editAnswers(TraceQuestionDTO dto) throws ServiceBizException;
	
	Map<String, Object> findByAnswerNo(String id1,String id2) throws ServiceBizException;
	
	void editAnswer(TraceQuestionDTO dto) throws ServiceBizException;
	
	//public PageInfoDto searchAnswerDetail(String id) throws ServiceBizException;
	
	//public List<Map> searchAnswerDetail(Long id)throws ServiceBizException;//明细
	
	public PageInfoDto searchAnswerDetail(Map<String, String> queryParam,String id) throws ServiceBizException;
	
	Map<String, Object> searchAnswerGroup(String id) throws ServiceBizException;
	
	void updateAnswerGroup(TraceQuestionDTO dto) throws ServiceBizException;
	
	public PageInfoDto searchQuestionnaires(Map<String, String> queryParam) throws ServiceBizException;
	
	void saveQuestionnaire(QuestionnaireDTO quesDto) throws ServiceBizException;
	
	Map<String, Object> seachQuestionnaire(String id) throws ServiceBizException;
	
	void updateQuestionnaire(QuestionnaireDTO map) throws ServiceBizException;
	
	public PageInfoDto seachQuestions(Map<String, String> queryParam,String id) throws ServiceBizException;
	
	public PageInfoDto editQuestions(Map<String, String> queryParam) throws ServiceBizException;
	
	void initTraceQuestion(QuestionnaireDTO map) throws ServiceBizException;
}
