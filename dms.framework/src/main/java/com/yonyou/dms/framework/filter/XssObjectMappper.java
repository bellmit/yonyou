
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.framework
 *
 * @File name : DmsObjectMappper.java
 *
 * @Author : zhangxc
 *
 * @Date : 2016年9月6日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月6日    zhangxc    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.framework.filter;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * 定义DMS 的对象转换逻辑
 * 
 * @author zhangxc
 * @date 2016年9月6日
 */

@SuppressWarnings("serial")
public class XssObjectMappper extends ObjectMapper {

    // 定义日志接口
//    private static final Logger logger = LoggerFactory.getLogger(XSSFilter.class);

    public XssObjectMappper(){
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.setSerializationInclusion(Include.NON_NULL);
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new JsonHtmlXssSerializer());
        this.registerModule(module);
    }

    static class JsonHtmlXssSerializer extends JsonSerializer<String> {

        public void serialize(String value, JsonGenerator jsonGenerator,
                              SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
            if (value != null) {
            	jsonGenerator.writeString(value.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;"));
//                jsonGenerator.writeString(StringUtils.escapeHtml(value));
            } else {
                jsonGenerator.writeString("");
            }
        }
    }
}
