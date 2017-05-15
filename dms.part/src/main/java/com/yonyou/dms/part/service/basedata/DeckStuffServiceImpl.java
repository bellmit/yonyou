
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : DeckStuffServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月5日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.PO.baseData.DictPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月5日
*/
@Service
public class DeckStuffServiceImpl implements DeckStuffService{

    @Override
    public PageInfoDto querySoPartDetail(Map map) throws ServiceBizException {
        String lockName=Utility.selLockerName("LOCK_USER", "TT_PART_ALLOCATE_IN", "Allocate_IN_No", map.get("soNo").toString());
        if(lockName.length()!=0){
            //用户锁定
           int lockFlag= Utility.updateByLocker("TT_PART_ALLOCATE_IN", FrameworkUtil.getLoginInfo().getUserId().toString(),
                                                "Allocate_IN_No", map.get("soNo").toString(),"LOCK_USER" );
           if(lockFlag==1){
               throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
           }
        }
//        SalesOrderPO po = SalesOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("soNo"));
//        List<Object> list = new ArrayList<>();
//        StringBuffer sql = new StringBuffer("select 12781002 as IS_SELECTED,CAST(CAST(A.BATCH_NO AS unsigned) AS CHAR)AS BATCH_NO,A.discount,A.real_price,A.so_No,A.part_No,A.DEALER_CODE, "); 
//        if (!StringUtils.isNullOrEmpty(po.get("BUSINESS_TYPE"))&&DictCodeConstants.DICT_SO_TYPE_RERURN.equals(po.get("BUSINESS_TYPE"))) {
//            sql.append(" case when A.part_Quantity>0 then -1*A.part_Quantity else A.part_Quantity  end as part_Quantity,");
//            sql.append(" case when A.part_Cost_Amount>0 then -1*A.part_Cost_Amount else A.part_Cost_Amount  end as part_Cost_Amount, ");
//            sql.append(" case when A.part_Sales_Amount>0 then -1*A.part_Sales_Amount else A.part_Sales_Amount  end as part_Sales_Amount,");
//        }else{
//            sql.append(" A.part_Quantity,");
//            sql.append(" A.part_Cost_Amount,");
//            sql.append(" A.part_Sales_Amount,");
//        }
//        sql.append(" A.storage_Code,A.unit_Code,A.storage_Position_Code,");
//        sql.append(" A.part_Cost_Price,A.part_Name,A.part_Sales_Price,A.remark,A.is_Finished,A.is_Finishedr,A.receiver,A.sender,A.send_Time,");
//        sql.append(" A.out_Stock_No,A.part_Batch_No,A.lock_User,A.non_One_Off,A.account_Mode,A.item_Id,A.LEND_ID, 0 as THIS_LEND_QUANTITY ,A.LEND_QUANTITY,A.RETURN_QUANTITY,");
//        sql.append(" (C.STOCK_QUANTITY + C.BORROW_QUANTITY -ifnull(C.LEND_QUANTITY,0)-ifnull(C.LOCKED_QUANTITY,0)");
//        sql.append(" )  AS USEABLE_STOCK,A.INTERIOR_PRICE,A.INTERIOR_AMOUNT from TT_SO_PART A ");
//        sql.append(" left join  TM_PART_STOCK  C ON A.DEALER_CODE = C.DEALER_CODE ");
//        sql.append(" AND A.D_KEY = C.D_KEY AND A.STORAGE_CODE = C.STORAGE_CODE  AND A.PART_NO = C.PART_NO");
//        sql.append(" where A.DEALER_CODE=? AND A.d_key=?");
//        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
//        list.add(CommonConstants.D_KEY);
//        return DAOUtil.pageQuery(sql.toString(), list);
        return null;
    }

    @Override
    public PageInfoDto queryServiceOrderBySoNo(Map map) throws ServiceBizException {
        DictPO find = DictPO.findFirst("type = ?", 8036);
        List<Object> list = new ArrayList<>();
        String a = "";
        StringBuffer sql = new StringBuffer();
        sql.append("select distinct a.*,b.MODEL_CODE AS MODEL  from TT_SALES_ORDER a  left join TM_VS_PRODUCT b on a.DEALER_CODE=b.DEALER_CODE ");
        sql.append(" and a.PRODUCT_CODE=b.PRODUCT_CODE");
        sql.append(" left join ( select distinct a.DEALER_CODE,a.so_no ,count(b.item_id) AS order_num , ");
        sql.append(" count(case when b.is_finished=12781001 then b.item_id end) AS finished_num");
        sql.append(" from tt_sales_order a left join tt_so_part b on a.DEALER_CODE=b.DEALER_CODE and a.so_no=b.so_no  ");
        sql.append(" group by a.DEALER_CODE,a.so_no) c on a.DEALER_CODE=c.DEALER_CODE and a.so_no=c.so_no");
        sql.append(" where a.DEALER_CODE=? and a.d_key=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if(find.get("STATUS")!= null && find.get("STATUS").equals(DictCodeConstants.DICT_IS_YES)){
            sql.append(" and (a.SO_STATUS = 13011025 OR a.SO_STATUS = 13011030 OR a.SO_STATUS = 13011035 OR a.SO_STATUS = 13011075 or a.SO_STATUS= 13011055)");
        }
        a = Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_IS_ACCOUNT_ASSIGN));
        if (!StringUtils.isNullOrEmpty(a)&&DictCodeConstants.DICT_IS_TYPE.equals(a)) {
            sql.append(" and  ( ( a.BUSINESS_TYPE="+DictCodeConstants.DICT_SO_TYPE_SERVICE+" or a.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL+" or a.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_RERURN+") ");
            if(find.get("STATUS")!= null && find.get("STATUS").equals(DictCodeConstants.DICT_IS_YES)){
                sql.append(" and ( a.PAY_OFF = "+DictCodeConstants.DICT_IS_YES+"  or a.LOSSES_PAY_OFF = "+DictCodeConstants.DICT_IS_YES+" ) ");
                sql.append(" or (a.ORDER_SORT="+DictCodeConstants.FDICT_SALES_ORDER_SORT_PRE+")) ");
            }else{
                sql.append(")");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("soNo"))) {
            sql.append(" and  a.SO_NO like ?");
            list.add("%"+map.get("soNo")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get(""))) {
            sql.append(" and a.CUSTOMER_NO= ？");
        }
        if (!StringUtils.isNullOrEmpty(map.get(""))) {
            sql.append(" and a.VIN like ?");
        }
        if (!StringUtils.isNullOrEmpty(map.get("isFinish"))) {
            if (DictCodeConstants.DICT_SN_FINISHED_STATUS_NOT.equals(map.get("isFinish"))) {
                sql.append(" and c.finished_num=0 ");
            }else if (DictCodeConstants.DICT_SN_FINISHED_STATUS_PART.equals(map.get("isFinish"))) {
                sql.append(" and (c.finished_num>0 and c.order_num>c.finished_num)");
            }else if (DictCodeConstants.DICT_SN_FINISHED_STATUS_ALL.equals(map.get("isFinish"))) {
                sql.append(" and (c.finished_num>0 and c.order_num=c.finished_num)");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("createDateBegin"))&&!StringUtils.isNullOrEmpty(map.get("createDateEnd"))) {
            Utility.getDateCond("A", "CREATE_DATE", map.get("createDateBegin").toString(), map.get("createDateEnd").toString());
        }
        return DAOUtil.pageQuery(sql.toString(), list);
    }

}
