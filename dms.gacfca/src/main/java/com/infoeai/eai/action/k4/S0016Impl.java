package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4MaterialPricePO;
import com.infoeai.eai.po.TmMaterialPricePO;
import com.infoeai.eai.vo.S0016VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.manage.domains.PO.basedata.TmBusinessParamPercentPO;

@Service
public class S0016Impl extends BaseService implements S0016 {

	private static final Logger logger = LoggerFactory.getLogger(S0015Impl.class);
	@Autowired
	K4SICommonDao dao;

	public List<S0016VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0016VO> voList = new ArrayList<S0016VO>();

		try {

			logger.info("====S0016 getXMLToVO() is START====");
			logger.info("====XML赋值到VO======");
			logger.info("====XMLSIZE:=======" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0016VO outVo = new S0016VO();
						outVo.setModelCode(map.get("MODEL_CODE")); // 车型代码
						outVo.setModelYear(map.get("MODEL_YEAR")); // 年款
						outVo.setSpecialSeries(map.get("SPECIAL_SERIES")); // 特殊车系
						outVo.setMsrpPrice(map.get("MSRP_PRICE")); // 零售价格
						outVo.setDateFrom(map.get("DATE_FROM")); // 有效期 Begin
						outVo.setDateTo(map.get("DATE_TO")); // 有效期 End
						outVo.setRowId(map.get("ROW_ID")); // ROW_ID

						voList.add(outVo);

						logger.info("====outVo:====" + outVo);

					}
				}
			}

			logger.info("====S0016 getXMLToVO() is END====");
		} catch (Throwable e) {
			logger.info("==============XML赋值VO失败===================");
			logger.error(e.getMessage(), e);
			throw new Exception("S0016 XML转换处理异常！" + e);
		} finally {
			logger.info("====S0016 getXMLToVO() is finish====");

		}
		return voList;
	}

	/**
	 * 执行
	 * 
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0016VO> voList) throws Throwable {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("====S0016 is begin====");

		List<returnVO> retVoList = new ArrayList<returnVO>();

		String[] returnVo = null;

		/******************** 开启事物 ********************/
		beginDbService();

		try {

			for (int i = 0; i < voList.size(); i++) {

				returnVo = S0016Check(voList.get(i)); // 校验 S0016VO 数据

				if (null == returnVo || returnVo.length <= 0) {

					k4BusinessProcess(voList.get(i)); // S0016 数据业务处理逻辑

				} else {

					/*
					 * 车辆接口表错误数据插入
					 */
					TiK4MaterialPricePO po = new TiK4MaterialPricePO();
					/// po.setIfId(Long.parseLong(SequenceManager.getSequence("")));//
					/// 接口ID
					po.setString("Model_Code", voList.get(i).getModelCode()); // 车型代码
					po.setString("Model_Year", voList.get(i).getModelYear()); // 年款
					po.setString("Special_Series", voList.get(i).getSpecialSeries()); // 特殊车系
					po.setString("Msrp_Price", voList.get(i).getMsrpPrice()); // 零售价格
					po.setString("Date_From", voList.get(i).getDateFrom()); // 有效期
					// Begin
					po.setString("Date_To", voList.get(i).getDateTo()); // 有效期
																		// End
					po.setString("Row_Id", voList.get(i).getRowId()); // ROW_ID
					po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_NO.toString()); // 是否成功（否）
					po.setString("Is_Message", returnVo[1]); // 错误
					po.setInteger("Create_By", OemDictCodeConstants.K4_S0016); // 创建人ID
					po.setTimestamp("Create_Date", format); // 创建日期
					po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
					po.saveIt();

					/*
					 * 返回错误信息
					 */
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);
					logger.info("==============S0016 返回不合格数据============RowId====" + returnVo[0]);
					logger.info("==============S0016 返回不合格数据============Message====" + returnVo[1]);
				}
			}

			dbService.endTxn(true);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0016业务处理异常！" + e);
		} finally {
			logger.info("====S0016 is finish====");
			dbService.clean();
		}
		return retVoList;
		/******************** 结束事物 ********************/
	}

	/**
	 * S0016数据校验逻辑
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private String[] S0016Check(S0016VO vo) throws Exception {

		logger.info("==============S0016 校验逻辑开始================");

		String[] returnVo = new String[2];

		// ROW_ID 非空校验
		if (CheckUtil.checkNull(vo.getRowId())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "ROW_ID 为空";
			return returnVo;
		}

		// 车型、年款、颜色、内饰是否为有效物料数据
		if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(),
				vo.getSpecialSeries()) == OemDictCodeConstants.IF_TYPE_NO.intValue()) {

			returnVo[0] = vo.getRowId();
			returnVo[1] = "无效物料 车型：" + vo.getModelCode() + "，年款：" + vo.getModelYear() + "，特殊车系："
					+ vo.getSpecialSeries();
			return returnVo;
		}

		// 零售价格非空校验
		if (CheckUtil.checkNull(vo.getMsrpPrice())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "MSRP_PRICE 为空";
			return returnVo;
		}

		// 有效期 Begin 非空校验
		if (CheckUtil.checkNull(vo.getDateFrom())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "DATE_FROM 为空";
			return returnVo;
		}

		// 有效期 Begin 长度校验
		if (vo.getDateFrom().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "DATE_FROM 长度与接口定义不一致：" + vo.getDateFrom();
			return returnVo;
		}

		// 有效期 End 非空校验
		if (CheckUtil.checkNull(vo.getDateTo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "DATE_TO 为空";
			return returnVo;
		}

		// 有效期 End 长度校验
		if (vo.getDateTo().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "DATE_TO 长度与接口定义不一致：" + vo.getDateTo();
			return returnVo;
		}

		return null;
	}

	/*
	 * S0016 数据业务处理逻辑
	 */
	public void k4BusinessProcess(S0016VO vo) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		logger.info("==============S0016 业务处理逻辑开始================");

		// 插入接口表
		TiK4MaterialPricePO po = new TiK4MaterialPricePO();
		// po.setIfId(Long.parseLong(SequenceManager.getSequence("")));// 接口ID
		po.setString("Model_Code", vo.getModelCode()); // 车型代码
		po.setString("Model_Year", vo.getModelYear()); // 年款
		po.setString("Special_Series", vo.getSpecialSeries()); // 特殊车系
		po.setString("Msrp_Price", vo.getMsrpPrice()); // 零售价格
		po.setString("Date_From", vo.getDateFrom()); // 有效期 Begin
		po.setString("Date_To", vo.getDateTo()); // 有效期 End
		po.setString("Row_Id", vo.getRowId()); // ROW_ID
		po.setInteger("Is_Result", OemDictCodeConstants.IF_TYPE_YES); // 是否成功（是）
		po.setInteger("Create_By", OemDictCodeConstants.K4_S0016); // 创建人ID
		po.setTimestamp("Create_Date", format); // 创建日期
		po.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
		po.saveIt();

		TmBusinessParamPercentPO businessParam = new TmBusinessParamPercentPO();
		businessParam.set("Para_Type", OemDictCodeConstants.PARAM_TYPE_07); // 订单价格比例

		List<TmBusinessParamPercentPO> businessParamList = TmBusinessParamPercentPO.find("Para_Type=?",
				OemDictCodeConstants.PARAM_TYPE_07);
		float rate = businessParamList.get(0).getFloat("Order_Rate")/100; // 100;

		// 根据车型代码，年款以及特殊车系查询物料

		String sql = "select * from (" + OemBaseDAO.getVwMaterialSql() + ")vm" + " where  vm.model_Code = '"
				+ vo.getModelCode() + "' and vm.model_Year='" + vo.getModelYear() + "' and vm.SPECIAL_SERIE_CODE='"+vo.getSpecialSeries()+"'";
		List<Map> materialList = OemDAOUtil.findAll(sql, null);
		/*
		 * 若物料信息有多条，则迭代循环新增
		 */
		for (int i = 0; i < materialList.size(); i++) {

			/*
			 * 1、如果已存在 (车型 + 年份 + special serial) 的记录, 对已有记录根据 DATE_FROM 排序, 得到
			 * DATE_FROM 最大的一条记录： A、如果接口的 DATE_FROM 小于这条记录, 则不更新; B、如果等于,
			 * 则直接覆盖该数据; C、如果大于, 则根据接口的 DATE_FROM 更新这条记录的 DATE_TO (即接口的
			 * DATE_FROM - 1 ), 同时新增接口记录; 2、如果不存在 (车型 + 年份 + special serial)
			 * 的记录,则直接新增一条数据
			 */

			List<TmMaterialPricePO> materialPrice = TmMaterialPricePO.find(" Material_Id=?",
					materialList.get(i).get("Material_Id"));
			if (null != materialPrice && materialPrice.size() > 0) {

				// 已存在 (车型 + 年份 + special serial) 的记录

				Date oldEnableDate = (Date) materialPrice.get(0).get("Enable_Date");
				Date newEnableDate = DateUtil.yyyyMMdd2Date(vo.getDateFrom());

				// 接口传来的 DATE_FROM 晚于当前日期，则进入条件
				if (newEnableDate.after(new Date())) {

					boolean isBefore = oldEnableDate.before(newEnableDate);
					boolean isAfter = oldEnableDate.after(newEnableDate);

					// 如果之前（有效期 Begin）早于本次（有效期 Begin），则更新该车款价格
					if (!isBefore && !isAfter) {

						// B、如果等于, 则直接覆盖该数据
						TmMaterialPricePO setMaterialPrice = TmMaterialPricePO.findFirst("Price_Id=?",
								materialList.get(i).get("Price_Id"));
						setMaterialPrice.setDouble("Base_Price", Double.parseDouble(vo.getMsrpPrice()) * rate); // 批发价格
						setMaterialPrice.setDouble("Msrp", Double.parseDouble(vo.getMsrpPrice())); // 零售价格
						setMaterialPrice.setTimestamp("Disable_Date", DateUtil.yyyyMMdd2Date(vo.getDateTo())); // 有效期
						// End
						setMaterialPrice.setInteger("Update_By", OemDictCodeConstants.K4_S0016); // 更新人ID
						setMaterialPrice.setTimestamp("Update_Date", new Date()); // 更新日期
						setMaterialPrice.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
						setMaterialPrice.saveIt();

					} else if (isBefore) {

						// C、如果大于, 则根据接口的 DATE_FROM 更新这条记录的 DATE_TO (即接口的
						// DATE_FROM - 1 ), 同时新增接口记录

						TmMaterialPricePO setMaterialPrice = TmMaterialPricePO.findFirst("Price_Id=?",
								materialPrice.get(0).get("Price_Id"));
						Date disableDate = DateUtil.dateAddAndSubtract(DateUtil.yyyyMMdd2Date(vo.getDateFrom()), -1);
						setMaterialPrice.setTimestamp("Disable_Date", disableDate); // 有效期
																					// End

						setMaterialPrice.setInteger("Update_By", OemDictCodeConstants.K4_S0016); // 更新人ID
						setMaterialPrice.setTimestamp("Update_Date", format); // 更新日期
						setMaterialPrice.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
						setMaterialPrice.saveIt();

						TmMaterialPricePO materialPricePo = new TmMaterialPricePO();
						// materialPricePo.setPriceId(Long.parseLong(SequenceManager.getSequence("")));//
						// 主键ID
						 materialPricePo.setLong("MATERIAL_ID",materialList.get(i).get("Material_Id"));
						// // 物料ID
						materialPricePo.setInteger("Oem_Company_Id",
								Long.parseLong(OemDictCodeConstants.OEM_ACTIVITIES)); // OEM公司ID
						materialPricePo.setDouble("Base_Price", Double.parseDouble(vo.getMsrpPrice()) * rate); // 批发价格
						materialPricePo.setDouble("Msrp", Double.parseDouble(vo.getMsrpPrice())); // 零售价格
						materialPricePo.set("Enable_Date", DateUtil.yyyyMMdd2Date(vo.getDateFrom())); // 有效期
																										// Begin
						materialPricePo.setTimestamp("Disable_Date", DateUtil.yyyyMMdd2Date(vo.getDateTo())); // 有效期
						// End
						materialPricePo.setInteger("Create_By", OemDictCodeConstants.K4_S0016); // 创建人ID
						materialPricePo.setTimestamp("Create_Date", format); // 创建日期
						materialPricePo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
						materialPricePo.saveIt();

					}
				}

			} else {

				// 不存在 (车型 + 年份 + special serial) 的记录
				TmMaterialPricePO materialPricePo = new TmMaterialPricePO();
				// materialPricePo.setPriceId(Long.parseLong(SequenceManager.getSequence("")));//
				// 主键ID
				materialPricePo.setLong("Material_Id", materialList.get(i).get("Material_Id")); // 物料ID
				materialPricePo.setLong("Oem_Company_Id", Long.parseLong(OemDictCodeConstants.OEM_ACTIVITIES)); // OEM公司ID
				materialPricePo.setDouble("Base_Price", Double.parseDouble(vo.getMsrpPrice()) * rate); // 批发价格
				materialPricePo.setDouble("Msrp", Double.parseDouble(vo.getMsrpPrice())); // 零售价格
				materialPricePo.setTimestamp("Enable_Date", DateUtil.yyyyMMdd2Date(vo.getDateFrom())); // 有效期
				// Begin
				materialPricePo.setTimestamp("Disable_Date", DateUtil.yyyyMMdd2Date(vo.getDateTo())); // 有效期
				// End
				materialPricePo.setInteger("Create_By", OemDictCodeConstants.K4_S0016); // 创建人ID
				materialPricePo.setTimestamp("Create_Date", format); // 创建日期
				materialPricePo.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00); // 逻辑删除
				materialPricePo.saveIt();
			}

		}

		logger.info("==============S0016 业务处理逻辑结束================");

	}

	/**
	 * 校验车型、年款、特殊车系是否为有效物料数据
	 * 
	 * @param modelCode
	 * @param modelYear
	 * @param colorCode
	 * @param trimCode
	 * @return
	 * @throws Exception
	 */
	private int doMaterialCheck(String modelCode, String modelYear, String specialSeries) throws Exception {

		String sql = "select * from (" + OemBaseDAO.getVwMaterialSql() + ")vm" + " where  vm.model_Code = '" + modelCode
				+ "' and vm.model_Year='" + modelYear + "' and vm.SPECIAL_SERIE_CODE='"+specialSeries+"'";
		List<Map> list = OemDAOUtil.findAll(sql, null);
		/*
		 * 校验车架号是否已存在
		 */
		if (list.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES; // 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO; // 否
		}
	}

	/**
	 * 根据物料ID获得物料基础定价
	 * 
	 * @param materialId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private TmMaterialPricePO getMaterialPrice(long materialId) throws Exception {

		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT A.PRICE_ID, -- 主键ID \n");
		sql.append("       A.MATERIAL_ID, -- 物料ID \n");
		sql.append("       A.ENABLE_DATE -- 有效期 Begin \n");
		sql.append("  FROM TM_MATERIAL_PRICE A, \n");
		sql.append("       (SELECT MATERIAL_ID, MAX(ENABLE_DATE) AS ENABLE_DATE \n");
		sql.append("          FROM TM_MATERIAL_PRICE \n");
		sql.append("         GROUP BY MATERIAL_ID) B \n");
		sql.append(" WHERE A.MATERIAL_ID = B.MATERIAL_ID \n");
		sql.append("   AND A.ENABLE_DATE = B.ENABLE_DATE \n");
		sql.append("   AND A.MATERIAL_ID = '" + materialId + "' \n");
		List<TmMaterialPricePO> list = new ArrayList<>();
		List<Map> list2 = OemDAOUtil.findAll(sql.toString(), null);
		TmMaterialPricePO po = new TmMaterialPricePO();
		if (null != list && list.size() > 0) {
			for (Map map : list2) {
				po.setInteger("Price_Id", map.get("PRICE_ID"));
				po.setLong("Material_Id", map.get("MATERIAL_ID"));
				po.setTimestamp("Enable_Date", map.get("ENABLE_DATE"));
			}
			return po;
		} else {
			return null;
		}

	}

}
