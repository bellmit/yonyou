package com.yonyou.dms.vehicle.dao.oldPart;

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
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmOldpartRuleDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpOldpartImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpUrgencyVinImpDTO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmOldpartRulePO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpOldpartImpPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpUrgencyVinImpPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpOldpartImpPO;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TtOpUrgencyVinImpPO;
@Repository
public class OldPartRuleMaintainDao extends OemBaseDAO{
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	public PageInfoDto findOldPartHouse(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer pasql = new StringBuffer();
		pasql.append(" SELECT TOR.RULE_ID, TOR.RULE_CODE, TOR.RULE_NAME,TOR.RULE_TYPE,\n");
		pasql.append(" TOR.REMARK,TOR.STATUS,TOR.CREATE_BY, DATE_FORMAT(TOR.CREATE_DATE,'%y-%m-%d') CREATE_DATE,TU.NAME \n");
		pasql.append(" FROM TM_OLDPART_RULE_DCS TOR \n");
		pasql.append("	LEFT JOIN TC_USER TU ON TOR.CREATE_BY=TU.USER_ID\n");
		pasql.append(" WHERE 1=1 \n");
	    pasql.append(" AND TOR.OEM_COMPANY_ID ="+loginInfo.getCompanyId());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ruleCode"))) {
			pasql.append("   	AND TOR.RULE_CODE  like '%"+queryParam.get("ruleCode")+"%' ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
			pasql.append("   AND TOR.STATUS =? ");
			params.add(queryParam.get("status"));
		}
		return pasql.toString();
	}
	
