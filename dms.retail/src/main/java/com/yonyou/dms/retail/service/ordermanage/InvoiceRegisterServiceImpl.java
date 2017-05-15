
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.retail
 *
 * @File name : InvoiceRegisterServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月28日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月28日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.retail.service.ordermanage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SoInvoicePO;
import com.yonyou.dms.common.domains.PO.basedata.TmWxOwnerChangePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusRelationPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS008;
import com.yonyou.dms.gacfca.SADMS008add;
import com.yonyou.dms.gacfca.SADMS095;
import com.yonyou.dms.gacfca.SEDMS022;
import com.yonyou.dms.gacfca.SOTDCS015ND;
import com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO;

/**
 * 开票登记实现类
 * @author DuPengXin
 * @date 2016年9月28日
 */
@Service
public class InvoiceRegisterServiceImpl implements InvoiceRegisterService{
    private static final Logger logger = LoggerFactory.getLogger(InvoiceRegisterServiceImpl.class);
    @Autowired
    SADMS008 SADMS008;
    @Autowired
    SADMS008add SADMS008add;
    @Autowired
    SOTDCS015ND SOTDCS015ND;
    @Autowired
    SEDMS022 SEDMS022;
    @Autowired
    SADMS095 SADMS095;
    @Autowired
    private CommonNoService     commonNoService;
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 查询
     * @author DuPengXin
     * @date 2016年9月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#queryInvoiceRegister(java.util.Map)
     */

