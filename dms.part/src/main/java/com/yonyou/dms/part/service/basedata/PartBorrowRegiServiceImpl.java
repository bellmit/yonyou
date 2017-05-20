package com.yonyou.dms.part.service.basedata;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.javalite.activejdbc.Base;

import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartBorrowItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartBorrowPO;
import com.yonyou.dms.common.domains.PO.stockmanage.PartAllocateInItemPO;
import com.yonyou.dms.common.domains.PO.stockmanage.PartAllocateInPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.BaseModel;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.controller.basedata.PartBorrowRegiController;
import com.yonyou.dms.part.domains.DTO.basedata.ListTtPartBorrowItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPartBorrowDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPartBorrowItemDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmDefaultParaDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmPartStockDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmPartStockItemDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtAccountsTransFlowDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartFlowDTO;
/**
 * 借进登记
* TODO description
* @author yujiangheng
* @date 2017年5月11日
 */
@Service
public class PartBorrowRegiServiceImpl implements PartBorrowRegiService{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartBorrowRegiServiceImpl.class);

    /**
     * 根据借进单号查询对应的借进明细 配件借进登记
    * @author yujiangheng
    * @date 2017年5月11日
    * @param borrowNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartBorrowRegiService#searchPartBorrowRegi(java.lang.String)
     */
    @Override
    public List<Map> searchPartBorrowRegiItem( String borrowNo) throws ServiceBizException {
        String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        //核对加锁
        String lockName=Utility.selLockerName("LOCK_USER", "TT_PART_BORROW", "BORROW_NO", borrowNo);
        //System.out.println("___________________________________________________"+lockName);
        if(lockName.length()!=0){
            //用户锁定
           int lockFlag= Utility.updateByLocker("TT_PART_BORROW_ITEM", FrameworkUtil.getLoginInfo().getEmployeeNo(),
                                                "BORROW_NO", borrowNo,"LOCK_USER" );
           if(lockFlag==1){
               throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
           }
        }
        StringBuffer sql = new StringBuffer("select A.ITEM_ID,A.BORROW_NO,A.DEALER_CODE,A.STORAGE_CODE,F.STORAGE_NAME,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,");
        sql.append("A.PART_NO,A.PART_NAME,A.UNIT_CODE,E.UNIT_NAME,A.IN_QUANTITY,A.WRITE_OFF_QUANTITY,A.COST_PRICE,A.COST_AMOUNT,A.IN_PRICE,A.IN_AMOUNT, ");
        sql.append("(A.IN_QUANTITY-A.WRITE_OFF_QUANTITY) as NORETURN,(b.STOCK_QUANTITY+b.BORROW_QUANTITY-b.LEND_QUANTITY-b.LOCKED_QUANTITY) as userable_quantity,c.IS_NEGATIVE,D.DOWN_TAG ");
        sql.append( "from  TT_PART_BORROW_ITEM A left join tm_part_stock b on a.STORAGE_CODE=b.STORAGE_CODE and a.DEALER_CODE=b.DEALER_CODE and a.part_no=b.part_no ");
        sql.append(" left join ("+CommonConstants.VM_STORAGE +") c on b.DEALER_CODE=c.DEALER_CODE  and b.storage_code=c.storage_code LEFT JOIN TM_PART_INFO D ON A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO = D.PART_NO ");
        sql.append("LEFT JOIN TM_unit E ON A.DEALER_CODE = E.DEALER_CODE AND  A.unit_code = E.unit_code  ");
        sql.append(" LEFT JOIN TM_STORAGE F ON A.DEALER_CODE = F.DEALER_CODE AND A.STORAGE_CODE = F.STORAGE_CODE  ");
        /*
         *   LEFT JOIN TM_unit E ON A.DEALER_CODE = E.DEALER_CODE AND  A.unit_code = E.unit_code
     LEFT JOIN TM_STORAGE F ON A.DEALER_CODE = F.DEALER_CODE AND A.STORAGE_CODE = F.STORAGE_CODE 
         */
        sql.append(  "where  A.DEALER_CODE='"+dealerCode + "'  and A.D_KEY=" + CommonConstants.D_KEY +Utility.getLikeCond(null, "BORROW_NO", borrowNo, "AND"));
        System.out.println("_________________________"+sql.toString());
        return DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 根据借进单号模糊查询借进主表信息
    * @author yujiangheng
    * @date 2017年5月11日
    * @param borrowNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartBorrowRegiService#searchPartBorrowRegi(java.lang.String)
     */
    @Override
    public PageInfoDto searchPartBorrowRegi(String borrowNo) throws ServiceBizException {
        String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sql = new StringBuffer("select A.DEALER_CODE,A.BORROW_NO,A.CUSTOMER_CODE,A.CUSTOMER_NAME,A.BORROW_TOTAL_AMOUNT,");
        sql.append( "A.HANDLER,B.EMPLOYEE_NAME,A.BORROW_DATE,A.IS_FINISHED,A.FINISHED_DATE,A.PAY_OFF,A.LOCK_USER  from TT_PART_BORROW A  ");
        sql.append("left join tm_employee  B on A.DEALER_CODE=B.DEALER_CODE AND A.HANDLER=B.EMPLOYEE_NO  ");
        sql.append("where A.DEALER_CODE='" + dealerCode+ "' and A.D_KEY="+ CommonConstants.D_KEY + " and  A.IS_FINISHED=");
        sql.append(CommonConstants.DICT_IS_NO+ Utility.getLikeCond(null, "A.BORROW_NO", borrowNo, "AND"));
        return DAOUtil.pageQuery(sql.toString(), null);
    }
    /**
     * 保存
    * @author yujiangheng
    * @date 2017年5月12日
    * @param listTtPartBorrowItemDTO
    * @return
    * @throws ServiceBizException
    * @throws ParseException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartBorrowRegiService#savePartBorrowRegi(com.yonyou.dms.part.domains.DTO.basedata.ListTtPartBorrowItemDTO)
     */
    @Override
    public String savePartBorrowRegi(ListTtPartBorrowItemDTO listTtPartBorrowItemDTO) throws ServiceBizException, ParseException {
        //System.out.println("____________________"+listTtPartBorrowItemDTO.toString());
        //设置主表的对象属性
        TtPartBorrowDTO  ttPartBorrowDTO=new TtPartBorrowDTO();
        setTtPartBorrowDTO(ttPartBorrowDTO,listTtPartBorrowItemDTO);
        System.out.println(ttPartBorrowDTO.toString());
        if(!StringUtils.isNullOrEmpty(  listTtPartBorrowItemDTO.getBorrowNo())){//有单号
            //删除需要删除的内容
            if(!"".equals(listTtPartBorrowItemDTO.getItems())&&listTtPartBorrowItemDTO.getItems()!=null){
                //将删除的元素删除
                String items =listTtPartBorrowItemDTO.getItems();
                 String items1=items.substring(items.indexOf(",")+1);
                String[] split = items1.split(",");
              //  System.out.println("____________________"+split);
            for (String item : split){
                    if(!"N".equals(item)){
                        TtPartBorrowItemPO ttPartBorrowItem=TtPartBorrowItemPO.findById(Long.parseLong(item));
                        Boolean result= ttPartBorrowItem.delete();
                        System.out.println("____________________"+result);
                    }
                } 
            }
            //更新主表
            TtPartBorrowPO ttPartBorrowPO=TtPartBorrowPO.findByCompositeKeys(ttPartBorrowDTO.getBorrowNo(),ttPartBorrowDTO.getDealerCode());
            ttPartBorrowPO.saveIt();
        }else{//没有单号
            //新建主表
            TtPartBorrowPO ttPartBorrowPO=new TtPartBorrowPO();
            setTtPartBorrowPO(ttPartBorrowPO,ttPartBorrowDTO);
            ttPartBorrowPO.saveIt();
        }
        
        //循环表格里的数据
        for(TtPartBorrowItemDTO ttPartBorrowItemDTO:listTtPartBorrowItemDTO.getDms_Borrow_Regi() ){
            ttPartBorrowItemDTO.setBorrowNo(ttPartBorrowDTO.getBorrowNo());//将主表的单号存入子表实体对象中
            //根据子表的item_id来判断表格中的数据是否存在于数据库中，进而执行新增或修改
            //新增的数据在前台页面上赋值为"N"
            if(!"N".equals(ttPartBorrowItemDTO.getItemId())&&"X".equals(ttPartBorrowItemDTO.getFlag())){//修改子表数据
                TtPartBorrowItemPO ttPartBorrowItemPO=TtPartBorrowItemPO.findById(ttPartBorrowItemDTO.getItemId());
                setTtPartBorrowItemPO(ttPartBorrowItemPO,ttPartBorrowItemDTO);
                ttPartBorrowItemPO.saveIt();
            }else if("N".equals(ttPartBorrowItemDTO.getItemId())){//新增子表数据
                TtPartBorrowItemPO ttPartBorrowItemPO=new TtPartBorrowItemPO();
                setTtPartBorrowItemPo(ttPartBorrowItemPO,ttPartBorrowItemDTO);
                ttPartBorrowItemPO.saveIt();
            }
        }
        return ttPartBorrowDTO.getBorrowNo();//将单号返回给前台，刷新表格数据
    }
    /**
     * 设置TtPartBorrowItemPO对象属性1-新增
    * TODO description
    * @author yujiangheng
    * @date 2017年5月11日
    * @param ttPartBorrowItemPO
    * @param ttPartBorrowItemDTO
     */
    private void setTtPartBorrowItemPo(TtPartBorrowItemPO ttPartBorrowItemPO, TtPartBorrowItemDTO ttPartBorrowItemDTO) {
//      ITEM_ID,BORROW_NO,DEALER_CODE,STORAGE_CODE,PART_BATCH_NO,PART_NO,PART_NAME,UNIT_CODE,
//      IN_QUANTITY,WRITE_OFF_QUANTITY // 核销数量,COST_PRICE,COST_AMOUNT,IN_PRICE,IN_AMOUNT,
        ttPartBorrowItemPO.setString("ITEM_ID",null);
//        if(!StringUtils.isNullOrEmpty( ttPartBorrowItemDTO.getWriteOffQuantity())){
//            ttPartBorrowItemPO.setFloat("WRITE_OFF_QUANTITY", ttPartBorrowItemDTO.getWriteOffQuantity());
//        }
        ttPartBorrowItemPO.setString("BORROW_NO",ttPartBorrowItemDTO.getBorrowNo());
        ttPartBorrowItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        ttPartBorrowItemPO.setString("PART_BATCH_NO", ttPartBorrowItemDTO.getPartBatchNo());
        ttPartBorrowItemPO.setString("PART_NO", ttPartBorrowItemDTO.getPartNo());
        ttPartBorrowItemPO.setString("PART_NAME", ttPartBorrowItemDTO.getPartName());
        ttPartBorrowItemPO.setString("UNIT_CODE", ttPartBorrowItemDTO.getUnitCode());
        ttPartBorrowItemPO.setString("STORAGE_CODE", ttPartBorrowItemDTO.getStorageCode());
        ttPartBorrowItemPO.setString("STORAGE_POSITION_CODE", ttPartBorrowItemDTO.getStoragePositionCode());
        ttPartBorrowItemPO.setDouble("COST_PRICE", ttPartBorrowItemDTO.getCostPrice());
        ttPartBorrowItemPO.setDouble("COST_AMOUNT", ttPartBorrowItemDTO.getCostAmount());
        if(!StringUtils.isNullOrEmpty( ttPartBorrowItemDTO.getWriteOffQuantity())){
            ttPartBorrowItemPO.setFloat("WRITE_OFF_QUANTITY", ttPartBorrowItemDTO.getWriteOffQuantity());
        }
        ttPartBorrowItemPO.setFloat("IN_QUANTITY", ttPartBorrowItemDTO.getInQuantity());
        ttPartBorrowItemPO.setDouble("IN_PRICE", ttPartBorrowItemDTO.getInPrice());
        ttPartBorrowItemPO.setDouble("IN_AMOUNT", ttPartBorrowItemDTO.getInAmount());
        
    }
    /**
     * 设置TtPartBorrowItemPO对象属性1-修改
    * TODO description
    * @author yujiangheng
    * @date 2017年5月11日
    * @param ttPartBorrowItemPO
    * @param ttPartBorrowItemDTO
     */
    private void setTtPartBorrowItemPO(TtPartBorrowItemPO ttPartBorrowItemPO, TtPartBorrowItemDTO ttPartBorrowItemDTO) {
        //PART_NO   IN_QUANTITY ，IN_PRICE,  IN_AMOUNT,
        ttPartBorrowItemPO.setString("PART_NO", ttPartBorrowItemDTO.getPartNo());
        ttPartBorrowItemPO.setFloat("IN_QUANTITY", ttPartBorrowItemDTO.getInQuantity());
        ttPartBorrowItemPO.setDouble("IN_PRICE", ttPartBorrowItemDTO.getInPrice());
        ttPartBorrowItemPO.setDouble("IN_AMOUNT", ttPartBorrowItemDTO.getInAmount());
    }
    /**
     * 设置TtPartBorrowPO对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月11日
    * @param ttPartBorrowPO
    * @param ttPartBorrowDTO
     */
    private void setTtPartBorrowPO(TtPartBorrowPO ttPartBorrowPO, TtPartBorrowDTO ttPartBorrowDTO) {
        System.out.println("_________________"+ttPartBorrowDTO.getDealerCode());
        ttPartBorrowPO.setString("DEALER_CODE", ttPartBorrowDTO.getDealerCode());
        ttPartBorrowPO.setString("BORROW_NO", ttPartBorrowDTO.getBorrowNo());
        ttPartBorrowPO.setString("CUSTOMER_CODE", ttPartBorrowDTO.getCustomerCode());
        ttPartBorrowPO.setString("CUSTOMER_NAME", ttPartBorrowDTO.getCustomerName());
        ttPartBorrowPO.setString("HANDLER", ttPartBorrowDTO.getHandler());
        ttPartBorrowPO.setDate("BORROW_DATE", ttPartBorrowDTO.getBorrowDate());
        ttPartBorrowPO.setDouble("BORROW_TOTAL_AMOUNT", ttPartBorrowDTO.getBorrowTotalAmount());
        
    }
    /**
     * 设置主表对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月11日
    * @param ttPartBorrowDTO
    * @param listTtPartBorrowItemDTO
     * @throws ParseException 
     */
    private void setTtPartBorrowDTO(TtPartBorrowDTO ttPartBorrowDTO, ListTtPartBorrowItemDTO listTtPartBorrowItemDTO) throws ParseException {
        ttPartBorrowDTO.setDealerCode(FrameworkUtil.getLoginInfo().getDealerCode());
        if( StringUtils.isNullOrEmpty(listTtPartBorrowItemDTO.getBorrowNo())){//没有单号
            CommonNoService CommonNo=new CommonNoServiceImpl();
            String SystemOrderNo=CommonNo.getSystemOrderNo(CommonConstants.SRV_JJDJDH);
            ttPartBorrowDTO.setBorrowNo(SystemOrderNo);
        }else{//有单号
            ttPartBorrowDTO.setBorrowNo(listTtPartBorrowItemDTO.getBorrowNo());
        }
        ttPartBorrowDTO.setCustomerCode(listTtPartBorrowItemDTO.getCustomerCode());
        ttPartBorrowDTO.setCustomerName(listTtPartBorrowItemDTO.getCustomerName());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //Date dateString = formatter.parse(formatter.format(listTtPartBorrowItemDTO.getBorrowDate()));
        ttPartBorrowDTO.setBorrowDate(formatter.parse(formatter.format(listTtPartBorrowItemDTO.getBorrowDate())));
        ttPartBorrowDTO.setHandler(FrameworkUtil.getLoginInfo().getEmployeeNo());
        ttPartBorrowDTO.setBorrowTotalAmount(listTtPartBorrowItemDTO.getBorrowTotalAmount());
    }
    /**
     * 作废
    * @author yujiangheng
    * @date 2017年5月12日
    * @param borrowNo
    * @throws ServiceBizException
    * @throws ParseException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartBorrowRegiService#deletePartBorrowRegi(java.lang.String)
     */
    @Override
    public void deletePartBorrowRegi(String borrowNo) throws ServiceBizException, ParseException {
        String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
        if(StringUtils.isNullOrEmpty(borrowNo)){
            throw new ServiceBizException("丢失主键！");
        }
        StringBuilder sb=new StringBuilder(" DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sb.append("AND BORROW_NO='"+borrowNo+"'");
        //删除子表信息
        TtPartBorrowItemPO.delete(sb.toString(), null);//sql语句为where 后面的部分
         //删除一条主表信息
        TtPartBorrowPO.delete(sb.toString(), null);
        //打印日志
        logger.debug("借进登记单删除：借进登记单号["+borrowNo+"]","TT_PART_BORROW,BORROW_NO="+borrowNo);
    }
    /**
     * 入账
    * @author yujiangheng
    * @date 2017年5月12日
    * @param queryParam
     * @throws Exception 
    * @see com.yonyou.dms.part.service.basedata.PartBorrowRegiService#accountPartBorrowRegi(java.util.Map)
     */
    @Override
    public void accountPartBorrowRegi(Map<String,String> queryParam) throws Exception {
        QueryMonthPeriodIsFinished();
        AccountPartBorrow(queryParam);//配件借进登记入帐操作
        BulidVoucherAccountPartBorrow(queryParam.get("BORROW_NO"));//借进登记生成凭证
    }
    /**
     * 借进登记生成凭证
    * TODO description
    * @author yujiangheng
     * @throws Exception 
     * @date 2017年5月12日
     */
    private void BulidVoucherAccountPartBorrow( String borrowNo) throws Exception {
        String dealerCode =FrameworkUtil.getLoginInfo().getDealerCode();
        long userId = FrameworkUtil.getLoginInfo().getUserId();
        TmDefaultParaPO defaultpo=TmDefaultParaPO.
                findByCompositeKeys(dealerCode,CommonConstants.FINANCE_ITEM_CODE);
        if(defaultpo != null && CommonConstants.DICT_IS_YES.equalsIgnoreCase(defaultpo.get("DEFAULT_VALUE").toString()) ) {//获取开关设置
            TmDefaultParaPO dp=TmDefaultParaPO.
                    findByCompositeKeys(dealerCode,CommonConstants.REPAIR_CESS_ITEM_CODE);
            float  cess =  Float.parseFloat(dp.get("DEFAULT_VALUE").toString());
            TtPartBorrowPO pb=TtPartBorrowPO.findByCompositeKeys(borrowNo,dealerCode);
            TtAccountsTransFlowDTO  po  =new TtAccountsTransFlowDTO();
             po.setTransId(null);
             po.setOrgCode(dealerCode);
             po.setDealerCode(dealerCode);          
             po.setTransDate(Utility.getCurrentDateTime());                 
             po.setTransType(Utility.getInt(CommonConstants.DICT_BUSINESS_TYPE_PART_BORROW));
             po.setTaxAmount(Double.parseDouble(pb.get("BORROW_TOTAL_AMOUNT").toString()));
             po.setNetAmount(Double.parseDouble(pb.get("BORROW_TOTAL_AMOUNT").toString())*(1F-cess));
             po.setBusinessNo(pb.get("BORROW_NO").toString());
             po.setIsValid(Utility.getInt(CommonConstants.DICT_IS_YES));
             po.setExecNum(0);
             po.setExecStatus(Utility.getInt(CommonConstants.DICT_EXEC_STATUS_NOT_EXEC));//未生产
             TtAccountsTransFlowPO ttAccountsTransFlowPO=new TtAccountsTransFlowPO();
             setTtAccountsTransFlowPO(ttAccountsTransFlowPO,po);
             ttAccountsTransFlowPO.saveIt();
        }
    }
    /**
     * 设置TtAccountsTransFlowPO对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月13日
    * @param ttAccountsTransFlowPO
    * @param po
     */
    private void setTtAccountsTransFlowPO(TtAccountsTransFlowPO ttAccountsTransFlowPO, TtAccountsTransFlowDTO po) {
        ttAccountsTransFlowPO.set("TRANS_ID",null);
        ttAccountsTransFlowPO.setString("ORG_CODE",po.getOrgCode());
        ttAccountsTransFlowPO.setString("DEALER_CODE",po.getDealerCode());          
        ttAccountsTransFlowPO.setDate("TRANS_DATE",po.getTransDate());                    
        ttAccountsTransFlowPO.setInteger("TRANS_TYPE",po.getTransType());
        ttAccountsTransFlowPO.setDouble("TAX_AMOUNT",po.getTaxAmount());
        ttAccountsTransFlowPO.setDouble("NET_AMOUNT",po.getNetAmount());
        ttAccountsTransFlowPO.setString("BUSINESS_NO",po.getBusinessNo());                  
        ttAccountsTransFlowPO.setInteger("IS_VALID",po.getIsValid());
        ttAccountsTransFlowPO.setInteger("EXEC_NUM",po.getExecNum());
        ttAccountsTransFlowPO.setInteger("EXEC_STATUS",po.getExecStatus());//未生产
    }
    /**
     * 入账操作
    * TODO description
    * @author yujiangheng
     * @throws Exception 
    * @date 2017年5月12日
     */
    private void AccountPartBorrow(Map<String,String> queryParam) throws Exception {
 //       System.err.println("4444444444444444444444444444444444444444444444");
      //获取借进单号
        String borrowNo = queryParam.get("BORROW_NO");
        String customerCode = queryParam.get("CUSTOMER_CODE");
        String customerName = queryParam.get("CUSTOMER_NAME");
        String sFINISHED_DATE = queryParam.get("FINISHED_DATE");
        double costAmountBeforeA = 0;   //批次表入账前成本
        double costAmountBeforeB = 0;   //库存表入账前成本
        double costAmountAfterA = 0;    //批次表入账后成本
        double costAmountAfterB = 0;    //库存表入账后成本
        String dealerCode =FrameworkUtil.getLoginInfo().getDealerCode();
        long userId = FrameworkUtil.getLoginInfo().getUserId();
        String empNo = FrameworkUtil.getLoginInfo().getDealerCode();
        if(StringUtils.isNullOrEmpty(borrowNo)){
            throw new ServiceBizException("丢失主键！");
        }
        // 对于未入账的配件  进行校验
        if (StringUtils.isNullOrEmpty(borrowNo)){
            String errPartDel=checkPartStockIsDeleteBySheetNo( dealerCode, "TT_PART_BORROW_ITEM", "BORROW_NO", borrowNo);
            if (!errPartDel.equals("")){
                throw new ServiceBizException("保存错误"+ errPartDel+" 配件库存已经删除,请更换重新操作!");
            }               
        }
        //TmPartInfoPOFactory partInfoPof = new TmPartInfoPOFactory();
        List<Map> listcheck = getNonOemPartList(dealerCode, "TT_PART_BORROW_ITEM","BORROW_NO", borrowNo);
        String errPart = "";
        if (listcheck != null && listcheck.size() > 0){
            logger.debug("list size :"+listcheck.size());
            for (int i = 0;i<listcheck.size() ;i++ ){
                Map<String,String> dyna= listcheck.get(i);
                if (errPart.equals("")){
                    errPart = dyna.get("PART_NO");
                }else{
                    errPart = errPart + ", "+dyna.get("PART_NO"); 
                }                       
            }
            logger.debug("errPart :"+errPart);
        }               
        if (!errPart.equals("")){
            throw new ServiceBizException("保存错误："+ errPart+" 非OEM配件不允许出OEM库,请重新操作!");
        }               
        /**
         * update wanghui 2010.02.21 reason
         * 相同账号分别调出同一张未入账的调拨入库单，点入账造成流水账中该入库单，有2条重复的流水账记录, 判断该入库单是否已经入账
         * flag==12781002没有入账；反之已经入账
         */
        String sheetNoType="BORROW_NO";
        String sheetType="TT_PART_BORROW";
        String flag=Utility.checkIsFinished(borrowNo, dealerCode, sheetType, sheetNoType);
        if (flag.equals(CommonConstants.DICT_IS_YES)) {
            logger.debug("入库单号:" + borrowNo + "已经入账，不能重复入账！");
            throw new ServiceBizException("借进登记单号:"+borrowNo+"已经入账，不能重复入账！");
        }
      //1、修改借进登记单的入帐状态为DICT_IS_YES  
        TtPartBorrowDTO partborrowPO = new TtPartBorrowDTO();
        TtPartBorrowDTO partborrowPOCon = new TtPartBorrowDTO();
       
        partborrowPO.setIsFinished(Utility.getInt(CommonConstants.DICT_IS_YES));//完成状态
        partborrowPO.setFinishedDate(new Date(System.currentTimeMillis()));//完成日期
        TtPartBorrowPO ttPartBorrowPO=TtPartBorrowPO.findByCompositeKeys(borrowNo,dealerCode);
        ttPartBorrowPO.setInteger("IS_FINISHED",partborrowPO.getIsFinished());
        ttPartBorrowPO.setDate("FINISHED_DATE",partborrowPO.getFinishedDate());
        ttPartBorrowPO.saveIt();
      //是否有此借进单号的明细信息 
        if (!StringUtils.isNullOrEmpty(borrowNo)){
            StringBuilder sb= new StringBuilder("SELECT  ITEM_ID,BORROW_NO,DEALER_CODE,STORAGE_CODE,PART_BATCH_NO,PART_NO,PART_NAME,UNIT_CODE,");
            sb.append("  IN_QUANTITY,WRITE_OFF_QUANTITY,COST_PRICE,COST_AMOUNT,IN_PRICE,IN_AMOUNT  FROM  TT_PART_BORROW_ITEM  WHERE DEALER_CODE='"+dealerCode+"'");
            sb.append("   AND BORROW_NO='"+borrowNo+"'   AND D_KEY='"+CommonConstants.D_KEY+"'");
            List<Map>  list=DAOUtil.findAll(sb.toString(), null);
            TmPartStockItemDTO stockItemPO = null;
            //逐条对应明细信息修改改配件进数量（库存、批次）
            if (list != null && list.size() > 0){
                Map<String,Object> listitemstock = new HashMap<String,Object>();
                for (int i = 0; i < list.size() ; i++){
                    costAmountBeforeA = 0;  //批次表入账前成本
                    costAmountBeforeB = 0;  //库存表入账前成本
                    costAmountAfterA = 0;   //批次表入账后成本
                    costAmountAfterB = 0;   //库存表入账后成本
                   Map<String,Object> borrowItemPO = list.get(i);
                    //更新配件库存批次表和配件库存表借进数量,最新入库日期
                    stockItemPO = new TmPartStockItemDTO();
                    stockItemPO.setPartNo(borrowItemPO.get("PART_NO")+"");
                    stockItemPO.setStorageCode(borrowItemPO.get("STORAGE_CODE")+"");
                    if (borrowItemPO.get("PART_BATCH_NO") != null && !"".equals(borrowItemPO.get("PART_BATCH_NO"))) {
                        stockItemPO.setPartBatchNo(borrowItemPO.get("PART_BATCH_NO")+"");
                    } else {
                        stockItemPO.setPartBatchNo(CommonConstants.defaultBatchName);
                    }
                    stockItemPO.setStockQuantity(Float.parseFloat(borrowItemPO.get("IN_QUANTITY")+""));
                    StringBuilder sb1= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STORAGE_CODE,PART_BATCH_NO,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,");
                    sb1.append("SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,BORROW_QUANTITY,PART_STATUS,LEND_QUANTITY,LAST_STOCK_IN,");
                    sb1.append("LAST_STOCK_OUT,FOUND_DATE,REMARK  FROM  TM_PART_STOCK_ITEM  WHERE DEALER_CODE='"+dealerCode+"'");
                    sb1.append("   AND STORAGE_CODE='"+borrowItemPO.get("STORAGE_CODE")+"'");
                    sb1.append("   AND PART_NO='"+borrowItemPO.get("PART_NO")+"'");
                    sb1.append("   AND PART_BATCH_NO='"+borrowItemPO.get("PART_BATCH_NO")+"'");
                    Map<String,Object>  itemPOb=DAOUtil.findFirst(sb1.toString(), null);
                    if (itemPOb != null && itemPOb.size() > 0) {
                        if (itemPOb.get("COST_AMOUNT") != null && !"".equals(itemPOb.get("COST_AMOUNT").toString())){
                            costAmountBeforeA = Double.parseDouble(itemPOb.get("COST_AMOUNT")+"");//批次表入帐前成本金额 
                        }
                    }
                    StringBuilder sb2= new StringBuilder("SELECT DEALER_CODE, PART_NO, STORAGE_CODE,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,");
                    sb2.append("MAX_STOCK,MIN_STOCK,BORROW_QUANTITY,LEND_QUANTITY,LOCKED_QUANTITY,PART_MODEL_GROUP_CODE_SET,PART_SPE_TYPE,IS_SUGGEST_ORDER,MONTHLY_QTY_FORMULA,LAST_STOCK_IN,");
                    sb2.append("  LAST_STOCK_OUT,FOUND_DATE,PART_MAIN_TYPE,ISAUTO_MAXMIN_STOCK,SAFE_STOCK,REMARK   FROM  TM_PART_STOCK  WHERE DEALER_CODE='"+dealerCode+"'");
                    sb2.append("   AND STORAGE_CODE='"+borrowItemPO.get("STORAGE_CODE")+"'");
                    sb2.append("   AND PART_NO='"+borrowItemPO.get("PART_NO")+"' AND D_KEY="+CommonConstants.D_KEY);
                    System.out.println(sb2.toString());
                  Map<String,Object>  stockPO=DAOUtil.findFirst(sb2.toString(), null);
                    if (stockPO != null && stockPO.size() > 0){
                        if (!StringUtils.isNullOrEmpty(stockPO.get("COST_AMOUNT")) ){
                            costAmountBeforeB =Double.parseDouble( stockPO.get("COST_AMOUNT").toString()); //库存表入账前成本金额
                        }
                    }
                    calBorrowQuantity(dealerCode, stockItemPO);
                  //更新库存最新入库日期
                    TmPartStockPO partStockPO =TmPartStockPO.
                            findByCompositeKeys(dealerCode,borrowItemPO.get("PART_NO"),borrowItemPO.get("STORAGE_CODE"));
                    if (StringUtils.isNullOrEmpty(dealerCode) || StringUtils.isNullOrEmpty(borrowItemPO.get("PART_NO")) || 
                       StringUtils.isNullOrEmpty(borrowItemPO.get("STORAGE_CODE"))){
                        throw new ServiceBizException("丢失主键！");
                    }
                    partStockPO.setDate("LAST_STOCK_IN", Utility.getCurrentDateTime());
                    partStockPO.saveIt();
                    
                    StringBuilder sb3= new StringBuilder("SELECT DEALER_CODE, PART_NO, STORAGE_CODE,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,");
                    sb3.append("MAX_STOCK,MIN_STOCK,BORROW_QUANTITY,LEND_QUANTITY,LOCKED_QUANTITY,PART_MODEL_GROUP_CODE_SET,PART_SPE_TYPE,IS_SUGGEST_ORDER,MONTHLY_QTY_FORMULA,LAST_STOCK_IN,");
                    sb3.append("  LAST_STOCK_OUT,FOUND_DATE,PART_MAIN_TYPE,ISAUTO_MAXMIN_STOCK,SAFE_STOCK,REMARK   FROM  TM_PART_STOCK  WHERE DEALER_CODE='"+dealerCode+"'");
                    sb3.append("   AND STORAGE_CODE='"+borrowItemPO.get("STORAGE_CODE")+"'");
                    sb3.append("   AND PART_NO='"+borrowItemPO.get("PART_NO")+"' AND D_KEY="+CommonConstants.D_KEY);
                    System.out.println(sb3.toString());
                  Map<String,Object>  StockAfter=DAOUtil.findFirst(sb3.toString(), null);
                    if (StockAfter != null && StockAfter.size() > 0) {
                        if (!StringUtils.isNullOrEmpty(StockAfter.get("COST_AMOUNT"))){
                            costAmountAfterB = Double.parseDouble( StockAfter.get("COST_AMOUNT").toString()); //库存表入账后成本金额
                        }
                    }
                  //更新库存批次最新入库日期
                    TmPartStockItemDTO itemPO = new TmPartStockItemDTO();
                    itemPO.setDealerCode(dealerCode);
                    itemPO.setPartNo(borrowItemPO.get("PART_NO").toString());
                    itemPO.setStorageCode(borrowItemPO.get("STORAGE_CODE").toString());
                    if (!StringUtils.isNullOrEmpty(borrowItemPO.get("PART_BATCH_NO"))) {
                        itemPO.setPartBatchNo(borrowItemPO.get("PART_BATCH_NO").toString());
                    } else {
                        itemPO.setPartBatchNo(CommonConstants.defaultBatchName);
                    }
                    System.out.println("__________"+ itemPO.getDealerCode()+":"+itemPO.getPartNo()+":"+itemPO.getStorageCode()+":"+itemPO.getPartBatchNo());
                    TmPartStockItemPO itemPOC = TmPartStockItemPO. findByCompositeKeys(
                          itemPO.getDealerCode(),itemPO.getPartNo(),itemPO.getStorageCode(),itemPO.getPartBatchNo());
                    System.out.println("__________"+itemPOC.toString());
                    itemPOC.setDate("LAST_STOCK_IN",Utility.getCurrentDateTime());
                    if (StringUtils.isNullOrEmpty(dealerCode) || StringUtils.isNullOrEmpty(borrowItemPO.get("PART_NO")) || 
                            StringUtils.isNullOrEmpty(borrowItemPO.get("STORAGE_CODE"))){
                        throw new ServiceBizException("丢失主键！");
                    }
                    itemPOC.saveIt();
                    StringBuilder sb4= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STORAGE_CODE,PART_BATCH_NO,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,");
                    sb4.append("SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,BORROW_QUANTITY,PART_STATUS,LEND_QUANTITY,LAST_STOCK_IN,");
                    sb4.append("LAST_STOCK_OUT,FOUND_DATE,REMARK  FROM  TM_PART_STOCK_ITEM  WHERE DEALER_CODE='"+itemPO.getDealerCode()+"'");
                    sb4.append("   AND STORAGE_CODE='"+itemPO.getStorageCode()+"'");
                    sb4.append("   AND PART_NO='"+itemPO.getPartNo()+"'");
                    sb4.append("   AND PART_BATCH_NO='"+itemPO.getPartBatchNo()+"'");
                   Map<String,Object>  itemAfter=DAOUtil.findFirst(sb4.toString(), null);
                    if (itemAfter != null && itemAfter.size() > 0){
                        if (!StringUtils.isNullOrEmpty(itemAfter.get("COST_AMOUNT")) ){
                            costAmountAfterA =Double.parseDouble( itemAfter.get("COST_AMOUNT").toString());   //批次表入账后成本金额
                        }
                    }
                  //4、向配件流水帐填加一条记录
                    TtPartFlowDTO flowPO = new TtPartFlowDTO();
                    flowPO.setFlowId(null);
                    flowPO.setDealerCode(dealerCode);
                 //   flowPO.setDKey(CommonConstant.D_KEY);
                    flowPO.setStorageCode(borrowItemPO.get("STORAGE_CODE").toString());
                    flowPO.setPartNo(borrowItemPO.get("PART_NO").toString());
                    flowPO.setPartBatchNo(borrowItemPO.get("PART_BATCH_NO").toString());
                    flowPO.setPartName(borrowItemPO.get("PART_NAME").toString());
                    
                    /*
ITEM_ID,BORROW_NO,DEALER_CODE,STORAGE_CODE,PART_BATCH_NO,PART_NO,PART_NAME,UNIT_CODE
IN_QUANTITY,WRITE_OFF_QUANTITY,COST_PRICE,COST_AMOUNT,IN_PRICE,IN_AMOUNT 
                     */
                    flowPO.setSheetNo(borrowItemPO.get("BORROW_NO").toString());
                    flowPO.setInOutType(Utility.getInt(CommonConstants.DICT_IN_OUT_TYPE_PART_BORROW));
                    flowPO.setInOutTag(Utility.getInt(CommonConstants.DICT_IS_NO));
                    flowPO.setStockInQuantity(Float.parseFloat(borrowItemPO.get("IN_QUANTITY").toString()));
                    flowPO.setCostPrice(Double.parseDouble(borrowItemPO.get("COST_PRICE")+""));
                    flowPO.setCostAmount(Double.parseDouble(borrowItemPO.get("COST_AMOUNT")+""));
                    double amount = Utility.add("1", Utility.getPartRate(dealerCode));
                    String rate = Double.toString(amount);
                    flowPO.setInOutNetPrice(Double.parseDouble(borrowItemPO.get("IN_PRICE")+"")); //出入库不含税单价
                    flowPO.setInOutNetAmount(Double.parseDouble(borrowItemPO.get("IN_AMOUNT")+""));//出入库不含税金额
                    flowPO.setInOutTaxedPrice(Utility.mul(borrowItemPO.get("IN_PRICE")+"", rate));//出入库含税单价
                    flowPO.setInOutTaxedAmount(Utility.mul(borrowItemPO.get("IN_AMOUNT")+"", rate));//出入库含税金额
                    flowPO.setCustomerCode(customerCode);
                    flowPO.setCustomerName(customerName);
                    flowPO.setOperator(empNo);
                    flowPO.setOperateDate(new Date(System.currentTimeMillis()));
                    StringBuilder sb5= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STORAGE_CODE,PART_BATCH_NO,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,");
                    sb5.append("SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,BORROW_QUANTITY,PART_STATUS,LEND_QUANTITY,LAST_STOCK_IN,");
                    sb5.append("LAST_STOCK_OUT,FOUND_DATE,REMARK  FROM  TM_PART_STOCK_ITEM  WHERE DEALER_CODE='"+dealerCode+"'");
                    sb5.append("   AND STORAGE_CODE='"+borrowItemPO.get("STORAGE_CODE")+"'");
                    sb5.append("   AND PART_NO='"+borrowItemPO.get("PART_NO")+"'");
                    sb5.append("   AND PART_BATCH_NO='"+borrowItemPO.get("PART_BATCH_NO")+"'");
                     listitemstock=DAOUtil.findFirst(sb5.toString(), null);
                    if (listitemstock != null && listitemstock.size() > 0) {
                        flowPO.setStockQuantity(Float.parseFloat(listitemstock.get("STOCK_QUANTITY").toString()));
                    }
                    flowPO.setCostAmountBeforeA(costAmountBeforeA);
                    flowPO.setCostAmountBeforeB(costAmountBeforeB);
                    flowPO.setCostAmountAfterA(costAmountAfterA);
                    flowPO.setCostAmountAfterB(costAmountAfterB);
                    PartFlowPO partFlowPO=new PartFlowPO(); 
                    setPartFlowPO(partFlowPO,flowPO);
                    partFlowPO.saveIt();
                    /**
                     * 如果配件帐面库存为0成本单价为0则把借入不含税单价记入库存成本单价
                     */
                   updateBorrowCostPrice(Double.parseDouble(borrowItemPO.get("IN_PRICE").toString()), borrowItemPO.get("PART_NO").toString(), 
                                         borrowItemPO.get("STORAGE_CODE").toString(), dealerCode);
                }
            }
        }
        
        
        
        
    }
    /**
     *  借进登记时如果该配件库存为0成本单价为0则以借进的不含税单价做成本价修改库存,批次 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月13日
    * @param costPrice
    * @param partNo
    * @param storageCode
    * @param dealerCode
     */
    private void updateBorrowCostPrice(double costPrice, String partNo, String storageCode, String dealerCode) {
        StringBuffer stock = new StringBuffer("UPDATE TM_PART_STOCK SET COST_PRICE = CASE WHEN STOCK_QUANTITY=0 AND COST_PRICE=0 THEN ");
        stock.append(costPrice+ " ELSE COST_PRICE END WHERE DEALER_CODE='"+ dealerCode);
        stock.append("' AND D_KEY=" + CommonConstants.D_KEY + " AND PART_NO='" + partNo + "'");
        
        StringBuffer stockItem = new StringBuffer("UPDATE TM_PART_STOCK_ITEM SET COST_PRICE = CASE WHEN STOCK_QUANTITY=0 ");
         stockItem.append( "AND COST_PRICE=0 THEN "+ costPrice + " ELSE COST_PRICE END WHERE DEALER_CODE='" );
         stockItem.append(dealerCode+ "' AND D_KEY=" + CommonConstants.D_KEY + " AND PART_NO='" + partNo + "' ");
         Base.exec(stock.toString());
         Base.exec(stockItem.toString());
    }
    /**
     * 设置PartFlowPO对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月13日
    * @param partFlowPO
    * @param flowPO
     */
    private void setPartFlowPO(PartFlowPO partFlowPO, TtPartFlowDTO flow) {
        partFlowPO.setString("CUSTOMER_CODE",flow.getCustomerCode());
        partFlowPO.setString("CUSTOMER_NAME",flow.getCustomerName());
        partFlowPO.setString("PART_NO",flow.getPartNo());
        partFlowPO.setString("STORAGE_CODE",flow.getStorageCode());
        partFlowPO.setString("SHEET_NO",flow.getSheetNo());
        partFlowPO.setString("PART_NAME",flow.getPartName());
        partFlowPO.setString("LICENSE",flow.getLicense());
        partFlowPO.setString("DEALER_CODE",flow.getDealerCode());
        partFlowPO.setString("OPERATOR",flow.getOperator());
        partFlowPO.setString("PART_BATCH_NO",flow.getPartBatchNo());
        partFlowPO.setFloat("STOCK_QUANTITY", flow.getStockQuantity());
        partFlowPO.setFloat("STOCK_IN_QUANTITY", flow.getStockInQuantity());
        partFlowPO.setDouble("COST_AMOUNT_BEFORE_A", flow.getCostAmountBeforeA());
        partFlowPO.setDouble("COST_AMOUNT_BEFORE_B", flow.getCostAmountBeforeB());
        partFlowPO.setDouble("COST_AMOUNT_AFTER_A", flow.getCostAmountAfterA());
        partFlowPO.setDouble("COST_AMOUNT_AFTER_B", flow.getCostAmountAfterB());
        partFlowPO.setDouble("IN_OUT_TAXED_PRICE", flow.getInOutTaxedPrice());
        partFlowPO.setDouble("IN_OUT_TAXED_AMOUNT", flow.getInOutTaxedAmount());
        partFlowPO.setDouble("IN_OUT_NET_PRICE", flow.getInOutNetPrice());
        partFlowPO.setDouble("IN_OUT_NET_AMOUNT", flow.getInOutNetAmount());
        partFlowPO.setDouble("COST_PRICE", flow.getCostPrice());
        partFlowPO.setDouble("COST_AMOUNT", flow.getCostAmount());
        partFlowPO.setDate("OPERATE_DATE", flow.getOperateDate());
        partFlowPO.setLong("FLOW_ID", flow.getFlowId());
        partFlowPO.setInteger("IN_OUT_TAG", flow.getInOutTag());
        partFlowPO.setInteger("IN_OUT_TYPE", flow.getInOutType());
    }
    /**
     * 更新配件库存批次表库存数量借进数量
    * TODO description
    * @author yujiangheng
    * @date 2017年5月13日
    * @param dealerCode
    * @param stockItemPO
     */
    private void calBorrowQuantity(String dealerCode, TmPartStockItemDTO itemPO) {
        StringBuffer sqlitem = new StringBuffer(" update TM_PART_STOCK_ITEM set BORROW_QUANTITY=BORROW_QUANTITY+"+ itemPO.getStockQuantity());
        sqlitem.append(" where DEALER_CODE='" + dealerCode + "'  and D_KEY=" + CommonConstants.D_KEY+ "  and PART_NO='" + itemPO.getPartNo()+ "'" );
        sqlitem.append("and  STORAGE_CODE='" + itemPO.getStorageCode() + "'");
        sqlitem.append(" and PART_BATCH_NO='" + itemPO.getPartBatchNo() + "'");
        
        StringBuffer sql = new StringBuffer(" update TM_PART_STOCK set BORROW_QUANTITY=BORROW_QUANTITY+" + itemPO.getStockQuantity() );
        sql.append(" where DEALER_CODE='" + dealerCode + "'  and D_KEY=" + CommonConstants.D_KEY + "  and PART_NO='" + itemPO.getPartNo() + "'");
        sql.append(" and STORAGE_CODE='" + itemPO.getStorageCode() + "'");
      //更新配件库存批次表借出数量
        System.out.println(sqlitem.toString());
        Base.exec(sqlitem.toString());
        //更新配件库存表借出数量
        Base.exec(sql.toString());
        logger.debug(sqlitem.toString());
        logger.debug(sql.toString());
    }
    /**
     * 查询业务表 sheetTable 关联Tm_part_info 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月12日
    * @param dealerCode
    * @param sheetTable
    * @param sheetName
    * @param sheetNo
    * @return
     */
    private List<Map> getNonOemPartList(String dealerCode,String sheetTable,String sheetName,String sheetNo) {
        
        if (Utility.getDefaultValue(CommonConstants.DEFAULT_PARA_OEM_PART_IN_CHECK).equals(CommonConstants.DICT_IS_YES)){
            return null;
        }
        StringBuffer sql = new StringBuffer(" select A.DEALER_CODE, A.PART_NO,A.PART_NAME,A.STORAGE_CODE from  "+sheetTable+" A ");
        sql.append(" left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.dealer_code = B.dealer_code) " );
        sql.append(" WHERE A.dealer_code = '"+dealerCode+"' " );
        sql.append(" AND B.DOWN_TAG =  "+CommonConstants.DICT_IS_NO+" " );
        sql.append(" AND A.STORAGE_CODE ='OEMK'");
        sql.append(" AND A."+sheetName+" = '"+sheetNo+"'");
        return  DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 通过相关单号查询 对应配件是否删除
    * TODO description
    * @author yujiangheng
    * @date 2017年5月12日
    * @param dealerCode
    * @param sheetTable
    * @param sheetFieldName
    * @param sheetNo
    * @return
     */
    private String checkPartStockIsDeleteBySheetNo(String dealerCode,String sheetTable,String sheetFieldName,String sheetNo) {
        // 开关未打开直接退出
        if (Utility.getDefaultValue(CommonConstants.DEFAULT_PARA_PART_STOCK_DELETE).equals(CommonConstants.DICT_IS_NO)){
            return "";
        }
        String returnPartNo = "";
        StringBuffer sql =new StringBuffer( "select B.D_KEY,A.PART_NO,A.PART_NAME,A.STORAGE_CODE from "+sheetTable+ " A " );
        sql.append(" LEFT JOIN tm_part_stock B ON (a.dealer_code = b.dealer_code and a.part_no = b.part_no and a.STORAGE_CODE = b.STORAGE_CODE) " );
        sql.append(" WHERE A."+sheetFieldName+" = '"+sheetNo+"' " );
            sql.append(" and A.dealer_code = '"+dealerCode+"' AND B.D_KEY = "+CommonConstants.DEFAULT_PARA_PART_DELETE_KEY);
            List<Map> returnList=DAOUtil.findAll(sql.toString(), null);
            if (returnList != null && returnList.size() > 0){
                for(int i=0; i< returnList.size(); i++){
                    Map<String,String> result=returnList.get(i);
                    if (returnPartNo.equals("")){
                        returnPartNo = result.get("PART_NO");
                    }else{
                        returnPartNo = returnPartNo + ", "+result.get("PART_NO");   
                    }           
                }
            }
        return returnPartNo;
    }
    /**
     * 入账前判断
    * TODO description
    * @author yujiangheng
    * @date 2017年5月12日
     */
    private void QueryMonthPeriodIsFinished() {
        //查询本月的报表是否完成
        //   List<Map> partMonth=Utility.isFinishedThisMonth();
           if (Utility.isFinishedThisMonth()==null) {
             throw new ServiceBizException("当前配件月报没有正确执行!");
           }   
           //查询当前时间的会计周期是否做过月结
           if (Utility.getIsFinished()==null) {
               throw new ServiceBizException("当前配件会计月报没有正确执行!");
             }   
        
    }
    
    
    
}
