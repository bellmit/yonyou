package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADCS074DTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS074Dao extends OemBaseDAO {
	

	/**
	 * 查询data ： vin，开票日期
	 */
	public List<SADCS074DTO> queryInvoiceDateSend(String vin,String invoiceDae){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT SR.INVOICE_DATE,TV.VIN\n");
		sql.append("FROM TT_VS_SALES_REPORT SR LEFT JOIN TM_VEHICLE_DEC TV\n");
		sql.append("ON SR.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("WHERE TV.VIN = '"+vin+"'\n");
		sql.append("AND to_char(SR.INVOICE_DATE,'yyyy-MM-dd')= to_char('"+invoiceDae+"','yyyy-MM-dd')\n");

		List<Map> listMap=OemDAOUtil.findAll(sql.toString(), null);
		List<SADCS074DTO> dtoList=null;
		SADCS074DTO dto=null;
		if(null!=listMap&&listMap.size()>0){
			dtoList=new ArrayList<SADCS074DTO>();
			for(int i=0;i<listMap.size();i++){
				dto=new SADCS074DTO();
				dto.setInvoiceDate(CommonUtils.parseDate(CommonUtils.checkNull(listMap.get(i).get("INVOICE_DATE"))));
				dto.setVin(CommonUtils.checkNull(listMap.get(i).get("VIN")));
			}
		}
		return dtoList;
	}
	
	/**
	 * 查询下发所有存有车辆信息的经销商代码
	 */
	public List<String> queryIDealerCode(String vin){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TDR.DMS_CODE FROM TI_DEALER_RELATION TDR\n");
		sql.append("inner JOIN (\n");
		sql.append("SELECT CASE\n");
		sql.append("          WHEN right (\n");
		sql.append("                  TM.DEALER_CODE,\n");
		sql.append("                  LENGTH (TM.DEALER_CODE) - (LENGTH (TM.DEALER_CODE) - 1)) =\n");
		sql.append("                  'A'\n");
		sql.append("          THEN\n");
		sql.append("             replace (TM.DEALER_CODE, 'A', '')\n");
		sql.append("          ELSE\n");
		sql.append("             TM.DEALER_CODE\n");
		sql.append("       END\n");
		sql.append("          AS DEALER_CODE\n");
		sql.append("FROM TT_VS_SALES_REPORT SR \n");
		sql.append("INNER JOIN TM_VEHICLE_DEC TV ON TV.VEHICLE_ID = SR.VEHICLE_ID\n");
		sql.append("INNER JOIN TM_DEALER TM ON TM.DEALER_ID = SR.DEALER_ID\n");
		/*参数位置*/
		sql.append("WHERE TV.VIN =  '"+vin+"'\n");
		/*参数位置*/
		sql.append("GROUP BY TM.DEALER_CODE\n");
		sql.append("union \n");
		sql.append("SELECT TM.DEALER_CODE\n");
		sql.append("FROM TM_DEALER TM INNER JOIN (\n");
		sql.append("SELECT CASE\n");
		sql.append("          WHEN right (\n");
		sql.append("                  WR.DEALER_CODE,\n");
		sql.append("                  LENGTH (WR.DEALER_CODE) - (LENGTH (WR.DEALER_CODE) - 1)) =\n");
		sql.append("                  'A'\n");
		sql.append("          THEN\n");
		sql.append("             replace (WR.DEALER_CODE, 'A', '')\n");
		sql.append("          ELSE\n");
		sql.append("             WR.DEALER_CODE\n");
		sql.append("       END\n");
		sql.append("          AS DEALER_CODE\n");
		sql.append("  FROM TT_WR_REPAIR WR\n");
		/*参数位置*/
		sql.append(" WHERE WR.VIN = '"+vin+"'\n");
		/*参数位置*/
		sql.append("GROUP BY WR.DEALER_CODE\n");
		sql.append(")T ON T.DEALER_CODE = TM.DEALER_CODE\n");
		sql.append("GROUP BY TM.DEALER_CODE\n");
		sql.append(") T on T.DEALER_CODE = TDR.DCS_CODE\n");
		
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null); 
		
		List<String> dealerlist=new ArrayList<>();
		
		if(null!=list&&list.size()>0){
			for(int i=0;i<list.size();i++){
				dealerlist.add(CommonUtils.checkNull(list.get(i).get("DMS_CODE")));
			}
		}
		
		return dealerlist;
	}
}
