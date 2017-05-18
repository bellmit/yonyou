/**
 * 
 */
package com.yonyou.dms.gacfca;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.CLDMS002Dto;
import com.yonyou.dms.common.domains.PO.basedata.ConfigurationPO;
import com.yonyou.dms.common.domains.PO.basedata.TmBrandPo;
import com.yonyou.dms.common.domains.PO.basedata.TmColorPo;
import com.yonyou.dms.common.domains.PO.basedata.TmModelLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TmSeriesPo;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 0 失败  1 成功
* TODO 业务描述：DCS->DMS CLDMS002 车型组主数据（品牌、车系、车型、配置）TM_VS_PRODUCT CLDMS002Dto.java 
* @author xhy
* @date 2017年2月14日
*/
@Service
public class CLDMS002CoudImpl implements CLDMS002Coud {
	final Logger logger = Logger.getLogger(CLDMS002CoudImpl.class);
	/* (non-Javadoc)
	 * @see com.yonyou.dms.gacfca.CLDMS002#performExecute()
	 */
	@SuppressWarnings({ "unused", "rawtypes", "static-access" })
	@Override
	public int getCLDMS002(LinkedList<CLDMS002Dto> voList,String dealerCode) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try{
			
			//判断是否为空,循环操作，根据业务相应的修改数据
			if (voList != null && voList.size() > 0){
				for (int i = 0; i < voList.size(); i++){
					CLDMS002Dto	dto= new CLDMS002Dto();
					dto=(CLDMS002Dto) voList.get(i);
					List<VsProductPO> lists=VsProductPO.findBySQL("select *  from tm_vs_product where DEALER_CODE=? AND PRODUCT_CODE =? ", new Object[]{dealerCode,dto.getBrandCode()});
					Integer val = 0;
					if (dto.getIsValid()!=null && "10011001".equals(dto.getIsValid().toString())){
						val = 12781001;
					}						
					else {
						val = 12781002;
					}
					Integer brandStatus =12781002;
					Integer seriesStatus =12781002;
					Integer modelStatus =12781002;
					if (dto.getBrandStatus()!=null && "10011001".equals(dto.getBrandStatus().toString())){
						brandStatus = 12781001;
					}						
					if (dto.getSeriesStatus()!=null && "10011001".equals(dto.getSeriesStatus().toString())){
						seriesStatus = 12781001;
					}						
					if (dto.getModelStatus()!=null && "10011001".equals(dto.getModelStatus().toString())){
						modelStatus = 12781001;
					}
					
					TmBrandPo brandPO = new TmBrandPo();
					TmSeriesPo seriesPO = new TmSeriesPo();
					TmModelPO modelPO = new TmModelPO();
					
					
					//插入品牌表
					TmBrandPo po = new TmBrandPo();
					/*po.setString("DEALER_CODE", dealerCode);
					po.setString("BRAND_CODE",dto.getBrandCode());*/
					List<Map> list=Base.findAll("SELECT *  FROM TM_BRAND WHERE DEALER_CODE='"+dealerCode+"' AND BRAND_CODE='"+dto.getBrandCode()+"' ");
					if(null != dto.getBrandCode() && !"".equals(dto.getBrandCode())){
						
						if (list != null && list.size()>0){
								String sql = "UPDATE tm_brand SET BRAND_NAME = '"+dto.getBrandName()+"' , OEM_TAG =12781001 , IS_VALID='"+brandStatus+"' , UPDATED_BY ='"+1L+"' WHERE DEALER_CODE='"+dealerCode+"' AND BRAND_CODE='"+dto.getBrandCode()+"' ";
							/*	TmBrandPo.update(" BRAND_NAME=? , OEM_TAG=? , IS_VALID=? , UPDATED_BY=?",
									"DELER_CODE=? AND BRAND_CODE=? ",

									 dto.getBrandName(),12781001,brandStatus,format,1L,dealerCode,dto.getBrandCode());*/
								Base.exec(sql);
							
						}else{


							
							try {
								brandPO.setString("DEALER_CODE", dealerCode);
								brandPO.setString("BRAND_CODE", CommonUtils.checkNull(dto.getBrandCode()));
								brandPO.setString("BRAND_NAME", CommonUtils.checkNull(dto.getBrandName()));
								brandPO.setString("OEM_TAG",12781001 );
								brandPO.setString("IS_VALID",brandStatus);
								//brandPO.setDate("CREATED_AT", Utility.getCurrentDateTime());
								brandPO.setString("CREATED_BY",1L);
								brandPO.insert();
							} catch (Exception e) {
								logger.debug(e);
							}
								}	
							}
						
					//插入车系表
					//TmSeriesPo po1 = new TmSeriesPo();
					/*po1.setString("DEALER_CODE", dealerCode);
					po1.setString("BRAND_CODE",dto.getBrandCode());
					po1.setString("SERIES_CODE",dto.getSeriesCode());*/
				//	po1.setSeriesName(vo.getSeriesName());
//					po1.setIsValid(val);
					List<Map> list1 =Base.findAll(" SELECT * FROM TM_SERIES WHERE DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=? ", new Object[]{dealerCode,dto.getBrandCode(),dto.getSeriesCode()});
					
					if (null != dto.getSeriesCode() && null != dto.getBrandCode() && !"".equals(dto.getBrandCode()) && !"".equals(dto.getSeriesCode())){
					
						if (null != list1 && list1.size()>0){
							String sql = "UPDATE TM_SERIES SET SERIES_NAME = '"+dto.getSeriesName()+"' , OEM_TAG =12781001 , IS_VALID='"+brandStatus+"' , UPDATED_BY ='"+1L+"' WHERE DEALER_CODE='"+dealerCode+"' AND BRAND_CODE='"+dto.getBrandCode()+"' AND SERIES_CODE ='"+dto.getSeriesCode()+"' ";
							/*TmSeriesPo.update(" SERIES_NAME=? , OEM_TAG=? , IS_VALID=? , UPDATED_AT=? , UPDATED_BY=?",
									"DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=?",
									 dto.getSeriesName(),12781001,brandStatus,format,1L,dealerCode,dto.getBrandCode(),dto.getSeriesCode());*/
							Base.exec(sql);
						}else{
							try {
								seriesPO.setString("DEALER_CODE", dealerCode);
								seriesPO.setString("BRAND_CODE",dto.getBrandCode());
								seriesPO.setString("SERIES_CODE",dto.getSeriesCode());
								seriesPO.setString("SERIES_NAME",dto.getSeriesName());
								seriesPO.setString("OEM_TAG",12781001);
								seriesPO.setString("IS_VALID",seriesStatus);
								//seriesPO.setDate("CREATED_AT", Utility.getCurrentDateTime());
								seriesPO.setString("CREATED_BY",1L);
								seriesPO.insert();
							} catch (Exception e) {
								logger.debug(e);
							}
							
							}
//						TM_MODEL_LABOUR  lim 增加 把车型维修项目默认上车系的code	
						//TmModelLabourPO laoldpo =new TmModelLabourPO();
						/*laoldpo.setString("DEALER_CODE", dealerCode);
						laoldpo.setString("MODEL_LABOUR_CODE",dto.getSeriesCode());*/
						List<Map> listlabour = Base.findAll(" SELECT * FROM TM_MODEL_LABOUR WHERE DEALER_CODE=? AND MODEL_LABOUR_CODE=?", new Object[]{dealerCode,dto.getSeriesCode()});
						
						TmModelLabourPO labourpo =new TmModelLabourPO();
						
						if (null != listlabour || listlabour.size()>0){
						/*	TmModelLabourPO.update("DOWN_TAG=? , MODEL_LABOUR_NAME=?, UPDATED_BY=? , UPDATED_AT=? ",
									"DEALER_CODE=? AND MODEL_LABOUR_CODE=?",
									12781001,dto.getSeriesName(), 1L,format,dealerCode,dto.getSeriesCode());*/
							String sql = "UPDATE TM_MODEL_LABOUR SET DOWN_TAG = 12781001  , MODEL_LABOUR_NAME ='"+dto.getSeriesName()+"' , UPDATED_BY ='"+1L+"' WHERE DEALER_CODE='"+dealerCode+"' AND MODEL_LABOUR_CODE='"+dto.getSeriesCode()+"'";
							Base.exec(sql);
						}else {
							labourpo.setLong("DOWN_TAG",12781001);
							labourpo.setString("DEALER_CODE", dealerCode);
							
							labourpo.setString("MODEL_LABOUR_NAME",dto.getSeriesName());
							labourpo.setString("MODEL_LABOUR_CODE",dto.getSeriesCode());
							labourpo.setString("CREATED_BY",1L);
							//labourpo.setDate("CREATED_AT", Utility.getCurrentDateTime());
							labourpo.insert();
							}
			
						}	
					
					//插入车型表
					//TmModelPO po2 = new TmModelPO();
					/*po2.setString("DEALER_CODE", dealerCode);
					po2.setString("BRAND_CODE",dto.getBrandCode());
					po2.setString("SERIES_CODE",dto.getSeriesCode());
					po2.setString("MODEL_CODE", dto.getModelCode());*/
					List<Map> list2 = Base.findAll(" SELECT * FROM TM_MODEL WHERE  DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=? AND MODEL_CODE=?", new Object[]{dealerCode,dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode()});
//				TM_MODEL_LABOUR  lim 增加 把车型维修项目默认上车系的code
					if (null != dto.getSeriesCode() && null != dto.getBrandCode() && null!=dto.getModelCode()
							&& !"".equals(dto.getBrandCode()) && !"".equals(dto.getSeriesCode()) && !"".equals(dto)){
						
						if (null != list2 && list2.size()>0){
							/*TmModelPO.update(" MODEL_LABOUR_CODE=? ,MODEL_NAME=?, OEM_TAG=?,IS_VALID=?,EXHAUST_QUANTITY=?,OIL_TYPE=?, UPDATED_BY=? , UPDATED_AT=?",
									"DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=? AND MODEL_CODE=?",
									dto.getSeriesCode(), dto.getModelName(),12781001,modelStatus,dto.getEngineDesc(),dto.getOilType().toString(),1L,format, dealerCode,dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode());
						*/
							
							StringBuilder sql = new StringBuilder();
							sql.append(" UPDATE TM_MODEL SET MODEL_LABOUR_CODE = '"+dto.getSeriesCode()+"' ");
							sql.append("  , MODEL_NAME ='"+dto.getModelName()+"', OEM_TAG= 12781001 , IS_VALID='"+modelStatus+"'");
							sql.append("  , EXHAUST_QUANTITY='"+dto.getEngineDesc()+"', OIL_TYPE='"+dto.getOilType().toString()+"' , UPDATED_BY= '"+1L+"'  ");
							sql.append(" WHERE DEALER_CODE ='"+dealerCode+"' AND BRAND_CODE='"+dto.getBrandCode()+"' AND MODEL_CODE ='"+dto.getModelCode()+"'  ");
							Base.exec(sql.toString());
						}else{	
							modelPO.setString("DEALER_CODE", dealerCode);
							modelPO.setString("BRAND_CODE",dto.getBrandCode());
							modelPO.setString("SERIES_CODE",dto.getSeriesCode());
							modelPO.setString("MODEL_LABOUR_CODE",dto.getSeriesCode());
							modelPO.setString("MODEL_CODE", dto.getModelCode());
							modelPO.setString("MODEL_NAME",dto.getModelName());	
							modelPO.setLong("OEM_TAG",12781001);
							modelPO.setLong("IS_VALID",modelStatus);
							modelPO.setString("EXHAUST_QUANTITY",dto.getEngineDesc());
							if(!StringUtils.isNullOrEmpty(dto.getOilType())){
								modelPO.setString("OIL_TYPE",dto.getOilType().toString());								
							}
							//modelPO.setDate("CREATED_AT", Utility.getCurrentDateTime());
							modelPO.setString("CREATED_BY",1L);
							modelPO.insert();
							
						
							}
						
						//根据车型去根性产品表中的排量和燃油类型
						if(!StringUtils.isNullOrEmpty(dto.getEngineDesc())&& !StringUtils.isNullOrEmpty(dto.getOilType())){
							StringBuilder sql = new StringBuilder();
							sql.append(" UPDATE TM_VS_PRODUCT SET EXHAUST_QUANTITY = '"+dto.getEngineDesc()+"' ");
							sql.append(" ,OIL_TYPE='"+dto.getOilType().toString()+"' ,UPDATED_BY= '"+1L+"'  ");
							sql.append(" WHERE DEALER_CODE ='"+dealerCode+"' AND MODEL_CODE='"+dto.getModelCode()+"'  ");
							Base.exec(sql.toString());
							/*VsProductPO.update(" EXHAUST_QUANTITY=? ,OIL_TYPE=?, UPDATED_BY=?,UPDATED_AT=?",
									"DEALER_CODE=? AND MODEL_CODE=? AND  D_KEY=?",
									dto.getEngineDesc(), dto.getOilType().toString(),1L,format,dealerCode,dto.getModelCode(),CommonConstants.D_KEY);
							*/
							}
				   }
					
					//插入配置表
					ConfigurationPO configurationPO = new ConfigurationPO();
				/*	po3.setString("DEALER_CODE", dealerCode);
					po3.setString("BRAND_CODE",dto.getBrandCode());
					po3.setString("SERIES_CODE",dto.getSeriesCode());
					po3.setString("MODEL_CODE", dto.getModelCode());
					po3.setString("CONFIG_CODE", dto.getConfigCode());*/
				//	po3.setConfigName(vo.getConfigName());
					List<Map> list3 = Base.findAll(" SELECT * FROM tm_configuration where DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=? AND MODEL_CODE=? AND CONFIG_CODE=? ", new Object[]{dealerCode,dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode(),dto.getConfigCode()});
					//ConfigurationPO configurationPO = ConfigurationPO.findByCompositeKeys(dealerCode,dto.getBrandCode(),dto.getModelCode(),dto.getSeriesCode(),dto.getConfigCode());
					if (null != dto.getSeriesCode() && null != dto.getBrandCode() && null!=dto.getModelCode() && null!=dto.getConfigCode()
						&& !"".equals(dto.getBrandCode()) && !"".equals(dto.getSeriesCode()) && !"".equals(dto.getModelCode()) && !"".equals(dto.getConfigCode())){
						
						if (null != list3 && list3.size()>0){
							StringBuffer sql = new StringBuffer();
							sql.append(" UPDATE tm_configuration SET CONFIG_NAME = '"+dto.getEngineDesc()+"' ");
							sql.append(" , OEM_TAG='"+dto.getOilType().toString()+"' , UPDATED_BY= '"+1L+"'");
							sql.append(" WHERE DEALER_CODE ='"+dealerCode+"' AND MODEL_CODE='"+dto.getModelCode()+"' AND BRAND_CODE = '"+dto.getBrandCode()+"' AND  SERIES_CODE = '"+dto.getSeriesCode()+"' AND CONFIG_CODE='"+dto.getConfigCode()+"' ");
							Base.exec(sql.toString());
							/*ConfigurationPO.update(" CONFIG_NAME=? ,OEM_TAG=?, UPDATED_BY=?,UPDATED_AT=?",
									"DEALER_CODE=? AND BRAND_CODE=? AND SERIES_CODE=? AND MODEL_CODE=? AND  CONFIG_CODE=?",
									dto.getConfigName(), 12781001,1L,format,dealerCode,dto.getBrandCode(),dto.getSeriesCode(),dto.getModelCode(), dto.getConfigCode());*/
							
						}else{
							configurationPO.setString("DEALER_CODE", dealerCode);
							configurationPO.setString("BRAND_CODE",dto.getBrandCode());
							configurationPO.setString("SERIES_CODE",dto.getSeriesCode());
							configurationPO.setString("MODEL_CODE", dto.getModelCode());
							configurationPO.setString("CONFIG_CODE", dto.getConfigCode());
							configurationPO.setString("CONFIG_NAME", dto.getConfigName());
							configurationPO.setLong("OEM_TAG",12781001);
							
							
							//configurationPO.setDate("CREATED_AT", Utility.getCurrentDateTime());
							configurationPO.setString("CREATED_BY",1L);
							configurationPO.insert();
							
							
						}
					}
					
					//插入颜色表
					TmColorPo colorPO = new TmColorPo();
					/*po4.setString("DEALER_CODE", dealerCode);
					po4.setString("COLOR_CODE",dto.getColorCode());*/
				//	po4.setColorName(vo.getColorName());
					List<Map> list4 = Base.findAll("SELECT * FROM  tm_color WHERE DEALER_CODE=? AND COLOR_CODE=?", new Object[]{dealerCode,dto.getColorCode()});
					//TmColorPo colorPO = TmColorPo.findByCompositeKeys(dealerCode,dto.getColorCode());
						if (null != dto.getColorCode() && !"".equals(dto.getColorCode())){
						
						if (null != list4 && list4.size()>0){
							StringBuffer sql = new StringBuffer();
							sql.append(" UPDATE tm_color SET COLOR_NAME = '"+dto.getColorName()+"' ");
							sql.append(" ,OEM_TAG='"+dto.getOilType().toString()+"' , UPDATED_BY= '"+1L+"'  ");
							sql.append(" WHERE DEALER_CODE ='"+dealerCode+"' AND COLOR_CODE='"+dto.getColorCode()+"'  ");
							Base.exec(sql.toString());
							
							/*TmColorPo.update(" COLOR_NAME=? ,OEM_TAG=?, UPDATED_BY=?,UPDATED_AT=?",
									"DEALER_CODE=? AND COLOR_CODE=? ",
									dto.getColorName(), 12781001,1L,format,dealerCode,dto.getColorCode());*/						
						}
						else
						{
							colorPO.setString("DEALER_CODE", dealerCode);
							colorPO.setString("COLOR_CODE",dto.getColorCode());
							colorPO.setString("COLOR_NAME",dto.getColorName());
							colorPO.setLong("OEM_TAG",12781001);	
							
							
							
						//colorPO.setDate("CREATED_AT", Utility.getCurrentDateTime());
						colorPO.setString("CREATED_BY",1L);
						brandPO.insert();
							
							
						}
					}
				}
					
		}
			return 1;	
		}catch(Exception e){
			return 0;	
		}
		
	}

}
