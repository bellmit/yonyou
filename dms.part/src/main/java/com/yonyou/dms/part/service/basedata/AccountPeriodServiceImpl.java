package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.UnitPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
/**
 * 会计周期实现类
* TODO description
* @author yangjie
* @date 2017年4月6日
 */
@Service
public class AccountPeriodServiceImpl implements AccountPeriodService{
    
    /**
     * 根据查询条件查询会计周期信息
    * @author yujiangheng
    * @date 2017年4月6日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.AccountPeriodService#searchAccountPeriod(java.util.Map)
     */
    @Override
    public PageInfoDto searchAccountPeriod(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
      //  System.out.println(pageInfoDto);
       return pageInfoDto;
    }
    /**
     * 封装查询语句
    * TODO description
    * @author yujiangheng
    * @date 2017年4月6日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,  ");//设置查询字段
        sb.append("(case when IS_EXECUTED=12781001  then 10571001 END ) IS_EXECUTED FROM TM_ACCOUNTING_CYCLE WHERE 1=1 ");
        //拼接模糊查询条件
         if(!StringUtils.isNullOrEmpty(queryParam.get("bYear"))){
             sb.append(" and B_YEAR like ? ");
             params.add("%"+queryParam.get("bYear")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("periods"))){
             sb.append(" and PERIODS like ? ");
             params.add("%"+queryParam.get("periods")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
             sb.append(" and BEGIN_DATE like ? ");
             params.add("%"+queryParam.get("beginDate")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
             sb.append(" and END_DATE like ? ");
             params.add("%"+queryParam.get("endDate")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("isExecuted"))){
             sb.append(" and IS_EXECUTED like ? ");
             params.add("%"+queryParam.get("isExecuted")+"%");
         }
         return sb.toString();
    }
   /**
    * 根据主键查询会计周期信息 和下个周期的结束日期
   * @author yujiangheng
   * @date 2017年4月6日
   * @param bYear
   * @param periods
   * @return
   * @throws ServiceBizException
   * (non-Javadoc)
   * @see com.yonyou.dms.part.service.basedata.AccountPeriodService#findByUnitCode(java.lang.String, java.lang.String)
    */
    @Override
    public Map<String,Object>   findByKeys(String bYear, String periods) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,  ");//设置查询字段
        sb.append("(case when IS_EXECUTED=12781001  then 10571001 END ) IS_EXECUTED FROM TM_ACCOUNTING_CYCLE WHERE 1=1 ");
      sb.append("and DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
      sb.append(" and B_YEAR = ? ");
      sb.append(" and PERIODS = ? ");
      List queryParam = new ArrayList();
      queryParam.add(bYear);
      queryParam.add(periods);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    
    
   /**
    *  更新当前周期的结束日期
   * @author yujiangheng
   * @date 2017年4月7日
   * @param bYear
   * @param periods
   * @param accountPeriodDTO
   * @throws ServiceBizException
   * (non-Javadoc)
   * @see com.yonyou.dms.part.service.basedata.AccountPeriodService#updateAccountPeriod(java.lang.String, java.lang.String, com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO)
    */
@Override
public void updateAccountPeriod(String bYear, String periods, AccountPeriodDTO accountPeriodDTO) throws ServiceBizException {
  //  String str= FrameworkUtil.getLoginInfo().getDealerCode();
    AccountPeriodPO accountPeriodPo = AccountPeriodPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bYear,periods);//联合主键时
    // StorePo lap=StorePo.findById(id);//单一主键
  //  System.out.println("--------"+accountPeriodDTO);
  setAccountPeriodPo(accountPeriodPo,accountPeriodDTO);    //设置对象属性
  accountPeriodPo.saveIt();
}
private void setAccountPeriodPo(AccountPeriodPO accountPeriodPo, AccountPeriodDTO accountPeriodDTO) {
    accountPeriodPo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
    accountPeriodPo.setString("B_YEAR", accountPeriodDTO.getbYear());
    accountPeriodPo.setString("PERIODS", accountPeriodDTO.getPeriods());
    accountPeriodPo.setDate("BEGIN_DATE", accountPeriodDTO.getBeginDate());
    accountPeriodPo.setDate("END_DATE", accountPeriodDTO.getEndDate());
    if(accountPeriodDTO.getIsExecuted()==null){
        accountPeriodDTO.setIsExecuted(12781002);
    }
    accountPeriodPo.setInteger("IS_EXECUTED", accountPeriodDTO.getIsExecuted());
}
/**
 * 更新后一个会计周期的开始日期
* @author yangjie
* @date 2017年4月7日
* @param string
* @param string2
* @param accountPeriod1
* @throws ServiceBizException
* (non-Javadoc)
* @see com.yonyou.dms.part.service.basedata.AccountPeriodService#updateNextAccountPeriod(java.lang.String, java.lang.String, java.util.Map)
 */
@Override
public void updateNextAccountPeriod(String bYear, String periods,
                                    Map<String, Object> accountPeriod1) throws ServiceBizException {
    AccountPeriodPO accountPeriodPo = AccountPeriodPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),bYear,periods);//联合主键时
    // StorePo lap=StorePo.findById(id);//单一主键
  setNextAccountPeriodPo(accountPeriodPo,accountPeriod1);    //设置对象属性
  accountPeriodPo.saveIt();
}
private void setNextAccountPeriodPo(AccountPeriodPO accountPeriodPo, Map<String, Object> accountPeriod1) {
    accountPeriodPo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
    accountPeriodPo.setString("B_YEAR", accountPeriod1.get("B_YEAR"));
    accountPeriodPo.setString("PERIODS", accountPeriod1.get("PERIODS"));
    accountPeriodPo.setDate("BEGIN_DATE", accountPeriod1.get("BEGIN_DATE"));
    accountPeriodPo.setDate("END_DATE", accountPeriod1.get("END_DATE"));
    if(accountPeriod1.get("IS_EXECUTED")==null){
        accountPeriod1.put("IS_EXECUTED", 12781002);
    }
    accountPeriodPo.setInteger("IS_EXECUTED", accountPeriod1.get("IS_EXECUTED"));
}
/**
 * 增加一个最新的会计周期信息
* @author yujiangheng
* @date 2017年4月7日
* @param accountPeriodDTO
* @throws ServiceBizException
* (non-Javadoc)
* @see com.yonyou.dms.part.service.basedata.AccountPeriodService#addAccountPeriod(com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO)
 */
