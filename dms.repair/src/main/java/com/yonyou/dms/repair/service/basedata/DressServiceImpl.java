package com.yonyou.dms.repair.service.basedata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.MainDressTypePO;
import com.yonyou.dms.common.domains.PO.basedata.OtherCostDefinePO;
import com.yonyou.dms.common.domains.PO.basedata.SubDressTypePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SubDressTypeDTO;

@Service
public class DressServiceImpl implements DressService{
    /**
     * 根据条件查询主分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#searchMainDress(java.util.Map)
     */
    @Override
    public PageInfoDto searchMainDress(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 查询主分类sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,MAIN_GROUP_CODE,MAIN_GROUP_NAME,");
             sb.append("(case when DOWN_TAG=12781001  then 10571001 END)  DOWN_TAG FROM tm_decrodate_group  WHERE 1=1");//设置查询字段
       //拼接模糊查询条件
        if(!StringUtils.isNullOrEmpty(queryParam.get("mainGroupCode"))){
            sb.append(" and MAIN_GROUP_CODE like ? ");
            params.add("%"+queryParam.get("mainGroupCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("mainGroupName"))){
            sb.append(" and MAIN_GROUP_NAME like ? ");
            params.add("%"+queryParam.get("mainGroupName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("downTag"))){
            sb.append(" and DOWN_TAG like ? ");
            params.add("%"+queryParam.get("downTag")+"%");
        }
        return sb.toString();
    }
   /**
    * 根据条件查询二级分类
   * @author yujiangheng
   * @date 2017年4月12日
   * @param mainGroupCode
   * @return
   * @throws ServiceBizException
   * (non-Javadoc)
   * @see com.yonyou.dms.repair.service.basedata.DressService#searchSubDress(java.lang.String)
    */
    @Override
    public PageInfoDto searchSubDress(String mainGroupCode) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySubSql(mainGroupCode,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 查询二级分类sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainGroupCode
    * @param params
    * @return
     */
    private String getQuerySubSql(String mainGroupCode,List<Object> params) {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,MAIN_GROUP_CODE,SUB_GROUP_CODE,SUB_GROUP_NAME,");
             sb.append("(case when DOWN_TAG=12781001  then 10571001 END)  DOWN_TAG FROM tm_decrodate_subgroup  WHERE 1=1");//设置查询字段
       //拼接模糊查询条件
        if(!StringUtils.isNullOrEmpty(mainGroupCode)){
            sb.append(" and MAIN_GROUP_CODE like ? ");
            params.add("%"+mainGroupCode+"%");
        }
        return sb.toString();
    }
    /**
     * 增加一个主分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public void addMainDress(MainDressTypeDTO mainDressTypeDTO) throws ServiceBizException {
        if(StringUtils.isNullOrEmpty(mainDressTypeDTO.getMainGroupCode())){
            throw new ServiceBizException("主分类代码不能为空");
        }
        if(StringUtils.isNullOrEmpty(mainDressTypeDTO.getMainGroupName())){
            throw new ServiceBizException("主分类名称不能为空");
        }
        //设置对象属性
        MainDressTypePO mainDressTypePO=new MainDressTypePO();
        setMainDressTypePO(mainDressTypePO,mainDressTypeDTO);
        //执行插入语句
        mainDressTypePO.saveIt();
    }
    private void setMainDressTypePO(MainDressTypePO mainDressTypePO, MainDressTypeDTO mainDressTypeDTO) {
        mainDressTypePO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        mainDressTypePO.setString("MAIN_GROUP_CODE", mainDressTypeDTO.getMainGroupCode());
        mainDressTypePO.setString("MAIN_GROUP_NAME", mainDressTypeDTO.getMainGroupName());
        if(mainDressTypeDTO.getDownTag()==null){
            mainDressTypeDTO.setDownTag(12781002);
        }
        mainDressTypePO.setInteger("DOWN_TAG", mainDressTypeDTO.getDownTag());
    }
    /**
     * 通过mainGroupCode查询一个主分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public Map<String, String> findByMainGroup(String mainGroupCode) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,MAIN_GROUP_CODE,MAIN_GROUP_NAME,");
        sb.append("(case when DOWN_TAG=12781001  then 10571001 END)  DOWN_TAG FROM tm_decrodate_group  WHERE 1=1");//设置查询字段
      sb.append(" and MAIN_GROUP_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(mainGroupCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    /**
     *通过 mainGroupCode修改一个主分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public void updateMainDress(String mainGroupCode, MainDressTypeDTO mainDressTypeDTO) throws ServiceBizException {
        MainDressTypePO mainDressTypePO=MainDressTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),mainGroupCode);
        setMainDressTypePO(mainDressTypePO,mainDressTypeDTO);
        //执行插入语句
        mainDressTypePO.saveIt();
    }
    /**
     * 增加一个二级分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public void addSubDress(String mainGroupCode,SubDressTypeDTO subDressTypeDTO) throws ServiceBizException {
        if(StringUtils.isNullOrEmpty(subDressTypeDTO.getSubGroupCode())){
            throw new ServiceBizException("主分类代码不能为空");
        }
        if(StringUtils.isNullOrEmpty(subDressTypeDTO.getSubGroupName())){
            throw new ServiceBizException("主分类名称不能为空");
        }
        subDressTypeDTO.setMainGroupCode(mainGroupCode);
        //设置对象属性
        SubDressTypePO subDressTypePO=new SubDressTypePO();
        setSubDressTypePO(subDressTypePO,subDressTypeDTO);
        //执行插入语句
        subDressTypePO.saveIt();
    }
  /**
   * 设置二级分类的对象属性
  * TODO description
  * @author yujiangheng
  * @date 2017年4月12日
  * @param subDressTypePO
  * @param subDressTypeDTO
   */
    private void setSubDressTypePO(SubDressTypePO subDressTypePO, SubDressTypeDTO subDressTypeDTO) {
        subDressTypePO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        subDressTypePO.setString("MAIN_GROUP_CODE", subDressTypeDTO.getMainGroupCode());
        subDressTypePO.setString("SUB_GROUP_CODE", subDressTypeDTO.getSubGroupCode());
        subDressTypePO.setString("SUB_GROUP_NAME", subDressTypeDTO.getSubGroupName());
        if(subDressTypeDTO.getDownTag()==null){
            subDressTypeDTO.setDownTag(12781002);
        }
        subDressTypePO.setInteger("DOWN_TAG", subDressTypeDTO.getDownTag());
    }
    /**
     * 查询一个二级分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public Map<String, String> findSubGroup(String mainGroupCode, String subGroupCode) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("select DEALER_CODE,MAIN_GROUP_CODE,SUB_GROUP_CODE,SUB_GROUP_NAME,");
        sb.append("(case when DOWN_TAG=12781001  then 10571001 END)  DOWN_TAG FROM tm_decrodate_subgroup  WHERE 1=1");//设置查询字段
      sb.append(" and MAIN_GROUP_CODE = ? ");
      sb.append(" and SUB_GROUP_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(mainGroupCode);
      queryParam.add(subGroupCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    /**
     * 修改一个二级分类
    * @author yujiangheng
    * @date 2017年4月12日
    * @param mainDressTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.DressService#addMainDress(com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO)
     */
    @Override
    public void updateSubDress(String mainGroupCode, String subGroupCode,SubDressTypeDTO subDressTypeDTO) throws ServiceBizException {
        SubDressTypePO mainDressTypePO=SubDressTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),mainGroupCode,subGroupCode);
        subDressTypeDTO.setMainGroupCode(mainGroupCode);
        setSubDressTypePO(mainDressTypePO,subDressTypeDTO);
        //执行插入语句
        mainDressTypePO.saveIt(); 
    }
}
