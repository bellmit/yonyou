/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.DMSTODCS004Cloud;
import com.yonyou.dms.DTO.gacfca.DMSTODCS004Dto;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;

/**
 * @author Administrator
 *业务描述：CALL车上报
 *@date 2017-04-20
 */
@Service
public class DMSTODCS004Impl implements DMSTODCS004 {

	final Logger logger = Logger.getLogger(DMSTODCS004Impl.class);

    @Autowired
    DMSTODCS004Cloud DMSTODCS004Cloud;
	/* (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.DMSTODCS004#getDSO0401(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int getDMSTODCS004(String soNo, String deliveryModeElec) throws ParseException, Exception {
	    String msg="1";
	    try {
	    	System.err.println(soNo);
	    	System.err.println(deliveryModeElec);
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            LinkedList<DMSTODCS004Dto> resultList = new LinkedList<DMSTODCS004Dto>();
            //不是CALL车类型不用上报
            if(Utility.testString(deliveryModeElec) && DictCodeConstants.DICT_DELIVERY_MODE_ELEC_ELEC_CALL.equals(deliveryModeElec)){
                logger.debug(deliveryModeElec);
                if (Utility.testString(soNo)) {
                    SalesOrderPO po = SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
                              if(po.getInteger("EC_ORDER")==Utility.getInt(DictCodeConstants.DICT_IS_YES)&&po.getInteger("BUSINESS_TYPE")==Utility.getInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                                List<Map> submislist=querySalesOrder(soNo);
                                if(submislist!=null&&submislist.size()>0){
                                    Map bean=submislist.get(0);
                                    DMSTODCS004Dto vo=new DMSTODCS004Dto();         
                                        vo.setBrandCode((String)bean.get("BRAND_CODE"));
                                        vo.setColorCode((String)bean.get("COLOR_CODE"));
                                        vo.setEcOrderNo((String)bean.get("EC_ORDER_NO"));
                                        vo.setDealerCode(dealerCode);
                                        vo.setGroupCode((String)bean.get("CONFIG_CODE"));
                                        vo.setModelCode((String)bean.get("MODEL_CODE"));
                                        vo.setModelYear((String)bean.get("YEAR_MODEL"));
                                        vo.setSeriesCode((String)bean.get("SERIES_CODE"));
                                        vo.setTrimCode((String)bean.get("INTENT_TRIM"));
                                        vo.setCallDate(new Date());
                                        resultList.add(vo); 
                                        msg = DMSTODCS004Cloud.handleExecutor(resultList);
                                    }
                                    
                            }
                        
                }
            }
            return Integer.parseInt(msg);
                
    
    
        } catch (Exception e) {
            return 0;
        }
	}

	/* (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.DMSTODCS004#querySalesOrder(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesOrder(String soNo) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuilder sql = new StringBuilder("");
		sql.append(" SELECT    a.DELIVERY_MODE_ELEC,b.YEAR_MODEL,A.CUSTOMER_NAME,A.Aborted_Date, A.CONFIRMED_DATE,a.EC_ORDER_NO,B.BRAND_CODE,B.SERIES_CODE, B.MODEL_CODE,    B.CONFIG_CODE, COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE, A.SO_STATUS,A.CERTIFICATE_NO, A.CT_CODE, d.RETAIL_FINANCE,  d.DEPOSIT_AMOUNT , d.DETERMINED_TIME,  d.INTENT_TRIM,d.CONTACTOR_MOBILE FROM TT_SALES_ORDER A ");
		sql.append( " LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY LEFT JOIN TM_VS_STOCK C ON  A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN INNER JOIN TT_ES_CUSTOMER_ORDER d ON a.DEALER_CODE=d.DEALER_CODE AND a.EC_ORDER_NO=d.EC_ORDER_NO and a.customer_no=d.customer_no WHERE  1=1 and  A.D_KEY = 0 and a.DEALER_CODE= '"+dealerCode+"' and a.so_no= '"+soNo+"'and a.EC_ORDER=12781001");
		List<Map> rsList = Base.findAll(sql.toString());
		System.err.println(sql.toString());
		return rsList;
	}

}
