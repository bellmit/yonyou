
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : SalesTraceResultSearchServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年1月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月10日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.customer.domains.DTO.customerManage.ColumnsDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.TableHeaderDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;


/**
* TODO description
* @author Administrator
* @date 2017年1月10日
*/
@Service
public class SalesTraceResultSearchServiceImpl implements SalesTraceResultSearchService {

    @Autowired
    private OperateLogService operateLogService;
    /**
    * @author Administrator
    * @date 2017年1月10日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#querySalesTraceResultInput(java.util.Map)
    */

    @Override
    public PageInfoDto querySalesTraceResultInput(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append( " SELECT ");
                   sb.append( " 0 AS VER,A.TRANCER,A.DEALER_CODE,em.USER_NAME as SOLD_NAME, EE.USER_NAME AS EMPLOYEE_NAME,");
                   sb.append( " A.INPUT_DATE, A.TRACE_TASK_ID,");
                   sb.append( " A.CUSTOMER_NAME, ");
                   sb.append( " A.CUSTOMER_STATUS, ");
                   sb.append( " A.CUSTOMER_TYPE, ");
                   sb.append( " A.VIN, ");
                   sb.append( " '' as TECHNICIAN_LIST, ");
                   sb.append( " D.BRAND_NAME, ");
                   sb.append( " S.SERIES_NAME, ");
                   sb.append( " M.MODEL_NAME, ");
                   sb.append( " A.CUSTOMER_NO, A.TASK_REMARK,");
                   sb.append( " A.INPUTER,A.TRACE_STATUS,H.TRACE_TIME,H.SALES_DATE, ");
                   sb.append(" A.SO_NO,E.CONTACTOR_MOBILE,E.CONTACTOR_PHONE,E.ZIP_CODE, ");
                   sb.append(" E.E_MAIL,E.PROVINCE,E.CITY,E.DISTRICT,E.ADDRESS,H.CONSULTANT,");
                   sb.append(" QU.QUESTIONNAIRE_NAME,k.STOCK_OUT_DATE, ");
                   sb.append( " F.BIRTHDAY" );
                   sb.append( " FROM ");
                   sb.append( " TT_SALES_TRACE_TASK A ");
                   sb.append( " LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE  "  );                 
                   sb.append( " LEFT JOIN ("+CommonConstants.VM_VEHICLE+")  H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE");
                   sb.append( " LEFT JOIN ("+CommonConstants.VM_OWNER+")  F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
                   sb.append(  " left join  ( "+CommonConstants.VM_BRAND +") D on A.BRAND =D.BRAND_CODE and A.DEALER_CODE = D.DEALER_CODE ");
                   sb.append(  " left join  ( "+CommonConstants.VM_SERIES +") S on A.SERIES =S.SERIES_CODE and S.BRAND_CODE = D.BRAND_CODE and D.DEALER_CODE = S.DEALER_CODE ");
                   sb.append(  " left join  ( "+CommonConstants.VM_MODEL +") M on A.MODEL =M.MODEL_CODE AND M.BRAND_CODE=S.BRAND_CODE AND M.SERIES_CODE =S.SERIES_CODE and S.DEALER_CODE = M.DEALER_CODE ");
                   sb.append(" Left join (select distinct ");
                   sb.append(" Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
                   sb.append(" from ("+CommonConstants.VT_TRACE_QUESTIONNAIRE+") N  ");
                   sb.append(" inner join TT_SALES_TRACE_TASK_QUESTION Q  ");
                   sb.append(" on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE AND Q.DEALER_CODE=N.DEALER_CODE) QU  ");
                   sb.append(" on QU.TRACE_TASK_ID=A.TRACE_TASK_ID ");
                   //增加出库日期
                   sb.append("  left join TT_SALES_ORDER k on k.DEALER_CODE=a.DEALER_CODE and k.so_no=a.so_no and k.vin=a.vin  ");
                   sb.append( " left join TM_USER em on em.USER_ID=E.SOLD_BY and em.DEALER_CODE=E.DEALER_CODE");
                   sb.append( " LEFT JOIN TM_USER EE ON A.TRANCER = EE.USER_ID AND A.dealer_code=EE.dealer_code ");
                   sb.append( " WHERE (A.TRACE_STATUS ="+DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END+" or A.TRACE_STATUS="+DictCodeConstants.DICT_TRACING_STATUS_FAIL_END+") ");
                 
                 this.sqlToLike(sb, queryParam.get("intentBrand"), "BRAND", "A");
                 this.sqlToLike(sb, queryParam.get("intentSeries"), "SERIES", "A");
                 this.sqlToLike(sb, queryParam.get("intentModel"), "MODEL", "A");
                 this.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
                 this.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
                 this.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
                 this.sqlToDate(sb, queryParam.get("beginconfirmeddate"), queryParam.get("endconfirmeddate"), "SALES_DATE", "H");
                 this.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
                 this.sqlToLike(sb, queryParam.get("customerType"), "CUSTOMER_TYPE", "A");
                 this.sqlToDate(sb, queryParam.get("beginoutdate"), queryParam.get("endoutdate"), "STOCK_OUT_DATE", "k");
                 this.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
                 
                 
        List<Object> queryList = new ArrayList<Object>();
        
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;  
        
    
    }
    
    
    /**
        * TODO 拼接sql语句模糊查询
        * 
        * @author yangjie
        * @date 2016年12月28日
        * @param param 查询条件
        * @param field 查询的字段
        * @param alias 表的别名
        * @return
        */
       public void sqlToLike(StringBuilder sb, String param, String field, String alias) {
           if (StringUtils.isNotBlank(param)) {
               sb.append("AND ");
               if (StringUtils.isNotBlank(alias)) {
                   sb.append(alias + "." + field);
               } else {
                   sb.append(field);
               }
               sb.append(" LIKE '%" + param + "%' ");
           }
       }

       /**
        * TODO 拼接sql语句等量查询
        * 
        * @author yangjie
        * @date 2016年12月28日
        * @param param 查询条件
        * @param field 查询的字段
        * @param alias 表的别名
        * @return
        */
       public void sqlToEquals(StringBuilder sb, String param, String field, String alias) {
           if (StringUtils.isNotBlank(param)) {
               sb.append(" AND ");
               if (StringUtils.isNotBlank(alias)) {
                   sb.append(alias + "." + field);
               } else {
                   sb.append(field);
               }
               sb.append(" = '" + param + "' ");
           }
       }
       
       /**
        * TODO 拼接sql语句时间查询(单个字段)
        * 
        * @author yangjie
        * @date 2016年12月28日
        * @param begin 开始时间
        * @param end 结束时间
        * @param field 查询的字段
        * @param alias 表的别名
        * @return
        */
       public void sqlToDate(StringBuilder sb, String begin, String end, String field, String alias) {
           if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
               sb.append("AND ");
               if (StringUtils.isNotBlank(alias)) {
                   sb.append(alias + "." + field);
               } else {
                   sb.append(field);
               }
               sb.append(" between '" + begin + "' AND '" + end + "' ");
           } else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
               sb.append("AND ");
               if (StringUtils.isNotBlank(alias)) {
                   sb.append(alias + "." + field);
               } else {
                   sb.append(field);
               }
               sb.append(" >= '" + begin + "' ");
           } else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
               sb.append("AND ");
               if (StringUtils.isNotBlank(alias)) {
                   sb.append(alias + "." + field);
               } else {
                   sb.append(field);
               }
               sb.append(" <= '" + end + "' ");
           }
       }
       
    /**
    * excel导出查询方法
    * 
    * @author Administrator
    * @date 2017年1月10日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#querySafeToExport(java.util.Map)
    */
       @SuppressWarnings({ "unchecked", "rawtypes", "unused" })
       @Override
       public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {
            
            StringBuilder sb = new StringBuilder();
            sb.append( " SELECT ");
            sb.append( " 0 AS VER,A.TRANCER,A.DEALER_CODE,em.USER_NAME as SOLD_NAME, EE.USER_NAME AS EMPLOYEE_NAME,");
            sb.append( " A.INPUT_DATE, A.TRACE_TASK_ID,");
            sb.append( " A.CUSTOMER_NAME, ");
            sb.append( " A.CUSTOMER_STATUS, ");
            sb.append( " A.CUSTOMER_TYPE, ");
            sb.append( " A.VIN, ");
            sb.append( " '' as TECHNICIAN_LIST, ");
            sb.append( " D.BRAND_NAME, ");
            sb.append( " S.SERIES_NAME, ");
            sb.append( " M.MODEL_NAME, ");
            sb.append( " A.CUSTOMER_NO, A.TASK_REMARK,");
            sb.append( " A.INPUTER,A.TRACE_STATUS,H.TRACE_TIME,H.SALES_DATE, ");
            sb.append(" A.SO_NO,E.CONTACTOR_MOBILE,E.CONTACTOR_PHONE,E.ZIP_CODE, ");
            sb.append(" E.E_MAIL,E.PROVINCE,E.CITY,E.DISTRICT,E.ADDRESS,H.CONSULTANT,");
            sb.append(" QU.QUESTIONNAIRE_NAME,k.STOCK_OUT_DATE, ");
            sb.append( " F.BIRTHDAY" );
            sb.append( " FROM ");
            sb.append( " TT_SALES_TRACE_TASK A ");
            sb.append( " LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE  "  );                 
            sb.append( " LEFT JOIN ("+CommonConstants.VM_VEHICLE+")  H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE");
            sb.append( " LEFT JOIN ("+CommonConstants.VM_OWNER+")  F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
            sb.append(  " left join  ( "+CommonConstants.VM_BRAND +") D on A.BRAND =D.BRAND_CODE and A.DEALER_CODE = D.DEALER_CODE ");
            sb.append(  " left join  ( "+CommonConstants.VM_SERIES +") S on A.SERIES =S.SERIES_CODE and S.BRAND_CODE = D.BRAND_CODE and D.DEALER_CODE = S.DEALER_CODE ");
            sb.append(  " left join  ( "+CommonConstants.VM_MODEL +") M on A.MODEL =M.MODEL_CODE AND M.BRAND_CODE=S.BRAND_CODE AND M.SERIES_CODE =S.SERIES_CODE and S.DEALER_CODE = M.DEALER_CODE ");
            sb.append(" Left join (select distinct ");
            sb.append(" Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
            sb.append(" from ("+CommonConstants.VT_TRACE_QUESTIONNAIRE+") N  ");
            sb.append(" inner join TT_SALES_TRACE_TASK_QUESTION Q  ");
            sb.append(" on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE AND Q.DEALER_CODE=N.DEALER_CODE) QU  ");
            sb.append(" on QU.TRACE_TASK_ID=A.TRACE_TASK_ID ");
            //增加出库日期
            sb.append("  left join TT_SALES_ORDER k on k.DEALER_CODE=a.DEALER_CODE and k.so_no=a.so_no and k.vin=a.vin  ");
            sb.append( " left join TM_USER em on em.USER_ID=E.SOLD_BY and em.dealer_code=E.dealer_code");
            sb.append( " LEFT JOIN TM_USER EE ON A.TRANCER = EE.USER_ID and A.dealer_code=EE.dealer_code ");
            sb.append( " WHERE (A.TRACE_STATUS ="+DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END+" or A.TRACE_STATUS="+DictCodeConstants.DICT_TRACING_STATUS_FAIL_END+") ");
          
          this.sqlToLike(sb, queryParam.get("intentBrand"), "BRAND", "A");
          this.sqlToLike(sb, queryParam.get("intentSeries"), "SERIES", "A");
          this.sqlToLike(sb, queryParam.get("intentModel"), "MODEL", "A");
          this.sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
          this.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
          this.sqlToLike(sb, queryParam.get("customerNo"), "CUSTOMER_NO", "A");
          this.sqlToDate(sb, queryParam.get("beginconfirmeddate"), queryParam.get("endconfirmeddate"), "SALES_DATE", "H");
          this.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
          this.sqlToLike(sb, queryParam.get("customerType"), "CUSTOMER_TYPE", "A");
          this.sqlToDate(sb, queryParam.get("beginoutdate"), queryParam.get("endoutdate"), "STOCK_OUT_DATE", "k");
          this.sqlToLike(sb, queryParam.get("traceStatus"), "TRACE_STATUS", "A");
          
                   
                     LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class); 
                     List<Map> list  = null;
                     List<Object> queryList = new ArrayList<Object>();
                     list = DAOUtil.findAll(sb.toString(), queryList);
                    for (Map map : list) {
                         if (map.get("TRACE_STATUS") != null && map.get("TRACE_STATUS") != "") {
                             if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371001) {
                                 map.put("TRACE_STATUS", "未跟踪");
                             } else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371002) {
                                 map.put("TRACE_STATUS", "继续跟踪");
                             }else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371003) {
                                 map.put("TRACE_STATUS", "成功结束跟踪");
                             }else if (Integer.parseInt(map.get("TRACE_STATUS").toString()) == 12371004) {
                                 map.put("TRACE_STATUS", "失败结束跟踪");
                             }                       
                         }
                         
                         if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
                             if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181001) {
                                 map.put("CUSTOMER_TYPE", "个人");
                             } else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181002) {
                                 map.put("CUSTOMER_TYPE", "公司");
                             }                       
                         }
                         
                     }
                     
                     
                     
                     OperateLogDto operateLogDto=new OperateLogDto();
                     operateLogDto.setOperateContent("销售回访结果查询导出");
                     operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
                     operateLogService.writeOperateLog(operateLogDto);
                
                  
           return list;
           
           
       }

       /**
        * 获取追加表头
        * 
        * @author Administrator
        * @date 2017年3月21日
        * @return
        * @throws ServiceBizException
        * (non-Javadoc)
        * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#getHeaderList(java.util.Map)
        */
	@Override
	public List<TableHeaderDTO> getHeaderList() throws ServiceBizException {
		StringBuffer sql=new StringBuffer();
		sql.append("select tq.question_code,tq.question_name,tq.answer_group_no  from  tt_trace_question tq where 1=1");
		List<Map> list=DAOUtil.findAll(sql.toString(), null);
		for(int i=0;i<=list.size();i++){
			Integer answerGroupNo=(Integer) list.get(i).get("answer_group_no");
			getColumnsList(answerGroupNo);
		}
		
		
		
		return null;
	}

	
	 /**
	    * 获取追加列名
	    * 
	    * @author Administrator
	    * @date 2017年3月21日
	    * @param queryParam
	    * @return
	    * @throws ServiceBizException
	    * (non-Javadoc)
	    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#getColumnsList(java.util.Map)
	    */
	@Override
	public List<ColumnsDTO> getColumnsList(Integer answerGroupNo) throws ServiceBizException {
		StringBuffer sql=new StringBuffer();
		sql.append("select answer_no,answer_name from tt_answer where answer_group_no=?");
		List<Map> list=DAOUtil.findAll(sql.toString(), null);
		List<ColumnsDTO> reList=new ArrayList();
		
		
		return null;
	}

	
	
	/**
	    * 获取答案
	    * 
	    * @author Administrator
	    * @date 2017年3月21日
	    * @param queryParam
	    * @return
	    * @throws ServiceBizException
	    * (non-Javadoc)
	    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#getColumnsList(java.util.Map)
	    */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> getQuestionAndAnswers(Map<String, String> queryParam) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode=loginInfo.getDealerCode();
		StringBuilder sql = new StringBuilder();
		sql.append(" (SELECT " + " DISTINCT A.QUESTION_CODE, ") ;
		sql.append( " A.QUESTION_CONTENT, " + " C.ANSWER_NO, " + " C.ANSWER ") ;
		sql.append( " FROM ('"+CommonConstants.VT_TRACE_QUESTION +"')A, ") ;
		sql.append( " ('"+CommonConstants.VT_ANSWER_GROUP +"')B, ('"+CommonConstants.VT_ANSWER +"')C  WHERE ") ;
		sql.append( " A.ANSWER_GROUP_NO=B.ANSWER_GROUP_NO ") ;
		sql.append( " AND B.ANSWER_GROUP_NO=C.ANSWER_GROUP_NO ") ;
		sql.append( " AND A.QUESTION_CODE IN ") ;
		sql.append( " (SELECT DISTINCT(D.QUESTION_CODE) " + " FROM ") ;
		sql.append( " TT_SALES_TRACE_TASK_QUESTION D " + " WHERE "  + "  D.DEALER_CODE='" + dealerCode) ;
		sql.append( "' AND EXISTS (SELECT E.TRACE_TASK_ID FROM  TT_SALES_TRACE_TASK E " ) ;
		sql.append( " LEFT JOIN ('"+CommonConstants.VM_VEHICLE+"') H ON E.VIN = H.VIN AND E.DEALER_CODE = H.DEALER_CODE " ) ;
		sql.append( " LEFT JOIN TT_SALES_ORDER k on k.DEALER_CODE=E.DEALER_CODE and k.so_no=E.so_no and k.vin=E.vin " ) ;
		sql.append( " WHERE  E.D_KEY = 0 AND E.DEALER_CODE='"+dealerCode+"' AND (E.TRACE_STATUS =12371003 or E.TRACE_STATUS=12371004) ") ; 
		sql.append( " AND E.TRACE_TASK_ID = D.TRACE_TASK_ID ") ;
		sql.append( " )))sw") ;
		 this.sqlToLike(sql, queryParam.get("intentBrand"), "BRAND", "");
         this.sqlToLike(sql, queryParam.get("intentSeries"), "SERIES", "");
         this.sqlToLike(sql, queryParam.get("intentModel"), "MODEL", "");
         this.sqlToLike(sql, queryParam.get("vin"), "VIN", "");
         this.sqlToDate(sql, queryParam.get("beginDate"), queryParam.get("endDate"), "INPUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerNo"), "CUSTOMER_NO", "");
         this.sqlToDate(sql, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"), "SALES_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerName"), "CUSTOMER_NAME", "");
         this.sqlToLike(sql, queryParam.get("customerType"), "CUSTOMER_TYPE", "");
         this.sqlToDate(sql, queryParam.get("begin_confirme_date"), queryParam.get("end_confirme_date"), "STOCK_OUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("traceStatus"), "TRACE_STATUS", "");
         
         
         List<Object> queryList = new ArrayList<Object>();
		 List<Map> list=DAOUtil.findAll(sql.toString(), queryList);
		
		return list;
	}

	

		/**
	    * 跟踪任务问题及明细
	    * 
	    * @author Administrator
	    * @date 2017年3月21日
	    * @param queryParam
	    * @return
	    * @throws ServiceBizException
	    * (non-Javadoc)
	    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#getColumnsList(java.util.Map)
	    */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryTtQuestionDetail(Map<String, String> queryParam) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode=loginInfo.getDealerCode();
		StringBuilder sql = new StringBuilder();
		sql.append("(SELECT A.TRACE_TASK_ID,A.TRACE_TASK_QUESTION_ID, A.QUESTIONNAIRE_CODE,A.QUESTION_CODE,A.ANSWER,A.SORT_NUM,B.ANSWER_NO ");
		sql.append(" FROM TT_SALES_TRACE_TASK_QUESTION  A ");
		sql.append(" LEFT JOIN TT_SALES_TRACE_ANSWER_DETAIL  B ON A.TRACE_TASK_ID=B.TRACE_TASK_ID ");
		sql.append(" AND A.DEALER_CODE=B.DEALER_CODE AND A.TRACE_TASK_QUESTION_ID=B.TRACE_TASK_QUESTION_ID ");
		sql.append(" WHERE  A.DEALER_CODE='"+dealerCode+"'" );
		sql.append(" AND EXISTS (SELECT E.TRACE_TASK_ID FROM TT_SALES_TRACE_TASK E " );
		sql.append(" LEFT JOIN ('"+CommonConstants.VM_VEHICLE+"') H ON E.VIN = H.VIN AND E.DEALER_CODE = H.DEALER_CODE " );
		sql.append(" LEFT JOIN TT_SALES_ORDER k on k.DEALER_CODE=E.DEALER_CODE and k.so_no=E.so_no and k.vin=E.vin " );
		sql.append(" WHERE E.D_KEY = 0 AND E.DEALER_CODE='"+dealerCode+"' AND (E.TRACE_STATUS =12371003 or E.TRACE_STATUS=12371004) "); 
		sql.append(" AND E.TRACE_TASK_ID = A.TRACE_TASK_ID)sw");
		 this.sqlToLike(sql, queryParam.get("intentBrand"), "BRAND", "");
         this.sqlToLike(sql, queryParam.get("intentSeries"), "SERIES", "");
         this.sqlToLike(sql, queryParam.get("intentModel"), "MODEL", "");
         this.sqlToLike(sql, queryParam.get("vin"), "VIN", "");
         this.sqlToDate(sql, queryParam.get("beginDate"), queryParam.get("endDate"), "INPUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerNo"), "CUSTOMER_NO", "");
         this.sqlToDate(sql, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"), "SALES_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerName"), "CUSTOMER_NAME", "");
         this.sqlToLike(sql, queryParam.get("customerType"), "CUSTOMER_TYPE", "");
         this.sqlToDate(sql, queryParam.get("begin_confirme_date"), queryParam.get("end_confirme_date"), "STOCK_OUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("traceStatus"), "TRACE_STATUS", "");
         
         
         List<Object> queryList = new ArrayList<Object>();
		 List<Map> list=DAOUtil.findAll(sql.toString(), queryList);
		
		return list;
	}

		/**
	    * 查询所有问卷问题，答案
	    * 
	    * @author Administrator
	    * @date 2017年3月21日
	    * @param queryParam
	    * @return
	    * @throws ServiceBizException
	    * (non-Javadoc)
	    * @see com.yonyou.dms.customer.service.customerManage.SalesTraceResultSearchService#getColumnsList(java.util.Map)
	    */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryAnswerAndQuestionAll(Map<String, String> queryParam) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode=loginInfo.getDealerCode();
		StringBuilder sql = new StringBuilder();
		sql.append("(SELECT DISTINCT G.* FROM (SELECT CASE WHEN C.QUESTION_TYPE = 11321001 THEN 12781001 ELSE F.IS_VALID END  IS_VALID,D.DEALER_CODE,D.IS_SERVICE_QUESTIONNAIRE, D.QUESTIONNAIRE_CODE,D.QUESTIONNAIRE_NAME,D.QUESTIONNAIRE_TYPE,C.QUESTION_CODE,C.ANSWER_GROUP_NO,");
		sql.append(" C.QUESTION_NAME,C.QUESTION_CONTENT,C.QUESTION_TYPE,coalesce(C.SORT_NUM,0) AS SORT_NUM,C.IS_MUST_FILLED,F.ANSWER,F.ANSWER_DESC,F.ANSWER_NO ");
		sql.append("  FROM ('"+CommonConstants.VT_TRACE_QUESTIONNAIRE +"') D ");
		sql.append(" LEFT JOIN   (SELECT A.QUESTIONNAIRE_CODE AS QUESTIONNAIRE_CODE,A.SORT_NUM,B.DEALER_CODE,B.QUESTION_CODE AS QUESTION_CODE ,");
		sql.append(" B.ANSWER_GROUP_NO AS ANSWER_GROUP_NO,B.QUESTION_NAME AS QUESTION_NAME,B.QUESTION_CONTENT AS QUESTION_CONTENT,B.QUESTION_TYPE AS  QUESTION_TYPE,");
		sql.append(" B.IS_MUST_FILLED AS IS_MUST_FILLED  FROM ('"+CommonConstants.VT_QUESTION_RELATION +"')  A  ");
		sql.append(" LEFT JOIN ('"+CommonConstants.VT_TRACE_QUESTION +"') B  ");
		sql.append(" ON A.QUESTION_CODE=B.QUESTION_CODE AND A.DEALER_CODE=B.DEALER_CODE ");
		sql.append(" WHERE A.DEALER_CODE='"+dealerCode+"'  )  C ");
		sql.append(" ON C.QUESTIONNAIRE_CODE=D.QUESTIONNAIRE_CODE ");
		//sql.append(" LEFT JOIN VT_ACTIVITY E   ON D.ACTIVITY_CODE = E.ACTIVITY_CODE AND D.DEALER_CODE = E.DEALER_CODE AND E.DEALER_CODE='"+entityCode+"' ");
		sql.append(" LEFT JOIN ('"+CommonConstants.VT_ANSWER +"') F ON F.DEALER_CODE=C.DEALER_CODE AND F.ANSWER_GROUP_NO=C.ANSWER_GROUP_NO )  G ");
		sql.append(" LEFT JOIN TT_SALES_TRACE_TASK_QUESTION H ON G.QUESTION_CODE=H.QUESTION_CODE AND G.QUESTIONNAIRE_CODE=H.QUESTIONNAIRE_CODE AND H.DEALER_CODE='"+dealerCode+"' AND G.DEALER_CODE=H.DEALER_CODE ");
		sql.append(" WHERE G.DEALER_CODE='"+dealerCode+"' ");
		sql.append(" AND G.IS_SERVICE_QUESTIONNAIRE = " + DictCodeConstants.DICT_IS_NO);
		sql.append(" AND G.QUESTION_CODE IS NOT NULL AND G.IS_VALID=" + DictCodeConstants.DICT_IS_YES );
		sql.append(" AND EXISTS (SELECT E.TRACE_TASK_ID FROM  TT_SALES_TRACE_TASK E "); 
		sql.append( " LEFT JOIN ('"+CommonConstants.VM_VEHICLE +"') H ON E.VIN = H.VIN AND E.DEALER_CODE = H.DEALER_CODE "); 
		sql.append( " LEFT JOIN TT_SALES_ORDER k on k.DEALER_CODE=E.DEALER_CODE and k.so_no=E.so_no and k.vin=E.vin ");
		sql.append( " WHERE E.D_KEY = 0 AND E.DEALER_CODE='"+dealerCode+"' AND (E.TRACE_STATUS =12371003 or E.TRACE_STATUS=12371004))sw " );
				// update by yt 2011.10.8 H表中没有TRACE_TASK_ID字段   + " AND E.TRACE_TASK_ID = H.TRACE_TASK_ID "
				
		 this.sqlToLike(sql, queryParam.get("intentBrand"), "BRAND", "");
         this.sqlToLike(sql, queryParam.get("intentSeries"), "SERIES", "");
         this.sqlToLike(sql, queryParam.get("intentModel"), "MODEL", "");
         this.sqlToLike(sql, queryParam.get("vin"), "VIN", "");
         this.sqlToDate(sql, queryParam.get("beginDate"), queryParam.get("endDate"), "INPUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerNo"), "CUSTOMER_NO", "");
         this.sqlToDate(sql, queryParam.get("begin_confirmed_date"), queryParam.get("end_confirmed_date"), "SALES_DATE", "");
         this.sqlToLike(sql, queryParam.get("customerName"), "CUSTOMER_NAME", "");
         this.sqlToLike(sql, queryParam.get("customerType"), "CUSTOMER_TYPE", "");
         this.sqlToDate(sql, queryParam.get("begin_confirme_date"), queryParam.get("end_confirme_date"), "STOCK_OUT_DATE", "");
         this.sqlToLike(sql, queryParam.get("traceStatus"), "TRACE_STATUS", "");
         
         
         List<Object> queryList = new ArrayList<Object>();
		 List<Map> list=DAOUtil.findAll(sql.toString(), queryList);
		
		
		
		return list;
	} 

}
