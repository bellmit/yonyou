package com.yonyou.dms.customer.service.vehicleLoss;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.LossVehicleRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.PO.customer.OwnerMemoPO;
import com.yonyou.dms.common.domains.PO.customer.VehicleLossRemindPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class VehicleLossRemindServiceImpl implements VehicleLossRemindService{

	@Override
	public PageInfoDto queryVehicleLossRemind(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
			sb.append("select * from ( ");
		}
		sb.append("SELECT  C.CONTACTOR_PROVINCE,C.CONTACTOR_CITY,C.CONTACTOR_DISTRICT,db.dealer_shortname,");
		sb.append("B.DELIVERER_MOBILE,B.DELIVERER_PHONE,B.MILEAGE,D.REMIND_DATE,Coalesce(B.IS_SELF_COMPANY,12781002) IS_SELF_COMPANY,B.FIRST_IN_DATE,D.REMIND_WAY,B.LICENSE,");
		sb.append("C.PHONE,C.MOBILE,C.OWNER_NAME,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_NAME,C.CONTACTOR_ADDRESS,C.CONTACTOR_ZIP_CODE,");
		sb.append("C.PROVINCE,C.CITY,C.DISTRICT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT,D.REMARK,D.REMIND_STATUS,D.REMIND_FAIL_REASON,");
		sb.append("B.VIN,Coalesce(B.IS_VALID,12781002) IS_VALID,B.OWNER_NO,C.BIRTHDAY,B.LAST_MAINTAIN_DATE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,C.OWNER_PROPERTY,B.CONSULTANT,B.SERVICE_ADVISOR,tms.user_name SERVICE_ADVISOR_NAME,");
		sb.append("C.ADDRESS,B.SALES_DATE,B.NEXT_MAINTAIN_MILEAGE,G.EMPLOYEE_NAME AS REMINDER_NAME,D.IS_RETURN_FACTORY,Coalesce(trace.Trace_tag,12781002) AS TRACE_TAG,trace.TRACE_STATUS,");
		sb.append("U.user_name LAST_SERVICE_ADVISOR, B.NEXT_MAINTAIN_DATE, B.DEALER_CODE,CASE WHEN R.VIN IS NULL THEN 12781002 ELSE 12781001 END ON_REPAIR,cd.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_VEHICLE + ") B");
		sb.append("  LEFT JOIN (select VIN,OWNER_NO,DEALER_CODE,RO_NO,SERVICE_ADVISOR from TT_REPAIR_ORDER where (DEALER_CODE,vin,ro_no) in (select DEALER_CODE,vin,max(RO_NO) from TT_REPAIR_ORDER WHERE REPAIR_TYPE_CODE <> ? ");
		queryList.add(CommonConstants.REPAIR_TYPE_SQWX);
		sb.append(" AND D_KEY = 0 group by DEALER_CODE,vin)) aa ON aa.vin=B.vin and aa.DEALER_CODE=B.DEALER_CODE and B.OWNER_NO=aa.OWNER_NO  ");
		sb.append("  LEFT JOIN tm_user U ON U.USER_ID = aa.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN tm_user tms ON tms.USER_ID = B.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sb.append(" left join( SELECT distinct D.vin,e.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_MEMBER_VEHICLE + ") D,");
		sb.append("(" + CommonConstants.VM_MEMBER_CARD + ") E ");
		sb.append(" WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  D.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and E.CARD_STATUS = ? ");
		queryList.add(CommonConstants.DICT_CARD_STATUS_NORMAL);
		sb.append(" ) cd on cd.vin=B.vin left join (select a.* from TT_VEHICLE_LOSS_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_VEHICLE_LOSS_REMIND where DEALER_CODE in ( select SHARE_ENTITY from ");
		sb.append("(" + CommonConstants.VM_ENTITY_SHARE_WITH + ") v where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  v.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and v.BIZ_CODE = 'TT_ALL_REMIND') and LAST_TAG= ? ");
		queryList.add(CommonConstants.DICT_IS_YES);
		sb.append(" and D_KEY= ? ");
		queryList.add(CommonConstants.D_KEY);
		  if(!StringUtils.isNullOrEmpty(queryParam.get("isReturnFactory")))
			{
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" AND IS_RETURN_FACTORY= ? ");
					queryList.add(Integer.parseInt(queryParam.get("isReturnFactory")));
				}
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" AND IS_RETURN_FACTORY is null ");
				}	
			}			
		sb.append(" GROUP BY VIN ) b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE  left join ");
		sb.append("(" + CommonConstants.VM_OWNER + ") C ");
		sb.append(" on  B.DEALER_CODE = C.DEALER_CODE and B.OWNER_NO=C.OWNER_NO 	LEFT JOIN (SELECT VIN,OWNER_NO,DEALER_CODE,COUNT(*) REPAIR_COUNT FROM TT_REPAIR_ORDER ");
		sb.append(" WHERE RO_STATUS = 12551001 AND RO_SPLIT_STATUS <> 13601002 AND D_KEY = 0 GROUP BY VIN,OWNER_NO,DEALER_CODE) R  ON B.DEALER_CODE = R.DEALER_CODE AND B.VIN = R.VIN AND B.OWNER_NO = R.OWNER_NO ");
		sb.append(" LEFT JOIN TT_LOSS_VEHICLE_TRACE TRACE  ON ( C.DEALER_CODE = TRACE.DEALER_CODE AND C.OWNER_NO = TRACE.OWNER_NO AND B.VIN = TRACE.VIN  )  WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND B.DEALER_CODE = ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		Utility.sqlToDate(sb,queryParam.get("startNotInDate"), queryParam.get("endNotInDate"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sb.append(" AND B.LICENSE = ? ");
			queryList.add(queryParam.get("license"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
//			sb.append(" AND B.CONSULTANT like ? ");
//			queryList.add("%"+ queryParam.get("consultant") +"%");
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("address"))) {
			sb.append(" AND C.ADDRESS like ? ");
			queryList.add("%"+ queryParam.get("address") +"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sb.append(" AND B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("cardTypeCode"))) {
			sb.append(" AND cd.CARD_TYPE_CODE = ? ");
			queryList.add(queryParam.get("cardTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
//			sb.append(" AND B.SERVICE_ADVISOR = ? ");
//			queryList.add(queryParam.get("serviceAdvisor"));
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("startNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endNotInMileage"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("endNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessKind"))) {
			sb.append(" and B.BUSINESS_KIND= ? ");
			queryList.add(queryParam.get("businessKind"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehiclePurpose"))) {
			sb.append(" and B.VEHICLE_PURPOSE= ? ");
			queryList.add(queryParam.get("vehiclePurpose"));
		}
		Utility.sqlToDate(sb,queryParam.get("last_maintain_begin_date"), queryParam.get("last_maintain_end_date"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sb.append(" and B.BRAND= ? ");
			queryList.add(queryParam.get("brandId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sb.append(" and B.SERIES= ? ");
			queryList.add(queryParam.get("seriesCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sb.append(" and B.MODEL= ? ");
			queryList.add(queryParam.get("modelCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sb.append(" and D.REMIND_STATUS = ? ");
			queryList.add(queryParam.get("remindStatus"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))&& !(queryParam.get("ownerProperty")).trim().equals("-1")) {
			sb.append("  and c.owner_Property= ? ");
			queryList.add(queryParam.get("ownerProperty"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageBegin"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("mileageBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageEnd"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("mileageEnd"));
		}
		Utility.sqlToDate(sb,queryParam.get("salesDateBegin"), queryParam.get("salesDateEnd"), "B.SALES_DATE", "SALES_DATE");
		Utility.sqlToDate(sb,queryParam.get("remindDateBegin"), queryParam.get("remindDateEnd"), "D.REMIND_DATE", "REMIND_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSelfCompany"))) {
			if(CommonConstants.DICT_IS_YES.equals(queryParam.get("isSelfCompany")))
			{
				sb.append(" AND B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
			}
			else
			{
				sb.append(" AND (B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
				sb.append(" or B.IS_SELF_COMPANY IS NULL  OR B.IS_SELF_COMPANY=0  ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("comeInFactory"))) {
			if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_YES))
			{
				//為空的進場日期
				sb.append(" and B.FIRST_IN_DATE IS NULL ");
			}
			else if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_NO))
			{
				sb.append(" and B.FIRST_IN_DATE IS NOT NULL ");
			}
		}
			if (!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))) {
				if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" and B.IS_VALID = ? "); 
					queryList.add(queryParam.get("IsValid"));
				}
				else if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" and B.IS_VALID = ? ");
					queryList.add(queryParam.get("IsValid"));
				}
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMember"))) {
                    sb.append(" AND EXISTS( SELECT * FROM ");
                    sb.append("("+ CommonConstants.VM_MEMBER_VEHICLE +" ) D,");
                    sb.append("("+ CommonConstants.VM_MEMBER_CARD +" ) E,");
                    sb.append("("+ CommonConstants.VM_MEMBER +" ) F ");
                    sb.append("WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID AND E.DEALER_CODE=F.DEALER_CODE AND E.MEMBER_NO=F.MEMBER_NO ");
                    if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
                        sb.append("AND D.DEALER_CODE = ? ");
                        queryList.add(queryParam.get("dealer_code"));
                    }
                    sb.append(" AND D.VIN=B.VIN AND  E.CARD_STATUS = ");
                    sb.append(CommonConstants.DICT_CARD_STATUS_NORMAL + "  AND F.MEMBER_STATUS = ");
                    sb.append(CommonConstants.DICT_MEMBER_STATUS_NORMAL + ") ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("flag"))) {
                if(queryParam.get("flag").equals(CommonConstants.DICT_IS_YES))
                {
                    sb.append(" and (D.REMIND_DATE IS NULL  ) ");
                }
            }
				if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
					sb.append(" AND B.VIN LIKE ? ");
					queryList.add("%"+ queryParam.get("vin") +"%");
				}
				
			if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
				sb.append(" )aaa where 1=1 and LAST_SERVICE_ADVISOR= ? ");
				queryList.add(queryParam.get("lastServiceadvisor"));
				if (!StringUtils.isNullOrEmpty(queryParam.get("traceTag"))) {
                    sb.append(" AND aaa.trace_tag = ? ");
                    queryList.add(queryParam.get("traceTag"));
                }
			}
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryOwnerNOByid(String id) throws ServiceBizException {
		String sb = new String("SELECT OWNER_NO,OWNER_NAME,OWNER_SPELL,GENDER,CT_CODE, CERTIFICATE_NO,PROVINCE,CITY,DISTRICT,ADDRESS,ZIP_CODE,INDUSTRY_FIRST,INDUSTRY_SECOND,TAX_NO,PRE_PAY,ARREARAGE_AMOUNT,CUS_RECEIVE_SORT FROM tm_owner WHERE owner_no ='"+id+"' " );
		List<Map> result = Base.findAll(sb.toString());
		List<Map> resultList = new ArrayList<Map>();
		if(result != null){
		Map map = result.get(0);
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,B.MEMO_INFO FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO WHERE A.DEALER_CODE = ? " );
		queryList.add(dealerCode);
		
		if (!StringUtils.isNullOrEmpty(map.get("OWNER_NO"))) {
			sql.append("  AND  A.OWNER_NO =  ? ");
			queryList.add( map.get("OWNER_NO"));
		}
		resultList = DAOUtil.findAll(sql.toString(), queryList);
		}
		return resultList;
	}
	
	@Override
	public void modifyOwnerNOByid(String id, OwnerDTO ownerDto) throws ServiceBizException {
		System.out.println(FrameworkUtil.getLoginInfo().getDealerCode());
		CarownerPO ownerPO = CarownerPO.findByCompositeKeys(id,FrameworkUtil.getLoginInfo().getDealerCode());
		assignmentWtpo(ownerPO,ownerDto);
		ownerPO.saveIt();
		List<OwnerMemoPO> findBySQL = OwnerMemoPO.findBySQL("SELECT * FROM TM_OWNER_MEMO WHERE OWNER_NO = ? ", id);
		if(findBySQL.size()>0){
			OwnerMemoPO ownerMemoPO = findBySQL.get(0);
			ownerMemoPO.setString("MEMO_INFO", ownerDto.getOwnerMemo());
			ownerMemoPO.saveIt();
		}else{
			OwnerMemoPO ownerMemoPO = new OwnerMemoPO();
			ownerMemoPO.setString("MEMO_INFO", ownerDto.getOwnerMemo());
			ownerMemoPO.setString("OWNER_NO", ownerDto.getOwnerNo());
			ownerMemoPO.saveIt();
		}
	}
	/**
     * WorkerTypePo对象赋值
    * @author sqh
    * @date 2017年4月5日
     */
    private void assignmentWtpo(CarownerPO ownerPO,OwnerDTO ownerDto)throws ServiceBizException{
    	ownerPO.setInteger("OWNER_PROPERTY", ownerDto.getOwnerProperty());
    	ownerPO.setString("OWNER_NAME",  ownerDto.getOwnerName());
    	ownerPO.setString("OWNER_SPELL", ownerDto.getOwnerSpell());
    	ownerPO.setInteger("GENDER", ownerDto.getGender());
    	ownerPO.setInteger("CT_CODE", ownerDto.getCtCode());
    	ownerPO.setString("CERTIFICATE_NO", ownerDto.getCertificateNo());
    	ownerPO.setInteger("PROVINCE", ownerDto.getProvince());
    	ownerPO.setInteger("city", ownerDto.getCity());
    	ownerPO.setInteger("DISTRICT", ownerDto.getDistrict());
    	ownerPO.setString("ADDRESS", ownerDto.getAddress());
    	ownerPO.setString("ZIP_CODE", ownerDto.getZipCode());
    	ownerPO.setInteger("INDUSTRY_FIRST",ownerDto.getIndustryFirst());
    	ownerPO.setInteger("INDUSTRY_SECOND", ownerDto.getIndustrySecond());
    	ownerPO.setString("TAX_NO", ownerDto.getTaxNo());
    	ownerPO.setDouble("PRE_PAY", ownerDto.getPrePay());
    	ownerPO.setDouble("ARREARAGE_AMOUNT", ownerDto.getArrearageAmount());
    	ownerPO.setDouble("CUS_RECEIVE_SORT", ownerDto.getCusReceiveSort());
    	ownerPO.setString("CERTIFICATE_NO", ownerDto.getCertificateNo());
    	ownerPO.setString("SECOND_ADDRESS", ownerDto.getSecondAddress());
    	ownerPO.setString("PHONE", ownerDto.getPhone());
    	ownerPO.setString("E_MAIL", ownerDto.getEMail());
    	ownerPO.setDate("BIRTHDAY", ownerDto.getBirthday());
    	ownerPO.setString("MOBILE", ownerDto.getMobile());
    	ownerPO.setDouble("FAMILY_INCOME", ownerDto.getFamilyIncome());
    	ownerPO.setDouble("OWNER_MARRIAGE", ownerDto.getOwnerMarriage());
    	ownerPO.setDouble("EDU_LEVEL", ownerDto.getEduLevel());
    	ownerPO.setString("HOBBY", ownerDto.getHobby());
    	ownerPO.setString("CONTACTOR_NAME", ownerDto.getContactorName());
    	ownerPO.setInteger("CONTACTOR_GENDER", ownerDto.getContactorGender());
    	ownerPO.setString("CONTACTOR_PHONE", ownerDto.getContactorPhone());
    	ownerPO.setString("CONTACTOR_EMAIL", ownerDto.getContactorEmail());
    	ownerPO.setString("CONTACTOR_FAX", ownerDto.getContactorFax());
    	ownerPO.setString("CONTACTOR_MOBILE", ownerDto.getContactorMobile());
    	ownerPO.setInteger("CONTACTOR_PROVINCE", ownerDto.getContactorProvince());
    	ownerPO.setString("CONTACTOR_CITY", ownerDto.getContactorCity());
    	ownerPO.setString("CONTACTOR_DISTRICT", ownerDto.getContactorDistrict());
    	ownerPO.setString("CONTACTOR_ADDRESS", ownerDto.getContactorAddress());
    	ownerPO.setString("CONTACTOR_ZIP_CODE", ownerDto.getContactorZipCode());
    	ownerPO.setInteger("CONTACTOR_HOBBY_CONTACT", ownerDto.getContactorHobbyContact());
    	ownerPO.setInteger("CONTACTOR_VOCATION_TYPE", ownerDto.getContactorVocationType());
    	ownerPO.setInteger("CONTACTOR_POSITION", ownerDto.getContactorPosition());
    	ownerPO.setString("REMARK", ownerDto.getRemark());
    }
    
    @SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryVehicelByVin(String vin) throws ServiceBizException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select max(c.REMIND_DATE) AS REMIND_DATE,a.dealer_code,a.OWNER_NO,a.OWNER_NAME,DATE(b.LAST_MAINTAIN_DATE) as LAST_MAINTAIN_DATE,b.vin,c.REMINDER from tm_owner a ");
		sql.append(" LEFT JOIN tm_vehicle b ON a.dealer_code = b.dealer_code and a.owner_no = b.owner_no ");
		sql.append(" LEFT JOIN tt_vehicle_loss_remind c on a.dealer_code = c.dealer_code and b.vin = c.vin where b.vin = ? ");
		queryList.add(vin);
		sql.append("and a.dealer_code = ? ");
		sql.append("GROUP BY c.REMIND_DATE desc limit 0,1");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		return list;
	}
	@Override
	public void addRemind(LossVehicleRemindDTO lossVehicleRemindDTO) throws ServiceBizException {
//		List<VehicleLossRemindPO> findBySQL = VehicleLossRemindPO.findBySQL("select * from tt_vehicle_loss_remind where VIN = ? ",lossVehicleRemindDTO.getVin());
//		if(findBySQL.size()>0){
//			VehicleLossRemindPO vehicleLossRemindPO = findBySQL.get(0);
//			vehicleLossRemindPO.setString("REMIND_CONTENT", lossVehicleRemindDTO.getRemindContent());
//			vehicleLossRemindPO.setString("CUSTOMER_FEEDBACK", lossVehicleRemindDTO.getCustomerFeedback());
//			vehicleLossRemindPO.setInteger("REMIND_WAY", lossVehicleRemindDTO.getRemindWay());
//			vehicleLossRemindPO.setString("REMINDER", lossVehicleRemindDTO.getReminder());
//			vehicleLossRemindPO.setInteger("REMIND_STATUS", lossVehicleRemindDTO.getRemindStatus());
//			vehicleLossRemindPO.setString("REMIND_FAIL_REASON", lossVehicleRemindDTO.getRemindFailReason());
//			vehicleLossRemindPO.setString("REMARK", lossVehicleRemindDTO.getRemark());
//			vehicleLossRemindPO.set("REMIND_DATE",new Timestamp(System.currentTimeMillis()));
//			vehicleLossRemindPO.saveIt();
//		}else{
			VehicleLossRemindPO vehicleLossRemindPO = new VehicleLossRemindPO();
			vehicleLossRemindPO.setString("VIN", lossVehicleRemindDTO.getVin());
			vehicleLossRemindPO.setString("REMIND_CONTENT", lossVehicleRemindDTO.getRemindContent());
			vehicleLossRemindPO.setString("CUSTOMER_FEEDBACK", lossVehicleRemindDTO.getCustomerFeedback());
			vehicleLossRemindPO.setInteger("REMIND_WAY", lossVehicleRemindDTO.getRemindWay());
			vehicleLossRemindPO.setString("REMINDER", lossVehicleRemindDTO.getReminder());
			vehicleLossRemindPO.setInteger("REMIND_STATUS", lossVehicleRemindDTO.getRemindStatus());
			vehicleLossRemindPO.setString("REMIND_FAIL_REASON", lossVehicleRemindDTO.getRemindFailReason());
			vehicleLossRemindPO.setString("REMARK", lossVehicleRemindDTO.getRemark());
			vehicleLossRemindPO.set("REMIND_DATE",new Timestamp(System.currentTimeMillis()));
			vehicleLossRemindPO.setString("LAST_TAG",CommonConstants.DICT_IS_YES);
			vehicleLossRemindPO.setString("OWNER_NO",lossVehicleRemindDTO.getOwnerNo());
			vehicleLossRemindPO.saveIt();
//		}
		
	}
	@Override
	public PageInfoDto queryRemindre(String vin, String ownerNo) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sb.append(" SELECT A.*,db.DEALER_SHORTNAME,V.NEW_VEHICLE_DATE,V.NEW_VEHICLE_MILEAGE,V.LICENSE,F.OWNER_NAME ,CASE WHEN E.DEALER_CODE != ? ");
		queryList.add(dealerCode);
		sb.append(" THEN '' ELSE E.EMPLOYEE_NAME END EMPLOYEE_NAME FROM ( ");
		sb.append(" SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_VERYCAR_ATTERM +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_VEHICLE_REMIND A  WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND') AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_VERYCAR_ATTERM +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE,A.RENEWAL_REMIND_STATUS,A.RENEWAL_FAILED_REASON,A.RENEWAL_REMARK,A.RENEWAL_FAILED_DATE as RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_INSURANCE_EXPIRE_REMIND A  WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_NEW_CAR +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_NEWVEHICLE_REMIND A  WHERE A.DEALER_CODE  in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE =? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_OWNER_BIRTHDAY +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_OWNER_BIRTHDAY_REMIND A  WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_CUSTOMER_LOSE +") ");
		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE,REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
		sb.append(" FROM TT_VEHICLE_LOSS_REMIND A WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
		queryList.add(dealerCode);
		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
		if (!StringUtils.isNullOrEmpty(vin)) {
			sb.append(" AND A.VIN = ? ");
			queryList.add(vin);
		}
		if (!StringUtils.isNullOrEmpty(ownerNo)) {
			sb.append(" AND A.OWNER_NO = ? ");
			queryList.add(ownerNo);
		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_TERMLY_MAINTAIN +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_TERMLY_MAINTAIN_REMIND A WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_GUAR_ATTERM +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_REPAIR_EXPIRE_REMIND A WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
//		sb.append(" UNION SELECT ");
//		sb.append(" ("+ DictCodeConstants.DICT_REMINDER_TYPE_YEAR_CHECK +") ");
//		sb.append(" AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER ");
//		sb.append(",'' as LAST_NEXT_MAINTAIN_DATE,0 as LAST_NEXT_MAINTAIN_MILEAGE ,A.DEALER_CODE ,0 AS RENEWAL_REMIND_STATUS,0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK,'' AS RENEWAL_FAILED_DATE ");
//		sb.append(" FROM TT_CHECK_EXPIRE_REMIND A WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") b where b.DEALER_CODE = ? ");
//		queryList.add(dealerCode);
//		sb.append(" and biz_code ='TT_ALL_REMIND')  AND A.D_KEY = ");
//		sb.append(" ("+ DictCodeConstants.D_KEY +") ");
//		if (!StringUtils.isNullOrEmpty(vin)) {
//			sb.append(" AND A.VIN = ? ");
//			queryList.add(vin);
//		}
//		if (!StringUtils.isNullOrEmpty(ownerNo)) {
//			sb.append(" AND A.OWNER_NO = ? ");
//			queryList.add(ownerNo);
//		}
		sb.append(") A LEFT JOIN TM_DEALER_BASICINFO db ON db.dealer_code = A.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_VEHICLE +") V ON V.VIN = A.VIN AND V.DEALER_CODE = ? ");
		queryList.add(dealerCode);
		sb.append(" LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON F.DEALER_CODE = ? ");
		queryList.add(dealerCode);
		
		sb.append(" LEFT JOIN TM_EMPLOYEE E ON E.DEALER_CODE = A.DEALER_CODE and  E.EMPLOYEE_NO=A.REMINDER GROUP BY A.REMIND_ID");
		System.out.print(sb);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryRemindID(String remindId) throws ServiceBizException {
		VehicleLossRemindPO vehicleLossRemindPO = VehicleLossRemindPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),remindId);
		List<Map> list = new ArrayList<Map>();
		list.add(vehicleLossRemindPO.toMap());
		return list;
	}
	
	@Override
	public void modifyRemindID(String remindId,LossVehicleRemindDTO lossVehicleRemindDTO) throws ServiceBizException {
		VehicleLossRemindPO vehicleLossRemindPO = VehicleLossRemindPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),remindId);
		vehicleLossRemindPO.setString("CUSTOMER_FEEDBACK",lossVehicleRemindDTO.getCustomerFeedback());
		vehicleLossRemindPO.setString("REMARK", lossVehicleRemindDTO.getRemark());
		vehicleLossRemindPO.saveIt();
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT aa.*,C.OWNER_NAME,C.PHONE,C.MOBILE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME from (SELECT '定期保养提醒' description,cast(D.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" D.DEALER_CODE,D.OWNER_NO,D.REMIND_DATE,D.REMIND_CONTENT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_STATUS,D.VIN ");
		stringBuffer.append(" FROM  TT_VEHICLE_LOSS_REMIND D where D.D_KEY = 0 ");
			stringBuffer.append(" AND D.DEALER_CODE  = ?  ");
			queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (D.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or D.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" ) aa left join ("+ CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE and B.VIN = aa.VIN ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" left join ("+ CommonConstants.VM_OWNER +") C");
		stringBuffer.append(" on aa.DEALER_CODE = c.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO ");
		stringBuffer.append(" union all SELECT '客户生日提醒' description,cast(B.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" A.DEALER_CODE,B.OWNER_NO,B.REMIND_DATE,B.REMIND_CONTENT,B.REMINDER,B.CUSTOMER_FEEDBACK,B.REMIND_STATUS,B.VIN,A.CUSTOMER_NAME OWNER_NAME,A.CONTACTOR_PHONE PHONE, ");
		stringBuffer.append(" A.CONTACTOR_MOBILE MOBILE,C.BRAND,tb.brand_name,C.SERIES,ts.series_name,C.MODEL,tm.MODEL_NAME FROM TM_POTENTIAL_CUSTOMER A LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE ");
		
		stringBuffer.append(" AND A.CUSTOMER_NO = B.OWNER_NO left join ("+ CommonConstants.VM_VEHICLE + ") C on B.DEALER_CODE = a.DEALER_CODE and C.VIN = B.VIN  ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON C.DEALER_CODE=tb.DEALER_CODE AND C.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON C.DEALER_CODE = ts.DEALER_CODE  AND C.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON C.DEALER_CODE = tm.DEALER_CODE  AND C.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" WHERE A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or B.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		stringBuffer.append(" AND A.D_KEY=0 AND A.CUSTOMER_TYPE=10181001 ");
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		
		
		stringBuffer.append(" union all SELECT '销售回访结果' description,cast(A.TRACE_TASK_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.CUSTOMER_NO OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT,");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.CUSTOMER_NAME OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE, ");
		stringBuffer.append(" A.BRAND,tb.brand_name,A.SERIES,ts.series_name,A.MODEL,tm.MODEL_NAME FROM  TT_SALES_TRACE_TASK A LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
		stringBuffer.append(" Left join (select distinct Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_SALES_TRACE_TASK_QUESTION Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE) ");
		stringBuffer.append(" QU on QU.TRACE_TASK_ID=A.TRACE_TASK_ID WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.CUSTOMER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		stringBuffer.append(" union all SELECT '回访结果' description,A.RO_NO REMIND_ID,A.DEALER_CODE,H.OWNER_NO,A.INPUT_DATE REMIND_DATE,A.REMARK REMIND_CONTENT,A.TRANCER REMINDER, ");
		stringBuffer.append(" '' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.OWNER_NAME,A.DELIVERER_MOBILE PHONE,A.DELIVERER_PHONE MOBILE,A.BRAND,tb.brand_name,A.SERIES,ts.series_name,A.MODEL,tm.MODEL_NAME FROM  TT_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE AND TECH.RO_NO=E.RO_NO AND TECH.D_KEY=0 ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO ");
		stringBuffer.append(" AND A.DEALER_CODE=F.DEALER_CODE WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (H.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
		stringBuffer.append(" union all SELECT '流失报警回访结果' description,cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT, ");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,F.OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,H.BRAND,tb.brand_name,H.SERIES,ts.series_name,H.MODEL,tm.MODEL_NAME FROM  TT_LOSS_VEHICLE_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN  AND A.DEALER_CODE = H.DEALER_CODE ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON H.DEALER_CODE=tb.DEALER_CODE AND H.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON H.DEALER_CODE = ts.DEALER_CODE  AND H.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON H.DEALER_CODE = tm.DEALER_CODE  AND H.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE Left join(select distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
		stringBuffer.append(" from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_LOSS_VHCL_TRCE_TASK_QN Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE ) QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID ");
		stringBuffer.append(" WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);

		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		
		return resultList;
	}
	//维修建议项目
	@Override
	public PageInfoDto QueryRepairSuggest(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append("SELECT C.ADDRESS,B.LICENSE , A.RO_NO,A.IS_VALID,db.dealer_shortname,A.SUGGEST_MAINTAIN_LABOUR_ID,A.LABOUR_CODE,A.LABOUR_NAME,A.STD_LABOUR_HOUR,A.LABOUR_AMOUNT,A.REASON,A.SUGGEST_DATE,B.SERVICE_ADVISOR,A.VIN,B.ENGINE_NO,A.DEALER_CODE,EM.EMPLOYEE_NAME as SERVICE_ADVISOR_NAME  FROM TT_SUGGEST_MAINTAIN_LABOUR A ");
		stringBuffer.append("LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO  = B.RO_NO  AND B.D_KEY = ");
		stringBuffer.append(" ("+ DictCodeConstants.D_KEY +") ");
		stringBuffer.append("  LEFT JOIN ("+ CommonConstants.VM_OWNER +") C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO  ");
		stringBuffer.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
		stringBuffer.append(" LEFT JOIN TM_EMPLOYEE EM ON EM.DEALER_CODE = B.DEALER_CODE  AND EM.EMPLOYEE_NO=B.SERVICE_ADVISOR  WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") h where h.DEALER_CODE = ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		stringBuffer.append(" and h.biz_code ='TT_SUGGEST_MAINTAIN' )  AND A.D_KEY =  ("+ DictCodeConstants.D_KEY +") ");
		stringBuffer.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("LICENSE"), "AND"));
		stringBuffer.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("ADDRESS"), "AND"));
		stringBuffer.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("ENGINE_NO"), "AND"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("SERVICE_ADVISOR"))) {
			stringBuffer.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("SERVICE_ADVISOR"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("OWNER_NO"))) {
			stringBuffer.append(" AND B.OWNER_NO = ? ");
			queryList.add(queryParam.get("OWNER_NO"));
		}
		stringBuffer.append(Utility.getLikeCond("A", "VIN", queryParam.get("VIN"), "AND"));
		Utility.sqlToDate(stringBuffer,queryParam.get("SUGGEST_BEGIN_DATE"), queryParam.get("SUGGEST_END_DATE"), "SUGGEST_DATE", "A");
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
		return pageInfoDto;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryVehicleLossRemindByVin(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
			sb.append("select * from ( ");
		}
		sb.append("SELECT  C.CONTACTOR_PROVINCE,C.CONTACTOR_CITY,C.CONTACTOR_DISTRICT,db.dealer_shortname,");
		sb.append("B.DELIVERER_MOBILE,B.DELIVERER_PHONE,B.MILEAGE,D.REMIND_DATE,Coalesce(B.IS_SELF_COMPANY,12781002) IS_SELF_COMPANY,B.FIRST_IN_DATE,D.REMIND_WAY,B.LICENSE,");
		sb.append("C.PHONE,C.MOBILE,C.OWNER_NAME,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_NAME,C.CONTACTOR_ADDRESS,C.CONTACTOR_ZIP_CODE,");
		sb.append("C.PROVINCE,C.CITY,C.DISTRICT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT,D.REMARK,D.REMIND_STATUS,D.REMIND_FAIL_REASON,");
		sb.append("B.VIN,Coalesce(B.IS_VALID,12781002) IS_VALID,B.OWNER_NO,C.BIRTHDAY,B.LAST_MAINTAIN_DATE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,C.OWNER_PROPERTY,B.CONSULTANT,B.SERVICE_ADVISOR,tms.user_name SERVICE_ADVISOR_NAME,");
		sb.append("C.ADDRESS,B.SALES_DATE,B.NEXT_MAINTAIN_MILEAGE,G.EMPLOYEE_NAME AS REMINDER_NAME,D.IS_RETURN_FACTORY,Coalesce(trace.Trace_tag,12781002) AS TRACE_TAG,trace.TRACE_STATUS,");
		sb.append("U.user_name LAST_SERVICE_ADVISOR, B.NEXT_MAINTAIN_DATE, B.DEALER_CODE,CASE WHEN R.VIN IS NULL THEN 12781002 ELSE 12781001 END ON_REPAIR,cd.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_VEHICLE + ") B");
		sb.append("  LEFT JOIN (select VIN,OWNER_NO,DEALER_CODE,RO_NO,SERVICE_ADVISOR from TT_REPAIR_ORDER where (DEALER_CODE,vin,ro_no) in (select DEALER_CODE,vin,max(RO_NO) from TT_REPAIR_ORDER WHERE REPAIR_TYPE_CODE <> ? ");
		queryList.add(CommonConstants.REPAIR_TYPE_SQWX);
		sb.append(" AND D_KEY = 0 group by DEALER_CODE,vin)) aa ON aa.vin=B.vin and aa.DEALER_CODE=B.DEALER_CODE and B.OWNER_NO=aa.OWNER_NO  ");
		sb.append("  LEFT JOIN tm_user U ON U.USER_ID = aa.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN tm_user tms ON tms.USER_ID = B.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sb.append(" left join( SELECT distinct D.vin,e.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_MEMBER_VEHICLE + ") D,");
		sb.append("(" + CommonConstants.VM_MEMBER_CARD + ") E ");
		sb.append(" WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  D.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and E.CARD_STATUS = ? ");
		queryList.add(CommonConstants.DICT_CARD_STATUS_NORMAL);
		sb.append(" ) cd on cd.vin=B.vin left join (select a.* from TT_VEHICLE_LOSS_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_VEHICLE_LOSS_REMIND where DEALER_CODE in ( select SHARE_ENTITY from ");
		sb.append("(" + CommonConstants.VM_ENTITY_SHARE_WITH + ") v where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  v.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and v.BIZ_CODE = 'TT_ALL_REMIND') and LAST_TAG= ? ");
		queryList.add(CommonConstants.DICT_IS_YES);
		sb.append(" and D_KEY= ? ");
		queryList.add(CommonConstants.D_KEY);
		  if(!StringUtils.isNullOrEmpty(queryParam.get("isReturnFactory")))
			{
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" AND IS_RETURN_FACTORY= ? ");
					queryList.add(Integer.parseInt(queryParam.get("isReturnFactory")));
				}
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" AND IS_RETURN_FACTORY is null ");
				}	
			}			
		sb.append(" GROUP BY VIN ) b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE  left join ");
		sb.append("(" + CommonConstants.VM_OWNER + ") C ");
		sb.append(" on  B.DEALER_CODE = C.DEALER_CODE and B.OWNER_NO=C.OWNER_NO 	LEFT JOIN (SELECT VIN,OWNER_NO,DEALER_CODE,COUNT(*) REPAIR_COUNT FROM TT_REPAIR_ORDER ");
		sb.append(" WHERE RO_STATUS = 12551001 AND RO_SPLIT_STATUS <> 13601002 AND D_KEY = 0 GROUP BY VIN,OWNER_NO,DEALER_CODE) R  ON B.DEALER_CODE = R.DEALER_CODE AND B.VIN = R.VIN AND B.OWNER_NO = R.OWNER_NO ");
		sb.append(" LEFT JOIN TT_LOSS_VEHICLE_TRACE TRACE  ON ( C.DEALER_CODE = TRACE.DEALER_CODE AND C.OWNER_NO = TRACE.OWNER_NO AND B.VIN = TRACE.VIN  )  WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND B.DEALER_CODE = ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		Utility.sqlToDate(sb,queryParam.get("startNotInDate"), queryParam.get("endNotInDate"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sb.append(" AND B.LICENSE = ? ");
			queryList.add(queryParam.get("license"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
//			sb.append(" AND B.CONSULTANT like ? ");
//			queryList.add("%"+ queryParam.get("consultant") +"%");
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("address"))) {
			sb.append(" AND C.ADDRESS like ? ");
			queryList.add("%"+ queryParam.get("address") +"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sb.append(" AND B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("cardTypeCode"))) {
			sb.append(" AND cd.CARD_TYPE_CODE = ? ");
			queryList.add(queryParam.get("cardTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
//			sb.append(" AND B.SERVICE_ADVISOR = ? ");
//			queryList.add(queryParam.get("serviceAdvisor"));
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("startNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endNotInMileage"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("endNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessKind"))) {
			sb.append(" and B.BUSINESS_KIND= ? ");
			queryList.add(queryParam.get("businessKind"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehiclePurpose"))) {
			sb.append(" and B.VEHICLE_PURPOSE= ? ");
			queryList.add(queryParam.get("vehiclePurpose"));
		}
		Utility.sqlToDate(sb,queryParam.get("last_maintain_begin_date"), queryParam.get("last_maintain_end_date"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sb.append(" and B.BRAND= ? ");
			queryList.add(queryParam.get("brandId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sb.append(" and B.SERIES= ? ");
			queryList.add(queryParam.get("seriesCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sb.append(" and B.MODEL= ? ");
			queryList.add(queryParam.get("modelCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sb.append(" and D.REMIND_STATUS = ? ");
			queryList.add(queryParam.get("remindStatus"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))&& !(queryParam.get("ownerProperty")).trim().equals("-1")) {
			sb.append("  and c.owner_Property= ? ");
			queryList.add(queryParam.get("ownerProperty"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageBegin"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("mileageBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageEnd"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("mileageEnd"));
		}
		Utility.sqlToDate(sb,queryParam.get("salesDateBegin"), queryParam.get("salesDateEnd"), "B.SALES_DATE", "SALES_DATE");
		Utility.sqlToDate(sb,queryParam.get("remindDateBegin"), queryParam.get("remindDateEnd"), "D.REMIND_DATE", "REMIND_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSelfCompany"))) {
			if(CommonConstants.DICT_IS_YES.equals(queryParam.get("isSelfCompany")))
			{
				sb.append(" AND B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
			}
			else
			{
				sb.append(" AND (B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
				sb.append(" or B.IS_SELF_COMPANY IS NULL  OR B.IS_SELF_COMPANY=0  ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("comeInFactory"))) {
			if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_YES))
			{
				//為空的進場日期
				sb.append(" and B.FIRST_IN_DATE IS NULL ");
			}
			else if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_NO))
			{
				sb.append(" and B.FIRST_IN_DATE IS NOT NULL ");
			}
		}
			if (!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))) {
				if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" and B.IS_VALID = ? "); 
					queryList.add(queryParam.get("IsValid"));
				}
				else if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" and B.IS_VALID = ? ");
					queryList.add(queryParam.get("IsValid"));
				}
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMember"))) {
                    sb.append(" AND EXISTS( SELECT * FROM ");
                    sb.append("("+ CommonConstants.VM_MEMBER_VEHICLE +" ) D,");
                    sb.append("("+ CommonConstants.VM_MEMBER_CARD +" ) E,");
                    sb.append("("+ CommonConstants.VM_MEMBER +" ) F ");
                    sb.append("WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID AND E.DEALER_CODE=F.DEALER_CODE AND E.MEMBER_NO=F.MEMBER_NO ");
                    if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
                        sb.append("AND D.DEALER_CODE = ? ");
                        queryList.add(queryParam.get("dealer_code"));
                    }
                    sb.append(" AND D.VIN=B.VIN AND  E.CARD_STATUS = ");
                    sb.append(CommonConstants.DICT_CARD_STATUS_NORMAL + "  AND F.MEMBER_STATUS = ");
                    sb.append(CommonConstants.DICT_MEMBER_STATUS_NORMAL + ") ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("flag"))) {
                if(queryParam.get("flag").equals(CommonConstants.DICT_IS_YES))
                {
                    sb.append(" and (D.REMIND_DATE IS NULL  ) ");
                }
            }
				if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
					sb.append(" AND B.VIN LIKE ? ");
					queryList.add("%"+ queryParam.get("vin") +"%");
				}
				
			if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
				sb.append(" )aaa where 1=1 and LAST_SERVICE_ADVISOR= ? ");
				queryList.add(queryParam.get("lastServiceadvisor"));
				if (!StringUtils.isNullOrEmpty(queryParam.get("traceTag"))) {
                    sb.append(" AND aaa.trace_tag = ? ");
                    queryList.add(queryParam.get("traceTag"));
                }
			}
		List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
		return resultList;
	}
	//维修建议配件
	@Override
	public PageInfoDto QueryRepair(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append("SELECT C.ADDRESS,B.LICENSE ,A.RO_NO,A.IS_VALID,db.dealer_shortname,A.SUGGEST_MAINTAIN_PART_ID,A.PART_NO,A.PART_NAME,A.SUGGEST_DATE,A.SALES_PRICE,A.QUANTITY,A.REASON,B.SERVICE_ADVISOR,A.VIN,B.ENGINE_NO ,A.DEALER_CODE ,EM.EMPLOYEE_NAME as SERVICE_ADVISOR_NAME  FROM TT_SUGGEST_MAINTAIN_PART A LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO = B.RO_NO AND B.D_KEY = ");
		stringBuffer.append(" ("+ DictCodeConstants.D_KEY +") ");
		stringBuffer.append("  LEFT JOIN ("+ CommonConstants.VM_OWNER +") C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO  ");
		stringBuffer.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
		stringBuffer.append(" LEFT JOIN TM_EMPLOYEE EM ON EM.DEALER_CODE = B.DEALER_CODE  AND EM.EMPLOYEE_NO=B.SERVICE_ADVISOR  WHERE A.DEALER_CODE in ( select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") h where h.DEALER_CODE = ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		stringBuffer.append(" and h.biz_code ='TT_SUGGEST_MAINTAIN' )  AND A.D_KEY =  ("+ DictCodeConstants.D_KEY +") ");
		stringBuffer.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("LICENSE"), "AND"));
		stringBuffer.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("ADDRESS"), "AND"));
		stringBuffer.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("ENGINE_NO"), "AND"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("SERVICE_ADVISOR"))) {
			stringBuffer.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("SERVICE_ADVISOR"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("OWNER_NO"))) {
			stringBuffer.append(" AND B.OWNER_NO = ? ");
			queryList.add(queryParam.get("OWNER_NO"));
		}
		stringBuffer.append(Utility.getLikeCond("A", "VIN", queryParam.get("VIN"), "AND"));
		Utility.sqlToDate(stringBuffer,queryParam.get("SUGGEST_BEGIN_DATE"), queryParam.get("SUGGEST_END_DATE"), "SUGGEST_DATE", "A");
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
		return pageInfoDto;
	}
	
	//维修投诉历史
	@Override
	public PageInfoDto QueryComplaint(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT db.DEALER_SHORTNAME,b.TECHNICIAN AS TECHNICIAN_NEW,A.VIN,A.COMPLAINT_NO,A.COMPLAINT_NAME,A.COMPLAINT_PHONE,A.COMPLAINT_TYPE,A.COMPLAINT_DATE,A.SERVICE_ADVISOR, ");
		stringBuffer.append(" A.COMPLAINT_MOBILE,A.COMPLAINT_GENDER,A.COMPLAINT_MAIN_TYPE,A.COMPLAINT_SUB_TYPE,A.CRC_COMPLAINT_NO,A.IS_GCR,A.CRC_COMPLAINT_SOURCE,A.TECHNICIAN, ");
		stringBuffer.append(" A.DEAL_STATUS,  A.COMPLAINT_END_DATE,A.DEPARTMENT,A.BE_COMPLAINT_EMP,A.CLOSE_DATE,A.COMPLAINT_RESULT,A.COMPLAINT_ORIGIN,A.IS_INTIME_DEAL,A.COMPLAINT_SUMMARY, ");
		stringBuffer.append(" A.COMPLAINT_REASON,A.CONSULTANT,A.OWNER_NAME,A.LICENSE,A.RESOLVENT,A.CLOSE_STATUS,IS_UPLOAD,A.DEALER_CODE FROM TT_CUSTOMER_COMPLAINT a left join TT_TECHNICIAN_I b on a.DEALER_CODE=b.DEALER_CODE and a.ro_no=b.ro_no  ");
		stringBuffer.append("  LEFT JOIN TM_DEALER_BASICINFO db ON A.dealer_code=db.dealer_code  ");
		stringBuffer.append(" WHERE a.D_KEY = ("+ DictCodeConstants.D_KEY +") AND a.IS_VALID= ("+ DictCodeConstants.DICT_IS_YES +") AND A.DEALER_CODE in( ");
		stringBuffer.append(" select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") h where h.DEALER_CODE = ?  ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		stringBuffer.append(" and h.biz_code = 'TT_CUSTOMER_COMPLAINT' ) ");
//		stringBuffer.append(Utility.getLikeCond("A", "OWNER_NAME", queryParam.get("OWNER_NAME"), "AND"));
		stringBuffer.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("LICENSE"), "AND"));
		if (!StringUtils.isNullOrEmpty(queryParam.get("VIN"))) {
			stringBuffer.append(" AND A.VIN = ? ");
			queryList.add(queryParam.get("VIN"));
		}
		
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
		return pageInfoDto;
	}
	
	@Override
	@SuppressWarnings({ "rawtypes"})
	public List<Map> exportVehicleLossRemind(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
			sb.append("select * from ( ");
		}
		sb.append("SELECT  C.CONTACTOR_PROVINCE,C.CONTACTOR_CITY,C.CONTACTOR_DISTRICT,db.dealer_shortname,");
		sb.append("B.DELIVERER_MOBILE,B.DELIVERER_PHONE,B.MILEAGE,D.REMIND_DATE,Coalesce(B.IS_SELF_COMPANY,12781002) IS_SELF_COMPANY,B.FIRST_IN_DATE,D.REMIND_WAY,B.LICENSE,");
		sb.append("C.PHONE,C.MOBILE,C.OWNER_NAME,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_NAME,C.CONTACTOR_ADDRESS,C.CONTACTOR_ZIP_CODE,");
		sb.append("C.PROVINCE,C.CITY,C.DISTRICT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT,D.REMARK,D.REMIND_STATUS,D.REMIND_FAIL_REASON,");
		sb.append("B.VIN,Coalesce(B.IS_VALID,12781002) IS_VALID,B.OWNER_NO,C.BIRTHDAY,B.LAST_MAINTAIN_DATE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,C.OWNER_PROPERTY,B.CONSULTANT,B.SERVICE_ADVISOR,tms.user_name SERVICE_ADVISOR_NAME,");
		sb.append("C.ADDRESS,B.SALES_DATE,B.NEXT_MAINTAIN_MILEAGE,G.EMPLOYEE_NAME AS REMINDER_NAME,D.IS_RETURN_FACTORY,Coalesce(trace.Trace_tag,12781002) AS TRACE_TAG,trace.TRACE_STATUS,");
		sb.append("U.user_name LAST_SERVICE_ADVISOR, B.NEXT_MAINTAIN_DATE, B.DEALER_CODE,CASE WHEN R.VIN IS NULL THEN 12781002 ELSE 12781001 END ON_REPAIR,cd.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_VEHICLE + ") B");
		sb.append("  LEFT JOIN (select VIN,OWNER_NO,DEALER_CODE,RO_NO,SERVICE_ADVISOR from TT_REPAIR_ORDER where (DEALER_CODE,vin,ro_no) in (select DEALER_CODE,vin,max(RO_NO) from TT_REPAIR_ORDER WHERE REPAIR_TYPE_CODE <> ? ");
		queryList.add(CommonConstants.REPAIR_TYPE_SQWX);
		sb.append(" AND D_KEY = 0 group by DEALER_CODE,vin)) aa ON aa.vin=B.vin and aa.DEALER_CODE=B.DEALER_CODE and B.OWNER_NO=aa.OWNER_NO  ");
		sb.append("  LEFT JOIN tm_user U ON U.USER_ID = aa.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN tm_user tms ON tms.USER_ID = B.SERVICE_ADVISOR ");
		sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sb.append(" left join( SELECT distinct D.vin,e.CARD_TYPE_CODE FROM ");
		sb.append("(" + CommonConstants.VM_MEMBER_VEHICLE + ") D,");
		sb.append("(" + CommonConstants.VM_MEMBER_CARD + ") E ");
		sb.append(" WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  D.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and E.CARD_STATUS = ? ");
		queryList.add(CommonConstants.DICT_CARD_STATUS_NORMAL);
		sb.append(" ) cd on cd.vin=B.vin left join (select a.* from TT_VEHICLE_LOSS_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_VEHICLE_LOSS_REMIND where DEALER_CODE in ( select SHARE_ENTITY from ");
		sb.append("(" + CommonConstants.VM_ENTITY_SHARE_WITH + ") v where 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND  v.DEALER_CODE= ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		sb.append(" and v.BIZ_CODE = 'TT_ALL_REMIND') and LAST_TAG= ? ");
		queryList.add(CommonConstants.DICT_IS_YES);
		sb.append(" and D_KEY= ? ");
		queryList.add(CommonConstants.D_KEY);
		  if(!StringUtils.isNullOrEmpty(queryParam.get("isReturnFactory")))
			{
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" AND IS_RETURN_FACTORY= ? ");
					queryList.add(Integer.parseInt(queryParam.get("isReturnFactory")));
				}
				if(queryParam.get("isReturnFactory").equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" AND IS_RETURN_FACTORY is null ");
				}	
			}			
		sb.append(" GROUP BY VIN ) b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE  left join ");
		sb.append("(" + CommonConstants.VM_OWNER + ") C ");
		sb.append(" on  B.DEALER_CODE = C.DEALER_CODE and B.OWNER_NO=C.OWNER_NO 	LEFT JOIN (SELECT VIN,OWNER_NO,DEALER_CODE,COUNT(*) REPAIR_COUNT FROM TT_REPAIR_ORDER ");
		sb.append(" WHERE RO_STATUS = 12551001 AND RO_SPLIT_STATUS <> 13601002 AND D_KEY = 0 GROUP BY VIN,OWNER_NO,DEALER_CODE) R  ON B.DEALER_CODE = R.DEALER_CODE AND B.VIN = R.VIN AND B.OWNER_NO = R.OWNER_NO ");
		sb.append(" LEFT JOIN TT_LOSS_VEHICLE_TRACE TRACE  ON ( C.DEALER_CODE = TRACE.DEALER_CODE AND C.OWNER_NO = TRACE.OWNER_NO AND B.VIN = TRACE.VIN  )  WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sb.append(" AND B.DEALER_CODE = ? ");
			queryList.add(queryParam.get("dealer_code"));
		}
		Utility.sqlToDate(sb,queryParam.get("startNotInDate"), queryParam.get("endNotInDate"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sb.append(" AND B.LICENSE = ? ");
			queryList.add(queryParam.get("license"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
//			sb.append(" AND B.CONSULTANT like ? ");
//			queryList.add("%"+ queryParam.get("consultant") +"%");
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("address"))) {
			sb.append(" AND C.ADDRESS like ? ");
			queryList.add("%"+ queryParam.get("address") +"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			sb.append(" AND B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("cardTypeCode"))) {
			sb.append(" AND cd.CARD_TYPE_CODE = ? ");
			queryList.add(queryParam.get("cardTypeCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
//			sb.append(" AND B.SERVICE_ADVISOR = ? ");
//			queryList.add(queryParam.get("serviceAdvisor"));
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.SERVICE_ADVISOR = ? ");
			queryList.add(queryParam.get("serviceAdvisor"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("startNotInMileage"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("startNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endNotInMileage"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("endNotInMileage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessKind"))) {
			sb.append(" and B.BUSINESS_KIND= ? ");
			queryList.add(queryParam.get("businessKind"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehiclePurpose"))) {
			sb.append(" and B.VEHICLE_PURPOSE= ? ");
			queryList.add(queryParam.get("vehiclePurpose"));
		}
		Utility.sqlToDate(sb,queryParam.get("last_maintain_begin_date"), queryParam.get("last_maintain_end_date"), "B.LAST_MAINTAIN_DATE", "LAST_MAINTAIN_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
			sb.append(" and B.BRAND= ? ");
			queryList.add(queryParam.get("brandId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sb.append(" and B.SERIES= ? ");
			queryList.add(queryParam.get("seriesCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sb.append(" and B.MODEL= ? ");
			queryList.add(queryParam.get("modelCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sb.append(" and D.REMIND_STATUS = ? ");
			queryList.add(queryParam.get("remindStatus"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))&& !(queryParam.get("ownerProperty")).trim().equals("-1")) {
			sb.append("  and c.owner_Property= ? ");
			queryList.add(queryParam.get("ownerProperty"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageBegin"))) {
			sb.append(" AND B.MILEAGE BETWEEN ? ");
			queryList.add(queryParam.get("mileageBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("mileageEnd"))) {
			sb.append(" AND ? ");
			queryList.add(queryParam.get("mileageEnd"));
		}
		Utility.sqlToDate(sb,queryParam.get("salesDateBegin"), queryParam.get("salesDateEnd"), "B.SALES_DATE", "SALES_DATE");
		Utility.sqlToDate(sb,queryParam.get("remindDateBegin"), queryParam.get("remindDateEnd"), "D.REMIND_DATE", "REMIND_DATE");
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSelfCompany"))) {
			if(CommonConstants.DICT_IS_YES.equals(queryParam.get("isSelfCompany")))
			{
				sb.append(" AND B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
			}
			else
			{
				sb.append(" AND (B.IS_SELF_COMPANY= ? ");
				queryList.add(queryParam.get("isSelfCompany"));
				sb.append(" or B.IS_SELF_COMPANY IS NULL  OR B.IS_SELF_COMPANY=0  ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("comeInFactory"))) {
			if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_YES))
			{
				//為空的進場日期
				sb.append(" and B.FIRST_IN_DATE IS NULL ");
			}
			else if((queryParam.get("comeInFactory")).trim().equals(CommonConstants.DICT_IS_NO))
			{
				sb.append(" and B.FIRST_IN_DATE IS NOT NULL ");
			}
		}
			if (!StringUtils.isNullOrEmpty(queryParam.get("IsValid"))) {
				if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_YES))
				{
					sb.append(" and B.IS_VALID = ? "); 
					queryList.add(queryParam.get("IsValid"));
				}
				else if ((queryParam.get("IsValid")).equals(CommonConstants.DICT_IS_NO))
				{
					sb.append(" and B.IS_VALID = ? ");
					queryList.add(queryParam.get("IsValid"));
				}
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMember"))) {
                    sb.append(" AND EXISTS( SELECT * FROM ");
                    sb.append("("+ CommonConstants.VM_MEMBER_VEHICLE +" ) D,");
                    sb.append("("+ CommonConstants.VM_MEMBER_CARD +" ) E,");
                    sb.append("("+ CommonConstants.VM_MEMBER +" ) F ");
                    sb.append("WHERE D.DEALER_CODE=E.DEALER_CODE AND D.CARD_ID=E.CARD_ID AND E.DEALER_CODE=F.DEALER_CODE AND E.MEMBER_NO=F.MEMBER_NO ");
                    if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
                        sb.append("AND D.DEALER_CODE = ? ");
                        queryList.add(queryParam.get("dealer_code"));
                    }
                    sb.append(" AND D.VIN=B.VIN AND  E.CARD_STATUS = ");
                    sb.append(CommonConstants.DICT_CARD_STATUS_NORMAL + "  AND F.MEMBER_STATUS = ");
                    sb.append(CommonConstants.DICT_MEMBER_STATUS_NORMAL + ") ");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("flag"))) {
                if(queryParam.get("flag").equals(CommonConstants.DICT_IS_YES))
                {
                    sb.append(" and (D.REMIND_DATE IS NULL  ) ");
                }
            }
				if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
					sb.append(" AND B.VIN LIKE ? ");
					queryList.add("%"+ queryParam.get("vin") +"%");
				}
				
			if (!StringUtils.isNullOrEmpty(queryParam.get("lastServiceadvisor"))) {
				sb.append(" )aaa where 1=1 and LAST_SERVICE_ADVISOR= ? ");
				queryList.add(queryParam.get("lastServiceadvisor"));
				if (!StringUtils.isNullOrEmpty(queryParam.get("traceTag"))) {
                    sb.append(" AND aaa.trace_tag = ? ");
                    queryList.add(queryParam.get("traceTag"));
                }
			}
		List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
		return resultList;
	}
	}
