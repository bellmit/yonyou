package com.yonyou.dms.part.service.basedata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.yonyou.dms.common.domains.PO.basedata.UnitPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;

/**
 * 计量单位实现类
* TODO description
* @author yujiangheng
* @date 2017年3月31日
 */
@Service
public class UnitServiceImpl implements UnitService {

    /**
     *查询所有计量单位
    * @author yujiangheng
    * @date 2017年3月31日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.UnitService#searchUnit(java.util.Map)
     */
    @Override
    public PageInfoDto searchUnit(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    
    /**
     *封装查询sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder("select UNIT_CODE,UNIT_NAME,DEALER_CODE, ");//设置查询字段
        sb.append("(case when OEM_TAG=12781001  then 10571001 END ) OEM_TAG FROM TM_UNIT WHERE 1=1 ");
       //拼接模糊查询条件
        if(!StringUtils.isNullOrEmpty(queryParam.get("unitCode"))){
            sb.append(" and UNIT_CODE like ? ");
            params.add("%"+queryParam.get("unitCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("unitName"))){
            sb.append(" and UNIT_NAME like ? ");
            params.add("%"+queryParam.get("unitName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("oemTag"))){
            sb.append(" and OEM_TAG like ? ");
            params.add("%"+queryParam.get("oemTag")+"%");
        }
        return sb.toString();
    }
//, query: SELECT COUNT(*) FROM (select UNIT_CODE,UNIT_NAME,DEALER_CODE (case when OEM_TAG=12781001  then 10571001 END ) OEM_TAG FROM TM_UNIT WHERE 1=1 ) tt where DEALER_CODE in (?,'-1'), params: 2100000
    /**
     * 增加一个计量单位
    * @author yyujiangheng
    * @date 2017年3月31日
    * @param unitPodto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.UnitService#addUnitPo(com.yonyou.dms.part.domains.DTO.basedata.UnitDTO)
     */
    @Override
    public void addUnitPo(UnitDTO unitdto) throws ServiceBizException {
        //校验传入参数
        if(StringUtils.isNullOrEmpty(unitdto.getUnitName())){
            throw new ServiceBizException("计量单位名称不能为空");
        }
        //设置对象属性
        UnitPo unitPo=new UnitPo();
        setUnitPo(unitPo,unitdto);
        //执行插入语句
        unitPo.saveIt();
    }
  /**
   * 根据Unit_code 获取计量单位信息
  * TODO description
  * @author yujiangheng
  * @date 2017年4月1日
  * @param id
  * @return
   */
    @Override
    public Map<String, String> findByUnitCode(String unitCode) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select DEALER_CODE,UNIT_CODE,UNIT_NAME, "); 
        sb.append("(case when OEM_TAG=12781001 then 10571001 END ) OEM_TAG from TM_UNIT where 1=1 ");
      sb.append("and DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
      sb.append(" and UNIT_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(unitCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    
    /**
     * 修改一个计量单位
    * @author yujiangheng
    * @date 2017年3月31日
    * @param STORAGE_CODE
    * @param storeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.UnitService#updateStore(java.lang.String, com.yonyou.dms.part.domains.DTO.basedata.StoreDTO)
     */
    @Override
    public void updateUnit(String unitCode, UnitDTO unitdto) throws ServiceBizException {
       String str= FrameworkUtil.getLoginInfo().getDealerCode();
               UnitPo unitPo = UnitPo.findByCompositeKeys(unitCode,FrameworkUtil.getLoginInfo().getDealerCode());//联合主键时
               // StorePo lap=StorePo.findById(id);//单一主键
             setUnitPo(unitPo,unitdto);    //设置对象属性
             unitPo.saveIt();
    }
    /**
     * 设置对象UnitDTO属性
    * TODO description
    * @author yujiangheng
    * @date 2017年3月31日
    * @param unitPo
    * @param unitdto
     */
        private void setUnitPo(UnitPo unitPo, UnitDTO unitdto) {
        unitPo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        unitPo.setString("UNIT_CODE", unitdto.getUnitCode());
        unitPo.setString("UNIT_NAME", unitdto.getUnitName());
        if(unitdto.getOemTag()==null){
            unitdto.setOemTag("12781002");
        }
        unitPo.setString("OEM_TAG", unitdto.getOemTag());
    }
        
  

}