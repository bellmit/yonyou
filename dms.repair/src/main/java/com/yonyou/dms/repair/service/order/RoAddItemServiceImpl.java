
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RoAddItemServiceImpl.java
*
* @Author : ZhengHe
*
* @Date : 2016年8月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月22日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.service.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.RoAddItemPO;
import com.yonyou.dms.commonAS.domains.DTO.order.RoAddItemDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
* TODO description
* @author ZhengHe
* @date 2016年8月22日
*/
@Service
public class RoAddItemServiceImpl implements RoAddItemService{

    
    /**
    * 新增附加项目
    * @author ZhengHe
    * @date 2016年8月22日
    * @param raiDto
    * @param roId
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.RoAddItemService#addRoAddItem(com.yonyou.dms.repair.domains.DTO.order.RoAddItemDTO, java.lang.String)
    */
    	
    @Override
    public void addRoAddItem(RoAddItemDTO raiDto, Long roId) throws ServiceBizException {
        RoAddItemPO raiPo=new RoAddItemPO();
        setRoAddItemPo(raiPo, raiDto,roId);
        raiPo.saveIt();
    }

    
    
    /**
    * 设置RoAddItemPO
    * @author ZhengHe
    * @date 2016年8月23日
    * @param raiPo
    * @param raiDto
    * @param roId
    */
    	
    public void setRoAddItemPo(RoAddItemPO raiPo,RoAddItemDTO raiDto, Long roId){
        raiPo.setLong("RO_ID", roId);
        raiPo.setString("CHARGE_PARTITION_CODE", raiDto.getChargePartitionCode());
        raiPo.setString("ADD_ITEM_CODE",raiDto.getAddItemCode());
        raiPo.setString("ADD_ITEM_NAME", raiDto.getAddItemName());
        raiPo.setString("REMARK", raiDto.getRemark());
        raiPo.setDouble("ADD_ITEM_AMOUNT", raiDto.getAddItemAmount());
        raiPo.setDouble("RECEIVABLE_AMOUNT", raiDto.getReceivableAmount());
    }



    
    /**
    * 删除附加项目信息
    * @author ZhengHe
    * @date 2016年9月21日
    * @param raiDto
    * @param roId
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.RoAddItemService#deleteRoAddItem(com.yonyou.dms.commonAS.domains.DTO.order.RoAddItemDTO, java.lang.Long)
    */
    	
    @Override
    public void deleteRoAddItem(Long roId) throws ServiceBizException {
        RoAddItemPO roiPo=new RoAddItemPO();
        roiPo.delete("RO_ID=?",roId);
    }



    
    /**
    * 获取附加项目信息
    * @author ZhengHe
    * @date 2016年9月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.order.RoAddItemService#queryAddRoItem(java.util.Map)
    */
    	
    @Override
    public List<Map> queryAddRoItem(Long roId) throws ServiceBizException {
        StringBuffer sqlsb=new StringBuffer("SELECT rai.DEALER_CODE,rai.CHARGE_PARTITION_CODE,rai.ADD_ITEM_CODE,rai.ADD_ITEM_NAME,rai.ADD_ITEM_AMOUNT,");
         sqlsb.append("rai.RECEIVABLE_AMOUNT,rai.REMARK,tcp.CHARGE_PARTITION_NAME,(rai.ADD_ITEM_AMOUNT-RECEIVABLE_AMOUNT) as DIS_COUNT_AMOUNT  from tt_ro_add_item as rai");
         sqlsb.append(" left join tm_charge_partition tcp on tcp.DEALER_CODE=rai.DEALER_CODE and tcp.CHARGE_PARTITION_CODE=rai.CHARGE_PARTITION_CODE ");
         sqlsb.append(" where 1=1 ");
         sqlsb.append(" and rai.RO_ID=?");
         List<Long> queryParam=new ArrayList<Long>();
         queryParam.add(roId);
         List<Map> raiList=new ArrayList<Map>();
         raiList=DAOUtil.findAll(sqlsb.toString(), queryParam);
         return raiList;
    }
}
