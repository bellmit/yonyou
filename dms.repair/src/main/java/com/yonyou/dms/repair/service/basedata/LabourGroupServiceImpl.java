
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourGroupServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月4日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月4日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourSubgroupDTO;
import com.yonyou.dms.repair.domains.PO.basedata.LabourGroupPO;
import com.yonyou.dms.repair.domains.PO.basedata.LabourSubgroupPO;

/**
 * 维修项目分类 接口 实现
 * @author DuPengXin
 * @date 2016年8月4日
 */
@Service
@SuppressWarnings("rawtypes")
public class LabourGroupServiceImpl implements LabourGroupService{
    //定义日志类型
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(LabourGroupServiceImpl.class);

    /**
     * 
     * 查询所有分类为了创建分类树状图
     * @author rongzoujie
     * @date 2016年8月9日
     * @return
     */
	public Map<String,List<Map>> queryAlllabourGroup(){
        List<Map> primaryLabourGroup = DAOUtil.findAll("select DEALER_CODE,MAIN_GROUP_CODE,MAIN_GROUP_NAME from tm_labour_group",new ArrayList<Object>());
        List<Map> subLabourGroup = DAOUtil.findAll("SELECT DEALER_CODE,SUB_GROUP_CODE,SUB_GROUP_NAME FROM tm_labour_subgroup",new ArrayList<Object>());
        Map<String,List<Map>> labourGroup = new HashMap<String, List<Map>>();
        labourGroup.put("primary", primaryLabourGroup);
        labourGroup.put("second", subLabourGroup);
        return labourGroup;
    }


    /**
     * 查询详细信息
     * @author DuPengXin
     * @date 2016年8月4日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#QueryLabourGroup(java.util.Map)
     */

