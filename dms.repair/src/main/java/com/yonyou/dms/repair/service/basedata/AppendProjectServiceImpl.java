
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AppendProjectServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.AppendProjectDTO;
import com.yonyou.dms.repair.domains.PO.basedata.AppendProjectPO;

/**
* 附加项目定义实现类
* @author zhongsw
* @date 2016年8月19日
*/
@Service
@SuppressWarnings("rawtypes")
public class AppendProjectServiceImpl implements AppendProjectService {
    
    /**
     * 查询条件
    * @author zhongsw
    * @date 2016年8月29日
    * @param queryParam
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.AppendProjectService#searchAppendProject(java.util.Map)
    */
    @Override
    public PageInfoDto searchAppendProject(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,ADD_ITEM_CODE,ADD_ITEM_NAME,ADD_ITEM_AMOUNT,OEM_TAG,IS_VALID from  tm_add_item where 1=1 ");
        List<Object> params = new ArrayList<Object>();
    if(!StringUtils.isNullOrEmpty(queryParam.get("add_item_code"))){
        sqlSb.append(" and ADD_ITEM_CODE like ?");
        params.add("%"+queryParam.get("add_item_code")+"%");
    }
    if(!StringUtils.isNullOrEmpty(queryParam.get("add_item_name"))){
        sqlSb.append(" AND ADD_ITEM_NAME like ?");
        params.add("%"+queryParam.get("add_item_name")+"%");
    }
    if(!StringUtils.isNullOrEmpty(queryParam.get("is_valid"))){
        sqlSb.append(" AND IS_VALID = ?");
        params.add(Integer.parseInt(queryParam.get("is_valid")));
    }
    PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
    return pageInfoDto;
    }

    /**
     * 新增
    * @author zhongsw
    * @date 2016年8月29日
    * @param modeldto
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.AppendProjectService#addModel(com.yonyou.dms.repair.domains.DTO.basedata.AppendProjectDTO)
    */
    @Override
    public String addModel(AppendProjectDTO modeldto) throws ServiceBizException {
        StringBuffer sb=new StringBuffer("select * from tm_add_item where ADD_ITEM_CODE=?");
        List<Object> list = new ArrayList<Object>();
        list.add(modeldto.getAdd_item_code());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        if(map.size()>0){
            throw new ServiceBizException("附加项目代码不能重复！"); 
        }else{
            AppendProjectPO model = new AppendProjectPO();
            model.setString("ADD_ITEM_CODE", modeldto.getAdd_item_code());
            model.setString("ADD_ITEM_NAME", modeldto.getAdd_item_name());
            model.setDouble("ADD_ITEM_AMOUNT", modeldto.getAdd_item_amount());
            model.setInteger("OEM_TAG", modeldto.getOem_tag());
            model.setInteger("IS_VALID", modeldto.getIs_valid());
            model.saveIt();
            return model.getString("ADD_ITEM_CODE");
            }
        }
    
    /**
     * 更新
    * @author zhongsw
    * @date 2016年8月29日
    * @param id
    * @param modeldto
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.AppendProjectService#updateModel(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.AppendProjectDTO)
    */
    @Override
    public void updateModel(String id, AppendProjectDTO modeldto) throws ServiceBizException {
        AppendProjectPO model = AppendProjectPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        model.setString("ADD_ITEM_CODE", modeldto.getAdd_item_code());
        model.setString("ADD_ITEM_NAME", modeldto.getAdd_item_name());
        model.setDouble("ADD_ITEM_AMOUNT", modeldto.getAdd_item_amount());
        model.setInteger("OEM_TAG", modeldto.getOem_tag());
        model.setInteger("IS_VALID", modeldto.getIs_valid());
        model.saveIt();
    }
    /**
     * 更新附加项目定义字段
    * @author zhongsw
    * @date 2016年8月29日
    * @param id
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.AppendProjectService#findById(java.lang.Long)
    */
    @Override
    public AppendProjectPO findById(String id) throws ServiceBizException {
        AppendProjectPO wtpo= AppendProjectPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        return wtpo;
    }

    /**
    * 附加项目下拉框 
    * @author ZhengHe
    * @date 2016年8月19日
    * @return
    * @throws ServiceBizException(non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.AppendProjectService#selectAppendProject()
     */
	@Override
    public List<Map> selectAppendProject() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,ADD_ITEM_CODE,ADD_ITEM_NAME,ADD_ITEM_AMOUNT,OEM_TAG,IS_VALID from  tm_add_item where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), params);
        return result;
    }

    @Override
    public Map selectAppendProjectByCode(String addItemCode) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,ADD_ITEM_CODE,ADD_ITEM_NAME,ADD_ITEM_AMOUNT,OEM_TAG,IS_VALID from  tm_add_item where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and ADD_ITEM_CODE=?");
        params.add(addItemCode);
        Map appendProjectMap=DAOUtil.findFirst(sqlSb.toString(), params);
        return appendProjectMap;
    }

}
