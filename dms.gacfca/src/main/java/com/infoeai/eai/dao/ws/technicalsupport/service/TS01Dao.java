package com.infoeai.eai.dao.ws.technicalsupport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.wsServer.tsdealer.TsDealerVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TS01Dao extends OemBaseDAO {

	public List<TsDealerVO> getTS01VO(String updateDate) throws ParseException {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CINFO.*,DINFO.SMALL_ORG_CODE SAREA_CODE,DINFO.ORG_NAME SAREA_NAME,DINFO.BIG_ORG_CODE BAREA_CODE,DINFO.BIG_ORG_NAME BAREA_NAME, \n");
		sql.append("DINFO.IS_K4,DINFO.IS_FIAT,DINFO.IS_CJD \n");
		sql.append("	 FROM ("+getDlrInfo()+") DINFO \n");
		sql.append("INNER JOIN\n");
		sql.append("(select td.DEALER_ID,td.DEALER_CODE,td.DEALER_SHORTNAME,td.DEALER_NAME,td.MARKETING \n");
		sql.append("	,tc.COMPANY_CODE DEALER_COMPANY_CODE,tc.COMPANY_NAME DEALER_COMPANY_NAME,td.DEALER_LEVEL\n");
		sql.append("	,td.PARENT_DEALER_D,tdg.GROUP_NAME ,td.FOUND_DATE,td.ACURA_GHAS_TYPE,td.STATUS \n");
		sql.append("	,td.PROVINCE_ID,td.CITY_ID,td.ADDRESS,td.ZIP_CODE,td.PHONE,td.LINK_MAN, td.LINK_MAN_MOBILE \n");
		sql.append("	,td.FAX_NO,td.EMAIL,td.TAXES_NO,td.ERP_CODE,td.BEGIN_BANK,td.BANK_CODE,td.REMARK  \n");
		sql.append("	,td1.DEALER_CODE OEM_COMPANY_CODE,td1.DEALER_NAME OEM_COMPANY_NAME  \n");
		sql.append("	,td.CREATE_DATE,td.UPDATE_DATE\n");
		sql.append("		FROM TM_DEALER TD  LEFT JOIN TM_COMPANY TC ON  TD.COMPANY_ID = TC.COMPANY_ID   \n");
		sql.append("			left join TM_DEALER_GROUP TDG ON TD.DEALER_GROUP_ID = TDG.GROUP_ID  \n");
		sql.append("			left join TM_DEALER td1 on tc.COMPANY_ID = td1.OEM_COMPANY_ID  \n");
		sql.append(")cInfo on dInfo.DEALER_CODE = cInfo.DEALER_CODE  \n");
		sql.append("	WHERE 1=1\n");
		if (updateDate!=null&&!"".equals(updateDate)){
			sql.append("	AND CINFO.CREATE_DATE BETWEEN '"+updateDate+"' AND NOW() AND CINFO.UPDATE_DATE is null \n");
			sql.append("	OR CINFO.UPDATE_DATE BETWEEN '"+updateDate+"' AND NOW() \n");
		}
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setTsDealerVOList(mapList);
	}
	
	private List<TsDealerVO> setTsDealerVOList(List<Map> mapList) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TsDealerVO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TsDealerVO vo = new TsDealerVO();
				Long dealerId = CommonUtils.checkNull(map.get("DEALER_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("DEALER_ID")));
			    String dealerCode = CommonUtils.checkNull(map.get("DEALER_CODE"));
			    String dealerName = CommonUtils.checkNull(map.get("DEALER_SHORTNAME"));
			    String dealerFullname = CommonUtils.checkNull(map.get("DEALER_NAME"));
			    String dealerEname = CommonUtils.checkNull(map.get("MARKETING"));
			    String dealerCompanyCode = CommonUtils.checkNull(map.get("DEALER_COMPANY_CODE"));
			    String dealerCompanyName = CommonUtils.checkNull(map.get("DEALER_COMPANY_CODE"));
			    Integer dealerLevel = CommonUtils.checkNull(map.get("DEALER_LEVEL")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("DEALER_LEVEL")));
			    String parentDealerCode = CommonUtils.checkNull(map.get("PARENT_DEALER_D"));
			    String dealerUnit = CommonUtils.checkNull(map.get("GROUP_NAME"));
			    Date openDate = CommonUtils.checkNull(map.get("FOUND_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("FOUND_DATE")));
			    Integer policyType = CommonUtils.checkNull(map.get("ACURA_GHAS_TYPE")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("ACURA_GHAS_TYPE")));
			    Integer status = CommonUtils.checkNull(map.get("STATUS")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("STATUS")));
			    Long province = CommonUtils.checkNull(map.get("PROVINCE_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("PROVINCE_ID")));
			    Long city = CommonUtils.checkNull(map.get("CITY_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("CITY_ID")));
			    String address = CommonUtils.checkNull(map.get("ADDRESS"));
			    String zipcode = CommonUtils.checkNull(map.get("ZIP_CODE"));
			    String hotLine = CommonUtils.checkNull(map.get("PHONE"));
			    String linkMan = CommonUtils.checkNull(map.get("LINK_MAN"));
			    String linkManMobil = CommonUtils.checkNull(map.get("LINK_MAN_MOBILE"));
			    String fax = CommonUtils.checkNull(map.get("FAX_NO"));
			    String eMail = CommonUtils.checkNull(map.get("EMAIL"));
			    String taxesNo = CommonUtils.checkNull(map.get("TAXES_NO"));
			    String erpCode = CommonUtils.checkNull(map.get("ERP_CODE"));
			    String beginBank = CommonUtils.checkNull(map.get("BEGIN_BANK"));
			    String bankCode = CommonUtils.checkNull(map.get("BANK_CODE"));
			    String remark = CommonUtils.checkNull(map.get("REMARK"));
			    String sareaCode = CommonUtils.checkNull(map.get("SAREA_CODE"));
			    String sareaName = CommonUtils.checkNull(map.get("SAREA_NAME"));
			    String bareaCode = CommonUtils.checkNull(map.get("BAREA_CODE"));
			    String bareaName = CommonUtils.checkNull(map.get("BAREA_NAME"));
			    String oemCompanyCode = CommonUtils.checkNull(map.get("OEM_COMPANY_CODE"));
			    String oemCompanyName = CommonUtils.checkNull(map.get("OEM_COMPANY_NAME"));
			    String isK4 = CommonUtils.checkNull(map.get("IS_K4"));
			    String isFiat = CommonUtils.checkNull(map.get("IS_FIAT"));
			    String isCjd = CommonUtils.checkNull(map.get("IS_CJD"));
				    
				vo.setAddress(address);
				vo.setBankCode(bankCode);
				vo.setBareaCode(bareaCode);
				vo.setBareaName(bareaName);
				vo.setBeginBank(beginBank);
				vo.setCity(city);
				vo.setDealerCode(dealerCode);
				vo.setDealerCompanyCode(dealerCompanyCode);
				vo.setDealerCompanyName(dealerCompanyName);
				vo.setDealerEname(dealerEname);
				vo.setDealerFullname(dealerFullname);
				vo.setDealerId(dealerId);
				vo.setDealerLevel(dealerLevel);
				vo.setDealerName(dealerName);
				vo.setDealerUnit(dealerUnit);
				vo.setEMail(eMail);
				vo.setErpCode(erpCode);
				vo.setFax(fax);
				vo.setHotLine(hotLine);
				vo.setIsCjd(isCjd);
				vo.setIsFiat(isFiat);
				vo.setIsK4(isK4);
				vo.setLinkMan(linkMan);
				vo.setLinkManMobil(linkManMobil);
				vo.setOemCompanyCode(oemCompanyCode);
				vo.setOpenDate(openDate);
				vo.setParentDealerCode(parentDealerCode);
				vo.setPolicyType(policyType);
				vo.setProvince(province);
				vo.setRemark(remark);
				vo.setSareaCode(sareaCode);
				vo.setSareaName(sareaName);
				vo.setStatus(status);
				vo.setTaxesNo(taxesNo);
				vo.setZipcode(zipcode);
				resultList.add(vo);
			}
		}
		return resultList;
	}


	private String getDlrInfo(){
		StringBuffer sql = new StringBuffer();
		sql.append("	SELECT TM.DEALER_ID,TM.DEALER_CODE,TM.DEALER_SHORTNAME, \n");
		sql.append("		TOR2.ORG_DESC BIG_ORG_NAME,TOR3.ORG_DESC ORG_NAME ,TOR3.ORG_ID AS SMALL_ORG_ID , \n");
		sql.append("		TOR3.PARENT_ORG_ID AS BIG_ORG_ID ,TOR3.ORG_CODE SMALL_ORG_CODE,TOR2.ORG_CODE BIG_ORG_CODE,\n");
		sql.append("		IFNULL(TM.IS_K4,10041002) IS_K4, IFNULL(TM.IS_FIAT,10041002) IS_FIAT,IFNULL(TM.IS_CJD,10041002) IS_CJD \n");
		sql.append("		FROM TM_DEALER TM ,TM_DEALER_ORG_RELATION TDOR ,TM_ORG  TOR3 ,TM_ORG TOR2  \n");
		sql.append("			WHERE TDOR.DEALER_ID = TM.DEALER_ID \n");
		sql.append("				AND (TOR3.ORG_ID = TDOR.ORG_ID \n");
		sql.append("				AND TOR3.ORG_LEVEL = 3 )  \n");
		sql.append("				AND (TOR3.PARENT_ORG_ID = TOR2.ORG_ID  \n");
		sql.append("				AND TOR2.ORG_LEVEL = 2 ) \n");
		sql.append("				AND  TOR3.BUSS_TYPE = 12351002 \n");
		sql.append("				AND TOR2.BUSS_TYPE = 12351002 \n");
		sql.append("				AND TM.DEALER_TYPE = 10771002 \n");
		sql.append("			GROUP BY TOR2.ORG_DESC,TOR3.ORG_DESC,TM.DEALER_ID,TM.DEALER_CODE \n");
		sql.append("				,TM.DEALER_SHORTNAME,TOR3.ORG_ID,TOR3.PARENT_ORG_ID,TOR2.ORG_CODE,TOR3.ORG_CODE,  \n");
		sql.append("				TM.IS_K4,TM.IS_FIAT,TM.IS_CJD \n");
		return sql.toString();
	}

}
