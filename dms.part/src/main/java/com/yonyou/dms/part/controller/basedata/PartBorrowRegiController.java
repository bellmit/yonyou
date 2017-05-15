package com.yonyou.dms.part.controller.basedata;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtPartBorrowItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPartBorrowItemDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.ListPartAllocateInItemDto;
import com.yonyou.dms.part.service.basedata.PartBorrowRegiService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 借进登记
* TODO description
* @author yujiangheng
* @date 2017年5月11日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/PartBorrowRegi")
public class PartBorrowRegiController {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartBorrowRegiController.class);

    @Autowired
    private PartBorrowRegiService partBorrowRegiService;
    /**
     * 根据借进单号查询对应的借进明细 配件借进登记
    * TODO description
    * @author yujiangheng
    * @date 2017年5月11日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/{borrowNo}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchPartBorrowRegiItem(@PathVariable  String borrowNo) throws ServiceBizException{
        
        PageInfoDto pageInfoDto = partBorrowRegiService.searchPartBorrowRegiItem(  borrowNo);
        return pageInfoDto;
    }  
   /**
    * 根据借进单号模糊查询借进主表信息 
   * TODO description
   * @author yujiangheng
   * @date 2017年5月11日
   * @param borrowNo
   * @return
   * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchPartBorrowRegi(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        System.out.println("________________________"+queryParam.get("borrowNo"));
        PageInfoDto pageInfoDto = partBorrowRegiService.searchPartBorrowRegi(  queryParam.get("borrowNo"));
        return pageInfoDto;
    }  
    /**
     * 保存
    * TODO description
    * @author yujiangheng
    * @date 2017年5月12日
    * @param listTtPartBorrowItemDTO
    * @param uriCB
    * @return
    * @throws ServiceBizException
    * @throws ParseException
     */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public String    savePartBorrowRegi(@RequestBody ListTtPartBorrowItemDTO listTtPartBorrowItemDTO,UriComponentsBuilder uriCB)throws ServiceBizException, ParseException{
       System.out.println("_________________"+listTtPartBorrowItemDTO.toString());
       Double total=0.00;
       for(TtPartBorrowItemDTO  ttPartBorrowItemDTO:listTtPartBorrowItemDTO.getDms_Borrow_Regi()){
           total+=ttPartBorrowItemDTO.getInAmount();
       }
       listTtPartBorrowItemDTO.setBorrowTotalAmount(total);
     //  Utility.stampToDate(listTtPartBorrowItemDTO.getBorrowDate());
      return partBorrowRegiService.savePartBorrowRegi(listTtPartBorrowItemDTO);
    }
    /**
     * 入账
    * TODO description
    * @author yujiangheng
    * @date 2017年5月12日
    * @param allocateInNo
    * @param isIdleAllocation
    * @param uriCB
    * @throws ServiceBizException
    * @throws Exception
     */
    @RequestMapping(value="/enterRecord/{borrowNo}/{customerCode}/{customerName}",method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void accountPartBorrowRegi( @PathVariable String borrowNo,@PathVariable String customerCode, 
             @PathVariable String customerName,  UriComponentsBuilder uriCB) throws ServiceBizException, Exception {
       Map<String,String> queryParam=new HashMap<String, String>();
       queryParam.put("BORROW_NO", borrowNo);
       queryParam.put("CUSTOMER_CODE", customerCode);
       queryParam.put("CUSTOMER_NAME", customerName);
      
       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
      Calendar cdate = Calendar.getInstance();
      String finishedDate = sdf.format(cdate.getTime());
      System.out.println(finishedDate);
      queryParam.put("FINISHED_DATE",sdf.format(cdate.getTime()));//完成日期
      System.out.println(queryParam.toString());
      partBorrowRegiService.accountPartBorrowRegi(queryParam);
    }
    @RequestMapping(value = "/delete/{borrowNo}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePartBorrowRegi(@PathVariable String borrowNo, UriComponentsBuilder uriCB) throws ServiceBizException, ParseException {
        System.out.println(borrowNo);
        partBorrowRegiService.deletePartBorrowRegi(borrowNo);
    }
    
}
