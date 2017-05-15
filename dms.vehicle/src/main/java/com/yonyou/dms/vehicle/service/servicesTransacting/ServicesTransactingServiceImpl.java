package com.yonyou.dms.vehicle.service.servicesTransacting;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtServiceInsurancePO;
import com.yonyou.dms.common.domains.PO.basedata.TtServiceNumberPlatePO;
import com.yonyou.dms.common.domains.PO.basedata.TtServiceTaxPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSoServicePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.servicesTransacting.ServicesTransactingDTO;

/**
 * 服务项目办理Service实现类
 * @author Benzc
 * @date 2017年4月13日
 */
@Service
public class ServicesTransactingServiceImpl implements ServicesTransactingService{
	
	@Autowired
    private CommonNoService commonNoService;
    
	/**
	 * 首页分页查询
	 * @author Benzc
	 * @date 2017年4月13日
	 */
	@Override
	public PageInfoDto QueryServicesTransacting(Map<String, String> queryParam) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("select a.DEALER_CODE,a.SO_NO,a.BUSINESS_TYPE, a.CUSTOMER_NO, a.CUSTOMER_NAME, ");
		sb.append(" a.CUSTOMER_TYPE, a.CONTACTOR_NAME, a.PHONE, a.ADDRESS, a.DELIVERY_ADDRESS, ");
		sb.append(" a.CT_CODE, a.CERTIFICATE_NO, a.SO_STATUS, a.SHEET_CREATE_DATE, ");
		sb.append(" a.SHEET_CREATED_BY, a.CONTRACT_NO, a.CONTRACT_DATE, a.CONTRACT_EARNEST, ");
		sb.append(" a.CONTRACT_MATURITY, a.PAY_MODE, a.LOAN_ORG, a.DELIVERY_MODE, a.CONSIGNEE_CODE, ");
		sb.append(" a.CONSIGNEE_NAME, a.DELIVERING_DATE, a.SOLD_BY, a.AUDITED_BY, a.RE_AUDITED_BY, ");
		sb.append(" a.REMARK, a.PRODUCT_CODE, a.PRE_INVOICE_AMOUNT, a.INVOICE_MODE, a.LICENSE, ");
		sb.append(" a.STORAGE_CODE, a.STORAGE_POSITION_CODE, a.DISPATCHED_DATE, a.DISPATCHED_BY, a.VIN, ");
		sb.append(" a.CONFIRMED_DATE, a.VEHICLE_PURPOSE, a.DIRECTIVE_PRICE, a.CONFIRMED_BY, ");
		sb.append(" a.STOCK_OUT_DATE, a.STOCK_OUT_BY, a.RETURN_IN_DATE, a.RETURN_IN_BY, a.IS_PERMUTED, ");
		sb.append(" a.PERMUTED_VIN, a.PERMUTED_DESC, a.VEHICLE_PRICE, a.PRESENT_SUM, a.OFFSET_AMOUNT, ");
		sb.append(" a.GARNITURE_SUM, a.UPHOLSTER_SUM, a.ORDER_RECEIVABLE_SUM, a.ABORTING_FLAG, ");
		sb.append(" a.ABORTING_DATE, a.ABORTING_BY, a.ABORTING_REASON, a.PENALTY_AMOUNT, a.ABORTED_DATE, ");
		sb.append(" a.ABORTED_BY, a.INSURANCE_SUM, a.ORDER_PAYED_AMOUNT, a.TAX_SUM, a.ORDER_DERATED_SUM, ");
		sb.append(" a.PLATE_SUM, a.ORDER_ARREARAGE_AMOUNT, a.LOAN_SUM, a.CON_RECEIVABLE_SUM, ");
		sb.append(" a.CON_PAYED_AMOUNT, a.ORDER_SUM, a.CON_ARREARAGE_AMOUNT, a.PAY_OFF, ");
		sb.append(" a.ALLOCATING_TYPE, a.CONSIGNER_CODE, a.CONSIGNER_NAME, a.CONSIGNED_SUM, a.OWNED_BY,U.USER_NAME,mo.MODEL_NAME,co.COLOR_NAME,pa.CONFIG_NAME, ");
		sb.append(" b.MODEL_CODE, b.CONFIG_CODE, COALESCE(a.COLOR_CODE,b.COLOR_CODE) as COLOR_CODE  from TT_SALES_ORDER a ");
		sb.append(" left join ("+CommonConstants.VM_VS_PRODUCT+") b on (a.PRODUCT_CODE = b.PRODUCT_CODE) AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY");
		sb.append(" LEFT JOIN TM_USER U ON a.SOLD_BY=U.USER_ID");
		sb.append(" LEFT JOIN TM_MODEL   mo   on   b.MODEL_CODE=mo.MODEL_CODE and b.DEALER_CODE=mo.DEALER_CODE");
		sb.append(" left  join   tm_configuration pa   on   b.CONFIG_CODE=pa.CONFIG_CODE and b.DEALER_CODE=pa.DEALER_CODE");
		sb.append(" LEFT JOIN tm_color   co   on  COALESCE(a.COLOR_CODE,b.COLOR_CODE) = co.COLOR_CODE and b.DEALER_CODE=co.DEALER_CODE");
		sb.append("  WHERE a.DEALER_CODE = '"+ dealerCode+ "' AND a.D_KEY="+ CommonConstants.D_KEY);
		sb.append(" AND a.SO_STATUS<> " + DictCodeConstants.DICT_SO_STATUS_NOT_AUDIT);
		sb.append(" AND a.SO_STATUS<>" + DictCodeConstants.DICT_SO_STATUS_SYSTEM_REJECT);
		sb.append(" AND a.SO_STATUS<> "+ DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL );
		List<Object> queryList = new ArrayList<Object>();
		this.setWhere(sb, queryParam, queryList);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	/**
	 * 查询条件设置
	 * @param sb
	 * @param queryParam
	 * @param queryList
	 */
	public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		System.err.println(queryParam.get("vin"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sb.append(" and a.VIN like ?");
			queryList.add("%"+queryParam.get("vin")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
			sb.append(" and a.CUSTOMER_NAME like ?");
			queryList.add("%"+queryParam.get("customerName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginCreate"))) {
            sb.append(" and a.SHEET_CREATE_DATE >= ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("beginCreate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("endCreate"))) {
            sb.append(" and a.SHEET_CREATE_DATE <= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("endCreate")));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and a.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }
	}
    
	/**
	 * 险种查询
	 * @author Benzc
	 * @date 2017年4月14日
	 */
	@Override
	public PageInfoDto QueryInsurance(Map<String, String> queryParam) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("select '' as IS_SELECTED,A.* FROM ("+CommonConstants.VM_INSURANCE_TYPE+") A WHERE A.DEALER_CODE='"+dealerCode+"' and A.INSURANCE_TYPE_STATUS=12781001");
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
    /**
     * 险种下拉框
     * @author Benzc
     * @date 2017年4月14日
     */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> QuerySelectInsurance() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT INSURANCE_TYPE_CODE,INSURANCE_TYPE_NAME,DEALER_CODE from TM_INSURANCE_TYPE ");
        List<Object> params = new ArrayList<Object>();
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;
	}
    
	/**
	 * 保险公司下拉框
	 * @author Benzc
	 * @date 2017年4月14日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> QuerySelectInsuratione() throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT INSURATION_CODE,INSURATION_NAME,DEALER_CODE FROM TM_INSURANCE");
        List<Object> params = new ArrayList<Object>();
        List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
        return list;
	}
    
	/**
	 * 根据订单编号查询服务项目
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@Override
	public PageInfoDto QueryService(String id) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SO_SERVICE WHERE DEALER_CODE=" + dealerCode + " AND D_KEY=" + CommonConstants.D_KEY + " AND SO_NO=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	/**
	 * 根据VIN查询保险办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@Override
	public PageInfoDto QueryInsurance(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SERVICE_INSURANCE WHERE DEALER_CODE=" + dealerCode + " AND D_KEY=" + CommonConstants.D_KEY + " AND VIN=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	/**
	 * 根据VIN查询税费办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@Override
	public PageInfoDto QueryTax(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SERVICE_TAX WHERE DEALER_CODE=" +dealerCode+ " AND D_KEY=" + CommonConstants.D_KEY +" AND VIN=?");
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	/**
	 * 根据VIN查询牌照办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@Override
	public PageInfoDto QueryLicense(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SERVICE_NUMBER_PLATE WHERE DEALER_CODE=" +dealerCode+" AND D_KEY=" + CommonConstants.D_KEY +" AND VIN=?" );
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
        return result;
	}
	
	/**
	 * 根据VIN查询贷款办理结果
	 * @author Benzc
	 * @date 2017年4月15日           
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map QueryLoan(String vin) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM TT_SERVICE_LOAN WHERE DEALER_CODE=" +dealerCode+" AND D_KEY=" + CommonConstants.D_KEY +" AND VIN=" +vin );
		Map result = DAOUtil.findFirst(sb.toString(), null);
		System.err.println(sb.toString());
        return result;
	}
    
	/**
	 * 服务项目办理新增后保存
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void addServiceTransacting(ServicesTransactingDTO servicesTransactingDTO, String soNo,String vin)
			throws ServiceBizException {
		//订单服务项目保存
		if (servicesTransactingDTO.getOrderService().size() > 0 && servicesTransactingDTO.getOrderService()!= null) {
            List<Map> list  =  servicesTransactingDTO.getOrderService();
            for(int i = 0;i<list.size();i++){
            	getSoService(list.get(i),soNo);
            }
        }
		//保险办理结果保存
		if (servicesTransactingDTO.getInsurancManage().size() > 0 && servicesTransactingDTO.getInsurancManage()!= null) {
            List<Map> list  =  servicesTransactingDTO.getInsurancManage();
            for(int i = 0;i<list.size();i++){
            	getServiceInsurance(list.get(i),vin);
            }
	    }
		//税费办理结果
		if (servicesTransactingDTO.getTaxManage().size() > 0 && servicesTransactingDTO.getTaxManage()!= null) {
            List<Map> list  =  servicesTransactingDTO.getTaxManage();
            for(int i = 0;i<list.size();i++){
            	getServiceTax(list.get(i),vin);
            }
	    }
		//牌照办理结果
		if (servicesTransactingDTO.getLicenseManage().size() > 0 && servicesTransactingDTO.getLicenseManage()!= null) {
            List<Map> list  =  servicesTransactingDTO.getLicenseManage();
            for(int i = 0;i<list.size();i++){
            	getServiceNumberPlate(list.get(i),vin);
            }
	    }
	}
	
	/**
     * 设置订单服务项目PO属性
     * @author Benzc
     * @date 2017年4月15日
     */
    @SuppressWarnings("rawtypes")
	public void getSoService(Map map,String soNo) {
    	List<TtSoServicePO> list = TtSoServicePO.findBySQL("SELECT * FROM TT_SO_SERVICE WHERE SO_NO=?", soNo);
    	for(int i = 0;i<list.size();i++){
    		TtSoServicePO tssPo = list.get(i);
    		tssPo.setInteger("IS_COMPLETED", map.get("isCompleted"));
        	tssPo.setString("COMPLETED_DATE", map.get("completedDate"));
        	tssPo.saveIt();
    	}
    }
    
    /**
     * 设置保险办理结果PO属性
     * @author Benzc
     * @date 2017年4月15日
     */
    @SuppressWarnings("rawtypes")
	public void getServiceInsurance(Map map,String vin) {
    	System.err.println(map.get("itemId"));
    	System.err.println(map.get("insuranceTypeCode"));
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
    		List<Object> insuranceList = new ArrayList<Object>();
    		insuranceList.add(map.get("itemId"));
    		insuranceList.add(vin);
    		List<TtServiceInsurancePO> list = TtServiceInsurancePO.findBySQL("SELECT * FROM TT_SERVICE_INSURANCE WHERE ITEM_ID=? AND VIN=?", insuranceList.toArray());
    		System.err.println(list);
        	for(int i = 0;i<list.size();i++){
        		TtServiceInsurancePO tsiPo = list.get(i);
        		tsiPo.setString("INSURANCE_TYPE_CODE", map.get("insuranceTypeCode"));
        		tsiPo.setString("INSURATION_CODE", map.get("insurationCode"));
        		tsiPo.setString("INSURANCE_BEGIN_DATE", map.get("insuranceBeginDate"));
        		tsiPo.setString("INSURANCE_END_DATE", map.get("insuranceEndDate"));
        		tsiPo.setString("INSURANCE_BILL_NO", map.get("insuranceBillNo"));
        		tsiPo.setInteger("RE_INSURE", map.get("reInsure"));
        		if(map.get("actualExpending") == null){
        			tsiPo.setDouble("ACTUAL_EXPENDING",0);
        		}else{
        			tsiPo.setDouble("ACTUAL_EXPENDING", map.get("actualExpending"));
        		}
        		tsiPo.setString("COMPLETED_BY", map.get("completedBy"));
        		tsiPo.setString("COMPLETED_DATE", map.get("completedDate2"));
        		tsiPo.setString("INSURANCE_INTRO_MAN", map.get("insuranceIntroMan"));
        		tsiPo.setString("RECORD_DATE", map.get("recordDate"));
        		tsiPo.setString("RECORDER", map.get("recorder"));
        		tsiPo.setInteger("IS_INSURE_REMIND", map.get("isInsureRemind"));
        		tsiPo.setString("INSURANCE_BUY_DATE", map.get("insuranceBuyDate"));
        		tsiPo.setInteger("IS_SELF_COMPANY_INSURANCE", map.get("isSelfCompanyInsurance"));
        		tsiPo.setString("REMARK", map.get("remark"));
        		tsiPo.saveIt();
        	}
    	}else{
    		TtServiceInsurancePO tsiPo2 = new TtServiceInsurancePO();
    		Long id = commonNoService.getId("ID");
    		tsiPo2.setLong("ITEM_ID", id);
    		tsiPo2.setString("VIN", vin);
    		tsiPo2.setString("INSURANCE_TYPE_CODE", map.get("insuranceTypeCode"));
    		tsiPo2.setString("INSURATION_CODE", map.get("insurationCode"));
    		tsiPo2.setString("INSURANCE_BEGIN_DATE", map.get("insuranceBeginDate"));
    		tsiPo2.setString("INSURANCE_END_DATE", map.get("insuranceEndDate"));
    		tsiPo2.setString("INSURANCE_BILL_NO", map.get("insuranceBillNo"));
    		tsiPo2.setInteger("RE_INSURE", map.get("reInsure"));
    		if(map.get("actualExpending") == null){
    			tsiPo2.setDouble("ACTUAL_EXPENDING",0);
    		}else{
    			tsiPo2.setDouble("ACTUAL_EXPENDING", map.get("actualExpending"));
    		}
    		tsiPo2.setString("COMPLETED_BY", map.get("completedBy"));
    		tsiPo2.setString("COMPLETED_DATE", map.get("completedDate2"));
    		tsiPo2.setString("INSURANCE_INTRO_MAN", map.get("insuranceIntroMan"));
    		tsiPo2.setDate("RECORD_DATE", map.get("recordDate"));
    		tsiPo2.setString("RECORDER", map.get("recorder"));
    		tsiPo2.setInteger("IS_INSURE_REMIND", map.get("isInsureRemind"));
    		tsiPo2.setString("INSURANCE_BUY_DATE", map.get("insuranceBuyDate"));
    		tsiPo2.setInteger("IS_SELF_COMPANY_INSURANCE", map.get("isSelfCompanyInsurance"));
    		tsiPo2.setString("REMARK", map.get("remark"));
    		tsiPo2.saveIt();
    	}
    	
    	
    }
    
    /**
     * 设置税费办理结果PO属性
     * @author Benzc
     * @date 2017年4月15日
     */
    @SuppressWarnings("rawtypes")
	public void getServiceTax(Map map,String vin) {
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
    		List<Object> taxList = new ArrayList<Object>();
    		taxList.add(map.get("itemId"));
    		taxList.add(vin);
    		List<TtServiceTaxPO> list = TtServiceTaxPO.findBySQL("SELECT * FROM TT_SERVICE_TAX WHERE ITEM_ID=? AND VIN=?", taxList.toArray());
        	for(int i = 0;i<list.size();i++){
        		TtServiceTaxPO tstPo = list.get(i);
        		if(map.get("taxSort") ==null){
        			tstPo.setString("TAX_SORT", 0);
        		}else{
        			tstPo.setString("TAX_SORT", map.get("taxSort"));
        		}
        		if(map.get("actualExpending3") == null){
        			tstPo.setDouble("ACTUAL_EXPENDING", 0);
        		}else{
        			tstPo.setDouble("ACTUAL_EXPENDING", map.get("actualExpending3"));
        		}
        		tstPo.setString("COMPLETED_BY", map.get("completedBy3"));
        		if(map.get("completedDate3") != null){
        			tstPo.setString("COMPLETED_DATE", map.get("completedDate3"));
        		}
        		tstPo.setString("RECORDER", map.get("recorder3"));
        		tstPo.setString("REMARK", map.get("remark3"));
        		tstPo.saveIt();
        	}
    	}else{
    		TtServiceTaxPO tstPo2 = new TtServiceTaxPO();
    		Long id = commonNoService.getId("ID");
	        tstPo2.setLong("ITEM_ID", id);
    		tstPo2.setString("VIN", vin);
    		if(map.get("taxSort") ==null){
    			tstPo2.setString("TAX_SORT", 0);
    		}else{
    			tstPo2.setString("TAX_SORT", map.get("taxSort"));
    		}
    		if(map.get("actualExpending3") == null){
    			tstPo2.setDouble("ACTUAL_EXPENDING", 0);
    		}else{
    			tstPo2.setDouble("ACTUAL_EXPENDING", map.get("actualExpending3"));
    		}
    		tstPo2.setString("COMPLETED_BY", map.get("completedBy3"));
    		if(map.get("completedDate3") != null){
    			tstPo2.setString("COMPLETED_DATE", map.get("completedDate3"));
    		}
    		tstPo2.setString("RECORDER", map.get("recorder3"));
    		tstPo2.setDate("RECORD_DATE", new Date());
    		tstPo2.setString("REMARK", map.get("remark3"));
    		tstPo2.saveIt();
    	}

    }
    
