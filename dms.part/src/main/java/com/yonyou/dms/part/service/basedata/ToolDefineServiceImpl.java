package com.yonyou.dms.part.service.basedata;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.ToolDefinePO;
import com.yonyou.dms.common.domains.PO.basedata.UnitPo;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ListToolDefineDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ToolDefineDTO;
/**
 * 工具定义实现类
* TODO description
* @author yujiangheng
* @date 2017年4月13日
 */
@Service
public class ToolDefineServiceImpl implements ToolDefineService {
    /**
     * 根据条件查询
    * @author yujiangheng
    * @date 2017年4月13日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolDefineService#searchToolDefine(java.util.Map)
     */
    @Override
    public PageInfoDto searchToolDefine(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        System.out.println(queryParam);
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 封装查询sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月13日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
         StringBuilder sb=new StringBuilder("SELECT A.DEALER_CODE,A.VER,TOOL_SPELL, TOOL_CODE, TOOL_NAME, A.TOOL_TYPE_CODE,");
         sb.append(" a.UNIT_CODE UNIT_CODE, POSITION, PRINCIPAL,D.UNIT_NAME UNIT_NAME,");//
         sb.append("B.TOOL_TYPE_NAME, C.EMPLOYEE_NAME as PRINCIPAL_NAME, A.STOCK_QUANTITY, ");
         sb.append("A.LEND_QUANTITY,BEGIN_DATE_VALIDITY,END_DATE_VALIDITY,A.DATE_VALIDITY, A.CAPITAL_ASSERTS_MANAGE_NO");
         sb.append(" FROM tm_tool_stock A  left join ");//tm_employee
          sb.append("(SELECT TOOL_TYPE_CODE,TOOL_TYPE_NAME FROM  ("+ CommonConstants.VM_TOOL_TYPE +") ss");
          sb.append(" where DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"') B on B.TOOL_TYPE_CODE=A.TOOL_TYPE_CODE ");
          sb.append(" left join (SELECT EMPLOYEE_NAME,EMPLOYEE_NO FROM tm_employee WHERE DEALER_CODE = '");
          sb.append(FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
          sb.append("  AND IS_VALID=12781001  ) C   on C.EMPLOYEE_NO=A.PRINCIPAL  ");
          sb.append( "LEFT JOIN tm_unit D ON D.UNIT_CODE=A.UNIT_CODE  WHERE A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
          sb.append(Utility.getLikeCond("A", "TOOL_CODE",queryParam.get("toolCode") , "AND"));
           sb.append(Utility.getLikeCond("A", "TOOL_SPELL",queryParam.get("toolSpell")  , "AND"));
           sb.append(Utility.getLikeCond("A", "POSITION", queryParam.get("position") , "AND"));
           if(!StringUtils.isNullOrEmpty(queryParam.get("toolTypeCode"))){
               sb.append("  AND A.TOOL_TYPE_CODE LIKE ? ");
               params.add("%"+queryParam.get("toolTypeCode")+"%");
           }
           if(!StringUtils.isNullOrEmpty(queryParam.get("principal"))){
               sb.append(" AND A.PRINCIPAL LIKE ? ");  
               params.add("%"+queryParam.get("principal")+"%");
           }
           return sb.toString();
    }
    /**
     * 获取下拉框员工姓名信息
    * TODO description
    * @author yujiangheng
    * @date 2017年4月13日
    * @return
    * @throws ServiceBizException
     */
    @Override
    public List<Map> getAllSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,EMPLOYEE_NO as PRINCIPAL,EMPLOYEE_NAME as PRINCIPAL_NAME FROM tm_employee WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), params);
        return result;
    }
    
