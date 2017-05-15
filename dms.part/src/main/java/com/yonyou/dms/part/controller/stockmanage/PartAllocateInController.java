package com.yonyou.dms.part.controller.stockmanage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.part.controller.basedata.UnitController;
import com.yonyou.dms.part.domains.DTO.stockmanage.ListPartAllocateInItemDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateInDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateInItemDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartBuyItemImportDto;
import com.yonyou.dms.part.service.stockmanage.PartAllocateInService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 调拨入库
* TODO description
* @author yujiangheng
* @date 2017年4月24日
 */
@Controller
@TxnConn
@RequestMapping("/stockmanage/allocateInOders")
public class PartAllocateInController extends BaseController {
 // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartAllocateInController.class);
    @Autowired
    private PartAllocateInService partAllocateInService;
    @Autowired
    private CommonNoService  commonNoService;
    @Autowired
    private ExcelRead<PartAllocateInItemDto>  excelReadService; //导出接口
    
   /**
    * 查询调拨单
   * TODO description
   * @author yujiangheng
   * @date 2017年4月24日
   * @param queryParam
   * @return
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryAllocateInOrders(@RequestParam Map<String, String> queryParam) {
        PageInfoDto AllocateInNo = partAllocateInService.qryAllocateInOrders(queryParam);
        return AllocateInNo;
    }
    /**
     * 通过主表 ALLOCATE_IN_NO 查询调拨入库明细表数据
     * @author xukl
     * @date 2016年8月7日
     * @param id
     * @return
     */
     @RequestMapping(value = "/Items/{ALLOCATE_IN_NO}",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto getPartAllocateInItems(@PathVariable(value = "ALLOCATE_IN_NO") String allocateInNo) {
         PageInfoDto pageInfoDto = partAllocateInService.getAllocateInItemsByAllocateInNo(allocateInNo);
         return pageInfoDto;
     }
     /**
      * 直接查出所有仓库的数据
     * TODO description
     * @author yujiangheng
     * @date 2017年4月1日
     * @param 
     * @return
     * @throws ServiceBizException
      */
     @RequestMapping(value="/Storage/Select",method = RequestMethod.GET)
     @ResponseBody
     public List<Map> getStorageAllSelect() throws ServiceBizException{
        List<Map> all = partAllocateInService.getStorageAllSelect();
         return all;
     }  
     /**
      * 查询符合条件的库存配件
     * TODO description
     * @author yujiangheng
     * @date 2017年4月25日
     * @param queryParam
     * @return
      */
     @RequestMapping(value="/findPartForAdd",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryStoragePartForAdd(@RequestParam Map<String, String> queryParam) {
         PageInfoDto AllocateInNo = partAllocateInService.queryStoragePartForAdd(queryParam);
         return AllocateInNo;
     }
     /**
      * 查询配件仓库库存
     * TODO description
     * @author yujiangheng
     * @date 2017年4月25日
     * @param queryParam
     * @return
      */
     @RequestMapping(value="/{storageCode}/{partNo}",method = RequestMethod.GET)
     @ResponseBody
     public Map<String,Object>  queryStoragePartOne(@PathVariable(value="storageCode") String storageCode,@PathVariable(value="partNo") String partNo) {
         Map<String, String> queryParam = new HashMap<String, String>();
         queryParam.put("STORAGE_CODE", storageCode);
         queryParam.put("PART_NO", partNo);
         Map<String,Object>  AllocateInNo = partAllocateInService.queryStoragePartOne(queryParam);
    //   System.out.println("------------------------"+AllocateInNo.toString());
         return AllocateInNo;
     }
     /**
      * 根据仓库代码查询库位
     * TODO description
     * @author yujiangheng
     * @date 2017年4月25日
     * @param queryParam
     * @return
      */
     @RequestMapping(value="/{storageCode}",method = RequestMethod.GET)
     @ResponseBody
     public Map<String,Object>  queryStoragePartOne(@PathVariable(value="storageCode") String storageCode) {
         Map<String, String> queryParam = new HashMap<String, String>();
         queryParam.put("STORAGE_CODE", storageCode);
         Map<String,Object>  AllocateInNo = partAllocateInService.queryStoragePosition(queryParam);
       System.out.println("------------------------"+AllocateInNo.toString());
         return AllocateInNo;
     }
     /**
      * 新增时查询配件信息2
      * @param queryParam
      * @return
      */
     @RequestMapping(value="/findPartForAddC/{storageCode}/{partNo}",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto findAllPartInfoC(@PathVariable String storageCode,@PathVariable String partNo){
         Map<String, String> queryParam = new HashMap<String, String>();
         queryParam.put("STORAGE_CODE", storageCode);
         queryParam.put("PART_NO", partNo);
         return partAllocateInService.findAllPartInfoC(queryParam);
     }
     /**
      * 查询符合条件的业务往来客户
      */
     @RequestMapping(value="/findPartCustomer",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto QueryPartCustomer(@RequestParam Map<String, String> queryParam) {
         PageInfoDto AllocateInNo = partAllocateInService.QueryPartCustomer(queryParam);
         return AllocateInNo;
     }
     /**
      *    保存调拨单信息
     * TODO description
     * @author yujiangheng
     * @date 2017年4月24日
     * @param listToolBuyItemDTO
     * @param uriCB
     * @throws ServiceBizException
     * @throws ParseException
      */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String    savePartAllocateIn(@RequestBody ListPartAllocateInItemDto listPartAllocateInItemDto,UriComponentsBuilder uriCB)throws ServiceBizException{
       System.out.println(listPartAllocateInItemDto.toString());
       return partAllocateInService.savePartAllocateIn(listPartAllocateInItemDto);
    }
    //入账后 表的isFinished字段值为12781001
    
    /**
     * 查询网内调拨
    * TODO description
    * @author yujiangheng
    * @date 2017年4月24日
    * @param queryParam
    * @return
     */
     @RequestMapping(value="/AllocateOutNet",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto queryAllocateOutNet(@RequestParam Map<String, String> queryParam) {
         PageInfoDto allocateOutNetItem = partAllocateInService.queryAllocateOutNet(queryParam);
         return allocateOutNetItem;
     }
     /**
      * 查询网内调拨明细
     * TODO description
     * @author yujiangheng
     * @date 2017年4月24日
     * @param queryParam
     * @return
      */
      @RequestMapping(value="/AllocateOutNetItem/{netAllocateNo}",method = RequestMethod.GET)
      @ResponseBody
      public PageInfoDto queryAllocateOutNetItem(@PathVariable String netAllocateNo) {
          Map<String,String> queryParam=new HashMap<String,String>();
          queryParam.put("netAllocateNo", netAllocateNo);
          PageInfoDto allocateOutNetItem = partAllocateInService.queryAllocateOutNetItem(queryParam);
          return allocateOutNetItem;
      }
      /**
       * 查询分厂调拨
      * TODO description
      * @author yujiangheng
      * @date 2017年4月24日
      * @param queryParam
      * @return
       */
       @RequestMapping(value="/BranchFactoryAllocate",method = RequestMethod.GET)
       @ResponseBody
       public PageInfoDto QueryBranchFactoryAllocate(@RequestParam Map<String, String> queryParam) {
           PageInfoDto allocateOutNetItem = partAllocateInService.QueryBranchFactoryAllocate(queryParam);
           return allocateOutNetItem;
       }
      /**
       * 查询分厂调拨明细
      * TODO description
      * @author yujiangheng
      * @date 2017年4月24日
      * @param queryParam
      * @return
       */
     /*  @RequestMapping(value="/BranchFactoryAllocateItem/{allocateOutNo}/{fromEntity}/{rate}",method = RequestMethod.GET)
       @ResponseBody
       public List<Map> QueryBranchFactoryAllocateItem(@PathVariable String allocateOutNo, 
                                                         @PathVariable String fromEntity,@PathVariable String rate) {
           Map<String,String> queryParam=new HashMap<String, String>();
           queryParam.put("ALLOCATE_OUT_NO", allocateOutNo);
           queryParam.put("FROM_ENTITY", fromEntity);
           queryParam.put("RATE", rate);
        System.out.println("_____________________________________"+queryParam.toString());
        List<Map> allocateOutNetItem = partAllocateInService.QueryBranchFactoryAllocateItem(queryParam);
           return allocateOutNetItem;
       }*/
       /**
        *  //调拨入库做废   CUSTOMER_CODE / ALLOCATE_IN_NO
       * TODO description
       * @author yujiangheng
       * @date 2017年5月4日
       * @param id
       * @param uriCB
       * @param ptdto   
        */
       @RequestMapping(value = "/delete/{customerCode}/{allocateInNo}", method = RequestMethod.DELETE)
       @ResponseStatus(HttpStatus.NO_CONTENT)
       public void deleteAllocateIn(@PathVariable String customerCode, @PathVariable String allocateInNo, UriComponentsBuilder uriCB) {
           System.out.println(customerCode+":"+allocateInNo);
           partAllocateInService.deleteAllocateIn(customerCode,allocateInNo);
       }
       /**
        * 调拨入库入账
       * TODO description
       * @author yujiangheng
       * @date 2017年5月5日
       * @param customerCode 
       * @param allocateInNo
       * @param uriCB
     * @throws Exception 
     * @throws ServiceBizException 
        */
       @RequestMapping(value="/enterRecord/{allocateInNo}/{isIdleAllocation}",method = RequestMethod.POST)
       @ResponseBody
       @ResponseStatus(HttpStatus.CREATED)
       public void accountPartAllocateIn( @PathVariable String allocateInNo,@PathVariable String isIdleAllocation, UriComponentsBuilder uriCB) throws ServiceBizException, Exception {
           Map<String,String> queryparams=new HashMap<String,String>();
           //页面参数： ALLOCATE_IN_NO  FINISHED_DATE   IS_IDLE_ALLOCATION
           queryparams.put("ALLOCATE_IN_NO",allocateInNo);
           queryparams.put("IS_IDLE_ALLOCATION",isIdleAllocation);
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
         Calendar cdate = Calendar.getInstance();
         String finishedDate = sdf.format(cdate.getTime());
       //  System.out.println(finishedDate);
           queryparams.put("FINISHED_DATE",sdf.format(cdate.getTime()));
             System.out.println(queryparams);
          partAllocateInService.accountPartAllocateIn(queryparams);
       }
     
      /**
       * 配件车型组查询
      * TODO description
      * @author yujiangheng
      * @date 2017年5月9日
      * @param queryParam
      * @return
       */
       @RequestMapping(value="/findPartModelGroup",method = RequestMethod.GET)
       @ResponseBody
       public PageInfoDto findPartModelGroup() throws Exception{
           return partAllocateInService.findPartModelGroup();
       }
       /**
        *   新增配件库存
       * TODO description
       * @author yujiangheng
       * @date 2017年4月24日
       * @param listToolBuyItemDTO
       * @param uriCB
       * @throws ServiceBizException
       * @throws ParseException
        */
      @RequestMapping(value="/MaintainPartStock",method = RequestMethod.POST)
      @ResponseBody
      @ResponseStatus(HttpStatus.CREATED)
      public void    addPartStock(@RequestBody  Map partInfoDTO,UriComponentsBuilder uriCB)throws ServiceBizException,Exception{
         System.out.println("_______________________________"+partInfoDTO.toString());
        partAllocateInService.addPartStock(partInfoDTO);
      }
      /**
       * 根据配件代码查询配件基本信息
      * TODO description
      * @author yujiangheng
      * @date 2017年4月25日
      * @param queryParam
      * @return
       */
      @RequestMapping(value="/partInfo/{PART_NO}",method = RequestMethod.GET)
      @ResponseBody
      public Map<String,Object>  queryPartInfo(@PathVariable(value="PART_NO") String partNo) {
          Map<String, String> queryParam = new HashMap<String, String>();
          queryParam.put("PART_NO", partNo);
         // System.out.println("------------------------"+partNo);
          Map<String,Object>  AllocateInNo = partAllocateInService.queryPartInfo(queryParam);
    //    System.out.println("------------------------"+AllocateInNo.toString());
          return AllocateInNo;
      }
       
}
