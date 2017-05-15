
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartInfoServiceImpl.java
*
* @Author : xukl
*
* @Date : 2016年7月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月5日    xukl    1.0
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

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO;

/**
* 配件基础信息实现类
* @author xukl
* @date 2016年7月5日
*/
@Service
@SuppressWarnings("rawtypes")
public class PartInfoServiceImpl implements PartInfoService{
    
    
    /**
     * 根据id查询配件信息
    *  @author xukl
    * @date 2016年8月2日
    * @param id
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#getPartInfoById(java.lang.Long)
    */
    	
    @Override
    public TmPartInfoPO getPartInfoById(String id) throws ServiceBizException{
        return TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
    }
    
    
    	
    
    /**
     * 查询
    *  @author xukl
    * @date 2016年7月12日
    * @param queryParam
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#queryPartInfos(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryPartInfos(Map<String,String> queryParam) throws ServiceBizException{

        StringBuffer sb = new StringBuffer("SELECT DEALER_CODE,BRAND,PART_NO,\n");
        		sb.append(" PART_NAME,PART_NAME_EN,SPELL_CODE,PART_GROUP_CODE,UNIT_NAME,\n");
        		sb.append(" UNIT_CODE,OPTION_RELATION,OPTION_NO,SERIES,PART_MODEL_GROUP_CODE_SET\n");
        		sb.append("	QUANTITY_PER_CAR,NODE_PRICE,PLAN_PRICE,LIMIT_PRICE,OEM_LIMIT_PRICE,\n");
        		sb.append(" REGULAR_PRICE,URGENT_PRICE,INSTRUCT_PRICE,INSURANCE_PRICE,REMARK,MAX_STOCK,MIN_STOCK,\n");
        		sb.append(" LEAD_TIME,PROVIDER_CODE,PART_STATUS,IS_UNSAFE,MIN_PACKAGE,ORDER_TYPE,PRODUCTING_AREA,\n");
        		sb.append(" MAIN_ORDER_TYPE,PART_PRODUCT_CODE,PART_MAIN_TYPE,FROM_ENTITY,IS_FREEZE,DOWN_TAG,ORI_PRO_CODE,\n");
        		sb.append(" ABC_TYPE,IS_THINGS,IS_OIL,IS_SPECIAL,BASE_WARP,APPLY_YEAR,IS_CAL_BASE,\n");
        		sb.append(" BO_FLAG,IS_ACC,SUB_CATEGORY_CODE,BIG_CATEGORY_CODE,SAFE_STOCK,IS_STORAGE_SALE,D_KEY,\n");
        		sb.append(" THD_CATEGORY_CODE,CREATED_BY,CREATED_AT,UPDATED_BY,UPDATED_AT,OEM_TAG,VER,\n");
        		sb.append(" QAN_RULES,SUBMIT_TIME,SALE_CODE,IS_BACK,IS_EXEMPT_C,GOODS_BRANDS,OEM_NO,\n");
        		sb.append(" PART_INFIX,DDCN_UPDATE_DATE,PART_INFIX_NAME,MIN_LIMIT_PRICE,ACC_MODE,PROVIDER_NAME,MAINTAIN_PRICE,\n");
        		sb.append(" IS_C_PURCHASE,IS_C_SALE,IS_MOP,IS_SJV,LIMIT_NO,PART_TYPE,\n");
        		sb.append(" IS_COMMON_IDENTITY,PART_VEHICLE_MODEL,PART_PROPERTY,REPORT_WAY");
        //是否显示索赔价
        if (!StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getPurchase())) {
        	if((boolean)FrameworkUtil.getLoginInfo().getPurchase().get("13131002")==true){
        		sb.append(" CLAIM_PRICE,\n");
        	}
		}
        sb.append(" FROM tm_part_info \n");
//        sb.append(" LEFT JOIN tm_brand tb ON tpi.BRAND = tb.BRAND_CODE and tb.DEALER_CODE = tpi.DEALER_CODE\n");
        sb.append(" WHERE 1 = 1\n");
         
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
            sb.append(" and BRAND like ?");
            params.add("%"+queryParam.get("brand")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))){
            sb.append(" and PART_GROUP_CODE like ?");
            params.add("%"+queryParam.get("partGroupCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
            sb.append(" and PART_NO like ?");
            params.add("%"+queryParam.get("partCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))){
            sb.append(" and SPELL_CODE like ?");
            params.add("%"+queryParam.get("spellCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))){
            sb.append(" and PART_GROUP_CODE like ?");
            params.add("%"+Integer.parseInt(queryParam.get("partGroupCode"))+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partInfix"))){
            sb.append(" and PART_INFIX like ?");
            params.add("%"+queryParam.get("partInfix")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partInfixName"))){
            sb.append(" and PART_INFIX_NAME like ?");
            params.add("%"+queryParam.get("partInfixName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("is_mop"))){
            sb.append(" and IS_MOP like ?");
            params.add("%"+queryParam.get("is_mop")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("is_sjv"))){
            sb.append(" and IS_SJV = ?");
            params.add(queryParam.get("is_sjv"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("oem"))){
            sb.append(" and OEM_TAG = ?");
            params.add(queryParam.get("oem"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("part_model_group_code_set"))){
            sb.append(" and PART_MODEL_GROUP_CODE_SET like ?");
            params.add("%"+queryParam.get("part_model_group_code_set")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("begin"))&&!StringUtils.isNullOrEmpty(queryParam.get("end"))) {
        	Utility.sqlToDate(sb, queryParam.get("begin"), queryParam.get("end"), "CREATED_AT",null);
		}
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }
    
    
    /**
     * 新增
    *  @author xukl
    * @date 2016年7月12日
    * @param partInfoDTO
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#addPartInfo(com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO)
    */
    	