    //判断工具代码是否已存在
    /**
     * 增加一条工具定义信息
    * @author yujiangheng
    * @date 2017年4月13日
    * @param toolDefineDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolDefineService#addToolDefine(com.yonyou.dms.part.domains.DTO.basedata.ToolDefineDTO)
     */
    public void addToolDefine(ToolDefineDTO toolDefineDTO) throws ServiceBizException {
        //校验传入参数
        if(StringUtils.isNullOrEmpty(toolDefineDTO.getToolCode())){
            throw new ServiceBizException("工具代码不能为空");
        }else{
            
        }
        ToolDefinePO toolDefinePo=new ToolDefinePO();
        setToolDefinePO(toolDefinePo,toolDefineDTO);    //设置对象属性
        toolDefinePo.saveIt();
    }
    /**
     * 设置对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月13日
    * @param toolDefinePo
    * @param toolDefineDTO
     */
    private void setToolDefinePO(ToolDefinePO toolDefinePo, ToolDefineDTO toolDefineDTO) {
        toolDefinePo.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        toolDefinePo.setString("TOOL_CODE",toolDefineDTO.getToolCode());
        toolDefinePo.setString("TOOL_NAME",toolDefineDTO.getToolCode());
        toolDefinePo.setString("TOOL_SPELL",toolDefineDTO.getToolCode());
        toolDefinePo.setString("UNIT_CODE",toolDefineDTO.getToolCode());
        toolDefinePo.setString("TOOL_TYPE_CODE",toolDefineDTO.getToolCode());
        toolDefinePo.setString("POSITION",toolDefineDTO.getToolCode());
      //  toolDefinePo.setFloat("STOCK_QUANTITY", toolDefineDTO.getStockQuantity()); //库存数量
  //      toolDefinePo.setFloat("LEND_QUANTITY", toolDefineDTO.getLendQuantity()); //借进数量
        toolDefinePo.setString("PRINCIPAL",toolDefineDTO.getPrincipal());
        toolDefinePo.setString("DATE_VALIDITY", toolDefineDTO.getEndDateValidity());
        toolDefinePo.setString("CAPITAL_ASSERTS_MANAGE_NO",toolDefineDTO.getCapitalAssertsManageNo());
        
    }
    /**
     * 根据tool_code查询一条信息
    * TODO description
    * @author yujiangheng
    * @date 2017年4月13日
    * @param mainGroupCode
    * @return
    * @throws ServiceBizException
     */
    @Override
    public Map<String, Object> findByToolCode(String toolCode) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT A.DEALER_CODE,A.VER,TOOL_SPELL, TOOL_CODE, TOOL_NAME, A.TOOL_TYPE_CODE,");
        sb.append(" a.UNIT_CODE UNIT_CODE,D.UNIT_NAME UNIT_NAME, POSITION, PRINCIPAL,");
        sb.append("B.TOOL_TYPE_NAME, C.EMPLOYEE_NAME as PRINCIPAL_NAME, A.STOCK_QUANTITY, ");
        sb.append("A.LEND_QUANTITY,BEGIN_DATE_VALIDITY,END_DATE_VALIDITY,A.DATE_VALIDITY, A.CAPITAL_ASSERTS_MANAGE_NO");
        sb.append(" FROM tm_tool_stock A  left join ");//tm_employee
         sb.append("(SELECT TOOL_TYPE_CODE,TOOL_TYPE_NAME FROM  ("+ CommonConstants.VM_TOOL_TYPE +") ss");
         sb.append(" where DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"') B on B.TOOL_TYPE_CODE=A.TOOL_TYPE_CODE ");
         sb.append(" left join (SELECT EMPLOYEE_NAME,EMPLOYEE_NO FROM tm_employee WHERE DEALER_CODE = '");
         sb.append(FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
         sb.append("  AND IS_VALID=12781001  ) C   on C.EMPLOYEE_NO=A.PRINCIPAL  ");
         sb.append("LEFT JOIN tm_unit D    ON   D.DEALER_CODE=A.DEALER_CODE AND  D.UNIT_CODE=A.UNIT_CODE ");
         sb.append( " WHERE A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
         sb.append(" and TOOL_CODE = ? ");
      List queryParam = new ArrayList();
      queryParam.add(toolCode);
      return DAOUtil.findFirst(sb.toString(), queryParam);
    }
    /**
     * 查询导出数据
    * @author yujiangheng
    * @date 2017年4月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolDefineService#queryToExport(java.util.Map)
     */
    @Override
    public List<Map> queryToExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb=new StringBuilder("SELECT A.DEALER_CODE,A.VER,TOOL_SPELL, TOOL_CODE, TOOL_NAME, A.TOOL_TYPE_CODE,");
        sb.append(" a.UNIT_CODE UNIT_CODE,POSITION, PRINCIPAL,D.UNIT_NAME UNIT_NAME, ");//
        sb.append("B.TOOL_TYPE_NAME, C.EMPLOYEE_NAME as PRINCIPAL_NAME, A.STOCK_QUANTITY, ");
        sb.append("A.LEND_QUANTITY,BEGIN_DATE_VALIDITY,END_DATE_VALIDITY,A.DATE_VALIDITY, A.CAPITAL_ASSERTS_MANAGE_NO");
        sb.append(" FROM tm_tool_stock A  left join ");//tm_employee
         sb.append("(SELECT TOOL_TYPE_CODE,TOOL_TYPE_NAME FROM  ("+ CommonConstants.VM_TOOL_TYPE +") ss");
         sb.append(" where DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"') B on B.TOOL_TYPE_CODE=A.TOOL_TYPE_CODE ");
         sb.append(" left join (SELECT EMPLOYEE_NAME,EMPLOYEE_NO FROM tm_employee WHERE DEALER_CODE = '");
         sb.append(FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
         sb.append("  AND IS_VALID=12781001  ) C   on C.EMPLOYEE_NO=A.PRINCIPAL  ");
//         sb.append("LEFT JOIN(SELECT DEALER_CODE,UNIT_CODE,UNIT_NAME FROM(   ");
//         sb.append("SELECT tu.DEALER_CODE,tu.UNIT_CODE,tu.UNIT_NAME FROM tm_unit tu INNER JOIN tm_tool_stock tts  ");
//         sb.append(" ON   tu.DEALER_CODE=tts.DEALER_CODE AND tu.UNIT_CODE=tts.UNIT_CODE      ");
//         sb.append(")vm_unit_stock WHERE DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"') D  ON D.UNIT_CODE=a.UNIT_CODE  ");
         sb.append( "LEFT JOIN tm_unit D ON D.UNIT_CODE=A.UNIT_CODE  WHERE A.DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
        List<Object> params =new ArrayList<Object>();
            for (int i = 0; i < params.size(); i++) { 
                if (params.get(i).equals(FrameworkUtil.getLoginInfo().getDealerCode())) { 
                    params.remove(i); 
                    i--; 
                } 
            }
            List<Map> list = DAOUtil.findAll(sb.toString(), params);
            return list;
    }
    /**
     * 根据toolCode
    * @author yujiangheng
    * @date 2017年4月17日
    * @param listToolDefineDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.ToolDefineService#saveAll(java.util.List)
     */
    @Override
    public void saveAll( List<ToolDefineDTO> listToolDefineDTO) throws ServiceBizException {
        for(ToolDefineDTO toolDefineDTO:listToolDefineDTO){
          //校验传入参数
            if(StringUtils.isNullOrEmpty(toolDefineDTO.getToolCode())){
                throw new ServiceBizException("工具代码不能为空");
            }
            if(StringUtils.isNullOrEmpty(toolDefineDTO.getToolName())){
                throw new ServiceBizException("工具名称不能为空");
            }
            System.out.println(toolDefineDTO.toString());
            ToolDefinePO toolDefinePO=
                    ToolDefinePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),toolDefineDTO.getToolCode());
            //判断是更新还是插入
           if(StringUtils.isNullOrEmpty(toolDefinePO)){
               ToolDefinePO toolDefine=new ToolDefinePO();
               //执行插入语句
               setToolDefineDTO(toolDefine,toolDefineDTO);
            
               toolDefine.saveIt();
            }else  if(!StringUtils.isNullOrEmpty(toolDefinePO)&&"X".equals(toolDefineDTO.getFlag())){
              //执行更新语句
                setToolDefineTO(toolDefinePO,toolDefineDTO);
             
             toolDefinePO.saveIt();
          }
        }
    }
    private void setToolDefineTO(ToolDefinePO toolDefinePO, ToolDefineDTO toolDefineDTO) {
        toolDefinePO.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        toolDefinePO.setString("TOOL_CODE",toolDefineDTO.getToolCode());
        toolDefinePO.setString("TOOL_NAME",toolDefineDTO.getToolName());
        toolDefinePO.setString("TOOL_SPELL",toolDefineDTO.getToolSpell());
        toolDefinePO.setString("TOOL_TYPE_CODE",toolDefineDTO.getToolTypeCode());
        toolDefinePO.setString("UNIT_CODE",toolDefineDTO.getUnitCode());
        toolDefinePO.setString("POSITION",toolDefineDTO.getPosition());
        toolDefinePO.setString("PRINCIPAL",toolDefineDTO.getPrincipal());
      
        toolDefinePO.setString("DATE_VALIDITY", toolDefineDTO.getEndDateValidity());
        toolDefinePO.setString("CAPITAL_ASSERTS_MANAGE_NO",toolDefineDTO.getCapitalAssertsManageNo());
        
      toolDefinePO.setFloat("LEND_QUANTITY", toolDefineDTO.getLendQuantity());
      toolDefinePO.setFloat("STOCK_QUANTITY", toolDefineDTO.getStockQuantity());
      toolDefinePO.setFloat("DATE_VALIDITY",toolDefineDTO.getDateValidity());
        
    }
    private void setToolDefineDTO(ToolDefinePO toolDefinePO, ToolDefineDTO toolDefineDTO) {
        toolDefinePO.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        toolDefinePO.setString("TOOL_CODE",toolDefineDTO.getToolCode());
        toolDefinePO.setString("TOOL_NAME",toolDefineDTO.getToolName());
        toolDefinePO.setString("TOOL_SPELL",toolDefineDTO.getToolSpell());
        toolDefinePO.setString("TOOL_TYPE_CODE",toolDefineDTO.getToolTypeCode());
        toolDefinePO.setString("UNIT_CODE",toolDefineDTO.getUnitCode());
        toolDefinePO.setString("POSITION",toolDefineDTO.getPosition());
        toolDefinePO.setString("PRINCIPAL",toolDefineDTO.getPrincipal());
      
        toolDefinePO.setString("DATE_VALIDITY", toolDefineDTO.getEndDateValidity());
        toolDefinePO.setString("CAPITAL_ASSERTS_MANAGE_NO",toolDefineDTO.getCapitalAssertsManageNo());
        
      toolDefinePO.setFloat("LEND_QUANTITY", toolDefineDTO.getLendQuantity());
      toolDefinePO.setFloat("STOCK_QUANTITY", toolDefineDTO.getStockQuantity());
      toolDefinePO.setFloat("DATE_VALIDITY",toolDefineDTO.getDateValidity());
    }
}
