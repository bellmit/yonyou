
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : PartStockServiceImpl.java
 *
 * @Author : xukl
 *
 * @Date : 2016年7月15日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年7月15日    xukl    1.0
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

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件库存信息查询实现类
 * @author dingchaoyu
 * @date 2016年7月15日
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PartStockServiceImpl implements PartStockService {

    /**
     * 
     *  @author xukl
     * @date 2016年7月15日
     * @param queryParam
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.part.service.basedata.PartStockService#queryPartStocks(java.util.Map)
     */

    @Override
    public PageInfoDto queryPartStocks(Map<String,String> queryParam) throws ServiceBizException{
        List<Object> params = new ArrayList<Object>();
        double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
        StringBuffer sqlsb = new StringBuffer();
        sqlsb.append("SELECT td.DEALER_NAME,A.PROVIDER_CODE,A.PROVIDER_NAME,B.OPTION_NO,A.COST_PRICE * "+rate);
        sqlsb.append(" AS NET_COST_PRICE,A.COST_AMOUNT * "+rate);
        sqlsb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,");
        sqlsb.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,");
        sqlsb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, A.SAFE_STOCK,");
        sqlsb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS,");
        sqlsb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,");
        sqlsb.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,");
        sqlsb.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,");
        sqlsb.append(" B.IS_ACC,B.SUB_CATEGORY_CODE,B.BIG_CATEGORY_CODE,B.THD_CATEGORY_CODE,B.IS_STORAGE_SALE,");
        sqlsb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");  
        sqlsb.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE,");
        sqlsb.append(" vst.STORAGE_NAME,B.IS_BACK FROM TM_PART_STOCK A ");
        sqlsb.append(" LEFT JOIN tm_dealer_basicinfo td ON A.DEALER_CODE=td.DEALER_CODE");
        sqlsb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+")");
        sqlsb.append(" B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sqlsb.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE DEALER_CODE");
        sqlsb.append(" in (select A.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A where DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND C.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND D_KEY="+CommonConstants.D_KEY);
        sqlsb.append(" GROUP BY DEALER_CODE,PART_NO ) D ");
        sqlsb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        sqlsb.append(" LEFT JOIN ("+CommonConstants.VM_STORAGE+")");
        sqlsb.append(" vst ON vst.DEALER_CODE=a.DEALER_CODE AND vst.storage_code=a.storage_code ");
        sqlsb.append(" WHERE A.DEALER_CODE in (select A.SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A WHERE DEALER_CODE =?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND A.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND A.D_KEY = ?");
        params.add(CommonConstants.D_KEY);
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))){
            String[] split = queryParam.get("STORAGE_CODE").split(",");
            sqlsb.append(" AND ");
            for(int i=0;i<split.length;i++){
                sqlsb.append(" A.STORAGE_CODE = '"+split[i]+"'");
                if (i+1<split.length) {
                    sqlsb.append(" OR ");
                }
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NO"))){
            sqlsb.append(" AND A.PART_NO like ?");
            params.add("%"+queryParam.get("PART_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NAME"))){
            sqlsb.append(" AND A.PART_NAME like ?");
            params.add("%"+queryParam.get("PART_NAME")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))){
            sqlsb.append(" AND A.PART_GROUP_CODE like ?");
            params.add("%"+queryParam.get("PART_GROUP_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_MODEL_GROUP_CODE_SET"))){
            sqlsb.append(" AND A.PART_MODEL_GROUP_CODE_SET like ?");
            params.add("%"+queryParam.get("PART_MODEL_GROUP_CODE_SET")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_STATUS"))){
            sqlsb.append(" AND A.PART_STATUS like ?");
            params.add("%"+queryParam.get("PART_STATUS")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE"))){
            sqlsb.append(" AND A.STORAGE_POSITION_CODE like ?");
            params.add("%"+queryParam.get("STORAGE_POSITION_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("SPELL_CODE"))){
            sqlsb.append(" AND A.SPELL_CODE like ?");
            params.add("%"+queryParam.get("SPELL_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_MAIN_TYPE"))){
            sqlsb.append(" AND A.PART_MAIN_TYPE like ?");
            params.add("%"+Integer.parseInt(queryParam.get("PART_MAIN_TYPE"))+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("IS_THINGS"))){
            sqlsb.append(" AND B.IS_THINGS like ?");
            params.add("%"+queryParam.get("IS_THINGS")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("DEALER_CODE"))){
            sqlsb.append(" AND A.DEALER_CODE = ?");
            params.add(queryParam.get("DEALER_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("REMARK"))){
            sqlsb.append(" AND A.REMARK like ?");
            params.add("%"+queryParam.get("REMARK")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("KUCUN"))&&queryParam.get("KUCUN").equals(DictCodeConstants.IS_YES)){
            sqlsb.append(" AND A.STOCK_QUANTITY = 0 AND B.PART_STATUS=?");
            params.add(DictCodeConstants.IS_YES);
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("INSTRUCT_PRICE"))){
            if (queryParam.get("INSTRUCT_PRICE").equals("1")) {
                sqlsb.append(" AND B.INSTRUCT_PRICE >= A.SALES_PRICE");
            }else if (queryParam.get("INSTRUCT_PRICE").equals("2")) {
                sqlsb.append(" AND B.INSTRUCT_PRICE <= A.SALES_PRICE");
            }else if (queryParam.get("INSTRUCT_PRICE").equals("3")) {
                sqlsb.append(" AND B.INSTRUCT_PRICE = A.SALES_PRICE");
            }else if (queryParam.get("INSTRUCT_PRICE").equals("4")) {
                sqlsb.append(" AND B.INSTRUCT_PRICE > A.SALES_PRICE");
            }else if (queryParam.get("INSTRUCT_PRICE").equals("5")) {
                sqlsb.append(" AND B.INSTRUCT_PRICE < A.SALES_PRICE");
            }
        }
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sqlsb.toString(), params);
        return pageinfoDto;
    }

    /**
     * 根据id查询
     *  @author xukl
     * @date 2016年7月19日
     * @param id
     * @return map
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.part.service.basedata.PartStockService#getPartStockById(java.lang.Long)
     */

    @Override
    public Map<String, Object>  getPartStockById(String id,String ids) throws ServiceBizException{
        List<Object> params = new ArrayList<Object>();
        double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
        StringBuffer sqlsb = new StringBuffer();
        sqlsb.append("SELECT td.DEALER_NAME,A.PROVIDER_CODE,A.PROVIDER_NAME,B.OPTION_NO,A.COST_PRICE * "+rate);
        sqlsb.append(" AS NET_COST_PRICE,A.COST_AMOUNT * "+rate);
        sqlsb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,");
        sqlsb.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,");
        sqlsb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, A.SAFE_STOCK,");
        sqlsb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS,");
        sqlsb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,");
        sqlsb.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,");
        sqlsb.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,");
        sqlsb.append(" B.IS_ACC,B.SUB_CATEGORY_CODE,B.BIG_CATEGORY_CODE,B.THD_CATEGORY_CODE,B.IS_STORAGE_SALE,");
        sqlsb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");  
        sqlsb.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE,");
        sqlsb.append(" vst.STORAGE_NAME,B.IS_BACK FROM TM_PART_STOCK A ");
        sqlsb.append(" LEFT JOIN tm_dealer_basicinfo td ON A.DEALER_CODE=td.DEALER_CODE");
        sqlsb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+")");
        sqlsb.append(" B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sqlsb.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE DEALER_CODE");
        sqlsb.append(" in (select A.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A where DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND C.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND D_KEY="+CommonConstants.D_KEY);
        sqlsb.append(" GROUP BY DEALER_CODE,PART_NO ) D ");
        sqlsb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        sqlsb.append(" LEFT JOIN ("+CommonConstants.VM_STORAGE+")");
        sqlsb.append(" vst ON vst.DEALER_CODE=a.DEALER_CODE AND vst.storage_code=a.storage_code ");
        sqlsb.append(" WHERE A.DEALER_CODE in (select A.SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A WHERE DEALER_CODE =?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND A.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND A.D_KEY = ?");
        params.add(CommonConstants.D_KEY);
        sqlsb.append(" AND A.PART_NO=?");
        params.add(id);
        sqlsb.append(" AND A.STORAGE_CODE=?");
        params.add(ids);
        return DAOUtil.findFirst(sqlsb.toString(), params);
    }

    /**
     * 根据id修改
     *  @author dingchaoyu
     * @date 2016年7月19日
     * @param id
     * @param partStockDTO
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.part.service.basedata.PartStockService#modifyPartStock(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartStockDTO)
     */

    @Override
    public void modifyPartStock(String id,String ids,Map partStockDTO) throws ServiceBizException {
    	TmPartStockPO partStock = TmPartStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id,ids);
    	TmPartInfoPO partinfo = TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        if (ids.equals("OEMK")) {
            StringBuilder sb= new StringBuilder("select * from tm_part_info where PART_NO=? and DOWN_TAG=12781001");
            List<String> params = new ArrayList<String>();
            params.add(partinfo.getString("PART_NO"));
            List<Map> list = DAOUtil.findAll(sb.toString(), params);
            if(!CommonUtils.isNullOrEmpty(list)){
                throw new ServiceBizException("非OEM配件不允许入OEM仓库!");
            }
        }
        partStock=setPartStocksPO(partStock,partinfo,partStockDTO);
        partStock.saveIt();
    }
    
    /**
     * 设置属性
     * @author dingchaoyu
     * @date 2016年8月25日
     * @param partStock
     * @param partStockDTO
     * @return
     * @throws ServiceBizException
     */

    private TmPartStockPO setPartStocksPO(TmPartStockPO partStock,TmPartInfoPO partinfo,Map partStockDTO)throws ServiceBizException{
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("STORAGE_CODE"))){
            partStock.setString("STORAGE_CODE", partStockDTO.get("STORAGE_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("STORAGE_POSITION_CODE"))){
            partStock.setString("STORAGE_POSITION_CODE", partStockDTO.get("STORAGE_POSITION_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_NO"))){
            partStock.setString("PART_NO", partStockDTO.get("PART_NO"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_NAME"))){
            partStock.setString("PART_NAME", partStockDTO.get("PART_NAME"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SPELL_CODE"))){
            partStock.setString("SPELL_CODE", partStockDTO.get("SPELL_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("INSTRUCT_PRICE"))){
            partinfo.setDouble("INSTRUCT_PRICE", partStockDTO.get("INSTRUCT_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("CLAIM_PRICE"))){
            partStock.setDouble("CLAIM_PRICE", partStockDTO.get("CLAIM_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAX_STOCK"))){
            partStock.setDouble("MAX_STOCK", partStockDTO.get("MAX_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SAFE_STOCK"))){
            partStock.setDouble("SAFE_STOCK", partStockDTO.get("SAFE_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("LATEST_PRICE"))){
            partStock.setDouble("LATEST_PRICE", partStockDTO.get("LATEST_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PRODUCTING_AREA"))){
            partinfo.setString("PRODUCTING_AREA", partStockDTO.get("PRODUCTING_AREA"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MIN_PACKAGE"))){
            partinfo.setDouble("MIN_PACKAGE", partStockDTO.get("MIN_PACKAGE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MIN_STOCK"))){
            partStock.setDouble("MIN_STOCK", partStockDTO.get("MIN_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("UNIT_CODE"))){
            partStock.setDouble("UNIT_CODE", partStockDTO.get("UNIT_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SALES_PRICE"))){
            partStock.setDouble("SALES_PRICE", partStockDTO.get("SALES_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_MAIN_TYPE"))){
            partStock.setDouble("PART_MAIN_TYPE", partStockDTO.get("PART_MAIN_TYPE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_SPE_TYPE"))){
            partStock.setDouble("PART_SPE_TYPE", partStockDTO.get("PART_SPE_TYPE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("INSURANCE_PRICE"))){
            partinfo.setDouble("INSURANCE_PRICE", partStockDTO.get("INSURANCE_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("REMARK"))){
            partStock.setString("REMARK", partStockDTO.get("REMARK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_STATUS"))){
            partStock.setDouble("PART_STATUS", partStockDTO.get("PART_STATUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("IS_STORAGE_SALE"))){
            partinfo.setInteger("IS_STORAGE_SALE", partStockDTO.get("IS_STORAGE_SALE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("IS_SUGGEST_ORDER"))){
            partStock.setDouble("IS_SUGGEST_ORDER", partStockDTO.get("IS_SUGGEST_ORDER"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MONTHLY_QTY_FORMULA"))){
            partStock.setDouble("MONTHLY_QTY_FORMULA", partStockDTO.get("MONTHLY_QTY_FORMULA"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JAN_MODULUS"))){
            partStock.setDouble("JAN_MODULUS", partStockDTO.get("JAN_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("FEB_MODULUS"))){
            partStock.setDouble("FEB_MODULUS", partStockDTO.get("FEB_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAR_MODULUS"))){
            partStock.setDouble("MAR_MODULUS", partStockDTO.get("MAR_MODULUS")); 
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("APR_MODULUS"))){
            partStock.setDouble("APR_MODULUS", partStockDTO.get("APR_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAY_MODULUS"))){
            partStock.setDouble("MAY_MODULUS", partStockDTO.get("MAY_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JUN_MODULUS"))){
            partStock.setDouble("JUN_MODULUS", partStockDTO.get("JUN_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JUL_MODULUS"))){
            partStock.setDouble("JUL_MODULUS", partStockDTO.get("JUL_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("AUG_MODULUS"))){
            partStock.setDouble("AUG_MODULUS", partStockDTO.get("AUG_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SEP_MODULUS"))){
            partStock.setDouble("SEP_MODULUS", partStockDTO.get("SEP_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("OCT_MODULUS"))){
            partStock.setDouble("OCT_MODULUS", partStockDTO.get("OCT_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("NOV_MODULUS"))){
            partStock.setDouble("NOV_MODULUS", partStockDTO.get("NOV_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("DEC_MODULUS"))){
            partStock.setDouble("DEC_MODULUS", partStockDTO.get("DEC_MODULUS"));
        }
        return partStock;
    }
    
    /**
     * 设置属性
     * @author xukl
     * @date 2016年8月25日
     * @param partStock
     * @param partStockDTO
     * @return
     * @throws ServiceBizException
     */

    private TmPartStockPO setPartStockPO(TmPartStockPO partStock,PartStockDTO partStockDTO)throws ServiceBizException{
        partStock.setString("STORAGE_CODE", partStockDTO.getSTORAGE_CODE());
        partStock.setString("PART_NO", partStockDTO.getPART_NO());
        partStock.setString("PART_NAME", partStockDTO.getPART_NAME());
        partStock.setInteger("PART_STATUS", partStockDTO.getPART_STATUS());
        partStock.setTimestamp("LAST_STOCK_IN", partStockDTO.getLAST_STOCK_IN());
        partStock.setTimestamp("LAST_STOCK_OUT", partStockDTO.getLAST_STOCK_OUT());
        partStock.setDouble("STOCK_QUANTITY", partStockDTO.getSTOCK_QUANTITY());
        partStock.setDouble("LOCKED_QUANTITY", partStockDTO.getLOCKED_QUANTITY());
        partStock.setDouble("BORROW_QUANTITY", partStockDTO.getBORROW_QUANTITY());
        partStock.setDouble("LEND_QUANTITY", partStockDTO.getLEND_QUANTITY());

        partStock.setDouble("MAX_STOCK", partStockDTO.getMAX_STOCK());
        partStock.setDouble("MIN_STOCK", partStockDTO.getMIN_STOCK());
        partStock.setDouble("LIMIT_PRICE", partStockDTO.getLIMIT_PRICE());

        partStock.setDouble("SALES_PRICE", partStockDTO.getSALES_PRICE());
        partStock.setDouble("CLAIM_PRICE", partStockDTO.getCLAIM_PRICE());
        partStock.setDouble("COST_PRICE", partStockDTO.getCOST_PRICE());
        partStock.setDouble("COST_AMOUNT", partStockDTO.getCOST_AMOUNT()); 
        return partStock;
    }
    /**
     * 根据查询条件查询
     *  @author xukl
     * @date 2016年7月26日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.part.service.basedata.PartStockService#queryPartStockForExport(java.util.Map)
     */

    @Override
    public List<Map> queryPartStockForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
        StringBuffer sqlsb = new StringBuffer();
        sqlsb.append("SELECT td.DEALER_NAME,A.PROVIDER_CODE,A.PROVIDER_NAME,B.OPTION_NO,A.COST_PRICE * "+rate);
        sqlsb.append(" AS NET_COST_PRICE,A.COST_AMOUNT * "+rate);
        sqlsb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,");
        sqlsb.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,");
        sqlsb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, A.SAFE_STOCK,");
        sqlsb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS,");
        sqlsb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,");
        sqlsb.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,");
        sqlsb.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,");
        sqlsb.append(" B.IS_ACC,B.SUB_CATEGORY_CODE,B.BIG_CATEGORY_CODE,B.THD_CATEGORY_CODE,B.IS_STORAGE_SALE,");
        sqlsb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");  
        sqlsb.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE,");
        sqlsb.append(" vst.storage_name as storage_code_name,B.IS_BACK FROM TM_PART_STOCK A ");
        sqlsb.append(" LEFT JOIN tm_dealer_basicinfo td ON A.DEALER_CODE=td.DEALER_CODE");
        sqlsb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+")");
        sqlsb.append(" B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sqlsb.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE DEALER_CODE");
        sqlsb.append(" in (select A.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A where DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND C.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND D_KEY="+CommonConstants.D_KEY);
        sqlsb.append(" GROUP BY DEALER_CODE,PART_NO ) D ");
        sqlsb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        sqlsb.append(" LEFT JOIN ("+CommonConstants.VM_STORAGE+")");
        sqlsb.append(" vst ON vst.DEALER_CODE=a.DEALER_CODE AND vst.storage_code=a.storage_code ");
        sqlsb.append(" WHERE A.DEALER_CODE in (select A.SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A WHERE DEALER_CODE =?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND A.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND A.D_KEY = ?");
        params.add(CommonConstants.D_KEY);
        List<Map> resultList = DAOUtil.findAll(sqlsb.toString(), params);
        return resultList;
    }

    /**
     * 查询配件信息
     *  @author 
     * @date 2017年4月21日
     * @param param
     * @return
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartInfoService#pageInfoList(java.util.Map)
     */

    @Override
    public PageInfoDto pageInfoList(Map<String, String> param) throws ServiceBizException{
        List<Object> list=new ArrayList<Object>();
        double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
        StringBuilder sb=new StringBuilder("SELECT B.OPTION_NO,A.COST_PRICE*"+rate);
        sb.append(" AS NET_COST_PRICE,A.COST_AMOUNT*"+rate);       
        sb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, TS.STORAGE_NAME, A.STORAGE_POSITION_CODE, A.PART_NAME,"); 
        sb.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,"); 
        sb.append(" A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK,");
        sb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY,TS.CJ_TAG, A.PART_STATUS,  "); 
        sb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, ");        
        sb.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS, ");
        sb.append(" A.SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA, ");
        sb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY,");
        sb.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,B.INSTRUCT_PRICE, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE");
        if (!StringUtils.isNullOrEmpty(Utility.getDefaultValue("1180"))&&Utility.getDefaultValue("1180").equals(DictCodeConstants.DICT_IS_YES)) {
            sb.append(",B.PART_INFIX,F.POS_CODE,E.POS_NAME");
        }else {
            sb.append(",'' as PART_INFIX,'' as POS_CODE,'' as POS_NAME");
        }
        sb.append(" FROM TM_PART_STOCK A LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sb.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C ");
        sb.append(" WHERE DEALER_CODE= ? AND D_KEY= ? GROUP BY DEALER_CODE,PART_NO ) D ");
        sb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        if (!StringUtils.isNullOrEmpty(Utility.getDefaultValue("1180"))&&Utility.getDefaultValue("1180").equals(DictCodeConstants.DICT_IS_YES)) {
            sb.append(" LEFT JOIN TW_POS_INFIX_RELATION F ON A.DEALER_CODE = F.DEALER_CODE AND B.PART_INFIX  = F.PART_INFIX AND F.IS_VALID = " +DictCodeConstants.DICT_IS_YES );
            sb.append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON e.is_valid="+DictCodeConstants.DICT_IS_YES+" and A.DEALER_CODE = E.DEALER_CODE AND F.POS_CODE = E.POS_CODE ");
        }
        sb.append(" LEFT JOIN TM_STORAGE TS ON A.DEALER_CODE = TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE ");
        sb.append(" WHERE A.DEALER_CODE = ? " + " AND A.D_KEY = ? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if(!StringUtils.isNullOrEmpty(param.get("partCode"))){
            sb.append("and ts.PART_NO like ? ");
            list.add("%"+param.get("partCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("partName"))){
            sb.append(" and ts.PART_NAME like ? ");
            list.add("%"+param.get("partName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("spellCode"))){
            sb.append("and ts.SPELL_CODE like ? ");
            list.add("%"+param.get("spellCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(param.get("PART_MODEL_GROUP_CODE_SET"))){
            sb.append("and ts.PART_MODEL_GROUP_CODE_SET = ? ");
            list.add(param.get("PART_MODEL_GROUP_CODE_SET"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("storageCode"))){
            sb.append(" and ts.STORAGE_CODE = ? ");
            list.add(param.get("storageCode"));
        }
        if(!StringUtils.isNullOrEmpty(param.get("PART_SPE_TYPE"))){
            sb.append(" and ts.PART_SPE_TYPE = ? ");
            list.add(Integer.parseInt(param.get("PART_SPE_TYPE")));
        }
        if(!StringUtils.isNullOrEmpty(param.get("brand"))){
            sb.append(" and tm.brand = ?");
            list.add(param.get("brand"));
        }
        return DAOUtil.pageQuery(sb.toString(), list);
    }


    /**
     *  导入
     * @date 2016年8月25日
     * @param partStockDTO
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.PartStockService#importPartStock(com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO)
     */
    @Override
    public void importPartStock(PartStockDTO partStockDTO) throws ServiceBizException {
        String partno =  partStockDTO.getPART_NO();
        String storagecode = partStockDTO.getSTORAGE_CODE();
        Integer partStatus = partStockDTO.getPART_STATUS();
        if(verifyExist("SELECT PART_NO, DEALER_CODE from tm_part_info where PART_NO= ? and PART_STATUS = ?", partno,partStatus)){
            throw new ServiceBizException("不存在此配件或者此配件已停用，不能导入！");
        }
        if(!verifyExist("select STORAGE_CODE,DEALER_CODE from tm_storage where STORAGE_CODE = ?", storagecode)){
            throw new ServiceBizException("不存在此仓库，不能导入！");
        }
        StringBuilder sb=new StringBuilder("SELECT DEALER_CODE,STORAGE_CODE,PART_NO from tm_part_stock where STORAGE_CODE =? and PART_NO = ?");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(storagecode);
        queryParam.add(partno);
        List<Map> list = DAOUtil.findAll(sb.toString(), queryParam);
        if(!CommonUtils.isNullOrEmpty(list)){
            throw new ServiceBizException("已存在此库存，不能导入！");
        }
        TmPartStockPO pspo = new TmPartStockPO();
        pspo=setPartStockPO(pspo, partStockDTO);
        pspo.saveIt();
    }
    
    /**
    * 校验记录是否存在
    * @author xukl
    * @date 2016年8月30日
    * @param table
    * @param whereSql
    * @param condition
    * @return
    */
    private boolean verifyExist(String whereSql,Object... condition){
        List<Object> queryParam=new ArrayList<Object>();
        for (int i = 0; i < condition.length; i++) {
            queryParam.add(condition[i]);
        }
        List<Map> list = DAOUtil.findAll(whereSql, queryParam);
        if(CommonUtils.isNullOrEmpty(list)){
            return false;
        }else{
            return true;
        }
    }
    
    /**
    * 校验 配件号 或者 仓库代码是否存在
    * @author jcsi
    * @date 2016年9月1日
    * @param partNo
    * @param logo
    * @return
     */ 
    @Override
    public boolean partNoOrStorageExist(String partNo,String logo) throws ServiceBizException{       
        StringBuilder sql=new StringBuilder("SELECT DEALER_CODE  from  ");
        //配件号
        if(logo.equals("partno")){
            sql.append(" tm_part_stock  where PART_NO=? ");
        //仓库代码
        }else if(logo.equals("storagecode")){
            sql.append(" tm_store where STORAGE_CODE=? ");
        } 
        return verifyExist(sql.toString(),partNo);
    }

    /**
     * 维修项目查询配件主数据 
    * @author rongzoujie
    * @date 2016年10月20日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartStockService#queryPartInfoByLabour(java.util.Map)
     */
    @Override
    public PageInfoDto queryPartInfoByLabour(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT t3.STORAGE_NAME,t1.FIT_MODEL_CODE,t2.STORAGE_CODE,t1.PART_ID,t1.DEALER_CODE,t1.PART_NO,t1.PART_NAME,t2.SALES_PRICE,(t2.STOCK_QUANTITY+t2.BORROW_QUANTITY-t2.LEND_QUANTITY-t2.LOCKED_QUANTITY) as USABLE_QUANTITY,t2.STOCK_QUANTITY,(case when t1.OEM_TAG = 10041001 then '是' else '否' end) as OEM_TAG,t1.PART_GROUP_CODE FROM tm_part_info t1 ");
        sql.append("LEFT JOIN TT_PART_STOCK t2 ON t1.PART_NO = t2.PART_NO and t1.DEALER_CODE = t2.DEALER_CODE ");
        sql.append("LEFT JOIN tm_store t3 ON t3.STORAGE_CODE = t2.STORAGE_CODE and t3.DEALER_CODE= t1.DEALER_CODE ");
        sql.append("AND t1.DEALER_CODE = t2.DEALER_CODE WHERE 1=1 and (t2.STOCK_QUANTITY+t2.BORROW_QUANTITY-t2.LEND_QUANTITY-t2.LOCKED_QUANTITY) > 0 ");
        List<Object> param =new ArrayList<Object>();
        
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))){
            sql.append("and t3.STORAGE_CODE = ?");
            param.add(queryParam.get("STORAGE_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BRAND"))){
            sql.append("and t1.BRAND = ?");
            param.add(queryParam.get("BRAND"));
        }     
        if(!StringUtils.isNullOrEmpty(queryParam.get("isOem"))){
            sql.append("and t1.OEM_TAG = ?");
            param.add(queryParam.get("isOem"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NO"))){
            sql.append("and t1.PART_NO = ?");
            param.add(queryParam.get("PART_NO"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NAME"))){
            sql.append("and t1.PART_NAME like ?");
            param.add("%"+queryParam.get("PART_NAME")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("SPELL_CODE"))){
            sql.append("and t1.SPELL_CODE like ?");
            param.add("%"+queryParam.get("SPELL_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))){
            sql.append("and t1.PART_GROUP_CODE = ?");
            param.add(queryParam.get("PART_GROUP_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("FIT_MODEL_CODE"))){
            sql.append("and t1.FIT_MODEL_CODE = ?");
            param.add(queryParam.get("FIT_MODEL_CODE"));
        }
        
        return DAOUtil.pageQuery(sql.toString(), param);
    }

    @Override
    public List<Map> getStockCodeById(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sqlSb = new StringBuffer("select * from tm_storage");
        return DAOUtil.findAll(sqlSb.toString(), null);
    }

    @Override
    public List<Map> getDealertCode(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.share_entity AS dealer_code,a.BIZ_CODE,a.RELATIONSHIP_MODE,b.DEALER_SHORTNAME,b.DEALER_NAME FROM ("
                  + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(")  a LEFT JOIN TM_dealer_BASICINFO  b ON b.dealer_code=a.share_entity WHERE   a.dealer_code='"
                  + FrameworkUtil.getLoginInfo().getDealerCode() + "' GROUP BY b.DEALER_SHORTNAME");
        List<Map> list = DAOUtil.findAll(sb.toString(), null);
        return list;
    }

    @Override
    public PageInfoDto pageInfoLists(String id,String ids) throws ServiceBizException {
        List<Object> queryParam=new ArrayList<Object>();
        StringBuilder sb=new StringBuilder("SELECT DEALER_CODE,PART_NO, STORAGE_CODE,REPORT_YEAR AS YEAR,REPORT_MONTH AS MONTH,IN_QUANTITY AS IN_QUANTITY,");
        sb.append(" OUT_QUANTITY AS OUT_QUANTITY FROM TT_PART_PERIOD_REPORT ");       
        sb.append(" WHERE PART_NO = ?"); 
        queryParam.add(id);
        sb.append(" AND STORAGE_CODE = ? "); 
        queryParam.add(ids);
        sb.append(" AND DEALER_CODE = ?"); 
        queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append(" AND D_KEY = ?"); 
        queryParam.add(CommonConstants.D_KEY);
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }

    @Override
    public String addPartStock(Map partInfoDTO) throws ServiceBizException {
        TmPartInfoPO partInfo = new TmPartInfoPO();
        TmPartStockPO partstock = new TmPartStockPO();
        partstock=this.setPartStocksPO(partstock,partInfo, partInfoDTO);
        StringBuilder sb= new StringBuilder("select * from tm_part_stock where PART_NO=?");
        List<String> params = new ArrayList<String>();
        params.add(partstock.getString("PART_NO"));
        List<Map> list = DAOUtil.findAll(sb.toString(), params);
        if(!CommonUtils.isNullOrEmpty(list)){
            throw new ServiceBizException("配件代码已存在,不能重复！");
        }
        if (!StringUtils.isNullOrEmpty(partstock.getString("DOWN_TAG"))) {
            if ((partstock.getString("DOWN_TAG")).equals("OEMK")) {
                StringBuilder sqlsb= new StringBuilder("select * from tm_part_info where PART_NO=? and DOWN_TAG=12781001");
                List<String> paramss = new ArrayList<String>();
                params.add(partstock.getString("PART_NO"));
                List<Map> lists = DAOUtil.findAll(sqlsb.toString(), paramss);
                if(CommonUtils.isNullOrEmpty(lists)){
                    throw new ServiceBizException("非OEM配件不允许入OEM仓库!");
                }
            }
        }
        partstock.saveIt();
        return partstock.getString("PART_NO");
    }

    @Override
    public PageInfoDto queryPartSn(String id, String ids) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select dealer_code,part_no,storage_code from Tm_Part_Back where part_no=? and storage_code=?");
        list.add(id);
        list.add(ids);
        return DAOUtil.pageQuery(sql.toString(), list);
    }
    
}
