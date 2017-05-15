/**
 * 
 */
package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TMLimitSeriesDatainfoDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPartItemDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.CommonErrorConstant;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.TtSalesPartDTO;
import com.yonyou.dms.part.service.basedata.SalesPartService;
import com.yonyou.dms.part.service.basedata.SalesQuoteService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 
* TODO description
* @author chenwei
* @date 2017年5月10日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ttSalesPart")
@SuppressWarnings("rawtypes")
public class salesPartController extends BaseController {

    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(salesPartController.class);
    @Autowired
    private SalesQuoteService   salesQuoteService;
    
    @Autowired
    private SalesPartService   salesPartService;

    @Autowired
    private CommonNoService     commonNoService;

    @Autowired
    private OperateLogService   operateLogService;
    
    @Autowired
    private ExcelGenerator        excelService;

    /**
     * 查询partInfo信息
     * 
     * @author chenwei
     * @date 2017年5月9日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/SearchSalesPart", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findAllSalesPartInfo(@RequestParam Map<String, String> queryParam) {
        return salesPartService.findAllSalesPartInfo(queryParam);
    }
    
    /**
     * 1新增配件页面 查询配件库存列表
     * 
     * @author chenwei
     * @date 2017年5月5日
     * @param queryParam 查询条件
     * @return 查询结果
     */
    @RequestMapping(value = "/queryPartStock", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartStock(@RequestParam Map<String, String> queryParam) {
        return salesPartService.queryPartStock(queryParam);
    }
    
    /**
     * 报价单保存/作废
     * 
     * @author 陈伟
     * @date 2017年3月24日
     * @param troubleDescDTO
     * @param uriCB
     * @return
     * @throws ServiceBizException
     */
    @RequestMapping(value = "/savePartSales",method = RequestMethod.POST)
    @ResponseBody
    public int addSalesQuote(@RequestBody TtSalesPartDTO salesPartDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        
        try {
//          新增开关是否启用配件限价功能
            List<TtSalesPartItemDTO> itemList = salesPartDTO.getPartSalestable();
            String isCheckPLP = commonNoService.getDefalutPara("3432");
            String salesPartNo = salesPartDTO.getSalesPartNo();
            String Id = "";
            String number = "";
            String itemIdTemp = "";
            TtSalesPartDTO partDto = new TtSalesPartDTO();
            String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
            Long userId = FrameworkUtil.getLoginInfo().getUserId();
            String vin = salesPartDTO.getVin();
            Map tmLSDPo1=null;
            List tmLimitSerieslist = null;
            /**
             * 根据工单号查询是否已经有该工单号的配件销售单记录(如果是现在对应的则不抛异常)
             * 如果有则抛异常,给出提示
             */
            if (!StringUtils.isNullOrEmpty(salesPartDTO.getRoNo()))
            {
                TtSalesPartDTO partPOCon = new TtSalesPartDTO();
                partPOCon.setdKey(CommonConstants.D_KEY);
                partPOCon.setRoNo(salesPartDTO.getRoNo());
                List<Map> partSalesItem = salesPartService.querySalesPartList(partPOCon);
                if (partSalesItem != null && partSalesItem.size() > 0)
                {
                    //判断是不是该销售单号
                    Map partPO = (Map) partSalesItem.get(0);
                    if (!salesPartDTO.getRoNo().trim().equals(partPO.get("SALES_PART_NO")))
                    {
                        //存在该工单的配件销售单记录
                        throw new ServiceBizException(CommonErrorConstant.MSG_ERROR_SALES_PART_RO_BEEN);
                    }
                }
            }
            //获取符合政策的浮动比例
            //如果有关联的工单的销售配件订单，那么车系和维修类型取工单上的
            //如果没有关联工单的配件销售单，哪门只通过车辆Vin获取车系和品牌，来获取，不判断维修类型
            if(Utility.testString(salesPartDTO.getRoNo())){
                Map<String, Object> ttROPo=new HashMap<String, Object>();
                ttROPo.put("roNo", salesPartDTO.getRoNo());
                ttROPo.put("dKey", CommonConstants.D_KEY);
                List<Map> list = salesPartService.selectRepairOrder(ttROPo);
                ttROPo = list.get(0);
                TMLimitSeriesDatainfoDTO tmLSDPo=new TMLimitSeriesDatainfoDTO();
                if(!StringUtils.isNullOrEmpty(ttROPo.get("repairTypeCode")))
                tmLSDPo.setRepairTypeCode(ttROPo.get("repairTypeCode").toString());
                if(!StringUtils.isNullOrEmpty(ttROPo.get("brand")))
                tmLSDPo.setBrandCode(ttROPo.get("brand").toString());
                if(!StringUtils.isNullOrEmpty(ttROPo.get("series")))
                tmLSDPo.setSeriesCode(ttROPo.get("series").toString());
                tmLSDPo.setdKey(CommonConstants.D_KEY);
                tmLSDPo.setIsValid(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                tmLSDPo.setOemTag(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                tmLimitSerieslist = salesPartService.queryLimitSeriesDatainfo(tmLSDPo);
                if(tmLimitSerieslist!=null&&tmLimitSerieslist.size()>0){
                    tmLSDPo1=(Map)tmLimitSerieslist.get(0);
                    logger.debug("---------------有销售浮动价-------------------");
                }
            }else if(Utility.testString(vin)){
                TmVehicleDTO tmVehiclePo=new TmVehicleDTO();
                tmVehiclePo.setVin(vin);
                List<Map> listVehicle = salesPartService.queryTmVehicleList(tmVehiclePo);
                if(listVehicle!=null&&listVehicle.size()>0){
                    Map vehicleMap=(Map)listVehicle.get(0);
                    TMLimitSeriesDatainfoDTO tmLSDPo=new TMLimitSeriesDatainfoDTO();
                    if(!StringUtils.isNullOrEmpty(vehicleMap.get("BRAND")))
                    tmLSDPo.setBrandCode(vehicleMap.get("BRAND").toString());
                    tmLSDPo.setSeriesCode(tmVehiclePo.getSeries());
                    tmLSDPo.setdKey(CommonConstants.D_KEY);
                    tmLSDPo.setIsValid(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                    tmLSDPo.setOemTag(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                    tmLimitSerieslist = salesPartService.queryLimitSeriesDatainfo(tmLSDPo);
                    if(tmLimitSerieslist!=null&&tmLimitSerieslist.size()>0){
                        tmLSDPo1=(Map)tmLimitSerieslist.get(0);
                        logger.debug("---------------有销售浮动价-------------------");
                    }
                }
            }
            if (!StringUtils.isNullOrEmpty(salesPartDTO.getUpdateStatus()) && "A".equals(salesPartDTO.getUpdateStatus())){
                
            }
            if (!StringUtils.isNullOrEmpty(salesPartDTO.getUpdateStatus()) && "U".equals(salesPartDTO.getUpdateStatus())){
                
            }
            if (!StringUtils.isNullOrEmpty(salesPartDTO.getUpdateStatus()) && "D".equals(salesPartDTO.getUpdateStatus())){
                
            }
            if (itemList != null && itemList.size() > 0){
                for(int i = 0; i< itemList.size(); i++){
                    TtSalesPartItemDTO itemDto = itemList.get(i);
                    TtSalesPartItemDTO item = new TtSalesPartItemDTO();
                    TtSalesPartItemDTO itemCon = new TtSalesPartItemDTO();
                    /**
                     * 写入配件销售子表的成本单价为配件库存中最新成本单价
                     */
                    if ("A".equals(itemDto.getItemUpdateStatus())){
                        item.setBatchNo(itemDto.getBatchNo());
                        item.setChargePartitionCode(itemDto.getChargePartitionCode());
                        if(!StringUtils.isNullOrEmpty(itemDto.getSalesDiscount()))
                            item.setDiscount(itemDto.getSalesDiscount());
                        else
                            item.setDiscount(itemDto.getDiscount());
                        item.setDealerCode(dealerCode);
                        item.setManageSortCode(itemDto.getManageSortCode());
                        item.setPartBatchNo(itemDto.getPartBatchNo());
                        item.setPartName(itemDto.getPartName());
                        item.setPartNo(itemDto.getPartNo());
                        item.setPartQuantity(itemDto.getPartQuantity());
//                      参数3432控制
                        logger.debug("参数3432控制判断,配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售限价");
                        if(Utility.testString(isCheckPLP) && DictCodeConstants.DICT_IS_YES.equals(isCheckPLP)){
                            if(tmLSDPo1!=null){
                              //销售浮动比例
                                Double limitSeriesDatainfo=Double.parseDouble(tmLSDPo1.get("LIMIT_PRICE_RATE").toString());
                                Double limitPrice=0.00;
                                //配件销售在出库配件时，管控配件发料单价（或出库单价）不能高于销售浮动价 
                                Map<String, Object> tpipo1 = new HashMap<String, Object>();
                                tpipo1.put("partNo", itemDto.getPartNo());
                                tpipo1.put("downTag", Utility.getInt(DictCodeConstants.DICT_IS_YES));
                                tpipo1.put("dKey", CommonConstants.D_KEY);
                                List<Map> listTpi = salesQuoteService.queryPartInfoList(tpipo1);
                              //获取销售价格
                                if (listTpi != null && listTpi.size() > 0){
                                    Map partInfoMap = listTpi.get(0);
                                    if(!StringUtils.isNullOrEmpty(partInfoMap.get("LIMIT_PRICE")) && Double.valueOf(partInfoMap.get("LIMIT_PRICE").toString())>0.0){
                                        limitPrice = Double.valueOf(partInfoMap.get("LIMIT_PRICE").toString());
                                        logger.debug("销售限价:" + limitPrice);
                                        logger.debug("浮动比例:" + limitSeriesDatainfo);
                                        //销售浮动比例+1
                                        limitSeriesDatainfo= add(1.00D,limitSeriesDatainfo);
                                        //（销售浮动比例+1）*销售价格
                                        limitPrice=mul(limitPrice,limitSeriesDatainfo);
                                        logger.debug("销售浮动价:" + limitPrice);
                                        //如果发料价格大于销售浮动价那么报错
                                        if(limitPrice > 0 && itemDto.getPartSalesPrice() >0){
                                            if(itemDto.getPartSalesPrice() > limitPrice) {
                                                 throw new ServiceBizException("配件("+itemDto.getPartNo()+")销售单价不能高于销售浮动价!");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        item.setPartSalesPrice(itemDto.getPartSalesPrice());
                        item.setPartSalesAmount(itemDto.getPartSalesAmount());
                        if (!StringUtils.isNullOrEmpty(itemDto.getPartCostPrice()))
                            item.setPartCostPrice(itemDto.getPartCostPrice());
                        if (!StringUtils.isNullOrEmpty(itemDto.getPartCostAmount()))
                            item.setPartCostAmount(itemDto.getPartCostAmount());
                        if(!StringUtils.isNullOrEmpty(itemDto.getSalesAmount()))    
                            item.setSalesAmount(itemDto.getSalesAmount());
                        if(!StringUtils.isNullOrEmpty(itemDto.getSalesDiscount()))  
                            item.setSalesDiscount(itemDto.getSalesDiscount());
                        item.setReceiver(itemDto.getReceiver());
                        if (!StringUtils.isNullOrEmpty(itemDto.getOldSalesPartNo()))
                        item.setOldSalesPartNo(itemDto.getOldSalesPartNo());  
                        if (!StringUtils.isNullOrEmpty(salesPartDTO.getUpdateStatus()) && "A".equals(salesPartDTO.getUpdateStatus().trim())){
                            item.setSalesPartNo(Id);
                        }
                        else{
                            item.setSalesPartNo(salesPartNo);
                        }
                        item.setSendTime(Utility.getCurrentDateTime());
                        item.setStorageCode(itemDto.getStorageCode());
                        item.setStoragePositionCode(itemDto.getStoragePositionCode());
                        item.setUnitCode(itemDto.getUnitCode());
                        //if(!StringUtils.isNullOrEmpty(itemDto.getPriceRate()))
                        item.setPriceRate(itemDto.getPriceRate());// 价格系数
                        item.setPriceType(itemDto.getPriceType());// 价格类型
                        item.setIsDiscount(itemDto.getIsDiscount());
                        //新增配件销售字表
                        salesPartService.insertTtSalesPartItemPO(item);
                    }
                    if ("U".equals(itemDto.getItemUpdateStatus())){
                        
                    }
                    if ("D".equals(itemDto.getItemUpdateStatus())){
                        
                    }
                }
                
                for(int i = 0; i < itemList.size() ; i ++){
                    TtSalesPartItemDTO itemDto = itemList.get(i);
                    //前提，是退料。每操作一次配件退料数量，就统计一次可退料的配件数量，>=零则可退，<零则不可退
                    if(!StringUtils.isNullOrEmpty(itemList.get(i).getOldSalesPartNo())){
                        Float quantity = salesPartService.querySumQuantity(itemDto.getPartNo(), salesPartNo, itemDto.getStorageCode(), itemDto.getStoragePositionCode(), itemDto.getPartBatchNo());
                        if(quantity<0){
                            throw new ServiceBizException("原配件销售单【"+itemDto.getOldSalesPartNo()+"】的配件【"+itemDto.getPartNo()+"】退料数量不能大于销售的数量");
                        }
                    }
                }
            }
            //actionContext.setStringValue("SALES_PART_NO", number);
            //actionContext.setStringValue("ITEM_ID", itemIdTemp);
            //actionContext.setStringValue("VER", new Integer(Utility.getInt(ver)+1).toString());
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * @param v1
     * @param v2
     * @return 乘法运算
     */
    public  double mul(double v1,double v2){ 

        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 

        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 

        return b1.multiply(b2).doubleValue(); 

        }
    /**
     * @param v1
     * @param v2
     * @return 加法运算
     */
    public  double add(double v1,double v2){ 

        BigDecimal b1 = new BigDecimal(Double.toString(v1)); 

        BigDecimal b2 = new BigDecimal(Double.toString(v2)); 

        return b1.add(b2).doubleValue(); 

        }

}
