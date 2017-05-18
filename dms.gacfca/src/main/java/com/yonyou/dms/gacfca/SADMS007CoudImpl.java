package com.yonyou.dms.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务描述：广汽菲克的 车辆调拨信息下发 
 * 
 * @date 2017年1月6日
 * @author Benzc
 *
 */
@Service
public class SADMS007CoudImpl implements SADMS007Coud{
	
	@SuppressWarnings({ "rawtypes", "unused" })
	@Override
	public int getSADMS007(String dealerCode,List<SA007Dto> dtlist) throws ServiceBizException,Exception {
//		String dealerCode=FrameworkUtil.getLoginInfo().getDealerCode();
		//LinkedList<SA007Dto> dtlist = new LinkedList<SA007Dto>();
//		VsStockPO.findBySQL("select * from tm_vs_stock where DEALER_CODE=?", dtlist.toArray());
		//判断是否为空，根据相应业务修改数据
		if(dtlist != null && dtlist.size()>0){
			for (int i = 0; i < dtlist.size(); i++){
				SA007Dto dt = new SA007Dto();
				dt = (SA007Dto)dtlist.get(i);
				if(dealerCode==null){
					return 0;
				}
				if(!StringUtils.isNullOrEmpty(dt.getInEntityCode()) && !StringUtils.isNullOrEmpty(dt.getOutEntityCode()) && 
						!StringUtils.isNullOrEmpty(dt.getVin())){
					if(dealerCode.equals(dt.getInEntityCode()) && !dealerCode.equals(dt.getOutEntityCode())){
						
						VsStockPO newpo = new VsStockPO();
						List<Map> list = Base.findAll("SELECT * FROM TM_VS_STOCK WHERE DEALER_CODE= '"+dealerCode+"' and VIN='"+dt.getVin()+"' and D_KEY= "+0);
						if(list!=null && list.size()>0){//更新
							VsStockPO.update("PRODUCT_CODE=? , ENGINE_NO=? , MANUFACTURE_DATE=? , KEY_NUMBER=? , CERTIFICATE_NUMBER=?"
									+ " , FACTORY_DATE=? , PURCHASE_PRICE=? , STOCK_STATUS=? , DISPATCHED_STATUS=?"
									+ " , STOCK_IN_TYPE=? , OEM_TAG=? , LATEST_STOCK_IN_DATE=? , UPDATED_BY=? , STORAGE_CODE=? , MAR_STATUS=?"
									, "DEALER_CODE=? AND VIN=? AND D_KEY=?", 
									dt.getProductCode(),dt.getEngineNo(),dt.getManufactureDate(),dt.getKeyNumber(),dt.getCertificateNumber(),
									dt.getFactoryDate(),dt.getVehiclePrice(),DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE,DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER,
									DictCodeConstants.DICT_STOCK_IN_TYPE_ALLOCATION,DictCodeConstants.STATUS_IS_YES,new Date(),1L,"OEMK",13061002,
									dealerCode,dt.getVin(),0);
							/*VsStockPO updatepo = new VsStockPO();
//							VsStockPO.update(updates, conditions, params)
							updatepo.findBySQL("DEALER_CODE=? and VIN=? and D_KEY=?", new Object[]{dealerCode,dt.getVin(),0});
							updatepo.setString("PRODUCT_CODE", dt.getProductCode());
							updatepo.setString("ENGINE_NO", dt.getEngineNo());
							updatepo.setDate("MANUFACTURE_DATE", dt.getManufactureDate());
							updatepo.setString("KEY_NUMBER", dt.getKeyNumber());
							updatepo.setString("CERTIFICATE_NUMBER",dt.getCertificateNumber());
							updatepo.setDate("FACTORY_DATE", dt.getFactoryDate());
							updatepo.setInteger("PURCHASE_PRICE",dt.getVehiclePrice());
							updatepo.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
							updatepo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
							updatepo.setInteger("STOCK_IN_TYPE", DictCodeConstants.DICT_STOCK_IN_TYPE_ALLOCATION);
							updatepo.setInteger("OEM_TAG", DictCodeConstants.STATUS_IS_YES);
							updatepo.setDate("LATEST_STOCK_IN_DATE", System.currentTimeMillis());
							updatepo.setDate("UPDATED_BY", 1L);
							updatepo.setDate("UPDATED_AT", System.currentTimeMillis());
							updatepo.setString("STORAGE_CODE", "OEMK");
							updatepo.setInteger("MAR_STATUS", 13061002);
							updatepo.saveIt();			*/
						} else {//插入
							newpo.setString("PRODUCT_CODE", dt.getProductCode());
							newpo.setString("ENGINE_NO", dt.getEngineNo());
							newpo.setDate("MANUFACTURE_DATE", dt.getManufactureDate());
							newpo.setString("KEY_NUMBER", dt.getKeyNumber());
							newpo.setString("CERTIFICATE_NUMBER",dt.getCertificateNumber());
							newpo.setDate("FACTORY_DATE", dt.getFactoryDate());
							newpo.setInteger("PURCHASE_PRICE",dt.getVehiclePrice());
							newpo.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE);
							newpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
							newpo.setInteger("STOCK_IN_TYPE", DictCodeConstants.DICT_STOCK_IN_TYPE_ALLOCATION);
							newpo.setInteger("OEM_TAG", DictCodeConstants.STATUS_IS_YES);
							newpo.setTimestamp("LATEST_STOCK_IN_DATE", System.currentTimeMillis());
							newpo.setLong("UPDATED_BY", 1L);
							//newpo.setTimestamp("UPDATED_DATE", new Date());
							newpo.setString("STORAGE_CODE", "OEMK");
							newpo.setInteger("MAR_STATUS", 13061002);
							newpo.setString("DEALER_CODE", dealerCode);
							newpo.setString("VIN", dt.getVin());
							newpo.insert();
							String price = "";
							if(!StringUtils.isNullOrEmpty(dt.getVehiclePrice())){
								price = dt.getVehiclePrice().toString();
							}
							this.insertToVehicle(dealerCode, dt.getVin(), 1L, dt.getProductCode(), dt.getEngineNo(), dt.getKeyNumber(), dt.getVehiclePrice(), dt.getFactoryDate());
						}
						
					}
					else if(dealerCode.equals(dt.getOutEntityCode()) && !dealerCode.equals(dt.getInEntityCode())){//调出车
						VsStockPO.update("UPDATED_BY=? , STOCK_STATUS=? , DISPATCHED_STATUS=? , STOCK_OUT_TYPE=? , LATEST_STOCK_OUT_DATE=?", 
								"DEALER_CODE=? AND VIN=? AND D_KEY=?", 
								1L,DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT,DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER,DictCodeConstants.DICT_STOCK_OUT_TYPE_ALLOCATION,new Date(),
								dealerCode,dt.getVin(),0);
						/*VsStockPO newpo = new VsStockPO();
						newpo.findBySQL("DEALER_CODE=? and VIN=? and 0", new Object[]{dealerCode,dt.getVin(),0});
						newpo.setString("UPDATED_BY", 1L);
						newpo.setDate("UPDATED_AT", System.currentTimeMillis());
						newpo.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT);
						newpo.setInteger("DISPATCHED_STATUS", DictCodeConstants.DICT_DISPATCHED_STATUS_HAVE_DELIVER);
						newpo.setInteger("STOCK_OUT_TYPE", DictCodeConstants.DICT_STOCK_OUT_TYPE_ALLOCATION);
						newpo.setDate("LATEST_STOCK_OUT_DATE", System.currentTimeMillis());
						newpo.saveIt();*/
					}
				}
			}
		}
		
