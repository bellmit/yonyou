
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : ModelServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年7月6日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月6日    yll    1.0
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

import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TmSeriesPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.ModelDto;

/**
 * 车型接口的实现类
 * @author yll
 * @date 2016年7月6日
 */
@Service
@SuppressWarnings("rawtypes")
public class ModelServiceImpl implements ModelService{
	/**
	 * 车型信息列表
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#queryModel(java.util.Map)
	 */
	@Override
	public PageInfoDto queryModel(Map<String, String> queryParam) throws ServiceBizException {
		/*StringBuilder sqlSb = new StringBuilder("SELECT  t1.BRAND_ID, t1.BRAND_NAME, t2.SERIES_ID, t2.SERIES_NAME, t3.MODEL_CODE, t3.MODEL_NAME, t3.MODEL_GROUP_NAME, t3.IS_VALID, t3.OEM_TAG, tmg.MODEL_LABOUR_NAME, t3.DEALER_CODE, t3.MODEL_ID FROM tm_model t3");
		sqlSb.append( " LEFT JOIN tm_model_group_item tmgi ON tmgi.MODEL_CODE = t3.MODEL_CODE INNER JOIN tm_series t2 on  t2.SERIES_ID = t3.SERIES_ID INNER JOIN tm_brand t1 on  t1.BRAND_ID = t2.BRAND_ID LEFT JOIN tm_model_group tmg on  tmg.MODEL_GROUP_ID = tmgi.MODEL_GROUP_ID WHERE 1 = 1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and t1.IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and t2.IS_VALID= ?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandName"))){
			sqlSb.append(" and t1.BRAND_ID = ?");
			params.add(Long.parseLong(queryParam.get("brandName")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sqlSb.append(" and t2.SERIES_ID = ?");
			params.add(Long.parseLong(queryParam.get("seriesName")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
			sqlSb.append(" and t3.MODEL_CODE like ?");
			params.add("%"+queryParam.get("modelCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelName"))){
			sqlSb.append(" and t3.MODEL_NAME = ?");
			params.add(queryParam.get("modelName"));
		}

		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and t3.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and t3.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}*/

		//执行查询操作
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 根据id查找车型
	 * @author yll
	 * @date 2016年7月6日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#getModelById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> getModelById(String mcode,String scode,String bcode) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT  t1.BRAND_CODE, t1.BRAND_NAME, t2.SERIES_CODE, t2.SERIES_NAME, t3.MODEL_CODE, t3.MODEL_NAME,t3.LABOUR_PRICE,t3.LOCAL_MODEL_CODE, t3.MODEL_GROUP_NAME, t3.IS_VALID, t3.OEM_TAG,t3.MODEL_LABOUR_CODE, tmgi.MODEL_LABOUR_NAME, t3.DEALER_CODE, t2.is_valid as IVS FROM tm_model t3");
		sqlSb.append( " LEFT JOIN tm_model_group tmgi ON tmgi.model_LABOUR_CODE = t3.model_LABOUR_CODE AND tmgi.DEALER_CODE = t3.DEALER_CODE INNER JOIN tm_series t2 ON  t2.SERIES_CODE = t3.SERIES_CODE  AND t2.BRAND_CODE = t3.BRAND_CODE  INNER JOIN tm_brand t1 ON  t1.BRAND_CODE = t2.BRAND_CODE WHERE 1 = 1");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and t1.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and t2.IS_VALID= ?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and t1.BRAND_CODE= ? ");
		sqlSb.append(" and t2.SERIES_CODE= ? ");
		sqlSb.append(" and t3.MODEL_CODE= ? ");
		params.add(bcode);
		params.add(scode);
		params.add(mcode);
		Map map = DAOUtil.findFirst(sqlSb.toString(), params);

		return map;
	}

