package com.yonyou.dms.manage.service.basedata.questionnaire;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class QuestionnaireNameServiceImpl implements QuestionnaireNameService{
	
	/**
	 * 问卷名称查询
	 * @param queryParams
	 * @return
	 * @throws ServiceBizException
	 */
    @Override
    public List<Map> queryQuestionnaireName(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DISTINCT(A.QUESTIONNAIRE_CODE),QUESTIONNAIRE_NAME,A.DEALER_CODE FROM "
        		+ " ( select  *  from    ("+CommonConstants.VT_TRACE_QUESTIONNAIRE+") QQ  where  QQ.IS_SERVICE_QUESTIONNAIRE = 12781002  and QQ.IS_VALID=12781001    )  A, "
        		+ "  ("+CommonConstants.VT_QUESTION_RELATION+") B, ( select  *  from  ("+CommonConstants.VT_TRACE_QUESTION+") WW   where WW.IS_VALID=12781001   )  C  "
        		+ " WHERE A.DEALER_CODE = B.DEALER_CODE AND A.QUESTIONNAIRE_CODE = B.QUESTIONNAIRE_CODE AND "
        		+ " A.DEALER_CODE = C.DEALER_CODE AND C.QUESTION_CODE=B.QUESTION_CODE GROUP BY "
        		+ " QUESTIONNAIRE_NAME,A.QUESTIONNAIRE_CODE");      
		List<Object> params = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
    }
}
