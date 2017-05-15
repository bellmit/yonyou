/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : JsonDateSerializer.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月24日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.utils.jsonSerializer.date;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yonyou.dms.function.common.CommonConstants;

/*
*
* @author zhangxianchao
* JsonDateSerializer
* @date 2016年2月24日
*/

public class JsonSimpleDateTimeSerializer extends JsonSerializer<Date> {

    /*
     * @author zhangxianchao
     * @date 2016年2月24日
     * @param value
     * @param gen
     * @param serializers
     * @throws IOException
     * @throws JsonProcessingException (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonSerializer#serialize(java.lang.Object,
     * com.fasterxml.jackson.core.JsonGenerator, com.fasterxml.jackson.databind.SerializerProvider)
     */

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException,
                                                                                         JsonProcessingException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.SIMPLE_DATE_TIME_FORMAT);
        String formattedDate = dateFormat.format(value);
        gen.writeString(formattedDate);
    }

}
