
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartCostAdjustServiceImpl.java
*
* @Author : jcsi
*
* @Date : 2016年7月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月15日    Administrator    1.0
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.PO.basedata.PartCostAdjustPo;

/**
* 配件成本价调整流水账 实现类
* @author jcsi
* @date 2016年7月15日
*/
@Service
public class PartCostAdjustServiceImpl implements PartCostAdjustService {
    
    
    @Autowired
    private OperateLogService operateLogService;
    
    @Autowired
    private CommonNoService commonNoService;
    
    /**
     * 根据查询条件查询
    * @author jcsi
    * @date 2016年7月29日
    * @param param
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartCostAdjustService#search(java.util.Map)
    */
    	
    public PageInfoDto search(Map<String, String> param)throws ServiceBizException{
        StringBuffer sb=new StringBuffer("select A.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE,TS.STORAGE_NAME, A.PART_BATCH_NO,");
        sb.append(" A.STORAGE_POSITION_CODE, A.PART_NAME,A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE,");
        sb.append(" B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY,");
        sb.append(" A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY,A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY,");
        sb.append(" A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO,A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET,");
        sb.append(" (A.STOCK_QUANTITY + A.BORROW_QUANTITY -IFNULL(A.LEND_QUANTITY,0)) AS USEABLE_QUANTITY, ");
        sb.append(" CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN");
        sb.append(" "+DictCodeConstants.DICT_IS_YES+" ELSE "+DictCodeConstants.DICT_IS_NO+" END  AS IS_MAINTAIN");
        sb.append(" from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO )");
        sb.append(" LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE)");
        sb.append(" LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE WHERE A.PART_STATUS<> "+ DictCodeConstants.DICT_IS_YES);
        sb.append(" AND A.DEALER_CODE = "+FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append("  AND C.D_KEY = ").append(CommonConstants.D_KEY);
        List<Object> queryParam=new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(param.get("STORAGE_CODE"))){
            sb.append(" and A.STORAGE_CODE = ? ");
            queryParam.add(param.get("STORAGE_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("PART_NO"))){
            sb.append(" and A.PART_NO = ?");
            queryParam.add(param.get("PART_NO"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("SPELL_CODE"))){
            sb.append(" and A.SPELL_CODE like ? ");
            queryParam.add("%"+param.get("SPELL_CODE")+"%");
        }
       
        return DAOUtil.pageQuery(sb.toString(), queryParam);
        
    }
    
    /**
     * 更新
    * @author jcsi
    * @date 2016年7月29日
    * @param psId
    * @param pcaDto
    * @param adjustNo
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartCostAdjustService#update(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartCostAdjustDto, java.lang.String)
     */
    @Override
    public void update(String psId,String psIds,Map map)throws ServiceBizException{
        List<Object> list = new ArrayList<>();
        list.add(psId);
        List<Map> all = DAOUtil.findAll("select DEALER_CODE,PART_BATCH_NO from tm_part_stock_item where part_no=?", list);
        String id = commonNoService.getSystemOrderNo(CommonConstants.SRV_CBTZDH);
        //修改库存
        TmPartStockPO partStockPO = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),psId,psIds);
        partStockPO.setDouble("COST_PRICE", map.get("NEW_PRICE"));
        partStockPO.setDouble("COST_AMOUNT", map.get("NEW_AMOUNT"));
        partStockPO.saveIt();
        //修改批次
        TmPartStockItemPO itemPO = TmPartStockItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),psId,psIds,all.get(0).get("PART_BATCH_NO"));
        itemPO.setDouble("COST_PRICE", map.get("NEW_PRICE"));
        itemPO.setDouble("COST_AMOUNT", map.get("NEW_AMOUNT"));
        itemPO.saveIt();
        //新增流水
        PartCostAdjustPo pca=new PartCostAdjustPo();
        pca.setString("ADJUST_NO", id);
        pca.setString("ADJUSTER", CommonConstants.SESSION_EMPNO);
        pca.setString("PART_NO", psId);
        pca.setString("PART_NAME", partStockPO.getString("PART_NAME"));
        pca.setDouble("COST_PRICE", map.get("COST_PRICE"));
        pca.setDouble("COST_AMOUNT", map.get("COST_AMOUNT"));
        pca.setDouble("NEW_PRICE", map.get("NEW_PRICE"));
        pca.setDouble("NEW_AMOUNT", map.get("NEW_AMOUNT"));
        pca.setString("CREATED_BY", CommonConstants.SESSION_USERID);
        pca.setString("STORAGE_POSITION_CODE",itemPO.getString("STORAGE_POSITION_CODE"));
        pca.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        pca.setString("STORAGE_CODE",partStockPO.getString("STORAGE_CODE"));
        pca.setString("STOCK_QUANTITY",partStockPO.getString("STOCK_QUANTITY"));
        pca.set("ADJUSTD_AT",new Date());
        pca.saveIt();
        //log
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("配件成本价调整："+map.get("STORAGE_CODE")+"库,原配件成本价："+map.get("COST_PRICE")+" , 调整为："+map.get("NEW_PRICE"));
        operateLogDto.setOperateType(DictCodeConstants.LOG_SYSTEM_PART);
        //operateLogService.writeOperateLog(operateLogDto);
        String content="配件成本价调整："+map.get("STORAGE_CODE")+"库,原配件成本价："+map.get("COST_PRICE")+" , 调整为："+map.get("NEW_PRICE");
        operateLogService.recordOperateLog(content, DictCodeConstants.LOG_SYSTEM_PART);
    }

    
  
    
    /**
     * 根据id查找
    * @author jcsi
    * @date 2016年7月29日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartCostAdjustService#findStoreById(java.lang.Long)
    */
    	
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> findStoreById(String id)throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        list.add(id);
        return DAOUtil.findFirst("select a.DEALER_CODE,b.STORAGE_CODE,b.STORAGE_NAME,a.PART_NO,a.PART_NAME,a.COST_PRICE,a.STOCK_QUANTITY,a.COST_AMOUNT from tm_part_stock a left join tm_storage b on a.storage_code=b.storage_code where part_no=?", list);
    }
    
    
    

}
