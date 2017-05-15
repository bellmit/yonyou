package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class InsuranceTypeServiceImpl implements InsuranceTypeService{

	@Override
	public PageInfoDto queryInsuranceType(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sb=new StringBuilder("select DEALER_CODE,INSURANCE_TYPE_CODE,INSURANCE_TYPE_NAME,IS_COM_INSURANCE,INSURANCE_TYPE_STATUS,OEM_TAG from tm_insurance_type where 1=1 ");		
		List<Object> queryList=new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceTypeCode"))){
			  sb.append(" and INSURANCE_TYPE_CODE = ?");
			  queryList.add(queryParam.get("insuranceTypeCode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("isComInsurance"))){
			  sb.append(" and IS_COM_INSURANCE = ? ");
			  queryList.add(queryParam.get("isComInsurance"));
		}
        if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceTypeStatus"))){
			  sb.append(" and INSURANCE_TYPE_STATUS = ?");
			  queryList.add(Integer.parseInt(queryParam.get("insuranceTypeStatus")));
		}
        if(!StringUtils.isNullOrEmpty(queryParam.get("oemTag"))){
		  sb.append(" and OEM_TAG = ?");
		  queryList.add(Integer.parseInt(queryParam.get("oemTag")));
	}         		
		PageInfoDto pageInfoDto=DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}

}
