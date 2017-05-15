
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : DefeatReasonService.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月8日    DuPengXin    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.domains.DTO.basedata.DefeatReasonDTO;
import com.yonyou.dms.retail.domains.PO.basedata.DefeatReasonPO;

/*
 * 
 * @author DuPengXin
 * @date 2016年7月1日
 */
public interface DefeatReasonService {

    public PageInfoDto QueryDefeatReason(Map<String, String> queryParam) throws ServiceBizException;

    public Long addDefeatReason(DefeatReasonDTO drdto) throws ServiceBizException;

    public void modifyReason(Long id, DefeatReasonDTO drdto) throws ServiceBizException;

    public DefeatReasonPO findById(Long id) throws ServiceBizException;

    public List<Map> queryDefeatReasonSel(Map<String, String> queryParam) throws ServiceBizException;
}
