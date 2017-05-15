
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SADMS001Impl.java
*
* @Author : yangjie
*
* @Date : 2017年1月17日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月17日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.VehicleShippingDetailDto;
import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.common.domains.PO.stockmanage.TtVsShippingNotifyPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * TODO 业务描述：车辆发运信息下发
 * 
 * @author yangjie
 * @date 2017年1月17日
 */

@Service
public class SADMS001Impl implements SADMS001 {

    private static final Logger logger = LoggerFactory.getLogger(SADMS001Impl.class);

    /**
     * @author yangjie
     * @date 2017年1月17日
     * @return (non-Javadoc)
     * @see com.yonyou.dms.gacfca.SADMS001#performExecute()
     */

    @Override
    public int getSADMS001(List<VehicleShippingDto> voList,String dealerCode) throws ServiceBizException {
        try {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		String format = df.format(new Date());
            //LinkedList voList = new LinkedList();
            //String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
            if (voList != null && voList.size() > 0) {
                for (int i = 0; i < voList.size(); i++) {
                    VehicleShippingDto dto = new VehicleShippingDto();
                    dto = (VehicleShippingDto) voList.get(i);
                    if (dto.getVehicleVoList() != null && dto.getVehicleVoList().size() > 0) {
                        VehicleShippingDetailDto dto1 = dto.getVehicleVoList().get(0);
                        List<Map> list = Base.findAll("SELECT *  FROM tt_vs_shipping_notify WHERE DEALER_CODE=? AND VIN=? AND D_KEY=?",new Object[]{dealerCode,dto1.getVin(),CommonConstants.D_KEY});

                     

                        if (list!=null&&list.size() > 0) {
                        	Map map=list.get(0);
                            Date downDate =(Date)map.get("DOWN_TIMESTAMP");
                            if (downDate != null && downDate.getTime() >= dto.getDownTimestamp().getTime()) {
                                logger.debug("============>>DE下发时序不对，更新失败！");
                                continue;// 跳出本次循环
                            } else {
                            	StringBuffer sql=new StringBuffer("");
                            	sql.append("update tt_vs_shipping_notify set DOWN_TIMESTAMP='"+df.format(dto.getDownTimestamp())+"',OEM_TAG='"+DictCodeConstants.IS_YES+"',IS_ALLOCATED='"+DictCodeConstants.IS_NOT+"',IS_CONSIGNED='"+DictCodeConstants.IS_NOT+"',");
                            	if (null != dto.getEcOrderNo() && !"".equals(dto.getEcOrderNo()))
                            	sql.append("EC_ORDER_NO='"+dto.getEcOrderNo()+"',");
                            	if (null != dto.getShippingOrderNo() && !"".equals(dto.getShippingOrderNo()))
                            	sql.append("SHIPPING_ORDER_NO='"+dto.getShippingOrderNo()+"',");
                            	if (null != dto.getPoNo() && !"".equals(dto.getPoNo()))
                            	sql.append("PO_NO='"+dto.getPoNo()+"',");
                            	if (null != dto.getShippingDate() && !"".equals(dto.getShippingDate()))
                            		sql.append("SHIPPING_DATE='"+df.format(dto.getShippingDate())+"',");
                            	if (null != dto.getDeliveryType() && !"".equals(dto.getDeliveryType()))
                            		sql.append("DELIVERY_TYPE='"+(dto.getPoNo()+"")+"',");
                            	 if (null != dto.getShipperName() && !"".equals(dto.getShipperName()))
                            		 sql.append("SHIPPER_NAME='"+dto.getShipperName()+"',");
                                 if (null != dto.getShipperLicense() && !"".equals(dto.getShipperLicense()))
                                	 sql.append("SHIPPER_LICENSE='"+dto.getShipperLicense()+"',");
                                 if (null != dto.getDeliverymanName() && !"".equals(dto.getDeliverymanName()))
                                	 sql.append("DELIVERYMAN_NAME='"+dto.getDeliverymanName()+"',");
                                 if (null != dto.getDeliverymanPhone() && !"".equals(dto.getDeliverymanPhone()))
                                	 sql.append("DELIVERYMAN_PHONE='"+dto.getDeliverymanPhone()+"',");
                                 if (null != dto.getArrivingDate() && !"".equals(dto.getArrivingDate()))
                                	 sql.append("ARRIVING_DATE='"+df.format(dto.getArrivingDate())+"',");
                                 if (null != dto.getShippingAddress() && !"".equals(dto.getShippingAddress()))
                                	 sql.append("SHIPPING_ADDRESS='"+dto.getShippingAddress()+"',");
                                 if (null != dto.getRemark() && !"".equals(dto.getRemark()))
                                	 sql.append("REMARK='"+dto.getRemark()+"',");
                                 if (null != dto1.getProductCode() && !"".equals(dto1.getProductCode()))
                                	 sql.append("PRODUCT_CODE='"+dto1.getProductCode()+"',");
                                 if (null != dto1.getEngineNo() && !"".equals(dto1.getEngineNo()))
                                	 sql.append("ENGINE_NO='"+dto1.getEngineNo()+"',");
                                 if (null != dto1.getKeyNumber() && !"".equals(dto1.getKeyNumber()))
                                	 sql.append("KEY_NUMBER='"+dto1.getKeyNumber()+"',");
                                 if (null != dto1.getHasCertificate() && !"".equals(dto1.getHasCertificate()))
                                	 sql.append("HAS_CERTIFICATE='"+dto1.getHasCertificate()+"',");
                                 if (null != dto1.getCertificateNumber() && !"".equals(dto1.getCertificateNumber()))
                                	 sql.append("CERTIFICATE_NUMBER='"+dto1.getCertificateNumber()+"',");
                                 if (null != dto1.getManufactureDate() && !"".equals(dto1.getManufactureDate()))
                                	 sql.append("MANUFACTURE_DATE='"+df.format(dto1.getManufactureDate())+"',");
                                 if (null != dto1.getFactoryDate() && !"".equals(dto1.getFactoryDate()))
                                	 sql.append("FACTORY_DATE='"+df.format(dto1.getFactoryDate())+"',");
                                 if (null != dto1.getVehiclePrice() && !"".equals(dto1.getVehiclePrice()))
                                	 sql.append("PURCHASE_PRICE='"+dto1.getVehiclePrice()+"',");
                                 if (null != dto1.getModelYear() && !"".equals(dto1.getModelYear()))
                                	 sql.append("MODEL_YEAR='"+dto1.getModelYear()+"',");
                                 sql.append("UPDATED_BY='"+CommonConstants.DE_CREATE_UPDATE_BY+"',");
                                 sql.append("UPDATED_AT='"+format+"'");
                                 sql.append(" WHERE DEALER_CODE='"+dealerCode+"' AND VIN='"+dto1.getVin()+"' AND D_KEY='"+CommonConstants.D_KEY+"'");
                                Base.exec(sql.toString());
                            }
                        } else {
                        	   TtVsShippingNotifyPO fpo = new TtVsShippingNotifyPO();
                               // 添加电商订单号
                               if (null != dto.getEcOrderNo() && !"".equals(dto.getEcOrderNo()))
                                   fpo.setString("EC_ORDER_NO", dto.getEcOrderNo());
                               if (null != dto.getShippingOrderNo() && !"".equals(dto.getShippingOrderNo()))
                                   fpo.setString("SHIPPING_ORDER_NO", dto.getShippingOrderNo());
                               fpo.setString("DEALER_CODE", dealerCode);
                               if (null != dto1.getVin() && !"".equals(dto1.getVin()))
                               	fpo.setString("VIN", dto1.getVin());
                               	fpo.setDate("DOWN_TIMESTAMP", df.format(dto.getDownTimestamp()));
                               	fpo.setLong("OEM_TAG", DictCodeConstants.IS_YES);
                               if (null != dto.getPoNo() && !"".equals(dto.getPoNo()))
                               	fpo.setString("PO_NO", dto.getPoNo());
                               if (null != dto.getShippingDate() && !"".equals(dto.getShippingDate()))
                                   fpo.setDate("SHIPPING_DATE", df.format(dto.getShippingDate()));
                               if (null != dto.getDeliveryType() && !"".equals(dto.getDeliveryType()))
                                   fpo.setString("DELIVERY_TYPE", dto.getDeliveryType().toString());
                               if (null != dto.getShipperName() && !"".equals(dto.getShipperName()))
                                   fpo.setString("SHIPPER_NAME", dto.getShipperName());
                               if (null != dto.getShipperLicense() && !"".equals(dto.getShipperLicense()))
                                   fpo.setString("SHIPPER_LICENSE", dto.getShipperLicense());
                               if (null != dto.getDeliverymanName() && !"".equals(dto.getDeliverymanName()))
                                   fpo.setString("DELIVERYMAN_NAME", dto.getDeliverymanName());
                               if (null != dto.getDeliverymanPhone() && !"".equals(dto.getDeliverymanPhone()))
                                   fpo.setString("DELIVERYMAN_PHONE", dto.getDeliverymanPhone());
                               if (null != dto.getArrivingDate() && !"".equals(dto.getArrivingDate()))
                                   fpo.setDate("ARRIVING_DATE", df.format(dto.getArrivingDate()));
                               if (null != dto.getShippingAddress() && !"".equals(dto.getShippingAddress()))
                                   fpo.setString("SHIPPING_ADDRESS", dto.getShippingAddress());
                               if (null != dto.getRemark() && !"".equals(dto.getRemark()))
                                   fpo.setString("REMARK", dto.getRemark());
                               if (null != dto1.getProductCode() && !"".equals(dto1.getProductCode()))
                                   fpo.setString("PRODUCT_CODE", dto1.getProductCode());
                               if (null != dto1.getEngineNo() && !"".equals(dto1.getEngineNo()))
                                   fpo.setString("ENGINE_NO", dto1.getEngineNo());
                               if (null != dto1.getKeyNumber() && !"".equals(dto1.getKeyNumber()))
                                   fpo.setString("KEY_NUMBER", dto1.getKeyNumber());
                               if (null != dto1.getHasCertificate() && !"".equals(dto1.getHasCertificate()))
                                   fpo.setInteger("HAS_CERTIFICATE", dto1.getHasCertificate());
                               if (null != dto1.getCertificateNumber() && !"".equals(dto1.getCertificateNumber()))
                                   fpo.setString("CERTIFICATE_NUMBER", dto1.getCertificateNumber());
                               if (null != dto1.getManufactureDate() && !"".equals(dto1.getManufactureDate()))
                                   fpo.setDate("MANUFACTURE_DATE", df.format(dto1.getManufactureDate()));
                               if (null != dto1.getFactoryDate() && !"".equals(dto1.getFactoryDate()))
                                   fpo.setDate("FACTORY_DATE", df.format(dto1.getFactoryDate()));
                               if (null != dto1.getVehiclePrice() && !"".equals(dto1.getVehiclePrice()))
                                   fpo.setDouble("PURCHASE_PRICE", dto1.getVehiclePrice());
                               fpo.setInteger("D_KEY", CommonConstants.D_KEY);
                               fpo.setLong("IS_ALLOCATED", DictCodeConstants.IS_NOT); // 加查询出来必要的东西
                               fpo.setLong("IS_CONSIGNED", DictCodeConstants.IS_NOT);
                               if (null != dto1.getModelYear() && !"".equals(dto1.getModelYear()))
                                   fpo.setString("MODEL_YEAR", dto1.getModelYear());
                        	fpo.setTimestamp("CREATED_AT", format);
                        	fpo.setLong("CREATED_BY", CommonConstants.DE_CREATE_UPDATE_BY);
                            fpo.insert();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
        return 1;
    }

}
