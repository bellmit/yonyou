/**
 * 
 */
package com.yonyou.dms.vehicle.service.insuranceBusiness;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.InsProposalDTO;
import com.yonyou.dms.common.domains.DTO.customer.ProposalTrackDTO;
import com.yonyou.dms.common.domains.PO.customer.InsProposalPO;
import com.yonyou.dms.common.domains.PO.customer.ProposalTrackPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * @author sqh
 *
 */
@Service
public class InsProposalServiceImpl implements InsProposalService{

	@Override
	public List<Map> queryInsCompany(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DEALER_CODE,INSURATION_CODE,INSURATION_NAME FROM tm_insurance ");
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		return list;
	}

	@Override
	public List<Map> queryInsBroker(Map<String, String> queryParam) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select c.DEALER_CODE,c.INS_BROKER,t.EMPLOYEE_NAME from TM_INS_PROPOSAL c left join tm_employee t on c.INS_BROKER = t.employee_no group by c.INS_BROKER ");
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		return list;
	}

	@Override
	public PageInfoDto queryInsProposal(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sqlSb.append(" select distinct a.*,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,e.TRACER,e.INPUT_DATE,e.TRACE_TYPE from TM_INS_PROPOSAL a LEFT JOIN TM_VEHICLE b on a.DEALER_CODE = b.DEALER_CODE ");
		sqlSb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
		sqlSb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON b.DEALER_CODE = ts.DEALER_CODE  AND b.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sqlSb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON b.DEALER_CODE = tm.DEALER_CODE  AND b.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sqlSb.append(" left join (select b.PROPOSAL_CODE,b.INPUT_DATE,b.TRACER,b.TRACE_TYPE,b.DEALER_CODE from TM_PROPOSAL_TRACK b inner join ");
		sqlSb.append(" (select max(INPUT_DATE) as td,PROPOSAL_CODE,DEALER_CODE from TM_PROPOSAL_TRACK group by PROPOSAL_CODE,DEALER_CODE) bb ");
		sqlSb.append(" on b.PROPOSAL_CODE = bb.PROPOSAL_CODE and b.INPUT_DATE = bb.td and b.DEALER_CODE=bb.DEALER_CODE ) as e ");
		sqlSb.append(" on a.PROPOSAL_CODE = e.PROPOSAL_CODE and a.DEALER_CODE=e.DEALER_CODE where a.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if(!StringUtils.isNullOrEmpty(queryParam.get("isLocal"))){
            sqlSb.append(" and a.IS_INS_LOCAL= ? ");
            queryList.add(queryParam.get("isLocal"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("isAddProposal"))){
            sqlSb.append(" and a.IS_ADD_PROPOSAL= ? ");
            queryList.add(queryParam.get("isAddProposal"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("isCredit"))){
            sqlSb.append(" and a.IS_INS_CREDIT= ? ");
            queryList.add(queryParam.get("isCredit"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insCompany"))){
            sqlSb.append(" and a.INSURATION_CODE= ? ");
            queryList.add(queryParam.get("insCompany"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insBroker"))){
            sqlSb.append(" and a.INS_BROKER= ? ");
            queryList.add(queryParam.get("insBroker"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("proposalName"))){
            sqlSb.append(" and a.PROPOSAL_NAME like ? ");
            queryList.add("%"+ queryParam.get("proposalName") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
            sqlSb.append(" and a.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("proposalType"))){
            sqlSb.append(" and a.PROPOSAL_TYPE= ? ");
            queryList.add(queryParam.get("proposalType"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insChannl"))){
            sqlSb.append(" and a.INS_CHANNELS= ? ");
            queryList.add(queryParam.get("insChannl"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("formStatus"))){
            sqlSb.append(" and a.FORM_STATUS= ? ");
            queryList.add(queryParam.get("formStatus"));
        }
		Utility.sqlToDate(sqlSb,queryParam.get("insSalesDate"), queryParam.get("insSalesDateEnd"), "INS_SALES_DATE", "a");
		Utility.sqlToDate(sqlSb,queryParam.get("completedDateB"), queryParam.get("completedDateE"), "COMPLETED_DATE", "a");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("license"))){
            sqlSb.append("  and a.LICENSE like ? ");
            queryList.add("%"+ queryParam.get("license") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
            sqlSb.append(" and b.BRAND= ? ");
            queryList.add(queryParam.get("brand"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("series"))){
            sqlSb.append(" and b.SERIES= ? ");
            queryList.add(queryParam.get("series"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("model"))){
            sqlSb.append(" and b.MODEL= ? ");
            queryList.add(queryParam.get("model"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("trackPeople"))){
            sqlSb.append(" and e.TRACER= ? ");
            queryList.add(queryParam.get("trackPeople"));
        }
		sqlSb.append(" GROUP BY a.vin ");
//		Utility.getDateCond("e", "INPUT_DATE", BtrackDate, EtrackDate);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), queryList);
		
		return pageInfoDto;
	}

	@Override
	public List<Map> exportInsProposal(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sqlSb.append(" select distinct a.*,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,e.TRACER,e.INPUT_DATE,e.TRACE_TYPE from TM_INS_PROPOSAL a LEFT JOIN TM_VEHICLE b on a.DEALER_CODE = b.DEALER_CODE and a.VIN = b.VIN ");
		sqlSb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
		sqlSb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON b.DEALER_CODE = ts.DEALER_CODE  AND b.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sqlSb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON b.DEALER_CODE = tm.DEALER_CODE  AND b.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sqlSb.append(" left join (select b.PROPOSAL_CODE,b.INPUT_DATE,b.TRACER,b.TRACE_TYPE,b.DEALER_CODE from TM_PROPOSAL_TRACK b inner join ");
		sqlSb.append(" (select max(INPUT_DATE) as td,PROPOSAL_CODE,DEALER_CODE from TM_PROPOSAL_TRACK group by PROPOSAL_CODE,DEALER_CODE) bb ");
		sqlSb.append(" on b.PROPOSAL_CODE = bb.PROPOSAL_CODE and b.INPUT_DATE = bb.td and b.DEALER_CODE=bb.DEALER_CODE ) as e ");
		sqlSb.append(" on a.PROPOSAL_CODE = e.PROPOSAL_CODE and a.DEALER_CODE=e.DEALER_CODE where a.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if(!StringUtils.isNullOrEmpty(queryParam.get("isLocal"))){
            sqlSb.append(" and a.IS_INS_LOCAL= ? ");
            queryList.add(queryParam.get("isLocal"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("isAddProposal"))){
            sqlSb.append(" and a.IS_ADD_PROPOSAL= ? ");
            queryList.add(queryParam.get("isAddProposal"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("isCredit"))){
            sqlSb.append(" and a.IS_INS_CREDIT= ? ");
            queryList.add(queryParam.get("isCredit"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insCompany"))){
            sqlSb.append(" and a.INSURATION_CODE= ? ");
            queryList.add(queryParam.get("insCompany"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insBroker"))){
            sqlSb.append(" and a.INS_BROKER= ? ");
            queryList.add(queryParam.get("insBroker"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("proposalName"))){
            sqlSb.append(" and a.PROPOSAL_NAME like ? ");
            queryList.add("%"+ queryParam.get("proposalName") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
            sqlSb.append(" and a.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("proposalType"))){
            sqlSb.append(" and a.PROPOSAL_TYPE= ? ");
            queryList.add(queryParam.get("proposalType"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("insChannl"))){
            sqlSb.append(" and a.INS_CHANNELS= ? ");
            queryList.add(queryParam.get("insChannl"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("formStatus"))){
            sqlSb.append(" and a.FORM_STATUS= ? ");
            queryList.add(queryParam.get("formStatus"));
        }
		Utility.sqlToDate(sqlSb,queryParam.get("insSalesDate"), queryParam.get("insSalesDateEnd"), "INS_SALES_DATE", "a");
		Utility.sqlToDate(sqlSb,queryParam.get("completedDateB"), queryParam.get("completedDateE"), "COMPLETED_DATE", "a");
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("license"))){
            sqlSb.append("  and a.LICENSE like ? ");
            queryList.add("%"+ queryParam.get("license") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("brand"))){
            sqlSb.append(" and b.BRAND= ? ");
            queryList.add(queryParam.get("brand"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("series"))){
            sqlSb.append(" and b.SERIES= ? ");
            queryList.add(queryParam.get("series"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("model"))){
            sqlSb.append(" and b.MODEL= ? ");
            queryList.add(queryParam.get("model"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("trackPeople"))){
            sqlSb.append(" and e.TRACER= ? ");
            queryList.add(queryParam.get("trackPeople"));
        }
		List<Map> list = DAOUtil.findAll(sqlSb.toString(), queryList);
		return list;
	}

	@Override
	public PageInfoDto queryTrackHistory(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sqlSb.append(" select a.*,t.user_name from TM_PROPOSAL_TRACK a LEFT JOIN tm_user t on a.tracer = t.USER_ID where a.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("proposalCode"))){
			sqlSb.append(" and a.PROPOSAL_CODE= ? ");
            queryList.add(queryParam.get("proposalCode"));
        }
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), queryList);
		
		return pageInfoDto;
	}

	@Override
	public void addProposalTrack(ProposalTrackDTO proposalTrackDTO) throws ServiceBizException {
		ProposalTrackPO proposalTrackPO = new ProposalTrackPO();
		proposalTrackPO.setString("PROPOSAL_CODE", proposalTrackDTO.getProposalCode());
		proposalTrackPO.setString("TRACK_CONTENT", proposalTrackDTO.getTrackContent());
		proposalTrackPO.setInteger("TRACE_TYPE", proposalTrackDTO.getTrackType());
		proposalTrackPO.setString("FAILING_REASON", proposalTrackDTO.getFailingReason());
		proposalTrackPO.setTimestamp("TRACK_NEXT_DATE", proposalTrackDTO.getTrackNextDate());
		proposalTrackPO.setString("CUSTOMER_FEEDBACK", proposalTrackDTO.getCustomerFeedback());
		proposalTrackPO.setString("REMARK", proposalTrackDTO.getRemark());
		proposalTrackPO.setString("TRACER", proposalTrackDTO.getTracer());
		proposalTrackPO.saveIt();
		InsProposalPO insProposalPO = InsProposalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),proposalTrackDTO.getProposalCode());
		if(proposalTrackDTO.getTrackType() == 16061003){
			insProposalPO.setInteger("FORM_STATUS", 12291002);
		}else if(proposalTrackDTO.getTrackType() == 16061004){
			insProposalPO.setInteger("FORM_STATUS", 12291005);
		}
		insProposalPO.saveIt();
	}

	@Override
	public void update(InsProposalDTO insProposalDTO) throws ServiceBizException {
		InsProposalPO insProposalPO1 = InsProposalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),insProposalDTO.getProposalCode());
		insProposalPO1.setInteger("FORM_STATUS", 12291005);
		insProposalPO1.saveIt();
	}

	@Override
	public void updateStatus(InsProposalDTO insProposalDTO) throws ServiceBizException {
		InsProposalPO insProposalPO1 = InsProposalPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),insProposalDTO.getProposalCode());
		insProposalPO1.setInteger("FORM_STATUS", 12291004);
		insProposalPO1.saveIt();
	}

	@Override
	public PageInfoDto queryOwner(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		sqlSb.append(" select distinct a.OWNER_NO,a.OWNER_NAME,a.OWNER_PROPERTY,a.ZIP_CODE,a.ADDRESS,a.PHONE,a.MOBILE,a.CT_CODE as CERTIFICATE_TYPE,a.CERTIFICATE_NO as CERTIFICATE_CODE, ");
		sqlSb.append(" b.DEALER_CODE,b.LICENSE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,b.ENGINE_NO,b.VIN,b.EXHAUST_QUANTITY,b.COLOR,b.SALES_AGENT_NAME,b.VEHICLE_PURPOSE, ");
		sqlSb.append(" b.IS_SELF_COMPANY,d.SO_NO,d.TAX_SUM,d.BUSINESS_TYPE,d.STOCK_OUT_DATE from (" + CommonConstants.VM_OWNER + ") a,(" + CommonConstants.VM_VEHICLE + ") b ");
		sqlSb.append(" left join (select * from TT_SALES_ORDER dd where dd.BUSINESS_TYPE=13001001) as d on b.VIN=d.VIN and d.DEALER_CODE=b.DEALER_CODE ");
		sqlSb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON b.DEALER_CODE=tb.DEALER_CODE AND b.BRAND=tb.BRAND_CODE ");
		sqlSb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON b.DEALER_CODE = ts.DEALER_CODE  AND b.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
		sqlSb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON b.DEALER_CODE = tm.DEALER_CODE  AND b.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
		sqlSb.append(" where a.OWNER_NO=b.OWNER_NO and  a.DEALER_CODE=b.DEALER_CODE and (b.OWNER_NO<>'888888888888' and b.OWNER_NO<>'999999999999') and b.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if(!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))){
			sqlSb.append(" and a.OWNER_NO= ? ");
            queryList.add(queryParam.get("ownerNo"));
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))){
			sqlSb.append(" and a.OWNER_NAME like ? ");
            queryList.add("%"+ queryParam.get("ownerName") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("phone"))){
			sqlSb.append(" and a.PHONE like ? ");
            queryList.add("%"+ queryParam.get("phone") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("mobile"))){
			sqlSb.append(" and a.MOBILE like ? ");
            queryList.add("%"+ queryParam.get("mobile") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("license"))){
			sqlSb.append(" and b.LICENSE like ? ");
            queryList.add("%"+ queryParam.get("license") +"%");
        }
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sqlSb.append(" and b.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
	System.out.print(sqlSb.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlSb.toString(), queryList);
		
		return pageInfoDto;
	}

//	@Override
//	public List<Map> queryByProposalCode(@PathVariable(value = "proposalCode") String proposalCode) throws ServiceBizException {
//		StringBuffer sqlSb = new StringBuffer();
//		List<Object> queryList = new ArrayList<Object>();
//		sqlSb.append(" select * from TM_PROPOSAL_TRACK  where DEALER_CODE= ? ");
//		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
//			sqlSb.append(" and PROPOSAL_CODE= ? ");
//            queryList.add(proposalCode);
//            List<Map> list = DAOUtil.findAll(sqlSb.toString(), queryList);
//    		return list;
//	}


}
