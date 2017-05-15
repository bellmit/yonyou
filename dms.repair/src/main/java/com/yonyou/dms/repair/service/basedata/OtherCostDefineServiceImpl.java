package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.OtherCostDefinePO;
import com.yonyou.dms.common.domains.PO.basedata.UnitPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;
/**
 * 其他成本业务层实现类
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
@Service
public class OtherCostDefineServiceImpl implements OtherCostDefineService{
    /**
     * 根据查询条件查询其他成本信息
    * @author yujiangheng
    * @date 2017年4月10日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.OtherCostDefineService#searchOtherCost(java.util.Map)
     */
    @Override
    public PageInfoDto searchOtherCost(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 封装查询sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月10日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder("select OTHER_COST_NAME,OTHER_COST_CODE,"
                + "DEALER_CODE FROM TM_OTHER_COST    WHERE 1=1");//设置查询字段
       //拼接模糊查询条件
        if(!StringUtils.isNullOrEmpty(queryParam.get("otherCostCode"))){
            sb.append(" and OTHER_COST_CODE like ? ");
            params.add("%"+queryParam.get("otherCostCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("otherCostName"))){
            sb.append(" and OTHER_COST_NAME like ? ");
            params.add("%"+queryParam.get("otherCostName")+"%");
        }
        return sb.toString();
    }
    /**
     * 增加一条其他成本信息
    * @author yujiangheng
    * @date 2017年4月10日
    * @param otherCostDefineDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.OtherCostDefineService#addOtherCostDefine(com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO)
     */
    @Override
    public void addOtherCostDefine(OtherCostDefineDTO otherCostDefineDTO) {
        //校验传入参数
        if(StringUtils.isNullOrEmpty(otherCostDefineDTO.getOtherCostCode())){
            throw new ServiceBizException("计量单位名称不能为空");
        }
        if(StringUtils.isNullOrEmpty(otherCostDefineDTO.getOtherCostName())){
            throw new ServiceBizException("计量单位名称不能为空");
        }
        //设置对象属性
        OtherCostDefinePO otherCostDefinePO=new OtherCostDefinePO();
        setOtherCostDefinePo(otherCostDefinePO,otherCostDefineDTO);
        //执行插入语句
        otherCostDefinePO.saveIt();
    }
    /**
     * 设置对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月10日
    * @param otherCostDefinePO
    * @param otherCostDefineDTO
     */
    private void setOtherCostDefinePo(OtherCostDefinePO otherCostDefinePO, OtherCostDefineDTO otherCostDefineDTO) {
        otherCostDefinePO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        otherCostDefinePO.setString("OTHER_COST_CODE", otherCostDefineDTO.getOtherCostCode());
        otherCostDefinePO.setString("OTHER_COST_NAME", otherCostDefineDTO.getOtherCostName());
        
    }
    /**
     * 根据otherCostCode查询一条其他成本的信息
    * @author yujiangheng
    * @date 2017年4月10日
    * @param itemId
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.OtherCostDefineService#findByItemId(java.lang.Integer)
     */
    @Override
    public Map<String, String> findByOtherCostCode(String otherCostCode ) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("select OTHER_COST_NAME,OTHER_COST_CODE,"
                + "DEALER_CODE FROM tm_other_cost WHERE 1=1");//设置查询字段
      sb.append(" and OTHER_COST_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(otherCostCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    @Override
    public void updateOtherCost(String otherCostCode, OtherCostDefineDTO otherCostDefineDTO) throws ServiceBizException {
        OtherCostDefinePO otherCostDefinePO=OtherCostDefinePO.findByCompositeKeys(otherCostCode,FrameworkUtil.getLoginInfo().getDealerCode());
        setOtherCostDefinePo(otherCostDefinePO,otherCostDefineDTO);
        otherCostDefinePO.saveIt();
    }
}


