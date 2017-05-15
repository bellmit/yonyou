
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
import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.NumberUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartCodesDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartInventoryItemDTO;
import com.yonyou.dms.part.domains.PO.basedata.PartInventoryItemPO;

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
        StringBuffer sqlsb=new StringBuffer("SELECT PART_INVENTORY_ID,DEALER_CODE,INVENTORY_NO,INVENTORY_DATE,IS_CONFIRMED,HANDLER FROM tt_part_inventory WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        sqlsb.append(" and INVENTORY_DATE >=? ");
        params.add(DateUtil.parseDefaultDate(queryParam.get("BENGIN_DATE")));
        sqlsb.append(" and INVENTORY_DATE <? ");
        params.add(DateUtil.addOneDay(queryParam.get("END_DATE")));
        if(!StringUtils.isNullOrEmpty(queryParam.get("INVENTORY_NO"))){
            sqlsb.append(" and INVENTORY_NO like ?");
            params.add("%"+queryParam.get("INVENTORY_NO")+"%");
        }
        //配件报溢(报损)使用
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_CONFIRMED"))){
            sqlsb.append(" and IS_CONFIRMED =? ");
            params.add(Integer.parseInt(queryParam.get("IS_CONFIRMED")));
            //报损
            if(queryParam.get("selectType").equals("loss")){
                sqlsb.append(" and LOSS_TAG=? ");
                //报溢
            }else if(queryParam.get("selectType").equals("profit")){
                sqlsb.append(" and PROFIT_TAG=? ");
            }
            params.add(DictCodeConstants.STATUS_IS_NOT);
        }
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sqlsb.toString(), params);
        return pageinfoDto;
    }

    /**
     * 根据条件查询配件信息
     * @author ZhengHe
     * @date 2016年7月27日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryItem(java.util.Map)
     */
    @Override
    public PageInfoDto queryPartInventoryItem(Map<String, String> queryParam)throws ServiceBizException{
        StringBuffer sb=new StringBuffer("SELECT t1.DEALER_CODE AS DEALER_CODE,t1.PART_CODE AS PART_CODE,t1.PART_NAME AS PART_NAME,t2.STORAGE_CODE AS STORAGE_CODE,t2.STORAGE_POSITION_CODE AS STORAGE_POSITION_CODE,(t2.STOCK_QUANTITY + t2.BORROW_QUANTITY - t2.LEND_QUANTITY) AS REAL_STOCK,t1.PART_GROUP_CODE AS PART_GROUP_CODE,t2.SALES_PRICE AS SALES_PRICE,t2.COST_PRICE AS PART_COST_PRICE,t1.PART_STATUS AS PART_STATUS,t3.STORAGE_NAME AS STORAGE_NAME FROM tm_part_info t1 ")
                .append(" LEFT JOIN tt_part_stock t2 ON t1.PART_CODE = t2.PART_CODE AND t1.DEALER_CODE=t2.DEALER_CODE")
                .append(" LEFT JOIN tm_store t3 ON  t2.STORAGE_CODE=t3.STORAGE_CODE AND t1.DEALER_CODE=t3.DEALER_CODE")
                .append(" WHERE 1=1");

        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_CODE"))){
            sb.append(" and t1.PART_CODE like ?");
            params.add("%"+queryParam.get("PART_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NAME"))){
            sb.append(" and t1.PART_NAME like ?");
            params.add("%"+queryParam.get("PART_NAME")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))){
            sb.append(" and t2.STORAGE_CODE=? ");
            params.add(queryParam.get("STORAGE_CODE"));
        }else{
            //默认全选时的配件仓库权限
            LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
            sb.append(" and t2.STORAGE_CODE IN (");
            sb.append( loginInfo.getPurchaseDepot()).append(")");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))){
            sb.append(" and t1.PART_GROUP_CODE=? ");
            params.add(Integer.parseInt(queryParam.get("PART_GROUP_CODE")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_STATUS"))){
            sb.append(" and t1.PART_STATUS=? ");
            params.add(Integer.parseInt(queryParam.get("PART_STATUS")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE_FROM"))){
            sb.append(" and t2.STORAGE_POSITION_CODE BETWEEN ?");
            params.add(queryParam.get("STORAGE_POSITION_CODE_FROM"));
            if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE_TO"))){
                sb.append(" and ?");
                params.add(queryParam.get("STORAGE_POSITION_CODE_TO"));
            }else{
                sb.append(" and '' ");
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE_TO"))){
            if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE_FROM"))){
                sb.append("");
            }else{
                sb.append(" and t2.STORAGE_POSITION_CODE BETWEEN '' and ?");
                params.add(queryParam.get("STORAGE_POSITION_CODE_TO"));
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("NUM"))){
            if(StringUtils.isEquals(queryParam.get("NUM"), DictCodeConstants.INVENTORY_GREATER+"")){
                sb.append(" and t2.STOCK_QUANTITY>0 ");
            }else{
                if(StringUtils.isEquals(queryParam.get("NUM"), DictCodeConstants.INVENTORY_LESS+"")){
                    sb.append(" and t2.STOCK_QUANTITY<0 ");
                }else{
                    sb.append(" and t2.STOCK_QUANTITY=0 ");
                }
            }
        }
//        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sb.toString(), params);
//        List<Map> rows=new ArrayList<Map>();
//        for(Map pageMap:pageinfoDto.getRows()){
//           String partCode=pageMap.get("PART_CODE").toString();
//           String storageCode=pageMap.get("STORAGE_CODE").toString();
//           pageMap.put("PART_STORAGE_CODE", partCode+"--"+storageCode);
//           rows.add(pageMap);
//        }
//        PageInfoDto newPageinfoDto=new PageInfoDto();
//        newPageinfoDto.setTotal(pageinfoDto.getTotal());
//        newPageinfoDto.setRows(rows);
      PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params,new DefinedRowProcessor() {
      @Override
      protected void process(Map<String, Object> row) {
          row.put("PART_STORAGE_CODE",row.get("PART_CODE")+"__"+row.get("STORAGE_CODE"));
      }
      });
        return pageInfoDto;
    }


    /**
     * 增加盘点单信息
     * @author ZhengHe
     * @date 2016年7月27日
     * @param partCodes
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#addPartInventory(java.util.List)
     */
    @Override
    public TtPartInventoryPO addPartInventory(List<PartCodesDTO> partCodes,PartInventoryDTO piDto,String systemOrderNo) throws ServiceBizException {
    	TtPartInventoryPO piPo=new TtPartInventoryPO();
        setPiPo(piPo,systemOrderNo,piDto);
        piPo.saveIt();
        for(PartCodesDTO partCode:partCodes){
            String[]partCodeArray=partCode.getPartStorageCode().split("__");
            Map piimap=getpiiPo(partCodeArray[0],partCodeArray[1]);
            PartInventoryItemPO piiPo=new PartInventoryItemPO();
            setPiiPo(piPo,piiPo,piimap,partCodeArray[0]);
            piiPo.saveIt();
        }

        return piPo;
    }

    
    /**
    * 根据id新增配件
    * @author ZhengHe
    * @date 2016年9月13日
    * @param id
    * @param partCodes
    * @param piDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#addPartInventoryById(java.lang.Long, java.util.List, com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO)
    */
    	
    @Override
    public void addPartInventoryById(Long id, List<PartCodesDTO> partCodes,
                                     PartInventoryDTO piDto) throws ServiceBizException {
        for(PartCodesDTO partCode:partCodes){
            String[]partCodeArray=partCode.getPartStorageCode().split("__");
            Map piiMap=getpiiMap(id,partCodeArray[0],partCodeArray[1]);
            if(!StringUtils.isNullOrEmpty(piiMap))
                throw new ServiceBizException("已存在该配件盘点单，无法进行新增");
        }
        TtPartInventoryPO piPo=TtPartInventoryPO.findById(id);
        for(PartCodesDTO partCode:partCodes){
            String[]partCodeArray=partCode.getPartStorageCode().split("__");
            Map piimap=getpiiPo(partCodeArray[0],partCodeArray[1]);
            PartInventoryItemPO piiPo=new PartInventoryItemPO();
            setPiiPo(piPo,piiPo,piimap,partCodeArray[0]);
            piiPo.saveIt();
        }
    }
    
    /**
     * 设置盘点单信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piPo
     * @param piDto
     */
    public void setPiPo(TtPartInventoryPO piPo,String systemOrderNo,PartInventoryDTO piDto){
        piPo.setString("INVENTORY_NO", systemOrderNo);
        piPo.setInteger("IS_CONFIRMED", DictCodeConstants.STATUS_IS_NOT);
        piPo.setInteger("PROFIT_TAG", DictCodeConstants.STATUS_IS_NOT);
        piPo.setInteger("LOSS_TAG", DictCodeConstants.STATUS_IS_NOT);
        piPo.setDouble("PROFIT_COUNT", 0);
        piPo.setDouble("LOSS_COUNT", 0);
        piPo.setDouble("PROFIT_AMOUNT", 0);
        piPo.setDouble("LOSS_AMOUNT", 0);
        piPo.setString("REMARK", "");
        piPo.setString("HANDLER",piDto.getHandler());
        piPo.setDate("INVENTORY_DATE", new Date());//setDate会将时间变为 00:00:00
    }

    /**
     * 设置盘点单明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piiDto
     * @return
     */
    public void setPiiPo(TtPartInventoryPO piPo,PartInventoryItemPO piiPo,Map piimap,String partCode){
        piiPo.setString("PART_NO", partCode);
        piiPo.setLong("PART_INVENTORY_ID", piPo.getLongId());
        piiPo.setString("STORAGE_CODE",piimap.get("STORAGE_CODE"));
        piiPo.setString("STORAGE_POSITION_CODE", piimap.get("STORAGE_POSITION_CODE"));
        piiPo.setString("PART_NAME", piimap.get("PART_NAME"));
        piiPo.setDouble("STOCK_QUANTITY", piimap.get("STOCK_QUANTITY"));
        piiPo.setDouble("BORROW_QUANTITY", piimap.get("BORROW_QUANTITY"));
        piiPo.setDouble("LEND_QUANTITY", piimap.get("LEND_QUANTITY"));
        piiPo.setDouble("REAL_STOCK", piimap.get("REAL_STOCK"));
        piiPo.setDouble("CHECK_QUANTITY", piimap.get("REAL_STOCK"));
        piiPo.setDouble("PROFIT_LOSS_QUANTITY",  0);
        piiPo.setDouble("COST_PRICE", piimap.get("PART_COST_PRICE"));
        piiPo.setDouble("PROFIT_LOSS_AMOUNT", 0);
    }
    /**
     * 获取配件数量
     * @author ZhengHe
     * @date 2016年8月2日
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#getAllStockQuantity()
     */
    @Override
    public Double getAllStockQuantity()throws ServiceBizException{
        StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,SUM(STOCK_QUANTITY) as ALL_NUM FROM tt_part_stock");
        List<Object> params = new ArrayList<Object>();
        Map<String,Object> numMap=DAOUtil.findFirst(sqlsb.toString(), params);
        Object allNum=numMap.get("ALL_NUM");
        return Double.parseDouble(allNum.toString());
    }

    /**
     * 获取配件明细
     * @author ZhengHe
     * @date 2016年8月2日
     * @param partCode
     * @return
     */
    public Map getpiiPo(String partCode ,String storageCode){
        StringBuffer sqlsb=new StringBuffer("SELECT t1.DEALER_CODE AS DEALER_CODE,t1.PART_CODE AS PART_CODE,t1.PART_NAME AS PART_NAME,t1.STORAGE_CODE AS STORAGE_CODE,t1.STORAGE_POSITION_CODE AS STORAGE_POSITION_CODE,t1.STOCK_QUANTITY STOCK_QUANTITY,t1.BORROW_QUANTITY BORROW_QUANTITY,t1.LEND_QUANTITY LEND_QUANTITY,t1.STOCK_QUANTITY+t1.BORROW_QUANTITY-t1.LEND_QUANTITY AS REAL_STOCK,t2.PART_GROUP_CODE AS PART_GROUP_CODE,t1.SALES_PRICE AS SALES_PRICE,t1.COST_PRICE AS PART_COST_PRICE,t1.PART_STATUS AS PART_STATUS FROM tt_part_stock t1 LEFT JOIN TM_PART_INFO t2 ON t1.PART_CODE=t2.PART_CODE AND t1.DEALER_CODE=t2.DEALER_CODE  WHERE 1=1 and t1.PART_CODE=? ");
        List<String> params=new ArrayList<String>();
        params.add(partCode);
        sqlsb.append(" and t1.STORAGE_CODE=?");
        params.add(storageCode);
        Map piiMap=DAOUtil.findFirst(sqlsb.toString(), params);
        return piiMap;
    }

    /**
     * 判断配件是否在配件明细中存在
     * @author ZhengHe
     * @date 2016年8月2日
     * @param partCode
     * @return
     */
    public Map getpiiMap(Long id,String partCode,String storageCode){
        StringBuffer sqlsb=new StringBuffer("SELECT t1.DEALER_CODE,t1.STORAGE_CODE as STORAGE_CODE,PART_NO FROM tt_part_inventory_item t1 LEFT JOIN tt_part_inventory t2 ON t1.`DEALER_CODE`=t2.`DEALER_CODE` AND t1.PART_INVENTORY_ID=t2.PART_INVENTORY_ID AND t2.IS_CONFIRMED='10041002' WHERE 1=1 AND PART_NO=?");
        List<Object> params=new ArrayList<Object>();
        params.add(partCode);
        sqlsb.append(" and t1.STORAGE_CODE=?");
        params.add(storageCode);
        sqlsb.append(" and t1.PART_INVENTORY_ID=?");
        params.add(id);
        Map piiMap=null;
        try{
            piiMap=DAOUtil.findFirst(sqlsb.toString(), params);
        }catch(Exception e){
            piiMap=null;
            return piiMap;
        }
        return piiMap;
    }

    /**
     * 获取配件单信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryById(java.lang.Long)
     */
    @Override
    public TtPartInventoryPO queryPartInventoryById(Long id) throws ServiceBizException {
        return TtPartInventoryPO.findById(id);
    }

    /**
     * 根据id获取配件明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryItemById(java.lang.Long)
     */
    @Override
    public PageInfoDto queryPartInventoryItemById(Long id,Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("SELECT ITEM_ID,ITEM_ID AS itemId,t1.PART_INVENTORY_ID AS PART_INVENTORY_ID,INVENTORY_NO,t1.DEALER_CODE AS DEALER_CODE,t3.STORAGE_NAME AS STORAGE_NAME,t1.STORAGE_CODE AS STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO,PART_NAME,STOCK_QUANTITY,BORROW_QUANTITY,LEND_QUANTITY,REAL_STOCK,CHECK_QUANTITY,PROFIT_LOSS_QUANTITY,COST_PRICE,PROFIT_LOSS_AMOUNT FROM tt_part_inventory_item t1 ");
        sqlsb.append(" LEFT JOIN tm_store t3 ON  t1.STORAGE_CODE=t3.STORAGE_CODE AND t1.DEALER_CODE=t3.DEALER_CODE ");       
        sqlsb.append("LEFT JOIN tt_part_inventory t2 ON t1.PART_INVENTORY_ID=t2.PART_INVENTORY_ID WHERE 1=1 and t1.PART_INVENTORY_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        if(!StringUtils.isNullOrEmpty(queryParam.get("isNotZero"))){
            sqlsb.append(" and PROFIT_LOSS_QUANTITY !=0");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partCodesearch"))){
            sqlsb.append(" and PART_NO=?");
            params.add(queryParam.get("partCodesearch"));
        }
        return DAOUtil.pageQuery(sqlsb.toString(), params);
    }

    /**
     * 根据id获取配件明细信息
     * @author ZhengHe
     * @date 2016年12月07日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryItemById(java.lang.Long)
     */
    @Override
    public List<Map> queryPartInventoryItemPrintById(Long id)throws ServiceBizException{
        StringBuffer sqlsb=new StringBuffer("SELECT ITEM_ID,ITEM_ID AS itemId,t1.PART_INVENTORY_ID AS PART_INVENTORY_ID,INVENTORY_NO,t1.DEALER_CODE AS DEALER_CODE,t3.STORAGE_NAME AS STORAGE_NAME,t1.STORAGE_CODE AS STORAGE_CODE,STORAGE_POSITION_CODE,PART_NO,PART_NAME,STOCK_QUANTITY,BORROW_QUANTITY,LEND_QUANTITY,REAL_STOCK,CHECK_QUANTITY,PROFIT_LOSS_QUANTITY,COST_PRICE,PROFIT_LOSS_AMOUNT FROM tt_part_inventory_item t1 ");
        sqlsb.append(" LEFT JOIN tm_store t3 ON  t1.STORAGE_CODE=t3.STORAGE_CODE AND t1.DEALER_CODE=t3.DEALER_CODE ");       
        sqlsb.append("LEFT JOIN tt_part_inventory t2 ON t1.PART_INVENTORY_ID=t2.PART_INVENTORY_ID WHERE 1=1 and t1.PART_INVENTORY_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return DAOUtil.findAll(sqlsb.toString(), params);
    }
    /**
     * 查询盘点明细（配件报溢用）
     *  @author xukl
     * @date 2016年8月16日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#qryInventoryItemById(java.lang.Long)
     */

    @Override
    public List<Map> qryInventoryItemById(Long id)throws ServiceBizException{
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("  ttpi.PART_NO AS PART_CODE,\n")
                .append("  ttpi.PART_NAME,\n")
                .append("  ttpi.STORAGE_CODE,\n")
                .append("  ttpi.STORAGE_POSITION_CODE,\n")
                .append("  tps.UNIT,\n")
                .append("  ttpi.COST_PRICE,\n")
                .append("  ttpi.ITEM_ID,\n")
                .append("  ttpi.PART_INVENTORY_ID,\n")
                .append("  ttpi.DEALER_CODE,\n")
                .append("  ttpi.PROFIT_LOSS_QUANTITY as PROFIT_QUANTITY,\n")
                .append("  ttpi.PROFIT_LOSS_AMOUNT as PROFIT_AMOUNT,\n")
                .append("  ttpi.COST_PRICE as PROFIT_PRICE,'13051001' as fromtype,\n")
                .append("  ttpi.COST_PRICE*ttpi.PROFIT_LOSS_QUANTITY as COST_AMOUNT\n")
                .append("FROM\n")
                .append("  tt_part_inventory_item ttpi\n")
                .append("LEFT JOIN tt_part_stock tps ON tps.STORAGE_CODE = ttpi.STORAGE_CODE\n")
                .append("AND tps.PART_CODE = ttpi.PART_NO and tps.DEALER_CODE= ttpi.DEALER_CODE\n")
                .append("WHERE\n")
                .append("  ttpi.PART_INVENTORY_ID =?\n");
        ;
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        List<Map> list=DAOUtil.findAll(sb.toString(), params);
        return list;
    }
    /**
     * 确定配件单及其明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param piDto
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#modifyPartInventory(com.yonyou.dms.part.domains.DTO.basedata.PartInventoryDTO)
     */
    @Override
    public void modifyPartInventory(String status,PartInventoryDTO piDto) throws ServiceBizException {
    	TtPartInventoryPO piPo=TtPartInventoryPO.findById(piDto.getPartInventoryId());
        if(StringUtils.isEquals(status, "true")){
            piPo.setInteger("IS_CONFIRMED",DictCodeConstants.STATUS_IS_YES);
        }
        setPiPo(piPo, piDto);
        piPo.saveIt();
    }

    /**
     * 获取配件库存数量
     * @author ZhengHe
     * @date 2016年8月2日
     * @param partCodes
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#getStockQuantity(java.util.List)
     */
    @Override
    public Double getStockQuantity(List<PartCodesDTO> partCodes)throws ServiceBizException{
        Double allNum=new Double(0);
        for(PartCodesDTO partCode:partCodes){
            String[]partCodeArray=partCode.getPartStorageCode().split("__");
            StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,STOCK_QUANTITY  FROM tt_part_stock where 1=1 ");
            List<String> params=new ArrayList<String>();
            sqlsb.append(" and PART_CODE=?");
            params.add(partCodeArray[0]);
            sqlsb.append(" and STORAGE_CODE=?");
            params.add(partCodeArray[1]);
            Map<String,Object> numMap=DAOUtil.findFirst(sqlsb.toString(), params);
            Object stockQuantity=numMap.get("STOCK_QUANTITY");
            allNum=allNum+Double.parseDouble(stockQuantity.toString());
        }
        return allNum;
    }

    /**
     * 删除配件单及其明细
     * @author ZhengHe
     * @date 2016年8月2日
     * @param id
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#deletePartInventoryById(java.lang.Long)
     */
    @Override
    public void deletePartInventoryById(Long inventoryId) throws ServiceBizException {
    	TtPartInventoryPO piPo=TtPartInventoryPO.findById(inventoryId);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件盘点单删除：盘点单号【"+piPo.get("INVENTORY_NO")+"】");
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        operateLogService.writeOperateLog(operateLogDto);
        piPo.deleteCascadeShallow();
    }

    /**
     * 删除配机明细信息
     * @author ZhengHe
     * @date 2016年8月2日
     * @param inventoryId
     * @param id
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#deletePartInventoryItemById(java.lang.Long, java.lang.Long)
     */
    @Override
    public void deletePartInventoryItemById(Long inventoryId,Long id) throws ServiceBizException {
        PartInventoryItemPO piiPo=PartInventoryItemPO.findById(id);
        TtPartInventoryPO piPo=TtPartInventoryPO.findById(inventoryId);
        piiPo.delete();
    }

    /**
     * 根据id获取盘点明细信息
     * @author ZhengHe
     * @date 2016年8月8日
     * @param inventoryId
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryPartInventoryByItemId(java.lang.Long, java.lang.Long)
     */
    @Override
    public PartInventoryItemPO queryPartInventoryByItemId(Long inventoryId, Long id) throws ServiceBizException {
        return PartInventoryItemPO.findById(id);
    }

    /**
     * 修改盘点明细
     * @author ZhengHe
     * @date 2016年8月8日
     * @param inventoryId
     * @param id
     * @param piiDto
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInventoryService#modifyPartInventoryItem(java.lang.Long, java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartInventoryItemDTO)
     */
    @Override
    public void modifyPartInventoryItem(Long inventoryId, Long id,
                                        PartInventoryItemDTO piiDto) throws ServiceBizException {
    	TtPartInventoryPO piPo=TtPartInventoryPO.findById(inventoryId);
        PartInventoryItemPO piiPo=PartInventoryItemPO.findById(id);
        setPiiPo(piiPo, piiDto);
        piiPo.saveIt();
    }

    /** 
     * 设置盘点明细信息
     * @author ZhengHe
     * @date 2016年8月8日
     * @param piiPo
     * @param piiDto
     */
    public void setPiiPo(PartInventoryItemPO piiPo,PartInventoryItemDTO piiDto){
        piiPo.setDouble("CHECK_QUANTITY", piiDto.getCheckQuantity());
        Double profit_loss_quantity=Double.valueOf(NumberUtil.sub(BigDecimal.valueOf(piiDto.getCheckQuantity()),BigDecimal.valueOf(piiPo.getDouble("REAL_STOCK"))).toString());
        piiPo.setDouble("PROFIT_LOSS_QUANTITY", profit_loss_quantity);
        Double profit_loss_amount=Double.valueOf(NumberUtil.mul(BigDecimal.valueOf(profit_loss_quantity), BigDecimal.valueOf(piiPo.getDouble("COST_PRICE"))).toString());
        piiPo.setDouble("PROFIT_LOSS_AMOUNT",profit_loss_amount);
    }

    /**
     * 设置盘点单信息
     * @author ZhengHe
     * @date 2016年8月8日
     * @param piPo
     * @param piDto
     */
    public void setPiPo(TtPartInventoryPO piPo,PartInventoryDTO piDto){
        piPo.setDate("INVENTORY_DATE", piDto.getInventoryDate());
        piPo.setString("REMARK", piDto.getRemark());
        piPo.setString("HANDLER", piDto.getHandler());
        List<Map> piiList=getPiiList(piPo.getLong("PART_INVENTORY_ID"));
        Integer profitCount=0;
        Integer lossCount=0;
        Double profitAmount=0.0;
        Double lossAmount=0.0;
        for(Map piiMap:piiList){
            Double profit_loss_quantity=Double.valueOf(piiMap.get("PROFIT_LOSS_QUANTITY").toString());
            Double profit_loss_amount=Double.valueOf(piiMap.get("PROFIT_LOSS_AMOUNT").toString());
            if(profit_loss_quantity>0){
                profitCount=profitCount+1;
            }
            if(profit_loss_quantity<0){
                lossCount=lossCount+1;
            }
            if(profit_loss_amount>0){
                profitAmount=profitAmount+profit_loss_amount;
            }
            if(profit_loss_amount<0){
                lossAmount=lossAmount+profit_loss_amount;
            }
        }
        piPo.setDouble("PROFIT_COUNT", profitCount);
        piPo.setDouble("LOSS_COUNT", lossCount);
        piPo.setDouble("PROFIT_AMOUNT", profitAmount);
        piPo.setDouble("LOSS_AMOUNT", lossAmount);
    }

    /**
     * 获取数量价格信息
     * @author ZhengHe
     * @date 2016年8月8日
     * @param partInventoryId
     * @return
     */
    public List<Map> getPiiList(Long partInventoryId){
        StringBuffer sqlsb=new StringBuffer("SELECT DEALER_CODE,PROFIT_LOSS_QUANTITY,PROFIT_LOSS_AMOUNT FROM tt_part_inventory_item WHERE 1=1 AND PART_INVENTORY_ID=?");
        List<Object> params=new ArrayList<Object>();
        params.add(partInventoryId);
        List<Map> piiList=null;
        try{
            piiList=DAOUtil.findAll(sqlsb.toString(), params);
        }catch(Exception e){
            piiList=null;
            return piiList;
        }
        return piiList;
    }

    /**
     *  判断明细
    * @author jcsi
    * @date 2016年8月23日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInventoryService#queryItem()
     */
    @Override
    public List<Map> queryItem(Long id,Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("  ttpi.PART_NO AS partCodeShow,\n")
                .append("  ttpi.PART_NAME as partNameShow,\n")
                .append("  ttpi.STORAGE_CODE as storageCodeShow,\n")
                .append("  ttpi.STORAGE_POSITION_CODE as storagePositionCodeShow,\n")
                .append("  tps.UNIT as unitShow,\n")
                .append("  ttpi.COST_PRICE as costPriceShow,\n")
                .append("  ttpi.ITEM_ID,\n")
                .append("  ttpi.PART_INVENTORY_ID,\n")
                .append("  ttpi.DEALER_CODE,\n")
                .append("  abs(ttpi.PROFIT_LOSS_QUANTITY) as lossQuantityShow,\n")
                .append("  abs(ttpi.PROFIT_LOSS_AMOUNT) as lossAmountShow,\n")
                .append("  ttpi.COST_PRICE as PROFIT_PRICE,'"+DictCodeConstants.SOURCE_TYPE_ORDER+"' as fromtype,\n")
                .append("  abs(round(ttpi.COST_PRICE*ttpi.PROFIT_LOSS_QUANTITY,2)) as costAmountShow,\n")
                .append("  ttpi.CHECK_QUANTITY-ttpi.REAL_STOCK as profitQuantity,")
                .append("  round((ttpi.PROFIT_LOSS_AMOUNT/ttpi.PROFIT_LOSS_QUANTITY),2) as lossPriceShow,  ")
                .append("  round(ttpi.PROFIT_LOSS_AMOUNT/(ttpi.CHECK_QUANTITY-ttpi.REAL_STOCK),2)  as profitPrice   ")
                .append("FROM\n")
                .append("  tt_part_inventory_item ttpi\n")
                .append("LEFT JOIN tt_part_stock tps ON tps.STORAGE_CODE = ttpi.STORAGE_CODE\n")
                .append("AND tps.PART_CODE = ttpi.PART_NO and tps.DEALER_CODE= ttpi.DEALER_CODE\n")
                .append("WHERE\n")
                .append("  ttpi.PART_INVENTORY_ID =?  \n");
        
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        if(!StringUtils.isNullOrEmpty(queryParam.get("type"))){
            //配件报溢 
            if("profit".equals(queryParam.get("type"))){
                sb.append(" and ttpi.CHECK_QUANTITY>ttpi.REAL_STOCK ");
            //报损    
            }else if("loss".equals(queryParam.get("type"))){
                sb.append(" and ttpi.CHECK_QUANTITY<ttpi.REAL_STOCK ");
            }
        }
        List<Map> list=DAOUtil.findAll(sb.toString(), params);
        return list;
    }



}
