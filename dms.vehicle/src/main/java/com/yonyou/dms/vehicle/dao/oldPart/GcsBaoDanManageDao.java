package com.yonyou.dms.vehicle.dao.oldPart;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpGcsImpPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpGcsImpPO;
@Repository
public class GcsBaoDanManageDao extends OemBaseDAO{

	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	public PageInfoDto findGcs(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	/**
	 * 下载
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		return OemDAOUtil.findAll(sql.toString(), params);

	}

	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		pasql.append("  select togp.GCS_ID, \n");
		pasql.append("    togp.DEALER_CODE,      \n");
		pasql.append("    togp.DEALER_NAME,      \n");
		pasql.append("    togp.REPAIR_NO,                    \n");
		pasql.append("    togp.VIN,                    \n");
		pasql.append("    DATE_FORMAT(togp.PAYMENT_DATE,'%Y-%m-%d') AS PAYMENT_DATE,     \n");
		pasql.append("    togp.PART_CODE,               \n");
		pasql.append("    togp.PART_NAME,             \n");
		pasql.append("    togp.PRICE,                \n");
		pasql.append("    DATE_FORMAT(togp.CREATE_DATE,'%Y-%m-%d') AS CREATE_DATE,   \n");
		pasql.append("    togp.PART_COUNT,   \n");
		pasql.append("    togp.SUBTOTAL,\n");
		pasql.append("    togp.IS_MAPPING,\n");
		pasql.append("    togp.OEM_COMPANY_ID\n");
		pasql.append("from TT_OP_GCS_IMP_DCS togp \n");
		pasql.append(" WHERE 1=1 \n");
		pasql.append(" AND togp.OEM_COMPANY_ID='"+loginInfo.getCompanyId()+"'");
		//索赔单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
			pasql.append("   AND togp.REPAIR_NO =?");
			params.add(queryParam.get("repairNo"));
		}
		//旧件代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("oldpPartNo"))) {
			pasql.append("   AND togp.PART_CODE =?");
			params.add(queryParam.get("oldpPartNo"));
		}
		//审核日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			pasql.append("   AND DATE(togp.CREATE_DATE) >= ? \n");
			params.add(queryParam.get("beginDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			pasql.append("   AND DATE(togp.CREATE_DATE) <= ? \n");
			params.add(queryParam.get("endDate"));
		}
		//VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			pasql.append("   AND togp.VIN (?)");
			params.add(queryParam.get("vin"));
		}
		//经销商代码
    	if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
    		pasql.append("   AND togp.DEALER_CODE =?");
    		params.add(queryParam.get("dealerCode"));
        }
        //是否返还
    	if (!StringUtils.isNullOrEmpty(queryParam.get("returnState"))) {
    		pasql.append("   AND togp.IS_MAPPING =?");
    		params.add(queryParam.get("returnState"));
        }
		return pasql.toString();
		
	}
	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TmpGcsImpDTO tvypDTO) {
		TmpGcsImpPO tvypPO = new TmpGcsImpPO();
		//设置对象属性
		setTmpGcsImpPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}
	
	public void setTmpGcsImpPO(TmpGcsImpPO retailBank,TmpGcsImpDTO retailBankDTO){
		retailBank.setString("DEALER_NAME", retailBankDTO.getDealerName());
		retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
		retailBank.setString("price", retailBankDTO.getPrice());
		retailBank.setString("payment_Date", retailBankDTO.getPaymentDate());
		retailBank.setString("subtotal", retailBankDTO.getSubtotal());
		retailBank.setString("VIN", retailBankDTO.getVin());
		retailBank.setString("part_Name", retailBankDTO.getPartName());
		retailBank.setString("part_Count", retailBankDTO.getPartCount());
		retailBank.setString("repair_no", retailBankDTO.getRepairNo());
		retailBank.setString("part_Code", retailBankDTO.getPartCode());
		retailBank.setString("ROW_NO",retailBankDTO.getRowNO());
	

	}
	
	//对临时表中的数据进行校验
	public ImportResultDto<TmpGcsImpDTO> checkDataVIN(TmpGcsImpDTO list) throws Exception {
		ImportResultDto<TmpGcsImpDTO> imp = new ImportResultDto<TmpGcsImpDTO>();

		ArrayList<TmpGcsImpDTO> err = new ArrayList<TmpGcsImpDTO>();
		
		TmpGcsImpDTO trmPO = new TmpGcsImpDTO();
	//检验数据为空
			String rel = check(list);
			if (!rel.equals("")) {
				 TmpGcsImpDTO rowDto = new TmpGcsImpDTO();
				 rowDto.setRowNO(Integer.valueOf(list.getRowNO().toString()));
				 rowDto.setErrorMsg(rel);
				 err.add(rowDto);
				 imp.setErrorList(err);
			}
		//校验经销商合法化
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params3 = new ArrayList<Object>();
		List<Map> notList = checkDealer(loginInfo);
		if(notList.size()>0){
			for(int i = 0; i <notList.size();i++){
				Map<String, Object> m=notList.get(i);
				 String er="";
				 TmpGcsImpDTO rowDto = new TmpGcsImpDTO();
				 er="经销商"+m.get("DEALER_CODE").toString()+"不存在";
				 rowDto.setRowNO(Integer.valueOf(m.get("ROW_NO").toString()));
				 rowDto.setErrorMsg(er);
				 err.add(rowDto);
				 imp.setErrorList(err);
				 logger.debug("经销商不存在");
			}
		}
		//校验VIN合法化
		List<Map> liVin=checkVin(loginInfo);
		if(liVin.size()>0){
			for(int i = 0; i <liVin.size();i++){
				Map<String, Object> m=liVin.get(i);
				 String er="";
				 TmpGcsImpDTO rowDto = new TmpGcsImpDTO();
				 er="车架号："+m.get("VIN").toString()+"不存在";
				 rowDto.setRowNO(Integer.valueOf(m.get("ROW_NO").toString()));
				 rowDto.setErrorMsg(er);
				 err.add(rowDto);
				 imp.setErrorList(err);
			}
		}
		//校验临时表中重复数据
		List<Map> liRecycle=checkRecycle(loginInfo);
		if(liRecycle.size()>0){
			for(Map<String, Object> m:liRecycle){
				 TmpGcsImpDTO rowDto = new TmpGcsImpDTO();
				 rowDto.setRowNO(0);
				 rowDto.setErrorMsg(m.get("SAME_DATA").toString()+"行，存在重复数据");
				 err.add(rowDto);
				 imp.setErrorList(err);
			}
		}
			return imp;
	
		}
	/**
	 * 查验零时表中经销商代码合法化
	 * @return
	 */
	public List<Map> checkDealer(LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();	
		StringBuffer sql=new StringBuffer();
		sql.append(" select trdb.ROW_NO,trdb.DEALER_CODE\n");
		sql.append(" from TMP_GCS_IMP_DCS trdb \n");
		sql.append(" where not exists ( select 1 from TM_DEALER td where  td.DEALER_CODE = trdb.DEALER_CODE ) \n");
		 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
			return resultList;
	}
	/**
	 * 校验vin合法化
	 * @return
	 */
	public List<Map> checkVin(LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();	
		StringBuffer sql=new StringBuffer();
		sql.append(" select trdb.ROW_NO,trdb.VIN from TMP_GCS_IMP_DCS trdb \n");
		sql.append(" where not exists (select 1 from TM_VEHICLE_DEC tv where tv.VIN = trdb.VIN)\n");
		 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}
	/**
	 * 校验重复数据
	 * @return
	 */
	public  List<Map> checkRecycle(LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();	
		StringBuffer sql=new StringBuffer();
		sql.append("  SELECT GROUP_CONCAT(T.ROW_NO SEPARATOR ',') AS SAME_DATA, T.DEALER_CODE,T.VIN,T.DEALER_NAME,T.PART_CODE,T.PART_NAME   , COUNT(1) flag  \n"); 
		sql.append("  from TMP_GCS_IMP_DCS T \n");
		sql.append("  group by T.DEALER_CODE,T.VIN");
		sql.append("  having count(*) > 1 \n");
		 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		 return resultList;
	}
	//查询出临时表中的数据
	public  List<Map> selectTemp() {
		 StringBuffer sql=new StringBuffer();
//		 LazyList<TmpGcsImpPO> resultList=TmpGcsImpPO.findAll();
		 List<Object> queryParam = new ArrayList<Object>();	
		 sql.append(" select * from TMP_GCS_IMP_DCS WHERE 1=1 \n");
		 List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		 return resultList;
	}
	// 合法判断
		private String check(TmpGcsImpDTO po) {
			StringBuffer result = new StringBuffer("");
			if (CommonUtils.checkNull(po.getDealerCode()).equals("")) {
				result.append("经销商代码不能为空。");
			}else{
				if(po.getDealerCode().indexOf("A")==-1&&po.getDealerCode().indexOf("a")==-1){
				  result.append("经销商不为售后经销商。");
			    }
			}
			if (CommonUtils.checkNull(po.getDealerName()).equals("")) {
				result.append("经销商名称不能为空。");
			}
			if (CommonUtils.checkNull(po.getRepairNo()).equals("")) {
				result.append("保修单号不能为空。");
			}
			if (CommonUtils.checkNull(po.getVin()).equals("")) {
				result.append("vin不能为空。");
			}
			if (CommonUtils.checkNull(po.getPartCode()).equals("")) {
				result.append("配件代码不能为空。");
			}
			if (CommonUtils.checkNull(po.getPartName()).equals("")) {
				result.append("配件名称不能为空。");
			}
			if(CommonUtils.checkNull(po.getPaymentDate()).equals("")){
				result.append("审核不能为空。");
			}else{
				if(!isDate(po.getPaymentDate())){
				    if(!CommonUtils.checkNull(po.getPaymentDate()).equals("")){
					     result.append("审核日期："+po.getPaymentDate()+" 格式不合法。");
				    }
				}
			}
			if (CommonUtils.checkNull(po.getPrice()).equals("")) {
				result.append("单价不能为空。");
			}

			if (CommonUtils.checkNull(po.getPartCount()).equals("")) {
				result.append("数量不能为空。");
			}

			if (CommonUtils.checkNull(po.getSubtotal()).equals("")) {
				result.append("小计不能为空。");
			}
			return result.toString();
		}
		public static boolean isDate(String value){
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat df3 = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
			SimpleDateFormat df4 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			boolean flag = false;
			Date d = null;
			try {
				d = df1.parse(value);
				flag = true;
			} catch (ParseException e) {
			}
			try {
				if(!flag){
					d = df2.parse(value);
					flag = true;
				}
			} catch (ParseException e) {
			}
			try {
				if(!flag){
					d = df3.parse(value);
					flag = true;
				}
			} catch (ParseException e) {
			}
			try {
				if(!flag){
					d = df4.parse(value);
					flag = true;
				}
			} catch (ParseException e) {
			}
			return flag;
		}
		/**
		 * 匹配旧件
		 * @param dealerCode
		 * @param repairNo
		 * @param partCode
		 * @return
		 */
		public List<Map> mappingOldPart(String dealerCode,
				String repairNo, String partCode) {
			StringBuffer sql = new StringBuffer();
			sql.append("select * from TT_OP_OLDPART_DCS toop \n");
			sql.append("where 1=1 \n");
			sql.append("and  TOOP.OLDPART_TYPE in('" + OemDictCodeConstants.OP_TYPE_NO_ORDER +"','"+OemDictCodeConstants.OP_TYPE_DESTROY +"')\n");//为未返还件
			//经销商代码
	        if(CommonUtils.checkNull(dealerCode) != null){
	        	sql.append(" and  TOOP.DEALER_CODE  = '" + dealerCode +"'\n");
	        }
	        //索赔单号
	        if(CommonUtils.checkNull(repairNo) != null){
	        	sql.append(" and  TOOP.CLAIM_NUMBER like '" + repairNo +"%'\n");
	        }
	        //配件代码
	        if(CommonUtils.checkNull(partCode) != null){
	        	sql.append(" and  TOOP.OLDPART_NO  = '" + partCode +"'\n");
	        }
	        
			return OemDAOUtil.findAll(sql.toString(), null);
		}
		/**
		 * 插入到正式表
		 * @param po
		 */
		public void setImpPO(TtOpGcsImpDTO po) {
			TtOpGcsImpPO tvypPO=new TtOpGcsImpPO();
			setTmRetailDiscountBankImportPO(tvypPO, po);
			tvypPO.saveIt();
		}

		public void setTmRetailDiscountBankImportPO(TtOpGcsImpPO retailBank,TtOpGcsImpDTO retailBankDTO){
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			retailBank.setString("DEALER_NAME", retailBankDTO.getDealerName());
			retailBank.setString("DEALER_CODE", retailBankDTO.getDealerCode());
			retailBank.setDouble("price", retailBankDTO.getPrice());
			retailBank.setDate("payment_Date", retailBankDTO.getPaymentDate());
			retailBank.setDouble("subtotal", retailBankDTO.getSubtotal());
			retailBank.setString("VIN", retailBankDTO.getVin());
			retailBank.setString("part_Name", retailBankDTO.getPartName());
			retailBank.setString("part_Count", retailBankDTO.getPartCount());
			retailBank.setString("repair_no", retailBankDTO.getRepairNo());
			retailBank.setString("part_Code", retailBankDTO.getPartCode());
			retailBank.setLong("create_By",retailBankDTO.getCreateBy());
			retailBank.setDate("create_Date", retailBankDTO.getCreateDate());
			retailBank.setInteger("is_Del", 0);
			retailBank.setInteger("ver", 0);
			retailBank.setInteger("is_Arc", 0);
			retailBank.setInteger("is_Mapping", retailBankDTO.getIsMapping());
			retailBank.setLong("oem_Company_Id",loginInfo.getCompanyId());
		}
}
