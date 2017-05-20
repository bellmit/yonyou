package com.infoeai.eai.action.k4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4VsDnReturnPO;
import com.infoeai.eai.vo.S0004VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0004Impl extends BaseService implements S0004 {

	private static final Logger logger = LoggerFactory.getLogger(S0004Impl.class);
	@Autowired
	K4SICommonDao dao;

	/**
	 * 执行入口
	 * 
	 * @param voList
	 * @return
	 * @throws Throwable
	 */
	public List<returnVO> execute(List<S0004VO> voList) throws Throwable {

		logger.info("==========S0004 is begin==========");

		List<returnVO> retVoList = new ArrayList<returnVO>();
		String[] returnVo = null;

		/********** 开启事物 **********/

		beginDbService();

		try {

			for (int i = 0; i < voList.size(); i++) {

				S0004VO s0004 = new S0004VO();
				s0004 = voList.get(i);

				// 校验S0004数据逻辑
				returnVo = s0004Check(s0004);

				if (returnVo == null) {

					// 插入接口表
					s0004.setIsResult(OemDictCodeConstants.IF_TYPE_YES.toString());
					this.saveTiTable(s0004);

					// 一下是业务处理方法方法参数是循环出voList的vo
					k4BusinessProcess(s0004);

				} else {

					// 插入接口表
					s0004.setIsResult(OemDictCodeConstants.IF_TYPE_NO.toString());
					s0004.setIsMessage(returnVo[1]);
					this.saveTiTable(s0004);

					// 返回错误信息
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);

					logger.info("==========S0004 返回不合格数据==========RowId==========" + returnVo[0]);

					logger.info("==========S0004 返回不合格数据==========Message==========" + returnVo[1]);

				}
			}

			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0004业务处理异常！" + e);
		} finally {
			logger.info("==========S0004 is finish==========");
			Base.detach();
			dbService.clean();
		}
		return retVoList;
		/********** 结束事物 **********/
	}

	/**
	 * S0004数据校验逻辑
	 */
	private String[] s0004Check(S0004VO vo) {
		logger.info("==============S0004 校验逻辑开始================");
		// 将错误的数据rowID放入returnVo[0],message放入returnVo[1]中
		String[] returnVo = new String[2];

		if (null == vo.getRowId() || "".equals(vo.getRowId())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "ROWID为空值";
			return returnVo;
		}

		if (null == vo.getSoNo() || "".equals(vo.getSoNo())) {

			returnVo[0] = vo.getRowId();
			returnVo[1] = "销售订单号为空值";
			return returnVo;

		} else {

			List<TtVsOrderPO> orderList = TtVsOrderPO.find("So_No=?", vo.getSoNo());

			if (null == orderList || orderList.size() <= 0) {

				returnVo[0] = vo.getRowId();
				returnVo[1] = "销售订单号在订单表中不存在";
				return returnVo;

			} /*
				 * else {
				 * 
				 * // 订单状态 Integer orderStatus =
				 * orderList.get(0).getOrderStatus();
				 * 
				 * if (orderStatus !=
				 * OemDictCodeConstants.SALE_ORDER_TYPE_07.intValue()) {
				 * 
				 * 
				 * SALE_ORDER_TYPE_07 = 70031007; // 扣款成功
				 * 订单状态非以上(70031007)，则返回错误信息
				 * 
				 * 
				 * returnVo[0] = vo.getRowId(); returnVo[1] = "不符合当前订单状态(" +
				 * orderStatus + ")"; return returnVo; } }
				 */
		}

		if (null == vo.getDnNo() || "".equals(vo.getDnNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单号为空值";
			return returnVo;
		}

		if (null == vo.getVin() || "".equals(vo.getVin())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "vin码为空值";
			return returnVo;
		} else if (vo.getVin().length() != 17) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "的vin码长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDnCreateDate() || "".equals(vo.getDnCreateDate())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单创建日期为空值";
			return returnVo;
		} else if (vo.getDnCreateDate().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单创建日期长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDnCreateTime() || "".equals(vo.getDnCreateTime())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单创建时间为空值";
			return returnVo;
		} else if (vo.getDnCreateTime().length() != 6) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "订单创建时间长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDnPostingDate() || "".equals(vo.getDnPostingDate())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单过账日期为空值";
			return returnVo;
		} else if (vo.getDnPostingDate().length() != 8) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单过账日期长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getDnPostingTime() || "".equals(vo.getDnPostingTime())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单过账时间为空值";
			return returnVo;
		} else if (vo.getDnPostingTime().length() != 6) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交货单过账时间长度与接口定义不一致";
			return returnVo;
		}

		if (null == vo.getFactoryCode() || "".equals(vo.getFactoryCode())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "发货工厂为空值";
			return returnVo;
		}

		logger.info("==============S0004 校验逻辑结束================");
		return null;
	}

	/*
	 * S0004数据业务处理逻辑
	 */
	public void k4BusinessProcess(S0004VO s0004Vo) throws Throwable {

		logger.info("==========S0004 业务处理逻辑开始==========");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		String dnCreateDate = s0004Vo.getDnCreateDate() + " " + s0004Vo.getDnCreateTime();
		String dnPostingDate = s0004Vo.getDnPostingDate() + " " + s0004Vo.getDnPostingTime();
		// update_date 20151208 by maxiang begin..
		List<TtVsOrderPO> list = TtVsOrderPO.find("So_No=?", s0004Vo.getSoNo());
		if (null != list && list.size() > 0) {
			TtVsOrderPO setOrderPo = new TtVsOrderPO();
			setOrderPo.setString("Dn_No", s0004Vo.getDnNo());
			setOrderPo.setString("Vin", s0004Vo.getVin());
			setOrderPo.setTimestamp("Dn_Create_Date", Utility.parseString2DateTime(dnCreateDate, "yyyyMMdd HHmmss"));
			setOrderPo.setTimestamp("Dn_Posting_Date", Utility.parseString2DateTime(dnPostingDate, "yyyyMMdd HHmmss"));
			setOrderPo.setString("Factory_Code", s0004Vo.getFactoryCode());
			// add by huyu 2016-7-22
			// 如果当前订单状态是“扣款成功”，将订单状态更新为“已生成发货单”
			Integer orderStatus = Integer.parseInt(list.get(0).get("Order_Status").toString());
			if (orderStatus == OemDictCodeConstants.SALE_ORDER_TYPE_07) {
				setOrderPo.setInteger("Order_Status", OemDictCodeConstants.SALE_ORDER_TYPE_08);
			}
			// end by huyu
			setOrderPo.setInteger("Update_By", OemDictCodeConstants.K4_S0004);
			setOrderPo.setTimestamp("Update_Date", format);

			setOrderPo.saveIt();
			// 新增订单历史记录
			CommonDAO.insertHistory((Long) list.get(0).get("Order_Id"), OemDictCodeConstants.SALE_ORDER_TYPE_08,
					"已生成发货单", "SAP", OemDictCodeConstants.K4_S0004, "");
		}
		// update_date 20151208 by maxiang end..

		logger.info("==========S0004 业务处理逻辑结束==========");
	}

	public List<S0004VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {

		List<S0004VO> voList = new ArrayList<S0004VO>();

		try {

			logger.info("==========S0004 getXMLToVO() is START==========");

			logger.info("==========XML赋值到VO==========");

			logger.info("==========XMLSIZE:==========" + xmlList.size());

			if (xmlList != null && xmlList.size() > 0) {

				for (int i = 0; i < xmlList.size(); i++) {

					Map<String, String> map = xmlList.get(i);

					if (map != null && map.size() > 0) {

						S0004VO outVo = new S0004VO();
						outVo.setSoNo(map.get("SO_NO")); // 销售订单号
						outVo.setDnNo(map.get("DN_NO")); // 交货单号码
						outVo.setDnCreateDate(map.get("DN_CREATE_DATE")); // 交货单创建日期
						outVo.setDnCreateTime(map.get("DN_CREATE_TIME")); // 交货单创建时间
						outVo.setDnPostingDate(map.get("DN_POSTING_DATE")); // 交货单过账日期
						outVo.setDnPostingTime(map.get("DN_POSTING_TIME")); // 交货单过账时间
						outVo.setFactoryCode(map.get("FACTORY_CODE")); // 发货工厂
						outVo.setVin(map.get("VIN")); // 车架号
						outVo.setRowId(map.get("ROW_ID")); // ROWID
						voList.add(outVo);

						logger.info("==========outVo:==========" + outVo);

					}
				}
			}

			logger.info("==========S0004 getXMLToVO() is END==========");

		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0004 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0004 getXMLToVO() is finish==========");
		}

		return voList;

	}

	/**
	 * 写入接口表数据
	 * 
	 * @param vo
	 */
	public void saveTiTable(S0004VO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		TiK4VsDnReturnPO soReturn = new TiK4VsDnReturnPO();

		// soReturn.setIfId(new Long(SequenceManager.getSequence("")));
		soReturn.setString("So_No", vo.getSoNo());
		soReturn.setString("Dn_No", vo.getDnNo());
		soReturn.setString("Vin", vo.getVin());
		soReturn.setString("Dn_Create_Date", vo.getDnCreateDate());
		soReturn.setString("Dn_Create_Time", vo.getDnCreateTime());
		soReturn.setString("Dn_Posting_Date", vo.getDnPostingDate());
		soReturn.setString("Dn_Posting_Time", vo.getDnPostingTime());
		soReturn.setString("Factory_Code", vo.getFactoryCode());
		soReturn.setString("Row_Id", vo.getRowId());
		soReturn.setLong("Create_By", OemDictCodeConstants.K4_S0004);
		soReturn.set("Create_Date2", new Date());
		soReturn.setInteger("Is_Del", OemDictCodeConstants.IS_DEL_00);
		soReturn.setInteger("Is_Result", vo.getIsResult());
		soReturn.set("Is_Message", vo.getIsMessage());

		soReturn.saveIt();

	}
}
