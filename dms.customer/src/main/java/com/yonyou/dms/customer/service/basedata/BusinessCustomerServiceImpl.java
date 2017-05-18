
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : CustomerResoServiceImpl.java
 *
* @Author : zhengcong
*
* @Date : 2017年3月22日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月22日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.customer.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.customer.domains.DTO.basedata.BusinessCustomerDTO;
import com.yonyou.dms.customer.domains.PO.basedata.BusinessCustomerPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务往来客户资料
 * 
 * @author chenwei
 * @date 2017年3月28日
 */
@Service
public class BusinessCustomerServiceImpl implements BusinessCustomerService {

    /**
     * 根据查询条件查询业务往来客户资料信息
     * 
     * @author chenwei
     * @date 2017年3月28日
     */
    @Override
    public PageInfoDto queryBusinessCustomer(Map<String, String> queryParam) throws ServiceBizException {

        StringBuilder sb = new StringBuilder("SELECT A.IS_VALID,A.BAL_OBJ_CODE,A.BAL_OBJ_NAME,A.ACCOUNT_AGE,A.ARREARAGE_AMOUNT,A.CUS_RECEIVE_SORT,A.CUSTOMER_CODE, A.DEALER_CODE, A.CUSTOMER_TYPE_CODE,");
        sb.append("A.CUSTOMER_NAME, A.PRE_PAY, A.CUSTOMER_SPELL, A.CUSTOMER_SHORT_NAME, A.ADDRESS, A.ZIP_CODE, A.CONTACTOR_NAME,  A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.FAX, ");
        sb.append("A.CONTRACT_NO, A.AGREEMENT_BEGIN_DATE,  A.AGREEMENT_END_DATE, A.PRICE_ADD_RATE, A.PRICE_RATE, A.SALES_BASE_PRICE_TYPE,A.LEAD_TIME, A.CREDIT_AMOUNT, ");
        sb.append("A.TOTAL_ARREARAGE_AMOUNT, A.ACCOUNT_TERM, A.TOTAL_ARREARAGE_TERM,  A.OEM_TAG, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, ");
        sb.append("B.DEALER2_CODE, B.DEALER_SHORT_NAME AS DEALER_NAME,D.VIN   FROM (SELECT B.CHILD_ENTITY AS DEALER_CODE ,A.CUSTOMER_CODE ,A.CUSTOMER_TYPE_CODE ,A.CUSTOMER_NAME ,");
        sb.append("A.CUSTOMER_SPELL ,A.CUSTOMER_SHORT_NAME ,A.ADDRESS ,A.ZIP_CODE ,A.CONTACTOR_NAME ,A.CONTACTOR_PHONE ,A.CONTACTOR_MOBILE ,A.FAX ,A.CONTRACT_NO ,");
        sb.append("A.AGREEMENT_BEGIN_DATE , A.AGREEMENT_END_DATE ,A.PRICE_ADD_RATE ,A.PRICE_RATE ,A.SALES_BASE_PRICE_TYPE ,A.CREDIT_AMOUNT , A.TOTAL_ARREARAGE_AMOUNT ,");
        sb.append("A.ACCOUNT_TERM , A.TOTAL_ARREARAGE_TERM ,(CASE WHEN PRE_PAY_PRIVATE = 1 THEN C.PRE_PAY ELSE A.PRE_PAY END) AS PRE_PAY ,");
        sb.append("( CASE  WHEN ARREARAGE_AMOUNT_PRIVATE = 1 THEN C.ARREARAGE_AMOUNT  ELSE A.ARREARAGE_AMOUNT END) AS ARREARAGE_AMOUNT ,");
        sb.append(" A.ACCOUNT_AGE , A.OEM_TAG ,A.CUS_RECEIVE_SORT ,A.BAL_OBJ_CODE ,A.BAL_OBJ_NAME ,A.LEAD_TIME,A.CREATED_BY ,A.CREATED_AT ,A.UPDATED_BY ,A.UPDATED_AT ,A.VER,");
        sb.append("A.IS_VALID  FROM TM_PART_CUSTOMER A LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP B ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_PART_CUSTOMER' ");
        sb.append(" LEFT OUTER JOIN TM_PART_CUSTOMER_SUBCLASS C ON B.CHILD_ENTITY = C.DEALER_CODE  AND A.CUSTOMER_CODE = C.CUSTOMER_CODE LEFT OUTER JOIN (");
        sb.append("SELECT DEALER_CODE, SUM(  CASE   WHEN PRIVATE_FIELD = 'PRE_PAY'  AND IS_VALID = 12781001   THEN 1  ELSE 0  END) AS PRE_PAY_PRIVATE, ");
        sb.append("SUM( CASE WHEN PRIVATE_FIELD = 'ARREARAGE_AMOUNT' AND IS_VALID = 12781001 THEN 1  ELSE 0 END) AS ARREARAGE_AMOUNT_PRIVATE ");
        sb.append("FROM TM_ENTITY_PRIVATE_FIELD  WHERE TABLE_NAME = 'TM_PART_CUSTOMER'  GROUP BY  DEALER_CODE)D ");
        sb.append("ON D.DEALER_CODE = A.DEALER_CODE) A  LEFT JOIN TM_PART_CUSTOMER C ON A.DEALER_CODE=C.DEALER_CODE AND A.CUSTOMER_CODE=C.CUSTOMER_CODE  ");
        sb.append("LEFT JOIN   TM_PART_CUSTOMER_DEALER B ON C.DEALER_CODE=B.DEALER_CODE AND C.DEALER2_CODE=B.DEALER2_CODE  LEFT JOIN (SELECT B.CHILD_ENTITY AS DEALER_CODE , ");
        sb.append(" A.OWNER_NO , A.CUSTOMER_CODE  FROM TM_OWNER A LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP B ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_OWNER' ");
        sb.append("LEFT OUTER JOIN TM_OWNER_SUBCLASS C ON B.CHILD_ENTITY = C.DEALER_CODE AND A.OWNER_NO = C.OWNER_NO ");
        sb.append("LEFT OUTER JOIN( SELECT DEALER_CODE,");
        sb.append(" SUM( CASE");
        sb.append(" WHEN PRIVATE_FIELD = 'PRE_PAY' ");
        sb.append("AND IS_VALID = 12781001 ");
        sb.append("THEN 1 ");
        sb.append("ELSE 0 ");
        sb.append("END) AS PRE_PAY_PRIVATE, ");
        sb.append("SUM( CASE ");
        sb.append("WHEN PRIVATE_FIELD = 'ARREARAGE_AMOUNT' ");
        sb.append("AND IS_VALID = 12781001 ");
        sb.append("THEN 1 ");
        sb.append("ELSE 0 ");
        sb.append("END) AS ARREARAGE_AMOUNT_PRIVATE ");
        sb.append("FROM  TM_ENTITY_PRIVATE_FIELD WHERE  TABLE_NAME = 'TM_OWNER' GROUP BY  DEALER_CODE ) D ON ");
        sb.append("D.DEALER_CODE = A.DEALER_CODE) E ON A.DEALER_CODE=E.DEALER_CODE AND A.CUSTOMER_CODE=E.CUSTOMER_CODE  LEFT JOIN (SELECT ");
        sb.append("  A.VIN , B.CHILD_ENTITY AS DEALER_CODE ,A.OWNER_NO FROM TM_VEHICLE A LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP B ");
        sb.append("ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_VEHICLE' LEFT OUTER JOIN TM_VEHICLE_SUBCLASS C ON B.CHILD_ENTITY = C.DEALER_CODE ");
        sb.append("AND A.OWNER_NO = C.OWNER_NO ");
        sb.append("AND A.VIN=C.VIN ");
        sb.append("LEFT OUTER JOIN ");
        sb.append("(SELECT DEALER_CODE FROM  TM_ENTITY_PRIVATE_FIELD WHERE  TABLE_NAME = 'TM_VEHICLE' GROUP BY  DEALER_CODE ) D");
        sb.append(" ON  D.DEALER_CODE = A.DEALER_CODE) D ON E.DEALER_CODE=D.DEALER_CODE AND E.OWNER_NO=D.OWNER_NO  WHERE  1=1   ");
        //sb.append("AND A.DEALER_CODE = '2100000'  AND A.CUSTOMER_NAME LIKE '%121212%' ");
        sb.append(" and A.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER_TYPE_CODE"))) {
            sb.append(" and A.CUSTOMER_TYPE_CODE like ? ");
            queryList.add("%" + queryParam.get("CUSTOMER_TYPE_CODE") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.CUSTOMER_NAME like ? ");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
            sb.append(" and A.CUSTOMER_CODE = ? ");
            queryList.add(queryParam.get("customerCode"));
        }
        sb.append(" ORDER BY A.CUSTOMER_CODE");
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    
    
	/**
	 * 新增
     * @author chenwei
     * @date 2017年3月22日
	 */
	@Override
	public void addBusinessCustomer(BusinessCustomerDTO cudto)throws ServiceBizException {		
	 // TODO Auto-generated method stub
	    BusinessCustomerPO businessCustomerPO = new BusinessCustomerPO();
        CheckBusinessCustomer(cudto);
        setBusinessCustomerPO(businessCustomerPO,cudto);
        businessCustomerPO.saveIt();
	}

  
    
    /**
     * 根据customerCode查询业务往来客户资料信息
     * @author chenwei
     * @date 2017年3月22日
     */
	@Override
	public Map<String, String> findByCustomerCode(String customerCode) throws ServiceBizException {
	    StringBuilder sb = new StringBuilder("select DEALER_CODE,CUSTOMER_CODE,CUSTOMER_TYPE_CODE,CUSTOMER_NAME,CUSTOMER_SPELL,CUSTOMER_SHORT_NAME,ADDRESS,ZIP_CODE,CONTACTOR_NAME,");
        sb.append("CONTACTOR_PHONE,CONTACTOR_MOBILE,FAX,CONTRACT_NO,AGREEMENT_BEGIN_DATE,AGREEMENT_END_DATE,PRICE_ADD_RATE,PRICE_RATE,SALES_BASE_PRICE_TYPE,CREDIT_AMOUNT,TOTAL_ARREARAGE_AMOUNT,ACCOUNT_TERM,TOTAL_ARREARAGE_TERM,PRE_PAY,ARREARAGE_AMOUNT,ACCOUNT_AGE,OEM_TAG,CUS_RECEIVE_SORT,BAL_OBJ_CODE,BAL_OBJ_NAME,LEAD_TIME,DEALER2_CODE from  TM_PART_CUSTOMER where 1=1 ");
    sb.append("and DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    sb.append(" and CUSTOMER_CODE = ? ");
    List<String> queryParam = new ArrayList<String>();
    queryParam.add(customerCode);
    return DAOUtil.findFirst(sb.toString(), queryParam);
}


	/**
	 * 更新
     * @author chenwei
     * @date 2017年3月21日
	 */

	@Override
	public void modifyByCustomerCode(String customerCode,BusinessCustomerDTO cudto) throws ServiceBizException{
	    BusinessCustomerPO cupo=BusinessCustomerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),customerCode);
	        cupo.setString("CUSTOMER_CODE", cudto.getCustomerCode());
		    cupo.setString("CUSTOMER_TYPE_CODE", cudto.getCustomerTypeCode());
		    cupo.setString("CUSTOMER_NAME", cudto.getCustomerName());
		    cupo.setString("CUSTOMER_SPELL", cudto.getCustomerSpell());
		    cupo.setString("CUSTOMER_SHORT_NAME", cudto.getCustomerShortName());
		    cupo.setString("ADDRESS", cudto.getAddress());
		    cupo.setString("ZIP_CODE", cudto.getZipCode());
		    cupo.setString("CONTACTOR_NAME", cudto.getContactorName());
		    cupo.setString("CONTACTOR_PHONE", cudto.getContactorPhone());
		    cupo.setString("CONTACTOR_MOBILE", cudto.getContactorMobile());
		    cupo.setString("FAX", cudto.getFax());
		    cupo.setString("CONTRACT_NO", cudto.getContractNo());
		    cupo.setDouble("CUS_RECEIVE_SORT", cudto.getCusReceiveSort());
		    cupo.setDate("AGREEMENT_BEGIN_DATE", cudto.getAgreementBeginDate());
		    cupo.setDate("AGREEMENT_END_DATE", cudto.getAgreementEndDate());
		    cupo.setDouble("LEAD_TIME", cudto.getLeadTime());
		    cupo.setDouble("PRICE_RATE", cudto.getPriceRate());
		    cupo.setDouble("SALES_BASE_PRICE_TYPE", cudto.getSalesBasePriceType());
		    cupo.setDouble("ACCOUNT_AGE", cudto.getAccountAge());
		    cupo.setDouble("PRICE_ADD_RATE", cudto.getPriceAddRate());
		    cupo.setDouble("OEM_TAG", cudto.getOemTag());
		    cupo.saveIt();
	}
	
	/**
     * 检查组合类别
     * 
     * @author chenwei
     * @date 2017年3月23日
     * @param pyto
     */

    public void CheckBusinessCustomer(BusinessCustomerDTO pyto) {
        StringBuffer sb = new StringBuffer(
                "select DEALER_CODE,CUSTOMER_CODE from TM_PART_CUSTOMER where 1=1 ");
        sb.append(" and DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sb.append(" and CUSTOMER_CODE=? ");
        List<Object> list = new ArrayList<Object>();
        list.add(pyto.getCustomerCode());
        List<Map> map = DAOUtil.findAll(sb.toString(), list);
        if (map.size() > 0) {
            throw new ServiceBizException("客户资料已经存在！");
        }
    }
    
    /**
     * 设置TroubleDescPO属性
     * 
     * @author chenwei
     * @date 2017年3月24日
     * @param typo
     * @param pyto
     */
    public void setBusinessCustomerPO(BusinessCustomerPO typo, BusinessCustomerDTO pyto) {
        typo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        typo.setString("CUSTOMER_CODE", pyto.getCustomerCode());
        typo.setString("CUSTOMER_TYPE_CODE", pyto.getCustomerTypeCode());
        typo.setString("CUSTOMER_NAME", pyto.getCustomerName());
        typo.setString("CUSTOMER_SPELL", pyto.getCustomerSpell());
        typo.setString("CUSTOMER_SHORT_NAME", pyto.getCustomerShortName());
        typo.setString("ADDRESS", pyto.getAddress());
        typo.setString("ZIP_CODE", pyto.getZipCode());
        typo.setString("CONTACTOR_NAME", pyto.getContactorName());
        typo.setString("CONTACTOR_PHONE", pyto.getContactorPhone());
        typo.setString("CONTACTOR_MOBILE", pyto.getContactorMobile());
        typo.setString("FAX", pyto.getFax());
        typo.setString("CONTRACT_NO", pyto.getContractNo());
        typo.setDouble("CUS_RECEIVE_SORT", pyto.getCusReceiveSort());
        typo.setDate("AGREEMENT_BEGIN_DATE", pyto.getAgreementBeginDate());
        typo.setDate("AGREEMENT_END_DATE", pyto.getAgreementEndDate());
        typo.setDouble("LEAD_TIME", pyto.getLeadTime());
        typo.setDouble("PRICE_RATE", pyto.getPriceRate());
        typo.setDouble("SALES_BASE_PRICE_TYPE", pyto.getSalesBasePriceType());
        typo.setDouble("ACCOUNT_AGE", pyto.getAccountAge());
        typo.setDouble("PRICE_ADD_RATE", pyto.getPriceAddRate());
        typo.setDouble("OEM_TAG", pyto.getOemTag());
    }


	/**
     * 下拉框显示客户类型列表的方法
     * @author chenwei
     * @date 2017年3月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.customer.service.basedata.BusinessCustomerService#queryCustomersDict(java.util.Map)
     */
    public List<Map> queryCustomersDict(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,CUSTOMER_TYPE_CODE,CUSTOMER_TYPE_NAME  from TM_CUSTOMER_TYPE where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        //执行查询操作
        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
        return result;
    }


    /**
     * 业务往来客户经销商导出
     * 
     * @author chenwei
     * @date 2017年3月29日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.BusinessCustomerService#queryCustomerRecordforExport(java.util.Map)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Map> queryCustomerRecordforExport(Map<String, String> queryParam) throws ServiceBizException {
        // TODO Auto-generated method stub
        StringBuffer sb = new StringBuffer();
        sb.append("select p.DEALER_CODE,p.CUSTOMER_CODE,p.CUSTOMER_TYPE_CODE,c.CUSTOMER_TYPE_NAME as CUSTOMER_TYPE_NAME,p.CUSTOMER_NAME,p.CUSTOMER_SPELL,p.CUSTOMER_SHORT_NAME,p.ADDRESS,p.ZIP_CODE,p.CONTACTOR_NAME,");
        sb.append("p.CONTACTOR_PHONE,p.CONTACTOR_MOBILE,p.FAX,p.CONTRACT_NO,p.AGREEMENT_BEGIN_DATE,p.AGREEMENT_END_DATE,p.PRICE_ADD_RATE,p.PRICE_RATE,p.SALES_BASE_PRICE_TYPE,p.CREDIT_AMOUNT,p.TOTAL_ARREARAGE_AMOUNT,p.ACCOUNT_TERM,p.TOTAL_ARREARAGE_TERM,p.PRE_PAY,p.ARREARAGE_AMOUNT,p.ACCOUNT_AGE,p.OEM_TAG as OEM_TAG,p.CUS_RECEIVE_SORT,p.BAL_OBJ_CODE,p.BAL_OBJ_NAME,p.LEAD_TIME,p.DEALER2_CODE,p.IS_VALID ");
        sb.append("from TM_PART_CUSTOMER p left join TM_CUSTOMER_TYPE c on p.CUSTOMER_TYPE_CODE=c.CUSTOMER_TYPE_CODE where 1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER_TYPE_CODE"))) {
            sb.append(" and CUSTOMER_TYPE_CODE like ?");
            queryList.add("%" + queryParam.get("CUSTOMER_TYPE_CODE") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))) {
            sb.append(" and CUSTOMER_CODE = ?");
            queryList.add(queryParam.get("customerCode"));
        }
        List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
        for(Map map :list){
            //是否有效
            if(map.get("IS_VALID")!=null && map.get("IS_VALID")!=""){
                if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781001) {
                    map.put("IS_VALID", "是");
                } else if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781002) {
                    map.put("IS_VALID", "否");
                }
            }
            //是否OEM
            if(map.get("OEM_TAG")!=null && map.get("OEM_TAG")!=""){
                if (Integer.parseInt(map.get("OEM_TAG").toString()) == 12781001) {
                    map.put("OEM_TAG", "是");
                } else if (Integer.parseInt(map.get("OEM_TAG").toString()) == 12781002) {
                    map.put("OEM_TAG", "否");
                }
            }else{
                map.put("OEM_TAG", "否");
            }
            //预收款
            if(map.get("PRE_PAY")==null && map.get("PRE_PAY")==""){
                map.put("PRE_PAY", "0");
            }
            //欠款金额
            if(map.get("ARREARAGE_AMOUNT")==null && map.get("ARREARAGE_AMOUNT")==""){
                map.put("ARREARAGE_AMOUNT", "0");
            }
            //加价率
            if(map.get("PRICE_ADD_RATE")==null && map.get("PRICE_ADD_RATE")==""){
                map.put("PRICE_ADD_RATE", "0");
            }
            //客户收款类别
            if(map.get("CUS_RECEIVE_SORT")!=null && map.get("CUS_RECEIVE_SORT")!=""){
                if (Integer.parseInt(map.get("CUS_RECEIVE_SORT").toString()) == DictCodeConstants.DICT_CUS_RECEIVE_SORT_CUSTOMER_PAY) {
                    map.put("CUS_RECEIVE_SORT", "客户付费");
                } else if (Integer.parseInt(map.get("CUS_RECEIVE_SORT").toString()) == DictCodeConstants.DICT_CUS_RECEIVE_SORT_INSURANCE_PAY) {
                    map.put("CUS_RECEIVE_SORT", "保险付费");
                } else if (Integer.parseInt(map.get("CUS_RECEIVE_SORT").toString()) == DictCodeConstants.DICT_CUS_RECEIVE_SORT_OEM_INDEMNITY) {
                    map.put("CUS_RECEIVE_SORT", "OEM索赔");
                } else if (Integer.parseInt(map.get("CUS_RECEIVE_SORT").toString()) == DictCodeConstants.DICT_CUS_RECEIVE_SORT_4S_PAY) {
                    map.put("CUS_RECEIVE_SORT", "4S店付费");
                } else if (Integer.parseInt(map.get("CUS_RECEIVE_SORT").toString()) == DictCodeConstants.DICT_CUS_RECEIVE_SORT_OTHER_INDEMNITY) {
                    map.put("CUS_RECEIVE_SORT", "其他索赔");
                }
            }else{
                map.put("CUS_RECEIVE_SORT", "客户付费");
            }
          //销售基价
            if(map.get("SALES_BASE_PRICE_TYPE")!=null && map.get("SALES_BASE_PRICE_TYPE")!=""){
                if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_COST) {
                    map.put("SALES_BASE_PRICE_TYPE", "成本价");
                } else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_SALE) {
                    map.put("SALES_BASE_PRICE_TYPE", "销售价");
                }else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INDEMNITY) {
                    map.put("SALES_BASE_PRICE_TYPE", "索赔价");
                }else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATTICE_POINT) {
                    map.put("SALES_BASE_PRICE_TYPE", "网点价");
                }else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_LATEST) {
                    map.put("SALES_BASE_PRICE_TYPE", "最新进货价");
                }else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_TAX_COST) {
                    map.put("SALES_BASE_PRICE_TYPE", "含税成本价");
                }else if (Integer.parseInt(map.get("SALES_BASE_PRICE_TYPE").toString()) == DictCodeConstants.DICT_SALES_BASE_PRICE_TYPE_INSURANCE) {
                    map.put("SALES_BASE_PRICE_TYPE", "保险价");
                }
            }else{
                map.put("SALES_BASE_PRICE_TYPE", "成本价");
            }
        }
        return list;
    }
	
}
