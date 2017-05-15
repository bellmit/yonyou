package com.yonyou.dms.part.service.basedata;
import java.util.List;
import java.util.Map;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;

/**
 * 会计周期接口
* TODO description
* @author yujiangheng
* @date 2017年4月6日
 */
public interface AccountPeriodService {
    
    public PageInfoDto searchAccountPeriod(Map<String, String> queryParam)throws ServiceBizException;///根据条件查询计量单位信息 
    public Map<String,Object>  findByKeys(String bYear,String periods)throws ServiceBizException;//根据unitCode查询一个计量单位信息
    public void updateAccountPeriod(String bYear, String periods, AccountPeriodDTO accountPeriodDTO) throws ServiceBizException;//修改
    public void updateNextAccountPeriod(String string, String string2, Map<String, Object> accountPeriod1)throws ServiceBizException;//更新修改周期的下一个周期
    public void addAccountPeriod(AccountPeriodDTO accountPeriodDTO)throws ServiceBizException;//增加一个最新的周期（日期最靠后）
    public  Map<String,Object> findLastOne()throws ServiceBizException;//获取会计周期表中的最后一条数据
    
    
}