	/**
	 * @remark 旧件导入类型维护查询
	 * @param ruleCode
	 * @param status
	 * @param pageSize
	 * @param curPage
	 * @return
	 * @throws Exception
	 */
	public PageInfoDto findOldType(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySqlType(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getQuerySqlType(Map<String, String> queryParam, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer pasql = new StringBuffer();
		pasql.append("SELECT TOOI.PART_IMP, TOOI.PART_NO, TOOI.PART_NAME, TOOI.OLDPART_IMP_TYPE,TOOI.RULE_ID \n");
		pasql.append("	FROM TT_OP_OLDPART_IMP_DCS TOOI \n");
		pasql.append("		WHERE 1=1 \n");
		pasql.append(" 	   		AND TOOI.OEM_COMPANY_ID="+loginInfo.getCompanyId());

		if (!StringUtils.isNullOrEmpty(queryParam.get("ruleId"))) {
			pasql.append("   AND TOOI.RULE_ID =? ");
			params.add(queryParam.get("ruleId"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("tbval"))) {
			pasql.append("   AND TOOI.OLDPART_IMP_TYPE =? ");
			params.add(queryParam.get("tbval"));
		}
		pasql.append(" ORDER BY TOOI.PART_IMP\n");
		pasql.append(" with ur \n");
		return pasql.toString();
	}
	/**
	 * 修改
	 * 
	 * @param id
	 * @param user
	 * @throws ServiceBizException
	 */
	public void modifyoldParth(Long id, TmOldpartRuleDTO tcdto) throws Exception {
		TmOldpartRulePO tc = TmOldpartRulePO.findById(id);
		setTmOldpartRule(tc, tcdto);
		tc.saveIt();
	}
	/**
	 * 设置对象属性
	 * @param tc
	 * @param user
	 */
	private void setTmOldpartRule(TmOldpartRulePO tc, TmOldpartRuleDTO tcdto) throws Exception{
		StringBuffer sb = new StringBuffer(
				"select status from TM_OLDPART_RULE_DCS where status='10011001' and rule_Type=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getRuleType());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("已经存在有效状态的规则编号，请重新输入！");
		}
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		tc.setDate("update_date", new Date());
		tc.setLong("update_by", loginInfo.getUserId());
		tc.setDouble("no_Order_Price", tcdto.getNoOrderPrice());
		tc.setInteger("is_Repair_Date", tcdto.getIsRepairDate());
		tc.setString("remark", tcdto.getRemark());
		tc.setInteger("status", tcdto.getStatus());
		tc.setInteger("is_Oldpart_Date", tcdto.getIsOldpartDate());
		tc.setDate("repair_Start_Date", tcdto.getRepairStartDate());
		tc.setInteger("is_Vin_Date", tcdto.getIsVinDate());
		tc.setDate("effective_Start_Date", tcdto.getEffectiveStartDate());
		tc.setDate("oldpart_End_Date", tcdto.getOldpartEndDate());
		tc.setInteger("rule_Type", tcdto.getRuleType());
		tc.setDouble("destroy_Price", tcdto.getDestroyPrice());
		tc.setDate("oldpart_Start_Date", tcdto.getOldpartStartDate());
		tc.setString("rule_Name", tcdto.getRuleName());
		tc.setDate("repair_End_Date", tcdto.getRepairEndDate());
		tc.setDate("effective_End_Date", tcdto.getEffectiveEndDate());
		tc.setDate("vin_End_Date", tcdto.getVinEndDate());
		tc.setDate("vin_Start_Date", tcdto.getVinStartDate());
	

}
	/**
	 * 旧件规则新增
	 * 
	 * @param tcbdto
	 * @return
	 * @throws ServiceBizException
	 */
	public long addnoldPart(TmOldpartRuleDTO tcdto) throws ServiceBizException {
		StringBuffer sb = new StringBuffer(
				"select rule_id from TM_OLDPART_RULE_DCS where rule_id=?");
		List<Object> list = new ArrayList<Object>();
		list.add(tcdto.getRuleId());
		List<Map> map = OemDAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("规则编号已存在，请重新输入！");
		}
		StringBuffer sb1 = new StringBuffer(
				"select status from TM_OLDPART_RULE_DCS where status='10011001' and rule_Type=?");
		List<Object> list1 = new ArrayList<Object>();
		list.add(tcdto.getRuleType());
		List<Map> map1 = OemDAOUtil.findAll(sb.toString(), list1);
		if (map1.size() > 0) {
			throw new ServiceBizException("已经存在有效状态的规则编号，请重新输入！");
		}
			TmOldpartRulePO tc = new TmOldpartRulePO();
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
			tc.setDate("create_date", new Date());
			tc.setLong("create_by", loginInfo.getUserId());
			tc.setDouble("no_Order_Price", tcdto.getNoOrderPrice());
			tc.setInteger("is_Repair_Date", tcdto.getIsRepairDate());
			tc.setString("remark", tcdto.getRemark());
			tc.setInteger("status", tcdto.getStatus());
			tc.setInteger("is_Oldpart_Date", tcdto.getIsOldpartDate());
			tc.setDate("repair_Start_Date", tcdto.getRepairStartDate());
			tc.setInteger("is_Vin_Date", tcdto.getIsVinDate());
			tc.setDate("effective_Start_Date", tcdto.getEffectiveStartDate());
			tc.setDate("oldpart_End_Date", tcdto.getOldpartEndDate());
			tc.setInteger("rule_Type", tcdto.getRuleType());
			tc.setDouble("destroy_Price", tcdto.getDestroyPrice());
			tc.setDate("oldpart_Start_Date", tcdto.getOldpartStartDate());
			tc.setString("rule_Name", tcdto.getRuleName());
			tc.setDate("repairEndDate", tcdto.getRepairEndDate());
			tc.setDate("effectiveEndDate", tcdto.getEffectiveEndDate());
			tc.setDate("vinEndDate", tcdto.getVinEndDate());
			tc.setDate("vinStartDate", tcdto.getVinStartDate());
			tc.setInteger("ver", 0);
			tc.setInteger("is_del", 0);
			tc.setInteger("is_arc", 0);
			tc.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
			tc.saveIt();
			return (Long) tc.getLongId();
		}
	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void insertTmpVsYearlyPlan(TmpOldpartImpDTO tvypDTO) {
		TmpOldpartImpPO tvypPO = new TmpOldpartImpPO();
		//设置对象属性
		setTmpOldpartImpPO(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}
	
	public void setTmpOldpartImpPO(TmpOldpartImpPO retailBank,TmpOldpartImpDTO retailBankDTO){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setString("PART_NO", retailBankDTO.getPartNo());
		retailBank.setString("PART_NAME", retailBankDTO.getPartName());
		retailBank.setString("ROW_NO", retailBankDTO.getRowNO());
	}
	//对临时表中的数据进行校验
	public ImportResultDto<TmpOldpartImpDTO> checkData(TmpOldpartImpDTO list) throws Exception {
		ImportResultDto<TmpOldpartImpDTO> imp = new ImportResultDto<TmpOldpartImpDTO>();
		boolean isError = false;
		ArrayList<TmpOldpartImpDTO> err = new ArrayList<TmpOldpartImpDTO>();
		TmpOldpartImpDTO trmPO = new TmpOldpartImpDTO();
		// 旧件代码
		if (!StringUtils.isNullOrEmpty(list.getPartNo())) {

			List<Object> queryParam = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select PART_NO from TT_OP_OLDPART_IMP_DCS where PART_NO= ?");
			queryParam.add(list.getPartNo());
			List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
			if (resultList != null && resultList.size() > 0) {
			} else {
				TmpOldpartImpDTO rowDto = new TmpOldpartImpDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("旧件代码已存在!");
				err.add(rowDto);
				imp.setErrorList(err);
			}
		}else{
			TmpOldpartImpDTO rowDto = new TmpOldpartImpDTO();
		rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
		rowDto.setErrorMsg("旧件代码不能为空!");
		err.add(rowDto);
		imp.setErrorList(err);
	}
		// 旧件名称
		if (!StringUtils.isNullOrEmpty(list.getPartNo())) {
		}else{
			TmpOldpartImpDTO rowDto = new TmpOldpartImpDTO();
			rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
			rowDto.setErrorMsg("旧件名称不能为空!");
			err.add(rowDto);
			imp.setErrorList(err);
		}
		return imp;
	}
	public List<Map> oemSelect(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("select PART_NO,PART_NAME from TMP_OLDPART_IMP_DCS ");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		return resultList;
		
	}
	//将数据导入正式表中
	public void save(Map<String, String> queryParam) {
	String type= CommonUtils.checkNull(queryParam.get("type"));//规则类型
	String ruleId= CommonUtils.checkNull(queryParam.get("ruleId"));//规则编号
	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	String sql=("insert into TT_OP_OLDPART_IMP_DCS(PART_IMP, PART_NO, PART_NAME, RULE_ID,OLDPART_IMP_TYPE,OEM_COMPANY_ID, CREATE_DATE,  CREATE_BY)"
			+ "		select F_GETID, toi.PART_NO, toi.PART_NAME,"+ruleId+","+type+","+loginInfo.getCompanyId()+",now(),"+loginInfo.getUserId()+" from TMP_OLDPART_IMP_DCS toi");
	OemDAOUtil.execBatchPreparement(sql.toString(), null);
	}
	/**
	 * 插入数据到临时表
	 * @param tvypDTO
	 */
	public void insertVIN(TmpUrgencyVinImpDTO tvypDTO) {
		TmpUrgencyVinImpPO tvypPO = new TmpUrgencyVinImpPO();
		//设置对象属性
		setVIN(tvypPO, tvypDTO);
		tvypPO.saveIt();
	}
	
	public void setVIN(TmpUrgencyVinImpPO retailBank,TmpUrgencyVinImpDTO retailBankDTO){
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		retailBank.setString("VIN", retailBankDTO.getVin());
		retailBank.setString("ROW_NO", retailBankDTO.getRowNO());
	}
	//对临时表中的数据进行校验
	public ImportResultDto<TmpUrgencyVinImpDTO> checkDataVIN(TmpUrgencyVinImpDTO list) throws Exception {
		ImportResultDto<TmpUrgencyVinImpDTO> imp = new ImportResultDto<TmpUrgencyVinImpDTO>();
		boolean isError = false;
		ArrayList<TmpUrgencyVinImpDTO> err = new ArrayList<TmpUrgencyVinImpDTO>();
		TmpUrgencyVinImpDTO trmPO = new TmpUrgencyVinImpDTO();
		// VIN代码
		if (!StringUtils.isNullOrEmpty(list.getVin())) {
			List<Object> queryParam = new ArrayList<Object>();
			StringBuffer sql = new StringBuffer();
			sql.append("select PART_NO from TT_OP_OLDPART_IMP_DCS where VIN= ?");
			queryParam.add(list.getVin());
			List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
			if (resultList != null && resultList.size() > 0) {
				TmpUrgencyVinImpDTO rowDto = new TmpUrgencyVinImpDTO();
				rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
				rowDto.setErrorMsg("在紧急回运VIN表中已存在!");
				err.add(rowDto);
				imp.setErrorList(err);
			} else{
				if(list.getVin().toString().length()!=17){
					TmpUrgencyVinImpDTO rowDto = new TmpUrgencyVinImpDTO();
					rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
					rowDto.setErrorMsg("VIN号码长度不为17位!");
					err.add(rowDto);
					imp.setErrorList(err);
				}
			}
		}else{
		TmpUrgencyVinImpDTO rowDto = new TmpUrgencyVinImpDTO();
		rowDto.setRowNO(Integer.parseInt(list.getRowNO().toString()));
		rowDto.setErrorMsg("VIN不能为空!");
		err.add(rowDto);
		imp.setErrorList(err);
		}

		return imp;
	}


	public List<Map> oemSelectvin(Map<String, String> queryParam) {
		StringBuffer sql = new StringBuffer();
		sql.append("select VIN from TMP_URGENCY_VIN_IMP_DCS ");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		return resultList;
		
	}
	//将数据导入正式表中
	public void saveVIN(Map<String, String> queryParam) {
	String type= CommonUtils.checkNull(queryParam.get("type"));//规则类型
	String ruleId= CommonUtils.checkNull(queryParam.get("ruleId"));//规则编号
	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	String sql=("insert into TT_OP_URGENCY_VIN_IMP_DCS( VIN, RULE_ID, OEM_COMPANY_ID, CREATE_DATE, CREATE_BY) select  ti.VIN,"+ruleId+","+loginInfo.getCompanyId()+",now(),"+loginInfo.getUserId()+" from TMP_URGENCY_VIN_IMP_DCS ti");
	OemDAOUtil.execBatchPreparement(sql, null);
	}
	
	
	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteChargeById(Long id) throws ServiceBizException {
		TtOpOldpartImpPO wtp = TtOpOldpartImpPO.findById(id);
		wtp.setInteger("is_del", 1);
		wtp.saveIt();
	}
	/**
	 * 根据ID删除
	 * 
	 * @param id
	 * @throws ServiceBizException
	 */
	public void deleteVinById(Long id) throws ServiceBizException {
		TtOpUrgencyVinImpPO wtp = TtOpUrgencyVinImpPO.findById(id);
		wtp.setInteger("is_del", 1);
		wtp.saveIt();
	}
}
