
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : MediaDetailController.java
*
* @Author : wangxin
*
* @Date : 2016年12月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月20日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.customer.service.basedata;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
* TODO description
* @author wangxin
* @date 2016年12月20日
*/

@Service
public class MediaDetailServiceImpl implements MediaDetailService {
	
    @Override
    public PageInfoDto findAllMediaDetail(Map<String, String> queryParam) {
        StringBuilder sb = new StringBuilder(" SELECT DEALER_CODE,MEDIA_TYPE,MEDIA_DETAIL,MEDIA_DETAIL_NAME FROM TT_MEDIA_DETAIL");
        PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), null);
        return dto;
    }

}
