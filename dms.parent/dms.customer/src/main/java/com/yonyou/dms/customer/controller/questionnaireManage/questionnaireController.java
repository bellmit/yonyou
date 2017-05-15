package com.yonyou.dms.customer.controller.questionnaireManage;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.customer.AnswerDTO;
import com.yonyou.dms.common.domains.DTO.customer.AnswerGroupDTO;
import com.yonyou.dms.common.domains.DTO.customer.QuestionnaireDTO;
import com.yonyou.dms.common.domains.DTO.customer.TraceQuestionDTO;
import com.yonyou.dms.customer.service.questionnaireManage.QuestionnaireService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 问卷制作
 * 
 * @author zhl
 * @date 2017年3月14日
 */
@Controller
@TxnConn
@RequestMapping("/questionnaire")
public class questionnaireController extends BaseController {
	@Autowired
	private QuestionnaireService questionnaireService;

	/**
	 * 问卷制作查询
	 * 
	 * @author Zhl
	 * @date 2017年3月06日
	 */
	@RequestMapping(value = "/searchQuestions", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryQuestions(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = questionnaireService.Queryquestions(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/addQuestions", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TraceQuestionDTO> addcarownerInfo(@RequestBody TraceQuestionDTO queDto,
			UriComponentsBuilder uriCB) {
		questionnaireService.addquestionsInfo(queDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<TraceQuestionDTO>(queDto, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id}/updateQuestion", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findById(@PathVariable String id) {
		return questionnaireService.findByCode(id);
	}

	/**
	 * 答案组
	 * 
	 * @author Zhl
	 * @date 2017年3月06日
	 */
	@RequestMapping(value = "/searchAnswers", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAnswers(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = questionnaireService.Queryanswers(queryParam);
		return pageInfoDto;
	}

	/**
	 * 获取答案组的下拉框
	 * 
	 * @author zhl
	 * @date 2017年4月2日
	 * @return
	 */

	@RequestMapping(value = "/searchAnswers/item", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryLabourPrice() {
		return questionnaireService.queryAnswersItem();
	}

	@RequestMapping(value = "/saveAnswers", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<AnswerGroupDTO> addanswerInfo(@RequestBody AnswerGroupDTO queDto,
			UriComponentsBuilder uriCB) {
		questionnaireService.addanswerInfo(queDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchAnswers").buildAndExpand().toUriString());
		return new ResponseEntity<AnswerGroupDTO>(queDto, headers, HttpStatus.CREATED);
	}

	/**
	 * 根据ANSWER_GROUP_NO查询问题答案
	 * 
	 * @author zhl
	 * @date 2017年3月17日
	 * @param ANSWER_GROUP_NO
	 * @return
	 */
	@RequestMapping(value = "/{id}/seachAnswer", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchAnswerByGroupNo(@RequestParam Map<String, String> queryParam,
			@PathVariable("id") String id) {
		PageInfoDto pageInfoDto = questionnaireService.searchAnswerByGroupNo(id);
		return pageInfoDto;
	}

	@RequestMapping(value = "/saveAnswersA", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<AnswerDTO> addAnswer(@RequestBody AnswerDTO queDto, UriComponentsBuilder uriCB) {
		questionnaireService.addanswerAInfo(queDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		// headers.set("Location",
		// uriCB.path("/questionnaire/{id}/seachAnswer").buildAndExpand().toUriString());
		return new ResponseEntity<AnswerDTO>(queDto, headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/updateQuestion", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TraceQuestionDTO> saveQuestions(@RequestBody TraceQuestionDTO map,
			UriComponentsBuilder uriCB) {
		questionnaireService.editQues(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<TraceQuestionDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 答案组修改查询
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/{id}/seachAnswers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByNo(@PathVariable String id) {
		return questionnaireService.findByNo(id);
	}

	/**
	 * 答案组修改保存
	 * 
	 * @param map
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateAnswers", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TraceQuestionDTO> saveAnswers(@RequestBody TraceQuestionDTO map, UriComponentsBuilder uriCB) {
		questionnaireService.editAnswers(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<TraceQuestionDTO>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{id1}/{id2}/findByAnswerNo", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findByAnswerNo(@PathVariable String id1, @PathVariable String id2) {
		return questionnaireService.findByAnswerNo(id1, id2);
	}

	@RequestMapping(value = "/updateAnswer", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TraceQuestionDTO> saveAnswer(@RequestBody TraceQuestionDTO map, UriComponentsBuilder uriCB) {
		questionnaireService.editAnswer(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<TraceQuestionDTO>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/searchAnswerDetail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchAnswerDetail(@RequestParam Map<String, String> queryParam, @PathVariable String id) {
		PageInfoDto pageInfoDto = questionnaireService.searchAnswerDetail(queryParam, id);
		return pageInfoDto;
	}

	@RequestMapping(value = "/{id}/searchAnswerGroup", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> searchAnswerGroup(@PathVariable String id) {
		return questionnaireService.searchAnswerGroup(id);
	}

	@RequestMapping(value = "/updateAnswerGroup", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TraceQuestionDTO> updateAnswerGroup(@RequestBody TraceQuestionDTO map,
			UriComponentsBuilder uriCB) {
		questionnaireService.updateAnswerGroup(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<TraceQuestionDTO>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/searchQuestionnaires", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchQuestionnaires(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = questionnaireService.searchQuestionnaires(queryParam);
		return pageInfoDto;
	}

	@RequestMapping(value = "/saveQuestionnaire", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<QuestionnaireDTO> saveQuestionnaire(@RequestBody QuestionnaireDTO quesDto,
			UriComponentsBuilder uriCB) {
		questionnaireService.saveQuestionnaire(quesDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		return new ResponseEntity<QuestionnaireDTO>(quesDto, headers, HttpStatus.CREATED);
	}
	
    @RequestMapping(value="/{id}/seachQuestionnaire",method=RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> seachQuestionnaire(@PathVariable String id){
        return questionnaireService.seachQuestionnaire(id);
    }
    
	@RequestMapping(value = "/updateQuestionnaire", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<QuestionnaireDTO> updateQuestionnaire(@RequestBody QuestionnaireDTO map, UriComponentsBuilder uriCB) {
		questionnaireService.updateQuestionnaire(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<QuestionnaireDTO>(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/seachQuestions/{id}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto seachQuestions(@RequestParam Map<String, String> queryParam, @PathVariable String id) {
		PageInfoDto pageInfoDto = questionnaireService.seachQuestions(queryParam, id);
		return pageInfoDto;
	}
	
	@RequestMapping(value = "/editQuestions", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto editQuestions(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = questionnaireService.editQuestions(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value = "/initTraceQuestion", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<QuestionnaireDTO> initTraceQuestion(@RequestBody QuestionnaireDTO map, UriComponentsBuilder uriCB) {
		questionnaireService.initTraceQuestion(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/questionnaire/searchQuestions").buildAndExpand().toUriString());
		return new ResponseEntity<QuestionnaireDTO>(headers, HttpStatus.CREATED);
	}

}
