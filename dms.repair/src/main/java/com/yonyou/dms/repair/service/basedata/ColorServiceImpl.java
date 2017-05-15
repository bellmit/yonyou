
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : ColorServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月11日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月11日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmColorPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.ColorDTO;

/**
 * 
 * @author Benzc
 * @date 2016年11月22日
 */
@Service
public class ColorServiceImpl implements ColorService{

    /**
     * 查询
     * @author Benzc
     * @date 2016年12月22日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.ColorService#QueryColor(java.util.Map)
     */

    @Override
    public PageInfoDto QueryColor(Map<String, String> queryParam) throws ServiceBizException{
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,COLOR_CODE,COLOR_NAME,OEM_TAG from tm_color where 1=1");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),null);
        return pageInfoDto;
    }

    /**
     * 新增
     * @author Benzc
     * @date 2016年12月22日
     * @param colordto
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.ColorService#addColor(com.yonyou.dms.repair.domains.DTO.basedata.ColorDTO)
     */

    @SuppressWarnings("rawtypes")
	@Override
    public void addColor(ColorDTO colordto) throws ServiceBizException{
    	
    	StringBuffer sb= new StringBuffer("select DEALER_CODE,COLOR_CODE,COLOR_NAME,OEM_TAG from tm_color where 1=1 and COLOR_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(colordto.getColorCode());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
	    if(map.size()>0){
	        throw new ServiceBizException("车辆颜色代码重复！");
	    }else{
			TmColorPo lap = new TmColorPo();
			lap.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			lap.setString("COLOR_CODE", colordto.getColorCode());
			lap.setString("COLOR_NAME", colordto.getColorName());
			lap.setString("OEM_TAG", colordto.getOemTag());
			lap.saveIt();
	    }
    }   

    /**
     * 修改
     * @author Benzc
     * @date 2016年12月22日
     * @param id
     * @param colordto
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.ColorService#updateColor(java.lang.Long, com.yonyou.dms.repair.domains.DTO.basedata.ColorDTO)
     */

    @Override
    public void updateColor(String id, ColorDTO colordto) throws ServiceBizException{
    	TmColorPo color=TmColorPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        setColor(color,colordto);
        color.saveIt();      
    }

    /**
     * 设置对象属性
     * @author Benzc
     * @date 2016年12月22日
     * @param color
     * @param colordto
     * @throws ServiceBizException
     */

    private void setColor(TmColorPo color, ColorDTO colordto) throws ServiceBizException{
        color.setString("COLOR_CODE", colordto.getColorCode());
        color.setString("COLOR_NAME", colordto.getColorName());
        color.setInteger("OEM_TAG", DictCodeConstants.STATUS_IS_NOT);
    }
    
    @Override
    public TmColorPo findById(String id) throws ServiceBizException {
        return TmColorPo.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
    }
    
    /**
     * 下拉框查询颜色
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> selectColor(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,COLOR_CODE,COLOR_NAME,OEM_TAG FROM tm_color where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sb.append(" and OEM_TAG=?");
        params.add(DictCodeConstants.IS_YES);
        List<Map> list = DAOUtil.findAll(sb.toString(),params);
		return list;
	}

}
