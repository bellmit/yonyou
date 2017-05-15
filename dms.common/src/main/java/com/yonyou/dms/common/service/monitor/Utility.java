
/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.common
 *
 * @File name : Utility.java
 *
 * @Author : LiGaoqi
 *
 * @Date : 2017年2月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年2月8日    LiGaoqi    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.common.service.monitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;

import com.yonyou.dms.common.domains.PO.basedata.EntityRelationshipPO;
import com.yonyou.dms.common.domains.PO.basedata.TmEntityPrivateFieldPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;

/**
 * TODO description
 * @author LiGaoqi
 * @date 2017年2月8日
 */

public class Utility {
	private static HashMap<String, String> entityPrivateFieldMapping = null;
	// entityRelationshipMapping存放BROTHER_MODE模式的<CHILD_ENTITY+BIZ_CODE,PARENT_ENTITY>映射关系，用于子找父
	private static HashMap<String, String> entityRelationshipMappingForFindParent = null;

	public static boolean isPrivateField(String entityCode, String tableName, String fieldName)
	{
		if (entityCode == null || entityCode.equals("") || tableName == null || tableName.equals("") || fieldName == null || fieldName.equals(""))
			return false;

		if (entityPrivateFieldMapping == null)
		{
			entityPrivateFieldMapping = new HashMap();
			List<Object> relatList = new ArrayList<Object>();
			relatList.add(Integer.valueOf(DictCodeConstants.DICT_IS_YES));       
			List<TmEntityPrivateFieldPO> list = TmEntityPrivateFieldPO.findBySQL("select * from TM_ENTITY_PRIVATE_FIELD where IS_VALID= ? ",relatList.toArray());           

			TmEntityPrivateFieldPO po = new TmEntityPrivateFieldPO();

			if (list != null && list.size() > 0)
			{
				for (int i = 0; i < list.size(); i++)
				{
					po = (TmEntityPrivateFieldPO) list.get(i);
					entityPrivateFieldMapping.put(po.getString("DEALER_CODE") + po.getString("TABLE_NAME") + po.getString("PRIVATE_FIELD"), po.getInteger("IS_VALID").toString());
				}
			} 
		}

		if (entityPrivateFieldMapping.containsKey(entityCode + tableName + fieldName))
		{

			return true;
		} else
		{

			return false;
		}
	}

	/**
	 * 
	 * 功能描述：获取父ENTITY_CODE，只适用于BROTHER_MODE模式
	 * 
	 * @param conn
	 * @param entityCode
	 * @param bizCode
	 * @return
	 */
	public static String getGroupEntity(String entityCode, String bizCode)
	{
		if (entityCode == null || entityCode.trim().equals(""))
			return entityCode;
		if (entityRelationshipMappingForFindParent == null)
		{
			entityRelationshipMappingForFindParent = new HashMap();
			List<Object> relat1List = new ArrayList<Object>();
			relat1List.add("BROTHER_MODE");
			List<EntityRelationshipPO> list = EntityRelationshipPO.findBySQL("select * from TM_ENTITY_RELATIONSHIP where RELATIONSHIP_MODE= ? ",relat1List.toArray());           

			EntityRelationshipPO po = new EntityRelationshipPO();
			if (list != null && list.size() > 0)
			{
				for (int i = 0; i < list.size(); i++)
				{
					po =list.get(i);

					//update by xubufeng 2013-04-11 start
					/**
					 * 之前的action忽略了map的相同的key对应的value会被覆盖掉，导致数据put进去的会有问题。
					 */
					String tempKey = po.getString("PARENT_ENTITY")== null ? "" : po.getString("PARENT_ENTITY");

					if (!entityRelationshipMappingForFindParent.containsKey(po.getString("CHILD_ENTITY")+po.getString("BIZ_CODE"))
							|| !tempKey.equals(po.getString("CHILD_ENTITY")))
						entityRelationshipMappingForFindParent.put(po.getString("CHILD_ENTITY")+po.getString("BIZ_CODE"), po.getString("PARENT_ENTITY"));
					//update by xubufeng 2013-04-11 end 

				}
			}
		}

		if (entityRelationshipMappingForFindParent.containsKey(entityCode + bizCode))
		{
			String tmp = entityRelationshipMappingForFindParent.get(entityCode + bizCode);
			return tmp;
		} else
			return entityCode;
	}


