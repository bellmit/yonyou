
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : AbsentDetailServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月26日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月26日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtAlertMsgPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMsgTypeMappingPO;
import com.yonyou.dms.common.domains.PO.basedata.TtShortPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.TtShortPartDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月26日
*/
@Service
public class AbsentDetailServiceImpl implements AbsentDetailService{

    @Override
    public PageInfoDto queryShortPart(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT  A.IS_BO,B.STOCK_QUANTITY,0 AS CHOSE,  A.SHORT_ID, A.DEALER_CODE, A.STORAGE_CODE, A.PART_NO, A.PART_NAME, ");
        sql.append("A.STORAGE_POSITION_CODE, A.IN_OUT_TYPE, A.SHORT_TYPE, A.SHEET_NO, A.CLOSE_STATUS, ");
        sql.append("A.IS_URGENT, A.LICENSE, A.SHORT_QUANTITY, A.HANDLER, A.CUSTOMER_NAME, A.PHONE, ");
        sql.append("A.SEND_TIME, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, ");
        sql.append("D.STATUS_DESC AS OUT_TYPE, C.USER_NAME AS SENDER_NAME, ");
        sql.append("(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY) AS REAL_QUANTITY ");
        sql.append("FROM TT_SHORT_PART A left join TM_PART_STOCK B on A.DEALER_CODE = B.DEALER_CODE ");
        sql.append("and A.STORAGE_CODE = B.STORAGE_CODE and A.PART_NO = B.PART_NO ");
        sql.append("left join TM_USER C on A.DEALER_CODE = C.DEALER_CODE and A.CREATED_BY = C.USER_ID ");
        sql.append("left join TM_SYSTEM_STATUS D on A.IN_OUT_TYPE = D.STATUS_CODE ");
        sql.append("WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString());
        }
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" AND A.STORAGE_CODE =? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" AND A.PART like ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("SHORT_TYPE"))) {
            sql.append(" AND A.SHORT_TYPE=? ");
            list.add(map.get("SHORT_TYPE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("CLOSE_STATUS"))) {
            sql.append(" AND A.CLOSE_STATUS=? ");
            list.add(map.get("CLOSE_STATUS"));
        }
        sql.append("ORDER BY A.SHORT_ID");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryShortPartImport(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT  A.IS_BO,B.STOCK_QUANTITY,0 AS CHOSE,  A.SHORT_ID, A.DEALER_CODE, A.STORAGE_CODE, A.PART_NO, A.PART_NAME, ");
        sql.append("A.STORAGE_POSITION_CODE, A.IN_OUT_TYPE, A.SHORT_TYPE, A.SHEET_NO, A.CLOSE_STATUS, ");
        sql.append("A.IS_URGENT, A.LICENSE, A.SHORT_QUANTITY, A.HANDLER, A.CUSTOMER_NAME, A.PHONE, ");
        sql.append("A.SEND_TIME, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, ");
        sql.append("D.STATUS_DESC AS OUT_TYPE, C.USER_NAME AS SENDER_NAME, ");
        sql.append("(B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY) AS REAL_QUANTITY ");
        sql.append("FROM TT_SHORT_PART A left join TM_PART_STOCK B on A.DEALER_CODE = B.DEALER_CODE ");
        sql.append("and A.STORAGE_CODE = B.STORAGE_CODE and A.PART_NO = B.PART_NO ");
        sql.append("left join TM_USER C on A.DEALER_CODE = C.DEALER_CODE and A.CREATED_BY = C.USER_ID ");
        sql.append("left join TM_SYSTEM_STATUS D on A.IN_OUT_TYPE = D.STATUS_CODE ");
        sql.append("WHERE A.DEALER_CODE =? AND A.D_KEY =? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("BOOK_BEGIN_DATE"))&&!StringUtils.isNullOrEmpty(map.get("BOOK_END_DATE"))) {
            Utility.getDateCond("A", "CREATED_AT", map.get("BOOK_BEGIN_DATE").toString(), map.get("BOOK_END_DATE").toString());
        }
        if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
            sql.append(" AND A.STORAGE_CODE =? ");
            list.add(map.get("STORAGE_CODE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("PART_NO"))) {
            sql.append(" AND A.PART like ");
            list.add("%"+map.get("PART_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("SHORT_TYPE"))) {
            sql.append(" AND A.SHORT_TYPE=? ");
            list.add(map.get("SHORT_TYPE"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("CLOSE_STATUS"))) {
            sql.append(" AND A.CLOSE_STATUS=? ");
            list.add(map.get("CLOSE_STATUS"));
        }
        sql.append("ORDER BY A.SHORT_ID");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public void save(TtShortPartDTO dto) throws ServiceBizException {
        String[] a = dto.getShortid().split(";");
        List<Object> list = new ArrayList<>();
        for(int i=0;i<a.length;i++){
            String[] b = a[i].split(",");
            String shortId = b[0];
            String isUrgent = b[1];
            String closeStatus = b[2];
            TtShortPartPO partPO = new TtShortPartPO();
            partPO.update("CLOSE_STATUS=? , IS_URGENT=?", "SHORT_ID=? and D_KEY=?", closeStatus,isUrgent,shortId,CommonConstants.D_KEY);
            if (DictCodeConstants.DICT_PART_SHORT_CLOSE_STATUS_TYPE_END_A_CASE.equals(closeStatus)) {
                String msgInfo = "到货提醒 配件代码:" + partPO.getString("PART_NO") + ";配件名称:" + partPO.getString("PART_NAME") + ";到货数量:" + partPO.getString("SHORT_QUANTITY");
                TtAlertMsgPO alertMsgPO = new TtAlertMsgPO();
                alertMsgPO.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                alertMsgPO.set("MSG_INFO",msgInfo);
                alertMsgPO.set("MSG_TIMESTAMP",new Date());
                alertMsgPO.set("ALERT_MSG_TYPE",DictCodeConstants.DICT_MSG_TYPE_ARRIVAL);
                alertMsgPO.set("D_KEY",DictCodeConstants.D_KEY);
                alertMsgPO.saveIt();
                
                TtMsgTypeMappingPO mappingPO = TtMsgTypeMappingPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),FrameworkUtil.getLoginInfo().getUserId(),DictCodeConstants.DICT_MSG_TYPE_ARRIVAL);
                mappingPO.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                mappingPO.set("ALERT_MSG_TYPE",DictCodeConstants.DICT_MSG_TYPE_ARRIVAL);
                mappingPO.set("IS_READ",DictCodeConstants.DICT_IS_NO);
                mappingPO.set("USER_ID",FrameworkUtil.getLoginInfo().getUserId());
                mappingPO.set("D_KEY",DictCodeConstants.D_KEY);
                mappingPO.saveIt();
            }
        }
    }
}
