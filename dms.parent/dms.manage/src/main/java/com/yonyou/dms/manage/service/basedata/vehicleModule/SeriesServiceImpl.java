
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : SeriesServiceImpl.java
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

import com.yonyou.dms.common.domains.PO.basedata.TmSeriesPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.SeriesDto;

/**
 * 车系接口的实现类
 * @author yll
 * @date 2016年7月6日
 */
@Service
@SuppressWarnings("rawtypes")
public class SeriesServiceImpl implements SeriesService{

	/**
	 * 查询车系信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#querySeries(java.util.Map)
	 */
	@Override
	public PageInfoDto querySeries(Map<String, String> queryParam) throws ServiceBizException {
		/*StringBuffer sqlSb = new StringBuffer("SELECT tms.SERIES_ID,tms.BRAND_ID,tms.DEALER_CODE,tms.SERIES_CODE,tms.SERIES_NAME,tms.OEM_TAG,tms.IS_VALID,tms.RECORD_VERSION,tms.CREATED_BY,tms.CREATED_AT,tms.UPDATED_BY,tms.UPDATED_AT,tmb.BRAND_NAME FROM tm_series tms left join tm_brand  tmb on tms.BRAND_ID=tmb.BRAND_ID  WHERE 1 = 1 ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and tmb.IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sqlSb.append(" and tms.BRAND_ID = ?");
			params.add(Long.parseLong(queryParam.get("brandId")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sqlSb.append(" and tms.SERIES_ID = ?");
			params.add(Long.parseLong(queryParam.get("seriesId")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and tms.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and tms.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}*/

		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 根据id查找车系
	 * @author yll
	 * @date 2016年7月6日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#getSeriesById(java.lang.Long)
	 */
	@Override
	public TmSeriesPo getSeriesById(String scode,String bcode) throws ServiceBizException {

		return TmSeriesPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode);
	}

	/**
	 * 车系添加
	 * @author yll
	 * @date 2016年7月6日
	 * @param seriesDto
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#addSeries(vehicleModule.SeriesDto)
	 */
	@Override
	public String addSeries(SeriesDto seriesDto) throws ServiceBizException {
		String seriesCode=seriesDto.getSeriesCode();
		String seriesName=seriesDto.getSeriesName();
		if(StringUtils.isNullOrEmpty(seriesDto.getBrandId())){
			throw new ServiceBizException("品牌代码不能为空");
		}//??
		if(StringUtils.isNullOrEmpty(seriesCode)){
			throw new ServiceBizException("车系代码不能为空");
		}
		if(SearchSeriesCode(seriesCode,seriesName)){
			TmSeriesPo series=new TmSeriesPo();
			setSeries(series,seriesDto);
			series.saveIt();
			return  series.getIdName();
		}else{
			throw new ServiceBizException("车系代码或名称不能重复");
		}


	}

