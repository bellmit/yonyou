
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

import com.yonyou.dms.framework.domains.DTO.baseData.DictDto;
import com.yonyou.dms.framework.domains.PO.baseData.DictPO;
import com.yonyou.dms.framework.service.cache.CacheService;
import com.yonyou.dms.function.exception.UtilException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 定义缓存服务    
 * @author zhangxc
 * @date 2016年8月22日
 */
@Component("DictCache")
public class DictCacheServiceImpl<T> implements CacheService<List<DictDto>>{
    private static final Logger logger = LoggerFactory.getLogger(DictCacheServiceImpl.class);
    /**
     * 定义缓存对象
     */
    private static Map<Integer,List<DictDto>> cacheMap = new HashMap<>();

    /**
     * 执行初始化操作
    * @author zhangxc
    * @date 2016年8月22日
    * (non-Javadoc)
    * @see com.yonyou.dms.common.service.system.cache.CacheService#init()
     */
    @Override
    public void init() {
        try{
            //查询TC_Code 表中所有的数据
            List<DictPO> dictList = DictPO.findAll().orderBy("TYPE asc,code_id asc");
            for (int i = 0; i < dictList.size(); i++) {
                //加载数据
                DictPO dictPO = dictList.get(i);
                initOneValue(dictPO);
            }
            logger.info("TC_CODE 初始化成功");
        }catch(Exception e){
            logger.error("TC_CODE 初始化错误:"+e.getMessage(),e);
        }finally{
            
        }
        
    }

    /**
     * 
    * @author zhangxc
    * @date 2016年8月22日
    * @param key
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.common.service.system.cache.CacheService#getValueBykey(java.lang.String)
     */
    @Override
    public List<DictDto> getValueBykey(String key) {
        List<DictDto> cacheValue = cacheMap.get(key);
        if(cacheValue==null){
            //重新加载这个key 值 的数据
            loadValueIfNotExists(key);
            return cacheMap.get(key);
        }else{
            return cacheValue;
        }
    }

    /**
     * 重新加载某一个key 值
    * @author zhangxc
    * @date 2016年8月22日
    * @param key
    * (non-Javadoc)
    * @see com.yonyou.dms.common.service.system.cache.CacheService#loadValueIfNotExists(java.lang.String)
     */
    @Override
    public void loadValueIfNotExists(String key) {
        try{
            List<DictPO> dictList = DictPO.find("TYPE = ? ", key).orderBy("code_id asc");
            for (int i = 0; i < dictList.size(); i++) {
                //加载数据
                DictPO dictPO = dictList.get(i);
                initOneValue(dictPO);
            }
        }catch(Exception e){
            logger.error("系统初始化错误:"+e.getMessage(),e);
        }finally{
        }
    }

    /**
     * 
    * 加载一个TC_code 的数据
    * @author zhangxc
    * @date 2016年8月22日
    * @param dictPO
     */
    private void initOneValue(DictPO dictPO){
        
        Integer type = dictPO.getInteger("TYPE");
        
        DictDto dictDto  = new DictDto();
        dictDto.setCode_id(dictPO.getInteger("CODE_ID"));
        dictDto.setCode_cn_desc(dictPO.getString("CODE_CN_DESC"));
        dictDto.setCode_en_desc(dictPO.getString("CODE_EN_DESC"));
        dictDto.setStatus(dictPO.getInteger("STATUS"));
        
        List<DictDto> listData = null;
        if(cacheMap.get(type)!=null){
            listData = cacheMap.get(type);
        }else{
            listData = new ArrayList<>();
            cacheMap.put(type, listData);
        }
        listData.add(dictDto);
    }

    /**
     * 获得所有的cache 数据
    * @author zhangxc
    * @date 2016年8月22日
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.common.service.system.cache.CacheService#getAllData()
     */
    @Override
    public Map<Integer, List<DictDto>> getAllData() {
        if(cacheMap.size()==0){
            synchronized (cacheMap) {
                if(cacheMap.size()==0){
                    init();
                }else{
                    return cacheMap;
                }
            }
        }
        return cacheMap;
    }
    
    /**
     * 
    * 根据描述信息获得对应的代码
    * @author zhangxc
    * @date 2016年8月26日
    * @param type
    * @param desc
    * @return
     */
    public Integer getCodeIdByDesc(Integer type,String desc){
        List<DictDto> dictList = cacheMap.get(type.intValue());
        if(CommonUtils.isNullOrEmpty(dictList)){
            throw new UtilException("字典代码:"+type+"未定义");
        }
        for(DictDto dict:dictList){
            if(desc.trim().equals(dict.getCode_cn_desc())){
                return dict.getCode_id();
            }
        }
        throw new UtilException(desc+"在系统中未定义");
    }
    
    /**
     * 
    * 根据codeID 获得对应的描述
    * @author zhangxc
    * @date 2016年9月28日
    * @param codeId
    * @return
     */
    public String getDescByCodeId(int codeId){
        String codeIdStr = codeId+"";
        Integer codeType = Integer.parseInt(codeIdStr.substring(0, 4));
        List<DictDto> dictList = cacheMap.get(codeType);
        for(int i=0;i<dictList.size();i++){
            DictDto dictDto = dictList.get(i);
            if(dictDto.getCode_id().intValue() == codeId){
                return dictDto.getCode_cn_desc();
            }
        }
        return null;
    }

	@Override
	public List<DictDto> getCodeList(Integer type) {
		// TODO Auto-generated method stub
		return null;
	}
}