    @Override
    public String addPartInfo(PartInfoDTO partInfoDTO) throws ServiceBizException{
        TmPartInfoPO partInfo = new TmPartInfoPO();
        partInfo=this.setPartInfoPO(partInfo, partInfoDTO);
        StringBuilder sb= new StringBuilder("select * from tm_part_info where PART_NO=?");
        List<String> params = new ArrayList<String>();
        params.add(partInfo.getString("PART_NO"));
        List<Map> list = DAOUtil.findAll(sb.toString(), params);
        if(!CommonUtils.isNullOrEmpty(list)){
            throw new ServiceBizException("配件代码已存在,不能重复！");
        }
        
        StringBuilder sbbrand= new StringBuilder("select * from tm_brand where BRAND_CODE = ?");
        List<String> param = new ArrayList<String>();
        param.add(partInfo.getString("BRAND"));
        if(CommonUtils.isNullOrEmpty( DAOUtil.findAll(sbbrand.toString(), param))){
            throw new ServiceBizException("不存在此品牌！");
        }
        partInfo.saveIt();
        return partInfo.getString("PART_NO");
    }
    
    /**
     * 修改
    *  @author xukl
    * @date 2016年7月13日
    * @param id
    * @param partInfoDTO
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#modifyPartInfo(java.lang.Long, com.yonyou.dms.part.domains.DTO.basedata.PartInfoDTO)
    */
    	
    @Override
    public void modifyPartInfo(String id,PartInfoDTO partInfoDTO) throws ServiceBizException {
        TmPartInfoPO partInfo = TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        partInfo=this.setPartInfoPO(partInfo, partInfoDTO);
        partInfo.saveIt();
    }
    
