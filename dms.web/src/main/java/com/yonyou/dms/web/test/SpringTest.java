package com.yonyou.dms.web.test;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.lms.DCSTOLMS001Impl;



/**
 * 功能说明：使用Main方法执行Spring测试的样例
 */
public class SpringTest extends  BaseService {
	private static Logger logger = Logger.getLogger(SpringTest.class);

	public static void main(String[] args) throws Exception {
		//ContextUtil.loadConf();
		GenericXmlApplicationContext context = new GenericXmlApplicationContext();  
        context.setValidating(false);  
        context.load("classpath*:applicationContext*.xml");  
        context.refresh();  
        DCSTOLMS001Impl dcsTOLMS001Impl = (DCSTOLMS001Impl)context.getBean("DCSTOLMS001Impl");
//	      standService.paginationByCondition(new StandQC());
//	      standService.findById("1");
        dcsTOLMS001Impl.execute(new Date(), new Date());
	}
}
