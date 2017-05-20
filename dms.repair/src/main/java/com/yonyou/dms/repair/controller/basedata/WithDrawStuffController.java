
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : RepairOrderController.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月11日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月11日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TmPartStockItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartFlowDTO;
import com.yonyou.dms.commonAS.domains.DTO.order.RepairOrderDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.ListAdMaintainDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.RoManageDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TmPartBackDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtAccountsTransFlowDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMaintainTableDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemCardActiDetailDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemberPartDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemberPartFlowDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartMonthReportDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedItemDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartPeriodReportDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO;
import com.yonyou.dms.repair.service.basedata.LimitSeriesDatainfoService;
import com.yonyou.dms.repair.service.basedata.MemberPartFlowService;
import com.yonyou.dms.repair.service.basedata.MemberPartService;
import com.yonyou.dms.repair.service.basedata.PartMonthReportService;
import com.yonyou.dms.repair.service.basedata.ShortPartService;
import com.yonyou.dms.repair.service.basedata.TmPartStockItemService;
import com.yonyou.dms.repair.service.basedata.TmPartStockService;
import com.yonyou.dms.repair.service.basedata.TtPartFlowService;
import com.yonyou.dms.repair.service.order.WithDrawStuffService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


/**
 * 工单信息
 * 
 * @author chenwei
 * @date 2017年4月1日
 */

