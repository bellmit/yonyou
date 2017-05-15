package com.yonyou.dms.repair.controller.precontract;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.Precontract.PrecontractArrangeService;
import com.yonyou.dms.repair.service.Precontract.PrecontractSumService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


@Controller
@TxnConn
@RequestMapping("/precontractSum/queryPreSum")
public class precontractSumController extends BaseController{
    
    @Autowired
    private PrecontractSumService precontractSumService;
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStockLog(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractSumService.QueryPrecontractSum(queryParam);
        return pageInfoDto;
    }
}