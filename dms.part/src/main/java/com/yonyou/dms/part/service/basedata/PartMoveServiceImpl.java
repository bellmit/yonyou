package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.AccountPeriodPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TmStoragePO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartBorrowItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartLendItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartPeriodReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtStockTransferItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtStockTransferPO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartAllocateOutItemPo;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.part.domains.DTO.basedata.PartItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartMoveStorageDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPartPeriodReportDTO;
import com.yonyou.dms.part.service.partOrder.StuffPriceAdjustService;

/**
 * @description 配件移库相关逻辑表达层
 * @author Administrator
 *
 */
@Service
public class PartMoveServiceImpl implements PartMoveService {

	final Logger logger = Logger.getLogger(PartMoveServiceImpl.class);

	@Autowired
	private CommonNoService commonNoService;
	
	@Autowired
	private StuffPriceAdjustService stuffPriceAdjustService;

	/**
	 * @description 根据条件查询移库订单申请
	 * @param transferNo
	 * @param transferDate
	 * @return PageInfoDto
	 */
	@Override
	public PageInfoDto getPartMoveInfos(String transferNo) {
		StringBuffer sql = new StringBuffer("SELECT TT_STOCK_TRANSFER.DEALER_CODE, TRANSFER_NO, tm_user.user_name AS HANDLER, TRANSFER_DATE, IS_FINISHED, FINISHED_DATE,"
				+ " IS_BATCH_TRANSFER, D_KEY, TT_STOCK_TRANSFER.CREATED_BY, TT_STOCK_TRANSFER.CREATED_AT, TT_STOCK_TRANSFER.UPDATED_BY,"
				+ " TT_STOCK_TRANSFER.UPDATED_AT, VER, USER1.USER_NAME LOCK_USER FROM TT_STOCK_TRANSFER LEFT JOIN tm_user ON tm_user.EMPLOYEE_NO = TT_STOCK_TRANSFER. HANDLER"
				+ " LEFT JOIN TM_USER USER1 ON USER1.USER_ID = TT_STOCK_TRANSFER.LOCK_USER"
				+" WHERE TT_STOCK_TRANSFER.D_KEY = "+CommonConstants.D_KEY+" AND TT_STOCK_TRANSFER.IS_FINISHED = "+CommonConstants.DICT_IS_NO);
		if(transferNo != null){
			sql.append(" and transfer_No like '%"+transferNo+"%'");
		}
		logger.debug(sql);
		return DAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * @description 根据配件移库单号查询移库配件详细
	 * @param transferNo
	 */
	@Override
	public PageInfoDto queryPartInfos(String transferNo) {
		//给表上锁，判断上锁是否成功,  如果上锁成功,则查询数据，供用户阅览，    如果上锁失败，则提示用户，上锁失败,并记录日志
		int result = Utility.updateByLocker("TT_STOCK_TRANSFER", FrameworkUtil.getLoginInfo().getUserId().toString(),"TRANSFER_NO", transferNo, "LOCK_USER");
		if(result > 0){
			StringBuffer sql = new StringBuffer("select A.ITEM_ID,tm_storage.STORAGE_NAME NEW_STORAGE_NAME,ts1.STORAGE_NAME OLD_STORAGE_NAME, A.DEALER_CODE, A.TRANSFER_NO, A.COST_PRICE, A.PART_BATCH_NO, A.PART_NO, A.PART_NAME, "
					+ "A.UNIT_CODE, A.OLD_STORAGE_CODE, A.NEW_STORAGE_CODE, A.OLD_STORAGEPOSITION_CODE, "
					+ "A.NEW_STORAGEPOSITION_CODE, A.COST_AMOUNT, A.TRANSFER_QUANTITY, A.D_KEY, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER,B.DOWN_TAG "
					+ "from TT_STOCK_TRANSFER_ITEM A LEFT JOIN TM_PART_INFO B ON A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO left join tm_storage on A.NEW_STORAGE_CODE = tm_storage.STORAGE_CODE"
					+ " left join tm_storage ts1 on A.OLD_STORAGE_CODE = ts1.STORAGE_CODE where A.DEALER_CODE='"
					+ FrameworkUtil.getLoginInfo().getDealerCode()
					+ "' and A.D_KEY=" + CommonConstants.D_KEY);
			if (transferNo != null && !"".equals(transferNo)){
				sql.append(" and A.TRANSFER_NO = '" + transferNo + "'");
			}
			logger.debug(sql.toString());
			return DAOUtil.pageQuery(sql.toString(), null);
		}else{
			logger.debug("TT_STOCK_TRANSFER 上锁失败,参数：transferNo="+transferNo+" userId="+FrameworkUtil.getLoginInfo().getUserId().toString());
			throw new ServiceBizException("上锁失败，请稍后重试");
		}
	}

	/**
	 * @description 根据条件查询配件库存
	 * @param conditionStr
	 * @return 
	 */
	@Override
	public PageInfoDto queryPartItem(PartItemDTO condition) {
		//判断页面是否选择筛选条件
		Boolean sign = false;
		if(condition != null){
			sign = true;
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		sql.append(" select A.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE,TM_STORAGE.STORAGE_NAME, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, A.PART_NAME, "
				+ " A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,"
				+ " ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY, "
				+ " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO, "		// add by sf 2010-12-17
				+ " A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET, "
				+ " (A.STOCK_QUANTITY + A.BORROW_QUANTITY - ifnull(A.LEND_QUANTITY, 0)"
				+ " ) AS USEABLE_QUANTITY, "
				+ " CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO " 
				+ " AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN "+CommonConstants.DICT_IS_YES
				+ " ELSE "+CommonConstants.DICT_IS_NO
				+ " END  AS IS_MAINTAIN "
				+ " from TM_PART_STOCK_ITEM  A LEFT JOIN TM_STORAGE ON TM_STORAGE.STORAGE_CODE = A.STORAGE_CODE  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) "
				+ " LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) "
				+ " LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE WHERE A.PART_STATUS<>"
				+ CommonConstants.DICT_IS_YES
				+ " AND A.DEALER_CODE = '"
				+ dealerCode
				+ "' AND C.D_KEY = "+CommonConstants.D_KEY);
		//根据额页面值进行sql语句拼接
		if(sign){
			if(condition.getPartModelGroupCodeSet() != null && !condition.getPartModelGroupCodeSet().isEmpty()){
				sql.append(" and C.PART_MODEL_GROUP_CODE_SET like '%"+condition.getPartModelGroupCodeSet()+"%'");
			}
			if(condition.getStorageCode() != null && !condition.getStorageCode().isEmpty()){
				sql.append(" and A.STORAGE_CODE = '" + condition.getStorageCode() + "'");
			}
			if(condition.getPartNo() != null && !condition.getPartNo().isEmpty()){
				sql.append(" and a.PART_NO like '%"+condition.getPartNo()+"%'");
			}
			if(condition.getPartName() != null && !condition.getPartName().isEmpty()){
				sql.append(" and a.PART_NAME like '%"+condition.getPartName()+"%'");
			}
			if(Utility.getDefaultValue("5433").equals("12781002")){
				sql.append(" and TS.CJ_TAG=12781001 ");
			}
			if(condition.getPartGroupCode() != null){
				sql.append(" and A.PART_GROUP_CODE = " + condition.getPartGroupCode());
			}
			if(condition.getStoragePositionCode() != null && !condition.getStoragePositionCode().isEmpty()){
				sql.append("and A.STORAGE_POSITION_CODE like '%"+condition.getStoragePositionCode()+"%'");
			}
			if(condition.getSpellCode() != null && !condition.getSpellCode().isEmpty()){
				sql.append(" and A.SPELL_CODE like '%"+condition.getSpellCode()+"%'");
			}
			if(condition.getPriceZero() != null && !condition.getPriceZero().isEmpty()){ 	//售价大于0
				sql.append(" and A.SALES_PRICE > 0");
			}
			if(condition.getStockZero() != null && !condition.getStockZero().isEmpty()){
				sql.append(" and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY -C.LOCKED_QUANTITY)  > 0 ");
			}
			if(condition.getBrand() != null && !condition.getBrand().isEmpty()){
				sql.append(" and B.BRAND =  '" + condition.getBrand() + "'  ");
			}
			if(condition.getDescription() != null && !condition.getDescription().isEmpty()){
				sql.append(" and A.REMARK like '%"+condition.getDescription()+"%'");
			}
		}
		String[] stoC = Utility.getStorageByUserId(userId).split(",");
		sql.append(" AND ( 1=2 ");
		for (int i = 0; i < stoC.length; i++)
		{
			if (stoC[i] != null && !"".equals(stoC[i].trim()))
			{
				sql.append(" OR A.STORAGE_CODE=" + stoC[i] + "  ");
			}
		}
		sql.append(" ) ");
		logger.debug(sql.toString());
		return DAOUtil.pageQuery(sql.toString(), null);
	}


	/**
	 * @description 查找配件的替换件
	 * @param partNo
	 */
	@Override
	public PageInfoDto queryPartReplace(String partNo) {
		PageInfoDto pageInfoDto = new PageInfoDto();
		//获取dealerCode,使用迭代查询所有的替换件
		String groupCode = Utility.getGroupEntity(FrameworkUtil.getLoginInfo().getDealerCode(), "TM_PART_INFO");
		TmPartInfoPO infoPO = TmPartInfoPO.findFirst("DEALER_CODE = ? and D_KEY = ? and PART_NO = ?", groupCode,CommonConstants.D_KEY,partNo);
		if(infoPO == null){
			pageInfoDto.setTotal(0L);
			return pageInfoDto;
		}
		String tpartNo = null;
		List list = new ArrayList();
		while(true){
			tpartNo = infoPO.getString("OPTION_NO");
			if (tpartNo == null || "".equals(tpartNo.trim())) {
				break;
			}
			TmPartInfoPO optionInfoPO = TmPartInfoPO.findFirst("DEALER_CODE = ? and D_KEY = ? and PART_NO = ?", groupCode,CommonConstants.D_KEY,tpartNo);
			if (optionInfoPO == null) {
				break;
			}
			list.add(tpartNo);
			if (infoPO.getString("PART_NO").equals(optionInfoPO.getString("OPTION_NO"))) {
				break;
			}
			if (partNo.equals(optionInfoPO.getString("OPTION_NO"))) {
				break;
			}
			if (list.size() > 20) {
				break;
			}
			infoPO = optionInfoPO;
		}
		List rlist = new ArrayList();
		for (int m = 0; m < list.size(); m++) {
			boolean dupl = false;
			for (int k = 0; k < rlist.size(); k++) {
				if (list.get(m).equals(rlist.get(k))) {
					dupl = true;
					break;
				}
			}
			if (!dupl) {
				rlist.add(list.get(m));
			}
		}
		List trueList = new ArrayList();
		if (rlist != null && rlist.size() > 0) {
			trueList = getReplaceParts(groupCode, rlist);
		}		
		pageInfoDto.setTotal((long)trueList.size());
		pageInfoDto.setRows(trueList);
		return pageInfoDto;
	}

	/**
	 * @description 查询配件替换件信息
	 * @param dealerCode
	 * @param partNo
	 * @return
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private List getReplaceParts(String dealerCode,List result) {
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("");
		String a = Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE));
		sql.append("SELECT A.NODE_PRICE,A.INSURANCE_PRICE,A.DEALER_CODE,A.PART_NO,A.STORAGE_CODE,TM_STORAGE.STORAGE_NAME,A.STORAGE_POSITION_CODE,"
				+"A.PART_NAME,A.SPELL_CODE,A.UNIT_CODE,A.STOCK_QUANTITY,A.SALES_PRICE,A.CLAIM_PRICE,B.LIMIT_PRICE,B.INSTRUCT_PRICE,"
				+"A.LATEST_PRICE,ROUND(A.COST_PRICE, 4) AS COST_PRICE,A.COST_AMOUNT,A.BORROW_QUANTITY,A.PART_STATUS,A.LEND_QUANTITY,"
				+"A.LAST_STOCK_IN,A.LAST_STOCK_OUT,A.REMARK,A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO,"
				+"A.VER,A.PART_GROUP_CODE"
				+ " FROM TM_PART_STOCK A LEFT JOIN TM_STORAGE ON TM_STORAGE.STORAGE_CODE = A.STORAGE_CODE  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B"
				+ " ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  ) "
				+ " LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE  c.part_status<>"+CommonConstants.DICT_IS_YES+"  AND  DEALER_CODE='"
				+ dealerCode
				+ "' AND D_KEY="
				+ CommonConstants.D_KEY
				+ " GROUP BY DEALER_CODE,PART_NO ) D "
				+ " ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )"
				+ "WHERE A.DEALER_CODE = '" + dealerCode + "' " + " AND A.D_KEY =  "
				+ CommonConstants.D_KEY+" and a.part_status<>"+CommonConstants.DICT_IS_YES  );
		sql.append(" AND ( ");
		for (int i = 0; i < result.size(); i++){
			sql.append(" A.PART_NO='" + result.get(i) + "' ");
			if (i < (result.size() - 1))
				sql.append(" OR ");
		}
		sql.append(" ) ");
		logger.debug(sql);
		return DAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * @description 查询旧仓库的配件能否移动到新仓库
	 */
	@Override
	public String checkStorageMove(String oldStorageCode, String newStorageCode) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String isCan = "12781001";
		//查询仓库类型
		logger.debug("from STOREPO from DEALER_CODE = "+dealerCode+" and STORAGE_CODE = " + oldStorageCode);
		TmStoragePO oldStorePo = TmStoragePO.findFirst("DEALER_CODE = ? and STORAGE_CODE = ?", dealerCode,oldStorageCode);
		logger.debug("from STOREPO from DEALER_CODE = "+dealerCode+" and STORAGE_CODE = " + newStorageCode);
		TmStoragePO newStorePo = TmStoragePO.findFirst("DEALER_CODE = ? and STORAGE_CODE = ?", dealerCode,newStorageCode);
		Integer outType = oldStorePo.getInteger("STORAGE_TYPE");
		Integer inType = newStorePo.getInteger("STORAGE_TYPE");
		if(outType==70041001){
			if(inType!=70041001){//配件是A类库  //入的不是A类库 测不能入
				isCan="12781002";
			}
		}else if(outType==70041002||outType==70041003){//如果原本是非A类库（不包括老仓库 老仓库都能入）
			if(inType==70041001){
				isCan="12781002";
			}
		}
		return isCan;
	}

	/**
	 * @description 查询配件扩展信息
	 * @param partNo
	 * @return 
	 */
	@Override
	public PageInfoDto queryPartItemInfos(String partNo,String storageCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TM_PART_INFO.DEALER_CODE,TM_PART_STOCK.SALES_PRICE,TM_PART_INFO.CLAIM_PRICE,TM_PART_STOCK.INSURANCE_PRICE,"
				+"TM_PART_INFO.LIMIT_PRICE,TM_PART_STOCK.MAX_STOCK,TM_PART_STOCK.MIN_STOCK,TM_PART_STOCK.STOCK_QUANTITY,"
				+"(TM_PART_STOCK.STOCK_QUANTITY + TM_PART_STOCK.BORROW_QUANTITY - TM_PART_STOCK.LEND_QUANTITY - TM_PART_STOCK.LOCKED_QUANTITY) AS USEABLE_STOCK,"
				+"TM_PART_STOCK.BORROW_QUANTITY,TM_PART_STOCK.LEND_QUANTITY,TM_PART_STOCK.LOCKED_QUANTITY,REPLACEPART.PART_NAME OPTION_NAME,REPLACESTOCK.STOCK_QUANTITY OPTION_STOCK,"
				+"TM_PART_INFO.PART_GROUP_CODE,TM_PART_INFO.UNIT_NAME,TM_PART_STOCK.PART_MODEL_GROUP_CODE_SET "
				+"FROM TM_PART_INFO LEFT JOIN TM_PART_STOCK ON (TM_PART_STOCK.PART_NO = TM_PART_INFO.PART_NO and TM_PART_STOCK.DEALER_CODE = TM_PART_INFO.DEALER_CODE)"
				+"LEFT JOIN TM_PART_INFO REPLACEPART ON (TM_PART_INFO.OPTION_NO = REPLACEPART.PART_NO and TM_PART_INFO.DEALER_CODE = REPLACEPART.DEALER_CODE)"
				+"LEFT JOIN TM_PART_STOCK REPLACESTOCK ON (REPLACEPART.PART_NO = REPLACESTOCK.PART_NO and REPLACESTOCK.DEALER_CODE = REPLACEPART.DEALER_CODE)"
				+"WHERE TM_PART_INFO.PART_NO = '"+partNo+"'"
				+"and TM_PART_INFO.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' "
				+"and tm_part_stock.STORAGE_CODE = '" + storageCode + "'");
		logger.debug(sql.toString());
		return DAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * @description 新增或更新移库单
	 * @param partMoveStorageDTO
	 * @return 
	 */
	@Override
	public String addPartItemMove(PartMoveStorageDTO partMoveStorageDTO) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String employeeNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		//对移库单做 saveOrUpdate操作
		if(!StringUtils.isNullOrEmpty(partMoveStorageDTO.getTransferNo())){
			TtStockTransferPO oldTtStockTransferPO = TtStockTransferPO.findFirst("DEALER_CODE = ? and D_KEY = ? and TRANSFER_NO = ?", dealerCode,CommonConstants.D_KEY,partMoveStorageDTO.getTransferNo());
			if(oldTtStockTransferPO == null){
				throw new ServiceBizException("数据过期，请刷新重试");
			}
			logger.debug("update TtStockTransferPO set HANDLER ="+employeeNo+",TRANSFER_DATE = "+com.yonyou.dms.common.Util.Utility.getSystemTimestamp()+",UPDATED_BY = "+employeeNo+",UPDATED_AT = "+com.yonyou.dms.common.Util.Utility.getSystemTimestamp() + " where DEALER_CODE = "+ dealerCode + " and D_KEY = " + CommonConstants.D_KEY + " and TRANSFER_NO= " + partMoveStorageDTO.getTransferNo());
			TtStockTransferPO.update("HANDLER = ?,TRANSFER_DATE = ?,UPDATED_BY = ?,UPDATED_AT = ?", 
					"DEALER_CODE = ? and D_KEY = ? and TRANSFER_NO = ?", 
					employeeNo,com.yonyou.dms.common.Util.Utility.getSystemTimestamp(),employeeNo,com.yonyou.dms.common.Util.Utility.getSystemTimestamp(),
					dealerCode,CommonConstants.D_KEY,partMoveStorageDTO.getTransferNo());
		}else{		//新增
			TtStockTransferPO ttStockTransferPO = new TtStockTransferPO();
			ttStockTransferPO.setString("DEALER_CODE", dealerCode);
			ttStockTransferPO.setString("TRANSFER_NO",commonNoService.getSystemOrderNo(CommonConstants.SRV_PJYKDH));
			ttStockTransferPO.setString("HANDLER",String.valueOf(employeeNo));
			ttStockTransferPO.set("TRANSFER_DATE", com.yonyou.dms.common.Util.Utility.getSystemTimestamp());
			ttStockTransferPO.setInteger("IS_FINISHED",CommonConstants.DICT_IS_NO);
			ttStockTransferPO.set("FINISHED_DATE", com.yonyou.dms.common.Util.Utility.getSystemTimestamp());
			ttStockTransferPO.setInteger("IS_BATCH_TRANSFER",CommonConstants.DICT_IS_NO); //
			
			ttStockTransferPO.saveIt();
			partMoveStorageDTO.setTransferNo(ttStockTransferPO.getString("TRANSFER_NO"));
		}
		//执行删除操作，获取前台删除的数据
		List<Map> jsonToList = JSONUtil.jsonToList(partMoveStorageDTO.getDelJsonStr(),Map.class);
		if(jsonToList != null){
			for (Map column : jsonToList) {
				TtStockTransferItemPO.delete("dealer_code = ? and d_key = ? and transfer_no = ? and part_no = ?", 
						dealerCode,CommonConstants.D_KEY,partMoveStorageDTO.getTransferNo(),column.get("PART_NO"));
			}
		}
		//新增值或修改后的值
		List<Map<String, Object>> partMoveItemTable = partMoveStorageDTO.getPartMoveItemTable();
		TtStockTransferItemPO ttStockTransferItemPO = null;
		if(partMoveItemTable != null && !partMoveItemTable.isEmpty()){
			for (Map<String,Object> object : partMoveItemTable) {
				if(object.containsKey("partSign") && object.get("partSign").toString().equals("A")){
					ttStockTransferItemPO = new TtStockTransferItemPO();
				}else{
					//问题1
					ttStockTransferItemPO = TtStockTransferItemPO.findFirst("dealer_code = ? and item_id = ? and part_no = ? and d_key = ?",
							dealerCode,object.get("ITEM_ID"),object.get("PART_NO"),CommonConstants.D_KEY);
					if(ttStockTransferItemPO == null){
						throw new ServiceBizException("数据过期，请刷新重试!");
					}
				}
				List<TmPartStockItemPO> partItemList = TmPartStockItemPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ?", 
						dealerCode,object.get("PART_NO"),object.get("OLD_STORAGE_CODE"));
				TmPartStockItemPO partItem = null;
				if(partItemList == null || partItemList.isEmpty()){
					throw new ServiceBizException("配件:"+object.get("PART_NAME")+",没有库存信息");
				}
				partItem = partItemList.get(0);
				ttStockTransferItemPO.setString("DEALER_CODE", dealerCode);
				ttStockTransferItemPO.setString("TRANSFER_NO", partMoveStorageDTO.getTransferNo());
				ttStockTransferItemPO.setDouble("COST_PRICE",object.get("COST_PRICE"));
				ttStockTransferItemPO.setString("PART_BATCH_NO",partItem.get("PART_BATCH_NO"));
				ttStockTransferItemPO.setString("PART_NO", object.get("PART_NO"));
				ttStockTransferItemPO.setString("PART_NAME", object.get("PART_NAME"));
				ttStockTransferItemPO.setString("UNIT_CODE", object.get("UNIT_CODE"));
				ttStockTransferItemPO.setString("OLD_STORAGE_CODE", object.get("OLD_STORAGE_CODE"));
				ttStockTransferItemPO.setString("OLD_STORAGEPOSITION_CODE", object.get("OLD_STORAGEPOSITION_CODE"));
				ttStockTransferItemPO.setString("NEW_STORAGE_CODE", object.get("NEW_STORAGE_CODE"));
				ttStockTransferItemPO.setString("NEW_STORAGEPOSITION_CODE", object.get("NEW_STORAGEPOSITION_CODE"));
				ttStockTransferItemPO.setString("TRANSFER_QUANTITY", object.get("TRANSFER_QUANTITY"));
				ttStockTransferItemPO.setString("COST_AMOUNT", object.get("COST_AMOUNT"));
				ttStockTransferItemPO.setInteger("D_KEY", CommonConstants.D_KEY);
				ttStockTransferItemPO.saveIt();
			}
		}
		return  partMoveStorageDTO.getTransferNo();
	}

	/**
	 * @description 删除移库单
	 * @param　transferNo
	 */
	@Override
	public String delPartItemMove(String transferNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		//删除移库单
		//判断主键和ENTITY_CODE是否为空
		if (StringUtils.isNullOrEmpty(transferNo) || dealerCode == null ){
			logger.debug("关键信息为空，请联系管理员");
			throw new ServiceBizException("关键信息为空，请联系管理员");
		}
		if (transferNo != null && !"".equals(transferNo.trim())) {
			// 先删子表信息
			TtStockTransferItemPO itemPO = new TtStockTransferItemPO();
			logger.debug("删除tt_stock_transfer_item数据：" + transferNo + " dealerCode:" +dealerCode);
			TtStockTransferItemPO.delete("d_key = ? and dealer_code = ? and transfer_no = ?", 
					CommonConstants.D_KEY,dealerCode,transferNo);
			logger.debug("删除tt_stock_transfer数据：" + transferNo + " dealerCode:" +dealerCode);
			TtStockTransferPO.delete("dealer_code = ? and d_key = ? and transfer_no = ?",
					dealerCode,CommonConstants.D_KEY,transferNo);
		}
		return "success";
	}

	/**
	 * @description 配件出库
	 * @param transferNo
	 * @return 
	 * @throws Exception 
	 */
	@Override
	public Map<String,Object> partOutStoragr(String transferNo) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		long userId = FrameworkUtil.getLoginInfo().getUserId();
		String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
		//判断主键和ENTITY_CODE是否为空
		if (StringUtils.isNullOrEmpty(transferNo) || StringUtils.isNullOrEmpty(dealerCode)){
			map.put("msg", "关键信息为空");
			map.put("result","error");
			return map;
		}
		//原仓库入账前后成本金额
		double costAmountBeforeA = 0;	//批次表入账前成本
		double costAmountBeforeB = 0;	//库存表入账前成本
		double costAmountAfterA = 0;	//批次表入账后成本
		double costAmountAfterB = 0;	//库存表入账后成本
		//新仓库入账前后成本金额
		double costAmountBeforeANew = 0;	//批次表入账前成本
		double costAmountBeforeBNew = 0;	//库存表入账前成本
		double costAmountAfterANew = 0;		//批次表入账后成本
		double costAmountAfterBNew = 0;		//库存表入账后成本



		/**
		 * update wanghui 2010.02.21 reason
		 * 相同账号分别调出同一张未入账的调拨入库单，点入账造成流水账中该入库单，有2条重复的流水账记录, 判断该入库单是否已经入账
		 * flag==12781002没有入账；反之已经入账
		 */
		String sheetNoType="TRANSFER_NO";
		String sheetType="TT_STOCK_TRANSFER";
		String flagP=checkIsFinished(transferNo, dealerCode, sheetType, sheetNoType);
		if (flagP.equals(CommonConstants.DICT_IS_YES)) {
			logger.debug("入库单号:" + transferNo + "已经入账，不能重复入账！");
			map.put("msg", "业务错误：入库单号" + transferNo + "已经入账，不能重复入账！");
			map.put("result","error");
			return map;
		}
		List<Map> finishedThisMonth = Utility.isFinishedThisMonth();
		if (finishedThisMonth == null || finishedThisMonth.isEmpty()){
			logger.error("业务错误：当前配件月报没有正确执行");
			map.put("msg", "业务错误：当前配件月报没有正确执行");
			map.put("result","error");
			return map;
		}	

		List<Map> list2 = Utility.getIsFinished();
		if (list2 == null 
				||list2.size()<=0 
				||(list2.get(0)==null
				||Integer.parseInt(list2.get(0).get("IS_EXECUTED").toString())!=Utility.getInt(CommonConstants.DICT_IS_YES))) {
			logger.error("业务错误：当前配件月结没有正确执行");
			map.put("msg", "业务错误：当前配件月结没有正确执行");
			map.put("result","error");
			return map;
		}

		if (transferNo != null && !"".equals(transferNo)){
			int update2 = TtStockTransferPO.update("UPDATED_BY = ? , UPDATED_AT = ? , IS_FINISHED = ? , FINISHED_DATE = ?", 
					"DEALER_CODE = ? AND D_KEY = ? AND TRANSFER_NO = ?",
					userId,com.yonyou.dms.common.Util.Utility.getSystemTimestamp(),CommonConstants.DICT_IS_YES,com.yonyou.dms.common.Util.Utility.getSystemTimestamp(),
					dealerCode,CommonConstants.D_KEY,transferNo);


			//1、根据移库单号查询移库明细信息
			LazyList<TtStockTransferItemPO> list = TtStockTransferItemPO.find("DEALER_CODE = ? and D_KEY = ? and TRANSFER_NO = ?", 
					dealerCode,CommonConstants.D_KEY,transferNo);
			String spellCode = "";//拼音代码
			int partGroupCode = 0;//配件类别
			double salesPrice = 0;//销售价
			double claimPrice = 0;//索赔价
			double limitPrice = 0;//销售限价
			double latestPrice = 0;//最新进货价
			String remark = "";//备注
			if (list != null && list.size() > 0){
				int count = list.size();
				TtStockTransferItemPO stockTransferItemPO = null;
				PartFlowPO flowPO = null;
				PartFlowPO flowPOIn = null;
				TmPartStockPO partStockPOC = null;
				List stockitemlist = new ArrayList();
				TmPartStockItemPO itemPO = null;
				TmPartStockItemPO itemPO2 = null;
				TmPartStockItemPO stockItemPO = null;
				TtStockTransferItemPO stockTransferItemPO3Con = null;
				TtStockTransferItemPO stockTransferItemPO3 = null;
				double oldcostPrice = 0;
				TmPartStockItemPO itemPOConn = null;
				TmPartStockItemPO itemPOOld = null;
				for (int i = 0; i < count; i++){
					double costPriceStock = 0;//库存成本单价
					double costAmountStock = 0;//库存成本金额
					float stockQuantity = 0;//库存数量
					float stockQuantityNew = 0;//入帐修改后的库存数量
					double costPriceNew = 0;//入帐修改后的库存成本单价					
					double itemCostAmount = 0;//业务单据领料出库成本金额
					double itemCostPrice = 0;//业务单句出库成本单价
					costAmountBeforeA = 0;	//批次表入账前成本
					costAmountBeforeB = 0;	//库存表入账前成本
					costAmountAfterA = 0;	//批次表入账后成本
					costAmountAfterB = 0;	//库存表入账后成本
					boolean isPl = false; //是否批量移库
					float boNum = 0;//借进数量
					float lendNum = 0;//借出数量
					float lockNum = 0;//锁定数量
					float stockNum = 0;//原库的库存数量
					stockTransferItemPO = new TtStockTransferItemPO();
					stockTransferItemPO = (TtStockTransferItemPO) list.get(i);
					/*
					 * 2009-03-27
					 * 如果新仓库中没有该配件,则新仓库的库存数量应为0,金额为0
					 */


					LazyList<TmPartStockPO> listitem = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ?", 
							dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"));
					float newStockQuantity;
					double newStockAmount;
					LazyList<TtStockTransferPO> listtst1 = TtStockTransferPO.find("DEALER_CODE = ? and TRANSFER_NO = ? and IS_BATCH_TRANSFER = ? and D_KEY = ?", 
							dealerCode,transferNo,CommonConstants.DICT_IS_YES,CommonConstants.D_KEY);
					// 如果是批量移库 将原本的只移可用库存改成平移把借进借出锁定来量一起移到对应的字段
					//获取对应字段的信息  change by ceg - 2015-5-20
					if (listtst1 != null && listtst1.size() > 0) {
						isPl=true;
						//查出该配件的 信息
						TmPartStockPO cStock = new TmPartStockPO();
						LazyList<TmPartStockPO> cList = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("OLD_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));
						if(cList!=null && cList.size()>0){
							cStock = (TmPartStockPO)cList.get(0);
							boNum = cStock.getFloat("BORROW_QUANTITY");
							lendNum = cStock.getFloat("LEND_QUANTITY");
							lockNum = cStock.getFloat("LOCKED_QUANTITY");
							stockNum = cStock.getFloat("STOCK_QUANTITY");
						}
					}
					//如果新仓库里面有则该配件信息
					TmPartStockPO stockPOaa = new TmPartStockPO();
					if (listitem != null && listitem.size() > 0){
						stockPOaa = listitem.get(0);
						newStockQuantity = stockPOaa.getFloat("STOCK_QUANTITY");
						newStockAmount=stockPOaa.getDouble("COST_AMOUNT");
					}else{
						newStockQuantity = 0;
						newStockAmount=0;
					}
					/* 结束 */

					LazyList<TmPartStockItemPO> partItemList = TmPartStockItemPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ?", 
							dealerCode,stockTransferItemPO.get("part_no"),stockTransferItemPO.get("OLD_STORAGE_CODE"));
					if (partItemList != null && partItemList.size() > 0){
						itemPOOld = (TmPartStockItemPO) partItemList.get(0);
						if (itemPOOld.getDouble("COST_PRICE") != null
								&& !"".equals(itemPOOld.getDouble("COST_PRICE")))
						{
							costPriceStock = itemPOOld.getDouble("COST_PRICE");//入账前原仓库成本单价
							itemCostPrice = costPriceStock;
						}
						if (itemPOOld.getDouble("COST_AMOUNT") != null && !"".equals(itemPOOld.getDouble("COST_AMOUNT").toString())){
							costAmountStock = itemPOOld.getDouble("COST_AMOUNT");//入帐前原仓库成本金额
							costAmountBeforeA = itemPOOld.getDouble("COST_AMOUNT");//批次表入帐前成本金额 
							itemCostAmount = costPriceStock
									* stockTransferItemPO.getDouble("TRANSFER_QUANTITY");//移库出库单据金额
							itemCostAmount = Utility.round(Double.toString(itemCostAmount), 2);
						}
						if (itemPOOld.getFloat("STOCK_QUANTITY") != null
								&& !"".equals(itemPOOld.getFloat("STOCK_QUANTITY").toString()))
						{
							stockQuantity = itemPOOld.getFloat("STOCK_QUANTITY");//入账前原仓库库存数量
						}
						stockTransferItemPO.set("PART_BATCH_NO",itemPOOld.get("PART_BATCH_NO"));
					}
					List stocknow = TmPartStockPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ?", 
							dealerCode,itemPOOld.getString("Part_No"),itemPOOld.get("STORAGE_CODE"),CommonConstants.D_KEY);
					if (stocknow != null && stocknow.size() > 0){
						TmPartStockPO stockPO = (TmPartStockPO)stocknow.get(0);
						if (stockPO.getFloat("COST_AMOUNT") != null && !"".equals(stockPO.getFloat("COST_AMOUNT").toString())){
							costAmountBeforeB = stockPO.getFloat("COST_AMOUNT"); //库存表入账前成本金额
						}

					}
					stockTransferItemPO3Con = new TtStockTransferItemPO();
					stockTransferItemPO3Con.set("Item_Id",stockTransferItemPO.get("Item_Id"));
					stockTransferItemPO3Con.set("dealer_Code",dealerCode);
					stockTransferItemPO3Con.set("D_Key",CommonConstants.D_KEY);
					stockTransferItemPO3 = new TtStockTransferItemPO();
					stockTransferItemPO3.set("Cost_Price",itemCostPrice);
					stockTransferItemPO3.set("Cost_Amount",itemCostAmount);
					stockTransferItemPO3.set("Updated_By",userId);
					stockTransferItemPO3.set("Updated_at",Utility.getCurrentDateTime());
					//修改业务单居明细表中成本单价成本金额
					if (stockTransferItemPO3Con.getString("DEALER_CODE")==null || stockTransferItemPO3Con.getInteger("ITEM_ID").toString()==null){
						throw new ServiceBizException("数据异常：关键数据为空");

					}
					TtStockTransferItemPO.update("COST_PRICE = ?,COST_AMOUNT = ?,UPDATED_BY = ?,UPDATED_AT = ? ", 
							"ITEM_ID = ? and DEALER_CODE = ? and D_KEY = ?",
							itemCostPrice,itemCostAmount,userId,Utility.getCurrentDateTime(),
							stockTransferItemPO.get("ITEM_ID"),dealerCode,CommonConstants.D_KEY);


					//更新(原仓库)配件库存批次表和配件库存表里库存数量成本单价成本金额
					itemPO = new TmPartStockItemPO();
					itemPO.set("PART_NO",stockTransferItemPO.getString("PART_NO"));
					if (stockTransferItemPO.get("PART_BATCH_NO") != null && !"".equals(stockTransferItemPO.get("PART_BATCH_NO"))){
						itemPO.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
					}
					else{
						itemPO.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
					}
					itemPO.set("COST_AMOUNT",-itemCostAmount);
					if(isPl){
						itemPO.set("STOCK_QUANTITY",-stockNum);
					}else{
						itemPO.set("STOCK_QUANTITY",-stockTransferItemPO.getFloat("TRANSFER_QUANTITY"));							
					}
					itemPO.set("STORAGE_CODE",stockTransferItemPO.get("OLD_STORAGE_CODE"));
					int record = calCostPrice1(dealerCode, itemPO,userId);
					if (record == 0){
						throw new ServiceBizException("系统异常：更新失败");
					}
					List listStockAfter = TmPartStockPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ?", 
							dealerCode,itemPOOld.get("PART_NO"),itemPOOld.get("STORAGE_CODE"),CommonConstants.D_KEY);
					if (listStockAfter != null && listStockAfter.size() > 0){
						TmPartStockPO StockAfter = (TmPartStockPO)listStockAfter.get(0);
						if (StockAfter.getDouble("COST_AMOUNT") != null && !"".equals(StockAfter.getDouble("COST_AMOUNT").toString())){
							costAmountAfterB = StockAfter.getDouble("COST_AMOUNT"); //库存表入账后成本金额
						}

					}
					List listItemAfter = TmPartStockItemPO.find("DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ?",
							dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("part_no"),stockTransferItemPO.get("OLD_STORAGE_CODE"));
					if (listItemAfter != null && listItemAfter.size() > 0){
						TmPartStockItemPO itemAfter = (TmPartStockItemPO) listItemAfter.get(0);
						if (itemAfter.getDouble("COST_AMOUNT") != null && !"".equals(itemAfter.getDouble("COST_AMOUNT").toString()))
						{
							costAmountAfterA = itemAfter.getDouble("COST_AMOUNT");	//批次表入账后成本金额
						}
					}

					//1.向流水账增加记录从原仓库移出
					double amount = Utility.add("1", Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE)));
					String rate = Double.toString(amount);
					flowPO = new PartFlowPO();
					flowPO.set("DEALER_CODE",dealerCode);
					flowPO.set("STORAGE_CODE",stockTransferItemPO.get("OLD_STORAGE_CODE"));
					flowPO.set("PART_NO",stockTransferItemPO.get("PART_NO"));
					flowPO.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
					flowPO.set("PART_NAME",stockTransferItemPO.get("PART_NAME"));
					flowPO.set("SHEET_NO",stockTransferItemPO.get("TRANSFER_NO"));
					flowPO.set("IN_OUT_TYPE",Utility.getInt(CommonConstants.DICT_IN_OUT_TYPE_PART_MOVE_OUT));//出入库类型
					flowPO.set("IN_OUT_TAG",Utility.getInt(CommonConstants.DICT_IS_YES));//是否出库
					flowPO.set("STOCK_OUT_QUANTITY",stockTransferItemPO.get("Transfer_Quantity"));
					flowPO.set("COST_PRICE",costPriceStock);
					flowPO.set("COST_AMOUNT",itemCostAmount);
					List liststock = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ?", 
							dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("OLD_STORAGE_CODE"));
					if (liststock != null && liststock.size() > 0)
					{
						partStockPOC = new TmPartStockPO();
						partStockPOC = (TmPartStockPO) liststock.get(0);
						stockQuantityNew = partStockPOC.getFloat("STOCK_QUANTITY");//入账后原仓库数量
						costPriceNew = partStockPOC.getFloat("COST_PRICE");//入帐后原仓库成本单价
						flowPO.set("STOCK_QUANTITY",stockQuantityNew);
					}
					flowPO.set("OPERATE_DATE",com.yonyou.dms.common.Util.Utility.getSystemTimestamp());
					flowPO.set("OPERATOR",empNo);
					flowPO.set("CREATED_BY",userId);
