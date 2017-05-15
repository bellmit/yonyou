package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADMS020Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS020DAO extends OemBaseDAO {

	public List<SADMS020Dto> queryInfo(String param) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer strSql = new StringBuffer(
				" SELECT  TF.VIN,TF.CUSTOMER_NO,TF.OWNER_NAME,TF.MOBILE,TF.GENDER,TF.ADDRESS,TF.E_MAIL,TF.ZIP_CODE,TF.DEALER_CODE \n");
		strSql.append(" FROM TI_WX_CUSTOMER_FEEDBACK_DCS TF,(" + getVwMaterialSql() + ") VM,TM_VEHICLE_DEC TV\n");
		strSql.append(" WHERE TF.IS_SCAN = 0 and TF.IS_DEL = 0 \n");
		strSql.append(" and TF.VIN=TV.VIN and VM.MATERIAL_ID=TV.MATERIAL_ID \n");
		strSql.append(
				" and (not exists (SELECT tc.BRAND_CODE FROM TI_BRAND_CODE_DCS tc WHERE tc.IS_DEL ='0' and tc.BRAND_CODE=vm.BRAND_CODE)) \n ");// 过滤FIAT品牌
		if (!"".equals(CommonUtils.checkNull(param))) {
			// params.add(param);
		}
		System.out.println(strSql.toString());
		List<Map> list = OemDAOUtil.findAll(strSql.toString(), null);
		List<SADMS020Dto> lis = new ArrayList<>();
		for (Map map : list) {
			SADMS020Dto dto = new SADMS020Dto();
			dto.setVin(map.get("VIN").toString());
			dto.setCustomerNo(CommonUtils.checkNull(map.get("CUSTOMER_NO").toString()));
			dto.setOwnerName(CommonUtils.checkNull(map.get("OWNER_NAME").toString()));
			dto.setMobile(CommonUtils.checkNull(map.get("MOBILE").toString()));
			dto.setGender(Integer.parseInt(CommonUtils.checkNull(map.get("GENDER"))));// 10471001
			dto.setAddress(CommonUtils.checkNull(map.get("ADDRESS").toString()));
			dto.seteMail(CommonUtils.checkNull(map.get("E_MAIL").toString()));
			dto.setZipCode(CommonUtils.checkNull(map.get("ZIP_CODE").toString()));
			dto.setDealerCode(map.get("DEALER_CODE").toString());
			lis.add(dto);
		}
		return lis;
	}

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String format = df.format(new Date());

	public void finishScanStatus(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE  ti_wx_customer_feedback_dcs  \n");
		sql.append("       set IS_SCAN = 1 ,UPDATE_BY= " + DEConstant.DE_UPDATE_BY + " ,update_date= '" + format
				+ "' where IS_SCAN = 0 and IS_DEL = 0  and vin='" + vin + "' \n");

		OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
	}

}
