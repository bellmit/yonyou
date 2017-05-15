
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : AclDmsDataProvider.java
*
* @Author : zhangxc
*
* @Date : 2016年11月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年11月17日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.interceptors.acl;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.framework.common.FrameworkConstants;
import com.yonyou.dms.framework.domain.ApplicationAccessUrlDto;
import com.yonyou.dms.framework.domain.FrameworkParamBean;
import com.yonyou.dms.framework.domain.UserAccessInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.f4.common.acl.AclDataProvider;
import com.yonyou.f4.common.acl.AclResource;
import com.yonyou.f4.common.acl.AclRole;
import com.yonyou.f4.common.acl.AclUser;


/**
* 实现权限控制接口
* @author zhangxc
* @date 2016年11月17日
*/

public class AclDmsDataProvider implements AclDataProvider {

	 // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(AclDmsDataProvider.class);
    @Autowired
    FrameworkParamBean frameworkParam;
    
    private static String[] anonymResouces;
    
    static{
        anonymResouces = getAnonymResouce();
    }
    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param s
    * @param s1
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclDataProvider#getResource(java.lang.String, java.lang.String)
    */

    @Override
    public AclResource getResource(String resouceId,String tenantId) {
       String[] resrouceSplits = resouceId.split(FrameworkConstants.ACL_RESOUCCE_SPLIT);
       return new AclDmsResouce(resrouceSplits[0], resrouceSplits[1], resrouceSplits[2]);
        
    }

    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param s
    * @param s1
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclDataProvider#getResourceUIDsByRole(java.lang.String, java.lang.String)
    */

    @Override
    public String[] getResourceUIDsByRole(String roleUID,String tenantId) {
        if(roleUID==null||roleUID.equals(frameworkParam.getAclAnonymUID())){
            return this.anonymResouces;
        }else{
        	 UserAccessInfoDto   powerUrl = ApplicationContextHelper.getBeanByType(UserAccessInfoDto.class);
        	 ApplicationAccessUrlDto   applicationUrlDto = ApplicationContextHelper.getBeanByType(ApplicationAccessUrlDto.class);
            //用户资源
            if(powerUrl.getUserResouces()!=null){
                String[] resource =  ArrayUtils.addAll(powerUrl.getUserResouces(), this.anonymResouces);
                return ArrayUtils.addAll(resource, applicationUrlDto.getDafaultUrl());
            }else{
                return this.anonymResouces;
            }
        }
    }

    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param s
    * @param s1
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclDataProvider#getRole(java.lang.String, java.lang.String)
    */

    @Override
    public AclRole getRole(String roleUid,String tenantId) {
        AclRole aclRole = new AclRole();
        aclRole.setUID(roleUid);
        return aclRole;
    }

    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param s
    * @param s1
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclDataProvider#getRoleUIDsByUser(java.lang.String, java.lang.String)
    */

    @Override
    public String[] getRoleUIDsByUser(String usrUID,String tenantId) {
        return new String[]{usrUID};
    }

    /**
    * @author zhangxc
    * @date 2016年11月17日
    * @param s
    * @param s1
    * @return
    * (non-Javadoc)
    * @see com.yonyou.f4.common.acl.AclDataProvider#getUser(java.lang.String, java.lang.String)
    */

    @Override
    public AclUser getUser(String uid,String tenantId) {
       AclUser aclUser = new AclUser();
       aclUser.setUID(uid);
       return aclUser;
    }

    /**
     * 
    * 获得匿名用户可访问资源
    * @author zhangxc
    * @date 2016年11月17日
    * @return
     */
    private static String[] getAnonymResouce(){   
       return new String[]{"GET"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"web"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"/common/login","DELETE"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"web"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"/common/login","GET"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"manage"+FrameworkConstants.ACL_RESOUCCE_SPLIT+"/basedata/dealers"};
    }
}