    /**
     * 设置牌照办理结果PO属性
     * @author Benzc
     * @date 2017年4月15日
     */
    @SuppressWarnings("rawtypes")
	public void getServiceNumberPlate(Map map,String vin) {
    	if(!StringUtils.isNullOrEmpty(map.get("itemId"))){
    		List<Object> plateList = new ArrayList<Object>();
    		plateList.add(map.get("itemId"));
    		plateList.add(vin);
    		List<TtServiceNumberPlatePO> list = TtServiceNumberPlatePO.findBySQL("SELECT * FROM TT_SERVICE_NUMBER_PLATE WHERE ITEM_ID=? AND VIN=?", plateList.toArray());
        	for(int i = 0;i<list.size();i++){
        		TtServiceNumberPlatePO tsnpPo = list.get(i);
        		tsnpPo.setString("license", map.get("license"));
        		tsnpPo.setString("LICENSE_DATE", map.get("licenseDate"));
        		if(map.get("actualExpending4") == null){
        			tsnpPo.setDouble("ACTUAL_EXPENDING", 0);
        		}else{
        			tsnpPo.setDouble("ACTUAL_EXPENDING", map.get("actualExpending4"));
        		}
        		tsnpPo.setString("COMPLETED_BY", map.get("completedBy4"));
        		if(map.get("completedDate4") != null){
        			tsnpPo.setString("COMPLETED_DATE", map.get("completedDate4"));
        		}
        		tsnpPo.setString("RECORDER", map.get("recorder4"));
        		tsnpPo.setString("REMARK", map.get("remark4"));
        		tsnpPo.saveIt();
        	}
    	}else{
    		TtServiceNumberPlatePO tsnpPo2 = new TtServiceNumberPlatePO();
    		Long id = commonNoService.getId("ID");
    		tsnpPo2.setLong("ITEM_ID", id);
    		tsnpPo2.setString("VIN", vin);
    		tsnpPo2.setString("license", map.get("license"));
    		tsnpPo2.setString("LICENSE_DATE", map.get("licenseDate"));
    		if(map.get("actualExpending4") == null){
    			tsnpPo2.setDouble("ACTUAL_EXPENDING", 0);
    		}else{
    			tsnpPo2.setDouble("ACTUAL_EXPENDING", map.get("actualExpending4"));
    		}
    		tsnpPo2.setString("COMPLETED_BY", map.get("completedBy4"));
    		if(map.get("completedDate4") != null){
    			tsnpPo2.setString("COMPLETED_DATE", map.get("completedDate4"));
    		}
    		tsnpPo2.setString("RECORDER", map.get("recorder4"));
    		tsnpPo2.setDate("RECORD_DATE", new Date());
    		tsnpPo2.setString("REMARK", map.get("remark4"));
    		tsnpPo2.saveIt();
    	}

    }	

}
