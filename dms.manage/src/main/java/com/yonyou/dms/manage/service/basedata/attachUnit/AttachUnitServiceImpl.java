
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : AttachUnitServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2016年12月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月16日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.manage.service.basedata.attachUnit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.AttachUnitDto;
import com.yonyou.dms.manage.domains.PO.basedata.AttachUnitPo;


/**
 * AttachUnitService实现类
* TODO description
* @author Administrator
* @date 2016年12月16日
*/
@Service
public class AttachUnitServiceImpl implements AttachUnitService {

    /**
    * @author Administrator
    * @date 2016年12月16日
    * @param queryParams
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#queryAttachUnit(java.util.Map)
    */

    @Override
    public PageInfoDto queryAttachUnit(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT ATTACHUNIT_ID,DEALER_CODE,UNIT_ATTACHCODE,UNIT_ATTACHNAME FROM tm_attachunit where 1=1");
       
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),null);
        return pageInfoDto;
    }

    /**
    * @author Administrator
    * @date 2016年12月16日
    * @param ptdto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#addAttachUnit(com.yonyou.dms.manage.domains.DTO.basedata.AttachUnitDto)
    */

    @Override
    public Long addAttachUnit(AttachUnitDto ptdto) throws ServiceBizException {
        if(!CommonUtils.isNullOrEmpty(getAttachUnitByCode(ptdto.getUnitAttachcode()))){
            throw new ServiceBizException("已存在此挂靠单位代码");
        }
        if(!CommonUtils.isNullOrEmpty(getAttachUnitByName(ptdto.getUnitAttachname()))){
            throw new ServiceBizException("已存在此挂靠单位名称");
        }
        AttachUnitPo ptPo=new AttachUnitPo();
        setAttachUnitPo(ptPo,ptdto);
        ptPo.saveIt();
        return ptPo.getLongId();
    }
    
    
    /**
     * 设置挂靠单位
     * @author ZhengHe
     * @date 2016年7月18日
     * @param ptPo
     * @param ptdto
     */
    public void setAttachUnitPo(AttachUnitPo ptPo,AttachUnitDto ptdto){
        ptPo.set("UNIT_ATTACHCODE",ptdto.getUnitAttachcode());
        ptPo.set("UNIT_ATTACHNAME",ptdto.getUnitAttachname());
    }

    
    /**
    * 根据Name查询信息
    * @author ZhengHe
    * @date 2016年UNIT_ATTACHCODE
    * @param UNIT_ATTACHNAME
    * @return
    * @throws ServiceBizException
    */
  @SuppressWarnings("rawtypes")
private Collection<?> getAttachUnitByName(String unitAttachname) throws ServiceBizException {
      StringBuilder sqlSb = new StringBuilder("SELECT ATTACHUNIT_ID,DEALER_CODE,UNIT_ATTACHCODE,UNIT_ATTACHNAME FROM tm_attachunit where 1=1");
      List<Object> params = new ArrayList<Object>();
      sqlSb.append(" and UNIT_ATTACHNAME=?");
      params.add(unitAttachname);
      List<Map> attachList=DAOUtil.findAll(sqlSb.toString(), params);
      return attachList;
    }

  
  /**
  * 根据Code查询信息
  * @author ZhengHe
  * @date 2016年12月16日
  * @param UNIT_ATTACHCODE
  * @return
  * @throws ServiceBizException
  */
@SuppressWarnings("rawtypes")
private Collection<?> getAttachUnitByCode(String unitAttachcode) throws ServiceBizException{
    StringBuilder sqlSb = new StringBuilder("SELECT ATTACHUNIT_ID,DEALER_CODE,UNIT_ATTACHCODE,UNIT_ATTACHNAME FROM tm_attachunit where 1=1");
    List<Object> params = new ArrayList<Object>();
    sqlSb.append(" and UNIT_ATTACHCODE=?");
    params.add(unitAttachcode);
    List<Map> attachList=DAOUtil.findAll(sqlSb.toString(), params);
    return attachList;
    }



    /**
    * @author Administrator
    * @date 2016年12月16日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#getAttachUnitById(java.lang.Long)
    */

    @Override
    public AttachUnitPo getAttachUnitById(Long id) throws ServiceBizException {
        return AttachUnitPo.findById(id);
    }

    /**
    * @author Administrator
    * @date 2016年12月16日
    * @param id
    * @param ptdto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#modifyAttachUnit(java.lang.Long, com.yonyou.dms.manage.domains.DTO.basedata.AttachUnitDto)
    */

    @Override
    public void modifyAttachUnit(Long id, AttachUnitDto ptdto) throws ServiceBizException {
        AttachUnitPo ptPo=AttachUnitPo.findById(id);
        if(!StringUtils.isEquals(ptPo.get("UNIT_ATTACHNAME"), ptdto.getUnitAttachname())&&!CommonUtils.isNullOrEmpty(getAttachUnitByName(ptdto.getUnitAttachname().trim()))){
            throw new ServiceBizException("已存在此挂靠单位名称");
        }
        setAttachUnitPo(ptPo,ptdto);
        ptPo.saveIt();

    }

    /**
     * 查询挂靠单位（下拉框）
    * @author Administrator
    * @date 2016年12月16日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#findAllAttachUnit()
    */

    @SuppressWarnings("rawtypes")
	@Override
    public List<Map> findAllAttachUnit() throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT ATTACHUNIT_ID,DEALER_CODE,UNIT_ATTACHCODE,UNIT_ATTACHNAME FROM tm_attachunit where 1=1 ");
        List<Object> param=new ArrayList<Object>();
        param.add(DictCodeConstants.STATUS_IS_YES);
        return DAOUtil.findAll(sb.toString(), param);
    }

    /**
    * @author Administrator
    * @date 2016年12月16日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.attachUnit.AttachUnitService#deleteAttachUnitById(java.lang.Long)
    */

    @Override
    public void deleteAttachUnitById(Long id) throws ServiceBizException {
        AttachUnitPo ptPo=AttachUnitPo.findById(id);
        ptPo.deleteCascadeShallow();

    }


}
