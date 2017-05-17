/**
 * 
 */
package com.yonyou.dms.part.controller.basedata;

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

import com.yonyou.dms.common.domains.PO.basedata.TtPartLendPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.LendToReturnDTO;
import com.yonyou.dms.part.service.basedata.LendToReturnService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/return")
@SuppressWarnings("rawtypes")
public class LendToReturnController extends BaseController {

	@Autowired
	private LendToReturnService lendToReturnService;
	
	/**
	 * 查询所有借出单
	 * @param query
	 * @return
	 */
	@RequestMapping(value="/findAllList",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllList(@RequestParam Map<String, String> query){
		return lendToReturnService.findAllList(query);// 取消操作时解锁
	}
	
	/**
	 * 查询借出明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findAllDetails/{id}",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllDetails(@PathVariable String id,@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = lendToReturnService.findAllDetails(id,queryParam);
		if(StringUtils.isNullOrEmpty(pageInfoDto)){
			return new PageInfoDto();
		}else{
			return pageInfoDto;// 页面关闭时解锁
		}
	}
	
	/**
	 * 主页查询借出明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findAllDetails/forLocale/{id}",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllDetailsForLocale(@PathVariable String id,@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		return lendToReturnService.findAllDetailsForLocale(id,queryParam);
	}

	/**
	 * 归还入账操作
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/returnList", method = RequestMethod.POST)
	public ResponseEntity<String> btnReturn(@RequestBody LendToReturnDTO dto,UriComponentsBuilder uriCB){
		String str = lendToReturnService.operate(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/return").buildAndExpand().toUriString());
		return new ResponseEntity<String>(str,headers, HttpStatus.CREATED);
	}
	
	/**
	 * 加锁用户
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateByLocker/{lendNo}", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateLocker(@PathVariable(value="lendNo") String id){
		String[] split = id.split(",");
		int locker = 0;
		for (int i = 0; i < split.length; i++) {
			locker = Utility.updateByLocker("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", split[i], "LOCK_USER");
		}
		return locker;
	}
	
	/**
	 * 解锁用户
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateUnLocker/{lendNo}", method = RequestMethod.POST)
	@ResponseBody
	public String updateUnLocker(@PathVariable(value="lendNo") String id){
		String[] noValue = id.split(",");
		String locker = Utility.updateByUnLock("TT_PART_LEND", FrameworkUtil.getLoginInfo().getUserId().toString(), "LEND_NO", noValue , "LOCK_USER");
		return locker;
	}
}