//					flowPO.set("CREATED_AT",Utility.getCurrentDateTime());
					flowPO.set("IN_OUT_NET_PRICE",stockTransferItemPO.getFloat("COST_PRICE"));//出入库不含税单价
					flowPO.set("IN_OUT_NET_AMOUNT",stockTransferItemPO.getFloat("COST_AMOUNT"));//出入库不含税金额
					String costPrice = StringUtils.isNullOrEmpty(stockTransferItemPO.get("COST_PRICE"))?"0":stockTransferItemPO.get("COST_PRICE").toString();
					flowPO.set("IN_OUT_TAXED_PRICE",Utility.mul(costPrice, String.valueOf(rate)));//出入库含税单价
					String costAmount = StringUtils.isNullOrEmpty(stockTransferItemPO.get("COST_AMOUNT"))?"0":stockTransferItemPO.get("COST_AMOUNT").toString();
					flowPO.set("IN_OUT_TAXED_AMOUNT",Utility.mul(costAmount, String.valueOf(rate)));//出入库含税金额
					flowPO.set("COST_AMOUNT_BEFORE_A",costAmountBeforeA);
					flowPO.set("COST_AMOUNT_BEFORE_B",costAmountBeforeB);
					flowPO.set("COST_AMOUNT_AFTER_A",costAmountAfterA);
					flowPO.set("COST_AMOUNT_AFTER_B",costAmountAfterB);
					flowPO.saveIt();

					//向流水帐增加记录移到新仓库
					flowPOIn = new PartFlowPO();
					flowPOIn.set("DEALER_CODE",dealerCode);
					flowPOIn.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));
					flowPOIn.set("PART_NO",stockTransferItemPO.get("PART_NO"));
					flowPOIn.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
					flowPOIn.set("PART_NAME",stockTransferItemPO.get("PART_NAME"));
					flowPOIn.set("SHEET_NO",stockTransferItemPO.get("TRANSFER_NO"));
					flowPOIn.set("IN_OUT_TYPE",Utility
							.getInt(CommonConstants.DICT_IN_OUT_TYPE_PART_MOVE_IN));
					flowPOIn.set("IN_OUT_TAG",Utility.getInt(CommonConstants.DICT_IS_NO));
					flowPOIn.set("STOCK_IN_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));

					//						flowPOIn.setStockQuantity(stockTransferItemPO.getTransferQuantity());//库存数量 

					flowPOIn.set("OPERATE_DATE",com.yonyou.dms.common.Util.Utility.getSystemTimestamp());
					flowPOIn.set("OPERATOR",empNo);
					flowPOIn.set("CREATED_BY",userId);
//					flowPOIn.set("CREATED_AT",Utility.getCurrentDateTime());
					flowPOIn.set("IN_OUT_NET_PRICE",stockTransferItemPO.getFloat("COST_PRICE"));//出入库不含税单价
					flowPOIn.set("IN_OUT_NET_AMOUNT",stockTransferItemPO.get("COST_AMOUNT"));//出入库不含税金额
					flowPOIn.set("IN_OUT_TAXED_PRICE",Utility.mul(costPrice, String.valueOf(rate)));//出入库含税单价
					flowPOIn.set("IN_OUT_TAXED_AMOUNT",Utility.mul(costAmount, String.valueOf(rate)));//出入库含税金额
					flowPOIn.set("COST_PRICE",itemCostPrice);
					flowPOIn.set("COST_AMOUNT",itemCostAmount);

					//3.根据移库明细表里 配件代码 新仓库代码 进货批号 查询 目标仓库是否有此配件
					TmPartStockItemPO.find("DEALER_CODE = ? and D_KEY = ? and PART_BATCH_NO = ? and PART_NO = ? and STORAGE_CODE = ?", 
							dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_BATCH_NO"),stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"));
					//新仓库中有要转移的配件
					if (stockitemlist != null && stockitemlist.size() > 0){
						costAmountBeforeANew = 0;	//批次表入账前成本
						costAmountBeforeBNew = 0;	//库存表入账前成本
						costAmountAfterANew = 0;	//批次表入账后成本
						costAmountAfterBNew = 0;	//库存表入账后成本
						float newBoNum = 0;
						float newLendNum = 0;
						float newLockNum = 0;
						float newStockNum = 0;

						TmPartStockItemPO itemPOb = null;
						itemPOb = (TmPartStockItemPO) stockitemlist.get(0);
						if (itemPOb.getFloat("COST_AMOUNT") != null
								&& !"".equals(itemPOb.getFloat("COST_AMOUNT").toString()))
						{
							costAmountBeforeANew = itemPOb.getFloat("COST_AMOUNT");//批次表入帐前成本金额 
						}
						List stockNew = TmPartStockPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ?", 
								dealerCode,stockTransferItemPO.get("Part_No"),stockTransferItemPO.get("NEW_STORAGE_CODE"),CommonConstants.D_KEY);
						if (stockNew != null && stockNew.size() > 0)
						{
							TmPartStockPO stockPO = (TmPartStockPO)stockNew.get(0);
							if (stockPO.getFloat("COST_AMOUNT") != null && !"".equals(stockPO.getFloat("COST_AMOUNT").toString())){
								costAmountBeforeBNew = stockPO.getFloat("COST_AMOUNT"); //库存表入账前成本金额
							}

						}

						//							TmPartStockItemPO itemPO3a = new TmPartStockItemPO();
						//							itemPO3a.setEntityCode(dealerCode);
						//							itemPO3a.setDKey(CommonConstant.D_KEY);
						//							itemPO3a.setPartNo(stockTransferItemPO.get("Part_No"));
						//							itemPO3a.setStorageCode(stockTransferItemPO.getNewStorageCode());
						if (stockTransferItemPO.get("PART_BATCH_NO") != null && !"".equals(stockTransferItemPO.get("Part_Batch_No"))){
							//								itemPO3a.set("Part_Batch_No",stockTransferItemPO.get("Part_Batch_No"));
						}else{
							stockItemPO.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
						}
						//更新(目标仓库)配件库存批次表和配件库存表中库存数量成本单价成本金额
						itemPO2 = new TmPartStockItemPO();                
						itemPO2.set("PART_NO",stockTransferItemPO.get("PART_NO"));
						if (stockTransferItemPO.get("PART_BATCH_NO") != null
								&& !"".equals(stockTransferItemPO.get("PART_BATCH_NO")))
						{
							itemPO2.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
						}
						else
						{
							itemPO2.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
						}
						if(isPl){//如果是批量移库
							itemPO2.set("STOCK_QUANTITY",stockNum);
						}else{
							itemPO2.set("STOCK_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));								
						}
						itemPO2.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));
						itemPO2.set("LATEST_PRICE",itemCostPrice);
						itemPO2.set("COST_AMOUNT",itemCostAmount);
						itemPO2.set("COST_PRICE",itemCostPrice);
						calCostPrice1(dealerCode, itemPO2,userId);
						List listStockAfterNew = TmPartStockPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ?", 
								dealerCode,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"),CommonConstants.D_KEY);
						if (listStockAfterNew != null && listStockAfterNew.size() > 0)
						{
							TmPartStockPO StockAfter = (TmPartStockPO)listStockAfterNew.get(0);
							if (StockAfter.get("COST_AMOUNT") != null && !"".equals(StockAfter.get("COST_AMOUNT").toString())){
								costAmountAfterBNew = StockAfter.getDouble("COST_AMOUNT"); //库存表入账后成本金额
							}

						}

						List listItemAfterNew = TmPartStockItemPO.find("DEALER_CODE = ? and D_KEY = ? and PART_BATCH_NO = ? and PART_NO = ? and STORAGE_CODE = ?",
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_BATCH_NO"),stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"));
						if (listItemAfterNew != null && listItemAfterNew.size() > 0){
							TmPartStockItemPO itemAfter = (TmPartStockItemPO) listItemAfterNew.get(0);
							if (itemAfter.getFloat("COST_AMOUNT") != null && !"".equals(itemAfter.getFloat("COST_AMOUNT").toString()))
							{
								costAmountAfterANew = itemAfter.getFloat("COST_AMOUNT");	//批次表入账后成本金额
							}
						}
						//如果新仓库中有要转移的配件 但配件的库位信息 车型组 备注等信息为空而原仓库中配件的这些信息是有的
						//则进行更新  by ceg 2015-04-30
						List oList = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("Old_Storage_Code"),stockTransferItemPO.get("PART_NO"));
						List nList = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?",
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("New_Storage_Code"),stockTransferItemPO.get("PART_NO"));
						StringBuffer nStockUpdate = new StringBuffer();

						StringBuffer oStockItem = new StringBuffer();
						oStockItem.append("DEALER_CODE = "+dealerCode)
						.append(" and D_KEY = " + CommonConstants.D_KEY)
						.append(" and PART_NO = " + stockTransferItemPO.get("PART_NO"))
						.append(" and STORAGE_CODE = " + stockTransferItemPO.get("NEW_STORAGE_CODE"));
						if (stockTransferItemPO.get("PART_BATCH_NO") != null
								&& !"".equals(stockTransferItemPO.get("PART_BATCH_NO"))){
							oStockItem.append(" and PART_BATCH_NO" +stockTransferItemPO.get("PART_BATCH_NO"));
						}else{
							oStockItem.append(" and PART_BATCH_NO" + CommonConstants.defaultBatchName);
						}
						boolean isVal=false;
						TmPartStockPO oStock;
						TmPartStockPO nStock;
						if(oList!=null && oList.size()>0&&nList!=null &&nList.size()>0){
							oStock=(TmPartStockPO)oList.get(0);
							nStock = (TmPartStockPO)nList.get(0);
							//当旧库中配件有库位新库中有配件但没库位的讲原库位带过来
							//获取新库中的 借入借出锁定量
							newStockNum = nStock.getFloat("STOCK_QUANTITY");
							newBoNum = nStock.getFloat("BORROW_QUANTITY");
							newLendNum = nStock.getFloat("LEND_QUANTITY");
							newLockNum = nStock.getFloat("LOCKED_QUANTITY");
							if((!StringUtils.isNullOrEmpty(oStock.get("STORAGE_POSITION_CODE"))||
									!StringUtils.isNullOrEmpty(stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE")))&&
									StringUtils.isNullOrEmpty(nStock.get("STORAGE_POSITION_CODE"))){
								if(!StringUtils.isNullOrEmpty(stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"))){
									nStockUpdate.append("STORAGE_POSITION_CODE = " + stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"));
									nStockUpdate.append(" , STORAGE_POSITION_CODE = " + stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"));
								}else{
									nStockUpdate.append(", STORAGE_POSITION_CODE = " + oStock.get("STORAGE_POSITION_CODE"));
									//更新库存明细中的库位
									TmPartStockItemPO.update("STORAGE_POSITION_CODE = ?", 
											oStockItem.toString(),
											oStock.get("STORAGE_POSITION_CODE"));
								}
							}
							//车型组
							if(!StringUtils.isNullOrEmpty(oStock.get("PART_MODEL_GROUP_CODE_SET"))&&
									StringUtils.isNullOrEmpty(nStock.get("PART_MODEL_GROUP_CODE_SET"))){
								nStockUpdate.append(" ,PART_MODEL_GROUP_CODE_SET = " + oStock.get("PART_MODEL_GROUP_CODE_SET"));
								isVal= true;
							}
							//备注
							if(!StringUtils.isNullOrEmpty(oStock.get("REMARK"))&&
									StringUtils.isNullOrEmpty(nStock.get("REMARK"))){
								nStockUpdate.append(",REMARK = " + oStock.get("REMARK"));
								isVal= true;
							}
							if(isPl){
								//nStockUpdate.setStockQuantity(stockNum+newStockNum); 前面的封装的方法同时把库存和库存明细表都更新了
								nStockUpdate.append(",BORROW_QUANTITY = " + (boNum+newBoNum));
								nStockUpdate.append(",LEND_QUANTITY = " + (lendNum+newLendNum));
								nStockUpdate.append(",LOCKED_QUANTITY = " + (lockNum+newLockNum));
								nStockUpdate.append(",UPDATED_BY = " + userId);
								nStockUpdate.append(",UPDATED_AT = " + Utility.getCurrentDateTime());

								TmPartStockPO.update("",
										"DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
										dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("NEW_STORAGE_CODE"));	
								//更新新库库存明细中的借入借出数量
								TmPartStockItemPO.update("BORROW_QUANTITY = ?,LEND_QUANTITY = ?", oStockItem.toString(), 
										boNum+newBoNum,lendNum+newLendNum);
								//将原仓库库存信息中借进借出锁定设为0
								TmPartStockPO.update("STOCK_QUANTITY = 0,BORROW_QUANTITY = 0,LEND_QUANTITY = 0,LOCKED_QUANTITY = 0,UPDATED_BY = ?,UPDATED_AT = ?", 
										"DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
										userId,com.yonyou.dms.common.Util.Utility.getSystemTimestamp(),dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("OLD_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));

								//将原库中库存明细中借进借出设置为0
								StringBuffer oldItem = new StringBuffer();
								oldItem.append("DEALER_CODE = " + dealerCode)
								.append(" and D_KEY = " + CommonConstants.D_KEY)
								.append(" and PART_NO = " + stockTransferItemPO.get("PART_NO"))
								.append(" and STORAGE_CODE = " + stockTransferItemPO.get("OLD_STORAGE_CODE"));

								if (stockTransferItemPO.get("PART_BATCH_NO") != null && !"".equals(stockTransferItemPO.get("PART_BATCH_NO"))){
									oldItem.append(" and PART_BATCH_NO" + stockTransferItemPO.get("PART_BATCH_NO"));
								}else {
									oldItem.append(" and PART_BATCH_NO" + CommonConstants.defaultBatchName);
								}
								TmPartStockItemPO.update("BORROW_QUANTITY = 0,LEND_QUANTITY = 0",
										oldItem.toString(), null);
							}else if(isVal){
								//									增加更新时间和更新人的添加
								nStockUpdate.append(",UPATED_AT = " + Utility.getCurrentDateTime());
								nStockUpdate.append(",UPATED_BY = " + userId);
								int update = TmPartStockPO.update(nStockUpdate.toString(),
										"DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
										dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("NEW_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));	
								if(update <= 0){
									map.put("result","error");
									map.put("msg", "业务错误：更新失败");
									return map;
								}
							}
						}					
					} else {
						costAmountBeforeANew = 0;	//批次表入账前成本
						costAmountBeforeBNew = 0;	//库存表入账前成本
						costAmountAfterANew = 0;	//批次表入账后成本
						costAmountAfterBNew = 0;	//库存表入账后成本
						//查询出拼音代码
						TmPartStockItemPO partStockPOspell = new TmPartStockItemPO();
						String defaultBatchName = "";
						if (!StringUtils.isNullOrEmpty(stockTransferItemPO.get("PART_BATCH_NO"))){
							defaultBatchName = stockTransferItemPO.getString("PART_BATCH_NO");
						} else {
							defaultBatchName = CommonConstants.defaultBatchName;
							partStockPOspell.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
						}
						LazyList<TmPartStockItemPO> spell = TmPartStockItemPO.find("DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ? and PART_BATCH_NO = ?", 
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("Part_No"),stockTransferItemPO.get("OLD_STORAGE_CODE"),defaultBatchName);
						if (spell != null && spell.size() > 0){
							partStockPOspell = (TmPartStockItemPO) spell.get(0);
							if (partStockPOspell != null && !"".equals(partStockPOspell)){
								spellCode = partStockPOspell.getString("SPELL_CODE");
								if (partStockPOspell.get("PART_GROUP_CODE") != null
										&& !"".equals(partStockPOspell.get("PART_GROUP_CODE")))
									partGroupCode = partStockPOspell.getInteger("PART_GROUP_CODE");
								if (partStockPOspell.getDouble("SALES_PRICE") != null
										&& !"".equals(partStockPOspell.getDouble("SALES_PRICE")))
									salesPrice = partStockPOspell.getDouble("SALES_PRICE");
								if (partStockPOspell.getDouble("CLAIM_PRICE") != null
										&& !"".equals(partStockPOspell.getDouble("CLAIM_PRICE")))
									claimPrice = partStockPOspell.getDouble("CLAIM_PRICE");
								if (partStockPOspell.getDouble("LIMIT_PRICE") != null
										&& !"".equals(partStockPOspell.getDouble("LIMIT_PRICE")))
									limitPrice = partStockPOspell.getDouble("LIMIT_PRICE");
								if (partStockPOspell.getDouble("LATEST_PRICE") != null
										&& !"".equals(partStockPOspell.getDouble("LATEST_PRICE")))
									latestPrice = partStockPOspell.getDouble("LATEST_PRICE");
							}
						}
						//对配件库存表增加一条记录
						TmPartStockPO stockPO = new TmPartStockPO();
						stockPO.set("DEALER_CODE",dealerCode);
						stockPO.set("PART_NO",stockTransferItemPO.get("PART_NO"));//配件代码
						stockPO.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));//新仓库代码
						//如果输入了新库位则付新库位的值没有就给原库位的值
						if(StringUtils.isNullOrEmpty(stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"))){
							stockPO.set("STORAGE_POSITION_CODE",stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"));//新库位名称
						}else{
							stockPO.set("STORAGE_POSITION_CODE",stockTransferItemPO.get("OLD_STORAGEPOSITION_CODE"));//旧库位名称
						}
						stockPO.set("PART_NAME",stockTransferItemPO.get("PART_NAME"));//配件名称
						stockPO.set("SPELL_CODE",spellCode);//拼音代码    
						stockPO.set("PART_GROUP_CODE",partGroupCode);//配件类别
						stockPO.set("UNIT_CODE",stockTransferItemPO.get("UNIT_CODE"));//计量单位代码
						if(isPl){							
							stockPO.set("STOCK_QUANTITY",stockNum);//库存数量
							stockPO.set("BORROW_QUANTITY",boNum);
							stockPO.set("LEND_QUANTITY",lendNum);
							stockPO.set("LOCKED_QUANTITY",lockNum);								
						}else{							
							stockPO.set("STOCK_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));//库存数量
							stockPO.set("BORROW_QUANTITY",0f);
							stockPO.set("LEND_QUANTITY",0f);
							stockPO.set("LOCKED_QUANTITY",0f);								
						}
						stockPO.set("SALES_PRICE",salesPrice);//销售价
						stockPO.set("CLAIM_PRICE",claimPrice);//索赔价 
						stockPO.set("LIMIT_PRICE",limitPrice);//销售限价
						stockPO.set("LATEST_PRICE",latestPrice);//最新进货价
						/*
						 * 找库存中改配件单价
						 */
						stockPO.set("COST_PRICE",costPriceStock);//成本单价
						stockPO.set("COST_AMOUNT",costPriceStock * stockTransferItemPO.getDouble("TRANSFER_QUANTITY"));//成本金额
						//新增最大最下库存数量为null
						Base.exec(" UPDATE TM_PART_STOCK SET  MAX_STOCK=null,MIN_STOCK=null  WHERE PART_NO='"
								+ stockTransferItemPO.get("PART_NO") + "' AND DEALER_CODE='" + dealerCode + "'");

						stockPO.set("PART_STATUS",Utility.getInt(CommonConstants.DICT_IS_NO));//配件状态
						stockPO.set("LAST_STOCK_IN",com.yonyou.dms.common.Util.Utility.getSystemTimestamp());//最新入库日期


						//							if (remark != null) 
						//								stockPO.setRemark(remark);//备注
						//							之前移库的配件是给备注赋值为'' 且没有移车型组的信息  现需要给加上  by cy 2015-4-30
						TmPartStockPO oStock = new TmPartStockPO();
						List oList = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
								dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("OLD_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));
						if(oList!=null && oList.size()>0){
							oStock = (TmPartStockPO)oList.get(0);
							remark=oStock.getString("REMARK");
							stockPO.set("REMARK",remark);
							stockPO.set("PART_MODEL_GROUP_CODE_SET",oStock.get("PART_MODEL_GROUP_CODE_SET"));
						}

						// add by 2011-01-27
						// 判断库存是否存在,D_KEY = 4
						List partDKeyList = TmPartStockPO.find(" DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ?", 
								dealerCode,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"));
						if (partDKeyList != null && partDKeyList.size() > 0){
							TmPartStockPO stockPONew = (TmPartStockPO)partDKeyList.get(0);
							if (stockPONew.get("COST_AMOUNT") != null && !"".equals(stockPONew.get("COST_AMOUNT").toString())){
								costAmountBeforeBNew = stockPONew.getDouble("COST_AMOUNT"); //库存表入账前成本金额
							}
							logger.debug("存在隔离配件 :"+stockTransferItemPO.get("PART_NO"));
							stockPO.set("D_KEY",CommonConstants.D_KEY);
							stockPO.set("UPDATED_BY",userId);
//							stockPO.set("UPDATED_AT",Utility.getCurrentDateTime());
							String sql = stockPO.toUpdate();
							DAOUtil.execBatchPreparement(sql, new ArrayList<>());
						}else{
							stockPO.set("CREATED_BY",userId);
//							stockPO.set("CREATED_AT",Utility.getCurrentDateTime());
							stockPO.saveIt();
						}
						

						List listStockAfterNew = TmPartStockPO.find("DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ? and D_KEY = ?", 
								dealerCode,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"),CommonConstants.D_KEY);
						if (listStockAfterNew != null && listStockAfterNew.size() > 0){
							TmPartStockPO StockAfter = (TmPartStockPO)listStockAfterNew.get(0);
							if (StockAfter.getDouble("COST_AMOUNT") != null && !"".equals(StockAfter.getDouble("COST_AMOUNT").toString())){
								costAmountAfterBNew = StockAfter.getDouble("COST_AMOUNT"); //库存表入账后成本金额
							}
						}

						List listItemAfterNew = TmPartStockPO.find("D_KEY = ? and DEALER_CODE = ? and PART_NO = ? and STORAGE_CODE = ?", 
								CommonConstants.DEFAULT_PARA_PART_DELETE_KEY,dealerCode,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"));
						if (listItemAfterNew != null && listItemAfterNew.size() > 0){
							TmPartStockItemPO itemAfter = (TmPartStockItemPO) listItemAfterNew.get(0);
							if (itemAfter.getFloat("COST_AMOUNT") != null && !"".equals(itemAfter.getFloat("COST_AMOUNT").toString())){
								costAmountAfterANew = itemAfter.getFloat("COST_AMOUNT");	//批次表入账后成本金额
							}
						}
						// end
						//POFactory.insert(conn, stockPO);			// edit by sf 2011-01-27 
						//对配件库存批次信息增加一条记录
						TmPartStockItemPO itemPO3 = new TmPartStockItemPO();
						itemPO3.set("DEALER_CODE",dealerCode);
						itemPO3.set("PART_NO",stockTransferItemPO.get("PART_NO"));//配件代码
						itemPO3.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));//新仓库代码
						itemPO3.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));//进货批号
						if(!StringUtils.isNullOrEmpty(stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"))){
							itemPO3.set("STORAGE_POSITION_CODE",stockTransferItemPO.get("NEW_STORAGEPOSITION_CODE"));//新库位代码
						}else{
							itemPO3.set("STORAGE_POSITION_CODE",stockTransferItemPO.get("OLD_STORAGEPOSITION_CODE"));//旧库位代码
						}
						itemPO3.set("PART_NAME",stockTransferItemPO.get("Part_Name"));//配件名称
						itemPO3.set("SPELL_CODE",spellCode);//拼音代码
						itemPO3.set("PART_GROUP_CODE",partGroupCode);//配件类别
						itemPO3.set("UNIT_CODE",stockTransferItemPO.get("UNIT_CODE"));//计量单位代码
						if(isPl){
							itemPO3.set("STOCK_QUANTITY",stockNum);
							itemPO3.set("BORROW_QUANTITY",boNum);
							itemPO3.set("LEND_QUANTITY",lendNum);
						}else{
							itemPO3.set("STOCK_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));//库存数量																
						}
						itemPO3.set("SALES_PRICE",salesPrice);//销售价
						itemPO3.set("CLAIM_PRICE",claimPrice);//索赔价
						itemPO3.set("LIMIT_PRICE",limitPrice);//销售限价  
						itemPO3.set("LATEST_PRICE",latestPrice);//最新进货价
						itemPO3.set("COST_PRICE",costPriceStock);//成本单价
						itemPO3.set("COST_AMOUNT",costPriceStock * stockTransferItemPO.getDouble("TRANSFER_QUANTITY"));//成本金额
						itemPO3.set("PART_STATUS",Utility.getInt(CommonConstants.DICT_IS_NO));//是否起用
						itemPO3.set("LAST_STOCK_IN",com.yonyou.dms.common.Util.Utility.getSystemTimestamp());//最新入库日期
						itemPO3.set("REMARK",remark);//备注
						itemPO3.set("CREATED_BY",userId);
//						itemPO3.set("CREATED_AT",Utility.getCurrentDateTime());
						// add by sf 2011-01-27			
						LazyList<TmPartStockItemPO> partItemDKeyList = TmPartStockItemPO.find("dealer_code = ? and part_no = ? and storage_code = ?", 
								dealerCode,stockTransferItemPO.get("Part_No"),stockTransferItemPO.get("New_Storage_Code"));
						if (partItemDKeyList != null && partItemDKeyList.size() > 0){
							logger.debug("批次表存在 :"+stockTransferItemPO.get("Part_No"));
							itemPO3.set("D_Key",CommonConstants.D_KEY);
							String update = itemPO3.toUpdate();
							DAOUtil.execBatchPreparement(update, new ArrayList<>());
						}else{
							itemPO3.saveIt();
						}
						
						///
						//原库付0
						if(isPl){
							TmPartStockPO oStock1 = new TmPartStockPO();//原库中数据
							List oList1 = TmPartStockPO.find("DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?", 
									dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("OLD_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));

							if(oList1!=null &&oList1.size()>0){
								TmPartStockPO.update("STOCK_QUANTITY = 0,BORRO_WQUANTITY = 0,LEND_QUANTITY = 0,LOCKED_QUANTITY = 0,UPDATED_BY = ?,UPDATED_at = ?", 
										"DEALER_CODE = ? and D_KEY = ? and STORAGE_CODE = ? and PART_NO = ?",
										userId,Utility.getCurrentDateTime(),dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("OLD_STORAGE_CODE"),stockTransferItemPO.get("PART_NO"));
							}

							//原明细表借进借出清0
							if (stockTransferItemPO.get("PART_BATCH_NO") != null && !"".equals(stockTransferItemPO.get("PART_BATCH_NO"))){
								defaultBatchName = stockTransferItemPO.getString("PART_BATCH_NO");
							}else{
								defaultBatchName = CommonConstants.defaultBatchName;
							}
							TmPartStockItemPO.update("BORROW_QUANTITY = 0 and LEND_QUANTITY = 0",
									"DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ? and PART_BATCH_NO = ?",
									dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("OLD_STORAGE_CODE"),defaultBatchName);
						}
					}
					TmPartStockItemPO partStockItemPO = new TmPartStockItemPO();
					String defaultBatchName = "";
					if (stockTransferItemPO.get("PART_BATCH_NO") != null
							&& !"".equals(stockTransferItemPO.get("PART_BATCH_NO"))){
						defaultBatchName = stockTransferItemPO.getString("PART_BATCH_NO");
					}else{
						defaultBatchName = CommonConstants.defaultBatchName;
					}
					liststock = TmPartStockItemPO.find("DEALER_CODE = ? and D_KEY = ? and PART_NO = ? and STORAGE_CODE = ? and PART_BATCH_NO = ?",
							dealerCode,CommonConstants.D_KEY,stockTransferItemPO.get("PART_NO"),stockTransferItemPO.get("NEW_STORAGE_CODE"),defaultBatchName);

					if (liststock != null && liststock.size() > 0){
						partStockItemPO = (TmPartStockItemPO) liststock.get(0);
						flowPOIn.set("STOCK_QUANTITY",partStockItemPO.get("STOCK_QUANTITY"));
					}
					flowPOIn.set("COST_AMOUNT_BEFORE_A",costAmountBeforeANew);
					flowPOIn.set("COST_AMOUNT_BEFORE_B",costAmountBeforeBNew);
					flowPOIn.set("COST_AMOUNT_AFTER_A",costAmountAfterANew);
					flowPOIn.set("COST_AMOUNT_AFTER_B",costAmountAfterBNew);
					flowPOIn.saveIt();
					//移库入库
					//自然月结报表
					TtPartMonthReportPO db = new TtPartMonthReportPO();
					String partBatchNo = stockTransferItemPO.getString("PART_BATCH_NO");
					if (partBatchNo != null && !"".equals(partBatchNo)){
						db.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
					}else{
						db.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
					}
					db.set("PART_NAME",stockTransferItemPO.get("PART_NAME"));
					db.set("PART_NO",stockTransferItemPO.get("PART_NO"));
					db.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));
					db.set("IN_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));
					db.set("STOCK_IN_AMOUNT",stockTransferItemPO.get("COST_AMOUNT"));
					db.set("CREATED_BY",userId);
					db.set("UPDATED_BY",userId);
					//参数没加公用方法
					db.set("OPEN_QUANTITY",newStockQuantity);//入帐前库存数量
					db.set("OPEN_PRICE",costPriceStock);//入帐钱库存成本单价
					db.set("OPEN_AMOUNT",newStockAmount);//入帐前成本金额
					db.set("CLOSE_PRICE",costPriceNew);//入帐后成本单价
					//END
					com.yonyou.dms.common.service.monitor.Utility.createOrUpdate(db, dealerCode);
					//会计月报表



					TtPartPeriodReportPO period = new TtPartPeriodReportPO();
					TtPartPeriodReportDTO ttPartPeriodReportDTO = new TtPartPeriodReportDTO();
					if (partBatchNo != null && !"".equals(partBatchNo)){
						period.set("PART_BATCH_NO",stockTransferItemPO.get("PART_BATCH_NO"));
						ttPartPeriodReportDTO.setPartBatchNo(stockTransferItemPO.getString("PART_BATCH_NO"));
					}else{
						period.set("PART_BATCH_NO",CommonConstants.defaultBatchName);
						ttPartPeriodReportDTO.setPartBatchNo(CommonConstants.defaultBatchName);
					}
					period.set("PART_NAME",stockTransferItemPO.get("PART_NAME"));
					ttPartPeriodReportDTO.setPartName(stockTransferItemPO.getString("PART_NAME"));
					period.set("PART_NO",stockTransferItemPO.get("PART_NO"));
					ttPartPeriodReportDTO.setPartNo(stockTransferItemPO.getString("PART_NO"));
					period.set("STORAGE_CODE",stockTransferItemPO.get("NEW_STORAGE_CODE"));
					ttPartPeriodReportDTO.setStorageCode(stockTransferItemPO.getString("NEW_STORAGE_CODE"));
					period.set("IN_QUANTITY",stockTransferItemPO.get("TRANSFER_QUANTITY"));
					ttPartPeriodReportDTO.setInQuantity(stockTransferItemPO.getDouble("TRANSFER_QUANTITY"));
					period.set("STOCK_IN_AMOUNT",stockTransferItemPO.get("COST_AMOUNT"));
					ttPartPeriodReportDTO.setStockInAmount(stockTransferItemPO.getDouble("COST_AMOUNT"));
					period.set("TRANSFER_IN_COUNT",stockTransferItemPO.get("TRANSFER_QUANTITY"));
					ttPartPeriodReportDTO.setTransferInCount(stockTransferItemPO.getDouble("TRANSFER_QUANTITY"));
					period.set("TRANSFER_IN_AMOUNT",stockTransferItemPO.get("COST_AMOUNT"));
					ttPartPeriodReportDTO.setTransferInAmount(stockTransferItemPO.getDouble("COST_AMOUNT"));
					period.set("CREATED_BY",userId);
					ttPartPeriodReportDTO.setCreatedBy(userId);
					period.set("UPDATED_BY",userId);
					ttPartPeriodReportDTO.setUpdatedBy(userId);
					//方法还未加
					period.set("OPEN_PRICE",costPriceStock);//入帐前成本单价
					ttPartPeriodReportDTO.setOpenPrice(costPriceStock);
					period.set("Open_Quantity",newStockQuantity);//入帐前库存数量
					ttPartPeriodReportDTO.setOpenQuantity(Double.parseDouble(String.valueOf(newStockQuantity)));
					period.set("Open_Amount",newStockAmount);//入帐前成本金额
					ttPartPeriodReportDTO.setOpenAmount(newStockAmount);
					period.set("Close_Price",costPriceNew);//入帐后成本单价
					ttPartPeriodReportDTO.setClosePrice(costPriceNew);
					//end
					AccountPeriodPO cycle = new AccountPeriodPO();
					cycle = stuffPriceAdjustService.getAccountCyclePO();
					
					stuffPriceAdjustService.createOrUpdate(period,ttPartPeriodReportDTO, cycle, dealerCode);
					//移库出库
					//自然月结报表
					TtPartMonthReportPO dbout = new TtPartMonthReportPO();
					if (partBatchNo != null && !"".equals(partBatchNo)){
						dbout.set("Part_Batch_No",stockTransferItemPO.get("Part_Batch_No"));
					}else{
						dbout.set("Part_Batch_No",CommonConstants.defaultBatchName);
					}
					dbout.set("Part_No",stockTransferItemPO.get("Part_No"));
					dbout.set("Storage_Code",stockTransferItemPO.get("Old_Storage_Code"));
					dbout.set("Out_Quantity",stockTransferItemPO.get("Transfer_Quantity"));
					dbout.set("Out_Amount",stockTransferItemPO.get("Cost_Amount"));
					dbout.set("Created_By",userId);
					dbout.set("Updated_By",userId);
					//参数没加公用方法
					dbout.set("Open_Quantity",stockQuantity);//入帐前库存数量
					dbout.set("Open_Price",costPriceStock);//入帐钱库存成本单价
					dbout.set("Open_Amount",costAmountStock);//入帐前成本金额
					dbout.set("Close_Price",costPriceNew);//入帐后成本单价
					//END
					com.yonyou.dms.common.service.monitor.Utility.createOrUpdate(dbout, dealerCode);
					//会计月报表
					AccountPeriodPO cycleout = new AccountPeriodPO();
					cycleout = stuffPriceAdjustService.getAccountCyclePO();
					TtPartPeriodReportPO periodout = new TtPartPeriodReportPO();
					TtPartPeriodReportDTO db2 = new TtPartPeriodReportDTO();
					if (partBatchNo != null && !"".equals(partBatchNo)){
						periodout.set("Part_Batch_No",stockTransferItemPO.get("Part_Batch_No"));
						  db2.setPartBatchNo(stockTransferItemPO.getString("Part_Batch_No"));
					}else{
						periodout.set("Part_Batch_No",CommonConstants.defaultBatchName);
						db2.setPartBatchNo(CommonConstants.defaultBatchName);
					}
					periodout.set("Part_Name",stockTransferItemPO.get("Part_Name"));
					db2.setPartName(stockTransferItemPO.getString("Part_Name"));
					periodout.set("Part_No",stockTransferItemPO.get("Part_No"));
					db2.setPartNo(stockTransferItemPO.getString("Part_No"));
					periodout.set("Storage_Code",stockTransferItemPO.get("Old_Storage_Code"));
					db2.setStorageCode(stockTransferItemPO.getString("Old_Storage_Code"));
					periodout.set("Out_Quantity",stockTransferItemPO.get("Transfer_Quantity"));
					db2.setOutQuantity(stockTransferItemPO.getDouble("Transfer_Quantity"));// 收费区分调整，数量为0
					periodout.set("Out_Amount",stockTransferItemPO.get("Cost_Amount"));
					db2.setOutAmount(stockTransferItemPO.getDouble("Cost_Amount"));
					String partPrice = StringUtils.isNullOrEmpty(stockTransferItemPO.getFloat("Cost_Price"))?"0":stockTransferItemPO.getFloat("Cost_Price").toString();
					periodout.set("Stock_Out_Cost_Amount",Utility.mul(partPrice, stockTransferItemPO
							.getFloat("Transfer_Quantity").toString()));
					db2.setStockOutCostAmount(Utility.mul(partPrice, stockTransferItemPO
							.getFloat("Transfer_Quantity").toString()));
					periodout.set("Transfer_Out_Count",stockTransferItemPO.get("Transfer_Quantity"));
					db2.setTransferOutCount(stockTransferItemPO.getDouble("Transfer_Quantity"));
					periodout.set("Transfer_Out_Cost_Amount",stockTransferItemPO.get("Cost_Amount"));
					db2.setTransferOutCostAmount(stockTransferItemPO.getDouble("Cost_Amount"));
					periodout.set("Created_By",userId);
					db2.setCreatedBy(FrameworkUtil.getLoginInfo().getUserId());
					periodout.set("Updated_By",userId); 
					db2.setUpdatedBy(FrameworkUtil.getLoginInfo().getUserId());
					//方法还未加；；；；；；；
					periodout.set("Open_Price",costPriceStock);//入帐前成本单价
					db2.setOpenPrice(costPriceStock);
					periodout.set("Open_Quantity",stockQuantity);//入帐前库存数量
					db2.setOpenQuantity(Double.parseDouble(String.valueOf(stockQuantity)));
					periodout.set("Open_Amount",costAmountStock);//入帐前成本金额
					db2.setOpenAmount(costAmountStock);
					periodout.set("Close_Price",costPriceNew);//入帐后成本单价
					db2.setClosePrice(costPriceNew);
					//end
					stuffPriceAdjustService.createOrUpdate(periodout,db2, cycleout, dealerCode);

					//添加是否批量移库判断
					TtStockTransferPO tstpo = new TtStockTransferPO();
					List listtst = TtStockTransferPO.find("dealer_code = ? and transfer_no = ? AND D_KEY = ? and is_batch_transfer = ?",
							dealerCode,transferNo,CommonConstants.DICT_IS_YES,CommonConstants.D_KEY);
					if (listtst != null && listtst.size() > 0) {
						if (stockTransferItemPO != null && StringUtils.isNullOrEmpty(stockTransferItemPO.get("Part_No")) && StringUtils.isNullOrEmpty(stockTransferItemPO.get("Old_Storage_Code"))) {
							logger.debug("*移库业务仓库同步更新-");
							//移库业务仓库同步更新到工单维修配件明细、配件销售单明细、调拨出库单明细、借进单明细、借出单明细、预留单
							TtRoRepairPartPO.update("Storage_Code = ?",
									"dealer_code = ? and part_no = ? and storage_code = ? and is_finished = ? and d_key = ?", 
									stockTransferItemPO.get("New_Storage_Code"),dealerCode,stockTransferItemPO.get("Part_No"),
									stockTransferItemPO.get("Old_Storage_Code"),CommonConstants.DICT_IS_NO,CommonConstants.D_KEY);

							TtSalesPartItemPO.update("Storage_Code = ?", 
									"dealer_code = ? and part_no = ? and storage_code = ? and is_finished = ? and d_key = ? ", 
									stockTransferItemPO.get("New_Storage_Code"),dealerCode,stockTransferItemPO.get("Part_No"),
									stockTransferItemPO.get("Old_Storage_Code"),CommonConstants.DICT_IS_NO,CommonConstants.D_KEY);

							String sql = " select b.* from TT_PART_ALLOCATE_OUT a inner join TT_PART_ALLOCATE_OUT_ITEM b on a.DEALER_code=b.DEALER_code and a.ALLOCATE_OUT_NO=b.ALLOCATE_OUT_NO " +
									" where a.IS_FINISHED = 12781002 and a.DEALER_code='" + dealerCode + "' and b.STORAGE_CODE='"  + stockTransferItemPO.get("Old_Storage_Code") + "' AND b.PART_NO='" + stockTransferItemPO.get("Part_No") + "' ";			
							List<Map> listOutItem = DAOUtil.findAll(sql, null);

							if (listOutItem != null && listOutItem.size() > 0) {
								for (Map map1 : listOutItem) {
									if (!StringUtils.isNullOrEmpty(map1.get("ALLOCATE_OUT_NO")) && !StringUtils.isNullOrEmpty(map1.get("PART_NO")) && !StringUtils.isNullOrEmpty(map1.get("STORAGE_CODE"))) {
										PartAllocateOutItemPo.update("storage_code = ?",
												"dealer_code = ? and Allocate_Out_No = ? and part_no = ? and storage_code = ? and_d_key = ?",
												stockTransferItemPO.get("New_Storage_Code"),dealerCode,map.get("ALLOCATE_OUT_NO"),map1.get("PART_NO"),map1.get("STORAGE_CODE"),
												CommonConstants.D_KEY);
									}
								}
							}
							//更新借进单明细的仓库code 
							TtPartBorrowItemPO.update("storage_code = ?", 
									"dealer_code = ? and part_no = ? and d_key = ? and storage_code = ?",
									stockTransferItemPO.get("New_Storage_Code"),
									dealerCode,stockTransferItemPO.get("Part_No"),CommonConstants.D_KEY,stockTransferItemPO.get("Old_Storage_Code"));
							//借出单
							TtPartLendItemPO.update("storage_code = ?", 
									"dealer_code = ? and part_no = ? and d_key = ? and storage_code = ?",
									stockTransferItemPO.get("new_Storage_Code"),
									dealerCode,stockTransferItemPO.get("Part_No"),
									CommonConstants.D_KEY,
									stockTransferItemPO.get("Old_Storage_Code"));

							//预留单
							TtPartObligatedItemPO.update("storage_code = ?", 
									"dealer_code = ? and part_no = ? and d_key = ? and storage_code = ? and is_Obligated = ?",
									stockTransferItemPO.get("New_Storage_Code"),
									dealerCode,stockTransferItemPO.get("Part_No"),CommonConstants.D_KEY,stockTransferItemPO.get("Old_Storage_Code"),CommonConstants.DICT_IS_YES);

						}
					}
				}
			}
		}
		Utility.updateByUnLock("TT_STOCK_TRANSFER", FrameworkUtil.getLoginInfo().getUserId().toString(),"TRANSFER_NO", new String[]{transferNo}, "LOCK_USER");
		map.put("result","success");
		map.put("msg", "移库单出库成功!");
		return map;
	}


	/**
	 * @description 校验配件是否入账
	 * @param sheetNo
	 * @param dealerCode
	 * @param sheetType
	 * @param sheetNoType
	 * @return
	 * @throws Exception
	 */
	public String checkIsFinished(String sheetNo, 
			String dealerCode, String sheetType,String sheetNoType) throws Exception {
		String flag = CommonConstants.DICT_IS_YES;
		if (sheetNo != null && !"".equals(sheetNo.trim()) && sheetType != null
				&& !"".equals(sheetType.trim())) {
			StringBuffer sql = new StringBuffer("");
			sql.append(" select * from "+sheetType+" where DEALER_code='"
					+ dealerCode + "' and d_key=" + CommonConstants.D_KEY
					+ " and "+sheetNoType+"='" + sheetNo + "' and IS_FINISHED="
					+ CommonConstants.DICT_IS_NO + " ");
			List<Map> findAll = DAOUtil.findAll(sql.toString(), null);
			if(null != findAll && !findAll.isEmpty()){
				flag = CommonConstants.DICT_IS_NO;
			}
		}
		return flag;
	}
	
	/**
	 * @discription 更新配件库存数量成本单价成本金额(调拨出库,内部领用，配件销售，配件报损，采购入库，配件移库,维修领料)
	 * @author qmb
	 * @date Nov 26, 2007 9:00:00 AM
	 * @param dealerCode
	 * @param stockItemPO
	 * @param conn
	 * @throws Exception 
	 */
	public int calCostPrice1(String dealerCode, TmPartStockItemPO stockItemPO,Long userId)
			throws Exception {

		StringBuffer sql = new StringBuffer("");
		StringBuffer sqlItem = new StringBuffer("");
		int record = 0;
		double rate = Utility.add("1", Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE)));
		logger.debug("税率:" + rate);
		
		if (Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_BATCH_SATAUS)).equals(CommonConstants.DICT_IS_NO)){
			//不使用批次的时候
			sql.append(" UPDATE TM_PART_STOCK SET UPDATED_AT='"+com.yonyou.dms.common.Util.Utility.getCurrentTime()+"',UPDATED_BY='"+userId+"',STOCK_QUANTITY = " +
							/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
							" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ,"
							+ " COST_PRICE = CASE WHEN (" +
									/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
									" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ) >  0 AND (" +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" + "
							+ " "
							+ stockItemPO.get("Cost_Amount")
							+ " )>= 0  THEN round((" +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" +  "
							+ stockItemPO.get("Cost_Amount")
							+ " )"
							+ " /( " +
									/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
									" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ),4) ELSE (CASE WHEN " +
									/*"COALESCE(LATEST_PRICE,0)"*/ Utility.getChangeNull("", "LATEST_PRICE", 0)+
									"=0 THEN COST_PRICE ELSE " +
									/*"COALESCE(LATEST_PRICE,0)" */Utility.getChangeNull("", "LATEST_PRICE", 0)+
									"/"+rate+" END) END , "
							+ " COST_AMOUNT = " +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" +  "
							+ stockItemPO.get("Cost_Amount") + " ");
			sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = " +
							/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
							" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ,"
							+ " COST_PRICE = CASE WHEN (" +
									/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
									" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ) > 0 AND (" +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" + "
							+ " "
							+ stockItemPO.get("Cost_Amount")
							+ " )>= 0  THEN round((" +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" +  "
							+ stockItemPO.get("Cost_Amount")
							+ " )"
							+ " /( " +
									/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
									" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ),4) ELSE (CASE WHEN " +
									/*"COALESCE(LATEST_PRICE,0)"*/  Utility.getChangeNull("", "LATEST_PRICE", 0)+
									"=0 THEN (SELECT COST_PRICE FROM TM_PART_STOCK WHERE PART_NO='"
							+ stockItemPO.get("Part_No")
							+ "' AND STORAGE_CODE='"
							+ stockItemPO.get("Storage_Code")
							+ "' AND D_KEY="
							+ CommonConstants.D_KEY
							+ " AND DEALER_CODE='"
							+ dealerCode
							+ "') ELSE " +
									/*"COALESCE(LATEST_PRICE,0)"*/ Utility.getChangeNull("", "LATEST_PRICE", 0)+
									"/"+rate+" END) END , "
							+ " COST_AMOUNT = " +
									/*"COALESCE(COST_AMOUNT,0)"*/ Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" +  "
							+ stockItemPO.get("Cost_Amount") + " ");

		}
		else
		{ //使用批次的情况下
			sql.append(" UPDATE TM_PART_STOCK SET UPDATED_AT=CURRENT TIMESTAMP,UPDATED_BY="+userId+",STOCK_QUANTITY = " +
					/*"COALESCE(STOCK_QUANTITY,0)" */Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
					" + "
					+ stockItemPO.get("Stock_Quantity") + " ," + " COST_PRICE = LATEST_PRICE , "
					+ " COST_AMOUNT = " +
							/*"COALESCE(COST_AMOUNT,0)" */Utility.getChangeNull("", "COST_AMOUNT", 0)+
							" -  " + stockItemPO.get("Cost_Amount")
					+ " ");
			sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = " +
							/*"COALESCE(STOCK_QUANTITY,0)"*/ Utility.getChangeNull("", "STOCK_QUANTITY", 0)+
							" + "
							+ stockItemPO.get("Stock_Quantity")
							+ " ,"
							+ " COST_PRICE = LATEST_PRICE , "
							+ " COST_AMOUNT = " +
									/*"COALESCE(COST_AMOUNT,0)" */Utility.getChangeNull("", "COST_AMOUNT", 0)+
									" -  "
							+ stockItemPO.get("Cost_Amount") + " ");
		}
		sql.append(" WHERE DEALER_CODE = '" + dealerCode + "'  AND PART_NO = '"
				+ stockItemPO.get("Part_No") + "' " + " AND STORAGE_CODE = '"
				+ stockItemPO.get("Storage_Code") + "' " + " AND D_KEY = " + CommonConstants.D_KEY);

		sqlItem.append(" WHERE DEALER_CODE = '" + dealerCode + "'  AND PART_NO = '"
				+ stockItemPO.get("Part_No") + "' " + " AND STORAGE_CODE = '"
				+ stockItemPO.get("Storage_Code") + "' " + " AND PART_BATCH_NO = '"
				+ stockItemPO.get("Part_Batch_No") + "' " + " AND D_KEY = " + CommonConstants.D_KEY);
		logger.debug(Utility.checkNegative(dealerCode, stockItemPO.getString("Storage_Code"))+"不允许负库存");
		if(stockItemPO!=null){
			DAOUtil.execBatchPreparement(sql.toString(), new ArrayList<>());
			DAOUtil.execBatchPreparement(sqlItem.toString(), new ArrayList<>());
			logger.debug("sql= " + sql);
			logger.debug("sqlItem= " + sqlItem);
		}
		return 1;
	}

	/**
	 * @description 获取打印的头信息
	 * @param 移库单号
	 * @return
	 */
	@Override
	public Map printPartMoveTitle(String transferNo) {
		if(StringUtils.isNullOrEmpty(transferNo)){
			throw new ServiceBizException("关键信息为空,请联系管理员！");
		}
		List<Map> findAll = Base.findAll("select TRANSFER_NO,TRANSFER_DATE from TT_STOCK_TRANSFER where TRANSFER_NO = ?", transferNo);
		Map result = null;
		if(findAll != null && !findAll.isEmpty()){
			result = findAll.get(0);
		}
		
	
		return result;
	}

	/**
	 * @description 获取打印表格信息
	 * @param transferNo
	 * @return
	 */
	@Override
	public List<Map> printPartMoveInfo(String transferNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sql = new StringBuffer();
				sql.append("select 	tt_stock_transfer.dealer_code,tt_stock_transfer.TRANSFER_NO,tt_stock_transfer.TRANSFER_DATE,tt_stock_transfer_item.PART_NO,tt_stock_transfer_item.PART_NAME,"
						+" tt_stock_transfer_item.OLD_STORAGE_CODE,tm_storage.STORAGE_NAME as OLD_STORAGE_NAME,tt_stock_transfer_item.OLD_STORAGEPOSITION_CODE,"
						+" tt_stock_transfer_item.NEW_STORAGE_CODE,left1.STORAGE_NAME as NEW_STORAGE_NAME,tt_stock_transfer_item.NEW_STORAGEPOSITION_CODE,"
						+"tt_stock_transfer_item.TRANSFER_QUANTITY "
						+ " from tt_stock_transfer "
						+ " left JOIN tt_stock_transfer_item on tt_stock_transfer.TRANSFER_NO = tt_stock_transfer_item.TRANSFER_NO"
						+"  INNER JOIN tm_storage on tm_storage.STORAGE_CODE = tt_stock_transfer_item.OLD_STORAGE_CODE"
						+"  INNER JOIN tm_storage left1 on  left1.STORAGE_CODE = tt_stock_transfer_item.NEW_STORAGE_CODE");
			sql.append(" where tt_stock_transfer.dealer_code = '"+dealerCode+"' and tt_stock_transfer.transfer_no = '"+transferNo+"' and tt_stock_transfer.d_key = " + CommonConstants.D_KEY);
		List<Map> findAll = DAOUtil.findAll(sql.toString(), null);
		return findAll;
	}

	/**
	 * @description 查询符合条件的配件
	 * @param oldStorageCode
	 * @param newStorageCode
	 */
	@Override
	public PageInfoDto queryPartStockItem(String oldStorageCode,String newStorageCode) {
		StringBuffer sql = new StringBuffer("");
		String stor = "";
		String stockCount = "";
		String ghStorage = "";
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if("12781002".equals(commonNoService.getDefalutPara("5433"))){
			ghStorage = " and TS.CJ_TAG=12781001 ";
		}else{
			ghStorage = " and  1=1 ";
		}
		stockCount = " and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY -C.LOCKED_QUANTITY)  > 0 ";
		if (oldStorageCode != null && !oldStorageCode.equals("")){
			stor = " and A.STORAGE_CODE = '" + oldStorageCode + "' ";
		}else{
			stor = " and  1 = 1 ";
		}
		/**
		 * 维修领料,配件销售,车间借料,内部领用,调拨出库,借出登记,配件移库,配件报损,配件预留界面新增查询配件，过滤掉已经停用的配件
		 */
		sql.append(" select A.NODE_PRICE,A.INSURANCE_PRICE,'A' AS partSign, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE AS OLD_STORAGE_CODE, A.PART_BATCH_NO,TS1.STORAGE_CODE AS NEW_STORAGE_CODE,"
				+ "tm_unit.UNIT_NAME,(C.STOCK_QUANTITY + C.BORROW_QUANTITY - C.LEND_QUANTITY - C.LOCKED_QUANTITY) AS TRANSFER_QUANTITY,"
				+ "TS1.STORAGE_NAME AS NEW_STORAGE_NAME,TS.STORAGE_NAME AS OLD_STORAGE_NAME,A.STORAGE_POSITION_CODE AS OLD_STORAGEPOSITION_CODE, A.PART_NAME,"
				+ " A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE,"
				+ " ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY, "
				+ " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO, "		// add by sf 2010-12-17
				+ " A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET, "
				+ " (A.STOCK_QUANTITY + A.BORROW_QUANTITY - " +
				/*"COALESCE(A.LEND_QUANTITY,0)"*/Utility.getChangeNull("A","LEND_QUANTITY", 0)+
				" ) AS USEABLE_QUANTITY, "
				+ " CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO " 
				+ " AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN "+CommonConstants.DICT_IS_YES
				+ " ELSE "+CommonConstants.DICT_IS_NO
				+ " END  AS IS_MAINTAIN "
				+ " from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) "
				+ " LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) "
				+ " LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE"
				+ " INNER JOIN TM_STORAGE TS1 ON A.DEALER_CODE = TS1.DEALER_CODE"
				+ " INNER JOIN (SELECT '"+newStorageCode+"' AS STORAGE_CODE) NEW_STORAGE ON NEW_STORAGE.STORAGE_CODE = TS1.STORAGE_CODE"
				+ " INNER JOIN tm_unit on tm_unit.DEALER_CODE = A.DEALER_CODE AND A.UNIT_CODE = tm_unit.UNIT_CODE"
				+ " WHERE A.PART_STATUS<>"
				+ CommonConstants.DICT_IS_YES
				+ " AND A.DEALER_CODE = '"
				+ dealerCode
				+ "' "
				+ " AND C.D_KEY = "+CommonConstants.D_KEY			// add by sf 2011-01-25
				+ stor
				+ ghStorage + stockCount);
		String[] stoC = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId()).split(",");
		sql.append(" AND ( 1=2 ");
		for (int i = 0; i < stoC.length; i++){
			if (stoC[i] != null && !"".equals(stoC[i].trim())){
				sql.append(" OR A.STORAGE_CODE=" + stoC[i] + "  ");
			}
		}
		sql.append(" ) ");
		logger.debug("sql= " + sql);
		return DAOUtil.pageQuery(sql.toString(), new ArrayList<>());
	}
}
