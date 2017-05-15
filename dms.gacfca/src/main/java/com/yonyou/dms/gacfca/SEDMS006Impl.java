package com.yonyou.dms.gacfca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PtDlyInfoDetailDto;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverItemPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 下发配件货运单信息
 * @author Administrator
 *
 */
@Service
public class SEDMS006Impl implements SEDMS006{

	final Logger logger = Logger.getLogger(SEDMS006Impl.class);

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public int getSEDMS006(String dealerCode,LinkedList<PtDlyInfoDto> ptDlyInfoList) {
		logger.info("==========SEDMS006Impl执行===========");
		try{
			if (dealerCode == null || "".equals(dealerCode.trim())) {
				logger.info("dealerCode 为空，方法中断");
				return 0;
			}
			if (ptDlyInfoList != null && ptDlyInfoList.size() > 0) {
				for(PtDlyInfoDto ptDlyInfoDto : ptDlyInfoList){
					TtPtDeliverDcsPO deliverPO = new TtPtDeliverDcsPO();
					deliverPO.setString("DEALER_CODE",dealerCode);
					deliverPO.setString("DELIVERY_ORDER_NO",ptDlyInfoDto.getDeliverynote());
					Date date=null;
					if (ptDlyInfoDto.getInvoiccreationdate()!=null){
						date=ptDlyInfoDto.getInvoiccreationdate();
					} else{
						date= new Date();
					}
					deliverPO.setDate("DELIVERY_TIME",date);
					deliverPO.setDate("INVOICE_DATE",date);
					deliverPO.setString("OEM_ORDER_NO",ptDlyInfoDto.getElinkorderno());
					String deliverNo = commonNoService.getSystemOrderNo(CommonConstants.REGEDIT_DO);
					deliverPO.setString("ORDER_REGEDIT_NO",deliverNo);
					deliverPO.setInteger("IN_STORAGED",Integer.parseInt(CommonConstants.DICT_IS_NO));
					deliverPO.setInteger("IS_SIGNED",Integer.parseInt(CommonConstants.DICT_IS_NO));
					deliverPO.setInteger("IS_VALID",Integer.parseInt(CommonConstants.DICT_IS_YES));
					deliverPO.setString("CASE_ID",ptDlyInfoDto.getCaseId());
					deliverPO.setDouble("QUANTITY",ptDlyInfoDto.getQuantity());
					deliverPO.setInteger("D_KEY",CommonConstants.D_KEY);
					deliverPO.setString("CREATE_BY","1");
					deliverPO.setDate("CREATE_AT",new Date());
					deliverPO.saveIt();
					if (ptDlyInfoDto.getPtDlyInfoDetailDtos()!= null && ptDlyInfoDto.getPtDlyInfoDetailDtos().size()>0){
						for (PtDlyInfoDetailDto ptDlyInfoDetailDto: ptDlyInfoDto.getPtDlyInfoDetailDtos()) {
							TtPtDeliverItemPO deliverItemPO = new TtPtDeliverItemPO();
							deliverItemPO.setString("DEALER_CODE",dealerCode);
							deliverItemPO.setString("PART_NO",ptDlyInfoDetailDto.getPartno());
							deliverItemPO.setString("PART_NAME",ptDlyInfoDetailDto.getPartname());
							deliverItemPO.setDouble("AMOUNT",ptDlyInfoDetailDto.getValueofgoods());
							deliverItemPO.setDouble("SUPPLY_QTY",ptDlyInfoDetailDto.getSapinvoicequantity());
							deliverItemPO.setDate("DELIVERY_TIME",date);
							deliverItemPO.setDouble("PLAN_PRICE",ptDlyInfoDetailDto.getNetprice());
							deliverItemPO.setInteger("SORT",ptDlyInfoDetailDto.getDeliverynoteItem());
							deliverItemPO.setString("ORDER_REGEDIT_NO",deliverNo);
							deliverItemPO.setString("OEM_ORDER_NO",ptDlyInfoDto.getElinkorderno());
							deliverItemPO.setInteger("D_KEY",CommonConstants.D_KEY);
							deliverItemPO.setString("CREATE_BY","1");
							deliverItemPO.setDate("CREATE_AT",new Date());
							deliverItemPO.saveIt();
						}
					}
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========SEDMS006Impl结束===========");
		}
	}
}
