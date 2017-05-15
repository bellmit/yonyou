
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : DefaultParaServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年1月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月18日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.service.basedata.parameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.common.FrameworkConstants;
import com.yonyou.dms.framework.domains.DTO.baseData.TmDefaultParaDTO;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.TmDefaultParaListDTO;
import com.yonyou.dms.manage.domains.PO.basedata.DealerBasicinfoPO;

/**
 * 基础参数设置
 * 
 * @author zhanshiwei
 * @date 2017年1月18日
 */
@Service
public class DefaultParaServiceImpl implements DefaultParaService {

    @Override
    public Map<String, Map<String, Object>> queryDefaultPara(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select DEALER_CODE,ITEM_CODE,ITEM_DESC,CASE DEFAULT_VALUE WHEN '12781001' THEN '10571001' ELSE DEFAULT_VALUE END as DEFAULT_VALUE from Tm_Default_Para where 1=1");
        List<String> params = new ArrayList<>();
        final Map<String, Map<String, Object>> basicresult = new HashMap<>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
            @Override
            protected void process(Map<String, Object> row) {
                basicresult.put(row.get("ITEM_CODE").toString(), row);
            }
        });       
        return basicresult;
    }

    /**
     * 基础参数修改
     * 
     * @author zhanshiwei
     * @date 2017年1月18日
     * @param basilistdto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.parameter.DefaultParaService#modifyBasicParametersListDTO(com.yonyou.dms.manage.domains.DTO.basedata.TmDefaultParaListDTO)
     */

    @Override
    public void modifyBasicParametersListDTO(TmDefaultParaListDTO basilistdto) throws ServiceBizException {
        List<TmDefaultParaDTO> paramList = basilistdto.getParamList();
        for (int i = 0; i < paramList.size(); i++) {
            TmDefaultParaPO basipo = new TmDefaultParaPO();
            basipo = TmDefaultParaPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),paramList.get(i).getItemCode());
            setBasicParametersPO(basipo, paramList.get(i));
            basipo.saveIt();
            this.updateBusTime(paramList.get(i));
        }
    }

    /**
     * 基础参数属性设置
     * 
     * @author zhanshiwei
     * @date 2017年1月18日
     * @param bapo
     * @param basiDto
     * @throws ServiceBizException
     */

    private void setBasicParametersPO(TmDefaultParaPO bapo, TmDefaultParaDTO basiDto) throws ServiceBizException {
        if(!StringUtils.isNullOrEmpty(basiDto.getIsCheckbox())){
            if(StringUtils.isEquals(DictCodeConstants.STATUS_IS_YES, basiDto.getIsCheckbox().toString())&&
                    StringUtils.isEquals("10571001", basiDto.getDefaultValue())){
                basiDto.setDefaultValue(DictCodeConstants.STATUS_IS_YES+"");
            }else{
                basiDto.setDefaultValue(DictCodeConstants.STATUS_IS_NOT+""); 
            }
        }
        bapo.setString("DEFAULT_VALUE", basiDto.getDefaultValue());
    }

    
    /**
    * 保存营业时间到经销商基本信息的营业时间
    * @author zhanshiwei
    * @date 2017年1月19日
    * @param basiDto
    */
    	
    public void updateBusTime(TmDefaultParaDTO basiDto){
        //检验上班时间、下班时间是否有改变，若有改变则修改经销商基本信息的营业时间字段（上班时间-下班时间）并且调用经销商基本信息上报接口
        if((!StringUtils.isNullOrEmpty(basiDto.getItemDesc()) && (StringUtils.isEquals("4001", basiDto.getItemDesc()) || StringUtils.isEquals("4002", basiDto.getItemDesc())))){
            DealerBasicinfoPO dealerBasi= DealerBasicinfoPO.findFirst("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
            if(StringUtils.isEquals("4001", basiDto.getItemDesc())){
                dealerBasi.setString("BUSINESS_HOURS", basiDto.getDefaultValue());
            }
            if(StringUtils.isEquals("4002", basiDto.getItemDesc())){
                dealerBasi.setString("BUSINESS_HOURS",dealerBasi.get("BUSINESS_HOURS")+"-"+basiDto.getDefaultValue());
            }
        }
    }
}
