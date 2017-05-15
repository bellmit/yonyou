package com.yonyou.dms.manage.controller.basedata;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.manage.service.basedata.date.YearMonthWeekServic;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 年，月，周联动下拉框Controller
 * @author yh135
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/date")
public class YearMonthWeekController {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialSelectController.class);
	
	@Autowired
	YearMonthWeekServic service;
	
	/**
     * 获取年
     * @author DC
     * @date 2017年3月12日
     * @return
     */
    @RequestMapping(value="/year",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getYearList(){
    	logger.info("=====年加载=====");
    	List<Map> tenantMapping = service.getYearList();
        return tenantMapping;
    }
    
    /**
     * 获取月
     * @author DC
     * @date 2017年3月12日
     * @return
     */
    @RequestMapping(value="/month",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getMonthList(){
    	logger.info("=====月加载=====");
    	List<Map> tenantMapping = service.getMonthList();
        return tenantMapping;
    }
    
    /**
     * 获取周
     * @author DC
     * @date 2017年3月12日
     * @return
     */
    @RequestMapping(value="/{orderYear}/{orderMonth}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getWeekList(@PathVariable(value = "orderYear") Integer year,@PathVariable(value = "orderMonth") int month){
    	logger.info("=====周加载=====");
    	List<Map> tenantMapping = service.getWeekList(year,month);
        return tenantMapping;
    }
    

}
