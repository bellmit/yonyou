
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : FailModelsServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月5日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月5日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.retail.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.controller.basedata.FailModelsController;
import com.yonyou.dms.retail.domains.DTO.basedata.FailModelsDto;
import com.yonyou.dms.retail.domains.PO.basedata.FailModels;

/**
 * 
 * 战败车型信息接口实现
 * @author yll
 * @date 2016年6月30日
 */
@Service
public class FailModelsServiceImpl implements FailModelsService{

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(FailModelsController.class);


	/**
	 * 
	 * @author yll
	 * @date 2016年6月30日
	 * @param queryParam
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.basedata.FailModelsService#queryFailModel(java.util.Map)
	 */
	@Override
	public PageInfoDto queryFailModel(Map<String, String> queryParam) {
		StringBuilder sqlSb = new StringBuilder("SELECT tfm.FAIL_MODEL_ID, ts.SERIES_NAME as INTENT_SERIES,tb.BRAND_NAME AS INTENT_BRAND, tfm.DEALER_CODE, tfm.FAIL_MODEL, tfm.FAIL_MODEL_DESC, tfm.OEM_TAG, tfm.IS_VALID FROM tm_Fail_MODEL tfm LEFT JOIN tm_series ts on tfm.INTENT_SERIES=ts.SERIES_CODE AND ts.DEALER_CODE=tfm.DEALER_CODE LEFT JOIN tm_brand tb ON tfm.INTENT_BRAND=tb.BRAND_CODE AND tfm.DEALER_CODE=tb.DEALER_CODE WHERE 1 = 1");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("intentSeries"))){
			sqlSb.append(" and ts.SERIES_NAME like ?");
			params.add("%"+queryParam.get("intentSeries")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("failModelDesc"))){
			sqlSb.append(" and tfm.FAIL_MODEL_DESC like ?");
			params.add("%"+queryParam.get("failModelDesc")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and tfm.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and tfm.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
		return pageInfoDto;

	}



	/**
	 * 
	 * @author yll
	 * @date 2016年6月30日
	 * @param failModelDto
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.basedata.FailModelsService#addFailModel(com.yonyou.dms.retail.domains.DTO.basedata.FailModelsDto)
	 */
	@Override
	public Long addFailModel(FailModelsDto failModelDto)  {
		String failModelCode=failModelDto.getFailModel();
		if(StringUtils.isNullOrEmpty(failModelCode)){
			throw new ServiceBizException("战败车型代码不能为空");
		}
		StringBuilder sqlSb = new StringBuilder("select FAIL_MODEL_ID,DEALER_CODE from tm_Fail_MODEL where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and FAIL_MODEL = ?");
		params.add(failModelCode);
		List<Map> list = DAOUtil.findAll(sqlSb.toString(),params);	
		if(CommonUtils.isNullOrEmpty(list)){
			FailModels failModels=new FailModels();
			setFailModels(failModels,failModelDto);
			failModels.saveIt();
			return failModels.getLongId();
		}else{
			throw new ServiceBizException("战败车型代码不能重复");
		}

	}

	/**
	 * 
	 * @author yll
	 * @date 2016年6月30日
	 * @param id
	 * @param failModelDto
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.basedata.FailModelsService#modifyFailModel(java.lang.Long, com.yonyou.dms.retail.domains.DTO.basedata.FailModelsDto)
	 */
	@Override
	public void modifyFailModel(Long id, FailModelsDto failModelDto) throws ServiceBizException {

		FailModels failModel = FailModels.findById(id);
		setFailModels(failModel,failModelDto);
		failModel.saveIt();

	}

	/**
	 * 
	 * @author yll
	 * @date 2016年6月30日
	 * @param id
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.basedata.FailModelsService#deleteFailModelById(java.lang.Long)
	 */
	@Override
	public void deleteFailModelById(Long id) throws ServiceBizException {
		FailModels failModel = FailModels.findById(id);
		failModel.delete();

	}



	/**
	 * 根据id查找战败车型信息
	 * @author yll
	 * @date 2016年6月30日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.retail.service.basedata.FailModelsService#getFailModelById(java.lang.Long)
	 */
	@Override
	public FailModels getFailModelById(Long id) throws ServiceBizException {
		return FailModels.findById(id);
	}
	/**
	 * 
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param failModel
	 * @param failModelDto
	 */
	private void setFailModels(FailModels failModel,FailModelsDto failModelDto){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		failModel.setString("dealer_code",loginInfo.getDealerCode());
		failModel.setString("intent_brand",failModelDto.getIntentBrand() );
		failModel.setString("intent_series",failModelDto.getIntentSeries());
		failModel.setString("fail_model",failModelDto.getFailModel());
		failModel.setString("fail_model_desc",failModelDto.getFailModelDesc());
		failModel.setInteger("oem_tag", DictCodeConstants.STATUS_IS_NOT);
		failModel.setInteger("is_valid", failModelDto.getIsValid());
	}
}
