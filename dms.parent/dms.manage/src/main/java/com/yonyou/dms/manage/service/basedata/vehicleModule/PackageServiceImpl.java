
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : ConfigurationServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月7日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月7日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.manage.service.basedata.vehicleModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.ConfigurationPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.PackageDto;

/**
 * 配件接口实现类
 * 
 * @author yll
 * @date 2016年7月7日
 */
@Service
@SuppressWarnings("rawtypes")
public class PackageServiceImpl implements PackageService {

	/**
	 * 配置信息查询方法
	 * 
	 * @author yll
	 * @date 2016年7月7日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#queryConfiguration(java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PageInfoDto queryConfiguration(Map<String, String> queryParam) throws ServiceBizException {
		/*StringBuilder sqlSb = new StringBuilder("select t1.BRAND_ID,t1.BRAND_NAME,t2.SERIES_NAME,t2.SERIES_ID,t3.MODEL_ID,t3.MODEL_CODE,t3.MODEL_NAME,t4.CONFIG_CODE,t4.CONFIG_NAME,t4.OEM_TAG,t4.IS_VALID,t4.PACKAGE_ID,t4.DEALER_CODE from tm_brand t1,tm_series t2,tm_model t3,tm_package t4 ");
		sqlSb.append( "where 1=1 and t1.BRAND_ID = t2.BRAND_ID and t2.SERIES_ID = t3.SERIES_ID and t3.MODEL_ID = t4.MODEL_ID  ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and t1.IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and t2.IS_VALID= ?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and t3.IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);

		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sqlSb.append(" and t1.BRAND_ID = ?");
			params.add(Long.parseLong(queryParam.get("brandId")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			sqlSb.append(" and t2.SERIES_ID = ?");
			params.add(Long.parseLong(queryParam.get("seriesId")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("modelId"))) {
			sqlSb.append(" and t3.MODEL_ID = ?");
			params.add(Long.parseLong(queryParam.get("modelId")));
		}


		if(!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
			sqlSb.append(" and t3.MODEL_CODE like ?");
			params.add("%"+queryParam.get("modelCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("configCode"))){
			sqlSb.append(" and t4.CONFIG_CODE like ?");
			params.add("%"+queryParam.get("configCode")+"%");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("packageId"))) {
			sqlSb.append(" and t4.PACKAGE_ID = ?");
			params.add(Integer.parseInt(queryParam.get("packageId")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))) {
			sqlSb.append(" and t4.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))) {
			sqlSb.append(" and t4.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}*/
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql, params);
		List<Map> rows = pageInfoDto.getRows();
		for (Map map : rows) {
			if(!StringUtils.isNullOrEmpty(map.get("config_code2"))){
				String str = map.get("config_code2").toString();
				String all = str.replace(".", "**");
				map.put("config_code2",all);
			}
		}
		return pageInfoDto;

	}

	/**
	 * 
	 * 根据id查找配置信息
	 * 
	 * @author yll
	 * @date 2016年7月7日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#getConfigurationById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getConfigurationById(String id) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT tc.dealer_code,tb.brand_code,tb.brand_name,ts.series_code,ts.series_name,tm.model_code,tm.model_name,tc.config_code,tc.config_name,tc.oem_tag,tm.is_valid");
		sqlSb.append( " FROM TM_CONFIGURATION tc,tm_brand tb,tm_series ts,tm_model tm");
		sqlSb.append(" WHERE tc.model_code = tm.model_code AND tm.series_code = ts.series_code AND ts.brand_code = tb.brand_code");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and tb.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and ts.IS_VALID= ?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and tm.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and tc.config_code=?");
		params.add(id);
		Map map = DAOUtil.findFirst(sqlSb.toString(), params);

		return map;
	}

	/**
	 * 
	 * 添加配置信息
	 * 
	 * @author yll
	 * @date 2016年7月7日
	 * @param configurationDto
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#addConfiguration(vehicleModule.PackageDto)
	 */
	@Override
	public Long addConfiguration(PackageDto configurationDto) throws ServiceBizException {

		String configCode=configurationDto.getConfigCode();
		String configName=configurationDto.getConfigName();
		if (StringUtils.isNullOrEmpty(configCode)) {
			throw new ServiceBizException("配置代码不能为空");
		}
			
		if(SearchPackageCode(configCode,configName)){
			ConfigurationPO configuration = new ConfigurationPO();
			setConfiguration(configuration, configurationDto);
			configuration.saveIt();
			return  null;
		}else{
			throw new ServiceBizException("配置代码或名称不能重复");
		}
		
	}

	/**
	 * 修改配置信息
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param configurationDto
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#modifyConfiguration(java.lang.Long,
	 *      vehicleModule.PackageDto)
	 */
	@Override
	public void modifyConfiguration(String ccode, PackageDto configurationDto) throws ServiceBizException {
		String configCode=configurationDto.getConfigCode();
		String configName=configurationDto.getConfigName();	
		if(SearchPackageName(configCode,configName)){
			ConfigurationPO configuration = ConfigurationPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),configurationDto.getBrandId(),configurationDto.getSeriesId(),configurationDto.getModelId(),ccode);
			setConfiguration(configuration, configurationDto);
			configuration.saveIt();
		}else{
			throw new ServiceBizException("配置名称不能重复");
		}
		
	}

	/**
	 * 删除配置信息
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#deleteConfigurationById(java.lang.Long)
	 */
	@Override
	public void deleteConfigurationById(String bcode,String scode,String mcode,String ccode) throws ServiceBizException {
		ConfigurationPO configuration = ConfigurationPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode,mcode,ccode);
		configuration.delete();

	}

	/**
	 * 
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param configuration
	 * @param configurationDto
	 */
	public void setConfiguration(ConfigurationPO configuration, PackageDto configurationDto) {

		configuration.setString("MODEL_CODE", configurationDto.getModelId());
		configuration.setString("SERIES_CODE", configurationDto.getSeriesId());
		configuration.setString("BRAND_CODE", configurationDto.getBrandId());
		configuration.setString("CONFIG_CODE", configurationDto.getConfigCode());
		configuration.setString("CONFIG_NAME", configurationDto.getConfigName());
		configuration.setInteger("OEM_TAG", DictCodeConstants.STATUS_IS_NOT);

	}

	/**
	 * 下拉框显示配置信息
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#queryConfiguration2(java.util.Map)
	 */
	@Override
	public List<Map> queryConfiguration2(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select  tm.MODEL_CODE,tp.CONFIG_CODE,(case when tp.CONFIG_NAME is null then ' ' else CONFIG_NAME end) as CONFIG_NAME ,tp.DEALER_CODE as DEALER_CODE from tm_configuration tp,tm_model tm where 1=1 and tm.MODEL_CODE=tp.MODEL_CODE ");
		List<Object> params = new ArrayList<Object>();
	//	sqlSb.append(" and IS_VALID=?");
	//	params.add(DictCodeConstants.STATUS_IS_YES);
	//	sqlSb.append(" and OEM_TAG= ?");
		//params.add(DictCodeConstants.STATUS_IS_NOT);
		  sqlSb.append(" and tm.DEALER_CODE = ?");
	        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
	        sqlSb.append(" and tp.DEALER_CODE = ?");
	        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("code"))) {
			
			sqlSb.append(" and tm.MODEL_CODE = ?");
			params.add(queryParam.get("code"));
		}
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	

	
	/**
	 * 下拉框显示配置信息
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#queryConfiguration2(java.util.Map)
	 */
	@Override
	public List<Map> queryConfigurationOEM(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select MODEL_CODE,CONFIG_CODE,CONFIG_NAME,DEALER_CODE  from tm_configuration where 1=1 ");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and OEM_TAG= ?");
		params.add(DictCodeConstants.STATUS_IS_NOT);
		if (!StringUtils.isNullOrEmpty(queryParam.get("code"))) {
			sqlSb.append(" and MODEL_CODE = ?");
			params.add(queryParam.get("code"));
		}
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	/**
	 * 导出方法
	 * 
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.PackageService#queryConfisForExport(java.util.Map)
	 */
	@Override
	public List<Map> queryConfisForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> resultList = DAOUtil.findAll(sql, params);
		return resultList;
	}

	/**
	 * 
	 * 导出方法的sql语句
	 * 
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sqlSb = new StringBuilder("SELECT tc.dealer_code,tb.BRAND_CODE,tb.BRAND_NAME,ts.series_code,ts.series_name,tm.model_code,tm.model_name,tc.config_code,REPLACE(tc.config_code,'/','@@@') config_code2,tc.config_name,tc.oem_tag,tm.is_valid");
		sqlSb.append(" FROM TM_CONFIGURATION tc,tm_brand tb,tm_series ts,tm_model tm");
		sqlSb.append(" WHERE tc.model_code = tm.model_code AND tm.series_code = ts.series_code AND ts.brand_code = tb.brand_code");
//		sqlSb.append(" and tm.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and ts.IS_VALID= ?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and tb.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);

		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sqlSb.append(" and tb.BRAND_CODE = ?");
			params.add(queryParam.get("brandId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))) {
			sqlSb.append(" and ts.SERIES_CODE = ?");
			params.add(queryParam.get("seriesId"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("modelName"))) {
			sqlSb.append(" and tm.MODEL_CODE = ?");
			params.add(queryParam.get("modelName"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
			sqlSb.append(" and tm.MODEL_CODE like ?");
			params.add("%"+queryParam.get("modelCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("configCode"))){
			sqlSb.append(" and tc.CONFIG_CODE like ?");
			params.add("%"+queryParam.get("configCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("packageId"))){
            sqlSb.append(" and tc.CONFIG_CODE like ?");
            params.add("%"+queryParam.get("packageId")+"%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))) {
			sqlSb.append(" and tc.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))) {
            sqlSb.append(" and tm.IS_VALID = ?");
            params.add(Integer.parseInt(queryParam.get("IsValid")));
        }
		return sqlSb.toString();
	}

	@Override
	public List<Map> queryPackageC(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select tm.MODEL_CODE,tp.CONFIG_CODE,tp.CONFIG_NAME,tp.DEALER_CODE as DEALER_CODE from tm_configuration tp,tm_model tm where 1=1 and tm.MODEL_CODE=tp.MODEL_CODE");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and tm.IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		if (!StringUtils.isNullOrEmpty(queryParam.get("code"))) {
			sqlSb.append(" and tm.MODEL_CODE = ?");
			params.add(queryParam.get("code"));
		}
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	

	
	public List<Map> queryPackageSelectModel(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,MODEL_CODE,MODEL_NAME,OEM_TAG  from tm_model where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	/**
	 * 
	* 判断是否存在已有的配置代码和名称
	* @author yll
	* @date 2016年10月19日
	* @param repairTypeName
	* @return
	 */
	 private boolean SearchPackageCode(String configCode,String configName) {
		 StringBuilder sqlSb = new StringBuilder("select CONFIG_CODE,DEALER_CODE from tm_configuration where 1=1");
			List<Object> params = new ArrayList<Object>();
			sqlSb.append(" and CONFIG_CODE = ?");
			params.add(configCode);
			sqlSb.append(" or CONFIG_NAME = ?");
			params.add(configName);
			List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);	
	        if(map.size()==0){
	            return true;
	        }
	        return false;
	    }
	 /**
		 * 
		* 判断是否存在已有的配置名称
		* @author yll
		* @date 2016年10月19日
		* @param repairTypeName
		* @return
		 */
		 private boolean SearchPackageName(String configCode,String configName) {
			 StringBuilder sqlSb = new StringBuilder("select CONFIG_CODE,DEALER_CODE from tm_configuration where 1=1");
				List<Object> params = new ArrayList<Object>();
				sqlSb.append(" and CONFIG_NAME = ?");
				params.add(configName);
				List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);	
		        if(map.size()==0){
		            return true;
		        }else{
					String configCodesql=(String) map.get(0).get("CONFIG_CODE");
					if(configCodesql.equals(configCode)){
						return true;	
					}
				}
		        return false;
		    }


}
