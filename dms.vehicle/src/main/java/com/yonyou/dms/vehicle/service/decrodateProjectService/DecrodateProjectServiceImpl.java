package com.yonyou.dms.vehicle.service.decrodateProjectService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.DecrodateItemPO;
import com.yonyou.dms.common.domains.PO.basedata.RepairItemPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairItemPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateImportDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectDTO;
import com.yonyou.dms.vehicle.domains.DTO.basedata.DecrodateProjectManageDTO;

@Service
@SuppressWarnings("all")
public class DecrodateProjectServiceImpl implements DecrodateProjectService {

	@Override
	public PageInfoDto queryDecrodateProject(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pagedto = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.*,b.`WORKER_TYPE_NAME` from ( SELECT DEALER_CODE,CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE, ");
		sql.append("LOCAL_LABOUR_NAME, LABOUR_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,");
		sql.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,");
		sql.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS, ");
		sql.append(" case when Created_at < Updated_at then Updated_at else Created_at end as NewDate, ");
		sql.append(
				" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=12781002) THEN 12781002 ELSE IS_MEMBER END IS_MEMBER  ");
		sql.append(" FROM TM_DECRODATE_ITEM " + "WHERE DEALER_CODE = '" + dealerCode + "'");
		Utility.sqlToLike(sql, queryParam.get("localLabourCode"), "LOCAL_LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("localLabourName"), "LOCAL_LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("labourCode"), "LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("labourName"), "LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("spellCode"), "SPELL_CODE", null);
		String downTag = queryParam.get("downTag");
		if (null != downTag && !"".equals(downTag.trim())) {
			if (downTag.equals(12781001))
				sql.append(" and DOWN_TAG = " + downTag);
			else
				sql.append(" and (DOWN_TAG <> 12781001 OR DOWN_TAG is null) ");
		}
		String repairGroupCode = "";
		String MainGroup = queryParam.get("mainGroupCode");
		String SubGroup = queryParam.get("subGroupCode");
		if (!StringUtils.isNullOrEmpty(MainGroup) || !StringUtils.isNullOrEmpty(SubGroup)) {
			if (StringUtils.isNullOrEmpty(MainGroup)) {
				MainGroup = "";
			}
			if (StringUtils.isNullOrEmpty(SubGroup)) {
				SubGroup = "";
			}
			repairGroupCode = MainGroup + SubGroup;
		}
		if (repairGroupCode != null && !repairGroupCode.trim().equals("")) {
			sql.append(" and REPAIR_GROUP_CODE = '" + repairGroupCode + "'");
		}
		String workerTypeCode = queryParam.get("workerTypeCode");
		if (workerTypeCode != null && !workerTypeCode.trim().equals("")) {
			sql.append(" and WORKER_TYPE_CODE = '" + workerTypeCode + "'");
		}

		String modelLabourCode = queryParam.get("modelLabourCode");
		if (modelLabourCode != null && !modelLabourCode.trim().equals("")) {
			sql.append(" and (1=2 ");
			String[] codeArray = modelLabourCode.split(",");
			for (int i = 0; i < codeArray.length; i++) {
				sql.append(" or MODEL_LABOUR_CODE = '" + codeArray[i] + "'");
			}
			sql.append(" ) ");
		}

		String nullLabour = queryParam.get("nullLabour");
		if (Utility.testString(nullLabour)) {
			if (nullLabour.equals(12781001))
				sql.append(" and (STD_LABOUR_HOUR is null or STD_LABOUR_HOUR = 0) ");
			else
				sql.append(" and ( STD_LABOUR_HOUR <> 0) ");
		}
		String startDate = queryParam.get("startDate");
		String endDate = queryParam.get("endDate");
		if (startDate != null && endDate != null) {
			sql.append(")A  LEFT JOIN TM_WORKER_TYPE b ON a.WORKER_TYPE_CODE = b.WORKER_TYPE_CODE  where 1=1 ");
			sql.append(Utility.getDateCond("A", "NewDate", startDate, endDate));
		} else {
			sql.append(")A  LEFT JOIN TM_WORKER_TYPE b ON a.WORKER_TYPE_CODE = b.WORKER_TYPE_CODE  WHERE 1=1 ");
		}
		System.err.println(sql.toString());
		List<Object> queryList = new ArrayList<Object>();
		pagedto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pagedto;
	}

	/**
	 * 多选下拉框 查询项目车型组
	 */
	@Override
	public List<Map> findAllPosition() {
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,MODEL_LABOUR_CODE,MODEL_LABOUR_NAME from TM_MODEL_LABOUR where 1=1 ");
		List<Object> param = new ArrayList<>();
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public List<Map> findMain() {
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,MAIN_GROUP_CODE,MAIN_GROUP_NAME from TM_DECRODATE_GROUP where 1=1 ");
		List<Object> param = new ArrayList<>();
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public List<Map> findSub2() {
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,SUB_GROUP_CODE,SUB_GROUP_NAME from TM_DECRODATE_SUBGROUP where 1=1 ");
		List<Object> param = new ArrayList<>();
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public List<Map> findsub(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sqlSb = new StringBuffer(
				"SELECT MAIN_GROUP_CODE,DEALER_CODE,SUB_GROUP_CODE,SUB_GROUP_NAME FROM TM_DECRODATE_SUBGROUP  WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (!StringUtils.isNullOrEmpty(queryParam.get("mainGroupCode"))) {
			sqlSb.append(" and MAIN_GROUP_CODE = ?");
			params.add(queryParam.get("mainGroupCode"));
		}
		List<Map> result = DAOUtil.findAll(sqlSb.toString(), params);
		return result;

	}

	@Override
	public List<Map> findWork() {
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,WORKER_TYPE_CODE,WORKER_TYPE_NAME from TM_WORKER_TYPE  where 1=1 ");
		List<Object> param = new ArrayList<>();
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public List<Map> findAllTree(Map query) throws ServiceBizException {
		List<Map> result = new ArrayList();
		Map first = new HashMap();
		first.put("id", "wx");
		first.put("parent", "#");
		first.put("text", "维修项目");	
		result.add(first);
		StringBuffer sql1 = new StringBuffer();
		StringBuffer sql2 = new StringBuffer();
		StringBuffer sql3 = new StringBuffer();
		sql1.append("SELECT DISTINCT DEALER_CODE,DOWN_TAG AS id,'wx' as parent ,CASE DOWN_TAG WHEN '12781001' THEN '下发维修项目'"
				+ " WHEN '12781002' THEN '本地维修项目' END AS text from  TM_DECRODATE_GROUP");
		
		sql2.append(
				"select DEALER_CODE,MAIN_GROUP_CODE as id,DOWN_TAG AS parent,MAIN_GROUP_NAME as text from TM_DECRODATE_GROUP");
		sql3.append(
				" SELECT MAIN_GROUP_CODE as parent,DEALER_CODE,SUB_GROUP_CODE as id ,SUB_GROUP_NAME as text FROM TM_DECRODATE_SUBGROUP");

		result.addAll(DAOUtil.findAll(sql1.toString(), null));
		result.addAll(DAOUtil.findAll(sql2.toString(), null));
		result.addAll(DAOUtil.findAll(sql3.toString(), null));
		for (Map map : result) {
			map.remove("DEALER_CODE");
			map.put("id", map.get("id").toString().toUpperCase());
			map.put("parent", map.get("parent").toString().toUpperCase());
		}
		return result;
	}

	@Override
	public List<Map> findRepair() {
		StringBuilder sb = new StringBuilder(
				"select DEALER_CODE,REPAIR_TYPE_CODE,REPAIR_TYPE_NAME from TM_REPAIR_TYPE where 1=1 ");
		List<Object> param = new ArrayList<>();
		return DAOUtil.findAll(sb.toString(), param);
	}

	@Override
	public void addDecrodate(DecrodateProjectManageDTO decrodateProjectManageDTO) throws ServiceBizException {
		// String[] modelLabourCodes =
		// decrodateProjectManageDTO.getModelLabourCode().split(",");
		List<String> modelLabourCodes = decrodateProjectManageDTO.getModelLabourCode();
		if (!StringUtils.isNullOrEmpty(modelLabourCodes)) {
			String myLabourCode = "";
			for (int i = 0; i < modelLabourCodes.size(); i++) {
				DecrodateItemPO diPo = new DecrodateItemPO();
				String code = modelLabourCodes.get(i);
				String labourCode = decrodateProjectManageDTO.getLabourCode();
				String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
				if (!myLabourCode.equals("")) {// 此处只有车型组的复制才有批量的操作，此处循环中保持了代码为了下一次使用。
					diPo.setString("LABOUR_CODE", myLabourCode);
				} else {

					TmRepairItemPO riPo = TmRepairItemPO.findByCompositeKeys(dealerCode, labourCode, code);

					diPo.setString("LABOUR_CODE", labourCode);

					if (!StringUtils.isNullOrEmpty(riPo)) {
						throw new ServiceBizException("维修项目代码重复，请重新输入！");
					}
				}
				diPo.setString("LOCAL_LABOUR_CODE", decrodateProjectManageDTO.getLocalLabourCode());
				diPo.setString("LOCAL_LABOUR_NAME", decrodateProjectManageDTO.getLocalLabourName());
				diPo.setString("LABOUR_NAME", decrodateProjectManageDTO.getLabourName());
				diPo.setString("STD_LABOUR_HOUR", decrodateProjectManageDTO.getStdLabourHour());
				diPo.setString("ASSIGN_LABOUR_HOUR", decrodateProjectManageDTO.getAssignLabourHour());
				diPo.setString("OPERATION_CODE", decrodateProjectManageDTO.getOperationCode());
				diPo.setString("WORKER_TYPE_CODE", decrodateProjectManageDTO.getWorkerTypeCode());
				diPo.setString("SPELL_CODE", decrodateProjectManageDTO.getSpellCode());
				diPo.setString("MODEL_LABOUR_CODE", modelLabourCodes.get(i));
				String repairGroupCode = decrodateProjectManageDTO.getMainGroupCode()
						+ decrodateProjectManageDTO.getSubGroupCode();
				diPo.setString("REPAIR_GROUP_CODE", repairGroupCode);
				// diPo.setString("DEALER_CODE", dealerCode);
				diPo.setString("REPAIR_TYPE_CODE", decrodateProjectManageDTO.getRepairTypeCode());
				diPo.setString("CLAIM_LABOUR", decrodateProjectManageDTO.getClaimLabour());
				diPo.setString("DOWN_TAG", "12781002");
				diPo.setString("IS_MEMBER", decrodateProjectManageDTO.getIsMember());
				diPo.saveIt();

			}
		}
	}

	@Override
	public void editDecrodate(DecrodateProjectDTO decrodateProjectDTO) throws ServiceBizException {
		String code = decrodateProjectDTO.getModelLabourCode();
		String labourCode = decrodateProjectDTO.getLabourCode();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		DecrodateItemPO diPo = DecrodateItemPO.findByCompositeKeys(dealerCode, labourCode, code);
		diPo.setString("LOCAL_LABOUR_CODE", decrodateProjectDTO.getLocalLabourCode());
		diPo.setString("LOCAL_LABOUR_NAME", decrodateProjectDTO.getLocalLabourName());
		diPo.setString("LABOUR_NAME", decrodateProjectDTO.getLabourName());
		diPo.setString("STD_LABOUR_HOUR", decrodateProjectDTO.getStdLabourHour());
		diPo.setString("ASSIGN_LABOUR_HOUR", decrodateProjectDTO.getAssignLabourHour());
		diPo.setString("OPERATION_CODE", decrodateProjectDTO.getOperationCode());
		diPo.setString("WORKER_TYPE_CODE", decrodateProjectDTO.getWorkerTypeCode());
		diPo.setString("SPELL_CODE", decrodateProjectDTO.getSpellCode());
		diPo.setString("REPAIR_TYPE_CODE", decrodateProjectDTO.getRepairTypeCode());
		diPo.setString("CLAIM_LABOUR", decrodateProjectDTO.getClaimLabour());
		diPo.setString("DOWN_TAG", "12781002");
		diPo.setString("IS_MEMBER", decrodateProjectDTO.getIsMember());
		diPo.saveIt();
	}

	@Override
	public Map<String, Object> findById(String id, String id1) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select * from ( SELECT DEALER_CODE,CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE, ");
		sql.append("LOCAL_LABOUR_NAME, LABOUR_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,");
		sql.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,");
		sql.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS, ");
		sql.append(" case when Created_at < Updated_at then Updated_at else Created_at end as NewDate, ");
		sql.append(
				" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=12781002) THEN 12781002 ELSE IS_MEMBER END IS_MEMBER  ");
		sql.append(" FROM TM_DECRODATE_ITEM " + "WHERE DEALER_CODE = '" + dealerCode + "'");
		sql.append(" AND MODEL_LABOUR_CODE = '" + id + "' AND LABOUR_CODE = '" + id1 + "' ) a ");
		Map map = DAOUtil.findFirst(sql.toString(), null);
		String repairGroupCode = map.get("REPAIR_GROUP_CODE").toString();
		String MainGroup = repairGroupCode.substring(0, 2);
		String SubGroup = repairGroupCode.substring(2, 4);
		System.err.println(repairGroupCode + "  " + MainGroup + "  " + SubGroup);
		map.put("MAIN_GROUP_CODE", MainGroup);
		map.put("SUB_GROUP_CODE", SubGroup);
		return map;
	}

	@Override
	public void copyAll(DecrodateProjectDTO decrodateProjectDTO) throws ServiceBizException {
		String srcLabourModel = decrodateProjectDTO.getSrcLabourModel();
		String dstLabourModel = decrodateProjectDTO.getDstLabourModel();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String timestamp = new Date().toString();
		if (srcLabourModel == null || dstLabourModel == null) {
			String message = srcLabourModel + dstLabourModel + "原车型组、复制到车辆组存在为空的字符，请检查！";
			throw new ServiceBizException(message);
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" DELETE  t  FROM  (SELECT a.LABOUR_CODE FROM TM_REPAIR_ITEM a WHERE a.DEALER_CODE = '" + dealerCode
				+ "' AND  ");
		sql.append(" a.MODEL_LABOUR_CODE = '" + srcLabourModel + "') a,TM_REPAIR_ITEM t ");
		sql.append(" WHERE t.DEALER_CODE = '" + dealerCode + "'  AND t.MODEL_LABOUR_CODE = '" + dstLabourModel
				+ "' AND a.LABOUR_CODE=t.LABOUR_CODE ");
		System.err.println("111   " + sql.toString());
		Base.exec(sql.toString());

		StringBuffer sbSqlInsert = new StringBuffer();
		sbSqlInsert.append(
				"insert into TM_REPAIR_ITEM (LOCAL_LABOUR_CODE,STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR, OPERATION_CODE,");
		sbSqlInsert
				.append("WORKER_TYPE_CODE,SPELL_CODE,LABOUR_CODE,LABOUR_NAME,CREATED_BY,CREATED_AT,MODEL_LABOUR_CODE,");
		sbSqlInsert.append(
				"REPAIR_GROUP_CODE,LOCAL_LABOUR_NAME,DEALER_CODE,DOWN_TAG)  select srct.LOCAL_LABOUR_CODE,srct.STD_LABOUR_HOUR,");
		sbSqlInsert.append("srct.ASSIGN_LABOUR_HOUR , srct.OPERATION_CODE,srct.WORKER_TYPE_CODE,srct.SPELL_CODE,");
		sbSqlInsert.append("srct.LABOUR_CODE,srct.LABOUR_NAME,'" + userId + "' as CREATED_BY ,'" + timestamp
				+ "' as  CREATED_AT, '" + dstLabourModel + "' as MODEL_LABOUR_CODE ");
		sbSqlInsert.append(",srct.REPAIR_GROUP_CODE,srct.LOCAL_LABOUR_NAME,srct.DEALER_CODE,12781002 as DOWN_TAG ");
		sbSqlInsert.append("from TM_REPAIR_ITEM srct where srct.DEALER_CODE ='" + dealerCode
				+ "' and srct.MODEL_LABOUR_CODE='" + srcLabourModel + "'");
		System.err.println("222   " + sbSqlInsert.toString());
		Base.exec(sbSqlInsert.toString());

		StringBuffer sbSqlUpdatePart = new StringBuffer();
		sbSqlUpdatePart.append("DELETE FROM TM_REPAIR_ITEM_PART WHERE  CONCAT(LABOUR_CODE,MODEL_LABOUR_CODE)  in ("
				+ " select CONCAT(LABOUR_CODE,MODEL_LABOUR_CODE) FROM TM_REPAIR_ITEM t WHERE t.DEALER_CODE ='"
				+ dealerCode + "'"
				+ " AND t.LABOUR_CODE IN ( SELECT LABOUR_CODE FROM  TM_REPAIR_ITEM src WHERE src.DEALER_CODE ='"
				+ dealerCode + "'" + " AND t.LABOUR_CODE=src.LABOUR_CODE AND src.MODEL_LABOUR_CODE='" + srcLabourModel
				+ "') " + " AND t.MODEL_LABOUR_CODE='" + dstLabourModel + "')");
		System.err.println("333   " + sbSqlUpdatePart.toString());
		Base.exec(sbSqlUpdatePart.toString());

		List<RepairItemPartPO> list = RepairItemPartPO
				.findBySQL("SELECT * FROM TM_REPAIR_ITEM_PART WHERE DEALER_CODE = '" + dealerCode + "' "
						+ " AND MODEL_LABOUR_CODE = '" + srcLabourModel + "'", null);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				RepairItemPartPO po = list.get(i);
				po.setString("MODEL_LABOUR_CODE", dstLabourModel);
				po.saveIt();
			}
		}

	}
	
	@Override
	public void copyOne(DecrodateProjectManageDTO decrodateProjectManageDTO) throws ServiceBizException {
		List<String> modelLabourCodes = decrodateProjectManageDTO.getModelLabourCode();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (!StringUtils.isNullOrEmpty(modelLabourCodes)) {
			for (int i = 0; i < modelLabourCodes.size(); i++) {
				if (modelLabourCodes.get(i) == null || decrodateProjectManageDTO.getLocalLabourCode() == null
						|| decrodateProjectManageDTO.getCodeFrom() == null) {
					throw new ServiceBizException("维修项目、项目车型组、原维修项目存在为空的字符，请检查！");
				}
				DecrodateItemPO po = DecrodateItemPO.findByCompositeKeys(dealerCode,
						decrodateProjectManageDTO.getLocalLabourCode(), modelLabourCodes.get(i));
				if(!StringUtils.isNullOrEmpty(po)){					
					po.deleteCascadeShallow();
				}
				DecrodateItemPO po1 = new DecrodateItemPO();
				po1.setString("LABOUR_CODE", decrodateProjectManageDTO.getLocalLabourCode());
				po1.setString("LOCAL_LABOUR_CODE", decrodateProjectManageDTO.getLocalLabourCode());
				po1.setString("LOCAL_LABOUR_NAME", decrodateProjectManageDTO.getLocalLabourName());
				po1.setString("LABOUR_NAME", decrodateProjectManageDTO.getLabourName());
				po1.setString("STD_LABOUR_HOUR", decrodateProjectManageDTO.getStdLabourHour());
				po1.setString("ASSIGN_LABOUR_HOUR", decrodateProjectManageDTO.getAssignLabourHour());
				po1.setString("OPERATION_CODE", decrodateProjectManageDTO.getOperationCode());
				po1.setString("WORKER_TYPE_CODE", decrodateProjectManageDTO.getWorkerTypeCode());
				po1.setString("SPELL_CODE", decrodateProjectManageDTO.getSpellCode());
				po1.setString("MODEL_LABOUR_CODE", modelLabourCodes.get(i));
				po1.setString("REPAIR_GROUP_CODE", decrodateProjectManageDTO.getRepairGroupCode());
				po1.setString("DEALER_CODE", dealerCode);
				po1.setString("DOWN_TAG", CommonConstants.DICT_IS_NO);
				po1.saveIt();
			}
		}
	}
	
    @Override
    public List<Map> queryDecrodate(Map<String, String> queryParam) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select a.*,b.`WORKER_TYPE_NAME`  from ( SELECT DEALER_CODE,CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE, ");
		sql.append("LOCAL_LABOUR_NAME, LABOUR_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,");
		sql.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,");
		sql.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS, ");
		sql.append(" case when Created_at < Updated_at then Updated_at else Created_at end as NewDate, ");
		sql.append(
				" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=12781002) THEN 12781002 ELSE IS_MEMBER END IS_MEMBER  ");
		sql.append(" FROM TM_DECRODATE_ITEM " + "WHERE DEALER_CODE = '" + dealerCode + "'");
		Utility.sqlToLike(sql, queryParam.get("localLabourCode"), "LOCAL_LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("localLabourName"), "LOCAL_LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("labourCode"), "LABOUR_CODE", null);
		Utility.sqlToLike(sql, queryParam.get("labourName"), "LABOUR_NAME", null);
		Utility.sqlToLike(sql, queryParam.get("spellCode"), "SPELL_CODE", null);
		String downTag = queryParam.get("downTag");
		if (null != downTag && !"".equals(downTag.trim())) {
			if (downTag.equals(12781001))
				sql.append(" and DOWN_TAG = " + downTag);
			else
				sql.append(" and (DOWN_TAG <> 12781001 OR DOWN_TAG is null) ");
		}
		String repairGroupCode = "";
		String MainGroup = queryParam.get("mainGroupCode");
		String SubGroup = queryParam.get("subGroupCode");
		if (!StringUtils.isNullOrEmpty(MainGroup) || !StringUtils.isNullOrEmpty(SubGroup)) {
			if (StringUtils.isNullOrEmpty(MainGroup)) {
				MainGroup = "";
			}
			if (StringUtils.isNullOrEmpty(SubGroup)) {
				SubGroup = "";
			}
			repairGroupCode = MainGroup + SubGroup;
		}
		if (repairGroupCode != null && !repairGroupCode.trim().equals("")) {
			sql.append(" and REPAIR_GROUP_CODE = '" + repairGroupCode + "'");
		}
		String workerTypeCode = queryParam.get("workerTypeCode");
		if (workerTypeCode != null && !workerTypeCode.trim().equals("")) {
			sql.append(" and WORKER_TYPE_CODE = '" + workerTypeCode + "'");
		}

		String modelLabourCode = queryParam.get("modelLabourCode");
		if (modelLabourCode != null && !modelLabourCode.trim().equals("")) {
			sql.append(" and (1=2 ");
			String[] codeArray = modelLabourCode.split(",");
			for (int i = 0; i < codeArray.length; i++) {
				sql.append(" or MODEL_LABOUR_CODE = '" + codeArray[i] + "'");
			}
			sql.append(" ) ");
		}

		String nullLabour = queryParam.get("nullLabour");
		if (Utility.testString(nullLabour)) {
			if (nullLabour.equals(12781001))
				sql.append(" and (STD_LABOUR_HOUR is null or STD_LABOUR_HOUR = 0) ");
			else
				sql.append(" and ( STD_LABOUR_HOUR <> 0) ");
		}
		String startDate = queryParam.get("startDate");
		String endDate = queryParam.get("endDate");
		if (startDate != null && endDate != null) {
			sql.append(")A  LEFT JOIN TM_WORKER_TYPE b ON a.WORKER_TYPE_CODE = b.WORKER_TYPE_CODE  where 1=1 ");
			sql.append(Utility.getDateCond("A", "NewDate", startDate, endDate));
		} else {
			sql.append(")A  LEFT JOIN TM_WORKER_TYPE b ON a.WORKER_TYPE_CODE = b.WORKER_TYPE_CODE  WHERE 1=1 ");
		}
    	 List<Object> queryList = new ArrayList<Object>();
    	 List<Map> resultList = DAOUtil.findAll(sql.toString(), queryList);
    	
        return resultList;
    }
    
    @Override
    public void addInfo(DecrodateImportDTO dto) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	if(StringUtils.isNullOrEmpty(dto.getModelLabourCode()) || StringUtils.isNullOrEmpty(dto.getRepairGroupCode()) || 
    			StringUtils.isNullOrEmpty(dto.getLabourCode()) || StringUtils.isNullOrEmpty(dto.getLabourName())){
    		throw new ServiceBizException("装潢车型分组代码,装潢项目分组代码,装潢项目代码,装潢项目名称为必填项!");
    	}else{
    		DecrodateItemPO diPo = new DecrodateItemPO();
    		diPo.setString("LABOUR_CODE", dto.getLabourCode());
    		diPo.setString("REPAIR_TYPE_CODE", dto.getRepairTypeCode());
    		diPo.setString("CLAIM_LABOUR", dto.getClaimLabour());
    		diPo.setString("LOCAL_LABOUR_CODE", dto.getLocalLabourCode());
    		diPo.setString("LOCAL_LABOUR_NAME", dto.getLocalLabourName());
    		diPo.setString("LABOUR_NAME", dto.getLabourName());
    		diPo.setString("STD_LABOUR_HOUR", dto.getStdLabourHour());
    		diPo.setString("ASSIGN_LABOUR_HOUR", dto.getAssignLabourHour());
    		diPo.setString("OPERATION_CODE", dto.getOperationCode());
    		diPo.setString("WORKER_TYPE_CODE", dto.getWorkerTypeCode());
    		diPo.setString("SPELL_CODE", dto.getSpellCode());
    		diPo.setString("MODEL_LABOUR_CODE", dto.getModelLabourCode());
    		diPo.setString("REPAIR_GROUP_CODE", dto.getRepairGroupCode());
    		diPo.setString("DEALER_CODE", dealerCode);
    		
    		List<DecrodateItemPO> mainList = DecrodateItemPO.findBySQL("SELECT * FROM TM_DECRODATE_ITEM WHERE DEALER_CODE = '"+dealerCode+"' "
    				+ " AND LABOUR_CODE = '"+dto.getLabourCode()+"' AND MODEL_LABOUR_CODE = '"+dto.getModelLabourCode()+"'", null);
    		if(mainList!=null&&mainList.size()>0){
    			if(!StringUtils.isNullOrEmpty(dto.getLabourCode()) && !StringUtils.isNullOrEmpty(dto.getModelLabourCode())){
    				DecrodateItemPO po2 = new DecrodateItemPO();
    				po2 = mainList.get(0);
    				if(po2.getString("DOWN_TAG")==null || CommonConstants.DICT_IS_NO.equals(po2.getString("DOWN_TAG"))){
    					po2.setString("LABOUR_CODE", dto.getLabourCode());
    					po2.setString("REPAIR_TYPE_CODE", dto.getRepairTypeCode());
    					po2.setString("CLAIM_LABOUR", dto.getClaimLabour());
    					po2.setString("LOCAL_LABOUR_CODE", dto.getLocalLabourCode());
    					po2.setString("LOCAL_LABOUR_NAME", dto.getLocalLabourName());
    					po2.setString("LABOUR_NAME", dto.getLabourName());
    					po2.setString("STD_LABOUR_HOUR", dto.getStdLabourHour());
    					po2.setString("ASSIGN_LABOUR_HOUR", dto.getAssignLabourHour());
    					po2.setString("OPERATION_CODE", dto.getOperationCode());
    					po2.setString("WORKER_TYPE_CODE", dto.getWorkerTypeCode());
    					po2.setString("SPELL_CODE", dto.getSpellCode());
    					po2.setString("MODEL_LABOUR_CODE", dto.getModelLabourCode());
    					po2.setString("REPAIR_GROUP_CODE", dto.getRepairGroupCode());
    					po2.setString("DEALER_CODE", dealerCode);
    					po2.saveIt();
    				}
    				if(po2.getString("DOWN_TAG")!=null || CommonConstants.DICT_IS_YES.equals(po2.getString("DOWN_TAG"))){
    					po2.setString("REPLACE_STATUS", CommonConstants.DICT_IS_YES);
    					po2.setString("LOCAL_LABOUR_CODE", dto.getLocalLabourCode());
    					po2.setString("LOCAL_LABOUR_NAME", dto.getLocalLabourName());
    					po2.setString("STD_LABOUR_HOUR", dto.getStdLabourHour());
    					po2.setString("ASSIGN_LABOUR_HOUR", dto.getAssignLabourHour());
    					po2.saveIt();
    				}
    			}
    		}else{
    			diPo.setString("DOWN_TAG",  CommonConstants.DICT_IS_NO);
    			diPo.saveIt();
    		}
    		
    	}
    	
    }

}
