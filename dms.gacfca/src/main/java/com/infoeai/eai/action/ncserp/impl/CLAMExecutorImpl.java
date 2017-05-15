package com.infoeai.eai.action.ncserp.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.po.TtClaimsNoticeDetailPO;
import com.infoeai.eai.po.TtClaimsNoticePO;
import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
/**
 * @author Benzc
 * @date 2017-04-27
 * YORD
 * Purchase Order Create(new)
 */
@Service
public class CLAMExecutorImpl {
	private static Logger logger = Logger.getLogger(CLAMExecutorImpl.class);
	Calendar sysDate = Calendar.getInstance();

	@Autowired
	SICommonDao siComDAO;
	
	public String insertNodeDetail(List<Map<String, String>> returnItemList,Map<String,String> mapHead)throws Exception{
		String returnResult = "01"; // 返回02表示成功,01表示失败
		Map<String,String> mapItem = new HashMap<String, String>();
		 try{
			 //Long nodeId = Long.parseLong(SequenceManager.getSequence(null));
			 TtClaimsNoticePO ttNotice= this.setHeadVo(mapHead);
			 ttNotice.setLong("CREATE_BY", new Long(11111));
			 ttNotice.setDate("CREATE_DATE", sysDate.getTime());
			 ttNotice.setInteger("IS_INVOICE", 50201001);
			 ttNotice.saveIt();
			for(int i=0;i<returnItemList.size();i++){
				mapItem = returnItemList.get(i);
				TtClaimsNoticeDetailPO ttNoticeDetail = this.setItemVo(mapItem);
				ttNoticeDetail.setLong("CREATE_BY", new Long(11111));
				ttNoticeDetail.setDate("CREATE_DATE", sysDate.getTime());
				ttNoticeDetail.saveIt();
			}
			 
		 }catch (Throwable e) {
			     logger.info("=============CLAM 插入业务表处理异常============");
			     logger.error(e.getMessage(), e);
				throw new Exception("============CLAM 插入业务表处理异常=================="+e);
			}
		 returnResult="02";
		return returnResult;
	}
	
	public String swtTodealer(String swt){
		List<TmCompanyPO> comList= null;
		List<TmDealerPO> derList= null;
		Long companyId = null;
		String dealerCode = null;
		
		comList = TmCompanyPO.findBySQL("SELECT * FROM tm_company WHERE SWT_CODE=?", swt);
		
		if (comList != null && comList.size() >0){
			TmCompanyPO ret = comList.get(0);
			companyId = (Long) ret.get("COMPANY_ID");
			derList = TmDealerPO.findBySQL("SELECT * FROM TM_DEALER WHERE COMPANY_ID=?", companyId);

			if(derList != null && derList.size()>0){
				TmDealerPO code = derList.get(0); 
				dealerCode = (String) code.get("DEALER_CODE");
			}else{
				dealerCode = swt;
			}
		}else{
			dealerCode = swt;
		}
		return dealerCode;
	}
	
