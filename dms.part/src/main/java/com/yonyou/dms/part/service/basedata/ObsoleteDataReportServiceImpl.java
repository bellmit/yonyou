
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ObsoleteDataReportServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年4月13日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月13日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.ObsoleteDataReportDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ObsoleteDataReportListDTO;
import com.yonyou.dms.part.domains.PO.basedata.ObsoleteDataReportPO;

/**
 * 呆滞品数据上报
 * 
 * @author sunguowei
 * @date 2017年4月13日
 */
@Service
public class ObsoleteDataReportServiceImpl implements ObsoleteDataReportService {

    /**
     * 呆滞品数据查询 sunguowei
     * 
     * @date 2017年4月13日
     * @param queryParams
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#queryObsoleteDataReport(java.util.Map)
     */

    @Override
    public PageInfoDto queryObsoleteDataReport(Map<String, String> queryParams) throws ServiceBizException{
        StringBuffer sql = new StringBuffer("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY,SUM(REPORTED_NUMBER) AS REPORTED_NUMBER from (select ");
        sql.append(DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, " + DictCodeConstants.DICT_IS_YES
                   + " AS IS_SELECT, ");
        sql.append(" A.DEALER_CODE,A.STORAGE_CODE,vst.STORAGE_NAME as STORAGE_CODE_NAME,A.part_no,A.STORAGE_POSITION_CODE,A.PART_NAME,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.COST_PRICE,A.UNIT_CODE,A.COST_AMOUNT,A.SALES_PRICE, ");
        sql.append(" A.STOCK_QUANTITY,D.REPORTED_NUMBER " + "from tm_part_stock A inner join( ");
        sql.append(CommonConstants.VM_PART_INFO);
        sql.append(" )B ON A.DEALER_CODE=B.DEALER_CODE and A.part_no=B.part_no left join(");
        sql.append(CommonConstants.VM_STORAGE);
        sql.append(" )vst on vst.DEALER_CODE=A.DEALER_CODE and vst.STORAGE_CODE=A.STORAGE_CODE LEFT JOIN TM_DEAD_PARTS_MANAGE D ON D.DEALER_CODE=A.DEALER_CODE AND D.STORAGE_CODE=A.STORAGE_CODE AND D.PART_NO=A.PART_NO left join TM_STORAGE C on C.DEALER_CODE=A.DEALER_CODE and C.STORAGE_CODE=A.STORAGE_CODE where 1=1");
        sql.append(" and A.DEALER_CODE=" + "'" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        sql.append(" and A.D_KEY=" + DictCodeConstants.D_KEY + " ");
        sql.append(" and (timestampdiff(QUARTER,timestamp(now()), timestamp(slow_moving_date)))>=(select BACK_LOGGED_DATE from TM_SLOW_MOVING_DATE )  and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0");
        sql.append(" AND C.STORAGE_TYPE = " + DictCodeConstants.PART_WAREHOUSE);
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO LIKE ? ");
            queryParam.add("%" + queryParams.get("partNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.STORAGE_CODE= ?");
            queryParam.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
            sql.append(" AND A.PART_NAME LIKE ? ");
            queryParam.add("%" + queryParams.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))) {
            sql.append(" AND A.STORAGE_POSITION_CODE LIKE ?");
            queryParam.add("%" + queryParams.get("storagePositionCode") + "%");
        }

        sql.append(" )SS GROUP BY DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY ");

        return DAOUtil.pageQuery(sql.toString(), queryParam);
    }

    /**
     * @author Administrator
     * @date 2017年4月13日
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#queryStorageCode()
     */