    @Override
    public PageInfoDto QueryLabourGroup(Map<String, String> queryParam) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,MAIN_GROUP_CODE,MAIN_GROUP_NAME,DOWN_TAG from tm_labour_group where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if(!StringUtils.isNullOrEmpty(queryParam.get("mainGroupCode"))){
            sqlSb.append(" and MAIN_GROUP_CODE like ?");
            params.add("%"+queryParam.get("mainGroupCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("mainGroupName"))){
            sqlSb.append(" AND MAIN_GROUP_NAME like ?");
            params.add("%"+queryParam.get("mainGroupName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("downTag"))){
            sqlSb.append(" AND DOWN_TAG = ?");
            params.add(Integer.parseInt(queryParam.get("downTag")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }


    /**
     * 双击主表查询子表信息
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#queryLabourSubgroup(java.lang.Long)
     */

    @Override
    public PageInfoDto queryLabourSubgroup(String id) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("SELECT t.DEALER_CODE,t.MAIN_GROUP_CODE,t.MAIN_GROUP_NAME,t.DOWN_TAG,m.SUB_GROUP_CODE,m.SUB_GROUP_NAME FROM tm_labour_group t INNER JOIN tm_labour_subgroup m WHERE t.MAIN_GROUP_CODE=m.MAIN_GROUP_CODE and m.MAIN_GROUP_CODE = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return DAOUtil.pageQuery(sqlSb.toString(), queryParams);
    }


    /**
     * 新增
     * @author DuPengXin
     * @date 2016年8月4日
     * @param labourgroupdto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#addLabourGroup(com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupDTO)
     */

    @Override
    public String addLabourGroup(LabourGroupDTO labourgroupdto) throws ServiceBizException{
        LabourGroupPO labourgroup = new LabourGroupPO();
        if(SearchLabourGroup(labourgroupdto.getMainGroupCode(),labourgroupdto.getMainGroupName())){
            setlabourgroup(labourgroup,labourgroupdto);
            labourgroup.saveIt();
            return labourgroup.getString("MAIN_GROUP_CODE");
        }else{
            throw new ServiceBizException("该维修项目分类代码或名称已存在");
        }
    }

    /**
     * 新增是查询数据库中是否有维修项目分类代码或名称相同的数据
     * @author DuPengXin
     * @date 2016年10月19日
     * @param mainGroupName
     * @param mainGroupCode
     * @return
     */

    private boolean SearchLabourGroup(String mainGroupName, String mainGroupCode) {
        StringBuilder sb=new StringBuilder("select tlg.DEALER_CODE,tlg.MAIN_GROUP_CODE,tlg.MAIN_GROUP_NAME from tm_labour_group tlg where tlg.MAIN_GROUP_CODE=? or tlg.MAIN_GROUP_NAME=?");
        List<Object> param=new ArrayList<Object>();
        param.add(mainGroupName);
        param.add(mainGroupCode);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }


    /**
     * 子表新增
     * @author DuPengXin
     * @date 2016年8月4日
     * @param labourgroupdto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#addLabourSubgroup(com.yonyou.dms.repair.domains.DTO.basedata.LabourSubgroupDTO)
     */

    @Override
    public String addLabourSubgroup(LabourSubgroupDTO laboursubgroupdto) throws ServiceBizException{
        LabourSubgroupPO laboursubgroup = new LabourSubgroupPO();
        // 设置对象属性
        if(SearchLabourSubgroup(laboursubgroupdto.getSubGroupCode(),(laboursubgroupdto.getSubGroupName()))){
            laboursubgroup.setString("MAIN_GROUP_CODE", laboursubgroupdto.getMainGroupCode());
            setlaboursubgroup(laboursubgroup, laboursubgroupdto);
            laboursubgroup.saveIt();
            return laboursubgroup.getString("SUB_GROUP_CODE");
        }else{
            throw new ServiceBizException("该维修项目二级分类代码或名称已存在");
        }
    }



    /**
     * 查询数据库中是否有相同的数据
     * @author DuPengXin
     * @date 2016年10月19日
     * @param subGroupCode
     * @param subGroupName
     * @return
     */

    private boolean SearchLabourSubgroup(String subGroupCode, String subGroupName) {
        StringBuilder sb=new StringBuilder("select tls.DEALER_CODE,tls.SUB_GROUP_CODE,tls.SUB_GROUP_NAME from tm_labour_subgroup tls where tls.SUB_GROUP_CODE=? or tls.SUB_GROUP_NAME=?");
        List<Object> param=new ArrayList<Object>();
        param.add(subGroupCode);
        param.add(subGroupName);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }


    /**
     * 修改
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @param labourgroupdto
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#updateLabourGroup(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.LabourGroupDTO)
     */

    public void updateLabourGroup(String id,LabourGroupDTO labourgroupdto) throws ServiceBizException{
        LabourGroupPO labourgroup = LabourGroupPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        if(SearchLabourGroupName(labourgroupdto.getMainGroupName(),id)){
            setlabourgroup(labourgroup,labourgroupdto);
            labourgroup.saveIt();
        }else{
            throw new ServiceBizException("该维修项目分类名称已存在");
        }
    }

    /**
     * 修改时查询数据库中是否有相同的数据
     * @author DuPengXin
     * @date 2016年10月19日
     * @param mainGroupName
     * @return
     */

    private boolean SearchLabourGroupName(String mainGroupName,String id) {
        StringBuilder sb=new StringBuilder("select tlg.DEALER_CODE,tlg.MAIN_GROUP_CODE,tlg.MAIN_GROUP_NAME from tm_labour_group tlg where tlg.MAIN_GROUP_NAME=? and tlg.MAIN_GROUP_CODE <> ?");
        List<Object> param=new ArrayList<Object>();
        param.add(mainGroupName);
        param.add(id);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 子表修改
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @param labourgsubroupdto
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#updateLabourSubgroup(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.LabourSubgroupDTO)
     */

    public void updateLabourSubgroup(String id,LabourSubgroupDTO laboursubgroupdto) throws ServiceBizException{
        LabourSubgroupPO laboursubgroup = LabourSubgroupPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),laboursubgroupdto.getMainGroupCode(),id);
        if(SearchLabourSubgroupName(laboursubgroupdto.getSubGroupName(),id)){
            setlaboursubgroup(laboursubgroup,laboursubgroupdto);
            laboursubgroup.saveIt();
        }else{
            throw new ServiceBizException("该维修项目分类名称已存在");
        }
    }

    /**
     * 修改是查询数据库中是否有相同的数据
     * @author DuPengXin
     * @date 2016年10月19日
     * @param subGroupName
     * @return
     */

    private boolean SearchLabourSubgroupName(String subGroupName,String id) {
        StringBuilder sb=new StringBuilder("select tls.DEALER_CODE,tls.SUB_GROUP_CODE,tls.SUB_GROUP_NAME from tm_labour_subgroup tls where tls.SUB_GROUP_NAME=? and tls.DEALER_CODE <> ?");
        List<Object> param=new ArrayList<Object>();
        param.add(subGroupName);
        param.add(id);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 获取信息
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#findById(java.lang.Long)
     */

    public LabourGroupPO findById(String id) throws ServiceBizException{
        LabourGroupPO labourgroup= LabourGroupPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        return labourgroup; 
    }
    /**
     * 获取子表信息
     * @author DuPengXin
     * @date 2016年8月4日
     * @param id
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#findById1(java.lang.Long)
     */

    @SuppressWarnings("unchecked")
    public Map<String,Object> findByIditem(String id) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("SELECT t.DEALER_CODE,t.MAIN_GROUP_CODE,t.MAIN_GROUP_NAME,t.DOWN_TAG,m.SUB_GROUP_CODE,m.SUB_GROUP_NAME FROM tm_labour_group t,tm_labour_subgroup m WHERE t.MAIN_GROUP_CODE=m.MAIN_GROUP_CODE and m.SUB_GROUP_CODE = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return  DAOUtil.findFirst(sqlSb.toString(), queryParams);
    }
    /**
     * 设置子表对象
     * @author DuPengXin
     * @date 2016年8月4日
     * @param laboursubgroup
     * @param labourgroupdto
     */

    private void setlaboursubgroup(LabourSubgroupPO laboursubgroup, LabourSubgroupDTO laboursubgroupdto) throws ServiceBizException{
        laboursubgroup.setString("SUB_GROUP_CODE", laboursubgroupdto.getSubGroupCode());
        laboursubgroup.setString("SUB_GROUP_NAME", laboursubgroupdto.getSubGroupName());
    }


    /**
     * 设置对象
     * @author DuPengXin
     * @date 2016年8月4日
     * @param labourgroup
     * @param labourgroupdto
     */

    private void setlabourgroup(LabourGroupPO labourgroup, LabourGroupDTO labourgroupdto) throws ServiceBizException{
        labourgroup.setString("MAIN_GROUP_CODE", labourgroupdto.getMainGroupCode());
        labourgroup.setString("MAIN_GROUP_NAME", labourgroupdto.getMainGroupName());
        labourgroup.setInteger("DOWN_TAG", labourgroupdto.getDownTag());
    }


    /**
     * 获取主分类下拉框
     * @author rongzoujie
     * @date 2016年8月10日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#getGroupList()
     */
    @Override
    public List<Map> getGroupList() {
        return DAOUtil.findAll("select MAIN_GROUP_NAME,MAIN_GROUP_CODE,DEALER_CODE from tm_labour_group",new ArrayList<Object>());
    }


    /**
     * 二级分类下来框
     * @author wantao
     * @date 2017年4月20日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.LabourGroupService#subGroupList()
     */
    @Override
    public List<Map> subGroupList(String mainGroupCode) {
        StringBuilder sql = new StringBuilder("select MAIN_GROUP_CODE,DEALER_CODE from tm_labour_group where 1=1 ");
        List<Object> queryParams = new ArrayList<Object>();
        sql.append("AND MAIN_GROUP_CODE = ?");
        queryParams.add(mainGroupCode);
        Map mainGroup = DAOUtil.findFirst(sql.toString(), queryParams);

        String primaryId = mainGroup.get("MAIN_GROUP_CODE").toString();
        sql = new StringBuilder("select SUB_GROUP_NAME,SUB_GROUP_CODE,DEALER_CODE from tm_labour_subgroup where 1=1 ");
        queryParams = new ArrayList<Object>();
        sql.append("and MAIN_GROUP_CODE = ?");
        queryParams.add(primaryId);
        List<Map> subGroup = DAOUtil.findAll(sql.toString(), queryParams);

        return subGroup;
    }
}
