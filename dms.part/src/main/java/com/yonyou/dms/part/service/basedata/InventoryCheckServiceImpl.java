/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPartInventoryPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryCheckDTO;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryItemDTO;
import com.yonyou.dms.part.domains.PO.basedata.PartInventoryItemPO;

/**
 * @author yangjie
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class InventoryCheckServiceImpl implements InventoryCheckService {

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto findAllInventoryInfo(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select A.DEALER_CODE,'' as SQL_CON ,'' as  SQL_TAG,'' as BEGIN_DATE_OUT ,'' as END_DATE_OUT ,'' as BEGIN_DATE_IN ,'' as  END_DATE_IN,'' as PART_MODEL_GROUP_CODE_SET ,A.REMARK, A.INVENTORY_NO, A.INVENTORY_DATE, A.PROFIT_AMOUNT, A.LOSS_COUNT, ")
				.append(" A.LOSS_AMOUNT,tu.EMPLOYEE_NAME as HANDLER, A.PROFIT_COUNT, A.IS_CONFIRMED, A.IS_FINISHED, A.PROFIT_TAG, ")
				.append(" A.LOSS_TAG,tua.USER_NAME as LOCK_USER  from  TT_PART_INVENTORY A LEFT JOIN TM_EMPLOYEE tu ON tu.DEALER_CODE = A.DEALER_CODE AND tu.EMPLOYEE_NO=A.HANDLER LEFT JOIN TM_USER tua ON tua.DEALER_CODE = A.DEALER_CODE AND tua.USER_ID=A.LOCK_USER" + " WHERE A.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ")
				.append(" AND D_KEY = ").append(CommonConstants.D_KEY).append(" ");
		if (!StringUtils.isNullOrEmpty(map.get("IS_CONFIRMED"))) {
			Utility.sqlToEquals(sb, map.get("IS_CONFIRMED"), "IS_CONFIRMED", "A");
		}else{
			sb.append(" AND A.IS_CONFIRMED = ").append(DictCodeConstants.IS_NOT);
		}
		Utility.sqlToLike(sb, map.get("INVENTORY_NO"), "INVENTORY_NO", "A");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public List<Map> findAllInventoryItemInfoById(String id) {
		// 核对是否加锁
		String lockerName = Utility.selLockerName("LOCK_USER", "tt_part_inventory", "INVENTORY_NO", id);
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNullOrEmpty(lockerName) || (!StringUtils.isNullOrEmpty(lockerName)
				&& lockerName.equals(FrameworkUtil.getLoginInfo().getUserId().toString()))) {// 表示锁定人为本人或未锁定
			if (StringUtils.isNullOrEmpty(lockerName)) {
				// 加锁
				Utility.updateByLocker("tt_part_inventory",
						FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", id, "LOCK_USER");
			}
			sb.append(
					"SELECT A.ITEM_ID,A.INVENTORY_NO,A.DEALER_CODE,A.STORAGE_CODE,ts.STORAGE_NAME,A.PART_BATCH_NO,A.PART_NO,A.PART_NAME,A.UNIT_CODE,A.CURRENT_STOCK,A.")
					.append("BORROW_QUANTITY,A.STORAGE_POSITION_CODE,A.LEND_QUANTITY,A.REAL_STOCK,A.CHECK_QUANTITY,")
					.append("COALESCE(A.PROFIT_LOSS_QUANTITY,0.00) AS PROFIT_LOSS_QUANTITY,A.COST_PRICE,A.")
					.append("PROFIT_LOSS_AMOUNT,A.D_KEY,A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,A.VER FROM Tt_Part_Inventory_Item A LEFT JOIN TM_STORAGE ts ON ts.DEALER_CODE=A.DEALER_CODE AND ts.STORAGE_CODE=A.STORAGE_CODE WHERE 1=1 ")
					.append(" AND A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
					.append("' And  A.Inventory_No = '").append(id).append("' ")
					.append(" order by A.PROFIT_LOSS_QUANTITY ASC ");
			return DAOUtil.findAll(sb.toString(), null);
		} else {
			throw new ServiceBizException("已被其他用户锁定");
		}
	}

	@Override
	public Map findinventoryFirst(String id) {
		String sql = "SELECT A.INVENTORY_NO,A.HANDLER,tu.EMPLOYEE_NAME as USER_NAME,A.INVENTORY_DATE,A.REMARK,A.DEALER_CODE FROM TT_PART_INVENTORY A LEFT JOIN TM_EMPLOYEE tu ON tu.DEALER_CODE=A.DEALER_CODE AND tu.EMPLOYEE_NO=A.HANDLER WHERE INVENTORY_NO = ?";
		List queryParam = new ArrayList();
		queryParam.add(id);
		return DAOUtil.findFirst(sql, queryParam);
	}

	@Override
	public PageInfoDto findPartInfo(Map<String, String> param) {
		StringBuffer sb = new StringBuffer();
		sb.append(
				" select A.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, A.PART_NAME, ")
				.append(" A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,")
				.append(" ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY, ")
				.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO, ")
				.append(" A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET, ")
				.append(" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - COALESCE(A.LEND_QUANTITY,0) ) AS USEABLE_QUANTITY, ")
				.append(" CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO ")
				.append(" AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN ").append(DictCodeConstants.IS_YES)
				.append(" ELSE ").append(DictCodeConstants.IS_NOT).append(" END  AS IS_MAINTAIN ")
				.append(" from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN (" + CommonConstants.VM_PART_INFO
						+ ") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) ")
				.append(" LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) ")
				.append(" LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE WHERE A.PART_STATUS<>")
				.append(DictCodeConstants.IS_YES).append(" AND A.DEALER_CODE = '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
		Utility.sqlToLike(sb, param.get("PART_MODEL_GROUP_CODE_SET"), "PART_MODEL_GROUP_CODE_SET", "C");
		sb.append(" AND C.D_KEY = ").append(CommonConstants.D_KEY);
		Utility.sqlToEquals(sb, param.get("STORAGE_CODE"), "STORAGE_CODE", "A");
		Utility.sqlToLike(sb, param.get("PART_NO"), "PART_NO", "A");
		Utility.sqlToLike(sb, param.get("PART_NAME"), "PART_NAME", "A");
		String defaultValue = Utility.getDefaultValue("5433");
		if (DictCodeConstants.DICT_IS_NO.equals(defaultValue)) {
			sb.append(" and TS.CJ_TAG=").append(DictCodeConstants.IS_YES);
		}
		Utility.sqlToEquals(sb, param.get("PART_GROUP_CODE"), "PART_GROUP_CODE", "A");
		Utility.sqlToLike(sb, param.get("STORAGE_POSITION_CODE"), "STORAGE_POSITION_CODE", "A");
		Utility.sqlToLike(sb, param.get("SPELL_CODE"), "SPELL_CODE", "A");
		// A.SALES_PRICE > 0,and (A.STOCK_QUANTITY + A.BORROW_QUANTITY -
		// A.LEND_QUANTITY -C.LOCKED_QUANTITY) > 0
		Utility.sqlToEquals(sb, param.get("BRAND"), "BRAND", "B");
		Utility.sqlToLike(sb, param.get("REMARK"), "REMARK", "A");
		sb.append(" AND ( 1=2 ");
		String storageByUserId = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId());
		if (!StringUtils.isNullOrEmpty(storageByUserId)) {
			sb.append(" OR A.STORAGE_CODE in (").append(storageByUserId).append(")");
		}
		sb.append(" ) ");
		return DAOUtil.pageQuery(sb.toString(), null);
	}

	@Override
	public String saveBlankInventoryNo() {
		String inventoryNo = commonNoService.getSystemOrderNo(CommonConstants.SRV_PJPDDH);// 获取盘点单号
		// 新增盘点单
		TtPartInventoryPO partInventoryPO = new TtPartInventoryPO();
		partInventoryPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		partInventoryPO.setString("INVENTORY_NO", inventoryNo);
		partInventoryPO.setString("HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
		partInventoryPO.set("INVENTORY_DATE", new Timestamp(System.currentTimeMillis()));
		partInventoryPO.setLong("IS_FINISHED", DictCodeConstants.IS_NOT);
		partInventoryPO.saveIt();
		return inventoryNo;
	}

	@Override
	public String addNewInventoryNo(InventoryCheckDTO dto) {
		String inventoryNo = "";
		// 查询配件盘点相关配件批次信息
		List<Map> list = findPartNoInfo(dto);
		if (list.size() > 0) {
			// 生成盘点单号
			inventoryNo = commonNoService.getSystemOrderNo(CommonConstants.SRV_PJPDDH);// 获取盘点单号
			// 新增盘点单
			TtPartInventoryPO partInventoryPO = new TtPartInventoryPO();
			partInventoryPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			partInventoryPO.setString("INVENTORY_NO", inventoryNo);
			partInventoryPO.setString("HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
			partInventoryPO.set("INVENTORY_DATE", new Timestamp(System.currentTimeMillis()));
			partInventoryPO.setLong("IS_FINISHED", DictCodeConstants.IS_NOT);
			partInventoryPO.setString("REMARK", dto.getRemark());
			partInventoryPO.saveIt();
			for (Map map : list) {
				PartInventoryItemPO inventoryItemPO = new PartInventoryItemPO();
				inventoryItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				inventoryItemPO.setString("INVENTORY_NO", inventoryNo);
				inventoryItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
				inventoryItemPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO"));
				inventoryItemPO.setString("PART_NO", map.get("PART_NO"));
				inventoryItemPO.setString("PART_NAME", map.get("PART_NAME"));
				inventoryItemPO.setString("UNIT_CODE", map.get("UNIT_CODE"));
				inventoryItemPO.setFloat("CURRENT_STOCK", map.get("STOCK_QUANTITY"));
				inventoryItemPO.setFloat("BORROW_QUANTITY", map.get("BORROW_QUANTITY"));
				inventoryItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
				inventoryItemPO.setFloat("LEND_QUANTITY", map.get("LEND_QUANTITY"));
				inventoryItemPO.setFloat("REAL_STOCK", map.get("REAL_STOCK"));
				inventoryItemPO.setDouble("COST_PRICE", map.get("COST_PRICE"));
				inventoryItemPO.setFloat("CHECK_QUANTITY", map.get("REAL_STOCK"));
				inventoryItemPO.saveIt();
			}
		}
		return inventoryNo;
	}

	/**
	 * 查询配件盘点相关配件批次信息
	 * 
	 * @param dto
	 * @return
	 */
	public List<Map> findPartNoInfo(InventoryCheckDTO dto) {
		StringBuffer str = new StringBuffer("( 1 = 0 ");
		String storageCode = "";
		for (Map map : dto.getDms_search()) {
			str.append(" or( ").append(" A.STORAGE_CODE='").append(objToStr(map.get("STORAGE_CODE")))
					.append("' and A.STORAGE_POSITION_CODE>='").append(objToStr(map.get("BEGIN")))
					.append("' and A.STORAGE_POSITION_CODE<='").append(objToStr(map.get("END"))).append("' ");
			if (!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))) {
				storageCode += "'" + map.get("STORAGE_CODE").toString() + "',";
			}
		}
		storageCode += "-1";
		str.append(") ");
		if (!StringUtils.isNullOrEmpty(dto.getAmount())) {
			if (DictCodeConstants.INVENTORY_GREATER == Integer.parseInt(dto.getAmount())) {// 大于零
				str.append(" and A.STOCK_QUANTITY > 0 ");
			} else if (DictCodeConstants.INVENTORY_EQUAL == Integer.parseInt(dto.getAmount())) {// 等于零
				str.append(" and A.STOCK_QUANTITY = 0 ");
			} else {
				str.append(" and A.STOCK_QUANTITY < 0 ");
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("select * from (select * from (SELECT A.DEALER_CODE, A.STORAGE_CODE, ")
				.append("COALESCE(B.STORAGE_POSITION_CODE,'') AS STORAGE_POSITION_CODE,")
				.append(" A.PART_NO,A.PART_NAME, A.STOCK_QUANTITY,").append(" (A.STOCK_QUANTITY + A.BORROW_QUANTITY - ")
				.append("COALESCE(A.LEND_QUANTITY,0)").append(" ) AS USEABLE_QUANTITY, A.PART_BATCH_NO, ")
				.append(" A.COST_PRICE,A.BORROW_QUANTITY,A.LEND_QUANTITY,A.UNIT_CODE ");
		if (!StringUtils.isNullOrEmpty(dto.getGroup().trim())) {
			sb.append(" ,PART_MODEL_GROUP_CODE_SET ");
		}
		sb.append(" FROM TM_PART_STOCK_ITEM A LEFT JOIN TM_PART_STOCK B ON ")
				.append("(A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO AND A.storage_code= B.storage_code and B.D_key = 0 and A.D_key = 0 ) ")
				.append(str.toString()).append(" AND A.DEALER_CODE= '")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  AND A.D_KEY = ")
				.append(CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(dto.getStop())) {
			if (dto.getStop().equals(DictCodeConstants.DICT_IS_YES)) {
				sb.append(" AND A.PART_STATUS = ").append(dto.getAmount());
			} else {
				sb.append(" AND (A.PART_STATUS = ").append(dto.getAmount()).append(" OR A.PART_STATUS IS NULL)");
			}
		}
		if (!StringUtils.isNullOrEmpty(dto.getGroup().trim())) {
			sb.append(" AND PART_MODEL_GROUP_CODE_SET like '%").append(dto.getGroup()).append("%' ");
		}
		sb.append(" AND (1=1 ");
		Utility.sqlToDate(sb, dto.getInBegin().toString(), dto.getInEnd().toString(), "LAST_STOCK_IN", "A");
		sb.append(" OR ");
		Utility.sqlToDate(sb, dto.getOutBegin().toString(), dto.getOutEnd().toString(), "LAST_STOCK_OUT", "A");
		sb.append(") ) a")
				.append(" left join(select storage_code as bstorage_code,part_no as bpart_no ,DEALER_CODE as bDEALER_CODE  from TT_PART_INVENTORY_ITEM where  DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(storageCode)) {
			sb.append(" AND STORAGE_CODE IN(").append(storageCode).append(") ");
		}
		if (!StringUtils.isNullOrEmpty(dto.getInventoryNo())) {
			sb.append(" AND inventory_no not in(").append(dto.getInventoryNo()).append(") ");
		}
		sb.append(" on a.part_no=b.bpart_no and a.storage_code=b.bstorage_code and a.DEALER_CODE=b.bDEALER_CODE )a ")
				.append(" where bstorage_code is null ");
		if (!StringUtils.isNullOrEmpty(dto.getSortBy())) {
			if (dto.getSortBy().equals("1")) {// 仓库加库位
				sb.append(" ORDER BY STORAGE_CODE,STORAGE_POSITION_CODE");
			} else if (dto.getSortBy().equals("2")) {// 仓库加配件代码
				sb.append(" ORDER BY STORAGE_CODE,BPART_NO");
			} else if (dto.getSortBy().equals("3")) {// 仓库加配件名称
				sb.append(" ORDER BY STORAGE_CODE,PART_NAME");
			} else {// 仓库加车型
				sb.append(" ORDER BY STORAGE_CODE ");
				if (!StringUtils.isNullOrEmpty(dto.getGroup()))
					sb.append(" ,PART_MODEL_GROUP_CODE_SET");
			}
		}
		return DAOUtil.findAll(sb.toString(), null);
	}

	private String objToStr(Object obj) {
		return StringUtils.isNullOrEmpty(obj) ? "" : obj.toString();
	}

	@Override
	public String saveInventoryInfo(InventoryItemDTO dto) {
		TtPartInventoryPO inventory = new TtPartInventoryPO();
		if (!StringUtils.isNullOrEmpty(dto.getInventoryNo())) {
			inventory = TtPartInventoryPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
					dto.getInventoryNo());
		}
		/**
		 * 根据IS_FINISHED判断是否做的盘点差异分析12781001是
		 * 如果盘点分离里没有报以和报损的配件，则把盘点单报以报损标志改为：12781001已经做过抱益抱损 在报以报损查询盘点单中就不差出该单据
		 * 如果盘点分析中只有盘亏配件，没有盘盈配件，则把报以标志改为已经做过报以12781001 只有盘盈则相反
		 * 通过盈亏数量判断该盘点单有没有报以或者报损的配件
		 */
		// String allFlag = "0";
		// String lossFlag = "0";// 0 12781001
		// String profitFlag = "0";// 1表示存在报以2表示不存在
		// if (inventory.getLong("IS_FINISHED") == DictCodeConstants.IS_YES) {
		// List<PartInventoryItemPO> list = PartInventoryItemPO.findBySQL(
		// "SELECT * FROM tt_part_inventory_item where DEALER_CODE = ? AND D_KEY
		// = ? AND INVENTORY_NO = ?",
		// FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY,
		// dto.getInventoryNo());
		// if (list.size() > 0) {
		// allFlag = "0";
		// int ac = 0;
		// int bc = 0;
		// for (PartInventoryItemPO itemPO : list) {
		// if (itemPO.getFloat("PROFIT_LOSS_QUANTITY") != null) {
		// if (itemPO.getFloat("PROFIT_LOSS_QUANTITY") > 0) {
		// // 存在报以
		// profitFlag = "1";// 12781002
		// ac = 1;
		// break;
		// }
		//
		// }
		// }
		// for (PartInventoryItemPO itemPO : list) {
		// if (itemPO.getFloat("PROFIT_LOSS_QUANTITY") != null) {
		// if (itemPO.getFloat("PROFIT_LOSS_QUANTITY") < 0) {
		// lossFlag = "1";
		// bc = 1;
		// break;
		// }
		// }
		// }
		// if (ac == 1 && bc == 1) {
		// allFlag = "1";
		// }
		// }
		// }
		String id = "";

		// 主表操作
		if (!StringUtils.isNullOrEmpty(dto.getInventoryNo())) {// 修改
			id = dto.getInventoryNo();
			inventory.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			inventory.setString("HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
			inventory.set("INVENTORY_DATE", new Timestamp(System.currentTimeMillis()));
			inventory.setLong("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId());
			inventory.saveIt();
		} else {// 新增
			id = commonNoService.getSystemOrderNo(CommonConstants.SRV_PJPDDH);// 获取盘点单号
			inventory.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
			inventory.setString("HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
			inventory.set("INVENTORY_DATE", new Timestamp(System.currentTimeMillis()));
			inventory.setString("INVENTORY_NO", id);
			inventory.setLong("IS_CONFIRMED", DictCodeConstants.IS_NOT);
			inventory.setLong("IS_FINISHED", DictCodeConstants.IS_NOT);
			inventory.setLong("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId());
			inventory.setString("REMARK", dto.getRemark());
			inventory.saveIt();
		}

		// 子表操作
		addOrUpdateItem(dto);
		return id;
	}

	/**
	 * 子表操作
	 * 
	 * @param dto
	 */
	public void addOrUpdateItem(InventoryItemDTO dto) {
		LazyList<PartInventoryItemPO> list = PartInventoryItemPO.find("DEALER_CODE = ? AND INVENTORY_NO = ?",
				FrameworkUtil.getLoginInfo().getDealerCode(), dto.getInventoryNo());
		if (list.size() > 0) {// 修改
			for (Map map : dto.getDms_details()) {
				PartInventoryItemPO inventoryItemPO = null;
				List<PartInventoryItemPO> list2 = PartInventoryItemPO.find(
						"DEALER_CODE = ? AND INVENTORY_NO = ? AND PART_NO = ?",
						FrameworkUtil.getLoginInfo().getDealerCode(), dto.getInventoryNo(),
						map.get("PART_NO").toString());
				if(list2.size()>0){// 修改
					inventoryItemPO = list2.get(0);
					inventoryItemPO.setFloat("CHECK_QUANTITY", map.get("CHECK_QUANTITY"));
					inventoryItemPO.setFloat("PROFIT_LOSS_QUANTITY", Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
							- Integer.parseInt(objToStr(map.get("REAL_STOCK"))));
					inventoryItemPO.setFloat("PROFIT_LOSS_AMOUNT",
							(Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
									- Float.parseFloat(objToStr(map.get("REAL_STOCK"))))
							* Float.parseFloat(objToStr(map.get("COST_PRICE"))));
					inventoryItemPO.saveIt();
				}else{// 新增
					inventoryItemPO = new PartInventoryItemPO();
					inventoryItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
					inventoryItemPO.setString("INVENTORY_NO", dto.getInventoryNo());
					inventoryItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
					inventoryItemPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO"));
					inventoryItemPO.setString("PART_NO", map.get("PART_NO"));
					inventoryItemPO.setString("PART_NAME", map.get("PART_NAME"));
					inventoryItemPO.setString("UNIT_CODE", map.get("UNIT_CODE"));
					inventoryItemPO.setFloat("CURRENT_STOCK", map.get("CURRENT_STOCK"));
					inventoryItemPO.setFloat("BORROW_QUANTITY", map.get("BORROW_QUANTITY"));
					inventoryItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
					inventoryItemPO.setFloat("LEND_QUANTITY", map.get("LEND_QUANTITY"));
					inventoryItemPO.setFloat("REAL_STOCK", map.get("REAL_STOCK"));
					inventoryItemPO.setDouble("COST_PRICE", map.get("COST_PRICE"));
					inventoryItemPO.setFloat("CHECK_QUANTITY", map.get("CHECK_QUANTITY"));
					inventoryItemPO.setFloat("PROFIT_LOSS_QUANTITY", Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
							- Integer.parseInt(objToStr(map.get("REAL_STOCK"))));
					inventoryItemPO.setFloat("PROFIT_LOSS_AMOUNT",
							(Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
									- Float.parseFloat(objToStr(map.get("REAL_STOCK"))))
							* Float.parseFloat(objToStr(map.get("COST_PRICE"))));
					inventoryItemPO.saveIt();
				}
			}
		} else {// 新增
			for (Map map : dto.getDms_details()) {
				PartInventoryItemPO inventoryItemPO = new PartInventoryItemPO();
				inventoryItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
				inventoryItemPO.setString("INVENTORY_NO", dto.getInventoryNo());
				inventoryItemPO.setString("STORAGE_CODE", map.get("STORAGE_CODE"));
				inventoryItemPO.setString("PART_BATCH_NO", map.get("PART_BATCH_NO"));
				inventoryItemPO.setString("PART_NO", map.get("PART_NO"));
				inventoryItemPO.setString("PART_NAME", map.get("PART_NAME"));
				inventoryItemPO.setString("UNIT_CODE", map.get("UNIT_CODE"));
				inventoryItemPO.setFloat("CURRENT_STOCK", map.get("CURRENT_STOCK"));
				inventoryItemPO.setFloat("BORROW_QUANTITY", map.get("BORROW_QUANTITY"));
				inventoryItemPO.setString("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
				inventoryItemPO.setFloat("LEND_QUANTITY", map.get("LEND_QUANTITY"));
				inventoryItemPO.setFloat("REAL_STOCK", map.get("REAL_STOCK"));
				inventoryItemPO.setDouble("COST_PRICE", map.get("COST_PRICE"));
				inventoryItemPO.setFloat("CHECK_QUANTITY", map.get("CHECK_QUANTITY"));
				inventoryItemPO.setFloat("PROFIT_LOSS_QUANTITY", Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
						- Integer.parseInt(objToStr(map.get("REAL_STOCK"))));
				inventoryItemPO.setFloat("PROFIT_LOSS_AMOUNT",
						(Float.parseFloat(objToStr(map.get("CHECK_QUANTITY")))
								- Float.parseFloat(objToStr(map.get("REAL_STOCK"))))
						* Float.parseFloat(objToStr(map.get("COST_PRICE"))));
				inventoryItemPO.saveIt();
			}
		}
	}

	/**
	 * 查询需要在数据库删除的PART_NO值
	 * 
	 * @param dto
	 * @return
	 */
	public List<String> findNotInDataBasePartNo(InventoryItemDTO dto) {
		String sql = "SELECT PART_NO,DEALER_CODE FROM TT_PART_INVENTORY_ITEM WHERE INVENTORY_NO = ?";
		List<Map> list = DAOUtil.findAll(sql, null);// 查询数据库中此入库单相关所有的VIN和DEALER_CODE
		List<String> para = new ArrayList<String>();// 用于保存数据库中此入库单相关所有的VIN
		List<String> data = new ArrayList<String>();// 用于保存前台此入库单相关所有的VIN
		List<String> result = new ArrayList<String>();// 用于保存数据库中的VIN而不存在于前台传过来的VIN
		for (Map sii : dto.getDms_details()) {
			if (sii.get("PART_NO") != null) {
				data.add(sii.get("PART_NO").toString());
			}
		}
		for (Map map : list) {
			if (map.get("PART_NO") != null) {
				para.add(map.get("PART_NO").toString());
			}
		}
		if (dto.getDms_details().size() > 0) {
			for (String str : para) {
				if (!data.contains(str)) {// 传到后台的VIN值存在数据库中而不存在前台,做删除操作的VIN
					result.add(str);
				}
			}
		} else {
			result = para;// 因为前台传过来的数据中没有数据,所以将数据库中此入库单明细的数据视为result里的项
		}
		return result;
	}

	@Override
	public String btnConfirm(InventoryItemDTO dto) {
		TtPartInventoryPO inventory = TtPartInventoryPO
				.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), dto.getInventoryNo());
		inventory.setString("HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
		inventory.set("INVENTORY_DATE", new Timestamp(System.currentTimeMillis()));
		inventory.setLong("LOCK_USER", FrameworkUtil.getLoginInfo().getUserId());
		inventory.setLong("IS_CONFIRMED", DictCodeConstants.IS_YES);
		inventory.saveIt();
		return null;
	}

	@Override
	public void btnDel(InventoryItemDTO dto) {
		PartInventoryItemPO.delete("DEALER_CODE = ? AND D_KEY = ? AND INVENTORY_NO = ?",
				FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY, dto.getInventoryNo());
		TtPartInventoryPO.delete("DEALER_CODE = ? AND D_KEY = ? AND INVENTORY_NO = ?",
				FrameworkUtil.getLoginInfo().getDealerCode(), CommonConstants.D_KEY, dto.getInventoryNo());
	}
}
