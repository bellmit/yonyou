
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.manage
 *
 * @File name : BrandServiceImpl.java
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

import com.yonyou.dms.common.domains.PO.basedata.TmBrandPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.vehicleModule.BrandDto;

/**
 * 品牌接口的实现类
 * @author yll
 * @date 2016年7月6日
 */
@Service
@SuppressWarnings("rawtypes")
public class BrandServiceImpl  implements BrandService{

	/**
	 * 品牌显示列表
	 * @author yll
	 * @date 2016年7月6日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrand(java.util.Map)
	 */
	@Override
	public PageInfoDto queryBrand(Map<String, String> queryParam) throws ServiceBizException {
		/*StringBuffer sqlSb = new StringBuffer("select BRAND_ID,DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG,IS_VALID,RECORD_VERSION,CREATED_BY,CREATED_AT,UPDATED_BY,UPDATED_AT  from tm_brand where 1=1");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sqlSb.append(" and BRAND_CODE like ?");
			params.add("%"+queryParam.get("brandCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}*/
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	
	   /**
     * 品牌显示列表
     * @author yll
     * @date 2016年7月6日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrand(java.util.Map)
     */
	@Override
    public List<Map> queryBrandSr(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> params = new ArrayList<Object>();
        sql.append("select * from TM_SC_BRAND A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
        sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and B.BIZ_CODE = 'UNIFIED_VIEW') order by brand_name  ");
        List<Map> map = DAOUtil.findAll(sql.toString(),params);
        return map;
    }

	/**
	 * 根据id查找品牌
	 * @author yll
	 * @date 2016年7月6日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#getBrandById(java.lang.Long)
	 */
	@Override
	public TmBrandPo getBrandById(String id) throws ServiceBizException {

		return TmBrandPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
	}

	/**
	 * 新增品牌信息
	 * @author yll
	 * @date 2016年7月6日
	 * @param brand
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#addBrand(vehicleModule.BrandDto)
	 */
	@Override
	public String addBrand(BrandDto brandDto) throws ServiceBizException {
		String brandCode=brandDto.getBrandCode();
		String brandName=brandDto.getBrandName();
		if(StringUtils.isNullOrEmpty(brandCode)){
			throw new ServiceBizException("品牌代码不能为空");
		}

		if(SearchBrandCode(brandCode,brandName)){
			TmBrandPo brand=new TmBrandPo();
			setBrand(brand,brandDto);
			brand.saveIt();
			return  brand.getIdName();
		}else{
			throw new ServiceBizException("品牌代码或名称不能重复");
		}

	}

	/**
	 * 修改品牌信息
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @param brandDto
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#modifyBrand(java.lang.Long, vehicleModule.BrandDto)
	 */
	@Override
	public void modifyBrand(String id, BrandDto brandDto) throws ServiceBizException {

		String brandCode=brandDto.getBrandCode();
		String brandName=brandDto.getBrandName();
		if(SearchBrandName(brandCode,brandName)){
			TmBrandPo brand=TmBrandPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
			setBrand(brand,brandDto);
			brand.saveIt();
		}else{
			throw new ServiceBizException("品牌名称不能重复");
		}
	}

	/**
	 * 删除品牌信息方法
	 * @author yll
	 * @date 2016年7月8日
	 * @param id
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#deleteBrandById(java.lang.Long)
	 */
	@Override
	public void deleteBrandById(String id) throws ServiceBizException {
		TmBrandPo brand=TmBrandPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		brand.delete();

	}
	/**
	 * 
	 * 
	 * @author yll
	 * @date 2016年7月8日
	 * @param brand
	 * @param brandDto
	 */
	private void setBrand(TmBrandPo brand,BrandDto brandDto){

		brand.setString("brand_code",brandDto.getBrandCode());
		brand.setString("brand_name",brandDto.getBrandName());
		brand.setInteger("oem_tag", DictCodeConstants.STATUS_IS_NOT);
		brand.setInteger("is_valid", brandDto.getIsValid());

	}

	/**
	 * 下拉框显示品牌的方法
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrand2(java.util.Map)
	 */
	public List<Map> queryBrand2(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG  from tm_brand where 1=1 ");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and IS_VALID=?");
		params.add(DictCodeConstants.IS_YES);
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
	//	sqlSb.append(" and OEM_TAG= ?");
	//	params.add(DictCodeConstants.STATUS_IS_NOT);
		//执行查询操作
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);

