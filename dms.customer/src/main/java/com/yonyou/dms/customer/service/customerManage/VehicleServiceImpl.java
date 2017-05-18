
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VehicleServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年8月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.RetainCustomersDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 车辆信息
 * 
 * @author zhanshiwei
 * @date 2016年8月9日
 */
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private CarownerService carownerservice;

    /**
     * 根据查询条件查询车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.VehicleService#queryVehicleInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.VEHICLE_ID,ve.DEALER_CODE,ve.VIN,ve.LICENSE,");
        sb.append(" ve.KEY_NO,ve.ENGINE_NO,ve.SALES_DATE,\n");
        sb.append(" ve.CONTACTOR_NAME,ve.CONTACTOR_PHONE,ve.CONTACTOR_MOBILE,ve.MILEAGE,\n");
        sb.append(" ow.ADDRESS,ow.OWNER_NO,ow.OWNER_NAME,ow.PHONE,");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME,\n");
        sb.append(" ve.BRAND_CODE,ve.SERIES_CODE,ve.MODEL_CODE,ve.CONFIG_CODE\n");
        sb.append(" from TM_VEHICLE ve \n");
        sb.append(" left  join   TM_OWNER   ow  on   ve.OWNER_ID=ow.OWNER_ID and ve.DEALER_CODE=ow.DEALER_CODE \n");
        sb.append(" left  join   TM_PACKAGE pa  on   ve.CONFIG_CODE=pa.CONFIG_CODE and ve.DEALER_CODE=pa.DEALER_CODE \n");
        sb.append(" left  join   TM_MODEL   mo  on   ve.MODEL_CODE=mo.MODEL_CODE and ve.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se  on   ve.SERIES_CODE=se.SERIES_CODE and ve.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   tm_brand   br  on   ve.BRAND_CODE = br.BRAND_CODE and ve.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 根据ID查询车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年8月9日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.VehicleService#queryVehicleInfoByid(Long)
     */

    @Override
    public Map<String, Object> queryVehicleInfoByid(Long vehicle_id) throws ServiceBizException {
        VehiclePO velPo = VehiclePO.findById(vehicle_id);
        Map<String, Object> tepomap = velPo.toMap();
        if (!StringUtils.isNullOrEmpty(tepomap.get("owner_id"))) {
            Map<String, Object> ownmap = CarownerPO.findById(tepomap.get("owner_id")).toMap();
            tepomap.putAll(ownmap);
        }

        return tepomap;
    }

    /**
     * 车辆信息新增
     * 
     * @author zhanshiwei
     * @date 2016年8月15日
     * @param tetainDto
     * @param ownerNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.VehicleService#addVehicleInfo(com.yonyou.dms.customer.domains.DTO.tenureCustomer.RetainCustomersDTO,
     * java.lang.String)
     */

    @Override
    public Long addVehicleInfo(RetainCustomersDTO tetainDto, String ownerNo) throws ServiceBizException {
        querycheckoutByVIN(tetainDto.getVin(), null);

        CarownerPO ownPo = new CarownerPO();
        carownerservice.checkPhone(tetainDto.getOwnerId(), tetainDto.getPhone(), tetainDto.getMobile());

        if (StringUtils.isNullOrEmpty(tetainDto.getOwnerId())) {
            tetainDto.setOwnerNo(ownerNo);
            ownPo.setTimestamp("FOUND_DATE", new Date());// 建档时间
        } else {
            ownPo = CarownerPO.findById(tetainDto.getOwnerId());
        }
        // 设置对象属性
        setCarowner(ownPo, tetainDto);
        ownPo.saveIt();
        VehiclePO velPo = new VehiclePO();
        tetainDto.setOwnerId(ownPo.getLongId());
        setVehicle(velPo, tetainDto);
        // ownPo.add(velPo);
        velPo.saveIt();
        return (Long) ownPo.getLongId();
    }

    /**
     * 设置 VehiclePO 属性赋值
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param velPo
     * @param tetainDto
     */

    public void setVehicle(VehiclePO velPo, RetainCustomersDTO tetainDto) {
        // 车辆更换车主
        velPo.setLong("OWNER_ID", tetainDto.getOwnerId());

        velPo.setString("VIN", tetainDto.getVin());// VIN号
        velPo.setString("LICENSE", tetainDto.getLicense());// 车牌号
        velPo.setString("ENGINE_NO", tetainDto.getEngineNo());// 发动机编号
        velPo.setDate("PRODUCT_DATE", tetainDto.getProductDate());// 制造日期
        velPo.setString("BRAND_CODE", tetainDto.getBrandCode());// 品牌
        velPo.setString("SERIES_CODE", tetainDto.getSeriesCode());// 车系
        velPo.setString("MODEL_CODE", tetainDto.getModelCode());// 车型
        velPo.setString("CONFIG_CODE", tetainDto.getConfigCode());// 配置
        velPo.setString("COLOR", tetainDto.getColor());// 颜色
        velPo.setString("INNER_COLOR", tetainDto.getInnerColor());// 内饰颜色
        velPo.setString("PRODUCT_CODE", tetainDto.getProductCode());// 产品代码
        velPo.setString("GEAR_NO", tetainDto.getGearNo());// 变速箱编号
        velPo.setString("KEY_NO", tetainDto.getKeyNo());// 钥匙号
        velPo.setString("PRODUCTING_AREA", tetainDto.getProductingArea());// 产地
        velPo.setInteger("DISCHARGE_STANDARD", tetainDto.getDischargeStandard());// 排放标准
        velPo.setInteger("VEHICLE_PURPOSE", tetainDto.getVehiclePurpose());// 车辆用途
        velPo.setString("CONSULTANT", tetainDto.getConsultant());// 销售顾问
        velPo.setString("SERVICE_ADVISOR", tetainDto.getServiceAdvisor());// 指定服务
        velPo.setString("DCRC_ADVISOR", tetainDto.getDcrcAdvisor());// DCRC专员
        velPo.setString("INSURANCE_ADVISOR", tetainDto.getInsuranceAdvisor());// 续保专员
        velPo.setDate("SALES_DATE", tetainDto.getSalesDate());// 销售日期
        velPo.setDate("LICENSE_DATE", tetainDto.getLicenseDate());// 上牌日期
        velPo.setInteger("IS_SELF_COMPANY", tetainDto.getIsSelfCompany());// 本公司购车
        velPo.setString("CONTACTOR_NAME", tetainDto.getContactorName());// 联系人
        velPo.setString("CONTACTOR_PHONE", tetainDto.getContactorPhone());// 联系人电话
        velPo.setString("CONTACTOR_MOBILE", tetainDto.getContactorMobile());// 联系人手机
        velPo.setInteger("CONTACTOR_GENDER", tetainDto.getContactorGender());// 联系人性别

        velPo.setString("CONTACTOR_PROVINCE", tetainDto.getContactorProvince());// 省份
        velPo.setString("CONTACTOR_CITY", tetainDto.getContactorCity());// 城市
        velPo.setString("CONTACTOR_DISTRICT", tetainDto.getContactorDistrict());// 区县
        velPo.setString("CONTACTOR_ADDRESS", tetainDto.getContactorAddress());// 地址
        velPo.setString("CONTACTOR_ZIP_CODE", tetainDto.getContactorZipCode());// 邮编
        velPo.setString("REMARK2", tetainDto.getRemark2());// 备注

        velPo.setString("MILEAGE", tetainDto.getMileage());// 行驶里程
        velPo.setString("CHANGE_MILEAGE", tetainDto.getChangeMileage());// 换表里程
        velPo.setDate("CHANGE_DATE", tetainDto.getChangeDate());// 换表日期
        velPo.setString("DAILY_AVERAGE_MILEAGE", tetainDto.getDailyAverageMileage());// 日局里程
        velPo.setDate("WRT_BEGIN_DATE", tetainDto.getWrtBeginDate());// 保修起始日期
        velPo.setDate("WRT_END_DATE", tetainDto.getWrtEndDate());// 保修结束日期
        velPo.setString("WRT_BEGIN_MILEAGE", tetainDto.getWrtBeginMileage());// 保修起始里程
        velPo.setString("WRT_END_MILEAGE", tetainDto.getWrtEndMileage());// 保修结束里程
        velPo.setDate("FIRST_IN_DATE", tetainDto.getFirstInDate());// 首次进场日期
        velPo.setDate("LAST_MAINTAIN_DATE", tetainDto.getLastMaintainDate());// 上次维修日期
        velPo.setDate("LAST_MAINTENANCE_DATE", tetainDto.getLastMaintenanceDate());// 上次保养日期
        velPo.setString("LAST_MAINTAIN_MILEAGE", tetainDto.getLastMaintainMileage());// 上次维修里程
        velPo.setString("LAST_MAINTENANCE_MILEAGE", tetainDto.getLastMaintenanceMileage());// 上次保养里程
        velPo.setDate("NEXT_MAINTENANCE_DATE", tetainDto.getNextMaintenanceDate());// 下次保养日期
        velPo.setString("NEXT_MAINTAIN_MILEAGE", tetainDto.getNextMaintainMileage());// 下次保养里程

    }

    /**
     * 查询条件
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" and OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and VIN like ?");
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" and LICENSE like ?");
            queryList.add("%" + queryParam.get("license") + "%");
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

    private void setCarowner(CarownerPO ownPo, RetainCustomersDTO tetainDto) {
        ownPo.setString("OWNER_NO", tetainDto.getOwnerNo().trim());// 车主编号
        ownPo.setString("OWNER_NAME", tetainDto.getOwnerName());// 车主名称
        ownPo.setString("PHONE", tetainDto.getPhone());// 电话
        ownPo.setString("MOBILE", tetainDto.getMobile());// 手机
        ownPo.setString("PROVINCE", tetainDto.getProvince());// 省份
        ownPo.setString("CITY", tetainDto.getCity());// 城市
        ownPo.setString("DISTRICT", tetainDto.getDistrict());// 区县
        ownPo.setString("ADDRESS", tetainDto.getAddress());// 地址
        ownPo.setString("ZIP_CODE", tetainDto.getZipCode());// 邮编
        ownPo.setInteger("OWNER_PROPERTY", tetainDto.getOwnerProperty());// 车主性质
        ownPo.setInteger("GENDER", tetainDto.getGender());// 车主性别
        // ownPo.setInteger("CT_CODE", tetainDto.getCtCode());// 证件类别
    }

    /**
     * 修改车辆信息
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param vehicle_id
     * @param tetainDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VehicleService#modifyVehicleInfo(Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.RetainCustomersDTO)
     */

    @Override
    public void modifyVehicleInfo(Long vehicle_id, RetainCustomersDTO tetainDto) throws ServiceBizException {
        querycheckoutByVIN(tetainDto.getVin(), vehicle_id);
        carownerservice.checkPhone(tetainDto.getOwnerId(), tetainDto.getPhone(), tetainDto.getMobile());

        CarownerPO ownPo = CarownerPO.findById(tetainDto.getOwnerId());
        // 设置对象属性
        setCarowner(ownPo, tetainDto);
        ownPo.saveIt();
        VehiclePO velPo = VehiclePO.findById(vehicle_id);
        tetainDto.setOwnerId(ownPo.getLongId());
        if(!StringUtils.isEquals(velPo.getString("LICENSE"), tetainDto.getLicense())){
            writeOperateLog(velPo, tetainDto);
        }
        setVehicle(velPo, tetainDto);
        // ownPo.add(velPo);
        velPo.saveIt();

    }
    
    /**
    * 更改车牌记录日志
    * @author zhanshiwei
    * @date 2016年11月4日
    * @param operateLogDto
    * @throws ServiceBizException
    */
    	
    public void writeOperateLog( VehiclePO velPo,RetainCustomersDTO tetainDto) throws ServiceBizException {
        operateLogService.recordOperateLog("更改车牌号:VIN为:"+tetainDto.getVin()+" 原车牌号"+velPo.getString("LICENSE")+"新车牌号"+tetainDto.getLicense(), DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
    }
    /**
     * 导出车辆信息查询
     * 
     * @author zhanshiwei
     * @date 2016年8月12日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.VehicleService#queryVehicleInfoforExport(java.util.Map)
     */

    @SuppressWarnings("rawtypes")
    @Override
    public List<Map> queryVehicleInfoforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.VEHICLE_ID,ve.DEALER_CODE,ve.VIN,ve.LICENSE,ve.ENGINE_NO,ve.CONTACTOR_NAME,ve.CONTACTOR_PHONE,ve.CONTACTOR_MOBILE,ve.CONTACTOR_GENDER,\n")
        .append("       ve.CONTACTOR_PROVINCE,ve.CONTACTOR_CITY,ve.CONTACTOR_DISTRICT,ve.CONTACTOR_ADDRESS,ve.CONTACTOR_ZIP_CODE,\n")
        .append("       ow.REMARK,ve.PRODUCT_DATE,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,colors.COLOR_NAME as COLOR,ve.PRODUCT_CODE,em.EMPLOYEE_NAME as SERVICE_ADVISOR,\n")
        .append("       em1.EMPLOYEE_NAME as DCRC_ADVISOR,em2.EMPLOYEE_NAME as INSURANCE_ADVISOR,ve.VEHICLE_PURPOSE,ve.INNER_COLOR,ve.GEAR_NO,ve.KEY_NO,ve.PRODUCTING_AREA,\n")
        .append("       ve.DISCHARGE_STANDARD, em3.EMPLOYEE_NAME as CONSULTANT,ve.SALES_DATE,ve.LICENSE_DATE,ve.IS_SELF_COMPANY,ve.CUSTOMER_NO,ve.REMARK2,ve.MILEAGE,ve.CHANGE_DATE,\n")
        .append("       ve.DAILY_AVERAGE_MILEAGE,ve.WRT_BEGIN_DATE,ve.WRT_END_DATE,ve.WRT_BEGIN_MILEAGE,ve.WRT_END_MILEAGE,ve.FIRST_IN_DATE,ve.LAST_MAINTAIN_DATE,ve.LAST_MAINTAIN_MILEAGE,ve.LAST_MAINTENANCE_DATE,\n")
        .append("       ve.LAST_MAINTENANCE_MILEAGE,ve.NEXT_MAINTENANCE_DATE,ve.NEXT_MAINTAIN_MILEAGE\n")
         .append("\n").append(" from  TM_VEHICLE ve\n")
         .append(" inner join   TM_OWNER     ow  on   ve.OWNER_ID=ow.OWNER_ID\n")
         .append(" left  join   TM_PACKAGE   pa  on   ve.CONFIG_CODE=pa.CONFIG_CODE and ve.DEALER_CODE=pa.DEALER_CODE\n")
         .append(" left  join   TM_MODEL     mo  on   ve.MODEL_CODE=mo.MODEL_CODE   and ve.DEALER_CODE=mo.DEALER_CODE\n")
         .append(" left  join   TM_SERIES    se  on   ve.SERIES_CODE=se.SERIES_CODE and se.DEALER_CODE=ve.DEALER_CODE\n")
         .append(" left  join   tm_brand     br  on   ve.BRAND_CODE = br.BRAND_CODE and br.DEALER_CODE=ve.DEALER_CODE\n")
         .append(" left  join   TM_EMPLOYEE  em  on   ve.SERVICE_ADVISOR=em.EMPLOYEE_NO and ve.DEALER_CODE=em.DEALER_CODE\n")
         .append(" left  join   TM_EMPLOYEE  em1 on   ve.DCRC_ADVISOR=em1.EMPLOYEE_NO and em1.DEALER_CODE=ve.DEALER_CODE\n")
         .append(" left  join   TM_EMPLOYEE  em2 on   ve.INSURANCE_ADVISOR=em2.EMPLOYEE_NO and em2.DEALER_CODE=ve.DEALER_CODE\n")
         .append(" left  join   TM_EMPLOYEE  em3 on   ve.CONSULTANT=em3.EMPLOYEE_NO and em3.DEALER_CODE=ve.DEALER_CODE\n")
         .append(" left  join   TM_COLOR     colors    on   ve.color=colors.COLOR_ID and colors.DEALER_CODE=ve.DEALER_CODE \n")
         .append(" where 1=1\n");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        
        OperateLogDto operateLogDto = new OperateLogDto();
        operateLogDto.setOperateContent("车主车辆信息导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }

    /**
     * 车主车辆信息查询(选择车主车辆信息时用)
     * 
     * @author zhanshiwei
     * @date 2016年8月16日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.tenureCustomer.VehicleService#queryVehicleAndOwnerInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleAndOwnerInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.VIN AS OLD_CUSTOMER_VIN,ow.OWNER_NAME AS OLD_CUSTOMER_NAME,ve.*");
     
        sb.append(" from TM_VEHICLE ve \n");
        sb.append(" inner join   TM_OWNER   ow  on   ve.OWNER_NO=ow.OWNER_NO\n");
   
        sb.append(" where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 根据车牌，姓名，手机号,vin查询车辆信息
     * 
     * @author ZhengHe
     * @date 2016年9月12日
     * @param params
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VehicleService#queryVehicleInfoByParams(java.lang.String)
     */

    @Override
    public List<Map> queryVehicleInfoByParams(String params) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.VEHICLE_ID,ve.DEALER_CODE,ve.VIN,ve.LICENSE,");
        sb.append(" ve.KEY_NO,ve.ENGINE_NO,ve.SALES_DATE,\n");
        sb.append(" ve.CONTACTOR_NAME,ve.CONTACTOR_PHONE,ve.CONTACTOR_MOBILE,ve.MILEAGE,\n");
        sb.append(" ow.ADDRESS,ow.OWNER_NO,ow.OWNER_NAME,ow.PHONE,");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME,\n");
        sb.append(" ve.BRAND_CODE,ve.SERIES_CODE,ve.MODEL_CODE,ve.CONFIG_CODE\n");
        sb.append(" from TM_VEHICLE ve \n");
        sb.append(" left  join   TM_OWNER   ow  on   ve.OWNER_ID=ow.OWNER_ID\n");
        sb.append(" left  join   TM_PACKAGE pa  on   ve.CONFIG_CODE=pa.CONFIG_CODE and pa.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo  on   ve.MODEL_CODE=mo.MODEL_CODE   and mo.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se  on   ve.SERIES_CODE=se.SERIES_CODE and se.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" left  join   tm_brand   br  on   ve.BRAND_CODE = br.BRAND_CODE and br.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" and( ");
        sb.append(" OWNER_NAME like ?");
        queryList.add("%" + params + "%");
        sb.append(" or VIN like ?");
        queryList.add("%" + params + "%");
        sb.append(" or CONTACTOR_PHONE like ?");
        queryList.add("%" + params + "%");
        sb.append(" or LICENSE like ?");
        queryList.add("%" + params + "%");
        sb.append(" )");
        List<Map> vehicleList = DAOUtil.findAll(sb.toString(), queryList);
        return vehicleList;
    }

    /**
     * 客户接待选择车辆
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VehicleService#queryVehicleInfoForRepair(java.util.Map)
     */

    @Override
    public PageInfoDto queryVehicleInfoForRepair(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.VEHICLE_ID,ve.DEALER_CODE,ve.VIN,ve.LICENSE,");
        sb.append(" ve.KEY_NO,ve.ENGINE_NO,ve.SALES_DATE,\n");
        sb.append(" ve.CONTACTOR_NAME,ve.CONTACTOR_PHONE,ve.CONTACTOR_MOBILE,ve.MILEAGE,\n");
        sb.append(" ow.ADDRESS,ow.OWNER_NO,ow.OWNER_NAME,ow.PHONE,ow.MOBILE,");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME,\n");
        sb.append(" ve.BRAND_CODE,ve.SERIES_CODE,ve.MODEL_CODE,ve.CONFIG_CODE,\n");
        sb.append(" br.BRAND_ID,se.SERIES_ID,mo.MODEL_ID ");
        sb.append(" from TM_VEHICLE ve \n");
        sb.append(" left  join   TM_OWNER   ow  on   ve.OWNER_ID=ow.OWNER_ID\n");
        sb.append(" left  join   TM_PACKAGE pa  on   ve.CONFIG_CODE=pa.CONFIG_CODE and ve.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo  on   ve.MODEL_CODE=mo.MODEL_CODE   and mo.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se  on   ve.SERIES_CODE=se.SERIES_CODE and ve.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   tm_brand   br  on   ve.BRAND_CODE = br.BRAND_CODE and br.DEALER_CODE=ve.DEALER_CODE\n");
        sb.append(" where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("vinAndLicense"))) {
            sb.append(" and( ");
            sb.append(" OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("vinAndLicense") + "%");
            sb.append(" or VIN like ?");
            queryList.add("%" + queryParam.get("vinAndLicense") + "%");
            sb.append(" or CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("vinAndLicense") + "%");
            sb.append(" or LICENSE like ?");
            queryList.add("%" + queryParam.get("vinAndLicense") + "%");
            sb.append(" )");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" and OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile"))) {
            sb.append(" and MOBILE like ?");
            queryList.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorName"))) {
            sb.append(" and CONTACTOR_NAME like ?");
            queryList.add("%" + queryParam.get("contactorName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license")) && !queryParam.get("license").equals("{[LICENSE]}")) {
            sb.append(" and LICENSE like ?");
            queryList.add("%" + queryParam.get("license") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 验证VIN重复
     * 
     * @author zhanshiwei
     * @date 2016年10月24日
     * @param vin
     * @param id
     */

    public void querycheckoutByVIN(String vin, Long id) {
        StringBuffer sb = new StringBuffer("select VIN,DEALER_CODE from TM_VEHICLE where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append(" and VEHICLE_ID <> ?");
            queryList.add(id);
        }
        sb.append(" and VIN = ?");
        queryList.add(vin);
        if (DAOUtil.findAll(sb.toString(), queryList).size() > 0) {
            throw new ServiceBizException("VIN号不能重复");
        }
    }
}
