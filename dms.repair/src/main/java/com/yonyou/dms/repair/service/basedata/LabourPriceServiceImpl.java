
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : LabourPriceServiceImpl.java
 *
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日    zhengcong    1.0
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

import com.yonyou.dms.common.domains.PO.basedata.TmLabourPricePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.LabourPriceDTO;

/**
 * 工时单价接口实现
 * 
 * @author zhengcong
 * @date 2017年3月23日
 */
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class LabourPriceServiceImpl implements LabourPriceService {

    /**
     * 根据传入信息查询工时单价信息
 * @author zhengcong
 * @date 2017年3月23日
     */
    @Override
    public PageInfoDto QueryLabourPrice(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer(
                "select DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE,OEM_TAG from tm_LABOUR_PRICE where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("LABOUR_PRICE_CODE"))) {
            sqlsb.append(" and LABOUR_PRICE_CODE like ?");
            params.add("%"+queryParam.get("LABOUR_PRICE_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("OEM_TAG"))) {
            sqlsb.append(" and OEM_TAG=?");
            params.add(Integer.parseInt(queryParam.get("OEM_TAG")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(),params);
        return pageInfoDto;
    }
    
    
    /**
     * 根据LABOUR_PRICE_CODE查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月22日
     */
	@Override
	public Map<String, String> findByCode(String LABOUR_PRICE_CODE) throws ServiceBizException {
    StringBuilder sb = new StringBuilder("select DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE,OEM_TAG from tm_LABOUR_PRICE where 1=1 "); 
    sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    sb.append(" and LABOUR_PRICE_CODE = ? ");
    List queryParam = new ArrayList();
    queryParam.add(LABOUR_PRICE_CODE);
    return DAOUtil.findFirst(sb.toString(), queryParam);
}


	/**
	 * 更新
     * @author zhengcong
     * @date 2017年3月21日
	 */

	@Override
	public void modifyByCode(String LABOUR_PRICE_CODE,LabourPriceDTO cudto) throws ServiceBizException{
		TmLabourPricePO cupo=TmLabourPricePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),LABOUR_PRICE_CODE);
		    cupo.setDouble("LABOUR_PRICE", cudto.getLabourPrice());
		    cupo.saveIt();
	}


	/**
	 * 新增
     * @author zhengcong
     * @date 2017年3月22日
	 */
	@Override
	public void addLabourPrice(LabourPriceDTO cudto)throws ServiceBizException {		
		StringBuffer sb= new StringBuffer("select DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE,OEM_TAG from tm_LABOUR_PRICE where 1=1 ");
		sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append("and LABOUR_PRICE_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(cudto.getLabourPriceCode());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
        if(map.size()>0 ){
	        throw new ServiceBizException("代码不允许重复");
	    }else{
	    	
	    	TmLabourPricePO cupo=new TmLabourPricePO();
	    	cupo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
	    	cupo.setString("LABOUR_PRICE_CODE", cudto.getLabourPriceCode());
	    	cupo.setString("LABOUR_PRICE", cudto.getLabourPrice());
		    cupo.saveIt();
		
	    }
	}



    /**
     * 删除
     * @author zhengcong
     * @date 2017年3月22日
     */
    @Override
    public void deleteLabourPrice(String LABOUR_PRICE_CODE) throws ServiceBizException {
        TmLabourPricePO lp=TmLabourPricePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),LABOUR_PRICE_CODE);
        lp.delete();
    }

    /**
     * 工时单价下拉框加载
     * @author wantao
     * @date 2017年4月20日
     * @return
     */
	@Override
	public List<Map> selectLabourPrice() throws ServiceBizException {
		StringBuffer sb=new StringBuffer("SELECT DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE FROM tm_labour_price WHERE 1=1");
		List<Object> params=new ArrayList<>();
		return DAOUtil.findAll(sb.toString(),params);
	}


}

