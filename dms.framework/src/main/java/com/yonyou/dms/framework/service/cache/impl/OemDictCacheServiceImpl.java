
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.common
 *
 * @File name : DictCacheServiceImpl.java
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.yonyou.dms.framework.domains.DTO.baseData.OemDictDto;
import com.yonyou.dms.framework.domains.PO.baseData.OemDictPO;
import com.yonyou.dms.framework.service.cache.CacheService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 定义缓存服务
 * 
 * @author zhangxc
 * @date 2016年8月22日
 */
@Component("OemDictCache")
public class OemDictCacheServiceImpl<T> implements CacheService<List<OemDictDto>> {
	private static final Logger logger = LoggerFactory.getLogger(OemDictCacheServiceImpl.class);
	/**
	 * 定义缓存对象
	 */
	private static Map<Integer, List<OemDictDto>> cacheMap = new HashMap<>();

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
			// 查询TC_Code 表中所有的数据
			List<OemDictPO> dictList = OemDictPO.find(" STATUS = ? ", OemDictCodeConstants.STATUS_ENABLE)
					.orderBy("TYPE asc,NUM asc");
			for (int i = 0; i < dictList.size(); i++) {
				// 加载数据
				OemDictPO dictPO = dictList.get(i);
				initOneValue(dictPO);
			}
			logger.info("TC_CODE_DCS 初始化成功");
		} catch (Exception e) {
			logger.error("TC_CODE_DCS 初始化错误:" + e.getMessage(), e);
		} finally {

		}

	}

	/**
	 * 
	 * @author zhangxc
	 * @date 2016年8月22日
	 * @param key
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.common.service.system.cache.CacheService#getValueBykey(java.lang.String)
	 */
	@Override
	public List<OemDictDto> getValueBykey(String key) {
		List<OemDictDto> cacheValue = cacheMap.get(key);
		if (cacheValue == null) {
			// 重新加载这个key 值 的数据
			loadValueIfNotExists(key);
			return cacheMap.get(key);
		} else {
			return cacheValue;
		}
	}

	/**
	 * 重新加载某一个key 值
	 * 
	 * @author zhangxc
	 * @date 2016年8月22日
	 * @param key
	 *            (non-Javadoc)
	 * @see com.yonyou.dms.common.service.system.cache.CacheService#loadValueIfNotExists(java.lang.String)
	 */
	@Override
	public void loadValueIfNotExists(String key) {
		try {
			List<OemDictPO> dictList = OemDictPO.find("TYPE = ? ", key).orderBy(" TYPE asc ,NUM  asc");
			for (int i = 0; i < dictList.size(); i++) {
				// 加载数据
				OemDictPO dictPO = dictList.get(i);
				initOneValue(dictPO);
			}
		} catch (Exception e) {
			logger.error("系统初始化错误:" + e.getMessage(), e);
		} finally {
		}
	}

	/**
	 * 
	 * 加载一个TC_code 的数据
	 * 
	 * @author zhangxc
	 * @date 2016年8月22日
	 * @param dictPO
	 */
	private void initOneValue(OemDictPO dictPO) {

		Integer type = dictPO.getInteger("TYPE");

		OemDictDto dictDto = new OemDictDto();
		dictDto.setCode_id(dictPO.getInteger("CODE_ID"));
		dictDto.setCode_desc(dictPO.getString("CODE_DESC"));
		dictDto.setType_name(dictPO.getString("TYPE_NAME"));
		dictDto.setStatus(dictPO.getInteger("STATUS"));

		List<OemDictDto> listData = null;
		if (cacheMap.get(type) != null) {
			listData = cacheMap.get(type);
		} else {
			listData = new ArrayList<>();
			cacheMap.put(type, listData);
		}
		listData.add(dictDto);
	}

	/**
	 * 获得所有的cache 数据
	 * 
	 * @author zhangxc
	 * @date 2016年8月22日
	 * @return (non-Javadoc)
	 * @see com.yonyou.dms.common.service.system.cache.CacheService#getAllData()
	 */
	@Override
	public Map<Integer, List<OemDictDto>> getAllData() {
		if (cacheMap.size() == 0) {
			synchronized (cacheMap) {
				if (cacheMap.size() == 0) {
					init();
				} else {
					return cacheMap;
				}
			}
		}
		return cacheMap;
	}

	/**
	 * 
	 * 根据描述信息获得对应的代码
	 * 
	 * @author zhangxc
	 * @date 2016年8月26日
	 * @param type
	 * @param desc
	 * @return
	 */
	public Integer getCodeIdByDesc(Integer type, String desc) {
		List<OemDictDto> dictList = cacheMap.get(type.intValue());
		if (CommonUtils.isNullOrEmpty(dictList)) {
			throw new UtilException("字典代码:" + type + "未定义");
		}
		for (OemDictDto dict : dictList) {
			if (desc.trim().equals(dict.getCode_desc())) {
				return dict.getCode_id();
			}
		}
		throw new UtilException(desc + "在系统中未定义");
	}

	/**
	 * 
	 * 根据描述信息获得对应的代码
	 * 
	 * @author zhangxc
	 * @date 2016年8月26日
	 * @param type
	 * @param desc
	 * @return
	 */
	@Override
	public List<OemDictDto> getCodeList(Integer type) {
		List<OemDictDto> dictList = cacheMap.get(type.intValue());
		if (CommonUtils.isNullOrEmpty(dictList)) {
			throw new UtilException("字典代码:" + type + "未定义");
		} else {
			return dictList;
		}
	}

	/**
	 * 
	 * 根据codeID 获得对应的描述
	 * 
	 * @author zhangxc
	 * @date 2016年9月28日
	 * @param codeId
	 * @return
	 */
	public String getDescByCodeId(int codeId) {
		String codeIdStr = codeId + "";
		Integer codeType = Integer.parseInt(codeIdStr.substring(0, 4));
		List<OemDictDto> dictList = cacheMap.get(codeType);
		for (int i = 0; i < dictList.size(); i++) {
			OemDictDto dictDto = dictList.get(i);
			if (dictDto.getCode_id().intValue() == codeId) {
				return dictDto.getCode_desc();
			}
		}
		return null;
	}
}
