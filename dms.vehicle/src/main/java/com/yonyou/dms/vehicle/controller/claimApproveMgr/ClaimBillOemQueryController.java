package com.yonyou.dms.vehicle.controller.claimApproveMgr;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerReportApprovalPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.claimApproveMgr.ClaimBillOemQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author ZhaoZ
 * @date 2017年4月25日
 */
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/claimBillOemQuery")
public class ClaimBillOemQueryController {

	private static final Logger logger = LoggerFactory.getLogger(ClaimBillOemQueryController.class);
	
	@Autowired
	private  ClaimBillOemQueryService claimService;
	
	
	
	/**
     * 取当前年
     * @author ZhaoZ
     * @date 2017年4月25日
     * @return
     */
   
	@RequestMapping(value="/getYear",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getYear(){
		logger.info("============ （获取年）===============");
		List<Map> yearList = new ArrayList<Map>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);	//获取当前年份
		Map<String,Object> yearMap = new HashMap<String,Object>();
		yearMap.put("FORECAST_YEAR", year);
		yearList.add(yearMap);
		Map<String,Object> yearMap2 = new HashMap<String,Object>();
		yearMap2.put("FORECAST_YEAR", year+1);
		yearList.add(yearMap2);
		return yearList;
    }
    
    /**
     * 取月份
     * @author ZhaoZ
     * @date 2017年4月25日
     * @return
     */
	@RequestMapping(value="/getMonth",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getMonth(){
		List<Map> list = new ArrayList<>();
    	Date dd = new Date();
 	    for(int i=1;i<13;i++){
 	    	Map map = new HashMap<>();
 	    	if(i<10){
 	    		map.put("month","0"+i);
 	    	}else{
 	    		map.put("month",i);
 	    	}
        	list.add(map);
		}
		return list;
    }
	
	/**
	 * 索赔申请单结算查询
	 * @param queryParams
	 * @return
	 */
	@RequestMapping(value = "/threePackItemQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto threePackItemQuery(@RequestParam Map<String, String> queryParams){
		logger.info("=====索赔申请单结算查询=====");
		return claimService.threePackItemList(queryParams);
		
	}
	
	/**
	 * 索赔申请单结算新增
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/{claimNos}/claimBillOemAdd", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto claimBillOemAdd(@PathVariable(value = "claimNos") String claimNos){
		logger.info("=====索赔申请单结算新增=====");
		return claimService.claimBillOemAddList(claimNos);
	}
	
	/**
	 * 索赔结算单查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/claimBillQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto claimBillQuery(@RequestParam Map<String, String> queryParams){
		logger.info("=====索赔结算单查询=====");
		return claimService.claimBillQueryList(queryParams);
	}
	
	/**
	 * 索赔结算单查询DLR
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/claimBillQueryDLR", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto claimBillQueryDLR(@RequestParam Map<String, String> queryParams){
		logger.info("=====索赔结算单查询=====");
		return claimService.claimBillQueryDLRList(queryParams);
	}
	
	/**
	 * 索赔结算单明细查询
	 * @param queryParams
	 * @return
	 */
	
	@RequestMapping(value = "/claimBillOemDetailQuery/{balanceId}", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto claimBillOemDetailQuery(@PathVariable(value = "balanceId") Long balanceId){
		logger.info("=====索赔结算单明细查询=====");
		return claimService.claimBillOemDetail(balanceId);
	}
}
