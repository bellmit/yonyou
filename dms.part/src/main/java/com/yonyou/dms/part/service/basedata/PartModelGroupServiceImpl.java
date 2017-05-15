
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : StoreServiceImpl.java
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PartModelGroupPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartModelGroupDTO;
import com.yonyou.dms.part.domains.DTO.basedata.StoreDTO;


/**
 * 配件车型组实现类
 * @author chenwei
 * @date 2017年3月22日
 */
@Service
public class PartModelGroupServiceImpl implements PartModelGroupService {

	/**
	 * @author chenwei
	 * @date 2017年3月22日
	 * @param id
	 * @return 查询结果
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.part.service.basedata.PartModelGroupService#findById(java.lang.Long)
	 */
	@Override
	public PartModelGroupPo findByPrimaryKey(String partModelGroupCode) throws ServiceBizException {
		// TODO Auto-generated method stub
		PartModelGroupPo wtpo = PartModelGroupPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),partModelGroupCode);
		return wtpo;
	}
	
	/**
	 * 查询
	 * @author chenwei
	 * @date 2017年3月22日
	 * @param queryParam
	 * @return 查询
	 */
	@Override
	public PageInfoDto searchPartModelGroup(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder("select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME,OEM_TAG,DOWN_TIMESTAMP from TM_PART_MODEL_GROUP where 1=1 ");
		List<Object> partModelGroupSql=new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("part_model_group_code"))){
			sb.append(" and PART_MODEL_GROUP_CODE like ?");
			partModelGroupSql.add("%"+queryParam.get("part_model_group_code")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("part_model_group_name"))){
			sb.append(" and PART_MODEL_GROUP_NAME like ? ");
			partModelGroupSql.add("%"+queryParam.get("part_model_group_name")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("oem_tag"))){
			sb.append(" and OEM_TAG = ? ");
			partModelGroupSql.add(Integer.parseInt(queryParam.get("oem_tag")));
		}
		PageInfoDto id=DAOUtil.pageQuery(sb.toString(), partModelGroupSql);
		return id;
	}
	
	/**
	 * 配件车型组新增界面实现
	 * @author zhongshiwei
	 * @date 2016年7月11日
	 * @param store
	 * @return 新增
	 */
	@Override
	public String insertPartModelGroupPo(PartModelGroupDTO partModelGroupto) throws ServiceBizException {
		StringBuffer sb= new StringBuffer("select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME,OEM_TAG,DOWN_TIMESTAMP from TM_PART_MODEL_GROUP where 1=1 and PART_MODEL_GROUP_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(partModelGroupto.getPart_model_group_code());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
	    StringBuffer sb2= new StringBuffer("select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME,OEM_TAG,DOWN_TIMESTAMP from TM_PART_MODEL_GROUP where 1=1 and PART_MODEL_GROUP_NAME=?");
	    List<Object> list2=new ArrayList<Object>();
        list2.add(partModelGroupto.getPart_model_group_name());
        List<Map> map2=DAOUtil.findAll(sb2.toString(), list2);
	    if(map.size()>0 || map2.size()>0){
	        throw new ServiceBizException("配件车型组代码或名称不能重复！");
	    }else{
	    	PartModelGroupPo lap = new PartModelGroupPo();
		lap.setString("DEALER_CODE", partModelGroupto.getDealer_code());
		lap.setString("PART_MODEL_GROUP_CODE", partModelGroupto.getPart_model_group_code());
		lap.setString("PART_MODEL_GROUP_NAME", partModelGroupto.getPart_model_group_name());
		lap.setInteger("OEM_TAG", partModelGroupto.getOem_tag());
		lap.setDate("DOWN_TIMESTAMP", partModelGroupto.getDown_timestamp());
		lap.saveIt();
		return lap.getString("PART_MODEL_GROUP_CODE");
	    }
	}
	
	/**
	 * 更新
	 * @author zhongsw
	 * @date 2016年8月5日
	 * @param id
	 * @param storeDTO
	 * @throws ServiceBizException(non-Javadoc)
	 * @see com.yonyou.dms.part.service.basedata.StoreService#updateStore(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.StoreDTO)
	 */
	@Override
	public void updatePartModelGroup(String partModelGroupCode, PartModelGroupDTO partModelGroupto) throws ServiceBizException {
		// TODO Auto-generated method stub
		PartModelGroupPo lap = PartModelGroupPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),partModelGroupCode);
		/*StringBuffer sb=new StringBuffer("select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME from TM_PART_MODEL_GROUP where PART_MODEL_GROUP_NAME = ? ");
		List<Object> list =new ArrayList<Object>();
		list.add(partModelGroupto.getPart_model_group_name());
		List<Map> map=DAOUtil.findAll(sb.toString(), list);
		String s=(String) lap.get("PART_MODEL_GROUP_NAME");
		if(s.equals(partModelGroupto.getPart_model_group_name()) || map.size()==0){*/
			lap.setString("PART_MODEL_GROUP_NAME", partModelGroupto.getPart_model_group_name());
		    lap.saveIt();
		/*}else if(map.size()>0){
		    throw new ServiceBizException("配件车型组名称不能重复！");
		}*/
	}
	
	@Override
	public void deletePartModelGroupById(Long id) throws ServiceBizException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Map> queryStore(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		return null;
	}
     
  

}