package com.yonyou.dms.vehicle.dao.insurancemanage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyExcelTempDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceCompanyMainDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyExcelTempDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyMainDcsPO;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
@Repository
public class InsuranceCompanyManageDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 *查询
	 */
	public PageInfoDto InsuranceCompanyManageQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> InsuranceCompanyManageDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("         SELECT  \n");
		sql.append("		  date_format(TICM.CREATE_DATE,'%Y-%c-%d ') CDate, \n");
		sql.append("		  TICM.INSURANCE_COMPANY_CODE, # --保险公司code  \n");
		sql.append("		  TICM.INSURANCE_COMPANY_NAME, # --保险公司name  \n");
		sql.append("		  TICM.INS_COMPANY_SHORT_NAME, # --保险公司简称  \n");
		sql.append("		  case when  TICM.IS_CO_INSURANCE_COMPANY = 1 then '是' else '否' end  IS_CO_INSURANCE_COMPANY,#  --合作保险公司 \n");
		sql.append("		  case when  TICM.IS_DOMN = 1 then '是' else '否' end  IS_DOMN   #--是否下发 \n");
		sql.append("		FROM TT_INSURANCE_COMPANY_MAIN_dcs TICM \n");
		sql.append("		 LEFT JOIN  TT_INSURANCE_COMPANY_dcs TIC ON  TICM.INSURANCE_COMPANY_CODE = TIC.INSURANCE_COMPANY_CODE \n");
		sql.append("		WHERE 1=1 \n");
		//保险公司代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyCode"))){
			sql.append("       AND UPPER(TICM.INSURANCE_COMPANY_CODE) LIKE UPPER('%"+queryParam.get("insuranceCompanyCode")+"%')  \n");
		}
		//保险公司名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceCompanyName"))){
			sql.append("      AND TICM.INSURANCE_COMPANY_NAME LIKE '%"+queryParam.get("insuranceCompanyName")+"%'  \n");
		}
		//是否已下发
		if(!StringUtils.isNullOrEmpty(queryParam.get("isInputDms"))){
			if(queryParam.get("isInputDms").toString().equals("10041001")){
				sql.append("        AND TICM.IS_DOMN = '1' \n");
			}
			if(queryParam.get("isInputDms").toString().equals("10041002")){
				sql.append("       AND   ((TICM.IS_DOMN = 0) or (TICM.IS_DOMN is NULL)) \n");
			}
		}
		//上传日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))&&!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("				AND TICM.CREATE_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d') \n");
			sql.append("			    AND TICM.CREATE_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//合作保险公司
		if(!StringUtils.isNullOrEmpty(queryParam.get("isCoCompany"))){
			if(queryParam.get("isCoCompany").toString().equals("10041001")){
				sql.append("        AND TICM.IS_CO_INSURANCE_COMPANY = '1' \n");
			}
			if(queryParam.get("isCoCompany").toString().equals("10041002")){
				sql.append("        AND   ((TICM.IS_CO_INSURANCE_COMPANY = 0) or (TICM.IS_CO_INSURANCE_COMPANY is NULL)) \n");
			}
		}
		return sql.toString();
	}

	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void TtInsuranceCompanyExcelTempImp(TtInsuranceCompanyExcelTempDcsDTO ticetDTO) {
		TtInsuranceCompanyExcelTempDcsPO ticetPO = new TtInsuranceCompanyExcelTempDcsPO();
		//设置对象属性
		setPO(ticetPO, ticetDTO);
		ticetPO.saveIt();
	}
	public void setPO(TtInsuranceCompanyExcelTempDcsPO ticetPO,TtInsuranceCompanyExcelTempDcsDTO ticetDTO){
		ticetPO.setString("INSURANCE_COMPANY_CODE", ticetDTO.getInsuranceCompanyCode());
		ticetPO.setString("INSURANCE_COMPANY_NAME", ticetDTO.getInsuranceCompanyName());
		ticetPO.setString("INS_COMPANY_SHORT_NAME", ticetDTO.getInsCompanyShortName());
		ticetPO.setString("OEM_TAG", "12781001");
		ticetPO.setString("IS_CO_INSURANCE_COMPANY", ticetDTO.getIsCoInsuranceCompany());
		ticetPO.setString("ERROR_ROW", ticetDTO.getRowNO());
	}
	
	//对临时表中的数据进行校验
	public ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> checkData(TtInsuranceCompanyExcelTempDcsDTO list) throws Exception {
		ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO> imp = new ImportResultDto<TtInsuranceCompanyExcelTempDcsDTO>();

		ArrayList<TtInsuranceCompanyExcelTempDcsDTO> err = new ArrayList<TtInsuranceCompanyExcelTempDcsDTO>();
		
		TtInsuranceCompanyExcelTempDcsDTO trmPO = new TtInsuranceCompanyExcelTempDcsDTO();
	//检验数据为空
			String rel = check(list);
			if (!rel.equals("")) {
				TtInsuranceCompanyExcelTempDcsDTO rowDto = new TtInsuranceCompanyExcelTempDcsDTO();
				 rowDto.setRowNO(Integer.valueOf(list.getRowNO().toString()));
				 rowDto.setErrorMsg(rel);
				 err.add(rowDto);
				 imp.setErrorList(err);
			}
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//校验临时表中重复数据
		List<Map> liRecycle=checkRecycle(loginInfo);
		if(liRecycle.size()>0){
			for(Map<String, Object> m:liRecycle){
				 TtInsuranceCompanyExcelTempDcsDTO rowDto = new TtInsuranceCompanyExcelTempDcsDTO();
				 rowDto.setErrorRow(0);
				 rowDto.setErrorMsg(m.get("SAME_DATA").toString()+"行，存在重复数据");
				 err.add(rowDto);
				 imp.setErrorList(err);
			}
		}
			return imp;
	
		}
	/**
	 * 校验重复数据
	 * @return
	 */
	public  List<Map> checkRecycle(LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();	
		StringBuffer sql = new StringBuffer("\n");
		sql.append("       SELECT GROUP_CONCAT(T.ERROR_ROW SEPARATOR ',') AS SAME_DATA, \n");
		sql.append("			T.INSURANCE_COMPANY_CODE, \n");
		sql.append("			T.INSURANCE_COMPANY_NAME, \n");
		sql.append("			T.INS_COMPANY_SHORT_NAME, \n");
		sql.append("			T.IS_CO_INSURANCE_COMPANY, \n");
		sql.append("			COUNT(1) flag   \n");
		sql.append("		  from tt_insurance_company_excel_temp_dcs T \n");
		sql.append("		  group by T.INSURANCE_COMPANY_CODE \n");
		sql.append("		  having count(*) > 1 ; \n");
	    List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	// 合法判断
			private String check(TtInsuranceCompanyExcelTempDcsDTO po) {
				StringBuffer result = new StringBuffer("");
				if (CommonUtils.checkNull(po.getInsuranceCompanyCode()).equals("")) {
					result.append("保险公司代码不能为空。");
				}else if(po.getInsuranceCompanyCode().length()>12){
					result.append("保险公司代码大于12位。");
				}
				if (CommonUtils.checkNull(po.getInsuranceCompanyName()).equals("")) {
					result.append("保险公司名称不能为空。");
				}
				if (CommonUtils.checkNull(po.getInsCompanyShortName()).equals("")) {
					result.append("保险公司简称不能为空。");
				}
				if (CommonUtils.checkNull(po.getIsCoInsuranceCompany()).equals("")) {
					result.append("合作保险公司不能为空。");
				}
				return result.toString();
			}
			
			//查询出临时表中的数据
			public  List<Map> selectTemp() {
				 StringBuffer sql=new StringBuffer();
				 List<Object> queryParam = new ArrayList<Object>();	
				 sql.append(" select * from tt_insurance_company_excel_temp_dcs WHERE 1=1 \n");
				 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				 return resultList;
			}
			/**
			 * 插入到正式表
			 * @param po
			 */
			public void setImpPO(TtInsuranceCompanyMainDcsDTO po) {
				StringBuffer sbb = new StringBuffer(
						"select INSURANCE_COMPANY_CODE   from tt_insurance_company_main_dcs where 1=1 and INSURANCE_COMPANY_CODE = ?");
				List<Object> list1 = new ArrayList<Object>();
				list1.add(po.getInsuranceCompanyCode());
				List<Map> mapp = OemDAOUtil.findAll(sbb.toString(), list1);
				if(mapp!=null&&mapp.size() > 0){//修改
					//修改需要的数据
					String CCode = po.getInsuranceCompanyCode();//保险公司code
					String Cname = po.getInsuranceCompanyName();//保险公司名称
					String Sname = po.getInsCompanyShortName();//保险公司简称
					String IsC = CommonUtils.checkNull(po.getIsCoInsuranceCompany());//是否合作保险公司
					Long updateBY = po.getCreateBy();
					//修改sql
					StringBuffer sql = new StringBuffer("\n");
					sql.append(" update tt_insurance_company_main_dcs set INSURANCE_COMPANY_NAME = '"+Cname+"',\n");
					sql.append(" INS_COMPANY_SHORT_NAME = '"+Sname+"', IS_DOMN='0', \n");
					sql.append(" UPDATE_BY = '"+updateBY+"',UPDATE_DATE = NOW(),CREATE_DATE = NOW() \n");
					if(!StringUtils.isNullOrEmpty(IsC)){
						sql.append(" ,IS_CO_INSURANCE_COMPANY = '"+IsC+"' \n");
					}
					sql.append(" where INSURANCE_COMPANY_CODE = '"+CCode+"' \n");
					OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
					//下发表
					StringBuffer sql2 = new StringBuffer("\n");
					sql2.append(" update tt_insurance_company_dcs set INSURANCE_COMPANY_NAME = '"+Cname+"',\n");
					sql2.append(" INS_COMPANY_SHORT_NAME = '"+Sname+"',  \n");
					sql2.append(" IS_SEND = '"+OemDictCodeConstants.IS_INPUT_DMS_03+"',	IS_SEND_DATE = null,  \n");
					sql2.append(" UPDATE_BY = '"+updateBY+"',UPDATE_DATE = NOW(),CREATE_DATE = NOW() \n");
					if(!StringUtils.isNullOrEmpty(IsC)){
						sql2.append(" ,IS_CO_INSURANCE_COMPANY = '"+IsC+"' \n");
					}
					sql2.append(" where INSURANCE_COMPANY_CODE = '"+CCode+"' \n");
					OemDAOUtil.execBatchPreparement(sql2.toString(), new ArrayList<>());
				}else{
						//新增
						TtInsuranceCompanyMainDcsPO tvypPO=new TtInsuranceCompanyMainDcsPO();
						setTtInsuranceCompanyMainDcsPO(tvypPO, po);
						tvypPO.saveIt();
						//新增（下发表）
						TtInsuranceCompanyDcsPO tpo = new TtInsuranceCompanyDcsPO();
						setTtInsuranceCompanyDcsPO(tpo, po);
						tpo.saveIt();
				}
		}
			//新增（主表）
			public void setTtInsuranceCompanyMainDcsPO(TtInsuranceCompanyMainDcsPO retailBank,TtInsuranceCompanyMainDcsDTO retailBankDTO){
				retailBank.setString("INSURANCE_COMPANY_NAME", retailBankDTO.getInsCompanyShortName());
				retailBank.setString("INSURANCE_COMPANY_CODE", retailBankDTO.getInsuranceCompanyCode());
				retailBank.setString("INS_COMPANY_SHORT_NAME", retailBankDTO.getInsCompanyShortName());
				retailBank.setString("IS_CO_INSURANCE_COMPANY", retailBankDTO.getIsCoInsuranceCompany());
				retailBank.setString("OEM_TAG", retailBankDTO.getOemTag());
				retailBank.setString("IS_DOMN","0");
				retailBank.setLong("CREATE_BY",retailBankDTO.getCreateBy());
				retailBank.setDate("CREATE_DATE", retailBankDTO.getCreateDate());
			}
			//新增（下发表）
			public void setTtInsuranceCompanyDcsPO(TtInsuranceCompanyDcsPO retailBank,TtInsuranceCompanyMainDcsDTO retailBankDTO){
				retailBank.setString("INSURANCE_COMPANY_NAME", retailBankDTO.getInsCompanyShortName());
				retailBank.setString("INSURANCE_COMPANY_CODE", retailBankDTO.getInsuranceCompanyCode());
				retailBank.setString("INS_COMPANY_SHORT_NAME", retailBankDTO.getInsCompanyShortName());
				retailBank.setString("IS_CO_INSURANCE_COMPANY", retailBankDTO.getIsCoInsuranceCompany());
				retailBank.setString("OEM_TAG", retailBankDTO.getOemTag());
				retailBank.setString("IS_SEND",OemDictCodeConstants.IS_INPUT_DMS_03);
				retailBank.setLong("CREATE_BY",retailBankDTO.getCreateBy());
				retailBank.setDate("CREATE_DATE", retailBankDTO.getCreateDate());
			}
}
