/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.framework
*
* @File name : BeanMapper.java
*
* @Author : LiaoYuzhi
*
* @Date : 2016年3月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年3月3日    LiaoYuzhi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
package com.yonyou.dms.framework.util.bean;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.yonyou.dms.function.exception.UtilException;

/*
*
* @author LiaoYuzhi
* 简单封装BeanUtils, 实现深度转换Bean<->Bean的Mapper映射
* @date 2016年3月3日
*/

public class BeanMapperUtil {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(BeanMapperUtil.class);
    /*
     * @author LiaoYuzhi 将对象dest的属性值复制到对象orig中
     * @date 2016年3月7日
     * @param dest
     * @param orig
     * @return
     */

    public static <T> T copyProperties(Object orig, Class<T> clazz) throws UtilException {
        if (orig == null) {
            throw new UtilException("input cannot be none");
        }
        T t;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            throw new UtilException(e);
        }
        BeanUtils.copyProperties(orig, t);
        return t;
    }

    /*
     * @author LiaoYuzhi 把origList 的属性值复制到 destList中
     * @date 2016年3月7日
     * @param origList
     * @param destClass
     * @return
     */

    public static <T> List<T> copyList(List<?> origList, Class<T> destClass) throws UtilException {
        List<T> destList = new ArrayList<>();
        for (Object orig : origList) {
            destList.add(copyProperties(orig, destClass));
        }
        return destList;
    }

}
