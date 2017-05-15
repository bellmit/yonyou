
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : DecroDateServiceImp.java
*
* @Author : zsw
*
* @Date : 2016年9月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月5日    zsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.ordermanage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.retail.domains.DTO.ordermanage.DecorationDTO;
import com.yonyou.dms.retail.domains.DTO.ordermanage.DecorationPartDTO;
import com.yonyou.dms.retail.domains.PO.ordermanage.DecorationPO;
import com.yonyou.dms.retail.domains.PO.ordermanage.DecorationPartPO;

/**装潢定义实现类
* 
* @author zsw
* @date 2016年9月5日
*/
@Service
public class DecroDateServiceImp implements DecroDateService {
    @Override
    public PageInfoDto queryDecroDate(Map<String,String> queryParam) throws ServiceBizException{
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("td.DECRODATE_ID,\n")
                .append("td.DEALER_CODE,\n")
                .append("td.LABOUR_CODE,\n")
                .append("td.LABOUR_NAME,\n")
                .append("td.SPELL_CODE,\n")
                .append("td.LOCAL_LABOUR_CODE,\n")
                .append("td.LOCAL_LABOUR_NAME,\n")
                .append("td.DECRODATE_TYPE,\n")
                .append("td.MODEL_LABOUR_CODE,\n")
                .append("td.STD_LABOUR_HOUR,\n")
                .append("td.ASSIGN_LABOUR_HOUR,\n")
                .append("tsd.DISCOUNT,\n")
                .append("tsd.AFTER_DISCOUNT_AMOUNT,\n")
                .append("tsd.REMARK,\n")
                .append("td.WORKER_TYPE_CODE \n")
                .append("from tm_decrodate td \n")
                .append("left join TT_SO_DECRODATE tsd on td.LABOUR_CODE=tsd.LABOUR_CODE and td.DEALER_CODE=tsd.DEALER_CODE \n")
                .append("where 1=1 \n");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("labourCode"))){
            sb.append(" and td.LABOUR_CODE like ?");
            params.add("%"+queryParam.get("labourCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("labourName"))){
            sb.append(" and td.LABOUR_NAME like ?");
            params.add("%"+queryParam.get("labourName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("decrodateType"))){
            sb.append(" and td.DECRODATE_TYPE = ?");
            params.add(Integer.parseInt(queryParam.get("decrodateType")));
        }
        sb.append(" group by td.DECRODATE_ID");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }
    
    /**
     * 新增
    * @author zhongsw
    * @date 2016年9月11日
    * @param decrodto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.DecroDateService#insertDecro(com.yonyou.dms.retail.domains.DTO.ordermanage.DecorationDTO)
    */
    	
    @Override
    public Long insertDecro(DecorationDTO decrodto) throws ServiceBizException {
        StringBuffer sb=new StringBuffer("select DECRODATE_ID,DEALER_CODE,LABOUR_CODE,LABOUR_NAME from tm_decrodate where LABOUR_CODE=?");
        List<Object> list=new ArrayList<Object>();
        list.add(decrodto.getLabourCode());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        
        StringBuffer sb2=new StringBuffer("select DECRODATE_ID,DEALER_CODE,LABOUR_CODE,LABOUR_NAME from tm_decrodate where LABOUR_NAME=?");
        List<Object> list2=new ArrayList<Object>();
        list2.add(decrodto.getLabourName());
        List<Map> map2=DAOUtil.findAll(sb2.toString(), list2);
        if(map.size()>0 || map2.size()>0){
            throw new ServiceBizException("装潢代码或名称不能重复！");
        }else{
        DecorationPO dp = new DecorationPO();
        dp.setString("LABOUR_CODE",decrodto.getLabourCode());
        dp.setString("LABOUR_NAME",decrodto.getLabourName());
        dp.setInteger("DECRODATE_TYPE",decrodto.getDecrodateType());
        dp.setString("LOCAL_LABOUR_CODE",decrodto.getLocalLabourCode());
        dp.setString("LOCAL_LABOUR_NAME",decrodto.getLocalLabourName());
        dp.setInteger("WORKER_TYPE_CODE",decrodto.getWorkerTypeCode());
        dp.setDouble("STD_LABOUR_HOUR",decrodto.getStdLabourHour());
        dp.setDouble("ASSIGN_LABOUR_HOUR",decrodto.getAssignLabourHour());
        dp.saveIt();
        if(decrodto.getDecorationpartItemList().size()>0){
            for (DecorationPartDTO decorationPartDto : decrodto.getDecorationpartItemList()) {
                DecorationPartPO dpp = new DecorationPartPO();
                dpp.setString("PART_NAME", decorationPartDto.getPartName());
                dpp.setString("STORAGE_CODE", decorationPartDto.getStorageCode());
                dpp.setString("PART_NO", decorationPartDto.getPartNo());
                dpp.setDouble("PART_QUANTITY", decorationPartDto.getNumber());
                dpp.setDouble("PART_SALES_PRICE", decorationPartDto.getPartSalesPrice());
                dp.add(dpp);
            }
        }
        return dp.getLongId();
        }
    }
    /**
     * DecorationPartPO赋值
    * @author zhongsw
    * @date 2016年11月3日
    * @param dpp
    * @param decorationPartDto
     */
    public void setDecorationPartPO(DecorationPartPO dpp,DecorationPartDTO decorationPartDto){
        dpp.setString("PART_NAME", decorationPartDto.getPartName());
        dpp.setString("STORAGE_CODE", decorationPartDto.getStorageCode());
        dpp.setString("PART_NO", decorationPartDto.getPartNo());
        dpp.setDouble("PART_QUANTITY", decorationPartDto.getNumber());
        dpp.setDouble("PART_SALES_PRICE", decorationPartDto.getPartSalesPrice());
    }
    
    /**
     * 根据ID 修改保存
     * @author zhongsw
     */

    @Override
    public void updateDecro(Long id, DecorationDTO decroDTO) throws ServiceBizException{
        DecorationPO dp=DecorationPO.findById(id);
        StringBuffer sb=new StringBuffer("select DECRODATE_ID,DEALER_CODE,LABOUR_CODE,LABOUR_NAME from tm_decrodate where LABOUR_NAME=?");
        List<Object> list=new ArrayList<Object>();
        list.add(decroDTO.getLabourName());
        List<Map> map=DAOUtil.findAll(sb.toString(), list);
        String s=dp.getString("LABOUR_NAME");
        if(s.equals(decroDTO.getLabourName()) || map.size()==0){
            dp.setString("LABOUR_CODE",decroDTO.getLabourCode());
            dp.setString("LABOUR_NAME",decroDTO.getLabourName());
            dp.setInteger("DECRODATE_TYPE",decroDTO.getDecrodateType());
            dp.setString("LOCAL_LABOUR_CODE",decroDTO.getLocalLabourCode());
            dp.setString("LOCAL_LABOUR_NAME",decroDTO.getLocalLabourName());
            dp.setInteger("WORKER_TYPE_CODE",decroDTO.getWorkerTypeCode());
            dp.setDouble("STD_LABOUR_HOUR",decroDTO.getStdLabourHour());
            dp.setDouble("ASSIGN_LABOUR_HOUR",decroDTO.getAssignLabourHour());
            dp.saveIt();
            DecorationPartPO.delete("DECRODATE_ID=?", id);
            for (DecorationPartDTO decorationpartdto : decroDTO.getDecorationpartItemList()) {
               DecorationPartPO dpp=new DecorationPartPO();
               /*dpp.setDouble("PART_QUANTITY", decorationpartdto.getPartQuantity());*/
                setDecorationPartPO(dpp,decorationpartdto);
                dp.add(dpp);
            }
        }else{
            throw new ServiceBizException("项目名称重复！");
        }
    }
    
    /**明细
    * @author zhongsw
    * @date 2016年9月11日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.DecroDateService#getDecoraById(java.lang.Long)
    */
    	
    @Override
    public List<Map> getDecoraById(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("td.DECRODATE_ID,\n")
                .append("td.DEALER_CODE,\n")
                .append("td.LABOUR_CODE,\n")
                .append("td.LABOUR_NAME,\n")
                .append("td.SPELL_CODE,\n")
                .append("td.LOCAL_LABOUR_CODE,\n")
                .append("td.LOCAL_LABOUR_NAME,\n")
                .append("td.DECRODATE_TYPE,\n")
                .append("td.MODEL_LABOUR_CODE,\n")
                .append("td.STD_LABOUR_HOUR,\n")
                .append("td.ASSIGN_LABOUR_HOUR,\n")
                .append("td.WORKER_TYPE_CODE,\n")
                .append("tdp.DECRODATE_PART_ID,\n")
                .append("tdp.STORAGE_CODE,\n")
                .append("tdp.PART_NO,\n")
                .append("tdp.PART_NAME,\n")
                .append("tdp.PART_QUANTITY,\n")
                .append("ts.STORAGE_NAME,\n")
                .append("tdp.PART_SALES_PRICE\n")
                .append(" from TM_DECRODATE td \n")
                .append(" left join TM_DECRODATE_PART tdp on td.DECRODATE_ID=tdp.DECRODATE_ID and td.DEALER_CODE=tdp.DEALER_CODE \n")
                .append(" LEFT JOIN TM_STORE ts on tdp.STORAGE_CODE=ts.STORAGE_CODE and tdp.DEALER_CODE=ts.DEALER_CODE \n")
                .append(" where td.DECRODATE_ID=?\n");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        List<Map> map= DAOUtil.findAll(sb.toString(), queryParams);
        return map;
    }

    /**
     * 根据ID 删除
     * @author zhongsw
     */
    @Override
    public void deleteDecroById(Long id) throws ServiceBizException{
        DecorationPO wtp=DecorationPO.findById(id);
        wtp.delete();
    }

    
    /**编辑数据查询
    * @author zhongsw
    * @date 2016年9月13日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.DecroDateService#editDecoraById(java.lang.Long)
    */
    	
    @Override
    public Map editDecoraById(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append("DECRODATE_ID,\n")
                .append("DEALER_CODE,\n")
                .append("LABOUR_CODE,\n")
                .append("LABOUR_NAME,\n")
                .append("SPELL_CODE,\n")
                .append("LOCAL_LABOUR_CODE,\n")
                .append("LOCAL_LABOUR_NAME,\n")
                .append("DECRODATE_TYPE,\n")
                .append("MODEL_LABOUR_CODE,\n")
                .append("STD_LABOUR_HOUR,\n")
                .append("ASSIGN_LABOUR_HOUR,\n")
                .append("WORKER_TYPE_CODE \n")
                .append(" from TM_DECRODATE \n")
                .append(" where DECRODATE_ID=?\n");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return DAOUtil.findFirst(sb.toString(), queryParams);
        
    }

    /**编辑数据查询
     * @author zhongsw
     * @date 2016年9月13日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.retail.service.ordermanage.DecroDateService#editDecoraById(java.lang.Long)
     */
         
     @Override
     public List<Map> editDecoraByIdItem(Long id) throws ServiceBizException {
         StringBuilder sb = new StringBuilder("SELECT\n")
                 .append("td.DECRODATE_ID,\n")
                 .append("tdp.DECRODATE_PART_ID,\n")
                 .append("tdp.DEALER_CODE,\n")
                 .append("tdp.STORAGE_CODE,\n")
                 .append("tdp.PART_NO,\n")
                 .append("tdp.PART_NAME,\n")
                 .append("tdp.PART_QUANTITY,\n")
                 .append("tdp.PART_SALES_PRICE,\n")
                 .append("ts.STORAGE_NAME\n")
                 .append(" from TM_DECRODATE td \n")
                 .append(" LEFT JOIN TM_DECRODATE_PART tdp on td.DECRODATE_ID=tdp.DECRODATE_ID and td.DEALER_CODE=tdp.DEALER_CODE \n")
                 .append(" LEFT JOIN TM_STORE ts on tdp.STORAGE_CODE=ts.STORAGE_CODE and tdp.DEALER_CODE=ts.DEALER_CODE \n")
                 .append(" where td.DECRODATE_ID=?\n");
         List<Object> queryParams = new ArrayList<Object>();
         queryParams.add(id);
         return DAOUtil.findAll(sb.toString(), queryParams);
         
     }
    
    /**子表明细
    * @author zhongsw
    * @date 2016年9月19日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.retail.service.ordermanage.DecroDateService#searchSalesOrderItem(java.lang.Long)
    */
    	
    @Override
    public PageInfoDto searchSalesOrderItem(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tdp.DECRODATE_PART_ID, tdp.DEALER_CODE, tdp.STORAGE_CODE, ts.STORAGE_NAME, tps.STORAGE_POSITION_CODE, tdp.PART_NO as PART_CODE, tdp.PART_NAME, tdp.PART_QUANTITY, tdp.PART_SALES_PRICE as SALES_PRICE,(tdp.PART_QUANTITY*tdp.PART_SALES_PRICE) as AFTER_DISCOUNT_AMOUNT from TM_DECRODATE_PART tdp LEFT JOIN tm_store ts on ts.STORAGE_CODE = tdp.STORAGE_CODE left join tt_part_stock tps on tps.STORAGE_CODE = tdp.STORAGE_CODE and tps.PART_CODE = tdp.PART_NO and tps.DEALER_CODE=tdp.DEALER_CODE WHERE tdp.DECRODATE_ID = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        PageInfoDto map= DAOUtil.pageQuery(sb.toString(), queryParams);
        return map;
    }
    
}
