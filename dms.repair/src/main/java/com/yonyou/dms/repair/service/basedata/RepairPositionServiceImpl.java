
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : PositionServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年7月15日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月15日    DuPengXin   1.0
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

import com.yonyou.dms.common.domains.PO.basedata.MaintainWorkTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.controller.basedata.RepairPositionController;
import com.yonyou.dms.repair.domains.DTO.basedata.RepairPositionDTO;

/**
 * @author DuPengXin
 * @date 2016年7月15日
 */

@Service
public class RepairPositionServiceImpl implements RepairPositionService {
    //定义日志类型
    private static final Logger logger = LoggerFactory.getLogger(RepairPositionController.class);


    /**
     * 查询
     * @author DuPengXin
     * @date 2016年7月15日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairPositionService#QueryPosition(java.util.Map)
     */

    @Override
    public PageInfoDto QueryPosition(Map<String, String> queryParam) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("select REPAIR_POSITION_ID,LABOUR_POSITION_CODE,LABOUR_POSITION_NAME,LABOUR_POSITION_TYPE,IS_VALID,DEALER_CODE from  tm_repair_position where 1=1 ");
        List<Object> params = new ArrayList<Object>();

        if(!StringUtils.isNullOrEmpty(queryParam.get("labourPositionName"))){
            sqlSb.append(" and LABOUR_POSITION_NAME like ?");
            params.add("%"+queryParam.get("labourPositionName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isValid"))){
            sqlSb.append(" and IS_VALID = ?");
            params.add(Integer.parseInt(queryParam.get("isValid")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }


    /**
     * 新增
     * @author DuPengXin
     * @date 2016年7月15日
     * @param positiondto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairPositionService#addPosition(com.yonyou.dms.repair.domains.DTO.basedata.RepairPositionDTO)
     */

    @Override
    public Long addPosition(RepairPositionDTO positiondto) throws ServiceBizException{
    	MaintainWorkTypePO position = new MaintainWorkTypePO();
        if(SearchLabourPositionCode(positiondto.getLabourPositionCode(),(positiondto.getLabourPositionName()))){
        setPosition(position,positiondto);
        position.saveIt();
        return position.getLongId();
        }else{
            throw new ServiceBizException("该工位代码或名称已存在");
        }
    }

    
    /**
    * 查询数据库中工位代码或名称是否已存在
    * @author DuPengXin
    * @date 2016年10月17日
    * @param labourPositionCode
    * @return
    */
    	
    private boolean SearchLabourPositionCode(String labourPositionCode,String labourPositionName) {
        StringBuilder sb=new StringBuilder("select trp.REPAIR_POSITION_ID,trp.DEALER_CODE,trp.LABOUR_POSITION_CODE,trp.LABOUR_POSITION_NAME from tm_repair_position trp where trp.LABOUR_POSITION_CODE=? or trp.LABOUR_POSITION_NAME=?");
        List<Object> param=new ArrayList<Object>();
        param.add(labourPositionCode);
        param.add(labourPositionName);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }


    /**
     * 修改
     * @author DuPengXin
     * @date 2016年7月15日
     * @param id
     * @param positiondto
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairPositionService#updatePosition(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.RepairPositionDTO)
     */

    public void updatePosition(Long id,RepairPositionDTO positiondto) throws ServiceBizException{
        MaintainWorkTypePO position = MaintainWorkTypePO.findById(id);
        if(SearchLabourPositionName(positiondto.getLabourPositionName(),id)){
            setPosition(position,positiondto);
            position.saveIt();
        }else{
            throw new ServiceBizException("该工位名称已存在");
        }
    }

    
    /**
    * 修改是查询数据库是否有相同的工位名称
    * @author DuPengXin
    * @date 2016年10月18日
    * @param labourPositionName
    * @return
    */
    	
    private boolean SearchLabourPositionName(String labourPositionName,Long id) {
        StringBuilder sb=new StringBuilder("select trp.REPAIR_POSITION_ID,trp.DEALER_CODE,trp.LABOUR_POSITION_CODE,trp.LABOUR_POSITION_NAME from tm_repair_position trp where trp.LABOUR_POSITION_NAME=? and trp.REPAIR_POSITION_ID <> ?");
        List<Object> param=new ArrayList<Object>();
        param.add(labourPositionName);
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
     * @date 2016年7月15日
     * @param id
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairPositionService#findById(java.lang.Long)
     */

    public MaintainWorkTypePO findById(Long id) throws ServiceBizException{
        MaintainWorkTypePO position= MaintainWorkTypePO.findById(id);
        return position; 
    }

    /**
     * 设置对象属性
     * @author DuPengXin
     * @date 2016年7月15日
     * @param position
     * @param positiondto
     */

    public void setPosition(MaintainWorkTypePO position,RepairPositionDTO positiondto)throws ServiceBizException {
        position.setString("LABOUR_POSITION_CODE", positiondto.getLabourPositionCode());
        position.setString("LABOUR_POSITION_NAME", positiondto.getLabourPositionName());
        position.setInteger("LABOUR_POSITION_TYPE", positiondto.getLabourPositionType());
        position.setInteger("IS_VALID", positiondto.getIsValid());
    }

    /**
     * 工位 （下拉框显示）
     * @author jcsi
     * @date 2016年8月1日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.RepairPositionService#findAllRepairPosition()
     */
    @Override
    public List<Map> findAllRepairPosition() throws ServiceBizException{
        StringBuilder sb=new StringBuilder("SELECT DEALER_CODE,LABOUR_POSITION_CODE,LABOUR_POSITION_NAME from tm_repair_position where 1=1");
        List<Object> param=new ArrayList<Object>();
       // param.add(DictCodeConstants.STATUS_IS_YES);
        return DAOUtil.findAll(sb.toString(), param);
    }


    @Override
    public String queryPositionType(Long id) throws ServiceBizException {
        StringBuilder sql =new StringBuilder("SELECT t1.CODE_CN_DESC, FROM tc_code t1 WHERE t1.code_id = ");
        sql.append(" (SELECT LABOUR_POSITION_TYPE FROM tm_repair_position t2 WHERE t2.REPAIR_POSITION_ID = ?)");
        return null;
    }

    /**
     * 派工工位下拉框
    * TODO description
    * @author rongzoujie
    * @date 2016年9月26日
    * @return
    * @throws ServiceBizException
     */
    @Override
    public List<Map> queryPosition() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT t2.CODE_CN_DESC, t1.REPAIR_POSITION_ID,t1.DEALER_CODE,t1.LABOUR_POSITION_NAME FROM tm_repair_position t1");
        sql.append(" Inner Join tc_code t2 ON t1.IS_VALID = ? and t1.LABOUR_POSITION_TYPE = t2.code_id");
        List<Object> param=new ArrayList<Object>();
        param.add(DictCodeConstants.STATUS_IS_YES);
        return DAOUtil.findAll(sql.toString(), param);
    }

}
