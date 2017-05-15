
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StoreService.java
*
* @Author : zhongsw
*
* @Date : 2016年7月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月10日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;


/**
 * 仓库接口
 * 
 * @author zhengcong
 * @date 2017年3月21日
 */

public interface StoreService {

	Map<String, String> findByStorageCode(String STORAGE_CODE) throws ServiceBizException;//根据vin查询详细信息

    
    public PageInfoDto searchStore(Map<String, String> queryParam)throws ServiceBizException;///查询
    
    
    public void updateStore(String STORAGE_CODE,StoreDTO storeDTO)throws ServiceBizException;///修改
    

    public List<Map> queryList() throws ServiceBizException;//查询仓库下拉列
    

}
