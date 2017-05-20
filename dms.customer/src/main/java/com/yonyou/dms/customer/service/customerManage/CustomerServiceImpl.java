
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月16日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.RetainCustomersDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 保有客户维护
 * 
 * @author zhanshiwei
 * @date 2016年8月16日
 */
@Service
public class CustomerServiceImpl implements CustomerService {
	 @Autowired
	 private OperateLogService operateLogService;

    /**
     * 保有客户查询
     * 
     * @author zhanshiwei
     * @date 2016年8月16日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CustomerService#queryCustomerInnfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryCustomerInnfo(Map<String, String> queryParam,String DealerCode) throws ServiceBizException {
       String empno=FrameworkUtil.getLoginInfo().getEmployeeNo();
       Long userid = FrameworkUtil.getLoginInfo().getUserId();
       String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT o.`CODE_CN_DESC` AS PROVINCE1 ,C.`CODE_CN_DESC` AS city1,d.`CODE_CN_DESC` AS district1,B.*,mo.MODEL_NAME  FROM ( ");
        sb.append("SELECT I.* FROM ( SELECT "	
				+ " e.SALES_AGENT_NAME,e.IS_DIRECT,e.IS_CONSIGNED,E.SUBMIT_STATUS,E.APACKAGE,E.EXCEPTION_CAUSE, E.VIN, E.OWNER_NO,E.IS_UPLOAD AS VIS_UPLOAD,E.COLOR_CODE,E.LICENSE,E.ORDER_SUM,/*F.CAR_AGE,*/F.OEM_TAG,C.DEALER_CODE,C.CUSTOMER_NO,C.CUSTOMER_NAME,"
				+ "C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,C.ZIP_CODE,C.BUY_PURPOSE,E.PO_CUSTOMER_NO,"
				+ "C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.E_MAIL,C.HOBBY,C.OWNER_MARRIAGE,C.AGE_STAGE,C.FAMILY_INCOME,C.POSITION_NAME,C.VOCATION_TYPE,C.EDUCATION_LEVEL,"
				+ "C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.IS_WHOLESALER,C.CT_CODE,C.CERTIFICATE_NO,"
				+ "C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.IS_REMOVED,"
				+ "C.DELAY_CONSULTANT,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,C.CUS_SOURCE,"
				+ "C.MEDIA_TYPE,C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,"
				+ "C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,C.FIRSTDATE_DRIVE,C.MARRY_DATE,C.IS_MESSAGED,"
				+ "C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.OWNED_BY,C.FOUND_DATE,EM.USER_NAME,E.SUBMIT_TIME,E.MODEL,C.IS_UPLOAD,C.LARGE_CUSTOMER_NO,C.ORGAN_TYPE,E.SALES_DATE,C.DCRC_SERVICE,C.ORGAN_TYPE_CODE,C.LAST_SOLD_BY,"
				+ "C.BUY_REASON,C.IS_FIRST_BUY,C.REMARK,C.CONSULTANT_TIME,C.BEST_CONTACT_TYPE,E.CONFIRMED_DATE,E.BRAND,E.SERIES,E.PRODUCT_CODE ,e.LICENSE_DATE,E.SO_NO,e.BUSINESS_TYPE,TA.INVOICE_CUSTOMER" 
				+ " FROM TM_CUSTOMER  C LEFT JOIN TM_USER EM ON EM.USER_ID=C.SOLD_BY AND EM.DEALER_CODE=C.DEALER_CODE"
				+ " LEFT JOIN (SELECT  M.SALES_AGENT_NAME,M.LICENSE_DATE,M.customer_no,M.VIN AS VIN,M.OWNER_NO AS OWNER_NO,M.IS_UPLOAD AS IS_UPLOAD,H.SO_NO," +
						"M.SALES_DATE,M.SUBMIT_TIME,M.BRAND,M.SERIES,M.MODEL,M.APACKAGE,H.PO_CUSTOMER_NO,H.ORDER_SUM,H.CONFIRMED_DATE,f.PRODUCT_CODE,F.COLOR_CODE,M.LICENSE,M.SUBMIT_STATUS,M.EXCEPTION_CAUSE,sk.IS_DIRECT,H.BUSINESS_TYPE,H.SO_STATUS,CASE WHEN H.BUSINESS_TYPE =13001003  THEN 12781001  ELSE 12781002 "
					+ "  END  as IS_CONSIGNED  FROM ("+CommonConstants.VM_VEHICLE+") M, " +
							"(  select distinct so.DEALER_CODE,cr.PO_CUSTOMER_NO,so.vin,so.SO_NO,so.CONFIRMED_DATE,so.BUSINESS_TYPE,so.so_status,so.product_code,so.order_sum,so.CUSTOMER_NO from TT_SALES_ORDER so, TT_PO_CUS_RELATION cr" +
							" WHERE cr.DEALER_CODE=so.DEALER_CODE AND cr.PO_CUSTOMER_NO=so.CUSTOMER_NO  union all" +
							" select t.DEALER_CODE,'' as PO_CUSTOMER_NO,vin,t.SEC_SO_NO as SO_NO,CONFIRMED_DATE, t.Bill_TYpe as BUSINESS_TYPE,t.so_status,t1.product_code,t.order_sum,t.CUSTOMER_NO from TT_SECOND_SALES_ORDER t,TT_SEC_SALES_ORDER_ITEM t1 " +
				         	" where t.SEC_SO_NO = t1.SEC_SO_NO AND t.CUSTOMER_NO NOT IN (SELECT PO_CUSTOMER_NO FROM TT_PO_CUS_RELATION WHERE DEALER_CODE = t.DEALER_CODE) and t1.IS_PUTINTO_SALEINFO = 12781001 " +
				         	" AND vin not in(SELECT VIN FROM TT_SEC_SALES_ORDER_ITEM s INNER JOIN TT_SECOND_SALES_ORDER s1 ON s.SEC_SO_NO = s1.SEC_SO_NO " +
							" where s1.BILL_Type = 19001002 and s1.OLD_SEC_SO_NO = t1.SEC_SO_NO))H,("+CommonConstants.VM_VS_PRODUCT+") F,TM_VS_STOCK SK WHERE M.DEALER_CODE='"
				+ DealerCode
				+ "' AND M.DEALER_CODE=H.DEALER_CODE AND M.VIN=H.VIN AND (H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_CLOSED+" "//将已完成改为已关单
				+ "  OR H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED+" OR H.SO_STATUS="+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" ) "
				+"AND H.BUSINESS_TYPE in ("+DictCodeConstants.DICT_SO_TYPE_GENERAL+","+DictCodeConstants.DICT_SO_TYPE_ALLOCATION+","+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+")"
				+ "  AND F.PRODUCT_CODE=H.PRODUCT_CODE AND H.DEALER_CODE=F.DEALER_CODE " 
				+"  AND SK.VIN=m.vin and sk.vin=h.vin and h.PRODUCT_CODE=sk.PRODUCT_CODE and sk.PRODUCT_CODE=f.PRODUCT_CODE and sk.DEALER_CODE=m.DEALER_CODE "
				+" )  E  ON E.customer_no=C.customer_no " +
				" LEFT JOIN (SELECT INVOICE_CUSTOMER,VIN FROM TT_SO_INVOICE  WHERE INVOICE_CHARGE_TYPE=13181001 AND (IS_VALID!="+DictCodeConstants.IS_NOT+" OR IS_VALID IS NULL))TA on E.VIN=TA.VIN "
				+ "LEFT JOIN (SELECT COUNT(1) AS CAR_COUNT,CUSTOMER_NO FROM ("+CommonConstants.VM_VEHICLE+") X WHERE X.DEALER_CODE='"
				+ DealerCode
				+ "' GROUP BY X.CUSTOMER_NO) G ON G.CUSTOMER_NO=C.CUSTOMER_NO "
				+ "LEFT JOIN (SELECT TRUNCATE(DATEDIFF(CURDATE(),DATE(LATEST_STOCK_OUT_DATE))/356,1)  AS CAR_AGE,vin  AS VIN,OEM_TAG AS OEM_TAG  FROM TM_VS_STOCK WHERE DEALER_CODE='"
				+ DealerCode + "') F  on F.VIN=E.VIN  " + " WHERE  C.DEALER_CODE='"
				+ DealerCode + "' AND C.D_KEY=0");
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            System.out.println(queryParam.get("soldBy"));
            sb.append(" and C.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }else
        {
            if (empno!=null && !"9997".equals(empno)){
             
                sb.append(DAOUtilGF.getOwnedByStr( "C", userid, orgCode,"45751000", FrameworkUtil.getLoginInfo().getDealerCode()));
            }
            
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
        sb.append("  )  B  LEFT JOIN tc_code C   ON  B.city = c.CODE_ID LEFT JOIN tc_code O ON B.province = O.CODE_ID LEFT JOIN tc_code D ON d.CODE_ID = B.district LEFT  JOIN   TM_MODEL   mo   ON   B.MODEL =mo.MODEL_CODE and B.DEALER_CODE=mo.DEALER_CODE AND B.SERIES= mo.SERIES_CODE AND B.BRAND= mo.BRAND_CODE");
        System.out.println("********************************");
        System.out.println(sb.toString());
        System.out.println("********************************");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),queryList);
        return pageInfoDto;
    }

    /**
     * 查询条件设置
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and cu.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" and ve.LICENSE like ?");
            queryList.add("%" + queryParam.get("license") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" and ow.OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))) {
            sb.append(" and ow.OWNER_PROPERTY  = ?");
            queryList.add(Integer.parseInt(queryParam.get("ownerProperty")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("sales_startdate"))) {
            sb.append(" and ve.SALES_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("sales_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("sales_enddate"))) {
            sb.append(" and ve.SALES_DATE < ? ");
            queryList.add(DateUtil.addOneDay(queryParam.get("sales_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and ve.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
            sb.append(" and ve.BRAND_CODE = ?");
            queryList.add(queryParam.get("brandCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" and ve.SERIES_CODE = ?");
            queryList.add(queryParam.get("seriesCode"));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" and ve.MODEL_CODE = ?");
            queryList.add(queryParam.get("modelCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("configCode"))) {
            sb.append(" and ve.CONFIG_CODE = ?");
            queryList.add(queryParam.get("configCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and CONSULTANT = ?");
            queryList.add(queryParam.get("consultant"));
        }
    }

    /**
     * 根据车辆ID查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CustomerService#queryCustomerVehicleInfoByid(long)
     */

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> queryCustomerVehicleInfoByid(long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT ve.VEHICLE_ID,ve.DEALER_CODE,ve.OWNER_ID,cu.CUSTOMER_ID,ve.VIN,ve.LICENSE,ve.BRAND_CODE,ve.SERIES_CODE,ve.MODEL_CODE,ve.CONFIG_CODE,ve.PRODUCT_CODE,ve.COLOR,\n");
        sb.append("       ve.LICENSE_DATE,ve.SALES_DATE,ve.VEHICLE_PURPOSE,ve.CONSULTANT,ve.DCRC_ADVISOR,\n");
        sb.append("       ow.OWNER_NAME,ow.OWNER_PROPERTY,ow.GENDER,ow.PHONE,ow.MOBILE,ow.CT_CODE,ow.CERTIFICATE_NO,\n");
        sb.append("       ow.PROVINCE,ow.CITY,ow.DISTRICT,ow.ADDRESS,ow.ZIP_CODE,ow.E_MAIL,ow.BIRTHDAY,ow.OWNER_MARRIAGE,\n");
        sb.append("       ow.EDU_LEVEL,ow.HOBBY,ow.FAMILY_INCOME,\n");
        sb.append("       cu.CUSTOMER_NO,cu.STOCK_OUT_DATE,cu.INSURANCE_EXPIRE_DATE,cu.VEHICLE_PRICE,cu.CONTRACT_NO,\n");
        sb.append("       cu.CUS_SOURCE,cu.MEDIA_TYPE,cu.BUY_PURPOSE,cu.IS_FIRST_BUY,cu.BUY_REASON\n");
        sb.append("FROM tm_vehicle ve\n");
        sb.append("left join TM_CUSTOMER cu on ve.VEHICLE_ID=cu.VEHICLE_ID\n");
        sb.append("left join TM_OWNER   ow on  ve.OWNER_ID=ow.OWNER_ID\n");
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" where ve.VEHICLE_ID = ?");
        queryList.add(id);
        Map<String, Object> cusVehMap = DAOUtil.findFirst(sb.toString(), queryList);
        return cusVehMap;
    }

    /**
     * 保有客户信息修改
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param id
     * @param tetainDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CustomerService#modifyCustomerVehicleInfo(long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.RetainCustomersDTO)
     */
    @Override
    public Map<String, Object> modifyRetainCusVehicleInfo(long vehicleId, RetainCustomersDTO tetainDto,
                                                          String customerNo) throws ServiceBizException {
        VehiclePO velPo = VehiclePO.findById(vehicleId);
        tetainDto.setVehicleId(vehicleId);
        this.setVehicle(velPo, tetainDto);
        velPo.saveIt();
        if (!StringUtils.isNullOrEmpty(velPo.get("owner_id"))) {
            CarownerPO ownPo = CarownerPO.findById(velPo.get("owner_id"));
            this.setCarowner(ownPo, tetainDto);
            ownPo.saveIt();
        }
        if (!StringUtils.isNullOrEmpty(tetainDto.getCustomerId())) {
            CustomerPO cusPo = CustomerPO.findById(tetainDto.getCustomerId());
            this.setCustomer(cusPo, tetainDto);
            cusPo.saveIt();
        } else {
            CustomerPO cusPo = new CustomerPO();
            this.setCustomer(cusPo, tetainDto);
            cusPo.saveIt();
        }
        return null;
    }

    /**
     * 设置车主信息PO属性
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param ownPo
     * @param ownDto
     */

    private void setCarowner(CarownerPO ownPo, RetainCustomersDTO tetainDto) {
        ownPo.setString("OWNER_NAME", tetainDto.getOwnerName());// 车主名称
        ownPo.setInteger("OWNER_PROPERTY", tetainDto.getOwnerProperty());// 车主性质
        ownPo.setInteger("GENDER", tetainDto.getGender());// 车主性别
        ownPo.setString("PHONE", tetainDto.getPhone());// 电话
        ownPo.setString("MOBILE", tetainDto.getMobile());// 手机
        ownPo.setInteger("CT_CODE", tetainDto.getCtCode());// 证件类别
        ownPo.setString("CERTIFICATE_NO", tetainDto.getCertificateNo());// 证件号
        ownPo.setString("PROVINCE", tetainDto.getProvince());// 省份
        ownPo.setString("CITY", tetainDto.getCity());// 城市
        ownPo.setString("DISTRICT", tetainDto.getDistrict());// 区县
        ownPo.setString("ADDRESS", tetainDto.getAddress());// 地址
        ownPo.setString("ZIP_CODE", tetainDto.getZipCode());// 邮编
        ownPo.setString("HOBBY", tetainDto.getHobby());// 爱好

        ownPo.setString("E_MAIL", tetainDto.geteMail());// 邮箱
        ownPo.setDate("BIRTHDAY", tetainDto.getBirthday());// 生日
        ownPo.setInteger("FAMILY_INCOME", tetainDto.getFamilyIncome());// 月收入
        ownPo.setInteger("EDU_LEVEL", tetainDto.getEduLevel());// 学历
        ownPo.setInteger("OWNER_MARRIAGE", tetainDto.getOwnerMarriage());// 婚姻状况

    }

    /**
     * 设置车辆PO属性
     * 
     * @author Administrator
     * @date 2016年8月17日
     * @param velPo
     * @param tetainDto
     */

    private void setVehicle(VehiclePO velPo, RetainCustomersDTO tetainDto) {
        velPo.setString("VIN", tetainDto.getVin());// VIN号
        velPo.setString("LICENSE", tetainDto.getVin());// 车牌号
        velPo.setString("BRAND_CODE", tetainDto.getBrandCode());// 品牌
        velPo.setString("SERIES_CODE", tetainDto.getSeriesCode());// 车系
        velPo.setString("MODEL_CODE", tetainDto.getModelCode());// 车型
        velPo.setString("CONFIG_CODE", tetainDto.getConfigCode());// 配置
        velPo.setString("COLOR", tetainDto.getColor());// 颜色
        velPo.setDate("LICENSE_DATE", tetainDto.getLicenseDate());// 上牌日期
        velPo.setDate("SALES_DATE", tetainDto.getSalesDate());// 销售日期
        velPo.setInteger("VEHICLE_PURPOSE", tetainDto.getVehiclePurpose());// 车辆用途
        velPo.setString("CONSULTANT", tetainDto.getConsultant());// 销售顾问
        velPo.setString("DCRC_ADVISOR", tetainDto.getDcrcAdvisor());// DCRC专员
        velPo.setString("CUSTOMER_NO", tetainDto.getCustomerNo());// 客户编号
    }

    /**
     * 设置保有客户PO属性
     * 
     * @author zhanshiwei
     * @date 2016年8月17日
     * @param cusPo
     * @param tetainDto
     */

    private void setCustomer(CustomerPO cusPo, RetainCustomersDTO tetainDto) {
        cusPo.setLong("VEHICLE_ID", tetainDto.getVehicleId());// 车辆ID
        cusPo.setDate("STOCK_OUT_DATE", tetainDto.getStockOutDate());// 出库日期
        // cusPo.setString("CUSTOMER_NO", tetainDto.getCustomerNo());// 客户编号
        cusPo.setInteger("CUS_SOURCE", tetainDto.getCusSource());// 客户来源
        cusPo.setInteger("MEDIA_TYPE", tetainDto.getMediaType());// 信息渠道
        cusPo.setInteger("BUY_PURPOSE", tetainDto.getBuyPurpose());// 购车目的
        // cusPo.setInteger("BUY_REASON", tetainDto.getBuyReason());// 购车因素
        cusPo.setInteger("IS_FIRST_BUY", tetainDto.getIsFirstBuy());// 是否首次购车
        cusPo.setString("CONTRACT_NO", tetainDto.getContractNo());// 合同编号
        cusPo.setDate("INSURANCE_EXPIRE_DATE", tetainDto.getInsuranceExpireDate());// 保险到期日期
        cusPo.setDouble("VEHICLE_PRICE", tetainDto.getVehiclePrice());// 价格
        cusPo.setString("BUY_REASON", StringUtils.listToString(tetainDto.getBuyReasonList(), ','));// 购车因素
    }

    /**
     * 保有客户选择查询
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CustomerService#queryCustomerSelectInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryCustomerSelectInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
      //  sb.append("select ve.VEHICLE_ID,cu.CUSTOMER_ID,ow.OWNER_ID,ve.DEALER_CODE,ve.CONSULTANT,cu.CUSTOMER_NO,ow.OWNER_NAME,ve.SALES_DATE,ve.VIN,\n");
        sb.append("select em.EMPLOYEE_NO,ve.ORGANIZATION_ID,ve.VEHICLE_ID,cu.CUSTOMER_ID,ow.OWNER_ID,ve.DEALER_CODE,ve.CONSULTANT,cu.CUSTOMER_NO,ow.OWNER_NAME,ve.SALES_DATE,ve.VIN,\n");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME,\n");
        sb.append(" ve.BRAND_CODE,ve.SERIES_CODE,ve.MODEL_CODE,ve.CONFIG_CODE,em.EMPLOYEE_NAME\n");
        sb.append(" from  TM_CUSTOMER   cu \n");
        sb.append("  inner  join    TM_VEHICLE ve on   cu.VEHICLE_ID=ve.VEHICLE_ID\n");
        sb.append("  inner  join    TM_OWNER      ow   on   ve.OWNER_ID=ow.OWNER_ID\n");
        sb.append(" left  join   TM_PACKAGE     pa  on   ve.CONFIG_CODE=pa.CONFIG_CODE\n");
        sb.append(" left  join   TM_MODEL   mo  on   ve.MODEL_CODE=mo.MODEL_CODE\n");
        sb.append(" left  join   TM_SERIES  se  on   ve.SERIES_CODE=se.SERIES_CODE\n");
        sb.append(" left  join   tm_brand   br  on   ve.BRAND_CODE = br.BRAND_CODE\n");
        sb.append(" left join    TM_EMPLOYEE em  on ve.CONSULTANT=em.EMPLOYEE_NO\n");

        sb.append("where  1=1\n").append("\n");

        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList,"302004");
        return id;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> queryOwnerCusforExport(Map<String, String> queryParam) throws ServiceBizException {
		  StringBuffer sb = new StringBuffer();
	        sb.append(" SELECT  B.* , u.`USER_NAME` AS SOLY ,s.`USER_NAME` AS LAST_SOLY FROM ( ");
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
	        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
	            sb.append(" and C.SOLD_BY = ?");
	            queryList.add(queryParam.get("soldBy"));
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
	        sb.append(" )B LEFT JOIN tm_user U ON B.dealer_code = U.`DEALER_CODE` AND B.SOLD_BY = U.`USER_ID` LEFT JOIN tm_user s ON b.LAST_SOLD_BY = s.`USER_ID`  ");
	        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
	        System.err.println(sb.toString());
	       for(Map map : resultList ) {
	    	
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
	        	
	        	if (map.get("BUY_PURPOSE") != null && map.get("BUY_PURPOSE") != "") {
	                if (Integer.parseInt(map.get("BUY_PURPOSE").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("BUY_PURPOSE", "是");
	                } else if (Integer.parseInt(map.get("BUY_PURPOSE").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("BUY_PURPOSE", "否");
	                }
	            }
	        	
	        	if (map.get("OEM_TAG") != null && map.get("OEM_TAG") != "") {
	                if (Integer.parseInt(map.get("OEM_TAG").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("OEM_TAG", "是");
	                } else if (Integer.parseInt(map.get("OEM_TAG").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("OEM_TAG", "否");
	                }
	            }
	        	
	        	if (map.get("IS_DIRECT") != null && map.get("IS_DIRECT") != "") {
	                if (Integer.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("IS_DIRECT", "是");
	                } else if (Integer.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("IS_DIRECT", "否");
	                }
	            }
	        	
	        	if (map.get("IS_CONSIGNED") != null && map.get("IS_CONSIGNED") != "") {
	                if (Integer.parseInt(map.get("IS_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_YES) {
	                    map.put("IS_CONSIGNED", "是");
	                } else if (Integer.parseInt(map.get("IS_CONSIGNED").toString()) == DictCodeConstants.STATUS_IS_NOT) {
	                    map.put("IS_CONSIGNED", "否");
	                }
	            }
	        	
	        	
	        	if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
	                if (map.get("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_INDIVIDUAL) ) {
	                    map.put("CUSTOMER_TYPE", "个人");
	                } else if (map.get("CUSTOMER_TYPE").toString().equals(DictCodeConstants.DICT_CUSTOMER_TYPE_COMPANY) ) {
	                    map.put("CUSTOMER_TYPE", "公司");
	                }
	            }
	        	
	        	
	        	if (map.get("CUS_SOURCE") != null && map.get("CUS_SOURCE") != "") {
	        		  if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_EXHI_HALL) {
		                    map.put("CUS_SOURCE", "来店/展厅客户");
		                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_MARKET_ACTIVITY) {
		                    map.put("CUS_SOURCE", "活动/展厅活动");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_TENURE_CUSTOMER) {
		                    map.put("CUS_SOURCE", "保客增购");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_FRIEND) {
		                    map.put("CUS_SOURCE", "保客推荐");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_OTHER) {
		                    map.put("CUS_SOURCE", "其他");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_VISITER) {
		                    map.put("CUS_SOURCE", "陌生拜访/电话销售");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_INTERNET) {
		                    map.put("CUS_SOURCE", "网络/电子商务");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WAY) {
		                    map.put("CUS_SOURCE", "路过");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_ORG_CODE) {
		                    map.put("CUS_SOURCE", "代理商/代销网点");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_CUSTOMER) {
		                    map.put("CUS_SOURCE", "来电顾客");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_DCC) {
		                    map.put("CUS_SOURCE", "DCC转入");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_SHOW) {
		                    map.put("CUS_SOURCE", "活动-车展");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_EXPERIENCE_DAY) {
		                    map.put("CUS_SOURCE", "活动-外场试驾活动");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_CARAVAN) {
		                    map.put("CUS_SOURCE", "活动-巡展/外展");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WEB) {
		                    map.put("CUS_SOURCE", "官网客户");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_CALL_CENTER) {
		                    map.put("CUS_SOURCE", "DAPSA");
		                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_DS_WEBSITE) {
		                    map.put("CUS_SOURCE", "DS");
		                }
		        	   
	            }
	        	if (map.get("CT_CODE") != null && map.get("CT_CODE") != "") {
	        	
	        	    if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_IDENTITY_CARD) ) {
	                    map.put("CT_CODE", "居民省份证");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_PASSPORT)) {
	                    map.put("CT_CODE", "护照");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_OFFICER) ) {
	                    map.put("CT_CODE", "军官");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_SOLDIER) ) {
	                    map.put("CT_CODE", "士兵");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_POLICE_OFFICER) ) {
	                    map.put("CT_CODE", "警官");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_OTHER) ) {
	                    map.put("CT_CODE", "其他");
	                } else if (map.get("CT_CODE").toString().equals(DictCodeConstants.DICT_CERTIFICATE_TYPE_ORGAN_CODE) ) {
	                    map.put("CT_CODE", "机构代码");
	                } 
	            }
	          }
	        OperateLogDto operateLogDto=new OperateLogDto();
	        operateLogDto.setOperateContent("保客导出");
	        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
	        operateLogService.writeOperateLog(operateLogDto);
	        return resultList;
	}
}
