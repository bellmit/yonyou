package com.yonyou.dms.manage.service.basedata.payType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.DTO.basedata.PayTypeDto;
import com.yonyou.dms.manage.domains.PO.basedata.PayTypePO;

/**
 * 支付方式实现类
 * @author Benzc
 * @date 2016年12月21日
 */
@Service
public class PayTypeServiceImpl implements PayTypeService{
    /**
     * 查询
     * @author Benzc
     */
	@Override
	public PageInfoDto findAllAmount(Map<String, String> queryParams) throws ServiceBizException {
		StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,PAY_TYPE_CODE,PAY_TYPE_NAME,WRITEOFF_TAG,IS_RESERVED,IS_INTEGRAL,CREATED_AT FROM tm_pay_type");
		PageInfoDto dto = DAOUtil.pageQuery(sb.toString(), null);
		return dto;
	}
    
	/**
	 * 新增
	 * @author Benzc
	 * @return 
	 */
	@Override
	public void insertPayTypePo(PayTypeDto paydto) throws ServiceBizException {		
		StringBuffer sb= new StringBuffer("SELECT DEALER_CODE,PAY_TYPE_CODE,PAY_TYPE_NAME,WRITEOFF_TAG,IS_RESERVED,IS_INTEGRAL from tm_pay_type where 1=1 and PAY_TYPE_CODE=?");
	    List<Object> list=new ArrayList<Object>();
	    list.add(paydto.getPayTypeCode());
	    List<Map> map=DAOUtil.findAll(sb.toString(), list);
	    StringBuffer sb2= new StringBuffer("SELECT DEALER_CODE,PAY_TYPE_CODE,PAY_TYPE_NAME,WRITEOFF_TAG,IS_RESERVED,IS_INTEGRAL from tm_pay_type where 1=1 and PAY_TYPE_NAME=?");
	    List<Object> list2=new ArrayList<Object>();
        list2.add(paydto.getPayTypeName());
        List<Map> map2=DAOUtil.findAll(sb2.toString(), list2);
        if(map.size()>0 || map2.size()>0){
	        throw new ServiceBizException("支付方式代码或名称不能重复！");
	    }else{
	    PayTypePO lap = new PayTypePO();
		lap.setString("PAY_TYPE_CODE", paydto.getPayTypeCode());
		lap.setString("PAY_TYPE_NAME", paydto.getPayTypeName());
		lap.setInteger("WRITEOFF_TAG", paydto.getWriteoffTag());
		lap.setInteger("IS_RESERVED", paydto.getIsReserved());
		lap.setInteger("IS_INTEGRAL", paydto.getisIntegral());
		lap.saveIt();
		
	    }
	}
    
	/**
	 * 修改
	 * @author Benzc
	 * @return 
	 */
	@Override
	public void updatePayType(String id, PayTypeDto payDto) throws ServiceBizException { 
		PayTypePO payType=PayTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
		setPayType(payType,payDto);
		payType.saveIt();
	}
	
	/**
     * 设置对象属性
     * @author Benzc
     * @date 2016年12月22日
     * @param color
     * @param colordto
     * @throws ServiceBizException
     */

    private void setPayType(PayTypePO payType, PayTypeDto payDto) throws ServiceBizException{
    	payType.setString("PAY_TYPE_CODE", payDto.getPayTypeCode());
    	payType.setString("PAY_TYPE_NAME", payDto.getPayTypeName());
    	payType.setString("WRITEOFF_TAG", payDto.getWriteoffTag());
    	payType.setString("IS_RESERVED", payDto.getIsReserved());
    	payType.setString("IS_INTEGRAL", payDto.getisIntegral());
    }

	@Override
	public PayTypePO findById(String id) throws ServiceBizException {
		return PayTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
	}

}
