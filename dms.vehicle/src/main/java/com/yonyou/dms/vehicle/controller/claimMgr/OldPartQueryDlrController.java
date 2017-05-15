package com.yonyou.dms.vehicle.controller.claimMgr;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.claimMgr.OldPartQueryDlrService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 旧件查询
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn
@RequestMapping("/OldPartQuery")
public class OldPartQueryDlrController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	OldPartQueryDlrService  oldPartQueryDlrService;
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto OldPartQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------- 旧件查询-----------------------");
    	PageInfoDto pageInfoDto = oldPartQueryDlrService.MVSFamilyMaintainQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
     * 索赔下拉框ClaimType
     */
    @RequestMapping(value="/ClaimType",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> ClaimType(@RequestParam Map<String, String> queryParams){
    	logger.info("-----------------------相关菲亚特车系-----------------------");
    	List<Map> tenantMapping = oldPartQueryDlrService.ClaimType(queryParams);
        return tenantMapping;
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void OldPartQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("----------------------- 旧件查询下载-----------------------");
    	oldPartQueryDlrService.OldPartQueryDownload(queryParam, request, response);
	}
	
}