	public static int createOrUpdate(TtPartMonthReportPO db, String dealerCode)throws Exception {
		StringBuffer sbSql = new StringBuffer();
		StringBuffer sbSqlCreate = new StringBuffer();
		StringBuffer sbSqlUpdate = new StringBuffer();
		String year = com.yonyou.dms.framework.util.Utility.getYear();
		String month = com.yonyou.dms.framework.util.Utility.getMonth();
		try
		{
			sbSql.append("SELECT DEALER_CODE FROM TT_PART_MONTH_REPORT ");
			sbSql.append(" WHERE  DEALER_CODE = '" + dealerCode
					+ "'  AND STORAGE_CODE=? AND PART_BATCH_NO=? AND PART_NO=? AND D_KEY = "
					+ CommonConstants.D_KEY + " AND REPORT_YEAR='" + year + "' AND REPORT_MONTH= '"
					+ month + "' FOR UPDATE ");

			sbSqlCreate
			.append(" INSERT INTO TT_PART_MONTH_REPORT"
					+ " (REPORT_YEAR, REPORT_MONTH,"
					+ " STORAGE_CODE,PART_BATCH_NO, PART_NO, PART_NAME, "
					+ " IN_QUANTITY, STOCK_IN_AMOUNT,"
					+ " INVENTORY_QUANTITY, INVENTORY_AMOUNT, OUT_QUANTITY,"
					+ " OUT_AMOUNT, CREATED_AT, CREATED_BY, DEALER_CODE,"
					+ " OPEN_QUANTITY, OPEN_PRICE, OPEN_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT"
					+ " )" + " VALUES (?, ?, " + " ?, ?, ?, ?," + " ?, ?, " + " ?, ?, ?,"
					+ " ?, ?, ?, ?," + " ?, ?, ?, ?, ?, ?" + " )");

			sbSqlUpdate
			.append(" UPDATE TT_PART_MONTH_REPORT"
					+ " SET IN_QUANTITY = CASE  WHEN IN_QUANTITY IS NULL THEN 0  ELSE IN_QUANTITY END + ?,"
					+ " STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT  END + ?,"
					+ " INVENTORY_QUANTITY = CASE  WHEN INVENTORY_QUANTITY IS NULL  THEN 0 ELSE INVENTORY_QUANTITY END + ?,"
					+ " INVENTORY_AMOUNT = CASE WHEN INVENTORY_AMOUNT IS NULL THEN 0 ELSE INVENTORY_AMOUNT END + ?,"
					+ " OUT_QUANTITY =  CASE  WHEN OUT_QUANTITY IS NULL  THEN 0  ELSE OUT_QUANTITY END + ?,"
					+ " OUT_AMOUNT =  CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT  END + ?,"
					+ " CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,"
					+ " CLOSE_PRICE = ?,"
					+ " CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-?,"
					+ " UPDATED_BY = ?," + " UPDATED_AT = ?" + " WHERE DEALER_CODE = ? "
					+ " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?"
					+ " AND STORAGE_CODE = ?" + " AND PART_BATCH_NO = ?"
					+ " AND PART_NO = ?" + " AND D_KEY = " + CommonConstants.D_KEY);

			if (db != null){
				if (db.get("In_Quantity") == null)
					db.set("In_Quantity",0f);
				if (db.get("Stock_In_Amount") == null)
					db.set("Stock_In_Amount",0d);
				if (db.get("Inventory_Quantity") == null)
					db.set("Inventory_Quantity",0f);
				if (db.get("Inventory_Amount") == null)
					db.set("Inventory_Amount",0d);
				if (db.get("Out_Quantity") == null)
					db.set("Out_Quantity",0f);
				if (db.get("Out_Amount") == null)
					db.set("Out_Amount",0d);
				if (db.get("Open_Amount") == null)
					db.set("Open_Amount",0d);
				if (db.get("Open_Price") == null)
					db.set("Open_Price",0d);
				if (db.get("Open_Quantity") == null)
					db.set("Open_Quantity",0f);
				if (db.get("Close_Amount") == null)
					db.set("Close_Amount",0d);
				if (db.get("Close_Price") == null)
					db.set("Close_Price",0d);
				if (db.get("Close_Quantity") == null)
					db.set("Close_Quantity",0f);
				//sbSql的？次序STORAGE_CODE,PART_NO
				List<Map> ps = Base.findAll(sbSql.toString(),
						db.getString("Storage_Code").trim(), db.getString("Part_Batch_No"),db.getString("Part_No"));
				if (ps != null) {
					for (Map map : ps) {
						//sbSqlUpdate的？次序
						Base.exec(sbSqlUpdate.toString(),
								Math.round(db.getFloat("In_Quantity") * 100 * 0.01),
								Math.round(db.getFloat("Stock_In_Amount") * 100 * 0.01),
								Math.round(db.getFloat("Inventory_Quantity") * 100 * 0.01),
								Math.round(db.getFloat("Inventory_Amount") * 100 * 0.01),
								Math.round(db.getFloat("Out_Quantity") * 100 * 0.01),
								Math.round(db.getFloat("Out_Amount") * 100 * 0.01),
								Math.round(db.getFloat("In_Quantity") * 100 * 0.01),
								Math.round(db.getFloat("Out_Quantity") * 100 * 0.01),
								Math.round(db.getFloat("Close_Price") * 10000 * 0.0001),
								Math.round(db.getFloat("Stock_In_Amount") * 100 * 0.01),
								Math.round(db.getFloat("Out_Amount") * 100 * 0.01),
								db.get("Updated_By"),
								new Date(),
								dealerCode,
								year,
								month,
								db.get("Storage_Code"),
								db.get("Part_Batch_No"),
								db.get("Part_No"));
					}
				} else {
					//sbSqlCreate的？次序
					Base.exec(sbSqlUpdate.toString(),
							year,
							month,
							db.get("Storage_Code"),
							db.get("Part_Batch_No"),
							db.get("Part_No"),
							db.get("Part_Name"),
							Math.round(db.getFloat("In_Quantity") * 100 * 0.01),
							Math.round(db.getFloat("Stock_In_Amount") * 100 * 0.01),
							Math.round(db.getFloat("Inventory_Quantity") * 100 * 0.01),
							Math.round(db.getFloat("Inventory_Amount") * 100 * 0.01),
							Math.round(db.getFloat("Out_Quantity") * 100 * 0.01),
							Math.round(db.getFloat("Out_Amount") * 100 * 0.01),
							new Date(),
							db.get("Created_By"),
							dealerCode,
							Math.round(db.getFloat("Open_Quantity") * 100 * 0.01),
							Math.round(db.getFloat("Open_Price") * 10000 * 0.0001),
							Math.round(db.getFloat("Open_Amount") * 100 * 0.01),
							getSubF(
									getAddF(db.getFloat("Open_Quantity"), db.getFloat("In_Quantity")),
									db.getFloat("Out_Quantity")),
							Math.round(db.getFloat("Close_Price") * 10000 * 0.0001),
							getSubD(getAddD(db.getDouble("Open_Amount"), db
									.getDouble("Stock_In_Amount")), db.getDouble("Out_Amount")));
				}
			}
			return -1;
		}
		catch (Exception err)
		{
			err.printStackTrace();
			throw err;
		}
	}

