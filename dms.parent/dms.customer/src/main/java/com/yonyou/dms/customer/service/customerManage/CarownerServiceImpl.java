
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : CarownerServiceImpl.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月8日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import com.yonyou.dms.function.common.CommonConstants;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.DTO.stockmanage.CarownerDTO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 车主信息维护
 * 
 * @author zhanshiwei
 * @date 2016年8月8日
 */
@Service
public class CarownerServiceImpl implements CarownerService {
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 查询车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#queryCarownerInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryCarownerInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select OWNER_ID,OWNER_NO,DEALER_CODE,OWNER_PROPERTY,OWNER_NAME,GENDER,CT_CODE,CERTIFICATE_NO,ADDRESS,ZIP_CODE,PHONE,MOBILE,PROVINCE,CITY,DISTRICT,REMARK,FOUND_DATE from tm_owner where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 新增车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param ownDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#addcarownerInfo(com.yonyou.dms.customer.domains.DTO.tenureCustomer.CarownerDTO)
     */

    @Override
    public long addcarownerInfo(CarownerDTO ownDto, String ownerNo) throws ServiceBizException {
        CarownerPO ownPo = new CarownerPO();
        ownDto.setOwnerNo(ownerNo);
        this.setCarowner(ownPo, ownDto);
        checkPhone(null, ownDto.getPhone(), ownDto.getMobile());
        ownPo.saveIt();
        return ownPo.getLongId();
    }
    
    /**
     * 验证手机号是否重复
     * @param phone
     * @param mobile
     */
    public void checkPhone(Object id ,String phone,String mobile){
    	 StringBuffer sb = new StringBuffer("SELECT DEALER_CODE,PHONE,MOBILE from tm_owner  where 1=1 \n");
    	 List<Object> queryList = new ArrayList<Object>();
    	 if(!StringUtils.isNullOrEmpty(phone)&&!StringUtils.isNullOrEmpty(mobile)){
    		 sb.append("and  (PHONE=?  or MOBILE=?)\n");
    		 queryList.add(phone);
    		 queryList.add(mobile);
    	 }
    	 else if (!StringUtils.isNullOrEmpty(phone)){
    		 sb.append("and  PHONE=?\n");
    		 queryList.add(phone);
    	 }
    	 else if (!StringUtils.isNullOrEmpty(mobile)){
    		 sb.append("and MOBILE=?\n");
    		 queryList.add(mobile);
    	 }
    	 if (!StringUtils.isNullOrEmpty(id)){
    		 sb.append("and OWNER_ID<>?");
    		 queryList.add(id);
    	 }
         List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
         if(resultList.size()>0){
             throw new ServiceBizException("手机或电话不能重复!");
         }
    }

    /**
     * 设置车主信息PO属性
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param ownPo
     * @param ownDto
     */

    private void setCarowner(CarownerPO ownPo, CarownerDTO ownDto) {
        ownPo.setString("OWNER_NO", ownDto.getOwnerNo());// 车主编号
        ownPo.setString("OWNER_NAME", ownDto.getOwnerName());// 车主名称
        ownPo.setInteger("OWNER_PROPERTY", ownDto.getOwnerProperty());// 车主性质
        ownPo.setInteger("GENDER", ownDto.getGender());// 车主性别
        ownPo.setString("PHONE", ownDto.getPhone());// 电话
        ownPo.setString("MOBILE", ownDto.getMobile());// 手机
        ownPo.setString("PROVINCE", ownDto.getProvince());// 省份
        ownPo.setString("CITY", ownDto.getCity());// 城市
        ownPo.setString("DISTRICT", ownDto.getDistrict());// 区县
        ownPo.setString("ADDRESS", ownDto.getAddress());// 地址
        ownPo.setString("ZIP_CODE", ownDto.getZipCode());// 邮编
        ownPo.setInteger("CT_CODE", ownDto.getCtCode());// 证件类别
        ownPo.setString("CERTIFICATE_NO", ownDto.getCertificateNo());// 证件号
        ownPo.setString("E_MAIL", ownDto.geteMail());// 邮箱
        ownPo.setDate("BIRTHDAY", ownDto.getBirthday());// 生日
        ownPo.setInteger("FAMILY_INCOME", ownDto.getFamilyIncome());// 月收入
        ownPo.setInteger("EDU_LEVEL", ownDto.getEduLevel());// 学历
        ownPo.setInteger("OWNER_MARRIAGE", ownDto.getOwnerMarriage());// 婚姻状况
        ownPo.setString("HOBBY", ownDto.getHobby());// 爱好
        ownPo.setString("PRE_PAY", ownDto.getPrePay());// 预收款
        ownPo.setString("ARREARAGE_AMOUNT", ownDto.getArrearageAmount());// 欠款金额
        ownPo.setString("REMARK", ownDto.getRemark());// 备注
        ownPo.setTimestamp("FOUND_DATE", ownDto.getFoundDate());// 建档时间
        ownPo.setString("TAX_NO", ownDto.getTaxNo());//税号
    }