	public TtClaimsNoticePO setHeadVo(Map<String,String> map) throws Exception{
		TtClaimsNoticePO headVo = new TtClaimsNoticePO();
		 try{
			//headVo.setClaimsBillingId(id);
			if(map.get("DBN_NO")!=null && map.get("DBN_NO").length() >0)
			    headVo.setString("VBN_NO", map.get("DBN_NO").toString());
			if(map.get("BUKRS")!=null && map.get("BUKRS").length() >0)
			    headVo.setString("COMPANY_CODE", map.get("BUKRS").toString());
			if(map.get("BUTXT")!=null && map.get("BUTXT").length() >0)
			    headVo.setString("COMPANY_NAME", map.get("BUTXT").toString());
			if(map.get("CLAM_MON_FROM")!=null && map.get("CLAM_MON_FROM").length() >0)
			    headVo.setString("CCLAIMS_MONTH_CYCLE_FROM", map.get("CLAM_MON_FROM").toString());
			if(map.get("CLAM_MON_TO")!=null &&map.get("CLAM_MON_TO").length() >0)
			    headVo.setString("CCLAIMS_MONTH_CYCLE_TO", map.get("CLAM_MON_TO"));
	/*		if(map.get("DEALER")!=null && map.get("DEALER").length() >0)
			    headVo.setSvcDealer(this.swtTodealer(map.get("DEALER").toString()));*/
			if(map.get("LIFNR")!=null && map.get("LIFNR").length() >0)
			    headVo.setString("SVC_DEALER", this.swtTodealer(map.get("LIFNR").toString()));
			if(map.get("DEALER_NAME") !=null && map.get("DEALER_NAME").length() >0)
			    headVo.setString("SVC_DEALER_NAME", map.get("DEALER_NAME").toString());
			if(map.get("F_AMT") !=null && map.get("F_AMT").length() >0)
			    headVo.setDouble("F_AMT", Double.parseDouble(map.get("F_AMT").toString()));
			if(map.get("W_AMT")!=null && map.get("W_AMT").length() >0)
			    headVo.setDouble("W_AMT", Double.parseDouble(map.get("W_AMT").toString()));
			if(map.get("EXTENDED_W") !=null && map.get("EXTENDED_W").length() >0)
			    headVo.setDouble("EXTENDED_W", Double.parseDouble(map.get("EXTENDED_W").toString()));
			if(map.get("GOODW_AMT")!=null && map.get("GOODW_AMT").length() >0)
				headVo.setDouble("GOODWILL_AMT", Double.parseDouble(map.get("GOODW_AMT").toString()));
			if(map.get("LOCALGW_AMT")!=null && map.get("LOCALGW_AMT").length() >0)
				headVo.setDouble("LOCAL_GOODWILL", Double.parseDouble(map.get("LOCALGW_AMT").toString()));
			if(map.get("RECALL_AMT")!=null && map.get("RECALL_AMT").length() >0)
				headVo.setDouble("RECALL_AMT", Double.parseDouble(map.get("RECALL_AMT").toString()));
			if(map.get("MOPAR_AMT")!=null && map.get("MOPAR_AMT").length() >0)
				headVo.setDouble("Mopar_Amt", Double.parseDouble(map.get("MOPAR_AMT").toString()));
			if(map.get("PDI_AMT") !=null && map.get("PDI_AMT").length() >0)
				headVo.setDouble("Pdi_Amt", Double.parseDouble(map.get("PDI_AMT").toString()));
			if(map.get("TCR_AMT") !=null && map.get("TCR_AMT").length() >0)
				headVo.setDouble("Tcr_Amt", Double.parseDouble(map.get("TCR_AMT").toString()));
			if(map.get("OTHERS_AMT")!=null && map.get("OTHERS_AMT").length() >0)
				headVo.setDouble("Others_Amt", Double.parseDouble(map.get("OTHERS_AMT").toString()));
			if(map.get("CCBACK_G_AMT") !=null && map.get("CCBACK_G_AMT").length() >0)
				headVo.setDouble("Claim_Chargerback_Amt", Double.parseDouble(map.get("CCBACK_G_AMT").toString()));
			if(map.get("CCBACK_L_AMT")!=null && map.get("CCBACK_L_AMT").length() >0)
				headVo.setDouble("Claim_Chargerback_AmtL", Double.parseDouble(map.get("CCBACK_L_AMT").toString()));
			if(map.get("TOTAL_AMT") !=null && map.get("TOTAL_AMT").length() >0)
				headVo.setDouble("Total_Amt", Double.parseDouble(map.get("TOTAL_AMT").toString()));
			if(map.get("VAT_INVO_AMT")!=null && map.get("VAT_INVO_AMT").length() >0)
				headVo.setDouble("Vat_Invoice_Amount", Double.parseDouble(map.get("VAT_INVO_AMT").toString()));
			if(map.get("F_RECS") !=null && map.get("F_RECS").length() >0)
				headVo.setLong("F_Recs", Long.parseLong(map.get("F_RECS").toString()));
			if(map.get("W_RECS") !=null && map.get("W_RECS").length() >0)
				headVo.setLong("W_Recs", Long.parseLong(map.get("W_RECS").toString()));
			if(map.get("GOOD_RECS")!=null && map.get("GOOD_RECS").length() >0)
				headVo.setLong("Goodwill_Recs", Long.parseLong(map.get("GOOD_RECS").toString()));
			if(map.get("LOCAL_RECS")!=null && map.get("LOCAL_RECS").length() >0)
				headVo.setLong("Localgoodwill_Recs", Long.parseLong(map.get("LOCAL_RECS").toString()));
			if(map.get("RE_RECS") !=null && map.get("RE_RECS").length() >0)
				headVo.setLong("Recall_Recs", Long.parseLong(map.get("RE_RECS").toString()));
			if(map.get("MOPAR_RECS")!=null && map.get("MOPAR_RECS").length() >0)
				headVo.setLong("Mopar_Recs", Long.parseLong(map.get("MOPAR_RECS").toString()));
			if(map.get("PDI_RECS")!=null && map.get("PDI_RECS").length() >0)
				headVo.setLong("Pdi_Recs", Long.parseLong(map.get("PDI_RECS").toString()));
			if(map.get("TCR_RECS") !=null && map.get("TCR_RECS").length() >0)
				headVo.setLong("Tcr_Recs", Long.parseLong(map.get("TCR_RECS").toString()));
			if(map.get("CHBACK_RECS") !=null && map.get("CHBACK_RECS").length() >0)
				headVo.setLong("Chargerback_Recs", Long.parseLong(map.get("CHBACK_RECS").toString()));
			if(map.get("OTHER_RECS") !=null && map.get("OTHER_RECS").length() >0)
				headVo.setLong("Others_Recs", Long.parseLong(map.get("OTHER_RECS").toString()));
			if(map.get("TOTAL_RECS")!=null && map.get("TOTAL_RECS").length() >0)
				headVo.setLong("Total_Recs", Long.parseLong(map.get("TOTAL_RECS").toString()));
			if(map.get("DBN_REMARKS") !=null && map.get("DBN_REMARKS").length() >0)
				headVo.setLong("Vbn_Remarks", Long.parseLong(map.get("DBN_REMARKS").toString()));
			if(map.get("DINVOCE")!=null && map.get("DINVOCE").length() >0)
				headVo.setString("Dealer_Invoice", map.get("DINVOCE").toString());
			if(map.get("E_IF")!=null && map.get("E_IF").length() >0)
				headVo.setString("E_If", map.get("E_IF").toString());
			//Zprorat 
			if(map.get("ZPRORAT")!=null && map.get("ZPRORAT").length()>0)
				headVo.setDouble("Zprorat", Double.parseDouble(map.get("ZPRORAT")));
			//d_flag 
			if(map.get("D_FLAG")!=null && map.get("D_FLAG").length()>0)
				headVo.setString("D_FLAG", map.get("D_FLAG"));
			//Zpart_tot
			if(map.get("ZPART_TOT")!=null && map.get("ZPART_TOT").length()>0)
				headVo.setDouble("Cond_Parts_Amt_Calc", Double.parseDouble(map.get("ZPART_TOT")));
			//Zlab_tot
			if(map.get("ZLAB_TOT")!=null && map.get("ZLAB_TOT").length()>0)
				headVo.setDouble("CondLabor_Amt_Calc", Double.parseDouble(map.get("ZLAB_TOT")));
			//Spec_tot 
			if(map.get("SPEC_TOT")!=null && map.get("SPEC_TOT").length()>0)
				headVo.setDouble("Cond_Spl_Srv_Amt_Calc", Double.parseDouble(map.get("SPEC_TOT")));
			//Dbn_date
			if(map.get("DBN_DATE") !=null && map.get("DBN_DATE").length() >0 && !"00000000".equals(map.get("DBN_DATE")))
			    headVo.setString("Vbn_Date", map.get("DBN_DATE").toString());
			
		 }catch (Throwable e) {
			 logger.error("============CLAM Head 赋值异常=================="+e.getMessage(),e);
			 throw new Exception("============CLAM Head 赋值异常=================="+e.getMessage(),e);
			}
		
		return headVo;
	}
	