    /**
    * @author xukl
    * @date 2016年7月13日
    * @param partInfo
    * @param partInfoDTO
    * @return
    */
    private TmPartInfoPO setPartInfoPO(TmPartInfoPO partInfo,PartInfoDTO partInfoDTO ) throws ServiceBizException{
        partInfo.setString("BRAND",partInfoDTO.getBrand());
        partInfo.setString("PART_GROUP_CODE",partInfoDTO.getPartGroupCode());
        partInfo.setString("PART_NO",partInfoDTO.getPartNo());
        partInfo.setString("PART_NAME",partInfoDTO.getPartName());
        partInfo.setString("PART_INFIX",partInfoDTO.getPartInfix());
        partInfo.setString("SPELL_CODE",partInfoDTO.getSpellCode());
        partInfo.setString("PART_INFIX_NAME",partInfoDTO.getPartInfixName());
        partInfo.setInteger("PART_MAIN_TYPE",partInfoDTO.getPartMainType());
        partInfo.setString("UNIT_CODE",partInfoDTO.getUnitCode());
        partInfo.setString("PART_MODEL_GROUP_CODE_SET",partInfoDTO.getPartModelGroupCodeSet());
        partInfo.setDouble("MIN_PACKAGE",partInfoDTO.getMinPackage());
        partInfo.setString("QUANTITY_PER_CAR",partInfoDTO.getQuantityPerCar());//单车用量
        partInfo.setDouble("INSTRUCT_PRICE",partInfoDTO.getInstructPrice());//建议售价
        partInfo.setDouble("LIMIT_PRICE",partInfoDTO.getLimitPrice());//销售限价
        partInfo.setString("IS_UNSAFE",partInfoDTO.getIsUnsafe());//是否危险品
        partInfo.setString("PART_STATUS",partInfoDTO.getPartStatus());//是否停用
        partInfo.setString("REMARK",partInfoDTO.getRemark());//备注
        partInfo.setString("OEM_TAG",DictCodeConstants.STATUS_IS_NOT);//是否OEM为否
        
        return partInfo;
    }
    
    
    /**
     * 删除配件信息
    *  @author xukl
    * @date 2016年7月13日
    * @param id
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#deletePartInfoById(java.lang.Long)
    */
    	
    @Override
    public void deletePartInfoById(String id) throws ServiceBizException{
        TmPartInfoPO partinfoPO = TmPartInfoPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        partinfoPO.delete();
    }
    
    /**
    *  @author xukl
    * @date 2016年7月22日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#queryPartInfoForExport(java.util.Map)
    */
    	
    @Override
    public List<Map> queryPartInfoForExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql =getSQL(queryParam, params);
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
    
    /**
    * 封装sql
    * @author xukl
    * @date 2016年8月2日
    * @param queryParam
    * @param params
    * @return
    */
    	
