
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.common
*
* @File name : RegionCacheSerivce.java
*
* @Author : zhangxc
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    zhangxc    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.service.cache.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.dms.framework.domains.DTO.baseData.OemRegionDto;
import com.yonyou.dms.framework.domains.PO.baseData.OemRegionPO;
import com.yonyou.dms.framework.service.cache.CacheService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 定义行政区划的缓存
 * 
 * @author zhangxc
 * @date 2016年8月22日
 */
@Component("OemRegionCache")
public class OemRegionCacheSerivceImpl<T> implements CacheService<OemRegionDto> {

    // 定义日志接口
    private static final Logger  logger     = LoggerFactory.getLogger(OemRegionCacheSerivceImpl.class);

    // 定义缓存对象--身份
    private Map<Long, OemRegionDto> cacheMap   = new HashMap<>();
    // 城市信息
    private Map<Long, OemRegionDto> cityMap    = new HashMap<>();
    // 区县
    private Map<Long, OemRegionDto> countryMap = new HashMap<>();

    /**
     * 执行初始化操作
     * 
     * @author zhangxc
     * @date 2016年8月22日 (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.cache.CacheService#init()
     */
    @Override
    public void init() {
        try {
            List<OemRegionPO> regionList = OemRegionPO.findAll().orderBy("REGION_TYPE ASC,REGION_ID asc");

            for (int i = 0; i < regionList.size(); i++) {
            	OemRegionPO regionPO = regionList.get(i);
                initOneValue(regionPO);
            }
            logger.info("TM_REGION 初始化成功");
        } catch (Exception e) {
            logger.error("系统初始化错误:" + e.getMessage(), e);
        } finally {

        }
    }

    /**
     * 获得某一key 值
     * 
     * @author zhangxc
     * @date 2016年8月22日
     * @param key
     * @return (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.cache.CacheService#getValueBykey(java.lang.String)
     */
    @Override
    public OemRegionDto getValueBykey(String key) {
    	OemRegionDto cacheValue = cacheMap.get(key);
        if (cacheValue == null) {
            // 重新加载这个key 值 的数据
            loadValueIfNotExists(key);
            return cacheMap.get(key);
        } else {
            return cacheValue;
        }
    }

    /**
     * load 某个key 的值
     * 
     * @author zhangxc
     * @date 2016年8月22日
     * @param key (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.cache.CacheService#loadValueIfNotExists(java.lang.String)
     */
    @Override
    public void loadValueIfNotExists(String key) {
        try {
            List<OemRegionPO> regionList = OemRegionPO.find("REGION_CODE = ? ", key).orderBy("REGION_TYPE ASC,REGION_ID asc");
            for (int i = 0; i < regionList.size(); i++) {
                // 加载数据
            	OemRegionPO regionPo = regionList.get(i);
                initOneValue(regionPo);
            }
        } catch (Exception e) {
            logger.error("TM_REGION 初始化错误:" + e.getMessage(), e);
        } finally {
        }
    }

    /**
     * 获得缓存的所有数据
     * 
     * @author zhangxc
     * @date 2016年8月22日
     * @return (non-Javadoc)
     * @see com.yonyou.dms.common.service.system.cache.CacheService#getAllData()
     */
    @Override
    public Map<Long, OemRegionDto> getAllData() {
        if (cacheMap.size() == 0) {
            synchronized (cacheMap) {
                if(cacheMap.size() == 0){
                    init();
                }else{
                    return cacheMap;
                }
            }
        }
        return cacheMap;
    }

