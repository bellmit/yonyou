
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : StuffPriceAdjustServiceImp.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月9日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月9日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.LimitSeriesDatainfoPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.partOrder.ListPtDmsOrderPriceDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPartPeriodReportDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtRepairOrderDTO;

/**
 * 发料价格调整
 * 
 * @author zhanshiwei
 * @date 2017年5月9日
 */
@Service
public class StuffPriceAdjustServiceImp implements StuffPriceAdjustService {

    @Autowired
    private CommonNoService     commonNoService;
    @Autowired
    private  BusinessService BusinessUtility;


    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(StuffPriceAdjustService.class);

    /**
     * @author zhanshiwei
     * @date 2017年5月9日
     * @param queryPram
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.StuffPriceAdjustService#queryRepairOrder(java.util.Map)
     */

    @Override
    public PageInfoDto queryRepairOrder(Map<String, String> queryPram) throws ServiceBizException {
        Integer roStatus = Integer.parseInt(DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
        String roNO = queryPram.get("roNo");
        String license = queryPram.get("license");
        String serviceAdvisor = queryPram.get("serviceAdvisor");
        String ownerName = queryPram.get("ownerName");

        StringBuffer sql = new StringBuffer();
        sql.append("select '12781001' AS COLOR_FLAG, A.VIN,B.CONSULTANT,A.DEALER_CODE, A.RO_NO, A.SALES_PART_NO, A.BOOKING_ORDER_NO, A.ESTIMATE_NO,"
                   + "   A.RO_TYPE, A.REPAIR_TYPE_CODE, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC, "
                   + "    A.SEQUENCE_NO, A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE,"
                   + "    A.IS_CUSTOMER_IN_ASC, A.IS_SEASON_CHECK, A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE,"
                   + "   A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST, A.RECOMMEND_EMP_NAME,"
                   + "     A.RECOMMEND_CUSTOMER_NAME, A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS, A.RO_STATUS,"
                   + "    A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, A.OWNER_NAME,"
                   + "    A.OWNER_PROPERTY, A.LICENSE,  A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.IN_MILEAGE,"
                   + "    A.OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE, A.TOTAL_CHANGE_MILEAGE,"
                   + "    A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, A.DELIVERER_PHONE,"
                   + "    A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG, A.WAIT_INFO_TAG, A.WAIT_PART_TAG,"
                   + "    A.COMPLETE_TIME, A.FOR_BALANCE_TIME, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LABOUR_PRICE"
                   + "    , A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT, A.ADD_ITEM_AMOUNT,"
                   + "    A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, A.BALANCE_AMOUNT,"
                   + "    A.RECEIVE_AMOUNT, A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT, A.TRACE_TAG, A.REMARK,"
                   + "    A.TEST_DRIVER, A.PRINT_RO_TIME, A.RO_CHARGE_TYPE, A.PRINT_RP_TIME, A.IS_ACTIVITY,"
                   + "    A.CUSTOMER_DESC, A.LOCK_USER, A.IS_CLOSE_RO, A.RO_SPLIT_STATUS,A.SO_NO,A.VER,A.IS_MAINTAIN,"
                   + "    A.IS_LARGESS_MAINTAIN,C.SALES_DATE,A.IS_QS, A.SCHEME_STATUS,(SELECT te.EMPLOYEE_NAME from TM_EMPLOYEE te where  te.EMPLOYEE_NO=A.SERVICE_ADVISOR) as EMPLOYEE_NAME, "
                   + "    (SELECT USER_NAME from TM_USER where USER_ID=A.LOCK_USER) as LOCK_USER_NAME"
                   + " FROM TT_REPAIR_ORDER A LEFT JOIN TT_SALES_PART B ON A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO  "
                   + " LEFT JOIN TM_VEHICLE C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN WHERE 1=1 "
                   + " AND A.D_KEY=" + CommonConstants.D_KEY + " ");

        sql.append(Utility.getLikeCond("A", "LICENSE", license, "AND"));
        if (roNO != null && !"".equals(roNO.trim())) {
            sql.append(" AND ( A.RO_NO LIKE '%" + roNO + "%'   or  A.LICENSE LIKE '%" + roNO + "%' ) ");
        }
        sql.append(Utility.getLikeCond("A", "OWNER_NAME", ownerName, "AND"));

        if (roStatus != null && roStatus != 0) {
            sql.append(" and A.RO_STATUS = " + roStatus + " ");
        }
        if (serviceAdvisor != null && serviceAdvisor.trim().length() > 0) {
            sql.append(" and A.SERVICE_ADVISOR = '" + serviceAdvisor + "' ");
        }
        sql.append("ORDER BY A.RO_CREATE_DATE DESC");
        List<Object> params = new ArrayList<Object>();
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), params);
        return id;
    }

    @Override
    public List<Map> queryPartSendPrice(Map<String, String> queryParam) throws ServiceBizException {
        CheckLockerValid(queryParam);
        String roNo = queryParam.get("roNo");
        StringBuffer sql = new StringBuffer("");
        sql.append("select A.IS_DISCOUNT,B.COST_PRICE,A.PART_SALES_PRICE AS OLD_PRICE, A.ITEM_ID, A.DEALER_CODE, A.RO_NO, A.PART_NO, A.PART_NAME, A.STORAGE_CODE, "
                   + " A.CHARGE_PARTITION_CODE, A.MANAGE_SORT_CODE, A.OUT_STOCK_NO, A.PRICE_TYPE, A.PART_BATCH_NO,"
                   + " A.IS_MAIN_PART, A.PART_QUANTITY, A.PRICE_RATE, A.OEM_LIMIT_PRICE, A.PART_COST_PRICE,"
                   + " A.PART_SALES_PRICE, A.PART_COST_AMOUNT, A.PART_SALES_AMOUNT, A.SENDER, A.RECEIVER, "
                   + " A.SEND_TIME, A.IS_FINISHED, A.BATCH_NO, A.ACTIVITY_CODE, A.PRE_CHECK, DISCOUNT, "
                   + " A.NEEDLESS_REPAIR, A.CONSIGN_EXTERIOR, A.PRINT_RP_TIME, A.STORAGE_POSITION_CODE, "
                   + " A.UNIT_CODE, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,C.DOWN_TAG,C.LIMIT_PRICE, "

                   + " A.VER  from TT_RO_REPAIR_PART A LEFT JOIN TM_PART_STOCK B ON A.DEALER_CODE=B.DEALER_CODE AND A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE "
                   + " LEFT JOIN (" + CommonConstants.VM_PART_INFO
                   + ") C ON( C.DEALER_CODE = A.DEALER_CODE AND C.PART_NO = A.PART_NO ) " + " WHERE 1=1"
                   + " and A.D_KEY = " + CommonConstants.D_KEY + " " + " and A.RO_NO = '" + roNo
                   + "' and A.IS_FINISHED = " + DictCodeConstants.DICT_IS_YES + " ");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public int CheckLockerValid(Map<String, String> queryParam) throws ServiceBizException {
        /*
         * // 取输入参数：表名称，工单号 String tableName = queryParam.get("TABLE_NAME"); String noName = queryParam.get("NO_NAME");
         */
        String noValue = queryParam.get("roNo");
        /*
         * String lockName = queryParam.get("LOCK_NAME"); String lockFlag = queryParam.get("LOCK_FLAG");// 0不加锁，1加锁 if
         * ("1".equals(lockFlag)) { return 2; } if (StringUtil.isNullOrEmpty(tableName) ||
         * StringUtil.isNullOrEmpty(noName) || StringUtil.isNullOrEmpty(noValue) || StringUtil.isNullOrEmpty(lockName))
         * { return 0; }
         */
        // 核对是否加锁
        String lockerNo = Utility.selLockerName("LOCK_USER", "TT_REPAIR_ORDER", "RO_NO", noValue);
        if (!StringUtils.isNullOrEmpty(lockerNo)
            && !lockerNo.equals(FrameworkUtil.getLoginInfo().getUserId().toString())) {
            throw new ServiceBizException(" 业务加锁错误!");
        } else {
            Utility.updateByLocker("TT_REPAIR_ORDER", FrameworkUtil.getLoginInfo().getUserId().toString(), "RO_NO",
                                   noValue, "LOCK_USER");
            return 1;
        }
    }

    /**
     * 业务描述:配件发料价格调整引起的工单修改
     * 
     * @author
     * @date 2017年5月10日
     * @param ttRepairDto
     * @throws Exception 
     * @see com.yonyou.dms.part.service.partOrder.StuffPriceAdjustService#changePartSendPrice(com.yonyou.dms.part.domains.DTO.partOrder.TtRepairOrderDTO)
     */

    @Override
    public void changePartSendPrice(TtRepairOrderDTO ttRepairDto) throws Exception {
        queryMonthPeriodIsFinished();
        // 新增开关是否启用配件限价功能
        String isCheckPLP = commonNoService.getDefalutPara("3432");

        String roNo = ttRepairDto.getRoNo();
        if (!CommonUtils.isNullOrEmpty(ttRepairDto.getPartSendPrice())) {
            // 获取符合政策的浮动比例
            // 如果有关联的工单的销售配件订单，那么车系和维修类型取工单上的
            // 如果没有关联工单的配件销售单，哪门只通过车辆Vin获取车系和品牌，来获取，不判断维修类型
            LimitSeriesDatainfoPO tmLSDPo1 = null;
            List<LimitSeriesDatainfoPO> tmLimitSerieslist = null;
            if (Utility.testString(ttRepairDto.getRoNo())) {
                RepairOrderPO ttROPo = RepairOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                         ttRepairDto.getRoNo());
                tmLimitSerieslist = LimitSeriesDatainfoPO.findBySQL("select * from TM_LIMIT_SERIES_DATAINFO where REPAIR_TYPE_CODE=? and BRAND_CODE=? and SERIES_CODE=? and Is_Valid=? and OEM_TAG=? and D_KEY=? and DEALER_CODE=?",
                                                                    ttROPo.getString("REPAIR_TYPE_CODE"),
                                                                    ttROPo.getString("BRAND_CODE"),
                                                                    ttROPo.getString("SERIES_CODE"),
                                                                    DictCodeConstants.DICT_IS_YES,
                                                                    DictCodeConstants.DICT_IS_YES,
                                                                    CommonConstants.D_KEY,
                                                                    FrameworkUtil.getLoginInfo().getDealerCode());
                if (tmLimitSerieslist != null && tmLimitSerieslist.size() > 0) {
                    tmLSDPo1 = tmLimitSerieslist.get(0);
                }
                LinkedList<TtRoRepairPartPO> linkedList = new LinkedList<TtRoRepairPartPO>();
                TtRoRepairPartPO[] records = new TtRoRepairPartPO[ttRepairDto.getPartSendPrice().size()];
                TtRoRepairPartPO roRepair = new TtRoRepairPartPO();
                TtRoRepairPartPO roRepairContion = new TtRoRepairPartPO();

                RepairOrderPO order = new RepairOrderPO();
                RepairOrderPO orderConditon = new RepairOrderPO();
                double oldPriceTrue = 0;
                for (int i = 0; i < ttRepairDto.getPartSendPrice().size(); i++) {
                    ListPtDmsOrderPriceDTO priceMap = ttRepairDto.getPartSendPrice().get(i);
                    String partSalesPrice =StringUtils.isNullOrEmpty(priceMap.getPartSalesPrice())?"0":priceMap.getPartSalesPrice().toString();
                    String oldPrice = StringUtils.isNullOrEmpty(priceMap.getOldPrice())?"0":priceMap.getOldPrice().toString();
                    if ((Utility.getDouble(partSalesPrice) == Utility.getDouble(oldPrice))) {
                        continue;
                    }
                    logger.debug("参数3432控制判断,配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售限价");
                    if (Utility.testString(isCheckPLP) && DictCodeConstants.DICT_IS_YES.equals(isCheckPLP)) {
                        if (tmLSDPo1 != null) {
                            // 销售浮动比例
                            Double limitSeriesDatainfo = Double.parseDouble(tmLSDPo1.getString("LIMIT_PRICE_RATE").toString());
                            Double limitPrice = 0.00;
                            // 配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售浮动价 add by ch 20160118
                            List<TmPartInfoPO> listTpi = TmPartInfoPO.findBySQL("select * from TM_PART_INFO where DEALER_CODE=? and PART_NO=?",
                                                                                FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                                priceMap.getPartNo());
                            // 获取销售价格
                            if (listTpi != null && listTpi.size() > 0) {
                                TmPartInfoPO tmparinfPO = listTpi.get(0);
                                limitPrice = tmparinfPO.getDouble("LIMIT_PRICE");
                                if (limitPrice != null && Utility.getDouble(limitPrice + "") > 0.0) {
                                    logger.debug("销售限价:" + limitPrice);
                                    logger.debug("浮动比例:" + limitSeriesDatainfo);
                                    // 销售浮动比例+1
                                    limitSeriesDatainfo = add(1.00D, limitSeriesDatainfo);
                                    // （销售浮动比例+1）*销售价格
                                    limitPrice = mul(limitPrice, limitSeriesDatainfo);

                                    logger.debug("销售浮动价:" + limitPrice);
                                    if (limitPrice > 0 && Utility.getDouble(partSalesPrice) > 0) {
                                        // 如果发料价格大于销售浮动价那么报错
                                        if (Utility.getDouble(partSalesPrice) > limitPrice) {
                                            throw new ServiceBizException(priceMap.getPartNo() + "配件发料价格大于最高限价:"
                                                                          + limitPrice + ",请调整发料价格！");
                                        }
                                    }
                                }

                            }
                        }
                    }

                    // 配件发料价格调整
                    if (commonNoService.getDefalutPara(String.valueOf(CommonConstants.DEFAULT_PARA_IS_LIMIT_PART_PRICE)).equals(CommonConstants.DICT_IS_YES)) {
                        List<Map> listpof = checkRepairPartLimitPrice(roNo);
                        String errPrice = "";
                        if (listpof != null && listpof.size() > 0) {
                            logger.debug("list size :" + listpof.size());
                            for (int k = 0; k < listpof.size(); k++) {
                                Map dyna = null;
                                dyna = listpof.get(k);
                                if (errPrice.equals("")) {
                                    errPrice = String.valueOf(dyna.get("PART_NO"));
                                } else {
                                    errPrice = errPrice + ", " + dyna.get("PART_NO");
                                }
                                logger.debug("errprice :" + errPrice);
                            }
                            if (!StringUtils.isNullOrEmpty(errPrice)) {
                                throw new ServiceBizException(" 配件价格不正确,请重新操作!");
                            }
                        }
                    }

                    /*
                     * 取单据中原来老的价格
                     */

                    List<TtRoRepairPartPO> partRoList = TtRoRepairPartPO.findBySQL("select * from TT_RO_REPAIR_PART where DEALER_CODE=? and ITEM_ID=?",
                                                                                   FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                                   priceMap.getItemId());
                    if (partRoList != null && partRoList.size() > 0) {
                        TtRoRepairPartPO partPOcc = partRoList.get(0);
                        oldPriceTrue = StringUtils.isNullOrEmpty(partPOcc.getDouble("PART_SALES_PRICE"))?(0.0):partPOcc.getDouble("PART_SALES_PRICE");
                    }
                    if (StringUtils.isNullOrEmpty(priceMap.getItemId()) || StringUtils.isNullOrEmpty(roNo)) {
                        throw new ServiceBizException("丢失主键值!");
                    }
                    /**
                     * 1、检验是否低于成本价 2、检验是否有低于成本价出库的权限
                     */

                    String flag = this.checParsCostPrice(priceMap.getPartNo(), priceMap.getStorageCode(),
                                                         Utility.getDouble(partSalesPrice));
                    if (flag != null && !"".equals(flag.trim()) && flag.equals(DictCodeConstants.DICT_IS_YES)) {
                        // 12781001 是低于成本单价
                        List listOption = this.checkLowCostPriceOut(FrameworkUtil.getLoginInfo().getUserId());
                        if (listOption != null && listOption.size() > 0) {
                            // 有低于成本价出库的权限
                        } else {
                            throw new ServiceBizException("保存错误!");
                        }
                    }
                    roRepairContion = TtRoRepairPartPO.findFirst("DEALER_CODE=? and ITEM_ID=?",
                                                                 FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                 priceMap.getItemId());
                    roRepairContion.setString("RO_NO", roNo);
                    roRepairContion.setInteger("D_KEY", CommonConstants.D_KEY);
                    roRepairContion.setDouble("PART_SALES_PRICE", partSalesPrice);
                    roRepairContion.setDouble("PART_SALES_AMOUNT", priceMap.getPartSalesAmount());
                    roRepairContion.saveIt();
                    linkedList.add(roRepair);

                    // 同时进行 维修工单表 修改维修总价
                    reCalcRepairAmount(roNo);// 修改维修材料费
                    BusinessUtility.updateRoManage(FrameworkUtil.getLoginInfo().getDealerCode(), roNo, FrameworkUtil.getLoginInfo().getUserId());
                    BusinessUtility.updateRepairOrder(FrameworkUtil.getLoginInfo().getDealerCode(), roNo);
                    // 配件流水帐新增一条调价信息记录
                    PartFlowPO flow = new PartFlowPO();
                   // flow.setFlowId(POFactory.getLongPriKey(conn, flow));
                    flow.setString("STORAGE_CODE", priceMap.getStorageCode());
                    flow.setString("PART_NO", priceMap.getPartNo());
                    flow.setString("PART_NAME", priceMap.getPartName());
                    flow.setString("SHEET_NO", roNo);
                    flow.setString("COST_PRICE", priceMap.getCostPrice());
                    flow.setDouble("COST_AMOUNT", "0");
                    flow.setString("OPERATOR", FrameworkUtil.getLoginInfo().getUserCode());
                    flow.setTimestamp("OPERATE_DATE", new Date(System.currentTimeMillis()));
                    if (partSalesPrice != null) {
                        if (Utility.getDouble(partSalesPrice) > oldPriceTrue) {
                            flow.setString("IN_OUT_TYPE", DictCodeConstants.DICT_IN_OUT_TYPE_DISPENSE_PRICE_IN_STOCK);
                            flow.setString("In_Out_Tag", DictCodeConstants.DICT_IS_NO);// 入库
                        }
                        if (Utility.getDouble(partSalesPrice) < oldPriceTrue) {
                            flow.setString("IN_OUT_TYPE", DictCodeConstants.DICT_IN_OUT_TYPE_DISPENSE_PRICE_OUT_STOCK);
                            flow.setString("In_Out_Tag", DictCodeConstants.DICT_IS_YES);// 出库

                        }

                        double amount = Utility.add("1", commonNoService.getDefalutPara(CommonConstants.DEFAULT_PARA_PART_RATE+""));
                                
                        String rate = Double.toString(amount);
                        double partSales = 0D;
                        partSales = Math.abs(Utility.getDouble(partSalesPrice) - oldPriceTrue);
       
                        flow.setString("IN_OUT_NET_AMOUNT", Utility.div(String.valueOf(partSales), rate)* Utility.getFloat(priceMap.getPartQuantity()+"")); // 不含税金额
                        flow.setString("IN_OUT_TAXED_AMOUNT", Utility.mul(String .valueOf(partSales), priceMap.getPartQuantity()+""));
                    }
                        
                        flow.setString("PART_BATCH_NO", priceMap.getPartBatchNo());
                        flow.setString("LICENSE", ttRepairDto.getLicense());
                        flow.setString("CUSTOMER_CODE", ttRepairDto.getCustomerCode());
                        flow.setString("CUSTOMER_NAME", ttRepairDto.getCustomerName());

                        // 查询工单的相关信息
                        TmPartStockPO psPO = new TmPartStockPO();
          
                        List<TmPartStockPO> roList=TmPartStockPO.findBySQL("select * from tm_part_stock where DEALER_CODE=? and PART_NO=? and D_KEY=?", FrameworkUtil.getLoginInfo().getDealerCode(),priceMap.getPartNo(),CommonConstants.D_KEY);
                        
                        if (roList != null && roList.size() > 0) {
                            psPO = roList.get(0);
                            flow.setString("STOCK_QUANTITY",psPO.getString("STOCK_QUANTITY"));
                        }
                        flow.saveIt();
                        /**
                         * 配件会计月报,有维修销售金额,发料价格产生的维修发料销售金额差异,记到原其他出库销售金额字段--新:发料价格调整差异金额
                         */

                        // 会计月报表
                        double amountch = Utility.getDouble(partSalesPrice)  - oldPriceTrue;
                   
                        AccountPeriodPO  cycle=new AccountPeriodPO();
                        cycle = getAccountCyclePO();
                        TtPartPeriodReportPO period = new TtPartPeriodReportPO();
                        period.setString("PART_BATCH_NO", priceMap.getPartBatchNo());
                        period.setString("PART_NO", priceMap.getPartNo());
                        period.setString("PART_NAME", priceMap.getPartName());
                        period.setString("STORAGE_CODE", priceMap.getStorageCode());
                        
                        period.setString("OTHER_OUT_SALE_AMOUNT", Utility.mul(String
                                                                              .valueOf(amountch), priceMap.getPartQuantity()+"")); // 金额的差价:发料价格调整
                        period.setString("Out_Amount", Utility.mul(String.valueOf(amountch),
                                                                   priceMap.getPartQuantity()+""));
                        period.setFloat("OUT_QUANTITY", Utility.getFloat("0"));// 收费区分调整，数量为0
                        
                        period.setDouble("STOCK_OUT_COST_AMOUNT", Utility.getDouble("0"));
                        period.setDouble("REPAIR_OUT_COST_AMOUNT",Utility.getDouble("0"));
                        period.setFloat("REPAIR_OUT_COUNT", Utility.getFloat("0"));
                        period.setString("CREATED_BY", FrameworkUtil.getLoginInfo().getUserId());
                        period.setString("UPDATED_BY", FrameworkUtil.getLoginInfo().getUserId());
                        
                        TtPartPeriodReportDTO db2 = new TtPartPeriodReportDTO();
                        db2.setPartBatchNo(priceMap.getPartBatchNo());
                        db2.setPartNo( priceMap.getPartNo());
                        db2.setPartName(priceMap.getPartName());
                        db2.setStorageCode(priceMap.getStorageCode());
                        db2.setOtherOutSaleAmount(Utility.mul(String
                                .valueOf(amountch),  priceMap.getPartQuantity()+"")); // 金额的差价:发料价格调整
                        db2.setOutAmount(Utility.mul(String.valueOf(amountch),
                                                     priceMap.getPartQuantity()+""));
                        db2.setOutQuantity(Utility.getDouble("0"));// 收费区分调整，数量为0
                        db2.setStockOutCostAmount(Utility.getDouble("0"));
                        db2.setRepairOutCostAmount(Utility.getDouble("0"));
                        db2.setRepairOutCount(Utility.getDouble("0"));
                        db2.setCreatedBy(FrameworkUtil.getLoginInfo().getUserId());
                        db2.setUpdatedBy(FrameworkUtil.getLoginInfo().getUserId());
                        
                        
                        TmPartStockItemPO newItem = new TmPartStockItemPO();
                        
                        List<TmPartStockItemPO> itemlist=TmPartStockItemPO.findBySQL("select * from TM_PART_STOCK_ITEM where DEALER_CODE=? and PART_NO=? and D_KEY=? and STORAGE_CODE=? and PART_BATCH_NO=?", 
                                                                                     FrameworkUtil.getLoginInfo().getDealerCode(),priceMap.getPartNo(),CommonConstants.D_KEY,priceMap.getStorageCode(),priceMap.getPartBatchNo());
                        if(itemlist!=null && itemlist.size()>0){
                            newItem = itemlist.get(0);
                            double costPriceNew = newItem.getDouble("COST_PRICE");// 入帐后的成本单价
                           
                            period.setDouble("CLOSE_PRICE",costPriceNew);// 入帐后成本单价
                            
                           createOrUpdate(period, db2,cycle, FrameworkUtil.getLoginInfo().getDealerCode());
                        }else{
                            throw new ServiceBizException("该配件已停用！","该配件已停用！");
                        }
                    }
                
                if (linkedList != null && linkedList.size() > 0) {
                  //  actionContext.setArrayValue("TT_RO_REPAIR_PART", records);
                }
            }
        }

    }

    /**
     * @author zhanshiwei
     * @date 2017年5月10日
     */

    public void queryMonthPeriodIsFinished() {
        if (Utility.isFinishedThisMonth().size() > 0) {
            throw new ServiceBizException(" 当前配件月报没有正确执行");
        }
        if (CommonUtils.isNullOrEmpty(Utility.getIsFinished())
            || StringUtils.isEquals(Utility.getIsFinished().get(0).get("IS_EXECUTED"), DictCodeConstants.DICT_IS_YES)) {
            throw new ServiceBizException(" 当前配件会计月报没有正确执行");
        }
    }

    /**
     * @param v1
     * @param v2
     * @return 加法运算
     */
    public double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();

    }

    /**
     * @param v1
     * @param v2
     * @return 乘法运算
     */
    public double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 功能描述：判断工单的配件价格是否正确
     * 
     * @author
     * @date 2017年5月10日
     * @param roNo
     * @return
     */

    public List<Map> checkRepairPartLimitPrice(String roNo) {
        StringBuffer sql = new StringBuffer("");

        sql.append(" select A.PART_NO,A.PART_NAME,A.PART_SALES_PRICE,B.LIMIT_PRICE,A.DEALER_CODE from TT_RO_REPAIR_PART A  "
                   + "left join tm_part_info B on (A.PART_NO = B.PART_NO " + "AND A.DEALER_CODE = B.DEALER_CODE) "
                   + "WHERE A.IS_FINISHED = " + DictCodeConstants.DICT_IS_NO + " AND  A.PART_SALES_PRICE > "
                   + Utility.getChangeNull("B", "LIMIT_PRICE", 0) + "  AND A.RO_NO ='" + roNo + "'"
                   + " AND B.DOWN_TAG = " + DictCodeConstants.DICT_IS_YES + " AND A.CHARGE_PARTITION_CODE <> '"
                   + CommonConstants.MANAGE_SORT_CLAIM_TAG + "' " + " AND "
                   + Utility.getChangeNull("B", "LIMIT_PRICE", 0) + " <> 0.0" + " AND A.PART_QUANTITY > 0.0 ");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);

    }

    // 检查是否小于成本价
    public String checParsCostPrice(String partNo, String storageCode, double newCostPrice) {
        String flag = DictCodeConstants.DICT_IS_NO;
        double costPrice = 0;

        StringBuffer sql = new StringBuffer("");
        sql.append("select cost_price,DEALER_CODE from tm_part_stock where 1=1 and part_no='" + partNo + "' and storage_code='"
                   + storageCode + "' ");
        List<Object> params = new ArrayList<>();
        final Map<String, Object> Priceresult = new HashMap<>();
        DAOUtil.findAll(sql.toString(), params, new DefinedRowProcessor() {

            @Override
            protected void process(Map<String, Object> row) {
                Priceresult.put("costPrice", StringUtils.isNullOrEmpty(row.get("cost_price"))?(0.0):row.get("cost_price"));
            }
        });
        costPrice = Utility.getDouble(StringUtils.isNullOrEmpty(Priceresult.get("cost_price"))?("0.0"):Priceresult.get("cost_price").toString());
        if (costPrice > newCostPrice) {
            flag = DictCodeConstants.DICT_IS_YES;// 发料价格小于成本单价
        }
        return flag;

    }

    public List<Map> checkLowCostPriceOut(long userId) {
        StringBuffer sql = new StringBuffer("");
        sql.append(" select * from tm_user_ctrl where CTRL_CODE='20180000' and user_id=" + userId + "");
        return DAOUtil.findAll(sql.toString(), new ArrayList<>());
    }

    public void reCalcRepairAmount(String roNo) {
        String sql = "";
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
        sql = " UPDATE TT_REPAIR_ORDER SET REPAIR_PART_AMOUNT =("
              + " SELECT SUM(Coalesce(DISCOUNT,1)*Coalesce(PART_QUANTITY,0)*Coalesce(PART_SALES_PRICE,0))"
              + " from TT_RO_REPAIR_PART " + " where DEALER_CODE = '" + entityCode + "'" + " AND D_KEY = "
              + CommonConstants.D_KEY + " AND RO_NO = '" + roNo + "' and NEEDLESS_REPAIR= "
              + DictCodeConstants.DICT_IS_NO + " AND (CHARGE_PARTITION_CODE='' or CHARGE_PARTITION_CODE is null))"
              + " WHERE RO_NO = '" + roNo + "'" + " AND D_KEY = " + CommonConstants.D_KEY + " AND DEALER_CODE = '"
              + entityCode + "'";
        Base.exec(sql);
    }
    

    /**
     * 
     * ：返回当前会期周期的PO
     * 
     * @param conn
     * @param entityCode
     * @return
     * @throws Exception
     */
    public AccountPeriodPO getAccountCyclePO() {
        final AccountPeriodPO dbTmp = new AccountPeriodPO();
            StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED  FROM TM_ACCOUNTING_CYCLE  WHERE 1=1 ");
            sql.append("AND CURRENT_TIMESTAMP BETWEEN BEGIN_DATE AND END_DATE");
            List<String> params = new ArrayList<>();
            DAOUtil.findAll(sql.toString(), params, new DefinedRowProcessor() {
                @Override
                protected void process(Map<String, Object> row) {
                    dbTmp.setString("B_YEAR", row.get("B_YEAR"));
                    dbTmp.setString("PERIODS", row.get("PERIODS"));
                    dbTmp.setString("BEGIN_DATE", row.get("BEGIN_DATE"));
                    dbTmp.setString("END_DATE", row.get("END_DATE"));
                    dbTmp.setString("IS_EXECUTED", row.get("IS_EXECUTED"));
                }
            });
            return dbTmp;
    }    
    public int createOrUpdate( TtPartPeriodReportPO db,TtPartPeriodReportDTO db2, AccountPeriodPO account, String entityCode) throws Exception{
        List<Object>  psInsert =new ArrayList<>();
        List<Object> psSelect=new ArrayList<>(); 

        StringBuffer sbSelect = new StringBuffer();
        StringBuffer sbSqlUpdate = new StringBuffer();
        StringBuffer sbSqlInsert = new StringBuffer();
        sbSelect.append(" SELECT DEALER_CODE" + " FROM TT_PART_PERIOD_REPORT"
                + " WHERE DEALER_CODE = '" + entityCode + "'" + " AND STORAGE_CODE = ?"
                + " AND PART_NO = ?" + " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?"
                + " AND PART_BATCH_NO = ?" + " AND D_KEY = " + CommonConstants.D_KEY);
        sbSqlUpdate
                .append(" UPDATE TT_PART_PERIOD_REPORT"
                        + " SET IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,"
                        + " STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,"
                        + " BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,"
                        + " BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,"
                        + " ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,"
                        + " ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,"
                        + " OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,"
                        + " OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,"
                        + " PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,"
                        + " PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,"
                        + " OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,"
                        + " STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,"
                        + " OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,"
                        + " REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,"
                        + " REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,"
                        + " REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,"
                        + " SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,"
                        + " SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,"
                        + " SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,"
                        + " INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,"
                        + " INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,"
                        + " INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,"
                        + " ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,"
                        + " ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,"
                        + " ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,"
                        + " OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,"
                        + " OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,"
                        + " OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,"
                        + " LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,"
                        + " LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,"
                        + " TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,"
                        + " TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,"
                        + " TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,"
                        + " TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,"
                        + " UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,"
                        + " UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,"
                        + " UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,"
                        + " CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?," 
                        + " CLOSE_PRICE = ?," 
                        + " CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?,"                    
                        
                        + " UPDATED_BY = ?," + " UPDATED_AT = ?" + " WHERE DEALER_CODE = ?"
                        + " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?"
                        + " AND STORAGE_CODE = ?" + " AND PART_BATCH_NO = ?" + " AND PART_NO = ?"
                        + " AND D_KEY = " + CommonConstants.D_KEY

                );

        sbSqlInsert
                .append(" INSERT INTO TT_PART_PERIOD_REPORT"
                        + " (REPORT_YEAR, REPORT_MONTH,STORAGE_CODE, PART_BATCH_NO,PART_NO,DEALER_CODE,PART_NAME, "
                        + " IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT, BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,"
                        + " ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,"
                        + " OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT,"
                        + " REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT,"
                        + " SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,"
                        + " INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,"
                        + " ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT,"
                        + " OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT,"
                        + " LOSS_OUT_COUNT, LOSS_OUT_AMOUNT, CREATED_BY, CREATED_AT,TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT,"
                        + " OPEN_QUANTITY, OPEN_PRICE, OPEN_AMOUNT,UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT"
                        + " ) VALUES(" + "  ?, ?, ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?,"
                        + "  ?, ?, ?, ?, ?," + "  ?, ?, ?, " + "  ?, ?, ?, " + "  ?, ?, ?, "
                        + "  ?, ?, ?, " + "  ?, ?, ?, " + "  ?, ?, ?, " + "  ?, ?, ?, ?,"
                        + "  ?, ?, ?, ?," 
                        + "  ?, ?, ?,"+" ?,?,?, " 
                        + " ?, ?, ?"  
                        + " )");

            logger.debug("::Select SQL :=" + sbSelect);
            logger.debug("::Insert SQL :=" + sbSqlInsert);
            logger.debug("::Update SQL :=" + sbSqlUpdate);
          
            if (db != null)
            {
                if (db.getFloat("OPEN_QUANTITY") == null)
                    db.setFloat("OPEN_QUANTITY", 0f);
                if (db.getDouble("OPEN_PRICE") == null)
                    db.setDouble("OPEN_PRICE",0d);
                if (db.getDouble("OPEN_AMOUNT") == null)
                    db.setDouble("OPEN_AMOUNT",0d);
                if (db.getFloat("IN_QUANTITY") == null)
                    db.setFloat("IN_QUANTITY",0f);
                if (db.getDouble("STOCK_IN_AMOUNT") == null)
                    db.setDouble("STOCK_IN_AMOUNT",0d);
                if (db.getFloat("BUY_IN_COUNT") == null)
                    db.setFloat("BUY_IN_COUNT",0f);
                if (db.getDouble("BUY_IN_AMOUNT") == null)
                    db.setDouble("BUY_IN_AMOUNT",0d);
                if (db.getDouble("ALLOCATE_IN_AMOUNT") == null)
                    db.setDouble("ALLOCATE_IN_AMOUNT",0d);
                if (db.getFloat("ALLOCATE_IN_COUNT") == null)
                    db.setFloat("ALLOCATE_IN_COUNT",0f);
                if (db.getFloat("OTHER_IN_COUNT") == null)
                    db.setFloat("OTHER_IN_COUNT",0f);
                if (db.getDouble("OTHER_IN_AMOUNT") == null)
                    db.setDouble("OTHER_IN_AMOUNT",0d);
                if (db.getFloat("PROFIT_IN_COUNT") == null)
                    db.setFloat("PROFIT_IN_COUNT",0f);
                if (db.getDouble("PROFIT_IN_AMOUNT") == null)
                    db.setDouble("PROFIT_IN_AMOUNT",0d);
                if (db.getFloat("OUT_QUANTITY") == null)
                    db.setFloat("OUT_QUANTITY",0f);
                if (db.getDouble("STOCK_OUT_COST_AMOUNT") == null)
                    db.setDouble("STOCK_OUT_COST_AMOUNT",0d);
                if (db.getDouble("OUT_AMOUNT") == null)
                    db.setDouble("OUT_AMOUNT",0d);
                if (db.getFloat("REPAIR_OUT_COUNT") == null)
                    db.setFloat("REPAIR_OUT_COUNT",0f);
                if (db.getDouble("REPAIR_OUT_COST_AMOUNT") == null)
                    db.setDouble("REPAIR_OUT_COST_AMOUNT",0d);
                if (db.getDouble("REPAIR_OUT_SALE_AMOUNT") == null)
                    db.setDouble("REPAIR_OUT_SALE_AMOUNT",0d);
                if (db.getFloat("SALE_OUT_COUNT") == null)
                    db.setFloat("SALE_OUT_COUNT",0f);
                if (db.getDouble("SALE_OUT_COST_AMOUNT") == null)
                    db.setDouble("SALE_OUT_COST_AMOUNT",0d);
                if (db.getDouble("SALE_OUT_SALE_AMOUNT") == null)
                    db.setDouble("SALE_OUT_SALE_AMOUNT",0d);
                if (db.getFloat("INNER_OUT_COUNT") == null)
                    db.setFloat("INNER_OUT_COUNT",0f);
                if (db.getDouble("INNER_OUT_COST_AMOUNT") == null)
                    db.setDouble("INNER_OUT_COST_AMOUNT",0d);
                if (db.getDouble("INNER_OUT_SALE_AMOUNT") == null)
                    db.setDouble("INNER_OUT_SALE_AMOUNT",0d);
                if (db.getFloat("ALLOCATE_OUT_COUNT") == null)
                    db.setFloat("ALLOCATE_OUT_COUNT",0f);
                if (db.getDouble("ALLOCATE_OUT_COST_AMOUNT") == null)
                    db.setDouble("ALLOCATE_OUT_COST_AMOUNT",0d);
                if (db.getDouble("ALLOCATE_OUT_SALE_AMOUNT") == null)
                    db.setDouble("ALLOCATE_OUT_SALE_AMOUNT",0d);
                if (db.getFloat("OTHER_OUT_COUNT") == null)
                    db.setFloat("OTHER_OUT_COUNT",0f);
                if (db.getDouble("OTHER_OUT_COST_AMOUNT") == null)
                    db.setDouble("OTHER_OUT_COST_AMOUNT",0d);
                if (db.getDouble("OTHER_OUT_SALE_AMOUNT") == null)
                    db.setDouble("OTHER_OUT_SALE_AMOUNT",0d);
                if (db.getFloat("LOSS_OUT_COUNT") == null)
                    db.setFloat("LOSS_OUT_COUNT",0f);
                if (db.getDouble("LOSS_OUT_AMOUNT") == null)
                    db.setDouble("LOSS_OUT_AMOUNT",0d);
                if (db.getFloat("CLOSE_QUANTITY") == null)
                    db.setFloat("CLOSE_QUANTITY",0f);
                if (db.getDouble("CLOSE_PRICE")== null)
                    db.setDouble("CLOSE_PRICE",0d);
                if (db.getDouble("CLOSE_AMOUNT") == null)
                    db.setDouble("CLOSE_AMOUNT",0d);
                if (db.getDouble("TRANSFER_IN_AMOUNT") == null)
                    db.setDouble("TRANSFER_IN_AMOUNT",0d);
                if (db.getDouble("TRANSFER_OUT_COST_AMOUNT") == null)
                    db.setDouble("TRANSFER_OUT_COST_AMOUNT",0d);
                if (db.getFloat("TRANSFER_IN_COUNT") == null)
                    db.setFloat("TRANSFER_IN_COUNT",0f);
                if (db.getFloat("TRANSFER_OUT_COUNT") == null)
                    db.setFloat("TRANSFER_OUT_COUNT",0f);
                if(db.getFloat("UPHOLSTER_OUT_COUNT")==null){
                    db.setFloat("UPHOLSTER_OUT_COUNT",0f);
                }
                if(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT")==null){
                    db.setDouble("UPHOLSTER_OUT_COST_AMOUNT",0d);
                }
                if(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT")==null){
                    db.setDouble("UPHOLSTER_OUT_SALE_AMOUNT",0d);
                }
             

                logger.debug(db.getString("STORAGE_CODE"));
                logger.debug(db.getString("PART_NO"));
                logger.debug(account.getString("B_YEAR"));
                logger.debug(account.getString("PERIODS"));
                psSelect.add(db.getString("STORAGE_CODE"));
                psSelect.add(db.getString("PART_NO"));
                psSelect.add(Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4));
                psSelect.add(Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2));
                psSelect.add(db.getString("PART_BATCH_NO"));
                logger.debug(this.getClass() + "------->psSelect.setString over!");

               List rs= DAOUtil.findAll(sbSelect.toString(), psSelect);
                                        

                if (rs.iterator().hasNext())
                {
                    List<Object>  psUpdate = new ArrayList<>(); 
                    //STOCK_IN_COUNT,STOCK_IN_AMOUNT,BUY_IN_COUNT,BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,          
                    psUpdate.add( Math.round(db.getFloat("IN_QUANTITY")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("STOCK_IN_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("BUY_IN_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("BUY_IN_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("ALLOCATE_IN_COUNT")*100)*0.01);

                    //ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,                       
                    psUpdate.add( Math.round(db.getDouble("ALLOCATE_IN_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("OTHER_IN_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("OTHER_IN_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("PROFIT_IN_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("PROFIT_IN_AMOUNT") * 100) * 0.01);

                    //STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,                       
                    psUpdate.add( Math.round(db.getFloat("OUT_QUANTITY")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getDouble("OUT_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("REPAIR_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("REPAIR_OUT_COST_AMOUNT") * 100) * 0.01);

                    //REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,                      
                    psUpdate.add( Math.round(db.getDouble("REPAIR_OUT_SALE_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("SALE_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("SALE_OUT_COST_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getDouble("SALE_OUT_SALE_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("INNER_OUT_COUNT")*100)*0.01);

                    //INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,                     
                    psUpdate.add( Math.round(db.getDouble("INNER_OUT_COST_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getDouble("INNER_OUT_SALE_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("ALLOCATE_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("ALLOCATE_OUT_COST_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT") * 100) * 0.01);

                    //OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT,LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,                       
                    psUpdate.add( Math.round(db.getFloat("OTHER_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("OTHER_OUT_COST_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getDouble("OTHER_OUT_SALE_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("LOSS_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("LOSS_OUT_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("TRANSFER_IN_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("TRANSFER_IN_AMOUNT") * 100) * 0.01);
                    psUpdate.add( Math.round(db.getFloat("TRANSFER_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("TRANSFER_OUT_COST_AMOUNT") * 100) * 0.01);
                    
//                  UPHOLSTER_OUT_COUNT
                    psUpdate.add( Math.round(db.getFloat("UPHOLSTER_OUT_COUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT")* 100) * 0.01);

                    psUpdate.add( Math.round(db.getFloat("IN_QUANTITY")*100)*0.01);
                    psUpdate.add( Math.round(db.getFloat("OUT_QUANTITY")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("CLOSE_PRICE") * 10000) * 0.0001);
                    psUpdate.add( Math.round(db.getDouble("STOCK_IN_AMOUNT")*100)*0.01);
                    psUpdate.add( Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT")*100)*0.01);
                    
                    
                    

                    //UPDATED_BY,UPDATED_AT,PART_PERIOD_REPORT_ID
                    psUpdate.add(db.getString("UPDATED_BY"));
                    psUpdate.add( Utility.getCurrentTimestamp());
                    psUpdate.add( entityCode);
                    psUpdate.add( Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4));
                    psUpdate.add( Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2));
                    psUpdate.add( db.getString("STORAGE_CODE"));
                    psUpdate.add( db.getString("PART_BATCH_NO"));
                    psUpdate.add( db.getString("PART_NO"));
                    logger.debug(this.getClass() + "--------->psUpdate over!");
                    DAOUtil.execBatchPreparement(sbSqlUpdate.toString(), psUpdate);
                    logger.debug(this.getClass() + "--------->psUpdate Execute over!");
                }
                else
                {
                    //InsertSQL的?的次序：
                    // REPORT_YEAR, REPORT_MONTH,STOCK_IN_COUNT, STOCK_IN_AMOUNT, BUY_IN_COUNT  
                    logger.debug("REPORT_YEAR:" + Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4));
                    logger.debug("REPORT_MONTH:"  + Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2));
                           
                    psInsert.add( Utility.fullSpaceBuffer2(account.getString("B_YEAR"), 4));
                    psInsert.add( Utility.fullSpaceBuffer2(account.getString("PERIODS"), 2));
                    psInsert.add( db.getString("STORAGE_CODE"));
                    psInsert.add( db.getString("PART_BATCH_NO"));
                    psInsert.add( db.getString("PART_NO"));
                    psInsert.add( entityCode);
                    psInsert.add( db.getString("PART_NAME"));

                    psInsert.add( Math.round(db.getFloat("IN_QUANTITY")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("STOCK_IN_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("BUY_IN_COUNT")*100)*0.01);

                    //BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT                       
                    psInsert.add( Math.round(db.getDouble("BUY_IN_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("ALLOCATE_IN_COUNT")*100)*0.01);
                    psInsert.add(Math.round(db.getDouble("ALLOCATE_IN_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("OTHER_IN_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("OTHER_IN_AMOUNT")*100)*0.01);

                    //PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT                      
                    psInsert.add( Math.round(db.getFloat("PROFIT_IN_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("PROFIT_IN_AMOUNT")*100)*0.01);
                    //psInsert.setDouble(18, Math.round(db.getSaleOutCount()*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("OUT_QUANTITY")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("STOCK_OUT_COST_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("OUT_AMOUNT")*100)*0.01);

                    //REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT                     
                    psInsert.add( Math.round(db.getFloat("REPAIR_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("REPAIR_OUT_COST_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("REPAIR_OUT_SALE_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("SALE_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("SALE_OUT_COST_AMOUNT")*100)*0.01);

                    //SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT, ALLOCATE_OUT_COUNT                     
                    psInsert.add( Math.round(db.getDouble("SALE_OUT_SALE_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("INNER_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("INNER_OUT_COST_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("INNER_OUT_SALE_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("ALLOCATE_OUT_COUNT")*100)*0.01);

                    //ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT                         
                    psInsert.add( Math.round(db.getDouble("ALLOCATE_OUT_COST_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("ALLOCATE_OUT_SALE_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("OTHER_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("OTHER_OUT_COST_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("OTHER_OUT_SALE_AMOUNT")*100)*0.01);

                    //LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,CREATED_BY,CREATED_AT, ORG_CODE                    
                    psInsert.add( Math.round(db.getFloat("LOSS_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("LOSS_OUT_AMOUNT")*100)*0.01);
                    psInsert.add( db.getString("CREATED_BY"));
                    psInsert.add( new java.sql.Date(System.currentTimeMillis()));
                    psInsert.add( Math.round(db.getFloat("TRANSFER_IN_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("TRANSFER_IN_AMOUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getFloat("TRANSFER_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("TRANSFER_OUT_COST_AMOUNT")*100)*0.01);
                    
                    psInsert.add( Math.round(db.getFloat("OPEN_QUANTITY")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("OPEN_PRICE") * 10000) * 0.0001);
                    psInsert.add( Math.round(db.getDouble("OPEN_AMOUNT")*100)*0.01);
                    
                    //UPHOLSTER_OUT_COUNT add by jll 2011-09-09
                    psInsert.add( Math.round(db.getFloat("UPHOLSTER_OUT_COUNT")*100)*0.01);
                    psInsert.add( Math.round(db.getDouble("UPHOLSTER_OUT_COST_AMOUNT") * 100) * 0.01);
                    psInsert.add( Math.round(db.getDouble("UPHOLSTER_OUT_SALE_AMOUNT")*100)*0.01);
                    
                    psInsert.add( getSubF(getAddF(db.getFloat("OPEN_QUANTITY"),db.getFloat("IN_QUANTITY")),db.getFloat("OUT_QUANTITY")));
                    psInsert.add( Math.round(db.getDouble("CLOSE_PRICE") * 10000) * 0.0001);
                    psInsert.add(getSubD(getAddD(db.getDouble("OPEN_AMOUNT"),db.getDouble("STOCK_IN_AMOUNT")),db.getDouble("STOCK_OUT_COST_AMOUNT")));
                    logger.debug(this.getClass() + "------->psInsert set over!");
                    logger.debug("db.getDealerId()--->" + entityCode);
                    //psInsert.executeUpdate();
                    DAOUtil.execBatchPreparement(sbSqlInsert.toString(), psInsert);
                    logger.debug(this.getClass() + "------->psInsert Execute over!");
                }
               
            }
            logger.debug(this.getClass() + "createOrUpdate over!");
            
            
      
            
            BusinessUtility.createOrUpdateDaily(db2,entityCode);
            logger.debug(this.getClass() + "createOrUpdateDaily over!");
            return 0;
      
    }
    private static Double getSubF(Double v1,Float v2){
        String s1="0";
        String s2="0";
        if(v1!=null)
        s1=v1.toString();
        if(v2!=null)
        s2=v2.toString();
        double d=Utility.round(new Double(Utility.sub(s1, s2)).toString(),2);
        return d;
    }
    private static Double getAddF(Float v1,Float v2){
        String s1="0";
        String s2="0";
        if(v1!=null)
        s1=v1.toString();
        if(v2!=null)
        s2=v2.toString();
        double d=Utility.round(new Double(Utility.add(s1, s2)).toString(),2);
        return d;
    }
    private static Double getAddD(Double v1,Double v2){
        String s1="0";
        String s2="0";
        if(v1!=null)
        s1=v1.toString();
        if(v2!=null)
        s2=v2.toString();
        return Utility.round(new Double(Utility.add(s1, s2)).toString(),2);
    }
    private static Double getSubD(Double v1,Double v2){
        String s1="0";
        String s2="0";
        if(v1!=null)
        s1=v1.toString();
        if(v2!=null)
        s2=v2.toString();
        return Utility.round(new Double(Utility.sub(s1, s2)).toString(),2);
    }
    public int adjustPartSendPriceLog(TtRepairOrderDTO ttRepairDto) throws ParseException{
        //           判断updateStatus[]是否为空
        if (!CommonUtils.isNullOrEmpty(ttRepairDto.getPartSendPrice())) {
        {
            LinkedList<OperateLogPO> linkedList = new LinkedList<OperateLogPO>();

            // 循环地初始化
            for (int i = 0; i < ttRepairDto.getPartSendPrice().size(); i++)
            {
                ListPtDmsOrderPriceDTO priceMap = ttRepairDto.getPartSendPrice().get(i);
                String partSalesPrice = priceMap.getPartSalesPrice() + "";
                String oldPrice = priceMap.getOldPrice() + "";
                if ((Utility.getDouble(partSalesPrice) == Utility.getDouble(oldPrice.toString()))) {
                    continue;
                }
                logger.debug("工单号:"+ttRepairDto.getRoNo()+"配件:"+priceMap.getPartNo());
                OperateLogPO operateLog = new OperateLogPO();
      
                operateLog.setTime("OPERATE_DATE", Utility.getCurrentDateTime());
                operateLog.setInteger("OPERATE_TYPE", DictCodeConstants.DICT_ASCLOG_PART_MANAGE);
                operateLog.setString("OPERATOR", FrameworkUtil.getLoginInfo().getUserCode());
                        
                String content = "";
             // 判断发料价格是否低于成本价格
                if (partSalesPrice != null  && priceMap.getCostPrice() != null&& Utility.getDouble(priceMap.getPartSalesPrice()+"") < Utility.getDouble(priceMap.getCostPrice()+""))
                {
                    content = " 发料价格低于成本价：";
                }
                else if (partSalesPrice != null && oldPrice != null&& Utility.getDouble(priceMap.getPartSalesPrice()+"") < Utility.getDouble(priceMap.getOldPrice()+""))         
                {
                    content = " 发料价格低于销售价格：";

                }
                else if (partSalesPrice != null && priceMap.getLimitPrice() != null&& Utility.getDouble(priceMap.getPartSalesPrice()+"") > Utility.getDouble(priceMap.getLimitPrice()))
                {
                    content = " 发料价格高于销售限价: ";
                }
                if (ttRepairDto.getRoNo() != null && ttRepairDto.getLicense() != null && priceMap.getStorageCode() != null && priceMap.getPartNo() != null)
                    operateLog.setString("Operate_Content", content + " 工单号【" + ttRepairDto.getRoNo() + "】车牌号【"
                            + ttRepairDto.getLicense() + "】 仓库代码 【" + priceMap.getStorageCode() + "】 配件号 【" + priceMap.getPartNo()
                            + "】 原销售价为【"+priceMap.getOldPrice()+"】 " +
                                    "调整后销售价为【"+priceMap.getPartSalesPrice()+"】");

               
                linkedList.add(operateLog);

            }

        }
    }
        return 0;
    }}
