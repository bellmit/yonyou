package com.yonyou.dms.part.controller.basedata;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.util.UriComponentsBuilder;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.AccountPeriodDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
import com.yonyou.dms.part.service.basedata.AccountPeriodService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 会计周期
* TODO description
* @author yujiangheng
* @date 2017年4月6日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/AccountPeriod")
public class AccountPeriodController {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
    @Autowired
    private AccountPeriodService accountPeriodService;
    
    /**
     * 根据查询条件查询计量单位信息
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto AccountPeriodSql(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = accountPeriodService.searchAccountPeriod(queryParam);
        return pageInfoDto;
    }  

    /**
     * 根据bYear、periods和dealerCode查询会计周期信息和下个周期的结束日期
     * @author yujiangheng
     * @date 2017年3月21日
     */
    @RequestMapping(value = "/{B_YEAR}/{PERIODS}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findByKeys(@PathVariable("B_YEAR") String bYear, @PathVariable("PERIODS") String periods,
                                          UriComponentsBuilder uriCB) throws ServiceBizException {
        Map<String, Object> accountPeriod = accountPeriodService.findByKeys(bYear, periods);
        List<String> nextPeriod = nextAccountPeriod(bYear,periods);//获取  下个周期的bYear/periods
            Map<String, Object> accountPeriod1 = accountPeriodService.findByKeys(nextPeriod.get(0), nextPeriod.get(1));
            if(accountPeriod1!=null){
                accountPeriod.put("nextEndDate", accountPeriod1.get("END_DATE"));
                System.out.println(accountPeriod);
                return accountPeriod;
            }
        accountPeriod.put("nextEndDate", null);
        System.out.println(accountPeriod.toString());
        return accountPeriod;
    }
    
  
    /**
     *此方法用于获取当前修改的会计周期的下一个周期的bYear和periods
    * TODO description
    * @author yujiangheng
    * @date 2017年4月7日
    * @param st
    * @param str
    * @return
     */
    public List<String> nextAccountPeriod(String st,String str){
        int b=Integer.parseInt(st);
        int a=Integer.parseInt(str)+1;//下一个周期
         StringBuilder sb=new StringBuilder(a+"");//将周期转换为字符串
         List<String> list=new ArrayList<String>();
          if(sb.length()<2){//一位数
            sb.insert(0, "0");
            list.add(st);
            list.add(sb.toString());
            return  list;
           }else{//两位数
               if(a>12){
                   b=b+1;
                   StringBuilder sb1=new StringBuilder(b+"");
                   str="01";
                   list.add(sb1.toString());
                   list.add(str);
                   return  list;  
               }else{
                   list.add(st);
                   list.add(sb.toString());
                   System.out.println(list.toString());
                   return list;
               }
           }   
    }
    /**
     * 根据bYear、periods和dealerCode修改当前会计周期的结束日期和更新后一个会计周期的开始信息
     * @author zhengcong
     * @throws ParseException 
     * @date 2017年3月21日
     */
    @RequestMapping(value = "/{B_YEAR}/{PERIODS}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<AccountPeriodDTO> updateAccountPeriod(@PathVariable("B_YEAR") String bYear,@PathVariable("PERIODS") String periods,
 @RequestBody AccountPeriodDTO accountPeriodDTO,UriComponentsBuilder uriCB) throws ServiceBizException, ParseException{
        System.out.println(bYear+":"+periods+":"+accountPeriodDTO);//
      //获取页面上修改后的数据插入数据库
        accountPeriodService.updateAccountPeriod(bYear,periods, accountPeriodDTO);
        Date endDate=getNextBeginDate(accountPeriodDTO);//获取设置后的下个周期的开始时间
        
        //判断当前修改的周期后面是否还有一个周期
        List<String> nextPeriod = nextAccountPeriod(bYear,periods);//获取  下个周期的bYear/periods
        Map<String, Object> accountPeriod1 = accountPeriodService.findByKeys(nextPeriod.get(0), nextPeriod.get(1));
        if(accountPeriod1!=null){
            accountPeriod1.put("BEGIN_DATE", endDate);
            accountPeriodService.updateNextAccountPeriod(nextPeriod.get(0), nextPeriod.get(1), accountPeriod1);
        }
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<AccountPeriodDTO>(headers, HttpStatus.CREATED);  
    }
    /**
     * 此方法用于获取修改后的周期结束时间的后一天即是下个周期的开始时间
    * TODO description
    * @author yujiangheng
    * @date 2017年4月7日
    * @param accountPeriodDTO
    * @return
    * @throws ParseException
     */
    private Date getNextBeginDate(AccountPeriodDTO accountPeriodDTO) throws ParseException{
      SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
      Date beginDate = (Date)accountPeriodDTO.getEndDate();//将页面上设置的结束日期转换为日期格式
      Calendar date = Calendar.getInstance();
      date.setTime(beginDate);
      date.set(Calendar.DATE, date.get(Calendar.DATE)+1);
      Date endDate = dft.parse(dft.format(date.getTime()));
        return endDate;
    }
    /**
     * 在新增之前先获取数据库最后一条数据，然后将其进行修改为下个周期传到新增页面
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param uriCB
    * @return
    * @throws ServiceBizException
     * @throws ParseException 
     */
    @RequestMapping(value = "/lastOne",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> beforeAddAccountPeriod( UriComponentsBuilder uriCB) throws ServiceBizException, ParseException {
        Map<String,Object> accountPeriod = accountPeriodService.findLastOne();
            getNewAccountPeriod(accountPeriod);
            System.out.println(accountPeriod);
        return accountPeriod;
    }
    /**
     * 改装最后一条数据
    * TODO description
    * @author yujiangheng
    * @date 2017年4月12日
    * @param accountPeriod
    * @return
    * @throws ParseException
     */
    private Map<String,Object> getNewAccountPeriod( Map<String,Object> accountPeriod) throws ParseException{
        String bYear=(String)accountPeriod.get("B_YEAR");
        String periods=(String)accountPeriod.get("PERIODS");
        //Date beginDate=(Date)accountPeriod.get("BEGIN_DATE");
        Date endDate=(Date)accountPeriod.get("END_DATE");
        List<String> list=nextAccountPeriod(bYear,periods);
        
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
        Calendar date = Calendar.getInstance();
        date.setTime(endDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE)+1);
        Date beginDate = sdf.parse(sdf.format(date.getTime()));//设置开始时间
        
        Calendar date2 = Calendar.getInstance();
        date2.setTime(beginDate);
        date2.set(Calendar.DATE, date2.get(Calendar.DATE)+30);
         endDate = sdf.parse(sdf.format(date2.getTime()));//设置结束时间
         accountPeriod.put("B_YEAR", list.get(0));
         accountPeriod.put("PERIODS", list.get(1));
         accountPeriod.put("BEGIN_DATE", beginDate);
         accountPeriod.put("END_DATE", endDate);
        return accountPeriod;
    }
   /**
    * 新增一个会计周期
   * TODO description
   * @author yujiangheng
   * @date 2017年4月7日
   * @param unitDTO
   * @param uriCB
   * @return
   * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AccountPeriodDTO> addAccountPeriod(@RequestBody AccountPeriodDTO accountPeriodDTO,
                                                    UriComponentsBuilder uriCB) throws ServiceBizException{
        accountPeriodService.addAccountPeriod(accountPeriodDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<AccountPeriodDTO>(accountPeriodDTO,headers, HttpStatus.CREATED);  
   }
    
    
}
