
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceProjectServiceImpl.java
*
* @Author : zhongsw
*
* @Date : 2016年9月11日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月11日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.ServiceProjectDTO;
import com.yonyou.dms.retail.domains.PO.ordermanage.ServiceProjectPO;

/**
 * 服务项目定义实现类
* TODO description
* @author zhongsw
* @date 2016年9月11日
*/
@Service
public class ServiceProjectServiceImpl implements ServiceProjectService{
    
    /**前台界面查询方法
    * @author zhongsw
    * @date 2016年9月11日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ServiceProjectService#searchServiceProject(java.util.Map)
    */
    	
    @Override
    public PageInfoDto searchServiceProject(Map<String,String> queryParam) throws ServiceBizException{
        StringBuilder sb=new StringBuilder("select SERVICE_ID,DEALER_CODE,SERVICE_CODE,SERVICE_NAME,SERVICE_TYPE,DIRECTIVE_PRICE,COST_PRICE,REMARK from TM_SALES_SERVICES where 1=1 ");      
        List<Object> insuranceSql=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("service_code"))){
            sb.append(" and SERVICE_CODE like ? ");
            insuranceSql.add("%"+queryParam.get("service_code")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("service_name"))){
            sb.append(" and SERVICE_NAME like ? ");
            insuranceSql.add("%"+queryParam.get("service_name")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("service_type"))){
            sb.append(" and SERVICE_TYPE = ? ");
            insuranceSql.add(Integer.parseInt(queryParam.get("service_type")));
        }
        
        
        System.out.println("**********************************");
        System.out.println(sb.toString());
        System.out.println("**********************************");
        
        PageInfoDto id=DAOUtil.pageQuery(sb.toString(), insuranceSql);
        return id;
    }
	
	
	
	
	
    /**新增数据
    * @author zhongsw
    * @date 2016年9月11日
    * @param serviceprojectdto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ServiceProjectService#add(com.yonyou.dms.retail.domains.DTO.ordermanage.ServiceProjectDTO)
    */
    	
    @Override
    public Long add(ServiceProjectDTO serviceprojectdto,ServiceProjectPO lap) throws ServiceBizException {
        StringBuffer sb=new StringBuffer("select SERVICE_ID,DEALER_CODE,SERVICE_CODE from TM_SALES_SERVICES where SERVICE_CODE=?");
        List<Object> list= new ArrayList<Object>();
        list.add(serviceprojectdto.getService_code());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        
        StringBuffer sb2=new StringBuffer("select SERVICE_ID,DEALER_CODE,SERVICE_CODE from TM_SALES_SERVICES where SERVICE_NAME=?");
        List<Object> list2= new ArrayList<Object>();
        list2.add(serviceprojectdto.getService_name());
        List<Map> map2=DAOUtil.findAll(sb2.toString(), list2);
        
      /*  if(map.size()>0 || map2.size()>0){
            throw new ServiceBizException("服务代码或名称重复！");
        }else{*/
        lap.setString("SERVICE_CODE",serviceprojectdto.getService_code());
        lap.setString("SERVICE_NAME", serviceprojectdto.getService_name());
        lap.setInteger("SERVICE_TYPE", serviceprojectdto.getService_type());
        lap.setDouble("DIRECTIVE_PRICE", serviceprojectdto.getDirective_price());
        lap.setDouble("COST_PRICE", serviceprojectdto.getCost_price());
        lap.setString("REMARK", serviceprojectdto.getRemark());
        lap.saveIt();
        return lap.getLongId();
       /* }*/
    }

    /**根据ID 修改保存
    * @author zhongsw
    * @date 2016年9月11日
    * @param id
    * @param serviceprojectdto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ServiceProjectService#update(java.lang.Long, com.yonyou.dms.retail.domains.DTO.ordermanage.ServiceProjectDTO)
    */
 
    @Override
    public void update(Long id, ServiceProjectDTO serviceprojectdto) throws ServiceBizException{
        ServiceProjectPO lap=ServiceProjectPO.findById(id);
        StringBuffer sb=new StringBuffer("select SERVICE_ID,DEALER_CODE,SERVICE_CODE from TM_SALES_SERVICES where SERVICE_NAME=?");
        List<Object> list= new ArrayList<Object>();
        list.add(serviceprojectdto.getService_name());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        String s=lap.getString("SERVICE_NAME");
      /*  if(map.size()==0 || s.equals(serviceprojectdto.getService_name()) && map.size()==1){*/
            lap.setString("SERVICE_CODE",serviceprojectdto.getService_code());
            lap.setString("SERVICE_NAME", serviceprojectdto.getService_name());
            lap.setInteger("SERVICE_TYPE", serviceprojectdto.getService_type());
            lap.setDouble("DIRECTIVE_PRICE", serviceprojectdto.getDirective_price());
            lap.setDouble("COST_PRICE", serviceprojectdto.getCost_price());
            lap.setString("REMARK", serviceprojectdto.getRemark());
            lap.saveIt();
       /* }else{
            throw new ServiceBizException("项目名称重复！");
        }*/
    }
    
    
    /**显示编辑明细数据
    * @author zhongsw
    * @date 2016年9月14日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ServiceProjectService#editSearch(java.lang.Long)
    */
    	
    @SuppressWarnings("rawtypes")
	@Override
    public Map editSearch(Long id) throws ServiceBizException{
        StringBuffer sb= new StringBuffer("select SERVICE_ID,DEALER_CODE,SERVICE_CODE,SERVICE_NAME,SERVICE_TYPE,DIRECTIVE_PRICE,COST_PRICE,REMARK from TM_SALES_SERVICES where SERVICE_ID=?");
        List<Object> l=new ArrayList<Object>();
        l.add(id);
        Map map=DAOUtil.findFirst(sb.toString(), l);
        return map;
    } 

    /**根据ID 删除
    * @author zhongsw
    * @date 2016年9月11日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.ServiceProjectService#deleteById(java.lang.Long)
    */
    	
    @Override
    public void deleteById(Long id) throws ServiceBizException{
        ServiceProjectPO wtp=ServiceProjectPO.findById(id);
        wtp.delete();
    }
}
