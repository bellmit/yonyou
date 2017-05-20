
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartInventoryServcieImpl.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年7月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月26日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.PartInventoryItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartCodesDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPartInventoryDTO;
import com.yonyou.dms.part.domains.DTO.basedata.TtPartInventoryItemDTO;

/**
 * PartInventoryService实现类
 * @author ZhengHe
 * @date 2016年7月26日
 */
@Service
public class PartInventoryServiceImpl implements PartInventoryService{

    @Autowired
    private OperateLogService operateLogService;
    
    /**
     * 根据条件查询盘点单信息
     * @author ZhengHe
     * @date 2016年7月26日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventory(java.util.Map)
     */
    @Override
    public PageInfoDto queryPartInventory(Map<String, String> queryParam)throws ServiceBizException{
        /*  
        
SELECT A.DEALER_CODE, A.INVENTORY_NO, A.INVENTORY_DATE, A.PROFIT_AMOUNT, A.LOSS_COUNT, A.LOSS_AMOUNT, A.HANDLER,
B.EMPLOYEE_NAME,A.PROFIT_COUNT, A.IS_CONFIRMED, A.IS_FINISHED, A.PROFIT_TAG, A.LOSS_TAG,A.REMARK,A.LOCK_USER 
  FROM TT_PART_INVENTORY A LEFT JOIN TM_EMPLOYEE  B ON A.DEALER_CODE=b.DEALER_CODE AND A.HANDLER=B.EMPLOYEE_NO
   WHERE A.DEALER_CODE = '2100000'  AND A.IS_CONFIRMED = 12781001  AND A.IS_FINISHED = 12781002  AND A.D_KEY = 0 
  AND    A.INVENTORY_NO   LIKE   '%1%'
  */
        String number=Utility.getLikeCond(null, "A.INVENTORY_NO", queryParam.get("INVENTORY_NO"), "AND");
        StringBuffer sql=new StringBuffer("SELECT A.DEALER_CODE, A.INVENTORY_NO, A.INVENTORY_DATE, A.PROFIT_AMOUNT, A.LOSS_COUNT, A.LOSS_AMOUNT, A.HANDLER,");
        sql.append("B.EMPLOYEE_NAME,A.PROFIT_COUNT, A.IS_CONFIRMED, A.IS_FINISHED, A.PROFIT_TAG, A.LOSS_TAG,A.REMARK,A.LOCK_USER ");
        sql.append("  FROM TT_PART_INVENTORY A LEFT JOIN TM_EMPLOYEE  B ON A.DEALER_CODE=b.DEALER_CODE AND A.HANDLER=B.EMPLOYEE_NO " );
        sql.append(" WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' "); 
        sql.append(" AND A.IS_CONFIRMED = " + CommonConstants.DICT_IS_YES + " ");
        sql.append( " AND A.IS_FINISHED = " + CommonConstants.DICT_IS_NO + " " + " AND A.D_KEY = " + CommonConstants.D_KEY + " "+number );
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), null);
        return pageinfoDto;
    }
    /**
     * 盘点盈利查询
    * @author yujiangheng
    * @date 2017年5月13日
    * @param inventoryNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryprofitItems(java.lang.String)
     */
    @Override
    public PageInfoDto queryPartInventoryprofitItems(String inventoryNo) throws ServiceBizException {
      /*//  核对加锁：
        String lockName=Utility.selLockerName("LOCK_USER", "TT_PART_INVENTORY", "INVENTORY_NO", inventoryNo);
           //System.out.println("___________________________________________________"+lockName);
           if(lockName.length()!=0){
               //用户锁定
              int lockFlag= Utility.updateByLocker("TT_PART_INVENTORY", FrameworkUtil.getLoginInfo().getEmployeeNo(),
                                                   "INVENTORY_NO", inventoryNo,"LOCK_USER" );
              if(lockFlag==1){
                  throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
              }
           }*/
        String number = "";
        String pa = " and A.PROFIT_LOSS_QUANTITY  > 0";

        if (inventoryNo != null && inventoryNo.trim().length() != 0) {
            number = " and A.INVENTORY_NO = '" + inventoryNo + "' ";
        } else{
            number = " and 1 = 1 ";
        }
        StringBuffer sql = new StringBuffer("SELECT A.ITEM_ID, A.INVENTORY_NO, A.DEALER_CODE, A.STORAGE_CODE,B.STORAGE_NAME, A.PART_BATCH_NO,  A.PART_NO, A.PART_NAME, A.UNIT_CODE, A.CURRENT_STOCK, ");
        sql.append("A.BORROW_QUANTITY,  A.STORAGE_POSITION_CODE, A.LEND_QUANTITY, A.REAL_STOCK, A.CHECK_QUANTITY, ABS(PROFIT_LOSS_QUANTITY) PROFIT_LOSS_QUANTITY, A.COST_PRICE, A.PROFIT_LOSS_AMOUNT    ");
        sql.append( " FROM TT_PART_INVENTORY_ITEM  A  ");
        sql.append(" LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE   ");
        sql.append( "  WHERE  A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()+ "' " );
        sql.append(" AND  A.D_KEY = " + CommonConstants.D_KEY + " " + number + pa);
        System.out.println(sql.toString());
        return DAOUtil.pageQuery(sql.toString(), null);
    }
    /**
     * 盘点亏损查询
    * @author yujiangheng
    * @date 2017年5月13日
    * @param inventoryNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventorylossItems(java.lang.String)
     */
    @Override
    public PageInfoDto queryPartInventorylossItems(String inventoryNo) throws ServiceBizException {
        //  核对加锁：
        String lockName=Utility.selLockerName("LOCK_USER", "TT_PART_INVENTORY", "INVENTORY_NO", inventoryNo);
           //System.out.println("___________________________________________________"+lockName);
           if(lockName.length()!=0){
               //用户锁定
              int lockFlag= Utility.updateByLocker("TT_PART_INVENTORY", FrameworkUtil.getLoginInfo().getEmployeeNo(),
                                                   "INVENTORY_NO", inventoryNo,"LOCK_USER" );
              if(lockFlag==1){
                  throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
              }
           }
        String number = "";
        String pa = " and A.PROFIT_LOSS_QUANTITY  <  0";

        if (inventoryNo != null && inventoryNo.trim().length() != 0) {
            number = " and A.INVENTORY_NO = '" + inventoryNo + "' ";
        } else{
            number = " and 1 = 1 ";
        }
        StringBuffer sql = new StringBuffer("SELECT A.ITEM_ID, A.INVENTORY_NO, A.DEALER_CODE, A.STORAGE_CODE,B.STORAGE_NAME, A.PART_BATCH_NO,  A.PART_NO, A.PART_NAME, A.UNIT_CODE, A.CURRENT_STOCK, ");
        sql.append("A.BORROW_QUANTITY,  A.STORAGE_POSITION_CODE, A.LEND_QUANTITY, A.REAL_STOCK, A.CHECK_QUANTITY, ABS(PROFIT_LOSS_QUANTITY) PROFIT_LOSS_QUANTITY, A.COST_PRICE, A.PROFIT_LOSS_AMOUNT    ");
        sql.append( " FROM TT_PART_INVENTORY_ITEM  A  ");
        sql.append(" LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE   ");
        sql.append( "  WHERE  A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()+ "' " );
        sql.append(" AND  A.D_KEY = " + CommonConstants.D_KEY + " " + number + pa);
        System.out.println(sql.toString());
        return DAOUtil.pageQuery(sql.toString(), null);
    }
    /**
     * 盘点盈利导出查询
    * @author yujiangheng
    * @date 2017年5月15日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryToExport1(java.util.Map)
     */
    @Override
    public List<Map> queryToExport1(String inventoryNo) throws ServiceBizException {
        String number = "";
        String pa = " and A.PROFIT_LOSS_QUANTITY  > 0";

        if (inventoryNo != null && inventoryNo.trim().length() != 0) {
            number = " and A.INVENTORY_NO = '" + inventoryNo + "' ";
        } else{
            number = " and 1 = 1 ";
        }
        StringBuffer sql = new StringBuffer("SELECT A.ITEM_ID, A.INVENTORY_NO, A.DEALER_CODE, A.STORAGE_CODE,B.STORAGE_NAME, A.PART_BATCH_NO,  A.PART_NO, A.PART_NAME, A.UNIT_CODE, A.CURRENT_STOCK, ");
        sql.append("A.BORROW_QUANTITY,  A.STORAGE_POSITION_CODE, A.LEND_QUANTITY, A.REAL_STOCK, A.CHECK_QUANTITY, ABS(PROFIT_LOSS_QUANTITY) PROFIT_LOSS_QUANTITY, A.COST_PRICE, A.PROFIT_LOSS_AMOUNT    ");
        sql.append( " FROM TT_PART_INVENTORY_ITEM  A  ");
        sql.append(" LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE   ");
        sql.append( "  WHERE  A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()+ "' " );
        sql.append(" AND  A.D_KEY = " + CommonConstants.D_KEY + " " + number + pa);
        return DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 盘点亏损导出查询
    * @author yujiangheng
    * @date 2017年5月18日
    * @param inventoryNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryToExport2(java.lang.String)
     */
    @Override
    public List<Map> queryToExport2(String inventoryNo) throws ServiceBizException {
        String number = "";
        String pa = " and A.PROFIT_LOSS_QUANTITY  < 0";

        if (inventoryNo != null && inventoryNo.trim().length() != 0) {
            number = " and A.INVENTORY_NO = '" + inventoryNo + "' ";
        } else{
            number = " and 1 = 1 ";
        }
        StringBuffer sql = new StringBuffer("SELECT A.ITEM_ID, A.INVENTORY_NO, A.DEALER_CODE, A.STORAGE_CODE,B.STORAGE_NAME, A.PART_BATCH_NO,  A.PART_NO, A.PART_NAME, A.UNIT_CODE, A.CURRENT_STOCK, ");
        sql.append("A.BORROW_QUANTITY,  A.STORAGE_POSITION_CODE, A.LEND_QUANTITY, A.REAL_STOCK, A.CHECK_QUANTITY, ABS(PROFIT_LOSS_QUANTITY) PROFIT_LOSS_QUANTITY, A.COST_PRICE, A.PROFIT_LOSS_AMOUNT    ");
        sql.append( " FROM TT_PART_INVENTORY_ITEM  A  ");
        sql.append(" LEFT JOIN TM_STORAGE B ON A.DEALER_CODE=B.DEALER_CODE AND A.STORAGE_CODE=B.STORAGE_CODE   ");
        sql.append( "  WHERE  A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()+ "' " );
        sql.append(" AND  A.D_KEY = " + CommonConstants.D_KEY + " " + number + pa);
        return DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 
    * @author yujiangheng
     * @throws Exception 
    * @date 2017年5月15日
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#achieve()
     */
    @Override
    public void achieve(String inventoryNo) throws Exception {//配件盘点 增删改
       //根据单号查询一遍，获取单号信息
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        /**
         * 根据IS_FINISHED判断是否做的盘点差异分析12781001是
         * 如果盘点分离里没有报溢和报损的配件，则把盘点单报以报损标志改为：12781001已经做过抱益抱损
         * 在报以报损查询盘点单中就不差出该单据
         * 如果盘点分析中只有盘亏配件，没有盘盈配件，则把报以标志改为已经做过报以12781001
         * 只有盘盈则相反
         * 通过盈亏数量判断该盘点单有没有报以或者报损的配件
         */
            String allFlag = "0";
            String lossFlag = "0";//0 12781001
            String profitFlag = "0";//1表示存在报溢 2表示不存在
            //根据单号查询明细表，对报损和报溢进行判断，之后改变主表中的报损和报溢标志、改变主表完成状态
            StringBuffer sb =new StringBuffer("SELECT * FROM TT_PART_INVENTORY_item WHERE   INVENTORY_NO='"+inventoryNo+"'");
            sb.append(" AND DEALER_CODE='"+dealerCode+"' AND D_KEY="+CommonConstants.D_KEY );
            List<Map> inlist=DAOUtil.findAll(sb.toString(), null);
            System.out.println("________________________________"+inlist);
            if (inlist != null && inlist.size() > 0) {
                allFlag="0";//报溢和报损标志
                int ac=0;//报溢标志
                int bc=0;//报损标志
                TtPartInventoryItemDTO itemPO = new TtPartInventoryItemDTO();
                for (int ak = 0; ak < inlist.size(); ak++) {
                    setTtPartInventoryItemDTO(itemPO,inlist.get(ak));
                    if (itemPO.getProfitLossQuantity() != null) {
                        if (itemPO.getProfitLossQuantity() > 0 ) { //存在报溢
                            profitFlag = "1";//12781002
                            ac=1;
                            break;
                        }
                    }
                }
                for (int aj = 0; aj < inlist.size(); aj++) {
                    setTtPartInventoryItemDTO(itemPO,inlist.get(aj));
                    if (itemPO.getProfitLossQuantity() != null) {
                        if (itemPO.getProfitLossQuantity() < 0)  { //存在报损
                            lossFlag = "1";
                            bc=1;
                            break;
                        }
                    }
                }
                if(ac==1 && bc==1) {//存在报溢和报损
                    allFlag="1";
                }
         
        if(StringUtils.isNullOrEmpty(inventoryNo)){
            throw new ServiceBizException("丢失主键！");
        }
        //更新主表
            if (!StringUtils.isNullOrEmpty(inventoryNo)) {
                TtPartInventoryPO inventory = TtPartInventoryPO.findByCompositeKeys(dealerCode,inventoryNo);
               //改变主表中的报损、报溢标志和完成状态
                inventory.setInteger("IS_FINISHED",Utility.getInt(CommonConstants.DICT_IS_YES));//盘点完成
                if(ac==1){
                    inventory.setInteger("PROFIT_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));
                }else{
                    inventory.setInteger("PROFIT_TAG",Utility.getInt(CommonConstants.DICT_IS_NO));
                }
                if(bc==1){
                    inventory.setInteger("LOSS_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));
                }else{
                    inventory.setInteger("LOSS_TAG",Utility.getInt(CommonConstants.DICT_IS_NO));
                }
                if(allFlag=="1"){
                    inventory.setInteger("PROFIT_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));
                    inventory.setInteger("LOSS_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));
                }
                inventory.saveIt();
            }
        }    
        
        
    }
    /**
     * 设置对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月17日
    * @param itemPO
    * @param map
     */
    private void setTtPartInventoryItemDTO(TtPartInventoryItemDTO itemPO, Map map) {
        System.out.println(map.get("ITEM_ID").toString());
        itemPO.setItemId(Long.parseLong(map.get("ITEM_ID").toString()));
        itemPO.setPartNo(map.get("PART_NO").toString());
        itemPO.setPartName(map.get("PART_NAME").toString());
        itemPO.setDealerCode(map.get("DEALER_CODE").toString());
        itemPO.setPartBatchNo(map.get("PART_BATCH_NO").toString());
        itemPO.setInventoryNo(map.get("INVENTORY_NO").toString());
        itemPO.setStorageCode(map.get("STORAGE_CODE").toString());
        itemPO.setUnitCode(map.get("UNIT_CODE").toString());
        itemPO.setStoragePositionCode(map.get("STORAGE_POSITION_CODE").toString());
        itemPO.setProfitLossQuantity(Float.parseFloat(map.get("PROFIT_LOSS_QUANTITY").toString()));
        itemPO.setBorrowQuantity(Float.parseFloat(map.get("BORROW_QUANTITY").toString()));
        itemPO.setCheckQuantity(Float.parseFloat(map.get("CHECK_QUANTITY").toString()));
        itemPO.setLendQuantity(Float.parseFloat(map.get("LEND_QUANTITY").toString()));
        itemPO.setCurrentStock(Float.parseFloat(map.get("CURRENT_STOCK").toString()));
        itemPO.setRealStock(Float.parseFloat(map.get("REAL_STOCK").toString()));
        itemPO.setProfitLossAmount(Double.parseDouble(map.get("PROFIT_LOSS_AMOUNT").toString()));
        itemPO.setCostPrice(Double.parseDouble(map.get("COST_PRICE").toString()));
    }
   

}
