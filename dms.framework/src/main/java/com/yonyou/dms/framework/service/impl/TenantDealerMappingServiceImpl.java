
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : TenantDealerMappingServiceImpl.java
*
* @Author : zhangxc
*
* @Date : 2016年11月4日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月4日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.common.database.DBService;
import com.yonyou.f4.common.database.JdbcTemplate;
import com.yonyou.f4.common.database.impl.DBServiceImpl;


/**
* 加载租户与经销商的对应关系
* @author zhangxc
* @date 2016年11月4日
*/
public class TenantDealerMappingServiceImpl implements TenantDealerMappingService{

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(TenantDealerMappingServiceImpl.class);
    
    private DBService dbService ;
    private Timer timer = null;

    @Value("${f4.multi.tenant}")
    public boolean isTenant = false;
    @Value("${f4.def.txn}")
    public String defTxn;
    @Value("${f4.def.ds}")
    public String defDs;
    @Value("${f4.def.schema}")
    public String defSchema;
    @Autowired
    ApplicationContext ac;
    
    //定义Map值
    private Map<String,Map<String,String>> mapingMap = new HashMap<>();
    
    /**
     * 
    * 执行加载
    * @author zhangxc
    * @date 2016年11月4日
     */
    @PostConstruct
    public void init() {
        dbService = (DBServiceImpl)ac.getBean(DBService.class);
        if(isTenant) {
            //加载对应关系
            loadTenantMapping();
            
            this.timer = new Timer("TenantDealerMappingLoader");
            this.timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    loadTenantMapping();
                }
            }, 5 * 60 * 1000l, 5 * 60 * 1000l);
            logger.info("TenantDealerMappingLoader has been scheduled to start!");
        }
    }
    
    /**
     * 
    * 销毁资源
    * @author zhangxc
    * @date 2016年11月4日
     */
    @PreDestroy
    public void destory() {
        if(isTenant) {
            //Stop the timer
            if(this.timer != null) {
                this.timer.cancel();
                this.timer = null;
                logger.info("TenantInfoLoader will be stop!");
            }
        }
    }
    /**
     * 加载所有的Mapping 关系
    * @author zhangxc
    * @date 2016年11月4日
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.TenantDealerMappingService#getAll()
     */
    @Override
    public Map<String,Map<String,String>> getAll() {
        if(isTenant==true&&CommonUtils.isNullOrEmpty(mapingMap)){
            synchronized (mapingMap) {
                loadTenantMapping();
            }
        }
        return mapingMap;
    }

    /**
     * 加载有效的经销商信息
    * @author zhangxc
    * @date 2016年11月4日
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.TenantDealerMappingService#getValid()
     */
    @Override
    public Map<String,Map<String,String>> getValid() {
        return null;
    }
    
    /**
     * 
    * 执行初始化
    * @author zhangxc
    * @date 2016年11月4日
     */
    private void loadTenantMapping() {
        try{
        	logger.debug("TenantDealerMappingServiceImpl loadTenantMapping");
            dbService.beginTxnById(defTxn, 30);
            logger.debug("TenantDealerMappingServiceImpl dbService.openConnById");
            Connection conn = dbService.openConnById(defDs);
            JdbcTemplate tpl = new JdbcTemplate(conn);
            List<Map<String,String>> ret= tpl.query("select DEALER_CODE,DEALER_SHORTNAME,TENANT_ID from f4_tenant_dealer_mapping order by DEALER_CODE", new JdbcTemplate.RowMapper<Map<String,String>>() {
                public Map<String, String> mapper(ResultSet rs) throws Exception {
                    Map<String,String> tenant = new HashMap<>();
                    tenant.put("DEALER_CODE", rs.getString("DEALER_CODE") );
                    tenant.put("DEALER_SHORTNAME", rs.getString("DEALER_SHORTNAME"));
                    tenant.put("TENANT_ID", rs.getString("TENANT_ID"));
                    return Collections.synchronizedMap(tenant);
                }
            });
            
            Map<String,Map<String,String>> mps = new HashMap<>();
            for(Map<String,String> t : ret ){
                mps.put(t.get("DEALER_CODE"), t);
            }
            logger.debug("TenantDealerMappingServiceImpl Collections.synchronizedMap");
            this.mapingMap = Collections.synchronizedMap(mps);
            logger.debug("TenantDealerMappingServiceImpl loadTenantMapping end");
        }catch(Exception e){
            logger.debug("加载租户与经销商关系失败",e);
        }finally{
            try{
                dbService.endTxn(true);
                dbService.clean();
            }catch(Exception e){
                logger.debug("清理资源失败",e);
            }
        }
    }

    /**
     * 根据tenantId 返回经销商列表
    * @author zhangxc
    * @date 2017年1月11日
    * @param tenantId
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.TenantDealerMappingService#getDealersByTenantId(java.lang.String)
     */
    @Override
    public List<String> getDealersByTenantId(String tenantId) {
        if(!CommonUtils.isNullOrEmpty(mapingMap)){
            //创建返回对象
            List<String> returnResult = new ArrayList<>();
            for(Map.Entry<String, Map<String,String>> dealerMapping:mapingMap.entrySet()){
                Map<String,String> entryValue =  dealerMapping.getValue();
                //判断租户是否一致，如果一致，则返回租户列表
                if(entryValue.get("TENANT_ID").equals(tenantId)){
                    returnResult.add(entryValue.get("DEALER_CODE"));
                }
            }
            return returnResult;
        }else{
            return null;
        }
    }

    /**
     * 根据经销商代码获取TenantId
    * @author zhangxc
    * @date 2017年1月14日
    * @param dealerCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.framework.service.TenantDealerMappingService#getTenantIdByDealerCode(java.lang.String)
     */
    @Override
    public String getTenantIdByDealerCode(String dealerCode) {
        return getAll().get(dealerCode).get("TENANT_ID");
    }
}
