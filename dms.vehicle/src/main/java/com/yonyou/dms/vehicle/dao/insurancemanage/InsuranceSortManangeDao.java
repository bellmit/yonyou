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
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortDcsDTO;
import com.yonyou.dms.vehicle.domains.DTO.insurancemanage.TtInsuranceSortExcelErrorDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyExcelTempDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceCompanyMainDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortDownDcsPO;
import com.yonyou.dms.vehicle.domains.PO.insurancemanage.TtInsuranceSortExcelErrorDcsPO;

/**
 * 保险公司维护
 * @author zhiahongmiao 
 *
 */
@Repository
public class InsuranceSortManangeDao extends OemBaseDAO{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	/**
	 *查询
	 */
	public PageInfoDto InsuranceSortManangeQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * 下载
	 */
	public List<Map> InsuranceSortManangeDownload(Map<String, String> queryParam) {
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
		sql.append("			date_format(TIS.CREATE_DATE ,'%Y-%c-%d ') CREATE_DATE, \n");
		sql.append("		  	TIS.INSURANCE_SORT_CODE,  #险种code \n");
		sql.append("		  	TIS.INSURANCE_SORT_NAME,  #险种名称 \n");
		sql.append("            case when TIS.IS_COM_INSURANCE = '12781001' then '是' else ( \n");
		sql.append("					case when TIS.IS_COM_INSURANCE = '12781002' then '否' else '未知' end \n");
		sql.append("			) end IS_COM_INSURANCE,   #是否交强险 \n");
		sql.append("			case when TIS.IS_DOWN  = 1 then '是' else ( \n");
		sql.append("				case when TIS.IS_DOWN  = 1 then '否' else '未知' end \n");
		sql.append("			) end IS_DOWN #--是否下发 \n");

		sql.append("		FROM \n");
		sql.append("		  tt_insurance_sort_dcs TIS \n");
		sql.append("		 LEFT JOIN \n");
		sql.append("		  tt_insurance_sort_down_dcs TISD \n");
		sql.append("		 ON \n");
		sql.append("		  TIS.SORT_ID= TISD.SORT_ID \n");
		sql.append("		WHERE TIS.STATUS='12781001' \n");
		//保种代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("insuranceSortCode"))){
			sql.append("       AND TIS.INSURANCE_SORT_CODE LIKE '%"+queryParam.get("insuranceSortCode")+"%'  \n");
		}
		//上传日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))&&!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("				AND TIS.CREATE_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d') \n");
			sql.append("			    AND TIS.CREATE_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//是否强交强险
		if(!StringUtils.isNullOrEmpty(queryParam.get("isComInsurance"))){
			if(queryParam.get("isComInsurance").toString().equals("10041001")){
				sql.append("        AND TISD.IS_COM_INSURANCE = '12781001' \n");
			}
			if(queryParam.get("isComInsurance").toString().equals("10041002")){
				sql.append("        AND   ((TISD.IS_COM_INSURANCE = '12781002') or (TISD.IS_COM_INSURANCE is NULL)) \n");
			}
		}
//		 	System.out.println("-----查询sql------"+sql.toString());
		return sql.toString();
	}

	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void InsuranceSortManangeImp(TtInsuranceSortExcelErrorDcsDTO ticetDTO) {
		TtInsuranceSortExcelErrorDcsPO ticetPO = new TtInsuranceSortExcelErrorDcsPO();
		//设置对象属性
		setPO(ticetPO, ticetDTO);
		ticetPO.saveIt();
	}
	public void setPO(TtInsuranceSortExcelErrorDcsPO ticetPO,TtInsuranceSortExcelErrorDcsDTO ticetDTO){
		ticetPO.setString("INSURANCE_SORT_CODE", ticetDTO.getInsuranceSortCode());
		ticetPO.setString("INSURANCE_SORT_NAME", ticetDTO.getInsuranceSortName());
		ticetPO.setString("IS_COM_INSURANCE", ticetDTO.getIsComInsurance());
		ticetPO.setString("OEM_TAG", "12781001");
		ticetPO.setString("ERROR_ROW", ticetDTO.getRowNO());
	}
	
