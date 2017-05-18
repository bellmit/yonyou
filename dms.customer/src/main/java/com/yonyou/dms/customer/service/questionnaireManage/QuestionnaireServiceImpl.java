package com.yonyou.dms.customer.service.questionnaireManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.AnswerDTO;
import com.yonyou.dms.common.domains.DTO.customer.AnswerGroupDTO;
import com.yonyou.dms.common.domains.DTO.customer.QuestionnaireDTO;
import com.yonyou.dms.common.domains.DTO.customer.TraceQuestionDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmEntityPrivateFieldPO;
import com.yonyou.dms.common.domains.PO.customer.AnswerGroupPO;
import com.yonyou.dms.common.domains.PO.customer.AnswerPO;
import com.yonyou.dms.common.domains.PO.customer.QuestionRelationPO;
import com.yonyou.dms.common.domains.PO.customer.TraceQuestionPO;
import com.yonyou.dms.common.domains.PO.customer.TraceQuestionnairePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Override
	public PageInfoDto Queryquestions(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT  C.DEALER_CODE, C.QUESTION_CODE,  C.ANSWER_GROUP_NO, C.QUESTION_NAME, C.QUESTION_CONTENT, C.QUESTION_DESC, C.QUESTION_TYPE,1 as VER, ");
		sb.append(" C.IS_MUST_FILLED,C.IS_STAT_REPORT, ");
		sb.append("	 C.DOWN_TAG,C.IS_VALID");
		sb.append("  FROM ( " + CommonConstants.VT_TRACE_QUESTION + " ) C " + "WHERE 1=1  ");
		List<Object> queryList = new ArrayList<Object>();
		// this.setWhere(sb, queryParam, queryList);
		System.out.println(sb.toString());
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	@Override
	public PageInfoDto Queryanswers(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"  SELECT C.DEALER_CODE, C.ANSWER_GROUP_NO,  C.ANSWER_GROUP_NAME, C.ANSWER_GROUP_DESC, C.DOWN_TAG,C.IS_VALID");
		sb.append("  FROM ( " + CommonConstants.VT_ANSWER_GROUP + " ) C " + "WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		this.setWhere(sb, queryParam, queryList);
		System.out.println(sb.toString());
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	private void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {

	}

	@Override
	public PageInfoDto searchAnswerByGroupNo(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT C.DEALER_CODE,  C.ANSWER_NO,C.ANSWER_GROUP_NO,  D.ANSWER_GROUP_NAME, C.ANSWER,C.ANSWER_DESC, C.DOWN_TAG, ");
		sb.append("  C.IS_VALID  FROM ( " + CommonConstants.VT_ANSWER + " ) C, ( " + CommonConstants.VT_ANSWER_GROUP
				+ " ) D  WHERE  C.ANSWER_GROUP_NO = D.ANSWER_GROUP_NO");
		sb.append(" and C.ANSWER_GROUP_NO = ?");

		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	@Override
	public void editQues(TraceQuestionDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String name = FrameworkUtil.getLoginInfo().getUserName();
		System.out.println(dealerCode + "----" + name);
		TraceQuestionPO answerPoCon = TraceQuestionPO.findFirst("QUESTION_CODE=?", map.getQuestionCode());
		if (answerPoCon != null) {
			TraceQuestionPO sto = TraceQuestionPO.findByCompositeKeys(dealerCode, map.getQuestionCode());
			sto.setString("QUESTION_CODE", map.getQuestionCode());
			sto.setString("QUESTION_NAME", map.getQuestionName());
			sto.setString("QUESTION_CONTENT", map.getQuestionContent());
			sto.setString("QUESTION_DESC", map.getQuestionDesc());			
			sto.setString("IS_VALID", map.getIsValid());
			sto.setString("IS_STAT_REPORT", map.getIsStatReport());
			sto.setString("IS_MUST_FILLED", map.getIsMustFilled());
			sto.saveIt();// 保存销售指导价
		}
	}

	@Override
	public void addquestionsInfo(TraceQuestionDTO queDto) throws ServiceBizException {
		TraceQuestionPO tracePo = new TraceQuestionPO();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String name = FrameworkUtil.getLoginInfo().getUserName();
		if (!StringUtils.isNullOrEmpty(queDto.getQuestionCode())) {
			System.out.println(dealerCode + "----" + name);
			TraceQuestionPO questPOCon = TraceQuestionPO.findFirst("QUESTION_CODE=?", queDto.getQuestionCode());
			if (questPOCon != null) {
				throw new ServiceBizException("问题编号已存在!");
			}

		}
		this.setTraceQuestion(tracePo, queDto);
		tracePo.saveIt();
	}

	public void setTraceQuestion(TraceQuestionPO tracePo, TraceQuestionDTO queDto) {
		tracePo.setString("QUESTION_CODE", queDto.getQuestionCode());
		tracePo.setInteger("QUESTION_TYPE", queDto.getQuestionType());
		tracePo.setString("QUESTION_NAME", queDto.getQuestionName());
		tracePo.setString("QUESTION_CONTENT", queDto.getQuestionContent());
		tracePo.setString("QUESTION_DESC", queDto.getQuestionDesc());
		tracePo.setString("IS_VALID", queDto.getIsValid());
		tracePo.setString("IS_STAT_REPORT", queDto.getIsStatReport());
		tracePo.setInteger("DOWN_TAG", 12781002);
		tracePo.setInteger("IS_MUST_FILLED", queDto.getIsMustFilled());
	}

	@Override
	public void addanswerInfo(AnswerGroupDTO queDto) throws ServiceBizException {
		AnswerGroupPO tracePo = new AnswerGroupPO();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String name = FrameworkUtil.getLoginInfo().getUserName();
		if (!StringUtils.isNullOrEmpty(queDto.getAnswerGroupNo())) {
			System.out.println(dealerCode + "----" + name);
			AnswerGroupPO anPOCon = AnswerGroupPO.findFirst("ANSWER_GROUP_NO=?", queDto.getAnswerGroupNo());
			if (anPOCon != null) {
				throw new ServiceBizException("问题组编号已存在!");
			}

		}
		this.setAnswers(tracePo, queDto);
		tracePo.saveIt();

	}

	public void setAnswers(AnswerGroupPO tracePo, AnswerGroupDTO queDto) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		tracePo.setString("DEALER_CODE", dealerCode);
		tracePo.setString("ANSWER_GROUP_NO", queDto.getAnswerGroupNo());
		tracePo.setString("ANSWER_GROUP_NAME", queDto.getAnswerGroupName());
		tracePo.setString("ANSWER_GROUP_DESC", queDto.getAnswerGroupDesc());
		if ((!StringUtils.isNullOrEmpty(queDto.getIsValid())) && (queDto.getIsValid()).equals(10571001)) {
			tracePo.setInteger("IS_VALID", 12781001);
		} else {
			tracePo.setInteger("IS_VALID", 12781002);
		}
		tracePo.setInteger("DOWN_TAG", 12781002);
	}

	@Override
	public void addanswerAInfo(AnswerDTO queDto) throws ServiceBizException {
		AnswerPO ansPO = new AnswerPO();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String name = FrameworkUtil.getLoginInfo().getUserName();
		if (!StringUtils.isNullOrEmpty(queDto.getAnswerNo())) {
			System.out.println(dealerCode + "----" + name);
			AnswerPO anPOCon = AnswerPO.findFirst("ANSWER_NO=?", queDto.getAnswerNo());
			if (anPOCon != null) {
				throw new ServiceBizException("答案编号已存在!");
			}

		}
		this.setAnswerAs(ansPO, queDto);
		ansPO.saveIt();

	}

	public void setAnswerAs(AnswerPO tracePo, AnswerDTO queDto) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		tracePo.setString("DEALER_CODE", dealerCode);
		tracePo.setString("ANSWER_GROUP_NO", queDto.getAnswerGroupNo());
		tracePo.setString("ANSWER", queDto.getAnswer());
		tracePo.setString("ANSWER_DESC", queDto.getAnswerDesc());
		tracePo.setString("ANSWER_NO", queDto.getAnswerNo());
		if ((!StringUtils.isNullOrEmpty(queDto.getIsValid())) && (queDto.getIsValid()).equals(10571001)) {
			tracePo.setInteger("IS_VALID", 12781001);
		} else {
			tracePo.setInteger("IS_VALID", 12781002);
		}
		tracePo.setInteger("DOWN_TAG", 12781002);
	}

	@Override
	public void editAnswers(Map<String, String> map) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> findByCode(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT  C.DEALER_CODE, C.QUESTION_CODE,  C.ANSWER_GROUP_NO, C.QUESTION_NAME, C.QUESTION_CONTENT, C.QUESTION_DESC, C.QUESTION_TYPE,1 as VER, ");
		sb.append(" C.IS_MUST_FILLED,C.IS_STAT_REPORT, ");
		sb.append(" C.DOWN_TAG,C.IS_VALID");
		sb.append(
				"  FROM ( " + CommonConstants.VT_TRACE_QUESTION + " ) C WHERE 1=1 and C.QUESTION_CODE = '" + id + "' ");
		List<Object> params = new ArrayList<Object>();
		return DAOUtil.findFirst(sb.toString(), params);
	}

	@Override
	public List<Map> queryAnswersItem() throws ServiceBizException {
		StringBuilder sql = new StringBuilder("SELECT AA.ANSWER_GROUP_NO,AA.ANSWER_GROUP_NAME,AA.DEALER_CODE FROM  ( "
				+ CommonConstants.VT_ANSWER_GROUP + " ) AA");
		List<Object> params = new ArrayList<Object>();
		return DAOUtil.findAll(sql.toString(), params);
	}

	@Override
	public Map<String, Object> findByNo(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"  SELECT C.DEALER_CODE, C.ANSWER_GROUP_NO,  C.ANSWER_GROUP_NAME, C.ANSWER_GROUP_DESC, C.DOWN_TAG,C.IS_VALID");
		sb.append("  FROM ( " + CommonConstants.VT_ANSWER_GROUP + " ) C " + "WHERE 1=1 and C.ANSWER_GROUP_NO = '" + id
				+ "'");
		List<Object> params = new ArrayList<Object>();
		System.err.println(sb.toString());
		return DAOUtil.findFirst(sb.toString(), params);
	}

	@Override
	public void editAnswers(TraceQuestionDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String answerGroupNo = map.getAnswerGroupNo();
		AnswerGroupPO po = AnswerGroupPO.findByCompositeKeys(dealerCode, answerGroupNo);
		po.setString("IS_VALID", map.getIsValid());
		po.saveIt();
	}

	@Override
	public Map<String, Object> findByAnswerNo(String id1, String id2) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT C.DEALER_CODE,  C.ANSWER_NO,C.ANSWER_GROUP_NO,  D.ANSWER_GROUP_NAME, C.ANSWER,C.ANSWER_DESC, C.DOWN_TAG, ");
		sb.append("  C.IS_VALID  FROM ( " + CommonConstants.VT_ANSWER + " ) C, ( " + CommonConstants.VT_ANSWER_GROUP
				+ " ) D  WHERE  C.ANSWER_GROUP_NO = D.ANSWER_GROUP_NO");
		sb.append(" and C.ANSWER_GROUP_NO = '" + id1 + "' AND C.ANSWER_NO = '" + id2 + "'");
		List<Object> params = new ArrayList<Object>();
		System.err.println(sb.toString());
		return DAOUtil.findFirst(sb.toString(), params);
	}

	@Override
	public void editAnswer(TraceQuestionDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String answerNo = map.getAnswerNo();
		AnswerPO po = AnswerPO.findByCompositeKeys(dealerCode, answerNo);
		po.setString("IS_VALID", map.getIsValid());
		po.saveIt();
	}

	@Override
	public PageInfoDto searchAnswerDetail(Map<String, String> queryParam, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT C.DEALER_CODE,  C.ANSWER_NO,C.ANSWER_GROUP_NO,  D.ANSWER_GROUP_NAME, C.ANSWER,C.ANSWER_DESC, C.DOWN_TAG, ");
		sb.append("  C.IS_VALID FROM ( " + CommonConstants.VT_ANSWER + " ) C, ( " + CommonConstants.VT_ANSWER_GROUP
				+ " ) D  WHERE  C.ANSWER_GROUP_NO = D.ANSWER_GROUP_NO");
		sb.append(" and C.ANSWER_GROUP_NO = ?");
		List<Object> queryParams = new ArrayList<Object>();
		queryParams.add(id);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryParams);
		return result;
	}

	@Override
	public Map<String, Object> searchAnswerGroup(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT  C.DEALER_CODE, C.QUESTION_CODE,  C.ANSWER_GROUP_NO, C.QUESTION_NAME, C.QUESTION_CONTENT, C.QUESTION_DESC, C.QUESTION_TYPE,1 as VER, ");
		sb.append(" C.IS_MUST_FILLED,C.IS_STAT_REPORT, ");
		sb.append("	 C.DOWN_TAG,C.IS_VALID");
		sb.append("  FROM ( " + CommonConstants.VT_TRACE_QUESTION + " ) C " + "WHERE 1=1 AND C.QUESTION_CODE = '" + id
				+ "' ");		
		List<Object> params = new ArrayList<Object>();
		System.err.println(sb.toString());
		return DAOUtil.findFirst(sb.toString(), params);
	}
	
	@Override
	public void updateAnswerGroup(TraceQuestionDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String questionCode = map.getQuestionCode();
		String answerGroupNo = map.getAnswerGroupNo();
		
		if(dealerCode==null || questionCode ==null){
			throw new ServiceBizException("丢失主键值!");
		}
		TraceQuestionPO po = TraceQuestionPO.findByCompositeKeys(dealerCode,questionCode);
		po.setString("ANSWER_GROUP_NO", answerGroupNo);
		po.saveIt();
	}
	
	@Override
	public PageInfoDto searchQuestionnaires(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,  A.SERIES_CODE, C.SERIES_NAME, A.QUESTIONNAIRE_CODE,  A.QUESTIONNAIRE_NAME,  A.QUESTIONNAIRE_TYPE,  A.ACTIVITY_CODE, ");
		sb.append(" A.IS_VALID, A.DOWN_TAG, A.DOWN_TIMESTAMP,A.IS_SERVICE_QUESTIONNAIRE, B.ACTIVITY_NAME, A.IS_LOSS_VEHICLE_QUESTIONNAIRE ");
		sb.append(" FROM  ( " + CommonConstants.VT_TRACE_QUESTIONNAIRE + " ) A LEFT JOIN TT_ACTIVITY B ON A.ACTIVITY_CODE = B.ACTIVITY_CODE ");
		sb.append(" LEFT JOIN TM_SERIES C ON A.SERIES_CODE = C.SERIES_CODE AND A.DEALER_CODE = C.DEALER_CODE AND C.IS_VALID = 12781001");
		sb.append(" WHERE 1=1 AND A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
		List<Object> queryList = new ArrayList<Object>();
		System.out.println(sb.toString());
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}
	
	@Override
	public void saveQuestionnaire(QuestionnaireDTO quesDto) throws ServiceBizException {
		TraceQuestionnairePO po = new TraceQuestionnairePO();
		String questionnaireCode = quesDto.getQuestionnaireCode();
		String questionnaireName = quesDto.getQuestionnaireName();
		String questionnaireType = quesDto.getQuestionnairetype();
		String activityCode = quesDto.getActivityCode();
		String isServiceQuestionnaire = quesDto.getIsServiceQuestionnaire();
		String isLossVehicleQuestionnaire = quesDto.getIsLossVehicleQuestionnaire();
		String seriesCode = quesDto.getSeriesCode();
		String isValid = quesDto.getIsValid();
		
		po.setString("QUESTIONNAIRE_CODE", questionnaireCode);
		po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		po.setString("QUESTIONNAIRE_NAME", questionnaireName);
		po.setString("QUESTIONNAIRE_TYPE", questionnaireType);
		po.setString("ACTIVITY_CODE", activityCode);
		po.setString("IS_SERVICE_QUESTIONNAIRE", isServiceQuestionnaire);
		po.setString("IS_LOSS_VEHICLE_QUESTIONNAIRE", isLossVehicleQuestionnaire);
		po.setString("SERIES_CODE", seriesCode);
		po.setString("IS_VALID", isValid);
		po.saveIt();
	}
	
	@Override
	public Map<String, Object> seachQuestionnaire(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT QUESTIONNAIRE_NAME,QUESTIONNAIRE_CODE,DEALER_CODE,IS_VALID FROM TT_TRACE_QUESTIONNAIRE ");
		sb.append(" WHERE QUESTIONNAIRE_CODE = '"+id+"'");
		List<Object> params = new ArrayList<Object>();
		System.err.println(sb.toString());
		return DAOUtil.findFirst(sb.toString(), params);
	}
	
	@Override
	public void updateQuestionnaire(QuestionnaireDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String questionnaireCode = map.getQuestionnaireCode();
		TraceQuestionnairePO po = TraceQuestionnairePO.findByCompositeKeys(dealerCode,questionnaireCode);
		po.setString("QUESTIONNAIRE_NAME", map.getQuestionnaireName());
		po.setString("IS_VALID", map.getIsValid());
		po.saveIt();
		
	}
	
	@Override
	public PageInfoDto seachQuestions(Map<String, String> queryParam, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.QUESTION_CODE,A.QUESTION_TYPE,A.QUESTION_NAME,A.QUESTION_CONTENT,A.QUESTION_DESC,"
				+ "B.SORT_NUM,B.DOWN_TAG,B.VER,B.QUESTIONNAIRE_CODE,A.DEALER_CODE FROM ( " + CommonConstants.VT_TRACE_QUESTION + " ) A LEFT JOIN "
				+ " TT_QUESTION_RELATION B ON A.DEALER_CODE = B.DEALER_CODE AND A.QUESTION_CODE = B.QUESTION_CODE "
				+ "AND B.QUESTIONNAIRE_CODE =  '"+id+"' WHERE a.QUESTION_CODE IN (SELECT QUESTION_CODE FROM Tt_Question_Relation c WHERE c.Questionnaire_Code = '"+id+"')");
		List<Object> queryParams = new ArrayList<Object>();
		
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryParams);
		return result;
	}
	
	@Override
	public PageInfoDto editQuestions(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.QUESTION_CODE,A.QUESTION_TYPE,A.QUESTION_NAME,A.QUESTION_CONTENT,A.QUESTION_DESC,"
				+ "B.SORT_NUM,B.DOWN_TAG,B.VER,B.QUESTIONNAIRE_CODE,A.DEALER_CODE FROM ( " + CommonConstants.VT_TRACE_QUESTION + " ) A LEFT JOIN "
				+ " TT_QUESTION_RELATION B ON A.DEALER_CODE = B.DEALER_CODE AND A.QUESTION_CODE = B.QUESTION_CODE ");
		List<Object> queryParams = new ArrayList<Object>();
		
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryParams);
		return result;
	}
	
	@Override
	public void initTraceQuestion(QuestionnaireDTO map) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String questionnaireCode = map.getQuestionnaireCode();
		String questionCode = map.getQuestionCode();
		String[] questionCodes = questionCode.split(";");		
		List<Object> relatList = new ArrayList<Object>();
        relatList.add(questionnaireCode); 
		List<QuestionRelationPO> list = QuestionRelationPO.findBySQL("SELECT * FROM TT_QUESTION_RELATION WHERE QUESTIONNAIRE_CODE = ?", relatList.toArray());
		Integer sortNums = list.size();
		for(int i = 0;i<questionCodes.length;i++ ){
			QuestionRelationPO po1 = QuestionRelationPO.findByCompositeKeys(dealerCode,questionnaireCode,questionCodes[i]);
			if(!StringUtils.isNullOrEmpty(po1)){
				throw new ServiceBizException("已关联相同问题,请重新选择!");
			}
			QuestionRelationPO po = new QuestionRelationPO();
			if(!StringUtils.isNullOrEmpty(questionCodes[i])){
				po.setString("DEALER_CODE", dealerCode);
				po.setString("QUESTIONNAIRE_CODE", questionnaireCode);
				po.setString("QUESTION_CODE", questionCodes[i]);
				po.setString("SORT_NUM", sortNums+i+1);
				po.saveIt();
			}
		}
		
	}
}