	private static Double getAddD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return com.yonyou.dms.framework.util.Utility.round(new Double(com.yonyou.dms.framework.util.Utility.add(s1, s2)).toString(), 2);
	}

	private static Double getSubD(Double v1, Double v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		return com.yonyou.dms.framework.util.Utility.round(new Double(com.yonyou.dms.framework.util.Utility.sub(s1, s2)).toString(), 2);
	}

	private static Double getAddF(Float v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = com.yonyou.dms.framework.util.Utility.round(new Double(com.yonyou.dms.framework.util.Utility.add(s1, s2)).toString(), 2);
		return d;
	}

	private static Double getSubF(Double v1, Float v2) {
		String s1 = "0";
		String s2 = "0";
		if (v1 != null)
			s1 = v1.toString();
		if (v2 != null)
			s2 = v2.toString();
		double d = com.yonyou.dms.framework.util.Utility.round(new Double(com.yonyou.dms.framework.util.Utility.sub(s1, s2)).toString(), 2);
		return d;
	}

	//配件月报表，九大类为查询条件
	public static List queryReportMonthByPartMainType(
			String dealerCode,
			String partMainType,
			String year,
			String month) throws Exception {
		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT B.PART_MAIN_TYPE,A.* FROM  TT_PART_MONTH_REPORT A LEFT JOIN VM_PART_INFO B ON A.DEALER_CODE=B.DEALER_CODE AND A.PART_NO=B.PART_NO "
				+ "WHERE A.REPORT_YEAR='"
				+ year
				+ "' AND A.REPORT_MONTH='"
				+ month
				+ "' AND A.DEALER_CODE='" + dealerCode + "' ");
		if(partMainType!=null && !"".equals(partMainType.trim())){
			sql.append(" AND B.PART_MAIN_TYPE="+partMainType+" ");
		}
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		return list;
	}


	public static boolean testIsNull(String src) {
		if (null == src || "".equals(src) || "null".equals(src))
			return true;
		return false;
	}

	public static boolean testIsNotNull(String src) {
		if (null != src && !"".equals(src) && !"null".equals(src))
			return true;
		return false;
	}

}
