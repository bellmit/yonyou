
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartParaServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtAdPartParaDTO;
import com.yonyou.dms.part.domains.PO.basedata.TtAdPartParaPO;

/**
 * 配件订货参数
 * 
 * @author zhanshiwei
 * @date 2017年4月13日
 */
@Service
public class PartParaServiceImpl implements PartParaService {

    @Override
    public PageInfoDto queryParPara(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,PARA_CODE,PARA_NAME,PARA_VALUE,REMARK,RECORD_VERSION  from TT_AD_PART_PARA");
        List<Object> queryList = new ArrayList<Object>();
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    @Override
    public void addTtAdPartPara(ListTtAdPartParaDTO partPdto) throws ServiceBizException {
       for(int i=0;i<partPdto.getPartList().size();i++){
           String paraValue=partPdto.getPartList().get(i).getParaValue().toString();
           String remark=partPdto.getPartList().get(i).getRemark()==null?"":partPdto.getPartList().get(i).getRemark().toString();
           String paraCode=partPdto.getPartList().get(i).getParaCode();
           String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
           TtAdPartParaPO.update("PARA_VALUE=?,REMARK=?", "DEALER_CODE=? and PARA_CODE=?",paraValue,remark,dealerCode,paraCode);
       }
        
    }

}
