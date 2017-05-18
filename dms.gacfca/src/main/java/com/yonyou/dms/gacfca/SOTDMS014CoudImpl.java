
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SOTDMS014Impl.java
*
* @Author : Administrator
*
* @Date : 2017年3月7日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月7日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author Administrator
* @date 2017年3月7日
*/
@Service
public class SOTDMS014CoudImpl implements SOTDMS014Coud{
    public int getSOTDMS014(List<TiAppUSwapDto> dtList) throws ServiceBizException {
        if (dtList != null && dtList.size() > 0){
            for (int i = 0; i < dtList.size(); i++){
                TiAppUSwapDto appUSwapVO=null;
//                PotentialCusPO tpcpo=null;
//                TtCustomerVehicleListPO cusvehOld = null;
//                TtCustomerVehicleListPO cusvehOld2=null;
                TtCustomerVehicleListPO cusveh = null;
//                TmScModelPO scModpo=null;
                List<UserPO> poList=null;
                List<TtCustomerVehicleListPO> poList1=null;
//                List poList2=null;
                appUSwapVO=(TiAppUSwapDto)dtList.get(i);
                if(!StringUtils.isNullOrEmpty(appUSwapVO.getUniquenessID())){
                    List<Object> cus1 = new ArrayList<Object>();
                    cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus1.add(appUSwapVO.getUniquenessID());   
                    cus1.add(DictCodeConstants.D_KEY);   
                     poList = PotentialCusPO.findBySQL("select * from tm_potential_customer where dealer_code=? and customer_no=? and D_Key=? ", cus1.toArray());
                   
                }else if(!StringUtils.isNullOrEmpty(appUSwapVO.getFCAID())){
                    List<Object> cus2 = new ArrayList<Object>();
                    cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus2.add(Long.valueOf(appUSwapVO.getFCAID()));   
                    cus2.add(DictCodeConstants.D_KEY);   
                     poList = UserPO.findBySQL("select * from tm_potential_customer where dealer_code=? and Spad_Customer_Id=? and D_Key=? ", cus2.toArray());
                 
                }
                if(poList!=null&&poList.size()>0){
                    List<Object> cus3 = new ArrayList<Object>();
                    cus3.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus3.add(appUSwapVO.getVINCode());
                    cus3.add(appUSwapVO.getUniquenessID());
                    cus3.add(DictCodeConstants.D_KEY);   
                    poList1 = TtCustomerVehicleListPO.findBySQL("select * from Tt_Customer_Vehicle_List where dealer_code=? and vin=? and customer_no=? and D_Key=? ", cus3.toArray());
                   
                    if(poList1!=null&&poList1.size()>0){
                        cusveh=(TtCustomerVehicleListPO)poList1.get(0);
                        if(appUSwapVO.getOwnBrandID()!=null)
                            cusveh.setString("Brand_Name",appUSwapVO.getOwnBrandID());
                        if(appUSwapVO.getEstimatedOne()!=null)
                            cusveh.setString("File_Urlmessage_A",appUSwapVO.getEstimatedOne());
                        if(appUSwapVO.getEstimatedTwo()!=null)
                            cusveh.setString("File_Urlmessage_C",appUSwapVO.getEstimatedTwo());
                        if(appUSwapVO.getOwnModelID()!=null)
                            cusveh.setString("Series_Name",appUSwapVO.getOwnModelID());
                        //根据传来的车系,品牌来查询车型
//                      if(appUSwapVO.getOwnBrandID()!=null&&appUSwapVO.getOwnModelID()!=null){
//                          scModpo=new TmScModelPO();
//                          scModpo.setEntityCode(entityCode);
//                          scModpo.setBrandCode(appUSwapVO.getOwnBrandID());
//                          scModpo.setSeriesCode(appUSwapVO.getOwnModelID());
//                          poList1=POFactory.select(conn, scModpo);
//                          if(poList1!=null&&poList1.size()>0){
//                              scModpo=(TmScModelPO)poList1.get(0);
//                              cusveh.setModelName(scModpo.getModelCode());
//                          }
//                      }
                        if(appUSwapVO.getOwnCarStyleID()!=null)
                        cusveh.setString("Model_Name",appUSwapVO.getOwnCarStyleID());
                        if(appUSwapVO.getOwnCarColor()!=null)
                            cusveh.setString("Color_Name",appUSwapVO.getOwnCarColor());
                        if(appUSwapVO.getDriveLicense()!=null)
                            cusveh.setString("File_Urlmessage_B",appUSwapVO.getDriveLicense());
                        if(appUSwapVO.getIsEstimated()!=null)
                            cusveh.setInteger("Is_Assessed",Integer.valueOf(appUSwapVO.getIsEstimated()+""));
                        if(appUSwapVO.getLicenceIssueDate()!=null)
                            cusveh.setDate("Purchase_Date",appUSwapVO.getLicenceIssueDate());
                        if(appUSwapVO.getTravlledDistance()!=null)
                            cusveh.setDouble("Mileage",Double.valueOf(appUSwapVO.getTravlledDistance()+""));
                        if(appUSwapVO.getEstimatedPrice()!=null)
                            cusveh.setDouble("Assessed_Price",Double.valueOf(appUSwapVO.getEstimatedPrice()+""));
                        if(appUSwapVO.getUpdateDate()!=null)
                            cusveh.setDate("spad_Update_Date",appUSwapVO.getUpdateDate());
//                        cusveh.setUpdateDate(appUSwapVO.getUpdateDate());
//                        cusveh.setUpdateBy(Long.valueOf(appUSwapVO.getDealerUserID()));
                        cusveh.saveIt();
                      
                    }       
                }
            }
        }
        return 1;
    }

}