    @Override
    public List<Map> queryStorageCode() {
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME,STORAGE_TYPE FROM tm_storage WHERE 1=1 AND STORAGE_TYPE="
                                              + DictCodeConstants.PART_WAREHOUSE);
        List<Object> queryParam = new ArrayList<>();
        return DAOUtil.findAll(sql.toString(), queryParam);
    }

    /**
     * @author Administrator
     * @date 2017年4月17日
     * @param id
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#queryObsoleteDataReportById(java.lang.String)
     */

    @Override
    public PageInfoDto queryObsoleteDataReportById(String id) {
        StringBuilder sql = new StringBuilder("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select REAL_TIME_NUMBER,DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY,SUM(REPORTED_NUMBER),CONTACTS,PHONE,ADDRESS AS REPORTED_NUMBER from (select ");
        sql.append(DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, " + DictCodeConstants.DICT_IS_YES
                   + " AS IS_SELECT, ");
        sql.append(" A.DEALER_CODE,A.STORAGE_CODE,vst.STORAGE_NAME as STORAGE_CODE_NAME,A.part_no,A.STORAGE_POSITION_CODE,A.PART_NAME,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.COST_PRICE,A.UNIT_CODE,A.COST_AMOUNT,A.SALES_PRICE, ");
        sql.append(" A.STOCK_QUANTITY,D.REPORTED_NUMBER,D.CONTACTS,D.PHONE,D.ADDRESS,D.REAL_TIME_NUMBER "
                   + "from tm_part_stock A inner join( ");
        sql.append(CommonConstants.VM_PART_INFO);
        sql.append(" )B ON A.DEALER_CODE=B.DEALER_CODE and A.part_no=B.part_no left join(");
        sql.append(CommonConstants.VM_STORAGE);
        sql.append(" )vst on vst.DEALER_CODE=A.DEALER_CODE and vst.STORAGE_CODE=A.STORAGE_CODE LEFT JOIN TM_DEAD_PARTS_MANAGE D ON D.DEALER_CODE=A.DEALER_CODE AND D.STORAGE_CODE=A.STORAGE_CODE AND D.PART_NO=A.PART_NO left join TM_STORAGE C on C.DEALER_CODE=A.DEALER_CODE and C.STORAGE_CODE=A.STORAGE_CODE where 1=1");
        sql.append(" and A.DEALER_CODE=" + "'" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        sql.append(" and A.D_KEY=" + DictCodeConstants.D_KEY + " ");
        sql.append(" and (timestampdiff(QUARTER,timestamp(now()), timestamp(slow_moving_date)))>=(select BACK_LOGGED_DATE from TM_SLOW_MOVING_DATE )  and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0");
        sql.append(" AND A.PART_NO =" + "'" + id + "'");
        sql.append(" AND C.STORAGE_TYPE = " + DictCodeConstants.PART_WAREHOUSE);
        sql.append(" )SS GROUP BY REAL_TIME_NUMBER,DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY,CONTACTS,PHONE,ADDRESS ");

        PageInfoDto pageQuery = DAOUtil.pageQuery(sql.toString(), queryParam);
        List<Map> rows = pageQuery.getRows();

        StringBuilder sql2 = new StringBuilder("");
        List<Object> queryParam2 = new ArrayList<>();
        sql2.append(" select DEALER_CODE,CONTACTS,PHONE,ADDRESS from TM_DEAD_PARTS_MANAGE where DEALER_CODE ='"
                    + FrameworkUtil.getLoginInfo().getDealerCode() + "' ORDER BY CREATED_AT DESC LIMIT 1");
        Map findFirst = DAOUtil.findFirst(sql2.toString(), queryParam2);
        String CONTACTS = (String) findFirst.get("CONTACTS");
        String PHONE = (String) findFirst.get("PHONE");
        String ADDRESS = (String) findFirst.get("ADDRESS");
        for (Map row : rows) {
            row.put("CONTACTS", CONTACTS);
            row.put("PHONE", PHONE);
            row.put("ADDRESS", ADDRESS);
        }
        return pageQuery;
    }

    /**
     * @author Administrator
     * @date 2017年4月18日
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#queryContacts()
     */

    @Override
    public Map<String, String> queryContacts() {
        StringBuilder sql = new StringBuilder("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select DEALER_CODE,CONTACTS,PHONE,ADDRESS from TM_DEAD_PARTS_MANAGE where DEALER_CODE ='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "' ORDER BY CREATED_AT DESC LIMIT 1 ");
        return DAOUtil.findFirst(sql.toString(), queryParam);
    }

    /**
     * @author Administrator
     * @date 2017年4月18日
     * @param queryParams (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#addObsoleteDataReport(java.util.Map)
     */

    @Override
    public void addObsoleteDataReport(ObsoleteDataReportDTO obsoleteDataReportDTO) {
        StringBuilder sql = new StringBuilder("");
        sql.append(" select DEALER_CODE,BACK_LOGGED_DATE,VALIDITY_DATE from TM_SLOW_MOVING_DATE where DEALER_CODE ='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        List<Object> queryParam = new ArrayList<>();
        Map findFirst = DAOUtil.findFirst(sql.toString(), queryParam);
        int validityDate = Integer.parseInt(findFirst.get("VALIDITY_DATE").toString());
        for (ObsoleteDataReportListDTO address : obsoleteDataReportDTO.getAddressList()) {
            ObsoleteDataReportPO obsoleteDataReportPO = new ObsoleteDataReportPO();

            obsoleteDataReportPO.set("STORAGE_POSITION_CODE", address.getStoragePositionCode());
            obsoleteDataReportPO.set("PART_NO", address.getPartNo());
            obsoleteDataReportPO.set("PART_NAME", address.getPartName());
            obsoleteDataReportPO.set("UNIT_CODE", address.getUnitCode());
            obsoleteDataReportPO.set("REPORTED_PRICE", address.getReportedPrice());
            obsoleteDataReportPO.set("REPORTED_TOTAL", address.getReportedTotal());
            obsoleteDataReportPO.set("REPORTED_NUMBER", address.getReportedNumber());
            obsoleteDataReportPO.set("REPORT_PERSON", address.getReportPerson());
            obsoleteDataReportPO.set("STORAGE_CODE", address.getStorageCode());
            obsoleteDataReportPO.set("CONTACTS", obsoleteDataReportDTO.getContacts());
            obsoleteDataReportPO.set("PHONE", obsoleteDataReportDTO.getPhone());
            obsoleteDataReportPO.set("ADDRESS", obsoleteDataReportDTO.getAddress());
            obsoleteDataReportPO.set("REPORTED_DATE", new Date());
            if (!StringUtils.isNullOrEmpty(address.getCostPrice())) {
                obsoleteDataReportPO.set("REPORTED_COST_PRICE", address.getCostPrice());
            }
            if (!StringUtils.isNullOrEmpty(address.getSalesPrice())) {
                obsoleteDataReportPO.set("REPORTED_SALE_PRICE", address.getSalesPrice());
            }
            obsoleteDataReportPO.set("D_KEY", CommonConstants.D_KEY);
            Calendar curr = Calendar.getInstance();
            curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) + validityDate); // 增加呆滞品过期月
            obsoleteDataReportPO.set("EXPIRATION_DATE", curr.getTime());
            obsoleteDataReportPO.saveIt();
        }
    }

    /**
     * @author Administrator
     * @date 2017年4月18日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.ObsoleteDataReportService#exportObsoleteDataReport(java.util.Map)
     */

    @Override
    public List<Map> exportObsoleteDataReport(Map<String, String> queryParams) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("");
        List<Object> queryParam = new ArrayList<>();
        sql.append(" select DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY,SUM(REPORTED_NUMBER) AS REPORTED_NUMBER from (select ");
        sql.append(DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, " + DictCodeConstants.DICT_IS_YES
                   + " AS IS_SELECT, ");
        sql.append(" A.DEALER_CODE,A.STORAGE_CODE,vst.STORAGE_NAME as STORAGE_CODE_NAME,A.part_no,A.STORAGE_POSITION_CODE,A.PART_NAME,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.COST_PRICE,A.UNIT_CODE,A.COST_AMOUNT,A.SALES_PRICE, ");
        sql.append(" A.STOCK_QUANTITY,D.REPORTED_NUMBER " + "from tm_part_stock A inner join( ");
        sql.append(CommonConstants.VM_PART_INFO);
        sql.append(" )B ON A.DEALER_CODE=B.DEALER_CODE and A.part_no=B.part_no left join(");
        sql.append(CommonConstants.VM_STORAGE);
        sql.append(" )vst on vst.DEALER_CODE=A.DEALER_CODE and vst.STORAGE_CODE=A.STORAGE_CODE LEFT JOIN TM_DEAD_PARTS_MANAGE D ON D.DEALER_CODE=A.DEALER_CODE AND D.STORAGE_CODE=A.STORAGE_CODE AND D.PART_NO=A.PART_NO left join TM_STORAGE C on C.DEALER_CODE=A.DEALER_CODE and C.STORAGE_CODE=A.STORAGE_CODE where 1=1");
        sql.append(" and A.DEALER_CODE=" + "'" + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        sql.append(" and A.D_KEY=" + DictCodeConstants.D_KEY + " ");
        sql.append(" and (timestampdiff(QUARTER,timestamp(now()), timestamp(slow_moving_date)))>=(select BACK_LOGGED_DATE from TM_SLOW_MOVING_DATE )  and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0");
        sql.append(" AND C.STORAGE_TYPE = " + DictCodeConstants.PART_WAREHOUSE);
        if (!StringUtils.isNullOrEmpty(queryParams.get("partNo"))) {
            sql.append(" AND A.PART_NO LIKE ? ");
            queryParam.add("%" + queryParams.get("partNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storageCode"))) {
            sql.append(" AND A.STORAGE_CODE= ?");
            queryParam.add(queryParams.get("storageCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
            sql.append(" AND A.PART_NAME LIKE ? ");
            queryParam.add("%" + queryParams.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("storagePositionCode"))) {
            sql.append(" AND A.STORAGE_POSITION_CODE LIKE ?");
            queryParam.add("%" + queryParams.get("storagePositionCode") + "%");
        }

        sql.append(" )SS GROUP BY DEALER_CODE,STORAGE_CODE,STORAGE_CODE_NAME,PART_NO,STORAGE_POSITION_CODE,PART_NAME,USEABLE_STOCK,COST_PRICE,UNIT_CODE,COST_AMOUNT,SALES_PRICE,STOCK_QUANTITY ");

        return DAOUtil.findAll(sql.toString(), queryParam);
    }

}
