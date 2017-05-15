
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : DiscountModeServiceImpl.java
*
 * @Author : zhengcong
 *
 * @Date : 2017年3月23日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月23日   zhengcong   1.0
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

import com.yonyou.dms.common.domains.PO.basedata.TmDiscountModePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.DiscountModeDTO;

/**
 * @author zhengcong
 * @date 2017年3月23日
*/
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class DiscountModeServiceImpl implements DiscountModeService{
    
//    @Autowired
//    private OperateLogService operateLogService;
    /**
    * 查询
 * @author zhengcong
 * @date 2017年3月23日
    * @return
    */
    	
    @Override
    public PageInfoDto QueryDiscountMode(Map<String, String> queryParam) throws ServiceBizException{

        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE, DISCOUNT_MODE_CODE, DISCOUNT_MODE_NAME, LABOUR_AMOUNT_DISCOUNT, ");
        		sqlSb.append("REPAIR_PART_DISCOUNT, SALES_PART_DISCOUNT, ADD_ITEM_DISCOUNT,  ");
        		sqlSb.append("NO_DISCOUNT_BY_INSURANCE, UPHOLSTER_AMOUNT_DISCOUNT, UPHOLSTER_PART_DISCOUNT ");
        		sqlSb.append("from  tm_discount_mode where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("discountModeCode"))){
            sqlSb.append(" and DISCOUNT_MODE_CODE like ?");
            params.add("%"+queryParam.get("discountModeCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("discountModeName"))){
            sqlSb.append(" and DISCOUNT_MODE_NAME like ?");
            params.add("%"+queryParam.get("discountModeName")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(),params);
        return pageInfoDto;
    }
    
    
    /**
     * 根据code查询业务往来客户信息
     * @author zhengcong
     * @date 2017年3月23日
     */
	@Override
	public Map<String, String> findByCode(String DISCOUNT_MODE_CODE) throws ServiceBizException {
    StringBuilder sb = new StringBuilder("SELECT DEALER_CODE, DISCOUNT_MODE_CODE, DISCOUNT_MODE_NAME, LABOUR_AMOUNT_DISCOUNT, ");
	sb.append("REPAIR_PART_DISCOUNT, SALES_PART_DISCOUNT, ADD_ITEM_DISCOUNT,  ");
	sb.append("NO_DISCOUNT_BY_INSURANCE, UPHOLSTER_AMOUNT_DISCOUNT, UPHOLSTER_PART_DISCOUNT ");
	sb.append("from  tm_discount_mode where 1=1 ");
    sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
    sb.append(" and DISCOUNT_MODE_CODE = ? ");
    List queryParam = new ArrayList();
    queryParam.add(DISCOUNT_MODE_CODE);
    return DAOUtil.findFirst(sb.toString(), queryParam);
}

	/**
	 * 更新
     * @author zhengcong
     * @date 2017年3月23日
	 */

	@Override
	public void modifyByCode(String DISCOUNT_MODE_CODE,DiscountModeDTO cudto) throws ServiceBizException{
		TmDiscountModePO cupo=TmDiscountModePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),DISCOUNT_MODE_CODE);
		    cupo.setString("DISCOUNT_MODE_NAME", cudto.getDiscountModeName());
		    cupo.setDouble("LABOUR_AMOUNT_DISCOUNT", cudto.getLabourAmountDiscount());
		    cupo.setDouble("REPAIR_PART_DISCOUNT", cudto.getRepairPartDiscount());
		    cupo.setDouble("SALES_PART_DISCOUNT", cudto.getSalesPartDiscount());
		    cupo.setDouble("ADD_ITEM_DISCOUNT", cudto.getAddItemDiscount());
		    cupo.setDouble("UPHOLSTER_AMOUNT_DISCOUNT", cudto.getUpholsterAmountDiscount());
		    cupo.setDouble("UPHOLSTER_PART_DISCOUNT", cudto.getUpholsterPartDiscount());
		    cupo.setInteger("NO_DISCOUNT_BY_INSURANCE", cudto.getNoDiscountByInsurance());
		    cupo.saveIt();
	}  
	
	
	
	/**
	 * 新增
     * @author zhengcong
     * @date 2017年3月23日
	 */
	@Override
	public void addDiscountMode(DiscountModeDTO cudto)throws ServiceBizException {		
		StringBuffer sb= new StringBuffer("SELECT DEALER_CODE, DISCOUNT_MODE_CODE from  tm_discount_mode where 1=1 ");
		sb.append("and dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sb.append("and DISCOUNT_MODE_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(cudto.getDiscountModeCode());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
        if(map.size()>0 ){
	        throw new ServiceBizException("代码不允许重复");
	    }else{
	    	
	    	TmDiscountModePO cupo=new TmDiscountModePO();
	    	cupo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
	    	cupo.setString("DISCOUNT_MODE_CODE", cudto.getDiscountModeCode());
		    cupo.setString("DISCOUNT_MODE_NAME", cudto.getDiscountModeName());
		    cupo.setDouble("LABOUR_AMOUNT_DISCOUNT", cudto.getLabourAmountDiscount());
		    cupo.setDouble("REPAIR_PART_DISCOUNT", cudto.getRepairPartDiscount());
		    cupo.setDouble("SALES_PART_DISCOUNT", cudto.getSalesPartDiscount());
		    cupo.setDouble("ADD_ITEM_DISCOUNT", cudto.getAddItemDiscount());
		    cupo.setDouble("UPHOLSTER_AMOUNT_DISCOUNT", cudto.getUpholsterAmountDiscount());
		    cupo.setDouble("UPHOLSTER_PART_DISCOUNT", cudto.getUpholsterPartDiscount());
		    cupo.setInteger("NO_DISCOUNT_BY_INSURANCE", cudto.getNoDiscountByInsurance());
		    cupo.saveIt();
		
	    }
	}	
	
	@Override
	public List<Map> queryDiscountMode() {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT * FROM TM_DISCOUNT_MODE WHERE DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
		List param = new ArrayList();		
		return DAOUtil.findAll(sb.toString(), param);
	}


}