		return result;
	}
	
	/**
     * 下拉框显示品牌的方法
     * @author yll
     * @date 2016年7月8日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrand2(java.util.Map)
     */
    public List<Map> queryBrand3(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG  from tm_brand where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and IS_VALID=?");
        params.add(DictCodeConstants.IS_YES);
        sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        //执行查询操作
        List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);

        return result;
    }
	
	/**
	 * 下拉框显示品牌的方法
	 * @author yll
	 * @date 2016年7月8日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrand2(java.util.Map)
	 */
	public List<Map> queryBrandOEM(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG  from tm_brand where 1=1 ");
		List<Object> params = new ArrayList<Object>();

		sqlSb.append(" and IS_VALID=?");
		params.add(DictCodeConstants.STATUS_IS_YES);
		sqlSb.append(" and OEM_TAG= ?");
		params.add(DictCodeConstants.STATUS_IS_NOT);
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		//执行查询操作
		List<Map> result = DAOUtil.findAll(sqlSb.toString(),params);

		return result;
	}

	/**
	 * 
	 * @author yll
	 * @date 2016年7月26日
	 * @param brandCode
	 * @return
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#selectBrandIdByCode(java.lang.String)
	 */
	@Override
	public Integer selectBrandIdByCode(String brandCode) {
		StringBuffer sqlSb = new StringBuffer("select BRAND_ID,DEALER_CODE from tm_brand where 1=1");
		List<String> params = new ArrayList<String>();
		if(!StringUtils.isNullOrEmpty(brandCode)){
			sqlSb.append(" and BRAND_CODE = ?");
			params.add(brandCode);
		}
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		Map pageInfoDto = DAOUtil.findFirst(sqlSb.toString(), params);
		Integer id=(Integer) pageInfoDto.get("BRAND_ID");
		return  id;

	}


	/**
	 * 品牌导出方法
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * (non-Javadoc)
	 * @see com.yonyou.dms.manage.service.basedata.vehicleModule.BrandService#queryBrandsForExport(java.util.Map)
	 */
	@Override
	public List<Map> queryBrandsForExport(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params);
		List<Map> resultList = DAOUtil.findAll(sql,params);
		return resultList;
	}
	/**
	 * 
	 * 获取导出用的sql语句
	 * @author yll
	 * @date 2016年7月26日
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
		StringBuffer sqlSb = new StringBuffer("SELECT DEALER_CODE,BRAND_CODE,BRAND_NAME,OEM_TAG,IS_VALID,VER,CREATED_BY,CREATED_AT,UPDATED_BY,UPDATED_AT  FROM tm_brand WHERE 1=1");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sqlSb.append(" and BRAND_CODE like ?");
			params.add("%"+queryParam.get("brandCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("OemTag"))){
			sqlSb.append(" and OEM_TAG = ?");
			params.add(Integer.parseInt(queryParam.get("OemTag")));
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))){
			sqlSb.append(" and IS_VALID = ?");
			params.add(Integer.parseInt(queryParam.get("IsValid")));
		}
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		return sqlSb.toString();
	}
	
	@SuppressWarnings("unused")
	private String getQuerySqlSrt(Map<String,String> queryParam,List<Object> params){
	    StringBuffer sql = new StringBuffer();
	    sql.append("select * from TM_SC_BRAND A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
        sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and B.BIZ_CODE = 'UNIFIED_VIEW') order by brand_name  ");
         System.out.println(sql);
        return sql.toString();
    }
	/**
	 * 
	 * 判断是否存在已有的品牌代码和名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchBrandCode(String brandCode,String brandName) {
		StringBuffer sqlSb = new StringBuffer("select BRAND_CODE,DEALER_CODE from tm_brand where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and BRAND_CODE = ?");
		params.add(brandCode);
		sqlSb.append(" or BRAND_NAME = ?");
		params.add(brandName);
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);
		if(map.size()==0){
			return true;
		}
		return false;
	}
	/**
	 * 
	 * 判断是否存在已有的品牌名称
	 * @author yll
	 * @date 2016年10月19日
	 * @param repairTypeName
	 * @return
	 */
	private boolean SearchBrandName(String brangCode,String brandName) {
		StringBuffer sqlSb = new StringBuffer("select BRAND_CODE,DEALER_CODE from tm_brand where 1=1");
		List<Object> params = new ArrayList<Object>();
		sqlSb.append(" and BRAND_NAME = ?");
		params.add(brandName);
		sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);
		if(map.size()==0){
			return true;
		}else{
			String brandCodesql=(String) map.get(0).get("BRAND_CODE");
			if(brandCodesql.equals(brangCode)){
				return true;	
			}
		}
		return false;
	}
    
	/**
	 * 
	 * 获取车系下拉选择
	 * @author Benzc
	 * @date 2016年12月28日
	 * @param repairTypeName
	 * @return
	 */
	@Override
	public List<Map> querySeries(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,SERIES_CODE,SERIES_NAME,OEM_TAG  from tm_series where 1=1 ");
		List<Object> params = new ArrayList<Object>();
	    sqlSb.append(" and IS_VALID=?");
	    params.add(DictCodeConstants.IS_YES);
	    sqlSb.append(" and DEALER_CODE= ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> map = DAOUtil.findAll(sqlSb.toString(),params);
		return map;
	}
	
	
	@Override
    public List<Map> querySeriesSr(Map<String, String> queryParam) throws ServiceBizException {
	     StringBuffer sql = new StringBuffer();
	     List<Object> params = new ArrayList<Object>();
	     sql.append("select * from TM_SC_series A where A.IS_VALID=12781001 AND A.dealer_code in (select B.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") B ");
	     sql.append("where B.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and B.BIZ_CODE = 'UNIFIED_VIEW') order by SERIES_name  ");
        List<Map> map = DAOUtil.findAll(sql.toString(),params);
        return map;
    }
	
	
	


}
