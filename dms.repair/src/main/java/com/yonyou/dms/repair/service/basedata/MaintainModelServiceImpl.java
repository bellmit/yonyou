
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : MaintainModelServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月12日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月12日    DuPengXin   1.0
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.MaintainModelDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.ModelGroupItemDTO;
import com.yonyou.dms.repair.domains.PO.basedata.MaintainModelPO;
import com.yonyou.dms.repair.domains.PO.basedata.ModelGroupItemPO;

/**
 * @author DuPengXin
 * @date 2016年7月12日
 */
@Service
public class MaintainModelServiceImpl implements MaintainModelService {
    //定义日志类型
    private static final Logger logger = LoggerFactory.getLogger(MaintainModelServiceImpl.class);

    /*
     * 根据查询条件查询数据
     * @author DuPengXin
     * @date 2016年7月7日
     */

    @Override
    public PageInfoDto QueryModel(Map<String, String> queryParam) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("select MODEL_GROUP_ID,MODEL_LABOUR_CODE,MODEL_LABOUR_NAME,OEM_TAG,IS_VALID,DEALER_CODE from  tm_model_group where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if(!StringUtils.isNullOrEmpty(queryParam.get("modelLabourName"))){
            sqlSb.append(" and MODEL_LABOUR_NAME like ?");
            params.add("%"+queryParam.get("modelLabourName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isValid"))){
            sqlSb.append(" AND IS_VALID = ?");
            params.add(Integer.parseInt(queryParam.get("isValid")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("oemTag"))){
            sqlSb.append(" AND OEM_TAG = ?");
            params.add(Integer.parseInt(queryParam.get("oemTag")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }
    


    /**
     * 新增
     * @author DuPengXin
     * @date 2016年7月12日
     * @param modeldto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#addModel(com.yonyou.dms.repair.domains.DTO.basedata.MaintainModelDTO)
     */

    @Override
    public Long addModel(MaintainModelDTO modeldto) throws ServiceBizException{
        MaintainModelPO model = new MaintainModelPO();
        if(SearchMaintainModel(modeldto.getModelLabourCode(),(modeldto.getModelLabourName()))){
            setModel(model,modeldto);
            model.saveIt();
            return model.getLongId();
        }else{
            throw new ServiceBizException("该维修项目组代码或名称已存在");
        }
    }


    /**
     * 新增维修项目名称或代码时查询数据库中是否有相同的数据
     * @author DuPengXin
     * @date 2016年10月18日
     * @param modelLabourCode
     * @param modelLabourName
     * @return
     */

    private boolean SearchMaintainModel(String modelLabourCode, String modelLabourName) {
        StringBuilder sb=new StringBuilder("select tmg.MODEL_GROUP_ID,tmg.DEALER_CODE,tmg.MODEL_LABOUR_CODE,tmg.MODEL_LABOUR_NAME from tm_model_group tmg where tmg.MODEL_LABOUR_CODE=? OR tmg.MODEL_LABOUR_NAME=?");
        List<Object> param=new ArrayList<Object>();
        param.add(modelLabourCode);
        param.add(modelLabourName);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }


    /**
     * 子表新增
     * @author DuPengXin
     * @date 2016年8月3日
     * @param groupdto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#addGroup(com.yonyou.dms.repair.domains.DTO.basedata.ModelGroupItemDTO)
     */

    @Override
    public Long addGroup(ModelGroupItemDTO groupdto)throws ServiceBizException {
        ModelGroupItemPO group = new ModelGroupItemPO();
        if(SearchModelCode(groupdto.getModelCode())){
            setGroup(group, groupdto);
            group.saveIt();
            return group.getLongId();
        }else{
            throw new ServiceBizException("该车型名称已存在其他维修车型分组中");
        }
    }

    /**
     * 新增时查询数据库中是否有相同的车型名称
     * @author DuPengXin
     * @date 2016年10月18日
     * @param modelName
     * @return
     */

    private boolean SearchModelCode(String modelCode) {
        StringBuilder sb=new StringBuilder("select tmgi.ITEM_ID,tmgi.DEALER_CODE,tmgi.MODEL_GROUP_ID,tmgi.MODEL_CODE,tmgi.MODEL_NAME from tm_model_group_item tmgi where tmgi.MODEL_CODE=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(modelCode);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 修改信息
     * @author DuPengXin
     * @date 2016年7月12日
     * @param id
     * @param modeldto
     */

    public void updateModel(Long id,MaintainModelDTO modeldto) throws ServiceBizException{
        MaintainModelPO model = MaintainModelPO.findById(id);
        if(SearchMaintainModelName(modeldto.getModelLabourName(),id)){
            setModel(model,modeldto);
            model.saveIt();
        }else{
            throw new ServiceBizException("该车型组名称已存在");
        }
    }

    /**
     * 修改时查询数据库中是否有相同的车型组名称
     * @author DuPengXin
     * @date 2016年10月18日
     * @param modelLabourName
     * @return
     */

    private boolean SearchMaintainModelName(String modelLabourName,Long id) {
        StringBuilder sb=new StringBuilder("select tmg.MODEL_GROUP_ID,tmg.DEALER_CODE,tmg.MODEL_LABOUR_CODE,tmg.MODEL_LABOUR_NAME from tm_model_group tmg where tmg.MODEL_LABOUR_NAME=? and tmg.MODEL_GROUP_ID <> ?");
        List<Object> param=new ArrayList<Object>();
        param.add(modelLabourName);
        param.add(id);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 删除子表信息
     * @author DuPengXin
     * @date 2016年8月3日
     * @param id
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#deleteGroupById(java.lang.Long)
     */

    public void deleteGroupById(Long id) throws ServiceBizException{
        ModelGroupItemPO group = ModelGroupItemPO.findById(id);
        group.delete();
    }
    /**
     * 获取信息
     * @author DuPengXin
     * @date 2016年7月12日
     * @param id
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#findById(java.lang.Long)
     */

    public MaintainModelPO findById(Long id) throws ServiceBizException{
        MaintainModelPO model= MaintainModelPO.findById(id);
        return model; 
    }

    /**
     * 双击主表显示子表信息
     * @author DuPengXin
     * @date 2016年7月22日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#queryGroupItem(java.lang.Long)
     */

    @Override
    public PageInfoDto queryGroupItem(Long id) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.DEALER_CODE,t.MODEL_GROUP_ID,t.MODEL_LABOUR_CODE,t.MODEL_LABOUR_NAME,i.ITEM_ID,i.MODEL_NAME from tm_model_group t INNER JOIN tm_model_group_item i where t.MODEL_GROUP_ID = i.MODEL_GROUP_ID and i.MODEL_GROUP_ID = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return DAOUtil.pageQuery(sqlSb.toString(), queryParams);
    }


    /**
     * 设置对象
     * @author DuPengXin
     * @date 2016年7月12日
     * @param model
     * @param modeldto
     */
    public void setModel(MaintainModelPO model,MaintainModelDTO modeldto) throws ServiceBizException{
        model.setString("MODEL_LABOUR_CODE", modeldto.getModelLabourCode());
        model.setString("MODEL_LABOUR_NAME", modeldto.getModelLabourName());
        model.setInteger("OEM_TAG", DictCodeConstants.STATUS_IS_NOT);
        model.setInteger("IS_VALID", modeldto.getIsValid());
    }

    /**
     * 子表对象
     * @author DuPengXin
     * @date 2016年8月3日
     * @param group
     * @param groupdto
     */

    public void setGroup(ModelGroupItemPO group, ModelGroupItemDTO groupdto) throws ServiceBizException{
        group.setInteger("MODEL_GROUP_ID", groupdto.getModelGroupId());
        group.setString("MODEL_CODE", groupdto.getModelCode());
        //根据dealer_code取name
        String str="SELECT t.DEALER_CODE,t.MODEL_NAME from tm_model t where t.MODEL_CODE =? and t.DEALER_CODE=?";
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(groupdto.getModelCode());
        queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
        Map result=DAOUtil.findFirst(str, queryParam);
        group.setString("MODEL_NAME", result.get("model_name"));
    }


    /**
     * 获取车型组的名称和代码
     * @author rongzoujie
     * @date 2016年8月10日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#getModelName()
     */
    @Override
    public List<Map> getModelName() throws ServiceBizException{
        List<Object> param =new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT MODEL_LABOUR_NAME,DEALER_CODE,MODEL_LABOUR_CODE FROM tm_model_group where 1=1 ");
        sql.append("and IS_VALID = ?");
        param.add(DictCodeConstants.STATUS_IS_YES);
        return DAOUtil.findAll(sql.toString(),param);
    }


    /**
     * 获取车型组代码 (工单修改界面的维修项目选择界面,车型组默认选择)
    * @author rongzoujie
    * @date 2016年11月11日
    * @param modelCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.MaintainModelService#getModelGroupCode(java.lang.String)
     */
    @Override
    public String getModelGroupCode(String modelCode) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT t1.dealer_code,t1.model_labour_code FROM tm_model_group t1");
        sql.append(" LEFT JOIN tm_model_group_item t2 ON t1.dealer_code = t2.dealer_code");
        sql.append(" AND t1.model_group_id = t2.model_group_id where 1=1");
        sql.append(" and t2.model_code = ?");
        List<Object> param = new ArrayList<Object>();
        param.add(modelCode);
        
        List<Map> result = DAOUtil.findAll(sql.toString(),param);
        if(result.size()>0){
            String modelGroupCode = result.get(0).get("model_labour_code").toString();
            return modelGroupCode;
        }
        
        return null;
    }
}