	public TtClaimsNoticeDetailPO setItemVo(Map<String,String> map)throws Exception{
		TtClaimsNoticeDetailPO itemVo = new TtClaimsNoticeDetailPO();
		try{
			if(map.get("DBN_NO")!=null && map.get("DBN_NO").length() >0)
			   itemVo.setString("VBN_NO", map.get("DBN_NO").toString());
			if(map.get("GCS_LC")!=null && map.get("GCS_LC").length() >0)
			   itemVo.setString("GCS_LC", map.get("GCS_LC").toString());
			if(map.get("BUKRS") !=null && map.get("BUKRS").length() >0)
			   itemVo.setString("COMPANY_CODE", map.get("BUKRS").toString());
			if(map.get("DEALER") !=null && map.get("DEALER").length() >0)
			   itemVo.setString("SVC_DEALER", map.get("DEALER").toString());
			if(map.get("DEALER_NAME") !=null && map.get("DEALER_NAME").length() >0)
			   itemVo.setString("Svc_Dealer_Nam", map.get("DEALER_NAME").toString());
			if(map.get("CLAIM_ID") !=null && map.get("CLAIM_ID").length() >0)
			   itemVo.setString("CLAIM_NUMBER", map.get("CLAIM_ID").toString());
			if(map.get("VIN") !=null && map.get("VIN").length() >0)
			   itemVo.setString("VIN", map.get("VIN").toString());
			if(map.get("WARR_DATE") !=null && map.get("WARR_DATE").length() >0 && !"00/00/0000".equals(map.get("WARR_DATE")))
			   itemVo.setDate("DATE_VHCL_IN_SVC", map.get("WARR_DATE").toString());
			if(map.get("REC_DATE") !=null && map.get("REC_DATE").length() >0 && !"00/00/0000".equals(map.get("REC_DATE")))
			   itemVo.setDate("Date_Vhcl_Recd", map.get("REC_DATE").toString());
			if(map.get("VHCL_FAMILY_CODE") !=null && map.get("VHCL_FAMILY_CODE").length() >0)
			   itemVo.setString("Vhcl_Family_Code", map.get("VHCL_FAMILY_CODE").toString());
			if(map.get("T_TYPE") !=null && map.get("T_TYPE").length() >0)
			   itemVo.setString("Transaction_Type", map.get("T_TYPE").toString());
			if(map.get("T_TYPE_DES") !=null && map.get("T_TYPE_DES").length() >0)
			   itemVo.setString("Transaction_Type_Desc", map.get("T_TYPE_DES").toString());
			if(map.get("LOP_FAIL_CODE") !=null && map.get("LOP_FAIL_CODE").length() >0)
			   itemVo.setString("Lop_Causal", map.get("LOP_FAIL_CODE").toString());
			if(map.get("FAIL_CODE_DES") !=null && map.get("FAIL_CODE_DES").length() >0)
			   itemVo.setString("X_Falur_Lop_Desc", map.get("FAIL_CODE_DES").toString());
			if(map.get("PART_ID") !=null && map.get("PART_ID").length() >0)
			   itemVo.setString("Part_Failed", map.get("PART_ID").toString());
			if(map.get("PART_ID_DES") !=null && map.get("PART_ID_DES").length() >0)
			   itemVo.setString("Failed_Part_Desc", map.get("PART_ID_DES").toString());
			if(map.get("ZLAB_TOT") !=null && map.get("ZLAB_TOT").length() >0)
			   itemVo.setDouble("Cond_Labor_Amt_Calc", Double.parseDouble(map.get("ZLAB_TOT").toString()));
			if(map.get("ZPART_TOT") !=null && map.get("ZPART_TOT").length() >0)
			   itemVo.setDouble("Cond_Parts_Amt_Calc", Double.parseDouble(map.get("ZPART_TOT").toString()));
			if(map.get("SPEC_TOT") !=null && map.get("SPEC_TOT").length() >0)
			   itemVo.setDouble("Cond_Spl_Srv_Amt_Calc", Double.parseDouble(map.get("SPEC_TOT").toString()));
			if(map.get("CR_MEMO_C") !=null && map.get("CR_MEMO_C").length() >0)
			   itemVo.setString("Claim_Payment_Cycle", map.get("CR_MEMO_C").toString());
			if(map.get("LOPS_COND") !=null && map.get("LOPS_COND").length() >0)
			   itemVo.setString("Lops_On_Condition_All", map.get("LOPS_COND").toString());
			if(map.get("VHCL_MODE_YEAR")!=null && map.get("VHCL_MODE_YEAR").length() >0)
			   itemVo.setString("Vhcl_Model_Year", map.get("VHCL_MODE_YEAR").toString());
			if(map.get("M_RATE")!=null && map.get("M_RATE").length() >0)
			   itemVo.setFloat("Mopar_Peg_Rate", Float.parseFloat(map.get("M_RATE").toString()));
			if(map.get("REMARK") !=null && map.get("REMARK").length() >0)
			   itemVo.setString("REMARKS", map.get("REMARK").toString());
			if(map.get("ODOM") !=null && map.get("ODOM").length() >0)
			   itemVo.setString("Odometer_Vhcl_Recd", map.get("ODOM").toString());
			if(map.get("CLAIM_SAN") !=null && map.get("CLAIM_SAN").length() >0)
			   itemVo.setString("Claim_No", map.get("CLAIM_SAN").toString());
			if(map.get("TOT_AMT") !=null && map.get("TOT_AMT").length() >0)
			   itemVo.setDouble("Cond_Total_Amt_Calc", Double.parseDouble(map.get("TOT_AMT").toString()));
			//Dbn_date
			if(map.get("DBN_DATE") !=null && map.get("DBN_DATE").length() >0 && !"00000000".equals(map.get("DBN_DATE")))
				   itemVo.setDate("Vbn_Date", map.get("DBN_DATE").toString());
			
	 }catch (Throwable e) {
			logger.error("============CLAM Item 赋值异常=================="+e.getMessage(),e);
			throw new Exception("============CLAM Item 赋值异常=================="+e.getMessage(),e);
		}
		return itemVo;
	}
	
	

	public Date getDate(String index,int i) throws Exception {
		DateFormat formatter;
		Date d = new Date();
		
		
		if (index == null || index.trim().equals("")) {
			return null;
		}
        if (i == 1) {
        	formatter = new SimpleDateFormat("dd/MM/yyyy");
		} else if (i == 2) {
			formatter = new SimpleDateFormat("yyyyMMdd");
		}else {
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		}

		d = formatter.parse(index);
		return d;
	}

}
