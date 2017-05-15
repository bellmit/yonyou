package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SOTDCS005Cloud;
import com.yonyou.dcs.gacfca.SOTDCS010Cloud;
import com.yonyou.dms.DTO.gacfca.TiDmsNSwapDto;
import com.yonyou.dms.DTO.gacfca.TiDmsUSwapDto;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerVehicleListDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class SOTDCS005Impl implements SOTDCS005 {
    @Autowired
    SOTDCS005Cloud sotdcs005cloud;
    @Autowired
    SOTDCS010Cloud sotdcs010cloud;

	@Override
	public int getSOTDCS005(String cusno,String status,List<TtCustomerVehicleListPO> PO,List<TtCustomerVehicleListDTO> DTO) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String groupCode = Utility.getGroupEntity(dealerCode, "TM_TRACKING_TASK");
//		Long userid = FrameworkUtil.getLoginInfo().getUserId();
		// 判断ENTITY_CODE是否为空
		if (dealerCode != null && !"".equals(dealerCode)) {

		}
		 boolean isCon = false;
		// 根据开关来判断是否执行程序5434
		String defautParam = Utility.getDefaultValue("5434");
		if (defautParam != null && defautParam.equals(DictCodeConstants.DICT_IS_NO)) {
		    isCon=true;
		}

		PotentialCusPO po1 = null;
//		PotentialCusPO po2 = null;
		PotentialCusPO po3 = null;
		List<PotentialCusPO> listpote = PotentialCusPO.findBySQL(" select * from TM_POTENTIAL_CUSTOMER where DEALER_CODE=? AND CUSTOMER_NO =? AND D_KEY=? ",
				new Object[] { groupCode, cusno, CommonConstants.D_KEY });
		if (listpote != null && !"".equals(listpote)) {
			po1 = listpote.get(0);
		}
		// 调低消息的优先级
		List<TiDmsNSwapDto> TiDmsNSwapDtoList = new LinkedList();
		List<TiDmsUSwapDto> TiDmsUSwapDtoList = new LinkedList();
		   for(TtCustomerVehicleListDTO keepDto : DTO){
		       

//             TtCustomerVehicleListPO poten = null;
//             TtCustomerVehicleListPO poten1 = null;
               TtCustomerVehicleListPO Newpoten = null;
               TiDmsNSwapDto tdNSwapdto = new TiDmsNSwapDto();
               TiDmsUSwapDto tdUSwapdto = new TiDmsUSwapDto();;
               List poList = new LinkedList<>();
               if (StringUtils.isNullOrEmpty(keepDto.getItemId())) {
                   System.out.println(dealerCode);
                   System.out.println(cusno);
                   System.out.println(keepDto.getVin());
                   poList = TtCustomerVehicleListPO.findBySQL("select * from tt_customer_vehicle_list where DEALER_CODE=? AND CUSTOMER_NO=? AND VIN =? AND D_KEY=?",
                           new Object[] { dealerCode, cusno, keepDto.getVin() , CommonConstants.D_KEY});

                   if (poList != null && poList.size() > 0) {
                       Newpoten = (TtCustomerVehicleListPO) poList.get(0);
                       if (Newpoten.getDate("CREATED_AT") != null) {
                           tdNSwapdto.setCreateDate(Newpoten.getDate("CREATED_AT"));
                       }
                       if (Newpoten.getDate("PURCHASE_DATE") != null) {
                           tdNSwapdto.setLicencelssueDate(Newpoten.getDate("PURCHASE_DATE"));
                       }
                       if (Newpoten.getString("FILE_MESSAGE_A") != null) {
                           tdNSwapdto.setEstimatedOne(Newpoten.getString("FILE_MESSAGE_A"));
                       }
                       if (Newpoten.getString("FILE_MESSAGE_C") != null) {
                           tdNSwapdto.setEstimatedTwo(Newpoten.getString("FILE_MESSAGE_C"));
                       }
                       if (cusno != null || !"".equals(cusno)) {
                           tdNSwapdto.setUniquenessId(cusno);
                       } 
                       if (Newpoten.getString("FILE_MESSAGE_B") != null) {
                           tdNSwapdto.setDriveLicense(Newpoten.getString("FILE_MESSAGE_B"));
                       }
                       tdNSwapdto.setDealerCode(dealerCode);
                       if (Newpoten.getString("BRAND_NAME") != null) {
                           tdNSwapdto.setOwnBrandId(Newpoten.getString("BRAND_NAME"));
                       }
                       if (Newpoten.getString("SERIES_NAME") != null) {
                           tdNSwapdto.setOwnModelId(Newpoten.getString("SERIES_NAME"));
                       }
                       if (Newpoten.getString("COLOR_NAME") != null) {
                           tdNSwapdto.setOwnCarColor(Newpoten.getString("COLOR_NAME"));
                       }
                       if (Newpoten.getInteger("IS_ASSESSED") != null) {
                           tdNSwapdto.setIsEstimated(Long.valueOf(Newpoten.getInteger("IS_ASSESSED")));
                       }
                       tdNSwapdto.setVinCode(Newpoten.getString("VIN"));
                       if (Newpoten.getString("MODEL_NAME") != null) {
                           tdNSwapdto.setOwnCarStyleId(Newpoten.getString("MODEL_NAME"));
                       }
                       if (Newpoten.getDouble("MILEAGE") != null) {
                           tdNSwapdto
                                   .setTravlledDistance((long) Double.parseDouble(Newpoten.getDouble("MILEAGE") + ""));
                       }
                       if (Newpoten.getInteger("IS_ASSESSED") != null
                               && Newpoten.getInteger("IS_ASSESSED").equals(12781001)) {
                           if (Newpoten.getDouble("ASSESSED_PRICE") != null) {
                               tdNSwapdto.setEstimatedPrice(
                                       (long) Double.parseDouble(Newpoten.getDouble("ASSESSED_PRICE") + ""));
                           }
                       }
                    
                      tdNSwapdto.setUniquenessId(cusno);
                       
                       if (po1 != null && po1.getLong("SOLD_BY") != null) {
                           tdNSwapdto.setDealerUserId(po1.getLong("SOLD_BY") + "");
                           tdNSwapdto.setDealerCode(dealerCode);
                       }
                     /*  tdNSwapdto.setFCAID(1000000L);
                       TiDmsNSwapDtoList.add(tdNSwapdto);*/
                    if (po1.getLong("SPAD_CUSTOMER_ID") != null) {
                           tdNSwapdto.setFCAID(po1.getLong("SPAD_CUSTOMER_ID"));
                           TiDmsNSwapDtoList.add(tdNSwapdto);
                       }
                   }
               } else if (!StringUtils.isNullOrEmpty(keepDto.getItemId())) {
                   tdUSwapdto = new TiDmsUSwapDto();
                   poList = TtCustomerVehicleListPO.findBySQL(
                           " select * from tt_customer_vehicle_list where DEALER_CODE=? AND CUSTOMER_NO=? AND VIN =? AND D_KEY=? ",
                           new Object[] { dealerCode, cusno, CommonConstants.D_KEY, keepDto.getVin() });
                   if (poList != null && poList.size() > 0) {
                       List poList1 = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where DEALER_CODE=? AND CUSTOMER_NO=?",
                               new Object[] { dealerCode, cusno });
                       if (poList1 != null && poList1.size() > 0) {
                           po3 = (PotentialCusPO) poList1.get(0);
                       }
                       Newpoten = (TtCustomerVehicleListPO) poList.get(0);
                       if (Newpoten.getDate("UPDATED_AT") != null) {
                           tdUSwapdto.setUpdateDate(Newpoten.getDate("UPDATED_AT"));
                       }
                       if (Newpoten.getDate("PURCHASE_DATE") != null) {
                           tdUSwapdto.setLicenceIssueDate(Newpoten.getDate("PURCHASE_DATE"));
                       }
                       if (Newpoten.getString("FILE_MESSAGE_A") != null) {
                           tdUSwapdto.setEstimatedOne(Newpoten.getString("FILE_MESSAGE_A"));
                       }
                       if (Newpoten.getString("FILE_MESSAGE_C") != null) {
                           tdUSwapdto.setEstimatedTwo(Newpoten.getString("FILE_MESSAGE_C"));
                       }
                       if (Newpoten.getString("CUSTOMER_NO") != null) {
                           tdUSwapdto.setUniquenessID(Newpoten.getString("CUSTOMER_NO"));
                       }
                       if (Newpoten.getString("FILE_MESSAGE_B") != null) {
                           tdUSwapdto.setDriveLicense(Newpoten.getString("FILE_MESSAGE_B"));
                       }
                       if (Newpoten.getString("BRAND_NAME") != null) {
                           tdUSwapdto.setOwnBrandID(Newpoten.getString("BRAND_NAME"));
                       }
                       if (Newpoten.getString("SERIES_NAME") != null) {
                           tdUSwapdto.setOwnModelID(Newpoten.getString("SERIES_NAME"));
                       }
                       if (Newpoten.getString("COLOR_NAME") != null) {
                           tdUSwapdto.setOwnCarColor(Newpoten.getString("COLOR_NAME"));
                       }
                       if (Newpoten.getInteger("IS_ASSESSED") != null) {
                           tdUSwapdto.setIsEstimated(Newpoten.getInteger("IS_ASSESSED"));
                       }
                       tdUSwapdto.setVINCode(Newpoten.getString("VIN"));
                       if (Newpoten.getDouble("MILEAGE") != null) {
                           tdUSwapdto
                                   .setTravlledDistance((int) Double.parseDouble(Newpoten.getDouble("MILEAGE") + ""));
                       }
                       if (Newpoten.getInteger("IS_ASSESSED") != null
                               && Newpoten.getInteger("IS_ASSESSED").equals(12781001)) {
                           if (Newpoten.getDouble("ASSESSED_PRICE") != null) {
                               tdUSwapdto.setEstimatedPrice(
                                       (int) Double.parseDouble(Newpoten.getDouble("ASSESSED_PRICE") + ""));
                           }
                       }
                      
                           tdUSwapdto.setUniquenessID(cusno);
                       
                       if (po3 != null && po3.getString("SOLD_BY") != null) {
                           tdUSwapdto.setDealerUserID(po3.getLong("SOLD_BY") + "");
                       }
                       if (po3 != null && po3.getLong("SPAD_CUSTOMER_ID") != null) {
                           tdUSwapdto.setFCAID(Integer.valueOf(po3.getLong("SPAD_CUSTOMER_ID") + ""));
                       }
                       tdUSwapdto.setEntityCode(dealerCode);
                       TiDmsUSwapDtoList.add(tdUSwapdto);
                   }
               }
           
               keepDto.getItemId();
               System.out.println(keepDto.getItemId());
           }
		   String msg ="1";
		   try {
		       if ("A".equals(status)&&!isCon) {
		           System.out.println("2111");
		           msg=sotdcs005cloud.handleExecutor(TiDmsNSwapDtoList);

	        }else if("U".equals(status)&&!isCon){
	            System.out.println("21113");
	            msg=sotdcs010cloud.handleExecutor(TiDmsUSwapDtoList);

	        }
            
        } catch (Exception e) {
            return 0;
        }
	
		return Integer.parseInt(msg);		
	}

}
