package com.yonyou.dms.gacfca;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.WXMainDetailDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TmWxHistoryPO;
import com.yonyou.dms.framework.DAO.DAOUtil;

/**
 * @description 修改微信首要联系方式
 * @author Administrator
 *
 */
@Service
public class CLDMS014Impl implements CLDMS014 {

	final Logger logger = Logger.getLogger(CLDMS014Impl.class);

	@Override
	public int getCLDMS014(String dealerCode,Long userId,List<WXMainDetailDTO> voList) {
		logger.info("==========CLDMS014Impl执行===========");
		try{	
			if(dealerCode==null){
				logger.debug("dealerCode 为空，方法中断");
				return 0;
			}

			if(voList!=null && voList.size()>0){
				for(WXMainDetailDTO wxMainDetailDTO : voList){
					List list= queryVin(dealerCode,wxMainDetailDTO.getVin());
					if (list!= null && !list.isEmpty()){
						logger.debug("update TmWxHistoryPO set IS_WX_CONNECT=12781002 where DEALER_CODE = "+dealerCode+" and VIN = "+wxMainDetailDTO.getVin()+" and IS_WX_CONNECT = 12781001");
						TmWxHistoryPO.update("IS_WX_CONNECT=12781002", "DEALER_CODE = ? and VIN = ? and IS_WX_CONNECT = 12781001", dealerCode,wxMainDetailDTO.getVin());
					}
					TmWxHistoryPO po=new TmWxHistoryPO();
					po.setString("DEALER_CODE",dealerCode);
						po.setString("CUSTOMER_NAME",Utility.testString(wxMainDetailDTO.getCustomerName()) ? wxMainDetailDTO.getCustomerName() : null);
						po.setString("MOBILE_PHONE",Utility.testString(wxMainDetailDTO.getMobilePhone()) ? wxMainDetailDTO.getMobilePhone() : null);
						po.setDate("UPDATE_MOBILE_DATE",Utility.testString(wxMainDetailDTO.getUpdateMobileDate()) ? wxMainDetailDTO.getUpdateMobileDate() : null);
						po.setString("VIN",Utility.testString(wxMainDetailDTO.getVin()) ? wxMainDetailDTO.getVin() : null);
					po.setInteger("IS_WX_CONNECT",12781001);
						po.setString("OWNER_NO",Utility.testString(wxMainDetailDTO.getOwnerNo()) ? wxMainDetailDTO.getOwnerNo() : null);
					po.setDate("CREATE_AT",Utility.getCurrentDateTime());
					po.saveIt();
				}
				//globalTxnMgr.commit(ut);
			}	
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========CLDMS014Impl结束===========");
		}
	}

	/**
	 * @description 查找VIN编号
	 * @param entityCode
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public List queryVin(String entityCode,String vin) throws Exception{
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT  vin  from TM_WX_HISTORY where ENTITY_CODE='"+entityCode+"' and VIN='"+vin+"'");
		logger.debug(sql);
		return DAOUtil.findAll(sql.toString(), null);
	}
}
