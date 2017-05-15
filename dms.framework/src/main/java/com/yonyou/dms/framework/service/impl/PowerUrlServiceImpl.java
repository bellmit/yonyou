package com.yonyou.dms.framework.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.domain.ApplicationAccessUrlDto;
import com.yonyou.dms.framework.service.PowerUrlService;
import com.yonyou.dms.framework.util.acl.AccessUrlUtils;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 获取默认的URL
 * @author zhangxc
 * @date 2016年11月22日
 */
@Service
public class PowerUrlServiceImpl implements PowerUrlService{
     

	
	
	/**
	 * 获取默认Url
	 * @author zhangxc
	 * @date 2016年11月22日
	 * (non-Javadoc)
	 * @throws ServiceBizException
	 * @see com.yonyou.dms.web.service.login.PowerUrlService#dafaultUrl()
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public void dafaultUrl() throws ServiceBizException {
		ApplicationAccessUrlDto   applicationUrlDto = ApplicationContextHelper.getBeanByType(ApplicationAccessUrlDto.class);
		if(applicationUrlDto.getDafaultUrl()==null){
			synchronized(this){
				if(applicationUrlDto.getDafaultUrl()==null){
					Map map = new HashMap();
					StringBuilder actionSql = new StringBuilder("SELECT ACTION_METHOD method,MODULE model ,ACTION_CODE code FROM  tc_default_action ");
					List<Map> dafaultUrls = Base.findAll(actionSql.toString());
					applicationUrlDto.setDafaultUrl(AccessUrlUtils.iteratorToArray(dafaultUrls));
				}
			}
		}
		
	}

	

}
