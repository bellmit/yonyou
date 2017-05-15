package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS004Cloud;
import com.yonyou.dms.DTO.gacfca.SameToDccDto;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务描述：实销信息上报
 * 把上端传来的客户专门报一个作为以前下发销售线索的回馈
 * @author wangliang
 * @date 2017年4月19日
 *
 */
@Service
public class SADMS008addImpl implements SADMS008add {
    private static final Logger logger = LoggerFactory.getLogger(SADMS008add.class);
    @Autowired
    SADCS004Cloud SADCS004Cloud;
	
	@SuppressWarnings("rawtypes")
	@Override
	public int getSADMS008add(String customerNo,String vin,Date invoiceDate,String isDms) throws ServiceBizException {
	    String msg="1";
	    String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		try{
		   
			if(("Y".equals(isDms))){
			    logger.info("=======================销售信息撞单上报SADMS008add开始=====================1");
	             
	             if(!StringUtils.isNullOrEmpty(customerNo)) {
	                 logger.info("=======================销售信息撞单上报SADMS008add开始=====================2");
	                 
	                    if(null != vin){                      
	                            List<SameToDccDto> resultList = new LinkedList<SameToDccDto>();
	                            List<Map> tmvpolist = Base.findAll("SELECT * FROM TM_VEHICLE WHERE DEALER_CODE = ? AND VIN = ? AND CUSTOMER_NO = ?",  new Object[]{dealerCode,vin,customerNo});
	                            
	                            if (tmvpolist!=null && tmvpolist.size()>0){
	                                logger.info("=======================销售信息撞单上报SADMS008add开始=====================3");
	                                //List<VsStockPO> list = VsStockPO.findBySQL("SELECT * FROM tm_vehicle WHERE Dealer_code = '"+dealerCode+"' AND VIN = '"+vin+"'", null); //库存中的车辆信息 
	                                VsStockPO tspo =VsStockPO.findByCompositeKeys(dealerCode,vin);
	                                if(tspo!=null){
	                                  
	                                    //检查是不是一般销售订单的车  或者直销车 
	                                    if(!StringUtils.isNullOrEmpty(tspo.getString("SO_NO"))){   //该车的销售订单记录
	                                        logger.info("=======================销售信息撞单上报SADMS008add开始=====================4");
	                                        SalesOrderPO salespo = SalesOrderPO.findByCompositeKeys(dealerCode,tspo.getString("SO_NO")); //tt_sales_order 销售订单信息
	                                        if (salespo!=null && salespo.getString("CUSTOMER_NO")!=null){
	                                            PotentialCusPO pcpo = PotentialCusPO.findByCompositeKeys(dealerCode,salespo.getString("CUSTOMER_NO"));
	                               
	                                            if (pcpo!=null && ((Utility.testString(pcpo.getString("OEM_CUSTOMER_NO")) && 
	    												(pcpo.getInteger("IS_SAME_DCC")==null || pcpo.getInteger("IS_SAME_DCC")==12781002)) ||
	    													(Utility.testString(pcpo.getInteger("IS_SPAD_CREATE")) && pcpo.getInteger("IS_SPAD_CREATE")==12781001))){
	    	                                        SameToDccDto vo= new SameToDccDto();
	                                                vo.setCustomerNo(pcpo.getString("CUSTOMER_NO"));
	                                                vo.setDealerCode(dealerCode);
	                                                vo.setNid(Utility.getLong(pcpo.getString("OEM_CUSTOMER_NO")));
	                                                vo.setStatus("1");
	                                                vo.setOpportunityLevelID(13101009); 
	                                                if (pcpo.getString("SOLD_BY")!=null && pcpo.getString("SOLD_BY")!="0"){
	                                                    UserPO userpo= UserPO.findByCompositeKeys(pcpo.getString("SOLD_BY"));
	                                                    if (userpo!=null){
	                                                        vo.setSalesConsultant(userpo.getString("USER_NAME"));   
	                                                    }
	                                                }
	                                                if (salespo.getString("PRODUCT_CODE") !=null ){
	                                                    VsProductPO pr = VsProductPO.findByCompositeKeys(dealerCode,salespo.getString("PRODUCT_CODE"));
	                                                    if ( pr!=null){
	                                                        vo.setBrandCode(pr.getString("BRAND_CODE"));
	                                                        vo.setSeriasCode(pr.getString("SERIES_CODE"));
	                                                        vo.setModelCode(pr.getString("MODEL_CODE"));
	                                                        vo.setConfigerCode(pr.getString("CONFIG_CODE"));    
	                                                    }
	                                                }
	                                                vo.setPurchaseTime(invoiceDate);
	                                                resultList.add(vo);
	                                            }
	                                        }
	                 
	                                    }
	                                }
	                            }
	                   
	                  if(resultList!=null&&resultList.size()>0) {
	                      logger.info("=======================销售信息撞单上报SADMS008add开始=====================");
	                      msg=SADCS004Cloud.receiveDate(resultList);
	                      logger.info("=======================销售信息撞单上报SADMS008add结束=====================");
	                  }
	                }
	             }
	       
			}
			
			 return Integer.parseInt(msg);	
		} catch(Exception e) {
		    logger.info("=======================销售信息撞单上报SADMS008add失败=====================");
			return 0;
		}

	}

}
