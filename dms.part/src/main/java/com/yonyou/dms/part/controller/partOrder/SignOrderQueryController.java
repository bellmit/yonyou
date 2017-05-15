package com.yonyou.dms.part.controller.partOrder;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.part.service.partOrder.SignOrderQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 货运签收单查询
* TODO description
* @author yujiangheng
* @date 2017年5月10日
 */
@Controller
@TxnConn
@RequestMapping("/partOrder/SignOrderQuery")
public class SignOrderQueryController {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(SignOrderQueryController.class);

    @Autowired
    private SignOrderQueryService signOrderQueryService;
    /**
     * 根据查询条件查询
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto SignOrderQuery(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = signOrderQueryService.SignOrderQuery(queryParam);
        return pageInfoDto;
    }  
    
    
    /**
     * 根据查询条件查询
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/detail",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QuerySignOrderDetail(@PathVariable String psoNo) throws ServiceBizException{
        PageInfoDto pageInfoDto = signOrderQueryService.QuerySignOrderDetail(psoNo);
        return pageInfoDto;
    }  
    
    
}
