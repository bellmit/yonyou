package com.yonyou.dms.vehicle.controller.threePack;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.PO.threePack.TtThreepackRepairPO;
import com.yonyou.dms.vehicle.service.threePack.ThreePackRepairAuditQueryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/repair")
public class ThreePackRepairAuditQueryController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ThreePackRepairAuditQueryService tservice;
	
	  @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询维修方案信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/list/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryList(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("配件查询");
	    	PageInfoDto pageInfoDto=tservice.queryList(id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/info/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryInfo(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("维修方案审核基本信息");
	    	PageInfoDto	pageInfoDto=tservice.queryInfo(id);
	        return pageInfoDto;
	    }
	  @RequestMapping(value="/infotoo/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> query(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("客户信息");
	    	 List<Map>	pageInfoDto=tservice.query(id);
	        return pageInfoDto.get(0);
	    }
	  @RequestMapping(value="/labourList/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryLabourList(@PathVariable(value = "id") Long id) throws Exception {
	    	logger.info("三包维修方案工时查询");
	    	PageInfoDto pageInfoDto=tservice.queryLabourList(id);
	        return pageInfoDto;
	    }
	  
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> findById(@PathVariable(value = "id") Long id){
	    	logger.info("经销商查询");
	    	TtThreepackRepairPO model= TtThreepackRepairPO.findById(id);
	        return model.toMap();
	    }

}
