package com.yonyou.dms.vehicle.service.saleOdditionalOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtSoPartPo;
import com.yonyou.dms.common.domains.PO.basedata.TtSoServicePO;
import com.yonyou.dms.common.domains.PO.basedata.TtSoUpholsterPO;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder.SalesOdditionalOrderDTO;

/**
 * 服务订单实现类
 * @author Benzc
 * @date 2017年3月8日
 */
@Service
public class SaleOdditionalOrderServiceImpl implements SaleOdditionalOrderService{
    
	 @Autowired
	 private CommonNoService commonNoService;
	
	/**
	 * 分页查询
	 */
	@Override
	public PageInfoDto QuerySaleOdditionalOrder(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DISTINCT A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
		sb.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ,A.VIN,A.SOLD_BY,A.VEHICLE_PRICE, ");
		sb.append(" A.SO_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CT_CODE,A.CERTIFICATE_NO,A.DELIVERING_DATE,A.BUSINESS_TYPE, ");
		sb.append(" mo.MODEL_NAME,se.SERIES_NAME,br.BRAND_NAME,pa.CONFIG_NAME,co.COLOR_NAME,em.USER_NAME");
		sb.append(" FROM TT_SALES_ORDER A  ");
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY ");
		//sb.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
		sb.append(" LEFT JOIN TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE");
		sb.append(" LEFT JOIN TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=se.DEALER_CODE");
		sb.append(" LEFT JOIN tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
		sb.append(" LEFT JOIN tm_configuration pa on   B.CONFIG_CODE=pa.CONFIG_CODE and B.DEALER_CODE=pa.DEALER_CODE");
		sb.append(" LEFT JOIN tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
		sb.append(" LEFT JOIN TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");
		sb.append(" WHERE  A.D_KEY ="+CommonConstants.D_KEY);
		if(!commonNoService.getDefalutPara("2077").equals(DictCodeConstants.DICT_IS_YES)){
			sb.append(" AND A.SO_STATUS <> "+DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD);
        }
		sb.append(" and a.so_no not in  (select OLD_SO_NO from tt_sales_order where  dealer_code=A.DEALER_CODE and OLD_SO_NO is not null and OLD_SO_NO<>'' and BUSINESS_TYPE=13001005) ");		
		sb.append(" and A.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_SERVICE);
		sb.append(" and (A.SO_STATUS= 13011025 or A.SO_STATUS= 13011035 or A.SO_STATUS=13011015 or A.SO_STATUS=13011010 or A.SO_STATUS=13021020)" );
		List<Object> queryList = new ArrayList<Object>();
		this.setWhere(sb, queryParam, queryList);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	//查询条件
	public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sb.append(" and A.SO_NO like ?");
            queryList.add("%" + queryParam.get("soNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and A.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("ContractNo"))) {
            sb.append(" and A.CONTRACT_NO like ?");
            queryList.add("%" + queryParam.get("ContractNo") + "%");
        }	
	}
    
	/**
	 * 根据客户编号查询客户信息
	 * @author Benzc
	 * @date 2017年3月17日
	 */
	@Override
	public PageInfoDto QueryCustomer(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String tag = queryParam.get("tag");
		System.err.println(tag);
		if(tag.equals("1")){  //潜在客户			
			sb.append(" SELECT M.MEDIA_DETAIL_NAME,A.*,13011010 AS SO_STATUS");
			sb.append(" ,c.INTENT_BRAND,c.INTENT_SERIES,c.INTENT_MODEL,c.INTENT_CONFIG,c.INTENT_COLOR,d.DEPOSIT_AMOUNT,c.IS_MAIN_MODEL, 'CUSTOMER' AS TAG  ");
			sb.append(" ,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,cn.CONFIG_NAME,co.COLOR_NAME,'13861001' AS ORDER_SORT,'10251001' AS PAY_MODE,'13031001' AS INVOICE_MODE,'11931002' AS VEHICLE_PURPOSE");
			sb.append(" from TM_POTENTIAL_CUSTOMER A ");
			sb.append(" left join  TT_CUSTOMER_INTENT_DETAIL c on c.DEALER_CODE = A.DEALER_CODE and c.INTENT_ID=A.INTENT_ID  ");
			sb.append(" left join  TT_ES_CUSTOMER_ORDER d on d.EC_ORDER_NO = A.EC_ORDER_NO  and a.DEALER_CODE=d.DEALER_CODE and a.customer_no=d.customer_no");
			sb.append(" LEFT JOIN TM_BRAND br on c.INTENT_BRAND=br.BRAND_CODE AND c.DEALER_CODE=br.DEALER_CODE");
			sb.append(" LEFT JOIN TM_MODEL   mo   on   c.INTENT_MODEL=mo.MODEL_CODE and c.DEALER_CODE=mo.DEALER_CODE");
	        sb.append(" LEFT JOIN TM_SERIES  se   on   c.INTENT_SERIES=se.SERIES_CODE and c.DEALER_CODE=se.DEALER_CODE");
	        sb.append(" LEFT JOIN TM_CONFIGURATION  cn   on   c.INTENT_CONFIG=cn.CONFIG_CODE and c.DEALER_CODE=cn.DEALER_CODE");
	        sb.append(" LEFT JOIN tm_color   co   on   c.INTENT_COLOR = co.COLOR_CODE and c.DEALER_CODE=co.DEALER_CODE");
	        sb.append(" LEFT JOIN tt_media_detail M ON M.MEDIA_DETAIL=A.MEDIA_DETAIL AND M.DEALER_CODE=A.DEALER_CODE");
			sb.append(" WHERE A.DEALER_CODE=" + dealerCode);
			sb.append(" and A.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_F+" and  A.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_FO);
			sb.append(" AND A.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_D);
			sb.append(" AND a.D_KEY =  " + CommonConstants.D_KEY);
			this.setCus(sb, queryParam, queryList);
			PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
			System.err.println(sb.toString());
			return result;
		}else{                      //售后客户
			sb.append(" SELECT 13011010 AS SO_STATUS,A.DEALER_CODE,A.OWNER_NO AS CUSTOMER_NO,A.OWNER_NAME AS CUSTOMER_NAME,A.OWNER_PROPERTY AS CUSTOMER_TYPE, A.PHONE AS");
			sb.append(" CONTACTOR_PHONE,MOBILE AS CONTACTOR_MOBILE,A.ADDRESS,A.CT_CODE,A.CERTIFICATE_NO,'OWNER' AS TAG,b.VIN,b.LICENSE,'13861001' AS ORDER_SORT,'10251001' AS PAY_MODE,'13031001' AS INVOICE_MODE,'11931002' AS VEHICLE_PURPOSE,");
			sb.append(" B.BRAND AS BRAND_CODE,B.SERIES AS SERIES_CODE,B.MODEL AS MODEL_CODE,B.COLOR AS COLOR_CODE,B.PRODUCT_CODE,C.PRODUCT_NAME");
			sb.append(" from TM_OWNER A");
			sb.append(" INNER JOIN TM_VEHICLE B ON a.OWNER_NO = b.OWNER_NO AND a.DEALER_CODE = b.DEALER_CODE");
			sb.append(" INNER JOIN TM_VS_PRODUCT C ON B.PRODUCT_CODE = C.PRODUCT_CODE AND B.DEALER_CODE = C.DEALER_CODE");
			sb.append(" WHERE a.DEALER_CODE = " + dealerCode);
			this.setCus2(sb, queryParam, queryList);
			PageInfoDto list = DAOUtil.pageQuery(sb.toString(), queryList);
			System.err.println(sb.toString());
			return list;
		}
	}
	
	//根据客户编号查询的查询信息
	public void setCus(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and A.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
            sb.append(" and A.CUSTOMER_TYPE like ?");
            queryList.add(queryParam.get("customerType"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and A.CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and A.INTENT_LEVEL = ?");
            queryList.add(queryParam.get("intentLevel"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and A.CONTACTOR_MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorMobile") + "%");
        }
		if(queryParam.get("tag").equals("1")){
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))) {
	            sb.append(" and C.IS_MAIN_MODEL = ?");
	            queryList.add(queryParam.get("isMainModel"));
	        }
		}else{
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sb.append(" and B.VIN like ?");
	            queryList.add("%" + queryParam.get("vin") + "%");
	        }
			if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
	            sb.append(" and B.LICENSE like ?");
	            queryList.add("%" + queryParam.get("license") + "%");
	        }
		}
				
	}
	
	public void setCus2(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and A.OWNER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
            sb.append(" and A.OWNER_PROPERTY = ?");
            queryList.add(queryParam.get("customerType"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and A.PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and A.INTENT_LEVEL = ?");
            queryList.add(queryParam.get("intentLevel"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and A.MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorMobile") + "%");
        }
		if(queryParam.get("tag").equals("1")){
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMainModel"))) {
	            sb.append(" and A.IS_MAIN_MODEL = ?");
	            queryList.add(queryParam.get("isMainModel"));
	        }
		}else{
			if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sb.append(" and B.VIN like ?");
	            queryList.add("%" + queryParam.get("vin") + "%");
	        }
			if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
	            sb.append(" and B.LICENSE like ?");
	            queryList.add("%" + queryParam.get("license") + "%");
	        }
		}
				
	}
    
	/**
	 * 根据销售单号查询订单信息
	 * @author Benzc
	 * @date 2017年3月17日
	 */
	@Override
	public PageInfoDto QuerySalesOrder(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
		sb.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ,A.VIN,A.SOLD_BY,A.VEHICLE_PRICE,");
		sb.append(" A.SO_NO AS SALES_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CT_CODE,A.CERTIFICATE_NO,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE");
		sb.append(" ,br.BRAND_NAME,mo.MODEL_NAME,se.SERIES_NAME,cn.CONFIG_NAME,co.COLOR_NAME,u.USER_NAME,B.PRODUCT_CODE,B.PRODUCT_NAME,11931002 AS VEHICLE_PURPOSE");
		sb.append(" FROM TT_SALES_ORDER A");
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
		sb.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
		sb.append(" LEFT JOIN TM_BRAND br on B.BRAND_CODE=br.BRAND_CODE AND B.DEALER_CODE=br.DEALER_CODE");
		sb.append(" LEFT JOIN TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE");
        sb.append(" LEFT JOIN TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=se.DEALER_CODE");
        sb.append(" LEFT JOIN TM_CONFIGURATION  cn   on   B.CONFIG_CODE=cn.CONFIG_CODE and B.DEALER_CODE=cn.DEALER_CODE");
        sb.append(" LEFT JOIN tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
        sb.append(" LEFT JOIN TM_USER u on A.OWNED_BY = u.USER_ID and A.DEALER_CODE=u.DEALER_CODE");
		sb.append(" WHERE  A.D_KEY = "+CommonConstants.D_KEY);
		//销售开票登记，选择为：完成或关单状态的销售订单 新增需求 CHRYSLER-245  DICT_SO_STATUS_HAVE_OUT_STOCK
		//sb.append(" and (A.SO_STATUS= "+DictCodeConstants.DICT_SO_STATUS_CLOSED+" or A.SO_STATUS= "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+")" );
		List<Object> queryList = new ArrayList<Object>();
		this.setSalesOrder(sb, queryParam, queryList);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	//根据销售单号查询订单信息（查询条件）
	public void setSalesOrder(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
            sb.append(" and A.SO_NO like ?");
            queryList.add("%" + queryParam.get("soNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and A.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("contractNo"))) {
            sb.append(" and A.CONTRACT_NO like ?");
            queryList.add("%" + queryParam.get("contractNo") + "%");
        }
	}
    
	/**
	 * 工时单价下拉框
	 * @author Benzc
	 * @date 2017年3月22日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> selectPrice(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sb = new StringBuilder("SELECT DEALER_CODE,LABOUR_PRICE_CODE,LABOUR_PRICE from tm_labour_price where 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> list = DAOUtil.findAll(sb.toString(),params);
		return list;
	}
    
	/**
	 * 服务项目编辑页面查询
	 * @author Benzc
	 * @date 2017年3月23日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryServiceProject(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("select DIRECTIVE_PRICE AS REAL_PRICE,DIRECTIVE_PRICE AS RECEIVEABLE_AMOUNT,DIRECTIVE_PRICE AS CONSIGNED_AMOUNT,'14061001' AS ACCOUNT_MODE,");
		sb.append(" SERVICE_ID,DEALER_CODE,SERVICE_CODE,SERVICE_NAME,SERVICE_TYPE,DIRECTIVE_PRICE,COST_PRICE,REMARK from TM_SALES_SERVICES where 1=1 ");
		sb.append(" and SERVICE_CODE=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
		System.err.println(sb.toString());
        return list;
	}
    
	/**
	 * 服务订单新增
	 * @author Benzc
	 * @date 2017年3月27日
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	@Override
	public void addSalesOdditionalOrder(SalesOdditionalOrderDTO salesOrderDTO, String soNo) throws ServiceBizException {
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		SalesOrderPO updatePo = SalesOrderPO.findByCompositeKeys(DealerCode,soNo);
		if(updatePo != null){
			if(!StringUtils.isNullOrEmpty(updatePo.getString("VIN"))){
				String uodateVin="";
                uodateVin = updatePo.getString("VIN");
            }
		}
	    SalesOrderPO salesOrderPO = new SalesOrderPO();
	    this.setSalesPO(salesOrderPO,salesOrderDTO, soNo,true);
	    //salesOrderPO.saveIt();
	    //装潢项目保存
	    if (salesOrderDTO.getSoDecrodateList().size() > 0 && salesOrderDTO.getSoDecrodateList()!= null) {
            List<Map> list  =  salesOrderDTO.getSoDecrodateList();
            System.err.println(soNo);
            for(int i = 0;i<list.size();i++){
            	getSoUpholster(list.get(i),soNo);
            }
	    	
        }
	    //精品材料保存
	    if (salesOrderDTO.getSoDecrodatePartList().size() > 0 && salesOrderDTO.getSoDecrodatePartList()!= null) {
        /*    for (SoPartDTO paDto : salesOrderDTO.getSoDecrodatePartList()) {
            	getSoPart(paDto,soNo);
            }*/
	    	List<Map> list  =  salesOrderDTO.getSoDecrodatePartList();
            for(int i = 0;i<list.size();i++){
            	getSoPart(list.get(i),soNo);
            }
        }
	    //服务项目保存
	    if (salesOrderDTO.getSoServicesList().size() > 0 && salesOrderDTO.getSoServicesList() != null) {
	    	List<Map> list  =  salesOrderDTO.getSoServicesList();
            for(int i = 0;i<list.size();i++){
            	getSoService(list.get(i),soNo);
            }
        }
		
	}
	
	/**
	 * 服务订单修改
	 * @author Benzc
	 * @date 2017年4月6日
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void modifyOdditionalOrderInfo(String soNo, SalesOdditionalOrderDTO salesOrderDTO) throws ServiceBizException {
		String DealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	/*	SalesOrderPO updatePo = SalesOrderPO.findByCompositeKeys(DealerCode,soNo);
		if(updatePo != null){
			if(!StringUtils.isNullOrEmpty(updatePo.getString("VIN"))){
				String uodateVin="";
                uodateVin = updatePo.getString("VIN");
            }
		}*/
	    SalesOrderPO salesOrderPO = SalesOrderPO.findByCompositeKeys(DealerCode,soNo);
	    this.setSalesPO(salesOrderPO,salesOrderDTO, soNo,false);
	    //salesOrderPO.saveIt();
	    
	    //装潢项目保存
	    if (salesOrderDTO.getSoDecrodateList().size() > 0 && salesOrderDTO.getSoDecrodateList()!= null) {
            List<Map> list  =  salesOrderDTO.getSoDecrodateList();
            for(int i = 0;i<list.size();i++){
            	setSoUpholster(list.get(i),soNo,salesOrderDTO);
            }
        }
	    //精品材料保存
	    if (salesOrderDTO.getSoDecrodatePartList().size() > 0 && salesOrderDTO.getSoDecrodatePartList()!= null) {
	    	List<Map> list  =  salesOrderDTO.getSoDecrodatePartList();
            for(int i = 0;i<list.size();i++){
            	setSoPart(list.get(i),soNo,salesOrderDTO);
            }
        }
	    //服务项目保存
	    if (salesOrderDTO.getSoServicesList().size() > 0 && salesOrderDTO.getSoServicesList() != null) {
	    	List<Map> list  =  salesOrderDTO.getSoServicesList();
            for(int i = 0;i<list.size();i++){
            	setSoService(list.get(i),soNo,salesOrderDTO);
            }
        }
		
	}
    
	//新增字段
	private void setSalesPO(SalesOrderPO po,SalesOdditionalOrderDTO salesOrderDTO, String soNo,boolean flag) {
		//订单信息
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())&&salesOrderDTO.getSoNo().equals(soNo)){
            po = SalesOrderPO.findByCompositeKeys(dealerCode,salesOrderDTO.getSoNo());
        }
        po.setString("BUSINESS_TYPE", DictCodeConstants.DICT_SO_TYPE_SERVICE);
        po.setString("SO_NO", soNo);
        po.setString("SALES_NO", salesOrderDTO.getSalesNo());
        po.setString("SERVICE_NO", salesOrderDTO.getServiceNo());
        po.setInteger("SO_STATUS", salesOrderDTO.getSoStatus());
        po.setString("CUSTOMER_NAME", salesOrderDTO.getCustomerName());
        if(flag){
        	po.setTimestamp("SHEET_CREATE_DATE", new Date());
        }
        po.setString("CUSTOMER_NO", salesOrderDTO.getCustomerNo());
        po.setInteger("CUSTOMER_TYPE", salesOrderDTO.getCustomerType());
        po.setInteger("SHEET_CREATED_BY",salesOrderDTO.getSheetCreatedBy());
        po.setString("PHONE", salesOrderDTO.getPhone());
        po.setString("CONTACTOR_NAME", salesOrderDTO.getContactorName());
        po.setString("ADDRESS", salesOrderDTO.getAddress());
        po.setInteger("CT_CODE", salesOrderDTO.getCtCode());
        po.setString("CONTRACT_NO", salesOrderDTO.getContractNo());
        po.setDate("CONTRACT_DATE", salesOrderDTO.getContractDate());
        po.setDate("CONTRACT_MATURITY", salesOrderDTO.getContractMaturity());
        po.setString("CERTIFICATE_NO", salesOrderDTO.getCertificateNo());
        po.setString("LOAN_ORG", salesOrderDTO.getLoanOrg());
        po.setString("SOLD_BY",salesOrderDTO.getSoldBy());//装潢专员
        po.setDouble("PRE_INVOICE_AMOUNT", salesOrderDTO.getPreInvoiceAmount());
        po.setInteger("PAY_MODE", salesOrderDTO.getPayMode());
        po.setInteger("INVOICE_MODE", salesOrderDTO.getInvoiceMode());
        po.setInteger("ORDER_SORT", salesOrderDTO.getOrderSort());
        po.setString("REMARK", salesOrderDTO.getRemark());
        //车辆信息
        po.setString("PRODUCT_CODE", salesOrderDTO.getProductCode());
        if(salesOrderDTO.getVin() == null && salesOrderDTO.getLicense() == null){
        	throw new ServiceBizException("VIN、车牌号不能同时为空！");
        }
        if(salesOrderDTO.getVin().length() != 17){
        	throw new ServiceBizException("VIN长度为17位!");
        }
        po.setString("VIN", salesOrderDTO.getVin());
        po.setString("LICENSE", salesOrderDTO.getLicense());
        po.setString("COLOR_CODE", salesOrderDTO.getColor());
        po.setInteger("VEHICLE_PURPOSE", salesOrderDTO.getVehiclePurpose());
        po.setString("VEHICLE_DESC", salesOrderDTO.getVehicleDesc());
        po.setInteger("DISCOUNT_MODE_CODE", salesOrderDTO.getDisCountModeCode());
        //财务信息
        po.setDouble("UPHOLSTER_SUM", salesOrderDTO.getUpholsterSum());
        po.setDouble("PRESENT_SUM", salesOrderDTO.getPresentSum());
        po.setDouble("OTHER_SERVICE_SUM", salesOrderDTO.getOtherServiceSum());
        po.setDouble("COMMISSION_BALANCE", salesOrderDTO.getCommissionBalance());
        po.setDouble("INSURANCE_SUM", salesOrderDTO.getInsuranceSum());
        po.setDouble("PLATE_SUM", salesOrderDTO.getPlateSum());
        po.setDouble("LOAN_SUM", salesOrderDTO.getLoadSum());
        po.setDouble("TAX_SUM", salesOrderDTO.getTaxSum());
        po.setDouble("CONSIGNED_SUM", salesOrderDTO.getConsignedSum());
        po.setDouble("CON_RECEIVABLE_SUM", salesOrderDTO.getConReceivableSum());
        po.setDouble("ORDER_SUM", salesOrderDTO.getOrderSum());
        po.setDouble("ORDER_RECEIVABLE_SUM", salesOrderDTO.getOrderReceivableSum());
        po.saveIt();
	}
	
	/**
     * 设置装潢项目PO属性
     * @author Benzc
     * @date 2017年3月28日
     */

    @SuppressWarnings("rawtypes")
	public void getSoUpholster(Map map,String soNo) {
    	TtSoUpholsterPO tsuPo = new TtSoUpholsterPO();
    	tsuPo.setString("SO_NO", soNo);
    	tsuPo.setString("UPHOLSTER_CODE", map.get("upholsterCode"));
    	tsuPo.setString("UPHOLSTER_NAME", map.get("upholsterName"));
    	tsuPo.setDouble("STD_LABOUR_HOUR", map.get("stdLabourHour"));
    	tsuPo.setDouble("LABOUR_PRICE", map.get("labourPrice"));
    	tsuPo.setDouble("DISCOUNT", map.get("discount"));
    	tsuPo.setInteger("ACCOUNT_MODE", map.get("accountMode"));
    	tsuPo.setString("REMARK", map.get("remark"));
    	tsuPo.saveIt();
    }
    
    /**
     * 设置精品材料PO属性
     * @author Benzc
     * @date 2017年4月5日
     */

    @SuppressWarnings("rawtypes")
	public void getSoPart(Map map,String soNo) {
    	TtSoPartPo spPo = new TtSoPartPo();
    	spPo.setString("SO_NO", soNo);
    	spPo.setString("STORAGE_CODE", map.get("storageCode"));
    	spPo.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
    	spPo.setString("PART_NO", map.get("partNo"));
    	spPo.setString("PART_NAME", map.get("partName"));
    	spPo.setString("UNIT_CODE", map.get("unitCode"));
    	spPo.setDouble("PART_SALES_PRICE", map.get("partSalesPrice"));
    	spPo.setDouble("REAL_PRICE", map.get("realPrice"));
    	spPo.setInteger("PART_QUANTITY", map.get("partQuantity"));
    	spPo.setInteger("ACCOUNT_MODE", map.get("accountMode"));
    	spPo.setDouble("DISCOUNT", map.get("discount"));
    	spPo.setDouble("PART_SALES_AMOUNT", map.get("partSalesAmount"));
    	spPo.setString("REMARK", map.get("remark"));
    	spPo.saveIt();
    }
    
    /**
     * 设置服务项目项目PO属性
     * @author Benzc
     * @date 2017年3月28日
     */

    @SuppressWarnings("rawtypes")
	public void getSoService(Map map,String soNo) {
    	TtSoServicePO tssPo = new TtSoServicePO();
    	tssPo.setString("SO_NO", soNo);
    	tssPo.setString("SERVICE_CODE", map.get("serviceCode"));
    	tssPo.setString("SERVICE_NAME", map.get("serviceName"));
    	tssPo.setDouble("SERVICE_TYPE", map.get("serviceType"));
    	tssPo.setDouble("DIRECTIVE_PRICE", map.get("directivePrice"));
    	tssPo.setDouble("REAL_PRICE", map.get("realPrice"));
    	tssPo.setDouble("ACCOUNT_MODE", map.get("accountMode"));
    	tssPo.setDouble("RECEIVEABLE_AMOUNT", map.get("receiveableAmount"));
    	tssPo.setDouble("CONSIGNED_AMOUNT", map.get("consignedAmount"));
    	tssPo.setString("REMARK", map.get("remark"));
    	tssPo.saveIt();
    }
    
    /**
     * 设置装潢项目PO属性
     * @author Benzc
     * @date 2017年3月28日
     */

    @SuppressWarnings("rawtypes")
	public void setSoUpholster(Map map,String soNo,SalesOdditionalOrderDTO salesOddDTO) {
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
    		String itemId = map.get("itemId").toString();
    		//List<String> itemIds = findNotInDataBaseVIN(dto);
    		List<TtSoUpholsterPO> list = TtSoUpholsterPO.findBySQL("SELECT * FROM TT_SO_UPHOLSTER WHERE ITEM_ID=?", itemId);
        	for(int i = 0;i<list.size();i++){
        		TtSoUpholsterPO tsuPo = list.get(i);
            	tsuPo.setString("UPHOLSTER_CODE", map.get("upholsterCode"));
            	tsuPo.setString("UPHOLSTER_NAME", map.get("upholsterName"));
            	tsuPo.setDouble("STD_LABOUR_HOUR", map.get("stdLabourHour"));
            	tsuPo.setDouble("LABOUR_PRICE", map.get("labourPrice"));
            	tsuPo.setDouble("DISCOUNT", map.get("discount"));
            	tsuPo.setInteger("ACCOUNT_MODE", map.get("accountMode"));
            	tsuPo.setString("REMARK", map.get("remark"));
            	tsuPo.saveIt();           	
        	}
        	//删除
        	StringBuffer sql = new StringBuffer();
        	sql.append("SELECT * FROM TT_SO_UPHOLSTER WHERE SO_NO=?");
        	List<Object> queryList = new ArrayList<Object>();
        	queryList.add(soNo);
        	List<Map> list1 = DAOUtil.findAll(sql.toString(), queryList);//数据库数据
        	System.err.println(sql.toString()+" ......."+soNo);
        	List<Map> list3 = salesOddDTO.getSoDecrodateList();
        	List<String> list2 = new ArrayList<String>();//前台itemId
        	for(int i=0;i<list3.size();i++){
        		if(!StringUtils.isNullOrEmpty(list3.get(i).get("itemId"))){
        			String item = list3.get(i).get("itemId").toString();
            		list2.add(item);
        		}
        	}
        	for(int k=0 ;k<list1.size();k++){
            	String id=list1.get(k).get("ITEM_ID").toString();
            	System.err.println(id);
            	if(list2.contains(id)){
            		System.err.println("匹配"+id);
            	}else if(!list2.contains(id)){
            		System.err.println("不匹配"+id);
            		TtSoUpholsterPO po = TtSoUpholsterPO.findById(id);
            		po.delete();
            	}
        	}
    	}
    	else{
    		TtSoUpholsterPO tsuPo1 = new TtSoUpholsterPO();
    		tsuPo1.setString("SO_NO", soNo);
    		tsuPo1.setString("UPHOLSTER_CODE", map.get("upholsterCode"));
    		tsuPo1.setString("UPHOLSTER_NAME", map.get("upholsterName"));
    		tsuPo1.setDouble("STD_LABOUR_HOUR", map.get("stdLabourHour"));
    		tsuPo1.setDouble("LABOUR_PRICE", map.get("labourPrice"));
    		tsuPo1.setDouble("DISCOUNT", map.get("discount"));
    		tsuPo1.setInteger("ACCOUNT_MODE", map.get("accountMode"));
    		tsuPo1.setString("REMARK", map.get("remark"));
    		tsuPo1.saveIt();
    	}
    }
    
