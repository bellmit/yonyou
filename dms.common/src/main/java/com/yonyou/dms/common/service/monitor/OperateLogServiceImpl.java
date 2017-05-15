
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : OperateLogServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.common.service.monitor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 操作日志实现接口
 * @author yll
 * @date 2016年7月14日
 */
@Service
public class OperateLogServiceImpl implements OperateLogService{

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OperateLogServiceImpl.class);

	/**
	 * 日志的查询方法
	 * @author yll
	 * @date 2016年8月7日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.OperateLogService#queryOperateLog(java.util.Map)
	 */
	@Override
	public PageInfoDto queryOperateLog(Map<String, String> queryParam) throws ServiceBizException {
	    SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder("SELECT A.LOG_ID,A.DEALER_CODE,A.OPERATE_DATE,A.OPERATE_CONTENT,A.OPERATE_TYPE,A.OPERATOR,");
		 sb.append("B.EMPLOYEE_NAME OPERATOR_NAME,C.CODE_CN_DESC OPERATE_TYPE_NAME FROM tt_operate_log A ");
		 sb.append("LEFT JOIN(SELECT DEALER_CODE,EMPLOYEE_NO,EMPLOYEE_NAME FROM   ");          
		 sb.append("( SELECT te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME FROM tm_employee te INNER JOIN tt_operate_log tol  ");
		 sb.append("ON tol.OPERATOR=te.EMPLOYEE_NO AND te.DEALER_CODE=tol.DEALER_CODE           ");
		 sb.append(" )vm_employee_operate_log ) B ON B.EMPLOYEE_NO=A.OPERATOR  ");
		 sb.append("LEFT JOIN (SELECT CODE_ID,CODE_CN_DESC FROM TC_CODE  )C ON C.CODE_ID=A.OPERATE_TYPE     ");
		 sb.append(" WHERE A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
		 List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("operateDateFrom"))){
		    sb.append(" AND A.OPERATE_DATE>=? ");
			params.add(queryParam.get("operateDateFrom"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("operateDateTo"))){
		    sb.append(" AND A.OPERATE_DATE<? ");
			params.add(queryParam.get("operateDateTo"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("operatorCode"))){//操作员代码
		    sb.append(" AND A.OPERATOR LIKE ?");
		    //params.add(Integer.parseInt(queryParam.get("operator")));
			params.add("%"+queryParam.get("operatorCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("operateCode"))){//操作类型代码
		    sb.append(" AND A.OPERATE_TYPE LIKE ?");
		    //params.add("%"+queryParam.get("operateType")+"%");
			params.add(Integer.parseInt(queryParam.get("operateCode")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("operateConntent"))){
            sb.append(" AND A.OPERATE_CONTENT LIKE ?");
            params.add("%"+queryParam.get("operateConntent")+"%");
        }
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
		return pageInfoDto;
	}

	/**
	 * 记录操作日志(作废)
	* @author jcsi
	* @date 2016年10月30日
	* @param operateLogPo
	* @param operateLogDto
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.common.service.monitor.OperateLogService#writeOperateLog(com.yonyou.dms.common.domains.PO.monitor.OperateLogPO, com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto)
	 */
    @Override
    @Deprecated
    public void writeOperateLog(OperateLogDto operateLogDto) throws ServiceBizException {
        OperateLogPO operateLogPo=new OperateLogPO();
        operateLogPo.setTimestamp("OPERATE_DATE", new Date());
        operateLogPo.setString("OPERATE_CONTENT", operateLogDto.getOperateContent());  //操作内容
        operateLogPo.setInteger("OPERATE_TYPE", operateLogDto.getOperateType()); //模块
        operateLogPo.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo()); 
        operateLogPo.setString("REMARK", operateLogDto.getRemark());
        operateLogPo.saveIt();
        
        
        
    }

    /**
	 * 记录操作日志
	* @author jcsi
	* @date 2016年11月21日
	* @param operateContent,operateType
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.common.service.monitor.OperateLogService#writeOperateLog(com.yonyou.dms.common.domains.PO.monitor.OperateLogPO, com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto)
	 */
	@Override
	public void recordOperateLog(String operateContent, Integer operateType, String... param) {
		 OperateLogPO operateLogPo=new OperateLogPO();
	     operateLogPo.setTimestamp("OPERATE_DATE", new Date());
	     operateLogPo.setString("OPERATE_CONTENT", operateContent);  //操作内容
	     operateLogPo.setInteger("OPERATE_TYPE", operateType); //模块
	     operateLogPo.setString("OPERATOR", FrameworkUtil.getLoginInfo().getEmployeeNo());
	     if(StringUtils.isNullOrEmpty(param)){
	    	 operateLogPo.setString("REMARK", param[0]);
	     }
	     operateLogPo.saveIt();
	}
	/**
	 * 查询操作员
	* @author yujiangheng
	* @date 2017年4月18日
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.common.service.monitor.OperateLogService#getAllSelect()
	 */
    @Override
    public List<Map> getAllSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,EMPLOYEE_NO ,EMPLOYEE_NAME  FROM tm_employee WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), params);
        return result;
    }

    /**
     * 获取导出数据
    * @author yujiangheng
    * @date 2017年4月18日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.common.service.monitor.OperateLogService#queryToExport(java.util.Map)
     */
    @Override
    public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT A.LOG_ID,A.DEALER_CODE,A.OPERATE_DATE,A.OPERATE_CONTENT,A.OPERATE_TYPE,A.OPERATOR,");
        sb.append("B.EMPLOYEE_NAME OPERATOR_NAME,C.CODE_CN_DESC OPERATE_TYPE_NAME FROM tt_operate_log A ");
        sb.append("LEFT JOIN(SELECT DEALER_CODE,EMPLOYEE_NO,EMPLOYEE_NAME FROM   ");          
        sb.append("( SELECT te.DEALER_CODE,te.EMPLOYEE_NO,te.EMPLOYEE_NAME FROM tm_employee te INNER JOIN tt_operate_log tol  ");
        sb.append("ON tol.OPERATOR=te.EMPLOYEE_NO AND te.DEALER_CODE=tol.DEALER_CODE           ");
        sb.append(" )vm_employee_operate_log ) B ON B.EMPLOYEE_NO=A.OPERATOR  ");
        sb.append("LEFT JOIN (SELECT CODE_ID,CODE_CN_DESC FROM TC_CODE  )C ON C.CODE_ID=A.OPERATE_TYPE     ");
        sb.append(" WHERE A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        List<Object> params =new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("operatorCode"))){//操作员代码
            sb.append(" AND A.OPERATOR LIKE ?");
            //params.add(Integer.parseInt(queryParam.get("operator")));
            params.add("%"+queryParam.get("operatorCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("operateCode"))){//操作类型代码
            sb.append(" AND A.OPERATE_TYPE LIKE ?");
            //params.add("%"+queryParam.get("operateType")+"%");
            params.add(Integer.parseInt(queryParam.get("operateCode")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("operateConntent"))){
            sb.append(" AND A.OPERATE_CONTENT LIKE ?");
            params.add("%"+queryParam.get("operateConntent")+"%");
        }
        for (int i = 0; i < params.size(); i++) { 
            if (params.get(i).equals(FrameworkUtil.getLoginInfo().getDealerCode())) { 
                params.remove(i); 
                i--; 
            } 
        }
        List<Map> list = DAOUtil.findAll(sb.toString(), params);
        return list;
    }
    
    



}
