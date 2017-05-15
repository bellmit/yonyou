/**
 * 
 */
package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.FamilyMenberDTO;
import com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerLinkmanInfoDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmCustomerFamilyPersonPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerLinkmanInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusRelationPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author Administrator
 *
 */
@Service
public class OwnerCusServiceImpl implements OwnerCusService {
    @Autowired
    private CommonNoService commonNoService;
    @Autowired
    private OperateLogService operateLogService;
	/**
	 * 根据父表主键查询(展厅客户意向报价)明细
	 * 
	 * @author zhanshiwei
	 * @date 2016年9月1日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#queryVisitIntentInfoByParendId(Long)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusByEmployee(String employeeNo, String vin, String dealerCode)
			throws ServiceBizException {

		List<Map> orderlist = null;
		List queryParam = new ArrayList();
		if (vin != null && vin.trim().length() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("select * from tt_sales_order where vin= ? ");
			queryParam.add(vin);
			orderlist = DAOUtil.findAll(sb.toString(), queryParam);

		}
		StringBuffer sql = new StringBuffer();
		StringBuffer sqltmp = new StringBuffer();
		StringBuffer sqltmpdb = new StringBuffer();
		List queryParam1 = new ArrayList();
		String sql1 = "select * from Tt_Sec_Sales_Order_Item where vin=? ";
		queryParam1.add(vin);
		List<Map> SecondSalesOrderlist = DAOUtil.findAll(sql1, queryParam1);

		sqltmp.append("select\n" + "s.DEALER_CODE,\n" + "   s.VIN,\n" + "   s.OWNER_NO,\n" + "  s.PO_CUSTOMER_NO,\n"
				+ "   s.PO_CUSTOMER_NAME,\n" + "   s.PO_CONTACTOR_PHONE,\n" + "   s.PO_CONTACTOR_MOBILE,\n"
				+ "   s.PO_ADDRESS,\n" + "    s.CUSTOMER_NO,\n" + "    s.CUSTOMER_NAME,\n" + "    s.SOD_CUSTOMER_ID,\n"
				+ "    s.CUSTOMER_TYPE,\n" + "    s.GENDER,\n" + "    s.BIRTHDAY,\n" + "    s.ZIP_CODE,\n"
				+ "    s.BUY_PURPOSE,\n" + "    s.COUNTRY_CODE,\n" + "    s.PROVINCE,\n" + "    s.CITY,\n"
				+ "    s.DISTRICT,\n" + "    s.ADDRESS,\n" + "    s.E_MAIL,\n" + "    s.HOBBY,\n"
				+ "    s.OWNER_MARRIAGE,\n" + "    s.AGE_STAGE,\n" + "    s.FAMILY_INCOME,\n" + "    s.POSITION_NAME,\n"
				+ "    s.VOCATION_TYPE,\n" + "    s.EDUCATION_LEVEL,\n" + "    s.CONTACTOR_PHONE,\n"
				+ "    s.CONTACTOR_MOBILE,\n" + "    s.IS_WHOLESALER,\n" + "    s.CT_CODE,\n"
				+ "    s.CERTIFICATE_NO,\n" + "    s.RECOMMEND_EMP_NAME,\n" + "    s.INIT_LEVEL,\n"
				+ "    s.INTENT_LEVEL,\n" + "    s.FAIL_CONSULTANT,\n" + "    s.DELAY_CONSULTANT,\n"
				+ "    s.INDUSTRY_FIRST,\n" + "    s.INDUSTRY_SECOND,\n" + "    s.SOLD_BY,\n" + "    s.CUS_SOURCE,\n"
				+ "    s.MEDIA_TYPE,\n" + "    s.IS_REPORTED,\n" + "    s.REPORT_REMARK,\n" + "    s.REPORT_DATETIME,\n"
				+ "    s.REPORT_STATUS,\n" + "    s.REPORT_AUDITING_REMARK,\n" + "    s.REPORT_ABORT_REASON,\n"
				+ "    s.GATHERED_SUM,\n" + "    s.ORDER_PAYED_SUM,\n" + "   s.CON_PAYED_SUM,\n"
				+ "    s.USABLE_AMOUNT,\n" + "    s.UN_WRITEOFF_SUM,\n" + "    s.OWNED_BY,\n" + "    s.FOUND_DATE,\n"
				+ "    s.IS_UPLOAD,\n" + "    s.LARGE_CUSTOMER_NO,\n" + "    s.ORGAN_TYPE,\n" + "    s.DCRC_SERVICE,\n"
				+ "    s.ORGAN_TYPE_CODE,\n" + "    s.BUY_REASON,\n" + "    s.IS_FIRST_BUY,\n" + "    s.REMARK,\n"
				+ "    s.CONSULTANT_TIME,\n" + "    s.BEST_CONTACT_TYPE,\n" + "    s.CAMPAIGN_CODE,\n"
				+ "    s.D_KEY,\n" + "    s.IS_PERSON_DRIVE_CAR,\n" + "    s.CHOICE_REASON,\n" + "    s.IS_DIRECT,\n"
				+ "    s.SUBMIT_TIME,\n" + "    s.DOWN_TIMESTAMP,\n" + "    s.MODIFY_REASON,\n"
				+ "    s.HAS_DRIVER_LICENSE,\n" + "    s.IS_CRPVIP,\n" + "    s.FAX,\n"
				+ "    s.RECOMMEND_EMP_PHONE, \n" + "   case when M_CAMPAIGN_NAME is not null then M_CAMPAIGN_NAME\n"
				+ "        when N_ADVERT_TOPIC is not null then N_ADVERT_TOPIC\n" + "        else ' '\n"
				+ "        end CAMPAIGN_NAME,IS_REMOVED ,\n" + "    s.FIRSTDATE_DRIVE, \n" + "    s.MARRY_DATE, \n"
				+ "    s.IS_MESSAGED \n" + "from ( \n");
		sqltmp.append("SELECT D.*," +
		// "CASE WHEN D.CUS_SOURCE =" +
		// DictDataConstant.DICT_CUS_SOURCE_MARKET_ACTIVITY + " THEN
		// M.CAMPAIGN_NAME WHEN D.CUS_SOURCE =" +
		// DictDataConstant.DICT_CUS_SOURCE_AD_ACTIVITY + " THEN N.ADVERT_TOPIC
		// ELSE ' ' END AS CAMPAIGN_NAME " +
				"(select M.CAMPAIGN_NAME from TT_CAMPAIGN_PLAN M\n"
				+ "          where D.CAMPAIGN_CODE=M.CAMPAIGN_CODE\n"
				+ "               AND D.DEALER_CODE=M.DEALER_CODE) M_CAMPAIGN_NAME,\n"
				+ "(select N.ADVERT_TOPIC from TT_ADVERT_MEDIA N where D.DEALER_CODE=N.DEALER_CODE\n"
				+ "AND D.CAMPAIGN_CODE= CAST(N.ITEM_ID AS CHAR(14))) N_ADVERT_TOPIC \n"
				+ "FROM (SELECT A.DEALER_CODE, D.VIN,321 AS OWNER_NO, C.PO_CUSTOMER_NO, B.CUSTOMER_NAME AS PO_CUSTOMER_NAME,B.CONTACTOR_PHONE AS PO_CONTACTOR_PHONE,B.CONTACTOR_MOBILE AS PO_CONTACTOR_MOBILE,B.ADDRESS AS PO_ADDRESS,");
		sqltmp.append(
				"A.CUSTOMER_NO,A.CUSTOMER_NAME,A.LARGE_CUSTOMER_NO,A.SOD_CUSTOMER_ID,A.CUSTOMER_TYPE,A.GENDER,A.BIRTHDAY,A.ZIP_CODE,A.BUY_PURPOSE,A.COUNTRY_CODE,A.PROVINCE,");
		sqltmp.append(
				"A.CITY,A.DISTRICT,A.ADDRESS,A.E_MAIL,A.HOBBY,A.OWNER_MARRIAGE,A.AGE_STAGE,A.FAMILY_INCOME,A.POSITION_NAME,A.VOCATION_TYPE,A.EDUCATION_LEVEL,");
		sqltmp.append(
				"A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.IS_WHOLESALER,A.CT_CODE,A.CERTIFICATE_NO,A.RECOMMEND_EMP_NAME,A.INIT_LEVEL,A.INTENT_LEVEL,A.FAIL_CONSULTANT,A.DELAY_CONSULTANT,A.INDUSTRY_FIRST,A.INDUSTRY_SECOND,A.SOLD_BY,A.CUS_SOURCE,");
		sqltmp.append(
				"A.MEDIA_TYPE,A.IS_REPORTED,A.REPORT_REMARK,A.REPORT_DATETIME,A.REPORT_STATUS,A.REPORT_AUDITING_REMARK,A.REPORT_ABORT_REASON,A.GATHERED_SUM,");
		sqltmp.append(
				"A.ORDER_PAYED_SUM,A.CON_PAYED_SUM,A.USABLE_AMOUNT,A.UN_WRITEOFF_SUM,A.OWNED_BY,A.FOUND_DATE,A.IS_UPLOAD,"
						+
						// "A.LARGE_CUSTOMER_NO," +
						"A.ORGAN_TYPE,A.IS_REMOVED,");
		sqltmp.append(
				"A.DCRC_SERVICE,A.ORGAN_TYPE_CODE,A.BUY_REASON,A.IS_FIRST_BUY,A.REMARK,A.CONSULTANT_TIME,A.BEST_CONTACT_TYPE,A.CAMPAIGN_CODE,A.D_KEY,A.IS_PERSON_DRIVE_CAR,A.CHOICE_REASON,A.IS_DIRECT,A.SUBMIT_TIME,A.DOWN_TIMESTAMP"
						+ ",A.MODIFY_REASON,A.HAS_DRIVER_LICENSE,A.IS_CRPVIP,A.FAX, A.RECOMMEND_EMP_PHONE,A.FIRSTDATE_DRIVE,A.MARRY_DATE,A.IS_MESSAGED  "); // add
																																							// by
																																							// sf
																																							// 2011-03-25
																																							// add
																																							// RECOMMEND_EMP_PHONE
		sqltmp.append(
				" FROM  TM_CUSTOMER A, TM_POTENTIAL_CUSTOMER B,TT_PO_CUS_RELATION C,TT_SALES_ORDER  d WHERE A.DEALER_CODE=C.DEALER_CODE AND  ");
		sqltmp.append(
				" A.CUSTOMER_NO=C.CUSTOMER_NO AND B.CUSTOMER_NO=C.PO_CUSTOMER_NO AND B.DEALER_CODE=C.DEALER_CODE and b.CUSTOMER_NO=d.CUSTOMER_NO AND c.vin=d.vin  and c.PO_CUSTOMER_NO=d.CUSTOMER_NO   and c.SO_NO=d.SO_NO) D ");
		sqltmp.append(" WHERE D.DEALER_CODE='" + dealerCode + "' AND D.D_KEY=0 AND D.CUSTOMER_NO='" + employeeNo + "'"
				+ " AND D.VIN='" + vin + "' ) S");
		// 调拨 委托
		sqltmpdb.append("select\n" + " case when M_CAMPAIGN_NAME is not null then M_CAMPAIGN_NAME\n"
				+ "      when N_ADVERT_TOPIC is not null then N_ADVERT_TOPIC\n" + "      else ' '\n"
				+ " end CAMPAIGN_NAME,\n" + "DEALER_CODE,\n" + " VIN,\n" + " OWNER_NO,\n" + " PO_CUSTOMER_NO,\n"
				+ " PO_CUSTOMER_NAME,\n" + " PO_CONTACTOR_PHONE,\n" + " PO_CONTACTOR_MOBILE,\n" + " PO_ADDRESS,\n"
				+ " CUSTOMER_NO,\n" + " CUSTOMER_NAME,\n" + " LARGE_CUSTOMER_NO,\n" + " SOD_CUSTOMER_ID,\n"
				+ " CUSTOMER_TYPE,\n" + " GENDER,\n" + " BIRTHDAY,\n" + " ZIP_CODE,\n" + " BUY_PURPOSE,\n"
				+ " COUNTRY_CODE,\n" + " PROVINCE,\n" + " CITY,\n" + " DISTRICT,\n" + " ADDRESS,\n" + " E_MAIL,\n"
				+ " HOBBY,\n" + " OWNER_MARRIAGE,\n" + " AGE_STAGE,\n" + " FAMILY_INCOME,\n" + " POSITION_NAME,\n"
				+ " VOCATION_TYPE,\n" + " EDUCATION_LEVEL,\n" + " CONTACTOR_PHONE,\n" + " CONTACTOR_MOBILE,\n"
				+ " IS_WHOLESALER,\n" + " CT_CODE,\n" + " CERTIFICATE_NO,\n" + " RECOMMEND_EMP_NAME,\n"
				+ " INIT_LEVEL,\n" + " INTENT_LEVEL,\n" + " FAIL_CONSULTANT,\n" + " DELAY_CONSULTANT,\n"
				+ " INDUSTRY_FIRST,\n" + " INDUSTRY_SECOND,\n" + " SOLD_BY,\n" + " CUS_SOURCE,\n" + " MEDIA_TYPE,\n"
				+ " IS_REPORTED,\n" + " REPORT_REMARK,\n" + " REPORT_DATETIME,\n" + " REPORT_STATUS,\n"
				+ " REPORT_AUDITING_REMARK,\n" + " REPORT_ABORT_REASON,\n" + " GATHERED_SUM,\n" + " ORDER_PAYED_SUM,\n"
				+ " CON_PAYED_SUM,\n" + " USABLE_AMOUNT,\n" + " UN_WRITEOFF_SUM,\n" + " OWNED_BY,\n" + " FOUND_DATE,\n"
				+ " IS_UPLOAD,\n" + " ORGAN_TYPE,\n" + " DCRC_SERVICE,\n" + " ORGAN_TYPE_CODE,\n" + " BUY_REASON,\n"
				+ " IS_FIRST_BUY,\n" + " REMARK,\n" + " CONSULTANT_TIME,\n" + " BEST_CONTACT_TYPE,\n"
				+ " CAMPAIGN_CODE,\n" + " D_KEY,\n" + " IS_PERSON_DRIVE_CAR,\n" + " CHOICE_REASON,\n" + " IS_DIRECT,\n"
				+ " SUBMIT_TIME,\n" + " DOWN_TIMESTAMP,\n" + " MODIFY_REASON,\n" + " HAS_DRIVER_LICENSE,\n"
				+ " IS_CRPVIP,\n" + " FAX,IS_REMOVED," + " FIRSTDATE_DRIVE, \n" + " MARRY_DATE, \n" + " IS_MESSAGED, \n"
				+ " RECOMMEND_EMP_PHONE \n" + "from ( \n");
		sqltmpdb.append(" SELECT " +
		// "'' as CAMPAIGN_NAME," +
				" (select M.CAMPAIGN_NAME from TT_CAMPAIGN_PLAN M\n" + "where A.CAMPAIGN_CODE=M.CAMPAIGN_CODE\n"
				+ "AND A.DEALER_CODE=M.DEALER_CODE) M_CAMPAIGN_NAME,\n"
				+ "(select N.ADVERT_TOPIC from TT_ADVERT_MEDIA N where A.DEALER_CODE=N.DEALER_CODE\n"
				+ "AND A.CAMPAIGN_CODE= CAST(N.ITEM_ID AS CHAR(14))) N_ADVERT_TOPIC ,\n"
				+ "A.DEALER_CODE, '' as  VIN,'' AS OWNER_NO, '' as PO_CUSTOMER_NO, '' AS PO_CUSTOMER_NAME,");
		sqltmpdb.append(" ''  AS PO_CONTACTOR_PHONE,'' AS PO_CONTACTOR_MOBILE,'' AS PO_ADDRESS,A.CUSTOMER_NO,");
		sqltmpdb.append(
				" A.CUSTOMER_NAME,A.LARGE_CUSTOMER_NO,A.SOD_CUSTOMER_ID,A.CUSTOMER_TYPE,A.GENDER,A.BIRTHDAY,A.ZIP_CODE,A.BUY_PURPOSE,A.COUNTRY_CODE,");
		sqltmpdb.append(
				" A.PROVINCE,A.CITY,A.DISTRICT,A.ADDRESS,A.E_MAIL,A.HOBBY,A.OWNER_MARRIAGE,A.AGE_STAGE,A.FAMILY_INCOME,A.POSITION_NAME,A.VOCATION_TYPE,");
		sqltmpdb.append(
				" A.EDUCATION_LEVEL,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.IS_WHOLESALER,A.CT_CODE,A.CERTIFICATE_NO,A.RECOMMEND_EMP_NAME,A.INIT_LEVEL,");
		sqltmpdb.append(
				" A.INTENT_LEVEL,A.FAIL_CONSULTANT,A.DELAY_CONSULTANT,A.INDUSTRY_FIRST,A.INDUSTRY_SECOND,A.SOLD_BY,A.CUS_SOURCE,A.MEDIA_TYPE,");
		sqltmpdb.append(
				" A.IS_REPORTED,A.REPORT_REMARK,A.REPORT_DATETIME,A.REPORT_STATUS,A.REPORT_AUDITING_REMARK,A.REPORT_ABORT_REASON,A.GATHERED_SUM,");
		sqltmpdb.append(
				" A.ORDER_PAYED_SUM,A.CON_PAYED_SUM,A.USABLE_AMOUNT,A.UN_WRITEOFF_SUM,A.OWNED_BY,A.FOUND_DATE,A.IS_UPLOAD,");
		sqltmpdb.append(
				" A.ORGAN_TYPE,A.DCRC_SERVICE,A.ORGAN_TYPE_CODE,A.BUY_REASON,A.IS_FIRST_BUY,A.REMARK,A.CONSULTANT_TIME,A.BEST_CONTACT_TYPE,");
		sqltmpdb.append(
				" A.CAMPAIGN_CODE,A.D_KEY,A.IS_PERSON_DRIVE_CAR,A.CHOICE_REASON,A.IS_DIRECT,A.SUBMIT_TIME,A.DOWN_TIMESTAMP,A.MODIFY_REASON,");
		sqltmpdb.append(
				" A.HAS_DRIVER_LICENSE,A.IS_CRPVIP,A.FAX,A.RECOMMEND_EMP_PHONE,A.IS_REMOVED ,A.FIRSTDATE_DRIVE,A.MARRY_DATE,A.IS_MESSAGED  FROM  TM_CUSTOMER A");
		sqltmpdb.append(
				" WHERE A.DEALER_CODE='" + dealerCode + "' AND A.D_KEY=0 AND A.CUSTOMER_NO='" + employeeNo + "') s ");

		if (orderlist == null) {
			sql.append(sqltmp);
		} else {
			if (orderlist.size() > 0) {
				// 调拨 委托 //此处 如做过其他订单 然后退回的 再做调拨单 怎么办
				// 应该取开单时间最晚的一个SHEET_CREATE_DATE add by lm
				if (orderlist.get(0).get("Business_Type") != null
						&& (orderlist.get(0).get("Business_Type").toString().equals("13001002")
								|| orderlist.get(0).get("Business_Type").toString().equals("13001002"))) {
					sql.append(sqltmpdb);
				}
			}
		}
		if (SecondSalesOrderlist != null) {// liangqian
			if (SecondSalesOrderlist.size() > 0)
				sql.append(sqltmpdb);
		}
		if (sql.length() == 0) {
			sql.append(sqltmp);
		}
		System.err.println(sql);
		return Base.findAll(sql.toString());
	}
	 /**
     * 潜在客户信息新增
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param potentialCusDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#addPotentialCusInfo(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public String addOwnerCusInfo(OwnerCusDTO ownerCusDto, String customerNo) throws ServiceBizException {
        CustomerPO ownercuspo = new CustomerPO();    
        ownerCusDto.setCustomerNo(customerNo);// 潜客编码    
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String owno =commonNoService.getSystemOrderNo(CommonConstants.OWNER_PREFIX);
        Long intentId = commonNoService.getId("ID");
        System.out.println("——————————————————————————————————开始保存——————————————————————————————---");     
        System.out.println(customerNo);        
        this.setOwnerCus(ownercuspo, ownerCusDto);      
        ownercuspo.saveIt();
        System.out.println("——————————————————————————————————保客保存结束——————————————————————————————---");
      if(ownerCusDto.getOwnerList4().size()>0 && ownerCusDto.getOwnerList4() !=null){
            for(TtCustomerLinkmanInfoDTO linkDto : ownerCusDto.getOwnerList4()){
                System.out.println(linkDto.getCompany());
                TtCustomerLinkmanInfoPO PO=getLinkman(linkDto, customerNo,intentId);
                PO.saveIt();
            } 
        }
        System.out.println("——————————————————————————————————联系人保存结束——————————————————————————————---");
        if(ownerCusDto.getOwnerList3().size()>0 && ownerCusDto.getOwnerList3() !=null){
            for(FamilyMenberDTO familyDto : ownerCusDto.getOwnerList3()){
                System.out.println(familyDto.getBirthday()); 
                getFamily(familyDto, customerNo).saveIt();
            } 
        }
        System.out.println("——————————————————————————————————家庭人保存结束——————————————————————————————---");
        CarownerPO ownerpo =new CarownerPO();
        ownerpo.setString("OWNER_NO", owno);     
        this.setOwner(ownerpo, ownerCusDto);       
        ownerpo.saveIt();
        System.out.println("——————————————————————————————————车主保存结束——————————————————————————————---");
        VehiclePO vehiclepo =new VehiclePO();
        vehiclepo.setString("OWNER_NO",owno);    
        vehiclepo.setString("CUSTOMER_NO",ownerCusDto.getCustomerNo());
        this.setVehicle(vehiclepo, ownerCusDto);       
        vehiclepo.saveIt();
        System.out.println("——————————————————————————————————车辆保存结束——————————————————————————————---");
        String cus2 =commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX);          
        List<Object> cus = new ArrayList<Object>();
        cus.add(ownerCusDto.getContactorMobile());
        cus.add(ownerCusDto.getContactorPhone());
        cus.add(loginInfo.getDealerCode());
        List<PotentialCusPO> potentialCusPo = PotentialCusPO.findBySQL("select * from Tm_potential_customer where CONTACTOR_MOBILE=? and CONTACTOR_MOBILE=? and dealer_code=?", cus.toArray());
        if (potentialCusPo.size() >0){
            PotentialCusPO gxbpo=new PotentialCusPO();
            gxbpo = (PotentialCusPO) potentialCusPo.get(0); 
          //  新增关联关系          
           this.insertRelation(ownerCusDto,gxbpo.get("CUSTOMER_NO"),customerNo);
           
            System.out.println("——————————————————————————————————查到此人则建立潜客和保客关系——————————————————————————————---");           
        }else{
            System.out.println("——————————————————————————————————若梅查到此人，新建潜客——————————————————————————————---"); 
          //新建潜在客户信息         
            this.insertPuCustomer(ownerCusDto,cus2);
            this.insertRelation(ownerCusDto,cus2,customerNo);
            Long intentId2 = commonNoService.getId("ID");
            if(ownerCusDto.getOwnerList4().size()>0 && ownerCusDto.getOwnerList4() !=null){
                for(TtCustomerLinkmanInfoDTO linkDto : ownerCusDto.getOwnerList4()){
                    System.out.println(linkDto.getCompany());
                    getLinkman1(linkDto, cus2,intentId2).saveIt();                 
                } 
            }           
        }
        TtCusIntentPO cusIntenPo2=new TtCusIntentPO();
        Long intentId3 = commonNoService.getId("ID");
        System.out.println(intentId3);
        System.out.println(cus2);
        cusIntenPo2.setString("DEALER_CODE",loginInfo.getDealerCode()); 
        cusIntenPo2.setString("INTENT_ID",intentId3);
        cusIntenPo2.setString("CUSTOMER_NO",cus2);
        cusIntenPo2.setString("INTENT_FINISHED",DictCodeConstants.STATUS_IS_NOT);
        cusIntenPo2.setString("IS_UPLOAD",DictCodeConstants.STATUS_IS_NOT);
        cusIntenPo2.saveIt();
      
        
        
        System.out.println("——————————————————————————————————保存结束——————————————————————————————---");
        return customerNo;
    }
	/**
	 * 保有客户信息修改
	 * 
	 * @author gm
	 * @date 2016年9月1日
	 * @param potentialCusDto
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#addPotentialCusInfo(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
	 */
	@Override
	public void modifyOwnerCusInfo(String custmerNo,String vin,String ownerNo,OwnerCusDTO ownerCusDto) throws ServiceBizException {
	    CustomerPO ownercuspo = CustomerPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),custmerNo);
	    Long intentId = commonNoService.getId("ID");	
		ownerCusDto.setCustomerNo(ownercuspo.getString("CUSTOMER_NO"));
		this.setOwnerCus(ownercuspo, ownerCusDto);
		ownercuspo.saveIt();
		if(ownerCusDto.getOwnerList4().size()>0 && ownerCusDto.getOwnerList4()!=null){
            for(TtCustomerLinkmanInfoDTO linkDto : ownerCusDto.getOwnerList4()){
                System.out.println(linkDto.getCompany());
            //    getLinkman(linkDto, customerNo).saveIt();
            } 
        }
        System.out.println("——————————————————————————————————联系人保存结束——————————————————————————————---");
        TtCustomerLinkmanInfoPO.delete(" CUSTOMER_NO= ? AND DEALER_CODE= ?", custmerNo,FrameworkUtil.getLoginInfo().getDealerCode());

        if(ownerCusDto.getOwnerList4().size()>0 && ownerCusDto.getOwnerList4() !=null){
            for(TtCustomerLinkmanInfoDTO linkDto : ownerCusDto.getOwnerList4()){
                System.out.println(linkDto.getCompany());
                TtCustomerLinkmanInfoPO po= getLinkman(linkDto, custmerNo,intentId);
                po.saveIt();
            } 
        }
        System.out.println("——————————————————————————————————联系人保存结束——————————————————————————————---");

        TmCustomerFamilyPersonPO.delete("connection_code= ? AND DEALER_CODE= ?", custmerNo,FrameworkUtil.getLoginInfo().getDealerCode());
        // 删除家庭
        if(ownerCusDto.getOwnerList3().size()>0 && ownerCusDto.getOwnerList3() !=null){
            for(FamilyMenberDTO familyDto : ownerCusDto.getOwnerList3()){
                System.out.println(familyDto.getBirthday()); 
                getFamily(familyDto, custmerNo).saveIt();
            } 
        }
        System.out.println("——————————————————————————————————家庭保存结束——————————————————————————————---");
      