	/**
	 * 添加车型信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param modelDto
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#addModel(vehicleModule.ModelDto)
	 */
	@Override
	public Long addModel(ModelDto modelDto) throws ServiceBizException {
		String modelCode=modelDto.getModelCode();
		String modelName=modelDto.getModelName();
		if(StringUtils.isNullOrEmpty(modelCode)){
			throw new ServiceBizException("车型代码不能为空");
		}

		if(SearchModelCode(modelCode,modelName)){
			TmModelPO model=new TmModelPO();
			setModel(model,modelDto);
			model.saveIt();
			return  null;
		}else{
			throw new ServiceBizException("车型代码或名称不能重复");
		}

	}

	/**
	 * 修改车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param modelDto
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#modifyModel(java.lang.Long, vehicleModule.ModelDto)
	 */
	@Override
	public void modifyModel(String mcode,String scode,String bcode,ModelDto modelDto) throws ServiceBizException {
		String modelCode=modelDto.getModelCode();
		String modelName=modelDto.getModelName();
		if(SearchModelName(modelCode,modelName)){
			TmSeriesPo ts = TmSeriesPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),modelDto.getBrandId(),modelDto.getSeriesId());
			if(ts!=null){
				if(ts.getLong("IS_VALID")==DictCodeConstants.IS_NOT){
					throw new ServiceBizException("车型【"+modelCode+"】所属的车系为无效状态，请先将其车系改成有效！");
				}else{
					TmModelPO model=TmModelPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode,mcode);
					setModel(model,modelDto);
					model.saveIt();
				}
			}else{
				throw new ServiceBizException("车型【"+modelCode+"】所属的车系为无效状态，请先将其车系改成有效！");
			}
		}else{
			throw new ServiceBizException("车型名称不能重复");
		}

	}

	/**
	 * 删除车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#deleteModelById(java.lang.Long)
	 */
	@Override
	public void deleteModelById(String mcode,String scode,String bcode) throws ServiceBizException {
		TmModelPO model=TmModelPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode,mcode);
		model.delete();

	}
	/**
	 * 
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param model
	 * @param modelDto
	 */
	public void setModel(TmModelPO model,ModelDto modelDto){
	    model.setString("brand_code", modelDto.getBrandId());
		model.setString("series_code", modelDto.getSeriesId());
		model.setString("model_code", modelDto.getModelCode());
		model.setString("model_name", modelDto.getModelName());
		model.setString("local_model_code", modelDto.getLocalCode());
		model.setString("model_labour_code", modelDto.getModelLabourCode());
		//model.setString("model_group_name", modelDto.getModelGroupName());
//		model.setString("model_group_code", modelDto.getModelGroupCode());
		if( modelDto.getPrice()!=null){
			model.setDouble("labour_price", modelDto.getPrice());
		}
//		model.setInteger("oem_tag", DictCodeConstants.STATUS_IS_NOT);
		if (model.getLong("oem_tag")!=DictCodeConstants.IS_YES) {
			model.setInteger("is_valid", modelDto.getIsValid());
		}
	}
	/**
	 * 下拉框查询车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#queryModel2(java.util.Map)
	 */
	@Override
	public List<Map> queryModel2(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select tmm.DEALER_CODE as DEALER_CODE,tmc.SERIES_CODE,tmm.MODEL_CODE,tmm.MODEL_NAME   from tm_MODEL tmm,tm_series tmc where 1=1 and tmm.SERIES_CODE=tmc.SERIES_CODE ");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		//sqlSb.append(" and OEM_TAG= ?");
		//params.add(DictCodeConstants.STATUS_IS_NOT);
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriescode"))){
			sqlSb.append(" and tmc.SERIES_CODE = ?");
			params.add(queryParam.get("seriescode"));
		}
		  sqlSb.append(" and tmc.DEALERL_CODE = ?");
	        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
	        sqlSb.append(" and tmm.DEALERL_CODE = ?");
	        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		//执行查询操作
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	
	
	
	
    /**
    * @author LiGaoqi
    * @date 2017年4月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#queryModelS(java.util.Map)
    */
    	
    @Override
    public List<Map> queryModelS(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select * from TM_SC_MODEL A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
        sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and B.BIZ_CODE = 'UNIFIED_VIEW'");
        if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
            sql.append(" and A.MODEL_CODE like ?");
            params.add("%" + queryParam.get("code") + "%");
        }
        sql.append(") order by model_name "); 
        List<Map> map = DAOUtil.findAll(sql.toString(),params);
        return map;
    
    }

    /**
	 * 下拉框查询车型信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#queryModel2(java.util.Map)
	 */
	@Override
	public List<Map> queryModelOEM(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,SERIES_CODE,MODEL_CODE,MODEL_NAME   from tm_MODEL where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and OEM_TAG= ?");
		  sqlSb.append(" and DEALERL_CODE = ?");
	        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		params.add(DictCodeConstants.STATUS_IS_NOT);
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and SERIES_CODE = ?");
			params.add(Long.parseLong(queryParam.get("code")));
		}
		//执行查询操作
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}

	/**
	 * 
	 * @author yll
	 * @date 2016年7月26日
	 * @param modelCode
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#selectModelIdByCode(java.lang.String)
	 */
	@Override
	public String selectModelIdByCode(String modelCode) {
		StringBuilder sqlSb = new StringBuilder("select MODEL_CODE,DEALER_CODE from tm_model where 1=1");
		List<String> params = new ArrayList<String>();
		if(!StringUtils.isNullOrEmpty(modelCode)){
			sqlSb.append(" and MODEL_CODE = ?");
			params.add(modelCode);
		}
		sqlSb.append(" and DEALERL_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		Map pageInfoDto = DAOUtil.findFirst(sqlSb.toString(), params);
		String id= (String) pageInfoDto.get("MODEL_CODE");
		return  id;
	}

	/**
	 * 车型导出方法
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.ModelService#queryModelsForExport(java.util.Map)
	 */
	@Override
	public List<Map> queryModelsForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		List<Map> resultList = DAOUtil.findAll(sql,params);
		return resultList;
	}

	/**
	 * 
	 * 导出方法的sql语句
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
		StringBuilder sqlSb = new StringBuilder("SELECT  t1.BRAND_CODE, t1.BRAND_NAME, t2.SERIES_CODE, t2.SERIES_NAME, t3.MODEL_CODE, t3.MODEL_NAME,t3.LABOUR_PRICE,t3.LOCAL_MODEL_CODE, t3.MODEL_GROUP_NAME, t3.IS_VALID, t3.OEM_TAG,t3.MODEL_LABOUR_CODE, tmgi.MODEL_LABOUR_NAME, t3.DEALER_CODE, t2.is_valid as IVS  FROM tm_model t3");
		sqlSb.append( " LEFT JOIN tm_model_group tmgi ON tmgi.model_LABOUR_CODE = t3.model_LABOUR_CODE AND tmgi.DEALER_CODE = t3.DEALER_CODE INNER JOIN tm_series t2 on  t2.SERIES_CODE = t3.SERIES_CODE INNER JOIN tm_brand t1 on  t1.BRAND_CODE = t2.BRAND_CODE WHERE 1 = 1");
//		sqlSb.append(" and t1.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
//		sqlSb.append(" and t2.IS_VALID= ?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sqlSb.append(" and t1.BRAND_CODE = ?");
			params.add(queryParam.get("brandId"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sqlSb.append(" and t2.SERIES_CODE = ?");
			params.add(queryParam.get("seriesId"));
		}

		if(!StringUtils.isNullOrEmpty(queryParam.get("modelName"))){
			sqlSb.append(" and t3.MODEL_CODE like ?");
			params.add("%"+queryParam.get("modelName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))){
			sqlSb.append(" and t3.MODEL_CODE like ?");
			params.add("%"+queryParam.get("modelCode")+"%");
		}

		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and t3.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and t3.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}


		return sqlSb.toString();
	}

	@Override
	public List<Map> queryModelC(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("select tmm.DEALER_CODE as DEALER_CODE,tmc.SERIES_CODE,tmm.MODEL_CODE,tmm.MODEL_NAME   from tm_MODEL tmm,tm_series tmc where 1=1 and tmc.IS_VALID=12781001 and tmm.SERIES_CODE=tmc.SERIES_CODE");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and tmm.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and tmc.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlSb.append(" and tmm.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and tmc.SERIES_CODE = ?");
			params.add(queryParam.get("code"));
		}
		//执行查询操作
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;
	}
	
	
	//二手车车型
	   @Override
	    public List<Map> queryModelCSr(Map<String, String> queryParam) throws ServiceBizException {
	       StringBuffer sql = new StringBuffer();
	        List<Object> params = new ArrayList<Object>();
           sql.append("select * from TM_SC_MODEL A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from VM_ENTITY_SHARE_WITH B ");
           sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and series_code=? AND B.BIZ_CODE = 'UNIFIED_VIEW') order by model_name ");
	        //执行查询操作
           if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
               params.add(queryParam.get("code"));
           }
	        List<Map> result = DAOUtil.findAll(sql.toString(),params);
	        return result;
	    }
	/**
	 * 
	 * 判断是否存在已有的车型代码和名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchModelCode(String modelCode,String modelName) {
		StringBuilder sqlSb = new StringBuilder("select MODEL_CODE,DEALER_CODE from tm_model where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and MODEL_CODE = ?");
		params.add(modelCode);
		sqlSb.append(" or MODEL_NAME = ?");
		params.add(modelName);
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);		
		if(map.size()==0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 判断是否存在已有的车型名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchModelName(String modelCode,String modelName) {
		StringBuilder sqlSb = new StringBuilder("select MODEL_CODE,DEALER_CODE from tm_model where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and MODEL_NAME = ?");
		params.add(modelName);
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);		
		if(map.size()==0){
			return true;
		}else{
			String modelCodesql=(String) map.get(0).get("MODEL_CODE");
			if(modelCodesql.equals(modelCode)){
				return true;	
			}
		}
		return false;
	}

    @Override
    public List<Map> queryLabour(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,MODEL_LABOUR_CODE,MODEL_LABOUR_NAME,OEM_TAG FROM tm_model_group WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
//        sqlSb.append(" and IS_VALID=?");
//        params.add(DictCodeConstants.STATUS_IS_YES);
        //sqlSb.append(" and OEM_TAG= ?");
        //params.add(DictCodeConstants.STATUS_IS_NOT);
        //执行查询操作
        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
        return result;
    }

    @Override
    public List<Map> queryPrice(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE,OEM_TAG FROM TM_LABOUR_PRICE WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
//        sqlSb.append(" and IS_VALID=?");
//        params.add(DictCodeConstants.STATUS_IS_YES);
        //sqlSb.append(" and OEM_TAG= ?");
        //params.add(DictCodeConstants.STATUS_IS_NOT);
        //执行查询操作
        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
        return result;
    }
    
	@Override
	public List<Map> selectModel(Map<String, String> queryParam) throws ServiceBizException {
		   StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,MODEL_CODE,MODEL_NAME,OEM_TAG FROM TM_MODEL WHERE 1=1 ");
	        List<Object> params = new ArrayList<Object>();
	        sqlSb.append(" and IS_VALID=?");
	        params.add(DictCodeConstants.STATUS_IS_YES);
	        //sqlSb.append(" and OEM_TAG= ?");
	        //params.add(DictCodeConstants.STATUS_IS_NOT);
	        //执行查询操作
	        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
	        return result;
	}
	@Override
    public List<Map> selectModelSr(Map<String, String> queryParam) throws ServiceBizException {
	    StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
       sql.append("select * from TM_SC_MODEL A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
       sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and  B.BIZ_CODE = 'UNIFIED_VIEW') order by model_name ");
            List<Map> result = DAOUtil.findAll(sql.toString(),params);
            return result;
    }
}
