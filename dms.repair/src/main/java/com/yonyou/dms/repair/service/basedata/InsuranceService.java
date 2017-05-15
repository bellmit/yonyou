package com.yonyou.dms.repair.service.basedata;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.InsuranceDTO;
import com.yonyou.dms.repair.domains.PO.basedata.InsurancePo;

/**
* 保险公司方法接口
* @author zhongsw
* @date 2016年7月1日
*/
	
public interface InsuranceService {
    
    public InsurancePo findByCode(String id) throws ServiceBizException;
    
    public PageInfoDto InsuranceSQL(Map<String, String> queryParam)throws ServiceBizException;///查询
   
    public List<Map> selectInsurance()throws ServiceBizException;//保险公司下拉框
}