@Override
public void addAccountPeriod(AccountPeriodDTO accountPeriodDTO) throws ServiceBizException {
  //校验传入参数
    if(StringUtils.isNullOrEmpty(accountPeriodDTO.getbYear())){
        throw new ServiceBizException("会计周期年度不能为空");
    }
    if(StringUtils.isNullOrEmpty(accountPeriodDTO.getPeriods())){
        throw new ServiceBizException("会计周期的周期不能为空");
    }
    AccountPeriodPO accountPeriodPo=new AccountPeriodPO();
    setAccountPeriodPo(accountPeriodPo,accountPeriodDTO);    //设置对象属性
     accountPeriodPo.saveIt();
}
    
/**
 * 在增加之前先获取到会计周期表中的最后一条数据
 * 在后台进行判断（最后一条数据是否是该年度下的最后一个周期，如果是则年度+1，周期变为 01）
 */
public  Map<String,Object> findLastOne(){
    StringBuilder sb=new StringBuilder("select DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,  ");//设置查询字段
    sb.append("(case when IS_EXECUTED=12781001  then 10571001 END ) IS_EXECUTED FROM TM_ACCOUNTING_CYCLE  ");
    sb.append("WHERE 1=1 ");
    sb.append(" GROUP BY end_date DESC limit 0,1");
    return DAOUtil.findFirst(sb.toString(), null);
}
}




