
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingOrderServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年10月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月14日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.order.BoAddItemDTO;
import com.yonyou.dms.repair.domains.DTO.order.BoLabourDTO;
import com.yonyou.dms.repair.domains.DTO.order.BoPartDTO;
import com.yonyou.dms.repair.domains.DTO.order.BookingOrderDTO;
import com.yonyou.dms.repair.domains.DTO.order.DrivingServiceOrderDTO;
import com.yonyou.dms.repair.domains.PO.order.BoAddItemPO;
import com.yonyou.dms.repair.domains.PO.order.BoLabourPO;
import com.yonyou.dms.repair.domains.PO.order.BoPartPO;
import com.yonyou.dms.repair.domains.PO.order.DrivingServiceOrderPO;


/**
* 预约
* @author jcsi
* @date 2016年10月14日
*/
@Service
public class BookingOrderServiceImpl implements BookingOrderService {
    
    @Autowired
    private CommonNoService commonNoService;
    
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 查询
    * @author jcsi
    * @date 2016年10月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#search(java.util.Map)
    */

    @Override
    public PageInfoDto search(Map<String, String> param) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT b.BO_NO,b.BO_ID,b.DEALER_CODE,b.BOOKING_ORDER_STATUS,b.CONTACTOR_NAME,b.CONTACTOR_MOBILE,");
        sb.append("o.OWNER_NAME,v.VIN,b.LICENSE,b.BOOKING_COME_TIME,b.BOOKING_TYPE_CODE,b.REMARK,r.REPAIR_TYPE_NAME  ");
        sb.append("from TT_BOOKING_ORDER b    ");
        sb.append("left JOIN tm_vehicle v on b.VEHICLE_ID=v.VEHICLE_ID   ");
        sb.append("left JOIN tm_owner o on v.OWNER_ID=o.OWNER_ID   ");
        sb.append("inner join tm_repair_type r on b.BOOKING_TYPE_CODE=r.REPAIR_TYPE_CODE and r.DEALER_CODE=b.DEALER_CODE where 1=1");
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("boNo"))){
            sb.append(" and b.BO_NO like ? ");
            queryParam.add("%"+param.get("boNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("ownerName"))){
            sb.append(" and o.OWNER_NAME like ? ");
            queryParam.add("%"+param.get("ownerName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("contactorMobile"))){
            sb.append(" and b.CONTACTOR_MOBILE like ? ");
            queryParam.add("%"+param.get("contactorMobile")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("license"))){
            sb.append(" and b.LICENSE like ? ");
            queryParam.add("%"+param.get("license")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("vin"))){
            sb.append(" and v.VIN like ? ");
            queryParam.add("%"+param.get("vin")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("bookingComeTimeFrom"))){
            sb.append(" and b.BOOKING_COME_TIME >= ? ");
            queryParam.add(DateUtil.parseDefaultDate(param.get("bookingComeTimeFrom")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("bookingComeTimeTo"))){
            sb.append(" and b.BOOKING_COME_TIME < ?");
            queryParam.add(DateUtil.addOneDay(param.get("bookingComeTimeTo")) );
        }
        if(!StringUtils.isNullOrEmpty(param.get("bookingOrderstatus"))){
            sb.append(" and b.BOOKING_ORDER_STATUS = ? ");
            queryParam.add(Integer.parseInt(param.get("bookingOrderstatus")));
        }
        
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    /**
     * 新增
    * @author jcsi
    * @date 2016年10月14日
    * @param bookingOrderDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#addBookingOrder(com.yonyou.dms.repair.domains.DTO.order.BookingOrderDTO)
    */

    @Override
    public Long addBookingOrder(BookingOrderDTO bookingOrderDto) throws ServiceBizException {
    	TtBookingOrderPO bookingOrderPo=new TtBookingOrderPO();
        setBookingOrderPO(bookingOrderPo,bookingOrderDto);
        bookingOrderPo.saveIt();  //保存预约单
        //维修项目
        for(BoLabourDTO dto:bookingOrderDto.getBolabourList()){
            BoLabourPO boLabourPo=new BoLabourPO();
            setBoLabourPO(boLabourPo,dto);
            bookingOrderPo.add(boLabourPo);
        }
        //维修配件
        for(BoPartDTO dto:bookingOrderDto.getBoPartList()){
            BoPartPO boPartPo=new BoPartPO();
            setBoPartPO(boPartPo,dto);
            bookingOrderPo.add(boPartPo);
        }
        //附加项目
        for(BoAddItemDTO dto:bookingOrderDto.getBoAddItemList()){
            BoAddItemPO boAddItemPo=new BoAddItemPO();
            setBoAddItemPO(boAddItemPo,dto);
            bookingOrderPo.add(boAddItemPo);
        }
        
        
        return bookingOrderPo.getLongId();
    }

    /**
     * 修改
    * @author jcsi
    * @date 2016年10月14日
    * @param bookingOrderDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#editBookingOrder(com.yonyou.dms.repair.domains.DTO.order.BookingOrderDTO)
    */

    @Override
    public void editBookingOrder(Long id,BookingOrderDTO bookingOrderDto) throws ServiceBizException {
    	TtBookingOrderPO bookingOrderPo=TtBookingOrderPO.findById(id);
        setBookingOrderPO(bookingOrderPo,bookingOrderDto);
        bookingOrderPo.saveIt();  //保存预约单
        //删除维修项目
        if(!StringUtils.isNullOrEmpty(bookingOrderDto.getDelBolabourId())){
            String[] delBolabourIdArr=bookingOrderDto.getDelBolabourId().split(",");
            for(int i=0;i<delBolabourIdArr.length;i++){
                BoLabourPO boLabourPo=BoLabourPO.findById(delBolabourIdArr[i]);
                boLabourPo.delete();
            }
        
        }
        
        //删除维修配件
        if(!StringUtils.isNullOrEmpty(bookingOrderDto.getDelBoPartId())){
            String[] delBoPartArr=bookingOrderDto.getDelBoPartId().split(",");
            for(int i=0;i<delBoPartArr.length;i++){
                BoPartPO boPartPO=BoPartPO.findById(delBoPartArr[i]);
                boPartPO.delete();
            }
        
        }
        
        //删除附加项目
        if(!StringUtils.isNullOrEmpty(bookingOrderDto.getDelBoAddItemId())){
            String[] delBoAddItemArr=bookingOrderDto.getDelBoAddItemId().split(",");
            for(int i=0;i<delBoAddItemArr.length;i++){
                BoAddItemPO boAddItemPo=BoAddItemPO.findById(delBoAddItemArr[i]);
                boAddItemPo.delete();
            }
        }
        
        //维修项目  新增/修改
        List<BoLabourDTO> boLabourList=bookingOrderDto.getBolabourList();
        if(!CommonUtils.isNullOrEmpty(boLabourList)){
            for(BoLabourDTO dto:boLabourList){
                BoLabourPO boLabourPo=new BoLabourPO();
                //如果不为空则修改    否则新增
                if(!StringUtils.isNullOrEmpty(dto.getBoLabourId())){
                    boLabourPo=BoLabourPO.findById(dto.getBoLabourId());
                }else{
                    boLabourPo.setLong("BO_ID", id); 
                }
                setBoLabourPO(boLabourPo,dto);
                boLabourPo.saveIt();
                
            }
        }
        
        //维修配件  新增/修改
        List<BoPartDTO> boPartList=bookingOrderDto.getBoPartList();
        if(!CommonUtils.isNullOrEmpty(boPartList)){
            for(BoPartDTO dto:boPartList){
                BoPartPO boPartPo=new BoPartPO();
                //如果不为空则修改    否则新增
                if(!StringUtils.isNullOrEmpty(dto.getBoPartId())){
                    boPartPo=BoPartPO.findById(dto.getBoPartId());
                }else{
                    boPartPo.setLong("BO_ID", id); 
                }
                setBoPartPO(boPartPo, dto);
                boPartPo.saveIt();
                
            }
        }
        
        //附加项目  新增/修改
        List<BoAddItemDTO> boAddItemList=bookingOrderDto.getBoAddItemList();
        if(!CommonUtils.isNullOrEmpty(boAddItemList)){
            for(BoAddItemDTO dto:boAddItemList){
                BoAddItemPO boAddItemPo=new BoAddItemPO();
                //如果不为空则修改    否则新增
                if(!StringUtils.isNullOrEmpty(dto.getBoAddItemId())){
                    boAddItemPo=BoAddItemPO.findById(dto.getBoAddItemId());
                    boAddItemPo.setString("ADD_ITEM_NAME", dto.getAddItemName());
                }else{
                    boAddItemPo.setLong("BO_ID", id); 
                    setBoAddItemPO(boAddItemPo, dto);
                }
               
                boAddItemPo.saveIt();
                
            }
        }
        
    }
    
    /**
    * 注入预约单属性
    * @author jcsi
    * @date 2016年10月14日
    * @param bookingOrderPO
    * @param bookingOrderDto
    * @throws ServiceBizException
     */
    public void setBookingOrderPO(TtBookingOrderPO bookingOrderPO,BookingOrderDTO bookingOrderDto)throws ServiceBizException{
        bookingOrderPO.setString("BO_NO", commonNoService.getSystemOrderNo(CommonConstants.BO_NO));
        bookingOrderPO.setLong("VEHICLE_ID", bookingOrderDto.getVehicleId());
        bookingOrderPO.setString("BRAND_CODE", bookingOrderDto.getBrandCode());
        bookingOrderPO.setString("SERIES_CODE", bookingOrderDto.getSeriesCode());
        bookingOrderPO.setString("MODEL_CODE", bookingOrderDto.getModelCode());
        bookingOrderPO.setString("CONTACTOR_NAME", bookingOrderDto.getContactorName());
        bookingOrderPO.setString("CONTACTOR_MOBILE", bookingOrderDto.getContactorMobile());
        bookingOrderPO.setString("BOOKING_TYPE_CODE", bookingOrderDto.getBookingTypeCode());
        bookingOrderPO.setString("LICENSE", bookingOrderDto.getLicense());
        //资料来源
        bookingOrderPO.setLong("BOOKING_SOURCE", bookingOrderDto.getBookingSource());
        if(bookingOrderDto.getBookingComeTime().after(new Date())){
            bookingOrderPO.setTimestamp("BOOKING_COME_TIME", bookingOrderDto.getBookingComeTime());    
        }else{
            throw new ServiceBizException("预约时间不能小于当前日期");
        }
        
        bookingOrderPO.setString("SERVICE_ADVISOR_ASS", bookingOrderDto.getServiceAdvisorAss());
        bookingOrderPO.setString("REMARK", bookingOrderDto.getRemark());
        //预约状态  默认为“未到店”
        bookingOrderPO.setLong("BOOKING_ORDER_STATUS", DictCodeConstants.BOOKING_ORDER_NOT);
        bookingOrderPO.setLong("IS_DRIVING_SERVICE", bookingOrderDto.getIsDrivingService());
        bookingOrderPO.setDouble("ESTIMATE_AMOUNT", getEstimateAmount(bookingOrderDto));
        bookingOrderPO.setInteger("IS_COMFIRM", bookingOrderDto.getIsComfirm());
        
    }
     
    
    /**
     * 注入维修项目属性
     * @author jcsi
     * @date 2016年10月14日
     * @param bookingOrderPO
     * @param bookingOrderDto
     * @throws ServiceBizException
      */
     public void setBoLabourPO(BoLabourPO bolabourPo,BoLabourDTO boLabourDto)throws ServiceBizException{
         StringBuilder sb=new StringBuilder();
         sb.append("select DEALER_CODE,LABOUR_NAME,LOCAL_LABOUR_CODE,LOCAL_LABOUR_NAME,STD_LABOUR_HOUR,ASSIGN_LABOUR_HOUR ");
         sb.append("from tm_labour  ");
         sb.append("where LABOUR_CODE=? and MODEL_LABOUR_CODE=? ");
         List<Object> param=new ArrayList<Object>();
         param.add(boLabourDto.getLabourCode());
         param.add(boLabourDto.getModelLabourCode());
         Map result=DAOUtil.findFirst(sb.toString(), param);
         
         bolabourPo.setString("LABOUR_CODE", boLabourDto.getLabourCode());
         bolabourPo.setString("LABOUR_NAME", result.get("LABOUR_NAME"));
         bolabourPo.setString("LOCAL_LABOUR_CODE", result.get("LOCAL_LABOUR_CODE"));
         bolabourPo.setString("LOCAL_LABOUR_NAME", result.get("LOCAL_LABOUR_NAME"));
         //标准工时
         bolabourPo.setDouble("STD_LABOUR_HOUR", result.get("STD_LABOUR_HOUR"));
         bolabourPo.setDouble("ASSIGN_LABOUR_HOUR", result.get("ASSIGN_LABOUR_HOUR"));
         //工时单价
         bolabourPo.setDouble("LABOUR_PRICE", boLabourDto.getLabourPrice());
         //工时费
         bolabourPo.setDouble("LABOUR_AMOUNT",NumberUtil.mul2Double((Double)result.get("STD_LABOUR_HOUR"),boLabourDto.getLabourPrice()));
         bolabourPo.setString("TROUBLE_DESC", boLabourDto.getTroubleDesc());  //故障描述
         bolabourPo.setString("MODEL_LABOUR_CODE", boLabourDto.getModelLabourCode());  //项目车型组
     }
    
     /**
      * 注入维修配件属性
      * @author jcsi
      * @date 2016年10月14日
      * @param bookingOrderPO
      * @param bookingOrderDto
      * @throws ServiceBizException
       */
      public void setBoPartPO(BoPartPO boPartPo,BoPartDTO boPartDto)throws ServiceBizException{
          StringBuilder sb=new StringBuilder();
          sb.append("select DEALER_CODE,STORAGE_POSITION_CODE,PART_NAME ");
          sb.append("from tt_part_stock  ");
          sb.append("where STORAGE_CODE=? and PART_CODE=? ");
          List<Object> param=new ArrayList<Object>();
          param.add(boPartDto.getStorageCode());
          param.add(boPartDto.getPartNo());
          Map result=DAOUtil.findFirst(sb.toString(), param);
          
          boPartPo.setString("STORAGE_CODE", boPartDto.getStorageCode());
          boPartPo.setString("STORAGE_POSITION_CODE", result.get("STORAGE_POSITION_CODE"));
          boPartPo.setString("PART_NO", boPartDto.getPartNo());
          boPartPo.setString("PART_NAME",result.get("PART_NAME"));
          boPartPo.setDouble("PART_QUANTITY", boPartDto.getPartQuantity());
          boPartPo.setDouble("PART_SALES_PRICE", boPartDto.getPartSalesPrice());
          boPartPo.setDouble("PART_SALES_AMOUNT",NumberUtil.mul2Double(boPartDto.getPartQuantity(),boPartDto.getPartSalesPrice()));
          boPartPo.setLong("IS_OBLIGATED", boPartDto.getIsObligated());
          
          
          
      }
      /**
       * 注入维修项目属性
       * @author jcsi
       * @date 2016年10月14日
       * @param bookingOrderPO
       * @param bookingOrderDto
       * @throws ServiceBizException
        */
       public void setBoAddItemPO(BoAddItemPO boAddItemPo,BoAddItemDTO boAddItemDto)throws ServiceBizException{
           StringBuilder sb=new StringBuilder();
           sb.append("select DEALER_CODE,ADD_ITEM_AMOUNT ");
           sb.append("from tm_add_item  ");
           sb.append("where ADD_ITEM_CODE=? ");
           List<Object> param=new ArrayList<Object>();
           param.add(boAddItemDto.getAddItemCode());
           Map result=DAOUtil.findFirst(sb.toString(), param);
           
           
           
           boAddItemPo.setString("CHARGE_PARTITION_CODE", boAddItemDto.getChargePartitionCode());
           boAddItemPo.setString("ADD_ITEM_CODE", boAddItemDto.getAddItemCode());
           boAddItemPo.setString("ADD_ITEM_NAME", getaddItemName(boAddItemDto.getAddItemCode()));
           boAddItemPo.setDouble("ADD_ITEM_AMOUNT", result.get("ADD_ITEM_AMOUNT"));
           boAddItemPo.setDouble("RECEIVABLE_AMOUNT", boAddItemDto.getReceivableAmount());
           boAddItemPo.setString("REMARK", boAddItemDto.getRemark());
           
           
       }
       public String getaddItemName(String addItemCode){
           String sql="SELECT t.ADD_ITEM_NAME,t.DEALER_CODE from tm_add_item t where t.ADD_ITEM_CODE=? and t.DEALER_CODE=?  ";
           List<Object> queryParam=new ArrayList<Object>();
           queryParam.add(addItemCode);
           queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
           Map result= DAOUtil.findFirst(sql, queryParam);
           return result.get("ADD_ITEM_NAME").toString();
       }
    /**
    * 计算预约单中的 预估金额  （维修项目、配件、附加项目 各项金额合计。）
    * @author jcsi
    * @date 2016年10月14日
    * @param bookingOrderDto
    * @return
     */
    public Double getEstimateAmount(BookingOrderDTO bookingOrderDto){
        Double estimateAmount=new Double("0");
        List<BoAddItemDTO> boAddItemList= bookingOrderDto.getBoAddItemList();  //附加项目
        List<BoLabourDTO> boLabourList=bookingOrderDto.getBolabourList();  //维修项目
        List<BoPartDTO> boPartList=bookingOrderDto.getBoPartList();  //配件
        if(!CommonUtils.isNullOrEmpty(boAddItemList)){
            for(BoAddItemDTO dto:boAddItemList){
                estimateAmount+=dto.getReceivableAmount();
            }
        }
        if(!CommonUtils.isNullOrEmpty(boLabourList)){
            for(BoLabourDTO dto:boLabourList){
                estimateAmount+=dto.getStdLabourHour()*dto.getLabourPrice();
            }
        }
        if(!CommonUtils.isNullOrEmpty(boPartList)){
            for(BoPartDTO dto:boPartList){
                estimateAmount+=dto.getPartSalesPrice()*dto.getPartQuantity();
            }
        }
        return estimateAmount;
    }

    /**
     * 保存取送车服务信息
    * @author jcsi
    * @date 2016年10月17日
    * @param drivingServiceOrderDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#addDrivingServiceOrder(com.yonyou.dms.repair.domains.DTO.order.DrivingServiceOrderDTO)
     */
    @Override
    public void addDrivingServiceOrder(DrivingServiceOrderDTO drivingServiceOrderDto) throws ServiceBizException {
        DrivingServiceOrderPO dsoPo=new DrivingServiceOrderPO();
        setDrivingServiceOrderPO(dsoPo,drivingServiceOrderDto);
        dsoPo.saveIt();
    }
    
    /**
    * 注入取送车服务信息属性
    * @author jcsi
    * @date 2016年10月17日
    * @param dsoPo
    * @param dsoDto
     */
    public void setDrivingServiceOrderPO(DrivingServiceOrderPO dsoPo,DrivingServiceOrderDTO dsoDto){
        dsoPo.setLong("BO_ID", dsoDto.getBoId());
        dsoPo.setString("START_ADD", dsoDto.getStartAdd());
        dsoPo.setString("START_CONTACTOR_NAME", dsoDto.getStartContactorName());
        dsoPo.setString("START_CONTACTOR_MOBILE", dsoDto.getStartContactorMobile());
        dsoPo.setString("DEST_ADD", dsoDto.getDestAdd());
        dsoPo.setString("DEST_CONTACTOR_NAME", dsoDto.getDestContactorName());
        dsoPo.setString("DEST_CONTACTOR_MOBILE", dsoDto.getDestContactorMobile());
        dsoPo.setTimestamp("START_TIME", dsoDto.getStartTime());
        dsoPo.setDouble("REFER_KILOMETER", dsoDto.getReferKilometer());
        dsoPo.setDouble("REFER_PRICE", dsoDto.getReferPrice());
        dsoPo.setString("SIGN", dsoDto.getSign());
        dsoPo.setString("SERVICE_ORDER_STATUS", dsoDto.getServiceOrderStatus());
        dsoPo.setLong("IDEALERS_ID", dsoDto.getIdealersId());
    }

    /**
     * 根据id查询预约单
    * @author jcsi
    * @date 2016年10月18日
    * @param Id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchBookingOrderByBoId(java.lang.Long)
     */
    @Override
    public Map searchBookingOrderByBoId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_ID,t.DEALER_CODE,t.BO_NO,t.LICENSE,t.BRAND_CODE,t.SERIES_CODE,t.MODEL_CODE,t.VEHICLE_ID,");
        sb.append("t.CONTACTOR_NAME,t.CONTACTOR_MOBILE mobile,t.BOOKING_TYPE_CODE,t.BOOKING_SOURCE,t.BOOKING_COME_TIME,t.SERVICE_ADVISOR_ASS,");
        sb.append("t.REMARK,t.ESTIMATE_AMOUNT,t.BOOKING_ORDER_STATUS,t.IS_COMFIRM    ");
        sb.append("from TT_BOOKING_ORDER t where t.BO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findFirst(sb.toString(), queryParam);
    }

    /**
     * 根据boId查询维修项目
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchBoLabourByBoId(java.lang.Long)
     */
    @Override
    public List<Map> searchBoLabourByBoId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_LABOUR_ID,t.BO_ID,t.DEALER_CODE,t.LABOUR_CODE,t.LABOUR_NAME,t.LOCAL_LABOUR_CODE,t.LOCAL_LABOUR_NAME,t.STD_LABOUR_HOUR,");
        sb.append("t.ASSIGN_LABOUR_HOUR,t.LABOUR_PRICE,t.LABOUR_AMOUNT,t.MODEL_LABOUR_CODE   ");
        sb.append("from TT_BO_LABOUR t where t.BO_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 根据boId查询配件
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchBoPartByBoId(java.lang.Long)
     */
    @Override
    public List<Map> searchBoPartByBoId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_PART_ID,t.BO_ID,t.DEALER_CODE,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.PART_SALES_PRICE salesPriceShow,");
        sb.append("t.PART_QUANTITY canNumShow,t.PART_SALES_AMOUNT salesAmountShow,t.STORAGE_CODE storageCodeShow,t.STORAGE_POSITION_CODE storagePositionCodeShow,");
        sb.append("1 as discountShow,t.PART_SALES_AMOUNT  as disSalesAmountShow,"+DictCodeConstants.STATUS_IS_NOT+" as isFinishedShow,0.00 as discountMoney  ");
        sb.append("from TT_BO_PART t  where t.BO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 根据boId查询附加项目
    * @author jcsi
    * @date 2016年10月18日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchAddItemByBoId(java.lang.Long)
     */
    @Override
    public List<Map> searchAddItemByBoId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_ADD_ITEM_ID,t.BO_ID,t.DEALER_CODE,t.ADD_ITEM_CODE addItemCode,t.ADD_ITEM_NAME addItemName,t.ADD_ITEM_AMOUNT addItemAmount,t.CHARGE_PARTITION_CODE chargePartitionName,t.RECEIVABLE_AMOUNT receivableAmount,t.REMARK   remark   ");
        sb.append("from TT_BO_ADD_ITEM t   where t.BO_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 预约取消
    * @author jcsi
    * @date 2016年10月19日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#updateBookingOrderStatusByBoId(java.lang.Long)
     */
    @Override
    public void updateBookingOrderStatusByBoId(Long id) throws ServiceBizException {
    	TtBookingOrderPO bookingOrderPO=TtBookingOrderPO.findById(id);
        bookingOrderPO.setLong("BOOKING_ORDER_STATUS", DictCodeConstants.BOOKING_ORDER_CANCEL);
        bookingOrderPO.saveIt();
    }

    /**
     * 作废
    * @author jcsi
    * @date 2016年10月19日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#deleteByBoId(java.lang.Long)
     */
    @Override
    public void deleteByBoId(Long id) throws ServiceBizException {
    	TtBookingOrderPO bookingOrderPO=TtBookingOrderPO.findById(id);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("预约单删除：预约单号【"+bookingOrderPO.getString("BO_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_REPAIR_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        bookingOrderPO.delete();
    }

    /**
     * 工单  页面查询预约项目
    * @author jcsi
    * @date 2016年10月27日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchOrderLabourByBoId(java.lang.Long)
     */
    @Override
    public List<Map> searchOrderLabourByBoId(Long id) throws ServiceBizException {
        StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_LABOUR_ID,t.BO_ID,t.DEALER_CODE,t.LABOUR_CODE labourCode,t.LABOUR_NAME labourName,t.LOCAL_LABOUR_CODE localLabourCode,t.LOCAL_LABOUR_NAME localLabourName,t.STD_LABOUR_HOUR stdHour,");
        sb.append("t.ASSIGN_LABOUR_HOUR assignLabourHour,t.LABOUR_PRICE workHourSinglePrice,t.LABOUR_AMOUNT  workHourPrice,t.MODEL_LABOUR_CODE modeGroup, ");
        sb.append("1 as discountRate,t.LABOUR_AMOUNT as receiveMoney,0.00 as discountMoney  ");
        sb.append("from TT_BO_LABOUR t where t.BO_ID=? ");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);
    }

    /**
     * 工单  页面查询预约配件
    * @author jcsi
    * @date 2016年10月27日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchOrderPartByBoId(java.lang.Long)
     */
    @Override
    public List<Map> searchOrderPartByBoId(Long id) throws ServiceBizException {
       /* StringBuilder sb=new StringBuilder();
        sb.append("SELECT t.BO_PART_ID,t.BO_ID,t.DEALER_CODE,t.PART_NO partCodeShow,t.PART_NAME partNameShow,t.PART_SALES_PRICE salesPriceShow,");
        sb.append("t.PART_QUANTITY canNumShow,t.PART_SALES_AMOUNT salesAmountShow,t.STORAGE_CODE storageCodeShow,t.STORAGE_POSITION_CODE storagePositionCodeShow   ");
        sb.append("from TT_BO_PART t  where t.BO_ID=?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        return DAOUtil.findAll(sb.toString(), queryParam);*/
        return null;
    }

    /**
     * 根据BO_Id 查询车辆信息
    * @author jcsi
    * @date 2016年10月27日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.BookingOrderService#searchCarIfoByBoId(java.lang.Long)
     */
    @Override
    public Map searchCarIfoByBoId(Long id) throws ServiceBizException {
    	TtBookingOrderPO bookingOrder=TtBookingOrderPO.findById(id);
        if(!StringUtils.isNullOrEmpty(bookingOrder.getString("VEHICLE_ID"))){
            StringBuilder sb=new StringBuilder("SELECT b.DEALER_CODE,v.VEHICLE_ID,v.LICENSE,v.CONTACTOR_NAME,v.CONTACTOR_PHONE,mo.MODEL_NAME,");
            sb.append("v.VIN,v.ENGINE_NO,b.BO_ID  ");
            sb.append(" from tm_vehicle  v  ");
            sb.append(" INNER JOIN tt_booking_order b on v.VEHICLE_ID=b.VEHICLE_ID  ");
            sb.append(" left  join   TM_MODEL   mo  on   v.MODEL_CODE=mo.MODEL_ID ");
            sb.append(" where b.BO_ID=? ");
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(id);
            return DAOUtil.findFirst(sb.toString(), queryParam);
        }else{
            return bookingOrder.toMap();
        } 
    }
    
    
    

}
