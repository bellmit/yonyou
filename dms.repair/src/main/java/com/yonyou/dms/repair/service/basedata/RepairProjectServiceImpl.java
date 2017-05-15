/**
 * 
 */
package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RepairProjectServiceImpl implements RepairProjectService {

	@Override
	public List<Map> findProjectModelList() {
		String dealerCode = Utility.getGroupEntity(FrameworkUtil.getLoginInfo().getDealerCode(), "TM_MODEL_LABOUR");
		String sql = "SELECT A.DEALER_CODE,A.MODEL_LABOUR_CODE,A.MODEL_LABOUR_NAME,CONCAT(A.MODEL_LABOUR_CODE,'    |    ',A.MODEL_LABOUR_NAME) as GROUP_NAME FROM TM_MODEL_LABOUR A WHERE A.DEALER_CODE = ?";
		List queryParam = new ArrayList();
		queryParam.add(dealerCode);
		return DAOUtil.findAll(sql, queryParam);
	}

	@Override
	public Map<String, String> findModelForInput() {
		String dealerCode = Utility.getGroupEntity(FrameworkUtil.getLoginInfo().getDealerCode(), "TM_MODEL_LABOUR");
		String sql = "SELECT A.DEALER_CODE,A.MODEL_LABOUR_CODE,B.MODEL_CODE FROM TM_MODEL_LABOUR A LEFT JOIN TM_MODEL B ON A.DEALER_CODE=B.DEALER_CODE AND A.MODEL_LABOUR_CODE=B.MODEL_LABOUR_CODE WHERE A.DEALER_CODE = ?";
		List queryParam = new ArrayList();
		queryParam.add(dealerCode);
		List<Map> list = DAOUtil.findAll(sql, queryParam);
		Map<String, String> callBack = new HashMap<String, String>();
		for (Map map : list) {
			if (StringUtils.isNullOrEmpty(callBack.get(objToStr(map.get("MODEL_LABOUR_CODE"))))) {// 为空表示callBack里面没有这个code
				callBack.put(objToStr(map.get("MODEL_LABOUR_CODE")), objToStr(map.get("MODEL_CODE")));
			} else {
				callBack.put(objToStr(map.get("MODEL_LABOUR_CODE")),
						callBack.get(map.get("MODEL_LABOUR_CODE")) + "," + objToStr(map.get("MODEL_CODE")));
			}
		}
		return callBack;
	}

	/**
	 * obj类型转str类型
	 * 
	 * @param obj
	 * @return
	 */
	public String objToStr(Object obj) {
		return StringUtils.isNullOrEmpty(obj) ? "" : obj.toString();
	}

	@Override
	public List<Map> findRepairProjectTree() {
		List<Map> list = new ArrayList<Map>();
		Map first = new HashMap();
		first.put("id", "one");
		first.put("parent", "#");
		first.put("text", "CJD下发项目");
		list.add(first);
		Map second = new HashMap();
		second.put("id", "two");
		second.put("parent", "#");
		second.put("text", "FIAT下发项目");
		list.add(second);
		Map third = new HashMap();
		third.put("id", "three");
		third.put("parent", "#");
		third.put("text", "本地维修项目");
		list.add(third);
		// 查询维修项目大类
		String sql = "SELECT MAIN_GROUP_CODE AS id,MAIN_GROUP_NAME AS text,CASE WHEN DOWN_TAG=12781001 THEN CASE WHEN GROUP_TYPE=70391001 THEN 'one' ELSE 'two' END ELSE 'three' END AS parent FROM TM_LABOUR_GROUP where DEALER_CODE='"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "'";
		List<Map> group = Base.findAll(sql);
		sql = "SELECT A.MAIN_GROUP_CODE AS parent,A.SUB_GROUP_CODE AS id,A.SUB_GROUP_NAME AS text FROM TM_LABOUR_GROUP B RIGHT JOIN TM_LABOUR_SUBGROUP A ON A.DEALER_CODE=B.DEALER_CODE AND A.MAIN_GROUP_CODE=B.MAIN_GROUP_CODE AND a.DEALER_CODE='"
				+ FrameworkUtil.getLoginInfo().getDealerCode() + "'";
		List<Map> sub = Base.findAll(sql);
		list.addAll(group);
		list.addAll(sub);
		return list;
	}

	@Override
	public PageInfoDto findRepairProjectList(Map<String, String> queryParam) {
		// Utility.getDefaultValue("1314");//缓存开关
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select distinct a.* ,b.IS_RESTRICT from (SELECT dealer_code, CLAIM_LABOUR ,REPAIR_TYPE_CODE,LOCAL_LABOUR_CODE, LABOUR_CODE,MODEL_YEAR, ")
				.append("LOCAL_LABOUR_NAME, LABOUR_NAME, STD_LABOUR_HOUR, ASSIGN_LABOUR_HOUR,")
				.append("WORKER_TYPE_CODE, SPELL_CODE, MODEL_LABOUR_CODE,OPERATION_CODE,")
				.append("REPAIR_GROUP_CODE ,VER, DOWN_TAG, OEM_LABOUR_HOUR, CREATED_AT, UPDATED_AT,REPLACE_STATUS,")
				.append(" CASE WHEN EXISTS (SELECT * FROM TW_ROITEM_INFIX_RELATION T WHERE T.IS_VALID=")
				.append(DictCodeConstants.IS_YES).append(" AND I.dealer_code = T.dealer_code ")
				.append("AND I.LABOUR_CODE = T.LABOUR_CODE) THEN '12781001' ELSE '12781002' END AS INFIX_CODE,LABOUR_TYPE, ")
				.append(" case when CREATED_AT < UPDATED_AT then UPDATED_AT else CREATED_AT end as NewDate, ")
				.append(" case when (IS_MEMBER IS NULL OR IS_MEMBER=0 OR IS_MEMBER=").append(DictCodeConstants.IS_NOT)
				.append(") THEN 12781002 ELSE IS_MEMBER END IS_MEMBER ").append(" FROM (")
				.append(CommonConstants.VM_REPAIR_ITEM).append(") I ").append("WHERE dealer_code = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'");
		Utility.sqlToLike(sb, queryParam.get("localLabourCode"), "LOCAL_LABOUR_CODE", null);
		Utility.sqlToLike(sb, queryParam.get("labourCode"), "LABOUR_CODE", null);
		Utility.sqlToLike(sb, queryParam.get("labourName"), "LABOUR_NAME", null);
		Utility.sqlToLike(sb, queryParam.get("localNabourName"), "LOCAL_LABOUR_NAME", null);
		Utility.sqlToLike(sb, queryParam.get("spellCode"), "SPELL_CODE", null);
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))
				&& Utility.getDefaultValue("9008").equals(DictCodeConstants.DICT_IS_YES)) {
			Utility.sqlToLike(sb, queryParam.get("modelYear"), "MODEL_YEAR", null);
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))
				&& Utility.getDefaultValue("9008").equals(DictCodeConstants.DICT_IS_NO)) {
			sb.append(" and left(labour_code,4)='").append(queryParam.get("modelYear")).append("'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partNo"))) {
			sb.append(
					" AND EXISTS (SELECT * FROM TM_REPAIR_ITEM_PART RI  WHERE RI.LABOUR_CODE = I.LABOUR_CODE AND RI.MODEL_LABOUR_CODE = I.MODEL_LABOUR_CODE AND RI.dealer_code = I.dealer_code AND RI.PART_NO LIKE '%")
					.append(queryParam.get("partNo")).append("%') ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sb.append(" AND EXISTS (SELECT * FROM TM_REPAIR_ITEM_PART RI  inner join (" + CommonConstants.VM_PART_INFO
					+ ") vp on vp.part_no=ri.part_no and vp.dealer_code=ri.dealer_code WHERE RI.LABOUR_CODE = I.LABOUR_CODE AND RI.MODEL_LABOUR_CODE = I.MODEL_LABOUR_CODE AND RI.dealer_code = I.dealer_code AND VP.PART_NAME LIKE '%")
					.append(queryParam.get("partName")).append("%'  )");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("downTag"))) {
			if (queryParam.get("downTag").equals(DictCodeConstants.DICT_IS_YES)) {
				sb.append(" and DOWN_TAG = ").append(DictCodeConstants.DICT_IS_YES);
			} else {
				sb.append(" and (DOWN_TAG <> ").append(DictCodeConstants.DICT_IS_YES).append(" OR DOWN_TAG is null) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairGroupCode"))) {
			Utility.sqlToEquals(sb, queryParam.get("repairGroupCode"), "REPAIR_GROUP_CODE", null);
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("workerTypeCode"))) {
			Utility.sqlToEquals(sb, queryParam.get("workerTypeCode"), "WORKER_TYPE_CODE", null);
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelLabourCode"))) {
			String[] split = queryParam.get("modelLabourCode").split(",");
			StringBuffer sql = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				sql.append(" or MODEL_LABOUR_CODE = '").append(split[i].split("|")[0]).append("' ");
			}
			if (!StringUtils.isNullOrEmpty(sql)) {
				sb.append(" and (1=2 ").append(sql.toString()).append(" ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("nullLabour"))) {
			if (queryParam.get("nullLabour").equals(DictCodeConstants.DICT_IS_YES)) {
				sb.append(" and (STD_LABOUR_HOUR is null or STD_LABOUR_HOUR = 0) ");
			} else {
				sb.append(" and ( STD_LABOUR_HOUR <> 0) ");
			}
		}
		sb.append(" )A LEFT JOIN TM_LOCAL_LABOUR_MESSAGE B ON A.dealer_code = B.dealer_code WHERE A.dealer_code='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'");
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sb.append(" and 1=1 ");
			Utility.sqlToDate(sb, queryParam.get("beginDate"), queryParam.get("endDate"), "NewDate", "A");
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMember"))
					&& queryParam.get("isMember").equals(DictCodeConstants.DICT_IS_NO)) {
				sb.append(" and A.IS_MEMBER=").append(queryParam.get("isMember")).append(" ");
			}
		} else {
			sb.append(" and 1=1 ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("isMember"))
					&& queryParam.get("isMember").equals(DictCodeConstants.DICT_IS_NO)) {
				sb.append(" and A.IS_MEMBER=").append(queryParam.get("isMember")).append(" ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("posName"))
				|| !StringUtils.isNullOrEmpty(queryParam.get("appName"))) {
			sb.append(" and exists (select * from TW_ROITEM_INFIX_RELATION b where B.IS_VALID=")
					.append(DictCodeConstants.DICT_IS_YES)
					.append(" AND a.LABOUR_CODE=b.LABOUR_CODE and a.dealer_code=b.dealer_code and ")
					.append(" exists (select * from TW_POS_INFIX_RELATION c where C.IS_VALID=")
					.append(DictCodeConstants.DICT_IS_YES)
					.append(" AND  b.dealer_code=c.dealer_code and b.PART_INFIX=c.PART_INFIX and ")
					.append(" exists (select * from TW_MALFUNCTION_POSITION d where c.dealer_code=d.dealer_code and c.pos_code=d.pos_code AND D.IS_VALID=")
					.append(DictCodeConstants.DICT_IS_YES).append(" ");
			if (!StringUtils.isNullOrEmpty(queryParam.get("posName"))) {
				Utility.sqlToLike(sb, queryParam.get("posName"), "pos_name", "d");
			}
			if (!StringUtils.isNullOrEmpty(queryParam.get("appName"))) {
				sb.append(" and exists (select * from TW_MALFUNCTION_MPOS_RELATION e  where E.IS_VALID=")
						.append(DictCodeConstants.DICT_IS_YES)
						.append(" AND d.dealer_code=e.dealer_code and d.pos_code = e.pos_code and exists")
						.append(" (select * from TW_MALFUNCTION_APPEARANCE f where F.IS_VALID=")
						.append(DictCodeConstants.DICT_IS_YES)
						.append(" AND e.dealer_code=f.dealer_code and  e.app_code=f.app_code and f.app_name like '%")
						.append(queryParam.get("appName")).append("%'))");
			}
			sb.append(" )  ))");
		}
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public PageInfoDto findRepairProjectItem(Map<String, String> queryParam) {
		String defaultValue = Utility.getDefaultValue("1180");
		Boolean flag = false;
		if (!StringUtils.isNullOrEmpty(defaultValue) && defaultValue.equals(DictCodeConstants.DICT_IS_YES)) {
			flag = true;
		}
		StringBuffer sb = new StringBuffer();
		if (!StringUtils.isNullOrEmpty(queryParam.get("isOEM"))
				&& queryParam.get("isOEM").equals(DictCodeConstants.DICT_IS_YES)) {
			if (flag) {
				sb.append(
						"SELECT PART_NO,PART_NAME,LABOUR_CODE,PART_QUANTITY,STORAGE_CODE,MODEL_LABOUR_CODE,PART_MODEL_GROUP_CODE_SET,")
						.append("REPAIR_I_P_ID,DEALER_CODE,UPDATED_BY,UPDATED_AT,CREATED_BY,CREATED_AT,VER,SALES_PRICE,STOCK_QUANTITY,LIMIT_PRICE,")
						.append("DOWN_TAG,PART_INFIX,POS_CODE,POS_NAME,MIN(WAR_LEVEL) AS WAR_LEVEL FROM (");
			}
			sb.append("SELECT A.PART_NO,C.PART_NAME,A.LABOUR_CODE,A.PART_QUANTITY,BB.STORAGE_CODE,")
					.append("A.MODEL_LABOUR_CODE,A.PART_MODEL_GROUP_CODE_SET,A.REPAIR_I_P_ID,A.DEALER_CODE,")
					.append("A.UPDATED_BY,A.UPDATED_AT,A.CREATED_BY,A.CREATED_AT,A.Ver,")
					.append("COALESCE(BB.SALES_PRICE, BB.LIMIT_PRICE) as SALES_PRICE,BB.STOCK_QUANTITY,C.LIMIT_PRICE,C.DOWN_TAG,C.PART_INFIX ");
			if (!flag) {
				sb.append(",'' AS POS_CODE,'' AS POS_NAME,'' AS WAR_LEVEL");
			} else {
				sb.append(
						",E.POS_CODE,E.POS_NAME,CASE WHEN (SELECT COALESCE(CURR_WAR_COUNT,0) FROM TW_WARRANTY_RECORD F WHERE F.DEALER_CODE = '")
						.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND F.VIN = '")
						.append(queryParam.get("vin"))
						.append("' AND F.POS_CODE = E.POS_CODE AND F.WAR_TYPE = 20841001) + 1 ")
						.append(" >= COALESCE(W.WAR_COUNT,0) THEN W.WAR_LEVEL ELSE 99999999 END AS WAR_LEVEL");
			}
			sb.append(" FROM ("+CommonConstants.VM_REPAIR_ITEM_PART+") A left join tm_part_stock ")
					.append(" BB on (BB.DEALER_CODE = A.DEALER_CODE and BB.part_No = A.part_no  ) ")
					.append("LEFT JOIN TM_PART_INFO C on (C.PART_NO = A.PART_NO AND C.DEALER_CODE = A.DEALER_CODE) ");
			if (flag) {
				sb.append(" LEFT JOIN TW_POS_INFIX_RELATION D ON A.DEALER_CODE = D.DEALER_CODE AND C.PART_INFIX ")
						.append(" = D.PART_INFIX AND D.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON E.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" AND  A.DEALER_CODE = E.DEALER_CODE AND D.POS_CODE = E.POS_CODE ")
						.append(" LEFT JOIN  TW_WARNING_RULE W ON W.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" AND A.DEALER_CODE = W.DEALER_CODE AND E.POS_CODE = W.POS_CODE AND W.WAR_TYPE = 20841001 ");
			}
			sb.append(" WHERE 1=1 ");
			Utility.sqlToEquals(sb, queryParam.get("labourCode"), "LABOUR_CODE", "A");
			Utility.sqlToEquals(sb, queryParam.get("modelLabourCode"), "MODEL_LABOUR_CODE", "A");
			Utility.sqlToEquals(sb, queryParam.get("entityCode"), "DEALER_CODE", "A");
			if (flag) {
				sb.append(
						" ) TEMP GROUP BY Part_No,Part_Name,Labour_Code,Part_Quantity,Storage_Code,Model_Labour_Code,")
						.append(" Part_Model_Group_Code_Set,Repair_I_P_Id,DEALER_CODE,UPDATED_BY,UPDATED_AT,CREATED_BY,CREATED_AT,")
						.append(" Ver,SALES_PRICE,STOCK_QUANTITY,LIMIT_PRICE,DOWN_TAG,PART_INFIX,POS_CODE,POS_NAME");
			}
		} else {
			if (flag) {
				sb.append(
						"SELECT PART_NO,PART_NAME,LABOUR_CODE,PART_QUANTITY,STORAGE_CODE,MODEL_LABOUR_CODE,PART_MODEL_GROUP_CODE_SET,")
						.append("REPAIR_I_P_ID,DEALER_CODE,UPDATED_BY,UPDATED_AT,CREATED_BY,CREATED_AT,VER,SALES_PRICE,STOCK_QUANTITY,LIMIT_PRICE,")
						.append("DOWN_TAG,PART_INFIX,POS_CODE,POS_NAME,MIN(WAR_LEVEL) AS WAR_LEVEL FROM (");
			}
			sb.append("SELECT A.PART_NO,C.PART_NAME,A.LABOUR_CODE,A.PART_QUANTITY,BB.STORAGE_CODE,")
					.append("A.MODEL_LABOUR_CODE,A.PART_MODEL_GROUP_CODE_SET,A.REPAIR_I_P_ID,A.DEALER_CODE,")
					.append("A.UPDATED_BY,A.UPDATED_AT,A.CREATED_BY,A.CREATED_AT,A.Ver,")
					.append("COALESCE(BB.SALES_PRICE, BB.LIMIT_PRICE) as SALES_PRICE,BB.STOCK_QUANTITY,C.LIMIT_PRICE,C.DOWN_TAG,C.PART_INFIX ");
			if (!flag) {
				sb.append(",'' AS POS_CODE,'' AS POS_NAME,'' AS WAR_LEVEL");
			} else {
				sb.append(
						",E.POS_CODE,E.POS_NAME,CASE WHEN (SELECT COALESCE(CURR_WAR_COUNT,0) FROM TW_WARRANTY_RECORD F WHERE F.DEALER_CODE =  '")
						.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND F.VIN = '")
						.append(queryParam.get("vin"))
						.append("' AND F.POS_CODE = E.POS_CODE AND F.WAR_TYPE = 20841001) + 1 ")
						.append(" >= COALESCE(W.WAR_COUNT,0) THEN W.WAR_LEVEL ELSE 99999999 END AS WAR_LEVEL");
			}
			sb.append(" FROM ("+CommonConstants.VM_REPAIR_ITEM_PART+") A left join tm_part_stock ")
					.append(" BB on (BB.DEALER_CODE = A.DEALER_CODE and BB.part_No = A.part_no AND a.STORAGE_CODE =bb.STORAGE_CODE  ) ")
					.append("LEFT JOIN ("+CommonConstants.VM_PART_INFO+") C on (C.PART_NO = A.PART_NO AND C.DEALER_CODE = A.DEALER_CODE) ");
			if (flag) {
				sb.append(" LEFT JOIN TW_POS_INFIX_RELATION D ON A.DEALER_CODE = D.DEALER_CODE AND C.PART_INFIX ")
						.append(" = D.PART_INFIX AND D.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON E.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" AND A.DEALER_CODE = E.DEALER_CODE AND D.POS_CODE = E.POS_CODE ")
						.append(" LEFT JOIN  TW_WARNING_RULE W ON W.IS_VALID=").append(DictCodeConstants.IS_YES)
						.append(" AND A.DEALER_CODE = W.DEALER_CODE AND E.POS_CODE = W.POS_CODE AND W.WAR_TYPE = 20841001 ");
			}
			sb.append(" WHERE 1=1 ");
			Utility.sqlToEquals(sb, queryParam.get("labourCode"), "LABOUR_CODE", "A");
			Utility.sqlToEquals(sb, queryParam.get("modelLabourCode"), "MODEL_LABOUR_CODE", "A");
			Utility.sqlToEquals(sb, queryParam.get("entityCode"), "DEALER_CODE", "A");
			if (flag) {
				sb.append(
						" ) TEMP GROUP BY Part_No,Part_Name,Labour_Code,Part_Quantity,Storage_Code,Model_Labour_Code,")
						.append(" Part_Model_Group_Code_Set,Repair_I_P_Id,DEALER_CODE,UPDATED_BY,UPDATED_AT,CREATED_BY,CREATED_AT,")
						.append(" Ver,SALES_PRICE,STOCK_QUANTITY,LIMIT_PRICE,DOWN_TAG,PART_INFIX,POS_CODE,POS_NAME");
			}
		}
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	/**
	 * 根据项目代码获取最高预警等级
	 */
	public PageInfoDto getWarLevelByLabourCode(Map<String, String> queryParam){
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT MIN(WAR_LEVEL) AS WAR_LEVEL FROM(SELECT CASE WHEN COALESCE((SELECT DISTINCT COALESCE(CURR_WAR_COUNT,0) FROM ")
				.append(" TW_ROITEM_INFIX_RELATION A LEFT JOIN TW_WARRANTY_RECORD F ON A.DEALER_CODE = F.DEALER_CODE AND A.PART_INFIX = F.INFIX_CODE ")
				.append(" WHERE A.IS_VALID=").append(DictCodeConstants.IS_YES).append(" AND F.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND F.VIN = '")
				.append(queryParam.get("vin")).append("' AND A.LABOUR_CODE = '").append(queryParam.get("labourCode"))
				.append("' AND F.WAR_TYPE = ").append(DictCodeConstants.DICT_WARNING_CATEGORY_INFIX)
				.append(" AND (F.INFIX_CODE = W.INFIX_CODE ")
				.append(" OR (W.INFIX_CODE IS NULL OR W.INFIX_CODE = '') AND F.INFIX_CODE = I.PART_INFIX)),0)+1 ")
				.append(" >= COALESCE(W.WAR_COUNT,0) THEN W.WAR_LEVEL ELSE 99999999 END AS WAR_LEVEL FROM TW_WARNING_RULE W ")
				.append(" LEFT JOIN (SELECT * FROM TW_ROITEM_INFIX_RELATION TEMP WHERE TEMP.IS_VALID=")
				.append(DictCodeConstants.IS_YES).append(" AND TEMP.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND TEMP.LABOUR_CODE = '")
				.append(queryParam.get("labourCode"))
				.append("' AND NOT EXISTS (SELECT * FROM TW_WARNING_RULE WR WHERE WR.DEALER_CODE =TEMP.DEALER_CODE AND WR.IS_VALID= ")
				.append(DictCodeConstants.IS_YES)
				.append(" AND WR.INFIX_CODE = TEMP.PART_INFIX)) I ON W.DEALER_CODE = I.DEALER_CODE AND (W.INFIX_CODE = '' OR W.INFIX_CODE IS NULL)")
				.append(" WHERE W.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
				.append("' AND W.WAR_TYPE = ").append(DictCodeConstants.DICT_WARNING_CATEGORY_INFIX)
				.append(" AND W.IS_VALID = ").append(DictCodeConstants.IS_YES)
				.append(" AND (EXISTS(SELECT * FROM TW_ROITEM_INFIX_RELATION R WHERE R.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND R.LABOUR_CODE = '")
				.append(queryParam.get("labourCode")).append("'  AND R.PART_INFIX = W.INFIX_CODE AND R.IS_VALID=")
				.append(DictCodeConstants.IS_YES)
				.append(")  OR (W.INFIX_CODE = '' OR W.INFIX_CODE IS NULL) AND EXISTS(SELECT * FROM TW_ROITEM_INFIX_RELATION R WHERE R.IS_VALID= ")
				.append(DictCodeConstants.IS_YES).append(" AND R.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND R.LABOUR_CODE = '")
				.append(queryParam.get("labourCode"))
				.append("'  AND NOT EXISTS (SELECT * FROM TW_WARNING_RULE WR WHERE WR.IS_VALID=")
				.append(DictCodeConstants.IS_YES).append(" AND WR.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND WR.INFIX_CODE = R.PART_INFIX)))");
		if (!StringUtils.isNullOrEmpty(queryParam.get("milage"))) {
			sb.append(" AND ").append(queryParam.get("milage")).append(" <= W.LEGEL_MILAGE ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDate"))) {
			sb.append(" AND DAY(SYSDATE()) - DAY('").append(queryParam.get("salesDate")).append("') <= W.LEGAL_DAYS ");
		}
		sb.append(" UNION ALL SELECT CASE WHEN COALESCE((SELECT DISTINCT COALESCE(CURR_WAR_COUNT,0) FROM ")
				.append(" TW_ROITEM_INFIX_RELATION A LEFT JOIN TW_POS_INFIX_RELATION C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_INFIX = C.PART_INFIX AND C.IS_VALID= ")
				.append(DictCodeConstants.IS_YES)
				.append(" LEFT JOIN TW_WARRANTY_RECORD F ON A.DEALER_CODE = F.DEALER_CODE AND C.POS_CODE = F.POS_CODE ")
				.append(" WHERE A.IS_VALID=").append(DictCodeConstants.IS_YES).append(" AND F.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND F.VIN = '")
				.append(queryParam.get("vin")).append("' AND A.LABOUR_CODE = '").append(queryParam.get("labourCode"))
				.append("' AND F.WAR_TYPE = ").append(DictCodeConstants.DICT_WARNING_CATEGORY_POSITION)
				.append(" AND F.POS_CODE = W.POS_CODE),0)+1 ")
				.append(" >= COALESCE(W.WAR_COUNT,0) THEN W.WAR_LEVEL ELSE 99999999 END AS WAR_LEVEL FROM TW_WARNING_RULE W WHERE W.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND W.WAR_TYPE = ")
				.append(DictCodeConstants.DICT_WARNING_CATEGORY_POSITION).append(" AND W.IS_VALID = ")
				.append(DictCodeConstants.IS_YES)
				.append(" AND EXISTS(SELECT * FROM TW_ROITEM_INFIX_RELATION R WHERE R.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND R.LABOUR_CODE = '")
				.append(queryParam.get("labourCode")).append("' AND R.IS_VALID= ").append(DictCodeConstants.IS_YES)
				.append(" AND EXISTS (SELECT * FROM TW_POS_INFIX_RELATION P WHERE P.DEALER_CODE = R.DEALER_CODE ")
				.append(" AND P.POS_CODE = W.POS_CODE AND P.PART_INFIX = R.PART_INFIX AND P.IS_VALID = ")
				.append(DictCodeConstants.IS_YES).append("))");
		if (!StringUtils.isNullOrEmpty(queryParam.get("milage"))) {
			sb.append(" AND ").append(queryParam.get("milage")).append(" <= W.LEGEL_MILAGE ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDate"))) {
			sb.append(" AND DAY(SYSDATE()) - DAY('").append(queryParam.get("salesDate")).append("') <= W.LEGAL_DAYS ");
		}
		sb.append(")");
		return DAOUtil.pageQuery(sb.toString(), null);
	}
}
