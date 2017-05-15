
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : CacheService.java
*
* @Author : zhangxc
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.service.cache;

import java.util.Map;

/**
* 定义缓存的实现接口
* @author zhangxc
* @date 2016年8月22日
*/

public interface CacheService<T> {

    /**
     * 
    * 初始化缓存数据
    * @author zhangxc
    * @date 2016年8月22日
    * @return
     */
    void init();
    
    /**
     * 
    * 根据ID 获取对应的缓存
    * @author zhangxc
    * @date 2016年8月22日
    * @return
     */
    T getValueBykey(String key);
    
    /**
     * 
    * 当不存在时，如何加载数据
    * @author zhangxc
    * @date 2016年8月22日
     */
    void loadValueIfNotExists(String key);
    
    /**
     * 
    * 获取所有的数据
    * @author zhangxc
    * @date 2016年8月22日
    * @return
     */
    Map<? extends Number,T> getAllData();
    
    /**
     * 获取某一类型的数据
     * @author 夏威
     * @date 2017-3-20
     */
    T getCodeList(Integer type);
    
}
