package com.yonyou.dms.retail.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.FileStoreService;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.dao.basedata.RetailBankDao;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetailDiscountBankImportTempDTO;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetalDiscountImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportPO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetailDiscountBankImportTempPO;

@Service
@SuppressWarnings("rawtypes")
public class RetailBankServiceImpl implements RetailBankService {
	@Autowired
	RetailBankDao dao;

	@Autowired
	FileStoreService fileStoreService;

	@Override
	public PageInfoDto findRetailDiscountBank(Map<String, String> queryParam) {
		PageInfoDto pgInfo = dao.findRetailDiscountBank(queryParam);
		return pgInfo;
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> param) throws ServiceBizException {
		List<Map> zz = dao.queryEmpInfoforExport(param);
		return zz;
	}

	@Override
	public TmRetailDiscountBankImportPO findById(Long id) throws ServiceBizException {
		TmRetailDiscountBankImportPO dr = TmRetailDiscountBankImportPO.findById(id);
		return dr;

	}

	@Override
	public void insertTmpVsProImpAudit(TmRetailDiscountBankImportTempDTO retailBankDTO) throws ServiceBizException {

		dao.insertTmpVsYearlyPlan(retailBankDTO);

	}

	@Override
	public List<Map> oemSelectTmpYearPlan(Map<String, String> queryParam) throws ServiceBizException {
		return dao.oemSelectTmpYearPlan(queryParam);
	}

	@Override
	public List<Map> selectTmRetailDiscountBankImportTempList(LoginInfoDto loginInfo) {
		return dao.selectTmRetailDiscountBankImportTempList(loginInfo);
	}