@Controller
@TxnConn
@RequestMapping("/basedata/ttRepairOrder")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class WithDrawStuffController extends BaseController {

    // 定义日志接口
    private static final Logger        logger = LoggerFactory.getLogger(WithDrawStuffController.class);
    @Autowired
    private WithDrawStuffService       withDrawStuffService;

    @Autowired
    private TmPartStockItemService     tmPartStockItemService;

    @Autowired
    private TtPartFlowService          partFlowService;

    @Autowired
    private ShortPartService           shortPartService;

    @Autowired
    private MemberPartService          memberPartService;

    @Autowired
    private MemberPartFlowService      memberPartFlowService;

    @Autowired
    private LimitSeriesDatainfoService limitSeriesDatainfoService;

    @Autowired
    private OperateLogService          operateLogService;

    @Autowired
    private CommonNoService            commonNoService;

    @Autowired
    private TmPartStockService         tmPartStockService;

    @Autowired
    private PartMonthReportService     partMonthReportService;

    /**
     * 弹出工单查询界面
     * 
     * @author chenwei
     * @date 2017年4月1日
     * @return
     */
    @RequestMapping(value = "/SearchRepairOrder", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> checkRoDetail(@RequestParam Map<String, String> queryParams) {
        List<Map> ttRepairOrderMapping = withDrawStuffService.checkRoDetail(queryParams);
        return ttRepairOrderMapping;
    }

    /**
     * 新增配件信息 TODO description
     * 
     * @author chenwei
     * @date 2017年4月7日
     * @param TtRoRepairPartDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void addTtRoRepairPart(@RequestBody TtRoRepairPartDTO ttRoRepairPartDTO,
                                  UriComponentsBuilder uriCB) throws ServiceBizException {
        //
        withDrawStuffService.addTtRoRepairPart(ttRoRepairPartDTO);
    }

    /**
     * 根据itemId修改工单对应的配件明细 TODO description
     * 
     * @author chenwei
     * @date 2017年4月7日
     * @param troubleCode
     * @param troubleDescDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void updateTroubleDesc(@PathVariable("itemId") Long itemId, @RequestBody TtRoRepairPartDTO ttRoRepairPartDTO,
                                  UriComponentsBuilder uriCB) throws ServiceBizException {
        withDrawStuffService.modifyByItemId(itemId, ttRoRepairPartDTO);
    }

    /**
     * 检查工单单据是否被锁住 TODO description
     * 
     * @author chenwei
     * @date 2017年4月5日
     * @param dealerCode
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/checkRepairOrderLock/{roNo}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> checkRepairOrderLock(@PathVariable("roNo") String roNo) throws IOException {
        List<Map> listMap = new ArrayList<Map>();
        RepairOrderDTO repairOrderto = new RepairOrderDTO();
        Map map = new HashMap();
        if (!StringUtils.isNullOrEmpty(roNo)) {
            map.put("roNo", roNo);
            List<Map> list = withDrawStuffService.checkRepairOrderLock(map);
            // 如果锁住则返回lock_user值
            if (null != list && list.size() > 0 && !StringUtils.isNullOrEmpty(list.get(0).get("RO_NO"))) {
                throw new ServiceBizException("业务加锁错误");
            } else {
                // 如果没有锁住就锁住
                withDrawStuffService.lockRepairOrder(roNo, repairOrderto);
            }
        }
        return listMap;
    }

    /**
     * @author chenwei
     * @date 2017年4月5日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/SearchMaintainPicking", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMaintainPicking(@RequestParam Map<String, String> queryParam) {
        return withDrawStuffService.queryMaintainPicking(queryParam);
    }
    
    /**
     * @author chenwei
     * @date 2017年5月1日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/roBackPart", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRepairOrderByCommitClose(@RequestParam Map<String, Object> queryParam) {
        return withDrawStuffService.queryRepairOrderByCommitClose(queryParam);
    }
    
    /**
     * @author chenwei
     * @date 2017年5月3日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/searchTtRoLabour/{roNo}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryTtRoLabourList(@PathVariable(value = "roNo") String roNo) {
        Map map = new HashMap();
        map.put("roNo", roNo);
        return withDrawStuffService.queryTtRoLabourList(map);
    }

    /**
     * 查询partInfo信息
     * 
     * @author chenwei
     * @date 2017年4月6日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartInfo", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfo(@RequestParam Map<String, String> queryParam) {
        return withDrawStuffService.queryPartInfo(queryParam);
    }
    
    /**
     * 查询partInfo信息
     * 
     * @author chenwei
     * @date 2017年5月3日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryBorrowPartInfo/{roNo}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryBorrowPartInfo(@PathVariable(value = "roNo") String roNo) {
        Map queryParam = new HashMap();
        queryParam.put("roNo", roNo);
        return withDrawStuffService.queryBorrowPartInfo(queryParam);
    }

    /**
     * 双击显示配件库存信息
     * 
     * @author chenwei
     * @date 2017年4月6日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartStock/{partNo}/{storageCode}", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartStock(@PathVariable("partNo") String partNo,
                                      @PathVariable("storageCode") String storageCode) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("partNo", partNo);
        map.put("storageCode", storageCode);
        return withDrawStuffService.queryPartStock(map);
    }

    /**
     * 员工下拉框加载
     * 
     * @author chenwei
     * @date 2017年4月12日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/employees/dict", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectEmployees(@RequestParam Map<String, String> queryParam) {
        List<Map> employeelist = withDrawStuffService.selectEmployees(queryParam);
        return employeelist;
    }

    /**
     * 出库前校验
     * 
     * @author chenwei
     * @date 2017年4月12日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/queryStockNegative", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryStockNegative(@RequestParam Map<String, String> queryParam) {
        String parentRow = queryParam.get("a");
        return null;
    }

    /**
     * 入账
     * 
     * @author chenwei
     * @date 2017年4月13日
     * @param queryParam
     * @return
     */
    @RequestMapping(value = "/account", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Map> account(@RequestBody ListAdMaintainDTO listAdMaintainDTO, UriComponentsBuilder uriCB) {
        String[] itmeIdString = new String[listAdMaintainDTO.getMaintainPickingTbl().size()];
        String[] oldPartPrice = new String[listAdMaintainDTO.getMaintainPickingTbl().size()];
        List<TmPartBackDTO> tmPartBackList = new ArrayList<TmPartBackDTO>();
        // 保存维修项目的RECORD_ID和ITEM_ID
        List tmPartStockA = new ArrayList();
        // 循环地初始化RoRepairPart Map
        Map<Integer, Map> map = new HashMap<Integer, Map>();
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        int isQueryPartBear = queryPartBear(listAdMaintainDTO, map, itmeIdString, dealerCode);//维修领料 保存当前编辑信息
        if(isQueryPartBear <= 0) return null;
        int isFinished  = QueryMonthPeriodIsFinished(listAdMaintainDTO);//查询本月的报表是否完成 查询当前时间的会计周期是否做过月结
        if(isFinished <= 0) return null;
        int isAccountRepairPart = AccountRepairPart(listAdMaintainDTO, map, itmeIdString, dealerCode, oldPartPrice);// 维修领料明细之配件入账
        if(isAccountRepairPart <= 0) return null;
        int isAuto = AutoRelievePartLeave(listAdMaintainDTO, dealerCode);
        if(isAuto <= 0) return null;
        int isBuild = BuildVoucherRepairPartDetailsRecord(listAdMaintainDTO, dealerCode);
        if(isBuild <= 0) return null;
        int isMain = MaintainPartBack(listAdMaintainDTO, dealerCode, tmPartStockA, tmPartBackList);
        if(isMain <= 0) return null;
        /**
         * 业务描述：经销商发料检验功能与更新交易平台呆滞件数量上报接口（DMS->DCS）
         * 
         * @author lujia
         * @date 2016-8-24
         * 
         */
        //SEDMS069();
        int isCommon = CommonEndAction(listAdMaintainDTO, dealerCode);
        if(isCommon <= 0) return null;
        return null;
    }
    
    public int CommonEndAction(ListAdMaintainDTO listAdMaintainDTO, String dealerCode){
        
        try {
            if(StringUtils.isNullOrEmpty(dealerCode)) return 0;
            
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int MaintainPartBack(ListAdMaintainDTO listAdMaintainDTO, String dealerCode, List tmPartStockA, List<TmPartBackDTO> tmPartBackList){
        
        try {
            List<TtMaintainTableDTO> tableList = listAdMaintainDTO.getMaintainPickingTbl();
            String roNo = listAdMaintainDTO.getRoNo();
            if(tmPartBackList != null && tmPartBackList.size() > 0){
                for (int i = 0; i < tmPartBackList.size(); i++){
                    TtMaintainTableDTO tableDto = (TtMaintainTableDTO)tableList.get(i);
                    String partQuantity = tableDto.getPartQuantity().toString();
                    if (partQuantity.indexOf(".") != -1) {
                        partQuantity = partQuantity.substring(0, partQuantity.indexOf("."));
                    }
                    int quantity = Integer.parseInt(partQuantity);
                    TmPartBackDTO backDto = (TmPartBackDTO)tmPartBackList.get(i);
                    String recordId = backDto.getRecordId();//待确认，数据库表无此字段
                    String[] backCode = backDto.getBackCode().split(",");
                    String isStockOut = "";
                    if(quantity > 0){
                        isStockOut = DictCodeConstants.DICT_IS_YES;
                    }else{
                        isStockOut = DictCodeConstants.DICT_IS_NO;
                    }
                    String partNo = backDto.getPartNo();
                    String storageCode = backDto.getStorageCode();
                    for (int j = 0; j < tmPartStockA.size(); j++){
                        Map dynaBean = (Map) tmPartStockA.get(i);
                        String recordIdPart = (String) dynaBean.get("RECORD_ID");
                        String itemIdPart = (String) dynaBean.get("ITEM_ID");
                        if (recordId.equals(recordIdPart)){
                            for (int k = 0; k < backCode.length; k++){
                                TmPartBackDTO partBack = new TmPartBackDTO();
                                partBack.setDealerCode(dealerCode);
                                partBack.setBackCode(backCode[k]);
                                partBack.setIsStockOut(Integer.parseInt(isStockOut));
                                partBack.setPartNo(partNo);
                                partBack.setStorageCode(storageCode);
                                partBack.setItemIdPart(Long.parseLong(itemIdPart));
                                partBack.setDKey(CommonConstants.D_KEY);
                                //partBack.setCreatedBy(1L);//待确认 
                                //partBack.setCreatedDate(new Date());//待确认 
                                TmPartBackDTO partBackCon = new TmPartBackDTO();
                                partBackCon.setBackCode(backCode[k]);
                                partBackCon.setPartNo(partNo);
                                partBackCon.setStorageCode(storageCode);
                                partBackCon.setDKey(CommonConstants.D_KEY);
                                modifyTmPartBackDTOByParams(partBackCon, partBack);
                            }
                        }
                    }
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int modifyTmPartBackDTOByParams(TmPartBackDTO partBackCon, TmPartBackDTO partBack){
        StringBuilder sqlStr = new StringBuilder(" DEALER_CODE = ?, BACK_CODE = ?, IS_STOCK_OUT = ?, PART_NO = ?");
        sqlStr.append(", STORAGE_CODE = ?, ITEM_ID_PART = ?, D_KEY = ?");
        StringBuilder sqlWhere = new StringBuilder(" BACK_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ? ");
        List paramsList = new ArrayList();
        paramsList.add(partBack.getDealerCode());
        paramsList.add(partBack.getBackCode());
        paramsList.add(partBack.getIsStockOut());
        paramsList.add(partBack.getPartNo());
        paramsList.add(partBack.getStorageCode());
        paramsList.add(partBack.getItemIdPart());
        paramsList.add(partBack.getDKey());
        paramsList.add(partBackCon.getBackCode());
        paramsList.add(partBackCon.getPartNo());
        paramsList.add(partBackCon.getStorageCode());
        paramsList.add(partBackCon.getDKey());
        return  withDrawStuffService.modifyTmPartBackDTOByParams(sqlStr.toString(), sqlWhere.toString(), paramsList);
    }
    
    /**
     * 维修领料明细之配件入账生产凭证
    * TODO description
    * @author chenwei
    * @date 2017年4月30日
    * @param listAdMaintainDTO
    * @param dealerCode
    * @return
     */
    public int BuildVoucherRepairPartDetailsRecord(ListAdMaintainDTO listAdMaintainDTO, String dealerCode){
        
        try {
            String roNo = listAdMaintainDTO.getRoNo();
            Map<String, Object> queryDefaultPara = new HashMap<String, Object>();
            queryDefaultPara.put("itemCode", CommonConstants.FINANCE_ITEM_CODE);
            String defaultpo = withDrawStuffService.selectDefaultPara(queryDefaultPara);
          //获取开关设置
            if(defaultpo != null && DictCodeConstants.DICT_IS_YES.equalsIgnoreCase(defaultpo) ){
                Map<String, Object> defaultParams = new HashMap<String, Object>();
                defaultParams.put("itemCode", CommonConstants.REPAIR_CESS_ITEM_CODE);
                String defaultCess = withDrawStuffService.selectDefaultPara(defaultParams);
                float  cess =  Float.parseFloat(defaultCess);
                Map rrpc=new HashMap();
                rrpc.put("dKey", CommonConstants.D_KEY);
                rrpc.put("roNo", roNo);
                List<Map> list= withDrawStuffService.selectRoRepairPart(rrpc);
                List<TtRoRepairPartDTO> dtoList = withDrawStuffService.changeMapToRoRepairPart(list);
                for(int i = 0; i < dtoList.size(); i++){
                    TtRoRepairPartDTO rrp=(TtRoRepairPartDTO)dtoList.get(i);
                    TtAccountsTransFlowDTO  po  = new  TtAccountsTransFlowDTO();
                    //Long transId = POFactory.getLongPriKey(conn, po);
                    //po.setTransId(transId);//待确认
                    po.setOrgCode(dealerCode);
                    po.setDealerCode(dealerCode);          
                    po.setTransDate(Utility.getCurrentDateTime());                 
                    po.setTransType(Utility.getInt(CommonConstants.DICT_BUSINESS_TYPE_REPAIR_PART));
                   if(rrp.getRO_NO()!=null && !rrp.getRO_NO().equals(""))
                    po.setSubBusinessNo(rrp.getRO_NO());        
                   po.setBusinessNo(rrp.getRO_NO());        
                   po.setTaxAmount(rrp.getPART_SALES_AMOUNT());//配件销售金额
                   po.setNetAmount(rrp.getPART_SALES_AMOUNT()*(1F- cess));
                   po.setIsValid(Utility.getInt(DictCodeConstants.DICT_IS_YES));
                   po.setExecNum(0);
                   po.setExecStatus(Utility.getInt(CommonConstants.DICT_EXEC_STATUS_NOT_EXEC));//未生产
                   withDrawStuffService.addTtAccountsTransFlow(po);
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     *自动解除配件预留 ,用于维修领料入账以及工单作废时 
    * TODO description
    * @author chenwei
    * @date 2017年4月30日
    * @param listAdMaintainDTO
    * @param dealerCode
    * @return
     */
    public int AutoRelievePartLeave(ListAdMaintainDTO listAdMaintainDTO, String dealerCode){
      //预约预留参数
        String roNo = "";
        String ObligatedNo = "";
        //if (actionContext.getStringValue("TT_REPAIR_ORDER.RO_NO") != null && !actionContext.getStringValue("TT_REPAIR_ORDER.RO_NO").equals(""))
        //    roNo = actionContext.getStringValue("TT_REPAIR_ORDER.RO_NO");
        //else 
        if(!StringUtils.isNullOrEmpty(listAdMaintainDTO.getRoNo())){
            roNo = String.valueOf(listAdMaintainDTO.getRoNo());
        }else{
            return 0;
        }
        Boolean isDelete = false;
        Boolean isCommit = false;
        //if (actionContext.getStringValue("IS_DELETE") != null && actionContext.getStringValue("IS_DELETE").equals(DictDataConstant.DICT_IS_YES))
        //    isDelete = true;
        try {
            long userId = FrameworkUtil.getLoginInfo().getUserId();
            if(!StringUtils.isNullOrEmpty(dealerCode)){
                throw new ServiceBizException("丢失主键值");
            }
            Map roCon = new HashMap();
            roCon.put("roNo", roNo);
            roCon.put("dKey", CommonConstants.D_KEY);
            List roList = withDrawStuffService.selectRepairOrder(roCon);
            if (roList != null && roList.size() > 0){
                Map ro = (Map) roList.get(0);
                isCommit = ro.get("roStatus").toString().equals(DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE);
            }
            else return 0;
            TtPartObligatedDTO poCon = new TtPartObligatedDTO();
            poCon.setSheetNo(roNo);
            poCon.setDKey(CommonConstants.D_KEY);
            List<Map> mapList = withDrawStuffService.queryTtPartObligatedByObject(poCon);
            List<TtPartObligatedDTO> poList = mapToObject(mapList);
            if (poList != null && poList.size() > 0){
                for(int t = 0; t < poList.size(); t++){
                    TtPartObligatedDTO po = (TtPartObligatedDTO) poList.get(t);
                    ObligatedNo = po.getObligatedNo();
                    if(StringUtils.isNullOrEmpty(ObligatedNo))
                        continue;
                  //如果已关单，则不能解预留
                    if(!StringUtils.isNullOrEmpty(po.getObligatedClose())){
                        if (DictCodeConstants.DICT_IS_YES.equals(String.valueOf(po.getObligatedClose())))
                            continue;
                    }
                  //工单解预留逻辑处理
                    TtPartObligatedItemDTO  itemCon = new TtPartObligatedItemDTO();
                    itemCon.setObligatedNo(ObligatedNo);
                    itemCon.setDKey(CommonConstants.D_KEY);
                    itemCon.setDealerCode(dealerCode);
                    List<Map> itemMapList = withDrawStuffService.queryTtPartObligatedItemByObject(itemCon);
                    List<TtPartObligatedItemDTO> itemList = withDrawStuffService.changeMapToTtPartObligatedItem(itemMapList);
                    if(itemList != null && itemList.size() > 0){
                        Double quantity = 0D;
                        for(int i = 0; i < itemList.size(); i++){
                            TtPartObligatedItemDTO  itempo = (TtPartObligatedItemDTO) itemList.get(i);
                            if (!DictCodeConstants.DICT_IS_YES.equals(itempo.getIsObligated().toString()))
                                continue;   //已被解预留的配件不再解预留
                            if (!isDelete && !isCommit){
                                Map partCon = new HashMap();
                                partCon.put("roNo", roNo);
                                partCon.put("partNo", itempo.getPartNo());
                                partCon.put("storageCode", itempo.getStorageCode());
                                partCon.put("dKey", CommonConstants.D_KEY);
                                List<Map> mapListPart = withDrawStuffService.selectRoRepairPart(partCon);
                                List<TtRoRepairPartDTO> listPart = withDrawStuffService.changeMapToRoRepairPart(mapListPart);
                                if (listPart != null && listPart.size() > 0){
                                    TtRoRepairPartDTO partPo = (TtRoRepairPartDTO) listPart.get(0);
                                    if (partPo.getIS_FINISHED() == null || !partPo.getIS_FINISHED().toString().equals(DictCodeConstants.DICT_IS_YES)
                                            || itempo.getIsObligated().toString().equals(DictCodeConstants.DICT_IS_NO))
                                        continue;
                                }
                                else
                                    continue;
                            }
                            quantity = itempo.getQuantity();
                            itemCon.setItemId(itempo.getItemId());
                            itempo.setQuantity(0.0D);
                            itempo.setCostAmount(0.0D);
                            itempo.setCostPrice(0.0D);
                            itempo.setIsObligated(Utility.getInt(DictCodeConstants.DICT_IS_NO));
                            modifyTtPartObligatedItemByParams(itemCon, itempo);
                         // 同时更新配件库存信息       
                            TmPartStockDTO stock = new TmPartStockDTO();
                            Map stockCondition = new HashMap();
                            
                            stockCondition.put("partNo", itempo.getPartNo());
                            stockCondition.put("storageCode", itempo.getStorageCode());
                            stockCondition.put("dKey", CommonConstants.D_KEY);
                            logger.debug("工单解锁 更新配件库存信息"+itempo.getPartNo());
                            logger.debug("工单解锁 更新配件库存信息"+itempo.getStorageCode());
                            List tmpartlist = tmPartStockService.selectPartStock(stockCondition);
                            List<TmPartStockDTO> rsList = tmPartStockService.changeMapToTmPartStockDTO(tmpartlist);
                            if (rsList != null){
                                TmPartStockDTO part = (TmPartStockDTO) rsList.get(0);
                                String lock = part.getLockedQuantity().toString();
                                logger.debug("配件预留 库存 有记录 :"+lock);
                                if (quantity != null){
                                //判断明细中配件的预留量是否大于库存中相应配件的锁定量
                                    if (Double.valueOf(lock)>=Double.valueOf(quantity)){
                                        float lockQuantity = (float) Utility.sub(lock, quantity.toString());
                                        stock.setLockedQuantity(lockQuantity);
                                    }
                                    else{
                                        stock.setLockedQuantity(Float.valueOf(lock));
                                    }
                                        
                                    
                                }
                            }
                            if (StringUtils.isNullOrEmpty(itempo.getPartNo()) || StringUtils.isNullOrEmpty(itempo.getStorageCode())){
                                //actionContext.setErrorContext(
                                //                              CommonErrorConstant.MSG_ERROR_LOST_KEY,
                                //                              MessageService.getInstance().getMessage(
                                //                                      CommonErrorConstant.MSG_ERROR_LOST_KEY),
                                //                              null);
                                 throw new ServiceBizException("丢失主键值");
                            }
                            modifyTmPartStockrByParams(stockCondition, stock);
                        }
                    }
                }
            }else{
                return 1;
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
            //throw new ServiceBizException("更新错误");
        }
    }
    
    public int modifyTmPartStockrByParams(Map stockCondition, TmPartStockDTO stock){
        StringBuilder sqlStr = new StringBuilder(" LOCKED_QUANTITY = ").append(stock.getLockedQuantity()).append(" ");
        StringBuilder sqlWhere = new StringBuilder(" PART_NO = ").append(stockCondition.get("partNo")).append(" and STORAGE_CODE = ");
        sqlWhere.append(stockCondition.get("storageCode")).append(" and D_KEY = ").append(stockCondition.get("dKey"));
        return tmPartStockService.modifyTmPartStockrByParams(sqlStr.toString(), sqlWhere.toString());
    }
    
    public int modifyTtPartObligatedItemByParams(TtPartObligatedItemDTO  itemCon, TtPartObligatedItemDTO  itempo){
        StringBuilder sqlStr = new StringBuilder(" OBLIGATED_NO = ?, IS_OBLIGATED = ?, PART_NO = ?, STORAGE_CODE = ?, QUANTITY = ?, COST_AMOUNT = ?, COST_PRICE = ?");
        StringBuilder sqlWhere = new StringBuilder(" OBLIGATED_NO = ? and D_KEY = ? and ITEM_ID = ? and DEALER_CODE = ? ");
        List params = new ArrayList();
        params.add(itempo.getObligatedNo());
        params.add(itempo.getIsObligated());
        params.add(itempo.getPartNo());
        params.add(itempo.getStorageCode());
        params.add(itempo.getQuantity());
        params.add(itempo.getCostAmount());
        params.add(itempo.getCostPrice());
        params.add(itemCon.getObligatedNo());
        params.add(itemCon.getDKey());
        params.add(itemCon.getItemId());
        params.add(itemCon.getDealerCode());
        return withDrawStuffService.modifyTtPartObligatedItemByParams(sqlStr.toString(), sqlWhere.toString(), params);
    }
    
    public List<TtPartObligatedDTO> mapToObject(List<Map> list){
        List<TtPartObligatedDTO> dtoList = new ArrayList<TtPartObligatedDTO>();
        if(null != list && list.size() > 0){
            for(int i = 0;i<list.size();i++){
                Map map = list.get(i);
                TtPartObligatedDTO dto = new TtPartObligatedDTO();
                if(!StringUtils.isNullOrEmpty(map.get("OBLIGATED_NO")))
                dto.setObligatedNo(map.get("OBLIGATED_NO").toString());
                if(!StringUtils.isNullOrEmpty(map.get("OBLIGATED_CLOSE")))
                dto.setObligatedClose(Integer.valueOf(map.get("OBLIGATED_CLOSE").toString())); 
                if(!StringUtils.isNullOrEmpty(map.get("DEALER_CODE")))
                    dto.setDealerCode(map.get("DEALER_CODE").toString());
                dtoList.add(dto);
            }
            return dtoList;
        }else{
            return dtoList;
        }
    }

    public int queryPartBear(ListAdMaintainDTO listAdMaintainDTO, Map<Integer, Map> map, String[] itmeIdString,
                             String dealerCode) {
        Map paramsList = new HashMap();
        paramsList.put("roNo", listAdMaintainDTO.getRoNo());
        // paramsList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        paramsList.put("dKey", DictCodeConstants.D_KEY);
        // String empNo = actionContext.getStringValue(SystemManagementCommonConstant.SESSION_EMPNO);
        try {
            // 判断已提交结算的工单不允许做维修发料
            List<Map> repairOrderList = withDrawStuffService.selectRepairOrder(paramsList);
            if (repairOrderList != null && repairOrderList.size() > 0) {
                Map repairOrder = repairOrderList.get(0);
                if (("12551005").equals(repairOrder.get("roStatus")) || ("12551010").equals(repairOrder.get("roStatus"))) {
                    throw new ServiceBizException("工单" + repairOrder.get("roNo") + "不为在修,不允许维修发料!");
                }
            }
            // 保存维修项目的RECORD_ID和ITEM_ID
            List tmPartStockA = new ArrayList();
            TtRoRepairPartDTO repair = null;
            double oldPriceTrue = 0;// 修改前的配件单价 obc
            // 判断工单是否是索赔单，如果是索赔单，则根据"服务活动能否添加配件"参数判断，如果开关打开不能在添加其他配件
            List<TtMaintainTableDTO> maintainPickingTbl = listAdMaintainDTO.getMaintainPickingTbl();
            Long itemIdAuto = commonNoService.getId("TT_RO_REPAIR_PART");
            if (null != maintainPickingTbl && maintainPickingTbl.size() > 0) {
                for (int i = 0; i < maintainPickingTbl.size(); i++) {
                    TtMaintainTableDTO mainTainDto = maintainPickingTbl.get(i);
                    repair = new TtRoRepairPartDTO();
                    // 如果是新增
                    if("A".equals(mainTainDto.getItemUpdateStatus())){
                        
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartQuantity())  && !StringUtils.isNullOrEmpty(mainTainDto.getPartSalesPrice()) && Utility.round(String.valueOf(Utility.mul(String.valueOf(mainTainDto.getPartQuantity())
                           ,String.valueOf(mainTainDto.getPartSalesPrice()))),2) > 0) {
                        Map<String, Object> queryDefaultPara = new HashMap<String, Object>();
                        queryDefaultPara.put("itemCode", "3340");
                        String paraList = withDrawStuffService.selectDefaultPara(queryDefaultPara);
                        Map<String, Object> queryParams = new HashMap<String, Object>();
                        queryParams.put("roNo", listAdMaintainDTO.getRoNo());
                        queryParams.put("dKey", DictCodeConstants.D_KEY);
                        List<Map> roPartList = withDrawStuffService.selectRoRepairPart(queryParams);
                        int count = 0;
                        
                        if (roPartList != null && roPartList.size() > 0) {
                            String[] activityCodes = new String[roPartList.size()];
                            for (int j = 0; j < roPartList.size(); j++) {
                                activityCodes[j] = (String) roPartList.get(j).get("activityCode");
                            }
                            if (activityCodes.length > 0) {
                                List<Map> list = getTtActivityList(activityCodes);
                                if (list != null && list.size() > 0) {
                                    for (int j = 0; j < list.size(); j++) {
                                        Map activityMap = (Map) list.get(j);
                                        int downTag = Integer.parseInt(activityMap.get("DOWN_TAG").toString());
                                        if (downTag == Integer.parseInt(DictCodeConstants.DICT_IS_YES)) {
                                            count++;
                                        }
                                    }
                                }
                            }
                        }
                        //
                        if ((repairOrderList != null && repairOrderList.size() > 0)
                            && (!StringUtils.isNullOrEmpty(paraList))) {
                            Map po1 = repairOrderList.get(0);
                            if ("12531004".equals(po1.get("roType")) && "12781001".equals(paraList)
                                && count > 0) {
                                throw new ServiceBizException("索赔单已添加下发活动，不能再添加配件!");
                            }
                        }
                    }
                    // 标记为A进行插入操作
                    repair.setITEM_ID(itemIdAuto);
                    repair.setIS_FINISHED(Utility.getInt(DictCodeConstants.DICT_IS_NO));
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getActivityCode())) {
                        repair.setACTIVITY_CODE(mainTainDto.getActivityCode());
                    }
                    // 是否是老系统中的card_id 卡ID 有待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getCardId())) {
                        repair.setCARD_ID(Utility.getInt(mainTainDto.getCardId()));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getBatchNo())) {
                        repair.setBATCH_NO(Utility.getInt(mainTainDto.getBatchNo()));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getChargePartitionCode())) {
                        repair.setCHARGE_PARTITION_CODE(mainTainDto.getChargePartitionCode());
                    }
                    // 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getConsignExterior())) {
                        repair.setCONSIGN_EXTERIOR(Utility.getInt(mainTainDto.getConsignExterior()));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getDiscount())) {
                        repair.setDISCOUNT(mainTainDto.getDiscount());
                    }
                    repair.setDEALER_CODE(dealerCode);
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getIsMainPart())) {
                        repair.setIS_MAIN_PART(Utility.getInt(mainTainDto.getIsMainPart()));
                    }
                    // manageSortCode 收费类别代码 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getManageSortCode())) {
                        repair.setMANAGE_SORT_CODE(mainTainDto.getManageSortCode());
                    }
                    // needlessRepair 是否不修 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getNeedlessRepair())) {
                        repair.setNEEDLESS_REPAIR(Utility.getInt(mainTainDto.getNeedlessRepair()));
                    }
                    // oemLimitPrice OEM销售限价
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getOemLimitPrice())) {
                        repair.setOEM_LIMIT_PRICE(mainTainDto.getOemLimitPrice());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getOutStockNo())) {
                        repair.setOUT_STOCK_NO(mainTainDto.getOutStockNo());
                    }
                    // 进货批号 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartBatchNo())) {
                        repair.setPART_BATCH_NO(mainTainDto.getPartBatchNo());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartCostAmount())) {
                        repair.setPART_COST_AMOUNT(mainTainDto.getPartCostAmount());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartCostPrice())) {
                        repair.setPART_COST_PRICE(mainTainDto.getPartCostPrice());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartName())) {
                        repair.setPART_NAME(mainTainDto.getPartName());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartNo())) {
                        repair.setPART_NO(mainTainDto.getPartNo());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartQuantity())) {
                        repair.setPART_QUANTITY(mainTainDto.getPartQuantity());
                        if (!StringUtils.isNullOrEmpty(mainTainDto.getPartSalesPrice())) {
                            repair.setPART_SALES_AMOUNT(Utility.round(String.valueOf(Utility.mul(mainTainDto.getPartQuantity().toString(),
                                                                                                 mainTainDto.getPartSalesPrice().toString())),
                                                                      2));
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartSalesPrice())) {
                        repair.setPART_SALES_PRICE(mainTainDto.getPartSalesPrice());
                    }
                    // preCheck 是否预检 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPreCheck())) {
                        repair.setPRE_CHECK(mainTainDto.getPreCheck());
                    }
                    // priceRate 价格系数 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPriceRate())) {
                        repair.setPRICE_RATE(mainTainDto.getPriceRate());
                    }
                    // priceType 价格类型 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPriceType())) {
                        repair.setPRICE_TYPE(mainTainDto.getPriceType());
                    }
                    // printBatchNo 预捡单打印流水号 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPrintBatchNo())) {
                        repair.setPRINT_BATCH_NO(mainTainDto.getPrintBatchNo());
                    }
                    // printRpTime 预先捡料单打印时间 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPrintRpTime())) {
                        repair.setPRINT_RP_TIME(mainTainDto.getPrintRpTime());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getReceiver())) {
                        repair.setRECEIVER(mainTainDto.getReceiver());
                    }
                    repair.setRO_NO(listAdMaintainDTO.getRoNo());
                    repair.setITEM_ID(mainTainDto.getItemId());// repair.setItemId(POFactory.getLongPriKey(conn,
                                                               // repair)); 老系统代码
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getSender())) {
                        repair.setSENDER(mainTainDto.getSender());
                    }
                    // sendTime 发料时间 前台没有
                    if (!StringUtils.isNullOrEmpty(listAdMaintainDTO.getSendTime())) {
                        repair.setSEND_TIME(Utility.getTimeStamp(listAdMaintainDTO.getSendTime()));
                    }
                    // 前台没有
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getStorageCode())) {
                        repair.setSTORAGE_CODE(mainTainDto.getStorageCode());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getStoragePositionCode())) {
                        repair.setSTORAGE_POSITION_CODE(mainTainDto.getStoragePositionCode());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getUnitCode())) {
                        repair.setUNIT_CODE(mainTainDto.getUnitCode());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getLabourCode())) {
                        repair.setLABOUR_CODE(mainTainDto.getLabourCode());
                        List<Map> labourList = withDrawStuffService.queryRoLabourByCodeAndRoNo(dealerCode,
                                                                                               mainTainDto.getLabourCode(),
                                                                                               "", "",
                                                                                               listAdMaintainDTO.getRoNo(),
                                                                                               mainTainDto.getLabourName());
                        if (null != labourList) {
                            Map labourMap = labourList.get(0);
                            if (null != labourMap) {
                                repair.setITEM_ID_LABOUR(Utility.getInt(labourMap.get("ITEM_ID").toString()));
                            }
                        }
                    }
                    // sIS_DXP 未知字段 和朱恒龙,郑聪沟通下
                    //if (!StringUtils.isNullOrEmpty("sIS_DXP")) {
                    //    long Dxp = 6970002;
                    //    repair.setFROM_TYPE(Dxp);
                    //}
                    repair.setIS_DISCOUNT(Utility.getInt(mainTainDto.getIsDiscount()));
                    // 待确认 配件中缀
                    repair.setPART_INFIX(mainTainDto.getPartInfix());
                    TtRoRepairPartPO partPo = withDrawStuffService.addTtRoRepairPart(repair);
                    
                    //linked.add(Utility.getReturnDynaBeanNoVer("ITEM_ID", repair.getITEM_ID()
                     //                                         .toString(), Utility.getInt(mainTainDto.getRecordId())));
                      Map map2 = new HashMap();
                      map2.put("RECORD_ID", mainTainDto.getRecordId());
                      if(!StringUtils.isNullOrEmpty(itemIdAuto)){
                          itmeIdString[i] = itemIdAuto.toString();
                      map2.put("ITEM_ID", itemIdAuto);
                      }else{
                          map2.put("ITEM_ID", null);
                      }
                      tmPartStockA.add(map2);
                    // 如果是修改
                    }else if("U".equals(mainTainDto.getItemUpdateStatus())){
                    Map<String, Object> queryParams = new HashMap<String, Object>();
                    queryParams.put("roNo", listAdMaintainDTO.getRoNo());
                    queryParams.put("dKey", DictCodeConstants.D_KEY);
                    queryParams.put("itemId", mainTainDto.getItemId());
                    List<Map> roRepairPartList = withDrawStuffService.selectRoRepairPart(queryParams);
                    if (roRepairPartList != null && roRepairPartList.size() > 0) {
                        Map partMap = roRepairPartList.get(0);
                        oldPriceTrue = Utility.getDouble(partMap.get("partSalesPrice").toString());
                    }
                    repair = new TtRoRepairPartDTO();
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getActivityCode())) {
                        repair.setACTIVITY_CODE(mainTainDto.getActivityCode());
                    }
                    // 是否是老系统中的card_id 卡ID 有待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getCardId())) {
                        repair.setCARD_ID(Utility.getInt(mainTainDto.getCardId()));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getBatchNo())) {
                        repair.setBATCH_NO(Utility.getInt(mainTainDto.getBatchNo()));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getChargePartitionCode())) {
                        repair.setCHARGE_PARTITION_CODE(mainTainDto.getChargePartitionCode());
                    }
                    // 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getConsignExterior())) {
                        repair.setCONSIGN_EXTERIOR(Utility.getInt(mainTainDto.getConsignExterior()));
                    }
                    //repair.setUpdatedBy(FrameworkUtil.getLoginInfo().getUserId().toString());
                    //repair.setUpdatedAt(Utility.getCurrentDateTime());
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getDiscount())) {
                        repair.setDISCOUNT(mainTainDto.getDiscount());
                    }
                    repair.setDEALER_CODE(dealerCode);
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getIsMainPart())) {
                        repair.setIS_MAIN_PART(Utility.getInt(mainTainDto.getIsMainPart()));
                    }
                    // manageSortCode 收费类别代码 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getManageSortCode())) {
                        repair.setMANAGE_SORT_CODE(mainTainDto.getManageSortCode());
                    }
                    // needlessRepair 是否不修 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getNeedlessRepair())) {
                        repair.setNEEDLESS_REPAIR(Utility.getInt(mainTainDto.getNeedlessRepair()));
                    }
                    // oemLimitPrice OEM销售限价
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getOemLimitPrice())) {
                        repair.setOEM_LIMIT_PRICE(mainTainDto.getOemLimitPrice());
                    }
                    // 进货批号 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartBatchNo())) {
                        repair.setPART_BATCH_NO(mainTainDto.getPartBatchNo());
                    }
                    if (!(mainTainDto.getIsFinished().equals(DictCodeConstants.DICT_IS_YES))) {
                        String message5 = mainTainDto.getIsFinished() + "修改以前没有入账";
                        if (!StringUtils.isNullOrEmpty(mainTainDto.getPartCostAmount())) {
                            repair.setPART_COST_AMOUNT(mainTainDto.getPartCostAmount());
                        }
                        if (!StringUtils.isNullOrEmpty(mainTainDto.getPartCostPrice())) {
                            repair.setPART_COST_PRICE(mainTainDto.getPartCostPrice());
                        }
                        if (!StringUtils.isNullOrEmpty(mainTainDto.getOutStockNo())) {
                            repair.setOUT_STOCK_NO(mainTainDto.getOutStockNo());
                        }
                        if (!StringUtils.isNullOrEmpty(mainTainDto.getPartQuantity())) {
                            repair.setPART_QUANTITY(mainTainDto.getPartQuantity());
                        }
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartName())) {
                        repair.setPART_NAME(mainTainDto.getPartName());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartQuantity())
                        && !StringUtils.isNullOrEmpty(mainTainDto.getPartSalesPrice())) {
                        repair.setPART_SALES_AMOUNT(Utility.round(String.valueOf(Utility.mul(mainTainDto.getPartQuantity().toString(),
                                                                                             mainTainDto.getPartSalesPrice().toString())),
                                                                  2));
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPartSalesPrice())) {
                        repair.setPART_SALES_PRICE(mainTainDto.getPartSalesPrice());
                    }
                    // preCheck 是否预检 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPreCheck())) {
                        repair.setPRE_CHECK(mainTainDto.getPreCheck());
                    }
                    // priceRate 价格系数 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPriceRate())) {
                        repair.setPRICE_RATE(mainTainDto.getPriceRate());
                    }
                    // priceType 价格类型 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPriceType())) {
                        repair.setPRICE_TYPE(mainTainDto.getPriceType());
                    }
                    // printBatchNo 预捡单打印流水号 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPrintBatchNo())) {
                        repair.setPRINT_BATCH_NO(mainTainDto.getPrintBatchNo());
                    }
                    // printRpTime 预先捡料单打印时间 待确认
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getPrintRpTime())) {
                        repair.setPRINT_RP_TIME(mainTainDto.getPrintRpTime());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getReceiver())) {
                        repair.setRECEIVER(mainTainDto.getReceiver());
                    }
                    repair.setRO_NO(listAdMaintainDTO.getRoNo());
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getSender())) {
                        repair.setSENDER(mainTainDto.getSender());
                    }
                    // 前台没有
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getStorageCode())) {
                        repair.setSTORAGE_CODE(mainTainDto.getStorageCode());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getStoragePositionCode())) {
                        repair.setSTORAGE_POSITION_CODE(mainTainDto.getStoragePositionCode());
                    }
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getUnitCode())) {
                        repair.setUNIT_CODE(mainTainDto.getUnitCode());
                    }
                    Map<String, Object> roRepairPartPO = new HashMap<String, Object>();
                    roRepairPartPO.put("dKey", DictCodeConstants.D_KEY);
                    roRepairPartPO.put("itemId", mainTainDto.getItemId());
                    List<Map> listA = withDrawStuffService.selectRoRepairPart(roRepairPartPO);
                    if (listA != null && listA.size() > 0) {
                        Map mapA = (Map) listA.get(0);
                        map.put(i, mapA);
                    }
                    repair.setIS_DISCOUNT(Utility.getInt(mainTainDto.getIsDiscount()));
                    if (!StringUtils.isNullOrEmpty(mainTainDto.getLabourCode())) {
                        repair.setLABOUR_CODE(mainTainDto.getLabourCode());
                        List<Map> labourList = withDrawStuffService.queryRoLabourByCodeAndRoNo(dealerCode,
                                                                                               mainTainDto.getLabourCode(),
                                                                                               "", "",
                                                                                               listAdMaintainDTO.getRoNo(),
                                                                                               mainTainDto.getLabourName());
                        if (null != labourList) {
                            Map labourMap = labourList.get(0);
                            if (null != labourMap) {
                                repair.setITEM_ID_LABOUR(Utility.getInt(labourMap.get("ITEM_ID").toString()));
                            }
                        }
                    } else {
                        mainTainDto.setLabourName("");
                    }
                    repair.setLABOUR_NAME(mainTainDto.getLabourName());
                    // sIS_DXP 未知字段 和朱恒龙,郑聪沟通下
                    if (!StringUtils.isNullOrEmpty("sIS_DXP")) {
                        long Dxp = 6970002;
                        repair.setFROM_TYPE(Dxp);
                    }
                    // 待确认 配件中缀
                    repair.setPART_INFIX(mainTainDto.getPartInfix());
                    withDrawStuffService.modifyByItemId(mainTainDto.getItemId(), repair);
                     Map map3 = new HashMap();
                     map3.put("RECORD_ID", mainTainDto.getRecordId());
                     map3.put("ITEM_ID", String.valueOf(mainTainDto.getItemId()));
                     tmPartStockA.add(map3);
                    // 判断此条记录是否已经入账
                    if (mainTainDto.getPartSalesPrice() != oldPriceTrue) {
                        List<Map> listRo = new ArrayList<Map>();
                        Map<String, Object> repairDtoMap = new HashMap<String, Object>();
                        repairDtoMap.put("itemId", mainTainDto.getItemId());
                        repairDtoMap.put("roNo", listAdMaintainDTO.getRoNo());
                        repairDtoMap.put("dKey", DictCodeConstants.D_KEY);
                        repairDtoMap.put("isFinished", mainTainDto.getIsFinished());
                        listRo = withDrawStuffService.selectRoRepairPart(repairDtoMap);
                        if (listRo != null && listRo.size() > 0) {
                            PartFlowDTO flow = new PartFlowDTO();// 配件流水账
                            flow.setCostAmount(mainTainDto.getPartCostAmount());
                            flow.setCostPrice(mainTainDto.getPartCostPrice());
                            flow.setCreatedBy(FrameworkUtil.getLoginInfo().getUserId().toString());
                            flow.setCreatedAt(Utility.getCurrentDateTime());
                            flow.setDealerCode(FrameworkUtil.getLoginInfo().getDealerCode());
                            Map<String, Object> queryDefaultPara = new HashMap<String, Object>();
                            String rate = "";
                            queryDefaultPara.put("itemCode", CommonConstants.DEFAULT_PARA_PART_RATE);
                            String paraList = withDrawStuffService.selectDefaultPara(queryDefaultPara);
                            if (!StringUtils.isNullOrEmpty(paraList)) {
                                double amount = Utility.add("1", paraList);
                                rate = Double.toString(amount);
                            }
                            // 不含税金额
                            flow.setInOutNetAmount(Utility.div(Double.toString(Utility.round(String.valueOf(Utility.mul(mainTainDto.getPartQuantity().toString(),
                                                                                                                        mainTainDto.getPartSalesPrice().toString())),
                                                                                             2)),
                                                               rate));
                            // 不含税价格
                            flow.setInOutNetPrice(Utility.div(Double.toString(Utility.getDouble(mainTainDto.getPartSalesPrice().toString())),
                                                              rate, 4));

                            flow.setInOutTaxedAmount(Utility.round(String.valueOf(Utility.mul(mainTainDto.getPartQuantity().toString(),
                                                                                              mainTainDto.getPartSalesPrice().toString())),
                                                                   2));
                            flow.setInOutTaxedPrice(Utility.getDouble(mainTainDto.getPartSalesPrice().toString()));
                            if (mainTainDto.getPartSalesPrice() > oldPriceTrue) {
                                flow.setInOutType(Utility.getInt(DictCodeConstants.DICT_IN_OUT_TYPE_DISPENSE_PRICE_IN_STOCK));
                                flow.setInOutTag(Utility.getInt(DictCodeConstants.DICT_IS_NO));// 入库
                            }
                            if (mainTainDto.getPartSalesPrice() < oldPriceTrue) {
                                flow.setInOutType(Utility.getInt(DictCodeConstants.DICT_IN_OUT_TYPE_DISPENSE_PRICE_OUT_STOCK));
                                flow.setInOutTag(Utility.getInt(DictCodeConstants.DICT_IS_YES));// 出库
                            }
                            flow.setStockOutQuantity(Float.valueOf(0));
                            flow.setOperateDate(new Date(System.currentTimeMillis()));
                            flow.setOperator("");// 从session中获取 待确认
                            flow.setPartBatchNo(mainTainDto.getPartBatchNo());
                            flow.setPartName(mainTainDto.getPartName());
                            flow.setPartNo(mainTainDto.getPartNo());
                            flow.setSheetNo(listAdMaintainDTO.getRoNo());
                            Map<String, Object> stckItemMap = new HashMap<String, Object>();
                            stckItemMap.put("partBatchNo", mainTainDto.getPartBatchNo());
                            stckItemMap.put("partNo", mainTainDto.getPartNo());
                            stckItemMap.put("storageCode", String.valueOf(mainTainDto.getStorageCode()));
                            stckItemMap.put("dKey", Utility.getInt(DictCodeConstants.D_KEY));
                            List<Map> partStockList = tmPartStockItemService.selectPartStockItem(stckItemMap);
                            if (null != partStockList && partStockList.size() > 0) {
                                Map partStockMap = partStockList.get(0);
                                flow.setStockQuantity(Float.parseFloat(partStockMap.get("stockQuantity").toString()));
                            }
                            flow.setStorageCode(mainTainDto.getStorageCode());
                            flow.setLicense(listAdMaintainDTO.getLicense());
                             flow.setCustomerCode(listAdMaintainDTO.getOwnerNo());//前台页面没有 待确认
                             flow.setCustomerName(listAdMaintainDTO.getOwnerName());//前台页面没有 待确认
                            partFlowService.addTtPartFlow(flow);
                        }
                    }
                    }
                }
                    //else if("D".equals(mainTainDto.getItemUpdateStatus())){
                if(!StringUtils.isNullOrEmpty(listAdMaintainDTO.getDeleteList())){
                    
                    String deleteList = listAdMaintainDTO.getDeleteList();
                    if(!StringUtils.isNullOrEmpty(deleteList)){
                        String[] deleteLists = deleteList.split(",");
                        for(int d = 0; d < deleteLists.length; d ++){
                            TtRoRepairPartPO partPo = DAOUtil.findFirstByDealer(TtRoRepairPartPO.class, " item_id = ? ", Long.valueOf(deleteLists[d]));
                            //TtRoRepairPartPO partPo = TtRoRepairPartPO.findByCompositeKeys(dealerCode,Long.valueOf(deleteLists[d]));
//                            TtRoRepairPartPO partPo = TtRoRepairPartPO.findFirst(" dealer_code = ? AND item_id = ? ", dealerCode,Long.valueOf(deleteLists[d]));
                         // 如果是删除的话
                            // 判断该工单的配件在缺料明细表中，是否已做BO，否则删除再插入。是则直接插入
                            Map<String, Object> shortPartMap = new HashMap<String, Object>();
                            shortPartMap.put("sheepNo", listAdMaintainDTO.getRoNo());
                            shortPartMap.put("partNo", partPo.getString("PART_NO"));
                            shortPartMap.put("storageCode", partPo.getString("STORAGE_CODE"));
                            shortPartMap.put("dKey", CommonConstants.D_KEY);
                            shortPartMap.put("isBo", DictCodeConstants.DICT_IS_NO);
                            if (!StringUtils.isNullOrEmpty(partPo.getString("STORAGE_POSITION_CODE")))
                                shortPartMap.put("storagePositionCode", partPo.getString("STORAGE_POSITION_CODE"));
                            List<Map> shortPartList = shortPartService.queryShortPart(shortPartMap);
                            if (null != shortPartList && shortPartList.size() > 0) {
                                Map shortMap = shortPartList.get(0);
                                shortPartService.deleteShortPartById(Utility.getLong(shortMap.get("shortId").toString()));
                                logger.debug("该配件【" + partPo.getString("PART_NO") + "】" + partPo.getString("PART_NAME")
                                                  + "没有生成BO订单，故删除");
                            }
                            // 入账的配件不允许删除
                            //List<TtRoRepairPartPO> listItem=TtRoRepairPartPO.findBySQL("select IS_FINISHED from TT_RO_REPAIR_PART where DEALER_CODE=? and ITEM_ID=? and D_KEY=?", dealerCode,mainTainDto.getItemId(),CommonConstants.D_KEY);
                            Map<String, Object> repairCon = new HashMap<String, Object>();
                            repairCon.put("itemId", partPo.getString("ITEM_ID"));
                            repairCon.put("dKey", CommonConstants.D_KEY);
                            List<Map> listItem = withDrawStuffService.selectRoRepairPart(repairCon);
                            if (listItem != null && listItem.size() > 0) {
                                Map roRepairPartPo = listItem.get(0);
                                if (!(roRepairPartPo.get("isFinished").toString().trim().equals(DictCodeConstants.DICT_IS_YES))) {
                                    if (roRepairPartPo.get("cardId") != null
                                        && Utility.getLong(roRepairPartPo.get("cardId").toString()) != 0L
                                        && (roRepairPartPo.get("activityCode") == null
                                            || "".equals(roRepairPartPo.get("activityCode")))) {
                                        this.operationMemberPartFlow(dealerCode, FrameworkUtil.getLoginInfo().getUserId(),
                                                                     FrameworkUtil.getLoginInfo().getEmployeeNo(), "2",
                                                                     roRepairPartPo, roRepairPartPo.get("roNo").toString(),
                                                                     "0");
                                    }
                                    // 删除
                                    withDrawStuffService.deleteRoRepairPart(Long.valueOf(partPo.getString("ITEM_ID")));
                                }
                            }
                        }
                    }
                }
                
                // 待确认
                // if (linked != null && linked.size() > 0)
                // {
                // actionContext.setArrayValue("TT_RO_REPAIR_PART", linked.toArray());
                // actionContext.setArrayValue("ITEM_ID", itmeIdString);
                // }
                // add by sf 2010-12-17 检查非OEM 配件出库
                List<Map> listcheck = getNonOemPartListOutReturn(dealerCode, "TT_RO_REPAIR_PART", "RO_NO",
                                                                 String.valueOf(listAdMaintainDTO.getRoNo()), null);
                StringBuilder errPart = new StringBuilder("");
                if (listcheck != null && listcheck.size() > 0) {
                    // logger.debug("list size :"+listcheck.size());
                    for (int i = 0; i < listcheck.size(); i++) {
                         Map dyna = (Map)listcheck.get(i);
                         if ("".equals(errPart)){
                         errPart.append(dyna.get("PART_NO"));
                         }else{
                         errPart.append(", ").append(dyna.get("PART_NO"));
                         }
                    }

                    // logger.debug("errPart :"+errPart);
                }
                //if (!StringUtils.isNullOrEmpty(errPart) && !"".equals(errPart)) {
                //    throw new ServiceBizException(errPart+"  非OEM配件不允许出OEM库,请重新操作!");
                //}
                withDrawStuffService.reCalcRepairAmount(listAdMaintainDTO.getRoNo(), new RepairOrderDTO());
                updateRoManage(dealerCode, String.valueOf(listAdMaintainDTO.getRoNo()),
                               FrameworkUtil.getLoginInfo().getUserId());
                updateRepairOrder(dealerCode, String.valueOf(listAdMaintainDTO.getRoNo()));
                // actionContext.setObjValue("TM_RO_REPAIR_PART_OLD", map);
                // actionContext.setArrayValue("TM_PART_STOCK_A", tmPartStockA.toArray());
            }
            // 比较该工单中的配件与缺料明细中记录的配件，如果都有，则不删除，反之则删除在工单中没有但记录了缺料明细且没做BO订单的配件
            deletePart(String.valueOf(listAdMaintainDTO.getRoNo()));
            logger.debug("删除在维修配件中不存在的但记录了缺料明细的配件");
            // end
             return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int QueryMonthPeriodIsFinished(ListAdMaintainDTO listAdMaintainDTO) {
        boolean isFinishThisMonth = isFinishedThisMonth();
        try {
            if (!isFinishThisMonth) {
                // actionContext.setErrorContext(
                // BusinessErrorConstant.MSG_ASC_PM_MONTH,
                // MessageService.getInstance().getMessage(BusinessErrorConstant.MSG_ASC_PM_MONTH),
                // new Throwable());
                throw new ServiceBizException("当前配件月报没有正确执行!");
            }
            List list2 = getIsFinished();
            Map bean = null;
            if (list2 != null && list2.size() > 0) bean = (Map) list2.get(0);
            if (list2 == null || list2.size() <= 0
                || (bean != null
                    && Utility.getInt(bean.get("IS_EXECUTED").toString()) != Utility.getInt(DictCodeConstants.DICT_IS_YES))) {
                // actionContext.setErrorContext(BusinessErrorConstant.MSG_ASC_PM_PERIOD,
                // MessageService.getInstance().getMessage(BusinessErrorConstant.MSG_ASC_PM_PERIOD),
                // new Throwable());
                throw new ServiceBizException("当前配件会计月报没有正确执行!");
            }
            return 1;
        } catch (Exception ex) {
            // actionContext.setErrorContext(CommonErrorConstant.MSG_ERROR_QUERY,
            // MessageService.getInstance().getMessage(
            // CommonErrorConstant.MSG_ERROR_QUERY), ex);
            ex.printStackTrace();
            return 0;
        }
    }

    public int AccountRepairPart(ListAdMaintainDTO listAdMaintainDTO, Map<Integer, Map> roRepairPartMap,
                                 String[] itmeIdString, String dealerCode, String[] oldPartPrice) {
        // String[] isSelect = (String[]) actionContext.getArrayValue("TT_RO_REPAIR_PART.IS_SELECTED");
        // String[] isSelect = (String[]) new String[10];//是否选中
        String Id = null;
        List<TtMaintainTableDTO> tblListDTO = listAdMaintainDTO.getMaintainPickingTbl();
        double costAmountBeforeA = 0; // 批次表入账前成本
        double costAmountBeforeB = 0; // 库存表入账前成本
        double costAmountAfterA = 0; // 批次表入账后成本
        double costAmountAfterB = 0; // 库存表入账后成本
        try {
            // 新增开关是否启用配件限价功能
            Map map = new HashMap();
            map.put("itemCode", "3432");
            String isCheckPLP = withDrawStuffService.selectDefaultPara(map);
            // 获取符合政策的浮动比例
            // 如果有关联的工单的销售配件订单，那么车系和维修类型取工单上的
            // 如果没有关联工单的配件销售单，那么只通过车辆Vin获取车系和品牌，来获取，不判断维修类型
            Map tmLSDPo1 = null;
            List<Map> tmLimitSerieslist = null;
            if (Utility.testString(String.valueOf(listAdMaintainDTO.getRoNo()))) {
                Map ttROPo = new HashMap();
                ttROPo.put("roNo", listAdMaintainDTO.getRoNo());
                ttROPo.put("dKey", CommonConstants.D_KEY);
                List<Map> ttOrderlist = withDrawStuffService.selectRepairOrder(ttROPo);
                ttROPo = (Map) ttOrderlist.get(0);
                Map tmLSDPo = new HashMap();
                tmLSDPo.put("repairTypeCode", ttROPo.get("repairTypeCode"));
                tmLSDPo.put("brandCode", ttROPo.get("brand"));
                tmLSDPo.put("seriesCode", ttROPo.get("series"));
                tmLSDPo.put("isValid", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                tmLSDPo.put("oemTag", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                tmLSDPo.put("dKey", CommonConstants.D_KEY);
                tmLimitSerieslist = limitSeriesDatainfoService.queryLimitSeriesDatainfo(tmLSDPo);
                if (tmLimitSerieslist != null && tmLimitSerieslist.size() > 0) {
                    tmLSDPo1 = (Map) tmLimitSerieslist.get(0);
                    // logger.debug("---------------有销售浮动价-------------------");
                }
            }
            /**
             * 校验当前登录用户是否有：低于成本价出库的权限
             */
            List<Map> rightsList = withDrawStuffService.checkUserRights(new HashMap());
            String rightsFlag = "";
            if (rightsList != null && rightsList.size() > 0) {
                // 该用户有低于成本价出库的权限
                rightsFlag = DictCodeConstants.DICT_IS_YES;
            }
            // 校验出库单价是否低于成本单价,已經入过帐的配件不在校验 当收费去飞为索赔时，不校验是否有权限，可以入账
            // 老系统这里是判断isSelected是否有值 待确认
            if (tblListDTO != null && tblListDTO.size() > 0) {
                // 老系统这里是根据isSelected来循环 待确认
                for (int jk = 0; jk < tblListDTO.size(); jk++) {
                    TtMaintainTableDTO tblDTO = (TtMaintainTableDTO) tblListDTO.get(jk);
                    // 这里加判断：如果是删除
                    if (!(tblDTO.getIsFinished().trim().equals(DictCodeConstants.DICT_IS_YES))) {
                        Map checkCostSizeParams = new HashMap();
                        checkCostSizeParams.put("partSalesPrice", tblDTO.getPartSalesPrice());
                        checkCostSizeParams.put("partNo", tblDTO.getPartNo());
                        checkCostSizeParams.put("storageCode", tblDTO.getStorageCode());
                        List priceList = withDrawStuffService.checkCostSize(checkCostSizeParams);
                        // 沒有入账的配件
                        if (!StringUtils.isNullOrEmpty(tblDTO.getChargePartitionCode()) && tblDTO.getChargePartitionCode().trim().equals("S")) {
                            break;
                        } else {
                            if (!(rightsFlag.trim().equals(DictCodeConstants.DICT_IS_YES))) {
                                // 沒有低于成本价出库权限
                                if (priceList != null && priceList.size() > 0) {
                                    // 沒有低于成本价出库的权限
                                    Exception e = new IllegalArgumentException();
                                    // actionContext.setErrorContext(CommonErrorConstant.MSG_ERROR_PART_NOT_LOW_COST,
                                    // MessageService.getInstance().getMessage(
                                    // CommonErrorConstant.MSG_ERROR_PART_NOT_LOW_COST, storageCode[jk],
                                    // partNo[jk]), e);
                                    return 0;
                                } else {
                                    // 有低于成本价出库权限
                                    // 低于成本价出库记录系统操作日誌
                                    if (priceList != null && priceList.size() > 0) {
                                        String contentsa = "做配件销售出库时仓库" + tblDTO.getStorageCode() + "配件"
                                                           + tblDTO.getPartNo() + "单价小于成本价";
                                        operateLogService.recordOperateLog(contentsa,
                                                                           Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE));
                                    }
                                }
                            }
                        }
                        logger.debug("参数3432控制判断,配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售限价");
                        if (Utility.testString(isCheckPLP) && DictCodeConstants.DICT_IS_YES.equals(isCheckPLP)) {
                            if (tmLSDPo1 != null) {
                                // 销售浮动比例
                                Double limitSeriesDatainfo = Double.parseDouble(tmLSDPo1.get("LIMIT_PRICE_RATE").toString());
                                Double limitPrice = 0.00;
                                // 配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售浮动价 add by ch 20160118
                                Map tpipo1 = new HashMap();
                                tpipo1.put("partNo", tblDTO.getPartNo());
                                tpipo1.put("downTag", Utility.getInt(DictCodeConstants.DICT_IS_YES));
                                tpipo1.put("dKey", CommonConstants.D_KEY);
                                List<Map> listTpi = withDrawStuffService.queryPartInfoList(tpipo1);
                                // 获取销售价格
                                if (listTpi != null && listTpi.size() > 0) {
                                    if (((Map) listTpi.get(0)).get("LIMIT_PRICE") != null
                                        && Utility.getDouble(((Map) listTpi.get(0)).get("LIMIT_PRICE").toString()) > 0.0) {
                                        limitPrice = Utility.getDouble(((Map) listTpi.get(0)).get("LIMIT_PRICE").toString());
                                        logger.debug("销售限价:" + limitPrice);
                                        logger.debug("浮动比例:" + limitSeriesDatainfo);
                                        // 销售浮动比例+1
                                        limitSeriesDatainfo = add(1.00D, limitSeriesDatainfo);
                                        // （销售浮动比例+1）*销售价格
                                        limitPrice = mul(limitPrice, limitSeriesDatainfo);
                                        logger.debug("销售浮动价:" + limitPrice);
                                        if (limitPrice > 0
                                            && Utility.getDouble(tblDTO.getPartSalesPrice().toString()) > 0) {
                                            // 如果发料价格大于销售浮动价那么报错
                                            if (Utility.getDouble(tblDTO.getPartSalesPrice().toString()) > limitPrice) {
                                                // actionContext.setErrorContext("配件(" + partNo[jk] + ")销售单价不能高于销售浮动价!",
                                                // "配件(" + partNo[jk] + ")销售单价不能高于销售浮动价!", new Exception());
                                                return 0;
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
            /**
             * 校验结束
             */
            PartFlowDTO flow = null;
            // LinkedList<DataBean> linked = new LinkedList<DataBean>();
            List<Object> linked = new ArrayList<Object>();
            if (tblListDTO != null && tblListDTO.size() > 0) {
                for (int j = 0; j < tblListDTO.size(); j++) {
                    if (DictCodeConstants.DICT_IS_YES.equals(tblListDTO.get(j).getIsSelected())) {
                        // Id = Utility.GetBillNo(entityCode, BillType.SRV_FLDH, conn);
                        Id = commonNoService.getSystemOrderNo(CommonConstants.SRV_FLDH);// call p_getbillno(?,?,?,?)
                        break;
                    }
                }
            }
            if (tblListDTO != null && tblListDTO.size() > 0) {
                for (int i = 0; i < tblListDTO.size(); i++) {
                    TtMaintainTableDTO tableDTO = tblListDTO.get(i);
                    /**
                     * 取库存批次里最新成本单价
                     */
                    double costPriceStock = 0;// 库存成本单价
                    double costAmountStock = 0;// 库存成本金额
                    float stockQuantity = 0;// 库存数量
                    float stockQuantityNew = 0;// 入帐修改后的库存数量
                    double costPriceNew = 0;// 入帐修改后的库存成本单价
                    double itemCostAmount = 0;// 业务单据领料出库成本金额
                    double itemCostPrice = 0;// 业务单句出库成本单价
                    costAmountBeforeA = 0; // 批次表入账前成本
                    costAmountBeforeB = 0; // 库存表入账前成本
                    costAmountAfterA = 0; // 批次表入账后成本
                    costAmountAfterB = 0; // 库存表入账后成本
                    map = new HashMap();
                    map.put("itemCode", CommonConstants.DEFAULT_PARA_PART_RATE);
                    String listMap = withDrawStuffService.selectDefaultPara(map);

                    double amount = Utility.add("1", listMap);
                    String rate = Double.toString(amount);

                    Map itemCon = new HashMap();
                    Map item = new HashMap();
                    itemCon.put("partBatchNo", tableDTO.getPartBatchNo());
                    itemCon.put("partNo", tableDTO.getPartNo());
                    itemCon.put("storageCode", tableDTO.getStorageCode());
                    itemCon.put("dKey", CommonConstants.D_KEY);
                    List list = tmPartStockItemService.selectPartStockItem(itemCon);

                    if (list != null && list.size() > 0) {
                        item = (Map) list.get(0);
                        if (item.get("costPrice") != null && !"".equals(item.get("costPrice").toString())) {
                            // 入帐前成本单价
                            costPriceStock = Utility.getDouble(item.get("costPrice").toString());
                            // 业务单据成本金额成本单价(领料和退料成本金额)
                            if (tableDTO.getPartQuantity() > 0) {
                                itemCostAmount = costPriceStock * tableDTO.getPartQuantity();
                                logger.debug("itemCostAmount:" + itemCostAmount); // Modifidy by sf 2011-02-21
                                itemCostAmount = Utility.round(Double.toString(itemCostAmount), 2);// 保留2位小数，四舍五入
                                logger.debug("itemCostAmount:" + itemCostAmount);
                                itemCostPrice = Utility.getDouble(item.get("costPrice").toString());
                            }
                            if (tableDTO.getPartQuantity() < 0) {
                                logger.debug("costPriceStock:" + costPriceStock);
                                itemCostAmount = tableDTO.getPartCostPrice() * tableDTO.getPartQuantity();
                                itemCostAmount = Utility.round(Double.toString(itemCostAmount), 2);// Modifidy by sf
                                                                                                   // 2011-02-21
                                logger.debug("itemCostAmount:" + itemCostAmount);
                                itemCostPrice = tableDTO.getPartCostPrice();
                            }
                        }
                        if (item.get("costAmount") != null && !"".equals(item.get("costAmount").toString())) {
                            // 入帐前库存成本金额
                            costAmountStock = Utility.getDouble(item.get("costAmount").toString());
                            costAmountBeforeA = Utility.getDouble(item.get("costAmount").toString());// 批次表入帐前成本金额
                        }

                        if (item.get("stockQuantity") != null && !"".equals(item.get("stockQuantity").toString())) {
                            // 入帐前库存中库存数量
                            stockQuantity = Float.valueOf(item.get("stockQuantity").toString());
                        }
                    }
                    Map stockPOCon = new HashMap();
                    stockPOCon.put("partNo", tableDTO.getPartNo());
                    stockPOCon.put("storageCode", tableDTO.getStorageCode());
                    stockPOCon.put("dKey", CommonConstants.D_KEY);
                    List<Map> stocknow = tmPartStockService.selectPartStock(stockPOCon);
                    if (stocknow != null && stocknow.size() > 0) {
                        Map stockPO = (Map) stocknow.get(0);
                        if (stockPO.get("costAmount") != null && !"".equals(stockPO.get("costAmount").toString())) {
                            costAmountBeforeB = Utility.getDouble(stockPO.get("costAmount").toString()); // 库存表入账前成本金额
                        }
                    }
                    if (DictCodeConstants.DICT_IS_YES.equals(tableDTO.getIsSelected())) {
                        // 更新工单维修配件明细中的成本价格和成本金额 以及入账标识
                        /**
                         * 根据配件数量判断是维修领料还是退料 领料把配件库存最新成本单价写入 退料则前台传的作为成本单价写入工单维修配件明细
                         */
                        Long itemId = null;
                        TtRoRepairPartDTO part = new TtRoRepairPartDTO();
                        part.setPART_COST_PRICE(itemCostPrice);// 成本单价
                        part.setPART_COST_AMOUNT(itemCostAmount);// 成本金额
                        if (tableDTO.getItemId() == 0) itemId = Utility.getLong(itmeIdString[i].toString());
                        else itemId = tableDTO.getItemId();
                        part.setIS_FINISHED(Utility.getInt(DictCodeConstants.DICT_IS_YES));
                        part.setSENDER(FrameworkUtil.getLoginInfo().getEmployeeNo());
                        // comg2011-07-27 待处理
                        /*
                         * if (( tblListDTO != null ) && (tableDTO sIS_DXP[i]!=null&& sIS_DXP[i]!="")) { if(
                         * (sendTime[i]==null) || (sendTime[i]=="") )//必须有值，否则报错，且TT_RO_REPAIR_PART.IS_SELECTED
                         * 必须是勾选状态（DictDataConstant.DICT_IS_YES） { actionContext.setErrorContext("入账提示",
                         * "入帐日期不能为空 ！",null); return 0; } int ICompareResult =-1;
                         * ICompareResult=BusinessUtility.TodayCompareFormatDate(sendTime[i]); if ( ICompareResult<0
                         * )//今天之后的日期,提示“不允许编写今天之后的”，彻底结束 { actionContext.setErrorContext("入账提示",
                         * "入帐日期不能是今天之后的日期！",null); return 0; } logger.debug("*********入账日期必须是当天及当天以前*********"); if (
                         * ICompareResult>0 )//不包括今天的。 //今天之前的日期，否的概念， { // comg2011-07-20 begin
                         * BusinessUtility.QueryAndInsertTsDxpAccountLog(conn,
                         * entityCode,sendTime[i]);//在日志表里查找，如果找到，则insert
                         * part.setDxpDate(Utility.getCurrentTimestamp());//业务单据明细表增加一字段，记录当时系统时间（DXP_Date＝当前时间） //
                         * comg2011-07-20 end } part.setSendTime(Utility.getTimeStamp(sendTime[i])); } else
                         */
                        part.setSEND_TIME(Utility.getCurrentDateTime());
                        part.setOUT_STOCK_NO(Id);
                        part.setBATCH_NO(Utility.getInt(tableDTO.getBatchNo()));
                        /*
                         * if ( sIS_DXP!=null) { if (sIS_DXP[i]!=null&& sIS_DXP[i]!="") { long Dxp=6970002;
                         * part.setFromType(Dxp); } }
                         */
                        // add by dyz 2010.11.15 如果有缺料的配件，则需要更新工单维修配件明细里 NON_ONE_OFF 为12781001
                        if (tblListDTO != null && DictCodeConstants.DICT_IS_YES.equals(tableDTO.getNonOneOff()))
                            part.setNON_ONE_OFF(Utility.getInt(DictCodeConstants.DICT_IS_YES));

                        if (dealerCode == null || "".equals(dealerCode) || itemId == null || "".equals(itemId)) {
                            // actionContext.setErrorContext(CommonErrorConstant.MSG_ERROR_LOST_KEY,
                            // MessageService.getInstance()
                            // .getMessage(CommonErrorConstant.MSG_ERROR_LOST_KEY), null);
                            return 0;
                        }
                        withDrawStuffService.modifyByItemId(itemId, part);
                        // DynaBean dBean = new DynaBean("part");

                        // dBean.add("RECORD_ID", recordId[i]);
                        // dBean.add("UPDATE_STATUS", "U");
                        // dBean.add("OUT_STOCK_NO", part.getOutStockNo().toString());
                        // dBean.add("SENDER", part.getSender().toString());
                        // dBean.add("BATCH_NO", part.getBatchNo().toString());
                        // dBean.add("ITEM_ID", partCon.getItemId().toString());
                        // dBean.add("IS_FINISHED", part.getIsFinished());
                        // dBean.add("SEND_TIME", Utility.getCurrentDateTime());
                        // linked.add(dBean);
                        // 更改库存和批次表中成本和数量
                        Map stockItem = new HashMap();
                        stockItem.put("stockQuantity", -tableDTO.getPartQuantity());// 本此出入库数量
                        stockItem.put("costAmount", -itemCostAmount);// 成本金额
                        stockItem.put("partNo", tableDTO.getPartNo());
                        stockItem.put("storageCode", tableDTO.getStorageCode());
                        stockItem.put("partBatchNo", tableDTO.getPartBatchNo());
                        int record = calCostPrice(dealerCode, stockItem);
                        if (record == 0) {
                            // actionContext.setErrorContext(BusinessErrorConstant.MSG_ASC_IS_NEGATIVE,
                            // MessageService.getInstance()
                            // .getMessage(BusinessErrorConstant.MSG_ASC_IS_NEGATIVE), null);
                            throw new ServiceBizException("仓库不支持负库存，请核对出入库数量！");
                        }
                        // 更改配件库存信息
                        TmPartStockDTO stockPO = new TmPartStockDTO();
                        stockPO.setLastStockOut(new Date(System.currentTimeMillis()));
                        stockPO.setSlowMovingDate(new Date(System.currentTimeMillis()));
                        stockPOCon.put("partNo", tableDTO.getPartNo());
                        stockPOCon.put("storageCode", tableDTO.getStorageCode());
                        stockPOCon.put("dKey", CommonConstants.D_KEY);

                        if (dealerCode == null || "".equals(dealerCode.trim()) || tableDTO.getPartNo() == null
                            || "".equals(tableDTO.getPartNo().trim()) || tableDTO.getStorageCode() == null
                            || "".equals(tableDTO.getStorageCode().trim())) {
                            // actionContext.setErrorContext(CommonErrorConstant.MSG_ERROR_LOST_KEY,
                            // MessageService.getInstance()
                            /// .getMessage(CommonErrorConstant.MSG_ERROR_LOST_KEY), null);
                            throw new ServiceBizException("丢失主键值！");
                            // return 0;
                        }
                        tmPartStockService.modifyByItemId(tableDTO.getPartNo(), tableDTO.getStorageCode(), stockPO);
                        List<Map> listStockAfter = tmPartStockService.selectPartStock(stockPOCon);
                        if (listStockAfter != null && listStockAfter.size() > 0) {
                            Map StockAfter = (Map) listStockAfter.get(0);
                            if (StockAfter.get("costAmount") != null
                                && !"".equals(StockAfter.get("costAmount").toString())) {
                                costAmountAfterB = Utility.getDouble(StockAfter.get("costAmount").toString()); // 库存表入账后成本金额
                            }
                        }
                        // 更改配件库存批次信息
                        Map stockItemPOCon = new HashMap();
                        TmPartStockItemDTO stockItemPO = new TmPartStockItemDTO();
                        stockItemPOCon.put("partNo", tableDTO.getPartNo());
                        stockItemPOCon.put("storageCode", tableDTO.getStorageCode());
                        stockItemPOCon.put("partBatchNo", tableDTO.getPartBatchNo());
                        stockItemPOCon.put("dKey", CommonConstants.D_KEY);
                        stockItemPO.setLastStockOut(new Date(System.currentTimeMillis()));
                        tmPartStockItemService.modifyByPrimaryId(tableDTO.getPartNo(), tableDTO.getStorageCode(),
                                                                 tableDTO.getPartBatchNo(), stockItemPO);
                        List<Map> listItemAfter = tmPartStockItemService.selectPartStockItem(stockItemPOCon);
                        if (listItemAfter != null && listItemAfter.size() > 0) {
                            Map itemAfter = (Map) listItemAfter.get(0);
                            if (itemAfter.get("costAmount") != null
                                && !"".equals(itemAfter.get("costAmount").toString())) {
                                costAmountAfterA = Utility.getDouble(itemAfter.get("costAmount").toString()); // 批次表入账后成本金额
                            }
                        }
                        // 新增一条配件流水帐
                        flow = new PartFlowDTO();
                        if (tblListDTO != null && tblListDTO.size() > 0) {
                            flow.setCostPrice(itemCostPrice);// 成本单价
                            flow.setCostAmount(itemCostAmount);// 成本金额
                            flow.setInOutNetAmount(Utility.div(Double.toString(Utility.round(String.valueOf(Utility.mul(tableDTO.getPartQuantity().toString(),
                                                                                                                        tableDTO.getPartSalesPrice().toString())),
                                                                                             2)),
                                                               rate)); // 不含税金额
                            flow.setInOutNetPrice(Utility.div(Double.toString(tableDTO.getPartSalesPrice()), rate, 4)); // 不含税价格
                            flow.setInOutTaxedAmount(Utility.round(String.valueOf(Utility.mul(tableDTO.getPartQuantity().toString(),
                                                                                              tableDTO.getPartSalesPrice().toString())),
                                                                   2));
                            flow.setInOutTaxedPrice(Utility.getDouble(tableDTO.getPartSalesPrice().toString()));
                        }
                        // flow.setCreateBy(userId);
                        // flow.setCreateDate(Utility.getCurrentDateTime());
                        flow.setLicense(listAdMaintainDTO.getLicense());
                        flow.setDealerCode(dealerCode);
                        // flow.setFlowId(POFactory.getLongPriKey(conn, flow));
                        flow.setInOutTag(Utility.getInt(DictCodeConstants.DICT_IS_YES));
                        flow.setStockOutQuantity(Utility.getFloat(tableDTO.getPartQuantity().toString()));
                        flow.setInOutType(Utility.getInt(DictCodeConstants.DICT_IN_OUT_TYPE_REPAIR_DISPENSE_PART));
                        // if ((sIS_DXP!=null)&&(sIS_DXP[i]!=null&& sIS_DXP[i]!=""))
                        // {
                        // flow.setOperateDate(Utility.getTimeStamp(sendTime[i]));
                        // }
                        // else
                        flow.setOperateDate(new Date(System.currentTimeMillis()));
                        flow.setOperator(FrameworkUtil.getLoginInfo().getEmployeeNo());
                        flow.setPartBatchNo(tableDTO.getPartBatchNo());
                        flow.setPartName(tableDTO.getPartName());
                        flow.setPartNo(tableDTO.getPartNo());
                        flow.setSheetNo(tableDTO.getRoNo());
                        flow.setStorageCode(tableDTO.getStorageCode());
                        flow.setCustomerName(listAdMaintainDTO.getOwnerName());
                        flow.setCustomerCode(listAdMaintainDTO.getOwnerNo());// ownerNo 待确认
                        flow.setRepairPartId(Utility.getLong(tableDTO.getItemId().toString()));
                        if (Utility.getLong(tableDTO.getItemId().toString()) == 0)
                            flow.setRepairPartId(Utility.getLong(itmeIdString[i]));
                        else flow.setRepairPartId(Utility.getLong(tableDTO.getItemId().toString()));
                        // 取当前配件库存
                        Map newItem = new HashMap();
                        if (listItemAfter != null && listItemAfter.size() > 0) {
                            newItem = (Map) listItemAfter.get(0);
                        } else {
                            // actionContext.setErrorContext(CommonErrorConstant.MSG_ERROR_49,
                            // MessageService.getInstance()
                            // .getMessage(CommonErrorConstant.MSG_ERROR_49), null);
                            // return 0;
                            throw new ServiceBizException("配件库存中没该配件信息!");
                        }
                        stockQuantityNew = Utility.getFloat(newItem.get("stockQuantity").toString());// 入帐后的库存数量
                        costPriceNew = Utility.getDouble(newItem.get("costPrice").toString());// 入帐后的成本单价
                        flow.setStockQuantity(stockQuantityNew);// 更改后库存数量
                        flow.setCostAmountBeforeA(costAmountBeforeA);
                        flow.setCostAmountBeforeB(costAmountBeforeB);
                        flow.setCostAmountAfterA(costAmountAfterA);
                        flow.setCostAmountAfterB(costAmountAfterB);
                        // if ((sIS_DXP!=null)&&(sIS_DXP[i]!=null&& sIS_DXP[i]!="")) {
                        // if (otherPartCostPrice != null)
                        // if (Utility.testString(otherPartCostPrice[i]))
                        // flow.setOtherPartCostPrice(Utility.getDouble(otherPartCostPrice[i]));
                        // if (otherPartCostAmount != null)
                        // if (Utility.testString(otherPartCostAmount[i]))
                        // flow.setOtherPartCostAmount(Utility.getDouble(otherPartCostAmount[i]));
                        // }

                        partFlowService.addTtPartFlow(flow);
                        // 自然月结报表
                        TtPartMonthReportDTO db = new TtPartMonthReportDTO();
                        db.setPartBatchNo(tableDTO.getPartBatchNo());
                        db.setPartNo(tableDTO.getPartNo());
                        db.setPartName(tableDTO.getPartName());
                        db.setStorageCode(tableDTO.getStorageCode());
                        db.setOutQuantity(Float.valueOf(tableDTO.getPartQuantity().toString()));
                        db.setOutAmount(itemCostAmount);
                        // 参数没加公用方法
                        db.setOpenQuantity(stockQuantity);// 入帐前库存数量
                        db.setOpenPrice(costPriceStock);// 入帐钱库存成本单价
                        db.setOpenAmount(costAmountStock);// 入帐前成本金额
                        db.setClosePrice(costPriceNew);// 入帐后成本单价
                        db.setReportYear(Utility.getYear());
                        db.setReportMonth(Utility.getMonth());
                        db.setDealerCode(dealerCode);
                        // END
                        createOrUpdate(db);

                        // 会计月报表
                        // AccountingCycleDTO cycle = new AccountingCycleDTO();
                        Map cycle = new HashMap();
                        cycle = getAccountCyclePO(dealerCode);
                        TtPartPeriodReportDTO period = new TtPartPeriodReportDTO();
                        period.setPartBatchNo(tableDTO.getPartBatchNo());
                        period.setPartNo(tableDTO.getPartNo());
                        period.setPartName(tableDTO.getPartName());
                        period.setStorageCode(tableDTO.getStorageCode());
                        period.setOutQuantity(tableDTO.getPartQuantity());
                        period.setOutAmount(Utility.round(String.valueOf(Utility.mul(tableDTO.getPartQuantity().toString(),
                                                                                     tableDTO.getPartSalesPrice().toString())),
                                                          2));
                        period.setStockOutCostAmount(itemCostAmount);
                        period.setRepairOutCostAmount(itemCostAmount);
                        period.setRepairOutCount(tableDTO.getPartQuantity());
                        period.setRepairOutSaleAmount(Utility.round(String.valueOf(Utility.mul(tableDTO.getPartQuantity().toString(),
                                                                                               tableDTO.getPartSalesPrice().toString())),
                                                                    2));
                        // 方法还未加
                        logger.debug("本次出入库数量:" + tableDTO.getPartQuantity());
                        logger.debug("本此出入库成本金额：" + itemCostAmount);
                        period.setOpenPrice(costPriceStock);// 入帐前成本单价
                        period.setOpenQuantity(Utility.getDouble(String.valueOf(stockQuantity)));// 入帐前库存数量
                        period.setOpenAmount(costAmountStock);// 入帐前成本金额
                        period.setClosePrice(costPriceNew);// 入帐后成本单价
                        // end
                        createOrUpdatePartPeriodReport(period, cycle, dealerCode);
                        // 如果是退料增加日志记录
                        // update wanghui 2010-01-28
                        insertLogParts(tableDTO.getPartNo(), tableDTO.getStorageCode(), tableDTO.getPartQuantity(),
                                       FrameworkUtil.getLoginInfo().getEmployeeNo(),
                                       FrameworkUtil.getLoginInfo().getUserId(), tableDTO.getRoNo(),
                                       listAdMaintainDTO.getLicense());
                    }
                        logger.debug("收费区分");
                        logger.debug("IS_FINISHED:" + tableDTO.getIsFinished());
                        logger.debug("ITEM_UPDATE_STATUS:" + tableDTO.getItemUpdateStatus());
                        if (("A").equals(tableDTO.getItemUpdateStatus())) {
                            if (!oldPartPrice[i].toString().equals(tableDTO.getPartSalesPrice().toString())) {
                                String content = "维修领料:工单号【" + tableDTO.getRoNo() + "】车牌号【"
                                                 + listAdMaintainDTO.getLicense() + "】仓库代码【" + tableDTO.getStorageCode()
                                                 + "】配件号【" + tableDTO.getPartNo() + "】 发料单价由【"
                                                 + oldPartPrice[i].toString() + "】" + "修改为：【"
                                                 + tableDTO.getPartSalesPrice() + "】";
                                logger.debug(content);
                                handleOperateLog(content, Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE), "");
                                logger.debug(content + ";remark:" + "" + ";operateType:" + tableDTO + ";operator:"
                                             + Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE) + ";userId:"
                                             + FrameworkUtil.getLoginInfo().getUserId() + ";");
                            }
                        }
                        // 收费区分修改
                        if (DictCodeConstants.DICT_IS_YES.equals(tableDTO.getIsFinished())
                            && ("U").equals(tableDTO.getItemUpdateStatus())) {
                            // 完成入帐的和操作标示为U的进行记差价流水帐
                            Map partPO = null;
                            for (int j = 0; j < roRepairPartMap.size(); j++) {
                                partPO = new HashMap();
                                if (!StringUtils.isNullOrEmpty(roRepairPartMap.get(j))) {
                                    partPO = roRepairPartMap.get(j);
                                    logger.debug("输出ITEM_ID:" + partPO.get("itemId"));
                                    logger.debug("ITEMIDOUT:" + tableDTO.getItemId());
                                    logger.debug("PartSalesPrice:" + tableDTO.getPartSalesPrice());
                                    logger.debug("OLD:" + partPO.get("partSalesPrice"));
                                    logger.debug("new:" + tableDTO.getPartSalesPrice());
                                    if (!StringUtils.isNullOrEmpty(partPO.get("itemId")) && Utility.getLong(partPO.get("itemId").toString()) == Utility.getLong(partPO.get("itemId").toString())) {
                                        // DYZ 2010.5.6 记录操作日志
                                        // 修改发料单价增加日志记录
                                        if (!partPO.get("partSalesPrice").toString().equals(tableDTO.getPartSalesPrice().toString())) {
                                            String content = "维修领料:工单号【" + tableDTO.getRoNo() + "】车牌号【"
                                                             + listAdMaintainDTO.getLicense() + "】仓库代码【"
                                                             + tableDTO.getStorageCode() + "】配件号【"
                                                             + tableDTO.getPartNo() + "】 发料单价由【"
                                                             + partPO.get("partSalesPrice").toString() + "】" + "修改为：【"
                                                             + tableDTO.getPartSalesPrice() + "】";
                                            logger.debug(content);

                                            handleOperateLog(content,
                                                             Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE), "");
                                        }
                                        logger.debug(costPriceNew + "DDDDDDDDDDDD");
                                        // 找到修改的ITEM_ID比较差价
                                        double oldprice = Utility.getDouble(partPO.get("partSalesPrice").toString());
                                        double chaPrice = Utility.getDouble(tableDTO.getPartSalesPrice().toString())
                                                          - oldprice;// 差价
                                        double discount = Utility.getDouble(partPO.get("discount").toString());// 折扣率旧单
                                        String flagOld = "";// 旧单是否有收费区分标示
                                        // 1:有收费区分；0：没有收费区分
                                        if (!StringUtils.isNullOrEmpty(partPO.get("chargePartitionCode"))) {
                                            flagOld = "1";
                                        } else {
                                            flagOld = "0";
                                        }
                                        logger.debug("flagOld:" + flagOld);
                                        String flagNew = "";// 新单是否有收费区分标示
                                        // 1:有收费区分；0:没有收费区分
                                        if (!StringUtils.isNullOrEmpty(tableDTO.getChargePartitionCode())) {
                                            flagNew = "1";
                                        } else {
                                            flagNew = "0";
                                        }
                                        logger.debug("flagNew:" + flagNew);
                                        logger.debug(costPriceNew + "CCCCCCCCCCCCCCCCCC");
                                        if (Utility.getDouble(tableDTO.getPartSalesPrice().toString()) != oldprice) {

                                            // 有 差价记流水账
                                            flow = new PartFlowDTO();
                                            flow.setDealerCode(dealerCode);
                                            flow.setdKey(CommonConstants.D_KEY);
                                            //flow.setCreateBy(FrameworkUtil.getLoginInfo().getUserId());
                                            //flow.setCreateDate(Utility.getCurrentDateTime());
                                            flow.setPartNo(tableDTO.getPartNo());
                                            flow.setPartName(tableDTO.getPartName());
                                            flow.setLicense(listAdMaintainDTO.getLicense());
                                            flow.setPartBatchNo(tableDTO.getPartBatchNo());
                                            //flow.setFlowId(POFactory.getLongPriKey(conn, flow));
                                            flow.setSheetNo(tableDTO.getRoNo());
                                            flow.setInOutType(Utility.getInt(DictCodeConstants.DICT_IN_OUT_TYPE_REPAIR_DISPENSE_PART));
                                            flow.setInOutTag(Utility.getInt(DictCodeConstants.DICT_IS_YES));
                                            flow.setRepairPartId(Utility.getLong(tableDTO.getItemId().toString()));
                                            flow.setCustomerCode(listAdMaintainDTO.getOwnerNo());
                                            flow.setCustomerName(listAdMaintainDTO.getOwnerName());
                                            flow.setOperator(FrameworkUtil.getLoginInfo().getEmployeeNo());
                                            //if ((sIS_DXP != null) && (sIS_DXP[i] != null && sIS_DXP[i] != "")) {
                                            //    flow.setOperateDate(Utility.getTimeStamp(sendTime[i]));
                                            //} else 
                                            flow.setOperateDate(Utility.getCurrentDateTime());
                                            flow.setStorageCode(tableDTO.getStorageCode());
                                            flow.setStockQuantity(stockQuantity);// 修改收费区分入帐库存数量不变
                                            flow.setCostPrice(costPriceStock);
                                            double chap = Utility.getDouble(tableDTO.getPartSalesPrice().toString()) - oldprice;
                                            flow.setInOutTaxedPrice(chap);// 含税单价计差价
                                            flow.setInOutTaxedAmount(chap * Utility.getFloat(tableDTO.getPartQuantity().toString()));// 含税金额
                                            flow.setInOutNetPrice(chap / Double.valueOf(rate));// 不含税单价
                                            flow.setInOutNetAmount(flow.getInOutTaxedAmount() / Double.valueOf(rate));// 不含税金额
                                            partFlowService.addTtPartFlow(flow);
                                            /**
                                             * 自然月报表修改收费区分时，出库成本金额为0,可以不计
                                             */
                                            /**
                                             * 自然月报表,结束
                                             */
                                            // 会计月报表
                                            Map accountingCycle = new HashMap();
                                            accountingCycle = getAccountCyclePO(dealerCode);
                                            TtPartPeriodReportDTO partPeriodReport = new TtPartPeriodReportDTO();
                                            partPeriodReport.setPartBatchNo(tableDTO.getPartBatchNo());
                                            partPeriodReport.setPartNo(tableDTO.getPartNo());
                                            partPeriodReport.setPartName(tableDTO.getPartName());
                                            partPeriodReport.setStorageCode(tableDTO.getStorageCode());
                                            partPeriodReport.setRepairOutSaleAmount(flow.getInOutTaxedAmount()); // 金额的差价
                                            partPeriodReport.setOutAmount(flow.getInOutTaxedAmount());
                                            partPeriodReport.setOutQuantity(Utility.getDouble("0"));// 收费区分调整，数量为0
                                            partPeriodReport.setStockOutCostAmount(Utility.getDouble("0"));
                                            partPeriodReport.setRepairOutCostAmount(Utility.getDouble("0"));
                                            partPeriodReport.setRepairOutCount(Utility.getDouble("0"));
                                            //partPeriodReport.setCreatedBy(FrameworkUtil.getLoginInfo().getUserId().toString());
                                            //partPeriodReport.setUpdateBy(FrameworkUtil.getLoginInfo().getUserId().toString());

                                            Map stockItemMap = new HashMap();
                                            stockItemMap.put("partNo", tableDTO.getPartNo());
                                            stockItemMap.put("storageCode", tableDTO.getStorageCode());
                                            stockItemMap.put("partBatchNo", tableDTO.getPartBatchNo());
                                            stockItemMap.put("dKey", CommonConstants.D_KEY);

                                            Map newItemDTO = new HashMap();
                                            List itemlist = new LinkedList();
                                            itemlist = tmPartStockItemService.selectPartStockItem(stockItemMap);
                                            newItemDTO = (Map) itemlist.get(0);
                                            costPriceNew = Utility.getDouble(newItemDTO.get("costPrice").toString());// 入帐后的成本单价

                                            partPeriodReport.setOpenPrice(costPriceStock);// 入帐前成本单价
                                            partPeriodReport.setOpenQuantity((double)stockQuantity);// 入帐前库存数量
                                            partPeriodReport.setOpenAmount(costAmountStock);// 入帐前成本金额
                                            partPeriodReport.setClosePrice(costPriceNew);// 入帐后成本单价
                                            logger.debug(costPriceNew + "aaaaaaaaaaaaaaaa");
                                            createOrUpdatePartPeriodReport(partPeriodReport, accountingCycle, dealerCode);

                                        }
                                    }
                                }
                            }
                        }
                    }
                  //add by jll 2012-07-04 退料时如果是会员活动退料，则退还会员活动
                    //if(cardId!=null && cardId.length>0 && activityCode!=null && activityCode.length>0
                    //        && partQuantity!=null && partQuantity.length>0  )
                    if(listAdMaintainDTO != null && listAdMaintainDTO.getMaintainPickingTbl().size() > 0){
                        String memBackFlag=DictCodeConstants.DICT_IS_NO;
                        for(int j = 0; j<listAdMaintainDTO.getMaintainPickingTbl().size(); j++){
                            TtMaintainTableDTO dto = tblListDTO.get(j);
                            //会员活动配件退料
                            if(Utility.getDouble(dto.getPartQuantity().toString())<0.0){
                                memBackFlag=DictCodeConstants.DICT_IS_YES;   
                                break;
                            }                   
                        }
                        if(memBackFlag.equals(DictCodeConstants.DICT_IS_YES)){
                          //获取维修配件表中本次变化的会员活动配件情况，判断正入账配件与退料入账配件记录数是否相同
                            //如果是说明会员活动是完全退料--退还会员活动，否则就是部分退料--不退还会员活动
                            List linkedList=new LinkedList(); 
                            for(int x = 0; x < tblListDTO.size(); x++){
                                TtMaintainTableDTO tableDto = tblListDTO.get(x);
                                if(x==0){
                                    linkedList.add(tableDto.getActivityCode());  
                                }                   
                                if(linkedList.indexOf(tableDto.getActivityCode())<0){
                                    linkedList.add(tableDto.getActivityCode());
                                }       
                            }
                          //获取到本次变化的会员活动编号
                            if(linkedList!=null && linkedList.size()>0){
                                //TtRoRepairPartPOFactory ppf=new TtRoRepairPartPOFactory();
                                TtMemCardActiDetailDTO detailPO=null;
                                Map activityPO=null;
                                Map cardActivityPO=null;
                                for(int y=0;y<linkedList.size();y++){
                                    TtMaintainTableDTO ttDto = tblListDTO.get(y);
                                    String memActivityCode=(String)linkedList.get(y);
                                    List<Map> comList = withDrawStuffService.queryMemActivityByCode(String.valueOf(ttDto.getRoNo()), memActivityCode, "1");
                                    List<Map> backList = withDrawStuffService.queryMemActivityByCode(String.valueOf(ttDto.getRoNo()), memActivityCode, "0");
                                    
                                    // add by zhangzhen 2013-2-22 DMS-10216
                                    if(comList!=null && comList.size()>0 && backList!=null && backList.size()>0){
                                        List sumList=new ArrayList();
                                        sumList.addAll(comList);
                                        sumList.addAll(backList);
                                        float sum=0;
                                        Map po;
                                        int size=sumList.size();
                                        for(int i=0;i<size;i++){
                                            po=(Map) sumList.get(i);
                                            sum += Utility.getFloat(po.get("PART_QUANTITY").toString());
                                        }
                                        if(sum == 0){
                                            //完全退料
                                            Map roRepairPartPO = (Map)comList.get(0);
                                            cardActivityPO=new HashMap();
                                            activityPO=new HashMap();
                                            cardActivityPO.put("cardId", roRepairPartPO.get("CARD_ID"));
                                            cardActivityPO.put("dealerCode", dealerCode);
                                            cardActivityPO.put("dKey", CommonConstants.D_KEY);
                                            cardActivityPO.put("memberActivityCode", roRepairPartPO.get("ACTIVITY_CODE"));
                                            List<Map> cardActiList = withDrawStuffService.queryMemberCardActivityByCode(cardActivityPO);
                                            if(cardActiList != null && cardActiList.size() > 0){
                                                Map activityPO2 = (Map)cardActiList.get(0);
//                                              更新会员卡活动表中的活动已用次数
                                                activityPO.put("usedTicketCount", Utility.getInt(activityPO2.get("USED_TICKET_COUNT").toString())-1);
                                                updateCardActivity(cardActivityPO, activityPO);
                                                
                                                //删除会员活动流水
                                                detailPO=new TtMemCardActiDetailDTO();
                                                detailPO.setCardId(Utility.getInt(roRepairPartPO.get("cardId").toString()));
                                                detailPO.setRoNo(roRepairPartPO.get("roNo").toString());
                                                detailPO.setMemberActivityCode(roRepairPartPO.get("activityCode").toString());
                                                detailPO.setdKey(Integer.valueOf(CommonConstants.D_KEY));
                                                //add by jll 2013-1-14
                                                handleOperateLog("删除会员活动流水", Integer.valueOf(DictCodeConstants.DICT_ASCLOG_MEMBER_MANAGE), "TT_MEM_CARD_ACTI_DETAIL,RO_NO="+roRepairPartPO.get("roNo").toString());
                                                withDrawStuffService.deleteMemberCardActivity(detailPO);
                                                logger.debug(" =====成功退还会员活动========================== ");
                                            }
                                        
                                        }else{
                                            logger.debug(" =====会员活动部分退料========================== ");
                                        }
                                    }
                                }
                            }
                        }               
                    }
                    //actionContext.setArrayValue("TT_RO_REPAIR_PART", linked.toArray());
                }
                return 1;
        } catch (Exception e) {
         // 系统 异常处理
            //actionContext.setErrorContext(CommonErrorConstants.MSG_ERROR_SAVE,

            //MessageService.getInstance().getMessage(CommonErrorConstant.MSG_ERROR_SAVE),

            //e);
            e.printStackTrace();
            return 0;
        }
    }
    
    

    /**
     * @method 配件发料价格修改进行的日志操作
     * @Author qmb
     * @param content
     * @throws Exception
     */
    public void handleOperateLog(String content, Integer operatorType, String remark) throws Exception {
        if(StringUtils.isNullOrEmpty(remark))
        operateLogService.recordOperateLog(content, operatorType);
        else
        operateLogService.recordOperateLog(content, operatorType, remark);
    }

    public void insertLogParts(String partNo, String storageCode, Double quantity, String empNo, Long userId, String roNO,
                               String license) throws Exception {
        if (quantity < -0.00001) {
            String contentsa = "维修领料退料操作:工单号【" + roNO + "】车牌号【" + license + "】仓库代码【" + storageCode + "】配件号【" + partNo
                               + "】维修领料退料数量【" + (-quantity) + "】   ";
            operateLogService.recordOperateLog(contentsa, Utility.getInt(DictCodeConstants.DICT_ASCLOG_PART_MANAGE));
        }
    }
    
    /**
     * 
    * TODO description
    * @author chenwei
    * @date 2017年4月25日
    * @return
     */
    public int updateCardActivity(Map sqlMap, Map sqlWhereMap){
        String sql = " USED_TICKET_COUNT = ? ";
        String sqlWhere = " CARD_ID = ? and DEALER_CODE = ? and D_KEY = ? and MEMBER_ACTIVITY_CODE = ?";
        List list = new ArrayList();
        list.add(sqlMap.get("usedTicketCount"));
        list.add("cardId");
        list.add("dealerCode");
        list.add("dKey");
        list.add("memberActivityCode");
       return  withDrawStuffService.modifyMemberCardActivityByParams(sql, sqlWhere, list);
    }

    /**
     * ：返回当前会期周期的PO
     * 
     * @param conn
     * @param dealerCode
     * @return
     * @throws Exception
     */
    public Map getAccountCyclePO(String dealerCode) throws Exception {
        // AccountingCycleDTO accountingCycle = new AccountingCycleDTO();
        List<Map> list = withDrawStuffService.queryAccountingCycle(new HashMap());
        if (null != list && list.size() > 0) {
            Map map = list.get(0);
            return map;
        }
        return null;
    }

    public int createOrUpdatePartPeriodReport(TtPartPeriodReportDTO db, Map account,
                                              String dealerCode) throws Exception {
        try {
            StringBuilder sbSqlUpdate = new StringBuilder();
            StringBuilder sbSqlUpdateWhere = new StringBuilder();
            StringBuilder sbSqlInsert = new StringBuilder();
            if (db != null) {
                if (db.getOpenQuantity() == null) db.setOpenQuantity(0d);
                if (db.getOpenPrice() == null) db.setOpenPrice(0d);
                if (db.getOpenAmount() == null) db.setOpenAmount(0d);
                if (db.getInQuantity() == null) db.setInQuantity(0d);
                if (db.getStockInAmount() == null) db.setStockInAmount(0d);
                if (db.getBuyInCount() == null) db.setBuyInCount(0d);
                if (db.getBuyInAmount() == null) db.setBuyInAmount(0d);
                if (db.getAllocateInAmount() == null) db.setAllocateInAmount(0d);
                if (db.getAllocateInCount() == null) db.setAllocateInCount(0d);
                if (db.getOtherInCount() == null) db.setOtherInCount(0d);
                if (db.getOtherInAmount() == null) db.setOtherInAmount(0d);
                if (db.getProfitInCount() == null) db.setProfitInCount(0d);
                if (db.getProfitInAmount() == null) db.setProfitInAmount(0d);
                if (db.getOutQuantity() == null) db.setOutQuantity(0d);
                if (db.getStockOutCostAmount() == null) db.setStockOutCostAmount(0d);
                if (db.getOutAmount() == null) db.setOutAmount(0d);
                if (db.getRepairOutCount() == null) db.setRepairOutCount(0d);
                if (db.getRepairOutCostAmount() == null) db.setRepairOutCostAmount(0d);
                if (db.getRepairOutSaleAmount() == null) db.setRepairOutSaleAmount(0d);
                if (db.getSaleOutCount() == null) db.setSaleOutCount(0d);
                if (db.getSaleOutCostAmount() == null) db.setSaleOutCostAmount(0d);
                if (db.getSaleOutSaleAmount() == null) db.setSaleOutSaleAmount(0d);
                if (db.getInnerOutCount() == null) db.setInnerOutCount(0d);
                if (db.getInnerOutCostAmount() == null) db.setInnerOutCostAmount(0d);
                if (db.getInnerOutSaleAmount() == null) db.setInnerOutSaleAmount(0d);
                if (db.getAllocateOutCount() == null) db.setAllocateOutCount(0d);
                if (db.getAllocateOutCostAmount() == null) db.setAllocateOutCostAmount(0d);
                if (db.getAllocateOutSaleAmount() == null) db.setAllocateOutSaleAmount(0d);
                if (db.getOtherOutCount() == null) db.setOtherOutCount(0d);
                if (db.getOtherOutCostAmount() == null) db.setOtherOutCostAmount(0d);
                if (db.getOtherOutSaleAmount() == null) db.setOtherOutSaleAmount(0d);
                if (db.getLossOutCount() == null) db.setLossOutCount(0d);
                if (db.getLossOutAmount() == null) db.setLossOutAmount(0d);
                if (db.getCloseQuantity() == null) db.setCloseQuantity(0d);
                if (db.getClosePrice() == null) db.setClosePrice(0d);
                if (db.getCloseAmount() == null) db.setCloseAmount(0d);
                if (db.getTransferInAmount() == null) db.setTransferInAmount(0d);
                if (db.getTransferOutCostAmount() == null) db.setTransferOutCostAmount(0d);
                if (db.getTransferInCount() == null) db.setTransferInCount(0d);
                if (db.getTransferOutCount() == null) db.setTransferOutCount(0d);
                if (db.getUpholsterOutCount() == null) {
                    db.setUpholsterOutCount(0d);
                }
                if (db.getUpholsterOutCostAmount() == null) {
                    db.setUpholsterOutCostAmount(0d);
                }
                if (db.getUpholsterOutSaleAmount() == null) {
                    db.setUpholsterOutSaleAmount(0d);
                }

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("storageCode", db.getStorageCode());
                map.put("partNo", db.getPartNo());
                map.put("reportYear", Utility.fullSpaceBuffer2(account.get("B_YEAR").toString(), 4));
                map.put("reportMonth", Utility.fullSpaceBuffer2(account.get("PERIODS").toString(), 2));
                map.put("partBatchNo", db.getPartBatchNo());
                map.put("dKey", CommonConstants.D_KEY);
                List<Map> list = withDrawStuffService.queryPartPeriodReportList(map);
                // 如果不为空，则修改
                if (null != list && list.size() > 0) {
                    sbSqlUpdate.append(" IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,");
                    sbSqlUpdate.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,");
                    sbSqlUpdate.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,");
                    sbSqlUpdate.append(" ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,");
                    sbSqlUpdate.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,");
                    sbSqlUpdate.append(" PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,");
                    sbSqlUpdate.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,");
                    sbSqlUpdate.append(" REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,");
                    sbSqlUpdate.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,");
                    sbSqlUpdate.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,");
                    sbSqlUpdate.append(" TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,");
                    sbSqlUpdate.append(" UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,");
                    sbSqlUpdate.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,");
                    sbSqlUpdate.append(" CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,");
                    sbSqlUpdate.append(" CLOSE_PRICE = ?, CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?,");
                    sbSqlUpdateWhere.append(" DEALER_CODE = ?  AND REPORT_YEAR = ? AND REPORT_MONTH = ? AND STORAGE_CODE = ? AND PART_BATCH_NO = ? AND PART_NO = ? AND D_KEY = ?");
                    sbSqlUpdateWhere.append(CommonConstants.D_KEY);
                    // STOCK_IN_COUNT,STOCK_IN_AMOUNT,BUY_IN_COUNT,BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,
                    List params = new ArrayList();
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInCount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInCount() * 100) * 0.01);

                    // ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,
                    params.add(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInCount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInAmount() * 100) * 0.01);

                    // STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);

                    // REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,
                    params.add(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCount() * 100) * 0.01);

                    params.add(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getClosePrice() * 10000) * 0.0001);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);

                    // UPDATE_BY,UPDATE_DATE,PART_PERIOD_REPORT_ID
                    params.add(dealerCode);
                    params.add(Utility.fullSpaceBuffer2(account.get("B_YEAR").toString(), 4));
                    params.add(Utility.fullSpaceBuffer2(account.get("PERIODS").toString(), 2));
                    params.add(db.getStorageCode());
                    params.add(db.getPartBatchNo());
                    params.add(db.getPartNo());
                    logger.debug(this.getClass() + "--------->psUpdate over!");
                    withDrawStuffService.modifyPartPeriodReportByParams(sbSqlUpdate.toString(),
                                                                        sbSqlUpdateWhere.toString(), params);
                    logger.debug(this.getClass() + "--------->psUpdate Execute over!");
                } else {
                    logger.debug("REPORT_YEAR:" + Utility.fullSpaceBuffer2(account.get("B_YEAR").toString(), 4));
                    logger.debug("REPORT_MONTH:" + Utility.fullSpaceBuffer2(account.get("PERIODS").toString(), 2));
                    db.setReportYear(Utility.fullSpaceBuffer2(account.get("B_YEAR").toString(), 4));
                    db.setReportMonth(Utility.fullSpaceBuffer2(account.get("PERIODS").toString(), 2));
                    db.setDealerCode(dealerCode);

                    db.setInQuantity(Math.round(db.getInQuantity() * 100) * 0.01);
                    db.setStockInAmount(Math.round(db.getStockInAmount() * 100) * 0.01);
                    db.setBuyInCount(Math.round(db.getBuyInCount() * 100) * 0.01);

                    // BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT
                    db.setBuyInAmount(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    db.setAllocateInCount(Math.round(db.getAllocateInCount() * 100) * 0.01);
                    db.setAllocateInAmount(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    db.setOtherInCount(Math.round(db.getOtherInCount() * 100) * 0.01);
                    db.setOtherInAmount(Math.round(db.getOtherInAmount() * 100) * 0.01);

                    // PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT
                    db.setProfitInCount(Math.round(db.getProfitInCount() * 100) * 0.01);
                    db.setProfitInAmount(Math.round(db.getProfitInAmount() * 100) * 0.01);
                    // psInsert.setDouble(18, Math.round(db.getSaleOutCount()*100)*0.01);
                    db.setOutQuantity(Math.round(db.getOutQuantity() * 100) * 0.01);
                    db.setStockOutCostAmount(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    db.setOutAmount(Math.round(db.getOutAmount() * 100) * 0.01);

                    // REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT,
                    // SALE_OUT_COST_AMOUNT
                    db.setRepairOutCount(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    db.setRepairOutCostAmount(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);
                    db.setRepairOutSaleAmount(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    db.setSaleOutCount(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    db.setSaleOutCostAmount(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);

                    // SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,
                    // ALLOCATE_OUT_COUNT
                    db.setSaleOutSaleAmount(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    db.setInnerOutCount(Math.round(db.getInnerOutCount() * 100) * 0.01);
                    db.setInnerOutCostAmount(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    db.setInnerOutSaleAmount(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    db.setAllocateOutCount(Math.round(db.getAllocateOutCount() * 100) * 0.01);

                    // ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT
                    db.setAllocateOutCostAmount(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    db.setAllocateOutSaleAmount(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);
                    db.setOtherOutCount(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    db.setOtherOutCostAmount(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    db.setOtherOutSaleAmount(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);

                    // LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,CREATE_BY,CREATE_DATE, ORG_CODE
                    db.setLossOutCount(Math.round(db.getLossOutCount() * 100) * 0.01);
                    db.setLossOutAmount(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    // psInsert.setLong(38, db.getCreateBy());
                    // psInsert.setDate(39, new java.sql.Date(System.currentTimeMillis()));
                    db.setTransferInCount(Math.round(db.getTransferInCount() * 100) * 0.01);
                    db.setTransferInAmount(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    db.setTransferOutCount(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    db.setTransferOutCostAmount(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    db.setOpenQuantity(Math.round(db.getOpenQuantity() * 100) * 0.01);
                    db.setOpenPrice(Math.round(db.getOpenPrice() * 10000) * 0.0001);
                    db.setOpenAmount(Math.round(db.getOpenAmount() * 100) * 0.01);

                    // UPHOLSTER_OUT_COUNT add by jll 2011-09-09
                    db.setUpholsterOutCount(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    db.setUpholsterOutCostAmount(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    db.setUpholsterOutSaleAmount(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    db.setCloseQuantity(getSubF(getAddF(Float.valueOf(db.getOpenQuantity().toString()),
                                                        Float.valueOf(db.getInQuantity().toString())),
                                                Float.valueOf(db.getOutQuantity().toString())));
                    db.setClosePrice(Math.round(db.getClosePrice() * 10000) * 0.0001);
                    db.setCloseAmount(getSubD(getAddD(db.getOpenAmount(), db.getStockInAmount()),
                                              db.getStockOutCostAmount()));
                    logger.debug(this.getClass() + "------->psInsert set over!");
                    logger.debug("db.getDealerId()--->" + dealerCode);
                    withDrawStuffService.addPartMonthReport(db);
                    logger.debug(this.getClass() + "------->psInsert Execute over!");
                }
            }
            logger.debug(this.getClass() + "createOrUpdate over!");
            createOrUpdateDaily(db, dealerCode);
            logger.debug(this.getClass() + "createOrUpdateDaily over!");
            return 0;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public int createOrUpdateDaily(TtPartPeriodReportDTO db, String entityCode) throws Exception {
        StringBuffer sbSqlUpdate = new StringBuffer();
        StringBuffer sbSqlUpdateWhere = new StringBuffer();
        StringBuffer sbSqlInsert = new StringBuffer();
        sbSqlUpdate.append(" IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,");
        sbSqlUpdate.append(" SET IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,");
        sbSqlUpdate.append(" PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" CLOSE_QUANTITY = CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY END + ?-?,");
        sbSqlUpdate.append(" CLOSE_AMOUNT = CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT END + ?-?,");

        sbSqlUpdateWhere.append("DEALER_CODE = ? AND REPORT_DATE = ? AND STORAGE_CODE = ? AND D_KEY = ").append(CommonConstants.D_KEY);

        sbSqlInsert.append(" INSERT INTO TT_PART_DAILY_REPORT (REPORT_DATE,STORAGE_CODE, DEALER_CODE, ");
        sbSqlInsert.append(" IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT, BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,");
        sbSqlInsert.append(" ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,");
        sbSqlInsert.append(" OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT,");
        sbSqlInsert.append(" REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" LOSS_OUT_COUNT, LOSS_OUT_AMOUNT, CREATE_BY, CREATE_DATE,TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT,");
        sbSqlInsert.append("  UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, ");
        sbSqlInsert.append(" CLOSE_QUANTITY,CLOSE_AMOUNT,OPEN_QUANTITY,OPEN_AMOUNT ");
        sbSqlInsert.append(" ) VALUES(?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?,?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ? ");
        sbSqlInsert.append(",(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append("(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append("(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-?,");
        sbSqlInsert.append("(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-?");
        sbSqlInsert.append(" )");

        try {
            if (db != null) {
                if (db.getOpenQuantity() == null) db.setOpenQuantity(0d);
                if (db.getOpenPrice() == null) db.setOpenPrice(0d);
                if (db.getOpenAmount() == null) db.setOpenAmount(0d);
                if (db.getInQuantity() == null) db.setInQuantity(0d);
                if (db.getStockInAmount() == null) db.setStockInAmount(0d);
                if (db.getBuyInCount() == null) db.setBuyInCount(0d);
                if (db.getBuyInAmount() == null) db.setBuyInAmount(0d);
                if (db.getAllocateInAmount() == null) db.setAllocateInAmount(0d);
                if (db.getAllocateInCount() == null) db.setAllocateInCount(0d);
                if (db.getOtherInCount() == null) db.setOtherInCount(0d);
                if (db.getOtherInAmount() == null) db.setOtherInAmount(0d);
                if (db.getProfitInCount() == null) db.setProfitInCount(0d);
                if (db.getProfitInAmount() == null) db.setProfitInAmount(0d);
                if (db.getOutQuantity() == null) db.setOutQuantity(0d);
                if (db.getStockOutCostAmount() == null) db.setStockOutCostAmount(0d);
                if (db.getOutAmount() == null) db.setOutAmount(0d);
                if (db.getRepairOutCount() == null) db.setRepairOutCount(0d);
                if (db.getRepairOutCostAmount() == null) db.setRepairOutCostAmount(0d);
                if (db.getRepairOutSaleAmount() == null) db.setRepairOutSaleAmount(0d);
                if (db.getSaleOutCount() == null) db.setSaleOutCount(0d);
                if (db.getSaleOutCostAmount() == null) db.setSaleOutCostAmount(0d);
                if (db.getSaleOutSaleAmount() == null) db.setSaleOutSaleAmount(0d);
                if (db.getInnerOutCount() == null) db.setInnerOutCount(0d);
                if (db.getInnerOutCostAmount() == null) db.setInnerOutCostAmount(0d);
                if (db.getInnerOutSaleAmount() == null) db.setInnerOutSaleAmount(0d);
                if (db.getAllocateOutCount() == null) db.setAllocateOutCount(0d);
                if (db.getAllocateOutCostAmount() == null) db.setAllocateOutCostAmount(0d);
                if (db.getAllocateOutSaleAmount() == null) db.setAllocateOutSaleAmount(0d);
                if (db.getOtherOutCount() == null) db.setOtherOutCount(0d);
                if (db.getOtherOutCostAmount() == null) db.setOtherOutCostAmount(0d);
                if (db.getOtherOutSaleAmount() == null) db.setOtherOutSaleAmount(0d);
                if (db.getLossOutCount() == null) db.setLossOutCount(0d);
                if (db.getLossOutAmount() == null) db.setLossOutAmount(0d);
                if (db.getCloseQuantity() == null) db.setCloseQuantity(0d);
                if (db.getClosePrice() == null) db.setClosePrice(0d);
                if (db.getCloseAmount() == null) db.setCloseAmount(0d);
                if (db.getTransferInAmount() == null) db.setTransferInAmount(0d);
                if (db.getTransferOutCostAmount() == null) db.setTransferOutCostAmount(0d);
                if (db.getTransferInCount() == null) db.setTransferInCount(0d);
                if (db.getTransferOutCount() == null) db.setTransferOutCount(0d);
                if (db.getUpholsterOutCount() == null) {
                    db.setUpholsterOutCount(0d);
                }
                if (db.getUpholsterOutCostAmount() == null) {
                    db.setUpholsterOutCostAmount(0d);
                }
                if (db.getUpholsterOutSaleAmount() == null) {
                    db.setUpholsterOutSaleAmount(0d);
                }
                Timestamp timestamp = Utility.getCurrentTimestamp();
                String CurDate = timestamp.toString().substring(0, 10);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("storageCode", db.getStorageCode());
                map.put("reportDate", CurDate);
                map.put("dKey", CommonConstants.D_KEY);
                List<Map> list = withDrawStuffService.queryPartDailyReportList(map);
                if (null != list && list.size() > 0) {
                    List params = new ArrayList();
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInCount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInCount() * 100) * 0.01);

                    // ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,
                    params.add(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInCount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInAmount() * 100) * 0.01);

                    // STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);

                    // REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,
                    params.add(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCount() * 100) * 0.01);

                    params.add(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    logger.debug("db.getInQuantity()" + db.getInQuantity());
                    logger.debug("db.getOutQuantity()" + db.getOutQuantity());
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);

                    // UPDATE_BY,UPDATE_DATE,PART_PERIOD_REPORT_ID
                    params.add(db.getDealerCode());
                    params.add(CurDate);
                    params.add(db.getStorageCode());
                    logger.debug(this.getClass() + "--------->psUpdate over!");
                    withDrawStuffService.modifyPartDailyReportByParams(sbSqlUpdate.toString(),
                                                                       sbSqlUpdateWhere.toString(), params);
                    logger.debug(this.getClass() + "--------->psUpdate Execute over!");
                } else {
                    List params = new ArrayList();
                    params.add(CurDate);
                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());

                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getBuyInCount() * 100) * 0.01);

                    params.add(Math.round(db.getBuyInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInCount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherInAmount() * 100) * 0.01);

                    params.add(Math.round(db.getProfitInCount() * 100) * 0.01);
                    params.add(Math.round(db.getProfitInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOutAmount() * 100) * 0.01);

                    params.add(Math.round(db.getRepairOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getRepairOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getSaleOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getSaleOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getInnerOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutCount() * 100) * 0.01);

                    params.add(Math.round(db.getAllocateOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getOtherOutSaleAmount() * 100) * 0.01);

                    params.add(Math.round(db.getLossOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getLossOutAmount() * 100) * 0.01);
                    params.add(FrameworkUtil.getLoginInfo().getUserId());
                    params.add(new java.sql.Date(System.currentTimeMillis()));
                    params.add(Math.round(db.getTransferInCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferInAmount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getTransferOutCostAmount() * 100) * 0.01);

                    params.add(Math.round(db.getUpholsterOutCount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getUpholsterOutSaleAmount() * 100) * 0.01);

                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
                    params.add(Math.round(db.getInQuantity() * 100) * 0.01);

                    params.add(db.getStorageCode());
                    params.add(db.getDealerCode());
                    params.add(Math.round(db.getStockOutCostAmount() * 100) * 0.01);
                    params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
                    logger.debug(this.getClass() + "------->psInsert set over!");
                    logger.debug("db.getDealerCode()--->" + db.getDealerCode());
                    withDrawStuffService.addPartDailyReport(sbSqlInsert.toString(), params);
                    logger.debug(this.getClass() + "------->psInsert Execute over!");
                }
            }
            logger.debug(this.getClass() + "createOrUpdate over!");
            logger.debug(this.getClass() + "createOrUpdateDaily over!");
            return 0;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    private int createOrUpdate(TtPartMonthReportDTO db) {
        try {
            List<Map> list = partMonthReportService.selectPartMonthReport(db);
            if (db != null) {
                if (db.getInQuantity() == null) db.setInQuantity(0f);
                if (db.getStockInAmount() == null) db.setStockInAmount(0d);
                if (db.getInventoryQuantity() == null) db.setInventoryQuantity(0f);
                if (db.getInventoryAmount() == null) db.setInventoryAmount(0d);
                if (db.getOutQuantity() == null) db.setOutQuantity(0f);
                if (db.getOutAmount() == null) db.setOutAmount(0d);
                if (db.getOpenAmount() == null) db.setOpenAmount(0d);
                if (db.getOpenPrice() == null) db.setOpenPrice(0d);
                if (db.getOpenQuantity() == null) db.setOpenQuantity(0f);
                if (db.getCloseAmount() == null) db.setCloseAmount(0d);
                if (db.getClosePrice() == null) db.setClosePrice(0d);
                if (db.getCloseQuantity() == null) db.setCloseQuantity(0f);
            }
            List params = new ArrayList();
            params.add(Math.round(db.getInQuantity() * 100) * 0.01);
            params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
            params.add(Math.round(db.getInventoryQuantity() * 100) * 0.01);
            params.add(Math.round(db.getInventoryAmount() * 100) * 0.01);
            params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
            params.add(Math.round(db.getOutAmount() * 100) * 0.01);
            params.add(Math.round(db.getInQuantity() * 100) * 0.01);
            params.add(Math.round(db.getOutQuantity() * 100) * 0.01);
            params.add(Math.round(db.getClosePrice() * 10000) * 0.0001);
            params.add(Math.round(db.getStockInAmount() * 100) * 0.01);
            params.add(Math.round(db.getOutAmount() * 100) * 0.01);
            params.add(db.getDealerCode());
            params.add(db.getReportYear());
            params.add(db.getReportMonth());
            params.add(db.getStorageCode());
            params.add(db.getPartBatchNo());
            params.add(db.getPartNo());
            params.add(CommonConstants.D_KEY);
            logger.debug("db.getClosePrice():-------------------------------" + db.getClosePrice());
            logger.debug("Math.rounddb.getClosePrice():-------------------------------"
                         + Math.round(db.getClosePrice() * 10000) * 0.0001);
            // 如果存在就更新
            if (list != null && list.size() > 0) {
                StringBuilder sqlStr = new StringBuilder(" IN_QUANTITY = CASE  WHEN IN_QUANTITY IS NULL THEN 0  ELSE IN_QUANTITY END + ?,");
                sqlStr.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT  END + ?,");
                sqlStr.append(" INVENTORY_QUANTITY = CASE  WHEN INVENTORY_QUANTITY IS NULL  THEN 0 ELSE INVENTORY_QUANTITY END + ?,");
                sqlStr.append(" INVENTORY_AMOUNT = CASE WHEN INVENTORY_AMOUNT IS NULL THEN 0 ELSE INVENTORY_AMOUNT END + ?,");
                sqlStr.append(" OUT_QUANTITY =  CASE  WHEN OUT_QUANTITY IS NULL  THEN 0  ELSE OUT_QUANTITY END + ?,");
                sqlStr.append(" OUT_AMOUNT =  CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT  END + ?,");
                sqlStr.append(" CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,");
                sqlStr.append(" CLOSE_PRICE = ?, CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?");
                StringBuilder sqlWhere = new StringBuilder();
                sqlWhere.append(" DEALER_CODE = ? ");
                sqlWhere.append(" AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?");
                sqlWhere.append(" AND STORAGE_CODE = ?" + " AND PART_BATCH_NO = ?");
                sqlWhere.append(" AND PART_NO = ?" + " AND D_KEY = ? ");
                partMonthReportService.updateModel(sqlStr.toString(), sqlWhere.toString(), params);
            } else {
                // 如果不存在，就新增
                db.setReportYear(db.getReportYear());
                db.setReportMonth(db.getReportMonth());
                db.setInQuantity((float) (Math.round(db.getInQuantity() * 100) * 0.01));
                db.setStockInAmount(Math.round(db.getStockInAmount() * 100) * 0.01);
                db.setInventoryQuantity((float) (Math.round(db.getInventoryQuantity() * 100) * 0.01));
                db.setInventoryAmount(Math.round(db.getInventoryAmount() * 100) * 0.01);
                db.setOutQuantity((float) (Math.round(db.getOutQuantity() * 100) * 0.01));
                db.setOutAmount(Math.round(db.getOutAmount() * 100) * 0.01);
                db.setOpenQuantity((float) (Math.round(db.getOpenQuantity() * 100) * 0.01));
                db.setOpenPrice(Math.round(db.getOpenPrice() * 10000) * 0.0001);
                db.setOpenAmount(Math.round(db.getOpenAmount() * 100) * 0.01);
                db.setCloseQuantity((Float.valueOf(getSubF(getAddF(db.getOpenQuantity(), db.getInQuantity()),
                                                           db.getOutQuantity()).toString())));
                // psCreate.setFloat(19, db.getCloseQuantity()+db.getInQuantity()-db.getOutQuantity());
                db.setClosePrice(Math.round(db.getClosePrice() * 10000) * 0.0001);
                db.setCloseAmount(getSubD(getAddD(db.getOpenAmount(), db.getStockInAmount()), db.getOutAmount()));
                partMonthReportService.addPartMonthReport(db);
            }
            return -1;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return -1;
    }

    /**
     * 查询配件自然月月结记录表 查询本月的报表是否完成
     * 
     * @param actionName
     * @param Connection
     * @return true 已完成 false 未完成
     * @throws SQLException
     */
    public boolean isFinishedThisMonth() {
        Map map = new HashMap();
        map.put("REPORT_YEAR", Utility.getYear());
        map.put("REPORT_MONTH", Utility.getMonth());
        List<Map> list = withDrawStuffService.queryMonthCycle(map);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * add by obc 删除在工单中没有，但却记录了缺料明细的配件
     */
    public void deletePart(String sheetNo) throws Exception {
        Map map = new HashMap();
        map.put("sheetNo", sheetNo);
        withDrawStuffService.deleteShortPart(map);
    }

    /**
     * 工单辅料管理费 TODO description
     * 1 删除 工单辅料管理费表记录
     * 2 更新 会员配件项目流水表的修改人修改时间
     * 3 工单维修项目明细 工单辅料管理费 工单附加项目明细
     * @author chenwei
     * @date 2017年4月19日
     * @param dealerCode
     * @param roNO
     * @param userId
     * @throws Exception
     */
    public void updateRoManage(String dealerCode, String roNO, long userId) throws Exception {
        Map manage = new HashMap();
        manage.put("roNo", roNO);
        manage.put("dealerCode", dealerCode);
        manage.put("dKey", CommonConstants.D_KEY);
        Map manageYes = new HashMap();
        Map manageNo = new HashMap();

        manageYes = manage;
        manageYes.put("isManaging", Utility.getInt(DictCodeConstants.DICT_IS_YES));
        List<Map> manageYesList = withDrawStuffService.queryManage(manageYes);

        manageNo = manage;
        manageNo.put("isManaging", Utility.getInt(DictCodeConstants.DICT_IS_NO));
        List<Map> manageNoList = withDrawStuffService.queryManage(manageNo);

        Map<String, Map> manageYesMap = new HashMap<String, Map>();
        Map<String, Map> manageNoMap = new HashMap<String, Map>();
        if (manageYesList != null) {
            for (int i = 0; i < manageYesList.size(); i++) {
                manageYes = (Map) manageYesList.get(i);
                manageYesMap.put(manageYes.get("MANAGE_SORT_CODE").toString(), manageYes);
            }
        }
        if (manageNoList != null) {
            for (int i = 0; i < manageNoList.size(); i++) {
                manageNo = (Map) manageNoList.get(i);
                manageNoMap.put(manageNo.get("MANAGE_SORT_CODE").toString(), manageNo);
            }
        }
        manage.put("isManaging", null);
        withDrawStuffService.deleteTtRoManage(manage);
        // 修改主表update相关信息 2012-11-16
        List updateParam = new ArrayList();
        updateParam.add(userId);
        updateParam.add(Utility.getCurrentDateTime());
        updateParam.add(roNO);
        updateParam.add(CommonConstants.D_KEY);
        updateParam.add(dealerCode);
        withDrawStuffService.modifyRepairOrderByParams(updateParam);
        System.out.println(manageYesMap);
        System.out.println(manageNoMap);

        insertOrUpdateRoManage(dealerCode, roNO, userId, manageYesMap, manageNoMap);
    }

    /**
     * 修改维修工单 TODO description
     * 
     * @author chenwei
     * @date 2017年4月20日
     * @param dealerCode
     * @param roNO
     * @throws Exception
     */
    public void updateRepairOrder(String dealerCode, String roNO) throws Exception {
        StringBuilder sql = new StringBuilder(" OVER_ITEM_AMOUNT = ");
        sql.append("COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='").append(roNO).append("'),0)");
        sql.append(",REPAIR_AMOUNT = COALESCE(REPAIR_PART_AMOUNT,0)+COALESCE(ADD_ITEM_AMOUNT,0)+COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='").append(roNO).append("'),0)");
        sql.append("+COALESCE(LABOUR_AMOUNT,0)+COALESCE(SALES_PART_AMOUNT,0)");
        sql.append(",ESTIMATE_AMOUNT = COALESCE(REPAIR_PART_AMOUNT,0)+COALESCE(ADD_ITEM_AMOUNT,0)+COALESCE((SELECT SUM(OVER_ITEM_AMOUNT) FROM TT_RO_MANAGE WHERE RO_NO='").append(roNO).append("'),0)");
        sql.append("+COALESCE(LABOUR_AMOUNT,0)+COALESCE(SALES_PART_AMOUNT,0) ");
        StringBuilder whereSql = new StringBuilder(" RO_NO='").append(roNO).append("' AND DEALER_CODE='");
        whereSql.append(dealerCode).append("'").append(" AND  D_KEY=").append(CommonConstants.D_KEY).append(" ");
        withDrawStuffService.updateRepairOrder(sql.toString(), whereSql.toString());
    }

    private static double getMul(Double v1, Float v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 == null) s1 = "0.00";
        else s1 = v1.toString();
        if (v2 == null) s2 = "0.00";
        else s2 = v2.toString();
        return Utility.round(new Double(Utility.mul(s1, s2)).toString(), 2);
    }

    private double getAdd(Double v1, double v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 == null) s1 = "0.00";
        else s1 = v1.toString();

        s2 = new Double(v2).toString();
        return Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
    }

    /**
     * 新增或者刪除工单辅料管理费 TODO description
     * 
     * @author chenwei
     * @date 2017年4月19日
     * @param dealerCode
     * @param roNO
     * @param userId
     * @param manageYesMap
     * @param manageNoMap
     * @throws Exception
     */
    private void insertOrUpdateRoManage(String dealerCode, String roNO, long userId, Map<String, Map> manageYesMap,
                                        Map<String, Map> manageNoMap) throws Exception {
        RoManageDTO manage = null;
        Map<String, Double> manageMap = new HashMap<String, Double>();
        Map labour = new HashMap();
        labour.put("roNo", roNO);
        labour.put("dKey", CommonConstants.D_KEY);
        labour.put("needlessRepair", Utility.getInt(DictCodeConstants.DICT_IS_NO));
        Map addItem = new HashMap();
        addItem.put("roNo", roNO);
        addItem.put("dKey", CommonConstants.D_KEY);
        Map repairPart = new HashMap();
        repairPart.put("roNo", roNO);
        repairPart.put("dKey", CommonConstants.D_KEY);
        repairPart.put("needlessRepair", Utility.getInt(DictCodeConstants.DICT_IS_NO));

        List<Map> labourList = withDrawStuffService.findRoLabourList(labour);
        List<Map> addItemList = withDrawStuffService.findRoAddItem(addItem);
        List<Map> repairPartList = withDrawStuffService.selectRoRepairPart(repairPart);
        for (int i = 0; i < labourList.size(); i++) {
            labour = (Map) labourList.get(i);
            Map manageTypePO = null;
            if (!StringUtils.isNullOrEmpty(labour.get("MANAGE_SORT_CODE"))) {
                manageTypePO = manageYesMap.get(labour.get("MANAGE_SORT_CODE"));
            }
            if (manageTypePO != null) {

                if (manageMap.get(manageTypePO.get("MANAGE_SORT_CODE")) == null)
                    manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                  getMul(Utility.getDouble(labour.get("LABOUR_AMOUNT").toString()),
                                         Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString())));
                else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                   manageMap.get(manageTypePO.get("MANAGE_SORT_CODE"))
                                                                                    + getMul(Utility.getDouble(labour.get("LABOUR_AMOUNT").toString()),
                                                                                             Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString())));
            }
        }
        for (int i = 0; i < addItemList.size(); i++) {
            addItem = (Map) addItemList.get(i);
            Map manageTypePO = null;
            if (!StringUtils.isNullOrEmpty(addItem.get("MANAGE_SORT_CODE"))) {
                manageTypePO = manageYesMap.get(addItem.get("MANAGE_SORT_CODE"));
            }
            if (manageTypePO != null) {
                if (manageMap.get(manageTypePO.get("MANAGE_SORT_CODE")) == null)
                    manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                  getMul(Utility.getDouble(addItem.get("ADD_ITEM_AMOUNT").toString()),
                                         Float.valueOf(manageTypePO.get("ADD_ITEM_RATE").toString())));
                else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                   manageMap.get(manageTypePO.get("MANAGE_SORT_CODE"))
                                                                                    + getMul(Utility.getDouble(addItem.get("ADD_ITEM_AMOUNT").toString()),
                                                                                             Float.valueOf(manageTypePO.get("ADD_ITEM_RATE").toString())));
            }
        }
        for (int i = 0; i < repairPartList.size(); i++) {
            repairPart = (Map) repairPartList.get(i);
            Map manageTypePO = null;
            if (!StringUtils.isNullOrEmpty(repairPart.get("manageSortCode"))) {
                manageTypePO = manageYesMap.get(repairPart.get("manageSortCode"));
            }
            if (manageTypePO != null) {
                if (manageMap.get(manageTypePO.get("MANAGE_SORT_CODE")) == null)
                    manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                  getMul(Utility.getDouble(repairPart.get("partSalesAmount").toString()),
                                         Float.valueOf(manageTypePO.get("REPAIR_PART_RATE").toString())));
                else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                   manageMap.get(manageTypePO.get("MANAGE_SORT_CODE"))
                                                                                    + getMul(Utility.getDouble(repairPart.get("partSalesAmount").toString()),
                                                                                             Float.valueOf(manageTypePO.get("REPAIR_PART_RATE").toString())));
            }
        }

        for (Iterator iter = manageMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            manage = getManage(roNO, (String) entry.getKey(), Utility.getInt(DictCodeConstants.DICT_IS_YES));
            Map manageTypePO = getManageOld((String) entry.getKey(), manageYesMap);
            if (manage == null) {
                manage = new RoManageDTO();
                // manage.setItemId(POFactory.getLongPriKey(conn, manage));
                manage.setIsManaging(Utility.getInt(DictCodeConstants.DICT_IS_YES));
                manage.setManageSortCode((String) entry.getKey());
                manage.setOverItemAmount((Double) entry.getValue());
                manage.setLabourAmountRate(Utility.getDouble(manageTypePO.get("LABOUR_AMOUNT_RATE").toString()));
                manage.setAddItemRate(Utility.getDouble(manageTypePO.get("ADD_ITEM_RATE").toString()));
                manage.setLabourRate(Utility.getDouble(manageTypePO.get("LABOUR_RATE").toString()));
                manage.setRepairPartRate(Utility.getDouble(manageTypePO.get("REPAIR_PART_RATE").toString()));
                manage.setSalesPartRate(Utility.getDouble(manageTypePO.get("SALES_PART_RATE").toString()));
                manage.setOverheadExpensesRate(Utility.getDouble(manageTypePO.get("OVERHEAD_EXPENSES_RATE").toString()));
                manage.setDealerCode(dealerCode);
                manage.setRoNo(roNO);
                withDrawStuffService.addTtRoManage(manage);
            }
        }
        Map<String, Double> manageTemp = manageMap;
        manageMap = new HashMap<String, Double>();

        for (int i = 0; i < labourList.size(); i++) {
            labour = (Map) labourList.get(i);
            if(!StringUtils.isNullOrEmpty(labour) && !StringUtils.isNullOrEmpty(labour.get("MANAGE_SORT_CODE"))){
                Map manageTypePO = getManageOld(labour.get("MANAGE_SORT_CODE").toString(), manageNoMap);
                if (manageTypePO != null) {

                    if (StringUtils.isNullOrEmpty(manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString())))
                        manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                      getMul(Utility.getDouble(labour.get("LABOUR_AMOUNT").toString()),
                                             Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString())) + getMul(Utility.getDouble(labour.get("STD_LABOUR_HOUR").toString()),
                                                                                                                        Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString())));
                    else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                       manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString())
                                                                                        + getMul(Utility.getDouble(labour.get("LABOUR_AMOUNT").toString()),
                                                                                                 Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString()))
                                                                                        + getMul(Utility.getDouble(labour.get("STD_LABOUR_HOUR").toString()),
                                                                                                 Float.valueOf(manageTypePO.get("LABOUR_AMOUNT_RATE").toString())));
                }
            }
        }

        for (int i = 0; i < addItemList.size(); i++) {
            addItem = (Map) addItemList.get(i);
            if(!StringUtils.isNullOrEmpty(addItem) && !StringUtils.isNullOrEmpty(addItem.get("MANAGE_SORT_CODE"))){
                Map manageTypePO = getManageOld(addItem.get("MANAGE_SORT_CODE").toString(), manageNoMap);
                if (manageTypePO != null) {
                    if (manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString()) == null)
                        manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                      getMul(Utility.getDouble(addItem.get("ADD_ITEM_AMOUNT").toString()),
                                             Float.valueOf(manageTypePO.get("ADD_ITEM_RATE").toString())));
                    else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                       manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString())
                                                                                        + getMul(Utility.getDouble(addItem.get("ADD_ITEM_AMOUNT").toString()),
                                                                                                 Float.valueOf(manageTypePO.get("ADD_ITEM_RATE").toString())));
                }
            }
        }
        for (int i = 0; i < repairPartList.size(); i++) {
            repairPart = (Map) repairPartList.get(i);
            if(!StringUtils.isNullOrEmpty(repairPart) && !StringUtils.isNullOrEmpty(repairPart.get("manageSortCode"))){
                Map manageTypePO = getManageOld(repairPart.get("manageSortCode").toString(), manageNoMap);
                if (manageTypePO != null) {
                    if (manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString()) == null)
                        manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                      getMul(Utility.getDouble(repairPart.get("partSalesAmount").toString()),
                                             Float.valueOf(manageTypePO.get("REPAIR_PART_RATE").toString())));
                    else manageMap.put(manageTypePO.get("MANAGE_SORT_CODE").toString(),
                                       manageMap.get(manageTypePO.get("MANAGE_SORT_CODE").toString())
                                                                                        + getMul(Utility.getDouble(repairPart.get("partSalesAmount").toString()),
                                                                                                 Float.valueOf(manageTypePO.get("REPAIR_PART_RATE").toString())));
                }
            }
        }

        for (Iterator iter = manageMap.entrySet().iterator(); iter.hasNext();) {
            Map.Entry entry = (Map.Entry) iter.next();
            manage = getManage(roNO, (String) entry.getKey(), Utility.getInt(DictCodeConstants.DICT_IS_NO));
            Map manageTypePO = getManageOld((String) entry.getKey(), manageNoMap);
            if (manage == null) {
                manage = new RoManageDTO();
                // manage.setItemId(POFactory.getLongPriKey(conn, manage));
                manage.setIsManaging(Utility.getInt(DictCodeConstants.DICT_IS_NO));
                manage.setManageSortCode((String) entry.getKey());
                manage.setOverItemAmount(getAdd((Double) entry.getValue(),
                                                getMul(manageTemp.get((String) entry.getKey()),
                                                       Float.valueOf(manageTypePO.get("OVERHEAD_EXPENSES_RATE").toString()))));
                manage.setLabourAmountRate(Utility.getDouble(manageTypePO.get("LABOUR_AMOUNT_RATE").toString()));
                manage.setAddItemRate(Utility.getDouble(manageTypePO.get("ADD_ITEM_RATE").toString()));
                manage.setLabourRate(Utility.getDouble(manageTypePO.get("LABOUR_RATE").toString()));
                manage.setRepairPartRate(Utility.getDouble(manageTypePO.get("REPAIR_PART_RATE").toString()));
                manage.setSalesPartRate(Utility.getDouble(manageTypePO.get("SALES_PART_RATE").toString()));
                manage.setOverheadExpensesRate(Utility.getDouble(manageTypePO.get("OVERHEAD_EXPENSES_RATE").toString()));
                manage.setDealerCode(dealerCode);
                manage.setRoNo(roNO);
                withDrawStuffService.addTtRoManage(manage);
            }
        }
    }

    private Map getManageOld(String manageSortCode, Map<String, Map> manageMap) {
        if (StringUtils.isNullOrEmpty(manageSortCode)) return null;
        Map po = null;
        po = manageMap.get(manageSortCode);
        return po;
    }

    private RoManageDTO getManage(String roNO, String manageSortCode, int isManaging) {
        if (manageSortCode == null || roNO == null) return null;
        Map po = new HashMap();
        po.put("manageSortCode", manageSortCode);
        po.put("isManaging", isManaging);
        po.put("roNO", roNO);
        po.put("dKey", CommonConstants.D_KEY);
        List<Map> mapList = withDrawStuffService.queryManage(po);
        if (mapList != null && mapList.size() > 0) {
            Map map = mapList.get(0);
            RoManageDTO manage = new RoManageDTO();
            manage.setIsManaging(Integer.valueOf(map.get("IS_MANAGING").toString()));
            manage.setManageSortCode(map.get("MANAGE_SORT_CODE").toString());
            manage.setOverItemAmount(Utility.getDouble(map.get("OVER_ITEM_AMOUNT").toString()));
            manage.setLabourAmountRate(Utility.getDouble(map.get("LABOUR_AMOUNT_RATE").toString()));
            manage.setAddItemRate(Utility.getDouble(map.get("ADD_ITEM_RATE").toString()));
            manage.setLabourRate(Utility.getDouble(map.get("LABOUR_RATE").toString()));
            manage.setRepairPartRate(Utility.getDouble(map.get("REPAIR_PART_RATE").toString()));
            manage.setSalesPartRate(Utility.getDouble(map.get("SALES_PART_RATE").toString()));
            manage.setOverheadExpensesRate(Utility.getDouble(map.get("OVERHEAD_EXPENSES_RATE").toString()));
            manage.setDealerCode(map.get("DEALER_CODE").toString());
            manage.setRoNo(roNO);
            return manage;
        }

        else return null;
    }

    /**
     * 功能描述：带配件退料判断条件 主要给维修领料使用
     * 
     * @param conn
     * @param entityCode
     * @param sheetTable
     * @param sheetName
     * @param sheetNo
     * @param quantityFieldName
     * @return
     * @throws Exception
     */
    public List<Map> getNonOemPartListOutReturn(String dealerCode, String sheetTable, String sheetName, String sheetNo,
                                                String quantityFieldName) throws Exception {
        Map params = new HashMap();
        params.put("itemCode", CommonConstants.DEFAULT_PARA_OEM_PART_IN_CHECK);
        String defaultValue = withDrawStuffService.selectDefaultPara(params);
        if (!DictCodeConstants.DICT_IS_YES.equals(defaultValue)) {
            return null;
        }
        String fieldName = "PART_QUANTITY";
        if (quantityFieldName != null && "".equals(quantityFieldName)) {
            fieldName = quantityFieldName;
        }
        List<Map> list = withDrawStuffService.getNonOemPartListOutReturn(dealerCode, sheetTable, sheetName, sheetNo,
                                                                         quantityFieldName);
        return list;
    }

    /**
     * 功能描述：操作会员配件及配件流水 Method operationMemberPartFlow
     * 
     * @param con
     * @param entityCode
     * @param userId
     * @param userNo
     * @param flag
     * @param roRepairPart
     * @param flag2 是否作废标识 1 代表作废 0为其它（包括部分会员配件） 用于区分作废和配件不入账
     * @throws Exception
     */
    public void operationMemberPartFlow(String dealerCode, Long userId, String userNo, String flag, Map roRepairPart,
                                        String roNo, String flag2) throws Exception {
        if (!StringUtils.isNullOrEmpty(roRepairPart.get("partNo"))) {
            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put("cardId", roRepairPart.get("cardId"));
            queryParams.put("partNo", roRepairPart.get("partNo"));
            queryParams.put("dKey", CommonConstants.D_KEY);
            List<Map> list = memberPartService.selectTtMemberPart(queryParams);
            if (null != list && list.size() > 0) {
                // 修改会员配件使用次数
                Map map = list.get(0);
                TtMemberPartDTO partDTO = new TtMemberPartDTO();
                Float usedPartCount = 0F;
                // 删除退回标识（用于会员删除时回退）
                if (!"".equals(flag)) {
                    usedPartCount = Float.valueOf(map.get("usedPartCount").toString())
                                    - Float.valueOf(roRepairPart.get("partQuantity").toString());
                } else {
                    usedPartCount = Float.valueOf(map.get("usedPartCount").toString())
                                    + Float.valueOf(roRepairPart.get("partQuantity").toString());
                }
                partDTO.setUsedPartCount(usedPartCount);
                partDTO.setUpdatedBy(userId.toString());
                partDTO.setUpdatedAt(Utility.getCurrentDateTime());
                partDTO.setItemId(Long.valueOf(map.get("itemId").toString()));
                memberPartService.modifyByItemId(map.get("itemId").toString(), partDTO);

                Map<String, Object> flowMap = new HashMap<String, Object>();
                flowMap.put("cardId", roRepairPart.get("cardId"));
                flowMap.put("partNo", roRepairPart.get("partNo"));
                flowMap.put("roNo", roRepairPart.get("roNo"));
                flowMap.put("dKey", CommonConstants.D_KEY);

                // 进行工单作废操作时，需要删除流水
                if ("2".equals(flag) && "0".equals(flag2)) {
                    // 删除会员卡配件流水 待添加日志表操作
                    memberPartFlowService.deleteTtMemberPartFlow(flowMap);
                } else if ("2".equals(flag) && "1".equals(flag2)) {
                    flowMap = new HashMap<String, Object>();
                    flowMap.put("cardId", roRepairPart.get("cardId"));
                    flowMap.put("roNo", roRepairPart.get("roNo"));
                    flowMap.put("dKey", CommonConstants.D_KEY);
                    // 待添加日志表操作
                    memberPartFlowService.deleteTtMemberPartFlow(flowMap);
                } else {
                    List<Map> flowList = memberPartFlowService.selectTtMemberPartFlow(flowMap);
                    if (flowList != null && flowList.size() > 0 && "".equals(flag)) {
                        Map memberFlowMap = flowList.get(0);
                        TtMemberPartFlowDTO flowDto = new TtMemberPartFlowDTO();
                        flowDto.setThisUseQuantity(Float.valueOf(memberFlowMap.get("thisUseQuantity").toString())
                                                   + Float.valueOf(roRepairPart.get("partQuantity").toString()));
                        flowDto.setUsedPartQuantity(usedPartCount);
                        flowDto.setUpdatedBy(userId.toString());
                        flowDto.setUpdatedAt(Utility.getCurrentDateTime());
                        List modifyList = new ArrayList();
                        modifyList.add(Float.valueOf(memberFlowMap.get("thisUseQuantity").toString())
                                       + Float.valueOf(roRepairPart.get("partQuantity").toString()));
                        modifyList.add(usedPartCount);
                        modifyList.add(userId.toString());
                        modifyList.add(Utility.getCurrentDateTime());
                        modifyList.add(roRepairPart.get("cardId"));
                        modifyList.add(roRepairPart.get("roNo"));
                        modifyList.add(CommonConstants.D_KEY);
                        modifyList.add(dealerCode);
                        memberPartFlowService.modifyMemberPartFlowByParams(modifyList);
                    } else {
                        TtMemberPartFlowDTO flowDto = new TtMemberPartFlowDTO();
                        // flowDto.setItemId(itemId);//确认后 是改为自增长
                        flowDto.setDealerCode(dealerCode);
                        flowDto.setdKey(CommonConstants.D_KEY);
                        flowDto.setCreatedBy(userId.toString());
                        flowDto.setCreatedAt(Utility.getCurrentDateTime());
                        flowDto.setRoNo(roRepairPart.get("roNo").toString());
                        flowDto.setCardId(Utility.getInt(roRepairPart.get("cardId").toString()));
                        flowDto.setStorageCode(roRepairPart.get("storageCode").toString());
                        flowDto.setPartNo(roRepairPart.get("partNo").toString());
                        flowDto.setPartName(roRepairPart.get("partName").toString());
                        flowDto.setUsedPartQuantity(usedPartCount);
                        flowDto.setPartQuantity(Utility.getDouble(map.get("partQuantity").toString()));
                        flowDto.setChargePartitionCode(roRepairPart.get("chargePartitionCode").toString());
                        flowDto.setUnitName(roRepairPart.get("unitName").toString());
                        if ("".equals(flag)) {
                            flowDto.setThisUseQuantity(Float.valueOf(roRepairPart.get("partQuantity").toString()));
                        } else {
                            flowDto.setThisUseQuantity(0F - Float.valueOf(roRepairPart.get("partQuantity").toString()));
                        }
                        flowDto.setPartSalesPrice(Utility.getDouble(roRepairPart.get("partSalesPrice").toString()));
                        flowDto.setPartCostPrice(Utility.getDouble(roRepairPart.get("partCostPrice").toString()));
                        flowDto.setPartSalesAmount(Utility.getDouble(roRepairPart.get("partSalesAmount").toString()));
                        flowDto.setPartCostAmount(Utility.getDouble(roRepairPart.get("partCostAmount").toString()));
                        flowDto.setOperator(userNo);
                        flowDto.setOperateTime(Utility.getCurrentDateTime());
                        flowDto.setDiscount(Utility.getDouble(roRepairPart.get("Discount").toString()));
                        flowDto.setIsMainPart(Integer.valueOf(roRepairPart.get("isMainPart").toString()));
                        flowDto.setLabourCode(roRepairPart.get("labourCode").toString());
                        memberPartFlowService.addMemberPartFlow(flowDto);
                    }
                }
            }
        } else {
            if ("2".equals(flag) && Utility.testString(roNo)) {
                // add by jll 配件与项目强关联时，作废工单，导致获取不到配件信息
                // 只能根据工单号到会员卡配件流水中反推 配件编号和会员卡ID,然后据此更新会员卡配件的已用次数
                Map<String, Object> flowMap = new HashMap<String, Object>();
                // flowMap.put("cardId", roRepairPart.get("cardId"));
                // flowMap.put("partNo", roRepairPart.get("partNo"));
                flowMap.put("roNo", roRepairPart.get("roNo"));
                flowMap.put("dKey", CommonConstants.D_KEY);
                List<Map> flowList = memberPartFlowService.selectTtMemberPartFlow(flowMap);
                if (flowList != null && flowList.size() > 0) {
                    // 工单中可能一次添加多个会员卡配件，所以要循环更新
                    for (int i = 0; i < flowList.size(); i++) {
                        Map map = flowList.get(i);
                        Map<String, Object> memberMap = new HashMap<String, Object>();
                        memberMap.put("cardId", map.get("CARD_ID"));
                        memberMap.put("partNo", map.get("PART_NO"));
                        memberMap.put("dKey", CommonConstants.D_KEY);
                        List<Map> memberList = memberPartService.selectTtMemberPart(memberMap);
                        if (memberList != null && memberList.size() > 0) {
                            Map oldpo = memberList.get(0);
                            List updatepo = new ArrayList();
                            // ===作废工单，退还的会员卡数量为=======
                            // logger.debug("===作废工单，退还的会员卡数量为======="+flowPO.getThisUseQuantity());
                            updatepo.add(Utility.getDouble(oldpo.get("usedPartCount").toString())
                                         - Utility.getDouble(map.get("THIS_USE_QUANTITY").toString()));
                            updatepo.add(userId);
                            updatepo.add(Utility.getCurrentDateTime());
                            updatepo.add(map.get("CARD_ID"));
                            updatepo.add(map.get("PART_NO"));
                            updatepo.add(CommonConstants.D_KEY);
                            updatepo.add(dealerCode);
                            memberPartService.modifyMemberPartByParams(updatepo);
                        }
                    }
                }
                Map deleteFlowMap = new HashMap();
                deleteFlowMap.put("roNo", roNo);
                memberPartFlowService.deleteTtMemberPartFlow(deleteFlowMap);
                // OperateLog.handleOperateLog("删除会员卡配件流水"
                // , "TT_MEMBER_PART_FLOW,RO_NO="+roNo,
                // Integer.valueOf(DictDataConstant.DICT_ASCLOG_MEMBER_MANAGE), userNo, con, entityCode,
                // userId);
            }
        }
    }

    public void deletePartFlowByRoNo(String entityCode, String roNo) throws ServiceBizException {

    }

    public List<Map> getTtActivityList(String[] activityCodes) {
        String temp = "";
        Map<String, String> activityParam = new HashMap<String, String>();
        for (int i = 0; i < activityCodes.length; i++) {
            temp = temp + "'" + activityCodes[i] + "'";
            if (i != activityCodes.length - 1) {
                temp = temp + ",";
            }
        }
        List<Map> list = withDrawStuffService.selectTtActivity(activityParam);
        return list;
    }

    /**
     * 查询当前时间的会计周期是否做过月结
     * 
     * @param conn
     * @param entityCode
     * @return
     * @throws Exception
     */
    public List getIsFinished() throws Exception {
        Map map = new HashMap();
        return withDrawStuffService.queryAccountingCycle(map);
    }

    /**
     * 更新配件库存数量成本单价成本金额(调拨出库,内部领用，配件销售，配件报损，采购入库，配件移库,维修领料) TODO description
     * 
     * @author chenwei
     * @date 2017年4月22日
     * @param stockItemPO
     * @return
     * @throws Exception
     */
    public int calCostPrice(String dealerCode, Map stockItemPO) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        StringBuffer sqlItem = new StringBuffer("");
        StringBuffer sqlWhere = new StringBuffer("");
        StringBuffer sqlItemWhere = new StringBuffer("");
        int record = 0;
        Map map = new HashMap();
        map.put("itemCode", CommonConstants.DEFAULT_PARA_PART_RATE);
        String defaultValue = withDrawStuffService.selectDefaultPara(map);
        double rate = Utility.add("1", defaultValue);
        Map statusMap = new HashMap();
        statusMap.put("itemCode", CommonConstants.DEFAULT_PARA_BATCH_SATAUS);
        String defaultStatus = withDrawStuffService.selectDefaultPara(statusMap);
        logger.debug("税率:" + rate);
        if (DictCodeConstants.DICT_IS_NO.equals(defaultStatus)) {
            // 不使用批次的时候
            sql.append(" STOCK_QUANTITY = IFNULL('STOCK_QUANTITY', 0)");
            sql.append(" + ").append(stockItemPO.get("stockQuantity")).append(", COST_PRICE = CASE WHEN (");
            sql.append("IFNULL('STOCK_QUANTITY', 0) + ").append(stockItemPO.get("stockQuantity")).append(" ) >  0 AND (");
            sql.append("IFNULL('COST_AMOUNT', 0) + ").append(stockItemPO.get("costAmount")).append(" )>= 0  THEN round((");
            sql.append("IFNULL('COST_AMOUNT', 0) + ").append(stockItemPO.get("costAmount")).append(" ) /( ");
            sql.append("IFNULL('COST_AMOUNT', 0) + ").append(stockItemPO.get("stockQuantity")).append(" ),4) ELSE (CASE WHEN ");
            sql.append("IFNULL('LATEST_PRICE', 0)=0 THEN COST_PRICE ELSE IFNULL('COST_AMOUNT', 0)/").append(rate).append(" END) END ,  COST_AMOUNT = ");
            sql.append("IFNULL('COST_AMOUNT', 0) +  ").append(stockItemPO.get("costAmount")).append(" ");
            sqlItem.append(" STOCK_QUANTITY = IFNULL('STOCK_QUANTITY', 0) + ");
            sqlItem.append(stockItemPO.get("stockQuantity")).append(" , COST_PRICE = CASE WHEN (").append("IFNULL('STOCK_QUANTITY', 0) + ");
            sqlItem.append(stockItemPO.get("stockQuantity")).append(" ) > 0 AND (").append("IFNULL('COST_AMOUNT', 0) +  ").append(stockItemPO.get("stockQuantity"));
            sqlItem.append(" )>= 0  THEN round((IFNULL('COST_AMOUNT', 0) + ").append(stockItemPO.get("costAmount")).append(" ) /( ");
            sqlItem.append("IFNULL('STOCK_QUANTITY', 0) + ").append(stockItemPO.get("stockQuantity")).append(" ),4) ELSE (CASE WHEN ");
            sqlItem.append("IFNULL('LATEST_PRICE', 0) + ").append("=0 THEN (SELECT COST_PRICE FROM TM_PART_STOCK WHERE PART_NO='");
            sqlItem.append(stockItemPO.get("partNo")).append("' AND STORAGE_CODE='").append(stockItemPO.get("storageCode"));
            sqlItem.append("' AND D_KEY=").append(CommonConstants.D_KEY).append(" AND DEALER_CODE='").append(dealerCode);
            sqlItem.append("') ELSE IFNULL('LATEST_PRICE', 0)/").append(rate).append(" END) END ,  COST_AMOUNT = ");
            sqlItem.append("IFNULL('COST_AMOUNT', 0) +  ").append(stockItemPO.get("costAmount")).append(" ");
        } else {
            // 使用批次的情况下
            sql.append(" STOCK_QUANTITY = IFNULL('STOCK_QUANTITY', 0) + ").append(stockItemPO.get("stockQuantity"));
            sql.append(" ,  COST_PRICE = LATEST_PRICE ,  COST_AMOUNT = ").append("IFNULL('COST_AMOUNT', 0) + ").append(" -  ");
            sql.append(stockItemPO.get("costAmount")).append(" ");
            sqlItem.append(" STOCK_QUANTITY = IFNULL('STOCK_QUANTITY', 0) + ").append(stockItemPO.get("stockQuantity"));
            sqlItem.append(" , COST_PRICE = LATEST_PRICE ,  COST_AMOUNT = ").append("IFNULL('COST_AMOUNT', 0)").append(" ");
        }
        sqlWhere.append(" DEALER_CODE = '").append(dealerCode).append("'  AND PART_NO = '").append(stockItemPO.get("partNo"));
        sqlWhere.append("'  AND STORAGE_CODE = '").append(stockItemPO.get("storageCode")).append("'  AND D_KEY = ").append(CommonConstants.D_KEY);
        sqlItemWhere.append(" DEALER_CODE = '").append(dealerCode).append("'  AND PART_NO = '").append(stockItemPO.get("partNo"));
        sqlItemWhere.append("'  AND STORAGE_CODE = '").append(stockItemPO.get("storageCode")).append("'  AND PART_BATCH_NO = '");
        sqlItemWhere.append(stockItemPO.get("partBatchNo")).append("'  AND D_KEY = ").append(CommonConstants.D_KEY);
        logger.debug(checkNegative(dealerCode, stockItemPO.get("storageCode").toString()) + "不允许负库存");
        try {
            record = tmPartStockService.modifyTmPartStockrByParams(sql.toString(), sqlWhere.toString());
            tmPartStockItemService.modifyTmPartStockrItemByParams(sqlItem.toString(), sqlItemWhere.toString(),
                                                                  new ArrayList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return record;
    }

    public boolean checkNegative(String entityCode, String storageCode) {
        Map sqlMap = new HashMap();
        sqlMap.put("storageCode", storageCode);
        List<Map> storageList = withDrawStuffService.queryStorageList(sqlMap);
        if (storageList == null || storageList.size() < 1) { // modifidy by sf 2011-02-15 FOR DXP
            return false;// 不允许
        }
        Map storagePO = (Map) storageList.get(0);
        try {
            if (Utility.getInt(DictCodeConstants.DICT_IS_NO) == Utility.getInt(storagePO.get("IS_NEGATIVE").toString())) {
                // 不允许负库存
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
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
     * @param v1
     * @param v2
     * @return 加法运算
     */
    public double add(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();

    }

    private Double getSubF(Double v1, Float v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null) s1 = v1.toString();
        if (v2 != null) s2 = v2.toString();
        double d = Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
        return d;
    }

    private Double getAddD(Double v1, Double v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null) s1 = v1.toString();
        if (v2 != null) s2 = v2.toString();
        return Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
    }

    private Double getSubD(Double v1, Double v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null) s1 = v1.toString();
        if (v2 != null) s2 = v2.toString();
        return Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
    }

    private static Double getAddF(Float v1, Float v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null) s1 = v1.toString();
        if (v2 != null) s2 = v2.toString();
        double d = Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
        return d;
    }
}
