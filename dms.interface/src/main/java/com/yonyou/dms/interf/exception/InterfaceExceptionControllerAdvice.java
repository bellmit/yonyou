/**
 * 
 */
package com.yonyou.dms.interf.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author zhangxc
 *
 */
@ControllerAdvice
public class InterfaceExceptionControllerAdvice {

    @Autowired
    ResourceBundleMessageSource messageSource;
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(InterfaceExceptionControllerAdvice.class);

	/**
	 * 
	* 处理InterfaceException
	* @author zhangxc
	* @date 2016年10月25日
	* @param error
	* @param request
	* @param response
	* @return
	 */
	@ExceptionHandler(InterfaceException.class)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> bizExceptionAdvice(InterfaceException error) {
	    if(StringUtils.isNullOrEmpty(error.getCause())){
	        logger.error("业务错误："+error.getMessage());
	    }else{
	        logger.error("业务错误："+error.getMessage(),error);
	    }
		Map<String, Object> map = new HashMap<>();
		map.put("errorMsg", "错误原因:" + error.getMessage());
		return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
