
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : SOTDMS012Impl.java
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

import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.ConfigurationPO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
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
public class SOTDMS012CoudImpl implements SOTDMS012Coud {
    public int getSOTDMS012(List<TiAppUCustomerInfoDto> infoList) throws ServiceBizException {
        if(infoList!=null&&infoList.size()>0){
            for(int i = 0 ; i < infoList.size();i++){
                TiAppUCustomerInfoDto vo=(TiAppUCustomerInfoDto)infoList.get(i);
                
                if(StringUtils.isNullOrEmpty(vo.getFCAID())){
                    
                    throw new ServiceBizException("售中回传的客户ID为空", "售中回传的客户ID为空");           
                }
                List<Object> cus1 = new ArrayList<Object>();
                cus1.add(FrameworkUtil.getLoginInfo().getDealerCode());
                cus1.add(vo.getFCAID().longValue());   
                cus1.add(DictCodeConstants.D_KEY);   
                List<PotentialCusPO> selectPoList = PotentialCusPO.findBySQL("select * from tm_potential_customer where dealer_code=? and Spad_Customer_Id=? and D_Key=? ", cus1.toArray());
              
                if(selectPoList==null || selectPoList.size()==0){
                    //如果是DMS创建 售中工具更新则要根据 customer_no来进行更新了
                    if(!StringUtils.isNullOrEmpty(vo.getUniquenessID())){
                        //TmPotentialCustomerPO tpo = new TmPotentialCustomerPO();
                        List<Object> cus2 = new ArrayList<Object>();
                        cus2.add(FrameworkUtil.getLoginInfo().getDealerCode());
                        cus2.add(vo.getFCAID().longValue());   
                        cus2.add(DictCodeConstants.D_KEY);   
                         selectPoList = PotentialCusPO.findBySQL("select * from tm_potential_customer where dealer_code=? and Customer_No=? and D_Key=? ", cus2.toArray());
                       
                        if(selectPoList==null || selectPoList.size()==0){
                            throw new ServiceBizException("根据售中传来的条件无法找到相应的客户信息", "根据售中传来的条件无法找到相应的客户信息");
                            
                        }
                    }else{
                        throw new ServiceBizException("没有找到该售中客户信息", "没有找到该售中客户信息");
                                         
                    }
                }
                
                //else{
                PotentialCusPO po1 = (PotentialCusPO)selectPoList.get(0);

                PotentialCusPO po =  (PotentialCusPO)selectPoList.get(0);
                    if(po1.getString("Is_To_Shop").equals(DictCodeConstants.DICT_IS_NO)){
//                      1.如果DMS端的是否到店为否，更新数据
                         if(vo.getIsToShop()==Integer.parseInt(DictCodeConstants.DICT_IS_YES)){
                                po.setInteger("Is_To_Shop",vo.getIsToShop());
                                po.setDate("Time_To_Shop",vo.getTimeToShop());
                            }else{
                                po.setDate("Time_To_Shop",vo.getTimeToShop());
                            } 
                    }
                    //客户意向表
                    List<Object> cus3 = new ArrayList<Object>();
                    cus3.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus3.add(po1.getString("Customer_No"));   
                    cus3.add(DictCodeConstants.D_KEY);   
                    List<TtCusIntentPO> selectCiList = TtCusIntentPO.findBySQL("select * from Tt_Cus_Intent where dealer_code=? and Customer_No=? and D_Key=? ", cus3.toArray());
//                    TtCusIntentPO selectci = new TtCusIntentPO();
                   
                    if(selectCiList==null|| selectCiList.size()==0){
                        throw new ServiceBizException("没有找到该潜在客户意向信息", "没有找到该潜在客户意向信息");
                        
                    }
                    TtCusIntentPO ci1 = (TtCusIntentPO)selectCiList.get(0);
                    TtCusIntentPO ci =  (TtCusIntentPO)selectCiList.get(0);//(TtCustomerIntentPO)selectCiList.get(0);
                    
//                    TtCustomerIntentDetailPO seletcCid = new TtCustomerIntentDetailPO();
                    List<Object> cus4 = new ArrayList<Object>();
                    cus4.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus4.add(ci1.get("Intent_Id"));   
                    cus4.add(12781001);   
                    cus4.add(DictCodeConstants.D_KEY);   
                    List<TtCustomerIntentDetailPO> selectCidList = TtCustomerIntentDetailPO.findBySQL("select * from Tt_Customer_Intent_Detail where dealer_code=? and Intent_Id=? and Is_Main_Model=? and D_Key=? ", cus4.toArray());
                  
                    if(selectCidList==null || selectCidList.size()==0){
                        throw new ServiceBizException("没有找到该潜在客户意向详细信息", "没有找到该潜在客户意向详细信息");
                       
                    }
                    
                    TtCustomerIntentDetailPO cid =  (TtCustomerIntentDetailPO)selectCidList.get(0); //(TtCustomerIntentDetailPO)selectCidList.get(0);
                    
                    if(!StringUtils.isNullOrEmpty(vo.getFCAID())){
                        po.setLong("Spad_Customer_Id",vo.getFCAID().longValue());
                    }
                    if(!StringUtils.isNullOrEmpty(vo.getClientType())){
                        po.setString("CustomerType",vo.getClientType());
                    }
                    
                    po.setString("Customer_Name",vo.getName());
                    if(!StringUtils.isNullOrEmpty(vo.getGender())){
                        po.setString("Gender",vo.getGender());
                    }
                    po.setString("Contactor_Mobile",vo.getPhone());
                    po.setString("Contactor_Phone",vo.getTelephone());
                    po.setDate("Birthday",vo.getBirthday());  
                    if(!StringUtils.isNullOrEmpty(vo.getOppLevelID())){
                        //po.setInitLevel(Utility.getInt(vo.getOppLevelID()));//初始级别
                        po.setInteger("IntentLevel",vo.getOppLevelID());//意向级别
                    }
                    
                    if(!StringUtils.isNullOrEmpty(vo.getSourceType())){
                        po.setInteger("Cus_Source",vo.getSourceType());
                    }
                    if(!StringUtils.isNullOrEmpty(vo.getSecondSourceType())){
                        po.setString("Campaign_Code",vo.getSecondSourceType());
                    }
                //  po.setEntityCode(vo.getDealerCode());
                    
                    if(!StringUtils.isNullOrEmpty(vo.getDealerUserID())){
                        po.setLong("Sold_By",vo.getDealerUserID());//tm_user 中的USER_ID
                        po.setLong("Owned_By",vo.getDealerUserID());
                    }
                    
                    
                    
                    //po.setFoundDate(vo.getCreateDate());
                    //po.setCreateDate(Utility.getCurrentDateTime());
//                  if(Utility.testString(vo.getDealerUserId())){
//                      po.setCreateBy(Utility.getLong(vo.getDealerUserId()));
//                  }

                    if(!StringUtils.isNullOrEmpty(vo.getGiveUpType())){//休眠类型
                        po.setInteger("Sleep_Type",vo.getGiveUpType());
                    }
                    
                    po.setString("Keep_Apply_Reasion",vo.getGiveUpCause());//休眠原因
                    
                
                    //创建中间表信息 (客户意向)
                //  TtCustomerIntentPO ci = new TtCustomerIntentPO();
        //          ci.setEntityCode(entityCode);
        //          ci.setIntentId(POFactory.getLongPriKey(con, ci));
                    
                    //购车预算
//                  if(Utility.testString(vo.getBuyCarBugget())){
//                      ci.setBudgetAmount(Utility.getDouble(vo.getBuyCarBugget()));//BUDGET_AMOUNT
//                  }
                    
                //  po.setIntentId(ci.getIntentId());//潜客中的意向ID
                    
                    //系统默认写入
            //      po.setIsUpload(Utility.getInt(DictDataConstant.DICT_IS_NO));// 待上报、
                    
                    //非首次购车客户类型判断  购车类型
                    if (!StringUtils.isNullOrEmpty(vo.getBuyCarcondition())) {
                        if("10541003".equals(vo.getBuyCarcondition())){
                            po.setInteger("Is_First_Buy",12781001);//首次购车
                            po.setString("Rebuy_Customer_Type",null);//非首次购车客户类型
                            
                        }
                        if("10541001".equals(vo.getBuyCarcondition())){
                            po.setInteger("Is_First_Buy",12781002);
                            po.setInteger("Rebuy_Customer_Type",10541001);//增购客户
                        }
                        if("10541002".equals(vo.getBuyCarcondition())){
                            po.setInteger("Is_First_Buy",12781002);
                            po.set("Rebuy_Customer_Type",10541002);//置换意向潜客
                        }
                    }else{
                        po.setInteger("Is_First_Buy",12781001);
                    }
                    
                    //add by jll 2015-07-13
                    po.setInteger("Province",vo.getProvinceID());//省
                    po.setInteger("City",vo.getCityID());//市
                    
                    
                    //创建中间表信息 (客户意向)
                    
//                  ci.setCustomerNo(po.getCustomerNo());
//                  ci.setEntityCode(po.getEntityCode());
//                  ci.setDKey(CommonConstant.D_KEY);
                    if(!StringUtils.isNullOrEmpty(vo.getBuyCarBudget())){
                        ci.setDouble("Budget_Amount",vo.getBuyCarBudget());    
                    }
                    if(!StringUtils.isNullOrEmpty(vo.getDealerUserID())){
                        ci.setLong("UpdateBy",vo.getDealerUserID());                          
                    }
                    ci.setDate("Updated_At",vo.getUpdateDate());
                    //客户意向报价
                //  TtCustomerIntentDetailPO cid = new TtCustomerIntentDetailPO();
                
                //  cid.setEntityCode(entityCode);
                //  cid.setItemId(POFactory.getLongPriKey(con, cid));
                //  cid.setIntentId(ci.getIntentId());
                    

                    
//                  edit by jll 2015-07-13 售中的品牌、车型、车款对应DMS的品牌、车系、配置
                    
                    cid.setString("Intent_Brand",vo.getBrandID());//品牌ID              
                    cid.setString("Intent_Series",vo.getModelID());//车型ID
                    cid.setString("IntentConfig",vo.getCarStyleID());//车款ID
                    
//                  add by jll 2015-07-13 如果存在配置信息，则需要反向获取在DMS的车型信息的数据，否则DMS前台不会显示
                    if(!StringUtils.isNullOrEmpty(vo.getCarStyleID())){
                        List<Object> cus5 = new ArrayList<Object>();
                        cus5.add(FrameworkUtil.getLoginInfo().getDealerCode());
                        cus5.add(vo.getCarStyleID());   
                      
                        cus5.add(DictCodeConstants.D_KEY);   
                        List<ConfigurationPO> configurationPO = ConfigurationPO.findBySQL("select * from Tm_Configuration where dealer_code=? and Config_Code=?  and D_Key=? ", cus5.toArray());
                      
                        
                        if(configurationPO!=null){
                            cid.setString("Intent_Model",configurationPO.get(0).get("Model_Code"));//车型代码
                        }
                        
                    }
                    
                    cid.setString("Intent_Color",vo.getIntentCarColor());//车辆颜色ID
                //  cid.setDKey(CommonConstant.D_KEY);
                    cid.setString("Competitor_Series",vo.getContendCar());//竞争车型
                    if(!StringUtils.isNullOrEmpty(vo.getDealerUserID())){
                        cid.setLong("Updated_By",vo.getDealerUserID());                         
                    }
                    cid.setDate("Updated_Date",vo.getUpdateDate());
                    
                    po.setDate("AuditDate",vo.getGiveUpDate()); //休眠时间
                    po.setDate("Spad_Update_Date",vo.getUpdateDate());
                    po.setDate("Updated_at",vo.getUpdateDate());
                    if(!StringUtils.isNullOrEmpty(vo.getDealerUserID())){
                        po.setLong("Updated_By",vo.getDealerUserID());                          
                    }
                    
                    List<Object> cus6 = new ArrayList<Object>();
                    cus6.add(FrameworkUtil.getLoginInfo().getDealerCode());
                    cus6.add(po1.getString("Customer_No"));   
                    cus6.add(DictCodeConstants.D_KEY);   
                    List<TtPoCusLinkmanPO> ttPoCusLinkmanPO1 = TtPoCusLinkmanPO.findBySQL("select * from Tt_Po_Cus_Linkman where dealer_code=? and Customer_No=? and D_Key=? ", cus6.toArray());
                   
                    if (ttPoCusLinkmanPO1!=null){
                        TtPoCusLinkmanPO  ttPoCusLinkmanPO=new TtPoCusLinkmanPO();
                        ttPoCusLinkmanPO=(TtPoCusLinkmanPO)ttPoCusLinkmanPO1.get(0);
                        if (!StringUtils.isNullOrEmpty(vo.getGender())) {
                            ttPoCusLinkmanPO.setString("Gender",vo.getGender());
                        }
                        ttPoCusLinkmanPO.setString("Contactor_Name",vo.getName());
                        ttPoCusLinkmanPO.setString("Phone",vo.getTelephone());
                        ttPoCusLinkmanPO.setString("Mobile",vo.getPhone());
                        if(!StringUtils.isNullOrEmpty(vo.getDealerUserID())){
                            ttPoCusLinkmanPO.set("Updated_By",vo.getDealerUserID());                                
                        }
                        ttPoCusLinkmanPO.set("Updated_at",vo.getUpdateDate());
                        ttPoCusLinkmanPO.saveIt();
                    }
                    
                    
                    /*
                      POFactory.insert(con, po);
                    POFactory.insert(con, ci);
                    POFactory.insert(con, cid); 
                     */
                     po.saveIt();
                     ci.saveIt();
                     cid.saveIt();
                //--}
            }
        }
        return 1;
        
    }

}