		return 1;
	}
	
	/**
	 * 入库新增TM_VEHICLE 校验车主表示是否有默认车主编号888888888
	 * @author Benzc
	 * @date 2017年1月10日
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public void insertToVehicle(
			String dealerCode,
			String vin,
			long userId,
			String productCode,
			String engineNo,
			String keyNumber,
			Double purPrice,
			Date factoryDate
		    ) throws Exception{
		String groupCodeOwner = dealerCode;
		List<Map> ownerList = Base.findAll("SELECT * FROM tm_owner WHERE DEALER_CODE=? and OWNER_NO=?", new Object[]{groupCodeOwner,"888888888888"});
		
		String ownerNo="";
		if(ownerList != null && ownerList.size()>0){
			Map ownerPO = ownerList.get(0);
			ownerNo = ownerPO.get("OWNER_NO").toString();
		}
		if(ownerNo != null && !"".equals(ownerNo)){
			VehiclePO vehiclePO2 = new VehiclePO();
			List<Map> veList = Base.findAll("SELECT * FROM tm_vehicle WHERE DEALER_CODE=? and VIN=?", new Object[]{dealerCode,vin});
			
			if(veList == null || veList.size() ==0){
				vehiclePO2.setInteger("DEALER_CODE", dealerCode);
				vehiclePO2.setString("VIN", vin);
				vehiclePO2.setString("OWNER_NO", ownerNo);
				vehiclePO2.setInteger("CREATED_BY", userId);
				vehiclePO2.setDate("CREATED_AT", System.currentTimeMillis());
				List<Map> vsProduct = Base.findAll("SELECT * FROM TM_VS_PRODUCT WHERE DEALER_CODE=? and D_KEY=? and PRODUCT_CODE=?",
						new Object[]{dealerCode,0,productCode});
				if(vsProduct != null && vsProduct.size() > 0){
					Map productPO = vsProduct.get(0);
					vehiclePO2.setString("BRAND", productPO.get("BRAND_CODE"));
					vehiclePO2.setString("SERIES", productPO.get("SERIES_CODE"));
					vehiclePO2.setString("MODEL", productPO.get("MODEL_CODE"));
					vehiclePO2.setString("COLOR", productPO.get("COLOR_CODE"));
					vehiclePO2.setString("APACKAGE", productPO.get("CONFIG_CODE"));
				}
				vehiclePO2.setString("LICENSE", "无牌照");
				vehiclePO2.setDouble("VEHICLE_PRICE", purPrice);
				vehiclePO2.setDate("FOUND_DATE", new Date());
				vehiclePO2.setDate("PRODUCT_DATE", factoryDate);
				vehiclePO2.setString("ENGINE_NO", engineNo);
				vehiclePO2.setString("KEY_NUMBER", keyNumber);
				vehiclePO2.setInteger("IS_UPLOAD", DictCodeConstants.STATUS_IS_NOT);
				vehiclePO2.setInteger("IS_UPLOAD_GROUP", DictCodeConstants.STATUS_IS_NOT);
				vehiclePO2.setInteger("IS_SELF_COMPANY", DictCodeConstants.STATUS_IS_YES);
				vehiclePO2.setInteger("IS_VALID", DictCodeConstants.STATUS_IS_YES);
				vehiclePO2.insert();
				
				//二级网点业务
				List<Map> listEntityVehicle = Utility.getSubEntityList(veList.get(0).get("DEALER_CODE").toString(), "TM_VEHICLE");
				if(listEntityVehicle != null)
				for(Map entityPO :listEntityVehicle){
				    TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
					poSub.setString("MAIN_ENTITY", vehiclePO2.getString("DEALER_CODE"));
					poSub.setString("DEALER_CODE", entityPO.get("CHILD_ENTITY"));
					poSub.setString("OWNER_NO", vehiclePO2.getString("OwNER_NO"));
					poSub.setString("VIN", vehiclePO2.getString("VIN"));
					poSub.setDate("CREATED_AT", vehiclePO2.getDate("CREATED_AT"));
					poSub.setInteger("CREATED_BY", vehiclePO2.getInteger("CREATED_BY"));
					
					//二级网点业务-车辆子表Insert
					poSub.setString("CONSULTANT", vehiclePO2.getString("CONSULTANT"));
					poSub.setInteger("IS_SELF_COMPANY", vehiclePO2.getString("IS_SELF_COMPANY"));
					poSub.setDate("FIRST_IN_DATE", vehiclePO2.getDate("FIRST_IN_DATE"));
					poSub.setString("CHIEF_TECHNICIAN",vehiclePO2.getString("CHIEF_TECHNICIAN"));
					poSub.setString("SERVICE_ADVISOR", vehiclePO2.getString("SERVICE_ADVISOR"));
					poSub.setString("INSURANCE_ADVISOR",vehiclePO2.getString("INSURANCE_ADVISOR"));
					poSub.setString("MAINTAIN_ADVISOR", vehiclePO2.getString("MAINTAIN_ADVISOR"));
					poSub.setDate("LAST_MAINTAIN_DATE", vehiclePO2.getDate("LAST_MAINTAIN_DATE"));
					poSub.setDouble("LAST_MAINTAIN_MILEAGE", vehiclePO2.getDouble("LAST_MAINTAIN_MILEAGE"));
					poSub.setDate("LAST_MAINTENANCE_DATE", vehiclePO2.getDate("LAST_MAINTENANCE_DATE"));
					poSub.setDouble("LAST_MAINTENANCE_MILEAGE", vehiclePO2.getDouble("LAST_MAINTENANCE_MILEAGE"));
					poSub.setDouble("PRE_PAY", vehiclePO2.getDouble("PRE_PAY"));
					poSub.setDouble("ARREARAGE_AMOUNT", vehiclePO2.getDouble("ARREARAGE_AMOUNT"));
					poSub.setDate("DISCOUNT_EXPIRE_DATE", vehiclePO2.getDate("DISCOUNT_EXPIRE_DATE"));
					poSub.setString("DISCOUNT_MODE_CODE", vehiclePO2.getString("DISCOUNT_MODE_CODE"));
					poSub.setInteger("IS_SELF_COMPANY_INSURANCE", vehiclePO2.getInteger("IS_SELF_COMPANY_INSURANCE"));
					poSub.setDate("ADJUST_DATE", vehiclePO2.getDate("ADJUST_DATE"));		
					poSub.setString("ADJUSTER", vehiclePO2.getString("ADJUSTER"));
					poSub.setInteger("IS_VALID", vehiclePO2.getInteger("IS_VALID"));
					poSub.setInteger("NO_VALID_REASON",vehiclePO2.getInteger("NO_VALID_REASON"));
                    
					poSub.insert();
				}
				
			}
		}
	}

	

}
