package com.yonyou.dms.repair.service.basedata;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;
import com.yonyou.dms.common.domains.PO.basedata.ToolBuyItemPO;
import com.yonyou.dms.common.domains.PO.basedata.ToolBuyPO;
import com.yonyou.dms.common.domains.PO.basedata.ToolDefinePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.ListToolBuyItemDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.ToolBuyItemDTO;
@Service
public class ToolBuyServiceImpl implements ToolBuyService{
    /**
     * 根据dealerCode，buyNo，isFinished 检索工具购入信息
    * @author yujiangheng
    * @date 2017年4月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#SearchToolBuyInfo(java.util.Map)
     */
    @Override
    public PageInfoDto SearchToolBuyInfo(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql(queryParam,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 购入单号查询sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月20日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
        StringBuilder sb=new StringBuilder( " SELECT  A.BUY_NO, A.DEALER_CODE, A.CUSTOMER_CODE, B.CUSTOMER_NAME, ");
               sb.append("A.BUY_DATE, A.IS_FINISHED,A.HANDLER, C.EMPLOYEE_NO,C.EMPLOYEE_NAME FROM TT_TOOL_BUY A ");
               sb.append( " LEFT JOIN ( "+CommonConstants.VM_PART_CUSTOMER);
               sb.append( " )  B ON B.DEALER_CODE = A.DEALER_CODE AND B.CUSTOMER_CODE = A.CUSTOMER_CODE ");
               sb.append( "LEFT JOIN tm_employee C ON C.EMPLOYEE_NO=A.HANDLER  ");
               sb.append(" WHERE A.DEALER_CODE = "+FrameworkUtil.getLoginInfo().getDealerCode());
               sb.append( " AND A.IS_FINISHED ='12781002'" );
               if(!StringUtils.isNullOrEmpty(queryParam.get("buyNo"))){
                   sb.append(" and   A.BUY_NO LIKE  ? ");
                   params.add("%"+queryParam.get("buyNo")+"%");
               }
        return sb.toString();
    }
    /**
     * 获取经手人下拉选
    * @author yujiangheng
    * @date 2017年4月20日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#getAllSelect()
     */
    @Override
    public List<Map> getAllSelect() throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,EMPLOYEE_NO ,EMPLOYEE_NAME ,");
        sb.append("CONCAT(EMPLOYEE_NAME,' | ', EMPLOYEE_NO) NO_NAME FROM tm_employee WHERE 1=1  ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sb.toString(), params);
        return result;
    }
    /**
     * 根据dealerCode，byNo，isFinished 检索工具明细信息
    * @author yujiangheng
    * @date 2017年4月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#SearchToolBuyItem(java.util.Map)
     */
    @Override
    public PageInfoDto SearchToolBuyItem(String buyNo) throws ServiceBizException {
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        String sql = getQuerySql2(buyNo,params);//构建查询语句
        PageInfoDto pageInfoDto=DAOUtil.pageQuery(sql, params);
       return pageInfoDto;
    }
    /**
     * 工具购入明细查询sql
    * TODO description
    * @author yujiangheng
    * @date 2017年4月20日
    * @param queryParam
    * @param params
    * @return
     */
    private String getQuerySql2(String buyNo, List<Object> params) {
        StringBuilder sb = new StringBuilder("SELECT A.DEALER_CODE, A.ITEM_ID, A.BUY_NO, A.TOOL_CODE,A.TOOL_NAME, A.UNIT_CODE, ");
        sb.append( " B.UNIT_NAME, A.UNIT_PRICE, A.QUANTITY, A.AMOUNT   FROM TT_TOOL_BUY_ITEM A    LEFT JOIN tm_unit  B ON B.UNIT_CODE=A.UNIT_CODE ");
        sb.append("AND BUY_NO IN (SELECT BUY_NO FROM TT_TOOL_BUY WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' )");
        sb.append(" WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb.append(" AND A.BUY_NO IN ( SELECT BUY_NO FROM TT_TOOL_BUY  ");
        sb.append("   WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"')  ");
        if(!StringUtils.isNullOrEmpty( buyNo)){
            sb.append(" and   A.BUY_NO=? ");
            params.add(buyNo);
        }
        return sb.toString();
    }
    /**
     * 导出
    * @author yujiangheng
    * @date 2017年4月22日
    * @param buyNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#queryToExport(java.lang.String)
     */
    @Override
    public List<Map> queryToExport(String buyNo) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT A.DEALER_CODE, A.ITEM_ID, A.BUY_NO, A.TOOL_CODE,A.TOOL_NAME, A.UNIT_CODE, ");
        sb.append( " B.UNIT_NAME, A.UNIT_PRICE, A.QUANTITY, A.AMOUNT   FROM TT_TOOL_BUY_ITEM A    LEFT JOIN tm_unit  B ON B.UNIT_CODE=A.UNIT_CODE ");
        sb.append("AND BUY_NO IN (SELECT BUY_NO FROM TT_TOOL_BUY WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' )");
        sb.append(" WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb.append(" AND A.BUY_NO IN ( SELECT BUY_NO FROM TT_TOOL_BUY  ");
        sb.append("   WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"')  ");
        List<Object> params =new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty( buyNo)){
            sb.append(" and   A.BUY_NO=? ");
            params.add(buyNo);
        }
       // List<Object> params =new ArrayList<Object>();
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
     * 入账操作
    * @author yujiangheng
    * @date 2017年4月22日
    * @param buyNo
    * @throws ServiceBizException
    * @throws ParseException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#AddAccount(java.lang.String)
     */
    @Override
    public void AddAccount(String buyNo) throws ServiceBizException, ParseException {
            //参数：BUY_NO:buyNo、IS_FINISHED:12781001、IN_OUT_TYPE:12161001
     // 执行业务操作 -- 更新购买单状态
        ToolBuyItemPO toolBuyPO=ToolBuyItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),buyNo);
       //设置更新的对象属性
        toolBuyPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        toolBuyPO.setInteger("IS_FINISHED", 12781001);
        //设置当前时间
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//小写的mm表示的是分钟  
        Calendar cdate = Calendar.getInstance();
        Date now = sdf.parse(sdf.format(cdate.getTime()));
       // System.out.println(sdf.format(endDate));
        toolBuyPO.setDate("BUY_DATE",now);
        toolBuyPO.saveIt();
        System.out.println("------------------------------------------------------1111111111111");
// 执行业务操作 -- 创建流水帐        
        // 执行业务操作 -- 工具采购单号查询
        //参数：String entityCode, String buyNo,isFinished, Timestamp beginDate, Timestamp endDate,
        Map<String,String> queryParam=new HashMap<String, String>();//传入的参数
        List<Object> params=new ArrayList<Object>();//定义需要添加的查询参数
        queryParam.put("BUY_NO", buyNo);
        queryParam.put("IS_FINISHED", "12781001");
        StringBuffer sb=new StringBuffer( " SELECT  A.BUY_NO, A.DEALER_CODE, A.CUSTOMER_CODE, B.CUSTOMER_NAME, ");
        sb.append("A.BUY_DATE, A.IS_FINISHED,A.HANDLER, C.EMPLOYEE_NO,C.EMPLOYEE_NAME FROM TT_TOOL_BUY A ");
        sb.append( " LEFT JOIN ( "+CommonConstants.VM_PART_CUSTOMER);
        sb.append( " )  B ON B.DEALER_CODE = A.DEALER_CODE AND B.CUSTOMER_CODE = A.CUSTOMER_CODE ");
        sb.append( "LEFT JOIN tm_employee C ON C.EMPLOYEE_NO=A.HANDLER  ");
        sb.append(" WHERE A.DEALER_CODE = "+FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append( " AND A.IS_FINISHED ='"+queryParam.get("IS_FINISHED")+"'" );
        Utility.sqlToDate(sb, "", "", "BUY_DATE", "A");
        if(!StringUtils.isNullOrEmpty(queryParam.get("buyNo"))){
            sb.append(" and   A.BUY_NO LIKE  ? ");
            params.add("%"+queryParam.get("buyNo")+"%");
        }
        List<Map> allToolBuy=DAOUtil.findAll(sb.toString(), params);//根据buyNo查询已经执行的购买单号信息
        System.out.println("------------------------------------------------------22222222");
        //执行业务操作 -- 工具采购查询   根据工厂编号，购入单号，状态，检索工具明细信息
        List<Object> params1=new ArrayList<Object>();//定义需要添加的查询参数
        StringBuilder sb1 = new StringBuilder("SELECT A.DEALER_CODE, A.ITEM_ID, A.BUY_NO, A.TOOL_CODE,A.TOOL_NAME, A.UNIT_CODE, ");
        sb1.append( " B.UNIT_NAME, A.UNIT_PRICE, A.QUANTITY, A.AMOUNT   FROM TT_TOOL_BUY_ITEM A    LEFT JOIN tm_unit  B ON B.UNIT_CODE=A.UNIT_CODE ");
        sb1.append("AND BUY_NO IN (SELECT BUY_NO FROM TT_TOOL_BUY WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' )");
        sb1.append(" WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb1.append(" AND A.BUY_NO IN ( SELECT BUY_NO FROM TT_TOOL_BUY  ");
        sb1.append("   WHERE DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"')  ");
        sb1.append( " AND A.IS_FINISHED ='"+queryParam.get("IS_FINISHED")+"'" );
        Utility.sqlToDate(sb, "", "", "BUY_DATE", "");
        if(!StringUtils.isNullOrEmpty( queryParam.get("buyNo"))){
            sb.append(" and   A.BUY_NO=? ");
            params1.add(buyNo);
        }
        List<Map> allToolBuyItem=DAOUtil.findAll(sb1.toString(), params1);//查询buyNo已经执行的购入工具明细
        System.out.println("------------------------------------------------------3333333333");
        if (allToolBuyItem != null && allToolBuyItem.size() > 0) {
            for (int i = 0; i < allToolBuyItem.size(); i++) {
                
                allToolBuyItem.get(i).get("TOOL_CODE");
                // 1.更新库存 entityCode, String toolCode,       double stockQuantity
          
 ToolDefinePO toolDefinePO=ToolDefinePO.findByCompositeKeys(allToolBuyItem.get(i).get("DEALER_CODE"),allToolBuyItem.get(i).get("TOOL_CODE"));
                        toolDefinePO.setString("DEALER_CODE",allToolBuyItem.get(i).get("DEALER_CODE"));
                        toolDefinePO.setString("TOOL_CODE", allToolBuyItem.get(i).get("TOOL_CODE"));
                        toolDefinePO.setFloat("STOCK_QUANTITY",  allToolBuyItem.get(i).get("QUANTITY"));
                        toolDefinePO.saveIt();
                        System.out.println("------------------------------------------------------44444444444444444444");
                //FLOW_ID,SHEET_NO,ENTITY_CODE,TOOL_CODE,TOOL_NAME,TOOL_TYPE_CODE,UNIT_CODE,
                //STOCK_QUANTITY,IN_QUANTITY,OUT_QUANTITY,IN_OUT_TYPE,HANDLER, CREATE_DATE, CREATE_BY
                
              //2.新增工具流水帐(工具购入)
                if (allToolBuy != null && allToolBuy.size() > 0) {
                    //新增表的字段
//                    FLOW_ID DECIMAL(14,0) NOT NULL,  -- flow_id         流水账id
//                    DEALER_CODE VARCHAR(30) NOT NULL,   -- dealer_code      经销商代码
//                    TOOL_CODE VARCHAR(15),          -- tool_code        工具代码
//                    TOOL_NAME VARCHAR(60),          -- tool_name        工具名称
//                    TOOL_TYPE_CODE VARCHAR(4),      -- tool_type_code   工具类型代码
//                    SHEET_NO VARCHAR(12),       -- sheet_no             sheet页
//                    UNIT_CODE VARCHAR(4),       -- unit_code            工具单位代码
//                    STOCK_QUANTITY DECIMAL(8,2) DEFAULT 0,      -- stock_quantity   库存数量
//                    IN_QUANTITY DECIMAL(8,2),       -- in_quantity      入库数量
//                    OUT_QUANTITY DECIMAL(8,2),      -- out_quantity     出库数量
//                    IN_OUT_TYPE DECIMAL(8,0),       -- in_out_type      出/入库类型
//                    CUSTOMER_NAME VARCHAR(120),     -- customer_name    供应商名称
//                    HANDLER VARCHAR(4),             -- handler      处理人
                    
                    //  参数：dealerCode ,allToolBuyItem[i].getToolCode(),  inOutType:12161001, allToolBuy[0].getHandler(), buyNo, 
                    StringBuilder sql = new StringBuilder(" INSERT INTO TT_TOOL_FLOW (FLOW_ID,SHEET_NO,ENTITY_CODE,CUSTOMER_NAME,TOOL_CODE,TOOL_NAME,");
                    sql.append("TOOL_TYPE_CODE,UNIT_CODE,STOCK_QUANTITY,IN_QUANTITY,OUT_QUANTITY,IN_OUT_TYPE,HANDLER, CREATE_DATE, CREATE_BY)");
                    sql.append(" SELECT D.BUY_NO,A.ENTITY_CODE,F.CUSTOMER_NAME,A.TOOL_CODE,A.TOOL_NAME,A.TOOL_TYPE_CODE,A.UNIT_CODE,");
                    sql.append(" STOCK_QUANTITY,D.QUANTITY,0,12161001,'"+allToolBuy.get(0).get("HANDLER")+"' ,"+now+" , ");
                    sql.append("FROM TM_TOOL_STOCK A, TT_TOOL_BUY_ITEM D,TT_TOOL_BUY E,( "+CommonConstants.VM_PART_CUSTOMER +" )F ");
                    sql.append("WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.DEALER_CODE = D.DEALER_CODE");
                    sql.append(" AND A.TOOL_CODE = D.TOOL_CODE AND D.BUY_NO = '"+buyNo+"' ");
                    sql.append(" AND  D.DEALER_CODE = E.DEALER_CODE AND D.BUY_NO = E.BUY_NO  ");
                    sql.append("AND  E.DEALER_CODE = F.DEALER_CODE AND E.CUSTOMER_CODE = F.CUSTOMER_CODE ");
                    sql.append("AND A.DEALER_CODE =  '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  AND A.TOOL_CODE = '"+allToolBuyItem.get(i).get("TOOL_CODE")+"'");
                    Base.exec(sql.toString());
                    System.out.println("------------------------------------------------------55555555555555555555555");
                }
            }
        }
        
       // return 1;
        // 更新库存
    }
    /**
     * 保存购入工具信息到工具购入明细表中
    * @author yujiangheng
    * @date 2017年4月22日
    * @param buyNo
    * @param listToolBuyDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.basedata.ToolBuyService#saveToolBuy(java.lang.String, java.util.List)
     */
    @Override
    public void saveToolBuy(ListToolBuyItemDTO listToolBuyItemDTO) throws ServiceBizException {
        for(ToolBuyItemDTO toolBuyItemDTO:listToolBuyItemDTO.getdms_table()){
            if(StringUtils.isNullOrEmpty(listToolBuyItemDTO.getBuyNo())){ //工具购入单号不存在
               //工具购入单号不存在，新建工具购入单 
// 取得TT_TOOL_BUY参数  CUSTOMER_CODE：供应商代码、BUY_DATE：购买时间、IS_FINISHED：是否执行、HANDLER：处理人
              //校验传入参数
                if(StringUtils.isNullOrEmpty(listToolBuyItemDTO.getCustomerCode())){
                    throw new ServiceBizException("供应商代码不能为空");
                }
                ToolBuyPO toolBuyPO=new ToolBuyPO();
                toolBuyPO.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                toolBuyPO.setString("BUY_NO",listToolBuyItemDTO.getBuyNo());
                toolBuyPO.setString("CUSTOMER_CODE",listToolBuyItemDTO.getCustomerCode());
                toolBuyPO.setString("BUY_DATE",listToolBuyItemDTO.getBuyDate());
                toolBuyPO.setString("IS_FINISHED",12781002);
                toolBuyPO.setString("HANDLER",listToolBuyItemDTO.getHandler());
                toolBuyPO.saveIt(); //新建工具购入单 
                //将工具购入明细存入该单下
                ToolBuyItemPO toolBuyItem=new ToolBuyItemPO();
                setToolBuyItemPOO(toolBuyItem,toolBuyItemDTO);
                //执行插入语句
                toolBuyItem.saveIt();
                
            }else{
          //工具购入单号存在
            toolBuyItemDTO.setBuyNo(listToolBuyItemDTO.getBuyNo());//将工具购入单号放入实体类对象中
            System.out.println("BuyNo:"+listToolBuyItemDTO.getBuyNo());
            ToolBuyItemPO toolBuyItemPO=  //通过主键获取PO
                    ToolBuyItemPO.findById(toolBuyItemDTO.getItemId());
              //根据PO判断是更新还是插入
             if(StringUtils.isNullOrEmpty(toolBuyItemPO)){ //增加
                 ToolBuyItemPO toolBuyItem=new ToolBuyItemPO();
                 setToolBuyItemPOO(toolBuyItem,toolBuyItemDTO);
                 //执行插入语句
                 toolBuyItem.saveIt();
              }else  if(!StringUtils.isNullOrEmpty(toolBuyItemPO)){//修改
               setToolBuyItemPO(toolBuyItemPO,toolBuyItemDTO);
               //执行插入语句
               toolBuyItemPO.saveIt();
            }
            }
          }
    }
    /**
     * 设置ToolBuyItemPO增加的对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月22日
    * @param toolDefine
    * @param toolBuyItemPO
     */
    private void setToolBuyItemPOO(ToolBuyItemPO toolBuyItem, ToolBuyItemDTO toolBuyItemDTO) {
        toolBuyItem.setInteger("ITEM_ID", toolBuyItemDTO.getItemId());
        toolBuyItem.setString("BUY_NO",toolBuyItemDTO.getBuyNo());
        toolBuyItem.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        toolBuyItem.setString("TOOL_CODE",toolBuyItemDTO.getToolCode());
        toolBuyItem.setString("TOOL_NAME",toolBuyItemDTO.getToolName());
        toolBuyItem.setString("UNIT_CODE",toolBuyItemDTO.getUnitCode());
        toolBuyItem.setDouble("UNIT_PRICE",toolBuyItemDTO.getUnitPrice());
        toolBuyItem.setDouble("QUANTITY",toolBuyItemDTO.getQuantity());
        toolBuyItem.setDouble("AMOUNT",toolBuyItemDTO.getAmount());
    }
    /**
     * 设置ToolBuyItemPO修改的对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月22日
    * @param toolDefine
    * @param toolBuyItemPO
     */
    private void setToolBuyItemPO(ToolBuyItemPO toolBuyItemPO, ToolBuyItemDTO toolBuyItemDTO) {
        toolBuyItemPO.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        toolBuyItemPO.setString("TOOL_CODE",toolBuyItemDTO.getToolCode());
        toolBuyItemPO.setString("TOOL_NAME",toolBuyItemDTO.getToolName());
        toolBuyItemPO.setString("UNIT_CODE",toolBuyItemDTO.getUnitCode());
        toolBuyItemPO.setDouble("UNIT_PRICE",toolBuyItemDTO.getUnitPrice());
        toolBuyItemPO.setDouble("QUANTITY",toolBuyItemDTO.getQuantity());
        toolBuyItemPO.setDouble("AMOUNT",toolBuyItemDTO.getAmount());
    }
    
  
    
}