    /**
     * 根据ID查询车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#customerResoInfoByid(long)
     */

    @Override
    public CarownerPO customerResoInfoByid(long id) throws ServiceBizException {
        return CarownerPO.findById(id);
    }

    /**
     * 修改车主信息
     * 
     * @author zhanshiwei
     * @date 2016年8月8日
     * @param id
     * @param ownDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#modifycarownerInfo(long,
     * com.yonyou.dms.customer.domains.DTO.tenureCustomer.CarownerDTO)
     */

    @Override
    public void modifycarownerInfo(long id, CarownerDTO ownDto) throws ServiceBizException {
        CarownerPO ownPo = CarownerPO.findById(id);
        this.setCarowner(ownPo, ownDto);
        checkPhone(id, ownDto.getPhone(), ownDto.getMobile());
        ownPo.saveIt();
    }

    /**
     * 导出查询
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.CarownerService#queryCarownerInfoforExport(java.util.Map)
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryCarownerInfoforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select one.DEALER_CODE,one.CERTIFICATE_NO,one.OWNER_NO,one.OWNER_NAME,one.PHONE,one.MOBILE,one.OWNER_PROPERTY,one.GENDER,one.CT_CODE,one.PROVINCE,one.CITY,\n");
        sb.append("one.DISTRICT,one.ZIP_CODE,one.ADDRESS,one.BIRTHDAY,one.E_MAIL,one.FAMILY_INCOME,\n");
        sb.append("one.OWNER_MARRIAGE,one.EDU_LEVEL,one.HOBBY,one.PRE_PAY,one.ARREARAGE_AMOUNT,one.TAX_NO,one.REMARK\n");
        sb.append("from tm_owner one\n");
        sb.append("where  1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        
        OperateLogDto operateLogDto = new OperateLogDto();
        operateLogDto.setOperateContent("车主信息导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        
        return resultList;
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
            sb.append(" and owner_no like ?");
            queryList.add("%" + queryParam.get("ownerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" and owner_name like ?");
            queryList.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("phone"))) {
            sb.append(" and phone like ?");
            queryList.add("%" + queryParam.get("phone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile"))) {
            sb.append(" and mobile like ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
    
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))) {
            sb.append(" and owner_property= ?");
            queryList.add(Integer.parseInt(queryParam.get("ownerProperty")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
            sb.append(" and found_date>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("foundDate_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
            sb.append(" and found_date<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("foundDate_enddate")));
        }
    }

    /**
     * 车主查询
    * @author jcsi
    * @date 2016年11月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.CarownerService#queryCarowner(java.util.Map)
     */
    @Override
    public PageInfoDto queryCarowner(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT t.OWNER_ID PART_CUSTOMER_ID,t.DEALER_CODE,t.OWNER_NO CUSTOMER_CODE,t.OWNER_NAME CUSTOMER_NAME,t.MOBILE,t.PHONE,t.OWNER_PROPERTY,t.GENDER  from  TM_OWNER t where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    
    /**
     * 家庭人员查询
    * @author jcsi
    * @date 2016年11月12日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.CarownerService#queryCarowner(java.util.Map)
     */ 
    
    @Override
    public PageInfoDto queryCarownerFamily(Map<String, String> queryParam,String entityCode) throws ServiceBizException {
    	String sql =new String();
    	
        List<Object> queryList = new ArrayList<Object>();
        sql = "select distinct * from " 
				+ " (select 13701001 AS PRE_PAY_CUS_TYPE,O.dealer_code, O.OWNER_NO as CUSTOMER_CODE, OWNER_NAME as CUSTOMER_NAME, ADDRESS,O.ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,O.PRE_PAY,O.ARREARAGE_AMOUNT,CUS_RECEIVE_SORT from ("+CommonConstants.VM_OWNER+") O "
				+" LEFT JOIN ("+CommonConstants.VM_VEHICLE+")  V ON V.dealer_code=O.dealer_code AND O.OWNER_NO=V.OWNER_NO "
				+ " where O.dealer_code = '" + entityCode + "'";
			if (!StringUtils.isNullOrEmpty(queryParam.get("lisence"))){
				sql=" select a.* from( ";
				sql += "select distinct * from " 
					+ " (select 13701001 AS PRE_PAY_CUS_TYPE,O.dealer_code, O.OWNER_NO as CUSTOMER_CODE, OWNER_NAME as CUSTOMER_NAME, ADDRESS,O.ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,O.PRE_PAY,O.ARREARAGE_AMOUNT,CUS_RECEIVE_SORT,V.LICENSE from  ("+CommonConstants.VM_OWNER+") O "
					+" LEFT JOIN ("+CommonConstants.VM_VEHICLE+")  V ON V.dealer_code=O.dealer_code AND O.OWNER_NO=V.OWNER_NO where O.dealer_code = '" + entityCode + "'";
				//车牌号不为空,增加查询条件
			
				if (!StringUtils.isNullOrEmpty(queryParam.get("lisence"))) {
					sql+=" and  V.LICENSE like ?";
			            queryList.add("%" + queryParam.get("lisence") + "%");
			        }
					
						
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")) && queryParam.get("customerType").equals(13701002) ){sql += " and 1=2 ";}
				sql += " union "
					+ " select 13701002 AS PRE_PAY_CUS_TYPE,y.dealer_code,y.CUSTOMER_CODE, y.CUSTOMER_NAME, y.ADDRESS,y.ZIP_CODE,y.CONTACTOR_NAME,y.CONTACTOR_PHONE,y.CONTACTOR_MOBILE,y.PRE_PAY,y.ARREARAGE_AMOUNT,y.CUS_RECEIVE_SORT,'' as LICENSE from ("+CommonConstants.VM_PART_CUSTOMER+") y"
					+ " where y.dealer_code = '" + entityCode + "'";
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")) && queryParam.get("customerType").equals(13701001) ){sql += " and 1=2 ";}
				else
					//车牌号不为空,过滤union下面的表数据
					if (!StringUtils.isNullOrEmpty(queryParam.get("lisence"))) {
						  sql += " and 1=2 ";
					 }
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")) && queryParam.get("customerType").equals(13701004) ){sql += " and 1=2 ";}//保险公司
				sql += " union "
					+ " select 13701004 AS PRE_PAY_CUS_TYPE,z.dealer_code,z.CUSTOMER_CODE, z.CUSTOMER_NAME, z.ADDRESS,z.ZIP_CODE,z.CONTACTOR_NAME,z.CONTACTOR_PHONE,z.CONTACTOR_MOBILE,z.PRE_PAY,z.ARREARAGE_AMOUNT,z.CUS_RECEIVE_SORT,'' as LICENSE from ("+CommonConstants.VM_PART_CUSTOMER+") z"
					+ " where z.dealer_code = '" + entityCode + "'and z.CUSTOMER_TYPE_CODE = (select e.CUSTOMER_TYPE_CODE from  ("+CommonConstants.VM_CUSTOMER_TYPE+") e where e.dealer_code='" + entityCode + "' and E.CUSTOMER_TYPE_NAME LIKE '%保险%') "; 
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")) && queryParam.get("customerType").equals(13701001) ){sql += " and 1=2 ";}
				else
					//车牌号不为空,过滤union下面的表数据
					if (!StringUtils.isNullOrEmpty(queryParam.get("lisence"))) {
						  sql += " and 1=2 ";
					 }
				sql+=") a where 1=1 ";
				//sql=" select * from (?7?0 se "+sql+") a where 1=1 ";
			}
			else
				sql+= " union "
				+ " select 13701002 AS PRE_PAY_CUS_TYPE,cx.dealer_code ,cx.CUSTOMER_CODE, cx.CUSTOMER_NAME, cx.ADDRESS,cx.ZIP_CODE,cx.CONTACTOR_NAME,cx.CONTACTOR_PHONE,cx.CONTACTOR_MOBILE,cx.PRE_PAY,cx.ARREARAGE_AMOUNT,cx.CUS_RECEIVE_SORT from  ("+CommonConstants.VM_PART_CUSTOMER+") cx"
				+ " where cx.dealer_code = '" + entityCode + "'"; 
				
				sql+= " ) A "
				+ " where 1 =1 ";
				//2009-09-27 增加开工单时间查询条件
//				if(beginTime!=null && beginTime.length()>0 && endTime!=null && endTime.length()>0){
//					sql+=" AND EXISTS (SELECT 1 FROM  TT_BALANCE_PAYOBJ B,TT_REPAIR_ORDER O" +
//							" WHERE O.dealer_code = '"+entityCode+"' AND A.dealer_code=O.dealer_code AND B.PAYMENT_OBJECT_CODE =" +
//									" A.CUSTOMER_CODE AND O.RO_NO=B.RO_NO " +
//									 Utility.getDateCond("O", "RO_CREATE_DATE", beginTime, endTime) + 
//			/*						" AND Timestamp(O.RO_CREATE_DATE) " +
//									"between ('"+beginTime+" 00:00:00')and ('"+endTime+" 23:59:59')" +*/
//											" ) ";
//				}
//				
				// add by sf 2010-12-06  增加配件销售单时间条件
//				if (salePartBegin != null && !"".equals(salePartBegin) &&
//						salePartEnd != null && !"".equals(salePartEnd)){
//					sql += " AND EXISTS (select 1 from  TT_BALANCE_PAYOBJ C, TT_SALES_PART S where S.dealer_code = '"+entityCode+"' " +
//							" AND C.dealer_code = S.dealer_code AND C.PAYMENT_OBJECT_CODE = A.CUSTOMER_CODE " +
//							" AND S.SALES_PART_NO = C.SALES_PART_NO " +
//							Utility.getDateCond("S", "CREATE_DATE", salePartBegin, salePartEnd)+
//							" )";
//				}
				// end
				
				
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerType")))
					sql += " and A.PRE_PAY_CUS_TYPE = " + queryParam.get("customerType") + "";
//				if (cusReceiveSort != null && !cusReceiveSort.trim().equals(""))
//					sql += " and A.CUS_RECEIVE_SORT = " + cusReceiveSort + "";
//				if (isSelectArrearage != null && isSelectArrearage.trim().equals(DictDataConstant.DICT_IS_YES))
//					sql += " and A.ARREARAGE_AMOUNT is not null and A.ARREARAGE_AMOUNT <> 0 ";
				if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
					sql += " and A.CUSTOMER_NAME like ?";
		            queryList.add("%" + queryParam.get("customerName") + "%");
		        }
		        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
		        	sql += " and A.CUSTOMER_CODE like ?";
		            queryList.add("%" + queryParam.get("customerNo") + "%");
		        }
		        System.out.println(sql);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(),queryList);
        return pageInfoDto;
    }
    /**
     * 车辆车主选择
     * 
     * @author zhanshiwei
     * @date 2016年8月18日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CarownerService#queryCarownerSelectInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryCarownerSelectInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("");
        sb.append("select ow.OWNER_ID,ow.DEALER_CODE,ow.OWNER_NO,ow.OWNER_NAME,ow.GENDER,ow.MOBILE,ow.PHONE,\n");
        sb.append(" ow.PROVINCE,ow.CITY,ow.DISTRICT,ow.ZIP_CODE,ow.OWNER_PROPERTY,ow.CT_CODE,ow.REMARK,ow.ADDRESS\n");
        sb.append(" from tm_owner ow where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
}