//        CarownerPO ownerpo2 = CarownerPO.findByCompositeKeys(ownerNo,FrameworkUtil.getLoginInfo().getDealerCode());
//        ownerCusDto.setCustomerNo(ownerNo);
//        this.setOwner(ownerpo2, ownerCusDto);       
//        ownerpo2.saveIt();
//        System.out.println("——————————————————————————————————车主保存结束——————————————————————————————---"); 
//        VehiclePO vehiclepo2 = VehiclePO.findByCompositeKeys(vin,FrameworkUtil.getLoginInfo().getDealerCode());
//        ownerCusDto.setVin(vin);
//        this.setVehicle(vehiclepo2, ownerCusDto);       
//        ownerpo2.saveIt();
        System.out.println("——————————————————————————————————车辆保存结束——————————————————————————————---");
     
	} 
	
	public void insertRelation(OwnerCusDTO ownerCusDto,Object cus2,String customerNo) {
	    Long intentId1 = commonNoService.getId("ID");  
	    TtPoCusRelationPO relationPo=new TtPoCusRelationPO();
        relationPo.setLong("ITEM_ID",intentId1);
        relationPo.setString("Customer_No",customerNo);
        relationPo.setString("Po_Customer_No",cus2);
        relationPo.setString("Vin",ownerCusDto.getVin());
        relationPo.setInteger("Vehi_Return",12781002);
        relationPo.saveIt();
	    
	}
	public void insertPuCustomer(OwnerCusDTO ownerCusDto, String cus2) {
	    PotentialCusPO cusct = new PotentialCusPO();
	
	    cusct.setString("CUSTOMER_NO",cus2);
	    cusct.setString("IS_UPLOAD", "12781002");// 上报
	    cusct.setString("CUSTOMER_NAME", ownerCusDto.getCustomerName());// 客户编码
	    cusct.setInteger("CUSTOMER_TYPE", ownerCusDto.getCustomerType());// 客户类型
	    cusct.setInteger("GENDER", ownerCusDto.getGender());// 性别
	    cusct.setString("CONTACTOR_MOBILE", ownerCusDto.getContactorMobile());// 手机
	    cusct.setString("CONTACTOR_PHONE", ownerCusDto.getContactorPhone());// 电话
	    cusct.setDate("BIRTHDAY", ownerCusDto.getBirthday());// 出生日期
	    cusct.setInteger("CUSTOMER_STATUS", "13211002");// 基盘客户
	    cusct.setInteger("PROVINCE", ownerCusDto.getProvince1());
	    cusct.setInteger("CITY", ownerCusDto.getCity1());
	    cusct.setInteger("DISTRICT", ownerCusDto.getDistrict1());
	    cusct.setString("ADDRESS", ownerCusDto.getAddress());
	    cusct.setString("E_MAIL", ownerCusDto.geteMail());
	    cusct.setInteger("HOBBY", ownerCusDto.getHobby());
	    cusct.setInteger("INDUSTRY_FIRST", ownerCusDto.getIndustryFirst());
	    cusct.setInteger("INDUSTRY_SECOND", ownerCusDto.getIndustrySecond());
		cusct.setString("SOLD_BY", ownerCusDto.getSoldBy());
		cusct.setInteger("CUS_SOURCE", ownerCusDto.getCusSource());
		cusct.setInteger("MEDIA_TYPE", ownerCusDto.getMediaType());
		cusct.setInteger("CT_CODE", ownerCusDto.getCtCode());
		cusct.setString("CERTIFICATE_NO", ownerCusDto.getCertificateNo());
		cusct.setString("RECOMMEND_EMP_NAME", ownerCusDto.getRecommendEmpName());
		cusct.setString("RECOMMEND_EMP_PHONE", ownerCusDto.getRecommendEmpPhone());
		cusct.setInteger("FAMILY_INCOME", ownerCusDto.getFamilyIncome());
		cusct.setInteger("AGE_STAGE", ownerCusDto.getAgeStage());
		cusct.setString("FAX", ownerCusDto.getFax());
		cusct.setInteger("EDUCATION_LEVEL", ownerCusDto.getEducationLevel());
		cusct.setInteger("OWNER_MARRIAGE", ownerCusDto.getOwnerMarriage());
		cusct.setInteger("VOCATION_TYPE", ownerCusDto.getVocationType());
		cusct.setInteger("POSITION_NAME", ownerCusDto.getPositionName());
		cusct.setInteger("IS_CRPVIP", ownerCusDto.getIsFirstBuy());
		cusct.setInteger("IS_FIRST_BUY", ownerCusDto.getIsFirstBuy());
	
		cusct.setInteger("HAS_DRIVER_LICENSE", ownerCusDto.getHasDriverLicense());
		if (!StringUtils.isNullOrEmpty(StringUtils.listToString(ownerCusDto.getBuyReason(), ','))){
		    cusct.setString("BUY_REASON", StringUtils.listToString(ownerCusDto.getBuyReason(), ','));// 购车因素
		}
		cusct.setString("MODIFY_REASON", ownerCusDto.getModifyReason());
		cusct.setInteger("BEST_CONTACT_TYPE", ownerCusDto.getBestContactType());
		cusct.setString("DCRC_SERVICE", ownerCusDto.getDcrcService());
		cusct.setString("LARGE_CUSTOMER_NO", ownerCusDto.getContactorMobile());
		cusct.setDate("BIRTHDAY", ownerCusDto.getBirthday());
		System.out.println("_____________________________555");
		System.out.println(cus2);
		cusct.saveIt();
		
		
	}
	   public void setOwnerCus(CustomerPO ownercuspo, OwnerCusDTO ownerCusDto) {

	        ownercuspo.setString("IS_UPLOAD", "12781002");// 上报
	        ownercuspo.setString("CUSTOMER_NO", ownerCusDto.getCustomerNo());// 潜客编号
	        ownercuspo.setString("CUSTOMER_NAME", ownerCusDto.getCustomerName());// 客户编码
	        ownercuspo.setInteger("CUSTOMER_TYPE", ownerCusDto.getCustomerType());// 客户类型
	        ownercuspo.setInteger("GENDER", ownerCusDto.getGender());// 性别
	        ownercuspo.setString("CONTACTOR_MOBILE", ownerCusDto.getContactorMobile());// 手机
	        ownercuspo.setString("CONTACTOR_PHONE", ownerCusDto.getContactorPhone());// 电话
	        ownercuspo.setDate("BIRTHDAY", ownerCusDto.getBirthday());// 出生日期
	        ownercuspo.setInteger("CUSTOMER_STATUS", "13211002");// 基盘客户
	        ownercuspo.setInteger("PROVINCE", ownerCusDto.getProvince1());
	        ownercuspo.setInteger("CITY", ownerCusDto.getCity1());
	        ownercuspo.setInteger("DISTRICT", ownerCusDto.getDistrict1());
	        ownercuspo.setString("ADDRESS", ownerCusDto.getAddress());
	        ownercuspo.setString("E_MAIL", ownerCusDto.geteMail());
	        ownercuspo.setInteger("HOBBY", ownerCusDto.getHobby());
	        ownercuspo.setInteger("INDUSTRY_FIRST", ownerCusDto.getIndustryFirst());
	        ownercuspo.setInteger("INDUSTRY_SECOND", ownerCusDto.getIndustrySecond());
	        ownercuspo.setString("SOLD_BY", ownerCusDto.getSoldBy());
	        ownercuspo.setInteger("CUS_SOURCE", ownerCusDto.getCusSource());
	        ownercuspo.setInteger("MEDIA_TYPE", ownerCusDto.getMediaType());
	        ownercuspo.setInteger("CT_CODE", ownerCusDto.getCtCode());
	        ownercuspo.setString("CERTIFICATE_NO", ownerCusDto.getCertificateNo());
	        ownercuspo.setString("RECOMMEND_EMP_NAME", ownerCusDto.getRecommendEmpName());
	        ownercuspo.setString("RECOMMEND_EMP_PHONE", ownerCusDto.getRecommendEmpPhone());
	        ownercuspo.setInteger("FAMILY_INCOME", ownerCusDto.getFamilyIncome());
	        ownercuspo.setInteger("AGE_STAGE", ownerCusDto.getAgeStage());
	        ownercuspo.setString("FAX", ownerCusDto.getFax());
	        ownercuspo.setInteger("EDUCATION_LEVEL", ownerCusDto.getEducationLevel());
	        ownercuspo.setInteger("OWNER_MARRIAGE", ownerCusDto.getOwnerMarriage());
	        ownercuspo.setInteger("VOCATION_TYPE", ownerCusDto.getVocationType());
	        ownercuspo.setInteger("POSITION_NAME", ownerCusDto.getPositionName());
	        ownercuspo.setInteger("IS_CRPVIP", ownerCusDto.getIsFirstBuy());
	        ownercuspo.setInteger("IS_FIRST_BUY", ownerCusDto.getIsFirstBuy());
	        ownercuspo.setDate("FIRSTDATE_DRIVE", ownerCusDto.getFirstdateDrive());
	        ownercuspo.setInteger("HAS_DRIVER_LICENSE", ownerCusDto.getHasDriverLicense());
	        if (!StringUtils.isNullOrEmpty(StringUtils.listToString(ownerCusDto.getBuyReason(), ','))){
	        ownercuspo.setString("BUY_REASON", StringUtils.listToString(ownerCusDto.getBuyReason(), ','));// 购车因素
	        }
	        ownercuspo.setString("MODIFY_REASON", ownerCusDto.getModifyReason());
	        ownercuspo.setInteger("BEST_CONTACT_TYPE", ownerCusDto.getBestContactType());
	        ownercuspo.setString("DCRC_SERVICE", ownerCusDto.getDcrcService());
	        ownercuspo.setString("LARGE_CUSTOMER_NO", ownerCusDto.getContactorMobile());
	        ownercuspo.setDate("BIRTHDAY", ownerCusDto.getBirthday());
	        ownercuspo.saveIt();
	        
	        
	    }
	public void setOwner(CarownerPO ownerpo, OwnerCusDTO ownerCusDto) {
	    ownerpo.setString("OWNER_NAME", ownerCusDto.getOwnerName());
        if (ownerCusDto.getCustomerType()!=0 &&  ownerCusDto.getCustomerType()!=null&&ownerCusDto.getCustomerType().equals(10181001)){
        ownerpo.setInteger("OWNER_PROPERTY", 11901002);
        }else{
            if (ownerCusDto.getCustomerType().equals(10181002)&& ownerCusDto.getCustomerType()!=0 &&  ownerCusDto.getCustomerType()!=null){
                ownerpo.setInteger("OWNER_PROPERTY", 11901001);
            }
        }
        ownerpo.setString("IS_UPLOAD", "12781002");
        ownerpo.setString("IS_UPLOAD_GROUP", "12781002"); 
	   }
	public void setVehicle(VehiclePO vehiclepo, OwnerCusDTO ownerCusDto) {
	    vehiclepo.setString("IS_UPLOAD", "12781002");
	    vehiclepo.setString("IS_UPLOAD_GROUP", "12781002"); 
	    vehiclepo.setString("IS_UPLOAD_GROUP", "12781002");
	    vehiclepo.setString("IS_SELF_COMPANY", "12781001");
        vehiclepo.setString("SALES_AGENT_NAME", ownerCusDto.getSalesAgentName()); 
     //开关1070   vehiclepo.setString("WrtEndMileage", "12781002");
        vehiclepo.setString("VIN", ownerCusDto.getVin());
     
        vehiclepo.setString("BRAND", ownerCusDto.getBrandId());
        vehiclepo.setString("SERIES", ownerCusDto.getSeriesId());
        vehiclepo.setString("MODEL", ownerCusDto.getIntentModel()); 
        vehiclepo.setString("APACKAGE",ownerCusDto.getApackage());
        vehiclepo.setString("COLOR", ownerCusDto.getColor());
        vehiclepo.setDate("SALES_DATE", ownerCusDto.getSalesdate()); 
        vehiclepo.setDate("LICENSE_DATE", ownerCusDto.getLicenseDate());
        vehiclepo.setString("LICENSE", ownerCusDto.getLisence());
        vehiclepo.setString("CONTRACT_NO", ownerCusDto.getContractNo()); 
        vehiclepo.setInteger("VEHICLE_PURPOSE", ownerCusDto.getVehiclePurpose());
        vehiclepo.setDate("INSURANCE_END_DATE", ownerCusDto.getInsuranceEndDate());
      
        vehiclepo.setString("VEHICLE_PRICE", ownerCusDto.getVehiclePrice());
        vehiclepo.setString("MILEAGE",ownerCusDto.getMileage());
        vehiclepo.setString("SALES_AGENT_NAME", ownerCusDto.getSalesAgentName()); 
        vehiclepo.setString("CONSULTANT", ownerCusDto.getConsultant());
        
	    
	}
	 public TtCustomerLinkmanInfoPO getLinkman(TtCustomerLinkmanInfoDTO Dto,String customerNo,Long intentId){
	     TtCustomerLinkmanInfoPO linkPO = new TtCustomerLinkmanInfoPO();
	     String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
	        //linkPO.setLong("ITEM_ID", intentId);
	        linkPO.setString("CUSTOMER_NO", customerNo);
	        linkPO.setString("DEALER_CODE", dealerCode);
	        linkPO.setString("COMPANY", Dto.getCompany());
	        linkPO.setString("CONTACTOR_DEPARTMENT", Dto.getContactorDepartment());
	        linkPO.setString("CONTACTOR_NAME", Dto.getContactorName());
	        linkPO.setInteger("GENDER", Dto.getGender());
	        linkPO.setInteger("IS_DEFAULT_CONTACTOR", Dto.getIsDefaultContactor());
	        linkPO.setInteger("CONTACTOR_TYPE", Dto.getContactorType());
	        linkPO.setString("POSITION_NAME", Dto.getPositionName());
	        linkPO.setString("PHONE", Dto.getPhone());
	        linkPO.setString("MOBILE", Dto.getMobile());
	        linkPO.setString("E_MAIL", Dto.geteMail());
	        linkPO.setString("FAX", Dto.getFax());
	        linkPO.setInteger("BEST_CONTACT_TYPE", Dto.getBestContactType());
	        linkPO.setInteger("BEST_CONTACT_TIME", Dto.getBestContactTime());
	        linkPO.setString("REMARK", Dto.getRemark());
	        return linkPO;
	    }
	 public TtPoCusLinkmanPO getLinkman1(TtCustomerLinkmanInfoDTO Dto,String customerNo,Long intentId){
	     TtPoCusLinkmanPO linkPO = new TtPoCusLinkmanPO();
            linkPO.setLong("ITEM_ID", intentId);
            linkPO.setString("CUSTOMER_NO", customerNo);
            linkPO.setString("COMPANY", Dto.getCompany());
            linkPO.setString("CONTACTOR_DEPARTMENT", Dto.getContactorDepartment());
            linkPO.setString("CONTACTOR_NAME", Dto.getContactorName());
            linkPO.setInteger("GENDER", Dto.getGender());
            linkPO.setInteger("IS_DEFAULT_CONTACTOR", Dto.getIsDefaultContactor());
            linkPO.setInteger("CONTACTOR_TYPE", Dto.getContactorType());
            linkPO.setString("POSITION_NAME", Dto.getPositionName());
            linkPO.setString("PHONE", Dto.getPhone());
            linkPO.setString("MOBILE", Dto.getMobile());
            linkPO.setString("E_MAIL", Dto.geteMail());
            linkPO.setString("FAX", Dto.getFax());
            linkPO.setInteger("BEST_CONTACT_TYPE", Dto.getBestContactType());
            linkPO.setInteger("BEST_CONTACT_TIME", Dto.getBestContactTime());
            linkPO.setString("REMARK", Dto.getRemark());
            return linkPO;
        }
	 public TmCustomerFamilyPersonPO getFamily(FamilyMenberDTO Dto,String customerNo){
	     TmCustomerFamilyPersonPO familyPO = new TmCustomerFamilyPersonPO();
	     familyPO.setString("CONNECTION_CODE", customerNo);
	     familyPO.setString("BIRTHDAY", Dto.getBirthday());
	     familyPO.setString("CUSTOMER_CODE", Dto.getCustomerCode());
	     familyPO.setInteger("EDUCATION_LEVEL", Dto.getEducationLevel());
	     familyPO.setInteger("GENDER", Dto.getGender());
	     familyPO.setInteger("OWNER_MARRIAGE", Dto.getOwnerMarriage());
	     familyPO.setString("PERSON_NAME", Dto.getCustomerName());
	     familyPO.setString("PHONE", Dto.getPhone());
	     familyPO.setInteger("RELATION", Dto.getRelation());
	  
           
            return familyPO;
        }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusByFamily(String employeeNo, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from TM_CUSTOMER_FAMILY_PERSON where CONNECTION_CODE = ? ");
		List queryParam = new ArrayList();

		queryParam.add(employeeNo);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Map> queryOwnerCusIntent(String customerNo, String dealerCode,String intentId) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.* FROM TT_SALES_PROMOTION_PLAN A " + "WHERE A.dealer_Code='"
                + dealerCode + "' " + "AND A.D_KEY=0 "
                + " AND A.CUSTOMER_NO=? AND A.INTENT_ID=? "
                + " AND (PROM_RESULT<>0 AND PROM_RESULT IS NOT NULL) ");
        List queryParam = new ArrayList();

        queryParam.add(customerNo);
        queryParam.add(intentId);
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
        return result;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Map> queryOwnerCusHistroy(String customerNo, String dealerCode) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT D.dealer_code,D.CUSTOMER_NO,E.INTENT_ID,E.IS_BUDGET_ENOUGH,E.BUDGET_AMOUNT,E.IS_TEST_DRIVE,E.TEST_DRIVE_DATE,E.PURCHASE_TYPE,E.FAIL_TYPE,E.FAIL_BRAND,E.FAIL_MODEL,E.DR_CODE,E.ABORT_REASON,E.INTENT_FINISHED,E.INTENT_FINISH_TIME,E.IS_UPLOAD,E.SUBMIT_TIME ");
        sql.append(" FROM (SELECT A.dealer_code,B.ITEM_ID,C.CUSTOMER_NO,B.PO_CUSTOMER_NO FROM TM_POTENTIAL_CUSTOMER C ,TT_PO_CUS_RELATION B,tm_customer A ");
        sql.append(" WHERE A.dealer_code=B.dealer_code AND A.CUSTOMER_NO=B.CUSTOMER_NO AND B.dealer_code=C.dealer_code AND B.PO_CUSTOMER_NO=C.CUSTOMER_NO AND A.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND A.D_KEY=0 AND A.CUSTOMER_NO=?) D, ");
        sql.append(" TT_CUSTOMER_INTENT E WHERE E.dealer_code=D.dealer_code AND E.CUSTOMER_NO=D.CUSTOMER_NO AND D.dealer_code='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND E.D_KEY=0 ");
        List queryParam = new ArrayList();
        queryParam.add(customerNo);
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Map> queryOwnerCusVehicle(String dealerCode) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.DEALER_CODE,A.VIN,D.BRAND_CODE,D.SERIES_CODE,D.MODEL_CODE,D.CONFIG_CODE,D.COLOR_CODE,D.OEM_TAG FROM TM_VS_STOCK A ,("+CommonConstants.VM_VS_PRODUCT+") D "
                + " WHERE  A.dealer_code='"+dealerCode+"' AND A.D_KEY=0 and A.STOCK_STATUS=13041001 AND A.PRODUCT_CODE = D.PRODUCT_CODE AND A.dealer_code=D.dealer_code AND (STOCK_OUT_TYPE =13241002  or STOCK_OUT_TYPE =13241003 ) "
                + "  AND NOT EXISTS ( SELECT VIN FROM ("+CommonConstants.VM_VEHICLE+") C WHERE  A.VIN = C.VIN AND C.CUSTOMER_NO IS"
                + " NOT NULL AND A.dealer_code = C.dealer_code AND A.dealer_code='"+dealerCode+"' )"); 

        List queryParam = new ArrayList();
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
        return result;
    }
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusByLinkman(String employeeNo, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT L.ITEM_ID,L.CUSTOMER_NO,L.BEST_CONTACT_TYPE,L.BEST_CONTACT_TIME,L.IS_DEFAULT_CONTACTOR,L.CONTACTOR_TYPE,L.COMPANY,L.CONTACTOR_NAME,L.GENDER,L.PHONE,L.MOBILE,L.E_MAIL,L.FAX,L.REMARK,L.CONTACTOR_DEPARTMENT,L.POSITION_NAME,L.DEALER_CODE FROM TT_CUSTOMER_LINKMAN_INFO L WHERE L.DEALER_CODE=? AND L.D_KEY=0 AND L.CUSTOMER_NO=? ");
		List queryParam = new ArrayList();
		queryParam.add(dealerCode);
		queryParam.add(employeeNo);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusByInsurance(String vin, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT A.*  FROM (" + CommonConstants.VM_SERVICE_INSURANCE
				+ ") A  WHERE  A.D_KEY = 0 AND A.VIN = ?  AND A.IS_VALID = 12781001 ");
		List queryParam = new ArrayList();
		queryParam.add(vin);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusByTreat(String employeeNo, String vin, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from (SELECT 12781002 AS IS_SELECTED,0 AS IS_SELECT, S.ITEM_ID,S.CUSTOMER_NO,T.CUSTOMER_NAME,S.VIN,S.dealer_code,S.CR_NAME,S.CR_TYPE,S.SCHEDULE_DATE,"
						+ "S.ACTION_DATE,S.CR_SCENE,S.CR_CONTEXT,S.CR_RESULT,S.CR_LINKER,S.NEXT_CR_DATE,S.NEXT_CR_CONTEXT,S.CREATE_TYPE,S.LINK_PHONE,S.LINK_MOBILE,S.SOLD_BY,S.OWNED_BY "
						+ ",S.TRANCE_TIME,S.TRANCE_USER,T.ADDRESS FROM TT_SALES_CR S,TM_CUSTOMER T WHERE S.CUSTOMER_NO=T.CUSTOMER_NO and s.dealer_code=t.dealer_code AND S.dealer_code=? "
						+ "AND S.D_KEY=0  AND S.VIN LIKE '%" + vin
						+ "%' AND S.CUSTOMER_NO=?  ORDER BY T.CUSTOMER_NAME ) P  ");
		List queryParam = new ArrayList();
		queryParam.add(dealerCode);
		queryParam.add(employeeNo);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerName(String ownerNo, String vin, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT OW.dealer_CODE,OW.OWNER_NO,OW.OWNER_NAME FROM  (" + CommonConstants.VM_OWNER
				+ ") OW WHERE OW.OWNER_NO=? ");
		List queryParam = new ArrayList();
		queryParam.add(ownerNo);
		System.err.println(sql);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerVehicle(String employeeNo, String vin, String dealerCode) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT VE.VIN,VE.OWNER_NO,VE.DEALER_CODE,VE.BRAND as BRAND_CODE,VE.SERIES as SERIES_CODE ,VE.MODEL as MODEL_CODE ,VE.COLOR ,VE.APACKAGE AS CONFIG_CODE,VE.SALES_DATE,VE.LICENSE_DATE,VE.LICENSE,VE.CONTRACT_NO,VE.VEHICLE_PURPOSE,VE.INSURANCE_END_DATE,VE.CONSULTANT,VE.MILEAGE,VE.VEHICLE_PRICE,ve.SALES_AGENT_NAME,ve.SUBMIT_STATUS, (CASE WHEN sa.FIRST_STOCK_OUT_DATE IS NULL THEN sa.LATEST_STOCK_OUT_DATE ELSE sa.FIRST_STOCK_OUT_DATE END) AS STOCK_OUT_DATE  FROM  ("
						+ CommonConstants.VM_VEHICLE
						+ ") VE  left join TM_VS_STOCK sa on sa.vin=ve.vin and sa.DEALER_CODE=ve.DEALER_CODE and sa.STOCK_STATUS=13041001 WHERE VE.VIN=? AND ve.dealer_code = ? ");
		List queryParam = new ArrayList();
		queryParam.add(vin);
		queryParam.add(dealerCode);
		System.err.println(sql);
		List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
		return result;

	}
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> queryOwnerCusforExport(Map<String, String> queryParam) throws ServiceBizException {

	        StringBuffer sb = new StringBuffer();
	       
	        sb.append("SELECT I.* FROM ( SELECT "   
	                + " e.SALES_AGENT_NAME,e.IS_DIRECT,e.IS_CONSIGNED,E.SUBMIT_STATUS,E.APACKAGE,E.EXCEPTION_CAUSE, E.VIN, E.OWNER_NO,E.IS_UPLOAD AS VIS_UPLOAD,E.COLOR_CODE,E.LICENSE,E.ORDER_SUM,/*F.CAR_AGE,*/F.OEM_TAG,C.DEALER_CODE,C.CUSTOMER_NO,C.CUSTOMER_NAME,"
	                + "C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,C.ZIP_CODE,C.BUY_PURPOSE,"
	                + "C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.E_MAIL,C.HOBBY,C.OWNER_MARRIAGE,C.AGE_STAGE,C.FAMILY_INCOME,C.POSITION_NAME,C.VOCATION_TYPE,C.EDUCATION_LEVEL,"
	                + "C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.IS_WHOLESALER,C.CT_CODE,C.CERTIFICATE_NO,"
	                + "C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.IS_REMOVED,"
	                + "C.DELAY_CONSULTANT,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,C.CUS_SOURCE,"
	                + "C.MEDIA_TYPE,C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,"
	                + "C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,C.FIRSTDATE_DRIVE,C.MARRY_DATE,C.IS_MESSAGED,"
	                + "C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.OWNED_BY,C.FOUND_DATE,E.SUBMIT_TIME,E.MODEL,C.IS_UPLOAD,C.LARGE_CUSTOMER_NO,C.ORGAN_TYPE,E.SALES_DATE,C.DCRC_SERVICE,C.ORGAN_TYPE_CODE,C.LAST_SOLD_BY,"
	                + "C.BUY_REASON,C.IS_FIRST_BUY,C.REMARK,C.CONSULTANT_TIME,C.BEST_CONTACT_TYPE,E.CONFIRMED_DATE,E.BRAND,E.SERIES,E.PRODUCT_CODE ,e.LICENSE_DATE,E.SO_NO,e.BUSINESS_TYPE,TA.INVOICE_CUSTOMER" 
	                + " FROM TM_CUSTOMER  C "
	                + " LEFT JOIN (SELECT  M.SALES_AGENT_NAME,M.LICENSE_DATE,M.customer_no,M.VIN AS VIN,M.OWNER_NO AS OWNER_NO,M.IS_UPLOAD AS IS_UPLOAD,H.SO_NO," +
	                        "M.SALES_DATE,M.SUBMIT_TIME,M.BRAND,M.SERIES,M.MODEL,M.APACKAGE,H.ORDER_SUM,H.CONFIRMED_DATE,f.PRODUCT_CODE,F.COLOR_CODE,M.LICENSE,M.SUBMIT_STATUS,M.EXCEPTION_CAUSE,sk.IS_DIRECT,H.BUSINESS_TYPE,H.SO_STATUS,CASE WHEN H.BUSINESS_TYPE =13001003  THEN 12781001  ELSE 12781002 "
	                    + "  END  as IS_CONSIGNED  FROM ("+CommonConstants.VM_VEHICLE+") M, " +
	                            "(  select distinct so.DEALER_CODE,so.vin,so.SO_NO,so.CONFIRMED_DATE,so.BUSINESS_TYPE,so.so_status,so.product_code,so.order_sum,so.CUSTOMER_NO from TT_SALES_ORDER so, TT_PO_CUS_RELATION cr" +
	                            " WHERE cr.DEALER_CODE=so.DEALER_CODE AND cr.PO_CUSTOMER_NO=so.CUSTOMER_NO  union all" +
	                            " select t.DEALER_CODE,vin,t.SEC_SO_NO as SO_NO,CONFIRMED_DATE, t.Bill_TYpe as BUSINESS_TYPE,t.so_status,t1.product_code,t.order_sum,t.CUSTOMER_NO from TT_SECOND_SALES_ORDER t,TT_SEC_SALES_ORDER_ITEM t1 " +
	                            " where t.SEC_SO_NO = t1.SEC_SO_NO AND t.CUSTOMER_NO NOT IN (SELECT PO_CUSTOMER_NO FROM TT_PO_CUS_RELATION WHERE DEALER_CODE = t.DEALER_CODE) and t1.IS_PUTINTO_SALEINFO = 12781001 " +
	                            " AND vin not in(SELECT VIN FROM TT_SEC_SALES_ORDER_ITEM s INNER JOIN TT_SECOND_SALES_ORDER s1 ON s.SEC_SO_NO = s1.SEC_SO_NO " +
	                            " where s1.BILL_Type = 19001002 and s1.OLD_SEC_SO_NO = t1.SEC_SO_NO))H,("+CommonConstants.VM_VS_PRODUCT+") F,TM_VS_STOCK SK WHERE M.DEALER_CODE='"
	                + FrameworkUtil.getLoginInfo().getDealerCode()
	                + "' AND M.DEALER_CODE=H.DEALER_CODE AND M.VIN=H.VIN AND (H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_CLOSED+" "//将已完成改为已关单
	                + "  OR H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED+" OR H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+") "
	                +"AND H.BUSINESS_TYPE in ("+DictCodeConstants.DICT_SO_TYPE_GENERAL+","+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+","+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+")"
	                + "  AND F.PRODUCT_CODE=H.PRODUCT_CODE AND H.DEALER_CODE=F.DEALER_CODE " 
	                +"  AND SK.VIN=m.vin and sk.vin=h.vin and h.PRODUCT_CODE=sk.PRODUCT_CODE and sk.PRODUCT_CODE=f.PRODUCT_CODE and sk.DEALER_CODE=m.DEALER_CODE "
	                +" )  E  ON E.customer_no=C.customer_no " +
	                " LEFT JOIN (SELECT INVOICE_CUSTOMER,VIN FROM TT_SO_INVOICE  WHERE INVOICE_CHARGE_TYPE=13181001 AND (IS_VALID!="+DictCodeConstants.IS_NOT+" OR IS_VALID IS NULL))TA on E.VIN=TA.VIN "
	                + "LEFT JOIN (SELECT COUNT(1) AS CAR_COUNT,CUSTOMER_NO FROM ("+CommonConstants.VM_VEHICLE+") X WHERE X.DEALER_CODE='"
	                + FrameworkUtil.getLoginInfo().getDealerCode()
	                + "' GROUP BY X.CUSTOMER_NO) G ON G.CUSTOMER_NO=C.CUSTOMER_NO "
	                + "LEFT JOIN (SELECT TRUNCATE(DATEDIFF(CURDATE(),DATE(LATEST_STOCK_OUT_DATE))/356,1)  AS CAR_AGE,vin  AS VIN,OEM_TAG AS OEM_TAG  FROM TM_VS_STOCK WHERE DEALER_CODE='"
	                + FrameworkUtil.getLoginInfo().getDealerCode() + "') F  on F.VIN=E.VIN  " + " WHERE  C.DEALER_CODE='"
	                + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND C.D_KEY=0");
	        List<Object> queryList = new ArrayList<Object>();
	        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
	            sb.append(" and C.CUSTOMER_NAME like ?");
	            queryList.add("%" + queryParam.get("customerName") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sb.append(" and E.VIN like ?");
	            queryList.add("%" + queryParam.get("vin") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("carCount"))) {
	            sb.append(" and G.CAR_COUNT = ?");
	            queryList.add(queryParam.get("carCount"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("carAge"))) {
	            sb.append(" and F.CAR_AGE = ?");
	            queryList.add(queryParam.get("carAge"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
	            sb.append(" and C.CONTACTOR_PHONE like ?");
	            queryList.add("%" + queryParam.get("contactorPhone") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
	            sb.append(" and C.CONTACTOR_MOBILE  = ?");
	            queryList.add("%" + queryParam.get("contactorMobile") + "%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("sales_startdate"))) {
	            sb.append(" and E.SALES_DATE >= ?");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("sales_startdate")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("sales_enddate"))) {
	            sb.append(" and E.SALES_DATE <= ? ");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("sales_enddate")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("firstFoundDate"))) {
	            sb.append(" and  C.FOUND_DATE >= ?");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("firstFoundDate")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("endFoundDate"))) {
	            sb.append(" and  C.FOUND_DATE <= ? ");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("endFoundDate")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("firstSubmitTime"))) {
	            sb.append(" and  E.SUBMIT_TIME >= ?");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("firstSubmitTime")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("endSubmitTime"))) {
	            sb.append(" and  E.SUBMIT_TIME <= ? ");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("endSubmitTime")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("firstConfirmedDate"))) {
	            sb.append(" and  E.CONFIRMED_DATE >= ?");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("firstConfirmedDate")));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("endConfirmedDate"))) {
	            sb.append(" and  E.CONFIRMED_DATE <= ? ");
	            queryList.add(DateUtil.parseDefaultDate(queryParam.get("endConfirmedDate")));
	        }
	        
	        if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
	            sb.append(" and E.BRAND = ?");
	            queryList.add(queryParam.get("brand"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
	            sb.append(" and E.SERIES = ?");
	            queryList.add(queryParam.get("series"));
	        }

	        if (!StringUtils.isNullOrEmpty(queryParam.get("visUpload"))) {
	            sb.append(" and E.IS_UPLOAD = ?");
	            queryList.add(queryParam.get("visUpload"));
	        }

	        if (!StringUtils.isNullOrEmpty(queryParam.get("province"))) {
	            sb.append(" and C.PROVINCE = ?");
	            queryList.add(queryParam.get("province"));
	        }

	        if (!StringUtils.isNullOrEmpty(queryParam.get("city"))) {
	            sb.append(" and C.CITY = ?");
	            queryList.add(queryParam.get("city"));
	        }

	        if (!StringUtils.isNullOrEmpty(queryParam.get("county"))) {
	            sb.append(" and C.DISTRICT = ?");
	            queryList.add(queryParam.get("county"));
	        }

	        if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
	            sb.append(" and E.MODEL_CODE = ?");
	            queryList.add(queryParam.get("model"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
	            sb.append(" and C.SOLD_BY = ?");
	            queryList.add(queryParam.get("consultant"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("submitStatus"))) {
	            sb.append(" and E.SUBMIT_STATUS =?");
	            queryList.add(queryParam.get("submitStatus"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("isConsigned"))) {
	            if ((queryParam.get("isConsigned")).trim().equals("12781001")){
	                sb.append(" AND e.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+" "); 
	            }else{
	                sb.append(" AND e.BUSINESS_TYPE != "+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+" ");        
	            }
	           
	        }
	        
	        if (!StringUtils.isNullOrEmpty(queryParam.get("isDirect"))) {   
	            if((queryParam.get("isDirect")).trim().equals(12781001)){
	                sb.append(" AND e.IS_DIRECT =? ");  
	            }else{
	                sb.append(" AND ((e.IS_DIRECT is null) or  (e.IS_DIRECT=? )   )");  
	                 
	            }
	             queryList.add(queryParam.get("isDirect"));
	        }       
	        sb.append("  AND (E.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_CLOSED+" OR E.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED+" OR E.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" ");
	        sb.append("  )  AND E.BUSINESS_TYPE IN("+DictCodeConstants.DICT_SO_TYPE_GENERAL+","+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+","+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+") ORDER BY C.CUSTOMER_NO,C.CREATED_AT) I");     
	        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);

	     /*  for(Map map : resultList ) {
	    	
	        	if (map.get("SUBMIT_STATUS") != null && map.get("SUBMIT_STATUS") != "") {
	                if (Integer.parseInt(map.get("SUBMIT_STATUS").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("SUBMIT_STATUS","是");
	                } else if (Integer.parseInt(map.get("SUBMIT_STATUS").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("SUBMIT_STATUS","否");
	                }
	            }
	        	
	        	if (map.get("VIS_UPLOAD") != null && map.get("VIS_UPLOAD") != "") {
	                if (Integer.parseInt(map.get("VIS_UPLOAD").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("VIS_UPLOAD", "是");
	                } else if (Integer.parseInt(map.get("VIS_UPLOAD").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("VIS_UPLOAD", "否");
	                }
	            }
	        	
	        	if (map.get("IS_FIRST_BUY") != null && map.get("IS_FIRST_BUY") != "") {
	                if (Integer.parseInt(map.get("IS_FIRST_BUY").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("IS_FIRST_BUY", "是");
	                } else if (Integer.parseInt(map.get("IS_FIRST_BUY").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("IS_FIRST_BUY", "否");
	                }
	            }
	        	
	        	if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
	                if (map.get("CUSTOMER_TYPE").toString() == DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL) {
	                    map.put("CUSTOMER_TYPE", "个人");
	                } else if (map.get("CUSTOMER_TYPE").toString() == DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY) {
	                    map.put("CUSTOMER_TYPE", "公司");
	                }
	            }
	        	
	        	if (map.get("CT_CODE") != null && map.get("CT_CODE") != "") {
	                if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD) {
	                    map.put("CT_CODE", "居民省份证");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_PASSPORT) {
	                    map.put("CT_CODE", "护照");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_OFFICER) {
	                    map.put("CT_CODE", "军官");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_SOLDIER) {
	                    map.put("CT_CODE", "士兵");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_POLICE_OFFICER) {
	                    map.put("CT_CODE", "警官");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_OTHER) {
	                    map.put("CT_CODE", "其他");
	                } else if (map.get("CT_CODE").toString() == DictCodeConstants.DICT_CERTIFICATE_TYPE_ORGAN_CODE) {
	                    map.put("CT_CODE", "机构代码");
	                } 
	            }
	          }*/
	       
	        
	        OperateLogDto operateLogDto=new OperateLogDto();
	        operateLogDto.setOperateContent("保客导出");
	        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
	        operateLogService.writeOperateLog(operateLogDto);
	        return resultList;
	    }
    
    /**
    * @author LiGaoqi
    * @date 2017年3月20日
    * @param ownerCusDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.OwnerCusService#modifySoldBy(com.yonyou.dms.common.domains.DTO.basedata.OwnerCusDTO)
    */
    	
    @Override
    public void modifySoldBy(OwnerCusDTO ownerCusDto) throws ServiceBizException {
        String[] ids = ownerCusDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
            CustomerPO cuspo = CustomerPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
            List<Object> salesPromotionList2 = new ArrayList<Object>();
            salesPromotionList2.add(no);
            salesPromotionList2.add(DictCodeConstants.D_KEY);
            salesPromotionList2.add(loginInfo.getDealerCode());
            List<TtSalesCrPO> salesPromotionPO2=TtSalesCrPO.findBySQL("select * from TT_SALES_CR where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND DATE(SCHEDULE_DATE) >= CURRENT_DATE AND (CR_RESULT IS NULL OR CR_RESULT = 0) ", salesPromotionList2.toArray());
            if(salesPromotionPO2!=null&&salesPromotionPO2.size()>0){
                for(int j=0;j<salesPromotionPO2.size();j++){
                    TtSalesCrPO planPO = salesPromotionPO2.get(j);
                    planPO.setString("SOLD_BY", ownerCusDto.getSoldBy().toString());
                    planPO.setString("OWNED_BY", ownerCusDto.getSoldBy().toString());
                    planPO.saveIt();
                }
            }
            System.out.println(ownerCusDto.getIsCustomer());
            System.out.println(ownerCusDto.getSoldBy().toString());
            if(!StringUtils.isNullOrEmpty(ownerCusDto.getIsCustomer())&&ownerCusDto.getIsCustomer().equals("12781001")){
                // 使用再分配功能将同时分配保有客户相同的D级潜客
                if(cuspo!=null&&!StringUtils.isNullOrEmpty(cuspo.getString("CUSTOMER_NO"))){
                    //PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),cuspo.getString("CUSTOMER_NO"));
                	PotentialCusPO potentialCusPo = new PotentialCusPO();
                	String customerNo = cuspo.getString("CUSTOMER_NO");
                	String dealerCode = loginInfo.getDealerCode();
                	List<PotentialCusPO> list = PotentialCusPO.findBySQL(" SELECT p.* FROM TM_POTENTIAL_CUSTOMER p LEFT JOIN TT_PO_CUS_RELATION pc  ON p.CUSTOMER_NO = pc.po_CUSTOMER_NO AND p.`DEALER_CODE`= pc.`DEALER_CODE` LEFT JOIN tm_customer c ON c.CUSTOMER_NO = pc.CUSTOMER_NO AND c.dealer_code = pc.`DEALER_CODE` WHERE c.`CUSTOMER_NO` = '"+customerNo+"' AND c.`DEALER_CODE` = '"+dealerCode+"' ", null);
                	if(!StringUtils.isNullOrEmpty(list)) {
                		potentialCusPo = list.get(0);
                	}
                	if(potentialCusPo!=null){
                        String soldBy = potentialCusPo.getString("SOLD_BY");
                        String tmpCusNo = potentialCusPo.getString("CUSTOMER_NO");
                        potentialCusPo.setString("SOLD_BY", ownerCusDto.getSoldBy().toString());
                        potentialCusPo.setString("OWNED_BY", ownerCusDto.getSoldBy().toString());
                        potentialCusPo.setString("LAST_SOLD_BY", soldBy);
                        potentialCusPo.setDate("CONSULTANT_TIME",new Date());
                        potentialCusPo.saveIt();
                        List<Object> salesPromotionList = new ArrayList<Object>();
                        salesPromotionList.add(tmpCusNo);
                        salesPromotionList.add(DictCodeConstants.D_KEY);
                        salesPromotionList.add(loginInfo.getDealerCode());          
                        List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND DATE(SCHEDULE_DATE) >= CURRENT_DATE AND (PROM_RESULT IS NULL OR PROM_RESULT = 0 OR PROM_RESULT ='13341009') ", salesPromotionList.toArray());
                        if(StringUtils.isNullOrEmpty(salesPromotionPO2)&&salesPromotionPO2.size()>0){
                           for(int p=0;p<salesPromotionPO2.size();p++){
                               TtSalesPromotionPlanPO planPO2 = salesPromotionPO.get(p);
                               planPO2.setString("SOLD_BY", ownerCusDto.getSoldBy().toString());
                               planPO2.setString("OWNED_BY", ownerCusDto.getSoldBy().toString());
                               planPO2.saveIt();
                           }
                        }
                        
                    }
                }
            }
            // 更新保有客户分配前的销售顾问
            String customersoldBy = cuspo.getString("SOLD_BY");
            cuspo.setString("LAST_SOLD_BY", customersoldBy);
            cuspo.setInteger("IS_UPLOAD", 12781002);
            cuspo.setDate("CONSULTANT_TIME", new Date());
            cuspo.setString("SOLD_BY", ownerCusDto.getSoldBy().toString());
            cuspo.setString("OWNED_BY", ownerCusDto.getSoldBy().toString());
            cuspo.saveIt();
            
        } 
        
    }
	 
}
