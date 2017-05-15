/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : PropertiesUtils.java
*
* @Author : zhangxianchao
*
* @Date : 2016年4月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年4月15日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/*
*
* @author zhangxianchao
* PropertiesUtils
* @date 2016年4月15日
*/

public class PropertiesUtils {

    /**
     * load by clazz
     */
    public static Properties loadPropertiesFromFile(Class<?> clazz, String filePath) throws IOException {
        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(filePath);
        return loadPropertiesFromInputStream(inputStream);
    }

    /**
     * load from stream
     */
    public static Properties loadPropertiesFromInputStream(InputStream inputStream) throws IOException {
        Properties pros = new Properties();
        try {
            pros.load(inputStream);
        } catch (IOException ex) {
            throw ex;
        }
        return pros;
    }

    public static Properties loadPropertiesFromSystem() {
        return System.getProperties();
    }
}
