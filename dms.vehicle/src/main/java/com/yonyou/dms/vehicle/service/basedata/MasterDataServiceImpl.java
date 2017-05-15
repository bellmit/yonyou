
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : MasterDataServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年9月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月8日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO;

/**
 * 整车产品信息实现类
 * @author DuPengXin
 * @date 2016年9月8日
 */
@Service
@SuppressWarnings("rawtypes")
public class MasterDataServiceImpl implements MasterDataService{
    @Autowired
    private OperateLogService operateLogService;
    /**
     *查询整车产品信息
     * @author DuPengXin
     * @date 2016年9月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.basedata.MasterDataService#QueryMasterData(java.util.Map)
     */

    @Override
    public PageInfoDto QueryMasterData(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sq = new StringBuffer();
        String isChangeRule=queryParam.get("IS_CHANGE_RILE");
        String isColor=queryParam.get("IS_COLOR");
        if(isChangeRule != null && isChangeRule.equals(DictCodeConstants.IS_YES)){
        sq.append(" SELECT  distinct CASE WHEN (B.SALES_CONSULTANT_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.SALES_CONSULTANT_PRICE ELSE B.SALES_CONSULTANT_PRICE END SALES_CONSULTANT_PRICE," );
        sq.append( " CASE WHEN (B.MANAGER_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.MANAGER_PRICE ELSE B.MANAGER_PRICE END MANAGER_PRICE," );
        sq.append( " CASE WHEN (B.GENERAL_MANAGER_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.GENERAL_MANAGER_PRICE ELSE B.GENERAL_MANAGER_PRICE END GENERAL_MANAGER_PRICE," );
        sq.append( " CASE WHEN (B.DISCOUNT_RATE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.MININUM_PRICE ELSE B.DISCOUNT_RATE END DISCOUNT_RATE," );
        sq.append( " A.WHOLESALE_DIRECTIVE_PRICE,A.PRODUCT_CODE,A.PRODUCT_NAME,A.YEAR_MODEL,A.EXHAUST_QUANTITY,A.OIL_TYPE,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE, ");
        sq.append( " A.CONFIG_CODE,A.COLOR_CODE,A.PRODUCT_STATUS,A.PRODUCT_TYPE,A.OEM_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE,B.DEALER_CODE,");
        sq.append( " A.LATEST_PURCHASE_PRICE,A.ENTER_DATE,EXEUNT_DATE,A.OEM_TAG,A.IS_VALID,A.REMARK,A.MININUM_PRICE " );                          
        sq.append( " FROM TT_SALES_ORDER B LEFT JOIN ( "+CommonConstants.VM_VS_PRODUCT +") A ON A.DEALER_CODE = B.DEALER_CODE AND A.PRODUCT_CODE = B.PRODUCT_CODE WHERE A.D_KEY=" + DictCodeConstants.D_KEY + " ");       
        Utility.sqlToLike(sq, queryParam.get("productCode"), "PRODUCT_CODE", "B");
        
        } else{                   
            if (isColor == null ){
                
                sq.append(" SELECT A.WHOLESALE_DIRECTIVE_PRICE,A.PRODUCT_CODE,A.PRODUCT_NAME,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE,a.MANAGER_PRICE,a.GENERAL_MANAGER_PRICE, ");
                		sq.append(  " A.CONFIG_CODE,A.COLOR_CODE,A.PRODUCT_STATUS,A.YEAR_MODEL,A.EXHAUST_QUANTITY,A.OIL_TYPE,A.PRODUCT_TYPE,A.OEM_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE AS VEHICLE_PRICE,");
						sq.append(  " A.LATEST_PURCHASE_PRICE,A.ENTER_DATE,EXEUNT_DATE,A.OEM_TAG,A.IS_VALID,REMARK,B.COLOR_NAME,A.MININUM_PRICE,A.DISCOUNT_RATE,A.DEALER_CODE, ");
						sq.append(  " D.BRAND_NAME,M.MODEL_NAME,S.SERIES_NAME,C.CONFIG_NAME," );
						sq.append(  " A.SALES_CONSULTANT_PRICE, A.SEC_SALES_CONSULTANT_PRICE, A.SEC_MANAGER_PRICE, A.SEC_GENERAL_MANAGER_PRICE, A.IS_MUST_PDI  ");
						sq.append(  " FROM ( "+CommonConstants.VM_VS_PRODUCT +") A ");
						sq.append(  " left join  ( "+CommonConstants.VM_COLOR +")  B on A.COLOR_CODE= B.COLOR_CODE and A.DEALER_CODE = B.DEALER_CODE ");
						sq.append(  " left join  ( "+CommonConstants.VM_BRAND +") D on A.BRAND_CODE =D.BRAND_CODE and A.DEALER_CODE = D.DEALER_CODE ");
						sq.append(  " left join  ( "+CommonConstants.VM_SERIES +") S on A.SERIES_CODE =S.SERIES_CODE and S.BRAND_CODE = D.BRAND_CODE and D.DEALER_CODE = S.DEALER_CODE ");
						sq.append(  " left join  ( "+CommonConstants.VM_MODEL +") M on A.MODEL_CODE =M.MODEL_CODE AND M.BRAND_CODE=S.BRAND_CODE AND M.SERIES_CODE =S.SERIES_CODE and S.DEALER_CODE = M.DEALER_CODE ");
						sq.append(  " left join  ( "+CommonConstants.VM_CONFIGURATION +") C on A.CONFIG_CODE =C.CONFIG_CODE AND C.BRAND_CODE=M.BRAND_CODE AND C.SERIES_CODE=M.SERIES_CODE AND C.MODEL_CODE=M.MODEL_CODE and M.DEALER_CODE = C.DEALER_CODE ");
						sq.append(  " WHERE  A.D_KEY=");
						sq.append(  CommonConstants.D_KEY + " ");          
						            }else{
						                sq.append(" SELECT  distinct A.COLOR_CODE,B.COLOR_NAME,A.DEALER_CODE "    );                                  
										sq.append(  " FROM ( "+CommonConstants.VM_VS_PRODUCT +") A left join  ( "+CommonConstants.VM_COLOR +")  B on A.COLOR_CODE= B.COLOR_CODE and A.DEALER_CODE = B.DEALER_CODE  WHERE   A.D_KEY=");
										sq.append(  CommonConstants.D_KEY + " ");          
						            }
            
            Utility.sqlToEquals(sq, queryParam.get("brandName"), "BRAND_CODE", "A"); 
            Utility.sqlToEquals(sq, queryParam.get("seriesName"), "SERIES_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("modelName"), "MODEL_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("configName"), "CONFIG_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("color"), "COLOR_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("productStatus"), "PRODUCT_STATUS", "A");
            Utility.sqlToEquals(sq, queryParam.get("isValid"), "IS_VALID", "A");
            Utility.sqlToLike(sq, queryParam.get("productCode"), "PRODUCT_CODE", "A");
            Utility.sqlToLike(sq, queryParam.get("productName"), "PRODUCT_NAME", "A");
            
        }
        
        
      
        
        
        List<Object> params = new ArrayList<Object>();

        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sq.toString(),params);
        return pageInfoDto;
    }

    /**
     * 新增
     * @author DuPengXin
     * @date 2016年9月9日
     * @param masterdatadto
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.basedata.MasterDataService#addMasterData(com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO)
     */

    @Override
    public Long addMasterData(MasterDataDTO masterdatadto) throws ServiceBizException {
        VsProductPO md = new VsProductPO();
        if(SearchMasterData(masterdatadto.getProductCode(),(masterdatadto.getProductName()))){
        setMasterData(md,masterdatadto);
        md.saveIt();
        return md.getLongId();
        }else{
            throw new ServiceBizException("该产品代码或名称已存在");
        }
    }

    
    /**
    * 查询是否有相同数据
    * @author DuPengXin
    * @date 2016年10月21日
    * @param productCode
    * @param productName
    * @return
    */
    	
    private boolean SearchMasterData(String productCode, String productName) {
        StringBuilder sb=new StringBuilder("select tvp.PRODUCT_CODE,tvp.DEALER_CODE,tvp.PRODUCT_CODE,tvp.PRODUCT_NAME from tm_vs_product tvp where tvp.PRODUCT_CODE=? or tvp.PRODUCT_NAME=?");
        List<Object> param=new ArrayList<Object>();
        param.add(productCode);
        param.add(productName);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()==0){
            return true;
        }
        return false;
    }

    /**
     * 设置对象属性
     * @author DuPengXin
     * @date 2016年9月9日
     * @param md
     * @param masterdatadto
     */

    private void setMasterData(VsProductPO md, MasterDataDTO masterdatadto) {
        md.setString("PRODUCT_CODE", masterdatadto.getProductCode());//产品代码
        md.setString("PRODUCT_NAME", masterdatadto.getProductName());//产品名称
        md.setString("PRODUCT_STATUS", masterdatadto.getProductStatus());//产品状态
        md.setInteger("PRODUCT_TYPE", masterdatadto.getProductType());//产品类别
        md.setString("BRAND_CODE", masterdatadto.getBrandCode());//品牌
        md.setString("SERIES_CODE", masterdatadto.getSeriesCode());//车系
        md.setString("MODEL_CODE", masterdatadto.getModelCode());//车型
        md.setString("CONFIG_CODE", masterdatadto.getConfigCode());//配置
        md.setString("COLOR_CODE", masterdatadto.getColor());//颜色
        md.setDate("ENTER_DATE", masterdatadto.getEnterDate());//上市日期
        md.setDate("EXEUNT_DATE", masterdatadto.getExeuntDate());//退市日期
        md.setDouble("OEM_DIRECTIVE_PRICE", masterdatadto.getOemDirectivePrice());//车厂指导价
        md.setDouble("DIRECTIVE_PRICE", masterdatadto.getDirectivePrice());//销售指导价
       // md.setInteger("OEM_TAG", masterdatadto.getOemTag());//是否OEM默认为否
        md.setInteger("IS_VALID", masterdatadto.getIsValid());//是否有效
        md.setString("REMARK", masterdatadto.getRemark());//备注
    }

    /**
     * 修改
    * @author DuPengXin
    * @date 2016年9月9日
    * @param id
    * @param masterdatadto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.MasterDataService#modifyMasterData(java.lang.Long, com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO)
    */
    	
    @Override
    public void modifyMasterData(String id,MasterDataDTO masterdatadto) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String ids=masterdatadto.getProductCode();
        VsProductPO md=VsProductPO.findByCompositeKeys(loginInfo.getDealerCode(),ids);
       
        if(SearchProductName(masterdatadto.getProductName(),ids)){
            throw new ServiceBizException("该产品名称已存在");
        }
        this.setMasterData(md,masterdatadto);
        md.saveIt();
    }
    
    
    /**
    * 查询是否有相同的数据
    * @author DuPengXin
    * @date 2016年10月21日
    * @param productName
    * @return
    */
    	
    private boolean SearchProductName(String productName,String ids) {
        StringBuilder sb=new StringBuilder("SELECT  tvp.DEALER_CODE, tvp.PRODUCT_CODE, tvp.PRODUCT_NAME FROM tm_vs_product tvp WHERE tvp.PRODUCT_CODE=? AND tvp.PRODUCT_NAME =? ");
        List<Object> param=new ArrayList<Object>();
        param.add(productName);
        param.add(ids);
        List<Map> map=DAOUtil.findAll(sb.toString(), param);
        if(map.size()>0){
            return true;
        }
        return false;
    }

    /**
    * 根据id查询
    * @author DuPengXin
    * @date 2016年9月9日
    * @param id
    * @return
    * @throws ServiceBizException
    */
    
    @Override	
    public List<Map> findById(String id)throws ServiceBizException{
        StringBuffer sql = new StringBuffer();
         sql.append(" SELECT tvp.DEALER_CODE,tvp.PRODUCT_CODE, tvp.DIRECTIVE_PRICE as VEHICLE_PRICE,tvp.DEALER_CODE, tvp.PRODUCT_CODE, tvp.PRODUCT_NAME,tvp.OEM_TAG, tvp.PRODUCT_STATUS,tvp.PRODUCT_TYPE,tvp.IS_VALID,tvp.OEM_DIRECTIVE_PRICE,");
         sql.append( " tvp.ENTER_DATE,tvp.EXEUNT_DATE, tvp.BRAND_CODE,tvp.MODEL_CODE,tvp.CONFIG_CODE,tvp.SERIES_CODE,tvp.COLOR_CODE,tvp.REMARK FROM tm_vs_product tvp ");
         sql.append( " WHERE  tvp.PRODUCT_CODE LIKE '"+id+"'");
        //Utility.sqlToLike(sql, id, "PRODUCT_CODE", "tvp");
        List<Object> queryParam=new ArrayList<Object>();
        queryParam.add(id);
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
        return result;
    }

    /**
     * 查询导出数据
    * @author DuPengXin
    * @date 2016年9月14日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.MasterDataService#queryMasterDataExport(java.util.Map)
    */
    	
    @SuppressWarnings({ "unchecked", "unused" })
    @Override
    public List<Map> queryMasterDataExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sq = new StringBuffer();
        String isChangeRule=queryParam.get("IS_CHANGE_RILE");
        String isColor=queryParam.get("IS_COLOR");
        if(isChangeRule != null && isChangeRule.equals(DictCodeConstants.IS_YES)){
        sq.append(" SELECT  distinct CASE WHEN (B.SALES_CONSULTANT_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.SALES_CONSULTANT_PRICE ELSE B.SALES_CONSULTANT_PRICE END SALES_CONSULTANT_PRICE," );
        sq.append(" CASE WHEN (B.MANAGER_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.MANAGER_PRICE ELSE B.MANAGER_PRICE END MANAGER_PRICE," );
        sq.append( " CASE WHEN (B.GENERAL_MANAGER_PRICE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.GENERAL_MANAGER_PRICE ELSE B.GENERAL_MANAGER_PRICE END GENERAL_MANAGER_PRICE," );
        sq.append( " CASE WHEN (B.DISCOUNT_RATE = 0 AND B.IS_FIRSET_SUMMIT = "+DictCodeConstants.IS_NOT+") THEN A.MININUM_PRICE ELSE B.DISCOUNT_RATE END DISCOUNT_RATE," );
        sq.append( " A.WHOLESALE_DIRECTIVE_PRICE,A.PRODUCT_CODE,A.PRODUCT_NAME,A.YEAR_MODEL,A.EXHAUST_QUANTITY,A.OIL_TYPE,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE, ");
        sq.append( " A.CONFIG_CODE,A.COLOR_CODE,A.PRODUCT_STATUS,A.PRODUCT_TYPE,A.OEM_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE,B.DEALER_CODE,");
        sq.append( " A.LATEST_PURCHASE_PRICE,A.ENTER_DATE,EXEUNT_DATE,A.OEM_TAG,A.IS_VALID,A.REMARK,A.MININUM_PRICE " );                                
        sq.append( " FROM TT_SALES_ORDER B LEFT JOIN ( "+CommonConstants.VM_VS_PRODUCT +") A ON A.DEALER_CODE = B.DEALER_CODE AND A.PRODUCT_CODE = B.PRODUCT_CODE WHERE A.D_KEY=" + DictCodeConstants.D_KEY + " ");       
        Utility.sqlToLike(sq, queryParam.get("productCode"), "PRODUCT_CODE", "B");
        
        } else{                   
            if (isColor == null ){
                
                sq.append(" SELECT A.WHOLESALE_DIRECTIVE_PRICE,A.PRODUCT_CODE,A.PRODUCT_NAME,A.BRAND_CODE,A.SERIES_CODE,A.MODEL_CODE,a.MANAGER_PRICE,a.GENERAL_MANAGER_PRICE, ");
				sq.append( " A.CONFIG_CODE,A.COLOR_CODE,A.PRODUCT_STATUS,A.YEAR_MODEL,A.EXHAUST_QUANTITY,A.OIL_TYPE,A.PRODUCT_TYPE,A.OEM_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE AS VEHICLE_PRICE,");
				sq.append( " A.LATEST_PURCHASE_PRICE,A.ENTER_DATE,EXEUNT_DATE,A.OEM_TAG,A.IS_VALID,REMARK,B.COLOR_NAME,A.MININUM_PRICE,A.DISCOUNT_RATE,A.DEALER_CODE, ");
				sq.append( " D.BRAND_NAME,M.MODEL_NAME,S.SERIES_NAME,C.CONFIG_NAME," );
				sq.append( " A.SALES_CONSULTANT_PRICE, A.SEC_SALES_CONSULTANT_PRICE, A.SEC_MANAGER_PRICE, A.SEC_GENERAL_MANAGER_PRICE, A.IS_MUST_PDI  ");
				sq.append( " FROM ( "+CommonConstants.VM_VS_PRODUCT +") A ");
				sq.append( " left join  ( "+CommonConstants.VM_COLOR +")  B on A.COLOR_CODE= B.COLOR_CODE and A.DEALER_CODE = B.DEALER_CODE ");
				sq.append(  " left join  ( "+CommonConstants.VM_BRAND +") D on A.BRAND_CODE =D.BRAND_CODE and A.DEALER_CODE = D.DEALER_CODE ");
				sq.append(  " left join  ( "+CommonConstants.VM_SERIES +") S on A.SERIES_CODE =S.SERIES_CODE and S.BRAND_CODE = D.BRAND_CODE and D.DEALER_CODE = S.DEALER_CODE ");
				sq.append(  " left join  ( "+CommonConstants.VM_MODEL +") M on A.MODEL_CODE =M.MODEL_CODE AND M.BRAND_CODE=S.BRAND_CODE AND M.SERIES_CODE =S.SERIES_CODE and S.DEALER_CODE = M.DEALER_CODE ");
				sq.append(  " left join  ( "+CommonConstants.VM_CONFIGURATION +") C on A.CONFIG_CODE =C.CONFIG_CODE AND C.BRAND_CODE=M.BRAND_CODE AND C.SERIES_CODE=M.SERIES_CODE AND C.MODEL_CODE=M.MODEL_CODE and M.DEALER_CODE = C.DEALER_CODE ");
				sq.append( " WHERE  A.D_KEY=");
				sq.append(  CommonConstants.D_KEY + " ");          
            }else{
                sq.append(" SELECT  distinct A.COLOR_CODE,B.COLOR_NAME,A.DEALER_CODE "  );                                    
				sq.append( " FROM ( "+CommonConstants.VM_VS_PRODUCT +") A left join  ( "+CommonConstants.VM_COLOR +")  B on A.COLOR_CODE= B.COLOR_CODE and A.DEALER_CODE = B.DEALER_CODE  WHERE   A.D_KEY=");
				sq.append(  CommonConstants.D_KEY + " ");          
            }
            Utility.sqlToEquals(sq, queryParam.get("brandName"), "BRAND_CODE", "A"); 
            Utility.sqlToEquals(sq, queryParam.get("seriesName"), "SERIES_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("modelName"), "MODEL_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("configName"), "CONFIG_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("color"), "COLOR_CODE", "A");
            Utility.sqlToEquals(sq, queryParam.get("productStatus"), "PRODUCT_STATUS", "A");
            Utility.sqlToEquals(sq, queryParam.get("isValid"), "IS_VALID", "A");
            Utility.sqlToLike(sq, queryParam.get("productCode"), "PRODUCT_CODE", "A");
            Utility.sqlToLike(sq, queryParam.get("productName"), "PRODUCT_NAME", "A");
            
        }
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class); 
        List<Map> list  = null;
        List<Object> queryList = new ArrayList<Object>();
        list = DAOUtil.findAll(sq.toString(), queryList);
       for (Map map : list) {
           if (map.get("PRODUCT_TYPE") != null && map.get("PRODUCT_TYPE") != "") {
                if (Integer.parseInt(map.get("PRODUCT_TYPE").toString()) == 10381001) {
                    map.put("PRODUCT_TYPE", "正常库存车");
                } else if (Integer.parseInt(map.get("PRODUCT_TYPE").toString()) == 10381002) {
                    map.put("PRODUCT_TYPE", "促销车");
                }else if (Integer.parseInt(map.get("PRODUCT_TYPE").toString()) == 10381003) {
                    map.put("PRODUCT_TYPE", "展车");
                }else if (Integer.parseInt(map.get("PRODUCT_TYPE").toString()) == 10381004) {
                    map.put("PRODUCT_TYPE", "改装车");
                }                        
            }
            
           
            if (map.get("IS_VALID") != null && map.get("IS_VALID") != "") {
                if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781001) {
                    map.put("IS_VALID", "是");
                } else if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781002) {
                    map.put("IS_VALID", "否");
                }
            }
            if (map.get("OEM_TAG") != null && map.get("OEM_TAG") != "") {
                if (Integer.parseInt(map.get("OEM_TAG").toString()) == 12781001) {
                    map.put("OEM_TAG", "是");
                } else if (Integer.parseInt(map.get("OEM_TAG").toString()) == 12781002) {
                    map.put("OEM_TAG", "否");
                }
            }
            if (map.get("IS_MUST_PDI") != null && map.get("IS_MUST_PDI") != "") {
                if (Integer.parseInt(map.get("IS_MUST_PDI").toString()) == 12781001) {
                    map.put("IS_MUST_PDI", "是");
                } else if (Integer.parseInt(map.get("IS_MUST_PDI").toString()) == 12781002) {
                    map.put("IS_MUST_PDI", "否");
                }
            }
            if (map.get("OIL_TYPE") != null && map.get("OIL_TYPE") != "") {
                if (Integer.parseInt(map.get("OIL_TYPE").toString()) == 50551001) {
                    map.put("OIL_TYPE", "汽油");
                } else if (Integer.parseInt(map.get("OIL_TYPE").toString()) == 50551002) {
                    map.put("OIL_TYPE", "柴油");
                }
            }
            if (map.get("PRODUCT_STATUS") != null && map.get("PRODUCT_STATUS") != "") {
                if (Integer.parseInt(map.get("PRODUCT_STATUS").toString()) == 13081001) {
                    map.put("PRODUCT_STATUS", "正常");
                } else if (Integer.parseInt(map.get("PRODUCT_STATUS").toString()) == 13081002) {
                    map.put("PRODUCT_STATUS", "退市");
                }
            }
           
            
       }
            OperateLogDto operateLogDto=new OperateLogDto();
            operateLogDto.setOperateContent("整车产品信息导出");
            operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
            operateLogService.writeOperateLog(operateLogDto);
       
         
       
    return list;
    }

    /**
     * 批量修改销售指导价
    * @author DuPengXin
    * @date 2016年9月18日
    * @param masterdatadto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.basedata.MasterDataService#salesPrice(com.yonyou.dms.vehicle.domains.DTO.basedata.MasterDataDTO)
    */
    	
    @Override
    public void salesPrice(MasterDataDTO masterdatadto) throws ServiceBizException {
        String[] ids = masterdatadto.getUserIds().split(",");
        for(int i=0;i<ids.length;i++){
            Long id = Long.parseLong(ids[i]);
            VsProductPO md =VsProductPO.findById(id);
            md.setDouble("DIRECTIVE_PRICE", masterdatadto.getDirectivePrice());
            md.saveIt();
        }
    }
}