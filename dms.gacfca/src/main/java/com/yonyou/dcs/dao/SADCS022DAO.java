package com.yonyou.dcs.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.SADMS022Dto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class SADCS022DAO extends OemBaseDAO {

	public List<SADMS022Dto> queryLoanRatMaintainInfo() throws ParseException {
		StringBuffer sql = new StringBuffer();
		sql.append("select tlrm.id,tlrm.code_id,\n");
		sql.append("       tc.btc_code as bank_id ,\n");
		sql.append("       tlrm.dpm_s ,\n");
		sql.append("       tlrm.dpm_e,\n");
		sql.append("       tlrm.rate,\n");
		sql.append("       tlrm.effective_date_s,\n");
		sql.append("       tlrm.effective_date_e,\n");
		sql.append("       tlrm.create_date,\n");
		sql.append("       tlrm.create_by,\n");
		sql.append("       tlrm.update_date,\n");
		sql.append("       tlrm.update_by,\n");
		sql.append("       tlrm.is_valid,\n");
		sql.append("       tlrm.brand_group_id,\n");
		sql.append("       tlrm.series_group_id, \n");
		sql.append("       tlrm.style_group_id, \n");
		sql.append("       tlrm.installment_number \n");
		sql.append("          from tm_loan_rat_maintain tlrm ,tc_bank tc\n ");
		sql.append("       where tc.id=tlrm.code_id and tlrm.is_scan = 0  limit 1,200");
		// 取前200条
		// sql.append(" fetch first 200 row only");
		List<Map> lis = OemDAOUtil.findAll(sql.toString(), null);
		List<SADMS022Dto> list = new ArrayList<SADMS022Dto>();
		for (Map map : lis) {
			SADMS022Dto dto = new SADMS022Dto();

			if (map.get("ID") != null) {
				dto.setId(map.get("ID").toString());

			}
			if (map.get("BANK_ID") != null) {

				dto.setBankCode(map.get("BANK_ID").toString());
			}
			if (map.get("DPM_S") != null) {
				dto.setDpmS(Double.parseDouble(map.get("DPM_S").toString()));
			}
			if (map.get("DPM_E") != null) {
				dto.setDpmE(Double.parseDouble(map.get("DPM_E").toString()));
			}
			if (map.get("RATE") != null) {
				dto.setRate(Double.parseDouble(map.get("RATE").toString()));
			}
			DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
			if (map.get("EFFECTIVE_DATE_S") != null) {
				Date effective_date_s = df1.parse(map.get("EFFECTIVE_DATE_S").toString());
				dto.setEffectiveDateS(effective_date_s);
			}
			if (map.get("EFFECTIVE_DATE_E") != null) {
				Date effective_date_e = df1.parse(map.get("EFFECTIVE_DATE_E").toString().toString());
				dto.setEffectiveDateE(effective_date_e);
			}
			if (map.get("CREATE_DATE") != null) {
				Date create_date = df1.parse(map.get("CREATE_DATE").toString());
				dto.setCreateDate(create_date);
			}
			if (map.get("CREATE_BY") != null) {
				dto.setCreateBy(Long.parseLong(map.get("CREATE_BY").toString()));
			}
			if (map.get("UPDATE_BY") != null) {
				dto.setUpdateBy(Long.parseLong(map.get("UPDATE_BY").toString()));
			}
			if (map.get("UPDATE_DATE") != null) {
				dto.setUpdateDate(df1.parse(map.get("UPDATE_DATE").toString()));
			}
			if (map.get("BRAND_GROUP_ID") != null) {
				dto.setBrandCode(map.get("BRAND_GROUP_ID").toString());
			}
			if (map.get("SERIES_GROUP_ID") != null) {

				dto.setSeriesCode(map.get("SERIES_GROUP_ID").toString());
			}
			if (map.get("STYLE_GROUP_ID") != null) {

				dto.setStyleCode(map.get("STYLE_GROUP_ID").toString());
				List<Map> list1 = this.queryModelCode(map.get("STYLE_GROUP_ID").toString());
				if (list1.size() != 0) {
					String modelCode = (String) list1.get(0).get("GROUP_CODE");
					dto.setModelCode(modelCode);
				}
			}
			if (map.get("INSTALLMENT_NUMBER") != null) {
				dto.setInstallmentNumber(Integer.parseInt((map.get("INSTALLMENT_NUMBER").toString())));
			}

			if (map.get("IS_VALID") != null) {

				dto.setIsValid(Integer.parseInt(map.get("IS_VALID").toString()));
			}
			list.add(dto);

		}
		return list;
	}

	private List<Map> queryModelCode(String groupCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T1.GROUP_CODE, T1.GROUP_NAME \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP T1, TM_VHCL_MATERIAL_GROUP T2 \n");
		sql.append(" WHERE T1.GROUP_ID = T2.PARENT_GROUP_ID \n");
		sql.append("   AND T1.STATUS = " + OemDictCodeConstants.STATUS_ENABLE + "\n");
		if (groupCode != null) {
			sql.append("     AND T2.GROUP_CODE = '" + groupCode + "'\n");
		}
		sql.append("   AND T2.GROUP_LEVEL = 4 \n");
		sql.append("   AND T2.GROUP_CODE <> T2.GROUP_NAME \n");// 过滤老的物料组车型
		sql.append("   AND T2.GROUP_NAME <> 'TBD' \n");// 过滤TBD车型
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<SADMS022Dto> queryLoanRatMaintainInfoMore(String array) throws ParseException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TLRM.ID,TLRM.CODE_ID,\n");
		sql.append("       TC.BTC_CODE as BANK_ID ,\n");
		sql.append("       TLRM.DPM_S ,\n");
		sql.append("       TLRM.DPM_E,\n");
		sql.append("       TLRM.RATE,\n");
		sql.append("       TLRM.EFFECTIVE_DATE_S,\n");
		sql.append("       TLRM.EFFECTIVE_DATE_E,\n");
		sql.append("       TLRM.CREATE_DATE,\n");
		sql.append("       TLRM.CREATE_BY,\n");
		sql.append("       TLRM.UPDATE_DATE,\n");
		sql.append("       TLRM.UPDATE_BY,\n");
		sql.append("       TLRM.IS_VALID,\n");
		sql.append("       TLRM.BRAND_GROUP_ID,\n");
		sql.append("       TLRM.SERIES_GROUP_ID, \n");
		sql.append("       TLRM.STYLE_GROUP_ID, \n");
		sql.append("       TLRM.INSTALLMENT_NUMBER \n");
		sql.append("          FROM TM_LOAN_RAT_MAINTAIN TLRM ,TC_BANK TC\n ");
		sql.append("       WHERE TC.ID=TLRM.CODE_ID AND TLRM.IS_SCAN = 0");
		sql.append("       and TLRM.ID in (" + array + ")");
		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		List<SADMS022Dto> list = new ArrayList<SADMS022Dto>();
		for (Map map : findAll) {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			SADMS022Dto dto = new SADMS022Dto();
			dto.setIsValid(Integer.parseInt(map.get("is_valid").toString()));
			dto.setInstallmentNumber(Integer.parseInt(map.get("installment_number").toString()));
			dto.setDpmE(Double.parseDouble(map.get("dpm_e").toString()));
			dto.setDpmS(Double.parseDouble(map.get("dpm_s").toString()));
			dto.setRate(Double.parseDouble(map.get("rate").toString()));
			dto.setId(map.get("ID").toString());
			dto.setBankCode(map.get("BANK_ID").toString());
			dto.setDpmS(map.get("DPM_S") == null ? null : Double.parseDouble(map.get("DPM_S").toString()));
			dto.setDpmE(map.get("DPM_E") == null ? null : Double.parseDouble(map.get("DPM_E").toString()));
			dto.setRate(map.get("RATE") == null ? null : Double.parseDouble(map.get("RATE").toString()));

			dto.setEffectiveDateS(df.parse(map.get("EFFECTIVE_DATE_S").toString()));
			dto.setEffectiveDateE(df.parse(map.get("EFFECTIVE_DATE_E").toString()));
			dto.setCreateDate(df.parse(map.get("CREATE_DATE").toString()));
			dto.setCreateBy(map.get("CREATE_BY") == null ? null : Long.parseLong(map.get("CREATE_BY").toString()));
			if (!"".equals(CommonUtils.checkNull(map.get("UPDATE_DATE")))) {

				dto.setUpdateDate(df.parse(map.get("UPDATE_DATE").toString()));
			}
			dto.setUpdateBy(map.get("UPDATE_BY") == null ? null : Long.parseLong(map.get("UPDATE_BY").toString()));
			dto.setBrandCode(map.get("BRAND_GROUP_ID").toString());
			dto.setSeriesCode(map.get("SERIES_GROUP_ID").toString());
			dto.setStyleCode(map.get("STYLE_GROUP_ID").toString());
			dto.setInstallmentNumber(map.get("INSTALLMENT_NUMBER") == null ? null
					: Integer.parseInt((map.get("INSTALLMENT_NUMBER").toString())));
			List<Map> lis = this.queryModelCode(map.get("STYLE_GROUP_ID").toString());
			if (lis.size() != 0) {
				String modelCode = (String) lis.get(0).get("GROUP_CODE");
				dto.setModelCode(modelCode);
			}
			dto.setIsValid(map.get("IS_VALID") == null ? null : Integer.parseInt(map.get("IS_VALID").toString()));
			list.add(dto);

		}
		return list;
	}

	public List<SADMS022Dto> queryLoanRatMaintainInfoEach(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TLRM.ID,TLRM.CODE_ID,\n");
		sql.append("       TC.BTC_CODE as BANK_ID ,\n");
		sql.append("       TLRM.DPM_S ,\n");
		sql.append("       TLRM.DPM_E,\n");
		sql.append("       TLRM.RATE,\n");
		sql.append("       TLRM.EFFECTIVE_DATE_S,\n");
		sql.append("       TLRM.EFFECTIVE_DATE_E,\n");
		sql.append("       TLRM.CREATE_DATE,\n");
		sql.append("       TLRM.CREATE_BY,\n");
		sql.append("       TLRM.UPDATE_DATE,\n");
		sql.append("       TLRM.UPDATE_BY,\n");
		sql.append("       TLRM.IS_VALID,\n");
		sql.append("       TLRM.BRAND_GROUP_ID,\n");
		sql.append("       TLRM.SERIES_GROUP_ID, \n");
		sql.append("       TLRM.STYLE_GROUP_ID, \n");
		sql.append("       TLRM.INSTALLMENT_NUMBER \n");
		sql.append("          FROM TM_LOAN_RAT_MAINTAIN TLRM ,TC_BANK TC\n ");
		sql.append("       WHERE TC.ID=TLRM.CODE_ID AND TLRM.IS_SCAN = 0");
		sql.append("       and TLRM.ID = " + id);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		List<SADMS022Dto> ll = new ArrayList<>();
		for (Map map : list) {
			try {
				SADMS022Dto dto = new SADMS022Dto();
				dto.setId(map.get("ID").toString());
				dto.setBankCode(map.get("BANK_ID").toString());
				dto.setDpmS(map.get("DPM_S") == null ? null : Double.parseDouble(map.get("DPM_S").toString()));
				dto.setDpmE(map.get("DPM_E") == null ? null : Double.parseDouble(map.get("DPM_E").toString()));
				dto.setRate(map.get("RATE") == null ? null : Double.parseDouble(map.get("RATE").toString()));
				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date effective_date_s = df.parse(map.get("EFFECTIVE_DATE_S").toString().toString());
				dto.setEffectiveDateS(effective_date_s);
				Date effective_date_e = df.parse(map.get("EFFECTIVE_DATE_E").toString());
				dto.setEffectiveDateE(effective_date_e);
				Date create_date = df.parse(map.get("CREATE_DATE").toString());
				dto.setCreateDate(create_date);
				dto.setCreateBy(map.get("CREATE_BY") == null ? null : Long.parseLong(map.get("CREATE_BY").toString()));
				if(map.get("UPDATE_DATE")!=null && !map.get("UPDATE_DATE").toString().equals("")){
					Date update_date = df.parse(map.get("UPDATE_DATE").toString());
					dto.setUpdateDate(update_date);
				}else{
					dto.setUpdateDate(null);
				}
				dto.setUpdateBy(map.get("UPDATE_BY") == null ? null : Long.parseLong(map.get("UPDATE_BY").toString()));
				dto.setBrandCode(map.get("BRAND_GROUP_ID").toString());
				dto.setSeriesCode(map.get("SERIES_GROUP_ID").toString());
				dto.setStyleCode(map.get("STYLE_GROUP_ID").toString());
				dto.setInstallmentNumber(map.get("INSTALLMENT_NUMBER") == null ? null
						: Integer.parseInt((map.get("INSTALLMENT_NUMBER").toString())));
				List<Map> lis = this.queryModelCode(map.get("STYLE_GROUP_ID").toString());
				if (lis.size() != 0) {
					String modelCode = (String) lis.get(0).get("GROUP_CODE");
					dto.setModelCode(modelCode);
				}
				dto.setIsValid(map.get("IS_VALID") == null ? null : Integer.parseInt(map.get("IS_VALID").toString()));
				ll.add(dto);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return ll;
	}

}