	/**
	 * 
	 * @Title: checkData @Description: 零售导入临时表 @param @param
	 * rowDto @param @return 设定文件 @return List<ImportResultDto> 返回类型 @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ImportResultDto<TmRetailDiscountBankImportTempDTO> checkData() throws ServiceBizException {
		ImportResultDto<TmRetailDiscountBankImportTempDTO> importResult = new ImportResultDto<TmRetailDiscountBankImportTempDTO>();
		ArrayList<TmRetailDiscountBankImportTempDTO> errorList = new ArrayList<TmRetailDiscountBankImportTempDTO>();
		//判空数据
		List<Map> nullDataList = dao.selectNullData();
		if(nullDataList.size()>0){
			for(Map<String, Object> m:nullDataList){
				TmRetailDiscountBankImportTempDTO err = new TmRetailDiscountBankImportTempDTO();
				err.setRowNO(Integer.parseInt(m.get("ROW_NO").toString()));
				err.setErrorMsg(m.get("ERR_DESC").toString());
				errorList.add(err);
			}
		}
		//校验经销商合法化
		List<Map> liDealer = dao.checkDealer();
		if(liDealer.size()>0){
			for(Map<String, Object> m:liDealer){
				 String er="";
				 TmRetailDiscountBankImportTempDTO err = new TmRetailDiscountBankImportTempDTO();
				 er="经销商"+m.get("DEALER_CODE").toString()+"不存在";
				 err.setRowNO(Integer.valueOf(m.get("ROW_NO").toString()));
				 err.setErrorMsg(er);
				 errorList.add(err);
			}
		}
		//校验VIN合法化
		List<Map> liVin = dao.checkVin();
		if(liVin.size()>0){
			for(Map<String, Object> m:liVin){
				 String er="";
				 TmRetailDiscountBankImportTempDTO err=new TmRetailDiscountBankImportTempDTO();
				 er="车架号："+m.get("VIN").toString()+"不存在";
				 err.setRowNO(Integer.valueOf(m.get("ROW_NO").toString()));
				 err.setErrorMsg(er);
				 errorList.add(err);
			}
		}
		//校验临时表中重复数据
		List<Map> liRecycle=dao.checkRecycle();
		if(liRecycle.size()>0){
			for(Map<String, Object> m:liRecycle){
				 TmRetailDiscountBankImportTempDTO err=new TmRetailDiscountBankImportTempDTO();
				 err.setRowNO(0);
				 err.setErrorMsg(m.get("SAME_DATA").toString()+"行，存在重复数据");
				 errorList.add(err);
			}
		}
		//数据正确性校验
		List<Map> liRight = dao.checkRight();
		if(liRight.size()>0){
			for(Map<String, Object> p:liRight){
				 TmRetailDiscountBankImportTempDTO err=new TmRetailDiscountBankImportTempDTO();
				 err.setRowNO(Integer.valueOf(p.get("ROW_NO").toString()));
				 err.setErrorMsg(p.get("ERR_DATA").toString());
				 errorList.add(err);
			}
		}
		importResult.setErrorList(errorList);
		return importResult;
	}

	@Override
	public void insertTmRetailDiscountBankImportTemp(List<TmRetailDiscountBankImportTempDTO> dataList,LoginInfoDto loginInfo) {
		for(int i=0;i<dataList.size();i++){
			TmRetailDiscountBankImportTempPO trdbitPO = new TmRetailDiscountBankImportTempPO();
			TmRetailDiscountBankImportTempDTO trdbitDTO = dataList.get(i);
			trdbitPO.setInteger("ROW_NO", i+1); //行号
			trdbitPO.setString("DEALER_SHORTNAME",trdbitDTO.getDealerShortname());//经销商名称
			trdbitPO.setString("DEALER_CODE", trdbitDTO.getDealerCode());//经销商代码
			trdbitPO.setString("CUSTOMER", trdbitDTO.getCustomer());//客户名称
			trdbitPO.setString("VIN", trdbitDTO.getVin());//车架号
			trdbitPO.setString("APP_STATE", trdbitDTO.getAppState());//审批状态
			trdbitPO.setString("APPLY_DATE", trdbitDTO.getApplyDate());//申请时间
			trdbitPO.setString("DEAL_DATE", trdbitDTO.getDealDate());//银行放款时间
			trdbitPO.setString("NET_PRICE", trdbitDTO.getNetPrice());//成交价
			trdbitPO.setString("FINANCING_PNAME", trdbitDTO.getFinancingPname());//零售融资产品名称
			trdbitPO.setString("FIRST_PERMENT", trdbitDTO.getFirstPerment());//首付
			trdbitPO.setString("DEAL_AMOUNT", trdbitDTO.getDealAmount());//贷款金额
			trdbitPO.setString("END_PERMENT", trdbitDTO.getEndPerment());//尾款
			String firstPermentRatio1 = trdbitDTO.getFirstPermentRatio();
			if(firstPermentRatio1.subSequence(firstPermentRatio1.length()-1, firstPermentRatio1.length()).equals("%")){
			String firstPermentRatio = String.valueOf(Double.valueOf(firstPermentRatio1.substring(0, firstPermentRatio1.length()-1))/100);
			trdbitPO.setString("FIRST_PERMENT_RATIO", firstPermentRatio);//首付比例
			}else{
				trdbitPO.setString("FIRST_PERMENT_RATIO", firstPermentRatio1);//首付比例
			}
			String endPermentRatio1 = trdbitDTO.getEndPermentRatio();
			if(endPermentRatio1.subSequence(endPermentRatio1.length()-1, endPermentRatio1.length()).equals("%")){
			String endPermentRatio = String.valueOf(Double.valueOf(endPermentRatio1.substring(0, endPermentRatio1.length()-1))/100);
			trdbitPO.setString("END_PERMENT_RATIO", endPermentRatio);//尾款比例
			}else{
				trdbitPO.setString("END_PERMENT_RATIO", endPermentRatio1);//首付比例
			}
			trdbitPO.setString("INSTALL_MENT_NUM", trdbitDTO.getInstallMentNum());//分期期数
			trdbitPO.setString("TOTAL_INTEREST", trdbitDTO.getTotalInterest());//总利息（手续费）
			String interestRate1 = trdbitDTO.getInterestRate();
			if(interestRate1.subSequence(interestRate1.length()-1, interestRate1.length()).equals("%")){
			String interestRate = String.valueOf(Double.valueOf(interestRate1.substring(0, interestRate1.length()-1))/100);
			trdbitPO.setString("INTEREST_RATE", interestRate);//原利率
			}else{
				trdbitPO.setString("INTEREST_RATE", interestRate1);//首付比例
			}
			String policyRate1 = trdbitDTO.getPolicyRate();
			if(policyRate1.subSequence(policyRate1.length()-1, policyRate1.length()).equals("%")){
			String policyRate = String.valueOf(Double.valueOf(policyRate1.substring(0, policyRate1.length()-1))/100);
			trdbitPO.setString("POLICY_RATE", policyRate);//政策费率
			}else{
				trdbitPO.setString("POLICY_RATE", policyRate1);//首付比例
			}
			String merchantFeesRate1 = trdbitDTO.getMerchantFeesRate();
			if(merchantFeesRate1.subSequence(merchantFeesRate1.length()-1, merchantFeesRate1.length()).equals("%")){
			String merchantFeesRate = String.valueOf(Double.valueOf(merchantFeesRate1.substring(0, merchantFeesRate1.length()-1))/100);
			trdbitPO.setString("MERCHANT_FEES_RATE", merchantFeesRate);//商户手续费率
			}else{
				trdbitPO.setString("MERCHANT_FEES_RATE", merchantFeesRate1);//首付比例
			}
			trdbitPO.setString("ALLOWANCED_SUM_TAX", trdbitDTO.getAllowancedSumTax());//贴息金额
			trdbitPO.setString("REMARK", trdbitDTO.getRemark());//备注
			trdbitPO.setLong("BANK", loginInfo.getUserId());
			trdbitPO.insert();
 		}
	}
	
	
	
}
