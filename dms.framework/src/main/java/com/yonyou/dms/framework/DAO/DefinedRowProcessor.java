
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.framework
*
* @File name : DefinedRowProcessor.java
*
* @Author : zhangxc
*
* @Date : 2016年7月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月7日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.framework.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.RowListenerAdapter;

/**
* TODO description
* @author zhangxc
* @date 2016年7月7日
*/

public abstract class DefinedRowProcessor extends RowListenerAdapter{
    protected List<Map> result = new ArrayList<>();
    
    public final  List<Map> getResult(){
        return result;
    }

    @Override
    public void onNext(Map<String, Object> row) {
        process(row);
        result.add(row);
    }
    
    abstract protected void process(Map<String, Object> row);
}
