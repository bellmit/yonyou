/**
 * 
 */
package com.yonyou.dms.part.controller.basedata;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesQuotePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.CommonErrorConstant;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.service.basedata.SalesPartService;
import com.yonyou.dms.part.service.basedata.SalesQuoteService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
* TODO description
* @author chenwei
* @date 2017年5月10日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ttSalesQuote")
@SuppressWarnings("rawtypes")
public class SalesQuoteController extends BaseController {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(SalesQuoteController.class);
    @Autowired
    private SalesQuoteService   salesQuoteService;
    
    @Autowired
    private SalesPartService   salesPartService;

    @Autowired
    private CommonNoService     commonNoService;

    @Autowired
    private OperateLogService   operateLogService;
    
    @Autowired
    private ExcelGenerator        excelService;

    /**
     * 查询partInfo信息
     * 
     * @author chenwei
     * @date 2017年5月3日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/SearchSalesQuote", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllSalesQuoteInfo(@RequestParam Map<String, String> queryParam) {
        return salesQuoteService.findAllSalesQuoteInfo(queryParam);
    }

    /**
     * 检查锁行，如果锁住则返回提示，如果未锁住，则锁住
     * 
     * @author chenwei
     * @date 2017年5月3日
     * @param quoteNo 销售报价单号
     * @return 查询结果
     */
    @RequestMapping(value = "/checkLockSalesQuote/{quoteNo}", method = RequestMethod.GET)
    @ResponseBody
    public String checkLockSalesQuote(@PathVariable("quoteNo") String quoteNo) {
        if (StringUtils.isNullOrEmpty(quoteNo)) return "0";
        String lockUser = salesQuoteService.checkLockSalesQuote(quoteNo);
        if (!StringUtils.isNullOrEmpty(lockUser)) {
            return lockUser;
        }
        int flag = Utility.updateByLocker("TT_SALES_QUOTE", FrameworkUtil.getLoginInfo().getUserId().toString(),
                                          "SALES_QUOTE_NO", quoteNo, "LOCK_USER");
        if (flag <= 0) {
            throw new ServiceBizException("单号[" + quoteNo + "]加锁失败!");
        }
        return "1";
    }
    
