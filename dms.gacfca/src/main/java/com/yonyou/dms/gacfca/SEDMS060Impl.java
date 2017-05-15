/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.bsuv.dms.DMSTODCS003ServiceImpl;
import com.infoeai.eai.wsServer.bsuv.lms.DMSTODCS003VO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;

/**业务描述：上报电商订单取消，交车，提交
 * @author Administrator
 *@date 2017-04-20
 */
@Service
public class SEDMS060Impl implements SEDMS060 {
    private static final Logger logger = LoggerFactory.getLogger(SEDMS060Impl.class);
    @Autowired
    DMSTODCS003ServiceImpl DMSTODCS003ServiceImpl;
	/* (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.SEDMS060#getDSO0401(java.lang.String, java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public int getSEDMS060(String soNo, String isNotAudit, String flag)
			throws ParseException, Exception {
	    try {
	        String msg="1";
	        boolean isAudit=true;
	        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        LinkedList<DMSTODCS003VO> resultList = new LinkedList<DMSTODCS003VO>();
	        //订单取消的时候，未提交的订单不用上报
	        if(Utility.testString(isNotAudit)&&DictCodeConstants.DICT_IS_YES.equals(isNotAudit)){
	            isAudit=false;
	        }
	        if(isAudit){
	            SalesOrderPO po = SalesOrderPO.findByCompositeKeys(dealerCode,soNo);
                if(po.getInteger("EC_ORDER")==Utility.getInt(DictCodeConstants.DICT_IS_YES)&&po.getInteger("BUSINESS_TYPE")==Utility.getInt(DictCodeConstants.DICT_SO_TYPE_GENERAL)){
                            List<Map> submislist=querySalesOrder(soNo);
                            if(submislist!=null&&submislist.size()>0){
                                
                                Map bean=submislist.get(0);
                                DMSTODCS003VO vo=new DMSTODCS003VO();
                                int soStatus=(Integer)bean.get("SO_STATUS");
                                if(soStatus==13011015||soStatus==13011020||soStatus==13011025||soStatus==13011030||soStatus==13011055){
                                    if(soStatus==13011015||soStatus==13011020||soStatus==13011025){
                                        if(Utility.testString(flag)&&"1".equals(flag)){
                                            vo.setBrandCode((String)bean.get("BRAND_CODE"));
                                            vo.setColorCode((String)bean.get("COLOR_CODE"));
                                            vo.setCustomerName((String)bean.get("CUSTOMER_NAME"));
                                    //      vo.setDeliverDate(bean.getDate("DETERMINED_TIME"));
                                            vo.setDepositAmount((Float)bean.get("DEPOSIT_AMOUNT"));
                                            vo.setDepositDate((Date)bean.get("DETERMINED_TIME"));
                                            vo.setEcOrderNo((String)bean.get("EC_ORDER_NO"));
                                            vo.setDealerCode(dealerCode);;
                                            vo.setEscComfirmType((Integer)bean.get("DELIVERY_MODE_ELEC"));
                                            vo.setGroupCode((String)bean.get("CONFIG_CODE"));
                                            vo.setIdCrad((String)bean.get("CERTIFICATE_NO"));
                                            vo.setModelCode((String)bean.get("MODEL_CODE"));
                                            vo.setModelYear((String)bean.get("YEAR_MODEL"));
                                            
                                            vo.setOrderStatus(soStatus);
                                            vo.setRetailFinance((String)bean.get("RETAIL_FINANCE"));
                                            vo.setTel((String)bean.get("CONTACTOR_MOBILE"));
                                            vo.setTrimCode((String)bean.get("INTENT_TRIM"));
                                            if(soStatus==13011055){
                                                vo.setRevokeDate((Date)bean.get("ABORTED_DATE"));
                                            }else if(soStatus==13011015||soStatus==13011020||soStatus==13011025){
                                                vo.setSubmitDate(Utility.getCurrentDateTime());
                                            }else if(soStatus==13011030){
                                                vo.setDeliverDate((Date)bean.get("CONFIRMED_DATE"));
                                            }
                                            vo.setSeriesCode((String)bean.get("SERIES_CODE"));
                                            resultList.add(vo);
                                            logger.info("=======================开始上报SEDMS060Impl");
                                            msg=DMSTODCS003ServiceImpl.handleExecutor(resultList);
                                        }
                                    }else{
                                        vo.setBrandCode((String)bean.get("BRAND_CODE"));
                                        vo.setColorCode((String)bean.get("COLOR_CODE"));
                                        vo.setCustomerName((String)bean.get("CUSTOMER_NAME"));
                                //      vo.setDeliverDate(bean.getDate("DETERMINED_TIME"));
                                        vo.setDepositAmount((Float)bean.get("DEPOSIT_AMOUNT"));
                                        vo.setDepositDate((Date)bean.get("DETERMINED_TIME"));
                                        vo.setEcOrderNo((String)bean.get("EC_ORDER_NO"));
                                        vo.setDealerCode(dealerCode);;
                                        vo.setEscComfirmType((Integer)bean.get("DELIVERY_MODE_ELEC"));
                                        vo.setGroupCode((String)bean.get("CONFIG_CODE"));
                                        vo.setIdCrad((String)bean.get("CERTIFICATE_NO"));
                                        vo.setModelCode((String)bean.get("MODEL_CODE"));
                                        vo.setModelYear((String)bean.get("YEAR_MODEL"));
                                        
                                        vo.setOrderStatus(soStatus);
                                        vo.setRetailFinance((String)bean.get("RETAIL_FINANCE"));
                                        vo.setTel((String)bean.get("CONTACTOR_MOBILE"));
                                        vo.setTrimCode((String)bean.get("INTENT_TRIM"));
                                        if(soStatus==13011055){
                                            vo.setRevokeDate((Date)bean.get("ABORTED_DATE"));
                                        }else if(soStatus==13011015||soStatus==13011020||soStatus==13011025){
                                            vo.setSubmitDate(Utility.getCurrentDateTime());
                                        }else if(soStatus==13011030){
                                            vo.setDeliverDate((Date)bean.get("CONFIRMED_DATE"));
                                        }
                                        vo.setSeriesCode((String)bean.get("SERIES_CODE"));
                                        resultList.add(vo);
                                        logger.info("=======================开始上报SEDMS060Impl");
                                        msg=DMSTODCS003ServiceImpl.handleExecutor(resultList);
                                    }
                                    
                                }
                                
                        }
                  
                }
	        }
	        logger.info("=======================结束上报SEDMS060Impl");
	        return Integer.parseInt(msg);
	    
        } catch (Exception e) {
            logger.info("=======================上报SEDMS060Impl失败");
            return 0;
        }
	}

	/* (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.SEDMS060#querySalesOrder(java.lang.String)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesOrder(String soNo) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT   d.CUSTOMER_NO,a.DELIVERY_MODE_ELEC,b.YEAR_MODEL,A.CUSTOMER_NAME,A.Aborted_Date, A.CONFIRMED_DATE,a.EC_ORDER_NO,B.BRAND_CODE,B.SERIES_CODE, B.MODEL_CODE,    B.CONFIG_CODE, COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE, A.SO_STATUS,A.CERTIFICATE_NO, A.CT_CODE, d.RETAIL_FINANCE,  d.DEPOSIT_AMOUNT , d.DETERMINED_TIME,  d.INTENT_TRIM,d.CONTACTOR_MOBILE FROM TT_SALES_ORDER A ");
		sql.append( " LEFT JOIN VM_VS_PRODUCT B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.ENTITY_CODE = B.ENTITY_CODE AND A.D_KEY = B.D_KEY LEFT JOIN TM_VS_STOCK C ON  A.ENTITY_CODE = C.ENTITY_CODE AND A.VIN = C.VIN INNER JOIN TT_ES_CUSTOMER_ORDER d ON a.entity_code=d.entity_code AND a.EC_ORDER_NO=d.EC_ORDER_NO and a.customer_no=d.customer_no WHERE  1=1 and  A.D_KEY = 0 and a.entity_code= '"+dealerCode+"' and a.so_no= '"+soNo+"'and a.EC_ORDER=12781001 with ur ");	
		List<Map> rsList = Base.findAll(sql.toString());
		return rsList;
	}

}
