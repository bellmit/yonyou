package com.yonyou.dms.web.controller.inter;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.yonyou.dms.web.service.inter.InterfaceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/inter")
public class InterfaceController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	InterfaceService iservice;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
		logger.info("查询接口收发信息");
		PageInfoDto pageInfoDto = iservice.getQuerySql(queryParam);
		return pageInfoDto;
	}
    /**
     * 根据ID 删除
     * 
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
        iservice.deleteUserById(id);
    }

}
