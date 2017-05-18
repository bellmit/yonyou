package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.manage.service.basedata.questionnaire.QuestionnaireNameService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 问卷名称查询
* @author Administrator
* @date 2016年12月28日
*/

@Controller
@TxnConn
@RequestMapping("/questionnaire")
public class QuestionnaireNameController{
    @Autowired
    private QuestionnaireNameService qns;
    
    /**
     * 问卷名称查询
     * @param queryParams
     * @return
     */
    @RequestMapping(method=RequestMethod.GET)
    @ResponseBody
    public List<Map> getAttachUnits(@RequestParam Map<String, String> queryParams){
        return qns.queryQuestionnaireName(queryParams);
    }
}
