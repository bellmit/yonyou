
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusComplaintServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2016年7月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月27日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.RowListener;
import org.javalite.activejdbc.RowProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerComplaintDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerComplaintDetailDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintDetailPO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 客户投诉
 * 
 * @author zhanshiwei
 * @date 2016年7月27日
 */
@Service
@SuppressWarnings("rawtypes")
public class CusComplaintServiceImpl implements CusComplaintService {
    @Autowired
    FileStoreService fileStoreService;
    /**
     * 查询投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param queryParam 查询条件
     * @return (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#QueryCusComplaint(java.util.Map)
     */

    @Override
    public PageInfoDto queryCusComplaint(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT pcus.DEALER_CODE,pcus.COMPLAINT_ID,pcus.COMPLAINT_NO,pcus.COMPLAINT_NAME,pcus.COMPLAINT_DATE,pcus.COMPLAINT_ORIGIN,\n")
        .append("       pcus.PRIORITY,pcus.COMPLAINT_MAIN_TYPE,pcus.COMPLAINT_TYPE,pcus.COMPLAINT_SERIOUS,pcus.DEAL_STATUS,pcus.COMPLAINT_MOBILE,\n")
        .append("       pcus.COMPLAINT_SUMMARY,org.ORG_NAME , pcus.DEPARTMENT,em.EMPLOYEE_NAME,pcus.BE_COMPLAINT_EMP,(select PARAM_VALUE  from TC_SYSTEM_PARAM where DEALER_CODE =-1 and PARAM_TYPE=1030) as OPE_TYPE\n")
        .append("FROM TT_CUSTOMER_COMPLAINT pcus\n")
        .append("left join tm_organization org on  pcus.DEPARTMENT=org.ORG_CODE and pcus.DEALER_CODE=org.DEALER_CODE\n")
        .append("left join TM_EMPLOYEE  em  on  pcus.BE_COMPLAINT_EMP=em.EMPLOYEE_NO   and pcus.DEALER_CODE=em.DEALER_CODE\n")
        .append("where 1=1\n");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_no"))) {
            sb.append(" and COMPLAINT_NO like ?");
            queryList.add("%" + queryParam.get("complaint_no") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_origin"))) {
            sb.append(" and COMPLAINT_ORIGIN = ?");
            queryList.add(Integer.parseInt(queryParam.get("complaint_origin")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customer_type_code"))) {
            sb.append(" and CUSTOMER_TYPE_CODE = ?");
            queryList.add(Integer.parseInt(queryParam.get("customer_type_code")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_type"))) {
            sb.append(" and COMPLAINT_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("complaint_type")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_name"))) {
            sb.append(" and COMPLAINT_NAME like ?");
            queryList.add("%" + queryParam.get("complaint_name") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_mobile"))) {
            sb.append(" and COMPLAINT_MOBILE like ?");
            queryList.add("%" + queryParam.get("complaint_mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_serious"))) {
            sb.append(" and COMPLAINT_SERIOUS = ?");
            queryList.add(Integer.parseInt(queryParam.get("complaint_serious")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("priority"))) {
            sb.append(" and PRIORITY = ?");
            queryList.add(Integer.parseInt(queryParam.get("priority")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("deal_status"))) {
            sb.append(" and DEAL_STATUS = ?");
            queryList.add(queryParam.get("deal_status"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_startdate"))) {
            sb.append(" and COMPLAINT_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("complaint_startdate")));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("complaint_enddate"))) {
            sb.append(" and COMPLAINT_DATE <?");
            queryList.add(DateUtil.addOneDay(queryParam.get("complaint_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("complaintMainType"))) {
            sb.append(" and COMPLAINT_MAIN_TYPE =?");
            queryList.add(Integer.parseInt(queryParam.get("complaintMainType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("notdeal"))
            && Integer.parseInt(queryParam.get("notdeal")) == DictCodeConstants.STATUS_IS_YES
            && StringUtils.isNullOrEmpty(queryParam.get("deal_status"))) {
            sb.append(" and DEAL_STATUS not in(?)");
            queryList.add(DictCodeConstants.COMPLAINT_DEAL_STATUS_04);
        }

        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 新增投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param complDTO
     * @return (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#addCustomerComplaint(com.yonyou.dms.customer.domains.DTO.cuscomplaint.CustomerComplaintDTO)
     */

    @Override
    public Long addCustomerComplaint(CustomerComplaintDTO complDTO, String complaintNo) throws ServiceBizException {

        if (StringUtils.isNullOrEmpty(complDTO.getMileage())) {
            complDTO.setMileage(0.0);
        }
        complDTO.setComplaintNo(complaintNo);// 编号
        setDealStatus(complDTO);
        CustomerComplaintPO complPO = new CustomerComplaintPO();
        // 设置对象属性
        setCustomerComplaintPO(complPO, complDTO);
        complPO.saveIt();
        if (complDTO.getComplaintDetai().size() > 0 && complDTO.getComplaintDetai() != null) {
            for (CustomerComplaintDetailDTO coppdetailDto : complDTO.getComplaintDetai()) {
                if(coppdetailDto.getDealDate().getTime()<complDTO.getComplaintDate().getTime()){
                   throw new ServiceBizException("投诉处理时间应该大于等于接待时间!");
                }
                CustomerComplaintDetailPO coppdetailPo =getCustomerComplaintDetailPO(coppdetailDto);
                complPO.add(coppdetailPo);
                //插入附件信息
                fileStoreService.addFileUploadInfo(coppdetailDto.getComplaintFile(), coppdetailPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_CUSTOMER_INFO_COMPLAINT);
            }
        }
        return complPO.getLongId();
    }

    /**
     * 设置投诉信息对象属性
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param complPO
     * @param complDTO
     */

    public void setCustomerComplaintPO(CustomerComplaintPO complPO, CustomerComplaintDTO complDTO) throws ServiceBizException {
        complPO.setString("COMPLAINT_NO", complDTO.getComplaintNo());// 投诉编号
        complPO.setString("COMPLAINT_NAME", complDTO.getComplaintName());// 投诉人姓名
        complPO.setInteger("COMPLAINT_GENDER", complDTO.getComplaintGender());// 投诉人性别
        complPO.setString("COMPLAINT_MOBILE", complDTO.getComplaintMobile());// 投诉人手机
        complPO.setInteger("COMPLAINT_ORIGIN", complDTO.getComplaintOrigin());// 投诉来源
        complPO.setInteger("COMPLAINT_MAIN_TYPE", complDTO.getComplaintMainType());// 投诉大类
        complPO.setInteger("COMPLAINT_TYPE", complDTO.getComplaintType());// 投诉类型
        complPO.setTimestamp("COMPLAINT_DATE", complDTO.getComplaintDate());// 投诉日期
        complPO.setString("RECEPTIONIST", complDTO.getReceptionist());// 投诉接待人
        complPO.setString("BE_COMPLAINT_EMP", complDTO.getBeComplaintEmp());// 投诉员工
        complPO.setString("DEPARTMENT", complDTO.getDepartment());// 投诉部门
        complPO.setInteger("DEAL_STATUS", complDTO.getDealStatus());// 状态
        complPO.setInteger("PRIORITY", complDTO.getPriority());// 优先级
        complPO.setInteger("COMPLAINT_SERIOUS", complDTO.getComplaintSerious());// 严重性(投诉性质)
        complPO.setString("COMPLAINT_SUMMARY", complDTO.getComplaintSummary());// 投诉摘要
        complPO.setString("COMPLAINT_REASON", complDTO.getComplaintReason());// 投诉原因
        complPO.setString("RESOLVENT", complDTO.getResolvent());// 解决方案

        complPO.setString("RO_NO", complDTO.getRoNo());// 工单号
        complPO.setTimestamp("RO_CREATE_DATE", complDTO.getRoCreateDate());// 工单开单日期
        complPO.setString("SERVICE_ADVISOR", complDTO.getServiceAdvisor());// 服务专员
        complPO.setString("DELIVERER", complDTO.getDeliverer());// 送修人
        complPO.setString("DELIVERER_PHONE", complDTO.getDelivererPhone());// 送修人电话
        complPO.setString("DELIVERER_MOBILE", complDTO.getDelivererMobile());// 送修人手机

        complPO.setString("SO_NO", complDTO.getSoNo());// 销售单号
        complPO.setDate("SALES_DATE", complDTO.getSalesDate());// 销售日期
        complPO.setString("CONSULTANT", complDTO.getConsultant());// 销售顾问
        complPO.setString("POTENTIAL_CUSTOMER_NAME", complDTO.getPotentialCustomerName());// 潜客名称
        complPO.setString("POTENTIAL_CUSTOMER_MOBILE", complDTO.getPotentialCustomerMobile());// 潜客手机
        complPO.setString("POTENTIAL_CUSTOMER_PHONE", complDTO.getPotentialCustomerPhone());// 潜客电话

        complPO.setString("VIN", complDTO.getVin());// VIN号
        complPO.setString("LICENSE", complDTO.getLicense());// 车牌号
        complPO.setString("OWNER_NAME", complDTO.getOwnerName());// 车主
        complPO.setDouble("MILEAGE", complDTO.getMileage());// 行驶里程
        complPO.setDate("BUY_CAR_DATE", complDTO.getBuyCarDate());// 购车时间
        complPO.setString("LINK_ADDRESS", complDTO.getLinkAddress());// 车主地址

    }

    /**
     * 设置投诉信息明细对象属性
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param coppdetailDto
     * @return
     */

    private CustomerComplaintDetailPO getCustomerComplaintDetailPO(CustomerComplaintDetailDTO coppdetailDto) throws ServiceBizException{
        CustomerComplaintDetailPO coppdetailPo = new CustomerComplaintDetailPO();
        coppdetailPo.setString("DEALER", coppdetailDto.getDealer());// 处理人
        coppdetailPo.setTimestamp("DEAL_DATE", coppdetailDto.getDealDate());// 处理时间
        coppdetailPo.setString("DEAL_RESULT", coppdetailDto.getDealResult());// 处理结果
        coppdetailPo.setString("OEM_DEAL_NAME", coppdetailDto.getOemDealName());// OEM处理人
        coppdetailPo.setString("REMARK", coppdetailDto.getRemark());// 备注
        return coppdetailPo;
    }

    /**
     * 根据ID查询投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#getCustomerComplaintById(java.lang.Long)
     */

    @Override
    public CustomerComplaintPO getCustomerComplaintById(Long id) throws ServiceBizException {
        return CustomerComplaintPO.findById(id);
    }

    /**
     * OEM 手动结案判断
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#getCustomerComplaintById(java.lang.Long)
     */

    @Override
    public Map<String, Object> getCustomerComplaintOpeType() throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select PARAM_VALUE as OPE_TYPE from TC_SYSTEM_PARAM where DEALER_CODE =-1 and PARAM_TYPE=1030");
        List<Object> queryList = new ArrayList<Object>();
        // 执行查询操作
        RowProcessor processor = Base.find(sb.toString(), queryList.toArray());
        final Map<String, Object> opetypeMap = new HashMap<String, Object>();
        processor.with(new RowListener() {
            @Override
            public boolean next(Map<String, Object> row) {
                opetypeMap.putAll(row);
                return true;
            }
        });
        return opetypeMap;
    }

    
    /**
    * 翻译部门Orgcode
    * @author zhanshiwei
    * @date 2016年10月31日
    * @param id
    * @return
    * @throws ServiceBizException
    */
    	
    public Map<String, Object> getCustomerComplaintOrgCode(Long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("SELECT pcus.DEALER_CODE,pcus.COMPLAINT_ID,org.ORG_NAME,pcus.DEPARTMENT\n")
        .append("FROM TT_CUSTOMER_COMPLAINT pcus\n")
        .append("left join tm_organization org on  pcus.DEPARTMENT=org.ORG_CODE and pcus.DEALER_CODE=org.DEALER_CODE\n")
        .append("where 1=1\n").append(" and pcus.COMPLAINT_ID=?" );
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
      
        final Map<String, Object> orgMap = new HashMap<String, Object>();
        // 执行查询操作
        DAOUtil.findAll(sb.toString(), queryList, new DefinedRowProcessor(){
            @Override
            protected void process(Map<String, Object> row) {
                orgMap.put("ORG_NAME", row.get("ORG_NAME"));
            }
        });
        
        return orgMap;
    }
    /**
     * 查询投诉明细
     * 
     * @author zhanshiwei
     * @date 2016年7月31日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#queryComplaintDetail(java.lang.Long)
     */

    @Override
    public List<Map> queryComplaintDetail(Long id) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select t.COMPLAINT_DETAIL_ID,t.DEALER,t.DEAL_DATE,t.DEAL_RESULT,t.OEM_DEAL_NAME,t.REMARK from tt_customer_complaint_detail t where t.COMPLAINT_ID=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sqlSb.toString(), queryParams.toArray());
    }

    /**
     * 修改投诉信息
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param id
     * @param complDTO
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#modifyCustomerComplaint(java.lang.Long,
     * com.yonyou.dms.customer.domains.DTO.cuscomplaint.CustomerComplaintDTO)
     */

    @Override
    public void modifyCustomerComplaint(Long id, CustomerComplaintDTO complDTO) throws ServiceBizException {
        CustomerComplaintPO cuscompPo = CustomerComplaintPO.findById(id);
        // 设置处理状态
        setDealStatus(complDTO);
        setCustomerComplaintPO(cuscompPo, complDTO);
        cuscompPo.saveIt();
        // 删除原
        List<CustomerComplaintDetailPO> complaintdetailList = CustomerComplaintDetailPO.find("COMPLAINT_ID = ?", id);
        for(CustomerComplaintDetailPO complPo:complaintdetailList){
            fileStoreService.updateNotValidByBillId(complPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_CUSTOMER_INFO_COMPLAINT);
        }
        CustomerComplaintDetailPO.delete("COMPLAINT_ID = ?", id);
        if (complDTO.getComplaintDetai().size() > 0 && complDTO.getComplaintDetai() != null) {
            for (CustomerComplaintDetailDTO cuscomplDetailDto : complDTO.getComplaintDetai()) {
                if(cuscomplDetailDto.getDealDate().getTime()<complDTO.getComplaintDate().getTime()){
                    throw new ServiceBizException("投诉处理时间应该大于等于接待时间!");
                 }
                
                CustomerComplaintDetailPO coppdetailPo =getCustomerComplaintDetailPO(cuscomplDetailDto);
                cuscompPo.add(coppdetailPo);
              //插入附件信息
                fileStoreService.addFileUploadInfo(cuscomplDetailDto.getComplaintFile(), coppdetailPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_CUSTOMER_INFO_COMPLAINT);
     
            }
        }
    }

    /**
     * 设置处理状态
     * 
     * @author zhanshiwei
     * @date 2016年10月14日
     * @param complDTO
     */

    public void setDealStatus(CustomerComplaintDTO complDTO) throws ServiceBizException {

        if (complDTO.getComplaintDetai().size() > 0 && (!StringUtils.isNullOrEmpty(complDTO.getBeComplaintEmp())
                                                        || !StringUtils.isNullOrEmpty(complDTO.getDepartment()))) {
            complDTO.setDealStatus(DictCodeConstants.COMPLAINT_DEAL_STATUS_03);
            if (DictCodeConstants.COMPLAINT_ORIGIN_01 == complDTO.getComplaintOrigin()) {
                Map<String, Object> opetypeMap = getCustomerComplaintOpeType();
                if (opetypeMap != null
                    && StringUtils.isEquals(DictCodeConstants.STATUS_IS_YES, opetypeMap.get("OPE_TYPE").toString())) {
                    complDTO.setDealStatus(DictCodeConstants.COMPLAINT_DEAL_STATUS_03);// 结案申请中
                }
            }
        } else if (StringUtils.isNullOrEmpty(complDTO.getBeComplaintEmp())
                   && StringUtils.isNullOrEmpty(complDTO.getDepartment())) {
            complDTO.setDealStatus(DictCodeConstants.COMPLAINT_DEAL_STATUS_01);
        } else if (!StringUtils.isNullOrEmpty(complDTO.getBeComplaintEmp())
                   || !StringUtils.isNullOrEmpty(complDTO.getDepartment())) {
            complDTO.setDealStatus(DictCodeConstants.COMPLAINT_DEAL_STATUS_02);
        }
    }

    /**
     * 结案操作
     * 
     * @author zhanshiwei
     * @date 2016年8月2日
     * @param id
     * @param complDTO
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#modifySettleSate(java.lang.Long,
     * com.yonyou.dms.customer.domains.DTO.cuscomplaint.CustomerComplaintDTO)
     */

    @Override
    public CustomerComplaintPO modifySettleSate(Long id, CustomerComplaintDTO complDTO) throws ServiceBizException {
        CustomerComplaintPO cuscompPo = CustomerComplaintPO.findById(id);
        if (DictCodeConstants.COMPLAINT_DEAL_STATUS_04 == cuscompPo.getInteger("deal_status")) {
            throw new ServiceBizException("已结案不要重复操作");
        }
        //设置处理状态
        setDealStatus(complDTO);
        complDTO.setDealStatus(DictCodeConstants.COMPLAINT_DEAL_STATUS_04);
        setCustomerComplaintPO(cuscompPo, complDTO);
        cuscompPo.saveIt();
        // 删除原
        List<CustomerComplaintDetailPO> complaintdetailList = CustomerComplaintDetailPO.find("COMPLAINT_ID = ?", id);
        for(CustomerComplaintDetailPO complPo:complaintdetailList){
            fileStoreService.updateNotValidByBillId(complPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_CUSTOMER_INFO_COMPLAINT);
        }
        CustomerComplaintDetailPO.delete("COMPLAINT_ID = ?", id);
        if (complDTO.getComplaintDetai().size() > 0 && complDTO.getComplaintDetai() != null) {
            for (CustomerComplaintDetailDTO cuscomplDetailDto : complDTO.getComplaintDetai()) {

                CustomerComplaintDetailPO coppdetailPo =getCustomerComplaintDetailPO(cuscomplDetailDto);
                cuscompPo.add(coppdetailPo);
                //插入附件信息
                fileStoreService.addFileUploadInfo(cuscomplDetailDto.getComplaintFile(), coppdetailPo.getLongId().toString(), DictCodeConstants.FILE_TYPE_CUSTOMER_INFO_COMPLAINT);
            }
        }
        return cuscompPo;
    }

    /**
     * OEM非手动调用接口结案操作(不用了)
     * 
     * @author zhanshiwei
     * @date 2016年8月15日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.cuscomplaint.CusComplaintService#complaintSettleforOem(java.lang.Long)
     */

    @Override
    public CustomerComplaintPO complaintSettleforOem(Long id) throws ServiceBizException {
        CustomerComplaintPO cuscompPo = CustomerComplaintPO.findById(id);
        if (DictCodeConstants.COMPLAINT_DEAL_STATUS_04 == cuscompPo.getInteger("deal_status")) {
            throw new ServiceBizException("已结案不要重复操作");
        }
        if (DictCodeConstants.COMPLAINT_ORIGIN_01 == cuscompPo.getInteger("complaint_origin")) {
            cuscompPo.setString("DEAL_STATUS", DictCodeConstants.COMPLAINT_DEAL_STATUS_04);// 处理结果
            cuscompPo.saveIt();
            return null;
        } else {
            return cuscompPo;

        }
    }

    /**
     * 统计投诉未处理个数
     * 
     * @author zhanshiwei
     * @date 2016年9月21日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.CusComplaintService#queryComplainCounts()
     */

    @Override
    public Map<String, Object> queryComplainCounts() throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select  COUNT(COMPLAINT_ID) as number,DEALER_CODE  from TT_CUSTOMER_COMPLAINT  where DEAL_STATUS not in(?) and COMPLAINT_SERIOUS=? ");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.COMPLAINT_DEAL_STATUS_04);
        params.add(DictCodeConstants.COMPLAINT_SERIOUS_01);
        final Map<String, Object> resultMap = new HashMap<String, Object>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                resultMap.putAll(row);
            }
        });
        return resultMap;
    }
}