    @Override
    public PageInfoDto queryInvoiceRegister(Map<String, String> queryParam) throws ServiceBizException {
        String sql = "";
        String invoiceNo=queryParam.get("invoiceNo");
        String vin=queryParam.get("vin");
        String certificateNo=queryParam.get("certificateNo");
        String license=queryParam.get("license");
        String soNo=queryParam.get("soNo");
//        Timestamp invoiceDateBegin=queryParam.get("invoiceDateBegin");
//        Timestamp invoiceDateEnd=queryParam.get("invoiceDateEnd");
        String invoiceChargeType=queryParam.get("invoiceChargeType"); 
        String remainingPara = commonNoService.getDefalutPara("3322");
//      sql = "SELECT A.* ,b.SO_STATUS FROM TT_SO_INVOICE A, TT_SALES_ORDER B ";
//      sql += "WHERE A.ENTITY_CODE = B.ENTITY_CODE AND A.D_KEY = B.D_KEY AND A.SO_NO = B.SO_NO ";
        sql = "SELECT A.*,IT.INVOICE_TYPE_NAME,EM1.USER_NAME AS NAME1,EM2.USER_NAME AS NAME2,EM3.USER_NAME AS NAME3,b.EC_ORDER_NO,b.SO_STATUS,C.IS_DIRECT,B.BUSINESS_TYPE,D.CUS_SOURCE,(" + remainingPara + " - A.FEE_VEHICLE_RESCAN_TIMES) FEE_VEHICLE_RESCAN_REMAINING FROM TT_SO_INVOICE A INNER JOIN TT_SALES_ORDER B";
        sql += " ON A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY AND A.SO_NO = B.SO_NO";
        sql += " LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN";
        sql += " LEFT JOIN TM_USER EM1 ON A.dealer_code = EM1.dealer_code AND A.INVOICE_WRITER = EM1.USER_ID";
        sql += " LEFT JOIN TM_USER EM2 ON A.dealer_code = EM2.dealer_code AND A.TRANSACTOR = EM2.USER_ID";
        sql += " LEFT JOIN TM_USER EM3 ON A.dealer_code = EM3.dealer_code AND A.RECORDER = EM3.USER_ID";
        sql += " LEFT JOIN TM_INVOICE_TYPE IT ON A.dealer_code = IT.dealer_code AND A.INVOICE_TYPE_CODE = IT.INVOICE_TYPE_CODE";
        sql += " LEFT JOIN Tm_Potential_Customer D ON B.dealer_code = D.dealer_code AND B.CUSTOMER_NO = D.CUSTOMER_NO";
        sql += " WHERE 1 = 1";
        sql += " AND A.dealer_code = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ";
        sql += " AND A.D_KEY = " + CommonConstants.D_KEY;
        if (!StringUtils.isNullOrEmpty(queryParam.get("businessType")))
            sql += " AND B.BUSINESS_TYPE = " + queryParam.get("businessType");
        if (!StringUtils.isNullOrEmpty(queryParam.get("soStatus"))){
            sql+=" and b.SO_STATUS="+queryParam.get("soStatus");
        }
     
        if (!StringUtils.isNullOrEmpty(queryParam.get("invoiceCustomer"))){
            sql += Utility.getLikeCond("A", "INVOICE_CUSTOMER", queryParam.get("invoiceCustomer"), "AND");
        }
          
        if (invoiceNo != null && !invoiceNo.trim().equals(""))
            sql += Utility.getLikeCond("A", "INVOICE_NO", invoiceNo, "AND");
        if (vin != null && !vin.trim().equals(""))
            sql += Utility.getLikeCond("A", "VIN", vin, "AND");
        if (certificateNo != null && !certificateNo.trim().equals(""))
            sql += Utility.getLikeCond("A", "CERTIFICATE_NO", certificateNo, "AND");
        if (license != null && !license.trim().equals(""))
            sql += Utility.getLikeCond("B", "LICENSE", license, "AND");
        if (soNo != null && !soNo.trim().equals(""))
            sql += Utility.getLikeCond("B", "SO_NO", soNo, "AND");
        /*if (invoiceDateBegin != null && invoiceDateEnd != null)
            sql += " AND A.INVOICE_DATE BETWEEN ? AND ? ";*/
    
       // sql+=Utility.getDateCond("A", "INVOICE_DATE", queryParam.get("invoiceDateBegin"), (queryParam.get("invoiceEndDate")));
        if (invoiceChargeType != null && !invoiceChargeType.trim().equals(""))
            sql += " AND A.INVOICE_CHARGE_TYPE = " + invoiceChargeType;
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("invoiceBeginDate"))) {
            sql+=" and A.INVOICE_DATE>= ?";
            //sql.append(" and A.INVOICE_DATE>= ?");
            params.add(DateUtil.parseDefaultDate(queryParam.get("invoiceBeginDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("invoiceEndDate"))) {
            sql+=" and A.INVOICE_DATE<?";
            params.add(DateUtil.addOneDay(queryParam.get("invoiceEndDate")));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
        return pageInfoDto;
    }
    //开票登记
    @Override
    public PageInfoDto qrySRSForInvoiceSet(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();  
        StringBuffer sql = new StringBuffer("");
     
            sql.append(" SELECT A.DEALER_CODE,A.CUSTOMER_NAME,em1.USER_NAME AS LOCK_BY,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
            sql.append( " br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,em.USER_NAME,tu.USER_NAME AS LOCK_NAME,B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ,A.VIN,A.SOLD_BY,A.VEHICLE_PRICE, ");
            sql.append( " A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CT_CODE,A.CERTIFICATE_NO,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE ");
            sql.append( " FROM TT_SALES_ORDER A  ");
            sql.append( " LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY ");
            sql.append( " LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN");
            sql.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
            sql.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
            sql.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
            sql.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
            sql.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
            sql.append( " left join TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");
            sql.append( " left join TM_USER em1  on A.LOCK_USER=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE"); 
            sql.append( " left join TM_USER tu  on A.LOCK_USER=tu.USER_ID AND A.DEALER_CODE=tu.DEALER_CODE"); 
            sql.append( " WHERE  A.D_KEY = ");
    		sql.append( DictCodeConstants.D_KEY);
                
         
/**
 * 审核订单和实际开票的人不一定是一个人，开票的人一班都是收银的人，也就是系统上面收款的人，也就是随便哪个财务都可以，只要有收款权限，都可以开票
狂奔的大雨 12:10:28
所以这个地方有没有必要绑定需要去了解一下  
狂奔的大雨 13:02:32
确认好了，开票这个权限放给所有的销售财务吧

          
 */         //使用参数隐藏‘已退回’销售单，即后台不查询，前台不显示
            if (!commonNoService.getDefalutPara("3322").equals(DictCodeConstants.DICT_IS_YES)){
                
                sql.append(" and A.SO_STATUS <> "+DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD);
            }   
                sql.append(" and a.so_no not in  (select OLD_SO_NO from tt_sales_order where  dealer_code=A.dealer_code and OLD_SO_NO is not null and OLD_SO_NO<>'' and BUSINESS_TYPE=13001005) ");     
            sql.append(" and A.BUSINESS_TYPE <> "+DictCodeConstants.DICT_SO_TYPE_RERURN); 
            //销售开票登记，选择为：完成或关单状态的销售订单 新增需求 CHRYSLER-245  DICT_SO_STATUS_HAVE_OUT_STOCK
            sql.append(" and (A.SO_STATUS= "+DictCodeConstants.DICT_SO_STATUS_CLOSED+" or A.SO_STATUS= "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+")" );
                sql.append(Utility.getStrCond("A", "dealer_code", FrameworkUtil.getLoginInfo().getDealerCode()));
                sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"),"AND"));
                sql.append(Utility.getLikeCond("A", "CONTRACT_NO", queryParam.get("contractNo"),"AND"));
                sql.append(Utility.getLikeCond("A", "CUSTOMER_NAME",queryParam.get("customerName"),"AND"));
                sql.append(Utility.getLikeCond("A", "SO_NO", queryParam.get("soNo"),"AND"));
        
            
        return DAOUtil.pageQuery(sql.toString(), params);
        
    }
    //零售变更
    @Override
    public PageInfoDto qrySRSForInvoiceSetrefund(Map<String, String> queryParam) throws ServiceBizException {
        
        List<Object> params = new ArrayList<Object>();  
        StringBuffer sql = new StringBuffer("");
        sql
        .append(" SELECT A.DEALER_CODE,E.ITEM_ID,E.VIN,E.SO_NO,A.CUSTOMER_TYPE,A.CUSTOMER_NAME,E.CT_CODE,E.CERTIFICATE_NO,D.ZIP_CODE,A.ASSESSED_PRICE,A.OLD_CAR_PURCHASE,A.OLD_BRAND_CODE," +
                " A.OLD_SERIES_CODE,A.OLD_MODEL_CODE,A.CUSTOMER_NO,A.IS_PERMUTED,A.PERMUTED_VIN,A.PERMUTED_DESC,A.ASSESSED_LICENSE,A.FILE_OLD_A,A.FILE_URLOLD_A, " +
                " D.GENDER,D.CONTACTOR_MOBILE,D.ADDRESS,E.INVOICE_DATE,E.INVOICE_AMOUNT,E.INVOICE_CHARGE_TYPE," +                   
                " E.INVOICE_NO,E.INVOICE_TYPE_CODE,E.INVOICE_CUSTOMER,E.INVOICE_WRITER,E.TRANSACTOR,E.REMARK," +
                " E.RETIAL_CHANGE_TIMES,E.RECORD_DATE,D.CUS_SOURCE FROM TT_SO_INVOICE E " +                         
                " inner join TT_sales_order A ON A.DEALER_CODE = E.DEALER_CODE AND A.D_KEY = E.D_KEY AND A.SO_NO = E.SO_NO "+   
                " LEFT join TM_POTENTIAL_CUSTOMER D ON A.DEALER_CODE = D.DEALER_CODE AND  A.CUSTOMER_NO = D.CUSTOMER_NO "+  
                 " LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN "
                + " WHERE  E.INVOICE_CHARGE_TYPE=13181001 AND  A.D_KEY = "+DictCodeConstants.D_KEY+"  "
                
    );
    sql.append(Utility.getStrCond("A", "DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode()));
    sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"),"AND"));
    sql.append(Utility.getLikeCond("E", "INVOICE_CUSTOMER",queryParam.get("customerName"),"AND"));
    sql.append(Utility.getLikeCond("A", "SO_NO", queryParam.get("soNo"),"AND"));
    sql.append(" ORDER BY E.RECORD_DATE DESC"); 
        return DAOUtil.pageQuery(sql.toString(), params);
        
    }
    /**
     * 新增
     * @author DuPengXin
     * @date 2016年9月28日
     * @param irdto
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#addInvoiceRegister(com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO)
     */

    @Override
    public String addInvoiceRegister(InvoiceRegisterDTO irdto) throws ServiceBizException {
        long userId = FrameworkUtil.getLoginInfo().getUserId();
        String entityCode =  FrameworkUtil.getLoginInfo().getDealerCode();
//        String[] status = (String[]) actionContext
//                .getArrayValue("TT_SO_INVOICE.UPDATE_STATUS");
//        String[] recordId = (String[]) actionContext
//                .getArrayValue("TT_SO_INVOICE.RECORD_ID");
//        String[] itemId = (String[]) actionContext
//                .getArrayValue("TT_SO_INVOICE.ITEM_ID");
        String soNo = irdto.getSoNo();
        String vin = irdto.getVin();
        String customerNo=null;
        String invoiceTypeCode = irdto.getInvoiceTypeCode();          
        String invoiceNo = irdto.getInvoiceNo();
        Integer invoiceChargeType = irdto.getInvoiceChargeType();
        Double invoiceAmount = irdto.getInvoiceAmount();
        String invoiceCustomer = irdto.getInvoiceCustomer();
        String invoiceWriter = irdto.getInvoiceWriter();
        Date invoiceDate =irdto.getInvoiceDate();
        Integer ctCode =irdto.getCtCode();
        String certificateNo = irdto.getCertificateNo();
        Long transactor = irdto.getTransactor();
        Long recorder = irdto.getRecorder();
        Date recordDate = irdto.getRecordDate();
        String remark = irdto.getRemark();
        
        
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        String currdate=sf.format(new Date());
        SimpleDateFormat sf2=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currdate2=sf2.format(new Date()); 
         String oldCerNo = irdto.getOldCerNo();
                    
        // 取得数据库连接参数
    //    String photo =actionContext.getStringValue("PHOTO");
         String wxFlag =irdto.getWxFlag();
   //     Connection conn = (Connection) actionContext.getConnection();
     //   String groupCode=Utility.getGroupEntity(conn, entityCode, "TM_VEHICLE");
         String isChange;
         if (oldCerNo.equals(certificateNo)){
              isChange ="12781002";   
         }else{
              isChange ="12781001"; 
         }
      
        if(invoiceChargeType==13181001){
            List<Object> cus = new ArrayList<Object>();
            cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus.add(soNo);
            cus.add(DictCodeConstants.D_KEY);
            cus.add(DictCodeConstants.DICT_INVOICE_FEE_VEHICLE);
            List<SalesOrderPO> listdt = SalesOrderPO.findBySQL("select * from Tt_So_Invoice where DEALER_CODE= ? AND SO_NO= ? and d_key=? and Invoice_Charge_Type=? ",cus.toArray());                                             
            if(listdt!=null && listdt.size()>0){
                throw new ServiceBizException("单号为"+soNo+"的销售单已经开据过费用类型为购车费用的发票");
                       
            }
            }
//        Long id = commonNoService.getId("ID");  
        SoInvoicePO po = new SoInvoicePO();
//        po.setLong("Item_id", id);
        po.setString("SO_NO", soNo);
        po.setString("FILE_URL", irdto.getFileUrl());
        po.setString("FILE_ID", irdto.getFileUrl());
        po.setString("VIN", vin);
        if(invoiceChargeType==13181001){
            po.setInteger("IDENTIFY_STATUS",17181002);
        }
        po.setString("Invoice_Type_Code",invoiceTypeCode);
        po.setString("Invoice_No",invoiceNo);
        po.setInteger("Invoice_Charge_Type",invoiceChargeType);
        po.setDouble("Invoice_Amount",invoiceAmount);
        po.setString("invoice_Customer",invoiceCustomer);
        po.setString("invoice_Writer",invoiceWriter);
        po.setDate("Invoice_Date",invoiceDate);
        po.setInteger("Ct_Code",ctCode);
        po.setString("Certificate_No",certificateNo);
        po.setString("Transactor",transactor);
        po.setLong("Recorder",recorder);
        po.setDate("Record_Date",currdate);
        po.setString("Remark",remark);
        po.setInteger("Is_Valid",12781001);
     
        po.setInteger("D_Key",DictCodeConstants.D_KEY);
        po.saveIt(); 
        
        if(invoiceChargeType==Integer.parseInt(DictCodeConstants.DICT_INVOICE_FEE_VEHICLE) 
                && wxFlag!=null && DictCodeConstants.DICT_IS_YES.equals(wxFlag)){
            List<Object> cus1 = new ArrayList<Object>();
            cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus1.add(vin);
            List<VehiclePO> vList = VehiclePO.findBySQL("select * from Tm_Vehicle where DEALER_CODE= ? AND vin=? ",cus1.toArray());                              
                if (vList!=null && vList.size()> 0 ) {
                    VehiclePO  poSource= (VehiclePO) vList.get(0);
                    if(!StringUtils.isNullOrEmpty(poSource.get("OWNER_NO"))&&(!"888888888888".equals(poSource.get("OWNER_NO").toString()))&&(!"999999999999".equals(poSource.get("OWNER_NO").toString()))){
                        List<Object> cus2 = new ArrayList<Object>();
                        cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
                        cus2.add(vin);
                        List<TmWxOwnerChangePO> vList2 = TmWxOwnerChangePO.findBySQL("select * from Tm_Wx_Owner_Change where DEALER_CODE= ? AND vin=? ",cus1.toArray()); 
                     if(vList2.size()>0){
                        if (vList2.get(0).getString("VIN")!=null){
                            CarownerPO vehiclepoT = CarownerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),poSource.get("Owner_No"));                         
                            vehiclepoT.setString("Address",vList2.get(0).get("Address"));//地址
                            vehiclepoT.setString("Owner_Name",vList2.get(0).get("Owner_Name"));//客户姓名
                            vehiclepoT.setString("Phone",vList2.get(0).get("Phone"));
                            vehiclepoT.setString("Mobile",vList2.get(0).get("Mobile"));//客户手机
                            if (!StringUtils.isNullOrEmpty(vList2.get(0).get("Gender")))
                            vehiclepoT.setInteger("Gender",vList2.get(0).get("Gender"));//性别
                            vehiclepoT.setString("E_Mail",vList2.get(0).get("E_Mail"));//email
                            vehiclepoT.setInteger("Zip_Code",vList2.get(0).get("Zip_Code"));//邮编
                            vehiclepoT.saveIt();
                        }
                     }
                    }
                }
            }
        if(invoiceChargeType==Integer.parseInt(DictCodeConstants.DICT_INVOICE_FEE_VEHICLE)){
         
            List cancelList = new ArrayList();
            cancelList =(ArrayList)this.queryOrderNeedCancel(soNo, vin);
            if (cancelList!=null && cancelList.size()>0){
                for(int k=0;k<cancelList.size();k++){
                    SoInvoicePO po1 =(SoInvoicePO) cancelList.get(k);
                    SoInvoicePO po2 = SoInvoicePO.findByCompositeKeys(po1.get("ITEM_ID"),FrameworkUtil.getLoginInfo().getDealerCode());                         
                    po2.setInteger("Is_Valid",Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                    po2.setDate("Cancel_Date",currdate2);
                    po2.saveIt();
                }
            }
            
        }
        if(invoiceChargeType==Integer.parseInt(DictCodeConstants.DICT_INVOICE_FEE_VEHICLE)){
            List<Object> cus4 = new ArrayList<Object>();
            cus4.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus4.add(vin);
            List<VehiclePO> vList2 = VehiclePO.findBySQL("select * from tm_vehicle where DEALER_CODE= ? AND vin=? ",cus4.toArray()); 
            VehiclePO tmvpo2 = new VehiclePO();  
            tmvpo2=(VehiclePO) vList2.get(0);
            tmvpo2.setDate("Sales_Date",invoiceDate);
            tmvpo2.setDate("Wrt_Begin_Date",invoiceDate);
            tmvpo2.setString("Invoice_No",invoiceNo);
            tmvpo2.saveIt();
            }
        if((isChange.equals("12781001")) && Utility.testString(ctCode) && Utility.testString(certificateNo)){
            List<Object> cus5 = new ArrayList<Object>();
            cus5.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus5.add(soNo);
            List<SalesOrderPO> orderPo = SalesOrderPO.findBySQL("select * from Tt_Sales_Order where DEALER_CODE= ? AND so_no=? and d_key=0",cus5.toArray()); 
           // TtSalesOrderPO orderPo1 = new TtSalesOrderPO();
            SalesOrderPO orderPo2 = new SalesOrderPO();
           
            if(orderPo != null){
                CarownerPO tmPo1 = CarownerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),orderPo.get(0).get("Owner_No"));                                
                tmPo1.setInteger("Ct_Code",ctCode);
                tmPo1.setString("Certificate_No",certificateNo);
                tmPo1.saveIt();                           
            }
            
            orderPo2 =(SalesOrderPO)orderPo.get(0);
            orderPo2.setInteger("Ct_Code",ctCode);
            orderPo2.setString("Certificate_No",certificateNo);
            orderPo2.saveIt();
            
            List<Object> cus4 = new ArrayList<Object>();
            cus4.add(FrameworkUtil.getLoginInfo().getDealerCode());
            cus4.add(vin);
            List<VehiclePO> tvPo = VehiclePO.findBySQL("select * from tm_vehicle where DEALER_CODE= ? AND vin=? ",cus4.toArray());   
            if(tvPo != null&&!StringUtils.isNullOrEmpty(tvPo.get(0).get("CUSTOMER_NO"))){
                customerNo=tvPo.get(0).get("CUSTOMER_NO").toString();
                CustomerPO cusPo2 = CustomerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),tvPo.get(0).get("CUSTOMER_NO"));
                cusPo2.setInteger("Ct_Code",ctCode);
                cusPo2.setString("Certificate_No",certificateNo);
                cusPo2.saveIt();
                
                CarownerPO ownerPo2 = CarownerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),tvPo.get(0).get("owner_no"));
               
                ownerPo2.setInteger("Ct_Code",ctCode);
                ownerPo2.setString("Certificate_No",certificateNo);
                ownerPo2.saveIt();
                
            }       
        }
        int flag1=SADMS008.getSADMS008(vin, customerNo);
        String is_dms=null;
        if(flag1==1){
            System.out.println("flag1=="+flag1);
            is_dms="Y";
        }
        if(flag1==0){
            throw new ServiceBizException("实销上报失败"); 
        }
        int flag2 = SADMS008add.getSADMS008add(customerNo, vin, invoiceDate, is_dms);
        if(flag2==0){
            throw new ServiceBizException("销售信息撞单上报失败"); 
        }
        int flag3=SOTDCS015ND.getSOTDCS015ND(soNo,  invoiceDate);
        if(flag3==0){
            throw new ServiceBizException("客户信息上报失败");
        }
      return soNo;
    }

    @Override
    public String addInvoiceRefund(InvoiceRefundDTO irdto) throws ServiceBizException {
        String cusNo =irdto.getCustomerNo();// 客户编号
        String soNo = irdto.getSoNo();// 订单编号
        String vin = irdto.getVin();// VIN
        String invoiceTypeCode = irdto.getInvoiceTypeCode();// 发票类型
        String invoiceNo = irdto.getInvoiceNo();// 发票编号
        Integer invoiceChargeType = irdto.getInvoiceChargeType();// 费用类型
        String invoiceAmount = irdto.getInvoiceAmount();// 发票金额
        String invoiceCustomer =irdto.getInvoiceCustomer();// 开票客户
        Long invoiceWriter = irdto.getInvoiceWriter();// 开票人员
        Date invoiceDate = irdto.getInvoiceDate();// 开票日期
        Integer ctCode =irdto.getCtCode();// 证件类型
        String certificateNo = irdto.getCertificateNo();// 证件编号
        Long transactor = irdto.getTransactor();// 经办人
        String remark = irdto.getRemark();// 备注
        Integer customerType =irdto.getCustomerType();// 客户类型
        String customerName = irdto.getCustomerName();// 客户名字
        String zipCode = irdto.getZipCode();// 邮编
        Double assessedPrice = irdto.getAssessedPrice();// 评估金额
        Double oldCarPurchase = irdto.getOldCarPurchase();// 收购金额
        String oldBrandCode = irdto.getOldBrandCode();// 品牌
        String oldSeriesCode = irdto.getOldSeriesCode();// 车系
        String oldModelCode =irdto.getOldModelCode();// 车型
        Integer isPermuted = irdto.getIspermuted();// 是否置换
        String permutedVin = irdto.getPermutedVin();// 置换车辆
        String permutedDesc =irdto.getPermutedDesc();// 二手车描述
        String assessedLicense = irdto.getAssessedLisence();// 车牌号
        String fileOldA = irdto.getFileOldA();// 收购协议ID
        String fileUrloldA = irdto.getFileUrloldA();// 收购协议URL
        String gender =irdto.getGender();// 性别
        String contactorMobile = irdto.getContactorMobile();// 手机
        String address =irdto.getAddress();// 地址
        Long itemId = irdto.getItemId();// 地址 
        
        
        PotentialCusPO pocuslist = PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),cusNo);         
        if (Utility.testString(customerType)) {
            pocuslist.setInteger("Customer_Type",customerType);
        }
        if (Utility.testString(customerName)) {
            pocuslist.setString("Customer_Name",customerName);
        }
        if (Utility.testString(zipCode)) {
            pocuslist.setString("Zip_Code",zipCode);
        }
        if (Utility.testString(certificateNo)) {
            pocuslist.setString("Certificate_No",certificateNo);
        }
        if (Utility.testString(ctCode)) {
            pocuslist.setInteger("Ct_Code",ctCode);
        }
        if (Utility.testString(gender)) {
            pocuslist.setInteger("Gender",gender);
        }
        if (Utility.testString(contactorMobile)) {
            pocuslist.setString("Contactor_Mobile",contactorMobile);
        }
        if (Utility.testString(address)) {
            pocuslist.setString("Address",address);
        }
        pocuslist.saveIt();
        //保有客户信息更新
       
        
        List<Object> cus1 = new ArrayList<Object>();
        cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus1.add(cusNo);
        cus1.add(soNo);
        cus1.add(vin);
        List<TtPoCusRelationPO> listPote = TtPoCusRelationPO.findBySQL("select * from Tt_Po_Cus_Relation where DEALER_CODE= ? AND Po_Customer_No=? AND VIN=? AND vin=? ",cus1.toArray());
        TtPoCusRelationPO cusrelist=new TtPoCusRelationPO();
       
        if(listPote.size()>0){
        cusrelist = (TtPoCusRelationPO)listPote.get(0);
        CustomerPO cuspo = CustomerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),cusrelist.get("CUSTOMER_NO"));   
        if (Utility.testString(customerType)) {
            cuspo.setInteger("Customer_Type",customerType);
        }
        if (Utility.testString(customerName)) {
            cuspo.setString("Customer_Name",customerName);
        }
        if (Utility.testString(zipCode)) {
            pocuslist.setString("Zip_Code",zipCode);
        }
        if (Utility.testString(certificateNo)) {
            pocuslist.setString("Certificate_No",certificateNo);
        }
        if (Utility.testString(ctCode)) {
            pocuslist.setInteger("Ct_Code",ctCode);
        }
        if (Utility.testString(gender)) {
            pocuslist.setInteger("Gender",gender);
        }
        if (Utility.testString(contactorMobile)) {
            pocuslist.setString("Contactor_Mobile",contactorMobile);
        }
        if (Utility.testString(address)) {
            pocuslist.setString("Address",address);
        }
     
        pocuslist.saveIt();
        }
        //车主信息
       
   
        VehiclePO veclpo=VehiclePO.findByCompositeKeys(vin,FrameworkUtil.getLoginInfo().getDealerCode());     
        veclpo.setDate("Sales_Date",invoiceDate);
        veclpo.setDate("Wrt_Begin_Date",invoiceDate);
        veclpo.saveIt();
        CarownerPO owlist=CarownerPO.findByCompositeKeys(veclpo.get("owner_no"),FrameworkUtil.getLoginInfo().getDealerCode());
        
        if (Utility.testString(customerName)) {
            owlist.setString("owner_name",customerName);
        }
        if (Utility.testString(zipCode)) {
            owlist.setString("Zip_Code",zipCode);
        }
        if (Utility.testString(certificateNo)) {
            owlist.setString("Certificate_No",certificateNo);
        }
        if (Utility.testString(ctCode)) {
            owlist.setInteger("Ct_Code",ctCode);
        }
        if (Utility.testString(gender)) {
            owlist.setInteger("Gender",gender);
        }
        if (Utility.testString(contactorMobile)) {
            owlist.setString("Contactor_Mobile",contactorMobile);
        }
        if (Utility.testString(address)) {
            owlist.setString("Address",address);
        }
        owlist.saveIt();
        
        //销售订单信息更新
        SalesOrderPO salelist =  SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),soNo);
        if (Utility.testString(customerType)) {
            salelist.setInteger("Customer_Type",customerType);
        }
        if (Utility.testString(customerName)) {
            salelist.setString("Customer_Name",customerName);
        }
        if (Utility.testString(zipCode)) {
            salelist.setString("Zip_Code",zipCode);
        }
        if (Utility.testString(certificateNo)) {
            salelist.setString("Certificate_No",certificateNo);
        }
        if (Utility.testString(ctCode)) {
            salelist.setInteger("Ct_Code",ctCode);
        }
        if (Utility.testString(contactorMobile)) {
            salelist.setString("phone",contactorMobile);
        }
        if (Utility.testString(address)) {
            salelist.setString("Address",address);
        }
        if (Utility.testString(oldCarPurchase)) {
            salelist.setDouble("Old_Car_Purchase",oldCarPurchase);
        }
        if (Utility.testString(oldBrandCode)) {
            salelist.setString("Old_Brand_Code",oldBrandCode);
        }
        if (Utility.testString(oldSeriesCode)) {
            salelist.set("Old_Series_Code",oldSeriesCode);
        }
        if (Utility.testString(oldModelCode)) {
            salelist.setString("Old_Model_Code",oldModelCode);
        }
        if (Utility.testString(isPermuted)) {
            salelist.setInteger("Is_Permuted",isPermuted);
        }
        if (Utility.testString(permutedVin)) {
            salelist.setString("Permuted_Vin",permutedVin);
        }
        if (Utility.testString(permutedDesc)) {
            salelist.setString("Permuted_Desc",permutedDesc);
        }
        if (Utility.testString(assessedLicense)) {
            salelist.setString("Assessed_License",assessedLicense);
        }
        if (Utility.testString(fileOldA)) {
            salelist.setString("File_Old_A",fileOldA);
        }
        if (Utility.testString(fileUrloldA)) {
            salelist.set("File_Urlold_A",fileUrloldA);
        }
      
            salelist.setDate("Invoice_Date",invoiceDate);
       
        if (Utility.testString(invoiceAmount)) {
            salelist.setDouble("Invoice_Amount",invoiceAmount);
        }
        if (Utility.testString(assessedPrice)) {
            salelist.setDouble("Assessed_Price",assessedPrice);
        }
        salelist.saveIt();
    
        //销售开票信息更新
        SoInvoicePO soInlist=SoInvoicePO.findByCompositeKeys(itemId,FrameworkUtil.getLoginInfo().getDealerCode());       
        if (Utility.testString(certificateNo)) {
            soInlist.setString("Certificate_No",certificateNo);
        }
        if (Utility.testString(ctCode)) {
            soInlist.setInteger("Ct_Code",ctCode);
        }
        salelist.setDate("Invoice_Date",invoiceDate);
        
        if (Utility.testString(invoiceAmount)) {
            salelist.setDouble("Invoice_Amount",invoiceAmount);
        }
        if (Utility.testString(invoiceNo)) {
            soInlist.set("Invoice_No",invoiceNo);
        }
        if (Utility.testString(invoiceChargeType)) {
            soInlist.setInteger("Invoice_Charge_Type",invoiceChargeType);
        }
        if (Utility.testString(invoiceTypeCode)) {
            soInlist.setString("Invoice_Type_Code",invoiceTypeCode);
        }
        if (Utility.testString(invoiceCustomer)) {
            soInlist.setString("Invoice_Customer",invoiceCustomer);
        }
        if (Utility.testString(invoiceWriter)) {
            soInlist.setLong("Invoice_Writer",invoiceWriter);
        }
        if (Utility.testString(transactor)) {
            soInlist.setLong("Transactor",transactor);
        }
        if (Utility.testString(remark)) {
            soInlist.setString("Remark",remark);
        }
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("日期"+format1.format(new Date()));
        soInlist.setString("Retial_Date",format1.format(new Date()));
        soInlist.setString("Retial_Change_Times",1);
        soInlist.saveIt(); 
        String flag=SADMS095.getSADMS095(irdto);
        if("0".equals(flag)){
            throw new ServiceBizException("零售变更信息上报失败！");  
        }
        return null;
    }
	/**
    * 新增是查询发票编号是否存在
    * @author DuPengXin
    * @date 2016年10月28日
    * @param invoiceNo
    * @return
    */
    	
    private boolean SearchInvoiceRegister(String invoiceNo) {
        StringBuilder sb=new StringBuilder("select tso.SO_INVOICE_ID,tso.DEALER_CODE,tso.INVOICE_NO from tt_so_invoice tso where tso.INVOICE_NO=? ");
        List<Object> param=new ArrayList<Object>();
        param.add(invoiceNo);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }
    public static List queryOrderNeedCancel(String soNo, String vin){       
        StringBuffer sql = new StringBuffer("");
        List<Object> param=new ArrayList<Object>();
        sql.append(
                   " SELECT * FROM TT_SO_INVOICE WHERE VIN='" +vin+ "' AND dealer_code='" +FrameworkUtil.getLoginInfo().getDealerCode()+ "' AND SO_NO!='"+soNo+"' AND INVOICE_CHARGE_TYPE="+DictCodeConstants.DICT_INVOICE_FEE_VEHICLE+"  "               
           );   
        List result=DAOUtil.findAll(sql.toString(), param);
        return result;       
    }
    
    /**
    * 新增选择购车费用判断该发票编号是否已是购车费用
    * @author DuPengXin
    * @date 2016年11月16日
    * @param soNoId
    * @param invoiceChargeType
    * @return
    */
    	
    private boolean SearchInvoice(Long soNoId,Integer invoiceChargeType) {
        StringBuilder sb=new StringBuilder("select tso.SO_INVOICE_ID,tso.DEALER_CODE,tso.SO_NO_ID,tso.INVOICE_NO from tt_so_invoice tso where tso.SO_NO_ID=? and tso.INVOICE_CHARGE_TYPE=? and tso.IS_VALID=?");
        List<Object> param=new ArrayList<Object>();
        param.add(soNoId);
        param.add(invoiceChargeType);
        param.add(DictCodeConstants.STATUS_IS_YES);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }
    

    /**
     * 设置对象属性
     * @author DuPengXin
     * @date 2016年9月28日
     * @param ir
     * @param irdto
     */

    private void setInvoiceRegister(SoInvoicePO ir, InvoiceRegisterDTO irdto) {
        ir.setString("VIN", irdto.getVin());//VIN
        ir.setString("INVOICE_CUSTOMER", irdto.getInvoiceCustomer());//开票客户
        ir.setString("INVOICE_NO", irdto.getInvoiceNo());//发票编号
        ir.setString("INVOICE_TYPE_CODE", irdto.getInvoiceTypeCode());//发票类型
        ir.setInteger("INVOICE_CHARGE_TYPE",irdto.getInvoiceChargeType());//费用类型
        ir.setDouble("INVOICE_AMOUNT", irdto.getInvoiceAmount());//发票金额
        ir.setString("TRANSACTOR", irdto.getTransactor());//经办人
        
        ir.setDate("INVOICE_DATE", irdto.getInvoiceDate());//开票日期
        ir.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_YES);//是否有效
    }

    /**
     * 修改
     * @author DuPengXin
     * @date 2016年9月29日
     * @param id
     * @param irdto
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#updateInvoiceRegister(java.lang.Long, com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO)
     */

    @Override
    public void updateInvoiceRegister(Long id, InvoiceRegisterDTO irdto) throws ServiceBizException {
        SoInvoicePO ir = SoInvoicePO.findByCompositeKeys(id,FrameworkUtil.getLoginInfo().getDealerCode());
        if(SearchInvoiceRegisters(irdto.getInvoiceNo(),id)){
            throw new ServiceBizException("该发票编号已经存在");
        }
        setInvoiceRegister(ir, irdto);
        ir.saveIt();
        List<Object> cus4 = new ArrayList<Object>();
        cus4.add(FrameworkUtil.getLoginInfo().getDealerCode());
        cus4.add(ir.getString("VIN"));
        List<VehiclePO> tvPo = VehiclePO.findBySQL("select * from tm_vehicle where DEALER_CODE= ? AND vin=? ",cus4.toArray());  
        String customerNo=null;
        if(tvPo!=null&&tvPo.size()>0){
            customerNo =tvPo.get(0).getString("CUSTOMER_NO");
        }
        int flag1=SADMS008.getSADMS008(ir.getString("VIN"), customerNo);
        String is_dms=null;
        if(flag1==1){
            is_dms="Y";
        }
        if(flag1==0){
            throw new ServiceBizException("实销上报失败"); 
        }
        int flag2 = SADMS008add.getSADMS008add(customerNo, ir.getString("VIN"), irdto.getInvoiceDate(), is_dms);
        if(flag2==0){
            throw new ServiceBizException("销售信息撞单上报失败"); 
        }
        int flag3=SOTDCS015ND.getSOTDCS015ND(irdto.getSoNo(),  irdto.getInvoiceDate());
        if(flag3==0){
            throw new ServiceBizException("客户信息上报失败");
        }
    }
    
    

    
    
    /**
    * @author LiGaoqi
    * @date 2017年4月12日
    * @param id
    * @param irdto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#updateInvoiceRegisterInvoice(java.lang.Long, com.yonyou.dms.retail.domains.DTO.ordermanage.InvoiceRegisterDTO)
    */
    	
    @Override
    public void updateInvoiceRegisterInvoice(Long id, InvoiceRegisterDTO irdto) throws ServiceBizException {
        SoInvoicePO ir = SoInvoicePO.findByCompositeKeys(id,FrameworkUtil.getLoginInfo().getDealerCode());
        int str = ir.getInteger("FEE_VEHICLE_RESCAN_TIMES");
        ir.setInteger("FEE_VEHICLE_RESCAN_TIMES", str+1);
        ir.setString("FILE_URL", irdto.getFileUrl());
        ir.saveIt();
        int flag = SEDMS022.getSEDMS022(id.toString(), irdto.getVin(), irdto.getFileUrl());
        if(flag==3){
            throw new ServiceBizException("车辆VIN为空，请重新查询后再试！");
        }
        if(flag==2){
            throw new ServiceBizException("发票上传信息为空，请重新查询后再试！");
        }
        if(flag==0){
            throw new ServiceBizException("发票补扫上报失败！");
        }

    }
    /**
    * 查询是否有相同的数据
    * @author DuPengXin
    * @date 2016年10月28日
    * @param invoiceNo
    * @param id
    * @return
    */
    	
    private boolean SearchInvoiceRegisters(String invoiceNo, Long id) {
        StringBuilder sb=new StringBuilder("select tso.ITEM_ID,tso.DEALER_CODE,tso.INVOICE_NO from tt_so_invoice tso where tso.INVOICE_NO=? and tso.ITEM_ID <> ?");
        List<Object> param=new ArrayList<Object>();
        param.add(invoiceNo);
        param.add(id);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()>0){
            return true;
        }
        return false;
    }


    /**
     * 根据ID查询
     * @author DuPengXin
     * @date 2016年9月29日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#findById(java.lang.Long)
     */
    public Map findById(Long id)throws ServiceBizException{
        String sql="select tsi.*,tso.CUS_SOURCE from tt_so_invoice tsi LEFT JOIN tt_sales_order tso ON tsi.SO_NO=tso.SO_NO where tsi.ITEM_ID=?";
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findFirst(sql, queryParam);
    }
    
    
    public Map qrySalesOrderRegisterWx(Map<String, String> queryParam) throws ServiceBizException{
        List<Object> params = new ArrayList<Object>();  
        String sql = "SELECT" + 
                " c.dealer_code," +
                " B.SALES_DATE," + 
                " C.CUSTOMER_NO," + 
                " C.CUSTOMER_TYPE," + 
                " C.GENDER," + 
                " C.BIRTHDAY," + 
                " C.AGE_STAGE," + 
                " C.CT_CODE," + 
                " C.CERTIFICATE_NO," + 
                " C.PROVINCE," + 
                " C.CITY," + 
                " C.DISTRICT," + 
                " C.ZIP_CODE," + 
                " C.ADDRESS," + 
                " C.BEST_CONTACT_TYPE," + 
                " C.CONTACTOR_PHONE," + 
                " C.CONTACTOR_MOBILE," + 
                " C.INDUSTRY_FIRST," + 
                " C.INDUSTRY_SECOND," + 
                " C.CUS_SOURCE," + 
                " C.BUY_REASON," + 
                " C.DCRC_SERVICE" + 
                " FROM TT_SALES_ORDER A LEFT JOIN TM_VEHICLE B" + 
                " ON A.dealer_code = B.dealer_code AND A.VIN = B.VIN" + 
                " LEFT JOIN TT_PO_CUS_RELATION D" +
                " ON A.dealer_code = D.dealer_code" +
                " AND A.CUSTOMER_NO = D.PO_CUSTOMER_NO" +
                " AND A.VIN = D.VIN AND D.VEHI_RETURN=12781002 " +
                " LEFT JOIN TM_CUSTOMER C" + 
                " ON D.dealer_code = C.dealer_code AND D.CUSTOMER_NO = C.CUSTOMER_NO" + 
                " WHERE 1=1" + 
                " AND A.dealer_code='"+ FrameworkUtil.getLoginInfo().getDealerCode()+"'" + 
                " AND A.D_KEY="+DictCodeConstants.D_KEY;
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
             sql += " AND A.VIN='"+queryParam.get("vin")+"'";
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sql+=" AND A.SO_NO='"+queryParam.get("soNo")+"'";
        }
        Map map= DAOUtil.findFirst(sql, params);
   
      
        return map;
    }
    
    
    
    /**
    * @author LiGaoqi
    * @date 2017年4月17日
    * @param m
    * @param c
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#searchVehicleSum(java.lang.String, java.lang.String)
    */
    	
    @Override
    public String searchVehicleSum(String m, String c) throws ServiceBizException {

        String msg="0";
        StringBuffer sb = new StringBuffer();
        sb.append("select VEHICLE_PRICE,DEALER_CODE from TT_SALES_ORDER where  DEALER_CODE= ? AND SO_NO= ? ");
        List<Object> queryList = new ArrayList<Object>();

        queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        System.out.println("ccccccccccccccccc");
        System.out.println(c);
        queryList.add(c);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             System.out.println(result.get(0).get("VEHICLE_PRICE").toString());
             msg=result.get(0).get("VEHICLE_PRICE").toString();
        }
        System.out.println(msg);
        return msg;
    
    }
    /**
     * 作废
    * @author DuPengXin
    * @date 2016年10月10日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.InvoiceRegisterService#cancelInvoiceRegister(java.lang.Long)
    */
    	
    @Override
    public void cancelInvoiceRegister(Long id)throws ServiceBizException{
        SoInvoicePO ir = SoInvoicePO.findByCompositeKeys(id,FrameworkUtil.getLoginInfo().getDealerCode());
        ir.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_NOT);
        //String sql="select tso.SO_NO_ID,tso.DEALER_CODE,tso.SO_NO from tt_sales_order tso LEFT JOIN tt_so_invoice tsi on tso.SO_NO_ID=tsi.SO_NO_ID where tso.SO_NO_ID=?";
        SalesOrderPO sopo=SalesOrderPO.findById(ir.get("SO_NO_ID"));
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("作废整车开票登记信息：销售单号【"+sopo.getString("SO_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_RETAIL_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        ir.saveIt();
    }
    public List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException {
     
        String sql = "";
        String invoiceNo=queryParam.get("invoiceNo");
        String vin=queryParam.get("vin");
        String certificateNo=queryParam.get("certificateNo");
        String license=queryParam.get("license");
        String soNo=queryParam.get("soNo");
//        Timestamp invoiceDateBegin=queryParam.get("invoiceDateBegin");
//        Timestamp invoiceDateEnd=queryParam.get("invoiceDateEnd");
        String invoiceChargeType=queryParam.get("invoiceChargeType"); 
        String remainingPara = commonNoService.getDefalutPara("3322");
//      sql = "SELECT A.* ,b.SO_STATUS FROM TT_SO_INVOICE A, TT_SALES_ORDER B ";
//      sql += "WHERE A.ENTITY_CODE = B.ENTITY_CODE AND A.D_KEY = B.D_KEY AND A.SO_NO = B.SO_NO ";
        sql = "SELECT A.* ,b.EC_ORDER_NO,b.SO_STATUS,C.IS_DIRECT,B.BUSINESS_TYPE,D.CUS_SOURCE,(" + remainingPara + " - A.FEE_VEHICLE_RESCAN_TIMES) FEE_VEHICLE_RESCAN_REMAINING FROM TT_SO_INVOICE A INNER JOIN TT_SALES_ORDER B";
        sql += " ON A.dealer_code = B.dealer_code AND A.D_KEY = B.D_KEY AND A.SO_NO = B.SO_NO";
        sql += " LEFT JOIN TM_VS_STOCK C ON A.dealer_code = C.dealer_code AND A.VIN = C.VIN";
        sql += " LEFT JOIN Tm_Potential_Customer D ON B.dealer_code = D.dealer_code AND B.CUSTOMER_NO = D.CUSTOMER_NO";
        sql += " WHERE 1 = 1";
        sql += " AND A.dealer_code = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "'";
        sql += " AND A.D_KEY = " + CommonConstants.D_KEY;
        if (!StringUtils.isNullOrEmpty(queryParam.get("businessType")))
            sql += " AND B.BUSINESS_TYPE = " + queryParam.get("business_Type");
        if (!StringUtils.isNullOrEmpty(queryParam.get("soStatus"))){
            sql+=" and b.SO_STATUS="+queryParam.get("soStatus");
        }
     
        if (!StringUtils.isNullOrEmpty(queryParam.get("invoiceCustomer"))){
            sql += Utility.getLikeCond("A", "INVOICE_CUSTOMER", queryParam.get("invoiceCustomer"), "AND");
        }
          
        if (invoiceNo != null && !invoiceNo.trim().equals(""))
            sql += Utility.getLikeCond("A", "INVOICE_NO", invoiceNo, "AND");
        if (vin != null && !vin.trim().equals(""))
            sql += Utility.getLikeCond("A", "VIN", vin, "AND");
        if (certificateNo != null && !certificateNo.trim().equals(""))
            sql += Utility.getLikeCond("A", "CERTIFICATE_NO", certificateNo, "AND");
        if (license != null && !license.trim().equals(""))
            sql += Utility.getLikeCond("B", "LICENSE", license, "AND");
        if (soNo != null && !soNo.trim().equals(""))
            sql += Utility.getLikeCond("B", "SO_NO", soNo, "AND");
        /*if (invoiceDateBegin != null && invoiceDateEnd != null)
            sql += " AND A.INVOICE_DATE BETWEEN ? AND ? ";*/
        sql+=Utility.getDateCond("A", "INVOICE_DATE", queryParam.get("invoiceDateBegin"), (queryParam.get("deEndS")));
        if (invoiceChargeType != null && !invoiceChargeType.trim().equals(""))
            sql += " AND A.INVOICE_CHARGE_TYPE = " + invoiceChargeType;
        List<Object> queryList = new ArrayList<Object>();
        List<Map> resultList = DAOUtil.findAll(sql.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("大客户导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }
}