	/**
	 * 修改车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param seriesDto
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#modifySeries(java.lang.Long, vehicleModule.SeriesDto)
	 */
	@Override
	public void modifySeries(String scode,String bcode, SeriesDto seriesDto) throws ServiceBizException {
		String seriesCode=seriesDto.getSeriesCode();
		String seriesName=seriesDto.getSeriesName();
		if(SearchSeriesName(seriesCode,seriesName)){
			TmSeriesPo series=TmSeriesPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode);
			setSeries(series,seriesDto);
			series.saveIt();
		}else{
			throw new ServiceBizException("车系名称不能重复");
		}

	}

	/**
	 * 根据id删除车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#deleteSeriesById(java.lang.Long)
	 */
	@Override
	public void deleteSeriesById(String scode,String bcode) throws ServiceBizException {
		TmSeriesPo series=TmSeriesPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bcode,scode);
		series.delete();

	}
	/**
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param series
	 * @param seriesDto
	 */
	public void setSeries(TmSeriesPo series,SeriesDto seriesDto){

		series.setString("brand_CODE", seriesDto.getBrandId());
		series.setString("series_code", seriesDto.getSeriesCode());
		series.setString("series_name", seriesDto.getSeriesName());
		series.setInteger("oem_tag", 12781002);
		series.setInteger("is_valid", seriesDto.getIsValid());

	}
	/**
	 * 下拉框显示车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#querySeries2(java.util.Map)
	 */
	@Override
	public List<Map> querySeries2(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select tb.BRAND_CODE,ts.DEALER_CODE as DEALER_CODE,ts.SERIES_CODE,ts.SERIES_NAME   from tm_series ts,tm_brand tb where 1=1 and tb.BRAND_CODE=ts.BRAND_CODE ");
		List<Object> params = new ArrayList<Object>();
//		sqlSb.append(" and IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		//sqlSb.append(" and OEM_TAG= ?");
		//params.add(DictCodeConstants.STATUS_IS_NOT);
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and tb.BRAND_CODE = ?");
			params.add(queryParam.get("code"));
		}

		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;

	}
	
	/**
	 * 下拉框显示车系信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#querySeries2(java.util.Map)
	 */
	@Override
	public List<Map> querySeriesOEM(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select BRAND_CODE,DEALER_CODE,SERIES_CODE,SERIES_NAME from tm_series where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and OEM_TAG= ?");
		params.add(DictCodeConstants.STATUS_IS_NOT);
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and BRAND_CODE = ?");
			params.add(Long.parseLong(queryParam.get("code")));
		}

		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);
		return result;

	}

	/**
	 * 
	 * @author yll
	 * @date 2016年7月26日
	 * @param seriesCode
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#selectSeriesIdByCode(java.lang.String)
	 */
	@Override
	public String selectSeriesIdByCode(String seriesCode) {
		StringBuffer sqlSb = new StringBuffer("select SERIES_CODE,DEALER_CODE from tm_series where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(seriesCode)){
			sqlSb.append(" and SERIES_CODE = ?");
			params.add(seriesCode);
		}
		Map pageInfoDto = DAOUtil.findFirst(sqlSb.toString(), params);
		String id=(String) pageInfoDto.get("SERIES_CODE");
		return  id;
	}

	/**
	 * 车系导出功能
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#querySeriessForExport(java.util.Map)
	 */
	@Override
	public List<Map> querySeriessForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		List<Map> resultList = DAOUtil.findAll(sql,params);
		return resultList;
	}
	/**
	 * 
	 * 获取导出用的SQL语句
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
		StringBuffer sqlSb = new StringBuffer("SELECT tms.BRAND_CODE,tms.DEALER_CODE,tms.SERIES_CODE,tms.SERIES_NAME,tms.OEM_TAG,tms.IS_VALID,tms.VER,tms.CREATED_BY,tms.CREATED_AT,tms.UPDATED_BY,tms.UPDATED_AT,tmb.BRAND_NAME,tmb.is_valid as IVB FROM tm_series tms LEFT JOIN tm_brand  tmb ON tms.BRAND_CODE=tmb.BRAND_CODE WHERE 1 = 1 ");
//		sqlSb.append(" and tmb.IS_VALID=?");
//		params.add(DictCodeConstants.STATUS_IS_YES);
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandId"))){
			sqlSb.append(" and tms.BRAND_CODE = ?");
			params.add(queryParam.get("brandId"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesId"))){
			sqlSb.append(" and tms.SERIES_CODE = ?");
			params.add(queryParam.get("seriesId"));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and tms.OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and tms.IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}
  
		return sqlSb.toString();
	}

	@Override
	public List<Map> querySeriesC(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select tb.BRAND_CODE,ts.DEALER_CODE as DEALER_CODE,ts.SERIES_CODE,ts.SERIES_NAME   from tm_series ts,tm_brand tb where 1=1 and tb.BRAND_CODE=ts.BRAND_CODE");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and ts.IS_VALID=?");
		params.add(DictCodeConstants.IS_YES);
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and tb.BRAND_CODE = ?");
			params.add(queryParam.get("code"));
		}

	      sqlSb.append(" and ts.DEALER_CODE = ?");
	      params.add(FrameworkUtil.getLoginInfo().getDealerCode());
	      sqlSb.append(" and tb.DEALER_CODE = ?");
          params.add(FrameworkUtil.getLoginInfo().getDealerCode());

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
    * @see com.yonyou.dms.manage.service.basedata.vehicleModule.SeriesService#querySeriesS(java.util.Map)
    */
    	
    @Override
    public List<Map> querySeriesS(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select * from TM_SC_SERIES A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
        sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and B.BIZ_CODE = 'UNIFIED_VIEW'");
        if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
            sql.append(" and A.BRAND_CODE = ?");
            params.add(queryParam.get("code"));
        }
        sql.append(") order by series_name "); 
        List<Map> map = DAOUtil.findAll(sql.toString(),params);
        return map;
    }

    @Override
    public List<Map> querySeriesCSr(Map<String, String> queryParam) throws ServiceBizException {
	    StringBuffer sql = new StringBuffer();
	    List<Object> params = new ArrayList<Object>();
        sql.append("select * from TM_SC_series A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from VM_ENTITY_SHARE_WITH B ");
        sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and brand_code=? and B.BIZ_CODE = 'UNIFIED_VIEW') order by SERIES_name  ");
        if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
            params.add(queryParam.get("code"));
        }

        List<Map> result = DAOUtil.findAll(sql.toString(),params);
        return result;

    }
	/**
	 * 
	 * 判断是否存在已有的车系代码和名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchSeriesCode(String seriesCode,String seriesName) {
		StringBuffer sqlSb = new StringBuffer("select SERIES_CODE,DEALER_CODE from tm_series where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and SERIES_CODE = ?");
		params.add(seriesCode);
		sqlSb.append(" or SERIES_NAME = ?");
		params.add(seriesName);
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);	
		if(map.size()==0){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 判断是否存在已有的车系名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchSeriesName(String seriesCode,String seriesName) {
		StringBuffer sqlSb = new StringBuffer("select SERIES_CODE,DEALER_CODE from tm_series where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and SERIES_NAME = ?");
		params.add(seriesName);
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);	
		if(map.size()==0){
			return true;
		}else{
			String seriesCodesql=(String) map.get(0).get("SERIES_CODE");
			if(seriesCodesql.equals(seriesCode)){
				return true;	
			}
		}
		return false;
	}
	
	
	
	
}
