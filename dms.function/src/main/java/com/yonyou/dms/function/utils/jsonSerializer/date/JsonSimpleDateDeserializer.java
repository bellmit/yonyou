/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : JsonDateDeserializer.java
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.JsonSerializeException;

/*
*
* @author zhangxianchao
* JsonDateDeserializer
* @date 2016年2月24日
*/

public class JsonSimpleDateDeserializer extends JsonDeserializer<Date> {

    /*
     * @author zhangxianchao
     * @date 2016年2月24日
     * @param p
     * @param ctxt
     * @return
     * @throws IOException
     * @throws JsonProcessingException (non-Javadoc)
     * @see com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.jackson.core.JsonParser,
     * com.fasterxml.jackson.databind.DeserializationContext)
     */

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        SimpleDateFormat format = new SimpleDateFormat(CommonConstants.SIMPLE_DATE_FORMAT);
        String date = p.getText();
        try {
            return format.parse(date);
        } catch (ParseException e) {
           throw new JsonSerializeException(e);
        }
    }

}