    private String getSQL(Map<String,String> queryParam,List<Object> params)throws ServiceBizException{

        StringBuilder sb = new StringBuilder("SELECT * FROM tm_part_info t  WHERE 1=1");
        if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
            sb.append(" and BRAND = ?");
            params.add(queryParam.get("brand"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
            sb.append(" and PART_NO like ?");
            params.add("%"+queryParam.get("partCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))){
            sb.append(" and SPELL_CODE like ?");
            params.add("%"+queryParam.get("spellCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))){
            sb.append(" and PART_GROUP_CODE = ?");
            params.add(queryParam.get("partGroupCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partInfix"))){
            sb.append(" and PART_INFIX like ?");
            params.add("%"+queryParam.get("partInfix")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partInfixName"))){
            sb.append(" and PART_INFIX_NAME like ?");
            params.add("%"+queryParam.get("partInfixName")+"%");
        }
        return sb.toString();
    }
    
    /**
     * 采购入库时查询配件信息
    *  @author xukl
    * @date 2016年8月4日
    * @param queryParam
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */
    	
    @Override
    public PageInfoDto qryPartInfos(Map<String,String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuilder sb = new StringBuilder("SELECT tpi.DEALER_CODE, tpi.PART_NO, tpi.PART_NAME, tps.STORAGE_CODE, tps.STORAGE_POSITION_CODE, tps.STOCK_QUANTITY, tpi.PART_GROUP_CODE, tps.TAX_LATEST_PRICE, tps.COST_PRICE, tpi.PART_STATUS, tpi.UNIT,");
        sb.append(" ts.STORAGE_NAME, tps.SALES_PRICE, tps.CLAIM_PRICE, tps.ADVICE_SALE_PRICE, tps.LIMIT_PRICE, tpi.PART_NO AS PART_NO,(tps.STOCK_QUANTITY - tps.LOCKED_QUANTITY + tps.BORROW_QUANTITY - tps.LEND_QUANTITY ) AS ENABLED_STORE");
        sb.append(" FROM tm_part_info tpi LEFT JOIN tt_part_stock tps ON tpi.PART_NO = tps.PART_NO AND (tpi.DEALER_CODE = tps.DEALER_CODE or tpi.DEALER_CODE = '-1') AND tps.STORAGE_CODE IN (");
        sb .append(loginInfo.getPurchaseDepot()).append(") LEFT JOIN TM_STORE ts ON tps.STORAGE_CODE = ts.STORAGE_CODE AND tps.DEALER_CODE = ts.DEALER_CODE  WHERE 1 = 1 ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
            sb.append(" and tps.STORAGE_CODE = ?");
            params.add(queryParam.get("storageCode"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partNo"))){
            sb.append(" and tpi.PART_NO like ?");
            params.add("%"+queryParam.get("partNo")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and tpi.PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("stockQuantity"))){
            sb.append(" and tps.STOCK_QUANTITY > ?");
            params.add(Double.valueOf(queryParam.get("stockQuantity")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))){
            sb.append(" and tpi.PART_GROUP_CODE = ?");
            params.add(queryParam.get("partGroupCode"));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    /**
     * 根据配件代码查询配件基本信息
    *  @author jcsi
    * @date 2016年8月4日
    * @param partCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */

	@Override
	public List<Map> searchByPartCode(String partNo) throws ServiceBizException {
		StringBuilder sb=new StringBuilder("select DEALER_CODE,PART_NO from tm_part_info where PART_NO=? ");
		List<Object> param=new ArrayList<Object>();
		param.add(partNo);
		return DAOUtil.findAll(sb.toString(), param);
	}
	
	/**
     * 查询配件类别下拉框
    *  @author dingchaoyu
    * @date 
    * @param partCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */
	@Override
	public List<Map> queryUnitCode(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select * from tm_unit where 1=1 ");
		return Base.findAll(sqlSb.toString());
	}
	
	/**
     * 查询配件车型组下拉框
    *  @author dingchaoyu
    * @date 
    * @param partCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */
	@Override
    public List<Map> queryPartGroupCodes(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer("select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME from tm_part_model_group t where 1=1 ");
    	return Base.findAll(sqlSb.toString());
    }

	/**
     * 查询配件车型组
    *  @author dingchaoyu
    * @date 
    * @param partCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */

	@Override
	public PageInfoDto queryPartGroupCode(Map<String, String> queryParam) throws ServiceBizException {
		String sqlSb = "select DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME from tm_part_model_group t where 1=1 ";
		return DAOUtil.pageQuery(sqlSb, null);
	}
	

	/**
     * 查询配件车系车型
    *  @author dingchaoyu
    * @date 
    * @param partCode
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.basedata.PartInfoService#qryPartInfos(java.util.Map)
    */

	@Override
	public PageInfoDto queryModel(Map<String, String> queryParam) throws ServiceBizException {
		String sqlSb = "select DEALER_CODE,BRAND_CODE,SERIES_CODE,MODEL_CODE,MODEL_NAME from tm_model t where 1=1 ";
		return DAOUtil.pageQuery(sqlSb, null);
	}




    @Override
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql =getObjectSQL(queryParam, params);
        List<Map> resultList = DAOUtil.findAll(sql,params);
        return resultList;
    }
    
    /**
     * 封装sql
     * @author xukl
     * @date 2016年8月2日
     * @param queryParam
     * @param params
     * @return
     */
         
     private String getObjectSQL(Map<String,Object> queryParam,List<Object> params)throws ServiceBizException{

         StringBuilder sb = new StringBuilder("SELECT dealer_code,part_no,part_name,down_tag,d_key FROM tm_part_info WHERE 1=1 ");
         if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
             sb.append(" and BRAND = ?");
             params.add(queryParam.get("brand"));
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
             sb.append(" and PART_NO like ?");
             params.add("%"+queryParam.get("partCode")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("downTag"))){
             sb.append(" and DOWN_TAG = ?");
             params.add(queryParam.get("downTag"));
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("dKey"))){
             sb.append(" and D_KEY = ?");
             params.add(queryParam.get("dKey"));
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
             sb.append(" and PART_NAME like ?");
             params.add("%"+queryParam.get("partName")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))){
             sb.append(" and SPELL_CODE like ?");
             params.add("%"+queryParam.get("spellCode")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))){
             sb.append(" and PART_GROUP_CODE = ?");
             params.add(queryParam.get("partGroupCode"));
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("partInfix"))){
             sb.append(" and PART_INFIX like ?");
             params.add("%"+queryParam.get("partInfix")+"%");
         }
         if(!StringUtils.isNullOrEmpty(queryParam.get("partInfixName"))){
             sb.append(" and PART_INFIX_NAME like ?");
             params.add("%"+queryParam.get("partInfixName")+"%");
         }
         return sb.toString();
     }

}
