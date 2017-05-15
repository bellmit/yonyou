package com.yonyou.dms.vehicle.controller.oldPart;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.DealerDetailedinfoDTO;
import com.yonyou.dms.vehicle.service.oldPart.OldPartReturnManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/returnManage")
public class OldPartReturnManageController  extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	OldPartReturnManageService oservice;
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("旧件回运管理信息");
        PageInfoDto pageInfoDto=oservice.findReturnManage(queryParam);
        return pageInfoDto;
	}
	@RequestMapping(value = "/{RETURN_BILL_NO}",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> query(@PathVariable("RETURN_BILL_NO") String returnBillNo) throws Exception {
    	logger.info("旧件回运清单信息");
    	Map<String,Object> pageInfoDto=oservice.findReturn(returnBillNo);
        return pageInfoDto;
	}
	@RequestMapping(value = "/find/{RETURN_BILL_NO}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeat(@PathVariable("RETURN_BILL_NO") String returnBillNo) throws Exception {
    	logger.info("旧件回运管理信息");
        PageInfoDto pageInfoDto=oservice.findOldPartById(returnBillNo);
        return pageInfoDto;
	}
    @RequestMapping(value = "/delete/{RETURN_BILL_NO}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharge(@PathVariable("RETURN_BILL_NO") String returnBillNo,UriComponentsBuilder uriCB) throws ServiceBizException{
        oservice.deleteById(returnBillNo);
    }
    @RequestMapping(value="/queryPass",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<DealerDetailedinfoDTO> queryPass(@RequestParam Map<String, String> queryParams){
    	logger.info("=====旧件清单发运=====");
    	
    	oservice.queryDealerPass(queryParams);
    	return new ResponseEntity<>(new HttpHeaders(), HttpStatus.CREATED);
    }
}
