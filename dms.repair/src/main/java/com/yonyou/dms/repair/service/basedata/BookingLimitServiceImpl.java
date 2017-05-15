
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : BookingLimitServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年10月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年10月12日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO;
import com.yonyou.dms.repair.domains.PO.basedata.BookingLimitPO;

/**
 * 预约限量设置
 * 
 * @author zhanshiwei
 * @date 2016年10月12日
 */
@Service
public class BookingLimitServiceImpl implements BookingLimitService {

    /**
     * 查询预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.BookingLimitService#queryBookingLimit(java.util.Map)
     */

    @Override
    public PageInfoDto queryBookingLimit(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("select li.BOOKING_ID,li.DEALER_CODE,li.BOOKING_TYPE_CODE,li.BEGIN_TIME,li.END_TIME,li.SATURATION,rt.REPAIR_TYPE_NAME from  TM_BOOKING_LIMIT li  ,TM_REPAIR_TYPE rt where 1=1 and rt.REPAIR_TYPE_CODE=li.BOOKING_TYPE_CODE and rt.DEALER_CODE=li.DEALER_CODE ");
        List<Object> params = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("bookingTypeCode"))) {
            sqlSb.append(" and BOOKING_TYPE_CODE = ?");
            params.add(queryParam.get("bookingTypeCode"));
        }

        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), params);
        return pageInfoDto;
    }

    /**
     * 根据ID预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.BookingLimitService#findBookingLimitById(java.lang.Long)
     */

    @Override
    public BookingLimitPO findBookingLimitById(Long id) throws ServiceBizException {

        return BookingLimitPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
    }

    /**
     * 新增预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param limidto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.BookingLimitService#addBookingLimit(com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO)
     */

    @Override
    public String addBookingLimit(BookingLimitDTO limidto) throws ServiceBizException {
        BookingLimitPO lomipo = new BookingLimitPO();
        CheckBookingLimit(limidto,null);
        setBookingLimitPO(lomipo, limidto);
        lomipo.saveIt();
        return lomipo.getString("BOOKING_TYPE_CODE");
    }

    /**
     * 修改预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param id
     * @param lomidto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.BookingLimitService#modifyBookingLimit(java.lang.Long,
     * com.yonyou.dms.repair.domains.DTO.basedata.BookingLimitDTO)
     */

    @Override
    public void modifyBookingLimit(Long id, BookingLimitDTO lomidto) throws ServiceBizException {
        BookingLimitPO lomipo = BookingLimitPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        CheckBookingLimit(lomidto,lomipo.getLong("BOOKING_ID"));
        setBookingLimitPO(lomipo, lomidto);
        lomipo.saveIt();
    }

    /**
     * 设置BookingLimitPO属性
     * 
     * @author zhanshiwei
     * @date 2016年10月12日
     * @param lomipo
     * @param lomidto
     */

    public void setBookingLimitPO(BookingLimitPO lomipo, BookingLimitDTO limidto) {
        lomipo.setString("BOOKING_TYPE_CODE", limidto.getBookingTypeCode());
        lomipo.setString("BEGIN_TIME", limidto.getBeginTime());
        lomipo.setString("END_TIME", limidto.getEndTime());
        lomipo.setDouble("SATURATION", limidto.getSaturation());
    }

    /**
     * 删除预约限量设置
     * 
     * @author zhanshiwei
     * @date 2016年10月13日
     * @param id
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.basedata.BookingLimitService#deleteBookingLimit(java.lang.Long)
     */

    @Override
    public void deleteBookingLimit(Long id) throws ServiceBizException {
        BookingLimitPO lomipo = BookingLimitPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        lomipo.delete();
    }
    
    /**
    * 检查预约限量设置
    * @author zhanshiwei
    * @date 2016年10月13日
    * @param limidto
    */
    	
    public void CheckBookingLimit(BookingLimitDTO limidto,Long id){
        if(limidto.getBeginTime().compareTo(limidto.getEndTime())>0){
            throw new ServiceBizException("结束时间必须大于开始时间!");
        }
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select li.BOOKING_ID,li.DEALER_CODE,li.BOOKING_TYPE_CODE from  TM_BOOKING_LIMIT li  where 1=1 ");
        if (!StringUtils.isNullOrEmpty(id)) {
            sqlSb.append(" and BOOKING_ID<>?");
            params.add(id);
        }
        sqlSb.append(" and BOOKING_TYPE_CODE=?");
        params.add(limidto.getBookingTypeCode());
        sqlSb.append(" and BEGIN_TIME=?");
        params.add(limidto.getBeginTime());
        sqlSb.append(" and END_TIME=?");
        params.add(limidto.getEndTime());

        if(DAOUtil.findAll(sqlSb.toString(), params).size()>0){
            throw new ServiceBizException("每种预约类型下的时间段，不可有交集的时间段!");
        }
    }
}