    /**
     * 查询配件销售单
    * TODO description
    * @author chenwei
    * @date 2017年5月8日
    * @param queryParam
    * @return
     */
    @RequestMapping(value = "/QueryPartSalesSlip", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QueryPartSalesSlip(@RequestParam Map<String, String> queryParam) {
        return salesQuoteService.QueryPartSalesSlip(queryParam);
    }
    
    /**
     * 检查锁行，如果锁住则返回提示，如果未锁住，则锁住
     * 
     * @author chenwei
     * @date 2017年5月8日
     * @param partNo 销售单号
     * @return 查询结果
     */
    @RequestMapping(value = "/checkLockSalesPart/{partNo}", method = RequestMethod.GET)
    @ResponseBody
    public String checkLockSalesPart(@PathVariable("partNo") String partNo) {
        if (StringUtils.isNullOrEmpty(partNo)) return "0";
        String lockUser = salesQuoteService.checkLockSalesPart(partNo);
        if (!StringUtils.isNullOrEmpty(lockUser)) {
            return lockUser;
        }
        int flag = Utility.updateByLocker("TT_SALES_PART", FrameworkUtil.getLoginInfo().getUserId().toString(),
                                          "SALES_PART_NO", partNo, "LOCK_USER");
        if (flag <= 0) {
            throw new ServiceBizException("销售单号[" + partNo + "]加锁失败!");
        }
        return "1";
    }
    
    /**
     * 导入销售单页面 精确查询配件销售单明细信息
     * 
     * @author chenwei
     * @date 2017年5月8日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/deleteSalesQuote/{quoteNo}/{itemId}", method = RequestMethod.DELETE)
    @ResponseBody
    public int deleteSalesQuote(@PathVariable("quoteNo") String quoteNo, @PathVariable("itemId") String itemId) {
        if(StringUtils.isNullOrEmpty(quoteNo) || StringUtils.isNullOrEmpty(itemId))return 0;
        

        return 1;
    }
    
    /**
     * 导入销售单页面 精确查询配件销售单明细信息
     * 
     * @author chenwei
     * @date 2017年5月8日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartSalesItem/{salesPartNo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPartSalesItem(@PathVariable("salesPartNo") String salesPartNo) {
        Map<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("salesPartNo", salesPartNo);
        try {
            double amount = Utility.add("1",Utility.getPartRate(FrameworkUtil.getLoginInfo().getDealerCode()) );
            queryParam.put("amount", String.valueOf(amount));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return salesQuoteService.queryPartSalesItem(queryParam);
    }
    
    @RequestMapping(value="/owner",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto QueryOwnerByNoOrSpell(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
        PageInfoDto pageInfoDto = salesQuoteService.QueryOwnerByNoOrSpell(queryParam);
        return pageInfoDto;
    }

    /**
     * 查询partInfo信息
     * 
     * @author chenwei
     * @date 2017年5月3日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartStockItem", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPartStockItem(@RequestParam Map<String, String> queryParam) {
        return salesQuoteService.queryPartStockItem(queryParam);
    }

    /**
     * 1新增配件页面 查询配件库存列表
     * 
     * @author chenwei
     * @date 2017年5月5日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartStock", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartStock(@RequestParam Map<String, String> queryParam) {
        return salesQuoteService.queryPartStock(queryParam);
    }

    /**
     * 2新增配件页面 精确查询配件库存信息
     * 
     * @author chenwei
     * @date 2017年5月6日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartInfo/{partNo}/{storageCode}/{partInfix}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfo(@PathVariable("partNo") String partNo,
                                     @PathVariable("storageCode") String storageCode,
                                     @PathVariable("partInfix") String partInfix) {
        Map<String, String> queryParam = new HashMap<String, String>();
        queryParam.put("partNo", partNo);
        queryParam.put("storageCode", storageCode);
        queryParam.put("partInfix", partInfix);
        return salesQuoteService.queryPartInfo(queryParam);
    }
    
    /**
     * 根据code 修改报价单为已完成状态    
    * TODO description
    * @author chenwei
    * @date 2017年5月8日
    * @param troubleCode
    * @param troubleDescDTO
    * @param uriCB
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value = "/changeQuoteIsfinished", method = RequestMethod.PUT)
    @ResponseBody
    public void changeQuoteIsfinished(@RequestBody TtSalesQuoteDTO ttSalesQuoteDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        salesQuoteService.updateTtSalesQuote(ttSalesQuoteDTO.getSalesQuoteNo(), ttSalesQuoteDTO);
    }
    
   /**
    * 导出报价单excel
   * TODO description
   * @author chenwei
   * @date 2017年5月9日
   * @param queryParam
   * @param request
   * @param response
   * @throws Exception
    */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportCustomerRecord(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        List<Map> resultList = salesQuoteService.queryPartStockItem(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_配件销售报价", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME", "仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
        exportColumnList.add(new ExcelExportColumn("UNIT_CODE", "单位"));
        exportColumnList.add(new ExcelExportColumn("OUT_PRICE", "销售单价"));
        exportColumnList.add(new ExcelExportColumn("OUT_QUANTITY", "销售数量"));
        exportColumnList.add(new ExcelExportColumn("OUT_AMOUNT", "销售金额"));
        
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_配件销售报价.xls", request, response);
        operateLogService.recordOperateLog("【" + FrameworkUtil.getLoginInfo().getDealerShortName()+"_配件销售报价.xls】界面执行了导出", Integer.valueOf(DictCodeConstants.DICT_ASCLOG_SYSTEM_MANAGE));
    }
    
    /**
     * 导出报价单
     * 
     * @author 陈伟
     * @date 2017年5月9日
     * @param salesQuoteDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/partQuoteToSale", method = RequestMethod.POST)
    @ResponseBody
    public String partQuoteToSale(@RequestBody TtSalesQuoteDTO salesQuoteDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        Map   quote = new HashMap();
        try {
            boolean isOk = false;
            if(StringUtils.isNullOrEmpty(salesQuoteDTO.getSalesQuoteNo())) return "1";
            String quoteNo = salesQuoteDTO.getSalesQuoteNo().toString();
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            Map<String, String> map = new HashMap<String, String>();
            map.put("quoteNo", salesQuoteDTO.getSalesQuoteNo());
            List<Map> mylist = salesQuoteService.findSalesQuoteList(map);
            if (mylist == null || mylist.size() > 0)return "1";
                quote = (Map) mylist.get(0);
            String   salesPartNo = commonNoService.getSystemOrderNo(CommonConstants.SRV_XSDH);
            // 创建一条配件销售单数据
            TtSalesPartPO  sales = new TtSalesPartPO();
            sales.setString("CUSTOMER_CODE", quote.get("CUSTOMER_CODE"));
            sales.setString("CUSTOMER_NAME", quote.get("CUSTOMER_NAME"));
            sales.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
            sales.setDouble("SALES_PART_AMOUNT", quote.get("OUT_AMOUNT"));
            sales.setString("SALES_PART_NO", salesPartNo);
            sales.setInteger("BALANCE_STATUS", Utility.getInt(DictCodeConstants.DICT_IS_NO));
            salesPartService.insertTtSalesQuotePO(sales);
         // 向配件销售单明细表里创建多条记录
            Map<String, Object>   quoteItemCon = new HashMap<String, Object>();
            Map   quoteItem = new HashMap();
            TtSalesPartItemPO  salesItem = null;
            quoteItemCon.put("quoteNo", quoteNo);
            quoteItemCon.put("dKey", CommonConstants.D_KEY);
            List<Map> itemList = salesQuoteService.findSalesQuoteItemList(quoteItemCon);
            if (itemList != null && itemList.size() > 0){
                for(int i= 0; i < itemList.size();i++){
                    quoteItem = itemList.get(i);
                    /**
                     * 停用的配件不能转销售单
                     */
                    Map<String, Object> partStockPO=new HashMap<String, Object>();
                    partStockPO.put("partNo", quoteItem.get("PART_NO"));
                    partStockPO.put("dKey", CommonConstants.D_KEY);
                    partStockPO.put("partName", quoteItem.get("PART_NAME"));
                    List<Map> list = salesQuoteService.findTmPartStockList(partStockPO);
                    if(list != null && list.size() > 0){
                        Map stock = list.get(0);
                        if(DictCodeConstants.DICT_IS_YES.equals(stock.get("PART_STATUS").toString())){
                            throw new ServiceBizException(CommonErrorConstant.MSG_ERROR_PART_NOT_SALES);
                        }
                    }
                    salesItem = new TtSalesPartItemPO();
                    salesItem.setString("CHARGE_PARTITION_CODE", quoteItem.get("CHARGE_PARTITION_CODE"));
                    salesItem.setFloat("DISCOUNT", 1F);
                    salesItem.setString("DEALER_CODE", dealerCode);
                    salesItem.setString("PART_BATCH_NO", quoteItem.get("PART_BATCH_NO"));
                    salesItem.setDouble("PART_COST_AMOUNT", quoteItem.get("COST_AMOUNT"));
                    salesItem.setDouble("PART_COST_PRICE", quoteItem.get("COST_PRICE"));
                    salesItem.setString("PART_NAME", quoteItem.get("PART_NAME"));
                    salesItem.setString("PART_NO", quoteItem.get("PART_NO"));
                    salesItem.setDouble("PART_QUANTITY", quoteItem.get("OUT_QUANTITY"));
                    salesItem.setDouble("SALES_AMOUNT", quoteItem.get("OUT_AMOUNT"));//配件销售金额     DMS-11651
                    salesItem.setDouble("PART_SALES_AMOUNT", quoteItem.get("OUT_AMOUNT"));//配件收费金额    
                    salesItem.setDouble("PART_SALES_PRICE", quoteItem.get("OUT_PRICE"));
                    salesItem.setString("SALES_PART_NO", salesPartNo);
                    salesItem.setString("STORAGE_CODE", quoteItem.get("STORAGE_CODE"));
                    salesItem.setString("STORAGE_POSITION_CODE", quoteItem.get("STORAGE_POSITION_CODE"));
                    salesItem.setString("UNIT_CODE", quoteItem.get("UNIT_CODE"));
                    salesItem.setInteger("IS_FINISHED", Utility.getInt(DictCodeConstants.DICT_IS_NO));
                    salesItem.setInteger("PRICE_TYPE", quoteItem.get("PRICE_TYPE"));//价格类型
                    salesItem.setDouble("PRICE_RATE", quoteItem.get("PRICE_RATE"));//价格系数
                    isOk = salesPartService.insertTtSalesQuoteItemPO(salesItem); 
                    if(!isOk) return "0";
                }
            }
            return salesPartNo;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    /**
     * 报价单保存/作废
     * 
     * @author 陈伟
     * @date 2017年3月24日
     * @param troubleDescDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public int addSalesQuote(@RequestBody TtSalesQuoteDTO salesQuoteDTO,UriComponentsBuilder uriCB) throws ServiceBizException {
        // salesQuoteService.insertTtSalesQuotePO(salesQuoteDTO);
        try {
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            if(StringUtils.isNullOrEmpty(dealerCode)){
                throw new ServiceBizException(CommonErrorConstant.MSG_ERROR_LOST_KEY);
            }
            Long userId = FrameworkUtil.getLoginInfo().getUserId();
            String EmployeeCode = FrameworkUtil.getLoginInfo().getEmployeeNo();
            List<TtSalesQuoteItemDTO> itemList = salesQuoteDTO.getItemList();
            if (itemList != null && itemList.size() > 0) {
                String errPrice = "";
                errPrice = checkPartLimitPrice(dealerCode, itemList, null);

                if (!errPrice.equals("")) {
                    throw new ServiceBizException(CommonErrorConstant.MSG_ERROR_SAVE);
                }
            }
            String salesNumber = "";
            TtSalesQuotePO quote = new TtSalesQuotePO();
            String id = "";
            if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getUpdateStatus()) && "A".equals(salesQuoteDTO.getUpdateStatus().trim())) {
                id = commonNoService.getSystemOrderNo(CommonConstants.SRV_CBTZDH);
                quote.setDouble("COST_AMOUNT", salesQuoteDTO.getCostAmount());
                quote.setString("CUSTOMER_CODE", salesQuoteDTO.getCustomerCode());
                quote.setString("CUSTOMER_NAME", salesQuoteDTO.getCustomerName());
                quote.setString("DEALER_CODE", dealerCode);
                quote.setDate("FINISHED_DATE", salesQuoteDTO.getFinishedDate());
                quote.setString("HANDLER", salesQuoteDTO.getHandler());
                quote.setString("LOCK_USER", salesQuoteDTO.getLockUser());
                quote.setDouble("OUT_AMOUNT", salesQuoteDTO.getOutAmount());
                quote.setString("POPEDOM_ORDER_NO", salesQuoteDTO.getPopedomOrderNo());
                quote.setDate("SALES_QUOTE_DATE", salesQuoteDTO.getSalesQuoteDate());
                quote.setString("SALES_QUOTE_NO", id);
                quote.setInteger("IS_FINISHED", DictCodeConstants.DICT_IS_NO);
                // POFactory.insert(conn, quote);
                salesQuoteService.insertTtSalesQuotePO(quote);
                salesNumber = id;
            }
            if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getUpdateStatus()) && "D".equals(salesQuoteDTO.getUpdateStatus().trim())) {
                if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getSalesQuoteNo())) {
                    TtSalesQuoteItemDTO quoteItemCon = new TtSalesQuoteItemDTO();
                    quoteItemCon.setSalesQuoteNo(salesQuoteDTO.getSalesQuoteNo());
                    quoteItemCon.setDKey(CommonConstants.D_KEY);
                    salesQuoteService.deleteTtSalesQuoteItem(quoteItemCon);
                    TtSalesQuoteDTO quoteCon = new TtSalesQuoteDTO();
                    quoteCon.setSalesQuoteNo(salesQuoteDTO.getSalesQuoteNo());
                    quoteCon.setdKey(CommonConstants.D_KEY);
                    String contentsa = "配件销售报价单删除：报价单号[" + salesQuoteDTO.getSalesQuoteNo() + "]";
                    operateLogService.recordOperateLog(contentsa,
                                                       Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE));
                    salesQuoteService.deleteTtSalesQuote(quoteCon);
                }
            }
            if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getUpdateStatus()) && "U".equals(salesQuoteDTO.getUpdateStatus().trim()))
            {
                if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getSalesQuoteNo()))
                {   
                    salesQuoteService.updateTtSalesQuote(salesQuoteDTO.getSalesQuoteNo(), salesQuoteDTO);
                    salesNumber = salesQuoteDTO.getSalesQuoteNo();
                }
            }
            if (itemList != null && itemList.size() > 0){
             // 循环地初始化
                for (int i = 0; i < itemList.size(); i++){
                    TtSalesQuoteItemDTO item = (TtSalesQuoteItemDTO)itemList.get(i);
                    if("A".equals(item.getUpdateStatus())){
                        if (!StringUtils.isNullOrEmpty(salesQuoteDTO.getUpdateStatus()) && "A".equals(salesQuoteDTO.getUpdateStatus().trim())){
                            item.setSalesQuoteNo(id);
                        }else{
                            item.setSalesQuoteNo(salesQuoteDTO.getSalesQuoteNo());
                            salesQuoteService.insertTtSalesQuoteItemPO(item);
                            //linked.add(Utility.getReturnDynaBeanNoVer("ITEM_ID", quoteItem.getItemId()
                            //                                          .toString(), Utility.getInt(recordId[i])));
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(item.getUpdateStatus()) && "D".equals(item.getUpdateStatus().trim()))
                    {
                        if (!StringUtils.isNullOrEmpty(item.getItemId()))
                        {
                            item.setDKey(CommonConstants.D_KEY);
                            salesQuoteService.deleteTtSalesQuoteItem(item);
                            String sqlStr = " 1 = 1 ";
                            StringBuilder sqlWhere = new StringBuilder(" POPEDOM_ORDER_NO = ").append(salesQuoteDTO.getPopedomOrderNo());
                            sqlWhere.append(" AND D_KEY = ").append(CommonConstants.D_KEY);
                            salesQuoteService.modifyTtSalesQuoteByParams(sqlStr, sqlWhere.toString(), null);
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(item.getUpdateStatus()) && "U".equals(item.getUpdateStatus().trim()))
                    {
                        if (!StringUtils.isNullOrEmpty(item.getItemId()))
                        {
                            salesQuoteService.updateTtSalesQuoteItem(item.getItemId(), item);
                        }

                    }
                }
            }
            //actionContext.setStringValue("SALES_NO", salesNumber);
            //if (linked != null && linked.size() > 0)
            //{
            //    actionContext.setArrayValue("TT_SALES_QUOTE_ITEM", linked.toArray());
            //}
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    

    /**
     * 功能描述：判断配件列表的价格是否正确 返回多个配件
     * 
     * @param conn
     * @param entityCode
     * @param partNo
     * @param salePrice
     * @return
     * @throws Exception
     */
    public String checkPartLimitPrice(String dealerCode, List<TtSalesQuoteItemDTO> itemList,
                                      String[] isFinish) throws Exception {

        String isCheck = Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_IS_LIMIT_PART_PRICE));
        logger.debug("开关 :" + isCheck);
        String returnPartStr = "";
        if (!DictCodeConstants.DICT_IS_YES.equals(isCheck)) {
            returnPartStr = "";
        } else {
            if (itemList != null && itemList.size() > 0) {

                if (isFinish != null && isFinish.length > 0) {
                    for (int i = 0; i < itemList.size(); i++) {
                        TtSalesQuoteItemDTO item = itemList.get(i);
                        if (!"D".equals(item.getUpdateStatus()) && !isFinish[i].equals(DictCodeConstants.DICT_IS_YES)
                            && this.checkPartPriceIsHigh(dealerCode, item.getPartNo(),
                                                         Utility.getFloat(item.getOutPrice().toString())) == 0) {
                            returnPartStr = returnPartStr + " " + item.getPartNo() + " ";
                        }
                    }
                } else {
                    for (int i = 0; i < itemList.size(); i++) {
                        TtSalesQuoteItemDTO item = itemList.get(i);
                        if (!"D".equals(item.getUpdateStatus())
                            && this.checkPartPriceIsHigh(dealerCode, item.getPartNo(),
                                                         Utility.getFloat(item.getOutPrice().toString())) == 0) {
                            returnPartStr = returnPartStr + " " + item.getPartNo() + " ";
                        }
                    }
                }
            }
        }
        if (returnPartStr.equals("")) {
            return "";
        } else {
            return " 以下配件价格不正确 :" + returnPartStr + "  请重新操作!";
        }
    }

    /**
     * 功能描述：判断配件销售的价格是否超过建议销售价
     * 
     * @param conn
     * @param entityCode
     * @param partNo
     * @param storageCode
     * @return
     * @throws Exception
     */
    public int checkPartPriceIsHigh(String dealerCode, String partNo, float aSalePrice) throws Exception {

        Map<String, Object> partInfoPOCon = new HashMap<String, Object>();
        partInfoPOCon.put("partNo", partNo);
        partInfoPOCon.put("dKey", CommonConstants.D_KEY);
        List<Map> partInfoList = salesQuoteService.queryPartInfoList(partInfoPOCon);

        if (partInfoList != null && partInfoList.size() > 0) {
            // TmPartInfoPO apartInfoPO = new TmPartInfoPO();
            Map apartInfoPO = (Map) partInfoList.get(0);

            logger.debug("配件销售单价 : " + aSalePrice);
            logger.debug("销售限价 :" + apartInfoPO.get("LIMIT_PRICE"));
            logger.debug("下发配件 :" + apartInfoPO.get("DOWN_TAG"));
            double aLimitPrice = 0L;
            if (!StringUtils.isNullOrEmpty(apartInfoPO.get("LIMIT_PRICE"))) {
                aLimitPrice = Double.valueOf(apartInfoPO.get("LIMIT_PRICE").toString());
            }
            logger.debug("  调整前 :" + (aSalePrice - aLimitPrice));
            logger.debug("配件销售单价*100 : " + Math.round(aSalePrice * 100));
            logger.debug("销售限价*100 : " + Math.round(aLimitPrice * 100));
            logger.debug("  调整后 :" + (Math.round(aSalePrice * 100) - Math.round(aLimitPrice * 100)));
            if (!StringUtils.isNullOrEmpty(apartInfoPO.get("LIMIT_PRICE"))
                && DictCodeConstants.DICT_IS_YES.equals(apartInfoPO.get("LIMIT_PRICE").toString())
                && ((Math.round(aSalePrice * 100) - Math.round(aLimitPrice * 100)) > 10) && (aLimitPrice > 0.001)) { 
             // add by  sf 2011-01-06 这样写不是很严密,但是业务上没有什么问题  
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }
    }

}