    /**
     * 设置精品材料PO属性
     * @author Benzc
     * @date 2017年4月5日
     */

    @SuppressWarnings("rawtypes")
	public void setSoPart(Map map,String soNo,SalesOdditionalOrderDTO salesOddDTO) {
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
        	String itemId = map.get("itemId").toString();
        	//System.err.println(itemId);
        	List<TtSoPartPo> list = TtSoPartPo.findBySQL("SELECT * FROM TT_SO_PART WHERE ITEM_ID=?", itemId);
        	for(int i = 0;i<list.size();i++){
        		TtSoPartPo spPo = list.get(i);
            	spPo.setString("STORAGE_CODE", map.get("storageCode"));
            	spPo.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
            	spPo.setString("PART_NO", map.get("partNo"));
            	spPo.setString("PART_NAME", map.get("partName"));
            	spPo.setString("UNIT_CODE", map.get("unitCode"));
            	spPo.setDouble("PART_SALES_PRICE", map.get("partSalesPrice"));
            	spPo.setDouble("REAL_PRICE", map.get("realPrice"));
            	spPo.setInteger("PART_QUANTITY", map.get("partQuantity"));
            	spPo.setInteger("ACCOUNT_MODE", map.get("accountMode"));
            	spPo.setDouble("DISCOUNT", map.get("discount"));
            	spPo.setDouble("PART_SALES_AMOUNT", map.get("partSalesAmount"));
            	spPo.setString("REMARK", map.get("remark"));
            	spPo.saveIt();
            	/*if (!StringUtils.isNullOrEmpty(itemId)) {
            		spPo.delete();
    			}*/
        	}
        	//删除
        	StringBuffer sql = new StringBuffer();
        	sql.append("SELECT * FROM TT_SO_PART WHERE SO_NO=?");
        	List<Object> queryList = new ArrayList<Object>();
        	queryList.add(soNo);
        	List<Map> list1 = DAOUtil.findAll(sql.toString(), queryList);//数据库数据
        	List<Map> list3 = salesOddDTO.getSoDecrodatePartList();
        	List<String> list2 = new ArrayList<String>();//前台itemId
        	for(int i=0;i<list3.size();i++){
        		if(!StringUtils.isNullOrEmpty(list3.get(i).get("itemId"))){
        			String item = list3.get(i).get("itemId").toString();
            		list2.add(item);
        		}
        	}
        	for(int k=0 ;k<list1.size();k++){
            	String id=list1.get(k).get("ITEM_ID").toString();
            	if(list2.contains(id)){
            		System.err.println("匹配"+id);
            	}else if(!list2.contains(id)){
            		System.err.println("不匹配"+id);
            		TtSoPartPo po = TtSoPartPo.findById(id);
            		po.delete();
            	}
        	}
    	}else{
    		TtSoPartPo spPo1 = new TtSoPartPo();
    		spPo1.setString("SO_NO", soNo);
    		spPo1.setString("STORAGE_CODE", map.get("storageCode"));
    		spPo1.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
    		spPo1.setString("PART_NO", map.get("partNo"));
    		spPo1.setString("PART_NAME", map.get("partName"));
    		spPo1.setString("UNIT_CODE", map.get("unitCode"));
    		spPo1.setDouble("PART_SALES_PRICE", map.get("partSalesPrice"));
    		spPo1.setDouble("REAL_PRICE", map.get("realPrice"));
    		spPo1.setInteger("PART_QUANTITY", map.get("partQuantity"));
    		spPo1.setInteger("ACCOUNT_MODE", map.get("accountMode"));
    		spPo1.setDouble("DISCOUNT", map.get("discount"));
    		spPo1.setDouble("PART_SALES_AMOUNT", map.get("partSalesAmount"));
    		spPo1.setString("REMARK", map.get("remark"));
    		spPo1.saveIt();
    	}

    }
    
    /**
     * 设置服务项目项目PO属性
     * @author Benzc
     * @date 2017年3月28日
     */

    @SuppressWarnings("rawtypes")
	public void setSoService(Map map,String soNo,SalesOdditionalOrderDTO salesOddDTO) {
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
    		String itemId = map.get("itemId").toString();
        	List<TtSoServicePO> list = TtSoServicePO.findBySQL("SELECT * FROM TT_SO_SERVICE WHERE ITEM_ID=?", itemId);
        	for(int i = 0;i<list.size();i++){
        		TtSoServicePO tssPo = list.get(i);
            	tssPo.setString("SERVICE_CODE", map.get("serviceCode"));
            	tssPo.setString("SERVICE_NAME", map.get("serviceName"));
            	tssPo.setDouble("SERVICE_TYPE", map.get("serviceType"));
            	tssPo.setDouble("DIRECTIVE_PRICE", map.get("directivePrice"));
            	tssPo.setDouble("REAL_PRICE", map.get("realPrice"));
            	tssPo.setDouble("ACCOUNT_MODE", map.get("accountMode"));
            	tssPo.setDouble("RECEIVEABLE_AMOUNT", map.get("receiveableAmount"));
            	tssPo.setDouble("CONSIGNED_AMOUNT", map.get("consignedAmount"));
            	tssPo.setString("REMARK", map.get("remark"));
            	tssPo.saveIt();
            	/*if (!StringUtils.isNullOrEmpty(itemId)) {
            		tssPo.delete();
    			}*/
        	}
        	//删除
        	StringBuffer sql = new StringBuffer();
        	sql.append("SELECT * FROM TT_SO_SERVICE WHERE SO_NO=?");
        	List<Object> queryList = new ArrayList<Object>();
        	queryList.add(soNo);
        	List<Map> list1 = DAOUtil.findAll(sql.toString(), queryList);//数据库数据
        	List<Map> list3 = salesOddDTO.getSoServicesList();
        	List<String> list2 = new ArrayList<String>();//前台itemId
        	for(int i=0;i<list3.size();i++){
        		if(!StringUtils.isNullOrEmpty(list3.get(i).get("itemId"))){
        			String item = list3.get(i).get("itemId").toString();
            		list2.add(item);
        		}
        	}
        	for(int k=0 ;k<list1.size();k++){
            	String id=list1.get(k).get("ITEM_ID").toString();
            	if(list2.contains(id)){
            		System.err.println("匹配"+id);
            	}else if(!list2.contains(id)){
            		System.err.println("不匹配"+id);
            		TtSoServicePO po = TtSoServicePO.findById(id);
            		po.delete();
            	}
        	}
    	}else{
    		TtSoServicePO tssPo = new TtSoServicePO();
    		tssPo.setString("SO_NO", soNo);
        	tssPo.setString("SERVICE_CODE", map.get("serviceCode"));
        	tssPo.setString("SERVICE_NAME", map.get("serviceName"));
        	tssPo.setDouble("SERVICE_TYPE", map.get("serviceType"));
        	tssPo.setDouble("DIRECTIVE_PRICE", map.get("directivePrice"));
        	tssPo.setDouble("REAL_PRICE", map.get("realPrice"));
        	tssPo.setDouble("ACCOUNT_MODE", map.get("accountMode"));
        	tssPo.setDouble("RECEIVEABLE_AMOUNT", map.get("receiveableAmount"));
        	tssPo.setDouble("CONSIGNED_AMOUNT", map.get("consignedAmount"));
        	tssPo.setString("REMARK", map.get("remark"));
        	tssPo.saveIt();
    	}
    	
    }
    
    /**
     * 查询要删除的itemId值
     * @author Benzc
     * @date 2017年4月10日
     *//*
    @SuppressWarnings("rawtypes")
	public List<String> findNotInDataItemId(SoUpholsterDTO dto) {
    	String sql = "SELECT ITEM_ID FROM TT_SO_UPHOLSTER WHERE ITEM_ID = '" + dto.getItemId() + "'";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中ITEM_ID
		List<String> para = new ArrayList<String>();// 用于保存数据库中相关所有的ITEM_ID
		List<String> data = new ArrayList<String>();// 用于保存前台相关所有的ITEM_ID
		List<String> result = new ArrayList<String>();// 用于保存数据库中的ITEM_ID而不存在于前台传过来的ITEM_ID
		for (SoUpholsterDTO sii : dto.getSoDecrodateList()) {
			if (sii.getItemId() != null) {
				data.add(sii.getItemId().toString());
			}
		}
		for (Map map : list) {
			if (map.get("ITEM_ID") != null) {
				para.add(map.get("ITEM_ID").toString());
			}
		}
		if (dto.getSoDecrodateList().size() > 0) {
			for (String str : para) {
				if (!data.contains(str)) {// 传到后台的ITEM_ID值存在数据库中而不存在前台,做删除操作的ITEM_ID
					result.add(str);
				}
			}
		} else {
			result = para;// 因为前台传过来的数据中没有数据,所以将数据库中的数据视为result里的项
		}
		return result;	
    }*/
    
	/**
	 * 装潢项目分页查询
	 * @author Benzc
	 * @date 2017年3月28日
	 */
	@Override
	public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pagedto = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select *,14061002 AS ACCOUNT_MODE,1.00 AS DISCOUNT,0 AS DISCOUNT_AMOUNT,0 AS RECEIVE_AMOUNT from ( SELECT DEALER_CODE,CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE AS UPHOLSTER_CODE, ");
		sql.append("LOCAL_LABOUR_NAME, LABOUR_NAME AS UPHOLSTER_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,");
		sql.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,");
		sql.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS, ");
		sql.append(" case when Created_at < Updated_at then Updated_at else Created_at end as NewDate, ");
		sql.append(
				" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=12781002) THEN 12781002 ELSE IS_MEMBER END IS_MEMBER  ");
		sql.append(" FROM TM_DECRODATE_ITEM " + "WHERE DEALER_CODE = '" + dealerCode + "'");
		Utility.sqlToLike(sql, queryParam.get("localLabourCode"), "LOCAL_LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("localLabourName"), "LOCAL_LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("labourCode"), "LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("labourName"), "LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("spellCode"), "SPELL_CODE", null);
		String downTag = queryParam.get("downTag");
		if (null != downTag && !"".equals(downTag.trim())) {
			if (downTag.equals(12781001))
				sql.append(" and DOWN_TAG = " + downTag);
			else
				sql.append(" and (DOWN_TAG <> 12781001 OR DOWN_TAG is null) ");
		}
		String repairGroupCode = "";
		String MainGroup = queryParam.get("mainGroupCode"); 
		String SubGroup = queryParam.get("subGroupCode");
		if(!StringUtils.isNullOrEmpty(MainGroup) || !StringUtils.isNullOrEmpty(SubGroup)){
			if(StringUtils.isNullOrEmpty(MainGroup)){
				MainGroup = "";
			}
			if(StringUtils.isNullOrEmpty(SubGroup)){
				SubGroup = "";
			}
			repairGroupCode = MainGroup +SubGroup;
		}
		if (repairGroupCode != null && !repairGroupCode.trim().equals("")) {
			sql.append(" and REPAIR_GROUP_CODE = '" + repairGroupCode + "'");
		}
		String workerTypeCode = queryParam.get("workerTypeCode");
		if (workerTypeCode != null && !workerTypeCode.trim().equals("")) {
			sql.append(" and WORKER_TYPE_CODE = '" + workerTypeCode + "'");
		}
		
		String modelLabourCode = queryParam.get("modelLabourCode");		
		if (modelLabourCode != null && !modelLabourCode.trim().equals("")) {
			sql.append(" and (1=2 ");
			String[] codeArray = modelLabourCode.split(",");
			for(int i = 0;i<codeArray.length;i++){
				sql.append( " or MODEL_LABOUR_CODE = '" + codeArray[i] + "'");
			}
			sql.append(" ) ");
		}
		
		String nullLabour = queryParam.get("nullLabour");
		if (Utility.testString(nullLabour)) {
			if (nullLabour.equals(12781001))
				sql.append(" and (STD_LABOUR_HOUR is null or STD_LABOUR_HOUR = 0) ");
			else
				sql.append(" and ( STD_LABOUR_HOUR <> 0) ");
		}
		String startDate = queryParam.get("startDate");
		String endDate = queryParam.get("endDate");
		if (startDate != null && endDate != null) {
			sql.append(")A  where 1=1 ");
			sql.append(Utility.getDateCond("A", "NewDate", startDate, endDate));
		} else {
			sql.append(")A  WHERE 1=1 ");
		}
		System.err.println(sql.toString());
		List<Object> queryList = new ArrayList<Object>();
		//this.setDecrodate(sql, queryParam, queryList);
		pagedto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pagedto;
	}

	/**
	 * 根据id查询服务订单信息
	 * @author Benzc
	 * @date 2017年3月29日
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> querySalesOdditionalOrderInfoByid(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT  C.PRODUCTING_AREA,PO.GENDER,A.OTHER_AMOUNT_OBJECT,A.OFFSET_AMOUNT,A.MOBILE,A.CONTACTOR_NAME,em.EMPLOYEE_NAME,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,");
		sb.append(" pa.CONFIG_NAME,co.COLOR_NAME,A.CUSTOMER_NAME,A.INVOICE_MODE,A.VEHICLE_PRICE,A.PRODUCT_CODE,B.PRODUCT_NAME,A.DELIVERY_MODE,A.PAY_MODE,A.VEHICLE_DESC,A.DISCOUNT_MODE_CODE,");
		sb.append(" A.DEALER_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
		sb.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE,A. VIN,A.SOLD_BY,A.SHEET_CREATED_BY,A.PHONE,A.ADDRESS,A.CONTRACT_MATURITY,A.LOAN_ORG,A.PRE_INVOICE_AMOUNT,A.REMARK,A.LICENSE,A.VEHICLE_PURPOSE,");
		sb.append(" A.SO_NO,A.SALES_NO,A.SERVICE_NO,A.STORAGE_CODE,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CUSTOMER_TYPE,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CERTIFICATE_NO,A.CT_CODE, ");
		sb.append(" A.UPHOLSTER_SUM,A.PRESENT_SUM,A.OTHER_SERVICE_SUM,A.COMMISSION_BALANCE,A.INSURANCE_SUM,A.PLATE_SUM,A.LOAN_SUM,A.TAX_SUM,A.CONSIGNED_SUM,A.CON_RECEIVABLE_SUM,A.ORDER_SUM,A.ORDER_RECEIVABLE_SUM, ");
		sb.append(" A.OWNER_NO, A.OWNER_NAME,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE FROM TT_SALES_ORDER A  ");
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
		sb.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
		sb.append(" left  join   tm_potential_customer  PO   on   A.CUSTOMER_NO=PO.CUSTOMER_NO and A.DEALER_CODE=po.DEALER_CODE");
		sb.append(" left  join   tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
		sb.append(" left  join   TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and B.DEALER_CODE=se.DEALER_CODE");
		sb.append(" left  join   TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and mo.BRAND_CODE=se.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=mo.DEALER_CODE");
		sb.append(" left  join   tm_configuration pa   on   B.CONFIG_CODE=pa.CONFIG_CODE and pa.BRAND_CODE=mo.BRAND_CODE and pa.SERIES_CODE=pa.SERIES_CODE and pa.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=pa.DEALER_CODE");
		sb.append(" left  join   tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
		sb.append(" left join TM_EMPLOYEE em  on A.SOLD_BY=em.EMPLOYEE_NO AND A.DEALER_CODE=em.DEALER_CODE");
		sb.append(" WHERE  A.D_KEY = "+ DictCodeConstants.D_KEY+ " AND NOT EXISTS(SELECT C.SO_NO FROM TT_SALES_ORDER C WHERE C.DEALER_CODE = A.DEALER_CODE AND C.VIN = A.VIN AND C.SO_NO != A.SO_NO "); 
		sb.append(" AND A.BUSINESS_TYPE = 13001004 AND A.ORDER_SORT = 13861002 AND C.BUSINESS_TYPE = 13001001 AND C.SO_STATUS != 13011055 AND C.SO_STATUS != 13011060)");
		sb.append(" and A.SO_NO= ?");
    	List<Object> queryList = new ArrayList<Object>();
    	queryList.add(id);
    	System.err.println(sb.toString());
 
        return DAOUtil.findAll(sb.toString(), queryList).get(0);
	}
    
	/**
	 * 查询装潢项目信息
	 * @author Benzc
	 * @date 2017年3月29日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryUpholster(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DEALER_CODE,ITEM_ID,UPHOLSTER_CODE,UPHOLSTER_NAME,STD_LABOUR_HOUR,");
		sb.append(" LABOUR_PRICE,ACCOUNT_MODE,DISCOUNT,REMARK");
		sb.append(" from tt_so_upholster where SO_NO=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}
	
	/**
	 * 查询精品材料信息
	 * @author Benzc
	 * @date 2017年4月6日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryPart(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DEALER_CODE,ITEM_ID,STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO,PART_NAME,UNIT_CODE,PART_SALES_PRICE,REAL_PRICE,");
		sb.append(" PART_QUANTITY,ACCOUNT_MODE,DISCOUNT,PART_SALES_AMOUNT,REMARK");
		sb.append(" from tt_so_part where SO_NO=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}
	
	/**
	 * 服务项目信息
	 * @author Benzc
	 * @date 2017年3月29日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryService(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DEALER_CODE,ITEM_ID,SERVICE_CODE,SERVICE_NAME,SERVICE_TYPE,DIRECTIVE_PRICE,");
		sb.append(" REAL_PRICE,ACCOUNT_MODE,RECEIVEABLE_AMOUNT,CONSIGNED_AMOUNT,REMARK");
		sb.append(" from tt_so_service where SO_NO=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}
    
	/**
	 * 优惠模式下拉框
	 * @author Benzc
	 * @date 2017年3月29日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> discountSelect(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,DISCOUNT_MODE_CODE,DISCOUNT_MODE_NAME FROM TM_DISCOUNT_MODE where 1=1");
		List<Object> list = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sb.toString(),list);
		return result;
	}
	
    /**
     * 精品材料查询
     * @author Benzc
     * @date 2017年3月30日
     */
	@Override
	public PageInfoDto queryPart(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT 0 as PART_SALES_PRICE,0 as REAL_PRICE,1 as PART_QUANTITY,1 as DISCOUNT,0 as PART_SALES_AMOUNT,0 as DISCOUNT_AMOUNT,14061002 AS ACCOUNT_MODE,");
		sb.append(" DEALER_CODE,PART_NO,PART_NAME,SALES_PRICE,COST_PRICE,CLAIM_PRICE,INSURANCE_PRICE,UNIT_CODE,");
		sb.append(" REMARK,STORAGE_CODE,STORAGE_POSITION_CODE,STOCK_QUANTITY,BORROW_QUANTITY,LEND_QUANTITY,");
		sb.append(" LOCKED_QUANTITY,NODE_PRICE as INTERIOR_PRICE ");
		sb.append(" FROM TM_PART_STOCK A ");
		sb.append(" WHERE DEALER_CODE = '"+ dealerCode);				
		sb.append("' and PART_STATUS<>"+DictCodeConstants.DICT_IS_YES);						
		sb.append(" AND A.D_KEY =" + CommonConstants.D_KEY);
		List<Object> queryList = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("storageName"))) {
            sb.append(" and STORAGE_CODE like ?");
            queryList.add("%" + queryParam.get("storageName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("partNo"))) {
            sb.append(" and PART_NO like ?");
            queryList.add("%" + queryParam.get("partNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
            sb.append(" and PART_NAME like ?");
            queryList.add("%" + queryParam.get("partName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))) {
            sb.append(" and SPELL_CODE like ?");
            queryList.add("%" + queryParam.get("spellCode") + "%");
        }
		
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
    
	/**
	 * 退料装潢信息查询
	 * @author Benzc
	 * @date 2017年4月7日
	 */
	@Override
	public PageInfoDto querySalesOrderPart(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT b.DEALER_CODE,b.UNIT_CODE,b.PART_COST_PRICE,b.PART_NAME,b.STORAGE_CODE,b.STORAGE_POSITION_CODE,b.PART_QUANTITY,b.INTERIOR_PRICE,");
		sb.append(" b.PART_SALES_PRICE,b.PART_SALES_AMOUNT,b.DISCOUNT,b.REAL_PRICE,b.ACCOUNT_MODE,a.SO_NO,a.VIN,a.LICENSE,b.PART_NO,a.CUSTOMER_NO,sto.STORAGE_NAME,");
		sb.append(" a.CUSTOMER_NAME FROM TT_SALES_ORDER A RIGHT JOIN TT_SO_PART B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" LEFT JOIN TM_STORAGE sto ON b.STORAGE_CODE=sto.STORAGE_CODE");
		sb.append(" WHERE B.IS_FINISHED = " + DictCodeConstants.DICT_IS_YES);
		
		List<Object> list = new ArrayList<Object>();
		this.setHere(sb, queryParam, list);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(),list);
		System.err.println(sb.toString());
		return result;
	}

	public void setHere(StringBuffer sb, Map<String, String> queryParam,List<Object> list) {
		System.out.println(queryParam.get("roNo"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))) {
            sb.append(" and B.SO_NO like ?");
            list.add("%" + queryParam.get("roNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("partNo"))) {
            sb.append(" and B.PART_NO like ?");
            list.add("%" + queryParam.get("partNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" and A.LICENSE like ?");
            list.add("%" + queryParam.get("license") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and A.CUSTOMER_NO like ?");
            list.add("%" + queryParam.get("customerNo") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and A.CUSTOMER_NAME like ?");
            list.add("%" + queryParam.get("customerName") + "%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and A.VIN like ?");
            list.add("%" + queryParam.get("vin") + "%");
        }
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesPart(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT b.DEALER_CODE,b.UNIT_CODE,b.PART_COST_PRICE,b.PART_NAME,b.STORAGE_CODE,b.STORAGE_POSITION_CODE,b.PART_QUANTITY,b.INTERIOR_PRICE,");
		sb.append(" b.PART_SALES_PRICE,b.PART_SALES_AMOUNT,b.DISCOUNT,b.REAL_PRICE,b.ACCOUNT_MODE,a.SO_NO,a.VIN,a.LICENSE,b.PART_NO,a.CUSTOMER_NO,");
		sb.append(" a.CUSTOMER_NAME FROM TT_SALES_ORDER A RIGHT JOIN TT_SO_PART B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
		sb.append(" WHERE B.IS_FINISHED = " + DictCodeConstants.DICT_IS_YES +" AND B.PART_NO=" + id);
		System.err.println(sb.toString());
		List<Object> list = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sb.toString(),list);
		return result;
	}
    
	/**
	 * 订单车辆信息查询
	 * @author Benzc
	 * @date 2017年4月12日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySalesOddBySoNo(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT A.DEALER_CODE,A.CUSTOMER_NAME,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,A.IS_SPEEDINESS,A.DELIVERY_MODE_ELEC,A.IS_ELECTRICITY,A.ESC_ORDER_STATUS,A.ESC_TYPE,A.EC_ORDER_NO,A.CUS_SOURCE,A.DEPOSIT_AMOUNT,A.EC_ORDER,A.VERIFICATION_TIMEOUT,");
		sb.append(" B.CONFIG_CODE,COALESCE(A.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ,A.VIN,A.SOLD_BY,A.VEHICLE_PRICE, ");
		sb.append(" A.SO_NO AS SALES_NO,A.SHEET_CREATE_DATE,A.SO_STATUS,A.CUSTOMER_NO,A.CONTRACT_NO,A.CONTRACT_DATE,A.OWNED_BY,A.LOCK_USER,A.CT_CODE,A.CERTIFICATE_NO,A.DELIVERING_DATE,C.IS_DIRECT,A.BUSINESS_TYPE, ");
		sb.append(" mo.MODEL_NAME,se.SERIES_NAME,br.BRAND_NAME,pa.CONFIG_NAME,co.COLOR_NAME,em.USER_NAME");
		sb.append(" FROM TT_SALES_ORDER A  ");
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY ");
		sb.append(" LEFT JOIN TM_VS_STOCK C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN");
		sb.append(" LEFT JOIN TM_MODEL   mo   on   B.MODEL_CODE=mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE");
		sb.append(" LEFT JOIN TM_SERIES  se   on   B.SERIES_CODE=se.SERIES_CODE and B.DEALER_CODE=se.DEALER_CODE");
		sb.append(" LEFT JOIN tm_brand   br   on   B.BRAND_CODE = br.BRAND_CODE and B.DEALER_CODE=br.DEALER_CODE");
		sb.append(" LEFT JOIN tm_configuration pa on   B.CONFIG_CODE=pa.CONFIG_CODE and B.DEALER_CODE=pa.DEALER_CODE");
		sb.append(" LEFT JOIN tm_color   co   on   B.COLOR_CODE = co.COLOR_CODE and B.DEALER_CODE=co.DEALER_CODE");
		sb.append(" LEFT JOIN TM_USER em  on A.SOLD_BY=em.USER_ID AND A.DEALER_CODE=em.DEALER_CODE");
		sb.append(" WHERE  A.D_KEY = 0 AND A.SO_NO='"+id+"'");
		System.err.println(sb.toString());
		List<Object> list = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sb.toString(),list);
		return result;
	}

	

}
