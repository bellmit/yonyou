package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TmScModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * 业务描述：创建客户信息（置换需求）APP新增接口实现类
 *  
 * @date 2017年2月16日
 * @author wangliang
 */
@Service
public class SOTDMS005CoudImpl implements SOTDMS005Coud {

	@SuppressWarnings("unused")
	@Override
	public int SOTDMS005(LinkedList voList) throws Exception {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		try {
			/*
			 * //判断是否为空,循环操作，根据业务相应的修改数据 if(entityCode==null){
			 * atx.setErrorContext(CommonErrorConstant.MSG_ERROR_LOST_KEY,
			 * MessageService.getInstance().getMessage(
			 * CommonErrorConstant.MSG_ERROR_LOST_KEY),null); return 0; }
			 * //根据开关来判断是否执行程序5434 String
			 * defautParam=BusinessUtility.getDefalutPara(atx.getConnection(),
			 * entityCode, CommonConstant.DEFAULT_PARA_SPAD_PROJECT_IS_OPEN);
			 * if(defautParam!=null&&defautParam.equals(DictDataConstant.
			 * DICT_IS_NO)){ return 1; }
			 */

			// ?LinkedList voList = (LinkedList) msg.getContent();
			//LinkedList voList = null;
			if (voList != null && voList.size() > 0) {
				for (int i = 0; i < voList.size(); i++) {
					TiAppNSwapDto appNSwapdto = null;
					PotentialCusPO tpcpo = null;
					PotentialCusPO tpcpoNew = null;
					TtCustomerVehicleListPO cusvehOld = null;
					TtCustomerVehicleListPO cusvehOld2 = null;
					TtCustomerVehicleListPO cusveh = null;
					TmScModelPO scModpo = null;
					List poList = null;
					List poList1 = null;
					List poList2 = null;
					appNSwapdto = (TiAppNSwapDto) voList.get(i);
					if (Utility.testString(appNSwapdto.getFcaId())
							|| Utility.testString(appNSwapdto.getUniquenessID())) {
						if (Utility.testString(appNSwapdto.getUniquenessID())) {
							tpcpo = new PotentialCusPO();
							tpcpo.setString("DEALER_CODE", dealerCode);
							tpcpo.setString("CUSTOMER_NO", appNSwapdto.getUniquenessID());
							tpcpo.setInteger("D_KEY", CommonConstants.D_KEY);
							// ?poList=POFactory.select(connRead,tpcpo);
							poList = tpcpo.findBySQL("CUSTOMER_NO = ? and dealer_code = ?",
									new Object[] { appNSwapdto.getUniquenessID(), dealerCode });

						} else if (Utility.testString(appNSwapdto.getFcaId())) {
							tpcpo = new PotentialCusPO();
							tpcpo.setString("DEALER_CODE", dealerCode);
							tpcpo.setInteger("SPAD_CUSTOMER_ID", Long.valueOf(appNSwapdto.getFcaId()));
							tpcpo.setInteger("D_KEY", CommonConstants.D_KEY);
							poList = tpcpo.findBySQL("SPAD_CUSTOMER_ID = ? and dealer_code = ?",
									new Object[] { Long.valueOf(appNSwapdto.getFcaId()), dealerCode });
						}
						if (poList != null && poList.size() > 0) {
							tpcpo = (PotentialCusPO) poList.get(0);
							cusvehOld = new TtCustomerVehicleListPO();
							cusvehOld.setString("CUSTOMER_NO", tpcpo.getString("CUSTOMER_NO"));
							cusvehOld.setString("DEALER_CODE", dealerCode);
							cusvehOld.setString("vin", appNSwapdto.getVinCode());
							cusvehOld.setInteger("D_KEY", CommonConstants.D_KEY);
							poList2 = tpcpo.findBySQL("CUSTOMER_NO = ? and dealer_code = ?",
									new Object[] { appNSwapdto.getUniquenessID(), dealerCode });
						}

						if (poList2 != null && poList2.size() > 0) {
							cusvehOld2 = (TtCustomerVehicleListPO) poList2.get(0);
							// 根据创建时间比较，如果PAD创建比DMS创建的大，那么更新，反之跳过
							if (cusvehOld2.getDate("CREATED_DATE").compareTo(appNSwapdto.getCreateDate()) < 0) {
								cusveh = new TtCustomerVehicleListPO();
								if (appNSwapdto.getOwnBrandId() != null) {
									cusveh.setString("BRAND_NAME", appNSwapdto.getOwnBrandId());
								}
								if (appNSwapdto.getEstimatedOne() != null) {
									cusveh.setString("FILE_URLMESSAGE_A", appNSwapdto.getEstimatedOne());
								}
								if (appNSwapdto.getEstimatedTwo() != null) {
									cusveh.setString("FILE_URLMESSAGE_C", appNSwapdto.getEstimatedTwo());
								}
								if (appNSwapdto.getOwnBrandId() != null) {
									cusveh.setString("SERIES_NAME", appNSwapdto.getOwnModelId());
								}
								if (appNSwapdto.getOwnCarStyleId() != null) {
									cusveh.setString("MODEL_NAME", appNSwapdto.getOwnCarStyleId());
								}

								if (appNSwapdto.getOwnCarColor() != null)
									cusveh.setString("COLOR_NAME", appNSwapdto.getOwnCarColor());
								if (appNSwapdto.getDriverLicense() != null)
									cusveh.setString("FILE_URLMESSAGE_B", appNSwapdto.getDriverLicense());
								if (appNSwapdto.getVinCode() != null)
									cusveh.setString("vin", appNSwapdto.getVinCode());
								if (appNSwapdto.getIsEstimated() != null)
									cusveh.setInteger("IS_ASSESSED",
											Integer.valueOf(appNSwapdto.getIsEstimated() + ""));
								if (appNSwapdto.getLicencelssueDate() != null)
									cusveh.setDate("PURCHASE_DATE", appNSwapdto.getLicencelssueDate());
								if (appNSwapdto.getTravlledDistance() != null)
									cusveh.setDouble("MILEAGE", Double.valueOf(appNSwapdto.getTravlledDistance() + ""));
								if (appNSwapdto.getEstimeedPrice() != null)
									cusveh.setDouble("ASSESSED_PRICE",
											Double.valueOf(appNSwapdto.getEstimeedPrice() + ""));
								if (appNSwapdto.getCreateDate() != null)
									cusveh.setDate("SPAD_UPDATE_DATE", appNSwapdto.getCreateDate());
								cusveh.setDate("CREATED_AT", appNSwapdto.getCreateDate());
								cusveh.setLong("UPDATED_BY", Long.valueOf(appNSwapdto.getDealerUserId()));
								// ?POFactory.update(conn, cusvehOld, cusveh);
								cusvehOld.saveIt();
								cusveh.saveIt();
							} else {
								continue;
							}
						} else {
							cusveh = new TtCustomerVehicleListPO();
							if (poList != null && poList.size() > 0) {
								cusveh.setString("CUSTOMER_NO", tpcpo.getString("CUSTOMER_NO"));
							}
							// ?Long itemid = POFactory.getLongPriKey(conn,
							// cusveh);
							Long itemid = null;
							cusveh.setString("DEALER_CODE", dealerCode);
							cusveh.setLong("ITEM_ID", itemid);
							if (appNSwapdto.getFcaId() != null)
								cusveh.setLong("SPAD_CUSTOMER_ID", appNSwapdto.getFcaId());
							if (appNSwapdto.getOwnBrandId() != null)
								cusveh.setString("BRAND_NAME", appNSwapdto.getOwnBrandId());
							if (appNSwapdto.getEstimatedOne() != null)
								cusveh.setString("FILE_URLMESSAGE_A", appNSwapdto.getEstimatedOne());
							if (appNSwapdto.getEstimatedTwo() != null)
								cusveh.setString("FILE_URLMESSAGE_C", appNSwapdto.getEstimatedTwo());
							if (appNSwapdto.getOwnModelId() != null)
								cusveh.setString("SERIES_NAME", appNSwapdto.getOwnModelId());
							if (appNSwapdto.getOwnCarStyleId() != null)
								cusveh.setString("MODEL_NAME", appNSwapdto.getOwnCarStyleId());
							if (appNSwapdto.getOwnCarColor() != null)
								cusveh.setString("COLOR_NAME", appNSwapdto.getOwnCarColor());
							if (appNSwapdto.getDriverLicense() != null)
								cusveh.setString("FILE_URLMESSAGE_B", appNSwapdto.getDriverLicense());
							if (appNSwapdto.getVinCode() != null)
								cusveh.setString("VIN", appNSwapdto.getVinCode());
							if (appNSwapdto.getIsEstimated() != null)
								cusveh.setInteger("IS_ASSESSED", Integer.valueOf(appNSwapdto.getIsEstimated() + ""));
							if (appNSwapdto.getLicencelssueDate() != null)
								cusveh.setDate("PURCHASE_DATE", appNSwapdto.getLicencelssueDate());
							if (appNSwapdto.getTravlledDistance() != null)
								cusveh.setDouble("Mileage", Double.valueOf(appNSwapdto.getTravlledDistance() + ""));
							if (appNSwapdto.getEstimeedPrice() != null)
								cusveh.setDouble("ASSESSED_PRICE", Double.valueOf(appNSwapdto.getEstimeedPrice() + ""));
							if (appNSwapdto.getCreateDate() != null)
								cusveh.setDate("CREATED_AT", appNSwapdto.getCreateDate());
							cusveh.setInteger("IS_SPAD_CREATE", Integer.valueOf(CommonConstants.DICT_IS_YES));
							cusveh.setDate("CREATED_AT", appNSwapdto.getCreateDate());
							cusveh.setLong("CREATED_BY", Long.valueOf(appNSwapdto.getDealerUserId()));
							cusveh.setInteger("D_KEY", CommonConstants.D_KEY);
							// ?POFactory.insert(conn, cusveh);
							cusveh.saveIt();
							tpcpoNew = new PotentialCusPO();
							// 潜客修改为置换意向潜客
							tpcpoNew.setInteger("IS_FIRST_BUY", Integer.valueOf(CommonConstants.DICT_IS_NO));
							tpcpoNew.setInteger("REBUY_CUSTOMER_TYPE",
									Integer.valueOf(CommonConstants.DICT_CUS_TYPE_REPLACE));

							if (tpcpo != null)
								// ?POFactory.update(conn, tpcpo, tpcpoNew);
								tpcpo.saveIt();
							tpcpoNew.saveIt();

						}
					}
				}
			}
		} catch (Exception e) {
			return 0;
		}
		return 1;
	}

}
