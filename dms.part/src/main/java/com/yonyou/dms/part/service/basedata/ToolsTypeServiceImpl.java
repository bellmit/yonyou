package com.yonyou.dms.part.service.basedata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.ToolsTypePO;
import com.yonyou.dms.common.domains.PO.basedata.UnitPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.ToolsTypeDTO;
@Service
public class ToolsTypeServiceImpl implements ToolsTypeService {
    /**
     * 查询
    * @author yangjie
    * @date 2017年4月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolsTypeService#searchToolsType(java.util.Map)
     */
    @Override
    public PageInfoDto searchToolsType(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 封装查询语句
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder("select TOOL_TYPE_CODE,TOOL_TYPE_NAME,"
                + "DEALER_CODE FROM tm_tool_type WHERE 1=1 ");//设置查询字段
       //拼接模糊查询条件
        if(!StringUtils.isNullOrEmpty(queryParam.get("toolTypeCode"))){
            sb.append(" and TOOL_TYPE_CODE like ? ");
            params.add("%"+queryParam.get("toolTypeCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("toolTypeName"))){
            sb.append(" and TOOL_TYPE_NAME like ? ");
            params.add("%"+queryParam.get("toolTypeName")+"%");
        }
        return sb.toString();
    }
    /**
     * 增加
    * @author yujiangheng
    * @date 2017年4月12日
    * @param toolsTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolsTypeService#addToolsType(com.yonyou.dms.part.domains.DTO.basedata.ToolsTypeDTO)
     */
    @Override
    public void addToolsType(ToolsTypeDTO toolsTypeDTO) throws ServiceBizException {
        //校验传入参数
        if(StringUtils.isNullOrEmpty(toolsTypeDTO.getToolTypeCode())){
            throw new ServiceBizException("工具类别代码不能为空");
        }
        if(StringUtils.isNullOrEmpty(toolsTypeDTO.getToolTypeName())){
            throw new ServiceBizException("工具类别名称不能为空");
        }
        //设置对象属性
        ToolsTypePO toolsTypePO=new ToolsTypePO();
        setToolsTypePO(toolsTypePO,toolsTypeDTO);
        //执行插入语句
        toolsTypePO.saveIt();
    }
    /**
     * 设置对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param toolsTypePO
    * @param toolsTypeDTO
     */
    private void setToolsTypePO(ToolsTypePO toolsTypePO, ToolsTypeDTO toolsTypeDTO) {
        toolsTypePO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        toolsTypePO.setString("TOOL_TYPE_CODE", toolsTypeDTO.getToolTypeCode());
        toolsTypePO.setString("TOOL_TYPE_NAME", toolsTypeDTO.getToolTypeName());
    }
    /**
     *修改一条 
    * @author yujiangheng
    * @date 2017年4月12日
    * @param toolTypeCode
    * @param toolsTypeDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolsTypeService#updateToolsType(java.lang.String, com.yonyou.dms.part.domains.DTO.basedata.ToolsTypeDTO)
     */
    @Override
    public void updateToolsType(String toolTypeCode, ToolsTypeDTO toolsTypeDTO) throws ServiceBizException {
        ToolsTypePO toolsTypePO=ToolsTypePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),toolTypeCode);
        setToolsTypePO(toolsTypePO,toolsTypeDTO);
        toolsTypePO.saveIt();//执行插入语句
    }
    /**
     * 查询一条
    * @author yujiangheng
    * @date 2017年4月12日
    * @param toolTypeCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolsTypeService#findBytoolTypeCode(java.lang.String)
     */
    @Override
    public Map<String, String> findBytoolTypeCode(String toolTypeCode) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("select TOOL_TYPE_CODE,TOOL_TYPE_NAME,"
                + "DEALER_CODE FROM tm_tool_type WHERE 1=1 ");//设置查询字段
      sb.append(" and TOOL_TYPE_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(toolTypeCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    @Override
    public List<Map> getAllSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select TOOL_TYPE_CODE,TOOL_TYPE_NAME,"
                + "DEALER_CODE FROM tm_tool_type WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), params);
        return result;
    }
}