    /**
     * 初始化其中一个Region
     * 
     * @author zhangxc
     * @date 2016年8月22日
     * @param dictPO
     */
    private void initOneValue(OemRegionPO regionPO) {

        Integer type = regionPO.getInteger("region_type");

        OemRegionDto regionDto = new OemRegionDto();
        regionDto.setRegion_id(regionPO.getLong("REGION_ID"));
        regionDto.setRegion_code(regionPO.getLong("REGION_CODE"));
        regionDto.setRegion_name(regionPO.getString("REGION_NAME"));
        regionDto.setRegion_type(regionPO.getInteger("REGION_TYPE"));

        Long parentId = regionPO.getLong("PARENT_ID");

        if (OemDictCodeConstants.REGION_TYPE_PROVINCE == type) {
            regionDto.setChildren(new HashMap<Long, OemRegionDto>());
            this.cacheMap.put(regionDto.getRegion_code(), regionDto);
        }

        if (OemDictCodeConstants.REGION_TYPE_CITY == type) {
        	OemRegionDto parentDto = null;
            parentDto = this.cacheMap.get(parentId);
            regionDto.setChildren(new HashMap<Long, OemRegionDto>());
            this.cityMap.put(regionDto.getRegion_code(), regionDto);
            parentDto.getChildren().put(regionDto.getRegion_code(), regionDto);

        }
        
        if (OemDictCodeConstants.REGION_TYPE_COUNTY == type) {
        	OemRegionDto parentDto = this.cityMap.get(parentId);
        	if(null!=parentDto){
        		parentDto.getChildren().put(regionDto.getRegion_code(), regionDto);
        		countryMap.put(regionDto.getRegion_code(), regionDto);
        	}
        }
    }

    /**
     * 根据名称，获得省份ID
     * 
     * @author zhangxc
     * @date 2016年8月26日
     * @param name
     * @return
     */
    public Long getProvinceIdByName(String name) {
        for (Map.Entry<Long, OemRegionDto> province : cacheMap.entrySet()) {
            if (name.trim().equals(province.getValue().getRegion_name())) {
                return province.getValue().getRegion_id();
            }
        }
        throw new UtilException("\"" + name + "\"在系统中未定义");
    }

    /**
     * 根据名称，获得城市ID
     * 
     * @author zhangxc
     * @date 2016年8月26日
     * @param name
     * @return
     */
    public Long getCityIdByName(Long provinceId, String name) {
        if (StringUtils.isNullOrEmpty(provinceId)) {
            return null;
        }
        OemRegionDto provinceRegion = cacheMap.get(provinceId.toString());
        Map<Long, OemRegionDto> cityMap = provinceRegion.getChildren();

        for (Map.Entry<Long, OemRegionDto> city : cityMap.entrySet()) {
            if (name.trim().equals(city.getValue().getRegion_name())) {
                return city.getValue().getRegion_id();
            }
        }
        throw new UtilException("\"" + name + "\"在对应省份\"" + provinceRegion.getRegion_name() + "\"中未定义");
    }

    /**
     * 根据名称，获得城市ID
     * 
     * @author zhangxc
     * @date 2016年8月26日
     * @param name
     * @return
     */
    public Long getCountryIdByName(Long provinceId, Long cityId, String name) {
        if (StringUtils.isNullOrEmpty(provinceId) || StringUtils.isNullOrEmpty(cityId)) {
            return null;
        }
        OemRegionDto provinceRegion = cacheMap.get(provinceId.toString());
        OemRegionDto citryRegion = provinceRegion.getChildren().get(cityId);
        Map<Long, OemRegionDto> countryMap = citryRegion.getChildren();

        for (Map.Entry<Long, OemRegionDto> country : countryMap.entrySet()) {
            if (name.trim().equals(country.getValue().getRegion_name())) {
                return country.getValue().getRegion_id();
            }
        }
        throw new UtilException(name + ":在对应城市:" + citryRegion.getRegion_name() + "中未定义");
    }

    /**
     * 根据省份ID 获得省份名称
     * 
     * @author zhangxc
     * @date 2016年9月29日
     * @param provinceId
     * @return
     */
    public String getProvinceNameById(Long provinceId) {
    	OemRegionDto dto = cacheMap.get(provinceId);
        if (dto != null) {
            return dto.getRegion_name();
        } else {
            return null;
        }

    }

    /**
     * 获得城市ID 对应的名称
     * 
     * @author zhangxc
     * @date 2016年9月29日
     * @param cityId
     * @return
     */
    public String getCityNameById(Long cityId) {
    	OemRegionDto dto = cityMap.get(cityId);
        if (dto != null) {
            return dto.getRegion_name();
        } else {
            return null;
        }
    }

    /**
     * 根据ID获得区县名称
     * 
     * @author zhangxc
     * @date 2016年9月29日
     * @param countryId
     * @return
     */
    public String getCountryNameById(Long countryId) {
    	OemRegionDto dto = countryMap.get(countryId);
        if (dto != null) {
            return dto.getRegion_name();
        } else {
            return null;
        }
    }

	@Override
	public OemRegionDto getCodeList(Integer type) {
		// TODO Auto-generated method stub
		return null;
	}
}
