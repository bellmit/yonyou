package com.yonyou.dms.gacfca;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS002Cloud;
import com.yonyou.dms.DTO.gacfca.VsStockDetailListDto;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.dms.common.domains.PO.basedata.SystemStatusPO;
import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultPO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsInspectionMarPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsStockEntryItemPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 业务描述：车辆验收信息上报
 * 
 * @author Benzc
 * @date 2017年1月5日
 * 
 */
@Service
public class SADMS002Impl implements SADMS002{
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS002Impl.class);
	@Autowired SADCS002Cloud SADCS002Cloud;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getSADMS002(String seNo,List<Map> items) throws ServiceBizException {
	    LinkedList<VsStockEntryItemDto> resultList;
		
		try {
			logger.info("========================SADMS002开始============================");
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			
			resultList = new LinkedList<VsStockEntryItemDto>();
			for (int i = 0; i < items.size(); i++) {
			    Map map = items.get(i);
	
					if (null != map.get("VIN") && !("").equals(map.get("VIN").toString())) {
						List<VsStockEntryItemPO> list = VsStockEntryItemPO.find("DEALER_CODE=? and SE_NO=? and VIN=? and D_KEY=?",
								new Object[] { dealerCode, seNo, map.get("VIN").toString(), 0 });
						VsStockEntryItemPO tpo = list.get(i);
						tpo.setInteger("IS_UPLOAD", DictCodeConstants.STATUS_IS_YES);
						tpo.setDate("SUBMIT_TIME", new Date(System.currentTimeMillis()));
						tpo.setDate("UPDATED_AT", new Date(System.currentTimeMillis()));
						tpo.setLong("UPDATED_BY", 1L);
						tpo.setInteger("D_KEY", 0);
						if (tpo.getString("DEALER_CODE") == null || "".equals(tpo.getString("DEALER_CODE"))
								|| tpo.getString("VIN") == null || "".equals(tpo.getString("VIN"))) {
							return "0";
						}
						tpo.saveIt();

						List<VsStockEntryItemPO> vseiList = VsStockEntryItemPO.findBySQL("SELECT * FROM tt_vs_stock_entry_item WHERE DEALER_CODE=? and SE_NO=? and VIN=? and D_KEY=?",
								new Object[] { dealerCode, seNo, map.get("VIN").toString(), 0 });
						VsStockEntryItemPO po = vseiList.get(i);
						

						VsStockEntryItemDto dto1 = new VsStockEntryItemDto();

						//根据产品代码获取产品主数据品牌车系车型颜色信息
						VsProductPO productPO = null;
						if (!StringUtils.isNullOrEmpty(map.get("PRODUCT_CODE"))) {
							List<VsProductPO> vpList = VsProductPO.findBySQL("SELECT * FROM TM_VS_PRODUCT WHERE DEALER_CODE=? and D_KEY=? and PRODUCT_CODE=?",
									new Object[] { dealerCode, 0, map.get("PRODUCT_CODE").toString() });
							productPO = vpList.get(i);
						}
                        
						List<UserPO> uList = UserPO.findBySQL("SELECT * FROM tm_user WHERE DEALER_CODE=? and USER_ID=?",
								new Object[] { dealerCode, po.getString("INSPECTOR") });
						UserPO po1 = uList.get(i);

						dto1.setDealerCode(dealerCode);
						dto1.setVin( map.get("VIN").toString());
						dto1.setRemark(po.getString("REMARK"));
						dto1.setInspectionDate(po.getDate("INSPECTION_DATE"));
						dto1.setInspectionPerson(po1.getString("USER_NAME"));

						if (productPO != null) {
							dto1.setBrand(productPO.getString("BRAND_CODE"));
							dto1.setSeries(productPO.getString("SERIES_CODE"));
							dto1.setModel(productPO.getString("MODEL_CODE"));
							dto1.setColor(productPO.getString("COLOR_CODE"));
						}

						//PDI检查结果
						VehiclePdiResultPO tecpo = new VehiclePdiResultPO();
						List<VehiclePdiResultPO> listtecpo = VehiclePdiResultPO.findBySQL("SELECT * FROM tt_vehicle_pdi_result WHERE DEALER_CODE=? and VIN=? and D_KEY=?",
								new Object[] { dealerCode,  map.get("VIN").toString(), 0 });
						if (listtecpo != null && listtecpo.size() > 0) {
							tecpo = listtecpo.get(0);
							dto1.setPdiResult(tecpo.getInteger("PDI_RESULT"));
						}
						//入库验收结果
						VsStockEntryItemPO tsdpo = new VsStockEntryItemPO();
						List<VsStockEntryItemPO> listtsdpo = VsStockEntryItemPO.findBySQL("SELECT * FROM tt_vs_stock_entry_item WHERE DEALER_CODE=? and VIN=? and D_KEY=?",
								new Object[] { dealerCode,  map.get("VIN").toString(), 0 });
						if (listtsdpo != null && listtsdpo.size() > 0) {
							tsdpo = listtsdpo.get(0);
							dto1.setInspectionResult(tsdpo.getInteger("INSPECTION_RESULT"));
						}

						List<VsInspectionMarPO> listDetail = VsInspectionMarPO.findBySQL("SELECT * FROM TT_VS_INSPECTION_MAR WHERE DEALER_CODE=? and SE_NO=? and VIN=? and D_KEY=?",
								new Object[] { dealerCode, seNo,  map.get("VIN").toString(), 0 });

						LinkedList dto2List = new LinkedList();
						if (listDetail != null && listDetail.size() > 0) {
							for (int j = 0; j < listDetail.size(); j++) {
								VsInspectionMarPO po3 = new VsInspectionMarPO();
								po3 = (VsInspectionMarPO) listDetail.get(i);

								SystemStatusPO po4 = SystemStatusPO.findFirst("SELECT * FROM tm_system_status WHERE STATUS_CODE=?", new Object[] { po3.getString("MAR_POSITION") });
								VsStockDetailListDto dto2 = new VsStockDetailListDto();
								dto2.setDamageDesc(po3.getString("MAR_REMARK"));
								dto2.setDamagePart(po4.getString("STATUS_DESC"));
								dto2List.add(dto2);
								dto2 = null;
								po3 = null;
								po4 = null;
							}
						}

						dto1.setVsStockDetialList(dto2List);
						resultList.add(dto1);
					}
			
			} 
			return SADCS002Cloud.receiveDate(resultList);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("==========================SADMS002异常============================");
			return "0";
		}	
	}

}
