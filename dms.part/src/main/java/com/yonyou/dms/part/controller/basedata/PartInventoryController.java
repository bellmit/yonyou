package com.yonyou.dms.part.controller.basedata;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListToolDefineDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ToolDefineDTO;
import com.yonyou.dms.part.service.basedata.PartInventoryService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 配件盘点差异分析
* TODO description
* @author yujiangheng
* @date 2017年5月15日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/partinventories")
public class PartInventoryController extends BaseController{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInventoryController.class);

    @Autowired
    private PartInventoryService pis;

  /*  @Autowired
    private CommonNoService commonNoService;*/
    
    @Autowired
    private ExcelGenerator excelService;//导出接口
    /*
     * 查询导出excel1
     * @author zhengcong
     * @date 2017年3月29日
     */
    @RequestMapping(value="/export1",method = RequestMethod.GET)
    public void excel1(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        String inventoryNo=queryParam.get("INVENTORY_NO");
      //  System.out.println("________________导出1"+inventoryNo);
        List<Map> resultList =pis.queryToExport1(inventoryNo);//查询出的数据
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_盘盈清单", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY","借进数量"));
        exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY","借出数量"));
        exportColumnList.add(new ExcelExportColumn("REAL_STOCK","实际库存"));
        exportColumnList.add(new ExcelExportColumn("CHECK_QUANTITY","清点数量"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_LOSS_QUANTITY","盈亏数量"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_LOSS_AMOUNT","盈亏金额"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价"));
        exportColumnList.add(new ExcelExportColumn("CURRENT_STOCK","当前库存"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_盘盈清单.xls", request, response);
    }
   /**
    * 查询导出excel2
   * TODO description
   * @author yujiangheng
   * @date 2017年5月15日
   * @param queryParam
   * @param request
   * @param response
    */
    @RequestMapping(value="/export2",method = RequestMethod.GET)
    public void excel2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        String inventoryNo=queryParam.get("INVENTORY_NO");
        System.out.println("________________导出2"+inventoryNo);
        List<Map> resultList =pis.queryToExport2(inventoryNo);//查询出的数据
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_盘亏清单", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME","仓库"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","库位"));
        exportColumnList.add(new ExcelExportColumn("PART_NO","配件代码"));
        exportColumnList.add(new ExcelExportColumn("PART_NAME","配件名称"));
        exportColumnList.add(new ExcelExportColumn("BORROW_QUANTITY","借进数量"));
        exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY","借出数量"));
        exportColumnList.add(new ExcelExportColumn("REAL_STOCK","实际库存"));
        exportColumnList.add(new ExcelExportColumn("CHECK_QUANTITY","清点数量"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_LOSS_QUANTITY","盈亏数量"));
        exportColumnList.add(new ExcelExportColumn("PROFIT_LOSS_AMOUNT","盈亏金额"));
        exportColumnList.add(new ExcelExportColumn("COST_PRICE","成本单价"));
        exportColumnList.add(new ExcelExportColumn("CURRENT_STOCK","当前库存"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_盘亏清单.xls", request, response);

        
    }
    
  /**
   *  简要描述:根据条件查询配件盘点单信息
  * TODO description
  * @author yujiangheng
  * @date 2017年5月13日
  * @param queryParam
  * @return
   */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInventory(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto =pis.queryPartInventory(queryParam);
        return pageInfoDto;
    } 
    /**
     * 通过主表 INVENTORY_NO 查询调拨入库明细表数据
    * TODO description
    * @author yujiangheng
    * @date 2017年5月13日
    * @param allocateInNo
    * @return
     */
     @RequestMapping(value = "/profit/{inventoryNo}",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryPartInventoryprofitItems(@PathVariable String inventoryNo) {
         PageInfoDto pageInfoDto = pis.queryPartInventoryprofitItems(inventoryNo);
         return pageInfoDto;
     }
     /**
      * 通过主表 INVENTORY_NO 查询调拨入库明细表数据
     * TODO description
     * @author yujiangheng
     * @date 2017年5月13日
     * @param allocateInNo
     * @return
      */
      @RequestMapping(value = "/loss/{inventoryNo}",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto queryPartInventorylossItems(@PathVariable String inventoryNo) {
          PageInfoDto pageInfoDto = pis.queryPartInventorylossItems(inventoryNo);
          return pageInfoDto;
      }
      
      /**
       *完成功能
      * TODO description
      * @author yujiangheng 
      * @date 2017年4月17日
      * @param listToolDefineDTO
      * @param uriCB
      * @throws ServiceBizException
       */
      @RequestMapping(value = "/achieve/{inventoryNo}", method = RequestMethod.GET)
      @ResponseBody
      @ResponseStatus(HttpStatus.CREATED)
      public void achieve(@PathVariable String inventoryNo,UriComponentsBuilder uriCB) throws Exception{
          System.out.println(inventoryNo);
         pis.achieve( inventoryNo);
     }
      


}
