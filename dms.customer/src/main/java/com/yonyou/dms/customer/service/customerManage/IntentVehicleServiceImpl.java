package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 客户意向综合查询
 * 
 * @author wangxin
 * @date 2016年12月26日
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service
public class IntentVehicleServiceImpl implements IntentVehicleService{
	

	
	@Override
    public PageInfoDto queryintentVehicle(Map<String, String> queryParam) throws ServiceBizException {
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer("select  E.MODEL_NAME,D.USER_NAME AS EMPLOYEE_NAME,A.ITEM_ID,A.DEALER_CODE,A.INTENT_MODEL,A.COMPETITOR_BRAND,C.CUSTOMER_NAME,C.CUSTOMER_TYPE,"
        		+ "C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.SOLD_BY,C.INTENT_LEVEL  FROM  TT_CUSTOMER_INTENT_DETAIL  A "
        		+ "INNER JOIN  TT_CUSTOMER_INTENT B ON A.INTENT_ID=B.INTENT_ID AND A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY "
        		+ "INNER JOIN  TM_POTENTIAL_CUSTOMER  C  ON B.CUSTOMER_NO= C.CUSTOMER_NO AND B.DEALER_CODE=C.DEALER_CODE AND B.D_KEY=C.D_KEY "
        		+ " LEFT JOIN TM_USER  D ON C.SOLD_BY = D.USER_ID AND C.DEALER_CODE=D.DEALER_CODE"
        		+ " LEFT JOIN TM_MODEL E ON A.INTENT_MODEL = E.MODEL_CODE AND E.DEALER_CODE= A.DEALER_CODE AND e.`IS_VALID`  = 12781001 "
        		+ "WHERE  1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and C.CUSTOMER_NAME LIKE ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and C.CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and C.CONTACTOR_MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorMobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelcode"))) {
            sb.append(" and A.INTENT_MODEL = ?");
            queryList.add( queryParam.get("modelcode") );
            
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
            sb.append(" and C.CUSTOMER_TYPE = ?");
            queryList.add((queryParam.get("customerType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and C.INTENT_LEVEL = ?");
            queryList.add((queryParam.get("intentLevel")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and C.SOLD_BY = ?");
            queryList.add((queryParam.get("soldBy")));
        }
        
        sb.append(DAOUtilGF.getOwnedByStr("C", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45705500", loginInfo.getDealerCode()));
        System.err.println("sql     "+sb.toString());
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
	
	/**
     * 导出查询
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#queryCarownerInfoforExport(java.util.Map)
     */

//    @SuppressWarnings("rawtypes")
//    @Override
//    public List<Map> queryintentVehiclerExport(Map<String, String> queryParam) throws ServiceBizException {
//        StringBuffer sb = new StringBuffer("select  E.MODEL_NAME,D.EMPLOYEE_NAME,A.ITEM_ID,A.DEALER_CODE,A.INTENT_MODEL,A.COMPETITOR_BRAND,C.CUSTOMER_NAME,C.CUSTOMER_TYPE,"
//        		+ "C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.SOLD_BY,C.INTENT_LEVEL  FROM  TT_CUSTOMER_INTENT_DETAIL  A "
//        		+ "INNER JOIN  TT_CUSTOMER_INTENT B ON A.INTENT_ID=B.INTENT_ID AND A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY "
//        		+ "INNER JOIN  TM_POTENTIAL_CUSTOMER  C  ON B.CUSTOMER_NO= C.CUSTOMER_NO AND B.DEALER_CODE=C.DEALER_CODE AND B.D_KEY=C.D_KEY "
//        		+ " LEFT JOIN TM_EMPLOYEE  D ON C.SOLD_BY = D.EMPLOYEE_NO "
//        		+ " LEFT JOIN TM_MODEL E ON A.INTENT_MODEL = E.MODEL_CODE "
//        		+ "WHERE  1=1 ");
//        List<Object> queryList = new ArrayList<Object>();
//        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
//            sb.append(" and C.CUSTOMER_NAME LIKE ?");
//            queryList.add("%" + queryParam.get("customerName") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
//            sb.append(" and C.CONTACTOR_PHONE like ?");
//            queryList.add("%" + queryParam.get("contactorPhone") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
//            sb.append(" and C.CONTACTOR_MOBILE like ?");
//            queryList.add("%" + queryParam.get("contactorMobile") + "%");
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("modelcode"))) {
//            sb.append(" and A.INTENT_MODEL = ?");
//            queryList.add(Integer.parseInt(queryParam.get("modelcode")));
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
//            sb.append(" and C.CUSTOMER_TYPE = ?");
//            queryList.add(Integer.parseInt(queryParam.get("customerType")));
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
//            sb.append(" and C.INTENT_LEVEL = ?");
//            queryList.add(Integer.parseInt(queryParam.get("intentLevel")));
//        }
//        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
//            sb.append(" and C.SOLD_BY = ?");
//            queryList.add(Integer.parseInt(queryParam.get("soldBy")));
//        }
//        System.err.println("导出SQL:   "+sb.toString());
//        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
//        
//        OperateLogDto operateLogDto = new OperateLogDto();
//        operateLogDto.setOperateContent("车主信息导出");
//        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
//        operateLogService.writeOperateLog(operateLogDto);
//        
//        return resultList;
//    }
    

	@Override
	public List<Map> queryintentVehiclerExport(Map<String, String> queryParam) throws ServiceBizException {

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = null;
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select  E.MODEL_NAME,D.USER_NAME AS EMPLOYEE_NAME,A.ITEM_ID,A.DEALER_CODE,A.INTENT_MODEL,A.COMPETITOR_BRAND,C.CUSTOMER_NAME,C.CUSTOMER_TYPE,"
        		+ "C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.SOLD_BY,C.INTENT_LEVEL  FROM  TT_CUSTOMER_INTENT_DETAIL  A "
        		+ "INNER JOIN  TT_CUSTOMER_INTENT B ON A.INTENT_ID=B.INTENT_ID AND A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY "
        		+ "INNER JOIN  TM_POTENTIAL_CUSTOMER  C  ON B.CUSTOMER_NO= C.CUSTOMER_NO AND B.DEALER_CODE=C.DEALER_CODE AND B.D_KEY=C.D_KEY "
        		+ " LEFT JOIN TM_USER  D ON C.SOLD_BY = D.USER_ID "
        		+ " LEFT JOIN TM_MODEL E ON A.INTENT_MODEL = E.MODEL_CODE  AND e.`IS_VALID`  = 12781001 "
        		+ "WHERE  1=1 ");
		 if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
	            sb.append(" and C.CUSTOMER_NAME LIKE ?");
	            queryList.add("%" + queryParam.get("customerName") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
	            sb.append(" and C.CONTACTOR_PHONE like ?");
	            queryList.add("%" + queryParam.get("contactorPhone") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
	            sb.append(" and C.CONTACTOR_MOBILE like ?");
	            queryList.add("%" + queryParam.get("contactorMobile") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("modelcode"))) {
	            sb.append(" and A.INTENT_MODEL = ?");
	            queryList.add((queryParam.get("modelcode")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
	            sb.append(" and C.CUSTOMER_TYPE = ?");
	            queryList.add((queryParam.get("customerType")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
	            sb.append(" and C.INTENT_LEVEL = ?");
	            queryList.add((queryParam.get("intentLevel")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
	            sb.append(" and C.SOLD_BY = ?");
	            queryList.add((queryParam.get("soldBy")));
	        }

		list = DAOUtil.findAll(sb.toString(), queryList);

		return list;

	}
    
    
    

}
