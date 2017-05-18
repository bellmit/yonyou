package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.WXBindingRsgDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;

/**
 * @description 接收微信绑定信息
 * @author Administrator
 *
 */
@Service
public class CLDMS013CoudImpl implements CLDMS013Coud {

	final Logger logger= Logger.getLogger(CLDMS013CoudImpl.class);

	@Override
	public int getCLDMS013(String dealerCode,Long userId, List<WXBindingRsgDTO> voList) {
		logger.info("==========CLDMS013Impl执行===========");
		try{	
			if(dealerCode==null){
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}
			if(voList!=null && !voList.isEmpty()){
				for(WXBindingRsgDTO vo :voList){
					if(Utility.testString(vo.getVin())){
						if(vo.getIsEntity().equals("12781002")){
							logger.debug("from TmVehiclePO DEALER_CODE = "+dealerCode+" and VIN = " + vo.getVin());
							List<TmVehiclePO> wslist = TmVehiclePO.findBySQL("DEALER_CODE = ? and VIN = ?", dealerCode,vo.getVin());
							if (null != wslist && wslist.size() == 0) {							
								//新增车主信息
								CarownerPO ownerPOb = new CarownerPO();
								ownerPOb.setString("DEALER_CODE",dealerCode);
								ownerPOb.setString("OWNER_NO",new CommonNoServiceImpl().getSystemOrderNo(CommonConstants.SRV_CZBH));// 车住编号
								ownerPOb.setString("SUB_OWNER_NO",Utility.testString(vo.getOwnerNo()) ? vo.getOwnerNo() : null);//车厂车主编号
								ownerPOb.setInteger("GENDER",Utility.testString(vo.getGender()) ? vo.getGender() : null);// 性别
								ownerPOb.setDate("BIRTHDAY",Utility.testString(vo.getBirthday()) ? vo.getBirthday() : null);// 生日
								ownerPOb.setString("ZIP_CODE",Utility.testString(vo.getZipCode()) ? vo.getZipCode() : null);// 邮编
								ownerPOb.setInteger("PROVINCE",Utility.testString(vo.getProvince()) ? vo.getProvince() : null);// 省
								ownerPOb.setInteger("CITY",Utility.testString(vo.getCity()) ? vo.getCity() : null);// 城市
								ownerPOb.setInteger("DISTRICT",Utility.testString(vo.getDistrict()) ? vo.getDistrict() : null);// 区县
								ownerPOb.setString("CT_CODE",Utility.testString(vo.getCtCode()) ? vo.getCtCode() : null);//证件号
								ownerPOb.setString("CERTIFICATE_NO",Utility.testString(vo.getCertificateNo()) ? vo.getCertificateNo() : null);// 证件号码
								ownerPOb.setString("HOBBY",Utility.testString(vo.getHobby()) ? vo.getHobby() : null);// 爱好
								ownerPOb.setInteger("INDUSTRY_FIRST",Utility.testString(vo.getIndustryFirst()) ? Utility.getInt(vo.getIndustryFirst()) : null);// 行业大类一	
								ownerPOb.setInteger("INDUSTRY_SECOND",Utility.testString(vo.getIndustrySecond()) ? Utility.getInt(vo.getIndustrySecond()) : null);// 行业大类二
								ownerPOb.setInteger("EDU_LEVEL",Utility.testString(vo.getEducationLevel()) ? vo.getEducationLevel() : null);// 学历
								ownerPOb.setDate("FOUND_DATE",Utility.getCurrentDateTime());// 建档日期
								ownerPOb.setDate("SUBMIT_TIME",Utility.testString(vo.getSubmitTime()) ? vo.getSubmitTime() : null);// 上报日期
								ownerPOb.setInteger("IS_UPLOAD",Utility.getInt(CommonConstants.DICT_IS_YES));//默认为是
								ownerPOb.setString("OWNER_NAME",Utility.testString(vo.getCustomerName()) ? vo.getCustomerName() : null);// 车主名称
								ownerPOb.setString("CREATED_BY",CommonConstants.DE_CREATE_UPDATE_BY);
								ownerPOb.setDate("CREATED_AT",Utility.getCurrentDateTime());				
								ownerPOb.setInteger("IS_UPLOAD_GROUP",Utility.getInt(CommonConstants.DICT_IS_NO));	
								ownerPOb.setInteger("OWNER_PROPERTY",Utility.testString(vo.getCtmType()) ? vo.getCtmType() : Utility.getInt(CommonConstants.DICT_OWNER_PROPERTY_PERSONAL));//车主性质
								ownerPOb.saveIt();
								TmVehiclePO tmVehiclePO2 = new TmVehiclePO();
								tmVehiclePO2.setString("DEALER_CODE",dealerCode);			
								tmVehiclePO2.setString("VIN",vo.getVin());//车架号
								tmVehiclePO2.setString("LICENSE",Utility.testString(vo.getLicense()) ? vo.getLicense() : DictCodeConstants.CON_LICENSE_NULL);//车牌号
								tmVehiclePO2.setString("ENGINE_NO",Utility.testString(vo.getEngineNo()) ? vo.getEngineNo() : null);
								tmVehiclePO2.setString("PRODUCT_CODE",Utility.testString(vo.getProductCode()) ? vo.getProductCode() : null);//产品代码	
								tmVehiclePO2.setString("BRAND",Utility.testString(vo.getBrand()) ? vo.getBrand() : null);//品牌
								tmVehiclePO2.setString("SERIES",Utility.testString(vo.getSeries()) ? vo.getSeries() : null);//车系
								tmVehiclePO2.setString("MODEL",Utility.testString(vo.getModel()) ? vo.getModel() : null);//车型
								tmVehiclePO2.setString("COLOR",Utility.testString(vo.getColor()) ? vo.getColor() : null);//颜色
								if(Utility.testString(vo.getModelYear())){
									tmVehiclePO2.setString("MODEL_YEAR",vo.getModelYear());//车型年
									tmVehiclePO2.setString("YEAR_MODEL",vo.getModelYear());//年款
								}
								tmVehiclePO2.setDouble("VEHICLE_PRICE",Utility.testString(vo.getVehiclePrice()) ? vo.getVehiclePrice() : null);//车辆价格
								tmVehiclePO2.setDate("SALES_DATE",Utility.testString(vo.getSalesDate()) ? vo.getSalesDate() : null);//销售日期
								tmVehiclePO2.setInteger("VEHICLE_PURPOSE",Utility.testString(vo.getVehiclePurpose()) ? vo.getVehiclePurpose() : null);//车辆用途
								tmVehiclePO2.setInteger("IS_BINDING",Utility.testString(vo.getIsBinding()) && "1".equals(vo.getIsBinding()) ? 12781001 : 12781002);//是否绑定微信									
								tmVehiclePO2.setDate("BINDING_DATE",Utility.testString(vo.getBindingDate()) ? vo.getBindingDate() : null);//微信绑定时间
								//DMS后台默认常量
								tmVehiclePO2.setDate("FOUND_DATE",new Date());//建档日期
								tmVehiclePO2.setInteger("IS_UPLOAD",Utility.getInt(CommonConstants.DICT_IS_NO));
								tmVehiclePO2.setInteger("IS_UPLOAD_GROUP",Utility.getInt(CommonConstants.DICT_IS_NO));
								tmVehiclePO2.setInteger("IS_SELF_COMPANY",Utility.getInt(CommonConstants.DICT_IS_NO));								
								tmVehiclePO2.setString("OWNER_NO",ownerPOb.getString("Owner_No"));//车主编号
								tmVehiclePO2.setString("CREATED_BY",CommonConstants.DE_CREATE_UPDATE_BY);
								tmVehiclePO2.setDate("CREATED_AT",Utility.getCurrentDateTime());
								tmVehiclePO2.saveIt();					
							}
						}
						//获取 VIN 号 根据vin更新车辆信息表中的威信绑定信息
						if("1".equals(vo.getIsBinding())){ 	//是否进行微信认证(1、已认证 0、未认证)
							logger.debug("update TmVehiclePO set IS_BINDING = "+Utility.getInt(CommonConstants.DICT_IS_YES)+", BINDING_DATE = "+vo.getBindingDate()+", UPDATE_AT = "+new Date()+", UPDATE_BY ="+userId + " where DEALER_CODE = "+dealerCode+" and VIN = "+vo.getVin());
							TmVehiclePO.update("IS_BINDING = ?, BINDING_DATE = ?, UPDATED_AT = ?, UPDATED_BY = ?", 
									"DEALER_CODE = ? and VIN = ?",
									Utility.getInt(CommonConstants.DICT_IS_YES),vo.getBindingDate(),new Date(),userId,dealerCode,vo.getVin());
						}else if("0".equals(vo.getIsBinding())){
							logger.debug("update TmVehiclePO set IS_BINDING = "+Utility.getInt(CommonConstants.DICT_IS_NO)+", UPDATE_AT = "+new Date()+", UPDATE_BY ="+userId + " where DEALER_CODE = "+dealerCode+" and VIN = "+vo.getVin());
							TmVehiclePO.update("IS_BINDING = ?, UPDATED_AT = ?, UPDATED_BY = ?", 
									"DEALER_CODE = ? and VIN = ?",
									Utility.getInt(CommonConstants.DICT_IS_NO),new Date(),userId,dealerCode,vo.getVin());
						}
						//如果该车辆有对应的订单和潜客信息则讲绑定信息更新到潜客表中去
						//先找到对应的销售订单中的潜客编号
						String customerNo=this.queryOrder(dealerCode, vo.getVin());
						if(Utility.testString(customerNo)){
							if("1".equals(vo.getIsBinding())){
								logger.debug("update TmVehiclePO set IS_BINDING = "+Utility.getInt(CommonConstants.DICT_IS_YES)+", BINDING_DATE = "+vo.getBindingDate()+",UPDATE_AT = "+new Date()+", UPDATE_BY ="+userId + " where DEALER_CODE = "+dealerCode+" and CUSTOMER_NO = "+customerNo);
								PotentialCusPO.update("IS_BINDING = ?,BINDING_DATE = ?,UPDATED_BY = ?, UPDATED_AT = ?",
										"DEALER_CODE = ? and CUSTOMER_NO = ?", 
										Utility.getInt(CommonConstants.DICT_IS_YES),vo.getBindingDate(),userId,new Date(),dealerCode,customerNo);
							}else if("0".equals(vo.getIsBinding())){
								logger.debug("update TmVehiclePO set IS_BINDING = "+Utility.getInt(CommonConstants.DICT_IS_NO)+", UPDATE_AT = "+new Date()+", UPDATE_BY ="+userId + " where DEALER_CODE = "+dealerCode+" and CUSTOMER_NO = "+customerNo);
								PotentialCusPO.update("IS_BINDING = ?,,UPDATED_BY = ?, UPDATED_AT = ?",
										"DEALER_CODE = ? and CUSTOMER_NO = ?", 
										Utility.getInt(CommonConstants.DICT_IS_NO),userId,new Date(),dealerCode,customerNo);
							}
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}
		finally{
			logger.info("==========CLDMS013Impl结束===========");
		}
		
	}

	/**
	 * @description 查询潜在客户
	 * @param dealerCode
	 * @param vin
	 * @return
	 * @throws SQLException
	 */
	private String queryOrder(String dealerCode,String vin){
		StringBuffer sql = new StringBuffer("");
		sql.append(" select CUSTOMER_NO from tt_sales_order where dealer_code='"+dealerCode+"' and vin='"+vin+"' and BUSINESS_TYPE=13001001 ");
		sql.append("  and  (so_status=13011030 OR so_status=13011035 OR so_status=13011075) and CONFIRMED_DATE is not null ");
		logger.debug(sql.toString());
		List<Map> result = DAOUtil.findAll(sql.toString(), null);
		if(result != null && !result.isEmpty()){
			return result.get(0).get("CUSTOMER_NO").toString();
		}
		return null;
	}

}