	//对临时表中的数据进行校验
	public ImportResultDto<TtInsuranceSortExcelErrorDcsDTO> checkData(TtInsuranceSortExcelErrorDcsDTO list) throws Exception {
		ImportResultDto<TtInsuranceSortExcelErrorDcsDTO> imp = new ImportResultDto<TtInsuranceSortExcelErrorDcsDTO>();

		ArrayList<TtInsuranceSortExcelErrorDcsDTO> err = new ArrayList<TtInsuranceSortExcelErrorDcsDTO>();
		
	//检验数据为空
			String rel = check(list);
			if (!rel.equals("")) {
				TtInsuranceSortExcelErrorDcsDTO rowDto = new TtInsuranceSortExcelErrorDcsDTO();
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
				TtInsuranceSortExcelErrorDcsDTO rowDto = new TtInsuranceSortExcelErrorDcsDTO();
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
		sql.append("             	 SELECT GROUP_CONCAT(T.ERROR_ROW SEPARATOR ',') AS SAME_DATA, \n");
		sql.append("					T.INSURANCE_SORT_CODE, \n");
		sql.append("					T.INSURANCE_SORT_NAME, \n");
		sql.append("					T.IS_COM_INSURANCE, \n");
		sql.append("					COUNT(1) flag   \n");
		sql.append("				  from tt_insurance_sort_excel_error_dcs T \n");
		sql.append("				  group by T.INSURANCE_SORT_CODE \n");
		sql.append("				  having count(*) > 1 ; \n");
	    List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	// 合法判断
			private String check(TtInsuranceSortExcelErrorDcsDTO po) {
				StringBuffer result = new StringBuffer("");
				if (CommonUtils.checkNull(po.getInsuranceSortCode()).equals("")) {
					result.append("险种代码不能为空。");
				}else if(po.getInsuranceSortCode().length()>12){
					result.append("险种代码大于4位。");
				}
				if (CommonUtils.checkNull(po.getInsuranceSortName()).equals("")) {
					result.append("险种名称不能为空。");
				}
				if (CommonUtils.checkNull(po.getIsComInsurance()).equals("")) {
					result.append("是否交强险不能为空。");
				}else if(!po.getIsComInsurance().toString().equals("1")&&!po.getIsComInsurance().toString().equals("0") ){
					result.append("否交强险请输入是或否。");
				}
				return result.toString();
			}
			
			//查询出临时表中的数据
			public  List<Map> selectTemp() {
				 StringBuffer sql=new StringBuffer();
				 List<Object> queryParam = new ArrayList<Object>();	
				 sql.append(" select * from tt_insurance_sort_excel_error_dcs WHERE 1=1 \n");
				 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
				 return resultList;
			}
			/**
			 * 插入到正式表
			 * @param po
			 */
			public void setImpPO(TtInsuranceSortDcsDTO po) {
				StringBuffer sbb = new StringBuffer(
						"select INSURANCE_COMPANY_CODE   from tt_insurance_company_main_dcs where 1=1 and INSURANCE_COMPANY_CODE = ?");
				List<Object> list1 = new ArrayList<Object>();
				list1.add(po.getInsuranceSortCode());
				List<Map> mapp = OemDAOUtil.findAll(sbb.toString(), list1);
				if(mapp!=null&&mapp.size() > 0){//修改
					//修改需要的数据
					String sCode = po.getInsuranceSortCode();//险种code
					String sname = po.getInsuranceSortName();//险种名称
					String IsC = CommonUtils.checkNull(po.getIsComInsurance());//是否交强险
					Long updateBY = po.getCreateBy();
					//修改sql
					StringBuffer sql = new StringBuffer("\n");
					sql.append(" update tt_insurance_sort_dcs set INSURANCE_SORT_NAME = '"+sname+"',\n");
					sql.append(" IS_DOWN = '0',UPDATE_BY = '"+updateBY+"', \n");
					sql.append(" UPDATE_DATE = NOW()  \n");
					if(!StringUtils.isNullOrEmpty(IsC)){
						sql.append(" ,IS_COM_INSURANCE = '"+IsC+"' \n");
					}
					sql.append(" where INSURANCE_SORT_CODE = '"+sCode+"' \n");
					OemDAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
					//下发表
					StringBuffer sql2 = new StringBuffer("\n");
					sql2.append(" update tt_insurance_sort_down_dcs set INSURANCE_SORT_NAME =  '"+sname+"',\n");
					sql2.append(" IS_INPUT_DMS = '"+OemDictCodeConstants.IS_INPUT_DMS_03+"',INPUT_DATE= null,CREATE_DATE = NOW(),  \n");
					sql2.append(" IS_SEND = '"+OemDictCodeConstants.IS_INPUT_DMS_03+"',	IS_SEND_DATE = null,  \n");
					sql2.append(" UPDATE_BY = '"+updateBY+"',UPDATE_DATE = NOW(),CREATE_DATE = NOW() \n");
					if(!StringUtils.isNullOrEmpty(IsC)){
						sql2.append(" ,IS_COM_INSURANCE = '"+IsC+"' \n");
					}
					sql2.append(" where INSURANCE_SORT_CODE = '"+sCode+"' \n");
					OemDAOUtil.execBatchPreparement(sql2.toString(), new ArrayList<>());
				}else{
						//新增
					TtInsuranceSortDcsPO tvypPO=new TtInsuranceSortDcsPO();
						setTtInsuranceCompanyMainDcsPO(tvypPO, po);
						tvypPO.saveIt();
						//新增（下发表）
						TtInsuranceSortDownDcsPO tpo = new TtInsuranceSortDownDcsPO();
						setTtInsuranceCompanyDcsPO(tpo, po);
						tpo.saveIt();
				}
		}
			//新增（主表）
			public void setTtInsuranceCompanyMainDcsPO(TtInsuranceSortDcsPO retailBank,TtInsuranceSortDcsDTO retailBankDTO){
				retailBank.setString("INSURANCE_SORT_CODE", retailBankDTO.getInsuranceSortCode());
				retailBank.setString("INSURANCE_SORT_NAME", retailBankDTO.getInsuranceSortName());
				retailBank.setString("IS_COM_INSURANCE", retailBankDTO.getIsComInsurance());
				retailBank.setString("OEM_TAG", retailBankDTO.getOemTag());
				retailBank.setString("IS_DOMN","0");
				retailBank.setString("STATUS","12781001");
				retailBank.setLong("CREATE_BY",retailBankDTO.getCreateBy());
				retailBank.setDate("CREATE_DATE", retailBankDTO.getCreateDate());
			}
			//新增（下发表）
			public void setTtInsuranceCompanyDcsPO(TtInsuranceSortDownDcsPO retailBank,TtInsuranceSortDcsDTO retailBankDTO){
				retailBank.setString("INSURANCE_COMPANY_NAME", retailBankDTO.getInsuranceSortCode());
				retailBank.setString("INSURANCE_COMPANY_CODE", retailBankDTO.getInsuranceSortName());
				retailBank.setString("INS_COMPANY_SHORT_NAME", retailBankDTO.getIsComInsurance());
				retailBank.setString("OEM_TAG", retailBankDTO.getOemTag());
				retailBank.setString("IS_INPUT_DMS",OemDictCodeConstants.IS_INPUT_DMS_03);
				retailBank.setLong("CREATE_BY",retailBankDTO.getCreateBy());
				retailBank.setDate("CREATE_DATE", retailBankDTO.getCreateDate());
			}
}
